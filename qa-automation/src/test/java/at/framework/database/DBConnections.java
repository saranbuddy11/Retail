package at.framework.database;

public class DBConnections {

	public String jdbcdriver = "jdbc:sqlserver://65.183.177.43:1920";
	public String dburl = "jdbc:mariadb://192.168.100.174/db";
	public String dbname = "qa";
	public String username = "QAUser";
	public String password = "Nyb!qcrpN3p4D@n6EZQPx:Ft";
	
	public String navigationMenu = "SELECT * FROM dbo.NavigationMenu where TestcaseID=";
	public String consumersearch = "SELECT * FROM dbo.ConsumerSearch where TestcaseID=";
	public String productsummary = "SELECT * FROM dbo.ProductSummary where TestcaseID=";
	public String locationsummary = "SELECT * FROM dbo.LocationSummary where TestcaseID=";
	public String consumersummary = "SELECT * FROM dbo.ConsumerSummary where TestcaseID=";
	public String reportsList = "SELECT * FROM dbo.ReportsList where TestcaseID=";
	public String devicelist = "SELECT * FROM dbo.DeviceList where TestcaseID=";
	public String locationlist = "SELECT * FROM dbo.LocationList where TestcaseID=";
}
