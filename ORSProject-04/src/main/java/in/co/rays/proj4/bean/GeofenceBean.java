package in.co.rays.proj4.bean;

public class GeofenceBean extends BaseBean {

	private long id;
	private String geoFenceCode;
	private String locationName;
	private String radius;
	private String status;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getGeoFenceCode() {
		return geoFenceCode;
	}

	public void setGeoFenceCode(String geoFenceCode) {
		this.geoFenceCode = geoFenceCode;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getRadius() {
		return radius;
	}

	public void setRadius(String radius) {
		this.radius = radius;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return geoFenceCode;
	}
}
