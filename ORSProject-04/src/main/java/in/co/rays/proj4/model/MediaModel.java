package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.MediaBean;
import in.co.rays.proj4.bean.RoleBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DataBaseException;
import in.co.rays.proj4.exception.DuplicateException;
import in.co.rays.proj4.util.JdbcDataSource;

public class MediaModel {

	public Integer nextpk() throws DataBaseException {
		Connection conn = null;
		int pk = 0;

		try {
			conn = JdbcDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("select max(id) from media");
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				pk = rs.getInt(1);

			}
			rs.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
			throw new DataBaseException("nextpk is Exception" + e.getMessage());
		} finally {
			JdbcDataSource.closeConnection(conn);
		}
		return pk + 1;

	}

	public long add(MediaBean bean) throws ApplicationException, DuplicateException {
		Connection conn = null;
		int pk = 0;

		MediaBean existsbean = findbypk(bean.getId());
		if (existsbean != null) {
			throw new DuplicateException("user alread exists");
		}

		try {
			pk = nextpk();
			conn = JdbcDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("insert into media values(?,?,?,?,?)");
			ps.setInt(1, pk);
			ps.setString(2, bean.getMediacode());
			ps.setString(3, bean.getMediatype());
			ps.setString(4, bean.getUri());
			ps.setString(5, bean.getStatus());
			ps.executeUpdate();

			conn.commit();
			ps.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();

			try {
				conn.rollback();

			} catch (Exception ex) {
				throw new ApplicationException("Exception is add Exception ");
			}
			throw new ApplicationException("Exception is rollback" + e.getMessage());

		} finally {
			JdbcDataSource.closeConnection(conn);
		}
		return pk;

	}

	public void update(MediaBean bean) throws ApplicationException {
		Connection conn = null;

		try {
			conn = JdbcDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(
					"update media set mediacode = ?, mediatype = ?, uri = ?, status = ? where id = ?");
			ps.setString(1, bean.getMediacode());
			ps.setString(2, bean.getMediatype());
			ps.setString(3, bean.getUri());
			ps.setString(4, bean.getStatus());
			ps.setLong(5, bean.getId());
			ps.executeUpdate();

			conn.commit();
			ps.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();

			try {
				conn.rollback();

			} catch (Exception ex) {
				throw new ApplicationException("Exception is update");

			}
			throw new ApplicationException("Exception is rollback");
		} finally {
			JdbcDataSource.closeConnection(conn);
		}
	}

	public void delete(MediaBean bean) throws ApplicationException {
		Connection conn = null;

		try {
			conn = JdbcDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("delete from media where id = ?");
			ps.setLong(1, bean.getId());

			ps.executeUpdate();

			ps.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception is delete" + e.getMessage());

		} finally {
			JdbcDataSource.closeConnection(conn);
		}
	}
	
	public MediaBean findbypk(long pk) {
		Connection conn  = null;
		MediaBean bean = null;
		
		StringBuffer sql = new StringBuffer("select * from media where id = ?");
		
		try {
			conn = JdbcDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setLong(1, pk);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				 bean = new MediaBean();
				bean.setId(rs.getLong(1));
				bean.setMediacode(rs.getString(2));
				bean.setMediatype(rs.getString(3));
				bean.setUri(rs.getString(4));
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
			
		return bean;
	}
		
	}
	
	 public List<MediaBean> search(MediaBean bean, int pageNo, int pageSize) throws ApplicationException {
	        StringBuffer sql = new StringBuffer("select * from media where 1=1");

	        if (bean != null) {
	            if (bean.getId() > 0) {
	                sql.append(" and id = " + bean.getId());
	            }
	            if (bean.getMediacode() != null && bean.getMediacode().length() > 0) {
	                sql.append(" and mediacode like '" + bean.getMediacode() + "%'");
	            }
	            if (bean.getMediatype() != null && bean.getMediatype().length() > 0) {
	                sql.append(" and mediatype like '" + bean.getMediatype() + "%'");
	            }
	            if (bean.getUri() != null && bean.getUri().length() > 0) {
	                sql.append(" and uri like '" + bean.getUri() + "%'");
	            }
	            if (bean.getStatus() != null && bean.getStatus().length() > 0) {
	                sql.append(" and status like '" + bean.getStatus() + "%'");
	            }
	        }

	        if (pageSize > 0) {
	            pageNo = (pageNo - 1) * pageSize;
	            sql.append(" limit " + pageNo + ", " + pageSize);
	        }

	        Connection conn = null;
	        ArrayList<MediaBean> list = new ArrayList<MediaBean>();

	        try {
	            conn = JdbcDataSource.getConnection();
	            PreparedStatement ps = conn.prepareStatement(sql.toString());
	            ResultSet rs = ps.executeQuery();

	            while (rs.next()) {
	                bean = new MediaBean();
	                bean.setId(rs.getLong(1));
	                bean.setMediacode(rs.getString(2));
	                bean.setMediatype(rs.getString(3));
	                bean.setUri(rs.getString(4));
	                bean.setStatus(rs.getString(5));
	               
	                list.add(bean);
	            }

	            rs.close();
	            ps.close();

	        } catch (Exception e) {
	            e.printStackTrace();
	            throw new ApplicationException("Exception search role" + e.getMessage());
	        } finally {
	            JdbcDataSource.closeConnection(conn);
	        }
	        return list;
	    }
	}


