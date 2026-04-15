package in.co.rays.proj4.bean;

import java.util.Date;

/**
 * StudentBean is a JavaBean class that represents a Student entity. It is used
 * to handle records of the ST_STUDENT table.
 *
 * This class contains student-related attributes such as personal details,
 * contact information, and associated college information.
 *
 * It extends BaseBean to inherit common properties like id, createdBy, etc.
 *
 * @author Harshit Panchal
 */
public class StudentBean extends BaseBean {

    /** First name of the student */
    private String firstname;

    /** Last name of the student */
    private String lastname;

    /** Date of birth of the student */
    private Date dob;

    /** Gender of the student */
    private String gender;

    /** Mobile number of the student */
    private String mobileno;

    /** Email address of the student */
    private String email;

    /** College ID associated with the student */
    private long collegeid;

    /** College name associated with the student */
    private String collegename;

    /**
     * Returns the first name of the student.
     *
     * @return firstname
     */
    public String getFirstName() {
        return firstname;
    }

    /**
     * Sets the first name of the student.
     *
     * @param firstname the first name to set
     */
    public void setFirstName(String firstname) {
        this.firstname = firstname;
    }

    /**
     * Returns the last name of the student.
     *
     * @return lastname
     */
    public String getLastName() {
        return lastname;
    }

    /**
     * Sets the last name of the student.
     *
     * @param lastname the last name to set
     */
    public void setLastName(String lastname) {
        this.lastname = lastname;
    }

    /**
     * Returns the date of birth of the student.
     *
     * @return dob
     */
    public Date getDob() {
        return dob;
    }

    /**
     * Sets the date of birth of the student.
     *
     * @param dob the date of birth to set
     */
    public void setDob(Date dob) {
        this.dob = dob;
    }

    /**
     * Returns the gender of the student.
     *
     * @return gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * Sets the gender of the student.
     *
     * @param gender the gender to set
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Returns the mobile number of the student.
     *
     * @return mobileno
     */
    public String getMobileno() {
        return mobileno;
    }

    /**
     * Sets the mobile number of the student.
     *
     * @param mobileno the mobile number to set
     */
    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    /**
     * Returns the email address of the student.
     *
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the student.
     *
     * @param email the email address to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the college ID associated with the student.
     *
     * @return collegeid
     */
    public long getCollegeId() {
        return collegeid;
    }

    /**
     * Sets the college ID associated with the student.
     *
     * @param collegeid the college ID to set
     */
    public void setCollegeId(long collegeid) {
        this.collegeid = collegeid;
    }

    /**
     * Returns the college name associated with the student.
     *
     * @return collegename
     */
    public String getCollegeName() {
        return collegename;
    }

    /**
     * Sets the college name associated with the student.
     *
     * @param collegename the college name to set
     */
    public void setCollegeName(String collegename) {
        this.collegename = collegename;
    }

    /**
     * Returns the display value of the bean. Usually used in dropdowns or UI
     * components.
     *
     * @return full name of the student as display value (firstname + " " + lastname)
     */
    
    public String getValue() {
        return firstname + " " + lastname;
    }
}