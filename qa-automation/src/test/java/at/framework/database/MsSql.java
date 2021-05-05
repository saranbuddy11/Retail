package at.framework.database;

import java.sql.Connection;

import org.apache.commons.dbcp.BasicDataSource;
import org.testng.Assert;

public class MsSql {
	//DBConnections dbConnection=new DBConnections();
	public static Connection connection ;
	private static BasicDataSource dataSource = new BasicDataSource();

    static {
    	try {
            dataSource.setUrl(DBConnections.getJdbcdriver());
            dataSource.setUsername(DBConnections.getUsername());
            dataSource.setPassword(DBConnections.getPassword());
            dataSource.setMinIdle(5);
            dataSource.setMaxIdle(10);
            dataSource.setMaxOpenPreparedStatements(10);
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
