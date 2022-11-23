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
import at.framework.ui.TextBox;
import at.smartshop.database.columns.CNLocation;
import at.smartshop.database.columns.CNNavigationMenu;
import at.smartshop.database.columns.CNV5Device;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.pages.CreatePromotions;
import at.smartshop.pages.EditPromotion;
import at.smartshop.pages.LocationList;
import at.smartshop.pages.LocationSummary;
import at.smartshop.pages.NavigationBar;
import at.smartshop.pages.PromotionList;
import at.smartshop.tests.TestInfra;
import at.smartshop.v5.pages.LandingPage;
import at.smartshop.v5.pages.Order;
import at.smartshop.v5.pages.ProductSearch;

@Listeners(at.framework.reportsetup.Listeners.class)
public class V5TestPromotion extends TestInfra {

	private ResultSets dataBase = new ResultSets();
	private Strings string = new Strings();
	private NavigationBar navigationBar = new NavigationBar();
	private Foundation foundation = new Foundation();
	private CreatePromotions createPromotions = new CreatePromotions();
	private LocationList locationList = new LocationList();
	private LocationSummary locationSummary = new LocationSummary();
	private TextBox textBox = new TextBox();
	private Order order = new Order();
	private EditPromotion editPromotion = new EditPromotion();

	private Map<String, String> rstNavigationMenuData;
	private Map<String, String> rstLocationData;
	private Map<String, String> rstV5DeviceData;

	@Test(description = "208975 - Verify the Promotion is reflected in V5 device - Create/Expire Promotion for Tender Type Promotion with Discount Timing as Recurrence.")
	public void verifyTenderDiscountWithDiscountTimingAsRecurrence() {
		final String CASE_NUM = "208975";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);

		List<String> navigationMenu = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> requiredData = Arrays
				.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> language = Arrays.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
		final String promotionName = string.getRandomCharacter();
		String displayName = string.getRandomCharacter();
		try {
			// Login to ADM application as Operator user
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(navigationMenu.get(0));

			// Create New Promotion
			foundation.waitforElement(PromotionList.BTN_CREATE, Constants.SHORT_TIME);
			foundation.click(PromotionList.BTN_CREATE);
			foundation.isDisplayed(CreatePromotions.LBL_CREATE_PROMOTION);
			createPromotions.newPromotion(rstLocationData.get(CNLocation.PROMOTION_TYPE), promotionName, displayName,
					requiredData.get(0),
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(CreatePromotions.BTN_NEXT);
			createPromotions.tenderDiscountDetails(requiredData.get(1), requiredData.get(2), requiredData.get(3),
					requiredData.get(7), requiredData.get(8));

			createPromotions.selectBundlePromotionTimes(requiredData.get(5), Constants.DELIMITER_SPACE);
			createPromotions.recurringDay();
			foundation.click(CreatePromotions.TXT_AMOUNT);

			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_OK);

			foundation.waitforElement(PromotionList.TXT_SEARCH_PROMONAME, Constants.SHORT_TIME);
			navigationBar.navigateToMenuItem(navigationMenu.get(1));

			// Selecting location
			locationList.selectLocationName(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));

			foundation.click(LocationSummary.BTN_SYNC);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			login.logout();

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			locationSummary.kiosklanguageSetting(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE),
					language.get(0), language.get(1));

			foundation.threadWait(Constants.SHORT_TIME);
			// login into Kiosk Device
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));

			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(requiredData.get(4));
			foundation.click(ProductSearch.BTN_PRODUCT);
			CustomisedAssert.assertTrue(foundation.isDisplayed(Order.BTN_CANCEL_ORDER));

			CustomisedAssert.assertTrue(requiredData.get(4).equals(foundation.getText(Order.LBL_PROMOTION_NAME)));
			String productPrice = foundation.getText(Order.LBL_PRODUCT_PRICE).split(Constants.DOLLAR)[1];

			// verify the display of total section
			CustomisedAssert
					.assertTrue(foundation.getText(Order.LBL_BALANCE_DUE).contains(String.valueOf(productPrice)));
			CustomisedAssert.assertTrue(foundation.getText(Order.LBL_SUB_TOTAL).contains(productPrice));
			CustomisedAssert
					.assertTrue(foundation.isDisplayed(order.objText(rstLocationData.get(CNLocation.ACTUAL_DATA))));

			List<String> orderPageData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.ORDER_PAGE).split(Constants.DELIMITER_TILD));
			List<String> paymentPageData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.PAYMENTS_PAGE).split(Constants.DELIMITER_TILD));
			CustomisedAssert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(0))));
			foundation.objectFocus(order.objText(orderPageData.get(1)));
			order.completeOrder(orderPageData.get(1), paymentPageData.get(0), paymentPageData.get(1));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			browser.close();
			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(navigationMenu.get(0));

			// Deleting the Promotion
			editPromotion.expirePromotion(rstLocationData.get(CNLocation.TAB_NAME), promotionName);
		}
	}
}
