package in.co.rays.proj4.bean;

/**
 * SubjectBean is a JavaBean class that represents a Subject entity. It is used
 * to handle records of the ST_SUBJECT table.
 *
 * This class contains subject-related attributes such as name, description,
 * and associated course information.
 *
 * It extends BaseBean to inherit common properties like id, createdBy, etc.
 *
 * @author Harshit Panchal
 */
public class SubjectBean extends BaseBean {

    /** Name of the subject */
    private String name;

    /** Course ID associated with the subject */
    private long courseid;

    /** Course name associated with the subject */
    private String coursename;

    /** Description of the subject */
    private String description;

    /**
     * Returns the name of the subject.
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the subject.
     *
     * @param name the subject name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the course ID associated with the subject.
     *
     * @return courseid
     */
    public long getCourseId() {
        return courseid;
    }

    /**
     * Sets the course ID associated with the subject.
     *
     * @param courseid the course ID to set
     */
    public void setCoursId(long courseid) {
        this.courseid = courseid;
    }

    /**
     * Returns the course name associated with the subject.
     *
     * @return coursename
     */
    public String getCourseName() {
        return coursename;
    }

    /**
     * Sets the course name associated with the subject.
     *
     * @param coursename the course name to set
     */
    public void setCourseName(String coursename) {
        this.coursename = coursename;
    }

    /**
     * Returns the description of the subject.
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the subject.
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
        return name;
    }
}