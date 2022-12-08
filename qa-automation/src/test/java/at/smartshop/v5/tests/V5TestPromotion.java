package at.smartshop.v5.tests;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import at.framework.database.mssql.Queries;
import at.framework.database.mssql.ResultSets;
import at.framework.generic.CustomisedAssert;
import at.framework.generic.Strings;
import at.framework.ui.Foundation;
import at.smartshop.database.columns.CNLocation;
import at.smartshop.database.columns.CNNavigationMenu;
import at.smartshop.database.columns.CNV5Device;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.pages.CreatePromotions;
import at.smartshop.pages.LocationList;
import at.smartshop.pages.NavigationBar;
import at.smartshop.pages.PromotionList;
import at.smartshop.tests.TestInfra;
import at.smartshop.v5.pages.LandingPage;
import at.smartshop.v5.pages.Order;
import at.smartshop.v5.pages.Payments;

@Listeners(at.framework.reportsetup.Listeners.class)
public class V5TestPromotion extends TestInfra {

	private ResultSets dataBase = new ResultSets();
	private Strings string = new Strings();
	private NavigationBar navigationBar = new NavigationBar();
	private Foundation foundation = new Foundation();
	private CreatePromotions createPromotions = new CreatePromotions();
	private LocationList locationList = new LocationList();
	private Order order = new Order();
	private PromotionList promotionList = new PromotionList();
	private Payments payments = new Payments();
	private LandingPage landingPage = new LandingPage();

	private Map<String, String> rstNavigationMenuData;
	private Map<String, String> rstLocationData;
	private Map<String, String> rstV5DeviceData;

	/**
	 * @author karthikr
	 * @date - 23/11/2022
	 */
	@Test(description = "208975 - Verify the Promotion is reflected in V5 device - Create/Expire Promotion for Tender Type Promotion with Discount Timing as Recurrence for Line Items.")
	public void verifyTenderDiscountWithDiscountTimingAsRecurrenceForLineItems() {
		final String CASE_NUM = "208975";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);

		List<String> navigationMenu = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> requiredData = Arrays
				.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> orderPageData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.ORDER_PAGE).split(Constants.DELIMITER_TILD));
		List<String> actualData = Arrays
				.asList(rstLocationData.get(CNLocation.ACTUAL_DATA).split(Constants.DELIMITER_TILD));
		final String promotionName = string.getRandomCharacter();
		String displayName = string.getRandomCharacter();
		try {
			// Login to ADM application as Operator user and select org
			navigationBar.launchBrowserAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(navigationMenu.get(0));

			// Navigate to Creation of Promotion page
			foundation.waitforElement(PromotionList.BTN_CREATE, Constants.SHORT_TIME);
			foundation.click(PromotionList.BTN_CREATE);
			foundation.isDisplayed(CreatePromotions.LBL_CREATE_PROMOTION);

			// Create new promotion as tender discount
			createPromotions.newPromotion(rstLocationData.get(CNLocation.PROMOTION_TYPE), promotionName, displayName,
					requiredData.get(0),
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(CreatePromotions.BTN_NEXT);

			// enter tender discount details with discounting type as recurrence
			createPromotions.tenderDiscountDetails(requiredData.get(1), requiredData.get(2), requiredData.get(3),
					requiredData.get(7), requiredData.get(8));
			createPromotions.selectPromotionTimes(requiredData.get(5), Constants.DELIMITER_SPACE);
			createPromotions.recurringDay();
			foundation.click(CreatePromotions.TXT_AMOUNT);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);
			CustomisedAssert.assertEquals(foundation.getText(CreatePromotions.POPUP_HEADER), requiredData.get(9));
			foundation.click(CreatePromotions.BTN_OK);
			foundation.waitforElement(PromotionList.TXT_SEARCH_PROMONAME, Constants.SHORT_TIME);

			// Selecting location and do Full Sync
			locationList.syncDevice(navigationMenu.get(1),
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));

			// login into Kiosk Device
			String price = landingPage.launchV5AndSelectProduct(requiredData.get(4));

			// verify the tender discount applies on order page
			CustomisedAssert.assertTrue(foundation.getText(Order.LBL_BALANCE_DUE).contains(String.valueOf(price)));
			CustomisedAssert.assertTrue(foundation.getText(Order.LBL_SUB_TOTAL).contains(price));
			CustomisedAssert.assertTrue(foundation.isDisplayed(Order.SAVINGS));
			CustomisedAssert.assertEquals(foundation.getText(Order.SAVINGS), actualData.get(0));
			CustomisedAssert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(0))));
			foundation.objectFocus(Order.LBL_MY_ACCOUNT);

			// complete transaction with verified Email
			payments.paymentUsingGMAVerifiedAccount(rstV5DeviceData.get(CNV5Device.EMAIL_ID),
					rstV5DeviceData.get(CNV5Device.PIN), rstV5DeviceData.get(CNV5Device.PAYMENTS_PAGE));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			browser.close();
			foundation.threadWait(Constants.SHORT_TIME);

			// Again Login to ADM application with Operator user to expire the promotion
			browser.launch(Constants.LOCAL, Constants.CHROME);
			navigationBar.launchBrowserAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Resetting the data
			promotionList.expirePromotion(navigationMenu.get(0), promotionName, actualData.get(1),
					rstLocationData.get(CNLocation.TAB_NAME));

			// Selecting location and do Full Sync
			locationList.syncDevice(navigationMenu.get(1),
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));

			// login into Kiosk Device
			String price = landingPage.launchV5AndSelectProduct(requiredData.get(4));

			// verify the tender discount applies on order page
			CustomisedAssert.assertTrue(foundation.getText(Order.LBL_BALANCE_DUE).contains(String.valueOf(price)));
			CustomisedAssert.assertTrue(foundation.getText(Order.LBL_SUB_TOTAL).contains(price));
			CustomisedAssert.assertFalse(foundation.isDisplayed(Order.SAVINGS));
			CustomisedAssert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(0))));
			foundation.objectFocus(Order.LBL_MY_ACCOUNT);
			browser.close();
			foundation.threadWait(Constants.SHORT_TIME);
		}
	}

	/**
	 * @author karthikr
	 * @date - 24/11/2022
	 */
	@Test(description = "209084 - Verify the Promotion is reflected in V5 device - Create/Expire Promotion for Tender Type Promotion with Discount Timing as Schedule for Line Items.")
	public void verifyTenderDiscountWithDiscountTimingAsScheduleForLineItems() {
		final String CASE_NUM = "209084";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);

		List<String> navigationMenu = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> requiredData = Arrays
				.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> orderPageData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.ORDER_PAGE).split(Constants.DELIMITER_TILD));
		List<String> actualData = Arrays
				.asList(rstLocationData.get(CNLocation.ACTUAL_DATA).split(Constants.DELIMITER_TILD));
		final String promotionName = string.getRandomCharacter();
		String displayName = string.getRandomCharacter();
		try {
			// Login to ADM application as Operator user and select org
			navigationBar.launchBrowserAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(navigationMenu.get(0));

			// Navigate to Creation of Promotion page
			foundation.waitforElement(PromotionList.BTN_CREATE, Constants.SHORT_TIME);
			foundation.click(PromotionList.BTN_CREATE);
			foundation.isDisplayed(CreatePromotions.LBL_CREATE_PROMOTION);

			// Create new promotion as tender discount
			createPromotions.newPromotion(rstLocationData.get(CNLocation.PROMOTION_TYPE), promotionName, displayName,
					requiredData.get(0),
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(CreatePromotions.BTN_NEXT);

			// enter tender discount details with discounting type as recurrence
			createPromotions.tenderDiscountDetails(requiredData.get(1), requiredData.get(2), requiredData.get(3),
					requiredData.get(7), requiredData.get(8));
			createPromotions.selectPromotionTimes(requiredData.get(6), Constants.DELIMITER_SPACE);
			foundation.click(CreatePromotions.TXT_AMOUNT);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);
			CustomisedAssert.assertEquals(foundation.getText(CreatePromotions.POPUP_HEADER), requiredData.get(9));
			foundation.click(CreatePromotions.BTN_OK);
			foundation.waitforElement(PromotionList.TXT_SEARCH_PROMONAME, Constants.SHORT_TIME);

			// Selecting location and do Full Sync
			locationList.syncDevice(navigationMenu.get(1),
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));

			// login into Kiosk Device
			String price = landingPage.launchV5AndSelectProduct(requiredData.get(4));

			// verify the tender discount applies on order page
			CustomisedAssert.assertTrue(foundation.getText(Order.LBL_BALANCE_DUE).contains(String.valueOf(price)));
			CustomisedAssert.assertTrue(foundation.getText(Order.LBL_SUB_TOTAL).contains(price));
			CustomisedAssert.assertTrue(foundation.isDisplayed(Order.SAVINGS));
			CustomisedAssert.assertEquals(foundation.getText(Order.SAVINGS), actualData.get(0));
			CustomisedAssert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(0))));
			foundation.objectFocus(Order.LBL_MY_ACCOUNT);

			// complete transaction with verified Email
			payments.paymentUsingGMAVerifiedAccount(rstV5DeviceData.get(CNV5Device.EMAIL_ID),
					rstV5DeviceData.get(CNV5Device.PIN), rstV5DeviceData.get(CNV5Device.PAYMENTS_PAGE));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			browser.close();
			foundation.threadWait(Constants.SHORT_TIME);

			// Again Login to ADM application with Operator user to expire the promotion
			browser.launch(Constants.LOCAL, Constants.CHROME);
			navigationBar.launchBrowserAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Resetting the data
			promotionList.expirePromotion(navigationMenu.get(0), promotionName, actualData.get(1),
					rstLocationData.get(CNLocation.TAB_NAME));

			// Selecting location and do Full Sync
			locationList.syncDevice(navigationMenu.get(1),
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));

			// login into Kiosk Device
			String price = landingPage.launchV5AndSelectProduct(requiredData.get(4));

			// verify the tender discount applies on order page
			CustomisedAssert.assertTrue(foundation.getText(Order.LBL_BALANCE_DUE).contains(String.valueOf(price)));
			CustomisedAssert.assertTrue(foundation.getText(Order.LBL_SUB_TOTAL).contains(price));
			CustomisedAssert.assertFalse(foundation.isDisplayed(Order.SAVINGS));
			CustomisedAssert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(0))));
			foundation.objectFocus(Order.LBL_MY_ACCOUNT);
			browser.close();
			foundation.threadWait(Constants.SHORT_TIME);
		}
	}

	/**
	 * @author karthikr
	 * @date - 28/11/2022
	 */
	@Test(description = "209121 - Verify the Promotion is reflected in V5 device - Create/Expire Promotion for Tender Type Promotion with Discount Timing as Recurrence for Overall Items.")
	public void verifyTenderDiscountWithDiscountTimingAsRecurrenceForOverAllItems() {
		final String CASE_NUM = "209121";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);

		List<String> navigationMenu = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> requiredData = Arrays
				.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> orderPageData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.ORDER_PAGE).split(Constants.DELIMITER_TILD));
		List<String> actualData = Arrays
				.asList(rstLocationData.get(CNLocation.ACTUAL_DATA).split(Constants.DELIMITER_TILD));
		final String promotionName = string.getRandomCharacter();
		String displayName = string.getRandomCharacter();
		try {
			// Login to ADM application as Operator user and select org
			navigationBar.launchBrowserAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(navigationMenu.get(0));

			// Navigate to Creation of Promotion page
			foundation.waitforElement(PromotionList.BTN_CREATE, Constants.SHORT_TIME);
			foundation.click(PromotionList.BTN_CREATE);
			foundation.isDisplayed(CreatePromotions.LBL_CREATE_PROMOTION);

			// Create new promotion as tender discount
			createPromotions.newPromotion(rstLocationData.get(CNLocation.PROMOTION_TYPE), promotionName, displayName,
					requiredData.get(0),
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(CreatePromotions.BTN_NEXT);

			// enter tender discount details with discounting type as recurrence
			createPromotions.tenderDiscountDetails(requiredData.get(1), requiredData.get(2), requiredData.get(3),
					requiredData.get(7), requiredData.get(8));
			createPromotions.selectPromotionTimes(requiredData.get(5), Constants.DELIMITER_SPACE);
			createPromotions.recurringDay();
			foundation.click(CreatePromotions.TXT_AMOUNT);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);
			CustomisedAssert.assertEquals(foundation.getText(CreatePromotions.POPUP_HEADER), requiredData.get(9));
			foundation.click(CreatePromotions.BTN_OK);
			foundation.waitforElement(PromotionList.TXT_SEARCH_PROMONAME, Constants.SHORT_TIME);

			// Selecting location and do Full Sync
			locationList.syncDevice(navigationMenu.get(1),
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));

			// login into Kiosk Device
			String price = landingPage.launchV5AndSelectProduct(requiredData.get(4));

			// verify the tender discount applies on order page
			CustomisedAssert.assertTrue(foundation.getText(Order.LBL_BALANCE_DUE).contains(String.valueOf(price)));
			CustomisedAssert.assertTrue(foundation.getText(Order.LBL_SUB_TOTAL).contains(price));
			CustomisedAssert.assertTrue(foundation.isDisplayed(Order.SAVINGS));
			CustomisedAssert.assertEquals(foundation.getText(Order.SAVINGS), actualData.get(0));
			CustomisedAssert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(0))));
			foundation.objectFocus(Order.LBL_MY_ACCOUNT);

			// complete transaction with verified Email
			payments.paymentUsingGMAVerifiedAccount(rstV5DeviceData.get(CNV5Device.EMAIL_ID),
					rstV5DeviceData.get(CNV5Device.PIN), rstV5DeviceData.get(CNV5Device.PAYMENTS_PAGE));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			browser.close();
			foundation.threadWait(Constants.SHORT_TIME);

			// Again Login to ADM application with Operator user to expire the promotion
			browser.launch(Constants.LOCAL, Constants.CHROME);
			navigationBar.launchBrowserAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Resetting the data
			promotionList.expirePromotion(navigationMenu.get(0), promotionName, actualData.get(1),
					rstLocationData.get(CNLocation.TAB_NAME));

			// Selecting location and do Full Sync
			locationList.syncDevice(navigationMenu.get(1),
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));

			// login into Kiosk Device
			String price = landingPage.launchV5AndSelectProduct(requiredData.get(4));

			// verify the tender discount applies on order page
			CustomisedAssert.assertTrue(foundation.getText(Order.LBL_BALANCE_DUE).contains(String.valueOf(price)));
			CustomisedAssert.assertTrue(foundation.getText(Order.LBL_SUB_TOTAL).contains(price));
			CustomisedAssert.assertFalse(foundation.isDisplayed(Order.SAVINGS));
			CustomisedAssert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(0))));
			foundation.objectFocus(Order.LBL_MY_ACCOUNT);
			browser.close();
			foundation.threadWait(Constants.SHORT_TIME);
		}
	}

	/**
	 * @author karthikr
	 * @date - 01/12/2022
	 */
	@Test(description = "209137 - Verify the Promotion is reflected in V5 device - Create/Expire Promotion for Tender Type Promotion with Discount Timing as Schedule for Overall Items.")
	public void verifyTenderDiscountWithDiscountTimingAsScheduleForOverAllItems() {
		final String CASE_NUM = "209137";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);

		List<String> navigationMenu = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> requiredData = Arrays
				.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> orderPageData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.ORDER_PAGE).split(Constants.DELIMITER_TILD));
		List<String> actualData = Arrays
				.asList(rstLocationData.get(CNLocation.ACTUAL_DATA).split(Constants.DELIMITER_TILD));
		final String promotionName = string.getRandomCharacter();
		String displayName = string.getRandomCharacter();
		try {
			// Login to ADM application as Operator user and select org
			navigationBar.launchBrowserAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(navigationMenu.get(0));

			// Navigate to Creation of Promotion page
			foundation.waitforElement(PromotionList.BTN_CREATE, Constants.SHORT_TIME);
			foundation.click(PromotionList.BTN_CREATE);
			foundation.isDisplayed(CreatePromotions.LBL_CREATE_PROMOTION);

			// Create new promotion as tender discount
			createPromotions.newPromotion(rstLocationData.get(CNLocation.PROMOTION_TYPE), promotionName, displayName,
					requiredData.get(0),
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(CreatePromotions.BTN_NEXT);

			// enter tender discount details with discounting type as recurrence
			createPromotions.tenderDiscountDetails(requiredData.get(1), requiredData.get(2), requiredData.get(3),
					requiredData.get(7), requiredData.get(8));
			createPromotions.selectPromotionTimes(requiredData.get(6), Constants.DELIMITER_SPACE);
			foundation.click(CreatePromotions.TXT_AMOUNT);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);
			CustomisedAssert.assertEquals(foundation.getText(CreatePromotions.POPUP_HEADER), requiredData.get(9));
			foundation.click(CreatePromotions.BTN_OK);
			foundation.waitforElement(PromotionList.TXT_SEARCH_PROMONAME, Constants.SHORT_TIME);

			// Selecting location and do Full Sync
			locationList.syncDevice(navigationMenu.get(1),
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));

			// login into Kiosk Device
			String price = landingPage.launchV5AndSelectProduct(requiredData.get(4));

			// verify the tender discount applies on order page
			CustomisedAssert.assertTrue(foundation.getText(Order.LBL_BALANCE_DUE).contains(String.valueOf(price)));
			CustomisedAssert.assertTrue(foundation.getText(Order.LBL_SUB_TOTAL).contains(price));
			CustomisedAssert.assertTrue(foundation.isDisplayed(Order.SAVINGS));
			CustomisedAssert.assertEquals(foundation.getText(Order.SAVINGS), actualData.get(0));
			CustomisedAssert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(0))));
			foundation.objectFocus(Order.LBL_MY_ACCOUNT);

			// complete transaction with verified Email
			payments.paymentUsingGMAVerifiedAccount(rstV5DeviceData.get(CNV5Device.EMAIL_ID),
					rstV5DeviceData.get(CNV5Device.PIN), rstV5DeviceData.get(CNV5Device.PAYMENTS_PAGE));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			browser.close();
			foundation.threadWait(Constants.SHORT_TIME);

			// Again Login to ADM application with Operator user to expire the promotion
			browser.launch(Constants.LOCAL, Constants.CHROME);
			navigationBar.launchBrowserAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Resetting the data
			promotionList.expirePromotion(navigationMenu.get(0), promotionName, actualData.get(1),
					rstLocationData.get(CNLocation.TAB_NAME));

			// Selecting location and do Full Sync
			locationList.syncDevice(navigationMenu.get(1),
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));

			// login into Kiosk Device
			String price = landingPage.launchV5AndSelectProduct(requiredData.get(4));

			// verify the tender discount applies on order page
			CustomisedAssert.assertTrue(foundation.getText(Order.LBL_BALANCE_DUE).contains(String.valueOf(price)));
			CustomisedAssert.assertTrue(foundation.getText(Order.LBL_SUB_TOTAL).contains(price));
			CustomisedAssert.assertFalse(foundation.isDisplayed(Order.SAVINGS));
			CustomisedAssert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(0))));
			foundation.objectFocus(Order.LBL_MY_ACCOUNT);
			browser.close();
			foundation.threadWait(Constants.SHORT_TIME);
		}
	}

	/**
	 * @author karthikr
	 * @date - 07/12/2022
	 */
	@Test(description = "220352 - Verify the Promotion is reflected in V5 device - Create/Expire Promotion for On Screen Promotion with Discount by Single Item with Discount Type as Amount & Discount Timing as Recurrence")
	public void verifyOnScreenWithDiscountTimingAsRecurrenceForSingleItem() {
		final String CASE_NUM = "220352";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);

		List<String> navigationMenu = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> requiredData = Arrays
				.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> orderPageData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.ORDER_PAGE).split(Constants.DELIMITER_TILD));
		List<String> actualData = Arrays
				.asList(rstLocationData.get(CNLocation.ACTUAL_DATA).split(Constants.DELIMITER_TILD));
		final String promotionName = string.getRandomCharacter();
		String displayName = string.getRandomCharacter();
		try {
			// Login to ADM application as Operator user and select org
			navigationBar.launchBrowserAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(navigationMenu.get(0));

			// Navigate to Creation of Promotion page
			foundation.waitforElement(PromotionList.BTN_CREATE, Constants.SHORT_TIME);
			foundation.click(PromotionList.BTN_CREATE);
			foundation.isDisplayed(CreatePromotions.LBL_CREATE_PROMOTION);

			// Create new promotion as On-Screen
			createPromotions.newPromotion(rstLocationData.get(CNLocation.PROMOTION_TYPE), promotionName, displayName,
					requiredData.get(0),
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(CreatePromotions.BTN_NEXT);

			// enter On-Screen details with discounting type as recurrence
			createPromotions.onScreenDetails(requiredData.get(2), requiredData.get(1), requiredData.get(6),
					requiredData.get(7));
			createPromotions.selectPromotionTimes(requiredData.get(4), Constants.DELIMITER_SPACE);
			createPromotions.recurringDay();
			createPromotions.selectOnScreenItem(requiredData.get(3));
			String discountPrice = foundation.getAttributeValue(CreatePromotions.TXT_AMOUNT);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);
			CustomisedAssert.assertEquals(foundation.getText(CreatePromotions.POPUP_HEADER), requiredData.get(8));
			foundation.click(CreatePromotions.BTN_OK);
			foundation.waitforElement(PromotionList.TXT_SEARCH_PROMONAME, Constants.SHORT_TIME);

			// Selecting location and do Full Sync
			locationList.syncDevice(navigationMenu.get(1),
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));

			// login into Kiosk Device
			String price = landingPage.launchV5AndSelectProduct(requiredData.get(3));

			// verify the On-Screen promo applies on order page
			CustomisedAssert.assertTrue(foundation.getText(Order.LBL_SUB_TOTAL).contains(price));
			CustomisedAssert.assertTrue(foundation.isDisplayed(Order.LBL_ORDER_DISCOUNT));
			String actuals = foundation.getText(Order.LBL_DISCOUNT_NAME);
			List<String> discountList = foundation.getTextofListElement(Order.LBL_ORDER_DISCOUNT);

			// Verifying the Promotion on transactions and discount amount
			CustomisedAssert.assertEquals(actuals, displayName);
			CustomisedAssert.assertTrue(discountList.get(2).contains(discountPrice));
			CustomisedAssert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(0))));
			foundation.objectFocus(Order.LBL_MY_ACCOUNT);

			// complete transaction with verified Email
			payments.paymentUsingGMAVerifiedAccount(rstV5DeviceData.get(CNV5Device.EMAIL_ID),
					rstV5DeviceData.get(CNV5Device.PIN), rstV5DeviceData.get(CNV5Device.PAYMENTS_PAGE));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			browser.close();
			foundation.threadWait(Constants.SHORT_TIME);

			// Again Login to ADM application with Operator user to expire the promotion
			browser.launch(Constants.LOCAL, Constants.CHROME);
			navigationBar.launchBrowserAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Resetting the data
			promotionList.expirePromotion(navigationMenu.get(0), promotionName, actualData.get(1),
					rstLocationData.get(CNLocation.TAB_NAME));

			// Selecting location and do Full Sync
			locationList.syncDevice(navigationMenu.get(1),
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));

			// login into Kiosk Device
			String price = landingPage.launchV5AndSelectProduct(requiredData.get(3));

			// verify the tender discount applies on order page
			CustomisedAssert.assertTrue(foundation.getText(Order.LBL_SUB_TOTAL).contains(price));
			CustomisedAssert.assertFalse(foundation.isDisplayed(Order.LBL_ORDER_DISCOUNT));
			CustomisedAssert.assertFalse(foundation.isDisplayed(Order.LBL_DISCOUNT_NAME));
			browser.close();
			foundation.threadWait(Constants.SHORT_TIME);
		}
	}

	/**
	 * @author karthikr
	 * @date - 08/12/2022
	 */
	@Test(description = "220442 - Verify the Promotion is reflected in V5 device - Create/Expire Promotion for On Screen Promotion with Discount by All Item with Discount Type as Amount & Discount Timing as Recurrence")
	public void verifyOnScreenWithDiscountTimingAsRecurrenceForAllItem() {
		final String CASE_NUM = "220442";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);

		List<String> navigationMenu = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> requiredData = Arrays
				.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> orderPageData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.ORDER_PAGE).split(Constants.DELIMITER_TILD));
		List<String> actualData = Arrays
				.asList(rstLocationData.get(CNLocation.ACTUAL_DATA).split(Constants.DELIMITER_TILD));
		final String promotionName = string.getRandomCharacter();
		String displayName = string.getRandomCharacter();
		try {
			// Login to ADM application as Operator user and select org
			navigationBar.launchBrowserAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(navigationMenu.get(0));

			// Navigate to Creation of Promotion page
			foundation.waitforElement(PromotionList.BTN_CREATE, Constants.SHORT_TIME);
			foundation.click(PromotionList.BTN_CREATE);
			foundation.isDisplayed(CreatePromotions.LBL_CREATE_PROMOTION);

			// Create new promotion as On-Screen
			createPromotions.newPromotion(rstLocationData.get(CNLocation.PROMOTION_TYPE), promotionName, displayName,
					requiredData.get(0),
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(CreatePromotions.BTN_NEXT);

			// enter On-Screen details with discounting type as recurrence
			createPromotions.onScreenDetails(requiredData.get(2), requiredData.get(1), requiredData.get(6),
					requiredData.get(7));
			createPromotions.selectPromotionTimes(requiredData.get(4), Constants.DELIMITER_SPACE);
			createPromotions.recurringDay();
			foundation.click(CreatePromotions.TXT_AMOUNT);
			foundation.click(CreatePromotions.BTN_ADD_ITEM);
			foundation.threadWait(Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.ITEM_MODAL_TITLE));
			foundation.click(CreatePromotions.ITEM_BUNDLE_ALL_CHECKBOX);
			foundation.click(CreatePromotions.BTN_CANCEL_ITEM_POPUP);
			foundation.threadWait(Constants.SHORT_TIME);
			String discountPrice = foundation.getAttributeValue(CreatePromotions.TXT_AMOUNT);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);
			CustomisedAssert.assertEquals(foundation.getText(CreatePromotions.POPUP_HEADER), requiredData.get(8));
			foundation.click(CreatePromotions.BTN_OK);
			foundation.waitforElement(PromotionList.TXT_SEARCH_PROMONAME, Constants.SHORT_TIME);

			// Selecting location and do Full Sync
			locationList.syncDevice(navigationMenu.get(1),
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));

			// login into Kiosk Device
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			landingPage.selectProduct(requiredData.get(3));
			String productPrice = foundation.getText(Order.LBL_PRODUCT_PRICE).split(Constants.DOLLAR)[1];
			CustomisedAssert.assertTrue(foundation.getText(Order.LBL_SUB_TOTAL).contains(productPrice));
			landingPage.selectProduct(requiredData.get(9));

			// verify the On-Screen promo applies on order page
			List<String> actuals = foundation.getTextofListElement(Order.LBL_DISCOUNT_NAME);
			List<String> discountList = foundation.getTextofListElement(Order.LBL_ORDER_DISCOUNT);

			// Verifying the Promotion on transactions and discount amount
			CustomisedAssert.assertEquals(actuals.get(0), displayName);
			CustomisedAssert.assertEquals(actuals.get(1), displayName);
			CustomisedAssert.assertTrue(discountList.get(2).contains(discountPrice));
			CustomisedAssert.assertTrue(discountList.get(6).contains(discountPrice));
			CustomisedAssert.assertTrue(foundation.isDisplayed(Order.LBL_ORDER_DISCOUNT));
			CustomisedAssert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(0))));
			foundation.objectFocus(Order.LBL_MY_ACCOUNT);

			// complete transaction with verified Email
			payments.paymentUsingGMAVerifiedAccount(rstV5DeviceData.get(CNV5Device.EMAIL_ID),
					rstV5DeviceData.get(CNV5Device.PIN), rstV5DeviceData.get(CNV5Device.PAYMENTS_PAGE));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			browser.close();
			foundation.threadWait(Constants.SHORT_TIME);

			// Again Login to ADM application with Operator user to expire the promotion
			browser.launch(Constants.LOCAL, Constants.CHROME);
			navigationBar.launchBrowserAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Resetting the data
			promotionList.expirePromotion(navigationMenu.get(0), promotionName, actualData.get(1),
					rstLocationData.get(CNLocation.TAB_NAME));

			// Selecting location and do Full Sync
			locationList.syncDevice(navigationMenu.get(1),
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));

			// login into Kiosk Device
			String price = landingPage.launchV5AndSelectProduct(requiredData.get(3));

			// verify the tender discount applies on order page
			CustomisedAssert.assertTrue(foundation.getText(Order.LBL_SUB_TOTAL).contains(price));
			CustomisedAssert.assertFalse(foundation.isDisplayed(Order.LBL_ORDER_DISCOUNT));
			CustomisedAssert.assertFalse(foundation.isDisplayed(Order.LBL_DISCOUNT_NAME));
			browser.close();
			foundation.threadWait(Constants.SHORT_TIME);
		}
	}
}