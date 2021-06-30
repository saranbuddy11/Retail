package at.smartshop.v5.tests;

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
import at.smartshop.database.columns.CNConsumerSearch;
import at.smartshop.database.columns.CNConsumerSummary;
import at.smartshop.database.columns.CNGlobalProductChange;
import at.smartshop.database.columns.CNNavigationMenu;
import at.smartshop.database.columns.CNV5Device;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.pages.ConsumerSearch;
import at.smartshop.pages.ConsumerSummary;
import at.smartshop.pages.GlobalProduct;
import at.smartshop.pages.GlobalProductChange;
import at.smartshop.pages.LocationList;
import at.smartshop.pages.LocationSummary;
import at.smartshop.pages.NavigationBar;
import at.smartshop.pages.ProductSummary;
import at.smartshop.tests.TestInfra;
import at.smartshop.v5.pages.AccountDetails;
import at.smartshop.v5.pages.AccountLogin;
import at.smartshop.v5.pages.CardPayment;
import at.smartshop.v5.pages.ChangePin;
import at.smartshop.v5.pages.CreateAccount;
import at.smartshop.v5.pages.EditAccount;
import at.smartshop.v5.pages.FingerPrintPayment;
import at.smartshop.v5.pages.FundAccount;
import at.smartshop.v5.pages.LandingPage;
import at.smartshop.v5.pages.Order;
import at.smartshop.v5.pages.Payments;
import at.smartshop.v5.pages.ProductSearch;
import at.smartshop.v5.pages.ScanPayment;

@Listeners(at.framework.reportsetup.Listeners.class)
public class V5Test extends TestInfra {
		
	private Foundation foundation=new Foundation();
	private TextBox textBox=new TextBox();
	private ResultSets dataBase = new ResultSets();
	private LandingPage landingPage=new LandingPage();
	private AccountLogin accountLogin=new AccountLogin();
	private EditAccount editAccount=new EditAccount();
	private NavigationBar navigationBar=new NavigationBar();
	private Dropdown dropDown=new Dropdown();
	private LocationList locationList=new LocationList();
	private Order order=new Order();
	private CardPayment cardPayment=new CardPayment();
	private CreateAccount createAccount=new CreateAccount();
	private Payments payments=new Payments();
	private ProductSearch productSearch=new ProductSearch();
	private AccountDetails accountDetails=new AccountDetails();
	private FundAccount fundAccount=new FundAccount();
	private ScanPayment scanPayment=new ScanPayment();
	private FingerPrintPayment fingerPrintPayment=new FingerPrintPayment();
	private ChangePin changePin=new ChangePin();
	private LocationSummary locationSummary=new LocationSummary();
	private ConsumerSearch consumerSearch=new ConsumerSearch();
	
	private Map<String, String> rstV5DeviceData;	
	private Map<String, String> rstNavigationMenuData;
	private Map<String, String> rstConsumerSearchData;
	private Map<String, String> rstConsumerSummaryData;
	private Map<String, String> rstProductSummaryData;
	
	@Test(description = "141874-Kiosk Manage Account > Edit Account > Update Information")
	public void editAccountUpdateInformation() {
		final String CASE_NUM = "141874";

		browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));	

		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		List<String> requiredData = Arrays.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		
		//login to application
		foundation.click(landingPage.objLanguage(requiredData.get(0)));
        foundation.click(LandingPage.BTN_LOGIN);
		foundation.click(AccountLogin.BTN_EMAIL_LOGIN);
		accountLogin.login(rstV5DeviceData.get(CNV5Device.EMAIL_ID), rstV5DeviceData.get(CNV5Device.PIN));
		
		// navigate to edit account and get previous information
		foundation.click(EditAccount.BTN_EDIT_ACCOUNT);
		String firstName=textBox.getTextFromInput(EditAccount.TXT_FIRST_NAME);
		String lastName=textBox.getTextFromInput(EditAccount.TXT_LAST_NAME);
		String emailAddress=textBox.getTextFromInput(EditAccount.TXT_EMAIL_ADDRESS);		
			
		// update information
		foundation.click(EditAccount.BTN_CAMEL_CASE);
		editAccount.updateText(EditAccount.TXT_FIRST_NAME, requiredData.get(1),firstName);
		editAccount.updateText(EditAccount.TXT_LAST_NAME, requiredData.get(2),lastName);
		editAccount.updateText(EditAccount.TXT_EMAIL_ADDRESS, requiredData.get(3),emailAddress);
		foundation.click(EditAccount.BTN_SAVE);
		assertTrue(foundation.isDisplayed(EditAccount.BTN_EDIT_ACCOUNT));
		
		//navigate back to edit account and verify all data are populating as entered before
		foundation.click(EditAccount.BTN_EDIT_ACCOUNT);		
		assertTrue(textBox.getTextFromInput(EditAccount.TXT_FIRST_NAME).equals(requiredData.get(1)));
		assertTrue(textBox.getTextFromInput(EditAccount.TXT_LAST_NAME).equals(requiredData.get(2)));
		assertTrue(textBox.getTextFromInput(EditAccount.TXT_EMAIL_ADDRESS).equals(requiredData.get(3)));		
		
		//reset the data
		foundation.click(EditAccount.BTN_CAMEL_CASE);
		editAccount.updateText(EditAccount.TXT_FIRST_NAME, firstName,requiredData.get(1));
		editAccount.updateText(EditAccount.TXT_LAST_NAME, lastName,requiredData.get(2));
		editAccount.updateText(EditAccount.TXT_EMAIL_ADDRESS, emailAddress,requiredData.get(3));
		foundation.click(EditAccount.BTN_SAVE);
		assertTrue(foundation.isDisplayed(EditAccount.BTN_EDIT_ACCOUNT));
	}
	
	@Test(description = "C142667 - SOS-24492- Kiosk Language selection")
	public void englishDefaultLanguage() {
		try {
		final String CASE_NUM = "142667";
		// Reading test data from DataBase
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		List<String> requiredData = Arrays.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		
		browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL,FilePath.PROPERTY_CONFIG_FILE));
		login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER,FilePath.PROPERTY_CONFIG_FILE), propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD,FilePath.PROPERTY_CONFIG_FILE));

		// Select Menu and Menu Item
		navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG,FilePath.PROPERTY_CONFIG_FILE));
			
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
        //login into Kiosk Device
        browser.launch(Constants.REMOTE,Constants.CHROME);
		browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
		
		//Validating Landing Page
		foundation.waitforElement(landingPage.objLanguage(requiredData.get(3)), Constants.SHORT_TIME);
		landingPage.verifyHomeScreenLanguage(rstV5DeviceData.get(CNV5Device.LANDING_PAGE));

		//Validating Search Page
		foundation.click(LandingPage.IMG_SEARCH_ICON);
		productSearch.verifyProductSearhPageLanguage(rstV5DeviceData.get(CNV5Device.PRODUCT_SEARCH_PAGE));

		foundation.click(AccountLogin.BTN_CAMELCASE);
		textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
        foundation.click(ProductSearch.BTN_PRODUCT);
        
        //verify Order Page     
        List<String> orderPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.ORDER_PAGE).split(Constants.DELIMITER_TILD));
        order.verifyOrderPageLanguage(rstV5DeviceData.get(CNV5Device.ORDER_PAGE));
        
        //Validating Credit/Debit Page
        foundation.click(order.objText(orderPageData.get(8)));
        cardPayment.verifyCardPaymentPageLanguage(rstV5DeviceData.get(CNV5Device.CREDIT_DEBIT_PAGE));
        
        foundation.click(CardPayment.BTN_CLOSE);
        foundation.waitforElement(Order.BTN_CANCEL_ORDER, Constants.SHORT_TIME);
        
        //verify Cancel Order Page
  		foundation.click(Order.BTN_CANCEL_ORDER);
        Assert.assertTrue(foundation.isDisplayed(createAccount.objText(rstV5DeviceData.get(CNV5Device.TRANSACTION_CANCEL))));
	
		//Validating Create Account Page
        foundation.waitforElement(LandingPage.BTN_CREATE_ACCOUNT, Constants.SHORT_TIME);
		foundation.click(LandingPage.BTN_CREATE_ACCOUNT);
		createAccount.verifyCreateAccoutnPageLanguage(rstV5DeviceData.get(CNV5Device.CREATE_ACCOUNT),requiredData.get(3),rstV5DeviceData.get(CNV5Device.ACTUAL_DATA));
		
		//Validating Account Login Page
		foundation.click(LandingPage.BTN_LOGIN);
		accountLogin.verifyAccountLoginPageLanguage(rstV5DeviceData.get(CNV5Device.LOGIN_PAGE));
			
        textBox.enterPin(propertyFile.readPropertyFile(Configuration.V5_PIN,FilePath.PROPERTY_CONFIG_FILE));
		foundation.click(AccountLogin.BTN_PIN_NEXT);
        
		//Verifying Account info page
		List<String> accountPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.ACCOUNT_DETAILS).split(Constants.DELIMITER_TILD));
		accountDetails.verifyAccountDetailsPageLanguage(rstV5DeviceData.get(CNV5Device.ACCOUNT_DETAILS),requiredData.get(3),rstV5DeviceData.get(CNV5Device.ACTUAL_DATA));
		
		//Verifying Fund with card page
		foundation.click(fundAccount.objText(accountPageData.get(1)));
		fundAccount.verifyFundAccountScreenLanguage(rstV5DeviceData.get(CNV5Device.FUND_ACCOUNT_PAGE));
		
		//Verifying Scan Setup page
		foundation.click(fundAccount.objText(accountPageData.get(4)));
		scanPayment.verifyScanPaymentPageLanguage(rstV5DeviceData.get(CNV5Device.QUICK_SCAN_SETUP),requiredData.get(3),rstV5DeviceData.get(CNV5Device.ACTUAL_DATA));
		
		//Verifying Finger print Setup page
		foundation.click(createAccount.objText(accountPageData.get(6)));
		fingerPrintPayment.verifyFingerPrintPaymentPageLanguage(rstV5DeviceData.get(CNV5Device.FINGER_PRINT_SETUP));
		
		//Verifying Edit account page
		foundation.click(createAccount.objText(accountPageData.get(7)));
		editAccount.verifyEditAccountPageLanguage(rstV5DeviceData.get(CNV5Device.EDIT_ACCOUNT_DETAILS));
		
		//verify Change pin
		List<String> accountEditPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.EDIT_ACCOUNT_DETAILS).split(Constants.DELIMITER_TILD));
		foundation.click(createAccount.objText(accountEditPageData.get(3)));
		changePin.verifyChangePinPageLanguage(rstV5DeviceData.get(CNV5Device.CHANGE_PIN));
		foundation.click(createAccount.objText(accountEditPageData.get(6)));
		
		//Verifying timeout popup
		foundation.waitforElement(Order.POP_UP_LBL_ORDER_TIMEOUT, Constants.EXTRA_LONG_TIME);
		editAccount.verifyTimeOutPopLanguage(rstV5DeviceData.get(CNV5Device.TIME_OUT_POPUP));

		//Verifying Product Purchase page
		foundation.waitforElement(LandingPage.IMG_SEARCH_ICON,Constants.SHORT_TIME);
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
	    
	    //Resetting the data
	    browser.launch(Constants.LOCAL,Constants.CHROME);
	    browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL,FilePath.PROPERTY_CONFIG_FILE));
		login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER,FilePath.PROPERTY_CONFIG_FILE), propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD,FilePath.PROPERTY_CONFIG_FILE));
	
		// Select Menu and Menu Item
		navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG,FilePath.PROPERTY_CONFIG_FILE));

        // Selecting location
        locationList.selectLocationName(requiredData.get(0));
        
        dropDown.selectItem(LocationSummary.DPD_KIOSK_LANGUAGE, requiredData.get(4), Constants.TEXT);
        dropDown.selectItem(LocationSummary.DPD_ALTERNATE_LANGUAGE, requiredData.get(5), Constants.TEXT);
        
        foundation.click(LocationSummary.BTN_SYNC);
        foundation.click(LocationSummary.BTN_SAVE);
        foundation.waitforElement(LocationList.TXT_FILTER, Constants.SHORT_TIME);
        login.logout();
                    
    } catch (Exception exc) {
        exc.printStackTrace();
        Assert.fail();
    }
}


	@Test(description = "C142733 - SOS-24492 Verify alternate language is set to Italian in Kiosk when user set the Alternate Language as italian and full sync is done in ADM")
	public void alternateItalianLanguage() {
		try {
		final String CASE_NUM = "142733";
		// Reading test data from DataBase
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		List<String> requiredData = Arrays.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
	
		browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL,FilePath.PROPERTY_CONFIG_FILE));
		login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER,FilePath.PROPERTY_CONFIG_FILE), propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD,FilePath.PROPERTY_CONFIG_FILE));
	
		// Select Menu and Menu Item
		navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG,FilePath.PROPERTY_CONFIG_FILE));

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
        //login into Kiosk Device
        browser.launch(Constants.REMOTE,Constants.CHROME);
		browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
		
		foundation.click(LandingPage.BTN_LANG);
		//Validating Landing Page
		foundation.waitforElement(landingPage.objLanguage(requiredData.get(3)), Constants.SHORT_TIME);
		landingPage.verifyHomeScreenLanguage(rstV5DeviceData.get(CNV5Device.LANDING_PAGE));

		//Validating Search Page
		foundation.click(LandingPage.IMG_SEARCH_ICON);
		productSearch.verifyProductSearhPageLanguage(rstV5DeviceData.get(CNV5Device.PRODUCT_SEARCH_PAGE));

		foundation.click(AccountLogin.BTN_CAMELCASE);
		textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
        foundation.click(ProductSearch.BTN_PRODUCT);
        
        //verify Order Page     
       List<String> orderPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.ORDER_PAGE).split(Constants.DELIMITER_TILD));
        order.verifyOrderPageLanguage(rstV5DeviceData.get(CNV5Device.ORDER_PAGE));
        
        //Validating Credit/Debit Page
        foundation.click(order.objText(orderPageData.get(8)));
        cardPayment.verifyCardPaymentPageLanguage(rstV5DeviceData.get(CNV5Device.CREDIT_DEBIT_PAGE));
        
        foundation.click(CardPayment.BTN_CLOSE);
        foundation.waitforElement(order.objText(orderPageData.get(1)), Constants.SHORT_TIME);
        
        //verify Cancel Order Page
  		foundation.click(order.objText(orderPageData.get(0)));
        Assert.assertTrue(foundation.isDisplayed(createAccount.objText(rstV5DeviceData.get(CNV5Device.TRANSACTION_CANCEL))));
	
		//Validating Create Account Page
        foundation.waitforElement(LandingPage.BTN_CREATE_ACCOUNT, Constants.SHORT_TIME);
		foundation.click(LandingPage.BTN_CREATE_ACCOUNT);
		createAccount.verifyCreateAccoutnPageLanguage(rstV5DeviceData.get(CNV5Device.CREATE_ACCOUNT),requiredData.get(3),rstV5DeviceData.get(CNV5Device.ACTUAL_DATA));
		
		//Validating Account Login Page
		foundation.click(LandingPage.BTN_LOGIN);
		accountLogin.verifyAccountLoginPageLanguage(rstV5DeviceData.get(CNV5Device.LOGIN_PAGE));
			
        textBox.enterPin(propertyFile.readPropertyFile(Configuration.V5_PIN,FilePath.PROPERTY_CONFIG_FILE));
		foundation.click(AccountLogin.BTN_PIN_NEXT);
        
		//Verifying Account info page
		List<String> accountPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.ACCOUNT_DETAILS).split(Constants.DELIMITER_TILD));
		accountDetails.verifyAccountDetailsPageLanguage(rstV5DeviceData.get(CNV5Device.ACCOUNT_DETAILS),requiredData.get(3),rstV5DeviceData.get(CNV5Device.ACTUAL_DATA));
		
		//Verifying Fund with card page
		foundation.click(fundAccount.objText(accountPageData.get(1)));
		fundAccount.verifyFundAccountScreenLanguage(rstV5DeviceData.get(CNV5Device.FUND_ACCOUNT_PAGE));
		
		//Verifying Scan Setup page
		foundation.click(fundAccount.objText(accountPageData.get(4)));
		scanPayment.verifyScanPaymentPageLanguage(rstV5DeviceData.get(CNV5Device.QUICK_SCAN_SETUP),requiredData.get(3),rstV5DeviceData.get(CNV5Device.ACTUAL_DATA));
		
		//Verifying Finger print Setup page
		foundation.click(createAccount.objText(accountPageData.get(6)));
		fingerPrintPayment.verifyFingerPrintPaymentPageLanguage(rstV5DeviceData.get(CNV5Device.FINGER_PRINT_SETUP));
		
		//Verifying Edit account page
		foundation.click(createAccount.objText(accountPageData.get(7)));
		editAccount.verifyEditAccountPageLanguage(rstV5DeviceData.get(CNV5Device.EDIT_ACCOUNT_DETAILS));
		
		//verify Change pin
		List<String> accountEditPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.EDIT_ACCOUNT_DETAILS).split(Constants.DELIMITER_TILD));
		foundation.click(createAccount.objText(accountEditPageData.get(3)));
		changePin.verifyChangePinPageLanguage(rstV5DeviceData.get(CNV5Device.CHANGE_PIN));
		foundation.click(createAccount.objText(accountEditPageData.get(6)));
		
		//Verifying timeout popup
		editAccount.verifyTimeOutPopLanguage(rstV5DeviceData.get(CNV5Device.TIME_OUT_POPUP));

		//Verifying Product Purchase page
		foundation.waitforElement(LandingPage.IMG_SEARCH_ICON,Constants.SHORT_TIME);
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
	    
	    //Resetting the data
	    browser.launch(Constants.LOCAL,Constants.CHROME);
	    browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL,FilePath.PROPERTY_CONFIG_FILE));
		login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER,FilePath.PROPERTY_CONFIG_FILE), propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD,FilePath.PROPERTY_CONFIG_FILE));
	
		// Select Menu and Menu Item
		navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG,FilePath.PROPERTY_CONFIG_FILE));

        // Selecting location
        locationList.selectLocationName(requiredData.get(0));
        
        dropDown.selectItem(LocationSummary.DPD_KIOSK_LANGUAGE, requiredData.get(4), Constants.TEXT);
        dropDown.selectItem(LocationSummary.DPD_ALTERNATE_LANGUAGE, requiredData.get(5), Constants.TEXT);
        
        foundation.click(LocationSummary.BTN_SYNC);
        foundation.click(LocationSummary.BTN_SAVE);
        foundation.waitforElement(LocationList.TXT_FILTER, Constants.SHORT_TIME);
        login.logout();
                    
    } catch (Exception exc) {
        exc.printStackTrace();
        Assert.fail();
    }
			
	}
	
	@Test(description = "C142734 - SOS-24492 Verify default language is set to french in Kiosk when user set the Default Language as french and full sync is done in ADM")
	public void frenchDefaultLanguage() {
		try {
		final String CASE_NUM = "142734";
		// Reading test data from DataBase
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		List<String> requiredData = Arrays.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		
		browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL,FilePath.PROPERTY_CONFIG_FILE));
		login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER,FilePath.PROPERTY_CONFIG_FILE), propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD,FilePath.PROPERTY_CONFIG_FILE));
	
		// Select Menu and Menu Item
		navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG,FilePath.PROPERTY_CONFIG_FILE));

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
        //login into Kiosk Device
        browser.launch(Constants.REMOTE,Constants.CHROME);
		browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
		
		//Validating Landing Page
		foundation.waitforElement(landingPage.objLanguage(requiredData.get(3)), Constants.SHORT_TIME);
		landingPage.verifyHomeScreenLanguage(rstV5DeviceData.get(CNV5Device.LANDING_PAGE));

		//Validating Search Page
		foundation.click(LandingPage.IMG_SEARCH_ICON);
		productSearch.verifyProductSearhPageLanguage(rstV5DeviceData.get(CNV5Device.PRODUCT_SEARCH_PAGE));

		foundation.click(AccountLogin.BTN_CAMELCASE);
		textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
        foundation.click(ProductSearch.BTN_PRODUCT);
        
        //verify Order Page     
        List<String> orderPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.ORDER_PAGE).split(Constants.DELIMITER_TILD));
        order.verifyOrderPageLanguage(rstV5DeviceData.get(CNV5Device.ORDER_PAGE));
        
        //Validating Credit/Debit Page
        foundation.click(order.objText(orderPageData.get(8)));
        cardPayment.verifyCardPaymentPageLanguage(rstV5DeviceData.get(CNV5Device.CREDIT_DEBIT_PAGE));
        
        foundation.click(CardPayment.BTN_CLOSE);
        foundation.waitforElement(order.objText(orderPageData.get(1)), Constants.SHORT_TIME);
        
        //verify Cancel Order Page
  		foundation.click(order.objText(orderPageData.get(0)));
        Assert.assertTrue(foundation.isDisplayed(createAccount.objText(rstV5DeviceData.get(CNV5Device.TRANSACTION_CANCEL))));
	
		//Validating Create Account Page
        foundation.waitforElement(LandingPage.BTN_CREATE_ACCOUNT, Constants.SHORT_TIME);
		foundation.click(LandingPage.BTN_CREATE_ACCOUNT);
		createAccount.verifyCreateAccoutnPageLanguage(rstV5DeviceData.get(CNV5Device.CREATE_ACCOUNT),requiredData.get(3),rstV5DeviceData.get(CNV5Device.ACTUAL_DATA));
		
		//Validating Account Login Page
		foundation.click(LandingPage.BTN_LOGIN);
		accountLogin.verifyAccountLoginPageLanguage(rstV5DeviceData.get(CNV5Device.LOGIN_PAGE));
			
        textBox.enterPin(propertyFile.readPropertyFile(Configuration.V5_PIN,FilePath.PROPERTY_CONFIG_FILE));
		foundation.click(AccountLogin.BTN_PIN_NEXT);
        
		//Verifying Account info page
		List<String> accountPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.ACCOUNT_DETAILS).split(Constants.DELIMITER_TILD));
		accountDetails.verifyAccountDetailsPageLanguage(rstV5DeviceData.get(CNV5Device.ACCOUNT_DETAILS),requiredData.get(3),rstV5DeviceData.get(CNV5Device.ACTUAL_DATA));
		
		//Verifying Fund with card page
		foundation.click(fundAccount.objText(accountPageData.get(1)));
		fundAccount.verifyFundAccountScreenLanguage(rstV5DeviceData.get(CNV5Device.FUND_ACCOUNT_PAGE));
		
		//Verifying Scan Setup page
		foundation.click(fundAccount.objText(accountPageData.get(4)));
		scanPayment.verifyScanPaymentPageLanguage(rstV5DeviceData.get(CNV5Device.QUICK_SCAN_SETUP),requiredData.get(3),rstV5DeviceData.get(CNV5Device.ACTUAL_DATA));
		
		//Verifying Finger print Setup page
		foundation.click(createAccount.objText(accountPageData.get(6)));
		fingerPrintPayment.verifyFingerPrintPaymentPageLanguage(rstV5DeviceData.get(CNV5Device.FINGER_PRINT_SETUP));
		
		//Verifying Edit account page
		foundation.click(createAccount.objText(accountPageData.get(7)));
		editAccount.verifyEditAccountPageLanguage(rstV5DeviceData.get(CNV5Device.EDIT_ACCOUNT_DETAILS));
		
		//verify Change pin
		List<String> accountEditPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.EDIT_ACCOUNT_DETAILS).split(Constants.DELIMITER_TILD));
		foundation.click(createAccount.objText(accountEditPageData.get(3)));
		changePin.verifyChangePinPageLanguage(rstV5DeviceData.get(CNV5Device.CHANGE_PIN));
		foundation.click(createAccount.objText(accountEditPageData.get(6)));
		
		//Verifying timeout popup
		editAccount.verifyTimeOutPopLanguage(rstV5DeviceData.get(CNV5Device.TIME_OUT_POPUP));

		//Verifying Product Purchase page
		foundation.waitforElement(LandingPage.IMG_SEARCH_ICON,Constants.SHORT_TIME);
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
	    
		//Resetting the data
	    browser.launch(Constants.LOCAL,Constants.CHROME);
	    browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL,FilePath.PROPERTY_CONFIG_FILE));
		login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER,FilePath.PROPERTY_CONFIG_FILE), propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD,FilePath.PROPERTY_CONFIG_FILE));
	
		// Select Menu and Menu Item
		navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG,FilePath.PROPERTY_CONFIG_FILE));

        // Selecting location
        locationList.selectLocationName(requiredData.get(0));
        
        dropDown.selectItem(LocationSummary.DPD_KIOSK_LANGUAGE, requiredData.get(4), Constants.TEXT);
        dropDown.selectItem(LocationSummary.DPD_ALTERNATE_LANGUAGE, requiredData.get(5), Constants.TEXT);
        
        foundation.click(LocationSummary.BTN_SYNC);
        foundation.click(LocationSummary.BTN_SAVE);
        foundation.waitforElement(LocationList.TXT_FILTER, Constants.SHORT_TIME);
        login.logout();
                    
    } catch (Exception exc) {
        exc.printStackTrace();
        Assert.fail();
    }
}
	
	@Test(description = "C142735 - SOS-24492 Verify default language is set to Dutch in Kiosk when user set the Default Language as Dutch and full sync is done in ADM")
	public void dutchDefaultLanguage() {
		try {
		final String CASE_NUM = "142735";
		// Reading test data from DataBase
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		List<String> requiredData = Arrays.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		
		browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL,FilePath.PROPERTY_CONFIG_FILE));
		login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER,FilePath.PROPERTY_CONFIG_FILE), propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD,FilePath.PROPERTY_CONFIG_FILE));
	
		// Select Menu and Menu Item
		navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG,FilePath.PROPERTY_CONFIG_FILE));

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
        //login into Kiosk Device
        browser.launch(Constants.REMOTE,Constants.CHROME);
		browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
		
		//Validating Landing Page
		foundation.waitforElement(landingPage.objLanguage(requiredData.get(3)), Constants.SHORT_TIME);
		landingPage.verifyHomeScreenLanguage(rstV5DeviceData.get(CNV5Device.LANDING_PAGE));

		//Validating Search Page
		foundation.click(LandingPage.IMG_SEARCH_ICON);
		productSearch.verifyProductSearhPageLanguage(rstV5DeviceData.get(CNV5Device.PRODUCT_SEARCH_PAGE));

		foundation.click(AccountLogin.BTN_CAMELCASE);
		textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
        foundation.click(ProductSearch.BTN_PRODUCT);
        
        //verify Order Page     
        List<String> orderPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.ORDER_PAGE).split(Constants.DELIMITER_TILD));
        order.verifyOrderPageLanguage(rstV5DeviceData.get(CNV5Device.ORDER_PAGE));
        
        //Validating Credit/Debit Page
        foundation.click(order.objText(orderPageData.get(8)));
        cardPayment.verifyCardPaymentPageLanguage(rstV5DeviceData.get(CNV5Device.CREDIT_DEBIT_PAGE));
        
        foundation.click(CardPayment.BTN_CLOSE);
        foundation.waitforElement(order.objText(orderPageData.get(1)), Constants.SHORT_TIME);
        
        //verify Cancel Order Page
  		foundation.click(order.objText(orderPageData.get(0)));
        Assert.assertTrue(foundation.isDisplayed(createAccount.objText(rstV5DeviceData.get(CNV5Device.TRANSACTION_CANCEL))));
	
		//Validating Create Account Page
        foundation.waitforElement(LandingPage.BTN_CREATE_ACCOUNT, Constants.SHORT_TIME);
		foundation.click(LandingPage.BTN_CREATE_ACCOUNT);
		createAccount.verifyCreateAccoutnPageLanguage(rstV5DeviceData.get(CNV5Device.CREATE_ACCOUNT),requiredData.get(3),rstV5DeviceData.get(CNV5Device.ACTUAL_DATA));
		
		//Validating Account Login Page
		foundation.click(LandingPage.BTN_LOGIN);
		accountLogin.verifyAccountLoginPageLanguage(rstV5DeviceData.get(CNV5Device.LOGIN_PAGE));
			
        textBox.enterPin(propertyFile.readPropertyFile(Configuration.V5_PIN,FilePath.PROPERTY_CONFIG_FILE));
		foundation.click(AccountLogin.BTN_PIN_NEXT);
        
		//Verifying Account info page
		List<String> accountPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.ACCOUNT_DETAILS).split(Constants.DELIMITER_TILD));
		accountDetails.verifyAccountDetailsPageLanguage(rstV5DeviceData.get(CNV5Device.ACCOUNT_DETAILS),requiredData.get(3),rstV5DeviceData.get(CNV5Device.ACTUAL_DATA));
		
		//Verifying Fund with card page
		foundation.click(fundAccount.objText(accountPageData.get(1)));
		fundAccount.verifyFundAccountScreenLanguage(rstV5DeviceData.get(CNV5Device.FUND_ACCOUNT_PAGE));
		
		//Verifying Scan Setup page
		foundation.click(fundAccount.objText(accountPageData.get(4)));
		scanPayment.verifyScanPaymentPageLanguage(rstV5DeviceData.get(CNV5Device.QUICK_SCAN_SETUP),requiredData.get(3),rstV5DeviceData.get(CNV5Device.ACTUAL_DATA));
		
		//Verifying Finger print Setup page
		foundation.click(createAccount.objText(accountPageData.get(6)));
		fingerPrintPayment.verifyFingerPrintPaymentPageLanguage(rstV5DeviceData.get(CNV5Device.FINGER_PRINT_SETUP));
		
		//Verifying Edit account page
		foundation.click(createAccount.objText(accountPageData.get(7)));
		editAccount.verifyEditAccountPageLanguage(rstV5DeviceData.get(CNV5Device.EDIT_ACCOUNT_DETAILS));
		
		//verify Change pin
		List<String> accountEditPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.EDIT_ACCOUNT_DETAILS).split(Constants.DELIMITER_TILD));
		foundation.click(createAccount.objText(accountEditPageData.get(3)));
		changePin.verifyChangePinPageLanguage(rstV5DeviceData.get(CNV5Device.CHANGE_PIN));
		foundation.click(createAccount.objText(accountEditPageData.get(6)));
		
		//Verifying timeout popup
		editAccount.verifyTimeOutPopLanguage(rstV5DeviceData.get(CNV5Device.TIME_OUT_POPUP));

		//Verifying Product Purchase page
		foundation.waitforElement(LandingPage.IMG_SEARCH_ICON,Constants.SHORT_TIME);
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
	    
		//resetting the data
	    browser.launch(Constants.LOCAL,Constants.CHROME);
	    browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL,FilePath.PROPERTY_CONFIG_FILE));
		login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER,FilePath.PROPERTY_CONFIG_FILE), propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD,FilePath.PROPERTY_CONFIG_FILE));
	
		// Select Menu and Menu Item
		navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG,FilePath.PROPERTY_CONFIG_FILE));

        // Selecting location
        locationList.selectLocationName(requiredData.get(0));
        
        dropDown.selectItem(LocationSummary.DPD_KIOSK_LANGUAGE, requiredData.get(4), Constants.TEXT);
        dropDown.selectItem(LocationSummary.DPD_ALTERNATE_LANGUAGE, requiredData.get(5), Constants.TEXT);
        
        foundation.click(LocationSummary.BTN_SYNC);
        foundation.click(LocationSummary.BTN_SAVE);
        foundation.waitforElement(LocationList.TXT_FILTER, Constants.SHORT_TIME);
        login.logout();
                    
    } catch (Exception exc) {
        exc.printStackTrace();
        Assert.fail();
    }
}
	
	@Test(description = "C142736 - SOS-24492 Verify default language is set to Swedish in Kiosk when user set the Default Language as Swedish and full sync is done in ADM")
	public void swedishDefaultLanguage() {
		try {
		final String CASE_NUM = "142736";
		// Reading test data from DataBase
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		List<String> requiredData = Arrays.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		
		browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL,FilePath.PROPERTY_CONFIG_FILE));
		login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER,FilePath.PROPERTY_CONFIG_FILE), propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD,FilePath.PROPERTY_CONFIG_FILE));
	
		// Select Menu and Menu Item
		navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG,FilePath.PROPERTY_CONFIG_FILE));

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
        //login into Kiosk Device
        browser.launch(Constants.REMOTE,Constants.CHROME);
		browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
		
		//Validating Landing Page
		foundation.waitforElement(landingPage.objLanguage(requiredData.get(3)), Constants.SHORT_TIME);
		landingPage.verifyHomeScreenLanguage(rstV5DeviceData.get(CNV5Device.LANDING_PAGE));

		//Validating Search Page
		foundation.click(LandingPage.IMG_SEARCH_ICON);
		productSearch.verifyProductSearhPageLanguage(rstV5DeviceData.get(CNV5Device.PRODUCT_SEARCH_PAGE));

		foundation.click(AccountLogin.BTN_CAMELCASE);
		textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
        foundation.click(ProductSearch.BTN_PRODUCT);
        
        //verify Order Page     
        List<String> orderPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.ORDER_PAGE).split(Constants.DELIMITER_TILD));
        order.verifyOrderPageLanguage(rstV5DeviceData.get(CNV5Device.ORDER_PAGE));
        
        //Validating Credit/Debit Page
        foundation.click(order.objText(orderPageData.get(8)));
        cardPayment.verifyCardPaymentPageLanguage(rstV5DeviceData.get(CNV5Device.CREDIT_DEBIT_PAGE));
        
        foundation.click(CardPayment.BTN_CLOSE);
        foundation.waitforElement(order.objText(orderPageData.get(1)), Constants.SHORT_TIME);
        
        //verify Cancel Order Page
  		foundation.click(order.objText(orderPageData.get(0)));
        Assert.assertTrue(foundation.isDisplayed(createAccount.objText(rstV5DeviceData.get(CNV5Device.TRANSACTION_CANCEL))));
	
		//Validating Create Account Page
        foundation.waitforElement(LandingPage.BTN_CREATE_ACCOUNT, Constants.SHORT_TIME);
		foundation.click(LandingPage.BTN_CREATE_ACCOUNT);
		createAccount.verifyCreateAccoutnPageLanguage(rstV5DeviceData.get(CNV5Device.CREATE_ACCOUNT),requiredData.get(3),rstV5DeviceData.get(CNV5Device.ACTUAL_DATA));
		
		//Validating Account Login Page
		foundation.click(LandingPage.BTN_LOGIN);
		accountLogin.verifyAccountLoginPageLanguage(rstV5DeviceData.get(CNV5Device.LOGIN_PAGE));
			
        textBox.enterPin(propertyFile.readPropertyFile(Configuration.V5_PIN,FilePath.PROPERTY_CONFIG_FILE));
		foundation.click(AccountLogin.BTN_PIN_NEXT);
        
		//Verifying Account info page
		List<String> accountPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.ACCOUNT_DETAILS).split(Constants.DELIMITER_TILD));
		accountDetails.verifyAccountDetailsPageLanguage(rstV5DeviceData.get(CNV5Device.ACCOUNT_DETAILS),requiredData.get(3),rstV5DeviceData.get(CNV5Device.ACTUAL_DATA));
		
		//Verifying Fund with card page
		foundation.click(fundAccount.objText(accountPageData.get(1)));
		fundAccount.verifyFundAccountScreenLanguage(rstV5DeviceData.get(CNV5Device.FUND_ACCOUNT_PAGE));
		
		//Verifying Scan Setup page
		foundation.click(fundAccount.objText(accountPageData.get(4)));
		scanPayment.verifyScanPaymentPageLanguage(rstV5DeviceData.get(CNV5Device.QUICK_SCAN_SETUP),requiredData.get(3),rstV5DeviceData.get(CNV5Device.ACTUAL_DATA));
		
		//Verifying Finger print Setup page
		foundation.click(createAccount.objText(accountPageData.get(6)));
		fingerPrintPayment.verifyFingerPrintPaymentPageLanguage(rstV5DeviceData.get(CNV5Device.FINGER_PRINT_SETUP));
		
		//Verifying Edit account page
		foundation.click(createAccount.objText(accountPageData.get(7)));
		editAccount.verifyEditAccountPageLanguage(rstV5DeviceData.get(CNV5Device.EDIT_ACCOUNT_DETAILS));
		
		//verify Change pin
		List<String> accountEditPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.EDIT_ACCOUNT_DETAILS).split(Constants.DELIMITER_TILD));
		foundation.click(createAccount.objText(accountEditPageData.get(3)));
		changePin.verifyChangePinPageLanguage(rstV5DeviceData.get(CNV5Device.CHANGE_PIN));
		foundation.click(createAccount.objText(accountEditPageData.get(6)));
		
		//Verifying timeout popup
		editAccount.verifyTimeOutPopLanguage(rstV5DeviceData.get(CNV5Device.TIME_OUT_POPUP));

		//Verifying Product Purchase page
		foundation.waitforElement(LandingPage.IMG_SEARCH_ICON,Constants.SHORT_TIME);
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
	    
		//Resetting data
	    browser.launch(Constants.LOCAL,Constants.CHROME);
	    browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL,FilePath.PROPERTY_CONFIG_FILE));
		login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER,FilePath.PROPERTY_CONFIG_FILE), propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD,FilePath.PROPERTY_CONFIG_FILE));
	
		// Select Menu and Menu Item
		navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG,FilePath.PROPERTY_CONFIG_FILE));

        // Selecting location
        locationList.selectLocationName(requiredData.get(0));
        
        dropDown.selectItem(LocationSummary.DPD_KIOSK_LANGUAGE, requiredData.get(4), Constants.TEXT);
        dropDown.selectItem(LocationSummary.DPD_ALTERNATE_LANGUAGE, requiredData.get(5), Constants.TEXT);
        
        foundation.click(LocationSummary.BTN_SYNC);
        foundation.click(LocationSummary.BTN_SAVE);
        foundation.waitforElement(LocationList.TXT_FILTER, Constants.SHORT_TIME);
        login.logout();
                    
    } catch (Exception exc) {
        exc.printStackTrace();
        Assert.fail();
    }
}
	
	@Test(description = "C142737 - SOS-24492 Verify default language is set to Norwegian in Kiosk when user set the Default Language as Norwegian and full sync is done in ADM")
	public void norwegianDefaultLanguage() {
		try {
		final String CASE_NUM = "142737";
		// Reading test data from DataBase
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		List<String> requiredData = Arrays.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		
		browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL,FilePath.PROPERTY_CONFIG_FILE));
		login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER,FilePath.PROPERTY_CONFIG_FILE), propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD,FilePath.PROPERTY_CONFIG_FILE));
	
		// Select Menu and Menu Item
		navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG,FilePath.PROPERTY_CONFIG_FILE));

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
        //login into Kiosk Device
        browser.launch(Constants.REMOTE,Constants.CHROME);
		browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
		
		//Validating Landing Page
		foundation.waitforElement(landingPage.objLanguage(requiredData.get(3)), Constants.SHORT_TIME);
		landingPage.verifyHomeScreenLanguage(rstV5DeviceData.get(CNV5Device.LANDING_PAGE));

		//Validating Search Page
		foundation.click(LandingPage.IMG_SEARCH_ICON);
		productSearch.verifyProductSearhPageLanguage(rstV5DeviceData.get(CNV5Device.PRODUCT_SEARCH_PAGE));

		foundation.click(AccountLogin.BTN_CAMELCASE);
		textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
        foundation.click(ProductSearch.BTN_PRODUCT);
        
        //verify Order Page     
        List<String> orderPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.ORDER_PAGE).split(Constants.DELIMITER_TILD));
        order.verifyOrderPageLanguage(rstV5DeviceData.get(CNV5Device.ORDER_PAGE));
        
        //Validating Credit/Debit Page
        foundation.click(order.objText(orderPageData.get(8)));
        cardPayment.verifyCardPaymentPageLanguage(rstV5DeviceData.get(CNV5Device.CREDIT_DEBIT_PAGE));
        
        foundation.click(CardPayment.BTN_CLOSE);
        foundation.waitforElement(order.objText(orderPageData.get(1)), Constants.SHORT_TIME);
        
        //verify Cancel Order Page
        foundation.click(cardPayment.objText(orderPageData.get(0)));
        Assert.assertTrue(foundation.isDisplayed(createAccount.objText(rstV5DeviceData.get(CNV5Device.TRANSACTION_CANCEL))));
	
		//Validating Create Account Page
        foundation.waitforElement(LandingPage.BTN_CREATE_ACCOUNT, Constants.SHORT_TIME);
		foundation.click(LandingPage.BTN_CREATE_ACCOUNT);
		createAccount.verifyCreateAccoutnPageLanguage(rstV5DeviceData.get(CNV5Device.CREATE_ACCOUNT),requiredData.get(3),rstV5DeviceData.get(CNV5Device.ACTUAL_DATA));
		
		//Validating Account Login Page
		foundation.click(LandingPage.BTN_LOGIN);
		accountLogin.verifyAccountLoginPageLanguage(rstV5DeviceData.get(CNV5Device.LOGIN_PAGE));
			
        textBox.enterPin(propertyFile.readPropertyFile(Configuration.V5_PIN,FilePath.PROPERTY_CONFIG_FILE));
		foundation.click(AccountLogin.BTN_PIN_NEXT);
        
		//Verifying Account info page
		List<String> accountPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.ACCOUNT_DETAILS).split(Constants.DELIMITER_TILD));
		accountDetails.verifyAccountDetailsPageLanguage(rstV5DeviceData.get(CNV5Device.ACCOUNT_DETAILS),requiredData.get(3),rstV5DeviceData.get(CNV5Device.ACTUAL_DATA));
		
		//Verifying Fund with card page
		foundation.click(fundAccount.objText(accountPageData.get(1)));
		fundAccount.verifyFundAccountScreenLanguage(rstV5DeviceData.get(CNV5Device.FUND_ACCOUNT_PAGE));
		
		//Verifying Scan Setup page
		foundation.click(fundAccount.objText(accountPageData.get(4)));
		scanPayment.verifyScanPaymentPageLanguage(rstV5DeviceData.get(CNV5Device.QUICK_SCAN_SETUP),requiredData.get(3),rstV5DeviceData.get(CNV5Device.ACTUAL_DATA));
		
		//Verifying Finger print Setup page
		foundation.click(createAccount.objText(accountPageData.get(6)));
		fingerPrintPayment.verifyFingerPrintPaymentPageLanguage(rstV5DeviceData.get(CNV5Device.FINGER_PRINT_SETUP));
		
		//Verifying Edit account page
		foundation.click(createAccount.objText(accountPageData.get(7)));
		editAccount.verifyEditAccountPageLanguage(rstV5DeviceData.get(CNV5Device.EDIT_ACCOUNT_DETAILS));
		
		//verify Change pin
		List<String> accountEditPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.EDIT_ACCOUNT_DETAILS).split(Constants.DELIMITER_TILD));
		foundation.click(createAccount.objText(accountEditPageData.get(3)));
		changePin.verifyChangePinPageLanguage(rstV5DeviceData.get(CNV5Device.CHANGE_PIN));
		foundation.click(createAccount.objText(accountEditPageData.get(6)));
		
		//Verifying timeout popup
		editAccount.verifyTimeOutPopLanguage(rstV5DeviceData.get(CNV5Device.TIME_OUT_POPUP));

		//Verifying Product Purchase page
		foundation.waitforElement(LandingPage.IMG_SEARCH_ICON,Constants.SHORT_TIME);
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
	    
		//Resetting data
	    browser.launch(Constants.LOCAL,Constants.CHROME);
	    browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL,FilePath.PROPERTY_CONFIG_FILE));
		login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER,FilePath.PROPERTY_CONFIG_FILE), propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD,FilePath.PROPERTY_CONFIG_FILE));
	
		// Select Menu and Menu Item
		navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG,FilePath.PROPERTY_CONFIG_FILE));

        // Selecting location
        locationList.selectLocationName(requiredData.get(0));
        
        dropDown.selectItem(LocationSummary.DPD_KIOSK_LANGUAGE, requiredData.get(4), Constants.TEXT);
        dropDown.selectItem(LocationSummary.DPD_ALTERNATE_LANGUAGE, requiredData.get(5), Constants.TEXT);
        
        foundation.click(LocationSummary.BTN_SYNC);
        foundation.click(LocationSummary.BTN_SAVE);
        foundation.waitforElement(LocationList.TXT_FILTER, Constants.SHORT_TIME);
        login.logout();
                    
    } catch (Exception exc) {
        exc.printStackTrace();
        Assert.fail();
    }
}
	

	@Test(description = "142738 - SOS-24492 Verify alternate language is set to spanish in Kiosk when user set the Alternate Language as spanish and full sync is done in ADM")
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
			
			//Validating Landing Page
			foundation.waitforElement(landingPage.objLanguage(requiredData.get(3)), Constants.SHORT_TIME);
			landingPage.verifyHomeScreenLanguage(rstV5DeviceData.get(CNV5Device.LANDING_PAGE));

			//Validating Search Page
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			productSearch.verifyProductSearhPageLanguage(rstV5DeviceData.get(CNV5Device.PRODUCT_SEARCH_PAGE));

			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
	        foundation.click(ProductSearch.BTN_PRODUCT);
	        
	        //verify Order Page     
	       List<String> orderPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.ORDER_PAGE).split(Constants.DELIMITER_TILD));
	        order.verifyOrderPageLanguage(rstV5DeviceData.get(CNV5Device.ORDER_PAGE));
	        
	        //Validating Credit/Debit Page
	        foundation.click(order.objText(orderPageData.get(8)));
	        cardPayment.verifyCardPaymentPageLanguage(rstV5DeviceData.get(CNV5Device.CREDIT_DEBIT_PAGE));
	        
	        foundation.click(CardPayment.BTN_CLOSE);
	        foundation.waitforElement(order.objText(orderPageData.get(0)), Constants.SHORT_TIME);
	        
	        //verify Cancel Order Page
	  		foundation.click(order.objText(orderPageData.get(0)));
	        Assert.assertTrue(foundation.isDisplayed(createAccount.objText(rstV5DeviceData.get(CNV5Device.TRANSACTION_CANCEL))));
		
			//Validating Create Account Page
	        foundation.waitforElement(LandingPage.BTN_CREATE_ACCOUNT, Constants.SHORT_TIME);
			foundation.click(LandingPage.BTN_CREATE_ACCOUNT);
			createAccount.verifyCreateAccoutnPageLanguage(rstV5DeviceData.get(CNV5Device.CREATE_ACCOUNT),requiredData.get(3),rstV5DeviceData.get(CNV5Device.ACTUAL_DATA));
			
			//Validating Account Login Page
			foundation.click(LandingPage.BTN_LOGIN);
			accountLogin.verifyAccountLoginPageLanguage(rstV5DeviceData.get(CNV5Device.LOGIN_PAGE));
				
	        textBox.enterPin(propertyFile.readPropertyFile(Configuration.V5_PIN,FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(AccountLogin.BTN_PIN_NEXT);
	        
			//Verifying Account info page
			List<String> accountPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.ACCOUNT_DETAILS).split(Constants.DELIMITER_TILD));
			accountDetails.verifyAccountDetailsPageLanguage(rstV5DeviceData.get(CNV5Device.ACCOUNT_DETAILS),requiredData.get(3),rstV5DeviceData.get(CNV5Device.ACTUAL_DATA));
			
			//Verifying Fund with card page
			foundation.click(fundAccount.objText(accountPageData.get(1)));
			fundAccount.verifyFundAccountScreenLanguage(rstV5DeviceData.get(CNV5Device.FUND_ACCOUNT_PAGE));
			
			//Verifying Scan Setup page
			foundation.click(fundAccount.objText(accountPageData.get(4)));
			scanPayment.verifyScanPaymentPageLanguage(rstV5DeviceData.get(CNV5Device.QUICK_SCAN_SETUP),requiredData.get(3),rstV5DeviceData.get(CNV5Device.ACTUAL_DATA));
			
			//Verifying Finger print Setup page
			foundation.click(createAccount.objText(accountPageData.get(6)));
			fingerPrintPayment.verifyFingerPrintPaymentPageLanguage(rstV5DeviceData.get(CNV5Device.FINGER_PRINT_SETUP));
			
			//Verifying Edit account page
			foundation.click(createAccount.objText(accountPageData.get(7)));
			editAccount.verifyEditAccountPageLanguage(rstV5DeviceData.get(CNV5Device.EDIT_ACCOUNT_DETAILS));
			
			//verify Change pin
			List<String> accountEditPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.EDIT_ACCOUNT_DETAILS).split(Constants.DELIMITER_TILD));
			foundation.click(createAccount.objText(accountEditPageData.get(3)));
			changePin.verifyChangePinPageLanguage(rstV5DeviceData.get(CNV5Device.CHANGE_PIN));
			foundation.click(createAccount.objText(accountEditPageData.get(6)));
			
			//Verifying timeout popup
			editAccount.verifyTimeOutPopLanguage(rstV5DeviceData.get(CNV5Device.TIME_OUT_POPUP));

			//Verifying Product Purchase page
			foundation.waitforElement(LandingPage.IMG_SEARCH_ICON,Constants.SHORT_TIME);
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
			exc.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test(description = "C142739 - SOS-24492 Verify alternate language is set to German in Kiosk when user set the Alternate Language as German and full sync is done in ADM")
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
			
			//Validating Landing Page
			foundation.waitforElement(landingPage.objLanguage(requiredData.get(3)), Constants.SHORT_TIME);
			landingPage.verifyHomeScreenLanguage(rstV5DeviceData.get(CNV5Device.LANDING_PAGE));

			//Validating Search Page
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			productSearch.verifyProductSearhPageLanguage(rstV5DeviceData.get(CNV5Device.PRODUCT_SEARCH_PAGE));

			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
	        foundation.click(ProductSearch.BTN_PRODUCT);
	        
	        //verify Order Page     
	       List<String> orderPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.ORDER_PAGE).split(Constants.DELIMITER_TILD));
	        order.verifyOrderPageLanguage(rstV5DeviceData.get(CNV5Device.ORDER_PAGE));
	        
	        //Validating Credit/Debit Page
	        foundation.click(order.objText(orderPageData.get(8)));
	        cardPayment.verifyCardPaymentPageLanguage(rstV5DeviceData.get(CNV5Device.CREDIT_DEBIT_PAGE));
	        
	        foundation.click(CardPayment.BTN_CLOSE);
	        foundation.waitforElement(order.objText(orderPageData.get(0)), Constants.SHORT_TIME);
	        
	        //verify Cancel Order Page
	  		foundation.click(order.objText(orderPageData.get(0)));
	        Assert.assertTrue(foundation.isDisplayed(createAccount.objText(rstV5DeviceData.get(CNV5Device.TRANSACTION_CANCEL))));
		
			//Validating Create Account Page
	        foundation.waitforElement(LandingPage.BTN_CREATE_ACCOUNT, Constants.SHORT_TIME);
			foundation.click(LandingPage.BTN_CREATE_ACCOUNT);
			createAccount.verifyCreateAccoutnPageLanguage(rstV5DeviceData.get(CNV5Device.CREATE_ACCOUNT),requiredData.get(3),rstV5DeviceData.get(CNV5Device.ACTUAL_DATA));
			
			//Validating Account Login Page
			foundation.click(LandingPage.BTN_LOGIN);
			accountLogin.verifyAccountLoginPageLanguage(rstV5DeviceData.get(CNV5Device.LOGIN_PAGE));
				
	        textBox.enterPin(propertyFile.readPropertyFile(Configuration.V5_PIN,FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(AccountLogin.BTN_PIN_NEXT);
	        
			//Verifying Account info page
			List<String> accountPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.ACCOUNT_DETAILS).split(Constants.DELIMITER_TILD));
			accountDetails.verifyAccountDetailsPageLanguage(rstV5DeviceData.get(CNV5Device.ACCOUNT_DETAILS),requiredData.get(3),rstV5DeviceData.get(CNV5Device.ACTUAL_DATA));
			
			//Verifying Fund with card page
			foundation.click(fundAccount.objText(accountPageData.get(1)));
			fundAccount.verifyFundAccountScreenLanguage(rstV5DeviceData.get(CNV5Device.FUND_ACCOUNT_PAGE));
			
			//Verifying Scan Setup page
			foundation.click(fundAccount.objText(accountPageData.get(4)));
			scanPayment.verifyScanPaymentPageLanguage(rstV5DeviceData.get(CNV5Device.QUICK_SCAN_SETUP),requiredData.get(3),rstV5DeviceData.get(CNV5Device.ACTUAL_DATA));
			
			//Verifying Finger print Setup page
			foundation.click(createAccount.objText(accountPageData.get(6)));
			fingerPrintPayment.verifyFingerPrintPaymentPageLanguage(rstV5DeviceData.get(CNV5Device.FINGER_PRINT_SETUP));
			
			//Verifying Edit account page
			foundation.click(createAccount.objText(accountPageData.get(7)));
			editAccount.verifyEditAccountPageLanguage(rstV5DeviceData.get(CNV5Device.EDIT_ACCOUNT_DETAILS));
			
			//verify Change pin
			List<String> accountEditPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.EDIT_ACCOUNT_DETAILS).split(Constants.DELIMITER_TILD));
			foundation.click(createAccount.objText(accountEditPageData.get(3)));
			changePin.verifyChangePinPageLanguage(rstV5DeviceData.get(CNV5Device.CHANGE_PIN));
			foundation.click(createAccount.objText(accountEditPageData.get(6)));
			
			//Verifying timeout popup
			editAccount.verifyTimeOutPopLanguage(rstV5DeviceData.get(CNV5Device.TIME_OUT_POPUP));

			//Verifying Product Purchase page
			foundation.waitforElement(LandingPage.IMG_SEARCH_ICON,Constants.SHORT_TIME);
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
			exc.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test(description = "C142740 - SOS-24492 Verify alternate language is set to Danish in Kiosk when user set the Alternate Language as Danish and full sync is done in ADM")
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
			
			//Validating Landing Page
			foundation.waitforElement(landingPage.objLanguage(requiredData.get(3)), Constants.SHORT_TIME);
			landingPage.verifyHomeScreenLanguage(rstV5DeviceData.get(CNV5Device.LANDING_PAGE));

			//Validating Search Page
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			productSearch.verifyProductSearhPageLanguage(rstV5DeviceData.get(CNV5Device.PRODUCT_SEARCH_PAGE));

			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
	        foundation.click(ProductSearch.BTN_PRODUCT);
	        
	        //verify Order Page     
	       List<String> orderPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.ORDER_PAGE).split(Constants.DELIMITER_TILD));
	        order.verifyOrderPageLanguage(rstV5DeviceData.get(CNV5Device.ORDER_PAGE));
	        
	        //Validating Credit/Debit Page
	        foundation.click(order.objText(orderPageData.get(8)));
	        cardPayment.verifyCardPaymentPageLanguage(rstV5DeviceData.get(CNV5Device.CREDIT_DEBIT_PAGE));
	        
	        foundation.click(CardPayment.BTN_CLOSE);
	        foundation.waitforElement(order.objText(orderPageData.get(0)), Constants.SHORT_TIME);
	        
	        //verify Cancel Order Page
	  		foundation.click(order.objText(orderPageData.get(0)));
	        Assert.assertTrue(foundation.isDisplayed(createAccount.objText(rstV5DeviceData.get(CNV5Device.TRANSACTION_CANCEL))));
		
			//Validating Create Account Page
	        foundation.waitforElement(LandingPage.BTN_CREATE_ACCOUNT, Constants.SHORT_TIME);
			foundation.click(LandingPage.BTN_CREATE_ACCOUNT);
			createAccount.verifyCreateAccoutnPageLanguage(rstV5DeviceData.get(CNV5Device.CREATE_ACCOUNT),requiredData.get(3),rstV5DeviceData.get(CNV5Device.ACTUAL_DATA));
			
			//Validating Account Login Page
			foundation.click(LandingPage.BTN_LOGIN);
			accountLogin.verifyAccountLoginPageLanguage(rstV5DeviceData.get(CNV5Device.LOGIN_PAGE));
				
	        textBox.enterPin(propertyFile.readPropertyFile(Configuration.V5_PIN,FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(AccountLogin.BTN_PIN_NEXT);
	        
			//Verifying Account info page
			List<String> accountPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.ACCOUNT_DETAILS).split(Constants.DELIMITER_TILD));
			accountDetails.verifyAccountDetailsPageLanguage(rstV5DeviceData.get(CNV5Device.ACCOUNT_DETAILS),requiredData.get(3),rstV5DeviceData.get(CNV5Device.ACTUAL_DATA));
			
			//Verifying Fund with card page
			foundation.click(fundAccount.objText(accountPageData.get(1)));
			fundAccount.verifyFundAccountScreenLanguage(rstV5DeviceData.get(CNV5Device.FUND_ACCOUNT_PAGE));
			
			//Verifying Scan Setup page
			foundation.click(fundAccount.objText(accountPageData.get(4)));
			scanPayment.verifyScanPaymentPageLanguage(rstV5DeviceData.get(CNV5Device.QUICK_SCAN_SETUP),requiredData.get(3),rstV5DeviceData.get(CNV5Device.ACTUAL_DATA));
			
			//Verifying Finger print Setup page
			foundation.click(createAccount.objText(accountPageData.get(6)));
			fingerPrintPayment.verifyFingerPrintPaymentPageLanguage(rstV5DeviceData.get(CNV5Device.FINGER_PRINT_SETUP));
			
			//Verifying Edit account page
			foundation.click(createAccount.objText(accountPageData.get(7)));
			editAccount.verifyEditAccountPageLanguage(rstV5DeviceData.get(CNV5Device.EDIT_ACCOUNT_DETAILS));
			
			//verify Change pin
			List<String> accountEditPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.EDIT_ACCOUNT_DETAILS).split(Constants.DELIMITER_TILD));
			foundation.click(createAccount.objText(accountEditPageData.get(3)));
			changePin.verifyChangePinPageLanguage(rstV5DeviceData.get(CNV5Device.CHANGE_PIN));
			foundation.click(createAccount.objText(accountEditPageData.get(6)));
			
			//Verifying timeout popup
			editAccount.verifyTimeOutPopLanguage(rstV5DeviceData.get(CNV5Device.TIME_OUT_POPUP));

			//Verifying Product Purchase page
			foundation.waitforElement(LandingPage.IMG_SEARCH_ICON,Constants.SHORT_TIME);
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

			//resetting test data
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
			exc.printStackTrace();
			Assert.fail();
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
			
			//Validating Landing Page
			foundation.waitforElement(landingPage.objLanguage(requiredData.get(3)), Constants.SHORT_TIME);
			landingPage.verifyHomeScreenLanguage(rstV5DeviceData.get(CNV5Device.LANDING_PAGE));

			//Validating Search Page
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			productSearch.verifyProductSearhPageLanguage(rstV5DeviceData.get(CNV5Device.PRODUCT_SEARCH_PAGE));

			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
	        foundation.click(ProductSearch.BTN_PRODUCT);
	        
	        //verify Order Page     
	       List<String> orderPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.ORDER_PAGE).split(Constants.DELIMITER_TILD));
	        order.verifyOrderPageLanguage(rstV5DeviceData.get(CNV5Device.ORDER_PAGE));
	        
	        //Validating Credit/Debit Page
	        foundation.click(order.objText(orderPageData.get(8)));
	        cardPayment.verifyCardPaymentPageLanguage(rstV5DeviceData.get(CNV5Device.CREDIT_DEBIT_PAGE));
	        
	        foundation.click(CardPayment.BTN_CLOSE);
	        foundation.waitforElement(order.objText(orderPageData.get(0)), Constants.SHORT_TIME);
	        
	        //verify Cancel Order Page
	  		foundation.click(order.objText(orderPageData.get(0)));
	        Assert.assertTrue(foundation.isDisplayed(createAccount.objText(rstV5DeviceData.get(CNV5Device.TRANSACTION_CANCEL))));
		
			//Validating Create Account Page
	        foundation.waitforElement(LandingPage.BTN_CREATE_ACCOUNT, Constants.SHORT_TIME);
			foundation.click(LandingPage.BTN_CREATE_ACCOUNT);
			createAccount.verifyCreateAccoutnPageLanguage(rstV5DeviceData.get(CNV5Device.CREATE_ACCOUNT),requiredData.get(3),rstV5DeviceData.get(CNV5Device.ACTUAL_DATA));
			
			//Validating Account Login Page
			foundation.click(LandingPage.BTN_LOGIN);
			accountLogin.verifyAccountLoginPageLanguage(rstV5DeviceData.get(CNV5Device.LOGIN_PAGE));
				
	        textBox.enterPin(propertyFile.readPropertyFile(Configuration.V5_PIN,FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(AccountLogin.BTN_PIN_NEXT);
	        
			//Verifying Account info page
			List<String> accountPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.ACCOUNT_DETAILS).split(Constants.DELIMITER_TILD));
			accountDetails.verifyAccountDetailsPageLanguage(rstV5DeviceData.get(CNV5Device.ACCOUNT_DETAILS),requiredData.get(3),rstV5DeviceData.get(CNV5Device.ACTUAL_DATA));
			
			//Verifying Fund with card page
			foundation.click(fundAccount.objText(accountPageData.get(1)));
			fundAccount.verifyFundAccountScreenLanguage(rstV5DeviceData.get(CNV5Device.FUND_ACCOUNT_PAGE));
			
			//Verifying Scan Setup page
			foundation.click(fundAccount.objText(accountPageData.get(4)));
			scanPayment.verifyScanPaymentPageLanguage(rstV5DeviceData.get(CNV5Device.QUICK_SCAN_SETUP),requiredData.get(3),rstV5DeviceData.get(CNV5Device.ACTUAL_DATA));
			
			//Verifying Finger print Setup page
			foundation.click(createAccount.objText(accountPageData.get(6)));
			fingerPrintPayment.verifyFingerPrintPaymentPageLanguage(rstV5DeviceData.get(CNV5Device.FINGER_PRINT_SETUP));
			
			//Verifying Edit account page
			foundation.click(createAccount.objText(accountPageData.get(7)));
			editAccount.verifyEditAccountPageLanguage(rstV5DeviceData.get(CNV5Device.EDIT_ACCOUNT_DETAILS));
			
			//verify Change pin
			List<String> accountEditPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.EDIT_ACCOUNT_DETAILS).split(Constants.DELIMITER_TILD));
			foundation.click(createAccount.objText(accountEditPageData.get(3)));
			changePin.verifyChangePinPageLanguage(rstV5DeviceData.get(CNV5Device.CHANGE_PIN));
			foundation.click(createAccount.objText(accountEditPageData.get(6)));
			
			//Verifying timeout popup
			editAccount.verifyTimeOutPopLanguage(rstV5DeviceData.get(CNV5Device.TIME_OUT_POPUP));

			//Verifying Product Purchase page
			foundation.waitforElement(LandingPage.IMG_SEARCH_ICON,Constants.SHORT_TIME);
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
			exc.printStackTrace();
			Assert.fail();
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
			
			//Validating Landing Page
			foundation.waitforElement(landingPage.objLanguage(requiredData.get(3)), Constants.SHORT_TIME);
			landingPage.verifyHomeScreenLanguage(rstV5DeviceData.get(CNV5Device.LANDING_PAGE));

			//Validating Search Page
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			productSearch.verifyProductSearhPageLanguage(rstV5DeviceData.get(CNV5Device.PRODUCT_SEARCH_PAGE));

			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
	        foundation.click(ProductSearch.BTN_PRODUCT);
	        
	        //verify Order Page     
	       List<String> orderPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.ORDER_PAGE).split(Constants.DELIMITER_TILD));
	        order.verifyOrderPageLanguage(rstV5DeviceData.get(CNV5Device.ORDER_PAGE));
	        
	        //Validating Credit/Debit Page
	        foundation.click(order.objText(orderPageData.get(8)));
	        cardPayment.verifyCardPaymentPageLanguage(rstV5DeviceData.get(CNV5Device.CREDIT_DEBIT_PAGE));
	        
	        foundation.click(CardPayment.BTN_CLOSE);
	        foundation.waitforElement(order.objText(orderPageData.get(0)), Constants.SHORT_TIME);
	        
	        //verify Cancel Order Page
	  		foundation.click(order.objText(orderPageData.get(0)));
	        Assert.assertTrue(foundation.isDisplayed(createAccount.objText(rstV5DeviceData.get(CNV5Device.TRANSACTION_CANCEL))));
		
			//Validating Create Account Page
	        foundation.waitforElement(LandingPage.BTN_CREATE_ACCOUNT, Constants.SHORT_TIME);
			foundation.click(LandingPage.BTN_CREATE_ACCOUNT);
			createAccount.verifyCreateAccoutnPageLanguage(rstV5DeviceData.get(CNV5Device.CREATE_ACCOUNT),requiredData.get(3),rstV5DeviceData.get(CNV5Device.ACTUAL_DATA));
			
			//Validating Account Login Page
			foundation.click(LandingPage.BTN_LOGIN);
			accountLogin.verifyAccountLoginPageLanguage(rstV5DeviceData.get(CNV5Device.LOGIN_PAGE));
				
	        textBox.enterPin(propertyFile.readPropertyFile(Configuration.V5_PIN,FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(AccountLogin.BTN_PIN_NEXT);
	        
			//Verifying Account info page
			List<String> accountPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.ACCOUNT_DETAILS).split(Constants.DELIMITER_TILD));
			accountDetails.verifyAccountDetailsPageLanguage(rstV5DeviceData.get(CNV5Device.ACCOUNT_DETAILS),requiredData.get(3),rstV5DeviceData.get(CNV5Device.ACTUAL_DATA));
			
			//Verifying Fund with card page
			foundation.click(fundAccount.objText(accountPageData.get(1)));
			fundAccount.verifyFundAccountScreenLanguage(rstV5DeviceData.get(CNV5Device.FUND_ACCOUNT_PAGE));
			
			//Verifying Scan Setup page
			foundation.click(fundAccount.objText(accountPageData.get(4)));
			scanPayment.verifyScanPaymentPageLanguage(rstV5DeviceData.get(CNV5Device.QUICK_SCAN_SETUP),requiredData.get(3),rstV5DeviceData.get(CNV5Device.ACTUAL_DATA));
			
			//Verifying Finger print Setup page
			foundation.click(createAccount.objText(accountPageData.get(6)));
			fingerPrintPayment.verifyFingerPrintPaymentPageLanguage(rstV5DeviceData.get(CNV5Device.FINGER_PRINT_SETUP));
			
			//Verifying Edit account page
			foundation.click(createAccount.objText(accountPageData.get(7)));
			editAccount.verifyEditAccountPageLanguage(rstV5DeviceData.get(CNV5Device.EDIT_ACCOUNT_DETAILS));
			
			//verify Change pin
			List<String> accountEditPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.EDIT_ACCOUNT_DETAILS).split(Constants.DELIMITER_TILD));
			foundation.click(createAccount.objText(accountEditPageData.get(3)));
			changePin.verifyChangePinPageLanguage(rstV5DeviceData.get(CNV5Device.CHANGE_PIN));
			foundation.click(createAccount.objText(accountEditPageData.get(6)));
			
			//Verifying timeout popup
			editAccount.verifyTimeOutPopLanguage(rstV5DeviceData.get(CNV5Device.TIME_OUT_POPUP));

			//Verifying Product Purchase page
			foundation.waitforElement(LandingPage.IMG_SEARCH_ICON,Constants.SHORT_TIME);
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
			
			//Navigating to product search page
			foundation.waitforElement(LandingPage.IMG_SEARCH_ICON,Constants.SHORT_TIME);
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			foundation.waitforElement(AccountLogin.BTN_CAMELCASE,Constants.SHORT_TIME);

			//searching for product
			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
	        foundation.click(ProductSearch.BTN_PRODUCT);
	        
	        //verify Order Page     
	       List<String> orderPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.ORDER_PAGE).split(Constants.DELIMITER_TILD));
	       Assert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(0))));

			foundation.objectFocus(order.objText(orderPageData.get(1)));
			foundation.click(order.objText(orderPageData.get(1)));

			foundation.waitforElement(AccountLogin.BTN_NEXT, Constants.SHORT_TIME);
			  
			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(propertyFile.readPropertyFile(Configuration.V5_USER,FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(AccountLogin.BTN_NEXT);
			foundation.waitforElement(AccountLogin.BTN_PIN_NEXT, Constants.SHORT_TIME);
			textBox.enterPin(propertyFile.readPropertyFile(Configuration.V5_PIN,FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(AccountLogin.BTN_PIN_NEXT);
			
			List<String> paymentPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.PAYMENTS_PAGE).split(Constants.DELIMITER_TILD));
			Assert.assertTrue(foundation.isDisplayed(payments.objText(paymentPageData.get(0))));     

			foundation.click(payments.objText(paymentPageData.get(1)));
			foundation.waitforElement(LandingPage.IMG_SEARCH_ICON,Constants.SHORT_TIME);

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
			Map<String, String> invCountBeforeSale=locationSummary.getProductDetails(requiredData.get(3));
			
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
			
			//Navigating to product search page
			foundation.waitforElement(LandingPage.IMG_SEARCH_ICON,Constants.SHORT_TIME);
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			foundation.waitforElement(AccountLogin.BTN_CAMELCASE,Constants.SHORT_TIME);

			//searching for product
			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
	        foundation.click(ProductSearch.BTN_PRODUCT);
	        
	        //verify Order Page     
	       List<String> orderPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.ORDER_PAGE).split(Constants.DELIMITER_TILD));
	       Assert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(0))));

			foundation.objectFocus(order.objText(orderPageData.get(1)));
			foundation.click(order.objText(orderPageData.get(1)));

			foundation.waitforElement(AccountLogin.BTN_NEXT, Constants.SHORT_TIME);
			  
			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(propertyFile.readPropertyFile(Configuration.V5_USER,FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(AccountLogin.BTN_NEXT);
			foundation.waitforElement(AccountLogin.BTN_PIN_NEXT, Constants.SHORT_TIME);
			textBox.enterPin(propertyFile.readPropertyFile(Configuration.V5_PIN,FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(AccountLogin.BTN_PIN_NEXT);
			
			List<String> paymentPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.PAYMENTS_PAGE).split(Constants.DELIMITER_TILD));
			Assert.assertTrue(foundation.isDisplayed(payments.objText(paymentPageData.get(0))));     

			foundation.click(payments.objText(paymentPageData.get(1)));
			foundation.waitforElement(LandingPage.IMG_SEARCH_ICON,Constants.SHORT_TIME);
			
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
			textBox.enterText(locationSummary.TXT_PRODUCT_FILTER, rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
			Map<String, String> invCountAfterSale=locationSummary.getProductDetails(requiredData.get(3));
       
	        Assert.assertTrue(Integer.valueOf(invCountAfterSale.get(requiredData.get(3)))==Integer.valueOf(invCountBeforeSale.get(requiredData.get(3)))-1);

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

			// enter new balance with reason
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

			dropDown.selectItem(LocationSummary.DPD_KIOSK_LANGUAGE, requiredData.get(1), Constants.TEXT);
//			locationSummary.selectTab(requiredData.get(2));
//			
//			foundation.waitforElement(LocationSummary.TBL_PRODUCTS_GRID, Constants.SHORT_TIME);
//			textBox.enterText(LocationSummary.TXT_PRODUCT_FILTER, rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
//			Map<String, String> invCountBeforeSale=locationSummary.getProductDetails(requiredData.get(3));
//			
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
			
			//Navigating to product search page
			foundation.waitforElement(LandingPage.IMG_SEARCH_ICON,Constants.SHORT_TIME);
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			foundation.waitforElement(AccountLogin.BTN_CAMELCASE,Constants.SHORT_TIME);

			//searching for product
			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
	        foundation.click(ProductSearch.BTN_PRODUCT);
	        
	        //verify Order Page     
	       List<String> orderPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.ORDER_PAGE).split(Constants.DELIMITER_TILD));
	       Assert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(0))));

			foundation.objectFocus(order.objText(orderPageData.get(1)));
			foundation.click(order.objText(orderPageData.get(1)));

			foundation.waitforElement(AccountLogin.BTN_NEXT, Constants.SHORT_TIME);
			  
			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(propertyFile.readPropertyFile(Configuration.V5_USER,FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(AccountLogin.BTN_NEXT);
			foundation.waitforElement(AccountLogin.BTN_PIN_NEXT, Constants.SHORT_TIME);
			textBox.enterPin(propertyFile.readPropertyFile(Configuration.V5_PIN,FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(AccountLogin.BTN_PIN_NEXT);
			
			List<String> paymentPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.PAYMENTS_PAGE).split(Constants.DELIMITER_TILD));
			Assert.assertTrue(foundation.isDisplayed(payments.objText(paymentPageData.get(0))));     

			foundation.click(payments.objText(paymentPageData.get(1)));
			Assert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(0))));
			//foundation.waitforElement(LandingPage.IMG_SEARCH_ICON,Constants.SHORT_TIME);
			
			browser.close();
			
			browser.launch(Constants.LOCAL, Constants.CHROME);
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

			// enter new balance with reason
			textBox.enterText(ConsumerSummary.TXT_ADJUST_BALANCE, rstConsumerSummaryData.get(CNConsumerSummary.AMOUNT));
			dropDown.selectItem(ConsumerSummary.DPD_REASON, rstConsumerSummaryData.get(CNConsumerSummary.REASON),
					Constants.TEXT);
			foundation.click(ConsumerSummary.BTN_REASON_SAVE);
			foundation.click(ConsumerSummary.BTN_SAVE);
//
//			// Selecting location
//			locationList.selectLocationName(requiredData.get(0));
//
//			dropDown.selectItem(LocationSummary.DPD_KIOSK_LANGUAGE, requiredData.get(1), Constants.TEXT);
//			locationSummary.selectTab(requiredData.get(2));
//			
//			foundation.waitforElement(LocationSummary.TBL_PRODUCTS_GRID, Constants.SHORT_TIME);
//			textBox.enterText(locationSummary.TXT_PRODUCT_FILTER, rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
//			Map<String, String> invCountAfterSale=locationSummary.getProductDetails(requiredData.get(3));
//       
//	        Assert.assertTrue(Integer.valueOf(invCountAfterSale.get(requiredData.get(3)))==Integer.valueOf(invCountBeforeSale.get(requiredData.get(3)))-1);

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
			locationSummary.updateInventory(requiredData.get(3), requiredData.get(4), requiredData.get(5));

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
			
			//Navigating to product search page
			foundation.waitforElement(LandingPage.IMG_SEARCH_ICON,Constants.SHORT_TIME);
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			foundation.waitforElement(AccountLogin.BTN_CAMELCASE,Constants.SHORT_TIME);

			//searching for product
			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
	        foundation.click(ProductSearch.BTN_PRODUCT);
	        
	        //verify Order Page     
	       List<String> orderPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.ORDER_PAGE).split(Constants.DELIMITER_TILD));
	       Assert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(0))));

			foundation.objectFocus(order.objText(orderPageData.get(1)));
			foundation.click(order.objText(orderPageData.get(1)));

			foundation.waitforElement(AccountLogin.BTN_NEXT, Constants.SHORT_TIME);
			  
			foundation.click(AccountLogin.BTN_CAMELCASE);
			textBox.enterKeypadText(propertyFile.readPropertyFile(Configuration.V5_USER,FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(AccountLogin.BTN_NEXT);
			foundation.waitforElement(AccountLogin.BTN_PIN_NEXT, Constants.SHORT_TIME);
			textBox.enterPin(propertyFile.readPropertyFile(Configuration.V5_PIN,FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(AccountLogin.BTN_PIN_NEXT);
			
			List<String> paymentPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.PAYMENTS_PAGE).split(Constants.DELIMITER_TILD));
			Assert.assertTrue(foundation.isDisplayed(payments.objText(paymentPageData.get(0))));     

			foundation.click(payments.objText(paymentPageData.get(1)));
			foundation.waitforElement(LandingPage.IMG_SEARCH_ICON,Constants.SHORT_TIME);
			
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
			textBox.enterText(locationSummary.TXT_PRODUCT_FILTER, rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
			locationSummary.updateInventory(requiredData.get(3), requiredData.get(6), requiredData.get(7));
		
		} catch (Exception exc) {
			exc.printStackTrace();
			Assert.fail();
		}
	}
}
