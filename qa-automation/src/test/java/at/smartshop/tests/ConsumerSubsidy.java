package at.smartshop.tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import at.framework.database.mssql.Queries;
import at.framework.database.mssql.ResultSets;
import at.framework.files.Excel;
import at.framework.files.PropertyFile;
import at.framework.generic.CustomisedAssert;
import at.framework.generic.DateAndTime;
import at.framework.generic.Numbers;
import at.framework.generic.Strings;
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
import at.smartshop.database.columns.CNNavigationMenu;
import at.smartshop.database.columns.CNProductSummary;
import at.smartshop.database.columns.CNReportList;
import at.smartshop.database.columns.CNV5Device;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.pages.AccountAdjustment;
import at.smartshop.pages.ConsumerSearch;
import at.smartshop.pages.ConsumerSummary;
import at.smartshop.pages.CreateLocation;
import at.smartshop.pages.CreatePromotions;
import at.smartshop.pages.LocationList;
import at.smartshop.pages.LocationSummary;
import at.smartshop.pages.NavigationBar;
import at.smartshop.pages.ReportList;
import at.smartshop.sos.pages.LoadGMA;
import at.smartshop.sos.pages.SOSHome;
import at.smartshop.v5.pages.AccountLogin;
import at.smartshop.v5.pages.LandingPage;
import at.smartshop.v5.pages.Order;
import at.smartshop.v5.pages.Payments;
import at.smartshop.v5.pages.ProductSearch;

@Listeners(at.framework.reportsetup.Listeners.class)
public class ConsumerSubsidy extends TestInfra {

	private ResultSets dataBase = new ResultSets();
	private NavigationBar navigationBar = new NavigationBar();
	private PropertyFile propertyFile = new PropertyFile();
	private Dropdown dropDown = new Dropdown();
	private TextBox textBox = new TextBox();
	private Strings strings = new Strings();
	private DateAndTime dateAndTime = new DateAndTime();
	private Foundation foundation = new Foundation();
	private ConsumerSearch consumerSearch = new ConsumerSearch();
	private LocationSummary locationSummary = new LocationSummary();
	private LocationList locationList = new LocationList();
	private ReportList reportList = new ReportList();
	private AccountAdjustment accountAdjustment = new AccountAdjustment();
	private CheckBox checkBox = new CheckBox();
	private SOSHome sosHome = new SOSHome();
	private Numbers numbers = new Numbers();
	private Excel excel = new Excel();
	private LoadGMA loadGma = new LoadGMA();
	private ConsumerSummary consumerSummary = new ConsumerSummary();
	private Table table = new Table();
	private Order order = new Order();

	private Map<String, String> rstV5DeviceData;
	private Map<String, String> rstNavigationMenuData;
	private Map<String, String> rstLocationListData;
	private Map<String, String> rstLocationSummaryData;
	private Map<String, String> rstconsumerSearchData;
	private Map<String, String> rstConsumerSummaryData;
	private Map<String, String> rstLoadProduct;
	private Map<String, String> rstGmaUser;
	private Map<String, String> rstProductSummaryData;
	private Map<String, String> rstReportListData;

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
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Select Menu, Menu Item and Location
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));

			// Verifying the selection of defaults for the Both GMA subsidy
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_MULTI_TAX_REPORT));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_GMA_SUBSIDY));
			List<String> values = dropDown.getAllItems(LocationSummary.DPD_GMA_SUBSIDY);
			List<String> expectedValues = new ArrayList<String>();
			expectedValues.add(requiredData.get(1));
			expectedValues.add(requiredData.get(0));
			CustomisedAssert.assertTrue(values.equals(expectedValues));
			String value = dropDown.getSelectedItem(LocationSummary.DPD_GMA_SUBSIDY);
			if (value.equals(requiredData.get(1))) {
				dropDown.selectItem(LocationSummary.DPD_GMA_SUBSIDY, requiredData.get(0), Constants.TEXT);
			}
			value = dropDown.getSelectedItem(LocationSummary.DPD_GMA_SUBSIDY);
			CustomisedAssert.assertEquals(value, requiredData.get(0));
			foundation.scrollIntoViewElement(LocationSummary.DPD_GMA_SUBSIDY);
			locationSummary.verifyTopOffSubsidy(requiredData);
			locationSummary.verifyRolloverSubsidy(requiredData);
			if (checkBox.isChkEnabled(LocationSummary.CHK_TOP_OFF_SUBSIDY)) {
				checkBox.check(LocationSummary.CHK_TOP_OFF_SUBSIDY);
				foundation.threadWait(Constants.ONE_SECOND);
				CustomisedAssert.assertFalse(checkBox.isChkEnabled(LocationSummary.CHK_ROLL_OVER_SUBSIDY));
				checkBox.unCheck(LocationSummary.CHK_TOP_OFF_SUBSIDY);
			} else if (checkBox.isChkEnabled(LocationSummary.CHK_ROLL_OVER_SUBSIDY)) {
				checkBox.check(LocationSummary.CHK_ROLL_OVER_SUBSIDY);
				foundation.threadWait(Constants.ONE_SECOND);
				CustomisedAssert.assertFalse(checkBox.isChkEnabled(LocationSummary.CHK_TOP_OFF_SUBSIDY));
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
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_GMA_SUBSIDY));

			// Verifying the duplicate names for both subsidy
			textBox.enterText(LocationSummary.TXT_TOP_OFF_GROUP_NAME, requiredData.get(10));
			textBox.enterText(LocationSummary.TXT_ROLL_OVER_GROUP_NAME, requiredData.get(10));
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_ERROR_MSG, Constants.SHORT_TIME);
			foundation.scrollIntoViewElement(LocationSummary.TXT_GMA_SUBSIDY);
			foundation.threadWait(Constants.TWO_SECOND);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.ROLL_OVER_WARNING_MSG));
			textBox.enterText(LocationSummary.TXT_ROLL_OVER_GROUP_NAME, requiredData.get(11));
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_GMA_SUBSIDY));
			foundation.scrollIntoViewElement(LocationSummary.CHK_TOP_OFF_SUBSIDY);
			checkBox.check(LocationSummary.CHK_TOP_OFF_SUBSIDY);

			// Verifying Date picker of Top Off
			foundation.click(LocationSummary.START_DATE_PICKER_TOP_OFF_1);
			locationSummary.verifyTopOffDateAutoLocation1(currentDate);
			String futureDate = dateAndTime.getFutureDate(Constants.REGEX_MM_DD_YYYY, requiredData.get(12));
			foundation.click(LocationSummary.START_DATE_PICKER_TOP_OFF_1);
			locationSummary.verifyTopOffFutureDateAutoLocation1(futureDate);
			checkBox.unCheck(LocationSummary.CHK_TOP_OFF_SUBSIDY);
			checkBox.check(LocationSummary.CHK_ROLL_OVER_SUBSIDY);

			// Verifying Date picker of Roll Over
			foundation.threadWait(Constants.ONE_SECOND);
			foundation.click(LocationSummary.START_DATE_PICKER_ROLL_OVER);
			foundation.threadWait(Constants.THREE_SECOND);
			foundation.click(LocationSummary.START_DATE_PICKER_ROLL_OVER);
			locationSummary.verifyRollOverDateLocation1(currentDate);
			foundation.click(LocationSummary.START_DATE_PICKER_ROLL_OVER);
			locationSummary.verifyRollOverFutureDateLocation1(futureDate);
			checkBox.unCheck(LocationSummary.CHK_ROLL_OVER_SUBSIDY);
			checkBox.check(LocationSummary.CHK_TOP_OFF_SUBSIDY);

			// Validating Recurrence field of Top Off subsidy
			dropDown.selectItem(LocationSummary.DPD_TOP_OFF_RECURRENCE, requiredData.get(13), Constants.TEXT);
			recurrence = dropDown.getSelectedItem(LocationSummary.DPD_TOP_OFF_RECURRENCE);
			CustomisedAssert.assertEquals(recurrence, requiredData.get(13));
			dropDown.selectItem(LocationSummary.DPD_TOP_OFF_RECURRENCE, requiredData.get(14), Constants.TEXT);
			recurrence = dropDown.getSelectedItem(LocationSummary.DPD_TOP_OFF_RECURRENCE);
			CustomisedAssert.assertEquals(recurrence, requiredData.get(14));
			dropDown.selectItem(LocationSummary.DPD_TOP_OFF_RECURRENCE, requiredData.get(15), Constants.TEXT);
			recurrence = dropDown.getSelectedItem(LocationSummary.DPD_TOP_OFF_RECURRENCE);
			CustomisedAssert.assertEquals(recurrence, requiredData.get(15));
			checkBox.unCheck(LocationSummary.CHK_TOP_OFF_SUBSIDY);

			// Validating Recurrence field of Roll Over subsidy
			checkBox.check(LocationSummary.CHK_ROLL_OVER_SUBSIDY);
			dropDown.selectItem(LocationSummary.DPD_ROLL_OVER_RECURRENCE, requiredData.get(13), Constants.TEXT);
			recurrence = dropDown.getSelectedItem(LocationSummary.DPD_ROLL_OVER_RECURRENCE);
			CustomisedAssert.assertEquals(recurrence, requiredData.get(13));
			dropDown.selectItem(LocationSummary.DPD_ROLL_OVER_RECURRENCE, requiredData.get(14), Constants.TEXT);
			recurrence = dropDown.getSelectedItem(LocationSummary.DPD_ROLL_OVER_RECURRENCE);
			CustomisedAssert.assertEquals(recurrence, requiredData.get(14));
			dropDown.selectItem(LocationSummary.DPD_ROLL_OVER_RECURRENCE, requiredData.get(15), Constants.TEXT);
			recurrence = dropDown.getSelectedItem(LocationSummary.DPD_ROLL_OVER_RECURRENCE);
			CustomisedAssert.assertEquals(recurrence, requiredData.get(15));
			checkBox.unCheck(LocationSummary.CHK_ROLL_OVER_SUBSIDY);

			// Verifying Group name field in both subsidy
			checkBox.check(LocationSummary.CHK_TOP_OFF_SUBSIDY);
			textBox.enterText(LocationSummary.TXT_TOP_OFF_GROUP_NAME, requiredData.get(16));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TOP_OFF_WARNING_MSG));
			checkBox.unCheck(LocationSummary.CHK_TOP_OFF_SUBSIDY);
			checkBox.check(LocationSummary.CHK_ROLL_OVER_SUBSIDY);
			textBox.enterText(LocationSummary.TXT_ROLL_OVER_GROUP_NAME, requiredData.get(16));
			foundation.threadWait(Constants.THREE_SECOND);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.LBL_ROLL_OVER_WARNING_MSG));
			foundation.threadWait(Constants.TWO_SECOND);

		} catch (Exception exc) {
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
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Select Menu, Menu Item and Location
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));

			// Verifying GMA subsidy field
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_GMA_SUBSIDY));
			String value = dropDown.getSelectedItem(LocationSummary.DPD_GMA_SUBSIDY);
			if (value.equals(requiredData.get(1))) {
				dropDown.selectItem(LocationSummary.DPD_GMA_SUBSIDY, requiredData.get(0), Constants.TEXT);
			}
			value = dropDown.getSelectedItem(LocationSummary.DPD_GMA_SUBSIDY);
			CustomisedAssert.assertEquals(value, requiredData.get(0));
			foundation.scrollIntoViewElement(LocationSummary.DPD_GMA_SUBSIDY);
			locationSummary.verifyTopOffSubsidy(requiredData);
			locationSummary.verifyRolloverSubsidy(requiredData);
			checkBox.check(LocationSummary.CHK_TOP_OFF_SUBSIDY);
			foundation.click(LocationSummary.START_DATE_PICKER_TOP_OFF);
			locationSummary.verifyTopOffDateAutoLocation1(currentDate);

			// Setting start date of GMA Subsidy as Weekly with Group Names
			dropDown.selectItem(LocationSummary.DPD_TOP_OFF_RECURRENCE, requiredData.get(10), Constants.TEXT);
			locationSummary.enterGroupNames(requiredData.get(7), requiredData.get(8), requiredData.get(9));
		} catch (Exception exc) {
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
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Select Menu, Menu Item and Location
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));

			// Verifying GMA Subsidy fields
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_GMA_SUBSIDY));
			String value = dropDown.getSelectedItem(LocationSummary.DPD_GMA_SUBSIDY);
			if (value.equals(requiredData.get(1))) {
				dropDown.selectItem(LocationSummary.DPD_GMA_SUBSIDY, requiredData.get(0), Constants.TEXT);
			}
			value = dropDown.getSelectedItem(LocationSummary.DPD_GMA_SUBSIDY);
			CustomisedAssert.assertEquals(value, requiredData.get(0));
			foundation.scrollIntoViewElement(LocationSummary.DPD_GMA_SUBSIDY);
			locationSummary.verifyTopOffSubsidy(requiredData);
			locationSummary.verifyRolloverSubsidy(requiredData);
			checkBox.check(LocationSummary.CHK_TOP_OFF_SUBSIDY);
			foundation.click(LocationSummary.START_DATE_PICKER_TOP_OFF);
			locationSummary.verifyTopOffDateAutoLocation1(currentDate);

			// Setting start date of GMA Subsidy as Daily with Group Names
			dropDown.selectItem(LocationSummary.DPD_TOP_OFF_RECURRENCE, requiredData.get(11), Constants.TEXT);
			locationSummary.enterGroupNames(requiredData.get(7), requiredData.get(8), requiredData.get(9));
		} catch (Exception exc) {
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
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Select Menu, Menu Item and Location
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));

			// Verifying GMA subsidy fields
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_GMA_SUBSIDY));
			String value = dropDown.getSelectedItem(LocationSummary.DPD_GMA_SUBSIDY);
			if (value.equals(requiredData.get(1))) {
				dropDown.selectItem(LocationSummary.DPD_GMA_SUBSIDY, requiredData.get(0), Constants.TEXT);
			}
			value = dropDown.getSelectedItem(LocationSummary.DPD_GMA_SUBSIDY);
			CustomisedAssert.assertEquals(value, requiredData.get(0));
			foundation.scrollIntoViewElement(LocationSummary.DPD_GMA_SUBSIDY);
			locationSummary.verifyTopOffSubsidy(requiredData);
			locationSummary.verifyRolloverSubsidy(requiredData);
			checkBox.check(LocationSummary.CHK_TOP_OFF_SUBSIDY);
			foundation.click(LocationSummary.START_DATE_PICKER_TOP_OFF);
			locationSummary.verifyTopOffDateAutoLocation1(currentDate);

			// Setting start date of GMA Subsidy as Monthly with Group Names
			dropDown.selectItem(LocationSummary.DPD_TOP_OFF_RECURRENCE, requiredData.get(12), Constants.TEXT);
			locationSummary.enterGroupNames(requiredData.get(7), requiredData.get(8), requiredData.get(9));
		} catch (Exception exc) {
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
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Select Menu, Menu Item and Location
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));

			// Verifying GMA subsidy fields
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_GMA_SUBSIDY));
			String value = dropDown.getSelectedItem(LocationSummary.DPD_GMA_SUBSIDY);
			if (value.equals(requiredData.get(1))) {
				dropDown.selectItem(LocationSummary.DPD_GMA_SUBSIDY, requiredData.get(0), Constants.TEXT);
			}
			value = dropDown.getSelectedItem(LocationSummary.DPD_GMA_SUBSIDY);
			CustomisedAssert.assertEquals(value, requiredData.get(0));
			foundation.scrollIntoViewElement(LocationSummary.DPD_GMA_SUBSIDY);
			locationSummary.verifyTopOffSubsidy(requiredData);
			locationSummary.verifyRolloverSubsidy(requiredData);
			dropDown.selectItem(LocationSummary.DPD_TOP_OFF_RECURRENCE, requiredData.get(10), Constants.TEXT);
			textBox.enterText(LocationSummary.TXT_TOP_OFF_GROUP_NAME, requiredData.get(7));

			// Validating Amount field of Top Off subsidy
			textBox.enterText(LocationSummary.TXT_TOP_OFF_AMOUNT, requiredData.get(11));
			CustomisedAssert.assertTrue(textBox.getTextFromInput(LocationSummary.TXT_TOP_OFF_AMOUNT_VALUE).equals("0"));
			textBox.enterText(LocationSummary.TXT_TOP_OFF_AMOUNT, requiredData.get(12));
			textBox.enterText(LocationSummary.TXT_ROLL_OVER_GROUP_NAME, requiredData.get(8));
			textBox.enterText(LocationSummary.TXT_PICKUP_LOCATION_NAME, requiredData.get(9));
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_ERROR_MSG, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.CHK_TOP_OFF_ERROR));
			textBox.enterText(LocationSummary.TXT_TOP_OFF_AMOUNT, requiredData.get(13));
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);

			// Validating Amount field of Roll Over subsidy
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_GMA_SUBSIDY));
			checkBox.unCheck(LocationSummary.CHK_TOP_OFF_SUBSIDY);
			checkBox.check(LocationSummary.CHK_ROLL_OVER_SUBSIDY);
			foundation.click(LocationSummary.START_DATE_PICKER_ROLL_OVER);
			locationSummary.verifyRollOverCurrentDate(currentDate);
			dropDown.selectItem(LocationSummary.DPD_ROLL_OVER_RECURRENCE, requiredData.get(10), Constants.TEXT);
			textBox.enterText(LocationSummary.TXT_ROLL_OVER_GROUP_NAME, requiredData.get(8));
			textBox.enterText(LocationSummary.TXT_ROLL_OVER_AMOUNT, requiredData.get(11));
			CustomisedAssert
					.assertTrue(textBox.getTextFromInput(LocationSummary.TXT_ROLL_OVER_AMOUNT_VALUE).equals("0"));
			textBox.enterText(LocationSummary.TXT_ROLL_OVER_AMOUNT, requiredData.get(12));
			textBox.enterText(LocationSummary.TXT_PICKUP_LOCATION_NAME, requiredData.get(9));
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_ERROR_MSG, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.CHK_ROLL_OVER_ERROR));
			textBox.enterText(LocationSummary.TXT_ROLL_OVER_AMOUNT, requiredData.get(13));
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {

			// resetting test data
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			foundation.waitforElement(
					locationList.getlocationElement(rstLocationListData.get(CNLocationList.LOCATION_NAME)),
					Constants.SHORT_TIME);
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			foundation.threadWait(Constants.THREE_SECOND);
			foundation.scrollIntoViewElement(LocationSummary.TXT_ROLL_OVER_SUBSIDY);
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
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Select Menu, Menu Item and Location
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));

			// Verifying GMA subsidy field
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_GMA_SUBSIDY));
			String value = dropDown.getSelectedItem(LocationSummary.DPD_GMA_SUBSIDY);
			if (value.equals(requiredData.get(1))) {
				dropDown.selectItem(LocationSummary.DPD_GMA_SUBSIDY, requiredData.get(0), Constants.TEXT);
			}
			value = dropDown.getSelectedItem(LocationSummary.DPD_GMA_SUBSIDY);
			CustomisedAssert.assertEquals(value, requiredData.get(0));
			foundation.scrollIntoViewElement(LocationSummary.DPD_GMA_SUBSIDY);
			locationSummary.verifyTopOffSubsidy(requiredData);
			locationSummary.verifyRolloverSubsidy(requiredData);
			if (checkBox.isChkEnabled(LocationSummary.CHK_TOP_OFF_SUBSIDY))
				checkBox.check(LocationSummary.CHK_TOP_OFF_SUBSIDY);
			CustomisedAssert.assertFalse(checkBox.isChkEnabled(LocationSummary.CHK_ROLL_OVER_SUBSIDY));
			checkBox.unCheck(LocationSummary.CHK_TOP_OFF_SUBSIDY);
			if (checkBox.isChkEnabled(LocationSummary.CHK_ROLL_OVER_SUBSIDY))
				checkBox.check(LocationSummary.CHK_ROLL_OVER_SUBSIDY);
			CustomisedAssert.assertFalse(checkBox.isChkEnabled(LocationSummary.CHK_TOP_OFF_SUBSIDY));
			checkBox.unCheck(LocationSummary.CHK_ROLL_OVER_SUBSIDY);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_GMA_SUBSIDY));
			checkBox.check(LocationSummary.CHK_TOP_OFF_SUBSIDY);

			// Validating Start Date picker field of Top Off subsidy
			foundation.click(LocationSummary.START_DATE_PICKER_TOP_OFF_1);
			locationSummary.verifyTopOffDateAutoLocation1(currentDate);
			String futureDate = dateAndTime.getFutureDate(Constants.REGEX_MM_DD_YYYY, requiredData.get(10));
			foundation.click(LocationSummary.START_DATE_PICKER_TOP_OFF_1);
			locationSummary.verifyTopOffFutureDateAutoLocation1(futureDate);

			// Validating Recurrence field of Top Off subsidy
			dropDown.selectItem(LocationSummary.DPD_TOP_OFF_RECURRENCE, requiredData.get(7), Constants.TEXT);
			recurrence = dropDown.getSelectedItem(LocationSummary.DPD_TOP_OFF_RECURRENCE);
			CustomisedAssert.assertEquals(recurrence, requiredData.get(7));
			dropDown.selectItem(LocationSummary.DPD_TOP_OFF_RECURRENCE, requiredData.get(8), Constants.TEXT);
			recurrence = dropDown.getSelectedItem(LocationSummary.DPD_TOP_OFF_RECURRENCE);
			CustomisedAssert.assertEquals(recurrence, requiredData.get(8));
			dropDown.selectItem(LocationSummary.DPD_TOP_OFF_RECURRENCE, requiredData.get(9), Constants.TEXT);
			recurrence = dropDown.getSelectedItem(LocationSummary.DPD_TOP_OFF_RECURRENCE);
			CustomisedAssert.assertEquals(recurrence, requiredData.get(9));
			checkBox.unCheck(LocationSummary.CHK_TOP_OFF_SUBSIDY);
			checkBox.check(LocationSummary.CHK_ROLL_OVER_SUBSIDY);

			// Validating Recurrence field of Roll Over subsidy
			dropDown.selectItem(LocationSummary.DPD_ROLL_OVER_RECURRENCE, requiredData.get(7), Constants.TEXT);
			recurrence = dropDown.getSelectedItem(LocationSummary.DPD_ROLL_OVER_RECURRENCE);
			CustomisedAssert.assertEquals(recurrence, requiredData.get(7));
			dropDown.selectItem(LocationSummary.DPD_ROLL_OVER_RECURRENCE, requiredData.get(8), Constants.TEXT);
			recurrence = dropDown.getSelectedItem(LocationSummary.DPD_ROLL_OVER_RECURRENCE);
			CustomisedAssert.assertEquals(recurrence, requiredData.get(8));
			dropDown.selectItem(LocationSummary.DPD_ROLL_OVER_RECURRENCE, requiredData.get(9), Constants.TEXT);
			recurrence = dropDown.getSelectedItem(LocationSummary.DPD_ROLL_OVER_RECURRENCE);
			CustomisedAssert.assertEquals(recurrence, requiredData.get(9));

			// Validating Group Name field of Both subsidy
			textBox.enterText(LocationSummary.TXT_TOP_OFF_GROUP_NAME, requiredData.get(11));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TOP_OFF_WARNING_MSG));
			textBox.enterText(LocationSummary.TXT_TOP_OFF_GROUP_NAME, requiredData.get(12));
			textBox.enterText(LocationSummary.TXT_ROLL_OVER_GROUP_NAME, requiredData.get(12));
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_ERROR_MSG, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.ROLL_OVER_WARNING_MSG));
			textBox.enterText(LocationSummary.TXT_ROLL_OVER_GROUP_NAME, requiredData.get(13));
			foundation.click(LocationSummary.BTN_SAVE);
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_GMA_SUBSIDY));
			checkBox.unCheck(LocationSummary.CHK_ROLL_OVER_SUBSIDY);
			checkBox.check(LocationSummary.CHK_TOP_OFF_SUBSIDY);

			// Validating Amount field of Top Off subsidy
			textBox.enterText(LocationSummary.TXT_TOP_OFF_AMOUNT, requiredData.get(14));
			CustomisedAssert.assertTrue(textBox.getTextFromInput(LocationSummary.TXT_TOP_OFF_AMOUNT_VALUE).equals("0"));
			textBox.enterText(LocationSummary.TXT_TOP_OFF_AMOUNT, requiredData.get(15));
			textBox.enterText(LocationSummary.TXT_ROLL_OVER_GROUP_NAME, requiredData.get(13));
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_ERROR_MSG, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.CHK_TOP_OFF_ERROR));
			textBox.enterText(LocationSummary.TXT_TOP_OFF_AMOUNT, requiredData.get(16));
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_GMA_SUBSIDY));

			// Validating Delete and Add subsidy of Top Off subsidy
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.BTN_DELETE_TOP_OFF));
			foundation.click(LocationSummary.BTN_DELETE_TOP_OFF);
			foundation.waitforElement(LocationList.TXT_SPINNER_ERROR_MSG, Constants.SHORT_TIME);
			checkBox.unCheck(LocationSummary.CHK_TOP_OFF_SUBSIDY);
			checkBox.check(LocationSummary.CHK_ROLL_OVER_SUBSIDY);

			// Validating Delete and Add subsidy of Roll Over subsidy
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.BTN_DELETE_ROLL_OVER));
			foundation.click(LocationSummary.BTN_DELETE_ROLL_OVER);
			foundation.waitforElement(LocationList.TXT_SPINNER_ERROR_MSG, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.BTN_ADD_ROLL_OVER));
			locationSummary.verifySignsTopOff();
		} catch (Exception exc) {
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
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Select Menu, Menu Item and Location
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));

			// Verifying Drop down values of GMA subsidy field
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_MULTI_TAX_REPORT));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_GMA_SUBSIDY));
			List<String> actualValue = dropDown.getAllItems(LocationSummary.DPD_GMA_SUBSIDY);
			ArrayList<String> expectedValue = new ArrayList<String>();
			expectedValue.add(requiredData.get(0));
			expectedValue.add(requiredData.get(1));
			CustomisedAssert.assertTrue(actualValue.equals(expectedValue));
			String value = dropDown.getSelectedItem(LocationSummary.DPD_GMA_SUBSIDY);
			CustomisedAssert.assertEquals(value, requiredData.get(0));
			dropDown.selectItem(LocationSummary.DPD_GMA_SUBSIDY, requiredData.get(1), Constants.TEXT);
			value = dropDown.getSelectedItem(LocationSummary.DPD_GMA_SUBSIDY);
			CustomisedAssert.assertEquals(value, requiredData.get(1));
			locationSummary.verifyTopOffSubsidy(requiredData);
			locationSummary.verifyRolloverSubsidy(requiredData);

		} catch (Exception exc) {
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
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Select Menu, Menu Item and Location
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			foundation.click(LocationList.BTN_CREATE);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_MULTI_TAX_REPORT));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_GMA_SUBSIDY));
			List<String> actualValue = dropDown.getAllItems(LocationSummary.DPD_GMA_SUBSIDY);
			ArrayList<String> expectedValue = new ArrayList<String>();
			expectedValue.add(requiredData.get(0));
			expectedValue.add(requiredData.get(1));
			CustomisedAssert.assertTrue(actualValue.equals(expectedValue));

			// Verifying Drop down values of GMA subsidy in Location creation Page
			String value = dropDown.getSelectedItem(LocationSummary.DPD_GMA_SUBSIDY);
			CustomisedAssert.assertEquals(value, requiredData.get(0));
			dropDown.selectItem(LocationSummary.DPD_GMA_SUBSIDY, requiredData.get(1), Constants.TEXT);
			value = dropDown.getSelectedItem(LocationSummary.DPD_GMA_SUBSIDY);
			CustomisedAssert.assertEquals(value, requiredData.get(1));
			locationSummary.verifyTopOffSubsidy(requiredData);
			locationSummary.verifyRolloverSubsidy(requiredData);

		} catch (Exception exc) {
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
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Select Menu, Menu Item and Location
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));

			// Verifying Add and Delete signs of Top Off Subsidy
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_GMA_SUBSIDY));
			String value = dropDown.getSelectedItem(LocationSummary.DPD_GMA_SUBSIDY);
			if (value.equals(requiredData.get(1))) {
				dropDown.selectItem(LocationSummary.DPD_GMA_SUBSIDY, requiredData.get(0), Constants.TEXT);
			}
			value = dropDown.getSelectedItem(LocationSummary.DPD_GMA_SUBSIDY);
			CustomisedAssert.assertEquals(value, requiredData.get(0));
			foundation.scrollIntoViewElement(LocationSummary.DPD_GMA_SUBSIDY);
			locationSummary.verifyTopOffSubsidy(requiredData);
			locationSummary.verifyRolloverSubsidy(requiredData);
			if (checkBox.isChkEnabled(LocationSummary.CHK_TOP_OFF_SUBSIDY))
				checkBox.check(LocationSummary.CHK_TOP_OFF_SUBSIDY);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.BTN_DELETE_TOP_OFF));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.BTN_ADD_TOP_OFF));
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
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_GMA_SUBSIDY));
			value = dropDown.getSelectedItem(LocationSummary.DPD_GMA_SUBSIDY);
			CustomisedAssert.assertEquals(value, requiredData.get(1));
			dropDown.selectItem(LocationSummary.DPD_GMA_SUBSIDY, requiredData.get(0), Constants.TEXT);
			locationSummary.verifyTopOffSubsidy(requiredData);
			locationSummary.verifyRolloverSubsidy(requiredData);
			foundation.click(LocationSummary.CHK_ROLL_OVER_SUBSIDY);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.BTN_DELETE_ROLL_OVER));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.BTN_ADD_ROLL_OVER));
			foundation.click(LocationSummary.BTN_DELETE_ROLL_OVER);
			foundation.waitforElement(LocationList.TXT_SPINNER_ERROR_MSG, Constants.SHORT_TIME);
			locationSummary.verifySignsRollOver();
		} catch (Exception exc) {
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
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Select Menu, Menu Item and Location
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));

			// Verifying GMA subsidy field
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_GMA_SUBSIDY));
			String value = dropDown.getSelectedItem(LocationSummary.DPD_GMA_SUBSIDY);
			if (value.equals(requiredData.get(1))) {
				dropDown.selectItem(LocationSummary.DPD_GMA_SUBSIDY, requiredData.get(0), Constants.TEXT);
			}
			value = dropDown.getSelectedItem(LocationSummary.DPD_GMA_SUBSIDY);
			CustomisedAssert.assertEquals(value, requiredData.get(0));
			foundation.scrollIntoViewElement(LocationSummary.DPD_GMA_SUBSIDY);
			locationSummary.verifyTopOffSubsidy(requiredData);
			locationSummary.verifyRolloverSubsidy(requiredData);

			// Verifying Default Radio Buttons of both subsidy
			if (checkBox.isChkEnabled(LocationSummary.CHK_TOP_OFF_SUBSIDY))
				checkBox.check(LocationSummary.CHK_TOP_OFF_SUBSIDY);
			CustomisedAssert.assertFalse(checkBox.isChkEnabled(LocationSummary.CHK_ROLL_OVER_SUBSIDY));
			checkBox.unCheck(LocationSummary.CHK_TOP_OFF_SUBSIDY);
			if (checkBox.isChkEnabled(LocationSummary.CHK_ROLL_OVER_SUBSIDY))
				checkBox.check(LocationSummary.CHK_ROLL_OVER_SUBSIDY);
			CustomisedAssert.assertFalse(checkBox.isChkEnabled(LocationSummary.CHK_TOP_OFF_SUBSIDY));
			checkBox.unCheck(LocationSummary.CHK_ROLL_OVER_SUBSIDY);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_GMA_SUBSIDY));
			checkBox.check(LocationSummary.CHK_TOP_OFF_SUBSIDY);

			// Verifying Start Date picker of Top Off Subsidy
			foundation.click(LocationSummary.START_DATE_PICKER_TOP_OFF_1);
			locationSummary.verifyTopOffDateAutoLocation1(currentDate);
			String futureDate = dateAndTime.getFutureDate(Constants.REGEX_MM_DD_YYYY, requiredData.get(10));
			foundation.click(LocationSummary.START_DATE_PICKER_TOP_OFF_1);
			locationSummary.verifyTopOffFutureDateAutoLocation1(futureDate);

			// Verifying Recurrence field of Top Off Subsidy
			dropDown.selectItem(LocationSummary.DPD_TOP_OFF_RECURRENCE, requiredData.get(7), Constants.TEXT);
			recurrence = dropDown.getSelectedItem(LocationSummary.DPD_TOP_OFF_RECURRENCE);
			CustomisedAssert.assertEquals(recurrence, requiredData.get(7));
			dropDown.selectItem(LocationSummary.DPD_TOP_OFF_RECURRENCE, requiredData.get(8), Constants.TEXT);
			recurrence = dropDown.getSelectedItem(LocationSummary.DPD_TOP_OFF_RECURRENCE);
			CustomisedAssert.assertEquals(recurrence, requiredData.get(8));
			dropDown.selectItem(LocationSummary.DPD_TOP_OFF_RECURRENCE, requiredData.get(9), Constants.TEXT);
			recurrence = dropDown.getSelectedItem(LocationSummary.DPD_TOP_OFF_RECURRENCE);
			CustomisedAssert.assertEquals(recurrence, requiredData.get(9));
			checkBox.unCheck(LocationSummary.CHK_TOP_OFF_SUBSIDY);

			// Verifying Recurrence field of Roll Over Subsidy
			checkBox.check(LocationSummary.CHK_ROLL_OVER_SUBSIDY);
			dropDown.selectItem(LocationSummary.DPD_ROLL_OVER_RECURRENCE, requiredData.get(7), Constants.TEXT);
			recurrence = dropDown.getSelectedItem(LocationSummary.DPD_ROLL_OVER_RECURRENCE);
			CustomisedAssert.assertEquals(recurrence, requiredData.get(7));
			dropDown.selectItem(LocationSummary.DPD_ROLL_OVER_RECURRENCE, requiredData.get(8), Constants.TEXT);
			recurrence = dropDown.getSelectedItem(LocationSummary.DPD_ROLL_OVER_RECURRENCE);
			CustomisedAssert.assertEquals(recurrence, requiredData.get(8));
			dropDown.selectItem(LocationSummary.DPD_ROLL_OVER_RECURRENCE, requiredData.get(9), Constants.TEXT);
			recurrence = dropDown.getSelectedItem(LocationSummary.DPD_ROLL_OVER_RECURRENCE);
			CustomisedAssert.assertEquals(recurrence, requiredData.get(9));

			// Verifying Group Name duplication in both subsidy
			textBox.enterText(LocationSummary.TXT_TOP_OFF_GROUP_NAME, requiredData.get(11));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TOP_OFF_WARNING_MSG));
			textBox.enterText(LocationSummary.TXT_TOP_OFF_GROUP_NAME, requiredData.get(12));
			textBox.enterText(LocationSummary.TXT_ROLL_OVER_GROUP_NAME, requiredData.get(12));
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_ERROR_MSG, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.ROLL_OVER_WARNING_MSG));
			textBox.enterText(LocationSummary.TXT_ROLL_OVER_GROUP_NAME, requiredData.get(13));
			foundation.click(LocationSummary.BTN_SAVE);
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_GMA_SUBSIDY));
			checkBox.unCheck(LocationSummary.CHK_ROLL_OVER_SUBSIDY);
			checkBox.check(LocationSummary.CHK_TOP_OFF_SUBSIDY);

			// Validating Amount field in Top Off Subsidy
			textBox.enterText(LocationSummary.TXT_TOP_OFF_AMOUNT, requiredData.get(14));
			CustomisedAssert.assertTrue(textBox.getTextFromInput(LocationSummary.TXT_TOP_OFF_AMOUNT_VALUE).equals("0"));
			textBox.enterText(LocationSummary.TXT_TOP_OFF_AMOUNT, requiredData.get(15));
			textBox.enterText(LocationSummary.TXT_ROLL_OVER_GROUP_NAME, requiredData.get(13));
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_ERROR_MSG, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.CHK_TOP_OFF_ERROR));
			textBox.enterText(LocationSummary.TXT_TOP_OFF_AMOUNT, requiredData.get(16));
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_GMA_SUBSIDY));

			// Validating Delete subsidy in Top Off subsidy
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.BTN_DELETE_TOP_OFF));
			foundation.click(LocationSummary.BTN_DELETE_TOP_OFF);
			foundation.waitforElement(LocationList.TXT_SPINNER_ERROR_MSG, Constants.SHORT_TIME);
			checkBox.unCheck(LocationSummary.CHK_TOP_OFF_SUBSIDY);
			checkBox.check(LocationSummary.CHK_ROLL_OVER_SUBSIDY);

			// Validating Add and Delete subsidy in Roll Over subsidy
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.BTN_DELETE_ROLL_OVER));
			foundation.click(LocationSummary.BTN_DELETE_ROLL_OVER);
			foundation.waitforElement(LocationList.TXT_SPINNER_ERROR_MSG, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.BTN_ADD_ROLL_OVER));
			locationSummary.verifySignsRollOver();
		} catch (Exception exc) {
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
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Select Menu, Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			foundation.click(LocationList.BTN_CREATE);

			// Verifying GMA subsidy in Location Creation Page
			dropDown.selectItem(CreateLocation.DPD_TIME_ZONE, requiredData.get(17), Constants.VALUE);
			dropDown.selectItem(CreateLocation.DPD_TYPE, requiredData.get(18), Constants.VALUE);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_MULTI_TAX_REPORT));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_GMA_SUBSIDY));
			String value = dropDown.getSelectedItem(LocationSummary.DPD_GMA_SUBSIDY);
			CustomisedAssert.assertEquals(value, requiredData.get(1));
			dropDown.selectItem(LocationSummary.DPD_GMA_SUBSIDY, requiredData.get(0), Constants.TEXT);
			locationSummary.verifyRolloverSubsidy(requiredData);
			if (checkBox.isChkEnabled(LocationSummary.CHK_ROLL_OVER_SUBSIDY))
				checkBox.check(LocationSummary.CHK_ROLL_OVER_SUBSIDY);
			CustomisedAssert.assertFalse(checkBox.isChkEnabled(LocationSummary.CHK_TOP_OFF_SUBSIDY));

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
			CustomisedAssert.assertTrue(actualValue.equals(expectedValue));
			foundation.click(LocationSummary.CHK_ROLL_OVER_SUBSIDY);
			foundation.click(LocationSummary.CHK_ROLL_OVER_SUBSIDY);

			// Verifying Group names of Roll over Subsidy
			textBox.enterText(LocationSummary.TXT_ROLL_OVER_GROUP_NAME, requiredData.get(11));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.LBL_ROLL_OVER_WARNING_MSG));
			textBox.enterText(LocationSummary.TXT_TOP_OFF_GROUP_NAME, requiredData.get(12));
			textBox.enterText(LocationSummary.TXT_ROLL_OVER_GROUP_NAME, requiredData.get(12));
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_ERROR_MSG, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.ROLL_OVER_WARNING_MSG));
			textBox.enterText(LocationSummary.TXT_ROLL_OVER_GROUP_NAME, requiredData.get(13));

			// Validating amount field of Roll Over subsidy
			textBox.enterText(LocationSummary.TXT_ROLL_OVER_AMOUNT, requiredData.get(14));
			CustomisedAssert
					.assertTrue(textBox.getTextFromInput(LocationSummary.TXT_ROLL_OVER_AMOUNT_VALUE).equals("0"));
			textBox.enterText(LocationSummary.TXT_ROLL_OVER_AMOUNT, requiredData.get(15));
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_ERROR_MSG, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.CHK_ROLL_OVER_ERROR));
			textBox.enterText(LocationSummary.TXT_ROLL_OVER_AMOUNT, requiredData.get(16));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.CHK_ROLL_OVER_ERROR));
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.click(LocationSummary.CHK_ROLL_OVER_SUBSIDY);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.BTN_DELETE_ROLL_OVER));

			// Verifying Add and Delete signs of Subsidy in Roll Over subsidy
			foundation.click(LocationSummary.BTN_DELETE_ROLL_OVER);
			foundation.waitforElement(LocationList.TXT_SPINNER_ERROR_MSG, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.BTN_ADD_ROLL_OVER));
			locationSummary.verifySignsRollOver();
		} catch (Exception exc) {
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
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Select Menu, Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			foundation.click(LocationList.BTN_CREATE);

			// Verifying GMA subsidy fields in Location Creation Page
			dropDown.selectItem(CreateLocation.DPD_TIME_ZONE, requiredData.get(17), Constants.VALUE);
			dropDown.selectItem(CreateLocation.DPD_TYPE, requiredData.get(18), Constants.VALUE);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_MULTI_TAX_REPORT));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_GMA_SUBSIDY));
			String value = dropDown.getSelectedItem(LocationSummary.DPD_GMA_SUBSIDY);
			CustomisedAssert.assertEquals(value, requiredData.get(1));
			dropDown.selectItem(LocationSummary.DPD_GMA_SUBSIDY, requiredData.get(0), Constants.TEXT);
			locationSummary.verifyTopOffSubsidy(requiredData);
			locationSummary.verifyRolloverSubsidy(requiredData);
			if (checkBox.isChkEnabled(LocationSummary.CHK_TOP_OFF_SUBSIDY))
				checkBox.check(LocationSummary.CHK_TOP_OFF_SUBSIDY);
			CustomisedAssert.assertFalse(checkBox.isChkEnabled(LocationSummary.CHK_ROLL_OVER_SUBSIDY));
			checkBox.unCheck(LocationSummary.CHK_TOP_OFF_SUBSIDY);
			if (checkBox.isChkEnabled(LocationSummary.CHK_ROLL_OVER_SUBSIDY))
				checkBox.check(LocationSummary.CHK_ROLL_OVER_SUBSIDY);
			CustomisedAssert.assertFalse(checkBox.isChkEnabled(LocationSummary.CHK_TOP_OFF_SUBSIDY));
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
			CustomisedAssert.assertTrue(actualValue.equals(expectedValue));
			checkBox.unCheck(LocationSummary.CHK_TOP_OFF_SUBSIDY);
			checkBox.check(LocationSummary.CHK_ROLL_OVER_SUBSIDY);
			actualValue = dropDown.getAllItems(LocationSummary.DPD_ROLL_OVER_RECURRENCE);
			CustomisedAssert.assertTrue(actualValue.equals(expectedValue));

			// Validating Group Names of both subsidy
			textBox.enterText(LocationSummary.TXT_TOP_OFF_GROUP_NAME, requiredData.get(11));
			textBox.enterText(LocationSummary.TXT_TOP_OFF_GROUP_NAME, requiredData.get(12));
			textBox.enterText(LocationSummary.TXT_ROLL_OVER_GROUP_NAME, requiredData.get(12));
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_ERROR_MSG, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.ROLL_OVER_WARNING_MSG));
			textBox.enterText(LocationSummary.TXT_ROLL_OVER_GROUP_NAME, requiredData.get(13));
			textBox.enterText(LocationSummary.TXT_TOP_OFF_AMOUNT, requiredData.get(14));

			// Validating Amount field of Top off
			CustomisedAssert.assertTrue(textBox.getTextFromInput(LocationSummary.TXT_TOP_OFF_AMOUNT_VALUE).equals("0"));
			textBox.enterText(LocationSummary.TXT_TOP_OFF_AMOUNT, requiredData.get(15));
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_ERROR_MSG, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.CHK_TOP_OFF_ERROR));
			textBox.enterText(LocationSummary.TXT_TOP_OFF_AMOUNT, requiredData.get(16));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.CHK_TOP_OFF_ERROR));
			foundation.click(LocationSummary.BTN_SAVE);

			// Verifying Add and Delete signs of Top off subsidy
			checkBox.check(LocationSummary.CHK_TOP_OFF_SUBSIDY);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.BTN_DELETE_TOP_OFF));
			foundation.click(LocationSummary.BTN_DELETE_TOP_OFF);
			foundation.waitforElement(LocationList.TXT_SPINNER_ERROR_MSG, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.BTN_ADD_TOP_OFF));
			locationSummary.verifySignsTopOff();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "165971-ADM>Subsidy Balance>Adjustment")
	public void verifySubsidyBalanceAdjustment() {
		final String CASE_NUM = "165971";

		// reading a data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstconsumerSearchData = dataBase.getConsumerSearchData(Queries.CONSUMER_SEARCH, CASE_NUM);
		rstConsumerSummaryData = dataBase.getConsumerSummaryData(Queries.CONSUMER_SUMMARY, CASE_NUM);

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

			// select menu and menuitems and verifying increment in subsidy
			consumerSummary.Incrementsubsidy(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM),
					rstconsumerSearchData.get(CNConsumerSearch.LOCATION), values.get(0),
					rstConsumerSummaryData.get(CNConsumerSummary.REASON));
			List<String> datas = dropDown.getAllItems(ConsumerSummary.REF_EFT);
			ArrayList<String> expectedValues = new ArrayList<String>();
			expectedValues.add(values.get(2));
			expectedValues.add(values.get(3));
			CustomisedAssert.assertTrue(datas.equals(expectedValues));
			foundation.waitforElement(ConsumerSummary.BTN_REASON_SAVE, Constants.SHORT_TIME);
			foundation.click(ConsumerSummary.BTN_REASON_SAVE);
			foundation.threadWait(Constants.SHORT_TIME);
			// verify the decrement in subsidy
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSummary.LBL_CONSUMER_SUMMARY));
			foundation.click(ConsumerSummary.BTN_ADJUST);
			textBox.enterText(ConsumerSummary.TXT_ADJUST_BALANCE, values.get(1));
			dropDown.selectItem(ConsumerSummary.DPD_REASON, rstConsumerSummaryData.get(CNConsumerSummary.REASON),
					Constants.TEXT);
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSummary.REF_EFT));
			foundation.click(ConsumerSummary.REF_EFT);
			foundation.waitforElement(ConsumerSummary.BTN_REASON_SAVE, Constants.SHORT_TIME);
			foundation.click(ConsumerSummary.BTN_REASON_SAVE);
			foundation.threadWait(Constants.SHORT_TIME);
			// verifying cancel button
			consumerSummary.CancelButtonInSubsidyAdjustment(values.get(0),
					rstConsumerSummaryData.get(CNConsumerSummary.REASON));
		}

		catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "165972 Verify subsidy balance adjustments in ADM Consumer summary page as operator role")
	public void verifySubsidyBalanceIncrement() {
		final String CASE_NUM = "165972";

		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstconsumerSearchData = dataBase.getConsumerSearchData(Queries.CONSUMER_SEARCH, CASE_NUM);
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
			// verify increment in subsidy balance
			consumerSummary.Incrementsubsidy(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM),
					rstconsumerSearchData.get(CNConsumerSearch.LOCATION), values.get(0),
					rstConsumerSummaryData.get(CNConsumerSummary.REASON));
			List<String> datas = dropDown.getAllItems(ConsumerSummary.REF_EFT);
			ArrayList<String> expectedValues = new ArrayList<String>();
			expectedValues.add(values.get(2));
			expectedValues.add(values.get(3));
			CustomisedAssert.assertTrue(datas.equals(expectedValues));
			foundation.waitforElement(ConsumerSummary.BTN_REASON_SAVE, Constants.SHORT_TIME);
			foundation.click(ConsumerSummary.BTN_REASON_SAVE);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.waitforElementToBeVisible(ConsumerSummary.LBL_CONSUMER_SUMMARY, Constants.SHORT_TIME);

			// verify the decrement in subsidy balance
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSummary.LBL_CONSUMER_SUMMARY));
			foundation.click(ConsumerSummary.BTN_ADJUST);
			foundation.waitforElementToBeVisible(ConsumerSummary.LBL_POPUP_ADJUST_BALANCE, Constants.SHORT_TIME);
			textBox.enterText(ConsumerSummary.TXT_ADJUST_BALANCE, values.get(1));
			dropDown.selectItem(ConsumerSummary.DPD_REASON, rstConsumerSummaryData.get(CNConsumerSummary.REASON),
					Constants.TEXT);
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSummary.REF_EFT));
			foundation.click(ConsumerSummary.REF_EFT);
			foundation.waitforElement(ConsumerSummary.BTN_REASON_SAVE, Constants.SHORT_TIME);
			foundation.click(ConsumerSummary.BTN_REASON_SAVE);
			foundation.waitforElement(ConsumerSummary.ERROR_MESSAGE, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSummary.ERROR_MESSAGE));
			foundation.click(ConsumerSummary.ERROR_MESSAGE);
			// verifying cancel button
			consumerSummary.CancelButtonInSubsidyAdjustment(values.get(0),
					rstConsumerSummaryData.get(CNConsumerSummary.REASON));
		}

		catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "165950- Verify to view the more than one location is filtered then the 'Bulk Assign Subsidy Group' option"
			+ "165949- Verify to view the 'Bulk Assign Subsidy Group' action in the Actions dropdown")
	public void verifyBulkAssignSubsidyGroupInAction() {
		final String CASE_NUM = "165950";

		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstconsumerSearchData = dataBase.getConsumerSearchData(Queries.CONSUMER_SEARCH, CASE_NUM);

		List<String> expectedData = Arrays
				.asList(rstconsumerSearchData.get(CNConsumerSearch.ACTIONS).split(Constants.DELIMITER_TILD));
		List<String> values = Arrays
				.asList(rstconsumerSearchData.get(CNConsumerSearch.LOCATION).split(Constants.DELIMITER_TILD));

		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			foundation.click(ConsumerSearch.CLEAR_SEARCH);
			consumerSearch.BulkAssignSubsidyGroup(values.get(0), rstconsumerSearchData.get(CNConsumerSearch.SEARCH),
					expectedData);
			foundation.click(ConsumerSearch.BULK_ASSIGN_SUBSIDY_GROUP);
			foundation.threadWait(Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSearch.LBL_BULK_ASSIGN_POPUP));
			foundation.click(ConsumerSearch.RSN_CANCEL);
			dropDown.selectItem(ConsumerSearch.DPD_LOCATION, values.get(1), Constants.TEXT);
			consumerSearch.BulkAssignSubsidyGroup(values.get(0), rstconsumerSearchData.get(CNConsumerSearch.SEARCH),
					expectedData);
			foundation.threadWait(Constants.THREE_SECOND);
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSearch.BULK_ASSIGN_GRAYED));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

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
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Setting GMA Subsidy ON with Group Names and Amount for TOP Off Subsidy
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menus.get(1));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			String value = dropDown.getSelectedItem(LocationSummary.DPD_GMA_SUBSIDY);
			if (value.equals(requiredData.get(1))) {
				dropDown.selectItem(LocationSummary.DPD_GMA_SUBSIDY, requiredData.get(0), Constants.TEXT);
			}
			value = dropDown.getSelectedItem(LocationSummary.DPD_GMA_SUBSIDY);
			CustomisedAssert.assertEquals(value, requiredData.get(0));
			foundation.scrollIntoViewElement(LocationSummary.DPD_GMA_SUBSIDY);
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
			login.sosLogin(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(SOSHome.LANDING_PAGE_HEADING));

			// select Organization and navigate to menu
			sosHome.selectOrginazation(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menus.get(0));
			CustomisedAssert.assertTrue(foundation.isDisplayed(SOSHome.PAGE_HEADING));

			// Creating Consumer with Subsidy Group via Template in SOS Load
			String requiredStringData = (requiredString + Constants.DELIMITER_HASH + requiredData.get(2));
			excel.writeToExcel(FilePath.GMA_ACCOUNT_TEMPLATE, rstGmaUser.get(CNGmaUser.SHEET_NAME),
					rstProductSummaryData.get(CNProductSummary.ITERATION_COUNT), requiredStringData);
			loadGma.gMAUser(rstLocationListData.get(CNLocationList.LOCATION_NAME), rstGmaUser.get(CNGmaUser.PIN_VALUE),
					rstGmaUser.get(CNGmaUser.START_BALANCE), FilePath.GMA_ACCOUNT_TEMPLATE,
					rstLoadProduct.get(CNLoadProduct.DELETE_EXISTING_PRODUCT));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LoadGMA.LBL_SUCCESS));
			sosHome.logout();

			// Again Login to ADM
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

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
			CustomisedAssert.assertEquals(subsidyName, requiredData.get(2));
			foundation.click(consumerSearch.objFirstNameCell(consumerSearch.getConsumerFirstName()));
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSummary.LBL_CONSUMER_SUMMARY));
			// CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSummary.TXT_CONSUMER_ACCOUNT));
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSummary.TXT_SUBSIDY_TOP_OFF));
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSummary.TXT_SUBSIDY_GROUP));
			subsidyName = dropDown.getSelectedItem(ConsumerSummary.DPD_SUBSIDY_GROUP_NAME);
			CustomisedAssert.assertEquals(subsidyName, requiredData.get(2));
			value = String.valueOf(consumerSummary.getBalance());
			CustomisedAssert.assertTrue(value.equals("25.0"));
//			value = String.valueOf(consumerSummary.getTypeBalance());
//			CustomisedAssert.assertTrue(value.equals(requiredData.get(4) + ".0"));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// resetting Test Data
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
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Setting GMA Subsidy ON with Group Names and Amount for TOP Off Subsidy
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menus.get(1));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			String value = dropDown.getSelectedItem(LocationSummary.DPD_GMA_SUBSIDY);
			if (value.equals(requiredData.get(1))) {
				dropDown.selectItem(LocationSummary.DPD_GMA_SUBSIDY, requiredData.get(0), Constants.TEXT);
			}
			value = dropDown.getSelectedItem(LocationSummary.DPD_GMA_SUBSIDY);
			CustomisedAssert.assertEquals(value, requiredData.get(0));
			foundation.scrollIntoViewElement(LocationSummary.DPD_GMA_SUBSIDY);
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
			login.sosLogin(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(SOSHome.LANDING_PAGE_HEADING));

			// select Organization and navigate to menu
			sosHome.selectOrginazation(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menus.get(0));
			CustomisedAssert.assertTrue(foundation.isDisplayed(SOSHome.PAGE_HEADING));

			// Creating Consumer with Subsidy Group via Template in SOS Load
			String requiredStringData = (requiredString + Constants.DELIMITER_HASH + requiredData.get(3));
			excel.writeToExcel(FilePath.GMA_ACCOUNT_TEMPLATE, rstGmaUser.get(CNGmaUser.SHEET_NAME),
					rstProductSummaryData.get(CNProductSummary.ITERATION_COUNT), requiredStringData);
			loadGma.gMAUser(rstLocationListData.get(CNLocationList.LOCATION_NAME), rstGmaUser.get(CNGmaUser.PIN_VALUE),
					rstGmaUser.get(CNGmaUser.START_BALANCE), FilePath.GMA_ACCOUNT_TEMPLATE,
					rstLoadProduct.get(CNLoadProduct.DELETE_EXISTING_PRODUCT));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LoadGMA.LBL_SUCCESS));
			sosHome.logout();

			// Again Login to ADM
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

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
			CustomisedAssert.assertEquals(subsidyName, requiredData.get(3));
			foundation.click(consumerSearch.objFirstNameCell(consumerSearch.getConsumerFirstName()));
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSummary.LBL_CONSUMER_SUMMARY));
			// CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSummary.TXT_CONSUMER_ACCOUNT));
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSummary.TXT_SUBSIDY_ROLL_OVER));
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSummary.TXT_SUBSIDY_GROUP));
			subsidyName = dropDown.getSelectedItem(ConsumerSummary.DPD_SUBSIDY_GROUP_NAME);
			CustomisedAssert.assertEquals(subsidyName, requiredData.get(3));
//			value = String.valueOf(consumerSummary.getBalance());
//			CustomisedAssert.assertTrue(value.equals("25.0"));
//			value = String.valueOf(consumerSummary.getTypeBalance());
//			CustomisedAssert.assertTrue(value.equals(requiredData.get(5) + ".0"));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// resetting test data
			locationSummary.subsidyResettingOff(menus.get(1), rstLocationListData.get(CNLocationList.LOCATION_NAME),
					requiredData.get(1));
		}
	}

	@Test(description = "165970 - Verify to view the ADM Hiding Subsidy for USConnect"
			+ "166067 - verify the GMA Subsidy field when Special Type as USConnect.")
	public void verifyHidingSubsidyForUSConnect() {
		final String CASE_NUM = "165970";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
		rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);

		List<String> requiredData = Arrays
				.asList(rstLocationSummaryData.get(CNLocationSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> menus = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		String groupNames = strings.getRandomCharacter();

		try {

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Select Menu, Menu Item and Location
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menus.get(0));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));

			// Clearing all Subsidy data on the Location level
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_MULTI_TAX_REPORT));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_GMA_SUBSIDY));
			String value = dropDown.getSelectedItem(LocationSummary.DPD_GMA_SUBSIDY);
			if (value.equals(requiredData.get(1))) {
				dropDown.selectItem(LocationSummary.DPD_GMA_SUBSIDY, requiredData.get(0), Constants.TEXT);
			}
			value = dropDown.getSelectedItem(LocationSummary.DPD_GMA_SUBSIDY);
			CustomisedAssert.assertEquals(value, requiredData.get(0));
			foundation.scrollIntoViewElement(LocationSummary.DPD_GMA_SUBSIDY);
			locationSummary.verifyTopOffSubsidy(requiredData);
			locationSummary.verifyRolloverSubsidy(requiredData);
			if (checkBox.isChkEnabled(LocationSummary.CHK_TOP_OFF_SUBSIDY))
				checkBox.unCheck(LocationSummary.CHK_TOP_OFF_SUBSIDY);
			else if (checkBox.isChkEnabled(LocationSummary.CHK_ROLL_OVER_SUBSIDY))
				checkBox.unCheck(LocationSummary.CHK_ROLL_OVER_SUBSIDY);
			textBox.enterText(LocationSummary.TXT_TOP_OFF_GROUP_NAME, groupNames);
			textBox.enterText(LocationSummary.TXT_ROLL_OVER_GROUP_NAME, groupNames + "test");
			textBox.enterText(LocationSummary.TXT_TOP_OFF_AMOUNT, "0");
			textBox.enterText(LocationSummary.TXT_ROLL_OVER_AMOUNT, "0");

			// Setting up Special type as USConnect
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_SPECIAL_TYPE));
			dropDown.selectItem(LocationSummary.DPD_SPECIAL_TYPE, requiredData.get(8), Constants.TEXT);
			value = dropDown.getSelectedItem(LocationSummary.DPD_SPECIAL_TYPE);
			CustomisedAssert.assertEquals(value, requiredData.get(8));
			textBox.enterText(LocationSummary.TXT_PAYROLL_GROUP_NAME, groupNames + "PayRoll");
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_ERROR_MSG, Constants.SHORT_TIME);

			// Validation Subsidy for Special Type USConnect in Location Summary Page
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_GMA_SUBSIDY));
			value = dropDown.getSelectedItem(LocationSummary.DPD_GMA_SUBSIDY);
			CustomisedAssert.assertEquals(value, requiredData.get(1));
			CustomisedAssert.assertFalse(foundation.isEnabled(LocationSummary.DPD_GMA_SUBSIDY));

			// Validation Subsidy for Special Type USConnect in Consumer Summary Page
			navigationBar.navigateToMenuItem(menus.get(1));
			foundation.click(ConsumerSearch.CLEAR_SEARCH);
			dropDown.selectItem(ConsumerSearch.DPD_LOCATION, rstLocationListData.get(CNLocationList.LOCATION_NAME),
					Constants.TEXT);
			foundation.click(ConsumerSearch.BTN_GO);
			foundation.threadWait(Constants.ONE_SECOND);
			List<String> tableHeaders = consumerSearch.getConsumerHeaders();
			CustomisedAssert.assertFalse(tableHeaders.contains(requiredData.get(9)));
			foundation.click(consumerSearch.objFirstNameCell(consumerSearch.getConsumerFirstName()));
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSummary.LBL_CONSUMER_SUMMARY));
			CustomisedAssert.assertFalse(foundation.isDisplayed(ConsumerSummary.TXT_SUBSIDY_GROUP));
			CustomisedAssert.assertFalse(foundation.isDisplayed(ConsumerSummary.TXT_TOP_OFF));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// resetting Test Data
			locationSummary.resettingSpecialTypeAndSubsidy(menus.get(0),
					rstLocationListData.get(CNLocationList.LOCATION_NAME), requiredData.get(7), requiredData.get(1));
		}
	}

	@Test(description = "166068 - verify the existing GMA Subsidy field setup after selecting the Special Type as USConnect")
	public void verifyExistingSubsidyForUSConnect() {
		final String CASE_NUM = "166068";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
		rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);

		List<String> requiredData = Arrays
				.asList(rstLocationSummaryData.get(CNLocationSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> menus = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		String groupNames = strings.getRandomCharacter();

		try {

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Select Menu, Menu Item and Location
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menus.get(0));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));

			// Setting up Subsidy data on the Location level
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_MULTI_TAX_REPORT));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_GMA_SUBSIDY));
			String value = dropDown.getSelectedItem(LocationSummary.DPD_GMA_SUBSIDY);
			if (value.equals(requiredData.get(1))) {
				dropDown.selectItem(LocationSummary.DPD_GMA_SUBSIDY, requiredData.get(0), Constants.TEXT);
			}
			value = dropDown.getSelectedItem(LocationSummary.DPD_GMA_SUBSIDY);
			CustomisedAssert.assertEquals(value, requiredData.get(0));
			foundation.scrollIntoViewElement(LocationSummary.DPD_GMA_SUBSIDY);
			locationSummary.verifyTopOffSubsidy(requiredData);
			locationSummary.verifyRolloverSubsidy(requiredData);
			if (checkBox.isChkEnabled(LocationSummary.CHK_TOP_OFF_SUBSIDY))
				checkBox.check(LocationSummary.CHK_TOP_OFF_SUBSIDY);
			else if (checkBox.isChkEnabled(LocationSummary.CHK_ROLL_OVER_SUBSIDY))
				checkBox.unCheck(LocationSummary.CHK_ROLL_OVER_SUBSIDY);
			textBox.enterText(LocationSummary.TXT_TOP_OFF_GROUP_NAME, groupNames);
			textBox.enterText(LocationSummary.TXT_ROLL_OVER_GROUP_NAME, groupNames + "test");
			textBox.enterText(LocationSummary.TXT_TOP_OFF_AMOUNT, "20");
			textBox.enterText(LocationSummary.TXT_ROLL_OVER_AMOUNT, "0");
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_ERROR_MSG, Constants.SHORT_TIME);

			// Setting up Special type as USConnect
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_SPECIAL_TYPE));
			dropDown.selectItem(LocationSummary.DPD_SPECIAL_TYPE, requiredData.get(8), Constants.TEXT);
			value = dropDown.getSelectedItem(LocationSummary.DPD_SPECIAL_TYPE);
			CustomisedAssert.assertEquals(value, requiredData.get(8));
			textBox.enterText(LocationSummary.TXT_PAYROLL_GROUP_NAME, groupNames + "PayRoll");
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_ERROR_MSG, Constants.SHORT_TIME);

			// Validation Subsidy for Special Type USConnect in Location Summary Page
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_GMA_SUBSIDY));
			value = dropDown.getSelectedItem(LocationSummary.DPD_GMA_SUBSIDY);
			CustomisedAssert.assertEquals(value, requiredData.get(1));
			CustomisedAssert.assertFalse(foundation.isEnabled(LocationSummary.DPD_GMA_SUBSIDY));

			// Validation Subsidy for Special Type USConnect in Consumer Summary Page
			navigationBar.navigateToMenuItem(menus.get(1));
			foundation.click(ConsumerSearch.CLEAR_SEARCH);
			dropDown.selectItem(ConsumerSearch.DPD_LOCATION, rstLocationListData.get(CNLocationList.LOCATION_NAME),
					Constants.TEXT);
			foundation.click(ConsumerSearch.BTN_GO);
			foundation.threadWait(Constants.ONE_SECOND);
			List<String> tableHeaders = consumerSearch.getConsumerHeaders();
			CustomisedAssert.assertFalse(tableHeaders.contains(requiredData.get(9)));
			foundation.click(consumerSearch.objFirstNameCell(consumerSearch.getConsumerFirstName()));
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSummary.LBL_CONSUMER_SUMMARY));
			CustomisedAssert.assertFalse(foundation.isDisplayed(ConsumerSummary.TXT_SUBSIDY_GROUP));
			CustomisedAssert.assertFalse(foundation.isDisplayed(ConsumerSummary.TXT_TOP_OFF));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// resetting Test Data
			locationSummary.resettingSpecialTypeAndSubsidy(menus.get(0),
					rstLocationListData.get(CNLocationList.LOCATION_NAME), requiredData.get(7), requiredData.get(1));
		}
	}

	@Test(description = "166014 - verify Subsidy group is set to 'No' in Location Summary page."
			+ "165948 - Verify the 'SUBSIDY GROUP' and Hint text '- SELECT SUBSIDY GROUP -' in Consumer Search")
	public void verifySubsidyGroupWithOperator() {
		final String CASE_NUM = "166014";

		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
		rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);

		List<String> requiredData = Arrays
				.asList(rstLocationSummaryData.get(CNLocationSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> menus = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));

		try {
			// Login to ADM
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Setting GMA Subsidy OFF
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			locationSummary.checkSubsidy(menus.get(0), rstLocationListData.get(CNLocationList.LOCATION_NAME),
					requiredData.get(1));

			// Verify GMA Subsidy column in Consumer Summary Page
			consumerSearch.searchConsumer(menus.get(1), rstLocationListData.get(CNLocationList.LOCATION_NAME));
			List<String> consumerHeaders = consumerSearch.getConsumerHeaders();
			CustomisedAssert.assertFalse(consumerHeaders.contains(requiredData.get(2)));
			foundation.click(consumerSearch.objFirstNameCell(consumerSearch.getConsumerFirstName()));
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSummary.LBL_CONSUMER_SUMMARY));
			CustomisedAssert.assertFalse(foundation.isDisplayed(ConsumerSummary.TXT_SUBSIDY_GROUP));

			// Setting GMA Subsidy ON
			locationSummary.checkSubsidy(menus.get(0), rstLocationListData.get(CNLocationList.LOCATION_NAME),
					requiredData.get(1));
			dropDown.selectItem(LocationSummary.DPD_GMA_SUBSIDY, requiredData.get(0), Constants.TEXT);
			if (checkBox.isChkEnabled(LocationSummary.CHK_TOP_OFF_SUBSIDY))
				checkBox.unCheck(LocationSummary.CHK_TOP_OFF_SUBSIDY);
			textBox.enterText(LocationSummary.TXT_TOP_OFF_GROUP_NAME, requiredData.get(4));
			if (checkBox.isChkEnabled(LocationSummary.CHK_ROLL_OVER_SUBSIDY))
				checkBox.unCheck(LocationSummary.CHK_ROLL_OVER_SUBSIDY);
			textBox.enterText(LocationSummary.TXT_ROLL_OVER_GROUP_NAME, requiredData.get(5));
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);

			// Verify GMA Subsidy column in Consumer Summary Page
			consumerSearch.searchConsumer(menus.get(1), rstLocationListData.get(CNLocationList.LOCATION_NAME));
			consumerHeaders = consumerSearch.getConsumerHeaders();
			CustomisedAssert.assertTrue(consumerHeaders.contains(requiredData.get(2)));
			foundation.click(consumerSearch.objFirstNameCell(consumerSearch.getConsumerFirstName()));
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSummary.LBL_CONSUMER_SUMMARY));
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSummary.TXT_SUBSIDY_GROUP));
			String hintText = dropDown.getSelectedItem(ConsumerSummary.DPD_SUBSIDY_GROUP_NAME);
			CustomisedAssert.assertEquals(hintText, requiredData.get(3));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {

			// resetting test data
			locationSummary.subsidyResettingOff(menus.get(0), rstLocationListData.get(CNLocationList.LOCATION_NAME),
					requiredData.get(1));
		}
	}

	@Test(description = "165954 - To Verify to handle a Payout and Close event to GMA account")
	public void verifyPayoutCloseEventToGMAAccount() {
		final String CASE_NUM = "165954";

		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
		rstGmaUser = dataBase.getGmaUserData(Queries.GMA_USER, CASE_NUM);
		rstLoadProduct = dataBase.getLoadProductData(Queries.LOAD_PRODUCT, CASE_NUM);
		rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);
		rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);
		rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

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
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Setting GMA Subsidy ON with Group Names and Amount for TOP Off Subsidy
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menus.get(1));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			String value = dropDown.getSelectedItem(LocationSummary.DPD_GMA_SUBSIDY);
			if (value.equals(requiredData.get(1))) {
				dropDown.selectItem(LocationSummary.DPD_GMA_SUBSIDY, requiredData.get(0), Constants.TEXT);
			}
			value = dropDown.getSelectedItem(LocationSummary.DPD_GMA_SUBSIDY);
			CustomisedAssert.assertEquals(value, requiredData.get(0));
			foundation.scrollIntoViewElement(LocationSummary.DPD_GMA_SUBSIDY);
			if (checkBox.isChkEnabled(LocationSummary.CHK_TOP_OFF_SUBSIDY))
				checkBox.check(LocationSummary.CHK_TOP_OFF_SUBSIDY);
			if (checkBox.isChkEnabled(LocationSummary.CHK_ROLL_OVER_SUBSIDY))
				checkBox.unCheck(LocationSummary.CHK_ROLL_OVER_SUBSIDY);
			locationSummary.enterSubsidyAmount(requiredData.get(4), requiredData.get(5));
			locationSummary.enterSubsidyGroupNames(requiredData.get(2), requiredData.get(3));
			login.logout();

			// Login into SOS application
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.SOS_CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.sosLogin(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(SOSHome.LANDING_PAGE_HEADING));

			// select Organization and navigate to menu
			sosHome.selectOrginazation(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menus.get(0));
			CustomisedAssert.assertTrue(foundation.isDisplayed(SOSHome.PAGE_HEADING));

			// Creating Consumer with Subsidy Group via Template in SOS Load
			String requiredStringData = (requiredString + Constants.DELIMITER_HASH + requiredData.get(2));
			excel.writeToExcel(FilePath.GMA_ACCOUNT_TEMPLATE, rstGmaUser.get(CNGmaUser.SHEET_NAME),
					rstProductSummaryData.get(CNProductSummary.ITERATION_COUNT), requiredStringData);
			loadGma.gMAUser(rstLocationListData.get(CNLocationList.LOCATION_NAME), rstGmaUser.get(CNGmaUser.PIN_VALUE),
					rstGmaUser.get(CNGmaUser.START_BALANCE), FilePath.GMA_ACCOUNT_TEMPLATE,
					rstLoadProduct.get(CNLoadProduct.DELETE_EXISTING_PRODUCT));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LoadGMA.LBL_SUCCESS));
			sosHome.logout();

			// Again Login to ADM
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Verify GMA Subsidy column in Consumer Summary Page
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menus.get(2));
			foundation.click(ConsumerSearch.CLEAR_SEARCH);
			dropDown.selectItem(ConsumerSearch.DPD_LOCATION, rstLocationListData.get(CNLocationList.LOCATION_NAME),
					Constants.TEXT);
			foundation.click(ConsumerSearch.BTN_GO);
			foundation.threadWait(Constants.ONE_SECOND);
			foundation.click(consumerSearch.objFirstNameCell(consumerSearch.getConsumerFirstName()));
			foundation.threadWait(Constants.ONE_SECOND);

			// Validating Consumer Summary page
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSummary.LBL_CONSUMER_SUMMARY));
			// CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSummary.TXT_CONSUMER_ACCOUNT));
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSummary.TXT_SUBSIDY_TOP_OFF));
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSummary.TXT_SUBSIDY_GROUP));
			value = String.valueOf(consumerSummary.getBalance());
			CustomisedAssert.assertTrue(value.equals("25.0"));
//			value = String.valueOf(consumerSummary.getTypeBalance());
//			CustomisedAssert.assertTrue(value.equals(requiredData.get(4) + ".0"));
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSummary.BTN_PAYOUT_CLOSE));
			foundation.click(ConsumerSummary.BTN_PAYOUT_CLOSE);
			foundation.alertAccept();
			foundation.waitforElement(LocationList.TXT_RECORD_UPDATE_MSG, Constants.SHORT_TIME);

			// Verify Consumer in Consumer Search Page after Payout and Close
			foundation.click(ConsumerSearch.CLEAR_SEARCH);
			dropDown.selectItem(ConsumerSearch.DPD_LOCATION, rstLocationListData.get(CNLocationList.LOCATION_NAME),
					Constants.TEXT);
			foundation.click(ConsumerSearch.BTN_GO);
			foundation.threadWait(Constants.ONE_SECOND);
			value = foundation.getText(ConsumerSearch.LNK_RECORD);
			CustomisedAssert.assertEquals(value, requiredData.get(6));

			// Verifying the Account Adjustment Report
			navigationBar.navigateToMenuItem(menus.get(3));
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));
			reportList.selectLocation(rstLocationListData.get(CNLocationList.LOCATION_NAME));

			// run and read report
			foundation.waitforClikableElement(ReportList.BTN_RUN_REPORT, Constants.SHORT_TIME);
			foundation.click(ReportList.BTN_RUN_REPORT);
			foundation.waitforElement(AccountAdjustment.LBL_REPORT_NAME, Constants.SHORT_TIME);
			String reportName = foundation.getText(AccountAdjustment.LBL_REPORT_NAME);
			CustomisedAssert.assertTrue(reportName.contains(rstReportListData.get(CNReportList.REPORT_NAME)));
			accountAdjustment.checkForDataAvailabilyInResultTable();
			foundation.click(AccountAdjustment.TABLE_AMOUNT_SORT);

			// Storing UI data and verifying the values
			Map<String, String> uiData = accountAdjustment.getTblRecords("2");
			accountAdjustment.verifyReasonCodeAndAmount(uiData, requiredData.get(7), requiredData.get(8),
					requiredData.get(9), requiredData.get(10), requiredDatas);
			foundation.threadWait(Constants.SHORT_TIME);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {

			// resetting test data
			locationSummary.subsidyResettingOff(menus.get(1), rstLocationListData.get(CNLocationList.LOCATION_NAME),
					requiredData.get(1));
		}
	}

	@Test(description = "165973 - Verify the subsidy balance in Balance History in ADM consumer summary page after batch")
	public void verifySubsidyBalanceHistory() {
		final String CASE_NUM = "165973";

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
		String currentDate = dateAndTime.getDateAndTime(Constants.REGEX_MM_DD_YYYY, Constants.TIME_ZONE_INDIA);

		try {
			// Login to ADM
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Setting GMA Subsidy ON with Group Names and Amount for TOP Off Subsidy
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menus.get(1));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			String value = dropDown.getSelectedItem(LocationSummary.DPD_GMA_SUBSIDY);
			if (value.equals(requiredData.get(1))) {
				dropDown.selectItem(LocationSummary.DPD_GMA_SUBSIDY, requiredData.get(0), Constants.TEXT);
			}
			value = dropDown.getSelectedItem(LocationSummary.DPD_GMA_SUBSIDY);
			CustomisedAssert.assertEquals(value, requiredData.get(0));
			foundation.scrollIntoViewElement(LocationSummary.DPD_GMA_SUBSIDY);
			if (checkBox.isChkEnabled(LocationSummary.CHK_TOP_OFF_SUBSIDY))
				checkBox.check(LocationSummary.CHK_TOP_OFF_SUBSIDY);
			if (checkBox.isChkEnabled(LocationSummary.CHK_ROLL_OVER_SUBSIDY))
				checkBox.unCheck(LocationSummary.CHK_ROLL_OVER_SUBSIDY);
			locationSummary.enterSubsidyAmount(requiredData.get(4), requiredData.get(5));
			locationSummary.enterSubsidyGroupNames(requiredData.get(2), requiredData.get(3));
			login.logout();

			// Login into SOS application
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.SOS_CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.sosLogin(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(SOSHome.LANDING_PAGE_HEADING));

			// select Organization and navigate to menu
			sosHome.selectOrginazation(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menus.get(0));
			CustomisedAssert.assertTrue(foundation.isDisplayed(SOSHome.PAGE_HEADING));

			// Creating Consumer with Subsidy Group via Template in SOS Load
			String requiredStringData = (requiredString + Constants.DELIMITER_HASH + requiredData.get(2));
			excel.writeToExcel(FilePath.GMA_ACCOUNT_TEMPLATE, rstGmaUser.get(CNGmaUser.SHEET_NAME),
					rstProductSummaryData.get(CNProductSummary.ITERATION_COUNT), requiredStringData);
			loadGma.gMAUser(rstLocationListData.get(CNLocationList.LOCATION_NAME), rstGmaUser.get(CNGmaUser.PIN_VALUE),
					rstGmaUser.get(CNGmaUser.START_BALANCE), FilePath.GMA_ACCOUNT_TEMPLATE,
					rstLoadProduct.get(CNLoadProduct.DELETE_EXISTING_PRODUCT));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LoadGMA.LBL_SUCCESS));
			sosHome.logout();

			// Again Login to ADM
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Verify GMA Subsidy column in Consumer Summary Page
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menus.get(2));
			foundation.click(ConsumerSearch.CLEAR_SEARCH);
			dropDown.selectItem(ConsumerSearch.DPD_LOCATION, rstLocationListData.get(CNLocationList.LOCATION_NAME),
					Constants.TEXT);
			foundation.click(ConsumerSearch.BTN_GO);
			foundation.threadWait(Constants.ONE_SECOND);
			foundation.click(consumerSearch.objFirstNameCell(consumerSearch.getConsumerFirstName()));

			// Validating Consumer Summary page
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSummary.LBL_CONSUMER_SUMMARY));
			// CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSummary.TXT_CONSUMER_ACCOUNT));
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSummary.TXT_SUBSIDY_TOP_OFF));
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSummary.TXT_SUBSIDY_GROUP));
			String subsidyName = dropDown.getSelectedItem(ConsumerSummary.DPD_SUBSIDY_GROUP_NAME);
			CustomisedAssert.assertEquals(subsidyName, requiredData.get(7));
			value = String.valueOf(consumerSummary.getBalance());
			CustomisedAssert.assertTrue(value.equals("25.0"));
//			value = String.valueOf(consumerSummary.getTypeBalance());
//			CustomisedAssert.assertTrue(value.equals(requiredData.get(4) + ".0"));

			// Adjusting Top Off Balance
			foundation.click(ConsumerSummary.BTN_TOP_OFF_ADJUST);
			foundation.click(ConsumerSummary.TXT_ADJUST_BALANCE);
			textBox.enterText(ConsumerSummary.TXT_ADJUST_BALANCE, requiredData.get(6));
			foundation.click(ConsumerSummary.BTN_REASON_SAVE);
			foundation.click(ConsumerSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_RECORD_UPDATE_MSG, Constants.SHORT_TIME);
			foundation.click(ConsumerSummary.BTN_SAVE);

			// Verify GMA Subsidy column in Consumer Summary Page
			foundation.click(ConsumerSearch.CLEAR_SEARCH);
			dropDown.selectItem(ConsumerSearch.DPD_LOCATION, rstLocationListData.get(CNLocationList.LOCATION_NAME),
					Constants.TEXT);
			foundation.click(ConsumerSearch.BTN_GO);
			foundation.threadWait(Constants.ONE_SECOND);
			foundation.click(consumerSearch.objFirstNameCell(consumerSearch.getConsumerFirstName()));

			// Verifying Balance History
			consumerSummary.balanceHistory(requiredData);
			consumerSummary.balanceHistoryData(requiredData.get(14), currentDate);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {

			// resetting test data
			locationSummary.subsidyResettingOff(menus.get(1), rstLocationListData.get(CNLocationList.LOCATION_NAME),
					requiredData.get(1));
		}
	}

	@Test(description = "165230 -Verify whether the 'Subsidy' reason code is displayed in report"
			+ "165956-To Verify whether the Subsidy purchase reason code is displayed in report for the the consumers associate")
	public void verifySubsidyReasonCode() {
		final String CASE_NUM = "165230";

		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstconsumerSearchData = dataBase.getConsumerSearchData(Queries.CONSUMER_SEARCH, CASE_NUM);
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
		rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);
		rstConsumerSummaryData = dataBase.getConsumerSummaryData(Queries.CONSUMER_SUMMARY, CASE_NUM);

		List<String> menu = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> headData = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION).split(Constants.DELIMITER_TILD));
		List<String> requiredData = Arrays
				.asList(rstconsumerSearchData.get(CNConsumerSearch.SEARCH).split(Constants.DELIMITER_TILD));
		List<String> balance = Arrays
				.asList(rstConsumerSummaryData.get(CNConsumerSummary.ADJUST_BALANCE).split(Constants.DELIMITER_TILD));

		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Verifying GMA Subsidy field
			navigationBar.navigateToMenuItem(menu.get(0));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			foundation.isDisplayed(LocationSummary.PANTRY_TYPE);
			foundation.threadWait(Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_GMA_SUBSIDY));
			String value = dropDown.getSelectedItem(LocationSummary.DPD_GMA_SUBSIDY);
			CustomisedAssert.assertEquals(value, requiredData.get(2));

			// Navigate to Admin>consumer
			navigationBar.navigateToMenuItem(menu.get(1));
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(ConsumerSearch.CLEAR_SEARCH);
			dropDown.selectItem(ConsumerSearch.DPD_LOCATION, requiredData.get(0), Constants.TEXT);
			foundation.click(ConsumerSearch.BTN_GO);
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSearch.TBL_CONSUMERS));
			foundation.click(ConsumerSearch.LNK_FIRST_ROW);
			foundation.click(ConsumerSummary.BTN_ADJUST);
			foundation.waitforElement(ConsumerSummary.LBL_POPUP_ADJUST_BALANCE, Constants.SHORT_TIME);
			textBox.enterText(ConsumerSummary.TXT_ADJUST_BALANCE, balance.get(0));
			dropDown.selectItem(ConsumerSummary.DPD_REASON, rstConsumerSummaryData.get(CNConsumerSummary.REASON),
					Constants.TEXT);
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSummary.REF_EFT));
			foundation.click(ConsumerSummary.BTN_REASON_SAVE);
			foundation.threadWait(Constants.THREE_SECOND);
			foundation.click(ConsumerSummary.BTN_SAVE);
			foundation.threadWait(Constants.THREE_SECOND);

			// verify the bulk assign subsidy group
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSearch.TXT_CONSUMER_SEARCH));
			foundation.click(ConsumerSearch.CLEAR_SEARCH);
			dropDown.selectItem(ConsumerSearch.DPD_LOCATION, requiredData.get(0), Constants.TEXT);
			foundation.click(ConsumerSearch.BTN_GO);
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSearch.TBL_CONSUMERS));
			table.selectRow(requiredData.get(0));
			foundation.click(ConsumerSearch.ACTION_BTN);
			foundation.click(ConsumerSearch.BULK_ASSIGN_SUBSIDY_GROUP);
			foundation.isDisabled(ConsumerSearch.LBL_BULK_ASSIGN_POPUP);
			foundation.threadWait(Constants.SHORT_TIME);
			dropDown.selectItem(ConsumerSearch.SUBSIDY_GROUP, requiredData.get(1), Constants.TEXT);
			foundation.click(ConsumerSearch.BTN_SAVE);
			foundation.threadWait(Constants.SHORT_TIME);

			// Navigate to Report>Account Adjustment
			navigationBar.navigateToMenuItem(menu.get(2));
			foundation.threadWait(Constants.THREE_SECOND);
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));
			reportList.selectLocation(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.waitforClikableElement(ReportList.BTN_RUN_REPORT, Constants.SHORT_TIME);
			foundation.click(ReportList.BTN_RUN_REPORT);
			foundation.waitforElement(AccountAdjustment.LBL_REPORT_NAME, Constants.SHORT_TIME);
			String reportName = foundation.getText(AccountAdjustment.LBL_REPORT_NAME);
			CustomisedAssert.assertTrue(reportName.contains(rstReportListData.get(CNReportList.REPORT_NAME)));
			accountAdjustment.checkForDataAvailabilyInResultTable();
			foundation.isDisplayed(AccountAdjustment.DATE_COLUMN);
			foundation.threadWait(Constants.ONE_SECOND);
			foundation.click(AccountAdjustment.REP_DATE);
			accountAdjustment.verifyHeaderData(headData);
			CustomisedAssert.assertTrue(foundation.isDisplayed(AccountAdjustment.COLUMN_ROWDATA));
			foundation.threadWait(Constants.SHORT_TIME);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			consumerSummary.balanceResettingData(menu.get(1), requiredData.get(0), balance.get(1),
					rstConsumerSummaryData.get(CNConsumerSummary.REASON));
		}
	}

	@Test(description = "166062- verify the reason code for the consumer subsidy in account adjustment report")
	public void verifyReasonCodeAfterTranscationFromDevice() {
		final String CASE_NUM = "166062";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
		rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		rstconsumerSearchData = dataBase.getConsumerSearchData(Queries.CONSUMER_SEARCH, CASE_NUM);
		rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);
		rstConsumerSummaryData = dataBase.getConsumerSummaryData(Queries.CONSUMER_SUMMARY, CASE_NUM);

		List<String> menu = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> requiredData = Arrays
				.asList(rstLocationSummaryData.get(CNLocationSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		String currentDate = dateAndTime.getDateAndTime(Constants.REGEX_MM_DD_YYYY, Constants.TIME_ZONE_INDIA);
		List<String> orderPageData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.ORDER_PAGE).split(Constants.DELIMITER_TILD));
		List<String> Balance = Arrays
				.asList(rstConsumerSummaryData.get(CNConsumerSummary.ADJUST_BALANCE).split(Constants.DELIMITER_TILD));
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Select Menu, Menu Item and Location
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menu.get(0));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));

			// Verifying the selection of defaults for GMA subsidy
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			foundation.threadWait(Constants.THREE_SECOND);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_GMA_SUBSIDY));
			List<String> values = dropDown.getAllItems(LocationSummary.DPD_GMA_SUBSIDY);
			List<String> expectedValues = new ArrayList<String>();
			expectedValues.add(requiredData.get(1));
			expectedValues.add(requiredData.get(0));
			CustomisedAssert.assertTrue(values.equals(expectedValues));
			dropDown.selectItem(LocationSummary.DPD_GMA_SUBSIDY, requiredData.get(0), Constants.TEXT);
			locationSummary.verifyTopOffSubsidy(requiredData);
			locationSummary.verifyRolloverSubsidy(requiredData);
			checkBox.check(LocationSummary.CHK_TOP_OFF_SUBSIDY);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(LocationSummary.START_DATE_PICKER_TOP_OFF);
			locationSummary.verifyTopOffDateAutomationLocation1(currentDate);
			dropDown.selectItem(LocationSummary.DPD_TOP_OFF_RECURRENCE, requiredData.get(8), Constants.TEXT);
			textBox.enterText(LocationSummary.TXT_TOP_OFF_GROUP_NAME, requiredData.get(7));
			textBox.enterText(LocationSummary.TXT_TOP_OFF_AMOUNT, requiredData.get(9));
			foundation.threadWait(Constants.SHORT_TIME);
			textBox.enterText(LocationSummary.TXT_ROLL_OVER_GROUP_NAME, requiredData.get(4));
			foundation.click(LocationSummary.BTN_SYNC);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			foundation.threadWait(Constants.SHORT_TIME);

			// Navigate to Admin>Consumer
			navigationBar.navigateToMenuItem(menu.get(2));
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSearch.TXT_CONSUMER_SEARCH));
			foundation.click(ConsumerSearch.CLEAR_SEARCH);
			textBox.enterText(ConsumerSearch.TXT_SEARCH, rstconsumerSearchData.get(CNConsumerSearch.SEARCH));
			dropDown.selectItem(ConsumerSearch.DPD_LOCATION, rstLocationListData.get(CNLocationList.LOCATION_NAME),
					Constants.TEXT);
			foundation.click(ConsumerSearch.BTN_GO);
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSearch.TBL_CONSUMERS));
			foundation.click(ConsumerSearch.LNK_FIRST_ROW);
			foundation.click(ConsumerSummary.BTN_ADJUST);
			foundation.waitforElement(ConsumerSummary.LBL_POPUP_ADJUST_BALANCE, Constants.SHORT_TIME);
			textBox.enterText(ConsumerSummary.TXT_ADJUST_BALANCE, Balance.get(1));
			dropDown.selectItem(ConsumerSummary.DPD_REASON, rstConsumerSummaryData.get(CNConsumerSummary.REASON),
					Constants.TEXT);
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSummary.REF_EFT));
			foundation.click(ConsumerSummary.BTN_REASON_SAVE);
			foundation.threadWait(Constants.THREE_SECOND);
			foundation.click(ConsumerSummary.BTN_SAVE);
			foundation.threadWait(Constants.THREE_SECOND);
			browser.close();

			// Launch V5 Device
			// foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LandingPage.IMG_SEARCH_ICON));
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
			foundation.click(ProductSearch.BTN_PRODUCT);
			CustomisedAssert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(0))));
			foundation.click(Payments.BTN_EMAIL_ACCOUNT);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(Payments.EMAIL_lOGIN_BTN);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.EMAIL_ID));
			foundation.click(AccountLogin.BTN_NEXT);
			foundation.waitforElement(AccountLogin.BTN_PIN_NEXT, Constants.SHORT_TIME);
			foundation.threadWait(Constants.ONE_SECOND);
			textBox.enterPin(rstV5DeviceData.get(CNV5Device.PIN));
			foundation.click(AccountLogin.BTN_PIN_NEXT);
			CustomisedAssert
					.assertTrue(foundation.isDisplayed(order.objText(rstV5DeviceData.get(CNV5Device.PAYMENTS_PAGE))));
			browser.close();

			// Navigate to Reports
			// browser.close();
			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));
			navigationBar.navigateToMenuItem(menu.get(1));
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));
			foundation.threadWait(3);
			reportList.selectLocation(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.waitforClikableElement(ReportList.BTN_RUN_REPORT, Constants.SHORT_TIME);
			foundation.click(ReportList.BTN_RUN_REPORT);
			foundation.waitforElement(AccountAdjustment.LBL_REPORT_NAME, Constants.SHORT_TIME);
			String reportName = foundation.getText(AccountAdjustment.LBL_REPORT_NAME);
			CustomisedAssert.assertTrue(reportName.contains(rstReportListData.get(CNReportList.REPORT_NAME)));
			accountAdjustment.checkForDataAvailabilyInResultTable();
			CustomisedAssert.assertTrue(foundation.isDisplayed(AccountAdjustment.REASON_CODE));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "165231- To Verify whether the 'Subsidy' reason code is displayed in report after completing the transaction"
			+ "166953-To Verify whether the 'Subsidy' reason code is displayed after funding the subsidy balance")
	public void verifyReasonCodeInReport() {
		final String CASE_NUM = "165231";

		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
		rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		rstconsumerSearchData = dataBase.getConsumerSearchData(Queries.CONSUMER_SEARCH, CASE_NUM);
		rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);
		rstConsumerSummaryData = dataBase.getConsumerSummaryData(Queries.CONSUMER_SUMMARY, CASE_NUM);

		List<String> headData = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION).split(Constants.DELIMITER_TILD));
		List<String> menu = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> requiredData = Arrays
				.asList(rstLocationSummaryData.get(CNLocationSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		String currentDate = dateAndTime.getDateAndTime(Constants.REGEX_MM_DD_YYYY, Constants.TIME_ZONE_INDIA);
		List<String> orderPageData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.ORDER_PAGE).split(Constants.DELIMITER_TILD));
		try {

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Select Menu, Menu Item and Location
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menu.get(0));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));

			// Verifying the selection of defaults for GMA subsidy
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_GMA_SUBSIDY));
			String value = dropDown.getSelectedItem(LocationSummary.DPD_GMA_SUBSIDY);
			CustomisedAssert.assertEquals(value, requiredData.get(0));
			checkBox.check(LocationSummary.CHK_TOP_OFF_SUBSIDY);
			foundation.threadWait(Constants.ONE_SECOND);
			foundation.click(LocationSummary.START_DATE_PICKER_TOP_OFF_1);
			locationSummary.verifyTopOffDateAutomationLocation1(currentDate);
			dropDown.selectItem(LocationSummary.DPD_TOP_OFF_RECURRENCE, requiredData.get(3), Constants.TEXT);
			textBox.enterText(LocationSummary.TXT_TOP_OFF_GROUP_NAME, requiredData.get(2));
			textBox.enterText(LocationSummary.TXT_TOP_OFF_AMOUNT, requiredData.get(4));
			foundation.click(LocationSummary.BTN_SYNC);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			foundation.threadWait(Constants.SHORT_TIME);

			// Navigate to Admin>consumer
			navigationBar.navigateToMenuItem(menu.get(1));
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSearch.TXT_CONSUMER_SEARCH));
			foundation.click(ConsumerSearch.CLEAR_SEARCH);
			textBox.enterText(ConsumerSearch.TXT_SEARCH, rstconsumerSearchData.get(CNConsumerSearch.SEARCH));
			dropDown.selectItem(ConsumerSearch.DPD_LOCATION, rstLocationListData.get(CNLocationList.LOCATION_NAME),
					Constants.TEXT);
			foundation.click(ConsumerSearch.BTN_GO);
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSearch.TBL_CONSUMERS));
			table.selectRow(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(ConsumerSearch.ACTION_BTN);
			foundation.click(ConsumerSearch.BULK_ASSIGN_SUBSIDY_GROUP);
			foundation.isDisabled(ConsumerSearch.LBL_BULK_ASSIGN_POPUP);
			foundation.threadWait(Constants.SHORT_TIME);
			dropDown.selectItem(ConsumerSearch.SUBSIDY_GROUP, rstconsumerSearchData.get(CNConsumerSearch.TITLE),
					Constants.TEXT);
			foundation.click(ConsumerSearch.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_RECORD_UPDATE_MSG, Constants.SHORT_TIME);
			login.logout();
			browser.close();

			// Launch V5 Device,Product Search,Transaction Done
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LandingPage.IMG_SEARCH_ICON));
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
			foundation.click(ProductSearch.BTN_PRODUCT);
			CustomisedAssert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(0))));
			foundation.waitforElement(Payments.BTN_EMAIL_ACCOUNT, Constants.SHORT_TIME);
			foundation.click(Payments.BTN_EMAIL_ACCOUNT);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(Payments.EMAIL_lOGIN_BTN);
			foundation.threadWait(Constants.SHORT_TIME);
//			foundation.click(Payments.EMAIL);
//			foundation.waitforElement(Payments.EMAIL_LOGIN_TXT, Constants.ONE_SECOND);
//			foundation.click(Payments.BTN_EMAIL_LOGIN);
//			foundation.threadWait(Constants.ONE_SECOND);
			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.EMAIL_ID));
			foundation.click(AccountLogin.BTN_NEXT);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.waitforElement(AccountLogin.BTN_PIN_NEXT, Constants.SHORT_TIME);
			textBox.enterPin(rstV5DeviceData.get(CNV5Device.PIN));
			foundation.click(AccountLogin.BTN_PIN_NEXT);
			CustomisedAssert
					.assertTrue(foundation.isDisplayed(order.objText(rstV5DeviceData.get(CNV5Device.PAYMENTS_PAGE))));

			// Navigate to Reports
			browser.close();
			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));
			navigationBar.navigateToMenuItem(menu.get(2));
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));
			reportList.selectLocation(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.waitforClikableElement(ReportList.BTN_RUN_REPORT, Constants.SHORT_TIME);
			foundation.click(ReportList.BTN_RUN_REPORT);
			foundation.waitforElement(AccountAdjustment.LBL_REPORT_NAME, Constants.SHORT_TIME);
			String reportName = foundation.getText(AccountAdjustment.LBL_REPORT_NAME);
			CustomisedAssert.assertTrue(reportName.contains(rstReportListData.get(CNReportList.REPORT_NAME)));
			accountAdjustment.checkForDataAvailabilyInResultTable();
			foundation.isDisplayed(AccountAdjustment.DATE_COLUMN);
			foundation.isDisplayed(AccountAdjustment.ACTION_COLUMN);
			foundation.threadWait(Constants.ONE_SECOND);
			foundation.click(AccountAdjustment.REP_DATE);
			accountAdjustment.verifyHeaderData(headData);
			CustomisedAssert.assertTrue(foundation.isDisplayed(AccountAdjustment.COLUMN_ROWDATA));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {

			consumerSummary.balanceResettingDataInAutomationLocation1(menu.get(1),
					rstconsumerSearchData.get(CNConsumerSearch.SEARCH),
					rstLocationListData.get(CNLocationList.LOCATION_NAME),
					rstConsumerSummaryData.get(CNConsumerSummary.ADJUST_BALANCE),
					rstConsumerSummaryData.get(CNConsumerSummary.REASON));
			browser.close();
		}
	}

	@Test(description = "165951- Verify to views the 'Bulk Assign Subsidy Group' prompt in Consumer Search page."
			+ "165953-To Verify the bulk assigns a subsidy group to one or more consumer accounts.")
	public void verifyBulkAssignSubsidyGroup() {
		final String CASE_NUM = "165953";

		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstconsumerSearchData = dataBase.getConsumerSearchData(Queries.CONSUMER_SEARCH, CASE_NUM);
		rstConsumerSummaryData = dataBase.getConsumerSummaryData(Queries.CONSUMER_SUMMARY, CASE_NUM);

		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Navigate to menuItem and verify GMA Subsidy group
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			foundation.click(ConsumerSearch.CLEAR_SEARCH);
			consumerSearch.BulkAssignSubsidyGroupInMoreThanTwoGrid(rstconsumerSearchData.get(CNConsumerSearch.LOCATION),
					rstconsumerSearchData.get(CNConsumerSearch.SEARCH),
					rstconsumerSearchData.get(CNConsumerSearch.SEARCH_BY));
			foundation.click(ConsumerSearch.BULK_ASSIGN_SUBSIDY_GROUP);
			foundation.threadWait(Constants.SHORT_TIME);

			// Verify Cancel button in BulkAssignSubsidyGroup
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSearch.LBL_BULK_ASSIGN_POPUP));
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSearch.SUBSIDY_GROUP));
			dropDown.selectItem(ConsumerSearch.SUBSIDY_GROUP, rstconsumerSearchData.get(CNConsumerSearch.ACTIONS),
					Constants.TEXT);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(ConsumerSearch.RSN_CANCEL);

			// Verify Save button in BulkAssignSubsidyGroup
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSearch.TXT_CONSUMER_SEARCH));
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(ConsumerSearch.ACTION_BTN);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(ConsumerSearch.BULK_ASSIGN_SUBSIDY_GROUP);
			foundation.threadWait(Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSearch.LBL_BULK_ASSIGN_POPUP));
			dropDown.selectItem(ConsumerSearch.SUBSIDY_GROUP, rstconsumerSearchData.get(CNConsumerSearch.ACTIONS),
					Constants.TEXT);
			foundation.click(ConsumerSearch.BTN_SAVE);

			// verify Grid in Consumer search
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(ConsumerSearch.LNK_FIRST_ROW);
			foundation.waitforElementToBeVisible(ConsumerSummary.DPD_SUBSIDY_GROUP_NAME, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSummary.DPD_SUBSIDY_GROUP_NAME));
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSummary.LBL_READ_BALANCE));
			foundation.click(ConsumerSummary.CANCEL_BTN);
			foundation.threadWait(Constants.THREE_SECOND);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "165947- Verify to views the 'Bulk Assign Subsidy Group' prompt in Consumer Search page")
	public void verifyExportButtonInActions() {
		final String CASE_NUM = "165947";

		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstconsumerSearchData = dataBase.getConsumerSearchData(Queries.CONSUMER_SEARCH, CASE_NUM);
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);

		List<String> menus = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));

		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Navigate to menu item and verify GMA Subsidy
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));
			navigationBar.navigateToMenuItem(menus.get(0));
			foundation.threadWait(Constants.ONE_SECOND);
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			foundation.threadWait(Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_GMA_SUBSIDY));
			String value = dropDown.getSelectedItem(LocationSummary.DPD_GMA_SUBSIDY);
			CustomisedAssert.assertEquals(value, rstLocationListData.get(CNLocationList.INFO_MESSAGE));

			// Navigate to Admin>Consumer
			navigationBar.navigateToMenuItem(menus.get(1));
			foundation.click(ConsumerSearch.CLEAR_SEARCH);
			dropDown.selectItem(ConsumerSearch.DPD_LOCATION, rstconsumerSearchData.get(CNConsumerSearch.LOCATION),
					Constants.TEXT);
			foundation.click(ConsumerSearch.BTN_GO);
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSearch.TBL_CONSUMERS));
			table.selectRow(rstconsumerSearchData.get(CNConsumerSearch.SEARCH));
			foundation.click(ConsumerSearch.ACTION_BTN);

			// Verify the Subsidy group in Action>Export
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(ConsumerSearch.BTN_EXPORT);
			foundation.threadWait(Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(excel.isFileDownloaded(FilePath.EXCEL_CONSUMER));
			foundation.threadWait(Constants.SHORT_TIME);
			Map<String, String> excelData = excel.getExcelData(FilePath.EXCEL_CONSUMER,
					rstconsumerSearchData.get(CNConsumerSearch.ACTIONS));
			foundation.threadWait(Constants.SHORT_TIME);
			List<String> actualColumnNames = new ArrayList<String>(excelData.keySet());
			CustomisedAssert.assertTrue(
					actualColumnNames.get(2).equals(rstconsumerSearchData.get(CNConsumerSearch.COLUMN_NAME)));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// delete files
			foundation.deleteFile(FilePath.EXCEL_CONSUMER);
		}
	}

	@Test(description = "168121-Verify the default option for the subsidy group")
	public void verifyDefaultOptionInSubsidyGroupDropdown() {
		final String CASE_NUM = "168121";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);

		List<String> menus = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> datas = Arrays
				.asList(rstLocationListData.get(CNLocationList.SHOW_RECORDS).split(Constants.DELIMITER_TILD));
		String currentDate = dateAndTime.getDateAndTime(Constants.REGEX_MM_DD_YYYY, Constants.TIME_ZONE_INDIA);
		try {
			// Login to ADM
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Navigate to Location and verify GMA Subsidy on
			navigationBar.navigateToMenuItem(menus.get(0));
			foundation.threadWait(Constants.ONE_SECOND);
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_GMA_SUBSIDY));
			String value = dropDown.getSelectedItem(LocationSummary.DPD_GMA_SUBSIDY);
			if (value.equals(datas.get(1))) {
				dropDown.selectItem(LocationSummary.DPD_GMA_SUBSIDY, datas.get(0), Constants.TEXT);
			}
			value = dropDown.getSelectedItem(LocationSummary.DPD_GMA_SUBSIDY);
			CustomisedAssert.assertEquals(value, datas.get(0));
			foundation.scrollIntoViewElement(LocationSummary.DPD_GMA_SUBSIDY);
			checkBox.check(LocationSummary.CHK_TOP_OFF_SUBSIDY);
			foundation.threadWait(Constants.ONE_SECOND);
			foundation.click(LocationSummary.START_DATE_PICKER_TOP_OFF_1);
			locationSummary.verifyTopOffDateAutomationNewLocation(currentDate);
			dropDown.selectItem(LocationSummary.DPD_TOP_OFF_RECURRENCE, datas.get(1), Constants.TEXT);
			textBox.enterText(LocationSummary.TXT_TOP_OFF_GROUP_NAME, datas.get(2));
			textBox.enterText(LocationSummary.TXT_TOP_OFF_AMOUNT, datas.get(3));
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			foundation.threadWait(Constants.SHORT_TIME);

			// Navigate to Admin>Consumer
			navigationBar.navigateToMenuItem(menus.get(1));
			foundation.click(ConsumerSearch.CLEAR_SEARCH);
			dropDown.selectItem(ConsumerSearch.DPD_LOCATION, rstLocationListData.get(CNLocationList.LOCATION_NAME),
					Constants.TEXT);
			foundation.click(ConsumerSearch.BTN_GO);
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSearch.TBL_CONSUMERS));
			foundation.click(ConsumerSearch.LNK_FIRST_ROW);
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSummary.DPD_SUBSIDY_GROUP_NAME));
			foundation.threadWait(Constants.SHORT_TIME);
			dropDown.selectItem(ConsumerSummary.DPD_SUBSIDY_GROUP_NAME, datas.get(4), Constants.TEXT);
			foundation.click(ConsumerSummary.BTN_SAVE);

			// Navige to Admin>Consumer to verify the RollOver Subsidy
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(ConsumerSearch.CLEAR_SEARCH);
			dropDown.selectItem(ConsumerSearch.DPD_LOCATION, rstLocationListData.get(CNLocationList.LOCATION_NAME),
					Constants.TEXT);
			foundation.click(ConsumerSearch.BTN_GO);
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSearch.TBL_CONSUMERS));
			foundation.click(ConsumerSearch.LNK_FIRST_ROW);
			foundation.threadWait(Constants.THREE_SECOND);
			foundation.click(ConsumerSummary.CANCEL_BTN);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(ConsumerSearch.CLEAR_SEARCH);
			dropDown.selectItem(ConsumerSearch.DPD_LOCATION, rstLocationListData.get(CNLocationList.LOCATION_NAME),
					Constants.TEXT);
			foundation.threadWait(5);
			foundation.click(ConsumerSearch.BTN_GO);
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSearch.TBL_CONSUMERS));
			foundation.click(ConsumerSearch.LNK_FIRST_ROW);
			foundation.threadWait(Constants.SHORT_TIME);
			dropDown.selectItem(ConsumerSummary.DPD_SUBSIDY_GROUP_NAME, datas.get(5), Constants.TEXT);
			foundation.threadWait(Constants.ONE_SECOND);
			foundation.click(ConsumerSummary.BTN_SAVE);
			foundation.waitforElement(ConsumerSummary.TXT_SPINNER_MSG, Constants.SHORT_TIME);
		}
	}

	@Test(description = "196261-Adm-consumer subsidy - Any changes made in location summary is effective top off balance in consumer summary")
	public void verifyAnyChangesMadeInLocationSummaryIsEffectiveTopOffBalanceInConsumerSummary() {
		final String CASE_NUM = "196261";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);

		List<String> menus = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> datas = Arrays
				.asList(rstLocationListData.get(CNLocationList.SHOW_RECORDS).split(Constants.DELIMITER_TILD));
		try {
			// Login to ADM,Enable GMA Subsidy and turn on pde
			locationList.navigateMenuAndMenuItem(menus.get(0), rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_GMA_SUBSIDY));
			String value = dropDown.getSelectedItem(LocationSummary.DPD_GMA_SUBSIDY);
			CustomisedAssert.assertEquals(value, datas.get(0));
			foundation.waitforElementToBeVisible(LocationSummary.DPD_PAYROLL, Constants.SHORT_TIME);
			dropDown.selectItem(LocationSummary.DPD_PAYROLL, datas.get(0), Constants.TEXT);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElementToBeVisible(LocationList.LBL_LOCATION_LIST, Constants.SHORT_TIME);

			// Navigate to Admin-->Consumer
			navigationBar.navigateToMenuItem(menus.get(1));
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSearch.TXT_CONSUMER_SEARCH));
			foundation.click(ConsumerSearch.CLEAR_SEARCH);
			dropDown.selectItem(ConsumerSearch.DPD_LOCATION, rstLocationListData.get(CNLocationList.LOCATION_NAME),
					Constants.TEXT);
			foundation.click(ConsumerSearch.BTN_GO);

			// Navigate to Consumer Summary and update the balance in top off subsidy
			consumerSummary.clickOnConsumerAndUpdateBalanceInTopOffSubsidy(datas.get(1));

			// verify the updated balance in top off subsidy field
			consumerSummary.clickOnConsumerAndVerrifyBalanceInTopOffSubsidy(
					rstLocationListData.get(CNLocationList.LOCATION_NAME), datas.get(2));

			// Turn off pde
			navigationBar.navigateToMenuItem(menus.get(0));
			foundation.threadWait(Constants.ONE_SECOND);
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			foundation.waitforElementToBeVisible(LocationSummary.DPD_PAYROLL, Constants.SHORT_TIME);
			dropDown.selectItem(LocationSummary.DPD_PAYROLL, datas.get(3), Constants.TEXT);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElementToBeVisible(LocationList.LBL_LOCATION_LIST, Constants.SHORT_TIME);

			// verify the updated balance in top off subsidy field
			navigationBar.navigateToMenuItem(menus.get(1));
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSearch.TXT_CONSUMER_SEARCH));
			foundation.click(ConsumerSearch.CLEAR_SEARCH);
			dropDown.selectItem(ConsumerSearch.DPD_LOCATION, rstLocationListData.get(CNLocationList.LOCATION_NAME),
					Constants.TEXT);
			foundation.click(ConsumerSearch.BTN_GO);
			consumerSummary.clickOnConsumerAndVerrifyBalanceInTopOffSubsidy(
					rstLocationListData.get(CNLocationList.LOCATION_NAME), datas.get(2));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// Resetting balance in top off subsidy
			consumerSummary.setTopOffSubsidyBalance(datas.get(4));
		}
	}

	/**
	 * @author karthikr SOS-32030
	 * @date - 04/07/2022
	 */
	@Test(description = "197692 - verify the consumer subsidy balance funding when the start date is set as current date and recurrence set to 'Daily'")
	public void verifyConsumerSubsidyBalanceWhenSetToCurrentDateWithRecurrenceDaily() {
		final String CASE_NUM = "197692";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);

		List<String> menus = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> requiredData = Arrays
				.asList(rstLocationSummaryData.get(CNLocationSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		String currentDate = dateAndTime.getDateAndTime(Constants.REGEX_MM_DD_YYYY, Constants.TIME_ZONE_INDIA);
		try {
			// Login to ADM as Super, Navigate to Location and select GMA subsidy to Verify
			// TopOff Subsidy
			// Login to ADM as Super
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Select Menu, Menu Item and Location
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menus.get(0));
			locationList.selectLocationName(rstLocationSummaryData.get(CNLocationSummary.NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);

			// Verify GMA Subsidy
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_GMA_SUBSIDY));
			String value = dropDown.getSelectedItem(LocationSummary.DPD_GMA_SUBSIDY);
			if (value.equals(requiredData.get(1))) {
				dropDown.selectItem(LocationSummary.DPD_GMA_SUBSIDY, requiredData.get(0), Constants.TEXT);
			}

			foundation.waitforElementToBeVisible(LocationSummary.CHK_DEFAULT_ROLL_OVER, Constants.THREE_SECOND);
			checkBox.check(LocationSummary.ROLL_OVER_CHECKBOX);
			foundation.threadWait(Constants.THREE_SECOND);

			// update roll over
			foundation.click(CreateLocation.DATE_PICKER_ROLL_OVER);
			locationSummary.verifyRollOverDateLocation1(currentDate);
			locationSummary.verifyGMASubsidy(LocationSummary.DPD_ROLL_OVER_RECURRENCE,
					LocationSummary.TXT_ROLL_OVER_GROUP_NAME, LocationSummary.TXT_ROLL_OVER_AMOUNT, requiredData.get(8),
					requiredData.get(7), requiredData.get(11));
			foundation.threadWait(Constants.SHORT_TIME);
			textBox.enterText(LocationSummary.TXT_TOP_OFF_GROUP_NAME, requiredData.get(10));
			foundation.waitforElementToBeVisible(LocationSummary.BTN_SAVE, Constants.THREE_SECOND);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.threadWait(Constants.SHORT_TIME);

//			locationSummary.navigateToLocationAndSelectGMASubsidyToVerifyTopOff(menus.get(0),
//					rstLocationSummaryData.get(CNLocationSummary.NAME), requiredData);

			locationList.selectLocationName(rstLocationSummaryData.get(CNLocationSummary.NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);

			// Verify GMA Subsidy
			foundation.scrollIntoViewElement(LocationSummary.DPD_GMA_SUBSIDY);
			locationSummary.verifyTopOffSubsidy(requiredData);
			locationSummary.verifyRolloverSubsidy(requiredData);
			foundation.waitforElementToBeVisible(LocationSummary.ROLL_OVER_CHECKBOX, Constants.SHORT_TIME);
			checkBox.unCheck(LocationSummary.ROLL_OVER_CHECKBOX);
			foundation.threadWait(Constants.THREE_SECOND);
			checkBox.check(LocationSummary.CHK_TOP_OFF_SUBSIDY);
			foundation.threadWait(Constants.THREE_SECOND);
			foundation.click(LocationSummary.START_DATE_PICKER_TOP_OFF_1);

			// Setting start date as Current date & recurrence as 'Daily' in TopOff Subsidy
			locationSummary.verifyTopOffDateAutoLocation1(currentDate);
			dropDown.selectItem(LocationSummary.DPD_TOP_OFF_RECURRENCE, requiredData.get(8), Constants.TEXT);
			textBox.enterText(LocationSummary.TXT_TOP_OFF_GROUP_NAME, requiredData.get(7));
			foundation.click(LocationSummary.TXT_TOP_OFF_AMOUNT);
			textBox.enterText(LocationSummary.TXT_TOP_OFF_AMOUNT, requiredData.get(9));
			foundation.threadWait(Constants.SHORT_TIME);
			textBox.enterText(LocationSummary.TXT_ROLL_OVER_GROUP_NAME, requiredData.get(10));
			foundation.waitforElementToBeVisible(LocationSummary.BTN_SAVE, Constants.THREE_SECOND);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElementToDisappear(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			foundation.waitforElementToBeVisible(LocationList.LBL_LOCATION_LIST, Constants.SHORT_TIME);

			// Navigate to Admin-->Consumer to validate Subsidy Balance for the consumer
			// associated to AutoLocation1 Location
			String balance = consumerSearch
					.searchConsumerWithMailAndNavigateToConsumerSummaryPageToValidateSubsidyBalance(menus.get(1),
							rstLocationSummaryData.get(CNLocationSummary.TAB_NAME),
							rstLocationSummaryData.get(CNLocationSummary.CONTACT_EMAIL),
							rstLocationSummaryData.get(CNLocationSummary.NAME));
			CustomisedAssert.assertEquals(balance, rstLocationSummaryData.get(CNLocationSummary.COLUMN_VALUE));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// Resetting GMA subsidy
			locationSummary.subsidyResettingValidationOff(menus.get(0),
					rstLocationSummaryData.get(CNLocationSummary.NAME), requiredData.get(1));
			login.logout();
			browser.close();
		}
	}

	/**
	 * @author karthikr SOS-32030
	 * @date - 04/07/2022
	 */
	@Test(description = "197693 - verify the consumer subsidy balance funding when the start date is set as past date and recurrence set to 'Daily'")
	public void verifyConsumerSubsidyBalanceWhenSetToPastDateWithRecurrenceDaily() {
		final String CASE_NUM = "197693";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);

		List<String> menus = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> requiredData = Arrays
				.asList(rstLocationSummaryData.get(CNLocationSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		String pastDate = dateAndTime.getPastDate(Constants.REGEX_MM_DD_YYYY, requiredData.get(10));
		String currentDate = dateAndTime.getDateAndTime(Constants.REGEX_MM_DD_YYYY, Constants.TIME_ZONE_INDIA);
		try {
			
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menus.get(0));
			locationList.selectLocationName(rstLocationSummaryData.get(CNLocationSummary.NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);

			// Verify GMA Subsidy
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_GMA_SUBSIDY));
			String value = dropDown.getSelectedItem(LocationSummary.DPD_GMA_SUBSIDY);
			if (value.equals(requiredData.get(1))) {
				dropDown.selectItem(LocationSummary.DPD_GMA_SUBSIDY, requiredData.get(0), Constants.TEXT);
			}

			foundation.waitforElementToBeVisible(LocationSummary.CHK_DEFAULT_ROLL_OVER, Constants.THREE_SECOND);
			checkBox.check(LocationSummary.ROLL_OVER_CHECKBOX);
			foundation.threadWait(Constants.THREE_SECOND);

			// update roll over
			foundation.click(CreateLocation.DATE_PICKER_ROLL_OVER);
			locationSummary.verifyRollOverDateLocation1(currentDate);
			locationSummary.verifyGMASubsidy(LocationSummary.DPD_ROLL_OVER_RECURRENCE,
					LocationSummary.TXT_ROLL_OVER_GROUP_NAME, LocationSummary.TXT_ROLL_OVER_AMOUNT, requiredData.get(8),
					requiredData.get(11), requiredData.get(12));
			foundation.threadWait(Constants.SHORT_TIME);
			textBox.enterText(LocationSummary.TXT_TOP_OFF_GROUP_NAME, requiredData.get(7));
			foundation.waitforElementToBeVisible(LocationSummary.BTN_SAVE, Constants.THREE_SECOND);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.threadWait(Constants.SHORT_TIME);

//			locationSummary.navigateToLocationAndSelectGMASubsidyToVerifyTopOff(menus.get(0),
//					rstLocationSummaryData.get(CNLocationSummary.NAME), requiredData);

			locationList.selectLocationName(rstLocationSummaryData.get(CNLocationSummary.NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);

			// Verify GMA Subsidy
			foundation.scrollIntoViewElement(LocationSummary.DPD_GMA_SUBSIDY);
			locationSummary.verifyTopOffSubsidy(requiredData);
			locationSummary.verifyRolloverSubsidy(requiredData);
			foundation.waitforElementToBeVisible(LocationSummary.ROLL_OVER_CHECKBOX, Constants.SHORT_TIME);
			checkBox.unCheck(LocationSummary.ROLL_OVER_CHECKBOX);
			foundation.threadWait(Constants.THREE_SECOND);
			checkBox.check(LocationSummary.CHK_TOP_OFF_SUBSIDY);
			foundation.threadWait(Constants.THREE_SECOND);
			foundation.click(LocationSummary.START_DATE_PICKER_TOP_OFF_1);
			// Login to ADM as Super, Navigate to Location and select GMA subsidy to Verify
			// TopOff Subsidy
//			locationSummary.navigateToLocationAndSelectGMASubsidyToVerifyTopOff(menus.get(0),
//					rstLocationSummaryData.get(CNLocationSummary.NAME), requiredData);

			// Setting start date as Past date & recurrence as 'Daily' in TopOff Subsidy
			locationSummary.verifyTopOffDateAsPastDateForAutoLocation1(pastDate);
			dropDown.selectItem(LocationSummary.DPD_TOP_OFF_RECURRENCE, requiredData.get(8), Constants.TEXT);
			textBox.enterText(LocationSummary.TXT_TOP_OFF_GROUP_NAME, requiredData.get(7));
			foundation.click(LocationSummary.TXT_TOP_OFF_AMOUNT);
			textBox.enterText(LocationSummary.TXT_TOP_OFF_AMOUNT, requiredData.get(9));
			foundation.threadWait(Constants.THREE_SECOND);
			textBox.enterText(LocationSummary.TXT_ROLL_OVER_GROUP_NAME, requiredData.get(11));
			foundation.threadWait(Constants.THREE_SECOND);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElementToDisappear(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			foundation.waitforElementToBeVisible(LocationList.LBL_LOCATION_LIST, Constants.SHORT_TIME);

			// Navigate to Admin-->Consumer to validate Subsidy Balance for the consumer
			// associated to AutoLocation1 Location
			String balance = consumerSearch
					.searchConsumerWithMailAndNavigateToConsumerSummaryPageToValidateSubsidyBalance(menus.get(1),
							rstLocationSummaryData.get(CNLocationSummary.TAB_NAME),
							rstLocationSummaryData.get(CNLocationSummary.CONTACT_EMAIL),
							rstLocationSummaryData.get(CNLocationSummary.NAME));
			CustomisedAssert.assertEquals(balance, rstLocationSummaryData.get(CNLocationSummary.COLUMN_VALUE));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// Resetting GMA subsidy
			locationSummary.subsidyResettingValidationOff(menus.get(0),
					rstLocationSummaryData.get(CNLocationSummary.NAME), requiredData.get(1));
			login.logout();
			browser.close();
		}
	}

	/**
	 * @author karthikr SOS-32030
	 * @date - 05/07/2022
	 */
	@Test(description = "197694 - verify the consumer subsidy balance funding when the start date is set as Future date and recurrence set to 'Daily'")
	public void verifyConsumerSubsidyBalanceWhenSetToFutureDateWithRecurrenceDaily() {
		final String CASE_NUM = "197694";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);

		List<String> menus = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> requiredData = Arrays
				.asList(rstLocationSummaryData.get(CNLocationSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		try {
			// Login to ADM as Super, Navigate to Location and select GMA subsidy to Verify
			// TopOff Subsidy
			locationSummary.navigateToLocationAndSelectGMASubsidyToVerifyTopOff(menus.get(0),
					rstLocationSummaryData.get(CNLocationSummary.NAME), requiredData);

			// Setting start date as Future date & recurrence as 'Daily' in TopOff Subsidy
			locationSummary.verifyTopOffDateAsFutureDateForAutoLocation1(
					rstLocationSummaryData.get(CNLocationSummary.START_DATE), Integer.parseInt(requiredData.get(10)),
					Integer.parseInt(requiredData.get(11)));
			dropDown.selectItem(LocationSummary.DPD_TOP_OFF_RECURRENCE, requiredData.get(8), Constants.TEXT);
			textBox.enterText(LocationSummary.TXT_TOP_OFF_GROUP_NAME, requiredData.get(7));
			foundation.click(LocationSummary.TXT_TOP_OFF_AMOUNT);
			textBox.enterText(LocationSummary.TXT_TOP_OFF_AMOUNT, requiredData.get(9));
			foundation.threadWait(Constants.SHORT_TIME);
			textBox.enterText(LocationSummary.TXT_ROLL_OVER_GROUP_NAME, requiredData.get(8));
			foundation.threadWait(Constants.THREE_SECOND);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElementToDisappear(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			foundation.waitforElementToBeVisible(LocationList.LBL_LOCATION_LIST, Constants.SHORT_TIME);

			// Navigate to Admin-->Consumer to validate Subsidy Balance for the consumer
			// associated to AutoLocation1 Location
			String balance = consumerSearch
					.searchConsumerWithMailAndNavigateToConsumerSummaryPageToValidateSubsidyBalance(menus.get(1),
							rstLocationSummaryData.get(CNLocationSummary.TAB_NAME),
							rstLocationSummaryData.get(CNLocationSummary.CONTACT_EMAIL),
							rstLocationSummaryData.get(CNLocationSummary.NAME));
			CustomisedAssert.assertEquals(balance, rstLocationSummaryData.get(CNLocationSummary.COLUMN_VALUE));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// Resetting GMA subsidy
			locationSummary.subsidyResettingValidationOff(menus.get(0),
					rstLocationSummaryData.get(CNLocationSummary.NAME), requiredData.get(1));
			login.logout();
			browser.close();
		}
	}

	/**
	 * @author karthikr SOS-32030
	 * @date - 05/07/2022
	 */
	@Test(description = "197701 - verify the consumer subsidy balance funding when the start date is set as current date and recurrence set to 'Weekly'")
	public void verifyConsumerSubsidyBalanceWhenSetToCurrentDateWithRecurrenceWeekly() {
		final String CASE_NUM = "197701";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);

		List<String> menus = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> requiredData = Arrays
				.asList(rstLocationSummaryData.get(CNLocationSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		String currentDate = dateAndTime.getDateAndTime(Constants.REGEX_MM_DD_YYYY, Constants.TIME_ZONE_INDIA);
		try {
			// Login to ADM as Super, Navigate to Location and select GMA subsidy to Verify
			// TopOff Subsidy
//			locationSummary.navigateToLocationAndSelectGMASubsidyToVerifyTopOff(menus.get(0),
//					rstLocationSummaryData.get(CNLocationSummary.NAME), requiredData);
			
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menus.get(0));
			locationList.selectLocationName(rstLocationSummaryData.get(CNLocationSummary.NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);

			// Verify GMA Subsidy
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_GMA_SUBSIDY));
			String value = dropDown.getSelectedItem(LocationSummary.DPD_GMA_SUBSIDY);
			if (value.equals(requiredData.get(1))) {
				dropDown.selectItem(LocationSummary.DPD_GMA_SUBSIDY, requiredData.get(0), Constants.TEXT);
			}

			foundation.waitforElementToBeVisible(LocationSummary.CHK_DEFAULT_ROLL_OVER, Constants.THREE_SECOND);
			checkBox.check(LocationSummary.ROLL_OVER_CHECKBOX);
			foundation.threadWait(Constants.THREE_SECOND);

			// update roll over
			foundation.click(CreateLocation.DATE_PICKER_ROLL_OVER);
			locationSummary.verifyRollOverDateLocation1(currentDate);
			locationSummary.verifyGMASubsidy(LocationSummary.DPD_ROLL_OVER_RECURRENCE,
					LocationSummary.TXT_ROLL_OVER_GROUP_NAME, LocationSummary.TXT_ROLL_OVER_AMOUNT, requiredData.get(8),
					requiredData.get(7), requiredData.get(11));
			foundation.threadWait(Constants.SHORT_TIME);
			textBox.enterText(LocationSummary.TXT_TOP_OFF_GROUP_NAME, requiredData.get(10));
			foundation.waitforElementToBeVisible(LocationSummary.BTN_SAVE, Constants.THREE_SECOND);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.threadWait(Constants.SHORT_TIME);

//			locationSummary.navigateToLocationAndSelectGMASubsidyToVerifyTopOff(menus.get(0),
//					rstLocationSummaryData.get(CNLocationSummary.NAME), requiredData);

			locationList.selectLocationName(rstLocationSummaryData.get(CNLocationSummary.NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);

			// Verify GMA Subsidy
			foundation.scrollIntoViewElement(LocationSummary.DPD_GMA_SUBSIDY);
			locationSummary.verifyTopOffSubsidy(requiredData);
			locationSummary.verifyRolloverSubsidy(requiredData);
			foundation.waitforElementToBeVisible(LocationSummary.ROLL_OVER_CHECKBOX, Constants.SHORT_TIME);
			checkBox.unCheck(LocationSummary.ROLL_OVER_CHECKBOX);
			foundation.threadWait(Constants.THREE_SECOND);
			checkBox.check(LocationSummary.CHK_TOP_OFF_SUBSIDY);
			foundation.threadWait(Constants.THREE_SECOND);
			foundation.click(LocationSummary.START_DATE_PICKER_TOP_OFF_1);

			// Setting start date as Current date & recurrence as 'Weekly' in TopOff Subsidy
			locationSummary.verifyTopOffDateAutoLocation1(currentDate);
			dropDown.selectItem(LocationSummary.DPD_TOP_OFF_RECURRENCE, requiredData.get(8), Constants.TEXT);
			textBox.enterText(LocationSummary.TXT_TOP_OFF_GROUP_NAME, requiredData.get(7));
			foundation.click(LocationSummary.TXT_TOP_OFF_AMOUNT);
			textBox.enterText(LocationSummary.TXT_TOP_OFF_AMOUNT, requiredData.get(9));
			foundation.threadWait(Constants.SHORT_TIME);
			textBox.enterText(LocationSummary.TXT_ROLL_OVER_GROUP_NAME, requiredData.get(10));
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElementToDisappear(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			foundation.waitforElementToBeVisible(LocationList.LBL_LOCATION_LIST, Constants.SHORT_TIME);

			// Navigate to Admin-->Consumer to validate Subsidy Balance for the consumer
			// associated to AutoLocation1 Location
			String balance = consumerSearch
					.searchConsumerWithMailAndNavigateToConsumerSummaryPageToValidateSubsidyBalance(menus.get(1),
							rstLocationSummaryData.get(CNLocationSummary.TAB_NAME),
							rstLocationSummaryData.get(CNLocationSummary.CONTACT_EMAIL),
							rstLocationSummaryData.get(CNLocationSummary.NAME));
			CustomisedAssert.assertEquals(balance, rstLocationSummaryData.get(CNLocationSummary.COLUMN_VALUE));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// Resetting GMA subsidy
			locationSummary.subsidyResettingValidationOff(menus.get(0),
					rstLocationSummaryData.get(CNLocationSummary.NAME), requiredData.get(1));
			login.logout();
			browser.close();
		}
	}

	/**
	 * @author karthikr SOS-32030
	 * @date - 06/07/2022
	 */
	@Test(description = "197696 - verify the consumer subsidy balance funding when the start date is set as past date and recurrence set to 'Weekly'")
	public void verifyConsumerSubsidyBalanceWhenSetToPastDateWithRecurrenceWeekly() {
		final String CASE_NUM = "197696";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);

		List<String> menus = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> requiredData = Arrays
				.asList(rstLocationSummaryData.get(CNLocationSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		String pastDate = dateAndTime.getPastDate(Constants.REGEX_MM_DD_YYYY, requiredData.get(10));
		String currentDate = dateAndTime.getDateAndTime(Constants.REGEX_MM_DD_YYYY, Constants.TIME_ZONE_INDIA);
		try {
			
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menus.get(0));
			locationList.selectLocationName(rstLocationSummaryData.get(CNLocationSummary.NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);

			// Verify GMA Subsidy
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_GMA_SUBSIDY));
			String value = dropDown.getSelectedItem(LocationSummary.DPD_GMA_SUBSIDY);
			if (value.equals(requiredData.get(1))) {
				dropDown.selectItem(LocationSummary.DPD_GMA_SUBSIDY, requiredData.get(0), Constants.TEXT);
			}

			foundation.waitforElementToBeVisible(LocationSummary.CHK_DEFAULT_ROLL_OVER, Constants.THREE_SECOND);
			checkBox.check(LocationSummary.ROLL_OVER_CHECKBOX);
			foundation.threadWait(Constants.THREE_SECOND);

			// update roll over
			foundation.click(CreateLocation.DATE_PICKER_ROLL_OVER);
			locationSummary.verifyRollOverDateLocation1(currentDate);
			locationSummary.verifyGMASubsidy(LocationSummary.DPD_ROLL_OVER_RECURRENCE,
					LocationSummary.TXT_ROLL_OVER_GROUP_NAME, LocationSummary.TXT_ROLL_OVER_AMOUNT, requiredData.get(8),
					requiredData.get(11), requiredData.get(12));
			foundation.threadWait(Constants.SHORT_TIME);
			textBox.enterText(LocationSummary.TXT_TOP_OFF_GROUP_NAME, requiredData.get(7));
			foundation.waitforElementToBeVisible(LocationSummary.BTN_SAVE, Constants.THREE_SECOND);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.threadWait(Constants.SHORT_TIME);

//			locationSummary.navigateToLocationAndSelectGMASubsidyToVerifyTopOff(menus.get(0),
//					rstLocationSummaryData.get(CNLocationSummary.NAME), requiredData);

			locationList.selectLocationName(rstLocationSummaryData.get(CNLocationSummary.NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);

			// Verify GMA Subsidy
			foundation.scrollIntoViewElement(LocationSummary.DPD_GMA_SUBSIDY);
			locationSummary.verifyTopOffSubsidy(requiredData);
			locationSummary.verifyRolloverSubsidy(requiredData);
			foundation.waitforElementToBeVisible(LocationSummary.ROLL_OVER_CHECKBOX, Constants.SHORT_TIME);
			checkBox.unCheck(LocationSummary.ROLL_OVER_CHECKBOX);
			foundation.threadWait(Constants.THREE_SECOND);
			checkBox.check(LocationSummary.CHK_TOP_OFF_SUBSIDY);
			foundation.threadWait(Constants.THREE_SECOND);
			foundation.click(LocationSummary.START_DATE_PICKER_TOP_OFF_1);
			// Login to ADM as Super, Navigate to Location and select GMA subsidy to Verify
			// TopOff Subsidy
//			locationSummary.navigateToLocationAndSelectGMASubsidyToVerifyTopOff(menus.get(0),
//					rstLocationSummaryData.get(CNLocationSummary.NAME), requiredData);

			// Setting start date as Past date & recurrence as 'Weekly' in TopOff Subsidy
			locationSummary.verifyTopOffDateAsPastDateForAutoLocation1(pastDate);
			dropDown.selectItem(LocationSummary.DPD_TOP_OFF_RECURRENCE, requiredData.get(8), Constants.TEXT);
			textBox.enterText(LocationSummary.TXT_TOP_OFF_GROUP_NAME, requiredData.get(7));
			foundation.click(LocationSummary.TXT_TOP_OFF_AMOUNT);
			textBox.enterText(LocationSummary.TXT_TOP_OFF_AMOUNT, requiredData.get(9));
			foundation.threadWait(Constants.THREE_SECOND);
			textBox.enterText(LocationSummary.TXT_ROLL_OVER_GROUP_NAME, requiredData.get(11));
			foundation.threadWait(Constants.THREE_SECOND);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElementToDisappear(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			foundation.waitforElementToBeVisible(LocationList.LBL_LOCATION_LIST, Constants.SHORT_TIME);

			// Navigate to Admin-->Consumer to validate Subsidy Balance for the consumer
			// associated to AutoLocation1 Location
			String balance = consumerSearch
					.searchConsumerWithMailAndNavigateToConsumerSummaryPageToValidateSubsidyBalance(menus.get(1),
							rstLocationSummaryData.get(CNLocationSummary.TAB_NAME),
							rstLocationSummaryData.get(CNLocationSummary.CONTACT_EMAIL),
							rstLocationSummaryData.get(CNLocationSummary.NAME));
			CustomisedAssert.assertEquals(balance, rstLocationSummaryData.get(CNLocationSummary.COLUMN_VALUE));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// Resetting GMA subsidy
			locationSummary.subsidyResettingValidationOff(menus.get(0),
					rstLocationSummaryData.get(CNLocationSummary.NAME), requiredData.get(1));
			login.logout();
			browser.close();
		}
	}

	/**
	 * @author karthikr SOS-31997
	 * @date - 06/07/2022
	 */
	@Test(description = "197697 - verify the consumer subsidy balance funding when the start date is set as Future date and recurrence set to 'Weekly'")
	public void verifyConsumerSubsidyBalanceWhenSetToFutureDateWithRecurrenceWeekly() {
		final String CASE_NUM = "197697";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);

		List<String> menus = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> requiredData = Arrays
				.asList(rstLocationSummaryData.get(CNLocationSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		String currentDate = dateAndTime.getDateAndTime(Constants.REGEX_MM_DD_YYYY, Constants.TIME_ZONE_INDIA);
		try {
			
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menus.get(0));
			locationList.selectLocationName(rstLocationSummaryData.get(CNLocationSummary.NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);

			// Verify GMA Subsidy
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_GMA_SUBSIDY));
			String value = dropDown.getSelectedItem(LocationSummary.DPD_GMA_SUBSIDY);
			if (value.equals(requiredData.get(1))) {
				dropDown.selectItem(LocationSummary.DPD_GMA_SUBSIDY, requiredData.get(0), Constants.TEXT);
			}

			foundation.waitforElementToBeVisible(LocationSummary.CHK_DEFAULT_ROLL_OVER, Constants.THREE_SECOND);
			checkBox.check(LocationSummary.ROLL_OVER_CHECKBOX);
			foundation.threadWait(Constants.THREE_SECOND);

			// update roll over
			foundation.click(CreateLocation.DATE_PICKER_ROLL_OVER);
			locationSummary.verifyRollOverDateLocation1(currentDate);
			locationSummary.verifyGMASubsidy(LocationSummary.DPD_ROLL_OVER_RECURRENCE,
					LocationSummary.TXT_ROLL_OVER_GROUP_NAME, LocationSummary.TXT_ROLL_OVER_AMOUNT, requiredData.get(8),
					requiredData.get(7), requiredData.get(13));
			foundation.threadWait(Constants.SHORT_TIME);
			textBox.enterText(LocationSummary.TXT_TOP_OFF_GROUP_NAME, requiredData.get(12));
			foundation.waitforElementToBeVisible(LocationSummary.BTN_SAVE, Constants.THREE_SECOND);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.threadWait(Constants.SHORT_TIME);

//			locationSummary.navigateToLocationAndSelectGMASubsidyToVerifyTopOff(menus.get(0),
//					rstLocationSummaryData.get(CNLocationSummary.NAME), requiredData);

			locationList.selectLocationName(rstLocationSummaryData.get(CNLocationSummary.NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);

			// Verify GMA Subsidy
			foundation.scrollIntoViewElement(LocationSummary.DPD_GMA_SUBSIDY);
			locationSummary.verifyTopOffSubsidy(requiredData);
			locationSummary.verifyRolloverSubsidy(requiredData);
			foundation.waitforElementToBeVisible(LocationSummary.ROLL_OVER_CHECKBOX, Constants.SHORT_TIME);
			checkBox.unCheck(LocationSummary.ROLL_OVER_CHECKBOX);
			foundation.threadWait(Constants.THREE_SECOND);
			checkBox.check(LocationSummary.CHK_TOP_OFF_SUBSIDY);
			foundation.threadWait(Constants.THREE_SECOND);
			foundation.click(LocationSummary.START_DATE_PICKER_TOP_OFF_1);
			// Login to ADM as Super, Navigate to Location and select GMA subsidy to Verify
			// TopOff Subsidy
//			locationSummary.navigateToLocationAndSelectGMASubsidyToVerifyTopOff(menus.get(0),
//					rstLocationSummaryData.get(CNLocationSummary.NAME), requiredData);

			// Setting start date as Future date & recurrence as 'Weekly' in TopOff Subsidy
			locationSummary.verifyTopOffDateAsFutureDateForAutoLocation1(
					rstLocationSummaryData.get(CNLocationSummary.START_DATE), Integer.parseInt(requiredData.get(10)),
					Integer.parseInt(requiredData.get(11)));
			dropDown.selectItem(LocationSummary.DPD_TOP_OFF_RECURRENCE, requiredData.get(8), Constants.TEXT);
			textBox.enterText(LocationSummary.TXT_TOP_OFF_GROUP_NAME, requiredData.get(7));
			foundation.click(LocationSummary.TXT_TOP_OFF_AMOUNT);
			textBox.enterText(LocationSummary.TXT_TOP_OFF_AMOUNT, requiredData.get(9));
			foundation.threadWait(Constants.THREE_SECOND);
			textBox.enterText(LocationSummary.TXT_TOP_OFF_GROUP_NAME, requiredData.get(12));
			foundation.threadWait(Constants.THREE_SECOND);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElementToDisappear(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			foundation.waitforElementToBeVisible(LocationList.LBL_LOCATION_LIST, Constants.SHORT_TIME);

			// Navigate to Admin-->Consumer to validate Subsidy Balance for the consumer
			// associated to AutoLocation1 Location
			String balance = consumerSearch
					.searchConsumerWithMailAndNavigateToConsumerSummaryPageToValidateSubsidyBalance(menus.get(1),
							rstLocationSummaryData.get(CNLocationSummary.TAB_NAME),
							rstLocationSummaryData.get(CNLocationSummary.CONTACT_EMAIL),
							rstLocationSummaryData.get(CNLocationSummary.NAME));
			CustomisedAssert.assertEquals(balance, rstLocationSummaryData.get(CNLocationSummary.COLUMN_VALUE));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// Resetting GMA subsidy
			locationSummary.subsidyResettingValidationOff(menus.get(0),
					rstLocationSummaryData.get(CNLocationSummary.NAME), requiredData.get(1));
			login.logout();
			browser.close();
		}
	}

	/**
	 * @author karthikr SOS-31997
	 * @date - 07/07/2022
	 */
	@Test(description = "197698 - verify the consumer subsidy balance funding when the start date is set as Current date and recurrence set to 'Monthly'")
	public void verifyConsumerSubsidyBalanceWhenSetToCurrentDateWithRecurrenceMonthly() {
		final String CASE_NUM = "197698";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);

		List<String> menus = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> requiredData = Arrays
				.asList(rstLocationSummaryData.get(CNLocationSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		String currentDate = dateAndTime.getDateAndTime(Constants.REGEX_MM_DD_YYYY, Constants.TIME_ZONE_INDIA);
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menus.get(0));
			locationList.selectLocationName(rstLocationSummaryData.get(CNLocationSummary.NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);

			// Verify GMA Subsidy
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_GMA_SUBSIDY));
			String value = dropDown.getSelectedItem(LocationSummary.DPD_GMA_SUBSIDY);
			if (value.equals(requiredData.get(1))) {
				dropDown.selectItem(LocationSummary.DPD_GMA_SUBSIDY, requiredData.get(0), Constants.TEXT);
			}

			foundation.waitforElementToBeVisible(LocationSummary.CHK_DEFAULT_ROLL_OVER, Constants.THREE_SECOND);
			checkBox.check(LocationSummary.ROLL_OVER_CHECKBOX);
			foundation.threadWait(Constants.THREE_SECOND);

			// update roll over
			foundation.click(CreateLocation.DATE_PICKER_ROLL_OVER);
			locationSummary.verifyRollOverDateLocation1(currentDate);
			locationSummary.verifyGMASubsidy(LocationSummary.DPD_ROLL_OVER_RECURRENCE,
					LocationSummary.TXT_ROLL_OVER_GROUP_NAME, LocationSummary.TXT_ROLL_OVER_AMOUNT, requiredData.get(8),
					requiredData.get(7), requiredData.get(11));
			foundation.threadWait(Constants.SHORT_TIME);
			textBox.enterText(LocationSummary.TXT_TOP_OFF_GROUP_NAME, requiredData.get(10));
			foundation.waitforElementToBeVisible(LocationSummary.BTN_SAVE, Constants.THREE_SECOND);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.threadWait(Constants.SHORT_TIME);

//			locationSummary.navigateToLocationAndSelectGMASubsidyToVerifyTopOff(menus.get(0),
//					rstLocationSummaryData.get(CNLocationSummary.NAME), requiredData);

			locationList.selectLocationName(rstLocationSummaryData.get(CNLocationSummary.NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);

			// Verify GMA Subsidy
			foundation.scrollIntoViewElement(LocationSummary.DPD_GMA_SUBSIDY);
			locationSummary.verifyTopOffSubsidy(requiredData);
			locationSummary.verifyRolloverSubsidy(requiredData);
			foundation.waitforElementToBeVisible(LocationSummary.ROLL_OVER_CHECKBOX, Constants.SHORT_TIME);
			checkBox.unCheck(LocationSummary.ROLL_OVER_CHECKBOX);
			foundation.threadWait(Constants.THREE_SECOND);
			checkBox.check(LocationSummary.CHK_TOP_OFF_SUBSIDY);
			foundation.threadWait(Constants.THREE_SECOND);
			foundation.click(LocationSummary.START_DATE_PICKER_TOP_OFF_1);

//			// Login to ADM as Super, Navigate to Location and select GMA subsidy to Verify
//			// TopOff Subsidy
//			locationSummary.navigateToLocationAndSelectGMASubsidyToVerifyTopOff(menus.get(0),
//					rstLocationSummaryData.get(CNLocationSummary.NAME), requiredData);

			// Setting start date as Current date & recurrence as 'Monthly' in TopOff
			// Subsidy
			locationSummary.verifyTopOffDateAutoLocation1(currentDate);
			dropDown.selectItem(LocationSummary.DPD_TOP_OFF_RECURRENCE, requiredData.get(8), Constants.TEXT);
			textBox.enterText(LocationSummary.TXT_TOP_OFF_GROUP_NAME, requiredData.get(7));
			foundation.click(LocationSummary.TXT_TOP_OFF_AMOUNT);
			textBox.enterText(LocationSummary.TXT_TOP_OFF_AMOUNT, requiredData.get(9));
			foundation.threadWait(Constants.SHORT_TIME);
			textBox.enterText(LocationSummary.TXT_ROLL_OVER_GROUP_NAME, requiredData.get(10));
			foundation.waitforElementToBeVisible(LocationSummary.BTN_SAVE, Constants.THREE_SECOND);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElementToDisappear(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			foundation.waitforElementToBeVisible(LocationList.LBL_LOCATION_LIST, Constants.SHORT_TIME);

			// Navigate to Admin-->Consumer to validate Subsidy Balance for the consumer
			// associated to AutoLocation1 Location
			String balance = consumerSearch
					.searchConsumerWithMailAndNavigateToConsumerSummaryPageToValidateSubsidyBalance(menus.get(1),
							rstLocationSummaryData.get(CNLocationSummary.TAB_NAME),
							rstLocationSummaryData.get(CNLocationSummary.CONTACT_EMAIL),
							rstLocationSummaryData.get(CNLocationSummary.NAME));
			CustomisedAssert.assertEquals(balance, rstLocationSummaryData.get(CNLocationSummary.COLUMN_VALUE));
			foundation.threadWait(Constants.SHORT_TIME);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// Resetting GMA subsidy
			locationSummary.subsidyResettingValidationOff(menus.get(0),
					rstLocationSummaryData.get(CNLocationSummary.NAME), requiredData.get(1));
			login.logout();
			browser.close();
		}
	}

	/**
	 * @author karthikr SOS-31997
	 * @date - 07/07/2022
	 */
	@Test(description = "197699 - verify the consumer subsidy balance funding when the start date is set as Past date and recurrence set to 'Monthly'")
	public void verifyConsumerSubsidyBalanceWhenSetToPastDateWithRecurrenceMonthly() {
		final String CASE_NUM = "197699";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);

		List<String> menus = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> requiredData = Arrays
				.asList(rstLocationSummaryData.get(CNLocationSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		String pastDate = dateAndTime.getPastDate(Constants.REGEX_MM_DD_YYYY, requiredData.get(10));
		String currentDate = dateAndTime.getDateAndTime(Constants.REGEX_MM_DD_YYYY, Constants.TIME_ZONE_INDIA);
		try {
			
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menus.get(0));
			locationList.selectLocationName(rstLocationSummaryData.get(CNLocationSummary.NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);

			// Verify GMA Subsidy
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_GMA_SUBSIDY));
			String value = dropDown.getSelectedItem(LocationSummary.DPD_GMA_SUBSIDY);
			if (value.equals(requiredData.get(1))) {
				dropDown.selectItem(LocationSummary.DPD_GMA_SUBSIDY, requiredData.get(0), Constants.TEXT);
			}

			foundation.waitforElementToBeVisible(LocationSummary.CHK_DEFAULT_ROLL_OVER, Constants.THREE_SECOND);
			checkBox.check(LocationSummary.ROLL_OVER_CHECKBOX);
			foundation.threadWait(Constants.THREE_SECOND);

			// update roll over
			foundation.click(CreateLocation.DATE_PICKER_ROLL_OVER);
			locationSummary.verifyRollOverDateLocation1(currentDate);
			locationSummary.verifyGMASubsidy(LocationSummary.DPD_ROLL_OVER_RECURRENCE,
					LocationSummary.TXT_ROLL_OVER_GROUP_NAME, LocationSummary.TXT_ROLL_OVER_AMOUNT, requiredData.get(8),
					requiredData.get(11), requiredData.get(12));
			foundation.threadWait(Constants.SHORT_TIME);
			textBox.enterText(LocationSummary.TXT_TOP_OFF_GROUP_NAME, requiredData.get(7));
			foundation.waitforElementToBeVisible(LocationSummary.BTN_SAVE, Constants.THREE_SECOND);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.threadWait(Constants.SHORT_TIME);

//			locationSummary.navigateToLocationAndSelectGMASubsidyToVerifyTopOff(menus.get(0),
//					rstLocationSummaryData.get(CNLocationSummary.NAME), requiredData);

			locationList.selectLocationName(rstLocationSummaryData.get(CNLocationSummary.NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);

			// Verify GMA Subsidy
			foundation.scrollIntoViewElement(LocationSummary.DPD_GMA_SUBSIDY);
			locationSummary.verifyTopOffSubsidy(requiredData);
			locationSummary.verifyRolloverSubsidy(requiredData);
			foundation.waitforElementToBeVisible(LocationSummary.ROLL_OVER_CHECKBOX, Constants.SHORT_TIME);
			checkBox.unCheck(LocationSummary.ROLL_OVER_CHECKBOX);
			foundation.threadWait(Constants.THREE_SECOND);
			checkBox.check(LocationSummary.CHK_TOP_OFF_SUBSIDY);
			foundation.threadWait(Constants.THREE_SECOND);
			foundation.click(LocationSummary.START_DATE_PICKER_TOP_OFF_1);
			// Login to ADM as Super, Navigate to Location and select GMA subsidy to Verify
			// TopOff Subsidy
//			locationSummary.navigateToLocationAndSelectGMASubsidyToVerifyTopOff(menus.get(0),
//					rstLocationSummaryData.get(CNLocationSummary.NAME), requiredData);

			// Setting start date as Past date & recurrence as 'Monthly' in TopOff Subsidy
			locationSummary.verifyTopOffDateAsPastDateForAutoLocation1(pastDate);
			dropDown.selectItem(LocationSummary.DPD_TOP_OFF_RECURRENCE, requiredData.get(8), Constants.TEXT);
			textBox.enterText(LocationSummary.TXT_TOP_OFF_GROUP_NAME, requiredData.get(7));
			foundation.click(LocationSummary.TXT_TOP_OFF_AMOUNT);
			textBox.enterText(LocationSummary.TXT_TOP_OFF_AMOUNT, requiredData.get(9));
			foundation.threadWait(Constants.THREE_SECOND);
			textBox.enterText(LocationSummary.TXT_ROLL_OVER_GROUP_NAME, requiredData.get(11));
			foundation.threadWait(Constants.THREE_SECOND);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElementToDisappear(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			foundation.waitforElementToBeVisible(LocationList.LBL_LOCATION_LIST, Constants.SHORT_TIME);

			// Navigate to Admin-->Consumer to validate Subsidy Balance for the consumer
			// associated to AutoLocation1 Location
			String balance = consumerSearch
					.searchConsumerWithMailAndNavigateToConsumerSummaryPageToValidateSubsidyBalance(menus.get(1),
							rstLocationSummaryData.get(CNLocationSummary.TAB_NAME),
							rstLocationSummaryData.get(CNLocationSummary.CONTACT_EMAIL),
							rstLocationSummaryData.get(CNLocationSummary.NAME));
			CustomisedAssert.assertEquals(balance, rstLocationSummaryData.get(CNLocationSummary.COLUMN_VALUE));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// Resetting GMA subsidy
			locationSummary.subsidyResettingValidationOff(menus.get(0),
					rstLocationSummaryData.get(CNLocationSummary.NAME), requiredData.get(1));
			login.logout();
			browser.close();
		}
	}

	/**
	 * @author karthikr SOS-31997
	 * @date - 08/07/2022
	 */
	@Test(description = "197700 - verify the consumer subsidy balance funding when the start date is set as Future date and recurrence set to 'Monthly'")
	public void verifyConsumerSubsidyBalanceWhenSetToFutureDateWithRecurrenceMonthly() {
		final String CASE_NUM = "197700";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);

		List<String> menus = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> requiredData = Arrays
				.asList(rstLocationSummaryData.get(CNLocationSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		String currentDate = dateAndTime.getDateAndTime(Constants.REGEX_MM_DD_YYYY, Constants.TIME_ZONE_INDIA);
		try {
			
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menus.get(0));
			locationList.selectLocationName(rstLocationSummaryData.get(CNLocationSummary.NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);

			// Verify GMA Subsidy
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_GMA_SUBSIDY));
			String value = dropDown.getSelectedItem(LocationSummary.DPD_GMA_SUBSIDY);
			if (value.equals(requiredData.get(1))) {
				dropDown.selectItem(LocationSummary.DPD_GMA_SUBSIDY, requiredData.get(0), Constants.TEXT);
			}

			foundation.waitforElementToBeVisible(LocationSummary.CHK_DEFAULT_ROLL_OVER, Constants.THREE_SECOND);
			checkBox.check(LocationSummary.ROLL_OVER_CHECKBOX);
			foundation.threadWait(Constants.THREE_SECOND);

			// update roll over
			foundation.click(CreateLocation.DATE_PICKER_ROLL_OVER);
			locationSummary.verifyRollOverDateLocation1(currentDate);
			locationSummary.verifyGMASubsidy(LocationSummary.DPD_ROLL_OVER_RECURRENCE,
					LocationSummary.TXT_ROLL_OVER_GROUP_NAME, LocationSummary.TXT_ROLL_OVER_AMOUNT, requiredData.get(8),
					requiredData.get(7), requiredData.get(13));
			foundation.threadWait(Constants.SHORT_TIME);
			textBox.enterText(LocationSummary.TXT_TOP_OFF_GROUP_NAME, requiredData.get(12));
			foundation.waitforElementToBeVisible(LocationSummary.BTN_SAVE, Constants.THREE_SECOND);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.threadWait(Constants.SHORT_TIME);

//			locationSummary.navigateToLocationAndSelectGMASubsidyToVerifyTopOff(menus.get(0),
//					rstLocationSummaryData.get(CNLocationSummary.NAME), requiredData);

			locationList.selectLocationName(rstLocationSummaryData.get(CNLocationSummary.NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);

			// Verify GMA Subsidy
			foundation.scrollIntoViewElement(LocationSummary.DPD_GMA_SUBSIDY);
			locationSummary.verifyTopOffSubsidy(requiredData);
			locationSummary.verifyRolloverSubsidy(requiredData);
			foundation.waitforElementToBeVisible(LocationSummary.ROLL_OVER_CHECKBOX, Constants.SHORT_TIME);
			checkBox.unCheck(LocationSummary.ROLL_OVER_CHECKBOX);
			foundation.threadWait(Constants.THREE_SECOND);
			checkBox.check(LocationSummary.CHK_TOP_OFF_SUBSIDY);
			foundation.threadWait(Constants.THREE_SECOND);
			foundation.click(LocationSummary.START_DATE_PICKER_TOP_OFF_1);
			// Login to ADM as Super, Navigate to Location and select GMA subsidy to Verify
			// TopOff Subsidy
//			locationSummary.navigateToLocationAndSelectGMASubsidyToVerifyTopOff(menus.get(0),
//					rstLocationSummaryData.get(CNLocationSummary.NAME), requiredData);

			// Setting start date as Future date & recurrence as 'Monthly' in TopOff Subsidy
			locationSummary.verifyTopOffDateAsFutureDateForAutoLocation1(
					rstLocationSummaryData.get(CNLocationSummary.START_DATE), Integer.parseInt(requiredData.get(10)),
					Integer.parseInt(requiredData.get(11)));
			dropDown.selectItem(LocationSummary.DPD_TOP_OFF_RECURRENCE, requiredData.get(8), Constants.TEXT);
			textBox.enterText(LocationSummary.TXT_TOP_OFF_GROUP_NAME, requiredData.get(7));
			foundation.click(LocationSummary.TXT_TOP_OFF_AMOUNT);
			textBox.enterText(LocationSummary.TXT_TOP_OFF_AMOUNT, requiredData.get(9));
			foundation.threadWait(Constants.SHORT_TIME);
			textBox.enterText(LocationSummary.TXT_ROLL_OVER_GROUP_NAME, requiredData.get(12));
			foundation.waitforElementToBeVisible(LocationSummary.BTN_SAVE, Constants.SHORT_TIME);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElementToDisappear(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			foundation.waitforElementToBeVisible(LocationList.LBL_LOCATION_LIST, Constants.SHORT_TIME);

			// Navigate to Admin-->Consumer to validate Subsidy Balance for the consumer
			// associated to AutoLocation1 Location
			String balance = consumerSearch
					.searchConsumerWithMailAndNavigateToConsumerSummaryPageToValidateSubsidyBalance(menus.get(1),
							rstLocationSummaryData.get(CNLocationSummary.TAB_NAME),
							rstLocationSummaryData.get(CNLocationSummary.CONTACT_EMAIL),
							rstLocationSummaryData.get(CNLocationSummary.NAME));
			CustomisedAssert.assertEquals(balance, rstLocationSummaryData.get(CNLocationSummary.COLUMN_VALUE));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// Resetting GMA subsidy
			locationSummary.subsidyResettingValidationOff(menus.get(0),
					rstLocationSummaryData.get(CNLocationSummary.NAME), requiredData.get(1));
			login.logout();
			browser.close();
		}
	}

	/**
	 * @author afrosean Date:25-08-2022
	 */
	@Test(description = "197553 - SOS-30462 : To Verify that the Exclude Weekends Checkbox is not displayed for 'Weekly' in GMA Subsidy")
	public void verifyExcludeWeekendsCheckboxForWeeklyRecurrence() {
		final String CASE_NUM = "197553";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);

		List<String> menus = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> requiredData = Arrays
				.asList(rstLocationSummaryData.get(CNLocationSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		String currentDate = dateAndTime.getDateAndTime(Constants.REGEX_MM_DD_YYYY, Constants.TIME_ZONE_INDIA);
		try {
			// Login to ADM as Super, Navigate to Location and select GMA subsidy to Verify
			// TopOff Subsidy
			locationSummary.navigateToLocationAndSelectGMASubsidyToVerifyTopOff(menus.get(0),
					rstLocationSummaryData.get(CNLocationSummary.NAME), requiredData);

			// Setting start date as current date & recurrence as 'Monthly' in TopOff
			// Subsidy
			locationSummary.verifyTopOffDateAutoLocation1(currentDate);
			locationSummary.verifyGMASubsidy(LocationSummary.DPD_TOP_OFF_RECURRENCE,
					LocationSummary.TXT_TOP_OFF_GROUP_NAME, LocationSummary.TXT_TOP_OFF_AMOUNT, requiredData.get(15),
					requiredData.get(7), requiredData.get(9));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.EXCLUDE_WEEKENDS));
			foundation.click(LocationSummary.BTN_ADD_TOP_OFF);

			// verify exclusive checkBox for top off
			locationSummary.verifyGMASubsidy(LocationSummary.DPD_TOP_OFF_RECURRENCE_NEWROW,
					LocationSummary.TXT_TOP_OFF_GROUP_NAME_NEWROW, LocationSummary.TXT_TOP_OFF_AMOUNT_NEWROW,
					requiredData.get(8), requiredData.get(13), requiredData.get(9));
			CustomisedAssert.assertFalse(foundation.isDisplayed(LocationSummary.EXECLUDE_ROW1));
			foundation.click(LocationSummary.BTN_EXTRA_ADD_TOP_OFF);

			// verify exclusive checkBox for adding recurrence to monthly and weekly
			locationSummary.verifyGMASubsidy(LocationSummary.DPD_TOP_OFF_RECURRENCE_SECONDROW,
					LocationSummary.TXT_TOP_OFF_GROUP_NAME_NEWROW, LocationSummary.TXT_TOP_OFF_AMOUNT_NEWROW,
					requiredData.get(12), requiredData.get(14), requiredData.get(9));
			CustomisedAssert.assertFalse(foundation.isDisplayed(LocationSummary.EXECLUDE_ROW2));
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElementToDisappear(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			foundation.waitforElementToBeVisible(LocationList.LBL_LOCATION_LIST, Constants.SHORT_TIME);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {

			// Resetting GMA subsidy
			locationSummary.subsidyResettingValidationOff(menus.get(0),
					rstLocationSummaryData.get(CNLocationSummary.NAME), requiredData.get(1));
			login.logout();
			browser.close();
		}
	}

	/**
	 * @author afrosean Date:26-08-2022
	 */
	@Test(description = "197554 - To Verify that the Exclude Weekends Checkbox is not displayed for monthly in GMA Subsidy")
	public void verifyExcludeWeekendsCheckboxForWeeklyRecurrenceInRollOver() {
		final String CASE_NUM = "197554";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);

		List<String> menus = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> requiredData = Arrays
				.asList(rstLocationSummaryData.get(CNLocationSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		String currentDate = dateAndTime.getDateAndTime(Constants.REGEX_MM_DD_YYYY, Constants.TIME_ZONE_INDIA);
		try {
			// Login to ADM as Super, Navigate to Location and select GMA subsidy to Verify
			// TopOff Subsidy
			locationSummary.navigateToLocationAndSelectGMASubsidyToVerifyRollOver(menus.get(0),
					rstLocationSummaryData.get(CNLocationSummary.NAME), requiredData);

			// Setting start date as current date & recurrence as 'Monthly' in TopOff
			// Subsidy
			locationSummary.verifyRollOverCurrentDate(currentDate);
			locationSummary.verifyGMASubsidy(LocationSummary.DPD_ROLL_OVER_RECURRENCE,
					LocationSummary.TXT_ROLL_OVER_GROUP_NAME, LocationSummary.TXT_ROLL_OVER_AMOUNT,
					requiredData.get(15), requiredData.get(7), requiredData.get(9));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.EXCLUDE_WEEKENDS_ROLLOVER));
			foundation.click(LocationSummary.BTN_ADD_ROLL_OVER);

			// verify exclusive checkBox for roll over
			locationSummary.verifyGMASubsidy(LocationSummary.DPD_ROLL_OVER_RECURRENCE_NEWROW,
					LocationSummary.TXT_ROLL_OVER_GROUP_NAME_NEWROW, LocationSummary.TXT_ROLL_OVER_AMOUNT_NEWROW,
					requiredData.get(8), requiredData.get(13), requiredData.get(9));
			CustomisedAssert.assertFalse(foundation.isDisplayed(LocationSummary.ROLL_OVER_EXECLUDE_ROW1));
			foundation.click(LocationSummary.BTN_EXTRA_ADD_ROLL_OVER);

			// verify exclusive checkBox for adding recurrence to monthly and weekly
			locationSummary.verifyGMASubsidy(LocationSummary.DPD_ROLL_OVER_RECURRENCE_SECONDROW,
					LocationSummary.TXT_ROLL_OVER_GROUP_NAME_SECONDROW, LocationSummary.TXT_ROLL_OVER_AMOUNT_NEWROW,
					requiredData.get(12), requiredData.get(14), requiredData.get(9));
			CustomisedAssert.assertFalse(foundation.isDisplayed(LocationSummary.ROLL_OVER_EXECLUDE_ROW2));
			foundation.waitforElementToBeVisible(LocationSummary.BTN_SAVE, 5);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElementToDisappear(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			foundation.waitforElementToBeVisible(LocationList.LBL_LOCATION_LIST, Constants.SHORT_TIME);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {

			// Resetting GMA subsidy
			locationSummary.subsidyResettingValidationOff(menus.get(0),
					rstLocationSummaryData.get(CNLocationSummary.NAME), requiredData.get(1));
			login.logout();
			browser.close();
		}
	}
}