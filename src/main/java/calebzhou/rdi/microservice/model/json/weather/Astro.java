
package calebzhou.rdi.microservice.model.json.weather;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Astro {

    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("sunrise")
    @Expose
    private Sunrise sunrise;
    @SerializedName("sunset")
    @Expose
    private Sunset sunset;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Sunrise getSunrise() {
        return sunrise;
    }

    public void setSunrise(Sunrise sunrise) {
        this.sunrise = sunrise;
    }

    public Sunset getSunset() {
        return sunset;
    }

    public void setSunset(Sunset sunset) {
        this.sunset = sunset;
    }

}
