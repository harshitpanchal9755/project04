package in.co.rays.proj4.bean;

/**
 * RoleBean is a JavaBean class that represents a Role entity. It is used to
 * handle records of the ST_ROLE table.
 *
 * This class contains role-related attributes such as name and description,
 * along with predefined constants representing the available roles in the
 * system such as Admin, Student, College, Kiosk, and Faculty.
 *
 * It extends BaseBean to inherit common properties like id, createdBy, etc.
 *
 * @author Harshit Panchal
 */
public class RoleBean extends BaseBean {

    /** Constant representing the Admin role */
    public static final int ADMIN = 1;

    /** Constant representing the Student role */
    public static final int STUDENT = 2;

    /** Constant representing the College role */
    public static final int COLLAGE = 3;

    /** Constant representing the Kiosk role */
    public static final int KISOK = 4;

    /** Constant representing the Faculty role */
    public static final int FACULTY = 5;

    /** Name of the role */
    private String name;

    /** Description of the role */
    private String description;

    /**
     * Returns the name of the role.
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the role.
     *
     * @param name the role name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the description of the role.
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the role.
     *
     * @param description the role description to set
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