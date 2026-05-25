package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import in.co.rays.proj4.bean.UserSessionBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DataBaseException;
import in.co.rays.proj4.exception.DuplicateException;
import in.co.rays.proj4.util.JdbcDataSource;

public class UserSessionModel {
	
	public Integer nextpk() throws DataBaseException {
		Connection conn = null;
		int pk = 0;

		try {
			conn = JdbcDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("select max(id) from user_session");
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();
			ps.close();

		} catch (Exception e) {
			e.printStackTrace();
			throw new DataBaseException("Exception in getting pk" + e.getMessage());
		} finally {
			JdbcDataSource.closeConnection(conn);
		}
		return pk + 1;
	}

	/**
	 * Adds a new Role record to the ST_ROLE table. Before inserting, it checks if a
	 * role with the same name already exists to prevent duplicates. Rolls back the
	 * transaction if any error occurs during insertion.
	 *
	 * @param bean the RoleBean containing the role details to be added
	 * @return the primary key of the newly added role record
	 * @throws ApplicationException if any error occurs during the add operation or
	 *                              rollback
	 * @throws DuplicateException   if a role with the same name already exists
	 */
	public long add(UserSessionBean bean) throws ApplicationException, DuplicateException {

		Connection conn = null;
		int pk = 0;

		UserSessionBean existBean = findBypk(bean.getId());

		if (existBean != null) {
			throw new DuplicateException("UserSession already exist");
		}

		try {
			pk = nextpk();
			conn = JdbcDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("insert into user_session values(?,?,?,?,?,?,?,?)");

			ps.setInt(1, pk);
			ps.setString(2, bean.getSessionCode());
			ps.setString(3, bean.getUserName());
			ps.setString(4, bean.getStatus());
			ps.setString(5, bean.getCreatedBy());
			ps.setString(6, bean.getModifiedBy());
			ps.setTimestamp(7, bean.getCreatedDateTime());
			ps.setTimestamp(8, bean.getModifiedDateTime());
			ps.executeUpdate();
			conn.commit();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationException("Exception : add rollback exception" + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in add usersession" + e.getMessage());
		} finally {
			JdbcDataSource.closeConnection(conn);
		}
		return pk;
	}

	/**
	 * Updates an existing Role record in the ST_ROLE table. Before updating, it
	 * checks if a role with the same name already exists to prevent duplicates.
	 * Rolls back the transaction if any error occurs during the update.
	 *
	 * @param bean the RoleBean containing the updated role details
	 * @throws ApplicationException if any error occurs during the update operation
	 *                              or rollback
	 * @throws DuplicateException   if a role with the same name already exists
	 */
	public void update(UserSessionBean bean) throws ApplicationException, DuplicateException {

		Connection conn = null;

		try {
			conn = JdbcDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(
					"update user_session set sessioncode = ?, username = ?, status = ?, created_by = ?, modified_by = ?, created_datetime = ?, modified_datetime = ? where id = ?");

			ps.setString(1, bean.getSessionCode());
			ps.setString(2, bean.getUserName());
			ps.setString(3, bean.getStatus());
			ps.setString(4, bean.getCreatedBy());
			ps.setString(5, bean.getModifiedBy());
			ps.setTimestamp(6, bean.getCreatedDateTime());
			ps.setTimestamp(7, bean.getModifiedDateTime());
			ps.setLong(8, bean.getId());
			ps.executeUpdate();
			conn.commit();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : add rollback exception" + ex.getMessage());
			}
			throw new ApplicationException("Exception : exception in add role" + e.getMessage());
		} finally {
			JdbcDataSource.closeConnection(conn);
		}
	}

	/**
	 * Deletes an existing Role record from the ST_ROLE table based on the ID
	 * present in the provided RoleBean. Rolls back the transaction if any error
	 * occurs during deletion.
	 *
	 * @param bean the RoleBean containing the ID of the role to be deleted
	 * @throws ApplicationException if any error occurs during the delete operation
	 *                              or rollback
	 * @throws DuplicateException   if any duplication conflict occurs during the
	 *                              delete operation
	 */
	public void delete(UserSessionBean bean) throws ApplicationException, DuplicateException {

		Connection conn = null;

		try {
			conn = JdbcDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("delete from user_session where id = ?");
			ps.setLong(1, bean.getId());
			long i = ps.executeUpdate();
			System.out.println(i + "record is delete");
			conn.commit();
			ps.close();

		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : delete rollback exception" + ex.getMessage());
			}
			throw new ApplicationException("Exception : exception in delete role" + e.getMessage());
		} finally {
			JdbcDataSource.closeConnection(conn);
		}
	}

	/**
	 * Finds and returns a Role record from the ST_ROLE table based on the given
	 * primary key.
	 *
	 * @param pk the primary key of the role record to be fetched
	 * @return RoleBean containing the role details, or null if not found
	 * @throws ApplicationException if any error occurs while fetching the record
	 */
	public UserSessionBean findBypk(long pk) throws ApplicationException {
		UserSessionBean bean = null;
		Connection conn = null;

		StringBuffer sql = new StringBuffer("select * from user_session where id = ?");

		try {
			conn = JdbcDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setLong(1, pk);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				bean = new UserSessionBean();
				bean.setId(rs.getLong(1));
				bean.setSessionCode(rs.getString(2));
				bean.setUserName(rs.getString(3));
				bean.setStatus(rs.getString(4));
				bean.setCreatedBy(rs.getString(5));
				bean.setModifiedBy(rs.getString(6));
				bean.setCreatedDateTime(rs.getTimestamp(7));
				bean.setModifiedDateTime(rs.getTimestamp(8));
			}

			rs.close();
			ps.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in getting user pk");
		} finally {
			JdbcDataSource.closeConnection(conn);
		}
		return bean;
	}

	

	/**
	 * Returns a list of all Role records from the ST_ROLE table without any filters
	 * or pagination.
	 *
	 * @return List of RoleBean containing all role records
	 * @throws ApplicationException if any error occurs while fetching the records
	 */
	public List<UserSessionBean> list() throws ApplicationException {
		return search(null, 0, 0);
	}

	/**
	 * Searches for Role records in the ST_ROLE table based on the provided search
	 * criteria with optional pagination support. If the bean is null, all records
	 * are returned. Supports filtering by id, name, and description.
	 *
	 * @param bean     the RoleBean containing the search criteria, or null to fetch
	 *                 all records
	 * @param pageNo   the page number for pagination (use 0 for no pagination)
	 * @param pageSize the number of records per page (use 0 for no pagination)
	 * @return List of RoleBean matching the search criteria
	 * @throws ApplicationException if any error occurs during the search operation
	 */
	public List<UserSessionBean> search(UserSessionBean bean, int pageNo, int pageSize) throws ApplicationException {
		StringBuffer sql = new StringBuffer("select * from user_session where 1=1");

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" and id = " + bean.getId());
			}
			if (bean.getSessionCode() != null && bean.getSessionCode().length() > 0) {
				sql.append(" and sessioncode like '" + bean.getSessionCode() + "%'");
			}
			if (bean.getUserName() != null && bean.getUserName().length() > 0) {
				sql.append(" and username like '" + bean.getUserName() + "%'");
			}
			if(bean.getStatus() != null && bean.getStatus().length() >0) {
				sql.append(" and status like '" + bean.getStatus() + "%'");
				
			}
		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + ", " + pageSize);
		}

		Connection conn = null;
		ArrayList<UserSessionBean> list = new ArrayList<UserSessionBean>();

		try {
			conn = JdbcDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				bean = new UserSessionBean();
				bean.setId(rs.getLong(1));
				bean.setSessionCode(rs.getString(2));
				bean.setUserName(rs.getString(3));
				bean.setStatus(rs.getString(4));
				bean.setCreatedBy(rs.getString(5));
				bean.setModifiedBy(rs.getString(6));
				bean.setCreatedDateTime(rs.getTimestamp(7));
				bean.setModifiedDateTime(rs.getTimestamp(8));
				list.add(bean);
			}

			rs.close();
			ps.close();

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception search UserSession" + e.getMessage());
		} finally {
			JdbcDataSource.closeConnection(conn);
		}
		return list;
	}


}
