package calebzhou.rdi.microservice.utils;

import calebzhou.rdi.microservice.App;
import calebzhou.rdi.microservice.model.Ip2RegionData;
import org.lionsoul.ip2region.xdb.Searcher;

import java.io.IOException;

public class IpRegionUtils {
    public static final Searcher ipSearcher;

    static {
        try {
            ipSearcher = Searcher.newWithBuffer(App.class.getResourceAsStream("/ip2region.xdb").readAllBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static Ip2RegionData searchRegionByIp(String ip){
        try {
            String searchedString = ipSearcher.search(ip);
            String[] split = searchedString.split("\\|");
            return new Ip2RegionData(split[0],split[2],split[3],split[4]);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

   /* public static SimpleGeoLocation getSimpleGeoLocation(String ip){
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
    }*/
}
