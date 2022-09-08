
package calebzhou.rdi.microservice.model.json.weather;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Wind__1 {

    @SerializedName("datetime")
    @Expose
    private String datetime;
    @SerializedName("speed")
    @Expose
    private Double speed;
    @SerializedName("direction")
    @Expose
    private Double direction;

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public Double getDirection() {
        return direction;
    }

    public void setDirection(Double direction) {
        this.direction = direction;
    }

}
