package at.smartshop.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.awt.AWTException;
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
import at.framework.generic.CustomisedAssert;
import at.framework.generic.Numbers;
import at.framework.generic.Strings;
import at.framework.ui.CheckBox;
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
import at.smartshop.pages.CreatePromotions;
import at.smartshop.pages.DeviceDashboard;
import at.smartshop.pages.DeviceList;
import at.smartshop.pages.DeviceSummary;
import at.smartshop.pages.GlobalProduct;
import at.smartshop.pages.GlobalProductChange;
import at.smartshop.pages.KioskCreate;
import at.smartshop.pages.LocationList;
import at.smartshop.pages.LocationSummary;
import at.smartshop.pages.NavigationBar;
import at.smartshop.pages.OrgSummary;
import at.smartshop.pages.ProductSummary;
import at.smartshop.pages.UserList;
import at.smartshop.pages.UserRoles;
import at.smartshop.v5.pages.LandingPage;
import at.smartshop.v5.pages.Order;
import at.smartshop.v5.pages.ProductSearch;

@Listeners(at.framework.reportsetup.Listeners.class)
public class Location extends TestInfra {
	private ResultSets dataBase = new ResultSets();
	private NavigationBar navigationBar = new NavigationBar();
	private GlobalProduct globalProduct = new GlobalProduct();
	private TextBox textBox = new TextBox();
	private Foundation foundation = new Foundation();
	private Table table = new Table();
	private UserRoles userRoles = new UserRoles();
	private UserList userList = new UserList();
	private Strings strings = new Strings();
	private LocationList locationList = new LocationList();
	private Dropdown dropDown = new Dropdown();
	private LocationSummary locationSummary = new LocationSummary();
	private GlobalProductChange globalProductChange = new GlobalProductChange();
	private Radio radio = new Radio();
	private Numbers numbers = new Numbers();
	private Strings string = new Strings();
	private Excel excel = new Excel();
	private DeviceList deviceList = new DeviceList();
	private DeviceSummary deviceSummary = new DeviceSummary();
	private CheckBox checkBox = new CheckBox();

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
			rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);
			rstNationalAccountData = dataBase.getNationalAccountsData(Queries.NATIONAL_ACCOUNTS, CASE_NUM);
			String locationName = rstLocationListData.get(CNLocationList.LOCATION_NAME);
			List<String> locationList_Dpd_Values = Arrays.asList(
					rstLocationListData.get(CNLocationList.DROPDOWN_LOCATION_LIST).split(Constants.DELIMITER_TILD));

			List<String> locationDisabled = Arrays.asList(
					rstLocationSummaryData.get(CNLocationSummary.LOCATION_DISABLED).split(Constants.DELIMITER_TILD));
			String locationDisabled_No = locationDisabled.get(1);

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			// Verifying the Location is Active or not
			if (foundation.isDisplayed(
					locationList.getlocationElement(rstLocationListData.get(CNLocationList.LOCATION_NAME))) == false) {

				dropDown.selectItem(LocationList.DPD_LOCATION_LIST, locationList_Dpd_Values.get(1), Constants.TEXT);
				locationList.selectLocationName(locationName);
				foundation.waitforElement(LocationSummary.DPD_DISABLED, Constants.SHORT_TIME);
				dropDown.selectItem(LocationSummary.DPD_DISABLED, locationDisabled_No, Constants.TEXT);
				foundation.click(LocationSummary.BTN_SAVE);
				foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			}

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
			CustomisedAssert.assertTrue((foundation.getText(ProductSummary.TBL_DATA))
					.equals(rstLocationListData.get(CNLocationList.LOCATION_NAME)));

		} catch (Exception exc) {
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
			List<String> locationList_Dpd_Values = Arrays.asList(
					rstLocationListData.get(CNLocationList.DROPDOWN_LOCATION_LIST).split(Constants.DELIMITER_TILD));
			List<String> locationDisabled = Arrays.asList(
					rstLocationSummaryData.get(CNLocationSummary.LOCATION_DISABLED).split(Constants.DELIMITER_TILD));

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			// Verifying the Location is Active or not
			if (foundation.isDisplayed(
					locationList.getlocationElement(rstLocationListData.get(CNLocationList.LOCATION_NAME))) == false) {

				dropDown.selectItem(LocationList.DPD_LOCATION_LIST, locationList_Dpd_Values.get(1), Constants.TEXT);
				locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
				foundation.waitforElement(LocationSummary.DPD_DISABLED, Constants.SHORT_TIME);
				dropDown.selectItem(LocationSummary.DPD_DISABLED, locationDisabled.get(1), Constants.TEXT);
				foundation.click(LocationSummary.BTN_SAVE);
				foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			}

			// Searching for Product
			foundation.waitforElement(
					locationList.getlocationElement(rstLocationListData.get(CNLocationList.LOCATION_NAME)),
					Constants.SHORT_TIME);
			textBox.enterText(LocationList.TXT_FILTER, rstLocationListData.get(CNLocationList.LOCATION_NAME));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));

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
			CustomisedAssert.assertTrue(foundation.getSizeofListElement(LocationSummary.ROW_PRODUCTS) <= 0);
			dropDown.selectItem(LocationSummary.DPD_DISABLED, locationDisabled.get(1), Constants.TEXT);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.DPD_LOCATION_LIST, Constants.SHORT_TIME);

		} catch (Exception exc) {
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
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.FIELD_RETRIEVE_CHECKBOX));
			foundation.click(LocationSummary.BTN_SAVE);

			CustomisedAssert.assertEquals(foundation.getText(LocationSummary.TXT_ERR_MSG),
					rstLocationListData.get(CNLocationList.INFO_MESSAGE));

		} catch (Exception exc) {
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

			// Verifying the Location is Active or not
			if (foundation.isDisplayed(
					locationList.getlocationElement(rstLocationListData.get(CNLocationList.LOCATION_NAME))) == false) {

				dropDown.selectItem(LocationList.DPD_LOCATION_LIST, locationList_Dpd_Values.get(1), Constants.TEXT);
				locationList.selectLocationName(locationName);
				foundation.waitforElement(LocationSummary.DPD_DISABLED, Constants.SHORT_TIME);
				dropDown.selectItem(LocationSummary.DPD_DISABLED, locationDisabled_No, Constants.TEXT);
				foundation.click(LocationSummary.BTN_SAVE);
				foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			}

			foundation.waitforElement(
					locationList.getlocationElement(rstLocationListData.get(CNLocationList.LOCATION_NAME)),
					Constants.SHORT_TIME);
			locationList.selectLocationName(locationName);

			// upload image
//			foundation.waitforElement(LocationSummary.BTN_HOME_COMMERCIAL, Constants.SHORT_TIME);
//			foundation.click(LocationSummary.BTN_HOME_COMMERCIAL);
//			foundation.click(LocationSummary.BTN_ADD_HOME_COMMERCIAL);
//			foundation.click(LocationSummary.TXT_UPLOAD_NEW);
//			textBox.enterText(LocationSummary.BTN_UPLOAD_INPUT, FilePath.IMAGE_PIXEL_SIZE);
//			foundation.threadWait(5);
//			textBox.enterText(LocationSummary.TXT_ADD_NAME,
//					rstLocationSummaryData.get(CNLocationSummary.REQUIRED_DATA));
//			foundation.click(LocationSummary.BTN_ADD);

			locationSummary.addHomeCommercials(rstLocationSummaryData.get(CNLocationSummary.ADDRESS));

			// disabling location
			foundation.threadWait(Constants.THREE_SECOND);
			locationList.selectLocationName(locationName);
			foundation.waitforElement(LocationSummary.DPD_DISABLED, Constants.SHORT_TIME);
			dropDown.selectItem(LocationSummary.DPD_DISABLED, locationDisabled_Yes, Constants.TEXT);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.click(LocationSummary.POP_UP_BTN_SAVE);

			// Navigating to disabled location list
			dropDown.selectItem(LocationList.DPD_LOCATION_LIST, locationList_Dpd_Values.get(1), Constants.TEXT);

			// validations
			Boolean status = foundation.isDisplayed(locationList.getlocationElement(locationName));
			CustomisedAssert.assertTrue(status);

			// resetting data
			locationList.selectLocationName(locationName);
			foundation.waitforElement(LocationSummary.DPD_DISABLED, Constants.SHORT_TIME);
			dropDown.selectItem(LocationSummary.DPD_DISABLED, locationDisabled_No, Constants.TEXT);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
		} catch (Exception exc) {
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
			foundation.threadWait(5);
			String product = rstGlobalProductChangeData.get(CNGlobalProductChange.PRODUCT_NAME);
			textBox.enterText(GlobalProductChange.TXT_PRODUCT_NAME, product);
			foundation.click(GlobalProductChange.BTN_PRODUCT_APPLY);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(globalProductChange.objProductName(product));
			foundation.waitforClikableElement(GlobalProductChange.BTN_NEXT, Constants.SHORT_TIME);
			foundation.click(GlobalProductChange.BTN_NEXT);

			// Update loyalty filter
			dropDown.selectItem(GlobalProductChange.DPD_LOYALTY_MULTIPLIER, "5", Constants.VALUE);
			foundation.click(GlobalProductChange.BTN_SUBMIT);
			foundation.click(GlobalProductChange.BTN_OK);
			foundation.threadWait(Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProductChange.REASONBOX_BTNOK));
			foundation.objectClick(GlobalProductChange.REASONBOX_BTNOK);
			foundation.threadWait(Constants.SHORT_TIME);

			// Select Menu and Global product
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(subMenu.get(1));

			// Search and select product
			textBox.enterText(GlobalProduct.TXT_FILTER, product);
			foundation.click(globalProduct.getGlobalProduct(product));

			// verify value in loyalty dropdown
			CustomisedAssert.assertEquals(dropDown.getSelectedItem(ProductSummary.DPD_LOYALTY_MULTIPLIER), "5");
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
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
			CustomisedAssert.assertEquals(foundation.getText(LocationSummary.LBL_TAX_CATEGORY), selectedTaxCat);
		} catch (Exception exc) {
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
			String location = propertyFile.readPropertyFile(Configuration.SECOND_LOC, FilePath.PROPERTY_CONFIG_FILE);
			String expectedData = rstDeviceListData.get(CNDeviceList.PRODUCT_NAME);

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORGANIZATION, FilePath.PROPERTY_CONFIG_FILE));

			locationList.selectLocationName(location);

			foundation.waitforElement(LocationSummary.BTN_DEVICE, Constants.SHORT_TIME);
			foundation.click(LocationSummary.BTN_DEVICE);
			textBox.enterText(LocationSummary.TXT_DEVICE_SEARCH, device);

			if (!foundation.isDisplayed(LocationSummary.LBL_CAUTION_ICON)) {
				foundation.click(LocationSummary.BTN_DEPLOY_DEVICE);
				foundation.waitforElement(LocationSummary.TXT_DEVICE_POPUP_SEARCH, Constants.SHORT_TIME);
				textBox.enterText(LocationSummary.TXT_DEVICE_POPUP_SEARCH, device);
				foundation.waitforElement(LocationSummary.LBL_ROW_HEADER, Constants.SHORT_TIME);
				foundation.waitforClikableElement(LocationSummary.LBL_COLUMN_DATA, Constants.SHORT_TIME);
				foundation.click(LocationSummary.LBL_COLUMN_DATA);
				foundation.click(LocationSummary.BTN_DEVICE_ADD);
				foundation.refreshPage();
				textBox.enterText(LocationSummary.TXT_DEVICE_SEARCH, device);
			}

			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.LBL_CAUTION_ICON));
			foundation.objectFocus(LocationSummary.LBL_CAUTION_ICON);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.LBL_HOVER_MESSAGE));
			foundation.click(LocationSummary.LBL_CAUTION_ICON);
			foundation.waitforElement(locationSummary.objDevice(device), Constants.SHORT_TIME);
			// foundation.click(locationSummary.objDevice(device));
			foundation.waitforElement(LocationSummary.TXT_DEVICE_STATUS, Constants.TWO_SECOND);
			CustomisedAssert.assertEquals(foundation.getText(LocationSummary.TXT_DEVICE_STATUS), expectedData);

		} catch (Exception exc) {
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

			String device = propertyFile.readPropertyFile(Configuration.DEVICE_ID, FilePath.PROPERTY_CONFIG_FILE);
			String location = propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1,
					FilePath.PROPERTY_CONFIG_FILE);
			String expectedData = rstDeviceListData.get(CNDeviceList.PRODUCT_NAME);

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			locationList.selectLocationName(location);
			// Navigating to device tab
			foundation.waitforElement(LocationSummary.BTN_DEVICE, Constants.SHORT_TIME);
			foundation.click(LocationSummary.BTN_DEVICE);
			textBox.enterText(LocationSummary.TXT_DEVICE_SEARCH, device);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.LBL_TICKMARK_ICON));
			foundation.click(LocationSummary.LBL_TICKMARK_ICON);
			foundation.waitforElement(locationSummary.objDevice(device), Constants.SHORT_TIME);
			foundation.isDisplayed(locationSummary.objDevice(device));
			foundation.waitforElement(LocationSummary.TXT_DEVICE_STATUS, Constants.SHORT_TIME);
			CustomisedAssert.assertEquals(foundation.getText(LocationSummary.TXT_DEVICE_STATUS), expectedData);

		} catch (Exception exc) {
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

			String device = propertyFile.readPropertyFile(Configuration.DEVICE_ID, FilePath.PROPERTY_CONFIG_FILE);
			String location = propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1,
					FilePath.PROPERTY_CONFIG_FILE);
			String expectedData = rstDeviceListData.get(CNDeviceList.PRODUCT_NAME);

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			locationList.selectLocationName(location);
			// Navigating to device tab
			foundation.waitforElement(LocationSummary.BTN_DEVICE, Constants.SHORT_TIME);
			textBox.enterText(LocationSummary.TXT_DEVICE_SEARCH, device);
			foundation.click(LocationSummary.BTN_DEVICE);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.LBL_TICKMARK_ICON));
			locationSummary.selectDeviceName(device);
			foundation.waitforElement(LocationSummary.TXT_DEVICE_SUMMARY, Constants.SHORT_TIME);
			String actualData = foundation.getText(LocationSummary.TXT_DEVICE_SUMMARY);
			CustomisedAssert.assertEquals(actualData, expectedData);
			actualData = foundation.getText(LocationSummary.TXT_DEVICE_NAME);
			CustomisedAssert.assertEquals(actualData, device);

		} catch (Exception exc) {
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

			String device = propertyFile.readPropertyFile(Configuration.DEVICE_ID, FilePath.PROPERTY_CONFIG_FILE);
			String location = propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1,
					FilePath.PROPERTY_CONFIG_FILE);
			String ipaddres = propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE)
					.split(":")[1].split("/")[2];

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
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.LBL_TICKMARK_ICON));
			Map<String, String> uiData = table.getTblSingleRowRecordUI(LocationSummary.TBL_DEVICE_GRID,
					LocationSummary.TBL_DEVICE_ROW);
			// Table Validations
			CustomisedAssert.assertEquals(uiData.get(dbData.get(0)), device);
			CustomisedAssert.assertEquals(uiData.get(dbData.get(1)), ipaddres);

		} catch (Exception exc) {
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
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			locationList.selectLocationName(location);

			// Verifying Device name is present or not
			if (foundation.isDisplayed(locationSummary.deviceName(device))) {

				// Deleting the Already Present Device
				locationSummary.removeDevice(device);
			}

			// Navigating to device tab
			foundation.waitforElement(LocationSummary.BTN_DEVICE, Constants.SHORT_TIME);
			foundation.click(LocationSummary.BTN_DEPLOY_DEVICE);
			foundation.waitforElement(LocationSummary.TXT_DEVICE_POPUP_SEARCH, Constants.SHORT_TIME);

//			CustomisedAssert.assertTrue(locationSummary.verifySortAscending(LocationSummary.LBL_COLUMN_DATA));
//			foundation.click(LocationSummary.LBL_ROW_HEADER);
//			CustomisedAssert.assertTrue(locationSummary.verifySortDescending(LocationSummary.LBL_COLUMN_DATA));
			textBox.enterText(LocationSummary.TXT_DEVICE_POPUP_SEARCH, device);
			foundation.threadWait(Constants.ONE_SECOND);
			Map<String, String> uiData = table.getTblSingleRowRecordUI(LocationSummary.TBL_DEVICE_POPUP_GRID,
					LocationSummary.TBL_DEVICE_POPUP_ROW);
			Map<String, String> dbData = new HashMap<>();
			dbData.put(expectedData.get(0), device);
			dbData.put(expectedData.get(1), expectedData.get(2));
			foundation.threadWait(Constants.ONE_SECOND);
			CustomisedAssert.assertEquals(uiData, dbData);

		} catch (Exception exc) {
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
			foundation.waitforElementToBeVisible(locationSummary.objUploadStatus("abc"), Constants.SHORT_TIME);
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
			CustomisedAssert.assertEquals(actualData, device);
			actualData = foundation.getText(LocationSummary.LBL_TABLEINFO);
			CustomisedAssert.assertTrue(actualData.contains(dbData.get(4)));
			// Add Functionality
			foundation.click(LocationSummary.LBL_COLUMN_DATA);
			foundation.click(LocationSummary.BTN_DEVICE_ADD);
			foundation.refreshPage();
			foundation.refreshPage();
			foundation.waitforElement(LocationSummary.TXT_DEVICE_SEARCH, Constants.SHORT_TIME);
			textBox.enterText(LocationSummary.TXT_DEVICE_SEARCH, device);
			foundation.waitforElement(locationSummary.objUploadStatus(device), Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(locationSummary.objUploadStatus(device)));
			foundation.click(LocationSummary.BTN_DEPLOY_DEVICE);
			textBox.enterText(LocationSummary.TXT_DEVICE_POPUP_SEARCH, device);
			actualData = foundation.getText(LocationSummary.LBL_TABLE_DATA);
			CustomisedAssert.assertEquals(actualData, dbData.get(2));
			// Close
			foundation.refreshPage();
			// Verifying the device is present or not
			if (foundation.isDisplayed(locationSummary.deviceName(dbData.get(3)))) {

				// Deleting the Already Present Device
				locationSummary.removeDevice(dbData.get(3));
			}

			foundation.click(LocationSummary.BTN_DEPLOY_DEVICE);
			foundation.waitforElement(LocationSummary.TXT_DEVICE_POPUP_SEARCH, Constants.SHORT_TIME);
			textBox.enterText(LocationSummary.TXT_DEVICE_POPUP_SEARCH, dbData.get(3));
			foundation.click(LocationSummary.BTN_DEVICE_CLOSE);
			foundation.waitforElementToBeVisible(locationSummary.objUploadStatus(device), Constants.SHORT_TIME);
			CustomisedAssert.assertFalse(foundation.isDisplayed(locationSummary.objUploadStatus(dbData.get(3))));

		} catch (Exception exc) {
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
			CustomisedAssert.assertEquals(actualData, device);
			actualData = foundation.getText(LocationSummary.LBL_TABLEINFO);
			CustomisedAssert.assertTrue(actualData.contains(dbData.get(3)));
			// Add Functionality
			foundation.click(LocationSummary.LBL_COLUMN_DATA);
			foundation.click(LocationSummary.BTN_DEVICE_ADD);
			foundation.refreshPage();
			foundation.refreshPage();
			foundation.waitforElement(LocationSummary.TXT_DEVICE_SEARCH, Constants.SHORT_TIME);
			textBox.enterText(LocationSummary.TXT_DEVICE_SEARCH, device);
			foundation.waitforElement(locationSummary.objUploadStatus(device), Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(locationSummary.objUploadStatus(device)));
		} catch (Exception exc) {
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
			CustomisedAssert.assertEquals(actualData, expectedData);

		} catch (Exception exc) {
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
					propertyFile.readPropertyFile(Configuration.RNOUS_ORGANIZATION, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(subMenu.get(0));

			String locationName = rstLocationListData.get(CNLocationList.LOCATION_NAME);

			dropDown.selectItem(OrgSummary.DPD_TAX_METHOD, requiredData.get(0), Constants.TEXT);
			foundation.click(OrgSummary.BTN_SAVE);
			foundation.waitforElement(OrgSummary.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			// Location
//			navigationBar.selectOrganization(
//					propertyFile.readPropertyFile(Configuration.ORGANIZATION_OF_HSRLOC, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(subMenu.get(1));
//			navigationBar.navigateToMenuItem("Super#NousDemo Org");
			locationList.selectLocationName(locationName);
			CustomisedAssert.assertFalse(foundation.isDisplayed(LocationSummary.LBL_TAX_MAPPING));

		} catch (Exception exc) {
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

		final String CASE_NUM = "143468";

		browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
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
		try {
			locationList.selectLocationName(locationName);
			locationSummary.selectTab(tabName);
			foundation.click(LocationSummary.LBL_TAX_MAPPING);
			dropDown.selectItem(LocationSummary.DPD_TAX_CAT, requiredData.get(0), Constants.TEXT);
			dropDown.selectItem(LocationSummary.DPD_TAX_RATE, requiredData.get(1), Constants.TEXT);
			foundation.click(LocationSummary.LBL_TAX_CAT_SAVE);
			locationSummary.selectTab(tabName);
			foundation.click(LocationSummary.LBL_TAX_MAPPING);
			CustomisedAssert.assertFalse(dropDown.verifyItemPresent(LocationSummary.DPD_TAX_CAT, requiredData.get(0)));
			foundation.click(LocationSummary.LBL_TAX_CAT_CANCEL);
			foundation.refreshPage();
			locationSummary.selectTab(tabName);
			table.selectRow(requiredData.get(0));
			foundation.waitforElement(LocationSummary.LBL_TAX_CAT_REMOVE, Constants.SHORT_TIME);
			foundation.click(LocationSummary.LBL_TAX_CAT_REMOVE);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
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
			CustomisedAssert.assertTrue(locationSummary.verifySortAscending(LocationSummary.TBL_PRODUCTS_GRID));
			foundation.click(LocationSummary.TBL_NAME_HEADER);
			CustomisedAssert.assertTrue(locationSummary.verifySortDescending(LocationSummary.TBL_PRODUCTS_GRID));
			textBox.enterText(LocationSummary.TXT_SEARCH, product);
			foundation.waitforElement(locationSummary.objProductPrice(product), Constants.SHORT_TIME);

			// Map<String, String> uiTableData =
			// table.getTblSingleRowRecordUI(LocationSummary.TBL_PRODUCTS,
			// LocationSummary.TBL_PRODUCTS_GRID);
			Map<String, String> dbData = new HashMap<>();
			for (int i = 0; i < requiredData.size(); i++) {
				dbData.put(requiredData.get(i), expectedData.get(i));
			}
			/*
			 * foundation.threadWait(Constants.TWO_SECOND);
			 * CustomisedAssert.assertEquals(uiTableData, dbData);
			 */
		} catch (Exception exc) {
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
			// String requiredData = rstLocationData.get(CNLocation.REQUIRED_DATA);

			// List<String> expectedData = Arrays
			// .asList(rstLocationData.get(CNLocation.ACTUAL_DATA).split(Constants.DELIMITER_TILD));
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

		} catch (Exception exc) {
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
			// String requiredData = rstLocationData.get(CNLocation.REQUIRED_DATA);
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
			foundation.threadWait(Constants.THREE_SECOND);
			CustomisedAssert.assertTrue(excel.isFileDownloaded(FilePath.EXCEL_LOCAL_PROD));

			// foundation.copyFile(FilePath.EXCEL_LOCAL_PROD, FilePath.EXCEL_PROD);
			// int excelCount = excel.getExcelRowCount(FilePath.EXCEL_PROD);

			// int excelCount = excel.getExcelRowCount(FilePath.EXCEL_LOCAL_PROD);
			// record count validation
			// CustomisedAssert.assertEquals(String.valueOf(excelCount), requiredData);

			Map<String, String> uidata = table.getTblSingleRowRecordUI(LocationSummary.TBL_PRODUCTS,
					LocationSummary.TBL_PRODUCTS_GRID);
			uidata.remove(expectedData.get(0));
			uidata.remove(expectedData.get(1));
			uidata.remove(expectedData.get(2));
			uidata.remove(expectedData.get(3));

			List<String> uiList = new ArrayList<String>(uidata.values());

			// excel data validation
			List<String> uiListHeaders = new ArrayList<String>(uidata.keySet());
			Map<String, String> excelData = excel.getExcelAsMapFromXSSFWorkbook(FilePath.EXCEL_LOCAL_PROD);

			Map<String, String> expectedValues = new HashMap<String, String>();
			for (int iter = 0; iter < uiListHeaders.size(); iter++) {
				expectedValues.put(uiListHeaders.get(iter), excelData.get(uiListHeaders.get(iter)));
			}

			for (int iter = 0; iter < uiListHeaders.size(); iter++) {
				if (uiListHeaders.get(iter).equals("Price")) {
					uiList.set(iter, Constants.DELIMITER_COMMA
							+ uiList.get(iter).replace(Constants.DELIMITER_COMMA, Constants.EMPTY_STRING));
				}
			}

			for (int iter = 0; iter < uiListHeaders.size(); iter++) {
				if (uiListHeaders.get(iter).equals("Deposit")) {
					expectedValues.put(uiListHeaders.get(iter),
							Constants.DOLLAR_SYMBOL + expectedValues.get(uiListHeaders.get(iter)));
				}
				if (uiListHeaders.get(iter).equals("Price")) {
					expectedValues.put(uiListHeaders.get(iter), Constants.DOLLAR_SYMBOL + expectedValues
							.get(uiListHeaders.get(iter)).replace(Constants.DELIMITER_COMMA, Constants.EMPTY_STRING));
				}
			}
			// locationList.verifyData(uiListHeaders, uiList, expectedValues);
//			CustomisedAssert.assertTrue(excel.verifyExcelData(uiList, FilePath.EXCEL_LOCAL_PROD, 1));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
//		} finally {
//			// delete files
//			foundation.deleteFile(FilePath.EXCEL_LOCAL_PROD);
//			foundation.deleteFile(FilePath.EXCEL_PROD);
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
			foundation.threadWait(Constants.SHORT_TIME);
			// hide functionality
			foundation.click(LocationSummary.BTN_MANAGE_COLUMNS);
			locationSummary.showHideManageColumn(Constants.HIDE, expectedData.get(0));
			foundation.threadWait(Constants.ONE_SECOND);
			foundation.click(LocationSummary.BTN_APPLY);
			foundation.waitforElementToDisappear(LocationSummary.BTN_APPLY, Constants.SHORT_TIME);
			CustomisedAssert.assertFalse(foundation.isDisplayed(locationSummary.objColumnHeaders(expectedData.get(0))));
			// show functionality
			foundation.click(LocationSummary.BTN_MANAGE_COLUMNS);
			locationSummary.showHideManageColumn(Constants.SHOW, expectedData.get(0));
			foundation.threadWait(Constants.ONE_SECOND);
			foundation.click(LocationSummary.BTN_APPLY);
			foundation.waitforElementToDisappear(LocationSummary.BTN_APPLY, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(locationSummary.objColumnHeaders(expectedData.get(0))));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "146435-QAA-102-ADM>Admin>Devices>Devices Section UI & Fields")
	public void deviceSectionUILocationSummary() {
		try {
			final String CASE_NUM = "146435";

			// Reading test data from DataBase
			rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);
//			List<String> devicetabHeaders = Arrays.asList(
//					rstLocationSummaryData.get(CNLocationSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));

			// Select Menu and Menu Item
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Navigating to device tab in location summary page
			locationList.selectLocationName(
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));
			foundation.waitforElement(LocationSummary.BTN_DEVICE, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.BTN_DEPLOY_DEVICE));
			foundation.objectFocus(LocationSummary.BTN_DEPLOY_DEVICE);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.LBL_SHOW_RECORDS));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.LBL_PAGER));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.LBL_TBL_HEADER));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "146436-QAA-102-ADM>Location Summary>Devices>Devices Section UI & Fields")
	public void deviceSectionUIAdminDeviceLocationSummary() {
		try {
			final String CASE_NUM = "146436";

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);
//			String location = propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1,
//					FilePath.PROPERTY_CONFIG_FILE);
			String deviceName = propertyFile.readPropertyFile(Configuration.DEVICE_ID, FilePath.PROPERTY_CONFIG_FILE);

			// Select Menu and Menu Item
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// navigate to admin>device and verify serial number filter functionality
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			CustomisedAssert.assertTrue(foundation.isDisplayed(DeviceDashboard.LBL_ADMIN_DEVICE_DASHBOARD));
			foundation.adjustBrowerSize("0.7");
			textBox.enterText(DeviceList.TXT_SEARCH_DEVICE, deviceName);
			foundation.objectClick(DeviceList.BTN_SUBMIT);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.objectClick(deviceList.deveiceLink(deviceName));

			// Navigating to device tab
			foundation.waitforElement(LocationSummary.BTN_DEVICE, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(DeviceSummary.LBL_DEVICE_SUMMARY));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "143203-QAA-104-ADM>Location Summary>Devices>Duration Link")
	public void verifyDurationLink() {
		try {
			final String CASE_NUM = "143203";

			// Reading test data from DataBase
			rstDeviceListData = dataBase.getDeviceListData(Queries.DEVICE_LIST, CASE_NUM);

			String device = rstDeviceListData.get(CNDeviceList.DEVICE);
			String location = rstDeviceListData.get(CNDeviceList.LOCATION);

			// Select Menu and Menu Item
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Navigate to location summary and verify duration link
			locationList.selectLocationName(location);
			foundation.waitforElement(LocationSummary.BTN_DEVICE, Constants.SHORT_TIME);
			textBox.enterText(LocationSummary.TXT_DEVICE_SEARCH, device);
			foundation.click(LocationSummary.LBL_DURATION);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_DEVICE_STATUS));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "143209-QAA-108-ADM>Location Summary>Devices>Deploy Device-X")
	public void verifyDeployDeviceX() {
		try {
			final String CASE_NUM = "143209";

			// Reading test data from DataBas
			rstDeviceListData = dataBase.getDeviceListData(Queries.DEVICE_LIST, CASE_NUM);

			String location = rstDeviceListData.get(CNDeviceList.LOCATION);

			// Select Menu and Menu Item
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			locationList.selectLocationName(location);
			// Navigating to device tab
			foundation.waitforElement(LocationSummary.BTN_DEVICE, Constants.SHORT_TIME);
			foundation.click(LocationSummary.BTN_DEPLOY_DEVICE);
			foundation.waitforElement(LocationSummary.TXT_DEVICE_POPUP_SEARCH, Constants.LONG_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.LBL_COLUMN_DATA));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.LBL_ROW_HEADER));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_DEVICE_POPUP_SEARCH));
			foundation.click(LocationSummary.LBL_POPUP_DEPLOY_DEVICE_CLOSE);
			foundation.threadWait(Constants.TWO_SECOND);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_DEVICE_SEARCH));
			CustomisedAssert.assertFalse(foundation.isDisplayed(LocationSummary.TXT_DEVICE_POPUP_SEARCH));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "143197-QAA-94-ADM>Location Summary>Loc Link>Loc Summary Save.")
	public void verifyLocationSummarySaveChanges() {
		try {
			final String CASE_NUM = "143197";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);

			String location = rstLocationData.get(CNLocation.LOCATION_NAME);
			List<String> addressDetails = Arrays
					.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// updating the Location Summary Details
			locationList.selectLocationName(location);
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			textBox.enterText(LocationSummary.ADDRESS_INPUT, addressDetails.get(0));
			textBox.enterText(LocationSummary.CONTACTNAME_INPUT, addressDetails.get(1));
			textBox.enterText(LocationSummary.CONTACTEMAIL_INPUT, addressDetails.get(2));
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.isDisplayed(LocationSummary.LBL_SPINNER_MSG);

			locationList.selectLocationName(location);
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);

			// Validating the updated Location Summary Details
			CustomisedAssert.assertEquals(foundation.getAttributeValue(LocationSummary.ADDRESS_INPUT),
					(addressDetails.get(0)));
			CustomisedAssert.assertEquals(foundation.getAttributeValue(LocationSummary.CONTACTNAME_INPUT),
					(addressDetails.get(1)));
			CustomisedAssert.assertEquals(foundation.getAttributeValue(LocationSummary.CONTACTEMAIL_INPUT),
					(addressDetails.get(2)));

			// Clearing the updated Location Summary Details
			textBox.clearText(LocationSummary.ADDRESS_INPUT);
			textBox.clearText(LocationSummary.CONTACTNAME_INPUT);
			textBox.clearText(LocationSummary.CONTACTEMAIL_INPUT);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.isDisplayed(LocationSummary.LBL_SPINNER_MSG);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "143199-QAA-95-ADM>Location Summary>Loc Link>Update Prices.")
	public void verifyUpdatePrices() {
		try {
			final String CASE_NUM = "143199";
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);

			String product = rstLocationData.get(CNLocation.PRODUCT_NAME);
			String location = rstLocationData.get(CNLocation.LOCATION_NAME);
			String tabName = rstLocationData.get(CNLocation.TAB_NAME);
			List<String> price = Arrays
					.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			locationList.selectLocationName(location);

			// Navigating to products tab and Updating the product price by clicking on
			// Update Prices.
			locationSummary.selectingProduct(tabName, product, product, price.get(0));
			foundation.isDisplayed(LocationSummary.LBL_SPINNER_MSG);
			login.logout();
			browser.close();

			// Launch V5 Device and Searching for product
			foundation.threadWait(Constants.TWO_SECOND);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LandingPage.IMG_SEARCH_ICON));
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadTextWithCaseSensitive(product);
			foundation.click(ProductSearch.BTN_PRODUCT);
			CustomisedAssert.assertTrue(foundation.isDisplayed(Order.BTN_CANCEL_ORDER));
			String productPrice = foundation.getText(Order.LBL_PRODUCT_PRICE).split(Constants.DOLLAR)[1];
            
			// verify the display of product price
			CustomisedAssert.assertTrue(productPrice.contains(price.get(0)));
			browser.close();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reverting the price back
			String product = rstLocationData.get(CNLocation.PRODUCT_NAME);
			String location = rstLocationData.get(CNLocation.LOCATION_NAME);
			String tabName = rstLocationData.get(CNLocation.TAB_NAME);
			List<String> price = Arrays
					.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			locationList.selectLocationName(location);
			locationSummary.selectingProduct(tabName, product, product, price.get(1));
			foundation.isDisplayed(LocationSummary.LBL_SPINNER_MSG);
		}
	}

	@Test(description = "143200-QAA-96-ADM>Location Summary>Loc Link>Update Prices and Full Sync.")
	public void verifyUpdatePricesAndFullSync() {
		try {
			final String CASE_NUM = "143200";
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);

			String product = rstLocationData.get(CNLocation.PRODUCT_NAME);
			String location = rstLocationData.get(CNLocation.LOCATION_NAME);
			String tabName = rstLocationData.get(CNLocation.TAB_NAME);
			List<String> price = Arrays
					.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			locationList.selectLocationName(location);

			// Updating the product price and making it to Full sync.
			locationSummary.selectingAndUpdatingProductPrice(tabName, product, price.get(0));
			foundation.click(LocationSummary.BTN_FULL_SYNC);
			foundation.isDisplayed(LocationSummary.LBL_SPINNER_MSG);
			login.logout();
			browser.close();

			// Launch V5 Device and Searching for product
//			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LandingPage.IMG_SEARCH_ICON));
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadTextWithCaseSensitive(product);
			foundation.click(ProductSearch.BTN_PRODUCT);
			CustomisedAssert.assertTrue(foundation.isDisplayed(Order.BTN_CANCEL_ORDER));
			String productPrice = foundation.getText(Order.LBL_PRODUCT_PRICE).split(Constants.DOLLAR)[1];

			// verify the display of product price
			CustomisedAssert.assertTrue(productPrice.contains(price.get(0)));
			browser.close();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reverting the price back
			String product = rstLocationData.get(CNLocation.PRODUCT_NAME);
			String location = rstLocationData.get(CNLocation.LOCATION_NAME);
			String tabName = rstLocationData.get(CNLocation.TAB_NAME);
			List<String> price = Arrays
					.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			locationList.selectLocationName(location);
			locationSummary.selectingProduct(tabName, product, product, price.get(1));
			foundation.isDisplayed(LocationSummary.LBL_SPINNER_MSG);
		}
	}

	@Test(description = "143534-QAA-225-ADM>Location Summary>Tax Mapping>Add Mapping Save & Cancel.")
	public void verifyAddMappingandCancelMappingOfTax() {
		try {
			final String CASE_NUM = "143534";

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
			foundation.click(LocationSummary.LBL_TAX_CAT_CANCEL);
			CustomisedAssert.assertFalse(table.isRowDisplayed(requiredData.get(0)));

			foundation.refreshPage();
			locationSummary.selectTab(tabName);
			foundation.click(LocationSummary.LBL_TAX_MAPPING);
			dropDown.selectItem(LocationSummary.DPD_TAX_CAT, requiredData.get(0), Constants.TEXT);
			dropDown.selectItem(LocationSummary.DPD_TAX_RATE, requiredData.get(1), Constants.TEXT);
			foundation.click(LocationSummary.LBL_TAX_CAT_SAVE);
			locationSummary.selectTab(tabName);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			String tabName = rstLocationData.get(CNLocation.TAB_NAME);
			List<String> requiredData = Arrays
					.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			locationSummary.selectTab(tabName);
			table.selectRow(requiredData.get(0));
			foundation.waitforElement(LocationSummary.LBL_TAX_CAT_REMOVE, Constants.SHORT_TIME);
			foundation.click(LocationSummary.LBL_TAX_CAT_REMOVE);
		}
	}

	@Test(description = "143533-224-ADM>Location Summary>Tax Mapping>Add Mapping And Removing Mapped Tax")
	public void verifyAddMappingandRemovingofMappedTax() {
		try {
			final String CASE_NUM = "143533";

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

//			foundation.refreshPage();
			foundation.click(LocationSummary.LBL_TAX_MAPPING);
			dropDown.selectItem(LocationSummary.DPD_TAX_CAT, requiredData.get(0), Constants.TEXT);
			dropDown.selectItem(LocationSummary.DPD_TAX_RATE, requiredData.get(1), Constants.TEXT);
			foundation.click(LocationSummary.LBL_TAX_CAT_SAVE);

			foundation.refreshPage();
			locationSummary.selectTab(tabName);
			table.selectRow(requiredData.get(0));
			foundation.waitforElement(LocationSummary.LBL_TAX_CAT_REMOVE, Constants.SHORT_TIME);
			foundation.click(LocationSummary.LBL_TAX_CAT_REMOVE);
			locationSummary.selectTab(tabName);
			CustomisedAssert.assertFalse(table.isRowDisplayed(requiredData.get(0)));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "176744-QAA-109-Verify Add button section  is displayed when clicked on Deploy device button on Location Summary Page")
	public void verifyDeployDeviceAddSection() {
		try {
			final String CASE_NUM = "176744";

			// Reading test data from DataBas
			rstDeviceListData = dataBase.getDeviceListData(Queries.DEVICE_LIST, CASE_NUM);
			String location = rstDeviceListData.get(CNDeviceList.LOCATION);

			// Select Menu and Menu Item
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			locationList.selectLocationName(location);

			// Navigating to device tab and click on Add button
			foundation.waitforElement(LocationSummary.BTN_DEVICE, Constants.SHORT_TIME);
			foundation.click(LocationSummary.BTN_DEPLOY_DEVICE);
			foundation.waitforElement(LocationSummary.TXT_DEVICE_POPUP_SEARCH, Constants.LONG_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.LBL_COLUMN_DATA));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.LBL_ROW_HEADER));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_DEVICE_POPUP_SEARCH));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.BTN_DEVICE_ADD));
			foundation.click(LocationSummary.BTN_DEVICE_ADD);
			foundation.refreshPage();
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_DEVICE_SEARCH));
			CustomisedAssert.assertFalse(foundation.isDisplayed(LocationSummary.TXT_DEVICE_POPUP_SEARCH));
			String resultText = foundation.getText(LocationSummary.DEVICE_RECORD);
			CustomisedAssert.assertTrue(resultText.contains(rstDeviceListData.get(CNDeviceList.ERROR_MESSAGE)));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "177340-ADM>LocationSummary>Verify Excel should be downloaded after clicking on export button on Location Summary Page")
	public void verifyExportButtonFunctionality() {
		try {
			final String CASE_NUM = "177340";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase

			rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);

			String location = rstLocationData.get(CNLocation.LOCATION_NAME);

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			locationList.selectLocationName(location);
			// Navigating to products tab
			foundation.waitforElement(LocationSummary.TAB_PRODUCTS, Constants.SHORT_TIME);
			foundation.click(LocationSummary.TAB_PRODUCTS);
			foundation.threadWait(Constants.ONE_SECOND);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.BTN_EXPORT));
			foundation.click(LocationSummary.BTN_EXPORT);
			foundation.threadWait(Constants.THREE_SECOND);
			CustomisedAssert.assertTrue(excel.isFileDownloaded(FilePath.EXCEL_LOCAL_PROD));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// delete files
			foundation.deleteFile(FilePath.EXCEL_LOCAL_PROD);
		}
	}

	@Test(description = "177406-QAA-114-ADM>LocationSummary>Verify all the selected products should be highlighted"
			+ "177616-QAA-115-ADM>Location Summary>Products>Add Product - Select None."
			+ "177679- QAA-111- ADM>Location Summary>Products>Add Product UI and Fields.")
	public void verifyUIWithHighlightedAndNonHighlightedProducts() {
		try {
			final String CASE_NUM = "177406";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase

			rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);

			String location = rstLocationData.get(CNLocation.LOCATION_NAME);

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			locationList.selectLocationName(location);

			// Navigating to products tab
			foundation.waitforElement(LocationSummary.TAB_PRODUCTS, Constants.SHORT_TIME);
			foundation.click(LocationSummary.TAB_PRODUCTS);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.BTN_ADD_PRODUCT));
			foundation.click(LocationSummary.BTN_ADD_PRODUCT);

			// verifying products UI fields
			foundation.waitforElement(LocationSummary.LBL_ADD_PRODUCT, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.LBL_ADD_PRODUCT));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_ADD_PRODUCT_SEARCH));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.BTN_CANCEL_PRODUCT));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.BTN_SELECTALL));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.BTN_SELECTNONE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.LBL_POPUP_ADD_PRODUCT_CLOSE));

			// selecting button select all and verifying all products are highlighted
			foundation.scrollIntoViewElement(LocationSummary.BTN_SELECTALL);
			if (foundation.isDisplayed(LocationSummary.BTN_SELECTALL)) {
				foundation.click(LocationSummary.BTN_SELECTALL);
				foundation.waitforElement(LocationSummary.VALIDATE_HIGHLIGHTED_TEXT, Constants.SHORT_TIME);
				locationSummary.verifyProductsHighlighted("true");
			}
			// selecting button select none and verifying all products are not highlighted
			foundation.click(LocationSummary.BTN_SELECTNONE);
			locationSummary.verifyProductsHighlighted("false");

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "177614-QAA-113-ADM>Location Summary>Products>Add Product - Close"
			+ "177617 -QAA-112- ADM>Location Summary>Products>Add Product - 'X'."
			+ "143219 -QAA-117-ADM>Location Summary>Products>Add Product - Add.")
	public void verifyAddProductAndCloseButtonFunctionality() {
		final String CASE_NUM = "177614";

		// Reading test data from DataBas
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);
		String location = rstLocationData.get(CNLocation.LOCATION_NAME);
		String product = rstLocationData.get(CNLocation.PRODUCT_NAME);
		List<String> printGroup = Arrays
				.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));

		try {

			// Select Menu and Menu Item
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			locationList.selectLocationName(location);

			// Navigating to products tab
			foundation.waitforElement(LocationSummary.TAB_PRODUCTS, Constants.SHORT_TIME);
			foundation.click(LocationSummary.TAB_PRODUCTS);

			// close the popup by close button
			locationSummary.verifyPopUpUIDisplayed();
			foundation.click(LocationSummary.BTN_CANCEL_PRODUCT);
			try {
				foundation.waitforElementToDisappear(LocationSummary.LBL_ADD_PRODUCT, Constants.SHORT_TIME);
				CustomisedAssert.assertFalse(foundation.isDisplayed(LocationSummary.LBL_ADD_PRODUCT));
			} catch (Exception exc) {
				TestInfra.failWithScreenShot(exc.toString());
			}
			foundation.click(LocationSummary.TAB_PRODUCTS);
			locationSummary.verifyProductsUI();

			// close the popup by 'X' button
			locationSummary.verifyPopUpUIDisplayed();
			foundation.click(LocationSummary.LBL_POPUP_ADD_PRODUCT_CLOSE);
			try {
				foundation.waitforElementToDisappear(LocationSummary.LBL_ADD_PRODUCT, Constants.SHORT_TIME);
				CustomisedAssert.assertFalse(foundation.isDisplayed(LocationSummary.LBL_ADD_PRODUCT));
			} catch (Exception exc) {
				TestInfra.failWithScreenShot(exc.toString());
			}
			foundation.click(LocationSummary.TAB_PRODUCTS);
			locationSummary.verifyProductsUI();

			// adding product and verifying on Product UI
			foundation.click(LocationSummary.BTN_ADD_PRODUCT);
			foundation.waitforElementToBeVisible(LocationSummary.TXT_ADD_PRODUCT_SEARCH, Constants.SHORT_TIME);
			foundation.threadWait(Constants.SHORT_TIME);
			textBox.enterText(LocationSummary.TXT_ADD_PRODUCT_SEARCH, product);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(LocationSummary.SELECT_PRODUCT);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(LocationSummary.BTN_ADD);
			foundation.waitforElementToDisappear(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			foundation.refreshPage();
			foundation.waitforElement(LocationSummary.TAB_PRODUCTS, Constants.SHORT_TIME);
			foundation.click(LocationSummary.TAB_PRODUCTS);
			foundation.WaitForAjax(5000);
			foundation.waitforElement(LocationSummary.TXT_PRODUCT_FILTER, Constants.SHORT_TIME);
			foundation.threadWait(Constants.MEDIUM_TIME);
			textBox.enterText(LocationSummary.TXT_PRODUCT_FILTER, product);
			foundation.WaitForAjax(7000);
			Assert.assertTrue(foundation.getText(LocationSummary.PRODUCT_NAME).equals(product));

			// selecting print group
			foundation.waitforElementToBeVisible(LocationSummary.BTN_MANAGE_COLUMNS, Constants.SHORT_TIME);
			foundation.click(LocationSummary.BTN_MANAGE_COLUMNS);
			foundation.waitforElementToBeVisible(LocationSummary.BTN_PRINT_GROUP, Constants.SHORT_TIME);
			foundation.click(LocationSummary.BTN_PRINT_GROUP);
			foundation.click(LocationSummary.BTN_PRINT_GROUP);
			foundation.waitforElementToBeVisible(LocationSummary.BTN_APPLY, Constants.SHORT_TIME);
			foundation.click(LocationSummary.BTN_APPLY);
			foundation.waitforElementToBeVisible(LocationSummary.LBL_PRINT_GROUP, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.LBL_PRINT_GROUP));
			locationSummary.selectPrintGroup(product, printGroup.get(0));
			foundation.click(LocationSummary.TXT_PRODUCT_FILTER);
			foundation.refreshPage();
			foundation.waitforElement(LocationSummary.TAB_PRODUCTS, Constants.SHORT_TIME);
			foundation.click(LocationSummary.TAB_PRODUCTS);
			foundation.WaitForAjax(5000);
			foundation.waitforElement(LocationSummary.TXT_PRODUCT_FILTER, Constants.SHORT_TIME);
			foundation.threadWait(Constants.MEDIUM_TIME);
			textBox.enterText(LocationSummary.TXT_PRODUCT_FILTER, product);
			foundation.WaitForAjax(7000);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.LBL_PRINT_GROUP));
			Assert.assertTrue(foundation.getText(LocationSummary.PRINTGROUP_NAME).equals(printGroup.get(0)));
			locationSummary.selectPrintGroup(product, printGroup.get(1));
			foundation.waitforElementToBeVisible(LocationSummary.BTN_MANAGE_COLUMNS, Constants.SHORT_TIME);
			foundation.click(LocationSummary.BTN_MANAGE_COLUMNS);
			foundation.waitforElementToBeVisible(LocationSummary.BTN_PRINT_GROUP, Constants.SHORT_TIME);
			foundation.click(LocationSummary.BTN_PRINT_GROUP);
			foundation.waitforElementToBeVisible(LocationSummary.BTN_APPLY, Constants.SHORT_TIME);
			foundation.click(LocationSummary.BTN_APPLY);
			foundation.waitforElementToBeVisible(LocationSummary.LBL_PRINT_GROUP, Constants.SHORT_TIME);
			foundation.click(LocationSummary.TXT_PRODUCT_FILTER);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		// reset the data
		foundation.click(LocationSummary.PRODUCT_NAME);
		foundation.waitforElement(LocationSummary.BTN_REMOVE, Constants.SHORT_TIME);
		foundation.click(LocationSummary.BTN_REMOVE);
	}

	@Test(description = "178509-QAA-122-ADM>Location Summary>Products>Update Min. Stock.")
	public void verifyUpdateMinimumStock() {
		final String CASE_NUM = "178509";

		// Reading test data from DataBas
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);
		String location = rstLocationData.get(CNLocation.LOCATION_NAME);
		String product = rstLocationData.get(CNLocation.PRODUCT_NAME);
		List<String> minStock = Arrays
				.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		try {

			// Select Menu and Menu Item
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			locationList.selectLocationName(location);

			// Navigating to products tab
			foundation.waitforElement(LocationSummary.TAB_PRODUCTS, Constants.SHORT_TIME);
			foundation.click(LocationSummary.TAB_PRODUCTS);
			foundation.WaitForAjax(5000);
			foundation.waitforElement(LocationSummary.TXT_PRODUCT_FILTER, Constants.SHORT_TIME);
			foundation.threadWait(Constants.MEDIUM_TIME);
			// updating Min Stock
			textBox.enterText(LocationSummary.TXT_PRODUCT_FILTER, product);
			foundation.threadWait(Constants.MEDIUM_TIME);
			CustomisedAssert.assertTrue(foundation.getText(LocationSummary.PRODUCT_NAME).equals(product));
			locationSummary.enterMinStock(product, minStock.get(0));
			foundation.click(LocationSummary.TXT_PRODUCT_FILTER);
			foundation.refreshPage();
			foundation.waitforElement(LocationSummary.TAB_PRODUCTS, Constants.SHORT_TIME);
			foundation.click(LocationSummary.TAB_PRODUCTS);
			foundation.WaitForAjax(5000);
			foundation.waitforElement(LocationSummary.TXT_PRODUCT_FILTER, Constants.SHORT_TIME);
			foundation.threadWait(Constants.MEDIUM_TIME);
			textBox.enterText(LocationSummary.TXT_PRODUCT_FILTER, product);
			foundation.WaitForAjax(7000);
			// reset the value
			CustomisedAssert.assertTrue(foundation.getText(LocationSummary.MIN_STOCK).equals(minStock.get(0)));
			locationSummary.enterMinStock(product, minStock.get(1));
			foundation.click(LocationSummary.TXT_PRODUCT_FILTER);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "178530-QAA-179-ADM>Location Summary>Promotions>Promotions UI and Fields."
			+ "178577-QAA-181-ADM>Location Summary>Promotions>Manage Columns."
			+ "178542-QAA-180-ADM>Location Summary>Promotions>Create Promotion.")
	public void verifyUIForPromotionsTab() {
		try {
			final String CASE_NUM = "178530";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase

			rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);
			List<String> requiredData = Arrays
					.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));

			String location = rstLocationData.get(CNLocation.LOCATION_NAME);

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			locationList.selectLocationName(location);

			// Navigating to promotions tab
			foundation.waitforElement(LocationSummary.TAB_PROMOTIONS, Constants.SHORT_TIME);
			foundation.click(LocationSummary.TAB_PROMOTIONS);

			// verifying Promotions UI and fields
			foundation.waitforElement(LocationSummary.BTN_CREATE_PROMO, Constants.SHORT_TIME);
			foundation.scrollIntoViewElement(LocationSummary.BTN_CREATE_PROMO);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.BTN_CREATE_PROMO));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.PROMOTIONS_SEARCH));
			locationSummary.verifyPromotionsTableHeaders(requiredData);

			// click on create Promotion button
			foundation.click(LocationSummary.BTN_CREATE_PROMO);
			foundation.threadWait(Constants.TWO_SECOND);

			// validate UI of enter promotion basics page
			assertTrue(foundation.isDisplayed(CreatePromotions.DPD_PROMO_TYPE));
			assertTrue(foundation.isDisplayed(CreatePromotions.TXT_PROMO_NAME));
			assertTrue(foundation.isDisplayed(CreatePromotions.BTN_NEXT));
			assertTrue(foundation.isDisplayed(CreatePromotions.BTN_CANCEL));
			assertEquals(foundation.getText(CreatePromotions.LBL_BASICINFO), requiredData.get(9));
			assertEquals(foundation.getText(CreatePromotions.LBL_ENTER_BASICINFO), requiredData.get(10));
			foundation.navigateToBackPage();
			foundation.waitforElement(LocationSummary.TAB_PROMOTIONS, Constants.SHORT_TIME);
			foundation.click(LocationSummary.TAB_PROMOTIONS);
			foundation.threadWait(Constants.THREE_SECOND);

			// Validate the Manage column Popup
			foundation.click(LocationSummary.BUTTON_MANAGE_COLUMNS);
			foundation.threadWait(Constants.THREE_SECOND);
			assertTrue(foundation.isDisplayed(LocationSummary.MANAGE_COLUMN_POPUP_HEADER));
			// Bug is raised for below reset button -
			// https://365retailmarkets.atlassian.net/browse/SOS-31011
			locationSummary.verifyPromotionsTableHeaders(requiredData);
			assertTrue(foundation.isDisplayed(LocationSummary.MANAGE_COLUMN_APPLY_BUTTON));
			assertTrue(foundation.isDisplayed(LocationSummary.MANAGE_COLUMN_CANCEL_BUTTON));
			foundation.click(LocationSummary.MANAGE_COLUMN_CANCEL_BUTTON);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * @author karthik Ragav
	 *
	 */
	@Test(description = "197172 - verify the extending of products to the location")
	public void verifyExtendingOfProductsToLocation() {
		final String CASE_NUM = "197172";

		// Reading test data from DataBase
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);
		try {
			// launch Browser, Select Menu and location
			navigationBar.launchBrowserAsSuperAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			locationList.selectLocationName(rstLocationData.get(CNLocation.LOCATION_NAME));
			CustomisedAssert.assertTrue(foundation.getText(LocationSummary.VALIDATE_HEADING)
					.contains(rstLocationData.get(CNLocation.LOCATION_NAME)));

			// Navigate to products tab and validate the UI
			foundation.scrollIntoViewElement(LocationSummary.TAB_PRODUCTS);
			foundation.click(LocationSummary.TAB_PRODUCTS);
			locationSummary.verifyPopUpUIDisplayed();

			// Adding the new product to Location
			foundation.threadWait(3);
			locationSummary.selectProduct(rstLocationData.get(CNLocation.PRODUCT_NAME));

			// Verifying the Product is added to Location or not
			foundation.waitforElement(LocationSummary.TAB_PRODUCTS, Constants.SHORT_TIME);
			foundation.click(LocationSummary.TAB_PRODUCTS);
			foundation.WaitForAjax(5000);
			foundation.waitforElement(LocationSummary.TXT_PRODUCT_FILTER, Constants.SHORT_TIME);
			foundation.threadWait(Constants.MEDIUM_TIME);
			textBox.enterText(LocationSummary.TXT_PRODUCT_FILTER, rstLocationData.get(CNLocation.PRODUCT_NAME));
			foundation.WaitForAjax(7000);
			CustomisedAssert.assertTrue(foundation.getText(LocationSummary.PRODUCT_NAME)
					.equals(rstLocationData.get(CNLocation.PRODUCT_NAME)));

			// Validating Add Product another time to check Product OverLay is
			// Displaying Properly or not
			locationSummary.verifyPopUpUIDisplayed();
			foundation.click(LocationSummary.BTN_CANCEL_PRODUCT);
			foundation.waitforElement(LocationSummary.TAB_PRODUCTS, Constants.SHORT_TIME);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// resetting the test data
			locationSummary.removeProductFromLocation(rstLocationData.get(CNLocation.PRODUCT_NAME),
					rstLocationData.get(CNLocation.LOCATION_NAME));
		}
	}

	/**
	 * @author karthik Ragav
	 *
	 */

	@Test(description = "197121 - To ensure that UI is displayed correctly in Location Subsidies page for both TopOff and Rollover Subsidy")
	public void verifySubsidiesUIElementsAndDataSavingInLocationSummaryPage() {
		final String CASE_NUM = "197121";

		// Reading test data from DataBase
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);

		List<String> requiredData = Arrays
				.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> title = Arrays.asList(rstLocationData.get(CNLocation.TITLE).split(Constants.DELIMITER_TILD));
		try {
			// launch Browser, Select Menu and location by Using Operator User
			navigationBar.launchBrowserAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));
			locationList.selectLocationName(rstLocationData.get(CNLocation.LOCATION_NAME));
			CustomisedAssert.assertTrue(foundation.getText(LocationSummary.VALIDATE_HEADING)
					.contains(rstLocationData.get(CNLocation.LOCATION_NAME)));

			// Verifying GMA subsidy, its fields and creating Topoff subsidy
			locationSummary.verifyGMASubsidyUIFields(requiredData);
			locationSummary.verifyBothSubsidesFields(requiredData);
			checkBox.check(LocationSummary.CHK_TOP_OFF_SUBSIDY);
			foundation.threadWait(Constants.ONE_SECOND);
			locationSummary.enterSubsidyGroupNames(title.get(0), title.get(1));

			// Again Revisiting Location Summary page for the same location to validate the
			// GMA Subsidy UI
			locationList.selectLocationName(rstLocationData.get(CNLocation.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			foundation.scrollIntoViewElement(LocationSummary.DPD_GMA_SUBSIDY);
			locationSummary.verifyBothSubsidesFields(requiredData);

			// Validating the Entered Group Names of Subsidies
			String value = foundation.getAttributeValue(LocationSummary.TXT_TOP_OFF_GROUP_NAME);
			CustomisedAssert.assertEquals(value, title.get(0));
			value = foundation.getAttributeValue(LocationSummary.TXT_ROLL_OVER_GROUP_NAME);
			CustomisedAssert.assertEquals(value, title.get(1));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// resetting GMA Subsidy
			locationSummary.subsidyResettingValidationOff(rstLocationData.get(CNLocation.NAME),
					rstLocationData.get(CNLocation.LOCATION_NAME), requiredData.get(1));
		}
	}

	/**
	 * @author afrosean Date:17-08-20222
	 */
	@Test(description = "202842- ADM > Admin > User and Roles verify Navigation bar, access location and Edit users")
	public void verifyNavigationBarAccessLocationAndEditUsersWithOperator() {
		final String CASE_NUM = "202842";

		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);

		List<String> menu = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> datas = Arrays.asList(rstLocationData.get(CNLocation.NAME).split(Constants.DELIMITER_TILD));

		try {
			// launch Browser with super user
			navigationBar.launchBrowserAsSuperAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Navigate to Super > User and Roles
			navigationBar.navigateToMenuItem(menu.get(0));
			userRoles.searchDriver(datas.get(0));
			login.logout();

			// launch Browser by Using Operator User
			navigationBar.launchBrowserAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// verify natigationBar in operator user
			navigationBar.verifyNavigationBarsArePresent();

			// Navigate to location summary page
			locationList.selectLocationName(rstLocationData.get(CNLocation.LOCATION_NAME));
			CustomisedAssert.assertTrue(foundation.getText(LocationSummary.VALIDATE_HEADING)
					.contains(rstLocationData.get(CNLocation.LOCATION_NAME)));

			// Navigate to Admin > User and roles
			navigationBar.navigateToMenuItem(menu.get(1));
			userList.verifyEditUserPage(datas.get(1), datas.get(2));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * @author Prabha Nigam
	 *
	 */

	@Test(description = "202819 - SOS-31922-ADM-Location Summary Page-->Add a Product , Popup Message is not getting displayed for Super")
	public void verifyPopUpMessageWhileAddingProductInLocationSummaryPage() {
		final String CASE_NUM = "202819";

		// Reading test data from DataBase
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);

		String location = rstLocationData.get(CNLocation.LOCATION_NAME);
		String product = rstLocationData.get(CNLocation.PRODUCT_NAME);
		try {
			// launch Browser, Select Menu and location by Using super User

			// Select Menu and Menu Item
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			locationList.selectLocationName(location);

			// Navigating to products tab
			foundation.waitforElement(LocationSummary.TAB_PRODUCTS, Constants.SHORT_TIME);
			foundation.click(LocationSummary.TAB_PRODUCTS);

			// adding product and verifying on Product UI
			foundation.click(LocationSummary.BTN_ADD_PRODUCT);
			foundation.waitforElementToBeVisible(LocationSummary.TXT_ADD_PRODUCT_SEARCH, Constants.SHORT_TIME);
			foundation.threadWait(Constants.SHORT_TIME);
			textBox.enterText(LocationSummary.TXT_ADD_PRODUCT_SEARCH, product);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(LocationSummary.SELECT_PRODUCT);
			foundation.click(LocationSummary.BTN_ADD);
			foundation.waitforElementToBeVisible(LocationList.TXT_SPINNER_SUCCESS_MSG, Constants.LONG_TIME);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// resetting the test data
			foundation.refreshPage();
			foundation.threadWait(Constants.THREE_SECOND);
			locationSummary.removeProductFromLocation(product, location);
		}
	}

	@Test(description = "204719 - ADM > Menu > Create New Location > Verify Daily Trans In Dashboard - TestRail")
	public void verifyDailyTransInDashBoard() {
		try {
			final String CASE_NUM = "204719";
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);
			List<String> DropDownResult = Arrays.asList(
					rstLocationSummaryData.get(CNLocationSummary.FILTER_RESULT).split(Constants.DELIMITER_TILD));
			String location = Constants.AUTO_TEST + string.getRandomCharacter();

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Navigate to menu item -> user and roles
			foundation.threadWait(Constants.SHORT_TIME);
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(LocationList.BTN_CREATE);

			textBox.enterText(LocationSummary.TXT_NAME, location);
			foundation.threadWait(Constants.SHORT_TIME);
			dropDown.selectItem(LocationSummary.DPD_TIME_ZONE, DropDownResult.get(0), Constants.TEXT);
			foundation.threadWait(Constants.SHORT_TIME);
			dropDown.selectItem(LocationSummary.DPD_TYPE, DropDownResult.get(1), Constants.TEXT);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.threadWait(Constants.SHORT_TIME);
			textBox.enterText(LocationList.TXT_FILTER, location);

			foundation.threadWait(Constants.SHORT_TIME);
			Map<String, String> tableInformation = locationList.fetchLocationInformation(location);
			foundation.threadWait(Constants.SHORT_TIME);
			CustomisedAssert.assertEquals(
					tableInformation.get(rstLocationSummaryData.get(CNLocationSummary.COLUMN_NAME)),
					rstLocationSummaryData.get(CNLocationSummary.COLUMN_VALUE));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * @author Prabha Nigam
	 *
	 */

	@Test(description = "202866 - SOS-31922-ADM-Location Summary Page-->Add a Product , Popup Message is not getting displayed for Operator")
	public void verifyPopUpMessageWhileAddingProductInLocationSummaryPageforOperator() {
		final String CASE_NUM = "202866";

		// Reading test data from DataBase
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);

		String location = rstLocationData.get(CNLocation.LOCATION_NAME);
		String product = rstLocationData.get(CNLocation.PRODUCT_NAME);
		try {
			// launch Browser, Select Menu and location by Using super User

			// Select Menu and Menu Item
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			locationList.selectLocationName(location);

			// Navigating to products tab
			foundation.waitforElement(LocationSummary.TAB_PRODUCTS, Constants.SHORT_TIME);
			foundation.click(LocationSummary.TAB_PRODUCTS);

			// adding product and verifying on Product UI
			foundation.click(LocationSummary.BTN_ADD_PRODUCT);
			foundation.waitforElementToBeVisible(LocationSummary.TXT_ADD_PRODUCT_SEARCH, Constants.SHORT_TIME);
			foundation.threadWait(Constants.SHORT_TIME);
			textBox.enterText(LocationSummary.TXT_ADD_PRODUCT_SEARCH, product);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(LocationSummary.SELECT_PRODUCT);
			foundation.click(LocationSummary.BTN_ADD);
			foundation.waitforElementToBeVisible(LocationList.TXT_SPINNER_SUCCESS_MSG, Constants.LONG_TIME);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// resetting the test data
			foundation.refreshPage();
			foundation.threadWait(Constants.THREE_SECOND);
			locationSummary.removeProductFromLocation(product, location);
		}
	}

	/**
	 * @author Prabha Nigam
	 *
	 */

	@Test(description = "203228 - SOS-31835 ADM>Location summary > Product grid> Edit Product>'Close' button is not working properly")
	public void verifyCloseButtonFromProductPopUpOnLocationSummaryPage() {
		final String CASE_NUM = "203228";

		// Reading test data from DataBase
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);

		String location = rstLocationData.get(CNLocation.LOCATION_NAME);
		String product = rstLocationData.get(CNLocation.PRODUCT_NAME);

		try {
			// launch Browser, Select Menu and location by Using super User

			// Select Menu and Menu Item
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			locationList.selectLocationName(location);

			// Navigating to products tab
			foundation.waitforElement(LocationSummary.TAB_PRODUCTS, Constants.SHORT_TIME);
			foundation.click(LocationSummary.TAB_PRODUCTS);

			// Searching product and verifying on Close button
			foundation.waitforElement(LocationSummary.TXT_PRODUCT_FILTER, Constants.SHORT_TIME);
			foundation.threadWait(Constants.MEDIUM_TIME);
			textBox.enterText(LocationSummary.TXT_PRODUCT_FILTER, product);
			foundation.threadWait(5);
			foundation.click(LocationSummary.PRODUCT_NAME);
			foundation.waitforElement(LocationSummary.BTN_REMOVE, Constants.SHORT_TIME);
			foundation.click(LocationSummary.BTN_CLOSE_PRODUCT);
			foundation.threadWait(5);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TAB_PRODUCTS));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * @author afrosean Date:08-09-2022
	 */
	@Test(description = "203865-ADM > Super > Create new location")
	public void verifyUserIsAbleToCreateLocation() {
		final String CASE_NUM = "203865";

		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);

		List<String> datas = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION).split(Constants.DELIMITER_TILD));
		String locationName = rstLocationData.get(CNLocation.NAME) + strings.getRandomCharacter();

		try {

			// launch Browser, Select Menu and location by Using super User
			navigationBar.launchBrowserAsSuperAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// select menu and menu item
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// create location under automation Org location
			locationList.createLocation(locationName, datas.get(1), datas.get(2));

			// search same location
			locationList.verifyLocationInLocationList(rstLocationData.get(CNLocation.NAME), datas.get(3));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * @author sakthir 12-09-2022
	 *
	 */

	@Test(description = "202654-Location Summary > Product Tab - Print Group Shows By Default")
	public void verifyPrintGroupTableHeaderShowsByDefaultInProductTab() {
		final String CASE_NUM = "202654";

		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);

		String location = rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM);
		String product = rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION);
		try {

			// Select Org & Menu
			navigationBar.launchBrowserAsSuperAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// select Location
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));
			locationList.selectLocationName(location);

			// Navigating to products tab
			foundation.waitforElement(LocationSummary.TAB_PRODUCTS, Constants.SHORT_TIME);
			foundation.click(LocationSummary.TAB_PRODUCTS);

			// adding product and verifying on Product UI
			foundation.waitforElementToBeVisible(LocationSummary.PRODUCT_NAME, Constants.SHORT_TIME);
			String Column_Header = foundation.getText(LocationSummary.TBL_PRODUCT_HEADER);
			CustomisedAssert.assertTrue(Column_Header.contains(product));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * @author sakthir Date:30-09-2022
	 */
	@Test(description = "176240-Verify FreedomPay Integration > ADM Premium Payment"
			+ "176137 - ADM >>location Summary >> Devices >> Device Summary Page"
			+ "176134- ADM >>Device Summary Page >>Premium Payment >> Freedom Pay Configuration"
			+ "176132-ADM >>Device Summary Page >>Premium Payment>>Drop down"
			+ "176131-ADM >>Device Summary Page >>Premium Payment option"
			+ "176133-ADM >> Device Summary Page >> Premium Payment >>Premium Pay ID")
	public void verifyADMPremiumPaymentAndFreedomPayConfigurationInDeviceSummary() {
		final String CASE_NUM = "176240";

		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
		rstDeviceListData = dataBase.getDeviceListData(Queries.DEVICE_LIST, CASE_NUM);

		List<String> data = Arrays
				.asList(rstDeviceListData.get(CNDeviceList.SERIAL_NUMBER).split(Constants.DELIMITER_TILD));

		// Launch ADM as super
		navigationBar.launchBrowserAsSuperAndSelectOrg(
				propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

		// navigate to location menu
		navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

		// select Location and verify Location Summary Heading
		locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
		CustomisedAssert.assertTrue(foundation.getText(LocationSummary.LBL_LOCATION_SUMMARY)
				.contains(rstLocationListData.get(CNLocationList.LOCATION_NAME)));
		foundation.objectFocus(LocationSummary.BTN_DEPLOY_DEVICE);

		// enter device and select device
		textBox.enterText(LocationSummary.TXT_DEVICE_SEARCH, rstDeviceListData.get(CNDeviceList.DEVICE));
		locationSummary.selectDeviceName(rstDeviceListData.get(CNDeviceList.DEVICE));
		CustomisedAssert.assertTrue(foundation.isDisplayed(DeviceSummary.LBL_DEVICE_SUMMARY));

		// select payment and freedom payment
		deviceSummary.selectPaymentAndSubPaymet(data.get(0), data.get(1));

		// verify freedom Payment options
		deviceSummary.verifyFreedomPayment();

	}

	/**
	 * @author sakthir Date:14-10-2022
	 */
	@Test(description = "204916-Verify the Stock well Location type in Location summary page for all existing locations"
			+ "204915-Verify the Stock well Location type in Location summary page for new location")
	public void verifyStockwellOptionInTypeFieldForExistingAndNewLocationInLocationSummary() {
		final String CASE_NUM = "204916";

		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);

		String menu = rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM);
		List<String> location = Arrays
				.asList(rstLocationListData.get(CNLocationList.LOCATION_NAME).split(Constants.DELIMITER_TILD));

		try {

			// Launch ADM as super
			navigationBar.launchBrowserAsSuperAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// select location and verify Stock well dropdown option in existing location
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));
			locationList.selectLocationName(location.get(0));
			foundation.scrollIntoViewElement(LocationSummary.DPD_TYPE);
			CustomisedAssert.assertTrue(
					foundation.getTextofListElement(LocationSummary.DPD_OPTION_TYPE).contains(location.get(1)));

			// navigate to Super->location org
			navigationBar.navigateToMenuItem(menu);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// click create new and verify Stock well dropdown option
			foundation.click(LocationList.BTN_CREATE);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.LBL_LOCATION_CREATE));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			foundation.scrollIntoViewElement(LocationSummary.DPD_TYPE);
			CustomisedAssert.assertTrue(
					foundation.getTextofListElement(LocationSummary.DPD_OPTION_TYPE).contains(location.get(1)));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * @author sakthir Date:17-10-2022
	 * @throws AWTException
	 */
	@Test(description = "204920-Verify Stock well Store Id setting should accept only alpha numeric characters with limit upto 100 characters"
			+ "204921-Verify that Stock well Store ID setting field is not mandatory field to save"
			+ "204922-Ensure that new field Stockwell Store ID is displayed when type is set to Stock well in Location Summary page")
	public void verifyStockwellStoreIdNewFieldAndLimitsForNewAndExistingAndSaveWithoutStoreIDValues()
			throws AWTException {
		final String CASE_NUM = "204920";

		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);

		List<String> menu = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> location = Arrays
				.asList(rstLocationListData.get(CNLocationList.LOCATION_NAME).split(Constants.DELIMITER_TILD));
		String Location = Constants.AUTO_TEST + string.getRandomCharacter();

		try {

			// Launch ADM as super
			navigationBar.launchBrowserAsSuperAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// select location and verify Stock well dropdown option in existing location
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));
			locationList.selectLocationName(location.get(0));
			foundation.scrollIntoViewElement(LocationSummary.DPD_TYPE);
			CustomisedAssert.assertTrue(
					foundation.getTextofListElement(LocationSummary.DPD_OPTION_TYPE).contains(location.get(1)));
			dropDown.selectItem(LocationSummary.DPD_TYPE, location.get(1), Constants.TEXT);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_STOCKWELL_STORE_ID));

			// click save while Stockwell Dropdown option selected without Store Id Field
			// data for existing location
			dropDown.selectItem(LocationSummary.DPD_TYPE, location.get(1), Constants.TEXT);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_STOCKWELL_STORE_ID));
			foundation.click(LocationSummary.BTN_SAVE);

			// verify After save for existing location
			locationList.selectLocationName(location.get(0));
			foundation.scrollIntoViewElement(LocationSummary.DPD_TYPE);
			dropDown.selectItem(LocationSummary.DPD_TYPE, location.get(1), Constants.TEXT);
			CustomisedAssert
					.assertTrue(foundation.getAttributeValue(LocationSummary.TXT_STOCKWELL_STORE_ID).length() == 0);

			// verify Store Id Field limits 100 character for existing location
			textBox.enterText(LocationSummary.TXT_STOCKWELL_STORE_ID, location.get(2));
			CustomisedAssert
					.assertTrue(foundation.getAttributeValue(LocationSummary.TXT_STOCKWELL_STORE_ID).length() == 100);
			foundation.click(LocationSummary.BTN_SAVE);

			// verify After save for existing location with Store Id
			locationList.selectLocationName(location.get(0));
			foundation.scrollIntoViewElement(LocationSummary.DPD_TYPE);
			dropDown.selectItem(LocationSummary.DPD_TYPE, location.get(1), Constants.TEXT);
			CustomisedAssert
					.assertTrue(foundation.getAttributeValue(LocationSummary.TXT_STOCKWELL_STORE_ID).length() == 100);

			// navigate to Super->location org
			navigationBar.navigateToMenuItem(menu.get(0));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// click create new
			foundation.click(LocationList.BTN_CREATE);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.LBL_LOCATION_CREATE));

			// verify Stock well dropdown option and verify save by creating new location
			textBox.enterText(LocationSummary.TXT_NAME, Location);
			foundation.threadWait(Constants.SHORT_TIME);
			dropDown.selectItem(LocationSummary.DPD_TIME_ZONE, location.get(3), Constants.TEXT);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			CustomisedAssert.assertTrue(
					foundation.getTextofListElement(LocationSummary.DPD_OPTION_TYPE).contains(location.get(1)));
			dropDown.selectItem(LocationSummary.DPD_TYPE, location.get(1), Constants.TEXT);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_STOCKWELL_STORE_ID));
			CustomisedAssert
					.assertTrue(foundation.getAttributeValue(LocationSummary.TXT_STOCKWELL_STORE_ID).length() == 0);
			foundation.click(LocationSummary.BTN_SAVE);

			// verify After save for new location
			locationList.selectLocationName(Location);
			foundation.scrollIntoViewElement(LocationSummary.DPD_TYPE);
			dropDown.selectItem(LocationSummary.DPD_TYPE, location.get(1), Constants.TEXT);
			CustomisedAssert
					.assertTrue(foundation.getAttributeValue(LocationSummary.TXT_STOCKWELL_STORE_ID).length() == 0);

			// verify Store Id Field limits 100 character for new location
			textBox.enterText(LocationSummary.TXT_STOCKWELL_STORE_ID, location.get(2));
			CustomisedAssert
					.assertTrue(foundation.getAttributeValue(LocationSummary.TXT_STOCKWELL_STORE_ID).length() == 100);
			foundation.click(LocationSummary.BTN_SAVE);

			// verify After save for new location with Store Id
			locationList.selectLocationName(Location);
			foundation.scrollIntoViewElement(LocationSummary.DPD_TYPE);
			dropDown.selectItem(LocationSummary.DPD_TYPE, location.get(1), Constants.TEXT);
			CustomisedAssert
					.assertTrue(foundation.getAttributeValue(LocationSummary.TXT_STOCKWELL_STORE_ID).length() == 100);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// resetting
			navigationBar.navigateToMenuItem(menu.get(0));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));
			locationList.selectLocationName(location.get(0));
			foundation.click(LocationSummary.TXT_STOCKWELL_STORE_ID);
			foundation.clearText();
			foundation.click(LocationSummary.BTN_SAVE);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));
			locationList.selectLocationName(location.get(0));
			dropDown.selectItem(LocationSummary.DPD_TYPE, location.get(7), Constants.TEXT);
			foundation.click(LocationSummary.BTN_SAVE);
			locationList.selectLocationName(Location);
			foundation.click(LocationSummary.TXT_STOCKWELL_STORE_ID);
			foundation.clearText();
			foundation.scrollIntoViewElement(LocationSummary.DPD_DISABLED);
			dropDown.selectItem(LocationSummary.DPD_DISABLED, location.get(6), Constants.TEXT);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.click(LocationSummary.POP_UP_BTN_SAVE);
		}
	}

}