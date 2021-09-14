package at.smartshop.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertTrue;

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
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.database.columns.CNConsumerSearch;
import at.smartshop.database.columns.CNConsumerSummary;
import at.smartshop.database.columns.CNLocationList;
import at.smartshop.database.columns.CNLocationSummary;
import at.smartshop.database.columns.CNNavigationMenu;
import at.smartshop.database.columns.CNProductSummary;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.pages.ConsumerSearch;
import at.smartshop.pages.ConsumerSummary;
import at.smartshop.pages.LocationList;
import at.smartshop.pages.LocationSummary;
import at.smartshop.pages.Login;
import at.smartshop.pages.NavigationBar;

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
	private LocationSummary locationSummary = new LocationSummary();
	private Numbers numbers=new Numbers();

	private Map<String, String> rstNavigationMenuData;
	private Map<String, String> rstConsumerSearchData;
	private Map<String, String> rstConsumerSummaryData;
	private Map<String, String> rstProductSummaryData;
	private Map<String, String> rstLocationListData;
	private Map<String, String> rstLocationSummaryData;

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

			browser.navigateURL(	propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
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
			Map<String, String> consumerTblRecords = consumerSearch.getConsumerRecords(rstConsumerSearchData.get(CNConsumerSearch.LOCATION));
			String balance = consumerTblRecords.get(columnName);
			String balance1 = balance.substring(1);
			Double newBalance = Double.parseDouble(balance1) + 2;
			String expectedBalance = "$" + String.valueOf(String.format("%.2f", newBalance));

			// clicking consumer id
			foundation.click(consumerSearch.objCell(firstName));
			foundation.click(ConsumerSearch.BTN_ADJUST);

			// enter new balance with reason
			textBox.enterText(ConsumerSummary.TXT_ADJUST_BALANCE, String.valueOf(newBalance));
			dropDown.selectItem(ConsumerSummary.DPD_REASON, rstConsumerSummaryData.get(CNConsumerSummary.REASON),Constants.TEXT);
			foundation.click(ConsumerSummary.BTN_REASON_SAVE);
			foundation.click(ConsumerSummary.BTN_SAVE);

			// Enter fields in Consumer Search Page
			consumerSearch.enterSearchFields(rstConsumerSearchData.get(CNConsumerSearch.SEARCH_BY),
					rstConsumerSearchData.get(CNConsumerSearch.CONSUMER_ID),
					rstConsumerSearchData.get(CNConsumerSearch.LOCATION),
					rstConsumerSearchData.get(CNConsumerSearch.STATUS));

			Map<String, String> consumerTblRecords2 = consumerSearch.getConsumerRecords(rstConsumerSearchData.get(CNConsumerSearch.LOCATION));
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

			Map<String, String> consumerTblRecords3 = consumerSearch.getConsumerRecords(rstConsumerSearchData.get(CNConsumerSearch.LOCATION));
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

			browser.navigateURL(	propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstConsumerSearchData = dataBase.getConsumerSearchData(Queries.CONSUMER_SEARCH, CASE_NUM);
			rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);
			rstConsumerSummaryData = dataBase.getConsumerSummaryData(Queries.CONSUMER_SUMMARY, CASE_NUM);

			// Select Menu and Menu Item
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Enter fields in Consumer Search Page
			consumerSearch.enterSearchFields(rstConsumerSearchData.get(CNConsumerSearch.SEARCH_BY),
					rstConsumerSearchData.get(CNConsumerSearch.CONSUMER_ID),
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE),
					rstConsumerSearchData.get(CNConsumerSearch.STATUS));

			// Split database data
			List<String> requiredData = Arrays.asList(rstProductSummaryData.get(CNProductSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			foundation.click(consumerSearch.objCell(requiredData.get(5)));

			// reading Balance and add to the array list
			double initialbalance = consumerSummary.getBalance();
			foundation.click(ConsumerSummary.BTN_ADJUST);

			// converting string to double and adding the adjusted value
			double updatedbalance = initialbalance+ Double.parseDouble(rstConsumerSummaryData.get(CNConsumerSummary.ADJUST_BALANCE));
			textBox.enterText(ConsumerSummary.TXT_ADJUST_BALANCE, Double.toString(updatedbalance));
			dropDown.selectItem(ConsumerSummary.DPD_REASON, rstConsumerSummaryData.get(CNConsumerSummary.REASON),Constants.TEXT);
			foundation.click(ConsumerSummary.BTN_REASON_CANCEL);
			double balanceAfterCancel = consumerSummary.getBalance();
			assertEquals(balanceAfterCancel, initialbalance);

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
			
			//add pay-cycle group in location summary page
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
			//delete pay-cycle and verify the display in consumer page
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
			
			//add pay-cycle group in location summary page
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
			//delete pay-cycle and verify the display in consumer page
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
			
			//add pay-cycle group in location summary page
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
			//delete pay-cycle and verify the display in consumer page
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
	
}
