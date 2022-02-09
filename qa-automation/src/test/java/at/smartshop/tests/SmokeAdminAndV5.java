package at.smartshop.tests;

import static org.junit.Assert.assertFalse;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import at.framework.browser.Browser;
import at.framework.database.mssql.Queries;
import at.framework.database.mssql.ResultSets;
import at.framework.files.PropertyFile;
import at.framework.generic.DateAndTime;
import at.framework.generic.Strings;
import at.framework.ui.CheckBox;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.Table;
import at.framework.ui.TextBox;
import at.smartshop.database.columns.CNConsumerSearch;
import at.smartshop.database.columns.CNConsumerSummary;
import at.smartshop.database.columns.CNLocation;
import at.smartshop.database.columns.CNNavigationMenu;
import at.smartshop.database.columns.CNUserRoles;
import at.smartshop.database.columns.CNV5Device;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.pages.ConsumerSearch;
import at.smartshop.pages.ConsumerSummary;
import at.smartshop.pages.CreatePromotions;
import at.smartshop.pages.EditPromotion;
import at.smartshop.pages.GlobalProduct;
import at.smartshop.pages.LocationList;
import at.smartshop.pages.LocationSummary;
import at.smartshop.pages.NavigationBar;
import at.smartshop.pages.PromotionList;
import at.smartshop.pages.TaxList;
import at.smartshop.pages.TransactionSearchPage;
import at.smartshop.pages.UserList;
import at.smartshop.pages.UserRoles;
import at.smartshop.v5.pages.AccountLogin;
import at.smartshop.v5.pages.LandingPage;
import at.smartshop.v5.pages.Order;
import at.smartshop.v5.pages.PaymentSuccess;
import at.smartshop.v5.pages.ProductSearch;

public class SmokeAdminAndV5 extends TestInfra{
	private PropertyFile propertyFile = new PropertyFile();
	private ResultSets dataBase = new ResultSets();
	private Foundation foundation = new Foundation();
	private NavigationBar navigationBar = new NavigationBar();
	private CreatePromotions createPromotions = new CreatePromotions();
	private LocationList locationList = new LocationList();
	private Dropdown dropDown = new Dropdown();
	private TextBox textBox = new TextBox();
	private Strings strings = new Strings();
	private DateAndTime dateAndTime = new DateAndTime();
	private PromotionList promotionList = new PromotionList();
	private EditPromotion editPromotion = new EditPromotion();
	private UserRoles userRoles = new UserRoles();
	private UserList userList = new UserList();
	private CheckBox checkBox = new CheckBox();
	private Table table = new Table();
	private GlobalProduct globalProducts=new GlobalProduct();
	private LocationSummary locationSummary=new LocationSummary();
	private TaxList taxList=new TaxList();
	private LandingPage landingPage = new LandingPage();
	public Browser browser = new Browser();
	private ProductSearch productSearch=new ProductSearch();
	private Order order=new Order();
	private ConsumerSearch consumerSearch = new ConsumerSearch();
	private ConsumerSummary consumerSummary = new ConsumerSummary();
	private AccountLogin accountLogin=new AccountLogin(); 
	private TransactionSearchPage transactionSearchPage=new TransactionSearchPage();
	
	

	private Map<String, String> rstNavigationMenuData;
	private Map<String, String> rstLocationData;
	private Map<String, String> rstLocationListData;
	private Map<String, String> rstLocationSummaryData;
	private Map<String, String> rstUserRolesData;
	private Map<String, String> rstV5DeviceData;
	private Map<String, String> rstConsumerSearchData;
	private Map<String, String> rstConsumerSummaryData;

	@Test(description = "167028-Tax list - create and edit tax rate")
	public void createAndEditTaxRate() {
		final String CASE_NUM = "167028";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		
		List<String> menuItem = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> language = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
		List<String> requiredData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		String locationName=propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE);
		String productName=rstV5DeviceData.get(CNV5Device.PRODUCT_NAME);
		String taxCategory=requiredData.get(0);
		String taxRateName=requiredData.get(1);
		String taxRate1=requiredData.get(2);
		
		try {
			//launch and select org
			navigationBar.launchBrowserAsSuperAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			
			// Assign tax category to product
			globalProducts.assignTaxCategory(productName,taxCategory);

			// assign tax mapping - location summary
			locationSummary.navigateAndAddTaxMap(locationName,taxCategory, taxRateName);

			// edit tax rate OR update tax rate
			assertTrue(taxList.updateTaxRate(taxRateName, taxRate1, requiredData.get(3), requiredData.get(3), requiredData.get(3)));

			// login to application
			browser.close();
			landingPage.launchV5AndSelectLanguageEnglish();
			
			//search and add product to cart
			assertTrue(productSearch.searchProduct(productName));

			//verify the tax in order page
			order.verifyTax(taxRate1);
			browser.close();

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		finally {
			//reset data-- 
			//remove tax mapping
			browser.launch(Constants.LOCAL, Constants.CHROME);
			navigationBar.launchBrowserAsSuperAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			locationSummary.navigateAndRemoveTaxMap(locationName,taxCategory);
			
			//remove tax assignment from product
			globalProducts.removeTaxCategory(productName);
		}
	}
	
	@Test(description = "167029-User and roles- create user role assign it to user, edit it then verify disable ")
	public void createUserAndRoleEditDelete() {
		final String CASE_NUM = "167029";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstUserRolesData = dataBase.getUserRolesData(Queries.USER_ROLES, CASE_NUM);
		
		String menuItem = rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM);
		List<String> requiredData = Arrays
				.asList(rstUserRolesData.get(CNUserRoles.ROW_RECORD).split(Constants.DELIMITER_TILD));
		List<String> roleData = Arrays
				.asList(rstUserRolesData.get(CNUserRoles.ROLE_NAME).split(Constants.DELIMITER_TILD));
		String userEmail="";
		try {
			//launch and select org
			navigationBar.launchBrowserAsSuperAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			
			//create new user and add role as super
			navigationBar.navigateToMenuItem(menuItem);
			userEmail=userList.createNewUser(requiredData.get(0), requiredData.get(1), requiredData.get(2));
			userList.updateORAddUserRole(roleData.get(0));
			
			//logout and login with created user and verify role applied
			foundation.threadWait(Constants.TWO_SECOND);
			login.logout();
			login.login(userEmail,
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			//assertTrue(foundation.isDisplayed(NavigationBar.MENU_SUPER));
			
			//update user role to admin
			navigationBar.navigateToMenuItem(menuItem);
			userList.searchAndSelectUser(userEmail);
			userList.updateORAddUserRole(roleData.get(1));
			assertFalse(foundation.isDisplayed(NavigationBar.MENU_SUPER));
			
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		finally {
			//reset data
			login.logout();
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItem);
			userList.disableUser(userEmail);
		}
	}
	
	@Test(description = "167030-Verify consumer search")
	public void consumerSearch() {
		final String CASE_NUM = "167030";

		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstConsumerSearchData = dataBase.getConsumerSearchData(Queries.CONSUMER_SEARCH, CASE_NUM);
		String location = propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE);
		String menuItem = rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM);
		
		try {
			//launch and select org
			navigationBar.launchBrowserAsSuperAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			
			//search for consumer and verify the navigation to consumer summary
			navigationBar.navigateToMenuItem(menuItem);
			consumerSearch.enterSearchFields(rstConsumerSearchData.get(CNConsumerSearch.SEARCH_BY),
					rstConsumerSearchData.get(CNConsumerSearch.CONSUMER_ID), location,
					rstConsumerSearchData.get(CNConsumerSearch.STATUS));
			foundation.click(consumerSearch.objCell(rstConsumerSearchData.get(CNConsumerSearch.FIRST_NAME)));
			assertTrue(foundation.isDisplayed(ConsumerSummary.TXT_EMAIL));
			
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
	
	@Test(description = "167031-Consumer summary- create account, edit account, payout and close")
	public void verifyPayoutAndClose() {
		final String CASE_NUM = "167031";
		
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstConsumerSearchData = dataBase.getConsumerSearchData(Queries.CONSUMER_SEARCH, CASE_NUM);
		String location= propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE);
		
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			
			//search for consumer and verify the navigation to consumer summary
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			foundation.click(ConsumerSearch.BTN_CREATE_NEW);
			dropDown.selectItem(ConsumerSearch.DPD_LOCATION,location, Constants.TEXT);
			String emailID=consumerSearch.createConsumer(location);
			foundation.click(ConsumerSummary.BTN_SAVE);
			foundation.waitforElement(ConsumerSearch.TXT_CONSUMER_SEARCH, Constants.SHORT_TIME);
			foundation.waitforElementToDisappear(ConsumerSummary.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			
			//verify payout and close
			consumerSearch.enterSearchFields(rstConsumerSearchData.get(CNConsumerSearch.SEARCH_BY), emailID, location,
					rstConsumerSearchData.get(CNConsumerSearch.STATUS));
			foundation.click(consumerSearch.objCell(consumerSearch.getConsumerName()));
			foundation.click(ConsumerSummary.BTN_PAYOUT_CLOSE);
			foundation.alertAccept();
			foundation.waitforElementToDisappear(ConsumerSummary.TXT_SPINNER_MSG, Constants.SHORT_TIME);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
	
	@Test(description = "167033-Login - login by operator, driver, reporter, super , national account users")
	public void loginUsingDiffrentUsers() {
		final String CASE_NUM = "167033";
		
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstConsumerSearchData = dataBase.getConsumerSearchData(Queries.CONSUMER_SEARCH, CASE_NUM);
		String operator=propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE);
		String driver=propertyFile.readPropertyFile(Configuration.DRIVER_USER, FilePath.PROPERTY_CONFIG_FILE);
		String nationalAccount=propertyFile.readPropertyFile(Configuration.NATIONAL_ACCOUNT_USER, FilePath.PROPERTY_CONFIG_FILE);
		String masterNationalAccount=propertyFile.readPropertyFile(Configuration.MASTER_NATIONAL_ACCOUNT_USER, FilePath.PROPERTY_CONFIG_FILE);
		String reporter=propertyFile.readPropertyFile(Configuration.REPORTER_USER, FilePath.PROPERTY_CONFIG_FILE);
		String automationOrg=propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE);
		
		try {
			
			//launch and select org as reporter
			navigationBar.launchBrowserAndSelectOrg(reporter,automationOrg);
			login.logout();
			
			//launch and select org as master national account
			navigationBar.launchBrowserAndSelectOrg(masterNationalAccount,automationOrg);
			login.logout();
			
			//launch and select org as super
			navigationBar.launchBrowserAsSuperAndSelectOrg(automationOrg);
			login.logout();
			
			//launch and select org as admin/operator
			navigationBar.launchBrowserAndSelectOrg(operator,automationOrg);
			login.logout();
			
			//launch and select org as driver
			navigationBar.launchBrowserAndSelectOrg(driver,automationOrg);
			login.logout();
			
			//launch and select org as national account
			navigationBar.launchBrowserAndSelectOrg(nationalAccount,automationOrg);
			login.logout();			
			
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
	
	@Test(description = "167034-Adjust balance- adjust consumer balance and place order on kiosk to verify remaining balance")
	public void adjustBalance() {
		final String CASE_NUM = "167034";
		
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstConsumerSearchData = dataBase.getConsumerSearchData(Queries.CONSUMER_SEARCH, CASE_NUM);
		rstConsumerSummaryData = dataBase.getConsumerSummaryData(Queries.CONSUMER_SUMMARY, CASE_NUM);
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		String automationOrg=propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE);
		
		String firstName = rstConsumerSummaryData.get(CNConsumerSummary.FIRST_NAME);
		String columnName = rstConsumerSearchData.get(CNConsumerSearch.COLUMN_NAME);
		String dbBalance = rstConsumerSummaryData.get(CNConsumerSummary.ADJUST_BALANCE);
		String reasonCode=rstConsumerSummaryData.get(CNConsumerSummary.REASON);
		String productName=rstV5DeviceData.get(CNV5Device.PRODUCT_NAME);
		String consumer=propertyFile.readPropertyFile(Configuration.V5_USER, FilePath.PROPERTY_CONFIG_FILE);
		String location= propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE);
		try {
			// launch and select org as super and navigate to consumer search page
			navigationBar.launchBrowserAsSuperAndSelectOrg(automationOrg);
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// search a consumer and navigate to its summary page
			consumerSearch.enterSearchFields(rstConsumerSearchData.get(CNConsumerSearch.SEARCH_BY),
					consumer,
					location,
					rstConsumerSearchData.get(CNConsumerSearch.STATUS));
			foundation.click(consumerSearch.objCell(consumerSearch.getConsumerName()));
			//update balance
			consumerSummary.adjustBalance(dbBalance, reasonCode);
			
			//verify updated balance displays on search page
			consumerSearch.enterSearchFields(rstConsumerSearchData.get(CNConsumerSearch.SEARCH_BY),
					consumer,
					location,
					rstConsumerSearchData.get(CNConsumerSearch.STATUS));
			String balanceSearchPage = consumerSearch.getBalance(location);
			assertEquals(balanceSearchPage, dbBalance);
			
			//increment balance
			String incrementedBalance=String.valueOf(String.format("%.2f", (Double.parseDouble(dbBalance) + 2)));
			foundation.click(consumerSearch.objCell(consumerSearch.getConsumerName()));
			consumerSummary.adjustBalance(incrementedBalance, reasonCode);
			
			//verify balance increment
			consumerSearch.enterSearchFields(rstConsumerSearchData.get(CNConsumerSearch.SEARCH_BY),
					consumer,
					location,
					rstConsumerSearchData.get(CNConsumerSearch.STATUS));
			String balanceIncrementSearchPage = consumerSearch.getBalance(location);
			assertEquals(balanceIncrementSearchPage, incrementedBalance);
			
			// place order in v5 using the above consumer
			landingPage.launchV5AndSelectLanguageEnglish();
			assertTrue(productSearch.searchProduct(productName));
			String subTotal=order.getSubtotal();
			accountLogin.login(propertyFile.readPropertyFile(Configuration.V5_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.V5_PIN, FilePath.PROPERTY_CONFIG_FILE));
			foundation.waitforElement(PaymentSuccess.BTN_YES, Constants.EXTRA_LONG_TIME);
			assertTrue(foundation.isDisplayed(PaymentSuccess.BTN_YES));
			browser.close();
			
			//login back to adm and confirm balance updated accordingly
			navigationBar.launchBrowserAsSuperAndSelectOrg(automationOrg);
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			consumerSearch.enterSearchFields(rstConsumerSearchData.get(CNConsumerSearch.SEARCH_BY),
					consumer,
					location,
					rstConsumerSearchData.get(CNConsumerSearch.STATUS));
			Double actualBalanceUpdatedAfterOrder = Double.parseDouble(consumerSearch.getBalance(location));
			Double expectedbalanceUpdatedAfterOrder=Double.parseDouble(incrementedBalance)-Double.parseDouble(subTotal);
			assertEquals(actualBalanceUpdatedAfterOrder,expectedbalanceUpdatedAfterOrder);
			
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
	
	@Test(description = "167027-Transaction search-Place order via kiosk and verify the transaction created under transaction search")
	public void transactionSearch() {
		final String CASE_NUM = "167027";
		
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstConsumerSearchData = dataBase.getConsumerSearchData(Queries.CONSUMER_SEARCH, CASE_NUM);
		rstConsumerSummaryData = dataBase.getConsumerSummaryData(Queries.CONSUMER_SUMMARY, CASE_NUM);
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		String automationOrg=propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE);
		
//		String firstName = rstConsumerSummaryData.get(CNConsumerSummary.FIRST_NAME);
//		String columnName = rstConsumerSearchData.get(CNConsumerSearch.COLUMN_NAME);
//		String dbBalance = rstConsumerSummaryData.get(CNConsumerSummary.ADJUST_BALANCE);
//		String reasonCode=rstConsumerSummaryData.get(CNConsumerSummary.REASON);
		String location=propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE);
		String productName=rstV5DeviceData.get(CNV5Device.PRODUCT_NAME);
		try {
			// place order in v5 using the above consumer
//			browser.close();
//			landingPage.launchV5AndSelectLanguageEnglish();
//			assertTrue(productSearch.searchProduct(productName));
//			String subTotal=order.getSubtotal();
//			foundation.click(Order.LBL_EMAIL);
//			if(foundation.isDisplayed(Order.BTN_EMAIL_LOGIN))
//				foundation.click(Order.BTN_EMAIL_LOGIN);
//			accountLogin.login(propertyFile.readPropertyFile(Configuration.V5_USER, FilePath.PROPERTY_CONFIG_FILE),
//					propertyFile.readPropertyFile(Configuration.V5_PIN, FilePath.PROPERTY_CONFIG_FILE));
//			foundation.waitforElement(PaymentSuccess.BTN_YES, Constants.EXTRA_LONG_TIME);
//			assertTrue(foundation.isDisplayed(PaymentSuccess.BTN_YES));
//			browser.close();
			
			//login back to adm and confirm balance updated accordingly
			navigationBar.launchBrowserAsSuperAndSelectOrg(automationOrg);
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			transactionSearchPage.selectTransactionID(Constants.TODAY, location, "0.10");
			transactionSearchPage.verifyTransactionDetails("$0.10", "ACCOUNT - SCANCODE", productName);
			
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
	
	
	
	
}
