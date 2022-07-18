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
import at.framework.ui.CheckBox;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.Table;
import at.framework.ui.TextBox;
import at.smartshop.database.columns.CNNavigationMenu;
import at.smartshop.database.columns.CNPickList;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.pages.CreateLocker;
import at.smartshop.pages.LocationList;
import at.smartshop.pages.LocationSummary;
import at.smartshop.pages.NavigationBar;
import at.smartshop.pages.PickList;
import at.smartshop.pages.UserRoles;

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
	private UserRoles userRoles = new UserRoles();

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
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
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
			foundation.click(PickList.LBL_SELECT_ALL);
			foundation.click(PickList.BTN_PICKLIST_PLAN);
			foundation.waitforElement(PickList.LBL_ADD_PRODUCT, Constants.SHORT_TIME);
			foundation.click(PickList.LBL_ADD_PRODUCT);
			foundation.waitforElement(PickList.LBL_ADD_PRODUCT_PICKLIST, Constants.SHORT_TIME);

			// verifying Location Dropdown is present or not
			CustomisedAssert.assertTrue(foundation.isDisplayed(PickList.DRP_LOCATION));
			dropDown.selectItem(PickList.DRP_LOCATION, location, Constants.TEXT);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(PickList.BTN_CLOSE);
			foundation.threadWait(Constants.ONE_SECOND);
			login.logout();

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "186564-SOS-25316 ADM>Picklist Manager>Picklist Manager>PickList >Verify Products are displaying under Pick list grid"
			+ "186567-SOS-24688-ADM>PickList Screen>Column data is not displaying properly in the Select product table")
	public void verifyRecentlyAddedProductOnPickListManagerPageAndTableHeaders() {
		try {
			final String CASE_NUM = "186564";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
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
			+ "195598-SOS-22330-ADM>Pick List Manager")
	public void verifyCancelOrderPicklistButtonInPicklistManagerPage() {
		try {
			final String CASE_NUM = "195609";

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
			foundation.click(PickList.BTN_SEND_TO_LIGHTSPEED);
			foundation.click(PickList.BTN_YES);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(pickList.selectLocationFromList(rstPickListData.get(CNPickList.LOCATIONS)));
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(PickList.BTN_APPLY);
			foundation.waitforElementToBeVisible(PickList.BTN_CANCEL_ORDER, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(PickList.BTN_CANCEL_ORDER));

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
			pickList.selectLocationInFilterApplyAndClickOnPlanPick(requiredData.get(0));

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
			pickList.selectLocationInFilterApplyAndClickOnPlanPick(requiredData.get(0));

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
	 * @author afrosean Story SOS-22355
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
			foundation.click(PickList.BTN_APPLY);
			foundation.waitforElementToBeVisible(PickList.SELECT_ALL, 5);
			CustomisedAssert.assertTrue(foundation.isDisplayed(PickList.SELECT_ALL));

			// verify highlight the all selected location
			foundation.click(PickList.SELECT_ALL);
			String color = foundation.getBGColor(PickList.SELECTED_LOCATION);
			CustomisedAssert.assertEquals(color, rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));
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

	@Test(description = "195605-SOS-22334-ADM>Pick List Manager>History Button")
	public void verifyHistoryButtonOnPickListManagerPage() {
		try {
			final String CASE_NUM = "195605";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstPickListData = dataBase.getPickListData(Queries.PICKLIST, CASE_NUM);
			List<String> dbData = Arrays
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

			// selecting select all location on Picklist Manager and verifying all products
			// are highlighted
			foundation.click(PickList.BTN_SELECT_ALL);
			foundation.threadWait(Constants.TWO_SECOND);
			String color = foundation.getBGColor(PickList.VALIDATE_HIGHLIGHTED_LOCATIONS);
			CustomisedAssert.assertEquals(color, rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));
			foundation.click(PickList.BTN_APPLY);
			foundation.waitforElement(PickList.LBL_SELECT_ALL, Constants.MEDIUM_TIME);
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

			// selecting required location on PickList Manager Page
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
	 * @author afrosean Story SOS-26625
	 * @date: 04-07-2022
	 */
	@Test(description = "C196144- ADM > Pick List Manager> Filter By Tab >Verify user can enters a product name to be applied to the filter"
			+ "C196146-ADM > Pick List Manager> Filter By Tab > User selects to filter locations and products by UPC"
			+ "C196149-ADM > Pick List Manager> Filter By Tab > User selects to filter locations and products by Product ID Range"
			+ "C196145-ADM > Pick List Manager> Filter By Tab > User selects to filter locations and products by UPC Range"
			+ "C196147-ADM > Pick List Manager> Filter By Tab >User selects to filter locations and products by Product ID")
	public void verifyUserCanEnterProductNameToBeAppliedToTheFilterWithOperator() {
		final String CASE_NUM = "196144";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstPickListData = dataBase.getPickListData(Queries.PICKLIST, CASE_NUM);

		List<String> requiredData = Arrays
				.asList(rstPickListData.get(CNPickList.LOCATIONS).split(Constants.DELIMITER_TILD));

		try {
			// Login to ADM
			navigationBar.launchBrowserAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Navigate to product--> pickList and click on pick list manager
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			foundation.waitforElementToBeVisible(PickList.PAGE_TITLE, 5);
			pickList.selectLocationInFilterApplyAndClickOnPlanPick(requiredData.get(0));

			// Enter a product name in product name box and verify
			textBox.enterText(PickList.TXT_PRODUCT_NAME, requiredData.get(1));
			foundation.click(PickList.BTN_FILTER_APPLY);
			foundation.waitforElementToBeVisible(PickList.TBL_ROW_DATA, 5);
			String data = foundation.getText(PickList.TBL_ROW_DATA);
			CustomisedAssert.assertTrue(data.contains(requiredData.get(1)));

			// Press the "x" in the "Product Name" field
			foundation.waitforElementToBeVisible(PickList.PRODUCT_CANCEL, 5);
			foundation.click(PickList.PRODUCT_CANCEL);
			CustomisedAssert
					.assertTrue(foundation.getTextAttribute(PickList.TXT_PRODUCT_NAME, Constants.VALUE).isEmpty());
			foundation.click(PickList.BTN_FILTER_CANCEL);
			foundation.waitforElementToBeVisible(PickList.POPUP_HEADER, 5);
			foundation.click(PickList.BTN_YES);

			// select UPC in dropDown
			pickList.selectDropdownInFilterBy(requiredData.get(2));

			// Select Product ID in dropDown
			pickList.selectDropdownInFilterBy(requiredData.get(3));

			// select UPC Range in dropDown
			pickList.selectDropdownInFilterByAndVerify(requiredData.get(4));

			// select Product ID Range in dropDown
			pickList.selectDropdownInFilterByAndVerify(requiredData.get(5));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * @author afrosean
	 * Date: 15-07-2022
	 */
	@Test(description = "197507- ADM > Pick List Manager>Plan picklist>Verify Disabled Driver and Route are still displayed while adding product"
			+ "197506-ADM > Pick List Manager>Plan picklist>Verify Export file")
	public void verifyDisableDriverAndRoute() {
		final String CASE_NUM = "197507";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstPickListData = dataBase.getPickListData(Queries.PICKLIST, CASE_NUM);

		List<String> menu = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> header = Arrays.asList(rstPickListData.get(CNPickList.LOCATIONS).split(Constants.DELIMITER_TILD));
		List<String> requiredData = Arrays
				.asList(rstPickListData.get(CNPickList.APLOCATION).split(Constants.DELIMITER_TILD));

		try {
			// Login to ADM
			navigationBar.launchBrowserAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Navigate to Admin-->Routes to disable the routes
			navigationBar.navigateToMenuItem(menu.get(0));	
			pickList.verifyRouteHeaders(header);
			pickList.searchRouteAndClickOnActiveCheckbox(requiredData.get(0), requiredData.get(2), requiredData.get(3), "uncheck" );

			// Navigate to Admin-->User and Roles to verify the driver name
			navigationBar.navigateToMenuItem(menu.get(2));
			userRoles.searchDriver(requiredData.get(3));

			// Navigate to product-->pickList and verify route
			navigationBar.navigateToMenuItem(menu.get(1));
			pickList.selectLocationInFilterApplyAndClickOnPlanPick(requiredData.get(1));

			// verify the router column
			String data = foundation.getText(PickList.TBL_ROW_DATA);
			CustomisedAssert.assertFalse(data.contains(requiredData.get(0)));

			// Add product and verify route
			pickList.searchProductAndAddProduct(requiredData.get(4), requiredData.get(5));

			// search product and export
			pickList.searchProductAndExport(requiredData.get(4), requiredData.get(0), requiredData.get(1),
					requiredData.get(7), rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));			

			// Delete the product
			foundation.waitforElementToBeVisible(PickList.DELETE_BTN, 5);
			foundation.click(PickList.DELETE_BTN);
			CustomisedAssert.assertTrue(foundation.isDisplayed(PickList.BTN_FILTER_APPLY));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// Navigate to Admin-->Routes to enable the routes
			navigationBar.navigateToMenuItem(menu.get(0));
			pickList.searchRouteAndClickOnActiveCheckbox(requiredData.get(0), requiredData.get(2), requiredData.get(3), "check");

			// delete downloaded file
			foundation.deleteFile(FilePath.pickListFilePath(requiredData.get(7),
					rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION)));
		}

	}
}
