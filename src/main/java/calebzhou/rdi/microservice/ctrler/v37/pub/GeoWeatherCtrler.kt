package calebzhou.rdi.microservice.ctrler.v37.pub

import calebzhou.rdi.microservice.HttpClient
import calebzhou.rdi.microservice.constant.CloudConst
import calebzhou.rdi.microservice.model.GeoLocation
import calebzhou.rdi.microservice.model.RdiGeoLocation
import calebzhou.rdi.microservice.model.RdiWeather
import calebzhou.rdi.microservice.model.json.tencentlbs.geocoder.TencentLbsGeoCoder
import calebzhou.rdi.microservice.model.json.tencentlbs.ip.TencentLbsIpToLocation
import calebzhou.rdi.microservice.model.parseCaiyunWeather
import calebzhou.rdi.microservice.utils.IpGeoUtils
import calebzhou.rdi.microservice.utils.RdiSerializer
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.Request
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*

/**
 * Created by calebzhou on 2022-10-05,21:20.
 */
@RestController
@RequestMapping("/v37/public")
class GeoWeatherCtrler {
    //10分钟刷新一次天气预报信息
    private val locaWeatherMap = Object2ObjectOpenHashMap<GeoLocation, RdiWeather>()
    init{
        Timer().schedule(object :TimerTask(){
            override fun run() {
                locaWeatherMap.clear()
            } },0,10*60*1000)
    }
    //获取天气
    @GetMapping("/weather")
    fun globalWeatherNow(@RequestParam latitude: Double, @RequestParam longitude: Double): RdiWeather {
        val rdiWeather: RdiWeather
        val geoLocation = GeoLocation(latitude, longitude)
        if (locaWeatherMap.containsKey(geoLocation)) {
            rdiWeather = locaWeatherMap[geoLocation]!!
            return rdiWeather
        }
        HttpClient.newCall(Request.Builder()
            .url("https://api.caiyunapp.com/v2.5/${CloudConst.caiyunWeatherKey}/${longitude},${latitude}/weather.json")
            .build()).execute().use { response->
            rdiWeather = parseCaiyunWeather(response.body!!.string())
            locaWeatherMap[geoLocation] = rdiWeather
            return rdiWeather
        }

    }
    @GetMapping("/ip2loca")
    fun ip2loca(@RequestParam ip:String): RdiGeoLocation {
        val processedIp = if (ip.startsWith("0:0:0:0:0:0:0:")|| ip == "127.0.0.1") "202.107.26.97" else ip
        var lbsJson:String;
        HttpClient.newCall(Request.Builder()
            .url("https://apis.map.qq.com/ws/location/v1/ip".toHttpUrl().newBuilder()
                .addQueryParameter("ip",processedIp)
                .addQueryParameter("key",CloudConst.tencentLbsKey)
                .build())
            .build()).execute().use { response->
            lbsJson = response.body!!.string()
        }
        val ip2RegionData = IpGeoUtils.searchIp2Region(ip)
        val lbs = RdiSerializer.gson.fromJson(lbsJson, TencentLbsIpToLocation::class.java)
        val adInfo = lbs.result!!.adInfo!!
        val location = lbs.result!!.location!!

        var nation = adInfo.nation!!
        var province = adInfo.province?:""
        var city = adInfo.city?:""
        val dist = adInfo.district?:""

        var latitude = location.lat!!
        var longitude = location.lng!!

        //中国境内并且省份为空 说明没查到地址 去ip2region里面查
        if(province.isEmpty() && nation == "中国"){
            province=ip2RegionData.province
            city=ip2RegionData.city
            //拿到了省份和城市 去腾讯云查
            HttpClient.newCall(Request.Builder()
                .url("https://apis.map.qq.com/ws/geocoder/v1/".toHttpUrl().newBuilder()
                    .addQueryParameter("address",province+city)
                    .addQueryParameter("key",CloudConst.tencentLbsKey)
                    .build())
                .build()).execute().use { response->
                val lbsGeoCoder = RdiSerializer.gson.fromJson(response.body!!.string(), TencentLbsGeoCoder::class.java)
                var locaGeo = lbsGeoCoder!!.result!!.location!!
                latitude = locaGeo.lat!!
                longitude= locaGeo.lng!!
            }
        }
        //不在中国境内 去ip2location查询
        if(nation != "中国"){
            val ipResult = IpGeoUtils.searchIp2Location(ip);
            nation=ipResult.countryLong+"|"+ipResult.countryShort;
            province=ipResult.region;
            city=ipResult.city;
            latitude= ipResult.latitude.toDouble();
            longitude= ipResult.longitude.toDouble();
        }

        return RdiGeoLocation(nation,province,city, dist,ip2RegionData.isp, GeoLocation(latitude,longitude))

    }
}