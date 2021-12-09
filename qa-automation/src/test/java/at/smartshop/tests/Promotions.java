package at.smartshop.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import at.framework.database.mssql.Queries;
import at.framework.database.mssql.ResultSets;
import at.framework.files.PropertyFile;
import at.framework.generic.DateAndTime;
import at.framework.generic.Strings;
import at.framework.ui.CheckBox;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.Table;
import at.framework.ui.TextBox;
import at.smartshop.database.columns.CNLocation;
import at.smartshop.database.columns.CNLocationList;
import at.smartshop.database.columns.CNLocationSummary;
import at.smartshop.database.columns.CNNavigationMenu;
import at.smartshop.database.columns.CNUserRoles;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.pages.CreatePromotions;
import at.smartshop.pages.EditPromotion;
import at.smartshop.pages.LocationList;
import at.smartshop.pages.LocationSummary;
import at.smartshop.pages.NavigationBar;
import at.smartshop.pages.PromotionList;
import at.smartshop.pages.UserList;
import at.smartshop.pages.UserRoles;

@Listeners(at.framework.reportsetup.Listeners.class)
public class Promotions extends TestInfra {

	private PropertyFile propertyFile = new PropertyFile();
	private ResultSets dataBase = new ResultSets();
	private Foundation foundation = new Foundation();
	private NavigationBar navigationBar = new NavigationBar();
	private CreatePromotions createPromotions = new CreatePromotions();
	private LocationList locationList = new LocationList();
	private Dropdown dropdown = new Dropdown();
	private TextBox textBox = new TextBox();
	private Strings strings = new Strings();
	private DateAndTime dateAndTime = new DateAndTime();
	private PromotionList promotionList = new PromotionList();
	private EditPromotion editPromotion = new EditPromotion();
	private UserRoles userRoles = new UserRoles();
	private UserList userList = new UserList();
	private CheckBox checkBox = new CheckBox();
	private Table table = new Table();	

	private Map<String, String> rstNavigationMenuData;
	private Map<String, String> rstLocationData;
	private Map<String, String> rstLocationListData;
	private Map<String, String> rstLocationSummaryData;
	private Map<String, String> rstUserRolesData;

	@Test(description = "141771-Verify All option is displayed in Location Dropdown")
	public void verifyPromotions() {
		try {
			final String CASE_NUM = "141771";

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

			List<String> requiredData = Arrays.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));

			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// New Promotion
			foundation.click(PromotionList.BTN_CREATE);
			createPromotions.newPromotion(promotionType, promotionName, requiredData.get(0), requiredData.get(1),locationName);

			// Validating "All" option in Location field
			String uiData = dropdown.getSelectedItem(CreatePromotions.DPD_LOCATION);
			assertEquals(uiData, locationName);

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "141779-Verify the Tender Discount Promotion with Tender type as Cash")
	public void verifyTenderDiscountWithTenderTypeCash() {
		final String CASE_NUM = "141779";

		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);

		String promotionType = rstLocationData.get(CNLocation.PROMOTION_TYPE);
		String locationName = rstLocationData.get(CNLocation.LOCATION_NAME);
		String gridName = rstLocationData.get(CNLocation.TAB_NAME);
		String promotionName = strings.getRandomCharacter();
		try {
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// validate UI of promotion list page info section
			assertTrue(foundation.isDisplayed(PromotionList.LBL_SEARCH));
			assertTrue(foundation.isDisplayed(PromotionList.CALENDER_DATE_RANGE));
			assertTrue(foundation.isDisplayed(PromotionList.DPD_LOCATION));
			assertTrue(foundation.isDisplayed(PromotionList.DPD_STATUS));
			assertTrue(foundation.isDisplayed(PromotionList.DPD_PROMOTYPE));
			assertTrue(foundation.isDisplayed(PromotionList.BTN_CREATE));
			assertTrue(foundation.isDisplayed(PromotionList.BTN_SEARCH));

			foundation.click(PromotionList.BTN_CREATE);
			foundation.isDisplayed(CreatePromotions.LBL_CREATE_PROMOTION);
			dropdown.selectItem(CreatePromotions.DPD_PROMO_TYPE, promotionType, Constants.TEXT);

			String basicInfoPageTitle = foundation.getText(CreatePromotions.LBL_PAGE_TITLE);
			assertTrue(basicInfoPageTitle.equals(rstLocationData.get(CNLocation.PROMOTION_TYPE)));

			textBox.enterText(CreatePromotions.TXT_PROMO_NAME, promotionName);
			foundation.click(CreatePromotions.BTN_NEXT);
			String filtersPageTitle = foundation.getText(CreatePromotions.LBL_PAGE_TITLE);
			assertTrue(filtersPageTitle.equals(rstLocationData.get(CNLocation.PROMOTION_TYPE)));

			textBox.enterText(CreatePromotions.DPD_ORG,propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			textBox.enterText(CreatePromotions.DPD_ORG, Keys.ENTER);
			dropdown.selectItem(CreatePromotions.DPD_LOCATION, locationName, Constants.TEXT);

			foundation.threadWait(Constants.TWO_SECOND);
			foundation.click(CreatePromotions.BTN_NEXT);
			List<String> requiredData = Arrays.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));

			dropdown.selectItem(CreatePromotions.MULTI_SELECT_TENDER_TYPES, requiredData.get(0), Constants.TEXT);
			dropdown.selectItem(CreatePromotions.DPD_DISCOUNT_TYPE, requiredData.get(1), Constants.TEXT);
			dropdown.selectItem(CreatePromotions.DPD_APPLY_DISCOUNT_TO, requiredData.get(2), Constants.TEXT);
			textBox.enterText(CreatePromotions.TXT_AMOUNT, requiredData.get(3));
			textBox.enterText(CreatePromotions.TXT_TRANSACTION_MIN, requiredData.get(4));
			dropdown.selectItem(CreatePromotions.DPD_DISCOUNT_TIME, requiredData.get(5), Constants.TEXT);
			foundation.click(CreatePromotions.CHK_SUNDAY);

			foundation.threadWait(Constants.TWO_SECOND);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.waitforElement(CreatePromotions.BTN_OK,Constants.SHORT_TIME);		
			 			 
			  List<String> popupFieldType = foundation.getTextofListElement(CreatePromotions.POP_UP_MESSAGES);
				List<String> popupField = null;
				popupField= Arrays.asList(popupFieldType.get(0).split(Constants.DELIMITER_COMMA));
				popupField= Arrays.asList(popupField.get(0).split(Constants.NEW_LINE));
	
				List<String> actualData = Arrays.asList(rstLocationData.get(CNLocation.ACTUAL_DATA).split(Constants.DELIMITER_TILD));
				assertEquals(popupField.get(0),actualData.get(0));
				assertEquals(popupField.get(1),actualData.get(1));				
			
			List<String> popupFieldArray = createPromotions.getPopUpData();		
			String currentDate=dateAndTime.getDateAndTime(Constants.REGEX_MMDDYY, Constants.TIME_ZONE_INDIA);

			assertTrue(popupFieldArray.get(0).contains(promotionType));
			assertTrue(popupFieldArray.get(1).contains(promotionName));
			assertEquals(popupFieldArray.get(2), actualData.get(2));
			assertEquals(popupFieldArray.get(3), actualData.get(3));
			assertEquals(popupFieldArray.get(4), actualData.get(4));
			assertEquals(popupFieldArray.get(5), actualData.get(5));
			assertEquals(popupFieldArray.get(6), actualData.get(6));
			assertTrue(popupFieldArray.get(7).contains(currentDate));
			assertTrue(popupFieldArray.get(8).contains(currentDate));

			foundation.click(CreatePromotions.BTN_OK);
			
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		finally {
			// Resetting the data
			promotionList.expirePromotion(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM),promotionName,gridName);
		}
	}


	@Test(description = "118620-This test validates the Create Promotion with Promotion Type as On Screen")
	public void verifyCreatePromotionWithOnScreen() {
		final String CASE_NUM = "118620";

		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
		rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);
		
		String promotionType = rstLocationData.get(CNLocation.PROMOTION_TYPE);
		String gridName = rstLocationData.get(CNLocation.TAB_NAME);
		String locationName = rstLocationData.get(CNLocation.LOCATION_NAME);
		List<String> menu = Arrays.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		String promotionName = strings.getRandomCharacter();
		try {
			browser.navigateURL(	propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
							propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			
			navigationBar.navigateToMenuItem(menu.get(0));
			foundation.waitforElement(UserRoles.LBL_USER_LIST,  Constants.SHORT_TIME);
			textBox.enterText(UserRoles.TXT_SEARCH_FILTER,propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(userRoles.getRowByText(rstLocationSummaryData.get(CNLocationSummary.CONTACT_EMAIL)));
			foundation.waitforElement(UserRoles.LBL_VIEW_ROLE, Constants.SHORT_TIME);
			foundation.click(UserList.LNK_ORG_REMOVE_ALL);
			List<String> orgs = Arrays.asList(rstLocationData.get(CNLocation.COLUMN_VALUE).split(Constants.DELIMITER_TILD));
			userList.selectOrgs(UserList.DPD_ORG, orgs);
			foundation.threadWait(Constants.TWO_SECOND);
			String allOption = rstLocationListData.get(CNLocationList.COLUMN_NAME);
			foundation.objectFocus(UserList.TXT_SEARCH_LOC);
			dropdown.selectItem(UserList.TXT_SEARCH_LOC, allOption, Constants.TEXT);
			foundation.threadWait(Constants.TWO_SECOND);
			foundation.click(UserList.BTN_UPDATE_USER);
			foundation.waitforElement(UserRoles.LBL_USER_LIST, Constants.SHORT_TIME);
			login.logout();

			// Operator User
			browser.navigateURL(	propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			List<String> locationNames = foundation.getTextofListElement(LocationList.LINK_LOCATION_LIST);
			navigationBar.navigateToMenuItem(menu.get(1));

			// Basic Information Page
			foundation.click(PromotionList.BTN_CREATE);
			foundation.isDisplayed(CreatePromotions.LBL_CREATE_PROMOTION);
			foundation.isDisplayed(CreatePromotions.LBL_BASIC_INFORMATION);
			dropdown.selectItem(CreatePromotions.DPD_PROMO_TYPE, promotionType, Constants.TEXT);
			textBox.enterText(CreatePromotions.TXT_PROMO_NAME, promotionName);
			textBox.enterText(CreatePromotions.TXT_DISPLAY_NAME, promotionName);
			foundation.click(CreatePromotions.BTN_NEXT);

			// Filter Page
			foundation.threadWait(Constants.TWO_SECOND);
			List<String> orgData = dropdown.getAllItems(CreatePromotions.DPD_ORGANIZATION);
			for (int iter = 0; iter < orgData.size(); iter++) {
				Assert.assertTrue(orgData.get(iter).contains(orgs.get(iter)));
			}
			List<String> fieldName = Arrays.asList(rstLocationListData.get(CNLocationList.INFO_MESSAGE).split(Constants.DELIMITER_TILD));
			Assert.assertTrue(foundation.isDisplayed(createPromotions.filterOptions(fieldName.get(0))));
			Assert.assertTrue(foundation.isDisplayed(createPromotions.filterOptions(fieldName.get(1))));
			textBox.enterText(CreatePromotions.DPD_ORG,	propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			textBox.enterText(CreatePromotions.DPD_ORG, Keys.ENTER);
			
			for (String location : locationNames) {
				assertTrue(dropdown.verifyItemPresent(CreatePromotions.DPD_LOCATION, location));
			}
			dropdown.selectItem(CreatePromotions.DPD_LOCATION, locationName, Constants.TEXT);
			foundation.waitforElement(CreatePromotions.BTN_NEXT, 2);
			foundation.click(CreatePromotions.BTN_NEXT);

			// Promotion Details Page
			List<String> discountType = Arrays.asList(rstLocationData.get(CNLocation.PRODUCT_NAME).split(Constants.DELIMITER_TILD));
			dropdown.selectItem(CreatePromotions.DPD_DISCOUNT_BY, discountType.get(0), Constants.TEXT);
			textBox.enterText(CreatePromotions.SEARCH_CATEGORY, discountType.get(2));
			foundation.threadWait(Constants.ONE_SECOND);
			textBox.enterText(CreatePromotions.SEARCH_CATEGORY, Keys.ENTER);
			dropdown.selectItem(CreatePromotions.DPD_DISCOUNT_BY, discountType.get(1), Constants.TEXT);
			textBox.enterText(CreatePromotions.TXT_ITEM, discountType.get(3));
			foundation.threadWait(Constants.ONE_SECOND);
			textBox.enterText(CreatePromotions.TXT_ITEM, Keys.ENTER);
			foundation.click(CreatePromotions.BTN_CREATE);
			foundation.waitforElement(CreatePromotions.POP_UP_MESSAGES, Constants.SHORT_TIME);

			List<String> popupFieldType = foundation.getTextofListElement(CreatePromotions.POP_UP_MESSAGES);
			List<String> popupField = null;
			popupField = Arrays.asList(popupFieldType.get(0).split(Constants.DELIMITER_COMMA));
			popupField = Arrays.asList(popupField.get(0).split(Constants.NEW_LINE));

			List<String> actualData = Arrays.asList(rstLocationData.get(CNLocation.ACTUAL_DATA).split(Constants.DELIMITER_TILD));
			assertEquals(popupField.get(0), actualData.get(0));
			assertEquals(popupField.get(1), actualData.get(1));

			List<String> popupFieldArray = createPromotions.getPopUpData();
			String currentDate = dateAndTime.getDateAndTime(Constants.REGEX_MMDDYY, Constants.TIME_ZONE_INDIA);

			assertTrue(popupFieldArray.get(0).contains(promotionType));
			assertTrue(popupFieldArray.get(1).contains(promotionName));
			assertTrue(popupFieldArray.get(2).contains(promotionName));
			assertEquals(popupFieldArray.get(3), actualData.get(2));
			assertEquals(popupFieldArray.get(4), actualData.get(3));
			assertEquals(popupFieldArray.get(5), actualData.get(4));
			assertEquals(popupFieldArray.get(6), actualData.get(5));
			assertTrue(popupFieldArray.get(7).contains(currentDate));
			assertTrue(popupFieldArray.get(8).contains(currentDate));
			foundation.click(CreatePromotions.BTN_OK);

			// Validating promotion is displayed
			foundation.waitforElement(PromotionList.PAGE_TITLE, Constants.SHORT_TIME);
			promotionList.verifyPromotionName(promotionName);
			
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		finally {
			// Resetting the data
			promotionList.expirePromotion(menu.get(1),promotionName,gridName);
		}
	}

	@Test(description = "141821-This test verifies the existing Promotion with new Org and Location")
	public void verifyExistingPromotionWithNewOrgLocation() {
		final String CASE_NUM = "141821";

		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
		rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);
	
		String promotionName = rstLocationData.get(CNLocation.PROMOTION_NAME);
		List<String> org = Arrays	.asList(rstLocationData.get(CNLocation.COLUMN_VALUE).split(Constants.DELIMITER_TILD));
		List<String> location = Arrays.asList(rstLocationData.get(CNLocation.LOCATION_NAME).split(Constants.DELIMITER_TILD));
		try {
			browser.navigateURL(	propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			foundation.waitforElement(PromotionList.PAGE_TITLE, Constants.SHORT_TIME);
			
			promotionList.searchPromotion(promotionName);
			foundation.doubleClick(PromotionList.TBL_COLUMN_NAME);

			// Basic Information Page
			foundation.waitforElement(CreatePromotions.LBL_BASIC_INFORMATION, Constants.SHORT_TIME);
			String title = rstLocationListData.get(CNLocationList.INFO_MESSAGE);
			Assert.assertTrue(foundation.getText(EditPromotion.PAGE_TITLE).contains(title));
			Assert.assertTrue(checkBox.isChecked(EditPromotion.CHK_ACTIVE));
			foundation.click(CreatePromotions.BTN_NEXT);

			// Filter Page
			
			String orgExistValue = dropdown.getSelectedItem(CreatePromotions.DPD_ORGANIZATION);
			Assert.assertEquals(orgExistValue, org.get(0));
			String locExistValue = dropdown.getSelectedItem(CreatePromotions.DPD_LOCATION);
			Assert.assertEquals(locExistValue, location.get(0));

			dropdown.deSelectItem(CreatePromotions.DPD_ORGANIZATION, org.get(0), Constants.TEXT);
			textBox.enterText(CreatePromotions.DPD_ORG, org.get(1));
			foundation.threadWait(Constants.ONE_SECOND);
			textBox.enterText(CreatePromotions.DPD_ORG, Keys.ENTER);
			dropdown.selectItem(CreatePromotions.DPD_LOCATION, location.get(1), Constants.TEXT);
			foundation.waitforElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);

			// Details page
			foundation.threadWait(Constants.TWO_SECOND);
			List<String> requiredData = Arrays.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			dropdown.deSelectItem(CreatePromotions.DPD_ITEM_SELECT, requiredData.get(0), Constants.TEXT);
			textBox.enterText(CreatePromotions.TXT_ITEM, requiredData.get(1));
			foundation.threadWait(Constants.ONE_SECOND);
			textBox.enterText(CreatePromotions.TXT_ITEM, Keys.ENTER);
			textBox.enterText(CreatePromotions.TXT_AMOUNT, requiredData.get(2));
			textBox.enterText(CreatePromotions.TXT_TRANSACTION_MIN, requiredData.get(3));
			foundation.click(CreatePromotions.BTN_CREATE);

			foundation.waitforElement(EditPromotion.LBL_PROMPT_TITLE, Constants.SHORT_TIME);
			Assert.assertTrue(foundation.getText(EditPromotion.LBL_PROMPT_TITLE).contains(rstLocationData.get(CNLocation.POPUP_NAME)));
			foundation.click(EditPromotion.BTN_CONTINUE);
			foundation.click(EditPromotion.BTN_SAVE);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_OK);
			foundation.waitforElement(PromotionList.PAGE_TITLE, Constants.MEDIUM_TIME);

			textBox.enterText(PromotionList.TXT_SEARCH_PROMONAME, promotionName);
			foundation.click(PromotionList.BTN_SEARCH);
			foundation.click(PromotionList.TBL_COLUMN_NAME);
			foundation.click(PromotionList.LINK_EXPAND);
			foundation.waitforElement(PromotionList.LBL_ORG_NAME, Constants.SHORT_TIME);
			String orgName = foundation.getText(PromotionList.LBL_ORG_NAME);
			Assert.assertEquals(org.get(1), orgName);
			foundation.click(PromotionList.LINK_EXPAND);
			foundation.waitforElement(PromotionList.LBL_LOCATION_NAME, Constants.SHORT_TIME);
			String locName = foundation.getText(PromotionList.LBL_LOCATION_NAME);
			Assert.assertEquals(location.get(1), locName);

			

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		finally {
			// Reset the Data
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.DASHBOARD_URL, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));		
			textBox.enterText(PromotionList.TXT_SEARCH_PROMONAME, promotionName);
			foundation.click(PromotionList.BTN_SEARCH);
			foundation.doubleClick(PromotionList.TBL_COLUMN_NAME);

			// Basic Information Page
			foundation.waitforElement(CreatePromotions.LBL_BASIC_INFORMATION, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);

			// Filter Page
			dropdown.deSelectItem(CreatePromotions.DPD_ORGANIZATION, org.get(1), Constants.TEXT);
			textBox.enterText(CreatePromotions.DPD_ORG, org.get(0));
			foundation.threadWait(Constants.ONE_SECOND);
			textBox.enterText(CreatePromotions.DPD_ORG, Keys.ENTER);
			dropdown.selectItem(CreatePromotions.DPD_LOCATION, location.get(0), Constants.TEXT);
			foundation.waitforElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);

			// Details page
			List<String> actualData = Arrays
					.asList(rstLocationData.get(CNLocation.ACTUAL_DATA).split(Constants.DELIMITER_TILD));
			dropdown.deSelectItem(CreatePromotions.DPD_ITEM_SELECT, actualData.get(0), Constants.TEXT);
			textBox.enterText(CreatePromotions.TXT_ITEM, actualData.get(1));
			foundation.threadWait(Constants.ONE_SECOND);
			textBox.enterText(CreatePromotions.TXT_ITEM, Keys.ENTER);
			textBox.enterText(CreatePromotions.TXT_AMOUNT, actualData.get(2));
			textBox.enterText(CreatePromotions.TXT_TRANSACTION_MIN, actualData.get(3));
			foundation.click(CreatePromotions.BTN_CREATE);

			foundation.waitforElement(EditPromotion.LBL_PROMPT_TITLE, Constants.SHORT_TIME);
			Assert.assertTrue(foundation.getText(EditPromotion.LBL_PROMPT_TITLE)
					.contains(rstLocationData.get(CNLocation.POPUP_NAME)));
			foundation.click(EditPromotion.BTN_CONTINUE);
			foundation.click(EditPromotion.BTN_SAVE);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_OK);
			foundation.waitforElement(PromotionList.PAGE_TITLE, Constants.MEDIUM_TIME);
		}
	}

	@Test(description = "141830-This test validates the Item getting updated in Promotion Screen- Bundle Promotion")
	public void verifyItemInPromotionScreen() {
		final String CASE_NUM = "141830";

		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
		rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);
		
		String promotionType = rstLocationData.get(CNLocation.PROMOTION_TYPE);
		String gridName = rstLocationData.get(CNLocation.TAB_NAME);
		String locationName = rstLocationData.get(CNLocation.LOCATION_NAME);
		List<String> requiredData = Arrays.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> actualData = Arrays.asList(rstLocationData.get(CNLocation.ACTUAL_DATA).split(Constants.DELIMITER_TILD));
		String promotionName = strings.getRandomCharacter();
		try {
			browser.navigateURL(	propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
							propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			foundation.waitforElement(PromotionList.PAGE_TITLE, Constants.SHORT_TIME);
			
			// Basic Information Page
			foundation.click(PromotionList.BTN_CREATE);
			foundation.isDisplayed(CreatePromotions.LBL_CREATE_PROMOTION);
			foundation.isDisplayed(CreatePromotions.LBL_BASIC_INFORMATION);
			dropdown.selectItem(CreatePromotions.DPD_PROMO_TYPE, promotionType, Constants.TEXT);
			textBox.enterText(CreatePromotions.TXT_PROMO_NAME, promotionName);
			textBox.enterText(CreatePromotions.TXT_DISPLAY_NAME, promotionName);
			foundation.click(CreatePromotions.BTN_NEXT);

			// Filter Page
			foundation.threadWait(Constants.ONE_SECOND);
			textBox.enterText(CreatePromotions.DPD_ORG,propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			textBox.enterText(CreatePromotions.DPD_ORG, Keys.ENTER);
			dropdown.selectItem(CreatePromotions.DPD_LOCATION, locationName, Constants.TEXT);
			foundation.waitforElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);

			// Detail page
			dropdown.selectItem(CreatePromotions.DPD_DISCOUNT_BY, requiredData.get(0), Constants.TEXT);
			textBox.enterText(CreatePromotions.TXT_ITEM, actualData.get(0));
			foundation.threadWait(Constants.ONE_SECOND);
			textBox.enterText(CreatePromotions.TXT_ITEM, Keys.ENTER);
			foundation.click(CreatePromotions.BTN_CREATE);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_OK);

			// Updating the Item to Category
			textBox.enterText(PromotionList.TXT_SEARCH_PROMONAME, promotionName);
			foundation.click(PromotionList.BTN_SEARCH);
			foundation.doubleClick(PromotionList.TBL_COLUMN_NAME);
			foundation.waitforElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.threadWait(Constants.ONE_SECOND);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.waitforElement(CreatePromotions.DPD_DISCOUNT_BY, Constants.SHORT_TIME);
			dropdown.selectItem(CreatePromotions.DPD_DISCOUNT_BY, requiredData.get(2), Constants.TEXT);
			textBox.enterText(CreatePromotions.SEARCH_CATEGORY, actualData.get(1));
			foundation.threadWait(Constants.ONE_SECOND);
			textBox.enterText(CreatePromotions.SEARCH_CATEGORY, Keys.ENTER);
			foundation.click(CreatePromotions.BTN_CREATE);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_OK);

			// Verify Item correctly updated in Promotion Screen
			textBox.enterText(PromotionList.TXT_SEARCH_PROMONAME, promotionName);
			foundation.click(PromotionList.BTN_SEARCH);
			foundation.doubleClick(PromotionList.TBL_COLUMN_NAME);
			foundation.waitforElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.threadWait(Constants.TWO_SECOND);
			foundation.click(CreatePromotions.BTN_NEXT);

			foundation.threadWait(Constants.TWO_SECOND);
			String bundleOption = dropdown.getSelectedItem(CreatePromotions.DPD_DISCOUNT_BY);
			Assert.assertEquals(bundleOption, requiredData.get(0));
			String itemValue = dropdown.getSelectedItem(EditPromotion.DPD_ITEM);
			Assert.assertEquals(itemValue, actualData.get(0));
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_OK);
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		finally {
			// Expire Promotion
			promotionList.expirePromotion(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM),promotionName,gridName);
		}
	}

	@Test(description = "141831-This test validates the Category getting updated in Promotion Screen- Bundle Promotion",enabled=false)
	public void verifyCategoryInPromotionScreen() {
		final String CASE_NUM = "141831";

		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
		rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);
		
		navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
		navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
		foundation.waitforElement(PromotionList.PAGE_TITLE, Constants.SHORT_TIME);

		String promotionType = rstLocationData.get(CNLocation.PROMOTION_TYPE);
		String gridName = rstLocationData.get(CNLocation.TAB_NAME);
		String locationName = rstLocationData.get(CNLocation.LOCATION_NAME);
		List<String> requiredData = Arrays.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> actualData = Arrays.asList(rstLocationData.get(CNLocation.ACTUAL_DATA).split(Constants.DELIMITER_TILD));
		String promotionName = strings.getRandomCharacter();
		try {
			browser.navigateURL(	propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
							propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Basic Information Page
			foundation.click(PromotionList.BTN_CREATE);
			foundation.isDisplayed(CreatePromotions.LBL_CREATE_PROMOTION);
			foundation.isDisplayed(CreatePromotions.LBL_BASIC_INFORMATION);
			dropdown.selectItem(CreatePromotions.DPD_PROMO_TYPE, promotionType, Constants.TEXT);
			textBox.enterText(CreatePromotions.TXT_PROMO_NAME, promotionName);
			textBox.enterText(CreatePromotions.TXT_DISPLAY_NAME, promotionName);
			foundation.click(CreatePromotions.BTN_NEXT);

			// Filter Page
			foundation.threadWait(Constants.ONE_SECOND);
			textBox.enterText(CreatePromotions.DPD_ORG,	propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			textBox.enterText(CreatePromotions.DPD_ORG, Keys.ENTER);
			dropdown.selectItem(CreatePromotions.DPD_LOCATION, locationName, Constants.TEXT);
			foundation.waitforElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);

			// Detail page
			dropdown.selectItem(CreatePromotions.DPD_DISCOUNT_BY, requiredData.get(1), Constants.TEXT);
			textBox.enterText(CreatePromotions.SEARCH_CATEGORY, actualData.get(1));
			foundation.threadWait(Constants.ONE_SECOND);
			textBox.enterText(CreatePromotions.SEARCH_CATEGORY, Keys.ENTER);
			foundation.click(CreatePromotions.BTN_CREATE);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_OK);

			// Updating the Item to Category
			foundation.waitforElement(PromotionList.PAGE_TITLE, Constants.SHORT_TIME);
			textBox.enterText(PromotionList.TXT_SEARCH_PROMONAME, promotionName);
			foundation.click(PromotionList.BTN_SEARCH);
			foundation.doubleClick(PromotionList.TBL_COLUMN_NAME);
			foundation.waitforElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.threadWait(Constants.TWO_SECOND);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.waitforElement(CreatePromotions.DPD_DISCOUNT_BY, Constants.SHORT_TIME);
			dropdown.selectItem(CreatePromotions.DPD_DISCOUNT_BY, requiredData.get(0), Constants.TEXT);
			textBox.enterText(CreatePromotions.TXT_ITEM, actualData.get(0));
			foundation.threadWait(Constants.ONE_SECOND);
			textBox.enterText(CreatePromotions.TXT_ITEM, Keys.ENTER);
			foundation.click(CreatePromotions.BTN_CREATE);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_OK);

			// Verify Item correctly updated in Promotion Screen
			textBox.enterText(PromotionList.TXT_SEARCH_PROMONAME, promotionName);
			foundation.click(PromotionList.BTN_SEARCH);
			foundation.doubleClick(PromotionList.TBL_COLUMN_NAME);
			foundation.waitforElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.threadWait(Constants.TWO_SECOND);
			foundation.click(CreatePromotions.BTN_NEXT);

			foundation.threadWait(Constants.TWO_SECOND);
			String bundleOption = dropdown.getSelectedItem(CreatePromotions.DPD_DISCOUNT_BY);
			Assert.assertEquals(bundleOption, requiredData.get(0));
			String itemValue = dropdown.getSelectedItem(EditPromotion.DPD_ITEM);
			Assert.assertEquals(itemValue, actualData.get(0));
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_OK);

		} catch (Throwable exc) {

			TestInfra.failWithScreenShot(exc.toString());
		}
		finally {
			// Resetting the data
			promotionList.expirePromotion(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM),promotionName,gridName);
		}
	}

	@Test(description = "141785-To Verify Creating On-Screen promotion - Discount Type (Percentage)")
	public void verifyOnScreenPromotionDiscountTypePercentage() {
		final String CASE_NUM = "141785";
		

		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);

		String promotionType = rstLocationData.get(CNLocation.PROMOTION_TYPE);
		String gridName = rstLocationData.get(CNLocation.TAB_NAME);
		String locationName = rstLocationData.get(CNLocation.LOCATION_NAME);
		String organization = propertyFile.readPropertyFile(Configuration.CURRENT_ORG,FilePath.PROPERTY_CONFIG_FILE);

		List<String> requiredData = Arrays.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> actualData = Arrays.asList(rstLocationData.get(CNLocation.ACTUAL_DATA).split(Constants.DELIMITER_TILD));
		String promotionName = strings.getRandomCharacter();
		try {
			browser.navigateURL(	propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
							propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// select admin-promotion menu
			navigationBar.selectOrganization(organization);
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Create new promotion
			foundation.click(PromotionList.BTN_CREATE);
			createPromotions.newPromotion(promotionType, promotionName, promotionName, organization, locationName);
			foundation.click(CreatePromotions.BTN_NEXT);
			dropdown.selectItem(CreatePromotions.DPD_DISCOUNT_BY, requiredData.get(0), Constants.TEXT);
			textBox.enterText(CreatePromotions.TXT_ITEMS, requiredData.get(1));
			foundation.threadWait(Constants.ONE_SECOND);
			textBox.enterText(CreatePromotions.TXT_ITEMS, Keys.ENTER);
			dropdown.selectItem(CreatePromotions.DPD_DISCOUNT_TYPE, requiredData.get(2), Constants.TEXT);
			textBox.enterText(CreatePromotions.TXT_DISCOUNT_PERCENTAGE, requiredData.get(3));
			textBox.enterText(CreatePromotions.TXT_TRANSACTION_MIN, requiredData.get(4));
			checkBox.check(CreatePromotions.CHK_PROMO_RESTRICTION);
			assertTrue(foundation.isDisplayed(CreatePromotions.TXT_PER_TRANSACTION_LIMIT));
			checkBox.unCheck(CreatePromotions.CHK_PROMO_RESTRICTION);
			assertFalse(foundation.isDisplayed(CreatePromotions.TXT_PER_TRANSACTION_LIMIT));
			dropdown.selectItem(CreatePromotions.DPD_DISCOUNT_TIME, requiredData.get(5), Constants.TEXT);
			dropdown.selectItem(CreatePromotions.DPD_DURATION, requiredData.get(6), Constants.TEXT);
			foundation.threadWait(Constants.ONE_SECOND);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);

			// verify promotion details in popup
			List<String> popupFieldType = foundation.getTextofListElement(CreatePromotions.POP_UP_MESSAGES);
			List<String> popupField = null;
			popupField = Arrays.asList(popupFieldType.get(0).split(Constants.DELIMITER_COMMA));
			popupField = Arrays.asList(popupField.get(0).split(Constants.NEW_LINE));
			assertEquals(popupField.get(0), actualData.get(0));
			assertEquals(popupField.get(1), actualData.get(1));
			List<String> popupFieldArray = createPromotions.getPopUpData();
			String currentDate = dateAndTime.getDateAndTime(Constants.REGEX_MMDDYY, Constants.TIME_ZONE_INDIA);
			assertTrue(popupFieldArray.get(0).contains(promotionType));
			assertTrue(popupFieldArray.get(1).contains(promotionName));
			assertTrue(popupFieldArray.get(2).contains(promotionName));
			assertEquals(popupFieldArray.get(3), actualData.get(2));
			assertEquals(popupFieldArray.get(4), actualData.get(3));
			assertEquals(popupFieldArray.get(5), actualData.get(4));
			assertEquals(popupFieldArray.get(6), actualData.get(5));
			assertTrue(popupFieldArray.get(7).contains(currentDate));
			assertTrue(popupFieldArray.get(8).contains(currentDate));		
			foundation.click(CreatePromotions.BTN_OK);

			// navigate back to same promotion and verify all the details are populating as entered
			foundation.waitforElement(PromotionList.TXT_SEARCH_PROMONAME, Constants.MEDIUM_TIME);
			textBox.enterText(PromotionList.TXT_SEARCH_PROMONAME, promotionName);
			foundation.click(PromotionList.BTN_SEARCH);
			foundation.doubleClick(PromotionList.TBL_COLUMN_NAME);
			assertTrue(checkBox.isChecked(EditPromotion.CHK_ACTIVE));
			assertTrue(dropdown.getSelectedItem(CreatePromotions.DPD_PROMO_TYPE).equals(promotionType));
			assertTrue(textBox.getTextFromInput(CreatePromotions.TXT_PROMO_NAME).equals(promotionName));
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.threadWait(Constants.THREE_SECOND);
			assertTrue(dropdown.getSelectedItem(CreatePromotions.DPD_ORGANIZATION).equals(organization));
			assertTrue(dropdown.getSelectedItem(CreatePromotions.DPD_LOCATION).equals(locationName));
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.threadWait(Constants.ONE_SECOND);
			foundation.click(EditPromotion.BTN_UPDATE);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.MEDIUM_TIME);
			foundation.click(CreatePromotions.BTN_OK);

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		finally {
			// Resetting the data
			promotionList.expirePromotion(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM),promotionName,gridName);
		}
	}

	@Test(description = "141786-To Verify Creating On-Screen promotion - Discount by Category")
	public void verifyOnScreenPromotionDiscountByCategory() {
		final String CASE_NUM = "141786";

		browser.navigateURL(	propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
		login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
						propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);

		String promotionType = rstLocationData.get(CNLocation.PROMOTION_TYPE);
		String gridName = rstLocationData.get(CNLocation.TAB_NAME);
		String locationName = rstLocationData.get(CNLocation.LOCATION_NAME);
		String organization = propertyFile.readPropertyFile(Configuration.CURRENT_ORG,FilePath.PROPERTY_CONFIG_FILE);

		List<String> requiredData = Arrays.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> actualData = Arrays.asList(rstLocationData.get(CNLocation.ACTUAL_DATA).split(Constants.DELIMITER_TILD));
		String promotionName = strings.getRandomCharacter();
		try {

			// select admin-promotion menu
			navigationBar.selectOrganization(organization);
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Create new promotion
			foundation.click(PromotionList.BTN_CREATE);
			createPromotions.newPromotion(promotionType, promotionName, promotionName, organization, locationName);
			foundation.click(CreatePromotions.BTN_NEXT);
			dropdown.selectItem(CreatePromotions.DPD_DISCOUNT_BY, requiredData.get(0), Constants.TEXT);
			textBox.enterText(CreatePromotions.TXT_CATEGORYS, requiredData.get(1));
			foundation.threadWait(Constants.ONE_SECOND);
			textBox.enterText(CreatePromotions.TXT_CATEGORYS, Keys.ENTER);
			dropdown.selectItem(CreatePromotions.DPD_DISCOUNT_TYPE, requiredData.get(2), Constants.TEXT);
			textBox.enterText(CreatePromotions.TXT_AMOUNT, requiredData.get(3));
			textBox.enterText(CreatePromotions.TXT_TRANSACTION_MIN, requiredData.get(4));
			checkBox.check(CreatePromotions.CHK_PROMO_RESTRICTION);
			assertTrue(foundation.isDisplayed(CreatePromotions.TXT_PER_TRANSACTION_LIMIT));
			checkBox.unCheck(CreatePromotions.CHK_PROMO_RESTRICTION);
			assertFalse(foundation.isDisplayed(CreatePromotions.TXT_PER_TRANSACTION_LIMIT));
			dropdown.selectItem(CreatePromotions.DPD_DISCOUNT_TIME, requiredData.get(5), Constants.TEXT);
			dropdown.selectItem(CreatePromotions.DPD_DURATION, requiredData.get(6), Constants.TEXT);
			foundation.threadWait(Constants.ONE_SECOND);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);

			// verify promotion details in popup
			List<String> popupFieldType = foundation.getTextofListElement(CreatePromotions.POP_UP_MESSAGES);
			List<String> popupField = null;
			popupField = Arrays.asList(popupFieldType.get(0).split(Constants.DELIMITER_COMMA));
			popupField = Arrays.asList(popupField.get(0).split(Constants.NEW_LINE));
			assertEquals(popupField.get(0), actualData.get(0));
			assertEquals(popupField.get(1), actualData.get(1));
			List<String> popupFieldArray = createPromotions.getPopUpData();
			String currentDate = dateAndTime.getDateAndTime(Constants.REGEX_MMDDYY, Constants.TIME_ZONE_INDIA);
			assertTrue(popupFieldArray.get(0).contains(promotionType));
			assertTrue(popupFieldArray.get(1).contains(promotionName));
			assertTrue(popupFieldArray.get(2).contains(promotionName));
			assertEquals(popupFieldArray.get(3), actualData.get(2));
			assertEquals(popupFieldArray.get(4), actualData.get(3));
			assertEquals(popupFieldArray.get(5), actualData.get(4));
			assertEquals(popupFieldArray.get(6), actualData.get(5));
			assertTrue(popupFieldArray.get(7).contains(currentDate));
			assertTrue(popupFieldArray.get(8).contains(currentDate));
			foundation.click(CreatePromotions.BTN_OK);

			// navigate back to same promotion and verify all the details are populating as entered
			foundation.waitforElement(PromotionList.PAGE_TITLE, Constants.SHORT_TIME);
			textBox.enterText(PromotionList.TXT_SEARCH_PROMONAME, promotionName);
			foundation.click(PromotionList.BTN_SEARCH);
			foundation.doubleClick(PromotionList.TBL_COLUMN_NAME);
			assertTrue(checkBox.isChecked(EditPromotion.CHK_ACTIVE));
			assertTrue(dropdown.getSelectedItem(CreatePromotions.DPD_PROMO_TYPE).equals(promotionType));
			assertTrue(textBox.getTextFromInput(CreatePromotions.TXT_PROMO_NAME).equals(promotionName));
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.waitforElement(CreatePromotions.DPD_ORGANIZATION, Constants.SHORT_TIME);
			assertTrue(dropdown.getSelectedItem(CreatePromotions.DPD_ORGANIZATION).equals(organization));
			assertTrue(dropdown.getSelectedItem(CreatePromotions.DPD_LOCATION).equals(locationName));
			foundation.threadWait(Constants.TWO_SECOND);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.threadWait(Constants.TWO_SECOND);
			foundation.click(EditPromotion.BTN_UPDATE);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_OK);
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		finally {
			// Resetting the data
			promotionList.expirePromotion(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM),promotionName,gridName);
		}
	}

	@Test(description = "141776-To Verify Creating Tender Discount promotion (Tender Type set as Account)")
	public void verifyTenderDiscountPromo() {
		final String CASE_NUM = "141776";

		browser.navigateURL(	propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
		login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
				propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);

		String promotionName = strings.getRandomCharacter();
		String promotionType = rstLocationData.get(CNLocation.PROMOTION_TYPE);
		String locationName = rstLocationData.get(CNLocation.LOCATION_NAME);
		// String requiredData = rstLocationData.get(CNLocation.REQUIRED_DATA);
		String gridName = rstLocationData.get(CNLocation.TAB_NAME);
		List<String> requiredData = Arrays.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		
		try {
			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// validate UI of promotion list page
			assertTrue(foundation.isDisplayed(PromotionList.TXT_SEARCH_PROMONAME));
			assertTrue(foundation.isDisplayed(PromotionList.CALENDER_DATE_RANGE));
			assertTrue(foundation.isDisplayed(PromotionList.DPD_LOCATION));
			assertTrue(foundation.isDisplayed(PromotionList.DPD_STATUS));
			assertTrue(foundation.isDisplayed(PromotionList.DPD_PROMOTYPE));
			assertEquals(foundation.getText(PromotionList.LBL_SEARCH), requiredData.get(0));
			assertEquals(foundation.getText(PromotionList.LBL_CALENDER_DATE_RANGE), requiredData.get(1));
			assertEquals(foundation.getText(PromotionList.LBL_LOCATION), requiredData.get(2));
			assertEquals(foundation.getText(PromotionList.LBL_STATUS), requiredData.get(3));
			assertEquals(foundation.getText(PromotionList.LBL_PROMOTYPE), requiredData.get(4));

			// Creating New Promotion
			foundation.click(PromotionList.BTN_CREATE);

			// validate UI of enter promotion basics page
			assertTrue(foundation.isDisplayed(CreatePromotions.DPD_PROMO_TYPE));
			assertTrue(foundation.isDisplayed(CreatePromotions.TXT_PROMO_NAME));
			assertTrue(foundation.isDisplayed(CreatePromotions.BTN_NEXT));
			assertTrue(foundation.isDisplayed(CreatePromotions.BTN_CANCEL));
			assertEquals(foundation.getText(CreatePromotions.LBL_BASICINFO), requiredData.get(5));
			assertEquals(foundation.getText(CreatePromotions.LBL_ENTER_BASICINFO), requiredData.get(6));

			// provide basic info and navigate to next
			dropdown.selectItem(CreatePromotions.DPD_PROMO_TYPE, promotionType, Constants.TEXT);
			textBox.enterText(CreatePromotions.TXT_PROMO_NAME, promotionName);
			foundation.click(CreatePromotions.BTN_NEXT);

			foundation.threadWait(Constants.ONE_SECOND);

			// validate UI of enter promotion filter page
			assertTrue(foundation.isDisplayed(CreatePromotions.DPD_ORG));
			assertTrue(foundation.isDisplayed(CreatePromotions.DPD_LOCATION));
			assertTrue(foundation.isDisplayed(CreatePromotions.BTN_NEXT));
			assertEquals(foundation.getText(CreatePromotions.LBL_FILTER), requiredData.get(7));
			assertEquals(foundation.getText(CreatePromotions.LBL_SELECT_CRITERIA), requiredData.get(8));

			// choose promotion filter
			textBox.enterText(CreatePromotions.DPD_ORG,	propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			textBox.enterText(CreatePromotions.DPD_ORG, Keys.ENTER);
			dropdown.selectItem(CreatePromotions.DPD_LOCATION, locationName, Constants.TEXT);
			foundation.waitforElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.threadWait(Constants.ONE_SECOND);

			// validate UI of enter promotion filter page
			assertTrue(foundation.isDisplayed(CreatePromotions.MULTI_SELECT_TENDER_TYPES));
			assertTrue(foundation.isDisplayed(CreatePromotions.DPD_DISCOUNT_TYPE));
			assertTrue(foundation.isDisplayed(CreatePromotions.DPD_APPLY_DISCOUNT_TO));
			assertTrue(foundation.isDisplayed(CreatePromotions.TXT_TRANSACTION_MIN));
			assertTrue(foundation.isDisplayed(CreatePromotions.DPD_DISCOUNT_TIME));
			assertTrue(foundation.isDisplayed(CreatePromotions.BTN_CREATE));
			assertEquals(foundation.getText(CreatePromotions.LBL_DETAILS), requiredData.get(9));
			assertEquals(foundation.getText(CreatePromotions.LBL_SET_PROMO_DETAILS), requiredData.get(10));

			dropdown.selectItem(CreatePromotions.MULTI_SELECT_TENDER_TYPES, requiredData.get(11), Constants.TEXT);
			foundation.threadWait(Constants.TWO_SECOND);
			foundation.click(CreatePromotions.BTN_NEXT);

			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_OK);

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		finally {
			// Resetting the data
			promotionList.expirePromotion(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM),promotionName,gridName);
		}
	}

	@Test(description = "141780-To Verify Creating Tender Discount promotion (Tender Type set as Credit)")
	public void verifyTenderDiscountPromotions() {
		final String CASE_NUM = "141780";
		
		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);

		String promotionType = rstLocationData.get(CNLocation.PROMOTION_TYPE);
		String locationName = rstLocationData.get(CNLocation.LOCATION_NAME);
		String gridName = rstLocationData.get(CNLocation.TAB_NAME);
		String promotionName = strings.getRandomCharacter();
		try {
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			
			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Creating New Promotion
			foundation.click(PromotionList.BTN_CREATE);
			foundation.isDisplayed(CreatePromotions.LBL_CREATE_PROMOTION);
			dropdown.selectItem(CreatePromotions.DPD_PROMO_TYPE, promotionType, Constants.TEXT);
			String basicInfoPageTitle = foundation.getText(CreatePromotions.LBL_PAGE_TITLE);
			assertTrue(basicInfoPageTitle.equals(rstLocationData.get(CNLocation.PROMOTION_TYPE)));
			
			textBox.enterText(CreatePromotions.TXT_PROMO_NAME, promotionName);
			foundation.click(CreatePromotions.BTN_NEXT);
			String filtersPageTitle = foundation.getText(CreatePromotions.LBL_PAGE_TITLE);
			assertTrue(filtersPageTitle.equals(rstLocationData.get(CNLocation.PROMOTION_TYPE)));
			textBox.enterText(CreatePromotions.DPD_ORG,propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			textBox.enterText(CreatePromotions.DPD_ORG, Keys.ENTER);
			dropdown.selectItem(CreatePromotions.DPD_LOCATION, locationName, Constants.TEXT);
			foundation.waitforElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);

			List<String> requiredData = Arrays.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			foundation.click(CreatePromotions.BTN_NEXT);
			dropdown.selectItem(CreatePromotions.MULTI_SELECT_TENDER_TYPES, requiredData.get(0), Constants.TEXT);
			dropdown.selectItem(CreatePromotions.DPD_DISCOUNT_TYPE, requiredData.get(1), Constants.TEXT);
			dropdown.selectItem(CreatePromotions.DPD_APPLY_DISCOUNT_TO, requiredData.get(2), Constants.TEXT);
			textBox.enterText(CreatePromotions.TXT_AMOUNT, requiredData.get(3));
			textBox.enterText(CreatePromotions.TXT_TRANSACTION_MIN, requiredData.get(4));
			dropdown.selectItem(CreatePromotions.DPD_DISCOUNT_TIME, requiredData.get(5), Constants.TEXT);

			foundation.threadWait(Constants.TWO_SECOND);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);

			List<String> popupFieldType = foundation.getTextofListElement(CreatePromotions.POP_UP_MESSAGES);
			List<String> popupField = null;
			popupField = Arrays.asList(popupFieldType.get(0).split(Constants.DELIMITER_COMMA));
			popupField = Arrays.asList(popupField.get(0).split(Constants.NEW_LINE));
			List<String> actualData = Arrays.asList(rstLocationData.get(CNLocation.ACTUAL_DATA).split(Constants.DELIMITER_TILD));
			assertEquals(popupField.get(0), actualData.get(0));
			assertEquals(popupField.get(1), actualData.get(1));
			List<String> popupFieldArray = createPromotions.getPopUpData();
			String currentDate = dateAndTime.getDateAndTime(Constants.REGEX_MMDDYY, Constants.TIME_ZONE_INDIA);

			assertTrue(popupFieldArray.get(0).contains(promotionType));
			assertTrue(popupFieldArray.get(1).contains(promotionName));
			assertEquals(popupFieldArray.get(2), actualData.get(2));
			assertEquals(popupFieldArray.get(3), actualData.get(3));
			assertEquals(popupFieldArray.get(4), actualData.get(4));
			assertEquals(popupFieldArray.get(5), actualData.get(5));
			assertEquals(popupFieldArray.get(6), actualData.get(6));
			assertTrue(popupFieldArray.get(7).contains(currentDate));
			assertTrue(popupFieldArray.get(8).contains(currentDate));

			foundation.click(CreatePromotions.BTN_OK);

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		finally {
			// Resetting the data
			promotionList.expirePromotion(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM),promotionName,gridName);
		}
	}

	@Test(description = "141774 - SOS-7519 - Verify Create Promotion page display only active location when Org  filter is selected")
	public void verifyActiveLocationPromotions() {
		final String CASE_NUM = "141774";
		
		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);

		String promotionName = rstLocationData.get(CNLocation.PROMOTION_NAME);
		String promotionType = rstLocationData.get(CNLocation.PROMOTION_TYPE);
		String locationName = rstLocationData.get(CNLocation.LOCATION_NAME);
		String requiredData = rstLocationData.get(CNLocation.REQUIRED_DATA);
		String gridName = rstLocationData.get(CNLocation.TAB_NAME);
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			
			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Creating New Promotion
			foundation.click(PromotionList.BTN_CREATE);

			dropdown.selectItem(CreatePromotions.DPD_PROMO_TYPE, promotionType, Constants.TEXT);
			textBox.enterText(CreatePromotions.TXT_PROMO_NAME, promotionName);
			foundation.click(CreatePromotions.BTN_NEXT);
			textBox.enterText(CreatePromotions.DPD_ORG,
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			textBox.enterText(CreatePromotions.DPD_ORG, Keys.ENTER);

			dropdown.selectItem(CreatePromotions.DPD_LOCATION, locationName, Constants.TEXT);
			foundation.waitforElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);

			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.waitforElement(CreatePromotions.MULTI_SELECT_TENDER_TYPES, Constants.SHORT_TIME);
			dropdown.selectItem(CreatePromotions.MULTI_SELECT_TENDER_TYPES, requiredData, Constants.TEXT);

			foundation.threadWait(Constants.TWO_SECOND);
			foundation.click(CreatePromotions.BTN_NEXT);

			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_OK);
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		finally {
			// Resetting the data
			promotionList.expirePromotion(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM),promotionName,gridName);
		}
	}

	@Test(description = "141804 - Verify if the select Category field loads properly for Onscreen promotions when user choose the filter ORG")
	public void verifyOnscreenPromotionsCategory() {
		final String CASE_NUM = "141804";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);

		String promotionType = rstLocationData.get(CNLocation.PROMOTION_TYPE);
		String locationName = rstLocationData.get(CNLocation.LOCATION_NAME);
		String gridName = rstLocationData.get(CNLocation.TAB_NAME);
		String promotionName = strings.getRandomCharacter();
		try {
			browser.navigateURL(	propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Creating New Promotion
			foundation.click(PromotionList.BTN_CREATE);
			foundation.isDisplayed(CreatePromotions.LBL_CREATE_PROMOTION);
			dropdown.selectItem(CreatePromotions.DPD_PROMO_TYPE, promotionType, Constants.TEXT);
			String basicInfoPageTitle = foundation.getText(CreatePromotions.LBL_PAGE_TITLE);
			assertTrue(basicInfoPageTitle.equals(rstLocationData.get(CNLocation.PROMOTION_TYPE)));
			
			textBox.enterText(CreatePromotions.TXT_PROMO_NAME, promotionName);
			String displayName = strings.getRandomCharacter();
			textBox.enterText(CreatePromotions.TXT_DISPLAY_NAME, displayName);
			foundation.click(CreatePromotions.BTN_NEXT);

			String filtersPageTitle = foundation.getText(CreatePromotions.LBL_PAGE_TITLE);
			assertTrue(filtersPageTitle.equals(promotionType));

			textBox.enterText(CreatePromotions.DPD_ORG,	propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			textBox.enterText(CreatePromotions.DPD_ORG, Keys.ENTER);

			dropdown.selectItem(CreatePromotions.DPD_LOCATION, locationName, Constants.TEXT);
			foundation.waitforElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);
			List<String> category = Arrays.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			foundation.waitforElement(CreatePromotions.DPD_DISCOUNT_BY, Constants.SHORT_TIME);
			dropdown.selectItem(CreatePromotions.DPD_DISCOUNT_BY, category.get(0), Constants.TEXT);

			foundation.click(CreatePromotions.SEARCH_CATEGORY);
			textBox.enterText(CreatePromotions.SEARCH_CATEGORY, category.get(1));
			foundation.threadWait(Constants.TWO_SECOND);
			textBox.enterText(CreatePromotions.SEARCH_CATEGORY, Keys.ENTER);

			String categorySelected = dropdown.getSelectedItem(CreatePromotions.DPD_CATEGORY);
			assertTrue(categorySelected.equals(category.get(1)));

			foundation.threadWait(Constants.TWO_SECOND);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_OK);
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		finally {
			// Resetting the data
			promotionList.expirePromotion(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM),promotionName,gridName);
		}
	}

	@Test(description = "118619-Verify to create a promotion with Promotion Type as Tender Discount")
	public void verifyCreateTenderDiscountPromo() {
		final String CASE_NUM = "118619";
		
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);
		rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);
		
		String promotionType = rstLocationData.get(CNLocation.PROMOTION_TYPE);
		String locationName = rstLocationData.get(CNLocation.LOCATION_NAME);
		String gridName = rstLocationData.get(CNLocation.TAB_NAME);
		List<String> requiredData = Arrays.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> actualData = Arrays.asList(rstLocationData.get(CNLocation.ACTUAL_DATA).split(Constants.DELIMITER_TILD));

		String promotionName = strings.getRandomCharacter();
		List<String> locationList = foundation.getTextofListElement(LocationList.LINK_LOCATION_LIST);
		List<String> menu = Arrays.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		try {
			
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
							propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			
			navigationBar.navigateToMenuItem(menu.get(0));
			foundation.waitforElement(UserRoles.LBL_USER_LIST, Constants.SHORT_TIME);
			textBox.enterText(UserRoles.TXT_SEARCH_FILTER,propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(userRoles.getRowByText(rstLocationSummaryData.get(CNLocationSummary.CONTACT_EMAIL)));
			foundation.waitforElement(UserRoles.LBL_VIEW_ROLE, Constants.SHORT_TIME);
			foundation.click(UserList.LNK_ORG_REMOVE_ALL);
			List<String> orgs = Arrays.asList(rstLocationData.get(CNLocation.COLUMN_VALUE).split(Constants.DELIMITER_TILD));
			userList.selectOrgs(UserList.DPD_ORG, orgs);
			foundation.threadWait(Constants.TWO_SECOND);
			login.logout();

			browser.navigateURL(	propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));			
			
			// select admin-promotion menu
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menu.get(1));

			// navigate to create new promo page
			foundation.click(PromotionList.BTN_CREATE);
			foundation.isDisplayed(CreatePromotions.LBL_CREATE_PROMOTION);
			dropdown.selectItem(CreatePromotions.DPD_PROMO_TYPE, promotionType, Constants.TEXT);

			// select promotion and navigate to next
			textBox.enterText(CreatePromotions.TXT_PROMO_NAME, promotionName);
			foundation.click(CreatePromotions.BTN_NEXT);
			
			foundation.waitforElement(CreatePromotions.DPD_ORG, Constants.SHORT_TIME);
			List<String> orgData = dropdown.getAllItems(CreatePromotions.DPD_ORGANIZATION);
			for (int iter = 0; iter < orgData.size(); iter++) {
				Assert.assertTrue(orgData.get(iter).contains(orgs.get(iter)));
			}
			textBox.enterText(CreatePromotions.DPD_ORG,	propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			textBox.enterText(CreatePromotions.DPD_ORG, Keys.ENTER);
			for (String location : locationList) {
				assertTrue(dropdown.verifyItemPresent(CreatePromotions.DPD_LOCATION, location));
			}
			dropdown.selectItem(CreatePromotions.DPD_LOCATION, locationName, Constants.TEXT);
			foundation.waitforElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);

			// select promotion details
			dropdown.selectItem(CreatePromotions.MULTI_SELECT_TENDER_TYPES, requiredData.get(0), Constants.TEXT);
			dropdown.selectItem(CreatePromotions.DPD_DISCOUNT_TYPE, requiredData.get(1), Constants.TEXT);
			dropdown.selectItem(CreatePromotions.DPD_APPLY_DISCOUNT_TO, requiredData.get(2), Constants.TEXT);
			textBox.enterText(CreatePromotions.TXT_AMOUNT, requiredData.get(3));
			textBox.enterText(CreatePromotions.TXT_TRANSACTION_MIN, requiredData.get(4));
			dropdown.selectItem(CreatePromotions.DPD_DISCOUNT_TIME, requiredData.get(5), Constants.TEXT);
			createPromotions.recurringDay();
			foundation.threadWait(Constants.TWO_SECOND);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);

			// verify promotion details
			List<String> popupFieldType = foundation.getTextofListElement(CreatePromotions.POP_UP_MESSAGES);
			List<String> popupField = null;
			popupField = Arrays.asList(popupFieldType.get(0).split(Constants.DELIMITER_COMMA));
			popupField = Arrays.asList(popupField.get(0).split(Constants.NEW_LINE));
			assertEquals(popupField.get(0), actualData.get(0));
			assertEquals(popupField.get(1), actualData.get(1));
			List<String> popupFieldArray = createPromotions.getPopUpData();
			String currentDate = dateAndTime.getDateAndTime(Constants.REGEX_MMDDYY, Constants.TIME_ZONE_INDIA);
			assertTrue(popupFieldArray.get(0).contains(promotionType));
			assertTrue(popupFieldArray.get(1).contains(promotionName));
			assertEquals(popupFieldArray.get(2), actualData.get(2));
			assertEquals(popupFieldArray.get(3), actualData.get(3));
			assertEquals(popupFieldArray.get(4), actualData.get(4));
			assertEquals(popupFieldArray.get(5), actualData.get(5));
			assertEquals(popupFieldArray.get(6), actualData.get(6));
			assertTrue(popupFieldArray.get(7).contains(currentDate));
			assertTrue(popupFieldArray.get(8).contains(currentDate));			
			foundation.click(CreatePromotions.BTN_OK);
			
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		finally {
			// Resetting the data
			promotionList.expirePromotion(menu.get(1),promotionName,gridName);
		}
	}

	@Test(description = "141820-Verify Operator will update the existing Promotion with Same Org/Location")
	public void verifyUpdatePromotion() {
		final String CASE_NUM = "141820";

		browser.navigateURL(	propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
		login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
				propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);

		String promotionType = rstLocationData.get(CNLocation.PROMOTION_TYPE);
		String gridName = rstLocationData.get(CNLocation.TAB_NAME);
		List<String> locationName = Arrays.asList(rstLocationData.get(CNLocation.LOCATION_NAME).split(Constants.DELIMITER_TILD));
		List<String> requiredData = Arrays.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		String promotionName = strings.getRandomCharacter() + strings.getRandomCharacter();
		try {
			// select admin-promotion menu
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Create new promotion
			foundation.click(PromotionList.BTN_CREATE);
			createPromotions.newPromotion(promotionType, promotionName, promotionName,
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE),
					locationName.get(0));
			
			foundation.threadWait(Constants.TWO_SECOND);
			foundation.waitforClikableElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);
			dropdown.selectItem(CreatePromotions.MULTI_SELECT_TENDER_TYPES, requiredData.get(0), Constants.TEXT);
			foundation.threadWait(Constants.ONE_SECOND);
			foundation.waitforClikableElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);
			foundation.waitforClikableElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_OK);

			// navigate to same promotion and validate update promotion
			foundation.waitforElement(PromotionList.PAGE_TITLE, Constants.MEDIUM_TIME);
			textBox.enterText(PromotionList.TXT_SEARCH_PROMONAME, promotionName);
			foundation.click(PromotionList.BTN_SEARCH);
			foundation.doubleClick(PromotionList.TBL_COLUMN_NAME);
			assertTrue(checkBox.isChecked(EditPromotion.CHK_ACTIVE));
			foundation.waitforClikableElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.threadWait(Constants.TWO_SECOND);
			assertTrue(foundation.getText(CreatePromotions.LBL_PAGE_TITLE)	.equals(rstLocationData.get(CNLocation.PROMOTION_TYPE)));
			assertTrue(dropdown.getSelectedItem(CreatePromotions.DPD_ORGANIZATION)
							.equals(propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE)));
			assertTrue(dropdown.getSelectedItem(CreatePromotions.DPD_LOCATION).equals(locationName.get(0)));
			foundation.click(EditPromotion.ICON_CLR_LOCATION);
			dropdown.selectItem(CreatePromotions.DPD_LOCATION, locationName.get(1), Constants.TEXT);
			foundation.threadWait(Constants.TWO_SECOND);
			foundation.waitforClikableElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);
			
			// select promotion details
			dropdown.selectItem(CreatePromotions.MULTI_SELECT_TENDER_TYPES, requiredData.get(0), Constants.TEXT);
			dropdown.selectItem(CreatePromotions.DPD_DISCOUNT_TYPE, requiredData.get(1), Constants.TEXT);
			foundation.threadWait(Constants.ONE_SECOND);
			foundation.waitforClikableElement(EditPromotion.BTN_UPDATE, Constants.SHORT_TIME);
			foundation.click(EditPromotion.BTN_UPDATE);
			foundation.click(EditPromotion.BTN_CONTINUE);
			foundation.waitforClikableElement(EditPromotion.BTN_SAVE, Constants.SHORT_TIME);
			foundation.click(EditPromotion.BTN_SAVE);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);
			foundation.waitforClikableElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_OK);
			foundation.waitforElement(PromotionList.PAGE_TITLE, Constants.LONG_TIME);

			textBox.enterText(PromotionList.TXT_SEARCH_PROMONAME, promotionName);
			foundation.click(PromotionList.BTN_SEARCH);
			foundation.click(PromotionList.LINK_EXPAND);
			foundation.waitforElement(PromotionList.LBL_ORG_NAME, Constants.SHORT_TIME);
			String orgName = foundation.getText(PromotionList.LBL_ORG_NAME);
			Assert.assertEquals(propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE), orgName);
			foundation.click(PromotionList.LINK_EXPAND);
			foundation.waitforElement(PromotionList.LBL_LOCATION_NAME, Constants.SHORT_TIME);
			String locName = foundation.getText(PromotionList.LBL_LOCATION_NAME);
			Assert.assertEquals(locationName.get(1), locName);
			foundation.threadWait(Constants.ONE_SECOND);
			
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		finally {
			// Resetting the data
			promotionList.expirePromotion(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM),promotionName,gridName);
		}
	}

	@Test(description = "118621 - Verify to create a promotion with Promotion Type as Bundle")
	public void verifyBundlePromotion() {
		final String CASE_NUM = "118621";
		
		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);
		rstUserRolesData = dataBase.getUserRolesData(Queries.USER_ROLES, CASE_NUM);

		String promotionName = strings.getRandomCharacter();
		String displayName = strings.getRandomCharacter();
		String promotionType = rstLocationData.get(CNLocation.PROMOTION_TYPE);
		String locationName = rstLocationData.get(CNLocation.LOCATION_NAME);
		String gridName = rstLocationData.get(CNLocation.TAB_NAME);
		List<String> requiredData = Arrays.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> menu = Arrays.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		try {
			browser.navigateURL(	propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
							propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			
			navigationBar.navigateToMenuItem(menu.get(0));
			textBox.enterText(UserList.TXT_FILTER, rstUserRolesData.get(CNUserRoles.ROLE_NAME));
			foundation.click(userList.objRoleName(rstUserRolesData.get(CNUserRoles.ROLE_NAME)));
			foundation.click(UserList.LNK_ORG_REMOVE_ALL);
			List<String> orgs = Arrays.asList(rstLocationData.get(CNLocation.COLUMN_VALUE).split(Constants.DELIMITER_TILD));
			dropdown.selectItem(UserList.DPD_ORG, orgs.get(0), Constants.TEXT);
			dropdown.selectItem(UserList.DPD_ORG, orgs.get(1), Constants.TEXT);
			foundation.threadWait(Constants.TWO_SECOND);
			dropdown.selectItem(UserList.TXT_SEARCH_LOC, rstLocationData.get(CNLocation.LOCATIONLIST_DPDN_VALUE),Constants.TEXT);
			foundation.threadWait(Constants.TWO_SECOND);
			foundation.click(UserList.BTN_UPDATE_USER);
			foundation.waitforElement(UserList.LBL_USER_LIST, Constants.SHORT_TIME);
			foundation.threadWait(Constants.TWO_SECOND);
			login.logout();

			// operator user
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
							propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			List<String> locationNames = foundation.getTextofListElement(CreatePromotions.LINK_LOCATION_LIST);
			navigationBar.navigateToMenuItem(menu.get(1));

			// New Promotion
			foundation.click(PromotionList.BTN_CREATE);
			dropdown.selectItem(CreatePromotions.DPD_PROMO_TYPE, promotionType, Constants.TEXT);
			textBox.enterText(CreatePromotions.TXT_PROMO_NAME, promotionName);
			textBox.enterText(CreatePromotions.TXT_DISPLAY_NAME, displayName);
			foundation.click(CreatePromotions.BTN_NEXT);
			
			// Filter Page
			foundation.threadWait(Constants.ONE_SECOND);
			createPromotions.verifyOrgField(orgs);
			textBox.enterText(CreatePromotions.DPD_ORG, requiredData.get(0));
			textBox.enterText(CreatePromotions.DPD_ORG, Keys.ENTER);
			//foundation.click(CreatePromotions.DPD_LOC);
			
			for (String location : locationNames) {
				assertTrue(dropdown.verifyItemPresent(CreatePromotions.DPD_LOCATION, location));
			}
			dropdown.selectItem(CreatePromotions.DPD_LOCATION, locationName, Constants.TEXT);
			foundation.threadWait(Constants.TWO_SECOND);
			foundation.click(CreatePromotions.BTN_NEXT);
			
			// Promotion Details Page
			List<String> discountType = Arrays.asList(rstLocationData.get(CNLocation.PRODUCT_NAME).split(Constants.DELIMITER_TILD));
			dropdown.selectItem(CreatePromotions.DPD_DISCOUNT_BY, discountType.get(0), Constants.TEXT);
			textBox.enterText(CreatePromotions.SEARCH_CATEGORY, discountType.get(1));
			foundation.threadWait(Constants.ONE_SECOND);
			textBox.enterText(CreatePromotions.SEARCH_CATEGORY, Keys.ENTER);
			foundation.click(CreatePromotions.BTN_CREATE);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);
			
			// popup message validations
			List<String> popupFieldType = foundation.getTextofListElement(CreatePromotions.POP_UP_MESSAGES);
			List<String> popupField = null;
			popupField = Arrays.asList(popupFieldType.get(0).split(Constants.DELIMITER_COMMA));
			popupField = Arrays.asList(popupField.get(0).split(Constants.NEW_LINE));

			List<String> actualData = Arrays.asList(rstLocationData.get(CNLocation.ACTUAL_DATA).split(Constants.DELIMITER_TILD));
			assertEquals(popupField.get(0), actualData.get(0));
			assertEquals(popupField.get(1), actualData.get(1));

			List<String> popupFieldArray = createPromotions.getPopUpData();
			String currentDate = dateAndTime.getDateAndTime(Constants.REGEX_MMDDYY, Constants.TIME_ZONE_INDIA);

			assertTrue(popupFieldArray.get(0).contains(promotionType));
			assertTrue(popupFieldArray.get(1).contains(promotionName));
			assertTrue(popupFieldArray.get(2).contains(displayName));
			assertEquals(popupFieldArray.get(3), actualData.get(3));
			assertEquals(popupFieldArray.get(4), actualData.get(4));
			assertEquals(popupFieldArray.get(5), actualData.get(5));
			assertEquals(popupFieldArray.get(6), actualData.get(6));
			assertTrue(popupFieldArray.get(7).contains(currentDate));
			assertTrue(popupFieldArray.get(8).contains(currentDate));

			foundation.click(CreatePromotions.BTN_OK);
			
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		finally {
			// Resetting the data
			promotionList.expirePromotion(menu.get(1),promotionName,gridName);
		}
	}

	@Test(description = "141803 - Veirfy if the select item field loads properly for Onscreen promotions when user chooses the ORG filter")
	public void verifyItemFieldOption() {
		final String CASE_NUM = "141803";
		
		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);

		String promotionName = strings.getRandomCharacter();
		String displayName = strings.getRandomCharacter();
		String promotionType = rstLocationData.get(CNLocation.PROMOTION_TYPE);
		String locationName = rstLocationData.get(CNLocation.LOCATION_NAME);
		String gridName = rstLocationData.get(CNLocation.TAB_NAME);

		List<String> requiredData = Arrays
				.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// New Promotion
			foundation.click(PromotionList.BTN_CREATE);
			foundation.isDisplayed(CreatePromotions.LBL_CREATE_PROMOTION);

			dropdown.selectItem(CreatePromotions.DPD_PROMO_TYPE, promotionType, Constants.TEXT);
			String basicInfoPageTitle = foundation.getText(CreatePromotions.LBL_PAGE_TITLE);
			assertTrue(basicInfoPageTitle.equals(rstLocationData.get(CNLocation.PROMOTION_TYPE)));

			textBox.enterText(CreatePromotions.TXT_PROMO_NAME, promotionName);
			textBox.enterText(CreatePromotions.TXT_DISPLAY_NAME, displayName);
			foundation.click(CreatePromotions.BTN_NEXT);

			String filtersPageTitle = foundation.getText(CreatePromotions.LBL_PAGE_TITLE);
			assertTrue(filtersPageTitle.equals(rstLocationData.get(CNLocation.PROMOTION_TYPE)));

			textBox.enterText(CreatePromotions.DPD_ORG, requiredData.get(0));
			textBox.enterText(CreatePromotions.DPD_ORG, Keys.ENTER);
			dropdown.selectItem(CreatePromotions.DPD_ORGANIZATION, requiredData.get(3), Constants.TEXT);
			dropdown.selectItem(CreatePromotions.DPD_LOCATION, locationName, Constants.TEXT);
			foundation.waitforElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);

			foundation.click(CreatePromotions.BTN_NEXT);
			dropdown.selectItem(CreatePromotions.DPD_DISCOUNT_BY, requiredData.get(1), Constants.TEXT);
			textBox.enterText(CreatePromotions.TXT_ITEM, requiredData.get(2));
			foundation.threadWait(Constants.ONE_SECOND);
			textBox.enterText(CreatePromotions.TXT_ITEM, Keys.ENTER);

			foundation.threadWait(Constants.TWO_SECOND);
			String actualValue = dropdown.getSelectedItem(CreatePromotions.DPD_ITEM_SELECT);
			assertEquals(actualValue, requiredData.get(2));

			textBox.enterText(CreatePromotions.TXT_AMOUNT, requiredData.get(4));
			textBox.enterText(CreatePromotions.TXT_TRANSACTION_MIN, requiredData.get(5));
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);

			// popup validations
			List<String> popupFieldType = foundation.getTextofListElement(CreatePromotions.POP_UP_MESSAGES);
			List<String> popupField = null;
			popupField = Arrays.asList(popupFieldType.get(0).split(Constants.DELIMITER_COMMA));
			popupField = Arrays.asList(popupField.get(0).split(Constants.NEW_LINE));

			List<String> actualData = Arrays.asList(rstLocationData.get(CNLocation.ACTUAL_DATA).split(Constants.DELIMITER_TILD));
			assertEquals(popupField.get(0), actualData.get(0));
			assertEquals(popupField.get(1), actualData.get(1));

			List<String> popupFieldArray = createPromotions.getPopUpData();
			String currentDate = dateAndTime.getDateAndTime(Constants.REGEX_MMDDYY, Constants.TIME_ZONE_INDIA);

			assertTrue(popupFieldArray.get(0).contains(promotionType));
			assertTrue(popupFieldArray.get(1).contains(promotionName));
			assertTrue(popupFieldArray.get(2).contains(displayName));
			assertEquals(popupFieldArray.get(3), actualData.get(3));
			assertEquals(popupFieldArray.get(4), actualData.get(4));
			assertEquals(popupFieldArray.get(5), actualData.get(5));
			assertEquals(popupFieldArray.get(6), actualData.get(6));
			assertTrue(popupFieldArray.get(7).contains(currentDate));
			assertTrue(popupFieldArray.get(8).contains(currentDate));

			foundation.click(CreatePromotions.BTN_OK);

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		finally {
			// Resetting the data
			promotionList.expirePromotion(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM),promotionName,gridName);
		}

	}

	@Test(description = "141772-SOS-7520 - Verify Promotion List page display only active location")
	public void verifyPromotionsActiveLocation() {
		final String CASE_NUM = "141772";
		
		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);
		rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);

		String promotionName = strings.getRandomCharacter();
		String promotionType = rstLocationData.get(CNLocation.PROMOTION_TYPE);
		String locationName = rstLocationData.get(CNLocation.LOCATION_NAME);

		List<String> locationDisabled = Arrays.asList(rstLocationSummaryData.get(CNLocationSummary.LOCATION_DISABLED).split(Constants.DELIMITER_TILD));

		String locationDisabled_No = locationDisabled.get(1);
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			
			// Selecting location
			locationList.selectLocationName(locationName);

			foundation.waitforElement(LocationSummary.DPD_DISABLED, Constants.SHORT_TIME);
			dropdown.selectItem(LocationSummary.DPD_DISABLED, locationDisabled_No, Constants.TEXT);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationSummary.LBL_SPINNER_MSG, Constants.SHORT_TIME);
			login.logout();

			List<String> requiredData = Arrays
					.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));

			// Select Org,Menu and Menu Item
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// New Promotion
			foundation.click(PromotionList.BTN_CREATE);
			createPromotions.newPromotion(promotionType, promotionName, requiredData.get(0), requiredData.get(1),
					locationName);

			// Validating "Active location name in Location field
			String uiData = dropdown.getSelectedItem(CreatePromotions.DPD_LOCATION);
			Assert.assertEquals(uiData, locationName);

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "141773-Verify Create Promotion page display only active location when location filter is selected")
	public void verifyPromotionsDisabledLocation() {
		final String CASE_NUM = "141773";
		
		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
		rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);

		String promotionName = strings.getRandomCharacter();
		String promotionType = rstLocationData.get(CNLocation.PROMOTION_TYPE);
		String locationName = rstLocationData.get(CNLocation.LOCATION_NAME);
		String locationListDpd = rstLocationListData.get(CNLocationList.DROPDOWN_LOCATION_LIST);		

		List<String> locationDisabled = Arrays.asList(rstLocationSummaryData.get(CNLocationSummary.LOCATION_DISABLED).split(Constants.DELIMITER_TILD));
		String locationDisabledYes = locationDisabled.get(0);
		String locationDisabledNo = locationDisabled.get(1);
		try {
			browser.navigateURL(	propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			
			// Selecting location
			locationList.selectLocationName(locationName);
			foundation.waitforElement(LocationSummary.DPD_DISABLED, Constants.SHORT_TIME);
			dropdown.selectItem(LocationSummary.DPD_DISABLED, locationDisabledYes, Constants.TEXT);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.click(LocationSummary.POP_UP_BTN_SAVE);
			foundation.waitforElement(LocationSummary.LBL_SPINNER_MSG, Constants.SHORT_TIME);
			login.logout();

			List<String> requiredData = Arrays.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));

			// Select Org,Menu and Menu Item
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// New Promotion
			foundation.click(PromotionList.BTN_CREATE);
			dropdown.selectItem(CreatePromotions.DPD_PROMO_TYPE, promotionType, Constants.TEXT);
			textBox.enterText(CreatePromotions.TXT_PROMO_NAME, promotionName);
			textBox.enterText(CreatePromotions.TXT_DISPLAY_NAME, requiredData.get(0));
			foundation.click(CreatePromotions.BTN_NEXT);

			textBox.enterText(CreatePromotions.DPD_ORG, requiredData.get(1));
			textBox.enterText(CreatePromotions.DPD_ORG, Keys.ENTER);
			foundation.threadWait(Constants.ONE_SECOND);
			foundation.click(CreatePromotions.DPD_LOC);			
			textBox.enterText(CreatePromotions.DPD_LOC, locationName);
			textBox.enterText(CreatePromotions.DPD_LOC, Keys.ENTER);
			Assert.assertTrue(foundation.isDisplayed(createPromotions.objLocation(requiredData.get(2))));		

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		finally {
			login.logout();
			editPromotion.switchAlert(rstLocationData.get(CNLocation.ACTUAL_DATA));
			// Resetting test data
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			dropdown.selectItem(LocationList.DPD_LOCATION_LIST, locationListDpd, Constants.TEXT);
			locationList.selectLocationName(locationName);
			foundation.waitforElement(LocationSummary.DPD_DISABLED, Constants.SHORT_TIME);
			dropdown.selectItem(LocationSummary.DPD_DISABLED, locationDisabledNo, Constants.TEXT);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_FILTER, Constants.SHORT_TIME);
		}
	}

	@Test(description = "141822-Verify Operator will update the existing Promotion with New Location")
	public void verifyItemFieldtOption() {
		final String CASE_NUM = "141822";
		
		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);

		String promotionName = strings.getRandomCharacter();
		String newPromotionName = strings.getRandomCharacter();
		String promotionType = rstLocationData.get(CNLocation.PROMOTION_TYPE);
		String gridName = rstLocationData.get(CNLocation.TAB_NAME);
		
		List<String> popupName = Arrays.asList(rstLocationData.get(CNLocation.POPUP_NAME).split(Constants.DELIMITER_TILD));
		List<String> locationName = Arrays.asList(rstLocationData.get(CNLocation.LOCATION_NAME).split(Constants.DELIMITER_TILD));
		List<String> requiredData = Arrays.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		try {
			browser.navigateURL(	propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// New Promotion
			foundation.click(PromotionList.BTN_CREATE);
			foundation.isDisplayed(CreatePromotions.LBL_CREATE_PROMOTION);

			dropdown.selectItem(CreatePromotions.DPD_PROMO_TYPE, promotionType, Constants.TEXT);
			String basicInfoPageTitle = foundation.getText(CreatePromotions.LBL_PAGE_TITLE);
			assertTrue(basicInfoPageTitle.equals(rstLocationData.get(CNLocation.PROMOTION_TYPE)));

			textBox.enterText(CreatePromotions.TXT_PROMO_NAME, promotionName);
			foundation.click(CreatePromotions.BTN_NEXT);

			textBox.enterText(CreatePromotions.DPD_ORG, requiredData.get(0));
			textBox.enterText(CreatePromotions.DPD_ORG, Keys.ENTER);
			dropdown.selectItem(CreatePromotions.DPD_LOCATION, locationName.get(0), Constants.TEXT);
			foundation.waitforElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);

			foundation.click(CreatePromotions.BTN_NEXT);
			dropdown.selectItem(CreatePromotions.MULTI_SELECT_TENDER_TYPES, requiredData.get(1), Constants.TEXT);
			foundation.threadWait(Constants.TWO_SECOND);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_OK);

			// Validating promotion is displayed
			foundation.waitforElement(PromotionList.TXT_SEARCH_PROMONAME, Constants.SHORT_TIME);
			textBox.enterText(PromotionList.TXT_SEARCH_PROMONAME, promotionName);
			foundation.click(PromotionList.BTN_SEARCH);
			table.selectRow(gridName, promotionName);
			promotionList.clickSelectedRow(gridName, promotionName);
			assertTrue(checkBox.isChecked(EditPromotion.CHK_ACTIVE));
			foundation.waitforElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);
			assertEquals(requiredData.get(0), dropdown.getSelectedItem(CreatePromotions.DPD_ORGANIZATION));
			dropdown.deSelectItem(CreatePromotions.DPD_LOCATION, locationName.get(0), Constants.TEXT);
			dropdown.selectItem(CreatePromotions.DPD_LOCATION, locationName.get(1), Constants.TEXT);
			foundation.threadWait(Constants.TWO_SECOND);
			foundation.click(EditPromotion.BTN_UPDATE);
			foundation.threadWait(Constants.TWO_SECOND);
			foundation.isDisplayed(CreatePromotions.DPD_DISCOUNT_TYPE);
			foundation.waitforElement(EditPromotion.BTN_UPDATE, Constants.SHORT_TIME);
			foundation.click(EditPromotion.BTN_UPDATE);
			foundation.threadWait(Constants.TWO_SECOND);
			String expectedAlertHeader = foundation.getText(EditPromotion.TXT_POPUP_HEADER);
			assertEquals(expectedAlertHeader, popupName.get(0));
			String expectedAlertMsg = foundation.getText(EditPromotion.TXT_POPUP_ALERT_MSG);
			List<String> expecetedData = Arrays.asList(expectedAlertMsg.split(Constants.NEW_LINE));
			assertEquals(expecetedData.get(0), popupName.get(1));
			assertEquals(expecetedData.get(2), popupName.get(2));
			foundation.waitforElement(EditPromotion.BTN_SAVE_AS, Constants.SHORT_TIME);
			foundation.click(EditPromotion.BTN_SAVE_AS);			

			textBox.enterText(EditPromotion.TXT_PROMOTION_NAME, newPromotionName);
			foundation.click(EditPromotion.BTN_CONTINUE);
			foundation.waitforElement(EditPromotion.BTN_OK, Constants.SHORT_TIME);
			foundation.click(EditPromotion.BTN_OK);
			// new promotion name validation
			foundation.waitforElement(PromotionList.TXT_SEARCH_PROMONAME, Constants.SHORT_TIME);
			textBox.enterText(PromotionList.TXT_SEARCH_PROMONAME, newPromotionName);
			foundation.click(PromotionList.BTN_SEARCH);
			Assert.assertTrue(foundation.getText(PromotionList.TBL_COLUMN_NAME).equals(newPromotionName));
			// old promotion name validation
			foundation.waitforElement(PromotionList.TXT_SEARCH_PROMONAME, Constants.SHORT_TIME);
			textBox.enterText(PromotionList.TXT_SEARCH_PROMONAME, promotionName);
			foundation.click(PromotionList.BTN_SEARCH);
			Assert.assertTrue(foundation.getText(PromotionList.TBL_COLUMN_NAME).equals(promotionName));
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		finally {
			//Expire Promotion
			promotionList.searchPromotion(newPromotionName);
			editPromotion.expirePromotion(gridName, newPromotionName);
			foundation.waitforElement(PromotionList.PAGE_TITLE, Constants.SHORT_TIME);
			promotionList.searchPromotion(promotionName);
			editPromotion.expirePromotion(gridName, promotionName);
			foundation.waitforElement(PromotionList.PAGE_TITLE, Constants.SHORT_TIME);
		}
	}

	@Test(description = "141823 -SOS-17467-Verify Operator will create Multiple Promotions for differenet locations")
	public void verifyMultiplePromtionsOption() {
		final String CASE_NUM = "141823";

		browser.navigateURL(	propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
		login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
				propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);

		String promotionType = rstLocationData.get(CNLocation.PROMOTION_TYPE);
		List<String> locationName = Arrays.asList(rstLocationData.get(CNLocation.LOCATION_NAME).split(Constants.DELIMITER_TILD));
		List<String> requiredData = Arrays.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		String promotionName = Constants.ACCOUNT_NAME + strings.getRandomCharacter();
		String gridName = rstLocationData.get(CNLocation.TAB_NAME);
		try {

			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// New Promotion
			for (int iter = 0; iter < locationName.size(); iter++) {				
				foundation.click(PromotionList.BTN_CREATE);
				foundation.isDisplayed(CreatePromotions.LBL_CREATE_PROMOTION);
				dropdown.selectItem(CreatePromotions.DPD_PROMO_TYPE, promotionType, Constants.TEXT);
				String basicInfoPageTitle = foundation.getText(CreatePromotions.LBL_PAGE_TITLE);
				assertTrue(basicInfoPageTitle.equals(rstLocationData.get(CNLocation.PROMOTION_TYPE)));
				textBox.enterText(CreatePromotions.TXT_PROMO_NAME, promotionName);
				foundation.click(CreatePromotions.BTN_NEXT);
				String filtersPageTitle = foundation.getText(CreatePromotions.LBL_PAGE_TITLE);
				assertTrue(filtersPageTitle.equals(rstLocationData.get(CNLocation.PROMOTION_TYPE)));

				textBox.enterText(CreatePromotions.DPD_ORG, requiredData.get(0));
				textBox.enterText(CreatePromotions.DPD_ORG, Keys.ENTER);
				dropdown.selectItem(CreatePromotions.DPD_LOCATION, locationName.get(iter), Constants.TEXT);
				foundation.waitforElement(CreatePromotions.BTN_NEXT, Constants.MEDIUM_TIME);
				foundation.threadWait(Constants.TWO_SECOND);

				foundation.click(CreatePromotions.BTN_NEXT);
				dropdown.selectItem(CreatePromotions.MULTI_SELECT_TENDER_TYPES, requiredData.get(1), Constants.TEXT);

				foundation.threadWait(Constants.TWO_SECOND);
				textBox.enterText(CreatePromotions.TXT_AMOUNT, requiredData.get(2));
				textBox.enterText(CreatePromotions.TXT_TRANSACTION_MIN, requiredData.get(3));
				foundation.click(CreatePromotions.BTN_NEXT);
				foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);
				// validating popup msg
				List<String> popupFieldType = foundation.getTextofListElement(CreatePromotions.POP_UP_MESSAGES);
				List<String> popupField = null;
				popupField = Arrays.asList(popupFieldType.get(0).split(Constants.DELIMITER_COMMA));
				popupField = Arrays.asList(popupField.get(0).split(Constants.NEW_LINE));

				List<String> actualData = Arrays.asList(rstLocationData.get(CNLocation.ACTUAL_DATA).split(Constants.DELIMITER_TILD));
				assertEquals(popupField.get(0), actualData.get(0));
				assertEquals(popupField.get(1), actualData.get(1));

				List<String> popupFieldArray = createPromotions.getPopUpData();
				String currentDate = dateAndTime.getDateAndTime(Constants.REGEX_MMDDYY, Constants.TIME_ZONE_INDIA);

				assertTrue(popupFieldArray.get(0).contains(promotionType));
				assertTrue(popupFieldArray.get(1).contains(promotionName));
				assertEquals(popupFieldArray.get(2), actualData.get(2));
				assertEquals(popupFieldArray.get(3), actualData.get(3));
				assertEquals(popupFieldArray.get(4), actualData.get(4));
				assertEquals(popupFieldArray.get(5), actualData.get(5));
				assertEquals(popupFieldArray.get(6), actualData.get(6));
				assertTrue(popupFieldArray.get(7).contains(currentDate));
				assertTrue(popupFieldArray.get(8).contains(currentDate));

				foundation.click(CreatePromotions.BTN_OK);
			}

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		finally {
			// Resetting the data
			for (int iter = 0; iter < locationName.size(); iter++) {
			promotionList.expirePromotion(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM),promotionName,gridName);
			}
		}
	}

	@Test(description = "141775-To Verify sorting of Start Date column in Promotion List grid")
	public void verifySortStartDate() {
		try {
			final String CASE_NUM = "141775";

			browser.navigateURL(	propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// verify start date sort
			foundation.click(PromotionList.LBL_START_DATE_HEADER);
			assertTrue(foundation.verifySortDate(PromotionList.LBL_START_DATE_LIST, Constants.ASCENDING,Constants.REGEX_MMDDUU));
			foundation.click(PromotionList.LBL_START_DATE_HEADER);
			assertTrue(foundation.verifySortDate(PromotionList.LBL_START_DATE_LIST, Constants.DESCENDING,Constants.REGEX_MMDDUU));

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}


	@Test(description = "141829-SOS-21458-Verify if Item is correctly getting updated in Promotion screen - Onscreen Promotion")
	public void verifyReplaceCategoryFieldtOptionWithItem() {
		try {
			final String CASE_NUM = "141829";

			browser.navigateURL(	propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from database
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);

			final String promotionName = strings.getRandomCharacter();
			String displayName = strings.getRandomCharacter();
			String promotionType = rstLocationData.get(CNLocation.PROMOTION_TYPE);
			String locationName = rstLocationData.get(CNLocation.LOCATION_NAME);
			String gridName = rstLocationData.get(CNLocation.TAB_NAME);

			List<String> requiredData = Arrays.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));

			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// New Promotion
			foundation.click(PromotionList.BTN_CREATE);
			foundation.isDisplayed(CreatePromotions.LBL_CREATE_PROMOTION);

			dropdown.selectItem(CreatePromotions.DPD_PROMO_TYPE, promotionType, Constants.TEXT);
			String basicInfoPageTitle = foundation.getText(CreatePromotions.LBL_PAGE_TITLE);
			assertTrue(basicInfoPageTitle.equals(rstLocationData.get(CNLocation.PROMOTION_TYPE)));

			textBox.enterText(CreatePromotions.TXT_PROMO_NAME, promotionName);
			textBox.enterText(CreatePromotions.TXT_DISPLAY_NAME, displayName);
			foundation.click(CreatePromotions.BTN_NEXT);

			String filtersPageTitle = foundation.getText(CreatePromotions.LBL_PAGE_TITLE);
			assertTrue(filtersPageTitle.equals(rstLocationData.get(CNLocation.PROMOTION_TYPE)));

			textBox.enterText(CreatePromotions.DPD_ORG, requiredData.get(0));
			textBox.enterText(CreatePromotions.DPD_ORG, Keys.ENTER);

			dropdown.selectItem(CreatePromotions.DPD_LOCATION, locationName, Constants.TEXT);
			foundation.waitforElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);

			foundation.click(CreatePromotions.BTN_NEXT);
			dropdown.selectItem(CreatePromotions.DPD_DISCOUNT_BY, requiredData.get(1), Constants.TEXT);
			textBox.enterText(CreatePromotions.SEARCH_CATEGORY, requiredData.get(2));
			foundation.threadWait(Constants.ONE_SECOND);
			textBox.enterText(CreatePromotions.SEARCH_CATEGORY, Keys.ENTER);

			textBox.enterText(CreatePromotions.TXT_AMOUNT, requiredData.get(3));
			textBox.enterText(CreatePromotions.TXT_TRANSACTION_MIN, requiredData.get(4));
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_OK);

			// editing item field
			foundation.waitforElement(PromotionList.TXT_SEARCH_PROMONAME, Constants.SHORT_TIME);
			textBox.enterText(PromotionList.TXT_SEARCH_PROMONAME, promotionName);
			foundation.click(PromotionList.BTN_SEARCH);
			Assert.assertTrue(foundation.getText(PromotionList.TBL_COLUMN_NAME).equals(promotionName));
			promotionList.clickSelectedRow(gridName, promotionName);
			foundation.waitforElement(EditPromotion.BTN_END_PROMO, Constants.SHORT_TIME);
			foundation.waitforElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);
			assertEquals(requiredData.get(0), dropdown.getSelectedItem(CreatePromotions.DPD_ORGANIZATION));
			foundation.threadWait(Constants.TWO_SECOND);
			foundation.click(EditPromotion.BTN_UPDATE);
			foundation.threadWait(Constants.TWO_SECOND);
			dropdown.selectItem(CreatePromotions.DPD_DISCOUNT_BY, requiredData.get(5), Constants.TEXT);
			textBox.enterText(CreatePromotions.TXT_ITEM, requiredData.get(6));
			foundation.threadWait(Constants.ONE_SECOND);
			textBox.enterText(CreatePromotions.TXT_ITEM, Keys.ENTER);
			foundation.click(EditPromotion.BTN_UPDATE);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_OK);

			// category field validation
			foundation.waitforElement(PromotionList.TXT_SEARCH_PROMONAME, Constants.SHORT_TIME);
			textBox.enterText(PromotionList.TXT_SEARCH_PROMONAME, promotionName);
			foundation.click(PromotionList.BTN_SEARCH);
			Assert.assertTrue(foundation.getText(PromotionList.TBL_COLUMN_NAME).equals(promotionName));
			promotionList.clickSelectedRow(gridName, promotionName);
			foundation.waitforElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);
			foundation.waitforElement(EditPromotion.BTN_END_PROMO, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.threadWait(Constants.TWO_SECOND);
			foundation.click(EditPromotion.BTN_NEXT);
			foundation.waitforElement(EditPromotion.DPD_ITEM, Constants.SHORT_TIME);
			String actualData = dropdown.getSelectedItem(EditPromotion.DPD_ITEM);
			assertEquals(actualData, requiredData.get(6));
			// end promotion
			foundation.click(EditPromotion.BTN_CANCEL);
			foundation.threadWait(Constants.TWO_SECOND);
			foundation.click(EditPromotion.BTN_CANCEL);
			foundation.waitforElement(EditPromotion.BTN_END_PROMO, Constants.SHORT_TIME);
			foundation.click(EditPromotion.BTN_END_PROMO);
			foundation.click(EditPromotion.BTN_CONTINUE);

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}


	@Test(description = "141828-SOS-21458-Verify if category is correctly getting updated in Promotion screen - Onscreen Promotion")
	public void verifyReplaceItemFieldtOptionWithCategory() {
		try {
			final String CASE_NUM = "141828";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from database
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);

			final String promotionName = strings.getRandomCharacter();
			String displayName = strings.getRandomCharacter();
			String promotionType = rstLocationData.get(CNLocation.PROMOTION_TYPE);
			String locationName = rstLocationData.get(CNLocation.LOCATION_NAME);
			String gridName = rstLocationData.get(CNLocation.TAB_NAME);

			List<String> requiredData = Arrays.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));

			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// New Promotion
			foundation.click(PromotionList.BTN_CREATE);
			foundation.isDisplayed(CreatePromotions.LBL_CREATE_PROMOTION);
			dropdown.selectItem(CreatePromotions.DPD_PROMO_TYPE, promotionType, Constants.TEXT);
			String basicInfoPageTitle = foundation.getText(CreatePromotions.LBL_PAGE_TITLE);
			assertTrue(basicInfoPageTitle.equals(rstLocationData.get(CNLocation.PROMOTION_TYPE)));
			textBox.enterText(CreatePromotions.TXT_PROMO_NAME, promotionName);
			textBox.enterText(CreatePromotions.TXT_DISPLAY_NAME, displayName);
			foundation.click(CreatePromotions.BTN_NEXT);
			String filtersPageTitle = foundation.getText(CreatePromotions.LBL_PAGE_TITLE);
			assertTrue(filtersPageTitle.equals(rstLocationData.get(CNLocation.PROMOTION_TYPE)));
			textBox.enterText(CreatePromotions.DPD_ORG, requiredData.get(0));
			textBox.enterText(CreatePromotions.DPD_ORG, Keys.ENTER);
			dropdown.selectItem(CreatePromotions.DPD_LOCATION, locationName, Constants.TEXT);
			foundation.waitforElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);
			dropdown.selectItem(CreatePromotions.DPD_DISCOUNT_BY, requiredData.get(1), Constants.TEXT);
			textBox.enterText(CreatePromotions.TXT_ITEM, requiredData.get(2));
			foundation.threadWait(Constants.ONE_SECOND);
			textBox.enterText(CreatePromotions.TXT_ITEM, Keys.ENTER);
			foundation.threadWait(Constants.TWO_SECOND);
			String actualValue = dropdown.getSelectedItem(CreatePromotions.DPD_ITEM_SELECT);
			assertEquals(actualValue, requiredData.get(2));
			textBox.enterText(CreatePromotions.TXT_AMOUNT, requiredData.get(5));
			textBox.enterText(CreatePromotions.TXT_TRANSACTION_MIN, requiredData.get(6));
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_OK);

			// editing category field
			foundation.waitforElement(PromotionList.TXT_SEARCH_PROMONAME, Constants.SHORT_TIME);
			textBox.enterText(PromotionList.TXT_SEARCH_PROMONAME, promotionName);
			foundation.click(PromotionList.BTN_SEARCH);
			Assert.assertTrue(foundation.getText(PromotionList.TBL_COLUMN_NAME).equals(promotionName));
			promotionList.clickSelectedRow(gridName, promotionName);
			foundation.waitforElement(EditPromotion.BTN_END_PROMO, Constants.SHORT_TIME);
			foundation.waitforElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);
			foundation.waitforClikableElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);
			assertEquals(requiredData.get(0), dropdown.getSelectedItem(CreatePromotions.DPD_ORGANIZATION));
			foundation.threadWait(Constants.TWO_SECOND);
			foundation.click(EditPromotion.BTN_UPDATE);
			foundation.threadWait(Constants.TWO_SECOND);
			dropdown.selectItem(CreatePromotions.DPD_DISCOUNT_BY, requiredData.get(3), Constants.TEXT);
			textBox.enterText(CreatePromotions.SEARCH_CATEGORY, requiredData.get(4));
			foundation.threadWait(Constants.ONE_SECOND);
			textBox.enterText(CreatePromotions.SEARCH_CATEGORY, Keys.ENTER);
			foundation.click(EditPromotion.BTN_UPDATE);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_OK);

			// category field validation
			foundation.waitforElement(PromotionList.TXT_SEARCH_PROMONAME, Constants.SHORT_TIME);
			textBox.enterText(PromotionList.TXT_SEARCH_PROMONAME, promotionName);
			foundation.click(PromotionList.BTN_SEARCH);
			Assert.assertTrue(foundation.getText(PromotionList.TBL_COLUMN_NAME).equals(promotionName));
			promotionList.clickSelectedRow(gridName, promotionName);
			foundation.waitforElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);
			foundation.waitforElement(EditPromotion.BTN_END_PROMO, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.threadWait(Constants.TWO_SECOND);
			foundation.click(EditPromotion.BTN_NEXT);
			foundation.waitforElement(EditPromotion.DPD_CATEGORY, Constants.SHORT_TIME);
			String actualData = dropdown.getSelectedItem(EditPromotion.DPD_CATEGORY);
			assertEquals(actualData, requiredData.get(4));
			// end promotion
			foundation.click(EditPromotion.BTN_CANCEL);
			foundation.threadWait(Constants.TWO_SECOND);
			foundation.click(EditPromotion.BTN_CANCEL);
			foundation.waitforElement(EditPromotion.BTN_END_PROMO, Constants.SHORT_TIME);
			foundation.click(EditPromotion.BTN_END_PROMO);
			foundation.click(EditPromotion.BTN_CONTINUE);

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}	
}
