package calebzhou.rdi.microservice.model.geo;

public class GeoLocationForeign {
    public String ip;
    public String type;
    public String continent_code;
    public String continent_name;
    public String country_code;
    public String country_name;
    public String region_code;
    public String region_name;
    public String city;
    public String zip;
    public double latitude;
    public double longitude;
    public Location location;
    public Connection connection;

    public class Location {
        public int geoname_id;
        public String capital;
        public Language languages[];
        public String country_flag;
        public String country_flag_emoji;
        public String country_flag_emoji_unicode;
        public String calling_code;
        public boolean is_eu;

        public class Language {
            public String code;
            public String name;
            public String _native;
        }
    }
    public class Connection{
        public String isp;
        public int asn;
    }
}