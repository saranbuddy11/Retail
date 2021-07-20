package at.framework.database.mssql;

public class Queries {
	public static final String NAVIGATION_MENU = "SELECT * FROM QA.dbo.NavigationMenu where TestcaseID=";
	public static final String CONSUMER_SEARCH = "SELECT * FROM QA.dbo.ConsumerSearch where TestcaseID=";
	public static final String PRODUCT_SUMMARY = "SELECT * FROM QA.dbo.ProductSummary where TestcaseID=";
	public static final String LOCATION_SUMMARY = "SELECT * FROM QA.dbo.LocationSummary where TestcaseID=";
	public static final String CONSUMER_SUMMARY = "SELECT * FROM QA.dbo.ConsumerSummary where TestcaseID=";
	public static final String REPORT_LIST = "SELECT * FROM QA.dbo.ReportsList where TestcaseID=";
	public static final String DEVICE_LIST = "SELECT * FROM QA.dbo.DeviceList where TestcaseID=";
	public static final String LOCATION_LIST = "SELECT * FROM QA.dbo.LocationList where TestcaseID=";
	public static final String GMA_USER = "SELECT * FROM QA.dbo.LoadGMAUserParameters where TestcaseID=";
	public static final String LOAD_PRODUCT="SELECT * FROM QA.dbo.LoadProductParameters where TestcaseID=";
	public static final String GLOBAL_PRODUCT_CHANGE="SELECT * FROM QA.dbo.GlobalProductChange where TestcaseID=";
	public static final String USER_ROLES = "SELECT * FROM QA.dbo.ViewRole where TestcaseID=";
	public static final String NATIONAL_ACCOUNTS="SELECT * FROM QA.dbo.NationalAccounts where TestcaseID=";
	public static final String LOCKER_SYSTEM ="SELECT * FROM QA.dbo.LockerSystem where TestcaseID=";
	public static final String LOCATION = "SELECT * FROM QA.dbo.Location where TestcaseID=";
	public static final String V5Device = "SELECT * FROM QA.dbo.V5Device where TestcaseID=";
	public static final String ORG_SUMMARY = "SELECT * FROM QA.dbo.Org where TestcaseID=";
}
