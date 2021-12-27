package calebzhou.rdicloudrest.model;

public class Weather {
    public Result result;

    public class Result {
        public Daily daily;

        public class Daily {
            public Skycon[] skycon;
            public Temperature[] temperature;
            public Precipitation[] precipitation;
            public Humidity[] humidity;
            public Wind[] wind;

            public class Skycon //数组[0]是当天
            {
                public String date;
                public String value;
            }

            public class Temperature //数组[0]是当天
            {
                public String date;
                public double max;
                public double min;
            }

            public class Precipitation//数组[0]是当天
            {
                public String date;
                public double max;
                public double min;
            }

            public class Pressure//数组[0]是当天
            {
                public String date;
                public double max;
                public double min;
            }

            public class Humidity {
                public String date;
                public double max;
                public double min;
            }

            public class Wind//数组[0]是当天
            {
                public String date;
                public Max max;
                public Min min;

                public class Max {
                    public double direction;
                    public double speed;
                }

                public class Min {
                    public double direction;
                    public double speed;
                }
            }
        }
    }
}
