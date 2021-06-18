package at.smartshop.v5.tests;


import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import at.framework.database.mssql.Queries;
import at.framework.database.mssql.ResultSets;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.database.columns.CNV5Device;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.tests.TestInfra;
import at.smartshop.v5.pages.AdminMenu;
import at.smartshop.v5.pages.LandingPage;
import at.smartshop.v5.pages.Order;
import at.smartshop.v5.pages.ProductSearch;
import at.smartshop.v5.pages.AccountLogin;
import at.smartshop.v5.pages.EditAccount;


@Listeners(at.framework.reportsetup.Listeners.class)
public class V5Test extends TestInfra {
		
	private Foundation foundation=new Foundation();	

	private TextBox textBox=new TextBox();
	private ResultSets dataBase = new ResultSets();
	private LandingPage landingPage=new LandingPage();
	private AccountLogin accountLogin=new AccountLogin();
	private EditAccount editAccount=new EditAccount();
	private AdminMenu adminMenu = new AdminMenu();
	
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
	
	@Test(description = "C141867 - This test validates the Driver Login and Log Out")
	public void verifyDriverLoginLogout() {
		try {
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(LandingPage.LINK_ENGLISH);
			foundation.doubleClick(LandingPage.IMG_LOGO);
			foundation.doubleClick(LandingPage.IMG_LOGO);
			foundation.click(LandingPage.IMG_LOGO);
			String pin = propertyFile.readPropertyFile(Configuration.V5_DRIVER_PIN, FilePath.PROPERTY_CONFIG_FILE);
			textBox.enterDriverPin(pin);
			foundation.click(AdminMenu.BTN_SIGN_IN);
			foundation.isDisplayed(AdminMenu.LINK_DRIVER_LOGOUT);
			foundation.click(AdminMenu.LINK_DRIVER_LOGOUT);

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
	
	@Test(description = "C141868-Thsi test validates the Inventory options")
	public void verifyInventoryOptions() {
		try {
			final String CASE_NUM= "141868";
			
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL , FilePath.PROPERTY_CONFIG_FILE));
			String language = rstV5DeviceData.get(CNV5Device.ACTUAL_DATA);
			foundation.click(landingPage.objLanguage(language));
			foundation.doubleClick(LandingPage.IMG_LOGO);
			foundation.doubleClick(LandingPage.IMG_LOGO);
			foundation.click(LandingPage.IMG_LOGO);
			String pin =  propertyFile.readPropertyFile(Configuration.V5_DRIVER_PIN, FilePath.PROPERTY_CONFIG_FILE);
			textBox.enterDriverPin(pin);
			foundation.click(AdminMenu.BTN_SIGN_IN);
			foundation.isDisplayed(AdminMenu.LINK_DRIVER_LOGOUT);
			foundation.click(AdminMenu.LINK_INVENTORY);
			List<String> optionNames = Arrays.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD)); 
			adminMenu.verifyOptions(optionNames);			
			
		}catch(Exception exc) {
			Assert.fail(exc.toString());
		}
	}
	
	@Test(description = "C141873 - Verify the products as per the user search")
	public void verifyProductSearch() {
		try {
			
			final String CASE_NUM ="141873";
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL , FilePath.PROPERTY_CONFIG_FILE));
			String language = rstV5DeviceData.get(CNV5Device.ACTUAL_DATA);
			foundation.click(landingPage.objLanguage(language));
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			List<String> productName = Arrays.asList(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME).split(Constants.DELIMITER_TILD));
			textBox.enterKeypadText(productName.get(0));
			String prodCount = foundation.getText(ProductSearch.LBL_PROD_COUNT);
			String prodFoundText = foundation.getText(ProductSearch.LBL_PROD_MESSAGE);
			String unMatchedProdMsg = prodCount+" "+prodFoundText;
			List<String> requiredData = Arrays.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			Assert.assertEquals(unMatchedProdMsg, requiredData.get(0));		
			
			foundation.click(ProductSearch.LINK_CLOSE_POPUP);
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(productName.get(1));
			String matchedCount = foundation.getText(ProductSearch.LBL_PROD_COUNT);
			String matchedProdMsg = matchedCount+" "+prodFoundText;
			Assert.assertEquals(matchedProdMsg, requiredData.get(1));
			
			
		}catch(Exception exc) {
			Assert.fail(exc.toString());
		}
	}
	
	@Test(description ="C141872 - This test validates the item in the Your order screen")
	public void verifyItemInOrderScreen() {
		try {
			
			final String CASE_NUM ="141872";
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL , FilePath.PROPERTY_CONFIG_FILE));
			String language = rstV5DeviceData.get(CNV5Device.ACTUAL_DATA);
			foundation.click(landingPage.objLanguage(language));
			
		    // search and click product
	         foundation.click(LandingPage.IMG_SEARCH_ICON);
	        textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
	        foundation.click(ProductSearch.BTN_PRODUCT);       
	        assertEquals(foundation.getText(Order.TXT_HEADER),rstV5DeviceData.get(CNV5Device.REQUIRED_DATA));       
	        assertEquals(foundation.getText(Order.TXT_PRODUCT),rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
			
		}catch(Exception exc) {
			Assert.fail(exc.toString());
		}
	}
}
