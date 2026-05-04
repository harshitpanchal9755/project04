package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.DataMappingBean;
import in.co.rays.proj4.bean.RoleBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DataBaseException;
import in.co.rays.proj4.exception.DuplicateException;
import in.co.rays.proj4.util.JdbcDataSource;

public class DataMappingModel {
	
	  public Integer nextpk() throws DataBaseException {
	        Connection conn = null;
	        int pk = 0;

	        try {
	            conn = JdbcDataSource.getConnection();
	            PreparedStatement ps = conn.prepareStatement("select max(id) from datamapping");
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
	  
	    public long add(DataMappingBean bean) throws ApplicationException, DuplicateException {

	        Connection conn = null;
	        int pk = 0;


	      DataMappingBean existsBean = findBypk(bean.getId());

	        try {
	            pk = nextpk();
	            conn = JdbcDataSource.getConnection();
	            conn.setAutoCommit(false);
	            PreparedStatement ps = conn.prepareStatement("insert into datamapping values(?,?,?,?,?)");

	            ps.setInt(1, pk);
	            ps.setString(2, bean.getMappingcode());
	            ps.setString(3, bean.getSourcefield());
	            ps.setString(4, bean.getTargetfield());
	            ps.setString(5, bean.getStatus());
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

	    public void update(DataMappingBean bean) throws ApplicationException, DuplicateException {

	        Connection conn = null;

	        try {
	            conn = JdbcDataSource.getConnection();
	            conn.setAutoCommit(false);
	            PreparedStatement ps = conn.prepareStatement(
	                    "update st_role set name = ?, description = ?, created_by = ?, modified_by = ?, created_datetime = ?, modified_datetime = ? where id = ?");

	            ps.setString(2, bean.getMappingcode());
	            ps.setString(3, bean.getSourcefield());
	            ps.setString(4, bean.getTargetfield());
	            ps.setString(5, bean.getStatus());
	            ps.setLong(5, bean.getId());
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

	    public void delete(DataMappingBean bean) throws ApplicationException, DuplicateException {

	        Connection conn = null;

	        try {
	            conn = JdbcDataSource.getConnection();
	            conn.setAutoCommit(false);
	            PreparedStatement ps = conn.prepareStatement("delete from datamapping where id = ?");
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

	    public DataMappingBean findBypk(long pk) throws ApplicationException {
	        DataMappingBean bean = null;
	        Connection conn = null;

	        StringBuffer sql = new StringBuffer("select * from datamapping where id = ?");

	        try {
	            conn = JdbcDataSource.getConnection();
	            PreparedStatement ps = conn.prepareStatement(sql.toString());
	            ps.setLong(1, pk);
	            ResultSet rs = ps.executeQuery();

	            while (rs.next()) {
	                bean = new DataMappingBean();
	                bean.setId(rs.getLong(1));
	                bean.setMappingcode(rs.getString(2));
	                bean.setSourcefield(rs.getString(3));
	                bean.setTargetfield(rs.getString(4));
	                bean.setStatus(rs.getString(5));
	            }

	            rs.close();
	            ps.close();

	        } catch (Exception e) {
	            throw new ApplicationException("Exception : Exception in getting user pk");
	        } finally {
	            JdbcDataSource.closeConnection(conn);
	        }
	        return bean;
	    }

	    public List<DataMappingBean> list() throws ApplicationException {
	        return search(null, 0, 0);
	    }

	    public List<DataMappingBean> search(DataMappingBean bean, int pageNo, int pageSize) throws ApplicationException {
	        StringBuffer sql = new StringBuffer("select * from st_role where 1=1");

	        if (bean != null) {
	            if (bean.getId() > 0) {
	                sql.append(" and id = " + bean.getId());
	            }
	            if (bean.getMappingcode() != null && bean.getMappingcode().length() > 0) {
	                sql.append(" and mappingcode like '" + bean.getMappingcode() + "%'");
	            }
	            if (bean.getSourcefield() != null && bean.getSourcefield().length() > 0) {
	                sql.append(" and sourcefield like '" + bean.getSourcefield() + "%'");
	            }
	        }

	        if (pageSize > 0) {
	            pageNo = (pageNo - 1) * pageSize;
	            sql.append(" limit " + pageNo + ", " + pageSize);
	        }

	        Connection conn = null;
	        ArrayList<DataMappingBean> list = new ArrayList<DataMappingBean>();

	        try {
	            conn = JdbcDataSource.getConnection();
	            PreparedStatement ps = conn.prepareStatement(sql.toString());
	            ResultSet rs = ps.executeQuery();

	            while (rs.next()) {
	            	 bean = new DataMappingBean();
		                bean.setId(rs.getLong(1));
		                bean.setMappingcode(rs.getString(2));
		                bean.setSourcefield(rs.getString(3));
		                bean.setTargetfield(rs.getString(4));
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
