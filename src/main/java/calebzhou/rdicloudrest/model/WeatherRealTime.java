package calebzhou.rdicloudrest.model;

public class WeatherRealTime {
    public String status;
    public String api_version;
    public String api_status;
    public String lang;
    public String unit;
    public long tzshift;
    public String timezone;
    public long server_time;
    public double[] location;
    public Result result;



    public static class Result {
        public Alert alert;
        public Realtime realtime;
        public long primary;


        public static class Alert {
            public String status;
            public Content content[];


            public static class Content {
                public String province;
                public String status;
                public String code;
                public String description;
                public long pubtimestamp;
                public String city;
                public String adcode;
                public String regionId;
                public double[] latlon;
                public String county;
                public String alertId;
                public String request_status;
                public String source;
                public String title;
                public String location;


            }
        }

        public static class Realtime {
            public String status;
            public float temperature;
            public float humidity;
            public float cloudrate;
            public String skycon;
            public double visibility;
            public double dswrf;
            public Wind wind;
            public float pressure;
            public float apparent_temperature;
            public Precipitation precipitation;
            public Air_quality air_quality;
            public Life_index life_index;



            public static class Wind {
                public float speed;
                public float direction;


            }

            public static class Precipitation {
                public Local local;
                public Nearest nearest;



                public static class Local {
                    public String status;
                    public String datasource;
                    public double intensity;


                }

                public static class Nearest {
                    public String status;
                    public double distance;
                    public double intensity;

                }
            }

            public static class Air_quality {
                public float pm25;
                public float pm10;
                public float o3;
                public float so2;
                public float no2;
                public float co;
                public Aqi aqi;
                public Description description;



                public static class Aqi {
                    public float chn;
                    public float usa;

                }

                public static class Description {
                    public String chn;
                    public String usa;

                }
            }

            public static class Life_index {
                public Ultraviolet ultraviolet;
                public Comfort comfort;



                public static class Ultraviolet {
                    public float index;
                    public String desc;


                }

                public static class Comfort {
                    public float index;
                    public String desc;


                }
            }
        }
    }
}