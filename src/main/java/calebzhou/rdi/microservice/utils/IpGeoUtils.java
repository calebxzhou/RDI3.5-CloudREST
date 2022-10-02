package calebzhou.rdi.microservice.utils;

import calebzhou.rdi.microservice.App;
import calebzhou.rdi.microservice.model.Ip2RegionData;
import com.ip2location.IP2Location;
import com.ip2location.IPResult;
import org.lionsoul.ip2region.xdb.Searcher;

import java.io.IOException;

public class IpGeoUtils {
    public static final Searcher ip2RegionSearcher;
    public static final IP2Location ip2LocationSearcher;

    static {
        try {
            ip2RegionSearcher = Searcher.newWithBuffer(App.class.getResourceAsStream("/ip2region.xdb").readAllBytes());
            IP2Location i2 = new IP2Location();
            i2.Open(App.class.getResourceAsStream("/ip2location_db.bin").readAllBytes());
            ip2LocationSearcher = i2;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static Ip2RegionData searchIp2Region(String ip){
        try {
            String searchedString = ip2RegionSearcher.search(ip);
            String[] split = searchedString.split("\\|");
            return new Ip2RegionData(split[0],split[2],split[3],split[4]);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static IPResult searchIp2Location(String ip){
        IPResult ipResult;
        try {
            ipResult = ip2LocationSearcher.IPQuery(ip);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ipResult;

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
