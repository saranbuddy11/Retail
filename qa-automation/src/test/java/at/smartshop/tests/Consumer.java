package at.smartshop.tests;

import static org.testng.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import at.framework.database.mssql.Queries;
import at.framework.database.mssql.ResultSets;
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
import at.smartshop.database.columns.CNNavigationMenu;
import at.smartshop.database.columns.CNOrgSummary;
import at.smartshop.database.columns.CNProductSummary;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.pages.ConsumerSearch;
import at.smartshop.pages.ConsumerSummary;
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

	private Map<String, String> rstNavigationMenuData;
	private Map<String, String> rstConsumerSearchData;
	private Map<String, String> rstConsumerSummaryData;
	private Map<String, String> rstProductSummaryData;
	private Map<String, String> rstConsumerData;
	private Map<String, String> rstOrgSummaryData;

	@Test(description = "116743-Verify Balance Increment with and without Reason Code")
	public void verifyBalanceIncrement() {
		try {
			final String CASE_NUM = "116743";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from database
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstConsumerSearchData = dataBase.getConsumerSearchData(Queries.CONSUMER_SEARCH, CASE_NUM);
			rstConsumerSummaryData = dataBase.getConsumerSummaryData(Queries.CONSUMER_SUMMARY, CASE_NUM);

			String firstName = rstConsumerSummaryData.get(CNConsumerSummary.FIRST_NAME);
			String columnName = rstConsumerSearchData.get(CNConsumerSearch.COLUMN_NAME);

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

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
			String expectedBalance = "$" + String.valueOf(newBalance);

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
			String expectedBalance2 = "$" + String.valueOf(newBalance2);

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

		} catch (Exception exc) {
			exc.printStackTrace();
			Assert.fail();
		}
	}

	@Test(description = "116747-Cancel Adjust Balance")
	public void cancelAdjustBalance() {
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

		} catch (Exception exc) {
			Assert.fail(exc.toString());
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
				String ren = countries.get(i);
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

		} catch (Exception exc) {
			Assert.fail(exc.toString());
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

		} catch (

		Exception exc) {
			Assert.fail(exc.toString());
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

		} catch (

		Exception exc) {
			Assert.fail(exc.toString());
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

		} catch (Exception exc) {
			Assert.fail(exc.toString());
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

		} catch (Exception exc) {
			Assert.fail(exc.toString());
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

		} catch (Exception exc) {
			Assert.fail(exc.toString());
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
		} catch (Exception exc) {
			Assert.fail(exc.toString());
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

		} catch (Exception exc) {
			Assert.fail(exc.toString());
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

		} catch (Exception exc) {
			Assert.fail(exc.toString());
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

		} catch (Exception exc) {
			Assert.fail(exc.toString());
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

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
}
