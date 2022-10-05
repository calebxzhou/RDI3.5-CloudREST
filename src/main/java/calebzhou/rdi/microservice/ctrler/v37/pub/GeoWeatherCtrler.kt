package calebzhou.rdi.microservice.ctrler.v37.pub

import calebzhou.rdi.microservice.HttpClient
import calebzhou.rdi.microservice.constant.CloudConst
import calebzhou.rdi.microservice.model.GeoLocation
import calebzhou.rdi.microservice.model.RdiWeather
import calebzhou.rdi.microservice.model.parseCaiyunWeather
import calebzhou.rdi.microservice.utils.RdiHttpClient
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap
import okhttp3.Request
import org.springframework.beans.factory.annotation.Autowired
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
}