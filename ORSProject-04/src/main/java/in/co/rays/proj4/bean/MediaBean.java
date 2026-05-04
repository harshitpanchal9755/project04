package in.co.rays.proj4.bean;

public class MediaBean {
	private long id;
	private String mediacode;
	private String mediatype;
	private String uri;
	private String status;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMediacode() {
		return mediacode;
	}

	public void setMediacode(String mediacode) {
		this.mediacode = mediacode;
	}

	public String getMediatype() {
		return mediatype;
	}

	public void setMediatype(String mediatype) {
		this.mediatype = mediatype;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}