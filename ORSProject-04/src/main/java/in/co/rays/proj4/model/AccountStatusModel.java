package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.AccountStatusBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DataBaseException;
import in.co.rays.proj4.exception.DuplicateException;
import in.co.rays.proj4.util.JdbcDataSource;

public class AccountStatusModel {
	public Integer nextpk() throws Exception {
		Connection conn = null;
		int pk = 0;

		try {
			conn = JdbcDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("select max(id) from account_status");
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				pk = rs.getInt(1);

			}

			rs.close();
			ps.close();

		} catch (Exception e) {
			throw new DataBaseException("nextpk Exception " + e.getMessage());
		}

		return pk + 1;

	}

	public long add(AccountStatusBean bean) throws SQLException, ApplicationException, DuplicateException {
		Connection conn = null;

		int pk = 0;

		PreparedStatement ps = conn.prepareStatement("insert into account_status values(?,?,?,?,?,?,?,?,?)");
		int i = ps.executeUpdate();

		AccountStatusBean existsbean = findBypk(bean.getId());
		if (existsbean != null) {
			throw new DuplicateException("Email Id already Exists");
		}

		try {
			conn = JdbcDataSource.getConnection();
			conn.setAutoCommit(false);
			ps.setInt(1, pk);
			ps.setInt(2, bean.getCheckAccountStatus());
			ps.setString(3, bean.getActiveAccount());
			ps.setString(4, bean.getDeactivateAccount());
			ps.setString(5, bean.getSuspendAccount());
			ps.setString(6, bean.getCreatedBy());
			ps.setString(7, bean.getModifiedBy());
			ps.setTimestamp(8, bean.getCreatedDateTime());
			ps.setTimestamp(9, bean.getModifiedDateTime());
			ps.executeUpdate();
			conn.commit();

		} catch (Exception e) {
			conn.rollback();

			throw new ApplicationException("Rollback Exeption ");
		} finally {
			JdbcDataSource.closeConnection(conn);
		}

		return pk;

	}

	public void update(AccountStatusBean bean) throws ApplicationException, DuplicateException {

		Connection conn = null;

		try {
			conn = JdbcDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(
					"update account_status set checkaccountstatus = ?, active = ?, deactivate, suspend, created_by = ?, modified_by = ?, created_datetime = ?, modified_datetime = ? where id = ?");

			ps.setInt(1, bean.getCheckAccountStatus());
			ps.setString(2, bean.getActiveAccount());
			ps.setString(3, bean.getDeactivateAccount());
			ps.setString(4, bean.getSuspendAccount());
			ps.setString(5, bean.getCreatedBy());
			ps.setString(6, bean.getModifiedBy());
			ps.setTimestamp(7, bean.getCreatedDateTime());
			ps.setTimestamp(8, bean.getModifiedDateTime());
			ps.setLong(9, bean.getId());
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
	public void delete(AccountStatusBean bean) throws ApplicationException, DuplicateException {

		Connection conn = null;

		try {
			conn = JdbcDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("delete from account_status where id = ?");
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

	private AccountStatusBean findBypk(long id) {
		AccountStatusBean bean = null;
		Connection conn = null;

		try {
			conn = JdbcDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("select * from account_status where id = ?");
			ps.setLong(1, bean.getId());
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				bean = new AccountStatusBean();
				bean.setId(rs.getLong(1));
				bean.setCheckAccountStatus(rs.getInt(2));
				bean.setActiveAccount(rs.getString(3));
				bean.setDeactivateAccount(rs.getString(4));
				bean.setSuspendAccount(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDateTime(rs.getTimestamp(8));
				bean.setModifiedDateTime(rs.getTimestamp(9));
			}
		} catch (Exception e) {
			e.printStackTrace();
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
	public List<AccountStatusBean> list() throws ApplicationException {
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
	public List<AccountStatusBean> search(AccountStatusBean bean, int pageNo, int pageSize) throws ApplicationException {
		StringBuffer sql = new StringBuffer("select * from st_role where 1=1");

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" and id = " + bean.getId());
			}
			if (bean.getCheckAccountStatus() > 0) {
				sql.append(" and checkAccountStatus" + bean.getCheckAccountStatus() );
			}
			
			if(bean.getActiveAccount() != null && bean.getActiveAccount().length() > 0) {
				sql.append("and like '" + bean.getActiveAccount() + "%'");
				
			}
			if (bean.getDeactivateAccount() != null && bean.getDeactivateAccount().length() > 0) {
				sql.append(" and deactiavte like '" + bean.getDeactivateAccount() + "%'");
			}
		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + ", " + pageSize);
		}

		Connection conn = null;
		ArrayList<AccountStatusBean> list = new ArrayList<AccountStatusBean>();

		try {
			conn = JdbcDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				bean = new AccountStatusBean();
				bean.setId(rs.getLong(1));
				bean.setCheckAccountStatus(rs.getInt(2));
				bean.setActiveAccount(rs.getString(3));
				bean.setDeactivateAccount(rs.getString(4));
				bean.setSuspendAccount(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDateTime(rs.getTimestamp(8));
				bean.setModifiedDateTime(rs.getTimestamp(9));
				list.add(bean);
			}

			rs.close();
			ps.close();

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception search role" + e.getMessage());
		} finally {
			JdbcDataSource.closeConnection(conn);
		}
		return list;
	}

}
