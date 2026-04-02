package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.GymTrainerBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DataBaseException;
import in.co.rays.proj4.exception.DuplicateException;
import in.co.rays.proj4.util.JdbcDataSource;

public class GymTrainerModel {

	public int nextpk() throws DataBaseException {
		int pk = 0;
		Connection conn = null;

		try {
			conn = JdbcDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("select max(id) from gym_trainer");
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				pk = rs.getInt(1);

			}

			rs.close();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DataBaseException("Exception is nextpk");

		} finally {
			JdbcDataSource.closeConnection(conn);
		}
		return pk + 1;

	}

	public long add(GymTrainerBean bean) throws ApplicationException, DuplicateException {
		Connection conn = null;
		int pk = 0;
		
		if(bean.getId() != null) {

		GymTrainerBean Existbean = findBypk(bean.getId());
		if (Existbean != null) {
			throw new DuplicateException("Id is already exist");

		}
		}

		try {
			pk = nextpk();
			conn = JdbcDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("insert into gym_trainer values(?,?,?,?)");
			ps.setInt(1, pk);
			ps.setString(2, bean.getName());
			ps.setString(3, bean.getSpecialization());
			ps.setDouble(4, bean.getSalary());
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
			throw new ApplicationException("Exception is add gymtrainer" + e.getMessage());
		} finally {
			JdbcDataSource.closeConnection(conn);
		
}
		return pk;

	}

	public void update(GymTrainerBean bean) throws ApplicationException, DuplicateException {
		Connection conn = null;
		
		GymTrainerBean ExistBean = findByName(bean.getName());
		
		if (ExistBean != null && !ExistBean.getId().equals(ExistBean)) {
			throw new DuplicateException("Gym is already exist");
		}
	

		try {
			conn = JdbcDataSource.getConnection();

			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(
					"update gym_trainer set name = ?, specialization = ?, salary = ? where id = ?");
			ps.setString(1, bean.getName());
			ps.setString(2, bean.getSpecialization());
			ps.setDouble(3, bean.getSalary());
			ps.setLong(4, bean.getId());
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
			throw new ApplicationException("Exception is update" + e.getMessage());

		} finally {
			JdbcDataSource.closeConnection(conn);
		}

	}

	public void delete(GymTrainerBean bean) throws ApplicationException {
		Connection conn = null;
		try {
			conn = JdbcDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("delete from gym_trainer where id = ?");
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

	public GymTrainerBean findBypk(long pk) throws ApplicationException {
		Connection conn = null;
		GymTrainerBean bean = null;

		StringBuffer sql = new StringBuffer("select * from gym_trainer where id = ?");

		try {
			conn = JdbcDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setLong(1, pk);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				bean = new GymTrainerBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setSpecialization(rs.getString(3));
				bean.setSalary(rs.getDouble(4));

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

	public GymTrainerBean findByName(String Name) throws ApplicationException {
		GymTrainerBean bean = null;
		Connection conn = null;

		StringBuffer sql = new StringBuffer("select * from gym_trainer where name = ?");

		try {
			conn = JdbcDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setString(1, Name);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				bean = new GymTrainerBean();

				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setSpecialization(rs.getString(3));
				bean.setSalary(rs.getDouble(4));

			}

			rs.close();
			ps.close();

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception is findbyname" + e.getMessage());
		} finally {
			JdbcDataSource.closeConnection(conn);
		}
		return bean;

	}

	public List<GymTrainerBean> list() throws Exception {
		return search(null, 0, 0);
	}

	public List<GymTrainerBean> search(GymTrainerBean bean, int pageNo, int pageSize) throws ApplicationException {

		StringBuffer sql = new StringBuffer("select * from gymtrainer where 1=1");

		if (bean != null) {

			if (bean.getId() > 0) {
				sql.append("and id" + bean.getId());
			}
			if (bean.getName() != null && bean.getName().length() > 0) {
				sql.append("and trainername like '" + bean.getName() + "%'");
			}
			if (bean.getSpecialization() != null && bean.getSpecialization().length() > 0) {
				sql.append("and specialization like '" + bean.getSpecialization() + "%'");
			}
			if (bean.getSalary() != null && (bean.getSalary() > 0)) {
				sql.append("and salary like '" + bean.getSalary() + "%'");
			}
		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + ", " + pageSize);
		}

		List<GymTrainerBean> list = new ArrayList<GymTrainerBean>();
		Connection conn = null;
		try {
			conn = JdbcDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				bean = new GymTrainerBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setSpecialization(rs.getString(3));
				bean.setSalary(rs.getDouble(4));
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
