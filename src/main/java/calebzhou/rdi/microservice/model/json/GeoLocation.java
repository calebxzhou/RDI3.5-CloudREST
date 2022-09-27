package calebzhou.rdi.microservice.model.json;

import calebzhou.rdi.microservice.utils.RdiSerializer;

import java.io.Serializable;

/**
 * Created by calebzhou on 2022-09-20,16:16.
 */
public class GeoLocation implements Serializable {
	public double latitude;
	public double longitude;

	public GeoLocation(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}
	@Override
	public String toString() {
		return RdiSerializer.GSON.toJson(this);
	}
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		GeoLocation that = (GeoLocation) o;
		return that.latitude == this.latitude && that.longitude == this.longitude;
	}

}
