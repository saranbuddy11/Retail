package at.framework.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.testng.Assert;

public class MsSql {

	public Connection getDBConnection() throws SQLException {
		DBConnections dbConnections = new DBConnections();
		String databaseURL = null;
		try {
			DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
			databaseURL = dbConnections.getJdbcdriver() + ";databaseName=" + dbConnections.getDbname() + ";user="
					+ dbConnections.getUsername() + ";password=" + dbConnections.getPassword();
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
		return DriverManager.getConnection(databaseURL);
	}

}
