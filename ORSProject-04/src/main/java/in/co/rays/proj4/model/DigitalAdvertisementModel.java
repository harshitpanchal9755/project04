package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.DigitalAdvertisementBean;
import in.co.rays.proj4.bean.RoleBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DataBaseException;
import in.co.rays.proj4.exception.DuplicateException;
import in.co.rays.proj4.util.JdbcDataSource;

public class DigitalAdvertisementModel {

	public Integer nextpk() throws DataBaseException {
		Connection conn = null;
		int pk = 0;

		try {
			conn = JdbcDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("select max(id) from digital_advertisemen");
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

	public long add(DigitalAdvertisementBean bean) throws ApplicationException, DuplicateException {

		Connection conn = null;
		int pk = 0;

		DigitalAdvertisementBean existBean = findBypk(bean.getId());

		if (existBean != null) {
			throw new DuplicateException("role already exist");
		}

		try {
			pk = nextpk();
			conn = JdbcDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("insert into st_role values(?,?,?,?,?,?,?)");

			ps.setInt(1, pk);
			ps.setString(2, bean.getAdTitle());
			ps.setString(3, bean.getTargetAudience());
			ps.setString(4, bean.getImpressions());
			ps.setString(5, bean.getClickRate());
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
	public void update(DigitalAdvertisementBean bean) throws ApplicationException, DuplicateException {

		Connection conn = null;

		try {
			conn = JdbcDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(
					"update digital_advertisement set adTitle = ?, targetAuidence = ?, impressions = ?, clickRate = ?, created_by = ?, modified_by = ?, created_datetime = ?, modified_datetime = ? where id = ?");

			ps.setString(1, bean.getAdTitle());
			ps.setString(2, bean.getTargetAudience());
			ps.setString(3, bean.getImpressions());
			ps.setString(4, bean.getClickRate());
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
	public void delete(DigitalAdvertisementBean bean) throws ApplicationException, DuplicateException {

		Connection conn = null;

		try {
			conn = JdbcDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("delete from digital_advertisement where id = ?");
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
	public DigitalAdvertisementBean findBypk(long pk) throws ApplicationException {
		DigitalAdvertisementBean bean = null;
		Connection conn = null;

		StringBuffer sql = new StringBuffer("select * from st_role where id = ?");

		try {
			conn = JdbcDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setLong(1, pk);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				bean = new DigitalAdvertisementBean();
				bean.setId(rs.getLong(1));
				bean.setAdTitle(rs.getString(2));
				bean.setTargetAudience(rs.getString(3));
				bean.setImpressions(rs.getString(4));
				bean.setClickRate(rs.getString(5));
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

	public List<DigitalAdvertisementBean> list() throws ApplicationException {
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
	public List<DigitalAdvertisementBean> search(DigitalAdvertisementBean bean, int pageNo, int pageSize)
			throws ApplicationException {
		StringBuffer sql = new StringBuffer("select * from st_role where 1=1");

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" and id = " + bean.getId());
			}
			if (bean.getAdTitle() != null && bean.getAdTitle().length() > 0) {
				sql.append(" and adTitle like '" + bean.getAdTitle() + "%'");
			}
			if (bean.getTargetAudience() != null && bean.getTargetAudience().length() > 0) {
				sql.append(" and targetAudienece like '" + bean.getTargetAudience() + "%'");
			}
			if (bean.getImpressions() != null && bean.getImpressions().length() > 0) {
				sql.append(" and impressions like '" + bean.getImpressions() + "%'");
			}
			if (bean.getClickRate() != null && bean.getClickRate().length() > 0) {
				sql.append(" and clickRate like '" + bean.getClickRate() + "%'");
			}
		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + ", " + pageSize);
		}

		Connection conn = null;
		ArrayList<DigitalAdvertisementBean> list = new ArrayList<DigitalAdvertisementBean>();

		try {
			conn = JdbcDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				bean = new DigitalAdvertisementBean();
				bean.setId(rs.getLong(1));
				bean.setAdTitle(rs.getString(2));
				bean.setTargetAudience(rs.getString(3));
				bean.setImpressions(rs.getString(4));
				bean.setClickRate(rs.getString(5));
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
