package calebzhou.rdi.microservice.ctrler.v37;


import calebzhou.rdi.microservice.constant.CloudConst;
import calebzhou.rdi.microservice.model.json.RdiGeoLocation;
import calebzhou.rdi.microservice.model.json.RdiWeather;
import calebzhou.rdi.microservice.model.json.weather.CaiyunWeather;
import calebzhou.rdi.microservice.utils.IpRegionUtils;
import calebzhou.rdi.microservice.utils.RdiHttpClient;
import calebzhou.rdi.microservice.model.json.TencentIpLocation;
import calebzhou.rdi.microservice.utils.RdiSerializer;
import okhttp3.Request;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/v37/public")
public class PublicCtrler {

    //ip转换成 国省市区
    @GetMapping("/ip2loca")
    public RdiGeoLocation  china_ip2loca(HttpServletRequest req,@RequestParam String ip){
        if(ip.startsWith("0:0:0:0:0:0:0:") || ip.equals("127.0.0.1"))
            ip = "119.117.129.78";
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
                lbs.result.location.lat,
                lbs.result.location.lng
        );

    }
    @GetMapping( "/weather")
    public RdiWeather globalWeatherNow(@RequestParam double latitude,@RequestParam double longitude){
        Request request = new RdiHttpClient.RequestBuilder()
                .type(RdiHttpClient.RequestType.GET)
                .url("https://api.caiyunapp.com/v2.5/%s/%f,%f/weather.json".formatted(CloudConst.caiyunWeatherKey,longitude,latitude))
                .param("alert", "true")
                .build();
        String json = RdiHttpClient.sendRequest(request);
        CaiyunWeather weather = RdiSerializer.GSON.fromJson(json, CaiyunWeather.class);
        return RdiWeather.fromCaiyunWeather(weather);
    }
}
