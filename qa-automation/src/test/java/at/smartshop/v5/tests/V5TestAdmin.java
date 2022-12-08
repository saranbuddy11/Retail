package at.smartshop.v5.tests;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import at.framework.browser.Browser;
import at.framework.database.mssql.Queries;
import at.framework.database.mssql.ResultSets;
import at.framework.generic.CustomisedAssert;
import at.framework.generic.DateAndTime;
import at.framework.ui.CheckBox;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.database.columns.CNConsumerSearch;
import at.smartshop.database.columns.CNLocationList;
import at.smartshop.database.columns.CNNavigationMenu;
import at.smartshop.database.columns.CNProductSummary;
import at.smartshop.database.columns.CNV5Device;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.pages.Campus;
import at.smartshop.pages.ConsumerSearch;
import at.smartshop.pages.ConsumerSummary;
import at.smartshop.pages.LocationList;
import at.smartshop.pages.LocationSummary;
import at.smartshop.pages.NavigationBar;
import at.smartshop.pages.TransactionSearchPage;
import at.smartshop.tests.TestInfra;
import at.smartshop.v5.pages.AccountLogin;
import at.smartshop.v5.pages.LandingPage;
import at.smartshop.v5.pages.PaymentSuccess;
import at.smartshop.v5.pages.Payments;
import at.smartshop.v5.pages.ProductSearch;

@Listeners(at.framework.reportsetup.Listeners.class)
public class V5TestAdmin extends TestInfra {
	private ResultSets dataBase = new ResultSets();
	private NavigationBar navigationBar = new NavigationBar();
	private Payments payments = new Payments();
	private TransactionSearchPage transactionSearchPage = new TransactionSearchPage();
	private ConsumerSearch consumerSearch = new ConsumerSearch();
	private Foundation foundation = new Foundation();
	private CheckBox checkBox = new CheckBox();
	private TextBox textBox = new TextBox();
	private AccountLogin accountLogin = new AccountLogin();
	private Browser browser = new Browser();
	private LandingPage landingPage = new LandingPage();
	private LocationSummary locationSummary = new LocationSummary();
	private LocationList locationList = new LocationList();
	private Campus campus = new Campus();
	private PaymentSuccess paymentSuccess = new PaymentSuccess();
	private Dropdown dropdown = new Dropdown();
	private DateAndTime dateAndTime = new DateAndTime();
	private ConsumerSummary consumerSummary = new ConsumerSummary();

	private Map<String, String> rstNavigationMenuData;
	private Map<String, String> rstV5DeviceData;
	private Map<String, String> rstProductSummaryData;

	/**
	 * @author afrosean Date:06-10-2022
	 */
	@Test(description = "178410-ADM - Admin - Transaction - Transaction Search")
	public void verifyAdminTransaction() {
		final String CASE_NUM = "178410";

		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);

		List<String> requiredData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.ORDER_PAGE).split(Constants.DELIMITER_TILD));
		List<String> Datas = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION).split(Constants.DELIMITER_TILD));

		try {

			// Launch v5 device and do transaction
			payments.launchV5DeviceAndDoTransaction(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME),
					rstV5DeviceData.get(CNV5Device.EMAIL_ID), rstV5DeviceData.get(CNV5Device.PIN), requiredData.get(0),
					rstV5DeviceData.get(CNV5Device.PAYMENTS_PAGE));

			// Launch ADM as super user
			browser.launch(Constants.LOCAL, Constants.CHROME);
			navigationBar.launchBrowserAsSuperAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Navigate Admin > Transaction
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			transactionSearchPage.verifyTransactionSearch(Datas.get(2), Datas.get(0), Datas.get(1), Datas.get(3));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

//	@Test(description = "205036-ADM > Update inventory from adm and verify in kiosk inventory from driver login")
//	public void updateInventoryFromAdmAndVerrifyInKioskFromDriverlogin() {
//		final String CASE_NUM = "205036";
//
//		// Reading test data from DataBase
//		//rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
//		rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);
//		
//		List<String> values = Arrays
//				.asList(rstLocationSummaryData.get(CNLocationSummary.PRODUCT_NAME).split(Constants.DELIMITER_TILD));
//
//		try {
//			// Select Org & Menu
//			navigationBar.launchBrowserAsSuperAndSelectOrg(
//					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
//
//			// select Location
//			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));
//			locationList.selectLocationName(rstLocationSummaryData.get(CNLocationSummary.COUNTRY));
//
//			// Navigating to products tab & update min max values in grid
//			locationSummary.updateMinAndMaxValueInProductGrid(values.get(0), values.get(3), values.get(1));
//			browser.close();
//			
//			//Login v5 device and verify inventory
//			browser.launch(Constants.REMOTE, Constants.CHROME);
//			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
//			CustomisedAssert.assertTrue(foundation.isDisplayed(LandingPage.IMG_SEARCH_ICON));
//			
//		} catch (Exception exc) {
//			TestInfra.failWithScreenShot(exc.toString());
//		}finally {
//			//resetting data
//			navigationBar.launchBrowserAsSuperAndSelectOrg(
//					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
//			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));
//			locationList.selectLocationName(rstLocationSummaryData.get(CNLocationSummary.COUNTRY));
//			locationSummary.updateMinAndMaxValueInProductGrid(values.get(0), values.get(3), values.get(2));
//			browser.close();		
//		}
//	}

	/**
	 * @author afrosean Date:14-11-2022
	 */
	@Test(description = "208792-V5 Kiosk - Create Account On Kiosk - Using Email")
	public void verifyCreatedAccountOnKioskReflectedInADM() {
		final String CASE_NUM = "208792";

		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);

		List<String> requiredData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> datas = Arrays.asList(rstV5DeviceData.get(CNV5Device.ACTUAL_DATA).split(Constants.DELIMITER_TILD));

		try {

			// Launch v5 device and create consumer
			payments.createAccountInV5Device(requiredData.get(0), requiredData.get(1), requiredData.get(2),
					requiredData.get(3));

			// Check same consumer is reflected in Adm or not
			browser.launch(Constants.LOCAL, Constants.CHROME);
			navigationBar.launchBrowserAsSuperAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			consumerSearch.verifyCreatedConsumerInConsumerPage(datas.get(0), datas.get(1), datas.get(2));

			// delete consumer
			foundation.click(ConsumerSummary.BTN_PAYOUT_CLOSE);
			foundation.alertAccept();
			foundation.waitforElementToDisappear(ConsumerSummary.TXT_SPINNER_MSG, Constants.LONG_TIME);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * @author afrosean Date:21-11-2022
	 */
	@Test(description = "208804- V5 Kiosk - PDE Purchase - Campus Location - Using Email")
	public void verifyPDEBalanceAfterTransaction() {
		final String CASE_NUM = "208804";

		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);

		List<String> requiredData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> menu = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> datas = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.PRODUCT_SEARCH_PAGE).split(Constants.DELIMITER_TILD));
		String currentDate = dateAndTime.getDateAndTime(Constants.REGEX_MM_DD_YYYY, Constants.TIME_ZONE_INDIA);

		try {

			// Login ADM & navigate to super > campus
			navigationBar.launchBrowserAsSuperAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));
			navigationBar.navigateToMenuItem(menu.get(0));

			// Search with created campus and adjust pde balance
			campus.searchWithCreatedCampusAndAdjustPDEBalanc(requiredData.get(0), requiredData.get(1), currentDate,
					requiredData.get(2), requiredData.get(3), requiredData.get(4));

			// Navigate to Admin > Consumer and make pde balance as Zero
			navigationBar.navigateToMenuItem(menu.get(2));
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSearch.TXT_CONSUMER_SEARCH));
			consumerSearch.enterSearchFields(datas.get(0), datas.get(1), datas.get(2), datas.get(3));
			consumerSummary.searchWithConsumerAndChangePDEBalance(requiredData.get(11), requiredData.get(10));

			// Remove device from location
			foundation.threadWait(Constants.SHORT_TIME);
			locationList.removeDevice(menu.get(1), requiredData.get(8));

			// deploy device in new location
			foundation.threadWait(Constants.THREE_SECOND);
			locationList.deployDevice(requiredData.get(5), requiredData.get(7));

			// Launch v5 device and do transaction
			browser.close();
			payments.v5Transaction(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME),
					rstV5DeviceData.get(CNV5Device.EMAIL_ID), rstV5DeviceData.get(CNV5Device.PIN));

			// Login to ADM as super
			browser.close();
			browser.launch(Constants.LOCAL, Constants.CHROME);
			navigationBar.launchBrowserAsSuperAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Navigate to Admin > Consumer and Seach with consumer
			navigationBar.navigateToMenuItem(menu.get(2));
			foundation.threadWait(Constants.THREE_SECOND);
			consumerSearch.enterSearchFields(datas.get(0), datas.get(1), datas.get(2), datas.get(3));

			// Navigate to consumer summary page and verify pde balance
			foundation.waitforElementToBeVisible(ConsumerSearch.LNK_FIRST_ROW, Constants.THREE_SECOND);
			foundation.click(ConsumerSearch.LNK_FIRST_ROW);
			foundation.waitforElementToBeVisible(ConsumerSummary.LBL_CONSUMER_SUMMARY, Constants.THREE_SECOND);
			String text = foundation.getText(ConsumerSummary.SUBSIDY_READ_BALANCE);
			CustomisedAssert.assertEquals(text, requiredData.get(9));
			foundation.threadWait(Constants.THREE_SECOND);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {

			// Remove device from location
			navigationBar.navigateToMenuItem(menu.get(1));
			foundation.threadWait(Constants.SHORT_TIME);
			locationList.removeDevice(menu.get(1), requiredData.get(5));

			// deploy device in new location
			foundation.threadWait(Constants.THREE_SECOND);
			locationList.deployDevice(requiredData.get(8), requiredData.get(7));
		}
	}

	/**
	 * @author afrosean Date:01-12-2022
	 */
	@Test(description = "208816-V5 Kiosk - Purchase uses balance in correct order (Subsidy > Consumer balance > PDE > Insufficient funds prompt) - Email")
	public void verifyPurchaseUsingSubsidyConsumerBalancePDEInOrderAndThenInsufficientBalancePageViaEmail() {
		final String CASE_NUM = "208816";

		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);

		List<String> menu = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> requiredData = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION).split(Constants.DELIMITER_TILD));
		List<String> datas = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		String currentDate = dateAndTime.getDateAndTime(Constants.REGEX_MM_DD_YYYY, Constants.TIME_ZONE_INDIA);

		try {
			// Login to ADM Application
			navigationBar.launchBrowserAsSuperAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menu.get(0));
			locationList.selectLocationName(requiredData.get(0));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.LBL_LOCATION_SUMMARY));

			// Enable GMA Subsidy and set topoff subsidy balance
			locationSummary.enableGMASubsidyAndUpdateTopOffBalance(requiredData.get(1), currentDate,
					requiredData.get(2), requiredData.get(3), requiredData.get(4), requiredData.get(5));

			// Navigate to Consumer Summary page and update all balance
			navigationBar.navigateToMenuItem(menu.get(1));
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSearch.TXT_CONSUMER_SEARCH));
			consumerSearch.enterSearchField(datas.get(0), datas.get(1), datas.get(2));
			foundation.waitforElementToBeVisible(ConsumerSearch.LNK_FIRST_ROW, Constants.SHORT_TIME);
			foundation.click(ConsumerSearch.LNK_FIRST_ROW);
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSummary.LBL_CONSUMER_SUMMARY));

			// Update Consumer account balance
			foundation.waitforElementToBeVisible(ConsumerSummary.BTN_ADJUST, Constants.SHORT_TIME);
			consumerSummary.adjustBalanceInAllAccount(ConsumerSummary.BTN_ADJUST, datas.get(3), datas.get(4));

			// update Pde balance
			foundation.waitforElementToBeVisible(ConsumerSummary.LBL_CONSUMER_SUMMARY, Constants.SHORT_TIME);
			consumerSummary.adjustBalanceInAllAccount(ConsumerSummary.SUBSIDY_ADJUST, datas.get(3), datas.get(4));
			foundation.waitforElementToBeVisible(ConsumerSummary.BTN_SAVE, Constants.SHORT_TIME);
			foundation.click(ConsumerSummary.BTN_SAVE);
			foundation.threadWait(Constants.THREE_SECOND);

			// Navigate to location summary page and click on full sync
			navigationBar.navigateToMenuItem(menu.get(0));
			locationList.selectLocationName(requiredData.get(0));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.LBL_LOCATION_SUMMARY));
			foundation.waitforElementToBeVisible(LocationSummary.BTN_FULL_SYNC, Constants.THREE_SECOND);
			foundation.click(LocationSummary.BTN_FULL_SYNC);
			foundation.threadWait(Constants.SHORT_TIME);

			// Login to V5 Device and verify balance
			browser.close();
			foundation.threadWait(Constants.THREE_SECOND);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));

			// Verify account balance in v5
			accountLogin.verifyAccountBalanceInV5(datas.get(1), rstV5DeviceData.get(CNV5Device.PIN), datas.get(5),
					datas.get(5));

			// Transaction using subsidy balance
			landingPage.transactionInV5Device(datas.get(6), datas.get(1), rstV5DeviceData.get(CNV5Device.PIN));

			// verify subsidy balance got decreased
			accountLogin.verifyAccountBalanceInV5(datas.get(1), rstV5DeviceData.get(CNV5Device.PIN), datas.get(5),
					datas.get(7));

			// Transaction using Consumer Account balance
			landingPage.transactionInV5Device(datas.get(6), datas.get(1), rstV5DeviceData.get(CNV5Device.PIN));

			// verify subsidy balance got decreased
			accountLogin.verifyAccountBalanceInV5(datas.get(1), rstV5DeviceData.get(CNV5Device.PIN), datas.get(7),
					datas.get(7));

			// Transaction using pde Account balance
			landingPage.transactionInV5Device(datas.get(6), datas.get(1), rstV5DeviceData.get(CNV5Device.PIN));

			// Transaction for insufficient balance
			landingPage.transactionInV5Device(datas.get(6), datas.get(1), rstV5DeviceData.get(CNV5Device.PIN));
			CustomisedAssert.assertTrue(foundation.isDisplayed(Payments.LBL_INSUFFICIENT_FUND));
			foundation.waitforElementToBeVisible(Payments.LBL_INSUFFICIENT_FUND, Constants.SHORT_TIME);
			String text = foundation.getText(Payments.LBL_INSUFFICIENT_FUND);
			CustomisedAssert.assertEquals(text, datas.get(8));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// resetting test data
			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.LOCAL, Constants.CHROME);
			navigationBar.launchBrowserAsSuperAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			locationSummary.subsidyResettingValidationOff(menu.get(0), requiredData.get(0), requiredData.get(6));
		}

	}

	/**
	 * @author afrosean Date:05-12-2022
	 */
	@Test(description = "208808-V5 Kiosk - PDE Purchase -Non- Campus Location - Using Email")
	public void verifyPDEBalanceAfterTransactionInNonCampusLocation() {
		final String CASE_NUM = "208808";

		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);

		List<String> requiredData = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION).split(Constants.DELIMITER_TILD));
		List<String> menu = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> datas = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		String currentDate = dateAndTime.getDateAndTime(Constants.REGEX_MM_DD_YYYY, Constants.TIME_ZONE_INDIA);

		try {

			// Login ADM & navigate to Location
			navigationBar.launchBrowserAsSuperAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));
			navigationBar.navigateToMenuItem(menu.get(0));
			locationList.selectLocationName(requiredData.get(4));

			// Set Payroll "on" and save in location summary page
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.LBL_LOCATION_SUMMARY));
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(LocationSummary.DATE_PICKER_PAY_ROLL);
			locationSummary.verifyRollOverDateAutoLocation1(currentDate);
			foundation.click(LocationSummary.TXT_PAYROLL);
			dropdown.selectItem(LocationSummary.DPD_PAY_CYCLE_RECURRENCE, requiredData.get(0), Constants.TEXT);
			textBox.enterText(LocationSummary.TXT_PAY_CYCLE_GROUP_NAME, requiredData.get(1));
			textBox.enterText(LocationSummary.TXT_PAY_ROLL_SPEND_LIMIT, requiredData.get(2));
			foundation.waitforElementToBeVisible(LocationSummary.BTN_SAVE, Constants.SHORT_TIME);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.threadWait(Constants.SHORT_TIME);

			// Navigate to Consumer summary page and set payroll detect
			navigationBar.navigateToMenuItem(menu.get(1));
			foundation.waitforElementToBeVisible(ConsumerSearch.TXT_CONSUMER_SEARCH, Constants.SHORT_TIME);
			consumerSearch.enterSearchField(datas.get(0), datas.get(1), datas.get(2));
			foundation.waitforElementToBeVisible(ConsumerSearch.LNK_FIRST_ROW, Constants.SHORT_TIME);
			foundation.click(ConsumerSearch.LNK_FIRST_ROW);
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSummary.LBL_CONSUMER_SUMMARY));

			// Update Consumer account balance
			foundation.waitforElementToBeVisible(ConsumerSummary.BTN_ADJUST, Constants.SHORT_TIME);
			consumerSummary.adjustBalanceInAllAccount(ConsumerSummary.BTN_ADJUST, datas.get(3), datas.get(4));

			// Update subsidy balance
			foundation.waitforElementToBeVisible(ConsumerSummary.LBL_CONSUMER_SUMMARY, Constants.SHORT_TIME);
			consumerSummary.adjustBalanceInAllAccount(ConsumerSummary.SUBSIDY_ADJUST, datas.get(3), datas.get(4));

			// update "PDE" balance
			foundation.waitforElementToBeVisible(ConsumerSummary.PAYROLL_BTN_ADJUST, Constants.SHORT_TIME);
			consumerSummary.adjustBalanceInAllAccount(ConsumerSummary.PAYROLL_BTN_ADJUST, datas.get(3), datas.get(4));
			foundation.waitforElementToBeVisible(ConsumerSummary.BTN_SAVE, Constants.THREE_SECOND);
			foundation.click(ConsumerSummary.BTN_SAVE);
			foundation.threadWait(Constants.SHORT_TIME);

			// Navigate to location summary page and click on full sync
			navigationBar.navigateToMenuItem(menu.get(0));
			locationList.selectLocationName(requiredData.get(4));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.LBL_LOCATION_SUMMARY));
			foundation.waitforElementToBeVisible(LocationSummary.BTN_FULL_SYNC, Constants.THREE_SECOND);
			foundation.click(LocationSummary.BTN_FULL_SYNC);
			foundation.threadWait(Constants.SHORT_TIME);

			// Login to V5 Device and verify balance
			browser.close();
			foundation.threadWait(Constants.THREE_SECOND);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));

			// Transaction using "PDE" Account balance
			landingPage.transactionInV5Device(requiredData.get(5), datas.get(1), requiredData.get(6));

			// Login to ADM
			browser.launch(Constants.LOCAL, Constants.CHROME);
			navigationBar.launchBrowserAsSuperAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menu.get(1));

			// Search with consumer and navigate to consumer summary page
			foundation.waitforElementToBeVisible(ConsumerSearch.TXT_CONSUMER_SEARCH, Constants.SHORT_TIME);
			consumerSearch.enterSearchField(datas.get(0), datas.get(1), datas.get(2));
			foundation.waitforElementToBeVisible(ConsumerSearch.LNK_FIRST_ROW, Constants.SHORT_TIME);
			foundation.click(ConsumerSearch.LNK_FIRST_ROW);
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSummary.LBL_CONSUMER_SUMMARY));

			// verify PDE purchase is updated in consumer summary page or not
			foundation.waitforElementToBeVisible(ConsumerSummary.BTN_ADJUST, Constants.SHORT_TIME);
			String text = foundation.getText(ConsumerSummary.PDE_BALANCE_READ);
			CustomisedAssert.assertEquals(text, requiredData.get(3));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * @author afrosean
	 * Date:08-12-2022
	 */
	@Test(description = "208774-V5 Kiosk - Account purchase - Using Fingerprint")
	public void verifyFingerPrintTransaction() {
		final String CASE_NUM = "208774";

		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);

		List<String> requiredData = Arrays
				.asList(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME).split(Constants.DELIMITER_TILD));
		List<String> data = Arrays
				.asList(rstProductSummaryData.get(CNProductSummary.PRICE).split(Constants.DELIMITER_TILD));

		try {
			// Process Sales API data
			paymentSuccess.processAPI(rstProductSummaryData.get(CNProductSummary.ACTUAL_DATA),
					rstProductSummaryData.get(CNProductSummary.REQUIRED_DATA),
					rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));

			// Login ADM & navigate to Location
			navigationBar.launchBrowserAsSuperAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Search with consumer and navigate to consumer summary page
			foundation.waitforElementToBeVisible(ConsumerSearch.TXT_CONSUMER_SEARCH, Constants.SHORT_TIME);
			consumerSearch.enterSearchField(requiredData.get(0), requiredData.get(1), requiredData.get(2));
			foundation.waitforElementToBeVisible(ConsumerSearch.LNK_FIRST_ROW, Constants.SHORT_TIME);
			foundation.click(ConsumerSearch.LNK_FIRST_ROW);
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSummary.LBL_CONSUMER_SUMMARY));

			// verify transaction data
			foundation.waitforElementToBeVisible(ConsumerSummary.LBL_BALANCE_HISTORY, Constants.SHORT_TIME);
			foundation.refreshPage();
			foundation.click(ConsumerSummary.FIRST_RECORD);
			foundation.threadWait(Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSummary.TRANSACTION_POPTUP));
			foundation.waitforElementToBeVisible(ConsumerSummary.ITEM_DETAILS, Constants.THREE_SECOND);
			String text = foundation.getText(ConsumerSummary.ITEM_DETAILS);
			CustomisedAssert.assertTrue(text.contains(data.get(0)));
			CustomisedAssert.assertTrue(text.contains(data.get(1)));
			foundation.threadWait(Constants.THREE_SECOND);
			CustomisedAssert.assertTrue(text.contains(data.get(2)));
			CustomisedAssert.assertTrue(text.contains(data.get(3)));
			

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
}
