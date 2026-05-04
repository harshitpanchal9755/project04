package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.GiftcardBean;
import in.co.rays.proj4.bean.LoginAttemptBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DataBaseException;
import in.co.rays.proj4.exception.DuplicateException;
import in.co.rays.proj4.util.JdbcDataSource;

public class LoginAttemptModel {

	public int nextpk() throws DataBaseException {
		Connection conn = null;
		int pk = 0;

		try {
			conn = JdbcDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("select max(id) from login_attempt");
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				pk = rs.getInt(1);

			}

			rs.close();
			ps.close();

		} catch (Exception e) {
			e.printStackTrace();
			throw new DataBaseException("Exception : Exception is nextpk" + e.getMessage());
		}

		return pk + 1;

	}

	public long add(LoginAttemptBean bean) throws ApplicationException, DuplicateException {
		Connection conn = null;
		int pk = 0;

		LoginAttemptBean Existbean = findBypk(bean.getId());
		if (Existbean != null) {
			throw new DuplicateException("id is aliready exist");

		}

		try {
			pk = nextpk();
			conn = JdbcDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("insert into login_attempt values(?,?,?,?,?)");
			ps.setInt(1, pk);
			ps.setString(2, bean.getAttemptcode());
			ps.setString(3, bean.getUsername());
			ps.setDate(4, new java.sql.Date(bean.getAttempttime().getTime()));
			ps.setString(5, bean.getStatus());
			ps.executeUpdate();
			conn.commit();
			ps.close();

		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception is rollback" + ex.getMessage());

			}
			throw new ApplicationException("Exception is add giftcard" + e.getMessage());
		} finally {
			JdbcDataSource.closeConnection(conn);
		}
		return pk;

	}

	public void update(LoginAttemptBean bean) throws ApplicationException {
		Connection conn = null;

		LoginAttemptBean CodeBean = findBycode(bean.getAttemptcode());

		try {
			conn = JdbcDataSource.getConnection();

			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(
					"update login_attempt set attemptCode = ?, userName = ?, attemptTime = ?, status = ? where id = ?");
			ps.setString(1, bean.getAttemptcode());
			ps.setDate(2, new java.sql.Date(bean.getAttempttime().getTime()));
			ps.setString(3, bean.getUsername());
			ps.setString(4, bean.getStatus());
			ps.setLong(5, bean.getId());
			ps.executeUpdate();
			conn.commit();
			ps.close();

		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();

			} catch (Exception ex) {
				throw new ApplicationException("Exception is rollback" + e.getMessage());

			}
			throw new ApplicationException("Exception is update");

		} finally {
			JdbcDataSource.closeConnection(conn);
		}

	}

	public void delete(GiftcardBean bean) throws ApplicationException {
		Connection conn = null;
		try {
			conn = JdbcDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("delete from login_attempt where id = ?");
			ps.setLong(1, bean.getId());
			ps.executeUpdate();
			ps.close();

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception is delete" + e.getMessage());

		} finally {
			JdbcDataSource.closeConnection(conn);
		}

	}

	public LoginAttemptBean findBypk(long pk) throws ApplicationException {
		Connection conn = null;
		LoginAttemptBean bean = null;

		StringBuffer sql = new StringBuffer("select * from login_attempt where id = ?");

		try {
			conn = JdbcDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setLong(1, pk);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				bean = new LoginAttemptBean();
				bean.setId(rs.getLong(1));
				bean.setAttemptcode(rs.getString(2));
				bean.setAttempttime(new Timestamp(3));
				bean.setUsername(rs.getString(4));
				bean.setStatus(rs.getString(5));
			}

			rs.close();
			ps.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception is finkbypk" + e.getMessage());
		} finally {
			JdbcDataSource.closeConnection(conn);
		}

		return bean;
	}

//	
	public LoginAttemptBean findBycode(String code) throws ApplicationException {
		LoginAttemptBean bean = null;
		Connection conn = null;

		StringBuffer sql = new StringBuffer("select * from login_attempt where code = ?");

		try {
			conn = JdbcDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setString(1, code);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				bean = new LoginAttemptBean();

				bean = new LoginAttemptBean();
				bean.setId(rs.getLong(1));
				bean.setAttemptcode(rs.getString(2));
				bean.setAttempttime(new Timestamp(3));
				bean.setUsername(rs.getString(4));
				bean.setStatus(rs.getString(5));

			}

			rs.close();
			ps.close();

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception is findbycode" + e.getMessage());
		} finally {
			JdbcDataSource.closeConnection(conn);
		}
		return bean;

	}

	public List<LoginAttemptBean> list() throws Exception {
		return search(null, 0, 0);
	}

	public List<LoginAttemptBean> search(LoginAttemptBean bean, int pageNo, int pageSize) throws ApplicationException {

		StringBuffer sql = new StringBuffer("select * from gift_card where 1=1");

		if (bean != null) {

			if (bean.getId() > 0) {
				sql.append("and id" + bean.getId());
			}
			if (bean.getAttemptcode() != null && bean.getAttemptcode().length() > 0) {
				sql.append("and attemptcode like '" + bean.getAttemptcode() + "%'");
			}
			if (bean.getAttempttime() != null && (bean.getAttempttime().getDate() > 0)) {
				sql.append("and attempttime like '" + bean.getAttempttime() + "%'");
			}
			if (bean.getUsername() != null && bean.getUsername().length() > 0) {
				sql.append("and username like '" + bean.getUsername() + "%'");
			}
			if (bean.getStatus() != null && bean.getStatus().length() > 0) {
				sql.append("and status like '" + bean.getStatus() + "%'");
			}
		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + ", " + pageSize);
		}

		List<LoginAttemptBean> list = new ArrayList<LoginAttemptBean>();
		Connection conn = null;
		try {
			conn = JdbcDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				bean = new LoginAttemptBean();
				bean.setId(rs.getLong(1));
				bean.setAttemptcode(rs.getString(2));
				bean.setAttempttime(rs.getTimestamp(3));
				bean.setUsername(rs.getString(4));
				bean.setStatus(rs.getString(5));
			}

			rs.close();
			ps.close();

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception is list" + e.getMessage());
		}
		return list;
	}

}
