package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.management.InvalidApplicationException;

import in.co.rays.proj4.bean.CollegeBean;
import in.co.rays.proj4.bean.GiftcardBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DataBaseException;
import in.co.rays.proj4.exception.DuplicateException;
import in.co.rays.proj4.util.JdbcDataSource;

public class GiftcardModel {
	
	public int nextpk() throws DataBaseException {
		Connection conn = null;
		int pk = 0;
		
		try {
			conn = JdbcDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("select max(id) from gift_card");
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				pk = rs.getInt(1);
				
			}
			
			rs.close();
			ps.close();
			
		}catch(Exception e) {
			e.printStackTrace();
			throw new DataBaseException("Exception : Exception is nextpk" + e.getMessage());
		}
			
		return pk + 1 ;
		
	}
	
	public long add(GiftcardBean bean) throws ApplicationException, DuplicateException {
		Connection conn = null;
		int pk = 0;
		
		GiftcardBean Existbean = findBypk(bean.getId());
		if(Existbean != null) {
			throw new  DuplicateException("id is aliready exist");
			
		}
		
		try {
			pk = nextpk();
			conn = JdbcDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("insert into gift_card values(?,?,?,?,?)");
			ps.setInt(1, pk);
			ps.setString(2, bean.getCode());
			ps.setBigDecimal(3, bean.getAmount());
			ps.setDate(4, new java.sql.Date(bean.getExpiredate().getTime()));
			ps.setString(5, bean.getStatus());
			ps.executeUpdate();
			conn.commit();
			ps.close();
			
		}catch(Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			}catch(Exception ex) {
				throw new ApplicationException("Exception is rollback" + ex.getMessage());
				
			}
			throw new ApplicationException("Exception is add giftcard" + e.getMessage());
		}finally {
			JdbcDataSource.closeConnection(conn);
		}
		return pk;
		
	}

	public void update(GiftcardBean bean) throws ApplicationException {
		Connection conn = null;
		
		GiftcardBean CodeBean = findBycode(bean.getCode());
		
		try {
			conn = JdbcDataSource.getConnection();
			
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("update gift_card set code = ?, amount = ?, expire_date = ?, status = ? where id = ?");
			ps.setString(1, bean.getCode());
			ps.setBigDecimal(2, bean.getAmount());
			ps.setDate(3, new java.sql.Date(bean.getExpiredate().getTime()));
			ps.setString(4, bean.getStatus());
			ps.setLong(5, bean.getId());
			ps.executeUpdate();
			conn.commit();
			ps.close();
			
		}catch(Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
				
			}catch(Exception ex) {
				throw new ApplicationException("Exception is rollback" + e.getMessage());
				
			}
			throw new ApplicationException("Exception is update");
			
		}finally {
			JdbcDataSource.closeConnection(conn);
		}	
		
	}
	
	public void delete(GiftcardBean bean) throws ApplicationException {
		Connection conn = null;
		try {
			conn = JdbcDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("delete from gift_card where id = ?");
			ps.setLong(1, bean.getId());
			ps.executeUpdate();
			ps.close();
			
		}catch(Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception is delete" + e.getMessage());
			
		}finally {
			JdbcDataSource.closeConnection(conn);
		}

	}
	

	public GiftcardBean findBypk(long pk) throws ApplicationException {
		Connection conn = null;
		GiftcardBean bean = null;
		
		StringBuffer sql = new StringBuffer("select * from gift_card where id = ?");
		
		try {
			conn = JdbcDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setLong(1, pk);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				bean = new GiftcardBean();
				bean.setId(rs.getLong(1));
				bean.setCode(rs.getString(2));
				bean.setAmount(rs.getBigDecimal(3));
				bean.setExpiredate(rs.getTimestamp(4));
				bean.setStatus(rs.getString(5));
			}
			
			rs.close();
			ps.close();
			
		}catch(Exception e) {
			throw new ApplicationException("Exception is finkbypk" + e.getMessage());
		}finally {
			JdbcDataSource.closeConnection(conn);
		}
		
		return bean;
	}
//	
	public GiftcardBean findBycode(String code) throws ApplicationException {
		GiftcardBean bean = null;
		Connection conn = null;
		
		StringBuffer sql = new StringBuffer("select * from gift_card where code = ?");
		
		try {
			conn = JdbcDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setString(1, code);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				bean = new GiftcardBean();
				
				bean.setId(rs.getLong(1));
				bean.setCode(rs.getString(2));
				bean.setAmount(rs.getBigDecimal(3));
				bean.setExpiredate(rs.getTimestamp(4));
				bean.setStatus(rs.getString(5));
			
			}
			
			rs.close();
			ps.close();
			
		}catch(Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception is findbycode" +  e.getMessage());
		}finally {
			JdbcDataSource.closeConnection(conn);
		}
		return bean;
		
	}
	
	public List<GiftcardBean> list() throws Exception {
		return search(null, 0, 0);
	}

	public  List<GiftcardBean> search(GiftcardBean bean , int pageNo, int pageSize) throws ApplicationException {
		
		StringBuffer sql = new StringBuffer("select * from gift_card where 1=1");
		
		if(bean != null) {
			
			if(bean.getId() > 0) {
				sql.append("and id" + bean.getId());
			}
				if(bean.getCode() != null && bean.getCode().length() > 0) {
					sql.append("and code like '" + bean.getCode() + "%'");
				}
				if(bean.getAmount() != null && ((CharSequence) bean.getAmount()).length() > 0) {
					sql.append("and amount like '" + bean.getAmount() + "%'");
				}
				if(bean.getExpiredate() != null && ((CharSequence) bean.getExpiredate()).length() > 0) {
					sql.append("and expiredate like '" + bean.getExpiredate() + "%'");
				}
				if(bean.getStatus() != null && bean.getStatus().length() > 0) {
					sql.append("and status like '" + bean.getStatus() + "%'");
				}
			}
			
			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				sql.append(" limit " + pageNo + ", " + pageSize);
			}
			
			List<GiftcardBean> list = new ArrayList<GiftcardBean>();
			Connection conn = null;
			try {
				conn = JdbcDataSource.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql.toString());
				ResultSet rs = ps.executeQuery();
				
				while(rs.next()) {
					bean = new GiftcardBean();
					bean.setId(rs.getLong(1));
					bean.setCode(rs.getString(2));
					bean.setAmount(rs.getBigDecimal(3));
					bean.setExpiredate(rs.getTimestamp(4));
					bean.setStatus(rs.getString(5));
				}
				
				rs.close();
				ps.close();
				
		}catch(Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception is list" + e.getMessage());
		}
		return list;
	}

	
	

}
