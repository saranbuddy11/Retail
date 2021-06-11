package at.smartshop.v5.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import at.framework.database.mssql.Queries;
import at.framework.database.mssql.ResultSets;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.database.columns.CNDeviceList;
import at.smartshop.database.columns.CNLocation;
import at.smartshop.database.columns.CNV5Device;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.tests.TestInfra;
import at.smartshop.v5.pages.AccountLogin;
import at.smartshop.v5.pages.EditAccount;
import at.smartshop.v5.pages.LandingPage;
import at.smartshop.v5.pages.Order;
import at.smartshop.v5.pages.ProductSearch;
import at.smartshop.utilities.*;

@Listeners(at.framework.reportsetup.Listeners.class)
public class V5Test extends TestInfra {

	private Foundation foundation = new Foundation();
	private TextBox textBox = new TextBox();
	private ResultSets dataBase = new ResultSets();
	private LandingPage landingPage = new LandingPage();
	private AccountLogin accountLogin = new AccountLogin();
	private EditAccount editAccount = new EditAccount();
	private CurrenyConverter currenyConverter = new CurrenyConverter();

	private Map<String, String> rstV5DeviceData;

	@Test(description = "141874-Kiosk Manage Account > Edit Account > Update Information")
	public void editAccountUpdateInformation() {
		final String CASE_NUM = "141874";

		browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));

		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		List<String> requiredData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));

		// login to application
		foundation.click(landingPage.objLanguage(requiredData.get(0)));
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
		editAccount.updateText(EditAccount.TXT_FIRST_NAME, requiredData.get(1), firstName);
		editAccount.updateText(EditAccount.TXT_LAST_NAME, requiredData.get(2), lastName);
		editAccount.updateText(EditAccount.TXT_EMAIL_ADDRESS, requiredData.get(3), emailAddress);
		foundation.click(EditAccount.BTN_SAVE);
		assertTrue(foundation.isDisplayed(EditAccount.BTN_EDIT_ACCOUNT));

		// navigate back to edit account and verify all data are populating as entered
		// before
		foundation.click(EditAccount.BTN_EDIT_ACCOUNT);
		assertTrue(textBox.getTextFromInput(EditAccount.TXT_FIRST_NAME).equals(requiredData.get(1)));
		assertTrue(textBox.getTextFromInput(EditAccount.TXT_LAST_NAME).equals(requiredData.get(2)));
		assertTrue(textBox.getTextFromInput(EditAccount.TXT_EMAIL_ADDRESS).equals(requiredData.get(3)));

		// reset the data
		foundation.click(EditAccount.BTN_CAMEL_CASE);
		editAccount.updateText(EditAccount.TXT_FIRST_NAME, firstName, requiredData.get(1));
		editAccount.updateText(EditAccount.TXT_LAST_NAME, lastName, requiredData.get(2));
		editAccount.updateText(EditAccount.TXT_EMAIL_ADDRESS, emailAddress, requiredData.get(3));
		foundation.click(EditAccount.BTN_SAVE);
		assertTrue(foundation.isDisplayed(EditAccount.BTN_EDIT_ACCOUNT));
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
		
		//cancel order
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
		assertEquals(foundation.getText(Order.LBL_TAX),requiredData.get(2));
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
		assertTrue(foundation.getText(ProductSearch.LBL_PRODUCT_NAME).contains(requiredData.get(2)));
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
		textBox.enterKeypadText(requiredData.get(1));
		assertTrue(foundation.isDisplayed(ProductSearch.BTN_PRODUCT));
		textBox.deleteKeypadText(requiredData.get(1));
		assertFalse(foundation.isDisplayed(ProductSearch.BTN_PRODUCT));
		textBox.enterKeypadText(requiredData.get(2));
		assertTrue(foundation.getText(ProductSearch.LBL_PRODUCT_NAME).contains(requiredData.get(2)));
		foundation.click(ProductSearch.BTN_PRODUCT);
		assertEquals(foundation.getText(Order.TXT_HEADER), actualData.get(0));
		assertEquals(foundation.getText(Order.TXT_PRODUCT), actualData.get(1));
	}
}
