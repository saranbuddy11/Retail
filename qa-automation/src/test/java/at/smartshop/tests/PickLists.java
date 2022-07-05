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
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.Table;
import at.framework.ui.TextBox;
import at.smartshop.database.columns.CNNavigationMenu;
import at.smartshop.database.columns.CNPickList;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.pages.GlobalProductChange;
import at.smartshop.pages.LocationList;
import at.smartshop.pages.LocationSummary;
import at.smartshop.pages.NavigationBar;
import at.smartshop.pages.PickList;

@Listeners(at.framework.reportsetup.Listeners.class)
public class PickLists extends TestInfra {
	private static final String formate = null;
	private static final String filename = null;
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
	private GlobalProductChange globalProductChange = new GlobalProductChange();

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
	
	//SOS - 29233 
	
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
			
			//selecting select all location on Picklist Manager 
			foundation.click(PickList.BTN_SELECT_ALL);
			foundation.click(PickList.BTN_APPLY);
			foundation.waitforElement(PickList.LBL_SELECT_ALL, Constants.SHORT_TIME);
			foundation.click(PickList.LBL_SELECT_ALL);
			foundation.click(PickList.BTN_PICKLIST_PLAN);
			foundation.waitforElement(PickList.LBL_ADD_PRODUCT, Constants.SHORT_TIME);
			foundation.click(PickList.LBL_ADD_PRODUCT);
			foundation.waitforElement(PickList.LBL_ADD_PRODUCT_PICKLIST, Constants.SHORT_TIME);
			
			//verifying Location Dropdown is present or not
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
	//SOS - 25316 
	
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
				
				//selecting required location on Picklist Manager 
				foundation.click(pickList.selectLocationFromList(rstPickListData.get(CNPickList.LOCATIONS)));
				foundation.click(PickList.BTN_APPLY);
				foundation.waitforElement(pickList.objPickList(rstPickListData.get(CNPickList.LOCATIONS)),
						Constants.SHORT_TIME);
				foundation.click(pickList.objPickList(rstPickListData.get(CNPickList.LOCATIONS)));
				foundation.click(PickList.BTN_PICKLIST_PLAN);
				foundation.waitforElement(PickList.LBL_SELECT_ALL, Constants.SHORT_TIME);
				
				//click on add product
				foundation.click(PickList.LBL_ADD_PRODUCT);
				foundation.waitforElement(PickList.LBL_ADD_PRODUCT_PICKLIST, Constants.SHORT_TIME);
				textBox.enterText(PickList.LBL_FILTER_TYPE, rstPickListData.get(CNPickList.PRODUCT_NAME));
				foundation.threadWait(Constants.SHORT_TIME);
				foundation.waitforElement(pickList.objPickList(rstPickListData.get(CNPickList.PRODUCT_NAME)),
						Constants.SHORT_TIME);
				
				//Update the need count and add the product
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
		
		//SOS - 22442		
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
				
				//click on create new and check for dropdown values  
				foundation.click(PickList.BTN_CREATE_NEW_ROUTE);
				List<String> routeValues = dropDown.getAllItems(PickList.DRP_ROUTE_DRIVER);
				routeValues.set(0, "Default");
				Collections.sort(routeValues);
				
				//navigate to Product > Picklist 
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
		
		//SOS- 14363
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
				
				//validating Picklist Manager Page
				CustomisedAssert.assertTrue(foundation.isDisplayed(PickList.PAGE_TITLE));
				
				//selecting required location on Picklist Manager Page
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
			}finally {
				foundation.click(PickList.BTN_CANCEL_ORDER);
				foundation.threadWait(Constants.SHORT_TIME);
				foundation.click(PickList.SELECT_ORDER_TAB);
				foundation.click(PickList.BTN_CONFIRM_CANCEL_ORDER);
			}
		}
		
	//SOS - 11502
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
				
				//verifying UI headers is same as on excel data
				Map<String, String> uidata = table.getTblSingleRowRecordUI(LocationSummary.TBL_PRODUCTS,
						LocationSummary.TBL_PRODUCTS_HEADER);
				List<String> uiListHeaders = new ArrayList<String>(uidata.keySet());				
				CustomisedAssert.assertTrue(excel.verifyExcelData(uiListHeaders, FilePath.EXCEL_LOCAL_PROD, 0));
				
			} catch (Exception exc) {
				TestInfra.failWithScreenShot(exc.toString());
			}
			finally {
				foundation.deleteFile(FilePath.EXCEL_LOCAL_PROD);
			}
		}
		//SOS - 1669
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
				//foundation.click(LocationSummary.BTN_SAVE);
				foundation.refreshPage();
				//locationList.selectLocationName(location);
				foundation.click(LocationSummary.TAB_PRODUCTS);
				foundation.threadWait(Constants.ONE_SECOND);
				textBox.enterText(LocationSummary.TXT_SEARCH, product);
				foundation.waitforElement(locationSummary.objProductPrice(product), Constants.SHORT_TIME);
				CustomisedAssert.assertTrue(foundation.getText(pickList.verifyPickListColumn(product)).equals(PickListValue.get(0)));						
				
				//reset the value			
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
                
				//Verifying the Table Headers 
				Map<String, String> uiTableHeaders =table.getTblHeadersPickListHistory(PickList.TBL_ROW_HEADERS);
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
				
				//selecting select all location on Picklist Manager and verifying all products are highlighted
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
				
				//validating Picklist Manager Page
				CustomisedAssert.assertTrue(foundation.isDisplayed(PickList.PAGE_TITLE));
				
				//selecting required location on Picklist Manager Page
				foundation.click(pickList.selectLocationFromList(rstPickListData.get(CNPickList.LOCATIONS)));
				foundation.click(PickList.BTN_APPLY);
				foundation.waitforElement(pickList.objPickList(rstPickListData.get(CNPickList.LOCATIONS)),
						Constants.SHORT_TIME);
				foundation.click(pickList.objPickList(rstPickListData.get(CNPickList.LOCATIONS)));
				foundation.click(PickList.BTN_EXPORT);
				foundation.threadWait(Constants.THREE_SECOND);
				CustomisedAssert.assertTrue(excel.isFileDownloaded(FilePath.pickListFilePathWithDateAndDay(rstPickListData.get(CNPickList.RECORDS),
						rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION))));					
				
			} catch (Exception exc) {
				TestInfra.failWithScreenShot(exc.toString());
			}
			finally {
				foundation.deleteFile(FilePath.pickListFilePathWithDateAndDay(rstPickListData.get(CNPickList.RECORDS),
						rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION)));
			}
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
}
