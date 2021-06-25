package at.smartshop.v5.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import at.framework.database.mssql.Queries;
import at.framework.database.mssql.ResultSets;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.database.columns.CNNavigationMenu;
import at.smartshop.database.columns.CNV5Device;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.pages.GlobalProduct;
import at.smartshop.pages.LocationList;
import at.smartshop.pages.LocationSummary;
import at.smartshop.pages.NavigationBar;
import at.smartshop.pages.ProductSummary;
import at.smartshop.tests.TestInfra;
import at.smartshop.utilities.CurrenyConverter;
import at.smartshop.v5.pages.AccountLogin;
import at.smartshop.v5.pages.EditAccount;
import at.smartshop.v5.pages.LandingPage;
import at.smartshop.v5.pages.Order;
import at.smartshop.v5.pages.ProductSearch;

@Listeners(at.framework.reportsetup.Listeners.class)
public class V5Test extends TestInfra {

	private Foundation foundation = new Foundation();
	private TextBox textBox = new TextBox();
	private ResultSets dataBase = new ResultSets();
	private LandingPage landingPage = new LandingPage();
	private AccountLogin accountLogin = new AccountLogin();
	private EditAccount editAccount = new EditAccount();
	private CurrenyConverter currenyConverter = new CurrenyConverter();
	private NavigationBar navigationBar=new NavigationBar();
	private GlobalProduct globalProduct=new GlobalProduct();
	private LocationList locationList=new LocationList();
	private LocationSummary locationSummary=new LocationSummary();
	private Dropdown dropdown=new Dropdown();

	private Map<String, String> rstV5DeviceData;
	private Map<String, String> rstNavigationMenuData;

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
		assertTrue(foundation.getText(ProductSearch.LBL_PRODUCT_NAME).contains(actualData.get(2)));
		foundation.click(ProductSearch.BTN_PRODUCT);
		assertEquals(foundation.getText(Order.TXT_HEADER), actualData.get(0));
		assertEquals(foundation.getText(Order.TXT_PRODUCT), actualData.get(2));
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
		
		//launch browser and select org
		browser.close();
		browser.launch(Constants.LOCAL, Constants.CHROME);
		browser.navigateURL(
				propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
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
		
		//reset data
		browser.close();
		browser.launch(Constants.LOCAL, Constants.CHROME);
		browser.navigateURL(
				propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
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
		
		//launch browser and select org
		browser.close();
		browser.launch(Constants.LOCAL, Constants.CHROME);
		browser.navigateURL(
				propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
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
		
		//reset data
		browser.close();
		browser.launch(Constants.LOCAL, Constants.CHROME);
		browser.navigateURL(
				propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
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
		
		//launch browser and select org
		browser.close();
		browser.launch(Constants.LOCAL, Constants.CHROME);
		browser.navigateURL(
				propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
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
		
		//reset data
		browser.close();
		browser.launch(Constants.LOCAL, Constants.CHROME);
		browser.navigateURL(
				propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
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
		
		//launch browser and select org
		browser.close();
		browser.launch(Constants.LOCAL, Constants.CHROME);
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
		
		//reset data
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
		
		//launch browser and select org
		browser.close();
		browser.launch(Constants.LOCAL, Constants.CHROME);
		browser.navigateURL(
				propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
		login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
				propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
		navigationBar.selectOrganization(
				propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
		
		// navigate to global product of V5 associated and update name and sync
		navigationBar.navigateToMenuItem(menuItem.get(0));
		foundation.threadWait(2000);		
		textBox.enterText(GlobalProduct.TXT_FILTER, requiredData.get(0));
		foundation.click(globalProduct.getGlobalProduct(requiredData.get(1)));
		dropdown.selectItem(ProductSummary.DPD_IS_DISABLED, requiredData.get(2), Constants.TEXT);
		foundation.click(ProductSummary.BTN_SAVE);
		foundation.waitforElement(GlobalProduct.TXT_FILTER,3);
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
		
		//reset data- enable back product
		browser.close();
		browser.launch(Constants.LOCAL, Constants.CHROME);
		browser.navigateURL(
				propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
		login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
				propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
		navigationBar.selectOrganization(
				propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
		navigationBar.navigateToMenuItem(menuItem.get(0));
		foundation.waitforElement(GlobalProduct.TXT_FILTER,3);
		foundation.click(GlobalProduct.ICON_FILTER);
		globalProduct.selectFilter(requiredData.get(6));
		textBox.enterText(GlobalProduct.TXT_FILTER, requiredData.get(0));
		foundation.click(globalProduct.getGlobalProduct(requiredData.get(1)));
		dropdown.selectItem(ProductSummary.DPD_IS_DISABLED, requiredData.get(5), Constants.TEXT);
		foundation.click(ProductSummary.BTN_SAVE);	
		
		//reset data-assign back the product to location
		navigationBar.navigateToMenuItem(menuItem.get(1));
		textBox.enterText(LocationList.TXT_FILTER, requiredData.get(3));
		locationList.selectLocationName(requiredData.get(3));
		locationSummary.selectTab(requiredData.get(7));
		foundation.waitforElement(LocationSummary.BTN_ADD_PRODUCT,2);
		locationSummary.addProduct(requiredData.get(0));
		foundation.click(LocationSummary.BTN_FULL_SYNC);
	}
}
