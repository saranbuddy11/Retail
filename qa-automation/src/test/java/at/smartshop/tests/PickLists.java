package at.smartshop.tests;

import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import at.framework.database.mssql.Queries;
import at.framework.database.mssql.ResultSets;
import at.framework.files.Excel;
import at.framework.generic.CustomisedAssert;
import at.framework.generic.DateAndTime;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.Table;
import at.framework.ui.TextBox;
import at.smartshop.database.columns.CNNavigationMenu;
import at.smartshop.database.columns.CNPickList;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.pages.LocationList;
import at.smartshop.pages.LocationSummary;
import at.smartshop.pages.NavigationBar;
import at.smartshop.pages.PickList;

@Listeners(at.framework.reportsetup.Listeners.class)
public class PickLists extends TestInfra {

	private NavigationBar navigationBar = new NavigationBar();
	private TextBox textBox = new TextBox();
	private Foundation foundation = new Foundation();
	private ResultSets dataBase = new ResultSets();
	private PickList pickList = new PickList();
	private Dropdown dropDown = new Dropdown();

	private LocationList locationList = new LocationList();
	private Excel excel = new Excel();
	private Table table = new Table();
	private LocationSummary locationSummary = new LocationSummary();
	private DateAndTime dateAndTime = new DateAndTime();

	private Map<String, String> rstNavigationMenuData;
	private Map<String, String> rstPickListData;

	@Test(description = "143192-QAA-67-verify picklist screen product table data -Super")
	public void verifyPickListColumnHeadersSuper() {
		try {
			final String CASE_NUM = "143192";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstPickListData = dataBase.getPickListData(Queries.PICKLIST, CASE_NUM);
			Map<String, String> dbData_Product = new HashMap<>();

			List<String> dbColumnHeaders = Arrays
					.asList(rstPickListData.get(CNPickList.COLUMN_HEADERS).split(Constants.DELIMITER_TILD));
			List<String> dbRowValues = Arrays
					.asList(rstPickListData.get(CNPickList.ROW_VALUES).split(Constants.DELIMITER_TILD));
			for (int i = 0; i < dbColumnHeaders.size(); i++) {
				dbData_Product.put(dbColumnHeaders.get(i), dbRowValues.get(i));
			}
			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			String menuItem = rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM);
			navigationBar.navigateToMenuItem(menuItem);
//			textBox.enterText(PickList.SEARCH_FILTER, rstPickListData.get(CNPickList.LOCATIONS));
//			foundation.click(PickList.LBL_SELECT_ALL);

			foundation.click(pickList.selectLocationFromList(rstPickListData.get(CNPickList.LOCATIONS)));

			foundation.click(PickList.BTN_APPLY);
			foundation.waitforElement(pickList.objPickList(rstPickListData.get(CNPickList.LOCATIONS)),
					Constants.SHORT_TIME);
			foundation.click(pickList.objPickList(rstPickListData.get(CNPickList.LOCATIONS)));
			foundation.click(PickList.BTN_PICKLIST_PLAN);
			foundation.waitforElement(PickList.LBL_SELECT_ALL, Constants.SHORT_TIME);
			foundation.click(PickList.LBL_SELECT_ALL);
			foundation.click(PickList.LBL_REMOVE);
			foundation.click(PickList.LBL_ADD_PRODUCT);
			foundation.waitforElement(PickList.LBL_ADD_PRODUCT_PICKLIST, Constants.SHORT_TIME);
			textBox.enterText(PickList.LBL_FILTER_TYPE, rstPickListData.get(CNPickList.PRODUCT_NAME));
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.waitforElement(pickList.objPickList(rstPickListData.get(CNPickList.PRODUCT_NAME)),
					Constants.SHORT_TIME);
			foundation.click(PickList.TBL_NEED);
			foundation.objectFocus(PickList.TXT_NEED);
			foundation.click(PickList.TXT_NEED);
			foundation.waitforElement(PickList.TXT_NEED, Constants.LONG_TIME);
			textBox.enterText(PickList.TXT_NEED, rstPickListData.get(CNPickList.NEED));
			foundation.click(pickList.objPickList(rstPickListData.get(CNPickList.PRODUCT_NAME)));
			foundation.click(PickList.LBL_PREVIEW);
			foundation.click(PickList.LBL_Add);
			foundation.waitforElement(PickList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			// Table validations
			Map<String, String> uiData = pickList.getTblSingleRowRecordUI(PickList.TBL_PRODUCT_GRID, PickList.TBL_ROW);
			CustomisedAssert.assertEquals(dbData_Product, uiData);
			foundation.click(pickList.objPickList(rstPickListData.get(CNPickList.LOCATIONS)));
			foundation.click(PickList.LBL_REMOVE);
			foundation.threadWait(Constants.ONE_SECOND);
			login.logout();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "143193-QAA-67-verify picklist screen product table data -finanacer")
	public void verifyPickListColumnHeadersFinancer() {
		try {
			final String CASE_NUM = "143193";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.FINANCE_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstPickListData = dataBase.getPickListData(Queries.PICKLIST, CASE_NUM);
			Map<String, String> dbData_Product = new HashMap<>();

			List<String> dbColumnHeaders = Arrays
					.asList(rstPickListData.get(CNPickList.COLUMN_HEADERS).split(Constants.DELIMITER_TILD));
			List<String> dbRowValues = Arrays
					.asList(rstPickListData.get(CNPickList.ROW_VALUES).split(Constants.DELIMITER_TILD));
			for (int i = 0; i < dbColumnHeaders.size(); i++) {
				dbData_Product.put(dbColumnHeaders.get(i), dbRowValues.get(i));
			}
			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			String menuItem = rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM);
			navigationBar.navigateToMenuItem(menuItem);
//			textBox.enterText(PickList.SEARCH_FILTER, rstPickListData.get(CNPickList.LOCATIONS));
//			foundation.click(PickList.LBL_SELECT_ALL);

			foundation.click(pickList.selectLocationFromList(rstPickListData.get(CNPickList.LOCATIONS)));

			foundation.click(PickList.BTN_APPLY);
			foundation.waitforElement(pickList.objPickList(rstPickListData.get(CNPickList.LOCATIONS)),
					Constants.SHORT_TIME);
			foundation.click(pickList.objPickList(rstPickListData.get(CNPickList.LOCATIONS)));
			foundation.click(PickList.BTN_PICKLIST_PLAN);
			foundation.waitforElement(PickList.LBL_SELECT_ALL, Constants.SHORT_TIME);
			foundation.click(PickList.LBL_SELECT_ALL);
			foundation.click(PickList.LBL_REMOVE);
			foundation.click(PickList.LBL_ADD_PRODUCT);
			foundation.waitforElement(PickList.LBL_ADD_PRODUCT_PICKLIST, Constants.SHORT_TIME);
			textBox.enterText(PickList.LBL_FILTER_TYPE, rstPickListData.get(CNPickList.PRODUCT_NAME));
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.waitforElement(pickList.objPickList(rstPickListData.get(CNPickList.PRODUCT_NAME)),
					Constants.SHORT_TIME);
			foundation.click(PickList.TBL_NEED);
			foundation.objectFocus(PickList.TXT_NEED);
			foundation.click(PickList.TXT_NEED);
			foundation.waitforElement(PickList.TXT_NEED, Constants.LONG_TIME);
			textBox.enterText(PickList.TXT_NEED, rstPickListData.get(CNPickList.NEED));
			foundation.click(pickList.objPickList(rstPickListData.get(CNPickList.PRODUCT_NAME)));
			foundation.click(PickList.LBL_PREVIEW);
			foundation.click(PickList.LBL_Add);
			foundation.waitforElement(PickList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			// Map<String, String> uiData =
			// pickList.getTblSingleRowRecordUI(PickList.TBL_PRODUCT_GRID,
			// PickList.TBL_ROW);
			// CustomisedAssert.assertEquals(dbData_Product, uiData);
			foundation.click(pickList.objPickList(rstPickListData.get(CNPickList.LOCATIONS)));
			foundation.click(PickList.LBL_REMOVE);
			foundation.threadWait(Constants.ONE_SECOND);
			login.logout();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "178590-SOS-29233-Verify Location Dropdown is present on Add Products to Picklist PopUp")
	public void verifyLocationOptionsInAddProductsDropDownInPicklistPopUp() {
		try {
			final String CASE_NUM = "178590";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstPickListData = dataBase.getPickListData(Queries.PICKLIST, CASE_NUM);

			final String location = rstPickListData.get(CNPickList.LOCATIONS);

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			String menuItem = rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM);
			navigationBar.navigateToMenuItem(menuItem);

			// selecting select all location on Picklist Manager
			foundation.click(PickList.BTN_SELECT_ALL);
			foundation.click(PickList.BTN_APPLY);
			foundation.waitforElement(PickList.LBL_SELECT_ALL, Constants.SHORT_TIME);
			foundation.click(PickList.BTN_SELECTALL);
			foundation.click(PickList.BTN_PICKLIST_PLAN);
			foundation.waitforElement(PickList.LBL_ADD_PRODUCT, Constants.SHORT_TIME);
			foundation.click(PickList.LBL_ADD_PRODUCT);
			foundation.waitforElement(PickList.LBL_ADD_PRODUCT_PICKLIST, Constants.SHORT_TIME);

			// verifying Location Dropdown is present or not
			CustomisedAssert.assertTrue(foundation.isDisplayed(PickList.DRP_LOCATION));
			foundation.threadWait(Constants.TWO_SECOND);
			dropDown.selectItem(PickList.DRP_LOCATION, location, Constants.TEXT);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(PickList.BTN_CLOSE);
			foundation.threadWait(Constants.ONE_SECOND);
			login.logout();

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
	// SOS - 25316

	@Test(description = "186564-SOS-25316 ADM>Picklist Manager>Picklist Manager>PickList >Verify Products are displaying under Pick list grid"
			+ "186567-SOS-24688-ADM>PickList Screen>Column data is not displaying properly in the Select product table"
			+ "197160 -SOS-13306-ADM > Product Tab > Pick Lists >Add Product>verify Route column Picklist grid"
			+ "197159 - SOS-13255-ADM > Product Tab > Pick Lists >verify Picklist grid"
			+ "196582 - SOS-27313 ADM > Pick List Manager>Plan picklist>Verify able to Add product")
	public void verifyRecentlyAddedProductOnPickListManagerPageAndTableHeaders() {
		try {
			final String CASE_NUM = "186564";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstPickListData = dataBase.getPickListData(Queries.PICKLIST, CASE_NUM);
			Map<String, String> dbData_Product = new HashMap<>();

			List<String> dbColumnHeaders = Arrays
					.asList(rstPickListData.get(CNPickList.COLUMN_HEADERS).split(Constants.DELIMITER_TILD));
			List<String> dbRowValues = Arrays
					.asList(rstPickListData.get(CNPickList.ROW_VALUES).split(Constants.DELIMITER_TILD));
			for (int i = 0; i < dbColumnHeaders.size(); i++) {
				dbData_Product.put(dbColumnHeaders.get(i), dbRowValues.get(i));
			}

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			String menuItem = rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM);
			navigationBar.navigateToMenuItem(menuItem);

			// selecting required location on Picklist Manager
			foundation.click(pickList.selectLocationFromList(rstPickListData.get(CNPickList.LOCATIONS)));
			foundation.click(PickList.BTN_APPLY);
			foundation.waitforElement(pickList.objPickList(rstPickListData.get(CNPickList.LOCATIONS)),
					Constants.SHORT_TIME);
			foundation.click(pickList.objPickList(rstPickListData.get(CNPickList.LOCATIONS)));
			foundation.click(PickList.BTN_PICKLIST_PLAN);
			foundation.waitforElement(PickList.LBL_SELECT_ALL, Constants.SHORT_TIME);

			// click on add product
			foundation.click(PickList.LBL_ADD_PRODUCT);
			foundation.waitforElement(PickList.LBL_ADD_PRODUCT_PICKLIST, Constants.SHORT_TIME);
			textBox.enterText(PickList.LBL_FILTER_TYPE, rstPickListData.get(CNPickList.PRODUCT_NAME));
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.waitforElement(pickList.objPickList(rstPickListData.get(CNPickList.PRODUCT_NAME)),
					Constants.SHORT_TIME);

			// Update the need count and add the product
			foundation.click(PickList.TBL_NEED);
			foundation.objectFocus(PickList.TXT_NEED);
			foundation.click(PickList.TXT_NEED);
			foundation.waitforElement(PickList.TXT_NEED, Constants.LONG_TIME);
			textBox.enterText(PickList.TXT_NEED, rstPickListData.get(CNPickList.NEED));
			foundation.click(pickList.objPickList(rstPickListData.get(CNPickList.PRODUCT_NAME)));
			foundation.click(PickList.LBL_PREVIEW);
			foundation.click(PickList.LBL_Add);
			foundation.waitforElement(PickList.TXT_SPINNER_MSG, Constants.SHORT_TIME);

			// Verify product is added and column data is coming properly
			Map<String, String> uiData = pickList.getTblSingleRowRecordUI(PickList.TBL_PRODUCT_GRID, PickList.TBL_ROW);
			CustomisedAssert.assertEquals(dbData_Product, uiData);
			foundation.click(pickList.objPickList(rstPickListData.get(CNPickList.LOCATIONS)));
			foundation.click(PickList.LBL_REMOVE);
			foundation.threadWait(Constants.ONE_SECOND);
			login.logout();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

// SOS - 22442
	@Test(description = "186631-SOS-22442-ADM>Verify Driver Dropdown details under Admin >Routes and Drivers under Picklist Manger Page")
	public void verifyDriverDropdownDetailsInAdminRoutesAndPickListManagerPage() {
		try {
			final String CASE_NUM = "186631";
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			List<String> menuItem = Arrays
					.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
			navigationBar.navigateToMenuItem(menuItem.get(0));

			// click on create new and check for dropdown values
			foundation.click(PickList.BTN_CREATE_NEW_ROUTE);
			List<String> routeValues = dropDown.getAllItems(PickList.DRP_ROUTE_DRIVER);
			routeValues.set(0, "Default");
			Collections.sort(routeValues);

			// navigate to Product > Picklist
			navigationBar.navigateToMenuItem(menuItem.get(1));
			foundation.click(PickList.BTN_SELECT_ALL);
			foundation.click(PickList.TXT_FILTERBY);
			List<String> pickListValues = dropDown.getAllItems(PickList.DRP_ROUTE_DRIVER);
			pickListValues.set(0, "Default");
			Collections.sort(pickListValues);
			assertEquals(routeValues, pickListValues);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	// SOS- 14363
	@Test(description = "195609-SOS-14363-ADM>Verify' Cancel PickList Order' button is present on Picklist Manger Page"
			+ "195598-SOS-22330-ADM>Pick List Manager"
			+ "196846-SOS-27340-ADM>Pick List Manager>Select  Location>Refresh button Verify Refresh the Select location grid"
			+ "195613 -SOS-26642 ADM > Pick List Manager> Send to Lightspeed")
	public void verifyCancelOrderPicklistButtonInPicklistManagerPage() {
		try {
			final String CASE_NUM = "195609";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstPickListData = dataBase.getPickListData(Queries.PICKLIST, CASE_NUM);
			List<String> sendToLightSpeedPopup = Arrays
					.asList(rstPickListData.get(CNPickList.ROW_VALUES).split(Constants.DELIMITER_TILD));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			String menuItem = rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM);
			navigationBar.navigateToMenuItem(menuItem);

			// validating Picklist Manager Page
			CustomisedAssert.assertTrue(foundation.isDisplayed(PickList.PAGE_TITLE));

			// selecting required location on Picklist Manager Page
			foundation.click(pickList.selectLocationFromList(rstPickListData.get(CNPickList.LOCATIONS)));
			foundation.click(PickList.BTN_APPLY);
			foundation.waitforElement(pickList.objPickList(rstPickListData.get(CNPickList.LOCATIONS)),
					Constants.SHORT_TIME);
			foundation.click(pickList.objPickList(rstPickListData.get(CNPickList.LOCATIONS)));

			// Verifying Refresh button and click on it
			foundation.click(PickList.BTN_REFRESH);
			foundation.waitforElement(PickList.BTN_CONFIRM_REFRESH, Constants.SHORT_TIME);
			foundation.click(PickList.BTN_CONFIRM_REFRESH);

			// Click on Send to Lightspeed button
			foundation.click(PickList.BTN_SEND_TO_LIGHTSPEED);

			// Verifying the details on confirm Popup for sending to Lightspeed
			CustomisedAssert.assertEquals(foundation.getText(PickList.TXT_SEND_PICKLIST), sendToLightSpeedPopup.get(0));
			CustomisedAssert.assertEquals(foundation.getText(PickList.TXT_CONFIRM_SENDING),
					sendToLightSpeedPopup.get(1));
			CustomisedAssert.assertEquals(foundation.getText(PickList.TXT_CONTINUE), sendToLightSpeedPopup.get(2));
			CustomisedAssert.assertEquals(foundation.getText(PickList.BTN_CANCEL), sendToLightSpeedPopup.get(3));
			CustomisedAssert.assertEquals(foundation.getText(PickList.BTN_YES), sendToLightSpeedPopup.get(4));

			// Select yes and send to Lightspeed
			foundation.click(PickList.BTN_YES);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(pickList.selectLocationFromList(rstPickListData.get(CNPickList.LOCATIONS)));
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(PickList.BTN_APPLY);
			foundation.waitforElementToBeVisible(PickList.BTN_CANCEL_ORDER, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(PickList.BTN_CANCEL_ORDER));
			foundation.threadWait(5);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			foundation.click(PickList.BTN_CANCEL_ORDER);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(PickList.SELECT_ORDER_TAB);
			foundation.click(PickList.BTN_CONFIRM_CANCEL_ORDER);
		}
	}

	// SOS - 11502
	@Test(description = "196125-SOS-11502-ADM > Location Summary > Products Tab > Export > Verify Picklist Action Column is Present")
	public void verifyPickListActionColumnPresentinTableAndExcel() {
		try {
			final String CASE_NUM = "196125";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstPickListData = dataBase.getPickListData(Queries.PICKLIST, CASE_NUM);
			String location = rstPickListData.get(CNPickList.LOCATIONS);

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			locationList.selectLocationName(location);

			// Navigating to products tab
			foundation.waitforElement(LocationSummary.TAB_PRODUCTS, Constants.SHORT_TIME);
			foundation.click(LocationSummary.TAB_PRODUCTS);
			foundation.click(LocationSummary.BTN_EXPORT);
			foundation.threadWait(Constants.THREE_SECOND);
			CustomisedAssert.assertTrue(excel.isFileDownloaded(FilePath.EXCEL_LOCAL_PROD));

			// verifying UI headers is same as on excel data
			Map<String, String> uidata = table.getTblSingleRowRecordUI(LocationSummary.TBL_PRODUCTS,
					LocationSummary.TBL_PRODUCTS_HEADER);
			List<String> uiListHeaders = new ArrayList<String>(uidata.keySet());
			CustomisedAssert.assertTrue(excel.verifyExcelData(uiListHeaders, FilePath.EXCEL_LOCAL_PROD, 0));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			foundation.deleteFile(FilePath.EXCEL_LOCAL_PROD);
		}
	}

	// SOS - 1669
	@Test(description = "196138-SOS-1669-ADM > Location Summary > Products Tab > Verify Picklist Action Column dropdown option after save")
	public void verifyPickListActionDropDownValueIsGettingSaved() {

		try {
			final String CASE_NUM = "196138";
			// Reading test data from DataBase
			rstPickListData = dataBase.getPickListData(Queries.PICKLIST, CASE_NUM);
			String product = rstPickListData.get(CNPickList.PRODUCT_NAME);
			String location = rstPickListData.get(CNPickList.LOCATIONS);
			List<String> PickListValue = Arrays
					.asList(rstPickListData.get(CNPickList.ROW_VALUES).split(Constants.DELIMITER_TILD));

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

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
			locationSummary.selectPickList(product, PickListValue.get(0));
			foundation.click(LocationSummary.TXT_SEARCH);
			// foundation.click(LocationSummary.BTN_SAVE);
			foundation.refreshPage();
			// locationList.selectLocationName(location);
			foundation.click(LocationSummary.TAB_PRODUCTS);
			foundation.threadWait(Constants.ONE_SECOND);
			textBox.enterText(LocationSummary.TXT_SEARCH, product);
			foundation.waitforElement(locationSummary.objProductPrice(product), Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(
					foundation.getText(pickList.verifyPickListColumn(product)).equals(PickListValue.get(0)));

			// reset the value
			locationSummary.selectPickList(product, PickListValue.get(1));
			foundation.click(LocationSummary.TXT_SEARCH);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * @author prabhanigam
	 * @Date -30/06/2022
	 */
	@Test(description = "195605-SOS-22334-ADM>Pick List Manager>History Button"
			+ "195601-SOS-22434-ADM>Pick List Manager>History Button>History grid Apply Date range for picklist history"
			+ "195602-SOS-22435-ADM>Pick List Manager>History Button>History grid Apply Location for picklist history"
			+ "195603-SOS-22436-ADM>Pick List Manager>History Button>History grid Search specific Location")
	public void verifyHistoryButtonFieldsOnPickListManagerPage() {
		try {
			final String CASE_NUM = "195605";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstPickListData = dataBase.getPickListData(Queries.PICKLIST, CASE_NUM);
			List<String> dbData = Arrays
					.asList(rstPickListData.get(CNPickList.COLUMN_HEADERS).split(Constants.DELIMITER_TILD));
			List<String> records = Arrays
					.asList(rstPickListData.get(CNPickList.RECORDS).split(Constants.DELIMITER_TILD));
			List<String> dateRange = Arrays
					.asList(rstPickListData.get(CNPickList.ROW_VALUES).split(Constants.DELIMITER_TILD));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			String menuItem = rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM);
			navigationBar.navigateToMenuItem(menuItem);
			foundation.click(PickList.BTN_HISTORY);
			CustomisedAssert.assertTrue(foundation.isDisplayed(PickList.LBL_HISTORY_TITLE));

			// Verifying the Table Headers
			Map<String, String> uiTableHeaders = table.getTblHeadersPickListHistory(PickList.TBL_ROW_HEADERS);
			List<String> uiListHeaders = new ArrayList<String>(uiTableHeaders.keySet());
			Collections.sort(uiListHeaders);
			CustomisedAssert.assertTrue(uiListHeaders.equals(dbData));

			// verifying Date Range Grid and selecting date
			foundation.click(PickList.TAB_DATE_RANGE);
			foundation.WaitForAjax(3000);
			CustomisedAssert.assertTrue(foundation.isDisplayed(PickList.TAB_START_DATE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(PickList.TAB_END_DATE));
			pickList.verifyDateRangeText(dateRange);

			// selecting today's date and verifying it
			pickList.selectDateRange(records.get(0));
			foundation.waitforElement(PickList.TAB_DATE_RANGE, Constants.SHORT_TIME);
			String currentDate = dateAndTime.getDateAndTime(Constants.REGEX_YYYY_MM_DD, Constants.TIME_ZONE_INDIA);
			String day = foundation.getText(PickList.TAB_DATE_RANGE);
			CustomisedAssert.assertTrue(day.contains(currentDate));
			foundation.click(PickList.BTN_SEARCH_HISTORY);
			String recordText = foundation.getText(PickList.TXT_HISTORY_RECORD);
			CustomisedAssert.assertTrue(recordText.contains("of") && recordText.contains("records"),
					"[Fail]: No total record text found");

			// verifying default location as 'ALL' and selecting specific location
			CustomisedAssert.assertTrue(foundation.getText(PickList.TXT_DEFAULT_LOC).contains(records.get(2)));
			foundation.click(PickList.LNK_REMOVE_ORG);
			dropDown.selectItem(PickList.DRP_SELECT_LOC, records.get(1), Constants.TEXT);
			foundation.click(PickList.BTN_SEARCH_HISTORY);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "195608-SOS-22342-ADM>Pick List Manager>Select Location>In picklist grid Select All location>Refresh Button")
	public void verifyRefreshButtonOnPickListManagerPage() {
		try {
			final String CASE_NUM = "195608";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstPickListData = dataBase.getPickListData(Queries.PICKLIST, CASE_NUM);

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			String menuItem = rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM);
			navigationBar.navigateToMenuItem(menuItem);

			// selecting select all location on PickList Manager and verifying all products
			// are highlighted
			foundation.click(PickList.BTN_SELECT_ALL);
			foundation.threadWait(Constants.TWO_SECOND);
			String color = foundation.getBGColor(PickList.VALIDATE_HIGHLIGHTED_LOCATIONS);
			CustomisedAssert.assertEquals(color, rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));
			foundation.click(PickList.BTN_APPLY);
			foundation.waitforElement(PickList.LBL_SELECT_ALL, Constants.SHORT_TIME);
			foundation.click(PickList.LBL_SELECT_ALL);
			foundation.waitforElement(PickList.VALIDATE_HIGHLIGHTED_PRODUCTS, Constants.SHORT_TIME);
			pickList.verifyProductsHighlighted("true");
			foundation.click(PickList.BTN_REFRESH);
			foundation.waitforElement(PickList.BTN_CONFIRM_REFRESH, Constants.SHORT_TIME);
			foundation.click(PickList.BTN_CONFIRM_REFRESH);
			CustomisedAssert.assertTrue(foundation.isDisplayed(PickList.PAGE_TITLE));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "195610-SOS-22344-ADM>Pick List Manager>Select Location>Export file")
	public void verifyExportButtonFunctionalityOnPickListManagerPage() {
		try {
			final String CASE_NUM = "195610";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstPickListData = dataBase.getPickListData(Queries.PICKLIST, CASE_NUM);

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			String menuItem = rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM);
			navigationBar.navigateToMenuItem(menuItem);

			// validating Picklist Manager Page
			CustomisedAssert.assertTrue(foundation.isDisplayed(PickList.PAGE_TITLE));

			// selecting required location on Picklist Manager Page
			foundation.click(pickList.selectLocationFromList(rstPickListData.get(CNPickList.LOCATIONS)));
			foundation.click(PickList.BTN_APPLY);
			foundation.waitforElement(pickList.objPickList(rstPickListData.get(CNPickList.LOCATIONS)),
					Constants.SHORT_TIME);
			foundation.click(pickList.objPickList(rstPickListData.get(CNPickList.LOCATIONS)));
			foundation.click(PickList.BTN_EXPORT);
			foundation.threadWait(Constants.THREE_SECOND);
			CustomisedAssert.assertTrue(excel
					.isFileDownloaded(FilePath.pickListFilePathWithDateAndDay(rstPickListData.get(CNPickList.RECORDS),
							rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION))));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			foundation.deleteFile(FilePath.pickListFilePathWithDateAndDay(rstPickListData.get(CNPickList.RECORDS),
					rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION)));
		}
	}

	/**
	 * @author afrosean Story SOS-25625 & SOS-29192
	 * @date: 20-06-2022
	 */
	@Test(description = "196844-ADM > Pick List Manager>Select location>Verify Reset negative to zero"
			+ "197247-ADM > Pick List Manager>Plan picklist>Verify Planned pick list show products")
	public void verifyResetNegativeToZeroAndPlannedPickList() {
		final String CASE_NUM = "196844";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstPickListData = dataBase.getPickListData(Queries.PICKLIST, CASE_NUM);

		try {
			// Login to ADM
			navigationBar.launchBrowserAsSuperAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Navigate to product--> pickList and click on Negative to zero in pick list
			// manager
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			foundation.waitforElementToBeVisible(PickList.PAGE_TITLE, 5);
			pickList.selectLocationAndClickOnNavigateToZero(rstPickListData.get(CNPickList.APLOCATION),
					rstPickListData.get(CNPickList.LOCATIONS));

			// verify the plan pick list(s)
			foundation.waitforElementToBeVisible(PickList.FILTER_LOCATION, 5);
			foundation.click(pickList.objPickList(rstPickListData.get(CNPickList.APLOCATION)));
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(PickList.BTN_PICKLIST_PLAN);
			foundation.waitforElementToBeVisible(PickList.FILTER_GRID, 5);
			String data = foundation.getText(PickList.TBL_ROW_DATA);
			CustomisedAssert.assertTrue(data.contains(rstPickListData.get(CNPickList.APLOCATION)));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * @author afrosean Story SOS-27323
	 * @date: 23-06-2022
	 */
	@Test(description = "C197105-ADM > Pick List Manager>Filter By Tab>Verify product 'filter by ' display only for selected option"
			+ "C197137-ADM > Pick List Manager>Filter By Tab>User select to filter by 'Pick List Action'"
			+ "C197138-ADM > Pick List Manager>Filter By Tab>User cancels out of the filtered selection")
	public void verifyFilterByAndPickListActionInPickListManagerOptions() {
		final String CASE_NUM = "197105";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstPickListData = dataBase.getPickListData(Queries.PICKLIST, CASE_NUM);

		List<String> requiredData = Arrays
				.asList(rstPickListData.get(CNPickList.APLOCATION).split(Constants.DELIMITER_TILD));
		List<String> dropDownData = Arrays
				.asList(rstPickListData.get(CNPickList.RECORDS).split(Constants.DELIMITER_TILD));

		try {
			// Login to ADM
			navigationBar.launchBrowserAsSuperAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Navigate to product--> pickList and click on pick list manager
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			foundation.waitforElementToBeVisible(PickList.PAGE_TITLE, 5);
			pickList.selectLocationInFilterAndApply(requiredData.get(0));

			// Click on plan pick list and verify the grid
			foundation.waitforElementToBeVisible(PickList.FILTER_LOCATION, 5);
			foundation.click(pickList.objPickList(requiredData.get(0)));
			foundation.click(PickList.BTN_PICKLIST_PLAN);
			foundation.waitforElementToBeVisible(PickList.FILTER_GRID, 5);
			String data = foundation.getText(PickList.TBL_ROW_DATA);
			CustomisedAssert.assertTrue(data.contains(requiredData.get(0)));

			// select the UPC and verify same upc id in grid
			pickList.selectDropdownValueAndApply(requiredData.get(1), requiredData.get(3));
			String datas = foundation.getText(PickList.TBL_ROW_DATA);
			CustomisedAssert.assertTrue(datas.contains(requiredData.get(3)));

			// select the product ID and verify the same product id in grid
			foundation.click(PickList.BTN_FILTER_CANCEL);
			foundation.waitforElementToBeVisible(PickList.POPUP_HEADER, 5);
			foundation.click(PickList.BTN_YES);
			pickList.selectDropdownValueAndApply(requiredData.get(2), requiredData.get(4));
			datas = foundation.getText(PickList.TBL_ROW_DATA);
			CustomisedAssert.assertTrue(datas.contains(requiredData.get(4)));

			// verify the pick list Actions dropdown
			pickList.verifyPickListActionsInDropdown(dropDownData, PickList.DPD_PICKLIST_ACTIONS);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * @author afrosean Story SOS-27323
	 * @date: 27-06-2022
	 */
	@Test(description = "C197141-ADM>Pick List Manager>Verify ability to sort picklist page"
			+ "C197106-ADM > Pick List Manager>Filter By Tab>User select to filter by 'Category'")
	public void verifyTableSortingAndCategoryFilterOptionsInPickListPage() {
		final String CASE_NUM = "197141";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstPickListData = dataBase.getPickListData(Queries.PICKLIST, CASE_NUM);

		List<String> requiredData = Arrays
				.asList(rstPickListData.get(CNPickList.APLOCATION).split(Constants.DELIMITER_TILD));
		try {
			// Login to ADM
			navigationBar.launchBrowserAsSuperAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Navigate to product--> pickList and click on Negative to zero in pick list
			// manager
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			foundation.waitforElementToBeVisible(PickList.PAGE_TITLE, 5);
			pickList.selectLocationInFilterAndApply(requiredData.get(0));

			// Click on plan pick list and verify the grid
			foundation.waitforElementToBeVisible(PickList.FILTER_LOCATION, 5);
			foundation.click(pickList.objPickList(requiredData.get(0)));
			foundation.click(PickList.BTN_PICKLIST_PLAN);

			// verify the dropDown category
			foundation.waitforElementToBeVisible(PickList.FILTER_PICKLIST, 5);
			String item = dropDown.getSelectedItem(PickList.DPD_CATEGORY);
			CustomisedAssert.assertEquals(item, requiredData.get(1));
			CustomisedAssert.assertTrue(foundation.isDisplayed(PickList.DPD_CATEGORY));
			dropDown.selectItem(PickList.DPD_CATEGORY, requiredData.get(2), Constants.TEXT);
			foundation.click(PickList.BTN_FILTER_APPLY);
			foundation.threadWait(Constants.SHORT_TIME);
			String datas = foundation.getText(PickList.TBL_ROW_DATA);
			CustomisedAssert.assertTrue(datas.contains(requiredData.get(2)));

			// Click on cancel and verify the ascending and decending in products name
			foundation.click(PickList.BTN_FILTER_CANCEL);
			foundation.waitforElementToBeVisible(PickList.POPUP_HEADER, 5);
			foundation.click(PickList.BTN_YES);
			foundation.waitforElementToBeVisible(PickList.PRODUCT_NAME_GRID, 5);
			CustomisedAssert.assertTrue(foundation.isDisplayed(PickList.PRODUCT_NAME_GRID));
			foundation.click(PickList.PRODUCT_NAME_GRID);
			datas = foundation.getText(PickList.PRODUCT_DATAS);
			CustomisedAssert.assertTrue(datas.contains(requiredData.get(3)));
			foundation.waitforElementToBeVisible(PickList.PRODUCT_NAME_GRID, 5);
			foundation.click(PickList.PRODUCT_NAME_GRID);
			datas = foundation.getText(PickList.PRODUCT_DATAS);
			CustomisedAssert.assertTrue(datas.contains(requiredData.get(4)));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * @author afrosean Story SOS-27323
	 * @date: 29-06-2022
	 */
	@Test(description = "C195612-ADM>Pick List Manager>Schedule>Select Location>Set Plan schedule for selected location")
	public void verifyScheduleLocationPlanForSelectedLocation() {
		final String CASE_NUM = "195612";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstPickListData = dataBase.getPickListData(Queries.PICKLIST, CASE_NUM);

		List<String> requiredData = Arrays
				.asList(rstPickListData.get(CNPickList.LOCATIONS).split(Constants.DELIMITER_TILD));

		try {
			// Login to ADM
			navigationBar.launchBrowserAsSuperAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Navigate to product--> pickList and click on scheduling
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			foundation.waitforElementToBeVisible(PickList.PAGE_TITLE, 5);
			foundation.click(PickList.BTN_SCHEDULING);

			// Navigate to route scheduling page and select location
			CustomisedAssert.assertTrue(foundation.isDisplayed(PickList.TXT_ROUTE_SCHEDULING));
			foundation.click(pickList.selectLocationFromList(rstPickListData.get(CNPickList.APLOCATION)));
			foundation.scrollIntoViewElement(PickList.BTN_APPLY);
			foundation.click(PickList.BTN_APPLY);
			foundation.waitforElement(pickList.objPickList(rstPickListData.get(CNPickList.APLOCATION)),
					Constants.SHORT_TIME);

			// Enter all the datas in route driver and date
			pickList.checkboxsServiceDay(requiredData.get(0), requiredData.get(1), "true");
			foundation.click(PickList.BTN_SAVE);
			foundation.waitforElementToBeVisible(PickList.SUCCESS_MSG, 5);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// resetting the data in route scheduling
			foundation.refreshPage();
			CustomisedAssert.assertTrue(foundation.isDisplayed(PickList.TXT_ROUTE_SCHEDULING));
			foundation.click(pickList.selectLocationFromList(rstPickListData.get(CNPickList.APLOCATION)));
			foundation.scrollIntoViewElement(PickList.BTN_APPLY);
			foundation.click(PickList.BTN_APPLY);
			foundation.waitforElement(pickList.objPickList(rstPickListData.get(CNPickList.APLOCATION)),
					Constants.SHORT_TIME);
			pickList.checkboxsServiceDay(requiredData.get(2), requiredData.get(2), "false");
			foundation.click(PickList.BTN_SAVE);
			foundation.waitforElement(PickList.SUCCESS_MSG, 5);
		}
	}

	/**
	 * @author afrosean Story SOS-22340
	 * @date: 30-06-2022
	 */
	@Test(description = "C195606-ADM>Pick List Manager>Select  Location>In picklist grid Select All location")
	public void verifyPicklistGridSelectAllLocation() {
		final String CASE_NUM = "195606";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		
		List<String> colours = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION).split(Constants.DELIMITER_TILD));


		try {
			// Login to ADM
			navigationBar.launchBrowserAsSuperAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Navigate to product--> pickList and click on scheduling
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			foundation.waitforElementToBeVisible(PickList.PAGE_TITLE, 5);

			// Select All Location And Click on apply
			foundation.click(PickList.BTN_SELECT_ALL);

			foundation.scrollIntoViewElement(PickList.BTN_APPLY);
			foundation.threadWait(Constants.TWO_SECOND);
			String color = foundation.getBGColor(PickList.VALIDATE_HIGHLIGHTED_LOCATIONS);
			foundation.threadWait(Constants.TWO_SECOND);
			CustomisedAssert.assertEquals(color, colours.get(0));
			foundation.click(PickList.BTN_APPLY);
			foundation.waitforElementToBeVisible(PickList.SELECT_ALL, 5);
			CustomisedAssert.assertTrue(foundation.isDisplayed(PickList.SELECT_ALL));

			// verify highlight the all selected location
			foundation.click(PickList.SELECT_ALL);
			color = foundation.getBGColor(PickList.SELECTED_LOCATION);
			CustomisedAssert.assertEquals(color, colours.get(1));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			foundation.deleteFile(FilePath.EXCEL_LOCAL_PROD);
		}
	}

	/**
	 * @author prabhanigam
	 * @Date -30/06/2022
	 */
	@Test(description = "197158-SOS-13250-ADM > Product Tab > Pick Lists >verify Locations are available"
			+ "197171 - SOS-13365-ADM > Product Tab > Pick Lists >verify Pick List Manager displays all buttons even without location selection")
	public void verifyButtonsInPickListManagerAndLocationsOnServiceSchedulePage() {
		try {
			final String CASE_NUM = "197158";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstPickListData = dataBase.getPickListData(Queries.PICKLIST, CASE_NUM);

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			String menuItem = rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM);
			navigationBar.navigateToMenuItem(menuItem);

			// validating Picklist Manager Page
			CustomisedAssert.assertTrue(foundation.isDisplayed(PickList.PAGE_TITLE));

			// verifying History, Refresh and Scheduling buttons are present
			CustomisedAssert.assertTrue(foundation.isDisplayed(PickList.BTN_REFRESH));
			CustomisedAssert.assertTrue(foundation.isDisplayed(PickList.BTN_HISTORY));
			CustomisedAssert.assertTrue(foundation.isDisplayed(PickList.BTN_SCHEDULING));

			// selecting required location on Picklist Manager Page
			foundation.click(PickList.BTN_SCHEDULING);
			foundation.click(pickList.selectLocationFromList(rstPickListData.get(CNPickList.LOCATIONS)));
			foundation.click(PickList.BTN_APPLY);
			CustomisedAssert.assertTrue(foundation.isDisplayed(PickList.TXT_SERVICE_SCHEDULE));
			CustomisedAssert.assertEquals(foundation.getText(PickList.TXT_SCHEDULING_LOCATION),
					rstPickListData.get(CNPickList.LOCATIONS));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}

	}

	/**
	 * @author prabhanigam
	 * @Date -30/06/2022
	 */
	@Test(description = "196142-SOS-26624 ADM > Pick List Manager Screen > Layout > Operator views the Locations tab"
			+ "196143 - SOS-26624 ADM > Pick List Manager Screen > Layout > view the Filter By tab"
			+ "197503 - SOS-26624  ADM > Pick List Manager Screen > Layout >choose the planning dropdown 'All' and Servicing dropdown 'All service Days'"
			+ "197504 - SOS-26624 ADM > Pick List Manager Screen > Layout >verify the Last Pick List Created date and Last Inventoried")
	public void verifyPickListManagerPageWithOperatorUser() {
		try {
			final String CASE_NUM = "196142";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstPickListData = dataBase.getPickListData(Queries.PICKLIST, CASE_NUM);
			List<String> dbData = Arrays
					.asList(rstPickListData.get(CNPickList.ROW_VALUES).split(Constants.DELIMITER_TILD));
			List<String> values = Arrays
					.asList(rstPickListData.get(CNPickList.RECORDS).split(Constants.DELIMITER_TILD));
			List<String> dbColumnHeaders = Arrays
					.asList(rstPickListData.get(CNPickList.COLUMN_HEADERS).split(Constants.DELIMITER_TILD));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			String menuItem = rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM);
			navigationBar.navigateToMenuItem(menuItem);

			// validating selectAll, DeselectAll, Clear and Apply button
			foundation.click(PickList.BTN_SELECT_ALL);
			foundation.threadWait(Constants.TWO_SECOND);
			foundation.click(PickList.TXT_DESELECT_ALL);
			CustomisedAssert.assertTrue(foundation.isDisplayed(PickList.VALIDATE_DESELECTED_LOCATION));
			foundation.threadWait(Constants.TWO_SECOND);
			foundation.click(PickList.BTN_SELECT_ALL);
			foundation.click(PickList.BTN_CLEAR);
			foundation.waitforElement(PickList.BTN_YES, Constants.SHORT_TIME);
			foundation.click(PickList.BTN_YES);
			CustomisedAssert.assertTrue(foundation.isDisplayed(PickList.VALIDATE_DESELECTED_LOCATION));
			foundation.threadWait(Constants.TWO_SECOND);
			foundation.click(pickList.selectLocationFromList(rstPickListData.get(CNPickList.LOCATIONS)));
			foundation.click(PickList.BTN_APPLY);
			foundation.threadWait(Constants.TWO_SECOND);
			foundation.waitforElement(pickList.objPickList(rstPickListData.get(CNPickList.LOCATIONS)),
					Constants.MEDIUM_TIME);

			// Validating Fields on Filter By Tab
			foundation.click(PickList.TXT_FILTERBY);
			pickList.verifyDropDownValues(values, dbData);
			CustomisedAssert.assertTrue(foundation.isDisplayed(PickList.LAST_PICKLIST_DATE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(PickList.LAST_INVENTORIED_DATE));

			// select Planning as ALL and Servicing as All Service Days
			dropDown.selectItem(PickList.DRP_PLANNING, values.get(1), Constants.TEXT);
			dropDown.selectItem(PickList.DRP_SERVICING, values.get(2), Constants.TEXT);
			foundation.click(PickList.BTN_APPLY_FILTERBY);
			foundation.waitforElement(pickList.objPickList(rstPickListData.get(CNPickList.LOCATIONS)),
					Constants.MEDIUM_TIME);
			CustomisedAssert.assertTrue(foundation.getText(PickList.TXT_FILTERED_LOCATION)
					.equals(rstPickListData.get(CNPickList.LOCATIONS)));

			// validating table headers
			pickList.getTableHeaders();
			pickList.verifyLocationHeaders(dbColumnHeaders);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}

	}

}
