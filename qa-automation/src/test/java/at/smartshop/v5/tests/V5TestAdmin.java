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
import at.smartshop.database.columns.CNNavigationMenu;
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
import at.smartshop.v5.pages.Payments;

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
	private Browser browser = new Browser();
	private LocationList locationList = new LocationList();
	private Campus campus = new Campus();
	private Dropdown dropdown = new Dropdown();
	private DateAndTime dateAndTime = new DateAndTime();
	private ConsumerSummary consumerSummary = new ConsumerSummary();

	private Map<String, String> rstNavigationMenuData;
	private Map<String, String> rstV5DeviceData;

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
	public void verifyCreatedAccountOnKiosk() {
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
	

}
