package calebzhou.rdicloudrest.utils;

import calebzhou.rdicloudrest.constants.CloudServiceConstants;
import calebzhou.rdicloudrest.model.IP2Location;
import com.google.gson.Gson;

public class GeographyUtils {
    public static IP2Location getGeoLocationFromIP(String ip) {
        String httpurl = "https://apis.map.qq.com/ws/location/v1/ip?ip=" + ip + "&key=" + CloudServiceConstants.tencentLbsKey;
        String locationResult = HttpUtils.doGet(httpurl);
        return new Gson().fromJson(locationResult, IP2Location.class);
    }
}
