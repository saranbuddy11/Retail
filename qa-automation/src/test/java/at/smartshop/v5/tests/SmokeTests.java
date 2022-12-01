package at.smartshop.v5.tests;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import at.framework.generic.CustomisedAssert;
import org.testng.annotations.Test;

import at.framework.database.mssql.Queries;
import at.framework.database.mssql.ResultSets;
import at.framework.generic.DateAndTime;
import at.framework.generic.Numbers;
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
import at.smartshop.database.columns.CNV5Device;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.pages.CreatePromotions;
import at.smartshop.pages.EditPromotion;
import at.smartshop.pages.GlobalProduct;
import at.smartshop.pages.LocationList;
import at.smartshop.pages.LocationSummary;
import at.smartshop.pages.Login;
import at.smartshop.pages.NavigationBar;
import at.smartshop.pages.OrgList;
import at.smartshop.pages.OrgSummary;
import at.smartshop.pages.ProductSummary;
import at.smartshop.pages.PromotionList;
import at.smartshop.pages.SelfService;
import at.smartshop.pages.UserList;
import at.smartshop.pages.UserRoles;
import at.smartshop.tests.TestInfra;
import at.smartshop.v5.pages.LandingPage;
import at.smartshop.v5.pages.Order;
import at.smartshop.v5.pages.ProductSearch;

public class SmokeTests extends TestInfra {
	private ResultSets dataBase = new ResultSets();
	private NavigationBar navigationBar = new NavigationBar();
	private TextBox textBox = new TextBox();
	private Foundation foundation = new Foundation();
	private LocationList locationList = new LocationList();
	private CheckBox checkBox = new CheckBox();
//	private Order order = new Order();
	private Dropdown dropDown = new Dropdown();
	private LocationSummary locationSummary = new LocationSummary();
	private LandingPage landingPage = new LandingPage();
	private UserRoles userRoles = new UserRoles();
	private Dropdown dropdown = new Dropdown();
	private UserList userList = new UserList();
	private Strings strings = new Strings();
	private CreatePromotions createPromotions = new CreatePromotions();
	private PromotionList promotionList = new PromotionList();
	private DateAndTime dateAndTime = new DateAndTime();
	private Numbers numbers = new Numbers();
	private GlobalProduct globalProduct = new GlobalProduct();
	private Table table = new Table();
	private SelfService selfService = new SelfService();

	private Map<String, String> rstV5DeviceData;
	private Map<String, String> rstNavigationMenuData;
	private Map<String, String> rstLocationListData;
	private Map<String, String> rstLocationSummaryData;
	private Map<String, String> rstLocationData;

	@Test(description = "166994- Verify Save Button on Location Summary page as Super")
	public void VerifySaveButton() {
		final String CASE_NUM = "166994";
		// Reading test data from DataBase
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
		rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		String menuItem = rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM);

		List<String> marketCard = Arrays
				.asList(rstLocationSummaryData.get(CNLocationSummary.COLUMN_NAME).split(Constants.DELIMITER_TILD));
		String ValidateHeading = marketCard.get(0);
		String fixedMarket = marketCard.get(1);
		String NoneMarket = marketCard.get(2);
		String locationName = rstLocationData.get(CNLocation.LOCATION_NAME);
		try {
			// launching browser and selecting org
			locationSummary.launchingBrowserAndSelectingOrg();
			navigationBar.navigateToMenuItem(menuItem);

			// Selecting location
			locationSummary.selectingMarketCard(locationName, ValidateHeading, fixedMarket);
			foundation.refreshPage();
			foundation.threadWait(Constants.TWO_SECOND);
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// Resetting test data
			locationSummary.selectingMarketCard(locationName, ValidateHeading, NoneMarket);
		}
	}

	@Test(description = "166995- Verify Update Prices Button on Location Summary page as Superr")
	public void VerifyUpdatePricesButton() {
		final String CASE_NUM = "166995";
		// Reading test data from DataBase
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
		rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);

		List<String> requiredData = Arrays
				.asList(rstLocationSummaryData.get(CNLocationSummary.COLUMN_NAME).split(Constants.DELIMITER_TILD));
		String validateHeading = requiredData.get(0);
		String tab = requiredData.get(1);
		String productName = requiredData.get(2);
		String scanCode = requiredData.get(3);
		String productPrice = requiredData.get(4);
		String resettedPrice = requiredData.get(5);
		String locationName = rstLocationData.get(CNLocation.LOCATION_NAME);
		try {
			// launching browser and selecting org
			locationSummary.launchingBrowserAndSelectingOrg();

			// Selecting location
			locationList.selectLocationName(locationName);
			foundation.waitforElement(LocationSummary.VALIDATE_HEADING, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.getText(LocationSummary.VALIDATE_HEADING).equals(validateHeading));
			locationSummary.selectingProduct(tab, productName, scanCode, productPrice);
			foundation.refreshPage();
			foundation.threadWait(Constants.TWO_SECOND);
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// Resetting test data
			locationSummary.selectingProduct(tab, productName, scanCode, resettedPrice);
			browser.close();
		}
	}

	@Test(description = "166996- Verify Update Prices & Full Sync Button on Location Summary page as Super")
	public void VerifyUpdatePricesAndFullSyncButton() {
		final String CASE_NUM = "166996";
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		List<String> requiredData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		String productName = requiredData.get(1);
		String scanCode = requiredData.get(0);
		String productPrice = requiredData.get(2);
		String productHeader = requiredData.get(4);
		String validateproductPrice = requiredData.get(6);
		String resettedPrice = requiredData.get(7);
		String tab = requiredData.get(8);
		String menuItem = rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM);
		try {
			// launching browser and selecting org
			locationSummary.launchingBrowserAndSelectingOrg();

			// navigate to location summary and update price and sync
			navigationBar.navigateToMenuItem(menuItem);
			textBox.enterText(LocationList.TXT_FILTER,
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));
			locationList.selectLocationName(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));
			locationSummary.selectingProduct(tab, productName, scanCode, productPrice);
			foundation.click(LocationSummary.BTN_FULL_SYNC);
			foundation.waitforElement(LocationSummary.LBL_SPINNER_MSG, Constants.SHORT_TIME);

			// launching v5 Device
			List<String> language = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));

			// launch v5 application
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));

			landingPage.changeLanguage(language.get(2), language.get(0), language.get(3));
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(productName);
			foundation.click(ProductSearch.BTN_PRODUCT);
			CustomisedAssert.assertEquals(foundation.getText(Order.TXT_HEADER), productHeader);
			CustomisedAssert.assertEquals(foundation.getText(Order.LBL_PRODUCT_PRICE), validateproductPrice);
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// reset data
			browser.close();
			browser.launch(Constants.LOCAL, Constants.CHROME);

			// launching browser and selecting org
			locationSummary.launchingBrowserAndSelectingOrg();
			navigationBar.navigateToMenuItem(menuItem);
			textBox.enterText(LocationList.TXT_FILTER,
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));
			locationList.selectLocationName(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));
			locationSummary.selectingProduct(tab, productName, scanCode, resettedPrice);
			foundation.click(LocationSummary.BTN_FULL_SYNC);
			foundation.waitforElement(LocationSummary.LBL_SPINNER_MSG, Constants.SHORT_TIME);
		}
	}

	@Test(description = "166997- Verify the Add/Edit/Remove Products on Location Summary page as Super")
	public void VerifyAddEditAndRemoveProduct() {
		final String CASE_NUM = "166997";
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		List<String> requiredData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		String tab = requiredData.get(0);
		String scanCode = requiredData.get(1);
		String productName = requiredData.get(1);
		String updatedProductName = requiredData.get(3);
		String productHeader = requiredData.get(4);
		String productPrice = requiredData.get(5);
		String menuItem = rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM);
		try {
			// launching browser and selecting org
			locationSummary.launchingBrowserAndSelectingOrg();

			// navigate to location summary and adding Product
			navigationBar.navigateToMenuItem(menuItem);
			textBox.enterText(LocationList.TXT_FILTER,
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));
			locationList.selectLocationName(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));
			locationSummary.selectTab(tab);
			foundation.threadWait(Constants.TWO_SECOND);
			locationSummary.addProduct(scanCode);
			foundation.waitforElement(LocationSummary.LBL_SPINNER_MSG, Constants.SHORT_TIME);
			foundation.refreshPage();
			locationSummary.addEditProduct(tab, scanCode, updatedProductName, menuItem);
			List<String> language = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
			locationSummary.kiosklanguageSetting(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE),
					language.get(0), language.get(1));

			// launch v5 application
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			landingPage.changeLanguage(language.get(2), language.get(0), language.get(3));
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			foundation.threadWait(Constants.THREE_SECOND);
			textBox.enterKeypadText(scanCode);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(ProductSearch.BTN_PRODUCT);
			foundation.threadWait(Constants.THREE_SECOND);
			CustomisedAssert.assertEquals(foundation.getText(Order.TXT_HEADER), productHeader);
			CustomisedAssert.assertEquals(foundation.getText(Order.TXT_PRODUCT), productName);
			CustomisedAssert.assertEquals(foundation.getText(Order.LBL_PRODUCT_PRICE), productPrice);
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// reset data
			browser.close();
			browser.launch(Constants.LOCAL, Constants.CHROME);

			// launching browser and selecting org
			locationSummary.launchingBrowserAndSelectingOrg();
			navigationBar.navigateToMenuItem(menuItem);
			textBox.enterText(LocationList.TXT_FILTER,
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));
			locationList.selectLocationName(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));
			locationSummary.addEditProduct(tab, updatedProductName, productName, menuItem);
			locationList.selectLocationName(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));
			locationSummary.selectTab(tab);
			foundation.WaitForAjax(5000);
			foundation.threadWait(Constants.TWO_SECOND);
			foundation.waitforElement(LocationSummary.TXT_PRODUCT_FILTER, Constants.SHORT_TIME);
			foundation.threadWait(Constants.EXTRA_LONG_TIME);
			textBox.enterText(LocationSummary.TXT_PRODUCT_FILTER, scanCode);
			foundation.WaitForAjax(10000);
			// Assert.assertTrue(foundation.getText(LocationSummary.PRODUCT_NAME).equals(updatedProductName));
			foundation.click(LocationSummary.PRODUCT_NAME);
			foundation.waitforElement(LocationSummary.BTN_REMOVE, Constants.SHORT_TIME);
			foundation.click(LocationSummary.BTN_REMOVE);
			foundation.click(LocationSummary.BTN_FULL_SYNC);
			foundation.threadWait(Constants.THREE_SECOND);
		}
	}

	@Test(description = "166998- Verify the Promotions tab: Create/Edit/Expire Promotions on Location Summary page as Operator")
	public void VerifyCreateEditExpireOnScreenPromotions() {
		final String CASE_NUM = "166998";
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
		List<String> requiredData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		String tab = requiredData.get(0);
//		String productName = requiredData.get(1);
		String productHeader = requiredData.get(2);
		List<String> menuItem = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		String promotionType = rstLocationData.get(CNLocation.PROMOTION_TYPE);
		String orgName = propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE);
		String promotionName = strings.getRandomCharacter();
		String gridName = rstLocationData.get(CNLocation.TAB_NAME);
		String statusType = rstLocationData.get(CNLocation.LOCATION_NAME);
		try {
			// launching browser and selecting org
			locationSummary.launchingBrowserAndSelectingOrg();
			navigationBar.navigateToMenuItem(menuItem.get(0));
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
			dropdown.selectItem(UserList.TXT_SEARCH_LOC, allOption, Constants.TEXT);
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
//			List<String> locationNames = foundation.getTextofListElement(LocationList.LINK_LOCATION_LIST);
			navigationBar.navigateToMenuItem(menuItem.get(1));
			textBox.enterText(LocationList.TXT_FILTER,
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));
			locationList.selectLocationName(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));
			locationSummary.selectTab(tab);
			foundation.threadWait(Constants.TWO_SECOND);

			// Basic Information Page
			foundation.click(LocationSummary.BTN_CREATE_PROMO);
			createPromotions.newPromotion(promotionType, promotionName, promotionName, orgName,
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));
			foundation.waitforElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);

			// Promotion Details Page
			List<String> discountType = Arrays
					.asList(rstLocationData.get(CNLocation.PRODUCT_NAME).split(Constants.DELIMITER_TILD));
			foundation.threadWait(Constants.SHORT_TIME);
			dropdown.selectItem(CreatePromotions.DPD_DISCOUNT_BY, discountType.get(0), Constants.TEXT);
//			textBox.enterText(CreatePromotions.SEARCH_CATEGORY, discountType.get(2));
//			foundation.threadWait(Constants.ONE_SECOND);
//			textBox.enterText(CreatePromotions.SEARCH_CATEGORY, Keys.ENTER);
			foundation.waitforElementToBeVisible(CreatePromotions.ADD_CATEGORY, Constants.THREE_SECOND);
			foundation.click(CreatePromotions.ADD_CATEGORY);
			textBox.enterText(CreatePromotions.CATEGORY_SEARCH_TXT, discountType.get(2));
			foundation.threadWait(Constants.SHORT_TIME);
			checkBox.check(CreatePromotions.SELECT_CAT_PRODUCT);
			foundation.waitforElementToBeVisible(CreatePromotions.BTN_CANCEL_CAT_POPUP, Constants.THREE_SECOND);
			foundation.click(CreatePromotions.BTN_CANCEL_CAT_POPUP);
			String onScreenDiscount = foundation.getAttributeValue(CreatePromotions.TXT_AMOUNT);
			foundation.threadWait(Constants.THREE_SECOND);
			foundation.click(CreatePromotions.BTN_CREATE);
			foundation.click(CreatePromotions.BTN_OK);
			foundation.threadWait(Constants.SHORT_TIME);

			// editing category field
			foundation.waitforElement(PromotionList.TXT_SEARCH_PROMONAME, Constants.EXTRA_LONG_TIME);
			textBox.enterText(PromotionList.TXT_SEARCH_PROMONAME, promotionName);
			dropdown.selectItem(PromotionList.DRP_STATUS, statusType, Constants.TEXT);
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
			foundation.threadWait(Constants.SHORT_TIME);
			dropdown.selectItem(CreatePromotions.DPD_DISCOUNT_BY, discountType.get(1), Constants.TEXT);
//			textBox.enterText(CreatePromotions.TXT_ITEM, discountType.get(3));
//			foundation.threadWait(Constants.ONE_SECOND);
//			textBox.enterText(CreatePromotions.TXT_ITEM, Keys.ENTER);
			foundation.threadWait(Constants.THREE_SECOND);
			foundation.click(CreatePromotions.ADD_ITEM);
			textBox.enterText(CreatePromotions.ITEM_SEARCH_TXT, discountType.get(3));
			foundation.threadWait(Constants.SHORT_TIME);
			checkBox.check(CreatePromotions.SELECT_ITEM_PRODUCT);
			foundation.waitforElementToBeVisible(CreatePromotions.BTN_CANCEL_ITEM_POPUP, Constants.THREE_SECOND);
			foundation.click(CreatePromotions.BTN_CANCEL_ITEM_POPUP);
			foundation.waitforElementToBeVisible(CreatePromotions.BTN_CREATE, Constants.THREE_SECOND);
			foundation.click(EditPromotion.BTN_UPDATE);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);
			foundation.waitforElement(CreatePromotions.POP_UP_MESSAGES, Constants.SHORT_TIME);
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
			foundation.threadWait(Constants.SHORT_TIME);
			CustomisedAssert.assertEquals(popupFieldArray.get(4), actualData.get(3));
			CustomisedAssert.assertEquals(popupFieldArray.get(5), actualData.get(4));
			CustomisedAssert.assertEquals(popupFieldArray.get(6), actualData.get(5));
			foundation.threadWait(Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(popupFieldArray.get(7).contains(currentDate));
			CustomisedAssert.assertTrue(popupFieldArray.get(8).contains(currentDate));
			foundation.click(CreatePromotions.BTN_OK);

			// Validating promotion is displayed
			foundation.waitforElement(PromotionList.PAGE_TITLE, Constants.SHORT_TIME);
			promotionList.verifyPromotionName(promotionName);
			login.logout();

			// launching test4 with super user for full sync
			locationSummary.launchingBrowserAndSelectingOrg();

			// launching v5 Device
			List<String> language = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
			locationSummary.kiosklanguageSetting(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE),
					language.get(0), language.get(1));

			// launch v5 application
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			landingPage.changeLanguage(language.get(2), language.get(0), language.get(3));
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(discountType.get(3));
			foundation.click(ProductSearch.BTN_PRODUCT);
			CustomisedAssert.assertEquals(foundation.getText(Order.TXT_HEADER), productHeader);
			CustomisedAssert.assertEquals(foundation.getText(Order.TXT_PRODUCT), discountType.get(3));
			CustomisedAssert.assertTrue(promotionName.equals(foundation.getText(Order.LBL_DISCOUNT_NAME)));
			List<String> discountList = foundation.getTextofListElement(Order.LBL_ORDER_DISCOUNT);
			CustomisedAssert.assertTrue(discountList.get(2).contains(onScreenDiscount));
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// reset data
			browser.close();
			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			promotionList.expirePromotion(menuItem.get(2), promotionName, statusType, gridName);
		}
	}

	@Test(description = "167054- Verify the Promotions tab: Create/Edit/Expire Promotions for Tender Type Promotion on Location Summary page as Operator")
	public void VerifyCreateEditExpireTenderPromotions() {
		final String CASE_NUM = "167054";
		browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
		login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
				propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

		// Reading test data from database
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);
		String promotionName = strings.getRandomCharacter();
		String promotionType = rstLocationData.get(CNLocation.PROMOTION_TYPE);
		String orgName = propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE);
		String gridName = rstLocationData.get(CNLocation.TAB_NAME);
		String statusType = rstLocationData.get(CNLocation.LOCATION_NAME);
		List<String> requiredData = Arrays
				.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> menuItem = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		try {
			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItem.get(0));
			textBox.enterText(LocationList.TXT_FILTER,
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));
			locationList.selectLocationName(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));
			locationSummary.selectTab(requiredData.get(0));
			foundation.threadWait(Constants.TWO_SECOND);

			// Creating New Promotion
			foundation.click(LocationSummary.BTN_CREATE_PROMO);

			// validate UI of enter promotion basics page
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.DPD_PROMO_TYPE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.TXT_PROMO_NAME));
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.BTN_NEXT));
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.BTN_CANCEL));
			CustomisedAssert.assertEquals(foundation.getText(CreatePromotions.LBL_BASICINFO), requiredData.get(1));
			CustomisedAssert.assertEquals(foundation.getText(CreatePromotions.LBL_ENTER_BASICINFO),
					requiredData.get(2));

			// provide basic info and navigate to next
			dropdown.selectItem(CreatePromotions.DPD_PROMO_TYPE, promotionType, Constants.TEXT);
			textBox.enterText(CreatePromotions.TXT_PROMO_NAME, promotionName);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.threadWait(Constants.SHORT_TIME);

			// choose promotion filter
			foundation.waitforElement(CreatePromotions.TXT_SEARCH_ORGPAGE, Constants.SHORT_TIME);
			textBox.enterText(CreatePromotions.TXT_SEARCH_ORGPAGE, orgName);
			foundation.threadWait(Constants.TWO_SECOND);
			foundation.click(CreatePromotions.CHECKBOX_ORG);
			foundation.waitforElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.waitforElement(CreatePromotions.ALL_LOCATION, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.ALL_LOCATION);
			foundation.waitforElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);

			// validate UI of enter promotion filter page
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.MULTI_SELECT_TENDER_TYPES));
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.DPD_APPLY_DISCOUNT_TO));
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.TXT_TRANSACTION_MIN));
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.DPD_DISCOUNT_TIME));
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.BTN_CREATE));
			CustomisedAssert.assertEquals(foundation.getText(CreatePromotions.LBL_DETAILS), requiredData.get(5));
			CustomisedAssert.assertEquals(foundation.getText(CreatePromotions.LBL_SET_PROMO_DETAILS),
					requiredData.get(6));
			dropdown.selectItem(CreatePromotions.MULTI_SELECT_TENDER_TYPES, requiredData.get(7), Constants.TEXT);
			foundation.threadWait(Constants.TWO_SECOND);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.MEDIUM_TIME);
			foundation.click(CreatePromotions.BTN_OK);
			foundation.threadWait(Constants.THREE_SECOND);

			// editing category field
			foundation.waitforElement(PromotionList.TXT_SEARCH_PROMONAME, Constants.EXTRA_LONG_TIME);
			textBox.enterText(PromotionList.TXT_SEARCH_PROMONAME, promotionName);
			dropdown.selectItem(PromotionList.DRP_STATUS, statusType, Constants.TEXT);
			foundation.click(PromotionList.BTN_SEARCH);
			CustomisedAssert.assertTrue(foundation.getText(PromotionList.TBL_COLUMN_NAME).equals(promotionName));
			promotionList.clickSelectedRow(gridName, promotionName);
			foundation.waitforElement(EditPromotion.BTN_END_PROMO, Constants.SHORT_TIME);
			foundation.waitforElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);
			foundation.waitforClikableElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.waitforClikableElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.waitforClikableElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.waitforClikableElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.threadWait(Constants.SHORT_TIME);
			dropdown.deSelectItem(CreatePromotions.MULTI_SELECT_TENDER_TYPES, requiredData.get(7), Constants.TEXT);
			dropdown.selectItem(CreatePromotions.MULTI_SELECT_TENDER_TYPES, requiredData.get(8), Constants.TEXT);
			foundation.click(EditPromotion.BTN_UPDATE);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.EXTRA_LONG_TIME);
			List<String> popupFieldType = foundation.getTextofListElement(CreatePromotions.POP_UP_MESSAGES);
			List<String> popupField = null;
			popupField = Arrays.asList(popupFieldType.get(0).split(Constants.DELIMITER_COMMA));
			popupField = Arrays.asList(popupField.get(0).split(Constants.NEW_LINE));
			List<String> actualData = Arrays
					.asList(rstLocationData.get(CNLocation.ACTUAL_DATA).split(Constants.DELIMITER_TILD));
			foundation.threadWait(Constants.SHORT_TIME);
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
			foundation.threadWait(Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(popupFieldArray.get(7).contains(currentDate));
			CustomisedAssert.assertTrue(popupFieldArray.get(8).contains(currentDate));
			foundation.click(CreatePromotions.BTN_OK);
			foundation.threadWait(Constants.SHORT_TIME);

			// Validating promotion is displayed
			foundation.waitforElement(PromotionList.PAGE_TITLE, Constants.SHORT_TIME);
			promotionList.verifyPromotionName(promotionName);
			login.logout();

			// Login to ADM
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			List<String> V5Data = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			String productName = V5Data.get(0);
			String productHeader = V5Data.get(1);

			// launching v5 Device
			List<String> language = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
			locationSummary.kiosklanguageSetting(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE),
					language.get(0), language.get(1));

			// launch v5 application
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			landingPage.changeLanguage(language.get(2), language.get(0), language.get(3));
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(productName);
			foundation.click(ProductSearch.BTN_PRODUCT);
			CustomisedAssert.assertEquals(foundation.getText(Order.TXT_HEADER), productHeader);
			CustomisedAssert.assertEquals(foundation.getText(Order.TXT_PRODUCT), productName);
			CustomisedAssert.assertTrue(V5Data.get(2).equals(foundation.getText(Order.TXT_TENDER_DISCOUNT)));
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// Resetting the data
			browser.close();
			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			promotionList.expirePromotion(menuItem.get(1), promotionName, statusType, gridName);
		}
	}

	@Test(description = "167053-Verify the Promotions tab: Create/Edit/Expire Promotions for Bundle Type Promotion on Location Summary page as Operator")
	public void VerifyCreateEditExpireBundlePromotions() {
		final String CASE_NUM = "167053";
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
		rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);
		String promotionType = rstLocationData.get(CNLocation.PROMOTION_TYPE);
		String gridName = rstLocationData.get(CNLocation.TAB_NAME);
		String locationName = rstLocationData.get(CNLocation.LOCATION_NAME);
		String orgName = propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE);
		String displayName = strings.getRandomCharacter();
		String statusType = rstLocationData.get(CNLocation.COLUMN_VALUE);
		List<String> requiredData = Arrays
				.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> actualData = Arrays
				.asList(rstLocationData.get(CNLocation.ACTUAL_DATA).split(Constants.DELIMITER_TILD));
		String promotionName = strings.getRandomCharacter();
		List<String> menuItem = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItem.get(0));
			textBox.enterText(LocationList.TXT_FILTER,
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));
			locationList.selectLocationName(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));
			locationSummary.selectTab(requiredData.get(0));
			foundation.threadWait(Constants.TWO_SECOND);

			// Creating New Promotion
			foundation.click(LocationSummary.BTN_CREATE_PROMO);
			createPromotions.newPromotion(promotionType, promotionName, displayName, orgName, locationName);
			foundation.waitforElementToBeVisible(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);
//			foundation.click(CreatePromotions.BTN_NEXT);
//			foundation.threadWait(Constants.SHORT_TIME);

			// Detail page
			dropdown.selectItem(CreatePromotions.DPD_DISCOUNT_BY, requiredData.get(3), Constants.TEXT);
//			textBox.enterText(CreatePromotions.SEARCH_CATEGORY, actualData.get(1));
//			foundation.threadWait(Constants.TWO_SECOND);
//			textBox.enterText(CreatePromotions.SEARCH_CATEGORY, Keys.ENTER);
			foundation.waitforElementToBeVisible(CreatePromotions.ADD_CATEGORY, Constants.THREE_SECOND);
			foundation.click(CreatePromotions.ADD_CATEGORY);
			textBox.enterText(CreatePromotions.CATEGORY_SEARCH_TXT, actualData.get(1));
			foundation.threadWait(Constants.SHORT_TIME);
			checkBox.check(CreatePromotions.SELECT_CAT_PRODUCT);
			foundation.waitforElementToBeVisible(CreatePromotions.BTN_CANCEL_CAT_POPUP, Constants.THREE_SECOND);
			foundation.click(CreatePromotions.BTN_CANCEL_CAT_POPUP);
			foundation.threadWait(Constants.THREE_SECOND);
			foundation.click(CreatePromotions.BTN_CREATE);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_OK);
			foundation.threadWait(Constants.THREE_SECOND);

			// Updating the Category to Item
			textBox.enterText(PromotionList.TXT_SEARCH_PROMONAME, promotionName);
			dropdown.selectItem(PromotionList.DRP_STATUS, statusType, Constants.TEXT);
			foundation.click(PromotionList.BTN_SEARCH);
			foundation.doubleClick(PromotionList.TBL_COLUMN_NAME);
			foundation.waitforElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.threadWait(Constants.THREE_SECOND);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.threadWait(Constants.THREE_SECOND);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.threadWait(Constants.THREE_SECOND);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.waitforElement(CreatePromotions.DPD_DISCOUNT_BY, Constants.SHORT_TIME);
			dropdown.selectItem(CreatePromotions.DPD_DISCOUNT_BY, requiredData.get(1), Constants.TEXT);
//			textBox.enterText(CreatePromotions.TXT_ITEM, actualData.get(0));
//			foundation.threadWait(Constants.ONE_SECOND);
//			textBox.enterText(CreatePromotions.TXT_ITEM, Keys.ENTER);
			foundation.threadWait(Constants.THREE_SECOND);
			foundation.click(CreatePromotions.ADD_ITEM);
			textBox.enterText(CreatePromotions.ITEM_SEARCH_TXT, actualData.get(0));
			foundation.threadWait(Constants.SHORT_TIME);
			checkBox.check(CreatePromotions.SELECT_ITEM_PRODUCT);
			foundation.waitforElementToBeVisible(CreatePromotions.BTN_CANCEL_ITEM_POPUP, Constants.THREE_SECOND);
			foundation.click(CreatePromotions.BTN_CANCEL_ITEM_POPUP);
			foundation.waitforElementToBeVisible(CreatePromotions.BTN_CREATE, Constants.THREE_SECOND);
			foundation.click(CreatePromotions.BTN_CREATE);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_OK);
			foundation.threadWait(Constants.SHORT_TIME);

			// Verify Item correctly updated in Promotion Screen
//			foundation.waitforElement(PromotionList.TXT_SEARCH_PROMONAME, Constants.EXTRA_LONG_TIME);
//			textBox.enterText(PromotionList.TXT_SEARCH_PROMONAME, promotionName);
//			dropdown.selectItem(PromotionList.DRP_STATUS, statusType, Constants.TEXT);
//			foundation.click(PromotionList.BTN_SEARCH);
//			foundation.doubleClick(PromotionList.TBL_COLUMN_NAME);
//			foundation.waitforElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);
//			foundation.click(CreatePromotions.BTN_NEXT);
//			foundation.threadWait(Constants.THREE_SECOND);
//			foundation.click(CreatePromotions.BTN_NEXT);
//			foundation.threadWait(Constants.THREE_SECOND);
//			foundation.click(CreatePromotions.BTN_NEXT);
//			foundation.threadWait(Constants.THREE_SECOND);
//			foundation.click(CreatePromotions.BTN_NEXT);
//			foundation.threadWait(Constants.SHORT_TIME);
//			String bundleOption = dropdown.getSelectedItem(CreatePromotions.DPD_DISCOUNT_BY);
//			Assert.assertEquals(bundleOption, requiredData.get(1));
//			foundation.threadWait(Constants.THREE_SECOND);
//			foundation.click(CreatePromotions.ADD_ITEM);
//			textBox.enterText(CreatePromotions.ITEM_SEARCH_TXT, actualData.get(0));
//			foundation.threadWait(Constants.SHORT_TIME);
//			String itemValue = foundation.getText(EditPromotion.SELECTED_ITEM);
//			CustomisedAssert.assertTrue(itemValue.contains(actualData.get(0)));
//			foundation.threadWait(Constants.SHORT_TIME);
//			foundation.waitforElementToBeVisible(CreatePromotions.BTN_CANCEL_ITEM_POPUP, Constants.THREE_SECOND);
//			foundation.click(CreatePromotions.BTN_CANCEL_ITEM_POPUP);
//			foundation.click(CreatePromotions.BTN_NEXT);
//			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);
//			foundation.click(CreatePromotions.BTN_OK);
			foundation.threadWait(Constants.THREE_SECOND);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.DASHBOARD_URL, FilePath.PROPERTY_CONFIG_FILE));
			textBox.enterText(LocationList.TXT_FILTER,
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));
			locationList.selectLocationName(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(LocationSummary.BTN_SYNC);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			login.logout();

			// launching test4 with super user for full sync
			locationSummary.launchingBrowserAndSelectingOrg();

			// launching v5 Device
			List<String> language = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
			locationSummary.kiosklanguageSetting(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE),
					language.get(0), language.get(1));
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			landingPage.changeLanguage(language.get(2), language.get(0), language.get(3));
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(actualData.get(0));
			foundation.click(ProductSearch.BTN_PRODUCT);
			CustomisedAssert.assertTrue(foundation.isDisplayed(Order.BTN_CANCEL_ORDER));
			CustomisedAssert.assertTrue(displayName.equals(foundation.getText(Order.LBL_PROMOTION_NAME)));
			List<String> discountList = foundation.getTextofListElement(Order.LBL_ORDER_DISCOUNT);
			String priceTotal = foundation.getText(CreatePromotions.LBL_TOTAL_PRICE);
			String bundleDiscount = foundation.getText(CreatePromotions.LBL_BUNDLE_DISCOUNT);
			CustomisedAssert.assertTrue(discountList.get(2).equals(bundleDiscount));

			// verify the display of total section
			String productPrice = foundation.getText(Order.LBL_PRODUCT_PRICE).split(Constants.DOLLAR)[1];
			String discount = foundation.getText(Order.LBL_DEPOSIT).split(Constants.DOLLAR)[1];
			Double expectedBalanceDue = Double.parseDouble(productPrice) - Double.parseDouble(discount);
			CustomisedAssert
					.assertTrue(foundation.getText(Order.LBL_BALANCE_DUE).contains(String.valueOf(expectedBalanceDue)));
			CustomisedAssert.assertTrue(foundation.getText(Order.LBL_SUB_TOTAL).contains(priceTotal));
			CustomisedAssert.assertEquals(foundation.getText(Order.LBL_DISCOUNT),
					Constants.DELIMITER_HYPHEN + bundleDiscount);
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// Expire Promotion
			browser.close();
			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			promotionList.expirePromotion(menuItem.get(1), promotionName, statusType, gridName);
		}
	}

	@Test(description = "166999- Verify the Home Commercial tab: Add/Remove commercials on Location Summary page as Super")
	public void VerifyAddRemoveHomeCommercial() {
		final String CASE_NUM = "166999";
		// Reading test data from DataBase
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		String locationName = propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1,
				FilePath.PROPERTY_CONFIG_FILE);
		try {
			String requiredData = rstV5DeviceData.get(CNV5Device.REQUIRED_DATA);
			// launching browser and selecting org
			locationSummary.launchingBrowserAndSelectingOrg();
			List<String> language = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));

			// Selecting Device's location
			locationList.selectLocationName(locationName);
			locationSummary.addHomeCommercials(rstV5DeviceData.get(CNV5Device.ACTUAL_DATA));
			locationSummary.kiosklanguageSetting(locationName, language.get(0), language.get(1));

			// launching v5 device
			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			foundation.refreshPage();
			foundation.waitforElement(landingPage.objImageDisplay(requiredData), Constants.EXTRA_LONG_TIME);
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// resetting test data
			browser.close();
			browser.launch(Constants.LOCAL, Constants.CHROME);

			// launching browser and selecting org
			locationSummary.launchingBrowserAndSelectingOrg();

			// Selecting Device's location
			locationList.selectLocationName(locationName);
			locationSummary.removeHomeCommercial(rstV5DeviceData.get(CNV5Device.ACTUAL_DATA));
		}
	}

	@Test(description = "167002- Verify the Create New products on Global Products as Super")
	public void VerifyCreateAddRemoveNewProduct() {
		final String CASE_NUM = "167002";
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
		rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		List<String> menuItem = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> requiredData = Arrays
				.asList(rstLocationSummaryData.get(CNLocationSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
//		String addProduct = rstLocationData.get(CNLocation.ACTUAL_DATA);
		String productHeader = requiredData.get(4);
		String locationName = rstLocationData.get(CNLocation.LOCATION_NAME);
		final String productName = rstLocationData.get(CNLocation.PRODUCT_NAME) + strings.getRandomCharacter();
		final String scanCode = rstLocationData.get(CNLocation.ACTUAL_DATA)
				+ strings.getRandomCharacter().toLowerCase();
		try {
			// launching browser and selecting org
			locationSummary.launchingBrowserAndSelectingOrg();
			navigationBar.navigateToMenuItem(menuItem.get(0));

			// Creating Product
			foundation.waitforElement(GlobalProduct.TXT_GLOBAL_PRODUCT, Constants.SHORT_TIME);
			CustomisedAssert
					.assertTrue(foundation.getText(GlobalProduct.TXT_GLOBAL_PRODUCT).equals(requiredData.get(1)));
			foundation.click(GlobalProduct.BTN_CREATE);
			foundation.waitforElement(GlobalProduct.TXT_PRODUCT_CREATE, Constants.SHORT_TIME);
			CustomisedAssert
					.assertTrue(foundation.getText(GlobalProduct.TXT_PRODUCT_CREATE).equals(requiredData.get(2)));
			textBox.enterText(GlobalProduct.TXT_PRODUCTNAME, productName);
			textBox.enterText(GlobalProduct.TXT_SCAN_CODE, scanCode);
			textBox.enterText(GlobalProduct.TXT_PRICE, String.valueOf(numbers.generateRandomNumber(0, 9)));
			foundation.threadWait(Constants.TWO_SECOND);
			foundation.click(GlobalProduct.BUTTON_SAVE);
			foundation.waitforElement(GlobalProduct.SELECT_LOCATION, Constants.SHORT_TIME);
			// textBox.enterText(GlobalProduct.SELECT_LOCATION, locationName);
			// foundation.threadWait(Constants.TWO_SECOND);
			// dropDown.selectItem(GlobalProduct.SELECT_LOCATION, locationName,
			// Constants.TEXT);
			// foundation.waitforClikableElement(GlobalProduct.LBL_SAVE_DONE,
			// Constants.SHORT_TIME);
			foundation.click(GlobalProduct.LBL_SAVE_DONE);

			// Editing the product
			navigationBar.navigateToMenuItem(menuItem.get(0));
			foundation.threadWait(Constants.SHORT_TIME);
			textBox.enterText(GlobalProduct.TXT_FILTER, productName);
			foundation.threadWait(Constants.MEDIUM_TIME);
			CustomisedAssert.assertTrue(foundation.getText(GlobalProduct.GBL_PRODUCT_DATA).equals(productName));
			table.selectRow(productName);
			foundation.scrollIntoViewElement(GlobalProduct.BTN_EXTEND);
			foundation.threadWait(Constants.MEDIUM_TIME);
			foundation.waitforElement(GlobalProduct.BTN_EXTEND_LOC, Constants.SHORT_TIME);
			foundation.click(GlobalProduct.BTN_EXTEND_LOC);
			foundation.threadWait(Constants.MEDIUM_TIME);
			textBox.enterText(LocationSummary.TXT_PRODUCT_FILTER, locationName);
			foundation.threadWait(Constants.MEDIUM_TIME);
			table.selectRow(locationName);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(LocationSummary.BTN_ADD_PRODUCT_ADD);
			navigationBar.navigateToMenuItem(menuItem.get(1));
			// foundation.refreshPage();
			locationList.selectLocationName(locationName);
			locationSummary.selectTab(requiredData.get(0));
			foundation.WaitForAjax(5000);
			foundation.waitforElement(LocationSummary.TXT_PRODUCT_FILTER, Constants.SHORT_TIME);
			foundation.threadWait(Constants.EXTRA_LONG_TIME);
			textBox.enterText(LocationSummary.TXT_PRODUCT_FILTER, productName);
			foundation.threadWait(Constants.LONG_TIME);
			CustomisedAssert.assertTrue(foundation.getText(LocationSummary.PRODUCT_NAME).equals(productName));
			foundation.click(LocationSummary.PRODUCT_NAME);
			foundation.waitforElement(LocationSummary.BTN_EDIT_PRODUCT, Constants.SHORT_TIME);
			foundation.click(LocationSummary.BTN_EDIT_PRODUCT);
			dropDown.selectItem(LocationSummary.TXT_CATEGORY, requiredData.get(3), Constants.TEXT);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.threadWait(Constants.SHORT_TIME);
			navigationBar.navigateToMenuItem(menuItem.get(1));

			// launching v5 Device
			List<String> language = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
			locationSummary.kiosklanguageSetting(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE),
					language.get(0), language.get(1));

			// launch v5 application
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			landingPage.changeLanguage(language.get(2), language.get(0), language.get(3));
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(productName);
			foundation.threadWait(Constants.THREE_SECOND);
			foundation.click(ProductSearch.BTN_PRODUCT);
			foundation.threadWait(Constants.THREE_SECOND);
			CustomisedAssert.assertEquals(foundation.getText(Order.TXT_HEADER), productHeader);
			CustomisedAssert.assertEquals(foundation.getText(Order.TXT_PRODUCT), productName);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// Resetting test data
			browser.close();
			browser.launch(Constants.LOCAL, Constants.CHROME);

			// launching browser and selecting org
			locationSummary.launchingBrowserAndSelectingOrg();
			navigationBar.navigateToMenuItem(menuItem.get(1));
			textBox.enterText(LocationList.TXT_FILTER,
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));
			locationList.selectLocationName(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));
			locationSummary.selectTab(requiredData.get(0));
			foundation.WaitForAjax(5000);
			foundation.waitforElement(LocationSummary.TXT_PRODUCT_FILTER, Constants.SHORT_TIME);
			foundation.threadWait(Constants.EXTRA_LONG_TIME);
			textBox.enterText(LocationSummary.TXT_PRODUCT_FILTER, productName);
			foundation.threadWait(Constants.LONG_TIME);
			foundation.WaitForAjax(10000);
			CustomisedAssert.assertTrue(foundation.getText(LocationSummary.PRODUCT_NAME).equals(productName));
			foundation.click(LocationSummary.PRODUCT_NAME);
			foundation.waitforElement(LocationSummary.BTN_REMOVE, Constants.SHORT_TIME);
			foundation.click(LocationSummary.BTN_REMOVE);
			foundation.waitforElementToBeVisible(LocationSummary.BTN_FULL_SYNC, Constants.THREE_SECOND);
			foundation.click(LocationSummary.BTN_FULL_SYNC);
			foundation.threadWait(Constants.THREE_SECOND);
		}
	}

	@Test(description = "167001- Verify the Inventory tab: Edit values and associate reason codes on Location Summary page as Super")
	public void VerifyInventoryTab() {
		final String CASE_NUM = "167001";
		rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		List<String> requiredData = Arrays
				.asList(rstLocationSummaryData.get(CNLocationSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		String menuItem = rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM);
		try {
			// launching browser and selecting org
			locationSummary.launchingBrowserAndSelectingOrg();

			// navigate to location summary
			navigationBar.navigateToMenuItem(menuItem);
			textBox.enterText(LocationList.TXT_FILTER,
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));
			locationList.selectLocationName(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));
			locationSummary.selectTab(requiredData.get(0));
			foundation.threadWait(Constants.TWO_SECOND);
			textBox.enterText(LocationSummary.TXT_INVENTORY_FILTER, requiredData.get(1));
			CustomisedAssert.assertTrue(foundation.getText(LocationSummary.INVENTORY_NAME).equals(requiredData.get(1)));
			locationSummary.updateInventory(requiredData.get(2), requiredData.get(3), requiredData.get(4));
			foundation.click(LocationSummary.BTN_SYNC);
			foundation.waitforElement(LocationSummary.LBL_SPINNER_MSG, Constants.SHORT_TIME);
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// reset data
			foundation.refreshPage();
			locationSummary.selectTab(requiredData.get(0));
			foundation.threadWait(Constants.TWO_SECOND);
			textBox.enterText(LocationSummary.TXT_INVENTORY_FILTER, requiredData.get(1));
			CustomisedAssert
					.assertTrue(foundation.getText(LocationSummary.INVENTORY_QUANTITY).equals(requiredData.get(3)));
			locationSummary.resetInventory(requiredData.get(2), requiredData.get(5));
			foundation.click(LocationSummary.BTN_SYNC);
			foundation.waitforElement(LocationSummary.LBL_SPINNER_MSG, Constants.SHORT_TIME);
		}
	}

	@Test(description = "167000- Verify the Tax Mapping tab: Add/Remove mapping on Location Summary page as Super")
	public void addRemoveTaxes() {
		final String CASE_NUM = "167000";
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		List<String> requiredData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		String productName = requiredData.get(1);
		String productHeader = requiredData.get(3);
		List<String> menuItem = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		try {
			// launching browser and selecting org
			locationSummary.launchingBrowserAndSelectingOrg();
			navigationBar.navigateToMenuItem(menuItem.get(2));
			dropDown.selectItem(OrgSummary.DPD_COUNTRY, requiredData.get(9), Constants.TEXT);
			dropDown.selectItem(OrgSummary.DPD_TAX_SYSTEM, requiredData.get(9), Constants.TEXT);
			foundation.click(OrgSummary.BTN_SAVE);
			foundation.waitforElementToDisappear(OrgSummary.TXT_SPINNER_MSG, Constants.EXTRA_LONG_TIME);
			foundation.waitforElement(OrgList.TXT_SEARCH_ORG, Constants.SHORT_TIME);
			navigationBar.navigateToMenuItem(menuItem.get(1));
			locationList.selectLocationName(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(LocationSummary.BTN_SYNC);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElementToDisappear(LocationList.TXT_SPINNER_MSG, Constants.EXTRA_LONG_TIME);
			foundation.waitforClikableElement(Login.LBL_USER_NAME, Constants.EXTRA_LONG_TIME);

			// add categories to products
			navigationBar.navigateToMenuItem(menuItem.get(0));
			foundation.threadWait(Constants.THREE_SECOND);
			globalProduct.selectGlobalProduct(requiredData.get(1));
			dropDown.selectItem(ProductSummary.DPD_TAX_CATEGORY, requiredData.get(2), Constants.TEXT);
			foundation.click(ProductSummary.BTN_SAVE);

			// save tax mapping
			navigationBar.navigateToMenuItem(menuItem.get(1));
			foundation.waitforElement(LocationList.TXT_FILTER, Constants.SHORT_TIME);
			locationList.selectLocationName(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));
			locationSummary.saveTaxMapping(requiredData.get(2), requiredData.get(8));
			foundation.click(LocationSummary.BTN_SAVE);

			// launching v5 Device
			List<String> language = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
			locationSummary.kiosklanguageSetting(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE),
					language.get(0), language.get(1));

			// launch v5 application
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			landingPage.changeLanguage(language.get(2), language.get(0), language.get(3));
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(productName);
			foundation.click(ProductSearch.BTN_PRODUCT);
			CustomisedAssert.assertEquals(foundation.getText(Order.TXT_HEADER), productHeader);
			CustomisedAssert.assertEquals(foundation.getText(Order.TXT_PRODUCT), productName);

			// verify the display of total section
			String productPrice = foundation.getText(Order.LBL_PRODUCT_PRICE).split(Constants.DOLLAR)[1];
			String tax1 = foundation.getText(Order.TAX).split(Constants.DOLLAR)[1];
			String tax2 = foundation.getText(Order.TAX).split(Constants.DOLLAR)[1];
			String tax3 = foundation.getText(Order.LBL_TAX_3).split(Constants.DOLLAR)[1];
			String tax4 = foundation.getText(Order.LBL_TAX_4).split(Constants.DOLLAR)[1];
			String balanceDue = foundation.getText(Order.LBL_BALANCE_DUE).split(Constants.DOLLAR)[1];
			String subTotal = foundation.getText(Order.LBL_SUB_TOTAL).split(Constants.DOLLAR)[1];
			Double expectedBalanceDue = Double.parseDouble(productPrice) + Double.parseDouble(tax1)
					+ Double.parseDouble(tax2) + Double.parseDouble(tax3) + Double.parseDouble(tax4);
			Double expectedTaxWithRoundUp = Math.round(expectedBalanceDue * 100.0) / 100.0;
			CustomisedAssert.assertEquals(balanceDue, expectedTaxWithRoundUp.toString());
			CustomisedAssert.assertEquals(subTotal, productPrice);
			CustomisedAssert.assertEquals(tax1, requiredData.get(4));
			CustomisedAssert.assertEquals(tax2, requiredData.get(5));
			CustomisedAssert.assertEquals(tax3, requiredData.get(6));
			CustomisedAssert.assertEquals(tax4, requiredData.get(7));
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// reset data
			browser.close();
			browser.launch(Constants.LOCAL, Constants.CHROME);

			// launching browser and selecting org
			locationSummary.launchingBrowserAndSelectingOrg();
			navigationBar.navigateToMenuItem(menuItem.get(1));
			foundation.waitforElement(LocationList.TXT_FILTER, Constants.SHORT_TIME);
			locationList.selectLocationName(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));
			locationSummary.removeTaxMapping(requiredData.get(2));
			foundation.click(LocationSummary.BTN_SYNC);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_FILTER, Constants.SHORT_TIME);
		}
	}

	@Test(description = "167007-Verify Create/Edit/Delete Menus in Micromarket service on Menus as Super")
	public void VerifyCreateEditDeleteMicroMarketMenu() {
		final String CASE_NUM = "167007";
		// reading data from dataBase
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
		rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		List<String> requiredData = Arrays
				.asList(rstLocationSummaryData.get(CNLocationSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> menuItem = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		try {
			// launching browser and selecting org
			locationSummary.launchingBrowserAndSelectingOrg();
			navigationBar.navigateToMenuItem(menuItem.get(0));
			dropDown.selectItem(SelfService.DPD_LOCATION,
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE),
					Constants.TEXT);
			foundation.click(SelfService.BTN_CREATE_NEW);
			textBox.enterText(SelfService.TXT_MENU_NAME, requiredData.get(0));
			selfService.clickCheckbox();
			foundation.click(SelfService.BTN_ADD_ITEM);
			textBox.enterText(SelfService.TXT_SEARCH_PRODUCT, rstLocationData.get(CNLocation.PRODUCT_NAME));
			foundation.click(SelfService.LBL_PRODUCT_NAME);
			foundation.click(SelfService.LBL_BTN_ADD);
			foundation.click(SelfService.BTN_SUBMENU_ADD);
			foundation.threadWait(Constants.THREE_SECOND);
			foundation.click(SelfService.BTN_ADD_MENU);
			textBox.enterText(SelfService.TXT_MENU_BUTTON_NAME, requiredData.get(1));
			foundation.click(SelfService.BTN_MENU_UPLOAD);
			textBox.enterText(SelfService.UPLOAD_BUTTON_IMG, FilePath.IMAGE_PATH);
			foundation.threadWait(Constants.ONE_SECOND);
			foundation.alertAccept();
			foundation.threadWait(Constants.THREE_SECOND);
			foundation.click(SelfService.BTN_ADD_IMG);
			foundation.threadWait(Constants.THREE_SECOND);
			foundation.click(SelfService.BTN_SAVE);

			// Updating the Menu
			foundation.threadWait(Constants.THREE_SECOND);
			dropDown.selectItem(SelfService.DPD_LOCATION,
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE),
					Constants.TEXT);
			textBox.enterText(SelfService.FILTER_MENU, requiredData.get(0));
			table.selectRow(requiredData.get(0));
			foundation.waitforElement(SelfService.BTN_ADD_ITEM, Constants.SHORT_TIME);
			textBox.enterText(SelfService.TXT_MONDAY_STRT, requiredData.get(2));
			textBox.enterText(SelfService.TXT_MONDAY_END, requiredData.get(3));
			foundation.click(SelfService.BTN_SAVE);
			foundation.waitforElement(LocationSummary.LBL_SPINNER_MSG, Constants.SHORT_TIME);
			navigationBar.navigateToMenuItem(menuItem.get(1));
			foundation.waitforElement(LocationList.TXT_FILTER, Constants.SHORT_TIME);
			List<String> language = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
			locationSummary.kiosklanguageSetting(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE),
					language.get(0), language.get(1));

			// launch v5 application
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			foundation.threadWait(Constants.MEDIUM_TIME);
			CustomisedAssert.assertEquals(foundation.getText(SelfService.TXT_ITEM), requiredData.get(4));
			CustomisedAssert.assertEquals(foundation.getText(SelfService.TXT_MENU), requiredData.get(1));
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// Resetting the data
			browser.close();
			browser.launch(Constants.LOCAL, Constants.CHROME);

			// launching browser and selecting org
			locationSummary.launchingBrowserAndSelectingOrg();
			navigationBar.navigateToMenuItem(menuItem.get(0));
			dropDown.selectItem(SelfService.DPD_LOCATION,
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE),
					Constants.TEXT);
			textBox.enterText(SelfService.FILTER_MENU, requiredData.get(0));
			table.selectRow(requiredData.get(0));
			foundation.waitforElement(SelfService.BTN_ADD_ITEM, Constants.SHORT_TIME);
			foundation.click(SelfService.BTN_DELETE);
			foundation.alertAccept();
			foundation.refreshPage();
			textBox.enterText(SelfService.FILTER_MENU, requiredData.get(0));
			foundation.threadWait(Constants.SHORT_TIME);
			CustomisedAssert.assertEquals(foundation.getText(SelfService.TXT_NO_MATCH), requiredData.get(5));
		}
	}
}