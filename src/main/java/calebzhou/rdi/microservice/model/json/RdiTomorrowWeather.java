package calebzhou.rdi.microservice.model.json;


import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RdiTomorrowWeather {
    public String skycon;
    public double tempLow;
    public double tempHigh;
    public double windSpeed;
    public double windDirection;
    public double humid;
    public double rain;
    public int aqi;

}
