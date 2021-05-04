package at.framework.database;

import java.sql.Connection;
import java.sql.DriverManager;
import org.testng.Assert;

public class MsSql {

	public Connection getDBConnection() {
	       
        DBConnections dbConnections = new DBConnections();
        String databaseURL = null;
        Connection connection = null;
        try {
            DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
            databaseURL = dbConnections.getJdbcdriver() + ";databaseName=" + dbConnections.getDbname() + ";user="
                    + dbConnections.getUsername() + ";password=" + dbConnections.getPassword();   
            connection=DriverManager.getConnection(databaseURL);
        } catch (Exception exc) {
            Assert.fail(exc.toString());
        }
        return connection;
       
    }
	
	

}
