
package calebzhou.rdi.microservice.model.json.weather;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Hourly {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("precipitation")
    @Expose
    private List<Precipitation__1> precipitation = null;
    @SerializedName("temperature")
    @Expose
    private List<Temperature> temperature = null;
    @SerializedName("wind")
    @Expose
    private List<Wind__1> wind = null;
    @SerializedName("humidity")
    @Expose
    private List<Humidity> humidity = null;
    @SerializedName("cloudrate")
    @Expose
    private List<Cloudrate> cloudrate = null;
    @SerializedName("skycon")
    @Expose
    private List<Skycon> skycon = null;
    @SerializedName("pressure")
    @Expose
    private List<Pressure> pressure = null;
    @SerializedName("visibility")
    @Expose
    private List<Visibility> visibility = null;
    @SerializedName("dswrf")
    @Expose
    private List<Dswrf> dswrf = null;
    @SerializedName("air_quality")
    @Expose
    private AirQuality__1 airQuality;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Precipitation__1> getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(List<Precipitation__1> precipitation) {
        this.precipitation = precipitation;
    }

    public List<Temperature> getTemperature() {
        return temperature;
    }

    public void setTemperature(List<Temperature> temperature) {
        this.temperature = temperature;
    }

    public List<Wind__1> getWind() {
        return wind;
    }

    public void setWind(List<Wind__1> wind) {
        this.wind = wind;
    }

    public List<Humidity> getHumidity() {
        return humidity;
    }

    public void setHumidity(List<Humidity> humidity) {
        this.humidity = humidity;
    }

    public List<Cloudrate> getCloudrate() {
        return cloudrate;
    }

    public void setCloudrate(List<Cloudrate> cloudrate) {
        this.cloudrate = cloudrate;
    }

    public List<Skycon> getSkycon() {
        return skycon;
    }

    public void setSkycon(List<Skycon> skycon) {
        this.skycon = skycon;
    }

    public List<Pressure> getPressure() {
        return pressure;
    }

    public void setPressure(List<Pressure> pressure) {
        this.pressure = pressure;
    }

    public List<Visibility> getVisibility() {
        return visibility;
    }

    public void setVisibility(List<Visibility> visibility) {
        this.visibility = visibility;
    }

    public List<Dswrf> getDswrf() {
        return dswrf;
    }

    public void setDswrf(List<Dswrf> dswrf) {
        this.dswrf = dswrf;
    }

    public AirQuality__1 getAirQuality() {
        return airQuality;
    }

    public void setAirQuality(AirQuality__1 airQuality) {
        this.airQuality = airQuality;
    }

}
