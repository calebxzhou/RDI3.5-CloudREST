
package calebzhou.rdi.microservice.model.json.weather;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class AirQuality__2 {

    @SerializedName("aqi")
    @Expose
    private List<Aqi__2> aqi = null;
    @SerializedName("pm25")
    @Expose
    private List<Pm25__1> pm25 = null;

    public List<Aqi__2> getAqi() {
        return aqi;
    }

    public void setAqi(List<Aqi__2> aqi) {
        this.aqi = aqi;
    }

    public List<Pm25__1> getPm25() {
        return pm25;
    }

    public void setPm25(List<Pm25__1> pm25) {
        this.pm25 = pm25;
    }

}
