package at.framework.database;

public class DBConnections {
	
	private static String jdbcdriver = "jdbc:sqlserver://65.183.177.43:1920";
	private String dburl = "jdbc:mariadb://192.168.100.174/db";
	private String dbname = "qa";
	private static String username = "QAUser";
	private static String password = "Nyb!qcrpN3p4D@n6EZQPx:Ft";
	
	private String navigationMenu = "SELECT * FROM QA.dbo.NavigationMenu where TestcaseID=";
	private String consumersearch = "SELECT * FROM QA.dbo.ConsumerSearch where TestcaseID=";
	private String productsummary = "SELECT * FROM QA.dbo.ProductSummary where TestcaseID=";
	private String locationsummary = "SELECT * FROM QA.dbo.LocationSummary where TestcaseID=";
	private String consumersummary = "SELECT * FROM QA.dbo.ConsumerSummary where TestcaseID=";
	private String reportsList = "SELECT * FROM QA.dbo.ReportsList where TestcaseID=";
	private String devicelist = "SELECT * FROM QA.dbo.DeviceList where TestcaseID=";
	private String locationlist = "SELECT * FROM QA.dbo.LocationList where TestcaseID=";

	public static String getJdbcdriver() {
		return jdbcdriver;
	}
	public void setJdbcdriver(String jdbcdriver) {
		DBConnections.jdbcdriver = jdbcdriver;
	}
	public String getDburl() {
		return dburl;
	}
	public void setDburl(String dburl) {
		this.dburl = dburl;
	}
	public String getDbname() {
		return dbname;
	}
	public void setDbname(String dbname) {
		this.dbname = dbname;
	}
	public static String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		DBConnections.username = username;
	}
	public static String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		DBConnections.password = password;
	}
	public String getNavigationMenu() {
		return navigationMenu;
	}
	public void setNavigationMenu(String navigationMenu) {
		this.navigationMenu = navigationMenu;
	}
	public String getConsumersearch() {
		return consumersearch;
	}
	public void setConsumersearch(String consumersearch) {
		this.consumersearch = consumersearch;
	}
	public String getProductsummary() {
		return productsummary;
	}
	public void setProductsummary(String productsummary) {
		this.productsummary = productsummary;
	}
	public String getLocationsummary() {
		return locationsummary;
	}
	public void setLocationsummary(String locationsummary) {
		this.locationsummary = locationsummary;
	}
	public String getConsumersummary() {
		return consumersummary;
	}
	public void setConsumersummary(String consumersummary) {
		this.consumersummary = consumersummary;
	}
	public String getReportsList() {
		return reportsList;
	}
	public void setReportsList(String reportsList) {
		this.reportsList = reportsList;
	}
	public String getDevicelist() {
		return devicelist;
	}
	public void setDevicelist(String devicelist) {
		this.devicelist = devicelist;
	}
	public String getLocationlist() {
		return locationlist;
	}
	public void setLocationlist(String locationlist) {
		this.locationlist = locationlist;
	}

}
