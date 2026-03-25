package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.CourseBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DataBaseException;
import in.co.rays.proj4.exception.DuplicateException;
import in.co.rays.proj4.util.JdbcDataSource;

public class CourseModel {

	public Integer nextpk() throws DataBaseException {
		Connection conn = null;
		int pk = 0;

		try {
			conn = JdbcDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("select max(id) from st_course");
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				pk = rs.getInt(1);

			}

			rs.close();
			ps.close();

		} catch (Exception e) {
			e.printStackTrace();
			throw new DataBaseException("Exception : Exception in getting pk" + e.getMessage());

		} finally {
			JdbcDataSource.closeConnection(conn);
		}

		return pk + 1;

	}

	public long add(CourseBean bean) throws Exception {
		Connection conn = null;
		int pk = 0;
		CourseBean existbean = findBypk(bean.getId());

		if (existbean != null) {
			throw new DuplicateException("id is already exist");

		}

		try {
			pk = nextpk();
			conn = JdbcDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("insert into st_course values(?,?, ?,?,?,?,?,?)");
			ps.setLong(1, pk);
			ps.setString(2, bean.getName());
			ps.setString(3, bean.getDuration());
			ps.setString(4, bean.getDescription());
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
				
			}catch(Exception ex) {
				throw new ApplicationException("Exception is rollback" + ex.getMessage());
				
			}
			throw new ApplicationException("Exception : Exception is findbypk" + e.getMessage());
		}
		return pk;

	}

	public void update(CourseBean bean) throws ApplicationException, DuplicateException {

		Connection conn = null;
		CourseBean existbean = findByName(bean.getName());

		if (existbean != null) {
			throw new DuplicateException("id is already exist");
		}

		try {
			conn = JdbcDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(
					"update st_course set name = ?, duration = ?, description = ?, created_by = ?, modified_by = ?, created_datetime = ?, modified_datetime = ? where id = ?");
			ps.setString(1, bean.getName());
			ps.setString(2, bean.getDuration());
			ps.setString(3, bean.getDescription());
			ps.setString(4, bean.getCreatedBy());
			ps.setString(5, bean.getModifiedBy());
			ps.setTimestamp(6, bean.getCreatedDateTime());
			ps.setTimestamp(7, bean.getModifiedDateTime());
			ps.setLong(8, bean.getId());
			 ps.executeUpdate();
			conn.commit();
			ps.close();

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception  : Exception is getting name" + e.getMessage());

		} finally {
			JdbcDataSource.closeConnection(conn);
		}
	}

	public void delete(CourseBean bean) throws ApplicationException {

		Connection conn = null;

		try {
			conn = JdbcDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("delete from st_course where id = ?");
			ps.setLong(1, bean.getId());
			long i = ps.executeUpdate();

			conn.commit();
			ps.close();

		} catch (Exception e) {
			e.printStackTrace();

			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Exception is rollback " + e.getMessage());

			}

			throw new ApplicationException("Exception : Exception is delete");

		} finally {
			JdbcDataSource.closeConnection(conn);
		}
	}

	public CourseBean findBypk(long pk) throws ApplicationException {
		CourseBean bean = null;
		Connection conn = null;
		
		StringBuffer sql = new StringBuffer("select * from st_course where id = ?");

		try {
			conn = JdbcDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setLong(1, pk);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				bean = new CourseBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDuration(rs.getString(3));
				bean.setDescription(rs.getString(4));
				bean.setCreatedBy(rs.getString(5));
				bean.setModifiedBy(rs.getString(6));
				bean.setCreatedDateTime(rs.getTimestamp(7));
				bean.setModifiedDateTime(rs.getTimestamp(8));
			}
			
			rs.close();
			ps.close();
			
		} catch(Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception is findbypk");

		} finally {
			JdbcDataSource.closeConnection(conn);
		}
		return bean;
	}

	public CourseBean findByName(String name) throws ApplicationException {
		CourseBean bean = null;
		Connection conn = null;

		try {
			conn = JdbcDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("select * from st_course where name = ?");
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				bean = new CourseBean();
				bean.setId(rs.getLong(1));
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDuration(rs.getString(3));
				bean.setDescription(rs.getString(4));
				bean.setCreatedBy(rs.getString(5));
				bean.setModifiedBy(rs.getString(6));
				bean.setCreatedDateTime(rs.getTimestamp(7));
				bean.setModifiedDateTime(rs.getTimestamp(8));
			}
			
			rs.close();
			ps.close();

		} catch (Exception e) {
			e.printStackTrace();

			throw new ApplicationException("Exception : Exception is findbyname");

		} finally {
			JdbcDataSource.closeConnection(conn);
		}
		return bean;
	}

	public List<CourseBean> search(CourseBean bean) {
		return null;

	}


public List<CourseBean> list() throws ApplicationException {
	return search(null, 0, 0);
}

public List<CourseBean> search(CourseBean bean, int pageNo, int pageSize) throws ApplicationException {
	StringBuffer sql = new StringBuffer("select * from st_course where 1=1");

	if (bean != null) {
		if (bean.getId() > 0) {
			sql.append(" and id = " + bean.getId());
		}
		if (bean.getName() != null && bean.getName().length() > 0) {
			sql.append(" and name like '" + bean.getName() + "%'");
		}
		if (bean.getDuration() != null && bean.getDuration().length() > 0) {
			sql.append(" and duration like '" + bean.getDuration() + "%'");
		}
		if (bean.getDescription() != null && bean.getDescription().length() > 0) {
			sql.append(" and description like '" + bean.getDescription() + "%'");
		}
	}

	if (pageSize > 0) {
		pageNo = (pageNo - 1) * pageSize;
		sql.append(" limit " + pageNo + ", " + pageSize);
	}

	List<CourseBean> list = new ArrayList<CourseBean>();
	Connection conn = null;
	try {
		conn = JdbcDataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			bean = new CourseBean();
			bean.setId(rs.getLong(1));
			bean.setName(rs.getString(2));
			bean.setDuration(rs.getString(3));
			bean.setDescription(rs.getString(4));
			bean.setCreatedBy(rs.getString(5));
			bean.setModifiedBy(rs.getString(6));
			bean.setCreatedDateTime(rs.getTimestamp(7));
			bean.setModifiedDateTime(rs.getTimestamp(8));
			list.add(bean);
		}
		rs.close();
		pstmt.close();
	} catch (Exception e) {
		throw new ApplicationException("Exception : Exception in search Course");
	} finally {
		JdbcDataSource.closeConnection(conn);
	}
	return list;
}
}
