package at.smartshop.tests;

import static org.testng.Assert.assertEquals;

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
import at.framework.ui.Radio;
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
import at.smartshop.pages.GlobalProductChange;
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
	private Dropdown dropDown = new Dropdown();
	private LocationSummary locationSummary = new LocationSummary();
	private Radio radio=new Radio();
	
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
			foundation.click(globalProduct.getGlobalProduct(rstDeviceListData.get(CNDeviceList.PRODUCT_NAME)));
			
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
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			
			List<String> locationDisabled = Arrays.asList(rstLocationSummaryData.get(CNLocationSummary.LOCATION_DISABLED).split(Constants.DELIMITER_TILD));
			dropDown.selectItem(LocationSummary.DPD_DISABLED, locationDisabled.get(0), Constants.TEXT);
			
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.click(LocationSummary.POP_UP_BTN_SAVE);
			
			List<String> dropDownList = Arrays
					.asList(rstLocationListData.get(CNLocationList.DROPDOWN_LOCATION_LIST).split(Constants.DELIMITER_TILD));
			dropDown.selectItem(LocationList.DPD_LOCATION_LIST,dropDownList.get(1),Constants.TEXT);
			
			// Searching for Product
			textBox.enterText(LocationList.TXT_FILTER, rstLocationListData.get(CNLocationList.LOCATION_NAME));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			
			foundation.click(LocationSummary.TBL_PRODUCTS);
		
			Assert.assertTrue(foundation.getSizeofListElement(LocationSummary.ROW_PRODUCTS)<= 0);
			
			dropDown.selectItem(LocationSummary.DPD_DISABLED, locationDisabled.get(1), Constants.TEXT);
			
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.DPD_LOCATION_LIST, 2000);
			
		} catch (Exception exc) {
			Assert.fail();
		}

	}
	
	@Test(description ="This test to verify the Error Message validation for Retrieve Account Methods")
    public void validateErrorMessage() {
        try {
            final String CASE_NUM="130658";
            browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL,FilePath.PROPERTY_CONFIG_FILE));
            login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER,FilePath.PROPERTY_CONFIG_FILE), propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD,FilePath.PROPERTY_CONFIG_FILE));
           
            rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
            rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
            rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);           
           
            navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.CURRENT_ORG,FilePath.PROPERTY_CONFIG_FILE));
            locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
            foundation.waitforElement(LocationSummary.BUTTON_LOCATION_INFO, 2);
            foundation.click(LocationSummary.BUTTON_LOCATION_INFO);
            dropDown.selectItem(LocationSummary.DPD_RETRIEVE_ACCOUNT, rstLocationSummaryData.get(CNLocationSummary.ENABLE_RETRIEVE_ACCOUNT), Constants.TEXT);
            Assert.assertTrue(foundation.isDisplayed(LocationSummary.FIELD_RETRIEVE_CHECKBOX));
            foundation.click(LocationSummary.BTN_SAVE);
           
            Assert.assertEquals(foundation.getText(LocationSummary.TXT_ERR_MSG), rstLocationListData.get(CNLocationList.INFO_MESSAGE));  
                   
        }catch(Exception exc) {
            Assert.fail(exc.toString());
        }
    }
	
	@Test(description = "verify Add Home commercial in Home commercial Tab and Disable Location")
	public void Add_HomeCommercial() {
		try {
			final String CASE_NUM = "114262";

			browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL,FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER,FilePath.PROPERTY_CONFIG_FILE), propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD,FilePath.PROPERTY_CONFIG_FILE));
			
			// Reading test data from DataBase
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
			rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);
			String locationName = rstLocationListData.get(CNLocationList.LOCATION_NAME);
			List<String> locationList_Dpd_Values = Arrays.asList(rstLocationListData.get(CNLocationList.DROPDOWN_LOCATION_LIST).split(Constants.DELIMITER_TILD));
			
			List<String> locationDisabled = Arrays.asList(rstLocationSummaryData.get(CNLocationSummary.LOCATION_DISABLED).split(Constants.DELIMITER_TILD));
			String locationDisabled_Yes = locationDisabled.get(0);
			String locationDisabled_No = locationDisabled.get(1);
			// Selecting location
			locationList.selectLocationName(locationName);

			// upload image
			foundation.waitforElement(LocationList.BTN_HOME_COMMERCIAL, 2000);
			foundation.click(LocationList.BTN_HOME_COMMERCIAL);
			foundation.click(LocationList.BTN_ADD_HOME_COMMERCIAL);
			foundation.click(LocationList.TXT_UPLOAD_NEW);
			textBox.enterText(LocationList.BTN_UPLOAD_INPUT, "C:\\Users\\ajaybabur\\Pictures\\icecream.jpg");
			textBox.enterText(LocationList.TXT_ADD_NAME, "Icecream");
			foundation.click(LocationList.BTN_ADD);

			// disabling location
			foundation.waitforElement(LocationSummary.DPD_DISABLED, 2000);
			dropDown.selectItem(LocationSummary.DPD_DISABLED,locationDisabled_Yes, "text");
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.click(LocationSummary.POP_UP_BTN_SAVE);

			// Navigating to disabled location list
			dropDown.selectItem(LocationList.DPD_LOCATION_LIST, locationList_Dpd_Values.get(1), "text");

			// validations
			Boolean status = foundation.isDisplayed(locationList.getlocationElement(locationName));
			Assert.assertTrue(status);
			
			//resetting data
			locationList.selectLocationName(locationName);
			foundation.waitforElement(LocationSummary.DPD_DISABLED, 2000);
			dropDown.selectItem(LocationSummary.DPD_DISABLED,locationDisabled_No, "text");
			foundation.click(LocationSummary.BTN_SAVE);
						
		} catch (Exception exc) {
			exc.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test(description = "Update Loyalty Multiplier for a product in Operator Product Catalog Change")
    public void UpdateLoyaltyMultiplier() {
        try {
            final String CASE_NUM = "111001";                
            
            // Reading test data from DataBase
            rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
            
            //Split database data
            List<String> subMenu = Arrays.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split("~"));
            
            // Select Menu and Menu Item
            navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.CURRENT_ORG,FilePath.PROPERTY_CONFIG_FILE));
            navigationBar.navigateToMenuItem(subMenu.get(0));

            // Select Operator Product Catalog Change radio button and select 1st product
            // from filter search
            radio.set(GlobalProductChange.RDO_OPERATOR_PRODUCT_CHANGE);
            String product = foundation.getText(GlobalProductChange.LBL_PRODUCT);
            foundation.click(GlobalProductChange.LBL_PRODUCT);
            foundation.click(GlobalProductChange.BTN_NEXT);

            // Update loyalty filter
            dropDown.selectItem(GlobalProductChange.DPD_LOYALITY_MULTIPLIER, "5", Constants.VALUE);
            foundation.click(GlobalProductChange.BTN_SUBMIT);
            foundation.click(GlobalProductChange.BTN_OK);
            foundation.isDisplayed(GlobalProductChange.MSG_SUCCESS);

            // Select Menu and Global product
            navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.CURRENT_ORG,FilePath.PROPERTY_CONFIG_FILE));
            navigationBar.navigateToMenuItem(subMenu.get(1));

            // Search and select product
            textBox.enterText(GlobalProduct.TXT_FILTER, product);
            foundation.click(globalProduct.getGlobalProduct(product));

            // verify value in loyalty dropdown
            assertEquals(dropDown.getSelectedItem(ProductSummary.DPD_LOYALTY_MULTIPLIER), "5");
        } catch (Exception exc) {
            exc.printStackTrace();
            Assert.fail();
        }
    }

 

    @Test(description = "Update Tax for Product and verify in Location Summary -> Products Tab")
    public void updateTaxForProduct() {
        try {
            final String CASE_NUM = "114899";            
            
            // Reading test data from DataBase
            rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
            rstDeviceListData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);
            rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);

            String product = rstDeviceListData.get(CNLocationSummary.PRODUCT_NAME);
            String location = rstLocationListData.get(CNLocationList.LOCATION_NAME);

            // Select Menu and Menu Item
            navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.CURRENT_ORG,FilePath.PROPERTY_CONFIG_FILE));
            navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

            // Searching for Product
            textBox.enterText(GlobalProduct.TXT_FILTER, product);
            foundation.click(globalProduct.getGlobalProduct(product));

            //select tax category
            dropDown.selectItemByIndex(ProductSummary.DPD_TAX_CATEGORY, 2);
            String selectedTaxCat = dropDown.getSelectedItem(ProductSummary.DPD_TAX_CATEGORY);
            foundation.click(ProductSummary.BTN_SAVE);

            // naviagate back to product summary page
            textBox.enterText(GlobalProduct.TXT_FILTER, product);
            foundation.click(globalProduct.getGlobalProduct(product));

            // Navigate to product's location
            textBox.enterText(ProductSummary.TXT_SEARCH, location);
            foundation.click(ProductSummary.TBL_DATA);
            foundation.waitforElement(ProductSummary.BTN_REMOVE, 10000);
            foundation.click(ProductSummary.BTN_EDIT_LOCATION);

            // navigate to product tab
            foundation.click(LocationSummary.TAB_PRODUCTS);

            // enable show tax cat column
            locationSummary.showTaxCategory();
            foundation.threadWait(3000);
            
            // ensure selected tax category from product summary page displays for the product here
            textBox.enterText(LocationSummary.TXT_SEARCH, product);            
            assertEquals(foundation.getText(LocationSummary.LBL_TAX_CATEGORY), selectedTaxCat);
        } catch (Exception exc) {
            exc.printStackTrace();
            Assert.fail();
        }
    }
}
