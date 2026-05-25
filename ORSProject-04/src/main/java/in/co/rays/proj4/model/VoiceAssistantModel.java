package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.BatchProcessingBean;
import in.co.rays.proj4.bean.VoiceAssistantBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DataBaseException;
import in.co.rays.proj4.exception.DuplicateException;
import in.co.rays.proj4.util.JdbcDataSource;

public class VoiceAssistantModel {
	public Integer nextpk() throws DataBaseException {
		Connection conn = null;
		int pk = 0;

		try {
			conn = JdbcDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("select max(id) from voice_assistant");
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

	public long add(VoiceAssistantBean bean) throws ApplicationException, DuplicateException {

		Connection conn = null;
		int pk = 0;

		VoiceAssistantBean existBean = findBypk(bean.getId());

		if (existBean != null) {
			throw new DuplicateException("VoiceAssistant login ID Invalid");
		}

		try {
			pk = nextpk();
			conn = JdbcDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("insert into voice_assistant values(?,?,?,?,?,?,?,?,?)");

			ps.setInt(1, pk);
			ps.setString(2, bean.getUserVoice());
			ps.setString(3, bean.getResponse());
			ps.setString(4, bean.getLanguage());
			ps.setDouble(5, bean.getAccuracy());
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
			throw new ApplicationException("Exception : Exception in add voice_assistant" + e.getMessage());
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
	public void update(VoiceAssistantBean bean) throws ApplicationException, DuplicateException {

		Connection conn = null;

		try {
			conn = JdbcDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(
					"update voice_assistant set uservoice = ?, response = ?, language = ?, accuracy = ?, created_by = ?, modified_by = ?, created_datetime = ?, modified_datetime = ? where id = ?");

			ps.setString(1, bean.getUserVoice());
			ps.setString(2, bean.getResponse());
			ps.setString(3, bean.getLanguage());
			ps.setDouble(4, bean.getAccuracy());
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
			throw new ApplicationException("Exception : exception in add voice_assistant" + e.getMessage());
		} finally {
			JdbcDataSource.closeConnection(conn);
		}
	}

	public void delete(VoiceAssistantBean bean) throws ApplicationException, DuplicateException {

		Connection conn = null;

		try {
			conn = JdbcDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("delete from voice_assistant where id = ?");
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
			throw new ApplicationException("Exception : exception in delete voice_assistant" + e.getMessage());
		} finally {
			JdbcDataSource.closeConnection(conn);
		}
	}

	public VoiceAssistantBean findBypk(long pk) throws ApplicationException {
		VoiceAssistantBean bean = null;
		Connection conn = null;

		StringBuffer sql = new StringBuffer("select * from voice_assistant where id = ?");

		try {
			conn = JdbcDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setLong(1, pk);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				bean = new VoiceAssistantBean();
				bean.setId(rs.getLong(1));
				bean.setUserVoice(rs.getString(2));
				bean.setResponse(rs.getString(3));
				bean.setLanguage(rs.getString(4));
				bean.setAccuracy(rs.getDouble(5));
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
	 * Returns a list of all Role records from the ST_ROLE table without any filters
	 * or pagination.
	 *
	 * @return List of RoleBean containing all role records
	 * @throws ApplicationException if any error occurs while fetching the records
	 */
	public List<VoiceAssistantBean> list() throws ApplicationException {
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
	public List<VoiceAssistantBean> search(VoiceAssistantBean bean, int pageNo, int pageSize)
			throws ApplicationException {
		StringBuffer sql = new StringBuffer("select * from voice_assistant where 1=1");

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" and id = " + bean.getId());
			}
			if (bean.getUserVoice() != null && bean.getUserVoice().length() > 0) {
				sql.append(" and userVoice like '" + bean.getUserVoice() + "%'");
			}
			if (bean.getResponse() != null && bean.getResponse().length() > 0) {
				sql.append(" and response like '" + bean.getResponse() + "%'");
			}
			if (bean.getLanguage() != null && bean.getLanguage().length() > 0) {
				sql.append(" and language like '" + bean.getLanguage() + "%'");
			}

		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + ", " + pageSize);
		}

		Connection conn = null;
		ArrayList<VoiceAssistantBean> list = new ArrayList<VoiceAssistantBean>();

		try {
			conn = JdbcDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				bean = new VoiceAssistantBean();
				bean.setId(rs.getLong(1));
				bean.setUserVoice(rs.getString(2));
				bean.setResponse(rs.getString(3));
				bean.setLanguage(rs.getString(4));
				bean.setAccuracy(rs.getDouble(5));
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
