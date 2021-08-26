package at.framework.database.mssql;

import java.sql.Connection;

import org.apache.commons.dbcp.BasicDataSource;
import org.testng.Assert;

import at.smartshop.tests.TestInfra;

public class Connections {
	public static Connection connection ;
	private static BasicDataSource dataSource = new BasicDataSource();
	
	public Connections(){ }
	
    static {
    	try {
            dataSource.setUrl(Properties.JDBC_DRIVIER);
            dataSource.setUsername(Properties.USERNAME);
            dataSource.setPassword(Properties.PASSWORD);
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
