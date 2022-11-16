package at.smartshop.tests;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import at.framework.database.mssql.Queries;
import at.framework.database.mssql.ResultSets;
import at.framework.files.PropertyFile;
import at.framework.generic.CustomisedAssert;
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
import at.smartshop.pages.Login;
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
	private Dropdown dropDown = new Dropdown();
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

	@Test(description = "141771-Verify All option is displayed in Location dropDown")
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
			String displayName = strings.getRandomCharacter();

			List<String> requiredData = Arrays
					.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));

			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// New Promotion
			foundation.click(PromotionList.BTN_CREATE);
			dropDown.selectItem(CreatePromotions.DPD_PROMO_TYPE, promotionType, Constants.TEXT);
			textBox.enterText(CreatePromotions.TXT_PROMO_NAME, promotionName);
			if (foundation.isDisplayed(CreatePromotions.TXT_DISPLAY_NAME))
				textBox.enterText(CreatePromotions.TXT_DISPLAY_NAME, displayName);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.waitforElement(CreatePromotions.TXT_SEARCH_ORGPAGE, Constants.SHORT_TIME);
			textBox.enterText(CreatePromotions.TXT_SEARCH_ORGPAGE, requiredData.get(0));
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(CreatePromotions.CHECKBOX_ORG);
			foundation.waitforElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.waitforElement(CreatePromotions.ALL_LOCATION, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.ALL_LOCATION);
			foundation.waitforElement(CreatePromotions.LOCATION_DISABLED, Constants.SHORT_TIME);

//			foundation.waitforElement(CreatePromotions.DPD_ORG, Constants.SHORT_TIME);
//			dropDown.selectItem(CreatePromotions.DPD_ORG, requiredData.get(0), Constants.TEXT);
//			foundation.click(CreatePromotions.BTN_ORG_RIGHT);
			// dropDown.selectItem(CreatePromotions.DPD_ORG, requiredData.get(1),
			// Constants.TEXT);
			// foundation.click(CreatePromotions.BTN_ORG_RIGHT);
//			foundation.threadWait(Constants.TWO_SECOND);
//			foundation.click(CreatePromotions.SELECT_ALL_LOCATION);
//			foundation.click(CreatePromotions.BTN_LOC_RIGHT);
			// createPromotions.newPromotion(promotionType, promotionName,
			// requiredData.get(0), requiredData.get(1),locationName);

			// Validating "All" option in Location field
			// String uiData =
			// dropDown.getSelectedItem(CreatePromotions.DPD_DESELECT_LOCATION);
			// assertEquals(uiData, locationName);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "141779-Verify the Tender Discount Promotion with Tender type as Cash")
	public void verifyTenderDiscountWithTenderTypeCash() {
		final String CASE_NUM = "141779";

		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);

		String promotionType = rstLocationData.get(CNLocation.PROMOTION_TYPE);
		String orgName = propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE);
		String gridName = rstLocationData.get(CNLocation.TAB_NAME);
		String promotionName = strings.getRandomCharacter();
		String statusType = rstLocationData.get(CNLocation.LOCATION_NAME);
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// validate UI of promotion list page info section
			CustomisedAssert.assertTrue(foundation.isDisplayed(PromotionList.LBL_SEARCH));
			CustomisedAssert.assertTrue(foundation.isDisplayed(PromotionList.CALENDER_DATE_RANGE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(PromotionList.DPD_LOCATION));
			CustomisedAssert.assertTrue(foundation.isDisplayed(PromotionList.DPD_STATUS));
			CustomisedAssert.assertTrue(foundation.isDisplayed(PromotionList.DPD_PROMOTYPE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(PromotionList.BTN_CREATE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(PromotionList.BTN_SEARCH));

			foundation.click(PromotionList.BTN_CREATE);
			foundation.isDisplayed(CreatePromotions.LBL_CREATE_PROMOTION);
			dropDown.selectItem(CreatePromotions.DPD_PROMO_TYPE, promotionType, Constants.TEXT);
			foundation.threadWait(Constants.TWO_SECOND);
			textBox.enterText(CreatePromotions.TXT_PROMO_NAME, promotionName);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.threadWait(Constants.TWO_SECOND);

//			dropDown.selectItem(CreatePromotions.DPD_ORG, orgName, Constants.TEXT);
//			foundation.click(CreatePromotions.BTN_ORG_RIGHT);
//			foundation.threadWait(Constants.TWO_SECOND);
//			foundation.click(CreatePromotions.SELECT_ALL_LOCATION);
//
//			foundation.threadWait(Constants.TWO_SECOND);
//			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.waitforElement(CreatePromotions.TXT_SEARCH_ORGPAGE, Constants.SHORT_TIME);
			textBox.enterText(CreatePromotions.TXT_SEARCH_ORGPAGE, orgName);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(CreatePromotions.CHECKBOX_ORG);
			foundation.waitforElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.waitforElement(CreatePromotions.TXT_LOC_SEARCH, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.ALL_LOCATION);
			foundation.waitforElement(CreatePromotions.BTN_NEXT, Constants.THREE_SECOND);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.objectClick(CreatePromotions.BTN_NEXT);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.objectClick(CreatePromotions.BTN_NEXT);
			foundation.waitforElementToBeVisible(CreatePromotions.LBL_BUILD_BUNDLE, Constants.SHORT_TIME);

			List<String> requiredData = Arrays
					.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));

			dropDown.selectItem(CreatePromotions.MULTI_SELECT_TENDER_TYPES, requiredData.get(0), Constants.TEXT);
			dropDown.selectItem(CreatePromotions.DPD_DISCOUNT_TYPE, requiredData.get(1), Constants.TEXT);
			dropDown.selectItem(CreatePromotions.DPD_APPLY_DISCOUNT_TO, requiredData.get(2), Constants.TEXT);
			textBox.enterText(CreatePromotions.TXT_AMOUNT, requiredData.get(3));
			textBox.enterText(CreatePromotions.TXT_TRANSACTION_MIN, requiredData.get(4));
			dropDown.selectItem(CreatePromotions.DPD_DISCOUNT_TIME, requiredData.get(5), Constants.TEXT);
			foundation.click(CreatePromotions.CHK_SUNDAY);

			foundation.threadWait(Constants.TWO_SECOND);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.LONG_TIME);

			List<String> popupFieldType = foundation.getTextofListElement(CreatePromotions.POP_UP_MESSAGES);
			List<String> popupField = null;
			popupField = Arrays.asList(popupFieldType.get(0).split(Constants.DELIMITER_COMMA));
			popupField = Arrays.asList(popupField.get(0).split(Constants.NEW_LINE));

			List<String> actualData = Arrays
					.asList(rstLocationData.get(CNLocation.ACTUAL_DATA).split(Constants.DELIMITER_TILD));
			CustomisedAssert.assertEquals(popupField.get(0), actualData.get(0));
			CustomisedAssert.assertEquals(popupField.get(1), actualData.get(1));

			List<String> popupFieldArray = createPromotions.getPopUpData();
			String currentDate = dateAndTime.getDateAndTime(Constants.REGEX_MMDDYY, Constants.TIME_ZONE_INDIA);

			CustomisedAssert.assertTrue(popupFieldArray.get(0).contains(promotionType));
			CustomisedAssert.assertTrue(popupFieldArray.get(1).contains(promotionName));
			CustomisedAssert.assertEquals(popupFieldArray.get(2), actualData.get(2));
			CustomisedAssert.assertEquals(popupFieldArray.get(3), actualData.get(3));
			CustomisedAssert.assertEquals(popupFieldArray.get(4), actualData.get(4));
			foundation.threadWait(Constants.SHORT_TIME);
			CustomisedAssert.assertEquals(popupFieldArray.get(5), actualData.get(5));
			CustomisedAssert.assertEquals(popupFieldArray.get(6), actualData.get(6));
			CustomisedAssert.assertTrue(popupFieldArray.get(7).contains(currentDate));
			CustomisedAssert.assertTrue(popupFieldArray.get(8).contains(currentDate));

			foundation.click(CreatePromotions.BTN_OK);
			foundation.threadWait(Constants.SHORT_TIME);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// Resetting the data
			promotionList.expirePromotion(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM), promotionName,
					statusType, gridName);
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
		String orgName = propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE);
		List<String> menu = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		String promotionName = strings.getRandomCharacter();
		String statusType = rstLocationData.get(CNLocation.LOCATION_NAME);
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.navigateToMenuItem(menu.get(0));
			foundation.waitforElement(UserRoles.LBL_USER_LIST, Constants.SHORT_TIME);
			textBox.enterText(UserRoles.TXT_SEARCH_FILTER,
					propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(userRoles.getRowByText(rstLocationSummaryData.get(CNLocationSummary.CONTACT_EMAIL)));
			foundation.waitforElement(UserRoles.LBL_VIEW_ROLE, Constants.SHORT_TIME);
			foundation.click(UserList.LNK_ORG_REMOVE_ALL);
			List<String> orgs = Arrays
					.asList(rstLocationData.get(CNLocation.COLUMN_VALUE).split(Constants.DELIMITER_TILD));
			userList.selectOrgs(UserList.DPD_ORG, orgs);
			foundation.threadWait(Constants.SHORT_TIME);
			String allOption = rstLocationListData.get(CNLocationList.COLUMN_NAME);
			foundation.objectFocus(UserList.TXT_SEARCH_LOC);
			dropDown.selectItem(UserList.TXT_SEARCH_LOC, allOption, Constants.TEXT);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(UserList.BTN_UPDATE_USER);
			foundation.waitforElement(UserList.LBL_USER_LIST, Constants.SHORT_TIME);
			foundation.threadWait(Constants.SHORT_TIME);
			login.logout();

			// operator user
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			List<String> locationNames = foundation.getTextofListElement(LocationList.LINK_LOCATION_LIST);
			navigationBar.navigateToMenuItem(menu.get(1));

			// Basic Information Page
			foundation.click(PromotionList.BTN_CREATE);
			createPromotions.newPromotion(promotionType, promotionName, promotionName, orgName, locationNames.get(0));
			foundation.waitforElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);

			// Promotion Details Page
			List<String> discountType = Arrays
					.asList(rstLocationData.get(CNLocation.PRODUCT_NAME).split(Constants.DELIMITER_TILD));
			dropDown.selectItem(CreatePromotions.DPD_DISCOUNT_BY, discountType.get(0), Constants.TEXT);
			foundation.waitforElementToBeVisible(CreatePromotions.ADD_CATEGORY, Constants.THREE_SECOND);
			foundation.click(CreatePromotions.ADD_CATEGORY);
			textBox.enterText(CreatePromotions.CATEGORY_SEARCH_TXT, discountType.get(2));
			foundation.threadWait(Constants.SHORT_TIME);
			checkBox.check(CreatePromotions.SELECT_CAT_PRODUCT);
			foundation.waitforElementToBeVisible(CreatePromotions.BTN_CANCEL_CAT_POPUP, Constants.THREE_SECOND);
			foundation.click(CreatePromotions.BTN_CANCEL_CAT_POPUP);
			dropDown.selectItem(CreatePromotions.DPD_DISCOUNT_BY, discountType.get(1), Constants.TEXT);
			foundation.click(CreatePromotions.ADD_ITEM);
			textBox.enterText(CreatePromotions.ITEM_SEARCH_TXT, discountType.get(3));
			foundation.threadWait(Constants.SHORT_TIME);
			checkBox.check(CreatePromotions.SELECT_ITEM_PRODUCT);
			foundation.waitforElementToBeVisible(CreatePromotions.BTN_CANCEL_ITEM_POPUP, Constants.THREE_SECOND);
			foundation.click(CreatePromotions.BTN_CANCEL_ITEM_POPUP);
			foundation.click(CreatePromotions.BTN_CREATE);
			foundation.waitforElement(CreatePromotions.POP_UP_MESSAGES, Constants.MEDIUM_TIME);

			List<String> popupFieldType = foundation.getTextofListElement(CreatePromotions.POP_UP_MESSAGES);
			List<String> popupField = null;
			popupField = Arrays.asList(popupFieldType.get(0).split(Constants.DELIMITER_COMMA));
			popupField = Arrays.asList(popupField.get(0).split(Constants.NEW_LINE));

			List<String> actualData = Arrays
					.asList(rstLocationData.get(CNLocation.ACTUAL_DATA).split(Constants.DELIMITER_TILD));
			CustomisedAssert.assertEquals(popupField.get(0), actualData.get(0));
			CustomisedAssert.assertEquals(popupField.get(1), actualData.get(1));

			List<String> popupFieldArray = createPromotions.getPopUpData();
			String currentDate = dateAndTime.getDateAndTime(Constants.REGEX_MMDDYY, Constants.TIME_ZONE_INDIA);

			CustomisedAssert.assertTrue(popupFieldArray.get(0).contains(promotionType));
			CustomisedAssert.assertTrue(popupFieldArray.get(1).contains(promotionName));
			CustomisedAssert.assertTrue(popupFieldArray.get(2).contains(promotionName));
			CustomisedAssert.assertEquals(popupFieldArray.get(3), actualData.get(2));
			CustomisedAssert.assertEquals(popupFieldArray.get(4), actualData.get(3));
			foundation.threadWait(Constants.SHORT_TIME);
			CustomisedAssert.assertEquals(popupFieldArray.get(5), actualData.get(4));
			CustomisedAssert.assertEquals(popupFieldArray.get(6), actualData.get(5));
			CustomisedAssert.assertTrue(popupFieldArray.get(7).contains(currentDate));
			CustomisedAssert.assertTrue(popupFieldArray.get(8).contains(currentDate));
			foundation.click(CreatePromotions.BTN_OK);

			// Validating promotion is displayed
			foundation.waitforElement(PromotionList.PAGE_TITLE, Constants.MEDIUM_TIME);
			promotionList.verifyPromotionName(promotionName);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// Resetting the data
			promotionList.expirePromotion(menu.get(1), promotionName, statusType, gridName);
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
		String orgName = propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE);
		String displayName = strings.getRandomCharacter();
		List<String> requiredData = Arrays
				.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> actualData = Arrays
				.asList(rstLocationData.get(CNLocation.ACTUAL_DATA).split(Constants.DELIMITER_TILD));
		String promotionName = strings.getRandomCharacter();
		String statusType = rstLocationData.get(CNLocation.COLUMN_VALUE);
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			foundation.waitforElement(PromotionList.PAGE_TITLE, Constants.SHORT_TIME);

			// Basic Information Page
			foundation.click(PromotionList.BTN_CREATE);
			createPromotions.newPromotion(promotionType, promotionName, displayName, orgName, locationName);
			foundation.waitforElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);

			// Detail page
			dropDown.selectItem(CreatePromotions.DPD_DISCOUNT_BY, requiredData.get(0), Constants.TEXT);
//			textBox.enterText(CreatePromotions.TXT_ITEM, actualData.get(0));
//			foundation.threadWait(Constants.ONE_SECOND);
//			textBox.enterText(CreatePromotions.TXT_ITEM, Keys.ENTER);
			foundation.click(CreatePromotions.ADD_ITEM);
			textBox.enterText(CreatePromotions.ITEM_SEARCH_TXT, actualData.get(0));
			foundation.threadWait(Constants.SHORT_TIME);
			checkBox.check(CreatePromotions.SELECT_ITEM_PRODUCT);
			foundation.waitforElementToBeVisible(CreatePromotions.BTN_CANCEL_ITEM_POPUP, Constants.THREE_SECOND);
			foundation.click(CreatePromotions.BTN_CANCEL_ITEM_POPUP);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_CREATE);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_OK);
			foundation.threadWait(Constants.SHORT_TIME);

			// Updating the Item to Category
			textBox.enterText(PromotionList.TXT_SEARCH_PROMONAME, promotionName);
			foundation.click(PromotionList.BTN_SEARCH);
			foundation.doubleClick(PromotionList.TBL_COLUMN_NAME);
			foundation.waitforElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.threadWait(Constants.SHORT_TIME);
//			dropDown.selectItem(CreatePromotions.DPD_DISCOUNT_BY, requiredData.get(2), Constants.TEXT);
//			textBox.enterText(CreatePromotions.SEARCH_CATEGORY, actualData.get(1));
//			foundation.threadWait(Constants.ONE_SECOND);
//			textBox.enterText(CreatePromotions.SEARCH_CATEGORY, Keys.ENTER);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.threadWait(Constants.ONE_SECOND);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.threadWait(Constants.SHORT_TIME);
			dropDown.selectItem(CreatePromotions.DPD_DISCOUNT_BY, requiredData.get(2), Constants.TEXT);
			foundation.waitforElementToBeVisible(CreatePromotions.ADD_CATEGORY, Constants.THREE_SECOND);
			foundation.click(CreatePromotions.ADD_CATEGORY);
			textBox.enterText(CreatePromotions.CATEGORY_SEARCH_TXT, actualData.get(1));
			foundation.threadWait(Constants.SHORT_TIME);
			checkBox.check(CreatePromotions.SELECT_CAT_PRODUCT);
			foundation.waitforElementToBeVisible(CreatePromotions.BTN_CANCEL_CAT_POPUP, Constants.THREE_SECOND);
			foundation.click(CreatePromotions.BTN_CANCEL_CAT_POPUP);
			foundation.threadWait(Constants.THREE_SECOND);
			foundation.click(CreatePromotions.BTN_CREATE);
			foundation.waitforElement(CreatePromotions.BUNDLE_PROMO_ALERT, Constants.SHORT_TIME);
			// foundation.click(CreatePromotions.BTN_CONTINUE);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_OK);

			// Verify Item correctly updated in Promotion Screen
			foundation.waitforElement(PromotionList.TXT_SEARCH_PROMONAME, Constants.SHORT_TIME);
			textBox.enterText(PromotionList.TXT_SEARCH_PROMONAME, promotionName);
			foundation.click(PromotionList.BTN_SEARCH);
			foundation.doubleClick(PromotionList.TBL_COLUMN_NAME);
			foundation.waitforElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.threadWait(Constants.SHORT_TIME);

			String bundleOption = dropDown.getSelectedItem(CreatePromotions.DPD_DISCOUNT_BY);
			CustomisedAssert.assertEquals(bundleOption, requiredData.get(2));
			foundation.waitforElementToBeVisible(CreatePromotions.ADD_CATEGORY, Constants.THREE_SECOND);
			foundation.click(CreatePromotions.ADD_CATEGORY);
			textBox.enterText(CreatePromotions.CATEGORY_SEARCH_TXT, actualData.get(1));
			foundation.threadWait(Constants.SHORT_TIME);
			String itemValue = foundation.getText(CreatePromotions.CATEGORY_SELECTED);
			CustomisedAssert.assertTrue(itemValue.contains(actualData.get(1)));
			foundation.waitforElementToBeVisible(CreatePromotions.BTN_CANCEL_CAT_POPUP, Constants.THREE_SECOND);
			foundation.click(CreatePromotions.BTN_CANCEL_CAT_POPUP);
			foundation.threadWait(Constants.THREE_SECOND);
//			foundation.click(CreatePromotions.BTN_NEXT);
//			foundation.waitforElement(CreatePromotions.BTN_CONTINUE, Constants.SHORT_TIME);
//			foundation.click(CreatePromotions.BTN_CONTINUE);
//			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);
//			foundation.click(CreatePromotions.BTN_OK);
			createPromotions.cancelingPromotion();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// Expire Promotion
			promotionList.expirePromotion(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM), promotionName,
					statusType, gridName);
		}
	}

	@Test(description = "141831-This test validates the Category getting updated in Promotion Screen- Bundle Promotion")
	public void verifyCategoryInPromotionScreen() {
		final String CASE_NUM = "141831";

		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
		rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);

		String promotionType = rstLocationData.get(CNLocation.PROMOTION_TYPE);
		String gridName = rstLocationData.get(CNLocation.TAB_NAME);
		String orgName = propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE);
		String locationName = rstLocationData.get(CNLocation.LOCATION_NAME);
		List<String> requiredData = Arrays
				.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> actualData = Arrays
				.asList(rstLocationData.get(CNLocation.ACTUAL_DATA).split(Constants.DELIMITER_TILD));
		String promotionName = strings.getRandomCharacter();
		String statusType = rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION);
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			foundation.waitforElement(PromotionList.PAGE_TITLE, Constants.SHORT_TIME);

			// Basic Information Page
//			foundation.click(PromotionList.BTN_CREATE);
//			foundation.isDisplayed(CreatePromotions.LBL_CREATE_PROMOTION);
//			foundation.isDisplayed(CreatePromotions.LBL_BASIC_INFORMATION);
//			dropDown.selectItem(CreatePromotions.DPD_PROMO_TYPE, promotionType, Constants.TEXT);
//			textBox.enterText(CreatePromotions.TXT_PROMO_NAME, promotionName);
//			textBox.enterText(CreatePromotions.TXT_DISPLAY_NAME, promotionName);
//			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.click(PromotionList.BTN_CREATE);
			createPromotions.newPromotion(promotionType, promotionName, promotionName, orgName, locationName);
			foundation.waitforElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);

			// Filter Page
//			foundation.threadWait(Constants.THREE_SECOND);
//			dropDown.selectItem(CreatePromotions.DPD_ORG,
//					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE),
//					Constants.TEXT);
//			foundation.click(CreatePromotions.BTN_ORG_RIGHT);
//			foundation.threadWait(Constants.THREE_SECOND);
//			dropDown.selectItem(CreatePromotions.DPD_LOC, locationName, Constants.TEXT);
//			foundation.click(CreatePromotions.BTN_LOC_RIGHT);
//			foundation.click(CreatePromotions.BTN_NEXT);
//			foundation.waitforElementToBeVisible(CreatePromotions.LBL_BUNDLE_DETAILS, 5);

			// Detail page

//			textBox.enterText(CreatePromotions.SEARCH_CATEGORY, actualData.get(1));
//			foundation.threadWait(Constants.ONE_SECOND);
//			textBox.enterText(CreatePromotions.SEARCH_CATEGORY, Keys.ENTER);
			dropDown.selectItem(CreatePromotions.DPD_DISCOUNT_BY, requiredData.get(1), Constants.TEXT);
			foundation.waitforElementToBeVisible(CreatePromotions.ADD_CATEGORY, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.ADD_CATEGORY);
			textBox.enterText(CreatePromotions.CATEGORY_SEARCH_TXT, actualData.get(1));
			foundation.threadWait(Constants.SHORT_TIME);
			checkBox.check(CreatePromotions.SELECT_CAT_PRODUCT);
			foundation.waitforElementToBeVisible(CreatePromotions.BTN_CANCEL_CAT_POPUP, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_CANCEL_CAT_POPUP);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_CREATE);
			foundation.waitforElement(CreatePromotions.BUNDLE_PROMO_ALERT, Constants.SHORT_TIME);
			// foundation.click(CreatePromotions.BTN_CONTINUE);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_OK);

			// Updating the Item to Category
			foundation.waitforElement(PromotionList.PAGE_TITLE, Constants.SHORT_TIME);
			textBox.enterText(PromotionList.TXT_SEARCH_PROMONAME, promotionName);
			foundation.click(PromotionList.BTN_SEARCH);
			foundation.doubleClick(PromotionList.TBL_COLUMN_NAME);
			foundation.waitforElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.threadWait(Constants.ONE_SECOND);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.waitforElement(CreatePromotions.DPD_DISCOUNT_BY, Constants.SHORT_TIME);
//			dropDown.selectItem(CreatePromotions.DPD_DISCOUNT_BY, requiredData.get(0), Constants.TEXT);
//			textBox.enterText(CreatePromotions.TXT_ITEM, actualData.get(0));
//			foundation.threadWait(Constants.ONE_SECOND);
//			textBox.enterText(CreatePromotions.TXT_ITEM, Keys.ENTER);
			dropDown.selectItem(CreatePromotions.DPD_DISCOUNT_BY, requiredData.get(0), Constants.TEXT);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(CreatePromotions.ADD_ITEM);
			textBox.enterText(CreatePromotions.ITEM_SEARCH_TXT, actualData.get(0));
			foundation.threadWait(Constants.SHORT_TIME);
			checkBox.check(CreatePromotions.SELECT_ITEM_PRODUCT);
			foundation.waitforElementToBeVisible(CreatePromotions.BTN_CANCEL_ITEM_POPUP, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_CANCEL_ITEM_POPUP);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_CREATE);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_OK);
			foundation.threadWait(Constants.SHORT_TIME);

			// Verify Item correctly updated in Promotion Screen
			textBox.enterText(PromotionList.TXT_SEARCH_PROMONAME, promotionName);
			foundation.click(PromotionList.BTN_SEARCH);
			foundation.doubleClick(PromotionList.TBL_COLUMN_NAME);
			foundation.waitforElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.threadWait(Constants.SHORT_TIME);

			String bundleOption = dropDown.getSelectedItem(CreatePromotions.DPD_DISCOUNT_BY);
			CustomisedAssert.assertEquals(bundleOption, requiredData.get(0));
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(CreatePromotions.ADD_ITEM);
			textBox.enterText(CreatePromotions.ITEM_SEARCH_TXT, actualData.get(0));
			foundation.threadWait(Constants.SHORT_TIME);
			String itemValue = foundation.getText(CreatePromotions.SELECTED_ITEM);
			CustomisedAssert.assertTrue(itemValue.contains(actualData.get(0)));
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.waitforElementToBeVisible(CreatePromotions.BTN_CANCEL_ITEM_POPUP, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_CANCEL_ITEM_POPUP);
			foundation.threadWait(Constants.SHORT_TIME);
//			String itemValue = dropDown.getSelectedItem(EditPromotion.DPD_ITEM);
//			CustomisedAssert.assertEquals(itemValue, actualData.get(0));
//			foundation.click(CreatePromotions.BTN_NEXT);
//			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);
//			foundation.click(CreatePromotions.BTN_OK);
			createPromotions.cancelingPromotion();

		} catch (Exception exc) {

			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// Resetting the data
			promotionList.expirePromotion(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM), promotionName,
					statusType, gridName);
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
		String organization = propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE);
		String displayName = strings.getRandomCharacter();

		List<String> requiredData = Arrays
				.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> actualData = Arrays
				.asList(rstLocationData.get(CNLocation.ACTUAL_DATA).split(Constants.DELIMITER_TILD));
		String promotionName = strings.getRandomCharacter();
		String statusType = rstLocationData.get(CNLocation.COLUMN_VALUE);
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// select admin-promotion menu
			navigationBar.selectOrganization(organization);
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Create new promotion
			foundation.click(PromotionList.BTN_CREATE);
			createPromotions.newPromotion(promotionType, promotionName, displayName, organization, locationName);
			foundation.click(CreatePromotions.BTN_NEXT);
			dropDown.selectItem(CreatePromotions.DPD_DISCOUNT_BY, requiredData.get(0), Constants.TEXT);
//			textBox.enterText(CreatePromotions.TXT_ITEMS, requiredData.get(1));
//			foundation.threadWait(Constants.ONE_SECOND);
//			textBox.enterText(CreatePromotions.TXT_ITEMS, Keys.ENTER);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(CreatePromotions.ADD_ITEM);
			textBox.enterText(CreatePromotions.ITEM_SEARCH_TXT, requiredData.get(1));
			foundation.threadWait(Constants.SHORT_TIME);
			checkBox.check(CreatePromotions.SELECT_ITEM_PRODUCT);
			foundation.waitforElementToBeVisible(CreatePromotions.BTN_CANCEL_ITEM_POPUP, Constants.THREE_SECOND);
			foundation.click(CreatePromotions.BTN_CANCEL_ITEM_POPUP);
			dropDown.selectItem(CreatePromotions.DPD_DISCOUNT_TYPE, requiredData.get(2), Constants.TEXT);
			textBox.enterText(CreatePromotions.TXT_DISCOUNT_PERCENTAGE, requiredData.get(3));
			textBox.enterText(CreatePromotions.TXT_TRANSACTION_MIN, requiredData.get(4));
			checkBox.check(CreatePromotions.CHK_PROMO_RESTRICTION);
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.TXT_PER_TRANSACTION_LIMIT));
			checkBox.unCheck(CreatePromotions.CHK_PROMO_RESTRICTION);
			CustomisedAssert.assertFalse(foundation.isDisplayed(CreatePromotions.TXT_PER_TRANSACTION_LIMIT));
			dropDown.selectItem(CreatePromotions.DPD_DISCOUNT_TIME, requiredData.get(5), Constants.TEXT);
			dropDown.selectItem(CreatePromotions.DPD_DURATION, requiredData.get(6), Constants.TEXT);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);

			// verify promotion details in popup
			List<String> popupFieldType = foundation.getTextofListElement(CreatePromotions.POP_UP_MESSAGES);
			List<String> popupField = null;
			popupField = Arrays.asList(popupFieldType.get(0).split(Constants.DELIMITER_COMMA));
			popupField = Arrays.asList(popupField.get(0).split(Constants.NEW_LINE));
			CustomisedAssert.assertEquals(popupField.get(0), actualData.get(0));
			CustomisedAssert.assertEquals(popupField.get(1), actualData.get(1));
			List<String> popupFieldArray = createPromotions.getPopUpData();
			String currentDate = dateAndTime.getDateAndTime(Constants.REGEX_MMDDYY, Constants.TIME_ZONE_INDIA);
			CustomisedAssert.assertTrue(popupFieldArray.get(0).contains(promotionType));
			CustomisedAssert.assertTrue(popupFieldArray.get(1).contains(promotionName));
			CustomisedAssert.assertTrue(popupFieldArray.get(2).contains(displayName));
			CustomisedAssert.assertEquals(popupFieldArray.get(3), actualData.get(2));
			CustomisedAssert.assertEquals(popupFieldArray.get(4), actualData.get(3));
			foundation.threadWait(Constants.SHORT_TIME);
			CustomisedAssert.assertEquals(popupFieldArray.get(5), actualData.get(4));
			CustomisedAssert.assertEquals(popupFieldArray.get(6), actualData.get(5));
			CustomisedAssert.assertTrue(popupFieldArray.get(7).contains(currentDate));
			CustomisedAssert.assertTrue(popupFieldArray.get(8).contains(currentDate));
			foundation.click(CreatePromotions.BTN_OK);

			// navigate back to same promotion and verify all the details are populating as
			// entered
			foundation.waitforElement(PromotionList.TXT_SEARCH_PROMONAME, Constants.MEDIUM_TIME);
			textBox.enterText(PromotionList.TXT_SEARCH_PROMONAME, promotionName);
			foundation.click(PromotionList.BTN_SEARCH);
			foundation.doubleClick(PromotionList.TBL_COLUMN_NAME);
			CustomisedAssert.assertTrue(checkBox.isChecked(EditPromotion.CHK_ACTIVE));
			foundation.waitforElementToBeVisible(CreatePromotions.DPD_PROMO_TYPE, Constants.THREE_SECOND);
			CustomisedAssert
					.assertTrue(dropDown.getSelectedItem(CreatePromotions.DPD_PROMO_TYPE).equals(promotionType));
			CustomisedAssert
					.assertTrue(textBox.getTextFromInput(CreatePromotions.TXT_PROMO_NAME).equals(promotionName));
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.waitforElement(CreatePromotions.TXT_SEARCH_ORGPAGE, Constants.SHORT_TIME);
			textBox.enterText(CreatePromotions.TXT_SEARCH_ORGPAGE, organization);
			foundation.threadWait(Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.getText(CreatePromotions.SELECTED_ORG).equals(organization));
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.waitforElement(CreatePromotions.TXT_LOC_SEARCH, Constants.SHORT_TIME);
			textBox.enterText(CreatePromotions.TXT_LOC_SEARCH, locationName);
			foundation.threadWait(Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.getText(CreatePromotions.SELECTED_LOCATION).equals(locationName));
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(EditPromotion.BTN_UPDATE);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.MEDIUM_TIME);
			foundation.click(CreatePromotions.BTN_OK);
			foundation.threadWait(Constants.SHORT_TIME);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// Resetting the data
			promotionList.expirePromotion(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM), promotionName,
					statusType, gridName);
		}
	}

	@Test(description = "141786-To Verify Creating On-Screen promotion - Discount by Category")
	public void verifyOnScreenPromotionDiscountByCategory() {
		final String CASE_NUM = "141786";

		browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
		login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
				propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);

		String promotionType = rstLocationData.get(CNLocation.PROMOTION_TYPE);
		String gridName = rstLocationData.get(CNLocation.TAB_NAME);
		String locationName = rstLocationData.get(CNLocation.LOCATION_NAME);
		String organization = propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE);
		String displayName = strings.getRandomCharacter();

		List<String> requiredData = Arrays
				.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> actualData = Arrays
				.asList(rstLocationData.get(CNLocation.ACTUAL_DATA).split(Constants.DELIMITER_TILD));
		String promotionName = strings.getRandomCharacter();
		String statusType = rstLocationData.get(CNLocation.COLUMN_VALUE);
		try {

			// select admin-promotion menu
			navigationBar.selectOrganization(organization);
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Create new promotion
			foundation.click(PromotionList.BTN_CREATE);
			createPromotions.newPromotion(promotionType, promotionName, displayName, organization, locationName);
			foundation.click(CreatePromotions.BTN_NEXT);
			dropDown.selectItem(CreatePromotions.DPD_DISCOUNT_BY, requiredData.get(0), Constants.TEXT);
//			textBox.enterText(CreatePromotions.TXT_CATEGORYS, requiredData.get(1));
//			foundation.threadWait(Constants.ONE_SECOND);
//			textBox.enterText(CreatePromotions.TXT_CATEGORYS, Keys.ENTER);
			dropDown.selectItem(CreatePromotions.DPD_DISCOUNT_BY, requiredData.get(0), Constants.TEXT);
			foundation.waitforElementToBeVisible(CreatePromotions.ADD_CATEGORY, Constants.THREE_SECOND);
			foundation.click(CreatePromotions.ADD_CATEGORY);
			textBox.enterText(CreatePromotions.CATEGORY_SEARCH_TXT, requiredData.get(1));
			foundation.threadWait(Constants.SHORT_TIME);
			checkBox.check(CreatePromotions.SELECT_CAT_PRODUCT);
			foundation.waitforElementToBeVisible(CreatePromotions.BTN_CANCEL_CAT_POPUP, Constants.THREE_SECOND);
			foundation.click(CreatePromotions.BTN_CANCEL_CAT_POPUP);
			dropDown.selectItem(CreatePromotions.DPD_DISCOUNT_TYPE, requiredData.get(2), Constants.TEXT);
			textBox.enterText(CreatePromotions.TXT_AMOUNT, requiredData.get(3));
			textBox.enterText(CreatePromotions.TXT_TRANSACTION_MIN, requiredData.get(4));
			checkBox.check(CreatePromotions.CHK_PROMO_RESTRICTION);
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.TXT_PER_TRANSACTION_LIMIT));
			checkBox.unCheck(CreatePromotions.CHK_PROMO_RESTRICTION);
			CustomisedAssert.assertFalse(foundation.isDisplayed(CreatePromotions.TXT_PER_TRANSACTION_LIMIT));
			dropDown.selectItem(CreatePromotions.DPD_DISCOUNT_TIME, requiredData.get(5), Constants.TEXT);
			dropDown.selectItem(CreatePromotions.DPD_DURATION, requiredData.get(6), Constants.TEXT);
			foundation.threadWait(Constants.ONE_SECOND);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);

			// verify promotion details in popup
			List<String> popupFieldType = foundation.getTextofListElement(CreatePromotions.POP_UP_MESSAGES);
			List<String> popupField = null;
			popupField = Arrays.asList(popupFieldType.get(0).split(Constants.DELIMITER_COMMA));
			popupField = Arrays.asList(popupField.get(0).split(Constants.NEW_LINE));
			CustomisedAssert.assertEquals(popupField.get(0), actualData.get(0));
			CustomisedAssert.assertEquals(popupField.get(1), actualData.get(1));
			foundation.threadWait(Constants.THREE_SECOND);
			List<String> popupFieldArray = createPromotions.getPopUpData();
			String currentDate = dateAndTime.getDateAndTime(Constants.REGEX_MMDDYY, Constants.TIME_ZONE_INDIA);
			CustomisedAssert.assertTrue(popupFieldArray.get(0).contains(promotionType));
			CustomisedAssert.assertTrue(popupFieldArray.get(1).contains(promotionName));
			CustomisedAssert.assertTrue(popupFieldArray.get(2).contains(displayName));
			CustomisedAssert.assertEquals(popupFieldArray.get(3), actualData.get(2));
			CustomisedAssert.assertEquals(popupFieldArray.get(4), actualData.get(3));
			foundation.threadWait(Constants.SHORT_TIME);
			CustomisedAssert.assertEquals(popupFieldArray.get(5), actualData.get(4));
			CustomisedAssert.assertEquals(popupFieldArray.get(6), actualData.get(5));
			CustomisedAssert.assertTrue(popupFieldArray.get(7).contains(currentDate));
			CustomisedAssert.assertTrue(popupFieldArray.get(8).contains(currentDate));
			foundation.click(CreatePromotions.BTN_OK);

			// navigate back to same promotion and verify all the details are populating as
			// entered
			foundation.waitforElement(PromotionList.PAGE_TITLE, Constants.SHORT_TIME);
			textBox.enterText(PromotionList.TXT_SEARCH_PROMONAME, promotionName);
			foundation.click(PromotionList.BTN_SEARCH);
			foundation.doubleClick(PromotionList.TBL_COLUMN_NAME);
			CustomisedAssert.assertTrue(checkBox.isChecked(EditPromotion.CHK_ACTIVE));
			foundation.threadWait(Constants.SHORT_TIME);
			CustomisedAssert
					.assertTrue(dropDown.getSelectedItem(CreatePromotions.DPD_PROMO_TYPE).equals(promotionType));
			CustomisedAssert
					.assertTrue(textBox.getTextFromInput(CreatePromotions.TXT_PROMO_NAME).equals(promotionName));
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.waitforElement(CreatePromotions.TXT_SEARCH_ORGPAGE, Constants.SHORT_TIME);
			textBox.enterText(CreatePromotions.TXT_SEARCH_ORGPAGE, organization);
			foundation.threadWait(Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.getText(CreatePromotions.SELECTED_ORG).equals(organization));
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.waitforElement(CreatePromotions.TXT_LOC_SEARCH, Constants.SHORT_TIME);
			textBox.enterText(CreatePromotions.TXT_LOC_SEARCH, locationName);
			foundation.threadWait(Constants.TWO_SECOND);
			CustomisedAssert.assertTrue(foundation.getText(CreatePromotions.SELECTED_LOCATION).equals(locationName));
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);

			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(EditPromotion.BTN_UPDATE);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_OK);
			foundation.threadWait(Constants.SHORT_TIME);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// Resetting the data
			promotionList.expirePromotion(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM), promotionName,
					statusType, gridName);
		}
	}

	@Test(description = "141776-To Verify Creating Tender Discount promotion (Tender Type set as Account)")
	public void verifyTenderDiscountPromo() {
		final String CASE_NUM = "141776";

		browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
		login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
				propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);

		String promotionName = strings.getRandomCharacter();
		String promotionType = rstLocationData.get(CNLocation.PROMOTION_TYPE);
		String orgName = propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE);
		// String requiredData = rstLocationData.get(CNLocation.REQUIRED_DATA);
		String gridName = rstLocationData.get(CNLocation.TAB_NAME);
		String statusType = rstLocationData.get(CNLocation.LOCATION_NAME);
		List<String> requiredData = Arrays
				.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));

		try {
			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// validate UI of promotion list page
			CustomisedAssert.assertTrue(foundation.isDisplayed(PromotionList.TXT_SEARCH_PROMONAME));
			CustomisedAssert.assertTrue(foundation.isDisplayed(PromotionList.CALENDER_DATE_RANGE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(PromotionList.DPD_LOCATION));
			CustomisedAssert.assertTrue(foundation.isDisplayed(PromotionList.DPD_STATUS));
			CustomisedAssert.assertTrue(foundation.isDisplayed(PromotionList.DPD_PROMOTYPE));
			foundation.threadWait(Constants.SHORT_TIME);
			CustomisedAssert.assertEquals(foundation.getText(PromotionList.LBL_SEARCH), requiredData.get(0));
			CustomisedAssert.assertEquals(foundation.getText(PromotionList.LBL_CALENDER_DATE_RANGE),
					requiredData.get(1));
			CustomisedAssert.assertEquals(foundation.getText(PromotionList.LBL_LOCATION), requiredData.get(2));
			CustomisedAssert.assertEquals(foundation.getText(PromotionList.LBL_STATUS), requiredData.get(3));
			CustomisedAssert.assertEquals(foundation.getText(PromotionList.LBL_PROMOTYPE), requiredData.get(4));

			// Creating New Promotion
			foundation.click(PromotionList.BTN_CREATE);

			// validate UI of enter promotion basics page
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.DPD_PROMO_TYPE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.TXT_PROMO_NAME));
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.BTN_NEXT));
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.BTN_CANCEL));
			CustomisedAssert.assertEquals(foundation.getText(CreatePromotions.LBL_BASICINFO), requiredData.get(5));
			CustomisedAssert.assertEquals(foundation.getText(CreatePromotions.LBL_ENTER_BASICINFO),
					requiredData.get(6));

			// provide basic info and navigate to next
//			dropDown.selectItem(CreatePromotions.DPD_PROMO_TYPE, promotionType, Constants.TEXT);
//			textBox.enterText(CreatePromotions.TXT_PROMO_NAME, promotionName);
//			foundation.click(CreatePromotions.BTN_NEXT);
//
//			foundation.threadWait(Constants.ONE_SECOND);
			dropDown.selectItem(CreatePromotions.DPD_PROMO_TYPE, promotionType, Constants.TEXT);
			textBox.enterText(CreatePromotions.TXT_PROMO_NAME, promotionName);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.threadWait(Constants.SHORT_TIME);

			// validate UI of enter promotion filter page
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.TXT_SEARCH_ORGPAGE));
//			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.DPD_LOCATION));
//			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.BTN_NEXT));
//			CustomisedAssert.assertEquals(foundation.getText(CreatePromotions.LBL_FILTER), requiredData.get(7));
//			CustomisedAssert.assertEquals(foundation.getText(CreatePromotions.LBL_SELECT_CRITERIA),
//					requiredData.get(8));
			foundation.waitforElement(CreatePromotions.TXT_SEARCH_ORGPAGE, Constants.SHORT_TIME);
			textBox.enterText(CreatePromotions.TXT_SEARCH_ORGPAGE, orgName);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(CreatePromotions.CHECKBOX_ORG);
			foundation.waitforElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.threadWait(Constants.SHORT_TIME);

			// choose promotion filter
			/*
			 * dropDown.selectItem(CreatePromotions.DPD_ORG, orgName, Constants.TEXT);
			 * foundation.click(CreatePromotions.BTN_ORG_RIGHT);
			 * foundation.threadWait(Constants.TWO_SECOND);
			 * foundation.click(CreatePromotions.SELECT_ALL_LOCATION);
			 * foundation.waitforElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);
			 * foundation.click(CreatePromotions.BTN_NEXT);
			 * foundation.threadWait(Constants.ONE_SECOND);
			 */
			foundation.waitforElement(CreatePromotions.ALL_LOCATION, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.ALL_LOCATION);
			foundation.waitforElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.threadWait(Constants.SHORT_TIME);

			// validate UI of enter promotion filter page
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.MULTI_SELECT_TENDER_TYPES));
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.DPD_DISCOUNT_TYPE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.DPD_APPLY_DISCOUNT_TO));
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.TXT_TRANSACTION_MIN));
			foundation.threadWait(Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.DPD_DISCOUNT_TIME));
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.BTN_CREATE));
			CustomisedAssert.assertEquals(foundation.getText(CreatePromotions.LBL_DETAILS), requiredData.get(9));
			CustomisedAssert.assertEquals(foundation.getText(CreatePromotions.LBL_SET_PROMO_DETAILS),
					requiredData.get(10));

			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.threadWait(Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.LBL_TENDERTYPE_ERROR));

			dropDown.selectItem(CreatePromotions.MULTI_SELECT_TENDER_TYPES, requiredData.get(11), Constants.TEXT);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.LONG_TIME);
			foundation.click(CreatePromotions.BTN_OK);
			foundation.threadWait(Constants.SHORT_TIME);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// Resetting the data
			promotionList.expirePromotion(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM), promotionName,
					statusType, gridName);
		}
	}

	@Test(description = "141780-To Verify Creating Tender Discount promotion (Tender Type set as Credit)")
	public void verifyTenderDiscountPromotions() {
		final String CASE_NUM = "141780";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);

		String promotionType = rstLocationData.get(CNLocation.PROMOTION_TYPE);
		String gridName = rstLocationData.get(CNLocation.TAB_NAME);
		String promotionName = strings.getRandomCharacter();
		String orgName = propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE);
		String statusType = rstLocationData.get(CNLocation.LOCATION_NAME);
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
			foundation.isDisplayed(CreatePromotions.LBL_CREATE_PROMOTION);
			dropDown.selectItem(CreatePromotions.DPD_PROMO_TYPE, promotionType, Constants.TEXT);
			String basicInfoPageTitle = foundation.getText(CreatePromotions.LBL_PAGE_TITLE);
			CustomisedAssert.assertTrue(basicInfoPageTitle.equals(rstLocationData.get(CNLocation.PROMOTION_TYPE)));

			textBox.enterText(CreatePromotions.TXT_PROMO_NAME, promotionName);
			foundation.click(CreatePromotions.BTN_NEXT);
			String filtersPageTitle = foundation.getText(CreatePromotions.LBL_PAGE_TITLE);
			CustomisedAssert.assertTrue(filtersPageTitle.equals(rstLocationData.get(CNLocation.PROMOTION_TYPE)));
//			dropDown.selectItem(CreatePromotions.DPD_ORG, orgName, Constants.TEXT);
//			foundation.click(CreatePromotions.BTN_ORG_RIGHT);
//			foundation.threadWait(Constants.TWO_SECOND);
//			foundation.click(CreatePromotions.SELECT_ALL_LOCATION);
//			foundation.waitforElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);
//			foundation.click(CreatePromotions.BTN_NEXT);
//			foundation.threadWait(Constants.TWO_SECOND);
			foundation.waitforElement(CreatePromotions.TXT_SEARCH_ORGPAGE, Constants.SHORT_TIME);
			textBox.enterText(CreatePromotions.TXT_SEARCH_ORGPAGE, orgName);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(CreatePromotions.CHECKBOX_ORG);
			foundation.waitforElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.waitforElement(CreatePromotions.ALL_LOCATION, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.ALL_LOCATION);
			foundation.waitforElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.threadWait(Constants.SHORT_TIME);

			List<String> requiredData = Arrays
					.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			dropDown.selectItem(CreatePromotions.MULTI_SELECT_TENDER_TYPES, requiredData.get(0), Constants.TEXT);
			dropDown.selectItem(CreatePromotions.DPD_DISCOUNT_TYPE, requiredData.get(1), Constants.TEXT);
			dropDown.selectItem(CreatePromotions.DPD_APPLY_DISCOUNT_TO, requiredData.get(2), Constants.TEXT);
			textBox.enterText(CreatePromotions.TXT_AMOUNT, requiredData.get(3));
			textBox.enterText(CreatePromotions.TXT_TRANSACTION_MIN, requiredData.get(4));
			dropDown.selectItem(CreatePromotions.DPD_DISCOUNT_TIME, requiredData.get(5), Constants.TEXT);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.MEDIUM_TIME);
			foundation.threadWait(Constants.SHORT_TIME);

			List<String> popupFieldType = foundation.getTextofListElement(CreatePromotions.POP_UP_MESSAGES);
			List<String> popupField = null;
			popupField = Arrays.asList(popupFieldType.get(0).split(Constants.DELIMITER_COMMA));
			popupField = Arrays.asList(popupField.get(0).split(Constants.NEW_LINE));
			List<String> actualData = Arrays
					.asList(rstLocationData.get(CNLocation.ACTUAL_DATA).split(Constants.DELIMITER_TILD));
			CustomisedAssert.assertEquals(popupField.get(0), actualData.get(0));
			CustomisedAssert.assertEquals(popupField.get(1), actualData.get(1));
			List<String> popupFieldArray = createPromotions.getPopUpData();
			String currentDate = dateAndTime.getDateAndTime(Constants.REGEX_MMDDYY, Constants.TIME_ZONE_INDIA);

			CustomisedAssert.assertTrue(popupFieldArray.get(0).contains(promotionType));
			CustomisedAssert.assertTrue(popupFieldArray.get(1).contains(promotionName));
			CustomisedAssert.assertEquals(popupFieldArray.get(2), actualData.get(2));
			CustomisedAssert.assertEquals(popupFieldArray.get(3), actualData.get(3));
			foundation.threadWait(Constants.SHORT_TIME);
			CustomisedAssert.assertEquals(popupFieldArray.get(4), actualData.get(4));
			CustomisedAssert.assertEquals(popupFieldArray.get(5), actualData.get(5));
			CustomisedAssert.assertEquals(popupFieldArray.get(6), actualData.get(6));
			CustomisedAssert.assertTrue(popupFieldArray.get(7).contains(currentDate));
			CustomisedAssert.assertTrue(popupFieldArray.get(8).contains(currentDate));
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_OK);
			foundation.threadWait(Constants.SHORT_TIME);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// Resetting the data
			promotionList.expirePromotion(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM), promotionName,
					statusType, gridName);
		}
	}

	@Test(description = "141774 - SOS-7519 - Verify Create Promotion page display only active location when Org  filter is selected")
	public void verifyActiveLocationPromotions() {
		final String CASE_NUM = "141774";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);

		String promotionName = rstLocationData.get(CNLocation.PROMOTION_NAME) + strings.getRandomCharacter();
		String promotionType = rstLocationData.get(CNLocation.PROMOTION_TYPE);
		String orgName = propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE);
		String locationName = propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE);
		String requiredData = rstLocationData.get(CNLocation.REQUIRED_DATA);
		String gridName = rstLocationData.get(CNLocation.TAB_NAME);
		String statusType = rstLocationData.get(CNLocation.LOCATION_NAME);
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

			createPromotions.newPromotionUsingTenderDiscount(promotionType, promotionName, orgName, locationName);

			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.waitforElement(CreatePromotions.MULTI_SELECT_TENDER_TYPES, Constants.SHORT_TIME);
			dropDown.selectItem(CreatePromotions.MULTI_SELECT_TENDER_TYPES, requiredData, Constants.TEXT);

			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);

			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_OK);
			foundation.threadWait(Constants.SHORT_TIME);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// Resetting the data
			promotionList.expirePromotion(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM), promotionName,
					statusType, gridName);
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
		String org = propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE);
		String displayName = strings.getRandomCharacter();
		String statusType = rstLocationData.get(CNLocation.COLUMN_VALUE);

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
			createPromotions.newPromotion(promotionType, promotionName, displayName, org, locationName);
			foundation.waitforElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);
			List<String> category = Arrays
					.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			foundation.waitforElement(CreatePromotions.DPD_DISCOUNT_BY, Constants.SHORT_TIME);
			dropDown.selectItem(CreatePromotions.DPD_DISCOUNT_BY, category.get(0), Constants.TEXT);

//			foundation.click(CreatePromotions.SEARCH_CATEGORY);
//			textBox.enterText(CreatePromotions.SEARCH_CATEGORY, category.get(1));
//			foundation.threadWait(Constants.TWO_SECOND);
//			textBox.enterText(CreatePromotions.SEARCH_CATEGORY, Keys.ENTER);
			foundation.waitforElementToBeVisible(CreatePromotions.ADD_CATEGORY, Constants.THREE_SECOND);
			foundation.click(CreatePromotions.ADD_CATEGORY);
			textBox.enterText(CreatePromotions.CATEGORY_SEARCH_TXT, category.get(1));
			foundation.threadWait(Constants.SHORT_TIME);
			checkBox.check(CreatePromotions.SELECT_CAT_PRODUCT);
			foundation.waitforElementToBeVisible(CreatePromotions.BTN_CANCEL_CAT_POPUP, Constants.THREE_SECOND);
			foundation.click(CreatePromotions.BTN_CANCEL_CAT_POPUP);

			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_OK);
			foundation.threadWait(Constants.SHORT_TIME);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// Resetting the data
			promotionList.expirePromotion(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM), promotionName,
					statusType, gridName);
		}
	}

	@Test(description = "118619-Verify to create a promotion with Promotion Type as Tender Discount")
	public void verifyCreateTenderDiscountPromo() {
		final String CASE_NUM = "118619";

		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);
		rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);

		String promotionType = rstLocationData.get(CNLocation.PROMOTION_TYPE);
		String orgName = propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE);
		String locationName = propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE);
		String gridName = rstLocationData.get(CNLocation.TAB_NAME);
		List<String> requiredData = Arrays
				.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> actualData = Arrays
				.asList(rstLocationData.get(CNLocation.ACTUAL_DATA).split(Constants.DELIMITER_TILD));

		String promotionName = strings.getRandomCharacter();
		// List<String> locationList =
		// foundation.getTextofListElement(LocationList.LINK_LOCATION_LIST);
		List<String> menu = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		String statusType = rstLocationData.get(CNLocation.LOCATION_NAME);
		try {

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.navigateToMenuItem(menu.get(0));
			foundation.waitforElement(UserRoles.LBL_USER_LIST, Constants.SHORT_TIME);
			textBox.enterText(UserRoles.TXT_SEARCH_FILTER,
					propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(userRoles.getRowByText(rstLocationSummaryData.get(CNLocationSummary.CONTACT_EMAIL)));
			foundation.waitforElement(UserRoles.LBL_VIEW_ROLE, Constants.SHORT_TIME);
			foundation.click(UserList.LNK_ORG_REMOVE_ALL);
			List<String> orgs = Arrays
					.asList(rstLocationData.get(CNLocation.COLUMN_VALUE).split(Constants.DELIMITER_TILD));
			userList.selectOrgs(UserList.DPD_ORG, orgs);
			foundation.threadWait(Constants.SHORT_TIME);
			login.logout();

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// select admin-promotion menu
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menu.get(1));

			// navigate to create new promo page
			foundation.click(PromotionList.BTN_CREATE);
			foundation.isDisplayed(CreatePromotions.LBL_CREATE_PROMOTION);

			// select promotion and navigate to next
			createPromotions.newPromotionUsingTenderDiscount(promotionType, promotionName, orgName, locationName);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.threadWait(Constants.SHORT_TIME);

			// select promotion details
			dropDown.selectItem(CreatePromotions.MULTI_SELECT_TENDER_TYPES, requiredData.get(0), Constants.TEXT);
			dropDown.selectItem(CreatePromotions.DPD_DISCOUNT_TYPE, requiredData.get(1), Constants.TEXT);
			dropDown.selectItem(CreatePromotions.DPD_APPLY_DISCOUNT_TO, requiredData.get(2), Constants.TEXT);
			textBox.enterText(CreatePromotions.TXT_AMOUNT, requiredData.get(3));
			foundation.threadWait(Constants.SHORT_TIME);
			textBox.enterText(CreatePromotions.TXT_TRANSACTION_MIN, requiredData.get(4));
			dropDown.selectItem(CreatePromotions.DPD_DISCOUNT_TIME, requiredData.get(5), Constants.TEXT);
			createPromotions.recurringDay();
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.MEDIUM_TIME);

			// verify promotion details
			List<String> popupFieldType = foundation.getTextofListElement(CreatePromotions.POP_UP_MESSAGES);
			List<String> popupField = null;
			popupField = Arrays.asList(popupFieldType.get(0).split(Constants.DELIMITER_COMMA));
			popupField = Arrays.asList(popupField.get(0).split(Constants.NEW_LINE));
			CustomisedAssert.assertEquals(popupField.get(0), actualData.get(0));
			CustomisedAssert.assertEquals(popupField.get(1), actualData.get(1));
			List<String> popupFieldArray = createPromotions.getPopUpData();
			String currentDate = dateAndTime.getDateAndTime(Constants.REGEX_MMDDYY, Constants.TIME_ZONE_INDIA);
			CustomisedAssert.assertTrue(popupFieldArray.get(0).contains(promotionType));
			CustomisedAssert.assertTrue(popupFieldArray.get(1).contains(promotionName));
			CustomisedAssert.assertEquals(popupFieldArray.get(2), actualData.get(2));
			CustomisedAssert.assertEquals(popupFieldArray.get(3), actualData.get(3));
			CustomisedAssert.assertEquals(popupFieldArray.get(4), actualData.get(4));
			foundation.threadWait(Constants.SHORT_TIME);
			CustomisedAssert.assertEquals(popupFieldArray.get(5), actualData.get(5));
			CustomisedAssert.assertEquals(popupFieldArray.get(6), actualData.get(6));
			CustomisedAssert.assertTrue(popupFieldArray.get(7).contains(currentDate));
			CustomisedAssert.assertTrue(popupFieldArray.get(8).contains(currentDate));
			foundation.click(CreatePromotions.BTN_OK);
			foundation.threadWait(Constants.TWO_SECOND);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// Resetting the data
			promotionList.expirePromotion(menu.get(1), promotionName, statusType, gridName);
		}
	}

	@Test(description = "141820-Verify Operator will update the existing Promotion with Same Org/Location")
	public void verifyUpdatePromotion() {
		final String CASE_NUM = "141820";

		browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
		login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
				propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);

		String promotionType = rstLocationData.get(CNLocation.PROMOTION_TYPE);
		String gridName = rstLocationData.get(CNLocation.TAB_NAME);
		String organization = propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE);
		List<String> locationName = Arrays
				.asList(rstLocationData.get(CNLocation.LOCATION_NAME).split(Constants.DELIMITER_TILD));
		List<String> requiredData = Arrays
				.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		String promotionName = strings.getRandomCharacter();
		String statusType = rstLocationData.get(CNLocation.COLUMN_VALUE);
		try {
			// select admin-promotion menu
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Create new promotion
			foundation.click(PromotionList.BTN_CREATE);
//			foundation.isDisplayed(CreatePromotions.LBL_CREATE_PROMOTION);
//			dropDown.selectItem(CreatePromotions.DPD_PROMO_TYPE, promotionType, Constants.TEXT);
//			foundation.threadWait(Constants.TWO_SECOND);
//			textBox.enterText(CreatePromotions.TXT_PROMO_NAME, promotionName);
//			foundation.click(CreatePromotions.BTN_NEXT);
//			foundation.waitforElement(CreatePromotions.DPD_ORG, Constants.SHORT_TIME);
//			dropDown.selectItem(CreatePromotions.DPD_ORG, organization, Constants.TEXT);
//			foundation.click(CreatePromotions.BTN_ORG_RIGHT);
//			dropDown.selectItem(CreatePromotions.DPD_LOC, locationName.get(0), Constants.TEXT);
//			foundation.click(CreatePromotions.BTN_LOC_RIGHT);
//			foundation.threadWait(Constants.TWO_SECOND);
			createPromotions.newPromotion(promotionType, promotionName, promotionName, organization,
					locationName.get(0));
			foundation.waitforElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);
			dropDown.selectItem(CreatePromotions.MULTI_SELECT_TENDER_TYPES, requiredData.get(0), Constants.TEXT);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.waitforClikableElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);
			foundation.waitforClikableElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_OK);

			// navigate to same promotion and validate update promotion
			foundation.waitforElement(PromotionList.PAGE_TITLE, Constants.MEDIUM_TIME);
			textBox.enterText(PromotionList.TXT_SEARCH_PROMONAME, promotionName);
			foundation.threadWait(Constants.THREE_SECOND);
			foundation.click(PromotionList.BTN_SEARCH);
			foundation.doubleClick(PromotionList.TBL_COLUMN_NAME);
			CustomisedAssert.assertTrue(checkBox.isChecked(EditPromotion.CHK_ACTIVE));
			foundation.waitforClikableElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.threadWait(Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.getText(CreatePromotions.LBL_PAGE_TITLE)
					.equals(rstLocationData.get(CNLocation.PROMOTION_TYPE)));
			foundation.waitforElement(CreatePromotions.TXT_SEARCH_ORGPAGE, Constants.SHORT_TIME);
			textBox.enterText(CreatePromotions.TXT_SEARCH_ORGPAGE, organization);
			foundation.threadWait(Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.getText(CreatePromotions.SELECTED_ORG).equals(organization));
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.waitforElement(CreatePromotions.TXT_LOC_SEARCH, Constants.SHORT_TIME);
			textBox.enterText(CreatePromotions.TXT_LOC_SEARCH, locationName.get(0));
			foundation.threadWait(Constants.SHORT_TIME);
			CustomisedAssert
					.assertTrue(foundation.getText(CreatePromotions.SELECTED_LOCATION).equals(locationName.get(0)));
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(CreatePromotions.CHECKBOX_LOC);
			foundation.threadWait(Constants.SHORT_TIME);
			textBox.enterText(CreatePromotions.TXT_LOC_SEARCH, locationName.get(1));
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(CreatePromotions.CHECKBOX_LOC);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.threadWait(Constants.SHORT_TIME);

//			CustomisedAssert.assertTrue(foundation.getText(CreatePromotions.SELECTED_ORG)
//					.equals(propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE)));
//			CustomisedAssert.assertTrue(
//					dropDown.getSelectedItem(CreatePromotions.DPD_DESELECT_LOCATION).equals(locationName.get(0)));
//			// foundation.click(EditPromotion.ICON_CLR_LOCATION);
//			dropDown.selectItem(CreatePromotions.DPD_DESELECT_LOCATION, locationName.get(0), Constants.TEXT);
//			foundation.click(CreatePromotions.BTN_LOC_LEFT);
//			dropDown.selectItem(CreatePromotions.DPD_LOCATION, locationName.get(1), Constants.TEXT);
//			foundation.click(CreatePromotions.BTN_LOC_RIGHT);
//			foundation.threadWait(Constants.TWO_SECOND);
//			foundation.waitforClikableElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);
//			foundation.click(CreatePromotions.BTN_NEXT);

			// select promotion details
			dropDown.selectItem(CreatePromotions.MULTI_SELECT_TENDER_TYPES, requiredData.get(0), Constants.TEXT);
			dropDown.selectItem(CreatePromotions.DPD_DISCOUNT_TYPE, requiredData.get(1), Constants.TEXT);
			foundation.threadWait(Constants.THREE_SECOND);
			foundation.waitforClikableElement(EditPromotion.BTN_UPDATE, Constants.SHORT_TIME);
			foundation.click(EditPromotion.BTN_UPDATE);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.objectClick(EditPromotion.BTN_CONTINUE);
			foundation.waitforClikableElement(EditPromotion.BTN_SAVE, Constants.SHORT_TIME);
			foundation.objectClick(EditPromotion.BTN_SAVE);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_OK);
			foundation.threadWait(Constants.SHORT_TIME);
			textBox.enterText(PromotionList.TXT_SEARCH_PROMONAME, promotionName);
			foundation.threadWait(Constants.THREE_SECOND);
			foundation.click(PromotionList.BTN_SEARCH);
//			foundation.click(PromotionList.LINK_EXPAND);
//			foundation.waitforElement(PromotionList.LBL_ORG_NAME, Constants.SHORT_TIME);
//			String orgName = foundation.getText(PromotionList.LBL_ORG_NAME);
//			CustomisedAssert.assertEquals(
//					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE), orgName);
//			foundation.click(PromotionList.LINK_EXPAND);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.doubleClick(PromotionList.TBL_COLUMN_NAME);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.waitforElement(CreatePromotions.TXT_LOC_SEARCH, Constants.SHORT_TIME);
			textBox.enterText(CreatePromotions.TXT_LOC_SEARCH, locationName.get(1));
			foundation.threadWait(Constants.SHORT_TIME);
			CustomisedAssert
					.assertTrue(foundation.getText(CreatePromotions.SELECTED_LOCATION).contains(locationName.get(1)));
			foundation.threadWait(Constants.SHORT_TIME);

			// Reverse to promotion page
			foundation.scrollIntoViewElement(CreatePromotions.BTN_CANCEL_1);
			foundation.objectClick(CreatePromotions.BTN_CANCEL_1);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_CANCEL_1);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_CANCEL_1);
			foundation.alertAccept();
			foundation.threadWait(Constants.SHORT_TIME);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// Resetting the data
			promotionList.expirePromotion(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM), promotionName,
					statusType, gridName);
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
		String orgName = propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE);
		String locationName = propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE);
		String gridName = rstLocationData.get(CNLocation.TAB_NAME);
		String statusType = rstLocationData.get(CNLocation.LOCATION_NAME);
		// List<String> requiredData =
		// Arrays.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> menu = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.navigateToMenuItem(menu.get(0));
			textBox.enterText(UserList.TXT_FILTER, rstUserRolesData.get(CNUserRoles.ROLE_NAME));
			foundation.click(userList.objRoleName(rstUserRolesData.get(CNUserRoles.ROLE_NAME)));
			foundation.click(UserList.LNK_ORG_REMOVE_ALL);
			List<String> orgs = Arrays
					.asList(rstLocationData.get(CNLocation.COLUMN_VALUE).split(Constants.DELIMITER_TILD));
			dropDown.selectItem(UserList.DPD_ORG, orgs.get(0), Constants.TEXT);
			dropDown.selectItem(UserList.DPD_ORG, orgs.get(1), Constants.TEXT);
			foundation.threadWait(Constants.TWO_SECOND);
			dropDown.selectItem(UserList.TXT_SEARCH_LOC, rstLocationData.get(CNLocation.LOCATIONLIST_DPDN_VALUE),
					Constants.TEXT);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(UserList.BTN_UPDATE_USER);
			foundation.waitforElement(UserList.LBL_USER_LIST, Constants.SHORT_TIME);
			foundation.threadWait(Constants.SHORT_TIME);
			login.logout();

			// operator user
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menu.get(1));

			// New Promotion
			foundation.click(PromotionList.BTN_CREATE);
			createPromotions.newPromotion(promotionType, promotionName, displayName, orgName, locationName);
			foundation.click(CreatePromotions.BTN_NEXT);

			// Promotion Details Page
			List<String> discountType = Arrays
					.asList(rstLocationData.get(CNLocation.PRODUCT_NAME).split(Constants.DELIMITER_TILD));
			dropDown.selectItem(CreatePromotions.DPD_DISCOUNT_BY, discountType.get(0), Constants.TEXT);
//			textBox.enterText(CreatePromotions.SEARCH_CATEGORY, discountType.get(1));
//			foundation.threadWait(Constants.ONE_SECOND);
//			textBox.enterText(CreatePromotions.SEARCH_CATEGORY, Keys.ENTER);
			foundation.waitforElementToBeVisible(CreatePromotions.ADD_CATEGORY, Constants.THREE_SECOND);
			foundation.click(CreatePromotions.ADD_CATEGORY);
			textBox.enterText(CreatePromotions.CATEGORY_SEARCH_TXT, discountType.get(1));
			foundation.threadWait(Constants.SHORT_TIME);
			checkBox.check(CreatePromotions.SELECT_CAT_PRODUCT);
			foundation.waitforElementToBeVisible(CreatePromotions.BTN_CANCEL_CAT_POPUP, Constants.THREE_SECOND);
			foundation.click(CreatePromotions.BTN_CANCEL_CAT_POPUP);
			foundation.click(CreatePromotions.BTN_CREATE);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);

			// popup message validations
			List<String> popupFieldType = foundation.getTextofListElement(CreatePromotions.POP_UP_MESSAGES);
			List<String> popupField = null;
			popupField = Arrays.asList(popupFieldType.get(0).split(Constants.DELIMITER_COMMA));
			popupField = Arrays.asList(popupField.get(0).split(Constants.NEW_LINE));

			List<String> actualData = Arrays
					.asList(rstLocationData.get(CNLocation.ACTUAL_DATA).split(Constants.DELIMITER_TILD));
			CustomisedAssert.assertEquals(popupField.get(0), actualData.get(0));
			CustomisedAssert.assertEquals(popupField.get(1), actualData.get(1));

			List<String> popupFieldArray = createPromotions.getPopUpData();
			String currentDate = dateAndTime.getDateAndTime(Constants.REGEX_MMDDYY, Constants.TIME_ZONE_INDIA);

			CustomisedAssert.assertTrue(popupFieldArray.get(0).contains(promotionType));
			CustomisedAssert.assertTrue(popupFieldArray.get(1).contains(promotionName));
			CustomisedAssert.assertTrue(popupFieldArray.get(2).contains(displayName));
			CustomisedAssert.assertEquals(popupFieldArray.get(3), actualData.get(3));
			foundation.threadWait(Constants.SHORT_TIME);
			CustomisedAssert.assertEquals(popupFieldArray.get(4), actualData.get(4));
			// CustomisedAssert.assertEquals(popupFieldArray.get(5), actualData.get(5));
			CustomisedAssert.assertEquals(popupFieldArray.get(6), actualData.get(6));
			CustomisedAssert.assertTrue(popupFieldArray.get(7).contains(currentDate));
			CustomisedAssert.assertTrue(popupFieldArray.get(8).contains(currentDate));

			foundation.click(CreatePromotions.BTN_OK);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// Resetting the data
			promotionList.expirePromotion(menu.get(1), promotionName, statusType, gridName);
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
		String orgName = propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE);
		String locationName = rstLocationData.get(CNLocation.LOCATION_NAME);
		String gridName = rstLocationData.get(CNLocation.TAB_NAME);
		String statusType = rstLocationData.get(CNLocation.COLUMN_VALUE);

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

			createPromotions.newPromotion(promotionType, promotionName, displayName, orgName, locationName);

			foundation.waitforElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);

			foundation.click(CreatePromotions.BTN_NEXT);
			dropDown.selectItem(CreatePromotions.DPD_DISCOUNT_BY, requiredData.get(1), Constants.TEXT);
//			textBox.enterText(CreatePromotions.TXT_ITEM, requiredData.get(2));
//			foundation.threadWait(Constants.ONE_SECOND);
//			textBox.enterText(CreatePromotions.TXT_ITEM, Keys.ENTER);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(CreatePromotions.ADD_ITEM);
			foundation.threadWait(Constants.SHORT_TIME);
			textBox.enterText(CreatePromotions.ITEM_SEARCH_TXT, requiredData.get(2));
			foundation.threadWait(Constants.SHORT_TIME);
			checkBox.check(CreatePromotions.SELECT_ITEM_PRODUCT);
			foundation.waitforElementToBeVisible(CreatePromotions.BTN_CANCEL_ITEM_POPUP, Constants.THREE_SECOND);
			foundation.click(CreatePromotions.BTN_CANCEL_ITEM_POPUP);
			foundation.threadWait(Constants.SHORT_TIME);
//			String actualValue = dropDown.getSelectedItem(CreatePromotions.DPD_ITEM_SELECT);
//			CustomisedAssert.assertEquals(actualValue, requiredData.get(2));
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(CreatePromotions.ADD_ITEM);
			textBox.enterText(CreatePromotions.ITEM_SEARCH_TXT, requiredData.get(2));
			foundation.threadWait(Constants.SHORT_TIME);
			String itemValue = foundation.getText(EditPromotion.SELECTED_ITEM);
			CustomisedAssert.assertEquals(itemValue, requiredData.get(2));
			foundation.waitforElementToBeVisible(CreatePromotions.BTN_CANCEL_ITEM_POPUP, Constants.THREE_SECOND);
			foundation.click(CreatePromotions.BTN_CANCEL_ITEM_POPUP);

			textBox.enterText(CreatePromotions.TXT_AMOUNT, requiredData.get(4));
			textBox.enterText(CreatePromotions.TXT_TRANSACTION_MIN, requiredData.get(5));
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);

			// popup validations
			List<String> popupFieldType = foundation.getTextofListElement(CreatePromotions.POP_UP_MESSAGES);
			List<String> popupField = null;
			popupField = Arrays.asList(popupFieldType.get(0).split(Constants.DELIMITER_COMMA));
			popupField = Arrays.asList(popupField.get(0).split(Constants.NEW_LINE));

			List<String> actualData = Arrays
					.asList(rstLocationData.get(CNLocation.ACTUAL_DATA).split(Constants.DELIMITER_TILD));
			CustomisedAssert.assertEquals(popupField.get(0), actualData.get(0));
			CustomisedAssert.assertEquals(popupField.get(1), actualData.get(1));

			List<String> popupFieldArray = createPromotions.getPopUpData();
			String currentDate = dateAndTime.getDateAndTime(Constants.REGEX_MMDDYY, Constants.TIME_ZONE_INDIA);

			CustomisedAssert.assertTrue(popupFieldArray.get(0).contains(promotionType));
			CustomisedAssert.assertTrue(popupFieldArray.get(1).contains(promotionName));
			CustomisedAssert.assertTrue(popupFieldArray.get(2).contains(displayName));
			foundation.threadWait(Constants.THREE_SECOND);
			CustomisedAssert.assertEquals(popupFieldArray.get(3), actualData.get(3));
			CustomisedAssert.assertEquals(popupFieldArray.get(4), actualData.get(4));
			foundation.threadWait(Constants.THREE_SECOND);
			CustomisedAssert.assertEquals(popupFieldArray.get(5), actualData.get(5));
			CustomisedAssert.assertEquals(popupFieldArray.get(6), actualData.get(6));
			CustomisedAssert.assertTrue(popupFieldArray.get(7).contains(currentDate));
			CustomisedAssert.assertTrue(popupFieldArray.get(8).contains(currentDate));
			foundation.threadWait(Constants.THREE_SECOND);

			foundation.click(CreatePromotions.BTN_OK);
			foundation.waitforElement(PromotionList.TXT_SEARCH_PROMONAME, Constants.SHORT_TIME);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// Resetting the data
			promotionList.expirePromotion(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM), promotionName,
					statusType, gridName);
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

		List<String> locationDisabled = Arrays.asList(
				rstLocationSummaryData.get(CNLocationSummary.LOCATION_DISABLED).split(Constants.DELIMITER_TILD));

		String locationDisabled_No = locationDisabled.get(1);
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Selecting location
			locationList.selectLocationName(locationName);

			foundation.waitforElement(LocationSummary.DPD_DISABLED, Constants.SHORT_TIME);
			dropDown.selectItem(LocationSummary.DPD_DISABLED, locationDisabled_No, Constants.TEXT);
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
			String uiData = dropDown.getSelectedItem(CreatePromotions.DPD_DESELECT_LOCATION);
			CustomisedAssert.assertEquals(uiData, locationName);

		} catch (Exception exc) {
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
		String displayName = strings.getRandomCharacter();

		List<String> locationDisabled = Arrays.asList(
				rstLocationSummaryData.get(CNLocationSummary.LOCATION_DISABLED).split(Constants.DELIMITER_TILD));
		String locationDisabledYes = locationDisabled.get(0);
		String locationDisabledNo = locationDisabled.get(1);
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Selecting location
			dropDown.selectItem(CreatePromotions.SELECT_DISABLED_LOCATION, locationListDpd, Constants.TEXT);
			locationList.selectLocationName(locationName);
			foundation.waitforElement(LocationSummary.DPD_DISABLED, Constants.SHORT_TIME);
			dropDown.selectItem(LocationSummary.DPD_DISABLED, locationDisabledYes, Constants.TEXT);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.click(LocationSummary.POP_UP_BTN_SAVE);
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
			dropDown.selectItem(CreatePromotions.DPD_PROMO_TYPE, promotionType, Constants.TEXT);
			textBox.enterText(CreatePromotions.TXT_PROMO_NAME, promotionName);
			if (foundation.isDisplayed(CreatePromotions.TXT_DISPLAY_NAME))
				textBox.enterText(CreatePromotions.TXT_DISPLAY_NAME, displayName);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.waitforElement(CreatePromotions.TXT_SEARCH_ORGPAGE, Constants.LONG_TIME);
			textBox.enterText(CreatePromotions.TXT_SEARCH_ORGPAGE, requiredData.get(1));
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(CreatePromotions.CHECKBOX_ORG);
			foundation.waitforElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);
			// dropDown.selectItem(DPD_ORG, orgName, Constants.TEXT);
			foundation.click(CreatePromotions.BTN_NEXT);
			// dropDown.selectItem(DPD_LOC, locationName, Constants.TEXT);
			foundation.waitforElement(CreatePromotions.TXT_LOC_SEARCH, Constants.SHORT_TIME);
			textBox.enterText(CreatePromotions.TXT_LOC_SEARCH, locationName);
			foundation.threadWait(Constants.LONG_TIME);
			CustomisedAssert.assertFalse(foundation.isDisplayed(CreatePromotions.SELECTED_LOCATION));
//			foundation.waitforElement(CreatePromotions.DPD_ORG, Constants.SHORT_TIME);
//			dropDown.selectItem(CreatePromotions.DPD_ORG, requiredData.get(1), Constants.TEXT);
//			foundation.click(CreatePromotions.BTN_ORG_RIGHT);
//			foundation.threadWait(Constants.TWO_SECOND);
//			CustomisedAssert.assertTrue(dropDown.verifyItemNotPresent(CreatePromotions.DPD_LOC, locationName));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			foundation.click(Login.LBL_USER_NAME);
			foundation.click(Login.MUN_LOGOUT);

			editPromotion.switchAlert(rstLocationData.get(CNLocation.ACTUAL_DATA));
			// Resetting test data
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			dropDown.selectItem(LocationList.DPD_LOCATION_LIST, locationListDpd, Constants.TEXT);
			locationList.selectLocationName(locationName);
			foundation.waitforElement(LocationSummary.DPD_DISABLED, Constants.SHORT_TIME);
			dropDown.selectItem(LocationSummary.DPD_DISABLED, locationDisabledNo, Constants.TEXT);
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
		String statusType = rstLocationData.get(CNLocation.COLUMN_VALUE);

		List<String> popupName = Arrays
				.asList(rstLocationData.get(CNLocation.POPUP_NAME).split(Constants.DELIMITER_TILD));
		List<String> locationName = Arrays
				.asList(rstLocationData.get(CNLocation.LOCATION_NAME).split(Constants.DELIMITER_TILD));
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
			createPromotions.newPromotionList(promotionType, promotionName, requiredData.get(0), locationName.get(0));
			foundation.waitforElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);

			foundation.click(CreatePromotions.BTN_NEXT);
			dropDown.selectItem(CreatePromotions.MULTI_SELECT_TENDER_TYPES, requiredData.get(1), Constants.TEXT);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_OK);
			foundation.threadWait(Constants.SHORT_TIME);

			// Validating promotion is displayed
			foundation.waitforElement(PromotionList.TXT_SEARCH_PROMONAME, Constants.SHORT_TIME);
			textBox.enterText(PromotionList.TXT_SEARCH_PROMONAME, promotionName);
			foundation.click(PromotionList.BTN_SEARCH);
			table.selectRow(gridName, promotionName);
			promotionList.clickSelectedRow(gridName, promotionName);
			CustomisedAssert.assertTrue(checkBox.isChecked(EditPromotion.CHK_ACTIVE));
			foundation.waitforElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);
//			CustomisedAssert.assertEquals(requiredData.get(0),
//			dropDown.getSelectedItem(CreatePromotions.DPD_DESELECT_ORGANIZATION));
//			dropDown.selectItem(CreatePromotions.DPD_DESELECT_LOCATION, locationName.get(0), Constants.TEXT);
//			foundation.click(CreatePromotions.BTN_LOC_LEFT);
//			foundation.threadWait(Constants.TWO_SECOND);
//			dropDown.selectItem(CreatePromotions.DPD_LOCATION, locationName.get(1), Constants.TEXT);
//			foundation.click(CreatePromotions.BTN_LOC_RIGHT);
//			foundation.threadWait(Constants.TWO_SECOND);
//			foundation.click(EditPromotion.BTN_UPDATE);
			foundation.waitforElement(CreatePromotions.TXT_SEARCH_ORGPAGE, Constants.LONG_TIME);
			textBox.enterText(CreatePromotions.TXT_SEARCH_ORGPAGE, requiredData.get(0));
			foundation.threadWait(Constants.SHORT_TIME);
			// dropDown.selectItem(DPD_ORG, orgName, Constants.TEXT);
			foundation.click(CreatePromotions.BTN_NEXT);
			// dropDown.selectItem(DPD_LOC, locationName, Constants.TEXT);
			foundation.waitforElement(CreatePromotions.TXT_LOC_SEARCH, Constants.SHORT_TIME);
			textBox.enterText(CreatePromotions.TXT_LOC_SEARCH, locationName.get(0));
			foundation.threadWait(Constants.LONG_TIME);
			foundation.click(CreatePromotions.CHECKBOX_LOC);
			foundation.waitforElement(CreatePromotions.TXT_LOC_SEARCH, Constants.SHORT_TIME);
			textBox.enterText(CreatePromotions.TXT_LOC_SEARCH, locationName.get(1));
			foundation.threadWait(Constants.LONG_TIME);
			foundation.click(CreatePromotions.CHECKBOX_LOC);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.waitforElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.waitforElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.threadWait(Constants.TWO_SECOND);
			foundation.isDisplayed(CreatePromotions.DPD_DISCOUNT_TYPE);
			foundation.waitforElement(EditPromotion.BTN_UPDATE, Constants.SHORT_TIME);
			foundation.click(EditPromotion.BTN_UPDATE);
			foundation.threadWait(Constants.TWO_SECOND);
			String expectedAlertHeader = foundation.getText(EditPromotion.TXT_POPUP_HEADER);
			CustomisedAssert.assertEquals(expectedAlertHeader, popupName.get(0));
			String expectedAlertMsg = foundation.getText(EditPromotion.TXT_POPUP_ALERT_MSG);
			List<String> expecetedData = Arrays.asList(expectedAlertMsg.split(Constants.NEW_LINE));
			CustomisedAssert.assertEquals(expecetedData.get(0), popupName.get(1));
			CustomisedAssert.assertEquals(expecetedData.get(2), popupName.get(2));
			foundation.waitforElement(EditPromotion.BTN_SAVE_AS, Constants.SHORT_TIME);
			foundation.click(EditPromotion.BTN_SAVE_AS);
			foundation.threadWait(Constants.SHORT_TIME);

			textBox.enterText(EditPromotion.TXT_PROMOTION_NAME, newPromotionName);
			foundation.click(EditPromotion.BTN_CONTINUE);
			foundation.waitforElement(EditPromotion.BTN_OK, Constants.SHORT_TIME);
			foundation.click(EditPromotion.BTN_OK);
			// new promotion name validation
			foundation.waitforElement(PromotionList.TXT_SEARCH_PROMONAME, Constants.SHORT_TIME);
			textBox.enterText(PromotionList.TXT_SEARCH_PROMONAME, newPromotionName);
			foundation.click(PromotionList.BTN_SEARCH);
			CustomisedAssert.assertTrue(foundation.getText(PromotionList.TBL_COLUMN_NAME).equals(newPromotionName));
			// old promotion name validation
			foundation.waitforElement(PromotionList.TXT_SEARCH_PROMONAME, Constants.SHORT_TIME);
			textBox.enterText(PromotionList.TXT_SEARCH_PROMONAME, promotionName);
			foundation.click(PromotionList.BTN_SEARCH);
			CustomisedAssert.assertTrue(foundation.getText(PromotionList.TBL_COLUMN_NAME).equals(promotionName));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// Expire Promotion
			promotionList.searchPromotion(newPromotionName, statusType);
			editPromotion.expirePromotion(gridName, newPromotionName);
			foundation.waitforElement(PromotionList.PAGE_TITLE, Constants.SHORT_TIME);
			promotionList.searchPromotion(promotionName, statusType);
			editPromotion.expirePromotion(gridName, promotionName);
			foundation.waitforElement(PromotionList.PAGE_TITLE, Constants.SHORT_TIME);
		}
	}

	@Test(description = "141775-To Verify sorting of Start Date column in Promotion List grid")
	public void verifySortStartDate() {
		try {
			final String CASE_NUM = "141775";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// verify start date sort
			foundation.threadWait(Constants.TWO_SECOND);
			foundation.click(PromotionList.LBL_START_DATE_HEADER);
			CustomisedAssert.assertTrue(foundation.verifySortDate(PromotionList.LBL_START_DATE_LIST,
					Constants.ASCENDING, Constants.REGEX_MMDDYY));
			foundation.click(PromotionList.LBL_START_DATE_HEADER);
			CustomisedAssert.assertTrue(foundation.verifySortDate(PromotionList.LBL_START_DATE_LIST,
					Constants.DESCENDING, Constants.REGEX_MMDDYY));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "141829-SOS-21458-Verify if Item is correctly getting updated in Promotion screen - Onscreen Promotion")
	public void verifyReplaceCategoryFieldtOptionWithItem() {
		try {
			final String CASE_NUM = "141829";

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
			String statusType = rstLocationData.get(CNLocation.COLUMN_VALUE);

			List<String> requiredData = Arrays
					.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));

			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// New Promotion
			foundation.click(PromotionList.BTN_CREATE);
			createPromotions.newPromotion(promotionType, promotionName, displayName, requiredData.get(0), locationName);
			foundation.waitforElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);
			dropDown.selectItem(CreatePromotions.DPD_DISCOUNT_BY, requiredData.get(1), Constants.TEXT);
//			textBox.enterText(CreatePromotions.SEARCH_CATEGORY, requiredData.get(2));
//			foundation.threadWait(Constants.TWO_SECOND);
//			textBox.enterText(CreatePromotions.SEARCH_CATEGORY, Keys.ENTER);
			foundation.waitforElementToBeVisible(CreatePromotions.ADD_CATEGORY, Constants.THREE_SECOND);
			foundation.click(CreatePromotions.ADD_CATEGORY);
			textBox.enterText(CreatePromotions.CATEGORY_SEARCH_TXT, requiredData.get(2));
			foundation.threadWait(Constants.SHORT_TIME);
			checkBox.check(CreatePromotions.SELECT_CAT_PRODUCT);
			foundation.waitforElementToBeVisible(CreatePromotions.BTN_CANCEL_CAT_POPUP, Constants.THREE_SECOND);
			foundation.click(CreatePromotions.BTN_CANCEL_CAT_POPUP);
			textBox.enterText(CreatePromotions.TXT_AMOUNT, requiredData.get(3));
			textBox.enterText(CreatePromotions.TXT_TRANSACTION_MIN, requiredData.get(4));
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_OK);

			// editing item field
			foundation.waitforElement(PromotionList.TXT_SEARCH_PROMONAME, Constants.SHORT_TIME);
			textBox.enterText(PromotionList.TXT_SEARCH_PROMONAME, promotionName);
			dropDown.selectItem(PromotionList.DRP_STATUS, statusType, Constants.TEXT);
			foundation.click(PromotionList.BTN_SEARCH);
			CustomisedAssert.assertTrue(foundation.getText(PromotionList.TBL_COLUMN_NAME).equals(promotionName));
			promotionList.clickSelectedRow(gridName, promotionName);
			foundation.waitforElement(EditPromotion.BTN_END_PROMO, Constants.SHORT_TIME);
			foundation.waitforElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(EditPromotion.BTN_NEXT);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(EditPromotion.BTN_NEXT);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(EditPromotion.BTN_NEXT);
			foundation.threadWait(Constants.SHORT_TIME);
			dropDown.selectItem(CreatePromotions.DPD_DISCOUNT_BY, requiredData.get(5), Constants.TEXT);
//			textBox.enterText(CreatePromotions.TXT_ITEM, requiredData.get(6));
//			foundation.threadWait(Constants.ONE_SECOND);
//			textBox.enterText(CreatePromotions.TXT_ITEM, Keys.ENTER);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(CreatePromotions.ADD_ITEM);
			foundation.threadWait(Constants.SHORT_TIME);
			textBox.enterText(CreatePromotions.ITEM_SEARCH_TXT, requiredData.get(6));
			foundation.threadWait(Constants.SHORT_TIME);
			checkBox.check(CreatePromotions.SELECT_ITEM_PRODUCT);
			foundation.waitforElementToBeVisible(CreatePromotions.BTN_CANCEL_ITEM_POPUP, Constants.THREE_SECOND);
			foundation.click(CreatePromotions.BTN_CANCEL_ITEM_POPUP);
			foundation.click(EditPromotion.BTN_UPDATE);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_OK);

			// category field validation
			foundation.waitforElement(PromotionList.TXT_SEARCH_PROMONAME, Constants.SHORT_TIME);
			textBox.enterText(PromotionList.TXT_SEARCH_PROMONAME, promotionName);
			dropDown.selectItem(PromotionList.DRP_STATUS, statusType, Constants.TEXT);
			foundation.click(PromotionList.BTN_SEARCH);
			CustomisedAssert.assertTrue(foundation.getText(PromotionList.TBL_COLUMN_NAME).equals(promotionName));
			promotionList.clickSelectedRow(gridName, promotionName);
			foundation.waitforElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);
			foundation.waitforElement(EditPromotion.BTN_END_PROMO, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(EditPromotion.BTN_NEXT);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(EditPromotion.BTN_NEXT);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(EditPromotion.BTN_NEXT);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(CreatePromotions.ADD_ITEM);
			foundation.threadWait(Constants.SHORT_TIME);
			textBox.enterText(CreatePromotions.ITEM_SEARCH_TXT, requiredData.get(6));
			foundation.threadWait(Constants.SHORT_TIME);
			String actualData = foundation.getText(CreatePromotions.SELECTED_ITEM);
			CustomisedAssert.assertEquals(actualData, requiredData.get(6));
			foundation.waitforElementToBeVisible(CreatePromotions.BTN_CANCEL_ITEM_POPUP, Constants.THREE_SECOND);
			foundation.click(CreatePromotions.BTN_CANCEL_ITEM_POPUP);

			// end promotion
			createPromotions.cancelingPromotion();
			promotionList.expirePromotion(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM), promotionName,
					statusType, gridName);

//			foundation.threadWait(Constants.TWO_SECOND);
//			foundation.click(EditPromotion.BTN_CANCEL);
//			foundation.waitforElement(EditPromotion.BTN_END_PROMO, Constants.SHORT_TIME);
//			foundation.click(EditPromotion.BTN_END_PROMO);
//			foundation.click(EditPromotion.BTN_CONTINUE);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "141828-SOS-21458-Verify if category is correctly getting updated in Promotion screen - Onscreen Promotion")
	public void verifyReplaceItemFieldtOptionWithCategory() {

		final String CASE_NUM = "141828";

		browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
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
		String statusType = rstLocationData.get(CNLocation.COLUMN_VALUE);

		List<String> requiredData = Arrays
				.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		try {
			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// New Promotion
			foundation.click(PromotionList.BTN_CREATE);
			createPromotions.newPromotion(promotionType, promotionName, displayName, requiredData.get(0), locationName);
			foundation.waitforElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);
			dropDown.selectItem(CreatePromotions.DPD_DISCOUNT_BY, requiredData.get(1), Constants.TEXT);
//			textBox.enterText(CreatePromotions.TXT_ITEM, requiredData.get(2));
//			foundation.threadWait(Constants.ONE_SECOND);
//			textBox.enterText(CreatePromotions.TXT_ITEM, Keys.ENTER);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(CreatePromotions.ADD_ITEM);
			foundation.threadWait(Constants.SHORT_TIME);
			textBox.enterText(CreatePromotions.ITEM_SEARCH_TXT, requiredData.get(2));
			foundation.threadWait(Constants.SHORT_TIME);
			checkBox.check(CreatePromotions.SELECT_ITEM_PRODUCT);
			foundation.threadWait(Constants.SHORT_TIME);
			String actualValue = foundation.getText(CreatePromotions.SELECTED_ITEM);
			CustomisedAssert.assertEquals(actualValue, requiredData.get(2));
			foundation.waitforElementToBeVisible(CreatePromotions.BTN_CANCEL_ITEM_POPUP, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_CANCEL_ITEM_POPUP);
			foundation.threadWait(Constants.SHORT_TIME);
			textBox.enterText(CreatePromotions.TXT_AMOUNT, requiredData.get(5));
			textBox.enterText(CreatePromotions.TXT_TRANSACTION_MIN, requiredData.get(6));
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_OK);

			// editing category field
			foundation.waitforElement(PromotionList.TXT_SEARCH_PROMONAME, Constants.SHORT_TIME);
			textBox.enterText(PromotionList.TXT_SEARCH_PROMONAME, promotionName);
			foundation.click(PromotionList.BTN_SEARCH);
			CustomisedAssert.assertTrue(foundation.getText(PromotionList.TBL_COLUMN_NAME).equals(promotionName));
			promotionList.clickSelectedRow(gridName, promotionName);
			foundation.waitforElement(EditPromotion.BTN_END_PROMO, Constants.SHORT_TIME);
			foundation.waitforElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);
			foundation.waitforClikableElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.threadWait(Constants.SHORT_TIME);
			dropDown.selectItem(CreatePromotions.DPD_DISCOUNT_BY, requiredData.get(3), Constants.TEXT);
//			textBox.enterText(CreatePromotions.SEARCH_CATEGORY, requiredData.get(4));
//			foundation.threadWait(Constants.TWO_SECOND);
//			textBox.enterText(CreatePromotions.SEARCH_CATEGORY, Keys.ENTER);
			foundation.waitforElementToBeVisible(CreatePromotions.ADD_CATEGORY, Constants.THREE_SECOND);
			foundation.click(CreatePromotions.ADD_CATEGORY);
			textBox.enterText(CreatePromotions.CATEGORY_SEARCH_TXT, requiredData.get(4));
			foundation.threadWait(Constants.SHORT_TIME);
			checkBox.check(CreatePromotions.SELECT_CAT_PRODUCT);
			foundation.waitforElementToBeVisible(CreatePromotions.BTN_CANCEL_CAT_POPUP, Constants.THREE_SECOND);
			foundation.click(CreatePromotions.BTN_CANCEL_CAT_POPUP);
			foundation.click(EditPromotion.BTN_UPDATE);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_OK);

			// category field validation
			foundation.waitforElement(PromotionList.TXT_SEARCH_PROMONAME, Constants.SHORT_TIME);
			textBox.enterText(PromotionList.TXT_SEARCH_PROMONAME, promotionName);
			dropDown.selectItem(PromotionList.DRP_STATUS, statusType, Constants.TEXT);
			foundation.click(PromotionList.BTN_SEARCH);
			CustomisedAssert.assertTrue(foundation.getText(PromotionList.TBL_COLUMN_NAME).equals(promotionName));
			promotionList.clickSelectedRow(gridName, promotionName);
			foundation.waitforElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);
			foundation.waitforElement(EditPromotion.BTN_END_PROMO, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(EditPromotion.BTN_NEXT);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(EditPromotion.BTN_NEXT);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(EditPromotion.BTN_NEXT);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.waitforElementToBeVisible(CreatePromotions.ADD_CATEGORY, Constants.THREE_SECOND);
			foundation.click(CreatePromotions.ADD_CATEGORY);
			textBox.enterText(CreatePromotions.CATEGORY_SEARCH_TXT, requiredData.get(4));
			foundation.threadWait(Constants.SHORT_TIME);
			String actualData = foundation.getText(CreatePromotions.CATEGORY_SELECTED);
			CustomisedAssert.assertTrue(actualData.contains(requiredData.get(4)));
			foundation.waitforElementToBeVisible(CreatePromotions.BTN_CANCEL_CAT_POPUP, Constants.THREE_SECOND);
			foundation.click(CreatePromotions.BTN_CANCEL_CAT_POPUP);
			// end promotion
//			foundation.click(EditPromotion.BTN_CANCEL);
//			foundation.threadWait(Constants.TWO_SECOND);
//			foundation.click(EditPromotion.BTN_CANCEL);
//			foundation.waitforElement(EditPromotion.BTN_END_PROMO, Constants.SHORT_TIME);
//			foundation.click(EditPromotion.BTN_END_PROMO);
//			foundation.click(EditPromotion.BTN_CONTINUE);
			createPromotions.cancelingPromotion();

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {

			promotionList.expirePromotion(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM), promotionName,
					statusType, gridName);

		}
	}

	@Test(description = "141821-This test verifies the existing Promotion with new Org and Location for On Screen Promotions")
	public void verifyExistingPromotionWithNewOrgLocation() {
		final String CASE_NUM = "141821";

		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
		rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);

		String promotionName = rstLocationData.get(CNLocation.PROMOTION_NAME);
		String statusType = rstLocationData.get(CNLocation.LOCATIONLIST_DPDN_VALUE);
		List<String> org = Arrays.asList(rstLocationData.get(CNLocation.COLUMN_VALUE).split(Constants.DELIMITER_TILD));
		List<String> location = Arrays
				.asList(rstLocationData.get(CNLocation.LOCATION_NAME).split(Constants.DELIMITER_TILD));
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			foundation.waitforElement(PromotionList.PAGE_TITLE, Constants.SHORT_TIME);

			promotionList.searchPromotion(promotionName, statusType);
			foundation.doubleClick(PromotionList.TBL_COLUMN_NAME);

			// Basic Information Page
			foundation.waitforElement(CreatePromotions.LBL_BASIC_INFORMATION, Constants.SHORT_TIME);
			String title = rstLocationListData.get(CNLocationList.INFO_MESSAGE);
			CustomisedAssert.assertTrue(foundation.getText(EditPromotion.PAGE_TITLE).contains(title));
			CustomisedAssert.assertTrue(checkBox.isChecked(EditPromotion.CHK_ACTIVE));
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.threadWait(Constants.SHORT_TIME);

			// Filter Page
			foundation.waitforElement(CreatePromotions.TXT_SEARCH_ORGPAGE, Constants.LONG_TIME);
			textBox.enterText(CreatePromotions.TXT_SEARCH_ORGPAGE, org.get(0));
			foundation.threadWait(Constants.LONG_TIME);
			String orgExistValue = foundation.getText(CreatePromotions.SELECTED_ORG);
			CustomisedAssert.assertEquals(orgExistValue, org.get(0));
			foundation.click(CreatePromotions.CHECKBOX_ORG);
			textBox.enterText(CreatePromotions.TXT_SEARCH_ORGPAGE, org.get(1));
			foundation.threadWait(Constants.LONG_TIME);
			foundation.click(CreatePromotions.CHECKBOX_ORG);
			foundation.waitforElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.threadWait(Constants.SHORT_TIME);

			foundation.waitforElement(CreatePromotions.TXT_LOC_SEARCH, Constants.LONG_TIME);
			textBox.enterText(CreatePromotions.TXT_LOC_SEARCH, location.get(1));
			foundation.threadWait(Constants.LONG_TIME);
			foundation.click(CreatePromotions.CHECKBOX_LOC);
			foundation.waitforElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.waitforElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);

//			foundation.threadWait(Constants.ONE_SECOND);
//			dropDown.selectItem(CreatePromotions.DPD_DESELECT_ORGANIZATION, org.get(0), Constants.TEXT);
//			foundation.click(CreatePromotions.BTN_ORG_LEFT);
//			foundation.threadWait(Constants.TWO_SECOND);
//			dropDown.deSelectItem(CreatePromotions.DPD_ORG, org.get(0), Constants.TEXT);
//			foundation.threadWait(Constants.ONE_SECOND);
//			dropDown.selectItem(CreatePromotions.DPD_ORG, org.get(1), Constants.TEXT);
//			foundation.click(CreatePromotions.BTN_ORG_RIGHT);
//			foundation.threadWait(Constants.TWO_SECOND);
//			dropDown.selectItem(CreatePromotions.DPD_LOC, location.get(1), Constants.TEXT);
//			foundation.click(CreatePromotions.BTN_LOC_RIGHT);
//			foundation.waitforElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);
//			foundation.click(CreatePromotions.BTN_NEXT);

			// Details page
			foundation.threadWait(Constants.SHORT_TIME);
			List<String> requiredData = Arrays
					.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> discountType = Arrays
					.asList(rstLocationData.get(CNLocation.PRODUCT_NAME).split(Constants.DELIMITER_TILD));
			dropDown.selectItem(CreatePromotions.DPD_DISCOUNT_BY, discountType.get(0), Constants.TEXT);
			// dropDown.deSelectItem(CreatePromotions.DPD_ITEM_SELECT, actualData.get(0),
			// Constants.TEXT);
//			textBox.enterText(CreatePromotions.TXT_ITEM, requiredData.get(0));
//			foundation.threadWait(Constants.ONE_SECOND);
//			textBox.enterText(CreatePromotions.TXT_ITEM, Keys.ENTER);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(CreatePromotions.ADD_ITEM);
			foundation.threadWait(Constants.SHORT_TIME);
			textBox.enterText(CreatePromotions.ITEM_SEARCH_TXT, requiredData.get(0));
			foundation.threadWait(Constants.SHORT_TIME);
			checkBox.check(CreatePromotions.SELECT_ITEM_PRODUCT);
			foundation.waitforElementToBeVisible(CreatePromotions.BTN_CANCEL_ITEM_POPUP, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_CANCEL_ITEM_POPUP);
			foundation.threadWait(Constants.SHORT_TIME);
			textBox.enterText(CreatePromotions.TXT_AMOUNT, requiredData.get(2));
			textBox.enterText(CreatePromotions.TXT_TRANSACTION_MIN, requiredData.get(3));
			foundation.click(CreatePromotions.BTN_CREATE);
			foundation.threadWait(Constants.SHORT_TIME);

			foundation.waitforElement(EditPromotion.LBL_PROMPT_TITLE, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.getText(EditPromotion.LBL_PROMPT_TITLE)
					.contains(rstLocationData.get(CNLocation.POPUP_NAME)));
			foundation.click(EditPromotion.BTN_CONTINUE);
			foundation.threadWait(Constants.TWO_SECOND);
			foundation.click(EditPromotion.BTN_SAVE);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_OK);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.waitforElement(PromotionList.PAGE_TITLE, Constants.MEDIUM_TIME);

			promotionList.searchPromotion(promotionName, statusType);
			foundation.click(PromotionList.TBL_COLUMN_NAME);
			foundation.threadWait(Constants.THREE_SECOND);
			foundation.click(PromotionList.LINK_EXPAND);
			foundation.waitforElement(PromotionList.LBL_ORG_NAME, Constants.SHORT_TIME);
			String orgName = foundation.getText(PromotionList.LBL_ORG_NAME);
			CustomisedAssert.assertEquals(org.get(1), orgName);
			foundation.click(PromotionList.LINK_EXPAND);
			foundation.waitforElement(PromotionList.LBL_LOCATION_NAME, Constants.SHORT_TIME);
			String locName = foundation.getText(PromotionList.LBL_LOCATION_NAME);
			CustomisedAssert.assertEquals(location.get(1), locName);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// Reset the Data
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.DASHBOARD_URL, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			promotionList.searchPromotion(promotionName, statusType);
			foundation.doubleClick(PromotionList.TBL_COLUMN_NAME);

			// Basic Information Page
			foundation.waitforElement(CreatePromotions.LBL_BASIC_INFORMATION, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);

			// Filter Page
			foundation.waitforElement(CreatePromotions.TXT_SEARCH_ORGPAGE, Constants.LONG_TIME);
			textBox.enterText(CreatePromotions.TXT_SEARCH_ORGPAGE, org.get(1));
			foundation.threadWait(Constants.LONG_TIME);
			foundation.click(CreatePromotions.CHECKBOX_ORG);
			textBox.enterText(CreatePromotions.TXT_SEARCH_ORGPAGE, org.get(0));
			foundation.threadWait(Constants.LONG_TIME);
			foundation.click(CreatePromotions.CHECKBOX_ORG);
			foundation.waitforElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.threadWait(Constants.SHORT_TIME);

			foundation.waitforElement(CreatePromotions.TXT_LOC_SEARCH, Constants.LONG_TIME);
			textBox.enterText(CreatePromotions.TXT_LOC_SEARCH, location.get(0));
			foundation.threadWait(Constants.LONG_TIME);
			foundation.click(CreatePromotions.CHECKBOX_LOC);
			foundation.waitforElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.waitforElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);

			// Details page
			List<String> actualData = Arrays
					.asList(rstLocationData.get(CNLocation.ACTUAL_DATA).split(Constants.DELIMITER_TILD));
			List<String> requiredData = Arrays
					.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			dropDown.selectItem(CreatePromotions.DPD_DISCOUNT_BY, requiredData.get(4), Constants.TEXT);
//			textBox.enterText(CreatePromotions.TXT_ITEM, actualData.get(0));
//			foundation.threadWait(Constants.ONE_SECOND);
//			textBox.enterText(CreatePromotions.TXT_ITEM, Keys.ENTER);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(CreatePromotions.ADD_ITEM);
			foundation.threadWait(Constants.SHORT_TIME);
			textBox.enterText(CreatePromotions.ITEM_SEARCH_TXT, actualData.get(0));
			foundation.threadWait(Constants.SHORT_TIME);
			checkBox.check(CreatePromotions.SELECT_ITEM_PRODUCT);
			foundation.waitforElementToBeVisible(CreatePromotions.BTN_CANCEL_ITEM_POPUP, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_CANCEL_ITEM_POPUP);
			textBox.enterText(CreatePromotions.TXT_AMOUNT, actualData.get(2));
			textBox.enterText(CreatePromotions.TXT_TRANSACTION_MIN, actualData.get(3));
			foundation.click(CreatePromotions.BTN_CREATE);

			foundation.waitforElement(EditPromotion.LBL_PROMPT_TITLE, Constants.SHORT_TIME);
			foundation.click(EditPromotion.BTN_CONTINUE);
			foundation.threadWait(Constants.THREE_SECOND);
			foundation.click(EditPromotion.BTN_SAVE);
			foundation.threadWait(Constants.THREE_SECOND);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_OK);
			foundation.waitforElement(PromotionList.PAGE_TITLE, Constants.MEDIUM_TIME);
		}
	}

	@Test(description = "141823 -SOS-17467-Verify Operator will create Multiple Promotions for differenet locations")
	public void verifyMultiplePromtionsOption() {
		final String CASE_NUM = "141823";

		browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
		login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
				propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);

		String promotionType = rstLocationData.get(CNLocation.PROMOTION_TYPE);
		List<String> locationName = Arrays
				.asList(rstLocationData.get(CNLocation.LOCATION_NAME).split(Constants.DELIMITER_TILD));
		List<String> requiredData = Arrays
				.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		String promotionName = Constants.ACCOUNT_NAME + strings.getRandomCharacter();
		String gridName = rstLocationData.get(CNLocation.TAB_NAME);
		// String statusType = rstLocationData.get(CNLocation.COLUMN_VALUE);
		try {

			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// New Promotion
			for (int iter = 0; iter < locationName.size(); iter++) {
				foundation.click(PromotionList.BTN_CREATE);

				foundation.isDisplayed(CreatePromotions.LBL_CREATE_PROMOTION);
				createPromotions.newPromotionList(promotionType, promotionName, requiredData.get(0),
						locationName.get(iter));
				foundation.waitforElement(CreatePromotions.BTN_NEXT, Constants.MEDIUM_TIME);
				foundation.threadWait(Constants.TWO_SECOND);

				foundation.click(CreatePromotions.BTN_NEXT);
				dropDown.selectItem(CreatePromotions.MULTI_SELECT_TENDER_TYPES, requiredData.get(1), Constants.TEXT);

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

				List<String> actualData = Arrays
						.asList(rstLocationData.get(CNLocation.ACTUAL_DATA).split(Constants.DELIMITER_TILD));
				CustomisedAssert.assertEquals(popupField.get(0), actualData.get(0));
				CustomisedAssert.assertEquals(popupField.get(1), actualData.get(1));

				List<String> popupFieldArray = createPromotions.getPopUpData();
				String currentDate = dateAndTime.getDateAndTime(Constants.REGEX_MMDDYY, Constants.TIME_ZONE_INDIA);

				CustomisedAssert.assertTrue(popupFieldArray.get(0).contains(promotionType));
				CustomisedAssert.assertTrue(popupFieldArray.get(1).contains(promotionName));
				CustomisedAssert.assertEquals(popupFieldArray.get(2), actualData.get(2));
				CustomisedAssert.assertEquals(popupFieldArray.get(3), actualData.get(3));
				CustomisedAssert.assertEquals(popupFieldArray.get(4), actualData.get(4));
				CustomisedAssert.assertEquals(popupFieldArray.get(5), actualData.get(5));
				CustomisedAssert.assertEquals(popupFieldArray.get(6), actualData.get(6));
				CustomisedAssert.assertTrue(popupFieldArray.get(7).contains(currentDate));
				CustomisedAssert.assertTrue(popupFieldArray.get(8).contains(currentDate));
				foundation.click(CreatePromotions.BTN_OK);
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// Resetting the data
			for (int iter = 0; iter < locationName.size(); iter++) {
				promotionList.expireMultiplePromotion(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM),
						promotionName, gridName);
			}
		}
	}

	@Test(description = "176247 - Verify the ADM > Promotions > Bundling > Step 3 (Promotion Details) > + Add Group (new)"
			+ "176248 - Verify that items & categories are unavailable in Bundle Group drop down when group option is selected & Created"
			+ "176249 - Verify when user deletes the created group ,then items & categories are available in Build Bundle Dropdown")
	public void verifyBundlePromtionsAddGroupOptions() {
		final String CASE_NUM = "176247";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);

		List<String> promoName = Arrays
				.asList(rstLocationData.get(CNLocation.PROMOTION_NAME).split(Constants.DELIMITER_TILD));
		List<String> requiredData = Arrays
				.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> menu = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Select Org,Menu and Menu Item and click Create Promotion
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menu.get(0));
			CustomisedAssert.assertTrue(foundation.isDisplayed(PromotionList.PAGE_TITLE));
			foundation.click(PromotionList.BTN_CREATE);
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.LBL_CREATE_PROMOTION));

			// Select Promo Type, Promo Name, Display Name and click Next
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.LBL_PROMO_TYPE));
			createPromotions.createPromotion(rstLocationData.get(CNLocation.PROMOTION_TYPE), promoName.get(0),
					promoName.get(1));

			// Choose Org and Location
//			String color = foundation.getTextColor(CreatePromotions.FILTER_PAGE);
//			CustomisedAssert.assertEquals(color, rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));
			foundation.threadWait(Constants.SHORT_TIME);
			createPromotions.selectOrgLoc(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE),
					rstLocationData.get(CNLocation.LOCATION_NAME));

			// Select Bundle Group in Details Page
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.BUNDLE_BUILD));
			String color = foundation.getTextColor(CreatePromotions.DETAILS_PAGE);
			CustomisedAssert.assertEquals(color, rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.LBL_BUILD_BUNDLE));
			createPromotions.verifyBundleOption(requiredData);
			dropDown.selectItem(CreatePromotions.DPD_DISCOUNT_BY, requiredData.get(3), Constants.TEXT);
			foundation.waitforElementToBeVisible(CreatePromotions.BTN_ADD_GROUP, 5);
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.BTN_ADD_GROUP));
			color = foundation.getTextColor(CreatePromotions.BTN_ADD_GROUP);
			CustomisedAssert.assertEquals(color, rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));

			// Creating the Group
			createPromotions.creatingBundleGroup(promoName.get(1) + strings.getRandomCharacter(),
					rstLocationData.get(CNLocation.PRODUCT_NAME));

			// Validating Bundle Option
			String value = foundation.getAttribute(CreatePromotions.BUNDLE_OPTION_ITEM, requiredData.get(4));
			CustomisedAssert.assertEquals(value, requiredData.get(5));
			value = foundation.getAttribute(CreatePromotions.BUNDLE_OPTION_CATEGORY, requiredData.get(4));
			CustomisedAssert.assertEquals(value, requiredData.get(5));

			// Deleting the Bundle Group and validating the Bundle Option
			foundation.click(CreatePromotions.DELETE_GROUP);
			foundation.threadWait(Constants.TWO_SECOND);
			foundation.click(CreatePromotions.BTN_EXPIRE);
			foundation.threadWait(Constants.THREE_SECOND);
			foundation.scrollIntoViewElement(CreatePromotions.BTN_ADD_GROUP);
			value = foundation.getAttribute(CreatePromotions.BUNDLE_OPTION_ITEM, requiredData.get(4));
			CustomisedAssert.assertTrue(value == null);
			value = foundation.getAttribute(CreatePromotions.BUNDLE_OPTION_CATEGORY, requiredData.get(4));
			CustomisedAssert.assertTrue(value == null);

			// Canceling the Promotion
			createPromotions.cancelingPromotion();

			// Navigating to Location
			navigationBar.navigateToMenuItem(menu.get(1));
			foundation.waitforElementToBeVisible(LocationList.LBL_LOCATION_LIST, 5);
			login.logout();
			browser.close();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "176281 - verify the view for the build bundle Groups"
			+ "176282 - verify the Edit of the build bundle Groups"
			+ "176283 - verify the deletion of groups from the build bundle section"
			+ "176271 - Verify the 'Build Bundle' Dropdown with new 'Group' option in step 3 (promotion details) for Bundle"
			+ "176272 - Verify the functionality of 'Group' option in step 3 (promotion details) for build Bundle promotion")
	public void verifyBundlePromtionsAddGroupOption() {
		final String CASE_NUM = "176281";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);

		List<String> promoName = Arrays
				.asList(rstLocationData.get(CNLocation.PROMOTION_NAME).split(Constants.DELIMITER_TILD));
		List<String> requiredData = Arrays
				.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> menu = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> productName = Arrays
				.asList(rstLocationData.get(CNLocation.PRODUCT_NAME).split(Constants.DELIMITER_TILD));
		List<String> categories = Arrays
				.asList(rstLocationData.get(CNLocation.COLUMN_NAME).split(Constants.DELIMITER_TILD));
		List<String> coordinates = Arrays
				.asList(rstLocationData.get(CNLocation.ACTUAL_DATA).split(Constants.DELIMITER_TILD));
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Select Org,Menu and Menu Item and click Create Promotion
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menu.get(0));
			CustomisedAssert.assertTrue(foundation.isDisplayed(PromotionList.PAGE_TITLE));
			foundation.click(PromotionList.BTN_CREATE);
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.LBL_CREATE_PROMOTION));

			// Select Promo Type, Promo Name, Display Name and click Next
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.LBL_PROMO_TYPE));
			createPromotions.createPromotion(rstLocationData.get(CNLocation.PROMOTION_TYPE), promoName.get(0),
					promoName.get(1));

			// Choose Org and Location
//			String color = foundation.getTextColor(CreatePromotions.FILTER_PAGE);
//			CustomisedAssert.assertEquals(color, rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));
			foundation.threadWait(Constants.SHORT_TIME);
			createPromotions.selectOrgLoc(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE),
					rstLocationData.get(CNLocation.LOCATION_NAME));

			// Select Bundle Group in Details Page
			String color = foundation.getTextColor(CreatePromotions.DETAILS_PAGE);
			CustomisedAssert.assertEquals(color, rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.LBL_BUILD_BUNDLE));
			createPromotions.verifyBundleOption(requiredData);
			dropDown.selectItem(CreatePromotions.DPD_DISCOUNT_BY, requiredData.get(3), Constants.TEXT);
			foundation.waitforElementToBeVisible(CreatePromotions.BTN_ADD_GROUP, 5);
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.BTN_ADD_GROUP));
			color = foundation.getTextColor(CreatePromotions.BTN_ADD_GROUP);
			CustomisedAssert.assertEquals(color, rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));

			// Creating the Group and validating the Icons
			createPromotions.creatingBundleGroupWithCategory(promoName.get(1) + strings.getRandomCharacter(),
					productName.get(0), categories.get(0));
			createPromotions.creatingBundleGroup(
					promoName.get(1) + strings.getRandomCharacter() + strings.getRandomCharacter(), productName.get(1));
			Point coordinatesAxis = foundation.getCoordinates(CreatePromotions.BTN_ADD_GROUP);
			CustomisedAssert.assertEquals(String.valueOf(coordinatesAxis.getX()), coordinates.get(0));
			foundation.threadWait(Constants.THREE_SECOND);
			CustomisedAssert.assertEquals(String.valueOf(coordinatesAxis.getY()), coordinates.get(1));
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.LBL_BUNDLE_GROUP_EDIT));
			color = foundation.getTextColor(CreatePromotions.LBL_BUNDLE_GROUP_EDIT);
			CustomisedAssert.assertEquals(color, rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));
			coordinatesAxis = foundation.getCoordinates(CreatePromotions.LBL_BUNDLE_GROUP_EDIT);
			CustomisedAssert.assertEquals(String.valueOf(coordinatesAxis.getX()), coordinates.get(2));
			CustomisedAssert.assertEquals(String.valueOf(coordinatesAxis.getY()), coordinates.get(3));

			// Validating Bundle Option
			String value = foundation.getAttribute(CreatePromotions.BUNDLE_OPTION_ITEM, requiredData.get(4));
			CustomisedAssert.assertEquals(value, requiredData.get(5));
			value = foundation.getAttribute(CreatePromotions.BUNDLE_OPTION_CATEGORY, requiredData.get(4));
			CustomisedAssert.assertEquals(value, requiredData.get(5));

			// Editing Bundle Group
			createPromotions.deleteBundleGroup();
			createPromotions.editBundleGroup(rstLocationData.get(CNLocation.TITLE), productName.get(2),
					categories.get(1));

			// Deleting the Bundle Group and validating the Bundle Option
			createPromotions.deleteBundleGroup();
			foundation.scrollIntoViewElement(CreatePromotions.BTN_ADD_GROUP);
			value = foundation.getAttribute(CreatePromotions.BUNDLE_OPTION_ITEM, requiredData.get(4));
			CustomisedAssert.assertTrue(value == null);
			value = foundation.getAttribute(CreatePromotions.BUNDLE_OPTION_CATEGORY, requiredData.get(4));
			CustomisedAssert.assertTrue(value == null);

			// Canceling the Promotion
			createPromotions.cancelingPromotion();

			// Navigating to Location
			navigationBar.navigateToMenuItem(menu.get(1));
			foundation.waitforElementToBeVisible(LocationList.LBL_LOCATION_LIST, 5);
			login.logout();
			browser.close();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "176278 - verify the 'Confirm Delete Group' prompt"
			+ "176279 - verify the Confirmation of Deleting the group from the bunle criteria"
			+ "176280 - verify the Cancellation of Deleting the group from the bunle criteria")
	public void verifyBundlePromtionsDeleteGroupOption() {
		final String CASE_NUM = "176278";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);

		List<String> promoName = Arrays
				.asList(rstLocationData.get(CNLocation.PROMOTION_NAME).split(Constants.DELIMITER_TILD));
		List<String> menu = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> requiredData = Arrays
				.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Select Org,Menu and Menu Item and click Create Promotion
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menu.get(0));
			CustomisedAssert.assertTrue(foundation.isDisplayed(PromotionList.PAGE_TITLE));
			foundation.click(PromotionList.BTN_CREATE);
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.LBL_CREATE_PROMOTION));

			// Select Promo Type, Promo Name, Display Name and click Next
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.LBL_PROMO_TYPE));
			createPromotions.createPromotion(rstLocationData.get(CNLocation.PROMOTION_TYPE), promoName.get(0),
					promoName.get(1));

			// Choose Org and Location
			createPromotions.selectOrgLoc(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE),
					rstLocationData.get(CNLocation.LOCATION_NAME));

			// Select Bundle Group in Details Page
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.LBL_BUILD_BUNDLE));
			dropDown.selectItem(CreatePromotions.DPD_DISCOUNT_BY, requiredData.get(0), Constants.TEXT);
			foundation.waitforElementToBeVisible(CreatePromotions.BTN_ADD_GROUP, 5);
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.BTN_ADD_GROUP));

			// Creating the Group and validating the Icons
			String groupName = promoName.get(1) + strings.getRandomCharacter();
			createPromotions.creatingBundleGroupWithCategory(groupName, rstLocationData.get(CNLocation.PRODUCT_NAME),
					rstLocationData.get(CNLocation.COLUMN_NAME));
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.LBL_BUNDLE_GROUP_EDIT));
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.LBL_CREATED_GROUP));
			String actualData = foundation.getAttributeValue(CreatePromotions.BUNDLE_GROUP_NAME);
			CustomisedAssert.assertTrue(actualData.contains(groupName));

			// Validating Delete Bundle Group Prompt
			foundation.click(CreatePromotions.DELETE_GROUP);
			foundation.threadWait(Constants.TWO_SECOND);
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.DELETE_GROUP_HEADER));
			String content = foundation.getText(CreatePromotions.PROMPT_CONTENT);
			CustomisedAssert.assertEquals(content, rstLocationData.get(CNLocation.POPUP_NAME));
			content = foundation.getText(CreatePromotions.BTN_EXPIRE);
			CustomisedAssert.assertEquals(content, requiredData.get(1));
			content = foundation.getText(CreatePromotions.BTN_PROMPT_CANCEL);
			CustomisedAssert.assertEquals(content, requiredData.get(2));

			// Canceling the Delete Bundle Prompt
			foundation.click(CreatePromotions.BTN_PROMPT_CANCEL);
			foundation.threadWait(Constants.THREE_SECOND);
			CustomisedAssert.assertFalse(foundation.isDisplayed(CreatePromotions.DELETE_GROUP_HEADER));
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.LBL_CREATED_GROUP));
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.BUNDLE_GROUP_NAME));

			// Deleting the Bundle Group and validating the Prompt
			foundation.click(CreatePromotions.DELETE_GROUP);
			foundation.threadWait(Constants.TWO_SECOND);
			foundation.click(CreatePromotions.BTN_EXPIRE);
			foundation.threadWait(Constants.THREE_SECOND);
			foundation.scrollIntoViewElement(CreatePromotions.BTN_ADD_GROUP);

			// Checking on Bundle Group Display after Deleting Bundle Group
			CustomisedAssert.assertFalse(foundation.isDisplayed(CreatePromotions.LBL_CREATED_GROUP));
			CustomisedAssert.assertFalse(foundation.isDisplayed(CreatePromotions.BUNDLE_GROUP_NAME));

			// Canceling the Promotion
			createPromotions.cancelingPromotion();

			// Navigating to Location
			navigationBar.navigateToMenuItem(menu.get(1));
			foundation.waitforElementToBeVisible(LocationList.LBL_LOCATION_LIST, 5);
			login.logout();
			browser.close();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "176287 - verify the filter by 'Items' (default) option"
			+ "176288 - verify the filter by 'Category' option")
	public void verifyBundlePromtionsOverLayOptions() {
		final String CASE_NUM = "176287";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);

		List<String> promoName = Arrays
				.asList(rstLocationData.get(CNLocation.PROMOTION_NAME).split(Constants.DELIMITER_TILD));
		List<String> menu = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> productName = Arrays
				.asList(rstLocationData.get(CNLocation.PRODUCT_NAME).split(Constants.DELIMITER_TILD));
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Select Org,Menu and Menu Item and click Create Promotion
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menu.get(0));
			CustomisedAssert.assertTrue(foundation.isDisplayed(PromotionList.PAGE_TITLE));
			foundation.click(PromotionList.BTN_CREATE);
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.LBL_CREATE_PROMOTION));

			// Select Promo Type, Promo Name, Display Name and click Next
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.LBL_PROMO_TYPE));
			createPromotions.createPromotion(rstLocationData.get(CNLocation.PROMOTION_TYPE), promoName.get(0),
					promoName.get(1));

			// Choose Org and Location
			createPromotions.selectOrgLoc(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE),
					rstLocationData.get(CNLocation.LOCATION_NAME));

			// Select Bundle Group in Details Page
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.LBL_BUILD_BUNDLE));
			dropDown.selectItem(CreatePromotions.DPD_DISCOUNT_BY, rstLocationData.get(CNLocation.REQUIRED_DATA),
					Constants.TEXT);
			foundation.waitforElementToBeVisible(CreatePromotions.BTN_ADD_GROUP, 5);
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.BTN_ADD_GROUP));

			// Creating the Group and validating the Icons
			createPromotions.creatingBundleGroupWithCategory(promoName.get(1) + strings.getRandomCharacter(),
					productName.get(0), rstLocationData.get(CNLocation.COLUMN_NAME));
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.LBL_BUNDLE_GROUP_EDIT));
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.LBL_CREATED_GROUP));

			// Validating Product and Categories under Bundle Group Overlay
			foundation.click(CreatePromotions.LBL_BUNDLE_GROUP_EDIT);
			foundation.waitforElementToBeVisible(CreatePromotions.LBL_BUNDLE_GROUP, 5);
			String color = foundation.getBGColor(CreatePromotions.PRODUCT_FILTER);
			CustomisedAssert.assertEquals(color, rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));
			CustomisedAssert.assertTrue(foundation.getSizeofListElement(CreatePromotions.ITEM_GRID) > 0);
			foundation.threadWait(Constants.THREE_SECOND);
			foundation.click(CreatePromotions.CATEGORY_FILTER);
			CustomisedAssert.assertTrue(foundation.getSizeofListElement(CreatePromotions.CATEGORY_GRID) > 0);
			foundation.click(CreatePromotions.PRODUCT_FILTER);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(CreatePromotions.INPUT_ITEM_SEARCH);
			textBox.clearText(CreatePromotions.INPUT_ITEM_SEARCH);
			textBox.enterText(CreatePromotions.INPUT_ITEM_SEARCH, productName.get(1));
			foundation.click(CreatePromotions.ITEM_CHECK_BOX);
			foundation.threadWait(Constants.THREE_SECOND);
			foundation.click(CreatePromotions.GROUP_MODAL_SAVE);
			foundation.threadWait(Constants.THREE_SECOND);

			// Deleting the Bundle Group and validating the Prompt
			foundation.click(CreatePromotions.DELETE_GROUP);
			foundation.threadWait(Constants.TWO_SECOND);
			foundation.click(CreatePromotions.BTN_EXPIRE);
			foundation.threadWait(Constants.THREE_SECOND);
			foundation.scrollIntoViewElement(CreatePromotions.BTN_ADD_GROUP);

			// Canceling the Promotion
			createPromotions.cancelingPromotion();

			// Navigating to Location
			navigationBar.navigateToMenuItem(menu.get(1));
			foundation.waitforElementToBeVisible(LocationList.LBL_LOCATION_LIST, 5);
			login.logout();
			browser.close();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "C176284-Verify the conflict for selecting the item and categories option in 'Build bundle' Dropdown"
			+ "C176285-Verify the conflict for selecting the Group option in 'Build bundle' Dropdown")
	public void verifyConflictForSelectingBundlePromtions() {
		final String CASE_NUM = "176284";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);

		List<String> requiredData = Arrays
				.asList(rstLocationData.get(CNLocation.PROMOTION_TYPE).split(Constants.DELIMITER_TILD));
		List<String> product = Arrays
				.asList(rstLocationData.get(CNLocation.ACTUAL_DATA).split(Constants.DELIMITER_TILD));
		List<String> color = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION).split(Constants.DELIMITER_TILD));
		List<String> org = Arrays.asList(rstLocationData.get(CNLocation.LOCATION_NAME).split(Constants.DELIMITER_TILD));
		try {
			// Select Org, Menu and Menu Item and click Create Promotion
			navigationBar.launchBrowserAsSuperAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			CustomisedAssert.assertTrue(foundation.isDisplayed(PromotionList.PAGE_TITLE));
			foundation.click(PromotionList.BTN_CREATE);

			// Navigate to create promotion page
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.LBL_CREATE_PROMOTION));
			String colour = foundation.getTextColor(PromotionList.BASIC_PROMOTION_TITLE);
			CustomisedAssert.assertEquals(colour, color.get(0));
			createPromotions.createPromotion(requiredData.get(0), requiredData.get(1), requiredData.get(2));

			// Navigate to Choosing promotion filter and select organization
			colour = foundation.getTextColor(PromotionList.CHOOSE_ORG_FILTER);
			CustomisedAssert.assertEquals(colour, color.get(0));
			createPromotions.selectOrgLoc(org.get(0), org.get(1));

			// Select one r more group in bundle criteria
			colour = foundation.getTextColor(PromotionList.PROMOTION_DETAILS);
			CustomisedAssert.assertEquals(colour, color.get(0));
			foundation.threadWait(Constants.THREE_SECOND);
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.BUNDLE_BUILD));
			dropDown.selectItem(CreatePromotions.DPD_DISCOUNT_BY, org.get(2), Constants.TEXT);
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.BTN_ADD_GROUP));

			// Create a bundle group in Group criteria
			foundation.click(CreatePromotions.BTN_ADD_GROUP);
			textBox.enterText(CreatePromotions.GROUP_NAME, requiredData.get(1));
			createPromotions.selectItemInBuildBundle(product.get(2));
			foundation.threadWait(Constants.THREE_SECOND);
			foundation.click(CreatePromotions.BTN_ADD);

			// One more bundle group
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_ADD_GROUP);
			textBox.enterText(CreatePromotions.GROUP_NAME, requiredData.get(2));
			createPromotions.selectItemInBuildBundle(product.get(3));
			foundation.threadWait(Constants.THREE_SECOND);
			foundation.click(CreatePromotions.BTN_ADD);

			// verifying the item and category is disabled
			foundation.click(CreatePromotions.DPD_DISCOUNT_BY);
			CustomisedAssert.assertTrue(foundation.isDisabled(createPromotions.dropdownBuildBundle(product.get(0))));
			CustomisedAssert.assertTrue(foundation.isDisabled(createPromotions.dropdownBuildBundle(product.get(1))));

			// removing the group bundle promotion
			createPromotions.deleteBundleGroup();
			foundation.threadWait(Constants.SHORT_TIME);
			createPromotions.deleteBundleGroup();

			// selecting a item&Category in build bundle
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.BUNDLE_BUILD));
			dropDown.selectItem(CreatePromotions.DPD_DISCOUNT_BY, product.get(0), Constants.TEXT);
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.ADD_ITEM));
			foundation.threadWait(Constants.SHORT_TIME);
			dropDown.selectItem(CreatePromotions.DPD_DISCOUNT_BY, product.get(1), Constants.TEXT);
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.ADD_CATEGORY));
			foundation.threadWait(Constants.SHORT_TIME);
			dropDown.selectItem(CreatePromotions.DPD_DISCOUNT_BY, org.get(2), Constants.TEXT);
			createPromotions.cancelingPromotion();
			login.logout();
			browser.close();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "C176286-Verifying the overlay of Group's Items and Categories"
			+ "C177864-verify the Group Name field in Select Group's Items and Categories overlay")
	public void verifyOverlayOfGroupItemsAndCategories() {
		final String CASE_NUM = "176286";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);

		List<String> requiredData = Arrays
				.asList(rstLocationData.get(CNLocation.PROMOTION_TYPE).split(Constants.DELIMITER_TILD));
		List<String> product = Arrays
				.asList(rstLocationData.get(CNLocation.ACTUAL_DATA).split(Constants.DELIMITER_TILD));
		List<String> datas = Arrays.asList(rstLocationData.get(CNLocation.NAME).split(Constants.DELIMITER_TILD));
		List<String> color = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION).split(Constants.DELIMITER_TILD));
		List<String> org = Arrays.asList(rstLocationData.get(CNLocation.LOCATION_NAME).split(Constants.DELIMITER_TILD));
		try {
			// Select Org,Menu and Menu Item and click Create Promotion
			navigationBar.launchBrowserAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			CustomisedAssert.assertTrue(foundation.isDisplayed(PromotionList.PAGE_TITLE));
			foundation.click(PromotionList.BTN_CREATE);

			// Navigate to create promotion page
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.LBL_CREATE_PROMOTION));
			String colour = foundation.getTextColor(PromotionList.BASIC_PROMOTION_TITLE);
			CustomisedAssert.assertEquals(colour, color.get(0));
			createPromotions.createPromotion(requiredData.get(0), requiredData.get(1), requiredData.get(2));

			// Navigate to Choosing promotion filter and select organization
			colour = foundation.getTextColor(PromotionList.CHOOSE_ORG_FILTER);
			CustomisedAssert.assertEquals(colour, color.get(0));
			createPromotions.selectOrgLoc(org.get(0), org.get(1));

			// Select one r more group in bundle criteria
			colour = foundation.getTextColor(PromotionList.PROMOTION_DETAILS);
			CustomisedAssert.assertEquals(colour, color.get(0));
			foundation.threadWait(Constants.THREE_SECOND);
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.BUNDLE_BUILD));
			dropDown.selectItem(CreatePromotions.DPD_DISCOUNT_BY, product.get(2), Constants.TEXT);
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.BTN_ADD_GROUP));

			// Create a bundle group in Group criteria
			createPromotions.creatingBundleGroup(requiredData.get(1), datas.get(2));

			// removing the group bundle promotion
			createPromotions.deleteBundleGroup();

			// validate the product & category field, verify Name, UPC & record field
			foundation.click(CreatePromotions.BTN_ADD_GROUP);
			foundation.threadWait(Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.NAME_GRID));
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.UPC_GRID));
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.TXT_RECORD));
			String bgColor = foundation.getBGColor(CreatePromotions.PRODUCT_FILTER);
			CustomisedAssert.assertEquals(bgColor, product.get(4));
			foundation.click(CreatePromotions.CATEGORY_FILTER);
			bgColor = foundation.getBGColor(CreatePromotions.CATEGORY_FILTER);
			CustomisedAssert.assertEquals(bgColor, product.get(4));
			foundation.click(CreatePromotions.PRODUCT_FILTER);

			// Create a group without group name,verify the error message & verify item
			// search box
			createPromotions.selectItemInBuildBundle(datas.get(2));
			createPromotions.selectItemInBuildBundle(datas.get(3));
			foundation.click(CreatePromotions.CATEGORY_FILTER);
			foundation.threadWait(Constants.SHORT_TIME);
			createPromotions.selectCategoryInBuildBundle(datas.get(1));
			// String text = foundation.getText(CreatePromotions.PROD_CATE_SELECTED);
			// CustomisedAssert.assertEquals(text, datas.get(0));
			foundation.click(CreatePromotions.PRODUCT_FILTER);
			foundation.threadWait(Constants.THREE_SECOND);
			textBox.enterText(CreatePromotions.ITEM_SEARCH, product.get(3));
			foundation.threadWait(Constants.THREE_SECOND);
			CustomisedAssert.assertTrue(foundation.isDisplayed(createPromotions.Product(product.get(3))));
			createPromotions.selectItemInBuildBundle(datas.get(4));
			foundation.click(CreatePromotions.BTN_ADD);
			foundation.threadWait(Constants.TWO_SECOND);
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.ERROR_MSG));
			foundation.click(CreatePromotions.CANCEL_BTN);
			foundation.threadWait(Constants.TWO_SECOND);
			foundation.click(CreatePromotions.BTN_EXPIRE);
			createPromotions.cancelingPromotion();
			login.logout();
			browser.close();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "176292 - verify the selection of the items in Select Group's Items and Categories overlay"
			+ "176293 - verify the selection of the category in Select Group's Items and Categories overlay")
	public void verifyBundlePromtionsOverLaySelection() {
		final String CASE_NUM = "176292";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);

		List<String> promoName = Arrays
				.asList(rstLocationData.get(CNLocation.PROMOTION_NAME).split(Constants.DELIMITER_TILD));
		List<String> menu = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> productName = Arrays
				.asList(rstLocationData.get(CNLocation.PRODUCT_NAME).split(Constants.DELIMITER_TILD));
		List<String> columnName = Arrays
				.asList(rstLocationData.get(CNLocation.COLUMN_NAME).split(Constants.DELIMITER_TILD));
		List<String> requiredData = Arrays
				.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Select Org,Menu and Menu Item and click Create Promotion
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menu.get(0));
			CustomisedAssert.assertTrue(foundation.isDisplayed(PromotionList.PAGE_TITLE));
			foundation.click(PromotionList.BTN_CREATE);
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.LBL_CREATE_PROMOTION));

			// Select Promo Type, Promo Name, Display Name and click Next
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.LBL_PROMO_TYPE));
			createPromotions.createPromotion(rstLocationData.get(CNLocation.PROMOTION_TYPE), promoName.get(0),
					promoName.get(1));

			// Choose Org and Location
			createPromotions.selectOrgLoc(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE),
					rstLocationData.get(CNLocation.LOCATION_NAME));

			// Select Bundle Group in Details Page
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.LBL_BUILD_BUNDLE));
			dropDown.selectItem(CreatePromotions.DPD_DISCOUNT_BY, requiredData.get(0), Constants.TEXT);
			foundation.waitforElementToBeVisible(CreatePromotions.BTN_ADD_GROUP, 5);
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.BTN_ADD_GROUP));

			// Creating the Group and validating the Items section with one or more Items
			// selected
			foundation.click(CreatePromotions.BTN_ADD_GROUP);
			foundation.waitforElementToBeVisible(CreatePromotions.LBL_BUNDLE_GROUP, 5);
			foundation.threadWait(Constants.TWO_SECOND);
			String color = foundation.getBGColor(CreatePromotions.PRODUCT_FILTER);
			CustomisedAssert.assertEquals(color, rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));
			CustomisedAssert.assertTrue(foundation.getSizeofListElement(CreatePromotions.ITEM_GRID) > 0);
			foundation.threadWait(Constants.THREE_SECOND);
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.NAME_GRID));
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.UPC_GRID));
			createPromotions.selectItemInBuildBundle(productName.get(0));
			createPromotions.selectItemInBuildBundle(productName.get(1));
			String msg = foundation.getText(CreatePromotions.BUNDLE_LIST_MESSAGE);
			CustomisedAssert.assertEquals(msg, requiredData.get(1));
			foundation.click(CreatePromotions.CATEGORY_FILTER);
			msg = foundation.getText(CreatePromotions.BUNDLE_LIST_MESSAGE);
			CustomisedAssert.assertEquals(msg, requiredData.get(1));
			List<String> groupData = foundation.getTextofListElement(CreatePromotions.BUNDLE_LIST);
			CustomisedAssert.assertEquals(groupData.get(0), productName.get(0));
			CustomisedAssert.assertEquals(groupData.get(1), productName.get(1));
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.BUNDLE_LIST_DELETE));
			foundation.click(CreatePromotions.PRODUCT_FILTER);
			foundation.threadWait(Constants.THREE_SECOND);
			createPromotions.selectItemInBuildBundle(productName.get(0));
			createPromotions.selectItemInBuildBundle(productName.get(1));
			foundation.threadWait(Constants.THREE_SECOND);
			msg = foundation.getText(CreatePromotions.BUNDLE_LIST_MESSAGE);
			CustomisedAssert.assertEquals(msg, requiredData.get(2));

			// Creating the Group and validating the Categories section with one or more
			// Items selected
			foundation.click(CreatePromotions.CATEGORY_FILTER);
			color = foundation.getBGColor(CreatePromotions.CATEGORY_FILTER);
			CustomisedAssert.assertEquals(color, rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));
			CustomisedAssert.assertTrue(foundation.getSizeofListElement(CreatePromotions.CATEGORY_GRID) > 0);
			foundation.threadWait(Constants.THREE_SECOND);
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.CATEGORY_NAME_GRID));
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.CATEGORY_UPC_GRID));
			createPromotions.selectCategoryInBuildBundle(columnName.get(0));
			createPromotions.selectCategoryInBuildBundle(columnName.get(1));
			msg = foundation.getText(CreatePromotions.BUNDLE_LIST_MESSAGE);
			CustomisedAssert.assertEquals(msg, requiredData.get(3));
			foundation.click(CreatePromotions.PRODUCT_FILTER);
			msg = foundation.getText(CreatePromotions.BUNDLE_LIST_MESSAGE);
			CustomisedAssert.assertEquals(msg, requiredData.get(3));
			groupData = foundation.getTextofListElement(CreatePromotions.BUNDLE_LIST);
			CustomisedAssert.assertEquals(groupData.get(0), columnName.get(0));
			CustomisedAssert.assertEquals(groupData.get(1), columnName.get(1));
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.BUNDLE_LIST_DELETE));
			foundation.click(CreatePromotions.CATEGORY_FILTER);
			foundation.threadWait(Constants.THREE_SECOND);
			createPromotions.selectCategoryInBuildBundle(columnName.get(0));
			createPromotions.selectCategoryInBuildBundle(columnName.get(1));
			foundation.threadWait(Constants.THREE_SECOND);
			msg = foundation.getText(CreatePromotions.BUNDLE_LIST_MESSAGE);
			CustomisedAssert.assertEquals(msg, requiredData.get(2));
			foundation.click(CreatePromotions.CANCEL_BTN);
			foundation.threadWait(Constants.THREE_SECOND);
			foundation.scrollIntoViewElement(CreatePromotions.BTN_ADD_GROUP);

			// Canceling the Promotion
			createPromotions.cancelingPromotion();

			// Navigating to Location
			navigationBar.navigateToMenuItem(menu.get(1));
			foundation.waitforElementToBeVisible(LocationList.LBL_LOCATION_LIST, 5);
			login.logout();
			browser.close();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "176294 - verify the close option in Select Group's Items and Categories overlay when changes are NOT made to the form"
			+ "176295 - verify the close option in Select Group's Items and Categories overlay when changes are made to the form"
			+ "176298 - Verify the close prompt in Select Group's Items and Categories overlay"
			+ "176299 - Verify the 'yes' option in the 'Confirm Close / Cancel' prompt in Select Group's Items and Categories overlay"
			+ "176300 - Verify the 'No' option in the 'Confirm Close / Cancel' prompt in Select Group's Items and Categories overlay")
	public void verifyBundlePromtionsOverLayCloseOption() {
		final String CASE_NUM = "176294";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);

		List<String> promoName = Arrays
				.asList(rstLocationData.get(CNLocation.PROMOTION_NAME).split(Constants.DELIMITER_TILD));
		List<String> menu = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> productName = Arrays
				.asList(rstLocationData.get(CNLocation.PRODUCT_NAME).split(Constants.DELIMITER_TILD));
		List<String> columnName = Arrays
				.asList(rstLocationData.get(CNLocation.COLUMN_NAME).split(Constants.DELIMITER_TILD));
		List<String> groupName = Arrays
				.asList(rstLocationData.get(CNLocation.DEVICE_NAME).split(Constants.DELIMITER_TILD));
		List<String> actualData = Arrays
				.asList(rstLocationData.get(CNLocation.ACTUAL_DATA).split(Constants.DELIMITER_TILD));
		List<String> requireData = Arrays
				.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Select Org,Menu and Menu Item and click Create Promotion
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menu.get(0));
			CustomisedAssert.assertTrue(foundation.isDisplayed(PromotionList.PAGE_TITLE));
			foundation.click(PromotionList.BTN_CREATE);
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.LBL_CREATE_PROMOTION));

			// Select Promo Type, Promo Name, Display Name and click Next
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.LBL_PROMO_TYPE));
			createPromotions.createPromotion(rstLocationData.get(CNLocation.PROMOTION_TYPE), promoName.get(0),
					promoName.get(1));

			// Choose Org and Location
			createPromotions.selectOrgLoc(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE),
					rstLocationData.get(CNLocation.LOCATION_NAME));

			// Select Bundle Group in Details Page
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.LBL_BUILD_BUNDLE));
			dropDown.selectItem(CreatePromotions.DPD_DISCOUNT_BY, requireData.get(0), Constants.TEXT);
			foundation.waitforElementToBeVisible(CreatePromotions.BTN_ADD_GROUP, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.BTN_ADD_GROUP));

			// Creating Two Group with Items & Category selected
			createPromotions.creatingBundleGroupWithCategory(groupName.get(0), productName.get(0), columnName.get(0));
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.LBL_BUNDLE_GROUP_EDIT));
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.LBL_CREATED_GROUP));

			// Creating another Group and Validate Close option
			foundation.click(CreatePromotions.BTN_ADD_GROUP);
			foundation.threadWait(Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.LBL_BUNDLE_GROUP));
			foundation.click(CreatePromotions.BUNDLE_GROUP_CLOSE_BTN);
			foundation.threadWait(Constants.SHORT_TIME);
			String actual = foundation.getText(CreatePromotions.LBL_CREATED_GROUP);
			CustomisedAssert.assertEquals(actual, groupName.get(0) + requireData.get(1));
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.LBL_BUILD_BUNDLE));
			int size = foundation.getSizeofListElement(CreatePromotions.LBL_BUNDLE_GROUP_EDIT);
			CustomisedAssert.assertTrue(size == 1);

			// Editing Existing Group and Validating Close option with Prompt
			foundation.click(CreatePromotions.LBL_BUNDLE_GROUP_EDIT);
			foundation.waitforElementToBeVisible(CreatePromotions.LBL_BUNDLE_GROUP, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.TXT_GROUP_NAME);
			textBox.clearText(CreatePromotions.TXT_GROUP_NAME);
			textBox.enterText(CreatePromotions.TXT_GROUP_NAME, groupName.get(1));
			foundation.click(CreatePromotions.INPUT_ITEM_SEARCH);
			textBox.clearText(CreatePromotions.INPUT_ITEM_SEARCH);
			textBox.enterText(CreatePromotions.INPUT_ITEM_SEARCH, productName.get(1));
			foundation.click(CreatePromotions.ITEM_CHECK_BOX);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(CreatePromotions.CATEGORY_FILTER);
			foundation.click(CreatePromotions.INPUT_CATEGORY_SEARCH);
			textBox.clearText(CreatePromotions.INPUT_CATEGORY_SEARCH);
			textBox.enterText(CreatePromotions.INPUT_CATEGORY_SEARCH, columnName.get(1));
			foundation.click(CreatePromotions.CATEGORY_CHECK_BOX);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BUNDLE_GROUP_CLOSE_BTN);

			// Validating Prompt content details and click No button
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.CLOSE_GROUP_PROMPT));
			String content = foundation.getText(CreatePromotions.PROMPT_CONTENT);
			CustomisedAssert.assertEquals(content, actualData.get(0));
			content = foundation.getText(CreatePromotions.BTN_EXPIRE);
			CustomisedAssert.assertEquals(content, actualData.get(1));
			content = foundation.getText(CreatePromotions.BTN_PROMPT_CANCEL);
			CustomisedAssert.assertEquals(content, actualData.get(2));
			foundation.click(CreatePromotions.BTN_PROMPT_CANCEL);
			foundation.threadWait(Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.LBL_BUNDLE_GROUP));

			// Validating Prompt by clicking Yes Button
			foundation.click(CreatePromotions.BUNDLE_GROUP_CLOSE_BTN);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_EXPIRE);
			foundation.threadWait(Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.LBL_BUILD_BUNDLE));
			actual = foundation.getText(CreatePromotions.LBL_CREATED_GROUP);
			CustomisedAssert.assertEquals(actual, groupName.get(0) + requireData.get(1));
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.LBL_BUILD_BUNDLE));
			size = foundation.getSizeofListElement(CreatePromotions.LBL_BUNDLE_GROUP_EDIT);
			CustomisedAssert.assertTrue(size == 1);

			// Checking that No changes have done for existing Group
			foundation.click(CreatePromotions.LBL_BUNDLE_GROUP_EDIT);
			foundation.waitforElementToBeVisible(CreatePromotions.LBL_BUNDLE_GROUP, Constants.SHORT_TIME);
//			List<String> list = foundation.getTextofListElement(CreatePromotions.BUNDLE_LIST);
//			CustomisedAssert.assertEquals(list.get(0), productName.get(0));
//			CustomisedAssert.assertEquals(list.get(1), columnName.get(0));
			foundation.click(CreatePromotions.BUNDLE_GROUP_CLOSE_BTN);
			foundation.click(CreatePromotions.BTN_EXPIRE);
			foundation.threadWait(Constants.SHORT_TIME);

			// Canceling the Promotion
			createPromotions.cancelingPromotion();

			// Navigating to Location
			navigationBar.navigateToMenuItem(menu.get(1));
			foundation.waitforElementToBeVisible(LocationList.LBL_LOCATION_LIST, Constants.SHORT_TIME);
			login.logout();
			browser.close();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "C176301-Verify the selection of item in Select Group's Items and Categories overlay when items are already being added"
			+ "C176302-Verify the selection of categories in Select Group's Items and Categories overlay when categories are already")
	public void verifyGroupItemAndCategoryOverlay() {
		final String CASE_NUM = "176301";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);

		List<String> requiredData = Arrays
				.asList(rstLocationData.get(CNLocation.PROMOTION_TYPE).split(Constants.DELIMITER_TILD));
		List<String> org = Arrays.asList(rstLocationData.get(CNLocation.LOCATION_NAME).split(Constants.DELIMITER_TILD));

		try {

			// Select Org,Menu and Menu Item and click Create Promotion
			navigationBar.launchBrowserAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			CustomisedAssert.assertTrue(foundation.isDisplayed(PromotionList.PAGE_TITLE));
			foundation.click(PromotionList.BTN_CREATE);

			// Navigate to create promotion page
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.LBL_CREATE_PROMOTION));
			createPromotions.createPromotion(requiredData.get(0), requiredData.get(1), requiredData.get(2));

			// Navigate to Choosing promotion filter and select organization
			createPromotions.selectOrgLoc(org.get(0), org.get(1));

			// Select one r more group in bundle criteria
			foundation.threadWait(Constants.THREE_SECOND);
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.BUNDLE_BUILD));
			dropDown.selectItem(CreatePromotions.DPD_DISCOUNT_BY, org.get(2), Constants.TEXT);
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.BTN_ADD_GROUP));

			// Create a bundle group in Group criteria
			foundation.click(CreatePromotions.BTN_ADD_GROUP);
			textBox.enterText(CreatePromotions.GROUP_NAME, requiredData.get(1));
			createPromotions.selectItemInBuildBundle(org.get(3));
			foundation.click(CreatePromotions.BTN_ADD);

			// verify the same product is grayed out or not
			foundation.click(CreatePromotions.BTN_ADD_GROUP);
			foundation.threadWait(Constants.THREE_SECOND);
			textBox.enterText(CreatePromotions.GROUP_NAME, requiredData.get(2));
			foundation.click(CreatePromotions.INPUT_ITEM_SEARCH);
			textBox.clearText(CreatePromotions.INPUT_ITEM_SEARCH);
			textBox.enterText(CreatePromotions.INPUT_ITEM_SEARCH, org.get(3));
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.PRODUCTS_DISABLE));

			// Navigate to category tab and create a group
			foundation.click(CreatePromotions.CATEGORY_FILTER);
			createPromotions.selectCategoryInBuildBundle(org.get(4));
			foundation.click(CreatePromotions.BTN_ADD);

			// verify the selected category is grayed out or not
			foundation.click(CreatePromotions.BTN_ADD_GROUP);
			foundation.threadWait(Constants.THREE_SECOND);
			textBox.enterText(CreatePromotions.GROUP_NAME, requiredData.get(2));
			foundation.click(CreatePromotions.CATEGORY_FILTER);
			foundation.click(CreatePromotions.INPUT_CATEGORY_SEARCH);
			textBox.clearText(CreatePromotions.INPUT_CATEGORY_SEARCH);
			textBox.enterText(CreatePromotions.INPUT_CATEGORY_SEARCH, org.get(4));
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.CATEGORY_DISABLE));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "C176303-Verify the selection of items in Select Group's Items and Categories overlay when items belonging to categories"
			+ "C176304-Verify the selection of items in Select Group's Items and Categories overlay when items belonging to categories"
			+ "C177867-Verify the 'Add' Button for existing bundle promotion in Select Group's Items and Categories Overlay"
			+ "C176274-Verify the conflict popup when duplicate items/categories are created for  existing group"
			+ "C176273-Verify the conflict popup when duplicate items/categories are created for current group while being created")
	public void verifyPopupMessageInGroupOverlay() {
		final String CASE_NUM = "176303";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);

		List<String> requiredData = Arrays
				.asList(rstLocationData.get(CNLocation.PROMOTION_TYPE).split(Constants.DELIMITER_TILD));
		List<String> contentpopup = Arrays
				.asList(rstLocationData.get(CNLocation.SHOW_RECORDS).split(Constants.DELIMITER_TILD));
		List<String> org = Arrays.asList(rstLocationData.get(CNLocation.LOCATION_NAME).split(Constants.DELIMITER_TILD));

		try {

			// Select Org,Menu and Menu Item and click Create Promotion
			navigationBar.launchBrowserAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			CustomisedAssert.assertTrue(foundation.isDisplayed(PromotionList.PAGE_TITLE));
			foundation.click(PromotionList.BTN_CREATE);

			// Navigate to create promotion page
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.LBL_CREATE_PROMOTION));
			createPromotions.createPromotion(requiredData.get(0), requiredData.get(1), requiredData.get(2));

			// Navigate to Choosing promotion filter and select organization
			createPromotions.selectOrgLoc(org.get(0), org.get(1));

			// Select one r more group in bundle criteria
			foundation.threadWait(Constants.THREE_SECOND);
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.BUNDLE_BUILD));
			dropDown.selectItem(CreatePromotions.DPD_DISCOUNT_BY, org.get(2), Constants.TEXT);
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.BTN_ADD_GROUP));

			// Create a bundle group in Group criteria
			foundation.click(CreatePromotions.BTN_ADD_GROUP);
			textBox.enterText(CreatePromotions.GROUP_NAME, requiredData.get(1));
			createPromotions.selectItemInBuildBundle(requiredData.get(3));
			foundation.click(CreatePromotions.BTN_ADD);

			// Create a another group with same item in category
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.BUNDLE_BUILD));
			foundation.click(CreatePromotions.BTN_ADD_GROUP);
			textBox.enterText(CreatePromotions.GROUP_NAME, requiredData.get(2));
			foundation.click(CreatePromotions.CATEGORY_FILTER);
			createPromotions.selectCategoryInBuildBundle(requiredData.get(4));

			// verify the popup message and click on yes
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.HEADER_POPUP));
			String text = foundation.getText(CreatePromotions.CONTENT_POPUP);
			CustomisedAssert.assertTrue(text.contains(contentpopup.get(0)));
			foundation.click(CreatePromotions.BTN_YES);
			foundation.click(CreatePromotions.BTN_ADD);

			// verify the selected product after updated a product
			foundation.waitforElementToBeVisible(CreatePromotions.LBL_BUNDLE_GROUP_EDIT, 5);
			String groupname = foundation.getText(CreatePromotions.LBL_CREATED_GROUP);
			CustomisedAssert.assertTrue(groupname.contains(requiredData.get(2)));
			foundation.click(CreatePromotions.LBL_BUNDLE_GROUP_EDIT);
			foundation.threadWait(Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.CATEGORY_FILTER));
			foundation.click(CreatePromotions.CATEGORY_FILTER);
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.CAT_PROMO_GRID));
			foundation.click(CreatePromotions.BTN_ADD);

			// removing the group bundle promotion
			createPromotions.deleteBundleGroup();

			// Create a group with Product
			foundation.click(CreatePromotions.BTN_ADD_GROUP);
			textBox.enterText(CreatePromotions.GROUP_NAME, requiredData.get(1));
			createPromotions.selectItemInBuildBundle(requiredData.get(3));
			foundation.click(CreatePromotions.BTN_ADD);

			// click on same group and change the Item to category
			foundation.click(CreatePromotions.LBL_BUNDLE_GROUP_EDIT);
			foundation.threadWait(Constants.THREE_SECOND);
			foundation.click(CreatePromotions.CATEGORY_FILTER);
			createPromotions.selectCategoryInBuildBundle(requiredData.get(4));

			// verify the popup message
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.CAT_POPUP_HEADER));
			text = foundation.getText(CreatePromotions.CONTENT_POPUP);
			CustomisedAssert.assertTrue(text.contains(contentpopup.get(1)));
			foundation.click(CreatePromotions.BTN_GOTIT);
			foundation.click(CreatePromotions.BTN_ADD);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "C176296-verify the showing message in Select Group's Items and Categories overlay"
			+ "C176297-verify the selected message in Select Group's Items and Categories overlay")
	public void verifyRecordsMessageInItemAndCategory() {
		final String CASE_NUM = "176296";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);

		List<String> requiredData = Arrays
				.asList(rstLocationData.get(CNLocation.PROMOTION_TYPE).split(Constants.DELIMITER_TILD));
		List<String> org = Arrays.asList(rstLocationData.get(CNLocation.LOCATION_NAME).split(Constants.DELIMITER_TILD));
		List<String> product = Arrays
				.asList(rstLocationData.get(CNLocation.COLUMN_NAME).split(Constants.DELIMITER_TILD));

		try {
			// Launch Browser and Login to ADM with Operator account
			navigationBar.launchBrowserAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Navigate to menu item and Click create promotion
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			CustomisedAssert.assertTrue(foundation.isDisplayed(PromotionList.PAGE_TITLE));
			foundation.click(PromotionList.BTN_CREATE);

			// Navigate to create promotion page
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.LBL_CREATE_PROMOTION));
			createPromotions.createPromotion(requiredData.get(0), requiredData.get(1), requiredData.get(2));

			// Navigate to Choosing promotion filter and select organization
			createPromotions.selectOrgLoc(org.get(0), org.get(1));

			// Select Group in bundle build Dropdown
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.BUNDLE_BUILD));
			dropDown.selectItem(CreatePromotions.DPD_DISCOUNT_BY, org.get(2), Constants.TEXT);
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.BTN_ADD_GROUP));

			// click on add group
			foundation.click(CreatePromotions.BTN_ADD_GROUP);
			textBox.enterText(CreatePromotions.GROUP_NAME, requiredData.get(1));

			// Select Item and verify the record
			foundation.click(CreatePromotions.PRODUCT_FILTER);
			textBox.enterText(CreatePromotions.ITEM_SEARCH, product.get(1));
			checkBox.check(CreatePromotions.CHOCOLATE_PRODUCT);
			String record = foundation.getText(CreatePromotions.RECORD_PRODUCT);
			CustomisedAssert.assertTrue(record.contains(product.get(2)));

			// Select Category verify the record
			foundation.click(CreatePromotions.CATEGORY_FILTER);
			textBox.enterText(CreatePromotions.INPUT_CATEGORY_SEARCH, product.get(6));
			checkBox.check(CreatePromotions.CAT_CATEGORY);
			foundation.threadWait(Constants.SHORT_TIME);
			String catrecord = foundation.getText(CreatePromotions.RECORD_CATEGORY);
			CustomisedAssert.assertTrue(catrecord.contains(product.get(3)));

			// verify the product & category selected
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.SELECTION));
			foundation.threadWait(Constants.SHORT_TIME);
			String catAndprod = foundation.getText(CreatePromotions.RECORD);
			CustomisedAssert.assertEquals(catAndprod, product.get(4));

			// Uncheck the category and Item
			checkBox.unCheck(CreatePromotions.CATEGORY_UNCHECK);
			foundation.threadWait(Constants.THREE_SECOND);
			foundation.click(CreatePromotions.PRODUCT_FILTER);
			checkBox.unCheck(CreatePromotions.PRODUCT_UNCHECK);

			// verify the product & category unselected in record
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.SELECTION));
			String cateprod = foundation.getText(CreatePromotions.RECORD);
			CustomisedAssert.assertEquals(cateprod, product.get(5));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "C176289-verify the search criteria by item name" + "C176291- verify the search criteria by UPC"
			+ "C176290-verify the search criteria by Category name")
	public void verifySearchCriteriaByItemCategoryAndUPC() {
		final String CASE_NUM = "176289";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);

		List<String> requiredData = Arrays
				.asList(rstLocationData.get(CNLocation.PROMOTION_TYPE).split(Constants.DELIMITER_TILD));
		List<String> Datas = Arrays.asList(rstLocationData.get(CNLocation.COLUMN_NAME).split(Constants.DELIMITER_TILD));
		List<String> org = Arrays.asList(rstLocationData.get(CNLocation.LOCATION_NAME).split(Constants.DELIMITER_TILD));
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Select Org,Menu and Menu Item and click Create Promotion
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			CustomisedAssert.assertTrue(foundation.isDisplayed(PromotionList.PAGE_TITLE));
			foundation.click(PromotionList.BTN_CREATE);
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.LBL_CREATE_PROMOTION));

			// Select Promo Type, Promo Name, Display Name and click Next
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.LBL_PROMO_TYPE));
			createPromotions.createPromotion(requiredData.get(0), requiredData.get(1), requiredData.get(2));

			// Choose Org and Location
			createPromotions.selectOrgLoc(org.get(0), org.get(1));

			// Select Bundle Group in Details Page
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.LBL_BUILD_BUNDLE));
			dropDown.selectItem(CreatePromotions.DPD_DISCOUNT_BY, rstLocationData.get(CNLocation.NAME), Constants.TEXT);
			foundation.waitforElementToBeVisible(CreatePromotions.BTN_ADD_GROUP, 5);
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.BTN_ADD_GROUP));

			// Select Product and verify the product
			foundation.click(CreatePromotions.BTN_ADD_GROUP);
			foundation.threadWait(Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.NAME_GRID));
			textBox.enterText(CreatePromotions.INPUT_ITEM_SEARCH, Datas.get(1));
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.BIRTHDAY_GRID));

			// Select Category and verify the Category
			foundation.click(CreatePromotions.CATEGORY_FILTER);
			textBox.enterText(CreatePromotions.INPUT_CATEGORY_SEARCH, Datas.get(2));
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.CAT_PROMO_GRID));

			// Select product and verify UPC
			foundation.click(CreatePromotions.PRODUCT_FILTER);
			textBox.enterText(CreatePromotions.INPUT_ITEM_SEARCH, Datas.get(0));
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.BIRTHDAY_GRID));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "C176790-Verify Bundle promo - pricing section when all categories are selected")
	public void verifyBundlePromoPricingSection() {
		final String CASE_NUM = "176790";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);

		List<String> requiredData = Arrays
				.asList(rstLocationData.get(CNLocation.PROMOTION_TYPE).split(Constants.DELIMITER_TILD));
		List<String> org = Arrays.asList(rstLocationData.get(CNLocation.LOCATION_NAME).split(Constants.DELIMITER_TILD));
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Select Org,Menu and Menu Item and click Create Promotion
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			CustomisedAssert.assertTrue(foundation.isDisplayed(PromotionList.PAGE_TITLE));
			foundation.click(PromotionList.BTN_CREATE);
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.LBL_CREATE_PROMOTION));

			// Select Promo Type, Promo Name, Display Name and click Next
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.LBL_PROMO_TYPE));
			createPromotions.createPromotion(requiredData.get(0), requiredData.get(1), requiredData.get(2));

			// Choose Org and Location
			createPromotions.selectOrgLoc(org.get(0), org.get(1));

			// Select Bundle Group in Details Page
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.LBL_BUILD_BUNDLE));
			dropDown.selectItem(CreatePromotions.DPD_DISCOUNT_BY, org.get(2), Constants.TEXT);
			foundation.waitforElementToBeVisible(CreatePromotions.ALL_CATEGORY, 5);
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.SEARCH_CATEGORY));

			// Check all categories checkbox and verify the price
			checkBox.check(CreatePromotions.ALL_CATEGORY);
			foundation.waitforElementToBeVisible(CreatePromotions.PRICING_GRID, 5);
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.PRICE_TAG));

			// verify quantity field
			foundation.waitforElement(CreatePromotions.QTY, Constants.THREE_SECOND);
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.QTY));
			String text = foundation.getAttributeValue(CreatePromotions.QTY);
			CustomisedAssert.assertEquals(text, requiredData.get(4));
			textBox.enterText(CreatePromotions.QTY, requiredData.get(3));
			foundation.click(CreatePromotions.BTN_CREATE);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_OK);

			// verify the created promotion
			CustomisedAssert.assertTrue(foundation.isDisplayed(PromotionList.PAGE_TITLE));
			promotionList.clickSelectedRow(requiredData.get(5), requiredData.get(1));
			CustomisedAssert.assertTrue(foundation.isDisplayed(PromotionList.BUY_PRICE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(EditPromotion.PRICING));
			foundation.waitforElement(EditPromotion.BTN_END_PROMO, Constants.SHORT_TIME);
			foundation.click(EditPromotion.BTN_END_PROMO);
			foundation.waitforElement(EditPromotion.BTN_CONTINUE, Constants.SHORT_TIME);
			foundation.click(EditPromotion.BTN_CONTINUE);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "C177866-Verify the 'Add' Button wheile creating new bundle promotion in Select Group's Items and Categories Overlay")
	public void verifyGroupDetailsInBundleCriteria() {
		final String CASE_NUM = "177866";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);

		List<String> requiredData = Arrays
				.asList(rstLocationData.get(CNLocation.PROMOTION_TYPE).split(Constants.DELIMITER_TILD));
		List<String> org = Arrays.asList(rstLocationData.get(CNLocation.LOCATION_NAME).split(Constants.DELIMITER_TILD));
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Select Org,Menu and Menu Item and click Create Promotion
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			CustomisedAssert.assertTrue(foundation.isDisplayed(PromotionList.PAGE_TITLE));
			foundation.click(PromotionList.BTN_CREATE);
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.LBL_CREATE_PROMOTION));

			// Select Promo Type, Promo Name, Display Name and click Next
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.LBL_PROMO_TYPE));
			createPromotions.createPromotion(requiredData.get(0), requiredData.get(1), requiredData.get(2));

			// Choose Org and Location
			createPromotions.selectOrgLoc(org.get(0), org.get(1));

			// Select one r more group in bundle criteria
			foundation.threadWait(Constants.THREE_SECOND);
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.BUNDLE_BUILD));
			dropDown.selectItem(CreatePromotions.DPD_DISCOUNT_BY, org.get(2), Constants.TEXT);
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.BTN_ADD_GROUP));

			// Create a bundle group in Group criteria
			foundation.click(CreatePromotions.BTN_ADD_GROUP);
			textBox.enterText(CreatePromotions.GROUP_NAME, requiredData.get(1));
			createPromotions.selectItemInBuildBundle(requiredData.get(3));
			foundation.click(CreatePromotions.BTN_ADD);

			// verify the created group name under bundle criteria & price tag
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.LBL_CREATED_GROUP));
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.BUNDLE_GROUP_NAME));
			String text = foundation.getText(CreatePromotions.BUNDLE_GROUP_NAME);
			CustomisedAssert.assertTrue(text.contains(requiredData.get(1)));
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.PRICE_TAG));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "176276 - Verify the Group bundle criteria in the Pricing section"
			+ "176277 - Verify the deletion of Group bundle criteria in the Pricing section")
	public void verifyBundleCriteriaInPricingSection() {
		final String CASE_NUM = "176276";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);

		List<String> promoName = Arrays
				.asList(rstLocationData.get(CNLocation.PROMOTION_NAME).split(Constants.DELIMITER_TILD));
		List<String> menu = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> productName = Arrays
				.asList(rstLocationData.get(CNLocation.PRODUCT_NAME).split(Constants.DELIMITER_TILD));
		List<String> categoryName = Arrays
				.asList(rstLocationData.get(CNLocation.COLUMN_NAME).split(Constants.DELIMITER_TILD));
		List<String> requiredData = Arrays
				.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		String groupName = promoName.get(1) + strings.getRandomCharacter();
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Select Org,Menu and Menu Item and click Create Promotion
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menu.get(0));
			CustomisedAssert.assertTrue(foundation.isDisplayed(PromotionList.PAGE_TITLE));
			foundation.click(PromotionList.BTN_CREATE);
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.LBL_CREATE_PROMOTION));

			// Select Promo Type, Promo Name, Display Name and click Next
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.LBL_PROMO_TYPE));
			createPromotions.createPromotion(rstLocationData.get(CNLocation.PROMOTION_TYPE), promoName.get(0),
					promoName.get(1));

			// Choose Org and Location
			createPromotions.selectOrgLoc(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE),
					rstLocationData.get(CNLocation.LOCATION_NAME));

			// Select Bundle Group in Details Page
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.LBL_BUILD_BUNDLE));
			dropDown.selectItem(CreatePromotions.DPD_DISCOUNT_BY, requiredData.get(0), Constants.TEXT);
			foundation.waitforElementToBeVisible(CreatePromotions.BTN_ADD_GROUP, 5);
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.BTN_ADD_GROUP));

			// Creating the Group and validating the Icons
			createPromotions.creatingBundleGroupWithCategory(groupName, productName.get(0), categoryName.get(0));
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.LBL_BUNDLE_GROUP_EDIT));
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.LBL_CREATED_GROUP));

			// Validating the Bundle Item under Pricing Section
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.BUNDLE_CRITERIA));
			String value = foundation.getTextAttribute(CreatePromotions.BUNDLE_CRITERIA, "value");
			CustomisedAssert.assertEquals(value, groupName + requiredData.get(1));
			value = foundation.getText(CreatePromotions.BUNDLE_ITEM_REMOVE);
			CustomisedAssert.assertEquals(String.valueOf(value), requiredData.get(2));

			// Deleting the Bundle Group and validating the Prompt
			createPromotions.deleteBundleGroup();
			CustomisedAssert.assertFalse(foundation.isDisplayed(CreatePromotions.BUNDLE_CRITERIA));
			foundation.scrollIntoViewElement(CreatePromotions.BTN_ADD_GROUP);

			// Canceling the Promotion
			createPromotions.cancelingPromotion();

			// Navigating to Location
			navigationBar.navigateToMenuItem(menu.get(1));
			foundation.waitforElementToBeVisible(LocationList.LBL_LOCATION_LIST, 5);
			login.logout();
			browser.close();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "C176275-verify the summary message when groups are added in the bundle criteria")
	public void verifySummaryMessageWhenGroupsAreAddedInBundleCriteria() {
		final String CASE_NUM = "176275";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);

		List<String> requiredData = Arrays
				.asList(rstLocationData.get(CNLocation.PROMOTION_TYPE).split(Constants.DELIMITER_TILD));
		List<String> org = Arrays.asList(rstLocationData.get(CNLocation.LOCATION_NAME).split(Constants.DELIMITER_TILD));
		try {
			// login, select menu and menu item
			promotionList.navigateMenuAndCreatePromo(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select Promo Type, Promo Name, Display Name and click Next
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.LBL_PROMO_TYPE));
			createPromotions.createPromotion(requiredData.get(0), requiredData.get(1), requiredData.get(2));

			// Choose Org and Location
			createPromotions.selectOrgLoc(org.get(0), org.get(1));

			// Select one r more group in bundle criteria
			foundation.threadWait(Constants.THREE_SECOND);
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.BUNDLE_BUILD));
			dropDown.selectItem(CreatePromotions.DPD_DISCOUNT_BY, org.get(2), Constants.TEXT);
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.BTN_ADD_GROUP));

			// Create a bundle group in Group criteria
			foundation.click(CreatePromotions.BTN_ADD_GROUP);
			foundation.waitforElementToBeVisible(CreatePromotions.PRODUCT_FILTER, Constants.SHORT_TIME);
			textBox.enterText(CreatePromotions.GROUP_NAME, requiredData.get(1));
			createPromotions.selectItemInBuildBundle(requiredData.get(3));
			foundation.click(CreatePromotions.BTN_ADD);

			// Create another bundle group in Group with category
			foundation.waitforElementToBeVisible(CreatePromotions.BTN_ADD_GROUP, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_ADD_GROUP);
			foundation.waitforElementToBeVisible(CreatePromotions.CATEGORY_FILTER, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.CATEGORY_FILTER);
			textBox.enterText(CreatePromotions.GROUP_NAME, requiredData.get(2));
			createPromotions.selectCategoryInBuildBundle(requiredData.get(4));
			foundation.click(CreatePromotions.BTN_ADD);

			// verify the summary message after created group
			foundation.waitforElementToBeVisible(CreatePromotions.BUNDLE_SUMMARY, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.LBL_CREATED_GROUP));
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.BUNDLE_SUMMARY));
			String groupname = foundation.getText(CreatePromotions.SUMMARY_GROUPNAME1);
			CustomisedAssert.assertEquals(groupname, requiredData.get(1));
			groupname = foundation.getText(CreatePromotions.SUMMARY_GROUPNAME2);
			CustomisedAssert.assertEquals(groupname, requiredData.get(2));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "C177865-verify the selection of the category in Select Group's Items and Categories overlay")
	public void verifySelectionOfTheCategoryInSelectGroupAndCategoriesOverlay() {
		final String CASE_NUM = "177865";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);

		List<String> requiredData = Arrays
				.asList(rstLocationData.get(CNLocation.PROMOTION_TYPE).split(Constants.DELIMITER_TILD));
		List<String> org = Arrays.asList(rstLocationData.get(CNLocation.LOCATION_NAME).split(Constants.DELIMITER_TILD));

		try {
			// login, select menu and menu item
			promotionList.navigateMenuAndCreatePromo(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Navigate to create promotion page
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.LBL_CREATE_PROMOTION));
			createPromotions.createPromotion(requiredData.get(0), requiredData.get(1), requiredData.get(2));

			// Navigate to Choosing promotion filter and select organization
			createPromotions.selectOrgLoc(org.get(0), org.get(1));

			// Select Group in bundle build Dropdown
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.BUNDLE_BUILD));
			dropDown.selectItem(CreatePromotions.DPD_DISCOUNT_BY, org.get(2), Constants.TEXT);
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.BTN_ADD_GROUP));

			// click on add group
			foundation.click(CreatePromotions.BTN_ADD_GROUP);
			textBox.enterText(CreatePromotions.GROUP_NAME, requiredData.get(1));

			// Select Category verify the record
			foundation.click(CreatePromotions.CATEGORY_FILTER);
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.CATEGORY_GRID));
			createPromotions.selectCategoryInBuildBundle(requiredData.get(3));
			foundation.threadWait(Constants.SHORT_TIME);
			String cat_record = foundation.getText(CreatePromotions.RECORD);
			CustomisedAssert.assertEquals(cat_record, rstLocationData.get(CNLocation.SHOW_RECORDS));

			// Navigate to product filter and verify the record
			foundation.click(CreatePromotions.PRODUCT_FILTER);
			foundation.waitforElementToBeVisible(CreatePromotions.GROUP_NAME, 5);
			cat_record = foundation.getText(CreatePromotions.RECORD);
			CustomisedAssert.assertEquals(cat_record, rstLocationData.get(CNLocation.SHOW_RECORDS));
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.BUNDLE_LIST));
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.LBL_BUNDLE_LIST));
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.NAME_BUILD_LIST));
			foundation.click(CreatePromotions.DELETE_BUILD);
			CustomisedAssert.assertFalse(foundation.isDisplayed(CreatePromotions.DELETE_BUILD));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * @author karthikr SOS-30671
	 * @date - 17/06/2022
	 */
	@Test(description = "197135 - To verify the Promotion, when Promotion Type changes from Bundle to On Screen Under Categories")
	public void verifyPromtionChangesFromBundleToOnScreenUnderCategories() {
		final String CASE_NUM = "197135";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);

		List<String> promoName = Arrays
				.asList(rstLocationData.get(CNLocation.PROMOTION_NAME).split(Constants.DELIMITER_TILD));
		List<String> promoType = Arrays
				.asList(rstLocationData.get(CNLocation.PROMOTION_TYPE).split(Constants.DELIMITER_TILD));
		List<String> menu = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> requiredData = Arrays
				.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		try {
			// Launching Browser and Creating Promotion on Bundle upto Location Selection
			createPromotions.launchBrowserAndCreateBundlePromoWithLocationDetails(menu.get(0), promoType.get(0),
					promoName.get(0), promoName.get(1), rstLocationData.get(CNLocation.LOCATION_NAME));

			// Select Build Bundle as Category in Details Page with All Categories checkbox
			createPromotions.selectBuildBundleAsCategoryAndCheckBox(requiredData.get(2));

			// Move to Create Promotion Page, Select Promotion Type as 'On Screen' and
			// Travel Back to Promotion Details Page
			createPromotions.changePromotionType(promoType.get(1));

			// Validating the Promotion Details Page for 'On Screen Promotion'
			String title = foundation.getText(CreatePromotions.LBL_PAGE_TITLE);
			CustomisedAssert.assertEquals(title, promoType.get(1));
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.ON_SCREEN_TENDER_DETAILS));

			// Canceling the Promotion
			createPromotions.cancelingPromotion();

			// Navigating to Location
			navigationBar.navigateToMenuItem(menu.get(1));
			foundation.waitforElementToBeVisible(LocationList.LBL_LOCATION_LIST, Constants.SHORT_TIME);
			login.logout();
			browser.close();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * @author karthikr SOS-30671
	 * @date - 20/06/2022
	 */
	@Test(description = "197134 - To verify the Promotion, when Promotion Type changes from Bundle to On Screen Under Items")
	public void verifyPromtionChangesFromBundleToOnScreenUnderItems() {
		final String CASE_NUM = "197134";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);

		List<String> promoName = Arrays
				.asList(rstLocationData.get(CNLocation.PROMOTION_NAME).split(Constants.DELIMITER_TILD));
		List<String> promoType = Arrays
				.asList(rstLocationData.get(CNLocation.PROMOTION_TYPE).split(Constants.DELIMITER_TILD));
		List<String> menu = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> requiredData = Arrays
				.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		try {
			// Launching Browser and Creating Promotion on Bundle upto Location Selection
			createPromotions.launchBrowserAndCreateBundlePromoWithLocationDetails(menu.get(0), promoType.get(0),
					promoName.get(0), promoName.get(1), rstLocationData.get(CNLocation.LOCATION_NAME));

			// Select Build Bundle as Item in Details Page with All Items checkbox
			createPromotions.selectBuildBundleAsItemAndCheckBox(requiredData.get(1));

			// Move to Create Promotion Page, Select Promotion Type as 'On Screen' and
			// Travel Back to Promotion Details Page
			createPromotions.changePromotionType(promoType.get(1));

			// Validating the Promotion Details Page for 'On Screen Promotion'
			String title = foundation.getText(CreatePromotions.LBL_PAGE_TITLE);
			CustomisedAssert.assertEquals(title, promoType.get(1));
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.ON_SCREEN_TENDER_DETAILS));

			// Canceling the Promotion
			createPromotions.cancelingPromotion();

			// Navigating to Location
			navigationBar.navigateToMenuItem(menu.get(1));
			foundation.waitforElementToBeVisible(LocationList.LBL_LOCATION_LIST, Constants.SHORT_TIME);
			login.logout();
			browser.close();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * @author karthikr SOS-30671
	 * @date - 23/06/2022
	 */
	@Test(description = "197133 - To verify the Promotion, when Promotion Type changes from Bundle to Tender Discount Under Categories")
	public void verifyPromtionChangesFromBundleToTenderUnderCategories() {
		final String CASE_NUM = "197133";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);

		List<String> promoName = Arrays
				.asList(rstLocationData.get(CNLocation.PROMOTION_NAME).split(Constants.DELIMITER_TILD));
		List<String> promoType = Arrays
				.asList(rstLocationData.get(CNLocation.PROMOTION_TYPE).split(Constants.DELIMITER_TILD));
		List<String> menu = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> requiredData = Arrays
				.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		try {
			// Launching Browser and Creating Promotion on Bundle upto Location Selection
			createPromotions.launchBrowserAndCreateBundlePromoWithLocationDetails(menu.get(0), promoType.get(0),
					promoName.get(0), promoName.get(1), rstLocationData.get(CNLocation.LOCATION_NAME));

			// Select Build Bundle as Category in Details Page with All Categories checkbox
			createPromotions.selectBuildBundleAsCategoryAndCheckBox(requiredData.get(2));

			// Move to Create Promotion Page, Select Promotion Type as 'Tender' and
			// Travel Back to Promotion Details Page
			createPromotions.changePromotionType(promoType.get(1));

			// Validating the Promotion Details Page for 'Tender Discount Promotion'
			String title = foundation.getText(CreatePromotions.LBL_PAGE_TITLE);
			CustomisedAssert.assertEquals(title, promoType.get(1));
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.TENDER_DISCOUNT_DETAILS));

			// Canceling the Promotion
			createPromotions.cancelingPromotion();

			// Navigating to Location
			navigationBar.navigateToMenuItem(menu.get(1));
			foundation.waitforElementToBeVisible(LocationList.LBL_LOCATION_LIST, Constants.SHORT_TIME);
			login.logout();
			browser.close();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * @author karthikr SOS-30671
	 * @date - 24/06/2022
	 */
	@Test(description = "197132 - To verify the Promotion, when Promotion Type changes from Bundle to Tender Discount Under Items")
	public void verifyPromtionChangesFromBundleToTenderUnderItems() {
		final String CASE_NUM = "197132";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);

		List<String> promoName = Arrays
				.asList(rstLocationData.get(CNLocation.PROMOTION_NAME).split(Constants.DELIMITER_TILD));
		List<String> promoType = Arrays
				.asList(rstLocationData.get(CNLocation.PROMOTION_TYPE).split(Constants.DELIMITER_TILD));
		List<String> menu = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> requiredData = Arrays
				.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		try {
			// Launching Browser and Creating Promotion on Bundle upto Location Selection
			createPromotions.launchBrowserAndCreateBundlePromoWithLocationDetails(menu.get(0), promoType.get(0),
					promoName.get(0), promoName.get(1), rstLocationData.get(CNLocation.LOCATION_NAME));

			// Select Build Bundle as Item in Details Page with All Item checkbox
			createPromotions.selectBuildBundleAsItemAndCheckBox(requiredData.get(1));

			// Move to Create Promotion Page, Select Promotion Type as 'Tender' and
			// Travel Back to Promotion Details Page
			createPromotions.changePromotionType(promoType.get(1));

			// Validating the Promotion Details Page for 'Tender Discount Promotion'
			String title = foundation.getText(CreatePromotions.LBL_PAGE_TITLE);
			CustomisedAssert.assertEquals(title, promoType.get(1));
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.TENDER_DISCOUNT_DETAILS));

			// Canceling the Promotion
			createPromotions.cancelingPromotion();

			// Navigating to Location
			navigationBar.navigateToMenuItem(menu.get(1));
			foundation.waitforElementToBeVisible(LocationList.LBL_LOCATION_LIST, Constants.SHORT_TIME);
			login.logout();
			browser.close();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * @author karthikr SOS-30671
	 * @date - 29/06/2022
	 */
	@Test(description = "197136 - To Verify whether 'Group' is disabled in bundle Promotion, when 'Items or Categories' is Selected")
	public void verifyWhetherGroupIsDisabledInBundlePromtionWhenItemOrCategorySelected() {
		final String CASE_NUM = "197136";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);

		List<String> promoName = Arrays
				.asList(rstLocationData.get(CNLocation.PROMOTION_NAME).split(Constants.DELIMITER_TILD));
		List<String> menu = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> requiredData = Arrays
				.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		try {
			// Launching Browser and Creating Promotion on Bundle upto Location Selection
			createPromotions.launchBrowserAndCreateBundlePromoWithLocationDetails(menu.get(0),
					rstLocationData.get(CNLocation.PROMOTION_TYPE), promoName.get(0), promoName.get(1),
					rstLocationData.get(CNLocation.LOCATION_NAME));

			// Select Build Bundle as Item in Details Page with All Item checkbox
			createPromotions.selectBuildBundleAsItemAndCheckBox(requiredData.get(1));

			// Verify Group under Build Bundle is disabled or not
			String value = createPromotions.verifyGroupIsDisabledOrNot();
			CustomisedAssert.assertEquals(value, requiredData.get(5));

			// Select Build Bundle as Category in Details Page with All Categories checkbox
			createPromotions.selectBuildBundleAsCategoryAndCheckBox(requiredData.get(2));

			// Verify Group under Build Bundle is disabled or not
			value = createPromotions.verifyGroupIsDisabledOrNot();
			CustomisedAssert.assertEquals(value, requiredData.get(5));

			// Canceling the Promotion
			createPromotions.cancelingPromotion();

			// Navigating to Location
			navigationBar.navigateToMenuItem(menu.get(1));
			foundation.waitforElementToBeVisible(LocationList.LBL_LOCATION_LIST, Constants.SHORT_TIME);
			login.logout();
			browser.close();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "197842-SOS-30207 ADM>Able to create the promotions for the products that are not available in locations")
	public void createOnScreenPromotionWithAgainSelectingLocation() {
		final String CASE_NUM = "197842";

		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
		rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);

		String promotionType = rstLocationData.get(CNLocation.PROMOTION_TYPE);
		String promotionName = strings.getRandomCharacter();
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			String menuItem = rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM);
			navigationBar.navigateToMenuItem(menuItem);
			List<String> currentData = Arrays
					.asList(rstLocationData.get(CNLocation.PRODUCT_NAME).split(Constants.DELIMITER_TILD));

			List<String> newData = Arrays
					.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));

			// Basic Information Page
			foundation.click(PromotionList.BTN_CREATE);
			createPromotions.newPromotion(promotionType, promotionName, promotionName, currentData.get(0),
					currentData.get(1));
			foundation.waitforElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);

			// Promotion Details Page
			dropDown.selectItem(CreatePromotions.DPD_DISCOUNT_BY, currentData.get(2), Constants.TEXT);
			textBox.enterText(CreatePromotions.TXT_ITEM, currentData.get(3));
			foundation.threadWait(Constants.ONE_SECOND);
			textBox.enterText(CreatePromotions.TXT_ITEM, Keys.ENTER);
			foundation.click(CreatePromotions.BTN_BACK);
			createPromotions.deselectOrgAndLoc();
//			foundation.waitforElementToBeVisible(CreatePromotions.CHOOSE_ROLE_DEVICE_FILTER, 3);
//			foundation.objectClick(CreatePromotions.BTN_CANCEL_1);
//			foundation.waitforElementToBeVisible(CreatePromotions.CHOOSE_LOCATION, 3);
//			foundation.click(CreatePromotions.BTN_BACK);
//			foundation.waitforElement(CreatePromotions.TXT_SEARCH_ORGPAGE, Constants.SHORT_TIME);
//			textBox.enterText(CreatePromotions.TXT_SEARCH_ORGPAGE, newData.get(2));
//			foundation.threadWait(Constants.TWO_SECOND);
//			foundation.click(CreatePromotions.CHECKBOX_ORG);
//			foundation.waitforElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);
//			dropDown.deSelectItem(CreatePromotions.DPD_ORG, newData.get(2), Constants.TEXT);
//			foundation.click(CreatePromotions.BTN_ORG_RIGHT);
//			foundation.threadWait(Constants.THREE_SECOND);
//			dropDown.selectItem(CreatePromotions.DPD_LOC, newData.get(1), Constants.TEXT);
//			foundation.threadWait(Constants.THREE_SECOND);
//			foundation.click(CreatePromotions.BTN_LOC_RIGHT);
//			foundation.click(CreatePromotions.BTN_NEXT);
//			dropDown.selectItem(DPD_LOC, locationName, Constants.TEXT);
//			foundation.waitforElement(CreatePromotions.TXT_LOC_SEARCH, Constants.SHORT_TIME);
//			textBox.enterText(CreatePromotions.TXT_LOC_SEARCH, newData.get(1));
//			foundation.threadWait(Constants.TWO_SECOND);
//			foundation.click(CreatePromotions.CHECKBOX_LOC);
//			foundation.waitforElement(CreatePromotions.BTN_NEXT, Constants.THREE_SECOND);
//			foundation.click(CreatePromotions.BTN_NEXT);
//			foundation.waitforElement(CreatePromotions.BTN_NEXT, Constants.THREE_SECOND);
//			foundation.click(CreatePromotions.BTN_NEXT);

			// click on create button and validate the err
			foundation.click(CreatePromotions.BTN_CREATE_PROMOTION);
			foundation.threadWait(Constants.THREE_SECOND);
			CustomisedAssert.assertTrue(foundation.getText(CreatePromotions.TXT_PROMO_ERROR)
					.equals(rstLocationData.get(CNLocation.SHOW_RECORDS)));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * @author afrosean Date:14-09-2022
	 */
	@Test(description = "204886-ADM > Promotion > Verify pricing section after creating promotion")
	public void verifyPriceAfterCreatingPromotion() {
		final String CASE_NUM = "204886";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);

		List<String> promoName = Arrays
				.asList(rstLocationData.get(CNLocation.PROMOTION_NAME).split(Constants.DELIMITER_TILD));
		try {
			// Launching Browser and Creating Promotion on Bundle upto Location Selection
			createPromotions.launchBrowserAndCreateBundlePromoWithLocationDetails(
					rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM), promoName.get(0), promoName.get(1),
					promoName.get(2), promoName.get(3));

			// Select Build Bundle as Category in Details Page with All Categories checkBox
			createPromotions.selectBuildBundleAsCategoryAndCheckBox(promoName.get(4));

			// verify price in promotion details page
			createPromotions.verifyPriceInPromotionDetails(promoName.get(5));

			// search with same promotion
			promotionList.searchPromotion(promoName.get(1), promoName.get(6));
			promotionList.clickSelectedRow(promoName.get(7), promoName.get(1));

			// verify price in promotion details page
			createPromotions.navigateToPromotionDetailsPageAndVerifyPrice(promoName.get(5));

			// expire promotion
			createPromotions.cancelingPromotionAndExpired();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
}
