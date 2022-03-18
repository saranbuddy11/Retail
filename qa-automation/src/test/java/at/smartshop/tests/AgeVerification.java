package at.smartshop.tests;

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
import at.smartshop.database.columns.CNAdminAgeVerification;
import at.smartshop.database.columns.CNLocationList;
import at.smartshop.database.columns.CNNavigationMenu;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.pages.AgeVerificationDetails;
import at.smartshop.pages.LocationList;
import at.smartshop.pages.LocationSummary;
import at.smartshop.pages.NavigationBar;

@Listeners(at.framework.reportsetup.Listeners.class)

public class AgeVerification extends TestInfra {
	private ResultSets dataBase = new ResultSets();
	private Foundation foundation = new Foundation();
	private NavigationBar navigationBar = new NavigationBar();
	private LocationList locationList = new LocationList();
	private CheckBox checkBox = new CheckBox();
	private Dropdown dropDown = new Dropdown();
	private AgeVerificationDetails ageVerificationDetails = new AgeVerificationDetails();
	private DateAndTime dateAndTime = new DateAndTime();

	private Map<String, String> rstNavigationMenuData;
	private Map<String, String> rstLocationListData;
	private Map<String, String> rstAdminAgeVerificationData;

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
			foundation.scrollIntoViewElement(LocationSummary.TXT_AGE_VERIFICATION);
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
			foundation.scrollIntoViewElement(LocationSummary.TXT_AGE_VERIFICATION);
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
			foundation.threadWait(Constants.TWO_SECOND);
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

	@Test(description = "168291 - verify the default status on pin status filter"
			+ "168292 - verify the options in pin status dropdown" + "168293 - verify the expired option"
			+ "168294 - verify the active option" + "168295 - verify the all option"
			+ "168296 - verify active option on all the pages" + "168297 - verify expired option on all the pages")
	public void verifyDefaultPinStatusInSuperUser() {
		final String CASE_NUM = "168291";

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
			CustomisedAssert.assertEquals(tabNames.get(16),
					rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));
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

	@Test(description = "168298 - verify the default status on pin status filter"
			+ "168299 - verify the options in pin status dropdown" + "168300 - verify the expired option"
			+ "168301 - verify the active option" + "168302 - verify the all option"
			+ "168303 - verify active option on all the pages" + "168304 - verify expired option on all the pages")
	public void verifyDefaultPinStatusInOperatorUser() {
		final String CASE_NUM = "168298";

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
			CustomisedAssert.assertEquals(tabNames.get(14),
					rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));
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

	@Test(description = "168558 - Age verification enable by operator")
	public void verifyAgeVerificationByOperator() {
		final String CASE_NUM = "168558";

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

	@Test(description = "168562- Age verification enable by super")
	public void verifyAgeVerificationBySuper() {
		final String CASE_NUM = "168562";

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

	@Test(description = "168560-Verify the location is availble on age verification screen after it is enabled at location summary page")
	public void verifyLocationAvailableInAgeVerificationAsOperator() {
		final String CASE_NUM = "168560";

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

	@Test(description = "168564-Verify the location is availble on age verification screen after it is enabled at location summary page")
	public void verifyLocationAvailableInAgeVerificationAsSuper() {
		final String CASE_NUM = "168564";

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
}
