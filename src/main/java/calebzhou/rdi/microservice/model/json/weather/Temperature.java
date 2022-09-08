
package calebzhou.rdi.microservice.model.json.weather;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Temperature {

    @SerializedName("datetime")
    @Expose
    private String datetime;
    @SerializedName("value")
    @Expose
    private Double value;

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

}
