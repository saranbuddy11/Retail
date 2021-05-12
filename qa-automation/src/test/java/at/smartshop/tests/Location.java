package at.smartshop.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import at.framework.database.mssql.Queries;
import at.framework.database.mssql.ResultSets;
import at.framework.ui.Foundation;
import at.framework.ui.Table;
import at.framework.ui.TextBox;
import at.smartshop.database.columns.CNDeviceList;
import at.smartshop.database.columns.CNLocationList;
import at.smartshop.database.columns.CNNavigationMenu;
import at.smartshop.keys.Constants;
import at.smartshop.keys.KeysConfiguration;
import at.smartshop.pages.GlobalProduct;
import at.smartshop.pages.NavigationBar;
import at.smartshop.pages.ProductSummary;
import at.smartshop.testData.TestDataFilesPaths;

@Listeners(at.framework.reportsSetup.Listeners.class)
public class Location extends TestInfra {
	private ResultSets dataBase = new ResultSets();
	private NavigationBar navigationBar = new NavigationBar();
	private GlobalProduct globalProduct = new GlobalProduct();
	private TextBox textBox = new TextBox();
	private Foundation foundation = new Foundation();
	private Table table=new Table();
	
	private Map<String, String> rstNavigationMenuData;
	private Map<String, String> rstDeviceListData;
	private Map<String, String> rstLocationListData;
	
	@Test(description = "114280- This test validates Extend Product")
	public void extendProducts() {
		try {			
			final String CASE_NUM = "114280";
			browser.navigateURL(propertyFile.readPropertyFile(KeysConfiguration.CURRENT_URL,TestDataFilesPaths.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(KeysConfiguration.CURRENT_USER,TestDataFilesPaths.PROPERTY_CONFIG_FILE), propertyFile.readPropertyFile(KeysConfiguration.CURRENT_PASSWORD,TestDataFilesPaths.PROPERTY_CONFIG_FILE));
			
			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstDeviceListData = dataBase.getDeviceListData(Queries.DEVICE_LIST, CASE_NUM);
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
			
			
			// Select Menu and Menu Item
			navigationBar.selectOrginazation(propertyFile.readPropertyFile(KeysConfiguration.CURRENT_ORG,TestDataFilesPaths.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(NavigationBar.MNU_PRODUCT, rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

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
			Assert.assertTrue((textBox.getText(ProductSummary.TBL_DATA))
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
}
