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
import at.framework.ui.TextBox;

import at.smartshop.pages.*;
import at.smartshop.database.columns.CNGlobalProductChange;
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
	
	private Map<String, String> rstNavigationMenuData;
	private Map<String, String> rstGlobalProductChangeData;

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
			price=Double.parseDouble(productSummary.getPriceFromLocationsTable());
			navigationBar.navigateToMenuItem(menuItem.get(0));
			
			textBox.enterText(GlobalProductChange.TXT_LOCATION_SEARCH, rstGlobalProductChangeData.get(CNGlobalProductChange.LOCATION_NAME));
			
			Thread.sleep(2000);
			foundation.click(GlobalProductChange.LNK_SEARCH_ALL);
			foundation.click(GlobalProductChange.TAB_PRODUCT);
			textBox.enterText(GlobalProductChange.TXT_PRODUCT_NAME, product);
			foundation.click(GlobalProductChange.BTN_PRODUCT_APPLY);			
			       
	        foundation.click(GlobalProductChange.LBL_PRODUCT);
			
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
	        foundation.isDisplayed(GlobalProductChange.MSG_SUCCESS, "Product increment submition successmessage");
	       
	        // Select Menu and Global product	       
	        navigationBar.navigateToMenuItem(menuItem.get(1));
	        
	       
	        // Search and select product
	        textBox.enterText(LocationList.TXT_FILTER, product);
	        globalProduct.selectGlobalProduct(product);
	        textBox.enterText(ProductSummary.TXT_LOCATION_SEARCH_FILTER, rstGlobalProductChangeData.get(CNGlobalProductChange.LOCATION_NAME));
	        double updatedPrice = price+Incrementprice;
    	    
	        assertEquals(Double.parseDouble(productSummary.getPriceFromLocationsTable()), updatedPrice);
	        
			
		} catch (Exception exc) {
			exc.printStackTrace();
			Assert.fail();
		}

	}
}
