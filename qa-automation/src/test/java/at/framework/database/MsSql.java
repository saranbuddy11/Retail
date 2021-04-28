package at.framework.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.testng.Assert;

public class MsSql {

	public Connection getDBConnection() throws SQLException {
		DBConnections DBConnections = new DBConnections();
		String dbURL = null;
		try {
			DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			dbURL = DBConnections.jdbcdriver + ";databaseName=" + DBConnections.dbname + ";user="
					+ DBConnections.username + ";password=" + DBConnections.password;
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
		return DriverManager.getConnection(dbURL);
	}

}
