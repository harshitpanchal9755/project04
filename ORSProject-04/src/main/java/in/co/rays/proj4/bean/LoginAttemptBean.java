package in.co.rays.proj4.bean;

import java.util.Date;

public class LoginAttemptBean {

	private long id;
	private String attemptcode;
	private String username;
	private Date attempttime;
	private String status;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAttemptcode() {
		return attemptcode;
	}

	public void setAttemptcode(String attemptcode) {
		this.attemptcode = attemptcode;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getAttempttime() {
		return attempttime;
	}

	public void setAttempttime(Date attempttime) {
		this.attempttime = attempttime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
