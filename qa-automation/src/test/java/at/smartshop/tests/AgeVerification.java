package at.smartshop.tests;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import at.framework.database.mssql.Queries;
import at.framework.database.mssql.ResultSets;
import at.framework.generic.CustomisedAssert;
import at.framework.ui.CheckBox;
import at.framework.ui.Foundation;
import at.smartshop.database.columns.CNLocationList;
import at.smartshop.database.columns.CNLocationSummary;
import at.smartshop.database.columns.CNNavigationMenu;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.pages.LocationList;
import at.smartshop.pages.LocationSummary;
import at.smartshop.pages.NavigationBar;
@Listeners(at.framework.reportsetup.Listeners.class)
public class AgeVerification extends TestInfra {
	private ResultSets dataBase = new ResultSets();
	private Foundation foundation = new Foundation();
	private NavigationBar navigationBar = new NavigationBar();
	private LocationSummary locationSummary = new LocationSummary();
	private LocationList locationList = new LocationList();
	private CheckBox checkBox = new CheckBox();

	
	private Map<String, String> rstNavigationMenuData;	
	private Map<String, String> rstLocationListData;
	private Map<String, String> rstLocationSummaryData;

	@Test(description = "168264 -Age verification enable by operator"
			           + "168265-Age verification disable by operator")
	public void verifyAgeVerificationByOperator() {
		final String CASE_NUM = "168264";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
	
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
           
			//Select Menu, Menu Item and verify the age verification disabled
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.AGE_VERIFICATION));
			foundation.isDisabled(LocationSummary.AGE_VERIFICATION);
			checkBox.check(LocationSummary.AGE_VERIFICATION);
			foundation.click(LocationSummary.BTN_SAVE);
			
			//Navigate to device
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			foundation.click(LocationSummary.DEVICE_BTN);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.AGE_VERIFICATION));
			
			//Navigate to Location to verify the age verification is enabled
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.AGE_VERIFICATION));
			foundation.isEnabled(LocationSummary.AGE_VERIFICATION);
			checkBox.unCheck(LocationSummary.AGE_VERIFICATION);
			foundation.click(LocationSummary.BTN_SAVE);
	
			//Navigate to deviceSummary
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			foundation.click(LocationSummary.DEVICE_BTN);
			CustomisedAssert.assertFalse(foundation.isDisplayed(LocationSummary.AGE_VERIFICATION));
			
		} 
	
     	catch (Exception exc) {
		TestInfra.failWithScreenShot(exc.toString());
	}}
	
       

	@Test(description = "168266 -Verify the location is availble on age verification screen after it is enabled at location summary page"
	           + "168267-Verify the location is not availble on age verification screen after it is diabled at location summary page")
     public void verifyLocationAvailableInAgeVerification() {
     final String CASE_NUM = "168266";

    // Reading test data from database
    rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
    rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
    
    List<String> menus = Arrays
			.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));

try {
	browser.navigateURL(
			propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
	login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
			propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
	CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));
	navigationBar.selectOrganization(
			propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

	//Select Menu, Menu Item and verify the age verification disabled
	navigationBar.navigateToMenuItem(menus.get(0));
	locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
	foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
	CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.AGE_VERIFICATION));
	foundation.isDisabled(LocationSummary.AGE_VERIFICATION);
	checkBox.check(LocationSummary.AGE_VERIFICATION);
	foundation.click(LocationSummary.BTN_SAVE);
	
	//Navigate to Admin>Age verification
	navigationBar.navigateToMenuItem(menus.get(0));
	
	
	
	
	
}
    catch (Exception exc) {
	TestInfra.failWithScreenShot(exc.toString());
}}}
