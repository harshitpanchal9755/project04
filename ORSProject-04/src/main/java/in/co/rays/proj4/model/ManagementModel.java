package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.ManagementBean;
import in.co.rays.proj4.bean.RoleBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DataBaseException;
import in.co.rays.proj4.exception.DuplicateException;
import in.co.rays.proj4.util.JdbcDataSource;

public class ManagementModel {

	public Integer nextpk() throws DataBaseException {
		Connection conn = null;
		int pk = 1;

		try {
			conn = JdbcDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("select max(id) from management");
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				pk = rs.getInt(1);

			}

			rs.close();
			ps.close();

		} catch (Exception e) {
			e.printStackTrace();

			throw new DataBaseException("Exception is nextpk()");

		} finally {
			JdbcDataSource.closeConnection(conn);
		}
		return pk + 1;

	}

	public long add(ManagementBean bean) throws DuplicateException, ApplicationException {
		int pk = 0;
		Connection conn = null;

		ManagementBean model = findbypk(bean.getId());
		if (model != null) {
			throw new DuplicateException("Exception is findbypk()");

		}
		try {
			pk = nextpk();
			conn = JdbcDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("insert into management values(?,?,?,?)");
			ps.setInt(1, pk);
			ps.setString(2, bean.getMenu());
			ps.setString(3, bean.getOrdertrack());
			ps.setString(4, bean.getUsercontrol());
			ps.executeUpdate();

			conn.commit();
			ps.close();

		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();

			} catch (Exception ex) {
				throw new ApplicationException("Exdeption is rollback()");

			}

		} finally {
			JdbcDataSource.closeConnection(conn);

		}
		return pk;

	}

	public void update(ManagementBean bean) throws ApplicationException {
		Connection conn = null;

		try {
			conn = JdbcDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn
					.prepareStatement("update management set menu = ?, ordertrack = ?, usercontrol = ? where id = ?");
			ps.setString(1, bean.getMenu());
			ps.setString(2, bean.getOrdertrack());
			ps.setString(3, bean.getUsercontrol());
			ps.setLong(4, bean.getId());
			ps.executeUpdate();
			conn.commit();
			ps.close();

		} catch (Exception e) {
			e.printStackTrace();

			try {
				conn.rollback();

			} catch (Exception ex) {
				throw new ApplicationException("Exception is rollback()" + e.getMessage());

			} finally {
				JdbcDataSource.closeConnection(conn);
			}
		}
	}

	public void delete(ManagementBean bean) throws ApplicationException {
		Connection conn = null;

		try {
			conn = JdbcDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("delete from management where id = ?");
			ps.setLong(1, bean.getId());
			ps.executeUpdate();
			conn.commit();
			ps.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception is Delete");

		} finally {
			JdbcDataSource.closeConnection(conn);

		}
	}

	public ManagementBean findbypk(long pk) throws ApplicationException {
		Connection conn = null;
		ManagementBean bean = null;

		StringBuffer sql = new StringBuffer("select * from management where id = ?");
		try {
			conn = JdbcDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setLong(1, pk);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				bean = new ManagementBean();

				bean.setId(rs.getLong(1));
				bean.setMenu(rs.getString(2));
				bean.setOrdertrack(rs.getString(3));
				bean.setUsercontrol(rs.getString(4));

			}
			rs.close();
			ps.close();

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception is findby pk");
		} finally {
			JdbcDataSource.closeConnection(conn);
		}
		return bean;
	}

	public List<ManagementBean> list() throws ApplicationException {
		return search(null, 0, 0);
	}

	public List<ManagementBean> search(ManagementBean bean, int pageNo, int pageSize) throws ApplicationException {
		StringBuffer sql = new StringBuffer("select * from st_role where 1=1");

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" and id = " + bean.getId());
			}
			if (bean.getMenu() != null && bean.getMenu().length() > 0) {
				sql.append(" and menu like '" + bean.getMenu() + "%'");
			}
			if (bean.getOrdertrack() != null && bean.getOrdertrack().length() > 0) {
				sql.append(" and ordertrack like '" + bean.getOrdertrack() + "%'");
			}
		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + ", " + pageSize);
		}

		Connection conn = null;
		ArrayList<ManagementBean> list = new ArrayList<ManagementBean>();

		try {
			conn = JdbcDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				bean = new ManagementBean();
				bean.setId(rs.getLong(1));
				bean.setMenu(rs.getString(2));
				bean.setOrdertrack(rs.getNString(3));
				bean.setUsercontrol(rs.getString(4));
				
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
