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
import at.framework.ui.TextBox;
import at.smartshop.database.columns.CNLocation;
import at.smartshop.database.columns.CNNavigationMenu;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.pages.CreatePromotions;
import at.smartshop.pages.EditPromotion;
import at.smartshop.pages.LocationList;
import at.smartshop.pages.NavigationBar;
import at.smartshop.pages.PromotionList;

@Listeners(at.framework.reportsetup.Listeners.class)
public class Promotions extends TestInfra {

	private PropertyFile propertyFile = new PropertyFile();
	private ResultSets dataBase = new ResultSets();
	private Foundation foundation = new Foundation();
	private NavigationBar navigationBar = new NavigationBar();
	private CreatePromotions createPromotions = new CreatePromotions();
	private Dropdown dropdown = new Dropdown();
	private TextBox textBox = new TextBox();
	private Strings strings = new Strings();
	private DateAndTime dateAndTime = new DateAndTime();
	private CheckBox checkBox = new CheckBox();

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
			createPromotions.newPromotion(promotionType, promotionName, promotionName, requiredData);

			// Validating "All" option in Location field
			String uiData = foundation.getText(CreatePromotions.DPD_LOCATION);
			Assert.assertEquals(uiData, locationName);

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	@Test(description = "141779 - Verify the Tender Discount Promotion with Tender type as Cash")
	public void verifyTenderDiscountWithTenderTypeCash() {
		try {
			final String CASE_NUM = "141779";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);

			String promotionType = rstLocationData.get(CNLocation.PROMOTION_TYPE);
			String locationName = rstLocationData.get(CNLocation.LOCATION_NAME);
			String gridName = rstLocationData.get(CNLocation.TAB_NAME);

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
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

			String promotionName = "Test" + strings.getRandomCharacter();
			textBox.enterText(CreatePromotions.TXT_PROMO_NAME, promotionName);
			foundation.click(CreatePromotions.BTN_NEXT);

			String filtersPageTitle = foundation.getText(CreatePromotions.LBL_PAGE_TITLE);
			assertTrue(filtersPageTitle.equals(rstLocationData.get(CNLocation.PROMOTION_TYPE)));

			textBox.enterText(CreatePromotions.DPD_ORG,
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			textBox.enterText(CreatePromotions.DPD_ORG, Keys.ENTER);
			dropdown.selectItem(CreatePromotions.DPD_LOCATION, locationName, Constants.TEXT);
			foundation.waitforElement(CreatePromotions.BTN_NEXT, 2000);

			foundation.click(CreatePromotions.BTN_NEXT);
			List<String> requiredData = Arrays
					.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));

			dropdown.selectItem(CreatePromotions.MULTI_SELECT_TENDER_TYPES, requiredData.get(0), Constants.TEXT);
			dropdown.selectItem(CreatePromotions.DPD_DISCOUNT_TYPE, requiredData.get(1), Constants.TEXT);
			dropdown.selectItem(CreatePromotions.DPD_APPLY_DISCOUNT_TO, requiredData.get(2), Constants.TEXT);
			textBox.enterText(CreatePromotions.TXT_AMOUNT, requiredData.get(3));
			textBox.enterText(CreatePromotions.TXT_TRANSACTION_MIN, requiredData.get(4));
			dropdown.selectItem(CreatePromotions.DPD_DISCOUNT_TIME, requiredData.get(5), Constants.TEXT);
			foundation.click(CreatePromotions.CHK_SUNDAY);

			foundation.threadWait(2000);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.waitforElement(CreatePromotions.BTN_OK, 2000);

			List<String> popupFieldType = foundation.getTextofListElement(CreatePromotions.POP_UP_MESSAGES);
			List<String> popupField = null;
			popupField = Arrays.asList(popupFieldType.get(0).split(Constants.DELIMITER_COMMA));
			popupField = Arrays.asList(popupField.get(0).split(Constants.NEW_LINE));

			List<String> actualData = Arrays
					.asList(rstLocationData.get(CNLocation.ACTUAL_DATA).split(Constants.DELIMITER_TILD));
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

			// Validating promotion is displayed
			foundation.waitforElement(PromotionList.PAGE_TITLE, 3000);
			assertTrue(foundation.getText(PromotionList.TBL_COLUMN_NAME).equals(promotionName));

			// Resetting the data
			createPromotions.expirePromotion(gridName, promotionName);

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	@Test(description = "118619-Verify to create a promotion with Promotion Type as Tender Discount")
	public void verifyCreateTenderDiscountPromo() {
		try {
			final String CASE_NUM = "118619";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			List<String> menu = Arrays
					.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
			navigationBar.navigateToMenuItem(menu.get(0));
			foundation.waitforElement(UserRoles.LBL_USER_LIST, 2000);
			textBox.enterText(UserRoles.TXT_SEARCH_FILTER,
					propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(userRoles.getRowByText(rstLocationSummaryData.get(CNLocationSummary.CONTACT_EMAIL)));
			foundation.waitforElement(UserRoles.LBL_VIEW_ROLE, 2000);
			foundation.click(UserList.LNK_ORG_REMOVE_ALL);
			List<String> orgs = Arrays
					.asList(rstLocationData.get(CNLocation.COLUMN_VALUE).split(Constants.DELIMITER_TILD));
			userList.selectOrgs(UserList.DPD_ORG, orgs);
			foundation.threadWait(2000);
			String allOption = rstLocationListData.get(CNLocationList.COLUMN_NAME);
			foundation.objectFocus(UserList.TXT_SEARCH_LOC);
			dropdown.selectItem(UserList.TXT_SEARCH_LOC, allOption, Constants.TEXT);
			foundation.threadWait(2000);
			foundation.click(UserList.BTN_UPDATE_USER);
			foundation.waitforElement(UserRoles.LBL_USER_LIST, 3000);
			login.logout();

			login.logout();

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);

			String promotionType = rstLocationData.get(CNLocation.PROMOTION_TYPE);
			String locationName = rstLocationData.get(CNLocation.LOCATION_NAME);
			String gridName = rstLocationData.get(CNLocation.TAB_NAME);
			List<String> requiredData = Arrays
					.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> actualData = Arrays
					.asList(rstLocationData.get(CNLocation.ACTUAL_DATA).split(Constants.DELIMITER_TILD));

			String promotionName = strings.getRandomCharacter();
			List<String> locationList = foundation.getTextofListElement(LocationList.LINK_LOCATION_LIST);

			// select admin-promotion menu
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// navigate to create new promo page
			foundation.click(PromotionList.BTN_CREATE);
			foundation.isDisplayed(CreatePromotions.LBL_CREATE_PROMOTION);
			dropdown.selectItem(CreatePromotions.DPD_PROMO_TYPE, promotionType, Constants.TEXT);

			// select promotion and navigate to next
			textBox.enterText(CreatePromotions.TXT_PROMO_NAME, promotionName);
			foundation.click(CreatePromotions.BTN_NEXT);

			// selct org and location- ensure related org and related location displays
			List<String> orgData = dropdown.getAllItems(CreatePromotions.DPD_ORGANIZATION);
			for (int iter = 0; iter < orgData.size(); iter++) {
				Assert.assertTrue(orgData.get(iter).contains(orgs.get(iter)));
			}
			textBox.enterText(CreatePromotions.DPD_ORG,
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			textBox.enterText(CreatePromotions.DPD_ORG, Keys.ENTER);
			for (String location : locationList) {
				assertTrue(dropdown.verifyItemPresent(CreatePromotions.DPD_LOCATION, location));
			}
			dropdown.selectItem(CreatePromotions.DPD_LOCATION, locationName, Constants.TEXT);
			assertFalse(foundation.isDisplayed(createPromotions.objFieldSet(requiredData.get(6))));
			assertFalse(foundation.isDisplayed(createPromotions.objFieldSet(requiredData.get(7))));
			assertFalse(foundation.isDisplayed(createPromotions.objFieldSet(requiredData.get(8))));
			assertFalse(foundation.isDisplayed(createPromotions.objFieldSet(requiredData.get(9))));
			foundation.waitforElement(CreatePromotions.BTN_NEXT, 2000);
			foundation.click(CreatePromotions.BTN_NEXT);

			// select promotion details
			dropdown.selectItem(CreatePromotions.MULTI_SELECT_TENDER_TYPES, requiredData.get(0), Constants.TEXT);
			dropdown.selectItem(CreatePromotions.DPD_DISCOUNT_TYPE, requiredData.get(1), Constants.TEXT);
			dropdown.selectItem(CreatePromotions.DPD_APPLY_DISCOUNT_TO, requiredData.get(2), Constants.TEXT);
			textBox.enterText(CreatePromotions.TXT_AMOUNT, requiredData.get(3));
			textBox.enterText(CreatePromotions.TXT_TRANSACTION_MIN, requiredData.get(4));
			dropdown.selectItem(CreatePromotions.DPD_DISCOUNT_TIME, requiredData.get(5), Constants.TEXT);
			foundation.click(CreatePromotions.CHK_SUNDAY);
			foundation.threadWait(2000);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.waitforElement(CreatePromotions.BTN_OK, 2000);

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

			// Resetting the data
			foundation.waitforElement(PromotionList.PAGE_TITLE, 3000);
			createPromotions.expirePromotion(gridName, promotionName);
			login.logout();
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menu.get(0));
			foundation.waitforElement(UserRoles.LBL_USER_LIST, 2000);
			textBox.enterText(UserRoles.TXT_SEARCH_FILTER,
					propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(userRoles.getRowByText(rstLocationSummaryData.get(CNLocationSummary.CONTACT_EMAIL)));
			foundation.waitforElement(UserRoles.LBL_VIEW_ROLE, 2000);
			foundation.click(UserList.LNK_ORG_REMOVE_ALL);

			dropdown.selectItem(UserList.DPD_ORG, allOption, Constants.TEXT);
			foundation.threadWait(2000);
			foundation.waitforElement(UserList.LNK_LOCATION_REMOVE_ALL, 10000);
			foundation.click(UserList.BTN_UPDATE_USER);
			foundation.waitforElement(UserRoles.LBL_USER_LIST, 3000);
			login.logout();

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}	
}
