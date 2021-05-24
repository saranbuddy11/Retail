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

import at.framework.database.mssql.Queries;
import at.framework.database.mssql.ResultSets;
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
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.keys.Reports;
import at.smartshop.pages.AccountAdjustment;
import at.smartshop.pages.BadScanReport;
import at.smartshop.pages.ConsumerSearch;
import at.smartshop.pages.ConsumerSummary;
import at.smartshop.pages.EmployeeCompDetailsReport;
import at.smartshop.pages.LocationList;
import at.smartshop.pages.LocationSummary;
import at.smartshop.pages.MemberPurchaseDetailsReport;
import at.smartshop.pages.NavigationBar;
import at.smartshop.pages.ProductPricingReport;
import at.smartshop.pages.ProductTaxReport;
import at.smartshop.pages.ReportList;
import at.smartshop.pages.TransactionCannedReport;
import at.smartshop.utilities.CurrenyConverter;

@Listeners(at.framework.reportsetup.Listeners.class)
public class Report extends TestInfra {
	private ResultSets dataBase = new ResultSets();
	private NavigationBar navigationBar = new NavigationBar();
	private ConsumerSearch consumerSearch = new ConsumerSearch();
	private ConsumerSummary consumerSummary = new ConsumerSummary();
	private LocationList locationList = new LocationList();
	private LocationSummary locationSummary = new LocationSummary();
	private Foundation foundation = new Foundation();
	private TextBox textBox = new TextBox();
	private Dropdown dropdown = new Dropdown();
	private DateAndTime dateAndTime = new DateAndTime();
	private ReportList reportList = new ReportList();
	private AccountAdjustment accountAdjustment = new AccountAdjustment();
	private ProductPricingReport productPricing = new ProductPricingReport();
	private ProductTaxReport productTax = new ProductTaxReport();
	private MemberPurchaseDetailsReport memberPurchaseDetails = new MemberPurchaseDetailsReport();
	private BadScanReport badScan = new BadScanReport();
	private TransactionCannedReport transactionCanned = new TransactionCannedReport();
	private CurrenyConverter converter = new CurrenyConverter();
	private EmployeeCompDetailsReport employeeCompDetails = new EmployeeCompDetailsReport();

	private Map<String, String> rstNavigationMenuData;
	private Map<String, String> rstConsumerSearchData;
	private Map<String, String> rstProductSummaryData;
	private Map<String, String> rstLocationSummaryData;
	private Map<String, String> rstConsumerSummaryData;
	private Map<String, String> rstReportListData;

	@Test(description = "119928- This test validates account adjustment report")
	public void accountAdjustmentReport() throws SQLException {
		try {
			Map<String, String> dbData = new HashMap<>();

			final String CASE_NUM = "119928";
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstConsumerSearchData = dataBase.getConsumerSearchData(Queries.CONSUMER_SEARCH, CASE_NUM);
			rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);
			rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);
			rstConsumerSummaryData = dataBase.getConsumerSummaryData(Queries.CONSUMER_SUMMARY, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			List<String> menuItems = Arrays
					.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
			navigationBar.navigateToMenuItem(menuItems.get(0));

			// Enter fields in Consumer Search Page
			consumerSearch.enterSearchFields(rstConsumerSearchData.get(CNConsumerSearch.SEARCH_BY),
					rstConsumerSearchData.get(CNConsumerSearch.CONSUMER_ID),
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE),
					rstConsumerSearchData.get(CNConsumerSearch.STATUS));

			// Split database data
			List<String> requiredData = Arrays
					.asList(rstProductSummaryData.get(CNProductSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));

			consumerSearch.clickCell(requiredData.get(5));

			List<String> columnName = Arrays
					.asList(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME).split(Constants.DELIMITER_TILD));
			List<String> tblColumnHeader = Arrays.asList(columnName.get(1).split(Constants.DELIMITER_HASH));

			// reading Balance and add to the array list
			double initialbalance = consumerSummary.getBalance();
			dbData.put(tblColumnHeader.get(7), converter.convertTOCurrency(initialbalance));

			foundation.click(ConsumerSummary.BTN_ADJUST);

			// converting string to double and adding the adjusted value
			double adustedBalance = Double.parseDouble(rstConsumerSummaryData.get(CNConsumerSummary.ADJUST_BALANCE));
			double updatedbalance = initialbalance
					+ Double.parseDouble(rstConsumerSummaryData.get(CNConsumerSummary.ADJUST_BALANCE));
			textBox.enterText(ConsumerSummary.TXT_ADJUST_BALANCE, Double.toString(updatedbalance));

			dropdown.selectItem(ConsumerSummary.DPD_REASON, rstConsumerSummaryData.get(CNConsumerSummary.REASON),
					Constants.TEXT);

			foundation.click(ConsumerSummary.BTN_SAVE);

			// converting time zone to specific time zone
			String updatedTime = String
					.valueOf(dateAndTime.getDateAndTime(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION),
							rstLocationSummaryData.get(CNLocationSummary.TIME_ZONE)));

			// Navigate to Reports
			navigationBar.navigateToMenuItem(menuItems.get(1));

			// Selecting the Date range and Location for running report
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));

			reportList.selectLocation(
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));

			foundation.click(ReportList.BTN_RUN_REPORT);

			// Validate Account Adjustment Report Title
			String reportName = foundation.getText(AccountAdjustment.LBL_REPORT_NAME);
			Assert.assertTrue(reportName.contains(rstReportListData.get(CNReportList.REPORT_NAME)));

			// Add db data to Array list
			dbData.put(tblColumnHeader.get(1),
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));
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

			textBox.enterText(AccountAdjustment.TXT_SEARCH, String.valueOf(updatedTime));

			// Storing UI data in iuData Map
			Map<String, String> uiData = accountAdjustment.getTblRecordsUI();

			// Validate account adjustment adjusted report data
			assertEquals(uiData, dbData);

		} catch (Exception exc) {
			Assert.fail();
		}
	}

	@Test(description = "This test validates Product Tax Report Data Calculation")
	public void ProductTaxReportData() {
		try {

			final String CASE_NUM = "120622";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			// process sales API to generate data
			productTax.processAPI(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));
			foundation.click(ReportList.BTN_RUN_REPORT);
			productTax.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));
			productTax.getTblRecordsUI();
			productTax.getIntialData().putAll(productTax.getReportsData());
			productTax.getRequiredRecord((String) productTax.getJsonData().get(Reports.TRANS_DATE_TIME),
					productTax.getScancodeData());
			// apply calculation and update data
			productTax.updateData(productTax.getTableHeaders().get(0),
					(String) productTax.getJsonData().get(Reports.TRANS_DATE_TIME));
			productTax.updateData(productTax.getTableHeaders().get(1),
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));
			productTax.updateData(productTax.getTableHeaders().get(2),
					propertyFile.readPropertyFile(Configuration.DEVICE_ID, FilePath.PROPERTY_CONFIG_FILE));
			productTax.updateData(productTax.getTableHeaders().get(3), productTax.getProductNameData());
			productTax.updateData(productTax.getTableHeaders().get(4), productTax.getScancodeData());
			productTax.updateData(productTax.getTableHeaders().get(5), productTax.getCategory1Data());
			productTax.updateData(productTax.getTableHeaders().get(6), productTax.getCategory2Data());
			productTax.updateData(productTax.getTableHeaders().get(7), productTax.getCategory3Data());
			productTax.updatePrice();
			productTax.updateData(productTax.getTableHeaders().get(9), productTax.getTaxCatData());
			productTax.updateData(productTax.getTableHeaders().get(10), productTax.getTaxData());
			productTax.updateData(productTax.getTableHeaders().get(11), productTax.getRequiredJsonData().get(0));
			// verify report headers
			productTax.verifyReportHeaders(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME));

			// verify report data
			productTax.verifyReportData();
		} catch (Exception exc) {
			Assert.fail();
		}

	}

	@Test(description = "This test validates Product Pricing Report Data Calculation")
	public void ProductPricingReportData() {
		try {

			final String CASE_NUM = "135696";
			rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Navigate to Reports
			List<String> menu = Arrays
					.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
			navigationBar.navigateToMenuItem(menu.get(0));

			// Select the Report Date range and Location
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectLocation(
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));

			// run and read report
			foundation.click(ReportList.BTN_RUN_REPORT);
			productPricing.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));
			productPricing.getTblRecordsUI();
			productPricing.getIntialData().putAll(productPricing.getReportsData());

			// get Location Data
			navigationBar.navigateToMenuItem(menu.get(1));
			locationList.selectLocaionName(
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));
			locationSummary.selectTab(rstLocationSummaryData.get(CNLocationSummary.TAB_NAME));
			locationSummary.manageColumn(rstLocationSummaryData.get(CNLocationSummary.COLUMN_NAME));

			// apply calculation and update data
			productPricing.updateData(rstProductSummaryData.get(CNProductSummary.SCAN_CODE),
					rstProductSummaryData.get(CNProductSummary.REQUIRED_DATA));

			// verify report headers
			productPricing.verifyReportHeaders(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME));

			// verify report data
			productPricing.verifyReportData();

		} catch (Exception exc) {
			Assert.fail();
		}

	}

	@Test(description = "This test validates Transaction Canned Report Data Calculation")
	public void TransactionCannedReportData() {
		try {

			final String CASE_NUM = "130612";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);
			rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			// process sales API to generate data
			transactionCanned.processAPI(rstLocationSummaryData.get(CNLocationSummary.REQUIRED_DATA));

			// Select Organization
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Navigate to Reports
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));

			reportList.selectLocation(
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));

			// run and read report
			foundation.click(ReportList.BTN_RUN_REPORT);
			transactionCanned.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));
			transactionCanned.getTblRecordsUI();
			transactionCanned.getIntialData().putAll(transactionCanned.getReportsData());
			transactionCanned.getIntialTotal().putAll(transactionCanned.getUpdatedTotal());

			// read updated data
			transactionCanned.processAPI(rstLocationSummaryData.get(CNLocationSummary.REQUIRED_DATA));
			foundation.click(ReportList.BTN_RUN_REPORT);
			transactionCanned.getTblRecordsUI();

			// apply calculation and update data
			transactionCanned.updateColumnData(transactionCanned.getTableHeaders().get(0),
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));
			transactionCanned.updateTransactions(transactionCanned.getTableHeaders().get(1),
					rstProductSummaryData.get(CNProductSummary.REQUIRED_DATA));
			transactionCanned.updateTransactions(transactionCanned.getTableHeaders().get(2),
					rstLocationSummaryData.get(CNLocationSummary.ACTUAL_DATA));
			transactionCanned.updateSales(transactionCanned.getTableHeaders().get(5),
					transactionCanned.getTableHeaders().get(1), transactionCanned.getTableHeaders().get(3));
			transactionCanned.updateUnitsPerTransactions(transactionCanned.getTableHeaders().get(2),
					transactionCanned.getTableHeaders().get(1), transactionCanned.getTableHeaders().get(4));
			transactionCanned.updateColumnData(transactionCanned.getTableHeaders().get(5),
					transactionCanned.getRequiredJsonData().get(0));
			transactionCanned.updateUnitsPerTransactions(transactionCanned.getTableHeaders().get(5),
					transactionCanned.getTableHeaders().get(4), transactionCanned.getTableHeaders().get(6));
			transactionCanned.updateTransactions(transactionCanned.getTableHeaders().get(7),
					rstProductSummaryData.get(CNProductSummary.ACTUAL_DATA));
			transactionCanned.updateSales(transactionCanned.getTableHeaders().get(11),
					transactionCanned.getTableHeaders().get(7), transactionCanned.getTableHeaders().get(8));
			transactionCanned.updatePercent(transactionCanned.getTableHeaders().get(8),
					transactionCanned.getTableHeaders().get(3), transactionCanned.getTableHeaders().get(9));
			transactionCanned.updateColumnData(transactionCanned.getTableHeaders().get(10),
					rstProductSummaryData.get(CNProductSummary.REQUIRED_DATA));
			transactionCanned.updateColumnData(transactionCanned.getTableHeaders().get(11),
					transactionCanned.getRequiredJsonData().get(0));
			transactionCanned.updateUnitsPerTransactions(transactionCanned.getTableHeaders().get(11),
					transactionCanned.getTableHeaders().get(10), transactionCanned.getTableHeaders().get(12));
			transactionCanned.updateTransactions(transactionCanned.getTableHeaders().get(13),
					rstProductSummaryData.get(CNProductSummary.ACTUAL_DATA));
			transactionCanned.updateSales(transactionCanned.getTableHeaders().get(17),
					transactionCanned.getTableHeaders().get(13), transactionCanned.getTableHeaders().get(14));
			transactionCanned.updatePercent(transactionCanned.getTableHeaders().get(14),
					transactionCanned.getTableHeaders().get(3), transactionCanned.getTableHeaders().get(15));
			transactionCanned.updateColumnData(transactionCanned.getTableHeaders().get(16),
					rstProductSummaryData.get(CNProductSummary.REQUIRED_DATA));
			transactionCanned.updateColumnData(transactionCanned.getTableHeaders().get(17),
					transactionCanned.getRequiredJsonData().get(0));
			transactionCanned.updateUnitsPerTransactions(transactionCanned.getTableHeaders().get(17),
					transactionCanned.getTableHeaders().get(16), transactionCanned.getTableHeaders().get(18));

			// Calculate total
			transactionCanned.updateTotal();
			transactionCanned.updateDecimalTotal();

			// verify report headers
			transactionCanned.verifyReportHeaders(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME));

			// verify report data
			transactionCanned.verifyReportData();

		} catch (Exception exc) {
			Assert.fail();
		}

	}

	@Test(description = "This test validates Member Purchase Details Report Data Calculation")
	public void MemberPurchaseDetailsReport() {
		try {

			final String CASE_NUM = "120269";

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			// Login
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Process Sales API data
			memberPurchaseDetails.processAPI(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));

			// Navigate to Reports
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Verify Report Name
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));
			foundation.click(ReportList.BTN_RUN_REPORT);
			memberPurchaseDetails.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));

			// run and read report
			textBox.enterText(MemberPurchaseDetailsReport.CSS_TXT_SEARCH,
					((String) memberPurchaseDetails.getJsonData().get(Reports.TRANS_DATE_TIME)));
			memberPurchaseDetails.getMemberPurchaseDetails();
			memberPurchaseDetails.getInitialData().putAll(memberPurchaseDetails.getReportsData());
			memberPurchaseDetails.getRequiredRecord(memberPurchaseDetails.getScancodeData());

			// apply calculation and update data
			List<String> requiredData = Arrays
					.asList(rstProductSummaryData.get(CNProductSummary.REQUIRED_DATA).split(Constants.DELIMITER_HASH));
			memberPurchaseDetails.updateData(memberPurchaseDetails.getTableHeaders().get(0), requiredData.get(0));
			memberPurchaseDetails.updateData(memberPurchaseDetails.getTableHeaders().get(1), requiredData.get(1));
			memberPurchaseDetails.updateData(memberPurchaseDetails.getTableHeaders().get(2), requiredData.get(2));
			memberPurchaseDetails.updateListData(memberPurchaseDetails.getTableHeaders().get(3),
					memberPurchaseDetails.getProductNameData());
			memberPurchaseDetails.updateListData(memberPurchaseDetails.getTableHeaders().get(5),
					memberPurchaseDetails.getPriceData());
			memberPurchaseDetails.updateListData(memberPurchaseDetails.getTableHeaders().get(6),
					memberPurchaseDetails.getTaxData());
			memberPurchaseDetails.updateTotal();

			// Verify Report Headers
			memberPurchaseDetails.verifyReportHeaders(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME));

			// Verify Report Data
			memberPurchaseDetails.verifyReportData();
		} catch (Exception exc) {
			Assert.fail();
		}
	}

	@Test(description = "This test validates Bad Scan Report Data Calculation")
	public void BadScanReportData() {
		try {

			final String CASE_NUM = "120821";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			// process sales API to generate data
			badScan.processAPI(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Navigate to Reports
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));

			reportList.selectLocation(
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));

			// run and read report
			foundation.click(ReportList.BTN_RUN_REPORT);
			badScan.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));
			badScan.getTblRecordsUI();
			badScan.getIntialData().putAll(badScan.getReportsData());
			badScan.getRequiredRecord((String) badScan.getData().get(Reports.TRANS_DATE_TIME));

			// apply calculation and update data
			badScan.updateData(badScan.getTableHeaders().get(0), propertyFile
					.readPropertyFile(Configuration.DEVICE_ID, FilePath.PROPERTY_CONFIG_FILE).toUpperCase());
			badScan.updateData(badScan.getTableHeaders().get(1),
					(String) badScan.getData().get(Reports.TRANS_DATE_TIME));
			badScan.updateData(badScan.getTableHeaders().get(2), badScan.getRequiredJsonData().get(1));
			badScan.updateData(badScan.getTableHeaders().get(3), badScan.getRequiredJsonData().get(0));

			// verify report headers
			badScan.verifyReportHeaders(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME));

			// verify report data
			badScan.verifyReportData();

		} catch (Exception exc) {
			Assert.fail();
		}

	}

	@Test(description = "This test validates Employee Comp Details Report Data Calculation")
	public void EmployeeCompDetailsReportData() {
		try {

			final String CASE_NUM = "141636";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			// process sales API to generate data
			employeeCompDetails.processAPI(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));
			reportList.selectLocation(
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(ReportList.BTN_RUN_REPORT);
			employeeCompDetails.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));
			employeeCompDetails.getTblRecordsUI();
			employeeCompDetails.getIntialData().putAll(employeeCompDetails.getReportsData());
			employeeCompDetails.getRequiredRecord(
					(String) employeeCompDetails.getJsonData().get(Reports.TRANS_DATE_TIME),
					employeeCompDetails.getProductNameData());

			// apply calculation and update data
			List<String> requiredData = Arrays
					.asList(rstProductSummaryData.get(CNProductSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			employeeCompDetails.updateData(employeeCompDetails.getTableHeaders().get(0), requiredData.get(0));
			employeeCompDetails.updateData(employeeCompDetails.getTableHeaders().get(1), requiredData.get(1));
			employeeCompDetails.updateData(employeeCompDetails.getTableHeaders().get(2), requiredData.get(2));
			employeeCompDetails.updateData(employeeCompDetails.getTableHeaders().get(3),
					employeeCompDetails.getProductNameData());
			employeeCompDetails.updateData(employeeCompDetails.getTableHeaders().get(4),
					employeeCompDetails.getPriceData());
			employeeCompDetails.updateData(employeeCompDetails.getTableHeaders().get(5),
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));
			employeeCompDetails.updateData(employeeCompDetails.getTableHeaders().get(6),
					(String) employeeCompDetails.getJsonData().get(Reports.TRANS_ID));
			employeeCompDetails.updateData(employeeCompDetails.getTableHeaders().get(7),
					(String) employeeCompDetails.getJsonData().get(Reports.TRANS_DATE_TIME));

			// verify report headers
			employeeCompDetails.verifyReportHeaders(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME));

			// verify report data
			employeeCompDetails.verifyReportData();
		} catch (Exception exc) {
			Assert.fail();
		}

	}

}
