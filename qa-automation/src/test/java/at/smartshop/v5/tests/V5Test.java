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
import at.smartshop.database.columns.CNV5Device;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.pages.LocationList;
import at.smartshop.pages.LocationSummary;
import at.smartshop.pages.NavigationBar;
import at.smartshop.tests.TestInfra;
import at.smartshop.v5.pages.AccountDetails;
import at.smartshop.v5.pages.AccountLogin;
import at.smartshop.v5.pages.CardPayment;
import at.smartshop.v5.pages.CreateAccount;
import at.smartshop.v5.pages.EditAccount;
import at.smartshop.v5.pages.LandingPage;
import at.smartshop.v5.pages.Order;
import at.smartshop.v5.pages.ProductSearch;

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
	

	private Map<String, String> rstV5DeviceData;	
	
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
	public void englishItalianLanguage() {
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
        foundation.waitforElement(LocationList.TXT_FILTER, 5);
        login.logout();
        browser.close();
        
        foundation.threadWait(5000);
        //login into Kiosk Device
        browser.launch(Constants.REMOTE,Constants.CHROME);
		browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
		
		//Validating Landing Page
		foundation.waitforElement(landingPage.objLanguage(requiredData.get(3)), 5);
		List<String> landingPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.LANDING_PAGE).split(Constants.DELIMITER_TILD));
	
		Assert.assertEquals(foundation.getText(LandingPage.LBL_ACCOUNT_LOGIN),landingPageData.get(0));
		Assert.assertEquals(foundation.getText(LandingPage.LBL_CREATE_ACCOUNT),landingPageData.get(1));
		Assert.assertTrue(foundation.isDisplayed(landingPage.objLanguage(landingPageData.get(2))));
		Assert.assertTrue(foundation.isDisplayed(landingPage.objLanguage(landingPageData.get(3))));
		Assert.assertEquals(foundation.getText(LandingPage.LBL_HEADER),landingPageData.get(4));
		Assert.assertEquals(foundation.getText(LandingPage.LBL_SEARCH),landingPageData.get(5));
		Assert.assertEquals(foundation.getText(LandingPage.LBL_SCAN),landingPageData.get(6));

		//Validating Search Page
		foundation.click(LandingPage.IMG_SEARCH_ICON);
		List<String> productSearchPage = Arrays.asList(rstV5DeviceData.get(CNV5Device.PRODUCT_SEARCH_PAGE).split(Constants.DELIMITER_TILD));
	
		Assert.assertEquals(foundation.getText(ProductSearch.LBL_PRODUCT_SEARCH_TITLE),productSearchPage.get(0));
		Assert.assertEquals(foundation.getText(ProductSearch.LBL_PRODUCT_SEARCH_HEADER),productSearchPage.get(1));
		Assert.assertEquals(foundation.getText(ProductSearch.LBL_PRODUCT_FOUND),productSearchPage.get(2));

		foundation.click(AccountLogin.BTN_CAMELCASE);
		textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
        foundation.click(ProductSearch.BTN_PRODUCT);
        
        //verify Order Page
        List<String> orderPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.ORDER_PAGE).split(Constants.DELIMITER_TILD));
        Assert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(0))));
        Assert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(1))));
        Assert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(2))));
        Assert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(3))));
        Assert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(4))));
        Assert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(5))));
        Assert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(6))));
        foundation.objectFocus(order.objText(orderPageData.get(7)));
        Assert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(7))));
        Assert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(8))));
        
        
        //Validating Credit/Debit Page
        foundation.click(order.objText(orderPageData.get(8)));
        List<String> crditDebitPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.CREDIT_DEBIT_PAGE).split(Constants.DELIMITER_TILD));
        foundation.waitforElement(cardPayment.objText(crditDebitPageData.get(0)), 5);        
        Assert.assertTrue(foundation.isDisplayed(cardPayment.objText(crditDebitPageData.get(0))));
        Assert.assertTrue(foundation.isDisplayed(cardPayment.objText(crditDebitPageData.get(1))));
        
        foundation.click(CardPayment.BTN_CLOSE);
        foundation.waitforElement(Order.BTN_CANCEL_ORDER, 5);
        
        //verify Cancel Order Page
  		foundation.click(Order.BTN_CANCEL_ORDER);
        Assert.assertTrue(foundation.isDisplayed(createAccount.objText(rstV5DeviceData.get(CNV5Device.TRANSACTION_CANCEL))));
		
        
		//Validating Create Account Page
        foundation.waitforElement(LandingPage.BTN_CREATE_ACCOUNT, 5);
		foundation.click(LandingPage.BTN_CREATE_ACCOUNT);
		List<String> createAccountPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.CREATE_ACCOUNT).split(Constants.DELIMITER_TILD));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(createAccountPageData.get(0))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(createAccountPageData.get(1))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(createAccountPageData.get(2))));
		foundation.objectFocus(CreateAccount.CHK_LABEL);
		foundation.click(CreateAccount.CHK_LABEL);
	
		foundation.click(createAccount.objText(createAccountPageData.get(1)));
		foundation.waitforElement(createAccount.objText(createAccountPageData.get(3)), 5);
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(createAccountPageData.get(4))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(createAccountPageData.get(5))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(createAccountPageData.get(6))));
		
		foundation.click(createAccount.objText(createAccountPageData.get(6)));
		foundation.objectFocus(CreateAccount.CHK_LABEL);
		foundation.click(CreateAccount.CHK_LABEL);
		foundation.click(createAccount.objText(createAccountPageData.get(1)));
		
		foundation.waitforElement(createAccount.objText(createAccountPageData.get(8)), 5);
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(createAccountPageData.get(3))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(createAccountPageData.get(7))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(createAccountPageData.get(8))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(createAccountPageData.get(9))));
				
		foundation.click(createAccount.objText(createAccountPageData.get(9)));
		foundation.waitforElement(createAccount.objText(createAccountPageData.get(4)), 5);
		foundation.click(createAccount.objText(createAccountPageData.get(5)));
		foundation.waitforElement(createAccount.objText(createAccountPageData.get(11)), 5);
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(createAccountPageData.get(3))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(createAccountPageData.get(10))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(createAccountPageData.get(11))));
		foundation.click(CreateAccount.BTN_CLOSE);
		foundation.waitforElement(LandingPage.BTN_CREATE_ACCOUNT,5);
		
		//Validating Account Login Page
		foundation.click(LandingPage.BTN_LOGIN);
		List<String> loginPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.LOGIN_PAGE).split(Constants.DELIMITER_TILD));
		Assert.assertEquals(foundation.getText(AccountLogin.LBL_PAGE_TITLE),loginPageData.get(0));
		Assert.assertEquals(foundation.getText(AccountLogin.LBL_PAGE_HEADER),loginPageData.get(1));
		Assert.assertEquals(foundation.getText(AccountLogin.LBL_SCAN),loginPageData.get(2));
		Assert.assertEquals(foundation.getText(AccountLogin.LBL_FINGER_PRINT),loginPageData.get(3));
		Assert.assertEquals(foundation.getText(AccountLogin.BTN_EMAIL_LOGIN),loginPageData.get(4));
		Assert.assertEquals(foundation.getText(AccountLogin.BTN_I_DONT_HAVE_ACCOUNT),loginPageData.get(5));
		
        //Validating Account Login Email Page
		foundation.click(AccountLogin.BTN_EMAIL_LOGIN);
		foundation.waitforElement(AccountLogin.BTN_NEXT, 5);
		Assert.assertEquals(foundation.getText(AccountLogin.LBL_PAGE_TITLE),loginPageData.get(0));
		Assert.assertEquals(foundation.getText(AccountLogin.LBL_EMAIL_HEADER),loginPageData.get(6));
		Assert.assertEquals(foundation.getText(AccountLogin.BTN_EMAIl_BACK),loginPageData.get(7));
		Assert.assertEquals(foundation.getText(AccountLogin.BTN_NEXT),loginPageData.get(8));
		  
		foundation.click(AccountLogin.BTN_CAMELCASE);
		textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.EMAIL_ID));
		foundation.click(AccountLogin.BTN_ENTER);
        foundation.click(AccountLogin.BTN_NEXT);
        
        //Validating Account Login PIN Page
        foundation.waitforElement(AccountLogin.BTN_PIN_NEXT, 5);
        Assert.assertEquals(foundation.getText(AccountLogin.LBL_PIN_PAGE_TITLE),loginPageData.get(0));
		Assert.assertEquals(foundation.getText(AccountLogin.LBL_PIN_HEADER),loginPageData.get(9));
		Assert.assertEquals(foundation.getText(AccountLogin.BTN_PIN_BACK),loginPageData.get(7));
		Assert.assertEquals(foundation.getText(AccountLogin.BTN_PIN_NEXT),loginPageData.get(10));
		
        textBox.enterPin(rstV5DeviceData.get(CNV5Device.PIN));
		foundation.click(AccountLogin.BTN_PIN_NEXT);
        
		//Verifying Account info page
		List<String> accountPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.ACCOUNT_DETAILS).split(Constants.DELIMITER_TILD));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountPageData.get(0))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountPageData.get(1))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountPageData.get(2))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountPageData.get(3))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountPageData.get(4))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountPageData.get(5))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountPageData.get(6))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountPageData.get(5))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountPageData.get(7))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountPageData.get(8))));
		
		//Verify Terms and Condition Page
		foundation.click(createAccount.objText(accountPageData.get(9)));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountPageData.get(9))));
		Assert.assertEquals(foundation.getText(AccountDetails.BTN_OK),accountPageData.get(10));
		foundation.click(AccountDetails.BTN_OK);
		
		//Verifying Fund with card page
		List<String> fundPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.FUND_ACCOUNT_PAGE).split(Constants.DELIMITER_TILD));
		foundation.click(createAccount.objText(accountPageData.get(1)));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(fundPageData.get(0))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(fundPageData.get(1))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(fundPageData.get(2))));
		foundation.click(createAccount.objText(fundPageData.get(3)));
		
		//Verifying Fund with card page details page
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(fundPageData.get(4))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(fundPageData.get(5))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(fundPageData.get(6))));
		
		foundation.click(createAccount.objText(fundPageData.get(6)));
		foundation.click(createAccount.objText(fundPageData.get(2)));
		foundation.click(createAccount.objText(accountPageData.get(4)));
		
		//Verifying Scan Setup page
		List<String> scanSetupPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.QUICK_SCAN_SETUP).split(Constants.DELIMITER_TILD));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(scanSetupPageData.get(0))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(scanSetupPageData.get(1))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(scanSetupPageData.get(2))));
		
		foundation.click(createAccount.objText(scanSetupPageData.get(2)));
		
		//Verifying Finger print Setup page
		List<String> fingerPrintPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.FINGER_PRINT_SETUP).split(Constants.DELIMITER_TILD));
		foundation.click(createAccount.objText(accountPageData.get(6)));

		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(fingerPrintPageData.get(0))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(fingerPrintPageData.get(1))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(fingerPrintPageData.get(2))));
		
		foundation.click(createAccount.objText(fingerPrintPageData.get(2)));
		
		//Verifying Edit account page
		foundation.click(createAccount.objText(accountPageData.get(7)));
		List<String> accountEditPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.EDIT_ACCOUNT_DETAILS).split(Constants.DELIMITER_TILD));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountEditPageData.get(0))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountEditPageData.get(1))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountEditPageData.get(2))));		
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountEditPageData.get(3))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountEditPageData.get(4))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountEditPageData.get(5))));	
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountEditPageData.get(6))));
		
		//verify Change pin
		foundation.click(createAccount.objText(accountEditPageData.get(3)));
		List<String> changePinPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.CHANGE_PIN).split(Constants.DELIMITER_TILD));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(changePinPageData.get(0))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(changePinPageData.get(1))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(changePinPageData.get(2))));
		
		foundation.click(createAccount.objText(changePinPageData.get(2)));
		
		foundation.click(createAccount.objText(accountEditPageData.get(6)));
		//Verifying timeout popup
		foundation.waitforElement(Order.POP_UP_LBL_ORDER_TIMEOUT, 30);
		List<String> timeOutPopupData = Arrays.asList(rstV5DeviceData.get(CNV5Device.TIME_OUT_POPUP).split(Constants.DELIMITER_TILD));
		Assert.assertEquals(foundation.getText(Order.POP_UP_TIMEOUT_YES),timeOutPopupData.get(3));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(timeOutPopupData.get(0))));
		foundation.click(Order.POP_UP_TIMEOUT_YES);
		foundation.waitforElement(createAccount.objText(timeOutPopupData.get(0)), 30);
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(timeOutPopupData.get(1))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(timeOutPopupData.get(2))));
	    browser.close();
	    
		
	    browser.launch(Constants.LOCAL,Constants.CHROME);
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
        foundation.waitforElement(LocationList.TXT_FILTER, 5);
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
        foundation.waitforElement(LocationList.TXT_FILTER, 5);
        login.logout();
        browser.close();
        
        foundation.threadWait(5000);
        //login into Kiosk Device
        browser.launch(Constants.REMOTE,Constants.CHROME);
		browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
		
		foundation.click(LandingPage.BTN_LANG);
		//Validating Landing Page
		foundation.waitforElement(landingPage.objLanguage(requiredData.get(3)), 5);
		List<String> landingPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.LANDING_PAGE).split(Constants.DELIMITER_TILD));
	
		Assert.assertEquals(foundation.getText(LandingPage.LBL_ACCOUNT_LOGIN),landingPageData.get(0));
		Assert.assertEquals(foundation.getText(LandingPage.LBL_CREATE_ACCOUNT),landingPageData.get(1));
		Assert.assertTrue(foundation.isDisplayed(landingPage.objLanguage(landingPageData.get(2))));
		Assert.assertTrue(foundation.isDisplayed(landingPage.objLanguage(landingPageData.get(3))));
		Assert.assertEquals(foundation.getText(LandingPage.LBL_HEADER),landingPageData.get(4));
		Assert.assertEquals(foundation.getText(LandingPage.LBL_SEARCH),landingPageData.get(5));
		Assert.assertEquals(foundation.getText(LandingPage.LBL_SCAN),landingPageData.get(6));

		//Validating Search Page
		foundation.click(LandingPage.IMG_SEARCH_ICON);
		List<String> productSearchPage = Arrays.asList(rstV5DeviceData.get(CNV5Device.PRODUCT_SEARCH_PAGE).split(Constants.DELIMITER_TILD));
	
		Assert.assertEquals(foundation.getText(ProductSearch.LBL_PRODUCT_SEARCH_TITLE),productSearchPage.get(0));
		Assert.assertEquals(foundation.getText(ProductSearch.LBL_PRODUCT_SEARCH_HEADER),productSearchPage.get(1));
		Assert.assertEquals(foundation.getText(ProductSearch.LBL_PRODUCT_FOUND),productSearchPage.get(2));

		foundation.click(AccountLogin.BTN_CAMELCASE);
		textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
        foundation.click(ProductSearch.BTN_PRODUCT);
        
        //verify Order Page
        List<String> orderPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.ORDER_PAGE).split(Constants.DELIMITER_TILD));
        Assert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(0))));
        Assert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(1))));
        Assert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(2))));
        Assert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(3))));
        Assert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(4))));
        Assert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(5))));
        Assert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(6))));
        foundation.objectFocus(order.objText(orderPageData.get(7)));
        Assert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(7))));
        Assert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(8))));
        
        
        //Validating Credit/Debit Page
        foundation.click(order.objText(orderPageData.get(8)));
        List<String> crditDebitPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.CREDIT_DEBIT_PAGE).split(Constants.DELIMITER_TILD));
        foundation.waitforElement(cardPayment.objText(crditDebitPageData.get(0)), 5);        
        Assert.assertTrue(foundation.isDisplayed(cardPayment.objText(crditDebitPageData.get(0))));
        Assert.assertTrue(foundation.isDisplayed(cardPayment.objText(crditDebitPageData.get(1))));
        Assert.assertTrue(foundation.isDisplayed(cardPayment.objText(crditDebitPageData.get(2))));
        
        foundation.click(CardPayment.BTN_CLOSE);
        foundation.waitforElement(cardPayment.objText(orderPageData.get(1)), 5);
        
        //verify Cancel Order Page
  		foundation.click(cardPayment.objText(orderPageData.get(1)));
        Assert.assertTrue(foundation.isDisplayed(createAccount.objText(rstV5DeviceData.get(CNV5Device.TRANSACTION_CANCEL))));
		
        
		//Validating Create Account Page
        foundation.waitforElement(LandingPage.BTN_CREATE_ACCOUNT, 5);
		foundation.click(LandingPage.BTN_CREATE_ACCOUNT);
		List<String> createAccountPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.CREATE_ACCOUNT).split(Constants.DELIMITER_TILD));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(createAccountPageData.get(0))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(createAccountPageData.get(1))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(createAccountPageData.get(2))));
		foundation.objectFocus(CreateAccount.CHK_LABEL);
		foundation.click(CreateAccount.CHK_LABEL);
	
		foundation.click(createAccount.objText(createAccountPageData.get(1)));
		foundation.waitforElement(createAccount.objText(createAccountPageData.get(3)), 5);
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(createAccountPageData.get(4))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(createAccountPageData.get(5))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(createAccountPageData.get(6))));
		
		foundation.click(createAccount.objText(createAccountPageData.get(6)));
		foundation.objectFocus(CreateAccount.CHK_LABEL);
		foundation.click(CreateAccount.CHK_LABEL);
		foundation.click(createAccount.objText(createAccountPageData.get(1)));
		
		foundation.waitforElement(createAccount.objText(createAccountPageData.get(8)), 5);
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(createAccountPageData.get(3))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(createAccountPageData.get(7))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(createAccountPageData.get(8))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(createAccountPageData.get(9))));
				
		foundation.click(createAccount.objText(createAccountPageData.get(9)));
		foundation.waitforElement(createAccount.objText(createAccountPageData.get(4)), 5);
		foundation.click(createAccount.objText(createAccountPageData.get(5)));
		foundation.waitforElement(createAccount.objText(createAccountPageData.get(11)), 5);
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(createAccountPageData.get(3))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(createAccountPageData.get(10))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(createAccountPageData.get(11))));
		foundation.click(CreateAccount.BTN_CLOSE);
		foundation.waitforElement(LandingPage.BTN_CREATE_ACCOUNT,5);
		
		//Validating Account Login Page
		foundation.click(LandingPage.BTN_LOGIN);
		List<String> loginPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.LOGIN_PAGE).split(Constants.DELIMITER_TILD));
		Assert.assertEquals(foundation.getText(AccountLogin.LBL_PAGE_TITLE),loginPageData.get(0));
		Assert.assertEquals(foundation.getText(AccountLogin.LBL_PAGE_HEADER),loginPageData.get(1));
		Assert.assertEquals(foundation.getText(AccountLogin.LBL_SCAN),loginPageData.get(2));
		Assert.assertEquals(foundation.getText(AccountLogin.LBL_FINGER_PRINT),loginPageData.get(3));
		Assert.assertEquals(foundation.getText(AccountLogin.BTN_EMAIL_LOGIN),loginPageData.get(4));
		Assert.assertEquals(foundation.getText(AccountLogin.BTN_I_DONT_HAVE_ACCOUNT),loginPageData.get(5));
		
        //Validating Account Login Email Page
		foundation.click(AccountLogin.BTN_EMAIL_LOGIN);
		foundation.waitforElement(AccountLogin.BTN_NEXT, 5);
		Assert.assertEquals(foundation.getText(AccountLogin.LBL_PAGE_TITLE),loginPageData.get(0));
		Assert.assertEquals(foundation.getText(AccountLogin.LBL_EMAIL_HEADER),loginPageData.get(6));
		Assert.assertEquals(foundation.getText(AccountLogin.BTN_EMAIl_BACK),loginPageData.get(7));
		Assert.assertEquals(foundation.getText(AccountLogin.BTN_NEXT),loginPageData.get(8));
		  
		foundation.click(AccountLogin.BTN_CAMELCASE);
		textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.EMAIL_ID));
		foundation.click(AccountLogin.BTN_ENTER);
        foundation.click(AccountLogin.BTN_NEXT);
        
        //Validating Account Login PIN Page
        foundation.waitforElement(AccountLogin.BTN_PIN_NEXT, 5);
        Assert.assertEquals(foundation.getText(AccountLogin.LBL_PIN_PAGE_TITLE),loginPageData.get(0));
		Assert.assertEquals(foundation.getText(AccountLogin.LBL_PIN_HEADER),loginPageData.get(9));
		Assert.assertEquals(foundation.getText(AccountLogin.BTN_PIN_BACK),loginPageData.get(7));
		Assert.assertEquals(foundation.getText(AccountLogin.BTN_PIN_NEXT),loginPageData.get(10));
		
        textBox.enterPin(rstV5DeviceData.get(CNV5Device.PIN));
		foundation.click(AccountLogin.BTN_PIN_NEXT);
        
		//Verifying Account info page
		List<String> accountPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.ACCOUNT_DETAILS).split(Constants.DELIMITER_TILD));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountPageData.get(0))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountPageData.get(1))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountPageData.get(2))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountPageData.get(3))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountPageData.get(4))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountPageData.get(5))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountPageData.get(6))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountPageData.get(5))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountPageData.get(7))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountPageData.get(8))));
		
		//Verify Terms and Condition Page
		foundation.click(createAccount.objText(accountPageData.get(9)));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountPageData.get(9))));
		Assert.assertEquals(foundation.getText(AccountDetails.BTN_OK),accountPageData.get(10));
		foundation.click(AccountDetails.BTN_OK);
		
		//Verifying Fund with card page
		List<String> fundPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.FUND_ACCOUNT_PAGE).split(Constants.DELIMITER_TILD));
		foundation.click(createAccount.objText(accountPageData.get(1)));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(fundPageData.get(0))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(fundPageData.get(1))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(fundPageData.get(2))));
		foundation.click(createAccount.objText(fundPageData.get(3)));
		
		//Verifying Fund with card page details page
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(fundPageData.get(4))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(fundPageData.get(5))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(fundPageData.get(6))));
		
		foundation.click(createAccount.objText(fundPageData.get(6)));
		foundation.click(createAccount.objText(fundPageData.get(2)));
		foundation.click(createAccount.objText(accountPageData.get(4)));
		
		//Verifying Scan Setup page
		List<String> scanSetupPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.QUICK_SCAN_SETUP).split(Constants.DELIMITER_TILD));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(scanSetupPageData.get(0))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(scanSetupPageData.get(1))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(scanSetupPageData.get(2))));
		
		foundation.click(createAccount.objText(scanSetupPageData.get(2)));
		
		//Verifying Finger print Setup page
		List<String> fingerPrintPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.FINGER_PRINT_SETUP).split(Constants.DELIMITER_TILD));
		foundation.click(createAccount.objText(accountPageData.get(6)));

		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(fingerPrintPageData.get(0))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(fingerPrintPageData.get(1))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(fingerPrintPageData.get(2))));
		
		foundation.click(createAccount.objText(fingerPrintPageData.get(2)));
		
		//Verifying Edit account page
		foundation.click(createAccount.objText(accountPageData.get(7)));
		List<String> accountEditPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.EDIT_ACCOUNT_DETAILS).split(Constants.DELIMITER_TILD));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountEditPageData.get(0))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountEditPageData.get(1))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountEditPageData.get(2))));		
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountEditPageData.get(3))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountEditPageData.get(4))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountEditPageData.get(5))));	
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountEditPageData.get(6))));
		
		//verify Change pin
		foundation.click(createAccount.objText(accountEditPageData.get(3)));
		List<String> changePinPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.CHANGE_PIN).split(Constants.DELIMITER_TILD));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(changePinPageData.get(0))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(changePinPageData.get(1))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(changePinPageData.get(2))));
		
		foundation.click(createAccount.objText(changePinPageData.get(2)));
		
		foundation.click(createAccount.objText(accountEditPageData.get(6)));
		//Verifying timeout popup
		List<String> timeOutPopupData = Arrays.asList(rstV5DeviceData.get(CNV5Device.TIME_OUT_POPUP).split(Constants.DELIMITER_TILD));
		foundation.waitforElement(createAccount.objText(timeOutPopupData.get(0)), 30);
		Assert.assertEquals(foundation.getText(Order.POP_UP_TIMEOUT_YES),timeOutPopupData.get(3));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(timeOutPopupData.get(0))));
		foundation.click(Order.POP_UP_TIMEOUT_YES);
		foundation.waitforElement(createAccount.objText(timeOutPopupData.get(0)), 30);
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(timeOutPopupData.get(1))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(timeOutPopupData.get(2))));
	    browser.close();
	    
		
	    browser.launch(Constants.LOCAL,Constants.CHROME);
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
        foundation.waitforElement(LocationList.TXT_FILTER, 5);
        login.logout();
//                    
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
        foundation.waitforElement(LocationList.TXT_FILTER, 5);
        login.logout();
        browser.close();
        
        foundation.threadWait(5000);
        //login into Kiosk Device
        browser.launch(Constants.REMOTE,Constants.CHROME);
		browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
		
		//Validating Landing Page
		foundation.waitforElement(landingPage.objLanguage(requiredData.get(3)), 5);
		List<String> landingPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.LANDING_PAGE).split(Constants.DELIMITER_TILD));
	
		Assert.assertEquals(foundation.getText(LandingPage.LBL_ACCOUNT_LOGIN),landingPageData.get(0));
		Assert.assertEquals(foundation.getText(LandingPage.LBL_CREATE_ACCOUNT),landingPageData.get(1));
		Assert.assertTrue(foundation.isDisplayed(landingPage.objLanguage(landingPageData.get(2))));
		Assert.assertTrue(foundation.isDisplayed(landingPage.objLanguage(landingPageData.get(3))));
		Assert.assertEquals(foundation.getText(LandingPage.LBL_HEADER),landingPageData.get(4));
		Assert.assertEquals(foundation.getText(LandingPage.LBL_SEARCH),landingPageData.get(5));
		Assert.assertEquals(foundation.getText(LandingPage.LBL_SCAN),landingPageData.get(6));

		//Validating Search Page
		foundation.click(LandingPage.IMG_SEARCH_ICON);
		List<String> productSearchPage = Arrays.asList(rstV5DeviceData.get(CNV5Device.PRODUCT_SEARCH_PAGE).split(Constants.DELIMITER_TILD));
	
		Assert.assertEquals(foundation.getText(ProductSearch.LBL_PRODUCT_SEARCH_TITLE),productSearchPage.get(0));
		Assert.assertEquals(foundation.getText(ProductSearch.LBL_PRODUCT_SEARCH_HEADER),productSearchPage.get(1));
		Assert.assertEquals(foundation.getText(ProductSearch.LBL_PRODUCT_FOUND),productSearchPage.get(2));

		foundation.click(AccountLogin.BTN_CAMELCASE);
		textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
        foundation.click(ProductSearch.BTN_PRODUCT);
        
        //verify Order Page
        List<String> orderPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.ORDER_PAGE).split(Constants.DELIMITER_TILD));
        Assert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(0))));
        Assert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(1))));
        Assert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(2))));
        Assert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(3))));
        Assert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(4))));
        Assert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(5))));
        Assert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(6))));
        foundation.objectFocus(order.objText(orderPageData.get(7)));
        Assert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(7))));
        Assert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(8))));
        
        
        //Validating Credit/Debit Page
        foundation.click(order.objText(orderPageData.get(8)));
        List<String> crditDebitPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.CREDIT_DEBIT_PAGE).split(Constants.DELIMITER_TILD));
        foundation.waitforElement(cardPayment.objText(crditDebitPageData.get(0)), 5);        
        Assert.assertTrue(foundation.isDisplayed(cardPayment.objText(crditDebitPageData.get(0))));
        Assert.assertTrue(foundation.isDisplayed(cardPayment.objText(crditDebitPageData.get(1))));
        
        foundation.click(CardPayment.BTN_CLOSE);
        foundation.waitforElement(order.objText(orderPageData.get(0)), 5);
        
        //verify Cancel Order Page
  		foundation.click(order.objText(orderPageData.get(0)));
        Assert.assertTrue(foundation.isDisplayed(createAccount.objText(rstV5DeviceData.get(CNV5Device.TRANSACTION_CANCEL))));
		
        
		//Validating Create Account Page
        foundation.waitforElement(LandingPage.BTN_CREATE_ACCOUNT, 5);
		foundation.click(LandingPage.BTN_CREATE_ACCOUNT);
		List<String> createAccountPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.CREATE_ACCOUNT).split(Constants.DELIMITER_TILD));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(createAccountPageData.get(0))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(createAccountPageData.get(1))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(createAccountPageData.get(2))));
		foundation.objectFocus(CreateAccount.CHK_LABEL);
		foundation.click(CreateAccount.CHK_LABEL);
	
		foundation.click(createAccount.objText(createAccountPageData.get(1)));
		foundation.waitforElement(createAccount.objText(createAccountPageData.get(3)), 5);
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(createAccountPageData.get(4))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(createAccountPageData.get(5))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(createAccountPageData.get(6))));
		
		foundation.click(createAccount.objText(createAccountPageData.get(6)));
		foundation.objectFocus(CreateAccount.CHK_LABEL);
		foundation.click(CreateAccount.CHK_LABEL);
		foundation.click(createAccount.objText(createAccountPageData.get(1)));
		
		foundation.waitforElement(createAccount.objText(createAccountPageData.get(7)), 5);
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(createAccountPageData.get(3))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(createAccountPageData.get(7))));
		String fingerPrintHeader=foundation.getText(CreateAccount.LBL_FINGERPRINT_HEADER);
		Assert.assertTrue(fingerPrintHeader.equals(createAccountPageData.get(8)));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(createAccountPageData.get(9))));
				
		foundation.click(createAccount.objText(createAccountPageData.get(9)));
		foundation.waitforElement(createAccount.objText(createAccountPageData.get(4)), 5);
		foundation.click(createAccount.objText(createAccountPageData.get(5)));
		foundation.waitforElement(createAccount.objText(createAccountPageData.get(10)), 5);
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(createAccountPageData.get(3))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(createAccountPageData.get(10))));
		String scanHeader=foundation.getText(CreateAccount.LBL_SCAN_HEADER);
		Assert.assertTrue(scanHeader.equals(createAccountPageData.get(11)));
		foundation.click(CreateAccount.BTN_CLOSE);
		foundation.waitforElement(LandingPage.BTN_CREATE_ACCOUNT,5);
		
		//Validating Account Login Page
		foundation.click(LandingPage.BTN_LOGIN);
		List<String> loginPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.LOGIN_PAGE).split(Constants.DELIMITER_TILD));
		Assert.assertEquals(foundation.getText(AccountLogin.LBL_PAGE_TITLE),loginPageData.get(0));
		Assert.assertEquals(foundation.getText(AccountLogin.LBL_PAGE_HEADER),loginPageData.get(1));
		Assert.assertEquals(foundation.getText(AccountLogin.LBL_SCAN),loginPageData.get(2));
		Assert.assertEquals(foundation.getText(AccountLogin.LBL_FINGER_PRINT),loginPageData.get(3));
		Assert.assertEquals(foundation.getText(AccountLogin.BTN_EMAIL_LOGIN),loginPageData.get(4));
		Assert.assertEquals(foundation.getText(AccountLogin.BTN_I_DONT_HAVE_ACCOUNT),loginPageData.get(5));
		
        //Validating Account Login Email Page
		foundation.click(AccountLogin.BTN_EMAIL_LOGIN);
		foundation.waitforElement(AccountLogin.BTN_NEXT, 5);
		Assert.assertEquals(foundation.getText(AccountLogin.LBL_PAGE_TITLE),loginPageData.get(0));
		Assert.assertEquals(foundation.getText(AccountLogin.LBL_EMAIL_HEADER),loginPageData.get(6));
		Assert.assertEquals(foundation.getText(AccountLogin.BTN_EMAIl_BACK),loginPageData.get(7));
		Assert.assertEquals(foundation.getText(AccountLogin.BTN_NEXT),loginPageData.get(8));
		  
		foundation.click(AccountLogin.BTN_CAMELCASE);
		textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.EMAIL_ID));
		foundation.click(AccountLogin.BTN_ENTER);
        foundation.click(AccountLogin.BTN_NEXT);
        
        //Validating Account Login PIN Page
        foundation.waitforElement(AccountLogin.BTN_PIN_NEXT, 5);
        Assert.assertEquals(foundation.getText(AccountLogin.LBL_PIN_PAGE_TITLE),loginPageData.get(0));
		Assert.assertEquals(foundation.getText(AccountLogin.LBL_PIN_HEADER),loginPageData.get(9));
		Assert.assertEquals(foundation.getText(AccountLogin.BTN_PIN_BACK),loginPageData.get(7));
		Assert.assertEquals(foundation.getText(AccountLogin.BTN_PIN_NEXT),loginPageData.get(10));
		
        textBox.enterPin(rstV5DeviceData.get(CNV5Device.PIN));
		foundation.click(AccountLogin.BTN_PIN_NEXT);
        
		//Verifying Account info page
		List<String> accountPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.ACCOUNT_DETAILS).split(Constants.DELIMITER_TILD));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountPageData.get(0))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountPageData.get(1))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountPageData.get(2))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountPageData.get(3))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountPageData.get(4))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountPageData.get(5))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountPageData.get(6))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountPageData.get(5))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountPageData.get(7))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountPageData.get(8))));
		
		//Verify Terms and Condition Page
		foundation.click(createAccount.objText(accountPageData.get(9)));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountPageData.get(9))));
		Assert.assertEquals(foundation.getText(AccountDetails.BTN_OK),accountPageData.get(10));
		foundation.click(AccountDetails.BTN_OK);
		
		//Verifying Fund with card page
		List<String> fundPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.FUND_ACCOUNT_PAGE).split(Constants.DELIMITER_TILD));
		foundation.click(createAccount.objText(accountPageData.get(1)));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(fundPageData.get(0))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(fundPageData.get(1))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(fundPageData.get(2))));
		foundation.click(createAccount.objText(fundPageData.get(3)));
		
		//Verifying Fund with card page details page
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(fundPageData.get(4))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(fundPageData.get(5))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(fundPageData.get(6))));
		
		foundation.click(createAccount.objText(fundPageData.get(6)));
		foundation.click(createAccount.objText(fundPageData.get(2)));
		foundation.click(createAccount.objText(accountPageData.get(4)));
		
		//Verifying Scan Setup page
		List<String> scanSetupPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.QUICK_SCAN_SETUP).split(Constants.DELIMITER_TILD));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(scanSetupPageData.get(0))));
		String frScanHeader=foundation.getText(CreateAccount.LBL_FR_SCAN_HEADER);
		Assert.assertTrue(frScanHeader.equals(scanSetupPageData.get(1)));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(scanSetupPageData.get(2))));
		
		foundation.click(createAccount.objText(scanSetupPageData.get(2)));
		
		//Verifying Finger print Setup page
		List<String> fingerPrintPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.FINGER_PRINT_SETUP).split(Constants.DELIMITER_TILD));
		foundation.click(createAccount.objText(accountPageData.get(6)));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(fingerPrintPageData.get(0))));
		String frFingerPrintHeader=foundation.getText(CreateAccount.LBL_FR_FINGERPRINT_HEADER);
		Assert.assertTrue(frFingerPrintHeader.equals(fingerPrintPageData.get(1)));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(fingerPrintPageData.get(2))));
		
		foundation.click(createAccount.objText(fingerPrintPageData.get(2)));
		
		//Verifying Edit account page
		foundation.click(createAccount.objText(accountPageData.get(7)));
		List<String> accountEditPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.EDIT_ACCOUNT_DETAILS).split(Constants.DELIMITER_TILD));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountEditPageData.get(0))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountEditPageData.get(1))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountEditPageData.get(2))));		
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountEditPageData.get(3))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountEditPageData.get(4))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountEditPageData.get(5))));	
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountEditPageData.get(6))));
		
		//verify Change pin
		foundation.click(createAccount.objText(accountEditPageData.get(3)));
		List<String> changePinPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.CHANGE_PIN).split(Constants.DELIMITER_TILD));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(changePinPageData.get(0))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(changePinPageData.get(1))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(changePinPageData.get(2))));
		
		foundation.click(createAccount.objText(changePinPageData.get(2)));
		
		foundation.click(createAccount.objText(accountEditPageData.get(6)));
		//Verifying timeout popup

		List<String> timeOutPopupData = Arrays.asList(rstV5DeviceData.get(CNV5Device.TIME_OUT_POPUP).split(Constants.DELIMITER_TILD));
		foundation.waitforElement(createAccount.objText(timeOutPopupData.get(0)), 30);
		Assert.assertEquals(foundation.getText(Order.POP_UP_TIMEOUT_YES),timeOutPopupData.get(3));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(timeOutPopupData.get(0))));
		foundation.click(Order.POP_UP_TIMEOUT_YES);
		foundation.waitforElement(createAccount.objText(timeOutPopupData.get(0)), 30);
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(timeOutPopupData.get(1))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(timeOutPopupData.get(2))));
	    browser.close();
	    
		
	    browser.launch(Constants.LOCAL,Constants.CHROME);
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
        foundation.waitforElement(LocationList.TXT_FILTER, 5);
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
        foundation.waitforElement(LocationList.TXT_FILTER, 5);
        login.logout();
        browser.close();
        
        foundation.threadWait(5000);
        //login into Kiosk Device
        browser.launch(Constants.REMOTE,Constants.CHROME);
		browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
		
		//Validating Landing Page
		foundation.waitforElement(landingPage.objLanguage(requiredData.get(3)), 5);
		List<String> landingPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.LANDING_PAGE).split(Constants.DELIMITER_TILD));
	
		Assert.assertEquals(foundation.getText(LandingPage.LBL_ACCOUNT_LOGIN),landingPageData.get(0));
		Assert.assertEquals(foundation.getText(LandingPage.LBL_CREATE_ACCOUNT),landingPageData.get(1));
		Assert.assertTrue(foundation.isDisplayed(landingPage.objLanguage(landingPageData.get(2))));
		Assert.assertTrue(foundation.isDisplayed(landingPage.objLanguage(landingPageData.get(3))));
		Assert.assertEquals(foundation.getText(LandingPage.LBL_HEADER),landingPageData.get(4));
		Assert.assertEquals(foundation.getText(LandingPage.LBL_SEARCH),landingPageData.get(5));
		Assert.assertEquals(foundation.getText(LandingPage.LBL_SCAN),landingPageData.get(6));

		//Validating Search Page
		foundation.click(LandingPage.IMG_SEARCH_ICON);
		List<String> productSearchPage = Arrays.asList(rstV5DeviceData.get(CNV5Device.PRODUCT_SEARCH_PAGE).split(Constants.DELIMITER_TILD));
	
		Assert.assertEquals(foundation.getText(ProductSearch.LBL_PRODUCT_SEARCH_TITLE),productSearchPage.get(0));
		Assert.assertEquals(foundation.getText(ProductSearch.LBL_PRODUCT_SEARCH_HEADER),productSearchPage.get(1));
		Assert.assertEquals(foundation.getText(ProductSearch.LBL_PRODUCT_FOUND),productSearchPage.get(2));

		foundation.click(AccountLogin.BTN_CAMELCASE);
		textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
        foundation.click(ProductSearch.BTN_PRODUCT);
        
        //verify Order Page
        List<String> orderPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.ORDER_PAGE).split(Constants.DELIMITER_TILD));
        Assert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(0))));
        Assert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(1))));
        Assert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(2))));
        Assert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(3))));
        Assert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(4))));
        Assert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(5))));
        Assert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(6))));
        foundation.objectFocus(order.objText(orderPageData.get(7)));
        Assert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(7))));
        Assert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(8))));
        
        
        //Validating Credit/Debit Page
        foundation.click(order.objText(orderPageData.get(8)));
        List<String> crditDebitPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.CREDIT_DEBIT_PAGE).split(Constants.DELIMITER_TILD));
        foundation.waitforElement(cardPayment.objText(crditDebitPageData.get(0)), 5);        
        Assert.assertTrue(foundation.isDisplayed(cardPayment.objText(crditDebitPageData.get(0))));
        Assert.assertTrue(foundation.isDisplayed(cardPayment.objText(crditDebitPageData.get(1))));
        
        foundation.click(CardPayment.BTN_CLOSE);
        foundation.waitforElement(order.objText(orderPageData.get(0)), 5);
        
        //verify Cancel Order Page
  		foundation.click(order.objText(orderPageData.get(0)));
        Assert.assertTrue(foundation.isDisplayed(createAccount.objText(rstV5DeviceData.get(CNV5Device.TRANSACTION_CANCEL))));
		
        
		//Validating Create Account Page
        foundation.waitforElement(LandingPage.BTN_CREATE_ACCOUNT, 5);
		foundation.click(LandingPage.BTN_CREATE_ACCOUNT);
		List<String> createAccountPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.CREATE_ACCOUNT).split(Constants.DELIMITER_TILD));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(createAccountPageData.get(0))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(createAccountPageData.get(1))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(createAccountPageData.get(2))));
		foundation.objectFocus(CreateAccount.CHK_LABEL);
		foundation.click(CreateAccount.CHK_LABEL);
	
		foundation.click(createAccount.objText(createAccountPageData.get(1)));
		foundation.waitforElement(createAccount.objText(createAccountPageData.get(3)), 5);
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(createAccountPageData.get(4))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(createAccountPageData.get(5))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(createAccountPageData.get(6))));
		
		foundation.click(createAccount.objText(createAccountPageData.get(6)));
		foundation.objectFocus(CreateAccount.CHK_LABEL);
		foundation.click(CreateAccount.CHK_LABEL);
		foundation.click(createAccount.objText(createAccountPageData.get(1)));
		
		foundation.waitforElement(createAccount.objText(createAccountPageData.get(7)), 5);
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(createAccountPageData.get(3))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(createAccountPageData.get(7))));
		String fingerPrintHeader=foundation.getText(CreateAccount.LBL_FINGERPRINT_HEADER);
		Assert.assertTrue(fingerPrintHeader.equals(createAccountPageData.get(8)));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(createAccountPageData.get(9))));
				
		foundation.click(createAccount.objText(createAccountPageData.get(9)));
		foundation.waitforElement(createAccount.objText(createAccountPageData.get(4)), 5);
		foundation.click(createAccount.objText(createAccountPageData.get(5)));
		foundation.waitforElement(createAccount.objText(createAccountPageData.get(10)), 5);
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(createAccountPageData.get(3))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(createAccountPageData.get(10))));
		String scanHeader=foundation.getText(CreateAccount.LBL_SCAN_HEADER);
		Assert.assertTrue(scanHeader.equals(createAccountPageData.get(11)));
		foundation.click(CreateAccount.BTN_CLOSE);
		foundation.waitforElement(LandingPage.BTN_CREATE_ACCOUNT,5);
//		
		//Validating Account Login Page
		foundation.click(LandingPage.BTN_LOGIN);
		List<String> loginPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.LOGIN_PAGE).split(Constants.DELIMITER_TILD));
		Assert.assertEquals(foundation.getText(AccountLogin.LBL_PAGE_TITLE),loginPageData.get(0));
		Assert.assertEquals(foundation.getText(AccountLogin.LBL_PAGE_HEADER),loginPageData.get(1));
		Assert.assertEquals(foundation.getText(AccountLogin.LBL_SCAN),loginPageData.get(2));
		Assert.assertEquals(foundation.getText(AccountLogin.LBL_FINGER_PRINT),loginPageData.get(3));
		Assert.assertEquals(foundation.getText(AccountLogin.BTN_EMAIL_LOGIN),loginPageData.get(4));
		Assert.assertEquals(foundation.getText(AccountLogin.BTN_I_DONT_HAVE_ACCOUNT),loginPageData.get(5));
		
        //Validating Account Login Email Page
		foundation.click(AccountLogin.BTN_EMAIL_LOGIN);
		foundation.waitforElement(AccountLogin.BTN_NEXT, 5);
		Assert.assertEquals(foundation.getText(AccountLogin.LBL_PAGE_TITLE),loginPageData.get(0));
		Assert.assertEquals(foundation.getText(AccountLogin.LBL_EMAIL_HEADER),loginPageData.get(6));
		Assert.assertEquals(foundation.getText(AccountLogin.BTN_EMAIl_BACK),loginPageData.get(7));
		Assert.assertEquals(foundation.getText(AccountLogin.BTN_NEXT),loginPageData.get(8));
		  
		foundation.click(AccountLogin.BTN_CAMELCASE);
		textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.EMAIL_ID));
		foundation.click(AccountLogin.BTN_ENTER);
        foundation.click(AccountLogin.BTN_NEXT);
        
        //Validating Account Login PIN Page
        foundation.waitforElement(AccountLogin.BTN_PIN_NEXT, 5);
        Assert.assertEquals(foundation.getText(AccountLogin.LBL_PIN_PAGE_TITLE),loginPageData.get(0));
		Assert.assertEquals(foundation.getText(AccountLogin.LBL_PIN_HEADER),loginPageData.get(9));
		Assert.assertEquals(foundation.getText(AccountLogin.BTN_PIN_BACK),loginPageData.get(7));
		Assert.assertEquals(foundation.getText(AccountLogin.BTN_PIN_NEXT),loginPageData.get(10));
		
        textBox.enterPin(rstV5DeviceData.get(CNV5Device.PIN));
		foundation.click(AccountLogin.BTN_PIN_NEXT);
        
		//Verifying Account info page
		List<String> accountPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.ACCOUNT_DETAILS).split(Constants.DELIMITER_TILD));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountPageData.get(0))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountPageData.get(1))));
		String tegoed=foundation.getText(CreateAccount.LBL_DUTCH_HEADER);
		Assert.assertTrue(tegoed.equals(accountPageData.get(2)));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountPageData.get(3))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountPageData.get(4))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountPageData.get(5))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountPageData.get(6))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountPageData.get(5))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountPageData.get(7))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountPageData.get(8))));
		
		//Verify Terms and Condition Page (Could not validate as there is no Ok button in terms and condition screen
		foundation.click(createAccount.objText(accountPageData.get(9)));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountPageData.get(9))));
		Assert.assertEquals(foundation.getText(AccountDetails.BTN_OK),accountPageData.get(10));
		foundation.click(AccountDetails.BTN_OK);
		
		//Verifying Fund with card page
		List<String> fundPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.FUND_ACCOUNT_PAGE).split(Constants.DELIMITER_TILD));
		foundation.click(createAccount.objText(accountPageData.get(1)));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(fundPageData.get(0))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(fundPageData.get(1))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(fundPageData.get(2))));
		foundation.click(createAccount.objText(fundPageData.get(3)));
		
		//Verifying Fund with card page details page
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(fundPageData.get(4))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(fundPageData.get(5))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(fundPageData.get(6))));
		
		foundation.click(createAccount.objText(fundPageData.get(6)));
		foundation.click(createAccount.objText(fundPageData.get(2)));
		foundation.click(createAccount.objText(accountPageData.get(4)));
		
		//Verifying Scan Setup page
		List<String> scanSetupPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.QUICK_SCAN_SETUP).split(Constants.DELIMITER_TILD));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(scanSetupPageData.get(0))));
		String frScanHeader=foundation.getText(CreateAccount.LBL_FR_SCAN_HEADER);
		Assert.assertTrue(frScanHeader.equals(scanSetupPageData.get(1)));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(scanSetupPageData.get(2))));
		
		foundation.click(createAccount.objText(scanSetupPageData.get(2)));
		
		//Verifying Finger print Setup page
		List<String> fingerPrintPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.FINGER_PRINT_SETUP).split(Constants.DELIMITER_TILD));
		foundation.click(createAccount.objText(accountPageData.get(6)));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(fingerPrintPageData.get(0))));
		String frFingerPrintHeader=foundation.getText(CreateAccount.LBL_FR_FINGERPRINT_HEADER);
		Assert.assertTrue(frFingerPrintHeader.equals(fingerPrintPageData.get(1)));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(fingerPrintPageData.get(2))));
		
		foundation.click(createAccount.objText(fingerPrintPageData.get(2)));
		
		//Verifying Edit account page
		foundation.click(createAccount.objText(accountPageData.get(7)));
		List<String> accountEditPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.EDIT_ACCOUNT_DETAILS).split(Constants.DELIMITER_TILD));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountEditPageData.get(0))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountEditPageData.get(1))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountEditPageData.get(2))));		
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountEditPageData.get(3))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountEditPageData.get(4))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountEditPageData.get(5))));	
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountEditPageData.get(6))));
		
		//verify Change pin
		foundation.click(createAccount.objText(accountEditPageData.get(3)));
		List<String> changePinPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.CHANGE_PIN).split(Constants.DELIMITER_TILD));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(changePinPageData.get(0))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(changePinPageData.get(1))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(changePinPageData.get(2))));
		
		foundation.click(createAccount.objText(changePinPageData.get(2)));
		
		foundation.click(createAccount.objText(accountEditPageData.get(6)));
		//Verifying timeout popup

		List<String> timeOutPopupData = Arrays.asList(rstV5DeviceData.get(CNV5Device.TIME_OUT_POPUP).split(Constants.DELIMITER_TILD));
		foundation.waitforElement(createAccount.objText(timeOutPopupData.get(0)), 30);
		Assert.assertEquals(foundation.getText(Order.POP_UP_TIMEOUT_YES),timeOutPopupData.get(3));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(timeOutPopupData.get(0))));
		foundation.click(Order.POP_UP_TIMEOUT_YES);
		foundation.waitforElement(createAccount.objText(timeOutPopupData.get(0)), 30);
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(timeOutPopupData.get(1))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(timeOutPopupData.get(2))));
	    browser.close();
	    
		
	    browser.launch(Constants.LOCAL,Constants.CHROME);
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
        foundation.waitforElement(LocationList.TXT_FILTER, 5);
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
        foundation.waitforElement(LocationList.TXT_FILTER, 5);
        login.logout();
        browser.close();
        
        foundation.threadWait(5000);
        //login into Kiosk Device
        browser.launch(Constants.REMOTE,Constants.CHROME);
		browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
		
		//Validating Landing Page
		foundation.waitforElement(landingPage.objLanguage(requiredData.get(3)), 5);
		List<String> landingPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.LANDING_PAGE).split(Constants.DELIMITER_TILD));
	
		Assert.assertEquals(foundation.getText(LandingPage.LBL_ACCOUNT_LOGIN),landingPageData.get(0));
		Assert.assertEquals(foundation.getText(LandingPage.LBL_CREATE_ACCOUNT),landingPageData.get(1));
		Assert.assertTrue(foundation.isDisplayed(landingPage.objLanguage(landingPageData.get(2))));
		Assert.assertTrue(foundation.isDisplayed(landingPage.objLanguage(landingPageData.get(3))));
		Assert.assertEquals(foundation.getText(LandingPage.LBL_HEADER),landingPageData.get(4));
		Assert.assertEquals(foundation.getText(LandingPage.LBL_SEARCH),landingPageData.get(5));
		Assert.assertEquals(foundation.getText(LandingPage.LBL_SCAN),landingPageData.get(6));

		//Validating Search Page
		foundation.click(LandingPage.IMG_SEARCH_ICON);
		List<String> productSearchPage = Arrays.asList(rstV5DeviceData.get(CNV5Device.PRODUCT_SEARCH_PAGE).split(Constants.DELIMITER_TILD));
	
		Assert.assertEquals(foundation.getText(ProductSearch.LBL_PRODUCT_SEARCH_TITLE),productSearchPage.get(0));
		Assert.assertEquals(foundation.getText(ProductSearch.LBL_PRODUCT_SEARCH_HEADER),productSearchPage.get(1));
		Assert.assertEquals(foundation.getText(ProductSearch.LBL_PRODUCT_FOUND),productSearchPage.get(2));

		foundation.click(AccountLogin.BTN_CAMELCASE);
		textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
        foundation.click(ProductSearch.BTN_PRODUCT);
        
        //verify Order Page
        List<String> orderPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.ORDER_PAGE).split(Constants.DELIMITER_TILD));
        Assert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(0))));
        Assert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(1))));
        Assert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(2))));
        Assert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(3))));
        Assert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(4))));
        Assert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(5))));
        Assert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(6))));
        foundation.objectFocus(order.objText(orderPageData.get(7)));
        Assert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(7))));
        Assert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(8))));
        
        
        //Validating Credit/Debit Page
        foundation.click(order.objText(orderPageData.get(8)));
        List<String> crditDebitPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.CREDIT_DEBIT_PAGE).split(Constants.DELIMITER_TILD));
        foundation.waitforElement(cardPayment.objText(crditDebitPageData.get(0)), 5);        
        Assert.assertTrue(foundation.isDisplayed(cardPayment.objText(crditDebitPageData.get(0))));
        Assert.assertTrue(foundation.isDisplayed(cardPayment.objText(crditDebitPageData.get(1))));
        
        foundation.click(CardPayment.BTN_CLOSE);
        foundation.waitforElement(order.objText(orderPageData.get(0)), 5);
        
        //verify Cancel Order Page
  		foundation.click(order.objText(orderPageData.get(0)));
        Assert.assertTrue(foundation.isDisplayed(createAccount.objText(rstV5DeviceData.get(CNV5Device.TRANSACTION_CANCEL))));
		
        
		//Validating Create Account Page
        foundation.waitforElement(LandingPage.BTN_CREATE_ACCOUNT, 5);
		foundation.click(LandingPage.BTN_CREATE_ACCOUNT);
		List<String> createAccountPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.CREATE_ACCOUNT).split(Constants.DELIMITER_TILD));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(createAccountPageData.get(0))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(createAccountPageData.get(1))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(createAccountPageData.get(2))));
		foundation.objectFocus(CreateAccount.CHK_LABEL);
		foundation.click(CreateAccount.CHK_LABEL);
	
		foundation.click(createAccount.objText(createAccountPageData.get(1)));
		foundation.waitforElement(createAccount.objText(createAccountPageData.get(3)), 5);
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(createAccountPageData.get(4))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(createAccountPageData.get(5))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(createAccountPageData.get(6))));
		
		foundation.click(createAccount.objText(createAccountPageData.get(6)));
		foundation.objectFocus(CreateAccount.CHK_LABEL);
		foundation.click(CreateAccount.CHK_LABEL);
		foundation.click(createAccount.objText(createAccountPageData.get(1)));
		
		foundation.waitforElement(createAccount.objText(createAccountPageData.get(7)), 5);
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(createAccountPageData.get(3))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(createAccountPageData.get(7))));
		String fingerPrintHeader=foundation.getText(CreateAccount.LBL_FINGERPRINT_HEADER);
		Assert.assertTrue(fingerPrintHeader.equals(createAccountPageData.get(8)));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(createAccountPageData.get(9))));
				
		foundation.click(createAccount.objText(createAccountPageData.get(9)));
		foundation.waitforElement(createAccount.objText(createAccountPageData.get(4)), 5);
		foundation.click(createAccount.objText(createAccountPageData.get(5)));
		foundation.waitforElement(createAccount.objText(createAccountPageData.get(10)), 5);
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(createAccountPageData.get(3))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(createAccountPageData.get(10))));
		String scanHeader=foundation.getText(CreateAccount.LBL_SCAN_HEADER);
		Assert.assertTrue(scanHeader.equals(createAccountPageData.get(11)));
		foundation.click(CreateAccount.BTN_CLOSE);
		foundation.waitforElement(LandingPage.BTN_CREATE_ACCOUNT,5);
		
		//Validating Account Login Page
		foundation.click(LandingPage.BTN_LOGIN);
		List<String> loginPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.LOGIN_PAGE).split(Constants.DELIMITER_TILD));
		Assert.assertEquals(foundation.getText(AccountLogin.LBL_PAGE_TITLE),loginPageData.get(0));
		Assert.assertEquals(foundation.getText(AccountLogin.LBL_PAGE_HEADER),loginPageData.get(1));
		Assert.assertEquals(foundation.getText(AccountLogin.LBL_SCAN),loginPageData.get(2));
		Assert.assertEquals(foundation.getText(AccountLogin.LBL_FINGER_PRINT),loginPageData.get(3));
		Assert.assertEquals(foundation.getText(AccountLogin.BTN_EMAIL_LOGIN),loginPageData.get(4));
		Assert.assertEquals(foundation.getText(AccountLogin.BTN_I_DONT_HAVE_ACCOUNT),loginPageData.get(5));
		
        //Validating Account Login Email Page
		foundation.click(AccountLogin.BTN_EMAIL_LOGIN);
		foundation.waitforElement(AccountLogin.BTN_NEXT, 5);
		Assert.assertEquals(foundation.getText(AccountLogin.LBL_PAGE_TITLE),loginPageData.get(0));
		Assert.assertEquals(foundation.getText(AccountLogin.LBL_EMAIL_HEADER),loginPageData.get(6));
		Assert.assertEquals(foundation.getText(AccountLogin.BTN_EMAIl_BACK),loginPageData.get(7));
		Assert.assertEquals(foundation.getText(AccountLogin.BTN_NEXT),loginPageData.get(8));
		  
		foundation.click(AccountLogin.BTN_CAMELCASE);
		textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.EMAIL_ID));
		foundation.click(AccountLogin.BTN_ENTER);
        foundation.click(AccountLogin.BTN_NEXT);
        
        //Validating Account Login PIN Page
        foundation.waitforElement(AccountLogin.BTN_PIN_NEXT, 5);
        Assert.assertEquals(foundation.getText(AccountLogin.LBL_PIN_PAGE_TITLE),loginPageData.get(0));
		Assert.assertEquals(foundation.getText(AccountLogin.LBL_PIN_HEADER),loginPageData.get(9));
		Assert.assertEquals(foundation.getText(AccountLogin.BTN_PIN_BACK),loginPageData.get(7));
		Assert.assertEquals(foundation.getText(AccountLogin.BTN_PIN_NEXT),loginPageData.get(10));
		
        textBox.enterPin(rstV5DeviceData.get(CNV5Device.PIN));
		foundation.click(AccountLogin.BTN_PIN_NEXT);
        
		//Verifying Account info page
		List<String> accountPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.ACCOUNT_DETAILS).split(Constants.DELIMITER_TILD));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountPageData.get(0))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountPageData.get(1))));
		String tegoed=foundation.getText(CreateAccount.LBL_DUTCH_HEADER);
		Assert.assertTrue(tegoed.equals(accountPageData.get(2)));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountPageData.get(3))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountPageData.get(4))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountPageData.get(5))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountPageData.get(6))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountPageData.get(5))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountPageData.get(7))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountPageData.get(8))));
		
		//Verify Terms and Condition Page (Could not validate as there is no Ok button in terms and condition screen
		foundation.click(createAccount.objText(accountPageData.get(9)));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountPageData.get(9))));
		Assert.assertEquals(foundation.getText(AccountDetails.BTN_OK),accountPageData.get(10));
		foundation.click(AccountDetails.BTN_OK);
		
		//Verifying Fund with card page
		List<String> fundPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.FUND_ACCOUNT_PAGE).split(Constants.DELIMITER_TILD));
		foundation.click(createAccount.objText(accountPageData.get(1)));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(fundPageData.get(0))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(fundPageData.get(1))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(fundPageData.get(2))));
		foundation.click(createAccount.objText(fundPageData.get(3)));
		
		//Verifying Fund with card page details page
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(fundPageData.get(4))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(fundPageData.get(5))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(fundPageData.get(6))));
		
		foundation.click(createAccount.objText(fundPageData.get(6)));
		foundation.click(createAccount.objText(fundPageData.get(2)));
		foundation.click(createAccount.objText(accountPageData.get(4)));
		
		//Verifying Scan Setup page
		List<String> scanSetupPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.QUICK_SCAN_SETUP).split(Constants.DELIMITER_TILD));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(scanSetupPageData.get(0))));
		String frScanHeader=foundation.getText(CreateAccount.LBL_FR_SCAN_HEADER);
		Assert.assertTrue(frScanHeader.equals(scanSetupPageData.get(1)));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(scanSetupPageData.get(2))));
		
		foundation.click(createAccount.objText(scanSetupPageData.get(2)));
		
		//Verifying Finger print Setup page
		List<String> fingerPrintPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.FINGER_PRINT_SETUP).split(Constants.DELIMITER_TILD));
		foundation.click(createAccount.objText(accountPageData.get(6)));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(fingerPrintPageData.get(0))));
		String frFingerPrintHeader=foundation.getText(CreateAccount.LBL_FR_FINGERPRINT_HEADER);
		Assert.assertTrue(frFingerPrintHeader.equals(fingerPrintPageData.get(1)));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(fingerPrintPageData.get(2))));
		
		foundation.click(createAccount.objText(fingerPrintPageData.get(2)));
		
		//Verifying Edit account page
		foundation.click(createAccount.objText(accountPageData.get(7)));
		List<String> accountEditPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.EDIT_ACCOUNT_DETAILS).split(Constants.DELIMITER_TILD));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountEditPageData.get(0))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountEditPageData.get(1))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountEditPageData.get(2))));		
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountEditPageData.get(3))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountEditPageData.get(4))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountEditPageData.get(5))));	
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountEditPageData.get(6))));
		
		//verify Change pin
		foundation.click(createAccount.objText(accountEditPageData.get(3)));
		List<String> changePinPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.CHANGE_PIN).split(Constants.DELIMITER_TILD));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(changePinPageData.get(0))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(changePinPageData.get(1))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(changePinPageData.get(2))));
		
		foundation.click(createAccount.objText(changePinPageData.get(2)));
		
		foundation.click(createAccount.objText(accountEditPageData.get(6)));
		//Verifying timeout popup

		List<String> timeOutPopupData = Arrays.asList(rstV5DeviceData.get(CNV5Device.TIME_OUT_POPUP).split(Constants.DELIMITER_TILD));
		foundation.waitforElement(createAccount.objText(timeOutPopupData.get(1)), 30);
		Assert.assertEquals(foundation.getText(Order.POP_UP_TIMEOUT_YES),timeOutPopupData.get(3));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(timeOutPopupData.get(0))));
		foundation.click(Order.POP_UP_TIMEOUT_YES);
		foundation.waitforElement(createAccount.objText(timeOutPopupData.get(0)), 30);
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(timeOutPopupData.get(1))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(timeOutPopupData.get(2))));
	    browser.close();
	    
		
	    browser.launch(Constants.LOCAL,Constants.CHROME);
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
        foundation.waitforElement(LocationList.TXT_FILTER, 5);
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
        foundation.waitforElement(LocationList.TXT_FILTER, 5);
        login.logout();
        browser.close();
        
        foundation.threadWait(5000);
        //login into Kiosk Device
        browser.launch(Constants.REMOTE,Constants.CHROME);
		browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
		
		//Validating Landing Page
		foundation.waitforElement(landingPage.objLanguage(requiredData.get(3)), 5);
		List<String> landingPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.LANDING_PAGE).split(Constants.DELIMITER_TILD));
	
		Assert.assertEquals(foundation.getText(LandingPage.LBL_ACCOUNT_LOGIN),landingPageData.get(0));
		Assert.assertEquals(foundation.getText(LandingPage.LBL_CREATE_ACCOUNT),landingPageData.get(1));
		Assert.assertTrue(foundation.isDisplayed(landingPage.objLanguage(landingPageData.get(2))));
		Assert.assertTrue(foundation.isDisplayed(landingPage.objLanguage(landingPageData.get(3))));
		Assert.assertEquals(foundation.getText(LandingPage.LBL_HEADER),landingPageData.get(4));
		Assert.assertEquals(foundation.getText(LandingPage.LBL_SEARCH),landingPageData.get(5));
		Assert.assertEquals(foundation.getText(LandingPage.LBL_SCAN),landingPageData.get(6));

		//Validating Search Page
		foundation.click(LandingPage.IMG_SEARCH_ICON);
		List<String> productSearchPage = Arrays.asList(rstV5DeviceData.get(CNV5Device.PRODUCT_SEARCH_PAGE).split(Constants.DELIMITER_TILD));
	
		Assert.assertEquals(foundation.getText(ProductSearch.LBL_PRODUCT_SEARCH_TITLE),productSearchPage.get(0));
		Assert.assertEquals(foundation.getText(ProductSearch.LBL_PRODUCT_SEARCH_HEADER),productSearchPage.get(1));
		Assert.assertEquals(foundation.getText(ProductSearch.LBL_PRODUCT_FOUND),productSearchPage.get(2));

		foundation.click(AccountLogin.BTN_CAMELCASE);
		textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
        foundation.click(ProductSearch.BTN_PRODUCT);
        
        //verify Order Page
        List<String> orderPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.ORDER_PAGE).split(Constants.DELIMITER_TILD));
        Assert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(0))));
        Assert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(1))));
        Assert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(2))));
        Assert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(3))));
        Assert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(4))));
        Assert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(5))));
        Assert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(6))));
        foundation.objectFocus(order.objText(orderPageData.get(7)));
        Assert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(7))));
        Assert.assertTrue(foundation.isDisplayed(order.objText(orderPageData.get(8))));
        
        
        //Validating Credit/Debit Page
        foundation.click(order.objText(orderPageData.get(8)));
        List<String> crditDebitPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.CREDIT_DEBIT_PAGE).split(Constants.DELIMITER_TILD));
        foundation.waitforElement(cardPayment.objText(crditDebitPageData.get(0)), 5);        
        Assert.assertTrue(foundation.isDisplayed(cardPayment.objText(crditDebitPageData.get(0))));
        Assert.assertTrue(foundation.isDisplayed(cardPayment.objText(crditDebitPageData.get(1))));
        
        foundation.click(CardPayment.BTN_CLOSE);
        foundation.waitforElement(order.objText(orderPageData.get(0)), 5);
        
        //verify Cancel Order Page
  		foundation.click(order.objText(orderPageData.get(0)));
        Assert.assertTrue(foundation.isDisplayed(createAccount.objText(rstV5DeviceData.get(CNV5Device.TRANSACTION_CANCEL))));
		
        
		//Validating Create Account Page
        foundation.waitforElement(LandingPage.BTN_CREATE_ACCOUNT, 5);
		foundation.click(LandingPage.BTN_CREATE_ACCOUNT);
		List<String> createAccountPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.CREATE_ACCOUNT).split(Constants.DELIMITER_TILD));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(createAccountPageData.get(0))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(createAccountPageData.get(1))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(createAccountPageData.get(2))));
		foundation.objectFocus(CreateAccount.CHK_LABEL);
		foundation.click(CreateAccount.CHK_LABEL);
	
		foundation.click(createAccount.objText(createAccountPageData.get(1)));
		foundation.waitforElement(createAccount.objText(createAccountPageData.get(3)), 5);
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(createAccountPageData.get(4))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(createAccountPageData.get(5))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(createAccountPageData.get(6))));
		
		foundation.click(CreateAccount.LBL_NW_FINGERPRINT_HEADER);
		
		foundation.objectFocus(CreateAccount.CHK_LABEL);
		foundation.click(CreateAccount.CHK_LABEL);
		foundation.click(createAccount.objText(createAccountPageData.get(1)));
		
		foundation.waitforElement(createAccount.objText(createAccountPageData.get(7)), 5);
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(createAccountPageData.get(3))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(createAccountPageData.get(7))));
		String fingerPrintHeader=foundation.getText(CreateAccount.LBL_FINGERPRINT_HEADER);
		Assert.assertTrue(fingerPrintHeader.equals(createAccountPageData.get(8)));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(createAccountPageData.get(9))));
				
		foundation.click(createAccount.objText(createAccountPageData.get(9)));
		foundation.waitforElement(createAccount.objText(createAccountPageData.get(4)), 5);
		foundation.click(createAccount.objText(createAccountPageData.get(5)));
		foundation.waitforElement(createAccount.objText(createAccountPageData.get(11)), 5);
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(createAccountPageData.get(3))));
		String scanHeader=foundation.getText(CreateAccount.LBL_NORWEGIAN_HEADER);
		Assert.assertTrue(scanHeader.equals(createAccountPageData.get(10)));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(createAccountPageData.get(11))));
		foundation.click(CreateAccount.BTN_CLOSE);
		foundation.waitforElement(LandingPage.BTN_CREATE_ACCOUNT,5);
		
		//Validating Account Login Page
		foundation.click(LandingPage.BTN_LOGIN);
		List<String> loginPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.LOGIN_PAGE).split(Constants.DELIMITER_TILD));
		Assert.assertEquals(foundation.getText(AccountLogin.LBL_PAGE_TITLE),loginPageData.get(0));
		Assert.assertEquals(foundation.getText(AccountLogin.LBL_PAGE_HEADER),loginPageData.get(1));
		Assert.assertEquals(foundation.getText(AccountLogin.LBL_SCAN),loginPageData.get(2));
		Assert.assertEquals(foundation.getText(AccountLogin.LBL_FINGER_PRINT),loginPageData.get(3));
		Assert.assertEquals(foundation.getText(AccountLogin.BTN_EMAIL_LOGIN),loginPageData.get(4));
		Assert.assertEquals(foundation.getText(AccountLogin.BTN_I_DONT_HAVE_ACCOUNT),loginPageData.get(5));
		
        //Validating Account Login Email Page
		foundation.click(AccountLogin.BTN_EMAIL_LOGIN);
		foundation.waitforElement(AccountLogin.BTN_NEXT, 5);
		Assert.assertEquals(foundation.getText(AccountLogin.LBL_PAGE_TITLE),loginPageData.get(0));
		Assert.assertEquals(foundation.getText(AccountLogin.LBL_EMAIL_HEADER),loginPageData.get(6));
		Assert.assertEquals(foundation.getText(AccountLogin.BTN_EMAIl_BACK),loginPageData.get(7));
		Assert.assertEquals(foundation.getText(AccountLogin.BTN_NEXT),loginPageData.get(8));
		  
		foundation.click(AccountLogin.BTN_CAMELCASE);
		textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.EMAIL_ID));
		foundation.click(AccountLogin.BTN_ENTER);
        foundation.click(AccountLogin.BTN_NEXT);
        
        //Validating Account Login PIN Page
        foundation.waitforElement(AccountLogin.BTN_PIN_NEXT, 5);
        Assert.assertEquals(foundation.getText(AccountLogin.LBL_PIN_PAGE_TITLE),loginPageData.get(0));
		Assert.assertEquals(foundation.getText(AccountLogin.LBL_PIN_HEADER),loginPageData.get(9));
		Assert.assertEquals(foundation.getText(AccountLogin.BTN_PIN_BACK),loginPageData.get(7));
		Assert.assertEquals(foundation.getText(AccountLogin.BTN_PIN_NEXT),loginPageData.get(10));
		
        textBox.enterPin(rstV5DeviceData.get(CNV5Device.PIN));
		foundation.click(AccountLogin.BTN_PIN_NEXT);
        
		//Verifying Account info page
		List<String> accountPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.ACCOUNT_DETAILS).split(Constants.DELIMITER_TILD));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountPageData.get(0))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountPageData.get(1))));
		String tegoed=foundation.getText(CreateAccount.LBL_DUTCH_HEADER);
		Assert.assertTrue(tegoed.equals(accountPageData.get(2)));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountPageData.get(3))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountPageData.get(4))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountPageData.get(5))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountPageData.get(6))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountPageData.get(5))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountPageData.get(7))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountPageData.get(8))));
		
		//Verify Terms and Condition Page (Could not validate as there is no Ok button in terms and condition screen
		foundation.click(createAccount.objText(accountPageData.get(9)));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountPageData.get(9))));
		Assert.assertEquals(foundation.getText(AccountDetails.BTN_OK),accountPageData.get(10));
		foundation.click(AccountDetails.BTN_OK);
		
		//Verifying Fund with card page
		List<String> fundPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.FUND_ACCOUNT_PAGE).split(Constants.DELIMITER_TILD));
		foundation.click(createAccount.objText(accountPageData.get(1)));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(fundPageData.get(0))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(fundPageData.get(1))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(fundPageData.get(2))));
		foundation.click(createAccount.objText(fundPageData.get(3)));
		
		//Verifying Fund with card page details page
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(fundPageData.get(4))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(fundPageData.get(5))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(fundPageData.get(6))));
		
		foundation.click(createAccount.objText(fundPageData.get(6)));
		foundation.click(createAccount.objText(fundPageData.get(2)));
		foundation.click(createAccount.objText(accountPageData.get(4)));
		
		//Verifying Scan Setup page
		List<String> scanSetupPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.QUICK_SCAN_SETUP).split(Constants.DELIMITER_TILD));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(scanSetupPageData.get(0))));
		String frScanHeader=foundation.getText(CreateAccount.LBL_FR_SCAN_HEADER);
		Assert.assertTrue(frScanHeader.equals(scanSetupPageData.get(1)));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(scanSetupPageData.get(2))));
		
		foundation.click(createAccount.objText(scanSetupPageData.get(2)));
		
		//Verifying Finger print Setup page
		List<String> fingerPrintPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.FINGER_PRINT_SETUP).split(Constants.DELIMITER_TILD));
		foundation.click(createAccount.objText(accountPageData.get(6)));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(fingerPrintPageData.get(0))));
		String frFingerPrintHeader=foundation.getText(CreateAccount.LBL_FR_FINGERPRINT_HEADER);
		Assert.assertTrue(frFingerPrintHeader.equals(fingerPrintPageData.get(1)));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(fingerPrintPageData.get(2))));
		
		foundation.click(createAccount.objText(fingerPrintPageData.get(2)));
		
		//Verifying Edit account page
		foundation.click(createAccount.objText(accountPageData.get(7)));
		List<String> accountEditPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.EDIT_ACCOUNT_DETAILS).split(Constants.DELIMITER_TILD));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountEditPageData.get(0))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountEditPageData.get(1))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountEditPageData.get(2))));		
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountEditPageData.get(3))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountEditPageData.get(4))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountEditPageData.get(5))));	
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(accountEditPageData.get(6))));
		
		//verify Change pin
		foundation.click(createAccount.objText(accountEditPageData.get(3)));
		List<String> changePinPageData = Arrays.asList(rstV5DeviceData.get(CNV5Device.CHANGE_PIN).split(Constants.DELIMITER_TILD));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(changePinPageData.get(0))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(changePinPageData.get(1))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(changePinPageData.get(2))));
		
		foundation.click(createAccount.objText(changePinPageData.get(2)));
		
		foundation.click(createAccount.objText(accountEditPageData.get(6)));
		//Verifying timeout popup

		List<String> timeOutPopupData = Arrays.asList(rstV5DeviceData.get(CNV5Device.TIME_OUT_POPUP).split(Constants.DELIMITER_TILD));
		foundation.waitforElement(createAccount.objText(timeOutPopupData.get(1)), 30);
		Assert.assertEquals(foundation.getText(Order.POP_UP_TIMEOUT_YES),timeOutPopupData.get(3));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(timeOutPopupData.get(0))));
		foundation.click(Order.POP_UP_TIMEOUT_YES);
		foundation.waitforElement(createAccount.objText(timeOutPopupData.get(0)), 30);
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(timeOutPopupData.get(1))));
		Assert.assertTrue(foundation.isDisplayed(createAccount.objText(timeOutPopupData.get(2))));
	    browser.close();
	    
		
	    browser.launch(Constants.LOCAL,Constants.CHROME);
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
        foundation.waitforElement(LocationList.TXT_FILTER, 5);
        login.logout();
                    
    } catch (Exception exc) {
        exc.printStackTrace();
        Assert.fail();
    }
}
}
