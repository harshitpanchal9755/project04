package in.co.rays.proj4.bean;

import java.util.Date;

/**
 * TimetableBean is a JavaBean class that represents a Timetable entity. It is
 * used to handle records of the ST_TIMETABLE table.
 *
 * This class contains timetable-related attributes such as semester,
 * description, exam date and time, and associated course and subject
 * information.
 *
 * It extends BaseBean to inherit common properties like id, createdBy, etc.
 *
 * @author Harshit Panchal
 */
public class TimetableBean extends BaseBean {

    /** Semester for which the timetable is created */
    private String semester;

    /** Description of the timetable entry */
    private String description;

    /** Date of the exam scheduled in the timetable */
    private Date examdate;

    /** Time of the exam scheduled in the timetable */
    private String examtime;

    /** Course ID associated with the timetable entry */
    private long courseid;

    /** Course name associated with the timetable entry */
    private String coursename;

    /** Subject ID associated with the timetable entry */
    private long subjectid;

    /** Subject name associated with the timetable entry */
    private String subjectname;

    /**
     * Returns the semester for which the timetable is created.
     *
     * @return semester
     */
    public String getSemester() {
        return semester;
    }

    /**
     * Sets the semester for which the timetable is created.
     *
     * @param semester the semester to set
     */
    public void setSemester(String semester) {
        this.semester = semester;
    }

    /**
     * Returns the description of the timetable entry.
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the timetable entry.
     *
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the date of the exam scheduled in the timetable.
     *
     * @return examdate
     */
    public Date getExamdate() {
        return examdate;
    }

    /**
     * Sets the date of the exam scheduled in the timetable.
     *
     * @param examdate the exam date to set
     */
    public void setExamdate(Date examdate) {
        this.examdate = examdate;
    }

    /**
     * Returns the time of the exam scheduled in the timetable.
     *
     * @return examtime
     */
    public String getExamtime() {
        return examtime;
    }

    /**
     * Sets the time of the exam scheduled in the timetable.
     *
     * @param examtime the exam time to set
     */
    public void setExamtime(String examtime) {
        this.examtime = examtime;
    }

    /**
     * Returns the course ID associated with the timetable entry.
     *
     * @return courseid
     */
    public long getCourseid() {
        return courseid;
    }

    /**
     * Sets the course ID associated with the timetable entry.
     *
     * @param courseid the course ID to set
     */
    public void setCourseid(long courseid) {
        this.courseid = courseid;
    }

    /**
     * Returns the course name associated with the timetable entry.
     *
     * @return coursename
     */
    public String getCoursename() {
        return coursename;
    }

    /**
     * Sets the course name associated with the timetable entry.
     *
     * @param coursename the course name to set
     */
    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    /**
     * Returns the subject ID associated with the timetable entry.
     *
     * @return subjectid
     */
    public long getSubjectid() {
        return subjectid;
    }

    /**
     * Sets the subject ID associated with the timetable entry.
     *
     * @param subjectid the subject ID to set
     */
    public void setSubjectid(long subjectid) {
        this.subjectid = subjectid;
    }

    /**
     * Returns the subject name associated with the timetable entry.
     *
     * @return subjectname
     */
    public String getSubjectname() {
        return subjectname;
    }

    /**
     * Sets the subject name associated with the timetable entry.
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
     * @return coursename as display value
     */
    
    public String getValue() {
        return coursename;
    }
}