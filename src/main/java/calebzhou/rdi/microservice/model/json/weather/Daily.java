
package calebzhou.rdi.microservice.model.json.weather;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Daily {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("astro")
    @Expose
    private List<Astro> astro = null;
    @SerializedName("precipitation")
    @Expose
    private List<Precipitation__2> precipitation = null;
    @SerializedName("temperature")
    @Expose
    private List<Temperature__1> temperature = null;
    @SerializedName("wind")
    @Expose
    private List<Wind__2> wind = null;
    @SerializedName("humidity")
    @Expose
    private List<Humidity__1> humidity = null;
    @SerializedName("cloudrate")
    @Expose
    private List<Cloudrate__1> cloudrate = null;
    @SerializedName("pressure")
    @Expose
    private List<Pressure__1> pressure = null;
    @SerializedName("visibility")
    @Expose
    private List<Visibility__1> visibility = null;
    @SerializedName("dswrf")
    @Expose
    private List<Dswrf__1> dswrf = null;
    @SerializedName("air_quality")
    @Expose
    private AirQuality__2 airQuality;
    @SerializedName("skycon")
    @Expose
    private List<Skycon__1> skycon = null;
    @SerializedName("skycon_08h_20h")
    @Expose
    private List<Skycon08h20h> skycon08h20h = null;
    @SerializedName("skycon_20h_32h")
    @Expose
    private List<Skycon20h32h> skycon20h32h = null;
    @SerializedName("life_index")
    @Expose
    private LifeIndex__1 lifeIndex;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Astro> getAstro() {
        return astro;
    }

    public void setAstro(List<Astro> astro) {
        this.astro = astro;
    }

    public List<Precipitation__2> getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(List<Precipitation__2> precipitation) {
        this.precipitation = precipitation;
    }

    public List<Temperature__1> getTemperature() {
        return temperature;
    }

    public void setTemperature(List<Temperature__1> temperature) {
        this.temperature = temperature;
    }

    public List<Wind__2> getWind() {
        return wind;
    }

    public void setWind(List<Wind__2> wind) {
        this.wind = wind;
    }

    public List<Humidity__1> getHumidity() {
        return humidity;
    }

    public void setHumidity(List<Humidity__1> humidity) {
        this.humidity = humidity;
    }

    public List<Cloudrate__1> getCloudrate() {
        return cloudrate;
    }

    public void setCloudrate(List<Cloudrate__1> cloudrate) {
        this.cloudrate = cloudrate;
    }

    public List<Pressure__1> getPressure() {
        return pressure;
    }

    public void setPressure(List<Pressure__1> pressure) {
        this.pressure = pressure;
    }

    public List<Visibility__1> getVisibility() {
        return visibility;
    }

    public void setVisibility(List<Visibility__1> visibility) {
        this.visibility = visibility;
    }

    public List<Dswrf__1> getDswrf() {
        return dswrf;
    }

    public void setDswrf(List<Dswrf__1> dswrf) {
        this.dswrf = dswrf;
    }

    public AirQuality__2 getAirQuality() {
        return airQuality;
    }

    public void setAirQuality(AirQuality__2 airQuality) {
        this.airQuality = airQuality;
    }

    public List<Skycon__1> getSkycon() {
        return skycon;
    }

    public void setSkycon(List<Skycon__1> skycon) {
        this.skycon = skycon;
    }

    public List<Skycon08h20h> getSkycon08h20h() {
        return skycon08h20h;
    }

    public void setSkycon08h20h(List<Skycon08h20h> skycon08h20h) {
        this.skycon08h20h = skycon08h20h;
    }

    public List<Skycon20h32h> getSkycon20h32h() {
        return skycon20h32h;
    }

    public void setSkycon20h32h(List<Skycon20h32h> skycon20h32h) {
        this.skycon20h32h = skycon20h32h;
    }

    public LifeIndex__1 getLifeIndex() {
        return lifeIndex;
    }

    public void setLifeIndex(LifeIndex__1 lifeIndex) {
        this.lifeIndex = lifeIndex;
    }

}
