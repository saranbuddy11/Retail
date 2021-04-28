package at.smartshop.tests;

import static org.testng.Assert.assertEquals;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import at.framework.browser.Factory;
import at.framework.database.DBConnections;
import at.framework.generic.DateAndTime;
import at.framework.generic.PropertyFile;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.database.columns.CNConsumerSearch;
import at.smartshop.database.columns.CNConsumerSummary;
import at.smartshop.database.columns.CNLocationSummary;
import at.smartshop.database.columns.CNNavigationMenu;
import at.smartshop.database.columns.CNProductSummary;
import at.smartshop.database.columns.CNReportList;
import at.smartshop.keys.Constants;
import at.smartshop.keys.KeysConfiguration;
import at.smartshop.pages.AccountAdjustment;
import at.smartshop.pages.ConsumerSearch;
import at.smartshop.pages.ConsumerSummary;
import at.smartshop.pages.NavigationBar;
import at.smartshop.pages.ReportList;
import at.smartshop.testData.TestDataFilesPaths;
import at.smartshop.utilities.CurrenyConverter;
import at.smartshop.utilities.DataBase;

@Listeners(at.framework.reports.Listeners.class)
public class Report extends TestInfra {
	DataBase db = new DataBase();
	DBConnections dBConnections = new DBConnections();
	NavigationBar navigationBar = new NavigationBar();
	ConsumerSearch consumerSearch = new ConsumerSearch();
	ConsumerSummary consumerSummary = new ConsumerSummary();
	Foundation foundation = new Foundation();
	TextBox textBox = new TextBox();
	Dropdown dropdown = new Dropdown();
	DateAndTime dateAndTime = new DateAndTime();
	ReportList reportList = new ReportList();
	AccountAdjustment accountAdjustment = new AccountAdjustment();
	CurrenyConverter converter = new CurrenyConverter();
	PropertyFile propertyFile= new PropertyFile();
	Factory fact = new Factory();

	private Map<String, String> rstNavigationMenuData;
	private Map<String, String> rstConsumerSearchData;
	private Map<String, String> rstProductSummaryData;
	private Map<String, String> rstLocationSummaryData;
	private Map<String, String> rstConsumerSummaryData;
	private Map<String, String> rstReportListData;
	
	@Test(description = "This test validates account adjustment report")
	public void AccountAdjustmentReport() throws SQLException {
		try {
			Map<String, String> dbData = new HashMap<>();

			final String CASE_NUM = "119928";
			browser.navigateURL(propertyFile.readConfig(KeysConfiguration.CURRENT_URL,TestDataFilesPaths.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readConfig(KeysConfiguration.CURRENT_USER,TestDataFilesPaths.PROPERTY_CONFIG_FILE), propertyFile.readConfig(KeysConfiguration.CURRENT_PASSWORD,TestDataFilesPaths.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = db.getNavigationMenuData(dBConnections.navigationMenu, CASE_NUM);
			rstConsumerSearchData = db.getConsumerSearchData(dBConnections.consumersearch, CASE_NUM);
			rstProductSummaryData = db.getProductSummaryData(dBConnections.productsummary, CASE_NUM);
			rstLocationSummaryData = db.getLocationSummaryData(dBConnections.locationsummary, CASE_NUM);
			rstConsumerSummaryData = db.getConsumerSummaryData(dBConnections.consumersummary, CASE_NUM);
			rstReportListData = db.getReportListData(dBConnections.reportsList, CASE_NUM);

			// Select Menu and Menu Item
			navigationBar.selectOrginazation(propertyFile.readConfig(KeysConfiguration.CURRENT_ORG,TestDataFilesPaths.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(navigationBar.mnuAdmin, rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			
			// Enter fields in Consumer Search Page
			consumerSearch.enterSearchFields(rstConsumerSearchData.get(CNConsumerSearch.SEARCH_BY),
					rstConsumerSearchData.get(CNConsumerSearch.CONSUMER_ID),
					propertyFile.readConfig(KeysConfiguration.CURRENT_LOC,TestDataFilesPaths.PROPERTY_CONFIG_FILE), rstConsumerSearchData.get(CNConsumerSearch.STATUS));

			// Split database data
			List<String> requiredData = Arrays
					.asList(rstProductSummaryData.get(CNProductSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			
			consumerSearch.clickCell(requiredData.get(5));

			// reading Balance and add to the array list
			double initialbalance = consumerSummary.getBalance();
			dbData.put(CNReportList.INITIAL_BALANCE, converter.convertTOCurrency(initialbalance));

			foundation.click(consumerSummary.btnAdjust);
 
			// converting string to double and adding the adjusted value
			double adustedBalance = Double.parseDouble(rstConsumerSummaryData.get(CNConsumerSummary.ADJUST_BALANCE));
			double updatedbalance = initialbalance
					+ Double.parseDouble(rstConsumerSummaryData.get(CNConsumerSummary.ADJUST_BALANCE));
			textBox.enterText(consumerSummary.txtAdjustBalance, Double.toString(updatedbalance));

			dropdown.selectItem(consumerSummary.dpdReason, rstConsumerSummaryData.get(CNConsumerSummary.REASON),
					Constants.TEXT);

			foundation.click(consumerSummary.btnSave);

			// converting time zone to specific time zone
			String updatedTime = String.valueOf(dateAndTime.getDateBasedOnZone(dateAndTime.getCurrentDate(),
					rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION),
					rstLocationSummaryData.get(CNLocationSummary.TIME_ZONE)));
			
			// Navigate to Reports
			foundation.click(navigationBar.mnuReports);

			// Selecting the Date range and Location for running report
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));

			reportList.selectLocation(propertyFile.readConfig(KeysConfiguration.CURRENT_LOC,TestDataFilesPaths.PROPERTY_CONFIG_FILE));

			foundation.click(reportList.btnRunReport);

			// Validate Account Adjustment Report Title
			String reportName = foundation.getText(accountAdjustment.lblReportName);
			Assert.assertTrue(reportName.contains(rstReportListData.get(CNReportList.REPORT_NAME)));			

			// Add db data to Array list
			dbData.put(CNProductSummary.LOCATION, propertyFile.readConfig(KeysConfiguration.CURRENT_LOC,TestDataFilesPaths.PROPERTY_CONFIG_FILE));
			dbData.put(CNProductSummary.ADJUSTER_ID, requiredData.get(0));
			dbData.put(CNProductSummary.ADJUSTER_NAME, requiredData.get(1));
			dbData.put(CNReportList.CONSUMER_ID, rstConsumerSearchData.get(CNConsumerSearch.CONSUMER_ID));
			dbData.put(CNProductSummary.CONSUMER_NAME, requiredData.get(2));
			dbData.put(CNProductSummary.ACTION, requiredData.get(3));

			dbData.put(CNReportList.UPDATED_BALANACE, converter.convertTOCurrency(updatedbalance));
			dbData.put(CNReportList.ADJUST_BALANCE, converter.convertTOCurrency(adustedBalance));
			dbData.put(CNConsumerSummary.REASON, String.valueOf(rstConsumerSummaryData.get(CNConsumerSummary.REASON)));
			dbData.put(CNProductSummary.REFLECT_ON_EFT, requiredData.get(4));
			
			dbData.put(CNReportList.UPDATED_TIME, String.valueOf(updatedTime));

			textBox.enterText(accountAdjustment.txtSearch, dbData.get(CNReportList.UPDATED_TIME));

			// Storing UI data in iuData Map
			Map<String, String> uiData = accountAdjustment.getTblRecordsUI();

			// Validate account adjustment adjusted report data
			assertEquals(uiData, dbData);
			
		} catch (Exception exc) {
			Assert.fail();
		}
	}

}
