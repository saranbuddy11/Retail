package at.smartshop.tests;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import at.framework.database.mssql.Queries;
import at.framework.database.mssql.ResultSets;
import at.framework.files.PropertyFile;
import at.framework.generic.DateAndTime;
import at.framework.ui.CheckBox;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.database.columns.CNLocationList;
import at.smartshop.database.columns.CNLocationSummary;
import at.smartshop.database.columns.CNNavigationMenu;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.pages.CreateLocation;
import at.smartshop.pages.LocationList;
import at.smartshop.pages.LocationSummary;
import at.smartshop.pages.NavigationBar;

@Listeners(at.framework.reportsetup.Listeners.class)
public class ConsumerSubsidy extends TestInfra {

	private ResultSets dataBase = new ResultSets();
	private NavigationBar navigationBar = new NavigationBar();
	private PropertyFile propertyFile = new PropertyFile();
	private Dropdown dropDown = new Dropdown();
	private TextBox textBox = new TextBox();
	private DateAndTime dateAndTime = new DateAndTime();
	private Foundation foundation = new Foundation();
	private LocationSummary locationSummary = new LocationSummary();
	private LocationList locationList = new LocationList();
	private CheckBox checkBox = new CheckBox();

	private Map<String, String> rstNavigationMenuData;
	private Map<String, String> rstLocationListData;
	private Map<String, String> rstLocationSummaryData;

	@Test(description = "166050-Verify the selection of defaults for the GMA subsidy")
	public void verifySelectionDefaults() {
		final String CASE_NUM = "166050";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
		rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);

		List<String> requiredData = Arrays
				.asList(rstLocationSummaryData.get(CNLocationSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));

		try {

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			Assert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Select Menu, Menu Item and Location
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));

			// Verifying the selection of defaults for the Both GMA subsidy
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			Assert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_GMA_SUBSIDY));
			String value = dropDown.getSelectedItem(LocationSummary.DPD_GMA_SUBSIDY);
			Assert.assertEquals(value, requiredData.get(1));
			dropDown.selectItem(LocationSummary.DPD_GMA_SUBSIDY, requiredData.get(0), Constants.TEXT);
			locationSummary.verifyTopOffSubsidy(requiredData);
			locationSummary.verifyRolloverSubsidy(requiredData);
			if (checkBox.isChkEnabled(LocationSummary.CHK_TOP_OFF_SUBSIDY)) {
				checkBox.check(LocationSummary.CHK_TOP_OFF_SUBSIDY);
				foundation.threadWait(Constants.ONE_SECOND);
				Assert.assertFalse(checkBox.isChkEnabled(LocationSummary.CHK_ROLL_OVER_SUBSIDY));
				checkBox.unCheck(LocationSummary.CHK_TOP_OFF_SUBSIDY);
			} else if (checkBox.isChkEnabled(LocationSummary.CHK_ROLL_OVER_SUBSIDY)) {
				checkBox.check(LocationSummary.CHK_ROLL_OVER_SUBSIDY);
				foundation.threadWait(Constants.ONE_SECOND);
				Assert.assertFalse(checkBox.isChkEnabled(LocationSummary.CHK_TOP_OFF_SUBSIDY));
				checkBox.unCheck(LocationSummary.CHK_ROLL_OVER_SUBSIDY);
			}

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// resetting test data
			locationSummary.subsidyResettingOff(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM),
					rstLocationListData.get(CNLocationList.LOCATION_NAME), requiredData.get(1));
		}
	}

	@Test(description = "166051-Verify the whether the default radio buttons are mandatory fields.")
	public void verifyDefaultRadioButtonsMandatory() {
		final String CASE_NUM = "166051";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
		rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);

		List<String> requiredData = Arrays
				.asList(rstLocationSummaryData.get(CNLocationSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));

		try {

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			Assert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Select Menu, Menu Item and Location
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));

			// Verifying default radio buttons are mandatory fields for both subsidy
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			Assert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_GMA_SUBSIDY));
			String value = dropDown.getSelectedItem(LocationSummary.DPD_GMA_SUBSIDY);
			Assert.assertEquals(value, requiredData.get(1));
			dropDown.selectItem(LocationSummary.DPD_GMA_SUBSIDY, requiredData.get(0), Constants.TEXT);
			locationSummary.verifyTopOffSubsidy(requiredData);
			locationSummary.verifyRolloverSubsidy(requiredData);
			locationSummary.enterGroupNames(requiredData.get(7), requiredData.get(8), requiredData.get(9));
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// resetting test data
			locationSummary.subsidyResettingOff(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM),
					rstLocationListData.get(CNLocationList.LOCATION_NAME), requiredData.get(1));
		}
	}

	@Test(description = "165962-To Verify setting start date for \"Weekly\" GMA Subsidy that the location")
	public void verifySettingStartDateForWeeklySubsidy() {
		final String CASE_NUM = "165962";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
		rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);

		List<String> requiredData = Arrays
				.asList(rstLocationSummaryData.get(CNLocationSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));

		String currentDate = dateAndTime.getDateAndTime(Constants.REGEX_MM_DD_YYYY, Constants.TIME_ZONE_INDIA);

		try {

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			Assert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Select Menu, Menu Item and Location
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));

			// Verifying GMA subsidy field
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			Assert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_GMA_SUBSIDY));
			String value = dropDown.getSelectedItem(LocationSummary.DPD_GMA_SUBSIDY);
			Assert.assertEquals(value, requiredData.get(1));
			dropDown.selectItem(LocationSummary.DPD_GMA_SUBSIDY, requiredData.get(0), Constants.TEXT);
			locationSummary.verifyTopOffSubsidy(requiredData);
			locationSummary.verifyRolloverSubsidy(requiredData);
			checkBox.check(LocationSummary.CHK_TOP_OFF_SUBSIDY);
			foundation.click(LocationSummary.START_DATE_PICKER_TOP_OFF);
			locationSummary.verifyTopOffDateAutoLocation1(currentDate);

			// Setting start date of GMA Subsidy as Weekly with Group Names
			dropDown.selectItem(LocationSummary.DPD_TOP_OFF_RECURRENCE, requiredData.get(10), Constants.TEXT);
			locationSummary.enterGroupNames(requiredData.get(7), requiredData.get(8), requiredData.get(9));
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// resetting test data
			locationSummary.subsidyResettingOffWithRecurrence(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM),
					rstLocationListData.get(CNLocationList.LOCATION_NAME), requiredData.get(1), requiredData.get(11));
		}
	}

	@Test(description = "165961 - To Verify setting start date for \"Daily\" GMA Subsidy that the location")
	public void verifySettingStartDateForDailySubsidy() {
		final String CASE_NUM = "165961";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
		rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);

		List<String> requiredData = Arrays
				.asList(rstLocationSummaryData.get(CNLocationSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		String currentDate = dateAndTime.getDateAndTime(Constants.REGEX_MM_DD_YYYY, Constants.TIME_ZONE_INDIA);

		try {

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			Assert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Select Menu, Menu Item and Location
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));

			// Verifying GMA Subsidy fields
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			Assert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_GMA_SUBSIDY));
			String value = dropDown.getSelectedItem(LocationSummary.DPD_GMA_SUBSIDY);
			Assert.assertEquals(value, requiredData.get(1));
			dropDown.selectItem(LocationSummary.DPD_GMA_SUBSIDY, requiredData.get(0), Constants.TEXT);
			locationSummary.verifyTopOffSubsidy(requiredData);
			locationSummary.verifyRolloverSubsidy(requiredData);
			checkBox.check(LocationSummary.CHK_TOP_OFF_SUBSIDY);
			foundation.click(LocationSummary.START_DATE_PICKER_TOP_OFF);
			locationSummary.verifyTopOffDateAutoLocation1(currentDate);

			// Setting start date of GMA Subsidy as Daily with Group Names
			dropDown.selectItem(LocationSummary.DPD_TOP_OFF_RECURRENCE, requiredData.get(11), Constants.TEXT);
			locationSummary.enterGroupNames(requiredData.get(7), requiredData.get(8), requiredData.get(9));
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// resetting test data
			locationSummary.subsidyResettingOffWithRecurrence(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM),
					rstLocationListData.get(CNLocationList.LOCATION_NAME), requiredData.get(1), requiredData.get(11));
		}
	}

	@Test(description = "165963 - To Verify setting start date for \"Monthly\" GMA Subsidy that the location")
	public void verifySettingStartDateForMonthlySubsidy() {
		final String CASE_NUM = "165963";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
		rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);

		List<String> requiredData = Arrays
				.asList(rstLocationSummaryData.get(CNLocationSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		String currentDate = dateAndTime.getDateAndTime(Constants.REGEX_MM_DD_YYYY, Constants.TIME_ZONE_INDIA);

		try {

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			Assert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Select Menu, Menu Item and Location
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));

			// Verifying GMA subsidy fields
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			Assert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_GMA_SUBSIDY));
			String value = dropDown.getSelectedItem(LocationSummary.DPD_GMA_SUBSIDY);
			Assert.assertEquals(value, requiredData.get(1));
			dropDown.selectItem(LocationSummary.DPD_GMA_SUBSIDY, requiredData.get(0), Constants.TEXT);
			locationSummary.verifyTopOffSubsidy(requiredData);
			locationSummary.verifyRolloverSubsidy(requiredData);
			checkBox.check(LocationSummary.CHK_TOP_OFF_SUBSIDY);
			foundation.click(LocationSummary.START_DATE_PICKER_TOP_OFF);
			locationSummary.verifyTopOffDateAutoLocation1(currentDate);

			// Setting start date of GMA Subsidy as Monthly with Group Names
			dropDown.selectItem(LocationSummary.DPD_TOP_OFF_RECURRENCE, requiredData.get(12), Constants.TEXT);
			locationSummary.enterGroupNames(requiredData.get(7), requiredData.get(8), requiredData.get(9));
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// resetting test data
			locationSummary.subsidyResettingOffWithRecurrence(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM),
					rstLocationListData.get(CNLocationList.LOCATION_NAME), requiredData.get(1), requiredData.get(11));
		}
	}

	@Test(description = "166056 - Verify the 'Amount' field for both the Top of subsidy and Rollover subsidy")
	public void verifyAmountFieldInBothSubsidy() {
		final String CASE_NUM = "166056";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
		rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);

		List<String> requiredData = Arrays
				.asList(rstLocationSummaryData.get(CNLocationSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		String currentDate = dateAndTime.getDateAndTime(Constants.REGEX_MM_DD_YYYY, Constants.TIME_ZONE_INDIA);

		try {

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			Assert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Select Menu, Menu Item and Location
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));

			// Verifying GMA subsidy fields
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			Assert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_GMA_SUBSIDY));
			String value = dropDown.getSelectedItem(LocationSummary.DPD_GMA_SUBSIDY);
			Assert.assertEquals(value, requiredData.get(1));
			dropDown.selectItem(LocationSummary.DPD_GMA_SUBSIDY, requiredData.get(0), Constants.TEXT);
			locationSummary.verifyTopOffSubsidy(requiredData);
			locationSummary.verifyRolloverSubsidy(requiredData);
			dropDown.selectItem(LocationSummary.DPD_TOP_OFF_RECURRENCE, requiredData.get(10), Constants.TEXT);
			textBox.enterText(LocationSummary.TXT_TOP_OFF_GROUP_NAME, requiredData.get(7));

			// Validating Amount field of Top Off subsidy
			textBox.enterText(LocationSummary.TXT_TOP_OFF_AMOUNT, requiredData.get(11));
			assertTrue(textBox.getTextFromInput(LocationSummary.TXT_TOP_OFF_AMOUNT_VALUE).equals("0"));
			textBox.enterText(LocationSummary.TXT_TOP_OFF_AMOUNT, requiredData.get(12));
			textBox.enterText(LocationSummary.TXT_ROLL_OVER_GROUP_NAME, requiredData.get(8));
			textBox.enterText(LocationSummary.TXT_PICKUP_LOCATION_NAME, requiredData.get(9));
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_ERROR_MSG, Constants.SHORT_TIME);
			Assert.assertTrue(foundation.isDisplayed(LocationSummary.CHK_TOP_OFF_ERROR));
			textBox.enterText(LocationSummary.TXT_TOP_OFF_AMOUNT, requiredData.get(13));
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);

			// Validating Amount field of Roll Over subsidy
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			Assert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_GMA_SUBSIDY));
			checkBox.unCheck(LocationSummary.CHK_TOP_OFF_SUBSIDY);
			checkBox.check(LocationSummary.CHK_ROLL_OVER_SUBSIDY);
			foundation.click(LocationSummary.START_DATE_PICKER_ROLL_OVER);
			locationSummary.verifyRollOverCurrentDate(currentDate);
			dropDown.selectItem(LocationSummary.DPD_ROLL_OVER_RECURRENCE, requiredData.get(10), Constants.TEXT);
			textBox.enterText(LocationSummary.TXT_ROLL_OVER_GROUP_NAME, requiredData.get(8));
			textBox.enterText(LocationSummary.TXT_ROLL_OVER_AMOUNT, requiredData.get(11));
			assertTrue(textBox.getTextFromInput(LocationSummary.TXT_ROLL_OVER_AMOUNT_VALUE).equals("0"));
			textBox.enterText(LocationSummary.TXT_ROLL_OVER_AMOUNT, requiredData.get(12));
			textBox.enterText(LocationSummary.TXT_PICKUP_LOCATION_NAME, requiredData.get(9));
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_ERROR_MSG, Constants.SHORT_TIME);
			Assert.assertTrue(foundation.isDisplayed(LocationSummary.CHK_ROLL_OVER_ERROR));
			textBox.enterText(LocationSummary.TXT_ROLL_OVER_AMOUNT, requiredData.get(13));
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {

			// resetting test data
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			foundation.waitforElement(
					locationList.getlocationElement(rstLocationListData.get(CNLocationList.LOCATION_NAME)),
					Constants.SHORT_TIME);
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			foundation.threadWait(Constants.ONE_SECOND);
			checkBox.check(LocationSummary.CHK_ROLL_OVER_SUBSIDY);
			dropDown.selectItem(LocationSummary.DPD_TOP_OFF_RECURRENCE, requiredData.get(10), Constants.TEXT);
			dropDown.selectItem(LocationSummary.DPD_GMA_SUBSIDY, requiredData.get(1), Constants.TEXT);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
		}
	}

	@Test(description = "166054 - Verify the 'Recurrence' field for both the Top of subsidy and Rollover subsidy")
	public void verifyRecurrenceFieldInBothSubsidy() {
		final String CASE_NUM = "166054";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
		rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);

		List<String> requiredData = Arrays
				.asList(rstLocationSummaryData.get(CNLocationSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		String recurrence = new String();

		try {

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			Assert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Select Menu, Menu Item and Location
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));

			// Verifying GMA subsidy field
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			Assert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_GMA_SUBSIDY));
			String value = dropDown.getSelectedItem(LocationSummary.DPD_GMA_SUBSIDY);
			Assert.assertEquals(value, requiredData.get(1));
			dropDown.selectItem(LocationSummary.DPD_GMA_SUBSIDY, requiredData.get(0), Constants.TEXT);
			locationSummary.verifyTopOffSubsidy(requiredData);
			locationSummary.verifyRolloverSubsidy(requiredData);
			checkBox.check(LocationSummary.CHK_TOP_OFF_SUBSIDY);

			// Validating Recurrence field of Top Off subsidy
			dropDown.selectItem(LocationSummary.DPD_TOP_OFF_RECURRENCE, requiredData.get(7), Constants.TEXT);
			recurrence = dropDown.getSelectedItem(LocationSummary.DPD_TOP_OFF_RECURRENCE);
			Assert.assertEquals(recurrence, requiredData.get(7));
			dropDown.selectItem(LocationSummary.DPD_TOP_OFF_RECURRENCE, requiredData.get(8), Constants.TEXT);
			recurrence = dropDown.getSelectedItem(LocationSummary.DPD_TOP_OFF_RECURRENCE);
			Assert.assertEquals(recurrence, requiredData.get(8));
			dropDown.selectItem(LocationSummary.DPD_TOP_OFF_RECURRENCE, requiredData.get(9), Constants.TEXT);
			recurrence = dropDown.getSelectedItem(LocationSummary.DPD_TOP_OFF_RECURRENCE);
			Assert.assertEquals(recurrence, requiredData.get(9));
			checkBox.unCheck(LocationSummary.CHK_TOP_OFF_SUBSIDY);

			// Validating Recurrence field of Roll Over subsidy
			checkBox.check(LocationSummary.CHK_ROLL_OVER_SUBSIDY);
			dropDown.selectItem(LocationSummary.DPD_ROLL_OVER_RECURRENCE, requiredData.get(7), Constants.TEXT);
			recurrence = dropDown.getSelectedItem(LocationSummary.DPD_ROLL_OVER_RECURRENCE);
			Assert.assertEquals(recurrence, requiredData.get(7));
			dropDown.selectItem(LocationSummary.DPD_ROLL_OVER_RECURRENCE, requiredData.get(8), Constants.TEXT);
			recurrence = dropDown.getSelectedItem(LocationSummary.DPD_ROLL_OVER_RECURRENCE);
			Assert.assertEquals(recurrence, requiredData.get(8));
			dropDown.selectItem(LocationSummary.DPD_ROLL_OVER_RECURRENCE, requiredData.get(9), Constants.TEXT);
			recurrence = dropDown.getSelectedItem(LocationSummary.DPD_ROLL_OVER_RECURRENCE);
			Assert.assertEquals(recurrence, requiredData.get(9));
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// resetting test data
			locationSummary.subsidyResettingValidationOff(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM),
					rstLocationListData.get(CNLocationList.LOCATION_NAME), requiredData.get(1));
		}
	}

	@Test(description = "165958 - Verify the 'Top Off Subsidy' setting up for Subsidy groups in Location Summary Page")
	public void verifyTopOffSubsidy() {
		final String CASE_NUM = "165958";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
		rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);

		List<String> requiredData = Arrays
				.asList(rstLocationSummaryData.get(CNLocationSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		String currentDate = dateAndTime.getDateAndTime(Constants.REGEX_MM_DD_YYYY, Constants.TIME_ZONE_INDIA);
		String recurrence = new String();

		try {

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			Assert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Select Menu, Menu Item and Location
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));

			// Verifying GMA subsidy field
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			Assert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_GMA_SUBSIDY));
			String value = dropDown.getSelectedItem(LocationSummary.DPD_GMA_SUBSIDY);
			Assert.assertEquals(value, requiredData.get(0));
			locationSummary.verifyTopOffSubsidy(requiredData);
			locationSummary.verifyRolloverSubsidy(requiredData);
			if (checkBox.isChkEnabled(LocationSummary.CHK_TOP_OFF_SUBSIDY))
				checkBox.check(LocationSummary.CHK_TOP_OFF_SUBSIDY);
			Assert.assertFalse(checkBox.isChkEnabled(LocationSummary.CHK_ROLL_OVER_SUBSIDY));
			checkBox.unCheck(LocationSummary.CHK_TOP_OFF_SUBSIDY);
			if (checkBox.isChkEnabled(LocationSummary.CHK_ROLL_OVER_SUBSIDY))
				checkBox.check(LocationSummary.CHK_ROLL_OVER_SUBSIDY);
			Assert.assertFalse(checkBox.isChkEnabled(LocationSummary.CHK_TOP_OFF_SUBSIDY));
			checkBox.unCheck(LocationSummary.CHK_ROLL_OVER_SUBSIDY);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			Assert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_GMA_SUBSIDY));
			checkBox.check(LocationSummary.CHK_TOP_OFF_SUBSIDY);

			// Validating Start Date picker field of Top Off subsidy
			foundation.click(LocationSummary.START_DATE_PICKER_TOP_OFF);
			locationSummary.verifyTopOffDateAutoLocation2(currentDate);
			String futureDate = dateAndTime.getFutureDate(Constants.REGEX_MM_DD_YYYY, requiredData.get(10));
			foundation.click(LocationSummary.START_DATE_PICKER_TOP_OFF);
			locationSummary.verifyTopOffFutureDateAutoLocation2(futureDate);

			// Validating Recurrence field of Top Off subsidy
			dropDown.selectItem(LocationSummary.DPD_TOP_OFF_RECURRENCE, requiredData.get(7), Constants.TEXT);
			recurrence = dropDown.getSelectedItem(LocationSummary.DPD_TOP_OFF_RECURRENCE);
			Assert.assertEquals(recurrence, requiredData.get(7));
			dropDown.selectItem(LocationSummary.DPD_TOP_OFF_RECURRENCE, requiredData.get(8), Constants.TEXT);
			recurrence = dropDown.getSelectedItem(LocationSummary.DPD_TOP_OFF_RECURRENCE);
			Assert.assertEquals(recurrence, requiredData.get(8));
			dropDown.selectItem(LocationSummary.DPD_TOP_OFF_RECURRENCE, requiredData.get(9), Constants.TEXT);
			recurrence = dropDown.getSelectedItem(LocationSummary.DPD_TOP_OFF_RECURRENCE);
			Assert.assertEquals(recurrence, requiredData.get(9));
			checkBox.unCheck(LocationSummary.CHK_TOP_OFF_SUBSIDY);
			checkBox.check(LocationSummary.CHK_ROLL_OVER_SUBSIDY);

			// Validating Recurrence field of Roll Over subsidy
			dropDown.selectItem(LocationSummary.DPD_ROLL_OVER_RECURRENCE, requiredData.get(7), Constants.TEXT);
			recurrence = dropDown.getSelectedItem(LocationSummary.DPD_ROLL_OVER_RECURRENCE);
			Assert.assertEquals(recurrence, requiredData.get(7));
			dropDown.selectItem(LocationSummary.DPD_ROLL_OVER_RECURRENCE, requiredData.get(8), Constants.TEXT);
			recurrence = dropDown.getSelectedItem(LocationSummary.DPD_ROLL_OVER_RECURRENCE);
			Assert.assertEquals(recurrence, requiredData.get(8));
			dropDown.selectItem(LocationSummary.DPD_ROLL_OVER_RECURRENCE, requiredData.get(9), Constants.TEXT);
			recurrence = dropDown.getSelectedItem(LocationSummary.DPD_ROLL_OVER_RECURRENCE);
			Assert.assertEquals(recurrence, requiredData.get(9));

			// Validating Group Name field of Both subsidy
			textBox.enterText(LocationSummary.TXT_TOP_OFF_GROUP_NAME, requiredData.get(11));
			Assert.assertTrue(foundation.isDisplayed(LocationSummary.TOP_OFF_WARNING_MSG));
			textBox.enterText(LocationSummary.TXT_TOP_OFF_GROUP_NAME, requiredData.get(12));
			textBox.enterText(LocationSummary.TXT_ROLL_OVER_GROUP_NAME, requiredData.get(12));
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_ERROR_MSG, Constants.SHORT_TIME);
			Assert.assertTrue(foundation.isDisplayed(LocationSummary.ROLL_OVER_WARNING_MSG));
			textBox.enterText(LocationSummary.TXT_ROLL_OVER_GROUP_NAME, requiredData.get(13));
			foundation.click(LocationSummary.BTN_SAVE);
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			Assert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_GMA_SUBSIDY));
			checkBox.unCheck(LocationSummary.CHK_ROLL_OVER_SUBSIDY);
			checkBox.check(LocationSummary.CHK_TOP_OFF_SUBSIDY);

			// Validating Amount field of Top Off subsidy
			textBox.enterText(LocationSummary.TXT_TOP_OFF_AMOUNT, requiredData.get(14));
			assertTrue(textBox.getTextFromInput(LocationSummary.TXT_TOP_OFF_AMOUNT_VALUE).equals("0"));
			textBox.enterText(LocationSummary.TXT_TOP_OFF_AMOUNT, requiredData.get(15));
			textBox.enterText(LocationSummary.TXT_ROLL_OVER_GROUP_NAME, requiredData.get(13));
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_ERROR_MSG, Constants.SHORT_TIME);
			Assert.assertTrue(foundation.isDisplayed(LocationSummary.CHK_TOP_OFF_ERROR));
			textBox.enterText(LocationSummary.TXT_TOP_OFF_AMOUNT, requiredData.get(16));
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			Assert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_GMA_SUBSIDY));

			// Validating Delete and Add subsidy of Top Off subsidy
			Assert.assertTrue(foundation.isDisplayed(LocationSummary.BTN_DELETE_TOP_OFF));
			foundation.click(LocationSummary.BTN_DELETE_TOP_OFF);
			foundation.waitforElement(LocationList.TXT_SPINNER_ERROR_MSG, Constants.SHORT_TIME);
			checkBox.unCheck(LocationSummary.CHK_TOP_OFF_SUBSIDY);
			checkBox.check(LocationSummary.CHK_ROLL_OVER_SUBSIDY);

			// Validating Delete and Add subsidy of Roll Over subsidy
			Assert.assertTrue(foundation.isDisplayed(LocationSummary.BTN_DELETE_ROLL_OVER));
			foundation.click(LocationSummary.BTN_DELETE_ROLL_OVER);
			foundation.waitforElement(LocationList.TXT_SPINNER_ERROR_MSG, Constants.SHORT_TIME);
			Assert.assertTrue(foundation.isDisplayed(LocationSummary.BTN_ADD_ROLL_OVER));
			locationSummary.verifySignsTopOff();
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// resetting test data
			locationSummary.subsidyResettingValidationOff(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM),
					rstLocationListData.get(CNLocationList.LOCATION_NAME), requiredData.get(0));
		}
	}

	@Test(description = "166052 - Verify the duplicate names for both the Top of subsidy and Rollover subsidy")
	public void verifyDuplicateNamesforBothSubsidy() {
		final String CASE_NUM = "166052";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
		rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);

		List<String> requiredData = Arrays
				.asList(rstLocationSummaryData.get(CNLocationSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));

		try {

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			Assert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Select Menu, Menu Item and Location
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));

			// Verifying GMA subsidy fields
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			Assert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_GMA_SUBSIDY));
			String value = dropDown.getSelectedItem(LocationSummary.DPD_GMA_SUBSIDY);
			Assert.assertEquals(value, requiredData.get(1));
			dropDown.selectItem(LocationSummary.DPD_GMA_SUBSIDY, requiredData.get(0), Constants.TEXT);
			locationSummary.verifyTopOffSubsidy(requiredData);
			locationSummary.verifyRolloverSubsidy(requiredData);

			// Verifying the duplicate names for both subsidy
			textBox.enterText(LocationSummary.TXT_TOP_OFF_GROUP_NAME, requiredData.get(7));
			textBox.enterText(LocationSummary.TXT_ROLL_OVER_GROUP_NAME, requiredData.get(7));
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_ERROR_MSG, Constants.SHORT_TIME);
			Assert.assertTrue(foundation.isDisplayed(LocationSummary.ROLL_OVER_WARNING_MSG));
			textBox.enterText(LocationSummary.TXT_ROLL_OVER_GROUP_NAME, requiredData.get(8));
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.threadWait(Constants.ONE_SECOND);

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// resetting test data
			locationSummary.subsidyResettingValidationOff(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM),
					rstLocationListData.get(CNLocationList.LOCATION_NAME), requiredData.get(1));
		}
	}

	@Test(description = "166049 - verify the GMA subsidy field when its set to 'Yes' under the location summary page")
	public void verifyGMASubsidyFieldYes() {
		final String CASE_NUM = "166049";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
		rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);

		List<String> requiredData = Arrays
				.asList(rstLocationSummaryData.get(CNLocationSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));

		try {

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			Assert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Select Menu, Menu Item and Location
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));

			// Verifying GMA subsidy fields
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			Assert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_GMA_SUBSIDY));
			String value = dropDown.getSelectedItem(LocationSummary.DPD_GMA_SUBSIDY);
			Assert.assertEquals(value, requiredData.get(1));
			dropDown.selectItem(LocationSummary.DPD_GMA_SUBSIDY, requiredData.get(0), Constants.TEXT);
			locationSummary.verifyTopOffSubsidy(requiredData);
			locationSummary.verifyRolloverSubsidy(requiredData);

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// resetting test data
			locationSummary.subsidyResettingValidationOff(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM),
					rstLocationListData.get(CNLocationList.LOCATION_NAME), requiredData.get(1));
		}
	}

	@Test(description = "166048 - verify the GMA subsidy under the location summary page")
	public void verifyGMASubsidy() {
		final String CASE_NUM = "166048";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
		rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);

		List<String> requiredData = Arrays
				.asList(rstLocationSummaryData.get(CNLocationSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));

		try {

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			Assert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Select Menu, Menu Item and Location
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));

			// Verifying GMA subsidy fields
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			Assert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_MULTI_TAX_REPORT));
			Assert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_GMA_SUBSIDY));
			List<String> values = dropDown.getAllItems(LocationSummary.DPD_GMA_SUBSIDY);
			Assert.assertTrue(values.equals(requiredData));
			String value = dropDown.getSelectedItem(LocationSummary.DPD_GMA_SUBSIDY);
			Assert.assertEquals(value, requiredData.get(0));
			dropDown.selectItem(LocationSummary.DPD_GMA_SUBSIDY, requiredData.get(1), Constants.TEXT);
			value = dropDown.getSelectedItem(LocationSummary.DPD_GMA_SUBSIDY);
			Assert.assertEquals(value, requiredData.get(1));

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// resetting test data
			locationSummary.subsidyResettingValidationOff(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM),
					rstLocationListData.get(CNLocationList.LOCATION_NAME), requiredData.get(0));
		}
	}

	@Test(description = "165957 - Verify the Location Summary Updates")
	public void verifyLocationSummaryUpdates() {
		final String CASE_NUM = "165957";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
		rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);

		List<String> requiredData = Arrays
				.asList(rstLocationSummaryData.get(CNLocationSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));

		try {

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			Assert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Select Menu, Menu Item and Location
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));

			// Verifying Drop down values of GMA subsidy field
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			Assert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_MULTI_TAX_REPORT));
			Assert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_GMA_SUBSIDY));
			List<String> actualValue = dropDown.getAllItems(LocationSummary.DPD_GMA_SUBSIDY);
			ArrayList<String> expectedValue = new ArrayList<String>();
			expectedValue.add(requiredData.get(0));
			expectedValue.add(requiredData.get(1));
			Assert.assertTrue(actualValue.equals(expectedValue));
			String value = dropDown.getSelectedItem(LocationSummary.DPD_GMA_SUBSIDY);
			Assert.assertEquals(value, requiredData.get(0));
			dropDown.selectItem(LocationSummary.DPD_GMA_SUBSIDY, requiredData.get(1), Constants.TEXT);
			value = dropDown.getSelectedItem(LocationSummary.DPD_GMA_SUBSIDY);
			Assert.assertEquals(value, requiredData.get(1));
			locationSummary.verifyTopOffSubsidy(requiredData);
			locationSummary.verifyRolloverSubsidy(requiredData);

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// resetting test data
			locationSummary.subsidyResettingValidationOff(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM),
					rstLocationListData.get(CNLocationList.LOCATION_NAME), requiredData.get(0));
		}
	}

	@Test(description = "165964 - Verify the Location Summary Updates in Location creation Page")
	public void verifyLocationSummaryUpdatesInLocationCreationPage() {
		final String CASE_NUM = "165964";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
		rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);

		List<String> requiredData = Arrays
				.asList(rstLocationSummaryData.get(CNLocationSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));

		try {

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			Assert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Select Menu, Menu Item and Location
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			foundation.click(LocationList.BTN_CREATE);
			Assert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_MULTI_TAX_REPORT));
			Assert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_GMA_SUBSIDY));
			List<String> actualValue = dropDown.getAllItems(LocationSummary.DPD_GMA_SUBSIDY);
			ArrayList<String> expectedValue = new ArrayList<String>();
			expectedValue.add(requiredData.get(0));
			expectedValue.add(requiredData.get(1));
			Assert.assertTrue(actualValue.equals(expectedValue));

			// Verifying Drop down values of GMA subsidy in Location creation Page
			String value = dropDown.getSelectedItem(LocationSummary.DPD_GMA_SUBSIDY);
			Assert.assertEquals(value, requiredData.get(0));
			dropDown.selectItem(LocationSummary.DPD_GMA_SUBSIDY, requiredData.get(1), Constants.TEXT);
			value = dropDown.getSelectedItem(LocationSummary.DPD_GMA_SUBSIDY);
			Assert.assertEquals(value, requiredData.get(1));
			locationSummary.verifyTopOffSubsidy(requiredData);
			locationSummary.verifyRolloverSubsidy(requiredData);

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// resetting test data
			locationSummary.subsidyResettingValidationOff(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM),
					rstLocationListData.get(CNLocationList.LOCATION_NAME), requiredData.get(0));
		}
	}

	@Test(description = "166057 - Verify the red minus sign and blue plus sign for both the Top off subsidy and Roll Over Subsidy")
	public void verifySignsOnBothSubsidy() {
		final String CASE_NUM = "166057";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
		rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);

		List<String> requiredData = Arrays
				.asList(rstLocationSummaryData.get(CNLocationSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));

		try {

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			Assert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Select Menu, Menu Item and Location
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));

			// Verifying Add and Delete signs of Top Off Subsidy
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			Assert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_GMA_SUBSIDY));
			String value = dropDown.getSelectedItem(LocationSummary.DPD_GMA_SUBSIDY);
			Assert.assertEquals(value, requiredData.get(1));
			dropDown.selectItem(LocationSummary.DPD_GMA_SUBSIDY, requiredData.get(0), Constants.TEXT);
			locationSummary.verifyTopOffSubsidy(requiredData);
			locationSummary.verifyRolloverSubsidy(requiredData);
			if (checkBox.isChkEnabled(LocationSummary.CHK_TOP_OFF_SUBSIDY))
				checkBox.check(LocationSummary.CHK_TOP_OFF_SUBSIDY);
			Assert.assertTrue(foundation.isDisplayed(LocationSummary.BTN_DELETE_TOP_OFF));
			Assert.assertTrue(foundation.isDisplayed(LocationSummary.BTN_ADD_TOP_OFF));
			foundation.click(LocationSummary.BTN_DELETE_TOP_OFF);
			foundation.waitforElement(LocationList.TXT_SPINNER_ERROR_MSG, Constants.SHORT_TIME);
			locationSummary.verifySignsTopOff();
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			foundation.waitforElement(
					locationList.getlocationElement(rstLocationListData.get(CNLocationList.LOCATION_NAME)),
					Constants.SHORT_TIME);
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);

			// Verifying Add and Delete signs of Roll Over Subsidy
			Assert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_GMA_SUBSIDY));
			value = dropDown.getSelectedItem(LocationSummary.DPD_GMA_SUBSIDY);
			Assert.assertEquals(value, requiredData.get(1));
			dropDown.selectItem(LocationSummary.DPD_GMA_SUBSIDY, requiredData.get(0), Constants.TEXT);
			locationSummary.verifyTopOffSubsidy(requiredData);
			locationSummary.verifyRolloverSubsidy(requiredData);
			foundation.click(LocationSummary.CHK_ROLL_OVER_SUBSIDY);
			Assert.assertTrue(foundation.isDisplayed(LocationSummary.BTN_DELETE_ROLL_OVER));
			Assert.assertTrue(foundation.isDisplayed(LocationSummary.BTN_ADD_ROLL_OVER));
			foundation.click(LocationSummary.BTN_DELETE_ROLL_OVER);
			foundation.waitforElement(LocationList.TXT_SPINNER_ERROR_MSG, Constants.SHORT_TIME);
			locationSummary.verifySignsRollOver();
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// resetting test data
			locationSummary.subsidyResettingValidationOff(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM),
					rstLocationListData.get(CNLocationList.LOCATION_NAME), requiredData.get(1));
		}
	}

	@Test(description = "165959 - Verify the Rollover Subsidy setting up for Subsidy groups in Location Summary Page")
	public void verifyRollOverSubsidy() {
		final String CASE_NUM = "165959";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
		rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);

		List<String> requiredData = Arrays
				.asList(rstLocationSummaryData.get(CNLocationSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		String currentDate = dateAndTime.getDateAndTime(Constants.REGEX_MM_DD_YYYY, Constants.TIME_ZONE_INDIA);
		String recurrence = new String();

		try {

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			Assert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Select Menu, Menu Item and Location
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));

			// Verifying GMA subsidy field
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			Assert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_GMA_SUBSIDY));
			String value = dropDown.getSelectedItem(LocationSummary.DPD_GMA_SUBSIDY);
			Assert.assertEquals(value, requiredData.get(0));
			locationSummary.verifyTopOffSubsidy(requiredData);
			locationSummary.verifyRolloverSubsidy(requiredData);

			// Verifying Default Radio Buttons of both subsidy
			if (checkBox.isChkEnabled(LocationSummary.CHK_TOP_OFF_SUBSIDY))
				checkBox.check(LocationSummary.CHK_TOP_OFF_SUBSIDY);
			Assert.assertFalse(checkBox.isChkEnabled(LocationSummary.CHK_ROLL_OVER_SUBSIDY));
			checkBox.unCheck(LocationSummary.CHK_TOP_OFF_SUBSIDY);
			if (checkBox.isChkEnabled(LocationSummary.CHK_ROLL_OVER_SUBSIDY))
				checkBox.check(LocationSummary.CHK_ROLL_OVER_SUBSIDY);
			Assert.assertFalse(checkBox.isChkEnabled(LocationSummary.CHK_TOP_OFF_SUBSIDY));
			checkBox.unCheck(LocationSummary.CHK_ROLL_OVER_SUBSIDY);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			Assert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_GMA_SUBSIDY));
			checkBox.check(LocationSummary.CHK_TOP_OFF_SUBSIDY);

			// Verifying Start Date picker of Top Off Subsidy
			foundation.click(LocationSummary.START_DATE_PICKER_TOP_OFF);
			locationSummary.verifyTopOffDateAutoLocation2(currentDate);
			String futureDate = dateAndTime.getFutureDate(Constants.REGEX_MM_DD_YYYY, requiredData.get(10));
			foundation.click(LocationSummary.START_DATE_PICKER_TOP_OFF);
			locationSummary.verifyTopOffFutureDateAutoLocation2(futureDate);

			// Verifying Recurrence field of Top Off Subsidy
			dropDown.selectItem(LocationSummary.DPD_TOP_OFF_RECURRENCE, requiredData.get(7), Constants.TEXT);
			recurrence = dropDown.getSelectedItem(LocationSummary.DPD_TOP_OFF_RECURRENCE);
			Assert.assertEquals(recurrence, requiredData.get(7));
			dropDown.selectItem(LocationSummary.DPD_TOP_OFF_RECURRENCE, requiredData.get(8), Constants.TEXT);
			recurrence = dropDown.getSelectedItem(LocationSummary.DPD_TOP_OFF_RECURRENCE);
			Assert.assertEquals(recurrence, requiredData.get(8));
			dropDown.selectItem(LocationSummary.DPD_TOP_OFF_RECURRENCE, requiredData.get(9), Constants.TEXT);
			recurrence = dropDown.getSelectedItem(LocationSummary.DPD_TOP_OFF_RECURRENCE);
			Assert.assertEquals(recurrence, requiredData.get(9));
			checkBox.unCheck(LocationSummary.CHK_TOP_OFF_SUBSIDY);

			// Verifying Recurrence field of Roll Over Subsidy
			checkBox.check(LocationSummary.CHK_ROLL_OVER_SUBSIDY);
			dropDown.selectItem(LocationSummary.DPD_ROLL_OVER_RECURRENCE, requiredData.get(7), Constants.TEXT);
			recurrence = dropDown.getSelectedItem(LocationSummary.DPD_ROLL_OVER_RECURRENCE);
			Assert.assertEquals(recurrence, requiredData.get(7));
			dropDown.selectItem(LocationSummary.DPD_ROLL_OVER_RECURRENCE, requiredData.get(8), Constants.TEXT);
			recurrence = dropDown.getSelectedItem(LocationSummary.DPD_ROLL_OVER_RECURRENCE);
			Assert.assertEquals(recurrence, requiredData.get(8));
			dropDown.selectItem(LocationSummary.DPD_ROLL_OVER_RECURRENCE, requiredData.get(9), Constants.TEXT);
			recurrence = dropDown.getSelectedItem(LocationSummary.DPD_ROLL_OVER_RECURRENCE);
			Assert.assertEquals(recurrence, requiredData.get(9));

			// Verifying Group Name duplication in both subsidy
			textBox.enterText(LocationSummary.TXT_TOP_OFF_GROUP_NAME, requiredData.get(11));
			Assert.assertTrue(foundation.isDisplayed(LocationSummary.TOP_OFF_WARNING_MSG));
			textBox.enterText(LocationSummary.TXT_TOP_OFF_GROUP_NAME, requiredData.get(12));
			textBox.enterText(LocationSummary.TXT_ROLL_OVER_GROUP_NAME, requiredData.get(12));
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_ERROR_MSG, Constants.SHORT_TIME);
			Assert.assertTrue(foundation.isDisplayed(LocationSummary.ROLL_OVER_WARNING_MSG));
			textBox.enterText(LocationSummary.TXT_ROLL_OVER_GROUP_NAME, requiredData.get(13));
			foundation.click(LocationSummary.BTN_SAVE);
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			Assert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_GMA_SUBSIDY));
			checkBox.unCheck(LocationSummary.CHK_ROLL_OVER_SUBSIDY);
			checkBox.check(LocationSummary.CHK_TOP_OFF_SUBSIDY);

			// Validating Amount field in Top Off Subsidy
			textBox.enterText(LocationSummary.TXT_TOP_OFF_AMOUNT, requiredData.get(14));
			assertTrue(textBox.getTextFromInput(LocationSummary.TXT_TOP_OFF_AMOUNT_VALUE).equals("0"));
			textBox.enterText(LocationSummary.TXT_TOP_OFF_AMOUNT, requiredData.get(15));
			textBox.enterText(LocationSummary.TXT_ROLL_OVER_GROUP_NAME, requiredData.get(13));
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_ERROR_MSG, Constants.SHORT_TIME);
			Assert.assertTrue(foundation.isDisplayed(LocationSummary.CHK_TOP_OFF_ERROR));
			textBox.enterText(LocationSummary.TXT_TOP_OFF_AMOUNT, requiredData.get(16));
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			Assert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_GMA_SUBSIDY));

			// Validating Delete subsidy in Top Off subsidy
			Assert.assertTrue(foundation.isDisplayed(LocationSummary.BTN_DELETE_TOP_OFF));
			foundation.click(LocationSummary.BTN_DELETE_TOP_OFF);
			foundation.waitforElement(LocationList.TXT_SPINNER_ERROR_MSG, Constants.SHORT_TIME);
			checkBox.unCheck(LocationSummary.CHK_TOP_OFF_SUBSIDY);
			checkBox.check(LocationSummary.CHK_ROLL_OVER_SUBSIDY);

			// Validating Add and Delete subsidy in Roll Over subsidy
			Assert.assertTrue(foundation.isDisplayed(LocationSummary.BTN_DELETE_ROLL_OVER));
			foundation.click(LocationSummary.BTN_DELETE_ROLL_OVER);
			foundation.waitforElement(LocationList.TXT_SPINNER_ERROR_MSG, Constants.SHORT_TIME);
			Assert.assertTrue(foundation.isDisplayed(LocationSummary.BTN_ADD_ROLL_OVER));
			locationSummary.verifySignsRollOver();
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// resetting test data
			locationSummary.subsidyResettingValidationOff(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM),
					rstLocationListData.get(CNLocationList.LOCATION_NAME), requiredData.get(0));
		}
	}

	@Test(description = "165966 - Verify the Rollover Subsidy setting up for Subsidy groups in Location Creation Page")
	public void verifyRollOverSubsidyInLocationCreationPage() {
		final String CASE_NUM = "165966";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
		rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);

		List<String> requiredData = Arrays
				.asList(rstLocationSummaryData.get(CNLocationSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		String currentDate = dateAndTime.getDateAndTime(Constants.REGEX_MM_DD_YYYY, Constants.TIME_ZONE_INDIA);

		try {

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			Assert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Select Menu, Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			foundation.click(LocationList.BTN_CREATE);

			// Verifying GMA subsidy in Location Creation Page
			dropDown.selectItem(CreateLocation.DPD_TIME_ZONE, requiredData.get(17), Constants.VALUE);
			dropDown.selectItem(CreateLocation.DPD_TYPE, requiredData.get(18), Constants.VALUE);
			Assert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_MULTI_TAX_REPORT));
			Assert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_GMA_SUBSIDY));
			String value = dropDown.getSelectedItem(LocationSummary.DPD_GMA_SUBSIDY);
			Assert.assertEquals(value, requiredData.get(1));
			dropDown.selectItem(LocationSummary.DPD_GMA_SUBSIDY, requiredData.get(0), Constants.TEXT);
			locationSummary.verifyRolloverSubsidy(requiredData);
			if (checkBox.isChkEnabled(LocationSummary.CHK_ROLL_OVER_SUBSIDY))
				checkBox.check(LocationSummary.CHK_ROLL_OVER_SUBSIDY);
			Assert.assertFalse(checkBox.isChkEnabled(LocationSummary.CHK_TOP_OFF_SUBSIDY));

			// Verifying Date picker of Roll Over Subsidy
			foundation.click(CreateLocation.DATE_PICKER_ROLL_OVER);
			locationSummary.verifyRollOverDateCreateLocation(currentDate);
			String futureDate = dateAndTime.getFutureDate(Constants.REGEX_MM_DD_YYYY, requiredData.get(10));
			foundation.click(CreateLocation.DATE_PICKER_ROLL_OVER);
			locationSummary.verifyRollOverFutureDateCreateLocation(futureDate);

			// Verifying Recurrence field value of Roll over subsidy
			List<String> actualValue = dropDown.getAllItems(LocationSummary.DPD_ROLL_OVER_RECURRENCE);
			ArrayList<String> expectedValue = new ArrayList<String>();
			expectedValue.add(requiredData.get(7));
			expectedValue.add(requiredData.get(8));
			expectedValue.add(requiredData.get(9));
			Assert.assertTrue(actualValue.equals(expectedValue));
			foundation.click(LocationSummary.CHK_ROLL_OVER_SUBSIDY);
			foundation.click(LocationSummary.CHK_ROLL_OVER_SUBSIDY);

			// Verifying Group names of Roll over Subsidy
			textBox.enterText(LocationSummary.TXT_ROLL_OVER_GROUP_NAME, requiredData.get(11));
			Assert.assertTrue(foundation.isDisplayed(LocationSummary.ROLL_OVER_WARNING_MSG));
			textBox.enterText(LocationSummary.TXT_TOP_OFF_GROUP_NAME, requiredData.get(12));
			textBox.enterText(LocationSummary.TXT_ROLL_OVER_GROUP_NAME, requiredData.get(12));
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_ERROR_MSG, Constants.SHORT_TIME);
			Assert.assertTrue(foundation.isDisplayed(LocationSummary.ROLL_OVER_WARNING_MSG));
			textBox.enterText(LocationSummary.TXT_ROLL_OVER_GROUP_NAME, requiredData.get(13));

			// Validating amount field of Roll Over subsidy
			textBox.enterText(LocationSummary.TXT_ROLL_OVER_AMOUNT, requiredData.get(14));
			assertTrue(textBox.getTextFromInput(LocationSummary.TXT_ROLL_OVER_AMOUNT_VALUE).equals("0"));
			textBox.enterText(LocationSummary.TXT_ROLL_OVER_AMOUNT, requiredData.get(15));
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_ERROR_MSG, Constants.SHORT_TIME);
			Assert.assertTrue(foundation.isDisplayed(LocationSummary.CHK_ROLL_OVER_ERROR));
			textBox.enterText(LocationSummary.TXT_ROLL_OVER_AMOUNT, requiredData.get(16));
			foundation.click(LocationSummary.BTN_SAVE);
			Assert.assertTrue(foundation.isDisplayed(LocationSummary.CHK_ROLL_OVER_ERROR));
			foundation.click(LocationSummary.CHK_ROLL_OVER_SUBSIDY);
			Assert.assertTrue(foundation.isDisplayed(LocationSummary.BTN_DELETE_ROLL_OVER));

			// Verifying Add and Delete signs of Subsidy in Roll Over subsidy
			foundation.click(LocationSummary.BTN_DELETE_ROLL_OVER);
			foundation.waitforElement(LocationList.TXT_SPINNER_ERROR_MSG, Constants.SHORT_TIME);
			Assert.assertTrue(foundation.isDisplayed(LocationSummary.BTN_ADD_ROLL_OVER));
			locationSummary.verifySignsRollOver();
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "165965 - Verify the Top Off Subsidy setting up for Subsidy groups in Location Creation Page")
	public void verifyTopOffSubsidyInLocationCreationPage() {
		final String CASE_NUM = "165965";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
		rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);

		List<String> requiredData = Arrays
				.asList(rstLocationSummaryData.get(CNLocationSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		String currentDate = dateAndTime.getDateAndTime(Constants.REGEX_MM_DD_YYYY, Constants.TIME_ZONE_INDIA);

		try {

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			Assert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Select Menu, Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			foundation.click(LocationList.BTN_CREATE);

			// Verifying GMA subsidy fields in Location Creation Page
			dropDown.selectItem(CreateLocation.DPD_TIME_ZONE, requiredData.get(17), Constants.VALUE);
			dropDown.selectItem(CreateLocation.DPD_TYPE, requiredData.get(18), Constants.VALUE);
			Assert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_MULTI_TAX_REPORT));
			Assert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_GMA_SUBSIDY));
			String value = dropDown.getSelectedItem(LocationSummary.DPD_GMA_SUBSIDY);
			Assert.assertEquals(value, requiredData.get(1));
			dropDown.selectItem(LocationSummary.DPD_GMA_SUBSIDY, requiredData.get(0), Constants.TEXT);
			locationSummary.verifyTopOffSubsidy(requiredData);
			locationSummary.verifyRolloverSubsidy(requiredData);
			if (checkBox.isChkEnabled(LocationSummary.CHK_TOP_OFF_SUBSIDY))
				checkBox.check(LocationSummary.CHK_TOP_OFF_SUBSIDY);
			Assert.assertFalse(checkBox.isChkEnabled(LocationSummary.CHK_ROLL_OVER_SUBSIDY));
			checkBox.unCheck(LocationSummary.CHK_TOP_OFF_SUBSIDY);
			if (checkBox.isChkEnabled(LocationSummary.CHK_ROLL_OVER_SUBSIDY))
				checkBox.check(LocationSummary.CHK_ROLL_OVER_SUBSIDY);
			Assert.assertFalse(checkBox.isChkEnabled(LocationSummary.CHK_TOP_OFF_SUBSIDY));
			checkBox.unCheck(LocationSummary.CHK_ROLL_OVER_SUBSIDY);
			checkBox.check(LocationSummary.CHK_TOP_OFF_SUBSIDY);

			// Verifying Date Picker of Top Off
			foundation.click(CreateLocation.DATE_PICKER_TOP_OFF);
			locationSummary.verifyTopOffDateAutoLocation2(currentDate);
			String futureDate = dateAndTime.getFutureDate(Constants.REGEX_MM_DD_YYYY, requiredData.get(10));
			foundation.click(CreateLocation.DATE_PICKER_TOP_OFF);
			locationSummary.verifyTopOffFutureDateAutoLocation2(futureDate);

			// Validating Recurrence field of Top Off and Roll Over
			List<String> actualValue = dropDown.getAllItems(LocationSummary.DPD_TOP_OFF_RECURRENCE);
			ArrayList<String> expectedValue = new ArrayList<String>();
			expectedValue.add(requiredData.get(7));
			expectedValue.add(requiredData.get(8));
			expectedValue.add(requiredData.get(9));
			Assert.assertTrue(actualValue.equals(expectedValue));
			checkBox.unCheck(LocationSummary.CHK_TOP_OFF_SUBSIDY);
			checkBox.check(LocationSummary.CHK_ROLL_OVER_SUBSIDY);
			actualValue = dropDown.getAllItems(LocationSummary.DPD_ROLL_OVER_RECURRENCE);
			Assert.assertTrue(actualValue.equals(expectedValue));

			// Validating Group Names of both subsidy
			textBox.enterText(LocationSummary.TXT_TOP_OFF_GROUP_NAME, requiredData.get(11));
			Assert.assertTrue(foundation.isDisplayed(LocationSummary.TOP_OFF_WARNING_MSG));
			textBox.enterText(LocationSummary.TXT_TOP_OFF_GROUP_NAME, requiredData.get(12));
			textBox.enterText(LocationSummary.TXT_ROLL_OVER_GROUP_NAME, requiredData.get(12));
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_ERROR_MSG, Constants.SHORT_TIME);
			Assert.assertTrue(foundation.isDisplayed(LocationSummary.ROLL_OVER_WARNING_MSG));
			textBox.enterText(LocationSummary.TXT_ROLL_OVER_GROUP_NAME, requiredData.get(13));
			textBox.enterText(LocationSummary.TXT_TOP_OFF_AMOUNT, requiredData.get(14));

			// Validating Amount field of Top off
			assertTrue(textBox.getTextFromInput(LocationSummary.TXT_TOP_OFF_AMOUNT_VALUE).equals("0"));
			textBox.enterText(LocationSummary.TXT_TOP_OFF_AMOUNT, requiredData.get(15));
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_ERROR_MSG, Constants.SHORT_TIME);
			Assert.assertTrue(foundation.isDisplayed(LocationSummary.CHK_TOP_OFF_ERROR));
			textBox.enterText(LocationSummary.TXT_TOP_OFF_AMOUNT, requiredData.get(16));
			foundation.click(LocationSummary.BTN_SAVE);
			Assert.assertTrue(foundation.isDisplayed(LocationSummary.CHK_TOP_OFF_ERROR));

			// Verifying Add and Delete signs of Top off subsidy
			checkBox.check(LocationSummary.CHK_TOP_OFF_SUBSIDY);
			Assert.assertTrue(foundation.isDisplayed(LocationSummary.BTN_DELETE_TOP_OFF));
			foundation.click(LocationSummary.BTN_DELETE_TOP_OFF);
			foundation.waitforElement(LocationList.TXT_SPINNER_ERROR_MSG, Constants.SHORT_TIME);
			Assert.assertTrue(foundation.isDisplayed(LocationSummary.BTN_ADD_TOP_OFF));
			locationSummary.verifySignsTopOff();
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "166053 - Verify the Start Date field for both Top off subsidy and Rollover subsidy")
	public void verifyStartDateFieldforBothSubsidy() {
		final String CASE_NUM = "166053";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
		rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);

		List<String> requiredData = Arrays
				.asList(rstLocationSummaryData.get(CNLocationSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		String currentDate = dateAndTime.getDateAndTime(Constants.REGEX_MM_DD_YYYY, Constants.TIME_ZONE_INDIA);

		try {

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			Assert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Select Menu, Menu Item and Location
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));

			// Verifying GMA subsidy fields
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			Assert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_GMA_SUBSIDY));
			String value = dropDown.getSelectedItem(LocationSummary.DPD_GMA_SUBSIDY);
			Assert.assertEquals(value, requiredData.get(1));
			dropDown.selectItem(LocationSummary.DPD_GMA_SUBSIDY, requiredData.get(0), Constants.TEXT);
			locationSummary.verifyTopOffSubsidy(requiredData);
			locationSummary.verifyRolloverSubsidy(requiredData);
			if (checkBox.isChecked(LocationSummary.CHK_TOP_OFF_SUBSIDY)) {
				checkBox.unCheck(LocationSummary.CHK_TOP_OFF_SUBSIDY);
				foundation.threadWait(Constants.ONE_SECOND);
			} else if (checkBox.isChecked(LocationSummary.CHK_ROLL_OVER_SUBSIDY)) {
				checkBox.unCheck(LocationSummary.CHK_ROLL_OVER_SUBSIDY);
				foundation.threadWait(Constants.ONE_SECOND);
			}
			checkBox.check(LocationSummary.CHK_TOP_OFF_SUBSIDY);

			// Verifying Date picker of Top Off
			foundation.click(LocationSummary.START_DATE_PICKER_TOP_OFF);
			locationSummary.verifyTopOffDateAutoLocation1(currentDate);
			String futureDate = dateAndTime.getFutureDate(Constants.REGEX_MM_DD_YYYY, requiredData.get(7));
			foundation.click(LocationSummary.START_DATE_PICKER_TOP_OFF);
			locationSummary.verifyTopOffFutureDateAutoLocation1(futureDate);
			checkBox.unCheck(LocationSummary.CHK_TOP_OFF_SUBSIDY);
			checkBox.check(LocationSummary.CHK_ROLL_OVER_SUBSIDY);

			// Verifying Date picker of Roll Over
			foundation.threadWait(Constants.ONE_SECOND);
			foundation.click(LocationSummary.START_DATE_PICKER_ROLL_OVER);
			locationSummary.verifyRollOverDateLocation1(currentDate);
			foundation.click(LocationSummary.START_DATE_PICKER_ROLL_OVER);
			locationSummary.verifyRollOverFutureDateLocation1(futureDate);

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// resetting test data
			locationSummary.subsidyResettingValidationOff(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM),
					rstLocationListData.get(CNLocationList.LOCATION_NAME), requiredData.get(1));
		}
	}

	@Test(description = "166055 - Verify the 'Group Name' field for both the Top off subsidy and Rollover subsidy")
	public void verifyGroupNameInBothSubsidy() {
		final String CASE_NUM = "166055";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
		rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);

		List<String> requiredData = Arrays
				.asList(rstLocationSummaryData.get(CNLocationSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));

		try {

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			Assert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Select Menu, Menu Item and Location
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));

			// Verifying GMA subsidy fields
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			Assert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_GMA_SUBSIDY));
			String value = dropDown.getSelectedItem(LocationSummary.DPD_GMA_SUBSIDY);
			Assert.assertEquals(value, requiredData.get(1));
			dropDown.selectItem(LocationSummary.DPD_GMA_SUBSIDY, requiredData.get(0), Constants.TEXT);
			locationSummary.verifyTopOffSubsidy(requiredData);
			locationSummary.verifyRolloverSubsidy(requiredData);
			checkBox.check(LocationSummary.CHK_TOP_OFF_SUBSIDY);

			// Verifying Group name field in both subsidy
			textBox.enterText(LocationSummary.TXT_TOP_OFF_GROUP_NAME, requiredData.get(7));
			Assert.assertTrue(foundation.isDisplayed(LocationSummary.TOP_OFF_WARNING_MSG));
			checkBox.unCheck(LocationSummary.CHK_TOP_OFF_SUBSIDY);
			checkBox.check(LocationSummary.CHK_ROLL_OVER_SUBSIDY);
			textBox.enterText(LocationSummary.TXT_ROLL_OVER_GROUP_NAME, requiredData.get(7));
			Assert.assertTrue(foundation.isDisplayed(LocationSummary.ROLL_OVER_WARNING_MSG));

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// resetting test data
			locationSummary.subsidyResettingValidationOff(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM),
					rstLocationListData.get(CNLocationList.LOCATION_NAME), requiredData.get(1));
		}
	}
}
