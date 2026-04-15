package in.co.rays.proj4.bean;

import java.util.Date;

/**
 * UserBean is a JavaBean class that represents a User entity. It is used to
 * handle records of the ST_USER table.
 *
 * This class contains user-related attributes such as name, login credentials,
 * personal details, and role information.
 *
 * It extends BaseBean to inherit common properties like id, createdBy, etc.
 *
 * @author Harshit Panchal
 */
public class UserBean extends BaseBean {

    /** First name of the user */
    private String firstName;

    /** Last name of the user */
    private String lastName;

    /** Login ID (email/username) of the user */
    private String login;

    /** Password of the user */
    private String password;

    /** Confirm password for validation purpose */
    private String confirmpassword;

    /** Date of birth of the user */
    private Date dob;

    /** Mobile number of the user */
    private String mobileNo;

    /** Role ID associated with the user */
    private long roleId;

    /** Gender of the user */
    private String gender;

    /**
     * Returns the confirm password value.
     *
     * @return confirmpassword
     */
    public String getConfirmpassword() {
        return confirmpassword;
    }

    /**
     * Sets the confirm password value.
     *
     * @param confirmpassword the confirm password to set
     */
    public void setConfirmpassword(String confirmpassword) {
        this.confirmpassword = confirmpassword;
    }

    /**
     * Returns the role ID associated with the user.
     *
     * @return roleId
     */
    public long getRoleId() {
        return roleId;
    }

    /**
     * Sets the role ID associated with the user.
     *
     * @param roleId the role ID to set
     */
    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    /**
     * Returns the first name of the user.
     *
     * @return firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the user.
     *
     * @param firstName the first name to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Returns the last name of the user.
     *
     * @return lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the user.
     *
     * @param lastName the last name to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Returns the login ID of the user.
     *
     * @return login
     */
    public String getLogin() {
        return login;
    }

    /**
     * Sets the login ID of the user.
     *
     * @param login the login ID to set
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Returns the password of the user.
     *
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user.
     *
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the confirm password value.
     * This method is an alias for {@link #getConfirmpassword()}.
     *
     * @return confirmpassword
     */
    public String getConfirmPassword() {
        return confirmpassword;
    }

    /**
     * Sets the confirm password value.
     * This method is an alias for {@link #setConfirmpassword(String)}.
     *
     * @param confirmpassword the confirm password to set
     */
    public void setConfirmPassword(String confirmpassword) {
        this.confirmpassword = confirmpassword;
    }

    /**
     * Returns the date of birth of the user.
     *
     * @return dob
     */
    public Date getDob() {
        return dob;
    }

    /**
     * Sets the date of birth of the user.
     *
     * @param dob the date of birth to set
     */
    public void setDob(Date dob) {
        this.dob = dob;
    }

    /**
     * Returns the mobile number of the user.
     *
     * @return mobileNo
     */
    public String getMobileNo() {
        return mobileNo;
    }

    /**
     * Sets the mobile number of the user.
     *
     * @param mobileNo the mobile number to set
     */
    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    /**
     * Returns the gender of the user.
     *
     * @return gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * Sets the gender of the user.
     *
     * @param gender the gender to set
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Returns the display value of the bean. Usually used in dropdowns or UI
     * components.
     *
     * @return null as no specific display value is defined for user
     */
   
    public String getValue() {
        return null;
    }
}