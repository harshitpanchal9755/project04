package in.co.rays.proj4.bean;

/**
 * CourseBean is a JavaBean class that represents a Course entity. It is used
 * to handle records of the ST_COURSE table.
 *
 * This class contains course-related attributes such as name, duration,
 * and description information.
 *
 * It extends BaseBean to inherit common properties like id, createdBy, etc.
 *
 * @author Harshit Panchal
 */
public class CourseBean extends BaseBean {

    /** Name of the course */
    private String name;

    /** Duration of the course */
    private String duration;

    /** Description of the course */
    private String description;

    /**
     * Returns the name of the course.
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the course.
     *
     * @param name the course name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the duration of the course.
     *
     * @return duration
     */
    public String getDuration() {
        return duration;
    }

    /**
     * Sets the duration of the course.
     *
     * @param duration the duration to set
     */
    public void setDuration(String duration) {
        this.duration = duration;
    }

    /**
     * Returns the description of the course.
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the course.
     *
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
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