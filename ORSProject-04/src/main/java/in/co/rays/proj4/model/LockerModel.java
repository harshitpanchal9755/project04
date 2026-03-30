package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.LockerBean;
import in.co.rays.proj4.bean.RoleBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DataBaseException;
import in.co.rays.proj4.exception.DuplicateException;
import in.co.rays.proj4.util.JdbcDataSource;

public class LockerModel {
	public Integer nextpk() throws DataBaseException {
		Connection conn = null;
		int pk = 0;
		
		try {
			conn = JdbcDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("select max(id) from locker");
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();
			ps.close();
			
		}catch(Exception e) {
			e.printStackTrace();
			throw new DataBaseException("Exception is nextpk" + e.getMessage());
			
		}finally {
			JdbcDataSource.closeConnection(conn);
		}
		
		return pk + 1;
	}
	
	public long add(LockerBean bean) throws ApplicationException, DuplicateException {

		Connection conn = null;
		int pk = 0;

		try {
			pk = nextpk();
			conn = JdbcDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("insert into locker values(?,?,?,?)");

			ps.setInt(1, pk);
			ps.setString(2, bean.getLockerNumber());
			ps.setString(3, bean.getLockerType());
			ps.setDouble(4, bean.getAnnualFee());
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
			throw new ApplicationException("Exception : exception in add locker" + e.getMessage());
		} finally {
			JdbcDataSource.closeConnection(conn);
		}
		return pk;

	}
	
	public void update(LockerBean bean) throws ApplicationException, DuplicateException {

		Connection conn = null;
		try {
			conn = JdbcDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(
					"update locker set locker_number = ?, locker_type = ?, annual_fee = ? where id = ?");

			ps.setString(1, bean.getLockerNumber());
			ps.setString(2, bean.getLockerType());
			ps.setDouble(3, bean.getAnnualFee());
			ps.setLong(4, bean.getId());
			ps.executeUpdate();
			conn.commit();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("update rollback exception" + ex.getMessage());
			}

			throw new ApplicationException("exception in update locker" + e.getMessage());
		} finally {
			JdbcDataSource.closeConnection(conn);
		}

	}
	
	public void delete(LockerBean bean) throws ApplicationException, DuplicateException {

		Connection conn = null;

		try {
			conn = JdbcDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("delete from locker where id = ?");
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
	
	public LockerBean findBypk(long pk) throws ApplicationException {
		LockerBean bean = null;
		Connection conn = null;
	
		StringBuffer sql = new StringBuffer("select * from locker where id = ?");
		
		try {
			conn = JdbcDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setLong(1, pk);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
			bean = new LockerBean();
			bean.setId(rs.getLong(1));
			bean.setLockerNumber(rs.getString(2));
			bean.setLockerType(rs.getString(3));
			bean.setAnnualFee(rs.getDouble(4));
			
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
	
	public List<LockerBean> list() throws ApplicationException {
		return search(null, 0, 0);
			
		}
		
		public List<LockerBean> search(LockerBean bean, int pageSize, int pageNo) throws ApplicationException {
			StringBuffer sql = new StringBuffer("select * from Locker where 1=1");
			
			if(bean != null) {
				
				if(bean.getId() > 0) {
					sql.append(" and id = " + bean.getId());
					
				}
				
				if(bean.getLockerNumber() != null && bean.getLockerNumber().length() > 0) {
					sql.append(" and lockernumber like '" + bean.getLockerNumber() + "%'");
					
					
				}
				
				if(bean.getLockerType() != null && bean.getLockerType().length() > 0) {
					sql.append(" and lockertype like '" + bean.getLockerType() + "%'");
					
				}
				
				if(bean.getAnnualFee() != null) {
					sql.append("and annualfee like '" + bean.getAnnualFee() + "%'");
					
				}
				
			}
			
			if(pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				sql.append(" limit" + pageNo + " ," + pageSize);
				
			}
			
			Connection conn = null;
			ArrayList<LockerBean> list = new ArrayList<LockerBean>();
			
			try {
				conn = JdbcDataSource.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql.toString());
				ResultSet rs = ps.executeQuery();
				
				while(rs.next()) {
					bean = new LockerBean();
					bean.setId(rs.getLong(1));
					bean.setLockerNumber(rs.getString(2));
					bean.setLockerType(rs.getString(3));
					bean.setAnnualFee(rs.getDouble(4));
					list.add(bean);
				}
				
				rs.close();
				ps.close();
				
			}catch(Exception e) {
				throw new ApplicationException("Exception : Exception search role");
				
			}finally {
				JdbcDataSource.closeConnection(conn);
			}
			return list;
	}
	

}
