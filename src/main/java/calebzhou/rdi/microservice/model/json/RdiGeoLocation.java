package calebzhou.rdi.microservice.model.json;


import calebzhou.rdi.microservice.utils.RdiSerializer;
import lombok.AllArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
public class RdiGeoLocation implements Serializable {
    public String nation;
    public String province;
    public String city;
    public String district;
    public String isp;
    public GeoLocation location;

    @Override
    public String toString() {
        return RdiSerializer.GSON.toJson(this);
    }
}
