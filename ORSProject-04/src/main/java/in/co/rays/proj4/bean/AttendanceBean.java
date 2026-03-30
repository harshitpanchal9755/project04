package in.co.rays.proj4.bean;

import java.util.Date;

public class AttendanceBean {

	private long id;
	private String code;
	private String employeename;
	private Date attendancedate;
	private String status;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getEmployeename() {
		return employeename;
	}

	public void setEmployeename(String employeename) {
		this.employeename = employeename;
	}

	public Date getAttendanceDate() {
		return attendancedate;
	}

	public void setDate(Date attendancedate) {
		this.attendancedate = attendancedate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
