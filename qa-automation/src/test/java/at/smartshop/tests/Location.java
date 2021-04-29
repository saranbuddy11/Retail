package at.smartshop.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.annotations.Listeners;

import at.framework.database.DBConnections;
import at.framework.ui.Foundation;
import at.framework.ui.Table;
import at.framework.ui.TextBox;

import at.smartshop.pages.*;
import at.smartshop.testData.TestDataFilesPaths;
import at.smartshop.utilities.DataBase;
import at.smartshop.database.columns.CNDeviceList;
import at.smartshop.database.columns.CNLocationList;
import at.smartshop.database.columns.CNNavigationMenu;
import at.smartshop.keys.Constants;
import at.smartshop.keys.KeysConfiguration;

@Listeners(at.framework.reports.Listeners.class)
public class Location extends TestInfra {
	DataBase db = new DataBase();
	DBConnections dBConnections = new DBConnections();
	NavigationBar navigationBar = new NavigationBar();
	GlobalProduct globalProduct = new GlobalProduct();
	TextBox textBox = new TextBox();
	ProductSummary productSummary = new ProductSummary();
	Foundation foundation = new Foundation();
	Table table=new Table();
	
	private Map<String, String> rstNavigationMenuData;
	private Map<String, String> rstDeviceListData;
	private Map<String, String> rstLocationListData;
	
	@Test(description = "This test validates Extend Product")
	public void ExtendProducts() {
		try {			
			final String CASE_NUM = "114280";
			browser.navigateURL(propertyFile.readConfig(KeysConfiguration.CURRENT_URL,TestDataFilesPaths.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readConfig(KeysConfiguration.CURRENT_USER,TestDataFilesPaths.PROPERTY_CONFIG_FILE), propertyFile.readConfig(KeysConfiguration.CURRENT_PASSWORD,TestDataFilesPaths.PROPERTY_CONFIG_FILE));
			
			// Reading test data from DataBase
			rstNavigationMenuData = db.getNavigationMenuData(dBConnections.navigationMenu, CASE_NUM);
			rstDeviceListData = db.getDeviceListData(dBConnections.devicelist, CASE_NUM);
			rstLocationListData = db.getLocationListData(dBConnections.locationlist, CASE_NUM);
			
			// Select Menu and Menu Item
			navigationBar.selectOrginazation(propertyFile.readConfig(KeysConfiguration.CURRENT_ORG,TestDataFilesPaths.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(navigationBar.mnuProduct, rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Searching for Product
			textBox.enterText(globalProduct.txtFilter, rstDeviceListData.get(CNDeviceList.PRODUCT_NAME));
			globalProduct.selectGlobalProduct(rstDeviceListData.get(CNDeviceList.PRODUCT_NAME));
			
			foundation.click(productSummary.btnExtend);

			// Extend product to location
			textBox.enterText(productSummary.txtFilter, rstLocationListData.get(CNLocationList.LOCATION_NAME));
			table.selectRow(Constants.PRODUCT_DATAGRID, rstLocationListData.get(CNLocationList.LOCATION_NAME));

			foundation.click(productSummary.btnSave);

			// Searching for Product and Validating the Location Name
			textBox.enterText(productSummary.txtSearch, rstLocationListData.get(CNLocationList.LOCATION_NAME));
			Assert.assertTrue((textBox.getText(productSummary.tblData))
					.equals(rstLocationListData.get(CNLocationList.LOCATION_NAME)));

			// Resetting test data
			foundation.click(productSummary.tblData);
			foundation.waitforElement(productSummary.btnRemove, 10000);
			foundation.click(productSummary.btnRemove);

		} catch (Exception exc) {
			Assert.fail();
		}

	}
}
