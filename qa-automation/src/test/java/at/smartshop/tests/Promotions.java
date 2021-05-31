package at.smartshop.tests;

import static org.testng.Assert.assertTrue;

import java.util.Map;

import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import at.framework.database.mssql.Queries;
import at.framework.database.mssql.ResultSets;
import at.framework.files.PropertyFile;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.database.columns.CNLocation;
import at.smartshop.database.columns.CNNavigationMenu;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.pages.CreatePromotions;
import at.smartshop.pages.EditPromotion;
import at.smartshop.pages.NavigationBar;
import at.smartshop.pages.PromotionList;

@Listeners(at.framework.reportsetup.Listeners.class)
public class Promotions extends TestInfra {

	private PropertyFile propertyFile = new PropertyFile();
	private ResultSets dataBase = new ResultSets();
	private Foundation foundation = new Foundation();
	private NavigationBar navigationBar = new NavigationBar();
	private CreatePromotions createPromotions = new CreatePromotions();
	private Dropdown dropdown=new Dropdown();
	private TextBox textBox = new TextBox();
	private EditPromotion editPromotion = new EditPromotion();
	
	private Map<String, String> rstNavigationMenuData;
	private Map<String, String> rstLocationData;

	@Test(description = "Verify All option is displayed in Location Dropdown")
	public void verifyPromotions() {
		try {
			final String CASE_NUM = "130666";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from database
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);

			String promotionName = rstLocationData.get(CNLocation.PROMOTION_NAME);
			String promotionType = rstLocationData.get(CNLocation.PROMOTION_TYPE);
			String locationName = rstLocationData.get(CNLocation.LOCATION_NAME);
			String requiredData = rstLocationData.get(CNLocation.REQUIRED_DATA);

			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// New Promotion
			foundation.click(PromotionList.BTN_CREATE);
			createPromotions.newPromotion(promotionType, promotionName, requiredData, locationName);

			// Validating "All" option in Location field
			String uiData = foundation.getText(CreatePromotions.DPD_LOCATION);
			Assert.assertEquals(uiData, locationName);

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	@Test(description = "C141774 - SOS-7519 - Verify Create Promotion page display only active location when Org  filter is selected")
	public void verifyActiveLocationPromotions() {
		try {
			final String CASE_NUM = "141774";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from database
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);

			String promotionName = rstLocationData.get(CNLocation.PROMOTION_NAME);
			String promotionType = rstLocationData.get(CNLocation.PROMOTION_TYPE);
			String locationName = rstLocationData.get(CNLocation.LOCATION_NAME);
			String requiredData = rstLocationData.get(CNLocation.REQUIRED_DATA);
			String gridName = rstLocationData.get(CNLocation.TAB_NAME);

			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Creating New Promotion
			foundation.click(PromotionList.BTN_CREATE);

			dropdown.selectItem(CreatePromotions.DPD_PROMO_TYPE, promotionType, Constants.TEXT);
			textBox.enterText(CreatePromotions.TXT_PROMO_NAME, promotionName);
			foundation.click(CreatePromotions.BTN_NEXT);
			textBox.enterText(CreatePromotions.DPD_ORG,propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			textBox.enterText(CreatePromotions.DPD_ORG, Keys.ENTER);
		
	        dropdown.selectItem(CreatePromotions.DPD_LOCATION, locationName, Constants.TEXT);
	        foundation.waitforElement(CreatePromotions.BTN_NEXT,2000);

			
			foundation.click(CreatePromotions.BTN_NEXT);
			dropdown.selectItem(CreatePromotions.MULTI_SELECT_TENDER_TYPES,requiredData, Constants.TEXT); 

			foundation.threadWait(2000);
			foundation.click(CreatePromotions.BTN_NEXT);
			
	        foundation.waitforElement(CreatePromotions.BTN_OK,2000);
	        foundation.click(CreatePromotions.BTN_OK);
	        
	        //Validating promotion is displayed
	        assertTrue(foundation.getText(PromotionList.TBL_COLUMN_NAME).equals(promotionName));

	        //Resetting the data
	        editPromotion.expirePromotion(gridName,promotionName);

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
}
