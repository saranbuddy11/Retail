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
	private DateAndTime dateAndTime = new DateAndTime();
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

	@Test(description = "178590-SOS-29233-Verify Location Dropdown is present on Add Products to Picklist PopUp"
			+ "196847 -SOS-27311-ADM>Pick List Manager>Select Location>Verify plan pick list reports")
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

			// verifying Location is present or not
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
			foundation.waitforElementToBeVisible(PickList.TBL_ADD_PRODUCT, Constants.SHORT_TIME);
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
			+ "195613 -SOS-26642 ADM > Pick List Manager> Send to Lightspeed"
			+ "196849-SOS-27313 ADM > Pick List Manager>Plan picklist>Send to Lightspeed")
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
			List<String> menuItem = Arrays
					.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
			
			navigationBar.navigateToMenuItem(menuItem.get(1));

			// Changing the dropdown value of Has Lightspeed to 'Yes' on Org Summary Page
			foundation.scrollIntoViewElement(PickList.DRP_HAS_LIGHTSPEED);
			if(!dropDown.getSelectedItem(PickList.DRP_HAS_LIGHTSPEED).equals("Yes")) {
			dropDown.selectItem(PickList.DRP_HAS_LIGHTSPEED, "Yes", Constants.TEXT);
			foundation.threadWait(3);
		    foundation.click(PickList.BTN_SAVE_LIGHTSPEED);}
		    foundation.threadWait(3);
			navigationBar.navigateToMenuItem(menuItem.get(0));

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
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(pickList.selectLocationFromList(rstPickListData.get(CNPickList.LOCATIONS)));
			foundation.click(PickList.BTN_APPLY);
			foundation.waitforElement(pickList.objPickList(rstPickListData.get(CNPickList.LOCATIONS)),
					Constants.SHORT_TIME);
			foundation.click(pickList.objPickList(rstPickListData.get(CNPickList.LOCATIONS)));
			foundation.waitforElement(PickList.BTN_SEND_TO_LIGHTSPEED, 3);
			foundation.threadWait(Constants.SHORT_TIME);
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
			foundation.threadWait(Constants.MEDIUM_TIME);
			foundation.waitforElementToBeVisible(PickList.BTN_CANCEL_ORDER, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(PickList.BTN_CANCEL_ORDER));
			foundation.click(PickList.BTN_CANCEL_ORDER);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(PickList.SELECT_ORDER_TAB);
			foundation.click(PickList.BTN_CONFIRM_CANCEL_ORDER);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} 
//		finally {
//			
//		}
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
			foundation.threadWait(Constants.THREE_SECOND);
			foundation.isDisplayed(LocationSummary.TBL_PRODUCTS_GRID);
			foundation.click(LocationSummary.BTN_EXPORT);
			foundation.threadWait(Constants.THREE_SECOND);
			CustomisedAssert.assertTrue(excel.isFileDownloaded(FilePath.EXCEL_LOCAL_PROD));
			foundation.threadWait(Constants.SHORT_TIME);
			// verifying UI headers is same as on excel data
			Map<String, String> uidata = table.getTblSingleRowRecordUI(LocationSummary.TBL_PRODUCTS,
					LocationSummary.TBL_PRODUCTS_HEADER);
			List<String> uiListHeaders = new ArrayList<String>(uidata.keySet());
			

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} 
		finally {
			foundation.deleteFile(FilePath.EXCEL_LOCAL_PROD);
			foundation.deleteFile(FilePath.EXCEL_PROD);
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
			foundation.waitforElementToBeVisible(PickList.BTN_PICKLIST_PLAN, 5);
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
			// foundation.waitforElementToBeVisible(PickList.SUCCESS_MSG, 5);
			// CustomisedAssert.assertTrue(foundation.isDisplayed(PickList.SUCCESS_MSG) );
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
			pickList.checkboxsServiceDay(requiredData.get(2), requiredData.get(3), "false");
			foundation.click(PickList.BTN_SAVE);
			// foundation.waitforElement(PickList.SUCCESS_MSG, 5);
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

			// selecting select all location on Picklist Manager and verifying all products
			// are highlighted
			foundation.click(PickList.BTN_SELECT_ALL);
			foundation.threadWait(Constants.TWO_SECOND);
			String color = foundation.getBGColor(PickList.VALIDATE_HIGHLIGHTED_LOCATIONS);
			CustomisedAssert.assertEquals(color, rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));
			foundation.click(PickList.BTN_APPLY);
			foundation.waitforElement(PickList.SELECT_ALL, Constants.MEDIUM_TIME);
			foundation.click(PickList.SELECT_ALL);
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
			excel.isFileDownloaded(FilePath.pickListFilePathWithDateAndDay(rstPickListData.get(CNPickList.RECORDS),
							rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION)));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			foundation.deleteFile(FilePath.pickListFilePathWithDateAndDay(rstPickListData.get(CNPickList.RECORDS),
					rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION)));
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
			List<String> dbColor = Arrays.asList(
					rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION).split(Constants.DELIMITER_TILD));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			String menuItem = rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM);
			navigationBar.navigateToMenuItem(menuItem);

			// validating selectAll, DeselectAll, Clear and Apply button
			foundation.click(PickList.BTN_SELECT_ALL);
			foundation.threadWait(Constants.TWO_SECOND);
			String color = foundation.getBGColor(PickList.VALIDATE_HIGHLIGHTED_LOCATIONS);
			CustomisedAssert.assertEquals(color, dbColor.get(0));
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

	/**
	 * @author prabhanigam
	 * @Date -20/07/2022
	 */
	@Test(description = "198386-SOS-26654 ADM>Pick List Manager>Select All location>Verify Filter By display selected location"
			+ "198388-SOS-26654 ADM>Pick List Manager>Select All location>Filter By>Verify 'Refresh' button"
			+ "198389-SOS-26654 ADM>Pick List Manager >Select location>Click 'Filter By'> Verify ' Reset Negatives to Zero' Button"
			+ "198390-SOS-26654 ADM>Pick List Manager >Select location>Click 'Filter By'> Verify ' Send To LightSpeed' Button"
			+ "198387-SOS-26654 ADM>Pick List Manager>Select All location>Filter By> 'Export' button>Verify Excel sheet displaying the selected filter details")
	public void verifyFilterByTabUIElementsOnPickListManager() {
		try {
			final String CASE_NUM = "198386";

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

			// selecting required location on Picklist Manager
			foundation.click(pickList.selectLocationFromList(rstPickListData.get(CNPickList.LOCATIONS)));
			foundation.click(PickList.BTN_APPLY);
			foundation.waitforElement(pickList.objPickList(rstPickListData.get(CNPickList.LOCATIONS)),
					Constants.SHORT_TIME);

			// Validating Fields on Filter By Tab
			foundation.click(PickList.TXT_FILTERBY);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(pickList.objPickList(rstPickListData.get(CNPickList.LOCATIONS)));
			String color = foundation.getBGColor(PickList.TAB_HIGHLIGHTED_LOCATION);
			CustomisedAssert.assertEquals(color, rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));

			// verifying Refresh button
			foundation.click(PickList.BTN_REFRESH);
			foundation.threadWait(Constants.TWO_SECOND);
			CustomisedAssert.assertTrue(foundation.isDisplayed(PickList.BTN_CANCEL));
			CustomisedAssert.assertTrue(foundation.isDisplayed(PickList.BTN_CONFIRM_REFRESH));
			foundation.click(PickList.BTN_CONFIRM_REFRESH);

			// Navigate to product--> pickList and click on Negative to zero in pick list
			// manager
			foundation.threadWait(Constants.SHORT_TIME);
			pickList.selectLocationAndClickOnNavigateToZero(rstPickListData.get(CNPickList.LOCATIONS),
					sendToLightSpeedPopup.get(0));

			// Validating Send To LightSpeed on Filter By Tab
			foundation.click(PickList.TXT_FILTERBY);
			foundation.click(pickList.objPickList(rstPickListData.get(CNPickList.LOCATIONS)));
			foundation.threadWait(Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(PickList.BTN_SEND_TO_LIGHTSPEED));
			foundation.click(PickList.BTN_SEND_TO_LIGHTSPEED);

			// Verifying the details on confirm Popup for sending to Lightspeed
			foundation.threadWait(Constants.TWO_SECOND);
			CustomisedAssert.assertEquals(foundation.getText(PickList.TXT_SEND_PICKLIST), sendToLightSpeedPopup.get(1));
			CustomisedAssert.assertEquals(foundation.getText(PickList.TXT_CONFIRM_SENDING),
					sendToLightSpeedPopup.get(2));
			CustomisedAssert.assertEquals(foundation.getText(PickList.TXT_CONTINUE), sendToLightSpeedPopup.get(3));
			CustomisedAssert.assertEquals(foundation.getText(PickList.BTN_CANCEL), sendToLightSpeedPopup.get(4));
			CustomisedAssert.assertEquals(foundation.getText(PickList.BTN_YES), sendToLightSpeedPopup.get(5));

			// Select yes and send to Lightspeed
			foundation.click(PickList.BTN_YES);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(pickList.selectLocationFromList(rstPickListData.get(CNPickList.LOCATIONS)));
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(PickList.BTN_APPLY);
			foundation.click(PickList.TXT_FILTERBY);
			foundation.threadWait(5);
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

	/**
	 * @author prabhanigam
	 * @Date -25/07/2022
	 */
	@Test(description = "198391-SOS-26654 ADM>Pick List Manager >Select location>Click 'Filter By'> Verify ' Push to inventory' Button"
			+ "198392-SOS-26654 ADM>Pick List Manager >Select location>Click 'Filter By'> Verify ' Push to inventory' cancel Prompt")
	public void verifyPushToInventoryButtonAndPopUpOnFilterByPickListManager() {
		// Reading test data from DataBase
		final String CASE_NUM = "198391";
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstPickListData = dataBase.getPickListData(Queries.PICKLIST, CASE_NUM);
		List<String> pushToInventoryPopup = Arrays
				.asList(rstPickListData.get(CNPickList.ROW_VALUES).split(Constants.DELIMITER_TILD));
		List<String> hasLightspeed = Arrays
				.asList(rstPickListData.get(CNPickList.RECORDS).split(Constants.DELIMITER_TILD));
		List<String> menuItem = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		try {

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.navigateToMenuItem(menuItem.get(0));

			// Changing the dropdown value of Has Lightspeed to 'No' on Org Summary Page
			pickList.selectingLightSpeed(hasLightspeed.get(1));

			// Navigating to Products>Picklist
			navigationBar.navigateToMenuItem(menuItem.get(1));

			// validating Picklist Manager Page
			foundation.waitforElement(PickList.PAGE_TITLE, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(PickList.PAGE_TITLE));

			// selecting required location on Picklist Manager Page
			foundation.click(pickList.selectLocationFromList(rstPickListData.get(CNPickList.LOCATIONS)));
			foundation.click(PickList.BTN_APPLY);
			foundation.click(PickList.TXT_FILTERBY);
			foundation.waitforElement(pickList.objPickList(rstPickListData.get(CNPickList.LOCATIONS)),
					Constants.SHORT_TIME);
			foundation.click(pickList.objPickList(rstPickListData.get(CNPickList.LOCATIONS)));

			// Click on Push To Inventory button
			foundation.click(PickList.BTN_PUSH_TO_INVENTORY);

			// Verifying the details on confirm Popup for sending to Lightspeed
			foundation.waitforElement(PickList.BTN_YES, Constants.SHORT_TIME);
			CustomisedAssert.assertEquals(foundation.getText(PickList.TXT_SEND_PICKLIST), pushToInventoryPopup.get(0));
			CustomisedAssert.assertEquals(foundation.getText(PickList.TXT_CONFIRM_SENDING),
					pushToInventoryPopup.get(1));
			CustomisedAssert.assertEquals(foundation.getText(PickList.TXT_CONTINUE), pushToInventoryPopup.get(2));
			CustomisedAssert.assertEquals(foundation.getText(PickList.BTN_CANCEL), pushToInventoryPopup.get(3));
			CustomisedAssert.assertEquals(foundation.getText(PickList.BTN_YES), pushToInventoryPopup.get(4));

			// Select yes and push to Inventory
			foundation.click(PickList.BTN_YES);
			foundation.threadWait(Constants.MEDIUM_TIME);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		// Resetting the data
		finally {
			// Changing the dropdown value of Has Lightspeed to 'Yes' on Org Summary Page

			// Navigating to Super >Org Summary
			navigationBar.navigateToMenuItem(menuItem.get(0));
			pickList.selectingLightSpeed(hasLightspeed.get(0));
		}
	}

	/**
	 * @author prabhanigam
	 * @Date -06/07/2022
	 */
	@Test(description = "SOS-22432 ADM > Pick List Screen > Push to Inventory"
			+ "SOS-22342 ADM > Pick List Screen > Push to Inventory > Cancel Prompt > Yes")
	public void verifyPushToInventoryButtonAndPopUpOnPickListManager() {
		// Reading test data from DataBase
		final String CASE_NUM = "197704";
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstPickListData = dataBase.getPickListData(Queries.PICKLIST, CASE_NUM);
		List<String> pushToInventoryPopup = Arrays
				.asList(rstPickListData.get(CNPickList.ROW_VALUES).split(Constants.DELIMITER_TILD));
		List<String> hasLightspeed = Arrays
				.asList(rstPickListData.get(CNPickList.RECORDS).split(Constants.DELIMITER_TILD));
		String selectingOption = hasLightspeed.get(1);
		List<String> menuItem = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		try {

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.navigateToMenuItem(menuItem.get(0));

			// Changing the dropdown value of Has Lightspeed to 'No' on Org Summary Page
			pickList.selectingLightSpeed(hasLightspeed.get(1));

			// Navigating to Products>Picklist
			navigationBar.navigateToMenuItem(menuItem.get(1));

			// validating Picklist Manager Page
			foundation.waitforElement(PickList.PAGE_TITLE, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(PickList.PAGE_TITLE));

			// selecting required location on Picklist Manager Page
			foundation.click(pickList.selectLocationFromList(rstPickListData.get(CNPickList.LOCATIONS)));
			foundation.click(PickList.BTN_APPLY);
			foundation.waitforElement(pickList.objPickList(rstPickListData.get(CNPickList.LOCATIONS)),
					Constants.SHORT_TIME);
			foundation.click(pickList.objPickList(rstPickListData.get(CNPickList.LOCATIONS)));

			// Click on Push To Inventory button
			foundation.click(PickList.BTN_PUSH_TO_INVENTORY);

			// Verifying the details on confirm Popup for sending to Lightspeed
			foundation.waitforElement(PickList.BTN_YES, Constants.SHORT_TIME);
			CustomisedAssert.assertEquals(foundation.getText(PickList.TXT_SEND_PICKLIST), pushToInventoryPopup.get(0));
			CustomisedAssert.assertEquals(foundation.getText(PickList.TXT_CONFIRM_SENDING),
					pushToInventoryPopup.get(1));
			CustomisedAssert.assertEquals(foundation.getText(PickList.TXT_CONTINUE), pushToInventoryPopup.get(2));
			CustomisedAssert.assertEquals(foundation.getText(PickList.BTN_CANCEL), pushToInventoryPopup.get(3));
			CustomisedAssert.assertEquals(foundation.getText(PickList.BTN_YES), pushToInventoryPopup.get(4));

			// Select yes and push to Inventory
			foundation.click(PickList.BTN_YES);
			foundation.threadWait(Constants.MEDIUM_TIME);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		// Resetting the data
		finally {
			// Changing the dropdown value of Has Lightspeed to 'Yes' on Org Summary Page

			// Navigating to Super >Org Summary
			navigationBar.navigateToMenuItem(menuItem.get(0));
			pickList.selectingLightSpeed(hasLightspeed.get(0));
		}
	}

	/**
	 * @author afrosean Date: 15-07-2022
	 */
	@Test(description = "197507- ADM > Pick List Manager>Plan picklist>Verify Disabled Driver and Route are still displayed while adding product"
			+ "197506-ADM > Pick List Manager>Plan picklist>Verify Export file"
			+ "197706- ADM > Pick List Manager>Plan picklist>Verify Disabled Driver and Route are still displayed while Adding Product"
			+ "197505-ADM > Pick List Manager>Plan picklist>Verify Disabled Driver and Route are still displayed in Pick List screen")

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
			pickList.searchRouteAndClickOnActiveCheckbox(requiredData.get(0), requiredData.get(2), requiredData.get(3),
					"uncheck");

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
		
		
        } catch (Exception exc) {			
			TestInfra.failWithScreenShot(exc.toString());
		}
			finally {
				// Delete the product
				foundation.click(pickList.selectRoutes(requiredData.get(1), requiredData.get(4)));
				foundation.waitforElementToBeVisible(PickList.DELETE_BTN, 5);
				foundation.click(PickList.DELETE_BTN);
				CustomisedAssert.assertTrue(foundation.isDisplayed(PickList.BTN_FILTER_APPLY));	
				// Navigate to Admin-->Routes to enable the routes
				navigationBar.navigateToMenuItem(menu.get(0));
				pickList.searchRouteAndClickOnActiveCheckbox(requiredData.get(0), requiredData.get(2), requiredData.get(3),
						"check");
		
				// delete downloaded file
				foundation.deleteFile(FilePath.pickListFilePathWithDateAndDay(requiredData.get(7),
						rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION)));
		}
	}

	/**
	 * @author prabhanigam
	 * @Date -07/07/2022
	 */

	@Test(description = "196848-SOS-27313-ADM>Pick List Manager>Select  Location>Verify picklist manage columns with show or hide options")

	public void verifyManageColumnsOnFilteredPickList() {
		final String CASE_NUM = "196848";

		
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstPickListData = dataBase.getPickListData(Queries.PICKLIST, CASE_NUM);
		List<String> data =Arrays
					.asList( rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION).split(Constants.DELIMITER_TILD));
		List<String> manageColumn =Arrays
					.asList( rstPickListData.get(CNPickList.APLOCATION).split(Constants.DELIMITER_TILD));
		 


		try {
//			browser.navigateURL(
//					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
//			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
//					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.launchBrowserAsSuperAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			String menuItem = rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM);

			// Navigating to Products>Picklist, selecting any Location and clicking on
			// PlanPicklist
			navigationBar.navigateToMenuItem(menuItem);
			foundation.click(pickList.selectLocationFromList(rstPickListData.get(CNPickList.LOCATIONS)));
			foundation.click(PickList.BTN_APPLY);
			foundation.waitforElement(pickList.objPickList(rstPickListData.get(CNPickList.LOCATIONS)),
					Constants.SHORT_TIME);
			foundation.click(pickList.objPickList(rstPickListData.get(CNPickList.LOCATIONS)));
			foundation.click(PickList.BTN_PICKLIST_PLAN);
			foundation.waitforElement(PickList.LBL_SELECT_ALL, Constants.SHORT_TIME);

			// Taking list of UI headers of Filetered location page
			Map<String, String> uiTableHeaders = table.getTblHeadersPickListHistory(PickList.FILTERED_PICKLIST_TBL_ROW);
			List<String> uiListHeaders = new ArrayList<String>(uiTableHeaders.keySet());
			Collections.sort(uiListHeaders);
			System.out.println(uiListHeaders);
			System.out.println(data);
			CustomisedAssert.assertTrue(uiListHeaders.equals(data));

//			// Click on Manage Column button and verifying the headers present
			foundation.click(PickList.BTN_MANAGE_COLUMN);
			CustomisedAssert.assertTrue(foundation.isDisplayed(PickList.LIST_COLUMN_CHOOSER));
			List<String> columnChooser = foundation.getTextofListElement(PickList.LIST_COLUMN_CHOOSER);

		    CustomisedAssert.assertTrue(columnChooser.equals(manageColumn));

//			System.out.println(manageColumn);
//			System.out.println(columnChooser);
//			CustomisedAssert.assertTrue(columnChooser.contains(uiListHeaders));
//			CustomisedAssert.assertTrue(foundation.isDisplayed(PickList.TXT_COLUMN_CHOOSER));
//			CustomisedAssert.assertTrue(foundation.isDisplayed(PickList.BTN_CANCEL_COLUMN));
//			foundation.threadWait(Constants.THREE_SECOND);
//			foundation.click(PickList.BTN_CANCEL_COLUMN);

			// Click on Manage Column button and verifying the headers present
			CustomisedAssert.assertTrue(columnChooser.equals(manageColumn));

			foundation.scrollIntoViewElement(PickList.BTN_CANCEL_COLUMN);
			CustomisedAssert.assertTrue(foundation.isDisplayed(PickList.TXT_COLUMN_CHOOSER));
			CustomisedAssert.assertTrue(foundation.isDisplayed(PickList.BTN_CANCEL_COLUMN));
			foundation.scrollIntoViewElement(PickList.BTN_CANCEL_COLUMN);
//			foundation.threadWait(Constants.SHORT_TIME);
//			foundation.click(PickList.BTN_CANCEL_COLUMN);
//			
//			// Click on Remove button
//			foundation.click(pickList.objPickList(rstPickListData.get(CNPickList.LOCATIONS)));
//			foundation.click(PickList.BTN_REMOVE_PRODUCT);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}

	}

	/**
	 * @author afrosean Story SOS-26625
	 * @date: 04-07-2022
	 */
	@Test(description = "196144- ADM > Pick List Manager> Filter By Tab >Verify user can enters a product name to be applied to the filter"
			+ "196146-ADM > Pick List Manager> Filter By Tab > User selects to filter locations and products by UPC"
			+ "196149-ADM > Pick List Manager> Filter By Tab > User selects to filter locations and products by Product ID Range"
			+ "196145-ADM > Pick List Manager> Filter By Tab > User selects to filter locations and products by UPC Range"
			+ "196147-ADM > Pick List Manager> Filter By Tab >User selects to filter locations and products by Product ID")
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
	 * @author afrosean Date-22-07-2022
	 */
	@Test(description = "198393- ADM>Pick List Manager >Select location>Click 'Filter By'> Verify ' Plan Pick List(s)' Button"
			+ "198397-ADM>Pick List Manager >Select location>Click 'Filter By'>Click ' Plan Pick List(s)' Button>click'Add Product'button>verify Add product displayed on pick list"
			+ "198395-ADM>Pick List Manager >Select location>Click 'Filter By'>Click ' Plan Pick List(s)' Button>verify 'Refresh' button on plan picklist"
			+ "198396-ADM>Pick List Manager >Select location>Click 'Filter By'>Click ' Plan Pick List(s)' Button>verify 'Add Product' button pop up"
			+ "198394-ADM>Pick List Manager >Select location>Click 'Filter By'>Click ' Plan Pick List(s)' Button>verify 'manage Columns' pop up")
	public void verifyFilterByPlanPickListUIElementsAndPopUp() {
		final String CASE_NUM = "198393";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstPickListData = dataBase.getPickListData(Queries.PICKLIST, CASE_NUM);

		List<String> requiredOption = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION).split(Constants.DELIMITER_TILD));

		try {
			// Login to ADM
			navigationBar.launchBrowserAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Navigate to product-->pickList
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			pickList.selectLocationInFilterApplyAndClickOnFilterByTab(requiredOption.get(0));

			// verify close and preview button in Add product
			foundation.click(PickList.LBL_ADD_PRODUCT);
			foundation.waitforElementToBeVisible(PickList.LBL_TITLE_HEADER, 5);
			CustomisedAssert.assertTrue(foundation.isDisplayed(PickList.LBL_TITLE_HEADER));
			CustomisedAssert.assertTrue(foundation.isDisplayed(PickList.BTN_CLOSE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(PickList.LBL_PREVIEW));

			// verify cancel button
			foundation.waitforElementToBeVisible(PickList.BTN_CLOSE, 5);
			foundation.click(PickList.BTN_CLOSE);
			foundation.waitforElementToBeVisible(PickList.FILTER_PICKLIST, 5);

			// Search product and click on refresh
			pickList.searchProductAndClickOnRefresh(requiredOption.get(1), requiredOption.get(0));

			// Click on manage column and verify cancel and apply button
			pickList.clickOnManageColumnAndVerifyApplyAndCancel();

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}

	}

	/**
	 * @author sakthir Date: 01-09-2022
	 */
	@Test(description = "203683-ADM> Picklist> Able to select more than one product from the pick lists to export")
	public void verifySelectMoreProductAndExportFile() {
		try {
			final String CASE_NUM = "203683";

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstPickListData = dataBase.getPickListData(Queries.PICKLIST, CASE_NUM);
			String  menu = rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM);
			List<String>location = Arrays
					.asList(rstPickListData.get(CNPickList.LOCATIONS).split(Constants.DELIMITER_TILD));
			String  filename =rstPickListData.get(CNPickList.RECORDS);
			String  date =rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION);
			
			// Select Org & Menu
			navigationBar.launchBrowserAsSuperAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			
			//navigate to Product->PickList
			navigationBar.navigateToMenuItem(menu);
			pickList.selectLocationAndPicklistBtn(location.get(0));
			foundation.click(PickList.TBL_ROW_DATA);
			foundation.clickShiftAndDown();
			
			int value=foundation.getSizeofListElement(PickList.SELECTED_ROW);
		
			
			//Export Excel File
			foundation.click(PickList.EXPORT_BTN);
			foundation.threadWait(Constants.THREE_SECOND);
			CustomisedAssert.assertTrue(excel.isFileDownloaded(FilePath.
					pickListFilePathWithDateAndDay(filename,date)));	
			int excelCount = excel.getExcelRowCount(FilePath.
					pickListFilePathWithDateAndDay(filename,date));
		
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			foundation.deleteFile(FilePath.pickListFilePathWithDateAndDay(rstPickListData.get(CNPickList.RECORDS),
					rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION)));
		}
	}

	/**
	 * @author sakthir Date-15-09-2022
	 */
	@Test(description = "204681-SOS-33846-To Verify Location Column under Plan pick list Add products in pick list menu"
			+ "204682-To Verify Location Column in Add Product(s) to pick list grid while selecting any particular location under plan pick list")
	public void verifyLocationNameInLocationColumnForAddProductPickList() {
		final String CASE_NUM = "204681";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstPickListData = dataBase.getPickListData(Queries.PICKLIST, CASE_NUM);

		String menu = rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM);
		String location = rstPickListData.get(CNPickList.LOCATIONS);
		
		try {
			// Select Org & Menu
			navigationBar.launchBrowserAsSuperAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Navigate to Product->PickList and Select Plan pick list
			navigationBar.navigateToMenuItem(menu);
			CustomisedAssert.assertTrue(foundation.isDisplayed(PickList.PAGE_TITLE));
			pickList.selectLocationAndPicklistBtn(location);
			foundation.waitforElement(PickList.LBL_ADD_PRODUCT, Constants.SHORT_TIME);
			foundation.click(PickList.LBL_ADD_PRODUCT);

			// Verifying location name shows under location column
			foundation.waitforElement(PickList.LBL_ADD_PRODUCT_PICKLIST, 5);
			CustomisedAssert.assertTrue(foundation.isDisplayed(PickList.LBL_ADD_PRODUCT_PICKLIST));
			CustomisedAssert.assertTrue(foundation.getTextofListElement(PickList.TBL_ADD_PRODUCT).contains(location));
			foundation.threadWait(Constants.THREE_SECOND);
			CustomisedAssert.assertTrue(foundation.isDisplayed(PickList.LBL_DISABLE_PREVIEW));
			textBox.enterText(PickList.LBL_FILTER_TYPE, rstPickListData.get(CNPickList.PRODUCT_NAME));
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.waitforElement(pickList.objPickList(rstPickListData.get(CNPickList.PRODUCT_NAME)),
					Constants.SHORT_TIME);
			foundation.click(PickList.TBL_NEED);
			foundation.objectFocus(PickList.TXT_NEED);
			foundation.click(PickList.TXT_NEED);
			foundation.waitforElement(PickList.TXT_NEED, Constants.LONG_TIME);
			foundation.clearText();
			foundation.click(pickList.objPickList(rstPickListData.get(CNPickList.PRODUCT_NAME)));
			CustomisedAssert.assertTrue(foundation.getText(PickList.ERROR_TXT_NEED).equals(rstPickListData.get(CNPickList.NEED)));
			foundation.click(PickList.BTN_CLOSE);

			// select location in pick list page ,click Add product and Verifying location
			// name shows under location column
			foundation.waitforElementToBeVisible(PickList.TBL_ROW_DATA, 3);
			foundation.click(PickList.TBL_ROW_DATA);
			foundation.click(PickList.LBL_ADD_PRODUCT);
			foundation.waitforElement(PickList.LBL_ADD_PRODUCT_PICKLIST, 5);
			CustomisedAssert.assertTrue(foundation.isDisplayed(PickList.LBL_ADD_PRODUCT_PICKLIST));
			CustomisedAssert.assertTrue(foundation.getTextofListElement(PickList.TBL_ADD_PRODUCT).contains(location));
			foundation.click(PickList.BTN_CLOSE);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * @author sakthir Date-10-10-2022
	 */
	@Test(description = "206149-To Verify Default options as Set Plan vs Pick Preference in popup under Picklist, Scheduling"
			+ "206150-To Verify Dropdown options for Plan vs Pick/Service Day Preference(s) column under Picklist, Scheduling"
			+ "206151-To Verify Save button is enabled by changing the options in Plan vs Pick/Service Day Preference(s)and selecting the checkbox"
			+ "206154-To Verify save button is disable when Plan vs pick/Service Day Preference(s) column set as Default option with Checked checkbox")
	public void verifySchedulingUsingSetPlanvsPickPreferenceOptionsAndSaveButtonEnableAndDisable() {
		final String CASE_NUM = "206149";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstPickListData = dataBase.getPickListData(Queries.PICKLIST, CASE_NUM);

		String menu = rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM);
		String location = rstPickListData.get(CNPickList.LOCATIONS);
		List<String> day = Arrays.asList(rstPickListData.get(CNPickList.PRODUCT_NAME).split(Constants.DELIMITER_TILD));
		List<String> data = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION).split(Constants.DELIMITER_TILD));

		try {
			// Select Org & Menu
			navigationBar.launchBrowserAsSuperAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Navigate to Product->PickList and Select Plan pick list
			navigationBar.navigateToMenuItem(menu);
			CustomisedAssert.assertTrue(foundation.isDisplayed(PickList.PAGE_TITLE));
			foundation.click(PickList.BTN_SCHEDULING);

			// Navigate to route scheduling page and select location
			CustomisedAssert.assertTrue(foundation.isDisplayed(PickList.TXT_ROUTE_SCHEDULING));
			foundation.click(pickList.selectLocationFromList(location));
			foundation.scrollIntoViewElement(PickList.BTN_APPLY);
			foundation.click(PickList.BTN_APPLY);
			foundation.waitforElement(pickList.objPickList(location), Constants.SHORT_TIME);

			// click on Set Plan vs Pick Preference
			foundation.click(PickList.BTN_PLAN_SERVICE_DAY);
			foundation.waitforElementToBeVisible(PickList.LBL_PLAN_SERVICE_DAY, 3);
			CustomisedAssert.assertTrue(foundation.isDisplayed(PickList.LBL_PLAN_SERVICE_DAY));

//			// verify default options
//			pickList.verifyDefaultOption(day);

			// verify Dropdown Options
			pickList.verifyDPDOption(data, day);

			// verify Save button is disable
			pickList.clickCheckbox("true");
			pickList.verifyDefaultOption(day);
			CustomisedAssert.assertTrue(foundation.isDisplayed(PickList.BTN_SAVE_DISABLE_PLAN_SERVICEDAY));

			// verify Save button is enable by selecting dropdown options
			pickList.verifySaveButtonEnableAfterSelectingDPDOption(data.get(1), day, "true");

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} 
//		finally {
//			// resetting
//			foundation.refreshPage();
//			foundation.click(pickList.selectLocationFromList(location));
//			foundation.scrollIntoViewElement(PickList.BTN_APPLY);
//			foundation.click(PickList.BTN_APPLY);
//			foundation.waitforElement(pickList.objPickList(location), Constants.SHORT_TIME);
//			foundation.click(PickList.BTN_PLAN_SERVICE_DAY);
//			foundation.waitforElementToBeVisible(PickList.LBL_PLAN_SERVICE_DAY, 3);
//			CustomisedAssert.assertTrue(foundation.isDisplayed(PickList.LBL_PLAN_SERVICE_DAY));
//			pickList.clickCheckbox("false");
//			pickList.verifyDefaultOption(day);
//			foundation.click(PickList.BTN_SAVE_PLAN_SERVICEDAY);
//		}
	}

	/**
	 * @author sakthir Date-12-10-2022
	 */
	@Test(description = "206152-To Verify that the user is able to uncheck the scheduled plan and able to save it"
			+ "206153-To Verify that the user is able to check the scheduled plan and able to save it")
	public void verifySchedulingEditingCheckAndUncheckUsingSetPlanvsPickPreferencePopup() {
		final String CASE_NUM = "206152";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstPickListData = dataBase.getPickListData(Queries.PICKLIST, CASE_NUM);

		String menu = rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM);
		String location = rstPickListData.get(CNPickList.LOCATIONS);
		List<String> day = Arrays.asList(rstPickListData.get(CNPickList.PRODUCT_NAME).split(Constants.DELIMITER_TILD));
		List<String> data = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION).split(Constants.DELIMITER_TILD));

		try {
			// Select Org & Menu
			navigationBar.launchBrowserAsSuperAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Navigate to Product->PickList and Select Plan pick list
			navigationBar.navigateToMenuItem(menu);
			CustomisedAssert.assertTrue(foundation.isDisplayed(PickList.PAGE_TITLE));
			foundation.click(PickList.BTN_SCHEDULING);

			// Navigate to route scheduling page and select location
			CustomisedAssert.assertTrue(foundation.isDisplayed(PickList.TXT_ROUTE_SCHEDULING));
			foundation.click(pickList.selectLocationFromList(location));
			foundation.scrollIntoViewElement(PickList.BTN_APPLY);
			foundation.click(PickList.BTN_APPLY);
			foundation.waitforElement(pickList.objPickList(location), Constants.SHORT_TIME);

			// Enter all the data in route driver and date
			pickList.clickCheckbox("true");
			foundation.click(PickList.BTN_SAVE);

			// Click on Set Plan vs Pick Preference and verify all checkbox are checked with
			// default option
			foundation.click(PickList.BTN_PLAN_SERVICE_DAY);
			foundation.waitforElementToBeVisible(PickList.LBL_PLAN_SERVICE_DAY, 3);
			CustomisedAssert.assertTrue(foundation.isDisplayed(PickList.LBL_PLAN_SERVICE_DAY));
			for (int i = 0; i <= 6; i++) {
				String value = foundation.getAttribute(pickList.objDay(String.valueOf(i + 1)), "aria-checked");
				CustomisedAssert.assertEquals(value, "true");
			}

			// Verify that the user is able to uncheck the scheduled plan and able to save
			// it
			foundation.click(pickList.objCheckBoxPlanServiceDay(day.get(3)));
			foundation.click(pickList.objCheckBoxPlanServiceDay(day.get(6)));
			CustomisedAssert
					.assertTrue(foundation.getText(pickList.objDropdownPlanServiceDay(day.get(3))).equals(day.get(7)));
			CustomisedAssert
					.assertTrue(foundation.getText(pickList.objDropdownPlanServiceDay(day.get(6))).equals(day.get(7)));
			foundation.click(PickList.BTN_SAVE_PLAN_SERVICEDAY);
			foundation.refreshPage();

			// select location and click on Set Plan vs Pick Preference
			CustomisedAssert.assertTrue(foundation.isDisplayed(PickList.TXT_ROUTE_SCHEDULING));
			foundation.click(pickList.selectLocationFromList(location));
			foundation.scrollIntoViewElement(PickList.BTN_APPLY);
			foundation.click(PickList.BTN_APPLY);
			foundation.waitforElement(pickList.objPickList(location), Constants.SHORT_TIME);
			foundation.click(PickList.BTN_PLAN_SERVICE_DAY);
			foundation.waitforElementToBeVisible(PickList.LBL_PLAN_SERVICE_DAY, 3);
			CustomisedAssert.assertTrue(foundation.isDisplayed(PickList.LBL_PLAN_SERVICE_DAY));

			// Verify that the user is able to check the scheduled plan and able to save it
			foundation.waitforElementToBeVisible(pickList.objDropdownPlanServiceDay(data.get(3)), 3);
			foundation.doubleClick(pickList.objDropdownPlanServiceDay(day.get(3)));
			foundation.waitforElementToBeVisible(pickList.objOption(data.get(3)), 3);
			foundation.click(pickList.objOption(data.get(3)));
			foundation.click(pickList.objCheckBoxPlanServiceDay(day.get(3)));
			foundation.doubleClick(pickList.objDropdownPlanServiceDay(day.get(6)));
			foundation.waitforElementToBeVisible(pickList.objOption(data.get(4)), 3);
			foundation.click(pickList.objOption(data.get(4)));
			foundation.click(pickList.objCheckBoxPlanServiceDay(day.get(6)));
			foundation.click(PickList.BTN_SAVE_PLAN_SERVICEDAY);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// resetting
			foundation.refreshPage();
			CustomisedAssert.assertTrue(foundation.isDisplayed(PickList.TXT_ROUTE_SCHEDULING));
			foundation.click(pickList.selectLocationFromList(location));
			foundation.scrollIntoViewElement(PickList.BTN_APPLY);
			foundation.click(PickList.BTN_APPLY);
			foundation.waitforElement(pickList.objPickList(location), Constants.SHORT_TIME);
			pickList.clickCheckbox("false");
			foundation.click(PickList.BTN_SAVE);
		}
	}

	/**
	 * @author sakthir Date-20-10-2022
	 */
	@Test(description = "206355-SOS-13307:To verify Add Product pop-up hint(Record Count) displays correct number of products for the locations"
			+ "206356-SOS-13365-To verify Pick List Manager buttons display without location selection"
			+ "206357-SOS-31236-To verify recently added product display after refresh")
	public void verifyPopUpRecordCountAndAddedProductAfterRefreshAndButtonsOnPickListManager() {
		final String CASE_NUM = "206355";

		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstPickListData = dataBase.getPickListData(Queries.PICKLIST, CASE_NUM);

		String menu = rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM);
		List<String> product = Arrays
				.asList(rstPickListData.get(CNPickList.PRODUCT_NAME).split(Constants.DELIMITER_TILD));
		List<String> needcount = Arrays.asList(rstPickListData.get(CNPickList.NEED).split(Constants.DELIMITER_TILD));
		List<String> location = Arrays
				.asList(rstPickListData.get(CNPickList.LOCATIONS).split(Constants.DELIMITER_TILD));
		List<String> button = Arrays.asList(rstPickListData.get(CNPickList.APLOCATION).split(Constants.DELIMITER_TILD));

		try {
			// Select Org & Menu
			navigationBar.launchBrowserAsSuperAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Navigate to product->PickList
			navigationBar.navigateToMenuItem(menu);
			CustomisedAssert.assertTrue(foundation.isDisplayed(PickList.PAGE_TITLE));

			// selecting required location on Picklist Manager
			foundation.click(pickList.selectLocationFromList(location.get(0)));
			foundation.click(pickList.selectLocationFromList(location.get(1)));
			foundation.click(PickList.BTN_APPLY);
			foundation.waitforElement(pickList.objPickList(location.get(0)), Constants.SHORT_TIME);
			foundation.click(pickList.objPickList(location.get(0)));
			CustomisedAssert.assertTrue(foundation.getTextofListElement(PickList.LIST_PICKLIST_BUTTONS).equals(button));
			foundation.click(PickList.BTN_APPLY);
			foundation.waitforElement(pickList.objPickList(location.get(0)), Constants.SHORT_TIME);
			CustomisedAssert
					.assertFalse(foundation.getTextofListElement(PickList.LIST_PICKLIST_BUTTONS).equals(button));

			// click plan pick List button
			foundation.click(pickList.objPickList(location.get(1)));
			foundation.click(PickList.BTN_PICKLIST_PLAN);
			foundation.waitforElement(PickList.LBL_SELECT_ALL, Constants.SHORT_TIME);

			// click on add product and verify the Record count
			foundation.click(PickList.LBL_ADD_PRODUCT);
			foundation.threadWait(3);
			dropDown.selectItem(PickList.DRP_LOCATION, location.get(1), Constants.TEXT);
			foundation.waitforElementToBeVisible(PickList.RECORD_COUNT, 5);
			String s = foundation.getText(PickList.RECORD_COUNT);
			String[] str = s.split(" ");
			for (int i = 0; i < str.length; i++) {
				System.out.println(str[i]);
			}
			CustomisedAssert.assertTrue(str[1].equals(str[5]));

			// Update the need count and add the product
			pickList.addProductWithNeedCount(product.get(0), needcount.get(0));
			foundation.click(PickList.LBL_PREVIEW);
			foundation.click(PickList.LBL_Add);
			foundation.waitforElement(PickList.TXT_SPINNER_MSG, Constants.SHORT_TIME);

			// Verify product is added and column data is coming properly
			CustomisedAssert.assertTrue(foundation.getTextofListElement(PickList.TABLE_ROW).contains(product.get(0)));
			foundation.click(PickList.REFRESH_BTN);
			foundation.waitforElementToBeVisible(PickList.POPUP_HEADER, 3);
			CustomisedAssert.assertTrue(foundation.isDisplayed(PickList.POPUP_HEADER));
			foundation.click(PickList.BTN_YES);
			foundation.waitforElementToBeVisible(PickList.TABLE_ROW, 3);
			CustomisedAssert.assertTrue(foundation.getTextofListElement(PickList.TABLE_ROW).contains(product.get(0)));
			foundation.click(pickList.objPickList(product.get(0)));
			foundation.click(PickList.DELECT_ROW);
			foundation.threadWait(3);
			CustomisedAssert.assertFalse(foundation.getTextofListElement(PickList.TABLE_ROW).contains(product.get(0)));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}

	}
	
	/**
	 * @author sakthir Date:21-11-2022
	 */
	@Test(description = "208979-verify Filter location grid without selecting location"
			+"208980-verify clear button Popup label in pick list manager page"
			+"208981-verify clear popup 'x' button in pick list manager "
			+"208982-verify clear popup 'cancel' button in pick list manager page"
			+"208983-verify clear button Popup message in pick list manager page"
			+"209074-verify clear popup 'yes' button in pick list manager page"
			+"208996-verify Refresh button Popup label in picklist manager page"
			+"208997-verify Refresh popup 'x' button in picklist manager page"
			+"208998-verify Refresh popup 'cancel' button in picklist manager page"
			+"208999-verify Refresh button Popup message in picklist manager page"
			+"209087-verify Refresh popup 'yes' button in plan picklist manager page")
	public void verifyClearAndRefreshPopupFunctionalityWithoutSelectingLocationInFilterLocationGrid() {
		final String CASE_NUM = "208979";

		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);

		String menu = rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM);
		List<String> data = Arrays.asList(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION).split(Constants.DELIMITER_TILD));

		try {
			// Select Org & Menu
			navigationBar.launchBrowserAsSuperAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Navigate to product->PickList
			navigationBar.navigateToMenuItem(menu);
			CustomisedAssert.assertTrue(foundation.isDisplayed(PickList.PAGE_TITLE));
					
			//Verify the filtered location grid
			foundation.threadWait(5);
			CustomisedAssert.assertTrue(foundation.getText(PickList.FILTERLOCATION_GRID).equals(data.get(0)));
			
			//select location and click on clear
			foundation.click(PickList.BTN_SELECT_ALL);
			CustomisedAssert.assertTrue(foundation.getBGColor(PickList.VALIDATE_HIGHLIGHTED_LOCATIONS).equals(data.get(3)));

			//verify clear popup functionality
			pickList.clearPopupFunctionalityInFilteredLocation(data.get(1),data.get(2), data.get(3), data.get(4));
			
			//verify Refresh popup functionality
			pickList.refreshPopupFunctionalityInFilteredLocation(data.get(3),data.get(5), data.get(6));

		}
		    catch (Exception exc) {
				TestInfra.failWithScreenShot(exc.toString());
			}
	}
	
	/**
	 * @author sakthir Date-23-11-2022
	 */
	@Test(description ="208990-verify Refresh button Popup label in plan picklist page"
						+"208991-verify Refresh popup 'x' button in plan picklist page"
						+"208992-verify Refresh popup 'cancel' button in plan picklist page"
						+"209086-verify Refresh popup 'yes' button in plan picklist page"
						+"208993-verify Refresh button Popup message in plan picklist page"
						+"208986-verify clear button Popup label in plan picklist page"
						+"208987-verify clear popup 'x' button in plan picklist page"
						+"208988-verify clear popup 'cancel' button in plan picklist page"
						+"209085-verify clear popup 'yes' button in pick list page"
						+"208989-verify clear button Popup message in plan picklist page")
	public void verifyClearAndRefreshPopupFunctionalityInFilterPickListGrid() {
		final String CASE_NUM = "208990";

		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstPickListData = dataBase.getPickListData(Queries.PICKLIST, CASE_NUM);

		String menu = rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM);
		List<String> data = Arrays.asList(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION)
				.split(Constants.DELIMITER_TILD));
		List<String> location = Arrays
				.asList(rstPickListData.get(CNPickList.LOCATIONS).split(Constants.DELIMITER_TILD));

		try {
			// Select Org & Menu
			navigationBar.launchBrowserAsSuperAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Navigate to product->PickList and select location
			navigationBar.navigateToMenuItem(menu);
			CustomisedAssert.assertTrue(foundation.isDisplayed(PickList.PAGE_TITLE));
			foundation.click(pickList.selectLocationFromList(location.get(0)));
			foundation.click(pickList.selectLocationFromList(location.get(1)));
			foundation.click(pickList.selectLocationFromList(location.get(2)));
			foundation.click(PickList.BTN_APPLY);
				
			//select one location and click 'plan pick list' button
			foundation.click(pickList.objPickList(location.get(1)));
			foundation.click(PickList.BTN_PICKLIST_PLAN);
			CustomisedAssert.assertTrue(foundation.isDisplayed(PickList.LBL_PICK_LIST_TITLE));
									
			//select location and click on clear
			foundation.click(PickList.LBL_SELECT_ALL);
			CustomisedAssert.assertTrue(foundation.getBGColor(PickList.TBL_FILTERED_PICKLIST_GRID).equals(data.get(0)));
						
			//verify clear popup functionality
			pickList.clearPopupFunctionalityInFilteredPickList(data.get(4),data.get(5),data.get(0),data.get(1));

		    //verify refresh popup functionality
			pickList.refreshPopupFunctionalityInFilteredPickList(data.get(0),data.get(2),data.get(3),data.get(1));

			
		}catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
  }
	
	/**
	 * @author sakthir Date-24-11-2022
	 */
	@Test(description = "209005-Verify selected locations at the top right corner by selecting a Locations filtered location grid"
						+"209006-Verify selected locations at the top right corner by selecting more then one Locations filtered location grid"
						+"209007-Verify selected locations at the top right corner by without selecting a Locations filtered location grid"
						+"208984-verify 'Select All' button in Filtered location Grid"
						+"208985-verify 'Deselect All' button in Filtered location Grid"
						+"208994-verify 'Select All' button in filter picklist grid"
						+"208995-verify 'Deselect All' button in filter picklist grid"
						+"209083-verify Selected location display in filtered location grid")
	public void verifySelectAllAndDeselectAllLocationFunctionalityInFilteredLocationAndFilteredPickListGrid() {
		final String CASE_NUM = "209005";

		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstPickListData = dataBase.getPickListData(Queries.PICKLIST, CASE_NUM);

		String menu = rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM);
		List<String> data = Arrays.asList(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION)
				.split(Constants.DELIMITER_TILD));
		List<String> location = Arrays
				.asList(rstPickListData.get(CNPickList.LOCATIONS).split(Constants.DELIMITER_TILD));
		List<String> record =Arrays
				.asList(rstPickListData.get(CNPickList.RECORDS).split(Constants.DELIMITER_TILD));

		try {
			// Select Org & Menu
			navigationBar.launchBrowserAsSuperAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Navigate to product->PickList and select location
			navigationBar.navigateToMenuItem(menu);
			CustomisedAssert.assertTrue(foundation.isDisplayed(PickList.PAGE_TITLE));
			foundation.click(pickList.selectLocationFromList(location.get(0)));
			foundation.click(pickList.selectLocationFromList(location.get(1)));
			foundation.click(pickList.selectLocationFromList(location.get(2)));
			foundation.click(PickList.BTN_APPLY);
			
			//verify selected location on grid
			CustomisedAssert.assertTrue(foundation.getTextofListElement(PickList.TBL_FILTERED_LOCATION_GRID).equals(location));
			
			//verify Filtered location table header
			CustomisedAssert.assertTrue(foundation.getTextofListElement(PickList.TBL_HEADER).equals(record));
			
			//verify without selected location on top corner message
			CustomisedAssert.assertTrue(foundation.getText(PickList.SELECT_COUNT).equals(data.get(0)));
			
			//Select one location and verify the selected location on top corner message
			foundation.waitforElement(pickList.objPickList(location.get(0)), Constants.SHORT_TIME);
			foundation.click(pickList.objPickList(location.get(0)));
			CustomisedAssert.assertTrue(foundation.getText(PickList.SELECT_COUNT).equals(data.get(1)));
			
			//Select 'Select All' location and verify the selected location on top corner message
			foundation.click(PickList.BTN_SELECTALL);
			CustomisedAssert.assertTrue(foundation.isDisplayed(PickList.SELECT_LOCATION));
			CustomisedAssert.assertTrue(foundation.getText(PickList.SELECT_COUNT).equals(data.get(2)));
			
			//verify Deselect All functionality
			foundation.click(PickList.BTN_DESELECTALL);
			CustomisedAssert.assertTrue(foundation.isDisplayed(PickList.DESELECT_LOCATION));
			
			
			//select one location and click 'plan pick list' button
			foundation.click(pickList.objPickList(location.get(1)));
			foundation.click(PickList.BTN_PICKLIST_PLAN);
			CustomisedAssert.assertTrue(foundation.isDisplayed(PickList.LBL_PICK_LIST_TITLE));
						
			//verify select all button
			foundation.waitforElement(PickList.LBL_SELECT_ALL, Constants.THREE_SECOND);
			foundation.click(PickList.LBL_SELECT_ALL);
			CustomisedAssert.assertTrue(foundation.getBGColor(PickList.TBL_FILTERED_PICKLIST_GRID).equals(data.get(3)));
			
			//verify deselect all button
			foundation.click(PickList.LBL_DESELECT_ALL);
			CustomisedAssert.assertTrue(foundation.getBGColor(PickList.VALIDATE_HIGHLIGHTED).equals(data.get(4)));
									
			
			
		}catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
     }
	
		/**
		 * @author sakthir Date-25-11-2022
		 */
		@Test(description = "209008-Verify selected locations at the top right corner by selecting one Location and one product filtered pick list grid"
								+"209009-Verify selected locations at the top right corner by without selecting a Locations filtered pick list grid"
								+"209010-Verify selected locations at the top right corner by selecting same location with different product filtered pick list grid"
								+"209011-Verify selected locations at the top right corner by selecting different location with same product filtered pick list grid"
								+"209012-Verify selected locations at the top right corner by selecting different location and different product filtered pick list grid")
		public void verifySelectedLocationAtTheTopRightCornerInFilteredPickListGrid() {
				final String CASE_NUM = "209008";
			
				// Reading test data from DataBase
				rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
				rstPickListData = dataBase.getPickListData(Queries.PICKLIST, CASE_NUM);
			
				String menu = rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM);
				List<String> data = Arrays.asList(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION)
							.split(Constants.DELIMITER_TILD));
				List<String> location = Arrays
							.asList(rstPickListData.get(CNPickList.LOCATIONS).split(Constants.DELIMITER_TILD));
				List<String> product = Arrays
							.asList(rstPickListData.get(CNPickList.PRODUCT_NAME).split(Constants.DELIMITER_TILD));
			
				try {
					// Select Org & Menu
					navigationBar.launchBrowserAsSuperAndSelectOrg(
								propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
					CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));
			
					// Navigate to product->PickList and select location
					navigationBar.navigateToMenuItem(menu);
					CustomisedAssert.assertTrue(foundation.isDisplayed(PickList.PAGE_TITLE));
					foundation.click(pickList.selectLocationFromList(location.get(0)));
					foundation.click(pickList.selectLocationFromList(location.get(1)));
					foundation.click(PickList.BTN_APPLY);
					
					//select one location and click 'plan pick list' button
					foundation.waitforElementToBeVisible(PickList.BTN_SELECTALL, 5);
					foundation.click(PickList.BTN_SELECTALL);
					foundation.click(PickList.BTN_PICKLIST_PLAN);
					CustomisedAssert.assertTrue(foundation.isDisplayed(PickList.LBL_PICK_LIST_TITLE));
							
				    //verify without selected location on top corner message
					CustomisedAssert.assertTrue(foundation.getText(PickList.SELECT_COUNT).equals(data.get(0)));
					
					
					//Select '1 location and 1 Product' and verify the selected location on top corner message
					foundation.waitforElement(pickList.objPickList(location.get(0)), Constants.SHORT_TIME);
					foundation.click(pickList.objPickList(product.get(0)));
					CustomisedAssert.assertTrue(foundation.getText(PickList.SELECT_COUNT).equals(data.get(1)));
					
					//Select '2 location and 1 Product' and verify the selected location on top corner message
					foundation.threadWait(Constants.SHORT_TIME);
					pickList.selectProductToVerifySelectedMessage(product.get(2), data.get(2));
			
					//Select '1 location and 2 Product' and verify the selected location on top corner message
					foundation.threadWait(Constants.SHORT_TIME);
					pickList.selectProductToVerifySelectedMessage(product.get(4), data.get(4));
					
					//Select '2 location and 2 Product' and verify the selected location on top corner message
					foundation.threadWait(Constants.SHORT_TIME);
					pickList.selectProductToVerifySelectedMessage(product.get(6), data.get(3));
									
				}catch (Exception exc) {
					TestInfra.failWithScreenShot(exc.toString());
				}
     }	
		
	/**
	* @author sakthir Date-29-11-2022
	*/
	@Test(description = "209000-verify Show record dropdown on picklist manager grid"
		                    +"209001-verify Show record functionality on picklist manager grid"
							+"209002-verify Show record dropdown on picklist grid"
							+"209003-verify Show record functionality on picklist grid")
	public void verifyShowRecordFunctionalityInFilteredLocationAndPickListGrid() {
			final String CASE_NUM = "209000";
		
			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstPickListData = dataBase.getPickListData(Queries.PICKLIST, CASE_NUM);
	
			String menu = rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM);
			List<String> data = Arrays.asList(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION)
					.split(Constants.DELIMITER_TILD));
			List<String> location =Arrays
					.asList( rstPickListData.get(CNPickList.LOCATIONS).split(Constants.DELIMITER_TILD));
	
			try {
					// Select Org & Menu
					navigationBar.launchBrowserAsSuperAndSelectOrg(
							propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
					CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));
		
					// Navigate to product->PickList and select location
					navigationBar.navigateToMenuItem(menu);
					CustomisedAssert.assertTrue(foundation.isDisplayed(PickList.PAGE_TITLE));
					foundation.click(PickList.BTN_SELECT_ALL);
					foundation.click(PickList.BTN_APPLY);
					
					//verify show record and Dropdown options in filtered location grid
					foundation.scrollIntoViewElement(PickList.LBL_SHOW_RECORD);
					CustomisedAssert.assertTrue(foundation.isDisplayed(PickList.LBL_SHOW_RECORD));
					foundation.click(PickList.BTN_SHOW_RECORD);
					CustomisedAssert.assertTrue(foundation.getTextofListElement(PickList.DPD_SHOW_RECORD).equals(data));
					
					//select option and verify list in filtered location grid
					foundation.click(pickList.selectShowRecordCount(data.get(1)));
					CustomisedAssert.assertEquals(foundation.getSizeofListElement(PickList.TBL_ROW_DATA), 10);
					
					//select location and plan pick list button
					foundation.click(pickList.objPickList(location.get(0)));
					foundation.click(PickList.BTN_PICKLIST_PLAN);
					CustomisedAssert.assertTrue(foundation.isDisplayed(PickList.LBL_PICK_LIST_TITLE));
					
					//verify show record and Dropdown options in filtered location grid
					foundation.scrollIntoViewElement(PickList.LBL_SHOW_RECORD);
					CustomisedAssert.assertTrue(foundation.isDisplayed(PickList.LBL_SHOW_RECORD));
					foundation.click(PickList.BTN_SHOW_RECORD);
					CustomisedAssert.assertTrue(foundation.getTextofListElement(PickList.DPD_SHOW_RECORD_PICKLIST).equals(data));
					
					//select option and verify list in filtered location grid
					foundation.click(pickList.selectShowRecordCountInPickListPage(data.get(0)));;
					CustomisedAssert.assertEquals(foundation.getSizeofListElement(PickList.TBL_ROW_DATA),5);		
					
			}catch (Exception exc) {
				TestInfra.failWithScreenShot(exc.toString());
			}
	     }	
		
		/**
		 * @author sakthir Date-30-11-2022
		 */
		@Test(description = "209066-ADM > Pick List > verify DeSelect All Button Under Schedule Route"
		                    +"209067-ADM > Pick List > verify Clear Button Under Location Tab"
							+"209068-ADM > Pick List > verify Cancel Button"
							+"209126-ADM > Pick List > Schedule Location Tab search functionality"
							+"209072-ADM > Pick List > verify service Schedule header"
							+"209127-verify Selected location display in service schedule grid")
		public void verifySearchCancelClearAndSelectedButtonFunctionalityinSchedulePage() {
					final String CASE_NUM = "209066";
		
				// Reading test data from DataBase
				rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
				rstPickListData = dataBase.getPickListData(Queries.PICKLIST, CASE_NUM);
		
				String menu = rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM);
				List<String> data = Arrays.asList(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION)
						.split(Constants.DELIMITER_TILD));
				List<String> location =Arrays
						.asList( rstPickListData.get(CNPickList.LOCATIONS).split(Constants.DELIMITER_TILD));
				List<String> header =Arrays
						.asList(rstPickListData.get(CNPickList.PRODUCT_NAME).split(Constants.DELIMITER_TILD));

				try {
					// Select Org & Menu
					navigationBar.launchBrowserAsSuperAndSelectOrg(
							propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
					CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));
		
					// Navigate to product->PickList and select location
					navigationBar.navigateToMenuItem(menu);
					CustomisedAssert.assertTrue(foundation.isDisplayed(PickList.PAGE_TITLE));
				
					//click scheduling button
					foundation.click(PickList.BTN_SCHEDULING);
					CustomisedAssert.assertTrue(foundation.isDisplayed(PickList.TXT_ROUTE_SCHEDULING));
					
					//verify Select All in location tab
					foundation.click(PickList.BTN_SELECT_ALL);
					CustomisedAssert.assertTrue(foundation.getBGColor(PickList.VALIDATE_HIGHLIGHTED_LOCATIONS).equals(data.get(0)));
					
					//verify Deselect All in location tab
					foundation.click(PickList.TXT_DESELECT_ALL);
					CustomisedAssert.assertTrue(foundation.isDisplayed(PickList.VALIDATE_DESELECTED_LOCATION));
					
					//verify clear popup functionality
					foundation.click(PickList.BTN_SELECT_ALL);
					pickList.clearPopupFunctionalityInScheduling(data.get(1),data.get(2), data.get(0), data.get(3));
					
					//verify cancel button
					foundation.click(PickList.BTN_SCHEDULE_CANCEL);
					CustomisedAssert.assertTrue(foundation.isDisplayed(PickList.PAGE_TITLE));
										

					foundation.click(PickList.BTN_SCHEDULING);
					CustomisedAssert.assertTrue(foundation.isDisplayed(PickList.TXT_ROUTE_SCHEDULING));
					foundation.click(pickList.selectLocationFromList(location.get(0)));
					foundation.click(pickList.selectLocationFromList(location.get(1)));
					foundation.click(pickList.selectLocationFromList(location.get(2)));
					foundation.click(PickList.BTN_APPLY);
					
					//verify table header
					CustomisedAssert.assertTrue(foundation.getTextofListElement(PickList.TBL_HEADER).equals(header));
					
					//verify selected location on grid
					CustomisedAssert.assertTrue(foundation.getTextofListElement(PickList.TBL_SERVICE_SCHEDULE_GRID).equals(location));
					
					//verify search text in location tab
					textBox.enterText(PickList.TXT_SECHEDULE_SEARCH,location.get(0));
					CustomisedAssert.assertTrue(foundation.getText(PickList.LBL_LOCATION).equals(location.get(0)));
					foundation.clearText();
					foundation.threadWait(Constants.THREE_SECOND);
					textBox.enterText(PickList.TXT_SECHEDULE_SEARCH,location.get(1));
					CustomisedAssert.assertTrue(foundation.getText(PickList.LBL_LOCATION).equals(location.get(1)));
					foundation.clearText();
					
										
				}catch (Exception exc) {
					TestInfra.failWithScreenShot(exc.toString());
				}
		     }
			
		/**
		 * @author sakthir Date-02-11-2022
		 */
		@Test(description = "209073-ADM > Pick List > Schedule FilterBy Tab"
		                    +"209069-ADM > Pick List > verify Service Date Drop Down"
							+"209070-ADM > Pick List > verify Service Date Drop Down by selecting some location"
							+"209071-ADM > Pick List > verify Route and Driver Drop Down")
		public void verifyRouteScheduleFilterByTabFunctionality() {
					final String CASE_NUM = "209073";
		
				// Reading test data from DataBase
				rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
				rstPickListData = dataBase.getPickListData(Queries.PICKLIST, CASE_NUM);
		
				String menu = rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM);
				List<String> location =Arrays
						.asList( rstPickListData.get(CNPickList.LOCATIONS).split(Constants.DELIMITER_TILD));
				List<String> record =Arrays
						.asList(rstPickListData.get(CNPickList.PRODUCT_NAME).split(Constants.DELIMITER_TILD));

				try {
					// Select Org & Menu
					navigationBar.launchBrowserAsSuperAndSelectOrg(
							propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
					CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));
		
					// Navigate to product->PickList and select location
					navigationBar.navigateToMenuItem(menu);
					CustomisedAssert.assertTrue(foundation.isDisplayed(PickList.PAGE_TITLE));
				
					//click scheduling button
					foundation.click(PickList.BTN_SCHEDULING);
					CustomisedAssert.assertTrue(foundation.isDisplayed(PickList.TXT_ROUTE_SCHEDULING));
					
	                //select location and click filter tab
					foundation.click(pickList.selectLocationFromList(location.get(0)));
					foundation.click(pickList.selectLocationFromList(location.get(1)));
					foundation.click(PickList.CLICK_FILTER_TAB);
					dropDown.selectItem(PickList.DPD_SERVICE_DAYS,record.get(0),Constants.TEXT);
					foundation.click(PickList.BTN_APPLY_FILTERBY_TAB);
					
					//verify the selected date on grid
					CustomisedAssert.assertTrue(foundation.isDisplayed(PickList.TXT_SERVICE_DAY_MONDAY));
					foundation.click(PickList.REMOVE_SELECTED_DAY);
										
					//select Driver and Route and verify in table
					dropDown.selectItem(PickList.DRP_ROUTE_DRIVER, record.get(2), Constants.TEXT);
                    dropDown.selectItem(PickList.DRP_ROUTE,record.get(1),Constants.TEXT);
					foundation.click(PickList.BTN_APPLY_FILTERBY_TAB);
					foundation.threadWait(Constants.SHORT_TIME);
					CustomisedAssert.assertTrue(foundation.getText(PickList.TBL_ROW_DATA).contains(record.get(1)));
					CustomisedAssert.assertTrue(foundation.getText(PickList.TBL_ROW_DATA).contains(record.get(2)));
										
				}catch (Exception exc) {
					TestInfra.failWithScreenShot(exc.toString());
				}
		     }
}