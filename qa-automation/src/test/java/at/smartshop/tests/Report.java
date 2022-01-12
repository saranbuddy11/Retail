package at.smartshop.tests;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import at.framework.browser.Factory;
import at.framework.database.mssql.Queries;
import at.framework.database.mssql.ResultSets;
import at.framework.generic.CustomisedAssert;
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
import at.smartshop.pages.AVISubFeeReport;
import at.smartshop.pages.AccountAdjustment;
import at.smartshop.pages.AlcoholSoldDetailsReport;
import at.smartshop.pages.BadScanReport;
import at.smartshop.pages.BillingInformationReport;
import at.smartshop.pages.CanadaMultiTaxReport;
import at.smartshop.pages.ConsumerSearch;
import at.smartshop.pages.ConsumerSummary;
import at.smartshop.pages.CrossOrgLoyaltyReport;
import at.smartshop.pages.DataSourceManager;
import at.smartshop.pages.DeviceByCategoryReport;
import at.smartshop.pages.EmployeeCompDetailsReport;
import at.smartshop.pages.FinancialRecapReport;
import at.smartshop.pages.FolioBillingReport;
import at.smartshop.pages.HealthAheadPercentageReport;
import at.smartshop.pages.HealthAheadReport;
import at.smartshop.pages.ICEReport;
import at.smartshop.pages.IntegrationPaymentReport;
import at.smartshop.pages.InvoiceDetailsReport;
import at.smartshop.pages.ItemStockoutReport;
import at.smartshop.pages.LocationList;
import at.smartshop.pages.LocationSummary;
import at.smartshop.pages.LoyaltyUserReport;
import at.smartshop.pages.MemberPurchaseDetailsReport;
import at.smartshop.pages.MemberPurchaseSummaryReport;
import at.smartshop.pages.NavigationBar;
import at.smartshop.pages.OrderTransactionTimeReport;
import at.smartshop.pages.PersonalChargeReport;
import at.smartshop.pages.ProductPricingReport;
import at.smartshop.pages.ProductSalesByCategoryReport;
import at.smartshop.pages.ProductTaxReport;
import at.smartshop.pages.QueuedCreditTransactionsReport;
import at.smartshop.pages.ReportList;
import at.smartshop.pages.SalesAnalysisReport;
import at.smartshop.pages.TenderTransactionLogReport;
import at.smartshop.pages.TipDetailsReport;
import at.smartshop.pages.TipSummaryReport;
import at.smartshop.pages.TransactionCannedReport;
import at.smartshop.pages.UnfinishedCloseReport;
import at.smartshop.pages.UnsoldReport;
import at.smartshop.pages.VoidedProductReport;
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
	private CanadaMultiTaxReport canadaMultiTax = new CanadaMultiTaxReport();
	private ProductSalesByCategoryReport productSalesCategory = new ProductSalesByCategoryReport();
	private UnfinishedCloseReport unfinishedClose = new UnfinishedCloseReport();
	private FolioBillingReport folioBilling = new FolioBillingReport();
	private QueuedCreditTransactionsReport queuedCreditTrans = new QueuedCreditTransactionsReport();
	private VoidedProductReport voidedProduct = new VoidedProductReport();
	private InvoiceDetailsReport invoiceDetails = new InvoiceDetailsReport();
	private IntegrationPaymentReport integrationPayments = new IntegrationPaymentReport();
	private SalesAnalysisReport salesAnalysisReport = new SalesAnalysisReport();
	private DataSourceManager dataSourceManager = new DataSourceManager();
	private TenderTransactionLogReport tenderTransactionLog = new TenderTransactionLogReport();
	private OrderTransactionTimeReport orderTransactionTime = new OrderTransactionTimeReport();
	private FinancialRecapReport financialRecap = new FinancialRecapReport();
	private HealthAheadPercentageReport healthAheadPercentage = new HealthAheadPercentageReport();
	private AVISubFeeReport aviSubFee = new AVISubFeeReport();
	private BillingInformationReport billingInformation = new BillingInformationReport();
	private PersonalChargeReport personalCharge = new PersonalChargeReport();
	private UnsoldReport unsold = new UnsoldReport();
	private LoyaltyUserReport loyaltyUser = new LoyaltyUserReport();
	private CrossOrgLoyaltyReport crossOrgLoyalty = new CrossOrgLoyaltyReport();
	private AlcoholSoldDetailsReport alcoholSoldDetails = new AlcoholSoldDetailsReport();

	private Map<String, String> rstNavigationMenuData;
	private Map<String, String> rstConsumerSearchData;
	private Map<String, String> rstProductSummaryData;
	private Map<String, String> rstLocationSummaryData;
	private Map<String, String> rstConsumerSummaryData;
	private Map<String, String> rstReportListData;
	
	@Parameters({ "driver", "browser", "reportsDB" })

	@BeforeClass
	public void beforeTest(String drivers, String browsers, String reportsDB) {
		try {
			browser.launch(drivers, browsers);
			dataSourceManager.switchToReportsDB(reportsDB);
			browser.close();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "119928-This test validates account adjustment report")
	public void accountAdjustmentReport() {
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
			foundation.click(consumerSearch.objCell(requiredData.get(5)));
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
			foundation.click(ConsumerSummary.BTN_REASON_SAVE);

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
			foundation.objectFocus(ReportList.BTN_RUN_REPORT);
			foundation.objectClick(ReportList.BTN_RUN_REPORT);
			foundation.waitforElement(AccountAdjustment.LBL_REPORT_NAME, Constants.SHORT_TIME);

			// Validate Account Adjustment Report Title
			String reportName = foundation.getText(AccountAdjustment.LBL_REPORT_NAME);
			CustomisedAssert.assertTrue(reportName.contains(rstReportListData.get(CNReportList.REPORT_NAME)));

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
			dbData.put(tblColumnHeader.get(0), String.valueOf(updatedTime.toUpperCase()));
			textBox.enterText(AccountAdjustment.TXT_SEARCH, String.valueOf(updatedTime).toUpperCase());

			// Storing UI data in iuData Map
			Map<String, String> uiData = accountAdjustment.getTblRecordsUI();

			// Validate account adjustment adjusted report data
			CustomisedAssert.assertEquals(uiData, dbData);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
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
			foundation.waitforClikableElement(ReportList.BTN_RUN_REPORT, Constants.SHORT_TIME);
			foundation.click(ReportList.BTN_RUN_REPORT);
			
			foundation.waitforElement(ProductTaxReport.LBL_REPORT_NAME, Constants.LONG_TIME);

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
			TestInfra.failWithScreenShot(exc.toString());
		}

	}

	@Test(description = "135696-This test validates Product Pricing Report Data Calculation")
	public void productPricingReportData() {
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

			List<String> menu = Arrays
					.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));

			navigationBar.navigateToMenuItem(menu.get(2));
			dataSourceManager.unCheckCheckBox(rstReportListData.get(CNReportList.REPORT_NAME));

			// Navigate to Reports

			navigationBar.navigateToMenuItem(menu.get(0));

			// Select the Report Date range and Location
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectLocation(
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));

			// run and read report
			foundation.waitforClikableElement(ReportList.BTN_RUN_REPORT, Constants.SHORT_TIME);
			foundation.click(ReportList.BTN_RUN_REPORT);
			productPricing.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));
			productPricing.getTblRecordsUI();
			productPricing.getIntialData().putAll(productPricing.getReportsData());

			// get Location Data
			navigationBar.navigateToMenuItem(menu.get(1));
			locationList.selectLocationName(
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
			TestInfra.failWithScreenShot(exc.toString());
		}

	}

	@Test(description = "130612-This test validates Transaction Canned Report Data Calculation")
	public void transactionCannedReportData() {
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

			String deviceId = rstProductSummaryData.get(CNProductSummary.DEVICE_ID);
			
			// process sales API to generate data
			transactionCanned.processAPI(rstLocationSummaryData.get(CNLocationSummary.REQUIRED_DATA), deviceId);

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
			foundation.objectFocus(ReportList.BTN_RUN_REPORT);
			foundation.click(ReportList.BTN_RUN_REPORT);
			transactionCanned.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));
			transactionCanned.getTblRecordsUI();
			transactionCanned.getIntialData().putAll(transactionCanned.getReportsData());
			transactionCanned.getIntialTotal().putAll(transactionCanned.getUpdatedTotal());

			// read updated data
			transactionCanned.processAPI(rstLocationSummaryData.get(CNLocationSummary.REQUIRED_DATA), deviceId);
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
			TestInfra.failWithScreenShot(exc.toString());
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
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "120821-This test validates Bad Scan Report Data Calculation")
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
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "141644- This test validates Device By Category Report Data Calculation")
	public void deviceByCategoryReportData() {
		try {

			final String CASE_NUM = "141644";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			String deviceId = rstProductSummaryData.get(CNProductSummary.DEVICE_ID);
			
			// process sales API to generate data
			deviceByCategory.processAPI(deviceId);
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
			foundation.objectFocus(ReportList.BTN_RUN_REPORT);
			foundation.click(ReportList.BTN_RUN_REPORT);
			deviceByCategory.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));
			foundation.waitforElement(DeviceByCategoryReport.TBL_DEVICE_BY_CATEGORY_GRID, Constants.SHORT_TIME);
			deviceByCategory.getTblRecordsUI(deviceId);
			deviceByCategory.getIntialData().putAll(deviceByCategory.getReportsData());
			deviceByCategory.processAPI(deviceId);

			// apply calculation and update data
			List<String> requiredData = Arrays
					.asList(rstProductSummaryData.get(CNProductSummary.REQUIRED_DATA).split(Constants.DELIMITER_HASH));
			deviceByCategory.updateData(deviceByCategory.getTableHeaders().get(0),
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));
			deviceByCategory.updateData(deviceByCategory.getTableHeaders().get(1), deviceId.toUpperCase());
			foundation.threadWait(Constants.TWO_SECOND);
			deviceByCategory.updateData(deviceByCategory.getTableHeaders().get(2), requiredData.get(0));
			deviceByCategory.updateData(deviceByCategory.getTableHeaders().get(3),
					deviceByCategory.getRequiredJsonData().get(1));
			deviceByCategory.updateData(deviceByCategory.getTableHeaders().get(4),
					deviceByCategory.getRequiredJsonData().get(2));
			deviceByCategory.updateCount(deviceByCategory.getTableHeaders().get(5), requiredData.get(1));
			deviceByCategory.updateCount(deviceByCategory.getTableHeaders().get(6), requiredData.get(2));
			deviceByCategory.updateTotal(deviceByCategory.getRequiredJsonData().get(0),
					deviceByCategory.getTableHeaders().get(5), deviceByCategory.getTableHeaders().get(7));

			// verify report headers
			deviceByCategory.verifyReportHeaders(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME));
			
			foundation.click(ReportList.BTN_RUN_REPORT);
			deviceByCategory.getTblRecordsUI(deviceId);

			// verify report data
			deviceByCategory.verifyReportData();

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "141636-This test validates Employee Comp Details Report Data Calculation")
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
			TestInfra.failWithScreenShot(exc.toString());
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
			foundation.objectFocus(ReportList.BTN_RUN_REPORT);
			foundation.click(ReportList.BTN_RUN_REPORT);
			foundation.waitforElement(MemberPurchaseSummaryReport.LBL_REPORT_NAME, Constants.SHORT_TIME);
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
			TestInfra.failWithScreenShot(exc.toString());
		}

	}

	@Test(description = "142642-This test validates ICE Report Data Calculation")
	public void iceReportData() {
		try {

			final String CASE_NUM = "142642";
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
			List<String> columnName = Arrays
					.asList(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME).split(Constants.DELIMITER_TILD));
			List<String> tabName = Arrays
					.asList(rstLocationSummaryData.get(CNLocationSummary.TAB_NAME).split(Constants.DELIMITER_TILD));
			List<String> actualData = Arrays
					.asList(rstProductSummaryData.get(CNProductSummary.ACTUAL_DATA).split(Constants.DELIMITER_TILD));
			List<String> requiredData = Arrays
					.asList(rstProductSummaryData.get(CNProductSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));

			Thread.sleep(1000);
			navigationBar.navigateToMenuItem(menu.get(0));

			// Select the Report Date range and Location
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));
			reportList.selectLocation(
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));

			// run and read report
			Thread.sleep(1000);
			foundation.waitforClikableElement(ReportList.BTN_RUN_REPORT, Constants.SHORT_TIME);
			foundation.objectClick(ReportList.BTN_RUN_REPORT);
			foundation.waitforElement(ICEReport.LBL_REPORT_NAME, Constants.SHORT_TIME);
			iceReport.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));
			iceReport.getTblRecordsUI();
			iceReport.getIntialData().putAll(iceReport.getReportsData());

			// get Location Data
			navigationBar.navigateToMenuItem(menu.get(1));
			locationList.selectLocationName(
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			iceReport.getADMData().add(foundation.getTextAttribute(LocationSummary.TXT_CUSTOMER, Constants.VALUE));
			iceReport.getADMData()
					.add(foundation.getTextAttribute(LocationSummary.TXT_LOCATION_NUMBER, Constants.VALUE));
			iceReport.getADMData().add(dropdown.getSelectedItem(LocationSummary.DPD_ROUTE));

			// update Data
			locationSummary.selectTab(tabName.get(0));
			// locationSummary.manageColumn(columnName.get(0));
			iceReport.updateData(rstProductSummaryData.get(CNProductSummary.SCAN_CODE), columnName.get(1));
			locationSummary.selectTab(tabName.get(1));
			locationSummary.updateInventory(rstProductSummaryData.get(CNProductSummary.SCAN_CODE), requiredData.get(0),
					actualData.get(0));
			iceReport.updateFills(rstProductSummaryData.get(CNProductSummary.SCAN_CODE), columnName.get(1),
					requiredData.get(0));
			locationSummary.updateInventory(rstProductSummaryData.get(CNProductSummary.SCAN_CODE), requiredData.get(1),
					actualData.get(1));
			foundation.refreshPage();
			locationSummary.selectTab(tabName.get(0));
			iceReport.updateWaste(rstProductSummaryData.get(CNProductSummary.SCAN_CODE), columnName.get(1),
					requiredData.get(1));
			iceReport.updateSold(rstProductSummaryData.get(CNProductSummary.SCAN_CODE));
			iceReport.updateClosingLevel(rstProductSummaryData.get(CNProductSummary.SCAN_CODE), columnName.get(1));

			// Select the Report Date range and Location
			iceReport.processAPI();
			navigationBar.navigateToMenuItem(menu.get(0));
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));
			reportList.selectLocation(
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));

			// run and read report
			foundation.waitforClikableElement(ReportList.BTN_RUN_REPORT, Constants.SHORT_TIME);
			foundation.objectClick(ReportList.BTN_RUN_REPORT);
			iceReport.getTblRecordsUI();

			// verify report headers
			iceReport.verifyReportHeaders(columnName.get(2));

			// verify report data
			iceReport.verifyReportData(rstProductSummaryData.get(CNProductSummary.SCAN_CODE));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}

	}

	@Test(description = "142802-This test validates Tip Summary Report Data Calculation")

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
			TestInfra.failWithScreenShot(exc.toString());
		}

	}

	@Test(description = "142756-This test validates Item Stockout Report Data Calculation")
	public void itemStockoutReportData() {
		try {

			final String CASE_NUM = "142756";

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);
			rstConsumerSummaryData = dataBase.getConsumerSummaryData(Queries.CONSUMER_SUMMARY, CASE_NUM);
			rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			List<String> menu = Arrays
					.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
			List<String> reportName = Arrays
					.asList(rstReportListData.get(CNReportList.REPORT_NAME).split(Constants.DELIMITER_TILD));
			List<String> columnName = Arrays
					.asList(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME).split(Constants.DELIMITER_TILD));
			List<String> actualData = Arrays
					.asList(rstProductSummaryData.get(CNProductSummary.ACTUAL_DATA).split(Constants.DELIMITER_TILD));
			List<String> requiredData = Arrays
					.asList(rstProductSummaryData.get(CNProductSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> reason = Arrays
					.asList(rstConsumerSummaryData.get(CNConsumerSummary.REASON).split(Constants.DELIMITER_TILD));

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// navigate to location
			navigationBar.navigateToMenuItem(menu.get(1));
			locationList.selectLocationName(
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));
			foundation.waitforElement(LocationSummary.LNK_INVENTORY, Constants.SHORT_TIME);
			locationSummary.selectTab(rstLocationSummaryData.get(CNLocationSummary.TAB_NAME));
			locationSummary.updateInventory(rstProductSummaryData.get(CNProductSummary.SCAN_CODE), actualData.get(0),
					reason.get(0));
			locationSummary.updateInventory(rstProductSummaryData.get(CNProductSummary.SCAN_CODE), actualData.get(1),
					reason.get(1));
			String stockout = itemStockout.getStockoutTime(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION),
					rstLocationSummaryData.get(CNLocationSummary.TIME_ZONE));
			// navigate to Reports
			navigationBar.navigateToMenuItem(menu.get(0));

			// Select the Report Date range and Location
			reportList.selectReport(reportName.get(0));
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));
			reportList.selectLocation(
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));
//			foundation.adjustBrowerSize(actualData.get(2));
//			foundation.objectClick(ReportList.BTN_RUN_REPORT);
//			foundation.waitforElement(ItemStockoutReport.LBL_REPORT_NAME, Constants.SHORT_TIME);
//			foundation.adjustBrowerSize(actualData.get(3));
			foundation.click(ReportList.BTN_RUN_REPORT);
			foundation.adjustBrowerSize(actualData.get(2));
			foundation.objectClick(ReportList.BTN_RUN_REPORT);
			foundation.waitforElement(ItemStockoutReport.LBL_REPORT_NAME, Constants.SHORT_TIME);
			foundation.adjustBrowerSize(actualData.get(3));
			foundation.threadWait(Constants.THREE_SECOND);
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
			foundation.threadWait(Constants.THREE_SECOND);
			itemStockout.getIntialDetailsData().putAll(itemStockout.getReportsDetailsData());
			itemStockout.updateDetailsData(stockout.toUpperCase(), requiredData.get(1), reason.get(1));

			// verify report details headers
			itemStockout.verifyReportHeaders(itemStockout.getItemStockoutDetailsHeaders(), columnName.get(2));

			// verify report details data
			itemStockout.verifyReportDetailsData(stockout.toUpperCase(), reason.get(1));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "142814-This test validates Tip Details Report Data Calculation")
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
			List<String> dateRange = Arrays.asList(rstReportListData.get(CNReportList.DATE_RANGE).split(Constants.DELIMITER_TILD));
			
			reportList.selectDate(dateRange.get(0));

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
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "142863-This test validates Health Ahead Report Data Calculation")
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

			String deviceId = rstProductSummaryData.get(CNProductSummary.DEVICE_ID);
			
			// process sales API to generate data
//			healthAhead.processAPI(rstProductSummaryData.get(CNProductSummary.ACTUAL_DATA),
//					rstProductSummaryData.get(CNProductSummary.REQUIRED_DATA), deviceId);

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
//			foundation.threadWait(Constants.ONE_SECOND);
//			foundation.objectFocus(ReportList.BTN_RUN_REPORT);
//			foundation.click(ReportList.BTN_RUN_REPORT);
			
			foundation.objectClick(ReportList.BTN_RUN_REPORT);
			foundation.threadWait(Constants.MEDIUM_TIME);

			healthAhead.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));
//			textBox.enterText(healthAhead.SEARCH_RESULT, deviceId.toUpperCase());

			healthAhead.getTblRecordsUI();
			healthAhead.getIntialData().putAll(healthAhead.getReportsData());
			healthAhead.getRequiredRecord(deviceId.toUpperCase());
			healthAhead.updateData(healthAhead.getTableHeaders().get(2), healthAhead.getRequiredJsonData().get(0));
			healthAhead.updateData(healthAhead.getTableHeaders().get(3), healthAhead.getRequiredJsonData().get(1));
			healthAhead.updateHealthAheadNet();
			// verify report headers
			healthAhead.verifyReportHeaders(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME));

			// verify report data
			healthAhead.verifyReportData();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "143034-This test validates Canada Multi Tax Report Data Calculation")
	public void canadaMultiTaxReportData() {
		try {

			final String CASE_NUM = "143034";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			// process sales API to generate data
			canadaMultiTax.processAPI(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));

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
			canadaMultiTax.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));
			canadaMultiTax.getTblRecordsUI();
			canadaMultiTax.getIntialData().putAll(canadaMultiTax.getReportsData());
			canadaMultiTax.getRequiredRecord((String) canadaMultiTax.getJsonData().get(Reports.TRANS_DATE_TIME),
					canadaMultiTax.getScancodeData());

			// apply calculation and update data
			canadaMultiTax.updateData(canadaMultiTax.getTableHeaders().get(0),
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));
			canadaMultiTax.updateData(canadaMultiTax.getTableHeaders().get(1),
					(propertyFile.readPropertyFile(Configuration.DEVICE_ID, FilePath.PROPERTY_CONFIG_FILE))
							.toUpperCase());
			canadaMultiTax.updateData(canadaMultiTax.getTableHeaders().get(2), canadaMultiTax.getCategory1Data());
			canadaMultiTax.updateData(canadaMultiTax.getTableHeaders().get(3), canadaMultiTax.getCategory2Data());
			canadaMultiTax.updateData(canadaMultiTax.getTableHeaders().get(4), canadaMultiTax.getCategory3Data());
			canadaMultiTax.updateData(canadaMultiTax.getTableHeaders().get(5), canadaMultiTax.getProductNameData());
			canadaMultiTax.updateData(canadaMultiTax.getTableHeaders().get(6), canadaMultiTax.getScancodeData());
			canadaMultiTax.updateData(canadaMultiTax.getTableHeaders().get(7), canadaMultiTax.getPriceData());
			canadaMultiTax.updateData(canadaMultiTax.getTableHeaders().get(8), canadaMultiTax.getDepositData());
			canadaMultiTax.updateData(canadaMultiTax.getTableHeaders().get(9), canadaMultiTax.getDiscountData());
			canadaMultiTax.updateTotalPrice();
			canadaMultiTax.updateData(canadaMultiTax.getTableHeaders().get(11), canadaMultiTax.getTaxCatData());
			canadaMultiTax.updateData(canadaMultiTax.getTableHeaders().get(12),
					(String) canadaMultiTax.getJsonData().get(Reports.TAX_1_LABEL));
			canadaMultiTax.updateData(canadaMultiTax.getTableHeaders().get(13), canadaMultiTax.getTax1Data());
			canadaMultiTax.updateData(canadaMultiTax.getTableHeaders().get(14),
					(String) canadaMultiTax.getJsonData().get(Reports.TAX_2_LABEL));
			canadaMultiTax.updateData(canadaMultiTax.getTableHeaders().get(15), canadaMultiTax.getTax2Data());
			canadaMultiTax.updateData(canadaMultiTax.getTableHeaders().get(16),
					(String) canadaMultiTax.getJsonData().get(Reports.TAX_3_LABEL));
			canadaMultiTax.updateData(canadaMultiTax.getTableHeaders().get(17), canadaMultiTax.getTax3Data());
			canadaMultiTax.updateData(canadaMultiTax.getTableHeaders().get(18),
					(String) canadaMultiTax.getJsonData().get(Reports.TAX_4_LABEL));
			canadaMultiTax.updateData(canadaMultiTax.getTableHeaders().get(19), canadaMultiTax.getTax4Data());
			canadaMultiTax.updateData(canadaMultiTax.getTableHeaders().get(20),
					(String) canadaMultiTax.getJsonData().get(Reports.TAX));

			// verify report headers
			canadaMultiTax.verifyReportHeaders(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME));

			// verify report data
			canadaMultiTax.verifyReportData();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}

	}

	@Test(description = "142906-This test validates Product Sales By Category Report Data Calculation")
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
			TestInfra.failWithScreenShot(exc.toString());
		}

	}

	@Test(description = "143433-This test validates Unfinished Close Report Data Calculation")
	public void unfinishedCloseReportData() {
		try {

			final String CASE_NUM = "143433";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			// process sales API to generate data
			unfinishedClose.processAPI(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION),
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
			unfinishedClose.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));
			String[] orderID = ((String) unfinishedClose.getJsonData().get(Reports.TRANS_ID))
					.split(Constants.DELIMITER_HYPHEN);
			textBox.enterText(UnfinishedCloseReport.TXT_FILTER, orderID[1]);
			unfinishedClose.getTblRecordsUI();
			unfinishedClose.getIntialData().putAll(unfinishedClose.getReportsData());
			unfinishedClose.getRequiredRecord(orderID[1]);

			// apply calculation and update data
			unfinishedClose.updateData(unfinishedClose.getTableHeaders().get(0), orderID[1]);
			unfinishedClose.updateData(unfinishedClose.getTableHeaders().get(1),
					rstProductSummaryData.get(CNProductSummary.ACTUAL_DATA));
			unfinishedClose.updateData(unfinishedClose.getTableHeaders().get(2),
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));
			unfinishedClose.updateData(unfinishedClose.getTableHeaders().get(3),
					(String) unfinishedClose.getJsonData().get(Reports.TRANS_DATE_TIME));
			unfinishedClose.updateData(unfinishedClose.getTableHeaders().get(4),
					unfinishedClose.getRequiredJsonData().get(0));
			unfinishedClose.updateData(unfinishedClose.getTableHeaders().get(5),
					unfinishedClose.getRequiredJsonData().get(1));

			// verify report headers
			unfinishedClose.verifyReportHeaders(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME));

			// verify report data
			unfinishedClose.verifyReportData();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}

	}

	@Test(description = "143189-This test validates Folio Billing Report Data Calculation")
	public void folioBillingReportData() {
		try {

			final String CASE_NUM = "143189";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			// process sales API to generate data
			folioBilling.processAPI(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range ,Location and Group By
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));
			reportList.selectLocation(
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));

			// run and read report
			foundation.click(ReportList.BTN_RUN_REPORT);
			folioBilling.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));
			folioBilling.getTblRecordsUI();
			folioBilling.getIntialData().putAll(folioBilling.getReportsData());
			folioBilling.getRequiredRecord((String) folioBilling.getJsonData().get(Reports.TRANS_DATE),
					folioBilling.getScancodeData());

			// apply calculation and update data
			folioBilling.updateData(folioBilling.getTableHeaders().get(0),
					(String) folioBilling.getJsonData().get(Reports.TRANS_DATE));
			folioBilling.updateData(folioBilling.getTableHeaders().get(1),
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));
			folioBilling.updateData(folioBilling.getTableHeaders().get(2),
					(String) folioBilling.getJsonData().get(Reports.TRANS_ID));
			folioBilling.updateData(folioBilling.getTableHeaders().get(3), folioBilling.getScancodeData());
			folioBilling.updateData(folioBilling.getTableHeaders().get(4), folioBilling.getProductNameData());
			folioBilling.updateData(folioBilling.getTableHeaders().get(5), folioBilling.getPriceData());
			folioBilling.updateData(folioBilling.getTableHeaders().get(6), folioBilling.getTaxData());
			folioBilling.updateTotal();
			folioBilling.updateData(folioBilling.getTableHeaders().get(8), Reports.ROOM);
			folioBilling.updateData(folioBilling.getTableHeaders().get(9),
					rstProductSummaryData.get(CNProductSummary.REQUIRED_DATA));

			// verify report headers
			folioBilling.verifyReportHeaders(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME));

			// verify report data
			folioBilling.verifyReportData();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}

	}

	@Test(description = "143527-This test validates Queued Credit Transactions Report Data Calculation")
	public void queuedCreditTransactionsReportData() {
		try {

			final String CASE_NUM = "143527";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			// process sales API to generate data
			queuedCreditTrans.processAPI(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));

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

			queuedCreditTrans.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));
			queuedCreditTrans.getTblRecordsUI();
			queuedCreditTrans.getIntialData().putAll(queuedCreditTrans.getReportsData());
			queuedCreditTrans.getRequiredRecord((String) queuedCreditTrans.getJsonData().get(Reports.TRANS_DATE_TIME));

			// apply calculation and update data
			queuedCreditTrans.updateData(queuedCreditTrans.getTableHeaders().get(0),
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));
			queuedCreditTrans.updateData(queuedCreditTrans.getTableHeaders().get(1),
					propertyFile.readPropertyFile(Configuration.DEVICE_ID, FilePath.PROPERTY_CONFIG_FILE));
			queuedCreditTrans.updateData(queuedCreditTrans.getTableHeaders().get(2),
					queuedCreditTrans.getRequiredJsonData().get(0));
			queuedCreditTrans.updateData(queuedCreditTrans.getTableHeaders().get(3),
					queuedCreditTrans.getRequiredJsonData().get(1));
			queuedCreditTrans.updateData(queuedCreditTrans.getTableHeaders().get(4),
					queuedCreditTrans.getRequiredJsonData().get(2));
			queuedCreditTrans.updateData(queuedCreditTrans.getTableHeaders().get(5),
					queuedCreditTrans.getRequiredJsonData().get(3));
			queuedCreditTrans.updateData(queuedCreditTrans.getTableHeaders().get(6),
					(String) queuedCreditTrans.getJsonData().get(Reports.TRANS_DATE_TIME));

			// verify report headers
			queuedCreditTrans.verifyReportHeaders(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME));

			// verify report data
			queuedCreditTrans.verifyReportData();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}

	}

	@Test(description = "143268-This test validates Voided Product Report Data Calculation")
	public void voidedProductReportData() {
		try {

			final String CASE_NUM = "143268";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			// process sales API to generate data
			voidedProduct.processAPI(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION),
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
			foundation.objectFocus(ReportList.BTN_RUN_REPORT);
			foundation.click(ReportList.BTN_RUN_REPORT);
			voidedProduct.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));
			String[] orderID = ((String) voidedProduct.getJsonData().get(Reports.TRANS_ID))
					.split(Constants.DELIMITER_HYPHEN);
			textBox.enterText(VoidedProductReport.TXT_FILTER, orderID[1]);
			foundation.waitforElement(VoidedProductReport.TBL_VOIDED_PRODUCT, Constants.SHORT_TIME);
			voidedProduct.getTblRecordsUI();
			voidedProduct.getIntialData().putAll(voidedProduct.getReportsData());
			voidedProduct.getRequiredRecord(
					((String) voidedProduct.getJsonData().get(Reports.TRANS_DATE_TIME)).toUpperCase(),
					voidedProduct.getProductNameData());

			// apply calculation and update data
			voidedProduct.updateData(voidedProduct.getTableHeaders().get(0), orderID[1]);
			voidedProduct.updateData(voidedProduct.getTableHeaders().get(1),
					rstProductSummaryData.get(CNProductSummary.ACTUAL_DATA));
			voidedProduct.updateData(voidedProduct.getTableHeaders().get(2),
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));
			voidedProduct.updateData(voidedProduct.getTableHeaders().get(3),
					(String) voidedProduct.getJsonData().get(Reports.TRANS_DATE_TIME));
			voidedProduct.updateData(voidedProduct.getTableHeaders().get(4), voidedProduct.getProductNameData());
			voidedProduct.updateData(voidedProduct.getTableHeaders().get(5), voidedProduct.getPriceData());

			// verify report headers
			voidedProduct.verifyReportHeaders(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME));

			// verify report data
			voidedProduct.verifyReportData();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "143547 -This test validates Integration Payments Report Data Calculation")
	public void integrationPaymentsReportData() {
		try {

			final String CASE_NUM = "143547";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			// process sales API to generate data
			integrationPayments.processAPI(Reports.GENESIS + Constants.DELIMITER_TILD + Reports.SPECIAL);

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
			foundation.waitforClikableElement(ReportList.BTN_RUN_REPORT, Constants.SHORT_TIME);
			foundation.click(ReportList.BTN_RUN_REPORT);

			integrationPayments.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));
			integrationPayments.getTblRecordsUI();
			integrationPayments.getIntialData().putAll(integrationPayments.getReportsData());
			integrationPayments.getRequiredRecord(
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE),
					Reports.SPECIAL + Constants.DELIMITER_TILD + Reports.GENESIS);
			integrationPayments.processAPI(Reports.SPECIAL + Constants.DELIMITER_TILD + Reports.GENESIS);
			Thread.sleep(1000);
			foundation.waitforClikableElement(ReportList.BTN_RUN_REPORT, Constants.SHORT_TIME);
			foundation.click(ReportList.BTN_RUN_REPORT);
			integrationPayments.getTblRecordsUI();

			// apply calculation and update data
			integrationPayments.updateData(integrationPayments.getTableHeaders().get(0),
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));
			integrationPayments.updateData(integrationPayments.getTableHeaders().get(1), propertyFile
					.readPropertyFile(Configuration.DEVICE_ID, FilePath.PROPERTY_CONFIG_FILE).toUpperCase());
			integrationPayments.updateValue(integrationPayments.getTableHeaders().get(2),
					Reports.SPECIAL + Constants.DELIMITER_TILD + Reports.GENESIS);
			integrationPayments.calculateAmount(integrationPayments.getAmountData());

			// verify report headers
			integrationPayments.verifyReportHeaders(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME));

			// verify report data
			integrationPayments.verifyReportData();
		} catch (Exception exc) {
			Assert.fail();
		}
	}

	@Test(description = "143532-Verify Gross Margin value in Sales Analysis Report uisng new formula")
	public void verifyGMPercentage() {
		try {

			final String CASE_NUM = "143532";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			salesAnalysisReport.processAPI(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			List<String> reportName = Arrays
					.asList(rstReportListData.get(CNReportList.REPORT_NAME).split(Constants.DELIMITER_TILD));
			List<String> columnName = Arrays
					.asList(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME).split(Constants.DELIMITER_TILD));
			String productName = rstProductSummaryData.get(CNProductSummary.PRODUCT_NAME);
			String productPrice = rstProductSummaryData.get(CNProductSummary.PRICE);

			reportList.selectReport(reportName.get(0));
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));
			reportList.selectLocation(
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(ReportList.BTN_RUN_REPORT);
			foundation.waitforElement(SalesAnalysisReport.LBL_REPORT_NAME, Constants.SHORT_TIME);
			salesAnalysisReport.verifyReportName(reportName.get(0));
			double expectedGMValue = salesAnalysisReport.getGMValueUsingCalculation(productPrice, productName,
					columnName.get(0), columnName.get(1), columnName.get(2));
			double actualGMValue = salesAnalysisReport.getGMValue(columnName.get(3), productName);

			CustomisedAssert.assertEquals(actualGMValue, expectedGMValue);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "145708-This test validates Tender Transactions Log Report Data Calculation")
	public void tenderTransactionLogData() {
		try {

			final String CASE_NUM = "145708";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			List<String> requiredData = Arrays
					.asList(rstProductSummaryData.get(CNProductSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));

			List<String> columnName = Arrays
					.asList(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME).split(Constants.DELIMITER_TILD));

			// process sales API to generate data
			tenderTransactionLog.processAPI(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION),
					rstProductSummaryData.get(CNProductSummary.ACTUAL_DATA));

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
			foundation.waitforClikableElement(ReportList.BTN_RUN_REPORT, Constants.SHORT_TIME);
			foundation.click(ReportList.BTN_RUN_REPORT);
			tenderTransactionLog.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));
			tenderTransactionLog.getTblRecordsUI();
			tenderTransactionLog.getIntialData().putAll(tenderTransactionLog.getReportsData());
			tenderTransactionLog.getRequiredRecord(
					(String) tenderTransactionLog.getJsonData().get(Reports.TRANS_DATE_TIME),
					productTax.getScancodeData());

			// apply calculation and update data
			tenderTransactionLog.updateData(tenderTransactionLog.getTableHeaders().get(0),
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));
			tenderTransactionLog.updateData(tenderTransactionLog.getTableHeaders().get(1),
					propertyFile.readPropertyFile(Configuration.DEVICE_ID, FilePath.PROPERTY_CONFIG_FILE));
			tenderTransactionLog.updateData(tenderTransactionLog.getTableHeaders().get(2),
					(String) tenderTransactionLog.getJsonData().get(Reports.TRANS_ID));
			tenderTransactionLog.updateData(tenderTransactionLog.getTableHeaders().get(3),
					(String) tenderTransactionLog.getJsonData().get(Reports.TRANS_DATE_TIME));
			tenderTransactionLog.updateData(tenderTransactionLog.getTableHeaders().get(4), requiredData.get(0));
			tenderTransactionLog.updateData(tenderTransactionLog.getTableHeaders().get(5), requiredData.get(1));
			tenderTransactionLog.updateData(tenderTransactionLog.getTableHeaders().get(6), requiredData.get(2));
			tenderTransactionLog.updateCashInCashOut(columnName.get(0), requiredData.get(3), requiredData.get(4));

			// verify report headers
			tenderTransactionLog.verifyReportHeaders(columnName.get(1));

			// verify report data
			tenderTransactionLog.verifyReportData();

		} catch (Exception exc) {
			Assert.fail();
		}

	}

	@Test(description = "145313-This test validates Order Transaction Time Report Data Calculation")
	public void orderTransactionTimeReportData() {
		try {

			final String CASE_NUM = "145313";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			List<String> requiredData = Arrays
					.asList(rstProductSummaryData.get(CNProductSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			// process sales API to generate data
			orderTransactionTime.processAPI(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));
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
			Thread.sleep(1000);
			foundation.waitforClikableElement(ReportList.BTN_RUN_REPORT, Constants.SHORT_TIME);
			foundation.click(ReportList.BTN_RUN_REPORT);
			orderTransactionTime.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));
			textBox.enterText(orderTransactionTime.SEARCH_RESULT, ((String) orderTransactionTime.getJsonData().get(Reports.TRANS_DATE_TIME)).toUpperCase());
			orderTransactionTime.getTblRecordsUI();
			orderTransactionTime.getIntialData().putAll(orderTransactionTime.getReportsData());
			orderTransactionTime.getRequiredRecord(
					((String) orderTransactionTime.getJsonData().get(Reports.TRANS_DATE_TIME)).toUpperCase());

			// apply calculation and update data
			orderTransactionTime.updateData(orderTransactionTime.getTableHeaders().get(1),
					(String) orderTransactionTime.getJsonData().get(Reports.TRANS_DATE_TIME));
			orderTransactionTime.updateData(orderTransactionTime.getTableHeaders().get(0),
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));
			orderTransactionTime.updateData(orderTransactionTime.getTableHeaders().get(2), requiredData.get(0));
			orderTransactionTime.updateData(orderTransactionTime.getTableHeaders().get(3), requiredData.get(1));
			orderTransactionTime.updateData(orderTransactionTime.getTableHeaders().get(4), requiredData.get(2));

			// verify report headers
			orderTransactionTime.verifyReportHeaders(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME));

			// verify report data
			orderTransactionTime.verifyReportData();

		} catch (Exception exc) {
			Assert.fail();
		}

	}

	@Test(description = "145249-This test validates Invoice Details Report Data Calculation")
	public void invoiceDetailsReportData() {
		try {

			final String CASE_NUM = "145249";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			// process sales API to generate data
			invoiceDetails.processAPI(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));

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

			invoiceDetails.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));
			invoiceDetails.getTblRecordsUI();
			invoiceDetails.getIntialData().putAll(invoiceDetails.getReportsData());
			invoiceDetails.getRequiredRecord((String) invoiceDetails.getJsonData().get(Reports.TRANS_ID),
					invoiceDetails.getScancodeData());

			// apply calculation and update data
			invoiceDetails.updateData(invoiceDetails.getTableHeaders().get(0),
					(String) invoiceDetails.getJsonData().get(Reports.TRANS_ID));
			invoiceDetails.updateData(invoiceDetails.getTableHeaders().get(1),
					(String) invoiceDetails.getJsonData().get(Reports.TRANS_DATE_TIME));
			invoiceDetails.updateData(invoiceDetails.getTableHeaders().get(2), invoiceDetails.getScancodeData());
			invoiceDetails.updateData(invoiceDetails.getTableHeaders().get(3), invoiceDetails.getProductNameData());
			invoiceDetails.updateData(invoiceDetails.getTableHeaders().get(4), invoiceDetails.getUnitMeasureData());
			invoiceDetails.updateData(invoiceDetails.getTableHeaders().get(5), invoiceDetails.getQuantityData());
			invoiceDetails.updateData(invoiceDetails.getTableHeaders().get(6), invoiceDetails.getPriceData());
			invoiceDetails.updateVAT();
			invoiceDetails.updateData(invoiceDetails.getTableHeaders().get(8), invoiceDetails.getTaxData());
			// verify report headers
			invoiceDetails.verifyReportHeaders(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME));

			// verify report data
			invoiceDetails.verifyReportData();
		} catch (Exception exc) {
			Assert.fail();
		}
	}

	@Test(description = "146058-This test validates Health Ahead Percentage Report Data Calculation")
	public void healthAheadPercentageReportData() {
		try {

			final String CASE_NUM = "146058";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			List<String> requiredData = Arrays
					.asList(rstProductSummaryData.get(CNProductSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));

			// process sales API to generate data
			healthAheadPercentage.processAPI();

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
			foundation.waitforClikableElement(ReportList.BTN_RUN_REPORT, Constants.SHORT_TIME);
			foundation.click(ReportList.BTN_RUN_REPORT);
			healthAheadPercentage.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));
			healthAheadPercentage.getTblRecordsUI();
			healthAheadPercentage.getIntialData().putAll(healthAheadPercentage.getReportsData());
			healthAheadPercentage.getRequiredRecord(
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));

			// apply calculation and update data
			healthAheadPercentage.updateData(
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));
			healthAheadPercentage.calculatePercentage(Integer.parseInt(requiredData.get(0)),
					Integer.parseInt(requiredData.get(1)));

			// verify report headers
			healthAheadPercentage.verifyReportHeaders(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME));

			// verify report data
			healthAheadPercentage.verifyReportData();

		} catch (Exception exc) {
			Assert.fail();
		}

	}

	@Test(description = "146027-This test validates AVI Sub Fee Report Data Calculation")
	public void aviSubFeeReportData() {
		try {

			final String CASE_NUM = "146027";

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));

			String deviceId = rstProductSummaryData.get(CNProductSummary.DEVICE_ID);
			
			aviSubFee.getPriorMonthData(rstProductSummaryData.get(CNProductSummary.REQUIRED_DATA),
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE),
					rstProductSummaryData.get(CNProductSummary.ACTUAL_DATA), deviceId);
			
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			aviSubFee.selectToday();
			reportList
					.selectOrg(propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// run and read report
			foundation.waitforClikableElement(ReportList.BTN_RUN_REPORT, Constants.SHORT_TIME);
			foundation.click(ReportList.BTN_RUN_REPORT);
			CustomisedAssert.assertTrue(Factory.getDriver().findElement(AVISubFeeReport.LBL_REPORT_NAME).isDisplayed());
			aviSubFee.getTblRecordsUI();
			aviSubFee.getIntialData().putAll(aviSubFee.getReportsData());

			// process sales API to generate data
			aviSubFee.processAPI(deviceId);
			foundation.waitforClikableElement(ReportList.BTN_RUN_REPORT, Constants.SHORT_TIME);
			foundation.click(ReportList.BTN_RUN_REPORT);
			aviSubFee.getTblRecordsUI();

			// apply calculation and update data
			aviSubFee.updateData(aviSubFee.getTableHeaders().get(0),
					rstProductSummaryData.get(CNProductSummary.ACTUAL_DATA));
			aviSubFee.updateData(aviSubFee.getTableHeaders().get(2),
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));
			aviSubFee.updateData(aviSubFee.getTableHeaders().get(1),
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			aviSubFee.updateData(aviSubFee.getTableHeaders().get(3), deviceId);
			aviSubFee.updateData(aviSubFee.getTableHeaders().get(5), (String) aviSubFee.getRequiredJsonData().get(0));
			aviSubFee.calculateTotalBillable();
			aviSubFee.updateData(aviSubFee.getTableHeaders().get(7), (String) aviSubFee.getRequiredJsonData().get(2));

			// verify report headers
			aviSubFee.verifyReportHeaders(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME));

			// verify report data
			aviSubFee.verifyReportData();
		} catch (Exception exc) {
			Assert.fail();
		}

	}

	@Test(description = "145723-This test validates Billing Information Report Data Calculation")
	public void billingInformationReportData() {
		try {

			final String CASE_NUM = "145723";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			List<String> requiredData = Arrays
					.asList(rstProductSummaryData.get(CNProductSummary.REQUIRED_DATA).split(Constants.DELIMITER_HASH));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// process sales API to generate data
			billingInformation.processAPI(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));

			// Select Menu and Menu Item
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));

			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));

			reportList.selectLocation(
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));

			// run and read report
			foundation.click(ReportList.BTN_RUN_REPORT);

			billingInformation.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));
			textBox.enterText(BillingInformationReport.TXT_FILTER,
					(String) billingInformation.getJsonData().get(Reports.TRANS_ID));
			billingInformation.getTblRecordsUI();
			billingInformation.getIntialData().putAll(billingInformation.getReportsData());
			billingInformation.getRequiredRecord((String) billingInformation.getJsonData().get(Reports.TRANS_ID));
			// apply calculation and update data
			billingInformation.updateData(billingInformation.getTableHeaders().get(0),
					(String) billingInformation.getJsonData().get(Reports.TRANS_ID));
			billingInformation.updateData(billingInformation.getTableHeaders().get(1),
					(String) billingInformation.getJsonData().get(Reports.TRANS_DATE_TIME));
			billingInformation.updateData(billingInformation.getTableHeaders().get(2), requiredData.get(0));
			billingInformation.updateData(billingInformation.getTableHeaders().get(3), requiredData.get(1));
			billingInformation.updateData(billingInformation.getTableHeaders().get(4), requiredData.get(2));
			billingInformation.updateData(billingInformation.getTableHeaders().get(5), requiredData.get(3));
			billingInformation.updateData(billingInformation.getTableHeaders().get(6), requiredData.get(4));
			billingInformation.updateData(billingInformation.getTableHeaders().get(7), requiredData.get(5));
			billingInformation.updateData(billingInformation.getTableHeaders().get(8),
					billingInformation.getRequiredJsonData().get(0));
			billingInformation.updateData(billingInformation.getTableHeaders().get(9),
					billingInformation.getRequiredJsonData().get(1));
			billingInformation.updateData(billingInformation.getTableHeaders().get(10),
					billingInformation.getRequiredJsonData().get(2));

			// verify report headers
			billingInformation.verifyReportHeaders(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME));

			// verify report data
			billingInformation.verifyReportData();

		} catch (Exception exc) {
			Assert.fail();
		}

	}

	@Test(description = "120387-This test validates Financial Recap Report Data Calculation")
	public void financialRecapReportData() {
		try {

			final String CASE_NUM = "120387";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);
			rstConsumerSearchData = dataBase.getConsumerSearchData(Queries.CONSUMER_SEARCH, CASE_NUM);
			rstConsumerSummaryData = dataBase.getConsumerSummaryData(Queries.CONSUMER_SUMMARY, CASE_NUM);

			List<String> menu = Arrays
					.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.navigateToMenuItem(menu.get(0));

			// Select the Report Date range and Location
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));

			reportList.selectLocation(
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));

			// run and read report
			foundation.objectClick(ReportList.BTN_RUN_REPORT);
			financialRecap.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));
			financialRecap.getTblRecordsUI();
			financialRecap.getIntialData().putAll(financialRecap.getReportsData());
			financialRecap.getFinancialRecapTotalData().putAll(financialRecap.getReportsTotalData());
			foundation.objectFocus(NavigationBar.DPD_ORG);

			// Consumer Details
			navigationBar.navigateToMenuItem(menu.get(1));
			foundation.waitforElement(ConsumerSearch.TXT_CONSUMER_SEARCH, Constants.SHORT_TIME);
			consumerSearch.enterSearchFields(rstConsumerSearchData.get(CNConsumerSearch.SEARCH_BY),
					rstConsumerSearchData.get(CNConsumerSearch.CONSUMER_ID),
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE),
					rstConsumerSearchData.get(CNConsumerSearch.STATUS));
			consumerSearch.verifyConsumerSummary(rstProductSummaryData.get(CNProductSummary.REQUIRED_DATA));
			financialRecap.adjustBalance(rstConsumerSummaryData.get(CNConsumerSummary.ADJUST_BALANCE),
					rstConsumerSummaryData.get(CNConsumerSummary.REASON));
			financialRecap.processAPI(rstProductSummaryData.get(CNProductSummary.ACTUAL_DATA));
			navigationBar.navigateToMenuItem(menu.get(0));
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));
			reportList.selectLocation(
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));
			foundation.objectClick(ReportList.BTN_RUN_REPORT);
			financialRecap.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));
			financialRecap.getTblRecordsUI();

			// apply calculation and update data
			financialRecap.updateData(financialRecap.getTableHeaders().get(0),
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));
			financialRecap.updateData(financialRecap.getTableHeaders().get(1),
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			financialRecap.calculateAmount(financialRecap.getTableHeaders().get(4),
					financialRecap.getRequiredJsonData().get(1));
			financialRecap.calculateAmount(financialRecap.getTableHeaders().get(5),
					financialRecap.getRequiredJsonData().get(1));
			financialRecap.calculateAmount(financialRecap.getTableHeaders().get(6),
					financialRecap.getRequiredJsonData().get(1));
			financialRecap.updateGrossSales();
			financialRecap.updateFees(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION),
					financialRecap.getTableHeaders().get(4), financialRecap.getTableHeaders().get(8));
			financialRecap.updateFees(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION),
					financialRecap.getTableHeaders().get(5), financialRecap.getTableHeaders().get(9));
			financialRecap.updateAdjustment(rstConsumerSummaryData.get(CNConsumerSummary.ADJUST_BALANCE),
					financialRecap.getTableHeaders().get(10));
			financialRecap.updateAdjustment(rstConsumerSummaryData.get(CNConsumerSummary.ADJUST_BALANCE),
					financialRecap.getTableHeaders().get(11));
			financialRecap.updateAdjustment(rstConsumerSummaryData.get(CNConsumerSummary.ADJUST_BALANCE),
					financialRecap.getTableHeaders().get(12));
			financialRecap.updateAdjustment(rstConsumerSummaryData.get(CNConsumerSummary.ADJUST_BALANCE),
					financialRecap.getTableHeaders().get(13));
			financialRecap.updateNetCashOwed();
			financialRecap.updateSalesTax();
			financialRecap.updateTotal();

			// verify report headers
			financialRecap.verifyReportHeaders(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME));

			// verify report data
			financialRecap.verifyReportData();
		} catch (Exception exc) {
			Assert.fail();
		}

	}

	@Test(description = "146142-This test validates Personal Charge Report Data Calculation")
	public void personalChargeReportData() {
		try {

			final String CASE_NUM = "146142";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			// Split database data
			List<String> requiredData = Arrays
					.asList(rstProductSummaryData.get(CNProductSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));

			// process sales API to generate data
			personalCharge.processAPI(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));

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
			foundation.waitforElement(ProductTaxReport.LBL_REPORT_NAME, Constants.SHORT_TIME);

			personalCharge.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));
			textBox.enterText(personalCharge.SEARCH_RESULT, (String) personalCharge.getJsonData().get(Reports.TRANS_ID));
			
			personalCharge.getTblRecordsUI();
			personalCharge.getIntialData().putAll(personalCharge.getReportsData());
			personalCharge.getRequiredRecord(
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE),
					(String) personalCharge.getJsonData().get(Reports.TRANS_ID));
			// apply calculation and update data
			personalCharge.updateData(personalCharge.getTableHeaders().get(0),
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));
			personalCharge.updateData(personalCharge.getTableHeaders().get(1), requiredData.get(0));
			personalCharge.updateData(personalCharge.getTableHeaders().get(2), requiredData.get(1));
			personalCharge.updateData(personalCharge.getTableHeaders().get(3), requiredData.get(0));
			personalCharge.updateData(personalCharge.getTableHeaders().get(4), requiredData.get(0));
			personalCharge.updateData(personalCharge.getTableHeaders().get(5),
					personalCharge.getRequiredJsonData().get(0));
			personalCharge.updateData(personalCharge.getTableHeaders().get(6), requiredData.get(2));

			personalCharge.updateData(personalCharge.getTableHeaders().get(7),
					(String) personalCharge.getJsonData().get(Reports.TRANS_ID));
			personalCharge.updateData(personalCharge.getTableHeaders().get(8),
					(String) personalCharge.getJsonData().get(Reports.TRANS_DATE_TIME));

			// verify report headers
			personalCharge.verifyReportHeaders(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME));

			// verify report data
			personalCharge.verifyReportData();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}

	}

	@Test(description = "146147 -This test validates Unsold Report Data Calculation")
	public void unsoldReportData() {
		try {

			final String CASE_NUM = "146147";

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
			unsold.processAPI();

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			List<String> menuItems = Arrays
					.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
			List<String> columnName = Arrays
					.asList(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME).split(Constants.DELIMITER_TILD));

			navigationBar.navigateToMenuItem(menuItems.get(0));

			locationList.selectLocationName(
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));
			locationSummary.selectTab(rstLocationSummaryData.get(CNLocationSummary.TAB_NAME));
			unsold.getProductNameData().addAll(locationSummary.getProductsNames());
			navigationBar.navigateToMenuItem(menuItems.get(1));

			// Select the Report Date range and Location
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));
			dropdown.selectItem(UnsoldReport.DPD_FILTER_BY, menuItems.get(0), Constants.TEXT);
			unsold.selectLocation(
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));

			// run and read report
			foundation.click(ReportList.BTN_RUN_REPORT);
			foundation.waitforElement(ProductTaxReport.LBL_REPORT_NAME, Constants.SHORT_TIME);

			unsold.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));
			unsold.getTblRecordsUI(columnName.get(0));

			// verify report headers
			unsold.verifyReportHeaders(columnName.get(1));

			// verify report data
			unsold.verifySoldProductsExist();
			unsold.verifyAllUnSoldProductsExist();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}

	}

	@Test(description = "146231-This test validates Loyalty User Report Data Calculation")
	public void loyaltyUserReportData() {
		try {

			final String CASE_NUM = "146231";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			// process sales API to generate data
			loyaltyUser.processAPI(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));

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
			foundation.waitforElement(ProductTaxReport.LBL_REPORT_NAME, Constants.SHORT_TIME);

			loyaltyUser.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));
			loyaltyUser.getTblRecordsUI();
			loyaltyUser.getIntialData().putAll(loyaltyUser.getReportsData());
			loyaltyUser.getRequiredRecord(propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));
			// apply calculation and update data
			loyaltyUser.updateData(loyaltyUser.getTableHeaders().get(0),
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			loyaltyUser.updateData(loyaltyUser.getTableHeaders().get(1),
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));
			loyaltyUser.updateData(loyaltyUser.getTableHeaders().get(2),
					rstProductSummaryData.get(CNProductSummary.REQUIRED_DATA));
			loyaltyUser.updateData(loyaltyUser.getTableHeaders().get(3), loyaltyUser.getRequiredJsonData().get(2));
			loyaltyUser.updateData(loyaltyUser.getTableHeaders().get(4), loyaltyUser.getRequiredJsonData().get(0));
			loyaltyUser.updateData(loyaltyUser.getTableHeaders().get(5),
					(String) loyaltyUser.getJsonData().get(Reports.TRANS_DATE_TIME));

			// verify report headers
			loyaltyUser.verifyReportHeaders(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME));

			// verify report data
			loyaltyUser.verifyReportData();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}

	}

	@Test(description = "146235-This test validates Cross Org Loyalty Report Data Calculation")
	public void crossOrgLoyaltyReportData() {
		try {

			final String CASE_NUM = "146235";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			// process sales API to generate data
			crossOrgLoyalty.processAPI();

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));

			reportList
					.selectOrg(propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// run and read report
			foundation.click(ReportList.BTN_RUN_REPORT);
			foundation.waitforElement(ProductTaxReport.LBL_REPORT_NAME, Constants.SHORT_TIME);
			crossOrgLoyalty.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));
			textBox.enterText(crossOrgLoyalty.SEARCH_RESULT, propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));
			
			crossOrgLoyalty.getTblRecordsUI();
			crossOrgLoyalty.getIntialData().putAll(crossOrgLoyalty.getReportsData());
			crossOrgLoyalty.getRequiredRecord(
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));
			crossOrgLoyalty.processAPI();
			foundation.threadWait(Constants.MEDIUM_TIME);
			foundation.click(ReportList.BTN_RUN_REPORT);
			textBox.enterText(crossOrgLoyalty.SEARCH_RESULT, propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));
			
			crossOrgLoyalty.getTblRecordsUI();

			// apply calculation and update data
			crossOrgLoyalty.updateData(crossOrgLoyalty.getTableHeaders().get(0),
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			crossOrgLoyalty.updateData(crossOrgLoyalty.getTableHeaders().get(1),
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));
			crossOrgLoyalty.updateData(crossOrgLoyalty.getTableHeaders().get(2),
					rstProductSummaryData.get(CNProductSummary.REQUIRED_DATA));
			crossOrgLoyalty.updatePoints(crossOrgLoyalty.getTableHeaders().get(4),
					rstProductSummaryData.get(CNProductSummary.ACTUAL_DATA));
			crossOrgLoyalty.updatePoints(crossOrgLoyalty.getTableHeaders().get(5),
					crossOrgLoyalty.getRequiredJsonData().get(2));
			crossOrgLoyalty.updateMoney(crossOrgLoyalty.getRequiredJsonData().get(0));
			crossOrgLoyalty.updatePointsPerDollor();

			// verify report headers
			crossOrgLoyalty.verifyReportHeaders(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME));

			// verify report data
			crossOrgLoyalty.verifyReportData();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}

	}

	@Test(description = "147403-This test validates Alcohol sold Details Report Data Calculation")
	public void alcoholSoldDetailsReportData() {
		try {

			final String CASE_NUM = "147403";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			// process sales API to generate data
			alcoholSoldDetails.processAPI();

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
			foundation.waitforElement(ProductTaxReport.LBL_REPORT_NAME, Constants.SHORT_TIME);

			alcoholSoldDetails.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));
			alcoholSoldDetails.getTblRecordsUI();
			alcoholSoldDetails.getIntialData().putAll(alcoholSoldDetails.getReportsData());
			alcoholSoldDetails.getRequiredRecord(alcoholSoldDetails.getScancodeData());

			// apply calculation and update data
			alcoholSoldDetails.updateData(alcoholSoldDetails.getTableHeaders().get(0),
					alcoholSoldDetails.getScancodeData());
			alcoholSoldDetails.updateData(alcoholSoldDetails.getTableHeaders().get(1),
					alcoholSoldDetails.getProductNameData());
			alcoholSoldDetails.updateSales(alcoholSoldDetails.getTableHeaders().get(2),
					(String) alcoholSoldDetails.getRequiredJsonData().get(0));
			alcoholSoldDetails.updateTax(alcoholSoldDetails.getTableHeaders().get(3),
					(String) alcoholSoldDetails.getRequiredJsonData().get(1));
			alcoholSoldDetails.updateData(alcoholSoldDetails.getTableHeaders().get(4),
					rstProductSummaryData.get(CNProductSummary.REQUIRED_DATA));
			alcoholSoldDetails.updateData(alcoholSoldDetails.getTableHeaders().get(5),
					rstProductSummaryData.get(CNProductSummary.REQUIRED_DATA));

			// verify report headers
			alcoholSoldDetails.verifyReportHeaders(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME));

			// verify report data
			alcoholSoldDetails.verifyReportData();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}

	}

}
