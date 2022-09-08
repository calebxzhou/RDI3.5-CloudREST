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
        StringBuilder alert = new StringBuilder();
        Result result = caiyunWeather.getResult();
        for (Content content : result.getAlert().getContent()) {
            alert
                    .append(content.getSource())
                    .append(content.getTitle())
                    .append(content.getDescription());
        }
        Realtime realtime = result.getRealtime();
        double temperature = realtime.getTemperature();
        double humidity = realtime.getHumidity();
        String skycon = realtime.getSkycon();
        double visibility = realtime.getVisibility();
        double windSpeed = realtime.getWind().getSpeed();
        double windDirection = realtime.getWind().getDirection();
        double pressure = realtime.getPressure();
        int aqi = realtime.getAirQuality().getAqi().getChn();
        Minutely minutely = result.getMinutely();
        double rainStrength = minutely.getPrecipitation().get(0);
        double rainProba = minutely.getProbability().get(0);
        String rainDescr = minutely.getDescription();
        String hourlyDescr = result.getHourly().getDescription();
        Daily daily = result.getDaily();
        Astro astro = daily.getAstro().get(0);
        String sunrise = astro.getSunrise().getTime();
        String sunset = astro.getSunset().getTime();


        return new RdiWeather(
                alert.toString(),
                temperature,
                humidity,
                skycon,
                visibility,
                windSpeed,
                windDirection,pressure,aqi,rainStrength,rainProba,rainDescr,hourlyDescr,
                sunrise,
                sunset,
                getTomorrowWeather(daily));
    }
    public String alert;
    public double temperature;
    public double humidity;
    public String skycon;
    public double visibility;
    public double windSpeed;
    public double windDirection;
    public double pressure;
    public int aqi;
    public double rainStrength;
    public double rainProba;
    public String rainDescr;
    public String hourlyDescr;
    public String sunRiseTime;
    public String sunSetTime;
    public RdiTomorrowWeather tomorrow;

}
