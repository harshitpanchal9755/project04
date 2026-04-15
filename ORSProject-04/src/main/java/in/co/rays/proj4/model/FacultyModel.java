package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import in.co.rays.proj4.bean.TimetableBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.util.JdbcDataSource;

/**
 * FacultyModel is a Model class that handles database operations related to
 * the Timetable entity for faculty-related queries. It interacts with the
 * ST_TIMETABLE table to perform search and validation operations.
 *
 * This class provides methods to check timetable entries based on various
 * criteria such as course name, subject name, semester, and exam time,
 * as well as a paginated search method.
 *
 * @author Ram
 */
public class FacultyModel {

    /**
     * Checks whether a timetable entry exists for the given course and exam date.
     *
     * Queries the ST_TIMETABLE table by matching course_id and exam_date.
     *
     * @param courseId  the ID of the course to search for
     * @param examDate  the exam date to match
     * @return TimetableBean if a matching record is found, null otherwise
     * @throws ApplicationException if any database exception occurs during the operation
     */
    public TimetableBean checkByCourseName(Long courseId, Date examDate) throws ApplicationException {
        StringBuffer sql = new StringBuffer("select * from st_timetable where course_id = ? and exam_date = ?");
        TimetableBean bean = null;
        Connection conn = null;

        try {
            conn = JdbcDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            pstmt.setLong(1, courseId);
            pstmt.setDate(2, new java.sql.Date(examDate.getTime()));

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                bean = new TimetableBean();
                bean.setId(rs.getLong(1));
                bean.setSemester(rs.getString(2));
                bean.setDescription(rs.getString(3));
                bean.setExamdate(rs.getDate(4));
                bean.setExamtime(rs.getString(5));
                bean.setCourseid(rs.getLong(6));
                bean.setCoursename(rs.getString(7));
                bean.setSubjectid(rs.getLong(8));
                bean.setSubjectname(rs.getString(9));
                bean.setCreatedBy(rs.getString(10));
                bean.setModifiedBy(rs.getString(11));
                bean.setCreatedDateTime(rs.getTimestamp(12));
                bean.setModifiedDateTime(rs.getTimestamp(13));
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            throw new ApplicationException("Exception : Exception in get Timetable");
        } finally {
            JdbcDataSource.closeConnection(conn);
        }
        return bean;
    }

    /**
     * Checks whether a timetable entry exists for the given course, subject, and exam date.
     *
     * Queries the ST_TIMETABLE table by matching course_id, subject_id, and exam_date.
     *
     * @param courseId   the ID of the course to search for
     * @param subjectId  the ID of the subject to search for
     * @param examDate   the exam date to match
     * @return TimetableBean if a matching record is found, null otherwise
     * @throws ApplicationException if any database exception occurs during the operation
     */
    public TimetableBean checkBySubjectName(Long courseId, Long subjectId, Date examDate) throws ApplicationException {
        StringBuffer sql = new StringBuffer(
                "select * from st_timetable where course_id = ? and subject_id = ? and exam_date = ?");
        TimetableBean bean = null;
        Connection conn = null;

        try {
            conn = JdbcDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            pstmt.setLong(1, courseId);
            pstmt.setLong(2, subjectId);
            pstmt.setDate(3, new java.sql.Date(examDate.getTime()));

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                bean = new TimetableBean();
                bean.setId(rs.getLong(1));
                bean.setSemester(rs.getString(2));
                bean.setDescription(rs.getString(3));
                bean.setExamdate(rs.getDate(4));
                bean.setExamtime(rs.getString(5));
                bean.setCourseid(rs.getLong(6));
                bean.setCoursename(rs.getString(7));
                bean.setSubjectid(rs.getLong(8));
                bean.setSubjectname(rs.getString(9));
                bean.setCreatedBy(rs.getString(10));
                bean.setModifiedBy(rs.getString(11));
                bean.setCreatedDateTime(rs.getTimestamp(12));
                bean.setModifiedDateTime(rs.getTimestamp(13));
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            throw new ApplicationException("Exception : Exception in get Timetable");
        } finally {
            JdbcDataSource.closeConnection(conn);
        }
        return bean;
    }

    /**
     * Checks whether a timetable entry exists for the given course, subject,
     * semester, and exam date.
     *
     * Queries the ST_TIMETABLE table by matching course_id, subject_id,
     * semester, and exam_date.
     *
     * @param courseId   the ID of the course to search for
     * @param subjectId  the ID of the subject to search for
     * @param semester   the semester name to match
     * @param examDate   the exam date to match
     * @return TimetableBean if a matching record is found, null otherwise
     * @throws ApplicationException if any database exception occurs during the operation
     */
    public TimetableBean checkBySemester(Long courseId, Long subjectId, String semester, Date examDate)
            throws ApplicationException {
        StringBuffer sql = new StringBuffer(
                "select * from st_timetable where course_id = ? and subject_id = ? and semester = ? and exam_date = ?");
        TimetableBean bean = null;
        Connection conn = null;

        try {
            conn = JdbcDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            pstmt.setLong(1, courseId);
            pstmt.setLong(2, subjectId);
            pstmt.setString(3, semester);
            pstmt.setDate(4, new java.sql.Date(examDate.getTime()));

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                bean = new TimetableBean();
                bean.setId(rs.getLong(1));
                bean.setSemester(rs.getString(2));
                bean.setDescription(rs.getString(3));
                bean.setExamdate(rs.getDate(4));
                bean.setExamtime(rs.getString(5));
                bean.setCourseid(rs.getLong(6));
                bean.setCoursename(rs.getString(7));
                bean.setSubjectid(rs.getLong(8));
                bean.setSubjectname(rs.getString(9));
                bean.setCreatedBy(rs.getString(10));
                bean.setModifiedBy(rs.getString(11));
                bean.setCreatedDateTime(rs.getTimestamp(12));
                bean.setModifiedDateTime(rs.getTimestamp(13));
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            throw new ApplicationException("Exception : Exception in get Timetable");
        } finally {
            JdbcDataSource.closeConnection(conn);
        }
        return bean;
    }

    /**
     * Checks whether a timetable entry exists matching all provided criteria including
     * course, subject, semester, exam date, exam time, and description.
     *
     * Queries the ST_TIMETABLE table by matching all six fields to detect
     * duplicate or conflicting timetable entries.
     *
     * @param courseId    the ID of the course to search for
     * @param subjectId   the ID of the subject to search for
     * @param semester    the semester name to match
     * @param examDate    the exam date to match
     * @param examTime    the exam time to match
     * @param description the description to match
     * @return TimetableBean if a matching record is found, null otherwise
     * @throws ApplicationException if any database exception occurs during the operation
     */
    public TimetableBean checkByExamTime(Long courseId, Long subjectId, String semester, Date examDate, String examTime,
            String description) throws ApplicationException {
        StringBuffer sql = new StringBuffer(
                "select * from st_timetable where course_id = ? and subject_id = ? and semester = ? and exam_date = ? and exam_time = ? and description = ?");
        TimetableBean bean = null;
        Connection conn = null;

        try {
            conn = JdbcDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            pstmt.setLong(1, courseId);
            pstmt.setLong(2, subjectId);
            pstmt.setString(3, semester);
            pstmt.setDate(4, new java.sql.Date(examDate.getTime()));
            pstmt.setString(5, examTime);
            pstmt.setString(6, description);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                bean = new TimetableBean();
                bean.setId(rs.getLong(1));
                bean.setSemester(rs.getString(2));
                bean.setDescription(rs.getString(3));
                bean.setExamdate(rs.getDate(4));
                bean.setExamtime(rs.getString(5));
                bean.setCourseid(rs.getLong(6));
                bean.setCoursename(rs.getString(7));
                bean.setSubjectid(rs.getLong(8));
                bean.setSubjectname(rs.getString(9));
                bean.setCreatedBy(rs.getString(10));
                bean.setModifiedBy(rs.getString(11));
                bean.setCreatedDateTime(rs.getTimestamp(12));
                bean.setModifiedDateTime(rs.getTimestamp(13));
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            throw new ApplicationException("Exception : Exception in get Timetable");
        } finally {
            JdbcDataSource.closeConnection(conn);
        }
        return bean;
    }

    /**
     * Searches timetable records from the ST_TIMETABLE table based on the
     * provided filter criteria with pagination support.
     *
     * Dynamically builds a SQL query using non-null fields of the given
     * TimetableBean. Supports filtering by id, course, subject, semester,
     * description, exam date, and exam time. Results are paginated using
     * pageNo and pageSize parameters.
     *
     * @param bean      the TimetableBean containing filter criteria; fields with
     *                  non-null or positive values are included in the WHERE clause
     * @param pageNo    the page number to retrieve (1-based index)
     * @param pageSize  the number of records per page; if 0 or less, pagination is skipped
     * @return a List of TimetableBean objects matching the search criteria
     * @throws ApplicationException if any database exception occurs during the operation
     */
    public List<TimetableBean> search(TimetableBean bean, int pageNo, int pageSize) throws ApplicationException {
        StringBuffer sql = new StringBuffer("select * from st_timetable where 1=1");

        if (bean != null) {
            if (bean.getId() > 0) {
                sql.append(" and id = " + bean.getId());
            }
            if (bean.getCourseid() > 0) {
                sql.append(" and course_id = " + bean.getCourseid());
            }
            if (bean.getCoursename() != null && bean.getCoursename().length() > 0) {
                sql.append(" and course_name like '" + bean.getCoursename() + "%'");
            }
            if (bean.getSubjectid() > 0) {
                sql.append(" and subject_id = " + bean.getSubjectid());
            }
            if (bean.getSubjectname() != null && bean.getSubjectname().length() > 0) {
                sql.append(" and subject_name like '" + bean.getSubjectname() + "%'");
            }
            if (bean.getSemester() != null && bean.getSemester().length() > 0) {
                sql.append(" and semester like '" + bean.getSemester() + "%'");
            }
            if (bean.getDescription() != null && bean.getDescription().length() > 0) {
                sql.append(" and description like '" + bean.getDescription() + "%'");
            }
            if (bean.getExamdate() != null && bean.getExamdate().getDate() > 0) {
                sql.append(" and exam_date like '" + new java.sql.Date(bean.getExamdate().getTime()) + "%'");
            }
            if (bean.getExamtime() != null && bean.getExamtime().length() > 0) {
                sql.append(" and exam_time like '" + bean.getExamtime() + "%'");
            }
        }

        if (pageSize > 0) {
            pageNo = (pageNo - 1) * pageSize;
            sql.append(" limit " + pageNo + ", " + pageSize);
        }

        ArrayList<TimetableBean> list = new ArrayList<TimetableBean>();
        Connection conn = null;
        try {
            conn = JdbcDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                bean = new TimetableBean();
                bean.setId(rs.getLong(1));
                bean.setSemester(rs.getString(2));
                bean.setDescription(rs.getString(3));
                bean.setExamdate(rs.getDate(4));
                bean.setExamtime(rs.getString(5));
                bean.setCourseid(rs.getLong(6));
                bean.setCoursename(rs.getString(7));
                bean.setSubjectid(rs.getLong(8));
                bean.setSubjectname(rs.getString(9));
                bean.setCreatedBy(rs.getString(10));
                bean.setModifiedBy(rs.getString(11));
                bean.setCreatedDateTime(rs.getTimestamp(12));
                bean.setModifiedDateTime(rs.getTimestamp(13));
                list.add(bean);
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            throw new ApplicationException("Exception : Exception in search Timetable");
        } finally {
            JdbcDataSource.closeConnection(conn);
        }
        return list;
    }
}