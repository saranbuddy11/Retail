package at.smartshop.tests;

import static org.junit.Assert.assertFalse;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import at.framework.database.mssql.Queries;
import at.framework.database.mssql.ResultSets;
import at.framework.files.Excel;
import at.framework.files.PropertyFile;
import at.framework.generic.Numbers;
import at.framework.generic.Strings;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.Table;
import at.framework.ui.TextBox;
import at.smartshop.database.columns.CNConsumer;
import at.smartshop.database.columns.CNConsumerSearch;
import at.smartshop.database.columns.CNConsumerSummary;
import at.smartshop.database.columns.CNLocationList;
import at.smartshop.database.columns.CNLocationSummary;
import at.smartshop.database.columns.CNNavigationMenu;
import at.smartshop.database.columns.CNOrgSummary;
import at.smartshop.database.columns.CNProductSummary;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.pages.ConsumerMove;
import at.smartshop.pages.ConsumerMoveHistory;
import at.smartshop.pages.ConsumerSearch;
import at.smartshop.pages.ConsumerSummary;
import at.smartshop.pages.LocationSummary;
import at.smartshop.pages.NavigationBar;
import at.smartshop.pages.OrgSummary;

@Listeners(at.framework.reportsetup.Listeners.class)
public class Consumer extends TestInfra {

	private ResultSets dataBase = new ResultSets();
	private NavigationBar navigationBar = new NavigationBar();
	private PropertyFile propertyFile = new PropertyFile();
	private ConsumerSearch consumerSearch = new ConsumerSearch();
	private Dropdown dropDown = new Dropdown();
	private Foundation foundation = new Foundation();
	private TextBox textBox = new TextBox();
	private ConsumerSummary consumerSummary = new ConsumerSummary();
	private Strings strings = new Strings();
	private Numbers numbers = new Numbers();
	private Table table = new Table();
	private LocationSummary locationSummary=new LocationSummary();
	private Excel excel=new Excel();
	private ConsumerMove consumerMove=new ConsumerMove();
	
	private Map<String, String> rstNavigationMenuData;
	private Map<String, String> rstConsumerSearchData;
	private Map<String, String> rstConsumerSummaryData;
	private Map<String, String> rstProductSummaryData;
	private Map<String, String> rstConsumerData;
	private Map<String, String> rstOrgSummaryData;
	private Map<String, String> rstLocationSummaryData;
	private Map<String, String> rstLocationListData;

	@Test(description = "116743-Verify Balance Increment with and without Reason Code")
	public void verifyBalanceIncrement() {
		final String CASE_NUM = "116743";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstConsumerSearchData = dataBase.getConsumerSearchData(Queries.CONSUMER_SEARCH, CASE_NUM);
		rstConsumerSummaryData = dataBase.getConsumerSummaryData(Queries.CONSUMER_SUMMARY, CASE_NUM);

		String firstName = rstConsumerSummaryData.get(CNConsumerSummary.FIRST_NAME);
		String columnName = rstConsumerSearchData.get(CNConsumerSearch.COLUMN_NAME);
		String dbBalance = rstConsumerSummaryData.get(CNConsumerSummary.ADJUST_BALANCE);
		try {

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Enter fields in Consumer Search Page
			consumerSearch.enterSearchFields(rstConsumerSearchData.get(CNConsumerSearch.SEARCH_BY),
					rstConsumerSearchData.get(CNConsumerSearch.CONSUMER_ID),
					rstConsumerSearchData.get(CNConsumerSearch.LOCATION),
					rstConsumerSearchData.get(CNConsumerSearch.STATUS));

			// clicking consumer id
			foundation.click(consumerSearch.objCell(firstName));
			foundation.click(ConsumerSearch.BTN_ADJUST);

			// enter new balance with reason
			textBox.enterText(ConsumerSummary.TXT_ADJUST_BALANCE, dbBalance);
			foundation.click(ConsumerSummary.BTN_REASON_SAVE);
			foundation.click(ConsumerSummary.BTN_SAVE);
			foundation.waitforElement(ConsumerSearch.DPD_SEARCH_BY, Constants.SHORT_TIME);
			// Enter fields in Consumer Search Page
			consumerSearch.enterSearchFields(rstConsumerSearchData.get(CNConsumerSearch.SEARCH_BY),
					rstConsumerSearchData.get(CNConsumerSearch.CONSUMER_ID),
					rstConsumerSearchData.get(CNConsumerSearch.LOCATION),
					rstConsumerSearchData.get(CNConsumerSearch.STATUS));
			Map<String, String> consumerTblRecords = consumerSearch
					.getConsumerRecords(rstConsumerSearchData.get(CNConsumerSearch.LOCATION));
			String balance = consumerTblRecords.get(columnName);
			String balance1 = balance.substring(1);
			Double newBalance = Double.parseDouble(balance1) + 2;
			String expectedBalance = "$" + String.valueOf(String.format("%.2f", newBalance));

			// clicking consumer id
			foundation.click(consumerSearch.objCell(firstName));
			foundation.click(ConsumerSearch.BTN_ADJUST);

			// enter new balance with reason
			textBox.enterText(ConsumerSummary.TXT_ADJUST_BALANCE, String.valueOf(newBalance));
			dropDown.selectItem(ConsumerSummary.DPD_REASON, rstConsumerSummaryData.get(CNConsumerSummary.REASON),
					Constants.TEXT);
			foundation.click(ConsumerSummary.BTN_REASON_SAVE);
			foundation.click(ConsumerSummary.BTN_SAVE);

			// Enter fields in Consumer Search Page
			consumerSearch.enterSearchFields(rstConsumerSearchData.get(CNConsumerSearch.SEARCH_BY),
					rstConsumerSearchData.get(CNConsumerSearch.CONSUMER_ID),
					rstConsumerSearchData.get(CNConsumerSearch.LOCATION),
					rstConsumerSearchData.get(CNConsumerSearch.STATUS));

			Map<String, String> consumerTblRecords2 = consumerSearch
					.getConsumerRecords(rstConsumerSearchData.get(CNConsumerSearch.LOCATION));
			String actualBalance = consumerTblRecords2.get(columnName);
			Assert.assertEquals(actualBalance, expectedBalance);

			// enter new balance with out reason
			String actualBalance1 = actualBalance.substring(1);
			Double newBalance2 = Double.parseDouble(actualBalance1) + 2;
			String expectedBalance2 = "$" + String.valueOf(String.format("%.2f", newBalance2));

			// clicking consumer id
			foundation.click(consumerSearch.objCell(firstName));
			foundation.click(ConsumerSearch.BTN_ADJUST);

			// enter new balance with reason
			textBox.enterText(ConsumerSummary.TXT_ADJUST_BALANCE, String.valueOf(newBalance2));
			foundation.click(ConsumerSummary.BTN_REASON_SAVE);
			foundation.click(ConsumerSummary.BTN_SAVE);

			// Enter fields in Consumer Search Page
			consumerSearch.enterSearchFields(rstConsumerSearchData.get(CNConsumerSearch.SEARCH_BY),
					rstConsumerSearchData.get(CNConsumerSearch.CONSUMER_ID),
					rstConsumerSearchData.get(CNConsumerSearch.LOCATION),
					rstConsumerSearchData.get(CNConsumerSearch.STATUS));

			Map<String, String> consumerTblRecords3 = consumerSearch
					.getConsumerRecords(rstConsumerSearchData.get(CNConsumerSearch.LOCATION));
			String actualBalance2 = consumerTblRecords3.get(columnName);
			Assert.assertEquals(actualBalance2, expectedBalance2);

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// clicking consumer id
			foundation.click(consumerSearch.objCell(firstName));
			foundation.click(ConsumerSearch.BTN_ADJUST);

			// enter new balance with reason
			textBox.enterText(ConsumerSummary.TXT_ADJUST_BALANCE, dbBalance);
			foundation.click(ConsumerSummary.BTN_REASON_SAVE);
			foundation.click(ConsumerSummary.BTN_SAVE);

		}
	}

	@Test(description = "116747-Cancel Adjust Balance")
	public void verifyCancelAdjustBalance() {
		try {
			final String CASE_NUM = "116747";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstConsumerSearchData = dataBase.getConsumerSearchData(Queries.CONSUMER_SEARCH, CASE_NUM);
			rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);
			rstConsumerSummaryData = dataBase.getConsumerSummaryData(Queries.CONSUMER_SUMMARY, CASE_NUM);

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Enter fields in Consumer Search Page
			consumerSearch.enterSearchFields(rstConsumerSearchData.get(CNConsumerSearch.SEARCH_BY),
					rstConsumerSearchData.get(CNConsumerSearch.CONSUMER_ID),
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE),
					rstConsumerSearchData.get(CNConsumerSearch.STATUS));

			// Split database data
			List<String> requiredData = Arrays
					.asList(rstProductSummaryData.get(CNProductSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			foundation.click(consumerSearch.objCell(requiredData.get(5)));

			// reading Balance and add to the array list
			double initialbalance = consumerSummary.getBalance();
			foundation.click(ConsumerSummary.BTN_ADJUST);

			// converting string to double and adding the adjusted value
			double updatedbalance = initialbalance
					+ Double.parseDouble(rstConsumerSummaryData.get(CNConsumerSummary.ADJUST_BALANCE));
			textBox.enterText(ConsumerSummary.TXT_ADJUST_BALANCE, Double.toString(updatedbalance));
			dropDown.selectItem(ConsumerSummary.DPD_REASON, rstConsumerSummaryData.get(CNConsumerSummary.REASON),
					Constants.TEXT);
			foundation.click(ConsumerSummary.BTN_REASON_CANCEL);
			double balanceAfterCancel = consumerSummary.getBalance();
			assertEquals(balanceAfterCancel, initialbalance);

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "143570-QAA-23-verify consumer account is created for all the countries available in country dropdown in org summary page.")
	public void verifyConsumerAccount() {
		try {
			final String CASE_NUM = "143570";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from database
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstConsumerData = dataBase.getConsumerData(Queries.CONSUMER, CASE_NUM);
			rstOrgSummaryData = dataBase.getOrgSummaryData(Queries.ORG_SUMMARY, CASE_NUM);

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			List<String> menuItem = Arrays
					.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
			List<String> requiredData = Arrays
					.asList(rstOrgSummaryData.get(CNOrgSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			navigationBar.navigateToMenuItem(menuItem.get(0));
			List<String> countries = dropDown.getAllItems(OrgSummary.DPD_COUNTRY);
			countries.remove(requiredData.get(0));
			countries.remove(requiredData.get(1));

			for (int i = 0; i < countries.size(); i++) {

				navigationBar.navigateToMenuItem(menuItem.get(0));
				foundation.waitforElement(OrgSummary.DPD_COUNTRY, Constants.SHORT_TIME);
				dropDown.selectItem(OrgSummary.DPD_COUNTRY, countries.get(i), Constants.TEXT);

				textBox.enterText(OrgSummary.TXT_CONTACT, requiredData.get(3));
				textBox.enterText(OrgSummary.TXT_ADDRESS, requiredData.get(3));
				foundation.threadWait(Constants.ONE_SECOND);
				if ((requiredData.get(1)).equals(countries.get(i))) {
					foundation.waitforElement(OrgSummary.TXT_ABN, Constants.SHORT_TIME);
					textBox.enterText(OrgSummary.TXT_ABN, requiredData.get(2));
				}
				foundation.click(OrgSummary.BTN_SAVE);
				foundation.waitforElement(OrgSummary.TXT_SPINNER_MSG, Constants.SHORT_TIME);
				navigationBar.navigateToMenuItem(menuItem.get(1));
				foundation.click(ConsumerSearch.BTN_CREATE);
				dropDown.selectItem(ConsumerSummary.DPD_LOCATION, rstConsumerData.get(CNConsumer.LOCATION),
						Constants.TEXT);
				textBox.enterText(ConsumerSummary.TXT_FIRSTNAME, Constants.AUTOMATION + strings.getRandomCharacter());
				textBox.enterText(ConsumerSummary.TXT_LASTNAME, Constants.AUTOMATION + strings.getRandomCharacter());
				textBox.enterText(ConsumerSummary.TXT_EMAIL,
						strings.getRandomCharacter() + rstConsumerData.get(CNConsumer.EMAIL));
				textBox.enterText(ConsumerSummary.TXT_SCANID, String.valueOf(numbers.generateRandomNumber(0, 99999)));
				textBox.enterText(ConsumerSummary.TXT_PIN, rstConsumerData.get(CNConsumer.PIN));
				textBox.enterText(ConsumerSummary.TXT_PHONE, rstConsumerData.get(CNConsumer.PHONE));
				foundation.click(ConsumerSummary.BTN_CREATE);
				foundation.waitforElement(ConsumerSummary.TXT_SPINNER_MSG, Constants.SHORT_TIME);
				String actualData = foundation.getText(ConsumerSummary.TXT_SPINNER_MSG);
				Assert.assertEquals(actualData, rstConsumerData.get(CNConsumer.INFO_MSG));

			}

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "143624-QAA-23-verify when existing scan code id is provided for consumer creation ,error message \"Scan code already belongs to a GMA account\" is displayed.")
	public void verifyDuplicateScan() {
		try {
			final String CASE_NUM = "143624";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from database
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstConsumerData = dataBase.getConsumerData(Queries.CONSUMER, CASE_NUM);
			rstOrgSummaryData = dataBase.getOrgSummaryData(Queries.ORG_SUMMARY, CASE_NUM);

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			String menuItem = rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM);
			navigationBar.navigateToMenuItem(menuItem);
			String name = rstConsumerData.get(CNConsumer.FIRST_NAME);
			consumerSearch.enterSearchFields(rstConsumerData.get(CNConsumer.SEARCH_BY), name,
					rstConsumerData.get(CNConsumer.LOCATION), rstConsumerData.get(CNConsumer.STATUS));
			Map<String, String> tableData = table.getTblSingleRowRecordUI(ConsumerSearch.TBL_LOCATION,
					ConsumerSearch.TBL_ROW);
			foundation.click(ConsumerSearch.BTN_CREATE);
			dropDown.selectItem(ConsumerSummary.DPD_LOCATION, rstConsumerData.get(CNConsumer.LOCATION), Constants.TEXT);
			textBox.enterText(ConsumerSummary.TXT_FIRSTNAME, Constants.AUTOMATION + strings.getRandomCharacter());
			textBox.enterText(ConsumerSummary.TXT_LASTNAME, Constants.AUTOMATION + strings.getRandomCharacter());
			textBox.enterText(ConsumerSummary.TXT_SCANID, tableData.get(rstConsumerData.get(CNConsumer.COLUMN_NAME)));
			textBox.enterText(ConsumerSummary.TXT_PIN, rstConsumerData.get(CNConsumer.PIN));
			String actualData = foundation.getText(ConsumerSummary.TXT_SCANID_ERROR);
			Assert.assertEquals(actualData, rstConsumerData.get(CNConsumer.SCANID_ERROR));

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "143625-QAA-23-verify when existing email id is provided for consumer creation ,error message \"Email is invalid or already in use\" is displayed")
	public void verifyDuplicateEmail() {
		try {
			final String CASE_NUM = "143625";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from database
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstConsumerData = dataBase.getConsumerData(Queries.CONSUMER, CASE_NUM);

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			String menuItem = rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM);
			navigationBar.navigateToMenuItem(menuItem);
			String name = rstConsumerData.get(CNConsumer.LAST_NAME);
			consumerSearch.enterSearchFields(rstConsumerData.get(CNConsumer.SEARCH_BY), name,
					rstConsumerData.get(CNConsumer.LOCATION), rstConsumerData.get(CNConsumer.STATUS));
			Map<String, String> tableData = table.getTblSingleRowRecordUI(ConsumerSearch.TBL_LOCATION,
					ConsumerSearch.TBL_ROW);
			foundation.click(ConsumerSearch.BTN_CREATE);
			dropDown.selectItem(ConsumerSummary.DPD_LOCATION, rstConsumerData.get(CNConsumer.LOCATION), Constants.TEXT);
			textBox.enterText(ConsumerSummary.TXT_FIRSTNAME, Constants.AUTOMATION + strings.getRandomCharacter());
			textBox.enterText(ConsumerSummary.TXT_LASTNAME, Constants.AUTOMATION + strings.getRandomCharacter());
			textBox.enterText(ConsumerSummary.TXT_EMAIL, tableData.get(rstConsumerData.get(CNConsumer.COLUMN_NAME)));
			textBox.enterText(ConsumerSummary.TXT_PIN, rstConsumerData.get(CNConsumer.PIN));
			String actualData = foundation.getText(ConsumerSummary.TXT_EMAILID_ERROR);
			Assert.assertEquals(actualData, rstConsumerData.get(CNConsumer.EMAIL_ERROR));

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "143684-QAA-23-verify when invalid email id is provided for consumer creation ,error message \"Invalid Email Address\" is displayed")
	public void verifyInvalidEmail() {
		try {
			final String CASE_NUM = "143684";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from database
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstConsumerData = dataBase.getConsumerData(Queries.CONSUMER, CASE_NUM);

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			String menuItem = rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM);
			navigationBar.navigateToMenuItem(menuItem);

			List<String> emailId = Arrays.asList(rstConsumerData.get(CNConsumer.EMAIL).split(Constants.DELIMITER_TILD));
			foundation.click(ConsumerSearch.BTN_CREATE);
			for (int i = 0; i < emailId.size(); i++) {
				dropDown.selectItem(ConsumerSummary.DPD_LOCATION, rstConsumerData.get(CNConsumer.LOCATION),
						Constants.TEXT);
				textBox.enterText(ConsumerSummary.TXT_FIRSTNAME, Constants.AUTOMATION + strings.getRandomCharacter());
				textBox.enterText(ConsumerSummary.TXT_LASTNAME, Constants.AUTOMATION + strings.getRandomCharacter());
				textBox.enterText(ConsumerSummary.TXT_EMAIL, emailId.get(i));
				textBox.enterText(ConsumerSummary.TXT_PIN, rstConsumerData.get(CNConsumer.PIN));
				String actualData = foundation.getText(ConsumerSummary.TXT_EMAILID_ERROR);
				Assert.assertEquals(actualData, rstConsumerData.get(CNConsumer.EMAIL_ERROR));
			}

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "143718-QAA-23-verify when email id and scan id are not provided for consumer creation, error message \"Email or Scan ID required\" is displayed")
	public void verifyWhenNoEmail() {
		try {
			final String CASE_NUM = "143718";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from database
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstConsumerData = dataBase.getConsumerData(Queries.CONSUMER, CASE_NUM);

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			String menuItem = rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM);
			navigationBar.navigateToMenuItem(menuItem);

			foundation.click(ConsumerSearch.BTN_CREATE);
			dropDown.selectItem(ConsumerSummary.DPD_LOCATION, rstConsumerData.get(CNConsumer.LOCATION), Constants.TEXT);
			textBox.enterText(ConsumerSummary.TXT_FIRSTNAME, Constants.AUTOMATION + strings.getRandomCharacter());
			textBox.enterText(ConsumerSummary.TXT_LASTNAME, Constants.AUTOMATION + strings.getRandomCharacter());

			textBox.enterText(ConsumerSummary.TXT_PIN, rstConsumerData.get(CNConsumer.PIN));
			foundation.click(ConsumerSummary.BTN_CREATE);
			String actualData = foundation.getText(ConsumerSummary.TXT_EMAILID_ERROR);
			Assert.assertEquals(actualData, rstConsumerData.get(CNConsumer.EMAIL_ERROR));
			actualData = foundation.getText(ConsumerSummary.TXT_SCANID_ERROR);
			Assert.assertEquals(actualData, rstConsumerData.get(CNConsumer.SCANID_ERROR));

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "143719-QAA-23-verify when provided pin is greater than 4 digits for consumer creation, error message \"PIN must be exactly 4 digits.\" is displayed.")
	public void verifyPinLength() {
		try {
			final String CASE_NUM = "143719";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from database
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstConsumerData = dataBase.getConsumerData(Queries.CONSUMER, CASE_NUM);

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			String menuItem = rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM);
			navigationBar.navigateToMenuItem(menuItem);

			foundation.click(ConsumerSearch.BTN_CREATE);
			List<String> pins = Arrays.asList(rstConsumerData.get(CNConsumer.PIN).split(Constants.DELIMITER_TILD));

			for (int i = 0; i < pins.size(); i++) {
				dropDown.selectItem(ConsumerSummary.DPD_LOCATION, rstConsumerData.get(CNConsumer.LOCATION),
						Constants.TEXT);
				textBox.enterText(ConsumerSummary.TXT_FIRSTNAME, Constants.AUTOMATION + strings.getRandomCharacter());
				textBox.enterText(ConsumerSummary.TXT_LASTNAME, Constants.AUTOMATION + strings.getRandomCharacter());

				textBox.enterText(ConsumerSummary.TXT_SCANID, String.valueOf(numbers.generateRandomNumber(0, 99999)));
				textBox.enterText(ConsumerSummary.TXT_PIN, pins.get(i));
				textBox.enterText(ConsumerSummary.TXT_PHONE, rstConsumerData.get(CNConsumer.PHONE));

				String actualData = foundation.getText(ConsumerSummary.TXT_PIN_ERROR);
				Assert.assertEquals(actualData, rstConsumerData.get(CNConsumer.PIN_ERROR));
			}

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "143720-QAA-23-verify when location is not provided for consumer creation, error message \"This field is required.\" is displayed.")
	public void verifyWhenNoLocation() {
		try {
			final String CASE_NUM = "143720";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from database
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstConsumerData = dataBase.getConsumerData(Queries.CONSUMER, CASE_NUM);

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			String menuItem = rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM);
			navigationBar.navigateToMenuItem(menuItem);

			foundation.click(ConsumerSearch.BTN_CREATE);
			textBox.enterText(ConsumerSummary.TXT_FIRSTNAME, Constants.AUTOMATION + strings.getRandomCharacter());
			textBox.enterText(ConsumerSummary.TXT_LASTNAME, Constants.AUTOMATION + strings.getRandomCharacter());

			textBox.enterText(ConsumerSummary.TXT_SCANID, String.valueOf(numbers.generateRandomNumber(0, 99999)));
			textBox.enterText(ConsumerSummary.TXT_PIN, rstConsumerData.get(CNConsumer.PIN));
			textBox.enterText(ConsumerSummary.TXT_PHONE, rstConsumerData.get(CNConsumer.PHONE));
			foundation.click(ConsumerSummary.BTN_CREATE);

			String actualData = foundation.getText(ConsumerSummary.TXT_LOC_ERROR);
			Assert.assertEquals(actualData, rstConsumerData.get(CNConsumer.ERROR_MSG));
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "143721-QAA-23-verify when invalid pin is provided for consumer creation, error message \"Please enter only digits\" is displayed.")
	public void verifyInvalidPin() {
		try {
			final String CASE_NUM = "143721";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from database
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstConsumerData = dataBase.getConsumerData(Queries.CONSUMER, CASE_NUM);

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			String menuItem = rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM);
			navigationBar.navigateToMenuItem(menuItem);

			foundation.click(ConsumerSearch.BTN_CREATE);

			dropDown.selectItem(ConsumerSummary.DPD_LOCATION, rstConsumerData.get(CNConsumer.LOCATION), Constants.TEXT);
			textBox.enterText(ConsumerSummary.TXT_FIRSTNAME, Constants.AUTOMATION + strings.getRandomCharacter());
			textBox.enterText(ConsumerSummary.TXT_LASTNAME, Constants.AUTOMATION + strings.getRandomCharacter());

			textBox.enterText(ConsumerSummary.TXT_SCANID, String.valueOf(numbers.generateRandomNumber(0, 99999)));
			textBox.enterText(ConsumerSummary.TXT_PIN, rstConsumerData.get(CNConsumer.PIN));
			textBox.enterText(ConsumerSummary.TXT_PHONE, rstConsumerData.get(CNConsumer.PHONE));

			String actualData = foundation.getText(ConsumerSummary.TXT_PIN_ERROR);
			Assert.assertEquals(actualData, rstConsumerData.get(CNConsumer.PIN_ERROR));

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "145231-QAA-23-verify when pin is not provided for consumer creation,error message \"This field is required.\" is displayed.")
	public void verifyNoPin() {
		try {
			final String CASE_NUM = "145231";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from database
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstConsumerData = dataBase.getConsumerData(Queries.CONSUMER, CASE_NUM);

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			String menuItem = rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM);
			navigationBar.navigateToMenuItem(menuItem);

			foundation.click(ConsumerSearch.BTN_CREATE);

			dropDown.selectItem(ConsumerSummary.DPD_LOCATION, rstConsumerData.get(CNConsumer.LOCATION), Constants.TEXT);
			textBox.enterText(ConsumerSummary.TXT_FIRSTNAME, Constants.AUTOMATION + strings.getRandomCharacter());
			textBox.enterText(ConsumerSummary.TXT_LASTNAME, Constants.AUTOMATION + strings.getRandomCharacter());
			textBox.enterText(ConsumerSummary.TXT_SCANID, String.valueOf(numbers.generateRandomNumber(0, 99999)));

			textBox.enterText(ConsumerSummary.TXT_PHONE, rstConsumerData.get(CNConsumer.PHONE));
			foundation.click(ConsumerSummary.BTN_CREATE);
			String actualData = foundation.getText(ConsumerSummary.TXT_PIN_ERROR);
			Assert.assertEquals(actualData, rstConsumerData.get(CNConsumer.PIN_ERROR));

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "145229-QAA-23-verify when first name is not provided for consumer creation, error message \"This field is required.\" is displayed.")
	public void verifyFNField() {
		try {
			final String CASE_NUM = "145229";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from database
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstConsumerData = dataBase.getConsumerData(Queries.CONSUMER, CASE_NUM);

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			String menuItem = rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM);
			navigationBar.navigateToMenuItem(menuItem);

			foundation.click(ConsumerSearch.BTN_CREATE);

			dropDown.selectItem(ConsumerSummary.DPD_LOCATION, rstConsumerData.get(CNConsumer.LOCATION), Constants.TEXT);

			textBox.enterText(ConsumerSummary.TXT_SCANID, String.valueOf(numbers.generateRandomNumber(0, 99999)));
			textBox.enterText(ConsumerSummary.TXT_PIN, rstConsumerData.get(CNConsumer.PIN));
			textBox.enterText(ConsumerSummary.TXT_PHONE, rstConsumerData.get(CNConsumer.PHONE));
			foundation.click(ConsumerSummary.BTN_CREATE);
			String actualData = foundation.getText(ConsumerSummary.TXT_FN_ERROR);
			Assert.assertEquals(actualData, rstConsumerData.get(CNConsumer.ERROR_MSG));
			actualData = foundation.getText(ConsumerSummary.TXT_LN_ERROR);
			Assert.assertEquals(actualData, rstConsumerData.get(CNConsumer.ERROR_MSG));

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "145230-QAA-23-verify when invalid first name or last name are provided for consumer creation, error message \"Special characters not permitted\" is displayed.")
	public void verifyInvalidNames() {
		try {
			final String CASE_NUM = "145230";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from database
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstConsumerData = dataBase.getConsumerData(Queries.CONSUMER, CASE_NUM);

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			String menuItem = rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM);
			navigationBar.navigateToMenuItem(menuItem);

			foundation.click(ConsumerSearch.BTN_CREATE);

			dropDown.selectItem(ConsumerSummary.DPD_LOCATION, rstConsumerData.get(CNConsumer.LOCATION), Constants.TEXT);
			textBox.enterText(ConsumerSummary.TXT_FIRSTNAME, rstConsumerData.get(CNConsumer.FIRST_NAME));
			textBox.enterText(ConsumerSummary.TXT_LASTNAME, rstConsumerData.get(CNConsumer.LAST_NAME));

			textBox.enterText(ConsumerSummary.TXT_SCANID, String.valueOf(numbers.generateRandomNumber(0, 99999)));

			String actualData = foundation.getText(ConsumerSummary.TXT_FN_ERROR);
			Assert.assertEquals(actualData, rstConsumerData.get(CNConsumer.ERROR_MSG));
			actualData = foundation.getText(ConsumerSummary.TXT_LN_ERROR);
			Assert.assertEquals(actualData, rstConsumerData.get(CNConsumer.ERROR_MSG));

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

@Test(description = "146086-QAA-230-create new paycycle group and verify created paycycle group displays while creating new customer as super")
	public void newPaycycleWithNewCustomerSuper() {
		final String CASE_NUM = "146086";
		
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstConsumerSummaryData = dataBase.getConsumerSummaryData(Queries.CONSUMER_SUMMARY, CASE_NUM);
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
		List<String> menuItem = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		String location= rstLocationListData.get(CNLocationList.LOCATION_NAME);
		String paycycle=rstConsumerSummaryData.get(CNConsumerSummary.PAY_CYCLE);
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			
			//add pay-cycle group in location summary page
			navigationBar.navigateToMenuItem(menuItem.get(0));
			locationSummary.addPaycyle(location, paycycle);
			
			//add consumer and verify the pay-cycle group display
			navigationBar.navigateToMenuItem(menuItem.get(1));
			foundation.click(ConsumerSearch.BTN_CREATE_NEW);
			dropDown.selectItem(ConsumerSearch.DPD_LOCATION,location, Constants.TEXT);
			assertTrue(dropDown.getAllItems(ConsumerSearch.DPD_PAY_CYCLE).contains(paycycle));
			dropDown.selectItem(ConsumerSearch.DPD_PAY_CYCLE,paycycle, Constants.TEXT);
			consumerSearch.createConsumer(location);
			assertEquals(dropDown.getSelectedItem(ConsumerSummary.DPD_PAY_CYCLE),paycycle);
			foundation.click(ConsumerSummary.BTN_PAYOUT_CLOSE);
			foundation.alertAccept();
			foundation.waitforElementToDisappear(ConsumerSummary.TXT_SPINNER_MSG, Constants.LONG_TIME);
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		finally {
			//reset data
			navigationBar.navigateToMenuItem(menuItem.get(0));
			locationSummary.deletePaycyle(location, paycycle);
		}
	}
	
	@Test(description = "146087-QAA-230-create new paycycle group and verify created paycycle group displays while creating new customer as operator")
	public void newPaycycleWithNewCustomerOperator() {
		final String CASE_NUM = "146087";
		
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstConsumerSummaryData = dataBase.getConsumerSummaryData(Queries.CONSUMER_SUMMARY, CASE_NUM);
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
		List<String> menuItem = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		String location= rstLocationListData.get(CNLocationList.LOCATION_NAME);
		String paycycle=rstConsumerSummaryData.get(CNConsumerSummary.PAY_CYCLE);
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			
			// add pay-cycle group in location summary page
			navigationBar.navigateToMenuItem(menuItem.get(0));
			locationSummary.addPaycyle(location, paycycle);
			login.logout();

			// login as operator
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			
			//add consumer and verify the pay-cycle group display
			navigationBar.navigateToMenuItem(menuItem.get(1));
			foundation.click(ConsumerSearch.BTN_CREATE_NEW);
			dropDown.selectItem(ConsumerSearch.DPD_LOCATION,location, Constants.TEXT);
			assertTrue(dropDown.getAllItems(ConsumerSearch.DPD_PAY_CYCLE).contains(paycycle));
			dropDown.selectItem(ConsumerSearch.DPD_PAY_CYCLE,paycycle, Constants.TEXT);
			consumerSearch.createConsumer(location);
			assertEquals(dropDown.getSelectedItem(ConsumerSummary.DPD_PAY_CYCLE),paycycle);
			foundation.click(ConsumerSummary.BTN_PAYOUT_CLOSE);
			foundation.alertAccept();
			foundation.waitforElementToDisappear(ConsumerSummary.TXT_SPINNER_MSG, Constants.LONG_TIME);
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		finally {
			//login as super and reset data
			login.logout();
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItem.get(0));
			locationSummary.deletePaycyle(location, paycycle);
		}
	}
	
	@Test(description = "146088-QAA-230-create new paycycle group and verify created paycycle group displays while editing customer in admin>consumer page as operator")
	public void newPaycycleWithEditCustomerOperator() {
		final String CASE_NUM = "146088";
		
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstConsumerSearchData = dataBase.getConsumerSearchData(Queries.CONSUMER_SEARCH, CASE_NUM);
		rstConsumerSummaryData = dataBase.getConsumerSummaryData(Queries.CONSUMER_SUMMARY, CASE_NUM);
		List<String> menuItem = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		String location= propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE);
		String paycycle=rstConsumerSummaryData.get(CNConsumerSummary.PAY_CYCLE);
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			
			//add pay-cycle group in location summary page
			navigationBar.navigateToMenuItem(menuItem.get(0));
			locationSummary.addPaycyle(location, paycycle);
			login.logout();
			
			//login as operator
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			
			//search for consumer and verify the pay-cycle group display
			navigationBar.navigateToMenuItem(menuItem.get(1));
			consumerSearch.enterSearchFields(rstConsumerSearchData.get(CNConsumerSearch.SEARCH_BY),
					rstConsumerSearchData.get(CNConsumerSearch.CONSUMER_ID), location,
					rstConsumerSearchData.get(CNConsumerSearch.STATUS));
			foundation.click(consumerSearch.objCell(rstConsumerSearchData.get(CNConsumerSearch.FIRST_NAME)));
			assertTrue(dropDown.getAllItems(ConsumerSummary.DPD_PAY_CYCLE).contains(paycycle));
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		finally {
			//reset data
			login.logout();
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItem.get(0));
			locationSummary.deletePaycyle(location, paycycle);
		}
	}
	
	@Test(description = "146089-QAA-230-create new paycycle group and verify created paycycle group displays while editing customer in admin>consumer page as super")
	public void newPaycycleWithEditCustomerSuper() {
		final String CASE_NUM = "146089";
		
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstConsumerSearchData = dataBase.getConsumerSearchData(Queries.CONSUMER_SEARCH, CASE_NUM);
		rstConsumerSummaryData = dataBase.getConsumerSummaryData(Queries.CONSUMER_SUMMARY, CASE_NUM);
		List<String> menuItem = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		String location= propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE);
		String paycycle=rstConsumerSummaryData.get(CNConsumerSummary.PAY_CYCLE);
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			
			//add pay-cycle group in location summary page
			navigationBar.navigateToMenuItem(menuItem.get(0));
			locationSummary.addPaycyle(location, paycycle);
			
			//search for consumer and verify the pay-cycle group display
			navigationBar.navigateToMenuItem(menuItem.get(1));
			consumerSearch.enterSearchFields(rstConsumerSearchData.get(CNConsumerSearch.SEARCH_BY),
					rstConsumerSearchData.get(CNConsumerSearch.CONSUMER_ID), location,
					rstConsumerSearchData.get(CNConsumerSearch.STATUS));
			foundation.click(consumerSearch.objCell(rstConsumerSearchData.get(CNConsumerSearch.FIRST_NAME)));
			assertTrue(dropDown.getAllItems(ConsumerSummary.DPD_PAY_CYCLE).contains(paycycle));
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		finally {
			//reset data
			navigationBar.navigateToMenuItem(menuItem.get(0));
			locationSummary.deletePaycyle(location, paycycle);
		}
	}
	
	@Test(description = "146090-QAA-230-delete the paycycle group and verify deleted paycycle group displays while creating new customer as super")
	public void deletePaycycleWithNewCustomerSuper() {
		final String CASE_NUM = "146090";
		
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstConsumerSearchData = dataBase.getConsumerSearchData(Queries.CONSUMER_SEARCH, CASE_NUM);
		rstConsumerSummaryData = dataBase.getConsumerSummaryData(Queries.CONSUMER_SUMMARY, CASE_NUM);
		List<String> menuItem = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		String location= propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE);
		String paycycle=rstConsumerSummaryData.get(CNConsumerSummary.PAY_CYCLE);
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			
			//add pay-cycle group in location summary page
			navigationBar.navigateToMenuItem(menuItem.get(0));
			locationSummary.addPaycyle(location, paycycle);
			
			//add consumer and verify the pay-cycle group display
			navigationBar.navigateToMenuItem(menuItem.get(1));
			foundation.click(ConsumerSearch.BTN_CREATE_NEW);
			dropDown.selectItem(ConsumerSearch.DPD_LOCATION,location, Constants.TEXT);
			assertTrue(dropDown.getAllItems(ConsumerSearch.DPD_PAY_CYCLE).contains(paycycle));
			dropDown.selectItem(ConsumerSearch.DPD_PAY_CYCLE,paycycle, Constants.TEXT);
			String emailID=consumerSearch.createConsumer(location);
			assertEquals(dropDown.getSelectedItem(ConsumerSummary.DPD_PAY_CYCLE),paycycle);
			foundation.threadWait(Constants.ONE_SECOND);
			
			//delete pay-cycle			
			navigationBar.navigateToMenuItem(menuItem.get(0));
			locationSummary.deletePaycyle(location, paycycle);
			
			//search the same consumer which is created and verify the display of pay-cycle		
			navigationBar.navigateToMenuItem(menuItem.get(1));
			consumerSearch.enterSearchFields(rstConsumerSearchData.get(CNConsumerSearch.SEARCH_BY),
					emailID, location,
					rstConsumerSearchData.get(CNConsumerSearch.STATUS));
			foundation.click(ConsumerSearch.LNK_FIRST_ROW);
			assertNotEquals(dropDown.getSelectedItem(ConsumerSummary.DPD_PAY_CYCLE), paycycle);
			assertFalse(dropDown.getAllItems(ConsumerSummary.DPD_PAY_CYCLE).contains(paycycle));
	
			//delete consumer
			foundation.click(ConsumerSummary.BTN_PAYOUT_CLOSE);
			foundation.alertAccept();
			foundation.waitforElementToDisappear(ConsumerSummary.TXT_SPINNER_MSG, Constants.LONG_TIME);
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
	
	@Test(description = "146091-QAA-230-delete the paycycle group and verify deleted paycycle group displays while creating new customer as operator")
	public void deletePaycycleWithNewCustomerOperator() {
		final String CASE_NUM = "146091";
		
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstConsumerSearchData = dataBase.getConsumerSearchData(Queries.CONSUMER_SEARCH, CASE_NUM);
		rstConsumerSummaryData = dataBase.getConsumerSummaryData(Queries.CONSUMER_SUMMARY, CASE_NUM);
		List<String> menuItem = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		String location= propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE);
		String paycycle=rstConsumerSummaryData.get(CNConsumerSummary.PAY_CYCLE);
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			
			//add pay-cycle group in location summary page
			navigationBar.navigateToMenuItem(menuItem.get(0));
			locationSummary.addPaycyle(location, paycycle);
			
			//login as operator
			login.logout();
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			
			//add consumer and verify the pay-cycle group display
			navigationBar.navigateToMenuItem(menuItem.get(1));
			foundation.click(ConsumerSearch.BTN_CREATE_NEW);
			dropDown.selectItem(ConsumerSearch.DPD_LOCATION,location, Constants.TEXT);
			assertTrue(dropDown.getAllItems(ConsumerSearch.DPD_PAY_CYCLE).contains(paycycle));
			dropDown.selectItem(ConsumerSearch.DPD_PAY_CYCLE,paycycle, Constants.TEXT);
			String emailID=consumerSearch.createConsumer(location);
			assertEquals(dropDown.getSelectedItem(ConsumerSummary.DPD_PAY_CYCLE),paycycle);
			foundation.threadWait(Constants.ONE_SECOND);
			
			//login as super and delete pay-cycle
			foundation.waitforElementToDisappear(ConsumerSummary.TXT_SPINNER_MSG, Constants.LONG_TIME);
			login.logout();
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItem.get(0));
			locationSummary.deletePaycyle(location, paycycle);
			
			//login as operator and search the same consumer which is created and verify the display of pay-cycle
			login.logout();
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItem.get(1));
			consumerSearch.enterSearchFields(rstConsumerSearchData.get(CNConsumerSearch.SEARCH_BY),
					emailID, location,
					rstConsumerSearchData.get(CNConsumerSearch.STATUS));
			foundation.click(ConsumerSearch.LNK_FIRST_ROW);
			assertNotEquals(dropDown.getSelectedItem(ConsumerSummary.DPD_PAY_CYCLE), paycycle);
			assertFalse(dropDown.getAllItems(ConsumerSummary.DPD_PAY_CYCLE).contains(paycycle));
	
			//delete consumer
			foundation.click(ConsumerSummary.BTN_PAYOUT_CLOSE);
			foundation.alertAccept();
			foundation.waitforElementToDisappear(ConsumerSummary.TXT_SPINNER_MSG, Constants.LONG_TIME);
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
	
	@Test(description = "146092-QAA-230-delete the paycycle group and verify deleted paycycle group displays while editing customer in admin>consumer page as operator")
	public void deletePaycycleWithEditCustomerOperator() {
		final String CASE_NUM = "146092";
		
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstConsumerSearchData = dataBase.getConsumerSearchData(Queries.CONSUMER_SEARCH, CASE_NUM);
		rstConsumerSummaryData = dataBase.getConsumerSummaryData(Queries.CONSUMER_SUMMARY, CASE_NUM);
		List<String> menuItem = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		String location= propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE);
		String paycycle=rstConsumerSummaryData.get(CNConsumerSummary.PAY_CYCLE);
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			
			//add pay-cycle group in location summary page
			navigationBar.navigateToMenuItem(menuItem.get(0));
			locationSummary.addPaycyle(location, paycycle);
			login.logout();
			
			//login as operator
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			
			//search for consumer and verify the pay-cycle group display
			navigationBar.navigateToMenuItem(menuItem.get(1));
			consumerSearch.enterSearchFields(rstConsumerSearchData.get(CNConsumerSearch.SEARCH_BY),
					rstConsumerSearchData.get(CNConsumerSearch.CONSUMER_ID), location,
					rstConsumerSearchData.get(CNConsumerSearch.STATUS));
			foundation.click(consumerSearch.objCell(rstConsumerSearchData.get(CNConsumerSearch.FIRST_NAME)));
			assertTrue(dropDown.getAllItems(ConsumerSummary.DPD_PAY_CYCLE).contains(paycycle));
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		finally {
			//delete pay-cycle and verify the display in consumer page
			login.logout();
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItem.get(0));
			locationSummary.deletePaycyle(location, paycycle);
			navigationBar.navigateToMenuItem(menuItem.get(1));
			consumerSearch.enterSearchFields(rstConsumerSearchData.get(CNConsumerSearch.SEARCH_BY),
					rstConsumerSearchData.get(CNConsumerSearch.CONSUMER_ID), location,
					rstConsumerSearchData.get(CNConsumerSearch.STATUS));
			foundation.click(consumerSearch.objCell(rstConsumerSearchData.get(CNConsumerSearch.FIRST_NAME)));
			assertFalse(dropDown.getAllItems(ConsumerSummary.DPD_PAY_CYCLE).contains(paycycle));
		}
	}
	
	@Test(description = "146093-QAA-230-delete the paycycle group and verify deleted paycycle group displays while editing customer in admin>consumer page as super")
	public void deletePaycycleWithEditCustomerAdminSuper() {
		final String CASE_NUM = "146093";
		
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstConsumerSearchData = dataBase.getConsumerSearchData(Queries.CONSUMER_SEARCH, CASE_NUM);
		rstConsumerSummaryData = dataBase.getConsumerSummaryData(Queries.CONSUMER_SUMMARY, CASE_NUM);
		List<String> menuItem = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		String location= propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE);
		String paycycle=rstConsumerSummaryData.get(CNConsumerSummary.PAY_CYCLE);
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			
			//add pay-cycle group in location summary page
			navigationBar.navigateToMenuItem(menuItem.get(0));
			locationSummary.addPaycyle(location, paycycle);
			
			//search for consumer and verify the pay-cycle group display
			navigationBar.navigateToMenuItem(menuItem.get(1));
			consumerSearch.enterSearchFields(rstConsumerSearchData.get(CNConsumerSearch.SEARCH_BY),
					rstConsumerSearchData.get(CNConsumerSearch.CONSUMER_ID), location,
					rstConsumerSearchData.get(CNConsumerSearch.STATUS));
			foundation.click(consumerSearch.objCell(rstConsumerSearchData.get(CNConsumerSearch.FIRST_NAME)));
			assertTrue(dropDown.getAllItems(ConsumerSummary.DPD_PAY_CYCLE).contains(paycycle));
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		finally {
			//delete pay-cycle and verify the display in consumer page
			login.logout();
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItem.get(0));
			locationSummary.deletePaycyle(location, paycycle);
			navigationBar.navigateToMenuItem(menuItem.get(1));
			consumerSearch.enterSearchFields(rstConsumerSearchData.get(CNConsumerSearch.SEARCH_BY),
					rstConsumerSearchData.get(CNConsumerSearch.CONSUMER_ID), location,
					rstConsumerSearchData.get(CNConsumerSearch.STATUS));
			foundation.click(consumerSearch.objCell(rstConsumerSearchData.get(CNConsumerSearch.FIRST_NAME)));
			assertFalse(dropDown.getAllItems(ConsumerSummary.DPD_PAY_CYCLE).contains(paycycle));
		}
	}
	
	@Test(description = "146094-QAA-230-delete the paycycle group and verify deleted paycycle group displays while editing customer in super>consumer page as super")
	public void deletePaycycleWithEditCustomerSuperSuper() {
		final String CASE_NUM = "146094";
		
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstConsumerSearchData = dataBase.getConsumerSearchData(Queries.CONSUMER_SEARCH, CASE_NUM);
		rstConsumerSummaryData = dataBase.getConsumerSummaryData(Queries.CONSUMER_SUMMARY, CASE_NUM);
		List<String> menuItem = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		String location= propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE);
		String paycycle=rstConsumerSummaryData.get(CNConsumerSummary.PAY_CYCLE);
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			
			//add pay-cycle group in location summary page
			navigationBar.navigateToMenuItem(menuItem.get(0));
			locationSummary.addPaycyle(location, paycycle);
			
			//search for consumer and verify the pay-cycle group display
			navigationBar.navigateToMenuItem(menuItem.get(1));
			consumerSearch.enterSearchFields(rstConsumerSearchData.get(CNConsumerSearch.SEARCH_BY),
					rstConsumerSearchData.get(CNConsumerSearch.CONSUMER_ID), location,
					rstConsumerSearchData.get(CNConsumerSearch.STATUS));
			foundation.click(consumerSearch.objCell(rstConsumerSearchData.get(CNConsumerSearch.FIRST_NAME)));
			assertTrue(dropDown.getAllItems(ConsumerSummary.DPD_PAY_CYCLE).contains(paycycle));
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		finally {
			//delete pay-cycle and verify the display in consumer page
			login.logout();
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItem.get(0));
			locationSummary.deletePaycyle(location, paycycle);
			navigationBar.navigateToMenuItem(menuItem.get(1));
			consumerSearch.enterSearchFields(rstConsumerSearchData.get(CNConsumerSearch.SEARCH_BY),
					rstConsumerSearchData.get(CNConsumerSearch.CONSUMER_ID), location,
					rstConsumerSearchData.get(CNConsumerSearch.STATUS));
			foundation.click(consumerSearch.objCell(rstConsumerSearchData.get(CNConsumerSearch.FIRST_NAME)));
			assertFalse(dropDown.getAllItems(ConsumerSummary.DPD_PAY_CYCLE).contains(paycycle));
		}
	}
	
	@Test(description = "146095-QAA-230-turn off payroll deduct and verify paycycle group displays while creating new customer as super")
	public void turnoffPayrollWithNewCustomerSuper() {
		final String CASE_NUM = "146095";
		
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);
		List<String> menuItem = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		String location = propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE);
		List<String> payrollDeduct = Arrays
				.asList(rstLocationSummaryData.get(CNLocationSummary.PAYROLL_DEDUCT).split(Constants.DELIMITER_TILD));
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			
			//turn off payroll deduct in location summary page
			navigationBar.navigateToMenuItem(menuItem.get(0));
			locationSummary.turnOnOROffPayRollDeduct(location, payrollDeduct.get(0));
			
			//add consumer and verify the pay-cycle group display
			navigationBar.navigateToMenuItem(menuItem.get(1));
			foundation.click(ConsumerSearch.BTN_CREATE_NEW);
			dropDown.selectItem(ConsumerSearch.DPD_LOCATION,location, Constants.TEXT);
			assertFalse(foundation.isDisplayed(ConsumerSummary.DPD_PAY_CYCLE));
			consumerSearch.createConsumer(location);
			assertFalse(foundation.isDisplayed(ConsumerSummary.DPD_PAY_CYCLE));
			
			//delete consumer
			foundation.click(ConsumerSummary.BTN_PAYOUT_CLOSE);
			foundation.alertAccept();
			foundation.waitforElementToDisappear(ConsumerSummary.TXT_SPINNER_MSG, Constants.LONG_TIME);
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		finally {
			//turn on payroll deduct in location summary page
			navigationBar.navigateToMenuItem(menuItem.get(0));
			locationSummary.turnOnOROffPayRollDeduct(location, payrollDeduct.get(1));
		}
	}
	
	@Test(description = "146096-QAA-230-turn off payroll deduct and verify paycycle group displays while creating new customer as operator")
	public void turnoffPayrollWithNewCustomerOperator() {
		final String CASE_NUM = "146096";
		
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);
		rstConsumerSearchData = dataBase.getConsumerSearchData(Queries.CONSUMER_SEARCH, CASE_NUM);
		List<String> menuItem = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		String location= propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE);
		List<String> payrollDeduct = Arrays
				.asList(rstLocationSummaryData.get(CNLocationSummary.PAYROLL_DEDUCT).split(Constants.DELIMITER_TILD));
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			
			//add pay-cycle group in location summary page
			navigationBar.navigateToMenuItem(menuItem.get(0));
			locationSummary.turnOnOROffPayRollDeduct(location, payrollDeduct.get(0));
			
			//login as operator
			login.logout();
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			
			//add consumer and verify the pay-cycle group display
			navigationBar.navigateToMenuItem(menuItem.get(1));
			foundation.click(ConsumerSearch.BTN_CREATE_NEW);
			dropDown.selectItem(ConsumerSearch.DPD_LOCATION,location, Constants.TEXT);
			assertFalse(foundation.isDisplayed(ConsumerSummary.DPD_PAY_CYCLE));
			consumerSearch.createConsumer(location);
			assertFalse(foundation.isDisplayed(ConsumerSummary.DPD_PAY_CYCLE));

			//delete consumer
			foundation.click(ConsumerSummary.BTN_PAYOUT_CLOSE);
			foundation.alertAccept();
			foundation.waitforElementToDisappear(ConsumerSummary.TXT_SPINNER_MSG, Constants.LONG_TIME);
			
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		finally {
			//login as super and turn on pay-cycle
			login.logout();
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItem.get(0));
			locationSummary.turnOnOROffPayRollDeduct(location, payrollDeduct.get(1));
		}
	}
	
	@Test(description = "146097-QAA-230-turn off payroll deduct and verify paycycle group displays while editing customer in admin>consumer page as operator")
	public void turnoffPayrollWithEditCustomerOperator() {
		final String CASE_NUM = "146097";
		
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstConsumerSearchData = dataBase.getConsumerSearchData(Queries.CONSUMER_SEARCH, CASE_NUM);
		rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);
		List<String> menuItem = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		String location= propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE);
		List<String> payrollDeduct = Arrays
				.asList(rstLocationSummaryData.get(CNLocationSummary.PAYROLL_DEDUCT).split(Constants.DELIMITER_TILD));
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			
			//turn off pay-cycle group in location summary page
			navigationBar.navigateToMenuItem(menuItem.get(0));
			locationSummary.turnOnOROffPayRollDeduct(location, payrollDeduct.get(0));
			login.logout();
			
			//login as operator
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			
			//search for consumer and verify the pay-cycle group display
			navigationBar.navigateToMenuItem(menuItem.get(1));
			consumerSearch.enterSearchFields(rstConsumerSearchData.get(CNConsumerSearch.SEARCH_BY),
					rstConsumerSearchData.get(CNConsumerSearch.CONSUMER_ID), location,
					rstConsumerSearchData.get(CNConsumerSearch.STATUS));
			foundation.click(consumerSearch.objCell(rstConsumerSearchData.get(CNConsumerSearch.FIRST_NAME)));
			assertFalse(foundation.isDisplayed(ConsumerSummary.DPD_PAY_CYCLE));
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		finally {
			//turn on pay-cycle and verify the display in consumer page
			login.logout();
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItem.get(0));
			locationSummary.turnOnOROffPayRollDeduct(location, payrollDeduct.get(1));
			navigationBar.navigateToMenuItem(menuItem.get(1));
			consumerSearch.enterSearchFields(rstConsumerSearchData.get(CNConsumerSearch.SEARCH_BY),
					rstConsumerSearchData.get(CNConsumerSearch.CONSUMER_ID), location,
					rstConsumerSearchData.get(CNConsumerSearch.STATUS));
			foundation.click(consumerSearch.objCell(rstConsumerSearchData.get(CNConsumerSearch.FIRST_NAME)));
			assertTrue(foundation.isDisplayed(ConsumerSummary.DPD_PAY_CYCLE));	
		}
	}
	
	@Test(description = "146098-QAA-230-turn off payroll deduct and verify paycycle group displays while editing customer in admin>consumer page as super")
	public void turnoffPayrollWithEditCustomerAdminSuper() {
		final String CASE_NUM = "146098";
		
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstConsumerSearchData = dataBase.getConsumerSearchData(Queries.CONSUMER_SEARCH, CASE_NUM);
		rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);
		List<String> menuItem = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		String location= propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE);
		List<String> payrollDeduct = Arrays
				.asList(rstLocationSummaryData.get(CNLocationSummary.PAYROLL_DEDUCT).split(Constants.DELIMITER_TILD));
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			
			//turn off pay-cycle group in location summary page
			navigationBar.navigateToMenuItem(menuItem.get(0));
			locationSummary.turnOnOROffPayRollDeduct(location, payrollDeduct.get(0));
			
			//search for consumer and verify the pay-cycle group display
			navigationBar.navigateToMenuItem(menuItem.get(1));
			consumerSearch.enterSearchFields(rstConsumerSearchData.get(CNConsumerSearch.SEARCH_BY),
					rstConsumerSearchData.get(CNConsumerSearch.CONSUMER_ID), location,
					rstConsumerSearchData.get(CNConsumerSearch.STATUS));
			foundation.click(consumerSearch.objCell(rstConsumerSearchData.get(CNConsumerSearch.FIRST_NAME)));
			assertFalse(foundation.isDisplayed(ConsumerSummary.DPD_PAY_CYCLE));			
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		finally {
			//turn on pay-cycle and verify the display in consumer page
			login.logout();
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItem.get(0));
			locationSummary.turnOnOROffPayRollDeduct(location, payrollDeduct.get(1));
			navigationBar.navigateToMenuItem(menuItem.get(1));
			consumerSearch.enterSearchFields(rstConsumerSearchData.get(CNConsumerSearch.SEARCH_BY),
					rstConsumerSearchData.get(CNConsumerSearch.CONSUMER_ID), location,
					rstConsumerSearchData.get(CNConsumerSearch.STATUS));
			foundation.click(consumerSearch.objCell(rstConsumerSearchData.get(CNConsumerSearch.FIRST_NAME)));
			assertTrue(foundation.isDisplayed(ConsumerSummary.DPD_PAY_CYCLE));
		}
	}
	
	@Test(description = "146099-QAA-230-turn off payroll deduct and verify paycycle group displays while editing customer in super>consumer page as super")
	public void trunoffPayrollWithEditCustomerSuperSuper() {
		final String CASE_NUM = "146099";
		
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstConsumerSearchData = dataBase.getConsumerSearchData(Queries.CONSUMER_SEARCH, CASE_NUM);
		rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);
		List<String> menuItem = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		String location= propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE);
		List<String> payrollDeduct = Arrays
				.asList(rstLocationSummaryData.get(CNLocationSummary.PAYROLL_DEDUCT).split(Constants.DELIMITER_TILD));
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			
			//turn off pay-cycle group in location summary page
			navigationBar.navigateToMenuItem(menuItem.get(0));
			locationSummary.turnOnOROffPayRollDeduct(location, payrollDeduct.get(0));
			
			//search for consumer and verify the pay-cycle group display
			navigationBar.navigateToMenuItem(menuItem.get(1));
			consumerSearch.enterSearchFields(rstConsumerSearchData.get(CNConsumerSearch.SEARCH_BY),
					rstConsumerSearchData.get(CNConsumerSearch.CONSUMER_ID), location,
					rstConsumerSearchData.get(CNConsumerSearch.STATUS));
			foundation.click(consumerSearch.objCell(rstConsumerSearchData.get(CNConsumerSearch.FIRST_NAME)));
			assertFalse(foundation.isDisplayed(ConsumerSummary.DPD_PAY_CYCLE));			
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		finally {
			//turn on pay-cycle and verify the display in consumer page
			login.logout();
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItem.get(0));
			locationSummary.turnOnOROffPayRollDeduct(location, payrollDeduct.get(1));
			navigationBar.navigateToMenuItem(menuItem.get(1));
			consumerSearch.enterSearchFields(rstConsumerSearchData.get(CNConsumerSearch.SEARCH_BY),
					rstConsumerSearchData.get(CNConsumerSearch.CONSUMER_ID), location,
					rstConsumerSearchData.get(CNConsumerSearch.STATUS));
			foundation.click(consumerSearch.objCell(rstConsumerSearchData.get(CNConsumerSearch.FIRST_NAME)));
			assertTrue(foundation.isDisplayed(ConsumerSummary.DPD_PAY_CYCLE));
		}
	}
	
	@Test(description = "146143-QAA-230-change paycycle group and verify changed paycycle group displays while creating customer in admin>consumer page as super")
	public void changePayCycleGroupWithNewCustomerSuper() {
		final String CASE_NUM = "146143";
		
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstConsumerSummaryData = dataBase.getConsumerSummaryData(Queries.CONSUMER_SUMMARY, CASE_NUM);
		List<String> menuItem = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		String location = propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE);
		List<String> paycycle = Arrays
				.asList(rstConsumerSummaryData.get(CNConsumerSummary.PAY_CYCLE).split(Constants.DELIMITER_TILD));
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			
			//add consumer and verify the pay-cycle group display
			navigationBar.navigateToMenuItem(menuItem.get(1));
			foundation.click(ConsumerSearch.BTN_CREATE_NEW);
			dropDown.selectItem(ConsumerSearch.DPD_LOCATION,location, Constants.TEXT);
			dropDown.selectItem(ConsumerSearch.DPD_PAY_CYCLE,paycycle.get(0), Constants.TEXT);
			consumerSearch.createConsumer(location);
			assertEquals(dropDown.getSelectedItem(ConsumerSummary.DPD_PAY_CYCLE),paycycle.get(0));
			
			//delete consumer
			foundation.click(ConsumerSummary.BTN_PAYOUT_CLOSE);
			foundation.alertAccept();
			foundation.waitforElementToDisappear(ConsumerSummary.TXT_SPINNER_MSG, Constants.LONG_TIME);
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
	
	@Test(description = "146144-QAA-230-change paycycle group and verify changed paycycle group displays while creating customer in admin>consumer page as operator")
	public void changePayCycleGroupWithNewCustomerOperator() {
		final String CASE_NUM = "146144";
		
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstConsumerSummaryData = dataBase.getConsumerSummaryData(Queries.CONSUMER_SUMMARY, CASE_NUM);
		List<String> menuItem = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		String location= propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE);
		List<String> paycycle = Arrays
				.asList(rstConsumerSummaryData.get(CNConsumerSummary.PAY_CYCLE).split(Constants.DELIMITER_TILD));
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));			
			
			//add consumer and verify the pay-cycle group display
			navigationBar.navigateToMenuItem(menuItem.get(1));
			foundation.click(ConsumerSearch.BTN_CREATE_NEW);
			dropDown.selectItem(ConsumerSearch.DPD_LOCATION,location, Constants.TEXT);
			dropDown.selectItem(ConsumerSearch.DPD_PAY_CYCLE,paycycle.get(0), Constants.TEXT);
			consumerSearch.createConsumer(location);
			assertEquals(dropDown.getSelectedItem(ConsumerSummary.DPD_PAY_CYCLE),paycycle.get(0));

			//delete consumer
			foundation.click(ConsumerSummary.BTN_PAYOUT_CLOSE);
			foundation.alertAccept();
			foundation.waitforElementToDisappear(ConsumerSummary.TXT_SPINNER_MSG, Constants.LONG_TIME);
			
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
	
	@Test(description = "146100-QAA-230-change paycycle group and verify changed paycycle group displays while editing customer in admin>consumer page as operator")
	public void changePayCycleGroupWithEditCustomerOperator() {
		final String CASE_NUM = "146100";
		
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstConsumerSearchData = dataBase.getConsumerSearchData(Queries.CONSUMER_SEARCH, CASE_NUM);
		rstConsumerSummaryData = dataBase.getConsumerSummaryData(Queries.CONSUMER_SUMMARY, CASE_NUM);
		List<String> menuItem = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		String location= propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE);
		List<String> paycycle = Arrays
				.asList(rstConsumerSummaryData.get(CNConsumerSummary.PAY_CYCLE).split(Constants.DELIMITER_TILD));
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			
			//search for consumer and verify the pay-cycle group display
			navigationBar.navigateToMenuItem(menuItem.get(1));
			consumerSearch.enterSearchFields(rstConsumerSearchData.get(CNConsumerSearch.SEARCH_BY),
					rstConsumerSearchData.get(CNConsumerSearch.CONSUMER_ID), location,
					rstConsumerSearchData.get(CNConsumerSearch.STATUS));
			foundation.click(consumerSearch.objCell(rstConsumerSearchData.get(CNConsumerSearch.FIRST_NAME)));
			dropDown.selectItem(ConsumerSummary.DPD_PAY_CYCLE,paycycle.get(0), Constants.TEXT);
			foundation.click(ConsumerSummary.BTN_SAVE);
			foundation.waitforElementToDisappear(ConsumerSummary.TXT_SPINNER_MSG, Constants.LONG_TIME);
			consumerSearch.enterSearchFields(rstConsumerSearchData.get(CNConsumerSearch.SEARCH_BY),
					rstConsumerSearchData.get(CNConsumerSearch.CONSUMER_ID), location,
					rstConsumerSearchData.get(CNConsumerSearch.STATUS));
			foundation.click(consumerSearch.objCell(rstConsumerSearchData.get(CNConsumerSearch.FIRST_NAME)));
			assertEquals(dropDown.getSelectedItem(ConsumerSummary.DPD_PAY_CYCLE),paycycle.get(0));
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		finally {
			//reset pay-cycle
			navigationBar.navigateToMenuItem(menuItem.get(1));
			consumerSearch.enterSearchFields(rstConsumerSearchData.get(CNConsumerSearch.SEARCH_BY),
					rstConsumerSearchData.get(CNConsumerSearch.CONSUMER_ID), location,
					rstConsumerSearchData.get(CNConsumerSearch.STATUS));
			foundation.click(consumerSearch.objCell(rstConsumerSearchData.get(CNConsumerSearch.FIRST_NAME)));
			dropDown.selectItem(ConsumerSummary.DPD_PAY_CYCLE,paycycle.get(1), Constants.TEXT);
			foundation.click(ConsumerSummary.BTN_SAVE);
		}
	}
	
	@Test(description = "146101-QAA-230-change paycycle group and verify changed paycycle group displays while editing customer in admin>consumer page as super")
	public void changePayCycleGroupWithEditCustomerAdminSuper() {
		final String CASE_NUM = "146101";
		
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstConsumerSearchData = dataBase.getConsumerSearchData(Queries.CONSUMER_SEARCH, CASE_NUM);
		rstConsumerSummaryData = dataBase.getConsumerSummaryData(Queries.CONSUMER_SUMMARY, CASE_NUM);
		List<String> menuItem = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		String location= propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE);
		List<String> paycycle = Arrays
				.asList(rstConsumerSummaryData.get(CNConsumerSummary.PAY_CYCLE).split(Constants.DELIMITER_TILD));
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			
			//search for consumer and verify the pay-cycle group display
			navigationBar.navigateToMenuItem(menuItem.get(1));
			consumerSearch.enterSearchFields(rstConsumerSearchData.get(CNConsumerSearch.SEARCH_BY),
					rstConsumerSearchData.get(CNConsumerSearch.CONSUMER_ID), location,
					rstConsumerSearchData.get(CNConsumerSearch.STATUS));
			foundation.click(consumerSearch.objCell(rstConsumerSearchData.get(CNConsumerSearch.FIRST_NAME)));
			dropDown.selectItem(ConsumerSummary.DPD_PAY_CYCLE,paycycle.get(0), Constants.TEXT);
			foundation.click(ConsumerSummary.BTN_SAVE);
			foundation.waitforElementToDisappear(ConsumerSummary.TXT_SPINNER_MSG, Constants.LONG_TIME);
			consumerSearch.enterSearchFields(rstConsumerSearchData.get(CNConsumerSearch.SEARCH_BY),
					rstConsumerSearchData.get(CNConsumerSearch.CONSUMER_ID), location,
					rstConsumerSearchData.get(CNConsumerSearch.STATUS));
			foundation.click(consumerSearch.objCell(rstConsumerSearchData.get(CNConsumerSearch.FIRST_NAME)));
			assertEquals(dropDown.getSelectedItem(ConsumerSummary.DPD_PAY_CYCLE),paycycle.get(0));		
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		finally {
			//reset pay-cycle
			navigationBar.navigateToMenuItem(menuItem.get(1));
			consumerSearch.enterSearchFields(rstConsumerSearchData.get(CNConsumerSearch.SEARCH_BY),
					rstConsumerSearchData.get(CNConsumerSearch.CONSUMER_ID), location,
					rstConsumerSearchData.get(CNConsumerSearch.STATUS));
			foundation.click(consumerSearch.objCell(rstConsumerSearchData.get(CNConsumerSearch.FIRST_NAME)));
			dropDown.selectItem(ConsumerSummary.DPD_PAY_CYCLE,paycycle.get(1), Constants.TEXT);
			foundation.click(ConsumerSummary.BTN_SAVE);
		}
	}
	
	@Test(description = "146102-QAA-230-change paycycle group and verify changed paycycle group displays while editing customer in super>consumer page as super")
	public void changePayCycleGroupWithEditCustomerSuperSuper() {
		final String CASE_NUM = "146102";
		
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstConsumerSearchData = dataBase.getConsumerSearchData(Queries.CONSUMER_SEARCH, CASE_NUM);
		rstConsumerSummaryData = dataBase.getConsumerSummaryData(Queries.CONSUMER_SUMMARY, CASE_NUM);
		List<String> menuItem = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		String location= propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE);
		List<String> paycycle = Arrays
				.asList(rstConsumerSummaryData.get(CNConsumerSummary.PAY_CYCLE).split(Constants.DELIMITER_TILD));
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			
			//search for consumer and verify the pay-cycle group display
			navigationBar.navigateToMenuItem(menuItem.get(1));
			consumerSearch.enterSearchFields(rstConsumerSearchData.get(CNConsumerSearch.SEARCH_BY),
					rstConsumerSearchData.get(CNConsumerSearch.CONSUMER_ID), location,
					rstConsumerSearchData.get(CNConsumerSearch.STATUS));
			foundation.click(consumerSearch.objCell(rstConsumerSearchData.get(CNConsumerSearch.FIRST_NAME)));
			dropDown.selectItem(ConsumerSummary.DPD_PAY_CYCLE,paycycle.get(0), Constants.TEXT);
			foundation.click(ConsumerSummary.BTN_SAVE);
			foundation.waitforElementToDisappear(ConsumerSummary.TXT_SPINNER_MSG, Constants.LONG_TIME);
			consumerSearch.enterSearchFields(rstConsumerSearchData.get(CNConsumerSearch.SEARCH_BY),
					rstConsumerSearchData.get(CNConsumerSearch.CONSUMER_ID), location,
					rstConsumerSearchData.get(CNConsumerSearch.STATUS));
			foundation.click(consumerSearch.objCell(rstConsumerSearchData.get(CNConsumerSearch.FIRST_NAME)));
			assertEquals(dropDown.getSelectedItem(ConsumerSummary.DPD_PAY_CYCLE),paycycle.get(0));		
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		finally {
			//reset pay-cycle
			navigationBar.navigateToMenuItem(menuItem.get(1));
			consumerSearch.enterSearchFields(rstConsumerSearchData.get(CNConsumerSearch.SEARCH_BY),
					rstConsumerSearchData.get(CNConsumerSearch.CONSUMER_ID), location,
					rstConsumerSearchData.get(CNConsumerSearch.STATUS));
			foundation.click(consumerSearch.objCell(rstConsumerSearchData.get(CNConsumerSearch.FIRST_NAME)));
			dropDown.selectItem(ConsumerSummary.DPD_PAY_CYCLE,paycycle.get(1), Constants.TEXT);
			foundation.click(ConsumerSummary.BTN_SAVE);
		}
	}
	
	@Test(description = "146145-QAA-230-edit paycycle group name and verify changed paycycle group displays while creating new customer in admin>consumer page as super")
	public void editPayCycleGrpNameNewCustomerSuper() {
		final String CASE_NUM = "146145";
		
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstConsumerSearchData = dataBase.getConsumerSearchData(Queries.CONSUMER_SEARCH, CASE_NUM);
		rstConsumerSummaryData = dataBase.getConsumerSummaryData(Queries.CONSUMER_SUMMARY, CASE_NUM);
		List<String> menuItem = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		String location= propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE);
		List<String> paycycle = Arrays
				.asList(rstConsumerSummaryData.get(CNConsumerSummary.PAY_CYCLE).split(Constants.DELIMITER_TILD));
		String emailID="";
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			
			//add pay-cycle group in location summary page
			navigationBar.navigateToMenuItem(menuItem.get(0));
			locationSummary.editPaycyle(location, paycycle.get(0),paycycle.get(1));
			
			//add consumer and verify the pay-cycle group display
			navigationBar.navigateToMenuItem(menuItem.get(1));
			foundation.click(ConsumerSearch.BTN_CREATE_NEW);
			dropDown.selectItem(ConsumerSearch.DPD_LOCATION,location, Constants.TEXT);
			assertTrue(dropDown.getAllItems(ConsumerSearch.DPD_PAY_CYCLE).contains(paycycle.get(1)));
			dropDown.selectItem(ConsumerSearch.DPD_PAY_CYCLE,paycycle.get(1), Constants.TEXT);
			emailID=consumerSearch.createConsumer(location);
			assertEquals(dropDown.getSelectedItem(ConsumerSummary.DPD_PAY_CYCLE),paycycle.get(1));
			foundation.threadWait(Constants.ONE_SECOND);
			
			//delete consumer
			foundation.click(ConsumerSummary.BTN_PAYOUT_CLOSE);
			foundation.alertAccept();
			foundation.waitforElementToDisappear(ConsumerSummary.TXT_SPINNER_MSG, Constants.LONG_TIME);
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		finally {			
			//reset pay-cycle name
			navigationBar.navigateToMenuItem(menuItem.get(0));
			locationSummary.editPaycyle(location, paycycle.get(1),paycycle.get(0));
		}
	}
	
	@Test(description = "146146-QAA-230-edit paycycle group name and verify changed paycycle group displays while creating new customer in admin>consumer page as operator")
	public void editPayCycleGrpNameNewCustomerOperator() {
		final String CASE_NUM = "146146";
		
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstConsumerSearchData = dataBase.getConsumerSearchData(Queries.CONSUMER_SEARCH, CASE_NUM);
		rstConsumerSummaryData = dataBase.getConsumerSummaryData(Queries.CONSUMER_SUMMARY, CASE_NUM);
		List<String> menuItem = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		String location= propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE);
		List<String> paycycle = Arrays
				.asList(rstConsumerSummaryData.get(CNConsumerSummary.PAY_CYCLE).split(Constants.DELIMITER_TILD));
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			
			//add pay-cycle group in location summary page
			navigationBar.navigateToMenuItem(menuItem.get(0));
			locationSummary.editPaycyle(location, paycycle.get(0),paycycle.get(1));
			
			//login as operator
			login.logout();
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			
			//add consumer and verify the pay-cycle group display
			navigationBar.navigateToMenuItem(menuItem.get(1));
			foundation.click(ConsumerSearch.BTN_CREATE_NEW);
			dropDown.selectItem(ConsumerSearch.DPD_LOCATION,location, Constants.TEXT);
			assertTrue(dropDown.getAllItems(ConsumerSearch.DPD_PAY_CYCLE).contains(paycycle.get(1)));
			dropDown.selectItem(ConsumerSearch.DPD_PAY_CYCLE,paycycle.get(1), Constants.TEXT);
			String emailID=consumerSearch.createConsumer(location);
			assertEquals(dropDown.getSelectedItem(ConsumerSummary.DPD_PAY_CYCLE),paycycle.get(1));
			foundation.threadWait(Constants.ONE_SECOND);
			
			//delete consumer
			foundation.click(ConsumerSummary.BTN_PAYOUT_CLOSE);
			foundation.alertAccept();
			foundation.waitforElementToDisappear(ConsumerSummary.TXT_SPINNER_MSG, Constants.LONG_TIME);
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		finally {
			//login as super and delete pay-cycle
			login.logout();
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItem.get(0));
			locationSummary.editPaycyle(location, paycycle.get(0),paycycle.get(1));
		}
	}
	
	@Test(description = "146103-QAA-230-edit paycycle group name and verify changed paycycle group displays while editing customer in admin>consumer page as operator")
	public void editPayCycleGrpNameEditCustomerOperator() {
		final String CASE_NUM = "146103";
		
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstConsumerSearchData = dataBase.getConsumerSearchData(Queries.CONSUMER_SEARCH, CASE_NUM);
		rstConsumerSummaryData = dataBase.getConsumerSummaryData(Queries.CONSUMER_SUMMARY, CASE_NUM);
		List<String> menuItem = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		String location= propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE);
		List<String> paycycle = Arrays
				.asList(rstConsumerSummaryData.get(CNConsumerSummary.PAY_CYCLE).split(Constants.DELIMITER_TILD));
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			
			//add pay-cycle group in location summary page
			navigationBar.navigateToMenuItem(menuItem.get(0));
			locationSummary.editPaycyle(location, paycycle.get(0),paycycle.get(1));
			login.logout();
			
			//login as operator
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			
			//search for consumer and verify the pay-cycle group display
			navigationBar.navigateToMenuItem(menuItem.get(1));
			consumerSearch.enterSearchFields(rstConsumerSearchData.get(CNConsumerSearch.SEARCH_BY),
					rstConsumerSearchData.get(CNConsumerSearch.CONSUMER_ID), location,
					rstConsumerSearchData.get(CNConsumerSearch.STATUS));
			foundation.click(consumerSearch.objCell(rstConsumerSearchData.get(CNConsumerSearch.FIRST_NAME)));
			assertTrue(dropDown.getAllItems(ConsumerSummary.DPD_PAY_CYCLE).contains(paycycle.get(1)));
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		finally {
			//delete pay-cycle and verify the display in consumer page
			login.logout();
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItem.get(0));
			locationSummary.editPaycyle(location, paycycle.get(1),paycycle.get(0));
			navigationBar.navigateToMenuItem(menuItem.get(1));
			consumerSearch.enterSearchFields(rstConsumerSearchData.get(CNConsumerSearch.SEARCH_BY),
					rstConsumerSearchData.get(CNConsumerSearch.CONSUMER_ID), location,
					rstConsumerSearchData.get(CNConsumerSearch.STATUS));
			foundation.click(consumerSearch.objCell(rstConsumerSearchData.get(CNConsumerSearch.FIRST_NAME)));
			assertTrue(dropDown.getAllItems(ConsumerSummary.DPD_PAY_CYCLE).contains(paycycle.get(0)));
		}
	}
	
	@Test(description = "146104-QAA-230-edit paycycle group name and verify changed paycycle group displays while editing customer in admin>consumer page as super")
	public void editPayCycleGrpNameEditCustomerAdminSuper() {
		final String CASE_NUM = "146104";
		
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstConsumerSearchData = dataBase.getConsumerSearchData(Queries.CONSUMER_SEARCH, CASE_NUM);
		rstConsumerSummaryData = dataBase.getConsumerSummaryData(Queries.CONSUMER_SUMMARY, CASE_NUM);
		List<String> menuItem = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		String location= propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE);
		List<String> paycycle = Arrays
				.asList(rstConsumerSummaryData.get(CNConsumerSummary.PAY_CYCLE).split(Constants.DELIMITER_TILD));
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			
			//add pay-cycle group in location summary page
			navigationBar.navigateToMenuItem(menuItem.get(0));
			locationSummary.editPaycyle(location, paycycle.get(0),paycycle.get(1));
			
			//search for consumer and verify the pay-cycle group display
			navigationBar.navigateToMenuItem(menuItem.get(1));
			consumerSearch.enterSearchFields(rstConsumerSearchData.get(CNConsumerSearch.SEARCH_BY),
					rstConsumerSearchData.get(CNConsumerSearch.CONSUMER_ID), location,
					rstConsumerSearchData.get(CNConsumerSearch.STATUS));
			foundation.click(consumerSearch.objCell(rstConsumerSearchData.get(CNConsumerSearch.FIRST_NAME)));
			assertTrue(dropDown.getAllItems(ConsumerSummary.DPD_PAY_CYCLE).contains(paycycle.get(1)));
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		finally {
			//delete pay-cycle and verify the display in consumer page
			login.logout();
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItem.get(0));
			locationSummary.editPaycyle(location, paycycle.get(1),paycycle.get(0));
			navigationBar.navigateToMenuItem(menuItem.get(1));
			consumerSearch.enterSearchFields(rstConsumerSearchData.get(CNConsumerSearch.SEARCH_BY),
					rstConsumerSearchData.get(CNConsumerSearch.CONSUMER_ID), location,
					rstConsumerSearchData.get(CNConsumerSearch.STATUS));
			foundation.click(consumerSearch.objCell(rstConsumerSearchData.get(CNConsumerSearch.FIRST_NAME)));
			assertTrue(dropDown.getAllItems(ConsumerSummary.DPD_PAY_CYCLE).contains(paycycle.get(0)));
		}
	}
	
	@Test(description = "146105-QAA-230-edit paycycle group name and verify changed paycycle group displays while editing customer in super>consumer page as super")
	public void editPayCycleGrpNameEditCustomerSuperSuper() {
		final String CASE_NUM = "146105";
		
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstConsumerSearchData = dataBase.getConsumerSearchData(Queries.CONSUMER_SEARCH, CASE_NUM);
		rstConsumerSummaryData = dataBase.getConsumerSummaryData(Queries.CONSUMER_SUMMARY, CASE_NUM);
		List<String> menuItem = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		String location= propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE);
		List<String> paycycle = Arrays
				.asList(rstConsumerSummaryData.get(CNConsumerSummary.PAY_CYCLE).split(Constants.DELIMITER_TILD));
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			
			//add pay-cycle group in location summary page
			navigationBar.navigateToMenuItem(menuItem.get(0));
			locationSummary.editPaycyle(location, paycycle.get(0),paycycle.get(1));
			
			//search for consumer and verify the pay-cycle group display
			navigationBar.navigateToMenuItem(menuItem.get(1));
			consumerSearch.enterSearchFields(rstConsumerSearchData.get(CNConsumerSearch.SEARCH_BY),
					rstConsumerSearchData.get(CNConsumerSearch.CONSUMER_ID), location,
					rstConsumerSearchData.get(CNConsumerSearch.STATUS));
			foundation.click(consumerSearch.objCell(rstConsumerSearchData.get(CNConsumerSearch.FIRST_NAME)));
			assertTrue(dropDown.getAllItems(ConsumerSummary.DPD_PAY_CYCLE).contains(paycycle.get(1)));
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		finally {
			//delete pay-cycle and verify the display in consumer page
			login.logout();
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItem.get(0));
			locationSummary.editPaycyle(location, paycycle.get(1),paycycle.get(0));
			navigationBar.navigateToMenuItem(menuItem.get(1));
			consumerSearch.enterSearchFields(rstConsumerSearchData.get(CNConsumerSearch.SEARCH_BY),
					rstConsumerSearchData.get(CNConsumerSearch.CONSUMER_ID), location,
					rstConsumerSearchData.get(CNConsumerSearch.STATUS));
			foundation.click(consumerSearch.objCell(rstConsumerSearchData.get(CNConsumerSearch.FIRST_NAME)));
			assertTrue(dropDown.getAllItems(ConsumerSummary.DPD_PAY_CYCLE).contains(paycycle.get(0)));
		}
	}
	
	@Test(description = "165199-QAA-88-Verify moved Active Consumer details is displays Correct in History (Org movement)")
	public void verifyConsumerOrgMovementHistory() {
		final String CASE_NUM = "165199";
	
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstConsumerSearchData = dataBase.getConsumerSearchData(Queries.CONSUMER_SEARCH, CASE_NUM);
		List<String> menuItem = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			
			navigationBar.navigateToMenuItem(menuItem.get(0));
			dropDown.selectItem(ConsumerMove.DPD_ORG, propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE), Constants.TEXT);
			foundation.threadWait(Constants.THREE_SECOND);
			dropDown.selectItem(ConsumerMove.DPD_LOCATION, propertyFile.readPropertyFile(Configuration.CURRENT_LOC , FilePath.PROPERTY_CONFIG_FILE), Constants.TEXT);
			foundation.click(ConsumerMove.BTN_GO);
			textBox.enterText(ConsumerMove.TXT_SEARCH_FILTER, rstConsumerSearchData.get(CNConsumerSearch.SEARCH));
			foundation.waitforElement(ConsumerMove.BTN_MOVE, Constants.SHORT_TIME);
			String notFound=foundation.getText(ConsumerMove.TBL_CONSUMER_ROW);
			
			
			if(!notFound.equals(rstConsumerSearchData.get(CNConsumerSearch.COLUMN_NAME))) {
			
			dropDown.selectItem(ConsumerMove.DPD_ORG, propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE), Constants.TEXT);
			foundation.threadWait(Constants.THREE_SECOND);
			dropDown.selectItem(ConsumerMove.DPD_LOCATION, propertyFile.readPropertyFile(Configuration.CURRENT_LOC , FilePath.PROPERTY_CONFIG_FILE), Constants.TEXT);
			foundation.click(ConsumerMove.BTN_GO);
			textBox.enterText(ConsumerMove.TXT_SEARCH_FILTER, rstConsumerSearchData.get(CNConsumerSearch.SEARCH));
			foundation.waitforElement(ConsumerMove.BTN_MOVE, Constants.SHORT_TIME);
			table.selectRow(rstConsumerSearchData.get(CNConsumerSearch.SEARCH));
			
			foundation.click(ConsumerMove.BTN_MOVE);
			foundation.waitforElement(ConsumerMove.BTN_MOVE_LIST_OK, Constants.SHORT_TIME);
			foundation.click(ConsumerMove.BTN_MOVE_LIST_OK);
			
			foundation.waitforElement(ConsumerMove.BTN_SAVE, Constants.SHORT_TIME);
			dropDown.selectItem(ConsumerMove.DPD_MOVE_FROM_ORG,propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE), Constants.TEXT);
			foundation.threadWait(Constants.THREE_SECOND);
			dropDown.selectItem(ConsumerMove.DPD_MOVE_FROM_LOCATION, propertyFile.readPropertyFile(Configuration.SECOND_LOC, FilePath.PROPERTY_CONFIG_FILE), Constants.TEXT);
			}else{
				
				dropDown.selectItem(ConsumerMove.DPD_ORG, propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE), Constants.TEXT);
				foundation.threadWait(Constants.THREE_SECOND);
				dropDown.selectItem(ConsumerMove.DPD_LOCATION, propertyFile.readPropertyFile(Configuration.SECOND_LOC, FilePath.PROPERTY_CONFIG_FILE), Constants.TEXT);
				foundation.click(ConsumerMove.BTN_GO);
				textBox.enterText(ConsumerMove.TXT_SEARCH_FILTER,rstConsumerSearchData.get(CNConsumerSearch.SEARCH));
				table.selectRow(rstConsumerSearchData.get(CNConsumerSearch.SEARCH));
				
				foundation.click(ConsumerMove.BTN_MOVE);
				foundation.waitforElement(ConsumerMove.BTN_MOVE_LIST_OK, Constants.SHORT_TIME);
				foundation.click(ConsumerMove.BTN_MOVE_LIST_OK);
				
				foundation.waitforElement(ConsumerMove.BTN_SAVE, Constants.SHORT_TIME);
				dropDown.selectItem(ConsumerMove.DPD_MOVE_FROM_ORG, propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE), Constants.TEXT);
				foundation.threadWait(Constants.THREE_SECOND);
				dropDown.selectItem(ConsumerMove.DPD_MOVE_FROM_LOCATION, propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE), Constants.TEXT);
			}
			
			foundation.click(ConsumerMove.BTN_SAVE);
			foundation.waitforElement(ConsumerMove.BTN_EXPORT,Constants.SHORT_TIME);
			String message= foundation.getText(ConsumerMove.LBL_COMPLETE_MOVE);
			assertTrue(message.equals(rstConsumerSearchData.get(CNConsumerSearch.TITLE)));
			
			//commented as reflecting moved Consumer will take 30 min 
//			String path = consumerMove.getFileName();	
//			assertTrue(foundation.isDisplayed(ConsumerMove.BTN_EXPORT));
//			boolean fileExists = foundation.isFileExists(path);
//			if (fileExists == false) {
//				foundation.deleteFile(path);
//			}
//			
//			foundation.click(ConsumerMove.BTN_EXPORT);
//			foundation.threadWait(Constants.SHORT_TIME);
//			// download assertion
//			Assert.assertTrue(excel.isFileDownloaded(path));
//
//			Map<String, String> singleRowData= consumerMove.getExcelDataAsMap(path);
//			System.out.println(singleRowData.get(rstConsumerSearchData.get(CNConsumerSearch.CONSUMER_ID)));
//			
//			navigationBar.selectOrganization(
//					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
//			navigationBar.navigateToMenuItem(menuItem.get(1));
//			
//			//search consumer
//			foundation.waitforElement(ConsumerSearch.DPD_SEARCH_BY, Constants.SHORT_TIME);
//			// Enter fields in Consumer Search Page
//			consumerSearch.enterSearchFields(rstConsumerSearchData.get(CNConsumerSearch.SEARCH_BY), rstConsumerSearchData.get(CNConsumerSearch.SEARCH), propertyFile.readPropertyFile(Configuration.SECOND_LOC, FilePath.PROPERTY_CONFIG_FILE), rstConsumerSearchData.get(CNConsumerSearch.STATUS));
//			
//			Map<String, String> tableData = table.getTblSingleRowRecordUI(ConsumerSearch.TBL_LOCATION,
//					ConsumerSearch.TBL_ROW);
//			
//			assertTrue(tableData.containsValue(rstConsumerSearchData.get(CNConsumerSearch.SEARCH)));
//			
//			navigationBar.navigateToMenuItem(menuItem.get(0));
//			foundation.click(ConsumerMove.BTN_HISTORY);
//			textBox.enterText(ConsumerMoveHistory.TXT_FILTER, singleRowData.get(rstConsumerSearchData.get(CNConsumerSearch.CONSUMER_ID)));
//			Map<String, String> moveHistory = table.getTblSingleRowRecordUI(ConsumerMoveHistory.TBL_MOVE_HISTORY,
//					ConsumerMoveHistory.TBL_HISTORY_DATA);
//			assertTrue(moveHistory.containsValue(singleRowData.get(rstConsumerSearchData.get(CNConsumerSearch.CONSUMER_ID))));

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
	
	@Test(description = "165197-QAA-88-Move Active Consumer from One Org to Another Org and Verify the Consumer is moved")
	public void verifyConsumerOrgMovement() {
		final String CASE_NUM = "165197";
	
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstConsumerSearchData = dataBase.getConsumerSearchData(Queries.CONSUMER_SEARCH, CASE_NUM);
		List<String> menuItem = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			
			navigationBar.navigateToMenuItem(menuItem.get(0));
			dropDown.selectItem(ConsumerMove.DPD_ORG, propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE), Constants.TEXT);
			foundation.threadWait(Constants.THREE_SECOND);
			dropDown.selectItem(ConsumerMove.DPD_LOCATION, propertyFile.readPropertyFile(Configuration.CURRENT_LOC , FilePath.PROPERTY_CONFIG_FILE), Constants.TEXT);
			foundation.click(ConsumerMove.BTN_GO);
			textBox.enterText(ConsumerMove.TXT_SEARCH_FILTER, rstConsumerSearchData.get(CNConsumerSearch.SEARCH));
			foundation.waitforElement(ConsumerMove.BTN_MOVE, Constants.SHORT_TIME);
			String notFound=foundation.getText(ConsumerMove.TBL_CONSUMER_ROW);
			
			
			if(!notFound.equals(rstConsumerSearchData.get(CNConsumerSearch.COLUMN_NAME))) {
			
			dropDown.selectItem(ConsumerMove.DPD_ORG, propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE), Constants.TEXT);
			foundation.threadWait(Constants.THREE_SECOND);
			dropDown.selectItem(ConsumerMove.DPD_LOCATION, propertyFile.readPropertyFile(Configuration.CURRENT_LOC , FilePath.PROPERTY_CONFIG_FILE), Constants.TEXT);
			foundation.click(ConsumerMove.BTN_GO);
			textBox.enterText(ConsumerMove.TXT_SEARCH_FILTER, rstConsumerSearchData.get(CNConsumerSearch.SEARCH));
			foundation.waitforElement(ConsumerMove.BTN_MOVE, Constants.SHORT_TIME);
			table.selectRow(rstConsumerSearchData.get(CNConsumerSearch.SEARCH));
			
			foundation.click(ConsumerMove.BTN_MOVE);
			foundation.waitforElement(ConsumerMove.BTN_MOVE_LIST_OK, Constants.SHORT_TIME);
			foundation.click(ConsumerMove.BTN_MOVE_LIST_OK);
			
			foundation.waitforElement(ConsumerMove.BTN_SAVE, Constants.SHORT_TIME);
			dropDown.selectItem(ConsumerMove.DPD_MOVE_FROM_ORG,propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE), Constants.TEXT);
			foundation.threadWait(Constants.THREE_SECOND);
			dropDown.selectItem(ConsumerMove.DPD_MOVE_FROM_LOCATION, propertyFile.readPropertyFile(Configuration.SECOND_LOC, FilePath.PROPERTY_CONFIG_FILE), Constants.TEXT);
			}else{
				
				dropDown.selectItem(ConsumerMove.DPD_ORG, propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE), Constants.TEXT);
				foundation.threadWait(Constants.THREE_SECOND);
				dropDown.selectItem(ConsumerMove.DPD_LOCATION, propertyFile.readPropertyFile(Configuration.SECOND_LOC, FilePath.PROPERTY_CONFIG_FILE), Constants.TEXT);
				foundation.click(ConsumerMove.BTN_GO);
				textBox.enterText(ConsumerMove.TXT_SEARCH_FILTER,rstConsumerSearchData.get(CNConsumerSearch.SEARCH));
				table.selectRow(rstConsumerSearchData.get(CNConsumerSearch.SEARCH));
				
				foundation.click(ConsumerMove.BTN_MOVE);
				foundation.waitforElement(ConsumerMove.BTN_MOVE_LIST_OK, Constants.SHORT_TIME);
				foundation.click(ConsumerMove.BTN_MOVE_LIST_OK);
				
				foundation.waitforElement(ConsumerMove.BTN_SAVE, Constants.SHORT_TIME);
				dropDown.selectItem(ConsumerMove.DPD_MOVE_FROM_ORG, propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE), Constants.TEXT);
				foundation.threadWait(Constants.THREE_SECOND);
				dropDown.selectItem(ConsumerMove.DPD_MOVE_FROM_LOCATION, propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE), Constants.TEXT);
			}
			
			foundation.click(ConsumerMove.BTN_SAVE);
			foundation.waitforElement(ConsumerMove.BTN_EXPORT,Constants.SHORT_TIME);
			String message= foundation.getText(ConsumerMove.LBL_COMPLETE_MOVE);
			assertTrue(message.equals(rstConsumerSearchData.get(CNConsumerSearch.TITLE)));
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
	
	@Test(description = "165200-QAA-88-Move Active Consumer from One Location to Another Location within the same Org and Verify the Consumer is moved")
	public void verifyConsumerLocationMovement() {
		final String CASE_NUM = "165200";
	
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstConsumerSearchData = dataBase.getConsumerSearchData(Queries.CONSUMER_SEARCH, CASE_NUM);
		List<String> menuItem = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			
			navigationBar.navigateToMenuItem(menuItem.get(0));
			dropDown.selectItem(ConsumerMove.DPD_ORG, propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE), Constants.TEXT);
			foundation.threadWait(Constants.THREE_SECOND);
			dropDown.selectItem(ConsumerMove.DPD_LOCATION, propertyFile.readPropertyFile(Configuration.CURRENT_LOC , FilePath.PROPERTY_CONFIG_FILE), Constants.TEXT);
			foundation.click(ConsumerMove.BTN_GO);
			textBox.enterText(ConsumerMove.TXT_SEARCH_FILTER, rstConsumerSearchData.get(CNConsumerSearch.SEARCH));
			foundation.waitforElement(ConsumerMove.BTN_MOVE, Constants.SHORT_TIME);
			String notFound=foundation.getText(ConsumerMove.TBL_CONSUMER_ROW);
			
			
			if(!notFound.equals(rstConsumerSearchData.get(CNConsumerSearch.COLUMN_NAME))) {
			
			dropDown.selectItem(ConsumerMove.DPD_ORG, propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE), Constants.TEXT);
			foundation.threadWait(Constants.THREE_SECOND);
			dropDown.selectItem(ConsumerMove.DPD_LOCATION, propertyFile.readPropertyFile(Configuration.CURRENT_LOC , FilePath.PROPERTY_CONFIG_FILE), Constants.TEXT);
			foundation.click(ConsumerMove.BTN_GO);
			textBox.enterText(ConsumerMove.TXT_SEARCH_FILTER, rstConsumerSearchData.get(CNConsumerSearch.SEARCH));
			foundation.waitforElement(ConsumerMove.BTN_MOVE, Constants.SHORT_TIME);
			table.selectRow(rstConsumerSearchData.get(CNConsumerSearch.SEARCH));
			
			foundation.click(ConsumerMove.BTN_MOVE);
			foundation.waitforElement(ConsumerMove.BTN_MOVE_LIST_OK, Constants.SHORT_TIME);
			foundation.click(ConsumerMove.BTN_MOVE_LIST_OK);
			
			foundation.waitforElement(ConsumerMove.BTN_SAVE, Constants.SHORT_TIME);
			dropDown.selectItem(ConsumerMove.DPD_MOVE_FROM_ORG,propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE), Constants.TEXT);
			foundation.threadWait(Constants.THREE_SECOND);
			dropDown.selectItem(ConsumerMove.DPD_MOVE_FROM_LOCATION, rstConsumerSearchData.get(CNConsumerSearch.LOCATION), Constants.TEXT);
			}else{
				
				dropDown.selectItem(ConsumerMove.DPD_ORG, propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE), Constants.TEXT);
				foundation.threadWait(Constants.THREE_SECOND);
				dropDown.selectItem(ConsumerMove.DPD_LOCATION,rstConsumerSearchData.get(CNConsumerSearch.LOCATION), Constants.TEXT);
				foundation.click(ConsumerMove.BTN_GO);
				textBox.enterText(ConsumerMove.TXT_SEARCH_FILTER,rstConsumerSearchData.get(CNConsumerSearch.SEARCH));
				table.selectRow(rstConsumerSearchData.get(CNConsumerSearch.SEARCH));
				
				foundation.click(ConsumerMove.BTN_MOVE);
				foundation.waitforElement(ConsumerMove.BTN_MOVE_LIST_OK, Constants.SHORT_TIME);
				foundation.click(ConsumerMove.BTN_MOVE_LIST_OK);
				
				foundation.waitforElement(ConsumerMove.BTN_SAVE, Constants.SHORT_TIME);
				dropDown.selectItem(ConsumerMove.DPD_MOVE_FROM_ORG, propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE), Constants.TEXT);
				foundation.threadWait(Constants.THREE_SECOND);
				dropDown.selectItem(ConsumerMove.DPD_MOVE_FROM_LOCATION, propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE), Constants.TEXT);
			}
			
			foundation.click(ConsumerMove.BTN_SAVE);
			foundation.waitforElement(ConsumerMove.BTN_EXPORT,Constants.SHORT_TIME);
			String message= foundation.getText(ConsumerMove.LBL_COMPLETE_MOVE);
			assertTrue(message.equals(rstConsumerSearchData.get(CNConsumerSearch.TITLE)));
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
	
	@Test(description = "165206-QAA-88-Move closed Consumer from One Org to Another Org and Verify the Consumer is moved")
	public void verifyClosedConsumerOrgMovement() {
		final String CASE_NUM = "165206";
	
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstConsumerSearchData = dataBase.getConsumerSearchData(Queries.CONSUMER_SEARCH, CASE_NUM);
		List<String> menuItem = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			
			navigationBar.navigateToMenuItem(menuItem.get(0));
			dropDown.selectItem(ConsumerMove.DPD_ORG, propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE), Constants.TEXT);
			foundation.threadWait(Constants.THREE_SECOND);
			dropDown.selectItem(ConsumerMove.DPD_LOCATION, propertyFile.readPropertyFile(Configuration.CURRENT_LOC , FilePath.PROPERTY_CONFIG_FILE), Constants.TEXT);
			foundation.click(ConsumerMove.BTN_GO);
			textBox.enterText(ConsumerMove.TXT_SEARCH_FILTER, rstConsumerSearchData.get(CNConsumerSearch.SEARCH));
			foundation.waitforElement(ConsumerMove.BTN_MOVE, Constants.SHORT_TIME);
			String notFound=foundation.getText(ConsumerMove.TBL_CONSUMER_ROW);
			
			
			if(!notFound.equals(rstConsumerSearchData.get(CNConsumerSearch.COLUMN_NAME))) {
			
			dropDown.selectItem(ConsumerMove.DPD_ORG, propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE), Constants.TEXT);
			foundation.threadWait(Constants.THREE_SECOND);
			dropDown.selectItem(ConsumerMove.DPD_LOCATION, propertyFile.readPropertyFile(Configuration.CURRENT_LOC , FilePath.PROPERTY_CONFIG_FILE), Constants.TEXT);
			foundation.click(ConsumerMove.BTN_GO);
			textBox.enterText(ConsumerMove.TXT_SEARCH_FILTER, rstConsumerSearchData.get(CNConsumerSearch.SEARCH));
			foundation.waitforElement(ConsumerMove.BTN_MOVE, Constants.SHORT_TIME);
			table.selectRow(rstConsumerSearchData.get(CNConsumerSearch.SEARCH));
			
			foundation.click(ConsumerMove.BTN_MOVE);
			foundation.waitforElement(ConsumerMove.BTN_MOVE_LIST_OK, Constants.SHORT_TIME);
			foundation.click(ConsumerMove.BTN_MOVE_LIST_OK);
			
			foundation.waitforElement(ConsumerMove.BTN_SAVE, Constants.SHORT_TIME);
			dropDown.selectItem(ConsumerMove.DPD_MOVE_FROM_ORG,propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE), Constants.TEXT);
			foundation.threadWait(Constants.THREE_SECOND);
			dropDown.selectItem(ConsumerMove.DPD_MOVE_FROM_LOCATION, propertyFile.readPropertyFile(Configuration.SECOND_LOC, FilePath.PROPERTY_CONFIG_FILE), Constants.TEXT);
			}else{
				
				dropDown.selectItem(ConsumerMove.DPD_ORG, propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE), Constants.TEXT);
				foundation.threadWait(Constants.THREE_SECOND);
				dropDown.selectItem(ConsumerMove.DPD_LOCATION, propertyFile.readPropertyFile(Configuration.SECOND_LOC, FilePath.PROPERTY_CONFIG_FILE), Constants.TEXT);
				foundation.click(ConsumerMove.BTN_GO);
				textBox.enterText(ConsumerMove.TXT_SEARCH_FILTER,rstConsumerSearchData.get(CNConsumerSearch.SEARCH));
				table.selectRow(rstConsumerSearchData.get(CNConsumerSearch.SEARCH));
				
				foundation.click(ConsumerMove.BTN_MOVE);
				foundation.waitforElement(ConsumerMove.BTN_MOVE_LIST_OK, Constants.SHORT_TIME);
				foundation.click(ConsumerMove.BTN_MOVE_LIST_OK);
				
				foundation.waitforElement(ConsumerMove.BTN_SAVE, Constants.SHORT_TIME);
				dropDown.selectItem(ConsumerMove.DPD_MOVE_FROM_ORG, propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE), Constants.TEXT);
				foundation.threadWait(Constants.THREE_SECOND);
				dropDown.selectItem(ConsumerMove.DPD_MOVE_FROM_LOCATION, propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE), Constants.TEXT);
			}
			
			foundation.click(ConsumerMove.BTN_SAVE);
			foundation.waitforElement(ConsumerMove.BTN_EXPORT,Constants.SHORT_TIME);
			String message= foundation.getText(ConsumerMove.LBL_COMPLETE_MOVE);
			assertTrue(message.equals(rstConsumerSearchData.get(CNConsumerSearch.TITLE)));
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
	
	@Test(description = "165207-QAA-88-Move closed Consumer from One Location to Another Location within the same Org and Verify the Consumer is moved")
	public void verifyClosedConsumerLocationMovement() {
		final String CASE_NUM = "165207";
	
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstConsumerSearchData = dataBase.getConsumerSearchData(Queries.CONSUMER_SEARCH, CASE_NUM);
		List<String> menuItem = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			
			navigationBar.navigateToMenuItem(menuItem.get(0));
			dropDown.selectItem(ConsumerMove.DPD_ORG, propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE), Constants.TEXT);
			foundation.threadWait(Constants.THREE_SECOND);
			dropDown.selectItem(ConsumerMove.DPD_LOCATION, propertyFile.readPropertyFile(Configuration.CURRENT_LOC , FilePath.PROPERTY_CONFIG_FILE), Constants.TEXT);
			foundation.click(ConsumerMove.BTN_GO);
			textBox.enterText(ConsumerMove.TXT_SEARCH_FILTER, rstConsumerSearchData.get(CNConsumerSearch.SEARCH));
			foundation.waitforElement(ConsumerMove.BTN_MOVE, Constants.SHORT_TIME);
			String notFound=foundation.getText(ConsumerMove.TBL_CONSUMER_ROW);
			
			
			if(!notFound.equals(rstConsumerSearchData.get(CNConsumerSearch.COLUMN_NAME))) {
			
			dropDown.selectItem(ConsumerMove.DPD_ORG, propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE), Constants.TEXT);
			foundation.threadWait(Constants.THREE_SECOND);
			dropDown.selectItem(ConsumerMove.DPD_LOCATION, propertyFile.readPropertyFile(Configuration.CURRENT_LOC , FilePath.PROPERTY_CONFIG_FILE), Constants.TEXT);
			foundation.click(ConsumerMove.BTN_GO);
			textBox.enterText(ConsumerMove.TXT_SEARCH_FILTER, rstConsumerSearchData.get(CNConsumerSearch.SEARCH));
			foundation.waitforElement(ConsumerMove.BTN_MOVE, Constants.SHORT_TIME);
			table.selectRow(rstConsumerSearchData.get(CNConsumerSearch.SEARCH));
			
			foundation.click(ConsumerMove.BTN_MOVE);
			foundation.waitforElement(ConsumerMove.BTN_MOVE_LIST_OK, Constants.SHORT_TIME);
			foundation.click(ConsumerMove.BTN_MOVE_LIST_OK);
			
			foundation.waitforElement(ConsumerMove.BTN_SAVE, Constants.SHORT_TIME);
			dropDown.selectItem(ConsumerMove.DPD_MOVE_FROM_ORG,propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE), Constants.TEXT);
			foundation.threadWait(Constants.THREE_SECOND);
			dropDown.selectItem(ConsumerMove.DPD_MOVE_FROM_LOCATION, rstConsumerSearchData.get(CNConsumerSearch.LOCATION), Constants.TEXT);
			}else{
				
				dropDown.selectItem(ConsumerMove.DPD_ORG, propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE), Constants.TEXT);
				foundation.threadWait(Constants.THREE_SECOND);
				dropDown.selectItem(ConsumerMove.DPD_LOCATION,rstConsumerSearchData.get(CNConsumerSearch.LOCATION), Constants.TEXT);
				foundation.click(ConsumerMove.BTN_GO);
				textBox.enterText(ConsumerMove.TXT_SEARCH_FILTER,rstConsumerSearchData.get(CNConsumerSearch.SEARCH));
				table.selectRow(rstConsumerSearchData.get(CNConsumerSearch.SEARCH));
				
				foundation.click(ConsumerMove.BTN_MOVE);
				foundation.waitforElement(ConsumerMove.BTN_MOVE_LIST_OK, Constants.SHORT_TIME);
				foundation.click(ConsumerMove.BTN_MOVE_LIST_OK);
				
				foundation.waitforElement(ConsumerMove.BTN_SAVE, Constants.SHORT_TIME);
				dropDown.selectItem(ConsumerMove.DPD_MOVE_FROM_ORG, propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE), Constants.TEXT);
				foundation.threadWait(Constants.THREE_SECOND);
				dropDown.selectItem(ConsumerMove.DPD_MOVE_FROM_LOCATION, propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE), Constants.TEXT);
			}
			
			foundation.click(ConsumerMove.BTN_SAVE);
			foundation.waitforElement(ConsumerMove.BTN_EXPORT,Constants.SHORT_TIME);
			String message= foundation.getText(ConsumerMove.LBL_COMPLETE_MOVE);
			assertTrue(message.equals(rstConsumerSearchData.get(CNConsumerSearch.TITLE)));
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
}