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

import at.framework.database.DBConnections;
import at.framework.database.DataBase;
import at.framework.generic.DateAndTime;
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

@Listeners(at.framework.reportsSetup.Listeners.class)
public class Report extends TestInfra {
	private DataBase dataBase = new DataBase();
	private DBConnections dbConnections = new DBConnections();
	private NavigationBar navigationBar = new NavigationBar();
	private ConsumerSearch consumerSearch = new ConsumerSearch();
	private ConsumerSummary consumerSummary = new ConsumerSummary();
	private Foundation foundation = new Foundation();
	private TextBox textBox = new TextBox();
	private Dropdown dropdown = new Dropdown();
	private DateAndTime dateAndTime = new DateAndTime();
	private ReportList reportList = new ReportList();
	private AccountAdjustment accountAdjustment = new AccountAdjustment();
	private CurrenyConverter converter = new CurrenyConverter();
	
	private Map<String, String> rstNavigationMenuData;
	private Map<String, String> rstConsumerSearchData;
	private Map<String, String> rstProductSummaryData;
	private Map<String, String> rstLocationSummaryData;
	private Map<String, String> rstConsumerSummaryData;
	private Map<String, String> rstReportListData;
	
	@Test(description = "This test validates account adjustment report")
	public void accountAdjustmentReport() throws SQLException {
		try {
			Map<String, String> dbData = new HashMap<>();

			final String CASE_NUM = "119928";
			browser.navigateURL(propertyFile.readConfig(KeysConfiguration.CURRENT_URL,TestDataFilesPaths.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readConfig(KeysConfiguration.CURRENT_USER,TestDataFilesPaths.PROPERTY_CONFIG_FILE), propertyFile.readConfig(KeysConfiguration.CURRENT_PASSWORD,TestDataFilesPaths.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(dbConnections.getNavigationMenu(), CASE_NUM);
			rstConsumerSearchData = dataBase.getConsumerSearchData(dbConnections.getConsumersearch(), CASE_NUM);
			rstProductSummaryData = dataBase.getProductSummaryData(dbConnections.getProductsummary(), CASE_NUM);
			rstLocationSummaryData = dataBase.getLocationSummaryData(dbConnections.getLocationsummary(), CASE_NUM);
			rstConsumerSummaryData = dataBase.getConsumerSummaryData(dbConnections.getConsumersummary(), CASE_NUM);
			rstReportListData = dataBase.getReportListData(dbConnections.getReportsList(), CASE_NUM);

			// Select Menu and Menu Item
			navigationBar.selectOrginazation(propertyFile.readConfig(KeysConfiguration.CURRENT_ORG,TestDataFilesPaths.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(NavigationBar.mnuAdmin, rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			
			// Enter fields in Consumer Search Page
			consumerSearch.enterSearchFields(rstConsumerSearchData.get(CNConsumerSearch.SEARCH_BY),
					rstConsumerSearchData.get(CNConsumerSearch.CONSUMER_ID),
					propertyFile.readConfig(KeysConfiguration.CURRENT_LOC,TestDataFilesPaths.PROPERTY_CONFIG_FILE), rstConsumerSearchData.get(CNConsumerSearch.STATUS));

			// Split database data
			List<String> requiredData = Arrays
					.asList(rstProductSummaryData.get(CNProductSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			
			consumerSearch.clickCell(requiredData.get(5));
			
			List<String> columnName = Arrays.asList(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME).split(Constants.DELIMITER_TILD));
			List<String> tblColumnHeader = Arrays.asList(columnName.get(1).split(Constants.DELIMITER_HASH));
			
			// reading Balance and add to the array list
			double initialbalance = consumerSummary.getBalance();
			dbData.put(tblColumnHeader.get(7), converter.convertTOCurrency(initialbalance));
			
			foundation.click(ConsumerSummary.btnAdjust);
 
			// converting string to double and adding the adjusted value
			double adustedBalance = Double.parseDouble(rstConsumerSummaryData.get(CNConsumerSummary.ADJUST_BALANCE));
			double updatedbalance = initialbalance
					+ Double.parseDouble(rstConsumerSummaryData.get(CNConsumerSummary.ADJUST_BALANCE));
			textBox.enterText(ConsumerSummary.txtAdjustBalance, Double.toString(updatedbalance));

			dropdown.selectItem(ConsumerSummary.dpdReason, rstConsumerSummaryData.get(CNConsumerSummary.REASON),
					Constants.TEXT);

			foundation.click(ConsumerSummary.btnSave);

			// converting time zone to specific time zone
			String updatedTime = String.valueOf(dateAndTime.getDateBasedOnZone(dateAndTime.getCurrentDate(),
					rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION),
					rstLocationSummaryData.get(CNLocationSummary.TIME_ZONE)));
			
			// Navigate to Reports
			foundation.click(NavigationBar.mnuReports);

			// Selecting the Date range and Location for running report
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));

			reportList.selectLocation(propertyFile.readConfig(KeysConfiguration.CURRENT_LOC,TestDataFilesPaths.PROPERTY_CONFIG_FILE));

			foundation.click(ReportList.btnRunReport);

			// Validate Account Adjustment Report Title
			String reportName = foundation.getText(AccountAdjustment.lblReportName);
			Assert.assertTrue(reportName.contains(rstReportListData.get(CNReportList.REPORT_NAME)));			

			// Add db data to Array list
			dbData.put(tblColumnHeader.get(1), propertyFile.readConfig(KeysConfiguration.CURRENT_LOC,TestDataFilesPaths.PROPERTY_CONFIG_FILE));
			dbData.put(tblColumnHeader.get(2), requiredData.get(0));
			dbData.put(tblColumnHeader.get(3), requiredData.get(1));
			dbData.put(tblColumnHeader.get(4), rstConsumerSearchData.get(CNConsumerSearch.CONSUMER_ID));
			dbData.put(tblColumnHeader.get(5), requiredData.get(2));
			dbData.put(tblColumnHeader.get(6), requiredData.get(3));

			dbData.put(tblColumnHeader.get(8), converter.convertTOCurrency(updatedbalance));
			dbData.put(tblColumnHeader.get(9), converter.convertTOCurrency(adustedBalance));
			dbData.put(CNConsumerSummary.REASON, String.valueOf(rstConsumerSummaryData.get(CNConsumerSummary.REASON)));
			dbData.put(tblColumnHeader.get(11), requiredData.get(4));
			
			dbData.put(tblColumnHeader.get(0), String.valueOf(updatedTime));

			textBox.enterText(AccountAdjustment.txtSearch, String.valueOf(updatedTime));

			// Storing UI data in iuData Map
			Map<String, String> uiData = accountAdjustment.getTblRecordsUI();

			// Validate account adjustment adjusted report data
			assertEquals(uiData, dbData);
			
		} catch (Exception exc) {
			Assert.fail();
		}
	}

}
