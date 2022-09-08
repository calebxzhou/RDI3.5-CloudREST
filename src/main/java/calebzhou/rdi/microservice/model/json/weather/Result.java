
package calebzhou.rdi.microservice.model.json.weather;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Result {

    @SerializedName("alert")
    @Expose
    private Alert alert;
    @SerializedName("realtime")
    @Expose
    private Realtime realtime;
    @SerializedName("minutely")
    @Expose
    private Minutely minutely;
    @SerializedName("hourly")
    @Expose
    private Hourly hourly;
    @SerializedName("daily")
    @Expose
    private Daily daily;
    @SerializedName("primary")
    @Expose
    private Integer primary;
    @SerializedName("forecast_keypoint")
    @Expose
    private String forecastKeypoint;

    public Alert getAlert() {
        return alert;
    }

    public void setAlert(Alert alert) {
        this.alert = alert;
    }

    public Realtime getRealtime() {
        return realtime;
    }

    public void setRealtime(Realtime realtime) {
        this.realtime = realtime;
    }

    public Minutely getMinutely() {
        return minutely;
    }

    public void setMinutely(Minutely minutely) {
        this.minutely = minutely;
    }

    public Hourly getHourly() {
        return hourly;
    }

    public void setHourly(Hourly hourly) {
        this.hourly = hourly;
    }

    public Daily getDaily() {
        return daily;
    }

    public void setDaily(Daily daily) {
        this.daily = daily;
    }

    public Integer getPrimary() {
        return primary;
    }

    public void setPrimary(Integer primary) {
        this.primary = primary;
    }

    public String getForecastKeypoint() {
        return forecastKeypoint;
    }

    public void setForecastKeypoint(String forecastKeypoint) {
        this.forecastKeypoint = forecastKeypoint;
    }

}
