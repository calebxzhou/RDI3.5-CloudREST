package calebzhou.rdicloudrest.utils;

import calebzhou.rdicloudrest.App;
import calebzhou.rdicloudrest.constants.CloudServiceConstants;
import calebzhou.rdicloudrest.module.weather.geo.GeoLocation;
import calebzhou.rdicloudrest.module.weather.geo.GeoLocationForeign;
import calebzhou.rdicloudrest.module.weather.geo.SimpleGeoLocation;
import com.google.gson.Gson;

public class GeographyUtils {
    public static SimpleGeoLocation getSimpleGeoLocation(String ip){
        if(App.DEBUG)
            ip="42.177.210.38";
        SimpleGeoLocation geoLocation = new SimpleGeoLocation();
        GeoLocation location = getGeoLocationFromIP(ip);
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
    public static GeoLocation getGeoLocationFromIP(String ip) {
        String httpurl = "https://apis.map.qq.com/ws/location/v1/ip?ip=" + ip + "&key=" + CloudServiceConstants.tencentLbsKey;
        String locationResult = HttpUtils.doGet(httpurl);
        return new Gson().fromJson(locationResult, GeoLocation.class);
    }
    public static GeoLocationForeign getLocationFromForeignIP(String ip){
        String url="http://api.ipstack.com/"+ip+"?access_key="+CloudServiceConstants.ipStackKey;
        String res=HttpUtils.doGet(url);
        return new Gson().fromJson(res, GeoLocationForeign.class);
    }
}
