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
			// String locationName = rstLocationData.get(CNLocation.LOCATION_NAME);
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
			foundation.waitforElement(CreatePromotions.DPD_ORG, Constants.SHORT_TIME);
			dropDown.selectItem(CreatePromotions.DPD_ORG, requiredData.get(0), Constants.TEXT);
			foundation.click(CreatePromotions.BTN_ORG_RIGHT);
			dropDown.selectItem(CreatePromotions.DPD_ORG, requiredData.get(1), Constants.TEXT);
			foundation.click(CreatePromotions.BTN_ORG_RIGHT);
			foundation.threadWait(Constants.TWO_SECOND);
			foundation.click(CreatePromotions.SELECT_ALL_LOCATION);
			foundation.click(CreatePromotions.BTN_LOC_RIGHT);
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

			dropDown.selectItem(CreatePromotions.DPD_ORG, orgName, Constants.TEXT);
			foundation.click(CreatePromotions.BTN_ORG_RIGHT);
			foundation.threadWait(Constants.TWO_SECOND);
			foundation.click(CreatePromotions.SELECT_ALL_LOCATION);

			foundation.threadWait(Constants.TWO_SECOND);
			foundation.click(CreatePromotions.BTN_NEXT);
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
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);

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
			foundation.threadWait(Constants.TWO_SECOND);
			String allOption = rstLocationListData.get(CNLocationList.COLUMN_NAME);
			foundation.objectFocus(UserList.TXT_SEARCH_LOC);
			dropDown.selectItem(UserList.TXT_SEARCH_LOC, allOption, Constants.TEXT);
			foundation.threadWait(Constants.TWO_SECOND);
			foundation.click(UserList.BTN_UPDATE_USER);
			foundation.waitforElement(UserList.LBL_USER_LIST, Constants.SHORT_TIME);
			foundation.threadWait(Constants.TWO_SECOND);
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
			textBox.enterText(CreatePromotions.SEARCH_CATEGORY, discountType.get(2));
			foundation.threadWait(Constants.ONE_SECOND);
			textBox.enterText(CreatePromotions.SEARCH_CATEGORY, Keys.ENTER);
			dropDown.selectItem(CreatePromotions.DPD_DISCOUNT_BY, discountType.get(1), Constants.TEXT);
			textBox.enterText(CreatePromotions.TXT_ITEM, discountType.get(3));
			foundation.threadWait(Constants.ONE_SECOND);
			textBox.enterText(CreatePromotions.TXT_ITEM, Keys.ENTER);
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
			dropDown.selectItem(CreatePromotions.DPD_DISCOUNT_BY, requiredData.get(2), Constants.TEXT);
			textBox.enterText(CreatePromotions.SEARCH_CATEGORY, actualData.get(1));
			foundation.threadWait(Constants.ONE_SECOND);
			textBox.enterText(CreatePromotions.SEARCH_CATEGORY, Keys.ENTER);
			foundation.click(CreatePromotions.BTN_CREATE);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_OK);

			// Verify Item correctly updated in Promotion Screen
			foundation.waitforElement(PromotionList.TXT_SEARCH_PROMONAME, Constants.SHORT_TIME);
			textBox.enterText(PromotionList.TXT_SEARCH_PROMONAME, promotionName);
			foundation.click(PromotionList.BTN_SEARCH);
			foundation.doubleClick(PromotionList.TBL_COLUMN_NAME);
			foundation.waitforElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.threadWait(Constants.TWO_SECOND);
			foundation.click(CreatePromotions.BTN_NEXT);

			foundation.threadWait(Constants.TWO_SECOND);
			String bundleOption = dropDown.getSelectedItem(CreatePromotions.DPD_DISCOUNT_BY);
			CustomisedAssert.assertEquals(bundleOption, requiredData.get(2));
			String itemValue = dropDown.getSelectedItem(CreatePromotions.DPD_SELECTED_ITEM);
			CustomisedAssert.assertEquals(itemValue, actualData.get(1));
			foundation.click(CreatePromotions.BTN_NEXT);
			// foundation.waitforElement(CreatePromotions.BTN_CONTINUE,
			// Constants.SHORT_TIME);
			// foundation.click(CreatePromotions.BTN_CONTINUE);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_OK);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// Expire Promotion
			promotionList.expirePromotion(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM), promotionName,
					statusType, gridName);
		}
	}

	@Test(description = "141831-This test validates the Category getting updated in Promotion Screen- Bundle Promotion", enabled = false)
	public void verifyCategoryInPromotionScreen() {
		final String CASE_NUM = "141831";

		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
		rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);

		navigationBar.selectOrganization(
				propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
		navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
		foundation.waitforElement(PromotionList.PAGE_TITLE, Constants.SHORT_TIME);

		String promotionType = rstLocationData.get(CNLocation.PROMOTION_TYPE);
		String gridName = rstLocationData.get(CNLocation.TAB_NAME);
		String locationName = rstLocationData.get(CNLocation.LOCATION_NAME);
		List<String> requiredData = Arrays
				.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> actualData = Arrays
				.asList(rstLocationData.get(CNLocation.ACTUAL_DATA).split(Constants.DELIMITER_TILD));
		String promotionName = strings.getRandomCharacter();
		String statusType = rstLocationData.get(CNLocation.LOCATION_NAME);
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Basic Information Page
			foundation.click(PromotionList.BTN_CREATE);
			foundation.isDisplayed(CreatePromotions.LBL_CREATE_PROMOTION);
			foundation.isDisplayed(CreatePromotions.LBL_BASIC_INFORMATION);
			dropDown.selectItem(CreatePromotions.DPD_PROMO_TYPE, promotionType, Constants.TEXT);
			textBox.enterText(CreatePromotions.TXT_PROMO_NAME, promotionName);
			textBox.enterText(CreatePromotions.TXT_DISPLAY_NAME, promotionName);
			foundation.click(CreatePromotions.BTN_NEXT);

			// Filter Page
			foundation.threadWait(Constants.ONE_SECOND);
			textBox.enterText(CreatePromotions.DPD_ORG,
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			textBox.enterText(CreatePromotions.DPD_ORG, Keys.ENTER);
			dropDown.selectItem(CreatePromotions.DPD_LOCATION, locationName, Constants.TEXT);
			foundation.waitforElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);

			// Detail page
			dropDown.selectItem(CreatePromotions.DPD_DISCOUNT_BY, requiredData.get(1), Constants.TEXT);
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
			dropDown.selectItem(CreatePromotions.DPD_DISCOUNT_BY, requiredData.get(0), Constants.TEXT);
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
			String bundleOption = dropDown.getSelectedItem(CreatePromotions.DPD_DISCOUNT_BY);
			CustomisedAssert.assertEquals(bundleOption, requiredData.get(0));
			String itemValue = dropDown.getSelectedItem(EditPromotion.DPD_ITEM);
			CustomisedAssert.assertEquals(itemValue, actualData.get(0));
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_OK);

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
			textBox.enterText(CreatePromotions.TXT_ITEMS, requiredData.get(1));
			foundation.threadWait(Constants.ONE_SECOND);
			textBox.enterText(CreatePromotions.TXT_ITEMS, Keys.ENTER);
			dropDown.selectItem(CreatePromotions.DPD_DISCOUNT_TYPE, requiredData.get(2), Constants.TEXT);
			textBox.enterText(CreatePromotions.TXT_DISCOUNT_PERCENTAGE, requiredData.get(3));
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
			List<String> popupFieldArray = createPromotions.getPopUpData();
			String currentDate = dateAndTime.getDateAndTime(Constants.REGEX_MMDDYY, Constants.TIME_ZONE_INDIA);
			CustomisedAssert.assertTrue(popupFieldArray.get(0).contains(promotionType));
			CustomisedAssert.assertTrue(popupFieldArray.get(1).contains(promotionName));
			CustomisedAssert.assertTrue(popupFieldArray.get(2).contains(displayName));
			CustomisedAssert.assertEquals(popupFieldArray.get(3), actualData.get(2));
			CustomisedAssert.assertEquals(popupFieldArray.get(4), actualData.get(3));
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
			CustomisedAssert
					.assertTrue(dropDown.getSelectedItem(CreatePromotions.DPD_PROMO_TYPE).equals(promotionType));
			CustomisedAssert
					.assertTrue(textBox.getTextFromInput(CreatePromotions.TXT_PROMO_NAME).equals(promotionName));
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.threadWait(Constants.THREE_SECOND);
			CustomisedAssert.assertTrue(
					dropDown.getSelectedItem(CreatePromotions.DPD_DESELECT_ORGANIZATION).equals(organization));
			CustomisedAssert
					.assertTrue(dropDown.getSelectedItem(CreatePromotions.DPD_DESELECT_LOCATION).equals(locationName));
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.threadWait(Constants.ONE_SECOND);
			foundation.click(EditPromotion.BTN_UPDATE);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.MEDIUM_TIME);
			foundation.click(CreatePromotions.BTN_OK);

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
			textBox.enterText(CreatePromotions.TXT_CATEGORYS, requiredData.get(1));
			foundation.threadWait(Constants.ONE_SECOND);
			textBox.enterText(CreatePromotions.TXT_CATEGORYS, Keys.ENTER);
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
			List<String> popupFieldArray = createPromotions.getPopUpData();
			String currentDate = dateAndTime.getDateAndTime(Constants.REGEX_MMDDYY, Constants.TIME_ZONE_INDIA);
			CustomisedAssert.assertTrue(popupFieldArray.get(0).contains(promotionType));
			CustomisedAssert.assertTrue(popupFieldArray.get(1).contains(promotionName));
			CustomisedAssert.assertTrue(popupFieldArray.get(2).contains(displayName));
			CustomisedAssert.assertEquals(popupFieldArray.get(3), actualData.get(2));
			CustomisedAssert.assertEquals(popupFieldArray.get(4), actualData.get(3));
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
			CustomisedAssert
					.assertTrue(dropDown.getSelectedItem(CreatePromotions.DPD_PROMO_TYPE).equals(promotionType));
			CustomisedAssert
					.assertTrue(textBox.getTextFromInput(CreatePromotions.TXT_PROMO_NAME).equals(promotionName));
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.waitforElement(CreatePromotions.DPD_ORGANIZATION, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(
					dropDown.getSelectedItem(CreatePromotions.DPD_DESELECT_ORGANIZATION).equals(organization));
			CustomisedAssert
					.assertTrue(dropDown.getSelectedItem(CreatePromotions.DPD_DESELECT_LOCATION).equals(locationName));
			foundation.threadWait(Constants.TWO_SECOND);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.threadWait(Constants.TWO_SECOND);
			foundation.click(EditPromotion.BTN_UPDATE);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_OK);
			foundation.threadWait(Constants.TWO_SECOND);
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
			dropDown.selectItem(CreatePromotions.DPD_PROMO_TYPE, promotionType, Constants.TEXT);
			textBox.enterText(CreatePromotions.TXT_PROMO_NAME, promotionName);
			foundation.click(CreatePromotions.BTN_NEXT);

			foundation.threadWait(Constants.ONE_SECOND);

			// validate UI of enter promotion filter page
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.DPD_ORG));
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.DPD_LOCATION));
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.BTN_NEXT));
			CustomisedAssert.assertEquals(foundation.getText(CreatePromotions.LBL_FILTER), requiredData.get(7));
			CustomisedAssert.assertEquals(foundation.getText(CreatePromotions.LBL_SELECT_CRITERIA),
					requiredData.get(8));

			// choose promotion filter
			dropDown.selectItem(CreatePromotions.DPD_ORG, orgName, Constants.TEXT);
			foundation.click(CreatePromotions.BTN_ORG_RIGHT);
			foundation.threadWait(Constants.TWO_SECOND);
			foundation.click(CreatePromotions.SELECT_ALL_LOCATION);
			foundation.waitforElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.threadWait(Constants.ONE_SECOND);

			// validate UI of enter promotion filter page
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.MULTI_SELECT_TENDER_TYPES));
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.DPD_DISCOUNT_TYPE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.DPD_APPLY_DISCOUNT_TO));
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.TXT_TRANSACTION_MIN));
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.DPD_DISCOUNT_TIME));
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.BTN_CREATE));
			CustomisedAssert.assertEquals(foundation.getText(CreatePromotions.LBL_DETAILS), requiredData.get(9));
			CustomisedAssert.assertEquals(foundation.getText(CreatePromotions.LBL_SET_PROMO_DETAILS),
					requiredData.get(10));

			dropDown.selectItem(CreatePromotions.MULTI_SELECT_TENDER_TYPES, requiredData.get(11), Constants.TEXT);
			foundation.threadWait(Constants.TWO_SECOND);
			foundation.click(CreatePromotions.BTN_NEXT);

			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_OK);

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
			foundation.threadWait(Constants.TWO_SECOND);
			dropDown.selectItem(CreatePromotions.DPD_ORG, orgName, Constants.TEXT);
			foundation.click(CreatePromotions.BTN_ORG_RIGHT);
			foundation.threadWait(Constants.TWO_SECOND);
			foundation.click(CreatePromotions.SELECT_ALL_LOCATION);
			foundation.waitforElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.threadWait(Constants.TWO_SECOND);

			List<String> requiredData = Arrays
					.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			dropDown.selectItem(CreatePromotions.MULTI_SELECT_TENDER_TYPES, requiredData.get(0), Constants.TEXT);
			dropDown.selectItem(CreatePromotions.DPD_DISCOUNT_TYPE, requiredData.get(1), Constants.TEXT);
			dropDown.selectItem(CreatePromotions.DPD_APPLY_DISCOUNT_TO, requiredData.get(2), Constants.TEXT);
			textBox.enterText(CreatePromotions.TXT_AMOUNT, requiredData.get(3));
			textBox.enterText(CreatePromotions.TXT_TRANSACTION_MIN, requiredData.get(4));
			dropDown.selectItem(CreatePromotions.DPD_DISCOUNT_TIME, requiredData.get(5), Constants.TEXT);

			foundation.threadWait(Constants.TWO_SECOND);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.MEDIUM_TIME);

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

			createPromotions.newPromotionList(promotionType, promotionName, orgName, locationName);

			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.waitforElement(CreatePromotions.MULTI_SELECT_TENDER_TYPES, Constants.SHORT_TIME);
			dropDown.selectItem(CreatePromotions.MULTI_SELECT_TENDER_TYPES, requiredData, Constants.TEXT);

			foundation.threadWait(Constants.TWO_SECOND);
			foundation.click(CreatePromotions.BTN_NEXT);

			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_OK);
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

			foundation.click(CreatePromotions.SEARCH_CATEGORY);
			textBox.enterText(CreatePromotions.SEARCH_CATEGORY, category.get(1));
			foundation.threadWait(Constants.TWO_SECOND);
			textBox.enterText(CreatePromotions.SEARCH_CATEGORY, Keys.ENTER);

			String categorySelected = dropDown.getSelectedItem(CreatePromotions.DPD_CATEGORY);
			CustomisedAssert.assertTrue(categorySelected.equals(category.get(1)));

			foundation.threadWait(Constants.TWO_SECOND);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_OK);
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
			foundation.threadWait(Constants.TWO_SECOND);
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
			createPromotions.newPromotionList(promotionType, promotionName, orgName, locationName);
			foundation.click(CreatePromotions.BTN_NEXT);

			// select promotion details
			dropDown.selectItem(CreatePromotions.MULTI_SELECT_TENDER_TYPES, requiredData.get(0), Constants.TEXT);
			dropDown.selectItem(CreatePromotions.DPD_DISCOUNT_TYPE, requiredData.get(1), Constants.TEXT);
			dropDown.selectItem(CreatePromotions.DPD_APPLY_DISCOUNT_TO, requiredData.get(2), Constants.TEXT);
			textBox.enterText(CreatePromotions.TXT_AMOUNT, requiredData.get(3));
			textBox.enterText(CreatePromotions.TXT_TRANSACTION_MIN, requiredData.get(4));
			dropDown.selectItem(CreatePromotions.DPD_DISCOUNT_TIME, requiredData.get(5), Constants.TEXT);
			createPromotions.recurringDay();
			foundation.threadWait(Constants.TWO_SECOND);
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
			foundation.isDisplayed(CreatePromotions.LBL_CREATE_PROMOTION);
			dropDown.selectItem(CreatePromotions.DPD_PROMO_TYPE, promotionType, Constants.TEXT);
			foundation.threadWait(Constants.TWO_SECOND);
			textBox.enterText(CreatePromotions.TXT_PROMO_NAME, promotionName);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.waitforElement(CreatePromotions.DPD_ORG, Constants.SHORT_TIME);
			dropDown.selectItem(CreatePromotions.DPD_ORG, organization, Constants.TEXT);
			foundation.click(CreatePromotions.BTN_ORG_RIGHT);
			dropDown.selectItem(CreatePromotions.DPD_LOC, locationName.get(0), Constants.TEXT);
			foundation.click(CreatePromotions.BTN_LOC_RIGHT);
			foundation.threadWait(Constants.TWO_SECOND);
			foundation.waitforClikableElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);
			dropDown.selectItem(CreatePromotions.MULTI_SELECT_TENDER_TYPES, requiredData.get(0), Constants.TEXT);
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
			CustomisedAssert.assertTrue(checkBox.isChecked(EditPromotion.CHK_ACTIVE));
			foundation.waitforClikableElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.threadWait(Constants.TWO_SECOND);
			CustomisedAssert.assertTrue(foundation.getText(CreatePromotions.LBL_PAGE_TITLE)
					.equals(rstLocationData.get(CNLocation.PROMOTION_TYPE)));
			CustomisedAssert.assertTrue(dropDown.getSelectedItem(CreatePromotions.DPD_DESELECT_ORGANIZATION)
					.equals(propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE)));
			CustomisedAssert.assertTrue(
					dropDown.getSelectedItem(CreatePromotions.DPD_DESELECT_LOCATION).equals(locationName.get(0)));
			// foundation.click(EditPromotion.ICON_CLR_LOCATION);
			dropDown.selectItem(CreatePromotions.DPD_DESELECT_LOCATION, locationName.get(0), Constants.TEXT);
			foundation.click(CreatePromotions.BTN_LOC_LEFT);
			dropDown.selectItem(CreatePromotions.DPD_LOCATION, locationName.get(1), Constants.TEXT);
			foundation.click(CreatePromotions.BTN_LOC_RIGHT);
			foundation.threadWait(Constants.TWO_SECOND);
			foundation.waitforClikableElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);

			// select promotion details
			dropDown.selectItem(CreatePromotions.MULTI_SELECT_TENDER_TYPES, requiredData.get(0), Constants.TEXT);
			dropDown.selectItem(CreatePromotions.DPD_DISCOUNT_TYPE, requiredData.get(1), Constants.TEXT);
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
			CustomisedAssert.assertEquals(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE), orgName);
			foundation.click(PromotionList.LINK_EXPAND);
			foundation.waitforElement(PromotionList.LBL_LOCATION_NAME, Constants.SHORT_TIME);
			String locName = foundation.getText(PromotionList.LBL_LOCATION_NAME);
			CustomisedAssert.assertEquals(locationName.get(1), locName);
			foundation.threadWait(Constants.ONE_SECOND);

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
			foundation.threadWait(Constants.TWO_SECOND);
			foundation.click(UserList.BTN_UPDATE_USER);
			foundation.waitforElement(UserList.LBL_USER_LIST, Constants.SHORT_TIME);
			foundation.threadWait(Constants.TWO_SECOND);
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
			CustomisedAssert.assertEquals(popupFieldArray.get(4), actualData.get(4));
			CustomisedAssert.assertEquals(popupFieldArray.get(5), actualData.get(5));
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
			textBox.enterText(CreatePromotions.TXT_ITEM, requiredData.get(2));
			foundation.threadWait(Constants.ONE_SECOND);
			textBox.enterText(CreatePromotions.TXT_ITEM, Keys.ENTER);

			foundation.threadWait(Constants.TWO_SECOND);
			String actualValue = dropDown.getSelectedItem(CreatePromotions.DPD_ITEM_SELECT);
			CustomisedAssert.assertEquals(actualValue, requiredData.get(2));

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
			CustomisedAssert.assertEquals(popupFieldArray.get(3), actualData.get(3));
			CustomisedAssert.assertEquals(popupFieldArray.get(4), actualData.get(4));
			CustomisedAssert.assertEquals(popupFieldArray.get(5), actualData.get(5));
			CustomisedAssert.assertEquals(popupFieldArray.get(6), actualData.get(6));
			CustomisedAssert.assertTrue(popupFieldArray.get(7).contains(currentDate));
			CustomisedAssert.assertTrue(popupFieldArray.get(8).contains(currentDate));

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
			foundation.waitforElement(CreatePromotions.DPD_ORG, Constants.SHORT_TIME);
			dropDown.selectItem(CreatePromotions.DPD_ORG, requiredData.get(1), Constants.TEXT);
			foundation.click(CreatePromotions.BTN_ORG_RIGHT);
			foundation.threadWait(Constants.TWO_SECOND);
			CustomisedAssert.assertTrue(dropDown.verifyItemNotPresent(CreatePromotions.DPD_LOC, locationName));

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
			CustomisedAssert.assertTrue(checkBox.isChecked(EditPromotion.CHK_ACTIVE));
			foundation.waitforElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);
			CustomisedAssert.assertEquals(requiredData.get(0),
					dropDown.getSelectedItem(CreatePromotions.DPD_DESELECT_ORGANIZATION));
			dropDown.selectItem(CreatePromotions.DPD_DESELECT_LOCATION, locationName.get(0), Constants.TEXT);
			foundation.click(CreatePromotions.BTN_LOC_LEFT);
			foundation.threadWait(Constants.TWO_SECOND);
			dropDown.selectItem(CreatePromotions.DPD_LOCATION, locationName.get(1), Constants.TEXT);
			foundation.click(CreatePromotions.BTN_LOC_RIGHT);
			foundation.threadWait(Constants.TWO_SECOND);
			foundation.click(EditPromotion.BTN_UPDATE);
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
			dropDown.selectItem(PromotionList.DRP_STATUS, statusType, Constants.TEXT);
			foundation.click(PromotionList.BTN_SEARCH);
			CustomisedAssert.assertTrue(foundation.getText(PromotionList.TBL_COLUMN_NAME).equals(promotionName));
			promotionList.clickSelectedRow(gridName, promotionName);
			foundation.waitforElement(EditPromotion.BTN_END_PROMO, Constants.SHORT_TIME);
			foundation.waitforElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);
			CustomisedAssert.assertEquals(requiredData.get(0),
					dropDown.getSelectedItem(CreatePromotions.DPD_DESELECT_ORGANIZATION));
			foundation.threadWait(Constants.TWO_SECOND);
			foundation.click(EditPromotion.BTN_UPDATE);
			foundation.threadWait(Constants.TWO_SECOND);
			dropDown.selectItem(CreatePromotions.DPD_DISCOUNT_BY, requiredData.get(5), Constants.TEXT);
			textBox.enterText(CreatePromotions.TXT_ITEM, requiredData.get(6));
			foundation.threadWait(Constants.ONE_SECOND);
			textBox.enterText(CreatePromotions.TXT_ITEM, Keys.ENTER);
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
			foundation.threadWait(Constants.TWO_SECOND);
			foundation.click(EditPromotion.BTN_NEXT);
			foundation.waitforElement(EditPromotion.DPD_ITEM, Constants.SHORT_TIME);
			String actualData = dropDown.getSelectedItem(EditPromotion.DPD_ITEM);
			CustomisedAssert.assertEquals(actualData, requiredData.get(6));
			// end promotion
			foundation.click(EditPromotion.BTN_CANCEL);
			foundation.threadWait(Constants.TWO_SECOND);
			foundation.click(EditPromotion.BTN_CANCEL);
			foundation.waitforElement(EditPromotion.BTN_END_PROMO, Constants.SHORT_TIME);
			foundation.click(EditPromotion.BTN_END_PROMO);
			foundation.click(EditPromotion.BTN_CONTINUE);

		} catch (Exception exc) {
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
			textBox.enterText(CreatePromotions.TXT_ITEM, requiredData.get(2));
			foundation.threadWait(Constants.ONE_SECOND);
			textBox.enterText(CreatePromotions.TXT_ITEM, Keys.ENTER);
			foundation.threadWait(Constants.TWO_SECOND);
			String actualValue = dropDown.getSelectedItem(CreatePromotions.DPD_ITEM_SELECT);
			CustomisedAssert.assertEquals(actualValue, requiredData.get(2));
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
			CustomisedAssert.assertEquals(requiredData.get(0),
					dropDown.getSelectedItem(CreatePromotions.DPD_DESELECT_ORGANIZATION));
			foundation.threadWait(Constants.TWO_SECOND);
			foundation.click(EditPromotion.BTN_UPDATE);
			foundation.threadWait(Constants.TWO_SECOND);
			dropDown.selectItem(CreatePromotions.DPD_DISCOUNT_BY, requiredData.get(3), Constants.TEXT);
			textBox.enterText(CreatePromotions.SEARCH_CATEGORY, requiredData.get(4));
			foundation.threadWait(Constants.ONE_SECOND);
			textBox.enterText(CreatePromotions.SEARCH_CATEGORY, Keys.ENTER);
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
			foundation.threadWait(Constants.TWO_SECOND);
			foundation.click(EditPromotion.BTN_NEXT);
			foundation.waitforElement(EditPromotion.DPD_CATEGORY, Constants.SHORT_TIME);
			String actualData = dropDown.getSelectedItem(EditPromotion.DPD_CATEGORY);
			CustomisedAssert.assertEquals(actualData, requiredData.get(4));
			// end promotion
			foundation.click(EditPromotion.BTN_CANCEL);
			foundation.threadWait(Constants.TWO_SECOND);
			foundation.click(EditPromotion.BTN_CANCEL);
			foundation.waitforElement(EditPromotion.BTN_END_PROMO, Constants.SHORT_TIME);
			foundation.click(EditPromotion.BTN_END_PROMO);
			foundation.click(EditPromotion.BTN_CONTINUE);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
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

			// Filter Page

			String orgExistValue = dropDown.getSelectedItem(CreatePromotions.DPD_DESELECT_ORGANIZATION);
			CustomisedAssert.assertEquals(orgExistValue, org.get(0));
			String locExistValue = dropDown.getSelectedItem(CreatePromotions.DPD_DESELECT_LOCATION);
			CustomisedAssert.assertEquals(locExistValue, location.get(0));
			foundation.threadWait(Constants.ONE_SECOND);
			dropDown.selectItem(CreatePromotions.DPD_DESELECT_ORGANIZATION, org.get(0), Constants.TEXT);
			foundation.click(CreatePromotions.BTN_ORG_LEFT);
			foundation.threadWait(Constants.TWO_SECOND);
			dropDown.deSelectItem(CreatePromotions.DPD_ORG, org.get(0), Constants.TEXT);
			foundation.threadWait(Constants.ONE_SECOND);
			dropDown.selectItem(CreatePromotions.DPD_ORG, org.get(1), Constants.TEXT);
			foundation.click(CreatePromotions.BTN_ORG_RIGHT);
			dropDown.selectItem(CreatePromotions.DPD_LOC, location.get(1), Constants.TEXT);
			foundation.click(CreatePromotions.BTN_LOC_RIGHT);

			foundation.waitforElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);

			// Details page
			foundation.threadWait(Constants.TWO_SECOND);
			List<String> requiredData = Arrays
					.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			dropDown.deSelectItem(CreatePromotions.DPD_ITEM_SELECT, requiredData.get(0), Constants.TEXT);
			textBox.enterText(CreatePromotions.TXT_ITEM, requiredData.get(1));
			foundation.threadWait(Constants.ONE_SECOND);
			textBox.enterText(CreatePromotions.TXT_ITEM, Keys.ENTER);
			textBox.enterText(CreatePromotions.TXT_AMOUNT, requiredData.get(2));
			textBox.enterText(CreatePromotions.TXT_TRANSACTION_MIN, requiredData.get(3));
			foundation.click(CreatePromotions.BTN_CREATE);

			foundation.waitforElement(EditPromotion.LBL_PROMPT_TITLE, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.getText(EditPromotion.LBL_PROMPT_TITLE)
					.contains(rstLocationData.get(CNLocation.POPUP_NAME)));
			foundation.click(EditPromotion.BTN_CONTINUE);
			foundation.click(EditPromotion.BTN_SAVE);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_OK);
			foundation.waitforElement(PromotionList.PAGE_TITLE, Constants.MEDIUM_TIME);

			promotionList.searchPromotion(promotionName, statusType);
			foundation.click(PromotionList.TBL_COLUMN_NAME);
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
			dropDown.selectItem(CreatePromotions.DPD_DESELECT_ORGANIZATION, org.get(1), Constants.TEXT);
			foundation.click(CreatePromotions.BTN_ORG_LEFT);
			foundation.threadWait(Constants.TWO_SECOND);
			dropDown.deSelectItem(CreatePromotions.DPD_ORG, org.get(1), Constants.TEXT);
			foundation.threadWait(Constants.ONE_SECOND);
			dropDown.selectItem(CreatePromotions.DPD_ORG, org.get(0), Constants.TEXT);
			foundation.click(CreatePromotions.BTN_ORG_RIGHT);
			foundation.threadWait(Constants.TWO_SECOND);
			dropDown.selectItem(CreatePromotions.DPD_LOC, location.get(0), Constants.TEXT);
			foundation.click(CreatePromotions.BTN_LOC_RIGHT);
			foundation.waitforElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);

			// Details page
			List<String> actualData = Arrays
					.asList(rstLocationData.get(CNLocation.ACTUAL_DATA).split(Constants.DELIMITER_TILD));
			dropDown.deSelectItem(CreatePromotions.DPD_ITEM_SELECT, actualData.get(0), Constants.TEXT);
			textBox.enterText(CreatePromotions.TXT_ITEM, actualData.get(1));
			foundation.threadWait(Constants.ONE_SECOND);
			textBox.enterText(CreatePromotions.TXT_ITEM, Keys.ENTER);
			textBox.enterText(CreatePromotions.TXT_AMOUNT, actualData.get(2));
			textBox.enterText(CreatePromotions.TXT_TRANSACTION_MIN, actualData.get(3));
			foundation.click(CreatePromotions.BTN_CREATE);

			foundation.waitforElement(EditPromotion.LBL_PROMPT_TITLE, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.getText(EditPromotion.LBL_PROMPT_TITLE)
					.contains(rstLocationData.get(CNLocation.POPUP_NAME)));
			foundation.click(EditPromotion.BTN_CONTINUE);
			foundation.click(EditPromotion.BTN_SAVE);
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
	public void verifyBundlePromtionsAddGroupOptionInOperator() {
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
			String color = foundation.getTextColor(CreatePromotions.FILTER_PAGE);
			CustomisedAssert.assertEquals(color, rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));
			createPromotions.selectOrgLoc(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE),
					rstLocationData.get(CNLocation.LOCATION_NAME));

			// Select Bundle Group in Details Page
			color = foundation.getTextColor(CreatePromotions.DETAILS_PAGE);
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

			// Cancelling the Promotion
			createPromotions.cancellingPromotion();

			// Navigating to Location
			navigationBar.navigateToMenuItem(menu.get(1));
			foundation.waitforElementToBeVisible(LocationList.LBL_LOCATION_LIST, 5);
			login.logout();
			browser.close();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "176281 - verify the view for the build bundle Groups")
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
		List<String> coordinates = Arrays
				.asList(rstLocationData.get(CNLocation.ACTUAL_DATA).split(Constants.DELIMITER_TILD));
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
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
			String color = foundation.getTextColor(CreatePromotions.FILTER_PAGE);
			CustomisedAssert.assertEquals(color, rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));
			createPromotions.selectOrgLoc(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE),
					rstLocationData.get(CNLocation.LOCATION_NAME));

			// Select Bundle Group in Details Page
			color = foundation.getTextColor(CreatePromotions.DETAILS_PAGE);
			CustomisedAssert.assertEquals(color, rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.LBL_BUILD_BUNDLE));
			createPromotions.verifyBundleOption(requiredData);
			dropDown.selectItem(CreatePromotions.DPD_DISCOUNT_BY, requiredData.get(3), Constants.TEXT);
			foundation.waitforElementToBeVisible(CreatePromotions.BTN_ADD_GROUP, 5);
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.BTN_ADD_GROUP));
			color = foundation.getTextColor(CreatePromotions.BTN_ADD_GROUP);
			CustomisedAssert.assertEquals(color, rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));

			// Creating the Group and validating the Icons
			createPromotions.creatingBundleGroup(promoName.get(1) + strings.getRandomCharacter(), productName.get(0));
			createPromotions.creatingBundleGroup(
					promoName.get(1) + strings.getRandomCharacter() + strings.getRandomCharacter(), productName.get(1));
			Point coordinatesAxis = foundation.getCoordinates(CreatePromotions.BTN_ADD_GROUP);
			CustomisedAssert.assertEquals(String.valueOf(coordinatesAxis.getX()), coordinates.get(0));
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

			// Deleting the Bundle Group and validating the Bundle Option
			createPromotions.deleteBundleGroup();
			createPromotions.deleteBundleGroup();
			foundation.scrollIntoViewElement(CreatePromotions.BTN_ADD_GROUP);
			value = foundation.getAttribute(CreatePromotions.BUNDLE_OPTION_ITEM, requiredData.get(4));
			CustomisedAssert.assertTrue(value == null);
			value = foundation.getAttribute(CreatePromotions.BUNDLE_OPTION_CATEGORY, requiredData.get(4));
			CustomisedAssert.assertTrue(value == null);

			// Cancelling the Promotion
			createPromotions.cancellingPromotion();

			// Navigating to Location
			navigationBar.navigateToMenuItem(menu.get(1));
			foundation.waitforElementToBeVisible(LocationList.LBL_LOCATION_LIST, 5);
			login.logout();
			browser.close();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
}
