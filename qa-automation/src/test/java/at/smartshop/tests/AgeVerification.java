package at.smartshop.tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
import at.smartshop.pages.UserRoles;

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
	private UserRoles userRoles = new UserRoles();

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
			foundation.threadWait(Constants.THREE_SECOND);
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));

			// Verifying the selection of defaults for Age Verification
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			foundation.scrollIntoViewElement(LocationSummary.TXT_AGE_VERIFICATION);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_AGE_VERIFICATION));
			if (checkBox.isChkEnabled(LocationSummary.CHK_AGE_VERIFICATION))
				checkBox.check(LocationSummary.CHK_AGE_VERIFICATION);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);

			// Navigate to Admin tab and verify Age Verification Sub Tab is present or not
			List<String> tabNames = navigationBar.getSubTabs(menus.get(1));
			CustomisedAssert.assertTrue(tabNames.contains(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION)));
			navigationBar.navigateToMenuItem(menus.get(1));
			CustomisedAssert.assertTrue(foundation.isDisplayed(AgeVerificationDetails.TXT_AGE_VERIFICATION));

			// Creating Age Verification PIN
			ageVerificationDetails.createAgeVerificationPin(rstLocationListData.get(CNLocationList.LOCATION_NAME),
					requiredData);

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
			foundation.scrollIntoViewElement(AgeVerificationDetails.TXT_STATUS);
			foundation.threadWait(Constants.ONE_SECOND);
			CustomisedAssert.assertFalse(foundation.isDisplayed(ageVerificationDetails.objExpirePinConfirmation(
					rstAdminAgeVerificationData.get(CNAdminAgeVerification.LOCATION_NAME), requiredData.get(0))));
			foundation.threadWait(Constants.ONE_SECOND);

			// Verify expired pins are moving to expiry pin list
			CustomisedAssert.assertTrue(foundation.isDisplayed(AgeVerificationDetails.TXT_STATUS));
			foundation.threadWait(Constants.THREE_SECOND);
			dropDown.selectItem(AgeVerificationDetails.DPD_STATUS, status.get(2), Constants.TEXT);
			textBox.enterText(AgeVerificationDetails.INPUT_TEXT, rstLocationListData.get(CNLocationList.LOCATION_NAME));
			CustomisedAssert.assertTrue(foundation.isDisplayed(ageVerificationDetails
					.objExpiredPinlist(rstAdminAgeVerificationData.get(CNAdminAgeVerification.LOCATION_NAME))));
			CustomisedAssert
					.assertTrue(foundation.isDisplayed(ageVerificationDetails.objExpiredPinlist(requiredData.get(4))));
			CustomisedAssert.assertTrue(foundation.isDisplayed(ageVerificationDetails.objExpiredPinlist(currentDate)));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// Resetting Age Verification Checkbox
			foundation.scrollIntoViewElement(LocationSummary.TAB_LOCATION);
			navigationBar.navigateToMenuItem(menus.get(0));
			foundation.threadWait(Constants.THREE_SECOND);
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			foundation.scrollIntoViewElement(LocationSummary.TXT_AGE_VERIFICATION);
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
			foundation.threadWait(Constants.THREE_SECOND);
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));

			// Verifying the selection of defaults for Age Verification
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			foundation.scrollIntoViewElement(LocationSummary.TXT_AGE_VERIFICATION);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_AGE_VERIFICATION));
			if (checkBox.isChkEnabled(LocationSummary.CHK_AGE_VERIFICATION))
				checkBox.check(LocationSummary.CHK_AGE_VERIFICATION);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);

			// Navigate to Admin tab and verify Age Verification Sub Tab is present or not
			List<String> tabNames = navigationBar.getSubTabs(menus.get(1));
			CustomisedAssert.assertTrue(tabNames.contains(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION)));
			navigationBar.navigateToMenuItem(menus.get(1));
			CustomisedAssert.assertTrue(foundation.isDisplayed(AgeVerificationDetails.TXT_AGE_VERIFICATION));

			// Creating Age Verification PIN
			ageVerificationDetails.createAgeVerificationPin(rstLocationListData.get(CNLocationList.LOCATION_NAME),
					requiredData);

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
			foundation.scrollIntoViewElement(AgeVerificationDetails.TXT_STATUS);
			foundation.threadWait(Constants.ONE_SECOND);
			CustomisedAssert.assertFalse(foundation.isDisplayed(ageVerificationDetails.objExpirePinConfirmation(
					rstAdminAgeVerificationData.get(CNAdminAgeVerification.LOCATION_NAME), requiredData.get(0))));
			foundation.threadWait(Constants.ONE_SECOND);

			// Verify expired pins are moving to expiry pin list
			CustomisedAssert.assertTrue(foundation.isDisplayed(AgeVerificationDetails.TXT_STATUS));
			foundation.threadWait(Constants.THREE_SECOND);
			dropDown.selectItem(AgeVerificationDetails.DPD_STATUS, status.get(2), Constants.TEXT);
			textBox.enterText(AgeVerificationDetails.INPUT_TEXT, rstLocationListData.get(CNLocationList.LOCATION_NAME));
			CustomisedAssert.assertTrue(foundation.isDisplayed(ageVerificationDetails
					.objExpiredPinlist(rstAdminAgeVerificationData.get(CNAdminAgeVerification.LOCATION_NAME))));
			CustomisedAssert
					.assertTrue(foundation.isDisplayed(ageVerificationDetails.objExpiredPinlist(requiredData.get(4))));
			CustomisedAssert.assertTrue(foundation.isDisplayed(ageVerificationDetails.objExpiredPinlist(currentDate)));
			foundation.threadWait(Constants.TWO_SECOND);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// Resetting Age Verification Checkbox
			foundation.scrollIntoViewElement(LocationSummary.TAB_LOCATION);
			navigationBar.navigateToMenuItem(menus.get(0));
			foundation.threadWait(Constants.THREE_SECOND);
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			foundation.scrollIntoViewElement(LocationSummary.TXT_AGE_VERIFICATION);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_AGE_VERIFICATION));
			if (checkBox.isChkEnabled(LocationSummary.CHK_AGE_VERIFICATION))
				checkBox.unCheck(LocationSummary.CHK_AGE_VERIFICATION);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			login.logout();
			browser.close();
		}
	}

	@Test(description = "168912 - verify the default status on pin status filter"
			+ "168913 - verify the options in pin status dropdown" + "168914 - verify the expired option"
			+ "168915 - verify the active option" + "168916 - verify the all option"
			+ "168917 - verify active option on all the pages" + "168918 - verify expired option on all the pages")
	public void verifyDefaultPinStatusInSuperUser() {
		final String CASE_NUM = "168912";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
		rstAdminAgeVerificationData = dataBase.getAdminAgeVerificationData(Queries.ADMIN_AGE_VERIFICATION, CASE_NUM);

		List<String> menus = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> status = Arrays
				.asList(rstAdminAgeVerificationData.get(CNAdminAgeVerification.STATUS).split(Constants.DELIMITER_TILD));
		List<String> requiredData = Arrays.asList(
				rstAdminAgeVerificationData.get(CNAdminAgeVerification.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		try {
			// Select Menu and Location
			navigationBar.launchBrowserAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menus.get(0));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));

			// Verifying the selection of defaults for Age Verification
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			foundation.scrollIntoViewElement(LocationSummary.TXT_AGE_VERIFICATION);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_AGE_VERIFICATION));
			if (checkBox.isChkEnabled(LocationSummary.CHK_AGE_VERIFICATION))
				checkBox.check(LocationSummary.CHK_AGE_VERIFICATION);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);

			// Navigate to Admin tab and verify Age Verification Sub Tab is present or not
			List<String> tabNames = navigationBar.getSubTabs(menus.get(1));
			CustomisedAssert.assertTrue(tabNames.contains(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION)));
			navigationBar.navigateToMenuItem(menus.get(1));
			CustomisedAssert.assertTrue(foundation.isDisplayed(AgeVerificationDetails.TXT_AGE_VERIFICATION));

			// Creating Age Verification PIN
			ageVerificationDetails.createAgeVerificationPin(rstLocationListData.get(CNLocationList.LOCATION_NAME),
					requiredData);

			// Verify Defaults in PIN Status filter
			CustomisedAssert.assertTrue(foundation.isDisplayed(AgeVerificationDetails.TXT_STATUS));
			String actualStatus = dropDown.getSelectedItem(AgeVerificationDetails.DPD_STATUS);
			CustomisedAssert.assertEquals(actualStatus, status.get(1));

			// Verify the Options of PIN status filter
			List<String> options = dropDown.getAllItems(AgeVerificationDetails.DPD_STATUS);
			CustomisedAssert.assertEquals(options.get(0), status.get(0));
			CustomisedAssert.assertEquals(options.get(1), status.get(1));
			CustomisedAssert.assertEquals(options.get(2), status.get(2));

			// Verify Expired PIN status
			dropDown.selectItem(AgeVerificationDetails.DPD_STATUS, status.get(2), Constants.TEXT);
			foundation.scrollIntoViewElement(AgeVerificationDetails.TXT_STATUS);
			foundation.threadWait(Constants.TWO_SECOND);
			Map<Integer, Map<String, String>> uiTableData = ageVerificationDetails.getTblRecordsUI();
			Map<String, String> innerMap = new HashMap<>();
			String innerValue = "";
			for (int i = 0; i < uiTableData.size(); i++) {
				innerMap = uiTableData.get(i);
				innerValue = innerMap.get("Actions");
				CustomisedAssert.assertEquals(innerValue, requiredData.get(0));
			}
			uiTableData.clear();

			// Verify Active PIN Status
			foundation.scrollIntoViewElement(AgeVerificationDetails.TXT_STATUS);
			dropDown.selectItem(AgeVerificationDetails.DPD_STATUS, status.get(1), Constants.TEXT);
			foundation.threadWait(Constants.TWO_SECOND);
			uiTableData = ageVerificationDetails.getTblRecordsUI();
			for (int i = 0; i < uiTableData.size(); i++) {
				innerMap = uiTableData.get(i);
				innerValue = innerMap.get("Actions");
				CustomisedAssert.assertEquals(innerValue, requiredData.get(1));
			}
			uiTableData.clear();

			// Verify ALL options
			dropDown.selectItem(AgeVerificationDetails.DPD_STATUS, status.get(0), Constants.TEXT);
			foundation.threadWait(Constants.TWO_SECOND);
			uiTableData = ageVerificationDetails.getTblRecordsUI();
			for (int i = 0; i < uiTableData.size(); i++) {
				innerMap = uiTableData.get(i);
				innerValue = innerMap.get("Actions");
				String actions = requiredData.get(0) + requiredData.get(1);
				CustomisedAssert.assertTrue(actions.contains(innerValue));
			}
			uiTableData.clear();

			// Verify active option on all the page Records
			dropDown.selectItem(AgeVerificationDetails.DPD_LENGTH, requiredData.get(2), Constants.TEXT);
			int record = Integer.parseInt(dropDown.getSelectedItem(AgeVerificationDetails.DPD_LENGTH));
			// Creating No of Active PIN record for validation
			for (int i = 0; i < record; i++) {
				ageVerificationDetails.createAgeVerificationPin(rstLocationListData.get(CNLocationList.LOCATION_NAME),
						requiredData);
			}
			foundation.threadWait(Constants.ONE_SECOND);
			foundation.scrollIntoViewElement(AgeVerificationDetails.TXT_STATUS);
			dropDown.selectItem(AgeVerificationDetails.DPD_STATUS, status.get(1), Constants.TEXT);
			foundation.click(AgeVerificationDetails.TXT_NEXT);
			uiTableData = ageVerificationDetails.getTblRecordsUI();
			for (int i = 0; i < uiTableData.size(); i++) {
				innerMap = uiTableData.get(i);
				innerValue = innerMap.get("Actions");
				CustomisedAssert.assertEquals(innerValue, requiredData.get(1));
			}
			foundation.refreshPage();
			foundation.waitforElementToDisappear(LocationList.TXT_SPINNER_MSG, Constants.TWO_SECOND);
			uiTableData.clear();

			// Verify expired option on all the page Records
			foundation.scrollIntoViewElement(AgeVerificationDetails.TXT_STATUS);
			dropDown.selectItem(AgeVerificationDetails.DPD_STATUS, status.get(2), Constants.TEXT);
			foundation.scrollIntoViewElement(AgeVerificationDetails.TXT_NEXT);
			foundation.click(AgeVerificationDetails.TXT_NEXT);
			foundation.scrollIntoViewElement(AgeVerificationDetails.TXT_STATUS);
			foundation.threadWait(Constants.TWO_SECOND);
			uiTableData = ageVerificationDetails.getTblRecordsUI();
			for (int i = 0; i < uiTableData.size(); i++) {
				innerMap = uiTableData.get(i);
				innerValue = innerMap.get("Actions");
				CustomisedAssert.assertEquals(innerValue, requiredData.get(0));
			}
			foundation.refreshPage();
			foundation.waitforElementToDisappear(LocationList.TXT_SPINNER_MSG, Constants.TWO_SECOND);
			uiTableData.clear();

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// Deleting No of Active PIN record After validation
			foundation.scrollIntoViewElement(AgeVerificationDetails.DPD_LENGTH);
			int record = Integer.parseInt(dropDown.getSelectedItem(AgeVerificationDetails.DPD_LENGTH));
			foundation.scrollIntoViewElement(AgeVerificationDetails.TXT_STATUS);
			dropDown.selectItem(AgeVerificationDetails.DPD_STATUS, status.get(1), Constants.TEXT);
			foundation.threadWait(Constants.THREE_SECOND);
			for (int i = 0; i < record + 1; i++) {
				foundation.click(ageVerificationDetails.objExpirePinConfirmationWithIndex(
						rstLocationListData.get(CNLocationList.LOCATION_NAME), requiredData.get(8), String.valueOf(1)));
				foundation.click(AgeVerificationDetails.BTN_YES);
				foundation.refreshPage();
				foundation.waitforElementToDisappear(LocationList.TXT_SPINNER_MSG, Constants.TWO_SECOND);
			}
		}
	}

	@Test(description = "168919 - verify the default status on pin status filter"
			+ "168920 - verify the options in pin status dropdown" + "168921 - verify the expired option"
			+ "168922 - verify the active option" + "168923 - verify the all option"
			+ "168924 - verify active option on all the pages" + "168925 - verify expired option on all the pages")
	public void verifyDefaultPinStatusInOperatorUser() {
		final String CASE_NUM = "168919";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
		rstAdminAgeVerificationData = dataBase.getAdminAgeVerificationData(Queries.ADMIN_AGE_VERIFICATION, CASE_NUM);

		List<String> menus = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> status = Arrays
				.asList(rstAdminAgeVerificationData.get(CNAdminAgeVerification.STATUS).split(Constants.DELIMITER_TILD));
		List<String> requiredData = Arrays.asList(
				rstAdminAgeVerificationData.get(CNAdminAgeVerification.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		try {
			// Select Menu and Location
			navigationBar.launchBrowserAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menus.get(0));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));

			// Verifying the selection of defaults for Age Verification
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			foundation.scrollIntoViewElement(LocationSummary.TXT_AGE_VERIFICATION);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_AGE_VERIFICATION));
			if (checkBox.isChkEnabled(LocationSummary.CHK_AGE_VERIFICATION))
				checkBox.check(LocationSummary.CHK_AGE_VERIFICATION);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);

			// Navigate to Admin tab and verify Age Verification Sub Tab is present or not
			List<String> tabNames = navigationBar.getSubTabs(menus.get(1));
			CustomisedAssert.assertTrue(tabNames.contains(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION)));
			navigationBar.navigateToMenuItem(menus.get(1));
			CustomisedAssert.assertTrue(foundation.isDisplayed(AgeVerificationDetails.TXT_AGE_VERIFICATION));

			// Creating Age Verification PIN
			ageVerificationDetails.createAgeVerificationPin(rstLocationListData.get(CNLocationList.LOCATION_NAME),
					requiredData);

			// Verify Defaults in PIN Status filter
			CustomisedAssert.assertTrue(foundation.isDisplayed(AgeVerificationDetails.TXT_STATUS));
			String actualStatus = dropDown.getSelectedItem(AgeVerificationDetails.DPD_STATUS);
			CustomisedAssert.assertEquals(actualStatus, status.get(1));

			// Verify the Options of PIN status filter
			List<String> options = dropDown.getAllItems(AgeVerificationDetails.DPD_STATUS);
			CustomisedAssert.assertEquals(options.get(0), status.get(0));
			CustomisedAssert.assertEquals(options.get(1), status.get(1));
			CustomisedAssert.assertEquals(options.get(2), status.get(2));

			// Verify Expired PIN status
			dropDown.selectItem(AgeVerificationDetails.DPD_STATUS, status.get(2), Constants.TEXT);
			foundation.scrollIntoViewElement(AgeVerificationDetails.TXT_STATUS);
			foundation.threadWait(Constants.TWO_SECOND);
			Map<Integer, Map<String, String>> uiTableData = ageVerificationDetails.getTblRecordsUI();
			Map<String, String> innerMap = new HashMap<>();
			String innerValue = "";
			for (int i = 0; i < uiTableData.size(); i++) {
				innerMap = uiTableData.get(i);
				innerValue = innerMap.get("Actions");
				CustomisedAssert.assertEquals(innerValue, requiredData.get(0));
			}
			uiTableData.clear();

			// Verify Active PIN Status
			foundation.scrollIntoViewElement(AgeVerificationDetails.TXT_STATUS);
			dropDown.selectItem(AgeVerificationDetails.DPD_STATUS, status.get(1), Constants.TEXT);
			foundation.threadWait(Constants.TWO_SECOND);
			uiTableData = ageVerificationDetails.getTblRecordsUI();
			for (int i = 0; i < uiTableData.size(); i++) {
				innerMap = uiTableData.get(i);
				innerValue = innerMap.get("Actions");
				CustomisedAssert.assertEquals(innerValue, requiredData.get(1));
			}
			uiTableData.clear();

			// Verify ALL options
			dropDown.selectItem(AgeVerificationDetails.DPD_STATUS, status.get(0), Constants.TEXT);
			foundation.threadWait(Constants.TWO_SECOND);
			uiTableData = ageVerificationDetails.getTblRecordsUI();
			for (int i = 0; i < uiTableData.size(); i++) {
				innerMap = uiTableData.get(i);
				innerValue = innerMap.get("Actions");
				String actions = requiredData.get(0) + requiredData.get(1);
				CustomisedAssert.assertTrue(actions.contains(innerValue));
			}
			uiTableData.clear();

			// Verify active option on all the page Records
			dropDown.selectItem(AgeVerificationDetails.DPD_LENGTH, requiredData.get(2), Constants.TEXT);
			int record = Integer.parseInt(dropDown.getSelectedItem(AgeVerificationDetails.DPD_LENGTH));
			// Creating No of Active PIN record for validation
			for (int i = 0; i < record; i++) {
				ageVerificationDetails.createAgeVerificationPin(rstLocationListData.get(CNLocationList.LOCATION_NAME),
						requiredData);
			}
			foundation.threadWait(Constants.ONE_SECOND);
			foundation.scrollIntoViewElement(AgeVerificationDetails.TXT_STATUS);
			dropDown.selectItem(AgeVerificationDetails.DPD_STATUS, status.get(1), Constants.TEXT);
			foundation.click(AgeVerificationDetails.TXT_NEXT);
			uiTableData = ageVerificationDetails.getTblRecordsUI();
			for (int i = 0; i < uiTableData.size(); i++) {
				innerMap = uiTableData.get(i);
				innerValue = innerMap.get("Actions");
				CustomisedAssert.assertEquals(innerValue, requiredData.get(1));
			}
			foundation.refreshPage();
			foundation.waitforElementToDisappear(LocationList.TXT_SPINNER_MSG, Constants.TWO_SECOND);
			uiTableData.clear();

			// Verify expired option on all the page Records
			foundation.scrollIntoViewElement(AgeVerificationDetails.TXT_STATUS);
			dropDown.selectItem(AgeVerificationDetails.DPD_STATUS, status.get(2), Constants.TEXT);
			foundation.scrollIntoViewElement(AgeVerificationDetails.TXT_NEXT);
			foundation.click(AgeVerificationDetails.TXT_NEXT);
			foundation.scrollIntoViewElement(AgeVerificationDetails.TXT_STATUS);
			foundation.threadWait(Constants.TWO_SECOND);
			uiTableData = ageVerificationDetails.getTblRecordsUI();
			for (int i = 0; i < uiTableData.size(); i++) {
				innerMap = uiTableData.get(i);
				innerValue = innerMap.get("Actions");
				CustomisedAssert.assertEquals(innerValue, requiredData.get(0));
			}
			foundation.refreshPage();
			foundation.waitforElementToDisappear(LocationList.TXT_SPINNER_MSG, Constants.TWO_SECOND);
			uiTableData.clear();

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// Deleting No of Active PIN record After validation
			foundation.scrollIntoViewElement(AgeVerificationDetails.DPD_LENGTH);
			int record = Integer.parseInt(dropDown.getSelectedItem(AgeVerificationDetails.DPD_LENGTH));
			foundation.scrollIntoViewElement(AgeVerificationDetails.TXT_STATUS);
			dropDown.selectItem(AgeVerificationDetails.DPD_STATUS, status.get(1), Constants.TEXT);
			for (int i = 0; i < record + 1; i++) {
				foundation.click(ageVerificationDetails.objExpirePinConfirmationWithIndex(
						rstLocationListData.get(CNLocationList.LOCATION_NAME), requiredData.get(8), String.valueOf(1)));
				foundation.click(AgeVerificationDetails.BTN_YES);
				foundation.refreshPage();
				foundation.waitforElementToDisappear(LocationList.TXT_SPINNER_MSG, Constants.TWO_SECOND);
			}
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
			foundation.scrollIntoViewElement(LocationSummary.TXT_AGE_VERIFICATION);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_AGE_VERIFICATION));
			if (checkBox.isChkEnabled(LocationSummary.CHK_AGE_VERIFICATION))
				checkBox.check(LocationSummary.CHK_AGE_VERIFICATION);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);

			// Navigate to device
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			foundation.scrollIntoViewElement(LocationSummary.DEVICE_BTN);
			foundation.click(LocationSummary.DEVICE_BTN);
			foundation.waitforElementToBeVisible(DeviceSummary.LBL_DEVICE_SUMMARY, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.AGE_VERIFICATION));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// Navigate to Location to verify the age verification is enabled
			foundation.scrollIntoViewElement(LocationSummary.TAB_LOCATION);
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			foundation.scrollIntoViewElement(LocationSummary.TXT_AGE_VERIFICATION);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_AGE_VERIFICATION));
			if (checkBox.isChecked(LocationSummary.CHK_AGE_VERIFICATION))
				checkBox.unCheck(LocationSummary.CHK_AGE_VERIFICATION);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
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
			foundation.scrollIntoViewElement(LocationSummary.TXT_AGE_VERIFICATION);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_AGE_VERIFICATION));
			if (checkBox.isChkEnabled(LocationSummary.CHK_AGE_VERIFICATION))
				checkBox.check(LocationSummary.CHK_AGE_VERIFICATION);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);

			// Navigate to device
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			foundation.scrollIntoViewElement(LocationSummary.DEVICE_BTN);
			foundation.click(LocationSummary.DEVICE_BTN);
			foundation.waitforElementToBeVisible(DeviceSummary.LBL_DEVICE_SUMMARY, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.AGE_VERIFICATION));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// Navigate to Location to verify the age verification is enabled
			foundation.scrollIntoViewElement(LocationSummary.TAB_LOCATION);
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			foundation.scrollIntoViewElement(LocationSummary.TXT_AGE_VERIFICATION);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_AGE_VERIFICATION));
			if (checkBox.isChecked(LocationSummary.CHK_AGE_VERIFICATION))
				checkBox.unCheck(LocationSummary.CHK_AGE_VERIFICATION);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
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
			foundation.threadWait(Constants.THREE_SECOND);

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
			foundation.scrollIntoViewElement(LocationSummary.TAB_LOCATION);
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
			foundation.threadWait(Constants.THREE_SECOND);

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
			foundation.scrollIntoViewElement(LocationSummary.TAB_LOCATION);
			navigationBar.navigateToMenuItem(menus.get(0));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.AGE_VERIFICATION));
			foundation.isEnabled(LocationSummary.AGE_VERIFICATION);
			checkBox.unCheck(LocationSummary.AGE_VERIFICATION);
			foundation.click(LocationSummary.BTN_SAVE);
		}
	}

	@Test(description = "168890 - verify age verification sub menu for age verification enabled org by super when it is enable"
			+ "168892 - verify age verification sub menu for age verification enabled org by super when it is disable")
	public void verifyAgeVerificationSubMenu() {
		final String CASE_NUM = "168890";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
		rstAdminAgeVerificationData = dataBase.getAdminAgeVerificationData(Queries.ADMIN_AGE_VERIFICATION, CASE_NUM);

		List<String> menus = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		try {
			// Select Menu and Location
			navigationBar.launchBrowserAsSuperAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menus.get(0));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));

			// Verifying the selection of defaults for Age Verification and Enabling It
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			foundation.threadWait(Constants.TWO_SECOND);
			foundation.scrollIntoViewElement(LocationSummary.TXT_AGE_VERIFICATION);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_AGE_VERIFICATION));
			if (checkBox.isChkEnabled(LocationSummary.CHK_AGE_VERIFICATION))
				checkBox.check(LocationSummary.CHK_AGE_VERIFICATION);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);

			// Navigate to Admin tab and verify Age Verification Sub Tab is present or not
			List<String> tabNames = navigationBar.getSubTabs(menus.get(1));
			CustomisedAssert.assertTrue(tabNames.contains(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION)));
			navigationBar.navigateToMenuItem(menus.get(1));
			CustomisedAssert.assertTrue(foundation.isDisplayed(AgeVerificationDetails.TXT_AGE_VERIFICATION));

			// Disabling Age Verification under Location Summary
			navigationBar.navigateToMenuItem(menus.get(0));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_AGE_VERIFICATION));
			if (checkBox.isChkEnabled(LocationSummary.CHK_AGE_VERIFICATION))
				checkBox.unCheck(LocationSummary.CHK_AGE_VERIFICATION);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);

			// Navigate to Admin tab>Age Verification Sub Tab
			navigationBar.navigateToMenuItem(menus.get(1));
			CustomisedAssert.assertTrue(foundation.isDisplayed(AgeVerificationDetails.TXT_AGE_VERIFICATION));

			// Verify Location Drop Down for Location Which is Disabled the Age Verification
			tabNames = dropDown.getAllItems(AgeVerificationDetails.DPD_LOCATION);
			CustomisedAssert.assertFalse(tabNames.contains(rstLocationListData.get(CNLocationList.LOCATION_NAME)));

			// Navigate to Org Summary and Disable Age Verification
			navigationBar.navigateToMenuItem(menus.get(2));
			CustomisedAssert.assertTrue(foundation.isDisplayed(OrgSummary.LBL_ORG_SUMMARY));
			foundation.scrollIntoViewElement(OrgSummary.TXT_AGE_VERIFICATION);
			CustomisedAssert.assertTrue(foundation.isDisplayed(OrgSummary.TXT_AGE_VERIFICATION));
			if (checkBox.isChkEnabled(OrgSummary.CHK_AGE_VERIFICATION))
				checkBox.unCheck(OrgSummary.CHK_AGE_VERIFICATION);
			foundation.threadWait(Constants.TWO_SECOND);
			if (foundation.isDisplayed(OrgSummary.POPUP_LBL_HEADER)) {
				foundation.click(OrgSummary.POPUP_BTN_YES);
				foundation.click(OrgSummary.BTN_SAVE);
				foundation.threadWait(Constants.THREE_SECOND);
			} else {
				foundation.click(OrgSummary.BTN_SAVE);
				foundation.threadWait(Constants.THREE_SECOND);
			}
			foundation.threadWait(Constants.ONE_SECOND);
			foundation.waitforElement(OrgList.LBL_ORG_LIST, Constants.SHORT_TIME);
			foundation.threadWait(Constants.TWO_SECOND);

			// Navigate to Admin tab and verify Age Verification Sub Tab is present or not
			tabNames = navigationBar.getSubTabs(menus.get(1));
			CustomisedAssert
					.assertFalse(tabNames.contains(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION)));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// Resetting Age Verification Checkbox in ORG Summary
			foundation.scrollIntoViewElement(LocationSummary.TAB_LOCATION);
			navigationBar.navigateToMenuItem(menus.get(2));
			CustomisedAssert.assertTrue(foundation.isDisplayed(OrgSummary.LBL_ORG_SUMMARY));
			foundation.scrollIntoViewElement(OrgSummary.TXT_AGE_VERIFICATION);
			CustomisedAssert.assertTrue(foundation.isDisplayed(OrgSummary.TXT_AGE_VERIFICATION));
			if (checkBox.isChkEnabled(OrgSummary.CHK_AGE_VERIFICATION))
				checkBox.check(OrgSummary.CHK_AGE_VERIFICATION);
			foundation.click(OrgSummary.BTN_SAVE);
			foundation.waitforElement(OrgList.LBL_ORG_LIST, Constants.SHORT_TIME);
			foundation.threadWait(Constants.TWO_SECOND);
			login.logout();
			browser.close();
		}
	}

	@Test(description = "168891 - verify age verification sub menu for age verification enabled org by operator when it is enable"
			+ "168893 - verify age verification sub menu for age verification disabled org by operator when it is disable")
	public void verifyAgeVerificationSubMenuWithOperatorUser() {
		final String CASE_NUM = "168891";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
		rstAdminAgeVerificationData = dataBase.getAdminAgeVerificationData(Queries.ADMIN_AGE_VERIFICATION, CASE_NUM);

		List<String> menus = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		try {
			// Select Menu and Location
			navigationBar.launchBrowserAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menus.get(0));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));

			// Verifying the selection of defaults for Age Verification and Enabling It
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			foundation.scrollIntoViewElement(LocationSummary.TXT_AGE_VERIFICATION);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_AGE_VERIFICATION));
			if (checkBox.isChkEnabled(LocationSummary.CHK_AGE_VERIFICATION))
				checkBox.check(LocationSummary.CHK_AGE_VERIFICATION);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);

			// Navigate to Admin tab and verify Age Verification Sub Tab is present or not
			List<String> tabNames = navigationBar.getSubTabs(menus.get(1));
			CustomisedAssert.assertTrue(tabNames.contains(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION)));
			navigationBar.navigateToMenuItem(menus.get(1));
			CustomisedAssert.assertTrue(foundation.isDisplayed(AgeVerificationDetails.TXT_AGE_VERIFICATION));
			login.logout();

			// Login with Super user to disable Age verification under Org Summary
			navigationBar.launchBrowserAsSuperAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menus.get(2));
			CustomisedAssert.assertTrue(foundation.isDisplayed(OrgSummary.LBL_ORG_SUMMARY));
			foundation.scrollIntoViewElement(OrgSummary.TXT_AGE_VERIFICATION);
			CustomisedAssert.assertTrue(foundation.isDisplayed(OrgSummary.TXT_AGE_VERIFICATION));
			if (checkBox.isChkEnabled(OrgSummary.CHK_AGE_VERIFICATION))
				checkBox.unCheck(OrgSummary.CHK_AGE_VERIFICATION);
			foundation.threadWait(Constants.TWO_SECOND);
			if (foundation.isDisplayed(OrgSummary.POPUP_LBL_HEADER)) {
				foundation.click(OrgSummary.POPUP_BTN_YES);
				foundation.click(OrgSummary.BTN_SAVE);
				foundation.threadWait(Constants.THREE_SECOND);
			} else {
				foundation.click(OrgSummary.BTN_SAVE);
				foundation.threadWait(Constants.THREE_SECOND);
			}
			foundation.threadWait(Constants.ONE_SECOND);
			foundation.waitforElement(OrgList.LBL_ORG_LIST, Constants.SHORT_TIME);
			foundation.threadWait(Constants.TWO_SECOND);
			login.logout();

			// Relogin with Operator User to verify Age Verification
			navigationBar.launchBrowserAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			foundation.threadWait(Constants.ONE_SECOND);
			navigationBar.navigateToMenuItem(menus.get(0));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.threadWait(Constants.ONE_SECOND);
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			CustomisedAssert.assertFalse(foundation.isDisplayed(LocationSummary.TXT_AGE_VERIFICATION));

			// Navigate to Admin tab and verify Age Verification Sub Tab is present or not
			tabNames = navigationBar.getSubTabs(menus.get(1));
			CustomisedAssert
					.assertFalse(tabNames.contains(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION)));
			foundation.threadWait(Constants.TWO_SECOND);
			login.logout();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// Resetting Age Verification Checkbox in ORG Summary
			navigationBar.launchBrowserAsSuperAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menus.get(2));
			CustomisedAssert.assertTrue(foundation.isDisplayed(OrgSummary.LBL_ORG_SUMMARY));
			foundation.scrollIntoViewElement(OrgSummary.TXT_AGE_VERIFICATION);
			CustomisedAssert.assertTrue(foundation.isDisplayed(OrgSummary.TXT_AGE_VERIFICATION));
			if (checkBox.isChkEnabled(OrgSummary.CHK_AGE_VERIFICATION))
				checkBox.check(OrgSummary.CHK_AGE_VERIFICATION);
			foundation.click(OrgSummary.BTN_SAVE);
			foundation.waitforElement(OrgList.LBL_ORG_LIST, Constants.SHORT_TIME);
			foundation.threadWait(Constants.TWO_SECOND);
			login.logout();
			browser.close();
		}
	}

	@Test(description = "168933 - Verify all the fields in Active pin panel" + "168934 - Verify the pagination"
			+ "168935 - verify next page button" + "168936 - verify previous page button")
	public void verifyAllFieldsOfAgeVerificationPanel() {
		final String CASE_NUM = "168933";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
		rstAdminAgeVerificationData = dataBase.getAdminAgeVerificationData(Queries.ADMIN_AGE_VERIFICATION, CASE_NUM);

		List<String> menus = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> requiredData = Arrays.asList(
				rstAdminAgeVerificationData.get(CNAdminAgeVerification.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
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
			CustomisedAssert.assertTrue(tabNames.contains(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION)));
			navigationBar.navigateToMenuItem(menus.get(1));
			CustomisedAssert.assertTrue(foundation.isDisplayed(AgeVerificationDetails.TXT_AGE_VERIFICATION));

			// Creating Age Verification PIN
			ageVerificationDetails.createAgeVerificationPin(rstLocationListData.get(CNLocationList.LOCATION_NAME),
					requiredData);

			// Verify the fields in Age verification setup
			CustomisedAssert.assertEquals(foundation.getAttribute(
					AgeVerificationDetails.LBL_AGE_VERIFICATION_SETUP_PANEL, requiredData.get(0)), requiredData.get(1));
			ageVerificationDetails.verifyAllFieldsOfAgeVerificationSetup();

			// Verify the fields in Active PIN's Panel
			CustomisedAssert.assertTrue(foundation.isDisplayed(AgeVerificationDetails.TXT_STATUS));
			List<String> options = dropDown.getAllItems(AgeVerificationDetails.DPD_STATUS);
			CustomisedAssert.assertEquals(options.get(0), status.get(0));
			CustomisedAssert.assertEquals(options.get(1), status.get(1));
			CustomisedAssert.assertEquals(options.get(2), status.get(2));
			ageVerificationDetails.verifyAllFieldsOfActivePins();

			// Verify the No of Records in a Page
			ageVerificationDetails.verifyPagination(requiredData.get(8), status.get(2));
			ageVerificationDetails.verifyPagination(requiredData.get(9), status.get(2));
			ageVerificationDetails.verifyPagination(requiredData.get(10), status.get(2));
			ageVerificationDetails.verifyPagination(requiredData.get(11), status.get(2));

			// Verify Next Button under Active PIN Panel
			foundation.scrollIntoViewElement(AgeVerificationDetails.TXT_NEXT);
			foundation.click(AgeVerificationDetails.TXT_NEXT);
			Map<Integer, Map<String, String>> uiTableData = ageVerificationDetails.getTblRecordsUI();
			int record = uiTableData.size();
			CustomisedAssert.assertEquals(String.valueOf(record), requiredData.get(11));

			// Verify Previous Button under Active PIN Panel
			foundation.scrollIntoViewElement(AgeVerificationDetails.TXT_PREVIOUS);
			foundation.click(AgeVerificationDetails.TXT_PREVIOUS);
			uiTableData = ageVerificationDetails.getTblRecordsUI();
			record = uiTableData.size();
			CustomisedAssert.assertEquals(String.valueOf(record), requiredData.get(11));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// Deleting Active PIN record After validation
			dropDown.selectItem(AgeVerificationDetails.DPD_STATUS, status.get(1), Constants.TEXT);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(ageVerificationDetails.objExpirePinConfirmation(
					rstLocationListData.get(CNLocationList.LOCATION_NAME), requiredData.get(2)));
			foundation.click(AgeVerificationDetails.BTN_YES);
			foundation.refreshPage();
			foundation.waitforElementToDisappear(LocationList.TXT_SPINNER_MSG, Constants.TWO_SECOND);
			// Resetting Age Verification Checkbox
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

	@Test(description = "168908 -age verification enable on device summary page"
			+ "168909-age verification disable on device summary page")
	public void verifyAgeVerificationInDeviceAsSuper() {
		final String CASE_NUM = "168908";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
		rstAdminAgeVerificationData = dataBase.getAdminAgeVerificationData(Queries.ADMIN_AGE_VERIFICATION, CASE_NUM);

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
			foundation.scrollIntoViewElement(LocationSummary.TXT_AGE_VERIFICATION);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_AGE_VERIFICATION));
			if (checkBox.isChkEnabled(LocationSummary.CHK_AGE_VERIFICATION))
				checkBox.check(LocationSummary.CHK_AGE_VERIFICATION);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);

			// Navigate to Device to check the age verification
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			foundation.click(LocationSummary.DEVICE_BTN);
			foundation.waitforElementToBeVisible(DeviceSummary.LBL_DEVICE_SUMMARY, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.AGE_VERIFICATION));
			checkBox.check(LocationSummary.AGE_VERIFICATION);
			foundation.click(DeviceSummary.BTN_SAVE);
			foundation.threadWait(Constants.THREE_SECOND);
			CustomisedAssert.assertTrue(foundation.isDisplayed(DeviceList.TXT_DEVICE_LIST));

			// Navigate to Device to Uncheck the age verification
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			foundation.click(LocationSummary.DEVICE_BTN);
			foundation.waitforElementToBeVisible(DeviceSummary.LBL_DEVICE_SUMMARY, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.AGE_VERIFICATION));
			checkBox.unCheck(LocationSummary.AGE_VERIFICATION);
			foundation.click(DeviceSummary.BTN_SAVE);
			foundation.threadWait(Constants.THREE_SECOND);
			CustomisedAssert.assertTrue(foundation.isDisplayed(DeviceList.TXT_DEVICE_LIST));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// Navigate to location to disable the age verification
			foundation.scrollIntoViewElement(LocationSummary.TAB_LOCATION);
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			foundation.scrollIntoViewElement(LocationSummary.TXT_AGE_VERIFICATION);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_AGE_VERIFICATION));
			if (checkBox.isChecked(LocationSummary.CHK_AGE_VERIFICATION))
				checkBox.unCheck(LocationSummary.CHK_AGE_VERIFICATION);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
		}
	}

	@Test(description = "168910 -age verification enable on device summary page by operator"
			+ "168911-age verification disable on device summary page by operator")
	public void verifyAgeVerificationInDeviceAsOperator() {
		final String CASE_NUM = "168910";

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
			foundation.scrollIntoViewElement(LocationSummary.TXT_AGE_VERIFICATION);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_AGE_VERIFICATION));
			if (checkBox.isChkEnabled(LocationSummary.CHK_AGE_VERIFICATION))
				checkBox.check(LocationSummary.CHK_AGE_VERIFICATION);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);

			// Navigate to Device to check the age verification
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			foundation.scrollIntoViewElement(LocationSummary.DEVICE_BTN);
			foundation.click(LocationSummary.DEVICE_BTN);
			foundation.waitforElementToBeVisible(DeviceSummary.LBL_DEVICE_SUMMARY, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.AGE_VERIFICATION));
			checkBox.check(LocationSummary.AGE_VERIFICATION);
			foundation.click(DeviceSummary.BTN_SAVE);
			foundation.threadWait(Constants.THREE_SECOND);
			CustomisedAssert.assertTrue(foundation.isDisplayed(DeviceList.TXT_DEVICE_LIST));

			// Navigate to Device to Uncheck the age verification
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			foundation.click(LocationSummary.DEVICE_BTN);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.AGE_VERIFICATION));
			checkBox.unCheck(LocationSummary.AGE_VERIFICATION);
			foundation.click(DeviceSummary.BTN_SAVE);
			foundation.threadWait(Constants.THREE_SECOND);
			CustomisedAssert.assertTrue(foundation.isDisplayed(DeviceList.TXT_DEVICE_LIST));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// Navigate to location to disable the age verification
			foundation.scrollIntoViewElement(LocationSummary.TAB_LOCATION);
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			foundation.scrollIntoViewElement(LocationSummary.TXT_AGE_VERIFICATION);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_AGE_VERIFICATION));
			if (checkBox.isChecked(LocationSummary.CHK_AGE_VERIFICATION))
				checkBox.unCheck(LocationSummary.CHK_AGE_VERIFICATION);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
		}
	}

	@Test(description = "169162-check the other devices on the location after age verification is enabled on one device"
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
			foundation.threadWait(Constants.SHORT_TIME);

			// Navigate to Device to enable 122 device
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			locationList.selectLocationName(location.get(0));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(LocationSummary.DEVICE_BTN);
			foundation.waitforElementToBeVisible(DeviceSummary.LBL_DEVICE_SUMMARY, Constants.SHORT_TIME);
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
			foundation.threadWait(Constants.SHORT_TIME);

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
			foundation.scrollIntoViewElement(LocationSummary.TAB_LOCATION);
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
			foundation.scrollIntoViewElement(LocationSummary.AGE_VERIFICATION);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.AGE_VERIFICATION));
			CustomisedAssert.assertTrue(foundation.isEnabled(LocationSummary.AGE_VERIFICATION));
			checkBox.unCheck(LocationSummary.AGE_VERIFICATION);
			foundation.threadWait(Constants.THREE_SECOND);
			if (foundation.isDisplayed(OrgSummary.POPUP_LBL_HEADER)) {
				foundation.click(OrgSummary.POPUP_BTN_YES);
				foundation.click(OrgSummary.BTN_SAVE);
				foundation.threadWait(Constants.THREE_SECOND);
			} else
				foundation.click(LocationSummary.BTN_SAVE);
			foundation.threadWait(Constants.THREE_SECOND);
			CustomisedAssert.assertTrue(foundation.isDisplayed(OrgList.LBL_ORG_LIST));

			// Navigate to Location
			navigationBar.navigateToMenuItem(menus.get(1));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			CustomisedAssert.assertFalse(foundation.isDisplayed(LocationSummary.AGE_VERIFICATION));

			// Navigate to Location to verify the age verification is enabled
			navigationBar.navigateToMenuItem(menus.get(0));
			foundation.scrollIntoViewElement(LocationSummary.AGE_VERIFICATION);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.AGE_VERIFICATION));
			if (checkBox.isChkEnabled(LocationSummary.CHK_AGE_VERIFICATION))
				checkBox.check(LocationSummary.CHK_AGE_VERIFICATION);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.threadWait(Constants.THREE_SECOND);
			CustomisedAssert.assertTrue(foundation.isDisplayed(OrgList.LBL_ORG_LIST));

			// Navigate to Location
			navigationBar.navigateToMenuItem(menus.get(1));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.AGE_VERIFICATION));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "168926-Verify no on age verification prompt"
			+ "168927-Verify yes, continue on age verification prompt")
	public void verifyNoOnAgeVerificationPrompt() {
		final String CASE_NUM = "168926";

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
			foundation.scrollIntoViewElement(LocationSummary.TXT_AGE_VERIFICATION);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_AGE_VERIFICATION));
			if (checkBox.isChkEnabled(LocationSummary.CHK_AGE_VERIFICATION))
				checkBox.check(LocationSummary.CHK_AGE_VERIFICATION);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);

			// Navigate to device to check on age verification
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			foundation.click(LocationSummary.DEVICE_BTN);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.AGE_VERIFICATION));
			checkBox.check(LocationSummary.AGE_VERIFICATION);
			foundation.click(DeviceSummary.BTN_SAVE);
			foundation.threadWait(Constants.SHORT_TIME);

			// Navigate to Location to verify the age verification prompt in NO
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.AGE_VERIFICATION));
			CustomisedAssert.assertTrue(foundation.isEnabled(LocationSummary.AGE_VERIFICATION));
			checkBox.unCheck(LocationSummary.AGE_VERIFICATION);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(LocationSummary.NO_BTN_PROMPT_AGEVERIFICATION);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.threadWait(Constants.THREE_SECOND);

			// Navigate to Location to verify the age verification prompt in YES
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.AGE_VERIFICATION));
			checkBox.unCheck(LocationSummary.AGE_VERIFICATION);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(LocationSummary.YES_BTN_PROMPT_AGEVERIFICATION);
			foundation.threadWait(Constants.TWO_SECOND);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.threadWait(Constants.THREE_SECOND);

			// Verify the AgeVerification in location summary and device to ensure it's
			// Disbaled
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.AGE_VERIFICATION));
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(LocationSummary.DEVICE_BTN);
			CustomisedAssert.assertFalse(foundation.isDisplayed(LocationSummary.AGE_VERIFICATION));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "168928-verify all the devices summary page on that location devices after disabling age verification on that location")
	public void verifyPromptDisableInDevice() {
		final String CASE_NUM = "168928";

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
			foundation.threadWait(Constants.THREE_SECOND);

			// Navigate to Device to enable 122 device
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			locationList.selectLocationName(location.get(0));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(LocationSummary.DEVICE_BTN);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.AGE_VERIFICATION));
			checkBox.check(LocationSummary.AGE_VERIFICATION);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.threadWait(Constants.SHORT_TIME);

			// Navigate to Device to enable 115 device
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			locationList.selectLocationName(location.get(0));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(LocationSummary.SECOND_DEVICE);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.AGE_VERIFICATION));
			checkBox.check(LocationSummary.AGE_VERIFICATION);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.threadWait(Constants.SHORT_TIME);

			// Navigate to Location to verify the age verification prompt in YES
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			locationList.selectLocationName(location.get(0));
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.AGE_VERIFICATION));
			checkBox.unCheck(LocationSummary.AGE_VERIFICATION);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(LocationSummary.YES_BTN_PROMPT_AGEVERIFICATION);
			foundation.threadWait(Constants.TWO_SECOND);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.threadWait(Constants.THREE_SECOND);

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

			// Remove Device from AutomationLocation1 Location and Uncheck the age
			// verification
			foundation.scrollIntoViewElement(LocationSummary.TAB_LOCATION);
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

	@Test(description = "168937 - verify the validation message for the email without syntax"
			+ "168938 - verify maximum field length" + "168939 - verify the special characters"
			+ "168940 - verify the numeric characters" + "168941 - verify the validation message for email address")
	public void verifyValidationMessage() {
		final String CASE_NUM = "168937";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
		rstAdminAgeVerificationData = dataBase.getAdminAgeVerificationData(Queries.ADMIN_AGE_VERIFICATION, CASE_NUM);

		List<String> menus = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> locationName = Arrays.asList(
				rstAdminAgeVerificationData.get(CNAdminAgeVerification.LOCATION_NAME).split(Constants.DELIMITER_TILD));
		List<String> requiredData = Arrays.asList(
				rstAdminAgeVerificationData.get(CNAdminAgeVerification.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		String currentDate = dateAndTime.getDateAndTime(Constants.REGEX_DD_MM_YYYY, Constants.TIME_ZONE_INDIA);
		List<String> status = Arrays
				.asList(rstAdminAgeVerificationData.get(CNAdminAgeVerification.STATUS).split(Constants.DELIMITER_TILD));
		try {
			// Select Menu and Location
			navigationBar.launchBrowserAsSuperAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menus.get(0));
			foundation.threadWait(Constants.THREE_SECOND);
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));

			// Choosing Age Verification checkbox under AutoLocation3
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			foundation.scrollIntoViewElement(LocationSummary.TXT_AGE_VERIFICATION);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_AGE_VERIFICATION));
			if (checkBox.isChkEnabled(LocationSummary.CHK_AGE_VERIFICATION))
				checkBox.check(LocationSummary.CHK_AGE_VERIFICATION);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			foundation.threadWait(Constants.THREE_SECOND);

			// Unchecking Age Verification checkbox under AutoLocation4
			locationList.selectLocationName(locationName.get(1));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			foundation.scrollIntoViewElement(LocationSummary.TXT_AGE_VERIFICATION);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_AGE_VERIFICATION));
			if (checkBox.isChkEnabled(LocationSummary.CHK_AGE_VERIFICATION))
				checkBox.unCheck(LocationSummary.CHK_AGE_VERIFICATION);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);

			// Navigate to Admin tab and verify Age Verification Sub Tab is present or not
			List<String> tabNames = navigationBar.getSubTabs(menus.get(1));
			CustomisedAssert.assertTrue(tabNames.contains(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION)));
			navigationBar.navigateToMenuItem(menus.get(1));
			CustomisedAssert.assertTrue(foundation.isDisplayed(AgeVerificationDetails.TXT_AGE_VERIFICATION));

			// Verify Age Verification Enabled Location visible under Location Dropdown
			List<String> location = dropDown.getAllItems(AgeVerificationDetails.DPD_LOCATION);
			CustomisedAssert.assertTrue(location.contains(rstLocationListData.get(CNLocationList.LOCATION_NAME)));
			CustomisedAssert.assertFalse(location.contains(locationName.get(1)));

			// Creating Age Verification PIN with wrong email syntax
			dropDown.selectItem(AgeVerificationDetails.DPD_LOCATION,
					rstLocationListData.get(CNLocationList.LOCATION_NAME), Constants.TEXT);
			textBox.enterText(AgeVerificationDetails.INPUT_MAIL, requiredData.get(3));
			textBox.enterText(AgeVerificationDetails.INPUT_FNAME, requiredData.get(4));
			textBox.enterText(AgeVerificationDetails.INPUT_LNAME, requiredData.get(5));
			dropDown.selectItem(AgeVerificationDetails.DPD_LANGUAGE, requiredData.get(6), Constants.TEXT);
			textBox.enterText(AgeVerificationDetails.CHECKOUT_DATE, currentDate);
			textBox.enterText(AgeVerificationDetails.INPUT_DAILY_USES, requiredData.get(7));
			foundation.objectClick(AgeVerificationDetails.BTN_CREATE_PIN);
			CustomisedAssert.assertTrue(foundation.isDisplayed(AgeVerificationDetails.TXT_EMAIL_ERROR));

			// Inserting Email field with maximum limit length and create Active PIN
			ageVerificationDetails.verifyEmailFieldinPinCreation(requiredData.get(8),
					rstLocationListData.get(CNLocationList.LOCATION_NAME), requiredData.get(0));

			// Inserting Email field with special characters along with maximum limit length
			String mail = requiredData.get(8).replace('k', '$');
			mail = mail.replace('r', '#');
			String mailSpecialChar = mail;
			ageVerificationDetails.createAgeVerificationPinWithoutEmail(
					rstLocationListData.get(CNLocationList.LOCATION_NAME), requiredData);
			ageVerificationDetails.verifyEmailFieldinPinCreation(mailSpecialChar,
					rstLocationListData.get(CNLocationList.LOCATION_NAME), requiredData.get(0));

			// Inserting Email field with numeric characters along with maximum limit length
			mail = mail.replace('$', '1');
			mail = mail.replace('#', '5');
			ageVerificationDetails.createAgeVerificationPinWithoutEmail(
					rstLocationListData.get(CNLocationList.LOCATION_NAME), requiredData);
			ageVerificationDetails.verifyEmailFieldinPinCreation(mail,
					rstLocationListData.get(CNLocationList.LOCATION_NAME), requiredData.get(0));

			// Verifying all Email's Created are stored properly in Table or not
			Map<Integer, Map<String, String>> uiTableData = ageVerificationDetails.getTblRecordsUI();
			Map<String, String> innerMap = new HashMap<>();
			List<String> mailId = new ArrayList<String>();
			String innerValue = "";
			for (int i = 0; i < uiTableData.size(); i++) {
				innerMap = uiTableData.get(i);
				innerValue = innerMap.get("Email Address");
				mailId.add(innerValue);
			}
			CustomisedAssert.assertEquals(mailId.get(0), mail);
			CustomisedAssert.assertEquals(mailId.get(1), mailSpecialChar);
			CustomisedAssert.assertEquals(mailId.get(2), requiredData.get(8));
			uiTableData.clear();

			// Verifying the validation message for email address
			ageVerificationDetails.createAgeVerificationPinWithoutEmail(
					rstLocationListData.get(CNLocationList.LOCATION_NAME), requiredData);
			foundation.objectClick(AgeVerificationDetails.BTN_CREATE_PIN);
			CustomisedAssert.assertTrue(foundation.isDisplayed(AgeVerificationDetails.TXT_EMAIL_ERROR));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// Deleting Active PIN record After validation
			dropDown.selectItem(AgeVerificationDetails.DPD_STATUS, status.get(1), Constants.TEXT);
			textBox.enterText(AgeVerificationDetails.INPUT_TEXT, rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.threadWait(Constants.THREE_SECOND);
			Map<Integer, Map<String, String>> uiTableData = ageVerificationDetails.getTblRecordsUI();
			for (int i = 0; i < uiTableData.size(); i++) {
				foundation.click(ageVerificationDetails.objExpirePinConfirmation(
						rstLocationListData.get(CNLocationList.LOCATION_NAME), requiredData.get(0)));
				foundation.click(AgeVerificationDetails.BTN_YES);
				foundation.refreshPage();
				foundation.waitforElementToDisappear(LocationList.TXT_SPINNER_MSG, Constants.TWO_SECOND);
			}

			// Resetting Age Verification Checkbox
			navigationBar.navigateToMenuItem(menus.get(0));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			foundation.scrollIntoViewElement(LocationSummary.TXT_AGE_VERIFICATION);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_AGE_VERIFICATION));
			if (checkBox.isChkEnabled(LocationSummary.CHK_AGE_VERIFICATION))
				checkBox.unCheck(LocationSummary.CHK_AGE_VERIFICATION);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			login.logout();
			browser.close();
		}
	}

	@Test(description = "168942 - verify the validation message for the email without syntax - Operator"
			+ "168943 - verify maximum field length - Operator" + "168944 - verify the special characters - Operator"
			+ "168945 - verify the numeric characters - Operator"
			+ "168946 - verify the validation message for email address - Operator")
	public void verifyValidationMessageInOperator() {
		final String CASE_NUM = "168942";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
		rstAdminAgeVerificationData = dataBase.getAdminAgeVerificationData(Queries.ADMIN_AGE_VERIFICATION, CASE_NUM);

		List<String> menus = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> locationName = Arrays.asList(
				rstAdminAgeVerificationData.get(CNAdminAgeVerification.LOCATION_NAME).split(Constants.DELIMITER_TILD));
		List<String> requiredData = Arrays.asList(
				rstAdminAgeVerificationData.get(CNAdminAgeVerification.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		String currentDate = dateAndTime.getDateAndTime(Constants.REGEX_DD_MM_YYYY, Constants.TIME_ZONE_INDIA);
		List<String> status = Arrays
				.asList(rstAdminAgeVerificationData.get(CNAdminAgeVerification.STATUS).split(Constants.DELIMITER_TILD));
		try {
			// Select Menu and Location
			navigationBar.launchBrowserAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menus.get(0));
			foundation.threadWait(Constants.THREE_SECOND);
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));

			// Choosing Age Verification checkbox under AutoLocation3
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			foundation.scrollIntoViewElement(LocationSummary.TXT_AGE_VERIFICATION);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_AGE_VERIFICATION));
			if (checkBox.isChkEnabled(LocationSummary.CHK_AGE_VERIFICATION))
				checkBox.check(LocationSummary.CHK_AGE_VERIFICATION);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			foundation.threadWait(Constants.THREE_SECOND);

			// Unchecking Age Verification checkbox under AutoLocation4
			locationList.selectLocationName(locationName.get(1));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			foundation.scrollIntoViewElement(LocationSummary.TXT_AGE_VERIFICATION);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_AGE_VERIFICATION));
			if (checkBox.isChkEnabled(LocationSummary.CHK_AGE_VERIFICATION))
				checkBox.unCheck(LocationSummary.CHK_AGE_VERIFICATION);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);

			// Navigate to Admin tab and verify Age Verification Sub Tab is present or not
			List<String> tabNames = navigationBar.getSubTabs(menus.get(1));
			CustomisedAssert.assertTrue(tabNames.contains(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION)));
			navigationBar.navigateToMenuItem(menus.get(1));
			CustomisedAssert.assertTrue(foundation.isDisplayed(AgeVerificationDetails.TXT_AGE_VERIFICATION));

			// Verify Age Verification Enabled Location visible under Location Dropdown
			List<String> location = dropDown.getAllItems(AgeVerificationDetails.DPD_LOCATION);
			CustomisedAssert.assertTrue(location.contains(rstLocationListData.get(CNLocationList.LOCATION_NAME)));
			CustomisedAssert.assertFalse(location.contains(locationName.get(1)));

			// Creating Age Verification PIN with wrong email syntax
			dropDown.selectItem(AgeVerificationDetails.DPD_LOCATION,
					rstLocationListData.get(CNLocationList.LOCATION_NAME), Constants.TEXT);
			textBox.enterText(AgeVerificationDetails.INPUT_MAIL, requiredData.get(3));
			textBox.enterText(AgeVerificationDetails.INPUT_FNAME, requiredData.get(4));
			textBox.enterText(AgeVerificationDetails.INPUT_LNAME, requiredData.get(5));
			dropDown.selectItem(AgeVerificationDetails.DPD_LANGUAGE, requiredData.get(6), Constants.TEXT);
			textBox.enterText(AgeVerificationDetails.CHECKOUT_DATE, currentDate);
			textBox.enterText(AgeVerificationDetails.INPUT_DAILY_USES, requiredData.get(7));
			foundation.objectClick(AgeVerificationDetails.BTN_CREATE_PIN);
			CustomisedAssert.assertTrue(foundation.isDisplayed(AgeVerificationDetails.TXT_EMAIL_ERROR));

			// Inserting Email field with maximum limit length and create Active PIN
			ageVerificationDetails.verifyEmailFieldinPinCreation(requiredData.get(8),
					rstLocationListData.get(CNLocationList.LOCATION_NAME), requiredData.get(0));

			// Inserting Email field with special characters along with maximum limit length
			String mail = requiredData.get(8).replace('k', '$');
			mail = mail.replace('r', '#');
			String mailSpecialChar = mail;
			ageVerificationDetails.createAgeVerificationPinWithoutEmail(
					rstLocationListData.get(CNLocationList.LOCATION_NAME), requiredData);
			ageVerificationDetails.verifyEmailFieldinPinCreation(mailSpecialChar,
					rstLocationListData.get(CNLocationList.LOCATION_NAME), requiredData.get(0));

			// Inserting Email field with numeric characters along with maximum limit length
			mail = mail.replace('$', '1');
			mail = mail.replace('#', '5');
			ageVerificationDetails.createAgeVerificationPinWithoutEmail(
					rstLocationListData.get(CNLocationList.LOCATION_NAME), requiredData);
			ageVerificationDetails.verifyEmailFieldinPinCreation(mail,
					rstLocationListData.get(CNLocationList.LOCATION_NAME), requiredData.get(0));

			// Verifying all Email's Created are stored properly in Table or not
			Map<Integer, Map<String, String>> uiTableData = ageVerificationDetails.getTblRecordsUI();
			Map<String, String> innerMap = new HashMap<>();
			List<String> mailId = new ArrayList<String>();
			String innerValue = "";
			for (int i = 0; i < uiTableData.size(); i++) {
				innerMap = uiTableData.get(i);
				innerValue = innerMap.get("Email Address");
				mailId.add(innerValue);
			}
			CustomisedAssert.assertEquals(mailId.get(0), mail);
			CustomisedAssert.assertEquals(mailId.get(1), mailSpecialChar);
			CustomisedAssert.assertEquals(mailId.get(2), requiredData.get(8));
			uiTableData.clear();

			// Verifying the validation message for email address
			ageVerificationDetails.createAgeVerificationPinWithoutEmail(
					rstLocationListData.get(CNLocationList.LOCATION_NAME), requiredData);
			foundation.objectClick(AgeVerificationDetails.BTN_CREATE_PIN);
			CustomisedAssert.assertTrue(foundation.isDisplayed(AgeVerificationDetails.TXT_EMAIL_ERROR));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// Deleting Active PIN record After validation
			dropDown.selectItem(AgeVerificationDetails.DPD_STATUS, status.get(1), Constants.TEXT);
			textBox.enterText(AgeVerificationDetails.INPUT_TEXT, rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.threadWait(Constants.THREE_SECOND);
			Map<Integer, Map<String, String>> uiTableData = ageVerificationDetails.getTblRecordsUI();
			for (int i = 0; i < uiTableData.size(); i++) {
				foundation.click(ageVerificationDetails.objExpirePinConfirmation(
						rstLocationListData.get(CNLocationList.LOCATION_NAME), requiredData.get(0)));
				foundation.click(AgeVerificationDetails.BTN_YES);
				foundation.refreshPage();
				foundation.waitforElementToDisappear(LocationList.TXT_SPINNER_MSG, Constants.TWO_SECOND);
			}

			// Resetting Age Verification Checkbox
			navigationBar.navigateToMenuItem(menus.get(0));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			foundation.scrollIntoViewElement(LocationSummary.TXT_AGE_VERIFICATION);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_AGE_VERIFICATION));
			if (checkBox.isChkEnabled(LocationSummary.CHK_AGE_VERIFICATION))
				checkBox.unCheck(LocationSummary.CHK_AGE_VERIFICATION);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			login.logout();
			browser.close();
		}
	}

	@Test(description = "168952 - verify first name field" + "168953 - verify last name field")
	public void verifyNameFields() {
		final String CASE_NUM = "168952";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
		rstAdminAgeVerificationData = dataBase.getAdminAgeVerificationData(Queries.ADMIN_AGE_VERIFICATION, CASE_NUM);

		List<String> menus = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> requiredData = Arrays.asList(
				rstAdminAgeVerificationData.get(CNAdminAgeVerification.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
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
			CustomisedAssert.assertTrue(tabNames.contains(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION)));
			navigationBar.navigateToMenuItem(menus.get(1));
			CustomisedAssert.assertTrue(foundation.isDisplayed(AgeVerificationDetails.TXT_AGE_VERIFICATION));

			// Creating Age Verification PIN
			ageVerificationDetails.createAgeVerificationPin(rstLocationListData.get(CNLocationList.LOCATION_NAME),
					requiredData);

			// Verify First Name & Last Name
			textBox.enterText(AgeVerificationDetails.INPUT_TEXT, rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.threadWait(Constants.THREE_SECOND);
			Map<Integer, Map<String, String>> uiTableData = ageVerificationDetails.getTblRecordsUI();
			Map<String, String> innerMap = new HashMap<>();
			String firstName = "";
			String lastName = "";
			for (int i = 0; i < uiTableData.size(); i++) {
				innerMap = uiTableData.get(i);
				firstName = innerMap.get("First Name");
				lastName = innerMap.get("Last Name");
			}
			CustomisedAssert.assertEquals(firstName, requiredData.get(4));
			CustomisedAssert.assertEquals(lastName, requiredData.get(5));
			uiTableData.clear();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// Deleting Active PIN record After validation
			foundation.click(ageVerificationDetails.objExpirePinConfirmation(
					rstLocationListData.get(CNLocationList.LOCATION_NAME), requiredData.get(0)));
			foundation.click(AgeVerificationDetails.BTN_YES);
			foundation.refreshPage();
			foundation.waitforElementToDisappear(LocationList.TXT_SPINNER_MSG, Constants.TWO_SECOND);
			// Resetting Age Verification Checkbox
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

	@Test(description = "168954 - verify first name field" + "168955 - verify last name field")
	public void verifyNameFieldsWithOperator() {
		final String CASE_NUM = "168954";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
		rstAdminAgeVerificationData = dataBase.getAdminAgeVerificationData(Queries.ADMIN_AGE_VERIFICATION, CASE_NUM);

		List<String> menus = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> requiredData = Arrays.asList(
				rstAdminAgeVerificationData.get(CNAdminAgeVerification.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
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
			CustomisedAssert.assertTrue(tabNames.contains(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION)));
			navigationBar.navigateToMenuItem(menus.get(1));
			CustomisedAssert.assertTrue(foundation.isDisplayed(AgeVerificationDetails.TXT_AGE_VERIFICATION));

			// Creating Age Verification PIN
			ageVerificationDetails.createAgeVerificationPin(rstLocationListData.get(CNLocationList.LOCATION_NAME),
					requiredData);

			// Verify First Name & Last Name
			textBox.enterText(AgeVerificationDetails.INPUT_TEXT, rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.threadWait(Constants.THREE_SECOND);
			Map<Integer, Map<String, String>> uiTableData = ageVerificationDetails.getTblRecordsUI();
			Map<String, String> innerMap = new HashMap<>();
			String firstName = "";
			String lastName = "";
			for (int i = 0; i < uiTableData.size(); i++) {
				innerMap = uiTableData.get(i);
				firstName = innerMap.get("First Name");
				lastName = innerMap.get("Last Name");
			}
			CustomisedAssert.assertEquals(firstName, requiredData.get(4));
			CustomisedAssert.assertEquals(lastName, requiredData.get(5));
			uiTableData.clear();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// Deleting Active PIN record After validation
			foundation.click(ageVerificationDetails.objExpirePinConfirmation(
					rstLocationListData.get(CNLocationList.LOCATION_NAME), requiredData.get(0)));
			foundation.click(AgeVerificationDetails.BTN_YES);
			foundation.refreshPage();
			foundation.waitforElementToDisappear(LocationList.TXT_SPINNER_MSG, Constants.TWO_SECOND);
			// Resetting Age Verification Checkbox
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

	@Test(description = "168961 - selection of date in picker calendar" + "168962 - verify the manual entry of date"
			+ "168963 - verify the manual entry of past date"
			+ "168964 - Age Verification Screen > Age Verification Setup Section > Location Dropdown")
	public void verifyDatePickerCalendar() {
		final String CASE_NUM = "168961";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
		rstAdminAgeVerificationData = dataBase.getAdminAgeVerificationData(Queries.ADMIN_AGE_VERIFICATION, CASE_NUM);

		List<String> menus = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> requiredData = Arrays.asList(
				rstAdminAgeVerificationData.get(CNAdminAgeVerification.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> status = Arrays
				.asList(rstAdminAgeVerificationData.get(CNAdminAgeVerification.STATUS).split(Constants.DELIMITER_TILD));
		String checkOutCurrentDate = dateAndTime.getDateAndTime(Constants.REGEX_MM_DD_YYYY, Constants.TIME_ZONE_INDIA);
		String checkOutFutureDate = dateAndTime.getFutureDate(Constants.REGEX_MM_DD_YYYY, requiredData.get(8));
		String futureDate = dateAndTime.getFutureDate(Constants.REGEX_MM_DD_YYYY, requiredData.get(8));
		String pastDate = dateAndTime.getPastDate(Constants.REGEX_MM_DD_YYYY, requiredData.get(8));
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
			CustomisedAssert.assertTrue(tabNames.contains(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION)));
			navigationBar.navigateToMenuItem(menus.get(1));
			CustomisedAssert.assertTrue(foundation.isDisplayed(AgeVerificationDetails.TXT_AGE_VERIFICATION));

			// Creating Age Verification PIN
			ageVerificationDetails.createAgeVerificationPin(rstLocationListData.get(CNLocationList.LOCATION_NAME),
					requiredData);

			// Creating Age Verification PIN with Future CheckOut Date
			dropDown.selectItem(AgeVerificationDetails.DPD_LOCATION,
					rstLocationListData.get(CNLocationList.LOCATION_NAME), Constants.TEXT);
			textBox.enterText(AgeVerificationDetails.INPUT_MAIL, requiredData.get(3));
			textBox.enterText(AgeVerificationDetails.INPUT_FNAME, requiredData.get(4));
			textBox.enterText(AgeVerificationDetails.INPUT_LNAME, requiredData.get(5));
			dropDown.selectItem(AgeVerificationDetails.DPD_LANGUAGE, requiredData.get(6), Constants.TEXT);
			textBox.enterText(AgeVerificationDetails.CHECKOUT_DATE, futureDate);
			textBox.enterText(AgeVerificationDetails.INPUT_DAILY_USES, requiredData.get(7));
			foundation.objectClick(AgeVerificationDetails.BTN_CREATE_PIN);
			foundation.waitforElementToDisappear(LocationList.TXT_SPINNER_MSG, Constants.ONE_SECOND);

			// Creating Age Verification PIN with Past CheckOut Date
			dropDown.selectItem(AgeVerificationDetails.DPD_LOCATION,
					rstLocationListData.get(CNLocationList.LOCATION_NAME), Constants.TEXT);
			textBox.enterText(AgeVerificationDetails.INPUT_MAIL, requiredData.get(3));
			textBox.enterText(AgeVerificationDetails.INPUT_FNAME, requiredData.get(4));
			textBox.enterText(AgeVerificationDetails.INPUT_LNAME, requiredData.get(5));
			dropDown.selectItem(AgeVerificationDetails.DPD_LANGUAGE, requiredData.get(6), Constants.TEXT);
			textBox.enterText(AgeVerificationDetails.CHECKOUT_DATE, pastDate);
			textBox.enterText(AgeVerificationDetails.INPUT_DAILY_USES, requiredData.get(7));
			foundation.objectClick(AgeVerificationDetails.BTN_CREATE_PIN);
			CustomisedAssert.assertTrue(foundation.isDisplayed(AgeVerificationDetails.TXT_CHECKOUT_ERROR));

			// Verify the fields of CheckOut
			foundation.scrollIntoViewElement(AgeVerificationDetails.TXT_STATUS);
			foundation.threadWait(Constants.TWO_SECOND);
			Map<Integer, Map<String, String>> uiTableData = ageVerificationDetails.getTblRecordsUI();
			Map<String, String> innerMap = new HashMap<>();
			List<String> checkoutDate = new ArrayList<String>();
			String innerValue = "";
			for (int i = 0; i < uiTableData.size(); i++) {
				innerMap = uiTableData.get(i);
				innerValue = innerMap.get("Checkout");
				checkoutDate.add(innerValue);
			}
			CustomisedAssert.assertEquals(checkoutDate.get(0), checkOutFutureDate);
			CustomisedAssert.assertEquals(checkoutDate.get(1), checkOutCurrentDate);
			uiTableData.clear();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// Deleting Active PIN record After validation
			foundation.threadWait(Constants.SHORT_TIME);
			dropDown.selectItem(AgeVerificationDetails.DPD_STATUS, status.get(1), Constants.TEXT);
			foundation.click(ageVerificationDetails.objExpirePinConfirmation(
					rstLocationListData.get(CNLocationList.LOCATION_NAME), requiredData.get(0)));
			foundation.click(AgeVerificationDetails.BTN_YES);
			foundation.refreshPage();
			foundation.waitforElementToDisappear(LocationList.TXT_SPINNER_MSG, Constants.TWO_SECOND);
			// Resetting Age Verification Checkbox
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

	@Test(description = "168956-Verify language dropdown for standard location")
	public void verifyLanguageDropdownBySuper() {
		final String CASE_NUM = "168956";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
		rstAdminAgeVerificationData = dataBase.getAdminAgeVerificationData(Queries.ADMIN_AGE_VERIFICATION, CASE_NUM);

		List<String> menus = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> language = Arrays
				.asList(rstLocationListData.get(CNLocationList.COLUMN_NAME).split(Constants.DELIMITER_TILD));
		List<String> datas = Arrays.asList(
				rstAdminAgeVerificationData.get(CNAdminAgeVerification.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		String currentDate = dateAndTime.getDateAndTime(Constants.REGEX_DD_MM_YYYY, Constants.TIME_ZONE_INDIA);

		try {
			// Select Menu and Location
			navigationBar.launchBrowserAsSuperAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menus.get(0));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));

			// Verifying the selection of defaults for Age Verification
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			foundation.scrollIntoViewElement(LocationSummary.TXT_AGE_VERIFICATION);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_AGE_VERIFICATION));
			if (checkBox.isChkEnabled(LocationSummary.CHK_AGE_VERIFICATION))
				checkBox.check(LocationSummary.CHK_AGE_VERIFICATION);
			foundation.threadWait(Constants.THREE_SECOND);
			dropDown.selectItem(LocationSummary.DPD_ALTERNATE_LANGUAGE, language.get(0), Constants.TEXT);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);

			// Navigate to Admin tab and verify the other language
			List<String> tabNames = navigationBar.getSubTabs(menus.get(1));
			CustomisedAssert.assertTrue(tabNames.contains(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION)));
			navigationBar.navigateToMenuItem(menus.get(1));
			CustomisedAssert.assertTrue(foundation.isDisplayed(AgeVerificationDetails.TXT_AGE_VERIFICATION));
			dropDown.selectItem(AgeVerificationDetails.DPD_LOCATION,
					rstAdminAgeVerificationData.get(CNAdminAgeVerification.LOCATION_NAME), Constants.TEXT);
			foundation.click(AgeVerificationDetails.DPD_LANGUAGE);
			foundation.threadWait(Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(ageVerificationDetails.language(language.get(0))));

			// Creating Age Verification PIN
			foundation.threadWait(Constants.THREE_SECOND);
			textBox.enterText(AgeVerificationDetails.INPUT_MAIL, datas.get(0));
			textBox.enterText(AgeVerificationDetails.INPUT_FNAME, datas.get(1));
			textBox.enterText(AgeVerificationDetails.INPUT_LNAME, datas.get(2));
			dropDown.selectItem(AgeVerificationDetails.DPD_LANGUAGE, datas.get(3), Constants.TEXT);
			textBox.enterText(AgeVerificationDetails.CHECKOUT_DATE, currentDate);
			textBox.enterText(AgeVerificationDetails.INPUT_DAILY_USES, datas.get(4));
			foundation.click(AgeVerificationDetails.BTN_CREATE_PIN);
			foundation.objectClick(AgeVerificationDetails.BTN_CREATE_PIN);
			foundation.waitforElementToDisappear(LocationList.TXT_SPINNER_MSG, Constants.ONE_SECOND);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// Resetting Age Verification Checkbox
			foundation.scrollIntoViewElement(LocationSummary.TAB_LOCATION);
			navigationBar.navigateToMenuItem(menus.get(0));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			foundation.scrollIntoViewElement(LocationSummary.TXT_AGE_VERIFICATION);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_AGE_VERIFICATION));
			if (checkBox.isChkEnabled(LocationSummary.CHK_AGE_VERIFICATION))
				checkBox.unCheck(LocationSummary.CHK_AGE_VERIFICATION);
			foundation.threadWait(Constants.THREE_SECOND);
			dropDown.selectItem(LocationSummary.DPD_ALTERNATE_LANGUAGE, language.get(1), Constants.TEXT);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			foundation.threadWait(Constants.THREE_SECOND);

			navigationBar.navigateToMenuItem(menus.get(1));
			foundation.click(AgeVerificationDetails.BTN_EXPIRE);
			foundation.threadWait(Constants.THREE_SECOND);
			CustomisedAssert.assertTrue(foundation.isDisplayed(AgeVerificationDetails.TXT_PROMPT_MSG));
			foundation.threadWait(Constants.THREE_SECOND);
			foundation.click(AgeVerificationDetails.BTN_YES);
			foundation.threadWait(Constants.SHORT_TIME);
			browser.close();
		}
	}

	@Test(description = "168957-verify the cap for unset" + "168958-verify the pins section"
			+ "168959-verify special characters" + "168960-verify the alphabetical characters")
	public void verifyDailyUsesInAgeVerification() {
		final String CASE_NUM = "168957";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
		rstAdminAgeVerificationData = dataBase.getAdminAgeVerificationData(Queries.ADMIN_AGE_VERIFICATION, CASE_NUM);

		List<String> menus = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> datas = Arrays.asList(
				rstAdminAgeVerificationData.get(CNAdminAgeVerification.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> dailyuses = Arrays
				.asList(rstAdminAgeVerificationData.get(CNAdminAgeVerification.STATUS).split(Constants.DELIMITER_TILD));
		String currentDate = dateAndTime.getDateAndTime(Constants.REGEX_DD_MM_YYYY, Constants.TIME_ZONE_INDIA);

		try {
			// Select Menu and Location
			navigationBar.launchBrowserAsSuperAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menus.get(0));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));

			// Verifying the selection of defaults for Age Verification
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			foundation.scrollIntoViewElement(LocationSummary.TXT_AGE_VERIFICATION);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_AGE_VERIFICATION));
			if (checkBox.isChkEnabled(LocationSummary.CHK_AGE_VERIFICATION))
				checkBox.check(LocationSummary.CHK_AGE_VERIFICATION);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);

			// Navigate to Admin tab and verify Age Verification Sub Tab is present or not
			List<String> tabNames = navigationBar.getSubTabs(menus.get(1));
			CustomisedAssert.assertTrue(tabNames.contains(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION)));
			navigationBar.navigateToMenuItem(menus.get(1));
			CustomisedAssert.assertTrue(foundation.isDisplayed(AgeVerificationDetails.TXT_AGE_VERIFICATION));

			// Creating Age Verification PIN without daily uses and verify the field
			dropDown.selectItem(AgeVerificationDetails.DPD_LOCATION,
					rstAdminAgeVerificationData.get(CNAdminAgeVerification.LOCATION_NAME), Constants.TEXT);
			textBox.enterText(AgeVerificationDetails.INPUT_MAIL, datas.get(3));
			textBox.enterText(AgeVerificationDetails.INPUT_FNAME, datas.get(4));
			textBox.enterText(AgeVerificationDetails.INPUT_LNAME, datas.get(5));
			dropDown.selectItem(AgeVerificationDetails.DPD_LANGUAGE, datas.get(6), Constants.TEXT);
			textBox.enterText(AgeVerificationDetails.CHECKOUT_DATE, currentDate);
			foundation.click(AgeVerificationDetails.BTN_CREATE_PIN);
			foundation.objectClick(AgeVerificationDetails.BTN_CREATE_PIN);
			foundation.waitforElementToDisappear(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			textBox.enterText(AgeVerificationDetails.INPUT_TEXT,
					rstAdminAgeVerificationData.get(CNAdminAgeVerification.LOCATION_NAME));
			foundation.threadWait(Constants.THREE_SECOND);
			Map<Integer, Map<String, String>> uiTableData = ageVerificationDetails.getTblRecordsUI();
			Map<String, String> innerMap = new HashMap<>();
			String innerValue = "";
			for (int i = 0; i <= 0; i++) {
				innerMap = uiTableData.get(i);
				innerValue = innerMap.get("Daily Uses");
				CustomisedAssert.assertEquals(innerValue, dailyuses.get(0));
			}
			uiTableData.clear();
			foundation.click(AgeVerificationDetails.BTN_EXPIRE);
			foundation.threadWait(Constants.THREE_SECOND);
			CustomisedAssert.assertTrue(foundation.isDisplayed(AgeVerificationDetails.TXT_PROMPT_MSG));
			foundation.threadWait(Constants.THREE_SECOND);
			foundation.click(AgeVerificationDetails.BTN_YES);
			foundation.threadWait(Constants.SHORT_TIME);

			// Creating Age Verification PIN with 100 and verify the field
			ageVerificationDetails.createAgeVerificationPin(rstLocationListData.get(CNLocationList.LOCATION_NAME),
					datas);
			textBox.enterText(AgeVerificationDetails.INPUT_TEXT,
					rstAdminAgeVerificationData.get(CNAdminAgeVerification.LOCATION_NAME));
			foundation.threadWait(Constants.THREE_SECOND);
			uiTableData = ageVerificationDetails.getTblRecordsUI();
			for (int i = 0; i <= 0; i++) {
				innerMap = uiTableData.get(i);
				innerValue = innerMap.get("Daily Uses");
				CustomisedAssert.assertEquals(innerValue, dailyuses.get(1));
			}
			uiTableData.clear();
			CustomisedAssert.assertTrue(foundation.isDisplayed(AgeVerificationDetails.DAILY_USES));
			foundation.click(AgeVerificationDetails.BTN_EXPIRE);
			foundation.threadWait(Constants.THREE_SECOND);
			CustomisedAssert.assertTrue(foundation.isDisplayed(AgeVerificationDetails.TXT_PROMPT_MSG));
			foundation.threadWait(Constants.THREE_SECOND);
			foundation.click(AgeVerificationDetails.BTN_YES);
			foundation.threadWait(Constants.SHORT_TIME);

			// Creating Age Verification PIN with "e" and verify the error message
			dropDown.selectItem(AgeVerificationDetails.DPD_LOCATION,
					rstAdminAgeVerificationData.get(CNAdminAgeVerification.LOCATION_NAME), Constants.TEXT);
			textBox.enterText(AgeVerificationDetails.INPUT_MAIL, datas.get(3));
			textBox.enterText(AgeVerificationDetails.INPUT_FNAME, datas.get(4));
			textBox.enterText(AgeVerificationDetails.INPUT_LNAME, datas.get(5));
			dropDown.selectItem(AgeVerificationDetails.DPD_LANGUAGE, datas.get(6), Constants.TEXT);
			textBox.enterText(AgeVerificationDetails.CHECKOUT_DATE, currentDate);
			textBox.enterText(AgeVerificationDetails.INPUT_DAILY_USES, datas.get(8));
			foundation.click(AgeVerificationDetails.BTN_CREATE_PIN);
			foundation.threadWait(Constants.THREE_SECOND);
			CustomisedAssert.assertTrue(foundation.isDisplayed(AgeVerificationDetails.ERROR_MSG_DAILY_USES));

			// Creating Age Verification PIN with "." and verify the error message
			dropDown.selectItem(AgeVerificationDetails.DPD_LOCATION,
					rstAdminAgeVerificationData.get(CNAdminAgeVerification.LOCATION_NAME), Constants.TEXT);
			textBox.enterText(AgeVerificationDetails.INPUT_MAIL, datas.get(3));
			textBox.enterText(AgeVerificationDetails.INPUT_FNAME, datas.get(4));
			textBox.enterText(AgeVerificationDetails.INPUT_LNAME, datas.get(5));
			dropDown.selectItem(AgeVerificationDetails.DPD_LANGUAGE, datas.get(6), Constants.TEXT);
			textBox.enterText(AgeVerificationDetails.CHECKOUT_DATE, currentDate);
			textBox.enterText(AgeVerificationDetails.INPUT_DAILY_USES, datas.get(9));
			foundation.click(AgeVerificationDetails.BTN_CREATE_PIN);
			foundation.threadWait(Constants.THREE_SECOND);
			CustomisedAssert.assertTrue(foundation.isDisplayed(AgeVerificationDetails.ERROR_MSG_DAILY_USES));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// Resetting Age Verification Checkbox
			foundation.scrollIntoViewElement(LocationSummary.TAB_LOCATION);
			navigationBar.navigateToMenuItem(menus.get(0));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			foundation.scrollIntoViewElement(LocationSummary.TXT_AGE_VERIFICATION);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_AGE_VERIFICATION));
			if (checkBox.isChkEnabled(LocationSummary.CHK_AGE_VERIFICATION))
				checkBox.unCheck(LocationSummary.CHK_AGE_VERIFICATION);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
		}
	}

	@Test(description = "169202-verify the selection of location by super"
			+ "169205-verify disabled location are not showing in location dropdown")
	public void verifySelectLocationBySuper() {
		final String CASE_NUM = "169202";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
		rstAdminAgeVerificationData = dataBase.getAdminAgeVerificationData(Queries.ADMIN_AGE_VERIFICATION, CASE_NUM);

		List<String> menus = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> requiredData = Arrays.asList(
				rstAdminAgeVerificationData.get(CNAdminAgeVerification.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		try {
			// Select Menu and Location
			navigationBar.launchBrowserAsSuperAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menus.get(0));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));

			// Verifying the selection of defaults for Age Verification
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			foundation.scrollIntoViewElement(LocationSummary.TXT_AGE_VERIFICATION);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_AGE_VERIFICATION));
			if (checkBox.isChkEnabled(LocationSummary.CHK_AGE_VERIFICATION))
				checkBox.check(LocationSummary.CHK_AGE_VERIFICATION);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);

			// Navigate to Admin tab and verify Age Verification Sub Tab is present or not
			List<String> tabNames = navigationBar.getSubTabs(menus.get(1));
			CustomisedAssert.assertTrue(tabNames.contains(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION)));
			navigationBar.navigateToMenuItem(menus.get(1));
			CustomisedAssert.assertTrue(foundation.isDisplayed(AgeVerificationDetails.TXT_AGE_VERIFICATION));

			// Creating Age Verification PIN
			ageVerificationDetails.createAgeVerificationPin(rstLocationListData.get(CNLocationList.LOCATION_NAME),
					requiredData);

			// Uncheck the Age Verification in location level
			navigationBar.navigateToMenuItem(menus.get(0));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			foundation.scrollIntoViewElement(LocationSummary.TXT_AGE_VERIFICATION);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_AGE_VERIFICATION));
			if (checkBox.isChkEnabled(LocationSummary.CHK_AGE_VERIFICATION))
				checkBox.unCheck(LocationSummary.CHK_AGE_VERIFICATION);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);

			// Navigate to Admin tab and verify Age Verification Sub Tab is present or not
			List<String> tabName = navigationBar.getSubTabs(menus.get(1));
			CustomisedAssert.assertTrue(tabName.contains(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION)));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			foundation.scrollIntoViewElement(LocationSummary.TAB_LOCATION);
			navigationBar.navigateToMenuItem(menus.get(1));
			foundation.click(AgeVerificationDetails.EXPIRE_BTN);
			foundation.threadWait(Constants.THREE_SECOND);
			CustomisedAssert.assertTrue(foundation.isDisplayed(AgeVerificationDetails.TXT_PROMPT_MSG));
			foundation.threadWait(Constants.THREE_SECOND);
			foundation.click(AgeVerificationDetails.BTN_YES);
			foundation.threadWait(Constants.SHORT_TIME);
			browser.close();
		}
	}

	@Test(description = "169203-verify the selection of location by operator")
	public void verifySelectLocationByOperator() {
		final String CASE_NUM = "169203";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
		rstAdminAgeVerificationData = dataBase.getAdminAgeVerificationData(Queries.ADMIN_AGE_VERIFICATION, CASE_NUM);

		List<String> menus = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> requiredData = Arrays.asList(
				rstAdminAgeVerificationData.get(CNAdminAgeVerification.REQUIRED_DATA).split(Constants.DELIMITER_TILD));

		try {
			// Select Menu and Location
			navigationBar.launchBrowserAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menus.get(0));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));

			// Verifying the selection of defaults for Age Verification
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			foundation.scrollIntoViewElement(LocationSummary.TXT_AGE_VERIFICATION);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_AGE_VERIFICATION));
			if (checkBox.isChkEnabled(LocationSummary.CHK_AGE_VERIFICATION))
				checkBox.check(LocationSummary.CHK_AGE_VERIFICATION);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);

			// Navigate to Admin tab and verify Age Verification Sub Tab is present or not
			List<String> tabNames = navigationBar.getSubTabs(menus.get(1));
			CustomisedAssert.assertTrue(tabNames.contains(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION)));
			navigationBar.navigateToMenuItem(menus.get(1));
			CustomisedAssert.assertTrue(foundation.isDisplayed(AgeVerificationDetails.TXT_AGE_VERIFICATION));

			// Creating Age Verification PIN
			ageVerificationDetails.createAgeVerificationPin(rstLocationListData.get(CNLocationList.LOCATION_NAME),
					requiredData);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// Resetting Age Verification Checkbox
			foundation.scrollIntoViewElement(LocationSummary.TAB_LOCATION);
			navigationBar.navigateToMenuItem(menus.get(0));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			foundation.scrollIntoViewElement(LocationSummary.TXT_AGE_VERIFICATION);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_AGE_VERIFICATION));
			if (checkBox.isChkEnabled(LocationSummary.CHK_AGE_VERIFICATION))
				checkBox.unCheck(LocationSummary.CHK_AGE_VERIFICATION);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);

			navigationBar.navigateToMenuItem(menus.get(1));
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(AgeVerificationDetails.EXPIRE_BTN);
			foundation.threadWait(Constants.THREE_SECOND);
			CustomisedAssert.assertTrue(foundation.isDisplayed(AgeVerificationDetails.TXT_PROMPT_MSG));
			foundation.threadWait(Constants.THREE_SECOND);
			foundation.click(AgeVerificationDetails.BTN_YES);
			foundation.threadWait(Constants.SHORT_TIME);
			browser.close();
		}
	}

	@Test(description = "169204-verify the maximum field length")
	public void verifyMaximumLengthLocationBySuper() {
		final String CASE_NUM = "169204";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
		rstAdminAgeVerificationData = dataBase.getAdminAgeVerificationData(Queries.ADMIN_AGE_VERIFICATION, CASE_NUM);

		List<String> menus = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> requiredData = Arrays.asList(
				rstAdminAgeVerificationData.get(CNAdminAgeVerification.REQUIRED_DATA).split(Constants.DELIMITER_TILD));

		try {
			// Select Menu and Location
			navigationBar.launchBrowserAsSuperAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menus.get(0));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));

			// Verifying the selection of defaults for Age Verification
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			foundation.scrollIntoViewElement(LocationSummary.TXT_AGE_VERIFICATION);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_AGE_VERIFICATION));
			if (checkBox.isChkEnabled(LocationSummary.CHK_AGE_VERIFICATION))
				checkBox.check(LocationSummary.CHK_AGE_VERIFICATION);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);

			// Navigate to Admin tab and verify Age Verification Sub Tab is present or not
			List<String> tabNames = navigationBar.getSubTabs(menus.get(1));
			CustomisedAssert.assertTrue(tabNames.contains(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION)));
			navigationBar.navigateToMenuItem(menus.get(1));
			CustomisedAssert.assertTrue(foundation.isDisplayed(AgeVerificationDetails.TXT_AGE_VERIFICATION));

			// Creating Age Verification PIN
			ageVerificationDetails.createAgeVerificationPin(rstLocationListData.get(CNLocationList.LOCATION_NAME),
					requiredData);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// Delete the datas
			foundation.click(AgeVerificationDetails.EXPIRE_BTN);
			foundation.threadWait(Constants.THREE_SECOND);
			CustomisedAssert.assertTrue(foundation.isDisplayed(AgeVerificationDetails.TXT_PROMPT_MSG));
			foundation.threadWait(Constants.THREE_SECOND);
			foundation.click(AgeVerificationDetails.BTN_YES);
			foundation.threadWait(Constants.SHORT_TIME);

			// Resetting Age Verification Checkbox
			foundation.scrollIntoViewElement(LocationSummary.TAB_LOCATION);
			navigationBar.navigateToMenuItem(menus.get(0));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			foundation.scrollIntoViewElement(LocationSummary.TXT_AGE_VERIFICATION);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_AGE_VERIFICATION));
			if (checkBox.isChkEnabled(LocationSummary.CHK_AGE_VERIFICATION))
				checkBox.unCheck(LocationSummary.CHK_AGE_VERIFICATION);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			login.logout();
			browser.close();
		}
	}

	@Test(description = "169025 - ADM > Users and Roles > Permission Matrix > Super and Operator > Admin Tab > Age Verification NEW")
	public void verifyUserRolesPermission() {
		final String CASE_NUM = "169025";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
		rstAdminAgeVerificationData = dataBase.getAdminAgeVerificationData(Queries.ADMIN_AGE_VERIFICATION, CASE_NUM);

		List<String> menus = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> requiredData = Arrays.asList(
				rstAdminAgeVerificationData.get(CNAdminAgeVerification.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		try {
			// Select Menu and Location
			navigationBar.launchBrowserAsSuperAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menus.get(0));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));

			// Verifying the selection of defaults for Age Verification
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			foundation.scrollIntoViewElement(LocationSummary.TXT_AGE_VERIFICATION);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_AGE_VERIFICATION));
			if (checkBox.isChkEnabled(LocationSummary.CHK_AGE_VERIFICATION))
				checkBox.check(LocationSummary.CHK_AGE_VERIFICATION);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);

			// Navigate to Admin > User roles and click Manage Roles to check Super Role
			navigationBar.navigateToMenuItem(menus.get(1));
			CustomisedAssert.assertTrue(foundation.isDisplayed(UserRoles.LBL_USER_LIST));
			foundation.click(UserRoles.BTN_MANAGE_ROLE);
			CustomisedAssert.assertTrue(foundation.isDisplayed(UserRoles.LBL_USER_ROLES));

			// Verify Super Role has Age Verification check box checked
			textBox.enterText(UserRoles.TXT_SEARCH_FILTER, requiredData.get(0));
			foundation.click(userRoles.getRowByText(requiredData.get(0)));
			CustomisedAssert.assertTrue(foundation.isDisplayed(UserRoles.LBL_VIEW_ROLE));
			foundation.click(UserRoles.TAB_ADMIN);
			CustomisedAssert.assertTrue(foundation.isDisplayed(userRoles.getRowByText(requiredData.get(3))));
			String value = foundation.getAttribute(userRoles.getAgeVerificationFeature(requiredData.get(6)),
					requiredData.get(4));
			CustomisedAssert.assertEquals(value, requiredData.get(5));
			value = foundation.getAttribute(userRoles.getAgeVerificationFeature(requiredData.get(7)),
					requiredData.get(4));
			CustomisedAssert.assertEquals(value, requiredData.get(5));
			value = foundation.getAttribute(userRoles.getAgeVerificationFeature(requiredData.get(8)),
					requiredData.get(4));
			CustomisedAssert.assertEquals(value, requiredData.get(5));
			value = foundation.getAttribute(userRoles.getAgeVerificationFeature(requiredData.get(9)),
					requiredData.get(4));
			CustomisedAssert.assertEquals(value, requiredData.get(5));

			// Navigate to Admin > User roles and click Manage Roles to check Operator Role
			navigationBar.navigateToMenuItem(menus.get(1));
			CustomisedAssert.assertTrue(foundation.isDisplayed(UserRoles.LBL_USER_LIST));
			foundation.click(UserRoles.BTN_MANAGE_ROLE);
			CustomisedAssert.assertTrue(foundation.isDisplayed(UserRoles.LBL_USER_ROLES));

			// Verify Operator Role has Age Verification check box checked
			textBox.enterText(UserRoles.TXT_SEARCH_FILTER, requiredData.get(1));
			foundation.click(userRoles.getRowByText(requiredData.get(1)));
			CustomisedAssert.assertTrue(foundation.isDisplayed(UserRoles.LBL_VIEW_ROLE));
			foundation.click(UserRoles.TAB_ADMIN);
			CustomisedAssert.assertTrue(foundation.isDisplayed(userRoles.getRowByText(requiredData.get(3))));
			value = foundation.getAttribute(userRoles.getAgeVerificationFeature(requiredData.get(6)),
					requiredData.get(4));
			CustomisedAssert.assertEquals(value, requiredData.get(5));
			value = foundation.getAttribute(userRoles.getAgeVerificationFeature(requiredData.get(7)),
					requiredData.get(4));
			CustomisedAssert.assertEquals(value, requiredData.get(5));
			value = foundation.getAttribute(userRoles.getAgeVerificationFeature(requiredData.get(8)),
					requiredData.get(4));
			CustomisedAssert.assertEquals(value, requiredData.get(5));
			value = foundation.getAttribute(userRoles.getAgeVerificationFeature(requiredData.get(9)),
					requiredData.get(4));
			CustomisedAssert.assertEquals(value, requiredData.get(5));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// Resetting Age Verification Checkbox
			navigationBar.navigateToMenuItem(menus.get(0));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			foundation.scrollIntoViewElement(LocationSummary.TXT_AGE_VERIFICATION);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_AGE_VERIFICATION));
			if (checkBox.isChkEnabled(LocationSummary.CHK_AGE_VERIFICATION))
				checkBox.unCheck(LocationSummary.CHK_AGE_VERIFICATION);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			login.logout();
			browser.close();
		}
	}

	@Test(description = "169024-ADM > Admin > Age Verification when to showhide new sub nav NEW")
	public void verifyLocationAvailableAsSuperAndoperator() {
		final String CASE_NUM = "169024";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);

		List<String> menus = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));

		try {
			navigationBar.launchBrowserAsSuperAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Select Menu, Menu Item and verify the age verification disabled
			navigationBar.navigateToMenuItem(menus.get(0));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.AGE_VERIFICATION));
			foundation.isDisabled(LocationSummary.AGE_VERIFICATION);
			checkBox.check(LocationSummary.AGE_VERIFICATION);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);

			// Navigate to Admin tab and verify Age Verification Sub Tab is present or not
			List<String> tabNames = navigationBar.getSubTabs(menus.get(1));
			CustomisedAssert.assertTrue(tabNames.contains(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION)));
			navigationBar.navigateToMenuItem(menus.get(1));
			CustomisedAssert.assertTrue(foundation.isDisplayed(AgeVerificationDetails.TXT_AGE_VERIFICATION));
			foundation.click(AgeVerificationDetails.DPD_LOCATION);
			foundation.threadWait(Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(ageVerificationDetails
					.automationNewLocation(rstLocationListData.get(CNLocationList.LOCATION_NAME))));
			login.logout();

			// Navigate to operator user
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menus.get(0));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			foundation.scrollIntoViewElement(LocationSummary.TXT_AGE_VERIFICATION);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_AGE_VERIFICATION));
			if (checkBox.isChkEnabled(LocationSummary.CHK_AGE_VERIFICATION))
				checkBox.check(LocationSummary.CHK_AGE_VERIFICATION);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);

			// Navigate to Admin tab and verify Age Verification Sub Tab is present or not
			List<String> tabName = navigationBar.getSubTabs(menus.get(1));
			CustomisedAssert.assertTrue(tabName.contains(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION)));
			navigationBar.navigateToMenuItem(menus.get(1));
			CustomisedAssert.assertTrue(foundation.isDisplayed(AgeVerificationDetails.TXT_AGE_VERIFICATION));
			foundation.threadWait(Constants.THREE_SECOND);
			foundation.click(AgeVerificationDetails.DPD_LOCATION);
			foundation.threadWait(Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(ageVerificationDetails
					.automationNewLocation(rstLocationListData.get(CNLocationList.LOCATION_NAME))));
			login.logout();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// Navigate to location to disable the age verification in super user
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menus.get(0));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			foundation.scrollIntoViewElement(LocationSummary.TXT_AGE_VERIFICATION);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_AGE_VERIFICATION));
			if (checkBox.isChecked(LocationSummary.CHK_AGE_VERIFICATION))
				checkBox.unCheck(LocationSummary.CHK_AGE_VERIFICATION);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			login.logout();

			// Navigate to operator user and disable the age verification
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menus.get(0));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			foundation.scrollIntoViewElement(LocationSummary.TXT_AGE_VERIFICATION);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_AGE_VERIFICATION));
			if (checkBox.isChecked(LocationSummary.CHK_AGE_VERIFICATION))
				checkBox.unCheck(LocationSummary.CHK_AGE_VERIFICATION);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			login.logout();
		}
	}

	/**
	 * @author karthikr SOS-25748
	 * @date - 22/06/2022
	 */
	@Test(description = "168144 -Email Templates > Age Verification QR & Six Digit PIN Code > Standard Locations")
	public void verifyAgeVerificationQRCodeInEmailForStandardLocations() {
		final String CASE_NUM = "168144";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstAdminAgeVerificationData = dataBase.getAdminAgeVerificationData(Queries.ADMIN_AGE_VERIFICATION, CASE_NUM);

		List<String> menus = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> requiredData = Arrays.asList(
				rstAdminAgeVerificationData.get(CNAdminAgeVerification.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		try {
			// Select Menu and Location
			navigationBar.launchBrowserAsSuperAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			locationList.selectLocationName(rstAdminAgeVerificationData.get(CNAdminAgeVerification.LOCATION_NAME));

			// Verifying the selection of defaults for Age Verification
			ageVerificationDetails.checkingOnDefaultsOfAgeVerification();

			// Navigate to Admin > Age Verification Sub Tab
			navigationBar.navigateToMenuItem(menus.get(1));
			CustomisedAssert.assertTrue(foundation.isDisplayed(AgeVerificationDetails.TXT_AGE_VERIFICATION));

			// Creating Age Verification PIN
			ageVerificationDetails.createAgeVerificationPin(
					rstAdminAgeVerificationData.get(CNAdminAgeVerification.LOCATION_NAME), requiredData);
			login.logout();

			// Open Outlook Server to validate the email Content
			navigationBar.launchBrowserWithOutLookMail();

			// Verify Email and Validate the QR code
			ageVerificationDetails.openingFolderAndClickMail(
					rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION), requiredData.get(9));
			String code = ageVerificationDetails.validateMailContent(requiredData.get(8),
					rstAdminAgeVerificationData.get(CNAdminAgeVerification.LOCATION_NAME), requiredData.get(1));
			ageVerificationDetails.deleteOutLookMailAndLogout();

			// Again Login into ADM, Navigate to Age Verification Sub Tab and click Resend
			navigationBar.launchBrowserAsSuperAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menus.get(1));
			foundation.click(AgeVerificationDetails.BTN_RESEND);
			login.logout();

			// Again Login to Outlook Server
			navigationBar.launchBrowserWithOutLookMail();

			// Verify Email and Validate the QR code
			ageVerificationDetails.openingFolderAndClickMail(
					rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION), requiredData.get(9));
			String QRcode = ageVerificationDetails.validateMailContent(requiredData.get(8),
					rstAdminAgeVerificationData.get(CNAdminAgeVerification.LOCATION_NAME), requiredData.get(1));
			CustomisedAssert.assertTrue(code != QRcode);
			ageVerificationDetails.deleteOutLookMailAndLogout();

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// Again Login into ADM application and Navigate to Age Verification Sub Tab
			navigationBar.launchBrowserAsSuperAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menus.get(1));
			CustomisedAssert.assertTrue(foundation.isDisplayed(AgeVerificationDetails.TXT_AGE_VERIFICATION));

			// Verify Expire Pin Confirmation Prompt with clicking Yes
			ageVerificationDetails.expirePinWithConfirmationPrompt(
					rstAdminAgeVerificationData.get(CNAdminAgeVerification.LOCATION_NAME), requiredData.get(0));

			// Resetting Age Verification Checkbox
			ageVerificationDetails.resettingAgeVerificationCheckBox(menus.get(0),
					rstAdminAgeVerificationData.get(CNAdminAgeVerification.LOCATION_NAME));
			login.logout();
			browser.close();
		}
	}
}
