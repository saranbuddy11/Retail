package at.smartshop.v5.tests;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.Keys;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import at.framework.database.mssql.Queries;
import at.framework.database.mssql.ResultSets;
import at.framework.generic.CustomisedAssert;
import at.framework.generic.DateAndTime;
import at.framework.generic.Numbers;
import at.framework.generic.Strings;
import at.framework.ui.CheckBox;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.Table;
import at.framework.ui.TextBox;
import at.smartshop.database.columns.CNConsumer;
import at.smartshop.database.columns.CNConsumerSearch;
import at.smartshop.database.columns.CNConsumerSummary;
import at.smartshop.database.columns.CNDeviceList;
import at.smartshop.database.columns.CNGlobalProductChange;
import at.smartshop.database.columns.CNLocation;
import at.smartshop.database.columns.CNLocationList;
import at.smartshop.database.columns.CNLocationSummary;
import at.smartshop.database.columns.CNNavigationMenu;
import at.smartshop.database.columns.CNOrgSummary;
import at.smartshop.database.columns.CNV5Device;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.pages.CategoryList;
import at.smartshop.pages.CategorySummary;
import at.smartshop.pages.ConsumerSearch;
import at.smartshop.pages.ConsumerSummary;
import at.smartshop.pages.CreatePromotions;
import at.smartshop.pages.DeviceCreate;
import at.smartshop.pages.DeviceDashboard;
import at.smartshop.pages.DeviceList;
import at.smartshop.pages.DeviceSummary;
import at.smartshop.pages.EditPromotion;
import at.smartshop.pages.GlobalProduct;
import at.smartshop.pages.LocationList;
import at.smartshop.pages.LocationSummary;
import at.smartshop.pages.Login;
import at.smartshop.pages.MicroMarketMenuList;
import at.smartshop.pages.NavigationBar;
import at.smartshop.pages.OrgList;
import at.smartshop.pages.OrgSummary;
import at.smartshop.pages.ProductSummary;
import at.smartshop.pages.PromotionList;
import at.smartshop.pages.TaxList;
import at.smartshop.tests.TestInfra;
import at.smartshop.v5.pages.AccountDetails;
import at.smartshop.v5.pages.AccountLogin;
import at.smartshop.v5.pages.AdminMenu;
import at.smartshop.v5.pages.CardPayment;
import at.smartshop.v5.pages.ChangePin;
import at.smartshop.v5.pages.CreateAccount;
import at.smartshop.v5.pages.DriverHomePage;
import at.smartshop.v5.pages.DriverLoginPage;
import at.smartshop.v5.pages.EditAccount;
import at.smartshop.v5.pages.FingerPrintPayment;
import at.smartshop.v5.pages.FundAccount;
import at.smartshop.v5.pages.InventoryHomePage;
import at.smartshop.v5.pages.LandingPage;
import at.smartshop.v5.pages.Order;
import at.smartshop.v5.pages.PaymentSuccess;
import at.smartshop.v5.pages.Payments;
import at.smartshop.v5.pages.Policy;
import at.smartshop.v5.pages.ProductSearch;
import at.smartshop.v5.pages.ScanPayment;
import at.smartshop.v5.pages.UserProfile;

@Listeners(at.framework.reportsetup.Listeners.class)
public class V5Test extends TestInfra {

	private Foundation foundation = new Foundation();
	private TextBox textBox = new TextBox();
	private ResultSets dataBase = new ResultSets();
	private LandingPage landingPage = new LandingPage();
	private AccountLogin accountLogin = new AccountLogin();
	private EditAccount editAccount = new EditAccount();
	private NavigationBar navigationBar = new NavigationBar();
	private GlobalProduct globalProduct = new GlobalProduct();
	private LocationList locationList = new LocationList();
	private LocationSummary locationSummary = new LocationSummary();
	private Dropdown dropdown = new Dropdown();
	private AdminMenu adminMenu = new AdminMenu();
	private Strings strings = new Strings();
	private DeviceSummary deviceSummary = new DeviceSummary();
	private DriverLoginPage driverLoginPage = new DriverLoginPage();
	private DriverHomePage driverHomePage = new DriverHomePage();
	private InventoryHomePage inventoryHomePage = new InventoryHomePage();
	private Dropdown dropDown = new Dropdown();
	private ProductSearch productSearch = new ProductSearch();
	private Order order = new Order();
	private CardPayment cardPayment = new CardPayment();
	private CreateAccount createAccount = new CreateAccount();
	private AccountDetails accountDetails = new AccountDetails();
	private FundAccount fundAccount = new FundAccount();
	private ScanPayment scanPayment = new ScanPayment();
	private FingerPrintPayment fingerPrintPayment = new FingerPrintPayment();
	private ChangePin changePin = new ChangePin();
	private OrgList orgList = new OrgList();
	private Payments payments = new Payments();
	private Strings string = new Strings();
	private CheckBox checkBox = new CheckBox();
	private MicroMarketMenuList microMarketMenu = new MicroMarketMenuList();
	private DateAndTime dateAndTime = new DateAndTime();
	private CategoryList categoryList = new CategoryList();
	private CategorySummary categorySummary = new CategorySummary();
	private ConsumerSearch consumerSearch = new ConsumerSearch();
	private ConsumerSummary consumerSummary = new ConsumerSummary();
	private Numbers numbers = new Numbers();
	private Table table = new Table();
	private TaxList taxList = new TaxList();
	private EditPromotion editPromotion = new EditPromotion();
	private CreatePromotions createPromotions = new CreatePromotions();

	private Map<String, String> rstV5DeviceData;
	private Map<String, String> rstDeviceListData;
	private Map<String, String> rstNavigationMenuData;
	private Map<String, String> rstLocationListData;
	private Map<String, String> rstLocationSummaryData;
	private Map<String, String> rstConsumerSearchData;
	private Map<String, String> rstConsumerSummaryData;
	private Map<String, String> rstConsumerData;
	private Map<String, String> rstOrgSummaryData;
	private Map<String, String> rstGlobalProductChangeData;
	private Map<String, String> rstLocationData;

	@Test(description = "141874-Kiosk Manage Account > Edit Account > Update Information")
	public void editAccountUpdateInformation() {
		final String CASE_NUM = "141874";
		String firstName = "";
		String lastName = "";
		String emailAddress = "";
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		List<String> actualData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.ACTUAL_DATA).split(Constants.DELIMITER_TILD));

		try {
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

//			List<String> requiredData = Arrays
//					.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> language = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			locationSummary.kiosklanguageSetting(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE),
					language.get(0), language.get(1));
			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);

			// login to application
			landingPage.changeLanguage(language.get(2), language.get(0), language.get(3));
			foundation.click(LandingPage.BTN_LOGIN);
			foundation.click(AccountLogin.BTN_EMAIL_LOGIN);
			accountLogin.login(propertyFile.readPropertyFile(Configuration.V5_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.V5_PIN, FilePath.PROPERTY_CONFIG_FILE));

			// navigate to edit account and get previous information
			foundation.click(EditAccount.BTN_EDIT_ACCOUNT);
			firstName = textBox.getTextFromInput(EditAccount.TXT_FIRST_NAME);
			lastName = textBox.getTextFromInput(EditAccount.TXT_LAST_NAME);
			emailAddress = textBox.getTextFromInput(EditAccount.TXT_EMAIL_ADDRESS);

			// update information
			foundation.click(EditAccount.BTN_CAMEL_CASE);
			editAccount.updateText(EditAccount.TXT_FIRST_NAME, actualData.get(0), firstName);
			editAccount.updateText(EditAccount.TXT_LAST_NAME, actualData.get(1), lastName);
			editAccount.updateText(EditAccount.TXT_EMAIL_ADDRESS, actualData.get(2), emailAddress);
			foundation.click(EditAccount.BTN_SAVE);
			foundation.waitforClikableElement(EditAccount.BTN_EDIT_ACCOUNT, Constants.EXTRA_LONG_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(EditAccount.BTN_EDIT_ACCOUNT));

			// navigate back to edit account and verify all data are populating as entered
			// before
			foundation.click(EditAccount.BTN_EDIT_ACCOUNT);
			CustomisedAssert.assertTrue(textBox.getTextFromInput(EditAccount.TXT_FIRST_NAME).equals(actualData.get(0)));
			CustomisedAssert.assertTrue(textBox.getTextFromInput(EditAccount.TXT_LAST_NAME).equals(actualData.get(1)));
			CustomisedAssert
					.assertTrue(textBox.getTextFromInput(EditAccount.TXT_EMAIL_ADDRESS).equals(actualData.get(2)));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// reset the data
			foundation.click(EditAccount.BTN_CAMEL_CASE);
			editAccount.updateText(EditAccount.TXT_FIRST_NAME, firstName, actualData.get(0));
			editAccount.updateText(EditAccount.TXT_LAST_NAME, lastName, actualData.get(1));
			editAccount.updateText(EditAccount.TXT_EMAIL_ADDRESS, emailAddress, actualData.get(2));
			foundation.click(EditAccount.BTN_SAVE);
			foundation.waitforClikableElement(EditAccount.BTN_EDIT_ACCOUNT, Constants.EXTRA_LONG_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(EditAccount.BTN_EDIT_ACCOUNT));
		}
	}

	@Test(description = "143464-Verify the Search button in Device when user set 'Yes' the Inherit from Location")
	public void verifySearchButton() {
		try {

			final String CASE_NUM = "143464";

			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			locationList.selectLocationName(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));

			foundation.objectFocus(LocationSummary.BTN_DEPLOY_DEVICE);
			textBox.enterText(LocationSummary.TXT_DEVICE_SEARCH,
					rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));
			locationSummary.selectDeviceName(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));
			foundation.waitforElement(DeviceSummary.LBL_DEVICE_SUMMARY, Constants.SHORT_TIME);
			List<String> requiredData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> language = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
			dropDown.selectItem(DeviceSummary.DPD_SHOW_SEARCH_BTN, requiredData.get(0), Constants.TEXT);
			foundation.click(DeviceSummary.BTN_SAVE);
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			// locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			locationList.selectLocationName(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));
			foundation.waitforElement(LocationSummary.BTN_SAVE, Constants.SHORT_TIME);
			foundation.click(LocationSummary.BUTTON_LOCATION_INFO);
			dropDown.selectItem(LocationSummary.DPD_SHOW_PROD_LOOKUP, requiredData.get(1), Constants.TEXT);
			foundation.click(LocationSummary.BTN_SYNC);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_FILTER, Constants.SHORT_TIME);
			foundation.waitforElementToDisappear(LocationList.TXT_SPINNER_MSG, Constants.EXTRA_LONG_TIME);
			login.logout();
			browser.close();

			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			landingPage.changeLanguage(language.get(2), language.get(0), language.get(3));

			foundation.isDisplayed(LandingPage.IMG_SEARCH_ICON);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "142741- SOS-24492 Verify alternate language is set to Finnish in Kiosk when user set the Alternate Language as Finnish and full sync is done in ADM")
	public void alternateFinnishLanguage() {
		final String CASE_NUM = "142741";
		// Reading test data from DataBase
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		List<String> requiredData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> language = Arrays.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Selecting location
			locationList.selectLocationName(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));
			dropDown.selectItem(LocationSummary.DPD_KIOSK_LANGUAGE, requiredData.get(1), Constants.TEXT);
			dropDown.selectItem(LocationSummary.DPD_ALTERNATE_LANGUAGE, requiredData.get(2), Constants.TEXT);
			foundation.click(LocationSummary.BTN_SYNC);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			foundation.waitforElementToDisappear(LocationList.TXT_SPINNER_MSG, Constants.EXTRA_LONG_TIME);
			login.logout();
			browser.close();

			foundation.threadWait(Constants.SHORT_TIME);
			// login into Kiosk Device
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			landingPage.changeLanguage(language.get(2), requiredData.get(2), language.get(3));

			// Validating Landing Page
			foundation.isDisplayed(LandingPage.IMG_SEARCH_ICON);
			landingPage.verifyHomeScreenLanguage(rstV5DeviceData.get(CNV5Device.LANDING_PAGE));

			// Validating Search Page
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			productSearch.verifyProductSearhPageLanguage(rstV5DeviceData.get(CNV5Device.PRODUCT_SEARCH_PAGE));

			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
			foundation.click(ProductSearch.BTN_PRODUCT);

			// verify Order Page
			List<String> orderPageData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.ORDER_PAGE).split(Constants.DELIMITER_TILD));
			order.verifyOrderPageLanguage(rstV5DeviceData.get(CNV5Device.ORDER_PAGE));

			// Validating Credit/Debit Page
			foundation.click(order.objText(orderPageData.get(8)));
			cardPayment.verifyCardPaymentPageLanguage(rstV5DeviceData.get(CNV5Device.CREDIT_DEBIT_PAGE));
			foundation.click(CardPayment.BTN_CLOSE);
			foundation.waitforElement(order.objText(orderPageData.get(0)), Constants.SHORT_TIME);

			// verify Cancel Order Page
			foundation.click(order.objText(orderPageData.get(0)));
			CustomisedAssert.assertTrue(
					foundation.isDisplayed(createAccount.objText(rstV5DeviceData.get(CNV5Device.TRANSACTION_CANCEL))));

			// Validating Create Account Page
			foundation.waitforElement(LandingPage.BTN_CREATE_ACCOUNT, Constants.SHORT_TIME);
			foundation.click(LandingPage.BTN_CREATE_ACCOUNT);
			createAccount.verifyCreateAccoutnPageLanguage(rstV5DeviceData.get(CNV5Device.CREATE_ACCOUNT),
					requiredData.get(3), rstV5DeviceData.get(CNV5Device.ACTUAL_DATA));

			// Validating Account Login Page
			foundation.click(LandingPage.BTN_LOGIN);
			accountLogin.verifyAccountLoginPageLanguage(rstV5DeviceData.get(CNV5Device.LOGIN_PAGE));

			textBox.enterPin(propertyFile.readPropertyFile(Configuration.V5_PIN, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(AccountLogin.BTN_PIN_NEXT);

			// Verifying Account info page
			List<String> accountPageData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.ACCOUNT_DETAILS).split(Constants.DELIMITER_TILD));
			accountDetails.verifyAccountDetailsPageLanguage(rstV5DeviceData.get(CNV5Device.ACCOUNT_DETAILS),
					requiredData.get(3), rstV5DeviceData.get(CNV5Device.ACTUAL_DATA));

			// Verifying Fund with card page
			foundation.click(fundAccount.objText(accountPageData.get(1)));
			fundAccount.verifyFundAccountScreenLanguage(rstV5DeviceData.get(CNV5Device.FUND_ACCOUNT_PAGE));

			// Verifying Scan Setup page
			foundation.click(fundAccount.objText(accountPageData.get(4)));
			scanPayment.verifyScanPaymentPageLanguage(rstV5DeviceData.get(CNV5Device.QUICK_SCAN_SETUP),
					requiredData.get(3), rstV5DeviceData.get(CNV5Device.ACTUAL_DATA));

			// Verifying Finger print Setup page
			foundation.click(createAccount.objText(accountPageData.get(6)));
			fingerPrintPayment.verifyFingerPrintPaymentPageLanguage(rstV5DeviceData.get(CNV5Device.FINGER_PRINT_SETUP));

			// Verifying Edit account page
			foundation.click(createAccount.objText(accountPageData.get(7)));
			editAccount.verifyEditAccountPageLanguage(rstV5DeviceData.get(CNV5Device.EDIT_ACCOUNT_DETAILS));

			// verify Change pin
			List<String> accountEditPageData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.EDIT_ACCOUNT_DETAILS).split(Constants.DELIMITER_TILD));
			foundation.click(createAccount.objText(accountEditPageData.get(3)));
			changePin.verifyChangePinPageLanguage(rstV5DeviceData.get(CNV5Device.CHANGE_PIN));
			foundation.click(createAccount.objText(accountEditPageData.get(6)));

			// Verifying timeout popup
			editAccount.verifyTimeOutPopLanguage(rstV5DeviceData.get(CNV5Device.TIME_OUT_POPUP));

			// Verifying Product Purchase page
			foundation.waitforElement(LandingPage.IMG_SEARCH_ICON, Constants.SHORT_TIME);
			foundation.click(LandingPage.IMG_SEARCH_ICON);

			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
			foundation.click(ProductSearch.BTN_PRODUCT);
			foundation.objectFocus(order.objText(orderPageData.get(7)));
			foundation.click(order.objText(orderPageData.get(7)));
			foundation.waitforElement(AccountLogin.BTN_NEXT, Constants.SHORT_TIME);

			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(
					propertyFile.readPropertyFile(Configuration.V5_USER, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(AccountLogin.BTN_NEXT);
			foundation.waitforElement(AccountLogin.BTN_PIN_NEXT, Constants.SHORT_TIME);
			textBox.enterPin(rstV5DeviceData.get(CNV5Device.PIN));
			foundation.click(AccountLogin.BTN_PIN_NEXT);
			payments.verifyPaymentPageLanguage(rstV5DeviceData.get(CNV5Device.PAYMENTS_PAGE));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			browser.close();
			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Selecting location
			locationList.selectLocationName(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));
			dropDown.selectItem(LocationSummary.DPD_KIOSK_LANGUAGE, requiredData.get(4), Constants.TEXT);
			dropDown.selectItem(LocationSummary.DPD_ALTERNATE_LANGUAGE, requiredData.get(2), Constants.TEXT);
			foundation.click(LocationSummary.BTN_SYNC);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			foundation.waitforElementToDisappear(LocationList.TXT_SPINNER_MSG, Constants.EXTRA_LONG_TIME);
			login.logout();
		}
	}

	@Test(description = "142742-Verify default and Alternative languages in Kiosk when user sets same language and full sync is done in ADM")
	public void englishDefaultAltLanguage() {
		final String CASE_NUM = "142742";
		// Reading test data from DataBase
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		List<String> requiredData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> language = Arrays.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Selecting location
			locationList.selectLocationName(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));
			dropDown.selectItem(LocationSummary.DPD_KIOSK_LANGUAGE, requiredData.get(1), Constants.TEXT);
			dropDown.selectItem(LocationSummary.DPD_ALTERNATE_LANGUAGE, requiredData.get(1), Constants.TEXT);

			foundation.click(LocationSummary.BTN_SYNC);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_FILTER, Constants.SHORT_TIME);
			foundation.waitforElementToDisappear(LocationList.TXT_SPINNER_MSG, Constants.EXTRA_LONG_TIME);
			login.logout();
			browser.close();

			foundation.threadWait(Constants.SHORT_TIME);
			// login into Kiosk Device
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			landingPage.changeLanguage(language.get(2), language.get(0), language.get(3));

			// Validating Landing Page
			landingPage.verifyHomeScreenLanguage(rstV5DeviceData.get(CNV5Device.LANDING_PAGE));

			// Validating Search Page
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			productSearch.verifyProductSearhPageLanguage(rstV5DeviceData.get(CNV5Device.PRODUCT_SEARCH_PAGE));

			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
			foundation.click(ProductSearch.BTN_PRODUCT);

			// verify Order Page
			List<String> orderPageData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.ORDER_PAGE).split(Constants.DELIMITER_TILD));
			order.verifyOrderPageLanguage(rstV5DeviceData.get(CNV5Device.ORDER_PAGE));

			// Validating Credit/Debit Page
			foundation.click(order.objText(orderPageData.get(8)));
			cardPayment.verifyCardPaymentPageLanguage(rstV5DeviceData.get(CNV5Device.CREDIT_DEBIT_PAGE));

			foundation.click(CardPayment.BTN_CLOSE);
			foundation.waitforElement(order.objText(orderPageData.get(0)), Constants.SHORT_TIME);

			// verify Cancel Order Page
			foundation.click(order.objText(orderPageData.get(0)));
			CustomisedAssert.assertTrue(
					foundation.isDisplayed(createAccount.objText(rstV5DeviceData.get(CNV5Device.TRANSACTION_CANCEL))));

			// Validating Create Account Page
			foundation.waitforElement(LandingPage.BTN_CREATE_ACCOUNT, Constants.SHORT_TIME);
			foundation.click(LandingPage.BTN_CREATE_ACCOUNT);
			createAccount.verifyCreateAccoutnPageLanguage(rstV5DeviceData.get(CNV5Device.CREATE_ACCOUNT),
					requiredData.get(3), rstV5DeviceData.get(CNV5Device.ACTUAL_DATA));

			// Validating Account Login Page
			foundation.click(LandingPage.BTN_LOGIN);
			accountLogin.verifyAccountLoginPageLanguage(rstV5DeviceData.get(CNV5Device.LOGIN_PAGE));

			textBox.enterPin(propertyFile.readPropertyFile(Configuration.V5_PIN, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(AccountLogin.BTN_PIN_NEXT);

			// Verifying Account info page
			List<String> accountPageData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.ACCOUNT_DETAILS).split(Constants.DELIMITER_TILD));
			accountDetails.verifyAccountDetailsPageLanguage(rstV5DeviceData.get(CNV5Device.ACCOUNT_DETAILS),
					requiredData.get(3), rstV5DeviceData.get(CNV5Device.ACTUAL_DATA));

			// Verifying Fund with card page
			foundation.click(fundAccount.objText(accountPageData.get(1)));
			fundAccount.verifyFundAccountScreenLanguage(rstV5DeviceData.get(CNV5Device.FUND_ACCOUNT_PAGE));

			// Verifying Scan Setup page
			foundation.click(fundAccount.objText(accountPageData.get(4)));
			scanPayment.verifyScanPaymentPageLanguage(rstV5DeviceData.get(CNV5Device.QUICK_SCAN_SETUP),
					requiredData.get(3), rstV5DeviceData.get(CNV5Device.ACTUAL_DATA));

			// Verifying Finger print Setup page
			foundation.click(createAccount.objText(accountPageData.get(6)));
			fingerPrintPayment.verifyFingerPrintPaymentPageLanguage(rstV5DeviceData.get(CNV5Device.FINGER_PRINT_SETUP));

			// Verifying Edit account page
			foundation.click(createAccount.objText(accountPageData.get(7)));
			editAccount.verifyEditAccountPageLanguage(rstV5DeviceData.get(CNV5Device.EDIT_ACCOUNT_DETAILS));

			// verify Change pin
			List<String> accountEditPageData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.EDIT_ACCOUNT_DETAILS).split(Constants.DELIMITER_TILD));
			foundation.click(createAccount.objText(accountEditPageData.get(3)));
			changePin.verifyChangePinPageLanguage(rstV5DeviceData.get(CNV5Device.CHANGE_PIN));
			foundation.click(createAccount.objText(accountEditPageData.get(6)));

			// Verifying timeout popup
			editAccount.verifyTimeOutPopLanguage(rstV5DeviceData.get(CNV5Device.TIME_OUT_POPUP));

			// Verifying Product Purchase page
			foundation.waitforElement(LandingPage.IMG_SEARCH_ICON, Constants.SHORT_TIME);
			foundation.click(LandingPage.IMG_SEARCH_ICON);

			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
			foundation.click(ProductSearch.BTN_PRODUCT);
			foundation.objectFocus(order.objText(orderPageData.get(7)));
			foundation.click(order.objText(orderPageData.get(7)));
			foundation.waitforElement(AccountLogin.BTN_NEXT, Constants.SHORT_TIME);
			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(
					propertyFile.readPropertyFile(Configuration.V5_USER, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(AccountLogin.BTN_NEXT);
			foundation.waitforElement(AccountLogin.BTN_PIN_NEXT, Constants.SHORT_TIME);
			textBox.enterPin(rstV5DeviceData.get(CNV5Device.PIN));
			foundation.click(AccountLogin.BTN_PIN_NEXT);
			payments.verifyPaymentPageLanguage(rstV5DeviceData.get(CNV5Device.PAYMENTS_PAGE));
			browser.close();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Selecting location
			locationList.selectLocationName(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));
			dropDown.selectItem(LocationSummary.DPD_KIOSK_LANGUAGE, requiredData.get(1), Constants.TEXT);
			dropDown.selectItem(LocationSummary.DPD_ALTERNATE_LANGUAGE, requiredData.get(2), Constants.TEXT);
			foundation.click(LocationSummary.BTN_SYNC);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			foundation.waitforElementToDisappear(LocationList.TXT_SPINNER_MSG, Constants.EXTRA_LONG_TIME);
			login.logout();
		}
	}

	@Test(description = "141867- This test validates the Driver Login and Log Out")
	public void verifyDriverLoginLogout() {
		try {

			final String CASE_NUM = "141867";

			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			List<String> language = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			locationSummary.kiosklanguageSetting(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE),
					language.get(0), language.get(1));

			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			landingPage.changeLanguage(language.get(2), language.get(0), language.get(3));
			adminMenu.navigateDriverLoginPage();
			String pin = propertyFile.readPropertyFile(Configuration.V5_DRIVER_PIN, FilePath.PROPERTY_CONFIG_FILE);
			textBox.enterDriverPin(pin);
			foundation.click(AdminMenu.BTN_SIGN_IN);
			foundation.isDisplayed(AdminMenu.LINK_DRIVER_LOGOUT);
			foundation.click(AdminMenu.LINK_DRIVER_LOGOUT);
			foundation.isDisplayed(AdminMenu.BTN_SIGN_IN);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "141875-Kiosk Manage Account > Edit Account > Change PIN")
	public void editAccountChangePin() {
		try {
			final String CASE_NUM = "141875";

			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			List<String> requiredData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> language = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			locationSummary.kiosklanguageSetting(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE),
					language.get(0), language.get(1));
			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			landingPage.changeLanguage(language.get(2), language.get(0), language.get(3));

			foundation.click(LandingPage.BTN_LOGIN);
			foundation.click(AccountLogin.BTN_EMAIL_LOGIN);
			accountLogin.login(propertyFile.readPropertyFile(Configuration.V5_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.V5_PIN, FilePath.PROPERTY_CONFIG_FILE));

			// navigate to edit account and update pin
			foundation.click(EditAccount.BTN_EDIT_ACCOUNT);
			foundation.click(EditAccount.BTN_CHANGE_PIN);
			textBox.enterPin(requiredData.get(1));
			foundation.click(EditAccount.BTN_EDIT_NEXT);
			textBox.enterPin(requiredData.get(1));
			foundation.click(EditAccount.BTN_SAVE_PIN);
			CustomisedAssert.assertTrue(foundation.isDisplayed(EditAccount.BTN_EDIT_ACCOUNT));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// reset data
			foundation.click(EditAccount.BTN_EDIT_ACCOUNT);
			foundation.click(EditAccount.BTN_CHANGE_PIN);
			textBox.enterPin(rstV5DeviceData.get(CNV5Device.PIN));
			foundation.click(EditAccount.BTN_EDIT_NEXT);
			textBox.enterPin(rstV5DeviceData.get(CNV5Device.PIN));
			foundation.click(EditAccount.BTN_SAVE_PIN);
			CustomisedAssert.assertTrue(foundation.isDisplayed(EditAccount.BTN_EDIT_ACCOUNT));
		}
	}

	@Test(description = "141887-Kiosk Checkout UI > Canceling Cart")
	public void cancellingCart() {
		try {
			final String CASE_NUM = "141887";

			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			List<String> requiredData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> language = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			locationSummary.kiosklanguageSetting(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE),
					language.get(0), language.get(1));

			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			List<String> actualData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.ACTUAL_DATA).split(Constants.DELIMITER_TILD));
			// login to application
			landingPage.changeLanguage(language.get(2), language.get(0), language.get(3));
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(requiredData.get(1));
			foundation.click(ProductSearch.BTN_PRODUCT);
			CustomisedAssert.assertEquals(foundation.getText(Order.TXT_HEADER), actualData.get(0));
			CustomisedAssert.assertEquals(foundation.getText(Order.TXT_PRODUCT), actualData.get(1));

			// cancel order
			foundation.click(Order.BTN_CANCEL_ORDER);
			CustomisedAssert.assertTrue(foundation.isDisplayed(Order.LBL_ORDER_CANCELLED));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "141889-Kiosk Checkout UI > Taxes Applied")
	public void taxesApplied() {
		final String CASE_NUM = "141889";
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		List<String> menuItem = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> requiredData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		try {
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
			List<String> actualData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.ACTUAL_DATA).split(Constants.DELIMITER_TILD));
			List<String> language = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			locationSummary.kiosklanguageSetting(rstLocationListData.get(CNLocationList.LOCATION_NAME), language.get(0),
					language.get(1));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// select country and tax systems
			navigationBar.navigateToMenuItem(menuItem.get(2));
			dropDown.selectItem(OrgSummary.DPD_COUNTRY, requiredData.get(5), Constants.TEXT);
			dropDown.selectItem(OrgSummary.DPD_TAX_SYSTEM, requiredData.get(5), Constants.TEXT);
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
			dropDown.selectItem(ProductSummary.DPD_TAX_CATEGORY, requiredData.get(0), Constants.TEXT);
			dropDown.selectItem(ProductSummary.DPD_DEPOSIT_CATEGORY, requiredData.get(3), Constants.TEXT);
			foundation.click(ProductSummary.BTN_SAVE);

			// save tax mapping
			navigationBar.navigateToMenuItem(menuItem.get(1));
			foundation.waitforElement(LocationList.TXT_FILTER, Constants.SHORT_TIME);
			locationList.selectLocationName(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));
			locationSummary.saveTaxMapping(requiredData.get(0), requiredData.get(4));
			foundation.click(LocationSummary.BTN_SAVE);

			// sync machine
			locationSummary.kiosklanguageSetting(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE),
					language.get(0), language.get(1));

			// login to application
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			landingPage.changeLanguage(language.get(2), language.get(0), language.get(3));
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(requiredData.get(1));
			foundation.click(ProductSearch.BTN_PRODUCT);
			CustomisedAssert.assertEquals(foundation.getText(Order.TXT_HEADER), actualData.get(0));
			CustomisedAssert.assertEquals(foundation.getText(Order.TXT_PRODUCT), actualData.get(1));

			// verify the display of total section
			String productPrice = foundation.getText(Order.LBL_PRODUCT_PRICE).split(Constants.DOLLAR)[1];
			String tax = foundation.getText(Order.LBL_VAT_VALUE).split(Constants.DOLLAR)[1];
			String balanceDue = foundation.getText(Order.LBL_BALANCE_DUE).split(Constants.DOLLAR)[1];
			String subTotal = foundation.getText(Order.LBL_SUB_TOTAL).split(Constants.DOLLAR)[1];

			Double expectedBalanceDue = Double.parseDouble(productPrice) + Double.parseDouble(tax);

			CustomisedAssert.assertEquals(balanceDue, expectedBalanceDue.toString());
			CustomisedAssert.assertEquals(subTotal, productPrice);
			CustomisedAssert.assertEquals(tax, requiredData.get(2));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// reset data
			browser.close();
			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.navigateToMenuItem(menuItem.get(1));
			foundation.waitforElement(LocationList.TXT_FILTER, Constants.SHORT_TIME);
			locationList.selectLocationName(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));
			locationSummary.removeTaxMapping(requiredData.get(0));
			foundation.click(LocationSummary.BTN_SYNC);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_FILTER, Constants.SHORT_TIME);
		}
	}

	@Test(description = "141890-Kiosk Checkout UI > Bottle Deposits Applied")
	public void bottleDepositApplied() {
		final String CASE_NUM = "141890";
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		List<String> menuItem = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> requiredData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		try {
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			List<String> language = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
			List<String> actualData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.ACTUAL_DATA).split(Constants.DELIMITER_TILD));

			// add categories to products
			navigationBar.navigateToMenuItem(menuItem.get(0));
			foundation.threadWait(Constants.THREE_SECOND);
			globalProduct.selectGlobalProduct(requiredData.get(1));
			dropDown.selectItem(ProductSummary.DPD_DEPOSIT_CATEGORY, requiredData.get(0), Constants.TEXT);
			foundation.click(ProductSummary.BTN_SAVE);
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			locationSummary.kiosklanguageSetting(rstLocationListData.get(CNLocationList.LOCATION_NAME), language.get(0),
					language.get(1));
			// sync machine
			navigationBar.navigateToMenuItem(menuItem.get(1));
			locationSummary.kiosklanguageSetting(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE),
					language.get(0), language.get(1));

			// launch V5
			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);

			landingPage.changeLanguage(language.get(2), language.get(0), language.get(3));
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(requiredData.get(1));
			foundation.click(ProductSearch.BTN_PRODUCT);
			CustomisedAssert.assertEquals(foundation.getText(Order.TXT_HEADER), actualData.get(0));
			CustomisedAssert.assertEquals(foundation.getText(Order.TXT_PRODUCT), actualData.get(1));

			// verify the display of total section
			String productPrice = foundation.getText(Order.LBL_PRODUCT_PRICE).split(Constants.DOLLAR)[1];
			String deposit = foundation.getText(Order.LBL_DEPOSIT).split(Constants.DOLLAR)[1];

			CustomisedAssert.assertTrue(foundation.getText(Order.LBL_SUB_TOTAL).contains(productPrice));
			String balanceDue = foundation.getText(Order.LBL_BALANCE_DUE);
			String subTotal = foundation.getText(Order.LBL_SUB_TOTAL);
			String tax = "0.00";
			if (foundation.isDisplayed(Order.LBL_TAX))
				tax = foundation.getText(Order.LBL_TAX).split(Constants.DOLLAR)[1];
			Double expectedBalanceDue = Double.parseDouble(productPrice) + Double.parseDouble(deposit)
					+ Double.parseDouble(tax);
			String expBalanceDue = String.format("%.2f", expectedBalanceDue);
			CustomisedAssert.assertTrue(foundation.getText(Order.LBL_BALANCE_DUE).contains(expBalanceDue));
			CustomisedAssert.assertTrue(balanceDue.contains(expBalanceDue));
			CustomisedAssert.assertTrue(subTotal.contains(productPrice));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// reset data
			browser.close();
			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItem.get(0));
			foundation.threadWait(Constants.THREE_SECOND);
			globalProduct.selectGlobalProduct(requiredData.get(1));
			dropDown.selectItem(ProductSummary.DPD_DEPOSIT_CATEGORY, requiredData.get(2), Constants.TEXT);
			foundation.click(ProductSummary.BTN_SAVE);
		}
	}

	@Test(description = "142696-SOS-24494-V5 -validate the search functionality for product search")
	public void searchFunctionalityProduct() {
		try {
			final String CASE_NUM = "142696";
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			List<String> requiredData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> language = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			locationSummary.kiosklanguageSetting(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE),
					language.get(0), language.get(1));

			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);

			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			List<String> actualData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.ACTUAL_DATA).split(Constants.DELIMITER_TILD));

			// login to application
			landingPage.changeLanguage(language.get(2), language.get(0), language.get(3));
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(requiredData.get(1));
			CustomisedAssert.assertTrue(foundation.isDisplayed(ProductSearch.BTN_PRODUCT));
			textBox.deleteKeypadText(requiredData.get(1));
			CustomisedAssert.assertFalse(foundation.isDisplayed(ProductSearch.BTN_PRODUCT));
			textBox.enterKeypadText(requiredData.get(2));
			CustomisedAssert.assertTrue(foundation.getText(ProductSearch.LBL_PRODUCT_NAME).contains(actualData.get(1)));
			foundation.click(ProductSearch.BTN_PRODUCT);
			CustomisedAssert.assertEquals(foundation.getText(Order.TXT_HEADER), actualData.get(0));
			CustomisedAssert.assertEquals(foundation.getText(Order.TXT_PRODUCT), actualData.get(1));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "142697-SOS-24494-V5 -validate the search functionality for scan code search")
	public void searchFunctionalityScancode() {
		try {

			final String CASE_NUM = "142697";
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			List<String> requiredData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> language = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			locationSummary.kiosklanguageSetting(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE),
					language.get(0), language.get(1));

			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			List<String> actualData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.ACTUAL_DATA).split(Constants.DELIMITER_TILD));

			// login to application
			landingPage.changeLanguage(language.get(2), language.get(0), language.get(3));
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			foundation.click(ProductSearch.BTN_123);
			textBox.enterKeypadNumber(requiredData.get(1));
			CustomisedAssert.assertTrue(foundation.isDisplayed(ProductSearch.BTN_PRODUCT));
			textBox.deleteKeypadText(requiredData.get(1));
			CustomisedAssert.assertFalse(foundation.isDisplayed(ProductSearch.BTN_PRODUCT));
			textBox.enterKeypadNumber(requiredData.get(2));
			CustomisedAssert
					.assertTrue(foundation.getText(ProductSearch.LBL_PRODUCT_NAME).contains(requiredData.get(3)));
			foundation.click(ProductSearch.BTN_PRODUCT);
			CustomisedAssert.assertEquals(foundation.getText(Order.TXT_HEADER), actualData.get(0));
			CustomisedAssert.assertEquals(foundation.getText(Order.TXT_PRODUCT), actualData.get(1));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "142699-SOS-24494-V5 - update product name and verify in kiosk machine cart page")
	public void updateProductName() {
		final String CASE_NUM = "142699";

		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);

		List<String> requiredData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> menuItem = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		try {
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);

			List<String> language = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));

			// launch browser and select org
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// navigate to global product of V5 associated and update name and sync
			navigationBar.navigateToMenuItem(menuItem.get(0));
			foundation.waitforElementToDisappear(GlobalProduct.IMG_DATA_GRID_LOADING, Constants.EXTRA_LONG_TIME);
			foundation.waitforElement(GlobalProduct.TXT_FILTER, Constants.SHORT_TIME);
			textBox.enterText(GlobalProduct.TXT_FILTER, requiredData.get(0));
			foundation.click(globalProduct.getGlobalProduct(requiredData.get(1)));
			foundation.waitforElementToDisappear(GlobalProduct.IMG_DATA_GRID_LOADING, Constants.EXTRA_LONG_TIME);
			textBox.enterText(ProductSummary.TXT_PRODUCT_NAME, requiredData.get(2));
			foundation.click(ProductSummary.BTN_SAVE);
			foundation.waitforElement(GlobalProduct.IMG_DATA_GRID_LOADING, Constants.EXTRA_LONG_TIME);
			foundation.waitforElementToDisappear(GlobalProduct.IMG_DATA_GRID_LOADING, Constants.EXTRA_LONG_TIME);
			navigationBar.navigateToMenuItem(menuItem.get(1));
			locationSummary.kiosklanguageSetting(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE),
					language.get(0), language.get(1));

			// launch v5 application
			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));

			landingPage.changeLanguage(language.get(2), language.get(0), language.get(3));
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(requiredData.get(1));
			CustomisedAssert.assertFalse(foundation.isDisplayed(ProductSearch.BTN_PRODUCT));
			textBox.deleteKeypadText(requiredData.get(1));
			textBox.enterKeypadText(requiredData.get(2));
			CustomisedAssert
					.assertTrue(foundation.getText(ProductSearch.LBL_PRODUCT_NAME).contains(requiredData.get(2)));
			foundation.click(ProductSearch.BTN_PRODUCT);
			CustomisedAssert.assertEquals(foundation.getText(Order.TXT_HEADER), requiredData.get(4));
			CustomisedAssert.assertEquals(foundation.getText(Order.TXT_PRODUCT), requiredData.get(2));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// reset data
			browser.close();
			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItem.get(0));
			foundation.waitforElementToDisappear(GlobalProduct.IMG_DATA_GRID_LOADING, Constants.EXTRA_LONG_TIME);
			textBox.enterText(GlobalProduct.TXT_FILTER, requiredData.get(0));
			foundation.click(globalProduct.getGlobalProduct(requiredData.get(2)));
			textBox.enterText(ProductSummary.TXT_PRODUCT_NAME, requiredData.get(1));
			foundation.click(ProductSummary.BTN_SAVE);
		}
	}

	@Test(description = "142700-SOS-24494-V5 - update tax category and verify in kiosk machine cart page")
	public void updateTaxCategory() {
		final String CASE_NUM = "142700";
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		List<String> requiredData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> menuItem = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		try {

			List<String> language = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));

			// launch browser and select org
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.navigateToMenuItem(menuItem.get(2));
			dropDown.selectItem(OrgSummary.DPD_COUNTRY, requiredData.get(9), Constants.TEXT);
			dropDown.selectItem(OrgSummary.DPD_TAX_SYSTEM, requiredData.get(9), Constants.TEXT);
			foundation.click(OrgSummary.BTN_SAVE);
			foundation.waitforElement(OrgList.LBL_ORG_LIST, Constants.SHORT_TIME);

			// navigate to global product of V5 associated and update tax category and sync
			navigationBar.navigateToMenuItem(menuItem.get(0));
			foundation.threadWait(Constants.SHORT_TIME);
			textBox.enterText(GlobalProduct.TXT_FILTER, requiredData.get(0));
			foundation.click(globalProduct.getGlobalProduct(requiredData.get(1)));
			dropdown.selectItem(ProductSummary.DPD_TAX_CATEGORY, requiredData.get(2), Constants.TEXT);
			foundation.click(ProductSummary.BTN_SAVE);
			navigationBar.navigateToMenuItem(menuItem.get(2));
			locationSummary.kiosklanguageSetting(requiredData.get(3), language.get(0), language.get(1));
			navigationBar.navigateToMenuItem(menuItem.get(1));

			foundation.waitforElement(LocationList.TXT_FILTER, Constants.SHORT_TIME);
			locationList.selectLocationName(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));
			locationSummary.saveTaxMapping(requiredData.get(2), requiredData.get(8));

			foundation.click(LocationSummary.BTN_SAVE);
			locationSummary.kiosklanguageSetting(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE),
					language.get(0), language.get(1));

			// launch v5 application
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));

			landingPage.changeLanguage(language.get(2), language.get(0), language.get(3));
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(requiredData.get(1));
			foundation.click(ProductSearch.BTN_PRODUCT);
			CustomisedAssert.assertEquals(foundation.getText(Order.TXT_HEADER), requiredData.get(4));
			CustomisedAssert.assertEquals(foundation.getText(Order.TXT_PRODUCT), requiredData.get(1));
			CustomisedAssert.assertEquals(foundation.getText(Order.LBL_VAT_VALUE), requiredData.get(6));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// reset data
			browser.close();
			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItem.get(0));
			foundation.threadWait(Constants.SHORT_TIME);
			textBox.enterText(GlobalProduct.TXT_FILTER, requiredData.get(0));
			foundation.click(globalProduct.getGlobalProduct(requiredData.get(1)));
			dropdown.selectItem(ProductSummary.DPD_TAX_CATEGORY, requiredData.get(7), Constants.TEXT);
			foundation.click(ProductSummary.BTN_SAVE);
			navigationBar.navigateToMenuItem(menuItem.get(1));
			foundation.waitforElement(LocationList.TXT_FILTER, Constants.SHORT_TIME);
			locationList.selectLocationName(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(LocationSummary.LNK_TAX_MAPPING);
			foundation.click(locationSummary.objTaxCategory(requiredData.get(2)));
			foundation.waitforElement(LocationSummary.BTN_POPUP_REMOVE, Constants.SHORT_TIME);
			foundation.click(LocationSummary.BTN_POPUP_REMOVE);
			foundation.click(LocationSummary.LNK_TAX_MAPPING);
			foundation.click(LocationSummary.BTN_SYNC);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_FILTER, Constants.SHORT_TIME);
		}
	}

	@Test(description = "142993-V5-Ensure canadian Currency Cash Funding")
	public void verifyCADCashFunding() {
		final String CASE_NUM = "142993";

		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);

		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);

		List<String> menu = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> requiredData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> language = Arrays.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
		List<String> country = Arrays
				.asList(rstLocationSummaryData.get(CNLocationSummary.COUNTRY).split(Constants.DELIMITER_TILD));
		try {
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
			List<String> actualData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.ACTUAL_DATA).split(Constants.DELIMITER_TILD));

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menu.get(0));
			foundation.waitforElement(OrgSummary.DPD_CURRENCY, Constants.SHORT_TIME);
			dropDown.selectItem(OrgSummary.DPD_COUNTRY, country.get(0), Constants.TEXT);
			dropDown.selectItem(OrgSummary.DPD_CURRENCY, requiredData.get(0), Constants.TEXT);
			foundation.click(OrgSummary.BTN_SAVE);
			foundation.waitforElement(OrgList.LBL_ORG_LIST, Constants.SHORT_TIME);
			navigationBar.navigateToMenuItem(menu.get(1));
			locationList.selectLocationName(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));
			dropDown.selectItem(LocationSummary.DPD_KIOSK_LANGUAGE, actualData.get(0), Constants.TEXT);
			dropDown.selectItem(LocationSummary.DPD_ALTERNATE_LANGUAGE, actualData.get(1), Constants.TEXT);
			foundation.click(LocationSummary.BTN_SYNC);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_FILTER, Constants.SHORT_TIME);
			foundation.waitforElementToDisappear(LocationList.TXT_SPINNER_MSG, Constants.EXTRA_LONG_TIME);
			login.logout();
			browser.close();

			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));

			landingPage.changeLanguage(language.get(2), language.get(0), language.get(3));
			foundation.click(LandingPage.LBL_ACCOUNT_LOGIN);
			foundation.click(AccountLogin.BTN_EMAIL_LOGIN);
			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(
					propertyFile.readPropertyFile(Configuration.V5_USER, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(AccountLogin.BTN_NEXT);
			foundation.waitforElement(AccountLogin.BTN_PIN_NEXT, Constants.SHORT_TIME);
			textBox.enterPin(propertyFile.readPropertyFile(Configuration.V5_PIN, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(AccountLogin.BTN_PIN_NEXT);
			foundation.click(AccountDetails.BTN_FUND_CASH);
			CustomisedAssert.assertTrue(foundation.isDisplayed(fundAccount.objText(requiredData.get(2))));
			foundation.click(fundAccount.objText(requiredData.get(3)));
			CustomisedAssert.assertTrue(foundation.isDisplayed(AccountDetails.BTN_FUND_CASH));
			browser.close();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// reset the data
			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menu.get(0));
			foundation.waitforElement(OrgSummary.DPD_CURRENCY, Constants.SHORT_TIME);
			dropDown.selectItem(OrgSummary.DPD_COUNTRY, country.get(1), Constants.TEXT);
			dropDown.selectItem(OrgSummary.DPD_CURRENCY, requiredData.get(1), Constants.TEXT);
			foundation.click(OrgSummary.BTN_SAVE);
			foundation.waitforElement(OrgList.LBL_ORG_LIST, Constants.SHORT_TIME);
			navigationBar.navigateToMenuItem(menu.get(1));
			locationList.selectLocationName(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(LocationSummary.BTN_SYNC);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_FILTER, Constants.SHORT_TIME);
		}
	}

	@Test(description = "142717-SOS-24494-V5 - Assign Deposit value of product and verify it on Kiosk machine cart page")
	public void assignDepositValue() {
		final String CASE_NUM = "142717";
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		List<String> requiredData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> menuItem = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));

		try {
			List<String> language = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));

			// launch browser and select org
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// navigate to global product of V5 associated and update deposit and sync
			navigationBar.navigateToMenuItem(menuItem.get(0));
			foundation.threadWait(Constants.SHORT_TIME);
			textBox.enterText(GlobalProduct.TXT_FILTER, requiredData.get(0));
			foundation.click(globalProduct.getGlobalProduct(requiredData.get(1)));
			dropdown.selectItem(ProductSummary.DPD_DEPOSIT_CATEGORY, requiredData.get(2), Constants.TEXT);
			foundation.click(ProductSummary.BTN_SAVE);
			navigationBar.navigateToMenuItem(menuItem.get(1));
			locationSummary.kiosklanguageSetting(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE),
					language.get(0), language.get(1));

			// launch v5 application
			foundation.threadWait(Constants.MEDIUM_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));

			landingPage.changeLanguage(language.get(2), language.get(0), language.get(3));
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(requiredData.get(1));
			CustomisedAssert
					.assertTrue(foundation.getText(ProductSearch.LBL_PRODUCT_NAME).contains(requiredData.get(1)));
			foundation.click(ProductSearch.BTN_PRODUCT);
			CustomisedAssert.assertEquals(foundation.getText(Order.TXT_HEADER), requiredData.get(4));
			CustomisedAssert.assertEquals(foundation.getText(Order.TXT_PRODUCT), requiredData.get(1));
			CustomisedAssert.assertEquals(foundation.getText(Order.LBL_DEPOSIT), requiredData.get(6));

			browser.close();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItem.get(0));
			foundation.threadWait(Constants.SHORT_TIME);
			textBox.enterText(GlobalProduct.TXT_FILTER, requiredData.get(0));
			foundation.click(globalProduct.getGlobalProduct(requiredData.get(1)));
			dropdown.selectItem(ProductSummary.DPD_DEPOSIT_CATEGORY, requiredData.get(7), Constants.TEXT);
			foundation.click(ProductSummary.BTN_SAVE);
		}
	}

	@Test(description = "142718-SOS-24494-V5 -Edit cost/price of the product and verify it on Kiosk machine cart page")
	public void editPrice() {
		final String CASE_NUM = "142718";

		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		List<String> requiredData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		String menuItem = rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM);

		try {
			// launch browser and select org
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// navigate to location summary and update price and sync
			navigationBar.navigateToMenuItem(menuItem);
			textBox.enterText(LocationList.TXT_FILTER,
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));
			locationList.selectLocationName(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));
			locationSummary.selectTab(requiredData.get(8));
			foundation.threadWait(Constants.TWO_SECOND);
			textBox.enterText(LocationSummary.TXT_PRODUCT_FILTER, requiredData.get(1));
			locationSummary.enterPrice(requiredData.get(0), requiredData.get(2));
			foundation.click(LocationSummary.BTN_SAVE);
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
			textBox.enterKeypadText(requiredData.get(1));
			foundation.click(ProductSearch.BTN_PRODUCT);
			CustomisedAssert.assertEquals(foundation.getText(Order.TXT_HEADER), requiredData.get(4));
			CustomisedAssert.assertEquals(foundation.getText(Order.LBL_PRODUCT_PRICE), requiredData.get(6));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// reset data
			browser.close();
			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItem);
			textBox.enterText(LocationList.TXT_FILTER,
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));
			locationList.selectLocationName(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));
			locationSummary.selectTab(requiredData.get(8));
			foundation.threadWait(Constants.TWO_SECOND);
			textBox.enterText(LocationSummary.TXT_PRODUCT_FILTER, requiredData.get(1));
			locationSummary.enterPrice(requiredData.get(0), requiredData.get(7));
			foundation.click(LocationSummary.BTN_SAVE);
		}
	}

	@Test(description = "142722-SOS-24494-V5 - Apply 'is disabled' for product and verify it on Kiosk machine cart page")
	public void applyIsDisabled() {
		final String CASE_NUM = "142722";

		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		List<String> requiredData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> menuItem = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		try {
			// launch browser and select org
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// navigate to global product of V5 associated and update name and sync
			navigationBar.navigateToMenuItem(menuItem.get(0));
			foundation.threadWait(Constants.TWO_SECOND);
			foundation.click(GlobalProduct.ICON_FILTER);
			globalProduct.selectFilter(requiredData.get(6));
			textBox.enterText(GlobalProduct.TXT_FILTER, requiredData.get(0));
			foundation.click(globalProduct.getGlobalProduct(requiredData.get(1)));
			dropdown.selectItem(ProductSummary.DPD_IS_DISABLED, requiredData.get(2), Constants.TEXT);
			foundation.click(ProductSummary.BTN_SAVE);
			foundation.waitforElement(GlobalProduct.TXT_FILTER, 3);
			navigationBar.navigateToMenuItem(menuItem.get(1));
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
			textBox.enterKeypadText(requiredData.get(1));
			CustomisedAssert.assertFalse(foundation.isDisplayed(ProductSearch.BTN_PRODUCT));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// reset data- enable back product
			browser.close();
			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItem.get(0));
			foundation.threadWait(Constants.TWO_SECOND);
			foundation.click(GlobalProduct.ICON_FILTER);
			globalProduct.selectFilter(requiredData.get(6));
			textBox.enterText(GlobalProduct.TXT_FILTER, requiredData.get(0));
			foundation.click(globalProduct.getGlobalProduct(requiredData.get(1)));
			dropdown.selectItem(ProductSummary.DPD_IS_DISABLED, requiredData.get(5), Constants.TEXT);
			foundation.click(ProductSummary.BTN_SAVE);

			// reset data-assign back the product to location
			navigationBar.navigateToMenuItem(menuItem.get(1));
			textBox.enterText(LocationList.TXT_FILTER,
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));
			locationList.selectLocationName(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));
			locationSummary.selectTab(requiredData.get(7));
			foundation.waitforElement(LocationSummary.BTN_ADD_PRODUCT, 2);
			locationSummary.addProduct(requiredData.get(0));
			foundation.click(LocationSummary.BTN_FULL_SYNC);
		}
	}

	@Test(description = "141868-This test validates the Inventory options")
	public void verifyInventoryOptions() {
		try {
			final String CASE_NUM = "141868";

			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);

			List<String> language = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			locationSummary.kiosklanguageSetting(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE),
					language.get(0), language.get(1));

			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));

			landingPage.changeLanguage(language.get(2), language.get(0), language.get(3));
			adminMenu.navigateDriverLoginPage();
			String pin = propertyFile.readPropertyFile(Configuration.V5_DRIVER_PIN, FilePath.PROPERTY_CONFIG_FILE);
			textBox.enterPin(pin);
			foundation.click(AdminMenu.BTN_SIGN_IN);
			foundation.isDisplayed(AdminMenu.LINK_DRIVER_LOGOUT);
			foundation.click(AdminMenu.LINK_INVENTORY);
			List<String> optionNames = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			adminMenu.verifyOptions(optionNames);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "141873- Verify the products as per the user search")
	public void verifyProductSearch() {
		try {

			final String CASE_NUM = "141873";
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);

			List<String> requiredData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> language = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			locationSummary.kiosklanguageSetting(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE),
					language.get(0), language.get(1));

			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			landingPage.changeLanguage(language.get(2), language.get(0), language.get(3));

			foundation.click(LandingPage.IMG_SEARCH_ICON);
			List<String> productName = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME).split(Constants.DELIMITER_TILD));
			textBox.enterKeypadText(productName.get(1));
			String prodCount = foundation.getText(ProductSearch.LBL_PROD_COUNT);
			String prodFoundText = foundation.getText(ProductSearch.LBL_PROD_MESSAGE);
			String unMatchedProdMsg = prodCount + " " + prodFoundText;
			CustomisedAssert.assertEquals(unMatchedProdMsg, requiredData.get(1));
			foundation.click(ProductSearch.LINK_CLOSE_POPUP);
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(productName.get(0));
			String matchedCount = foundation.getText(ProductSearch.LBL_PROD_COUNT);
			String matchedProdMsg = matchedCount + " " + prodFoundText;
			CustomisedAssert.assertEquals(matchedProdMsg, requiredData.get(0));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "141872-This test validates the item in the Your order screen")
	public void verifyItemInOrderScreen() {
		try {

			final String CASE_NUM = "141872";
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);

			List<String> language = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			locationSummary.kiosklanguageSetting(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE),
					language.get(0), language.get(1));

			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			landingPage.changeLanguage(language.get(2), language.get(0), language.get(3));

			// search and click product
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
			foundation.click(ProductSearch.BTN_PRODUCT);
			CustomisedAssert.assertEquals(foundation.getText(Order.TXT_HEADER),
					rstV5DeviceData.get(CNV5Device.REQUIRED_DATA));
			CustomisedAssert.assertEquals(foundation.getText(Order.TXT_PRODUCT),
					rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "142663- This test validates the functionality of Cancel order")

	public void verifyCancelOrderFunctionality() {
		try {

			final String CASE_NUM = "142663";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);

			List<String> language = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			locationSummary.kiosklanguageSetting(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE),
					language.get(0), language.get(1));
			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			landingPage.changeLanguage(language.get(2), language.get(0), language.get(3));
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
			foundation.click(ProductSearch.BTN_PRODUCT);
			CustomisedAssert.assertTrue(foundation.isDisplayed(Order.BTN_CANCEL_ORDER));
			foundation.click(Order.BTN_CANCEL_ORDER);
			CustomisedAssert.assertTrue(foundation.isDisplayed(Order.LBL_ORDER_CANCELLED));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LandingPage.IMG_SEARCH_ICON));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "142664- This test validates the Yes button functionality on Order Screen")
	public void verifyYesButtonFunctionality() {
		try {

			final String CASE_NUM = "142664";
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			deviceSummary.setTime(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE),
					rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION),
					rstV5DeviceData.get(CNV5Device.TIMEOUT_POPUP),
					rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			List<String> language = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
			locationSummary.kiosklanguageSetting(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE),
					language.get(0), language.get(1));

			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);

			landingPage.changeLanguage(language.get(2), language.get(0), language.get(3));
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
			foundation.click(ProductSearch.BTN_PRODUCT);
			CustomisedAssert.assertTrue(foundation.isDisplayed(Order.BTN_CANCEL_ORDER));
			foundation.waitforElement(Order.POP_UP_LBL_ORDER_TIMEOUT, Constants.LONG_TIME);
			foundation.click(Order.POP_UP_TIMEOUT_YES);
			CustomisedAssert.assertTrue(foundation.isDisplayed(Order.LBL_YOUR_ORDER));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "142665- This test validates the No button functionality on Order Screen")
	public void verifyNoButtonFunctionality() {
		try {

			final String CASE_NUM = "142665";

			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			deviceSummary.setTime(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE),
					rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION),
					rstV5DeviceData.get(CNV5Device.TIMEOUT_POPUP),
					rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			List<String> language = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			locationSummary.kiosklanguageSetting(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE),
					language.get(0), language.get(1));
			foundation.threadWait(Constants.SHORT_TIME);

			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);

			landingPage.changeLanguage(language.get(2), language.get(0), language.get(3));
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
			foundation.click(ProductSearch.BTN_PRODUCT);
			CustomisedAssert.assertTrue(foundation.isDisplayed(Order.BTN_CANCEL_ORDER));

			foundation.waitforElement(Order.POP_UP_LBL_ORDER_TIMEOUT, Constants.LONG_TIME);
			foundation.click(Order.POP_UP_TIMEOUT_NO);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LandingPage.IMG_SEARCH_ICON));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "142666- This test validates the Order Time out prompt will disapper after 5 sec")
	public void verifyPromptAfterTimeOut() {
		try {

			final String CASE_NUM = "142666";

			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			deviceSummary.setTime(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE),
					rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION),
					rstV5DeviceData.get(CNV5Device.TIMEOUT_POPUP),
					rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			List<String> language = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			locationSummary.kiosklanguageSetting(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE),
					language.get(0), language.get(1));
			foundation.threadWait(Constants.SHORT_TIME);

			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			landingPage.changeLanguage(language.get(2), language.get(0), language.get(3));

			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
			foundation.click(ProductSearch.BTN_PRODUCT);
			CustomisedAssert.assertTrue(foundation.isDisplayed(Order.BTN_CANCEL_ORDER));
			foundation.waitforElement(Order.POP_UP_LBL_ORDER_TIMEOUT, Constants.LONG_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(Order.POP_UP_LBL_ORDER_TIMEOUT));
			foundation.waitforElementToDisappear(Order.POP_UP_LBL_ORDER_TIMEOUT, Constants.EXTRA_LONG_TIME);
			foundation.waitforElement(LandingPage.IMG_SEARCH_ICON, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LandingPage.IMG_SEARCH_ICON));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}

	}

	@Test(description = "142680- This test validates the Order Timeout Prompt when user not  perform any action")
	public void verifyOrderTimePromptDetails() {
		try {

			final String CASE_NUM = "142680";

			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			deviceSummary.setTime(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE),
					rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION),
					rstV5DeviceData.get(CNV5Device.TIMEOUT_POPUP),
					rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			List<String> language = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			locationSummary.kiosklanguageSetting(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE),
					language.get(0), language.get(1));
			foundation.threadWait(Constants.SHORT_TIME);

			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			landingPage.changeLanguage(language.get(2), language.get(0), language.get(3));
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
			foundation.click(ProductSearch.BTN_PRODUCT);
			CustomisedAssert.assertTrue(foundation.isDisplayed(Order.BTN_CANCEL_ORDER));
			foundation.waitforElement(Order.POP_UP_LBL_ORDER_TIMEOUT, Constants.EXTRA_LONG_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(Order.POP_UP_LBL_ORDER_TIMEOUT));
			CustomisedAssert.assertTrue(foundation.isDisplayed(Order.POP_UP_LBL_ORDER_TIMEOUT_MSG));
			CustomisedAssert.assertTrue(foundation.isDisplayed(Order.POP_UP_TIMEOUT_YES));
			CustomisedAssert.assertTrue(foundation.isDisplayed(Order.POP_UP_TIMEOUT_NO));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}

	}

	@Test(description = "142729- This test validates the Order time out prompt when user decreases the time below 20 sec")
	public void verifyPromptWithBelow20Sec() {
		try {

			final String CASE_NUM = "142729";

			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			deviceSummary.setTime(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE),
					rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION),
					rstV5DeviceData.get(CNV5Device.TIMEOUT_POPUP),
					rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			List<String> language = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			locationSummary.kiosklanguageSetting(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE),
					language.get(0), language.get(1));

			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			landingPage.changeLanguage(language.get(2), language.get(0), language.get(3));
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
			foundation.click(ProductSearch.BTN_PRODUCT);
			CustomisedAssert.assertTrue(foundation.isDisplayed(Order.BTN_CANCEL_ORDER));
			foundation.threadWait(Constants.FIFTEEN_SECOND);
			CustomisedAssert.assertTrue(foundation.isDisplayed(Order.LBL_YOUR_ORDER));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "141863-Kiosk Account Login > Email")
	public void verifyLoginScreen() {
		try {

			final String CASE_NUM = "141863";

			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			List<String> language = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
			locationSummary.kiosklanguageSetting(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE),
					language.get(0), language.get(1));

			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			landingPage.changeLanguage(language.get(2), language.get(0), language.get(3));
			foundation.click(LandingPage.BTN_LOGIN);
			foundation.click(AccountLogin.BTN_EMAIL_LOGIN);
			accountLogin.login(propertyFile.readPropertyFile(Configuration.V5_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.V5_PIN, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(UserProfile.BTN_PRIVACY));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "141886-Kiosk Checkout UI > Cart Contents")
	public void verifyOrderScreen() {
		try {
			final String CASE_NUM = "141886";

			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			List<String> language = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
			locationSummary.kiosklanguageSetting(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE),
					language.get(0), language.get(1));

			List<String> requiredData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> actualData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.ACTUAL_DATA).split(Constants.DELIMITER_TILD));

			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			landingPage.changeLanguage(language.get(2), language.get(0), language.get(3));
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText((requiredData.get(1)));
			foundation.click(LandingPage.BTN_PRODUCT);
			String actualHeader = foundation.getText(LandingPage.TXT_HEADER);
			CustomisedAssert.assertEquals(actualHeader, actualData.get(1));
			String actualProduct = foundation.getText(LandingPage.TXT_PRODUCT);
			CustomisedAssert.assertEquals(actualProduct, actualData.get(0));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "142690-SOS-24493-Verify added Home Commercial Image(JPG) is displayed on V5 Device")
	public void verifyHomeCommercialJPG() {
		final String CASE_NUM = "142690";
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
		String locationName = propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1,
				FilePath.PROPERTY_CONFIG_FILE);
		final String imageName = string.getRandomCharacter();
		try {

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);

			String requiredData = rstV5DeviceData.get(CNV5Device.REQUIRED_DATA);
			List<String> language = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
			// Selecting Device's location
			locationList.selectLocationName(locationName);
			locationSummary.addHomeCommercial(imageName, FilePath.IMAGE_PATH);
			locationSummary.kiosklanguageSetting(locationName, language.get(0), language.get(1));

			// launching v5 device
			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			foundation.waitforElement(landingPage.objImageDisplay(requiredData), Constants.MEDIUM_TIME);
			String actualData = foundation.getTextAttribute(LandingPage.LNK_IMAGE, Constants.SRC);
			// Home Commercial image validation
			CustomisedAssert.assertEquals(actualData, requiredData);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// resetting test data
			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			// Selecting Device's location
			locationList.selectLocationName(locationName);
			locationSummary.removeHomeCommercial(imageName);
		}
	}

	@Test(description = "142691-SOS-24493-Verify added Home Commercial Image(PNG) is displayed on V5 Device")
	public void verifyHomeCommercialPNG() {
		final String CASE_NUM = "142691";
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
		String locationName = propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1,
				FilePath.PROPERTY_CONFIG_FILE);
		final String imageName = string.getRandomCharacter();
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);

			String requiredData = rstV5DeviceData.get(CNV5Device.REQUIRED_DATA);
			List<String> language = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));

			locationList.selectLocationName(locationName);
			locationSummary.addHomeCommercial(imageName, FilePath.IMAGE_PNG_PATH);
			locationSummary.kiosklanguageSetting(locationName, language.get(0), language.get(1));

			// launching v5 device
			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			foundation.refreshPage();
			foundation.waitforElement(landingPage.objImageDisplay(requiredData), Constants.MEDIUM_TIME);
			String actualData = foundation.getTextAttribute(LandingPage.LNK_IMAGE, Constants.SRC);
			// Home Commercial image validation
			CustomisedAssert.assertEquals(actualData, requiredData);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// resetting test data
			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			locationList.selectLocationName(locationName);
			locationSummary.removeHomeCommercial(imageName);
		}
	}

	@Test(description = "142692-SOS-24493-Verify removed Home Commercial Image(JPG) is not display in V5 Device.")
	public void verifyRemoveHomeCommercialJPG() {
		try {
			final String CASE_NUM = "142692";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
			String locationName = propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1,
					FilePath.PROPERTY_CONFIG_FILE);
			final String imageName = string.getRandomCharacter();
			String requiredData = rstV5DeviceData.get(CNV5Device.REQUIRED_DATA);
			List<String> language = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));

			// Selecting location
			locationList.selectLocationName(locationName);
			locationSummary.addHomeCommercial(imageName, FilePath.IMAGE_PATH);
			locationSummary.kiosklanguageSetting(locationName, language.get(0), language.get(1));

			// launching v5 device
			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			foundation.waitforElement(landingPage.objImageDisplay(requiredData), Constants.MEDIUM_TIME);
			String actualData = foundation.getTextAttribute(LandingPage.LNK_IMAGE, Constants.SRC);
			CustomisedAssert.assertEquals(actualData, requiredData);
			browser.close();

			// Remove home commercial
			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			locationList.selectLocationName(locationName);
			locationSummary.removeHomeCommercial(imageName);
			login.logout();
			browser.close();

			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertFalse(foundation.isDisplayed(landingPage.objImageDisplay(requiredData)));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "142693-SOS-24493-Verify removed Home Commercial Image(PNG) is not display in V5 Device.")
	public void verifyRemoveHomeCommercialPNG() {
		try {
			final String CASE_NUM = "142693";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
			String locationName = propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1,
					FilePath.PROPERTY_CONFIG_FILE);
			final String imageName = string.getRandomCharacter();
			String requiredData = rstV5DeviceData.get(CNV5Device.REQUIRED_DATA);
			List<String> language = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));

			// Selecting location
			locationList.selectLocationName(locationName);
			locationSummary.addHomeCommercial(imageName, FilePath.IMAGE_PNG_PATH);
			locationSummary.kiosklanguageSetting(locationName, language.get(0), language.get(1));

			// launching v5 device
			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			foundation.refreshPage();

			foundation.waitforElement(landingPage.objImageDisplay(requiredData), Constants.LONG_TIME);
			String actualData = foundation.getTextAttribute(LandingPage.LNK_IMAGE, Constants.SRC);
			CustomisedAssert.assertEquals(actualData, requiredData);
			browser.close();

			// Remove home commercial
			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			locationList.selectLocationName(locationName);
			locationSummary.removeHomeCommercial(imageName);
			login.logout();
			browser.close();

			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertFalse(foundation.isDisplayed(landingPage.objImageDisplay(requiredData)));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "142694-SOS-24493- verify default home commercial image is displayed when all the existing images are removed from location summary in Adm")
	public void verifyDefaultHomeCommercial() {
		try {
			final String CASE_NUM = "142694";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
			String locationName = propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1,
					FilePath.PROPERTY_CONFIG_FILE);
			final String cmrName = string.getRandomCharacter();
			List<String> requiredData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> language = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));

			locationList.selectLocationName(locationName);
			locationSummary.addHomeCommercial(cmrName, FilePath.IMAGE_PNG_PATH);
			locationSummary.kiosklanguageSetting(locationName, language.get(0), language.get(1));

			// launching v5 device
			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			foundation.refreshPage();
			foundation.waitforElement(landingPage.objImageDisplay(requiredData.get(1)), Constants.MEDIUM_TIME);
			String actualData = foundation.getTextAttribute(LandingPage.LNK_IMAGE, Constants.SRC);
			CustomisedAssert.assertEquals(actualData, requiredData.get(1));
			browser.close();

			// Remove home commercial
			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			locationList.selectLocationName(locationName);
			locationSummary.removeHomeCommercial(cmrName);
			login.logout();
			browser.close();

			// v5 device
			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			foundation.waitforElement(landingPage.objImageDisplay(requiredData.get(0)), Constants.MEDIUM_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(landingPage.objImageDisplay(requiredData.get(0))));
			CustomisedAssert.assertFalse(foundation.isDisplayed(landingPage.objImageDisplay(requiredData.get(1))));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "142695-SOS-24493-verify multiple home commercials are displayed on V5 Device when multiple homecommercials are uploaded in Adm")
	public void verifyMultipleHomeCommercial() {
		final String CASE_NUM = "142695";
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
		String locationName = propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1,
				FilePath.PROPERTY_CONFIG_FILE);
		final String cmrName1 = string.getRandomCharacter();
		final String cmrName2 = string.getRandomCharacter();
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
			List<String> requiredData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> language = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));

			// Selecting location
			locationList.selectLocationName(locationName);
			locationSummary.addHomeCommercial(cmrName1, FilePath.IMAGE_PATH);
			locationList.selectLocationName(locationName);
			locationSummary.addHomeCommercial(cmrName2, FilePath.IMAGE_PNG_PATH);
			locationSummary.kiosklanguageSetting(locationName, language.get(0), language.get(1));

			// launching v5 device
			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			foundation.waitforElement(landingPage.objImageDisplay(requiredData.get(0)), Constants.MEDIUM_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(landingPage.objImageDisplay(requiredData.get(0))));
			foundation.waitforElement(landingPage.objImageDisplay(requiredData.get(1)), Constants.MEDIUM_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(landingPage.objImageDisplay(requiredData.get(1))));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// resetting test data
			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			locationList.selectLocationName(locationName);
			locationSummary.removeHomeCommercial(cmrName1);
			locationSummary.removeHomeCommercial(cmrName2);
		}
	}

	@Test(description = "142720-SOS-24493-Verify error message is displayed when home commercial image is uploaded in JSON format")
	public void verifyHomeCommercialJSON() {
		try {
			final String CASE_NUM = "142720";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
			String locationName = propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1,
					FilePath.PROPERTY_CONFIG_FILE);
			String actualData = rstV5DeviceData.get(CNV5Device.ACTUAL_DATA);

			// Selecting location
			locationList.selectLocationName(locationName);

			// upload image-1
			foundation.waitforElement(LocationSummary.BTN_HOME_COMMERCIAL, Constants.SHORT_TIME);
			foundation.click(LocationSummary.BTN_HOME_COMMERCIAL);
			foundation.click(LocationSummary.BTN_ADD_HOME_COMMERCIAL);
			foundation.click(LocationSummary.TXT_UPLOAD_NEW);
			textBox.enterText(LocationSummary.BTN_UPLOAD_INPUT, FilePath.JSON_SALES_CREATION);
			foundation.waitforElement(LocationSummary.TXT_UPLOAD_STATUS, Constants.SHORT_TIME);
			String expectedData = foundation.getText(LocationSummary.TXT_UPLOAD_STATUS);
			CustomisedAssert.assertEquals(expectedData, actualData);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "142723-SOS-24493-Verify error message is displayed when home commercial image is uploaded in Text format")
	public void verifyHomeCommercialText() {
		try {
			final String CASE_NUM = "142723";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
			String locationName = propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1,
					FilePath.PROPERTY_CONFIG_FILE);
			String actualData = rstV5DeviceData.get(CNV5Device.ACTUAL_DATA);

			// Selecting location
			textBox.enterText(LocationList.TXT_FILTER, locationName);
			locationList.selectLocationName(locationName);

			// upload image-1
			foundation.waitforElement(LocationSummary.BTN_HOME_COMMERCIAL, Constants.SHORT_TIME);
			foundation.click(LocationSummary.BTN_HOME_COMMERCIAL);
			foundation.click(LocationSummary.BTN_ADD_HOME_COMMERCIAL);
			foundation.click(LocationSummary.TXT_UPLOAD_NEW);
			textBox.enterText(LocationSummary.BTN_UPLOAD_INPUT, FilePath.IMAGE_TEXT_PATH);
			foundation.waitforElement(locationSummary.objUploadStatus(actualData), Constants.SHORT_TIME);
			String expectedData = foundation.getText(LocationSummary.TXT_UPLOAD_STATUS);
			CustomisedAssert.assertEquals(expectedData, actualData);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "142724-SOS-24493-Verify error message is displayed when uploaded home commercial image size is more than 2MB")
	public void verifyHomeCommercialImageSize() {
		try {
			final String CASE_NUM = "142724";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
			String locationName = propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1,
					FilePath.PROPERTY_CONFIG_FILE);
			String actualData = rstV5DeviceData.get(CNV5Device.ACTUAL_DATA);

			// Selecting location
			textBox.enterText(LocationList.TXT_FILTER, locationName);
			locationList.selectLocationName(locationName);

			// upload image-1
			foundation.waitforElement(LocationSummary.BTN_HOME_COMMERCIAL, Constants.SHORT_TIME);
			foundation.click(LocationSummary.BTN_HOME_COMMERCIAL);
			foundation.click(LocationSummary.BTN_ADD_HOME_COMMERCIAL);
			foundation.click(LocationSummary.TXT_UPLOAD_NEW);
			textBox.enterText(LocationSummary.BTN_UPLOAD_INPUT, FilePath.IMAGE_SIZE_MORE);

			// foundation.waitforElement(locationSummary.objUploadStatus(actualData),
			// Constants.SHORT_TIME);
			String expectedData = foundation.getAlertMessage();
			;
			CustomisedAssert.assertEquals(expectedData, actualData);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "142725-SOS-24493-Verify error message is displayed when uploaded home commercial image pixel length is greater than 1024x520")
	public void verifyHomeCommercialPixelLength() {
		try {
			final String CASE_NUM = "142725";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			// Reading test data from DataBase
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
			String locationName = propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1,
					FilePath.PROPERTY_CONFIG_FILE);
			String actualData = rstV5DeviceData.get(CNV5Device.ACTUAL_DATA);

			// Selecting location
			textBox.enterText(LocationList.TXT_FILTER, locationName);
			locationList.selectLocationName(locationName);

			// upload image-1
			foundation.waitforElement(LocationSummary.BTN_HOME_COMMERCIAL, Constants.SHORT_TIME);
			foundation.click(LocationSummary.BTN_HOME_COMMERCIAL);
			foundation.click(LocationSummary.BTN_ADD_HOME_COMMERCIAL);
			foundation.click(LocationSummary.TXT_UPLOAD_NEW);
			textBox.enterText(LocationSummary.BTN_UPLOAD_INPUT, FilePath.IMAGE_SIZE_MORE);

			foundation.waitforElement(locationSummary.objUploadStatus(actualData), Constants.SHORT_TIME);
			// String expectedData = foundation.getText(LocationSummary.TXT_UPLOAD_STATUS);
			// CustomisedAssert.assertEquals(expectedData, actualData);
			String expectedData = foundation.getAlertMessage();
			// foundation.waitforElement(locationSummary.objUploadStatus(actualData),
			// Constants.SHORT_TIME);
			// String expectedData = foundation.getText(LocationSummary.TXT_UPLOAD_STATUS);
			CustomisedAssert.assertEquals(expectedData, actualData);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "141857-Kiosk 'Default' Landing UI > Language Selection")
	public void verifyLanguageSelection() {
		try {
			final String CASE_NUM = "141857";
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			List<String> actualData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.ACTUAL_DATA).split(Constants.DELIMITER_TILD));
			List<String> requiredData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> language = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Selecting location
			locationList.selectLocationName(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));

			dropDown.selectItem(LocationSummary.DPD_KIOSK_LANGUAGE, language.get(0), Constants.TEXT);
			dropDown.selectItem(LocationSummary.DPD_ALTERNATE_LANGUAGE, language.get(1), Constants.TEXT);

			foundation.click(LocationSummary.BTN_SYNC);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			foundation.waitforElementToDisappear(LocationList.TXT_SPINNER_MSG, Constants.EXTRA_LONG_TIME);
			login.logout();
			browser.close();

			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));

			landingPage.changeLanguage(language.get(2), language.get(0), language.get(3));
			foundation.waitforElement(landingPage.objText(actualData.get(0)), Constants.MEDIUM_TIME);
			String actualLanguage = foundation.getText(LandingPage.LBL_HEADER);
			CustomisedAssert.assertEquals(actualLanguage, actualData.get(0));
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(landingPage.objText(language.get(2)));
			foundation.click(landingPage.objLanguage(requiredData.get(1)));
			foundation.waitforElement(landingPage.objText(language.get(4)), Constants.SHORT_TIME);
			foundation.click(landingPage.objText(language.get(4)));
			foundation.waitforElement(landingPage.objText(actualData.get(1)), Constants.SHORT_TIME);
			actualLanguage = foundation.getText(LandingPage.LBL_HEADER);
			CustomisedAssert.assertEquals(actualLanguage, actualData.get(1));
			browser.close();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "141881- Kiosk Privacy Policy (if applicable)")
	public void verifyPrivacyPolicy() {
		try {
			final String CASE_NUM = "141881";

			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			List<String> language = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
			locationSummary.kiosklanguageSetting(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE),
					language.get(0), language.get(1));

			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			landingPage.changeLanguage(language.get(2), language.get(0), language.get(3));
			foundation.click(LandingPage.BTN_LOGIN);
			foundation.click(AccountLogin.BTN_EMAIL_LOGIN);
			accountLogin.login(propertyFile.readPropertyFile(Configuration.V5_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.V5_PIN, FilePath.PROPERTY_CONFIG_FILE));

			foundation.click(UserProfile.BTN_PRIVACY);
			String title = foundation.getText(Policy.LBL_POLICY_TITLE);
			CustomisedAssert.assertTrue(title.equals(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA)));
			foundation.click(Policy.BTN_OK);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "141883- Kiosk Terms and Conditions (if applicable)")
	public void verifyTermsAndCondition() {
		try {
			final String CASE_NUM = "141883";
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			List<String> language = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));

			locationSummary.kiosklanguageSetting(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE),
					language.get(0), language.get(1));

			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			landingPage.changeLanguage(language.get(2), language.get(0), language.get(3));

			foundation.click(LandingPage.BTN_LOGIN);
			foundation.click(AccountLogin.BTN_EMAIL_LOGIN);
			accountLogin.login(propertyFile.readPropertyFile(Configuration.V5_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.V5_PIN, FilePath.PROPERTY_CONFIG_FILE));

			foundation.click(UserProfile.BTN_TERMS_CONDITION);
			String title = foundation.getText(Policy.LBL_TERMS_CONDITION_TITLE);
			CustomisedAssert.assertTrue(title.equals(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA)));
			foundation.click(Policy.BTN_OK);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "142667- SOS-24492- Kiosk Language selection")
	public void englishDefaultLanguage() {
		final String CASE_NUM = "142667";
		// Reading test data from DataBase
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		List<String> requiredData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> language = Arrays.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Selecting location
			locationList.selectLocationName(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));
			dropDown.selectItem(LocationSummary.DPD_KIOSK_LANGUAGE, requiredData.get(1), Constants.TEXT);
			dropDown.selectItem(LocationSummary.DPD_ALTERNATE_LANGUAGE, requiredData.get(2), Constants.TEXT);

			foundation.click(LocationSummary.BTN_SYNC);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_FILTER, Constants.SHORT_TIME);
			foundation.waitforElementToDisappear(LocationList.TXT_SPINNER_MSG, Constants.EXTRA_LONG_TIME);
			login.logout();
			browser.close();

			foundation.threadWait(Constants.SHORT_TIME);
			// login into Kiosk Device
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));

			// Validating Landing Page
			landingPage.changeLanguage(language.get(2), language.get(0), language.get(3));
			landingPage.verifyHomeScreenLanguage(rstV5DeviceData.get(CNV5Device.LANDING_PAGE));

			// Validating Search Page
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			productSearch.verifyProductSearhPageLanguage(rstV5DeviceData.get(CNV5Device.PRODUCT_SEARCH_PAGE));

			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
			foundation.click(ProductSearch.BTN_PRODUCT);

			// verify Order Page
			List<String> orderPageData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.ORDER_PAGE).split(Constants.DELIMITER_TILD));
			order.verifyOrderPageLanguage(rstV5DeviceData.get(CNV5Device.ORDER_PAGE));

			// Validating Credit/Debit Page
			foundation.click(order.objText(orderPageData.get(8)));
			cardPayment.verifyCardPaymentPageLanguage(rstV5DeviceData.get(CNV5Device.CREDIT_DEBIT_PAGE));

			foundation.click(CardPayment.BTN_CLOSE);
			foundation.waitforElement(Order.BTN_CANCEL_ORDER, Constants.SHORT_TIME);

			// verify Cancel Order Page
			foundation.click(Order.BTN_CANCEL_ORDER);
			CustomisedAssert.assertTrue(
					foundation.isDisplayed(createAccount.objText(rstV5DeviceData.get(CNV5Device.TRANSACTION_CANCEL))));

			// Validating Create Account Page
			foundation.waitforElement(LandingPage.BTN_CREATE_ACCOUNT, Constants.EXTRA_LONG_TIME);
			foundation.click(LandingPage.BTN_CREATE_ACCOUNT);
			createAccount.verifyCreateAccoutnPageLanguage(rstV5DeviceData.get(CNV5Device.CREATE_ACCOUNT),
					requiredData.get(3), rstV5DeviceData.get(CNV5Device.ACTUAL_DATA));

			// Validating Account Login Page
			foundation.click(LandingPage.BTN_LOGIN);
			accountLogin.verifyAccountLoginPageLanguage(rstV5DeviceData.get(CNV5Device.LOGIN_PAGE));

			textBox.enterPin(propertyFile.readPropertyFile(Configuration.V5_PIN, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(AccountLogin.BTN_PIN_NEXT);

			// Verifying Account info page
			List<String> accountPageData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.ACCOUNT_DETAILS).split(Constants.DELIMITER_TILD));
			accountDetails.verifyAccountDetailsPageLanguage(rstV5DeviceData.get(CNV5Device.ACCOUNT_DETAILS),
					requiredData.get(3), rstV5DeviceData.get(CNV5Device.ACTUAL_DATA));

			// Verifying Fund with card page
			foundation.click(fundAccount.objText(accountPageData.get(1)));
			fundAccount.verifyFundAccountScreenLanguage(rstV5DeviceData.get(CNV5Device.FUND_ACCOUNT_PAGE));

			// Verifying Scan Setup page
			foundation.click(fundAccount.objText(accountPageData.get(4)));
			scanPayment.verifyScanPaymentPageLanguage(rstV5DeviceData.get(CNV5Device.QUICK_SCAN_SETUP),
					requiredData.get(3), rstV5DeviceData.get(CNV5Device.ACTUAL_DATA));

			// Verifying Finger print Setup page
			foundation.click(createAccount.objText(accountPageData.get(6)));
			fingerPrintPayment.verifyFingerPrintPaymentPageLanguage(rstV5DeviceData.get(CNV5Device.FINGER_PRINT_SETUP));

			// Verifying Edit account page
			foundation.click(createAccount.objText(accountPageData.get(7)));
			editAccount.verifyEditAccountPageLanguage(rstV5DeviceData.get(CNV5Device.EDIT_ACCOUNT_DETAILS));

			// verify Change pin
			List<String> accountEditPageData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.EDIT_ACCOUNT_DETAILS).split(Constants.DELIMITER_TILD));
			foundation.click(createAccount.objText(accountEditPageData.get(3)));
			changePin.verifyChangePinPageLanguage(rstV5DeviceData.get(CNV5Device.CHANGE_PIN));
			foundation.click(createAccount.objText(accountEditPageData.get(6)));

			// Verifying timeout popup
			foundation.waitforElement(Order.POP_UP_LBL_ORDER_TIMEOUT, Constants.EXTRA_LONG_TIME);
			editAccount.verifyTimeOutPopLanguage(rstV5DeviceData.get(CNV5Device.TIME_OUT_POPUP));

			// Verifying Product Purchase page
			foundation.waitforElement(LandingPage.IMG_SEARCH_ICON, Constants.SHORT_TIME);
			foundation.click(LandingPage.IMG_SEARCH_ICON);

			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
			foundation.click(ProductSearch.BTN_PRODUCT);

			foundation.objectFocus(order.objText(orderPageData.get(7)));
			foundation.click(order.objText(orderPageData.get(7)));

			foundation.waitforElement(AccountLogin.BTN_NEXT, Constants.SHORT_TIME);

			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(
					propertyFile.readPropertyFile(Configuration.V5_USER, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(AccountLogin.BTN_NEXT);
			foundation.waitforElement(AccountLogin.BTN_PIN_NEXT, Constants.SHORT_TIME);
			textBox.enterPin(rstV5DeviceData.get(CNV5Device.PIN));
			foundation.click(AccountLogin.BTN_PIN_NEXT);
			payments.verifyPaymentPageLanguage(rstV5DeviceData.get(CNV5Device.PAYMENTS_PAGE));
			browser.close();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// Resetting the data
			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Selecting location
			locationList.selectLocationName(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));

			dropDown.selectItem(LocationSummary.DPD_KIOSK_LANGUAGE, requiredData.get(4), Constants.TEXT);
			dropDown.selectItem(LocationSummary.DPD_ALTERNATE_LANGUAGE, requiredData.get(5), Constants.TEXT);

			foundation.click(LocationSummary.BTN_SYNC);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElementToDisappear(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			foundation.waitforElement(LocationList.TXT_FILTER, Constants.SHORT_TIME);
			login.logout();
		}
	}

	@Test(description = "142733- SOS-24492 Verify alternate language is set to Italian in Kiosk when user set the Alternate Language as italian and full sync is done in ADM")
	public void alternateItalianLanguage() {
		final String CASE_NUM = "142733";
		// Reading test data from DataBase
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		List<String> requiredData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> language = Arrays.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Selecting location
			locationList.selectLocationName(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));

			dropDown.selectItem(LocationSummary.DPD_KIOSK_LANGUAGE, requiredData.get(1), Constants.TEXT);
			dropDown.selectItem(LocationSummary.DPD_ALTERNATE_LANGUAGE, requiredData.get(2), Constants.TEXT);

			foundation.click(LocationSummary.BTN_SYNC);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_FILTER, Constants.SHORT_TIME);
			foundation.waitforElementToDisappear(LocationList.TXT_SPINNER_MSG, Constants.EXTRA_LONG_TIME);
			login.logout();
			browser.close();

			foundation.threadWait(Constants.SHORT_TIME);
			// login into Kiosk Device
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));

			// Validating Landing Page
			landingPage.changeLanguage(language.get(2), requiredData.get(3), language.get(3));
			landingPage.verifyHomeScreenLanguage(rstV5DeviceData.get(CNV5Device.LANDING_PAGE));

			// Validating Search Page
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			productSearch.verifyProductSearhPageLanguage(rstV5DeviceData.get(CNV5Device.PRODUCT_SEARCH_PAGE));

			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
			foundation.click(ProductSearch.BTN_PRODUCT);

			// verify Order Page
			List<String> orderPageData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.ORDER_PAGE).split(Constants.DELIMITER_TILD));
			order.verifyOrderPageLanguage(rstV5DeviceData.get(CNV5Device.ORDER_PAGE));

			// Validating Credit/Debit Page
			foundation.click(order.objText(orderPageData.get(8)));
			cardPayment.verifyCardPaymentPageLanguage(rstV5DeviceData.get(CNV5Device.CREDIT_DEBIT_PAGE));

			foundation.click(CardPayment.BTN_CLOSE);
			foundation.waitforElement(order.objText(orderPageData.get(1)), Constants.SHORT_TIME);

			// verify Cancel Order Page
			foundation.click(order.objText(orderPageData.get(1)));
			CustomisedAssert.assertTrue(
					foundation.isDisplayed(createAccount.objText(rstV5DeviceData.get(CNV5Device.TRANSACTION_CANCEL))));

			// Validating Create Account Page
			foundation.waitforElement(LandingPage.BTN_CREATE_ACCOUNT, Constants.SHORT_TIME);
			foundation.click(LandingPage.BTN_CREATE_ACCOUNT);
			createAccount.verifyCreateAccoutnPageLanguage(rstV5DeviceData.get(CNV5Device.CREATE_ACCOUNT),
					requiredData.get(3), rstV5DeviceData.get(CNV5Device.ACTUAL_DATA));

			// Validating Account Login Page
			foundation.click(LandingPage.BTN_LOGIN);
			accountLogin.verifyAccountLoginPageLanguage(rstV5DeviceData.get(CNV5Device.LOGIN_PAGE));

			textBox.enterPin(propertyFile.readPropertyFile(Configuration.V5_PIN, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(AccountLogin.BTN_PIN_NEXT);

			// Verifying Account info page
			List<String> accountPageData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.ACCOUNT_DETAILS).split(Constants.DELIMITER_TILD));
			accountDetails.verifyAccountDetailsPageLanguage(rstV5DeviceData.get(CNV5Device.ACCOUNT_DETAILS),
					requiredData.get(3), rstV5DeviceData.get(CNV5Device.ACTUAL_DATA));

			// Verifying Fund with card page
			foundation.click(fundAccount.objText(accountPageData.get(1)));
			fundAccount.verifyFundAccountScreenLanguage(rstV5DeviceData.get(CNV5Device.FUND_ACCOUNT_PAGE));

			// Verifying Scan Setup page
			foundation.click(fundAccount.objText(accountPageData.get(4)));
			scanPayment.verifyScanPaymentPageLanguage(rstV5DeviceData.get(CNV5Device.QUICK_SCAN_SETUP),
					requiredData.get(3), rstV5DeviceData.get(CNV5Device.ACTUAL_DATA));

			// Verifying Finger print Setup page
			foundation.click(createAccount.objText(accountPageData.get(6)));
			fingerPrintPayment.verifyFingerPrintPaymentPageLanguage(rstV5DeviceData.get(CNV5Device.FINGER_PRINT_SETUP));

			// Verifying Edit account page
			foundation.click(createAccount.objText(accountPageData.get(7)));
			editAccount.verifyEditAccountPageLanguage(rstV5DeviceData.get(CNV5Device.EDIT_ACCOUNT_DETAILS));

			// verify Change pin
			List<String> accountEditPageData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.EDIT_ACCOUNT_DETAILS).split(Constants.DELIMITER_TILD));
			foundation.click(createAccount.objText(accountEditPageData.get(3)));
			changePin.verifyChangePinPageLanguage(rstV5DeviceData.get(CNV5Device.CHANGE_PIN));
			foundation.click(createAccount.objText(accountEditPageData.get(6)));

			// Verifying timeout popup
			editAccount.verifyTimeOutPopLanguage(rstV5DeviceData.get(CNV5Device.TIME_OUT_POPUP));

			// Verifying Product Purchase page
			foundation.waitforElement(LandingPage.IMG_SEARCH_ICON, Constants.SHORT_TIME);
			foundation.click(LandingPage.IMG_SEARCH_ICON);

			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
			foundation.click(ProductSearch.BTN_PRODUCT);

			foundation.objectFocus(order.objText(orderPageData.get(7)));
			foundation.click(order.objText(orderPageData.get(7)));

			foundation.waitforElement(AccountLogin.BTN_NEXT, Constants.SHORT_TIME);

			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(
					propertyFile.readPropertyFile(Configuration.V5_USER, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(AccountLogin.BTN_NEXT);
			foundation.waitforElement(AccountLogin.BTN_PIN_NEXT, Constants.SHORT_TIME);
			textBox.enterPin(rstV5DeviceData.get(CNV5Device.PIN));
			foundation.click(AccountLogin.BTN_PIN_NEXT);
			payments.verifyPaymentPageLanguage(rstV5DeviceData.get(CNV5Device.PAYMENTS_PAGE));
			browser.close();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// Resetting the data
			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Selecting location
			locationList.selectLocationName(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));

			dropDown.selectItem(LocationSummary.DPD_KIOSK_LANGUAGE, requiredData.get(4), Constants.TEXT);
			dropDown.selectItem(LocationSummary.DPD_ALTERNATE_LANGUAGE, requiredData.get(5), Constants.TEXT);

			foundation.click(LocationSummary.BTN_SYNC);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElementToDisappear(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			foundation.waitforElement(LocationList.TXT_FILTER, Constants.SHORT_TIME);
			login.logout();
		}

	}

	@Test(description = "142734- SOS-24492 Verify default language is set to french in Kiosk when user set the Default Language as french and full sync is done in ADM")
	public void frenchDefaultLanguage() {
		final String CASE_NUM = "142734";
		// Reading test data from DataBase
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		List<String> requiredData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> language = Arrays.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Selecting location
			locationList.selectLocationName(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));
			dropDown.selectItem(LocationSummary.DPD_KIOSK_LANGUAGE, requiredData.get(1), Constants.TEXT);
			dropDown.selectItem(LocationSummary.DPD_ALTERNATE_LANGUAGE, requiredData.get(2), Constants.TEXT);
			foundation.click(LocationSummary.BTN_SYNC);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_FILTER, Constants.SHORT_TIME);
			foundation.waitforElementToDisappear(LocationList.TXT_SPINNER_MSG, Constants.EXTRA_LONG_TIME);
			login.logout();
			browser.close();

			foundation.threadWait(Constants.SHORT_TIME);
			// login into Kiosk Device
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));

			// Validating Landing Page
			landingPage.changeLanguage(language.get(2), requiredData.get(3), language.get(3));
			landingPage.verifyHomeScreenLanguage(rstV5DeviceData.get(CNV5Device.LANDING_PAGE));

			// Validating Search Page
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			productSearch.verifyProductSearhPageLanguage(rstV5DeviceData.get(CNV5Device.PRODUCT_SEARCH_PAGE));

			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
			foundation.click(ProductSearch.BTN_PRODUCT);

			// verify Order Page
			List<String> orderPageData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.ORDER_PAGE).split(Constants.DELIMITER_TILD));
			order.verifyOrderPageLanguage(rstV5DeviceData.get(CNV5Device.ORDER_PAGE));

			// Validating Credit/Debit Page
			foundation.click(order.objText(orderPageData.get(8)));
			cardPayment.verifyCardPaymentPageLanguage(rstV5DeviceData.get(CNV5Device.CREDIT_DEBIT_PAGE));
			foundation.click(CardPayment.BTN_CLOSE);
			foundation.waitforElement(order.objText(orderPageData.get(1)), Constants.SHORT_TIME);

			// verify Cancel Order Page
			foundation.click(order.objText(orderPageData.get(0)));

			CustomisedAssert.assertTrue(
					foundation.isDisplayed(createAccount.objText(rstV5DeviceData.get(CNV5Device.TRANSACTION_CANCEL))));

			// Validating Create Account Page
			foundation.waitforElement(LandingPage.BTN_CREATE_ACCOUNT, Constants.SHORT_TIME);
			foundation.click(LandingPage.BTN_CREATE_ACCOUNT);
			createAccount.verifyCreateAccoutnPageLanguage(rstV5DeviceData.get(CNV5Device.CREATE_ACCOUNT),
					requiredData.get(3), rstV5DeviceData.get(CNV5Device.ACTUAL_DATA));

			// Validating Account Login Page
			foundation.click(LandingPage.BTN_LOGIN);
			accountLogin.verifyAccountLoginPageLanguage(rstV5DeviceData.get(CNV5Device.LOGIN_PAGE));

			textBox.enterPin(propertyFile.readPropertyFile(Configuration.V5_PIN, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(AccountLogin.BTN_PIN_NEXT);

			// Verifying Account info page
			List<String> accountPageData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.ACCOUNT_DETAILS).split(Constants.DELIMITER_TILD));
			accountDetails.verifyAccountDetailsPageLanguage(rstV5DeviceData.get(CNV5Device.ACCOUNT_DETAILS),
					requiredData.get(3), rstV5DeviceData.get(CNV5Device.ACTUAL_DATA));

			// Verifying Fund with card page
			foundation.click(fundAccount.objText(accountPageData.get(1)));
			fundAccount.verifyFundAccountScreenLanguage(rstV5DeviceData.get(CNV5Device.FUND_ACCOUNT_PAGE));

			// Verifying Scan Setup page
			foundation.click(fundAccount.objText(accountPageData.get(4)));
			scanPayment.verifyScanPaymentPageLanguage(rstV5DeviceData.get(CNV5Device.QUICK_SCAN_SETUP),
					requiredData.get(3), rstV5DeviceData.get(CNV5Device.ACTUAL_DATA));

			// Verifying Finger print Setup page
			foundation.click(createAccount.objText(accountPageData.get(6)));
			fingerPrintPayment.verifyFingerPrintPaymentPageLanguage(rstV5DeviceData.get(CNV5Device.FINGER_PRINT_SETUP));

			// Verifying Edit account page
			foundation.click(createAccount.objText(accountPageData.get(7)));
			editAccount.verifyEditAccountPageLanguage(rstV5DeviceData.get(CNV5Device.EDIT_ACCOUNT_DETAILS));

			// verify Change pin
			List<String> accountEditPageData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.EDIT_ACCOUNT_DETAILS).split(Constants.DELIMITER_TILD));
			foundation.click(createAccount.objText(accountEditPageData.get(3)));
			changePin.verifyChangePinPageLanguage(rstV5DeviceData.get(CNV5Device.CHANGE_PIN));
			foundation.click(createAccount.objText(accountEditPageData.get(6)));

			// Verifying timeout popup
			editAccount.verifyTimeOutPopLanguage(rstV5DeviceData.get(CNV5Device.TIME_OUT_POPUP));

			// Verifying Product Purchase page
			foundation.waitforElement(LandingPage.IMG_SEARCH_ICON, Constants.SHORT_TIME);
			foundation.click(LandingPage.IMG_SEARCH_ICON);

			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
			foundation.click(ProductSearch.BTN_PRODUCT);

			foundation.objectFocus(order.objText(orderPageData.get(7)));
			foundation.click(order.objText(orderPageData.get(7)));
			foundation.waitforElement(AccountLogin.BTN_NEXT, Constants.SHORT_TIME);
			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(
					propertyFile.readPropertyFile(Configuration.V5_USER, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(AccountLogin.BTN_NEXT);
			foundation.waitforElement(AccountLogin.BTN_PIN_NEXT, Constants.SHORT_TIME);
			textBox.enterPin(rstV5DeviceData.get(CNV5Device.PIN));
			foundation.click(AccountLogin.BTN_PIN_NEXT);
			payments.verifyPaymentPageLanguage(rstV5DeviceData.get(CNV5Device.PAYMENTS_PAGE));
			browser.close();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// Resetting the data
			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Selecting location
			locationList.selectLocationName(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));
			dropDown.selectItem(LocationSummary.DPD_KIOSK_LANGUAGE, requiredData.get(4), Constants.TEXT);
			dropDown.selectItem(LocationSummary.DPD_ALTERNATE_LANGUAGE, requiredData.get(5), Constants.TEXT);
			foundation.click(LocationSummary.BTN_SYNC);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElementToDisappear(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			foundation.waitforElement(LocationList.TXT_FILTER, Constants.SHORT_TIME);
			login.logout();
		}
	}

	@Test(description = "142735- SOS-24492 Verify default language is set to Dutch in Kiosk when user set the Default Language as Dutch and full sync is done in ADM")
	public void dutchDefaultLanguage() {
		final String CASE_NUM = "142735";

		// Reading test data from DataBase
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		List<String> requiredData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> language = Arrays.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Selecting location
			locationList.selectLocationName(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));
			dropDown.selectItem(LocationSummary.DPD_KIOSK_LANGUAGE, requiredData.get(1), Constants.TEXT);
			dropDown.selectItem(LocationSummary.DPD_ALTERNATE_LANGUAGE, requiredData.get(2), Constants.TEXT);

			foundation.click(LocationSummary.BTN_SYNC);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_FILTER, Constants.SHORT_TIME);
			foundation.waitforElementToDisappear(LocationList.TXT_SPINNER_MSG, Constants.EXTRA_LONG_TIME);
			login.logout();
			browser.close();

			foundation.threadWait(Constants.SHORT_TIME);
			// login into Kiosk Device
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));

			// Validating Landing Page
			landingPage.changeLanguage(language.get(2), requiredData.get(3), language.get(3));
			landingPage.verifyHomeScreenLanguage(rstV5DeviceData.get(CNV5Device.LANDING_PAGE));

			// Validating Search Page
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			productSearch.verifyProductSearhPageLanguage(rstV5DeviceData.get(CNV5Device.PRODUCT_SEARCH_PAGE));

			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
			foundation.click(ProductSearch.BTN_PRODUCT);

			// verify Order Page
			List<String> orderPageData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.ORDER_PAGE).split(Constants.DELIMITER_TILD));
			order.verifyOrderPageLanguage(rstV5DeviceData.get(CNV5Device.ORDER_PAGE));

			// Validating Credit/Debit Page
			foundation.click(order.objText(orderPageData.get(8)));
			cardPayment.verifyCardPaymentPageLanguage(rstV5DeviceData.get(CNV5Device.CREDIT_DEBIT_PAGE));

			foundation.click(CardPayment.BTN_CLOSE);
			foundation.waitforElement(order.objText(orderPageData.get(1)), Constants.SHORT_TIME);

			// verify Cancel Order Page
			foundation.click(order.objText(orderPageData.get(0)));
			CustomisedAssert.assertTrue(
					foundation.isDisplayed(createAccount.objText(rstV5DeviceData.get(CNV5Device.TRANSACTION_CANCEL))));

			// Validating Create Account Page
			foundation.waitforElement(LandingPage.BTN_CREATE_ACCOUNT, Constants.SHORT_TIME);
			foundation.click(LandingPage.BTN_CREATE_ACCOUNT);
			createAccount.verifyCreateAccoutnPageLanguage(rstV5DeviceData.get(CNV5Device.CREATE_ACCOUNT),
					requiredData.get(3), rstV5DeviceData.get(CNV5Device.ACTUAL_DATA));

			// Validating Account Login Page
			foundation.click(LandingPage.BTN_LOGIN);
			accountLogin.verifyAccountLoginPageLanguage(rstV5DeviceData.get(CNV5Device.LOGIN_PAGE));

			textBox.enterPin(propertyFile.readPropertyFile(Configuration.V5_PIN, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(AccountLogin.BTN_PIN_NEXT);

			// Verifying Account info page
			List<String> accountPageData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.ACCOUNT_DETAILS).split(Constants.DELIMITER_TILD));
			accountDetails.verifyAccountDetailsPageLanguage(rstV5DeviceData.get(CNV5Device.ACCOUNT_DETAILS),
					requiredData.get(3), rstV5DeviceData.get(CNV5Device.ACTUAL_DATA));

			// Verifying Fund with card page
			foundation.click(fundAccount.objText(accountPageData.get(1)));
			fundAccount.verifyFundAccountScreenLanguage(rstV5DeviceData.get(CNV5Device.FUND_ACCOUNT_PAGE));

			// Verifying Scan Setup page
			foundation.click(fundAccount.objText(accountPageData.get(4)));
			scanPayment.verifyScanPaymentPageLanguage(rstV5DeviceData.get(CNV5Device.QUICK_SCAN_SETUP),
					requiredData.get(3), rstV5DeviceData.get(CNV5Device.ACTUAL_DATA));

			// Verifying Finger print Setup page
			foundation.click(createAccount.objText(accountPageData.get(6)));
			fingerPrintPayment.verifyFingerPrintPaymentPageLanguage(rstV5DeviceData.get(CNV5Device.FINGER_PRINT_SETUP));

			// Verifying Edit account page
			foundation.click(createAccount.objText(accountPageData.get(7)));
			editAccount.verifyEditAccountPageLanguage(rstV5DeviceData.get(CNV5Device.EDIT_ACCOUNT_DETAILS));

			// verify Change pin
			List<String> accountEditPageData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.EDIT_ACCOUNT_DETAILS).split(Constants.DELIMITER_TILD));
			foundation.click(createAccount.objText(accountEditPageData.get(3)));
			changePin.verifyChangePinPageLanguage(rstV5DeviceData.get(CNV5Device.CHANGE_PIN));
			foundation.click(createAccount.objText(accountEditPageData.get(6)));

			// Verifying timeout popup
			editAccount.verifyTimeOutPopLanguage(rstV5DeviceData.get(CNV5Device.TIME_OUT_POPUP));

			// Verifying Product Purchase page
			foundation.waitforElement(LandingPage.IMG_SEARCH_ICON, Constants.SHORT_TIME);
			foundation.click(LandingPage.IMG_SEARCH_ICON);

			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
			foundation.click(ProductSearch.BTN_PRODUCT);

			foundation.objectFocus(order.objText(orderPageData.get(7)));
			foundation.click(order.objText(orderPageData.get(7)));

			foundation.waitforElement(AccountLogin.BTN_NEXT, Constants.SHORT_TIME);

			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(
					propertyFile.readPropertyFile(Configuration.V5_USER, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(AccountLogin.BTN_NEXT);
			foundation.waitforElement(AccountLogin.BTN_PIN_NEXT, Constants.SHORT_TIME);
			textBox.enterPin(rstV5DeviceData.get(CNV5Device.PIN));
			foundation.click(AccountLogin.BTN_PIN_NEXT);
			payments.verifyPaymentPageLanguage(rstV5DeviceData.get(CNV5Device.PAYMENTS_PAGE));
			browser.close();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// resetting the data
			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Selecting location
			locationList.selectLocationName(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));

			dropDown.selectItem(LocationSummary.DPD_KIOSK_LANGUAGE, requiredData.get(4), Constants.TEXT);
			dropDown.selectItem(LocationSummary.DPD_ALTERNATE_LANGUAGE, requiredData.get(5), Constants.TEXT);

			foundation.click(LocationSummary.BTN_SYNC);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElementToDisappear(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			foundation.waitforElement(LocationList.TXT_FILTER, Constants.SHORT_TIME);
			login.logout();
		}
	}

	@Test(description = "142736- SOS-24492 Verify default language is set to Swedish in Kiosk when user set the Default Language as Swedish and full sync is done in ADM")
	public void swedishDefaultLanguage() {
		final String CASE_NUM = "142736";
		// Reading test data from DataBase
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		List<String> requiredData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> language = Arrays.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Selecting location
			locationList.selectLocationName(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));

			dropDown.selectItem(LocationSummary.DPD_KIOSK_LANGUAGE, requiredData.get(1), Constants.TEXT);
			dropDown.selectItem(LocationSummary.DPD_ALTERNATE_LANGUAGE, requiredData.get(2), Constants.TEXT);

			foundation.click(LocationSummary.BTN_SYNC);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_FILTER, Constants.SHORT_TIME);
			foundation.waitforElementToDisappear(LocationList.TXT_SPINNER_MSG, Constants.EXTRA_LONG_TIME);
			login.logout();
			browser.close();

			foundation.threadWait(Constants.SHORT_TIME);
			// login into Kiosk Device
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));

			// Validating Landing Page
			landingPage.changeLanguage(language.get(2), requiredData.get(3), language.get(3));
			landingPage.verifyHomeScreenLanguage(rstV5DeviceData.get(CNV5Device.LANDING_PAGE));

			// Validating Search Page
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			productSearch.verifyProductSearhPageLanguage(rstV5DeviceData.get(CNV5Device.PRODUCT_SEARCH_PAGE));

			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
			foundation.click(ProductSearch.BTN_PRODUCT);

			// verify Order Page
			List<String> orderPageData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.ORDER_PAGE).split(Constants.DELIMITER_TILD));
			order.verifyOrderPageLanguage(rstV5DeviceData.get(CNV5Device.ORDER_PAGE));

			// Validating Credit/Debit Page
			foundation.click(order.objText(orderPageData.get(8)));
			cardPayment.verifyCardPaymentPageLanguage(rstV5DeviceData.get(CNV5Device.CREDIT_DEBIT_PAGE));

			foundation.click(CardPayment.BTN_CLOSE);
			foundation.waitforElement(order.objText(orderPageData.get(1)), Constants.SHORT_TIME);

			// verify Cancel Order Page
			foundation.click(order.objText(orderPageData.get(0)));
			CustomisedAssert.assertTrue(
					foundation.isDisplayed(createAccount.objText(rstV5DeviceData.get(CNV5Device.TRANSACTION_CANCEL))));

			// Validating Create Account Page
			foundation.waitforElement(LandingPage.BTN_CREATE_ACCOUNT, Constants.SHORT_TIME);
			foundation.click(LandingPage.BTN_CREATE_ACCOUNT);
			createAccount.verifyCreateAccoutnPageLanguage(rstV5DeviceData.get(CNV5Device.CREATE_ACCOUNT),
					requiredData.get(3), rstV5DeviceData.get(CNV5Device.ACTUAL_DATA));

			// Validating Account Login Page
			foundation.click(LandingPage.BTN_LOGIN);
			accountLogin.verifyAccountLoginPageLanguage(rstV5DeviceData.get(CNV5Device.LOGIN_PAGE));

			textBox.enterPin(propertyFile.readPropertyFile(Configuration.V5_PIN, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(AccountLogin.BTN_PIN_NEXT);

			// Verifying Account info page
			List<String> accountPageData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.ACCOUNT_DETAILS).split(Constants.DELIMITER_TILD));
			accountDetails.verifyAccountDetailsPageLanguage(rstV5DeviceData.get(CNV5Device.ACCOUNT_DETAILS),
					requiredData.get(3), rstV5DeviceData.get(CNV5Device.ACTUAL_DATA));

			// Verifying Fund with card page
			foundation.click(fundAccount.objText(accountPageData.get(1)));
			fundAccount.verifyFundAccountScreenLanguage(rstV5DeviceData.get(CNV5Device.FUND_ACCOUNT_PAGE));

			// Verifying Scan Setup page
			foundation.click(fundAccount.objText(accountPageData.get(4)));
			scanPayment.verifyScanPaymentPageLanguage(rstV5DeviceData.get(CNV5Device.QUICK_SCAN_SETUP),
					requiredData.get(3), rstV5DeviceData.get(CNV5Device.ACTUAL_DATA));

			// Verifying Finger print Setup page
			foundation.click(createAccount.objText(accountPageData.get(6)));
			fingerPrintPayment.verifyFingerPrintPaymentPageLanguage(rstV5DeviceData.get(CNV5Device.FINGER_PRINT_SETUP));

			// Verifying Edit account page
			foundation.click(createAccount.objText(accountPageData.get(7)));
			editAccount.verifyEditAccountPageLanguage(rstV5DeviceData.get(CNV5Device.EDIT_ACCOUNT_DETAILS));

			// verify Change pin
			List<String> accountEditPageData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.EDIT_ACCOUNT_DETAILS).split(Constants.DELIMITER_TILD));
			foundation.click(createAccount.objText(accountEditPageData.get(3)));
			changePin.verifyChangePinPageLanguage(rstV5DeviceData.get(CNV5Device.CHANGE_PIN));
			foundation.click(createAccount.objText(accountEditPageData.get(6)));

			// Verifying timeout popup
			editAccount.verifyTimeOutPopLanguage(rstV5DeviceData.get(CNV5Device.TIME_OUT_POPUP));

			// Verifying Product Purchase page
			foundation.waitforElement(LandingPage.IMG_SEARCH_ICON, Constants.SHORT_TIME);
			foundation.click(LandingPage.IMG_SEARCH_ICON);

			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
			foundation.click(ProductSearch.BTN_PRODUCT);

			foundation.objectFocus(order.objText(orderPageData.get(7)));
			foundation.click(order.objText(orderPageData.get(7)));

			foundation.waitforElement(AccountLogin.BTN_NEXT, Constants.SHORT_TIME);

			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(
					propertyFile.readPropertyFile(Configuration.V5_USER, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(AccountLogin.BTN_NEXT);
			foundation.waitforElement(AccountLogin.BTN_PIN_NEXT, Constants.SHORT_TIME);
			textBox.enterPin(rstV5DeviceData.get(CNV5Device.PIN));
			foundation.click(AccountLogin.BTN_PIN_NEXT);
			payments.verifyPaymentPageLanguage(rstV5DeviceData.get(CNV5Device.PAYMENTS_PAGE));
			browser.close();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// Resetting data
			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Selecting location
			locationList.selectLocationName(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));

			dropDown.selectItem(LocationSummary.DPD_KIOSK_LANGUAGE, requiredData.get(4), Constants.TEXT);
			dropDown.selectItem(LocationSummary.DPD_ALTERNATE_LANGUAGE, requiredData.get(5), Constants.TEXT);

			foundation.click(LocationSummary.BTN_SYNC);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElementToDisappear(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			foundation.waitforElement(LocationList.TXT_FILTER, Constants.SHORT_TIME);
			login.logout();
		}
	}

	@Test(description = "142737- SOS-24492 Verify default language is set to Norwegian in Kiosk when user set the Default Language as Norwegian and full sync is done in ADM")
	public void norwegianDefaultLanguage() {
		final String CASE_NUM = "142737";
		// Reading test data from DataBase
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		List<String> requiredData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> language = Arrays.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Selecting location
			locationList.selectLocationName(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));

			dropDown.selectItem(LocationSummary.DPD_KIOSK_LANGUAGE, requiredData.get(1), Constants.TEXT);
			dropDown.selectItem(LocationSummary.DPD_ALTERNATE_LANGUAGE, requiredData.get(2), Constants.TEXT);

			foundation.click(LocationSummary.BTN_SYNC);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_FILTER, Constants.SHORT_TIME);
			foundation.waitforElementToDisappear(LocationList.TXT_SPINNER_MSG, Constants.EXTRA_LONG_TIME);
			login.logout();
			browser.close();

			foundation.threadWait(Constants.SHORT_TIME);
			// login into Kiosk Device
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));

			// Validating Landing Page
			landingPage.changeLanguage(language.get(2), requiredData.get(3), language.get(3));
			landingPage.verifyHomeScreenLanguage(rstV5DeviceData.get(CNV5Device.LANDING_PAGE));

			// Validating Search Page
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			productSearch.verifyProductSearhPageLanguage(rstV5DeviceData.get(CNV5Device.PRODUCT_SEARCH_PAGE));

			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
			foundation.click(ProductSearch.BTN_PRODUCT);

			// verify Order Page
			List<String> orderPageData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.ORDER_PAGE).split(Constants.DELIMITER_TILD));
			order.verifyOrderPageLanguage(rstV5DeviceData.get(CNV5Device.ORDER_PAGE));

			// Validating Credit/Debit Page
			foundation.click(order.objText(orderPageData.get(8)));
			cardPayment.verifyCardPaymentPageLanguage(rstV5DeviceData.get(CNV5Device.CREDIT_DEBIT_PAGE));

			foundation.click(CardPayment.BTN_CLOSE);
			foundation.waitforElement(order.objText(orderPageData.get(1)), Constants.SHORT_TIME);

			// verify Cancel Order Page
			foundation.click(cardPayment.objText(orderPageData.get(0)));
			CustomisedAssert.assertTrue(
					foundation.isDisplayed(createAccount.objText(rstV5DeviceData.get(CNV5Device.TRANSACTION_CANCEL))));

			// Validating Create Account Page
			foundation.waitforElement(LandingPage.BTN_CREATE_ACCOUNT, Constants.SHORT_TIME);
			foundation.click(LandingPage.BTN_CREATE_ACCOUNT);
			createAccount.verifyCreateAccoutnPageLanguage(rstV5DeviceData.get(CNV5Device.CREATE_ACCOUNT),
					requiredData.get(3), rstV5DeviceData.get(CNV5Device.ACTUAL_DATA));

			// Validating Account Login Page
			foundation.click(LandingPage.BTN_LOGIN);
			accountLogin.verifyAccountLoginPageLanguage(rstV5DeviceData.get(CNV5Device.LOGIN_PAGE));

			textBox.enterPin(propertyFile.readPropertyFile(Configuration.V5_PIN, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(AccountLogin.BTN_PIN_NEXT);

			// Verifying Account info page
			List<String> accountPageData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.ACCOUNT_DETAILS).split(Constants.DELIMITER_TILD));
			accountDetails.verifyAccountDetailsPageLanguage(rstV5DeviceData.get(CNV5Device.ACCOUNT_DETAILS),
					requiredData.get(3), rstV5DeviceData.get(CNV5Device.ACTUAL_DATA));

			// Verifying Fund with card page
			foundation.click(fundAccount.objText(accountPageData.get(1)));
			fundAccount.verifyFundAccountScreenLanguage(rstV5DeviceData.get(CNV5Device.FUND_ACCOUNT_PAGE));

			// Verifying Scan Setup page
			foundation.click(fundAccount.objText(accountPageData.get(4)));
			scanPayment.verifyScanPaymentPageLanguage(rstV5DeviceData.get(CNV5Device.QUICK_SCAN_SETUP),
					requiredData.get(3), rstV5DeviceData.get(CNV5Device.ACTUAL_DATA));

			// Verifying Finger print Setup page
			foundation.click(createAccount.objText(accountPageData.get(6)));
			fingerPrintPayment.verifyFingerPrintPaymentPageLanguage(rstV5DeviceData.get(CNV5Device.FINGER_PRINT_SETUP));

			// Verifying Edit account page
			foundation.click(createAccount.objText(accountPageData.get(7)));
			editAccount.verifyEditAccountPageLanguage(rstV5DeviceData.get(CNV5Device.EDIT_ACCOUNT_DETAILS));

			// verify Change pin
			List<String> accountEditPageData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.EDIT_ACCOUNT_DETAILS).split(Constants.DELIMITER_TILD));
			foundation.click(createAccount.objText(accountEditPageData.get(3)));
			changePin.verifyChangePinPageLanguage(rstV5DeviceData.get(CNV5Device.CHANGE_PIN));
			foundation.click(createAccount.objText(accountEditPageData.get(6)));

			// Verifying timeout popup
			editAccount.verifyTimeOutPopLanguage(rstV5DeviceData.get(CNV5Device.TIME_OUT_POPUP));

			// Verifying Product Purchase page
			foundation.waitforElement(LandingPage.IMG_SEARCH_ICON, Constants.SHORT_TIME);
			foundation.click(LandingPage.IMG_SEARCH_ICON);

			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
			foundation.click(ProductSearch.BTN_PRODUCT);

			foundation.objectFocus(order.objText(orderPageData.get(7)));
			foundation.click(order.objText(orderPageData.get(7)));

			foundation.waitforElement(AccountLogin.BTN_NEXT, Constants.SHORT_TIME);

			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(
					propertyFile.readPropertyFile(Configuration.V5_USER, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(AccountLogin.BTN_NEXT);
			foundation.waitforElement(AccountLogin.BTN_PIN_NEXT, Constants.SHORT_TIME);
			textBox.enterPin(rstV5DeviceData.get(CNV5Device.PIN));
			foundation.click(AccountLogin.BTN_PIN_NEXT);
			payments.verifyPaymentPageLanguage(rstV5DeviceData.get(CNV5Device.PAYMENTS_PAGE));
			browser.close();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// Resetting data
			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Selecting location
			locationList.selectLocationName(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));

			dropDown.selectItem(LocationSummary.DPD_KIOSK_LANGUAGE, requiredData.get(4), Constants.TEXT);
			dropDown.selectItem(LocationSummary.DPD_ALTERNATE_LANGUAGE, requiredData.get(5), Constants.TEXT);

			foundation.click(LocationSummary.BTN_SYNC);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElementToDisappear(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			foundation.waitforElement(LocationList.TXT_FILTER, Constants.SHORT_TIME);
			login.logout();
		}
	}

	@Test(description = "142738- SOS-24492 Verify alternate language is set to spanish in Kiosk when user set the Alternate Language as spanish and full sync is done in ADM")
	public void alternateSpanishLanguage() {
		final String CASE_NUM = "142738";
		// Reading test data from DataBase
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		List<String> requiredData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> language = Arrays.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Selecting location
			locationList.selectLocationName(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));

			dropDown.selectItem(LocationSummary.DPD_KIOSK_LANGUAGE, requiredData.get(1), Constants.TEXT);
			dropDown.selectItem(LocationSummary.DPD_ALTERNATE_LANGUAGE, requiredData.get(2), Constants.TEXT);

			foundation.click(LocationSummary.BTN_SYNC);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			foundation.waitforElementToDisappear(LocationList.TXT_SPINNER_MSG, Constants.EXTRA_LONG_TIME);
			login.logout();
			browser.close();

			foundation.threadWait(Constants.SHORT_TIME);
			// login into Kiosk Device
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));

			// Validating Landing Page
			landingPage.changeLanguage(language.get(2), requiredData.get(3), language.get(3));
			landingPage.verifyHomeScreenLanguage(rstV5DeviceData.get(CNV5Device.LANDING_PAGE));

			// Validating Search Page
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			productSearch.verifyProductSearhPageLanguage(rstV5DeviceData.get(CNV5Device.PRODUCT_SEARCH_PAGE));

			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
			foundation.click(ProductSearch.BTN_PRODUCT);

			// verify Order Page
			List<String> orderPageData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.ORDER_PAGE).split(Constants.DELIMITER_TILD));
			order.verifyOrderPageLanguage(rstV5DeviceData.get(CNV5Device.ORDER_PAGE));

			// Validating Credit/Debit Page
			foundation.click(order.objText(orderPageData.get(8)));
			cardPayment.verifyCardPaymentPageLanguage(rstV5DeviceData.get(CNV5Device.CREDIT_DEBIT_PAGE));

			foundation.click(CardPayment.BTN_CLOSE);
			foundation.waitforElement(order.objText(orderPageData.get(0)), Constants.SHORT_TIME);

			// verify Cancel Order Page
			foundation.click(order.objText(orderPageData.get(0)));
			CustomisedAssert.assertTrue(
					foundation.isDisplayed(createAccount.objText(rstV5DeviceData.get(CNV5Device.TRANSACTION_CANCEL))));

			// Validating Create Account Page
			foundation.waitforElement(LandingPage.BTN_CREATE_ACCOUNT, Constants.SHORT_TIME);
			foundation.click(LandingPage.BTN_CREATE_ACCOUNT);
			createAccount.verifyCreateAccoutnPageLanguage(rstV5DeviceData.get(CNV5Device.CREATE_ACCOUNT),
					requiredData.get(3), rstV5DeviceData.get(CNV5Device.ACTUAL_DATA));

			// Validating Account Login Page
			foundation.click(LandingPage.BTN_LOGIN);
			accountLogin.verifyAccountLoginPageLanguage(rstV5DeviceData.get(CNV5Device.LOGIN_PAGE));

			textBox.enterPin(propertyFile.readPropertyFile(Configuration.V5_PIN, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(AccountLogin.BTN_PIN_NEXT);

			// Verifying Account info page
			List<String> accountPageData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.ACCOUNT_DETAILS).split(Constants.DELIMITER_TILD));
			accountDetails.verifyAccountDetailsPageLanguage(rstV5DeviceData.get(CNV5Device.ACCOUNT_DETAILS),
					requiredData.get(3), rstV5DeviceData.get(CNV5Device.ACTUAL_DATA));

			// Verifying Fund with card page
			foundation.click(fundAccount.objText(accountPageData.get(1)));
			fundAccount.verifyFundAccountScreenLanguage(rstV5DeviceData.get(CNV5Device.FUND_ACCOUNT_PAGE));

			// Verifying Scan Setup page
			foundation.click(fundAccount.objText(accountPageData.get(4)));
			scanPayment.verifyScanPaymentPageLanguage(rstV5DeviceData.get(CNV5Device.QUICK_SCAN_SETUP),
					requiredData.get(3), rstV5DeviceData.get(CNV5Device.ACTUAL_DATA));

			// Verifying Finger print Setup page
			foundation.click(createAccount.objText(accountPageData.get(6)));
			fingerPrintPayment.verifyFingerPrintPaymentPageLanguage(rstV5DeviceData.get(CNV5Device.FINGER_PRINT_SETUP));

			// Verifying Edit account page
			foundation.click(createAccount.objText(accountPageData.get(7)));
			editAccount.verifyEditAccountPageLanguage(rstV5DeviceData.get(CNV5Device.EDIT_ACCOUNT_DETAILS));

			// verify Change pin
			List<String> accountEditPageData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.EDIT_ACCOUNT_DETAILS).split(Constants.DELIMITER_TILD));
			foundation.click(createAccount.objText(accountEditPageData.get(3)));
			changePin.verifyChangePinPageLanguage(rstV5DeviceData.get(CNV5Device.CHANGE_PIN));
			foundation.click(createAccount.objText(accountEditPageData.get(6)));

			// Verifying timeout popup
			editAccount.verifyTimeOutPopLanguage(rstV5DeviceData.get(CNV5Device.TIME_OUT_POPUP));

			// Verifying Product Purchase page
			foundation.waitforElement(LandingPage.IMG_SEARCH_ICON, Constants.SHORT_TIME);
			foundation.click(LandingPage.IMG_SEARCH_ICON);

			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
			foundation.click(ProductSearch.BTN_PRODUCT);

			foundation.objectFocus(order.objText(orderPageData.get(7)));
			foundation.click(order.objText(orderPageData.get(7)));

			foundation.waitforElement(AccountLogin.BTN_NEXT, Constants.SHORT_TIME);

			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(
					propertyFile.readPropertyFile(Configuration.V5_USER, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(AccountLogin.BTN_NEXT);
			foundation.waitforElement(AccountLogin.BTN_PIN_NEXT, Constants.SHORT_TIME);
			textBox.enterPin(rstV5DeviceData.get(CNV5Device.PIN));
			foundation.click(AccountLogin.BTN_PIN_NEXT);
			payments.verifyPaymentPageLanguage(rstV5DeviceData.get(CNV5Device.PAYMENTS_PAGE));
			browser.close();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// resetting test data
			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Selecting location
			locationList.selectLocationName(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));

			dropDown.selectItem(LocationSummary.DPD_KIOSK_LANGUAGE, requiredData.get(4), Constants.TEXT);
			dropDown.selectItem(LocationSummary.DPD_ALTERNATE_LANGUAGE, requiredData.get(5), Constants.TEXT);

			foundation.click(LocationSummary.BTN_SYNC);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElementToDisappear(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			foundation.waitforElement(LocationList.TXT_FILTER, Constants.SHORT_TIME);
			login.logout();
		}
	}

	@Test(description = "142739- SOS-24492 Verify alternate language is set to German in Kiosk when user set the Alternate Language as German and full sync is done in ADM")
	public void alternateGermanLanguage() {
		final String CASE_NUM = "142739";
		// Reading test data from DataBase
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		List<String> requiredData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> language = Arrays.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Selecting location
			locationList.selectLocationName(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));

			dropDown.selectItem(LocationSummary.DPD_KIOSK_LANGUAGE, requiredData.get(1), Constants.TEXT);
			dropDown.selectItem(LocationSummary.DPD_ALTERNATE_LANGUAGE, requiredData.get(2), Constants.TEXT);

			foundation.click(LocationSummary.BTN_SYNC);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			foundation.waitforElementToDisappear(LocationList.TXT_SPINNER_MSG, Constants.EXTRA_LONG_TIME);
			login.logout();
			browser.close();

			foundation.threadWait(Constants.SHORT_TIME);
			// login into Kiosk Device
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));

			foundation.threadWait(Constants.TWO_SECOND);
			// Validating Landing Page
			landingPage.changeLanguage(language.get(2), requiredData.get(3), language.get(3));
			landingPage.verifyHomeScreenLanguage(rstV5DeviceData.get(CNV5Device.LANDING_PAGE));

			// Validating Search Page
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			productSearch.verifyProductSearhPageLanguage(rstV5DeviceData.get(CNV5Device.PRODUCT_SEARCH_PAGE));

			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
			foundation.click(ProductSearch.BTN_PRODUCT);

			// verify Order Page
			List<String> orderPageData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.ORDER_PAGE).split(Constants.DELIMITER_TILD));
			order.verifyOrderPageLanguage(rstV5DeviceData.get(CNV5Device.ORDER_PAGE));

			// Validating Credit/Debit Page
			foundation.click(order.objText(orderPageData.get(8)));
			// cardPayment.verifyCardPaymentPageLanguage(rstV5DeviceData.get(CNV5Device.CREDIT_DEBIT_PAGE));
			// foundation.click(CardPayment.BTN_CLOSE);

			foundation.waitforElement(order.objText(orderPageData.get(0)), Constants.SHORT_TIME);

			// verify Cancel Order Page
			foundation.click(order.objText(orderPageData.get(0)));
			CustomisedAssert.assertTrue(
					foundation.isDisplayed(createAccount.objText(rstV5DeviceData.get(CNV5Device.TRANSACTION_CANCEL))));

			landingPage.changeLanguage(language.get(2), requiredData.get(3), language.get(3));
			// Validating Create Account Page
			foundation.waitforElement(LandingPage.BTN_CREATE_ACCOUNT, Constants.SHORT_TIME);
			foundation.click(LandingPage.BTN_CREATE_ACCOUNT);
			createAccount.verifyCreateAccoutnPageLanguage(rstV5DeviceData.get(CNV5Device.CREATE_ACCOUNT),
					requiredData.get(3), rstV5DeviceData.get(CNV5Device.ACTUAL_DATA));

			// Validating Account Login Page
			foundation.click(LandingPage.BTN_LOGIN);
			accountLogin.verifyAccountLoginPageLanguage(rstV5DeviceData.get(CNV5Device.LOGIN_PAGE));

			textBox.enterPin(propertyFile.readPropertyFile(Configuration.V5_PIN, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(AccountLogin.BTN_PIN_NEXT);

			// Verifying Account info page
			List<String> accountPageData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.ACCOUNT_DETAILS).split(Constants.DELIMITER_TILD));
			accountDetails.verifyAccountDetailsPageLanguage(rstV5DeviceData.get(CNV5Device.ACCOUNT_DETAILS),
					requiredData.get(3), rstV5DeviceData.get(CNV5Device.ACTUAL_DATA));

			// Verifying Fund with card page
			foundation.click(fundAccount.objText(accountPageData.get(1)));
			fundAccount.verifyFundAccountScreenLanguage(rstV5DeviceData.get(CNV5Device.FUND_ACCOUNT_PAGE));

			// Verifying Scan Setup page
			foundation.click(fundAccount.objText(accountPageData.get(4)));
			scanPayment.verifyScanPaymentPageLanguage(rstV5DeviceData.get(CNV5Device.QUICK_SCAN_SETUP),
					requiredData.get(3), rstV5DeviceData.get(CNV5Device.ACTUAL_DATA));

			// Verifying Finger print Setup page
			foundation.click(createAccount.objText(accountPageData.get(6)));
			fingerPrintPayment.verifyFingerPrintPaymentPageLanguage(rstV5DeviceData.get(CNV5Device.FINGER_PRINT_SETUP));

			// Verifying Edit account page
			foundation.click(createAccount.objText(accountPageData.get(7)));
			editAccount.verifyEditAccountPageLanguage(rstV5DeviceData.get(CNV5Device.EDIT_ACCOUNT_DETAILS));

			// verify Change pin
			List<String> accountEditPageData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.EDIT_ACCOUNT_DETAILS).split(Constants.DELIMITER_TILD));
			foundation.click(createAccount.objText(accountEditPageData.get(3)));
			changePin.verifyChangePinPageLanguage(rstV5DeviceData.get(CNV5Device.CHANGE_PIN));
			foundation.click(createAccount.objText(accountEditPageData.get(6)));

			// Verifying timeout popup
			editAccount.verifyTimeOutPopLanguage(rstV5DeviceData.get(CNV5Device.TIME_OUT_POPUP));
			landingPage.changeLanguage(language.get(2), requiredData.get(3), language.get(3));

			// Verifying Product Purchase page
			foundation.waitforElement(LandingPage.IMG_SEARCH_ICON, Constants.SHORT_TIME);
			foundation.click(LandingPage.IMG_SEARCH_ICON);

			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
			foundation.click(ProductSearch.BTN_PRODUCT);

			foundation.objectFocus(order.objText(orderPageData.get(7)));
			foundation.click(order.objText(orderPageData.get(7)));

			foundation.waitforElement(AccountLogin.BTN_NEXT, Constants.SHORT_TIME);

			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(
					propertyFile.readPropertyFile(Configuration.V5_USER, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(AccountLogin.BTN_NEXT);
			foundation.waitforElement(AccountLogin.BTN_PIN_NEXT, Constants.SHORT_TIME);
			textBox.enterPin(rstV5DeviceData.get(CNV5Device.PIN));
			foundation.click(AccountLogin.BTN_PIN_NEXT);
			payments.verifyPaymentPageLanguage(rstV5DeviceData.get(CNV5Device.PAYMENTS_PAGE));
			browser.close();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// resetting test data
			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Selecting location
			locationList.selectLocationName(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));

			dropDown.selectItem(LocationSummary.DPD_KIOSK_LANGUAGE, requiredData.get(4), Constants.TEXT);
			dropDown.selectItem(LocationSummary.DPD_ALTERNATE_LANGUAGE, requiredData.get(5), Constants.TEXT);

			foundation.click(LocationSummary.BTN_SYNC);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElementToDisappear(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			foundation.waitforElement(LocationList.TXT_FILTER, Constants.SHORT_TIME);
			login.logout();
		}
	}

	@Test(description = "142740- SOS-24492 Verify alternate language is set to Danish in Kiosk when user set the Alternate Language as Danish and full sync is done in ADM")
	public void alternateDanishLanguage() {
		final String CASE_NUM = "142740";
		// Reading test data from DataBase
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		List<String> requiredData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> language = Arrays.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Selecting location
			locationList.selectLocationName(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));

			dropDown.selectItem(LocationSummary.DPD_KIOSK_LANGUAGE, requiredData.get(1), Constants.TEXT);
			dropDown.selectItem(LocationSummary.DPD_ALTERNATE_LANGUAGE, requiredData.get(2), Constants.TEXT);

			foundation.click(LocationSummary.BTN_SYNC);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			foundation.waitforElementToDisappear(LocationList.TXT_SPINNER_MSG, Constants.EXTRA_LONG_TIME);
			login.logout();
			browser.close();

			foundation.threadWait(Constants.LONG_TIME);
			// login into Kiosk Device
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));

			landingPage.changeLanguage(language.get(2), requiredData.get(2), language.get(3));
			landingPage.verifyHomeScreenLanguage(rstV5DeviceData.get(CNV5Device.LANDING_PAGE));

			// Validating Search Page
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			productSearch.verifyProductSearhPageLanguage(rstV5DeviceData.get(CNV5Device.PRODUCT_SEARCH_PAGE));

			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
			foundation.click(ProductSearch.BTN_PRODUCT);

			// verify Order Page
			List<String> orderPageData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.ORDER_PAGE).split(Constants.DELIMITER_TILD));
			order.verifyOrderPageLanguage(rstV5DeviceData.get(CNV5Device.ORDER_PAGE));

			// Validating Credit/Debit Page
			foundation.click(order.objText(orderPageData.get(8)));
			cardPayment.verifyCardPaymentPageLanguage(rstV5DeviceData.get(CNV5Device.CREDIT_DEBIT_PAGE));

			foundation.click(CardPayment.BTN_CLOSE);
			foundation.waitforElement(order.objText(orderPageData.get(0)), Constants.SHORT_TIME);

			// verify Cancel Order Page
			foundation.click(order.objText(orderPageData.get(0)));
			CustomisedAssert.assertTrue(
					foundation.isDisplayed(createAccount.objText(rstV5DeviceData.get(CNV5Device.TRANSACTION_CANCEL))));

			// Validating Create Account Page
			foundation.waitforElement(LandingPage.BTN_CREATE_ACCOUNT, Constants.SHORT_TIME);
			foundation.click(LandingPage.BTN_CREATE_ACCOUNT);
			createAccount.verifyCreateAccoutnPageLanguage(rstV5DeviceData.get(CNV5Device.CREATE_ACCOUNT),
					requiredData.get(3), rstV5DeviceData.get(CNV5Device.ACTUAL_DATA));

			// Validating Account Login Page
			foundation.click(LandingPage.BTN_LOGIN);
			accountLogin.verifyAccountLoginPageLanguage(rstV5DeviceData.get(CNV5Device.LOGIN_PAGE));

			textBox.enterPin(propertyFile.readPropertyFile(Configuration.V5_PIN, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(AccountLogin.BTN_PIN_NEXT);

			// Verifying Account info page
			List<String> accountPageData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.ACCOUNT_DETAILS).split(Constants.DELIMITER_TILD));
			accountDetails.verifyAccountDetailsPageLanguage(rstV5DeviceData.get(CNV5Device.ACCOUNT_DETAILS),
					requiredData.get(3), rstV5DeviceData.get(CNV5Device.ACTUAL_DATA));

			// Verifying Fund with card page
			foundation.click(fundAccount.objText(accountPageData.get(1)));
			fundAccount.verifyFundAccountScreenLanguage(rstV5DeviceData.get(CNV5Device.FUND_ACCOUNT_PAGE));

			// Verifying Scan Setup page
			foundation.click(fundAccount.objText(accountPageData.get(4)));
			scanPayment.verifyScanPaymentPageLanguage(rstV5DeviceData.get(CNV5Device.QUICK_SCAN_SETUP),
					requiredData.get(3), rstV5DeviceData.get(CNV5Device.ACTUAL_DATA));

			// Verifying Finger print Setup page
			foundation.click(createAccount.objText(accountPageData.get(6)));
			fingerPrintPayment.verifyFingerPrintPaymentPageLanguage(rstV5DeviceData.get(CNV5Device.FINGER_PRINT_SETUP));

			// Verifying Edit account page
			foundation.click(createAccount.objText(accountPageData.get(7)));
			editAccount.verifyEditAccountPageLanguage(rstV5DeviceData.get(CNV5Device.EDIT_ACCOUNT_DETAILS));

			// verify Change pin
			List<String> accountEditPageData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.EDIT_ACCOUNT_DETAILS).split(Constants.DELIMITER_TILD));
			foundation.click(createAccount.objText(accountEditPageData.get(3)));
			changePin.verifyChangePinPageLanguage(rstV5DeviceData.get(CNV5Device.CHANGE_PIN));
			foundation.click(createAccount.objText(accountEditPageData.get(6)));

			// Verifying timeout popup
			editAccount.verifyTimeOutPopLanguage(rstV5DeviceData.get(CNV5Device.TIME_OUT_POPUP));

			// Verifying Product Purchase page
			foundation.waitforElement(LandingPage.IMG_SEARCH_ICON, Constants.SHORT_TIME);
			foundation.click(LandingPage.IMG_SEARCH_ICON);

			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
			foundation.click(ProductSearch.BTN_PRODUCT);

			foundation.objectFocus(order.objText(orderPageData.get(7)));
			foundation.click(order.objText(orderPageData.get(7)));

			foundation.waitforElement(AccountLogin.BTN_NEXT, Constants.SHORT_TIME);

			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(
					propertyFile.readPropertyFile(Configuration.V5_USER, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(AccountLogin.BTN_NEXT);
			foundation.waitforElement(AccountLogin.BTN_PIN_NEXT, Constants.SHORT_TIME);
			textBox.enterPin(rstV5DeviceData.get(CNV5Device.PIN));
			foundation.click(AccountLogin.BTN_PIN_NEXT);
			payments.verifyPaymentPageLanguage(rstV5DeviceData.get(CNV5Device.PAYMENTS_PAGE));
			browser.close();

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
//			// resetting test data
			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Selecting location
			locationList.selectLocationName(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));

			dropDown.selectItem(LocationSummary.DPD_KIOSK_LANGUAGE, requiredData.get(4), Constants.TEXT);
			dropDown.selectItem(LocationSummary.DPD_ALTERNATE_LANGUAGE, requiredData.get(5), Constants.TEXT);

			foundation.click(LocationSummary.BTN_SYNC);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElementToDisappear(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			foundation.waitforElement(LocationList.TXT_FILTER, Constants.SHORT_TIME);
			login.logout();
		}
	}

	@Test(description = "142892-Manitoba > v5 Kiosk Taxes")
	public void manitobaTaxes() {
		final String CASE_NUM = "142892";

		// Reading test data from DataBase
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);

		List<String> menuItem = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> products = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME).split(Constants.DELIMITER_TILD));
		List<String> taxes = Arrays.asList(rstV5DeviceData.get(CNV5Device.ORDER_PAGE).split(Constants.DELIMITER_TILD));
		List<String> requiredData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));

		try {

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// select the org and update country and tax system
			navigationBar.navigateToMenuItem(menuItem.get(0));
			orgList.selectOrg(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			dropDown.selectItem(OrgSummary.DPD_COUNTRY, requiredData.get(0), Constants.TEXT);
			dropDown.selectItem(OrgSummary.DPD_TAX_SYSTEM, requiredData.get(1), Constants.TEXT);
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

			// save taxes for products
			navigationBar.navigateToMenuItem(menuItem.get(2));
			foundation.threadWait(Constants.THREE_SECOND);
			textBox.enterText(GlobalProduct.TXT_FILTER, products.get(0));
			foundation.click(globalProduct.getGlobalProduct(products.get(0)));
			dropdown.selectItem(ProductSummary.DPD_TAX_CATEGORY, requiredData.get(7), Constants.TEXT);
			foundation.click(ProductSummary.BTN_SAVE);
			foundation.threadWait(Constants.THREE_SECOND);
			textBox.enterText(GlobalProduct.TXT_FILTER, products.get(1));
			foundation.click(globalProduct.getGlobalProduct(products.get(1)));
			dropdown.selectItem(ProductSummary.DPD_TAX_CATEGORY, requiredData.get(9), Constants.TEXT);
			foundation.click(ProductSummary.BTN_SAVE);
			foundation.threadWait(Constants.THREE_SECOND);
			textBox.enterText(GlobalProduct.TXT_FILTER, products.get(2));
			foundation.click(globalProduct.getGlobalProduct(products.get(2)));
			dropdown.selectItem(ProductSummary.DPD_TAX_CATEGORY, requiredData.get(11), Constants.TEXT);
			foundation.click(ProductSummary.BTN_SAVE);
			foundation.threadWait(Constants.THREE_SECOND);
			textBox.enterText(GlobalProduct.TXT_FILTER, products.get(3));
			foundation.click(globalProduct.getGlobalProduct(products.get(3)));
			dropdown.selectItem(ProductSummary.DPD_TAX_CATEGORY, requiredData.get(13), Constants.TEXT);
			foundation.click(ProductSummary.BTN_SAVE);

			// save tax mapping- precondition
			navigationBar.navigateToMenuItem(menuItem.get(1));
			foundation.waitforElement(LocationList.DPD_LOCATION_LIST, Constants.SHORT_TIME);
			List<String> language = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
			locationSummary.kiosklanguageSetting(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE),
					language.get(0), language.get(1));
			foundation.waitforElement(LocationList.TXT_FILTER, Constants.SHORT_TIME);
			locationList.selectLocationName(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));
			locationSummary.saveTaxMapping(requiredData.get(7), requiredData.get(8));
			locationSummary.saveTaxMapping(requiredData.get(9), requiredData.get(10));
			locationSummary.saveTaxMapping(requiredData.get(11), requiredData.get(12));
			foundation.click(LocationSummary.BTN_SAVE);

			// sync machine
			foundation.waitforElement(LocationSummary.BTN_SAVE, Constants.SHORT_TIME);
			locationSummary.kiosklanguageSetting(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE),
					language.get(0), language.get(1));

			// login into Kiosk Device add both rate 5 and rate 8 product and verify the
			// display of tax
			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			landingPage.changeLanguage(language.get(2), language.get(0), language.get(3));

			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(products.get(0));
			foundation.click(ProductSearch.BTN_PRODUCT);
			CustomisedAssert.assertTrue(foundation.isDisplayed(Order.TXT_HEADER));
			String uiSubTotal = foundation.getText(Order.LBL_SUB_TOTAL).replace("$", Constants.EMPTY_STRING);
			List<String> lblTaxes = foundation.getTextofListElement(Order.LBL_TAX);
			List<String> lblTaxValues = foundation.getTextofListElement(Order.LBL_VAT_VALUE);
			CustomisedAssert.assertEquals(lblTaxes.get(0), taxes.get(0));
			CustomisedAssert.assertEquals(lblTaxes.get(1), taxes.get(1));
			double calculatedTax1 = Double.parseDouble(uiSubTotal) * (Double.valueOf(taxes.get(7)) / 100);
			double expectedTaxWithRoundUp1 = Math.round(calculatedTax1 * 100.0) / 100.0;
			double calculatedTax2 = Double.parseDouble(uiSubTotal) * (Double.valueOf(taxes.get(8)) / 100);
			double expectedTaxWithRoundUp2 = Math.round(calculatedTax2 * 100.0) / 100.0;
			CustomisedAssert.assertEquals(Double.parseDouble(lblTaxValues.get(0).replace("$", Constants.EMPTY_STRING)),
					expectedTaxWithRoundUp1);
			CustomisedAssert.assertEquals(Double.parseDouble(lblTaxValues.get(1).replace("$", Constants.EMPTY_STRING)),
					expectedTaxWithRoundUp2);

			// add rate 5 product and verify the display of tax
			foundation.click(Order.BTN_CANCEL_ORDER);
			foundation.click(Order.LBL_ORDER_CANCELLED);
			foundation.waitforElement(LandingPage.IMG_SEARCH_ICON, Constants.SHORT_TIME);
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(products.get(1));
			foundation.click(ProductSearch.BTN_PRODUCT);
			CustomisedAssert.assertEquals(foundation.getText(Order.TXT_HEADER), requiredData.get(4));
			CustomisedAssert.assertEquals(foundation.getText(Order.LBL_TAX), taxes.get(0));
			order.verifyTax(taxes.get(7));

			// add rate 8 product and verify the display of tax
			foundation.click(Order.BTN_CANCEL_ORDER);
			foundation.click(Order.LBL_ORDER_CANCELLED);
			foundation.waitforElement(LandingPage.IMG_SEARCH_ICON, Constants.SHORT_TIME);
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(products.get(2));
			foundation.click(ProductSearch.BTN_PRODUCT);
			CustomisedAssert.assertEquals(foundation.getText(Order.TXT_HEADER), requiredData.get(4));
			CustomisedAssert.assertTrue(foundation.getText(Order.LBL_TAX).contains(taxes.get(5)));
			order.verifyTax(taxes.get(8));
			CustomisedAssert.assertEquals(foundation.getText(Order.LBL_VAT_VALUE), taxes.get(6));

			// add no tax assigned product and verify the display of tax section
			foundation.click(Order.BTN_CANCEL_ORDER);
			foundation.click(Order.LBL_ORDER_CANCELLED);
			foundation.waitforElement(LandingPage.IMG_SEARCH_ICON, Constants.SHORT_TIME);
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(products.get(3));
			foundation.click(ProductSearch.BTN_PRODUCT);
			CustomisedAssert.assertEquals(foundation.getText(Order.TXT_HEADER), requiredData.get(4));
			CustomisedAssert.assertFalse(foundation.isDisplayed(Order.LBL_TAX));
			CustomisedAssert.assertFalse(foundation.isDisplayed(Order.LBL_VAT_VALUE));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			browser.close();
			// Reset data
			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItem.get(0));
			// select the org and update country and tax system
			orgList.selectOrg(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			dropDown.selectItem(OrgSummary.DPD_COUNTRY, requiredData.get(5), Constants.TEXT);
			dropDown.selectItem(OrgSummary.DPD_TAX_SYSTEM, requiredData.get(6), Constants.TEXT);
			foundation.click(OrgSummary.BTN_SAVE);
			foundation.waitforElement(OrgSummary.TXT_SPINNER_MSG, Constants.SHORT_TIME);

			// remove mapping ,sync machine
			navigationBar.navigateToMenuItem(menuItem.get(1));
			foundation.waitforElement(LocationList.TXT_FILTER, Constants.SHORT_TIME);
			locationList.selectLocationName(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));
			locationSummary.removeTaxMapping(requiredData.get(7));
			locationSummary.removeTaxMapping(requiredData.get(9));
			locationSummary.removeTaxMapping(requiredData.get(11));
			foundation.click(LocationSummary.BTN_SYNC);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_FILTER, Constants.SHORT_TIME);

			// remove products tax category assigned
			navigationBar.navigateToMenuItem(menuItem.get(2));
			foundation.threadWait(Constants.THREE_SECOND);
			textBox.enterText(GlobalProduct.TXT_FILTER, products.get(0));
			foundation.click(globalProduct.getGlobalProduct(products.get(0)));
			dropdown.selectItem(ProductSummary.DPD_TAX_CATEGORY, requiredData.get(13), Constants.TEXT);
			foundation.click(ProductSummary.BTN_SAVE);
			foundation.threadWait(Constants.THREE_SECOND);
			textBox.enterText(GlobalProduct.TXT_FILTER, products.get(1));
			foundation.click(globalProduct.getGlobalProduct(products.get(1)));
			dropdown.selectItem(ProductSummary.DPD_TAX_CATEGORY, requiredData.get(13), Constants.TEXT);
			foundation.click(ProductSummary.BTN_SAVE);
			foundation.threadWait(Constants.THREE_SECOND);
			textBox.enterText(GlobalProduct.TXT_FILTER, products.get(2));
			foundation.click(globalProduct.getGlobalProduct(products.get(2)));
			dropdown.selectItem(ProductSummary.DPD_TAX_CATEGORY, requiredData.get(13), Constants.TEXT);
			foundation.click(ProductSummary.BTN_SAVE);
		}
	}

	@Test(description = "143019-SOS-11371 v5 > International GMA Login to Account Error")
	public void gmaLoginError() {
		final String CASE_NUM = "143019";

		// Reading test data from DataBase
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);

		List<String> menuItem = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> requiredData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> language = Arrays.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItem.get(0));

			// select the org and update country and tax system
			orgList.selectOrg(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			dropDown.selectItem(OrgSummary.DPD_CROSS_ORG_ACCOUNT, requiredData.get(0), Constants.TEXT);
			foundation.click(OrgSummary.BTN_SAVE);
			foundation.threadWait(Constants.TWO_SECOND);
			orgList.selectOrg(propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			dropDown.selectItem(OrgSummary.DPD_CROSS_ORG_ACCOUNT, requiredData.get(0), Constants.TEXT);
			foundation.click(OrgSummary.BTN_SAVE);

			// sync machine
			foundation.threadWait(Constants.TWO_SECOND);
			navigationBar.navigateToMenuItem(menuItem.get(1));
			foundation.waitforElement(LocationList.DPD_LOCATION_LIST, Constants.SHORT_TIME);

			locationSummary.kiosklanguageSetting(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE),
					language.get(0), language.get(1));

			// verify login error
			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			landingPage.changeLanguage(language.get(2), language.get(0), language.get(3));

			foundation.click(LandingPage.BTN_LOGIN);
			foundation.click(AccountLogin.BTN_EMAIL_LOGIN);
			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(
					propertyFile.readPropertyFile(Configuration.V5_USER, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(AccountLogin.BTN_ENTER);
			foundation.click(AccountLogin.BTN_NEXT);
			CustomisedAssert.assertEquals(foundation.getText(AccountLogin.LBL_ACCOUNT_NOT_AVAILABLE),
					requiredData.get(3));
			CustomisedAssert.assertEquals(foundation.getText(AccountLogin.LBL_GEO_GRAPHIC_LOCATION),
					requiredData.get(4));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// resetData
			browser.close();
			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItem.get(0));
			// select the org and update country and tax system
			orgList.selectOrg(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			dropDown.selectItem(OrgSummary.DPD_CROSS_ORG_ACCOUNT, requiredData.get(5), Constants.TEXT);
			foundation.click(OrgSummary.BTN_SAVE);
			foundation.threadWait(Constants.TWO_SECOND);
			orgList.selectOrg(propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			dropDown.selectItem(OrgSummary.DPD_CROSS_ORG_ACCOUNT, requiredData.get(5), Constants.TEXT);
			foundation.click(OrgSummary.BTN_SAVE);
			foundation.threadWait(Constants.THREE_SECOND);
			browser.close();

			// verify should login successfully
			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			landingPage.changeLanguage(language.get(2), language.get(0), language.get(3));

			foundation.click(LandingPage.BTN_LOGIN);
			foundation.click(AccountLogin.BTN_EMAIL_LOGIN);
			accountLogin.login(propertyFile.readPropertyFile(Configuration.V5_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.V5_PIN, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(EditAccount.BTN_EDIT_ACCOUNT));
		}
	}

	@Test(description = "142850-Verify all the Tabs displayed after login with Driver user")
	public void verifyTabsDsiplayed() {
		try {

			final String CASE_NUM = "142850";

			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			List<String> language = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			locationSummary.kiosklanguageSetting(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE),
					language.get(0), language.get(1));

			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);

			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));

			landingPage.changeLanguage(language.get(2), language.get(0), language.get(3));
			landingPage.navigateDriverLoginPage();
			driverLoginPage.enterDriverPin(rstV5DeviceData.get(CNV5Device.PIN));
			foundation.click(DriverLoginPage.BTN_SIGN_IN);
			CustomisedAssert.assertTrue(foundation.isDisplayed(DriverHomePage.TXT_MENU));
			List<String> tabNames = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			driverHomePage.verifyData(tabNames);
			foundation.click(DriverHomePage.LINK_LOGOUT);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "142853-Verify the Logout tab from Driver menu")
	public void verifyLogoutTab() {
		try {

			final String CASE_NUM = "142853";
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			List<String> language = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			locationSummary.kiosklanguageSetting(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE),
					language.get(0), language.get(1));

			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			landingPage.changeLanguage(language.get(2), language.get(0), language.get(3));

			landingPage.navigateDriverLoginPage();
			driverLoginPage.enterDriverPin(rstV5DeviceData.get(CNV5Device.PIN));
			foundation.click(DriverLoginPage.BTN_SIGN_IN);
			CustomisedAssert.assertTrue(foundation.isDisplayed(DriverHomePage.TXT_MENU));
			foundation.click(DriverHomePage.LINK_LOGOUT);
			CustomisedAssert.assertTrue(foundation.isDisplayed(DriverLoginPage.BTN_SIGN_IN));
			foundation.click(DriverLoginPage.BTN_SELF_SERVICE_MODE);
			foundation.isDisplayed(LandingPage.IMG_SEARCH_ICON);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "142851-Verify the deafult selected tab after login with Driver")
	public void verifyDefaultSelectedTab() {
		try {

			final String CASE_NUM = "142851";

			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			List<String> language = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			locationSummary.kiosklanguageSetting(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE),
					language.get(0), language.get(1));

			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);

			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			List<String> actualData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.ACTUAL_DATA).split(Constants.DELIMITER_TILD));
			landingPage.changeLanguage(language.get(2), language.get(0), language.get(3));

			landingPage.navigateDriverLoginPage();
			driverLoginPage.enterDriverPin(rstV5DeviceData.get(CNV5Device.PIN));
			foundation.click(DriverLoginPage.BTN_SIGN_IN);
			CustomisedAssert.assertTrue(foundation.isDisplayed(DriverHomePage.TXT_MENU));
			String value = foundation.getBGColor(DriverHomePage.LINK_CASHOUT);
			CustomisedAssert.assertEquals(actualData.get(1), value);
			CustomisedAssert.assertTrue(foundation.isDisplayed(DriverHomePage.LBL_CASHOUT));
			CustomisedAssert.assertTrue(foundation.isDisplayed(DriverHomePage.LBL_BILL_ACCEPTOR));
			List<String> currency = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			driverHomePage.verifyData(currency);
			CustomisedAssert.assertTrue(foundation.isDisplayed(DriverHomePage.BTN_CASHOUT));
			foundation.click(DriverHomePage.LINK_LOGOUT);
			CustomisedAssert.assertTrue(foundation.isDisplayed(DriverLoginPage.BTN_SIGN_IN));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "142852-Verify Inventory Tab details after login with Driver")
	public void verifyInventoryTab() {
		try {

			final String CASE_NUM = "142852";
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			List<String> language = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			locationSummary.kiosklanguageSetting(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE),
					language.get(0), language.get(1));

			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			landingPage.changeLanguage(language.get(2), language.get(0), language.get(3));

			landingPage.navigateDriverLoginPage();
			driverLoginPage.enterDriverPin(rstV5DeviceData.get(CNV5Device.PIN));
			foundation.click(DriverLoginPage.BTN_SIGN_IN);
			CustomisedAssert.assertTrue(foundation.isDisplayed(DriverHomePage.TXT_MENU));
			foundation.click(DriverHomePage.LINK_INVENTORY);
			foundation.waitforElement(InventoryHomePage.TXT_SELECT_ACTION, Constants.SHORT_TIME);
			CustomisedAssert.assertEquals(foundation.getText(InventoryHomePage.TXT_LOCATION),
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));
			List<String> options = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			inventoryHomePage.verifyInventoryOptions(options);
			foundation.click(InventoryHomePage.BTN_LOGOUT);
			CustomisedAssert.assertTrue(foundation.isDisplayed(DriverHomePage.TXT_MENU));
			foundation.click(DriverHomePage.LINK_LOGOUT);
			foundation.click(DriverLoginPage.BTN_SELF_SERVICE_MODE);
			foundation.isDisplayed(LandingPage.IMG_SEARCH_ICON);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "142994-SOS-11643 V5 > Menu Updates")
	public void verifyMenuLevelUpdates() {
		final String CASE_NUM = "142994";
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);

		List<String> requiredData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		try {
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			List<String> orderPageData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.ORDER_PAGE).split(Constants.DELIMITER_TILD));

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			dropDown.selectItem(MicroMarketMenuList.DPD_LOCATION,
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE),
					Constants.TEXT);
			foundation.click(MicroMarketMenuList.BTN_CREATE_NEW);
			textBox.enterText(MicroMarketMenuList.TXT_MENU_NAME, requiredData.get(1));
			checkBox.check(microMarketMenu.getCurrentDayObj(dateAndTime.getCurrentDay()));
			foundation.threadWait(Constants.ONE_SECOND);
			foundation.objectFocus(MicroMarketMenuList.BTN_MENU_ADD);
			foundation.click(MicroMarketMenuList.BTN_MENU_ADD);
			foundation.waitforElement(MicroMarketMenuList.TXT_BTN_NAME, Constants.SHORT_TIME);
			textBox.enterText(MicroMarketMenuList.TXT_BTN_NAME, requiredData.get(2));
			foundation.click(MicroMarketMenuList.BTN_SUBMENU_ADD);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(MicroMarketMenuList.LBL_ADD_PRODUCTS);
			foundation.waitforElement(MicroMarketMenuList.TXT_SEARCH_PRODUCT, Constants.SHORT_TIME);
			textBox.enterText(MicroMarketMenuList.TXT_SEARCH_PRODUCT, requiredData.get(3));
			foundation.waitforElement(MicroMarketMenuList.LBL_PRODUCT_NAME, Constants.SHORT_TIME);
			foundation.click(MicroMarketMenuList.LBL_PRODUCT_NAME);
			foundation.click(MicroMarketMenuList.LBL_BTN_ADD);
			foundation.threadWait(Constants.THREE_SECOND);
			foundation.objectFocus(MicroMarketMenuList.BTN_SAVE);
			foundation.waitforClikableElement(MicroMarketMenuList.BTN_SAVE, Constants.SHORT_TIME);
			foundation.click(MicroMarketMenuList.BTN_SAVE);
			foundation.waitforElement(MicroMarketMenuList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			// navigating to Home Page
			foundation.click(LocationList.LINK_HOME_PAGE);

			// update language pre-condition and sync
			List<String> language = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
			locationSummary.kiosklanguageSetting(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE),
					language.get(0), language.get(1));

			// login into Kiosk Device
			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			landingPage.changeLanguage(language.get(2), language.get(0), language.get(3));
			// foundation.refreshPage();
			foundation.waitforElementToBeVisible(landingPage.objText(requiredData.get(2)), Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(landingPage.objText(requiredData.get(2))));
			foundation.click(landingPage.objText(requiredData.get(2)));
			foundation.click(landingPage.objText(requiredData.get(3)));
			CustomisedAssert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(0))));
			CustomisedAssert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(1))));
			CustomisedAssert.assertTrue(foundation.isDisplayed(order.objText(requiredData.get(3))));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// resetting testdata
			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			textBox.enterText(MicroMarketMenuList.FILTER_MENU, requiredData.get(1));
			foundation.click(microMarketMenu.menuNameObj(requiredData.get(1)));
			foundation.click(MicroMarketMenuList.BTN_DELETE);
			foundation.alertAccept();
			// navigating to Home Page
			foundation.threadWait(Constants.ONE_SECOND);
			foundation.click(LocationList.LINK_HOME_PAGE);
			locationList.selectLocationName(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(LocationSummary.BTN_SYNC);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			foundation.waitforElementToDisappear(LocationList.TXT_SPINNER_MSG, Constants.EXTRA_LONG_TIME);
			login.logout();
		}
	}

	@Test(description = "142836-QAA-45 - Verify Sales transaction with email is completed in Kiosk")
	public void salesTransactionViaEmail() {
		try {
			final String CASE_NUM = "142836";
			// Reading test data from DataBase
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			List<String> requiredData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> language = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Selecting location
			locationList.selectLocationName(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));

			dropDown.selectItem(LocationSummary.DPD_KIOSK_LANGUAGE, requiredData.get(1), Constants.TEXT);
			foundation.click(LocationSummary.BTN_SYNC);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			foundation.waitforElementToDisappear(LocationList.TXT_SPINNER_MSG, Constants.EXTRA_LONG_TIME);
			login.logout();
			browser.close();

			foundation.threadWait(Constants.SHORT_TIME);
			// login into Kiosk Device
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			landingPage.changeLanguage(language.get(2), language.get(0), language.get(3));

			// Navigating to product search page
			foundation.waitforElement(LandingPage.IMG_SEARCH_ICON, Constants.SHORT_TIME);
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			foundation.waitforElement(AccountLogin.BTN_CAMELCASE, Constants.SHORT_TIME);

			// searching for product
			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
			foundation.click(ProductSearch.BTN_PRODUCT);

			// verify Order Page
			List<String> orderPageData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.ORDER_PAGE).split(Constants.DELIMITER_TILD));
			CustomisedAssert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(0))));

			foundation.objectFocus(order.objText(orderPageData.get(1)));
			foundation.click(order.objText(orderPageData.get(1)));

			foundation.waitforElement(AccountLogin.BTN_NEXT, Constants.SHORT_TIME);

			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(
					propertyFile.readPropertyFile(Configuration.V5_USER, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(AccountLogin.BTN_NEXT);
			foundation.waitforElement(AccountLogin.BTN_PIN_NEXT, Constants.SHORT_TIME);
			textBox.enterPin(propertyFile.readPropertyFile(Configuration.V5_PIN, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(AccountLogin.BTN_PIN_NEXT);

			List<String> paymentPageData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.PAYMENTS_PAGE).split(Constants.DELIMITER_TILD));
			CustomisedAssert.assertTrue(foundation.isDisplayed(payments.objText(paymentPageData.get(0))));

			foundation.click(payments.objText(paymentPageData.get(1)));
			foundation.waitforElement(LandingPage.IMG_SEARCH_ICON, Constants.SHORT_TIME);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "142845-QAA-45-Verify the product stock reduced in ADM when Sales transaction with email is completed in Kiosk")
	public void salesTransactionViaEmailProductCount() {
		try {
			final String CASE_NUM = "142845";
			// Reading test data from DataBase
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			List<String> requiredData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> language = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Selecting location
			locationList.selectLocationName(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));

			dropDown.selectItem(LocationSummary.DPD_KIOSK_LANGUAGE, requiredData.get(1), Constants.TEXT);
			locationSummary.selectTab(requiredData.get(2));

			foundation.waitforElement(LocationSummary.TBL_PRODUCTS_GRID, Constants.SHORT_TIME);
			textBox.enterText(LocationSummary.TXT_PRODUCT_FILTER, rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
			Map<String, String> invCountBeforeSale = locationSummary.getProductDetails(requiredData.get(3));

			foundation.click(LocationSummary.BTN_SYNC);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			foundation.waitforElementToDisappear(LocationList.TXT_SPINNER_MSG, Constants.EXTRA_LONG_TIME);
			login.logout();
			browser.close();

			foundation.threadWait(Constants.SHORT_TIME);
			// login into Kiosk Device
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			landingPage.changeLanguage(language.get(2), language.get(0), language.get(3));

			// Navigating to product search page
			foundation.waitforElement(LandingPage.IMG_SEARCH_ICON, Constants.SHORT_TIME);
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			foundation.waitforElement(AccountLogin.BTN_CAMELCASE, Constants.SHORT_TIME);

			// searching for product
			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
			foundation.click(ProductSearch.BTN_PRODUCT);

			// verify Order Page
			List<String> orderPageData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.ORDER_PAGE).split(Constants.DELIMITER_TILD));
			CustomisedAssert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(0))));

			foundation.objectFocus(order.objText(orderPageData.get(1)));
			foundation.click(order.objText(orderPageData.get(1)));

			foundation.waitforElement(AccountLogin.BTN_NEXT, Constants.SHORT_TIME);

			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(
					propertyFile.readPropertyFile(Configuration.V5_USER, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(AccountLogin.BTN_NEXT);
			foundation.waitforElement(AccountLogin.BTN_PIN_NEXT, Constants.SHORT_TIME);
			textBox.enterPin(propertyFile.readPropertyFile(Configuration.V5_PIN, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(AccountLogin.BTN_PIN_NEXT);

			List<String> paymentPageData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.PAYMENTS_PAGE).split(Constants.DELIMITER_TILD));
			CustomisedAssert.assertTrue(foundation.isDisplayed(payments.objText(paymentPageData.get(0))));

			foundation.click(payments.objText(paymentPageData.get(1)));
			foundation.waitforElement(LandingPage.IMG_SEARCH_ICON, Constants.SHORT_TIME);

			browser.close();

			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Selecting location
			locationList.selectLocationName(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));

			dropDown.selectItem(LocationSummary.DPD_KIOSK_LANGUAGE, requiredData.get(1), Constants.TEXT);
			locationSummary.selectTab(requiredData.get(2));

			foundation.waitforElement(LocationSummary.TBL_PRODUCTS_GRID, Constants.SHORT_TIME);
			textBox.enterText(LocationSummary.TXT_PRODUCT_FILTER, rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
			Map<String, String> invCountAfterSale = locationSummary.getProductDetails(requiredData.get(3));

			CustomisedAssert.assertTrue(Integer.valueOf(invCountAfterSale.get(requiredData.get(3))) == Integer
					.valueOf(invCountBeforeSale.get(requiredData.get(3))) - 1);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "142846-QAA-45-Verify the Stock reduced in ADM when Sales transaction with email when Account balance is less than product amount")
	public void salesTransactionWithNoAccountBalance() {
		final String CASE_NUM = "142846";
		// Reading test data from DataBase

		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		List<String> navigationMenu = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> language = Arrays.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
		try {
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstConsumerSearchData = dataBase.getConsumerSearchData(Queries.CONSUMER_SEARCH, CASE_NUM);
			rstConsumerSummaryData = dataBase.getConsumerSummaryData(Queries.CONSUMER_SUMMARY, CASE_NUM);

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(navigationMenu.get(0));

			// Enter fields in Consumer Search Page
			consumerSearch.enterSearchFields(rstConsumerSearchData.get(CNConsumerSearch.SEARCH_BY),
					rstConsumerSearchData.get(CNConsumerSearch.CONSUMER_ID),
					rstConsumerSearchData.get(CNConsumerSearch.LOCATION),
					rstConsumerSearchData.get(CNConsumerSearch.STATUS));

			// clicking consumer id
			foundation.click(consumerSearch.objCell(rstConsumerSearchData.get(CNConsumerSearch.COLUMN_NAME)));
			foundation.click(ConsumerSearch.BTN_ADJUST);

			// Enter new balance with reason
			textBox.enterText(ConsumerSummary.TXT_ADJUST_BALANCE,
					rstConsumerSummaryData.get(CNConsumerSummary.ADJUST_BALANCE));
			dropDown.selectItem(ConsumerSummary.DPD_REASON, rstConsumerSummaryData.get(CNConsumerSummary.REASON),
					Constants.TEXT);
			foundation.click(ConsumerSummary.BTN_REASON_SAVE);
			foundation.click(ConsumerSummary.BTN_SAVE);
			foundation.waitforElement(ConsumerSearch.DPD_SEARCH_BY, Constants.SHORT_TIME);

			// Select Menu and Menu Item
			navigationBar.navigateToMenuItem(navigationMenu.get(1));

			List<String> requiredData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			// Selecting location
			locationList.selectLocationName(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));

			// update Kiosk Language
			dropDown.selectItem(LocationSummary.DPD_KIOSK_LANGUAGE, requiredData.get(1), Constants.TEXT);
			foundation.click(LocationSummary.BTN_SYNC);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			foundation.waitforElementToDisappear(LocationList.TXT_SPINNER_MSG, Constants.EXTRA_LONG_TIME);
			login.logout();
			browser.close();

			foundation.threadWait(Constants.SHORT_TIME);
			// login into Kiosk Device
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			landingPage.changeLanguage(language.get(2), language.get(0), language.get(3));

			// Navigating to product search page
			foundation.waitforElement(LandingPage.IMG_SEARCH_ICON, Constants.SHORT_TIME);
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			foundation.waitforElement(AccountLogin.BTN_CAMELCASE, Constants.SHORT_TIME);

			// searching for product
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
			foundation.click(ProductSearch.BTN_PRODUCT);

			// verify Order Page
			List<String> orderPageData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.ORDER_PAGE).split(Constants.DELIMITER_TILD));
			CustomisedAssert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(0))));

			foundation.objectFocus(order.objText(orderPageData.get(1)));
			foundation.click(order.objText(orderPageData.get(1)));

			foundation.waitforElement(AccountLogin.BTN_NEXT, Constants.SHORT_TIME);

			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(rstConsumerSearchData.get(CNConsumerSearch.CONSUMER_ID));
			foundation.click(AccountLogin.BTN_NEXT);
			foundation.waitforElement(AccountLogin.BTN_PIN_NEXT, Constants.SHORT_TIME);
			textBox.enterPin(propertyFile.readPropertyFile(Configuration.V5_PIN, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(AccountLogin.BTN_PIN_NEXT);

			List<String> paymentPageData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.PAYMENTS_PAGE).split(Constants.DELIMITER_TILD));
			CustomisedAssert.assertTrue(foundation.isDisplayed(payments.objText(paymentPageData.get(0))));

			foundation.click(payments.objText(paymentPageData.get(1)));
			CustomisedAssert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(0))));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			browser.close();

			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.navigateToMenuItem(navigationMenu.get(0));

			// Enter fields in Consumer Search Page
			consumerSearch.enterSearchFields(rstConsumerSearchData.get(CNConsumerSearch.SEARCH_BY),
					rstConsumerSearchData.get(CNConsumerSearch.CONSUMER_ID),
					rstConsumerSearchData.get(CNConsumerSearch.LOCATION),
					rstConsumerSearchData.get(CNConsumerSearch.STATUS));

			// clicking consumer id
			foundation.click(consumerSearch.objCell(rstConsumerSearchData.get(CNConsumerSearch.COLUMN_NAME)));
			foundation.click(ConsumerSearch.BTN_ADJUST);

			// enter new balance with reason
			textBox.enterText(ConsumerSummary.TXT_ADJUST_BALANCE, rstConsumerSummaryData.get(CNConsumerSummary.AMOUNT));
			dropDown.selectItem(ConsumerSummary.DPD_REASON, rstConsumerSummaryData.get(CNConsumerSummary.REASON),
					Constants.TEXT);
			foundation.click(ConsumerSummary.BTN_REASON_SAVE);
			foundation.click(ConsumerSummary.BTN_SAVE);
			foundation.waitforClikableElement(ConsumerSearch.DPD_SEARCH_BY, Constants.EXTRA_LONG_TIME);
		}
	}

	@Test(description = "142847-QAA-45 - Verify Sales transaction with email failed when the stock is zero")
	public void salesTransactionWhenNoStock() {
		final String CASE_NUM = "142847";
		// Reading test data from DataBase
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		List<String> requiredData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			locationList.selectLocationName(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));
			locationSummary.selectTab(requiredData.get(2));
			foundation.waitforElement(LocationSummary.TBL_INVENTORY, Constants.EXTRA_LONG_TIME);
			locationSummary.updateInventory(requiredData.get(3), requiredData.get(4), requiredData.get(5));
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.DPD_LOCATION_LIST, Constants.SHORT_TIME);
			List<String> language = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
			locationSummary.kiosklanguageSetting(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE),
					language.get(0), language.get(1));

			// login into Kiosk Device
			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));

			// Navigating to product search page
			landingPage.changeLanguage(language.get(2), language.get(0), language.get(3));
			foundation.waitforElement(LandingPage.IMG_SEARCH_ICON, Constants.SHORT_TIME);

			foundation.click(LandingPage.IMG_SEARCH_ICON);
			foundation.waitforElement(AccountLogin.BTN_CAMELCASE, Constants.SHORT_TIME);

			// searching for product
			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
			foundation.click(ProductSearch.BTN_PRODUCT);

			// verify Order Page
			List<String> orderPageData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.ORDER_PAGE).split(Constants.DELIMITER_TILD));
			CustomisedAssert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(0))));
			foundation.objectFocus(order.objText(orderPageData.get(1)));
			foundation.click(order.objText(orderPageData.get(1)));

			foundation.waitforElement(AccountLogin.BTN_NEXT, Constants.SHORT_TIME);

			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(
					propertyFile.readPropertyFile(Configuration.V5_USER, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(AccountLogin.BTN_NEXT);
			foundation.waitforElement(AccountLogin.BTN_PIN_NEXT, Constants.SHORT_TIME);
			textBox.enterPin(propertyFile.readPropertyFile(Configuration.V5_PIN, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(AccountLogin.BTN_PIN_NEXT);

			List<String> paymentPageData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.PAYMENTS_PAGE).split(Constants.DELIMITER_TILD));
			CustomisedAssert.assertTrue(foundation.isDisplayed(payments.objText(paymentPageData.get(0))));
			foundation.click(payments.objText(paymentPageData.get(1)));
			foundation.waitforElement(LandingPage.IMG_SEARCH_ICON, Constants.SHORT_TIME);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());

		} finally {
			browser.close();
			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Selecting location
			locationList.selectLocationName(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));
			dropDown.selectItem(LocationSummary.DPD_KIOSK_LANGUAGE, requiredData.get(1), Constants.TEXT);
			locationSummary.selectTab(requiredData.get(2));
			foundation.waitforElement(LocationSummary.TBL_INVENTORY, Constants.SHORT_TIME);
			locationSummary.updateInventory(requiredData.get(3), requiredData.get(6), requiredData.get(7));
			foundation.click(LocationSummary.BTN_SYNC);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
		}
	}

	@Test(description = "143143-V5-Verify the VAT on Kiosk when user set only Tax Rate1 in ADM for UK")

	public void verifyVATwithTaxRate1() {
		final String CASE_NUM = "143143";
		try {
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			List<String> menu = Arrays
					.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
			List<String> country = Arrays
					.asList(rstLocationSummaryData.get(CNLocationSummary.COUNTRY).split(Constants.DELIMITER_TILD));
			List<String> requiredData = Arrays.asList(
					rstLocationSummaryData.get(CNLocationSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> actualData = Arrays
					.asList(rstLocationSummaryData.get(CNLocationSummary.ACTUAL_DATA).split(Constants.DELIMITER_TILD));
			List<String> language = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));

			navigationBar.navigateToMenuItem(menu.get(0));
			dropDown.selectItem(OrgSummary.DPD_COUNTRY, country.get(0), Constants.TEXT);
			dropDown.selectItem(OrgSummary.DPD_TAX_SYSTEM, requiredData.get(0), Constants.TEXT);
			foundation.click(OrgSummary.BTN_SAVE);
			foundation.waitforElement(OrgList.LBL_ORG_LIST, Constants.SHORT_TIME);
			navigationBar.navigateToMenuItem(menu.get(1));
			foundation.waitforElement(LocationList.TXT_FILTER, Constants.SHORT_TIME);
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			locationSummary.saveTaxMapping(actualData.get(0), actualData.get(1));
			CustomisedAssert.assertTrue(foundation.isDisplayed(locationSummary.objVerifyTaxRate(actualData.get(1))));
			foundation.click(LocationSummary.BTN_SAVE);
			locationSummary.kiosklanguageSetting(rstLocationListData.get(CNLocationList.LOCATION_NAME), language.get(0),
					language.get(1));

			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(landingPage.objLanguage(language.get(0)));
			foundation.refreshPage();
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(rstLocationSummaryData.get(CNLocationSummary.PRODUCT_NAME));
			foundation.click(ProductSearch.BTN_PRODUCT);
			CustomisedAssert.assertTrue(foundation.isDisplayed(Order.LBL_YOUR_ORDER));
			CustomisedAssert.assertTrue(
					foundation.isDisplayed(order.objText(rstLocationSummaryData.get(CNLocationSummary.PRODUCT_NAME))));
			order.verifyVAT(rstV5DeviceData.get(CNV5Device.ACTUAL_DATA));
			foundation.click(Order.LBL_EMAIL);
			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.EMAIL_ID));
			foundation.click(AccountLogin.BTN_NEXT);
			textBox.enterPin(rstV5DeviceData.get(CNV5Device.PIN));
			foundation.click(AccountLogin.BTN_PIN_NEXT);
			CustomisedAssert
					.assertTrue(foundation.isDisplayed(order.objText(rstV5DeviceData.get(CNV5Device.PAYMENTS_PAGE))));
			browser.close();

			// Reset the data
			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.navigateToMenuItem(menu.get(0));
			dropDown.selectItem(OrgSummary.DPD_COUNTRY, country.get(1), Constants.TEXT);
			dropDown.selectItem(OrgSummary.DPD_TAX_SYSTEM, requiredData.get(1), Constants.TEXT);
			foundation.click(OrgSummary.BTN_SAVE);
			foundation.waitforElement(OrgList.LBL_ORG_LIST, Constants.SHORT_TIME);
			navigationBar.navigateToMenuItem(menu.get(1));
			foundation.waitforElement(LocationList.TXT_FILTER, Constants.SHORT_TIME);
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.LNK_TAX_MAPPING);
			foundation.click(locationSummary.objVerifyTaxRate(actualData.get(1)));
			foundation.waitforElement(LocationSummary.BTN_POPUP_REMOVE, Constants.SHORT_TIME);
			foundation.click(LocationSummary.BTN_POPUP_REMOVE);
			foundation.click(LocationSummary.LNK_TAX_MAPPING);
			foundation.click(LocationSummary.BTN_SYNC);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_FILTER, Constants.SHORT_TIME);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "143144-V5-Verify the VAT on Kiosk when user set only Tax Rate1,2,3,4 in ADM for UK")
	public void verifyVATwithAllTaxRate() {
		final String CASE_NUM = "143144";
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);

		List<String> menu = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> country = Arrays
				.asList(rstLocationSummaryData.get(CNLocationSummary.COUNTRY).split(Constants.DELIMITER_TILD));
		List<String> requiredData = Arrays
				.asList(rstLocationSummaryData.get(CNLocationSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> actualData = Arrays
				.asList(rstLocationSummaryData.get(CNLocationSummary.ACTUAL_DATA).split(Constants.DELIMITER_TILD));

		try {
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
			List<String> language = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
			List<String> product = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME).split(Constants.DELIMITER_TILD));

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.navigateToMenuItem(menu.get(0));
			dropDown.selectItem(OrgSummary.DPD_COUNTRY, country.get(0), Constants.TEXT);
			dropDown.selectItem(OrgSummary.DPD_TAX_SYSTEM, requiredData.get(0), Constants.TEXT);
			foundation.click(OrgSummary.BTN_SAVE);
			foundation.waitforElement(OrgList.LBL_ORG_LIST, Constants.SHORT_TIME);
			navigationBar.navigateToMenuItem(menu.get(1));
			foundation.waitforElement(LocationList.TXT_FILTER, Constants.SHORT_TIME);
			locationList.selectLocationName(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));
			locationSummary.saveTaxMapping(actualData.get(0), actualData.get(1));
			CustomisedAssert.assertTrue(foundation.isDisplayed(locationSummary.objVerifyTaxRate(actualData.get(1))));
			foundation.click(LocationSummary.BTN_SAVE);
			locationSummary.kiosklanguageSetting(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE),
					language.get(0), language.get(1));

			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			landingPage.changeLanguage(language.get(2), language.get(0), language.get(3));

			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(product.get(0));
			foundation.click(ProductSearch.BTN_PRODUCT);
			CustomisedAssert.assertTrue(foundation.isDisplayed(Order.LBL_YOUR_ORDER));
			CustomisedAssert.assertTrue(foundation.isDisplayed(order.objText(product.get(0))));
			order.verifyVAT(rstV5DeviceData.get(CNV5Device.ACTUAL_DATA));
			foundation.click(Order.BTN_CANCEL_ORDER);
			CustomisedAssert.assertTrue(foundation.isDisplayed(Order.LBL_ORDER_CANCELLED));
			foundation.waitforClikableElement(LandingPage.IMG_SEARCH_ICON, Constants.SHORT_TIME);
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(product.get(1));
			foundation.click(ProductSearch.BTN_PRODUCT);
			CustomisedAssert.assertTrue(foundation.isDisplayed(Order.LBL_YOUR_ORDER));
			CustomisedAssert.assertTrue(foundation.isDisplayed(order.objText(product.get(1))));
			order.verifyVAT(rstV5DeviceData.get(CNV5Device.ACTUAL_DATA));

			foundation.objectFocus(Order.LBL_EMAIL);
			foundation.click(Order.LBL_EMAIL);
			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(
					propertyFile.readPropertyFile(Configuration.V5_USER, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(AccountLogin.BTN_NEXT);
			textBox.enterPin(rstV5DeviceData.get(CNV5Device.PIN));
			foundation.click(AccountLogin.BTN_PIN_NEXT);
			CustomisedAssert
					.assertTrue(foundation.isDisplayed(order.objText(rstV5DeviceData.get(CNV5Device.PAYMENTS_PAGE))));
			browser.close();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// Reset the data
			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.navigateToMenuItem(menu.get(0));
			dropDown.selectItem(OrgSummary.DPD_COUNTRY, country.get(1), Constants.TEXT);
			dropDown.selectItem(OrgSummary.DPD_TAX_SYSTEM, requiredData.get(1), Constants.TEXT);
			foundation.click(OrgSummary.BTN_SAVE);
			foundation.waitforElement(OrgList.LBL_ORG_LIST, Constants.SHORT_TIME);
			navigationBar.navigateToMenuItem(menu.get(1));
			foundation.waitforElement(LocationList.TXT_FILTER, Constants.SHORT_TIME);
			locationList.selectLocationName(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(LocationSummary.LNK_TAX_MAPPING);
			foundation.click(locationSummary.objVerifyTaxRate(actualData.get(1)));
			foundation.waitforElement(LocationSummary.BTN_POPUP_REMOVE, Constants.SHORT_TIME);
			foundation.click(LocationSummary.BTN_POPUP_REMOVE);
			foundation.click(LocationSummary.LNK_TAX_MAPPING);
			foundation.click(LocationSummary.BTN_SYNC);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_FILTER, Constants.SHORT_TIME);
		}
	}

	@Test(description = "143145-V5-Verify the VAT on Kiosk when user set only Tax Rate1,2,3,4 as zero in ADM for UK")
	public void verifyVATwithTaxZero() {

		final String CASE_NUM = "143145";
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);

		List<String> menu = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> country = Arrays
				.asList(rstLocationSummaryData.get(CNLocationSummary.COUNTRY).split(Constants.DELIMITER_TILD));
		List<String> requiredData = Arrays
				.asList(rstLocationSummaryData.get(CNLocationSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> actualData = Arrays
				.asList(rstLocationSummaryData.get(CNLocationSummary.ACTUAL_DATA).split(Constants.DELIMITER_TILD));

		try {
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
			List<String> language = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
			List<String> product = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME).split(Constants.DELIMITER_TILD));

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			locationList.selectLocationName(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));
			foundation.objectFocus(LocationSummary.BTN_DEPLOY_DEVICE);
			List<String> deviceData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.ACTUAL_DATA).split(Constants.DELIMITER_TILD));
			textBox.enterText(LocationSummary.TXT_DEVICE_SEARCH, deviceData.get(0));
			locationSummary.selectDeviceName(deviceData.get(0));
			foundation.waitforElement(DeviceSummary.LBL_DEVICE_SUMMARY, Constants.LONG_TIME);
			Foundation.getDriver().findElement(DeviceSummary.TXT_SCREEN_TIMEOUT).clear();
			textBox.enterText(DeviceSummary.TXT_SCREEN_TIMEOUT, deviceData.get(1));
			foundation.click(DeviceSummary.BTN_SAVE);

			navigationBar.navigateToMenuItem(menu.get(0));
			dropDown.selectItem(OrgSummary.DPD_COUNTRY, country.get(0), Constants.TEXT);
			dropDown.selectItem(OrgSummary.DPD_TAX_SYSTEM, requiredData.get(0), Constants.TEXT);
			foundation.click(OrgSummary.BTN_SAVE);
			foundation.waitforElement(OrgList.LBL_ORG_LIST, Constants.SHORT_TIME);
			navigationBar.navigateToMenuItem(menu.get(1));
			foundation.waitforElement(LocationList.TXT_FILTER, Constants.SHORT_TIME);
			locationList.selectLocationName(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));
			locationSummary.saveTaxMapping(actualData.get(0), actualData.get(1));
			CustomisedAssert.assertTrue(foundation.isDisplayed(locationSummary.objVerifyTaxRate(actualData.get(1))));
			foundation.click(LocationSummary.BTN_SAVE);
			locationSummary.kiosklanguageSetting(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE),
					language.get(0), language.get(1));

			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			landingPage.changeLanguage(language.get(2), language.get(0), language.get(3));

			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(product.get(0));
			foundation.click(ProductSearch.BTN_PRODUCT);
			CustomisedAssert.assertTrue(foundation.isDisplayed(Order.LBL_YOUR_ORDER));
			CustomisedAssert.assertTrue(foundation.isDisplayed(order.objText(product.get(0))));
			CustomisedAssert.assertFalse(foundation.isDisplayed(Order.LBL_VAT_VALUE));

			foundation.objectFocus(Order.LBL_EMAIL);
			foundation.click(Order.LBL_EMAIL);
			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(
					propertyFile.readPropertyFile(Configuration.V5_USER, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(AccountLogin.BTN_NEXT);
			textBox.enterPin(rstV5DeviceData.get(CNV5Device.PIN));
			foundation.click(AccountLogin.BTN_PIN_NEXT);
			foundation.waitforElement(order.objText(rstV5DeviceData.get(CNV5Device.PAYMENTS_PAGE)),
					Constants.SHORT_TIME);
			CustomisedAssert
					.assertTrue(foundation.isDisplayed(order.objText(rstV5DeviceData.get(CNV5Device.PAYMENTS_PAGE))));
			browser.close();

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {

			// Reset the data
			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.navigateToMenuItem(menu.get(0));
			dropDown.selectItem(OrgSummary.DPD_COUNTRY, country.get(1), Constants.TEXT);
			dropDown.selectItem(OrgSummary.DPD_TAX_SYSTEM, requiredData.get(1), Constants.TEXT);
			foundation.click(OrgSummary.BTN_SAVE);
			foundation.waitforElement(OrgList.LBL_ORG_LIST, Constants.SHORT_TIME);
			navigationBar.navigateToMenuItem(menu.get(1));
			foundation.waitforElement(LocationList.TXT_FILTER, Constants.SHORT_TIME);
			locationList.selectLocationName(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(LocationSummary.LNK_TAX_MAPPING);
			foundation.click(locationSummary.objVerifyTaxRate(actualData.get(1)));
			foundation.waitforElement(LocationSummary.BTN_POPUP_REMOVE, Constants.SHORT_TIME);
			foundation.click(LocationSummary.BTN_POPUP_REMOVE);
			foundation.click(LocationSummary.LNK_TAX_MAPPING);
			foundation.click(LocationSummary.BTN_SYNC);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_FILTER, Constants.SHORT_TIME);
		}
	}

	@Test(description = "143146-V5-Verify the VAT on Kiosk when user adds a product not assigned to the UK TAX Rate")
	public void verifyVATProductNotAssignedUKTax() {

		final String CASE_NUM = "143146";

		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);

		List<String> menu = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> country = Arrays
				.asList(rstLocationSummaryData.get(CNLocationSummary.COUNTRY).split(Constants.DELIMITER_TILD));
		List<String> requiredData = Arrays
				.asList(rstLocationSummaryData.get(CNLocationSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> actualData = Arrays
				.asList(rstLocationSummaryData.get(CNLocationSummary.ACTUAL_DATA).split(Constants.DELIMITER_TILD));

		try {
			List<String> language = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> product = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME).split(Constants.DELIMITER_TILD));

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			locationList.selectLocationName(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));
			foundation.objectFocus(LocationSummary.BTN_DEPLOY_DEVICE);
			List<String> deviceData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.ACTUAL_DATA).split(Constants.DELIMITER_TILD));
			textBox.enterText(LocationSummary.TXT_DEVICE_SEARCH, deviceData.get(0));
			locationSummary.selectDeviceName(deviceData.get(0));
			foundation.waitforElement(DeviceSummary.LBL_DEVICE_SUMMARY, Constants.LONG_TIME);
			Foundation.getDriver().findElement(DeviceSummary.TXT_SCREEN_TIMEOUT).clear();
			textBox.enterText(DeviceSummary.TXT_SCREEN_TIMEOUT, deviceData.get(1));
			foundation.click(DeviceSummary.BTN_SAVE);

			navigationBar.navigateToMenuItem(menu.get(0));
			dropDown.selectItem(OrgSummary.DPD_COUNTRY, country.get(0), Constants.TEXT);
			dropDown.selectItem(OrgSummary.DPD_TAX_SYSTEM, requiredData.get(0), Constants.TEXT);
			foundation.click(OrgSummary.BTN_SAVE);
			foundation.waitforElement(OrgList.LBL_ORG_LIST, Constants.SHORT_TIME);
			navigationBar.navigateToMenuItem(menu.get(1));
			foundation.waitforElement(LocationList.TXT_FILTER, Constants.SHORT_TIME);
			locationList.selectLocationName(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));
			locationSummary.saveTaxMapping(actualData.get(0), actualData.get(1));
			CustomisedAssert.assertTrue(foundation.isDisplayed(locationSummary.objVerifyTaxRate(actualData.get(1))));
			foundation.click(LocationSummary.BTN_SAVE);
			locationSummary.kiosklanguageSetting(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE),
					language.get(0), language.get(1));

			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			landingPage.changeLanguage(language.get(2), language.get(0), language.get(3));

			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(product.get(0));
			foundation.click(ProductSearch.BTN_PRODUCT);
			CustomisedAssert.assertTrue(foundation.isDisplayed(Order.LBL_YOUR_ORDER));
			CustomisedAssert.assertTrue(foundation.isDisplayed(order.objText(product.get(0))));
			CustomisedAssert.assertFalse(foundation.isDisplayed(Order.LBL_VAT_VALUE));

			foundation.objectFocus(Order.LBL_EMAIL);
			foundation.click(Order.LBL_EMAIL);
			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(
					propertyFile.readPropertyFile(Configuration.V5_USER, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(AccountLogin.BTN_NEXT);
			textBox.enterPin(rstV5DeviceData.get(CNV5Device.PIN));
			foundation.click(AccountLogin.BTN_PIN_NEXT);
			CustomisedAssert
					.assertTrue(foundation.isDisplayed(order.objText(rstV5DeviceData.get(CNV5Device.PAYMENTS_PAGE))));
			browser.close();

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {

			// Reset the data
			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.navigateToMenuItem(menu.get(0));
			dropDown.selectItem(OrgSummary.DPD_COUNTRY, country.get(1), Constants.TEXT);
			dropDown.selectItem(OrgSummary.DPD_TAX_SYSTEM, requiredData.get(1), Constants.TEXT);
			foundation.click(OrgSummary.BTN_SAVE);
			foundation.waitforElement(OrgList.LBL_ORG_LIST, Constants.SHORT_TIME);
			navigationBar.navigateToMenuItem(menu.get(1));
			foundation.waitforElement(LocationList.TXT_FILTER, Constants.SHORT_TIME);
			locationList.selectLocationName(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(LocationSummary.LNK_TAX_MAPPING);
			foundation.click(locationSummary.objVerifyTaxRate(actualData.get(1)));
			foundation.waitforElement(LocationSummary.BTN_POPUP_REMOVE, Constants.SHORT_TIME);
			foundation.click(LocationSummary.BTN_POPUP_REMOVE);
			foundation.click(LocationSummary.LNK_TAX_MAPPING);
			foundation.click(LocationSummary.BTN_SYNC);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_FILTER, Constants.SHORT_TIME);
		}
	}

	@Test(description = "143147-V5-Verify the VAT on Kiosk when user adds a product not assigned to the UK TAX Rate")
	public void verifyVATWithOutTaxMapping() {
		final String CASE_NUM = "143147";

		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);

		List<String> menu = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> country = Arrays
				.asList(rstLocationSummaryData.get(CNLocationSummary.COUNTRY).split(Constants.DELIMITER_TILD));
		List<String> requiredData = Arrays
				.asList(rstLocationSummaryData.get(CNLocationSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));

		try {
			List<String> language = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> product = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME).split(Constants.DELIMITER_TILD));

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			locationList.selectLocationName(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));
			foundation.objectFocus(LocationSummary.BTN_DEPLOY_DEVICE);
			List<String> deviceData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.ACTUAL_DATA).split(Constants.DELIMITER_TILD));
			textBox.enterText(LocationSummary.TXT_DEVICE_SEARCH, deviceData.get(0));
			locationSummary.selectDeviceName(deviceData.get(0));
			foundation.waitforElement(DeviceSummary.LBL_DEVICE_SUMMARY, Constants.LONG_TIME);
			Foundation.getDriver().findElement(DeviceSummary.TXT_SCREEN_TIMEOUT).clear();
			textBox.enterText(DeviceSummary.TXT_SCREEN_TIMEOUT, deviceData.get(1));
			foundation.click(DeviceSummary.BTN_SAVE);

			navigationBar.navigateToMenuItem(menu.get(0));
			dropDown.selectItem(OrgSummary.DPD_COUNTRY, country.get(0), Constants.TEXT);
			dropDown.selectItem(OrgSummary.DPD_TAX_SYSTEM, requiredData.get(0), Constants.TEXT);
			foundation.click(OrgSummary.BTN_SAVE);
			foundation.waitforElement(OrgList.LBL_ORG_LIST, Constants.SHORT_TIME);
			navigationBar.navigateToMenuItem(menu.get(1));
			foundation.waitforElement(LocationList.TXT_FILTER, Constants.SHORT_TIME);
			locationSummary.kiosklanguageSetting(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE),
					language.get(0), language.get(1));

			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			landingPage.changeLanguage(language.get(2), language.get(0), language.get(3));

			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(product.get(0));
			foundation.click(ProductSearch.BTN_PRODUCT);
			CustomisedAssert.assertTrue(foundation.isDisplayed(Order.LBL_YOUR_ORDER));
			CustomisedAssert.assertTrue(foundation.isDisplayed(order.objText(product.get(0))));
			CustomisedAssert.assertFalse(foundation.isDisplayed(Order.LBL_VAT_VALUE));

			foundation.objectFocus(Order.LBL_EMAIL);
			foundation.click(Order.LBL_EMAIL);
			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(
					propertyFile.readPropertyFile(Configuration.V5_USER, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(AccountLogin.BTN_NEXT);
			textBox.enterPin(rstV5DeviceData.get(CNV5Device.PIN));
			foundation.click(AccountLogin.BTN_PIN_NEXT);
			CustomisedAssert
					.assertTrue(foundation.isDisplayed(order.objText(rstV5DeviceData.get(CNV5Device.PAYMENTS_PAGE))));
			browser.close();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// Reset the data
			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.navigateToMenuItem(menu.get(0));
			dropDown.selectItem(OrgSummary.DPD_COUNTRY, country.get(1), Constants.TEXT);
			dropDown.selectItem(OrgSummary.DPD_TAX_SYSTEM, requiredData.get(1), Constants.TEXT);
			foundation.click(OrgSummary.BTN_SAVE);
			foundation.waitforElement(OrgList.LBL_ORG_LIST, Constants.SHORT_TIME);
			navigationBar.navigateToMenuItem(menu.get(1));
			foundation.waitforElement(LocationList.TXT_FILTER, Constants.SHORT_TIME);
			locationList.selectLocationName(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(LocationSummary.BTN_SYNC);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_FILTER, Constants.SHORT_TIME);
		}
	}

	@Test(description = "143148-V5-Verify the VAT on Kiosk when user adds a Multiple products with UK TAX Rate")
	public void verifyVATWithMultipleProducts() {
		final String CASE_NUM = "143148";

		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);

		List<String> menu = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> country = Arrays
				.asList(rstLocationSummaryData.get(CNLocationSummary.COUNTRY).split(Constants.DELIMITER_TILD));
		List<String> requiredData = Arrays
				.asList(rstLocationSummaryData.get(CNLocationSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> actualData = Arrays
				.asList(rstLocationSummaryData.get(CNLocationSummary.ACTUAL_DATA).split(Constants.DELIMITER_TILD));
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			List<String> language = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> product = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME).split(Constants.DELIMITER_TILD));

			navigationBar.navigateToMenuItem(menu.get(0));
			dropDown.selectItem(OrgSummary.DPD_COUNTRY, country.get(0), Constants.TEXT);
			dropDown.selectItem(OrgSummary.DPD_TAX_SYSTEM, requiredData.get(0), Constants.TEXT);
			foundation.click(OrgSummary.BTN_SAVE);
			foundation.waitforElement(OrgList.LBL_ORG_LIST, Constants.SHORT_TIME);
			navigationBar.navigateToMenuItem(menu.get(1));
			foundation.waitforElement(LocationList.TXT_FILTER, Constants.SHORT_TIME);
			locationList.selectLocationName(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));
			locationSummary.saveTaxMapping(actualData.get(0), actualData.get(1));
			CustomisedAssert.assertTrue(foundation.isDisplayed(locationSummary.objVerifyTaxRate(actualData.get(1))));
			foundation.click(LocationSummary.BTN_SAVE);
			locationSummary.kiosklanguageSetting(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE),
					language.get(0), language.get(1));

			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			landingPage.changeLanguage(language.get(2), language.get(0), language.get(3));

			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(product.get(0));
			foundation.click(ProductSearch.BTN_PRODUCT);
			CustomisedAssert.assertTrue(foundation.isDisplayed(Order.LBL_YOUR_ORDER));
			CustomisedAssert.assertTrue(foundation.isDisplayed(order.objText(product.get(0))));
			order.verifyVAT(rstV5DeviceData.get(CNV5Device.ACTUAL_DATA));
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(product.get(1));
			foundation.click(ProductSearch.BTN_PRODUCT);
			CustomisedAssert.assertTrue(foundation.isDisplayed(Order.LBL_YOUR_ORDER));
			CustomisedAssert.assertTrue(foundation.isDisplayed(order.objText(product.get(1))));
			order.verifyVAT(rstV5DeviceData.get(CNV5Device.ACTUAL_DATA));

			foundation.objectFocus(Order.LBL_EMAIL);
			foundation.click(Order.LBL_EMAIL);
			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(
					propertyFile.readPropertyFile(Configuration.V5_USER, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(AccountLogin.BTN_NEXT);
			textBox.enterPin(rstV5DeviceData.get(CNV5Device.PIN));
			foundation.click(AccountLogin.BTN_PIN_NEXT);
			foundation.waitforElement(order.objText(rstV5DeviceData.get(CNV5Device.PAYMENTS_PAGE)),
					Constants.SHORT_TIME);
			CustomisedAssert
					.assertTrue(foundation.isDisplayed(order.objText(rstV5DeviceData.get(CNV5Device.PAYMENTS_PAGE))));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// Reset the data
			browser.close();
			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.navigateToMenuItem(menu.get(0));
			dropDown.selectItem(OrgSummary.DPD_COUNTRY, country.get(1), Constants.TEXT);
			dropDown.selectItem(OrgSummary.DPD_TAX_SYSTEM, requiredData.get(1), Constants.TEXT);
			foundation.click(OrgSummary.BTN_SAVE);
			foundation.waitforElement(OrgList.LBL_ORG_LIST, Constants.SHORT_TIME);
			navigationBar.navigateToMenuItem(menu.get(1));
			foundation.waitforElement(LocationList.TXT_FILTER, Constants.SHORT_TIME);
			locationList.selectLocationName(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(LocationSummary.LNK_TAX_MAPPING);
			foundation.click(locationSummary.objVerifyTaxRate(actualData.get(1)));
			foundation.waitforElement(LocationSummary.BTN_POPUP_REMOVE, Constants.SHORT_TIME);
			foundation.click(LocationSummary.BTN_POPUP_REMOVE);
			foundation.click(LocationSummary.LNK_TAX_MAPPING);
			foundation.click(LocationSummary.BTN_SYNC);
			foundation.threadWait(Constants.TWO_SECOND);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_FILTER, Constants.SHORT_TIME);
		}
	}

	@Test(description = "143112-Edit existing tax category name and verify edits applied to product or not on product summary page")
	public void editTaxCategory() {
		final String CASE_NUM = "143112";
		// Reading test data from DataBase
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		String productName = rstV5DeviceData.get(CNV5Device.PRODUCT_NAME);
		List<String> requiredData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> menuItem = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item and add the tax category to a global product
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItem.get(0));
			globalProduct.selectGlobalProduct(productName);
			dropDown.selectItem(ProductSummary.DPD_TAX_CATEGORY, requiredData.get(0), Constants.TEXT);
			foundation.click(ProductSummary.BTN_SAVE);

			// edit tax category
			navigationBar.navigateToMenuItem(menuItem.get(1));
			categoryList.selectCategory(requiredData.get(0));
			categorySummary.updateName(requiredData.get(1));
			navigationBar.navigateToMenuItem(menuItem.get(0));
			globalProduct.selectGlobalProduct(productName);
			CustomisedAssert.assertEquals(dropDown.getSelectedItem(ProductSummary.DPD_TAX_CATEGORY),
					requiredData.get(1));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// reset data
			dropDown.selectItem(ProductSummary.DPD_TAX_CATEGORY, requiredData.get(2), Constants.TEXT);
			foundation.click(ProductSummary.BTN_SAVE);
			navigationBar.navigateToMenuItem(menuItem.get(1));
			categoryList.selectCategory(requiredData.get(1));
			categorySummary.updateName(requiredData.get(0));
		}
	}

	@Test(description = "143114-QA-19-Edit existing category name and verify edits applied to product or not on product summary page")
	public void editCategories() {
		final String CASE_NUM = "143114";

		// Reading test data from DataBase
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		String productName = rstV5DeviceData.get(CNV5Device.PRODUCT_NAME);
		List<String> requiredData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> menuItem = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item and add the tax category to a global product
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItem.get(0));
			globalProduct.selectGlobalProduct(productName);
			dropDown.selectItem(ProductSummary.DPD_CATEGORY1, requiredData.get(0), Constants.TEXT);
			dropDown.selectItem(ProductSummary.DPD_CATEGORY2, requiredData.get(1), Constants.TEXT);
			dropDown.selectItem(ProductSummary.DPD_CATEGORY3, requiredData.get(2), Constants.TEXT);
			foundation.click(ProductSummary.BTN_SAVE);

			// update tax categories
			navigationBar.navigateToMenuItem(menuItem.get(1));
			categoryList.selectCategory(requiredData.get(0));
			categorySummary.updateName(requiredData.get(3));
			foundation.threadWait(Constants.TWO_SECOND);
			categoryList.selectCategory(requiredData.get(1));
			categorySummary.updateName(requiredData.get(4));
			foundation.threadWait(Constants.TWO_SECOND);
			categoryList.selectCategory(requiredData.get(2));
			categorySummary.updateName(requiredData.get(5));

			// verify edits applied to product or not
			navigationBar.navigateToMenuItem(menuItem.get(0));
			globalProduct.selectGlobalProduct(productName);
			CustomisedAssert.assertEquals(dropDown.getSelectedItem(ProductSummary.DPD_CATEGORY1), requiredData.get(3));
			CustomisedAssert.assertEquals(dropDown.getSelectedItem(ProductSummary.DPD_CATEGORY2), requiredData.get(4));
			CustomisedAssert.assertEquals(dropDown.getSelectedItem(ProductSummary.DPD_CATEGORY3), requiredData.get(5));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// reset data
			dropDown.selectItem(ProductSummary.DPD_CATEGORY1, requiredData.get(6), Constants.TEXT);
			dropDown.selectItem(ProductSummary.DPD_CATEGORY2, requiredData.get(7), Constants.TEXT);
			dropDown.selectItem(ProductSummary.DPD_CATEGORY3, requiredData.get(8), Constants.TEXT);
			foundation.click(ProductSummary.BTN_SAVE);
			navigationBar.navigateToMenuItem(menuItem.get(1));
			categoryList.selectCategory(requiredData.get(3));
			categorySummary.updateName(requiredData.get(0));
			foundation.threadWait(Constants.TWO_SECOND);
			categoryList.selectCategory(requiredData.get(4));
			categorySummary.updateName(requiredData.get(1));
			foundation.threadWait(Constants.TWO_SECOND);
			categoryList.selectCategory(requiredData.get(5));
			categorySummary.updateName(requiredData.get(2));
		}
	}

	@Test(description = "143113-Edit existing deposit category name and verify edits applied to product or not on product summary page")
	public void editDepositCategory() {
		final String CASE_NUM = "143113";

		// Reading test data from DataBase
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		String productName = rstV5DeviceData.get(CNV5Device.PRODUCT_NAME);
		List<String> requiredData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> menuItem = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item and add the tax category to a global product
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItem.get(0));
			globalProduct.selectGlobalProduct(productName);
			dropDown.selectItem(ProductSummary.DPD_DEPOSIT_CATEGORY, requiredData.get(0), Constants.TEXT);
			foundation.click(ProductSummary.BTN_SAVE);

			// edit tax category
			navigationBar.navigateToMenuItem(menuItem.get(1));
			categoryList.selectCategory(requiredData.get(0));
			categorySummary.updateName(requiredData.get(1));
			navigationBar.navigateToMenuItem(menuItem.get(0));
			globalProduct.selectGlobalProduct(productName);
			CustomisedAssert.assertEquals(dropDown.getSelectedItem(ProductSummary.DPD_DEPOSIT_CATEGORY),
					requiredData.get(1));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// reset data
			dropDown.selectItem(ProductSummary.DPD_DEPOSIT_CATEGORY, requiredData.get(2), Constants.TEXT);
			foundation.click(ProductSummary.BTN_SAVE);
			navigationBar.navigateToMenuItem(menuItem.get(1));
			categoryList.selectCategory(requiredData.get(1));
			categorySummary.updateName(requiredData.get(0));
		}
	}

	@Test(description = "143115-Edit existing INVREASON category name and verify edits applied to product or not on product summary page")
	public void editInvReason() {
		final String CASE_NUM = "143115";
		// Reading test data from DataBase
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		String productName = rstV5DeviceData.get(CNV5Device.PRODUCT_NAME);
		List<String> requiredData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> menuItem = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// edit invReason code and verify the edits on product page
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItem.get(1));
			categoryList.selectCategory(requiredData.get(0));
			categorySummary.updateName(requiredData.get(1));
			navigationBar.navigateToMenuItem(menuItem.get(0));
			globalProduct.selectGlobalProduct(productName);
			textBox.enterText(ProductSummary.TXT_LOCATION_SEARCH_FILTER,
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(ProductSummary.LBL_REASON_CODE);
			List<String> listReasonCode = dropDown.getAllItems(ProductSummary.DPD_REASON_CODE);
			CustomisedAssert.assertTrue(listReasonCode.contains(requiredData.get(1)));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			navigationBar.navigateToMenuItem(menuItem.get(1));
			categoryList.selectCategory(requiredData.get(1));
			categorySummary.updateName(requiredData.get(0));
			foundation.waitforElement(CategoryList.TXT_SEARCH_CATEGORY, Constants.EXTRA_LONG_TIME);
		}
	}

	@Test(description = "142905-QAA-44-Place Order with valid email id")
	public void aanOrderWithEmailID() {
		try {
			final String CASE_NUM = "142905";

			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
			List<String> requiredData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			List<String> language = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			locationSummary.kiosklanguageSetting(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE),
					language.get(0), language.get(1));

			// navigate to payment success page and validate No receipt
			browser.close();
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			landingPage.changeLanguage(language.get(2), language.get(0), language.get(3));
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(requiredData.get(1));
			foundation.click(ProductSearch.BTN_PRODUCT);
			foundation.click(Order.LBL_MY_ACCOUNT);
			CustomisedAssert.assertTrue(foundation.isDisplayed(Order.BTN_LOGIN_WITH_EMAIL));
			foundation.click(Order.BTN_LOGIN_WITH_EMAIL);
			accountLogin.login(propertyFile.readPropertyFile(Configuration.V5_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.V5_DRIVER_PIN, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert
					.assertTrue(foundation.isDisplayed(order.objText(rstV5DeviceData.get(CNV5Device.PAYMENTS_PAGE))));
			foundation.waitforElement(LandingPage.IMG_SEARCH_ICON, Constants.SHORT_TIME);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}

	}

	@Test(description = "142907-QAA-44-verify daily revenue on location page")
	public void verifyDailyRevenue() {
		try {
			final String CASE_NUM = "142907";
			// Reading test data from DataBase
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			List<String> requiredData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));

			// verify daily revenue
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			textBox.enterText(LocationList.TXT_FILTER,
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));
			String dailyRevenue = foundation.getText(locationList.objDailyRevenue(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE)));
			CustomisedAssert.assertNotEquals(dailyRevenue, requiredData.get(1));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "143117-QAA19-Edit existing tax category name and verify edits applied to product or not on location summary page")
	public void editTaxCategoryLocation() {
		final String CASE_NUM = "143117";

		// Reading test data from DataBase
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		String productName = rstV5DeviceData.get(CNV5Device.PRODUCT_NAME);
		List<String> requiredData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> menuItem = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item and add the tax category to a global product
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItem.get(0));
			globalProduct.selectGlobalProduct(productName);
			dropDown.selectItem(ProductSummary.DPD_TAX_CATEGORY, requiredData.get(0), Constants.TEXT);
			foundation.click(ProductSummary.BTN_SAVE);

			// edit tax category
			navigationBar.navigateToMenuItem(menuItem.get(1));
			categoryList.selectCategory(requiredData.get(0));
			categorySummary.updateName(requiredData.get(1));

			// verify tax edits on location summary page
			navigationBar.navigateToMenuItem(menuItem.get(2));
			locationList.selectLocationName(requiredData.get(2));
			foundation.click(LocationSummary.TAB_PRODUCTS);
			foundation.click(LocationSummary.BTN_MANAGE_COLUMNS);
			locationSummary.showHideManageColumn(requiredData.get(3), requiredData.get(4));
			foundation.click(LocationSummary.BTN_APPLY);
			textBox.enterText(LocationSummary.TXT_PRODUCT_FILTER, productName);
			CustomisedAssert.assertEquals(locationSummary.getCellData(requiredData.get(5)), requiredData.get(1));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// reset data
			navigationBar.navigateToMenuItem(menuItem.get(0));
			globalProduct.selectGlobalProduct(productName);
			dropDown.selectItem(ProductSummary.DPD_TAX_CATEGORY, requiredData.get(6), Constants.TEXT);
			foundation.click(ProductSummary.BTN_SAVE);
			navigationBar.navigateToMenuItem(menuItem.get(1));
			categoryList.selectCategory(requiredData.get(1));
			categorySummary.updateName(requiredData.get(0));
		}
	}

	@Test(description = "143118-Edit existing deposit category name and verify edits applied to product or not on location summary page")
	public void editDepositCategoryLocation() {
		final String CASE_NUM = "143118";

		// Reading test data from DataBase
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		String productName = rstV5DeviceData.get(CNV5Device.PRODUCT_NAME);
		List<String> requiredData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> menuItem = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item and add the tax category to a global product
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItem.get(0));
			globalProduct.selectGlobalProduct(productName);
			dropDown.selectItem(ProductSummary.DPD_DEPOSIT_CATEGORY, requiredData.get(0), Constants.TEXT);
			foundation.click(ProductSummary.BTN_SAVE);

			// edit deposit category
			navigationBar.navigateToMenuItem(menuItem.get(1));
			categoryList.selectCategory(requiredData.get(0));
			categorySummary.updateName(requiredData.get(1));

			// verify deposit category edits on location summary page
			navigationBar.navigateToMenuItem(menuItem.get(2));
			locationList.selectLocationName(requiredData.get(2));
			foundation.click(LocationSummary.TAB_PRODUCTS);
			foundation.click(LocationSummary.BTN_MANAGE_COLUMNS);
			locationSummary.showHideManageColumn(requiredData.get(3), requiredData.get(4));
			foundation.click(LocationSummary.BTN_APPLY);
			textBox.enterText(LocationSummary.TXT_PRODUCT_FILTER, productName);
			CustomisedAssert.assertEquals(locationSummary.getCellData(requiredData.get(5)), requiredData.get(1));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// reset data
			navigationBar.navigateToMenuItem(menuItem.get(0));
			globalProduct.selectGlobalProduct(productName);
			dropDown.selectItem(ProductSummary.DPD_DEPOSIT_CATEGORY, requiredData.get(6), Constants.TEXT);
			foundation.click(ProductSummary.BTN_SAVE);
			navigationBar.navigateToMenuItem(menuItem.get(1));
			categoryList.selectCategory(requiredData.get(1));
			categorySummary.updateName(requiredData.get(0));
		}
	}

	@Test(description = "143119-QA-19-Edit existing category name and verify edits applied to product or not on location summary page")
	public void editCategoriesLocation() {
		final String CASE_NUM = "143119";

		// Reading test data from DataBase
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);

		List<String> requiredDataV5Device = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> menuItem = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		String productName = rstV5DeviceData.get(CNV5Device.PRODUCT_NAME);
		try {
			rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);
			List<String> requiredDataLocationSummary = Arrays.asList(
					rstLocationSummaryData.get(CNLocationSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item and add the tax category to a global product
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItem.get(0));
			globalProduct.selectGlobalProduct(productName);
			dropDown.selectItem(ProductSummary.DPD_CATEGORY1, requiredDataV5Device.get(0), Constants.TEXT);
			dropDown.selectItem(ProductSummary.DPD_CATEGORY2, requiredDataV5Device.get(1), Constants.TEXT);
			dropDown.selectItem(ProductSummary.DPD_CATEGORY3, requiredDataV5Device.get(2), Constants.TEXT);
			foundation.click(ProductSummary.BTN_SAVE);

			// update tax categories
			navigationBar.navigateToMenuItem(menuItem.get(1));
			categoryList.selectCategory(requiredDataV5Device.get(0));
			categorySummary.updateName(requiredDataV5Device.get(3));
			foundation.threadWait(Constants.TWO_SECOND);
			categoryList.selectCategory(requiredDataV5Device.get(1));
			categorySummary.updateName(requiredDataV5Device.get(4));
			foundation.threadWait(Constants.TWO_SECOND);
			categoryList.selectCategory(requiredDataV5Device.get(2));
			categorySummary.updateName(requiredDataV5Device.get(5));

			// -verify categories edits applied on location summary page-
			navigationBar.navigateToMenuItem(menuItem.get(2));
			locationList.selectLocationName(
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(LocationSummary.TAB_PRODUCTS);
			// select show categories
			foundation.click(LocationSummary.BTN_MANAGE_COLUMNS);
			foundation.threadWait(Constants.ONE_SECOND);
			locationSummary.showHideManageColumn(requiredDataLocationSummary.get(0),
					requiredDataLocationSummary.get(1));
			locationSummary.showHideManageColumn(requiredDataLocationSummary.get(0),
					requiredDataLocationSummary.get(2));
			locationSummary.showHideManageColumn(requiredDataLocationSummary.get(0),
					requiredDataLocationSummary.get(3));
			foundation.click(LocationSummary.BTN_APPLY);
			// verify edits
			textBox.enterText(LocationSummary.TXT_PRODUCT_FILTER, productName);
			CustomisedAssert.assertEquals(locationSummary.getCellData(requiredDataLocationSummary.get(4)),
					requiredDataV5Device.get(3));
			CustomisedAssert.assertEquals(locationSummary.getCellData(requiredDataLocationSummary.get(5)),
					requiredDataV5Device.get(4));
			CustomisedAssert.assertEquals(locationSummary.getCellData(requiredDataLocationSummary.get(6)),
					requiredDataV5Device.get(5));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// -reset data-
			// reset categories on products
			navigationBar.navigateToMenuItem(menuItem.get(0));
			globalProduct.selectGlobalProduct(productName);
			dropDown.selectItem(ProductSummary.DPD_CATEGORY1, requiredDataV5Device.get(6), Constants.TEXT);
			dropDown.selectItem(ProductSummary.DPD_CATEGORY2, requiredDataV5Device.get(6), Constants.TEXT);
			dropDown.selectItem(ProductSummary.DPD_CATEGORY3, requiredDataV5Device.get(6), Constants.TEXT);
			foundation.click(ProductSummary.BTN_SAVE);
			// reset categories names
			navigationBar.navigateToMenuItem(menuItem.get(1));
			categoryList.selectCategory(requiredDataV5Device.get(3));
			categorySummary.updateName(requiredDataV5Device.get(0));
			foundation.threadWait(Constants.TWO_SECOND);
			categoryList.selectCategory(requiredDataV5Device.get(4));
			categorySummary.updateName(requiredDataV5Device.get(1));
			foundation.threadWait(Constants.TWO_SECOND);
			categoryList.selectCategory(requiredDataV5Device.get(5));
			categorySummary.updateName(requiredDataV5Device.get(2));
		}
	}

	@Test(description = "143120-Edit existing INVREASON category name and verify edits applied to product or not on location summary page")
	public void editInvReasonLocation() {
		final String CASE_NUM = "143120";

		// Reading test data from DataBase
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);

		List<String> requiredData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> menuItem = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		try {
			String productName = rstV5DeviceData.get(CNV5Device.PRODUCT_NAME);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// edit invReason code and verify the edits on product page
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItem.get(1));
			categoryList.selectCategory(requiredData.get(0));
			categorySummary.updateName(requiredData.get(1));

			// -verify categories edits applied on location summary page-
			navigationBar.navigateToMenuItem(menuItem.get(2));
			locationList.selectLocationName(
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(LocationSummary.LNK_INVENTORY);
			foundation.threadWait(Constants.TWO_SECOND);
			textBox.enterText(LocationSummary.TXT_INVENTORY_FILTER, productName);
			foundation.click(LocationSummary.LBL_REASON_CODE);
			List<String> listReasonCode = foundation.getAttributeValueofListElement(LocationSummary.LIST_REASON_CODE,
					Constants.INNER_TEXT);
			CustomisedAssert.assertTrue(listReasonCode.contains(requiredData.get(1)));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// reset data
			navigationBar.navigateToMenuItem(menuItem.get(1));
			categoryList.selectCategory(requiredData.get(1));
			categorySummary.updateName(requiredData.get(0));
		}
	}

	@Test(description = "143123-Add new tax category and Edit it's name and verify edits applied to product or not on product summary page")
	public void addEditTaxCategory() {
		try {
			final String CASE_NUM = "143123";

			// Reading test data from DataBase
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			String productName = rstV5DeviceData.get(CNV5Device.PRODUCT_NAME);
			List<String> requiredData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> menuItem = Arrays
					.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// navigate to tax category
			navigationBar.navigateToMenuItem(menuItem.get(1));
			foundation.click(CategoryList.BTN_CREATE_NEW_CATEGORY);

			// add tax category
			String newTaxCat = string.getRandomCharacter().toUpperCase();
			String editedTaxCat = requiredData.get(1) + newTaxCat;
			categorySummary.addCategory(newTaxCat, requiredData.get(3));

			// verify newly added category displays in category list page
			CustomisedAssert.assertTrue(categoryList.verifyCategoryExist(newTaxCat));

			foundation.threadWait(Constants.TWO_SECOND);
			// Select Menu and Menu Item and add the tax category to a global product
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItem.get(0));
			globalProduct.selectGlobalProduct(productName);
			dropDown.selectItem(ProductSummary.DPD_TAX_CATEGORY, newTaxCat, Constants.TEXT);
			foundation.click(ProductSummary.BTN_SAVE);

			// edit tax category
			navigationBar.navigateToMenuItem(menuItem.get(1));
			categoryList.selectCategory(newTaxCat);
			categorySummary.updateName(editedTaxCat);
			navigationBar.navigateToMenuItem(menuItem.get(0));
			globalProduct.selectGlobalProduct(productName);
			CustomisedAssert.assertEquals(dropDown.getSelectedItem(ProductSummary.DPD_TAX_CATEGORY), editedTaxCat);

			// reset data
			dropDown.selectItem(ProductSummary.DPD_TAX_CATEGORY, requiredData.get(2), Constants.TEXT);
			foundation.click(ProductSummary.BTN_SAVE);
			navigationBar.navigateToMenuItem(menuItem.get(1));
			categoryList.selectCategory(editedTaxCat);
			categorySummary.updateName(newTaxCat);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "143124-Add new deposit category and Edit it's name and verify edits applied to product or not")
	public void addEditDepositCategory() {
		try {
			final String CASE_NUM = "143124";

			// Reading test data from DataBase
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			String productName = rstV5DeviceData.get(CNV5Device.PRODUCT_NAME);
			List<String> requiredData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> menuItem = Arrays
					.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// navigate to category page
			navigationBar.navigateToMenuItem(menuItem.get(1));
			foundation.click(CategoryList.BTN_CREATE_NEW_CATEGORY);

			// add deposit category
			String newDepositCat = string.getRandomCharacter().toUpperCase();
			String editedDepositCat = requiredData.get(1) + newDepositCat;
			categorySummary.addCategory(newDepositCat, requiredData.get(3));

			// verify newly added category displays in category list page
			CustomisedAssert.assertTrue(categoryList.verifyCategoryExist(newDepositCat));

			// Select Menu and Menu Item and add the tax category to a global product
			navigationBar.navigateToMenuItem(menuItem.get(0));
			globalProduct.selectGlobalProduct(productName);
			dropDown.selectItem(ProductSummary.DPD_DEPOSIT_CATEGORY, newDepositCat, Constants.TEXT);
			foundation.click(ProductSummary.BTN_SAVE);

			// edit tax category
			navigationBar.navigateToMenuItem(menuItem.get(1));
			categoryList.selectCategory(newDepositCat);
			categorySummary.updateName(editedDepositCat);
			navigationBar.navigateToMenuItem(menuItem.get(0));
			globalProduct.selectGlobalProduct(productName);
			CustomisedAssert.assertEquals(dropDown.getSelectedItem(ProductSummary.DPD_DEPOSIT_CATEGORY),
					editedDepositCat);

			// reset data
			dropDown.selectItem(ProductSummary.DPD_DEPOSIT_CATEGORY, requiredData.get(2), Constants.TEXT);
			foundation.click(ProductSummary.BTN_SAVE);
			navigationBar.navigateToMenuItem(menuItem.get(1));
			categoryList.selectCategory(editedDepositCat);
			categorySummary.updateName(newDepositCat);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "143125-QA-19-Add new category and Edit it's name and verify edits applied to product or not")
	public void addeditCategories() {
		try {
			final String CASE_NUM = "143125";

			// Reading test data from DataBase
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			String productName = rstV5DeviceData.get(CNV5Device.PRODUCT_NAME);
			List<String> requiredData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> menuItem = Arrays
					.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item and add the tax category to a global product
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItem.get(1));

			// add categories
			String newCategory1 = string.getRandomCharacter().toUpperCase();
			String newCategory2 = string.getRandomCharacter().toUpperCase();
			String newCategory3 = string.getRandomCharacter().toUpperCase();
			String editedCategory1 = requiredData.get(0) + newCategory1;
			String editedCategory2 = requiredData.get(0) + newCategory2;
			String editedCategory3 = requiredData.get(0) + newCategory3;
			foundation.click(CategoryList.BTN_CREATE_NEW_CATEGORY);
			categorySummary.addCategory(newCategory1, requiredData.get(1));
			foundation.click(CategoryList.BTN_CREATE_NEW_CATEGORY);
			categorySummary.addCategory(newCategory2, requiredData.get(1));
			foundation.click(CategoryList.BTN_CREATE_NEW_CATEGORY);
			categorySummary.addCategory(newCategory3, requiredData.get(1));

			// add categories to products
			navigationBar.navigateToMenuItem(menuItem.get(0));
			globalProduct.selectGlobalProduct(productName);
			dropDown.selectItem(ProductSummary.DPD_CATEGORY1, newCategory1, Constants.TEXT);
			dropDown.selectItem(ProductSummary.DPD_CATEGORY2, newCategory2, Constants.TEXT);
			dropDown.selectItem(ProductSummary.DPD_CATEGORY3, newCategory3, Constants.TEXT);
			foundation.click(ProductSummary.BTN_SAVE);

			// update tax categories
			navigationBar.navigateToMenuItem(menuItem.get(1));
			categoryList.selectCategory(newCategory1);
			categorySummary.updateName(editedCategory1);
			foundation.threadWait(Constants.TWO_SECOND);
			categoryList.selectCategory(newCategory2);
			categorySummary.updateName(editedCategory2);
			foundation.threadWait(Constants.TWO_SECOND);
			categoryList.selectCategory(newCategory3);
			categorySummary.updateName(editedCategory3);

			// verify edits applied to product or not
			navigationBar.navigateToMenuItem(menuItem.get(0));
			globalProduct.selectGlobalProduct(productName);
			CustomisedAssert.assertEquals(dropDown.getSelectedItem(ProductSummary.DPD_CATEGORY1), editedCategory1);
			CustomisedAssert.assertEquals(dropDown.getSelectedItem(ProductSummary.DPD_CATEGORY2), editedCategory2);
			CustomisedAssert.assertEquals(dropDown.getSelectedItem(ProductSummary.DPD_CATEGORY3), editedCategory3);

			// reset data
			dropDown.selectItem(ProductSummary.DPD_CATEGORY1, requiredData.get(2), Constants.TEXT);
			dropDown.selectItem(ProductSummary.DPD_CATEGORY2, requiredData.get(2), Constants.TEXT);
			dropDown.selectItem(ProductSummary.DPD_CATEGORY3, requiredData.get(2), Constants.TEXT);
			foundation.click(ProductSummary.BTN_SAVE);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "143126-Add new INVREASON category and Edit it's name and verify edits applied to product or not")
	public void addEditInvReason() {
		try {
			final String CASE_NUM = "143126";

			// Reading test data from DataBase
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			String productName = rstV5DeviceData.get(CNV5Device.PRODUCT_NAME);
			List<String> requiredData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> menuItem = Arrays
					.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// navigate to category page
			navigationBar.navigateToMenuItem(menuItem.get(1));
			foundation.click(CategoryList.BTN_CREATE_NEW_CATEGORY);

			// add deposit category
			String newInvReason = string.getRandomCharacter().toUpperCase();
			String editedInvReason = requiredData.get(0) + newInvReason;
			categorySummary.addCategory(newInvReason, requiredData.get(1));

			// verify newly added category displays in category list page
			CustomisedAssert.assertTrue(categoryList.verifyCategoryExist(newInvReason));

			// edit invReason code and verify the edits on product page
			navigationBar.navigateToMenuItem(menuItem.get(1));
			categoryList.selectCategory(newInvReason);
			categorySummary.updateName(editedInvReason);
			navigationBar.navigateToMenuItem(menuItem.get(0));
			globalProduct.selectGlobalProduct(productName);
			textBox.enterText(ProductSummary.TXT_LOCATION_SEARCH_FILTER,
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(ProductSummary.LBL_REASON_CODE);
			List<String> listReasonCode = dropDown.getAllItems(ProductSummary.DPD_REASON_CODE);
			CustomisedAssert.assertTrue(listReasonCode.contains(editedInvReason));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "143122-QAA19-Edit existing tax category and verify edits applied to product or not on location page- tax mapping tab")
	public void editTaxCategoryTaxMapping() {
		final String CASE_NUM = "143122";

		// Reading test data from DataBase
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		List<String> requiredData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> menuItem = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		// String productName = rstV5DeviceData.get(CNV5Device.PRODUCT_NAME);
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// verify tax edits on location summary page
			navigationBar.navigateToMenuItem(menuItem.get(0));
			locationList.selectLocationName(requiredData.get(0));
			foundation.click(LocationSummary.TAB_TAX_MAPPING);
			foundation.click(LocationSummary.BTN_ADD_MAPPING);
			dropdown.selectItem(LocationSummary.DPD_TAX_CATEGORY, requiredData.get(1), Constants.TEXT);
			dropdown.selectItem(LocationSummary.DPD_TAX_RATE, requiredData.get(5), Constants.TEXT);
			foundation.click(LocationSummary.BTN_SAVE_MAPPING);

			// edit tax category
			navigationBar.navigateToMenuItem(menuItem.get(1));
			categoryList.selectCategory(requiredData.get(1));
			categorySummary.updateName(requiredData.get(2));
			categoryList.selectCategory(requiredData.get(3));
			categorySummary.updateName(requiredData.get(4));

			// verify tax edits on location summary page- tax mapping tab- for saved
			// category
			navigationBar.navigateToMenuItem(menuItem.get(0));
			locationList.selectLocationName(requiredData.get(0));
			foundation.click(LocationSummary.TAB_TAX_MAPPING);
			textBox.enterText(LocationSummary.TXT_SEARCH_TAX_MAPPING, requiredData.get(2));
			CustomisedAssert.assertTrue(foundation.isDisplayed(locationSummary.objTaxCategory(requiredData.get(2))));
			// reset data- remove added mapping
			foundation.click(locationSummary.objTaxCategory(requiredData.get(2)));
			foundation.click(LocationSummary.BTN_REMOVE_MAPPING);

			// verify tax edits on location summary page- tax mapping tab- on tax category
			// list
			foundation.click(LocationSummary.TAB_TAX_MAPPING);
			foundation.click(LocationSummary.BTN_ADD_MAPPING);
			List<String> listTaxCategory = dropDown.getAllItems(LocationSummary.DPD_TAX_CATEGORY);
			CustomisedAssert.assertTrue(listTaxCategory.contains(requiredData.get(4)));
			foundation.click(LocationSummary.BTN_CANCEL_MAPPING);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// reset data
			navigationBar.navigateToMenuItem(menuItem.get(1));
			categoryList.selectCategory(requiredData.get(2));
			categorySummary.updateName(requiredData.get(1));
			categoryList.selectCategory(requiredData.get(4));
			categorySummary.updateName(requiredData.get(3));
		}
	}

	@Test(description = "143128-QAA19-Add new tax category and Edit it's category name and verify edits applied to product or not on location page - tax mapping tab")

	public void addEditTaxCategoryTaxMapping() {
		try {
			final String CASE_NUM = "143128";

			// Reading test data from DataBase
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			List<String> requiredData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> menuItem = Arrays
					.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// add tax categories
			navigationBar.navigateToMenuItem(menuItem.get(1));
			String newTaxCategory1 = string.getRandomCharacter().toUpperCase();
			String newTaxCategory2 = string.getRandomCharacter().toUpperCase();
			String editedTaxCategory1 = requiredData.get(2) + newTaxCategory1;
			String editedTaxCategory2 = requiredData.get(2) + newTaxCategory2;
			foundation.click(CategoryList.BTN_CREATE_NEW_CATEGORY);
			categorySummary.addCategory(newTaxCategory1, requiredData.get(4));
			foundation.click(CategoryList.BTN_CREATE_NEW_CATEGORY);
			categorySummary.addCategory(newTaxCategory2, requiredData.get(4));

			// save tax mapping on location summary page as precondition
			navigationBar.navigateToMenuItem(menuItem.get(2));
			locationList.selectLocationName(rstV5DeviceData.get(CNV5Device.LOCATION));
			foundation.click(LocationSummary.TAB_TAX_MAPPING);
			foundation.click(LocationSummary.BTN_ADD_MAPPING);
			dropdown.selectItem(LocationSummary.DPD_TAX_CATEGORY, newTaxCategory1, Constants.TEXT);
			dropdown.selectItem(LocationSummary.DPD_TAX_RATE, requiredData.get(1), Constants.TEXT);
			foundation.click(LocationSummary.BTN_SAVE_MAPPING);

			// edit tax category
			navigationBar.navigateToMenuItem(menuItem.get(1));
			categoryList.selectCategory(newTaxCategory1);
			categorySummary.updateName(editedTaxCategory1);
			categoryList.selectCategory(newTaxCategory2);
			categorySummary.updateName(editedTaxCategory2);

			// verify tax edits on location summary page- tax mapping tab- for saved
			// category
			navigationBar.navigateToMenuItem(menuItem.get(2));
			locationList.selectLocationName(rstV5DeviceData.get(CNV5Device.LOCATION));
			foundation.click(LocationSummary.TAB_TAX_MAPPING);
			textBox.enterText(LocationSummary.TXT_SEARCH_TAX_MAPPING, editedTaxCategory1);
			CustomisedAssert.assertTrue(foundation.isDisplayed(locationSummary.objTaxCategory(editedTaxCategory1)));
			// reset data- remove added mapping
			foundation.click(locationSummary.objTaxCategory(editedTaxCategory1));
			foundation.click(LocationSummary.BTN_REMOVE_MAPPING);

			// verify tax edits on location summary page- tax mapping tab- on tax category
			// list
			foundation.click(LocationSummary.TAB_TAX_MAPPING);
			foundation.click(LocationSummary.BTN_ADD_MAPPING);
			List<String> listTaxCategory = dropDown.getAllItems(LocationSummary.DPD_TAX_CATEGORY);
			CustomisedAssert.assertTrue(listTaxCategory.contains(editedTaxCategory2));
			foundation.click(LocationSummary.BTN_CANCEL_MAPPING);

			// reset data
			navigationBar.navigateToMenuItem(menuItem.get(1));
			categoryList.selectCategory(editedTaxCategory1);
			categorySummary.updateName(newTaxCategory1);
			categoryList.selectCategory(editedTaxCategory2);
			categorySummary.updateName(newTaxCategory2);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "143116-QAA-19-Edit existing BALREASON category name and verify edits applied on consumer page or not")

	public void editBalReason() {
		final String CASE_NUM = "143116";

		// Reading test data from DataBase
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstConsumerSearchData = dataBase.getConsumerSearchData(Queries.CONSUMER_SEARCH, CASE_NUM);
		rstConsumerSummaryData = dataBase.getConsumerSummaryData(Queries.CONSUMER_SUMMARY, CASE_NUM);

		List<String> requiredData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> menuItem = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		String firstName = rstConsumerSummaryData.get(CNConsumerSummary.FIRST_NAME);
		// String columnName = rstConsumerSearchData.get(CNConsumerSearch.COLUMN_NAME);
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// edit tax category
			navigationBar.navigateToMenuItem(menuItem.get(0));
			categoryList.selectCategory(requiredData.get(0));
			categorySummary.updateName(requiredData.get(1));

			// navigate to consumer summary page and verify edits applied ot not for balance
			// reason
			navigationBar.navigateToMenuItem(menuItem.get(1));
			consumerSearch.enterSearchFields(rstConsumerSearchData.get(CNConsumerSearch.SEARCH_BY),
					rstConsumerSearchData.get(CNConsumerSearch.CONSUMER_ID),
					rstConsumerSearchData.get(CNConsumerSearch.LOCATION),
					rstConsumerSearchData.get(CNConsumerSearch.STATUS));

			// clicking consumer id
			foundation.click(consumerSearch.objCell(firstName));
			foundation.click(ConsumerSearch.BTN_ADJUST);

			// Map<String, String> consumerTblRecords =
			// consumerSearch.getConsumerRecords(rstConsumerSearchData.get(CNConsumerSearch.LOCATION));
			String balance = textBox.getTextFromInput(ConsumerSummary.TXT_ADJUST_BALANCE);
			// String balance1 = balance.substring(1);
			Double newBalance = Double.parseDouble(balance) + 2;
			// String expectedBalance = "$" + String.valueOf(newBalance);

			// enter new balance with reason
			textBox.enterText(ConsumerSummary.TXT_ADJUST_BALANCE, String.valueOf(newBalance));
			dropDown.selectItem(ConsumerSummary.DPD_REASON, requiredData.get(1), Constants.TEXT);
			foundation.click(ConsumerSummary.BTN_REASON_SAVE);
			foundation.refreshPage();
			textBox.enterText(ConsumerSummary.TXT_SEARCH_ACCOUNT_ADJUSTMENT, requiredData.get(1));
			CustomisedAssert.assertTrue(foundation.isDisplayed(consumerSummary.objTaxCategory(requiredData.get(1))));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// reset data
			navigationBar.navigateToMenuItem(menuItem.get(0));
			categoryList.selectCategory(requiredData.get(1));
			categorySummary.updateName(requiredData.get(0));
		}
	}

	@Test(description = "143121-QAA19-Edit existing tax category percentage (tax rate) and verify edits applied to product or not on V5 order page")
	public void editTaxRateCategory() {
		final String CASE_NUM = "143121";

		// Reading test data from DataBase
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		List<String> requiredData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> menuItem = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));

		try {

			String productName = rstV5DeviceData.get(CNV5Device.PRODUCT_NAME);
			List<String> language = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// add categories to products
			navigationBar.navigateToMenuItem(menuItem.get(0));
			foundation.threadWait(Constants.THREE_SECOND);
			globalProduct.selectGlobalProduct(productName);
			dropDown.selectItem(ProductSummary.DPD_TAX_CATEGORY, requiredData.get(0), Constants.TEXT);
			foundation.click(ProductSummary.BTN_SAVE);

			// save tax mapping on location summary page as precondition
			navigationBar.navigateToMenuItem(menuItem.get(2));
			locationList.selectLocationName(rstV5DeviceData.get(CNV5Device.LOCATION));
			locationSummary.saveTaxMapping(requiredData.get(0), requiredData.get(0));

			// edit tax category
			navigationBar.navigateToMenuItem(menuItem.get(1));
			categoryList.selectCategory(requiredData.get(0));
			categorySummary.updateName(requiredData.get(1));

			// verify tax edits on location summary page- tax mapping tab- for saved
			// category
			navigationBar.navigateToMenuItem(menuItem.get(2));
			locationList.selectLocationName(rstV5DeviceData.get(CNV5Device.LOCATION));
			foundation.click(LocationSummary.TAB_TAX_MAPPING);
			textBox.enterText(LocationSummary.TXT_SEARCH_TAX_MAPPING, requiredData.get(1));
			CustomisedAssert.assertTrue(foundation.isDisplayed(locationSummary.objTaxCategory(requiredData.get(1))));
			foundation.click(locationSummary.objTaxCategory(requiredData.get(1)));
			dropDown.selectItem(LocationSummary.DPD_TAX_RATE_EDIT, requiredData.get(2), Constants.TEXT);
			foundation.click(LocationSummary.BTN_SAVE_MAPPING);

			// set language and sync machine
			navigationBar.navigateToMenuItem(menuItem.get(2));
			locationSummary.kiosklanguageSetting(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE),
					language.get(0), language.get(1));

			// launch v5 application
			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			landingPage.changeLanguage(language.get(2), language.get(0), language.get(3));
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(productName);
			foundation.click(ProductSearch.BTN_PRODUCT);
			CustomisedAssert.assertEquals(foundation.getText(Order.TXT_PRODUCT), productName);
			order.verifyTax(requiredData.get(3));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// reset data
			// reset- category name
			browser.close();
			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// reset mapping
			navigationBar.navigateToMenuItem(menuItem.get(2));
			locationList.selectLocationName(rstV5DeviceData.get(CNV5Device.LOCATION));
			foundation.click(LocationSummary.TAB_TAX_MAPPING);
			textBox.enterText(LocationSummary.TXT_SEARCH_TAX_MAPPING, requiredData.get(1));
			CustomisedAssert.assertTrue(foundation.isDisplayed(locationSummary.objTaxCategory(requiredData.get(1))));
			foundation.click(locationSummary.objTaxCategory(requiredData.get(1)));
			dropDown.selectItem(LocationSummary.DPD_TAX_RATE_EDIT, requiredData.get(4), Constants.TEXT);
			foundation.click(LocationSummary.BTN_SAVE_MAPPING);

			// reset/remove mapping
			navigationBar.navigateToMenuItem(menuItem.get(2));
			locationList.selectLocationName(rstV5DeviceData.get(CNV5Device.LOCATION));
			locationSummary.removeTaxMapping(requiredData.get(1));

			// reset category
			navigationBar.navigateToMenuItem(menuItem.get(1));
			categoryList.selectCategory(requiredData.get(1));
			categorySummary.updateName(requiredData.get(0));
		}
	}

	@Test(description = "143020-SOS-10567: v5 > International GMA Pay by Account Error")
	public void gmaPayByAccountError() {
		final String CASE_NUM = "143020";
		// Reading test data from DataBase
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);

		List<String> menuItem = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> requiredData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> language = Arrays.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItem.get(0));

			// select the org and update country and tax system
			orgList.selectOrg(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			dropDown.selectItem(OrgSummary.DPD_CROSS_ORG_ACCOUNT, requiredData.get(0), Constants.TEXT);
			foundation.click(OrgSummary.BTN_SAVE);
			foundation.threadWait(Constants.TWO_SECOND);
			orgList.selectOrg(propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			dropDown.selectItem(OrgSummary.DPD_CROSS_ORG_ACCOUNT, requiredData.get(0), Constants.TEXT);
			foundation.click(OrgSummary.BTN_SAVE);

			// sync machine
			foundation.threadWait(Constants.TWO_SECOND);
			navigationBar.navigateToMenuItem(menuItem.get(1));
			foundation.threadWait(Constants.THREE_SECOND);
			locationList.selectLocationName(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(LocationSummary.BTN_SYNC);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_FILTER, Constants.SHORT_TIME);
			browser.close();

			// try navigate to payment page and validate error messages
			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			landingPage.changeLanguage(language.get(2), language.get(0), language.get(3));
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(requiredData.get(3));
			foundation.click(ProductSearch.BTN_PRODUCT);
			CustomisedAssert.assertTrue(foundation.isDisplayed(Order.LBL_EMAIL));
			foundation.click(Order.LBL_EMAIL);
			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(
					propertyFile.readPropertyFile(Configuration.V5_USER, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(AccountLogin.BTN_ENTER);
			foundation.click(AccountLogin.BTN_NEXT);
			CustomisedAssert.assertEquals(foundation.getText(AccountLogin.LBL_ACCOUNT_NOT_AVAILABLE),
					requiredData.get(4));
			CustomisedAssert.assertEquals(foundation.getText(AccountLogin.LBL_GEO_GRAPHIC_LOCATION),
					requiredData.get(5));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// resetData
			browser.close();
			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItem.get(0));
			// select the org and update country and tax system
			orgList.selectOrg(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			dropDown.selectItem(OrgSummary.DPD_CROSS_ORG_ACCOUNT, requiredData.get(6), Constants.TEXT);
			foundation.click(OrgSummary.BTN_SAVE);
			foundation.threadWait(Constants.TWO_SECOND);
			orgList.selectOrg(propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			dropDown.selectItem(OrgSummary.DPD_CROSS_ORG_ACCOUNT, requiredData.get(6), Constants.TEXT);
			foundation.click(OrgSummary.BTN_SAVE);
			navigationBar.navigateToMenuItem(menuItem.get(0));
			browser.close();

			// check user can place order without error
			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			landingPage.changeLanguage(language.get(2), language.get(0), language.get(3));
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(requiredData.get(3));
			foundation.click(ProductSearch.BTN_PRODUCT);
			CustomisedAssert.assertTrue(foundation.isDisplayed(Order.LBL_EMAIL));
			foundation.click(Order.LBL_EMAIL);
			accountLogin.login(propertyFile.readPropertyFile(Configuration.V5_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.V5_PIN, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(PaymentSuccess.BTN_YES));
		}
	}

	@Test(description = "143129-QAA-19-Add new BALREASON category and Edit it's name and verify edits applied on consumer page or not")
	public void addEditBalReason() {
		try {
			final String CASE_NUM = "143129";

			// Reading test data from DataBase
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstConsumerSearchData = dataBase.getConsumerSearchData(Queries.CONSUMER_SEARCH, CASE_NUM);
			rstConsumerSummaryData = dataBase.getConsumerSummaryData(Queries.CONSUMER_SUMMARY, CASE_NUM);
			String firstName = rstConsumerSummaryData.get(CNConsumerSummary.FIRST_NAME);
			List<String> requiredData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> menuItem = Arrays
					.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// navigate to new category page
			navigationBar.navigateToMenuItem(menuItem.get(0));
			foundation.click(CategoryList.BTN_CREATE_NEW_CATEGORY);

			// add balance category
			String newBalCat = string.getRandomCharacter().toUpperCase();
			String editedBalCat = requiredData.get(0) + newBalCat;
			categorySummary.addCategory(newBalCat, requiredData.get(1));

			// verify newly added category displays in category list page
			CustomisedAssert.assertTrue(categoryList.verifyCategoryExist(newBalCat));

			// edit tax category
			navigationBar.navigateToMenuItem(menuItem.get(0));
			categoryList.selectCategory(newBalCat);
			categorySummary.updateName(editedBalCat);

			// navigate to consumer summary page and verify edits applied ot not for balance
			// reason
			navigationBar.navigateToMenuItem(menuItem.get(1));
			consumerSearch.enterSearchFields(rstConsumerSearchData.get(CNConsumerSearch.SEARCH_BY),
					rstConsumerSearchData.get(CNConsumerSearch.CONSUMER_ID),
					rstConsumerSearchData.get(CNConsumerSearch.LOCATION),
					rstConsumerSearchData.get(CNConsumerSearch.STATUS));

			// clicking consumer id
			foundation.click(consumerSearch.objCell(firstName));
			foundation.click(ConsumerSearch.BTN_ADJUST);

			// Map<String, String> consumerTblRecords =
			// consumerSearch.getConsumerRecords(rstConsumerSearchData.get(CNConsumerSearch.LOCATION));
			String balance = textBox.getTextFromInput(ConsumerSummary.TXT_ADJUST_BALANCE);
			Double newBalance = Double.parseDouble(balance) + 2;

			// enter new balance with reason
			textBox.enterText(ConsumerSummary.TXT_ADJUST_BALANCE, String.valueOf(newBalance));
			dropDown.selectItem(ConsumerSummary.DPD_REASON, editedBalCat, Constants.TEXT);
			foundation.click(ConsumerSummary.BTN_REASON_SAVE);
			foundation.refreshPage();
			textBox.enterText(ConsumerSummary.TXT_SEARCH_ACCOUNT_ADJUSTMENT, editedBalCat);
			CustomisedAssert.assertTrue(foundation.isDisplayed(consumerSummary.objTaxCategory(editedBalCat)));

			// reset data
			navigationBar.navigateToMenuItem(menuItem.get(0));
			categoryList.selectCategory(editedBalCat);
			categorySummary.updateName(newBalCat);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "143128-QAA19-Add new tax category and Edit it's category name and verify edits applied to product or not on location page - tax mapping tab")
	public void addEditTaxRateCategory() {
		try {
			final String CASE_NUM = "143128";

			// Reading test data from DataBase
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			String productName = rstV5DeviceData.get(CNV5Device.PRODUCT_NAME);
			String location = rstV5DeviceData.get(CNV5Device.LOCATION);
			List<String> requiredData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> menuItem = Arrays
					.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
			List<String> language = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// navigate to new category page
			navigationBar.navigateToMenuItem(menuItem.get(1));
			foundation.click(CategoryList.BTN_CREATE_NEW_CATEGORY);

			// add tax category
			String newTaxCat = string.getRandomCharacter().toUpperCase();
			// String newTaxRateCat = string.getRandomCharacter().toUpperCase();
			String editedTaxCat = requiredData.get(0) + newTaxCat;
			categorySummary.addCategory(newTaxCat, requiredData.get(4));

			// verify newly added category displays in category list page
			CustomisedAssert.assertTrue(categoryList.verifyCategoryExist(newTaxCat));

			// save tax mapping on location summary page as precondition
			navigationBar.navigateToMenuItem(menuItem.get(2));
			locationList.selectLocationName(location);
			foundation.click(LocationSummary.TAB_TAX_MAPPING);
			foundation.click(LocationSummary.BTN_ADD_MAPPING);
			dropdown.selectItem(LocationSummary.DPD_TAX_CATEGORY, newTaxCat, Constants.TEXT);
			dropdown.selectItem(LocationSummary.DPD_TAX_RATE, requiredData.get(1), Constants.TEXT);
			foundation.click(LocationSummary.BTN_SAVE_MAPPING);

			// add categories to products
			navigationBar.navigateToMenuItem(menuItem.get(0));
			foundation.threadWait(Constants.THREE_SECOND);
			globalProduct.selectGlobalProduct(productName);
			dropDown.selectItem(ProductSummary.DPD_TAX_CATEGORY, newTaxCat, Constants.TEXT);
			foundation.click(ProductSummary.BTN_SAVE);

			// edit tax category
			navigationBar.navigateToMenuItem(menuItem.get(1));
			categoryList.selectCategory(newTaxCat);
			categorySummary.updateName(editedTaxCat);

			// verify tax edits on location summary page- tax mapping tab- for saved
			// category
			navigationBar.navigateToMenuItem(menuItem.get(2));
			locationList.selectLocationName(location);
			foundation.click(LocationSummary.TAB_TAX_MAPPING);
			textBox.enterText(LocationSummary.TXT_SEARCH_TAX_MAPPING, editedTaxCat);
			CustomisedAssert.assertTrue(foundation.isDisplayed(locationSummary.objTaxCategory(editedTaxCat)));
			foundation.click(locationSummary.objTaxCategory(editedTaxCat));
			dropDown.selectItem(LocationSummary.DPD_TAX_RATE_EDIT, "AUTOTAXPERCENTAGE2", Constants.TEXT);
			foundation.click(LocationSummary.BTN_SAVE_MAPPING);

			// set language and sync machine
			navigationBar.navigateToMenuItem(menuItem.get(2));
			locationSummary.kiosklanguageSetting(location, language.get(0), language.get(1));

			// launch v5 application
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			// foundation.click(landingPage.objLanguage(language.get(0)));
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(productName);
			foundation.click(ProductSearch.BTN_PRODUCT);
			CustomisedAssert.assertEquals(foundation.getText(Order.TXT_PRODUCT), productName);
			order.verifyTax(requiredData.get(2));

			// reset data
			// reset- category name
			browser.close();
			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			// reset mapping
			navigationBar.navigateToMenuItem(menuItem.get(2));
			locationList.selectLocationName(location);
			foundation.click(LocationSummary.TAB_TAX_MAPPING);
			textBox.enterText(LocationSummary.TXT_SEARCH_TAX_MAPPING, editedTaxCat);
			CustomisedAssert.assertTrue(foundation.isDisplayed(locationSummary.objTaxCategory(editedTaxCat)));
			foundation.click(locationSummary.objTaxCategory(editedTaxCat));
			foundation.click(LocationSummary.BTN_REMOVE_MAPPING);
			// reset category
			navigationBar.navigateToMenuItem(menuItem.get(1));
			categoryList.selectCategory(editedTaxCat);
			categorySummary.updateName(newTaxCat);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "143467-QAA-192-Verify when new tax mapping is added, it should display in tax mapping grid in adm and in sales of product in Kiosk.")
	public void verifyAddingNewTaxRate() {
		final String CASE_NUM = "143467";

		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);
		rstGlobalProductChangeData = dataBase.getGlobalProductChangeData(Queries.GLOBAL_PRODUCT_CHANGE, CASE_NUM);
		rstOrgSummaryData = dataBase.getOrgSummaryData(Queries.ORG_SUMMARY, CASE_NUM);
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);

		String locationName = rstLocationData.get(CNLocation.LOCATION_NAME);
		String tabName = rstLocationData.get(CNLocation.TAB_NAME);
		String timeZone = rstLocationData.get(CNLocation.TIMEZONE);
		List<String> requiredData = Arrays
				.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> menuItem = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		String taxRateName = "";
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			int date = Integer.parseInt(dateAndTime.getDateAndTime(requiredData.get(0), timeZone));
			String currentDay;
			if (date < 10) {
				currentDay = String.valueOf(date).substring(0, 1);
			} else {
				currentDay = String.valueOf(date);
			}

			String product = rstGlobalProductChangeData.get(CNGlobalProductChange.PRODUCT_NAME);

			navigationBar.navigateToMenuItem(menuItem.get(0));
			foundation.click(TaxList.BTN_NEW);
			taxRateName = Constants.ACCOUNT_NAME + string.getRandomCharacter();
			textBox.enterText(TaxList.TXT_DESCRIPTION, taxRateName);
			foundation.click(TaxList.BTN_SAVE);
			textBox.enterText(TaxList.LBL_SEARCH, taxRateName);
			table.selectRow(taxRateName);
			foundation.click(TaxList.BTN_ADDRATE);
			textBox.enterText(TaxList.TXT_RATE_1, requiredData.get(2));
			textBox.enterText(TaxList.TXT_RATE_2, requiredData.get(3));
			textBox.enterText(TaxList.TXT_RATE_3, requiredData.get(4));
			textBox.enterText(TaxList.TXT_RATE_4, requiredData.get(5));

			foundation.click(TaxList.LBL_CALENDER);
			taxList.selectDate(currentDay);
			textBox.enterText(TaxList.TXT_EFFECTIVETIME, requiredData.get(6));
			foundation.click(TaxList.LBL_TAXRATE_SAVE);
			// navigating global product
			navigationBar.navigateToMenuItem(menuItem.get(1));
			foundation.threadWait(Constants.LONG_TIME);
			textBox.enterText(GlobalProduct.TXT_FILTER, product);
			table.selectRow(product);
			foundation.waitforElement(ProductSummary.DPD_TAX_CATEGORY, Constants.SHORT_TIME);
			dropDown.selectItem(ProductSummary.DPD_TAX_CATEGORY, requiredData.get(1), Constants.TEXT);
			String expectedPrice = foundation.getTextAttribute(ProductSummary.TXT_PRICE, Constants.SRC);
			double expectedTaxRate1 = taxList.getTaxAmount(requiredData.get(2), expectedPrice);
			double expectedTaxRate2 = taxList.getTaxAmount(requiredData.get(3), expectedPrice);
			double expectedTaxRate3 = taxList.getTaxAmount(requiredData.get(4), expectedPrice);
			double expectedTaxRate4 = taxList.getTaxAmount(requiredData.get(5), expectedPrice);
			foundation.click(ProductSummary.BTN_SAVE);
			// org summary
			navigationBar.navigateToMenuItem(menuItem.get(2));
			dropDown.selectItem(OrgSummary.DPD_TAX_SYSTEM, rstOrgSummaryData.get(CNOrgSummary.REQUIRED_DATA),
					Constants.TEXT);
			foundation.click(OrgSummary.BTN_SAVE);
			foundation.waitforElement(OrgSummary.TXT_SPINNER_MSG, Constants.THREE_SECOND);
			// navigating location
			navigationBar.navigateToMenuItem(menuItem.get(3));
			locationList.selectLocationName(locationName);
			locationSummary.selectTab(tabName);

			// Verifying Device name is present or not
			if (foundation.isDisplayed(locationSummary.deviceName(requiredData.get(1)))) {

				// Deleting the Already Present Device
				locationSummary.removeDevice(requiredData.get(1));
			}

			foundation.click(LocationSummary.LBL_TAX_MAPPING);
			dropDown.selectItem(LocationSummary.DPD_TAX_CAT, requiredData.get(1), Constants.TEXT);
			dropDown.selectItem(LocationSummary.DPD_TAX_RATE, taxRateName, Constants.TEXT);
			foundation.click(LocationSummary.LBL_TAX_CAT_SAVE);
			locationSummary.selectTab(tabName);
			textBox.enterText(LocationSummary.TXT_TAX_FILTER, taxRateName);
			Map<String, String> uiData = table.getTblSingleRowRecordUI(LocationSummary.TBL_TAX_GRID,
					LocationSummary.TBL_ROW);

			Map<String, String> dbData_Tax = new HashMap<>();
			dbData_Tax.put(requiredData.get(8), requiredData.get(1));
			dbData_Tax.put(requiredData.get(9), taxRateName);
			CustomisedAssert.assertEquals(uiData, dbData_Tax);
			dropDown.selectItem(LocationSummary.DPD_KIOSK_LANGUAGE, requiredData.get(7), Constants.TEXT);
			foundation.click(LocationSummary.BTN_SYNC);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);

			login.logout();
			browser.close();

			foundation.threadWait(Constants.SHORT_TIME);
			// login into Kiosk Device
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			foundation.waitforElement(LandingPage.IMG_SEARCH_ICON, Constants.SHORT_TIME);

			// Navigating to product search page
			foundation.waitforElement(LandingPage.IMG_SEARCH_ICON, Constants.SHORT_TIME);
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			foundation.waitforElement(AccountLogin.BTN_CAMELCASE, Constants.SHORT_TIME);

			// searching for product

			textBox.enterKeypadText(product);
			foundation.click(ProductSearch.BTN_PRODUCT);

			// verify Order Page
			List<String> orderPageData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.ORDER_PAGE).split(Constants.DELIMITER_TILD));
			CustomisedAssert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(0))));
			String actualTaxRate1 = foundation.getText(Order.LBL_TAX_1);
			String actualTaxRate2 = foundation.getText(Order.LBL_TAX_2);
			String actualTaxRate3 = foundation.getText(Order.LBL_TAX_3);
			String actualTaxRate4 = foundation.getText(Order.LBL_TAX_4);
			String actualBalDue = foundation.getText(Order.LBL_BALANCE_DUE);
			String actualSubTotal = foundation.getText(Order.LBL_SUB_TOTAL);
			CustomisedAssert.assertEquals(actualTaxRate1, requiredData.get(10) + String.valueOf(expectedTaxRate1));
			CustomisedAssert.assertEquals(actualTaxRate2, requiredData.get(10) + String.valueOf(expectedTaxRate2));
			CustomisedAssert.assertEquals(actualTaxRate3, requiredData.get(10) + String.valueOf(expectedTaxRate3));
			CustomisedAssert.assertEquals(actualTaxRate4, requiredData.get(10) + String.valueOf(expectedTaxRate4));
			CustomisedAssert.assertEquals(actualSubTotal, requiredData.get(10) + expectedPrice);

			float expectedBalDue = (float) (Float.parseFloat(expectedPrice) + expectedTaxRate1 + expectedTaxRate2
					+ expectedTaxRate3 + expectedTaxRate4);
			CustomisedAssert.assertTrue(actualBalDue.contains(String.valueOf(expectedBalDue)));
			foundation.objectFocus(order.objText(orderPageData.get(1)));
			foundation.click(order.objText(orderPageData.get(1)));

			foundation.waitforElement(AccountLogin.BTN_NEXT, Constants.SHORT_TIME);

			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(
					propertyFile.readPropertyFile(Configuration.V5_USER, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(AccountLogin.BTN_NEXT);
			foundation.waitforElement(AccountLogin.BTN_PIN_NEXT, Constants.SHORT_TIME);
			textBox.enterPin(propertyFile.readPropertyFile(Configuration.V5_PIN, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(AccountLogin.BTN_PIN_NEXT);
			List<String> paymentPageData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.PAYMENTS_PAGE).split(Constants.DELIMITER_TILD));

			CustomisedAssert.assertTrue(foundation.isDisplayed(payments.objText(paymentPageData.get(0))));

			foundation.click(payments.objText(paymentPageData.get(1)));
			foundation.waitforElement(LandingPage.IMG_SEARCH_ICON, Constants.SHORT_TIME);
			browser.close();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// resetting test data
			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			locationList.selectLocationName(locationName);
			locationSummary.selectTab(tabName);
			textBox.enterText(LocationSummary.TXT_TAX_FILTER, taxRateName);
			table.selectRow(taxRateName);
			foundation.waitforElement(LocationSummary.LBL_TAX_CAT_REMOVE, Constants.SHORT_TIME);
			foundation.click(LocationSummary.LBL_TAX_CAT_REMOVE);
			foundation.waitforElement(LocationSummary.BTN_CLOSE_COMMERCIAL, Constants.SHORT_TIME);
		}
	}

	@Test(description = "143528-QAA-192-Verify when tax rate is removed, it should not display in tax Mapping Field and should not applied in sales of product in Kiosk.")
	public void verifyRemovingTaxMapping() {
		try {
			final String CASE_NUM = "143528";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);
			rstGlobalProductChangeData = dataBase.getGlobalProductChangeData(Queries.GLOBAL_PRODUCT_CHANGE, CASE_NUM);
			rstOrgSummaryData = dataBase.getOrgSummaryData(Queries.ORG_SUMMARY, CASE_NUM);
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			String locationName = rstLocationData.get(CNLocation.LOCATION_NAME);
			String tabName = rstLocationData.get(CNLocation.TAB_NAME);
			String product = rstGlobalProductChangeData.get(CNGlobalProductChange.PRODUCT_NAME);
			List<String> requiredData = Arrays
					.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> menuItem = Arrays
					.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));

			navigationBar.navigateToMenuItem(menuItem.get(0));
			foundation.threadWait(Constants.MEDIUM_TIME);
			textBox.enterText(GlobalProduct.TXT_FILTER, product);
			table.selectRow(product);
			foundation.waitforElement(ProductSummary.DPD_TAX_CATEGORY, Constants.SHORT_TIME);
			dropDown.selectItem(ProductSummary.DPD_TAX_CATEGORY, requiredData.get(0), Constants.TEXT);
			String expectedPrice = foundation.getTextAttribute(ProductSummary.TXT_PRICE, Constants.SRC);

			foundation.click(ProductSummary.BTN_SAVE);
			// org summary
			navigationBar.navigateToMenuItem(menuItem.get(1));
			dropDown.selectItem(OrgSummary.DPD_TAX_SYSTEM, rstOrgSummaryData.get(CNOrgSummary.REQUIRED_DATA),
					Constants.TEXT);
			foundation.click(OrgSummary.BTN_SAVE);
			foundation.waitforElement(OrgSummary.TXT_SPINNER_MSG, Constants.THREE_SECOND);
			// navigating location-- Mapping Tax
			navigationBar.navigateToMenuItem(menuItem.get(2));
			locationList.selectLocationName(locationName);
			locationSummary.selectTab(tabName);
			locationSummary.saveTaxMapping(requiredData.get(0), requiredData.get(1));
//			foundation.click(LocationSummary.LBL_TAX_MAPPING);
//			dropDown.selectItem(LocationSummary.DPD_TAX_CAT, requiredData.get(0), Constants.TEXT);
//			dropDown.selectItem(LocationSummary.DPD_TAX_RATE, requiredData.get(1), Constants.TEXT);
//			foundation.click(LocationSummary.LBL_TAX_CAT_SAVE);

			dropDown.selectItem(LocationSummary.DPD_KIOSK_LANGUAGE, requiredData.get(2), Constants.TEXT);
			foundation.click(LocationSummary.BTN_SYNC);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);

			login.logout();
			browser.close();

			foundation.threadWait(Constants.SHORT_TIME);
			// login into Kiosk Device
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			foundation.waitforElement(LandingPage.IMG_SEARCH_ICON, Constants.SHORT_TIME);

			// Navigating to product search page
			foundation.waitforElement(LandingPage.IMG_SEARCH_ICON, Constants.SHORT_TIME);
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			foundation.waitforElement(AccountLogin.BTN_CAMELCASE, Constants.SHORT_TIME);

			// searching for product

			textBox.enterKeypadText(product);
			foundation.click(ProductSearch.BTN_PRODUCT);

			// verify Order Page
			List<String> orderPageData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.ORDER_PAGE).split(Constants.DELIMITER_TILD));
			CustomisedAssert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(0))));
			CustomisedAssert.assertTrue(foundation.isDisplayed(Order.LBL_TAX_1));

			// removing Tax Mapping
			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			locationList.selectLocationName(locationName);
			locationSummary.selectTab(tabName);
			textBox.enterText(LocationSummary.TXT_TAX_FILTER, requiredData.get(1));
			table.selectRow(requiredData.get(1));
			foundation.waitforElement(LocationSummary.LBL_TAX_CAT_REMOVE, Constants.SHORT_TIME);
			foundation.click(LocationSummary.LBL_TAX_CAT_REMOVE);
			CustomisedAssert.assertFalse(foundation.isDisplayed(locationSummary.objTable(requiredData.get(0))));
			foundation.click(LocationSummary.BTN_SYNC);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			login.logout();
			browser.close();

			foundation.threadWait(Constants.SHORT_TIME);
			// login into Kiosk Device
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			foundation.waitforElement(LandingPage.IMG_SEARCH_ICON, Constants.SHORT_TIME);

			// Navigating to product search page
			foundation.waitforElement(LandingPage.IMG_SEARCH_ICON, Constants.SHORT_TIME);
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			foundation.waitforElement(AccountLogin.BTN_CAMELCASE, Constants.SHORT_TIME);

			// searching for product

			textBox.enterKeypadText(product);
			foundation.click(ProductSearch.BTN_PRODUCT);

			// verify Order Page
			CustomisedAssert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(0))));
			String actualBalDue = foundation.getText(Order.LBL_BALANCE_DUE);
			CustomisedAssert.assertEquals(actualBalDue, requiredData.get(3) + expectedPrice);
			CustomisedAssert.assertFalse(foundation.isDisplayed(Order.LBL_TAX_1));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "143529-QAA-192-Verify when tax rate is replaced with another tax rate in tax mapping page, it should display in tax mapping grid page in ADM and sales of product in Kiosk.")
	public void verifyReplaceTaxRate() {
		try {
			final String CASE_NUM = "143529";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);
			rstGlobalProductChangeData = dataBase.getGlobalProductChangeData(Queries.GLOBAL_PRODUCT_CHANGE, CASE_NUM);
			rstOrgSummaryData = dataBase.getOrgSummaryData(Queries.ORG_SUMMARY, CASE_NUM);
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			String locationName = rstLocationData.get(CNLocation.LOCATION_NAME);
			String tabName = rstLocationData.get(CNLocation.TAB_NAME);
			String product = rstGlobalProductChangeData.get(CNGlobalProductChange.PRODUCT_NAME);
			List<String> requiredData = Arrays
					.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> menuItem = Arrays
					.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
			navigationBar.navigateToMenuItem(menuItem.get(3));
			locationList.selectLocationName(locationName);
			locationSummary.selectTab(tabName);
			locationSummary.saveTaxMapping(requiredData.get(2), requiredData.get(0));
//			foundation.click(LocationSummary.LBL_TAX_MAPPING);
//			dropDown.selectItem(LocationSummary.DPD_TAX_CAT, requiredData.get(2), Constants.TEXT);
//			dropDown.selectItem(LocationSummary.DPD_TAX_RATE, requiredData.get(0), Constants.TEXT);
//			foundation.click(LocationSummary.LBL_TAX_CAT_SAVE);
//			locationSummary.selectTab(tabName);
			textBox.enterText(LocationSummary.TXT_TAX_FILTER, requiredData.get(0));
			Map<String, String> uiData = table.getTblSingleRowRecordUI(LocationSummary.TBL_TAX_GRID,
					LocationSummary.TBL_ROW);

			Map<String, String> dbData_Tax = new HashMap<>();
			dbData_Tax.put(requiredData.get(8), requiredData.get(2));
			dbData_Tax.put(requiredData.get(9), requiredData.get(0));
			CustomisedAssert.assertEquals(uiData, dbData_Tax);

			// reading another tax rate
			navigationBar.navigateToMenuItem(menuItem.get(0));

			textBox.enterText(TaxList.LBL_SEARCH, requiredData.get(1));
			table.selectRow(requiredData.get(1));
			Map<String, String> uiTaxRates = table.getTblSingleRowRecordUI(TaxList.TBL_TAX_GRID, TaxList.TBL_ROW);
			String taxRate1 = uiTaxRates.get(requiredData.get(3)).substring(0, 4);
			String taxRate2 = uiTaxRates.get(requiredData.get(4)).substring(0, 4);
			String taxRate3 = uiTaxRates.get(requiredData.get(5)).substring(0, 4);
			String taxRate4 = uiTaxRates.get(requiredData.get(6)).substring(0, 4);

			// replacing 1st tax rate with another tax rate

			navigationBar.navigateToMenuItem(menuItem.get(3));
			locationList.selectLocationName(locationName);
			locationSummary.selectTab(tabName);
			textBox.enterText(LocationSummary.TXT_TAX_FILTER, requiredData.get(0));
			table.selectRow(requiredData.get(0));

			dropDown.selectItem(LocationSummary.DPD_TAX_RATE_2, requiredData.get(1), Constants.TEXT);
			foundation.click(LocationSummary.LBL_TAX_CAT_SAVE);
			locationSummary.selectTab(tabName);
			textBox.enterText(LocationSummary.TXT_TAX_FILTER, requiredData.get(1));
			uiData = table.getTblSingleRowRecordUI(LocationSummary.TBL_TAX_GRID, LocationSummary.TBL_ROW);

			dbData_Tax = new HashMap<>();
			dbData_Tax.put(requiredData.get(8), requiredData.get(2));
			dbData_Tax.put(requiredData.get(9), requiredData.get(1));
			CustomisedAssert.assertEquals(uiData, dbData_Tax);
			dropDown.selectItem(LocationSummary.DPD_KIOSK_LANGUAGE, requiredData.get(7), Constants.TEXT);
			foundation.click(LocationSummary.BTN_SYNC);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);

			// globalproduct
			navigationBar.navigateToMenuItem(menuItem.get(1));

			foundation.threadWait(Constants.MEDIUM_TIME);
			textBox.enterText(GlobalProduct.TXT_FILTER, product);
			table.selectRow(product);
			foundation.waitforElement(ProductSummary.DPD_TAX_CATEGORY, Constants.SHORT_TIME);
			dropDown.selectItem(ProductSummary.DPD_TAX_CATEGORY, requiredData.get(2), Constants.TEXT);
			String expectedPrice = foundation.getTextAttribute(ProductSummary.TXT_PRICE, Constants.SRC);
			double expectedTaxRate1 = taxList.getTaxAmount(taxRate1, expectedPrice);
			double expectedTaxRate2 = taxList.getTaxAmount(taxRate2, expectedPrice);
			double expectedTaxRate3 = taxList.getTaxAmount(taxRate3, expectedPrice);
			double expectedTaxRate4 = taxList.getTaxAmount(taxRate4, expectedPrice);

			foundation.click(ProductSummary.BTN_SAVE);
			// org summary
			navigationBar.navigateToMenuItem(menuItem.get(2));
			dropDown.selectItem(OrgSummary.DPD_TAX_SYSTEM, rstOrgSummaryData.get(CNOrgSummary.REQUIRED_DATA),
					Constants.TEXT);
			foundation.click(OrgSummary.BTN_SAVE);
			foundation.waitforElement(OrgSummary.TXT_SPINNER_MSG, Constants.THREE_SECOND);
			login.logout();
			browser.close();

			foundation.threadWait(Constants.SHORT_TIME);
			// login into Kiosk Device
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			foundation.waitforElement(LandingPage.IMG_SEARCH_ICON, Constants.SHORT_TIME);

			// Navigating to product search page
			foundation.waitforElement(LandingPage.IMG_SEARCH_ICON, Constants.SHORT_TIME);
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			foundation.waitforElement(AccountLogin.BTN_CAMELCASE, Constants.SHORT_TIME);

			// searching for product

			textBox.enterKeypadText(product);
			foundation.click(ProductSearch.BTN_PRODUCT);

			// verify Order Page
			List<String> orderPageData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.ORDER_PAGE).split(Constants.DELIMITER_TILD));

			String actualTaxRate1 = foundation.getText(Order.LBL_TAX_1);
			String actualTaxRate2 = foundation.getText(Order.LBL_TAX_2);
			String actualTaxRate3 = foundation.getText(Order.LBL_TAX_3);
			String actualTaxRate4 = foundation.getText(Order.LBL_TAX_4);
			String actualBalDue = foundation.getText(Order.LBL_BALANCE_DUE);
			String actualSubTotal = foundation.getText(Order.LBL_SUB_TOTAL);
			CustomisedAssert.assertEquals(actualTaxRate1, requiredData.get(10) + String.valueOf(expectedTaxRate1));
			CustomisedAssert.assertEquals(actualTaxRate2, requiredData.get(10) + String.valueOf(expectedTaxRate2));
			CustomisedAssert.assertEquals(actualTaxRate3, requiredData.get(10) + String.valueOf(expectedTaxRate3));
			CustomisedAssert.assertEquals(actualTaxRate4, requiredData.get(10) + String.valueOf(expectedTaxRate4));
			CustomisedAssert.assertEquals(actualSubTotal, requiredData.get(10) + expectedPrice);

			float expectedBalDue = (float) (Float.parseFloat(expectedPrice) + expectedTaxRate1 + expectedTaxRate2
					+ expectedTaxRate3 + expectedTaxRate4);
			CustomisedAssert.assertTrue(actualBalDue.contains(String.valueOf(expectedBalDue)));
			foundation.objectFocus(order.objText(orderPageData.get(1)));
			foundation.click(order.objText(orderPageData.get(1)));

			foundation.waitforElement(AccountLogin.BTN_NEXT, Constants.SHORT_TIME);

			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(
					propertyFile.readPropertyFile(Configuration.V5_USER, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(AccountLogin.BTN_NEXT);
			foundation.waitforElement(AccountLogin.BTN_PIN_NEXT, Constants.SHORT_TIME);
			textBox.enterPin(propertyFile.readPropertyFile(Configuration.V5_PIN, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(AccountLogin.BTN_PIN_NEXT);
			List<String> paymentPageData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.PAYMENTS_PAGE).split(Constants.DELIMITER_TILD));

			// CustomisedAssert.assertTrue(foundation.isDisplayed(payments.objText(paymentPageData.get(0))));

			foundation.click(payments.objText(paymentPageData.get(1)));
			foundation.waitforElement(LandingPage.IMG_SEARCH_ICON, Constants.SHORT_TIME);
			browser.close();
			// resetting test data
			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			locationList.selectLocationName(locationName);
			locationSummary.selectTab(tabName);
			textBox.enterText(LocationSummary.TXT_TAX_FILTER, requiredData.get(1));
			table.selectRow(requiredData.get(1));
			foundation.waitforElement(LocationSummary.LBL_TAX_CAT_REMOVE, Constants.SHORT_TIME);
			foundation.click(LocationSummary.LBL_TAX_CAT_REMOVE);
			foundation.waitforElement(LocationSummary.BTN_CLOSE_COMMERCIAL, Constants.SHORT_TIME);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "143530-QAA-192-Verify when tax rate is edited in tax rates page, it should display in tax mapping grid and also it should reflect in sales of product in Kiosk.")
	public void verifyEditTaxRate() {

		// Reading test data from DataBase
		final String CASE_NUM = "143530";
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);
		rstGlobalProductChangeData = dataBase.getGlobalProductChangeData(Queries.GLOBAL_PRODUCT_CHANGE, CASE_NUM);
		rstOrgSummaryData = dataBase.getOrgSummaryData(Queries.ORG_SUMMARY, CASE_NUM);
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);

		browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
		login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
				propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

		navigationBar.selectOrganization(
				propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
		String locationName = rstLocationData.get(CNLocation.LOCATION_NAME);
		String tabName = rstLocationData.get(CNLocation.TAB_NAME);
		String product = rstGlobalProductChangeData.get(CNGlobalProductChange.PRODUCT_NAME);
		String timeZone = rstLocationData.get(CNLocation.TIMEZONE);
		final String taxRateName = Constants.ACCOUNT_NAME + string.getRandomCharacter();
		List<String> requiredData = Arrays
				.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> menuItem = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		int date = Integer.parseInt(dateAndTime.getDateAndTime(requiredData.get(15), timeZone));
		String currentDay;
		if (date < 10) {
			currentDay = String.valueOf(date).substring(0, 1);
		} else {
			currentDay = String.valueOf(date);
		}

		try {

			navigationBar.navigateToMenuItem(menuItem.get(3));
			locationList.selectLocationName(locationName);
			locationSummary.selectTab(tabName);
			locationSummary.saveTaxMapping(requiredData.get(1), requiredData.get(0));
//			foundation.click(LocationSummary.LBL_TAX_MAPPING);
//			dropDown.selectItem(LocationSummary.DPD_TAX_CAT, requiredData.get(1), Constants.TEXT);
//			dropDown.selectItem(LocationSummary.DPD_TAX_RATE, requiredData.get(0), Constants.TEXT);
//			foundation.click(LocationSummary.LBL_TAX_CAT_SAVE);
			// locationSummary.selectTab(tabName);
			textBox.enterText(LocationSummary.TXT_TAX_FILTER, requiredData.get(0));
			Map<String, String> uiData = table.getTblSingleRowRecordUI(LocationSummary.TBL_TAX_GRID,
					LocationSummary.TBL_ROW);

			Map<String, String> dbData_Tax = new HashMap<>();
			dbData_Tax.put(requiredData.get(3), requiredData.get(1));
			dbData_Tax.put(requiredData.get(4), requiredData.get(0));
			CustomisedAssert.assertEquals(uiData, dbData_Tax);

			// editing tax rates and description name
			navigationBar.navigateToMenuItem(menuItem.get(0));
			textBox.enterText(TaxList.LBL_SEARCH, requiredData.get(0));
			table.selectRow(requiredData.get(0));

			foundation.click(TaxList.BTN_ADDRATE);
			textBox.enterText(TaxList.TXT_RATE_1, requiredData.get(6));
			textBox.enterText(TaxList.TXT_RATE_2, requiredData.get(7));
			textBox.enterText(TaxList.TXT_RATE_3, requiredData.get(8));
			textBox.enterText(TaxList.TXT_RATE_4, requiredData.get(9));

			foundation.click(TaxList.LBL_CALENDER);
			taxList.selectDate(currentDay);
			textBox.enterText(TaxList.TXT_EFFECTIVETIME, requiredData.get(14));
			foundation.click(TaxList.LBL_TAXRATE_SAVE);
			textBox.enterText(TaxList.TXT_DESCRIPTION, taxRateName);
			foundation.waitforClikableElement(TaxList.BTN_SAVE, Constants.SHORT_TIME);
			foundation.click(TaxList.BTN_SAVE);

			// navigating global product
			navigationBar.navigateToMenuItem(menuItem.get(1));
			foundation.threadWait(Constants.LONG_TIME);
			textBox.enterText(GlobalProduct.TXT_FILTER, product);
			table.selectRow(product);
			foundation.waitforElement(ProductSummary.DPD_TAX_CATEGORY, Constants.SHORT_TIME);
			dropDown.selectItem(ProductSummary.DPD_TAX_CATEGORY, requiredData.get(1), Constants.TEXT);
			String expectedPrice = foundation.getTextAttribute(ProductSummary.TXT_PRICE, Constants.SRC);
			double expectedTaxRate1 = taxList.getTaxAmount(requiredData.get(6), expectedPrice);
			double expectedTaxRate2 = taxList.getTaxAmount(requiredData.get(7), expectedPrice);
			double expectedTaxRate3 = taxList.getTaxAmount(requiredData.get(8), expectedPrice);
			double expectedTaxRate4 = taxList.getTaxAmount(requiredData.get(9), expectedPrice);
			foundation.click(ProductSummary.BTN_SAVE);
			// org summary
			navigationBar.navigateToMenuItem(menuItem.get(2));
			dropDown.selectItem(OrgSummary.DPD_TAX_SYSTEM, rstOrgSummaryData.get(CNOrgSummary.REQUIRED_DATA),
					Constants.TEXT);
			foundation.click(OrgSummary.BTN_SAVE);
			foundation.waitforElement(OrgSummary.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			// locationSummary page validations
			navigationBar.navigateToMenuItem(menuItem.get(3));
			locationList.selectLocationName(locationName);
			locationSummary.selectTab(tabName);
			textBox.enterText(LocationSummary.TXT_TAX_FILTER, taxRateName);
			uiData = table.getTblSingleRowRecordUI(LocationSummary.TBL_TAX_GRID, LocationSummary.TBL_ROW);

			dbData_Tax = new HashMap<>();
			dbData_Tax.put(requiredData.get(3), requiredData.get(1));
			dbData_Tax.put(requiredData.get(4), taxRateName);
			CustomisedAssert.assertEquals(uiData, dbData_Tax);
			CustomisedAssert.assertFalse(foundation.isDisplayed(locationSummary.objTable(requiredData.get(0))));

			// navigation global product
			dropDown.selectItem(LocationSummary.DPD_KIOSK_LANGUAGE, requiredData.get(2), Constants.TEXT);
			foundation.click(LocationSummary.BTN_SYNC);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);

			login.logout();
			browser.close();

			foundation.threadWait(Constants.SHORT_TIME);
			// login into Kiosk Device
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			foundation.waitforElement(LandingPage.IMG_SEARCH_ICON, Constants.SHORT_TIME);

			// Navigating to product search page
			foundation.waitforElement(LandingPage.IMG_SEARCH_ICON, Constants.SHORT_TIME);
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			foundation.waitforElement(AccountLogin.BTN_CAMELCASE, Constants.SHORT_TIME);

			// searching for product

			textBox.enterKeypadText(product);
			foundation.click(ProductSearch.BTN_PRODUCT);

			// verify Order Page
			List<String> orderPageData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.ORDER_PAGE).split(Constants.DELIMITER_TILD));
			CustomisedAssert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(0))));
			String actualTaxRate1 = foundation.getText(Order.LBL_TAX_1);
			String actualTaxRate2 = foundation.getText(Order.LBL_TAX_2);
			String actualTaxRate3 = foundation.getText(Order.LBL_TAX_3);
			String actualTaxRate4 = foundation.getText(Order.LBL_TAX_4);
			String actualBalDue = foundation.getText(Order.LBL_BALANCE_DUE);
			String actualSubTotal = foundation.getText(Order.LBL_SUB_TOTAL);
			CustomisedAssert.assertEquals(actualTaxRate1, requiredData.get(5) + String.valueOf(expectedTaxRate1));
			CustomisedAssert.assertEquals(actualTaxRate2, requiredData.get(5) + String.valueOf(expectedTaxRate2));
			CustomisedAssert.assertEquals(actualTaxRate3, requiredData.get(5) + String.valueOf(expectedTaxRate3));
			CustomisedAssert.assertEquals(actualTaxRate4, requiredData.get(5) + String.valueOf(expectedTaxRate4));
			CustomisedAssert.assertEquals(actualSubTotal, requiredData.get(5) + expectedPrice);

			float expectedBalDue = (float) (Float.parseFloat(expectedPrice) + expectedTaxRate1 + expectedTaxRate2
					+ expectedTaxRate3 + expectedTaxRate4);
			CustomisedAssert.assertTrue(actualBalDue.contains(String.valueOf(expectedBalDue)));
			foundation.objectFocus(order.objText(orderPageData.get(1)));
			foundation.click(order.objText(orderPageData.get(1)));

			foundation.waitforElement(AccountLogin.BTN_NEXT, Constants.SHORT_TIME);

			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(
					propertyFile.readPropertyFile(Configuration.V5_USER, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(AccountLogin.BTN_NEXT);
			foundation.waitforElement(AccountLogin.BTN_PIN_NEXT, Constants.SHORT_TIME);
			textBox.enterPin(propertyFile.readPropertyFile(Configuration.V5_PIN, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(AccountLogin.BTN_PIN_NEXT);
			List<String> paymentPageData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.PAYMENTS_PAGE).split(Constants.DELIMITER_TILD));

			CustomisedAssert.assertTrue(foundation.isDisplayed(payments.objText(paymentPageData.get(0))));

			foundation.click(payments.objText(paymentPageData.get(1)));
			foundation.waitforElement(LandingPage.IMG_SEARCH_ICON, Constants.SHORT_TIME);
			browser.close();

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// resetting test data
			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			locationList.selectLocationName(locationName);
			locationSummary.selectTab(tabName);
			textBox.enterText(LocationSummary.TXT_TAX_FILTER, taxRateName);
			table.selectRow(taxRateName);
			foundation.waitforElement(LocationSummary.LBL_TAX_CAT_REMOVE, Constants.SHORT_TIME);
			foundation.click(LocationSummary.LBL_TAX_CAT_REMOVE);
			foundation.waitforElement(LocationSummary.BTN_CLOSE_COMMERCIAL, Constants.SHORT_TIME);

			navigationBar.navigateToMenuItem(menuItem.get(0));
			textBox.enterText(TaxList.LBL_SEARCH, taxRateName);
			table.selectRow(taxRateName);

			foundation.click(TaxList.BTN_ADDRATE);
			textBox.enterText(TaxList.TXT_RATE_1, requiredData.get(10));
			textBox.enterText(TaxList.TXT_RATE_2, requiredData.get(11));
			textBox.enterText(TaxList.TXT_RATE_3, requiredData.get(12));
			textBox.enterText(TaxList.TXT_RATE_4, requiredData.get(13));

			foundation.click(TaxList.LBL_CALENDER);
			taxList.selectDate(currentDay);
			textBox.enterText(TaxList.TXT_EFFECTIVETIME, requiredData.get(14));
			foundation.click(TaxList.LBL_TAXRATE_SAVE);
			textBox.enterText(TaxList.TXT_DESCRIPTION, requiredData.get(0));
			foundation.click(TaxList.BTN_SAVE);

		}
	}

	@Test(description = "143063-Validate v5 transactions with Active Bundle promotions with Flash Sale")
	public void bundleFlashSale() {
		final String CASE_NUM = "143063";
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);
		final String promotionName = string.getRandomCharacter();
		String gridName = rstLocationData.get(CNLocation.TAB_NAME);
		List<String> navigationMenu = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		try {
			// Reading test data from database
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			String displayName = string.getRandomCharacter();
			String promotionType = rstLocationData.get(CNLocation.PROMOTION_TYPE);
			String locationName = propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1,
					FilePath.PROPERTY_CONFIG_FILE);

			List<String> requiredData = Arrays
					.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> language = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));

			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(navigationMenu.get(0));

			// New Promotion
			createPromotions.BundlePromotion(promotionType, promotionName, displayName, requiredData.get(0),
					locationName);
			createPromotions.selectBundlePromotionDetails(requiredData.get(1), requiredData.get(2), requiredData.get(4),
					rstV5DeviceData.get(CNLocation.REQUIRED_DATA));
			createPromotions.selectBundlePromotionPricing(requiredData.get(6));
			createPromotions.selectBundlePromotionTimes(requiredData.get(3), requiredData.get(5));

			String priceTotal = foundation.getText(CreatePromotions.LBL_TOTAL_PRICE);
			String bundleDiscount = foundation.getText(CreatePromotions.LBL_BUNDLE_DISCOUNT);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_OK);

			foundation.waitforElement(PromotionList.TXT_SEARCH_PROMONAME, Constants.SHORT_TIME);
			navigationBar.navigateToMenuItem(navigationMenu.get(1));

			// Selecting location
			locationList.selectLocationName(locationName);

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

			locationSummary.kiosklanguageSetting(locationName, language.get(0), language.get(1));

			foundation.threadWait(Constants.SHORT_TIME);
			// login into Kiosk Device
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));

			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(requiredData.get(2));
			foundation.click(ProductSearch.BTN_PRODUCT);
			CustomisedAssert.assertTrue(foundation.isDisplayed(Order.BTN_CANCEL_ORDER));

			CustomisedAssert.assertTrue(displayName.equals(foundation.getText(Order.LBL_PROMOTION_NAME)));
			List<String> discountList = foundation.getTextofListElement(Order.LBL_ORDER_DISCOUNT);
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
			editPromotion.expirePromotion(gridName, promotionName);
		}
	}

	@Test(description = "143064-Validate v5 transactions with expired Bundle promotions")
	public void expiredbundleFlashSale() {
		try {
			final String CASE_NUM = "143064";

			// Reading test data from database
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			final String promotionName = string.getRandomCharacter();
			String displayName = string.getRandomCharacter();
			String promotionType = rstLocationData.get(CNLocation.PROMOTION_TYPE);
			String locationName = propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1,
					FilePath.PROPERTY_CONFIG_FILE);
			String gridName = rstLocationData.get(CNLocation.TAB_NAME);

			List<String> navigationMenu = Arrays
					.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
			List<String> requiredData = Arrays
					.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> language = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));

			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(navigationMenu.get(0));

			// New Promotion
			createPromotions.BundlePromotion(promotionType, promotionName, displayName, requiredData.get(0),
					locationName);
			createPromotions.selectBundlePromotionDetails(requiredData.get(1), requiredData.get(2), requiredData.get(4),
					rstV5DeviceData.get(CNLocation.REQUIRED_DATA));
			createPromotions.selectBundlePromotionPricing(requiredData.get(6));
			createPromotions.selectBundlePromotionTimes(requiredData.get(3), requiredData.get(5));

			String priceTotal = foundation.getText(CreatePromotions.LBL_TOTAL_PRICE);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_OK);

			foundation.waitforElement(PromotionList.TXT_SEARCH_PROMONAME, Constants.SHORT_TIME);

			// Expiring the Promotion
			editPromotion.expirePromotion(gridName, promotionName);
			foundation.waitforElement(PromotionList.TXT_SEARCH_PROMONAME, Constants.SHORT_TIME);
			navigationBar.navigateToMenuItem(navigationMenu.get(1));

			// Selecting location
			locationList.selectLocationName(locationName);

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

			locationSummary.kiosklanguageSetting(locationName, language.get(0), language.get(1));

			foundation.threadWait(Constants.SHORT_TIME);
			// login into Kiosk Device
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));

			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(requiredData.get(2));
			foundation.click(ProductSearch.BTN_PRODUCT);
			CustomisedAssert.assertTrue(foundation.isDisplayed(Order.BTN_CANCEL_ORDER));

			CustomisedAssert.assertTrue(requiredData.get(2).equals(foundation.getText(Order.LBL_PROMOTION_NAME)));

			// verify the display of total section
			String productPrice = foundation.getText(Order.LBL_PRODUCT_PRICE).split(Constants.DOLLAR)[1];
			CustomisedAssert
					.assertTrue(foundation.getText(Order.LBL_BALANCE_DUE).contains(String.valueOf(productPrice)));
			CustomisedAssert.assertTrue(foundation.getText(Order.LBL_SUB_TOTAL).contains(priceTotal));

			List<String> orderPageData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.ORDER_PAGE).split(Constants.DELIMITER_TILD));
			List<String> paymentPageData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.PAYMENTS_PAGE).split(Constants.DELIMITER_TILD));
			CustomisedAssert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(0))));
			foundation.objectFocus(order.objText(orderPageData.get(1)));
			order.completeOrder(orderPageData.get(1), paymentPageData.get(0), paymentPageData.get(1));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "143065-Validate v5 transactions with Discount on multiple Line items with Recurrence for Bundle Promotion")
	public void recurrencebundlePromotion() {
		final String CASE_NUM = "143065";
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		final String promotionName = string.getRandomCharacter();
		String gridName = rstLocationData.get(CNLocation.TAB_NAME);

		List<String> navigationMenu = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		try {
			// Reading test data from database
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			String displayName = string.getRandomCharacter();
			String promotionType = rstLocationData.get(CNLocation.PROMOTION_TYPE);
			String locationName = propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1,
					FilePath.PROPERTY_CONFIG_FILE);

			List<String> requiredData = Arrays
					.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> language = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));

			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(navigationMenu.get(0));

			// New Promotion
			createPromotions.BundlePromotion(promotionType, promotionName, displayName, requiredData.get(0),
					locationName);
			createPromotions.selectBundlePromotionDetails(requiredData.get(1), requiredData.get(2), requiredData.get(5),
					rstV5DeviceData.get(CNLocation.REQUIRED_DATA));

			textBox.enterText(CreatePromotions.TXT_ITEM1, requiredData.get(3));
			foundation.threadWait(Constants.ONE_SECOND);
			textBox.enterText(CreatePromotions.TXT_ITEM1, Keys.ENTER);
			foundation.threadWait(Constants.TWO_SECOND);
			textBox.enterText(CreatePromotions.TXT_TRANSACTION_MIN, requiredData.get(5));

			createPromotions.selectBundlePromotionPricing(requiredData.get(6));
			createPromotions.selectBundlePromotionTimes(requiredData.get(4), Constants.DELIMITER_SPACE);
			createPromotions.recurringDay();

			String priceTotal = foundation.getText(CreatePromotions.LBL_TOTAL_PRICE).split(Constants.DOLLAR)[1];
			String bundleDiscount = foundation.getText(CreatePromotions.LBL_BUNDLE_DISCOUNT);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_OK);

			foundation.waitforElement(PromotionList.TXT_SEARCH_PROMONAME, Constants.SHORT_TIME);
			navigationBar.navigateToMenuItem(navigationMenu.get(1));

			// Selecting location
			locationList.selectLocationName(locationName);

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

			locationSummary.kiosklanguageSetting(locationName, language.get(0), language.get(1));

			foundation.threadWait(Constants.SHORT_TIME);
			// login into Kiosk Device
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));

			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(requiredData.get(2));
			foundation.click(ProductSearch.BTN_PRODUCT);
			CustomisedAssert.assertTrue(foundation.isDisplayed(Order.BTN_CANCEL_ORDER));

			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(requiredData.get(3));
			foundation.click(ProductSearch.BTN_PRODUCT);
			CustomisedAssert.assertTrue(foundation.isDisplayed(Order.BTN_CANCEL_ORDER));

			CustomisedAssert.assertTrue(displayName.equals(foundation.getText(Order.LBL_PROMOTION_NAME)));
			List<String> discountList = foundation.getTextofListElement(Order.LBL_ORDER_DISCOUNT);
			CustomisedAssert.assertTrue(discountList.get(2).equals(bundleDiscount));

			// verify the display of total section
			// String productPrice =
			// foundation.getText(Order.LBL_PRODUCT_PRICE).split(Constants.DOLLAR)[1];
			String discount = foundation.getText(Order.LBL_DEPOSIT).split(Constants.DOLLAR)[1];
			Double expectedBalanceDue = Double.parseDouble(priceTotal) - Double.parseDouble(discount);
			CustomisedAssert
					.assertTrue(foundation.getText(Order.LBL_BALANCE_DUE).contains(String.valueOf(expectedBalanceDue)));
			CustomisedAssert.assertTrue(foundation.getText(Order.LBL_SUB_TOTAL).contains(priceTotal));
			CustomisedAssert.assertEquals(foundation.getText(Order.LBL_DISCOUNT),
					Constants.DELIMITER_HYPHEN + bundleDiscount);

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
			editPromotion.expirePromotion(gridName, promotionName);
		}
	}

	@Test(description = "143066-Validate v5 transactions with Discount Category with Scheduled for Bundle Promotion")
	public void discountbundlePromotion() {
		final String CASE_NUM = "143066";
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		final String promotionName = string.getRandomCharacter();
		String gridName = rstLocationData.get(CNLocation.TAB_NAME);

		List<String> navigationMenu = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		try {
			// Reading test data from database
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			String displayName = string.getRandomCharacter();
			String promotionType = rstLocationData.get(CNLocation.PROMOTION_TYPE);
			String locationName = propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1,
					FilePath.PROPERTY_CONFIG_FILE);

			List<String> requiredData = Arrays
					.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> language = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));

			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(navigationMenu.get(0));

			// New Promotion
			createPromotions.BundlePromotion(promotionType, promotionName, displayName, requiredData.get(0),
					locationName);
			createPromotions.selectBundlePromotionDetails(requiredData.get(1), requiredData.get(2), requiredData.get(5),
					rstV5DeviceData.get(CNLocation.REQUIRED_DATA));
			createPromotions.selectBundlePromotionPricing(requiredData.get(6));
			createPromotions.selectBundlePromotionTimes(requiredData.get(4), Constants.DELIMITER_SPACE);

			List<String> bundleDiscount = foundation.getTextofListElement(CreatePromotions.LBL_BUNDLE_DISCOUNT);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_OK);

			foundation.waitforElement(PromotionList.TXT_SEARCH_PROMONAME, Constants.SHORT_TIME);
			navigationBar.navigateToMenuItem(navigationMenu.get(1));

			// Selecting location
			locationList.selectLocationName(locationName);

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

			locationSummary.kiosklanguageSetting(locationName, language.get(0), language.get(1));

			foundation.threadWait(Constants.SHORT_TIME);
			// login into Kiosk Device
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));

			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(requiredData.get(3));
			foundation.click(ProductSearch.BTN_PRODUCT);
			CustomisedAssert.assertTrue(foundation.isDisplayed(Order.BTN_CANCEL_ORDER));

			CustomisedAssert.assertTrue(displayName.equals(foundation.getText(Order.LBL_PROMOTION_NAME)));
			List<String> discountList = foundation.getTextofListElement(Order.LBL_ORDER_DISCOUNT);
			CustomisedAssert.assertTrue(Double.parseDouble(discountList.get(2).split(Constants.DOLLAR)[1]) >= Double
					.parseDouble(bundleDiscount.get(0).split(Constants.DOLLAR)[1])
					&& Double.parseDouble(discountList.get(2).split(Constants.DOLLAR)[1]) <= Double
							.parseDouble(bundleDiscount.get(1).split(Constants.DOLLAR)[1]));

			// verify the display of total section
			String productPrice = foundation.getText(Order.LBL_PRODUCT_PRICE).split(Constants.DOLLAR)[1];
			String discount = foundation.getText(Order.LBL_DEPOSIT).split(Constants.DOLLAR)[1];
			Double expectedBalanceDue = Double.parseDouble(productPrice) - Double.parseDouble(discount);
			CustomisedAssert
					.assertTrue(foundation.getText(Order.LBL_BALANCE_DUE).contains(String.valueOf(expectedBalanceDue)));
			CustomisedAssert.assertTrue(foundation.getText(Order.LBL_SUB_TOTAL).contains(productPrice));
			CustomisedAssert.assertEquals(foundation.getText(Order.LBL_DISCOUNT),
					Constants.DELIMITER_HYPHEN + "$" + discount);

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
			editPromotion.expirePromotion(gridName, promotionName);
		}
	}

	@Test(description = "143067-Validate v5 transactions with Min Transaction amount for Bundle Promotion")
	public void minTransactionbundlePromotion() {
		final String CASE_NUM = "143067";
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		final String promotionName = string.getRandomCharacter();
		String gridName = rstLocationData.get(CNLocation.TAB_NAME);

		List<String> navigationMenu = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		try {
			// Reading test data from database
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			String displayName = string.getRandomCharacter();
			String promotionType = rstLocationData.get(CNLocation.PROMOTION_TYPE);
			String locationName = propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1,
					FilePath.PROPERTY_CONFIG_FILE);

			List<String> requiredData = Arrays
					.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> language = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));

			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(navigationMenu.get(0));

			// New Promotion
			createPromotions.BundlePromotion(promotionType, promotionName, displayName, requiredData.get(0),
					locationName);
			createPromotions.selectBundlePromotionDetails(requiredData.get(1), requiredData.get(2), requiredData.get(5),
					rstV5DeviceData.get(CNLocation.REQUIRED_DATA));
			createPromotions.selectBundlePromotionPricing(requiredData.get(6));
			createPromotions.selectBundlePromotionTimes(requiredData.get(4), Constants.DELIMITER_SPACE);

			List<String> bundleDiscount = foundation.getTextofListElement(CreatePromotions.LBL_BUNDLE_DISCOUNT);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_OK);

			foundation.waitforElement(PromotionList.TXT_SEARCH_PROMONAME, Constants.SHORT_TIME);
			navigationBar.navigateToMenuItem(navigationMenu.get(1));

			// Selecting location
			locationList.selectLocationName(locationName);

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

			locationSummary.kiosklanguageSetting(locationName, language.get(0), language.get(1));

			foundation.threadWait(Constants.SHORT_TIME);
			// login into Kiosk Device
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));

			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(requiredData.get(3));
			foundation.click(ProductSearch.BTN_PRODUCT);
			CustomisedAssert.assertTrue(foundation.isDisplayed(Order.BTN_CANCEL_ORDER));

			CustomisedAssert.assertTrue(displayName.equals(foundation.getText(Order.LBL_PROMOTION_NAME)));
			List<String> discountList = foundation.getTextofListElement(Order.LBL_ORDER_DISCOUNT);

			CustomisedAssert.assertTrue(Double.parseDouble(discountList.get(2).split(Constants.DOLLAR)[1]) >= Double
					.parseDouble(bundleDiscount.get(0).split(Constants.DOLLAR)[1])
					&& Double.parseDouble(discountList.get(2).split(Constants.DOLLAR)[1]) <= Double
							.parseDouble(bundleDiscount.get(1).split(Constants.DOLLAR)[1]));

			// verify the display of total section
			String productPrice = foundation.getText(Order.LBL_PRODUCT_PRICE).split(Constants.DOLLAR)[1];
			String discount = foundation.getText(Order.LBL_DEPOSIT).split(Constants.DOLLAR)[1];
			Double expectedBalanceDue = Double.parseDouble(productPrice) - Double.parseDouble(discount);
			CustomisedAssert
					.assertTrue(foundation.getText(Order.LBL_BALANCE_DUE).contains(String.valueOf(expectedBalanceDue)));
			CustomisedAssert.assertTrue(foundation.getText(Order.LBL_SUB_TOTAL).contains(productPrice));
			// CustomisedAssert.assertEquals(foundation.getText(Order.LBL_DISCOUNT),Constants.DELIMITER_HYPHEN+bundleDiscount);
			CustomisedAssert.assertEquals(foundation.getText(Order.LBL_DISCOUNT),
					Constants.DELIMITER_HYPHEN + "$" + discount);

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
			editPromotion.expirePromotion(gridName, promotionName);
		}
	}

	@Test(description = "143068-Validate v5 transactions with discount more than Transaction amount for Bundle Promotion")
	public void discountTransactionbundlePromotion() {
		final String CASE_NUM = "143068";
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);
		String gridName = rstLocationData.get(CNLocation.TAB_NAME);
		final String promotionName = string.getRandomCharacter();

		List<String> navigationMenu = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> requiredData = Arrays
				.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		try {
			// Reading test data from database
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			String displayName = string.getRandomCharacter();
			String promotionType = rstLocationData.get(CNLocation.PROMOTION_TYPE);
			String locationName = propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1,
					FilePath.PROPERTY_CONFIG_FILE);

			List<String> language = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));

			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(navigationMenu.get(0));

			// New Promotion
			createPromotions.BundlePromotion(promotionType, promotionName, displayName, requiredData.get(0),
					locationName);
			createPromotions.selectBundlePromotionDetails(requiredData.get(1), requiredData.get(2), requiredData.get(5),
					rstV5DeviceData.get(CNLocation.REQUIRED_DATA));
			createPromotions.selectBundlePromotionPricing(requiredData.get(6));
			createPromotions.selectBundlePromotionTimes(requiredData.get(4), Constants.DELIMITER_SPACE);

			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.waitforElement(CreatePromotions.BTN_CONTINUE, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_CONTINUE);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_OK);

			foundation.waitforElement(PromotionList.TXT_SEARCH_PROMONAME, Constants.SHORT_TIME);
			navigationBar.navigateToMenuItem(navigationMenu.get(1));

			// Selecting location
			locationList.selectLocationName(locationName);

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

			locationSummary.kiosklanguageSetting(locationName, language.get(0), language.get(1));

			foundation.threadWait(Constants.SHORT_TIME);
			// login into Kiosk Device
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));

			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(requiredData.get(3));
			foundation.click(ProductSearch.BTN_PRODUCT);
			CustomisedAssert.assertTrue(foundation.isDisplayed(Order.BTN_CANCEL_ORDER));

			CustomisedAssert.assertTrue(requiredData.get(3).equals(foundation.getText(Order.LBL_PROMOTION_NAME)));

			// verify the display of total section
			String productPrice = foundation.getText(Order.LBL_PRODUCT_PRICE).split(Constants.DOLLAR)[1];
			CustomisedAssert
					.assertTrue(foundation.getText(Order.LBL_BALANCE_DUE).contains(String.valueOf(productPrice)));
			CustomisedAssert.assertTrue(foundation.getText(Order.LBL_SUB_TOTAL).contains(productPrice));

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
			editPromotion.expirePromotion(gridName, promotionName);

		}
	}

	@Test(description = "143069-Validate v5 transactions with Discount Amount exceeds Bundle Price for Bundle Promotion")
	public void discountExceedsbundlePromotion() {
		final String CASE_NUM = "143069";
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		final String promotionName = string.getRandomCharacter();
		String gridName = rstLocationData.get(CNLocation.TAB_NAME);

		List<String> navigationMenu = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> requiredData = Arrays
				.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));

		try {
			// Reading test data from database
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstOrgSummaryData = dataBase.getOrgSummaryData(Queries.ORG_SUMMARY, CASE_NUM);

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			String displayName = string.getRandomCharacter();
			String promotionType = rstLocationData.get(CNLocation.PROMOTION_TYPE);
			String locationName = propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1,
					FilePath.PROPERTY_CONFIG_FILE);

			List<String> language = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));

			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(navigationMenu.get(0));

			// New Promotion
			createPromotions.BundlePromotion(promotionType, promotionName, displayName, requiredData.get(0),
					locationName);
			createPromotions.selectBundlePromotionDetails(requiredData.get(1), requiredData.get(2), requiredData.get(5),
					rstV5DeviceData.get(CNLocation.REQUIRED_DATA));
			createPromotions.selectBundlePromotionPricing(requiredData.get(6));
			foundation.click(CreatePromotions.RB_BUNDLE_AMOUNT);
			foundation.click(CreatePromotions.CHK_BUNDLE_OVERFLOW);
			createPromotions.selectBundlePromotionTimes(requiredData.get(4), Constants.DELIMITER_SPACE);

			// String priceTotal=foundation.getText(CreatePromotions.LBL_TOTAL_PRICE);
			// String bundleDiscount=
			// foundation.getText(CreatePromotions.LBL_BUNDLE_DISCOUNT);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.waitforElement(CreatePromotions.BTN_CONTINUE, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_CONTINUE);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_OK);

			foundation.waitforElement(PromotionList.TXT_SEARCH_PROMONAME, Constants.SHORT_TIME);
			navigationBar.navigateToMenuItem(navigationMenu.get(2));

			// Selecting location
			locationList.selectLocationName(locationName);

			foundation.click(LocationSummary.BTN_SYNC);
			// foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			login.logout();

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.navigateToMenuItem(navigationMenu.get(1));
			foundation.waitforClikableElement(OrgSummary.DPD_COUNTRY, Constants.SHORT_TIME);
			dropDown.selectItem(OrgSummary.DPD_COUNTRY, rstOrgSummaryData.get(CNOrgSummary.REQUIRED_DATA),
					Constants.TEXT);
			dropDown.selectItem(OrgSummary.DPD_TAX_SYSTEM, rstOrgSummaryData.get(CNOrgSummary.REQUIRED_DATA),
					Constants.TEXT);
			foundation.click(OrgSummary.BTN_SAVE);
			foundation.waitforClikableElement(OrgList.LBL_ORG_LIST, Constants.SHORT_TIME);

			navigationBar.navigateToMenuItem(navigationMenu.get(2));

			locationSummary.kiosklanguageSetting(locationName, language.get(0), language.get(1));

			foundation.threadWait(Constants.SHORT_TIME);
			// login into Kiosk Device
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));

			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(requiredData.get(3));
			foundation.click(ProductSearch.BTN_PRODUCT);
			CustomisedAssert.assertTrue(foundation.isDisplayed(Order.BTN_CANCEL_ORDER));

			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(requiredData.get(7));
			foundation.click(ProductSearch.BTN_PRODUCT);
			CustomisedAssert.assertTrue(foundation.isDisplayed(Order.BTN_CANCEL_ORDER));

			CustomisedAssert.assertTrue(displayName.equals(foundation.getText(Order.LBL_PROMOTION_NAME)));
			List<String> discountList = foundation.getTextofListElement(Order.LBL_ORDER_DISCOUNT);
			CustomisedAssert.assertTrue(discountList.get(2).equals(foundation.getText(Order.LBL_PRODUCT_PRICE)));

			List<String> subProductPrice = foundation.getTextofListElement(Order.LBL_MULTI_PRODUCTS);
			// verify the display of total section
			Double totalProductPrice = Double.parseDouble(subProductPrice.get(0).split(Constants.DOLLAR)[1])
					+ Double.parseDouble(subProductPrice.get(1).split(Constants.DOLLAR)[1]);
			String productPrice = String.format("%.2f", totalProductPrice);
			// String discount =
			// foundation.getText(Order.LBL_DEPOSIT).split(Constants.DOLLAR)[1];
			Double expectedBalanceDue = totalProductPrice - totalProductPrice;
			String balanceDue = String.format("%.2f", expectedBalanceDue);
			CustomisedAssert.assertTrue(foundation.getText(Order.LBL_BALANCE_DUE).contains(balanceDue));
			CustomisedAssert.assertTrue(foundation.getText(Order.LBL_SUB_TOTAL).contains(String.valueOf(productPrice)));
			CustomisedAssert.assertTrue(foundation.getText(Order.LBL_DISCOUNT).contains(String.valueOf(productPrice)));

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
			editPromotion.expirePromotion(gridName, promotionName);
		}
	}

	@Test(description = "143070-Validate v5 transactions without Discount when different device type is selected for Bundle Promotion")
	public void devicebundlePromotion() {
		final String CASE_NUM = "143070";
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		final String promotionName = string.getRandomCharacter();
		String gridName = rstLocationData.get(CNLocation.TAB_NAME);

		List<String> navigationMenu = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		try {
			// Reading test data from database
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstOrgSummaryData = dataBase.getOrgSummaryData(Queries.ORG_SUMMARY, CASE_NUM);

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			String displayName = string.getRandomCharacter();
			String promotionType = rstLocationData.get(CNLocation.PROMOTION_TYPE);
			String locationName = propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1,
					FilePath.PROPERTY_CONFIG_FILE);

			List<String> requiredData = Arrays
					.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> language = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));

			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(navigationMenu.get(0));

			// New Promotion
			foundation.waitforElement(PromotionList.BTN_CREATE, Constants.SHORT_TIME);
			foundation.click(PromotionList.BTN_CREATE);
			foundation.isDisplayed(CreatePromotions.LBL_CREATE_PROMOTION);
			createPromotions.newPromotion(promotionType, promotionName, displayName, requiredData.get(0), locationName);

			dropDown.selectItem(CreatePromotions.DPD_DEVICE, requiredData.get(7), Constants.TEXT);
			foundation.click(CreatePromotions.BTN_NEXT);
			createPromotions.selectBundlePromotionDetails(requiredData.get(1), requiredData.get(2), requiredData.get(5),
					rstV5DeviceData.get(CNLocation.REQUIRED_DATA));
			createPromotions.selectBundlePromotionPricing(requiredData.get(6));

			foundation.click(CreatePromotions.RB_BUNDLE_PRICE);
			createPromotions.selectBundlePromotionTimes(requiredData.get(4), Constants.DELIMITER_SPACE);

			// String priceTotal=foundation.getText(CreatePromotions.LBL_TOTAL_PRICE);
			String bundleDiscount = foundation.getText(CreatePromotions.LBL_BUNDLE_DISCOUNT);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_OK);

			foundation.waitforElement(PromotionList.TXT_SEARCH_PROMONAME, Constants.SHORT_TIME);
			navigationBar.navigateToMenuItem(navigationMenu.get(2));

			// Selecting location
			locationList.selectLocationName(locationName);

			foundation.click(LocationSummary.BTN_SYNC);
			// foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			login.logout();

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.navigateToMenuItem(navigationMenu.get(1));
			foundation.waitforClikableElement(OrgSummary.DPD_COUNTRY, Constants.SHORT_TIME);
			dropDown.selectItem(OrgSummary.DPD_COUNTRY, rstOrgSummaryData.get(CNOrgSummary.REQUIRED_DATA),
					Constants.TEXT);
			dropDown.selectItem(OrgSummary.DPD_TAX_SYSTEM, rstOrgSummaryData.get(CNOrgSummary.REQUIRED_DATA),
					Constants.TEXT);
			foundation.click(OrgSummary.BTN_SAVE);
			foundation.waitforClikableElement(OrgList.LBL_ORG_LIST, Constants.SHORT_TIME);

			navigationBar.navigateToMenuItem(navigationMenu.get(2));
			locationSummary.kiosklanguageSetting(locationName, language.get(0), language.get(1));

			foundation.threadWait(Constants.SHORT_TIME);
			// login into Kiosk Device
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));

			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(requiredData.get(3));
			foundation.click(ProductSearch.BTN_PRODUCT);
			CustomisedAssert.assertTrue(foundation.isDisplayed(Order.BTN_CANCEL_ORDER));

			CustomisedAssert.assertTrue(displayName.equals(foundation.getText(Order.LBL_PROMOTION_NAME)));
			List<String> discountList = foundation.getTextofListElement(Order.LBL_ORDER_DISCOUNT);
			CustomisedAssert.assertTrue(discountList.get(2).contains(bundleDiscount));

			// verify the display of total section
			String productPrice = foundation.getText(Order.LBL_PRODUCT_PRICE).split(Constants.DOLLAR)[1];

			// remove next 5 lines and uncomment next 2 lines
			String discount = foundation.getText(Order.LBL_DEPOSIT).split(Constants.DOLLAR)[1];
			Double expectedBalanceDue = Double.parseDouble(productPrice) - Double.parseDouble(discount);
			CustomisedAssert
					.assertTrue(foundation.getText(Order.LBL_BALANCE_DUE).contains(String.valueOf(expectedBalanceDue)));
			CustomisedAssert.assertTrue(foundation.getText(Order.LBL_SUB_TOTAL).contains(productPrice));
			CustomisedAssert.assertTrue(foundation.getText(Order.LBL_DISCOUNT).contains(discount));

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
			editPromotion.expirePromotion(gridName, promotionName);
		}
	}

	@Test(description = "143071-Validate v5 transactions with Active Onscreen promotions with Recurrence")
	public void onScreenRecurrence() {
		final String CASE_NUM = "143071";
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		String gridName = rstLocationData.get(CNLocation.TAB_NAME);
		final String promotionName = string.getRandomCharacter();
		List<String> navigationMenu = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> requiredData = Arrays
				.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		try {
			// Reading test data from database
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstOrgSummaryData = dataBase.getOrgSummaryData(Queries.ORG_SUMMARY, CASE_NUM);

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			String displayName = string.getRandomCharacter();
			String promotionType = rstLocationData.get(CNLocation.PROMOTION_TYPE);
			String locationName = propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1,
					FilePath.PROPERTY_CONFIG_FILE);

			List<String> language = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));

			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(navigationMenu.get(0));

			// New Promotion
			createPromotions.BundlePromotion(promotionType, promotionName, displayName, requiredData.get(0),
					locationName);
			createPromotions.selectBundlePromotionDetails(requiredData.get(1), requiredData.get(2), requiredData.get(5),
					rstV5DeviceData.get(CNLocation.REQUIRED_DATA));
			createPromotions.selectBundlePromotionTimes(requiredData.get(3), Constants.DELIMITER_SPACE);
			createPromotions.recurringDay();

			String onScreenDiscount = foundation.getAttributeValue(CreatePromotions.TXT_AMOUNT);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_OK);

			foundation.waitforElement(PromotionList.TXT_SEARCH_PROMONAME, Constants.SHORT_TIME);
			navigationBar.navigateToMenuItem(navigationMenu.get(2));

			// Selecting location
			locationList.selectLocationName(locationName);

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

			navigationBar.navigateToMenuItem(navigationMenu.get(1));
			foundation.waitforClikableElement(OrgSummary.DPD_COUNTRY, Constants.SHORT_TIME);
			dropDown.selectItem(OrgSummary.DPD_COUNTRY, rstOrgSummaryData.get(CNOrgSummary.REQUIRED_DATA),
					Constants.TEXT);
			dropDown.selectItem(OrgSummary.DPD_TAX_SYSTEM, rstOrgSummaryData.get(CNOrgSummary.REQUIRED_DATA),
					Constants.TEXT);
			foundation.click(OrgSummary.BTN_SAVE);
			foundation.waitforClikableElement(OrgList.LBL_ORG_LIST, Constants.SHORT_TIME);

			navigationBar.navigateToMenuItem(navigationMenu.get(2));
			locationSummary.kiosklanguageSetting(locationName, language.get(0), language.get(1));

			foundation.threadWait(Constants.SHORT_TIME);
			// login into Kiosk Device
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));

			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(requiredData.get(2));
			foundation.click(ProductSearch.BTN_PRODUCT);
			CustomisedAssert.assertTrue(foundation.isDisplayed(Order.BTN_CANCEL_ORDER));

			CustomisedAssert.assertTrue(displayName.equals(foundation.getText(Order.LBL_DISCOUNT_NAME)));
			List<String> discountList = foundation.getTextofListElement(Order.LBL_ORDER_DISCOUNT);
			CustomisedAssert.assertTrue(discountList.get(2).contains(onScreenDiscount));

			// verify the display of total section
			String productPrice = foundation.getText(Order.LBL_PRODUCT_PRICE).split(Constants.DOLLAR)[1];
			String discount = foundation.getText(Order.LBL_DEPOSIT).split(Constants.DOLLAR)[1];
			Double expectedBalanceDue = Double.parseDouble(productPrice) - Double.parseDouble(discount);
			CustomisedAssert
					.assertTrue(foundation.getText(Order.LBL_BALANCE_DUE).contains(String.valueOf(expectedBalanceDue)));
			CustomisedAssert.assertTrue(foundation.getText(Order.LBL_SUB_TOTAL).contains(productPrice));
			CustomisedAssert.assertTrue(foundation.getText(Order.LBL_DISCOUNT).contains(onScreenDiscount));

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
			editPromotion.expirePromotion(gridName, promotionName);
		}
	}

	@Test(description = "143072-Validate v5 transactions with expired Onscreen promotions")
	public void expiredonScreenRecurrence() {
		try {
			final String CASE_NUM = "143072";

			// Reading test data from database
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstOrgSummaryData = dataBase.getOrgSummaryData(Queries.ORG_SUMMARY, CASE_NUM);

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			final String promotionName = string.getRandomCharacter();
			String displayName = string.getRandomCharacter();
			String promotionType = rstLocationData.get(CNLocation.PROMOTION_TYPE);
			String locationName = propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1,
					FilePath.PROPERTY_CONFIG_FILE);
			String gridName = rstLocationData.get(CNLocation.TAB_NAME);

			List<String> navigationMenu = Arrays
					.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
			List<String> requiredData = Arrays
					.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> language = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));

			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(navigationMenu.get(0));

			// New Promotion
			createPromotions.BundlePromotion(promotionType, promotionName, displayName, requiredData.get(0),
					locationName);
			createPromotions.selectBundlePromotionDetails(requiredData.get(1), requiredData.get(2), requiredData.get(5),
					rstV5DeviceData.get(CNLocation.REQUIRED_DATA));
			createPromotions.selectBundlePromotionTimes(requiredData.get(3), Constants.DELIMITER_SPACE);
			createPromotions.recurringDay();

			// String onScreenDiscount=
			// foundation.getAttributeValue(CreatePromotions.TXT_AMOUNT);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_OK);

			foundation.waitforElement(PromotionList.TXT_SEARCH_PROMONAME, Constants.SHORT_TIME);

			// Deleting the Promotion
			editPromotion.expirePromotion(gridName, promotionName);
			foundation.waitforElement(PromotionList.TXT_SEARCH_PROMONAME, Constants.SHORT_TIME);

			navigationBar.navigateToMenuItem(navigationMenu.get(2));

			// Selecting location
			locationList.selectLocationName(locationName);

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

			navigationBar.navigateToMenuItem(navigationMenu.get(1));
			foundation.waitforClikableElement(OrgSummary.DPD_COUNTRY, Constants.SHORT_TIME);
			dropDown.selectItem(OrgSummary.DPD_COUNTRY, rstOrgSummaryData.get(CNOrgSummary.REQUIRED_DATA),
					Constants.TEXT);
			dropDown.selectItem(OrgSummary.DPD_TAX_SYSTEM, rstOrgSummaryData.get(CNOrgSummary.REQUIRED_DATA),
					Constants.TEXT);
			foundation.click(OrgSummary.BTN_SAVE);
			foundation.waitforClikableElement(OrgList.LBL_ORG_LIST, Constants.SHORT_TIME);

			navigationBar.navigateToMenuItem(navigationMenu.get(2));
			locationSummary.kiosklanguageSetting(locationName, language.get(0), language.get(1));

			foundation.threadWait(Constants.SHORT_TIME);
			// login into Kiosk Device
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));

			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(requiredData.get(2));
			foundation.click(ProductSearch.BTN_PRODUCT);
			CustomisedAssert.assertTrue(foundation.isDisplayed(Order.BTN_CANCEL_ORDER));

			CustomisedAssert.assertTrue(requiredData.get(2).equals(foundation.getText(Order.LBL_PROMOTION_NAME)));

			// verify the display of total section
			String productPrice = foundation.getText(Order.LBL_PRODUCT_PRICE).split(Constants.DOLLAR)[1];
			CustomisedAssert
					.assertTrue(foundation.getText(Order.LBL_BALANCE_DUE).contains(String.valueOf(productPrice)));
			CustomisedAssert.assertTrue(foundation.getText(Order.LBL_SUB_TOTAL).contains(productPrice));

			List<String> orderPageData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.ORDER_PAGE).split(Constants.DELIMITER_TILD));
			List<String> paymentPageData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.PAYMENTS_PAGE).split(Constants.DELIMITER_TILD));
			CustomisedAssert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(0))));
			foundation.objectFocus(order.objText(orderPageData.get(1)));
			order.completeOrder(orderPageData.get(1), paymentPageData.get(0), paymentPageData.get(1));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "143073-Validate v5 transactions with Discount on multiple Line items with Flash Sale for Onscreen Promotion")
	public void recurrenceOnScreenPromotion() {
		final String CASE_NUM = "143073";
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);
		final String promotionName = string.getRandomCharacter();
		String gridName = rstLocationData.get(CNLocation.TAB_NAME);
		List<String> navigationMenu = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		try {

			// Reading test data from database
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstOrgSummaryData = dataBase.getOrgSummaryData(Queries.ORG_SUMMARY, CASE_NUM);

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			String displayName = string.getRandomCharacter();
			String promotionType = rstLocationData.get(CNLocation.PROMOTION_TYPE);
			String locationName = propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1,
					FilePath.PROPERTY_CONFIG_FILE);

			List<String> requiredData = Arrays
					.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> language = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));

			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(navigationMenu.get(0));

			// New Promotion
			createPromotions.BundlePromotion(promotionType, promotionName, displayName, requiredData.get(0),
					locationName);
			createPromotions.selectBundlePromotionDetails(requiredData.get(1), requiredData.get(2), requiredData.get(6),
					rstV5DeviceData.get(CNLocation.REQUIRED_DATA));

			textBox.enterText(CreatePromotions.TXT_ITEM1, requiredData.get(3));
			foundation.threadWait(Constants.ONE_SECOND);
			textBox.enterText(CreatePromotions.TXT_ITEM1, Keys.ENTER);
			foundation.threadWait(Constants.TWO_SECOND);
			dropDown.selectItem(CreatePromotions.DPD_DISCOUNT_TYPE, requiredData.get(7), Constants.TEXT);
			textBox.enterText(CreatePromotions.TXT_DISCOUNT_PERCENTAGE, requiredData.get(5));

			createPromotions.selectBundlePromotionTimes(requiredData.get(4), requiredData.get(8));

			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_OK);

			foundation.waitforElement(PromotionList.TXT_SEARCH_PROMONAME, Constants.SHORT_TIME);
			navigationBar.navigateToMenuItem(navigationMenu.get(2));

			// Selecting location
			locationList.selectLocationName(locationName);

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

			locationSummary.kiosklanguageSetting(locationName, language.get(0), language.get(1));

			foundation.threadWait(Constants.SHORT_TIME);
			// login into Kiosk Device
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));

			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(requiredData.get(2));
			foundation.click(ProductSearch.BTN_PRODUCT);
			CustomisedAssert.assertTrue(foundation.isDisplayed(Order.BTN_CANCEL_ORDER));

			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(requiredData.get(3));
			foundation.click(ProductSearch.BTN_PRODUCT);
			CustomisedAssert.assertTrue(foundation.isDisplayed(Order.BTN_CANCEL_ORDER));

			List<String> productPrice = foundation.getTextofListElement(Order.LBL_MULTI_PRODUCTS);
			List<String> discountList = foundation.getTextofListElement(Order.LBL_ORDER_DISCOUNT);

			Double expectedBalanceDue = Double
					.parseDouble(productPrice.get(0).replaceAll(Constants.REPLACE_DOLLOR, Constants.EMPTY_STRING))
					+ Double.parseDouble(
							productPrice.get(1).replaceAll(Constants.REPLACE_DOLLOR, Constants.EMPTY_STRING))
					- Double.parseDouble(
							discountList.get(2).replaceAll(Constants.REPLACE_DOLLOR, Constants.EMPTY_STRING))
					- Double.parseDouble(
							discountList.get(6).replaceAll(Constants.REPLACE_DOLLOR, Constants.EMPTY_STRING));

			CustomisedAssert
					.assertTrue(foundation.getText(Order.LBL_BALANCE_DUE).contains(String.valueOf(expectedBalanceDue)));
			double subToal = Double
					.parseDouble(productPrice.get(0).replaceAll(Constants.REPLACE_DOLLOR, Constants.EMPTY_STRING))
					+ Double.parseDouble(
							productPrice.get(1).replaceAll(Constants.REPLACE_DOLLOR, Constants.EMPTY_STRING));
			double expectedTotalRoundUp = Math.round(subToal * 100.0) / 100.0;
			CustomisedAssert
					.assertTrue(foundation.getText(Order.LBL_SUB_TOTAL).contains(String.valueOf(expectedTotalRoundUp)));
			CustomisedAssert.assertTrue(foundation.getText(Order.LBL_DISCOUNT).contains(String.valueOf(Double
					.parseDouble(discountList.get(2).replaceAll(Constants.REPLACE_DOLLOR, Constants.EMPTY_STRING))
					+ Double.parseDouble(
							discountList.get(6).replaceAll(Constants.REPLACE_DOLLOR, Constants.EMPTY_STRING)))));

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
			editPromotion.expirePromotion(gridName, promotionName);
		}
	}

	@Test(description = "143074-Validate v5 transactions with Discount Category with Scheduled for Onscreen Promotion")
	public void scheduledOnScreenPromotion() {
		final String CASE_NUM = "143074";
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);
		List<String> navigationMenu = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> requiredData = Arrays
				.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));

		final String promotionName = string.getRandomCharacter();
		String gridName = rstLocationData.get(CNLocation.TAB_NAME);
		try {
			// Reading test data from database
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstOrgSummaryData = dataBase.getOrgSummaryData(Queries.ORG_SUMMARY, CASE_NUM);

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			String displayName = string.getRandomCharacter();
			String promotionType = rstLocationData.get(CNLocation.PROMOTION_TYPE);
			String locationName = propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1,
					FilePath.PROPERTY_CONFIG_FILE);

			List<String> language = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));

			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(navigationMenu.get(0));

			// New Promotion
			createPromotions.BundlePromotion(promotionType, promotionName, displayName, requiredData.get(0),
					locationName);
			createPromotions.selectBundlePromotionDetails(requiredData.get(1), requiredData.get(2), requiredData.get(8),
					rstV5DeviceData.get(CNLocation.REQUIRED_DATA));

			createPromotions.selectBundlePromotionTimes(requiredData.get(4), requiredData.get(5));
			String onScreenDiscount = foundation.getAttributeValue(CreatePromotions.TXT_AMOUNT);

			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_OK);

			foundation.waitforElement(PromotionList.TXT_SEARCH_PROMONAME, Constants.SHORT_TIME);
			navigationBar.navigateToMenuItem(navigationMenu.get(2));

			// Selecting location
			locationList.selectLocationName(locationName);

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

			locationSummary.kiosklanguageSetting(locationName, language.get(0), language.get(1));

			foundation.threadWait(Constants.SHORT_TIME);
			// login into Kiosk Device
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));

			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(requiredData.get(3));
			foundation.click(ProductSearch.BTN_PRODUCT);
			CustomisedAssert.assertTrue(foundation.isDisplayed(Order.BTN_CANCEL_ORDER));

			CustomisedAssert.assertTrue(displayName.equals(foundation.getText(Order.LBL_DISCOUNT_NAME)));
			List<String> discountList = foundation.getTextofListElement(Order.LBL_ORDER_DISCOUNT);
			CustomisedAssert.assertTrue(discountList.get(2).contains(onScreenDiscount));

			// verify the display of total section
			String productPrice = foundation.getText(Order.LBL_PRODUCT_PRICE).split(Constants.DOLLAR)[1];
			String discount = foundation.getText(Order.LBL_DEPOSIT).split(Constants.DOLLAR)[1];
			Double expectedBalanceDue = Double.parseDouble(productPrice) - Double.parseDouble(discount);
			CustomisedAssert
					.assertTrue(foundation.getText(Order.LBL_BALANCE_DUE).contains(String.valueOf(expectedBalanceDue)));
			CustomisedAssert.assertTrue(foundation.getText(Order.LBL_SUB_TOTAL).contains(productPrice));
			CustomisedAssert.assertTrue(foundation.getText(Order.LBL_DISCOUNT).contains(onScreenDiscount));

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
			editPromotion.expirePromotion(gridName, promotionName);
		}
	}

	@Test(description = "143075-Validate v5 transactions with Min Transaction amount for Onscreen Promotion")
	public void minTransactionOnScreenPromotion() {
		final String CASE_NUM = "143075";
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);
		final String promotionName = string.getRandomCharacter();
		String gridName = rstLocationData.get(CNLocation.TAB_NAME);

		List<String> navigationMenu = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		try {

			// Reading test data from database
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstOrgSummaryData = dataBase.getOrgSummaryData(Queries.ORG_SUMMARY, CASE_NUM);

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			String displayName = string.getRandomCharacter();
			String promotionType = rstLocationData.get(CNLocation.PROMOTION_TYPE);
			String locationName = propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1,
					FilePath.PROPERTY_CONFIG_FILE);

			List<String> requiredData = Arrays
					.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> language = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));

			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(navigationMenu.get(0));

			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(navigationMenu.get(0));

			// New Promotion
			createPromotions.BundlePromotion(promotionType, promotionName, displayName, requiredData.get(0),
					locationName);
			createPromotions.selectBundlePromotionDetails(requiredData.get(1), requiredData.get(2), requiredData.get(7),
					rstV5DeviceData.get(CNLocation.REQUIRED_DATA));

			createPromotions.selectBundlePromotionTimes(requiredData.get(4), Constants.DELIMITER_SPACE);
			String onScreenDiscount = foundation.getAttributeValue(CreatePromotions.TXT_AMOUNT);

			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_OK);

			foundation.waitforElement(PromotionList.TXT_SEARCH_PROMONAME, Constants.SHORT_TIME);
			navigationBar.navigateToMenuItem(navigationMenu.get(2));

			// Selecting location
			locationList.selectLocationName(locationName);

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

			locationSummary.kiosklanguageSetting(locationName, language.get(0), language.get(1));

			foundation.threadWait(Constants.SHORT_TIME);
			// login into Kiosk Device
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));

			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(requiredData.get(3));
			foundation.click(ProductSearch.BTN_PRODUCT);
			CustomisedAssert.assertTrue(foundation.isDisplayed(Order.BTN_CANCEL_ORDER));

			CustomisedAssert.assertTrue(displayName.equals(foundation.getText(Order.LBL_DISCOUNT_NAME)));
			List<String> discountList = foundation.getTextofListElement(Order.LBL_ORDER_DISCOUNT);
			CustomisedAssert.assertTrue(discountList.get(2).contains(onScreenDiscount));

			// verify the display of total section
			String productPrice = foundation.getText(Order.LBL_PRODUCT_PRICE).split(Constants.DOLLAR)[1];
			String discount = foundation.getText(Order.LBL_DEPOSIT).split(Constants.DOLLAR)[1];
			Double expectedBalanceDue = Double.parseDouble(productPrice) - Double.parseDouble(discount);
			CustomisedAssert
					.assertTrue(foundation.getText(Order.LBL_BALANCE_DUE).contains(String.valueOf(expectedBalanceDue)));
			CustomisedAssert.assertTrue(foundation.getText(Order.LBL_SUB_TOTAL).contains(productPrice));
			CustomisedAssert.assertTrue(foundation.getText(Order.LBL_DISCOUNT).contains(onScreenDiscount));

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
			editPromotion.expirePromotion(gridName, promotionName);
		}
	}

	@Test(description = "143076-Validate v5 transactions with Less than Min Transaction amount for Onscreen Promotion")
	public void lessThanMinTransactionOnScreenPromotion() {
		final String CASE_NUM = "143076";
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);
		List<String> navigationMenu = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));

		final String promotionName = string.getRandomCharacter();
		String gridName = rstLocationData.get(CNLocation.TAB_NAME);
		try {

			// Reading test data from database
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstOrgSummaryData = dataBase.getOrgSummaryData(Queries.ORG_SUMMARY, CASE_NUM);

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			String displayName = string.getRandomCharacter();
			String promotionType = rstLocationData.get(CNLocation.PROMOTION_TYPE);
			String locationName = propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1,
					FilePath.PROPERTY_CONFIG_FILE);

			List<String> requiredData = Arrays
					.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> language = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));

			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(navigationMenu.get(0));

			// New Promotion
			createPromotions.BundlePromotion(promotionType, promotionName, displayName, requiredData.get(0),
					locationName);
			createPromotions.selectBundlePromotionDetails(requiredData.get(1), requiredData.get(2), requiredData.get(7),
					rstV5DeviceData.get(CNLocation.REQUIRED_DATA));

			createPromotions.selectBundlePromotionTimes(requiredData.get(4), Constants.DELIMITER_SPACE);
			// String onScreenDiscount=
			// foundation.getAttributeValue(CreatePromotions.TXT_AMOUNT);

			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_OK);

			foundation.waitforElement(PromotionList.TXT_SEARCH_PROMONAME, Constants.SHORT_TIME);
			navigationBar.navigateToMenuItem(navigationMenu.get(2));

			// Selecting location
			locationList.selectLocationName(locationName);

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

			locationSummary.kiosklanguageSetting(locationName, language.get(0), language.get(1));

			foundation.threadWait(Constants.SHORT_TIME);
			// login into Kiosk Device
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));

			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(requiredData.get(3));
			foundation.click(ProductSearch.BTN_PRODUCT);
			CustomisedAssert.assertTrue(foundation.isDisplayed(Order.BTN_CANCEL_ORDER));

			CustomisedAssert.assertTrue(requiredData.get(3).equals(foundation.getText(Order.LBL_PROMOTION_NAME)));

			// verify the display of total section
			String productPrice = foundation.getText(Order.LBL_PRODUCT_PRICE).split(Constants.DOLLAR)[1];
			CustomisedAssert.assertTrue(foundation.getText(Order.LBL_SUB_TOTAL).contains(productPrice));
			CustomisedAssert.assertTrue(foundation.getText(Order.LBL_BALANCE_DUE).contains(productPrice));

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
			editPromotion.expirePromotion(gridName, promotionName);
		}
	}

	@Test(description = "143077-Validate v5 transactions without Discount when different device type is selected for Onscreen Promotion")
	public void deviceOnScreenPromotion() {
		final String CASE_NUM = "143077";
		List<String> navigationMenu = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);

		final String promotionName = string.getRandomCharacter();
		String gridName = rstLocationData.get(CNLocation.TAB_NAME);
		try {
			// Reading test data from database
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstOrgSummaryData = dataBase.getOrgSummaryData(Queries.ORG_SUMMARY, CASE_NUM);

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			String displayName = string.getRandomCharacter();
			String promotionType = rstLocationData.get(CNLocation.PROMOTION_TYPE);
			String locationName = propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1,
					FilePath.PROPERTY_CONFIG_FILE);

			List<String> requiredData = Arrays
					.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> language = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));

			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(navigationMenu.get(0));

			// New Promotion
			foundation.waitforElement(PromotionList.BTN_CREATE, Constants.SHORT_TIME);
			foundation.click(PromotionList.BTN_CREATE);
			foundation.isDisplayed(CreatePromotions.LBL_CREATE_PROMOTION);
			createPromotions.newPromotion(promotionType, promotionName, displayName, requiredData.get(0), locationName);
			dropDown.selectItem(CreatePromotions.DPD_DEVICE, requiredData.get(8), Constants.TEXT);
			foundation.click(CreatePromotions.BTN_NEXT);

			createPromotions.selectBundlePromotionDetails(requiredData.get(1), requiredData.get(2), requiredData.get(7),
					rstV5DeviceData.get(CNLocation.REQUIRED_DATA));
			createPromotions.selectBundlePromotionTimes(requiredData.get(4), Constants.DELIMITER_SPACE);

			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_OK);

			foundation.waitforElement(PromotionList.TXT_SEARCH_PROMONAME, Constants.SHORT_TIME);
			navigationBar.navigateToMenuItem(navigationMenu.get(2));

			// Selecting location
			locationList.selectLocationName(locationName);

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

			locationSummary.kiosklanguageSetting(locationName, language.get(0), language.get(1));

			foundation.threadWait(Constants.SHORT_TIME);
			// login into Kiosk Device
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));

			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(requiredData.get(3));
			foundation.click(ProductSearch.BTN_PRODUCT);
			CustomisedAssert.assertTrue(foundation.isDisplayed(Order.BTN_CANCEL_ORDER));

			CustomisedAssert.assertTrue(requiredData.get(3).equals(foundation.getText(Order.LBL_PROMOTION_NAME)));
			String productPrice = foundation.getText(Order.LBL_PRODUCT_PRICE).split(Constants.DOLLAR)[1];

			// remove next 5 lines and uncomment next 2 lines
			String discount = foundation.getText(Order.LBL_DEPOSIT).split(Constants.DOLLAR)[1];
			Double expectedBalanceDue = Double.parseDouble(productPrice) - Double.parseDouble(discount);
			CustomisedAssert
					.assertTrue(foundation.getText(Order.LBL_BALANCE_DUE).contains(String.valueOf(expectedBalanceDue)));
			CustomisedAssert.assertTrue(foundation.getText(Order.LBL_SUB_TOTAL).contains(productPrice));
			CustomisedAssert.assertTrue(foundation.getText(Order.LBL_DISCOUNT).contains(discount));

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
			editPromotion.expirePromotion(gridName, promotionName);
		}
	}

	@Test(description = "143078-Validate v5 transactions with Discount type is Amount for Onscreen Promotion")
	public void discountAmountOnScreenPromotion() {
		final String CASE_NUM = "143078";
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);
		List<String> navigationMenu = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));

		final String promotionName = string.getRandomCharacter();
		String gridName = rstLocationData.get(CNLocation.TAB_NAME);
		try {
			// Reading test data from database
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstOrgSummaryData = dataBase.getOrgSummaryData(Queries.ORG_SUMMARY, CASE_NUM);

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			String displayName = string.getRandomCharacter();
			String promotionType = rstLocationData.get(CNLocation.PROMOTION_TYPE);
			String locationName = propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1,
					FilePath.PROPERTY_CONFIG_FILE);

			List<String> requiredData = Arrays
					.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> language = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));

			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(navigationMenu.get(0));

			// New Promotion
			foundation.waitforElement(PromotionList.BTN_CREATE, Constants.SHORT_TIME);
			foundation.click(PromotionList.BTN_CREATE);
			foundation.isDisplayed(CreatePromotions.LBL_CREATE_PROMOTION);
			createPromotions.newPromotion(promotionType, promotionName, displayName, requiredData.get(0), locationName);
			foundation.click(CreatePromotions.BTN_NEXT);
			createPromotions.selectBundlePromotionDetails(requiredData.get(1), requiredData.get(2), requiredData.get(7),
					rstV5DeviceData.get(CNLocation.REQUIRED_DATA));
			dropDown.selectItem(CreatePromotions.DPD_DISCOUNT_TYPE, requiredData.get(5), Constants.TEXT);
			createPromotions.selectBundlePromotionTimes(requiredData.get(4), Constants.DELIMITER_SPACE);

			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_OK);

			foundation.waitforElement(PromotionList.TXT_SEARCH_PROMONAME, Constants.SHORT_TIME);
			navigationBar.navigateToMenuItem(navigationMenu.get(2));

			// Selecting location
			locationList.selectLocationName(locationName);

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

			locationSummary.kiosklanguageSetting(locationName, language.get(0), language.get(1));

			foundation.threadWait(Constants.SHORT_TIME);
			// login into Kiosk Device
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));

			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(requiredData.get(3));
			foundation.click(ProductSearch.BTN_PRODUCT);
			CustomisedAssert.assertTrue(foundation.isDisplayed(Order.BTN_CANCEL_ORDER));

			CustomisedAssert.assertTrue(requiredData.get(3).equals(foundation.getText(Order.LBL_PROMOTION_NAME)));
			String productPrice = foundation.getText(Order.LBL_PRODUCT_PRICE).split(Constants.DOLLAR)[1];

			// verify the display of total section
			String discount = foundation.getText(Order.LBL_DEPOSIT).split(Constants.DOLLAR)[1];
			Double expectedBalanceDue = Double.parseDouble(productPrice) - Double.parseDouble(discount);
			CustomisedAssert
					.assertTrue(foundation.getText(Order.LBL_BALANCE_DUE).contains(String.valueOf(expectedBalanceDue)));
			CustomisedAssert.assertTrue(foundation.getText(Order.LBL_SUB_TOTAL).contains(productPrice));
			CustomisedAssert.assertTrue(foundation.getText(Order.LBL_DISCOUNT).contains(discount));

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
			editPromotion.expirePromotion(gridName, promotionName);
		}
	}

	@Test(description = "143079-Validate v5 transactions with Discount type is Percentage for Onscreen Promotion")
	public void discountPercentageOnScreenPromotion() {
		final String CASE_NUM = "143079";
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);
		List<String> navigationMenu = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		final String promotionName = string.getRandomCharacter();
		String gridName = rstLocationData.get(CNLocation.TAB_NAME);
		try {
			// Reading test data from database
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstOrgSummaryData = dataBase.getOrgSummaryData(Queries.ORG_SUMMARY, CASE_NUM);

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			String displayName = string.getRandomCharacter();
			String promotionType = rstLocationData.get(CNLocation.PROMOTION_TYPE);
			String locationName = propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1,
					FilePath.PROPERTY_CONFIG_FILE);

			List<String> requiredData = Arrays
					.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> language = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));

			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(navigationMenu.get(0));

			// New Promotion
			foundation.waitforElement(PromotionList.BTN_CREATE, Constants.SHORT_TIME);
			foundation.click(PromotionList.BTN_CREATE);
			foundation.isDisplayed(CreatePromotions.LBL_CREATE_PROMOTION);
			createPromotions.newPromotion(promotionType, promotionName, displayName, requiredData.get(0), locationName);
			foundation.click(CreatePromotions.BTN_NEXT);
			createPromotions.selectBundlePromotionDetails(requiredData.get(1), requiredData.get(2), requiredData.get(7),
					rstV5DeviceData.get(CNLocation.REQUIRED_DATA));
			dropDown.selectItem(CreatePromotions.DPD_DISCOUNT_TYPE, requiredData.get(5), Constants.TEXT);
			createPromotions.selectBundlePromotionTimes(requiredData.get(4), Constants.DELIMITER_SPACE);

			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_OK);

			foundation.waitforElement(PromotionList.TXT_SEARCH_PROMONAME, Constants.SHORT_TIME);
			navigationBar.navigateToMenuItem(navigationMenu.get(2));

			// Selecting location
			locationList.selectLocationName(locationName);

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

			locationSummary.kiosklanguageSetting(locationName, language.get(0), language.get(1));

			foundation.threadWait(Constants.SHORT_TIME);
			// login into Kiosk Device
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));

			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(requiredData.get(3));
			foundation.click(ProductSearch.BTN_PRODUCT);
			CustomisedAssert.assertTrue(foundation.isDisplayed(Order.BTN_CANCEL_ORDER));

			CustomisedAssert.assertTrue(requiredData.get(3).equals(foundation.getText(Order.LBL_PROMOTION_NAME)));
			String productPrice = foundation.getText(Order.LBL_PRODUCT_PRICE).split(Constants.DOLLAR)[1];

			// verify the display of total section
			String discount = foundation.getText(Order.LBL_DEPOSIT).split(Constants.DOLLAR)[1];
			Double expectedBalanceDue = Double.parseDouble(productPrice) - Double.parseDouble(discount);
			CustomisedAssert
					.assertTrue(foundation.getText(Order.LBL_BALANCE_DUE).contains(String.valueOf(expectedBalanceDue)));
			CustomisedAssert.assertTrue(foundation.getText(Order.LBL_SUB_TOTAL).contains(productPrice));
			CustomisedAssert.assertTrue(foundation.getText(Order.LBL_DISCOUNT).contains(discount));

			double discountamount = Double.parseDouble(productPrice) * Double.parseDouble(requiredData.get(7)) / 100;
			CustomisedAssert.assertTrue(discount.equalsIgnoreCase(String.valueOf(discountamount)));

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
			editPromotion.expirePromotion(gridName, promotionName);
		}
	}

	@Test(description = "143080-Validate v5 transactions with Active Tender Discount promotions with Scheduled")
	public void tenderDiscountScheduled() {
		final String CASE_NUM = "143080";

		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);
		List<String> navigationMenu = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		final String promotionName = string.getRandomCharacter();
		String gridName = rstLocationData.get(CNLocation.TAB_NAME);
		try {

			// Reading test data from database
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstOrgSummaryData = dataBase.getOrgSummaryData(Queries.ORG_SUMMARY, CASE_NUM);

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			String displayName = string.getRandomCharacter();
			String promotionType = rstLocationData.get(CNLocation.PROMOTION_TYPE);
			String locationName = propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1,
					FilePath.PROPERTY_CONFIG_FILE);

			List<String> requiredData = Arrays
					.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> language = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));

			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(navigationMenu.get(0));

			// New Promotion
			foundation.waitforElement(PromotionList.BTN_CREATE, Constants.SHORT_TIME);
			foundation.click(PromotionList.BTN_CREATE);
			foundation.isDisplayed(CreatePromotions.LBL_CREATE_PROMOTION);
			createPromotions.newPromotion(promotionType, promotionName, displayName, requiredData.get(0), locationName);
			foundation.click(CreatePromotions.BTN_NEXT);
			createPromotions.tenderDiscountDetails(requiredData.get(1), requiredData.get(2), requiredData.get(3),
					requiredData.get(6), requiredData.get(7));
			createPromotions.selectBundlePromotionTimes(requiredData.get(5), Constants.DELIMITER_SPACE);
			foundation.click(CreatePromotions.TXT_AMOUNT);

			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_OK);

			foundation.waitforElement(PromotionList.TXT_SEARCH_PROMONAME, Constants.SHORT_TIME);
			navigationBar.navigateToMenuItem(navigationMenu.get(2));

			// Selecting location
			locationList.selectLocationName(locationName);

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

			locationSummary.kiosklanguageSetting(locationName, language.get(0), language.get(1));

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
			editPromotion.expirePromotion(gridName, promotionName);
		}
	}

	@Test(description = "143081-Validate v5 transactions with expired promotions")
	public void expiredTenderDiscount() {
		try {
			final String CASE_NUM = "143081";

			// Reading test data from database
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstOrgSummaryData = dataBase.getOrgSummaryData(Queries.ORG_SUMMARY, CASE_NUM);

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			final String promotionName = string.getRandomCharacter();
			String displayName = string.getRandomCharacter();
			String promotionType = rstLocationData.get(CNLocation.PROMOTION_TYPE);
			String locationName = propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1,
					FilePath.PROPERTY_CONFIG_FILE);
			String gridName = rstLocationData.get(CNLocation.TAB_NAME);

			List<String> navigationMenu = Arrays
					.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
			List<String> requiredData = Arrays
					.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> language = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));

			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(navigationMenu.get(0));

			// New Promotion
			foundation.waitforElement(PromotionList.BTN_CREATE, Constants.SHORT_TIME);
			foundation.click(PromotionList.BTN_CREATE);
			foundation.isDisplayed(CreatePromotions.LBL_CREATE_PROMOTION);
			createPromotions.newPromotion(promotionType, promotionName, displayName, requiredData.get(0), locationName);
			foundation.click(CreatePromotions.BTN_NEXT);
			createPromotions.tenderDiscountDetails(requiredData.get(1), requiredData.get(2), requiredData.get(3),
					requiredData.get(6), requiredData.get(7));
			createPromotions.selectBundlePromotionTimes(requiredData.get(5), Constants.DELIMITER_SPACE);
			foundation.click(CreatePromotions.TXT_AMOUNT);

			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_OK);

			navigationBar.navigateToMenuItem(navigationMenu.get(2));

			// Selecting location
			locationList.selectLocationName(locationName);

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

			locationSummary.kiosklanguageSetting(locationName, language.get(0), language.get(1));

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
			editPromotion.expirePromotion(gridName, promotionName);
			browser.close();

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
					.assertFalse(foundation.isDisplayed(order.objText(rstLocationData.get(CNLocation.ACTUAL_DATA))));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "143082-Validate v5 transactions with Tender type as Account with Flash Sale")
	public void tenderDiscountFlashSale() {
		final String CASE_NUM = "143082";
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);
		List<String> navigationMenu = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		final String promotionName = string.getRandomCharacter();
		String gridName = rstLocationData.get(CNLocation.TAB_NAME);
		try {
			// Reading test data from database
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstOrgSummaryData = dataBase.getOrgSummaryData(Queries.ORG_SUMMARY, CASE_NUM);

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			String displayName = string.getRandomCharacter();
			String promotionType = rstLocationData.get(CNLocation.PROMOTION_TYPE);
			String locationName = propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1,
					FilePath.PROPERTY_CONFIG_FILE);

			List<String> requiredData = Arrays
					.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> language = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));

			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(navigationMenu.get(0));

			// New Promotion
			foundation.waitforElement(PromotionList.BTN_CREATE, Constants.SHORT_TIME);
			foundation.click(PromotionList.BTN_CREATE);
			foundation.isDisplayed(CreatePromotions.LBL_CREATE_PROMOTION);
			createPromotions.newPromotion(promotionType, promotionName, displayName, requiredData.get(0), locationName);
			foundation.click(CreatePromotions.BTN_NEXT);
			createPromotions.tenderDiscountDetails(requiredData.get(1), requiredData.get(2), requiredData.get(3),
					requiredData.get(7), requiredData.get(8));
			createPromotions.selectBundlePromotionTimes(requiredData.get(5), requiredData.get(6));
			foundation.click(CreatePromotions.TXT_AMOUNT);

			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_OK);

			foundation.waitforElement(PromotionList.TXT_SEARCH_PROMONAME, Constants.SHORT_TIME);
			navigationBar.navigateToMenuItem(navigationMenu.get(2));

			// Selecting location
			locationList.selectLocationName(locationName);

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

			locationSummary.kiosklanguageSetting(locationName, language.get(0), language.get(1));

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
					.assertFalse(foundation.isDisplayed(order.objText(rstLocationData.get(CNLocation.ACTUAL_DATA))));

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
			editPromotion.expirePromotion(gridName, promotionName);
		}
	}

	@Test(description = "143083-Validate v5 transactions with Tender type as Premium payment with Percentage")
	public void tenderDiscountPremiumPercent() {
		final String CASE_NUM = "143083";
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);
		List<String> navigationMenu = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> requiredData = Arrays
				.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		final String promotionName = string.getRandomCharacter();
		String gridName = rstLocationData.get(CNLocation.TAB_NAME);
		try {
			// Reading test data from database
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstOrgSummaryData = dataBase.getOrgSummaryData(Queries.ORG_SUMMARY, CASE_NUM);

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			String displayName = string.getRandomCharacter();
			String promotionType = rstLocationData.get(CNLocation.PROMOTION_TYPE);
			String locationName = propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1,
					FilePath.PROPERTY_CONFIG_FILE);

			List<String> language = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));

			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(navigationMenu.get(0));

			// New Promotion
			foundation.waitforElement(PromotionList.BTN_CREATE, Constants.SHORT_TIME);
			foundation.click(PromotionList.BTN_CREATE);
			foundation.isDisplayed(CreatePromotions.LBL_CREATE_PROMOTION);
			createPromotions.newPromotion(promotionType, promotionName, displayName, requiredData.get(0), locationName);
			foundation.click(CreatePromotions.BTN_NEXT);
			createPromotions.tenderDiscountDetails(requiredData.get(1), requiredData.get(2), requiredData.get(3),
					requiredData.get(7), requiredData.get(8));
			createPromotions.selectBundlePromotionTimes(requiredData.get(5), Constants.DELIMITER_SPACE);
			createPromotions.recurringDay();
			foundation.click(CreatePromotions.TXT_DISCOUNT_PERCENTAGE);

			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_OK);

			foundation.waitforElement(PromotionList.TXT_SEARCH_PROMONAME, Constants.SHORT_TIME);
			navigationBar.navigateToMenuItem(navigationMenu.get(2));

			// Selecting location
			locationList.selectLocationName(locationName);

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

			locationSummary.kiosklanguageSetting(locationName, language.get(0), language.get(1));

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
			editPromotion.expirePromotion(gridName, promotionName);
		}
	}

	@Test(description = "143084-Validate v5 transactions with Discount type is Amount with Recurrence")
	public void tenderDiscountAmountRecurrence() {
		final String CASE_NUM = "143084";
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);
		List<String> navigationMenu = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> requiredData = Arrays
				.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		final String promotionName = string.getRandomCharacter();
		String gridName = rstLocationData.get(CNLocation.TAB_NAME);
		try {
			// Reading test data from database
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstOrgSummaryData = dataBase.getOrgSummaryData(Queries.ORG_SUMMARY, CASE_NUM);

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			String displayName = string.getRandomCharacter();
			String promotionType = rstLocationData.get(CNLocation.PROMOTION_TYPE);
			String locationName = propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1,
					FilePath.PROPERTY_CONFIG_FILE);

			List<String> language = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));

			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(navigationMenu.get(0));

			// New Promotion
			foundation.waitforElement(PromotionList.BTN_CREATE, Constants.SHORT_TIME);
			foundation.click(PromotionList.BTN_CREATE);
			foundation.isDisplayed(CreatePromotions.LBL_CREATE_PROMOTION);
			createPromotions.newPromotion(promotionType, promotionName, displayName, requiredData.get(0), locationName);
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
			navigationBar.navigateToMenuItem(navigationMenu.get(2));

			// Selecting location
			locationList.selectLocationName(locationName);

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

			locationSummary.kiosklanguageSetting(locationName, language.get(0), language.get(1));

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
			editPromotion.expirePromotion(gridName, promotionName);
		}
	}

	@Test(description = "143085-Validate v5 transactions with Discount type is percentage")
	public void tenderDiscountPercentageDiscount() {
		final String CASE_NUM = "143085";
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);
		List<String> navigationMenu = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> requiredData = Arrays
				.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		final String promotionName = string.getRandomCharacter();
		String gridName = rstLocationData.get(CNLocation.TAB_NAME);
		try {
			// Reading test data from database
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstOrgSummaryData = dataBase.getOrgSummaryData(Queries.ORG_SUMMARY, CASE_NUM);

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			String displayName = string.getRandomCharacter();
			String promotionType = rstLocationData.get(CNLocation.PROMOTION_TYPE);
			String locationName = propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1,
					FilePath.PROPERTY_CONFIG_FILE);

			List<String> language = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));

			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(navigationMenu.get(0));

			// New Promotion
			foundation.waitforElement(PromotionList.BTN_CREATE, Constants.SHORT_TIME);
			foundation.click(PromotionList.BTN_CREATE);
			foundation.isDisplayed(CreatePromotions.LBL_CREATE_PROMOTION);
			createPromotions.newPromotion(promotionType, promotionName, displayName, requiredData.get(0), locationName);
			foundation.click(CreatePromotions.BTN_NEXT);
			createPromotions.tenderDiscountDetails(requiredData.get(1), requiredData.get(2), requiredData.get(3),
					requiredData.get(7), requiredData.get(8));

			createPromotions.selectBundlePromotionTimes(requiredData.get(5), Constants.DELIMITER_SPACE);
			createPromotions.recurringDay();
			foundation.click(CreatePromotions.TXT_DISCOUNT_PERCENTAGE);

			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_OK);

			foundation.waitforElement(PromotionList.TXT_SEARCH_PROMONAME, Constants.SHORT_TIME);
			navigationBar.navigateToMenuItem(navigationMenu.get(2));

			// Selecting location
			locationList.selectLocationName(locationName);

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

			locationSummary.kiosklanguageSetting(locationName, language.get(0), language.get(1));

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
			editPromotion.expirePromotion(gridName, promotionName);
		}
	}

	@Test(description = "143086-Validate v5 transactions with Discount on multiple Line items with Min Transaction amount")
	public void tenderDiscountWithMultipleLineItems() {
		final String CASE_NUM = "143086";
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);
		List<String> navigationMenu = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> requiredData = Arrays
				.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		final String promotionName = string.getRandomCharacter();
		String gridName = rstLocationData.get(CNLocation.TAB_NAME);
		try {
			// Reading test data from database
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstOrgSummaryData = dataBase.getOrgSummaryData(Queries.ORG_SUMMARY, CASE_NUM);

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			String displayName = string.getRandomCharacter();
			String promotionType = rstLocationData.get(CNLocation.PROMOTION_TYPE);
			String locationName = propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1,
					FilePath.PROPERTY_CONFIG_FILE);

			List<String> language = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));

			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(navigationMenu.get(0));

			// New Promotion
			foundation.waitforElement(PromotionList.BTN_CREATE, Constants.SHORT_TIME);
			foundation.click(PromotionList.BTN_CREATE);
			foundation.isDisplayed(CreatePromotions.LBL_CREATE_PROMOTION);
			createPromotions.newPromotion(promotionType, promotionName, displayName, requiredData.get(0), locationName);
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
			navigationBar.navigateToMenuItem(navigationMenu.get(2));

			// Selecting location
			locationList.selectLocationName(locationName);

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

			locationSummary.kiosklanguageSetting(locationName, language.get(0), language.get(1));

			foundation.threadWait(Constants.SHORT_TIME);
			// login into Kiosk Device
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));

			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(requiredData.get(4));
			foundation.click(ProductSearch.BTN_PRODUCT);
			CustomisedAssert.assertTrue(foundation.isDisplayed(Order.BTN_CANCEL_ORDER));
			String productPrice = foundation.getText(Order.LBL_PRODUCT_PRICE).split(Constants.DOLLAR)[1];

			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(requiredData.get(4));
			foundation.click(ProductSearch.BTN_PRODUCT);
			CustomisedAssert.assertTrue(foundation.isDisplayed(Order.BTN_CANCEL_ORDER));

			double price = Double.parseDouble(productPrice);
			price = price + price;
			// verify the display of total section
			CustomisedAssert.assertTrue(foundation.getText(Order.LBL_BALANCE_DUE).contains(String.valueOf(price)));
			CustomisedAssert.assertTrue(foundation.getText(Order.LBL_SUB_TOTAL).contains(String.valueOf(price)));
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
			editPromotion.expirePromotion(gridName, promotionName);
		}
	}

	@Test(description = "143087-Validate v5 transactions with Discount Overall transaction Less than Min Transaction amount")
	public void tenderDiscountLessMinimumTransaction() {
		final String CASE_NUM = "143087";
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);
		List<String> navigationMenu = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> requiredData = Arrays
				.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		final String promotionName = string.getRandomCharacter();
		String gridName = rstLocationData.get(CNLocation.TAB_NAME);
		try {
			// Reading test data from database
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstOrgSummaryData = dataBase.getOrgSummaryData(Queries.ORG_SUMMARY, CASE_NUM);

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			String displayName = string.getRandomCharacter();
			String promotionType = rstLocationData.get(CNLocation.PROMOTION_TYPE);
			String locationName = propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1,
					FilePath.PROPERTY_CONFIG_FILE);

			List<String> language = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));

			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(navigationMenu.get(0));

			// New Promotion
			foundation.waitforElement(PromotionList.BTN_CREATE, Constants.SHORT_TIME);
			foundation.click(PromotionList.BTN_CREATE);
			foundation.isDisplayed(CreatePromotions.LBL_CREATE_PROMOTION);
			createPromotions.newPromotion(promotionType, promotionName, displayName, requiredData.get(0), locationName);
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
			navigationBar.navigateToMenuItem(navigationMenu.get(2));

			// Selecting location
			locationList.selectLocationName(locationName);

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

			locationSummary.kiosklanguageSetting(locationName, language.get(0), language.get(1));

			foundation.threadWait(Constants.SHORT_TIME);
			// login into Kiosk Device
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));

			foundation.waitforClikableElement(LandingPage.IMG_SEARCH_ICON, Constants.SHORT_TIME);
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(requiredData.get(4));
			foundation.click(ProductSearch.BTN_PRODUCT);
			CustomisedAssert.assertTrue(foundation.isDisplayed(Order.BTN_CANCEL_ORDER));
			String productPrice = foundation.getText(Order.LBL_PRODUCT_PRICE).split(Constants.DOLLAR)[1];

			// verify the display of total section
			CustomisedAssert
					.assertTrue(foundation.getText(Order.LBL_BALANCE_DUE).contains(String.valueOf(productPrice)));
			CustomisedAssert.assertTrue(foundation.getText(Order.LBL_SUB_TOTAL).contains(String.valueOf(productPrice)));
			CustomisedAssert
					.assertFalse(foundation.isDisplayed(order.objText(rstLocationData.get(CNLocation.ACTUAL_DATA))));

			foundation.waitforClikableElement(LandingPage.IMG_SEARCH_ICON, Constants.SHORT_TIME);
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(requiredData.get(4));
			foundation.click(ProductSearch.BTN_PRODUCT);
			CustomisedAssert.assertTrue(foundation.isDisplayed(Order.BTN_CANCEL_ORDER));
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
			editPromotion.expirePromotion(gridName, promotionName);
		}
	}

	@Test(description = "143088-Validate v5 transactions without Discount when different device type is selected")
	public void tenderDiscountDifferentDevice() {
		final String CASE_NUM = "143088";
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);
		List<String> navigationMenu = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> requiredData = Arrays
				.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		final String promotionName = string.getRandomCharacter();
		String gridName = rstLocationData.get(CNLocation.TAB_NAME);
		try {
			// Reading test data from database
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstOrgSummaryData = dataBase.getOrgSummaryData(Queries.ORG_SUMMARY, CASE_NUM);

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			String displayName = string.getRandomCharacter();
			String promotionType = rstLocationData.get(CNLocation.PROMOTION_TYPE);
			String locationName = propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1,
					FilePath.PROPERTY_CONFIG_FILE);

			List<String> language = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));

			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(navigationMenu.get(0));

			// New Promotion
			foundation.waitforElement(PromotionList.BTN_CREATE, Constants.SHORT_TIME);
			foundation.click(PromotionList.BTN_CREATE);
			foundation.isDisplayed(CreatePromotions.LBL_CREATE_PROMOTION);
			createPromotions.newPromotion(promotionType, promotionName, displayName, requiredData.get(0), locationName);
			dropDown.selectItem(CreatePromotions.DPD_DEVICE, requiredData.get(9), Constants.TEXT);
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
			navigationBar.navigateToMenuItem(navigationMenu.get(2));

			// Selecting location
			locationList.selectLocationName(locationName);

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

			locationSummary.kiosklanguageSetting(locationName, language.get(0), language.get(1));

			foundation.threadWait(Constants.SHORT_TIME);
			// login into Kiosk Device
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));

			foundation.waitforClikableElement(LandingPage.IMG_SEARCH_ICON, Constants.SHORT_TIME);
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(requiredData.get(4));
			foundation.click(ProductSearch.BTN_PRODUCT);
			CustomisedAssert.assertTrue(foundation.isDisplayed(Order.BTN_CANCEL_ORDER));
			String productPrice = foundation.getText(Order.LBL_PRODUCT_PRICE).split(Constants.DOLLAR)[1];

			// verify the display of total section
			CustomisedAssert
					.assertTrue(foundation.getText(Order.LBL_BALANCE_DUE).contains(String.valueOf(productPrice)));
			CustomisedAssert.assertTrue(foundation.getText(Order.LBL_SUB_TOTAL).contains(String.valueOf(productPrice)));
			CustomisedAssert
					.assertFalse(foundation.isDisplayed(order.objText(rstLocationData.get(CNLocation.ACTUAL_DATA))));

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
			editPromotion.expirePromotion(gridName, promotionName);
		}
	}

	@Test(description = "145703-QAA-227-verify adding bulk funds for closed account and transactions in kiosk")
	public void verifyAddBulkFundsClosedAccount() {
		try {
			final String CASE_NUM = "145703";
// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstConsumerData = dataBase.getConsumerData(Queries.CONSUMER, CASE_NUM);
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);

			List<String> language = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));

			String balance = rstConsumerData.get(CNConsumerSummary.ADJUST_BALANCE);
			String location = rstConsumerData.get(CNConsumerSearch.LOCATION);
			List<String> menuItem = Arrays
					.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
			List<String> status = Arrays
					.asList(rstConsumerData.get(CNConsumerSearch.STATUS).split(Constants.DELIMITER_TILD));
			List<String> searchBy = Arrays
					.asList(rstConsumerData.get(CNConsumerSearch.SEARCH_BY).split(Constants.DELIMITER_TILD));
			String eMail = string.getRandomCharacter() + rstConsumerData.get(CNConsumer.EMAIL);
			String firstName = Constants.AUTOMATION + string.getRandomCharacter();

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItem.get(0));
			foundation.click(ConsumerSearch.BTN_CREATE);
			dropDown.selectItem(ConsumerSummary.DPD_LOCATION, rstConsumerData.get(CNConsumer.LOCATION), Constants.TEXT);
			textBox.enterText(ConsumerSummary.TXT_FIRSTNAME, firstName);
			textBox.enterText(ConsumerSummary.TXT_LASTNAME, Constants.AUTOMATION + string.getRandomCharacter());
			textBox.enterText(ConsumerSummary.TXT_EMAIL, eMail);
			textBox.enterText(ConsumerSummary.TXT_SCANID, String.valueOf(numbers.generateRandomNumber(0, 99999)));
			textBox.enterText(ConsumerSummary.TXT_PIN, rstConsumerData.get(CNConsumer.PIN));
			textBox.enterText(ConsumerSummary.TXT_PHONE, rstConsumerData.get(CNConsumer.PHONE));
			foundation.click(ConsumerSummary.BTN_CREATE);
			foundation.waitforElement(ConsumerSummary.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			foundation.click(ConsumerSummary.BTN_SAVE);

			// Enter fields in Consumer Search Page
			consumerSearch.enterSearchFields(searchBy.get(0), eMail, rstConsumerData.get(CNConsumerSearch.LOCATION),
					status.get(0));
			consumerSearch.objLocation(rstConsumerData.get(CNConsumerSearch.LOCATION));
			foundation.click(ConsumerSearch.BTN_ACTIONS);
			foundation.click(ConsumerSearch.LBL_BULK_PAYOUT);
			foundation.click(ConsumerSearch.BTN_CONFIRM);

			foundation.threadWait(Constants.TWO_SECOND);
			consumerSearch.enterSearchFields(searchBy.get(1), firstName, rstConsumerData.get(CNConsumerSearch.LOCATION),
					status.get(1));
			consumerSearch.objLocation(rstConsumerData.get(CNConsumerSearch.LOCATION));
			foundation.click(ConsumerSearch.BTN_ACTIONS);
			foundation.click(ConsumerSearch.LBL_BULK_ADD_FUNDS);
			foundation.waitforElement(ConsumerSummary.TXT_ADJUST_BALANCE, Constants.SHORT_TIME);
			textBox.enterText(ConsumerSummary.TXT_ADJUST_BALANCE, balance);
			foundation.click(ConsumerSummary.BTN_REASON_SAVE);
			foundation.waitforElement(ConsumerSearch.BTN_OK, Constants.SHORT_TIME);
			foundation.click(ConsumerSearch.BTN_OK);

			// Map<String, String> uiTbl =
			// consumerSearch.getConsumerRecords(rstConsumerData.get(CNConsumerSearch.LOCATION));
			// String uiBal = uiTbl.get(rstConsumerData.get(CNConsumerSearch.COLUMN_NAME));
			// need to update validations after bug is fixed--SOS-25278
			// Assert.assertEquals(uiBal, balance);
			// set language and sync machine
			navigationBar.navigateToMenuItem(menuItem.get(1));
			locationSummary.kiosklanguageSetting(location, language.get(0), language.get(1));

			foundation.threadWait(Constants.SHORT_TIME);
			// login into Kiosk Device
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			foundation.waitforElement(LandingPage.IMG_SEARCH_ICON, Constants.SHORT_TIME);

			// Navigating to product search page
			foundation.waitforElement(LandingPage.IMG_SEARCH_ICON, Constants.SHORT_TIME);
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			foundation.waitforElement(AccountLogin.BTN_CAMELCASE, Constants.SHORT_TIME);

			// searching for product
			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
			foundation.click(ProductSearch.BTN_PRODUCT);

			// verify Order Page
			List<String> orderPageData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.ORDER_PAGE).split(Constants.DELIMITER_TILD));
			CustomisedAssert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(0))));

			foundation.objectFocus(order.objText(orderPageData.get(1)));
			foundation.click(order.objText(orderPageData.get(1)));

			foundation.waitforElement(AccountLogin.BTN_NEXT, Constants.SHORT_TIME);

			textBox.enterKeypadText(eMail);
			foundation.click(AccountLogin.BTN_NEXT);
//			foundation.waitforElement(order.objText(orderPageData.get(1)), Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(1))));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "145706-QAA-227-verify adding negative balance for active account and Close the account.")
	public void verifyNegativeBalClosedAccount() {
		try {
			final String CASE_NUM = "145706";
// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstConsumerData = dataBase.getConsumerData(Queries.CONSUMER, CASE_NUM);
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);

			List<String> balance = Arrays
					.asList(rstConsumerData.get(CNConsumerSummary.ADJUST_BALANCE).split(Constants.DELIMITER_TILD));
			String location = rstConsumerData.get(CNConsumerSearch.LOCATION);
			List<String> menuItem = Arrays
					.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
			List<String> status = Arrays
					.asList(rstConsumerData.get(CNConsumerSearch.STATUS).split(Constants.DELIMITER_TILD));
			List<String> searchBy = Arrays
					.asList(rstConsumerData.get(CNConsumerSearch.SEARCH_BY).split(Constants.DELIMITER_TILD));
			String eMail = string.getRandomCharacter() + rstConsumerData.get(CNConsumer.EMAIL);

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItem.get(0));
			foundation.click(ConsumerSearch.BTN_CREATE);
			dropDown.selectItem(ConsumerSummary.DPD_LOCATION, rstConsumerData.get(CNConsumer.LOCATION), Constants.TEXT);
			textBox.enterText(ConsumerSummary.TXT_FIRSTNAME, Constants.AUTOMATION + string.getRandomCharacter());
			textBox.enterText(ConsumerSummary.TXT_LASTNAME, Constants.AUTOMATION + string.getRandomCharacter());
			textBox.enterText(ConsumerSummary.TXT_EMAIL, eMail);
			textBox.enterText(ConsumerSummary.TXT_SCANID, String.valueOf(numbers.generateRandomNumber(0, 99999)));
			textBox.enterText(ConsumerSummary.TXT_PIN, rstConsumerData.get(CNConsumer.PIN));
			textBox.enterText(ConsumerSummary.TXT_PHONE, rstConsumerData.get(CNConsumer.PHONE));
			foundation.click(ConsumerSummary.BTN_CREATE);
			foundation.waitforElement(ConsumerSummary.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			foundation.click(ConsumerSummary.BTN_SAVE);

			// Enter fields in Consumer Search Page

			foundation.threadWait(Constants.ONE_SECOND);
			consumerSearch.enterSearchFields(searchBy.get(0), eMail, location, status.get(0));
			consumerSearch.objLocation(rstConsumerData.get(CNConsumerSearch.LOCATION));
			foundation.click(ConsumerSearch.BTN_ACTIONS);
			foundation.click(ConsumerSearch.LBL_BULK_ADD_FUNDS);
			foundation.waitforElement(ConsumerSummary.TXT_ADJUST_BALANCE, Constants.SHORT_TIME);
			textBox.enterText(ConsumerSummary.TXT_ADJUST_BALANCE, balance.get(0));
			foundation.click(ConsumerSummary.BTN_REASON_SAVE);
			foundation.waitforElement(ConsumerSearch.BTN_OK, Constants.SHORT_TIME);
			foundation.click(ConsumerSearch.BTN_OK);
			foundation.waitforElementToDisappear(ConsumerSearch.BTN_OK, Constants.SHORT_TIME);
			// Negative Balance
			consumerSearch.enterSearchFields(searchBy.get(0), eMail, location, status.get(0));
			consumerSearch.objLocation(rstConsumerData.get(CNConsumerSearch.LOCATION));
			foundation.click(ConsumerSearch.BTN_ACTIONS);
			foundation.click(ConsumerSearch.LBL_BULK_REMOVE_FUNDS);
			foundation.waitforElement(ConsumerSummary.TXT_ADJUST_BALANCE, Constants.SHORT_TIME);
			textBox.enterText(ConsumerSummary.TXT_ADJUST_BALANCE, balance.get(1));
			foundation.click(ConsumerSummary.BTN_REASON_SAVE);
			foundation.waitforElement(ConsumerSearch.BTN_OK, Constants.SHORT_TIME);
			foundation.click(ConsumerSearch.BTN_OK);

			consumerSearch.enterSearchFields(searchBy.get(1), rstConsumerData.get(CNConsumerSearch.CONSUMER_ID),
					location, status.get(1));
			consumerSearch.objLocation(rstConsumerData.get(CNConsumerSearch.LOCATION));
			foundation.click(ConsumerSearch.BTN_ACTIONS);
			foundation.click(ConsumerSearch.LBL_BULK_PAYOUT);
			foundation.click(ConsumerSearch.BTN_CONFIRM);

			Map<String, String> uiTbl = consumerSearch
					.getConsumerRecords(rstConsumerData.get(CNConsumerSearch.LOCATION));
			String uiBal = uiTbl.get(rstConsumerData.get(CNConsumerSearch.COLUMN_NAME));
			CustomisedAssert.assertEquals(uiBal, balance.get(2));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "166026 - Verify subsidy balance in kiosk device for the consumer part of home campus"
			+ "166030 - Verify the payroll balance when having multi balances in V5 device."
			+ "166031 - Verify the UI for the multi balance in V5 device.")

	public void verifySubsidyBalanceForConsumer() {

		final String CASE_NUM = "166026";

		// Reading test data from DataBase
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);

		List<String> requiredData = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION).split(Constants.DELIMITER_TILD));
		List<String> menus = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		String currentDate = dateAndTime.getDateAndTime(Constants.REGEX_MM_DD_YYYY, Constants.TIME_ZONE_INDIA);
		String requiredDatas = strings.getRandomCharacter();
		List<String> actualData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.ACTUAL_DATA).split(Constants.DELIMITER_TILD));
		List<String> paymentPageData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.PAYMENTS_PAGE).split(Constants.DELIMITER_TILD));
		try {
			// Launch ADM and select Org & Location
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Navigate to Super> Device and Verify Create New Device page
			navigationBar.navigateToMenuItem(menus.get(0));
			CustomisedAssert.assertTrue(foundation.isDisplayed(DeviceList.TXT_DEVICE_LIST));
			foundation.click(DeviceList.BTN_CREATE_NEW);
			CustomisedAssert.assertTrue(foundation.isDisplayed(DeviceCreate.TITLE_KIOSK));
			textBox.enterText(DeviceCreate.TXT_NAME, requiredDatas);
			String text = dropDown.getSelectedItem(DeviceCreate.TYPE);
			CustomisedAssert.assertEquals(text, requiredData.get(7));
			foundation.click(DeviceCreate.BTN_CANCEl);
			CustomisedAssert.assertTrue(foundation.isDisplayed(DeviceList.TXT_DEVICE_LIST));

			// Select Location and Set Consumer Subsidy as Yes with requried details
			navigationBar.navigateToMenuItem(menus.get(1));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.BTN_DEPLOY_DEVICE));
			text = foundation.getText(LocationSummary.DEVICE_NAME);
			CustomisedAssert.assertEquals(text, requiredData.get(8));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_GMA_SUBSIDY));
			String value = dropDown.getSelectedItem(LocationSummary.DPD_GMA_SUBSIDY);
			CustomisedAssert.assertEquals(value, requiredData.get(0));
			if (checkBox.isChkEnabled(LocationSummary.CHK_TOP_OFF_SUBSIDY))
				checkBox.check(LocationSummary.CHK_TOP_OFF_SUBSIDY);
			foundation.click(LocationSummary.START_DATE_PICKER_TOP_OFF);
			locationSummary.verifyTopOffDateAutoLocation1(currentDate);
			foundation.click(LocationSummary.TXT_TOP_OFF_SUBSIDY);
			dropDown.selectItem(LocationSummary.DPD_TOP_OFF_RECURRENCE, requiredData.get(6), Constants.TEXT);
			textBox.enterText(LocationSummary.TXT_TOP_OFF_GROUP_NAME, requiredData.get(2));
			textBox.enterText(LocationSummary.TXT_TOP_OFF_AMOUNT, requiredData.get(4));
			foundation.click(LocationSummary.START_DATE_PICKER_ROLL_OVER);
			locationSummary.verifyRollOverCurrentDate(currentDate);
			dropDown.selectItem(LocationSummary.DPD_ROLL_OVER_RECURRENCE, requiredData.get(6), Constants.TEXT);
			textBox.enterText(LocationSummary.TXT_ROLL_OVER_GROUP_NAME, requiredData.get(3));
			textBox.enterText(LocationSummary.TXT_ROLL_OVER_AMOUNT, requiredData.get(5));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_PAYROLL));
			CustomisedAssert.assertFalse(foundation.isEnabled(LocationSummary.INPUT_PAYROLL));
			value = dropDown.getSelectedItem(LocationSummary.INPUT_PAYROLL);
			CustomisedAssert.assertEquals(value, requiredData.get(0));
			foundation.click(LocationSummary.BTN_SYNC);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);

			// Navigate to Admin>Consumer, verify Subsidy Group and Logout
			navigationBar.navigateToMenuItem(menus.get(2));
			foundation.click(ConsumerSearch.CLEAR_SEARCH);
			textBox.enterText(ConsumerSearch.TXT_SEARCH, requiredData.get(9));
			dropDown.selectItem(ConsumerSearch.DPD_LOCATION, rstLocationListData.get(CNLocationList.LOCATION_NAME),
					Constants.TEXT);
			foundation.click(ConsumerSearch.BTN_GO);
			foundation.threadWait(Constants.ONE_SECOND);
			foundation.click(consumerSearch.objFirstNameCell(consumerSearch.getConsumerFirstName()));
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSummary.LBL_CONSUMER_SUMMARY));
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSummary.TXT_CONSUMER_ACCOUNT));
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSummary.TXT_SUBSIDY_GROUP));
			String balance = foundation.getText(ConsumerSummary.LBL_READ_BALANCE);
			String subsidyName = dropDown.getSelectedItem(ConsumerSummary.DPD_SUBSIDY_GROUP_NAME);
			CustomisedAssert.assertEquals(subsidyName, requiredData.get(10));
			foundation.click(ConsumerSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_RECORD_UPDATE_MSG, Constants.SHORT_TIME);

			// Select location and sync with device
			locationList.syncDevice(menus.get(1), rstLocationListData.get(CNLocationList.LOCATION_NAME));

			// Launch V5 Device
			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LandingPage.IMG_SEARCH_ICON));
			String typeBalance = "$" + requiredData.get(4) + ".00";
			accountLogin.verifyConsumerAccountLogin(rstV5DeviceData.get(CNV5Device.EMAIL_ID),
					rstV5DeviceData.get(CNV5Device.PIN), requiredData.get(9), balance, typeBalance,
					rstV5DeviceData.get(CNV5Device.ACCOUNT_DETAILS));

			// Product Search
			foundation.click(LandingPage.IMG_ORDER_SEARCH_ICON);
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
			foundation.click(ProductSearch.BTN_PRODUCT);
			CustomisedAssert.assertEquals(foundation.getText(Order.TXT_HEADER), actualData.get(0));
			CustomisedAssert.assertEquals(foundation.getText(Order.TXT_PRODUCT), actualData.get(1));

			// Transaction Done using Consumer Account Subsidy Balance
			foundation.objectFocus(order.objText(rstV5DeviceData.get(CNV5Device.ORDER_PAGE)));
			foundation.click(order.objText(rstV5DeviceData.get(CNV5Device.ORDER_PAGE)));
			foundation.waitforElement(Payments.BTN_EMAIL_LOGIN, Constants.SHORT_TIME);
			String balanceDue = foundation.getText(Order.LBL_CHARGE_AMT).split(Constants.DOLLAR)[1];
			double bal = Double.valueOf(requiredData.get(4)) - Double.valueOf(balanceDue);
			typeBalance = "$" + String.valueOf(bal);
			foundation.click(Payments.BTN_EMAIL_LOGIN);
			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.EMAIL_ID));
			foundation.click(AccountLogin.BTN_NEXT);
			foundation.waitforElement(AccountLogin.BTN_PIN_NEXT, Constants.SHORT_TIME);
			textBox.enterPin(rstV5DeviceData.get(CNV5Device.PIN));
			foundation.click(AccountLogin.BTN_PIN_NEXT);
			CustomisedAssert.assertTrue(foundation.isDisplayed(order.objText(paymentPageData.get(0))));
			foundation.waitforElement(LandingPage.IMG_SEARCH_ICON, Constants.SHORT_TIME);

			// Verifying Consumer Subsidy Balance after Transaction
			accountLogin.verifyConsumerAccountLogin(rstV5DeviceData.get(CNV5Device.EMAIL_ID),
					rstV5DeviceData.get(CNV5Device.PIN), requiredData.get(9), balance, typeBalance,
					rstV5DeviceData.get(CNV5Device.ACCOUNT_DETAILS));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			browser.close();
			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menus.get(2));

			// Search Consumer Account
			foundation.click(ConsumerSearch.CLEAR_SEARCH);
			textBox.enterText(ConsumerSearch.TXT_SEARCH, requiredData.get(9));
			dropDown.selectItem(ConsumerSearch.DPD_LOCATION, rstLocationListData.get(CNLocationList.LOCATION_NAME),
					Constants.TEXT);
			foundation.click(ConsumerSearch.BTN_GO);
			foundation.threadWait(Constants.ONE_SECOND);
			foundation.click(consumerSearch.objFirstNameCell(consumerSearch.getConsumerFirstName()));

			// Resetting Subsidy Balance
			foundation.click(ConsumerSummary.BTN_TOP_OFF_ADJUST);
			foundation.waitforElement(ConsumerSummary.LBL_POPUP_ADJUST_BALANCE, Constants.SHORT_TIME);
			textBox.enterText(ConsumerSummary.TXT_ADJUST_BALANCE, requiredData.get(4));
			dropdown.selectItem(ConsumerSummary.DPD_REASON, requiredData.get(11), Constants.TEXT);
			foundation.click(ConsumerSummary.BTN_REASON_SAVE);
			foundation.click(ConsumerSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_RECORD_UPDATE_MSG, Constants.SHORT_TIME);

			// Select location and sync with device
			locationList.syncDevice(menus.get(1), rstLocationListData.get(CNLocationList.LOCATION_NAME));
		}
	}

	@Test(description = "166032 - Verify the payment of product purchase which exceeds Subsidy and Account balance.")

	public void verifyPaymentOfProductPurchase() {

		final String CASE_NUM = "166032";

		// Reading test data from DataBase
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);

		List<String> requiredData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> menus = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		String currentDate = dateAndTime.getDateAndTime(Constants.REGEX_MM_DD_YYYY, Constants.TIME_ZONE_INDIA);
		List<String> actualData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.ACTUAL_DATA).split(Constants.DELIMITER_TILD));
		try {
			// Launch ADM and select Org
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Select Location and Set Consumer Subsidy as Yes with requried details
			navigationBar.navigateToMenuItem(menus.get(0));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_GMA_SUBSIDY));
			String value = dropDown.getSelectedItem(LocationSummary.DPD_GMA_SUBSIDY);
			CustomisedAssert.assertEquals(value, requiredData.get(0));
			if (checkBox.isChkEnabled(LocationSummary.CHK_TOP_OFF_SUBSIDY))
				checkBox.unCheck(LocationSummary.CHK_TOP_OFF_SUBSIDY);
			if (checkBox.isChkEnabled(LocationSummary.CHK_ROLL_OVER_SUBSIDY))
				checkBox.unCheck(LocationSummary.CHK_ROLL_OVER_SUBSIDY);
			foundation.click(LocationSummary.START_DATE_PICKER_TOP_OFF);
			locationSummary.verifyTopOffDateAutoLocation1(currentDate);
			foundation.click(LocationSummary.TXT_TOP_OFF_SUBSIDY);
			dropDown.selectItem(LocationSummary.DPD_TOP_OFF_RECURRENCE, requiredData.get(7), Constants.TEXT);
			textBox.enterText(LocationSummary.TXT_TOP_OFF_GROUP_NAME, requiredData.get(2));
			textBox.enterText(LocationSummary.TXT_TOP_OFF_AMOUNT, requiredData.get(4));
			foundation.click(LocationSummary.START_DATE_PICKER_ROLL_OVER);
			locationSummary.verifyRollOverCurrentDate(currentDate);
			dropDown.selectItem(LocationSummary.DPD_ROLL_OVER_RECURRENCE, requiredData.get(7), Constants.TEXT);
			textBox.enterText(LocationSummary.TXT_ROLL_OVER_GROUP_NAME, requiredData.get(3));
			textBox.enterText(LocationSummary.TXT_ROLL_OVER_AMOUNT, requiredData.get(5));
			foundation.click(LocationSummary.BTN_SYNC);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);

			// Navigate to Admin>Consumer, verify Subsidy Group and Logout
			navigationBar.navigateToMenuItem(menus.get(1));
			foundation.click(ConsumerSearch.CLEAR_SEARCH);
			textBox.enterText(ConsumerSearch.TXT_SEARCH, requiredData.get(8));
			dropDown.selectItem(ConsumerSearch.DPD_LOCATION, rstLocationListData.get(CNLocationList.LOCATION_NAME),
					Constants.TEXT);
			foundation.click(ConsumerSearch.BTN_GO);
			foundation.threadWait(Constants.ONE_SECOND);
			foundation.click(consumerSearch.objFirstNameCell(consumerSearch.getConsumerFirstName()));
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSummary.LBL_CONSUMER_SUMMARY));
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSummary.TXT_CONSUMER_ACCOUNT));
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSummary.TXT_SUBSIDY_GROUP));
			foundation.click(ConsumerSummary.BTN_ADJUST);
			foundation.waitforElement(ConsumerSummary.LBL_POPUP_ADJUST_BALANCE, Constants.SHORT_TIME);
			textBox.enterText(ConsumerSummary.TXT_ADJUST_BALANCE, requiredData.get(6));
			dropdown.selectItem(ConsumerSummary.DPD_REASON, requiredData.get(9), Constants.TEXT);
			foundation.click(ConsumerSummary.BTN_REASON_SAVE);
			foundation.waitforElement(ConsumerSummary.SUCCESS_MESSAGE, Constants.SHORT_TIME);
			dropDown.selectItem(ConsumerSummary.DPD_SUBSIDY_GROUP_NAME, requiredData.get(2), Constants.TEXT);
			foundation.click(ConsumerSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_RECORD_UPDATE_MSG, Constants.SHORT_TIME);

			// Select location and sync with device
			locationList.syncDevice(menus.get(0), rstLocationListData.get(CNLocationList.LOCATION_NAME));

			// Launch V5 Device
			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LandingPage.IMG_SEARCH_ICON));

			// Product Search
			foundation.click(LandingPage.IMG_ORDER_SEARCH_ICON);
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
			foundation.click(ProductSearch.BTN_PRODUCT);
			CustomisedAssert.assertEquals(foundation.getText(Order.TXT_HEADER), actualData.get(0));
			CustomisedAssert.assertEquals(foundation.getText(Order.TXT_PRODUCT), actualData.get(1));

			// Verify product purchase which exceeds Subsidy and Account balance
			foundation.objectFocus(order.objText(rstV5DeviceData.get(CNV5Device.ORDER_PAGE)));
			foundation.click(order.objText(rstV5DeviceData.get(CNV5Device.ORDER_PAGE)));
			foundation.waitforElement(Payments.BTN_EMAIL_LOGIN, Constants.SHORT_TIME);
			foundation.click(Payments.BTN_EMAIL_LOGIN);
			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.EMAIL_ID));
			foundation.click(AccountLogin.BTN_NEXT);
			foundation.waitforElement(AccountLogin.BTN_PIN_NEXT, Constants.SHORT_TIME);
			textBox.enterPin(rstV5DeviceData.get(CNV5Device.PIN));
			foundation.click(AccountLogin.BTN_PIN_NEXT);
			CustomisedAssert.assertTrue(foundation.isDisplayed(Payments.LBL_INSUFFICIENT_FUND));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			browser.close();
			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menus.get(1));

			// Search Consumer Account
			foundation.click(ConsumerSearch.CLEAR_SEARCH);
			textBox.enterText(ConsumerSearch.TXT_SEARCH, requiredData.get(8));
			dropDown.selectItem(ConsumerSearch.DPD_LOCATION, rstLocationListData.get(CNLocationList.LOCATION_NAME),
					Constants.TEXT);
			foundation.click(ConsumerSearch.BTN_GO);
			foundation.threadWait(Constants.ONE_SECOND);
			foundation.click(consumerSearch.objFirstNameCell(consumerSearch.getConsumerFirstName()));

			// Resetting Subsidy Balance
			foundation.click(ConsumerSummary.BTN_ADJUST);
			foundation.waitforElement(ConsumerSummary.LBL_POPUP_ADJUST_BALANCE, Constants.SHORT_TIME);
			textBox.enterText(ConsumerSummary.TXT_ADJUST_BALANCE, requiredData.get(10));
			dropdown.selectItem(ConsumerSummary.DPD_REASON, requiredData.get(9), Constants.TEXT);
			foundation.click(ConsumerSummary.BTN_REASON_SAVE);
			foundation.click(ConsumerSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_RECORD_UPDATE_MSG, Constants.SHORT_TIME);

			// Select location and sync with device
			locationList.syncDevice(menus.get(0), rstLocationListData.get(CNLocationList.LOCATION_NAME));
		}
	}

	@Test(description = "166033 - Verify the payment of product purchase which exceeds Subsidy, Account and PDE balance.")

	public void verifyPaymentOfProductPurchasePDEBalance() {

		final String CASE_NUM = "166033";

		// Reading test data from DataBase
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
		rstDeviceListData = dataBase.getDeviceListData(Queries.DEVICE_LIST, CASE_NUM);

		List<String> requiredData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> menus = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		String currentDate = dateAndTime.getDateAndTime(Constants.REGEX_MM_DD_YYYY, Constants.TIME_ZONE_INDIA);
		List<String> location = Arrays
				.asList(rstLocationListData.get(CNLocationList.LOCATION_NAME).split(Constants.DELIMITER_TILD));
		List<String> actualData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.ACTUAL_DATA).split(Constants.DELIMITER_TILD));
		try {
			// Launch ADM and select Org
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Select Location and Set Consumer Subsidy as Yes with requried details
			navigationBar.navigateToMenuItem(menus.get(0));
			locationList.selectLocationName(location.get(0));
			foundation.objectFocus(LocationSummary.BTN_DEPLOY_DEVICE);

			// Remove Device from AutomationLocation1 Location
			if (foundation.isDisplayed(LocationSummary.TBL_DEPLOYED_DEVICE_LIST)) {
				foundation.click(LocationSummary.TBL_DEPLOYED_DEVICE_LIST);
				foundation.waitforElement(DeviceDashboard.BTN_LIVE_CONNECTION_STATUS, Constants.SHORT_TIME);
				foundation.click(DeviceDashboard.BTN_REMOVE_DEVICE);
				foundation.waitforElement(DeviceDashboard.BTN_YES_REMOVE, Constants.SHORT_TIME);
				foundation.click(DeviceDashboard.BTN_YES_REMOVE);
				foundation.waitforElement(LocationSummary.BTN_DEPLOY_DEVICE, Constants.SHORT_TIME);
			}

			// Deploy Device to AutoLocationConsumerVerified Location
			navigationBar.navigateToMenuItem(menus.get(0));
			locationList.selectLocationName(location.get(1));
			foundation.objectFocus(LocationSummary.BTN_DEPLOY_DEVICE);
			foundation.click(LocationSummary.BTN_DEPLOY_DEVICE);
			foundation.waitforElement(LocationSummary.TXT_FIND_DEVICE, Constants.SHORT_TIME);
			textBox.enterText(LocationSummary.TXT_FIND_DEVICE, rstDeviceListData.get(CNDeviceList.PRODUCT_NAME));
			foundation.click(LocationSummary.TBL_DEVICE_LIST);
			foundation.click(LocationSummary.BTN_ADD_PRODUCT_ADD);
			foundation.waitforElement(LocationSummary.BTN_DEPLOY_DEVICE, Constants.SHORT_TIME);
			foundation.refreshPage();
			foundation.objectFocus(LocationSummary.BTN_LOCATION_SETTINGS);
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);

			// Select GMA Subsidy ON
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_GMA_SUBSIDY));
			String value = dropDown.getSelectedItem(LocationSummary.DPD_GMA_SUBSIDY);
			if (value.equals(requiredData.get(1)))
				dropDown.selectItem(LocationSummary.DPD_GMA_SUBSIDY, requiredData.get(0), Constants.TEXT);
			if (checkBox.isChkEnabled(LocationSummary.CHK_TOP_OFF_SUBSIDY))
				checkBox.unCheck(LocationSummary.CHK_TOP_OFF_SUBSIDY);
			if (checkBox.isChkEnabled(LocationSummary.CHK_ROLL_OVER_SUBSIDY))
				checkBox.unCheck(LocationSummary.CHK_ROLL_OVER_SUBSIDY);
			foundation.click(LocationSummary.START_DATE_PICKER_TOP_OFF);
			locationSummary.verifyTopOffDateAutoLocation2(currentDate);
			foundation.click(LocationSummary.TXT_TOP_OFF_SUBSIDY);
			dropDown.selectItem(LocationSummary.DPD_TOP_OFF_RECURRENCE, requiredData.get(7), Constants.TEXT);
			textBox.enterText(LocationSummary.TXT_TOP_OFF_GROUP_NAME, requiredData.get(2));
			textBox.enterText(LocationSummary.TXT_TOP_OFF_AMOUNT, requiredData.get(4));
			foundation.click(LocationSummary.START_DATE_PICKER_ROLL_OVER);
			locationSummary.verifyRollOverDateCreateLocation(currentDate);
			dropDown.selectItem(LocationSummary.DPD_ROLL_OVER_RECURRENCE, requiredData.get(7), Constants.TEXT);
			textBox.enterText(LocationSummary.TXT_ROLL_OVER_GROUP_NAME, requiredData.get(3));
			textBox.enterText(LocationSummary.TXT_ROLL_OVER_AMOUNT, requiredData.get(5));

			// Select Payroll Deduct ON
			foundation.objectFocus(LocationSummary.TXT_PAYROLL);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_PAYROLL));
			dropDown.selectItem(LocationSummary.DPD_PAYROLL, requiredData.get(0), Constants.TEXT);
			textBox.enterText(LocationSummary.INPUT_PAYROLL_MAIL, rstV5DeviceData.get(CNV5Device.EMAIL_ID));
			foundation.click(LocationSummary.DATE_PICKER_PAY_ROLL);
			locationSummary.verifyTopOffDateAutoLocation1(currentDate);
			foundation.click(LocationSummary.TXT_PAYROLL);
			dropDown.selectItem(LocationSummary.DPD_PAY_CYCLE_RECURRENCE, requiredData.get(7), Constants.TEXT);
			textBox.enterText(LocationSummary.TXT_PAY_CYCLE_GROUP_NAME,
					rstLocationListData.get(CNLocationList.PAY_CYCLE));
			textBox.enterText(LocationSummary.TXT_PAY_ROLL_SPEND_LIMIT, requiredData.get(6));
			foundation.click(LocationSummary.BTN_SYNC);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);

			// Navigate to Admin>Consumer, Select Subsidy Group & Pay cycle Group and Logout
			navigationBar.navigateToMenuItem(menus.get(1));
			foundation.click(ConsumerSearch.CLEAR_SEARCH);
			dropDown.selectItem(ConsumerSearch.DPD_SEARCH_BY,
					rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION), Constants.TEXT);
			textBox.enterText(ConsumerSearch.TXT_SEARCH, rstV5DeviceData.get(CNV5Device.EMAIL_ID));
			dropDown.selectItem(ConsumerSearch.DPD_LOCATION, location.get(1), Constants.TEXT);
			foundation.click(ConsumerSearch.BTN_GO);
			foundation.threadWait(Constants.ONE_SECOND);
			foundation.click(consumerSearch.objFirstNameCell(consumerSearch.getConsumerFirstName()));
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSummary.LBL_CONSUMER_SUMMARY));
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSummary.TXT_CONSUMER_ACCOUNT));
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSummary.TXT_SUBSIDY_GROUP));
			foundation.click(ConsumerSummary.BTN_ADJUST);
			foundation.waitforElement(ConsumerSummary.LBL_POPUP_ADJUST_BALANCE, Constants.SHORT_TIME);
			textBox.enterText(ConsumerSummary.TXT_ADJUST_BALANCE, requiredData.get(6));
			dropdown.selectItem(ConsumerSummary.DPD_REASON, requiredData.get(8), Constants.TEXT);
			foundation.click(ConsumerSummary.BTN_REASON_SAVE);
			foundation.waitforElement(ConsumerSummary.SUCCESS_MESSAGE, Constants.SHORT_TIME);
			dropDown.selectItem(ConsumerSummary.DPD_SUBSIDY_GROUP_NAME, requiredData.get(2), Constants.TEXT);
			value = dropDown.getSelectedItem(ConsumerSummary.DPD_PAY_CYCLE);
			CustomisedAssert.assertEquals(value, rstLocationListData.get(CNLocationList.PAY_CYCLE));
			textBox.enterText(ConsumerSummary.INPUT_PAY_ROLL_ID, requiredData.get(11));
			foundation.click(ConsumerSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_RECORD_UPDATE_MSG, Constants.SHORT_TIME);

			// Select location and sync with device
			locationList.syncDevice(menus.get(0), location.get(1));

			// Launch V5 Device foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LandingPage.IMG_SEARCH_ICON));

			// Product Search
			foundation.click(LandingPage.IMG_ORDER_SEARCH_ICON);
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
			foundation.click(ProductSearch.BTN_PRODUCT);
			CustomisedAssert.assertEquals(foundation.getText(Order.TXT_HEADER), actualData.get(0));
			CustomisedAssert.assertEquals(foundation.getText(Order.TXT_PRODUCT), actualData.get(1));

			// Verify product purchase which exceeds Subsidy and Account balance
			foundation.objectFocus(order.objText(rstV5DeviceData.get(CNV5Device.ORDER_PAGE)));
			foundation.click(order.objText(rstV5DeviceData.get(CNV5Device.ORDER_PAGE)));
			foundation.waitforElement(Payments.BTN_EMAIL_LOGIN, Constants.SHORT_TIME);
			foundation.click(Payments.BTN_EMAIL_LOGIN);
			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.EMAIL_ID));
			foundation.click(AccountLogin.BTN_NEXT);
			foundation.waitforElement(AccountLogin.BTN_PIN_NEXT, Constants.SHORT_TIME);
			textBox.enterPin(rstV5DeviceData.get(CNV5Device.PIN));
			foundation.click(AccountLogin.BTN_PIN_NEXT);
			CustomisedAssert.assertTrue(foundation.isDisplayed(Payments.LBL_INSUFFICIENT_FUND));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {

			browser.close();
			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Select Org and menu
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menus.get(1));

			// Search Consumer Account
			foundation.click(ConsumerSearch.CLEAR_SEARCH);
			dropDown.selectItem(ConsumerSearch.DPD_SEARCH_BY,
					rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION), Constants.TEXT);
			textBox.enterText(ConsumerSearch.TXT_SEARCH, rstV5DeviceData.get(CNV5Device.EMAIL_ID));
			dropDown.selectItem(ConsumerSearch.DPD_LOCATION, location.get(1), Constants.TEXT);
			foundation.click(ConsumerSearch.BTN_GO);
			foundation.threadWait(Constants.ONE_SECOND);
			foundation.click(consumerSearch.objFirstNameCell(consumerSearch.getConsumerFirstName()));

			// Resetting Subsidy Balance
			foundation.click(ConsumerSummary.BTN_ADJUST);
			foundation.waitforElement(ConsumerSummary.LBL_POPUP_ADJUST_BALANCE, Constants.SHORT_TIME);
			textBox.enterText(ConsumerSummary.TXT_ADJUST_BALANCE, requiredData.get(10));
			dropdown.selectItem(ConsumerSummary.DPD_REASON, requiredData.get(8), Constants.TEXT);
			foundation.click(ConsumerSummary.BTN_REASON_SAVE);
			foundation.click(ConsumerSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_RECORD_UPDATE_MSG, Constants.SHORT_TIME);

			// Remove Device from AutoLocationConsumerVerified Location
			locationList.removeDevice(menus.get(0), location.get(1));

			// Deploy Device to AutomationLocation1 Location
			locationList.deployDevice(location.get(0), rstDeviceListData.get(CNDeviceList.PRODUCT_NAME));
		}
	}

	@Test(description = "166034 - Verify the payment of product purchase with only Account balance")

	public void verifyProductPurchaseWithAccountBalance() {

		final String CASE_NUM = "166034";

		// Reading test data from DataBase
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
		rstDeviceListData = dataBase.getDeviceListData(Queries.DEVICE_LIST, CASE_NUM);

		List<String> requiredData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> menus = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		String currentDate = dateAndTime.getDateAndTime(Constants.REGEX_MM_DD_YYYY, Constants.TIME_ZONE_INDIA);
		List<String> location = Arrays
				.asList(rstLocationListData.get(CNLocationList.LOCATION_NAME).split(Constants.DELIMITER_TILD));
		List<String> actualData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.ACTUAL_DATA).split(Constants.DELIMITER_TILD));
		try {
			// Launch ADM and select Org
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Select Location
			navigationBar.navigateToMenuItem(menus.get(0));
			locationList.selectLocationName(location.get(0));
			foundation.objectFocus(LocationSummary.BTN_DEPLOY_DEVICE);

			// Remove Device from AutomationLocation1 Location
			if (foundation.isDisplayed(LocationSummary.TBL_DEPLOYED_DEVICE_LIST)) {
				foundation.click(LocationSummary.TBL_DEPLOYED_DEVICE_LIST);
				foundation.waitforElement(DeviceDashboard.BTN_LIVE_CONNECTION_STATUS, Constants.SHORT_TIME);
				foundation.click(DeviceDashboard.BTN_REMOVE_DEVICE);
				foundation.waitforElement(DeviceDashboard.BTN_YES_REMOVE, Constants.SHORT_TIME);
				foundation.click(DeviceDashboard.BTN_YES_REMOVE);
				foundation.waitforElement(LocationSummary.BTN_DEPLOY_DEVICE, Constants.SHORT_TIME);
			}

			// Deploy Device to AutoLocationConsumerVerified Location
			navigationBar.navigateToMenuItem(menus.get(0));
			locationList.selectLocationName(location.get(1));
			foundation.objectFocus(LocationSummary.BTN_DEPLOY_DEVICE);
			foundation.click(LocationSummary.BTN_DEPLOY_DEVICE);
			foundation.waitforElement(LocationSummary.TXT_FIND_DEVICE, Constants.SHORT_TIME);
			textBox.enterText(LocationSummary.TXT_FIND_DEVICE, rstDeviceListData.get(CNDeviceList.PRODUCT_NAME));
			foundation.click(LocationSummary.TBL_DEVICE_LIST);
			foundation.click(LocationSummary.BTN_ADD_PRODUCT_ADD);
			foundation.waitforElement(LocationSummary.BTN_DEPLOY_DEVICE, Constants.SHORT_TIME);
			foundation.refreshPage();
			foundation.objectFocus(LocationSummary.BTN_LOCATION_SETTINGS);
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);

			// Select GMA Subsidy ON
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_GMA_SUBSIDY));
			String value = dropDown.getSelectedItem(LocationSummary.DPD_GMA_SUBSIDY);
			if (value.equals(requiredData.get(1)))
				dropDown.selectItem(LocationSummary.DPD_GMA_SUBSIDY, requiredData.get(0), Constants.TEXT);
			if (checkBox.isChkEnabled(LocationSummary.CHK_TOP_OFF_SUBSIDY))
				checkBox.unCheck(LocationSummary.CHK_TOP_OFF_SUBSIDY);
			if (checkBox.isChkEnabled(LocationSummary.CHK_ROLL_OVER_SUBSIDY))
				checkBox.unCheck(LocationSummary.CHK_ROLL_OVER_SUBSIDY);
			foundation.click(LocationSummary.START_DATE_PICKER_TOP_OFF);
			locationSummary.verifyTopOffDateAutoLocation2(currentDate);
			foundation.click(LocationSummary.TXT_TOP_OFF_SUBSIDY);
			dropDown.selectItem(LocationSummary.DPD_TOP_OFF_RECURRENCE, requiredData.get(7), Constants.TEXT);
			textBox.enterText(LocationSummary.TXT_TOP_OFF_GROUP_NAME, requiredData.get(2));
			textBox.enterText(LocationSummary.TXT_TOP_OFF_AMOUNT, requiredData.get(4));
			foundation.click(LocationSummary.START_DATE_PICKER_ROLL_OVER);
			locationSummary.verifyRollOverDateCreateLocation(currentDate);
			dropDown.selectItem(LocationSummary.DPD_ROLL_OVER_RECURRENCE, requiredData.get(7), Constants.TEXT);
			textBox.enterText(LocationSummary.TXT_ROLL_OVER_GROUP_NAME, requiredData.get(3));
			textBox.enterText(LocationSummary.TXT_ROLL_OVER_AMOUNT, requiredData.get(5));

			// Select Payroll Deduct ON
			foundation.objectFocus(LocationSummary.TXT_PAYROLL);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_PAYROLL));
			dropDown.selectItem(LocationSummary.DPD_PAYROLL, requiredData.get(0), Constants.TEXT);
			textBox.enterText(LocationSummary.INPUT_PAYROLL_MAIL, rstV5DeviceData.get(CNV5Device.EMAIL_ID));
			foundation.click(LocationSummary.DATE_PICKER_PAY_ROLL);
			locationSummary.verifyTopOffDateAutoLocation1(currentDate);
			foundation.click(LocationSummary.TXT_PAYROLL);
			dropDown.selectItem(LocationSummary.DPD_PAY_CYCLE_RECURRENCE, requiredData.get(7), Constants.TEXT);
			textBox.enterText(LocationSummary.TXT_PAY_CYCLE_GROUP_NAME,
					rstLocationListData.get(CNLocationList.PAY_CYCLE));
			textBox.enterText(LocationSummary.TXT_PAY_ROLL_SPEND_LIMIT, requiredData.get(6));
			foundation.click(LocationSummary.BTN_SYNC);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);

			// Navigate to Admin>Consumer, Select Subsidy Group & Pay cycle Group and Logout
			navigationBar.navigateToMenuItem(menus.get(1));
			foundation.click(ConsumerSearch.CLEAR_SEARCH);
			dropDown.selectItem(ConsumerSearch.DPD_SEARCH_BY,
					rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION), Constants.TEXT);
			textBox.enterText(ConsumerSearch.TXT_SEARCH, rstV5DeviceData.get(CNV5Device.EMAIL_ID));
			dropDown.selectItem(ConsumerSearch.DPD_LOCATION, location.get(1), Constants.TEXT);
			foundation.click(ConsumerSearch.BTN_GO);
			foundation.threadWait(Constants.ONE_SECOND);
			foundation.click(consumerSearch.objFirstNameCell(consumerSearch.getConsumerFirstName()));
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSummary.LBL_CONSUMER_SUMMARY));
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSummary.TXT_CONSUMER_ACCOUNT));
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSummary.TXT_SUBSIDY_GROUP));
			foundation.click(ConsumerSummary.BTN_ADJUST);
			foundation.waitforElement(ConsumerSummary.LBL_POPUP_ADJUST_BALANCE, Constants.SHORT_TIME);
			textBox.enterText(ConsumerSummary.TXT_ADJUST_BALANCE, requiredData.get(10));
			dropdown.selectItem(ConsumerSummary.DPD_REASON, requiredData.get(8), Constants.TEXT);
			foundation.click(ConsumerSummary.BTN_REASON_SAVE);
			foundation.waitforElement(ConsumerSummary.SUCCESS_MESSAGE, Constants.SHORT_TIME);
			dropDown.selectItem(ConsumerSummary.DPD_SUBSIDY_GROUP_NAME, requiredData.get(2), Constants.TEXT);
			value = dropDown.getSelectedItem(ConsumerSummary.DPD_PAY_CYCLE);
			CustomisedAssert.assertEquals(value, rstLocationListData.get(CNLocationList.PAY_CYCLE));
			foundation.click(ConsumerSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_RECORD_UPDATE_MSG, Constants.SHORT_TIME);

			// Select location and sync with device
			locationList.syncDevice(menus.get(0), location.get(1));

			// Launch V5 Device and login to consumer account to check account balance
			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LandingPage.IMG_SEARCH_ICON));
			accountLogin.verifyAccountDetails(rstV5DeviceData.get(CNV5Device.EMAIL_ID),
					rstV5DeviceData.get(CNV5Device.PIN), requiredData.get(10));

			// Verify product purchase Transaction with Account balance until Account
			// Balance gets Zero
			String actualBal = accountLogin.productPurchase(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME), actualData,
					rstV5DeviceData.get(CNV5Device.ORDER_PAGE), rstV5DeviceData.get(CNV5Device.EMAIL_ID),
					rstV5DeviceData.get(CNV5Device.PIN), requiredData.get(10),
					rstV5DeviceData.get(CNV5Device.PAYMENTS_PAGE));

			accountLogin.verifyAccountDetails(rstV5DeviceData.get(CNV5Device.EMAIL_ID),
					rstV5DeviceData.get(CNV5Device.PIN), actualBal);
			actualBal = accountLogin.productPurchase(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME), actualData,
					rstV5DeviceData.get(CNV5Device.ORDER_PAGE), rstV5DeviceData.get(CNV5Device.EMAIL_ID),
					rstV5DeviceData.get(CNV5Device.PIN), actualBal, rstV5DeviceData.get(CNV5Device.PAYMENTS_PAGE));
			CustomisedAssert
					.assertTrue(foundation.isDisplayed(order.objText(rstV5DeviceData.get(CNV5Device.PAYMENTS_PAGE))));
			foundation.waitforElement(LandingPage.IMG_SEARCH_ICON, Constants.SHORT_TIME);
			accountLogin.verifyAccountDetails(rstV5DeviceData.get(CNV5Device.EMAIL_ID),
					rstV5DeviceData.get(CNV5Device.PIN), actualBal);

			// Product Search
			foundation.click(LandingPage.IMG_ORDER_SEARCH_ICON);
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
			foundation.click(ProductSearch.BTN_PRODUCT);
			CustomisedAssert.assertEquals(foundation.getText(Order.TXT_HEADER), actualData.get(0));
			CustomisedAssert.assertEquals(foundation.getText(Order.TXT_PRODUCT), actualData.get(1));

			// Verify product purchase Transaction with Account balance when Zero balance
			foundation.objectFocus(order.objText(rstV5DeviceData.get(CNV5Device.ORDER_PAGE)));
			foundation.click(order.objText(rstV5DeviceData.get(CNV5Device.ORDER_PAGE)));
			foundation.waitforElement(Payments.BTN_EMAIL_LOGIN, Constants.SHORT_TIME);
			foundation.click(Payments.BTN_EMAIL_LOGIN);
			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.EMAIL_ID));
			foundation.click(AccountLogin.BTN_NEXT);
			foundation.waitforElement(AccountLogin.BTN_PIN_NEXT, Constants.SHORT_TIME);
			textBox.enterPin(rstV5DeviceData.get(CNV5Device.PIN));
			foundation.click(AccountLogin.BTN_PIN_NEXT);
			CustomisedAssert.assertTrue(foundation.isDisplayed(Payments.LBL_INSUFFICIENT_FUND));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			browser.close();
			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Select Org and Menu
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menus.get(1));

			// Search Consumer Account
			foundation.click(ConsumerSearch.CLEAR_SEARCH);
			dropDown.selectItem(ConsumerSearch.DPD_SEARCH_BY,
					rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION), Constants.TEXT);
			textBox.enterText(ConsumerSearch.TXT_SEARCH, rstV5DeviceData.get(CNV5Device.EMAIL_ID));
			dropDown.selectItem(ConsumerSearch.DPD_LOCATION, location.get(1), Constants.TEXT);
			foundation.click(ConsumerSearch.BTN_GO);
			foundation.threadWait(Constants.ONE_SECOND);
			foundation.click(consumerSearch.objFirstNameCell(consumerSearch.getConsumerFirstName()));

			// Resetting Subsidy Balance
			foundation.click(ConsumerSummary.BTN_ADJUST);
			foundation.waitforElement(ConsumerSummary.LBL_POPUP_ADJUST_BALANCE, Constants.SHORT_TIME);
			textBox.enterText(ConsumerSummary.TXT_ADJUST_BALANCE, requiredData.get(11));
			dropdown.selectItem(ConsumerSummary.DPD_REASON, requiredData.get(8), Constants.TEXT);
			foundation.click(ConsumerSummary.BTN_REASON_SAVE);
			foundation.click(ConsumerSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_RECORD_UPDATE_MSG, Constants.SHORT_TIME);

			// Remove Device from AutoLocationConsumerVerified Location
			locationList.removeDevice(menus.get(0), location.get(1));

			// Deploy Device to AutomationLocation1 Location
			locationList.deployDevice(location.get(0), rstDeviceListData.get(CNDeviceList.PRODUCT_NAME));
		}
	}

	@Test(description = "166035 - Verify the payment of product purchase with only PDE balance")

	public void verifyProductPurchaseWithPDEBalance() {

		final String CASE_NUM = "166035";

		// Reading test data from DataBase
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
		rstDeviceListData = dataBase.getDeviceListData(Queries.DEVICE_LIST, CASE_NUM);

		List<String> requiredData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> menus = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		String currentDate = dateAndTime.getDateAndTime(Constants.REGEX_MM_DD_YYYY, Constants.TIME_ZONE_INDIA);
		List<String> location = Arrays
				.asList(rstLocationListData.get(CNLocationList.LOCATION_NAME).split(Constants.DELIMITER_TILD));
		List<String> actualData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.ACTUAL_DATA).split(Constants.DELIMITER_TILD));
		try {
			// Launch ADM and select Org
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Select Location
			navigationBar.navigateToMenuItem(menus.get(0));
			locationList.selectLocationName(location.get(0));
			foundation.objectFocus(LocationSummary.BTN_DEPLOY_DEVICE);

			// Remove Device from AutomationLocation1 Location
			if (foundation.isDisplayed(LocationSummary.TBL_DEPLOYED_DEVICE_LIST)) {
				foundation.click(LocationSummary.TBL_DEPLOYED_DEVICE_LIST);
				foundation.waitforElement(DeviceDashboard.BTN_LIVE_CONNECTION_STATUS, Constants.SHORT_TIME);
				foundation.click(DeviceDashboard.BTN_REMOVE_DEVICE);
				foundation.waitforElement(DeviceDashboard.BTN_YES_REMOVE, Constants.SHORT_TIME);
				foundation.click(DeviceDashboard.BTN_YES_REMOVE);
				foundation.waitforElement(LocationSummary.BTN_DEPLOY_DEVICE, Constants.SHORT_TIME);
			}

			// Deploy Device to AutoLocationConsumerVerified Location
			navigationBar.navigateToMenuItem(menus.get(0));
			locationList.selectLocationName(location.get(1));
			foundation.objectFocus(LocationSummary.BTN_DEPLOY_DEVICE);
			foundation.click(LocationSummary.BTN_DEPLOY_DEVICE);
			foundation.waitforElement(LocationSummary.TXT_FIND_DEVICE, Constants.SHORT_TIME);
			textBox.enterText(LocationSummary.TXT_FIND_DEVICE, rstDeviceListData.get(CNDeviceList.PRODUCT_NAME));
			foundation.click(LocationSummary.TBL_DEVICE_LIST);
			foundation.click(LocationSummary.BTN_ADD_PRODUCT_ADD);
			foundation.waitforElement(LocationSummary.BTN_DEPLOY_DEVICE, Constants.SHORT_TIME);
			foundation.refreshPage();
			foundation.objectFocus(LocationSummary.BTN_LOCATION_SETTINGS);
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);

			// Select GMA Subsidy ON
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_GMA_SUBSIDY));
			String value = dropDown.getSelectedItem(LocationSummary.DPD_GMA_SUBSIDY);
			if (value.equals(requiredData.get(1)))
				dropDown.selectItem(LocationSummary.DPD_GMA_SUBSIDY, requiredData.get(0), Constants.TEXT);
			if (checkBox.isChkEnabled(LocationSummary.CHK_TOP_OFF_SUBSIDY))
				checkBox.unCheck(LocationSummary.CHK_TOP_OFF_SUBSIDY);
			if (checkBox.isChkEnabled(LocationSummary.CHK_ROLL_OVER_SUBSIDY))
				checkBox.unCheck(LocationSummary.CHK_ROLL_OVER_SUBSIDY);
			foundation.click(LocationSummary.START_DATE_PICKER_TOP_OFF);
			locationSummary.verifyTopOffDateAutoLocation2(currentDate);
			foundation.click(LocationSummary.TXT_TOP_OFF_SUBSIDY);
			dropDown.selectItem(LocationSummary.DPD_TOP_OFF_RECURRENCE, requiredData.get(7), Constants.TEXT);
			textBox.enterText(LocationSummary.TXT_TOP_OFF_GROUP_NAME, requiredData.get(2));
			textBox.enterText(LocationSummary.TXT_TOP_OFF_AMOUNT, requiredData.get(4));
			foundation.click(LocationSummary.START_DATE_PICKER_ROLL_OVER);
			locationSummary.verifyRollOverDateCreateLocation(currentDate);
			dropDown.selectItem(LocationSummary.DPD_ROLL_OVER_RECURRENCE, requiredData.get(7), Constants.TEXT);
			textBox.enterText(LocationSummary.TXT_ROLL_OVER_GROUP_NAME, requiredData.get(3));
			textBox.enterText(LocationSummary.TXT_ROLL_OVER_AMOUNT, requiredData.get(5));

			// Select Payroll Deduct ON
			foundation.objectFocus(LocationSummary.TXT_PAYROLL);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_PAYROLL));
			dropDown.selectItem(LocationSummary.DPD_PAYROLL, requiredData.get(0), Constants.TEXT);
			textBox.enterText(LocationSummary.INPUT_PAYROLL_MAIL, rstV5DeviceData.get(CNV5Device.EMAIL_ID));
			if (!checkBox.isChecked(LocationSummary.CHK_BOX_PAYROLL_DEDUCT))
				checkBox.check(LocationSummary.CHK_BOX_PAYROLL_DEDUCT);
			if (!checkBox.isChecked(LocationSummary.CHK_BOX_PAYROLL_DEDUCT_STREAM))
				checkBox.check(LocationSummary.CHK_BOX_PAYROLL_DEDUCT_STREAM);
			if (!checkBox.isChecked(LocationSummary.CHK_BOX_PAYROLL_DEDUCT_OFFLINE))
				checkBox.check(LocationSummary.CHK_BOX_PAYROLL_DEDUCT_OFFLINE);
			foundation.click(LocationSummary.DATE_PICKER_PAY_ROLL);
			locationSummary.verifyTopOffDateAutoLocation1(currentDate);
			foundation.click(LocationSummary.TXT_PAYROLL);
			dropDown.selectItem(LocationSummary.DPD_PAY_CYCLE_RECURRENCE, requiredData.get(7), Constants.TEXT);
			textBox.enterText(LocationSummary.TXT_PAY_CYCLE_GROUP_NAME,
					rstLocationListData.get(CNLocationList.PAY_CYCLE));
			textBox.enterText(LocationSummary.TXT_PAY_ROLL_SPEND_LIMIT, requiredData.get(6));
			foundation.click(LocationSummary.BTN_SYNC);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);

			// Navigate to Admin>Consumer, Select Subsidy Group & Pay cycle Group and Logout
			navigationBar.navigateToMenuItem(menus.get(1));
			foundation.click(ConsumerSearch.CLEAR_SEARCH);
			dropDown.selectItem(ConsumerSearch.DPD_SEARCH_BY,
					rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION), Constants.TEXT);
			textBox.enterText(ConsumerSearch.TXT_SEARCH, rstV5DeviceData.get(CNV5Device.EMAIL_ID));
			dropDown.selectItem(ConsumerSearch.DPD_LOCATION, location.get(1), Constants.TEXT);
			foundation.click(ConsumerSearch.BTN_GO);
			foundation.threadWait(Constants.ONE_SECOND);
			foundation.click(consumerSearch.objFirstNameCell(consumerSearch.getConsumerFirstName()));
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSummary.LBL_CONSUMER_SUMMARY));
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSummary.TXT_CONSUMER_ACCOUNT));
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSummary.TXT_SUBSIDY_GROUP));
			foundation.click(ConsumerSummary.BTN_ADJUST);
			foundation.waitforElement(ConsumerSummary.LBL_POPUP_ADJUST_BALANCE, Constants.SHORT_TIME);
			textBox.enterText(ConsumerSummary.TXT_ADJUST_BALANCE, requiredData.get(10));
			dropdown.selectItem(ConsumerSummary.DPD_REASON, requiredData.get(8), Constants.TEXT);
			foundation.click(ConsumerSummary.BTN_REASON_SAVE);
			foundation.waitforElement(ConsumerSummary.SUCCESS_MESSAGE, Constants.SHORT_TIME);
			dropDown.selectItem(ConsumerSummary.DPD_SUBSIDY_GROUP_NAME, requiredData.get(12), Constants.TEXT);
			textBox.enterText(ConsumerSummary.INPUT_PAY_ROLL_ID, requiredData.get(13));
			foundation.click(ConsumerSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_RECORD_UPDATE_MSG, Constants.SHORT_TIME);

			// Select location and sync with device
			locationList.syncDevice(menus.get(0), location.get(1));

			// Launch V5 Device and login to consumer account to check PDE balance
			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LandingPage.IMG_SEARCH_ICON));

			// Verify product purchase Transaction with PDE balance until PDE
			// Balance gets Zero
			String actualBal = accountLogin.productPurchase(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME), actualData,
					rstV5DeviceData.get(CNV5Device.ORDER_PAGE), rstV5DeviceData.get(CNV5Device.EMAIL_ID),
					rstV5DeviceData.get(CNV5Device.PIN), requiredData.get(10),
					rstV5DeviceData.get(CNV5Device.PAYMENTS_PAGE));
			foundation.threadWait(Constants.ONE_SECOND);
			actualBal = accountLogin.productPurchase(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME), actualData,
					rstV5DeviceData.get(CNV5Device.ORDER_PAGE), rstV5DeviceData.get(CNV5Device.EMAIL_ID),
					rstV5DeviceData.get(CNV5Device.PIN), actualBal, rstV5DeviceData.get(CNV5Device.PAYMENTS_PAGE));
			accountLogin.verifyAccountDetails(rstV5DeviceData.get(CNV5Device.EMAIL_ID),
					rstV5DeviceData.get(CNV5Device.PIN), actualBal);

			// Product Search
			foundation.click(LandingPage.IMG_ORDER_SEARCH_ICON);
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
			foundation.click(ProductSearch.BTN_PRODUCT);
			CustomisedAssert.assertEquals(foundation.getText(Order.TXT_HEADER), actualData.get(0));
			CustomisedAssert.assertEquals(foundation.getText(Order.TXT_PRODUCT), actualData.get(1));

			// Verify product purchase Transaction with PDE balance when Zero balance
			foundation.objectFocus(order.objText(rstV5DeviceData.get(CNV5Device.ORDER_PAGE)));
			foundation.click(order.objText(rstV5DeviceData.get(CNV5Device.ORDER_PAGE)));
			foundation.waitforElement(Payments.BTN_EMAIL_LOGIN, Constants.SHORT_TIME);
			foundation.click(Payments.BTN_EMAIL_LOGIN);
			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.EMAIL_ID));
			foundation.click(AccountLogin.BTN_NEXT);
			foundation.waitforElement(AccountLogin.BTN_PIN_NEXT, Constants.SHORT_TIME);
			textBox.enterPin(rstV5DeviceData.get(CNV5Device.PIN));
			foundation.click(AccountLogin.BTN_PIN_NEXT);
			CustomisedAssert.assertTrue(foundation.isDisplayed(Payments.LBL_INSUFFICIENT_FUND));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			browser.close();
			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Select Org and menu
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menus.get(1));

			// Search Consumer Account
			foundation.click(ConsumerSearch.CLEAR_SEARCH);
			dropDown.selectItem(ConsumerSearch.DPD_SEARCH_BY,
					rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION), Constants.TEXT);
			textBox.enterText(ConsumerSearch.TXT_SEARCH, rstV5DeviceData.get(CNV5Device.EMAIL_ID));
			dropDown.selectItem(ConsumerSearch.DPD_LOCATION, location.get(1), Constants.TEXT);
			foundation.click(ConsumerSearch.BTN_GO);
			foundation.threadWait(Constants.ONE_SECOND);
			foundation.click(consumerSearch.objFirstNameCell(consumerSearch.getConsumerFirstName()));

			// Resetting Subsidy Balance
			foundation.click(ConsumerSummary.BTN_ADJUST);
			foundation.waitforElement(ConsumerSummary.LBL_POPUP_ADJUST_BALANCE, Constants.SHORT_TIME);
			textBox.enterText(ConsumerSummary.TXT_ADJUST_BALANCE, requiredData.get(11));
			dropdown.selectItem(ConsumerSummary.DPD_REASON, requiredData.get(8), Constants.TEXT);
			foundation.click(ConsumerSummary.BTN_REASON_SAVE);
			foundation.click(ConsumerSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_RECORD_UPDATE_MSG, Constants.SHORT_TIME);

			// Remove Device from AutoLocationConsumerVerified Location
			locationList.removeDevice(menus.get(0), location.get(1));

			// Deploy Device to AutomationLocation1 Location
			locationList.deployDevice(location.get(0), rstDeviceListData.get(CNDeviceList.PRODUCT_NAME));
		}
	}

	@Test(description = "166036 - Verify the payment of product purchase with only Subsidy balance")

	public void verifyProductPurchaseWithSubsidyBalance() {

		final String CASE_NUM = "166036";

		// Reading test data from DataBase
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
		rstDeviceListData = dataBase.getDeviceListData(Queries.DEVICE_LIST, CASE_NUM);

		List<String> requiredData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> menus = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		String currentDate = dateAndTime.getDateAndTime(Constants.REGEX_MM_DD_YYYY, Constants.TIME_ZONE_INDIA);
		List<String> location = Arrays
				.asList(rstLocationListData.get(CNLocationList.LOCATION_NAME).split(Constants.DELIMITER_TILD));
		List<String> actualData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.ACTUAL_DATA).split(Constants.DELIMITER_TILD));
		try {
			// Launch ADM and select Org
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Select Location
			navigationBar.navigateToMenuItem(menus.get(0));
			locationList.selectLocationName(location.get(0));
			foundation.objectFocus(LocationSummary.BTN_DEPLOY_DEVICE);

			// Remove Device from AutomationLocation1 Location
			if (foundation.isDisplayed(LocationSummary.TBL_DEPLOYED_DEVICE_LIST)) {
				foundation.click(LocationSummary.TBL_DEPLOYED_DEVICE_LIST);
				foundation.waitforElement(DeviceDashboard.BTN_LIVE_CONNECTION_STATUS, Constants.SHORT_TIME);
				foundation.click(DeviceDashboard.BTN_REMOVE_DEVICE);
				foundation.waitforElement(DeviceDashboard.BTN_YES_REMOVE, Constants.SHORT_TIME);
				foundation.click(DeviceDashboard.BTN_YES_REMOVE);
				foundation.waitforElement(LocationSummary.BTN_DEPLOY_DEVICE, Constants.SHORT_TIME);
			}

			// Deploy Device to AutoLocationConsumerVerified Location
			navigationBar.navigateToMenuItem(menus.get(0));
			locationList.selectLocationName(location.get(1));
			foundation.objectFocus(LocationSummary.BTN_DEPLOY_DEVICE);
			foundation.click(LocationSummary.BTN_DEPLOY_DEVICE);
			foundation.waitforElement(LocationSummary.TXT_FIND_DEVICE, Constants.SHORT_TIME);
			textBox.enterText(LocationSummary.TXT_FIND_DEVICE, rstDeviceListData.get(CNDeviceList.PRODUCT_NAME));
			foundation.click(LocationSummary.TBL_DEVICE_LIST);
			foundation.click(LocationSummary.BTN_ADD_PRODUCT_ADD);
			foundation.waitforElement(LocationSummary.BTN_DEPLOY_DEVICE, Constants.SHORT_TIME);
			foundation.refreshPage();
			foundation.objectFocus(LocationSummary.BTN_LOCATION_SETTINGS);
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);

			// Select GMA Subsidy ON
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_GMA_SUBSIDY));
			String value = dropDown.getSelectedItem(LocationSummary.DPD_GMA_SUBSIDY);
			if (value.equals(requiredData.get(1)))
				dropDown.selectItem(LocationSummary.DPD_GMA_SUBSIDY, requiredData.get(0), Constants.TEXT);
			if (checkBox.isChkEnabled(LocationSummary.CHK_TOP_OFF_SUBSIDY))
				checkBox.unCheck(LocationSummary.CHK_TOP_OFF_SUBSIDY);
			if (checkBox.isChkEnabled(LocationSummary.CHK_ROLL_OVER_SUBSIDY))
				checkBox.unCheck(LocationSummary.CHK_ROLL_OVER_SUBSIDY);
			foundation.click(LocationSummary.START_DATE_PICKER_TOP_OFF);
			locationSummary.verifyTopOffDateAutoLocation2(currentDate);
			foundation.click(LocationSummary.TXT_TOP_OFF_SUBSIDY);
			dropDown.selectItem(LocationSummary.DPD_TOP_OFF_RECURRENCE, requiredData.get(7), Constants.TEXT);
			textBox.enterText(LocationSummary.TXT_TOP_OFF_GROUP_NAME, requiredData.get(2));
			textBox.enterText(LocationSummary.TXT_TOP_OFF_AMOUNT, requiredData.get(4));
			foundation.click(LocationSummary.START_DATE_PICKER_ROLL_OVER);
			locationSummary.verifyRollOverDateCreateLocation(currentDate);
			dropDown.selectItem(LocationSummary.DPD_ROLL_OVER_RECURRENCE, requiredData.get(7), Constants.TEXT);
			textBox.enterText(LocationSummary.TXT_ROLL_OVER_GROUP_NAME, requiredData.get(3));
			textBox.enterText(LocationSummary.TXT_ROLL_OVER_AMOUNT, requiredData.get(5));

			// Select Payroll Deduct ON
			foundation.objectFocus(LocationSummary.TXT_PAYROLL);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_PAYROLL));
			dropDown.selectItem(LocationSummary.DPD_PAYROLL, requiredData.get(0), Constants.TEXT);
			textBox.enterText(LocationSummary.INPUT_PAYROLL_MAIL, rstV5DeviceData.get(CNV5Device.EMAIL_ID));
			foundation.click(LocationSummary.DATE_PICKER_PAY_ROLL);
			locationSummary.verifyTopOffDateAutoLocation1(currentDate);
			foundation.click(LocationSummary.TXT_PAYROLL);
			dropDown.selectItem(LocationSummary.DPD_PAY_CYCLE_RECURRENCE, requiredData.get(7), Constants.TEXT);
			textBox.enterText(LocationSummary.TXT_PAY_CYCLE_GROUP_NAME,
					rstLocationListData.get(CNLocationList.PAY_CYCLE));
			textBox.enterText(LocationSummary.TXT_PAY_ROLL_SPEND_LIMIT, requiredData.get(6));
			foundation.click(LocationSummary.BTN_SYNC);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);

			// Navigate to Admin>Consumer, Select Subsidy Group & Pay cycle Group and Logout
			navigationBar.navigateToMenuItem(menus.get(1));
			foundation.click(ConsumerSearch.CLEAR_SEARCH);
			dropDown.selectItem(ConsumerSearch.DPD_SEARCH_BY,
					rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION), Constants.TEXT);
			textBox.enterText(ConsumerSearch.TXT_SEARCH, rstV5DeviceData.get(CNV5Device.EMAIL_ID));
			dropDown.selectItem(ConsumerSearch.DPD_LOCATION, location.get(1), Constants.TEXT);
			foundation.click(ConsumerSearch.BTN_GO);
			foundation.threadWait(Constants.ONE_SECOND);
			foundation.click(consumerSearch.objFirstNameCell(consumerSearch.getConsumerFirstName()));
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSummary.LBL_CONSUMER_SUMMARY));
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSummary.TXT_CONSUMER_ACCOUNT));
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSummary.TXT_SUBSIDY_GROUP));
			foundation.click(ConsumerSummary.BTN_ADJUST);
			foundation.waitforElement(ConsumerSummary.LBL_POPUP_ADJUST_BALANCE, Constants.SHORT_TIME);
			textBox.enterText(ConsumerSummary.TXT_ADJUST_BALANCE, requiredData.get(10));
			dropdown.selectItem(ConsumerSummary.DPD_REASON, requiredData.get(8), Constants.TEXT);
			foundation.click(ConsumerSummary.BTN_REASON_SAVE);
			foundation.waitforElement(ConsumerSummary.SUCCESS_MESSAGE, Constants.SHORT_TIME);
			dropDown.selectItem(ConsumerSummary.DPD_SUBSIDY_GROUP_NAME, requiredData.get(2), Constants.TEXT);
			foundation.click(ConsumerSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_RECORD_UPDATE_MSG, Constants.SHORT_TIME);

			// Select location and sync with device
			locationList.syncDevice(menus.get(0), location.get(1));

			// Launch V5 Device and login to consumer account to check account balance
			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LandingPage.IMG_SEARCH_ICON));
			accountLogin.verifySubsidyDetails(rstV5DeviceData.get(CNV5Device.EMAIL_ID),
					rstV5DeviceData.get(CNV5Device.PIN), requiredData.get(4));

			// Verify product purchase Transaction with Account balance until Account
			// Balance gets Zero
			String actualBal = accountLogin.productPurchase(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME), actualData,
					rstV5DeviceData.get(CNV5Device.ORDER_PAGE), rstV5DeviceData.get(CNV5Device.EMAIL_ID),
					rstV5DeviceData.get(CNV5Device.PIN), requiredData.get(4),
					rstV5DeviceData.get(CNV5Device.PAYMENTS_PAGE));

			accountLogin.verifySubsidyDetails(rstV5DeviceData.get(CNV5Device.EMAIL_ID),
					rstV5DeviceData.get(CNV5Device.PIN), actualBal);
			actualBal = accountLogin.productPurchase(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME), actualData,
					rstV5DeviceData.get(CNV5Device.ORDER_PAGE), rstV5DeviceData.get(CNV5Device.EMAIL_ID),
					rstV5DeviceData.get(CNV5Device.PIN), actualBal, rstV5DeviceData.get(CNV5Device.PAYMENTS_PAGE));
			CustomisedAssert
					.assertTrue(foundation.isDisplayed(order.objText(rstV5DeviceData.get(CNV5Device.PAYMENTS_PAGE))));
			foundation.waitforElement(LandingPage.IMG_SEARCH_ICON, Constants.SHORT_TIME);
			accountLogin.verifySubsidyDetails(rstV5DeviceData.get(CNV5Device.EMAIL_ID),
					rstV5DeviceData.get(CNV5Device.PIN), actualBal);

			// Product Search
			foundation.click(LandingPage.IMG_ORDER_SEARCH_ICON);
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
			foundation.click(ProductSearch.BTN_PRODUCT);
			CustomisedAssert.assertEquals(foundation.getText(Order.TXT_HEADER), actualData.get(0));
			CustomisedAssert.assertEquals(foundation.getText(Order.TXT_PRODUCT), actualData.get(1));

			// Verify product purchase Transaction with Account balance when Zero balance
			foundation.objectFocus(order.objText(rstV5DeviceData.get(CNV5Device.ORDER_PAGE)));
			foundation.click(order.objText(rstV5DeviceData.get(CNV5Device.ORDER_PAGE)));
			foundation.waitforElement(Payments.BTN_EMAIL_LOGIN, Constants.SHORT_TIME);
			foundation.click(Payments.BTN_EMAIL_LOGIN);
			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.EMAIL_ID));
			foundation.click(AccountLogin.BTN_NEXT);
			foundation.waitforElement(AccountLogin.BTN_PIN_NEXT, Constants.SHORT_TIME);
			textBox.enterPin(rstV5DeviceData.get(CNV5Device.PIN));
			foundation.click(AccountLogin.BTN_PIN_NEXT);
			CustomisedAssert.assertTrue(foundation.isDisplayed(Payments.LBL_INSUFFICIENT_FUND));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			browser.close();
			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Select Org and menu
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menus.get(1));

			// Search Consumer Account
			foundation.click(ConsumerSearch.CLEAR_SEARCH);
			dropDown.selectItem(ConsumerSearch.DPD_SEARCH_BY,
					rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION), Constants.TEXT);
			textBox.enterText(ConsumerSearch.TXT_SEARCH, rstV5DeviceData.get(CNV5Device.EMAIL_ID));
			dropDown.selectItem(ConsumerSearch.DPD_LOCATION, location.get(1), Constants.TEXT);
			foundation.click(ConsumerSearch.BTN_GO);
			foundation.threadWait(Constants.ONE_SECOND);
			foundation.click(consumerSearch.objFirstNameCell(consumerSearch.getConsumerFirstName()));

			// Resetting Subsidy Balance and consumer balance
			foundation.click(ConsumerSummary.BTN_ADJUST);
			foundation.waitforElement(ConsumerSummary.LBL_POPUP_ADJUST_BALANCE, Constants.SHORT_TIME);
			textBox.enterText(ConsumerSummary.TXT_ADJUST_BALANCE, requiredData.get(11));
			dropdown.selectItem(ConsumerSummary.DPD_REASON, requiredData.get(8), Constants.TEXT);
			foundation.click(ConsumerSummary.BTN_REASON_SAVE);
			foundation.click(ConsumerSummary.BTN_TOP_OFF_ADJUST);
			foundation.waitforElement(ConsumerSummary.LBL_POPUP_ADJUST_BALANCE, Constants.SHORT_TIME);
			textBox.enterText(ConsumerSummary.TXT_ADJUST_BALANCE, requiredData.get(4));
			dropdown.selectItem(ConsumerSummary.DPD_REASON, requiredData.get(8), Constants.TEXT);
			foundation.click(ConsumerSummary.BTN_REASON_SAVE);
			foundation.click(ConsumerSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_RECORD_UPDATE_MSG, Constants.SHORT_TIME);

			// Remove Device from AutoLocationConsumerVerified Location
			locationList.removeDevice(menus.get(0), location.get(1));

			// Deploy Device to AutomationLocation1 Location
			locationList.deployDevice(location.get(0), rstDeviceListData.get(CNDeviceList.PRODUCT_NAME));
		}
	}

	@Test(description = "166038 - Verify the payment of product purchase with Subsidy balance disabled")

	public void verifyProductPurchaseWithSubsidyBalanceDisabled() {

		final String CASE_NUM = "166038";

		// Reading test data from DataBase
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
		rstDeviceListData = dataBase.getDeviceListData(Queries.DEVICE_LIST, CASE_NUM);

		List<String> requiredData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> menus = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		String currentDate = dateAndTime.getDateAndTime(Constants.REGEX_MM_DD_YYYY, Constants.TIME_ZONE_INDIA);
		List<String> location = Arrays
				.asList(rstLocationListData.get(CNLocationList.LOCATION_NAME).split(Constants.DELIMITER_TILD));
		List<String> actualData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.ACTUAL_DATA).split(Constants.DELIMITER_TILD));
		try {
			// Launch ADM and select Org
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Select Location
			navigationBar.navigateToMenuItem(menus.get(0));
			locationList.selectLocationName(location.get(0));
			foundation.objectFocus(LocationSummary.BTN_DEPLOY_DEVICE);

			// Remove Device from AutomationLocation1 Location
			if (foundation.isDisplayed(LocationSummary.TBL_DEPLOYED_DEVICE_LIST)) {
				foundation.click(LocationSummary.TBL_DEPLOYED_DEVICE_LIST);
				foundation.waitforElement(DeviceDashboard.BTN_LIVE_CONNECTION_STATUS, Constants.SHORT_TIME);
				foundation.click(DeviceDashboard.BTN_REMOVE_DEVICE);
				foundation.waitforElement(DeviceDashboard.BTN_YES_REMOVE, Constants.SHORT_TIME);
				foundation.click(DeviceDashboard.BTN_YES_REMOVE);
				foundation.waitforElement(LocationSummary.BTN_DEPLOY_DEVICE, Constants.SHORT_TIME);
			}

			// Deploy Device to AutoLocationConsumerVerified Location
			navigationBar.navigateToMenuItem(menus.get(0));
			locationList.selectLocationName(location.get(1));
			foundation.objectFocus(LocationSummary.BTN_DEPLOY_DEVICE);
			foundation.click(LocationSummary.BTN_DEPLOY_DEVICE);
			foundation.waitforElement(LocationSummary.TXT_FIND_DEVICE, Constants.SHORT_TIME);
			textBox.enterText(LocationSummary.TXT_FIND_DEVICE, rstDeviceListData.get(CNDeviceList.PRODUCT_NAME));
			foundation.click(LocationSummary.TBL_DEVICE_LIST);
			foundation.click(LocationSummary.BTN_ADD_PRODUCT_ADD);
			foundation.waitforElement(LocationSummary.BTN_DEPLOY_DEVICE, Constants.SHORT_TIME);
			foundation.refreshPage();
			foundation.objectFocus(LocationSummary.BTN_LOCATION_SETTINGS);
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);

			// Select GMA Subsidy Off
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_GMA_SUBSIDY));
			String value = dropDown.getSelectedItem(LocationSummary.DPD_GMA_SUBSIDY);
			if (value.equals(requiredData.get(0)))
				dropDown.selectItem(LocationSummary.DPD_GMA_SUBSIDY, requiredData.get(1), Constants.TEXT);

			// Select Payroll Deduct ON
			foundation.objectFocus(LocationSummary.TXT_PAYROLL);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_PAYROLL));
			dropDown.selectItem(LocationSummary.DPD_PAYROLL, requiredData.get(0), Constants.TEXT);
			textBox.enterText(LocationSummary.INPUT_PAYROLL_MAIL, rstV5DeviceData.get(CNV5Device.EMAIL_ID));
			foundation.click(LocationSummary.DATE_PICKER_PAY_ROLL);
			locationSummary.verifyTopOffDateAutoLocation1(currentDate);
			foundation.click(LocationSummary.TXT_PAYROLL);
			dropDown.selectItem(LocationSummary.DPD_PAY_CYCLE_RECURRENCE, requiredData.get(3), Constants.TEXT);
			textBox.enterText(LocationSummary.TXT_PAY_CYCLE_GROUP_NAME,
					rstLocationListData.get(CNLocationList.PAY_CYCLE));
			textBox.enterText(LocationSummary.TXT_PAY_ROLL_SPEND_LIMIT, requiredData.get(2));
			foundation.click(LocationSummary.BTN_SYNC);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);

			// Navigate to Admin>Consumer, Check Subsidy Group & Select Pay cycle Group and
			// Logout
			navigationBar.navigateToMenuItem(menus.get(1));
			foundation.click(ConsumerSearch.CLEAR_SEARCH);
			dropDown.selectItem(ConsumerSearch.DPD_SEARCH_BY,
					rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION), Constants.TEXT);
			textBox.enterText(ConsumerSearch.TXT_SEARCH, rstV5DeviceData.get(CNV5Device.EMAIL_ID));
			dropDown.selectItem(ConsumerSearch.DPD_LOCATION, location.get(1), Constants.TEXT);
			foundation.click(ConsumerSearch.BTN_GO);
			foundation.threadWait(Constants.ONE_SECOND);
			foundation.click(consumerSearch.objFirstNameCell(consumerSearch.getConsumerFirstName()));
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSummary.LBL_CONSUMER_SUMMARY));
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSummary.TXT_CONSUMER_ACCOUNT));
			foundation.click(ConsumerSummary.BTN_ADJUST);
			foundation.waitforElement(ConsumerSummary.LBL_POPUP_ADJUST_BALANCE, Constants.SHORT_TIME);
			textBox.enterText(ConsumerSummary.TXT_ADJUST_BALANCE, requiredData.get(6));
			dropdown.selectItem(ConsumerSummary.DPD_REASON, requiredData.get(4), Constants.TEXT);
			foundation.click(ConsumerSummary.BTN_REASON_SAVE);
			foundation.waitforElement(ConsumerSummary.SUCCESS_MESSAGE, Constants.SHORT_TIME);
			value = dropDown.getSelectedItem(ConsumerSummary.DPD_PAY_CYCLE);
			CustomisedAssert.assertEquals(value, rstLocationListData.get(CNLocationList.PAY_CYCLE));
			foundation.click(ConsumerSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_RECORD_UPDATE_MSG, Constants.SHORT_TIME);

			// Select location and sync with device
			locationList.syncDevice(menus.get(0), location.get(1));

			// Launch V5 Device and login to consumer account to check account balance
			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LandingPage.IMG_SEARCH_ICON));

			// Verify product purchase Transaction with Account balance
			String actualBal = accountLogin.productPurchase(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME), actualData,
					rstV5DeviceData.get(CNV5Device.ORDER_PAGE), rstV5DeviceData.get(CNV5Device.EMAIL_ID),
					rstV5DeviceData.get(CNV5Device.PIN), requiredData.get(6),
					rstV5DeviceData.get(CNV5Device.PAYMENTS_PAGE));
			actualBal = actualBal + "0";
			foundation.click(LandingPage.BTN_LOGIN);
			foundation.click(AccountLogin.BTN_EMAIL_LOGIN);
			accountLogin.login(rstV5DeviceData.get(CNV5Device.EMAIL_ID), rstV5DeviceData.get(CNV5Device.PIN));
			CustomisedAssert.assertTrue(foundation.isDisplayed(AccountLogin.LBL_ACCOUNT_1));
			String balance = foundation.getText(AccountLogin.LBL_BALANCE_1);
			CustomisedAssert.assertEquals(balance, actualBal);
			foundation.click(AccountLogin.BTN_PROFILE_CLOSE);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LandingPage.IMG_SEARCH_ICON));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			browser.close();
			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Select Org and menu
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menus.get(1));

			// Search Consumer Account
			foundation.click(ConsumerSearch.CLEAR_SEARCH);
			dropDown.selectItem(ConsumerSearch.DPD_SEARCH_BY,
					rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION), Constants.TEXT);
			textBox.enterText(ConsumerSearch.TXT_SEARCH, rstV5DeviceData.get(CNV5Device.EMAIL_ID));
			dropDown.selectItem(ConsumerSearch.DPD_LOCATION, location.get(1), Constants.TEXT);
			foundation.click(ConsumerSearch.BTN_GO);
			foundation.threadWait(Constants.ONE_SECOND);
			foundation.click(consumerSearch.objFirstNameCell(consumerSearch.getConsumerFirstName()));

			// Resetting consumer balance
			foundation.click(ConsumerSummary.BTN_ADJUST);
			foundation.waitforElement(ConsumerSummary.LBL_POPUP_ADJUST_BALANCE, Constants.SHORT_TIME);
			textBox.enterText(ConsumerSummary.TXT_ADJUST_BALANCE, requiredData.get(7));
			dropdown.selectItem(ConsumerSummary.DPD_REASON, requiredData.get(4), Constants.TEXT);
			foundation.click(ConsumerSummary.BTN_REASON_SAVE);
			foundation.click(ConsumerSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_RECORD_UPDATE_MSG, Constants.SHORT_TIME);

			// Resetting GMA Subsidy ON
			navigationBar.navigateToMenuItem(menus.get(0));
			locationList.selectLocationName(location.get(1));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_GMA_SUBSIDY));
			dropDown.selectItem(LocationSummary.DPD_GMA_SUBSIDY, requiredData.get(0), Constants.TEXT);
			textBox.enterText(LocationSummary.TXT_TOP_OFF_GROUP_NAME, "testTopOff");
			textBox.enterText(LocationSummary.TXT_ROLL_OVER_GROUP_NAME, "testRollOver");
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);

			// Remove Device from AutoLocationConsumerVerified Location
			locationList.removeDevice(menus.get(0), location.get(1));

			// Deploy Device to AutomationLocation1 Location
			locationList.deployDevice(location.get(0), rstDeviceListData.get(CNDeviceList.PRODUCT_NAME));
		}
	}

	@Test(description = "166039 - Verify the payment of product purchase with Subsidy balance, Account and PDE")

	public void verifyPaymentOfProductPurchaseWithAllBalance() {

		final String CASE_NUM = "166039";

		// Reading test data from DataBase
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
		rstDeviceListData = dataBase.getDeviceListData(Queries.DEVICE_LIST, CASE_NUM);

		List<String> requiredData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> menus = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		String currentDate = dateAndTime.getDateAndTime(Constants.REGEX_MM_DD_YYYY, Constants.TIME_ZONE_INDIA);
		List<String> location = Arrays
				.asList(rstLocationListData.get(CNLocationList.LOCATION_NAME).split(Constants.DELIMITER_TILD));
		List<String> actualData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.ACTUAL_DATA).split(Constants.DELIMITER_TILD));
		List<String> productName = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME).split(Constants.DELIMITER_TILD));
		try {
			// Launch ADM and select Org
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Select Location and Set Consumer Subsidy as Yes with requried details
			navigationBar.navigateToMenuItem(menus.get(0));
			locationList.selectLocationName(location.get(0));
			foundation.objectFocus(LocationSummary.BTN_DEPLOY_DEVICE);

			// Remove Device from AutomationLocation1 Location
			if (foundation.isDisplayed(LocationSummary.TBL_DEPLOYED_DEVICE_LIST)) {
				foundation.click(LocationSummary.TBL_DEPLOYED_DEVICE_LIST);
				foundation.waitforElement(DeviceDashboard.BTN_LIVE_CONNECTION_STATUS, Constants.SHORT_TIME);
				foundation.click(DeviceDashboard.BTN_REMOVE_DEVICE);
				foundation.waitforElement(DeviceDashboard.BTN_YES_REMOVE, Constants.SHORT_TIME);
				foundation.click(DeviceDashboard.BTN_YES_REMOVE);
				foundation.waitforElement(LocationSummary.BTN_DEPLOY_DEVICE, Constants.SHORT_TIME);
			}

			// Deploy Device to AutoLocationConsumerVerified Location
			navigationBar.navigateToMenuItem(menus.get(0));
			locationList.selectLocationName(location.get(1));
			foundation.objectFocus(LocationSummary.BTN_DEPLOY_DEVICE);
			foundation.click(LocationSummary.BTN_DEPLOY_DEVICE);
			foundation.waitforElement(LocationSummary.TXT_FIND_DEVICE, Constants.SHORT_TIME);
			textBox.enterText(LocationSummary.TXT_FIND_DEVICE, rstDeviceListData.get(CNDeviceList.PRODUCT_NAME));
			foundation.click(LocationSummary.TBL_DEVICE_LIST);
			foundation.click(LocationSummary.BTN_ADD_PRODUCT_ADD);
			foundation.waitforElement(LocationSummary.BTN_DEPLOY_DEVICE, Constants.SHORT_TIME);
			foundation.refreshPage();
			foundation.objectFocus(LocationSummary.BTN_LOCATION_SETTINGS);
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);

			// Select GMA Subsidy ON
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_GMA_SUBSIDY));
			String value = dropDown.getSelectedItem(LocationSummary.DPD_GMA_SUBSIDY);
			if (value.equals(requiredData.get(1)))
				dropDown.selectItem(LocationSummary.DPD_GMA_SUBSIDY, requiredData.get(0), Constants.TEXT);
			if (checkBox.isChkEnabled(LocationSummary.CHK_TOP_OFF_SUBSIDY))
				checkBox.unCheck(LocationSummary.CHK_TOP_OFF_SUBSIDY);
			if (checkBox.isChkEnabled(LocationSummary.CHK_ROLL_OVER_SUBSIDY))
				checkBox.unCheck(LocationSummary.CHK_ROLL_OVER_SUBSIDY);
			foundation.click(LocationSummary.START_DATE_PICKER_TOP_OFF);
			locationSummary.verifyTopOffDateAutoLocation2(currentDate);
			foundation.click(LocationSummary.TXT_TOP_OFF_SUBSIDY);
			dropDown.selectItem(LocationSummary.DPD_TOP_OFF_RECURRENCE, requiredData.get(7), Constants.TEXT);
			textBox.enterText(LocationSummary.TXT_TOP_OFF_GROUP_NAME, requiredData.get(2));
			textBox.enterText(LocationSummary.TXT_TOP_OFF_AMOUNT, requiredData.get(4));
			foundation.click(LocationSummary.START_DATE_PICKER_ROLL_OVER);
			locationSummary.verifyRollOverDateCreateLocation(currentDate);
			dropDown.selectItem(LocationSummary.DPD_ROLL_OVER_RECURRENCE, requiredData.get(7), Constants.TEXT);
			textBox.enterText(LocationSummary.TXT_ROLL_OVER_GROUP_NAME, requiredData.get(3));
			textBox.enterText(LocationSummary.TXT_ROLL_OVER_AMOUNT, requiredData.get(4));

			// Select Payroll Deduct ON
			foundation.objectFocus(LocationSummary.TXT_PAYROLL);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_PAYROLL));
			dropDown.selectItem(LocationSummary.DPD_PAYROLL, requiredData.get(0), Constants.TEXT);
			textBox.enterText(LocationSummary.INPUT_PAYROLL_MAIL, rstV5DeviceData.get(CNV5Device.EMAIL_ID));
			foundation.click(LocationSummary.DATE_PICKER_PAY_ROLL);
			locationSummary.verifyTopOffDateAutoLocation1(currentDate);
			foundation.click(LocationSummary.TXT_PAYROLL);
			dropDown.selectItem(LocationSummary.DPD_PAY_CYCLE_RECURRENCE, requiredData.get(7), Constants.TEXT);
			textBox.enterText(LocationSummary.TXT_PAY_CYCLE_GROUP_NAME,
					rstLocationListData.get(CNLocationList.PAY_CYCLE));
			textBox.enterText(LocationSummary.TXT_PAY_ROLL_SPEND_LIMIT, requiredData.get(6));
			foundation.click(LocationSummary.BTN_SYNC);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);

			// Navigate to Admin>Consumer, Select Subsidy Group & Pay cycle Group and Logout
			navigationBar.navigateToMenuItem(menus.get(1));
			foundation.click(ConsumerSearch.CLEAR_SEARCH);
			dropDown.selectItem(ConsumerSearch.DPD_SEARCH_BY,
					rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION), Constants.TEXT);
			textBox.enterText(ConsumerSearch.TXT_SEARCH, rstV5DeviceData.get(CNV5Device.EMAIL_ID));
			dropDown.selectItem(ConsumerSearch.DPD_LOCATION, location.get(1), Constants.TEXT);
			foundation.click(ConsumerSearch.BTN_GO);
			foundation.threadWait(Constants.ONE_SECOND);
			foundation.click(consumerSearch.objFirstNameCell(consumerSearch.getConsumerFirstName()));
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSummary.LBL_CONSUMER_SUMMARY));
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSummary.TXT_CONSUMER_ACCOUNT));
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSummary.TXT_SUBSIDY_GROUP));
			foundation.click(ConsumerSummary.BTN_ADJUST);
			foundation.waitforElement(ConsumerSummary.LBL_POPUP_ADJUST_BALANCE, Constants.SHORT_TIME);
			textBox.enterText(ConsumerSummary.TXT_ADJUST_BALANCE, requiredData.get(5));
			dropdown.selectItem(ConsumerSummary.DPD_REASON, requiredData.get(8), Constants.TEXT);
			foundation.click(ConsumerSummary.BTN_REASON_SAVE);
			foundation.waitforElement(ConsumerSummary.SUCCESS_MESSAGE, Constants.SHORT_TIME);
			dropDown.selectItem(ConsumerSummary.DPD_SUBSIDY_GROUP_NAME, requiredData.get(2), Constants.TEXT);
			value = dropDown.getSelectedItem(ConsumerSummary.DPD_PAY_CYCLE);
			CustomisedAssert.assertEquals(value, rstLocationListData.get(CNLocationList.PAY_CYCLE));
			textBox.enterText(ConsumerSummary.INPUT_PAY_ROLL_ID, requiredData.get(11));
			foundation.click(ConsumerSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_RECORD_UPDATE_MSG, Constants.SHORT_TIME);

			// Select location and sync with device
			locationList.syncDevice(menus.get(0), location.get(1));

			// Launch V5 Device foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LandingPage.IMG_SEARCH_ICON));
			accountLogin.verifyAccountDetails(rstV5DeviceData.get(CNV5Device.EMAIL_ID),
					rstV5DeviceData.get(CNV5Device.PIN), requiredData.get(5));

			// Verify product purchase Transaction with Account balance
			String actualBal = accountLogin.productPurchase(productName.get(0), actualData,
					rstV5DeviceData.get(CNV5Device.ORDER_PAGE), rstV5DeviceData.get(CNV5Device.EMAIL_ID),
					rstV5DeviceData.get(CNV5Device.PIN), requiredData.get(5),
					rstV5DeviceData.get(CNV5Device.PAYMENTS_PAGE));

			accountLogin.verifyAccountDetails(rstV5DeviceData.get(CNV5Device.EMAIL_ID),
					rstV5DeviceData.get(CNV5Device.PIN), actualBal);

			// Product Search
			foundation.click(LandingPage.IMG_ORDER_SEARCH_ICON);
			textBox.enterKeypadText(productName.get(1));
			foundation.click(ProductSearch.BTN_PRODUCT);
			CustomisedAssert.assertEquals(foundation.getText(Order.TXT_HEADER), actualData.get(0));
			CustomisedAssert.assertEquals(foundation.getText(Order.TXT_PRODUCT), actualData.get(2));

			// Verify product purchase Transaction with PDE balance
			foundation.objectFocus(order.objText(rstV5DeviceData.get(CNV5Device.ORDER_PAGE)));
			foundation.click(order.objText(rstV5DeviceData.get(CNV5Device.ORDER_PAGE)));
			foundation.waitforElement(Payments.BTN_EMAIL_LOGIN, Constants.SHORT_TIME);
			foundation.click(Payments.BTN_EMAIL_LOGIN);
			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.EMAIL_ID));
			foundation.click(AccountLogin.BTN_NEXT);
			foundation.waitforElement(AccountLogin.BTN_PIN_NEXT, Constants.SHORT_TIME);
			textBox.enterPin(rstV5DeviceData.get(CNV5Device.PIN));
			foundation.click(AccountLogin.BTN_PIN_NEXT);
			CustomisedAssert
					.assertTrue(foundation.isDisplayed(order.objText(rstV5DeviceData.get(CNV5Device.PAYMENTS_PAGE))));
			foundation.waitforElement(LandingPage.IMG_SEARCH_ICON, Constants.SHORT_TIME);
			accountLogin.verifyAccountDetails(rstV5DeviceData.get(CNV5Device.EMAIL_ID),
					rstV5DeviceData.get(CNV5Device.PIN), actualBal);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {

			browser.close();
			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Select Org and menu
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menus.get(1));

			// Search Consumer Account
			foundation.click(ConsumerSearch.CLEAR_SEARCH);
			dropDown.selectItem(ConsumerSearch.DPD_SEARCH_BY,
					rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION), Constants.TEXT);
			textBox.enterText(ConsumerSearch.TXT_SEARCH, rstV5DeviceData.get(CNV5Device.EMAIL_ID));
			dropDown.selectItem(ConsumerSearch.DPD_LOCATION, location.get(1), Constants.TEXT);
			foundation.click(ConsumerSearch.BTN_GO);
			foundation.threadWait(Constants.ONE_SECOND);
			foundation.click(consumerSearch.objFirstNameCell(consumerSearch.getConsumerFirstName()));

			// Resetting Subsidy Balance
			foundation.click(ConsumerSummary.BTN_ADJUST);
			foundation.waitforElement(ConsumerSummary.LBL_POPUP_ADJUST_BALANCE, Constants.SHORT_TIME);
			textBox.enterText(ConsumerSummary.TXT_ADJUST_BALANCE, requiredData.get(10));
			dropdown.selectItem(ConsumerSummary.DPD_REASON, requiredData.get(8), Constants.TEXT);
			foundation.click(ConsumerSummary.BTN_REASON_SAVE);
			foundation.click(ConsumerSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_RECORD_UPDATE_MSG, Constants.SHORT_TIME);

			// Remove Device from AutoLocationConsumerVerified Location
			locationList.removeDevice(menus.get(0), location.get(1));

			// Deploy Device to AutomationLocation1 Location
			locationList.deployDevice(location.get(0), rstDeviceListData.get(CNDeviceList.PRODUCT_NAME));
		}
	}

	@Test(description = "166040 - verify the multi balance are applicable to USConnect kiosk devices")
	public void verifyMultiBalanceForUSConnectDevice() {
		final String CASE_NUM = "166040";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);

		List<String> requiredData = Arrays
				.asList(rstV5DeviceData.get(CNLocationSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Select Menu, Menu Item and Location
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));

			// Setting up Special type as USConnect
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_SPECIAL_TYPE));
			dropDown.selectItem(LocationSummary.DPD_SPECIAL_TYPE, requiredData.get(3), Constants.TEXT);
			String value = dropDown.getSelectedItem(LocationSummary.DPD_SPECIAL_TYPE);
			CustomisedAssert.assertEquals(value, requiredData.get(3));
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);

			// Select location and sync with device
			locationList.syncDevice(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM),
					rstLocationListData.get(CNLocationList.LOCATION_NAME));

			// Launch V5 Device and login to consumer account to check account Tab
			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LandingPage.IMG_SEARCH_ICON));
			foundation.click(LandingPage.BTN_MANAGE_LOGIN);
			foundation.click(AccountLogin.BTN_EMAIL_LOGIN);
			accountLogin.login(rstV5DeviceData.get(CNV5Device.EMAIL_ID), rstV5DeviceData.get(CNV5Device.PIN));
			String text = foundation.getText(AccountLogin.LBL_CONSUMER_NAME);
			CustomisedAssert.assertTrue(text.contains(requiredData.get(4)));
			CustomisedAssert.assertTrue(foundation.isDisplayed(AccountLogin.LBL_ACC_BAL));
			CustomisedAssert.assertFalse(foundation.isDisplayed(AccountLogin.LBL_SUBSIDY));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			browser.close();
			// resetting Test Data
			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Select Org and menu
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));

			// Setting up Special type as USConnect
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_SPECIAL_TYPE));
			dropDown.selectItem(LocationSummary.DPD_SPECIAL_TYPE, requiredData.get(2), Constants.TEXT);
			String value = dropDown.getSelectedItem(LocationSummary.DPD_SPECIAL_TYPE);
			CustomisedAssert.assertEquals(value, requiredData.get(2));
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);

			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);

			// Select GMA Subsidy ON
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_GMA_SUBSIDY));
			value = dropDown.getSelectedItem(LocationSummary.DPD_GMA_SUBSIDY);
			if (value.equals(requiredData.get(1))) {
				dropDown.selectItem(LocationSummary.DPD_GMA_SUBSIDY, requiredData.get(0), Constants.TEXT);
			}
			textBox.enterText(LocationSummary.TXT_TOP_OFF_GROUP_NAME, requiredData.get(5));
			textBox.enterText(LocationSummary.TXT_ROLL_OVER_GROUP_NAME, requiredData.get(6));
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);

			// Select location and sync with device
			locationList.syncDevice(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM),
					rstLocationListData.get(CNLocationList.LOCATION_NAME));
		}
	}

	@Test(description = "148841-Verify to Applying Default Group for Subsidy")

	public void verifykioskCreateAccountByEmail() {
		final String CASE_NUM = "148841";

		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);

		List<String> datas = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		try {
			// Launch v5 device
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LandingPage.IMG_SEARCH_ICON));
			foundation.waitforElement(LandingPage.BTN_CREATE_ACCOUNT, Constants.SHORT_TIME);
			foundation.click(LandingPage.BTN_CREATE_ACCOUNT);
			foundation.click(CreateAccount.LBL_SKIP_TO_END);
			foundation.objectFocus(CreateAccount.CHK_LABEL);
			foundation.click(CreateAccount.CHK_LABEL);
			foundation.threadWait(Constants.ONE_SECOND);
			foundation.click(CreateAccount.CONFIRM_BTN);
			foundation.waitforElement(CreateAccount.EMAIL_BTN, Constants.SHORT_TIME);
			foundation.click(CreateAccount.EMAIL_BTN);
			foundation.waitforElement(CreateAccount.EMAIL_BTN_CREATE, Constants.ONE_SECOND);
			foundation.click(CreateAccount.EMAIL_BTN_CREATE);
			foundation.threadWait(Constants.ONE_SECOND);
			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.EMAIL_ID));
			foundation.click(CreateAccount.BTN_NEXT);
			foundation.threadWait(Constants.SHORT_TIME);
			textBox.enterPin(rstV5DeviceData.get(CNV5Device.PIN));
			foundation.waitforElement(CreateAccount.BTN_PIN_NEXT, Constants.SHORT_TIME);
			foundation.click(CreateAccount.BTN_PIN_NEXT);
			textBox.enterPin(rstV5DeviceData.get(CNV5Device.PIN));
			foundation.waitforElement(AccountLogin.BTN_PIN_NEXT, Constants.SHORT_TIME);
			foundation.click(CreateAccount.BTN_PIN_NEXT);
			foundation.waitforElement(CreateAccount.TXT_FIRST_NAME, Constants.SHORT_TIME);
			foundation.click(CreateAccount.TXT_FIRST_NAME);
			foundation.threadWait(Constants.ONE_SECOND);
			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(datas.get(0));
			foundation.click(CreateAccount.TXT_LAST_NAME);
			textBox.enterKeypadText(datas.get(1));
			foundation.waitforElement(CreateAccount.NEXT_BTN, Constants.SHORT_TIME);
			foundation.click(CreateAccount.NEXT_BTN);
			foundation.waitforElement(CreateAccount.ADDLATER, Constants.SHORT_TIME);
			foundation.click(CreateAccount.ADDLATER);
			foundation.waitforElement(CreateAccount.COMPLETE_SETUP, Constants.ONE_SECOND);
			foundation.click(CreateAccount.CANCEL_BTN);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			browser.close();
			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSearch.TXT_CONSUMER_SEARCH));
			foundation.click(ConsumerSearch.CLEAR_SEARCH);
			textBox.enterText(ConsumerSearch.TXT_SEARCH, rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));
			dropDown.selectItem(ConsumerSearch.DPD_LOCATION, rstLocationListData.get(CNLocationList.LOCATION_NAME),
					Constants.TEXT);
			foundation.click(ConsumerSearch.BTN_GO);
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSearch.TBL_CONSUMERS));
			foundation.click(ConsumerSearch.LNK_FIRST_ROW);
			foundation.threadWait(Constants.THREE_SECOND);
			foundation.click(ConsumerSummary.BTN_PAYOUT_CLOSE);
			foundation.alertAccept();
			foundation.waitforElementToDisappear(ConsumerSummary.TXT_SPINNER_MSG, Constants.LONG_TIME);
		}
	}

	@Test(description = "166996 - Verify Update Prices & Full Sync Button on Location Summary page as Super")
	public void verifyUpdatePriceAndFullSync() {
		final String CASE_NUM = "166996";

		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);

		List<String> requiredData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		try {
			// launch browser and select org
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// navigate to location summary and update price and sync
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			locationList.selectLocationName(rstV5DeviceData.get(CNV5Device.LOCATION));
			foundation.objectFocus(LocationSummary.BTN_DEPLOY_DEVICE);
			locationSummary.selectingProduct(requiredData.get(8), requiredData.get(1), requiredData.get(0),
					requiredData.get(2));
			foundation.click(LocationSummary.BTN_FULL_SYNC);
			foundation.waitforElement(LocationSummary.LBL_SPINNER_MSG, Constants.SHORT_TIME);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			foundation.waitforElementToDisappear(LocationList.TXT_SPINNER_MSG, Constants.EXTRA_LONG_TIME);
			login.logout();
			browser.close();

			// launch v5 application
			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LandingPage.IMG_SEARCH_ICON));
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(requiredData.get(1));
			foundation.click(ProductSearch.BTN_PRODUCT);
			CustomisedAssert.assertEquals(foundation.getText(Order.TXT_HEADER), requiredData.get(4));
			CustomisedAssert.assertEquals(foundation.getText(Order.TXT_PRODUCT), requiredData.get(1));
			CustomisedAssert.assertEquals(foundation.getText(Order.LBL_PRODUCT_PRICE), requiredData.get(6));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// reset data
			browser.close();
			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			locationList.selectLocationName(rstV5DeviceData.get(CNV5Device.LOCATION));
			foundation.objectFocus(LocationSummary.BTN_DEPLOY_DEVICE);
			locationSummary.selectingProduct(requiredData.get(8), requiredData.get(1), requiredData.get(0),
					requiredData.get(7));
			foundation.click(LocationSummary.BTN_FULL_SYNC);
			foundation.waitforElement(LocationSummary.LBL_SPINNER_MSG, Constants.SHORT_TIME);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			foundation.waitforElementToDisappear(LocationList.TXT_SPINNER_MSG, Constants.EXTRA_LONG_TIME);
			login.logout();
			browser.close();
		}
	}
}
