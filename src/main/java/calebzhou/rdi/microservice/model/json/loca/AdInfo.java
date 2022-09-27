
package calebzhou.rdi.microservice.model.json.loca;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class AdInfo {

    @SerializedName("adcode")
    @Expose
    private String adcode;

    public String getAdcode() {
        return adcode;
    }

    public void setAdcode(String adcode) {
        this.adcode = adcode;
    }

}
