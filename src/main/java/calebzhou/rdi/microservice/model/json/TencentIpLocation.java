package calebzhou.rdi.microservice.model.json;

public class TencentIpLocation {
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
    public Result result;

    public static class Result {
        public Location location;

        public static class Location {
            public float lng;
            public float lat;
        }

        public Ad_info ad_info;

        public static class Ad_info {
            public String nation;
            public String province;
            public String city;
            public String district;
        }

    }
}
