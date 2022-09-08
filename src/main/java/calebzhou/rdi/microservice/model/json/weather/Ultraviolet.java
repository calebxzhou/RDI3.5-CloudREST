
package calebzhou.rdi.microservice.model.json.weather;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Ultraviolet {

    @SerializedName("index")
    @Expose
    private Double index;
    @SerializedName("desc")
    @Expose
    private String desc;

    public Double getIndex() {
        return index;
    }

    public void setIndex(Double index) {
        this.index = index;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
