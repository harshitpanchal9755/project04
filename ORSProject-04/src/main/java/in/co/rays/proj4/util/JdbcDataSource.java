package in.co.rays.proj4.util;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public final class JdbcDataSource {

	private static JdbcDataSource jdbc = null;
	private static ComboPooledDataSource cpds = null;

	ResourceBundle rb = ResourceBundle.getBundle("in.co.rays.proj4.bundle.system");

	private JdbcDataSource() {
		cpds = new ComboPooledDataSource();
		try {
			cpds.setDriverClass(rb.getString("driver"));
			cpds.setJdbcUrl(rb.getString("url"));
			cpds.setUser(rb.getString("username"));
			cpds.setPassword(rb.getString("password"));
			cpds.setMaxPoolSize(Integer.parseInt(rb.getString("maxpoolsize")));
			cpds.setAcquireIncrement(Integer.parseInt(rb.getString("acquireincrement")));
			cpds.setInitialPoolSize(Integer.parseInt(rb.getString("initialpoolsize")));
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
	}

	public static JdbcDataSource getInstance() {

		if (jdbc == null) {
			jdbc = new JdbcDataSource();
		}
		return jdbc;

	}

	public static Connection getConnection() {
		try {
			return getInstance().cpds.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public static void closeConnection(Connection conn, Statement stmt, ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void closeConnection(Connection conn, Statement stmt) {
		closeConnection(conn, stmt, null);
	}

	public static void closeConnection(Connection conn) {
		closeConnection(conn, null);
	}
}
