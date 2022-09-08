
package calebzhou.rdi.microservice.model.json.weather;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class CarWashing {

    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("index")
    @Expose
    private String index;
    @SerializedName("desc")
    @Expose
    private String desc;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
