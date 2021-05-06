package at.smartshop.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import at.framework.database.DBConnections;
import at.framework.database.DataBase;
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
	private DataBase dataBase = new DataBase();
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
			browser.navigateURL(propertyFile.readConfig(KeysConfiguration.CURRENT_URL,TestDataFilesPaths.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readConfig(KeysConfiguration.CURRENT_USER,TestDataFilesPaths.PROPERTY_CONFIG_FILE), propertyFile.readConfig(KeysConfiguration.CURRENT_PASSWORD,TestDataFilesPaths.PROPERTY_CONFIG_FILE));
			
			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(DBConnections.NAVIGATION_MENU, CASE_NUM);
			rstDeviceListData = dataBase.getDeviceListData(DBConnections.DEVICE_LIST, CASE_NUM);
			rstLocationListData = dataBase.getLocationListData(DBConnections.LOCATION_LIST, CASE_NUM);
			
			
			// Select Menu and Menu Item
			navigationBar.selectOrginazation(propertyFile.readConfig(KeysConfiguration.CURRENT_ORG,TestDataFilesPaths.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(NavigationBar.mnuProduct, rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Searching for Product
			textBox.enterText(GlobalProduct.txtFilter, rstDeviceListData.get(CNDeviceList.PRODUCT_NAME));
			globalProduct.selectGlobalProduct(rstDeviceListData.get(CNDeviceList.PRODUCT_NAME));
			
			foundation.click(ProductSummary.btnExtend);

			// Extend product to location
			textBox.enterText(ProductSummary.txtFilter, rstLocationListData.get(CNLocationList.LOCATION_NAME));
			table.selectRow(Constants.PRODUCT_DATAGRID, rstLocationListData.get(CNLocationList.LOCATION_NAME));

			foundation.click(ProductSummary.btnSave);

			// Searching for Product and Validating the Location Name
			textBox.enterText(ProductSummary.txtSearch, rstLocationListData.get(CNLocationList.LOCATION_NAME));
			Assert.assertTrue((textBox.getText(ProductSummary.tblData))
					.equals(rstLocationListData.get(CNLocationList.LOCATION_NAME)));

			// Resetting test data
			foundation.click(ProductSummary.tblData);
			foundation.waitforElement(ProductSummary.btnRemove, 10000);
			foundation.click(ProductSummary.btnRemove);
			foundation.waitforElement(ProductSummary.txtSearch, 10000);
			
		} catch (Exception exc) {
			Assert.fail();
		}

	}
}
