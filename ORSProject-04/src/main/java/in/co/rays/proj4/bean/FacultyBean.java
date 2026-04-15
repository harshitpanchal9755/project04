package in.co.rays.proj4.bean;

import java.util.Date;

/**
 * FacultyBean is a JavaBean class that represents a Faculty entity. It is used
 * to handle records of the ST_FACULTY table.
 *
 * This class contains faculty-related attributes such as personal details,
 * contact information, and associated college, course, and subject information.
 *
 * It extends BaseBean to inherit common properties like id, createdBy, etc.
 *
 * @author Harshit Panchal
 */
public class FacultyBean extends BaseBean {

    /** First name of the faculty */
    private String firstname;

    /** Last name of the faculty */
    private String lastname;

    /** Date of birth of the faculty */
    private Date dob;

    /** Gender of the faculty */
    private String gender;

    /** Mobile number of the faculty */
    private String mobileno;

    /** Email address of the faculty */
    private String email;

    /** College ID associated with the faculty */
    private long collegeid;

    /** College name associated with the faculty */
    private String collegename;

    /** Course ID associated with the faculty */
    private long courseid;

    /** Course name associated with the faculty */
    private String coursename;

    /** Subject ID associated with the faculty */
    private long subjectid;

    /** Subject name associated with the faculty */
    private String subjectname;

    /**
     * Returns the first name of the faculty.
     *
     * @return firstname
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * Sets the first name of the faculty.
     *
     * @param firstname the first name to set
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * Returns the last name of the faculty.
     *
     * @return lastname
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * Sets the last name of the faculty.
     *
     * @param lastname the last name to set
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * Returns the date of birth of the faculty.
     *
     * @return dob
     */
    public Date getDob() {
        return dob;
    }

    /**
     * Sets the date of birth of the faculty.
     *
     * @param dob the date of birth to set
     */
    public void setDob(Date dob) {
        this.dob = dob;
    }

    /**
     * Returns the gender of the faculty.
     *
     * @return gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * Sets the gender of the faculty.
     *
     * @param gender the gender to set
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Returns the mobile number of the faculty.
     *
     * @return mobileno
     */
    public String getMobileno() {
        return mobileno;
    }

    /**
     * Sets the mobile number of the faculty.
     *
     * @param mobileno the mobile number to set
     */
    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    /**
     * Returns the email address of the faculty.
     *
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the faculty.
     *
     * @param email the email address to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the college ID associated with the faculty.
     *
     * @return collegeid
     */
    public long getCollegeid() {
        return collegeid;
    }

    /**
     * Sets the college ID associated with the faculty.
     *
     * @param collegeid the college ID to set
     */
    public void setCollegeid(long collegeid) {
        this.collegeid = collegeid;
    }

    /**
     * Returns the college name associated with the faculty.
     *
     * @return collegename
     */
    public String getCollegename() {
        return collegename;
    }

    /**
     * Sets the college name associated with the faculty.
     *
     * @param collegename the college name to set
     */
    public void setCollegename(String collegename) {
        this.collegename = collegename;
    }

    /**
     * Returns the course ID associated with the faculty.
     *
     * @return courseid
     */
    public long getCourseid() {
        return courseid;
    }

    /**
     * Sets the course ID associated with the faculty.
     *
     * @param courseid the course ID to set
     */
    public void setCourseid(long courseid) {
        this.courseid = courseid;
    }

    /**
     * Returns the course name associated with the faculty.
     *
     * @return coursename
     */
    public String getCoursename() {
        return coursename;
    }

    /**
     * Sets the course name associated with the faculty.
     *
     * @param coursename the course name to set
     */
    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    /**
     * Returns the subject ID associated with the faculty.
     *
     * @return subjectid
     */
    public long getSubjectid() {
        return subjectid;
    }

    /**
     * Sets the subject ID associated with the faculty.
     *
     * @param subjectid the subject ID to set
     */
    public void setSubjectid(long subjectid) {
        this.subjectid = subjectid;
    }

    /**
     * Returns the subject name associated with the faculty.
     *
     * @return subjectname
     */
    public String getSubjectname() {
        return subjectname;
    }

    /**
     * Sets the subject name associated with the faculty.
     *
     * @param subjectname the subject name to set
     */
    public void setSubjectname(String subjectname) {
        this.subjectname = subjectname;
    }

    /**
     * Returns the display value of the bean. Usually used in dropdowns or UI
     * components.
     *
     * @return null as no specific display value is defined for faculty
     */
    
    public String getValue() {
        return null;
    }
}