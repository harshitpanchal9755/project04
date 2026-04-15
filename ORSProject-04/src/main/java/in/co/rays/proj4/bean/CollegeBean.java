package in.co.rays.proj4.bean;
/**
 * CollegeBean is a JavaBean class that represents a College entity. It is used
 * to handle records of the ST_COLLEGE table.
 *
 * This class contains college-related attributes such as name, address, state,
 * city, and phone number information.
 *
 * It extends BaseBean to inherit common properties like id, createdBy, etc.
 *
 * @author Harshit Panchal
 */

public class CollegeBean extends BaseBean {

	  /** Name of the college */
	private String name;
	  /** Address of the college */
	private String address;
	 /** State where the college is located */
	private String state;
	 /** City where the college is located */
	private String city;
	 /** Phone number of the college */
	private String phoneno;

	 /**
     * Returns the name of the college.
     *
     * @return name
     */
	public String getName() {
		return name;
	}
	 /**
     * Sets the name of the college.
     *
     * @param name the college name to set
     */

	public void setName(String name) {
		this.name = name;
	}
	 /**
     * Returns the address of the college.
     *
     * @return address
     */

	public String getAddress() {
		return address;
	}
	 /**
     * Sets the address of the college.
     *
     * @param address the address to set
     */

	public void setAddress(String address) {
		this.address = address;
	}
	 /**
     * Returns the state where the college is located.
     *
     * @return state
     */

	public String getState() {
		return state;
	}
	 /**
     * Sets the state where the college is located.
     *
     * @param state the state to set
     */

	public void setState(String state) {
		this.state = state;
	}

    /**
     * Returns the city where the college is located.
     *
     * @return city
     */

	public String getCity() {
		return city;
	}
	 /**
     * Sets the city where the college is located.
     *
     * @param city the city to set
     */

	public void setCity(String city) {
		this.city = city;
	}
	  /**
     * Returns the phone number of the college.
     *
     * @return phoneno
     */

	public String getPhoneno() {
		return phoneno;
	}
	  /**
     * Sets the phone number of the college.
     *
     * @param phoneno the phone number to set
     */

	public void setPhoneno(String phoneno) {
		this.phoneno = phoneno;
	}
	 /**
     * Returns the display value of the bean. Usually used in dropdowns or UI
     * components.
     *
     * @return name as display value
     */

	public String getValue() {
		// TODO Auto-generated method stub
		return name;
	}

}
