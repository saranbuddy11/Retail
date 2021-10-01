package at.smartshop.tests;

import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import at.framework.database.mssql.Queries;
import at.framework.database.mssql.ResultSets;
import at.framework.files.Excel;
import at.framework.generic.Numbers;
import at.framework.generic.Strings;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.Radio;
import at.framework.ui.Table;
import at.framework.ui.TextBox;
import at.smartshop.database.columns.CNDeviceList;
import at.smartshop.database.columns.CNGlobalProductChange;
import at.smartshop.database.columns.CNLocation;
import at.smartshop.database.columns.CNLocationList;
import at.smartshop.database.columns.CNLocationSummary;
import at.smartshop.database.columns.CNNationalAccounts;
import at.smartshop.database.columns.CNNavigationMenu;
import at.smartshop.database.columns.CNOrgSummary;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.pages.GlobalProduct;
import at.smartshop.pages.GlobalProductChange;
import at.smartshop.pages.KioskCreate;
import at.smartshop.pages.LocationList;
import at.smartshop.pages.LocationSummary;
import at.smartshop.pages.NavigationBar;
import at.smartshop.pages.OrgSummary;
import at.smartshop.pages.ProductSummary;

@Listeners(at.framework.reportsetup.Listeners.class)
public class Location extends TestInfra {
	private ResultSets dataBase = new ResultSets();
	private NavigationBar navigationBar = new NavigationBar();
	private GlobalProduct globalProduct = new GlobalProduct();
	private TextBox textBox = new TextBox();
	private Foundation foundation = new Foundation();
	private Table table = new Table();
	private LocationList locationList = new LocationList();
	private Dropdown dropDown = new Dropdown();
	private LocationSummary locationSummary = new LocationSummary();
	private GlobalProductChange globalProductChange = new GlobalProductChange();
	private Radio radio = new Radio();
	private Numbers numbers = new Numbers();
	private Strings string = new Strings();
	private Excel excel = new Excel();

	private Map<String, String> rstGlobalProductChangeData;
	private Map<String, String> rstNavigationMenuData;
	private Map<String, String> rstDeviceListData;
	private Map<String, String> rstLocationListData;
	private Map<String, String> rstLocationSummaryData;
	private Map<String, String> rstNationalAccountData;
	private Map<String, String> rstLocationData;
	private Map<String, String> rstOrgSummaryData;

	@Test(description = "114280- This test validates Extend Product")
	public void extendProducts() {
		try {
			final String CASE_NUM = "114280";
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstDeviceListData = dataBase.getDeviceListData(Queries.DEVICE_LIST, CASE_NUM);
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
			rstNationalAccountData = dataBase.getNationalAccountsData(Queries.NATIONAL_ACCOUNTS, CASE_NUM);

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Searching for Product
			textBox.enterText(GlobalProduct.TXT_FILTER, rstDeviceListData.get(CNDeviceList.PRODUCT_NAME));
			foundation.click(globalProduct.getGlobalProduct(rstDeviceListData.get(CNDeviceList.PRODUCT_NAME)));
			foundation.click(ProductSummary.BTN_EXTEND);

			// Extend product to location
			textBox.enterText(ProductSummary.TXT_FILTER, rstLocationListData.get(CNLocationList.LOCATION_NAME));
			table.selectRow(rstNationalAccountData.get(CNNationalAccounts.GRID_NAME),
					rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(ProductSummary.BTN_MODAL_SAVE);

			// Searching for Product and Validating the Location Name
			textBox.enterText(ProductSummary.TXT_LOCATION_SEARCH_FILTER,
					rstLocationListData.get(CNLocationList.LOCATION_NAME));
			Assert.assertTrue((foundation.getText(ProductSummary.TBL_DATA))
					.equals(rstLocationListData.get(CNLocationList.LOCATION_NAME)));

		} catch (Throwable exc) {
			Assert.fail();
		} finally {
			// Resetting test data
			foundation.click(ProductSummary.TBL_DATA);
			foundation.waitforElement(ProductSummary.BTN_REMOVE, Constants.MEDIUM_TIME);
			foundation.click(ProductSummary.BTN_REMOVE);
			foundation.waitforElement(ProductSummary.TXT_LOCATION_SEARCH_FILTER, Constants.MEDIUM_TIME);
		}
	}

	@Test(description = "110650-This test to Verify Products are removed from the Location when location is disabled")
	public void RemoveProductFromLocation() {
		try {
			final String CASE_NUM = "110650";
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
			rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Searching for Product
			foundation.waitforElement(
					locationList.getlocationElement(rstLocationListData.get(CNLocationList.LOCATION_NAME)),
					Constants.SHORT_TIME);
			textBox.enterText(LocationList.TXT_FILTER, rstLocationListData.get(CNLocationList.LOCATION_NAME));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));

			List<String> locationDisabled = Arrays.asList(
					rstLocationSummaryData.get(CNLocationSummary.LOCATION_DISABLED).split(Constants.DELIMITER_TILD));
			dropDown.selectItem(LocationSummary.DPD_DISABLED, locationDisabled.get(0), Constants.TEXT);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.click(LocationSummary.POP_UP_BTN_SAVE);
			List<String> dropDownList = Arrays.asList(
					rstLocationListData.get(CNLocationList.DROPDOWN_LOCATION_LIST).split(Constants.DELIMITER_TILD));
			foundation.waitforElement(LocationList.DPD_LOCATION_LIST, Constants.SHORT_TIME);
			dropDown.selectItem(LocationList.DPD_LOCATION_LIST, dropDownList.get(1), Constants.TEXT);

			// Searching for Product
			textBox.enterText(LocationList.TXT_FILTER, rstLocationListData.get(CNLocationList.LOCATION_NAME));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.TAB_PRODUCTS);
			Assert.assertTrue(foundation.getSizeofListElement(LocationSummary.ROW_PRODUCTS) <= 0);
			dropDown.selectItem(LocationSummary.DPD_DISABLED, locationDisabled.get(1), Constants.TEXT);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.DPD_LOCATION_LIST, Constants.SHORT_TIME);

		} catch (Throwable exc) {
			Assert.fail();
		}

	}

	@Test(description = "130658-This test to verify the Error Message validation for Retrieve Account Methods")
	public void validateErrorMessage() {
		try {
			final String CASE_NUM = "130658";
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
			rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.waitforElement(LocationSummary.BUTTON_LOCATION_INFO, Constants.SHORT_TIME);
			foundation.click(LocationSummary.BUTTON_LOCATION_INFO);
			dropDown.selectItem(LocationSummary.DPD_RETRIEVE_ACCOUNT,
					rstLocationSummaryData.get(CNLocationSummary.ENABLE_RETRIEVE_ACCOUNT), Constants.TEXT);
			Assert.assertTrue(foundation.isDisplayed(LocationSummary.FIELD_RETRIEVE_CHECKBOX));
			foundation.click(LocationSummary.BTN_SAVE);

			Assert.assertEquals(foundation.getText(LocationSummary.TXT_ERR_MSG),
					rstLocationListData.get(CNLocationList.INFO_MESSAGE));

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "114262-verify Add Home commercial in Home commercial Tab and Disable Location")
	public void addHomeCommercial() {
		try {
			final String CASE_NUM = "114262";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
			rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);
			String locationName = rstLocationListData.get(CNLocationList.LOCATION_NAME);
			List<String> locationList_Dpd_Values = Arrays.asList(
					rstLocationListData.get(CNLocationList.DROPDOWN_LOCATION_LIST).split(Constants.DELIMITER_TILD));

			List<String> locationDisabled = Arrays.asList(
					rstLocationSummaryData.get(CNLocationSummary.LOCATION_DISABLED).split(Constants.DELIMITER_TILD));
			String locationDisabled_Yes = locationDisabled.get(0);
			String locationDisabled_No = locationDisabled.get(1);
			// Selecting location
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			foundation.waitforElement(
					locationList.getlocationElement(rstLocationListData.get(CNLocationList.LOCATION_NAME)),
					Constants.SHORT_TIME);
			locationList.selectLocationName(locationName);

			// upload image
			foundation.waitforElement(LocationSummary.BTN_HOME_COMMERCIAL, Constants.SHORT_TIME);
			foundation.click(LocationSummary.BTN_HOME_COMMERCIAL);
			foundation.click(LocationSummary.BTN_ADD_HOME_COMMERCIAL);
			foundation.click(LocationSummary.TXT_UPLOAD_NEW);
			textBox.enterText(LocationSummary.BTN_UPLOAD_INPUT, FilePath.IMAGE_PATH);
			textBox.enterText(LocationSummary.TXT_ADD_NAME,
					rstLocationSummaryData.get(CNLocationSummary.REQUIRED_DATA));
			foundation.click(LocationSummary.BTN_ADD);

			// disabling location
			foundation.waitforElement(LocationSummary.DPD_DISABLED, Constants.SHORT_TIME);
			dropDown.selectItem(LocationSummary.DPD_DISABLED, locationDisabled_Yes, Constants.TEXT);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.click(LocationSummary.POP_UP_BTN_SAVE);

			// Navigating to disabled location list
			dropDown.selectItem(LocationList.DPD_LOCATION_LIST, locationList_Dpd_Values.get(1), Constants.TEXT);

			// validations
			Boolean status = foundation.isDisplayed(locationList.getlocationElement(locationName));
			Assert.assertTrue(status);

			// resetting data
			locationList.selectLocationName(locationName);
			foundation.waitforElement(LocationSummary.DPD_DISABLED, Constants.SHORT_TIME);
			dropDown.selectItem(LocationSummary.DPD_DISABLED, locationDisabled_No, Constants.TEXT);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
		} catch (Throwable exc) {
			exc.printStackTrace();
			Assert.fail();
		}
	}

	@Test(description = "111001-Update Loyalty Multiplier for a product in Operator Product Catalog Change")
	public void updateLoyaltyMultiplier() {
		try {
			final String CASE_NUM = "111001";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstGlobalProductChangeData = dataBase.getGlobalProductChangeData(Queries.GLOBAL_PRODUCT_CHANGE, CASE_NUM);

			// Split database data
			List<String> subMenu = Arrays.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split("~"));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(subMenu.get(0));

			// Searching for Product
			radio.set(GlobalProductChange.RDO_OPERATOR_PRODUCT_CHANGE);
			String product = rstGlobalProductChangeData.get(CNGlobalProductChange.PRODUCT_NAME);
			textBox.enterText(GlobalProductChange.TXT_PRODUCT_NAME, product);
			foundation.click(GlobalProductChange.BTN_PRODUCT_APPLY);
			foundation.threadWait(Constants.TWO_SECOND);
			foundation.click(globalProductChange.objProductName(product));
			foundation.click(GlobalProductChange.BTN_NEXT);

			// Update loyalty filter
			dropDown.selectItem(GlobalProductChange.DPD_LOYALITY_MULTIPLIER, "5", Constants.VALUE);
			foundation.click(GlobalProductChange.BTN_SUBMIT);
			foundation.click(GlobalProductChange.BTN_OK);
			foundation.isDisplayed(GlobalProductChange.MSG_SUCCESS);

			// Select Menu and Global product
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(subMenu.get(1));

			// Search and select product
			textBox.enterText(GlobalProduct.TXT_FILTER, product);
			foundation.click(globalProduct.getGlobalProduct(product));

			// verify value in loyalty dropdown
			assertEquals(dropDown.getSelectedItem(ProductSummary.DPD_LOYALTY_MULTIPLIER), "5");
		} catch (Throwable exc) {
			exc.printStackTrace();
			Assert.fail();
		}
	}

	@Test(description = "114899-Update Tax for Product and verify in Location Summary -> Products Tab")
	public void updateTaxForProduct() {
		try {
			final String CASE_NUM = "114899";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstDeviceListData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);

			String product = rstDeviceListData.get(CNLocationSummary.PRODUCT_NAME);
			String location = rstLocationListData.get(CNLocationList.LOCATION_NAME);

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Searching for Product
			textBox.enterText(GlobalProduct.TXT_FILTER, product);
			foundation.click(globalProduct.getGlobalProduct(product));

			// select tax category
			dropDown.selectItemByIndex(ProductSummary.DPD_TAX_CATEGORY, Constants.TWO_SECOND);
			String selectedTaxCat = dropDown.getSelectedItem(ProductSummary.DPD_TAX_CATEGORY);
			foundation.click(ProductSummary.BTN_SAVE);

			// naviagate back to product summary page
			textBox.enterText(GlobalProduct.TXT_FILTER, product);
			foundation.click(globalProduct.getGlobalProduct(product));

			// Navigate to product's location
			textBox.enterText(ProductSummary.TXT_LOCATION_SEARCH_FILTER, location);
			foundation.click(ProductSummary.TBL_DATA);
			foundation.waitforElement(ProductSummary.BTN_REMOVE, Constants.MEDIUM_TIME);
			foundation.click(ProductSummary.BTN_EDIT_LOCATION);

			// navigate to product tab
			foundation.click(LocationSummary.TAB_PRODUCTS);

			// enable show tax cat column
			locationSummary.manageColumn(rstLocationListData.get(CNLocationList.SHOW_RECORDS));

			// ensure selected tax category from product summary page displays for the
			// product here
			textBox.enterText(LocationSummary.TXT_SEARCH, product);
			assertEquals(foundation.getText(LocationSummary.LBL_TAX_CATEGORY), selectedTaxCat);
		} catch (Throwable exc) {
			exc.printStackTrace();
			Assert.fail();
		}
	}

	@Test(description = "146023-QAA-103-Verify caution icon and device dashboard page are displayed for offline device.")
	public void verifyCautionIcon() {
		try {
			final String CASE_NUM = "146023";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstDeviceListData = dataBase.getDeviceListData(Queries.DEVICE_LIST, CASE_NUM);

			String device = rstDeviceListData.get(CNDeviceList.DEVICE);
			String location = rstDeviceListData.get(CNDeviceList.LOCATION);
			String expectedData = rstDeviceListData.get(CNDeviceList.PRODUCT_NAME);

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			locationList.selectLocationName(location);

			foundation.waitforElement(LocationSummary.BTN_DEVICE, Constants.SHORT_TIME);
			foundation.click(LocationSummary.BTN_DEVICE);
			textBox.enterText(LocationSummary.TXT_DEVICE_SEARCH, device);
			Assert.assertTrue(foundation.isDisplayed(LocationSummary.LBL_CAUTION_ICON));
			foundation.objectFocus(LocationSummary.LBL_CAUTION_ICON);
			Assert.assertTrue(foundation.isDisplayed(LocationSummary.LBL_HOVER_MESSAGE));
			foundation.click(LocationSummary.LBL_CAUTION_ICON);
			foundation.click(locationSummary.objDevice(device));
			Assert.assertEquals(foundation.getText(LocationSummary.TXT_DEVICE_STATUS), expectedData);

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "146024-QAA-103-verify tick mark icon and device dashboard page are displayed for Online device.")
	public void verifyTickMarkIcon() {
		try {
			final String CASE_NUM = "146024";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstDeviceListData = dataBase.getDeviceListData(Queries.DEVICE_LIST, CASE_NUM);

			String device = rstDeviceListData.get(CNDeviceList.DEVICE);
			String location = rstDeviceListData.get(CNDeviceList.LOCATION);
			String expectedData = rstDeviceListData.get(CNDeviceList.PRODUCT_NAME);

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			locationList.selectLocationName(location);
			// Navigating to device tab
			foundation.waitforElement(LocationSummary.BTN_DEVICE, Constants.SHORT_TIME);
			foundation.click(LocationSummary.BTN_DEVICE);
			textBox.enterText(LocationSummary.TXT_DEVICE_SEARCH, device);
			Assert.assertTrue(foundation.isDisplayed(LocationSummary.LBL_TICKMARK_ICON));
			foundation.click(LocationSummary.LBL_TICKMARK_ICON);
			foundation.waitforElement(locationSummary.objDevice(device), Constants.SHORT_TIME);
			foundation.click(locationSummary.objDevice(device));
			Assert.assertEquals(foundation.getText(LocationSummary.TXT_DEVICE_STATUS), expectedData);

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "146025-QAA-105-verify device summary page is displayed when user clicks on any device name under devices tab in location summary page.")
	public void verifyDevicePage() {
		try {
			final String CASE_NUM = "146025";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstDeviceListData = dataBase.getDeviceListData(Queries.DEVICE_LIST, CASE_NUM);

			String device = rstDeviceListData.get(CNDeviceList.DEVICE);
			String location = rstDeviceListData.get(CNDeviceList.LOCATION);
			String expectedData = rstDeviceListData.get(CNDeviceList.PRODUCT_NAME);

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			locationList.selectLocationName(location);
			// Navigating to device tab
			foundation.waitforElement(LocationSummary.BTN_DEVICE, Constants.SHORT_TIME);
			foundation.click(LocationSummary.BTN_DEVICE);
			textBox.enterText(LocationSummary.TXT_DEVICE_SEARCH, device);
			Assert.assertTrue(foundation.isDisplayed(LocationSummary.LBL_TICKMARK_ICON));
			locationSummary.selectDeviceName(device);
			foundation.waitforElement(LocationSummary.TXT_DEVICE_SUMMARY, Constants.SHORT_TIME);
			String actualData = foundation.getText(LocationSummary.TXT_DEVICE_SUMMARY);
			Assert.assertEquals(actualData, expectedData);
			actualData = foundation.getText(LocationSummary.TXT_DEVICE_NAME);
			Assert.assertEquals(actualData, device);

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "146026-QAA-103-verify device table is displayed in location Summary Page under device tab.")
	public void verifyDeviceTableUI() {
		try {
			final String CASE_NUM = "146026";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstDeviceListData = dataBase.getDeviceListData(Queries.DEVICE_LIST, CASE_NUM);

			String device = rstDeviceListData.get(CNDeviceList.DEVICE);
			String location = rstDeviceListData.get(CNDeviceList.LOCATION);

			List<String> dbData = Arrays
					.asList(rstDeviceListData.get(CNDeviceList.PRODUCT_NAME).split(Constants.DELIMITER_TILD));
			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			locationList.selectLocationName(location);
			// Navigating to device tab
			foundation.waitforElement(LocationSummary.BTN_DEVICE, Constants.SHORT_TIME);
			foundation.click(LocationSummary.BTN_DEVICE);
			textBox.enterText(LocationSummary.TXT_DEVICE_SEARCH, device);
			Assert.assertTrue(foundation.isDisplayed(LocationSummary.LBL_TICKMARK_ICON));
			Map<String, String> uiData = table.getTblSingleRowRecordUI(LocationSummary.TBL_DEVICE_GRID,
					LocationSummary.TBL_DEVICE_ROW);
			// Table Validations
			Assert.assertEquals(uiData.get(dbData.get(0)), device);
			Assert.assertEquals(uiData.get(dbData.get(1)), dbData.get(2));

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "146083-QAA-107-verify UI and sorting functionality of deploy device popup fields")
	public void verifyDeviceUI() {
		try {
			final String CASE_NUM = "146083";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstDeviceListData = dataBase.getDeviceListData(Queries.DEVICE_LIST, CASE_NUM);

			String device = rstDeviceListData.get(CNDeviceList.DEVICE);
			String location = rstDeviceListData.get(CNDeviceList.LOCATION);

			List<String> expectedData = Arrays
					.asList(rstDeviceListData.get(CNDeviceList.PRODUCT_NAME).split(Constants.DELIMITER_TILD));
			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			locationList.selectLocationName(location);
			// Navigating to device tab
			foundation.waitforElement(LocationSummary.BTN_DEVICE, Constants.SHORT_TIME);
			foundation.click(LocationSummary.BTN_DEPLOY_DEVICE);
			foundation.waitforElement(LocationSummary.TXT_DEVICE_POPUP_SEARCH, Constants.SHORT_TIME);

			Assert.assertTrue(locationSummary.verifySortAscending(LocationSummary.LBL_COLUMN_DATA));
			foundation.click(LocationSummary.LBL_ROW_HEADER);
			Assert.assertTrue(locationSummary.verifySortDescending(LocationSummary.LBL_COLUMN_DATA));
			textBox.enterText(LocationSummary.TXT_DEVICE_POPUP_SEARCH, device);
			Map<String, String> uiData = table.getTblSingleRowRecordUI(LocationSummary.TBL_DEVICE_POPUP_GRID,
					LocationSummary.TBL_DEVICE_POPUP_ROW);
			Map<String, String> dbData = new HashMap<>();
			dbData.put(expectedData.get(0), device);
			dbData.put(expectedData.get(1), expectedData.get(2));
			Assert.assertEquals(uiData, dbData);

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "146084-QAA-107-Verify Add,close and search functionality in Depoly device")
	public void verifyAddClose() {
		try {
			final String CASE_NUM = "146084";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstDeviceListData = dataBase.getDeviceListData(Queries.DEVICE_LIST, CASE_NUM);

			final String device = rstDeviceListData.get(CNDeviceList.DEVICE) + string.getRandomCharacter();
			String location = rstDeviceListData.get(CNDeviceList.LOCATION);

			List<String> dbData = Arrays
					.asList(rstDeviceListData.get(CNDeviceList.PRODUCT_NAME).split(Constants.DELIMITER_TILD));
			List<String> menuItem = Arrays
					.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItem.get(0));
			foundation.waitforElement(KioskCreate.BTN_CREATE, Constants.SHORT_TIME);
			foundation.click(KioskCreate.BTN_CREATE);
			textBox.enterText(KioskCreate.TXT_NAME, device);
			dropDown.selectItem(KioskCreate.DPD_ORG, dbData.get(0), Constants.TEXT);
			dropDown.selectItem(KioskCreate.DPD_PROCESSOR, dbData.get(1), Constants.TEXT);
			textBox.enterText(KioskCreate.TXT_TERMINAL_ID, String.valueOf(numbers.generateRandomNumber(0, 99999)));
			foundation.waitforElement(KioskCreate.BTN_SAVE, Constants.SHORT_TIME);
			foundation.click(KioskCreate.BTN_SAVE);
			foundation.waitforElement(KioskCreate.TXT_DEVICE_LIST, Constants.SHORT_TIME);
			navigationBar.navigateToMenuItem(menuItem.get(1));
			locationList.selectLocationName(location);
			// Navigating to device tab
			foundation.waitforElement(LocationSummary.BTN_DEVICE, Constants.SHORT_TIME);
			foundation.click(LocationSummary.BTN_DEPLOY_DEVICE);
			// search functionality
			foundation.waitforElement(LocationSummary.TXT_DEVICE_POPUP_SEARCH, Constants.SHORT_TIME);
			textBox.enterText(LocationSummary.TXT_DEVICE_POPUP_SEARCH, device);
			foundation.waitforElement(LocationSummary.LBL_ROW_HEADER, Constants.SHORT_TIME);
			foundation.waitforClikableElement(LocationSummary.LBL_COLUMN_DATA, Constants.SHORT_TIME);
			String actualData = foundation.getText(LocationSummary.LBL_COLUMN_DATA);
			Assert.assertEquals(actualData, device);
			actualData = foundation.getText(LocationSummary.LBL_TABLEINFO);
			Assert.assertTrue(actualData.contains(dbData.get(4)));
			// Add Functionality
			foundation.click(LocationSummary.LBL_COLUMN_DATA);
			foundation.click(LocationSummary.BTN_DEVICE_ADD);
			foundation.refreshPage();
			foundation.refreshPage();
			foundation.waitforElement(LocationSummary.TXT_DEVICE_SEARCH, Constants.SHORT_TIME);
			textBox.enterText(LocationSummary.TXT_DEVICE_SEARCH, device);
			foundation.waitforElement(locationSummary.objUploadStatus(device), Constants.SHORT_TIME);
			Assert.assertTrue(foundation.isDisplayed(locationSummary.objUploadStatus(device)));
			foundation.click(LocationSummary.BTN_DEPLOY_DEVICE);
			textBox.enterText(LocationSummary.TXT_DEVICE_POPUP_SEARCH, device);
			actualData = foundation.getText(LocationSummary.LBL_TABLE_DATA);
			Assert.assertEquals(actualData, dbData.get(2));
			// Close
			foundation.refreshPage();
			foundation.click(LocationSummary.BTN_DEPLOY_DEVICE);
			foundation.waitforElement(LocationSummary.TXT_DEVICE_POPUP_SEARCH, Constants.SHORT_TIME);
			textBox.enterText(LocationSummary.TXT_DEVICE_POPUP_SEARCH, dbData.get(3));
			foundation.click(LocationSummary.LBL_COLUMN_DATA);
			foundation.click(LocationSummary.BTN_DEVICE_CLOSE);
			foundation.waitforElement(locationSummary.objUploadStatus(device), Constants.SHORT_TIME);
			Assert.assertFalse(foundation.isDisplayed(locationSummary.objUploadStatus(dbData.get(3))));

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "146085-QAA-107-verify created device is displayed in devices tab in location summary page")
	public void verifyDeviceCreation() {
		try {
			final String CASE_NUM = "146085";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstDeviceListData = dataBase.getDeviceListData(Queries.DEVICE_LIST, CASE_NUM);

			final String device = rstDeviceListData.get(CNDeviceList.DEVICE) + string.getRandomCharacter();
			String location = rstDeviceListData.get(CNDeviceList.LOCATION);

			List<String> dbData = Arrays
					.asList(rstDeviceListData.get(CNDeviceList.PRODUCT_NAME).split(Constants.DELIMITER_TILD));
			List<String> menuItem = Arrays
					.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItem.get(0));
			foundation.waitforElement(KioskCreate.BTN_CREATE, Constants.SHORT_TIME);
			foundation.click(KioskCreate.BTN_CREATE);
			textBox.enterText(KioskCreate.TXT_NAME, device);
			dropDown.selectItem(KioskCreate.DPD_ORG, dbData.get(0), Constants.TEXT);
			dropDown.selectItem(KioskCreate.DPD_PROCESSOR, dbData.get(1), Constants.TEXT);
			textBox.enterText(KioskCreate.TXT_TERMINAL_ID, String.valueOf(numbers.generateRandomNumber(0, 99999)));
			foundation.waitforClikableElement(KioskCreate.BTN_SAVE, Constants.SHORT_TIME);
			foundation.click(KioskCreate.BTN_SAVE);
			foundation.waitforElement(KioskCreate.TXT_DEVICE_LIST, Constants.SHORT_TIME);
			navigationBar.navigateToMenuItem(menuItem.get(1));
			locationList.selectLocationName(location);
			// Navigating to device tab
			foundation.waitforElement(LocationSummary.BTN_DEVICE, Constants.SHORT_TIME);
			foundation.click(LocationSummary.BTN_DEPLOY_DEVICE);
			// search functionality
			foundation.waitforElement(LocationSummary.TXT_DEVICE_POPUP_SEARCH, Constants.SHORT_TIME);
			textBox.enterText(LocationSummary.TXT_DEVICE_POPUP_SEARCH, device);
			foundation.waitforElement(LocationSummary.LBL_ROW_HEADER, Constants.SHORT_TIME);
			foundation.waitforClikableElement(LocationSummary.LBL_COLUMN_DATA, Constants.SHORT_TIME);
			String actualData = foundation.getText(LocationSummary.LBL_COLUMN_DATA);
			Assert.assertEquals(actualData, device);
			actualData = foundation.getText(LocationSummary.LBL_TABLEINFO);
			Assert.assertTrue(actualData.contains(dbData.get(3)));
			// Add Functionality
			foundation.click(LocationSummary.LBL_COLUMN_DATA);
			foundation.click(LocationSummary.BTN_DEVICE_ADD);
			foundation.refreshPage();
			foundation.refreshPage();
			foundation.waitforElement(LocationSummary.TXT_DEVICE_SEARCH, Constants.SHORT_TIME);
			textBox.enterText(LocationSummary.TXT_DEVICE_SEARCH, device);
			foundation.waitforElement(locationSummary.objUploadStatus(device), Constants.SHORT_TIME);
			Assert.assertTrue(foundation.isDisplayed(locationSummary.objUploadStatus(device)));
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "146126-QAA-107-verify when device is mapped to location, mapped device should not be available in another location.")
	public void verifyAlreadyMappedDevice() {
		try {
			final String CASE_NUM = "146126";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstDeviceListData = dataBase.getDeviceListData(Queries.DEVICE_LIST, CASE_NUM);

			String device = rstDeviceListData.get(CNDeviceList.DEVICE);
			String location = rstDeviceListData.get(CNDeviceList.LOCATION);

			String expectedData = rstDeviceListData.get(CNDeviceList.PRODUCT_NAME);
			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			locationList.selectLocationName(location);
			// Navigating to device tab
			foundation.waitforElement(LocationSummary.BTN_DEVICE, Constants.SHORT_TIME);
			foundation.click(LocationSummary.BTN_DEPLOY_DEVICE);
			foundation.waitforElement(LocationSummary.TXT_DEVICE_POPUP_SEARCH, Constants.SHORT_TIME);
			textBox.enterText(LocationSummary.TXT_DEVICE_POPUP_SEARCH, device);
			String actualData = foundation.getText(LocationSummary.LBL_TABLE_DATA);
			Assert.assertEquals(actualData, expectedData);

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "143463-Verify Verify when tax method is set to Item level in OrgSummary Page , Tax mapping should not display in Location Summary Page.")
	public void verifyItemLevelTax() {
		final String CASE_NUM = "143463";
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstOrgSummaryData = dataBase.getOrgSummaryData(Queries.ORG_SUMMARY, CASE_NUM);
		rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
		// Split database data
		List<String> subMenu = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> requiredData = Arrays
				.asList(rstOrgSummaryData.get(CNOrgSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));

		try {

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(subMenu.get(0));

			String locationName = rstLocationListData.get(CNLocationList.LOCATION_NAME);

			dropDown.selectItem(OrgSummary.DPD_TAX_METHOD, requiredData.get(0), Constants.TEXT);
			foundation.click(OrgSummary.BTN_SAVE);
			foundation.waitforElement(OrgSummary.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			// Location
			navigationBar.navigateToMenuItem(subMenu.get(1));
			locationList.selectLocationName(locationName);
			Assert.assertFalse(foundation.isDisplayed(LocationSummary.LBL_TAX_MAPPING));

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());

		} finally {

			// resetting test data
			navigationBar.navigateToMenuItem(subMenu.get(0));
			dropDown.selectItem(OrgSummary.DPD_TAX_METHOD, requiredData.get(1), Constants.TEXT);
			foundation.click(OrgSummary.BTN_SAVE);
			foundation.waitforElement(OrgSummary.TXT_SPINNER_MSG, Constants.SHORT_TIME);
		}
	}

	@Test(description = "143468-Verify already assigned category should not display in tax category dropdown")
	public void verifyTaxCategoryDpd() {
		try {
			final String CASE_NUM = "143468";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);

			String locationName = rstLocationData.get(CNLocation.LOCATION_NAME);
			String tabName = rstLocationData.get(CNLocation.TAB_NAME);
			List<String> requiredData = Arrays
					.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));

			locationList.selectLocationName(locationName);
			locationSummary.selectTab(tabName);
			foundation.click(LocationSummary.LBL_TAX_MAPPING);
			dropDown.selectItem(LocationSummary.DPD_TAX_CAT, requiredData.get(0), Constants.TEXT);
			dropDown.selectItem(LocationSummary.DPD_TAX_RATE, requiredData.get(1), Constants.TEXT);
			foundation.click(LocationSummary.LBL_TAX_CAT_SAVE);
			locationSummary.selectTab(tabName);
			foundation.click(LocationSummary.LBL_TAX_MAPPING);
			Assert.assertFalse(dropDown.verifyItemPresent(LocationSummary.DPD_TAX_CAT, requiredData.get(0)));
			foundation.click(LocationSummary.LBL_TAX_CAT_CANCEL);
			table.selectRow(requiredData.get(0));
			foundation.waitforElement(LocationSummary.LBL_TAX_CAT_REMOVE, Constants.SHORT_TIME);
			foundation.click(LocationSummary.LBL_TAX_CAT_REMOVE);

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	@Test(description = "146222-QAA-110-Verify UI and sorting functionality of products table in location Summary Page under products tab.")
	public void verifyProductsUI() {
		try {
			final String CASE_NUM = "146222";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);

			String product = rstLocationData.get(CNLocation.PRODUCT_NAME);
			String location = rstLocationData.get(CNLocation.LOCATION_NAME);
			List<String> requiredData = Arrays
					.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));

			List<String> expectedData = Arrays
					.asList(rstLocationData.get(CNLocation.ACTUAL_DATA).split(Constants.DELIMITER_TILD));
			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			locationList.selectLocationName(location);
			// Navigating to products tab
			foundation.waitforElement(LocationSummary.TAB_PRODUCTS, Constants.SHORT_TIME);
			foundation.click(LocationSummary.TAB_PRODUCTS);
			foundation.threadWait(Constants.ONE_SECOND);
			Assert.assertTrue(locationSummary.verifySortAscending(LocationSummary.TBL_PRODUCTS_GRID));
			foundation.click(LocationSummary.TBL_NAME_HEADER);
			Assert.assertTrue(locationSummary.verifySortDescending(LocationSummary.TBL_PRODUCTS_GRID));
			textBox.enterText(LocationSummary.TXT_SEARCH, product);
			foundation.waitforElement(locationSummary.objProductPrice(product), Constants.SHORT_TIME);
			Map<String, String> uiTableData = table.getTblSingleRowRecordUI(LocationSummary.TBL_PRODUCTS,
					LocationSummary.TBL_PRODUCTS_GRID);
			Map<String, String> dbData = new HashMap<>();
			for (int i = 0; i < requiredData.size(); i++) {
				dbData.put(requiredData.get(i), expectedData.get(i));
			}
			Assert.assertEquals(uiTableData, dbData);

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "146225-QAA-110-verify show records dropdown functionality products table in location Summary Page under products tab.")
	public void verifyShowRecordsDPD() {
		try {
			final String CASE_NUM = "146225";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase

			rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);

			String location = rstLocationData.get(CNLocation.LOCATION_NAME);
			String requiredData = rstLocationData.get(CNLocation.REQUIRED_DATA);

//			List<String> expectedData = Arrays
//					.asList(rstLocationData.get(CNLocation.ACTUAL_DATA).split(Constants.DELIMITER_TILD));
			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			locationList.selectLocationName(location);
			// Navigating to products tab
			foundation.waitforElement(LocationSummary.TAB_PRODUCTS, Constants.SHORT_TIME);
			foundation.click(LocationSummary.TAB_PRODUCTS);
			foundation.click(LocationSummary.DPD_SHOW_RECORD);
			foundation.click(LocationSummary.TXT_10_RECORD);
			foundation.threadWait(Constants.ONE_SECOND);
			foundation.click(LocationSummary.DPD_SHOW_RECORD);
			foundation.click(LocationSummary.TXT_5_RECORD);
			// textBox.enterText(LocationSummary.DPD_SHOW_RECORD, Keys.ARROW_UP);
			foundation.threadWait(Constants.TWO_SECOND);

			String[] uiData = (foundation.getText(LocationSummary.TXT_PRODUCTS_COUNT)).split(" ");
			System.out.println(uiData);
			Assert.assertEquals(uiData[2], requiredData);

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "146228-QAA-110-verify Export Button functionality for products table in location Summary Page under products tab.")
	public void verifyExportButton() {
		try {
			final String CASE_NUM = "146228";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase

			rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);

			String location = rstLocationData.get(CNLocation.LOCATION_NAME);
			String requiredData = rstLocationData.get(CNLocation.REQUIRED_DATA);
			String product = rstLocationData.get(CNLocation.PRODUCT_NAME);

			List<String> expectedData = Arrays
					.asList(rstLocationData.get(CNLocation.ACTUAL_DATA).split(Constants.DELIMITER_TILD));
			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			locationList.selectLocationName(location);
			// Navigating to products tab
			foundation.waitforElement(LocationSummary.TAB_PRODUCTS, Constants.SHORT_TIME);
			foundation.click(LocationSummary.TAB_PRODUCTS);
			foundation.threadWait(Constants.ONE_SECOND);
			textBox.enterText(LocationSummary.TXT_SEARCH, product);
			foundation.waitforElement(locationSummary.objProductPrice(product), Constants.SHORT_TIME);

			foundation.click(LocationSummary.BTN_EXPORT);

			Assert.assertTrue(excel.isFileDownloaded(FilePath.EXCEL_LOCAL_PROD));

			foundation.copyFile(FilePath.EXCEL_LOCAL_PROD, FilePath.EXCEL_PROD);
			int excelCount = excel.getExcelRowCount(FilePath.EXCEL_PROD);
			// record count validation
			Assert.assertEquals(String.valueOf(excelCount), requiredData);

			Map<String, String> uidata = table.getTblSingleRowRecordUI(LocationSummary.TBL_PRODUCTS,
					LocationSummary.TBL_PRODUCTS_GRID);
			uidata.remove(expectedData.get(0));
			uidata.remove(expectedData.get(1));
			uidata.remove(expectedData.get(2));
			uidata.remove(expectedData.get(3));

			List<String> uiList = new ArrayList<String>(uidata.values());
			// excel data validation
			Assert.assertTrue(excel.verifyExcelData(uiList, FilePath.EXCEL_PROD, 1));

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// delete files
			foundation.deleteFile(FilePath.EXCEL_LOCAL_PROD);
			foundation.deleteFile(FilePath.EXCEL_PROD);
		}
	}

	@Test(description = "146228-QAA-110-verify Export Button functionality for products table in location Summary Page under products tab.")
	public void verifyManageButton() {
		try {
			final String CASE_NUM = "146228";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase

			rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);

			String location = rstLocationData.get(CNLocation.LOCATION_NAME);

			List<String> expectedData = Arrays
					.asList(rstLocationData.get(CNLocation.ACTUAL_DATA).split(Constants.DELIMITER_TILD));
			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			locationList.selectLocationName(location);
			// Navigating to products tab
			foundation.waitforElement(LocationSummary.TAB_PRODUCTS, Constants.SHORT_TIME);
			foundation.click(LocationSummary.TAB_PRODUCTS);
			foundation.threadWait(Constants.ONE_SECOND);
			// hide functionality
			foundation.click(LocationSummary.BTN_MANAGE_COLUMNS);
			locationSummary.showHideManageColumn(Constants.HIDE, expectedData.get(0));
			foundation.threadWait(Constants.ONE_SECOND);
			foundation.click(LocationSummary.BTN_APPLY);
			foundation.waitforElementToDisappear(LocationSummary.BTN_APPLY, Constants.SHORT_TIME);

			Assert.assertFalse(foundation.isDisplayed(locationSummary.objColumnHeaders(expectedData.get(0))));
			// show functionality
			foundation.click(LocationSummary.BTN_MANAGE_COLUMNS);
			locationSummary.showHideManageColumn(Constants.SHOW, expectedData.get(0));
			foundation.threadWait(Constants.ONE_SECOND);
			foundation.click(LocationSummary.BTN_APPLY);
			foundation.waitforElementToDisappear(LocationSummary.BTN_APPLY, Constants.SHORT_TIME);
			Assert.assertTrue(foundation.isDisplayed(locationSummary.objColumnHeaders(expectedData.get(0))));

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
}
