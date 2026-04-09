package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.RoleBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DataBaseException;
import in.co.rays.proj4.exception.DuplicateException;
import in.co.rays.proj4.util.JdbcDataSource;

public class RoleModel {

	public Integer nextpk() throws DataBaseException {
		Connection conn = null;
		int pk = 0;

		try {
			conn = JdbcDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("select max(id) from st_role");
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();
			ps.close();

		} catch (Exception e) {
			e.printStackTrace();
			throw new DataBaseException("Exception in getting pk" + e.getMessage());
		} finally {
			JdbcDataSource.closeConnection(conn);
		}
		return pk + 1;

	}

	public long add(RoleBean bean) throws ApplicationException, DuplicateException {

		Connection conn = null;
		int pk = 0;

		RoleBean existBean = findByName(bean.getName());

		if (existBean != null) {
			throw new DuplicateException("role already exist");
		}

		try {
			pk = nextpk();
			conn = JdbcDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("insert into st_role values(?,?,?,?,?,?,?)");

			ps.setInt(1, pk);
			ps.setString(2, bean.getName());
			ps.setString(3, bean.getDescription());
			ps.setString(4, bean.getCreatedBy());
			ps.setString(5, bean.getModifiedBy());
			ps.setTimestamp(6, bean.getCreatedDateTime());
			ps.setTimestamp(7, bean.getModifiedDateTime());
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
			throw new ApplicationException("Exception : Exception in add role" + e.getMessage());
		} finally {
			JdbcDataSource.closeConnection(conn);
		}
		return pk;

	}

	public void update(RoleBean bean) throws ApplicationException, DuplicateException {

		Connection conn = null;
		RoleBean existBean = findByName(bean.getName());

		if (existBean != null) {
			throw new DuplicateException("role already exist");
		}

		try {
			conn = JdbcDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(
					"update st_role set name = ?, description = ?, created_by = ?, modified_by = ?, created_datetime = ?, modified_datetime = ? where id = ?");

			ps.setString(1, bean.getName());
			ps.setString(2, bean.getDescription());
			ps.setString(3, bean.getCreatedBy());
			ps.setString(4, bean.getModifiedBy());
			ps.setTimestamp(5, bean.getCreatedDateTime());
			ps.setTimestamp(6, bean.getModifiedDateTime());
			ps.setLong(7, bean.getId());
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

			throw new ApplicationException("Exception : exception in add role" + e.getMessage());
		} finally {
			JdbcDataSource.closeConnection(conn);
		}

	}

	public void delete(RoleBean bean) throws ApplicationException, DuplicateException {

		Connection conn = null;

		try {
			conn = JdbcDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("delete from st_role where id = ?");
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

			throw new ApplicationException("Exception : exception in delete role" + e.getMessage());

		} finally {
			JdbcDataSource.closeConnection(conn);
		}

	}
	
	public RoleBean findBypk(long pk) throws ApplicationException {
		RoleBean bean = null;
		Connection conn = null;
	
		StringBuffer sql = new StringBuffer("select * from st_role where id = ?");
		
		try {
			conn = JdbcDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setLong(1, pk);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
			bean = new RoleBean();
			bean.setId(rs.getLong(1));
			bean.setName(rs.getString(2));
			bean.setDescription(rs.getString(3));
			bean.setCreatedBy(rs.getString(4));
			bean.setModifiedBy(rs.getString(5));
			bean.setCreatedDateTime(rs.getTimestamp(6));
			bean.setModifiedDateTime(rs.getTimestamp(7));

		}
		
			rs.close();
			ps.close();
			
		}catch(Exception e) {
			throw new ApplicationException("Exception : Exception in getting user pk");
			
		}finally {
			JdbcDataSource.closeConnection(conn);

		}
		return bean;	
	}
	
	public RoleBean findByName(String name) throws ApplicationException {
		
		RoleBean bean = null;
		Connection conn = null;
		
		StringBuffer sql = new StringBuffer("select * from st_role where name = ?");
		
		try {
			conn = JdbcDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				bean = new RoleBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setCreatedBy(rs.getString(4));
				bean.setModifiedBy(rs.getString(5));
				bean.setCreatedDateTime(rs.getTimestamp(6));
				bean.setModifiedDateTime(rs.getTimestamp(7));
				
			}
			rs.close();
			ps.close();
			
		}catch(Exception e) {
			throw new ApplicationException("Exception : Exception in getting user by role");
			
		}finally {
			JdbcDataSource.closeConnection(conn);
		}
		
		return bean;
	}
	
	public List<RoleBean> list() throws ApplicationException {
	return search(null, 0, 0);
		
	}
	
	public List<RoleBean> search(RoleBean bean, int pageNo, int pageSize) throws ApplicationException {
		StringBuffer sql = new StringBuffer("select * from st_role where 1=1");
		
		if(bean != null) {
			
			if(bean.getId() > 0) {
				sql.append(" and id = " + bean.getId());
				
			}
			
			if(bean.getName() != null && bean.getName().length() > 0) {
				sql.append(" and name like '" + bean.getName() + "%'");
				
				
			}
			
			if(bean.getDescription() != null && bean.getDescription().length() > 0) {
				sql.append(" and description like '" + bean.getDescription() + "%'");
				
			}
			
		}
		
		if(pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + ", " + pageSize);
			
		}
		
		Connection conn = null;
		ArrayList<RoleBean> list = new ArrayList<RoleBean>();
		
		try {
			conn = JdbcDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				bean = new RoleBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setCreatedBy(rs.getString(4));
				bean.setModifiedBy(rs.getString(5));
				bean.setCreatedDateTime(rs.getTimestamp(6));
				bean.setModifiedDateTime(rs.getTimestamp(7));
				list.add(bean);
			}
			
			rs.close();
			ps.close();
			
		}catch(Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception search role" + e.getMessage());
			
		}finally {
			JdbcDataSource.closeConnection(conn);
		}
		return list;
}
	
	

}
