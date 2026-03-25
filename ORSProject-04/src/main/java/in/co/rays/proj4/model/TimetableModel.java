package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.protocol.Resultset;

import in.co.rays.proj4.bean.CourseBean;
import in.co.rays.proj4.bean.SubjectBean;
import in.co.rays.proj4.bean.TimetableBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DataBaseException;
import in.co.rays.proj4.util.JdbcDataSource;

public class TimetableModel {

	public Integer nextpk() throws DataBaseException {
		Connection conn = null;

		int pk = 0;

		try {
			conn = JdbcDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("select max(id) from st_timetable");
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				pk = rs.getInt(1);

			}
			rs.close();
			ps.close();

		} catch (Exception e) {
			throw new DataBaseException("Exception : Exception is nextpk" + e.getMessage());
		}
		return pk + 1;

	}

	public long add(TimetableBean bean) throws Exception {
		Connection conn = null;
		int pk = 0;

		CourseModel courseModel = new CourseModel();
		CourseBean CourseBean = courseModel.findBypk(bean.getCourseid());
		bean.setCoursename(bean.getCoursename());

		SubjectModel subjectModel = new SubjectModel();
		SubjectBean subjectBean = subjectModel.findByName(bean.getSubjectname());
		bean.setSubjectname(bean.getSubjectname());

		try {
			pk = nextpk();
			conn = JdbcDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("insert into st_timetable values(?,?,?,?,?,?,?,?,?,?,?,?,?)");
			ps.setInt(1, pk);
			ps.setString(2, bean.getSemester());
			ps.setString(3, bean.getDescription());
			ps.setDate(4, new java.sql.Date(bean.getExamdate().getTime()));
			ps.setString(5, bean.getExamtime());
			ps.setLong(6, bean.getCourseid());
			ps.setString(7, bean.getCoursename());
			ps.setLong(8, bean.getSubjectid());
			ps.setString(9, bean.getSubjectname());
			ps.setString(10, bean.getCreatedBy());
			ps.setString(11, bean.getModifiedBy());
			ps.setTimestamp(12, bean.getCreatedDateTime());
			ps.setTimestamp(13, bean.getModifiedDateTime());
			ps.executeUpdate();

			conn.commit();
			ps.close();

		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Exception is rollback" + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception is add" + e.getMessage());
		} finally {
			JdbcDataSource.closeConnection(conn);
		}
		return pk;

	}

	public void update(TimetableBean bean) throws ApplicationException {

		Connection conn = null;
		CourseModel courseModel = new CourseModel();
		CourseBean courseBean = courseModel.findBypk(bean.getCourseid());
		bean.setCoursename(courseBean.getName());

		SubjectModel subjectModel = new SubjectModel();
		SubjectBean subjectBean = subjectModel.findByPk(bean.getSubjectid());
		bean.setSubjectname(subjectBean.getName());

		try {
			conn = JdbcDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(
					"update st_timetable set semester = ?, description = ?, exam_date = ?, exam_time = ?, course_id = ?, course_name = ?, subject_id = ?, subject_name = ?, created_by = ?, modified_by = ?, created_datetime = ?, modified_datetime = ? where id = ?");
			ps.setString(1, bean.getSemester());
			ps.setString(2, bean.getDescription());
			ps.setDate(3, new java.sql.Date(bean.getExamdate().getTime()));
			ps.setString(4, bean.getExamtime());
			ps.setLong(5, bean.getCourseid());
			ps.setString(6, bean.getCoursename());
			ps.setLong(7, bean.getSubjectid());
			ps.setString(8, bean.getSubjectname());
			ps.setString(9, bean.getCreatedBy());
			ps.setString(10, bean.getModifiedBy());
			ps.setTimestamp(11, bean.getCreatedDateTime());
			ps.setTimestamp(12, bean.getModifiedDateTime());
			ps.setLong(13, bean.getId());
			ps.executeUpdate();

			conn.commit();
			ps.close();
		} catch (Exception e) {
			try {
				conn.rollback();

			} catch (Exception ex) {
				e.printStackTrace();
				throw new ApplicationException("Exception  : Exception rollback" + e.getMessage());

			}
			throw new ApplicationException("Exception : Exception is update" + e.getMessage());

		} finally {
			JdbcDataSource.closeConnection(conn);
		}
	}

	public void delete(TimetableBean bean) throws ApplicationException {
		Connection conn = null;

		try {
			conn = JdbcDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("delete from st_timetable where id ");
			ps.setLong(1, bean.getId());
			ps.executeUpdate();
			conn.commit();
			ps.close();

		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Exception is rollback" + e.getMessage());

			}
			throw new ApplicationException("Exception : Exception is delelet " + e.getMessage());

		} finally {
			JdbcDataSource.closeConnection(conn);
		}

	}

	public TimetableBean findBYpk(long pk) throws ApplicationException {
		Connection conn = null;
		TimetableBean bean = null;
		
		StringBuffer sql = new StringBuffer("select * from st_timetable where id = ?");
		try {
			conn = JdbcDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setLong(1, pk);
			ResultSet rs = ps.executeQuery();
					
					while(rs.next()) {
						bean = new  TimetableBean();
						bean.setId(rs.getLong(1));
						bean.setSemester(rs.getString(2));
						bean.setDescription(rs.getString(3));
						bean.setExamdate(rs.getDate(4));
						bean.setExamtime(rs.getString(5));
						bean.setCourseid(rs.getLong(6));
						bean.setCoursename(rs.getString(7));
						bean.setSubjectid(rs.getLong(8));
						bean.setSubjectname(rs.getString(9));
						bean.setCreatedBy(rs.getString(10));
						bean.setModifiedBy(rs.getString(11));
						bean.setCreatedDateTime(rs.getTimestamp(12));
						bean.setModifiedDateTime(rs.getTimestamp(13));
						
					}
					
					rs.close();
					ps.close();
					
		}catch(Exception e) {
			throw new ApplicationException("Exception : Exception is findbypk" + e.getMessage());
		}finally {
			JdbcDataSource.closeConnection(conn);
		}
		return bean;
		
	}
	
	public TimetableBean checkByCourseName(Long courseId, Date examDate) throws ApplicationException {
		StringBuffer sql = new StringBuffer("select * from st_timetable where course_id = ? and exam_date = ?");
		TimetableBean bean = null;
		Connection conn = null;

		try {
			conn = JdbcDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, courseId);
			pstmt.setDate(2, new java.sql.Date(examDate.getTime()));

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new TimetableBean();
				bean.setId(rs.getLong(1));
				bean.setSemester(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setExamdate(rs.getDate(4));
				bean.setExamtime(rs.getString(5));
				bean.setCourseid(rs.getLong(6));
				bean.setCoursename(rs.getString(7));
				bean.setSubjectid(rs.getLong(8));
				bean.setSubjectname(rs.getString(9));
				bean.setCreatedBy(rs.getString(10));
				bean.setModifiedBy(rs.getString(11));
				bean.setCreatedDateTime(rs.getTimestamp(12));
				bean.setModifiedDateTime(rs.getTimestamp(13));
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in get Timetable");

		} finally {
			JdbcDataSource.closeConnection(conn);
		}
		return bean;
	}

	public TimetableBean checkBySubjectName(Long courseId, Long subjectId, Date examDate) throws ApplicationException {
		StringBuffer sql = new StringBuffer(
				"select * from st_timetable where course_id = ? and subject_id = ? and exam_date = ?");
		TimetableBean bean = null;
		Connection conn = null;

		try {
			conn = JdbcDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, courseId);
			pstmt.setLong(2, subjectId);
			pstmt.setDate(3, new java.sql.Date(examDate.getTime()));

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new TimetableBean();
				bean.setId(rs.getLong(1));
				bean.setSemester(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setExamdate(rs.getDate(4));
				bean.setExamtime(rs.getString(5));
				bean.setCourseid(rs.getLong(6));
				bean.setCoursename(rs.getString(7));
				bean.setSubjectid(rs.getLong(8));
				bean.setSubjectname(rs.getString(9));
				bean.setCreatedBy(rs.getString(10));
				bean.setModifiedBy(rs.getString(11));
				bean.setCreatedDateTime(rs.getTimestamp(12));
				bean.setModifiedDateTime(rs.getTimestamp(13));
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in get Timetable");

		} finally {
			JdbcDataSource.closeConnection(conn);
		}
		return bean;
	}

	public TimetableBean checkBySemester(Long courseId, Long subjectId, String semester, Date examDate)
			throws ApplicationException {
		StringBuffer sql = new StringBuffer(
				"select * from st_timetable where course_id = ? and subject_id = ? and semester = ? and exam_date = ?");
		TimetableBean bean = null;
		Connection conn = null;

		try {
			conn = JdbcDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, courseId);
			pstmt.setLong(2, subjectId);
			pstmt.setString(3, semester);
			pstmt.setDate(4, new java.sql.Date(examDate.getTime()));

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new TimetableBean();
				bean.setId(rs.getLong(1));
				bean.setSemester(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setExamdate(rs.getDate(4));
				bean.setExamtime(rs.getString(5));
				bean.setCourseid(rs.getLong(6));
				bean.setCoursename(rs.getString(7));
				bean.setSubjectid(rs.getLong(8));
				bean.setSubjectname(rs.getString(9));
				bean.setCreatedBy(rs.getString(10));
				bean.setModifiedBy(rs.getString(11));
				bean.setCreatedDateTime(rs.getTimestamp(12));
				bean.setModifiedDateTime(rs.getTimestamp(13));
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in get Timetable");
		} finally {
		}
		return bean;
	}

	public TimetableBean checkByExamTime(Long courseId, Long subjectId, String semester, Date examDate, String examTime,
			String description) throws ApplicationException {
		StringBuffer sql = new StringBuffer(
				"select * from st_timetable where course_id = ? and subject_id = ? and semester = ? and exam_date = ? and exam_time = ? and description = ?");
		TimetableBean bean = null;
		Connection conn = null;

		try {
			conn = JdbcDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, courseId);
			pstmt.setLong(2, subjectId);
			pstmt.setString(3, semester);
			pstmt.setDate(4, new java.sql.Date(examDate.getTime()));
			pstmt.setString(5, examTime);
			pstmt.setString(6, description);

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new TimetableBean();
				bean.setId(rs.getLong(1));
				bean.setSemester(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setExamdate(rs.getDate(4));
				bean.setExamtime(rs.getString(5));
				bean.setCourseid(rs.getLong(6));
				bean.setCoursename(rs.getString(7));
				bean.setSubjectid(rs.getLong(8));
				bean.setSubjectname(rs.getString(9));
				bean.setCreatedBy(rs.getString(10));
				bean.setModifiedBy(rs.getString(11));
				bean.setCreatedDateTime(rs.getTimestamp(12));
				bean.setModifiedDateTime(rs.getTimestamp(13));
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in get Timetable");
		} finally {
			JdbcDataSource.closeConnection(conn);
		}
		return bean;
	}

	public List<TimetableBean> search(TimetableBean bean, int pageNo, int pageSize) throws ApplicationException {
		StringBuffer sql = new StringBuffer("select * from st_timetable where 1=1");

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" and id = " + bean.getId());
			}
			if (bean.getCourseid() > 0) {
				sql.append(" and course_id = " + bean.getCourseid());
			}
			if (bean.getCoursename() != null && bean.getCoursename().length() > 0) {
				sql.append(" and course_name like '" + bean.getCoursename() + "%'");
			}
			if (bean.getSubjectid() > 0) {
				sql.append(" and subject_id = " + bean.getSubjectid());
			}
			if (bean.getSubjectname() != null && bean.getSubjectname().length() > 0) {
				sql.append(" and subject_name like '" + bean.getSubjectname() + "%'");
			}
			if (bean.getSemester() != null && bean.getSemester().length() > 0) {
				sql.append(" and semester like '" + bean.getSemester() + "%'");
			}
			if (bean.getDescription() != null && bean.getDescription().length() > 0) {
				sql.append(" and description like '" + bean.getDescription() + "%'");
			}
			if (bean.getExamdate() != null && bean.getExamdate().getDate() > 0) {
				sql.append(" and exam_date like '" + new java.sql.Date(bean.getExamdate().getTime()) + "%'");
			}
			if (bean.getExamtime() != null && bean.getExamtime().length() > 0) {
				sql.append(" and exam_time like '" + bean.getExamtime() + "%'");
			}
		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + ", " + pageSize);
		}

		ArrayList<TimetableBean> list = new ArrayList<TimetableBean>();
		Connection conn = null;
		try {
			conn = JdbcDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new TimetableBean();
				bean.setId(rs.getLong(1));
				bean.setSemester(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setExamdate(rs.getDate(4));
				bean.setExamtime(rs.getString(5));
				bean.setCourseid(rs.getLong(6));
				bean.setCoursename(rs.getString(7));
				bean.setSubjectid(rs.getLong(8));
				bean.setSubjectname(rs.getString(9));
				bean.setCreatedBy(rs.getString(10));
				bean.setModifiedBy(rs.getString(11));
				bean.setCreatedDateTime(rs.getTimestamp(12));
				bean.setModifiedDateTime(rs.getTimestamp(13));
				list.add(bean);
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in search Timetable");
		} finally {
			JdbcDataSource.closeConnection(conn);
		}
		return list;
	}

}
