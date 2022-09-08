
package calebzhou.rdi.microservice.model.json.weather;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class AirQuality {

    @SerializedName("pm25")
    @Expose
    private double pm25;
    @SerializedName("pm10")
    @Expose
    private double pm10;
    @SerializedName("o3")
    @Expose
    private double o3;
    @SerializedName("so2")
    @Expose
    private double so2;
    @SerializedName("no2")
    @Expose
    private double no2;
    @SerializedName("co")
    @Expose
    private double co;
    @SerializedName("aqi")
    @Expose
    private Aqi aqi;
    @SerializedName("description")
    @Expose
    private Description description;

    public double getPm25() {
        return pm25;
    }

    public void setPm25(double pm25) {
        this.pm25 = pm25;
    }

    public double getPm10() {
        return pm10;
    }

    public void setPm10(double pm10) {
        this.pm10 = pm10;
    }

    public double getO3() {
        return o3;
    }

    public void setO3(double o3) {
        this.o3 = o3;
    }

    public double getSo2() {
        return so2;
    }

    public void setSo2(double so2) {
        this.so2 = so2;
    }

    public double getNo2() {
        return no2;
    }

    public void setNo2(double no2) {
        this.no2 = no2;
    }

    public double getCo() {
        return co;
    }

    public void setCo(double co) {
        this.co = co;
    }

    public Aqi getAqi() {
        return aqi;
    }

    public void setAqi(Aqi aqi) {
        this.aqi = aqi;
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

}
