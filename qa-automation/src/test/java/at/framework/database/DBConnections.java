package at.framework.database;

public class DBConnections {
	
	public static final String JDBC_DRIVIER = "jdbc:sqlserver://65.183.177.43:1920";
	public String dburl = "jdbc:mariadb://192.168.100.174/db";
	public String dbname = "qa";
	public static final String USERNAME = "QAUser";
	public static final String PASSWORD = "Nyb!qcrpN3p4D@n6EZQPx:Ft";
	
	public static final String NAVIGATION_MENU = "SELECT * FROM QA.dbo.NavigationMenu where TestcaseID=";
	public static final String CONSUMER_SEARCH = "SELECT * FROM QA.dbo.ConsumerSearch where TestcaseID=";
	public static final String PRODUCT_SUMMARY = "SELECT * FROM QA.dbo.ProductSummary where TestcaseID=";
	public static final String LOCATION_SUMMARY = "SELECT * FROM QA.dbo.LocationSummary where TestcaseID=";
	public static final String CONSUMER_SUMMARY = "SELECT * FROM QA.dbo.ConsumerSummary where TestcaseID=";
	public static final String REPORT_LIST = "SELECT * FROM QA.dbo.ReportsList where TestcaseID=";
	public static final String DEVICE_LIST = "SELECT * FROM QA.dbo.DeviceList where TestcaseID=";
	public static final String LOCATION_LIST = "SELECT * FROM QA.dbo.LocationList where TestcaseID=";
}
