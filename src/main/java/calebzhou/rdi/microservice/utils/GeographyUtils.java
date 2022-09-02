package calebzhou.rdi.microservice.utils;

import calebzhou.rdi.microservice.App;
import calebzhou.rdi.microservice.constants.CloudConst;
import calebzhou.rdi.microservice.model.json.TencentIpLocation;
import calebzhou.rdi.microservice.model.geo.GeoLocationForeign;
import calebzhou.rdi.microservice.model.geo.SimpleGeoLocation;
import com.google.gson.Gson;

public class GeographyUtils {
    public static SimpleGeoLocation getSimpleGeoLocation(String ip){
        if(App.DEBUG)
            ip="42.177.210.38";
        SimpleGeoLocation geoLocation = new SimpleGeoLocation();
        TencentIpLocation location = getGeoLocationFromIP(ip);
        if(location.result.ad_info.nation.equals("中国")){
            geoLocation.setCountry(location.result.ad_info.nation)
                    .setProvince(location.result.ad_info.province)
                    .setCity(location.result.ad_info.city)
                    .setDistrict(location.result.ad_info.district);
        }else{
            GeoLocationForeign foreign = getLocationFromForeignIP(ip);
            geoLocation.setCountry(foreign.country_name);
            geoLocation.setProvince(foreign.region_name);
            geoLocation.setCity(foreign.city);
            geoLocation.setDistrict(foreign.continent_name);
        }
        return geoLocation;
    }
    public static TencentIpLocation getGeoLocationFromIP(String ip) {
        String httpurl = "https://apis.map.qq.com/ws/location/v1/ip?ip=" + ip + "&key=" + CloudConst.tencentLbsKey;
        String locationResult = RdiHttpClient.sendRequestAsync(new RdiHttpClient(RdiHttpClient.Type.get,
                httpurl),result -> {

        });
        return new Gson().fromJson(locationResult, TencentIpLocation.class);
    }
    public static GeoLocationForeign getLocationFromForeignIP(String ip){
        String url="http://api.ipstack.com/"+ip+"?access_key="+ CloudConst.ipStackKey;
        String res= RdiHttpClient.sendRequestAsync(url);
        return new Gson().fromJson(res, GeoLocationForeign.class);
    }
}
