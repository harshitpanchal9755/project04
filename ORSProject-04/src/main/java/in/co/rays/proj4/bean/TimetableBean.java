package in.co.rays.proj4.bean;

import java.util.Date;

public class TimetableBean extends BaseBean {

	private String semester;

	private String description;

	private Date examdate;

	private String examtime;

	private long courseid;

	private String coursename;

	private long subjectid;

	private String subjectname;
	
	

	public String getSemester() {
		return semester;
	}



	public void setSemester(String semester) {
		this.semester = semester;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public Date getExamDate() {
		return examdate;
	}



	public void setExamDate(Date examdate) {
		this.examdate = examdate;
	}



	public String getExamTime() {
		return examtime;
	}



	public void setExamTime(String examtime) {
		this.examtime = examtime;
	}



	public long getCourseId() {
		return courseid;
	}



	public void setCourseId(long courseid) {
		this.courseid = courseid;
	}



	public String getCourseName() {
		return coursename;
	}



	public void setCourseName(String coursename) {
		this.coursename = coursename;
	}



	public long getSubjectId() {
		return subjectid;
	}



	public void setSubjectId(long subjectid) {
		this.subjectid = subjectid;
	}



	public String getSubjectName() {
		return subjectname;
	}



	public void setSubjectName(String subjectname) {
		this.subjectname = subjectname;
	}



	public String getValue() {
		// TODO Auto-generated method stub
		return coursename;
	}



}