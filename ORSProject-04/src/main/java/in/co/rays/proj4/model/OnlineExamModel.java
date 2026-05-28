package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.OnlineExamBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateException;
import in.co.rays.proj4.util.JdbcDataSource;

public class OnlineExamModel {
	public Integer nextpk() {
		Connection conn = null;
		int pk = 0;

		try {
			PreparedStatement ps = conn.prepareStatement("select max(id) from online_exam");
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				OnlineExamBean bean = new OnlineExamBean();
				rs.close();
				ps.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} finally {
			JdbcDataSource.closeConnection(conn);
		}

		return pk + 1;

	}

	public long add(OnlineExamBean bean) throws ApplicationException, DuplicateException {

		Connection conn = null;
		int pk = 0;

		OnlineExamBean existBean = findBypk(bean.getId());

		if (existBean != null) {
			throw new DuplicateException("VoiceAssistant login ID Invalid");
		}

		try {
			pk = nextpk();
			conn = JdbcDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("insert into online_exam values(?,?,?,?,?,?,?,?,?)");

			ps.setInt(1, pk);
			ps.setString(2, bean.getSubjectName());
			ps.setInt(3, bean.getTotalMarks());
			ps.setInt(4, bean.getObtainedMarks());
			ps.setString(5, bean.getResult());
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
			throw new ApplicationException("Exception : Exception in add online_exam" + e.getMessage());
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
	public void update(OnlineExamBean bean) throws ApplicationException, DuplicateException {

		Connection conn = null;

		try {
			conn = JdbcDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(
					"update online_exam set subjectname = ?, totalmarks = ?, obtainedmarks = ?, result = ?, created_by = ?, modified_by = ?, created_datetime = ?, modified_datetime = ? where id = ?");

			ps.setString(1, bean.getSubjectName());
			ps.setInt(2, bean.getTotalMarks());
			ps.setInt(3, bean.getObtainedMarks());
			ps.setString(4, bean.getResult());
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
			throw new ApplicationException("Exception : exception in add online_exam" + e.getMessage());
		} finally {
			JdbcDataSource.closeConnection(conn);
		}
	}

	public void delete(OnlineExamBean bean) throws ApplicationException, DuplicateException {

		Connection conn = null;

		try {
			conn = JdbcDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("delete from online_exam where id = ?");
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
			throw new ApplicationException("Exception : exception in delete online_exam" + e.getMessage());
		} finally {
			JdbcDataSource.closeConnection(conn);
		}
	}

	public OnlineExamBean findBypk(long pk) throws ApplicationException {
		OnlineExamBean bean = null;
		Connection conn = null;

		StringBuffer sql = new StringBuffer("select * from online_exam where id = ?");

		try {
			conn = JdbcDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setLong(1, pk);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				bean = new OnlineExamBean();
				bean.setId(rs.getLong(1));
				bean.setSubjectName(rs.getString(1));
				bean.setTotalMarks(rs.getInt(2));
				bean.setObtainedMarks(rs.getInt(3));
				bean.setResult(rs.getString(5));
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
	public List<OnlineExamBean> list() throws ApplicationException {
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
	public List<OnlineExamBean> search(OnlineExamBean bean, int pageNo, int pageSize) throws ApplicationException {
		StringBuffer sql = new StringBuffer("select * from voice_assistant where 1=1");

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" and id = " + bean.getId());
			}
			if (bean.getSubjectName() != null && bean.getSubjectName().length() > 0) {
				sql.append(" and subjectname like '" + bean.getSubjectName() + "%'");
			}
			if (bean.getTotalMarks() > 0) {
				sql.append(" and totalmarks '" + bean.getTotalMarks());
			}
			if (bean.getObtainedMarks() > 0) {
				sql.append(" and '" + bean.getObtainedMarks());
			}

			if (bean.getResult() != null && bean.getResult().length() > 0) {
				sql.append(" and result like '" + bean.getResult() + "%'");
			}

		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + ", " + pageSize);
		}

		Connection conn = null;
		ArrayList<OnlineExamBean> list = new ArrayList<OnlineExamBean>();

		try {
			conn = JdbcDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				bean = new OnlineExamBean();
				bean.setId(rs.getLong(1));
				bean.setSubjectName(rs.getString(1));
				bean.setTotalMarks(rs.getInt(2));
				bean.setObtainedMarks(rs.getInt(3));
				bean.setResult(rs.getString(5));
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
			throw new ApplicationException("Exception search online_exam" + e.getMessage());
		} finally {
			JdbcDataSource.closeConnection(conn);
		}
		return list;
	}

}
