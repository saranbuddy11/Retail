package at.smartshop.tests;

import static org.testng.Assert.assertEquals;

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
import at.smartshop.pages.ICEReport;
import at.smartshop.pages.DeviceByCategoryReport;
import at.smartshop.pages.EmployeeCompDetailsReport;
import at.smartshop.pages.HealthAheadReport;
import at.smartshop.pages.ItemStockoutReport;
import at.smartshop.pages.LocationList;
import at.smartshop.pages.LocationSummary;
import at.smartshop.pages.MemberPurchaseDetailsReport;
import at.smartshop.pages.MemberPurchaseSummaryReport;
import at.smartshop.pages.NavigationBar;
import at.smartshop.pages.ProductPricingReport;
import at.smartshop.pages.ProductSalesByCategoryReport;
import at.smartshop.pages.ProductTaxReport;
import at.smartshop.pages.ReportList;
import at.smartshop.pages.TipDetailsReport;
import at.smartshop.pages.TipSummaryReport;
import at.smartshop.pages.TransactionCannedReport;
import at.smartshop.pages.UnfinishedCloseReport;
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
	private ICEReport iceReport = new ICEReport();
	private DeviceByCategoryReport deviceByCategory = new DeviceByCategoryReport();
	private EmployeeCompDetailsReport employeeCompDetails = new EmployeeCompDetailsReport();
	private TipSummaryReport tipSummary = new TipSummaryReport();
	private ItemStockoutReport itemStockout = new ItemStockoutReport();
	private MemberPurchaseSummaryReport memberPurchaseSummary = new MemberPurchaseSummaryReport();
	private HealthAheadReport healthAhead = new HealthAheadReport();
	private TipDetailsReport tipDetails = new TipDetailsReport();
	private ProductSalesByCategoryReport productSalesCategory = new ProductSalesByCategoryReport();
	private UnfinishedCloseReport unfinishedClose = new UnfinishedCloseReport();

	private Map<String, String> rstNavigationMenuData;
	private Map<String, String> rstConsumerSearchData;
	private Map<String, String> rstProductSummaryData;
	private Map<String, String> rstLocationSummaryData;
	private Map<String, String> rstConsumerSummaryData;
	private Map<String, String> rstReportListData;

	@Test(description = "119928- This test validates account adjustment report")
	public void accountAdjustmentReport()  {
		try {
			Map<String, String> dbData = new HashMap<>();

			final String CASE_NUM = "119928";
			browser.navigateURL(	propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
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
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			List<String> menuItems = Arrays.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
			navigationBar.navigateToMenuItem(menuItems.get(0));

			// Enter fields in Consumer Search Page
			consumerSearch.enterSearchFields(rstConsumerSearchData.get(CNConsumerSearch.SEARCH_BY),
																rstConsumerSearchData.get(CNConsumerSearch.CONSUMER_ID),
																propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE),
																rstConsumerSearchData.get(CNConsumerSearch.STATUS));

			// Split database data
			List<String> requiredData = Arrays.asList(rstProductSummaryData.get(CNProductSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			foundation.click(consumerSearch.objCell(requiredData.get(5)));
			List<String> columnName = Arrays.asList(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME).split(Constants.DELIMITER_TILD));
			List<String> tblColumnHeader = Arrays.asList(columnName.get(1).split(Constants.DELIMITER_HASH));

			// reading Balance and add to the array list
			double initialbalance = consumerSummary.getBalance();
			dbData.put(tblColumnHeader.get(7), converter.convertTOCurrency(initialbalance));
			foundation.click(ConsumerSummary.BTN_ADJUST);

			// converting string to double and adding the adjusted value
			double adustedBalance = Double.parseDouble(rstConsumerSummaryData.get(CNConsumerSummary.ADJUST_BALANCE));
			double updatedbalance = initialbalance+ Double.parseDouble(rstConsumerSummaryData.get(CNConsumerSummary.ADJUST_BALANCE));
			textBox.enterText(ConsumerSummary.TXT_ADJUST_BALANCE, Double.toString(updatedbalance));
			dropdown.selectItem(ConsumerSummary.DPD_REASON, rstConsumerSummaryData.get(CNConsumerSummary.REASON),Constants.TEXT);
			foundation.click(ConsumerSummary.BTN_REASON_SAVE);

			// converting time zone to specific time zone
			String updatedTime = String.valueOf(dateAndTime.getDateAndTime(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION),
											rstLocationSummaryData.get(CNLocationSummary.TIME_ZONE)));

			// Navigate to Reports
			navigationBar.navigateToMenuItem(menuItems.get(1));

			// Selecting the Date range and Location for running report
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));
			reportList.selectLocation(propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(ReportList.BTN_RUN_REPORT);

			// Validate Account Adjustment Report Title
			String reportName = foundation.getText(AccountAdjustment.LBL_REPORT_NAME);
			Assert.assertTrue(reportName.contains(rstReportListData.get(CNReportList.REPORT_NAME)));

			// Add db data to Array list
			dbData.put(tblColumnHeader.get(1),propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));
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

	@Test(description = "120622-This test validates Product Tax Report Data Calculation")
	public void productTaxReportData() {
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

			reportList.selectLocation(
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));

			// run and read report
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

	@Test(description = "135696-This test validates Product Pricing Report Data Calculation")
	public void productPricingReportData() {
		try {

			final String CASE_NUM = "135696";
			rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);

			// Select Menu and Menu Item
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Navigate to Reports
			List<String> menu = Arrays.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
			navigationBar.navigateToMenuItem(menu.get(0));

			// Select the Report Date range and Location
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectLocation(propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));

			// run and read report
			foundation.click(ReportList.BTN_RUN_REPORT);
			foundation.waitforElement(ProductPricingReport.LBL_REPORT_NAME, Constants.EXTRA_LONG_TIME);
			productPricing.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));
			productPricing.getTblRecordsUI();
			productPricing.getIntialData().putAll(productPricing.getReportsData());

			// get Location Data
			navigationBar.navigateToMenuItem(menu.get(1));
			locationList.selectLocationName(propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));
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

	@Test(description = "130612-This test validates Transaction Canned Report Data Calculation")
	public void transactionCannedReportData() {
		try {

			final String CASE_NUM = "130612";

			browser.navigateURL(	propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
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
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Navigate to Reports
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));
			reportList.selectLocation(propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));

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
			transactionCanned.updateColumnData(transactionCanned.getTableHeaders().get(4),
					rstProductSummaryData.get(CNProductSummary.REQUIRED_DATA));
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

	@Test(description = "120269-This test validates Member Purchase Details Report Data Calculation")
	public void memberPurchaseDetailsReport() {
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

	@Test(description = "120821 - This test validates Bad Scan Report Data Calculation")
	public void badScanReportData() {
		try {

			final String CASE_NUM = "120821";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);

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

	@Test(description = "141644 - This test validates Device By Category Report Data Calculation")
	public void deviceByCategoryReportData() {
		try {

			final String CASE_NUM = "141644";

			browser.navigateURL(	propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			// process sales API to generate data
			deviceByCategory.processAPI();
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));
			reportList.selectLocation(propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));

			// run and read report
			foundation.click(ReportList.BTN_RUN_REPORT);
			deviceByCategory.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));
			deviceByCategory.getTblRecordsUI();
			deviceByCategory.getIntialData().putAll(deviceByCategory.getReportsData());
			deviceByCategory.processAPI();
			foundation.click(ReportList.BTN_RUN_REPORT);
			deviceByCategory.getTblRecordsUI();

			// apply calculation and update data
			List<String> requiredData = Arrays.asList(rstProductSummaryData.get(CNProductSummary.REQUIRED_DATA).split(Constants.DELIMITER_HASH));
			deviceByCategory.updateData(deviceByCategory.getTableHeaders().get(0),propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));
			deviceByCategory.updateData(deviceByCategory.getTableHeaders().get(1), propertyFile.readPropertyFile(Configuration.DEVICE_ID, FilePath.PROPERTY_CONFIG_FILE).toUpperCase());
			foundation.threadWait(Constants.TWO_SECOND);
			deviceByCategory.updateData(deviceByCategory.getTableHeaders().get(2), requiredData.get(0));
			deviceByCategory.updateData(deviceByCategory.getTableHeaders().get(3),deviceByCategory.getRequiredJsonData().get(1));
			deviceByCategory.updateData(deviceByCategory.getTableHeaders().get(4),deviceByCategory.getRequiredJsonData().get(2));
			deviceByCategory.updateCount(deviceByCategory.getTableHeaders().get(5), requiredData.get(1));
			deviceByCategory.updateCount(deviceByCategory.getTableHeaders().get(6), requiredData.get(2));
			deviceByCategory.updateTotal(deviceByCategory.getRequiredJsonData().get(0),
			deviceByCategory.getTableHeaders().get(5), deviceByCategory.getTableHeaders().get(7));
			
			// verify report headers
			deviceByCategory.verifyReportHeaders(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME));

			// verify report data
			deviceByCategory.verifyReportData();
			
		} catch (Exception exc) {
			Assert.fail();
		}
	}

	@Test(description = "141-636-This test validates Employee Comp Details Report Data Calculation")
	public void employeeCompDetailsReportData() {
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

	@Test(description = "142715-This test validates Product Tax Report Data Calculation")
	public void memberPurchaseSummaryReportData() {
		try {
			final String CASE_NUM = "142715";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			// process sales API to generate data
			memberPurchaseSummary.processAPI();

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));
			foundation.click(ReportList.BTN_RUN_REPORT);
			memberPurchaseSummary.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));
			memberPurchaseSummary.getTblRecordsUI();
			memberPurchaseSummary.getIntialData().putAll(memberPurchaseSummary.getReportsData());

			// Process GMA and sales API
			memberPurchaseSummary.processAPI();
			foundation.click(ReportList.BTN_RUN_REPORT);
			memberPurchaseSummary.getTblRecordsUI();

			// apply calculation and update data
			memberPurchaseSummary.updateData(rstProductSummaryData.get(CNProductSummary.REQUIRED_DATA));
			memberPurchaseSummary.updateAmount(memberPurchaseSummary.getTableHeaders().get(3),
					memberPurchaseSummary.getRequiredJsonData().get(0));
			memberPurchaseSummary.updateAmount(memberPurchaseSummary.getTableHeaders().get(4),
					memberPurchaseSummary.getRequiredJsonData().get(1));
			memberPurchaseSummary.updateTotal();

			// verify report headers
			memberPurchaseSummary.verifyReportHeaders(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME));

			// verify report data
			memberPurchaseSummary.verifyReportData();
		} catch (Exception exc) {
			Assert.fail();
		}

	}

	@Test(description = "142642-This test validates ICE Report Data Calculation")
	public void iceReportData() {
		try {

			final String CASE_NUM = "142642";
			rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			browser.navigateURL(	propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
							propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);

			// Select Menu and Menu Item
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Navigate to Reports
			List<String> menu = Arrays.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
			List<String> columnName = Arrays.asList(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME).split(Constants.DELIMITER_TILD));
			List<String> tabName = Arrays	.asList(rstLocationSummaryData.get(CNLocationSummary.TAB_NAME).split(Constants.DELIMITER_TILD));
			List<String> actualData = Arrays.asList(rstProductSummaryData.get(CNProductSummary.ACTUAL_DATA).split(Constants.DELIMITER_TILD));
			List<String> requiredData = Arrays.asList(rstProductSummaryData.get(CNProductSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));

			navigationBar.navigateToMenuItem(menu.get(0));

			// Select the Report Date range and Location
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));
			reportList.selectLocation(propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));

			// run and read report
			foundation.click(ReportList.BTN_RUN_REPORT);
			iceReport.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));
			iceReport.getTblRecordsUI();
			iceReport.getIntialData().putAll(iceReport.getReportsData());

			// get Location Data
			navigationBar.navigateToMenuItem(menu.get(1));
			locationList.selectLocationName(propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			iceReport.getADMData().add(foundation.getTextAttribute(LocationSummary.TXT_CUSTOMER));
			iceReport.getADMData().add(foundation.getTextAttribute(LocationSummary.TXT_LOCATION_NUMBER));
			iceReport.getADMData().add(dropdown.getSelectedItem(LocationSummary.DPD_ROUTE));

			// update Data
			locationSummary.selectTab(tabName.get(0));
			//locationSummary.manageColumn(columnName.get(0));
			iceReport.updateData(rstProductSummaryData.get(CNProductSummary.SCAN_CODE), columnName.get(1));
			locationSummary.selectTab(tabName.get(1));
			locationSummary.updateInventory(rstProductSummaryData.get(CNProductSummary.SCAN_CODE), requiredData.get(0),actualData.get(0));
			iceReport.updateFills(rstProductSummaryData.get(CNProductSummary.SCAN_CODE), columnName.get(1),requiredData.get(0));
			locationSummary.updateInventory(rstProductSummaryData.get(CNProductSummary.SCAN_CODE), requiredData.get(1),actualData.get(1));
			foundation.refreshPage();
			locationSummary.selectTab(tabName.get(0));
			iceReport.updateWaste(rstProductSummaryData.get(CNProductSummary.SCAN_CODE), columnName.get(1),requiredData.get(1));
			iceReport.updateSold(rstProductSummaryData.get(CNProductSummary.SCAN_CODE));
			iceReport.updateClosingLevel(rstProductSummaryData.get(CNProductSummary.SCAN_CODE), columnName.get(1));

			// Select the Report Date range and Location
			iceReport.processAPI();
			navigationBar.navigateToMenuItem(menu.get(0));
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));
			reportList.selectLocation(propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));

			// run and read report
			foundation.click(ReportList.BTN_RUN_REPORT);
			iceReport.getTblRecordsUI();

			// verify report headers
			iceReport.verifyReportHeaders(columnName.get(2));

			// verify report data
			iceReport.verifyReportData(rstProductSummaryData.get(CNProductSummary.SCAN_CODE));

		} catch (Exception exc) {
			Assert.fail();
		}

	}

	@Test(description = "This test validates Tip Summary Report Data Calculation")

	public void tipSummaryReportData() {
		try {

			final String CASE_NUM = "142802";
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			// process sales API to generate data
			tipSummary.processAPI(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));

			reportList.selectLocation(
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));

			// run and read report
			foundation.click(ReportList.BTN_RUN_REPORT);
			tipSummary.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));
			tipSummary.getTblRecordsUI();
			tipSummary.getIntialData().putAll(tipSummary.getReportsData());
			tipSummary.getRequiredRecord(rstProductSummaryData.get(CNProductSummary.REQUIRED_DATA));

			// process API
			tipSummary.processAPI(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));
			foundation.click(ReportList.BTN_RUN_REPORT);
			tipSummary.getTblRecordsUI();

			// apply calculation and update data
			tipSummary.updateData(rstProductSummaryData.get(CNProductSummary.ACTUAL_DATA));
			tipSummary.updateAmount(tipSummary.getTableHeaders().get(3),
					rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));
			tipSummary.updateAmount(tipSummary.getTableHeaders().get(6), tipSummary.getRequiredJsonData().get(0));

			// verify report headers
			tipSummary.verifyReportHeaders(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME));

			// verify report data
			tipSummary.verifyReportData();
		} catch (Exception exc) {
			Assert.fail();
		}

	}

	@Test(description = "This test validates Item Stockout Report Data Calculation")
	public void itemStockoutReportData() {
		try {

			final String CASE_NUM = "142756";
			
			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);
			rstConsumerSummaryData = dataBase.getConsumerSummaryData(Queries.CONSUMER_SUMMARY, CASE_NUM);
			rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);
			
			browser.navigateURL(	propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));			
			
			List<String> menu = Arrays.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
			List<String> reportName = Arrays.asList(rstReportListData.get(CNReportList.REPORT_NAME).split(Constants.DELIMITER_TILD));
			List<String> columnName = Arrays.asList(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME).split(Constants.DELIMITER_TILD));
			List<String> actualData = Arrays.asList(rstProductSummaryData.get(CNProductSummary.ACTUAL_DATA).split(Constants.DELIMITER_TILD));
			List<String> requiredData = Arrays.asList(rstProductSummaryData.get(CNProductSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> reason = Arrays.asList(rstConsumerSummaryData.get(CNConsumerSummary.REASON).split(Constants.DELIMITER_TILD));
			
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// navigate to location
			navigationBar.navigateToMenuItem(menu.get(1));
			locationList.selectLocationName(propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));
			foundation.waitforElement(LocationSummary.LNK_INVENTORY, Constants.SHORT_TIME);
			locationSummary.selectTab(rstLocationSummaryData.get(CNLocationSummary.TAB_NAME));
			locationSummary.updateInventory(rstProductSummaryData.get(CNProductSummary.SCAN_CODE), actualData.get(0),reason.get(0));
			locationSummary.updateInventory(rstProductSummaryData.get(CNProductSummary.SCAN_CODE), actualData.get(1),reason.get(1));
			String stockout = itemStockout.getStockoutTime(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION),
																					rstLocationSummaryData.get(CNLocationSummary.TIME_ZONE));
			// navigate to Reports
			navigationBar.navigateToMenuItem(menu.get(0));

			// Select the Report Date range and Location
			reportList.selectReport(reportName.get(0));
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));
			reportList.selectLocation(propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));
			foundation.adjustBrowerSize(actualData.get(2));
			foundation.objectClick(ReportList.BTN_RUN_REPORT);
			foundation.adjustBrowerSize(actualData.get(3));

			itemStockout.verifyReportName(reportName.get(1));
			itemStockout.getTblRecordsUI();
			itemStockout.getRequiredRecord(rstProductSummaryData.get(CNProductSummary.SCAN_CODE));
			itemStockout.getIntialData().putAll(itemStockout.getReportsData());

			// apply calculation and update data
			itemStockout.updateData(requiredData.get(0), actualData.get(1), stockout);

			// verify report headers
			itemStockout.verifyReportHeaders(itemStockout.getTableHeaders(), columnName.get(0));

			// verify report data
			itemStockout.verifyReportData();

			// navigate to Details
			itemStockout.navigateToProductsEvents(columnName.get(1),
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE),
					rstProductSummaryData.get(CNProductSummary.SCAN_CODE));
			itemStockout.getItemStockoutDetails();
			itemStockout.getIntialDetailsData().putAll(itemStockout.getReportsDetailsData());
			itemStockout.updateDetailsData(stockout, requiredData.get(1), reason.get(1));

			// verify report details headers
			itemStockout.verifyReportHeaders(itemStockout.getItemStockoutDetailsHeaders(), columnName.get(2));

			// verify report details data
			itemStockout.verifyReportDetailsData(stockout, reason.get(1));
		} catch (Exception exc) {
			Assert.fail();
		}
	}

	@Test(description = "This test validates Tip Details Report Data Calculation")
	public void tipDetailsReportData() {
		try {

			final String CASE_NUM = "142814";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			List<String> requiredOption = Arrays.asList(
					rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION).split(Constants.DELIMITER_TILD));

			// process sales API to generate data
			tipDetails.processAPI(requiredOption.get(0), requiredOption.get(1));

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));

			reportList.selectLocation(
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));

			// run and read report
			foundation.click(ReportList.BTN_RUN_REPORT);

			tipDetails.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));
			tipDetails.getTblRecordsUI();
			tipDetails.getIntialData().putAll(tipDetails.getReportsData());
			tipDetails.getRequiredRecord(rstProductSummaryData.get(CNProductSummary.REQUIRED_DATA),
					(String) tipDetails.getJsonData().get(Reports.TRANS_ID));

//			// process API
//			tipDetails.processAPI(requiredOption.get(0), requiredOption.get(1));
//			foundation.click(ReportList.BTN_RUN_REPORT);
//			tipDetails.getTblRecordsUI();

			// apply calculation and update data
			tipDetails.updateData(rstProductSummaryData.get(CNProductSummary.ACTUAL_DATA), requiredOption.get(1));
//			tipDetails.updateAmount(tipDetails.getTableHeaders().get(3),
//					rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));
//			tipDetails.updateAmount(tipDetails.getTableHeaders().get(6), tipDetails.getRequiredJsonData().get(0));

			// verify report headers
			tipDetails.verifyReportHeaders(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME));

			// verify report data
			tipDetails.verifyReportData();
		} catch (Exception exc) {
			Assert.fail();
		}
	}

	@Test(description = "This test validates Health Ahead Report Data Calculation")
	public void healthAheadReportData() {
		try {

			final String CASE_NUM = "142863";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			// process sales API to generate data
			healthAhead.processAPI(rstProductSummaryData.get(CNProductSummary.ACTUAL_DATA),
					rstProductSummaryData.get(CNProductSummary.REQUIRED_DATA));

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));

			reportList.selectLocation(
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));

			// run and read report
			foundation.click(ReportList.BTN_RUN_REPORT);

			healthAhead.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));
			healthAhead.getTblRecordsUI();
			healthAhead.getIntialData().putAll(healthAhead.getReportsData());
			healthAhead.getRequiredRecord(propertyFile
					.readPropertyFile(Configuration.DEVICE_ID, FilePath.PROPERTY_CONFIG_FILE).toUpperCase());
			healthAhead.updateData(healthAhead.getTableHeaders().get(2), healthAhead.getRequiredJsonData().get(0));
			healthAhead.updateData(healthAhead.getTableHeaders().get(3), healthAhead.getRequiredJsonData().get(1));
			healthAhead.updateHealthAheadNet();
			// verify report headers
			healthAhead.verifyReportHeaders(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME));

			// verify report data
			healthAhead.verifyReportData();
		} catch (Exception exc) {
			Assert.fail();
		}

	}

	@Test(description = "This test validates Product Sales By Category Report Data Calculation")
	public void productSalesByCategoryReportData() {
		try {

			final String CASE_NUM = "142906";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			// process sales API to generate data
			productSalesCategory.processAPI(rstProductSummaryData.get(CNProductSummary.SCAN_CODE),
					rstProductSummaryData.get(CNProductSummary.CATEGORY2));

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range ,Location and Group By
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));
			reportList.selectLocation(
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));
			foundation.objectFocus(ReportList.DPD_GROUP_BY);
			dropdown.selectItem(ReportList.DPD_GROUP_BY, rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION),
					Constants.TEXT);

			// run and read report
			foundation.click(ReportList.BTN_RUN_REPORT);
			productSalesCategory.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));
			productSalesCategory.getTblRecordsUI();
			productSalesCategory.getIntialData().putAll(productSalesCategory.getReportsData());

			// Process API and read updated data
			productSalesCategory.processAPI(rstProductSummaryData.get(CNProductSummary.SCAN_CODE),
					rstProductSummaryData.get(CNProductSummary.CATEGORY2));
			foundation.click(ReportList.BTN_RUN_REPORT);
			productSalesCategory.getTblRecordsUI();
			productSalesCategory.getRequiredRecord(rstProductSummaryData.get(CNProductSummary.CATEGORY2));

			// apply calculation and update data
			productSalesCategory.updateSalesAmount();
			productSalesCategory.updateTax();
			productSalesCategory.updateCount(productSalesCategory.getTableHeaders().get(3));
			productSalesCategory.updateCount(productSalesCategory.getTableHeaders().get(4));

			// verify report headers
			productSalesCategory.verifyReportHeaders(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME));

			// verify report data
			productSalesCategory.verifyReportData();
		} catch (Exception exc) {
			Assert.fail();
		}

	}
	
	@Test(description = "This test validates Unfinished Close Report Data Calculation")
	public void unfinishedCloseReportData() {
		try {

			final String CASE_NUM = "142906";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			// process sales API to generate data
			unfinishedClose.processAPI(rstProductSummaryData.get(CNProductSummary.SCAN_CODE));

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range ,Location and Group By
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));
			reportList.selectLocation(
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));
			foundation.objectFocus(ReportList.DPD_GROUP_BY);
			dropdown.selectItem(ReportList.DPD_GROUP_BY, rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION),
					Constants.TEXT);

			// run and read report
			foundation.click(ReportList.BTN_RUN_REPORT);
			productSalesCategory.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));
			productSalesCategory.getTblRecordsUI();
			productSalesCategory.getIntialData().putAll(productSalesCategory.getReportsData());

			// Process API and read updated data
			productSalesCategory.processAPI(rstProductSummaryData.get(CNProductSummary.SCAN_CODE),
					rstProductSummaryData.get(CNProductSummary.CATEGORY2));
			foundation.click(ReportList.BTN_RUN_REPORT);
			productSalesCategory.getTblRecordsUI();
			productSalesCategory.getRequiredRecord(rstProductSummaryData.get(CNProductSummary.CATEGORY2));

			// apply calculation and update data
			productSalesCategory.updateSalesAmount();
			productSalesCategory.updateTax();
			productSalesCategory.updateCount(productSalesCategory.getTableHeaders().get(3));
			productSalesCategory.updateCount(productSalesCategory.getTableHeaders().get(4));

			// verify report headers
			productSalesCategory.verifyReportHeaders(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME));

			// verify report data
			productSalesCategory.verifyReportData();
		} catch (Exception exc) {
			Assert.fail();
		}
	}

}
