package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.CourseBean;
import in.co.rays.proj4.bean.SubjectBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DataBaseException;
import in.co.rays.proj4.exception.DuplicateException;
import in.co.rays.proj4.util.JdbcDataSource;

/**
 * SubjectModel is a Model class that handles all database operations for the
 * Subject entity. It is used to perform CRUD operations on the ST_SUBJECT
 * table.
 *
 * This class contains methods for adding, updating, deleting, searching, and
 * retrieving subject records. It also resolves the associated course name
 * using {@link CourseModel} before persisting subject data.
 *
 * It interacts with the database using JDBC and communicates with the
 * {@link SubjectBean} for data transfer.
 *
 * @author Harshit Panchal
 */
public class SubjectModel {

    /**
     * Returns the next available primary key for the ST_SUBJECT table by
     * fetching the current maximum ID and incrementing it by 1.
     *
     * @return next available primary key as Integer
     * @throws DataBaseException if any database error occurs while fetching the key
     */
    public Integer nextpk() throws DataBaseException {
        Connection conn = null;
        int pk = 0;

        try {
            conn = JdbcDataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement("select max(id) from st_subject");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                pk = rs.getInt(1);
            }

            rs.close();
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
            throw new DataBaseException("Exception : Exception in getting pk" + e.getMessage());

        } finally {
            JdbcDataSource.closeConnection(conn);
        }

        return pk + 1;
    }

    /**
     * Adds a new Subject record to the ST_SUBJECT table. Before inserting, it
     * fetches the associated course name using {@link CourseModel} and checks
     * if a subject with the same name already exists to prevent duplicates.
     * Rolls back the transaction if any error occurs during insertion.
     *
     * @param bean the SubjectBean containing the subject details to be added
     * @return the primary key of the newly added subject record
     * @throws ApplicationException if any error occurs during the add operation
     *                              or rollback
     * @throws DuplicateException   if a subject with the same name already exists
     */
    public long add(SubjectBean bean) throws ApplicationException, DuplicateException {
        Connection conn = null;
        int pk = 0;
        CourseModel courseModel = new CourseModel();
        CourseBean coursebean = courseModel.findBypk(bean.getCourseId());
        bean.setCourseName(coursebean.getName());

        SubjectBean duplicatename = findByName(bean.getName());
        if (duplicatename != null) {
            throw new DuplicateException("name is alreadyexist");
        }

        try {
            pk = nextpk();
            conn = JdbcDataSource.getConnection();
            conn.setAutoCommit(false);
            PreparedStatement ps = conn.prepareStatement("insert into st_subject values(?,?,?,?,?,?,?,?,?)");
            ps.setInt(1, pk);
            ps.setString(2, bean.getName());
            ps.setLong(3, bean.getCourseId());
            ps.setString(4, bean.getCourseName());
            ps.setString(5, bean.getDescription());
            ps.setString(6, bean.getCreatedBy());
            ps.setString(7, bean.getModifiedBy());
            ps.setTimestamp(8, bean.getCreatedDateTime());
            ps.setTimestamp(9, bean.getModifiedDateTime());
            ps.executeUpdate();
            conn.commit();
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (Exception ex) {
                e.printStackTrace();
                throw new ApplicationException("Exception : Exception is rollback" + e.getMessage());
            }
            throw new ApplicationException("Exception is finkBypk");

        } finally {
            JdbcDataSource.closeConnection(conn);
        }

        return pk;
    }

    /**
     * Updates an existing Subject record in the ST_SUBJECT table. Before
     * updating, it fetches the latest course name using {@link CourseModel}
     * and sets it on the bean. Rolls back the transaction if any error occurs
     * during the update.
     *
     * @param bean the SubjectBean containing the updated subject details
     * @throws ApplicationException if any error occurs during the update operation
     *                              or rollback
     * @throws DuplicateException   if a subject with the same name already exists
     */
    public void update(SubjectBean bean) throws ApplicationException, DuplicateException {
        Connection conn = null;
        CourseModel courseModel = new CourseModel();
        CourseBean courseBean = courseModel.findBypk(bean.getCourseId());
        bean.setCourseName(courseBean.getName());

        try {
            conn = JdbcDataSource.getConnection();
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement(
                    "update st_subject set name = ?, course_id = ?, course_name = ?, description = ?, created_by = ?, modified_by = ?, created_datetime = ?, modified_datetime = ? where id = ?");
            pstmt.setString(1, bean.getName());
            pstmt.setLong(2, bean.getCourseId());
            pstmt.setString(3, bean.getCourseName());
            pstmt.setString(4, bean.getDescription());
            pstmt.setString(5, bean.getCreatedBy());
            pstmt.setString(6, bean.getModifiedBy());
            pstmt.setTimestamp(7, bean.getCreatedDateTime());
            pstmt.setTimestamp(8, bean.getModifiedDateTime());
            pstmt.setLong(9, bean.getId());
            pstmt.executeUpdate();
            conn.commit();
            pstmt.close();
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
            }
            throw new ApplicationException("Exception in updating Subject ");
        } finally {
            JdbcDataSource.closeConnection(conn);
        }
    }

    /**
     * Deletes an existing Subject record from the ST_SUBJECT table based on
     * the ID present in the provided SubjectBean. Rolls back the transaction
     * if any error occurs during deletion.
     *
     * @param bean the SubjectBean containing the ID of the subject to be deleted
     * @throws ApplicationException if any error occurs during the delete operation
     *                              or rollback
     */
    public void delete(SubjectBean bean) throws ApplicationException {
        Connection conn = null;
        try {
            conn = JdbcDataSource.getConnection();
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement("delete from st_subject where id = ?");
            pstmt.setLong(1, bean.getId());
            pstmt.executeUpdate();
            conn.commit();
            pstmt.close();
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
            }
            throw new ApplicationException("Exception : Exception in delete Subject");
        } finally {
            JdbcDataSource.closeConnection(conn);
        }
    }

    /**
     * Finds and returns a Subject record from the ST_SUBJECT table based on
     * the given primary key.
     *
     * @param pk the primary key of the subject record to be fetched
     * @return SubjectBean containing the subject details, or null if not found
     * @throws ApplicationException if any error occurs while fetching the record
     */
    public SubjectBean findByPk(long pk) throws ApplicationException {
        SubjectBean bean = null;
        Connection conn = null;

        StringBuffer sql = new StringBuffer("select * from st_subject where id = ?");
        try {
            conn = JdbcDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            pstmt.setLong(1, pk);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                bean = new SubjectBean();
                bean.setId(rs.getLong(1));
                bean.setName(rs.getString(2));
                bean.setCoursId(rs.getLong(3));
                bean.setCourseName(rs.getString(4));
                bean.setDescription(rs.getString(5));
                bean.setCreatedBy(rs.getString(6));
                bean.setModifiedBy(rs.getString(7));
                bean.setCreatedDateTime(rs.getTimestamp(8));
                bean.setModifiedDateTime(rs.getTimestamp(9));
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            throw new ApplicationException("Exception : Exception in getting Subject by pk");
        } finally {
            JdbcDataSource.closeConnection(conn);
        }
        return bean;
    }

    /**
     * Finds and returns a Subject record from the ST_SUBJECT table based on
     * the given subject name.
     *
     * @param name the name of the subject to be fetched
     * @return SubjectBean containing the subject details, or null if not found
     * @throws ApplicationException if any error occurs while fetching the record
     */
    public SubjectBean findByName(String name) throws ApplicationException {
        StringBuffer sql = new StringBuffer("select * from st_subject where name = ?");
        SubjectBean bean = null;
        Connection conn = null;
        try {
            conn = JdbcDataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql.toString());
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                bean = new SubjectBean();
                bean.setId(rs.getLong(1));
                bean.setName(rs.getString(2));
                bean.setCoursId(rs.getLong(3));
                bean.setCourseName(rs.getString(4));
                bean.setDescription(rs.getString(5));
                bean.setCreatedBy(rs.getString(6));
                bean.setModifiedBy(rs.getString(7));
                bean.setCreatedDateTime(rs.getTimestamp(8));
                bean.setModifiedDateTime(rs.getTimestamp(9));
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Exception : Exception Subject Name" + e.getMessage());
        } finally {
            JdbcDataSource.closeConnection(conn);
        }
        return bean;
    }

    /**
     * Returns a list of all Subject records from the ST_SUBJECT table without
     * any filters or pagination.
     *
     * @return List of SubjectBean containing all subject records
     * @throws ApplicationException if any error occurs while fetching the records
     */
    public List<SubjectBean> list() throws ApplicationException {
        return search(null, 0, 0);
    }

    /**
     * Searches for Subject records in the ST_SUBJECT table based on the provided
     * search criteria with optional pagination support. If the bean is null, all
     * records are returned. Supports filtering by id, name, course ID, course
     * name, and description.
     *
     * @param bean     the SubjectBean containing the search criteria, or null to
     *                 fetch all records
     * @param pageNo   the page number for pagination (use 0 for no pagination)
     * @param pageSize the number of records per page (use 0 for no pagination)
     * @return List of SubjectBean matching the search criteria
     * @throws ApplicationException if any error occurs during the search operation
     */
    public List<SubjectBean> search(SubjectBean bean, int pageNo, int pageSize) throws ApplicationException {
        StringBuffer sql = new StringBuffer("select * from st_subject where 1=1");

        if (bean != null) {
            if (bean.getId() > 0) {
                sql.append(" and id = " + bean.getId());
            }
            if (bean.getName() != null && bean.getName().length() > 0) {
                sql.append(" and name like '" + bean.getName() + "%'");
            }
            if (bean.getCourseId() > 0) {
                sql.append(" and course_id = " + bean.getCourseId());
            }
            if (bean.getCourseName() != null && bean.getCourseName().length() > 0) {
                sql.append(" and course_name like '" + bean.getCourseName() + "%'");
            }
            if (bean.getDescription() != null && bean.getDescription().length() > 0) {
                sql.append(" and description like '" + bean.getDescription() + "%'");
            }
        }

        if (pageSize > 0) {
            pageNo = (pageNo - 1) * pageSize;
            sql.append(" limit " + pageNo + ", " + pageSize);
        }

        ArrayList<SubjectBean> list = new ArrayList<SubjectBean>();
        Connection conn = null;
        try {
            conn = JdbcDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                bean = new SubjectBean();
                bean.setId(rs.getLong(1));
                bean.setName(rs.getString(2));
                bean.setCoursId(rs.getLong(3));
                bean.setCourseName(rs.getString(4));
                bean.setDescription(rs.getString(5));
                bean.setCreatedBy(rs.getString(6));
                bean.setModifiedBy(rs.getString(7));
                bean.setCreatedDateTime(rs.getTimestamp(8));
                bean.setModifiedDateTime(rs.getTimestamp(9));
                list.add(bean);
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            throw new ApplicationException("Exception : Exception in search Subject");
        } finally {
            JdbcDataSource.closeConnection(conn);
        }
        return list;
    }
}