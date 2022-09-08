
package calebzhou.rdi.microservice.model.json.weather;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Description {

    @SerializedName("usa")
    @Expose
    private String usa;
    @SerializedName("chn")
    @Expose
    private String chn;

    public String getUsa() {
        return usa;
    }

    public void setUsa(String usa) {
        this.usa = usa;
    }

    public String getChn() {
        return chn;
    }

    public void setChn(String chn) {
        this.chn = chn;
    }

}
