package at.smartshop.tests;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import at.framework.database.mssql.Queries;
import at.framework.database.mssql.ResultSets;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.Table;
import at.framework.ui.TextBox;
import at.smartshop.database.columns.CNDeviceList;
import at.smartshop.database.columns.CNLocationList;
import at.smartshop.database.columns.CNLocationSummary;
import at.smartshop.database.columns.CNNavigationMenu;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.pages.GlobalProduct;
import at.smartshop.pages.LocationList;
import at.smartshop.pages.LocationSummary;
import at.smartshop.pages.NavigationBar;
import at.smartshop.pages.ProductSummary;

@Listeners(at.framework.reportsetup.Listeners.class)
public class Location extends TestInfra {
	private ResultSets dataBase = new ResultSets();
	private NavigationBar navigationBar = new NavigationBar();
	private GlobalProduct globalProduct = new GlobalProduct();
	private TextBox textBox = new TextBox();
	private Foundation foundation = new Foundation();
	private Table table=new Table();
	private LocationList locationList = new LocationList();
	private Dropdown dropdown = new Dropdown();
	
	private Map<String, String> rstNavigationMenuData;
	private Map<String, String> rstDeviceListData;
	private Map<String, String> rstLocationListData;
	private Map<String, String> rstLocationSummaryData;
	
	@Test(description = "114280- This test validates Extend Product")
	public void extendProducts() {
		try {			
			final String CASE_NUM = "114280";
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL,FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER,FilePath.PROPERTY_CONFIG_FILE), propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD,FilePath.PROPERTY_CONFIG_FILE));
			
			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstDeviceListData = dataBase.getDeviceListData(Queries.DEVICE_LIST, CASE_NUM);
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
			
			
			// Select Menu and Menu Item
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.CURRENT_ORG,FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Searching for Product
			textBox.enterText(GlobalProduct.TXT_FILTER, rstDeviceListData.get(CNDeviceList.PRODUCT_NAME));
			globalProduct.selectGlobalProduct(rstDeviceListData.get(CNDeviceList.PRODUCT_NAME));
			
			foundation.click(ProductSummary.BTN_EXTEND);

			// Extend product to location
			textBox.enterText(ProductSummary.TXT_FILTER, rstLocationListData.get(CNLocationList.LOCATION_NAME));
			table.selectRow(Constants.PRODUCT_DATAGRID, rstLocationListData.get(CNLocationList.LOCATION_NAME));

			foundation.click(ProductSummary.BTN_SAVE);

			// Searching for Product and Validating the Location Name
			textBox.enterText(ProductSummary.TXT_SEARCH, rstLocationListData.get(CNLocationList.LOCATION_NAME));
			Assert.assertTrue((foundation.getText(ProductSummary.TBL_DATA))
					.equals(rstLocationListData.get(CNLocationList.LOCATION_NAME)));

			// Resetting test data
			foundation.click(ProductSummary.TBL_DATA);
			foundation.waitforElement(ProductSummary.BTN_REMOVE, 10000);
			foundation.click(ProductSummary.BTN_REMOVE);
			foundation.waitforElement(ProductSummary.TXT_SEARCH, 10000);
			
		} catch (Exception exc) {
			Assert.fail();
		}
	}
	
	@Test(description = "This test to Verify Products are removed from the Location when location is disabled")
	public void RemoveProductFromLocation() {
		try {			
			final String CASE_NUM = "110650";
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL,FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER,FilePath.PROPERTY_CONFIG_FILE), propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD,FilePath.PROPERTY_CONFIG_FILE));
			
			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
			rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);
			
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.CURRENT_ORG,FilePath.PROPERTY_CONFIG_FILE));
			
			// Searching for Product
			textBox.enterText(LocationList.TXT_FILTER, rstLocationListData.get(CNLocationList.LOCATION_NAME));
			locationList.selectLocaionName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			
			List<String> locationDisabled = Arrays.asList(rstLocationSummaryData.get(CNLocationSummary.LOCATION_DISABLED).split(Constants.DELIMITER_TILD));
			dropdown.selectItem(LocationSummary.DPD_DISABLED, locationDisabled.get(0), Constants.TEXT);
			
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.click(LocationSummary.POP_UP_BTN_SAVE);
			
			List<String> dropDownList = Arrays
					.asList(rstLocationListData.get(CNLocationList.DROPDOWN_LOCATION_LIST).split(Constants.DELIMITER_TILD));
			dropdown.selectItem(LocationList.DPD_LOCATION_LIST,dropDownList.get(1),Constants.TEXT);
			
			// Searching for Product
			textBox.enterText(LocationList.TXT_FILTER, rstLocationListData.get(CNLocationList.LOCATION_NAME));
			locationList.selectLocaionName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			
			foundation.click(LocationSummary.TBL_PRODUCTS);
			
			Assert.assertTrue(table.getTblRowCount(LocationSummary.TBL_PRODUCTS_LIST)<= 0);	
			
			dropdown.selectItem(LocationSummary.DPD_DISABLED, locationDisabled.get(1), Constants.TEXT);
			
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.DPD_LOCATION_LIST, 2000);
			
		} catch (Exception exc) {
			Assert.fail();
		}

	}
}
