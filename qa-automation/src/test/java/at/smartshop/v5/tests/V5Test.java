package at.smartshop.v5.tests;

import static org.testng.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import at.framework.database.mssql.Queries;
import at.framework.database.mssql.ResultSets;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.database.columns.CNLocationList;
import at.smartshop.database.columns.CNNavigationMenu;
import at.smartshop.database.columns.CNV5Device;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.pages.DeviceSummary;
import at.smartshop.pages.LocationList;
import at.smartshop.pages.LocationSummary;
import at.smartshop.pages.NavigationBar;
import at.smartshop.tests.TestInfra;
import at.smartshop.v5.pages.AccountLogin;
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
	private NavigationBar navigationBar = new NavigationBar();
	private LocationSummary locationSummary = new LocationSummary();
	private LocationList locationList = new LocationList();
	private DeviceSummary deviceSummary = new DeviceSummary();

	
	private Map<String, String> rstV5DeviceData;
	private Map<String, String> rstNavigationMenuData;
	private Map<String, String> rstLocationListData;
	
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
	
	@Test(description = "C142663 - This test validates the functionality of Cancel order functionality")
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
			
		}catch(Exception exc) {
			Assert.fail(exc.toString());
		}
	}
	
	@Test(description = "C142664 - This test validates the Yes button functionality on Order Screen")
	public void verifyYesButtonFunctionality() {
		try {
			
			final String CASE_NUM = "142664";
			
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstNavigationMenuData =  dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
			
			 browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL,FilePath.PROPERTY_CONFIG_FILE));
			 login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER,FilePath.PROPERTY_CONFIG_FILE), propertyFile
					 							.readPropertyFile(Configuration.CURRENT_PASSWORD,FilePath.PROPERTY_CONFIG_FILE));
			 navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG,FilePath.PROPERTY_CONFIG_FILE));
			 
			 String menu = rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM);
			 String deviceName = rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION);
			 String locationName = rstLocationListData.get(CNLocationList.LOCATION_NAME);
			 String language = rstV5DeviceData.get(CNV5Device.REQUIRED_DATA);
			 String time = rstV5DeviceData.get(CNV5Device.TIMEOUT_POPUP);
			 
			 locationList.selectLocationName(locationName);
			 foundation.objectFocus(LocationSummary.BTN_DEPLOY_DEVICE);
			 textBox.enterText(LocationSummary.TXT_DEVICE_SEARCH, deviceName);
			 locationSummary.selectDeviceName(deviceName);
			 foundation.waitforElement(DeviceSummary.LBL_DEVICE_SUMMARY, 2);
			 deviceSummary.setTimeOut(time);
			 foundation.click(DeviceSummary.BTN_SAVE);
			 navigationBar.navigateToMenuItem(menu);
			 locationList.selectLocationName(locationName);
			 foundation.waitforElement(LocationSummary.BTN_SAVE, 5);
			 foundation.click(LocationSummary.BTN_SYNC);
			 foundation.click(LocationSummary.BTN_SAVE);
			 foundation.waitforElement(LocationList.TXT_FILTER, 5);
			 login.logout();
			 browser.close();
			 
			browser.launch(Constants.REMOTE,Constants.CHROME);		
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));	
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			foundation.click(landingPage.objLanguage(language));
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
			foundation.click(ProductSearch.BTN_PRODUCT);
			Assert.assertTrue(foundation.isDisplayed(Order.BTN_CANCEL_ORDER));
			foundation.waitforElement(Order.POP_UP_LBL_ORDER_TIMEOUT, 20);
			foundation.click(Order.POP_UP_TIMEOUT_YES);
			Assert.assertTrue(foundation.isDisplayed(Order.LBL_YOUR_ORDER));
			
		}catch(Exception exc) {
			Assert.fail(exc.toString());
		}
	}
	
	@Test(description = "C142665 - This test validates the No button functionality on Order Screen")
	public void verifyNoButtonFunctionality() {
		try {
			
			final String CASE_NUM = "142665";
			
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstNavigationMenuData =  dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
			
			 browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL,FilePath.PROPERTY_CONFIG_FILE));
			 login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER,FilePath.PROPERTY_CONFIG_FILE), propertyFile
					 							.readPropertyFile(Configuration.CURRENT_PASSWORD,FilePath.PROPERTY_CONFIG_FILE));
			 navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG,FilePath.PROPERTY_CONFIG_FILE));
			 
			 String menu = rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM);
			 String deviceName = rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION);
			 String locationName = rstLocationListData.get(CNLocationList.LOCATION_NAME);
			 String language = rstV5DeviceData.get(CNV5Device.REQUIRED_DATA);
			 String time = rstV5DeviceData.get(CNV5Device.TIMEOUT_POPUP);
			 
			 locationList.selectLocationName(locationName);
			 foundation.objectFocus(LocationSummary.BTN_DEPLOY_DEVICE);
			 textBox.enterText(LocationSummary.TXT_DEVICE_SEARCH, deviceName);
			 locationSummary.selectDeviceName(deviceName);
			 foundation.waitforElement(DeviceSummary.LBL_DEVICE_SUMMARY, 2);
			 deviceSummary.setTimeOut(time);
			 foundation.click(DeviceSummary.BTN_SAVE);
			 navigationBar.navigateToMenuItem(menu);
			 locationList.selectLocationName(locationName);
			 foundation.waitforElement(LocationSummary.BTN_SAVE, 5);
			 foundation.click(LocationSummary.BTN_SYNC);
			 foundation.click(LocationSummary.BTN_SAVE);
			 foundation.waitforElement(LocationList.TXT_FILTER, 5);
			 login.logout();
			 browser.close();
			 
			 browser.launch(Constants.REMOTE,Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));	
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			foundation.click(landingPage.objLanguage(language));
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
			foundation.click(ProductSearch.BTN_PRODUCT);
			Assert.assertTrue(foundation.isDisplayed(Order.BTN_CANCEL_ORDER));
			foundation.waitforElement(Order.POP_UP_LBL_ORDER_TIMEOUT, 20);
			foundation.click(Order.POP_UP_TIMEOUT_NO);
			Assert.assertTrue(foundation.isDisplayed(LandingPage.IMG_SEARCH_ICON));
			
		}catch(Exception exc) {
			Assert.fail(exc.toString());
		}
	}
	
	@Test(description = "C142666 - This test validates the Order Time out prompt will disapper after time out")
	public void verifyPromptAfterTimeOut() {
		try {
			
			final String CASE_NUM = "142666";
			
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstNavigationMenuData =  dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
			
			 browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL,FilePath.PROPERTY_CONFIG_FILE));
			 login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER,FilePath.PROPERTY_CONFIG_FILE), propertyFile
					 							.readPropertyFile(Configuration.CURRENT_PASSWORD,FilePath.PROPERTY_CONFIG_FILE));
			 navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG,FilePath.PROPERTY_CONFIG_FILE));
			 
			 String menu = rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM);
			 String deviceName = rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION);
			 String locationName = rstLocationListData.get(CNLocationList.LOCATION_NAME);
			 String language = rstV5DeviceData.get(CNV5Device.REQUIRED_DATA);
			 String time = rstV5DeviceData.get(CNV5Device.TIMEOUT_POPUP);
			 
			 locationList.selectLocationName(locationName);
			 foundation.objectFocus(LocationSummary.BTN_DEPLOY_DEVICE);
			 textBox.enterText(LocationSummary.TXT_DEVICE_SEARCH, deviceName);
			 locationSummary.selectDeviceName(deviceName);
			 foundation.waitforElement(DeviceSummary.LBL_DEVICE_SUMMARY, 2);
			 deviceSummary.setTimeOut(time);
			 foundation.click(DeviceSummary.BTN_SAVE);
			 navigationBar.navigateToMenuItem(menu);
			 locationList.selectLocationName(locationName);
			 foundation.waitforElement(LocationSummary.BTN_SAVE, 5);
			 foundation.click(LocationSummary.BTN_SYNC);
			 foundation.click(LocationSummary.BTN_SAVE);
			 foundation.waitforElement(LocationList.TXT_FILTER, 5);
			 login.logout();
			 browser.close();
			 
			 browser.launch(Constants.REMOTE,Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));	
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			foundation.click(landingPage.objLanguage(language));
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
			foundation.click(ProductSearch.BTN_PRODUCT);
			Assert.assertTrue(foundation.isDisplayed(Order.BTN_CANCEL_ORDER));
			foundation.waitforElement(Order.POP_UP_LBL_ORDER_TIMEOUT, 20);
			Assert.assertTrue(foundation.isDisplayed(Order.POP_UP_LBL_ORDER_TIMEOUT));
			foundation.waitforElement(LandingPage.IMG_SEARCH_ICON, 5);
			Assert.assertTrue(foundation.isDisplayed(LandingPage.IMG_SEARCH_ICON));
			
		}catch(Exception exc) {
			Assert.fail(exc.toString());
		}
		
	}
	
	@Test(description="C142680 - This test validates the Order Timeout Prompt when user not  perform any action")
	public void verifyOrderTimePromptDetails() {
		try {
			
			final String CASE_NUM ="142680";
			
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstNavigationMenuData =  dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
			
			 browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL,FilePath.PROPERTY_CONFIG_FILE));
			 login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER,FilePath.PROPERTY_CONFIG_FILE), propertyFile
					 							.readPropertyFile(Configuration.CURRENT_PASSWORD,FilePath.PROPERTY_CONFIG_FILE));
			 navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG,FilePath.PROPERTY_CONFIG_FILE));
			 
			 String menu = rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM);
			 String deviceName = rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION);
			 String locationName = rstLocationListData.get(CNLocationList.LOCATION_NAME);
			 String language = rstV5DeviceData.get(CNV5Device.REQUIRED_DATA);
			 String time = rstV5DeviceData.get(CNV5Device.TIMEOUT_POPUP);
			 
			 locationList.selectLocationName(locationName);
			 foundation.objectFocus(LocationSummary.BTN_DEPLOY_DEVICE);
			 textBox.enterText(LocationSummary.TXT_DEVICE_SEARCH, deviceName);
			 locationSummary.selectDeviceName(deviceName);
			 foundation.waitforElement(DeviceSummary.LBL_DEVICE_SUMMARY, 2);
			 deviceSummary.setTimeOut(time);
			 foundation.click(DeviceSummary.BTN_SAVE);
			 navigationBar.navigateToMenuItem(menu);
			 locationList.selectLocationName(locationName);
			 foundation.waitforElement(LocationSummary.BTN_SAVE, 5);
			 foundation.click(LocationSummary.BTN_SYNC);
			 foundation.click(LocationSummary.BTN_SAVE);
			 foundation.waitforElement(LocationList.TXT_FILTER, 5);
			 login.logout();
			 browser.close();
			
	        browser.launch(Constants.REMOTE,Constants.CHROME);
	        browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(landingPage.objLanguage(language));
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
			foundation.click(ProductSearch.BTN_PRODUCT);
			Assert.assertTrue(foundation.isDisplayed(Order.BTN_CANCEL_ORDER));
			foundation.waitforElement(Order.POP_UP_LBL_ORDER_TIMEOUT, 32);
			Assert.assertTrue(foundation.isDisplayed(Order.POP_UP_LBL_ORDER_TIMEOUT));
			Assert.assertTrue(foundation.isDisplayed(Order.POP_UP_LBL_ORDER_TIMEOUT_MSG));
			Assert.assertTrue(foundation.isDisplayed(Order.POP_UP_TIMEOUT_YES));
			Assert.assertTrue(foundation.isDisplayed(Order.POP_UP_TIMEOUT_NO));	
			
		}catch(Exception exc) {
			Assert.fail(exc.toString());
		}
	}
	
	@Test(description = "C142729 - This test validates the Order time out prompt when user decreases the time below 20 sec")
	public void verifyPromptWithBelow20Sec() {
		try {
			
			final String CASE_NUM ="142729";
			
			  rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			  rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			  rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST,CASE_NUM);
			  
			  browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL,FilePath.PROPERTY_CONFIG_FILE));
			  login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER,FilePath.PROPERTY_CONFIG_FILE), 
					  		  propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD,FilePath.PROPERTY_CONFIG_FILE));
			  navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.RNOUS_ORG,FilePath.PROPERTY_CONFIG_FILE));
				  
			  locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			  foundation.objectFocus(LocationSummary.BTN_DEPLOY_DEVICE);
			  textBox.enterText(LocationSummary.TXT_DEVICE_SEARCH, rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));
			  locationSummary.selectDeviceName(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));
			  foundation.waitforElement(DeviceSummary.LBL_DEVICE_SUMMARY, 2);
			  deviceSummary.setTimeOut(rstV5DeviceData.get(CNV5Device.TIMEOUT_POPUP)); 
			  foundation.click(DeviceSummary.BTN_SAVE);
			  navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			  locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			  foundation.waitforElement(LocationSummary.BTN_SAVE, 5);
			  foundation.click(LocationSummary.BTN_SYNC);
			  foundation.click(LocationSummary.BTN_SAVE);
			  foundation.waitforElement(LocationList.TXT_FILTER, 5); 
			  login.logout();
			  browser.close();			 
			
	        browser.launch(Constants.REMOTE,Constants.CHROME);
	        browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(landingPage.objLanguage(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA)));
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME));
			foundation.click(ProductSearch.BTN_PRODUCT);
			Assert.assertTrue(foundation.isDisplayed(Order.BTN_CANCEL_ORDER));
			foundation.threadWait(15000);
			Assert.assertTrue(foundation.isDisplayed(Order.LBL_YOUR_ORDER));
			
		}catch(Exception exc) {
			Assert.fail(exc.toString());
		}
	}
	
}
