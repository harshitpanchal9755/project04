package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import in.co.rays.proj4.bean.DataImportLogBean;
import in.co.rays.proj4.bean.RoleBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DataBaseException;
import in.co.rays.proj4.exception.DuplicateException;
import in.co.rays.proj4.util.JdbcDataSource;

public class DataImportModel {

	public Integer nextpk() throws SQLException, DataBaseException {

		Connection conn = null;
		int pk = 0;

		try {
			conn = JdbcDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from data_importlog");
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				pk = rs.getInt(1);

			}
			rs.close();
			pstmt.close();

		} catch (Exception e) {
			e.printStackTrace();
			throw new DataBaseException("nextpk is not found" + e.getMessage());
		} finally {
			JdbcDataSource.closeConnection(conn);
		}

		return pk + 1;

	}

	public long add(DataImportLogBean bean) throws DuplicateException, ApplicationException {
		Connection conn = null;
		int pk = 0;
		
		DataImportLogBean existsbean = findbypk(bean.getId());
		if(existsbean != null) {
			throw new DuplicateException("Exception in findbypk");
			
		}

		try {
			pk = nextpk();
			conn = JdbcDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement ps = conn.prepareStatement("insert into data_importlog values(?,?,?,?,?)");
			ps.setInt(1, pk);
			ps.setString(2, bean.getImportlogcode());
			ps.setString(3, bean.getFilename());
			ps.setString(4, bean.getImportedby());
			ps.setString(5, bean.getStatus());
			
			int i = ps.executeUpdate();

			conn.commit();
			ps.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();

			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception is Invalid" + e.getMessage());

			} finally {
				JdbcDataSource.closeConnection(conn);
			}

		}
		return pk;
	}
	

	public void update(DataImportLogBean bean) throws ApplicationException { 
		Connection conn = null;
		
		try {
			conn = JdbcDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("update dataimport_log set importlogcode = ?, filename = ?, importedby = ?, status = ? where id = ? ");
			ps.setString(1, bean.getImportlogcode());
			ps.setString(2, bean.getFilename());
			ps.setString(3, bean.getImportedby());
			ps.setString(4, bean.getStatus());
			ps.setLong(5, bean.getId());
			ps.executeUpdate();
			
			conn.commit();
			ps.close();
			conn.close();
			
		}catch(Exception e) {
			e.printStackTrace();
			
			try {
				conn.rollback();
				
			}catch(Exception ex) {
				throw new ApplicationException("Exception is rollback" + e.getMessage());
			}
			finally {
				JdbcDataSource.closeConnection(conn);
			}
		}
		
	}
	
	public void delete(DataImportLogBean bean) throws ApplicationException {
		Connection conn = null;
		
		try {
			conn = JdbcDataSource.getConnection();
				conn = JdbcDataSource.getConnection();
				conn.setAutoCommit(false);
				PreparedStatement ps = conn.prepareStatement("delete from data_importlog where id = ?");
				ps.setLong(1, bean.getId());
				ps.executeUpdate();
				conn.commit();
				ps.close();
				conn.close();
				
			}catch(Exception e) {
				e.printStackTrace();
				
				try {
					conn.rollback();
				}catch(Exception ex) {
					throw new ApplicationException("Exception is rollback" + e.getMessage());
				}
				finally {
					JdbcDataSource.closeConnection(conn);
				}
			}
	}
	
	public DataImportLogBean findbypk(long pk) throws ApplicationException {
		DataImportLogBean bean = null;
		Connection conn = null;
		
		StringBuffer sql = new StringBuffer("select * from data_importlog where id = ?");
		try {
			conn = JdbcDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setLong(1, pk);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
			 bean = new DataImportLogBean();
				
				bean.setId(rs.getLong(1));
				bean.setImportlogcode(rs.getString(2));
				bean.setFilename(rs.getString(3));
				bean.setImportedby(rs.getString(4));
				bean.setStatus(rs.getString(5));
				
			}
			rs.close();
			ps.close();
		}catch(Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception is findbypk");
		}
		finally {
			JdbcDataSource.closeConnection(conn);
		}
		return bean;
	}
	
	
}
			
