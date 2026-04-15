package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.CollegeBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DataBaseException;
import in.co.rays.proj4.exception.DuplicateException;
import in.co.rays.proj4.util.JdbcDataSource;

/**
 * CollegeModel is a Model class that handles database operations for the
 * College entity. It interacts with the ST_COLLEGE table to perform CRUD
 * operations such as add, update, delete, and search.
 *
 * This class also provides utility methods like primary key generation,
 * lookup by primary key, and lookup by college name.
 *
 * @author Ram
 */
public class CollegeModel {

    /**
     * Generates and returns the next available primary key for the ST_COLLEGE table.
     *
     * Queries the maximum existing id from ST_COLLEGE and returns it incremented by 1.
     *
     * @return the next available primary key as an Integer
     * @throws DataBaseException if any database exception occurs while retrieving the max id
     */
    public Integer nextpk() throws DataBaseException {
        Connection conn = null;
        int pk = 0;
        try {
            conn = JdbcDataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement("select max(id) from st_college");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                pk = rs.getInt(1);
            }

            rs.close();
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
            throw new DataBaseException("Exception : Exception is getting pk" + e.getMessage());

        } finally {
            JdbcDataSource.closeConnection(conn);
        }
        return pk + 1;
    }

    /**
     * Adds a new College record to the ST_COLLEGE table.
     *
     * Checks for a duplicate entry by college name before inserting. Generates
     * the next primary key using nextpk() and inserts the CollegeBean data.
     * Rolls back the transaction if an error occurs during insertion.
     *
     * @param bean the CollegeBean containing college details to be added
     * @return the generated primary key of the newly added college record
     * @throws DuplicateException   if a record with the same name already exists
     * @throws ApplicationException if any database exception occurs during add or rollback
     */
    public long add(CollegeBean bean) throws DuplicateException, ApplicationException {
        Connection conn = null;
        int pk = 0;

        CollegeBean beanExist = findByName(bean.getName());

        if (beanExist != null) {
            throw new DuplicateException("Login id already exist");
        }

        try {
            pk = nextpk();
            conn = JdbcDataSource.getConnection();
            conn.setAutoCommit(false);
            PreparedStatement ps = conn.prepareStatement("insert into st_college values(?,?,?,?,?,?,?,?,?,?)");
            ps.setInt(1, pk);
            ps.setString(2, bean.getName());
            ps.setString(3, bean.getAddress());
            ps.setString(4, bean.getState());
            ps.setString(5, bean.getCity());
            ps.setString(6, bean.getPhoneno());
            ps.setString(7, bean.getCreatedBy());
            ps.setString(8, bean.getModifiedBy());
            ps.setTimestamp(9, bean.getCreatedDateTime());
            ps.setTimestamp(10, bean.getModifiedDateTime());
            ps.executeUpdate();
            conn.commit();
            ps.close();

        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new ApplicationException("Exception : add rollback" + ex.getMessage());
            }
            throw new ApplicationException("Exception : Exception add user");

        } finally {
            JdbcDataSource.closeConnection(conn);
        }
        return pk;
    }

    /**
     * Updates an existing College record in the ST_COLLEGE table.
     *
     * Checks for a duplicate entry by college name before updating. Updates
     * all fields of the record matched by the id in the given CollegeBean.
     * Rolls back the transaction if an error occurs during the update.
     *
     * @param bean the CollegeBean containing updated college details
     * @throws DuplicateException   if a record with the same name already exists
     * @throws ApplicationException if any database exception occurs during the update or rollback
     */
    public void update(CollegeBean bean) throws ApplicationException, DuplicateException {
        Connection conn = null;
        CollegeBean Existbean = findByName(bean.getName());

        if (Existbean != null) {
            throw new DuplicateException("name is already exist ");
        }

        try {
            conn = JdbcDataSource.getConnection();
            conn.setAutoCommit(false);
            PreparedStatement ps = conn.prepareStatement(
                    "update st_college set name = ?, address = ?, state = ?, city = ?, phone_no = ?, created_by = ?, modified_by = ?, created_datetime = ?, modified_datetime = ? where id = ?");
            ps.setString(1, bean.getName());
            ps.setString(2, bean.getAddress());
            ps.setString(3, bean.getState());
            ps.setString(4, bean.getCity());
            ps.setString(5, bean.getPhoneno());
            ps.setString(6, bean.getCreatedBy());
            ps.setString(7, bean.getModifiedBy());
            ps.setTimestamp(8, bean.getCreatedDateTime());
            ps.setTimestamp(9, bean.getModifiedDateTime());
            ps.setLong(10, bean.getId());
            ps.executeUpdate();
            conn.commit();
            ps.close();

        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            throw new ApplicationException("Exception : Exception is getting update" + e.getMessage());

        } finally {
            JdbcDataSource.closeConnection(conn);
        }
    }

    /**
     * Deletes a College record from the ST_COLLEGE table by its id.
     *
     * Executes a delete query matching the id from the given CollegeBean
     * and commits the transaction on success.
     *
     * @param bean the CollegeBean containing the id of the college to be deleted
     * @throws ApplicationException if any database exception occurs during the delete operation
     */
    public void delete(CollegeBean bean) throws Exception {
        Connection conn = null;

        try {
            conn = JdbcDataSource.getConnection();
            conn.setAutoCommit(false);
            PreparedStatement ps = conn.prepareStatement("delete from st_college where id = ?");
            ps.setLong(1, bean.getId());
            ps.executeUpdate();
            conn.commit();
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Exception : Exception is delete" + e.getMessage());

        } finally {
            JdbcDataSource.closeConnection(conn);
        }
    }

    /**
     * Finds and returns a College record from the ST_COLLEGE table by its primary key.
     *
     * Queries the ST_COLLEGE table using the given primary key value and maps
     * the result to a CollegeBean.
     *
     * @param pk the primary key (id) of the college to retrieve
     * @return CollegeBean if a matching record is found, null otherwise
     * @throws ApplicationException if any database exception occurs during the lookup
     */
    public CollegeBean findBypk(long pk) throws ApplicationException {
        Connection conn = null;
        CollegeBean bean = null;

        StringBuffer sql = new StringBuffer("select * from st_college where id = ?");

        try {
            conn = JdbcDataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql.toString());
            ps.setLong(1, pk);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                bean = new CollegeBean();
                bean.setId(rs.getLong(1));
                bean.setName(rs.getString(2));
                bean.setAddress(rs.getString(3));
                bean.setState(rs.getString(4));
                bean.setCity(rs.getString(5));
                bean.setPhoneno(rs.getString(6));
                bean.setCreatedBy(rs.getString(7));
                bean.setModifiedBy(rs.getString(8));
                bean.setCreatedDateTime(rs.getTimestamp(9));
                bean.setModifiedDateTime(rs.getTimestamp(10));
            }

            rs.close();
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Exception : Exception is getting pk" + e.getMessage());

        } finally {
            JdbcDataSource.closeConnection(conn);
        }
        return bean;
    }

    /**
     * Finds and returns a College record from the ST_COLLEGE table by its name.
     *
     * Queries the ST_COLLEGE table using an exact match on the college name
     * and maps the result to a CollegeBean.
     *
     * @param name the name of the college to search for
     * @return CollegeBean if a matching record is found, null otherwise
     * @throws ApplicationException if any database exception occurs during the lookup
     */
    public CollegeBean findByName(String name) throws ApplicationException {
        CollegeBean bean = null;
        Connection conn = null;

        StringBuffer sql = new StringBuffer("select * from st_college where name = ?");

        try {
            conn = JdbcDataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql.toString());
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                bean = new CollegeBean();
                bean.setId(rs.getLong(1));
                bean.setName(rs.getString(2));
                bean.setAddress(rs.getString(3));
                bean.setState(rs.getString(4));
                bean.setCity(rs.getString(5));
                bean.setPhoneno(rs.getString(6));
                bean.setCreatedBy(rs.getString(7));
                bean.setModifiedBy(rs.getString(8));
                bean.setCreatedDateTime(rs.getTimestamp(9));
                bean.setModifiedDateTime(rs.getTimestamp(10));
            }

            rs.close();
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Exception : Exception is getting name" + e.getMessage());

        } finally {
            JdbcDataSource.closeConnection(conn);
        }
        return bean;
    }

    /**
     * Returns a list of all College records from the ST_COLLEGE table.
     *
     * Delegates to search(CollegeBean, int, int) with no filters and no
     * pagination, returning the complete list of colleges.
     *
     * @return a List of all CollegeBean objects from ST_COLLEGE
     * @throws ApplicationException if any database exception occurs during the operation
     */
    public List<CollegeBean> list() throws ApplicationException {
        return search(null, 0, 0);
    }

    /**
     * Searches College records from the ST_COLLEGE table based on the provided
     * filter criteria with pagination support.
     *
     * Dynamically builds a SQL query using non-null fields of the given
     * CollegeBean. Supports filtering by id, name, address, state, city,
     * and phone number. Results are paginated using pageNo and pageSize parameters.
     *
     * @param bean      the CollegeBean containing filter criteria; fields with
     *                  non-null or positive values are included in the WHERE clause
     * @param pageNo    the page number to retrieve (1-based index)
     * @param pageSize  the number of records per page; if 0 or less, pagination is skipped
     * @return a List of CollegeBean objects matching the search criteria
     * @throws ApplicationException if any database exception occurs during the operation
     */
    public List<CollegeBean> search(CollegeBean bean, int pageNo, int pageSize) throws ApplicationException {
        StringBuffer sql = new StringBuffer("select * from st_college where 1 = 1");

        if (bean != null) {
            if (bean.getId() > 0) {
                sql.append(" and id = " + bean.getId());
            }
            if (bean.getName() != null && bean.getName().length() > 0) {
                sql.append(" and name like '" + bean.getName() + "%'");
            }
            if (bean.getAddress() != null && bean.getAddress().length() > 0) {
                sql.append(" and address like '" + bean.getAddress() + "%'");
            }
            if (bean.getState() != null && bean.getState().length() > 0) {
                sql.append(" and state like '" + bean.getState() + "%'");
            }
            if (bean.getCity() != null && bean.getCity().length() > 0) {
                sql.append(" and city like '" + bean.getCity() + "%'");
            }
            if (bean.getPhoneno() != null && bean.getPhoneno().length() > 0) {
                sql.append(" and phone_no = " + bean.getPhoneno());
            }
        }

        if (pageSize > 0) {
            pageNo = (pageNo - 1) * pageSize;
            sql.append(" limit " + pageNo + ", " + pageSize);
        }

        ArrayList<CollegeBean> list = new ArrayList<CollegeBean>();
        Connection conn = null;

        try {
            conn = JdbcDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                bean = new CollegeBean();
                bean.setId(rs.getLong(1));
                bean.setName(rs.getString(2));
                bean.setAddress(rs.getString(3));
                bean.setState(rs.getString(4));
                bean.setCity(rs.getString(5));
                bean.setPhoneno(rs.getString(6));
                bean.setCreatedBy(rs.getString(7));
                bean.setModifiedBy(rs.getString(8));
                bean.setCreatedDateTime(rs.getTimestamp(9));
                bean.setModifiedDateTime(rs.getTimestamp(10));
                list.add(bean);
            }
            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception : Exception in search college" + e.getMessage());

        } finally {
            JdbcDataSource.closeConnection(conn);
        }
        return list;
    }
}