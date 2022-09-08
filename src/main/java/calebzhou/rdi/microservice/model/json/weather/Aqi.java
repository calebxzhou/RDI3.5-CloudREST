
package calebzhou.rdi.microservice.model.json.weather;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Aqi {

    @SerializedName("chn")
    @Expose
    private Integer chn;
    @SerializedName("usa")
    @Expose
    private Integer usa;

    public Integer getChn() {
        return chn;
    }

    public void setChn(Integer chn) {
        this.chn = chn;
    }

    public Integer getUsa() {
        return usa;
    }

    public void setUsa(Integer usa) {
        this.usa = usa;
    }

}
