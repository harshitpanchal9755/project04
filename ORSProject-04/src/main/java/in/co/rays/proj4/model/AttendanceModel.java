package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.xdevapi.Result;

import in.co.rays.proj4.bean.AttendanceBean;
import in.co.rays.proj4.bean.RoleBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DataBaseException;
import in.co.rays.proj4.exception.DuplicateException;
import in.co.rays.proj4.util.JdbcDataSource;

public class AttendanceModel {

	public int nextpk() throws DataBaseException {
		Connection conn = null;
		int pk = 0;

		try {
			conn = JdbcDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("select max(id) from attendance");
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				pk = nextpk();

			}

			rs.close();
			ps.close();

		} catch (Exception e) {
			e.printStackTrace();
			throw new DataBaseException("Exception is nextpk" + e.getMessage());
		}
		return pk + 1;

	}

	public long add(AttendanceBean bean) throws ApplicationException, DuplicateException {
		Connection conn = null;
		int pk = 0;

		AttendanceBean Existbean = findBypk(bean.getId());

		if (Existbean != null) {
			throw new DuplicateException("id is already Exist");

		}

		try {
			pk = nextpk();
			conn = JdbcDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("insert into attendance values(?,?,?,?,?)");
			ps.setInt(1, pk);
			ps.setString(2, bean.getCode());
			ps.setString(3, bean.getEmployeename());
			ps.setDate(4, new java.sql.Date(bean.getAttendanceDate().getTime()));
			ps.setString(5, bean.getStatus());
			ps.executeUpdate();
			conn.commit();
			ps.close();

		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception os rollback" + e.getMessage());
			}
			throw new ApplicationException("Exception is addattendance" + e.getMessage());
		} finally {
			JdbcDataSource.closeConnection(conn);
		}
		return pk;

	}

	public void update(AttendanceBean bean) throws ApplicationException, DuplicateException {
		Connection conn = null;

		AttendanceBean Existbean = findByname(bean.getEmployeename());

		if (Existbean != null) {
			throw new DuplicateException("name is already Exist");

		}

		try {
			conn = JdbcDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(
					"update attendance set attendance_code = ?, employee_name = ?, date = ?, status = ? where id = ?");
			ps.setString(1, bean.getCode());
			ps.setString(2, bean.getEmployeename());
			ps.setDate(3, new java.sql.Date(bean.getAttendanceDate().getTime()));
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
				throw new ApplicationException("Exception os rollback" + e.getMessage());
			}
			throw new ApplicationException("Exception is updateattendance" + e.getMessage());
		} finally {
			JdbcDataSource.closeConnection(conn);
		}

	}

	public void delete(AttendanceBean bean) throws ApplicationException {
		Connection conn = null;

		try {

			conn = JdbcDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("delete from attendance where id = ? ");
			ps.setString(1, bean.getCode());
			ps.executeUpdate();
			conn.commit();
			ps.close();

		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception os rollback" + e.getMessage());
			}
			throw new ApplicationException("Exception is delete attendance" + e.getMessage());
		} finally {
			JdbcDataSource.closeConnection(conn);
		}
	}

	public AttendanceBean findByname(String employeename) throws ApplicationException {
		AttendanceBean bean = null;
		Connection conn = null;
		StringBuffer sql = new StringBuffer("select * from attendance where employee_name = ?");

		try {
			conn = JdbcDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setString(1, employeename);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				bean = new AttendanceBean();
				bean.setId(rs.getLong(1));
				bean.setCode(rs.getString(2));
				bean.setEmployeename(rs.getString(3));
				bean.setDate(new Timestamp(4));
				bean.setStatus(rs.getString(5));

			}

			rs.close();
			ps.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception is findbyname" + e.getMessage());

		} finally {
			JdbcDataSource.closeConnection(conn);
		}
		return bean;
	}

	private AttendanceBean findBypk(long id) throws ApplicationException {
		AttendanceBean bean = null;
		Connection conn = null;
		StringBuffer sql = new StringBuffer("select * from attendance where id = ?");

		try {
			conn = JdbcDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				bean = new AttendanceBean();
				bean.setId(rs.getLong(1));
				bean.setCode(rs.getString(2));
				bean.setEmployeename(rs.getString(3));
				bean.setDate(new Timestamp(4));
				bean.setStatus(rs.getString(5));

			}

			rs.close();
			ps.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception is findbypk" + e.getMessage());

		} finally {
			JdbcDataSource.closeConnection(conn);
		}
		return bean;
	}

	public List<AttendanceBean> list() throws Exception {
		return search(null, 0, 0);

	}

	public List<AttendanceBean> search(AttendanceBean bean, int pageNo, int pageSize) throws Exception {

		StringBuffer sql = new StringBuffer("select * from attendance where 1=1 ");
		if (bean != null) {

			if (bean.getId() > 0) {
				sql.append("and id" + bean.getId());
			}

			if (bean.getCode() != null && bean.getCode().length() > 0) {
				sql.append("and code like '" + bean.getCode() + "%'");

			}

			if (bean.getEmployeename() != null && bean.getEmployeename().length() > 0) {
				sql.append("and employeename like '" + bean.getEmployeename() + "%'");

			}

			if (bean.getStatus() != null && bean.getStatus().length() > 0) {
				sql.append("and status like '" + bean.getStatus() + "%'");
			}

			if (pageNo > 0) {
				pageNo = (pageNo - 1) * pageSize;
				sql.append("limit" + pageNo + "," + pageSize);
			}
		}
		Connection conn = null;
		ArrayList<AttendanceBean> list = new ArrayList<AttendanceBean>();

		try {
			conn = JdbcDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				bean = new AttendanceBean();
				bean.setId(rs.getLong(1));
				bean.setCode(rs.getString(2));
				bean.setEmployeename(rs.getString(3));
				bean.setDate(new Timestamp(4));
				bean.setStatus(rs.getString(5));
				list.add(bean);
			}

			rs.close();
			ps.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception search attendance");

		} finally {
			JdbcDataSource.closeConnection(conn);
		}
		return list;

	}
}
