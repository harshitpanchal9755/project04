package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import in.co.rays.proj4.bean.InsuranceAppBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DataBaseException;
import in.co.rays.proj4.exception.DuplicateException;
import in.co.rays.proj4.util.JdbcDataSource;

public class InsuranceAppModel {

	public Integer nextpk() throws DataBaseException {
		int pk = 0;
		Connection conn = null;

		try {
			conn = JdbcDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("select max(id) from insurance_app");
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

	public long add(InsuranceAppBean bean) throws ApplicationException, DuplicateException {
		Connection conn = null;
		int pk = 0;

		InsuranceAppBean Existbean = findBypk(bean.getId());
		if (Existbean != null) {
			throw new DuplicateException("Id is already exist");

		}

		try {
			pk = nextpk();
			conn = JdbcDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("insert into insurance_app values(?,?,?,?,?,?,?,?,?)");
			ps.setInt(1, pk);
			ps.setString(2, bean.getCustomerName());
			ps.setString(3, bean.getPolicyType());
			ps.setDouble(4, bean.getAmount());
			ps.setString(5, bean.getClaimStatus());
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
				throw new ApplicationException("Exception is rollback" + ex.getMessage());

			}
			throw new ApplicationException("Exception is add gymtrainer" + e.getMessage());
		} finally {
			JdbcDataSource.closeConnection(conn);

		}
		return pk;

	}

	public void update(InsuranceAppBean bean) throws ApplicationException, DuplicateException {
		Connection conn = null;

		try {
			conn = JdbcDataSource.getConnection();

			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(
					"update insurance_app set customername = ?, policytype = ?, amount = ?, claimstatus = ?, created_by = ?, modified_by = ?, created_datetime = ?, modified_datetime = ? where id = ?");
			ps.setString(1, bean.getCustomerName());
			ps.setString(2, bean.getPolicyType());
			ps.setDouble(3, bean.getAmount());
			ps.setString(4, bean.getClaimStatus());
			ps.setString(5, bean.getCreatedBy());
			ps.setString(6, bean.getModifiedBy());
			ps.setTimestamp(7, bean.getCreatedDateTime());
			ps.setTimestamp(8, bean.getModifiedDateTime());
			ps.setLong(9, bean.getId());
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

	public void delete(InsuranceAppBean bean) throws ApplicationException {
		Connection conn = null;
		try {
			conn = JdbcDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("delete from insurance_app where id = ?");
			ps.setLong(1, bean.getId());
			ps.executeUpdate();
			conn.commit();
			ps.close();

		} catch (Exception e) {
			
			try {
				
				conn.rollback();
			}catch (Exception ex) {
				throw new ApplicationException("Exception in rollBack" + ex.getMessage());
			
			}
			
			throw new ApplicationException("Exception is deletedException e");

		} finally {
			JdbcDataSource.closeConnection(conn);
		}

	}

	public InsuranceAppBean findBypk(long pk) throws ApplicationException {
		Connection conn = null;
		InsuranceAppBean bean = null;

		StringBuffer sql = new StringBuffer("select * from insurance_app where id = ?");

		try {
			conn = JdbcDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setLong(1, pk);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				bean = new InsuranceAppBean();
				bean.setId(rs.getLong(1));
				bean.setCustomerName(rs.getString(2));
				bean.setPolicyType(rs.getString(3));
				bean.setAmount(rs.getDouble(4));
				bean.setClaimStatus(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDateTime(rs.getTimestamp(8));
				bean.setModifiedDateTime(rs.getTimestamp(9));

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

	public List<InsuranceAppBean> list() throws Exception {
		return search(null, 0, 0);
	}

	public List<InsuranceAppBean> search(InsuranceAppBean bean, int pageNo, int pageSize) throws ApplicationException {

		StringBuffer sql = new StringBuffer("select * from insurance_app where 1=1");

		if (bean != null) {

			if (bean.getId() > 0) {
				sql.append(" and id = " + bean.getId());
			}
			if (bean.getCustomerName() != null && bean.getCustomerName().length() > 0) {
				sql.append(" and customername like '" + bean.getCustomerName() + "%'");
			}
			if (bean.getPolicyType() != null && bean.getPolicyType().length() > 0) {
				sql.append(" and policytype like '" + bean.getPolicyType() + "%'");
			}

			if (bean.getAmount() > 0) {
				sql.append(" and amount = " + bean.getAmount());
			}

			if (bean.getClaimStatus() != null && (bean.getClaimStatus().length() > 0)) {
				sql.append(" and claimstatus like '" + bean.getClaimStatus() + "%'");
			}
		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + ", " + pageSize);
		}

		List<InsuranceAppBean> list = new ArrayList<InsuranceAppBean>();
		Connection conn = null;
		try {
			conn = JdbcDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				bean = new InsuranceAppBean();
				bean.setId(rs.getLong(1));
				bean.setCustomerName(rs.getString(2));
				bean.setPolicyType(rs.getString(3));
				bean.setAmount(rs.getDouble(4));
				bean.setClaimStatus(rs.getString(5));
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
			throw new ApplicationException("Exception is list" + e.getMessage());
		}
		return list;
	}

}
