package at.smartshop.tests;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import at.framework.database.mssql.Queries;
import at.framework.database.mssql.ResultSets;
import at.framework.generic.CustomisedAssert;
import at.framework.generic.DateAndTime;
import at.framework.ui.CheckBox;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.database.columns.CNAdminAgeVerification;
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
import at.smartshop.pages.OrgSummary;

@Listeners(at.framework.reportsetup.Listeners.class)

public class AgeVerification extends TestInfra {
	private ResultSets dataBase = new ResultSets();
	private Foundation foundation = new Foundation();
	private NavigationBar navigationBar = new NavigationBar();
	private TextBox textBox = new TextBox();
	private LocationList locationList = new LocationList();
	private CheckBox checkBox = new CheckBox();
	private Dropdown dropDown = new Dropdown();
	private AgeVerificationDetails ageVerificationDetails = new AgeVerificationDetails();
	private DateAndTime dateAndTime = new DateAndTime();

	private Map<String, String> rstNavigationMenuData;
	private Map<String, String> rstLocationListData;
	private Map<String, String> rstAdminAgeVerificationData;
	private Map<String, String> rstDeviceListData;

	@Test(description = "168894 - Verify the Expire pin confirmation prompt text"
			+ "168895 - Verify the buttons on Expire pin confirmation prompt"
			+ "168896 - Verify the cancel button in Expire pin confirmation prompt"
			+ "168897 - Verify the expiry of the pin" + "168898 - verify expired pins are moving to expiry pin list"
			+ "168899 - check active pin list after cancelling the pin expiration"
			+ "168900 - Verify close button on expire pin confirmation prompt")
	public void verifyExpirePinPrompt() {
		final String CASE_NUM = "168894";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
		rstAdminAgeVerificationData = dataBase.getAdminAgeVerificationData(Queries.ADMIN_AGE_VERIFICATION, CASE_NUM);

		List<String> menus = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> requiredData = Arrays.asList(
				rstAdminAgeVerificationData.get(CNAdminAgeVerification.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		String currentDate = dateAndTime.getDateAndTime(Constants.REGEX_MM_DD_YYYY, Constants.TIME_ZONE_INDIA);
		List<String> status = Arrays
				.asList(rstAdminAgeVerificationData.get(CNAdminAgeVerification.STATUS).split(Constants.DELIMITER_TILD));
		try {
			// Select Menu and Location
			navigationBar.launchBrowserAsSuperAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menus.get(0));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));

			// Verifying the selection of defaults for Age Verification
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_AGE_VERIFICATION));
			if (checkBox.isChkEnabled(LocationSummary.CHK_AGE_VERIFICATION))
				checkBox.check(LocationSummary.CHK_AGE_VERIFICATION);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);

			// Navigate to Admin tab and verify Age Verification Sub Tab is present or not
			List<String> tabNames = navigationBar.getSubTabs(menus.get(1));
			CustomisedAssert.assertEquals(tabNames.get(16),
					rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));
			navigationBar.navigateToMenuItem(menus.get(1));
			CustomisedAssert.assertTrue(foundation.isDisplayed(AgeVerificationDetails.TXT_AGE_VERIFICATION));

			// Verify Expire Pin Confirmation Prompt content, its buttons and cancel the
			// prompt
			ageVerificationDetails.verifyPinExpirationPrompt(
					rstAdminAgeVerificationData.get(CNAdminAgeVerification.LOCATION_NAME), requiredData, status.get(1));

			// Verify Close Button on Expire Pin Confirmation Prompt
			foundation.click(ageVerificationDetails.objExpirePinConfirmation(
					rstAdminAgeVerificationData.get(CNAdminAgeVerification.LOCATION_NAME), requiredData.get(0)));
			foundation.click(AgeVerificationDetails.BTN_CLOSE);
			CustomisedAssert.assertTrue(foundation.isDisplayed(ageVerificationDetails.objExpirePinConfirmation(
					rstAdminAgeVerificationData.get(CNAdminAgeVerification.LOCATION_NAME), requiredData.get(0))));
			foundation.threadWait(Constants.ONE_SECOND);

			// Verify Expire Pin Confirmation Prompt with clicking Yes
			foundation.click(ageVerificationDetails.objExpirePinConfirmation(
					rstAdminAgeVerificationData.get(CNAdminAgeVerification.LOCATION_NAME), requiredData.get(0)));
			foundation.click(AgeVerificationDetails.BTN_YES);
			foundation.refreshPage();
			CustomisedAssert.assertFalse(foundation.isDisplayed(ageVerificationDetails.objExpirePinConfirmation(
					rstAdminAgeVerificationData.get(CNAdminAgeVerification.LOCATION_NAME), requiredData.get(0))));
			foundation.threadWait(Constants.ONE_SECOND);

			// Verify expired pins are moving to expiry pin list
			CustomisedAssert.assertTrue(foundation.isDisplayed(AgeVerificationDetails.TXT_STATUS));
			dropDown.selectItem(AgeVerificationDetails.DPD_STATUS, status.get(2), Constants.TEXT);
			CustomisedAssert.assertTrue(foundation.isDisplayed(ageVerificationDetails
					.objExpiredPinlist(rstAdminAgeVerificationData.get(CNAdminAgeVerification.LOCATION_NAME))));
			CustomisedAssert
					.assertTrue(foundation.isDisplayed(ageVerificationDetails.objExpiredPinlist(requiredData.get(4))));
			CustomisedAssert.assertTrue(foundation.isDisplayed(ageVerificationDetails.objExpiredPinlist(currentDate)));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// Resetting Age Verification Checkbox
			ageVerificationDetails.createAgeVerificationPin(rstLocationListData.get(CNLocationList.LOCATION_NAME),
					requiredData);
			CustomisedAssert.assertTrue(foundation.isDisplayed(ageVerificationDetails.objExpirePinConfirmation(
					rstAdminAgeVerificationData.get(CNAdminAgeVerification.LOCATION_NAME), requiredData.get(0))));
			navigationBar.navigateToMenuItem(menus.get(0));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_AGE_VERIFICATION));
			if (checkBox.isChkEnabled(LocationSummary.CHK_AGE_VERIFICATION))
				checkBox.unCheck(LocationSummary.CHK_AGE_VERIFICATION);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			login.logout();
			browser.close();
		}
	}

	@Test(description = "168901 - Verify the Expire pin confirmation prompt text"
			+ "168902 - Verify the buttons on Expire pin confirmation prompt"
			+ "168903 - Verify the cancel button in Expire pin confirmation prompt"
			+ "168904 - Verify the expiry of the pin" + "168905 - verify expired pins are moving to expiry pin list"
			+ "168906 - check active pin list after cancelling the pin expiration" + "168907 - Verify pin expiry")
	public void verifyExpirePinPromptInOperatorUser() {
		final String CASE_NUM = "168901";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
		rstAdminAgeVerificationData = dataBase.getAdminAgeVerificationData(Queries.ADMIN_AGE_VERIFICATION, CASE_NUM);

		List<String> menus = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> requiredData = Arrays.asList(
				rstAdminAgeVerificationData.get(CNAdminAgeVerification.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		String currentDate = dateAndTime.getDateAndTime(Constants.REGEX_MM_DD_YYYY, Constants.TIME_ZONE_INDIA);
		List<String> status = Arrays
				.asList(rstAdminAgeVerificationData.get(CNAdminAgeVerification.STATUS).split(Constants.DELIMITER_TILD));
		try {
			// Select Menu and Location
			navigationBar.launchBrowserAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menus.get(0));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));

			// Verifying the selection of defaults for Age Verification
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_AGE_VERIFICATION));
			if (checkBox.isChkEnabled(LocationSummary.CHK_AGE_VERIFICATION))
				checkBox.check(LocationSummary.CHK_AGE_VERIFICATION);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);

			// Navigate to Admin tab and verify Age Verification Sub Tab is present or not
			List<String> tabNames = navigationBar.getSubTabs(menus.get(1));
			CustomisedAssert.assertEquals(tabNames.get(14),
					rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));
			navigationBar.navigateToMenuItem(menus.get(1));
			CustomisedAssert.assertTrue(foundation.isDisplayed(AgeVerificationDetails.TXT_AGE_VERIFICATION));

			// Verify Expire Pin Confirmation Prompt content, its buttons and cancel the
			// prompt
			ageVerificationDetails.verifyPinExpirationPrompt(
					rstAdminAgeVerificationData.get(CNAdminAgeVerification.LOCATION_NAME), requiredData, status.get(1));

			// Verify Close Button on Expire Pin Confirmation Prompt
			foundation.click(ageVerificationDetails.objExpirePinConfirmation(
					rstAdminAgeVerificationData.get(CNAdminAgeVerification.LOCATION_NAME), requiredData.get(0)));
			foundation.click(AgeVerificationDetails.BTN_CLOSE);
			CustomisedAssert.assertTrue(foundation.isDisplayed(ageVerificationDetails.objExpirePinConfirmation(
					rstAdminAgeVerificationData.get(CNAdminAgeVerification.LOCATION_NAME), requiredData.get(0))));
			foundation.threadWait(Constants.ONE_SECOND);

			// Verify Expire Pin Confirmation Prompt with clicking Yes
			foundation.click(ageVerificationDetails.objExpirePinConfirmation(
					rstAdminAgeVerificationData.get(CNAdminAgeVerification.LOCATION_NAME), requiredData.get(0)));
			foundation.click(AgeVerificationDetails.BTN_YES);
			foundation.refreshPage();
			CustomisedAssert.assertFalse(foundation.isDisplayed(ageVerificationDetails.objExpirePinConfirmation(
					rstAdminAgeVerificationData.get(CNAdminAgeVerification.LOCATION_NAME), requiredData.get(0))));
			foundation.threadWait(Constants.ONE_SECOND);

			// Verify expired pins are moving to expiry pin list
			CustomisedAssert.assertTrue(foundation.isDisplayed(AgeVerificationDetails.TXT_STATUS));
			dropDown.selectItem(AgeVerificationDetails.DPD_STATUS, status.get(2), Constants.TEXT);
			CustomisedAssert.assertTrue(foundation.isDisplayed(ageVerificationDetails
					.objExpiredPinlist(rstAdminAgeVerificationData.get(CNAdminAgeVerification.LOCATION_NAME))));
			CustomisedAssert
					.assertTrue(foundation.isDisplayed(ageVerificationDetails.objExpiredPinlist(requiredData.get(4))));
			CustomisedAssert.assertTrue(foundation.isDisplayed(ageVerificationDetails.objExpiredPinlist(currentDate)));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// Resetting Age Verification Checkbox
			ageVerificationDetails.createAgeVerificationPin(rstLocationListData.get(CNLocationList.LOCATION_NAME),
					requiredData);
			CustomisedAssert.assertTrue(foundation.isDisplayed(ageVerificationDetails.objExpirePinConfirmation(
					rstAdminAgeVerificationData.get(CNAdminAgeVerification.LOCATION_NAME), requiredData.get(0))));
			navigationBar.navigateToMenuItem(menus.get(0));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_AGE_VERIFICATION));
			if (checkBox.isChkEnabled(LocationSummary.CHK_AGE_VERIFICATION))
				checkBox.unCheck(LocationSummary.CHK_AGE_VERIFICATION);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			login.logout();
			browser.close();
		}
	}

	@Test(description = "168995- Age verification enable by operator")
	public void verifyAgeVerificationByOperator() {
		final String CASE_NUM = "168995";

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

			// Navigate to device
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			foundation.click(LocationSummary.DEVICE_BTN);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.AGE_VERIFICATION));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// Navigate to Location to verify the age verification is enabled
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.AGE_VERIFICATION));
			CustomisedAssert.assertTrue(foundation.isEnabled(LocationSummary.AGE_VERIFICATION));
			checkBox.unCheck(LocationSummary.AGE_VERIFICATION);
			foundation.click(LocationSummary.BTN_SAVE);
		}
	}

	@Test(description = "168999- Age verification enable by super")
	public void verifyAgeVerificationBySuper() {
		final String CASE_NUM = "168999";

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

			// Navigate to device
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			foundation.click(LocationSummary.DEVICE_BTN);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.AGE_VERIFICATION));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// Navigate to Location to verify the age verification is enabled
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.AGE_VERIFICATION));
			CustomisedAssert.assertTrue(foundation.isEnabled(LocationSummary.AGE_VERIFICATION));
			checkBox.unCheck(LocationSummary.AGE_VERIFICATION);
			foundation.click(LocationSummary.BTN_SAVE);
		}
	}

	@Test(description = "168997-Verify the location is availble on age verification screen after it is enabled at location summary page")
	public void verifyLocationAvailableInAgeVerificationAsOperator() {
		final String CASE_NUM = "168997";

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

			// Select Menu, Menu Item and verify the age verification disabled
			navigationBar.navigateToMenuItem(menus.get(0));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.AGE_VERIFICATION));
			foundation.isDisabled(LocationSummary.AGE_VERIFICATION);
			checkBox.check(LocationSummary.AGE_VERIFICATION);
			foundation.click(LocationSummary.BTN_SAVE);

			// Navigate to Admin>Age verification to verify the location
			navigationBar.navigateToMenuItem(menus.get(1));
			CustomisedAssert.assertTrue(foundation.isDisplayed(AgeVerificationDetails.TXT_AGE_VERIFICATION));
			foundation.click(AgeVerificationDetails.DPD_LOCATION);
			foundation.threadWait(Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(ageVerificationDetails
					.automationNewLocation(rstLocationListData.get(CNLocationList.LOCATION_NAME))));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// Navigate to location to disable the age verification
			navigationBar.navigateToMenuItem(menus.get(0));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.AGE_VERIFICATION));
			foundation.isEnabled(LocationSummary.AGE_VERIFICATION);
			checkBox.unCheck(LocationSummary.AGE_VERIFICATION);
			foundation.click(LocationSummary.BTN_SAVE);
		}
	}

	@Test(description = "169001-Verify the location is availble on age verification screen after it is enabled at location summary page")
	public void verifyLocationAvailableInAgeVerificationAsSuper() {
		final String CASE_NUM = "169001";

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

			// Select Menu, Menu Item and verify the age verification disabled
			navigationBar.navigateToMenuItem(menus.get(0));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.AGE_VERIFICATION));
			foundation.isDisabled(LocationSummary.AGE_VERIFICATION);
			checkBox.check(LocationSummary.AGE_VERIFICATION);
			foundation.click(LocationSummary.BTN_SAVE);

			// Navigate to Admin>Age verification to verify the location
			foundation.threadWait(Constants.TWO_SECOND);
			navigationBar.navigateToMenuItem(menus.get(1));
			CustomisedAssert.assertTrue(foundation.isDisplayed(AgeVerificationDetails.TXT_AGE_VERIFICATION));
			foundation.click(AgeVerificationDetails.DPD_LOCATION);
			foundation.threadWait(Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(ageVerificationDetails
					.automationNewLocation(rstLocationListData.get(CNLocationList.LOCATION_NAME))));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// Navigate to location to disable the age verification
			navigationBar.navigateToMenuItem(menus.get(0));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.AGE_VERIFICATION));
			foundation.isEnabled(LocationSummary.AGE_VERIFICATION);
			checkBox.unCheck(LocationSummary.AGE_VERIFICATION);
			foundation.click(LocationSummary.BTN_SAVE);
		}
	}

	/*
	 * <<<<<<< HEAD
	 * 
	 * @Test(description = "168926-Verify no on age verification prompt" +
	 * "168927-Verify yes, continue on age verification prompt") public void
	 * verifyNoOnAgeVerificationPrompt() { final String CASE_NUM = "168926"; =======
	 * 
	 * @Test(description = "168908 -age verification enable on device summary page"
	 * + "168909-age verification disable on device summary page") public void
	 * verifyAgeVerificationInDeviceAsSuper() { final String CASE_NUM = "168908";
	 * 
	 * // Reading test data from database rstNavigationMenuData =
	 * dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
	 * rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST,
	 * CASE_NUM);
	 * 
	 * try { browser.navigateURL(
	 * propertyFile.readPropertyFile(Configuration.CURRENT_URL,
	 * FilePath.PROPERTY_CONFIG_FILE));
	 * login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER,
	 * FilePath.PROPERTY_CONFIG_FILE),
	 * propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD,
	 * FilePath.PROPERTY_CONFIG_FILE));
	 * CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.
	 * LBL_LOCATION_LIST)); navigationBar.selectOrganization(
	 * propertyFile.readPropertyFile(Configuration.CURRENT_ORG,
	 * FilePath.PROPERTY_CONFIG_FILE));
	 * 
	 * // Select Menu, Menu Item and verify the age verification disabled
	 * navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.
	 * MENU_ITEM));
	 * locationList.selectLocationName(rstLocationListData.get(CNLocationList.
	 * LOCATION_NAME)); foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
	 * CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.
	 * AGE_VERIFICATION));
	 * CustomisedAssert.assertTrue(foundation.isDisabled(LocationSummary.
	 * AGE_VERIFICATION)); checkBox.check(LocationSummary.AGE_VERIFICATION);
	 * foundation.click(LocationSummary.BTN_SAVE);
	 * 
	 * // Navigate to Device
	 * navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.
	 * MENU_ITEM));
	 * locationList.selectLocationName(rstLocationListData.get(CNLocationList.
	 * LOCATION_NAME)); foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
	 * foundation.click(LocationSummary.DEVICE_BTN);
	 * CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.
	 * AGE_VERIFICATION)); checkBox.check(LocationSummary.AGE_VERIFICATION);
	 * foundation.click(DeviceSummary.BTN_SAVE);
	 * CustomisedAssert.assertTrue(foundation.isDisplayed(DeviceList.TXT_DEVICE_LIST
	 * ));
	 * 
	 * // Navigate to Device
	 * navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.
	 * MENU_ITEM));
	 * locationList.selectLocationName(rstLocationListData.get(CNLocationList.
	 * LOCATION_NAME)); foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
	 * foundation.click(LocationSummary.DEVICE_BTN);
	 * CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.
	 * AGE_VERIFICATION)); checkBox.unCheck(LocationSummary.AGE_VERIFICATION);
	 * foundation.click(DeviceSummary.BTN_SAVE);
	 * CustomisedAssert.assertTrue(foundation.isDisplayed(DeviceList.TXT_DEVICE_LIST
	 * ));
	 * 
	 * } catch (Exception exc) { TestInfra.failWithScreenShot(exc.toString()); }
	 * 
	 * finally { // Navigate to location to disable the age verification
	 * navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.
	 * MENU_ITEM));
	 * locationList.selectLocationName(rstLocationListData.get(CNLocationList.
	 * LOCATION_NAME)); foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
	 * CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.
	 * AGE_VERIFICATION)); foundation.isEnabled(LocationSummary.AGE_VERIFICATION);
	 * foundation.threadWait(Constants.ONE_SECOND);
	 * checkBox.unCheck(LocationSummary.AGE_VERIFICATION);
	 * foundation.click(LocationSummary.BTN_SAVE);
	 * 
	 * } }
	 * 
	 * @Test(description =
	 * "168910 -age verification enable on device summary page by operator" +
	 * "168911-age verification disable on device summary page by operator") public
	 * void verifyAgeVerificationInDeviceAsOperator() { final String CASE_NUM =
	 * "168910"; >>>>>>> main
	 * 
	 * // Reading test data from database rstNavigationMenuData =
	 * dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
	 * rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST,
	 * CASE_NUM);
	 * 
	 * try { browser.navigateURL(
	 * propertyFile.readPropertyFile(Configuration.CURRENT_URL,
	 * FilePath.PROPERTY_CONFIG_FILE));
	 * login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER,
	 * FilePath.PROPERTY_CONFIG_FILE),
	 * propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD,
	 * FilePath.PROPERTY_CONFIG_FILE));
	 * CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.
	 * LBL_LOCATION_LIST)); navigationBar.selectOrganization(
	 * propertyFile.readPropertyFile(Configuration.CURRENT_ORG,
	 * FilePath.PROPERTY_CONFIG_FILE));
	 * 
	 * // Select Menu, Menu Item and verify the age verification disabled
	 * navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.
	 * MENU_ITEM));
	 * locationList.selectLocationName(rstLocationListData.get(CNLocationList.
	 * LOCATION_NAME)); foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
	 * CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.
	 * AGE_VERIFICATION));
	 * CustomisedAssert.assertTrue(foundation.isDisabled(LocationSummary.
	 * AGE_VERIFICATION)); checkBox.check(LocationSummary.AGE_VERIFICATION);
	 * foundation.click(LocationSummary.BTN_SAVE);
	 * 
	 * <<<<<<< HEAD // Navigate to device ======= // Navigate to Device >>>>>>> main
	 * navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.
	 * MENU_ITEM));
	 * locationList.selectLocationName(rstLocationListData.get(CNLocationList.
	 * LOCATION_NAME)); foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
	 * foundation.click(LocationSummary.DEVICE_BTN);
	 * CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.
	 * AGE_VERIFICATION)); checkBox.check(LocationSummary.AGE_VERIFICATION);
	 * foundation.click(DeviceSummary.BTN_SAVE);
	 * CustomisedAssert.assertTrue(foundation.isDisplayed(DeviceList.TXT_DEVICE_LIST
	 * ));
	 * 
	 * <<<<<<< HEAD // Navigate to Location to verify the age verification prompt in
	 * NO
	 * navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.
	 * MENU_ITEM));
	 * locationList.selectLocationName(rstLocationListData.get(CNLocationList.
	 * LOCATION_NAME)); foundation.threadWait(Constants.SHORT_TIME);
	 * foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
	 * CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.
	 * AGE_VERIFICATION));
	 * CustomisedAssert.assertTrue(foundation.isEnabled(LocationSummary.
	 * AGE_VERIFICATION)); checkBox.unCheck(LocationSummary.AGE_VERIFICATION);
	 * foundation.threadWait(Constants.SHORT_TIME);
	 * foundation.click(LocationSummary.NO_BTN_PROMPT_AGEVERIFICATION);
	 * foundation.click(LocationSummary.BTN_SAVE);
	 * 
	 * // Navigate to Location to verify the age verification prompt in YES
	 * navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.
	 * MENU_ITEM));
	 * locationList.selectLocationName(rstLocationListData.get(CNLocationList.
	 * LOCATION_NAME)); foundation.threadWait(Constants.SHORT_TIME);
	 * foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
	 * CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.
	 * AGE_VERIFICATION)); checkBox.unCheck(LocationSummary.AGE_VERIFICATION);
	 * foundation.threadWait(Constants.SHORT_TIME);
	 * foundation.click(LocationSummary.YES_BTN_PROMPT_AGEVERIFICATION);
	 * foundation.threadWait(Constants.TWO_SECOND);
	 * foundation.click(LocationSummary.BTN_SAVE);
	 * 
	 * // Verify the AgeVerification in location summary and device to ensure it's
	 * // Disbaled
	 * navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.
	 * MENU_ITEM));
	 * locationList.selectLocationName(rstLocationListData.get(CNLocationList.
	 * LOCATION_NAME)); foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
	 * CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.
	 * AGE_VERIFICATION)); foundation.threadWait(Constants.SHORT_TIME);
	 * foundation.click(LocationSummary.DEVICE_BTN);
	 * CustomisedAssert.assertFalse(foundation.isDisplayed(LocationSummary.
	 * AGE_VERIFICATION));
	 * 
	 * ======= // Navigate to Device
	 * navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.
	 * MENU_ITEM));
	 * locationList.selectLocationName(rstLocationListData.get(CNLocationList.
	 * LOCATION_NAME)); foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
	 * foundation.click(LocationSummary.DEVICE_BTN);
	 * CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.
	 * AGE_VERIFICATION)); checkBox.unCheck(LocationSummary.AGE_VERIFICATION);
	 * foundation.click(DeviceSummary.BTN_SAVE);
	 * CustomisedAssert.assertTrue(foundation.isDisplayed(DeviceList.TXT_DEVICE_LIST
	 * ));
	 * 
	 * } catch (Exception exc) { TestInfra.failWithScreenShot(exc.toString()); }
	 * 
	 * finally { // Navigate to location to disable the age verification
	 * navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.
	 * MENU_ITEM));
	 * locationList.selectLocationName(rstLocationListData.get(CNLocationList.
	 * LOCATION_NAME)); foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
	 * CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.
	 * AGE_VERIFICATION)); foundation.isEnabled(LocationSummary.AGE_VERIFICATION);
	 * foundation.threadWait(Constants.ONE_SECOND);
	 * checkBox.unCheck(LocationSummary.AGE_VERIFICATION);
	 * foundation.click(LocationSummary.BTN_SAVE); } }
	 */

	@Test(description = "168572-check the other devices on the location after age verification is enabled on one device"
			+ "169163-check the other devices on the location after age verification is disabled on one device")

	public void verifyOtherDeviceInAgeVerification() {
		final String CASE_NUM = "169162";

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
			foundation.click(LocationSummary.CANCEL_BTN);
            foundation.threadWait(Constants.SHORT_TIME);
			
			// Navigate to Device to enable 115 device
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			locationList.selectLocationName(location.get(0));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(LocationSummary.SECOND_DEVICE);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.AGE_VERIFICATION));
			foundation.click(LocationSummary.CANCEL_BTN);
			CustomisedAssert.assertTrue(foundation.isDisplayed(DeviceList.SUPER_DEVICE));

			// Navigate to Location to verify the age verification is enabled
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			locationList.selectLocationName(location.get(0));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.AGE_VERIFICATION));
			CustomisedAssert.assertTrue(foundation.isEnabled(LocationSummary.AGE_VERIFICATION));
			checkBox.unCheck(LocationSummary.AGE_VERIFICATION);
			foundation.click(LocationSummary.BTN_SAVE);
			

			// Navigate to device 115 to verify the checkbox is displayed or not
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			locationList.selectLocationName(location.get(0));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(LocationSummary.SECOND_DEVICE);
			CustomisedAssert.assertFalse(foundation.isDisplayed(LocationSummary.AGE_VERIFICATION));
			foundation.click(LocationSummary.CANCEL_BTN);
			CustomisedAssert.assertTrue(foundation.isDisplayed(DeviceList.SUPER_DEVICE));

			// Navigate to Device to enable 122 device
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			locationList.selectLocationName(location.get(0));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(LocationSummary.DEVICE_BTN);
			CustomisedAssert.assertFalse(foundation.isDisplayed(LocationSummary.AGE_VERIFICATION));
			foundation.click(LocationSummary.CANCEL_BTN);
			CustomisedAssert.assertTrue(foundation.isDisplayed(DeviceList.SUPER_DEVICE));
			foundation.threadWait(Constants.SHORT_TIME);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {

			// Remove Device from AutomationLocation1 Location and uncheck the age
			// verification
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
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

	@Test(description = "168932-Age verification disable by super" + "168931-Age verification enable by super ")
	public void verifyAgeVerificationInOrgSummaryAsSuper() {
		final String CASE_NUM = "168931";

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
			if (foundation.isDisplayed(OrgSummary.POPUP_LBL_HEADER)) {
				foundation.click(OrgSummary.POPUP_BTN_YES);
				foundation.click(OrgSummary.BTN_SAVE);
				}
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