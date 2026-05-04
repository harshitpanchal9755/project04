package in.co.rays.proj4.bean;

import in.co.rays.proj4.controller.BaseCtl;
import in.co.rays.proj4.controller.ORSView;

public class DataImportLogBean {
	private long id;
	private String importlogcode;
	private String filename;
	private String importedby;
	private String status;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getImportlogcode() {
		return importlogcode;
	}

	public void setImportlogcode(String importlogcode) {
		this.importlogcode = importlogcode;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getImportedby() {
		return importedby;
	}

	public void setImportedby(String importedby) {
		this.importedby = importedby;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
