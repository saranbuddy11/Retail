package at.smartshop.v5.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotEquals;
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
import at.framework.generic.DateAndTime;
import at.framework.generic.Strings;
import at.framework.ui.CheckBox;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.database.columns.CNConsumerSearch;
import at.smartshop.database.columns.CNConsumerSummary;
import at.smartshop.database.columns.CNLocation;
import at.smartshop.database.columns.CNDeviceList;
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
import at.smartshop.pages.DeviceSummary;
import at.smartshop.pages.EditPromotion;
import at.smartshop.pages.GlobalProduct;
import at.smartshop.pages.LocationList;
import at.smartshop.pages.LocationSummary;
import at.smartshop.pages.MicroMarketMenuList;
import at.smartshop.pages.NavigationBar;
import at.smartshop.pages.OrgList;
import at.smartshop.pages.OrgSummary;
import at.smartshop.pages.ProductSummary;
import at.smartshop.pages.PromotionList;
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
	private CreatePromotions createPromotions=new CreatePromotions();
	private LandingPage landingPage = new LandingPage();
	private AccountLogin accountLogin = new AccountLogin();
	private EditAccount editAccount = new EditAccount();
	private NavigationBar navigationBar = new NavigationBar();
	private GlobalProduct globalProduct = new GlobalProduct();
	private LocationList locationList = new LocationList();
	private LocationSummary locationSummary = new LocationSummary();
	private Dropdown dropdown = new Dropdown();
	private AdminMenu adminMenu = new AdminMenu();
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
	private CategoryList categoryList=new CategoryList();
	private CategorySummary categorySummary=new CategorySummary();
	private ConsumerSearch consumerSearch = new ConsumerSearch();
	private EditPromotion editPromotion=new EditPromotion();
	private OrgSummary orgSummary=new OrgSummary();

	private Map<String, String> rstV5DeviceData;
	private Map<String, String> rstNavigationMenuData;
	private Map<String, String> rstLocationListData;
	private Map<String, String> rstLocationSummaryData;
	private Map<String, String> rstConsumerSearchData;
	private Map<String, String> rstConsumerSummaryData;
	private Map<String, String> rstLocationData;
	private Map<String, String> rstOrgSummaryData;



	@Test(description = "141874-Kiosk Manage Account > Edit Account > Update Information")
	public void editAccountUpdateInformation() {
		try {
			final String CASE_NUM = "141874";

			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
			browser.navigateURL(	propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
							propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			List<String> requiredData = Arrays.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> language = Arrays	.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			locationSummary.kiosklanguageSetting(rstLocationListData.get(CNLocationList.LOCATION_NAME), language.get(0),language.get(1));

			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);

			// login to application
			foundation.click(landingPage.objLanguage(requiredData.get(1)));
			foundation.click(LandingPage.BTN_LOGIN);
			foundation.click(AccountLogin.BTN_EMAIL_LOGIN);
			accountLogin.login(rstV5DeviceData.get(CNV5Device.EMAIL_ID), rstV5DeviceData.get(CNV5Device.PIN));

			// navigate to edit account and get previous information
			foundation.click(EditAccount.BTN_EDIT_ACCOUNT);
			String firstName = textBox.getTextFromInput(EditAccount.TXT_FIRST_NAME);
			String lastName = textBox.getTextFromInput(EditAccount.TXT_LAST_NAME);
			String emailAddress = textBox.getTextFromInput(EditAccount.TXT_EMAIL_ADDRESS);

			// update information
			foundation.click(EditAccount.BTN_CAMEL_CASE);
			List<String> actualData = Arrays.asList(rstV5DeviceData.get(CNV5Device.ACTUAL_DATA).split(Constants.DELIMITER_TILD));
			editAccount.updateText(EditAccount.TXT_FIRST_NAME, actualData.get(0), firstName);
			editAccount.updateText(EditAccount.TXT_LAST_NAME, actualData.get(1), lastName);
			editAccount.updateText(EditAccount.TXT_EMAIL_ADDRESS, actualData.get(2), emailAddress);
			foundation.click(EditAccount.BTN_SAVE);
			assertTrue(foundation.isDisplayed(EditAccount.BTN_EDIT_ACCOUNT));

			// navigate back to edit account and verify all data are populating as entered before
			foundation.click(EditAccount.BTN_EDIT_ACCOUNT);
			assertTrue(textBox.getTextFromInput(EditAccount.TXT_FIRST_NAME).equals(actualData.get(0)));
			assertTrue(textBox.getTextFromInput(EditAccount.TXT_LAST_NAME).equals(actualData.get(1)));
			assertTrue(textBox.getTextFromInput(EditAccount.TXT_EMAIL_ADDRESS).equals(actualData.get(2)));

			// reset the data
			foundation.click(EditAccount.BTN_CAMEL_CASE);
			editAccount.updateText(EditAccount.TXT_FIRST_NAME, firstName, actualData.get(0));
			editAccount.updateText(EditAccount.TXT_LAST_NAME, lastName, actualData.get(1));
			editAccount.updateText(EditAccount.TXT_EMAIL_ADDRESS, emailAddress, actualData.get(2));
			foundation.click(EditAccount.BTN_SAVE);
			assertTrue(foundation.isDisplayed(EditAccount.BTN_EDIT_ACCOUNT));

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	@Test(description = "143464-Verify the Search button in Device when user set 'Yes' the Inherit from Location")
	public void verifySearchButton() {
		try {

			final String CASE_NUM = "143464";

			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
			rstOrgSummaryData = dataBase.getOrgSummaryData(Queries.LOCATION_LIST, CASE_NUM);

			browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					        propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.objectFocus(LocationSummary.BTN_DEPLOY_DEVICE);
			textBox.enterText(LocationSummary.TXT_DEVICE_SEARCH,rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));
			locationSummary.selectDeviceName(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));
			foundation.waitforElement(DeviceSummary.LBL_DEVICE_SUMMARY, Constants.SHORT_TIME);
			List<String> requiredData = Arrays.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			dropDown.selectItem(DeviceSummary.DPD_SHOW_SEARCH_BTN, requiredData.get(0), Constants.TEXT);
			foundation.click(DeviceSummary.BTN_SAVE);
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.waitforElement(LocationSummary.BTN_SAVE, Constants.SHORT_TIME);
			foundation.click(LocationSummary.BUTTON_LOCATION_INFO);
			dropDown.selectItem(LocationSummary.DPD_SHOW_PROD_LOOKUP, requiredData.get(1), Constants.TEXT);
			foundation.click(LocationSummary.BTN_SYNC);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_FILTER, Constants.SHORT_TIME);
			login.logout();
			browser.close();
			
			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(landingPage.objLanguage(rstV5DeviceData.get(CNV5Device.ACTUAL_DATA)));
			foundation.refreshPage();
			foundation.isDisplayed(LandingPage.IMG_SEARCH_ICON);

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}

	}

	@Test(description = "142741- SOS-24492 Verify alternate language is set to Finnish in Kiosk when user set the Alternate Language as Finnish and full sync is done in ADM")
	public void alternateFinnishLanguage() {
		try {
			final String CASE_NUM = "142741";
			// Reading test data from DataBase
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			List<String> requiredData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Selecting location
			locationList.selectLocationName(requiredData.get(0));
			dropDown.selectItem(LocationSummary.DPD_KIOSK_LANGUAGE, requiredData.get(1), Constants.TEXT);
			dropDown.selectItem(LocationSummary.DPD_ALTERNATE_LANGUAGE, requiredData.get(2), Constants.TEXT);
			foundation.click(LocationSummary.BTN_SYNC);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			login.logout();
			browser.close();

			foundation.threadWait(Constants.SHORT_TIME);
			// login into Kiosk Device
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(LandingPage.BTN_LANG);

			// Validating Landing Page
			foundation.waitforElement(landingPage.objLanguage(requiredData.get(3)), Constants.SHORT_TIME);
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
			Assert.assertTrue(
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
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.EMAIL_ID));
			foundation.click(AccountLogin.BTN_NEXT);
			foundation.waitforElement(AccountLogin.BTN_PIN_NEXT, Constants.SHORT_TIME);
			textBox.enterPin(rstV5DeviceData.get(CNV5Device.PIN));
			foundation.click(AccountLogin.BTN_PIN_NEXT);
			payments.verifyPaymentPageLanguage(rstV5DeviceData.get(CNV5Device.PAYMENTS_PAGE));
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
			locationList.selectLocationName(requiredData.get(0));
			dropDown.selectItem(LocationSummary.DPD_KIOSK_LANGUAGE, requiredData.get(4), Constants.TEXT);
			dropDown.selectItem(LocationSummary.DPD_ALTERNATE_LANGUAGE, requiredData.get(2), Constants.TEXT);
			foundation.click(LocationSummary.BTN_SYNC);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			login.logout();

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	@Test(description = "142742-Verify default and Alternative languages in Kiosk when user sets same language and full sync is done in ADM")
	public void englishDefaultAltLanguage() {
		try {
			final String CASE_NUM = "142742";
			// Reading test data from DataBase
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			List<String> requiredData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Selecting location
			locationList.selectLocationName(requiredData.get(0));
			dropDown.selectItem(LocationSummary.DPD_KIOSK_LANGUAGE, requiredData.get(1), Constants.TEXT);
			dropDown.selectItem(LocationSummary.DPD_ALTERNATE_LANGUAGE, requiredData.get(1), Constants.TEXT);

			foundation.click(LocationSummary.BTN_SYNC);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_FILTER, Constants.SHORT_TIME);
			login.logout();
			browser.close();

			foundation.threadWait(Constants.SHORT_TIME);
			// login into Kiosk Device
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(LandingPage.BTN_LANG);

			// Validating Landing Page
			// foundation.waitforElement(landingPage.objLanguage(requiredData.get(1)),
			// Constants.SHORT_TIME);
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
			Assert.assertTrue(
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
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.EMAIL_ID));
			foundation.click(AccountLogin.BTN_NEXT);
			foundation.waitforElement(AccountLogin.BTN_PIN_NEXT, Constants.SHORT_TIME);
			textBox.enterPin(rstV5DeviceData.get(CNV5Device.PIN));
			foundation.click(AccountLogin.BTN_PIN_NEXT);
			payments.verifyPaymentPageLanguage(rstV5DeviceData.get(CNV5Device.PAYMENTS_PAGE));
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
			locationList.selectLocationName(requiredData.get(0));
			dropDown.selectItem(LocationSummary.DPD_KIOSK_LANGUAGE, requiredData.get(1), Constants.TEXT);
			dropDown.selectItem(LocationSummary.DPD_ALTERNATE_LANGUAGE, requiredData.get(2), Constants.TEXT);
			foundation.click(LocationSummary.BTN_SYNC);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			login.logout();

		} catch (Exception exc) {
			Assert.fail(exc.toString());
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
			locationSummary.kiosklanguageSetting(rstLocationListData.get(CNLocationList.LOCATION_NAME), language.get(0),
					language.get(1));

			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(LandingPage.LINK_ENGLISH);
			adminMenu.navigateDriverLoginPage();
			String pin = propertyFile.readPropertyFile(Configuration.V5_DRIVER_PIN, FilePath.PROPERTY_CONFIG_FILE);
			textBox.enterDriverPin(pin);
			foundation.click(AdminMenu.BTN_SIGN_IN);
			foundation.isDisplayed(AdminMenu.LINK_DRIVER_LOGOUT);
			foundation.click(AdminMenu.LINK_DRIVER_LOGOUT);
			foundation.isDisplayed(AdminMenu.BTN_SIGN_IN);

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	@Test(description = "141875-Kiosk Manage Account > Edit Account > Change PIN")
	public void editAccountChangePin() {
		try {
			final String CASE_NUM = "141875";

			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
			browser.navigateURL(	propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			List<String> requiredData = Arrays.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> language = Arrays	.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			locationSummary.kiosklanguageSetting(rstLocationListData.get(CNLocationList.LOCATION_NAME), language.get(0),language.get(1));

			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			foundation.click(landingPage.objLanguage(requiredData.get(0)));
			foundation.click(LandingPage.BTN_LOGIN);
			foundation.click(AccountLogin.BTN_EMAIL_LOGIN);
			accountLogin.login(rstV5DeviceData.get(CNV5Device.EMAIL_ID), rstV5DeviceData.get(CNV5Device.PIN));

			// navigate to edit account and update pin
			foundation.click(EditAccount.BTN_EDIT_ACCOUNT);
			foundation.click(EditAccount.BTN_CHANGE_PIN);
			textBox.enterPin(requiredData.get(1));
			foundation.click(EditAccount.BTN_EDIT_NEXT);
			textBox.enterPin(requiredData.get(1));
			foundation.click(EditAccount.BTN_SAVE_PIN);
			assertTrue(foundation.isDisplayed(EditAccount.BTN_EDIT_ACCOUNT));

			// reset data
			foundation.click(EditAccount.BTN_EDIT_ACCOUNT);
			foundation.click(EditAccount.BTN_CHANGE_PIN);
			textBox.enterPin(rstV5DeviceData.get(CNV5Device.PIN));
			foundation.click(EditAccount.BTN_EDIT_NEXT);
			textBox.enterPin(rstV5DeviceData.get(CNV5Device.PIN));
			foundation.click(EditAccount.BTN_SAVE_PIN);
			assertTrue(foundation.isDisplayed(EditAccount.BTN_EDIT_ACCOUNT));
			
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	@Test(description = "141887-Kiosk Checkout UI > Canceling Cart")
	public void cancellingCart() {
		try {
			final String CASE_NUM = "141887";

			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
			browser.navigateURL(	propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
				         	propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			List<String> requiredData = Arrays.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> language = Arrays	.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			locationSummary.kiosklanguageSetting(rstLocationListData.get(CNLocationList.LOCATION_NAME), language.get(0),language.get(1));

			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			List<String> actualData = Arrays.asList(rstV5DeviceData.get(CNV5Device.ACTUAL_DATA).split(Constants.DELIMITER_TILD));
			// login to application
			foundation.click(landingPage.objLanguage(requiredData.get(0)));
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(requiredData.get(1));
			foundation.click(ProductSearch.BTN_PRODUCT);
			assertEquals(foundation.getText(Order.TXT_HEADER), actualData.get(0));
			assertEquals(foundation.getText(Order.TXT_PRODUCT), actualData.get(1));

			// cancel order
			foundation.click(Order.BTN_CANCEL_ORDER);
			assertTrue(foundation.isDisplayed(Order.LBL_ORDER_CANCELLED));
			
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	@Test(description = "141889-Kiosk Checkout UI > Taxes Applied")
	public void taxesApplied() {

		try {
			final String CASE_NUM = "141889";

			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
			browser.navigateURL(	propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
							propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			List<String> language = Arrays	.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			locationSummary.kiosklanguageSetting(rstLocationListData.get(CNLocationList.LOCATION_NAME), language.get(0),language.get(1));

			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			List<String> requiredData = Arrays.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> actualData = Arrays.asList(rstV5DeviceData.get(CNV5Device.ACTUAL_DATA).split(Constants.DELIMITER_TILD));

			// login to application
			foundation.click(landingPage.objLanguage(requiredData.get(0)));
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(requiredData.get(1));
			foundation.click(ProductSearch.BTN_PRODUCT);
			assertEquals(foundation.getText(Order.TXT_HEADER), actualData.get(0));
			assertEquals(foundation.getText(Order.TXT_PRODUCT), actualData.get(1));

			// verify the display of total section
			String productPrice = foundation.getText(Order.LBL_PRODUCT_PRICE).split(Constants.DOLLAR)[1];
			String deposit = foundation.getText(Order.LBL_DEPOSIT).split(Constants.DOLLAR)[1];
			Double expectedBalanceDue = Double.parseDouble(productPrice) + Double.parseDouble(deposit);
			assertTrue(foundation.getText(Order.LBL_BALANCE_DUE).contains(String.valueOf(expectedBalanceDue)));
			assertTrue(foundation.getText(Order.LBL_SUB_TOTAL).contains(productPrice));
			assertEquals(foundation.getText(Order.LBL_TAX), requiredData.get(2));

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	@Test(description = "141890-Kiosk Checkout UI > Bottle Deposits Applied")
	public void bottleDepositApplied() {
		try {
			final String CASE_NUM = "141890";

			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
			browser.navigateURL(	propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			List<String> language = Arrays	.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			locationSummary.kiosklanguageSetting(rstLocationListData.get(CNLocationList.LOCATION_NAME), language.get(0),language.get(1));

			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			List<String> requiredData = Arrays.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> actualData = Arrays.asList(rstV5DeviceData.get(CNV5Device.ACTUAL_DATA).split(Constants.DELIMITER_TILD));

			// login to application
			foundation.click(landingPage.objLanguage(requiredData.get(0)));
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(requiredData.get(1));
			foundation.click(ProductSearch.BTN_PRODUCT);
			assertEquals(foundation.getText(Order.TXT_HEADER), actualData.get(0));
			assertEquals(foundation.getText(Order.TXT_PRODUCT), actualData.get(1));

			// verify the display of total section
			String productPrice = foundation.getText(Order.LBL_PRODUCT_PRICE).split(Constants.DOLLAR)[1];
			String deposit = foundation.getText(Order.LBL_DEPOSIT).split(Constants.DOLLAR)[1];
			Double expectedBalanceDue = Double.parseDouble(productPrice) + Double.parseDouble(deposit);
			String expBalanceDue = String. format("%.2f", expectedBalanceDue);
			assertTrue(foundation.getText(Order.LBL_BALANCE_DUE).contains(expBalanceDue));
			assertTrue(foundation.getText(Order.LBL_SUB_TOTAL).contains(productPrice));
			
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	@Test(description = "142696-SOS-24494-V5 -validate the search functionality for product search")
	public void searchFunctionalityProduct() {
		try {
			final String CASE_NUM = "142696";
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
							propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			List<String> requiredData = Arrays.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> language = Arrays	.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			locationSummary.kiosklanguageSetting(rstLocationListData.get(CNLocationList.LOCATION_NAME), language.get(0),language.get(1));

			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);

			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			List<String> actualData = Arrays.asList(rstV5DeviceData.get(CNV5Device.ACTUAL_DATA).split(Constants.DELIMITER_TILD));

			// login to application
			foundation.click(landingPage.objLanguage(requiredData.get(0)));
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(requiredData.get(1));
			assertTrue(foundation.isDisplayed(ProductSearch.BTN_PRODUCT));
			textBox.deleteKeypadText(requiredData.get(1));
			assertFalse(foundation.isDisplayed(ProductSearch.BTN_PRODUCT));
			textBox.enterKeypadText(requiredData.get(2));
			assertTrue(foundation.getText(ProductSearch.LBL_PRODUCT_NAME).contains(actualData.get(1)));
			foundation.click(ProductSearch.BTN_PRODUCT);
			assertEquals(foundation.getText(Order.TXT_HEADER), actualData.get(0));
			assertEquals(foundation.getText(Order.TXT_PRODUCT), actualData.get(1));
			
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	@Test(description = "142697-SOS-24494-V5 -validate the search functionality for scan code search")
	public void searchFunctionalityScancode() {
		try {

			final String CASE_NUM = "142697";
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);

			browser.navigateURL(	propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			List<String> requiredData = Arrays.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> language = Arrays	.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			locationSummary.kiosklanguageSetting(rstLocationListData.get(CNLocationList.LOCATION_NAME), language.get(0),language.get(1));

			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			List<String> actualData = Arrays.asList(rstV5DeviceData.get(CNV5Device.ACTUAL_DATA).split(Constants.DELIMITER_TILD));

			// login to application
			foundation.click(landingPage.objLanguage(requiredData.get(0)));
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			foundation.click(ProductSearch.BTN_123);
			textBox.enterKeypadText(requiredData.get(1));
			assertTrue(foundation.isDisplayed(ProductSearch.BTN_PRODUCT));
			textBox.deleteKeypadText(requiredData.get(1));
			assertFalse(foundation.isDisplayed(ProductSearch.BTN_PRODUCT));
			textBox.enterKeypadText(requiredData.get(2));
			assertTrue(foundation.getText(ProductSearch.LBL_PRODUCT_NAME).contains(requiredData.get(3)));
			foundation.click(ProductSearch.BTN_PRODUCT);
			assertEquals(foundation.getText(Order.TXT_HEADER), actualData.get(0));
			assertEquals(foundation.getText(Order.TXT_PRODUCT), actualData.get(1));
			
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	@Test(description = "142699-SOS-24494-V5 - update product name and verify in kiosk machine cart page")
	public void updateProductName() {
		try {

			final String CASE_NUM = "142699";
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			
			List<String> requiredData = Arrays.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> menuItem = Arrays.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
			List<String> language = Arrays	.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));

			// launch browser and select org
			browser.navigateURL(	propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
							propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// navigate to global product of V5 associated and update name and sync
			navigationBar.navigateToMenuItem(menuItem.get(0));
			foundation.threadWait(Constants.ONE_SECOND);
			textBox.enterText(GlobalProduct.TXT_FILTER, requiredData.get(0));
			foundation.click(globalProduct.getGlobalProduct(requiredData.get(1)));
			textBox.enterText(ProductSummary.TXT_PRODUCT_NAME, requiredData.get(2));
			foundation.click(ProductSummary.BTN_SAVE);
			navigationBar.navigateToMenuItem(menuItem.get(1));
			locationSummary.kiosklanguageSetting(rstLocationListData.get(CNLocationList.LOCATION_NAME), language.get(0),language.get(1));

			// launch v5 application
			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(landingPage.objLanguage(requiredData.get(5)));
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(requiredData.get(1));
			assertFalse(foundation.isDisplayed(ProductSearch.BTN_PRODUCT));
			textBox.deleteKeypadText(requiredData.get(1));
			textBox.enterKeypadText(requiredData.get(2));
			assertTrue(foundation.getText(ProductSearch.LBL_PRODUCT_NAME).contains(requiredData.get(2)));
			foundation.click(ProductSearch.BTN_PRODUCT);
			assertEquals(foundation.getText(Order.TXT_HEADER), requiredData.get(4));
			assertEquals(foundation.getText(Order.TXT_PRODUCT), requiredData.get(2));

			// reset data
			browser.close();
			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
							propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItem.get(0));
			foundation.threadWait(Constants.TWO_SECOND);
			textBox.enterText(GlobalProduct.TXT_FILTER, requiredData.get(0));
			foundation.click(globalProduct.getGlobalProduct(requiredData.get(2)));
			textBox.enterText(ProductSummary.TXT_PRODUCT_NAME, requiredData.get(1));
			foundation.click(ProductSummary.BTN_SAVE);
			
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	@Test(description = "142700-SOS-24494-V5 - update tax category and verify in kiosk machine cart page")
	public void updateTaxCategory() {
		try {

			final String CASE_NUM = "142700";

			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			List<String> requiredData = Arrays.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> menuItem = Arrays.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
			List<String> language = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));

			// launch browser and select org
			browser.navigateURL(	propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					        propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// navigate to global product of V5 associated and update tax category and sync
			navigationBar.navigateToMenuItem(menuItem.get(0));
			foundation.threadWait(Constants.SHORT_TIME);
			textBox.enterText(GlobalProduct.TXT_FILTER, requiredData.get(0));
			foundation.click(globalProduct.getGlobalProduct(requiredData.get(1)));
			dropdown.selectItem(ProductSummary.DPD_TAX_CATEGORY, requiredData.get(2), Constants.TEXT);
			foundation.click(ProductSummary.BTN_SAVE);
			locationSummary.kiosklanguageSetting(requiredData.get(3), language.get(0),language.get(1));

			// launch v5 application
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(landingPage.objLanguage(requiredData.get(5)));
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(requiredData.get(1));
			foundation.click(ProductSearch.BTN_PRODUCT);
			assertEquals(foundation.getText(Order.TXT_HEADER), requiredData.get(4));
			assertEquals(foundation.getText(Order.TXT_PRODUCT), requiredData.get(1));
			assertEquals(foundation.getText(Order.LBL_TAX), requiredData.get(6));

			// reset data
			browser.close();
			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(	propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					        propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItem.get(0));
			foundation.threadWait(Constants.SHORT_TIME);
			textBox.enterText(GlobalProduct.TXT_FILTER, requiredData.get(0));
			foundation.click(globalProduct.getGlobalProduct(requiredData.get(1)));
			dropdown.selectItem(ProductSummary.DPD_TAX_CATEGORY, requiredData.get(7), Constants.TEXT);
			foundation.click(ProductSummary.BTN_SAVE);
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	@Test(description = "142993-V5-Ensure canadian Currency Cash Funding")
	public void verifyCADCashFunding() {
		try {
			final String CASE_NUM = "142993";

			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);

			browser.navigateURL(	propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			List<String> menu = Arrays.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
			List<String> requiredData = Arrays.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> actualData = Arrays.asList(rstV5DeviceData.get(CNV5Device.ACTUAL_DATA).split(Constants.DELIMITER_TILD));
			List<String> country = Arrays.asList(rstLocationSummaryData.get(CNLocationSummary.COUNTRY).split(Constants.DELIMITER_TILD));

			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menu.get(0));
			foundation.waitforElement(OrgSummary.DPD_CURRENCY, Constants.SHORT_TIME);
			dropDown.selectItem(OrgSummary.DPD_COUNTRY, country.get(0), Constants.TEXT);
			dropDown.selectItem(OrgSummary.DPD_CURRENCY, requiredData.get(0), Constants.TEXT);
			foundation.click(OrgSummary.BTN_SAVE);
			foundation.waitforElement(OrgList.LBL_ORG_LIST, Constants.SHORT_TIME);
			navigationBar.navigateToMenuItem(menu.get(1));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			dropDown.selectItem(LocationSummary.DPD_KIOSK_LANGUAGE, actualData.get(0), Constants.TEXT);
			dropDown.selectItem(LocationSummary.DPD_ALTERNATE_LANGUAGE, actualData.get(1), Constants.TEXT);
			foundation.click(LocationSummary.BTN_SYNC);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_FILTER, Constants.SHORT_TIME);
			login.logout();
			browser.close();

			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(landingPage.objLanguage(actualData.get(0)));
			foundation.click(LandingPage.LBL_ACCOUNT_LOGIN);
			foundation.click(AccountLogin.BTN_EMAIL_LOGIN);
			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(propertyFile.readPropertyFile(Configuration.V5_USER, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(AccountLogin.BTN_NEXT);
			foundation.waitforElement(AccountLogin.BTN_PIN_NEXT, Constants.SHORT_TIME);
			textBox.enterPin(propertyFile.readPropertyFile(Configuration.V5_PIN, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(AccountLogin.BTN_PIN_NEXT);
			foundation.click(AccountDetails.BTN_FUND_CASH);
			Assert.assertTrue(foundation.isDisplayed(fundAccount.objText(requiredData.get(2))));
			foundation.click(fundAccount.objText(requiredData.get(3)));
			Assert.assertTrue(foundation.isDisplayed(AccountDetails.BTN_FUND_CASH));
			browser.close();

			// reset the data
			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(	propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menu.get(0));
			foundation.waitforElement(OrgSummary.DPD_CURRENCY, Constants.SHORT_TIME);
			dropDown.selectItem(OrgSummary.DPD_COUNTRY, country.get(1), Constants.TEXT);
			dropDown.selectItem(OrgSummary.DPD_CURRENCY, requiredData.get(1), Constants.TEXT);
			foundation.click(OrgSummary.BTN_SAVE);
			foundation.waitforElement(OrgList.LBL_ORG_LIST, Constants.SHORT_TIME);
			navigationBar.navigateToMenuItem(menu.get(1));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_SYNC);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_FILTER, Constants.SHORT_TIME);

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	@Test(description = "142717-SOS-24494-V5 - Assign Deposit value of product and verify it on Kiosk machine cart page")
	public void assignDepositValue() {
		try {

			final String CASE_NUM = "142717";

			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			List<String> requiredData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> menuItem = Arrays
					.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
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
			foundation.threadWait(Constants.TWO_SECOND);
			textBox.enterText(GlobalProduct.TXT_FILTER, requiredData.get(0));
			foundation.click(globalProduct.getGlobalProduct(requiredData.get(1)));
			dropdown.selectItem(ProductSummary.DPD_DEPOSIT_CATEGORY, requiredData.get(2), Constants.TEXT);
			foundation.click(ProductSummary.BTN_SAVE);
			navigationBar.navigateToMenuItem(menuItem.get(1));
			locationSummary.kiosklanguageSetting(requiredData.get(3), language.get(0), language.get(1));

			// launch v5 application
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(landingPage.objLanguage(requiredData.get(5)));
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(requiredData.get(1));
			assertTrue(foundation.getText(ProductSearch.LBL_PRODUCT_NAME).contains(requiredData.get(1)));
			foundation.click(ProductSearch.BTN_PRODUCT);
			assertEquals(foundation.getText(Order.TXT_HEADER), requiredData.get(4));
			assertEquals(foundation.getText(Order.TXT_PRODUCT), requiredData.get(1));
			assertEquals(foundation.getText(Order.LBL_DEPOSIT), requiredData.get(6));

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
			foundation.threadWait(Constants.TWO_SECOND);
			textBox.enterText(GlobalProduct.TXT_FILTER, requiredData.get(0));
			foundation.click(globalProduct.getGlobalProduct(requiredData.get(1)));
			dropdown.selectItem(ProductSummary.DPD_DEPOSIT_CATEGORY, requiredData.get(7), Constants.TEXT);
			foundation.click(ProductSummary.BTN_SAVE);
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	@Test(description = "142718-SOS-24494-V5 -Edit cost/price of the product and verify it on Kiosk machine cart page")
	public void editPrice() {
		try {
			final String CASE_NUM = "142718";

			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			List<String> requiredData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			String menuItem = rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM);

			// launch browser and select org

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// navigate to location summary and update price and sync
			navigationBar.navigateToMenuItem(menuItem);
			textBox.enterText(LocationList.TXT_FILTER, requiredData.get(3));
			locationList.selectLocationName(requiredData.get(3));
			locationSummary.selectTab(requiredData.get(8));
			foundation.threadWait(Constants.TWO_SECOND);
			textBox.enterText(LocationSummary.TXT_PRODUCT_FILTER, requiredData.get(1));
			locationSummary.enterPrice(requiredData.get(0), requiredData.get(2));
			foundation.click(LocationSummary.BTN_SAVE);
			List<String> language = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
			locationSummary.kiosklanguageSetting(rstLocationListData.get(CNLocationList.LOCATION_NAME), language.get(0),
					language.get(1));

			// launch v5 application
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(landingPage.objLanguage(requiredData.get(5)));
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(requiredData.get(1));
			foundation.click(ProductSearch.BTN_PRODUCT);
			assertEquals(foundation.getText(Order.TXT_HEADER), requiredData.get(4));
			assertEquals(foundation.getText(Order.LBL_PRODUCT_PRICE), requiredData.get(6));

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
			textBox.enterText(LocationList.TXT_FILTER, requiredData.get(3));
			locationList.selectLocationName(requiredData.get(3));
			locationSummary.selectTab(requiredData.get(8));
			foundation.threadWait(Constants.TWO_SECOND);
			textBox.enterText(LocationSummary.TXT_PRODUCT_FILTER, requiredData.get(1));
			locationSummary.enterPrice(requiredData.get(0), requiredData.get(7));
			foundation.click(LocationSummary.BTN_SAVE);
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	@Test(description = "142722-SOS-24494-V5 - Apply 'is disabled' for product and verify it on Kiosk machine cart page")
	public void applyIsDisabled() {
		try {
			final String CASE_NUM = "142722";

			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			List<String> requiredData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> menuItem = Arrays
					.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));

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
			locationSummary.kiosklanguageSetting(requiredData.get(3), language.get(0), language.get(1));

			// launch v5 application
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(landingPage.objLanguage(requiredData.get(4)));
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(requiredData.get(1));
			assertFalse(foundation.isDisplayed(ProductSearch.BTN_PRODUCT));

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
			foundation.waitforElement(GlobalProduct.TXT_FILTER, 3);
			foundation.click(GlobalProduct.ICON_FILTER);
			globalProduct.selectFilter(requiredData.get(6));
			textBox.enterText(GlobalProduct.TXT_FILTER, requiredData.get(0));
			foundation.click(globalProduct.getGlobalProduct(requiredData.get(1)));
			dropdown.selectItem(ProductSummary.DPD_IS_DISABLED, requiredData.get(5), Constants.TEXT);
			foundation.click(ProductSummary.BTN_SAVE);

			// reset data-assign back the product to location
			navigationBar.navigateToMenuItem(menuItem.get(1));
			textBox.enterText(LocationList.TXT_FILTER, requiredData.get(3));
			locationList.selectLocationName(requiredData.get(3));
			locationSummary.selectTab(requiredData.get(7));
			foundation.waitforElement(LocationSummary.BTN_ADD_PRODUCT, 2);
			locationSummary.addProduct(requiredData.get(0));
			foundation.click(LocationSummary.BTN_FULL_SYNC);
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	@Test(description = "141868-This test validates the Inventory options")
	public void verifyInventoryOptions() {
		try {
			final String CASE_NUM = "141868";

			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			browser.navigateURL(	propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
							propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);

			List<String> language = Arrays	.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			locationSummary.kiosklanguageSetting(rstLocationListData.get(CNLocationList.LOCATION_NAME), language.get(0),language.get(1));

			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(landingPage.objLanguage(language.get(0)));
			adminMenu.navigateDriverLoginPage();
			String pin = propertyFile.readPropertyFile(Configuration.V5_DRIVER_PIN, FilePath.PROPERTY_CONFIG_FILE);
			textBox.enterPin(pin);
			foundation.click(AdminMenu.BTN_SIGN_IN);
			foundation.isDisplayed(AdminMenu.LINK_DRIVER_LOGOUT);
			foundation.click(AdminMenu.LINK_INVENTORY);
			List<String> optionNames = Arrays.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			adminMenu.verifyOptions(optionNames);

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	@Test(description = "141873- Verify the products as per the user search")
	public void verifyProductSearch() {
		try {

			final String CASE_NUM = "141873";
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			browser.navigateURL(	propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
							propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);

			List<String> requiredData = Arrays.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> language = Arrays	.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			locationSummary.kiosklanguageSetting(rstLocationListData.get(CNLocationList.LOCATION_NAME), language.get(0),language.get(1));

			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));

			foundation.click(landingPage.objLanguage(language.get(0)));
			foundation.refreshPage();
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			List<String> productName = Arrays.asList(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME).split(Constants.DELIMITER_TILD));
			textBox.enterKeypadText(productName.get(0));
			String prodCount = foundation.getText(ProductSearch.LBL_PROD_COUNT);
			String prodFoundText = foundation.getText(ProductSearch.LBL_PROD_MESSAGE);
			String unMatchedProdMsg = prodCount + " " + prodFoundText;
			Assert.assertEquals(unMatchedProdMsg, requiredData.get(0));
			foundation.click(ProductSearch.LINK_CLOSE_POPUP);
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(productName.get(1));
			String matchedCount = foundation.getText(ProductSearch.LBL_PROD_COUNT);
			String matchedProdMsg = matchedCount + " " + prodFoundText;
			Assert.assertEquals(matchedProdMsg, requiredData.get(1));

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	@Test(description = "141872-This test validates the item in the Your order screen")
	public void verifyItemInOrderScreen() {
		try {

			final String CASE_NUM = "141872";

			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			browser.navigateURL(	propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
							propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);

			List<String> language = Arrays	.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			locationSummary.kiosklanguageSetting(rstLocationListData.get(CNLocationList.LOCATION_NAME), language.get(0),language.get(1));

			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(landingPage.objLanguage(language.get(0)));

			// search and click product
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
			foundation.click(ProductSearch.BTN_PRODUCT);
			assertEquals(foundation.getText(Order.TXT_HEADER), rstV5DeviceData.get(CNV5Device.REQUIRED_DATA));
			assertEquals(foundation.getText(Order.TXT_PRODUCT), rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	@Test(description = "142663- This test validates the functionality of Cancel order")

	public void verifyCancelOrderFunctionality() {
		try {

			final String CASE_NUM = "142663";
			
			browser.navigateURL(	propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
							propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			
		
			List<String> language = Arrays.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			locationSummary.kiosklanguageSetting(rstLocationListData.get(CNLocationList.LOCATION_NAME),language.get(0),language.get(1));						
			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);					
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
		
			foundation.click(landingPage.objLanguage(language.get(0)));
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
			foundation.click(ProductSearch.BTN_PRODUCT);
			Assert.assertTrue(foundation.isDisplayed(Order.BTN_CANCEL_ORDER));
			foundation.click(Order.BTN_CANCEL_ORDER);
			Assert.assertTrue(foundation.isDisplayed(Order.LBL_ORDER_CANCELLED));
			Assert.assertTrue(foundation.isDisplayed(LandingPage.IMG_SEARCH_ICON));

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	@Test(description = "142664- This test validates the Yes button functionality on Order Screen")
	public void verifyYesButtonFunctionality() {
		try {

			final String CASE_NUM = "142664";
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);

			browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			deviceSummary.setTime(rstLocationListData.get(CNLocationList.LOCATION_NAME),
												rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION),
												rstV5DeviceData.get(CNV5Device.TIMEOUT_POPUP),
												rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			List<String> language = Arrays.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			locationSummary.kiosklanguageSetting(rstLocationListData.get(CNLocationList.LOCATION_NAME),language.get(0),language.get(1));
			
			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			foundation.click(landingPage.objLanguage(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA)));
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
			foundation.click(ProductSearch.BTN_PRODUCT);
			Assert.assertTrue(foundation.isDisplayed(Order.BTN_CANCEL_ORDER));
			foundation.waitforElement(Order.POP_UP_LBL_ORDER_TIMEOUT, Constants.EXTRA_LONG_TIME);
			foundation.click(Order.POP_UP_TIMEOUT_YES);
			Assert.assertTrue(foundation.isDisplayed(Order.LBL_YOUR_ORDER));

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	@Test(description = "142665- This test validates the No button functionality on Order Screen")
	public void verifyNoButtonFunctionality() {
		try {

			final String CASE_NUM = "142665";

			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);

			browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
							propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			deviceSummary.setTime(rstLocationListData.get(CNLocationList.LOCATION_NAME),
												rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION),
												rstV5DeviceData.get(CNV5Device.TIMEOUT_POPUP),
												rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			List<String> language = Arrays.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			locationSummary.kiosklanguageSetting(rstLocationListData.get(CNLocationList.LOCATION_NAME),language.get(0),language.get(1));
			foundation.threadWait(Constants.SHORT_TIME);

			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			foundation.click(landingPage.objLanguage(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA)));
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
			foundation.click(ProductSearch.BTN_PRODUCT);
			Assert.assertTrue(foundation.isDisplayed(Order.BTN_CANCEL_ORDER));

			foundation.waitforElement(Order.POP_UP_LBL_ORDER_TIMEOUT, Constants.LONG_TIME);
			foundation.click(Order.POP_UP_TIMEOUT_NO);
			Assert.assertTrue(foundation.isDisplayed(LandingPage.IMG_SEARCH_ICON));

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	@Test(description = "142666- This test validates the Order Time out prompt will disapper after 5 sec")
	public void verifyPromptAfterTimeOut() {
		try {

			final String CASE_NUM = "142666";

			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);

			browser.navigateURL(	propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
							propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			deviceSummary.setTime(rstLocationListData.get(CNLocationList.LOCATION_NAME),
												rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION),
												rstV5DeviceData.get(CNV5Device.TIMEOUT_POPUP),
												rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			List<String> language = Arrays.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			locationSummary.kiosklanguageSetting(rstLocationListData.get(CNLocationList.LOCATION_NAME),language.get(0),language.get(1));
			foundation.threadWait(Constants.SHORT_TIME);

			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			foundation.click(landingPage.objLanguage(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA)));
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
			foundation.click(ProductSearch.BTN_PRODUCT);
			Assert.assertTrue(foundation.isDisplayed(Order.BTN_CANCEL_ORDER));
			foundation.waitforElement(Order.POP_UP_LBL_ORDER_TIMEOUT, Constants.LONG_TIME);
			Assert.assertTrue(foundation.isDisplayed(Order.POP_UP_LBL_ORDER_TIMEOUT));
			foundation.waitforElement(LandingPage.IMG_SEARCH_ICON, Constants.SHORT_TIME);
			Assert.assertTrue(foundation.isDisplayed(LandingPage.IMG_SEARCH_ICON));

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}

	}

	@Test(description = "142680- This test validates the Order Timeout Prompt when user not  perform any action")
	public void verifyOrderTimePromptDetails() {
		try {

			final String CASE_NUM = "142680";

			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);

			browser.navigateURL(	propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
							propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			deviceSummary.setTime(rstLocationListData.get(CNLocationList.LOCATION_NAME),
												rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION),
												rstV5DeviceData.get(CNV5Device.TIMEOUT_POPUP),
												rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			List<String> language = Arrays.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			locationSummary.kiosklanguageSetting(rstLocationListData.get(CNLocationList.LOCATION_NAME),language.get(0),language.get(1));
			foundation.threadWait(Constants.SHORT_TIME);

			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(landingPage.objLanguage(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA)));
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
			foundation.click(ProductSearch.BTN_PRODUCT);
			Assert.assertTrue(foundation.isDisplayed(Order.BTN_CANCEL_ORDER));
			foundation.waitforElement(Order.POP_UP_LBL_ORDER_TIMEOUT, Constants.EXTRA_LONG_TIME);
			Assert.assertTrue(foundation.isDisplayed(Order.POP_UP_LBL_ORDER_TIMEOUT));
			Assert.assertTrue(foundation.isDisplayed(Order.POP_UP_LBL_ORDER_TIMEOUT_MSG));
			Assert.assertTrue(foundation.isDisplayed(Order.POP_UP_TIMEOUT_YES));
			Assert.assertTrue(foundation.isDisplayed(Order.POP_UP_TIMEOUT_NO));

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}

	}

	@Test(description = "142729- This test validates the Order time out prompt when user decreases the time below 20 sec")
	public void verifyPromptWithBelow20Sec() {
		try {

			final String CASE_NUM = "142729";

			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);

			browser.navigateURL(	propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
							 propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			deviceSummary.setTime(rstLocationListData.get(CNLocationList.LOCATION_NAME),
					rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION),
					rstV5DeviceData.get(CNV5Device.TIMEOUT_POPUP),
					rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			
			List<String> language = Arrays.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			locationSummary.kiosklanguageSetting(rstLocationListData.get(CNLocationList.LOCATION_NAME),language.get(0),language.get(1));
			
			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(landingPage.objLanguage(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA)));
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
			foundation.click(ProductSearch.BTN_PRODUCT);
			Assert.assertTrue(foundation.isDisplayed(Order.BTN_CANCEL_ORDER));
			foundation.threadWait(Constants.FIFTEEN_SECOND);
			Assert.assertTrue(foundation.isDisplayed(Order.LBL_YOUR_ORDER));

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	@Test(description = "141863-Kiosk Account Login > Email")
	public void verifyLoginScreen() {
		try {
			
			final String CASE_NUM = "141863";	
			
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
			
			browser.navigateURL(	propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
							 propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			List<String> language = Arrays.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
			locationSummary.kiosklanguageSetting(rstLocationListData.get(CNLocationList.LOCATION_NAME),language.get(0),language.get(1));
			
			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(landingPage.objLanguage(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA)));
			foundation.click(LandingPage.BTN_LOGIN);
			foundation.click(AccountLogin.BTN_EMAIL_LOGIN);
			accountLogin.login(rstV5DeviceData.get(CNV5Device.EMAIL_ID), rstV5DeviceData.get(CNV5Device.PIN));
			assertTrue(foundation.isDisplayed(UserProfile.BTN_PRIVACY));

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	@Test(description = "141886-Kiosk Checkout UI > Cart Contents")
	public void verifyOrderScreen() {
		try {
			final String CASE_NUM = "141886";
			
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);			
			browser.navigateURL(	propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
							 propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			List<String> language = Arrays.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
			locationSummary.kiosklanguageSetting(rstLocationListData.get(CNLocationList.LOCATION_NAME),language.get(0),language.get(1));		
			
			List<String> requiredData = Arrays.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> actualData = Arrays.asList(rstV5DeviceData.get(CNV5Device.ACTUAL_DATA).split(Constants.DELIMITER_TILD));
			
			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(landingPage.objLanguage(requiredData.get(0)));
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText((requiredData.get(1)));
			foundation.click(LandingPage.BTN_PRODUCT);
			String actualHeader = foundation.getText(LandingPage.TXT_HEADER);
			assertEquals(actualHeader, actualData.get(1));
			String actualProduct = foundation.getText(LandingPage.TXT_PRODUCT);
			assertEquals(actualProduct, actualData.get(0));

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	@Test(description = "142690-SOS-24493-Verify added Home Commercial Image(JPG) is displayed on V5 Device")
	public void verifyHomeCommercialJPG() {
		try {
			final String CASE_NUM = "142690";

			browser.navigateURL(	propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
							propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
		
			// Reading test data from DataBase
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
			String locationName = rstLocationListData.get(CNLocationList.LOCATION_NAME);
			final String imageName = string.getRandomCharacter();
			String requiredData = rstV5DeviceData.get(CNV5Device.REQUIRED_DATA);
			List<String> language = Arrays.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
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
			assertEquals(actualData, requiredData);

			// resetting test data
			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(	propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			// Selecting Device's location
			locationList.selectLocationName(locationName);
			locationSummary.removeHomeCommercial(imageName);

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	@Test(description = "142691-SOS-24493-Verify added Home Commercial Image(PNG) is displayed on V5 Device")
	public void verifyHomeCommercialPNG() {
		try {
			final String CASE_NUM = "142691";

			browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			
			// Reading test data from DataBase
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
			String locationName = rstLocationListData.get(CNLocationList.LOCATION_NAME);
			final String imageName = string.getRandomCharacter();
			String requiredData = rstV5DeviceData.get(CNV5Device.REQUIRED_DATA);
			List<String> language = Arrays.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
			
			locationList.selectLocationName(locationName);
			locationSummary.addHomeCommercial(imageName, FilePath.IMAGE_PNG_PATH);
			locationSummary.kiosklanguageSetting(locationName, language.get(0), language.get(1));					
			
			// launching v5 device
			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			foundation.waitforElement(landingPage.objImageDisplay(requiredData), Constants.MEDIUM_TIME);
			String actualData = foundation.getTextAttribute(LandingPage.LNK_IMAGE, Constants.SRC);
			// Home Commercial image validation
			assertEquals(actualData, requiredData);

			// resetting test data
			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(	propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			locationList.selectLocationName(locationName);
			locationSummary.removeHomeCommercial(imageName);

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	@Test(description = "142692-SOS-24493-Verify removed Home Commercial Image(JPG) is not display in V5 Device.")
	public void verifyRemoveHomeCommercialJPG() {
		try {
			final String CASE_NUM = "142692";

			browser.navigateURL(	propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
							propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			
			// Reading test data from DataBase
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
			String locationName = rstLocationListData.get(CNLocationList.LOCATION_NAME);
			final String imageName = string.getRandomCharacter();
			String requiredData = rstV5DeviceData.get(CNV5Device.REQUIRED_DATA);
			List<String> language = Arrays.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));

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
			assertEquals(actualData, requiredData);
			browser.close();
			
			// Remove home commercial
			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
							propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			locationList.selectLocationName(locationName);
			locationSummary.removeHomeCommercial(imageName);
			login.logout();
			browser.close();

			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			assertFalse(foundation.isDisplayed(landingPage.objImageDisplay(requiredData)));

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	@Test(description = "142693-SOS-24493-Verify removed Home Commercial Image(PNG) is not display in V5 Device.")
	public void verifyRemoveHomeCommercialPNG() {
		try {
			final String CASE_NUM = "142693";

			browser.navigateURL(	propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
							propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			
			// Reading test data from DataBase
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
			String locationName = rstLocationListData.get(CNLocationList.LOCATION_NAME);
			final String imageName = string.getRandomCharacter();
			String requiredData = rstV5DeviceData.get(CNV5Device.REQUIRED_DATA);
			List<String> language = Arrays.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));

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
			assertEquals(actualData, requiredData);
			browser.close();
			
			// Remove home commercial
			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(	propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			locationList.selectLocationName(locationName);
			locationSummary.removeHomeCommercial(imageName);
			login.logout();
			browser.close();

			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			assertFalse(foundation.isDisplayed(landingPage.objImageDisplay(requiredData)));

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	@Test(description = "142694-SOS-24493- verify default home commercial image is displayed when all the existing images are removed from location summary in Adm")
	public void verifyDefaultHomeCommercial() {
		try {
			final String CASE_NUM = "142694";

			browser.navigateURL(	propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
			String locationName = rstLocationListData.get(CNLocationList.LOCATION_NAME);
			final String cmrName = string.getRandomCharacter();
			List<String> requiredData = Arrays.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> language = Arrays.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));

			locationList.selectLocationName(locationName);
			locationSummary.addHomeCommercial(cmrName, FilePath.IMAGE_PNG_PATH);
			locationSummary.kiosklanguageSetting(locationName, language.get(0), language.get(1));	
			
			// launching v5 device
			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			foundation.waitforElement(landingPage.objImageDisplay(requiredData.get(1)), Constants.MEDIUM_TIME);
			String actualData = foundation.getTextAttribute(LandingPage.LNK_IMAGE, Constants.VALUE);
			assertEquals(actualData, requiredData.get(1));
			browser.close();
			
			// Remove home commercial
			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(	propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			locationList.selectLocationName(locationName);
			locationSummary.removeHomeCommercial(cmrName);
			login.logout();
			browser.close();
			
			// v5 device
			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			foundation.waitforElement(landingPage.objImageDisplay(requiredData.get(0)), Constants.MEDIUM_TIME);
			assertTrue(foundation.isDisplayed(landingPage.objImageDisplay(requiredData.get(0))));
			assertFalse(foundation.isDisplayed(landingPage.objImageDisplay(requiredData.get(1))));

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	@Test(description = "142695-SOS-24493-verify multiple home commercials are displayed on V5 Device when multiple homecommercials are uploaded in Adm")
	public void verifyMultipleHomeCommercial() {
		try {
			final String CASE_NUM = "142695";

			browser.navigateURL(	propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			
			// Reading test data from DataBase
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
			String locationName = rstLocationListData.get(CNLocationList.LOCATION_NAME);
			final String cmrName1 = string.getRandomCharacter();
			final String cmrName2 = string.getRandomCharacter();
			List<String> requiredData = Arrays.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> language = Arrays.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
			
			// Selecting location
			locationList.selectLocationName(locationName);
			locationSummary.addHomeCommercial(cmrName1, FilePath.IMAGE_PATH);
			locationSummary.addHomeCommercial(cmrName2, FilePath.IMAGE_PNG_PATH);
			locationSummary.kiosklanguageSetting(locationName, language.get(0), language.get(1));	
			
			// launching v5 device
			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			foundation.waitforElement(landingPage.objImageDisplay(requiredData.get(0)), Constants.MEDIUM_TIME);
			assertTrue(foundation.isDisplayed(landingPage.objImageDisplay(requiredData.get(0))));
			foundation.waitforElement(landingPage.objImageDisplay(requiredData.get(1)), Constants.MEDIUM_TIME);
			assertTrue(foundation.isDisplayed(landingPage.objImageDisplay(requiredData.get(1))));

			// resetting test data
			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			locationList.selectLocationName(locationName);
			locationSummary.removeHomeCommercial(cmrName1);
			locationSummary.removeHomeCommercial(cmrName2);

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	@Test(description = "142720-SOS-24493-Verify error message is displayed when home commercial image is uploaded in JSON format")
	public void verifyHomeCommercialJSON() {
		try {
			final String CASE_NUM = "142720";

			browser.navigateURL(	propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			
			// Reading test data from DataBase
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
			String locationName = rstLocationListData.get(CNLocationList.LOCATION_NAME);
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
			assertEquals(expectedData, actualData);

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	@Test(description = "142723-SOS-24493-Verify error message is displayed when home commercial image is uploaded in Text format")
	public void verifyHomeCommercialText() {
		try {
			final String CASE_NUM = "142723";

			browser.navigateURL(	propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			
			// Reading test data from DataBase
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
			String locationName = rstLocationListData.get(CNLocationList.LOCATION_NAME);
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
			assertEquals(expectedData, actualData);

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	@Test(description = "142724-SOS-24493-Verify error message is displayed when uploaded home commercial image size is more than 2MB")
	public void verifyHomeCommercialImageSize() {
		try {
			final String CASE_NUM = "142724";

			browser.navigateURL(	propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			
			// Reading test data from DataBase
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
			String locationName = rstLocationListData.get(CNLocationList.LOCATION_NAME);
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
			String expectedData = foundation.getText(LocationSummary.TXT_UPLOAD_STATUS);
			assertEquals(expectedData, actualData);

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	@Test(description = "142725-SOS-24493-Verify error message is displayed when uploaded home commercial image pixel length is greater than 1024x520")
	public void verifyHomeCommercialPixelLength() {
		try {
			final String CASE_NUM = "142725";

			browser.navigateURL(	propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			// Reading test data from DataBase
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
			String locationName = rstLocationListData.get(CNLocationList.LOCATION_NAME);
			String actualData = rstV5DeviceData.get(CNV5Device.ACTUAL_DATA);

			// Selecting location
			textBox.enterText(LocationList.TXT_FILTER, locationName);
			locationList.selectLocationName(locationName);

			// upload image-1
			foundation.waitforElement(LocationSummary.BTN_HOME_COMMERCIAL, Constants.SHORT_TIME);
			foundation.click(LocationSummary.BTN_HOME_COMMERCIAL);
			foundation.click(LocationSummary.BTN_ADD_HOME_COMMERCIAL);
			foundation.click(LocationSummary.TXT_UPLOAD_NEW);
			textBox.enterText(LocationSummary.BTN_UPLOAD_INPUT, FilePath.IMAGE_PIXEL_SIZE);
			foundation.waitforElement(locationSummary.objUploadStatus(actualData), Constants.SHORT_TIME);
			String expectedData = foundation.getText(LocationSummary.TXT_UPLOAD_STATUS);
			assertEquals(expectedData, actualData);

		} catch (Exception exc) {
			Assert.fail(exc.toString());
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

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Selecting location
			locationList.selectLocationName(requiredData.get(2));

			dropDown.selectItem(LocationSummary.DPD_KIOSK_LANGUAGE, requiredData.get(0), Constants.TEXT);
			dropDown.selectItem(LocationSummary.DPD_ALTERNATE_LANGUAGE, requiredData.get(1), Constants.TEXT);

			foundation.click(LocationSummary.BTN_SYNC);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			login.logout();
			browser.close();

			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));

			foundation.click(landingPage.objLanguage(requiredData.get(0)));
			foundation.waitforElement(landingPage.objText(actualData.get(0)), Constants.MEDIUM_TIME);
			String actualLanguage = foundation.getText(LandingPage.LBL_HEADER);
			assertEquals(actualLanguage, actualData.get(0));
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(landingPage.objLanguage(requiredData.get(1)));
			foundation.waitforElement(landingPage.objText(actualData.get(1)), Constants.SHORT_TIME);
			actualLanguage = foundation.getText(LandingPage.LBL_HEADER);
			assertEquals(actualLanguage, actualData.get(1));
			browser.close();
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	@Test(description = "141881- Kiosk Privacy Policy (if applicable)")
	public void verifyPrivacyPolicy() {
		try {
			final String CASE_NUM = "141881";
						
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
			browser.navigateURL(	propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			
			List<String> language = Arrays.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
			locationSummary.kiosklanguageSetting(rstLocationListData.get(CNLocationList.LOCATION_NAME), language.get(0), language.get(1));	
			
			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(landingPage.objLanguage( language.get(0)));
			foundation.click(LandingPage.BTN_LOGIN);
			foundation.click(AccountLogin.BTN_EMAIL_LOGIN);
			accountLogin.login(rstV5DeviceData.get(CNV5Device.EMAIL_ID), rstV5DeviceData.get(CNV5Device.PIN));

			foundation.click(UserProfile.BTN_PRIVACY);
			String title = foundation.getText(Policy.LBL_POLICY_TITLE);
			Assert.assertTrue(title.equals(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA)));
			foundation.click(Policy.BTN_OK);

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	@Test(description = "141883- Kiosk Terms and Conditions (if applicable)")
	public void verifyTermsAndCondition() {
		try {
			final String CASE_NUM = "141883";
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
			
			browser.navigateURL(	propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			
			List<String> language = Arrays.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
			locationSummary.kiosklanguageSetting(rstLocationListData.get(CNLocationList.LOCATION_NAME), language.get(0), language.get(1));	
			
			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(landingPage.objLanguage(language.get(0)));
			foundation.refreshPage();
			foundation.click(LandingPage.BTN_LOGIN);
			foundation.click(AccountLogin.BTN_EMAIL_LOGIN);
			accountLogin.login(propertyFile.readPropertyFile(Configuration.V5_USER, FilePath.PROPERTY_CONFIG_FILE),
										propertyFile.readPropertyFile(Configuration.V5_PIN, FilePath.PROPERTY_CONFIG_FILE));

			foundation.click(UserProfile.BTN_TERMS_CONDITION);
			String title = foundation.getText(Policy.LBL_TERMS_CONDITION_TITLE);
			Assert.assertTrue(title.equals(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA)));
			foundation.click(Policy.BTN_OK);

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	@Test(description = "142667- SOS-24492- Kiosk Language selection")
	public void englishDefaultLanguage() {
		try {
			final String CASE_NUM = "142667";
			// Reading test data from DataBase
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			List<String> requiredData = Arrays.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));

			browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Selecting location
			locationList.selectLocationName(requiredData.get(0));
			dropDown.selectItem(LocationSummary.DPD_KIOSK_LANGUAGE, requiredData.get(1), Constants.TEXT);
			dropDown.selectItem(LocationSummary.DPD_ALTERNATE_LANGUAGE, requiredData.get(2), Constants.TEXT);

			foundation.click(LocationSummary.BTN_SYNC);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_FILTER, Constants.SHORT_TIME);
			login.logout();
			browser.close();

			foundation.threadWait(Constants.SHORT_TIME);
			// login into Kiosk Device
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));

			// Validating Landing Page
			foundation.waitforElement(landingPage.objLanguage(requiredData.get(3)), Constants.SHORT_TIME);
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
			Assert.assertTrue(
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
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.EMAIL_ID));
			foundation.click(AccountLogin.BTN_NEXT);
			foundation.waitforElement(AccountLogin.BTN_PIN_NEXT, Constants.SHORT_TIME);
			textBox.enterPin(rstV5DeviceData.get(CNV5Device.PIN));
			foundation.click(AccountLogin.BTN_PIN_NEXT);
			payments.verifyPaymentPageLanguage(rstV5DeviceData.get(CNV5Device.PAYMENTS_PAGE));
			browser.close();

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
			locationList.selectLocationName(requiredData.get(0));

			dropDown.selectItem(LocationSummary.DPD_KIOSK_LANGUAGE, requiredData.get(4), Constants.TEXT);
			dropDown.selectItem(LocationSummary.DPD_ALTERNATE_LANGUAGE, requiredData.get(5), Constants.TEXT);

			foundation.click(LocationSummary.BTN_SYNC);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_FILTER, Constants.SHORT_TIME);
			login.logout();

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	@Test(description = "142733- SOS-24492 Verify alternate language is set to Italian in Kiosk when user set the Alternate Language as italian and full sync is done in ADM")
	public void alternateItalianLanguage() {
		try {
			final String CASE_NUM = "142733";
			// Reading test data from DataBase
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			List<String> requiredData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Selecting location
			locationList.selectLocationName(requiredData.get(0));

			dropDown.selectItem(LocationSummary.DPD_KIOSK_LANGUAGE, requiredData.get(1), Constants.TEXT);
			dropDown.selectItem(LocationSummary.DPD_ALTERNATE_LANGUAGE, requiredData.get(2), Constants.TEXT);

			foundation.click(LocationSummary.BTN_SYNC);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_FILTER, Constants.SHORT_TIME);
			login.logout();
			browser.close();

			foundation.threadWait(Constants.SHORT_TIME);
			// login into Kiosk Device
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));

			foundation.click(LandingPage.BTN_LANG);
			// Validating Landing Page
			foundation.waitforElement(landingPage.objLanguage(requiredData.get(3)), Constants.SHORT_TIME);
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
			Assert.assertTrue(
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
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.EMAIL_ID));
			foundation.click(AccountLogin.BTN_NEXT);
			foundation.waitforElement(AccountLogin.BTN_PIN_NEXT, Constants.SHORT_TIME);
			textBox.enterPin(rstV5DeviceData.get(CNV5Device.PIN));
			foundation.click(AccountLogin.BTN_PIN_NEXT);
			payments.verifyPaymentPageLanguage(rstV5DeviceData.get(CNV5Device.PAYMENTS_PAGE));
			browser.close();

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
			locationList.selectLocationName(requiredData.get(0));

			dropDown.selectItem(LocationSummary.DPD_KIOSK_LANGUAGE, requiredData.get(4), Constants.TEXT);
			dropDown.selectItem(LocationSummary.DPD_ALTERNATE_LANGUAGE, requiredData.get(5), Constants.TEXT);

			foundation.click(LocationSummary.BTN_SYNC);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_FILTER, Constants.SHORT_TIME);
			login.logout();

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}

	}

	@Test(description = "142734- SOS-24492 Verify default language is set to french in Kiosk when user set the Default Language as french and full sync is done in ADM")
	public void frenchDefaultLanguage() {
		try {
			final String CASE_NUM = "142734";
			// Reading test data from DataBase
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			List<String> requiredData = Arrays.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));

			browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Selecting location
			locationList.selectLocationName(requiredData.get(0));
			dropDown.selectItem(LocationSummary.DPD_KIOSK_LANGUAGE, requiredData.get(1), Constants.TEXT);
			dropDown.selectItem(LocationSummary.DPD_ALTERNATE_LANGUAGE, requiredData.get(2), Constants.TEXT);
			foundation.click(LocationSummary.BTN_SYNC);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_FILTER, Constants.SHORT_TIME);
			login.logout();
			browser.close();

			foundation.threadWait(Constants.SHORT_TIME);
			// login into Kiosk Device
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));

			// Validating Landing Page
			foundation.waitforElement(landingPage.objLanguage(requiredData.get(3)), Constants.SHORT_TIME);
			landingPage.verifyHomeScreenLanguage(rstV5DeviceData.get(CNV5Device.LANDING_PAGE));

			// Validating Search Page
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			productSearch.verifyProductSearhPageLanguage(rstV5DeviceData.get(CNV5Device.PRODUCT_SEARCH_PAGE));

			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
			foundation.click(ProductSearch.BTN_PRODUCT);

			// verify Order Page
			List<String> orderPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.ORDER_PAGE).split(Constants.DELIMITER_TILD));
			order.verifyOrderPageLanguage(rstV5DeviceData.get(CNV5Device.ORDER_PAGE));

			// Validating Credit/Debit Page
			foundation.click(order.objText(orderPageData.get(8)));
			cardPayment.verifyCardPaymentPageLanguage(rstV5DeviceData.get(CNV5Device.CREDIT_DEBIT_PAGE));
			foundation.click(CardPayment.BTN_CLOSE);
			foundation.waitforElement(order.objText(orderPageData.get(1)), Constants.SHORT_TIME);

			// verify Cancel Order Page
			foundation.click(order.objText(orderPageData.get(0)));
			Assert.assertTrue(	foundation.isDisplayed(createAccount.objText(rstV5DeviceData.get(CNV5Device.TRANSACTION_CANCEL))));

			// Validating Create Account Page
			foundation.waitforElement(LandingPage.BTN_CREATE_ACCOUNT, Constants.SHORT_TIME);
			foundation.click(LandingPage.BTN_CREATE_ACCOUNT);
			createAccount.verifyCreateAccoutnPageLanguage(rstV5DeviceData.get(CNV5Device.CREATE_ACCOUNT),requiredData.get(3), rstV5DeviceData.get(CNV5Device.ACTUAL_DATA));

			// Validating Account Login Page
			foundation.click(LandingPage.BTN_LOGIN);
			accountLogin.verifyAccountLoginPageLanguage(rstV5DeviceData.get(CNV5Device.LOGIN_PAGE));

			textBox.enterPin(propertyFile.readPropertyFile(Configuration.V5_PIN, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(AccountLogin.BTN_PIN_NEXT);

			// Verifying Account info page
			List<String> accountPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.ACCOUNT_DETAILS).split(Constants.DELIMITER_TILD));
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
			List<String> accountEditPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.EDIT_ACCOUNT_DETAILS).split(Constants.DELIMITER_TILD));
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
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.EMAIL_ID));
			foundation.click(AccountLogin.BTN_NEXT);
			foundation.waitforElement(AccountLogin.BTN_PIN_NEXT, Constants.SHORT_TIME);
			textBox.enterPin(rstV5DeviceData.get(CNV5Device.PIN));
			foundation.click(AccountLogin.BTN_PIN_NEXT);
			payments.verifyPaymentPageLanguage(rstV5DeviceData.get(CNV5Device.PAYMENTS_PAGE));
			browser.close();

			// Resetting the data
			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Selecting location
			locationList.selectLocationName(requiredData.get(0));
			dropDown.selectItem(LocationSummary.DPD_KIOSK_LANGUAGE, requiredData.get(4), Constants.TEXT);
			dropDown.selectItem(LocationSummary.DPD_ALTERNATE_LANGUAGE, requiredData.get(5), Constants.TEXT);
			foundation.click(LocationSummary.BTN_SYNC);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_FILTER, Constants.SHORT_TIME);
			login.logout();

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	@Test(description = "142735- SOS-24492 Verify default language is set to Dutch in Kiosk when user set the Default Language as Dutch and full sync is done in ADM")
	public void dutchDefaultLanguage() {
		try {
			final String CASE_NUM = "142735";
			
			// Reading test data from DataBase
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			List<String> requiredData = Arrays.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));

			browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Selecting location
			locationList.selectLocationName(requiredData.get(0));
			dropDown.selectItem(LocationSummary.DPD_KIOSK_LANGUAGE, requiredData.get(1), Constants.TEXT);
			dropDown.selectItem(LocationSummary.DPD_ALTERNATE_LANGUAGE, requiredData.get(2), Constants.TEXT);

			foundation.click(LocationSummary.BTN_SYNC);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_FILTER, Constants.SHORT_TIME);
			login.logout();
			browser.close();

			foundation.threadWait(Constants.SHORT_TIME);
			// login into Kiosk Device
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));

			// Validating Landing Page
			foundation.waitforElement(landingPage.objLanguage(requiredData.get(3)), Constants.SHORT_TIME);
			landingPage.verifyHomeScreenLanguage(rstV5DeviceData.get(CNV5Device.LANDING_PAGE));

			// Validating Search Page
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			productSearch.verifyProductSearhPageLanguage(rstV5DeviceData.get(CNV5Device.PRODUCT_SEARCH_PAGE));

			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
			foundation.click(ProductSearch.BTN_PRODUCT);

			// verify Order Page
			List<String> orderPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.ORDER_PAGE).split(Constants.DELIMITER_TILD));
			order.verifyOrderPageLanguage(rstV5DeviceData.get(CNV5Device.ORDER_PAGE));

			// Validating Credit/Debit Page
			foundation.click(order.objText(orderPageData.get(8)));
			cardPayment.verifyCardPaymentPageLanguage(rstV5DeviceData.get(CNV5Device.CREDIT_DEBIT_PAGE));

			foundation.click(CardPayment.BTN_CLOSE);
			foundation.waitforElement(order.objText(orderPageData.get(1)), Constants.SHORT_TIME);

			// verify Cancel Order Page
			foundation.click(order.objText(orderPageData.get(0)));
			Assert.assertTrue(	foundation.isDisplayed(createAccount.objText(rstV5DeviceData.get(CNV5Device.TRANSACTION_CANCEL))));

			// Validating Create Account Page
			foundation.waitforElement(LandingPage.BTN_CREATE_ACCOUNT, Constants.SHORT_TIME);
			foundation.click(LandingPage.BTN_CREATE_ACCOUNT);
			createAccount.verifyCreateAccoutnPageLanguage(rstV5DeviceData.get(CNV5Device.CREATE_ACCOUNT),requiredData.get(3), rstV5DeviceData.get(CNV5Device.ACTUAL_DATA));

			// Validating Account Login Page
			foundation.click(LandingPage.BTN_LOGIN);
			accountLogin.verifyAccountLoginPageLanguage(rstV5DeviceData.get(CNV5Device.LOGIN_PAGE));

			textBox.enterPin(propertyFile.readPropertyFile(Configuration.V5_PIN, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(AccountLogin.BTN_PIN_NEXT);

			// Verifying Account info page
			List<String> accountPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.ACCOUNT_DETAILS).split(Constants.DELIMITER_TILD));
			accountDetails.verifyAccountDetailsPageLanguage(rstV5DeviceData.get(CNV5Device.ACCOUNT_DETAILS),requiredData.get(3), rstV5DeviceData.get(CNV5Device.ACTUAL_DATA));

			// Verifying Fund with card page
			foundation.click(fundAccount.objText(accountPageData.get(1)));
			fundAccount.verifyFundAccountScreenLanguage(rstV5DeviceData.get(CNV5Device.FUND_ACCOUNT_PAGE));

			// Verifying Scan Setup page
			foundation.click(fundAccount.objText(accountPageData.get(4)));
			scanPayment.verifyScanPaymentPageLanguage(rstV5DeviceData.get(CNV5Device.QUICK_SCAN_SETUP),requiredData.get(3), rstV5DeviceData.get(CNV5Device.ACTUAL_DATA));

			// Verifying Finger print Setup page
			foundation.click(createAccount.objText(accountPageData.get(6)));
			fingerPrintPayment.verifyFingerPrintPaymentPageLanguage(rstV5DeviceData.get(CNV5Device.FINGER_PRINT_SETUP));

			// Verifying Edit account page
			foundation.click(createAccount.objText(accountPageData.get(7)));
			editAccount.verifyEditAccountPageLanguage(rstV5DeviceData.get(CNV5Device.EDIT_ACCOUNT_DETAILS));

			// verify Change pin
			List<String> accountEditPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.EDIT_ACCOUNT_DETAILS).split(Constants.DELIMITER_TILD));
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
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.EMAIL_ID));
			foundation.click(AccountLogin.BTN_NEXT);
			foundation.waitforElement(AccountLogin.BTN_PIN_NEXT, Constants.SHORT_TIME);
			textBox.enterPin(rstV5DeviceData.get(CNV5Device.PIN));
			foundation.click(AccountLogin.BTN_PIN_NEXT);
			payments.verifyPaymentPageLanguage(rstV5DeviceData.get(CNV5Device.PAYMENTS_PAGE));
			browser.close();

			// resetting the data
			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
							propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Selecting location
			locationList.selectLocationName(requiredData.get(0));

			dropDown.selectItem(LocationSummary.DPD_KIOSK_LANGUAGE, requiredData.get(4), Constants.TEXT);
			dropDown.selectItem(LocationSummary.DPD_ALTERNATE_LANGUAGE, requiredData.get(5), Constants.TEXT);

			foundation.click(LocationSummary.BTN_SYNC);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_FILTER, Constants.SHORT_TIME);
			login.logout();

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	@Test(description = "142736- SOS-24492 Verify default language is set to Swedish in Kiosk when user set the Default Language as Swedish and full sync is done in ADM")
	public void swedishDefaultLanguage() {
		try {
			final String CASE_NUM = "142736";
			// Reading test data from DataBase
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			List<String> requiredData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Selecting location
			locationList.selectLocationName(requiredData.get(0));

			dropDown.selectItem(LocationSummary.DPD_KIOSK_LANGUAGE, requiredData.get(1), Constants.TEXT);
			dropDown.selectItem(LocationSummary.DPD_ALTERNATE_LANGUAGE, requiredData.get(2), Constants.TEXT);

			foundation.click(LocationSummary.BTN_SYNC);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_FILTER, Constants.SHORT_TIME);
			login.logout();
			browser.close();

			foundation.threadWait(Constants.SHORT_TIME);
			// login into Kiosk Device
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));

			// Validating Landing Page
			foundation.waitforElement(landingPage.objLanguage(requiredData.get(3)), Constants.SHORT_TIME);
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
			Assert.assertTrue(
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
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.EMAIL_ID));
			foundation.click(AccountLogin.BTN_NEXT);
			foundation.waitforElement(AccountLogin.BTN_PIN_NEXT, Constants.SHORT_TIME);
			textBox.enterPin(rstV5DeviceData.get(CNV5Device.PIN));
			foundation.click(AccountLogin.BTN_PIN_NEXT);
			payments.verifyPaymentPageLanguage(rstV5DeviceData.get(CNV5Device.PAYMENTS_PAGE));
			browser.close();

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
			locationList.selectLocationName(requiredData.get(0));

			dropDown.selectItem(LocationSummary.DPD_KIOSK_LANGUAGE, requiredData.get(4), Constants.TEXT);
			dropDown.selectItem(LocationSummary.DPD_ALTERNATE_LANGUAGE, requiredData.get(5), Constants.TEXT);

			foundation.click(LocationSummary.BTN_SYNC);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_FILTER, Constants.SHORT_TIME);
			login.logout();

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	@Test(description = "142737- SOS-24492 Verify default language is set to Norwegian in Kiosk when user set the Default Language as Norwegian and full sync is done in ADM")
	public void norwegianDefaultLanguage() {
		try {
			final String CASE_NUM = "142737";
			// Reading test data from DataBase
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			List<String> requiredData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Selecting location
			locationList.selectLocationName(requiredData.get(0));

			dropDown.selectItem(LocationSummary.DPD_KIOSK_LANGUAGE, requiredData.get(1), Constants.TEXT);
			dropDown.selectItem(LocationSummary.DPD_ALTERNATE_LANGUAGE, requiredData.get(2), Constants.TEXT);

			foundation.click(LocationSummary.BTN_SYNC);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_FILTER, Constants.SHORT_TIME);
			login.logout();
			browser.close();

			foundation.threadWait(Constants.SHORT_TIME);
			// login into Kiosk Device
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));

			// Validating Landing Page
			foundation.waitforElement(landingPage.objLanguage(requiredData.get(3)), Constants.SHORT_TIME);
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
			Assert.assertTrue(
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
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.EMAIL_ID));
			foundation.click(AccountLogin.BTN_NEXT);
			foundation.waitforElement(AccountLogin.BTN_PIN_NEXT, Constants.SHORT_TIME);
			textBox.enterPin(rstV5DeviceData.get(CNV5Device.PIN));
			foundation.click(AccountLogin.BTN_PIN_NEXT);
			payments.verifyPaymentPageLanguage(rstV5DeviceData.get(CNV5Device.PAYMENTS_PAGE));
			browser.close();

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
			locationList.selectLocationName(requiredData.get(0));

			dropDown.selectItem(LocationSummary.DPD_KIOSK_LANGUAGE, requiredData.get(4), Constants.TEXT);
			dropDown.selectItem(LocationSummary.DPD_ALTERNATE_LANGUAGE, requiredData.get(5), Constants.TEXT);

			foundation.click(LocationSummary.BTN_SYNC);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_FILTER, Constants.SHORT_TIME);
			login.logout();

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	@Test(description = "142738- SOS-24492 Verify alternate language is set to spanish in Kiosk when user set the Alternate Language as spanish and full sync is done in ADM")
	public void alternateSpanishLanguage() {
		try {
			final String CASE_NUM = "142738";
			// Reading test data from DataBase
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			List<String> requiredData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Selecting location
			locationList.selectLocationName(requiredData.get(0));

			dropDown.selectItem(LocationSummary.DPD_KIOSK_LANGUAGE, requiredData.get(1), Constants.TEXT);
			dropDown.selectItem(LocationSummary.DPD_ALTERNATE_LANGUAGE, requiredData.get(2), Constants.TEXT);

			foundation.click(LocationSummary.BTN_SYNC);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			login.logout();
			browser.close();

			foundation.threadWait(Constants.SHORT_TIME);
			// login into Kiosk Device
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(LandingPage.BTN_LANG);

			// Validating Landing Page
			foundation.waitforElement(landingPage.objLanguage(requiredData.get(3)), Constants.SHORT_TIME);
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
			Assert.assertTrue(
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
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.EMAIL_ID));
			foundation.click(AccountLogin.BTN_NEXT);
			foundation.waitforElement(AccountLogin.BTN_PIN_NEXT, Constants.SHORT_TIME);
			textBox.enterPin(rstV5DeviceData.get(CNV5Device.PIN));
			foundation.click(AccountLogin.BTN_PIN_NEXT);
			payments.verifyPaymentPageLanguage(rstV5DeviceData.get(CNV5Device.PAYMENTS_PAGE));
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
			locationList.selectLocationName(requiredData.get(0));

			dropDown.selectItem(LocationSummary.DPD_KIOSK_LANGUAGE, requiredData.get(4), Constants.TEXT);
			dropDown.selectItem(LocationSummary.DPD_ALTERNATE_LANGUAGE, requiredData.get(5), Constants.TEXT);

			foundation.click(LocationSummary.BTN_SYNC);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			login.logout();

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	@Test(description = "142739- SOS-24492 Verify alternate language is set to German in Kiosk when user set the Alternate Language as German and full sync is done in ADM")
	public void alternateGermanLanguage() {
		try {
			final String CASE_NUM = "142739";
			// Reading test data from DataBase
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			List<String> requiredData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Selecting location
			locationList.selectLocationName(requiredData.get(0));

			dropDown.selectItem(LocationSummary.DPD_KIOSK_LANGUAGE, requiredData.get(1), Constants.TEXT);
			dropDown.selectItem(LocationSummary.DPD_ALTERNATE_LANGUAGE, requiredData.get(2), Constants.TEXT);

			foundation.click(LocationSummary.BTN_SYNC);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			login.logout();
			browser.close();

			foundation.threadWait(Constants.SHORT_TIME);
			// login into Kiosk Device
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(LandingPage.BTN_LANG);

			// Validating Landing Page
			foundation.waitforElement(landingPage.objLanguage(requiredData.get(3)), Constants.SHORT_TIME);
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
			Assert.assertTrue(
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
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.EMAIL_ID));
			foundation.click(AccountLogin.BTN_NEXT);
			foundation.waitforElement(AccountLogin.BTN_PIN_NEXT, Constants.SHORT_TIME);
			textBox.enterPin(rstV5DeviceData.get(CNV5Device.PIN));
			foundation.click(AccountLogin.BTN_PIN_NEXT);
			payments.verifyPaymentPageLanguage(rstV5DeviceData.get(CNV5Device.PAYMENTS_PAGE));
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

			// Selecting location
			locationList.selectLocationName(requiredData.get(0));

			dropDown.selectItem(LocationSummary.DPD_KIOSK_LANGUAGE, requiredData.get(4), Constants.TEXT);
			dropDown.selectItem(LocationSummary.DPD_ALTERNATE_LANGUAGE, requiredData.get(5), Constants.TEXT);

			foundation.click(LocationSummary.BTN_SYNC);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			login.logout();

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	@Test(description = "142740- SOS-24492 Verify alternate language is set to Danish in Kiosk when user set the Alternate Language as Danish and full sync is done in ADM")
	public void alternateDanishLanguage() {
		try {
			final String CASE_NUM = "142740";
			// Reading test data from DataBase
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			List<String> requiredData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Selecting location
			locationList.selectLocationName(requiredData.get(0));

			dropDown.selectItem(LocationSummary.DPD_KIOSK_LANGUAGE, requiredData.get(1), Constants.TEXT);
			dropDown.selectItem(LocationSummary.DPD_ALTERNATE_LANGUAGE, requiredData.get(2), Constants.TEXT);

			foundation.click(LocationSummary.BTN_SYNC);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			login.logout();
			browser.close();

			foundation.threadWait(Constants.SHORT_TIME);
			// login into Kiosk Device
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));

			foundation.click(LandingPage.BTN_LANG);

			// Validating Landing Page
			foundation.waitforElement(landingPage.objLanguage(requiredData.get(3)), Constants.SHORT_TIME);
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
			Assert.assertTrue(
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
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.EMAIL_ID));
			foundation.click(AccountLogin.BTN_NEXT);
			foundation.waitforElement(AccountLogin.BTN_PIN_NEXT, Constants.SHORT_TIME);
			textBox.enterPin(rstV5DeviceData.get(CNV5Device.PIN));
			foundation.click(AccountLogin.BTN_PIN_NEXT);
			payments.verifyPaymentPageLanguage(rstV5DeviceData.get(CNV5Device.PAYMENTS_PAGE));
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

			// Selecting location
			locationList.selectLocationName(requiredData.get(0));

			dropDown.selectItem(LocationSummary.DPD_KIOSK_LANGUAGE, requiredData.get(4), Constants.TEXT);
			dropDown.selectItem(LocationSummary.DPD_ALTERNATE_LANGUAGE, requiredData.get(5), Constants.TEXT);

			foundation.click(LocationSummary.BTN_SYNC);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			login.logout();

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	@Test(description = "142892-Manitoba > v5 Kiosk Taxes")
	public void manitobaTaxes() {
		try {
			final String CASE_NUM = "142892";

			// Reading test data from DataBase
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);

			List<String> menuItem = Arrays.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
			List<String> products = Arrays.asList(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME).split(Constants.DELIMITER_TILD));
			List<String> taxes = Arrays.asList(rstV5DeviceData.get(CNV5Device.ORDER_PAGE).split(Constants.DELIMITER_TILD));
			List<String> requiredData = Arrays.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));

			browser.navigateURL(	propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItem.get(0));

			// select the org and update country and tax system
			orgList.selectOrg(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			dropDown.selectItem(OrgSummary.DPD_COUNTRY, requiredData.get(0), Constants.TEXT);
			dropDown.selectItem(OrgSummary.DPD_TAX_SYSTEM, requiredData.get(1), Constants.TEXT);
			foundation.click(OrgSummary.BTN_SAVE);

			// sync machine
			foundation.threadWait(Constants.TWO_SECOND);
			navigationBar.navigateToMenuItem(menuItem.get(1));
			foundation.waitforElement(LocationList.DPD_LOCATION_LIST, Constants.SHORT_TIME);
			List<String> language = Arrays	.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
			locationSummary.kiosklanguageSetting(requiredData.get(2), language.get(0), language.get(1));

			// login into Kiosk Device add both rate 5 and rate 8 product and verify the display of tax
			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(landingPage.objLanguage(language.get(0)));
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(products.get(0));
			foundation.click(ProductSearch.BTN_PRODUCT);
			Assert.assertTrue(foundation.isDisplayed(Order.TXT_HEADER));
			List<String> lblTaxes = foundation.getTextofListElement(Order.LBL_TAX);
			List<String> lblTaxValues = foundation.getTextofListElement(Order.LBL_VAT_VALUE);
			Assert.assertEquals(lblTaxes.get(0), taxes.get(0));
			Assert.assertEquals(lblTaxes.get(1), taxes.get(1));
			Assert.assertEquals(lblTaxValues.get(0), taxes.get(2));
			Assert.assertEquals(lblTaxValues.get(1), taxes.get(3));

			// add rate 5 product and verify the display of tax
			foundation.click(Order.BTN_CANCEL_ORDER);
			foundation.click(Order.LBL_ORDER_CANCELLED);
			foundation.waitforElement(LandingPage.IMG_SEARCH_ICON, Constants.SHORT_TIME);
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(products.get(1));
			foundation.click(ProductSearch.BTN_PRODUCT);
			Assert.assertEquals(foundation.getText(Order.TXT_HEADER), requiredData.get(4));
			Assert.assertEquals(foundation.getText(Order.LBL_TAX), taxes.get(0));
			Assert.assertEquals(foundation.getText(Order.LBL_VAT_VALUE), taxes.get(4));
			
			//add rate 8 product and verify the display of tax
			foundation.click(Order.BTN_CANCEL_ORDER);
			foundation.click(Order.LBL_ORDER_CANCELLED);
			foundation.waitforElement(LandingPage.IMG_SEARCH_ICON, Constants.SHORT_TIME);
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(products.get(2));
			foundation.click(ProductSearch.BTN_PRODUCT);
			Assert.assertEquals(foundation.getText(Order.TXT_HEADER), requiredData.get(4));
			Assert.assertTrue(foundation.getText(Order.LBL_TAX).contains(taxes.get(5)));

			Assert.assertEquals(foundation.getText(Order.LBL_VAT_VALUE), taxes.get(6));
			
			//add no tax assigned product and verify the display of tax section
			foundation.click(Order.BTN_CANCEL_ORDER);
			foundation.click(Order.LBL_ORDER_CANCELLED);
			foundation.waitforElement(LandingPage.IMG_SEARCH_ICON, Constants.SHORT_TIME);
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(products.get(3));
			foundation.click(ProductSearch.BTN_PRODUCT);
			Assert.assertEquals(foundation.getText(Order.TXT_HEADER), requiredData.get(4));
			Assert.assertFalse(foundation.isDisplayed(Order.LBL_TAX));
			Assert.assertFalse(foundation.isDisplayed(Order.LBL_VAT_VALUE));
			browser.close();
			
			// Reset data
			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			// Select Menu and Menu Item
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItem.get(0));
			// select the org and update country and tax system
			orgList.selectOrg(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			dropDown.selectItem(OrgSummary.DPD_COUNTRY, requiredData.get(5), Constants.TEXT);
			dropDown.selectItem(OrgSummary.DPD_TAX_SYSTEM, requiredData.get(6), Constants.TEXT);
			foundation.click(OrgSummary.BTN_SAVE);
			// sync machine
			foundation.threadWait(Constants.TWO_SECOND);
			navigationBar.navigateToMenuItem(menuItem.get(1));
			foundation.waitforElement(LocationList.DPD_LOCATION_LIST, Constants.SHORT_TIME);
			locationList.selectLocationName(requiredData.get(2));
			foundation.click(LocationSummary.BTN_SYNC);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_FILTER, Constants.SHORT_TIME);
			browser.close();

		} catch (Exception exc) {
			Assert.fail();
		}
	}

	@Test(description = "143019-SOS-11371 v5 > International GMA Login to Account Error")
	public void gmaLoginError() {
		try {

			final String CASE_NUM = "143019";

			// Reading test data from DataBase
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);

			List<String> menuItem = Arrays.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
			List<String> requiredData = Arrays.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));

			browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
							propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
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
			List<String> language = Arrays	.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
			locationSummary.kiosklanguageSetting(requiredData.get(1), language.get(0), language.get(1));

			// verify login error
			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(landingPage.objLanguage(requiredData.get(2)));
			foundation.click(LandingPage.BTN_LOGIN);
			foundation.click(AccountLogin.BTN_EMAIL_LOGIN);
			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.EMAIL_ID));
			foundation.click(AccountLogin.BTN_ENTER);
			foundation.click(AccountLogin.BTN_NEXT);
			Assert.assertEquals(foundation.getText(AccountLogin.LBL_ACCOUNT_NOT_AVAILABLE), requiredData.get(3));
			Assert.assertEquals(foundation.getText(AccountLogin.LBL_GEO_GRAPHIC_LOCATION), requiredData.get(4));
			browser.close();

			// resetData
			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(	propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			// Select Menu and Menu Item
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
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
			
			// verify login error
			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(landingPage.objLanguage(requiredData.get(2)));
			foundation.click(LandingPage.BTN_LOGIN);
			foundation.click(AccountLogin.BTN_EMAIL_LOGIN);
			accountLogin.login(rstV5DeviceData.get(CNV5Device.EMAIL_ID), rstV5DeviceData.get(CNV5Device.PIN));
			assertTrue(foundation.isDisplayed(EditAccount.BTN_EDIT_ACCOUNT));

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	@Test(description = "142850-Verify all the Tabs displayed after login with Driver user")
	public void verifyTabsDsiplayed() {
		try {

			final String CASE_NUM = "142850";
			
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
			
			browser.navigateURL(	propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
							propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			List<String> language = Arrays.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			locationSummary.kiosklanguageSetting(rstLocationListData.get(CNLocationList.LOCATION_NAME),language.get(0),language.get(1));
						
			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(landingPage.objLanguage(rstV5DeviceData.get(CNV5Device.ACTUAL_DATA)));
			landingPage.navigateDriverLoginPage();
			driverLoginPage.enterDriverPin(rstV5DeviceData.get(CNV5Device.PIN));
			foundation.click(DriverLoginPage.BTN_SIGN_IN);
			Assert.assertTrue(foundation.isDisplayed(DriverHomePage.TXT_MENU));
			List<String> tabNames = Arrays.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			driverHomePage.verifyData(tabNames);
			foundation.click(DriverHomePage.LINK_LOGOUT);

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	@Test(description = "142853-Verify the Logout tab from Driver menu")
	public void verifyLogoutTab() {
		try {

			final String CASE_NUM = "142853";
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
			
			browser.navigateURL(	propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
							propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			List<String> language = Arrays.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			locationSummary.kiosklanguageSetting(rstLocationListData.get(CNLocationList.LOCATION_NAME),language.get(0),language.get(1));
						
			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(landingPage.objLanguage(rstV5DeviceData.get(CNV5Device.ACTUAL_DATA)));
			landingPage.navigateDriverLoginPage();
			driverLoginPage.enterDriverPin(rstV5DeviceData.get(CNV5Device.PIN));
			foundation.click(DriverLoginPage.BTN_SIGN_IN);
			Assert.assertTrue(foundation.isDisplayed(DriverHomePage.TXT_MENU));
			foundation.click(DriverHomePage.LINK_LOGOUT);
			Assert.assertTrue(foundation.isDisplayed(DriverLoginPage.BTN_SIGN_IN));
			foundation.click(DriverLoginPage.BTN_SELF_SERVICE_MODE);
			foundation.isDisplayed(LandingPage.IMG_SEARCH_ICON);

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	@Test(description = "142851-Verify the deafult selected tab after login with Driver")
	public void verifyDefaultSelectedTab() {
		try {

			final String CASE_NUM = "142851";
			
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
			
			browser.navigateURL(	propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
							propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			List<String> language = Arrays.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			locationSummary.kiosklanguageSetting(rstLocationListData.get(CNLocationList.LOCATION_NAME),language.get(0),language.get(1));
						
			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			List<String> actualData = Arrays.asList(rstV5DeviceData.get(CNV5Device.ACTUAL_DATA).split(Constants.DELIMITER_TILD));
			foundation.click(landingPage.objLanguage(actualData.get(0)));
			landingPage.navigateDriverLoginPage();
			driverLoginPage.enterDriverPin(rstV5DeviceData.get(CNV5Device.PIN));
			foundation.click(DriverLoginPage.BTN_SIGN_IN);
			Assert.assertTrue(foundation.isDisplayed(DriverHomePage.TXT_MENU));
			String value = foundation.getBGColor(DriverHomePage.LINK_CASHOUT);
			Assert.assertEquals(actualData.get(1), value);
			Assert.assertTrue(foundation.isDisplayed(DriverHomePage.LBL_CASHOUT));
			Assert.assertTrue(foundation.isDisplayed(DriverHomePage.LBL_BILL_ACCEPTOR));
			List<String> currency = Arrays.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			driverHomePage.verifyData(currency);
			Assert.assertTrue(foundation.isDisplayed(DriverHomePage.BTN_CASHOUT));
			foundation.click(DriverHomePage.LINK_LOGOUT);
			Assert.assertTrue(foundation.isDisplayed(DriverLoginPage.BTN_SIGN_IN));

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	@Test(description = "142852-Verify Inventory Tab details after login with Driver")
	public void verifyInventoryTab() {
		try {

			final String CASE_NUM = "142852";
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);			
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
			
			browser.navigateURL(	propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
							propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			List<String> language = Arrays.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			locationSummary.kiosklanguageSetting(rstLocationListData.get(CNLocationList.LOCATION_NAME),language.get(0),language.get(1));
						
			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(landingPage.objLanguage(rstV5DeviceData.get(CNV5Device.ACTUAL_DATA)));
			landingPage.navigateDriverLoginPage();
			driverLoginPage.enterDriverPin(rstV5DeviceData.get(CNV5Device.PIN));
			foundation.click(DriverLoginPage.BTN_SIGN_IN);
			Assert.assertTrue(foundation.isDisplayed(DriverHomePage.TXT_MENU));
			foundation.click(DriverHomePage.LINK_INVENTORY);
			foundation.waitforElement(InventoryHomePage.TXT_SELECT_ACTION, Constants.SHORT_TIME);
			Assert.assertEquals(foundation.getText(InventoryHomePage.TXT_LOCATION),rstLocationListData.get(CNLocationList.LOCATION_NAME));
			List<String> options = Arrays.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			inventoryHomePage.verifyInventoryOptions(options);
			foundation.click(InventoryHomePage.BTN_LOGOUT);
			Assert.assertTrue(foundation.isDisplayed(DriverHomePage.TXT_MENU));
			foundation.click(DriverHomePage.LINK_LOGOUT);
			foundation.click(DriverLoginPage.BTN_SELF_SERVICE_MODE);
			foundation.isDisplayed(LandingPage.IMG_SEARCH_ICON);

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}


	@Test(description = "142994-SOS-11643 V5 > Menu Updates")
	public void verifyMenuLevelUpdates() {
		try {
			final String CASE_NUM = "142994";
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			List<String> requiredData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
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

			dropDown.selectItem(MicroMarketMenuList.DPD_LOCATION, requiredData.get(0), Constants.TEXT);
			foundation.click(MicroMarketMenuList.BTN_CREATE_NEW);
			textBox.enterText(MicroMarketMenuList.TXT_MENU_NAME, requiredData.get(1));
			checkBox.check(microMarketMenu.getCurrentDayObj(dateAndTime.getCurrentDay()));
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
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.objectFocus(MicroMarketMenuList.BTN_SAVE);
			foundation.click(MicroMarketMenuList.BTN_SAVE);
			foundation.waitforElement(MicroMarketMenuList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			// navigating to Home Page
			foundation.click(LocationList.LINK_HOME_PAGE);
			
			//update language pre-condition and sync
			List<String> language = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
			locationSummary.kiosklanguageSetting(requiredData.get(0), language.get(0), language.get(1));

			// login into Kiosk Device
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			foundation.waitforElement(landingPage.objText(requiredData.get(2)), Constants.SHORT_TIME);
			Assert.assertTrue(foundation.isDisplayed(landingPage.objText(requiredData.get(2))));
			foundation.click(landingPage.objText(requiredData.get(2)));
			foundation.click(landingPage.objText(requiredData.get(3)));
			Assert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(0))));
			Assert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(1))));
			Assert.assertTrue(foundation.isDisplayed(order.objText(requiredData.get(3))));
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
			foundation.refreshPage();
			foundation.click(LocationList.LINK_HOME_PAGE);
			locationList.selectLocationName(requiredData.get(0));
			foundation.click(LocationSummary.BTN_SYNC);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			login.logout();

		} catch (Exception exc) {
			exc.printStackTrace();
			Assert.fail();
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

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Selecting location
			locationList.selectLocationName(requiredData.get(0));

			dropDown.selectItem(LocationSummary.DPD_KIOSK_LANGUAGE, requiredData.get(1), Constants.TEXT);
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
			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
			foundation.click(ProductSearch.BTN_PRODUCT);

			// verify Order Page
			List<String> orderPageData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.ORDER_PAGE).split(Constants.DELIMITER_TILD));
			Assert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(0))));

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
			Assert.assertTrue(foundation.isDisplayed(payments.objText(paymentPageData.get(0))));

			foundation.click(payments.objText(paymentPageData.get(1)));
			foundation.waitforElement(LandingPage.IMG_SEARCH_ICON, Constants.SHORT_TIME);

		} catch (Exception exc) {
			exc.printStackTrace();
			Assert.fail();
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

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Selecting location
			locationList.selectLocationName(requiredData.get(0));

			dropDown.selectItem(LocationSummary.DPD_KIOSK_LANGUAGE, requiredData.get(1), Constants.TEXT);
			locationSummary.selectTab(requiredData.get(2));

			foundation.waitforElement(LocationSummary.TBL_PRODUCTS_GRID, Constants.SHORT_TIME);
			textBox.enterText(LocationSummary.TXT_PRODUCT_FILTER, rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
			Map<String, String> invCountBeforeSale = locationSummary.getProductDetails(requiredData.get(3));

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
			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
			foundation.click(ProductSearch.BTN_PRODUCT);

			// verify Order Page
			List<String> orderPageData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.ORDER_PAGE).split(Constants.DELIMITER_TILD));
			Assert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(0))));

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
			Assert.assertTrue(foundation.isDisplayed(payments.objText(paymentPageData.get(0))));

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
			locationList.selectLocationName(requiredData.get(0));

			dropDown.selectItem(LocationSummary.DPD_KIOSK_LANGUAGE, requiredData.get(1), Constants.TEXT);
			locationSummary.selectTab(requiredData.get(2));

			foundation.waitforElement(LocationSummary.TBL_PRODUCTS_GRID, Constants.SHORT_TIME);
			textBox.enterText(LocationSummary.TXT_PRODUCT_FILTER, rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
			Map<String, String> invCountAfterSale = locationSummary.getProductDetails(requiredData.get(3));

			Assert.assertTrue(Integer.valueOf(invCountAfterSale.get(requiredData.get(3))) == Integer
					.valueOf(invCountBeforeSale.get(requiredData.get(3))) - 1);

		} catch (Exception exc) {
			exc.printStackTrace();
			Assert.fail();
		}
	}

	@Test(description = "142846-QAA-45-Verify the Stock reduced in ADM when Sales transaction with email when Account balance is less than product amount")
	public void salesTransactionWithNoAccountBalance() {
		try {
			final String CASE_NUM = "142846";
			// Reading test data from DataBase
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstConsumerSearchData = dataBase.getConsumerSearchData(Queries.CONSUMER_SEARCH, CASE_NUM);
			rstConsumerSummaryData = dataBase.getConsumerSummaryData(Queries.CONSUMER_SUMMARY, CASE_NUM);

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			
			List<String> navigationMenu = Arrays
					.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));

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
			textBox.enterText(ConsumerSummary.TXT_ADJUST_BALANCE, rstConsumerSummaryData.get(CNConsumerSummary.ADJUST_BALANCE));
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
			locationList.selectLocationName(requiredData.get(0));
			
			//update Kiosk Language
			dropDown.selectItem(LocationSummary.DPD_KIOSK_LANGUAGE, requiredData.get(1), Constants.TEXT);
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
			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
			foundation.click(ProductSearch.BTN_PRODUCT);

			// verify Order Page
			List<String> orderPageData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.ORDER_PAGE).split(Constants.DELIMITER_TILD));
			Assert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(0))));

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
			Assert.assertTrue(foundation.isDisplayed(payments.objText(paymentPageData.get(0))));

			foundation.click(payments.objText(paymentPageData.get(1)));
			Assert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(0))));
			
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
			foundation.waitforElement(ConsumerSearch.DPD_SEARCH_BY, Constants.SHORT_TIME);

		} catch (Exception exc) {
			exc.printStackTrace();
			Assert.fail();
		}
	}

	@Test(description = "142847-QAA-45 - Verify Sales transaction with email failed when the stock is zero")
	public void salesTransactionWhenNoStock() {
		try {
			final String CASE_NUM = "142847";
			// Reading test data from DataBase
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			List<String> requiredData = Arrays.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));

			browser.navigateURL(	propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			locationList.selectLocationName(requiredData.get(0));
			locationSummary.selectTab(requiredData.get(2));
			foundation.waitforElement(LocationSummary.TBL_INVENTORY, Constants.SHORT_TIME);
			locationSummary.updateInventory(requiredData.get(3), requiredData.get(4), requiredData.get(5));
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.DPD_LOCATION_LIST, Constants.SHORT_TIME);
			List<String> language = Arrays	.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
			locationSummary.kiosklanguageSetting(requiredData.get(0), language.get(0), language.get(1));
			
			// login into Kiosk Device
			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));

			// Navigating to product search page
			foundation.waitforElement(LandingPage.IMG_SEARCH_ICON, Constants.SHORT_TIME);
			foundation.refreshPage();
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			foundation.waitforElement(AccountLogin.BTN_CAMELCASE, Constants.SHORT_TIME);

			// searching for product
			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
			foundation.click(ProductSearch.BTN_PRODUCT);

			// verify Order Page
			List<String> orderPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.ORDER_PAGE).split(Constants.DELIMITER_TILD));
			Assert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(0))));

			foundation.objectFocus(order.objText(orderPageData.get(1)));
			foundation.click(order.objText(orderPageData.get(1)));

			foundation.waitforElement(AccountLogin.BTN_NEXT, Constants.SHORT_TIME);

			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(propertyFile.readPropertyFile(Configuration.V5_USER, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(AccountLogin.BTN_NEXT);
			foundation.waitforElement(AccountLogin.BTN_PIN_NEXT, Constants.SHORT_TIME);
			textBox.enterPin(propertyFile.readPropertyFile(Configuration.V5_PIN, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(AccountLogin.BTN_PIN_NEXT);

			List<String> paymentPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.PAYMENTS_PAGE).split(Constants.DELIMITER_TILD));
			Assert.assertTrue(foundation.isDisplayed(payments.objText(paymentPageData.get(0))));
			foundation.click(payments.objText(paymentPageData.get(1)));
			foundation.waitforElement(LandingPage.IMG_SEARCH_ICON, Constants.SHORT_TIME);
			browser.close();

			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(	propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Selecting location
			locationList.selectLocationName(requiredData.get(0));
			dropDown.selectItem(LocationSummary.DPD_KIOSK_LANGUAGE, requiredData.get(1), Constants.TEXT);
			locationSummary.selectTab(requiredData.get(2));
			foundation.waitforElement(LocationSummary.TBL_INVENTORY, Constants.SHORT_TIME);
			locationSummary.updateInventory(requiredData.get(3), requiredData.get(6), requiredData.get(7));
			foundation.click(LocationSummary.BTN_SYNC);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);

		} catch (Exception exc) {
			Assert.fail(exc.toString());

		}
	}

		@Test(description = "143143>V5-Verify the VAT on Kiosk when user set only Tax Rate1 in ADM for UK")
			public void verifyVATwithTaxRate1() {
				try {
					final String CASE_NUM = "143143";

					rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
					rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
					rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);
					rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
					
					browser.navigateURL(	propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
					login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
							propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
					navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
					
					List<String> menu = Arrays.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
					List<String> country = Arrays.asList(rstLocationSummaryData.get(CNLocationSummary.COUNTRY).split(Constants.DELIMITER_TILD));
					List<String> requiredData = Arrays.asList(rstLocationSummaryData.get(CNLocationSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
					List<String> actualData = Arrays.asList(rstLocationSummaryData.get(CNLocationSummary.ACTUAL_DATA).split(Constants.DELIMITER_TILD));
					List<String> language = Arrays.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
					
					navigationBar.navigateToMenuItem(menu.get(0));
					dropDown.selectItem(OrgSummary.DPD_COUNTRY, country.get(0), Constants.TEXT);
					dropDown.selectItem(OrgSummary.DPD_TAX_SYSTEM, requiredData.get(0), Constants.TEXT);
					foundation.click(OrgSummary.BTN_SAVE);
					foundation.waitforElement(OrgList.LBL_ORG_LIST, Constants.SHORT_TIME);
					navigationBar.navigateToMenuItem(menu.get(1));
					foundation.waitforElement(LocationList.TXT_FILTER, Constants.SHORT_TIME);
					locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
					locationSummary.taxMapping(actualData.get(0), actualData.get(1));
					Assert.assertTrue(foundation.isDisplayed(locationSummary.objVerifyTaxRate(actualData.get(1))));
					foundation.click(LocationSummary.BTN_SAVE);
					locationSummary.kiosklanguageSetting(rstLocationListData.get(CNLocationList.LOCATION_NAME), language.get(0), language.get(1));
										
					foundation.threadWait(Constants.SHORT_TIME);
					browser.launch(Constants.REMOTE, Constants.CHROME);
					browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
					foundation.click(landingPage.objLanguage(language.get(0)));
					foundation.refreshPage();
					foundation.click(LandingPage.IMG_SEARCH_ICON);
					textBox.enterKeypadText(rstLocationSummaryData.get(CNLocationSummary.PRODUCT_NAME));
					foundation.click(ProductSearch.BTN_PRODUCT);
					Assert.assertTrue(foundation.isDisplayed(Order.LBL_YOUR_ORDER));
					Assert.assertTrue(foundation.isDisplayed(order.objText(rstLocationSummaryData.get(CNLocationSummary.PRODUCT_NAME))));
					order.verifyVAT(rstV5DeviceData.get(CNV5Device.ACTUAL_DATA));
					foundation.click(Order.LBL_EMAIL);
					foundation.click(AccountLogin.BTN_CAMELCASE);
					textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.EMAIL_ID));
					foundation.click(AccountLogin.BTN_NEXT);
					textBox.enterPin(rstV5DeviceData.get(CNV5Device.PIN));
					foundation.click(AccountLogin.BTN_PIN_NEXT);
					Assert.assertTrue(foundation.isDisplayed(order.objText(rstV5DeviceData.get(CNV5Device.PAYMENTS_PAGE))));
					browser.close();
					
					// Reset the data
					browser.launch(Constants.LOCAL, Constants.CHROME);
					browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
					login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
							propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
					navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

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
			Assert.fail(exc.toString());
		}
	}
		
	@Test(description = "143144>V5-Verify the VAT on Kiosk when user set only Tax Rate1,2,3,4 in ADM for UK")
	public void verifyVATwithAllTaxRate() {
		try {
			final String CASE_NUM = "143144";

			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);

			browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
							propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			List<String> menu = Arrays.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
			List<String> country = Arrays.asList(rstLocationSummaryData.get(CNLocationSummary.COUNTRY).split(Constants.DELIMITER_TILD));
			List<String> requiredData = Arrays.asList(rstLocationSummaryData.get(CNLocationSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> actualData = Arrays.asList(rstLocationSummaryData.get(CNLocationSummary.ACTUAL_DATA).split(Constants.DELIMITER_TILD));
			List<String> language = Arrays	.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> product = Arrays.asList(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME).split(Constants.DELIMITER_TILD));

			navigationBar.navigateToMenuItem(menu.get(0));
			dropDown.selectItem(OrgSummary.DPD_COUNTRY, country.get(0), Constants.TEXT);
			dropDown.selectItem(OrgSummary.DPD_TAX_SYSTEM, requiredData.get(0), Constants.TEXT);
			foundation.click(OrgSummary.BTN_SAVE);
			foundation.waitforElement(OrgList.LBL_ORG_LIST, Constants.SHORT_TIME);
			navigationBar.navigateToMenuItem(menu.get(1));
			foundation.waitforElement(LocationList.TXT_FILTER, Constants.SHORT_TIME);
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			locationSummary.taxMapping(actualData.get(0), actualData.get(1));
			Assert.assertTrue(foundation.isDisplayed(locationSummary.objVerifyTaxRate(actualData.get(1))));
			foundation.click(LocationSummary.BTN_SAVE);
			locationSummary.kiosklanguageSetting(rstLocationListData.get(CNLocationList.LOCATION_NAME), language.get(0), language.get(1));

			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(landingPage.objLanguage(language.get(0)));
			foundation.refreshPage();
			foundation.click(LandingPage.IMG_SEARCH_ICON);			
			textBox.enterKeypadText(product.get(0));
			foundation.click(ProductSearch.BTN_PRODUCT);
			Assert.assertTrue(foundation.isDisplayed(Order.LBL_YOUR_ORDER));
			Assert.assertTrue(foundation.isDisplayed(order.objText(product.get(0))));
			order.verifyVAT(rstV5DeviceData.get(CNV5Device.ACTUAL_DATA));
			foundation.click(Order.BTN_CANCEL_ORDER);
			Assert.assertTrue(foundation.isDisplayed(Order.LBL_ORDER_CANCELLED));
			foundation.waitforClikableElement(LandingPage.IMG_SEARCH_ICON, Constants.SHORT_TIME);
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(product.get(1));
			foundation.click(ProductSearch.BTN_PRODUCT);
			Assert.assertTrue(foundation.isDisplayed(Order.LBL_YOUR_ORDER));
			Assert.assertTrue(foundation.isDisplayed(order.objText(product.get(1))));
			order.verifyVAT(rstV5DeviceData.get(CNV5Device.ACTUAL_DATA));
			foundation.click(Order.LBL_EMAIL);
			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.EMAIL_ID));
			foundation.click(AccountLogin.BTN_NEXT);
			textBox.enterPin(rstV5DeviceData.get(CNV5Device.PIN));
			foundation.click(AccountLogin.BTN_PIN_NEXT);
			Assert.assertTrue(foundation.isDisplayed(order.objText(rstV5DeviceData.get(CNV5Device.PAYMENTS_PAGE))));
			browser.close();

			// Reset the data
			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
							propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

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
			Assert.fail(exc.toString());
		}
	}
	
	@Test(description = "143145>V5-Verify the VAT on Kiosk when user set only Tax Rate1,2,3,4 as zero in ADM for UK")
	public void verifyVATwithTaxZero() {
		try {
			final String CASE_NUM = "143145";

			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
			
			List<String> menu = Arrays.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
			List<String> country = Arrays.asList(rstLocationSummaryData.get(CNLocationSummary.COUNTRY).split(Constants.DELIMITER_TILD));
			List<String> requiredData = Arrays.asList(rstLocationSummaryData.get(CNLocationSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> actualData = Arrays.asList(rstLocationSummaryData.get(CNLocationSummary.ACTUAL_DATA).split(Constants.DELIMITER_TILD));
			List<String> language = Arrays	.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> product = Arrays.asList(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME).split(Constants.DELIMITER_TILD));

			browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
							propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));		

			navigationBar.navigateToMenuItem(menu.get(0));
			dropDown.selectItem(OrgSummary.DPD_COUNTRY, country.get(0), Constants.TEXT);
			dropDown.selectItem(OrgSummary.DPD_TAX_SYSTEM, requiredData.get(0), Constants.TEXT);
			foundation.click(OrgSummary.BTN_SAVE);
			foundation.waitforElement(OrgList.LBL_ORG_LIST, Constants.SHORT_TIME);
			navigationBar.navigateToMenuItem(menu.get(1));
			foundation.waitforElement(LocationList.TXT_FILTER, Constants.SHORT_TIME);
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			locationSummary.taxMapping(actualData.get(0), actualData.get(1));
			Assert.assertTrue(foundation.isDisplayed(locationSummary.objVerifyTaxRate(actualData.get(1))));
		    foundation.click(LocationSummary.BTN_SAVE);
			locationSummary.kiosklanguageSetting(rstLocationListData.get(CNLocationList.LOCATION_NAME), language.get(0), language.get(1));
			
			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(landingPage.objLanguage(language.get(0)));
			foundation.refreshPage();
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(product.get(0));
			foundation.click(ProductSearch.BTN_PRODUCT);
			Assert.assertTrue(foundation.isDisplayed(Order.LBL_YOUR_ORDER));
			Assert.assertTrue(foundation.isDisplayed(order.objText(product.get(0))));
			Assert.assertFalse(foundation.isDisplayed(Order.LBL_VAT_VALUE));
			foundation.click(Order.LBL_EMAIL);
			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.EMAIL_ID));
			foundation.click(AccountLogin.BTN_NEXT);
			textBox.enterPin(rstV5DeviceData.get(CNV5Device.PIN));
			foundation.click(AccountLogin.BTN_PIN_NEXT);
			foundation.waitforElement(order.objText(rstV5DeviceData.get(CNV5Device.PAYMENTS_PAGE)),Constants.SHORT_TIME);
			Assert.assertTrue(foundation.isDisplayed(order.objText(rstV5DeviceData.get(CNV5Device.PAYMENTS_PAGE))));
			browser.close();

			// Reset the data
			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
							propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

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
			Assert.fail(exc.toString());
		}
	}

	@Test(description = "143146>V5-Verify the VAT on Kiosk when user adds a product not assigned to the UK TAX Rate")
	public void verifyVATProductNotAssignedUKTax() {
		try {
			final String CASE_NUM = "143146";

			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
			
			List<String> menu = Arrays.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
			List<String> country = Arrays.asList(rstLocationSummaryData.get(CNLocationSummary.COUNTRY).split(Constants.DELIMITER_TILD));
			List<String> requiredData = Arrays.asList(rstLocationSummaryData.get(CNLocationSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> actualData = Arrays.asList(rstLocationSummaryData.get(CNLocationSummary.ACTUAL_DATA).split(Constants.DELIMITER_TILD));
			List<String> language = Arrays	.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> product = Arrays.asList(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME).split(Constants.DELIMITER_TILD));

			browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
							propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));		

			navigationBar.navigateToMenuItem(menu.get(0));
			dropDown.selectItem(OrgSummary.DPD_COUNTRY, country.get(0), Constants.TEXT);
			dropDown.selectItem(OrgSummary.DPD_TAX_SYSTEM, requiredData.get(0), Constants.TEXT);
			foundation.click(OrgSummary.BTN_SAVE);
			foundation.waitforElement(OrgList.LBL_ORG_LIST, Constants.SHORT_TIME);
			navigationBar.navigateToMenuItem(menu.get(1));
			foundation.waitforElement(LocationList.TXT_FILTER, Constants.SHORT_TIME);
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			locationSummary.taxMapping(actualData.get(0), actualData.get(1));
			Assert.assertTrue(foundation.isDisplayed(locationSummary.objVerifyTaxRate(actualData.get(1))));
			foundation.click(LocationSummary.BTN_SAVE);
			locationSummary.kiosklanguageSetting(rstLocationListData.get(CNLocationList.LOCATION_NAME), language.get(0), language.get(1));

			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(landingPage.objLanguage(language.get(0)));
			foundation.refreshPage();
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(product.get(0));
			foundation.click(ProductSearch.BTN_PRODUCT);
			Assert.assertTrue(foundation.isDisplayed(Order.LBL_YOUR_ORDER));
			Assert.assertTrue(foundation.isDisplayed(order.objText(product.get(0))));
			Assert.assertFalse(foundation.isDisplayed(Order.LBL_VAT_VALUE));
			foundation.click(Order.LBL_EMAIL);
			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.EMAIL_ID));
			foundation.click(AccountLogin.BTN_NEXT);
			textBox.enterPin(rstV5DeviceData.get(CNV5Device.PIN));
			foundation.click(AccountLogin.BTN_PIN_NEXT);
			Assert.assertTrue(foundation.isDisplayed(order.objText(rstV5DeviceData.get(CNV5Device.PAYMENTS_PAGE))));
			browser.close();

			// Reset the data
			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
							propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

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
			Assert.fail(exc.toString());
		}
	}
	
	@Test(description = "143147>V5-Verify the VAT on Kiosk when user adds a product not assigned to the UK TAX Rate")
	public void verifyVATWithOutTaxMapping() {
		try {			
			final String CASE_NUM = "143147";

			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
			
			List<String> menu = Arrays.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
			List<String> country = Arrays.asList(rstLocationSummaryData.get(CNLocationSummary.COUNTRY).split(Constants.DELIMITER_TILD));
			List<String> requiredData = Arrays.asList(rstLocationSummaryData.get(CNLocationSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> language = Arrays.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> product = Arrays.asList(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME).split(Constants.DELIMITER_TILD));

			browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
							propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));		

			navigationBar.navigateToMenuItem(menu.get(0));
			dropDown.selectItem(OrgSummary.DPD_COUNTRY, country.get(0), Constants.TEXT);
			dropDown.selectItem(OrgSummary.DPD_TAX_SYSTEM, requiredData.get(0), Constants.TEXT);
			foundation.click(OrgSummary.BTN_SAVE);
			foundation.waitforElement(OrgList.LBL_ORG_LIST, Constants.SHORT_TIME);
			navigationBar.navigateToMenuItem(menu.get(1));
			foundation.waitforElement(LocationList.TXT_FILTER, Constants.SHORT_TIME);
			locationSummary.kiosklanguageSetting(rstLocationListData.get(CNLocationList.LOCATION_NAME), language.get(0), language.get(1));
			
			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(landingPage.objLanguage(language.get(0)));
			foundation.refreshPage();
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(product.get(0));
			foundation.click(ProductSearch.BTN_PRODUCT);
			Assert.assertTrue(foundation.isDisplayed(Order.LBL_YOUR_ORDER));
			Assert.assertTrue(foundation.isDisplayed(order.objText(product.get(0))));
			Assert.assertFalse(foundation.isDisplayed(Order.LBL_VAT_VALUE));
			foundation.click(Order.LBL_EMAIL);
			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.EMAIL_ID));
			foundation.click(AccountLogin.BTN_NEXT);
			textBox.enterPin(rstV5DeviceData.get(CNV5Device.PIN));
			foundation.click(AccountLogin.BTN_PIN_NEXT);
			Assert.assertTrue(foundation.isDisplayed(order.objText(rstV5DeviceData.get(CNV5Device.PAYMENTS_PAGE))));
			browser.close();

			// Reset the data
			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
							propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.navigateToMenuItem(menu.get(0));
			dropDown.selectItem(OrgSummary.DPD_COUNTRY, country.get(1), Constants.TEXT);
			dropDown.selectItem(OrgSummary.DPD_TAX_SYSTEM, requiredData.get(1), Constants.TEXT);
			foundation.click(OrgSummary.BTN_SAVE);
			foundation.waitforElement(OrgList.LBL_ORG_LIST, Constants.SHORT_TIME);
			navigationBar.navigateToMenuItem(menu.get(1));
			foundation.waitforElement(LocationList.TXT_FILTER, Constants.SHORT_TIME);
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BTN_SYNC);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_FILTER, Constants.SHORT_TIME);

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
	
	@Test(description = "143148>V5-Verify the VAT on Kiosk when user adds a Multile products with UK TAX Rate")
	public void verifyVATWithMultipleProducts() {
		try {			
			final String CASE_NUM = "143148";

			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
		
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
							propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));	
			
			List<String> menu = Arrays.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
			List<String> country = Arrays.asList(rstLocationSummaryData.get(CNLocationSummary.COUNTRY).split(Constants.DELIMITER_TILD));
			List<String> requiredData = Arrays.asList(rstLocationSummaryData.get(CNLocationSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> language = Arrays.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> product = Arrays.asList(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME).split(Constants.DELIMITER_TILD));
			List<String> actualData = Arrays.asList(rstLocationSummaryData.get(CNLocationSummary.ACTUAL_DATA).split(Constants.DELIMITER_TILD));				

			navigationBar.navigateToMenuItem(menu.get(0));
			dropDown.selectItem(OrgSummary.DPD_COUNTRY, country.get(0), Constants.TEXT);
			dropDown.selectItem(OrgSummary.DPD_TAX_SYSTEM, requiredData.get(0), Constants.TEXT);
			foundation.click(OrgSummary.BTN_SAVE);
			foundation.waitforElement(OrgList.LBL_ORG_LIST, Constants.SHORT_TIME);
			navigationBar.navigateToMenuItem(menu.get(1));
			foundation.waitforElement(LocationList.TXT_FILTER, Constants.SHORT_TIME);
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			locationSummary.taxMapping(actualData.get(0), actualData.get(1));
			Assert.assertTrue(foundation.isDisplayed(locationSummary.objVerifyTaxRate(actualData.get(1))));
			foundation.click(LocationSummary.BTN_SAVE);
			locationSummary.kiosklanguageSetting(rstLocationListData.get(CNLocationList.LOCATION_NAME), language.get(0), language.get(1));
			
			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(landingPage.objLanguage(language.get(0)));
			foundation.refreshPage();
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(product.get(0));
			foundation.click(ProductSearch.BTN_PRODUCT);
			Assert.assertTrue(foundation.isDisplayed(Order.LBL_YOUR_ORDER));
			Assert.assertTrue(foundation.isDisplayed(order.objText(product.get(0))));
			order.verifyVAT(rstV5DeviceData.get(CNV5Device.ACTUAL_DATA));
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(product.get(1));
			foundation.click(ProductSearch.BTN_PRODUCT);
			Assert.assertTrue(foundation.isDisplayed(Order.LBL_YOUR_ORDER));
			Assert.assertTrue(foundation.isDisplayed(order.objText(product.get(1))));
			order.verifyVAT(rstV5DeviceData.get(CNV5Device.ACTUAL_DATA));
			foundation.click(Order.LBL_EMAIL);
			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.EMAIL_ID));
			foundation.click(AccountLogin.BTN_NEXT);
			textBox.enterPin(rstV5DeviceData.get(CNV5Device.PIN));
			foundation.click(AccountLogin.BTN_PIN_NEXT);
			foundation.waitforElement(order.objText(rstV5DeviceData.get(CNV5Device.PAYMENTS_PAGE)),Constants.SHORT_TIME);
			Assert.assertTrue(foundation.isDisplayed(order.objText(rstV5DeviceData.get(CNV5Device.PAYMENTS_PAGE))));
			browser.close();

			// Reset the data
			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
							propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

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
			foundation.threadWait(Constants.TWO_SECOND);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_FILTER, Constants.SHORT_TIME);

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
	
	@Test(description = "143112-Edit existing tax category name and verify edits applied to product or not on product summary page")
	public void editTaxCategory() {
		try {
			final String CASE_NUM = "143112";
			
			// Reading test data from DataBase
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			String productName=rstV5DeviceData.get(CNV5Device.PRODUCT_NAME);			
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
			navigationBar.navigateToMenuItem(menuItem.get(0));
			globalProduct.selectGlobalProduct(productName);
			dropDown.selectItem(ProductSummary.DPD_TAX_CATEGORY, requiredData.get(0), Constants.TEXT);
			foundation.click(ProductSummary.BTN_SAVE);

			//edit tax category
			navigationBar.navigateToMenuItem(menuItem.get(1));
			categoryList.selectCategory(requiredData.get(0));
			categorySummary.updateName(requiredData.get(1));
			navigationBar.navigateToMenuItem(menuItem.get(0));
			globalProduct.selectGlobalProduct(productName);
			assertEquals(dropDown.getSelectedItem(ProductSummary.DPD_TAX_CATEGORY), requiredData.get(1));
			
			//reset data
			dropDown.selectItem(ProductSummary.DPD_TAX_CATEGORY, requiredData.get(2), Constants.TEXT);
			foundation.click(ProductSummary.BTN_SAVE);
			navigationBar.navigateToMenuItem(menuItem.get(1));
			categoryList.selectCategory(requiredData.get(1));
			categorySummary.updateName(requiredData.get(0));								
		} catch (Exception exc) {
			Assert.fail();
		}
	}
	
	@Test(description = "143114-QA-19-Edit existing category name and verify edits applied to product or not on product summary page")
	public void editCategories() {
		try {
			final String CASE_NUM = "143114";
			
			// Reading test data from DataBase
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			String productName=rstV5DeviceData.get(CNV5Device.PRODUCT_NAME);			
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
			navigationBar.navigateToMenuItem(menuItem.get(0));
			globalProduct.selectGlobalProduct(productName);
			dropDown.selectItem(ProductSummary.DPD_CATEGORY1, requiredData.get(0), Constants.TEXT);
			dropDown.selectItem(ProductSummary.DPD_CATEGORY2, requiredData.get(1), Constants.TEXT);
			dropDown.selectItem(ProductSummary.DPD_CATEGORY3, requiredData.get(2), Constants.TEXT);
			foundation.click(ProductSummary.BTN_SAVE);

			//update tax categories
			navigationBar.navigateToMenuItem(menuItem.get(1));
			categoryList.selectCategory(requiredData.get(0));
			categorySummary.updateName(requiredData.get(3));
			foundation.threadWait(Constants.TWO_SECOND);
			categoryList.selectCategory(requiredData.get(1));
			categorySummary.updateName(requiredData.get(4));
			foundation.threadWait(Constants.TWO_SECOND);
			categoryList.selectCategory(requiredData.get(2));
			categorySummary.updateName(requiredData.get(5));
			
			//verify edits applied to product or not
			navigationBar.navigateToMenuItem(menuItem.get(0));
			globalProduct.selectGlobalProduct(productName);
			assertEquals(dropDown.getSelectedItem(ProductSummary.DPD_CATEGORY1), requiredData.get(3));
			assertEquals(dropDown.getSelectedItem(ProductSummary.DPD_CATEGORY2), requiredData.get(4));
			assertEquals(dropDown.getSelectedItem(ProductSummary.DPD_CATEGORY3), requiredData.get(5));
			
			//reset data
			dropDown.selectItem(ProductSummary.DPD_CATEGORY1, requiredData.get(6), Constants.TEXT);
			dropDown.selectItem(ProductSummary.DPD_CATEGORY2, requiredData.get(6), Constants.TEXT);
			dropDown.selectItem(ProductSummary.DPD_CATEGORY3, requiredData.get(6), Constants.TEXT);
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
			
		} catch (Exception exc) {
			Assert.fail();
		}
	}
	
	@Test(description = "143113-Edit existing deposit category name and verify edits applied to product or not on product summary page")
	public void editDepositCategory() {
		try {
			final String CASE_NUM = "143113";
			
			// Reading test data from DataBase
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			String productName=rstV5DeviceData.get(CNV5Device.PRODUCT_NAME);			
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
			navigationBar.navigateToMenuItem(menuItem.get(0));
			globalProduct.selectGlobalProduct(productName);
			dropDown.selectItem(ProductSummary.DPD_DEPOSIT_CATEGORY, requiredData.get(0), Constants.TEXT);
			foundation.click(ProductSummary.BTN_SAVE);

			//edit tax category
			navigationBar.navigateToMenuItem(menuItem.get(1));
			categoryList.selectCategory(requiredData.get(0));
			categorySummary.updateName(requiredData.get(1));
			navigationBar.navigateToMenuItem(menuItem.get(0));
			globalProduct.selectGlobalProduct(productName);
			assertEquals(dropDown.getSelectedItem(ProductSummary.DPD_DEPOSIT_CATEGORY), requiredData.get(1));
			
			//reset data
			dropDown.selectItem(ProductSummary.DPD_DEPOSIT_CATEGORY, requiredData.get(2), Constants.TEXT);
			foundation.click(ProductSummary.BTN_SAVE);
			navigationBar.navigateToMenuItem(menuItem.get(1));
			categoryList.selectCategory(requiredData.get(1));
			categorySummary.updateName(requiredData.get(0));								
		} catch (Exception exc) {
			Assert.fail();
		}
	}
	
	@Test(description = "143115-Edit existing INVREASON category name and verify edits applied to product or not on product summary page")
	public void editInvReason() {
		try {
			final String CASE_NUM = "143115";
			
			// Reading test data from DataBase
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			String productName=rstV5DeviceData.get(CNV5Device.PRODUCT_NAME);			
			List<String> requiredData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> menuItem = Arrays
					.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			//edit invReason code and verify the edits on product page
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItem.get(1));
			categoryList.selectCategory(requiredData.get(0));
			categorySummary.updateName(requiredData.get(1));
			navigationBar.navigateToMenuItem(menuItem.get(0));
			globalProduct.selectGlobalProduct(productName);
			textBox.enterText(ProductSummary.TXT_LOCATION_SEARCH_FILTER, propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(ProductSummary.LBL_REASON_CODE);
			List<String> listReasonCode = dropDown.getAllItems(ProductSummary.DPD_REASON_CODE);
			assertTrue(listReasonCode.contains(requiredData.get(1)));
			
			//reset data
			navigationBar.navigateToMenuItem(menuItem.get(1));
			categoryList.selectCategory(requiredData.get(1));
			categorySummary.updateName(requiredData.get(0));								
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
	
	@Test(description = "142905-QAA-44-Place Order with valid email id")
	public void anOrderWithEmailID() {
		try {
		final String CASE_NUM = "142905";	

		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		List<String> requiredData = Arrays.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		
		//navigate to payment success page and validate No receipt
		browser.close();
		browser.launch(Constants.REMOTE, Constants.CHROME);
		browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
		foundation.click(landingPage.objLanguage(requiredData.get(0)));
		foundation.click(LandingPage.IMG_SEARCH_ICON);
		textBox.enterKeypadText(requiredData.get(1));
		foundation.click(ProductSearch.BTN_PRODUCT);
		assertTrue(foundation.isDisplayed(Order.LBL_EMAIL));
		foundation.click(Order.LBL_EMAIL);
		accountLogin.login(propertyFile.readPropertyFile(Configuration.V5_USER, FilePath.PROPERTY_CONFIG_FILE), propertyFile.readPropertyFile(Configuration.V5_PIN, FilePath.PROPERTY_CONFIG_FILE));
		assertTrue(foundation.isDisplayed(PaymentSuccess.BTN_YES));
		
		}catch (Exception exc) {
			Assert.fail(exc.toString());
		}
		
	}

	@Test(description = "142907-QAA-44-verify daily revenue on location page")
	public void verifyDailyRevenue() {
		try {
			final String CASE_NUM = "142907";
			// Reading test data from DataBase
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			List<String> requiredData = Arrays.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			
			//verify daily revenue			
			browser.navigateURL(	propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
							propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			textBox.enterText(LocationList.TXT_FILTER, requiredData.get(0));
			String dailyRevenue=foundation.getText(locationList.objDailyRevenue(requiredData.get(0)));
			assertNotEquals(dailyRevenue, requiredData.get(1));
		}
		catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
	
	@Test(description = "143063-Validate v5 transactions with Active Bundle promotions with Flash Sale")
	public void bundleFlashSale() {
		try {
			final String CASE_NUM = "143063";
			
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
			String locationName = rstLocationData.get(CNLocation.LOCATION_NAME);
			String gridName = rstLocationData.get(CNLocation.TAB_NAME);

			List<String> navigationMenu = Arrays.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
			List<String> requiredData = Arrays.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> language = Arrays.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
			
			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(navigationMenu.get(0));

			// New Promotion
			createPromotions.BundlePromotion(promotionType, promotionName, displayName, requiredData.get(0),locationName);
			createPromotions.selectBundlePromotionDetails(requiredData.get(1), requiredData.get(2), requiredData.get(4),rstV5DeviceData.get(CNLocation.REQUIRED_DATA));
			createPromotions.selectBundlePromotionPricing(requiredData.get(6));
			createPromotions.selectBundlePromotionTimes(requiredData.get(3),requiredData.get(5));
			
			String priceTotal=foundation.getText(CreatePromotions.LBL_TOTAL_PRICE);
			String bundleDiscount= foundation.getText(CreatePromotions.LBL_BUNDLE_DISCOUNT);
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
			
			locationSummary.kiosklanguageSetting(locationName, language.get(0),language.get(1));
			
			foundation.threadWait(Constants.SHORT_TIME);
			// login into Kiosk Device
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(requiredData.get(2));
			foundation.click(ProductSearch.BTN_PRODUCT);
			Assert.assertTrue(foundation.isDisplayed(Order.BTN_CANCEL_ORDER));
			
			Assert.assertTrue(displayName.equals(foundation.getText(Order.LBL_PROMOTION_NAME)));
			List<String> discountList=foundation.getTextofListElement(Order.LBL_ORDER_DISCOUNT);
			Assert.assertTrue(discountList.get(2).equals(bundleDiscount));
			
			// verify the display of total section
            String productPrice = foundation.getText(Order.LBL_PRODUCT_PRICE).split(Constants.DOLLAR)[1];
            String discount = foundation.getText(Order.LBL_DEPOSIT).split(Constants.DOLLAR)[1];
            Double expectedBalanceDue = Double.parseDouble(productPrice) - Double.parseDouble(discount);
            assertTrue(foundation.getText(Order.LBL_BALANCE_DUE).contains(String.valueOf(expectedBalanceDue)));
            assertTrue(foundation.getText(Order.LBL_SUB_TOTAL).contains(priceTotal));
            assertEquals(foundation.getText(Order.LBL_DISCOUNT),Constants.DELIMITER_HYPHEN+bundleDiscount);
            
            List<String> orderPageData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.ORDER_PAGE).split(Constants.DELIMITER_TILD));
			List<String> paymentPageData = Arrays
			.asList(rstV5DeviceData.get(CNV5Device.PAYMENTS_PAGE).split(Constants.DELIMITER_TILD));
			Assert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(0))));
            foundation.objectFocus(order.objText(orderPageData.get(1)));
            order.completeOrder(orderPageData.get(1),paymentPageData.get(0), paymentPageData.get(1));
                  
            browser.close();
            browser.launch(Constants.LOCAL, Constants.CHROME);
            browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			
			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(navigationMenu.get(0));
		
			// Deleting the Promotion
			editPromotion.expirePromotion(gridName, promotionName);

		} catch (Exception exc) {
			Assert.fail(exc.toString());
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
			String locationName = rstLocationData.get(CNLocation.LOCATION_NAME);
			String gridName = rstLocationData.get(CNLocation.TAB_NAME);

			List<String> navigationMenu = Arrays.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
			List<String> requiredData = Arrays.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> language = Arrays.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
			
			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(navigationMenu.get(0));

			// New Promotion
			createPromotions.BundlePromotion(promotionType, promotionName, displayName, requiredData.get(0),locationName);
			createPromotions.selectBundlePromotionDetails(requiredData.get(1), requiredData.get(2), requiredData.get(4),rstV5DeviceData.get(CNLocation.REQUIRED_DATA));
			createPromotions.selectBundlePromotionPricing(requiredData.get(6));
			createPromotions.selectBundlePromotionTimes(requiredData.get(3),requiredData.get(5));
				
			String priceTotal=foundation.getText(CreatePromotions.LBL_TOTAL_PRICE);
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

			locationSummary.kiosklanguageSetting(locationName, language.get(0),language.get(1));
			
			foundation.threadWait(Constants.SHORT_TIME);
			// login into Kiosk Device
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(requiredData.get(2));
			foundation.click(ProductSearch.BTN_PRODUCT);
			Assert.assertTrue(foundation.isDisplayed(Order.BTN_CANCEL_ORDER));
			
			Assert.assertTrue(requiredData.get(2).equals(foundation.getText(Order.LBL_PROMOTION_NAME)));
			
			// verify the display of total section
            String productPrice = foundation.getText(Order.LBL_PRODUCT_PRICE).split(Constants.DOLLAR)[1];
            assertTrue(foundation.getText(Order.LBL_BALANCE_DUE).contains(String.valueOf(productPrice)));
            assertTrue(foundation.getText(Order.LBL_SUB_TOTAL).contains(priceTotal));
            
            List<String> orderPageData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.ORDER_PAGE).split(Constants.DELIMITER_TILD));
            List<String> paymentPageData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.PAYMENTS_PAGE).split(Constants.DELIMITER_TILD));
			Assert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(0))));
            foundation.objectFocus(order.objText(orderPageData.get(1)));
			order.completeOrder(orderPageData.get(1),paymentPageData.get(0), paymentPageData.get(1));

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
	
	@Test(description = "143065-Validate v5 transactions with Discount on multiple Line items with Recurrence for Bundle Promotion")
	public void recurrencebundlePromotion() {
		try {
			final String CASE_NUM = "143065";
			
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
			String locationName = rstLocationData.get(CNLocation.LOCATION_NAME);
			String gridName = rstLocationData.get(CNLocation.TAB_NAME);

			List<String> navigationMenu = Arrays.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
			List<String> requiredData = Arrays.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> language = Arrays.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
			
			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(navigationMenu.get(0));

			// New Promotion
			createPromotions.BundlePromotion(promotionType, promotionName, displayName, requiredData.get(0),locationName);
			createPromotions.selectBundlePromotionDetails(requiredData.get(1), requiredData.get(2), requiredData.get(5),rstV5DeviceData.get(CNLocation.REQUIRED_DATA));

			textBox.enterText(CreatePromotions.TXT_ITEM1, requiredData.get(3));
			foundation.threadWait(Constants.ONE_SECOND);
			textBox.enterText(CreatePromotions.TXT_ITEM1, Keys.ENTER);
			foundation.threadWait(Constants.TWO_SECOND);
			textBox.enterText(CreatePromotions.TXT_TRANSACTION_MIN, requiredData.get(5));
			
			createPromotions.selectBundlePromotionPricing(requiredData.get(6));
			createPromotions.selectBundlePromotionTimes(requiredData.get(4),Constants.DELIMITER_SPACE);
			createPromotions.recurringDay();
			
			String priceTotal=foundation.getText(CreatePromotions.LBL_TOTAL_PRICE).split(Constants.DOLLAR)[1];
			String bundleDiscount= foundation.getText(CreatePromotions.LBL_BUNDLE_DISCOUNT);
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

			locationSummary.kiosklanguageSetting(locationName, language.get(0),language.get(1));
			
			foundation.threadWait(Constants.SHORT_TIME);
			// login into Kiosk Device
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(requiredData.get(2));
			foundation.click(ProductSearch.BTN_PRODUCT);
			Assert.assertTrue(foundation.isDisplayed(Order.BTN_CANCEL_ORDER));
			
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(requiredData.get(3));
			foundation.click(ProductSearch.BTN_PRODUCT);
			Assert.assertTrue(foundation.isDisplayed(Order.BTN_CANCEL_ORDER));
			
			Assert.assertTrue(displayName.equals(foundation.getText(Order.LBL_PROMOTION_NAME)));
			List<String> discountList=foundation.getTextofListElement(Order.LBL_ORDER_DISCOUNT);
			Assert.assertTrue(discountList.get(2).equals(bundleDiscount));
			
			// verify the display of total section
            String productPrice = foundation.getText(Order.LBL_PRODUCT_PRICE).split(Constants.DOLLAR)[1];
            String discount = foundation.getText(Order.LBL_DEPOSIT).split(Constants.DOLLAR)[1];
            Double expectedBalanceDue = Double.parseDouble(priceTotal) - Double.parseDouble(discount);
            assertTrue(foundation.getText(Order.LBL_BALANCE_DUE).contains(String.valueOf(expectedBalanceDue)));
            assertTrue(foundation.getText(Order.LBL_SUB_TOTAL).contains(priceTotal));
            assertEquals(foundation.getText(Order.LBL_DISCOUNT),Constants.DELIMITER_HYPHEN+bundleDiscount);
            
            List<String> orderPageData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.ORDER_PAGE).split(Constants.DELIMITER_TILD));
            List<String> paymentPageData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.PAYMENTS_PAGE).split(Constants.DELIMITER_TILD));
			Assert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(0))));
            foundation.objectFocus(order.objText(orderPageData.get(1)));
			order.completeOrder(orderPageData.get(1),paymentPageData.get(0), paymentPageData.get(1));
                  
            browser.close();
            browser.launch(Constants.LOCAL, Constants.CHROME);
            browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			
			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(navigationMenu.get(0));
		
			// Deleting the Promotion
			editPromotion.expirePromotion(gridName, promotionName);

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
	
	@Test(description = "143066-Validate v5 transactions with Discount Category with Scheduled for Bundle Promotion")
	public void discountbundlePromotion() {
		try {
			final String CASE_NUM = "143066";
			
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
			String locationName = rstLocationData.get(CNLocation.LOCATION_NAME);
			String gridName = rstLocationData.get(CNLocation.TAB_NAME);

			List<String> navigationMenu = Arrays.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
			List<String> requiredData = Arrays.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> language = Arrays.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
			
			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(navigationMenu.get(0));

			// New Promotion
			createPromotions.BundlePromotion(promotionType, promotionName, displayName, requiredData.get(0),locationName);
			createPromotions.selectBundlePromotionDetails(requiredData.get(1), requiredData.get(2), requiredData.get(5),rstV5DeviceData.get(CNLocation.REQUIRED_DATA));
			createPromotions.selectBundlePromotionPricing(requiredData.get(6));
			createPromotions.selectBundlePromotionTimes(requiredData.get(4),Constants.DELIMITER_SPACE);
						
			String priceTotal=foundation.getText(CreatePromotions.LBL_TOTAL_PRICE);
			String bundleDiscount= foundation.getText(CreatePromotions.LBL_BUNDLE_DISCOUNT);
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
		
			locationSummary.kiosklanguageSetting(locationName, language.get(0),language.get(1));
			
			foundation.threadWait(Constants.SHORT_TIME);
			// login into Kiosk Device
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
						
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(requiredData.get(3));
			foundation.click(ProductSearch.BTN_PRODUCT);
			Assert.assertTrue(foundation.isDisplayed(Order.BTN_CANCEL_ORDER));
						
			Assert.assertTrue(displayName.equals(foundation.getText(Order.LBL_PROMOTION_NAME)));
			List<String> discountList=foundation.getTextofListElement(Order.LBL_ORDER_DISCOUNT);
			Assert.assertTrue(discountList.get(2).equals(bundleDiscount));
						
			// verify the display of total section
			String productPrice = foundation.getText(Order.LBL_PRODUCT_PRICE).split(Constants.DOLLAR)[1];
			String discount = foundation.getText(Order.LBL_DEPOSIT).split(Constants.DOLLAR)[1];
			Double expectedBalanceDue = Double.parseDouble(productPrice) - Double.parseDouble(discount);
			assertTrue(foundation.getText(Order.LBL_BALANCE_DUE).contains(String.valueOf(expectedBalanceDue)));
			assertTrue(foundation.getText(Order.LBL_SUB_TOTAL).contains(priceTotal));
			assertEquals(foundation.getText(Order.LBL_DISCOUNT),Constants.DELIMITER_HYPHEN+bundleDiscount);
			            
			List<String> orderPageData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.ORDER_PAGE).split(Constants.DELIMITER_TILD));
			List<String> paymentPageData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.PAYMENTS_PAGE).split(Constants.DELIMITER_TILD));
			Assert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(0))));
			foundation.objectFocus(order.objText(orderPageData.get(1)));
			order.completeOrder(orderPageData.get(1),paymentPageData.get(0), paymentPageData.get(1));
			                  
			browser.close();
			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
						
			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(navigationMenu.get(0));
					
			// Deleting the Promotion
			//dropDown.selectItem(PromotionList.DPD_STATUS, requiredData.get(4), Constants.TEXT);
			//foundation.click(PromotionList.BTN_SEARCH);
			editPromotion.expirePromotion(gridName, promotionName);

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
	
	@Test(description = "143067-Validate v5 transactions with Min Transaction amount for Bundle Promotion")
	public void minTransactionbundlePromotion() {
		try {
			final String CASE_NUM = "143067";
			
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
			String locationName = rstLocationData.get(CNLocation.LOCATION_NAME);
			String gridName = rstLocationData.get(CNLocation.TAB_NAME);

			List<String> navigationMenu = Arrays.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
			List<String> requiredData = Arrays.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> language = Arrays.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
			
			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(navigationMenu.get(0));

			// New Promotion
			createPromotions.BundlePromotion(promotionType, promotionName, displayName, requiredData.get(0),locationName);
			createPromotions.selectBundlePromotionDetails(requiredData.get(1), requiredData.get(2), requiredData.get(5),rstV5DeviceData.get(CNLocation.REQUIRED_DATA));
			createPromotions.selectBundlePromotionPricing(requiredData.get(6));
			createPromotions.selectBundlePromotionTimes(requiredData.get(4),Constants.DELIMITER_SPACE);
						
			String priceTotal=foundation.getText(CreatePromotions.LBL_TOTAL_PRICE);
			String bundleDiscount= foundation.getText(CreatePromotions.LBL_BUNDLE_DISCOUNT);
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
			
			locationSummary.kiosklanguageSetting(locationName, language.get(0),language.get(1));
			
			foundation.threadWait(Constants.SHORT_TIME);
			// login into Kiosk Device
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
						
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(requiredData.get(3));
			foundation.click(ProductSearch.BTN_PRODUCT);
			Assert.assertTrue(foundation.isDisplayed(Order.BTN_CANCEL_ORDER));
						
			Assert.assertTrue(displayName.equals(foundation.getText(Order.LBL_PROMOTION_NAME)));
			List<String> discountList=foundation.getTextofListElement(Order.LBL_ORDER_DISCOUNT);
			Assert.assertTrue(discountList.get(2).equals(bundleDiscount));
						
			// verify the display of total section
			String productPrice = foundation.getText(Order.LBL_PRODUCT_PRICE).split(Constants.DOLLAR)[1];
			String discount = foundation.getText(Order.LBL_DEPOSIT).split(Constants.DOLLAR)[1];
			Double expectedBalanceDue = Double.parseDouble(productPrice) - Double.parseDouble(discount);
			assertTrue(foundation.getText(Order.LBL_BALANCE_DUE).contains(String.valueOf(expectedBalanceDue)));
			assertTrue(foundation.getText(Order.LBL_SUB_TOTAL).contains(priceTotal));
			assertEquals(foundation.getText(Order.LBL_DISCOUNT),Constants.DELIMITER_HYPHEN+bundleDiscount);
			            
			List<String> orderPageData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.ORDER_PAGE).split(Constants.DELIMITER_TILD));
			List<String> paymentPageData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.PAYMENTS_PAGE).split(Constants.DELIMITER_TILD));
			Assert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(0))));
			foundation.objectFocus(order.objText(orderPageData.get(1)));
			order.completeOrder(orderPageData.get(1),paymentPageData.get(0), paymentPageData.get(1));
			                  
			browser.close();
			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
						
			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(navigationMenu.get(0));
					
			// Deleting the Promotion
			editPromotion.expirePromotion(gridName, promotionName);

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
	
	@Test(description = "143068-Validate v5 transactions with discount more than Transaction amount for Bundle Promotion")
	public void discountTransactionbundlePromotion() {
		try {
			final String CASE_NUM = "143068";

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
			String locationName = rstLocationData.get(CNLocation.LOCATION_NAME);
			String gridName = rstLocationData.get(CNLocation.TAB_NAME);

			List<String> navigationMenu = Arrays.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
			List<String> requiredData = Arrays.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> language = Arrays.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
			
			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(navigationMenu.get(0));

			// New Promotion
			createPromotions.BundlePromotion(promotionType, promotionName, displayName, requiredData.get(0),locationName);
			createPromotions.selectBundlePromotionDetails(requiredData.get(1), requiredData.get(2), requiredData.get(5),rstV5DeviceData.get(CNLocation.REQUIRED_DATA));
			createPromotions.selectBundlePromotionPricing(requiredData.get(6));
			createPromotions.selectBundlePromotionTimes(requiredData.get(4),Constants.DELIMITER_SPACE);
						
			String priceTotal=foundation.getText(CreatePromotions.LBL_TOTAL_PRICE);
			String bundleDiscount= foundation.getText(CreatePromotions.LBL_BUNDLE_DISCOUNT).split(Constants.REPLACE_DOLLOR)[1];
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

			locationSummary.kiosklanguageSetting(locationName, language.get(0),language.get(1));
			
			foundation.threadWait(Constants.SHORT_TIME);
			// login into Kiosk Device
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
						
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(requiredData.get(3));
			foundation.click(ProductSearch.BTN_PRODUCT);
			Assert.assertTrue(foundation.isDisplayed(Order.BTN_CANCEL_ORDER));
						
			Assert.assertTrue(requiredData.get(3).equals(foundation.getText(Order.LBL_PROMOTION_NAME)));
						
			// verify the display of total section
			String productPrice = foundation.getText(Order.LBL_PRODUCT_PRICE).split(Constants.DOLLAR)[1];
			assertTrue(foundation.getText(Order.LBL_BALANCE_DUE).contains(String.valueOf(priceTotal)));
			assertTrue(foundation.getText(Order.LBL_SUB_TOTAL).contains(priceTotal));
			            
			List<String> orderPageData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.ORDER_PAGE).split(Constants.DELIMITER_TILD));
			List<String> paymentPageData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.PAYMENTS_PAGE).split(Constants.DELIMITER_TILD));
			Assert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(0))));
			foundation.objectFocus(order.objText(orderPageData.get(1)));
			order.completeOrder(orderPageData.get(1),paymentPageData.get(0), paymentPageData.get(1));
			                  
			browser.close();
			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
						
			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(navigationMenu.get(0));
					
			// Deleting the Promotion
			editPromotion.expirePromotion(gridName, promotionName);

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
	
	@Test(description = "143069-Validate v5 transactions with Discount Amount exceeds Bundle Price for Bundle Promotion")
	public void discountExceedsbundlePromotion() {
		try {
			final String CASE_NUM = "143069";

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
			String locationName = rstLocationData.get(CNLocation.LOCATION_NAME);
			String gridName = rstLocationData.get(CNLocation.TAB_NAME);

			List<String> navigationMenu = Arrays.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
			List<String> requiredData = Arrays.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> language = Arrays.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
			
			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(navigationMenu.get(0));

			// New Promotion
			createPromotions.BundlePromotion(promotionType, promotionName, displayName, requiredData.get(0),locationName);
			createPromotions.selectBundlePromotionDetails(requiredData.get(1), requiredData.get(2), requiredData.get(5),rstV5DeviceData.get(CNLocation.REQUIRED_DATA));
			createPromotions.selectBundlePromotionPricing(requiredData.get(6));
			foundation.click(CreatePromotions.RB_BUNDLE_AMOUNT);
			foundation.click(CreatePromotions.CHK_BUNDLE_OVERFLOW);
			createPromotions.selectBundlePromotionTimes(requiredData.get(4),Constants.DELIMITER_SPACE);
						
			String priceTotal=foundation.getText(CreatePromotions.LBL_TOTAL_PRICE);
			String bundleDiscount= foundation.getText(CreatePromotions.LBL_BUNDLE_DISCOUNT);
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
			//foundation.click(LocationSummary.BTN_SAVE);
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
			foundation.waitforClikableElement(OrgSummary.DPD_COUNTRY,Constants.SHORT_TIME);
			dropDown.selectItem(OrgSummary.DPD_COUNTRY, rstOrgSummaryData.get(CNOrgSummary.REQUIRED_DATA), Constants.TEXT);
			dropDown.selectItem(OrgSummary.DPD_TAX_SYSTEM, rstOrgSummaryData.get(CNOrgSummary.REQUIRED_DATA), Constants.TEXT);
			foundation.click(OrgSummary.BTN_SAVE);
			foundation.waitforClikableElement(OrgList.LBL_ORG_LIST,Constants.SHORT_TIME);

			navigationBar.navigateToMenuItem(navigationMenu.get(2));
		
			locationSummary.kiosklanguageSetting(locationName, language.get(0),language.get(1));
			
			foundation.threadWait(Constants.SHORT_TIME);
			// login into Kiosk Device
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
						
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(requiredData.get(3));
			foundation.click(ProductSearch.BTN_PRODUCT);
			Assert.assertTrue(foundation.isDisplayed(Order.BTN_CANCEL_ORDER));
			
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(requiredData.get(7));
			foundation.click(ProductSearch.BTN_PRODUCT);
			Assert.assertTrue(foundation.isDisplayed(Order.BTN_CANCEL_ORDER));
						
			Assert.assertTrue(displayName.equals(foundation.getText(Order.LBL_PROMOTION_NAME)));
			List<String> discountList=foundation.getTextofListElement(Order.LBL_ORDER_DISCOUNT);
			Assert.assertTrue(discountList.get(2).equals(foundation.getText(Order.LBL_PRODUCT_PRICE)));
			
			List<String> subProductPrice=foundation.getTextofListElement(Order.LBL_MULTI_PRODUCTS);
			// verify the display of total section
			Double totalProductPrice = Double.parseDouble(subProductPrice.get(0).split(Constants.DOLLAR)[1]) + Double.parseDouble(subProductPrice.get(1).split(Constants.DOLLAR)[1]);
			String productPrice=String.format("%.2f",totalProductPrice);
			//String discount = foundation.getText(Order.LBL_DEPOSIT).split(Constants.DOLLAR)[1];
			Double expectedBalanceDue = totalProductPrice - totalProductPrice;
			String balanceDue=String.format("%.2f",expectedBalanceDue);
			assertTrue(foundation.getText(Order.LBL_BALANCE_DUE).contains(balanceDue));
			assertTrue(foundation.getText(Order.LBL_SUB_TOTAL).contains(String.valueOf(productPrice)));
			assertTrue(foundation.getText(Order.LBL_DISCOUNT).contains(String.valueOf(productPrice)));
			            
			List<String> orderPageData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.ORDER_PAGE).split(Constants.DELIMITER_TILD));
			List<String> paymentPageData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.PAYMENTS_PAGE).split(Constants.DELIMITER_TILD));
			Assert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(0))));
			foundation.objectFocus(order.objText(orderPageData.get(1)));
			order.completeOrder(orderPageData.get(1),paymentPageData.get(0), paymentPageData.get(1));
			                  
			browser.close();
			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
						
			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(navigationMenu.get(0));
					
			// Deleting the Promotion
			editPromotion.expirePromotion(gridName, promotionName);

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
	
	@Test(description = "143070-Validate v5 transactions without Discount when different device type is selected for Bundle Promotion")
	public void devicebundlePromotion() {
		try {
			final String CASE_NUM = "143070";

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
			String locationName = rstLocationData.get(CNLocation.LOCATION_NAME);
			String gridName = rstLocationData.get(CNLocation.TAB_NAME);

			List<String> navigationMenu = Arrays.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
			List<String> requiredData = Arrays.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> language = Arrays.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
			
			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(navigationMenu.get(0));

			// New Promotion
			foundation.waitforElement(PromotionList.BTN_CREATE,Constants.SHORT_TIME);
			foundation.click(PromotionList.BTN_CREATE);
			foundation.isDisplayed(CreatePromotions.LBL_CREATE_PROMOTION);
			createPromotions.newPromotion(promotionType, promotionName, displayName, requiredData.get(0),locationName);
			
			dropDown.selectItem(CreatePromotions.DPD_DEVICE,  requiredData.get(7), Constants.TEXT);
			foundation.click(CreatePromotions.BTN_NEXT);
			createPromotions.selectBundlePromotionDetails(requiredData.get(1), requiredData.get(2), requiredData.get(5),rstV5DeviceData.get(CNLocation.REQUIRED_DATA));
			createPromotions.selectBundlePromotionPricing(requiredData.get(6));
			
			foundation.click(CreatePromotions.RB_BUNDLE_PRICE);
			createPromotions.selectBundlePromotionTimes(requiredData.get(4),Constants.DELIMITER_SPACE);
						
			String priceTotal=foundation.getText(CreatePromotions.LBL_TOTAL_PRICE);
			String bundleDiscount= foundation.getText(CreatePromotions.LBL_BUNDLE_DISCOUNT);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_OK);
						
			foundation.waitforElement(PromotionList.TXT_SEARCH_PROMONAME, Constants.SHORT_TIME);			
			navigationBar.navigateToMenuItem(navigationMenu.get(2));
						
			// Selecting location
			locationList.selectLocationName(locationName);

			foundation.click(LocationSummary.BTN_SYNC);
			//foundation.click(LocationSummary.BTN_SAVE);
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
			foundation.waitforClikableElement(OrgSummary.DPD_COUNTRY,Constants.SHORT_TIME);
			dropDown.selectItem(OrgSummary.DPD_COUNTRY, rstOrgSummaryData.get(CNOrgSummary.REQUIRED_DATA), Constants.TEXT);
			dropDown.selectItem(OrgSummary.DPD_TAX_SYSTEM, rstOrgSummaryData.get(CNOrgSummary.REQUIRED_DATA), Constants.TEXT);
			foundation.click(OrgSummary.BTN_SAVE);
			foundation.waitforClikableElement(OrgList.LBL_ORG_LIST,Constants.SHORT_TIME);

			navigationBar.navigateToMenuItem(navigationMenu.get(2));
			locationSummary.kiosklanguageSetting(locationName, language.get(0),language.get(1));
						
			foundation.threadWait(Constants.SHORT_TIME);
			// login into Kiosk Device
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
						
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(requiredData.get(3));
			foundation.click(ProductSearch.BTN_PRODUCT);
			Assert.assertTrue(foundation.isDisplayed(Order.BTN_CANCEL_ORDER));
						
			Assert.assertTrue(displayName.equals(foundation.getText(Order.LBL_PROMOTION_NAME)));
			List<String> discountList=foundation.getTextofListElement(Order.LBL_ORDER_DISCOUNT);
			Assert.assertTrue(discountList.get(2).contains(bundleDiscount));

			// verify the display of total section
            String productPrice = foundation.getText(Order.LBL_PRODUCT_PRICE).split(Constants.DOLLAR)[1];
            
            //remove next 5 lines and uncomment next 2 lines
            String discount = foundation.getText(Order.LBL_DEPOSIT).split(Constants.DOLLAR)[1];
            Double expectedBalanceDue = Double.parseDouble(productPrice) - Double.parseDouble(discount);
            assertTrue(foundation.getText(Order.LBL_BALANCE_DUE).contains(String.valueOf(expectedBalanceDue)));
            assertTrue(foundation.getText(Order.LBL_SUB_TOTAL).contains(productPrice));
            assertTrue(foundation.getText(Order.LBL_DISCOUNT).contains(discount));
			
            //assertTrue(foundation.getText(Order.LBL_BALANCE_DUE).contains(String.valueOf(productPrice)));
            //assertTrue(foundation.getText(Order.LBL_SUB_TOTAL).contains(productPrice));
            
			List<String> orderPageData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.ORDER_PAGE).split(Constants.DELIMITER_TILD));
			List<String> paymentPageData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.PAYMENTS_PAGE).split(Constants.DELIMITER_TILD));
			Assert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(0))));
			foundation.objectFocus(order.objText(orderPageData.get(1)));
			order.completeOrder(orderPageData.get(1),paymentPageData.get(0), paymentPageData.get(1));
			                  
			browser.close();
			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
						
			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(navigationMenu.get(0));
					
			// Deleting the Promotion
			editPromotion.expirePromotion(gridName, promotionName);

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
	
	@Test(description = "143071-Validate v5 transactions with Active Onscreen promotions with Recurrence")
	public void onScreenRecurrence() {
		try {
			final String CASE_NUM = "143071";
			
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
			String locationName = rstLocationData.get(CNLocation.LOCATION_NAME);
			String gridName = rstLocationData.get(CNLocation.TAB_NAME);

			List<String> navigationMenu = Arrays.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
			List<String> requiredData = Arrays.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> language = Arrays.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
			
			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(navigationMenu.get(0));

			// New Promotion
			createPromotions.BundlePromotion(promotionType, promotionName, displayName, requiredData.get(0),locationName);
			createPromotions.selectBundlePromotionDetails(requiredData.get(1), requiredData.get(2), requiredData.get(5),rstV5DeviceData.get(CNLocation.REQUIRED_DATA));
			createPromotions.selectBundlePromotionTimes(requiredData.get(3),Constants.DELIMITER_SPACE);
			createPromotions.recurringDay();
			
			String onScreenDiscount= foundation.getAttributeValue(CreatePromotions.TXT_AMOUNT);
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
			foundation.waitforClikableElement(OrgSummary.DPD_COUNTRY,Constants.SHORT_TIME);
			dropDown.selectItem(OrgSummary.DPD_COUNTRY, rstOrgSummaryData.get(CNOrgSummary.REQUIRED_DATA), Constants.TEXT);
			dropDown.selectItem(OrgSummary.DPD_TAX_SYSTEM, rstOrgSummaryData.get(CNOrgSummary.REQUIRED_DATA), Constants.TEXT);
			foundation.click(OrgSummary.BTN_SAVE);
			foundation.waitforClikableElement(OrgList.LBL_ORG_LIST,Constants.SHORT_TIME);

			navigationBar.navigateToMenuItem(navigationMenu.get(2));
			locationSummary.kiosklanguageSetting(locationName, language.get(0),language.get(1));
			
			foundation.threadWait(Constants.SHORT_TIME);
			// login into Kiosk Device
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(requiredData.get(2));
			foundation.click(ProductSearch.BTN_PRODUCT);
			Assert.assertTrue(foundation.isDisplayed(Order.BTN_CANCEL_ORDER));
			
			Assert.assertTrue(displayName.equals(foundation.getText(Order.LBL_DISCOUNT_NAME)));
			List<String> discountList=foundation.getTextofListElement(Order.LBL_ORDER_DISCOUNT);
			Assert.assertTrue(discountList.get(2).contains(onScreenDiscount));

			// verify the display of total section
            String productPrice = foundation.getText(Order.LBL_PRODUCT_PRICE).split(Constants.DOLLAR)[1];
            String discount = foundation.getText(Order.LBL_DEPOSIT).split(Constants.DOLLAR)[1];
            Double expectedBalanceDue = Double.parseDouble(productPrice) - Double.parseDouble(discount);
            assertTrue(foundation.getText(Order.LBL_BALANCE_DUE).contains(String.valueOf(expectedBalanceDue)));
            assertTrue(foundation.getText(Order.LBL_SUB_TOTAL).contains(productPrice));
            assertTrue(foundation.getText(Order.LBL_DISCOUNT).contains(onScreenDiscount));
            
            List<String> orderPageData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.ORDER_PAGE).split(Constants.DELIMITER_TILD));
			List<String> paymentPageData = Arrays
			.asList(rstV5DeviceData.get(CNV5Device.PAYMENTS_PAGE).split(Constants.DELIMITER_TILD));
			Assert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(0))));
            foundation.objectFocus(order.objText(orderPageData.get(1)));
            order.completeOrder(orderPageData.get(1),paymentPageData.get(0), paymentPageData.get(1));
                  
            browser.close();
            browser.launch(Constants.LOCAL, Constants.CHROME);
            browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			
			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(navigationMenu.get(0));
		
			// Deleting the Promotion
			editPromotion.expirePromotion(gridName, promotionName);

		} catch (Exception exc) {
			Assert.fail(exc.toString());
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
			String locationName = rstLocationData.get(CNLocation.LOCATION_NAME);
			String gridName = rstLocationData.get(CNLocation.TAB_NAME);

			List<String> navigationMenu = Arrays.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
			List<String> requiredData = Arrays.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> language = Arrays.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
			
			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(navigationMenu.get(0));

			// New Promotion
			createPromotions.BundlePromotion(promotionType, promotionName, displayName, requiredData.get(0),locationName);
			createPromotions.selectBundlePromotionDetails(requiredData.get(1), requiredData.get(2), requiredData.get(5),rstV5DeviceData.get(CNLocation.REQUIRED_DATA));
			createPromotions.selectBundlePromotionTimes(requiredData.get(3),Constants.DELIMITER_SPACE);
			createPromotions.recurringDay();
			
			//String onScreenDiscount= foundation.getAttributeValue(CreatePromotions.TXT_AMOUNT);
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
			foundation.waitforClikableElement(OrgSummary.DPD_COUNTRY,Constants.SHORT_TIME);
			dropDown.selectItem(OrgSummary.DPD_COUNTRY, rstOrgSummaryData.get(CNOrgSummary.REQUIRED_DATA), Constants.TEXT);
			dropDown.selectItem(OrgSummary.DPD_TAX_SYSTEM, rstOrgSummaryData.get(CNOrgSummary.REQUIRED_DATA), Constants.TEXT);
			foundation.click(OrgSummary.BTN_SAVE);
			foundation.waitforClikableElement(OrgList.LBL_ORG_LIST,Constants.SHORT_TIME);

			navigationBar.navigateToMenuItem(navigationMenu.get(2));
			locationSummary.kiosklanguageSetting(locationName, language.get(0),language.get(1));
			
			foundation.threadWait(Constants.SHORT_TIME);
			// login into Kiosk Device
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(requiredData.get(2));
			foundation.click(ProductSearch.BTN_PRODUCT);
			Assert.assertTrue(foundation.isDisplayed(Order.BTN_CANCEL_ORDER));
			
			Assert.assertTrue(requiredData.get(2).equals(foundation.getText(Order.LBL_PROMOTION_NAME)));

			// verify the display of total section
            String productPrice = foundation.getText(Order.LBL_PRODUCT_PRICE).split(Constants.DOLLAR)[1];
            assertTrue(foundation.getText(Order.LBL_BALANCE_DUE).contains(String.valueOf(productPrice)));
            assertTrue(foundation.getText(Order.LBL_SUB_TOTAL).contains(productPrice));

            
            List<String> orderPageData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.ORDER_PAGE).split(Constants.DELIMITER_TILD));
			List<String> paymentPageData = Arrays
			.asList(rstV5DeviceData.get(CNV5Device.PAYMENTS_PAGE).split(Constants.DELIMITER_TILD));
			Assert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(0))));
            foundation.objectFocus(order.objText(orderPageData.get(1)));
            order.completeOrder(orderPageData.get(1),paymentPageData.get(0), paymentPageData.get(1));
                  
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
	
	@Test(description = "143073-Validate v5 transactions with Discount on multiple Line items with Flash Sale for Onscreen Promotion")
	public void recurrenceOnScreenPromotion() {
		try { 
			final String CASE_NUM = "143073";
			
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
			String locationName = rstLocationData.get(CNLocation.LOCATION_NAME);
			String gridName = rstLocationData.get(CNLocation.TAB_NAME);

			List<String> navigationMenu = Arrays.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
			List<String> requiredData = Arrays.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> language = Arrays.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
			
			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(navigationMenu.get(0));

			// New Promotion
			createPromotions.BundlePromotion(promotionType, promotionName, displayName, requiredData.get(0),locationName);
			createPromotions.selectBundlePromotionDetails(requiredData.get(1), requiredData.get(2), requiredData.get(6),rstV5DeviceData.get(CNLocation.REQUIRED_DATA));

			textBox.enterText(CreatePromotions.TXT_ITEM1, requiredData.get(3));
			foundation.threadWait(Constants.ONE_SECOND);
			textBox.enterText(CreatePromotions.TXT_ITEM1, Keys.ENTER);
			foundation.threadWait(Constants.TWO_SECOND);
			dropDown.selectItem(CreatePromotions.DPD_DISCOUNT_TYPE, requiredData.get(7), Constants.TEXT);
			textBox.enterText(CreatePromotions.TXT_DISCOUNT_PERCENTAGE, requiredData.get(5));
			
			createPromotions.selectBundlePromotionTimes(requiredData.get(4),requiredData.get(8));
			
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

			locationSummary.kiosklanguageSetting(locationName, language.get(0),language.get(1));
			
			foundation.threadWait(Constants.SHORT_TIME);
			// login into Kiosk Device
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(requiredData.get(2));
			foundation.click(ProductSearch.BTN_PRODUCT);
			Assert.assertTrue(foundation.isDisplayed(Order.BTN_CANCEL_ORDER));
			
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(requiredData.get(3));
			foundation.click(ProductSearch.BTN_PRODUCT);
			Assert.assertTrue(foundation.isDisplayed(Order.BTN_CANCEL_ORDER));

			List<String> productPrice=foundation.getTextofListElement(Order.LBL_MULTI_PRODUCTS);
			List<String> discountList=foundation.getTextofListElement(Order.LBL_ORDER_DISCOUNT);
			
			Double expectedBalanceDue = Double.parseDouble(productPrice.get(0).replaceAll(Constants.REPLACE_DOLLOR,Constants.EMPTY_STRING))+Double.parseDouble(productPrice.get(1).replaceAll(Constants.REPLACE_DOLLOR,Constants.EMPTY_STRING))
				- Double.parseDouble(discountList.get(2).replaceAll(Constants.REPLACE_DOLLOR,Constants.EMPTY_STRING)) -Double.parseDouble(discountList.get(6).replaceAll(Constants.REPLACE_DOLLOR,Constants.EMPTY_STRING));

            assertTrue(foundation.getText(Order.LBL_BALANCE_DUE).contains(String.valueOf(expectedBalanceDue)));
            double subToal = Double.parseDouble(productPrice.get(0).replaceAll(Constants.REPLACE_DOLLOR,Constants.EMPTY_STRING))+Double.parseDouble(productPrice.get(1).replaceAll(Constants.REPLACE_DOLLOR,Constants.EMPTY_STRING));
            double expectedTotalRoundUp = Math.round(subToal*100.0)/100.0;
            assertTrue(foundation.getText(Order.LBL_SUB_TOTAL).contains(String.valueOf(expectedTotalRoundUp)));
            assertTrue(foundation.getText(Order.LBL_DISCOUNT).contains(String.valueOf(Double.parseDouble(discountList.get(2).replaceAll(Constants.REPLACE_DOLLOR,Constants.EMPTY_STRING)) +Double.parseDouble(discountList.get(6).replaceAll(Constants.REPLACE_DOLLOR,Constants.EMPTY_STRING)))));
 
           
           List<String> orderPageData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.ORDER_PAGE).split(Constants.DELIMITER_TILD));
            List<String> paymentPageData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.PAYMENTS_PAGE).split(Constants.DELIMITER_TILD));
			Assert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(0))));
            foundation.objectFocus(order.objText(orderPageData.get(1)));
			order.completeOrder(orderPageData.get(1),paymentPageData.get(0), paymentPageData.get(1));
                  
            browser.close();
            browser.launch(Constants.LOCAL, Constants.CHROME);
            browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			
			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(navigationMenu.get(0));
		
			// Deleting the Promotion
			editPromotion.expirePromotion(gridName, promotionName);

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
	
	@Test(description = "143074-Validate v5 transactions with Discount Category with Scheduled for Onscreen Promotion")
	public void scheduledOnScreenPromotion() {
		try { 
			final String CASE_NUM = "143074";
			
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
			String locationName = rstLocationData.get(CNLocation.LOCATION_NAME);
			String gridName = rstLocationData.get(CNLocation.TAB_NAME);

			List<String> navigationMenu = Arrays.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
			List<String> requiredData = Arrays.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> language = Arrays.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
			
			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(navigationMenu.get(0));

			// New Promotion
			createPromotions.BundlePromotion(promotionType, promotionName, displayName, requiredData.get(0),locationName);
			createPromotions.selectBundlePromotionDetails(requiredData.get(1), requiredData.get(2), requiredData.get(8),rstV5DeviceData.get(CNLocation.REQUIRED_DATA));
			
			createPromotions.selectBundlePromotionTimes(requiredData.get(4),requiredData.get(5));
			String onScreenDiscount= foundation.getAttributeValue(CreatePromotions.TXT_AMOUNT);
			
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

			locationSummary.kiosklanguageSetting(locationName, language.get(0),language.get(1));
			
			foundation.threadWait(Constants.SHORT_TIME);
			// login into Kiosk Device
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(requiredData.get(3));
			foundation.click(ProductSearch.BTN_PRODUCT);
			Assert.assertTrue(foundation.isDisplayed(Order.BTN_CANCEL_ORDER));
			
			Assert.assertTrue(displayName.equals(foundation.getText(Order.LBL_DISCOUNT_NAME)));
			List<String> discountList=foundation.getTextofListElement(Order.LBL_ORDER_DISCOUNT);
			Assert.assertTrue(discountList.get(2).contains(onScreenDiscount));

			// verify the display of total section
            String productPrice = foundation.getText(Order.LBL_PRODUCT_PRICE).split(Constants.DOLLAR)[1];
            String discount = foundation.getText(Order.LBL_DEPOSIT).split(Constants.DOLLAR)[1];
            Double expectedBalanceDue = Double.parseDouble(productPrice) - Double.parseDouble(discount);
            assertTrue(foundation.getText(Order.LBL_BALANCE_DUE).contains(String.valueOf(expectedBalanceDue)));
            assertTrue(foundation.getText(Order.LBL_SUB_TOTAL).contains(productPrice));
            assertTrue(foundation.getText(Order.LBL_DISCOUNT).contains(onScreenDiscount));
           
           
           List<String> orderPageData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.ORDER_PAGE).split(Constants.DELIMITER_TILD));
            List<String> paymentPageData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.PAYMENTS_PAGE).split(Constants.DELIMITER_TILD));
			Assert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(0))));
            foundation.objectFocus(order.objText(orderPageData.get(1)));
			order.completeOrder(orderPageData.get(1),paymentPageData.get(0), paymentPageData.get(1));
                  
            browser.close();
            browser.launch(Constants.LOCAL, Constants.CHROME);
            browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			
			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(navigationMenu.get(0));
		
			// Deleting the Promotion
			dropDown.selectItem(PromotionList.DPD_STATUS, requiredData.get(4), Constants.TEXT);
			foundation.click(PromotionList.BTN_SEARCH);
			editPromotion.expirePromotion(gridName, promotionName);

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
	
	@Test(description = "143075-Validate v5 transactions with Min Transaction amount for Onscreen Promotion")
	public void minTransactionOnScreenPromotion() {
		try { 
			final String CASE_NUM = "143075";
			
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
			String locationName = rstLocationData.get(CNLocation.LOCATION_NAME);
			String gridName = rstLocationData.get(CNLocation.TAB_NAME);

			List<String> navigationMenu = Arrays.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
			List<String> requiredData = Arrays.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> language = Arrays.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
			
			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(navigationMenu.get(0));

			// New Promotion
			createPromotions.BundlePromotion(promotionType, promotionName, displayName, requiredData.get(0),locationName);
			createPromotions.selectBundlePromotionDetails(requiredData.get(1), requiredData.get(2), requiredData.get(7),rstV5DeviceData.get(CNLocation.REQUIRED_DATA));
			
			createPromotions.selectBundlePromotionTimes(requiredData.get(4),Constants.DELIMITER_SPACE);
			String onScreenDiscount= foundation.getAttributeValue(CreatePromotions.TXT_AMOUNT);
			
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

			locationSummary.kiosklanguageSetting(locationName, language.get(0),language.get(1));
			
			foundation.threadWait(Constants.SHORT_TIME);
			// login into Kiosk Device
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(requiredData.get(3));
			foundation.click(ProductSearch.BTN_PRODUCT);
			Assert.assertTrue(foundation.isDisplayed(Order.BTN_CANCEL_ORDER));
			
			Assert.assertTrue(displayName.equals(foundation.getText(Order.LBL_DISCOUNT_NAME)));
			List<String> discountList=foundation.getTextofListElement(Order.LBL_ORDER_DISCOUNT);
			Assert.assertTrue(discountList.get(2).contains(onScreenDiscount));

			// verify the display of total section
            String productPrice = foundation.getText(Order.LBL_PRODUCT_PRICE).split(Constants.DOLLAR)[1];
            String discount = foundation.getText(Order.LBL_DEPOSIT).split(Constants.DOLLAR)[1];
            Double expectedBalanceDue = Double.parseDouble(productPrice) - Double.parseDouble(discount);
            assertTrue(foundation.getText(Order.LBL_BALANCE_DUE).contains(String.valueOf(expectedBalanceDue)));
            assertTrue(foundation.getText(Order.LBL_SUB_TOTAL).contains(productPrice));
            assertTrue(foundation.getText(Order.LBL_DISCOUNT).contains(onScreenDiscount));
           
           
           List<String> orderPageData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.ORDER_PAGE).split(Constants.DELIMITER_TILD));
            List<String> paymentPageData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.PAYMENTS_PAGE).split(Constants.DELIMITER_TILD));
			Assert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(0))));
            foundation.objectFocus(order.objText(orderPageData.get(1)));
			order.completeOrder(orderPageData.get(1),paymentPageData.get(0), paymentPageData.get(1));
                  
            browser.close();
            browser.launch(Constants.LOCAL, Constants.CHROME);
            browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			
			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(navigationMenu.get(0));
		
			// Deleting the Promotion
			dropDown.selectItem(PromotionList.DPD_STATUS, requiredData.get(4), Constants.TEXT);
			foundation.click(PromotionList.BTN_SEARCH);
			editPromotion.expirePromotion(gridName, promotionName);

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
}
