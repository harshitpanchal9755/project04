package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.MarksheetBean;
import in.co.rays.proj4.bean.StudentBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DataBaseException;
import in.co.rays.proj4.exception.DuplicateException;
import in.co.rays.proj4.util.JdbcDataSource;

/**
 * MarksheetModel is a Model class that handles all database operations for the
 * Marksheet entity. It is used to perform CRUD operations on the ST_MARKSHEET
 * table.
 *
 * This class contains methods for adding, updating, deleting, searching, and
 * retrieving marksheet records. It also resolves the associated student name
 * using {@link StudentModel} before persisting marksheet data, and provides a
 * merit list feature based on minimum qualifying marks in each subject.
 *
 * It interacts with the database using JDBC and communicates with the
 * {@link MarksheetBean} for data transfer.
 *
 * @author Harshit Panchal
 */
public class MarksheetModel {

    /**
     * Returns the next available primary key for the ST_MARKSHEET table by
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
            PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_marksheet");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                pk = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            throw new DataBaseException("Exception in Marksheet getting PK");
        } finally {
            JdbcDataSource.closeConnection(conn);
        }
        return pk + 1;
    }

    /**
     * Adds a new Marksheet record to the ST_MARKSHEET table. Before inserting,
     * it fetches the associated student name using {@link StudentModel} and
     * checks if a marksheet with the same roll number already exists to prevent
     * duplicates. Rolls back the transaction if any error occurs during insertion.
     *
     * @param bean the MarksheetBean containing the marksheet details to be added
     * @return the primary key of the newly added marksheet record
     * @throws ApplicationException if any error occurs during the add operation
     *                              or rollback
     * @throws DuplicateException   if a marksheet with the same roll number
     *                              already exists
     */
    public long add(MarksheetBean bean) throws ApplicationException, DuplicateException {

        Connection conn = null;
        int pk = 0;

        StudentModel studentModel = new StudentModel();
        StudentBean studentbean = studentModel.findByPk(bean.getStudentId());
        bean.setName(studentbean.getFirstName() + " " + studentbean.getLastName());

        MarksheetBean duplicateMarksheet = findByRollNo(bean.getRollno());

        if (duplicateMarksheet != null) {
            throw new DuplicateException("Roll Number already exists");
        }

        try {
            conn = JdbcDataSource.getConnection();
            pk = nextPk();
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn
                    .prepareStatement("insert into st_marksheet values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            pstmt.setInt(1, pk);
            pstmt.setString(2, bean.getRollno());
            pstmt.setLong(3, bean.getStudentId());
            pstmt.setString(4, bean.getName());
            pstmt.setInt(5, bean.getPhysics());
            pstmt.setInt(6, bean.getChemistry());
            pstmt.setInt(7, bean.getMaths());
            pstmt.setString(8, bean.getCreatedBy());
            pstmt.setString(9, bean.getModifiedBy());
            pstmt.setTimestamp(10, bean.getCreatedDateTime());
            pstmt.setTimestamp(11, bean.getModifiedDateTime());
            pstmt.executeUpdate();
            conn.commit();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("add rollback exception " + ex.getMessage());
            }
            throw new ApplicationException("Exception in add marksheet");
        } finally {
            JdbcDataSource.closeConnection(conn);
        }
        return pk;
    }

    /**
     * Updates an existing Marksheet record in the ST_MARKSHEET table. Before
     * updating, it checks if another marksheet with the same roll number already
     * exists to prevent duplicates, and fetches the latest student name using
     * {@link StudentModel}. Rolls back the transaction if any error occurs
     * during the update.
     *
     * @param bean the MarksheetBean containing the updated marksheet details
     * @throws ApplicationException if any error occurs during the update operation
     *                              or rollback
     * @throws DuplicateException   if another marksheet with the same roll number
     *                              already exists
     */
    public void update(MarksheetBean bean) throws ApplicationException, DuplicateException {

        Connection conn = null;

        MarksheetBean beanExist = findByRollNo(bean.getRollno());

        if (beanExist != null && beanExist.getId() != bean.getId()) {
            throw new DuplicateException("Roll No is already exist");
        }

        StudentModel studentModel = new StudentModel();
        StudentBean studentbean = studentModel.findByPk(bean.getStudentId());
        bean.setName(studentbean.getFirstName() + " " + studentbean.getLastName());

        try {
            conn = JdbcDataSource.getConnection();
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement(
                    "update st_marksheet set roll_no = ?, student_id = ?, name = ?, physics = ?, chemistry = ?, maths = ?, created_by = ?, modified_by = ?, created_datetime = ?, modified_datetime = ? where id = ?");
            pstmt.setString(1, bean.getRollno());
            pstmt.setLong(2, bean.getStudentId());
            pstmt.setString(3, bean.getName());
            pstmt.setInt(4, bean.getPhysics());
            pstmt.setInt(5, bean.getChemistry());
            pstmt.setInt(6, bean.getMaths());
            pstmt.setString(7, bean.getCreatedBy());
            pstmt.setString(8, bean.getModifiedBy());
            pstmt.setTimestamp(9, bean.getCreatedDateTime());
            pstmt.setTimestamp(10, bean.getModifiedDateTime());
            pstmt.setLong(11, bean.getId());
            pstmt.executeUpdate();
            conn.commit();
            pstmt.close();
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Update rollback exception " + ex.getMessage());
            }
            throw new ApplicationException("Exception in updating Marksheet ");
        } finally {
            JdbcDataSource.closeConnection(conn);
        }
    }

    /**
     * Deletes an existing Marksheet record from the ST_MARKSHEET table based
     * on the ID present in the provided MarksheetBean. Rolls back the transaction
     * if any error occurs during deletion.
     *
     * @param bean the MarksheetBean containing the ID of the marksheet to be deleted
     * @throws ApplicationException if any error occurs during the delete operation
     *                              or rollback
     */
    public void delete(MarksheetBean bean) throws ApplicationException {

        Connection conn = null;

        try {
            conn = JdbcDataSource.getConnection();
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement("delete from st_marksheet where id = ?");
            pstmt.setLong(1, bean.getId());
            System.out.println("Deleted Marksheet");
            pstmt.executeUpdate();
            conn.commit();
            pstmt.close();
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Delete rollback exception " + ex.getMessage());
            }
            throw new ApplicationException("Exception in delete marksheet");
        } finally {
            JdbcDataSource.closeConnection(conn);
        }
    }

    /**
     * Finds and returns a Marksheet record from the ST_MARKSHEET table based
     * on the given primary key.
     *
     * @param pk the primary key of the marksheet record to be fetched
     * @return MarksheetBean containing the marksheet details, or null if not found
     * @throws ApplicationException if any error occurs while fetching the record
     */
    public MarksheetBean findByPk(long pk) throws ApplicationException {

        StringBuffer sql = new StringBuffer("select * from st_marksheet where id = ?");
        MarksheetBean bean = null;
        Connection conn = null;

        try {
            conn = JdbcDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            pstmt.setLong(1, pk);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                bean = new MarksheetBean();
                bean.setId(rs.getLong(1));
                bean.setRollno(rs.getString(2));
                bean.setStudentId(rs.getLong(3));
                bean.setName(rs.getString(4));
                bean.setPhysics(rs.getInt(5));
                bean.setChemistry(rs.getInt(6));
                bean.setMaths(rs.getInt(7));
                bean.setCreatedBy(rs.getString(8));
                bean.setModifiedBy(rs.getString(9));
                bean.setCreatedDateTime(rs.getTimestamp(10));
                bean.setModifiedDateTime(rs.getTimestamp(11));
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            throw new ApplicationException("Exception in getting marksheet by pk");
        } finally {
            JdbcDataSource.closeConnection(conn);
        }
        return bean;
    }

    /**
     * Finds and returns a Marksheet record from the ST_MARKSHEET table based
     * on the given roll number.
     *
     * @param rollNo the roll number of the marksheet record to be fetched
     * @return MarksheetBean containing the marksheet details, or null if not found
     * @throws ApplicationException if any error occurs while fetching the record
     */
    public MarksheetBean findByRollNo(String rollNo) throws ApplicationException {

        StringBuffer sql = new StringBuffer("select * from st_marksheet where roll_no = ?");
        MarksheetBean bean = null;
        Connection conn = null;

        try {
            conn = JdbcDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            pstmt.setString(1, rollNo);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                bean = new MarksheetBean();
                bean.setId(rs.getLong(1));
                bean.setRollno(rs.getString(2));
                bean.setStudentId(rs.getLong(3));
                bean.setName(rs.getString(4));
                bean.setPhysics(rs.getInt(5));
                bean.setChemistry(rs.getInt(6));
                bean.setMaths(rs.getInt(7));
                bean.setCreatedBy(rs.getString(8));
                bean.setModifiedBy(rs.getString(9));
                bean.setCreatedDateTime(rs.getTimestamp(10));
                bean.setModifiedDateTime(rs.getTimestamp(11));
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            throw new ApplicationException("Exception in getting marksheet by roll no");
        } finally {
            JdbcDataSource.closeConnection(conn);
        }
        return bean;
    }

    /**
     * Searches for Marksheet records in the ST_MARKSHEET table based on the
     * provided search criteria with optional pagination support. If the bean is
     * null, all records are returned. Supports filtering by id, roll number,
     * name, physics marks, chemistry marks, and maths marks.
     *
     * @param bean     the MarksheetBean containing the search criteria, or null
     *                 to fetch all records
     * @param pageNo   the page number for pagination (use 0 for no pagination)
     * @param pageSize the number of records per page (use 0 for no pagination)
     * @return List of MarksheetBean matching the search criteria
     * @throws ApplicationException if any error occurs during the search operation
     */
    public List<MarksheetBean> search(MarksheetBean bean, int pageNo, int pageSize) throws ApplicationException {

        StringBuffer sql = new StringBuffer("select * from st_marksheet where 1=1");

        if (bean != null) {
            if (bean.getId() > 0) {
                sql.append(" and id = " + bean.getId());
            }
            if (bean.getRollno() != null && bean.getRollno().length() > 0) {
                sql.append(" and roll_no like '" + bean.getRollno() + "%'");
            }
            if (bean.getName() != null && bean.getName().length() > 0) {
                sql.append(" and name like '" + bean.getName() + "%'");
            }
            if (bean.getPhysics() > 0) {
                sql.append(" and physics = " + bean.getPhysics());
            }
            if (bean.getChemistry() > 0) {
                sql.append(" and chemistry = " + bean.getChemistry());
            }
            if (bean.getMaths() > 0) {
                sql.append(" and maths = '" + bean.getMaths());
            }
        }

        if (pageSize > 0) {
            pageNo = (pageNo - 1) * pageSize;
            sql.append(" limit " + pageNo + ", " + pageSize);
        }

        ArrayList<MarksheetBean> list = new ArrayList<MarksheetBean>();
        Connection conn = null;
        try {
            conn = JdbcDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                bean = new MarksheetBean();
                bean.setId(rs.getLong(1));
                bean.setRollno(rs.getString(2));
                bean.setStudentId(rs.getLong(3));
                bean.setName(rs.getString(4));
                bean.setPhysics(rs.getInt(5));
                bean.setChemistry(rs.getInt(6));
                bean.setMaths(rs.getInt(7));
                bean.setCreatedBy(rs.getString(8));
                bean.setModifiedBy(rs.getString(9));
                bean.setCreatedDateTime(rs.getTimestamp(10));
                bean.setModifiedDateTime(rs.getTimestamp(11));
                list.add(bean);
            }
            rs.close();
        } catch (Exception e) {
            throw new ApplicationException("Update rollback exception " + e.getMessage());
        } finally {
            JdbcDataSource.closeConnection(conn);
        }
        return list;
    }

    /**
     * Returns a merit list of students who have passed all three subjects by
     * scoring more than 33 marks each in Physics, Chemistry, and Maths. The
     * results are ordered by total marks in descending order and support
     * optional pagination.
     *
     * @param pageNo   the page number for pagination (use 0 for no pagination)
     * @param pageSize the number of records per page (use 0 for no pagination)
     * @return List of MarksheetBean containing the merit list records ordered
     *         by total marks in descending order
     * @throws ApplicationException if any error occurs while fetching the merit list
     */
    public List<MarksheetBean> getMeritList(int pageNo, int pageSize) throws ApplicationException {

        ArrayList<MarksheetBean> list = new ArrayList<MarksheetBean>();
        StringBuffer sql = new StringBuffer(
                "select id, roll_no, name, physics, chemistry, maths, (physics + chemistry + maths) as total from st_marksheet where physics > 33 and chemistry > 33 and maths > 33 order by total desc");

        if (pageSize > 0) {
            pageNo = (pageNo - 1) * pageSize;
            sql.append(" limit " + pageNo + ", " + pageSize);
        }

        Connection conn = null;

        try {
            conn = JdbcDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                MarksheetBean bean = new MarksheetBean();
                bean.setId(rs.getLong(1));
                bean.setRollno(rs.getString(2));
                bean.setName(rs.getString(3));
                bean.setPhysics(rs.getInt(4));
                bean.setChemistry(rs.getInt(5));
                bean.setMaths(rs.getInt(6));
                list.add(bean);
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            throw new ApplicationException("Exception in getting merit list of Marksheet");
        } finally {
            JdbcDataSource.closeConnection(conn);
        }
        return list;
    }
}