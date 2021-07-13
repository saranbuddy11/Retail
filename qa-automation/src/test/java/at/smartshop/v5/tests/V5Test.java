package at.smartshop.v5.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import at.framework.database.mssql.Queries;
import at.framework.database.mssql.ResultSets;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.database.columns.CNLocationList;
import at.smartshop.database.columns.CNNavigationMenu;
import at.smartshop.database.columns.CNV5Device;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.pages.GlobalProduct;
import at.smartshop.pages.LocationList;
import at.smartshop.pages.LocationSummary;
import at.smartshop.pages.NavigationBar;
import at.smartshop.pages.OrgList;
import at.smartshop.pages.OrgSummary;
import at.smartshop.pages.ProductSummary;
import at.smartshop.tests.TestInfra;
import at.smartshop.utilities.CurrenyConverter;
import at.smartshop.pages.DeviceSummary;
import at.smartshop.pages.LocationList;
import at.smartshop.pages.LocationSummary;
import at.smartshop.pages.NavigationBar;
import at.smartshop.tests.TestInfra;
import at.smartshop.v5.pages.AccountDetails;
import at.smartshop.v5.pages.AccountLogin;
import at.smartshop.v5.pages.AdminMenu;
import at.smartshop.v5.pages.CardPayment;
import at.smartshop.v5.pages.ChangePin;
import at.smartshop.v5.pages.CreateAccount;
import at.smartshop.v5.pages.EditAccount;
import at.smartshop.v5.pages.FingerPrintPayment;
import at.smartshop.v5.pages.FundAccount;
import at.smartshop.v5.pages.LandingPage;
import at.smartshop.v5.pages.Order;
import at.smartshop.v5.pages.ProductSearch;
import at.smartshop.v5.pages.Payments;
import at.smartshop.v5.pages.ProductSearch;
import at.smartshop.v5.pages.ScanPayment;

@Listeners(at.framework.reportsetup.Listeners.class)
public class V5Test extends TestInfra {
	private Foundation foundation = new Foundation();
	private TextBox textBox = new TextBox();
	private ResultSets dataBase = new ResultSets();
	private LandingPage landingPage = new LandingPage();
	private AccountLogin accountLogin = new AccountLogin();
	private EditAccount editAccount = new EditAccount();
	private CurrenyConverter currenyConverter = new CurrenyConverter();
	private NavigationBar navigationBar = new NavigationBar();
	private GlobalProduct globalProduct = new GlobalProduct();
	private LocationList locationList = new LocationList();
	private LocationSummary locationSummary = new LocationSummary();
	private Dropdown dropdown = new Dropdown();
	private AdminMenu adminMenu = new AdminMenu();
	private DeviceSummary deviceSummary = new DeviceSummary();
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
	private Payments payments = new Payments();
	private OrgList orgList=new OrgList();

	private Map<String, String> rstV5DeviceData;
	private Map<String, String> rstNavigationMenuData;
	private Map<String, String> rstLocationListData;

	@Test(description = "141874-Kiosk Manage Account > Edit Account > Update Information")
	public void editAccountUpdateInformation() {
		try {
			final String CASE_NUM = "141874";

			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			List<String> requiredData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Selecting location
			locationList.selectLocationName(requiredData.get(0));
			dropDown.selectItem(LocationSummary.DPD_KIOSK_LANGUAGE, requiredData.get(1), Constants.TEXT);

			foundation.click(LocationSummary.BTN_SYNC);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_FILTER, Constants.SHORT_TIME);
			login.logout();
			browser.close();

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
			List<String> actualData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.ACTUAL_DATA).split(Constants.DELIMITER_TILD));
			editAccount.updateText(EditAccount.TXT_FIRST_NAME, actualData.get(0), firstName);
			editAccount.updateText(EditAccount.TXT_LAST_NAME, actualData.get(1), lastName);
			editAccount.updateText(EditAccount.TXT_EMAIL_ADDRESS, actualData.get(2), emailAddress);
			foundation.click(EditAccount.BTN_SAVE);
			assertTrue(foundation.isDisplayed(EditAccount.BTN_EDIT_ACCOUNT));

			// navigate back to edit account and verify all data are populating as entered
			// before
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

	@Test(description = "C142741 - SOS-24492 Verify alternate language is set to Finnish in Kiosk when user set the Alternate Language as Finnish and full sync is done in ADM")
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

	@Test(description = "C142742 -Verify default and Alternative languages in Kiosk when user sets same language and full sync is done in ADM")
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

	@Test(description = "C141867 - This test validates the Driver Login and Log Out")
	public void verifyDriverLoginLogout() {
		try {

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
		final String CASE_NUM = "141875";

		browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));

		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		List<String> requiredData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));

		// login to application
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
	}

	@Test(description = "141887-Kiosk Checkout UI > Canceling Cart")
	public void cancellingCart() {
		final String CASE_NUM = "141887";

		browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));

		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		List<String> requiredData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> actualData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.ACTUAL_DATA).split(Constants.DELIMITER_TILD));

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
	}

	@Test(description = "141889-Kiosk Checkout UI > Taxes Applied")
	public void taxesApplied() {

		final String CASE_NUM = "141889";

		browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));

		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		List<String> requiredData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> actualData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.ACTUAL_DATA).split(Constants.DELIMITER_TILD));

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
	}

	@Test(description = "141890-Kiosk Checkout UI > Bottle Deposits Applied")
	public void bottleDepositApplied() {
		final String CASE_NUM = "141890";

		browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));

		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		List<String> requiredData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> actualData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.ACTUAL_DATA).split(Constants.DELIMITER_TILD));

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
	}

	@Test(description = "142696-SOS-24494-V5 -validate the search functionality for product search")
	public void searchFunctionalityProduct() {
		final String CASE_NUM = "142696";

		browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));

		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		List<String> requiredData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> actualData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.ACTUAL_DATA).split(Constants.DELIMITER_TILD));

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
	}

	@Test(description = "142697-SOS-24494-V5 -validate the search functionality for scan code search")
	public void searchFunctionalityScancode() {
		final String CASE_NUM = "142697";

		browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));

		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		List<String> requiredData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> actualData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.ACTUAL_DATA).split(Constants.DELIMITER_TILD));

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

		// launch browser and select org
		browser.close();
		browser.launch(Constants.LOCAL, Constants.CHROME);
		browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
		login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
				propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
		navigationBar.selectOrganization(
				propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

		// navigate to global product of V5 associated and update name and sync
		navigationBar.navigateToMenuItem(menuItem.get(0));
		foundation.threadWait(1000);
		textBox.enterText(GlobalProduct.TXT_FILTER, requiredData.get(0));
		foundation.click(globalProduct.getGlobalProduct(requiredData.get(1)));
		textBox.enterText(ProductSummary.TXT_PRODUCT_NAME, requiredData.get(2));
		foundation.click(ProductSummary.BTN_SAVE);
		navigationBar.navigateToMenuItem(menuItem.get(1));
		textBox.enterText(LocationList.TXT_FILTER, requiredData.get(3));
		locationList.selectLocationName(requiredData.get(3));
		foundation.click(LocationSummary.BTN_FULL_SYNC);
		browser.close();

		// launch v5 application
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
		navigationBar.selectOrganization(
				propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
		navigationBar.navigateToMenuItem(menuItem.get(0));
		foundation.threadWait(2000);
		textBox.enterText(GlobalProduct.TXT_FILTER, requiredData.get(0));
		foundation.click(globalProduct.getGlobalProduct(requiredData.get(2)));
		textBox.enterText(ProductSummary.TXT_PRODUCT_NAME, requiredData.get(1));
		foundation.click(ProductSummary.BTN_SAVE);

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

		// launch browser and select org
		browser.close();
		browser.launch(Constants.LOCAL, Constants.CHROME);
		browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
		login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
				propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
		navigationBar.selectOrganization(
				propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

		// navigate to global product of V5 associated and update tax category and sync
		navigationBar.navigateToMenuItem(menuItem.get(0));
		foundation.threadWait(3000);
		textBox.enterText(GlobalProduct.TXT_FILTER, requiredData.get(0));
		foundation.click(globalProduct.getGlobalProduct(requiredData.get(1)));
		dropdown.selectItem(ProductSummary.DPD_TAX_CATEGORY, requiredData.get(2), Constants.TEXT);
		foundation.click(ProductSummary.BTN_SAVE);
		navigationBar.navigateToMenuItem(menuItem.get(1));
		textBox.enterText(LocationList.TXT_FILTER, requiredData.get(3));
		locationList.selectLocationName(requiredData.get(3));
		foundation.click(LocationSummary.BTN_FULL_SYNC);
		browser.close();

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
		browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
		login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
				propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
		navigationBar.selectOrganization(
				propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
		navigationBar.navigateToMenuItem(menuItem.get(0));
		foundation.threadWait(3000);
		textBox.enterText(GlobalProduct.TXT_FILTER, requiredData.get(0));
		foundation.click(globalProduct.getGlobalProduct(requiredData.get(1)));
		dropdown.selectItem(ProductSummary.DPD_TAX_CATEGORY, requiredData.get(7), Constants.TEXT);
		foundation.click(ProductSummary.BTN_SAVE);

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

		// launch browser and select org
		browser.close();
		browser.launch(Constants.LOCAL, Constants.CHROME);
		browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
		login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
				propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
		navigationBar.selectOrganization(
				propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

		// navigate to global product of V5 associated and update deposit and sync
		navigationBar.navigateToMenuItem(menuItem.get(0));
		foundation.threadWait(2000);
		textBox.enterText(GlobalProduct.TXT_FILTER, requiredData.get(0));
		foundation.click(globalProduct.getGlobalProduct(requiredData.get(1)));
		dropdown.selectItem(ProductSummary.DPD_DEPOSIT_CATEGORY, requiredData.get(2), Constants.TEXT);
		foundation.click(ProductSummary.BTN_SAVE);
		navigationBar.navigateToMenuItem(menuItem.get(1));
		textBox.enterText(LocationList.TXT_FILTER, requiredData.get(3));
		locationList.selectLocationName(requiredData.get(3));
		foundation.click(LocationSummary.BTN_FULL_SYNC);
		browser.close();

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
		browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
		login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
				propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
		navigationBar.selectOrganization(
				propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
		navigationBar.navigateToMenuItem(menuItem.get(0));
		foundation.threadWait(2000);
		textBox.enterText(GlobalProduct.TXT_FILTER, requiredData.get(0));
		foundation.click(globalProduct.getGlobalProduct(requiredData.get(1)));
		dropdown.selectItem(ProductSummary.DPD_DEPOSIT_CATEGORY, requiredData.get(7), Constants.TEXT);
		foundation.click(ProductSummary.BTN_SAVE);

	}

	@Test(description = "142718-SOS-24494-V5 -Edit cost/price of the product and verify it on Kiosk machine cart page")
	public void editPrice() {
		final String CASE_NUM = "142718";

		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		List<String> requiredData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		String menuItem = rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM);

		// launch browser and select org
		browser.close();
		browser.launch(Constants.LOCAL, Constants.CHROME);
		browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
		login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
				propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
		navigationBar.selectOrganization(
				propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

		// navigate to location summary and update price and sync
		navigationBar.navigateToMenuItem(menuItem);
		textBox.enterText(LocationList.TXT_FILTER, requiredData.get(3));
		locationList.selectLocationName(requiredData.get(3));
		locationSummary.selectTab(requiredData.get(8));
		foundation.threadWait(2000);
		textBox.enterText(LocationSummary.TXT_PRODUCT_FILTER, requiredData.get(1));
		locationSummary.enterPrice(requiredData.get(0), requiredData.get(2));
		foundation.click(LocationSummary.BTN_SAVE);
		foundation.click(LocationSummary.BTN_FULL_SYNC);
		browser.close();

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
		browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
		login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
				propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
		navigationBar.selectOrganization(
				propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
		navigationBar.navigateToMenuItem(menuItem);
		textBox.enterText(LocationList.TXT_FILTER, requiredData.get(3));
		locationList.selectLocationName(requiredData.get(3));
		locationSummary.selectTab(requiredData.get(8));
		foundation.threadWait(2000);
		textBox.enterText(LocationSummary.TXT_PRODUCT_FILTER, requiredData.get(1));
		locationSummary.enterPrice(requiredData.get(0), requiredData.get(7));
		foundation.click(LocationSummary.BTN_SAVE);

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

		// launch browser and select org
		browser.close();
		browser.launch(Constants.LOCAL, Constants.CHROME);
		browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
		login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
				propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
		navigationBar.selectOrganization(
				propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

		// navigate to global product of V5 associated and update name and sync
		navigationBar.navigateToMenuItem(menuItem.get(0));
		foundation.threadWait(2000);
		foundation.click(GlobalProduct.ICON_FILTER);
		globalProduct.selectFilter(requiredData.get(6));
		textBox.enterText(GlobalProduct.TXT_FILTER, requiredData.get(0));
		foundation.click(globalProduct.getGlobalProduct(requiredData.get(1)));
		dropdown.selectItem(ProductSummary.DPD_IS_DISABLED, requiredData.get(2), Constants.TEXT);
		foundation.click(ProductSummary.BTN_SAVE);
		foundation.waitforElement(GlobalProduct.TXT_FILTER, 3);
		navigationBar.navigateToMenuItem(menuItem.get(1));
		textBox.enterText(LocationList.TXT_FILTER, requiredData.get(3));
		locationList.selectLocationName(requiredData.get(3));
		foundation.click(LocationSummary.BTN_FULL_SYNC);
		browser.close();

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
		browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
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
	}

	@Test(description = "C141868-Thsi test validates the Inventory options")
	public void verifyInventoryOptions() {
		try {
			final String CASE_NUM = "141868";

			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			String language = rstV5DeviceData.get(CNV5Device.ACTUAL_DATA);
			foundation.click(landingPage.objLanguage(language));
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
			Assert.fail(exc.toString());
		}
	}

	@Test(description = "C141873 - Verify the products as per the user search")
	public void verifyProductSearch() {
		try {

			final String CASE_NUM = "141873";
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			String language = rstV5DeviceData.get(CNV5Device.ACTUAL_DATA);
			foundation.click(landingPage.objLanguage(language));
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			List<String> productName = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME).split(Constants.DELIMITER_TILD));
			textBox.enterKeypadText(productName.get(0));
			String prodCount = foundation.getText(ProductSearch.LBL_PROD_COUNT);
			String prodFoundText = foundation.getText(ProductSearch.LBL_PROD_MESSAGE);
			String unMatchedProdMsg = prodCount + " " + prodFoundText;
			List<String> requiredData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
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

	@Test(description = "C141872 - This test validates the item in the Your order screen")
	public void verifyItemInOrderScreen() {
		try {

			final String CASE_NUM = "141872";
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			String language = rstV5DeviceData.get(CNV5Device.ACTUAL_DATA);
			foundation.click(landingPage.objLanguage(language));

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

	@Test(description = "C142663 - This test validates the functionality of Cancel order")

	public void verifyCancelOrderFunctionality() {
		try {

			final String CASE_NUM = "142663";
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			String language = rstV5DeviceData.get(CNV5Device.REQUIRED_DATA);
			foundation.click(landingPage.objLanguage(language));
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

	@Test(description = "C142664 - This test validates the Yes button functionality on Order Screen")
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

			deviceSummary.setTime(rstLocationListData.get(CNLocationList.LOCATION_NAME),
					rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION),
					rstV5DeviceData.get(CNV5Device.TIMEOUT_POPUP),
					rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			login.logout();
			browser.close();

			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			foundation.click(landingPage.objLanguage(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA)));
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
			foundation.click(ProductSearch.BTN_PRODUCT);
			Assert.assertTrue(foundation.isDisplayed(Order.BTN_CANCEL_ORDER));
			foundation.waitforElement(Order.POP_UP_LBL_ORDER_TIMEOUT, Constants.LONG_TIME);
			foundation.click(Order.POP_UP_TIMEOUT_YES);
			Assert.assertTrue(foundation.isDisplayed(Order.LBL_YOUR_ORDER));

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	@Test(description = "C142665 - This test validates the No button functionality on Order Screen")
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

			deviceSummary.setTime(rstLocationListData.get(CNLocationList.LOCATION_NAME),
					rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION),
					rstV5DeviceData.get(CNV5Device.TIMEOUT_POPUP),
					rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			login.logout();
			browser.close();

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

	@Test(description = "C142666 - This test validates the Order Time out prompt will disapper after 5 sec")
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

			deviceSummary.setTime(rstLocationListData.get(CNLocationList.LOCATION_NAME),
					rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION),
					rstV5DeviceData.get(CNV5Device.TIMEOUT_POPUP),
					rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			login.logout();
			browser.close();

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

	@Test(description = "C142680 - This test validates the Order Timeout Prompt when user not  perform any action")
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
			deviceSummary.setTime(rstLocationListData.get(CNLocationList.LOCATION_NAME),
					rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION),
					rstV5DeviceData.get(CNV5Device.TIMEOUT_POPUP),
					rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			login.logout();
			browser.close();

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

	@Test(description = "C142729 - This test validates the Order time out prompt when user decreases the time below 20 sec")
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

			deviceSummary.setTime(rstLocationListData.get(CNLocationList.LOCATION_NAME),
					rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION),
					rstV5DeviceData.get(CNV5Device.TIMEOUT_POPUP),
					rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			login.logout();
			browser.close();

			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(landingPage.objLanguage(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA)));
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
			foundation.click(ProductSearch.BTN_PRODUCT);
			Assert.assertTrue(foundation.isDisplayed(Order.BTN_CANCEL_ORDER));
			foundation.threadWait(15000);
			Assert.assertTrue(foundation.isDisplayed(Order.LBL_YOUR_ORDER));

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
	
	@Test(description = "143019-SOS-11371 v5 > International GMA Login to Account Error")
	public void gmaLoginError() {
		try {

			final String CASE_NUM = "143019";

			// Reading test data from DataBase
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);

			List<String> menuItem = Arrays
					.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
			List<String> requiredData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));

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
			locationList.selectLocationName(requiredData.get(1));
			foundation.click(LocationSummary.BTN_SYNC);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_FILTER, Constants.SHORT_TIME);
			browser.close();
			
			//verify login error
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(landingPage.objLanguage(requiredData.get(2)));
			foundation.click(LandingPage.BTN_LOGIN);
			foundation.click(AccountLogin.BTN_EMAIL_LOGIN);
			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(propertyFile.readPropertyFile(Configuration.V5_USER, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(AccountLogin.BTN_ENTER);
			foundation.click(AccountLogin.BTN_NEXT);
			Assert.assertEquals(foundation.getText(AccountLogin.LBL_ACCOUNT_NOT_AVAILABLE), requiredData.get(3));
			Assert.assertEquals(foundation.getText(AccountLogin.LBL_GEO_GRAPHIC_LOCATION), requiredData.get(4));
			browser.close();
			
			//resetData
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
			
			//verify login error
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(landingPage.objLanguage(requiredData.get(2)));
			foundation.click(LandingPage.BTN_LOGIN);
			foundation.click(AccountLogin.BTN_EMAIL_LOGIN);
			accountLogin.login(propertyFile.readPropertyFile(Configuration.V5_USER, FilePath.PROPERTY_CONFIG_FILE), propertyFile.readPropertyFile(Configuration.V5_PIN, FilePath.PROPERTY_CONFIG_FILE));
			assertTrue(foundation.isDisplayed(EditAccount.BTN_EDIT_ACCOUNT));

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
}
