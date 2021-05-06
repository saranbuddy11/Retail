package at.framework.database;

import java.sql.Connection;

import org.apache.commons.dbcp.BasicDataSource;
import org.testng.Assert;

public class MsSqlConnection {
	public static Connection connection ;
	private static BasicDataSource dataSource = new BasicDataSource();
	
	public MsSqlConnection(){ }
	
    static {
    	try {
            dataSource.setUrl(MsSqlProperties.JDBC_DRIVIER);
            dataSource.setUsername(MsSqlProperties.USERNAME);
            dataSource.setPassword(MsSqlProperties.PASSWORD);
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
    
  
    
}
