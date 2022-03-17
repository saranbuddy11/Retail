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
import at.framework.ui.TextBox;
import at.smartshop.database.columns.CNDeviceList;
import at.smartshop.database.columns.CNLocationList;
import at.smartshop.database.columns.CNNavigationMenu;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.pages.AgeVerificationDetails;
import at.smartshop.pages.DeviceDashboard;
import at.smartshop.pages.DeviceList;
import at.smartshop.pages.DeviceSummary;
import at.smartshop.pages.LocationList;
import at.smartshop.pages.LocationSummary;
import at.smartshop.pages.NavigationBar;
import at.smartshop.pages.OrgList;

@Listeners(at.framework.reportsetup.Listeners.class)
public class AgeVerification extends TestInfra {
	private ResultSets dataBase = new ResultSets();
	private Foundation foundation = new Foundation();
	private NavigationBar navigationBar = new NavigationBar();
	private LocationList locationList = new LocationList();
	private TextBox textBox = new TextBox();
	private CheckBox checkBox = new CheckBox();
	private AgeVerificationDetails ageverificationdetails = new AgeVerificationDetails();

	private Map<String, String> rstNavigationMenuData;
	private Map<String, String> rstLocationListData;
	private Map<String, String> rstDeviceListData;


	@Test(description = "168570 -age verification enable on device summary page"
			+ "168571-age verification disable on device summary page")
	public void verifyAgeVerificationInDeviceAsSuper() {
		final String CASE_NUM = "168570";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);

		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu, Menu Item and verify the age verification disabled
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.AGE_VERIFICATION));
			CustomisedAssert.assertTrue(foundation.isDisabled(LocationSummary.AGE_VERIFICATION));
			checkBox.check(LocationSummary.AGE_VERIFICATION);
			foundation.click(LocationSummary.BTN_SAVE);

			// Navigate to Device
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			foundation.click(LocationSummary.DEVICE_BTN);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.AGE_VERIFICATION));
			checkBox.check(LocationSummary.AGE_VERIFICATION);
			foundation.click(DeviceSummary.BTN_SAVE);
			CustomisedAssert.assertTrue(foundation.isDisplayed(DeviceList.TXT_DEVICE_LIST));

			// Navigate to Device
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			foundation.click(LocationSummary.DEVICE_BTN);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.AGE_VERIFICATION));
			checkBox.unCheck(LocationSummary.AGE_VERIFICATION);
			foundation.click(DeviceSummary.BTN_SAVE);
			CustomisedAssert.assertTrue(foundation.isDisplayed(DeviceList.TXT_DEVICE_LIST));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}

		finally {
			// Navigate to location to disable the age verification
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.AGE_VERIFICATION));
			foundation.isEnabled(LocationSummary.AGE_VERIFICATION);
			foundation.threadWait(Constants.ONE_SECOND);
			checkBox.unCheck(LocationSummary.AGE_VERIFICATION);
			foundation.click(LocationSummary.BTN_SAVE);

		}
	}

	@Test(description = "168574 -age verification enable on device summary page by operator"
			+ "168575-age verification disable on device summary page by operator")
	public void verifyAgeVerificationInDeviceAsOperator() {
		final String CASE_NUM = "168574";

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

			// Select Menu, Menu Item and verify the age verification disabled
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.AGE_VERIFICATION));
			CustomisedAssert.assertTrue(foundation.isDisabled(LocationSummary.AGE_VERIFICATION));
			checkBox.check(LocationSummary.AGE_VERIFICATION);
			foundation.click(LocationSummary.BTN_SAVE);

			// Navigate to Device
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			foundation.click(LocationSummary.DEVICE_BTN);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.AGE_VERIFICATION));
			checkBox.check(LocationSummary.AGE_VERIFICATION);
			foundation.click(DeviceSummary.BTN_SAVE);
			CustomisedAssert.assertTrue(foundation.isDisplayed(DeviceList.TXT_DEVICE_LIST));

			// Navigate to Device
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			foundation.click(LocationSummary.DEVICE_BTN);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.AGE_VERIFICATION));
			checkBox.unCheck(LocationSummary.AGE_VERIFICATION);
			foundation.click(DeviceSummary.BTN_SAVE);
			CustomisedAssert.assertTrue(foundation.isDisplayed(DeviceList.TXT_DEVICE_LIST));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}

		finally {
			// Navigate to location to disable the age verification
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.AGE_VERIFICATION));
			foundation.isEnabled(LocationSummary.AGE_VERIFICATION);
			foundation.threadWait(Constants.ONE_SECOND);
			checkBox.unCheck(LocationSummary.AGE_VERIFICATION);
			foundation.click(LocationSummary.BTN_SAVE);
		}
	}

	@Test(description = "168572-check the other devices on the location after age verification is enabled on one device"
			+ "168573-check the other devices on the location after age verification is disabled on one device")

	public void verifyOtherDeviceInAgeVerification() {
		final String CASE_NUM = "168572";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
		rstDeviceListData = dataBase.getDeviceListData(Queries.DEVICE_LIST, CASE_NUM);

		List<String> location = Arrays
				.asList(rstLocationListData.get(CNLocationList.LOCATION_NAME).split(Constants.DELIMITER_TILD));

		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Select location
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			locationList.selectLocationName(location.get(1));
			foundation.objectFocus(LocationSummary.BTN_DEPLOY_DEVICE);

			// Remove Device from Automation@365 Location
			if (foundation.isDisplayed(LocationSummary.TBL_DEPLOYED_DEVICE_LIST)) {
				foundation.click(LocationSummary.TBL_DEPLOYED_DEVICE_LIST);
				foundation.waitforElement(DeviceDashboard.BTN_LIVE_CONNECTION_STATUS, Constants.SHORT_TIME);
				foundation.click(DeviceDashboard.BTN_REMOVE_DEVICE);
				foundation.waitforElement(DeviceDashboard.BTN_YES_REMOVE, Constants.SHORT_TIME);
				foundation.click(DeviceDashboard.BTN_YES_REMOVE);
				foundation.waitforElement(LocationSummary.BTN_DEPLOY_DEVICE, Constants.SHORT_TIME);
			}

			// Deploy Device to AutomationLocation1 Location
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			locationList.selectLocationName(location.get(0));
			foundation.objectFocus(LocationSummary.BTN_DEPLOY_DEVICE);
			foundation.click(LocationSummary.BTN_DEPLOY_DEVICE);
			foundation.waitforElement(LocationSummary.TXT_FIND_DEVICE, Constants.SHORT_TIME);
			textBox.enterText(LocationSummary.TXT_FIND_DEVICE, rstDeviceListData.get(CNDeviceList.PRODUCT_NAME));
			foundation.click(LocationSummary.TBL_DEVICE_LIST);
			foundation.click(LocationSummary.BTN_ADD_PRODUCT_ADD);
			foundation.waitforElement(LocationSummary.BTN_DEPLOY_DEVICE, Constants.SHORT_TIME);
			foundation.refreshPage();

			// Navigate to Location and enable the age verification
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.AGE_VERIFICATION));
			checkBox.check(LocationSummary.AGE_VERIFICATION);
			foundation.click(LocationSummary.BTN_SAVE);

			// Navigate to Device to enable 122 device
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			locationList.selectLocationName(location.get(0));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(LocationSummary.DEVICE_BTN);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.AGE_VERIFICATION));
			checkBox.check(LocationSummary.AGE_VERIFICATION);
			foundation.click(DeviceSummary.BTN_SAVE);
			CustomisedAssert.assertTrue(foundation.isDisplayed(DeviceList.TXT_DEVICE_LIST));

			// Navigate to Device to enable 115 device
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			locationList.selectLocationName(location.get(0));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(LocationSummary.SECOND_DEVICE);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.AGE_VERIFICATION));
			checkBox.check(LocationSummary.AGE_VERIFICATION);
			foundation.click(DeviceSummary.BTN_SAVE);
			foundation.threadWait(Constants.SHORT_TIME);

			// Navigate to one device to disabled the age verification
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			locationList.selectLocationName(location.get(0));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(LocationSummary.DEVICE_BTN);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.AGE_VERIFICATION));
			checkBox.unCheck(LocationSummary.AGE_VERIFICATION);
			foundation.click(DeviceSummary.BTN_SAVE);
			CustomisedAssert.assertTrue(foundation.isDisplayed(DeviceList.TXT_DEVICE_LIST));

			// Navigate to another device to verify the check box is enable r disable &
			// uncheck
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			locationList.selectLocationName(location.get(0));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(LocationSummary.SECOND_DEVICE);
			CustomisedAssert.assertTrue(checkBox.isChecked(LocationSummary.AGE_VERIFICATION));
			foundation.threadWait(Constants.TWO_SECOND);
			checkBox.unCheck(LocationSummary.AGE_VERIFICATION);
			foundation.click(DeviceSummary.BTN_SAVE);
			foundation.threadWait(Constants.SHORT_TIME);

			// Navigate to Device to enable 122 device
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			locationList.selectLocationName(location.get(0));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(LocationSummary.DEVICE_BTN);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.AGE_VERIFICATION));
			checkBox.check(LocationSummary.AGE_VERIFICATION);
			foundation.click(DeviceSummary.BTN_SAVE);
			CustomisedAssert.assertTrue(foundation.isDisplayed(DeviceList.TXT_DEVICE_LIST));

			// verify the another location
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			locationList.selectLocationName(location.get(0));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(LocationSummary.SECOND_DEVICE);
			CustomisedAssert.assertTrue(checkBox.isChkEnabled(LocationSummary.AGE_VERIFICATION));
			foundation.threadWait(Constants.TWO_SECOND);
			
	
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {

			// Remove Device from AutomationLocation1 Location and uncheck the age
			// verification
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			locationList.selectLocationName(location.get(0));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.AGE_VERIFICATION));
			CustomisedAssert.assertTrue(foundation.isEnabled(LocationSummary.AGE_VERIFICATION));
			checkBox.unCheck(LocationSummary.AGE_VERIFICATION);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.threadWait(Constants.SHORT_TIME);
			locationList.selectLocationName(location.get(0));
			foundation.objectFocus(LocationSummary.BTN_DEPLOY_DEVICE);
			textBox.enterText(LocationSummary.TXT_DEVICE_SEARCH, rstDeviceListData.get(CNDeviceList.PRODUCT_NAME));
			foundation.isDisplayed(LocationSummary.TBL_DEPLOYED_DEVICE_LIST);
			foundation.click(LocationSummary.TBL_DEPLOYED_DEVICE_LIST);
			foundation.waitforElement(DeviceDashboard.BTN_REMOVE_DEVICE, Constants.SHORT_TIME);
			foundation.click(DeviceDashboard.BTN_REMOVE_DEVICE);
			foundation.waitforElement(DeviceDashboard.BTN_YES_REMOVE, Constants.SHORT_TIME);
			foundation.click(DeviceDashboard.BTN_YES_REMOVE);
			foundation.threadWait(Constants.SHORT_TIME);

			// Deploy Device to Automation@365
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			locationList.selectLocationName(location.get(1));
			foundation.objectFocus(LocationSummary.BTN_DEPLOY_DEVICE);
			foundation.click(LocationSummary.BTN_DEPLOY_DEVICE);
			foundation.waitforElement(LocationSummary.TXT_FIND_DEVICE, Constants.SHORT_TIME);
			textBox.enterText(LocationSummary.TXT_FIND_DEVICE, rstDeviceListData.get(CNDeviceList.PRODUCT_NAME));
			foundation.click(LocationSummary.TBL_DEVICE_LIST);
			foundation.click(LocationSummary.BTN_ADD_PRODUCT_ADD);
			foundation.waitforElement(LocationSummary.BTN_DEPLOY_DEVICE, Constants.SHORT_TIME);
			foundation.refreshPage();
			foundation.objectFocus(LocationSummary.BTN_LOCATION_SETTINGS);
			login.logout();
			browser.close();
		}

	}

	@Test(description = "168318-Age verification disable by super" + "168317-Age verification enable by super ")
	public void verifyAgeVerificationInOrgSummaryAsSuper() {
		final String CASE_NUM = "168318";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);

		List<String> menus = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));

		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu, Menu Item and verify the age verification disabled in Org level
			navigationBar.navigateToMenuItem(menus.get(0));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.AGE_VERIFICATION));
			CustomisedAssert.assertTrue(foundation.isEnabled(LocationSummary.AGE_VERIFICATION));
			checkBox.unCheck(LocationSummary.AGE_VERIFICATION);
			foundation.click(LocationSummary.BTN_SAVE);
			CustomisedAssert.assertTrue(foundation.isDisabled(OrgList.LBL_ORG_LIST));

			// Navigate to Location
			navigationBar.navigateToMenuItem(menus.get(1));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			CustomisedAssert.assertFalse(foundation.isDisplayed(LocationSummary.AGE_VERIFICATION));

			// Navigate to Location to verify the age verification is enabled
			navigationBar.navigateToMenuItem(menus.get(0));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.AGE_VERIFICATION));
			CustomisedAssert.assertTrue(foundation.isDisabled(LocationSummary.AGE_VERIFICATION));
			checkBox.check(LocationSummary.AGE_VERIFICATION);
			foundation.click(LocationSummary.BTN_SAVE);
			CustomisedAssert.assertTrue(foundation.isDisabled(OrgList.LBL_ORG_LIST));

			// Navigate to Location
			navigationBar.navigateToMenuItem(menus.get(1));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.AGE_VERIFICATION));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
}
