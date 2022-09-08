
package calebzhou.rdi.microservice.model.json.weather;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class CaiyunWeather {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("api_version")
    @Expose
    private String apiVersion;
    @SerializedName("api_status")
    @Expose
    private String apiStatus;
    @SerializedName("lang")
    @Expose
    private String lang;
    @SerializedName("unit")
    @Expose
    private String unit;
    @SerializedName("tzshift")
    @Expose
    private Integer tzshift;
    @SerializedName("timezone")
    @Expose
    private String timezone;
    @SerializedName("server_time")
    @Expose
    private Integer serverTime;
    @SerializedName("location")
    @Expose
    private List<Double> location = null;
    @SerializedName("result")
    @Expose
    private Result result;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    public String getApiStatus() {
        return apiStatus;
    }

    public void setApiStatus(String apiStatus) {
        this.apiStatus = apiStatus;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getTzshift() {
        return tzshift;
    }

    public void setTzshift(Integer tzshift) {
        this.tzshift = tzshift;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public Integer getServerTime() {
        return serverTime;
    }

    public void setServerTime(Integer serverTime) {
        this.serverTime = serverTime;
    }

    public List<Double> getLocation() {
        return location;
    }

    public void setLocation(List<Double> location) {
        this.location = location;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

}
