package at.smartshop.sos.tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import at.framework.database.mssql.Queries;
import at.framework.database.mssql.ResultSets;
import at.framework.files.Excel;
import at.framework.generic.CustomisedAssert;
import at.framework.generic.DateAndTime;
import at.framework.generic.Numbers;
import at.framework.generic.Strings;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.database.columns.CNGmaUser;
import at.smartshop.database.columns.CNLoadProduct;
import at.smartshop.database.columns.CNLocationList;
import at.smartshop.database.columns.CNLocationSummary;
import at.smartshop.database.columns.CNLoginPage;
import at.smartshop.database.columns.CNNavigationMenu;
import at.smartshop.database.columns.CNProduct;
import at.smartshop.database.columns.CNProductSummary;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.pages.ConsumerSearch;
import at.smartshop.pages.ConsumerSummary;
import at.smartshop.pages.LocationList;
import at.smartshop.pages.LocationSummary;
import at.smartshop.pages.Login;
import at.smartshop.pages.NavigationBar;
import at.smartshop.pages.ProductSummary;
import at.smartshop.sos.pages.LoadAdvana;
import at.smartshop.sos.pages.LoadDeviceID;
import at.smartshop.sos.pages.LoadGMA;
import at.smartshop.sos.pages.LoadProduct;
import at.smartshop.sos.pages.LoadProductPricing;
import at.smartshop.sos.pages.LoadQueue;
import at.smartshop.sos.pages.SOSHome;
import at.smartshop.tests.TestInfra;

@Listeners(at.framework.reportsetup.Listeners.class)
public class SOSLoad extends TestInfra {
	private ResultSets dataBase = new ResultSets();
	private SOSHome sosHome = new SOSHome();
	private Foundation foundation = new Foundation();
	private Strings strings = new Strings();
	private Numbers numbers = new Numbers();
	private Excel excel = new Excel();
	private TextBox textBox = new TextBox();
	private LoadGMA loadGma = new LoadGMA();
	private NavigationBar navigationBar = new NavigationBar();
	private LocationList locationList = new LocationList();
	private Dropdown dropDown = new Dropdown();
	private LocationSummary locationSummary = new LocationSummary();
	private ConsumerSearch consumerSearch = new ConsumerSearch();
	private LoadProduct loadProduct = new LoadProduct();
	private LoadDeviceID loadDeviceID = new LoadDeviceID();
	private LoadAdvana loadAdvana = new LoadAdvana();
	private DateAndTime dateAndTime = new DateAndTime();
	private LoadProductPricing loadProductPricing = new LoadProductPricing();
	
	private Map<String, String> rstProductSummaryData;
	private Map<String, String> rstLocationListData;
	private Map<String, String> rstLoadProduct;
	private Map<String, String> rstGmaUser;
	private Map<String, String> rstLocationSummaryData;
	private Map<String, String> rstNavigationMenuData;
	private Map<String, String> rstLoginPageData;
	private Map<String, String> rstProductData;

	int requiredValue = numbers.generateRandomNumber(0, 999999);
	String requiredDatas = strings.getRandomCharacter();
	String requiredString = (requiredDatas + Constants.DELIMITER_HASH + requiredDatas + Constants.DELIMITER_HASH
			+ requiredDatas + "1234" + Constants.DELIMITER_HASH + "25" + Constants.DELIMITER_HASH + requiredDatas
			+ "@gmail.com" + Constants.DELIMITER_HASH + String.valueOf(requiredValue) + Constants.DELIMITER_HASH
			+ requiredDatas + Constants.DELIMITER_HASH + String.valueOf(requiredValue) + Constants.DELIMITER_HASH
			+ requiredDatas + "group");

	@Test(description = "117462-This test validate the Success message after updaloding the GMA Template")
	public void verifySuccessMessage() {
		try {
			final String CASE_NUM = "117462";
			// Reading the Database
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
			rstLoadProduct = dataBase.getLoadProductData(Queries.LOAD_PRODUCT, CASE_NUM);
			rstGmaUser = dataBase.getGmaUserData(Queries.GMA_USER, CASE_NUM);
			rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);

			// Navigating to the Global Market Account page
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.SOS_CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.sosLogin(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			sosHome.selectOrginazation(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Configuring the Fields in GMA page
			String requiredString = (requiredDatas + Constants.DELIMITER_HASH + requiredDatas + Constants.DELIMITER_HASH
					+ requiredDatas + Constants.DELIMITER_HASH + "5" + Constants.DELIMITER_HASH + requiredDatas
					+ "@gmail.com" + Constants.DELIMITER_HASH + String.valueOf(requiredValue) + Constants.DELIMITER_HASH
					+ requiredDatas + Constants.DELIMITER_HASH + String.valueOf(requiredValue)
					+ Constants.DELIMITER_HASH + requiredDatas + "group");

			excel.writeToExcel(FilePath.GMA_ACCOUNT_TEMPLATE, loadGma.SHEET,
					rstProductSummaryData.get(CNProductSummary.ITERATION_COUNT), requiredString);
			loadGma.gMAUser(rstLocationListData.get(CNLocationList.LOCATION_NAME), rstGmaUser.get(CNGmaUser.PIN_VALUE),
					rstGmaUser.get(CNGmaUser.START_BALANCE), FilePath.GMA_ACCOUNT_TEMPLATE,
					rstLoadProduct.get(CNLoadProduct.DELETE_EXISTING_PRODUCT));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LoadGMA.LBL_SUCCESS));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "117465-Verify whether Payroll ID and Pay Cycle Group are mandatory to be entered in template")
	public void PayrollIDAndPayCycleUploadNonMandatory() {
		try {
			final String CASE_NUM = "117465";

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
			rstLoadProduct = dataBase.getLoadProductData(Queries.LOAD_PRODUCT, CASE_NUM);
			rstGmaUser = dataBase.getGmaUserData(Queries.GMA_USER, CASE_NUM);

			// Login into SOS application
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.SOS_CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.sosLogin(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// select Organization
			sosHome.selectOrginazation(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(SOSHome.MENU);

			// construct string with no value for payroll id and payroll group
			CustomisedAssert.assertTrue(foundation.isDisplayed(LoadGMA.BTN_SUBMIT));
			int requiredValue = numbers.generateRandomNumber(0, 999999);
			String requiredData = strings.getRandomCharacter();
			String requiredString = (requiredData + "#" + requiredData + "#" + requiredData + "#" + "5" + "#"
					+ requiredData + "@gmail.com" + "#" + String.valueOf(requiredValue) + "#" + requiredData + "#" + " "
					+ "#" + " ");

			// Write excel and upload file
			excel.writeToExcel(FilePath.GMA_ACCOUNT_TEMPLATE, loadGma.SHEET,
					rstProductSummaryData.get(CNProductSummary.ITERATION_COUNT), requiredString);
			loadGma.gMAUser(rstLocationListData.get(CNLocationList.LOCATION_NAME), rstGmaUser.get(CNGmaUser.PIN_VALUE),
					rstGmaUser.get(CNGmaUser.START_BALANCE), FilePath.GMA_ACCOUNT_TEMPLATE,
					rstLoadProduct.get(CNLoadProduct.DELETE_EXISTING_PRODUCT));
			foundation.waitforElement(LoadGMA.LBL_SUCCESS, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LoadGMA.LBL_SUCCESS));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	// SOS-12754 user story under Consumer Subsidy
	@Test(description = "166958 - Verify the updated column data in consumer template from sos load")
	public void verifyUpdatedColumnDataOfSubsidyGroup() {

		try {
			final String CASE_NUM = "166958";

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
			rstGmaUser = dataBase.getGmaUserData(Queries.GMA_USER, CASE_NUM);

			// Login into SOS application
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.SOS_CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.sosLogin(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(SOSHome.LANDING_PAGE_HEADING));

			// select Organization and navigate to menu
			sosHome.selectOrginazation(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			CustomisedAssert.assertTrue(foundation.isDisplayed(SOSHome.PAGE_HEADING));

			Map<String, String> excelData = excel.getExcelAsMap(FilePath.GMA_ACCOUNT_TEMPLATE,
					rstGmaUser.get(CNGmaUser.SHEET_NAME));
			List<String> actualColumnNames = new ArrayList<String>(excelData.keySet());
			List<String> expectedColumnNames = Arrays
					.asList(rstGmaUser.get(CNGmaUser.COLUMN_NAME).split(Constants.DELIMITER_TILD));
			sosHome.verifyColumnNames(expectedColumnNames, actualColumnNames);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	// SOS-12754 user story under Consumer Subsidy
	@Test(description = "166959 - Verify the updated column data in consumer template from sos load when entered valid value.")
	public void verifyUpdatedColumnDataOfSubsidyGroupWithValidValue() {
		final String CASE_NUM = "166959";

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

			// Setting GMA Subsidy ON with Group Names
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menus.get(1));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			String value = dropDown.getSelectedItem(LocationSummary.DPD_GMA_SUBSIDY);
			CustomisedAssert.assertEquals(value, requiredData.get(1));
			dropDown.selectItem(LocationSummary.DPD_GMA_SUBSIDY, requiredData.get(0), Constants.TEXT);
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

			// Configuring the Fields in GMA page and Uploading Template in SOS Load
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
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSummary.TXT_SUBSIDY_GROUP));
			subsidyName = dropDown.getSelectedItem(ConsumerSummary.DPD_SUBSIDY_GROUP_NAME);
			CustomisedAssert.assertEquals(subsidyName, requiredData.get(2));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {

			// resetting test data
			locationSummary.subsidyResettingOff(menus.get(1), rstLocationListData.get(CNLocationList.LOCATION_NAME),
					requiredData.get(1));
		}
	}

	// SOS-12754 user story under Consumer Subsidy
	@Test(description = "166960 - Verify the updated column data in consumer template from sos load when entered Invalid value.")
	public void verifyUpdatedColumnDataOfSubsidyGroupWithInValidValue() {
		final String CASE_NUM = "166960";

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

			// Setting GMA Subsidy ON with Group Names
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menus.get(1));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			String value = dropDown.getSelectedItem(LocationSummary.DPD_GMA_SUBSIDY);
			CustomisedAssert.assertEquals(value, requiredData.get(1));
			dropDown.selectItem(LocationSummary.DPD_GMA_SUBSIDY, requiredData.get(0), Constants.TEXT);
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

			// Configuring the Fields in GMA page and Uploading Template in SOS Load
			String requiredStringData = (requiredString + Constants.DELIMITER_HASH + requiredDatas + "TopOff");
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
			foundation.threadWait(Constants.THREE_SECOND);
			String subsidyName = consumerSearch.getSubsidyName();
			CustomisedAssert.assertTrue(subsidyName.isEmpty());
			foundation.click(consumerSearch.objFirstNameCell(consumerSearch.getConsumerFirstName()));
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSummary.LBL_CONSUMER_SUMMARY));
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSummary.TXT_SUBSIDY_GROUP));
			subsidyName = dropDown.getSelectedItem(ConsumerSummary.DPD_SUBSIDY_GROUP_NAME);
			CustomisedAssert.assertEquals(subsidyName, requiredData.get(4));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {

			// resetting test data
			locationSummary.subsidyResettingOff(menus.get(1), rstLocationListData.get(CNLocationList.LOCATION_NAME),
					requiredData.get(1));
		}
	}

	// SOS-12754 user story under Consumer Subsidy
	@Test(description = "166961 - Verify the updated column data in consumer template from sos load when entered No value.")
	public void verifyUpdatedColumnDataOfSubsidyGroupWithNoValue() {
		final String CASE_NUM = "166961";

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

			// Setting GMA Subsidy ON with Group Names
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menus.get(1));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			String value = dropDown.getSelectedItem(LocationSummary.DPD_GMA_SUBSIDY);
			CustomisedAssert.assertEquals(value, requiredData.get(1));
			dropDown.selectItem(LocationSummary.DPD_GMA_SUBSIDY, requiredData.get(0), Constants.TEXT);
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

			// Configuring the Fields in GMA page and Uploading Template in SOS Load
			excel.writeToExcel(FilePath.GMA_ACCOUNT_TEMPLATE, rstGmaUser.get(CNGmaUser.SHEET_NAME),
					rstProductSummaryData.get(CNProductSummary.ITERATION_COUNT), requiredString);
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
			foundation.threadWait(Constants.THREE_SECOND);
			String subsidyName = consumerSearch.getSubsidyName();
			CustomisedAssert.assertTrue(subsidyName.isEmpty());
			foundation.click(consumerSearch.objFirstNameCell(consumerSearch.getConsumerFirstName()));
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSummary.LBL_CONSUMER_SUMMARY));
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSummary.TXT_SUBSIDY_GROUP));
			subsidyName = dropDown.getSelectedItem(ConsumerSummary.DPD_SUBSIDY_GROUP_NAME);
			CustomisedAssert.assertEquals(subsidyName, requiredData.get(4));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {

			// resetting test data
			locationSummary.subsidyResettingOff(menus.get(1), rstLocationListData.get(CNLocationList.LOCATION_NAME),
					requiredData.get(1));
		}
	}

	// SOS-12754 user story under Consumer Subsidy
	@Test(description = "166962 - Verify the updated column data in consumer template from sos load when 'subsidy group' in ADM is set to No")
	public void verifyUpdatedColumnDataOfSubsidyGroupSetToNoInAdm() {
		final String CASE_NUM = "166962";

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

			// Setting GMA Subsidy ON with Group Names
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menus.get(1));
			foundation.threadWait(Constants.THREE_SECOND);
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			String value = dropDown.getSelectedItem(LocationSummary.DPD_GMA_SUBSIDY);
			if (value == requiredData.get(1)) {
				foundation.click(LocationSummary.BTN_SAVE);
				foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			} else {
				dropDown.selectItem(LocationSummary.DPD_GMA_SUBSIDY, requiredData.get(1), Constants.TEXT);
				foundation.click(LocationSummary.BTN_SAVE);
				foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			}
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

			// Configuring the Fields in GMA page and Uploading Template in SOS Load
			String requiredStringData = (requiredString + Constants.DELIMITER_HASH + requiredDatas + "TopOff");
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
			List<String> tableHeaders = consumerSearch.getConsumerHeaders();
			CustomisedAssert.assertFalse(tableHeaders.contains(requiredData.get(2)));
			foundation.threadWait(Constants.THREE_SECOND);
			foundation.click(consumerSearch.objFirstNameCell(consumerSearch.getConsumerFirstName()));
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSummary.LBL_CONSUMER_SUMMARY));
			CustomisedAssert.assertFalse(foundation.isDisplayed(ConsumerSummary.TXT_SUBSIDY_GROUP));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {

			// resetting test data
			locationSummary.subsidyResettingOff(menus.get(1), rstLocationListData.get(CNLocationList.LOCATION_NAME),
					requiredData.get(1));
		}
	}
	
	/**
	 * @author sakthir 
	 * Date:08-11-2022
	 */
	@Test(description = "152464-SOSLoad - Login"
			+"152465-SOSLoad - Failed Login")
	public void verifyLoginWithInvalidAndValidCredentialsAndNavigationMenuAfterLogin() {
		final String CASE_NUM = "152464";

		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		 rstLoginPageData = dataBase.getLoginPageData(Queries.LOGIN_PAGE, CASE_NUM);

		List<String> invalidData = Arrays
				.asList(rstLoginPageData.get(CNLoginPage.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> invalidMsg = Arrays
				.asList(rstLoginPageData.get(CNLoginPage.INVALID).split(Constants.DELIMITER_TILD));
		List<String> navMenu = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION).split(Constants.DELIMITER_TILD));

		try {
			
			//Validate Login with Incorrect User name with correct password error msg
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.SOS_CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.sosLogin(invalidData.get(0),propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.getText(SOSHome.LOGIN_USERNAME_ERROR).equals(invalidMsg.get(0)));
			
			//Validate Login with correct User name with Incorrect password error msg
			login.sosLogin(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),invalidData.get(1));
			CustomisedAssert.assertTrue(foundation.getText(SOSHome.LOGIN_PASSWORD_ERROR).equals(invalidMsg.get(1)));
			
			// Login into SOS application with valid User name and password
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.SOS_CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.sosLogin(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(SOSHome.LANDING_PAGE_HEADING));
			
			//Validate Navigation menu
			foundation.threadWait(Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.getTextofListElement(SOSHome.NAV_MENU).equals(navMenu));
			
		}
		catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
	
	/**
	 * @author sakthir 
	 * Date:09-11-2022
	 */
	@Test(description = "152468-SOSLoad - Org Search"
			+"152467-SOSLoad - Org List"
			+"152466-SOSLoad - Logout")
	public void verifySearchORGFunctionalityAndVerifyAllADMOrgsPresentInSOSOrgList() {
		final String CASE_NUM = "152468";
		     
		     // Reading test data from DataBase
				rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
				
	try {
			// Login into SOS application with valid User
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.SOS_CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.sosLogin(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
				propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(SOSHome.LANDING_PAGE_HEADING));
			
//			//Search org
//			navigationBar.selectOrganization(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));
			
			//Get Org list from SOS loader
			foundation.click(NavigationBar.DPD_ORG);
			int SOSOrg=foundation.getSizeofListElement(Login.DPD_ORG);
			
			//verify log out button and sign out from login user
			foundation.click(NavigationBar.DPD_ORG);
			sosHome.logout();
			
			//login as super user in ADM
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			
			//get Org list from ADM
			foundation.click(NavigationBar.DPD_ORG);
			int ADMOrg=foundation.getSizeofListElement(Login.DPD_ORG);
			
			//verify SOS org and ADM org
			System.out.println(ADMOrg);
			System.out.println(SOSOrg);
			CustomisedAssert.assertEquals(SOSOrg,ADMOrg);
			
			
		}
		catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
	
	/**
	 * @author Afrose
	 * Date:10-11-2022
	 */
	@Test(description = "152469-SOSLoad - Upload Product"
			+"223164-verify Uploading proper formatted Excel and verify In ADM location Summary Page Under Product Tab with delete existing as 'No'")
	public void verifyThatProductUploadedFromSOSLoadIsGettingAddedInProductTabForLocationInADM() {
		final String CASE_NUM = "152469";
		     
		    // Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLoadProduct = dataBase.getLoadProductData(Queries.LOAD_PRODUCT, CASE_NUM);
			rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);
			
			List<String> menu = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
			List<String> location = Arrays
					.asList(rstLoadProduct.get(CNLoadProduct.LOAD_TYPE).split(Constants.DELIMITER_TILD));
			String deleteExisting =rstLoadProduct.get(CNLoadProduct.DELETE_EXISTING_PRODUCT);
			String requiredData = strings.getRandomCharacter()+"RoseMilk";
			String requiredString = ( requiredData  + "#" + " " + "#" + "Milk" + "#" + "234" + "#"
					+ "Juice" + "#" + "Drinks" + "#" + " " + "#" + " " + "#" + " " + "#" + " " + "#" + "11" + "#" + "13" + "#" + " " 
					+ "#" + " " + "#" + " " + "#" + " " + "#" + " " + "#" + " " + "#" + " " + "#" + " " + "#" + " " + "#" + " " + "#" + " " 
					+ "#" + "Below Maximum" + "#" + "11" + "#" + "13" + "#" + " " + "#" + " " + "#" + " " + "#" + " " );
		try {
			
			// Login into SOS application with valid User
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.SOS_CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.sosLogin(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
				
			sosHome.selectOrginazation(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(SOSHome.LANDING_PAGE_HEADING));
			
			//navigate to product
			navigationBar.navigateToMenuItem(menu.get(0));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LoadProduct.LBL_PRODUCT));
			
			//Select location for product 
			foundation.threadWait(Constants.THREE_SECOND);
			foundation.click(LoadProduct.BTN_SELECTALL);
			CustomisedAssert.assertTrue(foundation.getBGColor(LoadProduct.DPD_LOCATION).equals(location.get(2)));
			foundation.click(LoadProduct.BTN_SELECTNONE);
			CustomisedAssert.assertTrue(foundation.getBGColor(LoadProduct.DPD_LOCATION).equals(location.get(3)));
			foundation.threadWait(Constants.THREE_SECOND);
			foundation.click(loadProduct.clickLocation(location.get(0)));
			
			//Creating product with price via Template in SOS Load
			foundation.threadWait(Constants.THREE_SECOND);
//			String requiredStringData = (requiredString + Constants.DELIMITER_HASH + data.get(0));
			excel.writeToExcel(FilePath.PRODUCT_TEMPLATE,loadProduct.SHEET,"1#1#2",requiredString);
			textBox.enterText(LoadProduct.BTN_CHOOSE_FILE, FilePath.PRODUCT_TEMPLATE);
			if(!foundation.getText(LoadProduct.DELETE_EXISTING_PRODUCT).equals(deleteExisting))
			foundation.click(LoadProduct.BTN_SUBMIT);
			sosHome.logout();
			foundation.threadWait(Constants.THREE_SECOND);
			
			//log in ADM as super user
			navigationBar.launchBrowserAsSuperAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			
			//select product added location from location list
			locationList.selectLocationName(location.get(0));
			foundation.scrollIntoViewElement(LocationSummary.TAB_PRODUCTS);
			foundation.click(LocationSummary.TAB_PRODUCTS);
			foundation.waitforElementToBeVisible(LocationSummary.TBL_PRODUCTS_GRID, 5);
			foundation.threadWait(Constants.THREE_SECOND);
			textBox.enterText(LocationSummary.TXT_PRODUCT_FILTER, requiredData );
			foundation.threadWait(Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.getText(LocationSummary.TBL_PRODUCTS_GRID).contains(requiredData));
		}
		catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		finally {
			foundation.click(LocationSummary.PRODUCT_NAME);
			foundation.waitforElement(LocationSummary.BTN_EDIT_PRODUCT, Constants.MEDIUM_TIME);
			foundation.threadWait(Constants.THREE_SECOND);
			foundation.click(LocationSummary.BTN_EDIT_PRODUCT);
			foundation.threadWait(Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(ProductSummary.LBL_PRODUCT_SUMMMARY));
			dropDown.selectItem(ProductSummary.DPD_IS_DISABLED, deleteExisting, Constants.TEXT);
			foundation.click(ProductSummary.BTN_SAVE);
			foundation.threadWait(Constants.SHORT_TIME);
		}
	}
	
	

	/**
	 * @author sakthir 
	 * Date:10-11-2022
	 */
		@Test(description = "152477-SOSLoad - Device IDs - Failure")
		public void verifyDeviceIDFailureWithInvalidData() {
			final String CASE_NUM = "152477";

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			
			List<String> menu = Arrays
					.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
			String location =rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION);
			
			try {
				// Login to ADM
				browser.navigateURL(
						propertyFile.readPropertyFile(Configuration.SOS_CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
				login.sosLogin(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
						propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
					
				sosHome.selectOrginazation(
						propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
				CustomisedAssert.assertTrue(foundation.isDisplayed(SOSHome.LANDING_PAGE_HEADING));
				
			    //navigate to Device Id
				foundation.threadWait(Constants.THREE_SECOND);
				navigationBar.navigateToMenuItem(menu.get(0));
				CustomisedAssert.assertTrue(foundation.isDisplayed(LoadDeviceID.LBL_MIDDID));
				
				//select processor and upload invalid file
				foundation.threadWait(Constants.SHORT_TIME);
				foundation.click(loadDeviceID.clickLocation(menu.get(1)));
				int requiredValue = numbers.generateRandomNumber(0, 999999);
				String requiredData = strings.getRandomCharacter();
				String requiredString = (requiredValue + "#" + requiredData + "#" + "https://some.url.com" + "#" + requiredData + "#"
						+ requiredData + "#" + requiredValue + "#" + requiredValue + "#" + requiredValue );

				// Write excel and upload file
				foundation.threadWait(Constants.THREE_SECOND);
				excel.writeToExcel(FilePath.MIDDID_TEMPLATE, loadDeviceID.SHEET,
					menu.get(2) , requiredString);
				textBox.enterText(LoadDeviceID.BTN_CHOOSE_FILE,FilePath.MIDDID_TEMPLATE);
				foundation.threadWait(Constants.SHORT_TIME);
				foundation.click(LoadDeviceID.BTN_SUBMIT);
				foundation.threadWait(Constants.THREE_SECOND);
				
				//verify the error page
				CustomisedAssert.assertTrue(foundation.isDisplayed(LoadDeviceID.LBL_ERROR_MSG));
				CustomisedAssert.assertTrue(foundation.getText(LoadDeviceID.TBL_ERROR_DATA).contains(location));
				
				}catch (Exception exc) {
					TestInfra.failWithScreenShot(exc.toString());
				}
	}
		
		/**
		 * @author sakthir 
		 * Date:11-11-2022
		 */
		@Test(description = "152475-SOSLoad - Global Market Account - Failure")
		public void verifyGMAAccountFailureWithInvalidData() {
			try {
				final String CASE_NUM = "152475";

				// Reading test data from DataBase
				rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
				rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);
				rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
				rstLoadProduct = dataBase.getLoadProductData(Queries.LOAD_PRODUCT, CASE_NUM);
				rstGmaUser = dataBase.getGmaUserData(Queries.GMA_USER, CASE_NUM);

				// Login into SOS application
				browser.navigateURL(
						propertyFile.readPropertyFile(Configuration.SOS_CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
				login.sosLogin(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
						propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

				// select Organization
				sosHome.selectOrginazation(
						propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
				foundation.click(SOSHome.MENU);

				// construct string with invalid GMA Template
				CustomisedAssert.assertTrue(foundation.isDisplayed(LoadGMA.BTN_SUBMIT));
				String requiredData = strings.getRandomCharacter();
				String requiredString = (requiredData + "#" + " " + "#" + " " + "#" + " " + "#"
						+ " " + "#" + " " + "#" + " " + "#" + " " + "#" + " " );

				// Write excel and upload file
				excel.writeToExcel(FilePath.GMA_ACCOUNT_TEMPLATE, loadGma.SHEET,
						rstProductSummaryData.get(CNProductSummary.ITERATION_COUNT), requiredString);
				foundation.threadWait(Constants.SHORT_TIME);
				loadGma.gMAUser(rstLocationListData.get(CNLocationList.LOCATION_NAME), rstGmaUser.get(CNGmaUser.PIN_VALUE),
						rstGmaUser.get(CNGmaUser.START_BALANCE), FilePath.GMA_ACCOUNT_TEMPLATE,
						rstLoadProduct.get(CNLoadProduct.DELETE_EXISTING_PRODUCT));
				foundation.waitforElement(LoadGMA.LBL_ERROR_MSG, Constants.SHORT_TIME);
				foundation.threadWait(Constants.SHORT_TIME);
				CustomisedAssert.assertTrue(foundation.isDisplayed(LoadGMA.LBL_ERROR_MSG));

				//verify error page
				foundation.threadWait(Constants.SHORT_TIME);
				CustomisedAssert.assertTrue(foundation.isDisplayed(LoadGMA.LBL_TITLE_ERROR_PAGE));
				foundation.threadWait(Constants.SHORT_TIME);
				CustomisedAssert.assertTrue(foundation.isDisplayed(LoadGMA.LBL_SUBTITLE_ERROR_PAGE));

			} catch (Exception exc) {
				TestInfra.failWithScreenShot(exc.toString());
			}
		}
		
		/**
		 * @author sakthir 
		 * Date:14-11-2022
		 */
		@Test(description ="152470-SOSLoad - Upload Product - Failure")
		public void verifyProductUploadFailureWithInvalidData() {
			final String CASE_NUM = "152470";
			     
			     // Reading test data from DataBase
					rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
					rstLoadProduct = dataBase.getLoadProductData(Queries.LOAD_PRODUCT, CASE_NUM); 
					
					String delectExisting =rstLoadProduct.get(CNLoadProduct.DELETE_EXISTING_PRODUCT);
					String menu =rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM);
					List<String> location =Arrays
							.asList(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION).split(Constants.DELIMITER_TILD));
		try {
				
				// Login into SOS application with valid User
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.SOS_CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.sosLogin(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
				
			sosHome.selectOrginazation(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(SOSHome.LANDING_PAGE_HEADING));
			
			//navigate to product
			navigationBar.navigateToMenuItem(menu);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LoadProduct.LBL_PRODUCT));
					
			//write Invalid excel and upload the file
			foundation.threadWait(Constants.THREE_SECOND);
			foundation.click(loadProduct.clickLocation(location.get(0)));
			
			//Creating product with improper price via Template in SOS Load
			foundation.threadWait(Constants.THREE_SECOND);
			String requiredString = (" " + "#" + " " + "#" + "Milk" + "#" + "234" + "#"
					+ "Juice" + "#" + "Drinks" + "#" + " " + "#" + " " + "#" + " " + "#" + " " + "#" + "11" + "#" + "13" + "#" + " " 
					+ "#" + " " + "#" + " " + "#" + " " + "#" + " " + "#" + " " + "#" + " " + "#" + " " + "#" + " " + "#" + " " + "#" + " " 
					+ "#" + "Below Maximum" + "#" + "11" + "#" + "13" + "#" + " " + "#" + " " + "#" + " " + "#" + " " );
			excel.writeToExcel(FilePath.PRODUCT_TEMPLATE,loadProduct.SHEET,location.get(1), requiredString);
			textBox.enterText(LoadProduct.BTN_CHOOSE_FILE, FilePath.PRODUCT_TEMPLATE);
			foundation.threadWait(Constants.SHORT_TIME);
			if(!foundation.getText(LoadProduct.DELETE_EXISTING_PRODUCT).equals(delectExisting))
				foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(LoadProduct.BTN_SUBMIT);
			foundation.threadWait(Constants.TEN_SECOND);
			
			//verify the error page
			CustomisedAssert.assertTrue(foundation.isDisplayed(LoadProduct.LBL_PRODUCT_ERROR));
			
			
		}
		catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

		/**
		 * @author sakthir 
		 * Date:14-11-2022
		 */
		@Test(description = "152478-SOSLoad - Add Home Commercial"
				+"152480-SOSLoad - Remove Home Commercial"
				+"152442-SOS-21268- Verify the upload is successful if same image is loaded for a different commercial name")
		public void verifyAddAndRemoveHomeCommercialAndSameImageUploadWithDifferentNameSuccessfully() {
			
				final String CASE_NUM = "152478";

				// Reading test data from DataBase
				rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
				rstGmaUser = dataBase.getGmaUserData(Queries.GMA_USER, CASE_NUM);
				
				List<String> menu =Arrays
						.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
				String currentDate = dateAndTime.getDateAndTime(Constants.REGEX_DD_MM_YYYY, Constants.TIME_ZONE_INDIA);
				List<String> location =Arrays
						.asList(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION).split(Constants.DELIMITER_TILD));
//				String requiredString = ("c9fe79b15aedea1c5d957187573f2b6e");
		try {
			
				// Login into SOS application
				browser.navigateURL(
						propertyFile.readPropertyFile(Configuration.SOS_CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
				login.sosLogin(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
						propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
				sosHome.selectOrginazation(
						propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
				CustomisedAssert.assertTrue(foundation.isDisplayed(SOSHome.LANDING_PAGE_HEADING));
				
				//navigate to home commercial
				foundation.threadWait(Constants.THREE_SECOND);
				navigationBar.navigateToMenuItem(menu.get(0));
								
				//Upload image and File writing excel with location Id
				foundation.threadWait(Constants.THREE_SECOND);
				loadAdvana.addHomeCommercial(location.get(0),location.get(1),location.get(2),(
						propertyFile.readPropertyFile(Configuration.SOS_LOCATION_ID, FilePath.PROPERTY_CONFIG_FILE)),location.get(3),currentDate);
				CustomisedAssert.assertTrue(foundation.getText(LoadAdvana.GET_MSG).equals(location.get(5)));
				
				//verify in Queue
				foundation.threadWait(Constants.THREE_SECOND);
				navigationBar.navigateToMenuItem(menu.get(1));
				CustomisedAssert.assertTrue(foundation.getText(LoadQueue.TBL_DATA).contains(location.get(8)));

				//navigate to home commercial
				foundation.threadWait(Constants.THREE_SECOND);
				navigationBar.navigateToMenuItem(menu.get(0));
				
				//Upload same image and File with different commercial name
				foundation.threadWait(Constants.THREE_SECOND);
				loadAdvana.addHomeCommercial(location.get(0),location.get(9),location.get(2),(
						propertyFile.readPropertyFile(Configuration.SOS_LOCATION_ID, FilePath.PROPERTY_CONFIG_FILE)),location.get(3),currentDate);
				foundation.threadWait(Constants.SHORT_TIME);
				CustomisedAssert.assertTrue(foundation.getText(LoadAdvana.GET_MSG).equals(location.get(5)));
				
				//verify in Queue
				foundation.threadWait(Constants.THREE_SECOND);
				navigationBar.navigateToMenuItem(menu.get(1));
				foundation.threadWait(Constants.SHORT_TIME);
				CustomisedAssert.assertTrue(foundation.getText(LoadQueue.TBL_DATA).contains(location.get(8)));
				sosHome.logout();
				foundation.threadWait(Constants.THREE_SECOND);
				
				// Launch ADM as super
				navigationBar.launchBrowserAsSuperAndSelectOrg(
						propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
				
				//select commercial Added location and Click commercial Tab
				CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));
				locationList.selectLocationName(location.get(6));
				CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.LBL_LOCATION_SUMMARY));
				locationSummary.selectHomeCommercialTab(location.get(7));
				foundation.threadWait(Constants.SHORT_TIME);
				textBox.enterText(LocationSummary.TXT_CMR_FILTER, location.get(1));
				CustomisedAssert.assertTrue(foundation.getText(LocationSummary.TBL_HOME_COMMERCIAL).contains(location.get(1)));
				foundation.threadWait(Constants.SHORT_TIME);
				textBox.enterText(LocationSummary.TXT_CMR_FILTER, location.get(9));
				CustomisedAssert.assertTrue(foundation.getText(LocationSummary.TBL_HOME_COMMERCIAL).contains(location.get(9)));
				login.logout();
				foundation.threadWait(Constants.THREE_SECOND);
				
				// Login into SOS application
				browser.navigateURL(
						propertyFile.readPropertyFile(Configuration.SOS_CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
				login.sosLogin(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
						propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
				sosHome.selectOrginazation(
						propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
				CustomisedAssert.assertTrue(foundation.isDisplayed(SOSHome.LANDING_PAGE_HEADING));
				
				//navigate to home commercial
				foundation.threadWait(Constants.THREE_SECOND);
				navigationBar.navigateToMenuItem(menu.get(0));
				foundation.threadWait(Constants.SHORT_TIME);
				loadAdvana.removeHomeCommercial(location.get(4),location.get(2),(
						propertyFile.readPropertyFile(Configuration.SOS_LOCATION_ID, FilePath.PROPERTY_CONFIG_FILE)),location.get(5));
				
				//verify in Queue
				navigationBar.navigateToMenuItem(menu.get(1));
				foundation.threadWait(Constants.THREE_SECOND);
				CustomisedAssert.assertTrue(foundation.getText(LoadQueue.TBL_DATA).contains(location.get(8)));
				sosHome.logout();
				foundation.threadWait(Constants.THREE_SECOND);
				
				// Launch ADM as super
				navigationBar.launchBrowserAsSuperAndSelectOrg(
						propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
				
				//select commercial Added location and Click commercial Tab
				foundation.threadWait(Constants.THREE_SECOND);
				CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));
				foundation.threadWait(Constants.SHORT_TIME);
				locationList.selectLocationName(location.get(6));
				CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.LBL_LOCATION_SUMMARY));
				foundation.threadWait(Constants.SHORT_TIME);
				locationSummary.selectHomeCommercialTab(location.get(7));
				foundation.threadWait(Constants.SHORT_TIME);
				textBox.enterText(LocationSummary.TXT_CMR_FILTER, location.get(1));
				
				//verify removed commercial
				foundation.threadWait(Constants.SHORT_TIME);
				CustomisedAssert.assertFalse(foundation.isDisplayed(LocationSummary.TBL_HOME_COMMERCIAL));
				
			} catch (Exception exc) {
				TestInfra.failWithScreenShot(exc.toString());
			}
		}
		
		/**
		 * @author sakthir 
		 * Date:16-11-2022
		 */
		@Test(description = "152479-SOSLoad - Add Home Commercial - Failure"
				+"152481-SOSLoad - Remove Home Commercial - Failure")
		public void verifyHomeCommercialUploadFailureWithInvalidDataWhileAddAndRemove() {
			
				final String CASE_NUM = "152479";

				// Reading test data from DataBase
				rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
				rstGmaUser = dataBase.getGmaUserData(Queries.GMA_USER, CASE_NUM);
				
				List<String> menu =Arrays
						.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
				String currentDate = dateAndTime.getDateAndTime(Constants.REGEX_DD_MM_YYYY, Constants.TIME_ZONE_INDIA);
				List<String> location =Arrays
						.asList(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION).split(Constants.DELIMITER_TILD));
				String requiredString = ("c9fe79b15");
		try {
			
				// Login into SOS application
				browser.navigateURL(
						propertyFile.readPropertyFile(Configuration.SOS_CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
				login.sosLogin(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
						propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
				sosHome.selectOrginazation(
						propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
				CustomisedAssert.assertTrue(foundation.isDisplayed(SOSHome.LANDING_PAGE_HEADING));
				
				//navigate to home commercial
				navigationBar.navigateToMenuItem(menu.get(0));
								
				//Upload image and File writing excel with Invalid location Id
				loadAdvana.addHomeCommercial(location.get(0),location.get(1),location.get(2),requiredString,location.get(3),currentDate);
				CustomisedAssert.assertTrue(foundation.getText(LoadAdvana.GET_MSG).contains(location.get(5)));
				
				//verify in Queue
				navigationBar.navigateToMenuItem(menu.get(1));
				foundation.threadWait(Constants.SHORT_TIME);
				CustomisedAssert.assertTrue(foundation.getText(LoadQueue.TBL_DATA).contains(location.get(8)));
				
				//navigate to home commercial
				navigationBar.navigateToMenuItem(menu.get(0));
				foundation.threadWait(Constants.SHORT_TIME);
				loadAdvana.removeHomeCommercial(location.get(4),location.get(2), requiredString,location.get(5));
				
				//verify in Queue
				navigationBar.navigateToMenuItem(menu.get(1));
				foundation.threadWait(Constants.SHORT_TIME);
				CustomisedAssert.assertTrue(foundation.getText(LoadQueue.TBL_DATA).contains(location.get(8)));			

			} catch (Exception exc) {
				TestInfra.failWithScreenShot(exc.toString());
			}
		}
		
		/**
		 * @author sakthir 
		 * Date:16-11-2022
		 */
		@Test(description ="152472-SOSLoad - Product Pricing - Failure"
                     +"152471-SOSLoad - Product Pricing")
		public void verifyProductPricingUploadWithInvalidDataAndValidData() {
			final String CASE_NUM = "152472";
			     
			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
							
			List<String> menu =Arrays
					.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
			List<String> location =Arrays
					.asList(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION).split(Constants.DELIMITER_TILD));
			String data=("115334");        
		try {
				
			// Login into SOS application with valid User
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.SOS_CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.sosLogin(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
				
			sosHome.selectOrginazation(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(SOSHome.LANDING_PAGE_HEADING));
			
			//navigate to ProductPricing
			navigationBar.navigateToMenuItem(menu.get(0));
						
			//Creating product with improper Product pricing via Template in SOS Load
			foundation.threadWait(Constants.SHORT_TIME);
			String requiredString = ( "123456788" + "#" + "12.6" );
			excel.writeToExcel(FilePath.PRODUCT_PRICING_TEMPLATE,loadProductPricing.SHEET,location.get(2), requiredString);
			textBox.enterText(LoadProductPricing.BTN_CHOOSE_FILE, FilePath.PRODUCT_PRICING_TEMPLATE);
            textBox.enterText(LoadProductPricing.TXT_LOCATION_NUMBER, location.get(0));
            foundation.threadWait(Constants.THREE_SECOND);
			foundation.click(LoadProductPricing.BTN_SUBMIT);
			
			//verify Success msg page
			CustomisedAssert.assertTrue(foundation.isDisplayed(LoadProductPricing.LBL_UPDATE_PARAMETER));
			CustomisedAssert.assertTrue(foundation.getText(LoadProductPricing.TXT_MSG).contains(location.get(6)));
			foundation.click(LoadProductPricing.BTN_OK);
			CustomisedAssert.assertTrue(foundation.isDisplayed(SOSHome.LANDING_PAGE_HEADING));
			
			//verify the Queue page
			foundation.threadWait(Constants.THREE_SECOND);
			navigationBar.navigateToMenuItem(menu.get(1));
			CustomisedAssert.assertTrue(foundation.getText(LoadQueue.TBL_DATA).contains(location.get(1)));

            //navigate to ProductPricing
			foundation.threadWait(Constants.THREE_SECOND);
			navigationBar.navigateToMenuItem(menu.get(0));
						
			//Creating product with proper Product pricing via Template in SOS Load
			foundation.threadWait(Constants.SHORT_TIME);
			String price=("20.6");  
			excel.writeToExcel(FilePath.PRODUCT_PRICING_TEMPLATE,loadProductPricing.SHEET,location.get(2), data + "#" + price );
			textBox.enterText(LoadProductPricing.BTN_CHOOSE_FILE, FilePath.PRODUCT_PRICING_TEMPLATE);
                        textBox.enterText(LoadProductPricing.TXT_LOCATION_NUMBER, location.get(0));
			foundation.click(LoadProductPricing.BTN_SUBMIT);
			foundation.threadWait(Constants.THREE_SECOND);
			
			//verify the Queue page
			foundation.threadWait(Constants.THREE_SECOND);
			navigationBar.navigateToMenuItem(menu.get(1));
			CustomisedAssert.assertTrue(foundation.getText(LoadQueue.TBL_DATA).contains(location.get(3)));
			sosHome.logout();
			foundation.threadWait(Constants.THREE_SECOND);

			// Launch ADM as super
			navigationBar.launchBrowserAsSuperAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
				
			//select product added location from location list
			locationList.selectLocationName(location.get(5));
			foundation.scrollIntoViewElement(LocationSummary.TAB_PRODUCTS);
			foundation.click(LocationSummary.TAB_PRODUCTS);
			foundation.waitforElementToBeVisible(LocationSummary.TBL_PRODUCTS_GRID, 5);
			foundation.threadWait(Constants.THREE_SECOND);
			textBox.enterText(LocationSummary.TXT_PRODUCT_FILTER, data);
			foundation.threadWait(Constants.SHORT_TIME);
            CustomisedAssert.assertTrue(foundation.getText(LocationSummary.TBL_PRODUCTS_GRID).contains(price));
			
		}
		catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
        finally{
            locationSummary.enterPrice(data,location.get(4));
            foundation.click(LocationSummary.TXT_PRODUCT_FILTER);
            CustomisedAssert.assertTrue(foundation.getText(LocationSummary.TBL_PRODUCTS_GRID).contains(location.get(4)));
        }
	}
		
		/**
		 * @author sakthir 
		 * Date:06-01-2023
		 */
		@Test(description ="223154-Verify All Field In Load Product Parameters"
                +"223155-SOSLoad>Product>verify select All button Functionality"
				+"223156-SOSLoad>Product>verify select None button Functionality"
                +"223157-SOSLoad>Product>verify Load Type DropDown Options"
                +"223160-SOSLoad>Product>verify Choose File button"
				+"223161-SOSLoad>Product>verify Delete Existing Products field DropDown Options"
				+"223162-SOSLoad>Product>verify OnHandQty field DropDown Options"
				+"223159-verify Able to click any location and header message from location field in load Product Parameters")
		public void verifyAllFieldInLoadProductParameters() {
			final String CASE_NUM = "223154";
			     
			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLoadProduct = dataBase.getLoadProductData(Queries.LOAD_PRODUCT, CASE_NUM);
			rstProductData = dataBase.getProductData(Queries.PRODUCT, CASE_NUM);
							
			String menu =rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM);
			List<String> data =Arrays
					.asList(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION).split(Constants.DELIMITER_TILD));
			List<String> loadType =Arrays
					.asList(rstLoadProduct.get(CNLoadProduct.LOAD_TYPE).split(Constants.DELIMITER_TILD));
			List<String> deleteExisting =Arrays
					.asList(rstLoadProduct.get(CNLoadProduct.DELETE_EXISTING_PRODUCT).split(Constants.DELIMITER_TILD));
			List<String> onHandQty =Arrays
					.asList(rstProductData.get(CNProduct.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			
		try {
				
			// Login into SOS application with valid User
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.SOS_CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.sosLogin(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
				
			sosHome.selectOrginazation(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(SOSHome.LANDING_PAGE_HEADING));
			
			//navigate to Product
			navigationBar.navigateToMenuItem(menu);
			
			//verify All fields in load Product Parameters
		    loadProduct.verifyProductFields();
		    
		    //verify Select All button functionality
		    foundation.click(LoadProduct.BTN_SELECTALL);
		    CustomisedAssert.assertTrue(foundation.getBGColor(LoadProduct.DPD_LOCATION).equals(data.get(0)));
		   
		    //verify Select None button functionality
		    foundation.click(LoadProduct.BTN_SELECTNONE);
		    CustomisedAssert.assertTrue(foundation.getBGColor(LoadProduct.DPD_LOCATION).equals(data.get(1)));

		    //verify Load Type Dropdown Option
		    CustomisedAssert.assertTrue(foundation.getTextofListElement(LoadProduct.DPD_LOAD_TYPE).equals(loadType));

		    //verify Delete Existing Products Dropdown Option
		    CustomisedAssert.assertTrue(foundation.getTextofListElement(LoadProduct.DPD_DELETE_EXISTING_PRODUCT).equals(deleteExisting));
		   
		    //verify Update OnHandQty Dropdown Option
		    CustomisedAssert.assertTrue(foundation.getTextofListElement(LoadProduct.DPD_UPDATE_ONHAND_QTY).equals(onHandQty));
		    
		    //verify Able to click location from location list
		    foundation.click(loadProduct.clickLocation(data.get(2)));
		    CustomisedAssert.assertTrue(foundation.getBGColor(LoadProduct.SELECTED_LOCATION).equals(data.get(3)));
		    
		    //verify for header message in location field
		    CustomisedAssert.assertTrue(foundation.isDisplayed(LoadProduct.LBL_LOCATION_MESSAGE));
		    
		}
		catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
     
	}	
		
		/**
		 * @author sakthir 
		 * Date:10-01-2023
		 */
		@Test(description ="223158-SOSLoad>Product>verify location list match with ADM Location list")
		public void verifyAllADMLocationListsPresentInSOSLocationLists() {
			final String CASE_NUM = "223158";
			     
			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
							
			String menu =rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM);
			
		try {
				
			// Login into SOS application with valid User
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.SOS_CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.sosLogin(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
				
			sosHome.selectOrginazation(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONSOSLOAD_ORG, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(SOSHome.LANDING_PAGE_HEADING));
			
			//navigate to Product
			navigationBar.navigateToMenuItem(menu);
			
			//Get location count from location list in Load Product Parameters
			int SOSlocationCount=foundation.getSizeofListElement(LoadProduct.DPD_LOCATION);
			List<String> SOSlocationList=foundation.getTextofListElement(LoadProduct.DPD_LOCATION);
			sosHome.logout();
	
			//login as super user in ADM
			navigationBar.launchBrowserAsSuperAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONSOSLOAD_ORG, FilePath.PROPERTY_CONFIG_FILE));
			
			//get location count from location page
			int ADMlocationCount=foundation.getSizeofListElement(LocationList.GET_LOCATION_LIST);	
			List<String> ADMlocationList=foundation.getTextofListElement(LocationList.GET_LOCATION_LIST);
			
			//verify that ADM location list match with SOS location list count 
			CustomisedAssert.assertEquals(SOSlocationCount,ADMlocationCount);
			CustomisedAssert.assertTrue(SOSlocationList.equals(ADMlocationList));
		}
		catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} 
	}
		
		/**
		 * @author sakthir 
		 * Date:11-01-2023
		 */
		@Test(description ="223168-SOSLoad>DeviceIDs>verify Processor DropDown Options"
				+"223169-SOSLoad>DeviceIDs>verify Choose File button")
		public void verifyAllFieldAndProcessorFieldDropDownOptions() {
			final String CASE_NUM = "223168";
			     
			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
							
			String menu =rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM);
			List<String> option =Arrays
					.asList(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION).split(Constants.DELIMITER_TILD));
			
		try {
				
			// Login into SOS application with valid User
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.SOS_CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.sosLogin(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
				
			sosHome.selectOrginazation(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(SOSHome.LANDING_PAGE_HEADING));
			
			//navigate to DeviceId
			navigationBar.navigateToMenuItem(menu);
			
			//verify All Fields in DeviceID Page
			CustomisedAssert.assertTrue(foundation.isDisplayed(LoadDeviceID.LBL_MIDDID));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LoadDeviceID.LBL_PROCESSOR));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LoadDeviceID.PROCESSOR_MESSAGE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LoadDeviceID.LBL_FILE_NAME));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LoadDeviceID.BTN_CHOOSE_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LoadDeviceID.BTN_SUBMIT));
			
			//Processor Dropdown option
			CustomisedAssert.assertTrue(foundation.getTextofListElement(LoadDeviceID.DPD_PROCESSOR_OPTION).equals(option));
		}
		catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
		
		/**
		 * @author sakthir 
		 * Date:11-01-2023
		 */
		@Test(description ="223163-verify Uploading proper formatted Excel and verify In ADM location Summary Page Under Product Tab with delete existing as 'Yes'")
		public void verifyADMLocationPageAddedProductCountWithSelectingDeleteExistingOptionsAsYesInSOSLoad() {
			final String CASE_NUM = "223163";
			     
			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLoadProduct = dataBase.getLoadProductData(Queries.LOAD_PRODUCT, CASE_NUM);
							
			String menu =rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM);
			List<String> location =Arrays
					.asList(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION).split(Constants.DELIMITER_TILD));
			List<String> deleteExisting =Arrays
					.asList(rstLoadProduct.get(CNLoadProduct.DELETE_EXISTING_PRODUCT).split(Constants.DELIMITER_TILD));
			String requiredData = strings.getRandomCharacter()+"RoseMilk";
			String requiredString = (requiredData  + "#" + " " + "#" + "Milk" + "#" + "234");
			
		try {
				
			// Login into SOS application with valid User
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.SOS_CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.sosLogin(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
				
			sosHome.selectOrginazation(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONSOSLOAD_ORG, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(SOSHome.LANDING_PAGE_HEADING));
			
			//navigate to Product
			navigationBar.navigateToMenuItem(menu);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LoadProduct.LBL_PRODUCT));
			
			//Select location for product to Add
			foundation.click(loadProduct.clickLocation(location.get(0)));
			
			//Creating product with Delete 'yes' option SOS Load
			loadProduct.uploadExcelAndSelectDeleteOption(requiredString, deleteExisting.get(0));
			textBox.enterText(LoadProduct.TXT_DELETE_POPUP, location.get(4));
			foundation.click(LoadProduct.BTN_DELETE);
			CustomisedAssert.assertTrue(foundation.getText(LoadProduct.SUCCESS_MESSAGE).equals(location.get(5)));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LoadProduct.LBL_PRODUCT_RESULT));
			sosHome.logout();
			
			//log in ADM as super user
			navigationBar.launchBrowserAsSuperAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONSOSLOAD_ORG, FilePath.PROPERTY_CONFIG_FILE));
			
			//select product verify added location from location list product tab
			locationSummary.verifyAddedProductCount(location.get(0), location.get(2));
			
		}
		catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

		/**
		 * @author sakthir 
		 * Date:12-01-2023
		 */
		@Test(description ="223166-verify Uploading improper formatted Excel and verify failure message with delete existing as yes"
				+"223167-verify Uploading improper formatted Excel and verify failure message with delete existing as No"
				+"223165-Verify Delete Popup message and Buttons")
		public void verifyFailureMessageWhileUploadingImproperExcelWithDeleteExistingaAsYesAndNo() {
			final String CASE_NUM = "223166";
			     
			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLoadProduct = dataBase.getLoadProductData(Queries.LOAD_PRODUCT, CASE_NUM);
			rstProductData = dataBase.getProductData(Queries.PRODUCT, CASE_NUM);
							
			String menu =rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM);
			List<String> data =Arrays
					.asList(rstLoadProduct.get(CNLoadProduct.DELETE_EXISTING_PRODUCT).split(Constants.DELIMITER_TILD));
			List<String> header =Arrays
					.asList(rstLoadProduct.get(CNLoadProduct.LOAD_TYPE).split(Constants.DELIMITER_HASH));
			List<String> content =Arrays
					.asList(rstProductData.get(CNProduct.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> message =Arrays
					.asList(rstProductData.get(CNProduct.PRODUCT_NAME).split(Constants.DELIMITER_TILD));
			String requiredString = (" "+ "#" + " " + "#" + "Milk" + "#" + "234");
			
		try {
				
			// Login into SOS application with valid User
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.SOS_CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.sosLogin(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
				
			sosHome.selectOrginazation(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONSOSLOAD_ORG, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(SOSHome.LANDING_PAGE_HEADING));
			
			//navigate to Product
			navigationBar.navigateToMenuItem(menu);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LoadProduct.LBL_PRODUCT));
			
			//Select location for product to Add
			foundation.click(loadProduct.clickLocation(data.get(2)));
			
			//Creating product with Delete 'yes' option SOS Load
			loadProduct.uploadExcelAndSelectDeleteOption(requiredString, data.get(0));
			
			//verify Delete Popup
			loadProduct.verifyDeletePopupFunctionalityAndButton();
			textBox.enterText(LoadProduct.TXT_DELETE_POPUP, data.get(3));
			foundation.click(LoadProduct.BTN_DELETE);
			
			//verify error message
			CustomisedAssert.assertTrue(foundation.getTextofListElement(LoadProduct.SUCCESS_MESSAGE).equals(message));
            CustomisedAssert.assertTrue(foundation.isDisplayed(LoadProduct.LBL_PRODUCT_ERROR));
			
			//verify the error page header
   			CustomisedAssert.assertTrue(foundation.getTextofListElement(LoadProduct.TBL_PRODUCT_ERROR_HEADER).equals(header));
			
			//verify the error page content
            CustomisedAssert.assertTrue(foundation.getTextofListElement(LoadProduct.TBL_PRODUCT_ERROR_CONTENT).equals(content));
			
			//navigate to ProductPricing
			navigationBar.navigateToMenuItem(menu);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LoadProduct.LBL_PRODUCT));
			
			//Select location for product to Add
			foundation.click(loadProduct.clickLocation(data.get(2)));
			
			//Creating product with Delete 'No' option SOS Load
			loadProduct.uploadExcelAndSelectDeleteOption(requiredString, data.get(1));
			
			//verify error message
			 CustomisedAssert.assertTrue(foundation.isDisplayed(LoadProduct.LBL_PRODUCT_ERROR));
			
			//verify the error page header
			CustomisedAssert.assertTrue(foundation.getTextofListElement(LoadProduct.TBL_PRODUCT_ERROR_HEADER).equals(header));
			
			//verify the error page content
			CustomisedAssert.assertTrue(foundation.getTextofListElement(LoadProduct.TBL_PRODUCT_ERROR_CONTENT).equals(content));
			
		}
		catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}	
		
		/**
		 * @author sakthir 
		 * Date:13-01-2023
		 */
		@Test(description ="223682-To Verify the Table Headers of 'Queue' in SOSLoad"
				+"223683-To Verify the Show Count Record Options"
				+"223684-To Verify the Page navigations and Record"
				+"223685-To Verify the search field in Queue Processes page"
				+"223690-Verify the data displayed below each column in Queue")
		public void verify() {
			final String CASE_NUM = "223682";
			     
			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLoadProduct = dataBase.getLoadProductData(Queries.LOAD_PRODUCT, CASE_NUM);
			 
			List<String> menu =Arrays
					.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
			List<String> option =Arrays
					.asList(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION).split(Constants.DELIMITER_TILD));
			List<String> header =Arrays
					.asList(rstLoadProduct.get(CNLoadProduct.LOAD_TYPE).split(Constants.DELIMITER_TILD));
			String requiredString = ("c9fe79b15");
			String currentDate = dateAndTime.getDateAndTime(Constants.REGEX_M_DD_YYYY, Constants.TIME_ZONE_INDIA);
			
		try {
				
			// Login into SOS application with valid User
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.SOS_CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.sosLogin(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
				
			sosHome.selectOrginazation(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(SOSHome.LANDING_PAGE_HEADING));
			
			//navigate to Advana
			navigationBar.navigateToMenuItem(menu.get(1));
			
			//Upload image and File writing excel with Invalid location Id
			loadAdvana.addHomeCommercial(option.get(0),option.get(1),option.get(2),requiredString,option.get(3),currentDate);
			CustomisedAssert.assertTrue(foundation.getText(LoadAdvana.GET_MSG).contains(option.get(4)));
			
			//navigate to Queue
			navigationBar.navigateToMenuItem(menu.get(0));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LoadQueue.LBL_QUEUE));
			
			//verify the table header
			CustomisedAssert.assertTrue(foundation.getTextofListElement(LoadQueue.TBL_HEADER).equals(header));
			
			//verify search functionality
			CustomisedAssert.assertTrue(foundation.isDisplayed(LoadQueue.TXT_SEARCH));
			textBox.enterText(LoadQueue.TXT_SEARCH,option.get(5));
			List <String> data=foundation.getTextofListElement(LoadQueue.TBL_REQUIRED_DATA);
			CustomisedAssert.assertTrue(data.contains(option.get(5)));
			
			//verify data column
			CustomisedAssert.assertTrue(data.contains(option.get(6)) && data.contains(option.get(7)) && data.contains(option.get(5))
					&& foundation.getText(LoadQueue.GET_TIME).contains(currentDate) && data.contains(option.get(8)),"[Fail]: No text found");
			
			//verify the record
			foundation.waitforElementToBeVisible(LoadQueue.LBL_RECORDS_COUNT, 5);
			String s = foundation.getText(LoadQueue.LBL_RECORDS_COUNT);
			String[] str = s.split(" ");
			for (int i = 0; i < str.length; i++) {
			System.out.println(str[i]);
			}
			CustomisedAssert.assertTrue(s.contains("1 - ") && s.contains("of") && s.contains("records"),
					"[Fail]: No total record text found");
			CustomisedAssert.assertTrue(str[2].equals(str[4]));
			
			//verify Page navigation
			CustomisedAssert.assertTrue(foundation.isDisplayed(LoadQueue.LBL_PAGE_NAVIGATION));
			CustomisedAssert.assertTrue(foundation.getBGColor(LoadQueue.PAGE_NAVIGATION).equals(option.get(9)));
			
			//verify show count
			CustomisedAssert.assertTrue(foundation.getText(LoadQueue.LBL_SHOW_RECORD).contains(option.get(10)) && 
					foundation.getText(LoadQueue.LBL_SHOW_RECORD).contains(option.get(11)),"[Fail]: No text found");
		   
		}
		catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}	
	
}