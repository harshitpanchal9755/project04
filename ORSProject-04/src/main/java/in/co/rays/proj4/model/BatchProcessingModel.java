package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.BatchProcessingBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DataBaseException;
import in.co.rays.proj4.exception.DuplicateException;
import in.co.rays.proj4.util.JdbcDataSource;

public class BatchProcessingModel {

	public Integer nextpk() throws DataBaseException {
		Connection conn = null;
		int pk = 0;

		try {
			conn = JdbcDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("select max(id) from batch_processing");
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

	public long add(BatchProcessingBean bean) throws ApplicationException, DuplicateException {

		Connection conn = null;
		int pk = 0;
		
		BatchProcessingBean existBean = findBypk(bean.getId());
		
		if(existBean != null) {
			throw new DuplicateException("findbypk is invalid");
		}


		try {
			pk = nextpk();
			conn = JdbcDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("insert into batch_processing values(?,?,?,?,?,?,?,?,?)");

			ps.setInt(1, pk);
			ps.setString(2, bean.getBatchCode());
			ps.setString(3, bean.getBatchName());
			ps.setInt(4, bean.getTotalRecords());
			ps.setString(5, bean.getStatus());
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
				ex.printStackTrace();
				throw new ApplicationException("Exception : add rollback exception" + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in add role" + e.getMessage());
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
	public void update(BatchProcessingBean bean) throws ApplicationException, DuplicateException {

		Connection conn = null;

		try {
			conn = JdbcDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(
					"update batch_processing set batchCode = ?, batchName = ?, totalRecords = ?, status = ?, createdBy = ?, modifiedBy = ?, createdDateTime = ?, modifiedDateTime = ? where id = ?");

			ps.setString(1, bean.getBatchCode());
			ps.setString(2, bean.getBatchName());
			ps.setInt(3, bean.getTotalRecords());
			ps.setString(4, bean.getStatus());
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

	public void delete(BatchProcessingBean bean) throws ApplicationException, DuplicateException {

		Connection conn = null;

		try {
			conn = JdbcDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("delete from batch_processing where id = ?");
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

	public BatchProcessingBean findBypk(long pk) throws ApplicationException {
		BatchProcessingBean bean = null;
		Connection conn = null;

		StringBuffer sql = new StringBuffer("select * from batch_processing where id = ?");

		try {
			conn = JdbcDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setLong(1, pk);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				bean = new BatchProcessingBean();
				bean.setId(rs.getLong(1));
				bean.setBatchCode(rs.getString(2));
				bean.setBatchName(rs.getString(3));
				bean.setTotalRecords(rs.getInt(4));
				bean.setStatus(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDateTime(rs.getTimestamp(8));
				bean.setModifiedDateTime(rs.getTimestamp(9));
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
	 * Finds and returns a Role record from the ST_ROLE table based on the given
	 * role name.
	 *
	 * @param name the name of the role to be fetched
	 * @return RoleBean containing the role details, or null if not found
	 * @throws ApplicationException if any error occurs while fetching the record
	 */
	public BatchProcessingBean findByName(String name) throws ApplicationException {

		BatchProcessingBean bean = null;
		Connection conn = null;

		StringBuffer sql = new StringBuffer("select * from batch_processing where name = ?");

		try {
			conn = JdbcDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				bean = new BatchProcessingBean();
				bean.setId(rs.getLong(1));
				bean.setBatchCode(rs.getString(2));
				bean.setBatchName(rs.getString(3));
				bean.setTotalRecords(rs.getInt(4));
				bean.setStatus(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDateTime(rs.getTimestamp(8));
				bean.setModifiedDateTime(rs.getTimestamp(9));
			}
			rs.close();
			ps.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in getting user by role");
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
	public List<BatchProcessingBean> list() throws ApplicationException {
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
	public List<BatchProcessingBean> search(BatchProcessingBean bean, int pageNo, int pageSize) throws ApplicationException {
		StringBuffer sql = new StringBuffer("select * from batch_processing where 1=1");

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" and id = " + bean.getId());
			}
			if (bean.getBatchCode() != null && bean.getBatchCode().length() > 0) {
				sql.append(" and batchCode like '" + bean.getBatchCode() + "%'");
			}
			if (bean.getBatchName() != null && bean.getBatchName().length() > 0) {
				sql.append(" and batchName like '" + bean.getBatchName() + "%'");
			}
			if (bean.getTotalRecords() > 0) {
				sql.append(" and TotalRecords = " + bean.getTotalRecords());
			}
			if(bean.getStatus() != null && bean.getStatus().length() > 0) {
				sql.append(" and status like '" + bean.getStatus() + "%'");
			}
		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + ", " + pageSize);
		}

		Connection conn = null;
		ArrayList<BatchProcessingBean> list = new ArrayList<BatchProcessingBean>();

		try {
			conn = JdbcDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				bean = new BatchProcessingBean();
				bean.setId(rs.getLong(1));
				bean.setBatchCode(rs.getString(2));
				bean.setBatchName(rs.getString(3));
				bean.setTotalRecords(rs.getInt(4));
				bean.setStatus(rs.getString(5));
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
