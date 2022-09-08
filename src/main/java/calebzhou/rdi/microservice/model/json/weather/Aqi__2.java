
package calebzhou.rdi.microservice.model.json.weather;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Aqi__2 {

    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("max")
    @Expose
    private Max__1 max;
    @SerializedName("avg")
    @Expose
    private Avg__1 avg;
    @SerializedName("min")
    @Expose
    private Min__1 min;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Max__1 getMax() {
        return max;
    }

    public void setMax(Max__1 max) {
        this.max = max;
    }

    public Avg__1 getAvg() {
        return avg;
    }

    public void setAvg(Avg__1 avg) {
        this.avg = avg;
    }

    public Min__1 getMin() {
        return min;
    }

    public void setMin(Min__1 min) {
        this.min = min;
    }

}
