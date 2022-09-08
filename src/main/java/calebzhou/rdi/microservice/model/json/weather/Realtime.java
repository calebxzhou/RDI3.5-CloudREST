
package calebzhou.rdi.microservice.model.json.weather;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Realtime {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("temperature")
    @Expose
    private Double temperature;
    @SerializedName("humidity")
    @Expose
    private Double humidity;
    @SerializedName("cloudrate")
    @Expose
    private Double cloudrate;
    @SerializedName("skycon")
    @Expose
    private String skycon;
    @SerializedName("visibility")
    @Expose
    private Double visibility;
    @SerializedName("dswrf")
    @Expose
    private Double dswrf;
    @SerializedName("wind")
    @Expose
    private Wind wind;
    @SerializedName("pressure")
    @Expose
    private Double pressure;
    @SerializedName("apparent_temperature")
    @Expose
    private Double apparentTemperature;
    @SerializedName("precipitation")
    @Expose
    private Precipitation precipitation;
    @SerializedName("air_quality")
    @Expose
    private AirQuality airQuality;
    @SerializedName("life_index")
    @Expose
    private LifeIndex lifeIndex;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    public Double getCloudrate() {
        return cloudrate;
    }

    public void setCloudrate(Double cloudrate) {
        this.cloudrate = cloudrate;
    }

    public String getSkycon() {
        return skycon;
    }

    public void setSkycon(String skycon) {
        this.skycon = skycon;
    }

    public Double getVisibility() {
        return visibility;
    }

    public void setVisibility(Double visibility) {
        this.visibility = visibility;
    }

    public Double getDswrf() {
        return dswrf;
    }

    public void setDswrf(Double dswrf) {
        this.dswrf = dswrf;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Double getPressure() {
        return pressure;
    }

    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }

    public Double getApparentTemperature() {
        return apparentTemperature;
    }

    public void setApparentTemperature(Double apparentTemperature) {
        this.apparentTemperature = apparentTemperature;
    }

    public Precipitation getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(Precipitation precipitation) {
        this.precipitation = precipitation;
    }

    public AirQuality getAirQuality() {
        return airQuality;
    }

    public void setAirQuality(AirQuality airQuality) {
        this.airQuality = airQuality;
    }

    public LifeIndex getLifeIndex() {
        return lifeIndex;
    }

    public void setLifeIndex(LifeIndex lifeIndex) {
        this.lifeIndex = lifeIndex;
    }

}
