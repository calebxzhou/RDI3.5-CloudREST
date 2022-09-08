
package calebzhou.rdi.microservice.model.json.weather;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class LifeIndex__1 {

    @SerializedName("ultraviolet")
    @Expose
    private List<Ultraviolet__1> ultraviolet = null;
    @SerializedName("carWashing")
    @Expose
    private List<CarWashing> carWashing = null;
    @SerializedName("dressing")
    @Expose
    private List<Dressing> dressing = null;
    @SerializedName("comfort")
    @Expose
    private List<Comfort__1> comfort = null;
    @SerializedName("coldRisk")
    @Expose
    private List<ColdRisk> coldRisk = null;

    public List<Ultraviolet__1> getUltraviolet() {
        return ultraviolet;
    }

    public void setUltraviolet(List<Ultraviolet__1> ultraviolet) {
        this.ultraviolet = ultraviolet;
    }

    public List<CarWashing> getCarWashing() {
        return carWashing;
    }

    public void setCarWashing(List<CarWashing> carWashing) {
        this.carWashing = carWashing;
    }

    public List<Dressing> getDressing() {
        return dressing;
    }

    public void setDressing(List<Dressing> dressing) {
        this.dressing = dressing;
    }

    public List<Comfort__1> getComfort() {
        return comfort;
    }

    public void setComfort(List<Comfort__1> comfort) {
        this.comfort = comfort;
    }

    public List<ColdRisk> getColdRisk() {
        return coldRisk;
    }

    public void setColdRisk(List<ColdRisk> coldRisk) {
        this.coldRisk = coldRisk;
    }

}
