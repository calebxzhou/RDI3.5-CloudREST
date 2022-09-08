
package calebzhou.rdi.microservice.model.json.weather;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Local {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("datasource")
    @Expose
    private String datasource;
    @SerializedName("intensity")
    @Expose
    private Double intensity;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDatasource() {
        return datasource;
    }

    public void setDatasource(String datasource) {
        this.datasource = datasource;
    }

    public Double getIntensity() {
        return intensity;
    }

    public void setIntensity(Double intensity) {
        this.intensity = intensity;
    }

}
