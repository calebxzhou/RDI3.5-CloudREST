
package calebzhou.rdi.microservice.model.json.loca;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Result {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("location")
    @Expose
    private Location location;
    @SerializedName("ad_info")
    @Expose
    private AdInfo adInfo;
    @SerializedName("address_components")
    @Expose
    private AddressComponents addressComponents;
    @SerializedName("similarity")
    @Expose
    private Double similarity;
    @SerializedName("deviation")
    @Expose
    private Integer deviation;
    @SerializedName("reliability")
    @Expose
    private Integer reliability;
    @SerializedName("level")
    @Expose
    private Integer level;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public AdInfo getAdInfo() {
        return adInfo;
    }

    public void setAdInfo(AdInfo adInfo) {
        this.adInfo = adInfo;
    }

    public AddressComponents getAddressComponents() {
        return addressComponents;
    }

    public void setAddressComponents(AddressComponents addressComponents) {
        this.addressComponents = addressComponents;
    }

    public Double getSimilarity() {
        return similarity;
    }

    public void setSimilarity(Double similarity) {
        this.similarity = similarity;
    }

    public Integer getDeviation() {
        return deviation;
    }

    public void setDeviation(Integer deviation) {
        this.deviation = deviation;
    }

    public Integer getReliability() {
        return reliability;
    }

    public void setReliability(Integer reliability) {
        this.reliability = reliability;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

}
