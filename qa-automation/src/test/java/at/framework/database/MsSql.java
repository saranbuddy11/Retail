package at.framework.database;

import java.sql.Connection;

import org.apache.commons.dbcp.BasicDataSource;
import org.testng.Assert;

public class MsSql {
	public static Connection connection ;
	private static BasicDataSource dataSource = new BasicDataSource();

    static {
    	try {
            dataSource.setUrl(DBConnections.JDBC_DRIVIER);
            dataSource.setUsername(DBConnections.USERNAME);
            dataSource.setPassword(DBConnections.PASSWORD);
            dataSource.setMinIdle(5);
            dataSource.setMaxIdle(10);
            dataSource.setMaxOpenPreparedStatements(100);
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
    	
    }
    
    public static Connection getConnection()  {
    	
    	try {
    		connection = dataSource.getConnection();    		
    	}
    	catch (Exception exc) {
			Assert.fail(exc.toString());
		}
    	return connection;
    }
    
    public MsSql(){ }
    
}
