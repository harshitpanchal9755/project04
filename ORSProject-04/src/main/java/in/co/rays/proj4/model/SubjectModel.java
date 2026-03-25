package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.CourseBean;
import in.co.rays.proj4.bean.SubjectBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DataBaseException;
import in.co.rays.proj4.exception.DuplicateException;
import in.co.rays.proj4.util.JdbcDataSource;

public class SubjectModel {

	public Integer nextpk() throws DataBaseException {
		Connection conn = null;
		int pk = 0;

		try {
			conn = JdbcDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("select max(id) from st_subject");
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

	public long add(SubjectBean bean) throws ApplicationException, DuplicateException {
		Connection conn = null;
		int pk = 0;
		CourseModel courseModel = new   CourseModel();
		CourseBean coursebean = courseModel.findBypk(bean.getCourseId());
		bean.setCourseName(coursebean.getName());

		SubjectBean duplicatename = findByName(bean.getName());
		if (duplicatename != null) {
			throw new DuplicateException("name is alreadyexist");

		}

		try {
			pk = nextpk();
			conn = JdbcDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("insert into st_subject values(?,?,?,?,?,?,?,?,?)");
			ps.setInt(1, pk);
			ps.setString(2, bean.getName());
			ps.setLong(3, bean.getCourseId());
			ps.setString(4, bean.getCourseName());
			ps.setString(5, bean.getDescription());
			ps.setString(6, bean.getCreatedBy());
			ps.setString(7, bean.getModifiedBy());
			ps.setTimestamp(8, bean.getCreatedDateTime());
			ps.setTimestamp(9, bean.getModifiedDateTime());
			ps.executeUpdate();
			conn.commit(); // End transaction
			ps.close();

		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();

			} catch (Exception ex) {
				e.printStackTrace();
				throw new ApplicationException("Exception : Exception is rollback" + e.getMessage());

			}
			throw new ApplicationException("Exception is finkBypk");

		} finally {
			JdbcDataSource.closeConnection(conn);
		}

		return pk;

	}

	public void update(SubjectBean bean) throws ApplicationException, DuplicateException {
		Connection conn = null;
		CourseModel courseModel = new CourseModel();
		CourseBean courseBean = courseModel.findBypk(bean.getCourseId());
		bean.setCourseName(courseBean.getName());
		try {
			conn = JdbcDataSource.getConnection();

			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn.prepareStatement(
					"update st_subject set name = ?, course_id = ?, course_name = ?, description = ?, created_by = ?, modified_by = ?, created_datetime = ?, modified_datetime = ? where id = ?");
			pstmt.setString(1, bean.getName());
			pstmt.setLong(2, bean.getCourseId());
			pstmt.setString(3, bean.getCourseName());
			pstmt.setString(4, bean.getDescription());
			pstmt.setString(5, bean.getCreatedBy());
			pstmt.setString(6, bean.getModifiedBy());
			pstmt.setTimestamp(7, bean.getCreatedDateTime());
			pstmt.setTimestamp(8, bean.getModifiedDateTime());
			pstmt.setLong(9, bean.getId());
			pstmt.executeUpdate();
			conn.commit(); // End transaction
			pstmt.close();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in updating Subject ");
		} finally {
			JdbcDataSource.closeConnection(conn);
		}
	}

	public void delete(SubjectBean bean) throws ApplicationException {
		Connection conn = null;
		try {
			conn = JdbcDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("delete from st_subject where id = ?");
			pstmt.setLong(1, bean.getId());
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in delete Subject");
		} finally {
			JdbcDataSource.closeConnection(conn);
		}
	}

	public SubjectBean findByPk(long pk) throws ApplicationException {
		SubjectBean bean = null;
		Connection conn = null;

		StringBuffer sql = new StringBuffer("select * from st_subject where id = ?");
		try {
			conn = JdbcDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new SubjectBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setCoursId(rs.getLong(3));
				bean.setCourseName(rs.getString(4));
				bean.setDescription(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDateTime(rs.getTimestamp(8));
				bean.setModifiedDateTime(rs.getTimestamp(9));
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in getting Subject by pk");
		} finally {
			JdbcDataSource.closeConnection(conn);
		}
		return bean;
	}

	public SubjectBean findByName(String name) throws ApplicationException {
		StringBuffer sql = new StringBuffer("select * from st_subject where name = ?");
		SubjectBean bean = null;
		Connection conn = null;
		try {
			conn = JdbcDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				bean = new SubjectBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setCoursId(rs.getLong(3));
				bean.setCourseName(rs.getString(4));
				bean.setDescription(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDateTime(rs.getTimestamp(8));
				bean.setModifiedDateTime(rs.getTimestamp(9));
			}
			rs.close();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception Subject Name" + e.getMessage());
		} finally {
			JdbcDataSource.closeConnection(conn);
		}
		return bean;
	}

	public List<SubjectBean> list() throws ApplicationException {
		return search(null, 0, 0);
	}

	public List<SubjectBean> search(SubjectBean bean, int pageNo, int pageSize) throws ApplicationException {
		StringBuffer sql = new StringBuffer("select * from st_subject where 1=1");

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" and id = " + bean.getId());
			}
			if (bean.getName() != null && bean.getName().length() > 0) {
				sql.append(" and name like '" + bean.getName() + "%'");
			}
			if (bean.getCourseId() > 0) {
				sql.append(" and course_id = " + bean.getCourseId());
			}
			if (bean.getCourseName() != null && bean.getCourseName().length() > 0) {
				sql.append(" and course_name like '" + bean.getCourseName() + "%'");
			}
			if (bean.getDescription() != null && bean.getDescription().length() > 0) {
				sql.append(" and description like '" + bean.getDescription() + "%'");
			}

		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + ", " + pageSize);
		}

		ArrayList<SubjectBean> list = new ArrayList<SubjectBean>();
		Connection conn = null;
		try {
			conn = JdbcDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new SubjectBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setCoursId(rs.getLong(3));
				bean.setCourseName(rs.getString(4));
				bean.setDescription(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDateTime(rs.getTimestamp(8));
				bean.setModifiedDateTime(rs.getTimestamp(9));
				list.add(bean);
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in search Subject");
		} finally {
			JdbcDataSource.closeConnection(conn);
		}
		return list;
	}
}
