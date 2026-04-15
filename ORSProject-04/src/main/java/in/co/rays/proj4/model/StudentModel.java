package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.CollegeBean;
import in.co.rays.proj4.bean.StudentBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DataBaseException;
import in.co.rays.proj4.exception.DuplicateException;
import in.co.rays.proj4.util.JdbcDataSource;

/**
 * StudentModel is a Model class that handles all database operations for the
 * Student entity. It is used to perform CRUD operations on the ST_STUDENT
 * table.
 *
 * This class contains methods for adding, updating, deleting, searching, and
 * retrieving student records. It also resolves the associated college name
 * using {@link CollegeModel} before persisting student data.
 *
 * It interacts with the database using JDBC and communicates with the
 * {@link StudentBean} for data transfer.
 *
 * @author Harshit Panchal
 */
public class StudentModel {

    /**
     * Returns the next available primary key for the ST_STUDENT table by
     * fetching the current maximum ID and incrementing it by 1.
     *
     * @return next available primary key as Integer
     * @throws DataBaseException if any database error occurs while fetching the key
     */
    public Integer nextPk() throws DataBaseException {

        Connection conn = null;
        int pk = 0;

        try {
            conn = JdbcDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_student");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                pk = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            throw new DataBaseException("Exception : Exception in getting PK");
        } finally {
            JdbcDataSource.closeConnection(conn);
        }
        return pk + 1;
    }

    /**
     * Adds a new Student record to the ST_STUDENT table. Before inserting, it
     * fetches the associated college name using {@link CollegeModel} and checks
     * if a student with the same email already exists to prevent duplicates.
     * Rolls back the transaction if any error occurs during insertion.
     *
     * @param bean the StudentBean containing the student details to be added
     * @return the primary key of the newly added student record
     * @throws ApplicationException if any error occurs during the add operation
     *                              or rollback
     * @throws DuplicateException   if a student with the same email already exists
     */
    public long add(StudentBean bean) throws ApplicationException, DuplicateException {

        Connection conn = null;

        CollegeModel collegeModel = new CollegeModel();
        CollegeBean collegeBean = collegeModel.findBypk(bean.getCollegeId());
        bean.setCollegeName(collegeBean.getName());

        StudentBean existBean = findByEmailId(bean.getEmail());
        int pk = 0;

        if (existBean != null) {
            throw new DuplicateException("Email already exists");
        }

        try {
            pk = nextPk();
            conn = JdbcDataSource.getConnection();
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn
                    .prepareStatement("insert into st_student values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            pstmt.setInt(1, pk);
            pstmt.setString(2, bean.getFirstName());
            pstmt.setString(3, bean.getLastName());
            pstmt.setDate(4, new java.sql.Date(bean.getDob().getTime()));
            pstmt.setString(5, bean.getGender());
            pstmt.setString(6, bean.getMobileno());
            pstmt.setString(7, bean.getEmail());
            pstmt.setLong(8, bean.getCollegeId());
            pstmt.setString(9, bean.getCollegeName());
            pstmt.setString(10, bean.getCreatedBy());
            pstmt.setString(11, bean.getModifiedBy());
            pstmt.setTimestamp(12, bean.getCreatedDateTime());
            pstmt.setTimestamp(13, bean.getModifiedDateTime());
            pstmt.executeUpdate();
            conn.commit();
            pstmt.close();
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
            }
            e.printStackTrace();
            throw new ApplicationException("Exception : Exception in add Student");
        } finally {
            JdbcDataSource.closeConnection(conn);
        }
        return pk;
    }

    /**
     * Updates an existing Student record in the ST_STUDENT table. Before
     * updating, it fetches the latest college name using {@link CollegeModel}
     * and checks if another student with the same email already exists to
     * prevent duplicates. Rolls back the transaction if any error occurs
     * during the update.
     *
     * @param bean the StudentBean containing the updated student details
     * @throws ApplicationException if any error occurs during the update operation
     *                              or rollback
     * @throws DuplicateException   if another student with the same email already
     *                              exists
     */
    public void update(StudentBean bean) throws ApplicationException, DuplicateException {

        Connection conn = null;

        CollegeModel collegeModel = new CollegeModel();
        CollegeBean collegeBean = collegeModel.findBypk(bean.getCollegeId());
        bean.setCollegeName(collegeBean.getName());

        StudentBean existBean = findByEmailId(bean.getEmail());

        if (existBean != null && existBean.getId() != bean.getId()) {
            throw new DuplicateException("Email Id is already exist");
        }

        try {
            conn = JdbcDataSource.getConnection();
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement(
                    "update st_student set first_name = ?, last_name = ?, dob = ?, gender = ?, mobile_no = ?, email = ?, college_id = ?, college_name = ?, created_by = ?, modified_by = ?, created_datetime = ?, modified_datetime = ? where id = ?");
            pstmt.setString(1, bean.getFirstName());
            pstmt.setString(2, bean.getLastName());
            pstmt.setDate(3, new java.sql.Date(bean.getDob().getTime()));
            pstmt.setString(4, bean.getGender());
            pstmt.setString(5, bean.getMobileno());
            pstmt.setString(6, bean.getEmail());
            pstmt.setLong(7, bean.getCollegeId());
            pstmt.setString(8, bean.getCollegeName());
            pstmt.setString(9, bean.getCreatedBy());
            pstmt.setString(10, bean.getModifiedBy());
            pstmt.setTimestamp(11, bean.getCreatedDateTime());
            pstmt.setTimestamp(12, bean.getModifiedDateTime());
            pstmt.setLong(13, bean.getId());
            pstmt.executeUpdate();
            conn.commit();
            pstmt.close();
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
            }
            e.printStackTrace();
            throw new ApplicationException("Exception in updating Student ");
        } finally {
            JdbcDataSource.closeConnection(conn);
        }
    }

    /**
     * Deletes an existing Student record from the ST_STUDENT table based on
     * the ID present in the provided StudentBean. Rolls back the transaction
     * if any error occurs during deletion.
     *
     * @param bean the StudentBean containing the ID of the student to be deleted
     * @throws ApplicationException if any error occurs during the delete operation
     *                              or rollback
     */
    public void delete(StudentBean bean) throws ApplicationException {

        Connection conn = null;

        try {
            conn = JdbcDataSource.getConnection();
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement("delete from st_student where id = ?");
            pstmt.setLong(1, bean.getId());
            pstmt.executeUpdate();
            conn.commit();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
            }
            throw new ApplicationException("Exception : Exception in delete Student");
        } finally {
            JdbcDataSource.closeConnection(conn);
        }
    }

    /**
     * Finds and returns a Student record from the ST_STUDENT table based on
     * the given primary key.
     *
     * @param pk the primary key of the student record to be fetched
     * @return StudentBean containing the student details, or null if not found
     * @throws ApplicationException if any error occurs while fetching the record
     */
    public StudentBean findByPk(long pk) throws ApplicationException {

        StringBuffer sql = new StringBuffer("select * from st_student where id = ?");
        StudentBean bean = null;
        Connection conn = null;

        try {
            conn = JdbcDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            pstmt.setLong(1, pk);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                bean = new StudentBean();
                bean.setId(rs.getLong(1));
                bean.setFirstName(rs.getString(2));
                bean.setLastName(rs.getString(3));
                bean.setDob(rs.getDate(4));
                bean.setGender(rs.getString(5));
                bean.setMobileno(rs.getString(6));
                bean.setEmail(rs.getString(7));
                bean.setCollegeId(rs.getLong(8));
                bean.setCollegeName(rs.getString(9));
                bean.setCreatedBy(rs.getString(10));
                bean.setModifiedBy(rs.getString(11));
                bean.setCreatedDateTime(rs.getTimestamp(12));
                bean.setModifiedDateTime(rs.getTimestamp(13));
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            throw new ApplicationException("Exception : Exception in getting User by pk");
        } finally {
            JdbcDataSource.closeConnection(conn);
        }
        return bean;
    }

    /**
     * Finds and returns a Student record from the ST_STUDENT table based on
     * the given email address.
     *
     * @param Email the email address of the student to be fetched
     * @return StudentBean containing the student details, or null if not found
     * @throws ApplicationException if any error occurs while fetching the record
     */
    public StudentBean findByEmailId(String Email) throws ApplicationException {

        StringBuffer sql = new StringBuffer("select * from st_student where email = ?");
        StudentBean bean = null;
        Connection conn = null;

        try {
            conn = JdbcDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            pstmt.setString(1, Email);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                bean = new StudentBean();
                bean.setId(rs.getLong(1));
                bean.setFirstName(rs.getString(2));
                bean.setLastName(rs.getString(3));
                bean.setDob(rs.getDate(4));
                bean.setGender(rs.getString(5));
                bean.setMobileno(rs.getString(6));
                bean.setEmail(rs.getString(7));
                bean.setCollegeId(rs.getLong(8));
                bean.setCollegeName(rs.getString(9));
                bean.setCreatedBy(rs.getString(10));
                bean.setModifiedBy(rs.getString(11));
                bean.setCreatedDateTime(rs.getTimestamp(12));
                bean.setModifiedDateTime(rs.getTimestamp(13));
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            throw new ApplicationException("Exception : Exception in getting User by Email");
        } finally {
            JdbcDataSource.closeConnection(conn);
        }
        return bean;
    }

    /**
     * Returns a list of all Student records from the ST_STUDENT table without
     * any filters or pagination.
     *
     * @return List of StudentBean containing all student records
     * @throws ApplicationException if any error occurs while fetching the records
     */
    public List<StudentBean> list() throws ApplicationException {
        return search(null, 0, 0);
    }

    /**
     * Searches for Student records in the ST_STUDENT table based on the provided
     * search criteria with optional pagination support. If the bean is null, all
     * records are returned. Supports filtering by id, first name, last name, date
     * of birth, gender, mobile number, email, and college name.
     *
     * @param bean     the StudentBean containing the search criteria, or null to
     *                 fetch all records
     * @param pageNo   the page number for pagination (use 0 for no pagination)
     * @param pageSize the number of records per page (use 0 for no pagination)
     * @return List of StudentBean matching the search criteria
     * @throws ApplicationException if any error occurs during the search operation
     */
    public List<StudentBean> search(StudentBean bean, int pageNo, int pageSize) throws ApplicationException {

        StringBuffer sql = new StringBuffer("select * from st_student where 1 = 1");

        if (bean != null) {
            if (bean.getId() > 0) {
                sql.append(" and id = " + bean.getId());
            }
            if (bean.getFirstName() != null && bean.getFirstName().length() > 0) {
                sql.append(" and first_name like '" + bean.getFirstName() + "%'");
            }
            if (bean.getLastName() != null && bean.getLastName().length() > 0) {
                sql.append(" and last_name like '" + bean.getLastName() + "%'");
            }
            if (bean.getDob() != null && bean.getDob().getDate() > 0) {
                sql.append(" and dob = " + bean.getDob());
            }
            if (bean.getGender() != null && bean.getGender().length() > 0) {
                sql.append(" and gender like '" + bean.getGender() + "%'");
            }
            if (bean.getMobileno() != null && bean.getMobileno().length() > 0) {
                sql.append(" and mobile_no like '" + bean.getMobileno() + "%'");
            }
            if (bean.getEmail() != null && bean.getEmail().length() > 0) {
                sql.append(" and email like '" + bean.getEmail() + "%'");
            }
            if (bean.getCollegeName() != null && bean.getCollegeName().length() > 0) {
                sql.append(" and college_name = " + bean.getCollegeName());
            }
        }

        if (pageSize > 0) {
            pageNo = (pageNo - 1) * pageSize;
            sql.append(" limit " + pageNo + ", " + pageSize);
        }

        ArrayList<StudentBean> list = new ArrayList<StudentBean>();
        Connection conn = null;
        try {
            conn = JdbcDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                bean = new StudentBean();
                bean.setId(rs.getLong(1));
                bean.setFirstName(rs.getString(2));
                bean.setLastName(rs.getString(3));
                bean.setDob(rs.getDate(4));
                bean.setGender(rs.getString(5));
                bean.setMobileno(rs.getString(6));
                bean.setEmail(rs.getString(7));
                bean.setCollegeId(rs.getLong(8));
                bean.setCollegeName(rs.getString(9));
                bean.setCreatedBy(rs.getString(10));
                bean.setModifiedBy(rs.getString(11));
                bean.setCreatedDateTime(rs.getTimestamp(12));
                bean.setModifiedDateTime(rs.getTimestamp(13));
                list.add(bean);
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            throw new ApplicationException("Exception : Exception in search Student");
        } finally {
            JdbcDataSource.closeConnection(conn);
        }
        return list;
    }
}