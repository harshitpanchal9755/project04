package in.co.rays.proj4.bean;

/**
 * MarksheetBean is a JavaBean class that represents a Marksheet entity. It is
 * used to handle records of the ST_MARKSHEET table.
 *
 * This class contains marksheet-related attributes such as roll number,
 * student details, and subject-wise marks for physics, chemistry, and maths.
 *
 * It extends BaseBean to inherit common properties like id, createdBy, etc.
 *
 * @author Harshit Panchal
 */
public class MarksheetBean extends BaseBean {

    /** Roll number of the student */
    private String rollno;

    /** Unique identifier of the student associated with the marksheet */
    private long studentid;

    /** Name of the student */
    private String name;

    /** Marks obtained in Physics */
    private int physics;

    /** Marks obtained in Chemistry */
    private int chemistry;

    /** Marks obtained in Maths */
    private int maths;

    /**
     * Returns the roll number of the student.
     *
     * @return rollno
     */
    public String getRollno() {
        return rollno;
    }

    /**
     * Sets the roll number of the student.
     *
     * @param rollno the roll number to set
     */
    public void setRollno(String rollno) {
        this.rollno = rollno;
    }

    /**
     * Returns the unique ID of the student associated with the marksheet.
     *
     * @return studentid
     */
    public long getStudentId() {
        return studentid;
    }

    /**
     * Sets the unique ID of the student associated with the marksheet.
     *
     * @param studentid the student ID to set
     */
    public void setStudentId(long studentid) {
        this.studentid = studentid;
    }

    /**
     * Returns the name of the student.
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the student.
     *
     * @param name the student name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the marks obtained in Physics.
     *
     * @return physics
     */
    public int getPhysics() {
        return physics;
    }

    /**
     * Sets the marks obtained in Physics.
     *
     * @param physics the physics marks to set
     */
    public void setPhysics(int physics) {
        this.physics = physics;
    }

    /**
     * Returns the marks obtained in Chemistry.
     *
     * @return chemistry
     */
    public int getChemistry() {
        return chemistry;
    }

    /**
     * Sets the marks obtained in Chemistry.
     *
     * @param chemistry the chemistry marks to set
     */
    public void setChemistry(int chemistry) {
        this.chemistry = chemistry;
    }

    /**
     * Returns the marks obtained in Maths.
     *
     * @return maths
     */
    public int getMaths() {
        return maths;
    }

    /**
     * Sets the marks obtained in Maths.
     *
     * @param maths the maths marks to set
     */
    public void setMaths(int maths) {
        this.maths = maths;
    }

    /**
     * Returns the display value of the bean. Usually used in dropdowns or UI
     * components.
     *
     * @return null as no specific display value is defined for marksheet
     */
    
    public String getValue() {
        return null;
    }
}