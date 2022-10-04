package calebzhou.rdi.microservice.ctrler.v37.pub;


import calebzhou.rdi.microservice.constant.CloudConst;
import calebzhou.rdi.microservice.dao.RecordMapper;
import calebzhou.rdi.microservice.model.GeoLocation;
import calebzhou.rdi.microservice.model.Ip2RegionData;
import calebzhou.rdi.microservice.model.RdiGeoLocation;
import calebzhou.rdi.microservice.model.RecordBlock;
import calebzhou.rdi.microservice.model.json.RdiWeather;
import calebzhou.rdi.microservice.model.json.loca.Location;
import calebzhou.rdi.microservice.model.json.loca.TencentGeoCoder;
import calebzhou.rdi.microservice.utils.RdiHttpClient;
import calebzhou.rdi.microservice.model.json.TencentIpLocation;
import com.ip2location.IPResult;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import okhttp3.Request;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@RestController
@RequestMapping("/v37/public")
public class PublicCtrler {

    //ip转换成 国省市区
    @GetMapping("/ip2loca")
    public RdiGeoLocation  china_ip2loca(@RequestParam String ip){
        if(ip.startsWith("0:0:0:0:0:0:0:") || ip.equals("127.0.0.1"))
            ip = "202.107.26.97";
        Request request = new RdiHttpClient.RequestBuilder()
                .type(RdiHttpClient.RequestType.GET)
                .url("https://apis.map.qq.com/ws/location/v1/ip")
                .param("ip", ip)
                .param("key", CloudConst.tencentLbsKey)
                .build();
        Ip2RegionData ip2RegionData = IpGeoUtils.searchIp2Region(ip);
        TencentIpLocation lbs = RdiSerializer.GSON.fromJson(RdiHttpClient.sendRequest(request), TencentIpLocation.class);
        //https://apis.map.qq.com/ws/geocoder/v1/?address=新疆克孜勒苏&key=IQJBZ-AKBCI-CBMGL-5GJ53-UJHNQ-RQBOV
        String nation = lbs.result.ad_info.nation;
        String province = lbs.result.ad_info.province;
        String city = lbs.result.ad_info.city;
        double latitude = lbs.result.location.lat;
        double longitude = lbs.result.location.lng;
        //中国境内并且省份为空 说明没查到地址 去ip2region里面查
        if("中国".equals(nation)&& StringUtils.isEmpty(province)){
            province=ip2RegionData.getProvince();
            city=ip2RegionData.getCity();
            //拿到了省份和城市 去腾讯云查
            request = new RdiHttpClient.RequestBuilder()
                    .type(RdiHttpClient.RequestType.GET)
                    .url("https://apis.map.qq.com/ws/geocoder/v1/")
                    .param("address", province+city)
                    .param("key", CloudConst.tencentLbsKey)
                    .build();
            TencentGeoCoder tencentGeoCoder = RdiSerializer.GSON.fromJson(RdiHttpClient.sendRequest(request), TencentGeoCoder.class);
            Location location = tencentGeoCoder.getResult().getLocation();
            latitude = location.getLat();
            longitude = location.getLng();
        }
        //不在中国境内 去ip2location查询
        if(!"中国".equals(nation)){
            IPResult ipResult = IpGeoUtils.searchIp2Location(ip);
            nation=ipResult.getCountryLong()+"|"+ipResult.getCountryShort();
            province=ipResult.getRegion();
            city=ipResult.getCity();
            latitude=ipResult.getLatitude();
            longitude=ipResult.getLongitude();
        }


        return new RdiGeoLocation(
                nation,
                province,
                city,
                lbs.result.ad_info.district,
                ip2RegionData.getIsp(),
               new GeoLocation(latitude, longitude)
        );

    }
    //10分钟刷新一次天气预报信息
    private static final Object2ObjectOpenHashMap<GeoLocation,RdiWeather> locaWeatherMap = new Object2ObjectOpenHashMap<>();
    static {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                locaWeatherMap.clear();
            }
        },0,10*60*1000);
    }
    @GetMapping( "/weather")
    public RdiWeather globalWeatherNow(@RequestParam double latitude,@RequestParam double longitude){
        RdiWeather rdiWeather;
        GeoLocation geoLocation = new GeoLocation(latitude, longitude);
        if(locaWeatherMap.containsKey(geoLocation)){
            rdiWeather = locaWeatherMap.get(geoLocation);
            return rdiWeather;
        }
        Request request = new RdiHttpClient.RequestBuilder()
                .type(RdiHttpClient.RequestType.GET)
                .url("https://api.caiyunapp.com/v2.5/%s/%f,%f/weather.json".formatted(CloudConst.caiyunWeatherKey,longitude,latitude))
                .param("alert", "true")
                .build();
        String json = RdiHttpClient.sendRequest(request);
        CaiyunWeather weather = RdiSerializer.GSON.fromJson(json, CaiyunWeather.class);
        rdiWeather = RdiWeather.fromCaiyunWeather(weather);
        locaWeatherMap.put(geoLocation,rdiWeather);
        return rdiWeather;
    }

    @Autowired
    RecordMapper recordMapper;
    @GetMapping("/block_record/{dim}/{x}/{y}/{z}")
    public List<RecordBlock> getRecordBlock(@PathVariable String dim, @PathVariable int x, @PathVariable int y, @PathVariable int z){
        return recordMapper.getRecordBlocksByCoord(dim, x, y, z);
    }
}
