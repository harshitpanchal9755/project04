package in.co.rays.proj4.bean;

import java.util.Date;

public class StudentBean extends BaseBean {

	private String firstname;
	private String lastname;
	private Date dob;
	private String gender;
	private String mobileno;
	private String email;
	private long collegeid;
	private String collegename;

	public String getFirstName() {
		return firstname;
	}

	public void setFirstName(String firstname) {
		this.firstname = firstname;
	}

	public String getLastName() {
		return lastname;
	}

	public void setLastName(String lastname) {
		this.lastname = lastname;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getMobileno() {
		return mobileno;
	}

	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getCollegeId() {
		return collegeid;
	}

	public void setCollegeId(long collegeid) {
		this.collegeid = collegeid;
	}

	public String getCollegeName() {
		return collegename;
	}

	public void setCollegeName(String collegename) {
		this.collegename = collegename;
	}

}
