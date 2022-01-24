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
import at.framework.generic.Numbers;
import at.framework.generic.Strings;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.smartshop.database.columns.CNGmaUser;
import at.smartshop.database.columns.CNLoadProduct;
import at.smartshop.database.columns.CNLocationList;
import at.smartshop.database.columns.CNLocationSummary;
import at.smartshop.database.columns.CNNavigationMenu;
import at.smartshop.database.columns.CNProductSummary;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.pages.ConsumerSearch;
import at.smartshop.pages.ConsumerSummary;
import at.smartshop.pages.LocationList;
import at.smartshop.pages.LocationSummary;
import at.smartshop.pages.NavigationBar;
import at.smartshop.sos.pages.LoadGMA;
import at.smartshop.sos.pages.SOSHome;
import at.smartshop.tests.TestInfra;

@Listeners(at.framework.reportsetup.Listeners.class)
public class Sos extends TestInfra {
	private ResultSets dataBase = new ResultSets();
	private SOSHome sosHome = new SOSHome();
	private Foundation foundation = new Foundation();
	private Strings strings = new Strings();
	private Numbers numbers = new Numbers();
	private Excel excel = new Excel();
	private LoadGMA loadGma = new LoadGMA();
	private NavigationBar navigationBar = new NavigationBar();
	private LocationList locationList = new LocationList();
	private Dropdown dropDown = new Dropdown();
	private LocationSummary locationSummary = new LocationSummary();
	private ConsumerSearch consumerSearch = new ConsumerSearch();
	private Map<String, String> rstProductSummaryData;
	private Map<String, String> rstLocationListData;
	private Map<String, String> rstLoadProduct;
	private Map<String, String> rstGmaUser;
	private Map<String, String> rstLocationSummaryData;
	private Map<String, String> rstNavigationMenuData;

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
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
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
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
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
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
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
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
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

		} catch (Throwable exc) {
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
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
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
			String subsidyName = consumerSearch.getSubsidyName();
			CustomisedAssert.assertTrue(subsidyName.isEmpty());
			foundation.click(consumerSearch.objFirstNameCell(consumerSearch.getConsumerFirstName()));
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSummary.LBL_CONSUMER_SUMMARY));
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSummary.TXT_SUBSIDY_GROUP));
			subsidyName = dropDown.getSelectedItem(ConsumerSummary.DPD_SUBSIDY_GROUP_NAME);
			CustomisedAssert.assertEquals(subsidyName, requiredData.get(4));

		} catch (Throwable exc) {
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
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
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
			foundation.threadWait(Constants.ONE_SECOND);
			String subsidyName = consumerSearch.getSubsidyName();
			CustomisedAssert.assertTrue(subsidyName.isEmpty());
			foundation.click(consumerSearch.objFirstNameCell(consumerSearch.getConsumerFirstName()));
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSummary.LBL_CONSUMER_SUMMARY));
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSummary.TXT_SUBSIDY_GROUP));
			subsidyName = dropDown.getSelectedItem(ConsumerSummary.DPD_SUBSIDY_GROUP_NAME);
			CustomisedAssert.assertEquals(subsidyName, requiredData.get(4));

		} catch (Throwable exc) {
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
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
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
			foundation.click(consumerSearch.objFirstNameCell(consumerSearch.getConsumerFirstName()));
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSummary.LBL_CONSUMER_SUMMARY));
			CustomisedAssert.assertFalse(foundation.isDisplayed(ConsumerSummary.TXT_SUBSIDY_GROUP));

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {

			// resetting test data
			locationSummary.subsidyResettingOff(menus.get(1), rstLocationListData.get(CNLocationList.LOCATION_NAME),
					requiredData.get(1));
		}
	}
}
