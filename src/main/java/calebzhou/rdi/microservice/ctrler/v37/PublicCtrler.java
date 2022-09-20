package calebzhou.rdi.microservice.ctrler.v37;


import calebzhou.rdi.microservice.constant.CloudConst;
import calebzhou.rdi.microservice.model.json.GeoLocation;
import calebzhou.rdi.microservice.model.json.RdiGeoLocation;
import calebzhou.rdi.microservice.model.json.RdiWeather;
import calebzhou.rdi.microservice.model.json.weather.CaiyunWeather;
import calebzhou.rdi.microservice.utils.IpRegionUtils;
import calebzhou.rdi.microservice.utils.RdiHttpClient;
import calebzhou.rdi.microservice.model.json.TencentIpLocation;
import calebzhou.rdi.microservice.utils.RdiSerializer;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import okhttp3.Request;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Timer;
import java.util.TimerTask;

@RestController
@RequestMapping("/v37/public")
public class PublicCtrler {

    //ip转换成 国省市区
    @GetMapping("/ip2loca")
    public RdiGeoLocation  china_ip2loca(HttpServletRequest req,@RequestParam String ip){
        if(ip.startsWith("0:0:0:0:0:0:0:") || ip.equals("127.0.0.1"))
            ip = "119.119.99.199";
        Request request = new RdiHttpClient.RequestBuilder()
                .type(RdiHttpClient.RequestType.GET)
                .url("https://apis.map.qq.com/ws/location/v1/ip")
                .param("ip", ip)
                .param("key", CloudConst.tencentLbsKey)
                .build();
        String json = RdiHttpClient.sendRequest(request);
        String isp = IpRegionUtils.getIspByIp(ip);
        TencentIpLocation lbs = RdiSerializer.GSON.fromJson(json, TencentIpLocation.class);

        return new RdiGeoLocation(
                lbs.result.ad_info.nation,
                lbs.result.ad_info.province,
                lbs.result.ad_info.city,
                lbs.result.ad_info.district,
                isp,
               new GeoLocation( lbs.result.location.lat,
                lbs.result.location.lng)
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
}
