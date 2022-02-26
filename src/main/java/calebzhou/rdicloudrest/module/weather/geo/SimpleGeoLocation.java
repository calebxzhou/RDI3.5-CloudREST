package calebzhou.rdicloudrest.module.weather.geo;

public class SimpleGeoLocation {
    String country;
    String province;
    String city;
    String district;

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
