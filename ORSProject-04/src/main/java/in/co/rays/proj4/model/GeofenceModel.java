package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.GeofenceBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DataBaseException;
import in.co.rays.proj4.exception.DuplicateException;
import in.co.rays.proj4.util.JdbcDataSource;

public class GeofenceModel {

	public Integer nextpk() throws DataBaseException {
		Connection conn = null;
		int pk = 0;

		try {
			conn = JdbcDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("select max(id) from geofence");
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

	public long add(GeofenceBean bean) throws ApplicationException, DuplicateException {

		Connection conn = null;
		int pk = 0;

		GeofenceBean existBean = findBypk(bean.getId());

		if (existBean != null) {
			throw new DuplicateException("Geofence already exist");
		}

		try {
			pk = nextpk();
			conn = JdbcDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("insert into geofence values(?,?,?,?,?,?,?,?,?)");

			ps.setInt(1, pk);
			ps.setString(2, bean.getGeoFenceCode());
			ps.setString(3, bean.getLocationName());
			ps.setString(4, bean.getRadius());
			ps.setString(5, bean.getStatus());
			ps.setString(6, bean.getCreatedBy());
			ps.setString(7, bean.getModifiedBy());
			ps.setTimestamp(8, bean.getCreatedDateTime());
			ps.setTimestamp(9, bean.getModifiedDateTime());
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
			throw new ApplicationException("Exception : Exception in add geofence" + e.getMessage());
		} finally {
			JdbcDataSource.closeConnection(conn);
		}
		return pk;
	}

	public void update(GeofenceBean bean) throws ApplicationException, DuplicateException {

		Connection conn = null;

		try {
			conn = JdbcDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(
					"update geofence set geofenceCode = ?, locationName = ?, radius = ?, status = ? created_by = ?, modified_by = ?,created_datetime = ?, modified_datetime = ? where id = ?");

			ps.setString(1, bean.getGeoFenceCode());
			ps.setString(2, bean.getLocationName());
			ps.setString(3, bean.getRadius());
			ps.setString(4, bean.getStatus());
			ps.setString(5, bean.getCreatedBy());
			ps.setString(6, bean.getModifiedBy());
			ps.setTimestamp(7, bean.getCreatedDateTime());
			ps.setTimestamp(8, bean.getModifiedDateTime());
			ps.setLong(9, bean.getId());
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

	public void delete(GeofenceBean bean) throws ApplicationException, DuplicateException {

		Connection conn = null;

		try {
			conn = JdbcDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("delete from geofence where id = ?");
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

	public GeofenceBean findBypk(long pk) throws ApplicationException {
		GeofenceBean bean = null;
		Connection conn = null;

		StringBuffer sql = new StringBuffer("select * from geofence where id = ?");

		try {
			conn = JdbcDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setLong(1, pk);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				bean = new GeofenceBean();
				bean.setId(rs.getLong(1));
				bean.setGeoFenceCode(rs.getString(2));
				bean.setLocationName(rs.getString(3));
				bean.setRadius(rs.getString(4));
				bean.setStatus(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDateTime(rs.getTimestamp(8));
				bean.setModifiedDateTime(rs.getTimestamp(9));

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

	public GeofenceBean findByName(String name) throws ApplicationException {

		GeofenceBean bean = null;
		Connection conn = null;

		StringBuffer sql = new StringBuffer("select * from geofence where name = ?");

		try {
			conn = JdbcDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				bean = new GeofenceBean();
				bean.setId(rs.getLong(1));
				bean.setGeoFenceCode(rs.getString(2));
				bean.setLocationName(rs.getString(3));
				bean.setRadius(rs.getString(4));
				bean.setStatus(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDateTime(rs.getTimestamp(8));
				bean.setModifiedDateTime(rs.getTimestamp(9));
			}
			rs.close();
			ps.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in getting user by role");
		} finally {
			JdbcDataSource.closeConnection(conn);
		}

		return bean;
	}

	public List<GeofenceBean> list() throws ApplicationException {
		return search(null, 0, 0);
	}

	public List<GeofenceBean> search(GeofenceBean bean, int pageNo, int pageSize) throws ApplicationException {
		StringBuffer sql = new StringBuffer("select * from geofence where 1=1");

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" and id = " + bean.getId());
			}
			if (bean.getGeoFenceCode() != null && bean.getGeoFenceCode().length() > 0) {
				sql.append(" and code like '" + bean.getGeoFenceCode() + "%'");
			}
			if (bean.getLocationName() != null && bean.getLocationName().length() > 0) {
				sql.append(" and locationname like '" + bean.getLocationName() + "%'");
			}
		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + ", " + pageSize);
		}

		Connection conn = null;
		ArrayList<GeofenceBean> list = new ArrayList<GeofenceBean>();

		try {
			conn = JdbcDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				bean = new GeofenceBean();
				bean.setId(rs.getLong(1));
				bean.setGeoFenceCode(rs.getString(2));
				bean.setLocationName(rs.getString(3));
				bean.setRadius(rs.getString(4));
				bean.setStatus(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDateTime(rs.getTimestamp(8));
				bean.setModifiedDateTime(rs.getTimestamp(9));
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
