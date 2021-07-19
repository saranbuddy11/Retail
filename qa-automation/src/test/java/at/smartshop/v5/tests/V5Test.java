package at.smartshop.v5.tests;

import static org.testng.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
import at.smartshop.v5.pages.AccountLogin;
import at.smartshop.v5.pages.EditAccount;
import at.smartshop.v5.pages.LandingPage;

@Listeners(at.framework.reportsetup.Listeners.class)
public class V5Test extends TestInfra {
		
	private Foundation foundation=new Foundation();
	private TextBox textBox=new TextBox();
	private ResultSets dataBase = new ResultSets();
	private LandingPage landingPage=new LandingPage();
	private AccountLogin accountLogin=new AccountLogin();
	private EditAccount editAccount=new EditAccount();
	
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
	
	@Test(description ="142718 - SOS-24494-V5 - Apply Discount ( Discount field ) for product and verify it on Kiosk machine cart page")
	public void testRailForPass() {
		final String CASE_NUM = "142721";	
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			//foundation.click(LandingPage.BTN_LOGIN);
		}
		catch (Exception exc) {
			// TODO: handle exception
		}
	}
	
	@Test(description ="142722-SOS-24494-V5 - Apply Discount ( Discount field ) for product and verify it on Kiosk machine cart page")
	public void testRailForFail() {
		final String CASE_NUM = "142722";
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(LandingPage.BTN_LOGIN);
		}
		catch (Exception exc) {
			// TODO: handle exception
		}
	}
}
