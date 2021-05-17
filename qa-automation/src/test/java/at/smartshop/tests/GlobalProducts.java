package at.smartshop.tests;

import static org.testng.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.annotations.Listeners;

import at.framework.database.mssql.Queries;
import at.framework.database.mssql.ResultSets;
import at.framework.ui.Foundation;
import at.framework.ui.Table;
import at.framework.ui.TextBox;

import at.smartshop.pages.*;
import at.smartshop.database.columns.CNGlobalProductChange;
import at.smartshop.database.columns.CNLocationList;
import at.smartshop.database.columns.CNLocationSummary;
import at.smartshop.database.columns.CNNavigationMenu;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.keys.Configuration;

@Listeners(at.framework.reportsetup.Listeners.class)
public class GlobalProducts extends TestInfra {
	private NavigationBar navigationBar = new NavigationBar();
	private GlobalProduct globalProduct = new GlobalProduct();
	private TextBox textBox = new TextBox();
	private ProductSummary productSummary = new ProductSummary();
	private Foundation foundation = new Foundation();
	private ResultSets dataBase = new ResultSets();
	private LocationList locationList = new LocationList();
	private Table table = new Table();
	private GlobalProductChange globalProductChange=new GlobalProductChange();
	
	private Map<String, String> rstNavigationMenuData;
	private Map<String, String> rstGlobalProductChangeData;
	private Map<String, String> rstLocationSummaryData;
	private Map<String, String> rstLocationListData;

	@Test(description = "This test to Increment Price value for a product in Global Product Change for Location(s)")
	public void IncrementPriceForProductInGPCLocation() {
		try {			
			final String CASE_NUM = "110985";
			double price = 0.00;
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL,FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER,FilePath.PROPERTY_CONFIG_FILE), propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD,FilePath.PROPERTY_CONFIG_FILE));
			
			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstGlobalProductChangeData = dataBase.getGlobalProductChangeData(Queries.GLOBAL_PRODUCT_CHANGE, CASE_NUM);
			
			// Select Menu and Menu Item
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.CURRENT_ORG,FilePath.PROPERTY_CONFIG_FILE));
			List<String> menuItem = Arrays
					.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
			navigationBar.navigateToMenuItem(menuItem.get(1));
			
			// Searching for Product
			String product= rstGlobalProductChangeData.get(CNGlobalProductChange.PRODUCT_NAME);
			textBox.enterText(GlobalProduct.TXT_FILTER, product);
			globalProduct.selectGlobalProduct(rstGlobalProductChangeData.get(CNGlobalProductChange.PRODUCT_NAME));
			
			textBox.enterText(ProductSummary.TXT_LOCATION_SEARCH_FILTER, rstGlobalProductChangeData.get(CNGlobalProductChange.LOCATION_NAME));
			Map<String, String> productsRecord=productSummary.getProductsRecords(rstGlobalProductChangeData.get(CNGlobalProductChange.LOCATION_NAME));
			price=Double.parseDouble(productsRecord.get("Price"));
			navigationBar.navigateToMenuItem(menuItem.get(0));
			
			globalProductChange.selectLocation(rstGlobalProductChangeData.get(CNGlobalProductChange.LOCATION_NAME));
			foundation.click(GlobalProductChange.TAB_PRODUCT);
			textBox.enterText(GlobalProductChange.TXT_PRODUCT_NAME, product);
			foundation.click(GlobalProductChange.BTN_PRODUCT_APPLY);			
			       
	        foundation.click(globalProductChange.selectTableRow(rstGlobalProductChangeData.get(CNGlobalProductChange.LOCATION_NAME)));
	        
			foundation.click(GlobalProductChange.BTN_NEXT);
			
			//increment the price			
			foundation.click(GlobalProductChange.BTN_INCREMENT);
			String priceText=foundation.getText(GlobalProductChange.LBL_PRICE);
			String minText=foundation.getText(GlobalProductChange.LBL_MIN);
			String maxText=foundation.getText(GlobalProductChange.LBL_MAX);
			
			//Validate the fields
			List<String> lblPrice = Arrays.asList(rstGlobalProductChangeData.get(CNGlobalProductChange.INCREMENT_LABEL).split(Constants.DELIMITER_TILD));
			assertEquals(priceText, lblPrice.get(0));
			assertEquals(minText, lblPrice.get(1));
			assertEquals(maxText, lblPrice.get(2));
			
			double Incrementprice =Double.parseDouble(rstGlobalProductChangeData.get(CNGlobalProductChange.INCREMENT_PRICE));
			textBox.enterText(GlobalProductChange.TXT_PRICE, Double.toString(Incrementprice));
			
			
			foundation.click(GlobalProductChange.BTN_SUBMIT);
	        foundation.click(GlobalProductChange.BTN_OK);
	        foundation.isDisplayed(GlobalProductChange.MSG_SUCCESS);
	       
	        // Select Menu and Global product	       
	        navigationBar.navigateToMenuItem(menuItem.get(1));
	        
	       
	        // Search and select product
	        textBox.enterText(LocationList.TXT_FILTER, product);
	        globalProduct.selectGlobalProduct(product);
	        textBox.enterText(ProductSummary.TXT_LOCATION_SEARCH_FILTER, rstGlobalProductChangeData.get(CNGlobalProductChange.LOCATION_NAME));
	        double updatedPrice = price+Incrementprice;
	        
	        Map<String, String> updatedProductsRecord=productSummary.getProductsRecords(rstGlobalProductChangeData.get(CNGlobalProductChange.LOCATION_NAME));
    	    
			assertEquals(Double.parseDouble(updatedProductsRecord.get("Price")), updatedPrice);
			
		} catch (Exception exc) {
			exc.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test(description = "This test validates Removed Extended Location")
	public void RemoveLocation() {
		try {
			final String CASE_NUM = "116004";
			
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL,FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER,FilePath.PROPERTY_CONFIG_FILE), propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD,FilePath.PROPERTY_CONFIG_FILE));
	
			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
						
			String locationName = rstLocationListData.get(CNLocationList.LOCATION_NAME);
			
			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.CURRENT_ORG,
					FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			

			// Selecting the Product
			textBox.enterText(LocationList.TXT_FILTER, rstLocationSummaryData.get(CNLocationSummary.PRODUCT_NAME));
			locationList.selectGlobalProduct(rstLocationSummaryData.get(CNLocationSummary.PRODUCT_NAME));

			// selecting location
			foundation.click(productSummary.getLocationNamePath(locationName));

			// Remove selected location
			foundation.waitforElement(ProductSummary.BTN_REMOVE, 2000);
			foundation.click(ProductSummary.BTN_REMOVE);

			// Validations
			foundation.wait(2000);
			Boolean status = productSummary.verifyLocationName(locationName);
			Assert.assertFalse(status);
			
			//resetting test data
			foundation.waitforElement(ProductSummary.BTN_EXTEND, 2000);
			foundation.click(ProductSummary.BTN_EXTEND);
			textBox.enterText(ProductSummary.TXT_FILTER, locationName);
			table.selectRow(Constants.PRODUCT_DATAGRID, locationName);

			foundation.click(ProductSummary.BTN_SAVE);

		} catch (Exception exc) {
			exc.printStackTrace();
			Assert.fail();
		}
	}
}
