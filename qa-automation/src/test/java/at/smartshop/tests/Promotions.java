package at.smartshop.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import at.framework.database.mssql.Queries;
import at.framework.database.mssql.ResultSets;
import at.framework.files.PropertyFile;
import at.framework.ui.Foundation;
import at.smartshop.database.columns.CNLocation;
import at.smartshop.database.columns.CNNavigationMenu;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.FilePath;
import at.smartshop.pages.CreatePromotions;
import at.smartshop.pages.NavigationBar;
import at.smartshop.pages.PromotionList;

@Listeners(at.framework.reportsetup.Listeners.class)
public class Promotions extends TestInfra {

	private PropertyFile propertyFile = new PropertyFile();
	private ResultSets dataBase = new ResultSets();
	private Foundation foundation = new Foundation();
	private NavigationBar navigationBar = new NavigationBar();
	private CreatePromotions createPromotions = new CreatePromotions();

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
			createPromotions.newPromotion(promotionType, promotionName, requiredData);

			// Validating "All" option in Location field
			String uiData = foundation.getText(CreatePromotions.DPD_LOCATION);
			Assert.assertEquals(uiData, locationName);

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
	
	@Test(description = "141776-To Verify Creating Tender Discount promotion (Tender Type set as Account)")
	public void verifyTenderDiscountPromo() {
		try {
			final String CASE_NUM = "141776";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from database
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);

			String promotionName = strings.getRandomCharacter();
			String promotionType = rstLocationData.get(CNLocation.PROMOTION_TYPE);
			String locationName = rstLocationData.get(CNLocation.LOCATION_NAME);
			//String requiredData = rstLocationData.get(CNLocation.REQUIRED_DATA);
			String gridName = rstLocationData.get(CNLocation.TAB_NAME);
			List<String>  requiredData= Arrays.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));

			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			//validate UI of promotion list page
			assertTrue(foundation.isDisplayed(PromotionList.TXT_SEARCH));
			assertTrue(foundation.isDisplayed(PromotionList.CALENDER_DATE_RANGE));
			assertTrue(foundation.isDisplayed(PromotionList.DPD_LOCATION));
			assertTrue(foundation.isDisplayed(PromotionList.DPD_STATUS));
			assertTrue(foundation.isDisplayed(PromotionList.DPD_PROMOTYPE));			
			assertEquals(foundation.getText(PromotionList.LBL_SEARCH),requiredData.get(0));
			assertEquals(foundation.getText(PromotionList.LBL_CALENDER_DATE_RANGE),requiredData.get(1));
			assertEquals(foundation.getText(PromotionList.LBL_LOCATION),requiredData.get(2));
			assertEquals(foundation.getText(PromotionList.LBL_STATUS),requiredData.get(3));
			assertEquals(foundation.getText(PromotionList.LBL_PROMOTYPE),requiredData.get(4));
			
			// Creating New Promotion
			foundation.click(PromotionList.BTN_CREATE);

			//validate UI of enter promotion basics page
			assertTrue(foundation.isDisplayed(CreatePromotions.DPD_PROMO_TYPE));
			assertTrue(foundation.isDisplayed(CreatePromotions.TXT_PROMO_NAME));
			assertTrue(foundation.isDisplayed(CreatePromotions.BTN_NEXT));
			assertTrue(foundation.isDisplayed(CreatePromotions.BTN_CANCEL));					
			assertEquals(foundation.getText(CreatePromotions.LBL_BASICINFO),requiredData.get(5));
			assertEquals(foundation.getText(CreatePromotions.LBL_ENTER_BASICINFO),requiredData.get(6));
			
			//provide basic info and navigate to next
			dropdown.selectItem(CreatePromotions.DPD_PROMO_TYPE, promotionType, Constants.TEXT);
			textBox.enterText(CreatePromotions.TXT_PROMO_NAME, promotionName);
			foundation.click(CreatePromotions.BTN_NEXT);
			
			foundation.threadWait(1000);
			
			//validate UI of enter promotion filter page
			assertTrue(foundation.isDisplayed(CreatePromotions.DPD_ORG));
			assertTrue(foundation.isDisplayed(CreatePromotions.DPD_LOCATION));
			assertTrue(foundation.isDisplayed(CreatePromotions.BTN_NEXT));
			assertEquals(foundation.getText(CreatePromotions.LBL_FILTER),requiredData.get(7));
			assertEquals(foundation.getText(CreatePromotions.LBL_SELECT_CRITERIA),requiredData.get(8));
			
			//choose promotion filter
			textBox.enterText(CreatePromotions.DPD_ORG,
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			textBox.enterText(CreatePromotions.DPD_ORG, Keys.ENTER);
			dropdown.selectItem(CreatePromotions.DPD_LOCATION, locationName, Constants.TEXT);
			foundation.waitforElement(CreatePromotions.BTN_NEXT, 2000);
			foundation.click(CreatePromotions.BTN_NEXT);

			foundation.threadWait(1000);
			
			//validate UI of enter promotion filter page
			assertTrue(foundation.isDisplayed(CreatePromotions.MULTI_SELECT_TENDER_TYPES));
			assertTrue(foundation.isDisplayed(CreatePromotions.DPD_DISCOUNT_TYPE));
			assertTrue(foundation.isDisplayed(CreatePromotions.DPD_APPLY_DISCOUNT_TO));
			assertTrue(foundation.isDisplayed(CreatePromotions.TXT_TRANSACTION_MIN));
			assertTrue(foundation.isDisplayed(CreatePromotions.DPD_DISCOUNT_TIME));
			assertTrue(foundation.isDisplayed(CreatePromotions.BTN_CREATE));
			assertEquals(foundation.getText(CreatePromotions.LBL_DETAILS),requiredData.get(9));
			assertEquals(foundation.getText(CreatePromotions.LBL_SET_PROMO_DETAILS),requiredData.get(10));			
			
			dropdown.selectItem(CreatePromotions.MULTI_SELECT_TENDER_TYPES, requiredData.get(11), Constants.TEXT);
			foundation.threadWait(2000);
			foundation.click(CreatePromotions.BTN_NEXT);

			foundation.waitforElement(CreatePromotions.BTN_OK, 2000);
			foundation.click(CreatePromotions.BTN_OK);

			// Validating promotion is displayed
			foundation.threadWait(2000);
			assertTrue(foundation.getText(PromotionList.TBL_COLUMN_NAME).equals(promotionName));

			// Resetting the data
			promotionList.searchPromotion(promotionName);
			assertTrue(foundation.getText(PromotionList.TBL_COLUMN_NAME).equals(promotionName));
			editPromotion.expirePromotion(gridName,promotionName);

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

}
