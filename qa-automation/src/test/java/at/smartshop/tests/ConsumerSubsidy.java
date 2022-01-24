package at.smartshop.tests;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import at.framework.generic.Numbers;
import at.framework.generic.Strings;

import at.framework.database.mssql.Queries;
import at.framework.database.mssql.ResultSets;
import at.framework.files.Excel;
import at.framework.files.PropertyFile;
import at.framework.generic.CustomisedAssert;
import at.framework.generic.DateAndTime;
import at.framework.ui.CheckBox;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.Table;
import at.framework.ui.TextBox;
import at.smartshop.database.columns.CNConsumerSearch;
import at.smartshop.database.columns.CNConsumerSummary;
import at.smartshop.database.columns.CNGmaUser;
import at.smartshop.database.columns.CNLoadProduct;
import at.smartshop.database.columns.CNLocationList;
import at.smartshop.database.columns.CNLocationSummary;
import at.smartshop.database.columns.CNNationalAccounts;
import at.smartshop.database.columns.CNNavigationMenu;
import at.smartshop.database.columns.CNProductSummary;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.pages.ConsumerSearch;
import at.smartshop.pages.ConsumerSummary;
import at.smartshop.pages.CreateLocation;
import at.smartshop.pages.LocationList;
import at.smartshop.pages.LocationSummary;
import at.smartshop.pages.NavigationBar;
import at.smartshop.sos.pages.LoadGMA;
import at.smartshop.sos.pages.SOSHome;

@Listeners(at.framework.reportsetup.Listeners.class)
public class ConsumerSubsidy extends TestInfra {

	private ResultSets dataBase = new ResultSets();
	private NavigationBar navigationBar = new NavigationBar();
	private PropertyFile propertyFile = new PropertyFile();
	private Dropdown dropDown = new Dropdown();
	private TextBox textBox = new TextBox();
	private DateAndTime dateAndTime = new DateAndTime();
	private Foundation foundation = new Foundation();
	private ConsumerSearch consumerSearch = new ConsumerSearch();
	private LocationSummary locationSummary = new LocationSummary();
	private LocationList locationList = new LocationList();
	private CheckBox checkBox = new CheckBox();
	private Table table = new Table();
	private ConsumerSummary consumerSummar= new ConsumerSummary();
	private SOSHome sosHome = new SOSHome();
	private Strings strings = new Strings();
	private Numbers numbers = new Numbers();
	private Excel excel = new Excel();
	private LoadGMA loadGma = new LoadGMA();
	
	private ConsumerSummary consumerSummary = new ConsumerSummary();
	private Map<String, String> rstNavigationMenuData;
	private Map<String, String> rstLocationListData;
	private Map<String, String> rstLocationSummaryData;

	private Map<String, String> rstconsumerSearchData;
	private Map<String,String>  rstConsumerSummaryData;
	private Map<String, String> rstLoadProduct;
	private Map<String, String> rstGmaUser;
	private Map<String, String> rstProductSummaryData;

	int requiredValue = numbers.generateRandomNumber(0, 999999);
	String requiredDatas = strings.getRandomCharacter();
	String requiredString = (requiredDatas + Constants.DELIMITER_HASH + requiredDatas + Constants.DELIMITER_HASH
			+ requiredDatas + "1234" + Constants.DELIMITER_HASH + "25" + Constants.DELIMITER_HASH + requiredDatas
			+ "@gmail.com" + Constants.DELIMITER_HASH + String.valueOf(requiredValue) + Constants.DELIMITER_HASH
			+ requiredDatas + Constants.DELIMITER_HASH + String.valueOf(requiredValue) + Constants.DELIMITER_HASH
			+ requiredDatas + "group");


	@Test(description = "166048 - verify the GMA subsidy under the location summary page"
			+ "166049 - verify the GMA subsidy field when its set to 'Yes' under the location summary page"
			+ "166050 - Verify the selection of defaults for the GMA subsidy"
			+ "166051 - Verify the whether the default radio buttons are mandatory fields."
			+ "166052 - Verify the duplicate names for both the Top of subsidy and Rollover subsidy"
			+ "166053 - Verify the Start Date field for both Top off subsidy and Rollover subsidy"
			+ "166054 - Verify the 'Recurrence' field for both the Top of subsidy and Rollover subsidy"
			+ "166055 - Verify the 'Group Name' field for both the Top off subsidy and Rollover subsidy")
	public void verifyGmaSubsidy() {
		final String CASE_NUM = "166050";

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

			// Verifying the selection of defaults for the Both GMA subsidy
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			Assert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_MULTI_TAX_REPORT));
			Assert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_GMA_SUBSIDY));
			List<String> values = dropDown.getAllItems(LocationSummary.DPD_GMA_SUBSIDY);
			List<String> expectedValues = new ArrayList<String>();
			expectedValues.add(requiredData.get(1));
			expectedValues.add(requiredData.get(0));
			Assert.assertTrue(values.equals(expectedValues));
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

			// Verifying default radio buttons are mandatory fields for both subsidy
			if (checkBox.isChkEnabled(LocationSummary.CHK_TOP_OFF_SUBSIDY)) {
				if (checkBox.isChecked(LocationSummary.CHK_TOP_OFF_SUBSIDY))
					checkBox.unCheck(LocationSummary.CHK_TOP_OFF_SUBSIDY);
			} else if (checkBox.isChkEnabled(LocationSummary.CHK_ROLL_OVER_SUBSIDY)) {
				if (checkBox.isChecked(LocationSummary.CHK_ROLL_OVER_SUBSIDY))
					checkBox.unCheck(LocationSummary.CHK_ROLL_OVER_SUBSIDY);
			}
			locationSummary.enterGroupNames(requiredData.get(7), requiredData.get(8), requiredData.get(9));

			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			Assert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_GMA_SUBSIDY));

			// Verifying the duplicate names for both subsidy
			textBox.enterText(LocationSummary.TXT_TOP_OFF_GROUP_NAME, requiredData.get(10));
			textBox.enterText(LocationSummary.TXT_ROLL_OVER_GROUP_NAME, requiredData.get(10));
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_ERROR_MSG, Constants.SHORT_TIME);
			Assert.assertTrue(foundation.isDisplayed(LocationSummary.ROLL_OVER_WARNING_MSG));
			textBox.enterText(LocationSummary.TXT_ROLL_OVER_GROUP_NAME, requiredData.get(11));
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			Assert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_GMA_SUBSIDY));
			checkBox.check(LocationSummary.CHK_TOP_OFF_SUBSIDY);

			// Verifying Date picker of Top Off
			foundation.click(LocationSummary.START_DATE_PICKER_TOP_OFF);
			locationSummary.verifyTopOffDateAutoLocation1(currentDate);
			String futureDate = dateAndTime.getFutureDate(Constants.REGEX_MM_DD_YYYY, requiredData.get(12));
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
			checkBox.unCheck(LocationSummary.CHK_ROLL_OVER_SUBSIDY);
			checkBox.check(LocationSummary.CHK_TOP_OFF_SUBSIDY);

			// Validating Recurrence field of Top Off subsidy
			dropDown.selectItem(LocationSummary.DPD_TOP_OFF_RECURRENCE, requiredData.get(13), Constants.TEXT);
			recurrence = dropDown.getSelectedItem(LocationSummary.DPD_TOP_OFF_RECURRENCE);
			Assert.assertEquals(recurrence, requiredData.get(13));
			dropDown.selectItem(LocationSummary.DPD_TOP_OFF_RECURRENCE, requiredData.get(14), Constants.TEXT);
			recurrence = dropDown.getSelectedItem(LocationSummary.DPD_TOP_OFF_RECURRENCE);
			Assert.assertEquals(recurrence, requiredData.get(14));
			dropDown.selectItem(LocationSummary.DPD_TOP_OFF_RECURRENCE, requiredData.get(15), Constants.TEXT);
			recurrence = dropDown.getSelectedItem(LocationSummary.DPD_TOP_OFF_RECURRENCE);
			Assert.assertEquals(recurrence, requiredData.get(15));
			checkBox.unCheck(LocationSummary.CHK_TOP_OFF_SUBSIDY);

			// Validating Recurrence field of Roll Over subsidy
			checkBox.check(LocationSummary.CHK_ROLL_OVER_SUBSIDY);
			dropDown.selectItem(LocationSummary.DPD_ROLL_OVER_RECURRENCE, requiredData.get(13), Constants.TEXT);
			recurrence = dropDown.getSelectedItem(LocationSummary.DPD_ROLL_OVER_RECURRENCE);
			Assert.assertEquals(recurrence, requiredData.get(13));
			dropDown.selectItem(LocationSummary.DPD_ROLL_OVER_RECURRENCE, requiredData.get(14), Constants.TEXT);
			recurrence = dropDown.getSelectedItem(LocationSummary.DPD_ROLL_OVER_RECURRENCE);
			Assert.assertEquals(recurrence, requiredData.get(14));
			dropDown.selectItem(LocationSummary.DPD_ROLL_OVER_RECURRENCE, requiredData.get(15), Constants.TEXT);
			recurrence = dropDown.getSelectedItem(LocationSummary.DPD_ROLL_OVER_RECURRENCE);
			Assert.assertEquals(recurrence, requiredData.get(15));
			checkBox.unCheck(LocationSummary.CHK_ROLL_OVER_SUBSIDY);

			// Verifying Group name field in both subsidy
			checkBox.check(LocationSummary.CHK_TOP_OFF_SUBSIDY);
			textBox.enterText(LocationSummary.TXT_TOP_OFF_GROUP_NAME, requiredData.get(16));
			Assert.assertTrue(foundation.isDisplayed(LocationSummary.TOP_OFF_WARNING_MSG));
			checkBox.unCheck(LocationSummary.CHK_TOP_OFF_SUBSIDY);
			checkBox.check(LocationSummary.CHK_ROLL_OVER_SUBSIDY);
			textBox.enterText(LocationSummary.TXT_ROLL_OVER_GROUP_NAME, requiredData.get(16));
			Assert.assertTrue(foundation.isDisplayed(LocationSummary.ROLL_OVER_WARNING_MSG));

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// resetting test data
			locationSummary.subsidyResettingValidationOff(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM),
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

	 @Test(description="165971-ADM>Subsidy Balance>Adjustment")	
	   public void SubsidyBalanceAdjustment() {
	     final String CASE_NUM="165971";
	        
	       //reading a data from databae
	        rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstconsumerSearchData = dataBase.getConsumerSearchData(Queries.CONSUMER_SEARCH, CASE_NUM);
	        rstConsumerSummaryData=dataBase.getConsumerSummaryData(Queries.CONSUMER_SUMMARY, CASE_NUM);
			
			List<String> values = Arrays
					.asList(rstConsumerSummaryData.get(CNConsumerSummary.ADJUST_BALANCE).split(Constants.DELIMITER_TILD));
			
		   try {
			   browser.navigateURL(
						propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
				login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
						propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
				CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));
				navigationBar.selectOrganization(
						propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
				
				//select menu and menuitems and verifying increment in subsidy 
				consumerSearch.Incrementsubsidy(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM), rstconsumerSearchData.get(CNConsumerSearch.LOCATION));
	            foundation.waitforElement(ConsumerSummary.TXT_ADJUST_BALANCE, Constants.SHORT_TIME);
	            textBox.enterText(ConsumerSummary.TXT_ADJUST_BALANCE,values.get(0));
	            dropDown.selectItem(ConsumerSummary.DPD_REASON,rstConsumerSummaryData.get(CNConsumerSummary.REASON), Constants.TEXT);
	            CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSummary.REF_EFT));
	            foundation.click(ConsumerSummary.REF_EFT);
	            List<String> datas = dropDown.getAllItems(ConsumerSummary.REF_EFT);
				ArrayList<String> expectedValues = new ArrayList<String>();
				expectedValues.add(values.get(2));
				expectedValues.add(values.get(3));
				CustomisedAssert.assertTrue(datas.equals(expectedValues));
	            foundation.waitforElement(ConsumerSummary.BTN_REASON_SAVE, Constants.SHORT_TIME);
	            foundation.click(ConsumerSummary.BTN_REASON_SAVE);
	            
	            //verify the decrement in subsidy   
	            CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSummary.LBL_CONSUMER_SUMMARY));
	            foundation.click(ConsumerSummary.BTN_ADJUST);
	            textBox.enterText(ConsumerSummary.TXT_ADJUST_BALANCE,values.get(1));
	            dropDown.selectItem(ConsumerSummary.DPD_REASON,rstConsumerSummaryData.get(CNConsumerSummary.REASON), Constants.TEXT);
	            CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSummary.REF_EFT));
	            foundation.click(ConsumerSummary.REF_EFT);
	            foundation.waitforElement(ConsumerSummary.BTN_REASON_SAVE, Constants.SHORT_TIME);
	            foundation.click(ConsumerSummary.BTN_REASON_SAVE);
	            
	            
	            //verifying cancel button
	            foundation.click(ConsumerSummary.BTN_ADJUST);
	            foundation.waitforElement(ConsumerSummary.TXT_ADJUST_BALANCE, Constants.SHORT_TIME);
	            textBox.enterText(ConsumerSummary.TXT_ADJUST_BALANCE,values.get(0));
	            dropDown.selectItem(ConsumerSummary.DPD_REASON,rstConsumerSummaryData.get(CNConsumerSummary.REASON), Constants.TEXT);
	            CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSummary.REF_EFT));
		        foundation.click(ConsumerSummary.BTN_REASON_CANCEL);
		        
		   }	    	
		
	       catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}}
	 
	   @Test(description="165972 Verify subsidy balance adjustments in ADM Consumer summary page as operator role")
	   public void SubsidyBalanceIncrement() {
		   final String CASE_NUM="165972";
		   
		   rstNavigationMenuData  = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		   rstconsumerSearchData  = dataBase.getConsumerSearchData(Queries.CONSUMER_SEARCH, CASE_NUM);
		   rstConsumerSummaryData = dataBase.getConsumerSummaryData(Queries.CONSUMER_SUMMARY, CASE_NUM);
		   
		   List<String> values = Arrays
					.asList(rstConsumerSummaryData.get(CNConsumerSummary.ADJUST_BALANCE).split(Constants.DELIMITER_TILD));
			
		   try {
			   browser.navigateURL(
						propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
				login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
						propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
				CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));
				navigationBar.selectOrganization(
						propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
				
				//verify increment in subsidy balance
				consumerSearch.Incrementsubsidy(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM), rstconsumerSearchData.get(CNConsumerSearch.LOCATION));
	            foundation.waitforElement(ConsumerSummary.LBL_POPUP_ADJUST_BALANCE, Constants.SHORT_TIME);
	            textBox.enterText(ConsumerSummary.TXT_ADJUST_BALANCE,values.get(0));
	            dropDown.selectItem(ConsumerSummary.DPD_REASON,rstConsumerSummaryData.get(CNConsumerSummary.REASON), Constants.TEXT);
	            CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSummary.REF_EFT));
	            foundation.click(ConsumerSummary.REF_EFT);
	            foundation.waitforElement(ConsumerSummary.BTN_REASON_SAVE, Constants.SHORT_TIME);
	            foundation.click(ConsumerSummary.BTN_REASON_SAVE);
	            
	            //verify the decrement in subsidy balance  
	            CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSummary.LBL_CONSUMER_SUMMARY));
	            foundation.click(ConsumerSummary.BTN_ADJUST);
	            foundation.waitforElement(ConsumerSummary.LBL_POPUP_ADJUST_BALANCE, Constants.SHORT_TIME);
	            textBox.enterText(ConsumerSummary.TXT_ADJUST_BALANCE,values.get(1));
	            dropDown.selectItem(ConsumerSummary.DPD_REASON,rstConsumerSummaryData.get(CNConsumerSummary.REASON), Constants.TEXT);
	            CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSummary.REF_EFT));
	            foundation.click(ConsumerSummary.REF_EFT);
	            List<String> datas = dropDown.getAllItems(ConsumerSummary.REF_EFT);
				ArrayList<String> expectedValues = new ArrayList<String>();
				expectedValues.add(values.get(2));
				expectedValues.add(values.get(3));
				CustomisedAssert.assertTrue(datas.equals(expectedValues));
	            foundation.waitforElement(ConsumerSummary.BTN_REASON_SAVE, Constants.SHORT_TIME);
	            foundation.click(ConsumerSummary.BTN_REASON_SAVE);
	            foundation.waitforElement(ConsumerSummary.ERROR_MESSAGE, Constants.SHORT_TIME);
	            CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSummary.ERROR_MESSAGE));
	            foundation.click(ConsumerSummary.ERROR_MESSAGE);
	            
	            
	            //verifying cancel button
	            foundation.click(ConsumerSummary.BTN_ADJUST);
	            foundation.waitforElement(ConsumerSummary.TXT_ADJUST_BALANCE, Constants.SHORT_TIME);
	            textBox.enterText(ConsumerSummary.TXT_ADJUST_BALANCE,values.get(0));
	            dropDown.selectItem(ConsumerSummary.DPD_REASON,rstConsumerSummaryData.get(CNConsumerSummary.REASON), Constants.TEXT);
	            CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSummary.BTN_REASON_CANCEL));
		        foundation.click(ConsumerSummary.BTN_REASON_CANCEL);
		       
		   }	    	
		
	       catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}}
	   
	   @Test(description="165949- Verify to view the 'Bulk Assign Subsidy Group' action in the Actions dropdown")	
	    public void BulkAssignSubsidyGroup() {
	    	final String CASE_NUM="165949";
	    	rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstconsumerSearchData = dataBase.getConsumerSearchData(Queries.CONSUMER_SEARCH, CASE_NUM);
			List<String> values = Arrays
					.asList(rstconsumerSearchData.get(CNConsumerSearch.ACTIONS).split(Constants.DELIMITER_TILD));
			
			
			  try {
				   browser.navigateURL(
							propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
					login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
							propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
					CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));
					
					navigationBar.selectOrganization(
							propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
					navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
					foundation.click(ConsumerSearch.CANCEL);
					dropDown.selectItem(ConsumerSearch.DPD_LOCATION, rstconsumerSearchData.get(CNConsumerSearch.LOCATION), Constants.TEXT);
	                foundation.click(ConsumerSearch.BTN_GO);
	                CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSearch.TBL_CONSUMERS));
	                table.selectRow(rstconsumerSearchData.get(CNConsumerSearch.SEARCH));
	                foundation.click(ConsumerSearch.ACTION_BTN);
	                foundation.threadWait(Constants.SHORT_TIME);
	                List<String> datas = foundation.getTextofListElement(consumerSearch.BTN_ACTION);
	    			CustomisedAssert.assertTrue(datas.equals(values));
	    			foundation.click(ConsumerSearch.BULK_SUBSIDY);
	                foundation.click(ConsumerSearch.RSN_CANCEL);	     
	   }
			  catch (Exception exc) {
					TestInfra.failWithScreenShot(exc.toString());
}}
	   @Test(description="165950- Verify to view the more than one location is filtered then the 'Bulk Assign Subsidy Group' option")	
	    public void BulkAssignSubsidyGroupGrey() {
	    	final String CASE_NUM="165950";
	    	rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstconsumerSearchData = dataBase.getConsumerSearchData(Queries.CONSUMER_SEARCH, CASE_NUM);
			 List<String> values = Arrays
						.asList(rstconsumerSearchData.get(CNConsumerSearch.LOCATION).split(Constants.DELIMITER_TILD));
			 List<String> menu = Arrays
						.asList(rstconsumerSearchData.get(CNConsumerSearch.ACTIONS).split(Constants.DELIMITER_TILD));
			  try {
				   browser.navigateURL(
							propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
					login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
							propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
					CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));
					
					navigationBar.selectOrganization(
							propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
					navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
					foundation.click(ConsumerSearch.CANCEL);
					dropDown.selectItem(ConsumerSearch.DPD_LOCATION, values.get(0), Constants.TEXT);
					dropDown.selectItem(ConsumerSearch.DPD_LOCATION, values.get(1), Constants.TEXT);
	                foundation.click(ConsumerSearch.BTN_GO);
	                CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSearch.TBL_CONSUMERS));
	                table.selectRow(rstconsumerSearchData.get(CNConsumerSearch.SEARCH));
	                foundation.click(ConsumerSearch.ACTION_BTN);
	                List<String> datas = foundation.getTextofListElement(consumerSearch.BTN_ACTION);
	    			CustomisedAssert.assertTrue(datas.equals(menu));
	                CustomisedAssert.assertTrue(foundation.isDisabled(consumerSearch.BULK_SUBSIDY));
	                foundation.threadWait(Constants.SHORT_TIME);
	                login.logout();
	       
	   }
			  catch (Throwable exc) {
					TestInfra.failWithScreenShot(exc.toString());
}}
	@Test(description = "166058 - Verify the top off subsidy option")
	public void verifyTopOffSubsidyOption() {
		final String CASE_NUM = "166058";

		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
		rstGmaUser = dataBase.getGmaUserData(Queries.GMA_USER, CASE_NUM);
		rstLoadProduct = dataBase.getLoadProductData(Queries.LOAD_PRODUCT, CASE_NUM);
		rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);
		rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);

		List<String> requiredData = Arrays
				.asList(rstLocationSummaryData.get(CNLocationSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> menus = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));

		try {
			// Login to ADM
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			Assert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Setting GMA Subsidy ON with Group Names and Amount for TOP Off Subsidy
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menus.get(1));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			String value = dropDown.getSelectedItem(LocationSummary.DPD_GMA_SUBSIDY);
			Assert.assertEquals(value, requiredData.get(1));
			dropDown.selectItem(LocationSummary.DPD_GMA_SUBSIDY, requiredData.get(0), Constants.TEXT);
			if (checkBox.isChkEnabled(LocationSummary.CHK_TOP_OFF_SUBSIDY))
				checkBox.unCheck(LocationSummary.CHK_TOP_OFF_SUBSIDY);
			if (checkBox.isChkEnabled(LocationSummary.CHK_ROLL_OVER_SUBSIDY))
				checkBox.unCheck(LocationSummary.CHK_ROLL_OVER_SUBSIDY);
			locationSummary.enterSubsidyAmount(requiredData.get(4), requiredData.get(5));
			locationSummary.enterSubsidyGroupNames(requiredData.get(2), requiredData.get(3));
			login.logout();

			// Login into SOS application
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.SOS_CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			Assert.assertTrue(foundation.isDisplayed(SOSHome.LANDING_PAGE_HEADING));

			// select Organization and navigate to menu
			sosHome.selectOrginazation(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menus.get(0));
			Assert.assertTrue(foundation.isDisplayed(SOSHome.PAGE_HEADING));

			// Creating Consumer with Subsidy Group via Template in SOS Load
			String requiredStringData = (requiredString + Constants.DELIMITER_HASH + requiredData.get(2));
			excel.writeToExcel(FilePath.GMA_ACCOUNT_TEMPLATE, rstGmaUser.get(CNGmaUser.SHEET_NAME),
					rstProductSummaryData.get(CNProductSummary.ITERATION_COUNT), requiredStringData);
			loadGma.gMAUser(rstLocationListData.get(CNLocationList.LOCATION_NAME), rstGmaUser.get(CNGmaUser.PIN_VALUE),
					rstGmaUser.get(CNGmaUser.START_BALANCE), FilePath.GMA_ACCOUNT_TEMPLATE,
					rstLoadProduct.get(CNLoadProduct.DELETE_EXISTING_PRODUCT));
			Assert.assertTrue(foundation.isDisplayed(LoadGMA.LBL_SUCCESS));
			sosHome.logout();

			// Again Login to ADM
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			Assert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Verify GMA Subsidy column in Consumer Summary Page
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menus.get(2));
			foundation.click(ConsumerSearch.CLEAR_SEARCH);
			dropDown.selectItem(ConsumerSearch.DPD_LOCATION, rstLocationListData.get(CNLocationList.LOCATION_NAME),
					Constants.TEXT);
			foundation.click(ConsumerSearch.BTN_GO);
			foundation.threadWait(Constants.ONE_SECOND);
			String subsidyName = consumerSearch.getSubsidyName();
			Assert.assertEquals(subsidyName, requiredData.get(2));
			foundation.click(consumerSearch.objFirstNameCell(consumerSearch.getConsumerFirstName()));
			Assert.assertTrue(foundation.isDisplayed(ConsumerSummary.LBL_CONSUMER_SUMMARY));
			Assert.assertTrue(foundation.isDisplayed(ConsumerSummary.TXT_CONSUMER_ACCOUNT));
			Assert.assertTrue(foundation.isDisplayed(ConsumerSummary.TXT_SUBSIDY_TOP_OFF));
			Assert.assertTrue(foundation.isDisplayed(ConsumerSummary.TXT_SUBSIDY_GROUP));
			subsidyName = dropDown.getSelectedItem(ConsumerSummary.DPD_SUBSIDY_GROUP_NAME);
			Assert.assertEquals(subsidyName, requiredData.get(2));
			value = String.valueOf(consumerSummary.getBalance());
			assertTrue(value.equals("25.0"));
			value = String.valueOf(consumerSummary.getTypeBalance());
			assertTrue(value.equals(requiredData.get(4) + ".0"));

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {

			// resetting test data
			locationSummary.subsidyResettingOff(menus.get(1), rstLocationListData.get(CNLocationList.LOCATION_NAME),
					requiredData.get(1));
		}
	}

	@Test(description = "166059 - Verify the RollOver subsidy option")
	public void verifyRollOverSubsidyOption() {
		final String CASE_NUM = "166059";

		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
		rstGmaUser = dataBase.getGmaUserData(Queries.GMA_USER, CASE_NUM);
		rstLoadProduct = dataBase.getLoadProductData(Queries.LOAD_PRODUCT, CASE_NUM);
		rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);
		rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);

		List<String> requiredData = Arrays
				.asList(rstLocationSummaryData.get(CNLocationSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> menus = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));

		try {
			// Login to ADM
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			Assert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Setting GMA Subsidy ON with Group Names and Amount for TOP Off Subsidy
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menus.get(1));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			String value = dropDown.getSelectedItem(LocationSummary.DPD_GMA_SUBSIDY);
			Assert.assertEquals(value, requiredData.get(1));
			dropDown.selectItem(LocationSummary.DPD_GMA_SUBSIDY, requiredData.get(0), Constants.TEXT);
			if (checkBox.isChkEnabled(LocationSummary.CHK_TOP_OFF_SUBSIDY))
				checkBox.unCheck(LocationSummary.CHK_TOP_OFF_SUBSIDY);
			if (checkBox.isChkEnabled(LocationSummary.CHK_ROLL_OVER_SUBSIDY))
				checkBox.unCheck(LocationSummary.CHK_ROLL_OVER_SUBSIDY);
			locationSummary.enterSubsidyAmount(requiredData.get(4), requiredData.get(5));
			locationSummary.enterSubsidyGroupNames(requiredData.get(2), requiredData.get(3));
			login.logout();

			// Login into SOS application
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.SOS_CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			Assert.assertTrue(foundation.isDisplayed(SOSHome.LANDING_PAGE_HEADING));

			// select Organization and navigate to menu
			sosHome.selectOrginazation(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menus.get(0));
			Assert.assertTrue(foundation.isDisplayed(SOSHome.PAGE_HEADING));

			// Creating Consumer with Subsidy Group via Template in SOS Load
			String requiredStringData = (requiredString + Constants.DELIMITER_HASH + requiredData.get(3));
			excel.writeToExcel(FilePath.GMA_ACCOUNT_TEMPLATE, rstGmaUser.get(CNGmaUser.SHEET_NAME),
					rstProductSummaryData.get(CNProductSummary.ITERATION_COUNT), requiredStringData);
			loadGma.gMAUser(rstLocationListData.get(CNLocationList.LOCATION_NAME), rstGmaUser.get(CNGmaUser.PIN_VALUE),
					rstGmaUser.get(CNGmaUser.START_BALANCE), FilePath.GMA_ACCOUNT_TEMPLATE,
					rstLoadProduct.get(CNLoadProduct.DELETE_EXISTING_PRODUCT));
			Assert.assertTrue(foundation.isDisplayed(LoadGMA.LBL_SUCCESS));
			sosHome.logout();

			// Again Login to ADM
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			Assert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Verify GMA Subsidy column in Consumer Summary Page
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menus.get(2));
			foundation.click(ConsumerSearch.CLEAR_SEARCH);
			dropDown.selectItem(ConsumerSearch.DPD_LOCATION, rstLocationListData.get(CNLocationList.LOCATION_NAME),
					Constants.TEXT);
			foundation.click(ConsumerSearch.BTN_GO);
			foundation.threadWait(Constants.ONE_SECOND);
			String subsidyName = consumerSearch.getSubsidyName();
			Assert.assertEquals(subsidyName, requiredData.get(3));
			foundation.click(consumerSearch.objFirstNameCell(consumerSearch.getConsumerFirstName()));
			Assert.assertTrue(foundation.isDisplayed(ConsumerSummary.LBL_CONSUMER_SUMMARY));
			Assert.assertTrue(foundation.isDisplayed(ConsumerSummary.TXT_CONSUMER_ACCOUNT));
			Assert.assertTrue(foundation.isDisplayed(ConsumerSummary.TXT_SUBSIDY_ROLL_OVER));
			Assert.assertTrue(foundation.isDisplayed(ConsumerSummary.TXT_SUBSIDY_GROUP));
			subsidyName = dropDown.getSelectedItem(ConsumerSummary.DPD_SUBSIDY_GROUP_NAME);
			Assert.assertEquals(subsidyName, requiredData.get(3));
			value = String.valueOf(consumerSummary.getBalance());
			assertTrue(value.equals("25.0"));
			value = String.valueOf(consumerSummary.getTypeBalance());
			assertTrue(value.equals(requiredData.get(5) + ".0"));

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {

			// resetting test data
			locationSummary.subsidyResettingOff(menus.get(1), rstLocationListData.get(CNLocationList.LOCATION_NAME),
					requiredData.get(1));
		}
	}
}

