package calebzhou.rdi.microservice.model.weather;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class SimpleGeoLocation {
    String country;
    String province;
    String city;
    String district;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE)
                .append("country", country)
                .append("province", province)
                .append("city", city)
                .append("district", district)
                .toString();
    }

    public String getCountry() {
        return country;
    }

    public SimpleGeoLocation setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getProvince() {
        return province;
    }

    public SimpleGeoLocation setProvince(String province) {
        this.province = province;
        return this;
    }

    public String getCity() {
        return city;
    }

    public SimpleGeoLocation setCity(String city) {
        this.city = city;
        return this;
    }

    public String getDistrict() {
        return district;
    }

    public SimpleGeoLocation setDistrict(String district) {
        this.district = district;
        return this;
    }
}
