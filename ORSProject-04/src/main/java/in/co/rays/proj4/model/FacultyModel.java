package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import in.co.rays.proj4.bean.CollegeBean;
import in.co.rays.proj4.bean.CourseBean;
import in.co.rays.proj4.bean.FacultyBean;
import in.co.rays.proj4.bean.SubjectBean;
import in.co.rays.proj4.bean.TimetableBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DataBaseException;
import in.co.rays.proj4.exception.DuplicateException;
import in.co.rays.proj4.util.JdbcDataSource;

/**
 * FacultyModel is a Model class that handles database operations related to
 * the Timetable entity for faculty-related queries. It interacts with the
 * ST_TIMETABLE table to perform search and validation operations.
 *
 * This class provides methods to check timetable entries based on various
 * criteria such as course name, subject name, semester, and exam time,
 * as well as a paginated search method.
 *
 * @author Harshit
 */
public class FacultyModel {

	public Integer nextPk() throws DataBaseException {
		Connection conn = null;
		int pk = 0;
		try {
			conn = JdbcDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_faculty");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			throw new DataBaseException("Exception : Exception in getting PK");
		} finally {
			JdbcDataSource.closeConnection(conn);
		}
		return pk + 1;
	}

	public long add(FacultyBean bean) throws ApplicationException, DuplicateException {
		Connection conn = null;
		int pk = 0;

		CollegeModel collegeModel = new CollegeModel();
		CollegeBean collegeBean = collegeModel.findBypk(bean.getCollegeid());
		bean.setCollegename(collegeBean.getName());

		CourseModel courseModel = new CourseModel();
		CourseBean courseBean = courseModel.findBypk(bean.getCourseid());
		bean.setCoursename(courseBean.getName());

		SubjectModel subjectModel = new SubjectModel();
		SubjectBean subjectBean = subjectModel.findByPk(bean.getSubjectid());
		bean.setSubjectname(subjectBean.getName());

		FacultyBean existbean = findByEmail(bean.getEmail());

		if (existbean != null) {
			throw new DuplicateException("Email Id already exists");
		}

		try {
			conn = JdbcDataSource.getConnection();
			pk = nextPk();
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn.prepareStatement(
					"insert into st_faculty values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getFirstname());
			pstmt.setString(3, bean.getLastname());
			pstmt.setDate(4, new java.sql.Date(bean.getDob().getTime()));
			pstmt.setString(5, bean.getGender());
			pstmt.setString(6, bean.getMobileno());
			pstmt.setString(7, bean.getEmail());
			pstmt.setLong(8, bean.getCollegeid());
			pstmt.setString(9, bean.getCollegename());
			pstmt.setLong(10, bean.getCourseid());
			pstmt.setString(11, bean.getCoursename());
			pstmt.setLong(12, bean.getSubjectid());
			pstmt.setString(13, bean.getSubjectname());
			pstmt.setString(14, bean.getCreatedBy());
			pstmt.setString(15, bean.getModifiedBy());
			pstmt.setTimestamp(16, bean.getCreatedDateTime());
			pstmt.setTimestamp(17, bean.getModifiedDateTime());
			pstmt.executeUpdate();
			conn.commit(); // End transaction
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in add Faculty");
		} finally {
			JdbcDataSource.closeConnection(conn);
		}
		return pk;
	}

	public void update(FacultyBean bean) throws ApplicationException, DuplicateException {
		Connection conn = null;

		// get College Name
		CollegeModel collegeModel = new CollegeModel();
		CollegeBean collegeBean = collegeModel.findBypk(bean.getCollegeid());
		bean.setCollegename(collegeBean.getName());

		// get Course Name
		CourseModel courseModel = new CourseModel();
		CourseBean courseBean = courseModel.findBypk(bean.getCourseid());
		bean.setCoursename(courseBean.getName());

		// get Subject Name
		SubjectModel subjectModel = new SubjectModel();
		SubjectBean subjectBean = subjectModel.findByPk(bean.getSubjectid());
		bean.setSubjectname(subjectBean.getName());

		FacultyBean beanExist = findByEmail(bean.getEmail());
		if (beanExist != null && !(beanExist.getId() == bean.getId())) {
			throw new DuplicateException("EmailId is already exist");
		}
		try {
			conn = JdbcDataSource.getConnection();

			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(
					"update st_faculty set first_name = ?, last_name = ?, dob = ?, gender = ?, mobile_no = ?, email = ?, college_id = ?, college_name = ?, course_id = ?, course_name = ?, subject_id = ?, subject_name = ?, created_by = ?, modified_by = ?, created_datetime = ?, modified_datetime = ? where id = ?");

			pstmt.setString(1, bean.getFirstname());
			pstmt.setString(2, bean.getLastname());
			pstmt.setDate(3, new java.sql.Date(bean.getDob().getTime()));
			pstmt.setString(4, bean.getGender());
			pstmt.setString(5, bean.getMobileno());
			pstmt.setString(6, bean.getEmail());
			pstmt.setLong(7, bean.getCollegeid());
			pstmt.setString(8, bean.getCollegename());
			pstmt.setLong(9, bean.getCourseid());
			pstmt.setString(10, bean.getCoursename());
			pstmt.setLong(11, bean.getSubjectid());
			pstmt.setString(12, bean.getSubjectname());
			pstmt.setString(13, bean.getCreatedBy());
			pstmt.setString(14, bean.getModifiedBy());
			pstmt.setTimestamp(15, bean.getCreatedDateTime());
			pstmt.setTimestamp(16, bean.getModifiedDateTime());
			pstmt.setLong(17, bean.getId());
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				e.printStackTrace();
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in updating Faculty " + e.getMessage());
		} finally {
			JdbcDataSource.closeConnection(conn);
		}
	}

	public void delete(FacultyBean bean) throws ApplicationException {
		Connection conn = null;
		try {
			conn = JdbcDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("delete from st_faculty where id = ?");
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
			throw new ApplicationException("Exception : Exception in delete Faculty");
		} finally {
			JdbcDataSource.closeConnection(conn);
		}
	}

	public FacultyBean findByPk(long pk) throws ApplicationException {
		StringBuffer sql = new StringBuffer("select * from st_faculty where id = ?");
		FacultyBean bean = null;
		Connection conn = null;
		try {
			conn = JdbcDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new FacultyBean();
				bean.setId(rs.getLong(1));
				bean.setFirstname(rs.getString(2));
				bean.setLastname(rs.getString(3));
				bean.setDob(rs.getDate(4));
				bean.setGender(rs.getString(5));
				bean.setMobileno(rs.getString(6));
				bean.setEmail(rs.getString(7));
				bean.setCollegeid(rs.getLong(8));
				bean.setCollegename(rs.getString(9));
				bean.setCourseid(rs.getLong(10));
				bean.setCoursename(rs.getString(11));
				bean.setSubjectid(rs.getLong(12));
				bean.setSubjectname(rs.getString(13));
				bean.setCreatedBy(rs.getString(14));
				bean.setModifiedBy(rs.getString(15));
				bean.setCreatedDateTime(rs.getTimestamp(16));
				bean.setModifiedDateTime(rs.getTimestamp(17));
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in getting Faculty by pk");
		} finally {
			JdbcDataSource.closeConnection(conn);
		}
		return bean;
	}

	public FacultyBean findByEmail(String email) throws ApplicationException {
		StringBuffer sql = new StringBuffer("select * from st_faculty where email = ?");
		FacultyBean bean = null;
		Connection conn = null;
		System.out.println("sql" + sql);

		try {
			conn = JdbcDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, email);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new FacultyBean();
				bean.setId(rs.getLong(1));
				bean.setFirstname(rs.getString(2));
				bean.setLastname(rs.getString(3));
				bean.setDob(rs.getDate(4));
				bean.setGender(rs.getString(5));
				bean.setMobileno(rs.getString(6));
				bean.setEmail(rs.getString(7));
				bean.setCollegeid(rs.getLong(8));
				bean.setCollegename(rs.getString(9));
				bean.setCourseid(rs.getLong(10));
				bean.setCoursename(rs.getString(11));
				bean.setSubjectid(rs.getLong(12));
				bean.setSubjectname(rs.getString(13));
				bean.setCreatedBy(rs.getString(14));
				bean.setModifiedBy(rs.getString(15));
				bean.setCreatedDateTime(rs.getTimestamp(16));
				bean.setModifiedDateTime(rs.getTimestamp(17));
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in getting Faculty by Email");
		} finally {
			JdbcDataSource.closeConnection(conn);
		}
		return bean;
	}

	public List<FacultyBean> search(FacultyBean bean, int pageNo, int pageSize) throws ApplicationException {
		StringBuffer sql = new StringBuffer("select * from st_faculty where 1=1");

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" and id = " + bean.getId());
			}
			if (bean.getCollegeid() > 0) {
				sql.append(" and college_id = " + bean.getCollegeid());
			}
			if (bean.getSubjectid() > 0) {
				sql.append(" and subject_id = " + bean.getSubjectid());
			}
			if (bean.getCourseid() > 0) {
				sql.append(" and course_id = " + bean.getCourseid());
			}
			if (bean.getFirstname() != null && bean.getFirstname().length() > 0) {
				sql.append(" and firstname like '" + bean.getFirstname() + "%'");
			}
			if (bean.getLastname() != null && bean.getLastname().length() > 0) {
				sql.append(" and lastname like '" + bean.getLastname() + "%'");
			}
			if (bean.getGender() != null && bean.getGender().length() > 0) {
				sql.append(" and gender like '" + bean.getGender() + "%'");
			}
			if (bean.getDob() != null && bean.getDob().getDate() > 0) {
				sql.append(" and dob = ?" + bean.getDob());
			}
			if (bean.getEmail() != null && bean.getEmail().length() > 0) {
				sql.append(" and email like '" + bean.getEmail() + "%'");
			}
			if (bean.getMobileno() != null && bean.getMobileno().length() > 0) {
				sql.append(" and mobileno = ?" + bean.getMobileno());
			}
			if (bean.getCoursename() != null && bean.getCoursename().length() > 0) {
				sql.append(" and coursename like '" + bean.getCoursename() + "%'");
			}
			if (bean.getCollegename() != null && bean.getCollegename().length() > 0) {
				sql.append(" and collegename like '" + bean.getCollegename() + "%'");
			}
			if (bean.getSubjectname() != null && bean.getSubjectname().length() > 0) {
				sql.append(" and subjectname like '" + bean.getSubjectname() + "%'");
			}
		}
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + ", " + pageSize);
		}

		ArrayList<FacultyBean> list = new ArrayList<FacultyBean>();
		Connection conn = null;
		try {
			conn = JdbcDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new FacultyBean();
				bean.setId(rs.getLong(1));
				bean.setFirstname(rs.getString(2));
				bean.setLastname(rs.getString(3));
				bean.setDob(rs.getDate(4));
				bean.setGender(rs.getString(5));
				bean.setMobileno(rs.getString(6));
				bean.setEmail(rs.getString(7));
				bean.setCollegeid(rs.getLong(8));
				bean.setCollegename(rs.getString(9));
				bean.setCourseid(rs.getLong(10));
				bean.setCoursename(rs.getString(11));
				bean.setSubjectid(rs.getLong(12));
				bean.setSubjectname(rs.getString(13));
				bean.setCreatedBy(rs.getString(14));
				bean.setModifiedBy(rs.getString(15));
				bean.setCreatedDateTime(rs.getTimestamp(16));
				bean.setModifiedDateTime(rs.getTimestamp(17));
				list.add(bean);
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in search Faculty");
		} finally {
			JdbcDataSource.closeConnection(conn);
		}
		return list;
	}
}