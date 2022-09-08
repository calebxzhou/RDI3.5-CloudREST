
package calebzhou.rdi.microservice.model.json.weather;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Minutely {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("datasource")
    @Expose
    private String datasource;
    @SerializedName("precipitation_2h")
    @Expose
    private List<Double> precipitation2h = null;
    @SerializedName("precipitation")
    @Expose
    private List<Double> precipitation = null;
    @SerializedName("probability")
    @Expose
    private List<Double> probability = null;
    @SerializedName("description")
    @Expose
    private String description;

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

    public List<Double> getPrecipitation2h() {
        return precipitation2h;
    }

    public void setPrecipitation2h(List<Double> precipitation2h) {
        this.precipitation2h = precipitation2h;
    }

    public List<Double> getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(List<Double> precipitation) {
        this.precipitation = precipitation;
    }

    public List<Double> getProbability() {
        return probability;
    }

    public void setProbability(List<Double> probability) {
        this.probability = probability;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
