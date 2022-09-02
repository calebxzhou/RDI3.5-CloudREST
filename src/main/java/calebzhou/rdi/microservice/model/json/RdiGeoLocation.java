package calebzhou.rdi.microservice.model.json;


import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RdiGeoLocation {
    public String nation;
    public String province;
    public String city;
    public String district;
    public String isp;
    public float latitude;
    public float longitude;
}
