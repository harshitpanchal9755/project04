package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import in.co.rays.proj4.bean.TimetableBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.util.JdbcDataSource;

public class FaculityModel {

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
			JdbcDataSource.closeConnection(conn);
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
