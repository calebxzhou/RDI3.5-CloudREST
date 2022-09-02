package calebzhou.rdi.microservice.ctrler;


import calebzhou.rdi.microservice.constants.CloudConst;
import calebzhou.rdi.microservice.model.json.RdiGeoLocation;
import calebzhou.rdi.microservice.utils.RdiHttpClient;
import calebzhou.rdi.microservice.model.json.TencentIpLocation;
import calebzhou.rdi.microservice.utils.RdiSerializer;
import okhttp3.Request;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/public")
public class PublicCtrler {

    @RequestMapping(value = "/ip2loca",method = RequestMethod.GET)
    public String  china_ip2loca(HttpServletRequest req, HttpServletResponse resp){
        String ip = req.getRemoteAddr();
        Request request = new RdiHttpClient.RequestBuilder()
                .type(RdiHttpClient.RequestType.GET)
                .param("ip", ip)
                .param("key", CloudConst.tencentLbsKey)
                .url("https://apis.map.qq.com/ws/location/v1/ip")
                .build();
        String json = RdiHttpClient.sendRequest(request);
        TencentIpLocation lbs = RdiSerializer.GSON.fromJson(json, TencentIpLocation.class);

        RdiGeoLocation rdiGeoLocation = new RdiGeoLocation(
                lbs.result.ad_info.nation,
                lbs.result.ad_info.province,
                lbs.result.ad_info.city,
                lbs.result.ad_info.district,
                "//...",
                lbs.result.location.lat,
                lbs.result.location.lng
        );
        return RdiSerializer.GSON.toJson(rdiGeoLocation);

    }
}
