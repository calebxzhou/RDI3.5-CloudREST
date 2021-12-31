package calebzhou.rdicloudrest.model.geo;

public class GeoLocation {
    /*{
    "status": 0,
    "message": "query ok",
    "result": {
        "ip": "42.7.103.228",
        "location": {
            "lat": 40.73545,
            "lng": 120.89393
        },
        "ad_info": {
            "nation": "中国",
            "province": "辽宁省",
            "city": "葫芦岛市",
            "district": "龙港区",
            "adcode": 211403
        }
    }
}
*/
    public int status;
    public String message;
    public Result result;

    public class Result {
        public String ip;

        public Location location;

        public class Location {
            public double lng;
            public double lat;
        }

        public Ad_info ad_info;

        public class Ad_info {
            public String nation;
            public String province;
            public String city;
            public String district;
            public int adcode;
        }

    }
}
