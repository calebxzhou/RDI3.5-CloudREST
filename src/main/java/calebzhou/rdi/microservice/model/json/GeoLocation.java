package calebzhou.rdi.microservice.model.json;

/**
 * Created by calebzhou on 2022-09-20,16:16.
 */
public class GeoLocation {
	public double latitude;
	public double longitude;

	public GeoLocation(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		GeoLocation that = (GeoLocation) o;
		return that.latitude == this.latitude && that.longitude == this.longitude;
	}

}
