
package calebzhou.rdi.microservice.model.json.weather;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class LifeIndex {

    @SerializedName("ultraviolet")
    @Expose
    private Ultraviolet ultraviolet;
    @SerializedName("comfort")
    @Expose
    private Comfort comfort;

    public Ultraviolet getUltraviolet() {
        return ultraviolet;
    }

    public void setUltraviolet(Ultraviolet ultraviolet) {
        this.ultraviolet = ultraviolet;
    }

    public Comfort getComfort() {
        return comfort;
    }

    public void setComfort(Comfort comfort) {
        this.comfort = comfort;
    }

}
