
package calebzhou.rdi.microservice.model.json.weather;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Wind__2 {

    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("max")
    @Expose
    private Max max;
    @SerializedName("min")
    @Expose
    private Min min;
    @SerializedName("avg")
    @Expose
    private Avg avg;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Max getMax() {
        return max;
    }

    public void setMax(Max max) {
        this.max = max;
    }

    public Min getMin() {
        return min;
    }

    public void setMin(Min min) {
        this.min = min;
    }

    public Avg getAvg() {
        return avg;
    }

    public void setAvg(Avg avg) {
        this.avg = avg;
    }

}
