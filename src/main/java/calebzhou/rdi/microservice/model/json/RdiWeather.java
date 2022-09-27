package calebzhou.rdi.microservice.model.json;

import calebzhou.rdi.microservice.model.json.weather.*;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RdiWeather {
    public static RdiTomorrowWeather getTomorrowWeather(Daily daily){
        String skycon = daily.getSkycon().get(1).getValue();
        int aqi = daily.getAirQuality().getAqi().get(1).getMax().getChn();
        Max wind = daily.getWind().get(1).getMax();
        double windSpeed = wind.getSpeed();
        double windDirection = wind.getDirection();
        double tempLow = daily.getTemperature().get(1).getMin();
        double tempHigh = daily.getTemperature().get(1).getMax();
        double rain = daily.getPrecipitation().get(1).getMax();
        double humid = daily.getHumidity().get(1).getMax();
        return new RdiTomorrowWeather(skycon,tempLow,tempHigh,windSpeed,windDirection,humid,rain,aqi);
    }
    public static RdiWeather fromCaiyunWeather(CaiyunWeather caiyunWeather){
        StringBuilder alertString = new StringBuilder();
        Result result = caiyunWeather.getResult();
        for (Content content : result.getAlert().getContent()) {
            alertString
                    .append(content.getSource()).append(" ").append(content.getStatus()).append("\n")
                    .append(content.getLocation()).append("：").append(content.getTitle()).append("\n")
                    .append(content.getDescription())
                    .append("\n============\n");
        }
        Realtime realtime = result.getRealtime();
        double temperature = realtime.getTemperature();
        double humidity = realtime.getHumidity();
        String skycon = realtime.getSkycon();
        double visibility = realtime.getVisibility();
        double windSpeed = realtime.getWind().getSpeed();
        double windDirection = realtime.getWind().getDirection();
        //double pressure = realtime.getPressure();
        int aqiValue = realtime.getAirQuality().getAqi().getChn();
        String aqiDescription = realtime.getAirQuality().getDescription().getChn();
        Minutely minutely = result.getMinutely();
        double rainProba = minutely.getProbability().get(0);
        String minuteRainDescription = minutely.getDescription();
        String hourlyDescription = result.getHourly().getDescription();
        String sunrise = result.getDaily().getAstro().get(0).getSunrise().getTime();
        String sunset = result.getDaily().getAstro().get(0).getSunset().getTime();


        return new RdiWeather(
                alertString.toString(),
                temperature,
                humidity,
                skycon,
                visibility,
                windSpeed,
                windDirection,
                aqiValue,
                aqiDescription,
                rainProba,
                minuteRainDescription,
                hourlyDescription,
                sunrise,
                sunset/*,
                getTomorrowWeather(daily)*/);
    }
    public String alert;
    public double temperature;
    public double humidity;
    public String skycon;
    public double visibility;
    public double windSpeed;
    public double windDirection;
    public int aqiValue;
    public String aqiDescription;
    public double rainProba;
    public String minuteRainDescription;
    public String hourlyDescr;
    public String sunRiseTime;
    public String sunSetTime;
   // public RdiTomorrowWeather tomorrow;

}
