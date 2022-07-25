package at.smartshop.tests;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.Keys;
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
import at.framework.generic.Strings;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.database.columns.CNConsumerSearch;
import at.smartshop.database.columns.CNConsumerSummary;
import at.smartshop.database.columns.CNLocation;
import at.smartshop.database.columns.CNLocationSummary;
import at.smartshop.database.columns.CNNavigationMenu;
import at.smartshop.database.columns.CNOrgSummary;
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
import at.smartshop.pages.CashFlow;
import at.smartshop.pages.ConsumerFeedbackSurvey;
import at.smartshop.pages.ConsumerSearch;
import at.smartshop.pages.ConsumerSummary;
import at.smartshop.pages.CreatePromotions;
import at.smartshop.pages.CrossOrgLoyaltyReport;
import at.smartshop.pages.CrossOrgRateReport;
import at.smartshop.pages.DataSourceManager;
import at.smartshop.pages.DeviceByCategoryReport;
import at.smartshop.pages.EmployeeCompDetailsReport;
import at.smartshop.pages.FinancialRecapReport;
import at.smartshop.pages.FolioBillingReport;
import at.smartshop.pages.GuestPassByDevice;
import at.smartshop.pages.HealthAheadPercentageReport;
import at.smartshop.pages.HealthAheadReport;
import at.smartshop.pages.ICEReport;
import at.smartshop.pages.IntegrationPaymentReport;
import at.smartshop.pages.InventoryAdjustmentDetail;
import at.smartshop.pages.InventoryList;
import at.smartshop.pages.InventoryTotals;
import at.smartshop.pages.InventoryVariance;
import at.smartshop.pages.InvoiceDetailsReport;
import at.smartshop.pages.ItemStockoutReport;
import at.smartshop.pages.LocationList;
import at.smartshop.pages.LocationSummary;
import at.smartshop.pages.LoyaltyUserReport;
import at.smartshop.pages.MemberPurchaseDetailsReport;
import at.smartshop.pages.MemberPurchaseSummaryReport;
import at.smartshop.pages.MultiTaxReport;
import at.smartshop.pages.NavigationBar;
import at.smartshop.pages.OrderTransactionTimeReport;
import at.smartshop.pages.OrgSummary;
import at.smartshop.pages.PersonalChargeReport;
import at.smartshop.pages.ProductPricingReport;
import at.smartshop.pages.ProductSalesByCategoryReport;
import at.smartshop.pages.ProductTaxReport;
import at.smartshop.pages.PromotionAnalysis;
import at.smartshop.pages.PromotionList;
import at.smartshop.pages.QueuedCreditTransactionsReport;
import at.smartshop.pages.RemainingGuestPassLiability;
import at.smartshop.pages.ReportList;
import at.smartshop.pages.SalesAnalysisReport;
import at.smartshop.pages.SalesItemDetailsReport;
import at.smartshop.pages.TenderTransactionLogReport;
import at.smartshop.pages.TipDetailsReport;
import at.smartshop.pages.TipSummaryReport;
import at.smartshop.pages.TransactionCannedReport;
import at.smartshop.pages.UnfinishedCloseReport;
import at.smartshop.pages.UnsoldReport;
import at.smartshop.pages.VoidedProductReport;
import at.smartshop.utilities.CurrenyConverter;
import at.smartshop.v5.pages.AccountLogin;
import at.smartshop.v5.pages.LandingPage;
import at.smartshop.v5.pages.Order;
import at.smartshop.v5.pages.Payments;
import at.smartshop.v5.pages.ProductSearch;

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
	private Strings strings = new Strings();
	private PromotionList promotionList = new PromotionList();
	private PromotionAnalysis promotionAnalysis = new PromotionAnalysis();
	private CreatePromotions createPromotions = new CreatePromotions();
	private MultiTaxReport multiTaxReport = new MultiTaxReport();
	private CashFlow cashFlow = new CashFlow();
	private Order order = new Order();
	private SalesItemDetailsReport salesItemDetailsReport = new SalesItemDetailsReport();
	private CrossOrgRateReport crossOrgRate = new CrossOrgRateReport();
	private InventoryAdjustmentDetail inventoryAdjustmentDetail = new InventoryAdjustmentDetail();
	private InventoryTotals InventoryTotals = new InventoryTotals();
	private RemainingGuestPassLiability remainingGuestPassLiability = new RemainingGuestPassLiability();
	private GuestPassByDevice guestPassByDevice = new GuestPassByDevice();
	private InventoryList inventoryList = new InventoryList();
	private InventoryVariance inventoryVariance = new InventoryVariance();
	private ConsumerFeedbackSurvey consumerFeedbackSurvey = new ConsumerFeedbackSurvey();

	private Map<String, String> rstNavigationMenuData;
	private Map<String, String> rstConsumerSearchData;
	private Map<String, String> rstProductSummaryData;
	private Map<String, String> rstLocationSummaryData;
	private Map<String, String> rstConsumerSummaryData;
	private Map<String, String> rstReportListData;
	private Map<String, String> rstLocationData;
	private Map<String, String> rstOrgSummaryData;

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
//			consumerSearch.enterSearchFields("Email", "NaveenAutomation@gmail.com",
//					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE),
//					rstConsumerSearchData.get(CNConsumerSearch.STATUS));

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
			/*
			 * String updatedTime =
			 * String.valueOf(dateAndTime.getDateAndTimeWithOneHourAhead(
			 * rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION),
			 * rstLocationSummaryData.get(CNLocationSummary.TIME_ZONE)));
			 */

			String updatedTime = String
					.valueOf(dateAndTime.getDateAndTime(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION),
							rstLocationSummaryData.get(CNLocationSummary.TIME_ZONE)));

			foundation.threadWait(Constants.ONE_SECOND);

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
			foundation.threadWait(Constants.TWO_SECOND);
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
					rstProductSummaryData.get(CNProductSummary.ACTUAL_DATA));
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
					rstProductSummaryData.get(CNProductSummary.ACTUAL_DATA));
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

			deviceByCategory.getRequiredRecord(rstProductSummaryData.get(CNProductSummary.CATEGORY2));

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

			foundation.threadWait(Constants.ONE_SECOND);
			navigationBar.navigateToMenuItem(menu.get(0));

			// Select the Report Date range and Location
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));
			reportList.selectLocation(
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));

			// run and read report
			foundation.threadWait(Constants.ONE_SECOND);
			foundation.waitforClikableElement(ReportList.BTN_RUN_REPORT, Constants.SHORT_TIME);
			foundation.objectClick(ReportList.BTN_RUN_REPORT);
			foundation.waitforElement(ICEReport.LBL_REPORT_NAME, Constants.MEDIUM_TIME);
			iceReport.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));
			textBox.enterText(ICEReport.TXT_SEARCH, rstProductSummaryData.get(CNProductSummary.SCAN_CODE));
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
			foundation.waitforElement(ICEReport.LBL_REPORT_NAME, Constants.SHORT_TIME);
			textBox.enterText(ICEReport.TXT_SEARCH, rstProductSummaryData.get(CNProductSummary.SCAN_CODE));
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
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));
			foundation.waitforElement(LocationSummary.LNK_INVENTORY, Constants.SHORT_TIME);
			locationSummary.selectTab(rstLocationSummaryData.get(CNLocationSummary.TAB_NAME));
			foundation.threadWait(Constants.TWO_SECOND);
			textBox.enterText(LocationSummary.TXT_INVENTORY_FILTER,
					rstProductSummaryData.get(CNProductSummary.SCAN_CODE));

			locationSummary.updateInventory(rstProductSummaryData.get(CNProductSummary.SCAN_CODE), actualData.get(0),
					reason.get(0));
			textBox.enterText(LocationSummary.TXT_INVENTORY_FILTER,
					rstProductSummaryData.get(CNProductSummary.SCAN_CODE));
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
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));
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

//			// navigate to Details
//			itemStockout.navigateToProductsEvents(columnName.get(1),
//					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE),
//					rstProductSummaryData.get(CNProductSummary.SCAN_CODE));
//			itemStockout.getItemStockoutDetails();
//			foundation.threadWait(Constants.THREE_SECOND);
//			itemStockout.getIntialDetailsData().putAll(itemStockout.getReportsDetailsData());
//			itemStockout.updateDetailsData(stockout.toUpperCase(), requiredData.get(1), reason.get(1));
//
//			// verify report details headers
//			itemStockout.verifyReportHeaders(itemStockout.getItemStockoutDetailsHeaders(), columnName.get(2));
//
//			// verify report details data
//			itemStockout.verifyReportDetailsData(stockout.toUpperCase(), reason.get(1));
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
			List<String> dateRange = Arrays
					.asList(rstReportListData.get(CNReportList.DATE_RANGE).split(Constants.DELIMITER_TILD));

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
			textBox.enterText(productSalesCategory.SEARCH_RESULT,
					rstProductSummaryData.get(CNProductSummary.CATEGORY2));

			productSalesCategory.getTblRecordsUI();
			productSalesCategory.getIntialData().putAll(productSalesCategory.getReportsData());

			// Process API and read updated data
			productSalesCategory.processAPI(rstProductSummaryData.get(CNProductSummary.SCAN_CODE),
					rstProductSummaryData.get(CNProductSummary.CATEGORY2));
			foundation.click(ReportList.BTN_RUN_REPORT);
			textBox.enterText(productSalesCategory.SEARCH_RESULT,
					rstProductSummaryData.get(CNProductSummary.CATEGORY2));

			productSalesCategory.getTblRecordsUI();
			productSalesCategory.getRequiredRecord(rstProductSummaryData.get(CNProductSummary.CATEGORY2));

			// apply calculation and update data
			productSalesCategory.updatePrice();
			productSalesCategory.updateSalesAmount();
			productSalesCategory.updateTax();
			productSalesCategory.updateCount(productSalesCategory.getTableHeaders().get(6));
			productSalesCategory.updateCount(productSalesCategory.getTableHeaders().get(7));

			// verify report headers
//			productSalesCategory.verifyReportHeaders(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME));

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

			String deviceId = rstProductSummaryData.get(CNProductSummary.DEVICE_ID);

			// process sales API to generate data
			integrationPayments.processAPI(Reports.GENESIS + Constants.DELIMITER_TILD + Reports.SPECIAL, deviceId);

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
					Reports.GENESIS + Constants.DELIMITER_TILD + Reports.SPECIAL);
			integrationPayments.processAPI(Reports.GENESIS + Constants.DELIMITER_TILD + Reports.SPECIAL, deviceId);
			foundation.threadWait(Constants.ONE_SECOND);
			foundation.waitforClikableElement(ReportList.BTN_RUN_REPORT, Constants.SHORT_TIME);
			foundation.click(ReportList.BTN_RUN_REPORT);
			integrationPayments.getTblRecordsUI();

			// apply calculation and update data
			integrationPayments.updateData(integrationPayments.getTableHeaders().get(0),
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));
			integrationPayments.updateData(integrationPayments.getTableHeaders().get(1), deviceId.toUpperCase());
			integrationPayments.updateValue(integrationPayments.getTableHeaders().get(2),
					Reports.GENESIS + Constants.DELIMITER_TILD + Reports.SPECIAL);
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
			foundation.threadWait(Constants.ONE_SECOND);
			foundation.waitforClikableElement(ReportList.BTN_RUN_REPORT, Constants.SHORT_TIME);
			foundation.click(ReportList.BTN_RUN_REPORT);
			orderTransactionTime.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));
			textBox.enterText(orderTransactionTime.SEARCH_RESULT,
					((String) orderTransactionTime.getJsonData().get(Reports.TRANS_DATE_TIME)).toUpperCase());
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
			Assert.assertTrue(Factory.getDriver().findElement(aviSubFee.LBL_REPORT_NAME).isDisplayed());

			textBox.enterText(aviSubFee.SEARCH_RESULT, deviceId);
			aviSubFee.getTblRecordsUI();
			aviSubFee.getIntialData().putAll(aviSubFee.getReportsData());

			// process sales API to generate data
			aviSubFee.processAPI(deviceId);
			foundation.waitforClikableElement(ReportList.BTN_RUN_REPORT, Constants.SHORT_TIME);
			foundation.click(ReportList.BTN_RUN_REPORT);

			textBox.enterText(aviSubFee.SEARCH_RESULT, deviceId);
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
			textBox.enterText(personalCharge.SEARCH_RESULT,
					(String) personalCharge.getJsonData().get(Reports.TRANS_ID));

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
			loyaltyUser.getRequiredRecord(
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));
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
			textBox.enterText(crossOrgLoyalty.SEARCH_RESULT,
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));

			crossOrgLoyalty.getTblRecordsUI();
			crossOrgLoyalty.getIntialData().putAll(crossOrgLoyalty.getReportsData());
			crossOrgLoyalty.getRequiredRecord(
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));
			crossOrgLoyalty.processAPI();
			foundation.threadWait(Constants.MEDIUM_TIME);
			foundation.click(ReportList.BTN_RUN_REPORT);
			textBox.enterText(crossOrgLoyalty.SEARCH_RESULT,
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));

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

	@Test(description = "167829-Verify the UnRedeemed Promtions for Tender Discount Promotion with Tender type as Cash of Promtion Analysis")
	public void PromtionAnalysisReportDataValidtionOfUnredeemedPromtionswithTenderDiscount() {
		final String CASE_NUM = "167829";

		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);
		rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);
		rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);
		rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);

		String promotionType = rstLocationData.get(CNLocation.PROMOTION_TYPE);
		String orgName = propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE);
		String locationName = propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE);
		String gridName = rstLocationData.get(CNLocation.TAB_NAME);
		String promotionName = strings.getRandomCharacter();
		String statusType = rstLocationData.get(CNLocation.LOCATION_NAME);

		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			List<String> menuItems = Arrays
					.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
			navigationBar.navigateToMenuItem(menuItems.get(0));

			// creation of promotion
			foundation.click(PromotionList.BTN_CREATE);
			foundation.isDisplayed(CreatePromotions.LBL_CREATE_PROMOTION);
			dropdown.selectItem(CreatePromotions.DPD_PROMO_TYPE, promotionType, Constants.TEXT);
			foundation.threadWait(Constants.TWO_SECOND);
			textBox.enterText(CreatePromotions.TXT_PROMO_NAME, promotionName);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.threadWait(Constants.TWO_SECOND);

			dropdown.selectItem(CreatePromotions.DPD_ORG, orgName, Constants.TEXT);
			foundation.click(CreatePromotions.BTN_ORG_RIGHT);
			foundation.threadWait(Constants.TWO_SECOND);
			dropdown.selectItem(CreatePromotions.DPD_LOCATION, locationName, Constants.TEXT);
			foundation.click(CreatePromotions.BTN_LOC_RIGHT);

			foundation.threadWait(Constants.TWO_SECOND);
			foundation.click(CreatePromotions.BTN_NEXT);
			List<String> requiredData = Arrays
					.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));

			dropdown.selectItem(CreatePromotions.MULTI_SELECT_TENDER_TYPES, requiredData.get(0), Constants.TEXT);
			dropdown.selectItem(CreatePromotions.DPD_DISCOUNT_TYPE, requiredData.get(1), Constants.TEXT);
			dropdown.selectItem(CreatePromotions.DPD_APPLY_DISCOUNT_TO, requiredData.get(2), Constants.TEXT);
			textBox.enterText(CreatePromotions.TXT_AMOUNT, requiredData.get(3));
			textBox.enterText(CreatePromotions.TXT_TRANSACTION_MIN, requiredData.get(4));
			dropdown.selectItem(CreatePromotions.DPD_DISCOUNT_TIME, requiredData.get(5), Constants.TEXT);
			foundation.click(CreatePromotions.CHK_SUNDAY);

			foundation.threadWait(Constants.TWO_SECOND);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_OK);
			foundation.threadWait(Constants.TWO_SECOND);

			// Navigate to Reports
			navigationBar.navigateToMenuItem(menuItems.get(1));

			foundation.threadWait(Constants.TWO_SECOND);
			// Selecting the Date range and Location for running report
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));

			reportList.selectGroupByOption(rstReportListData.get(CNReportList.GROUPBY_DROPDOWN), Constants.TEXT);

			reportList.selectAllOptionOfFilter();

			List<String> selectValueForSelectedFilterType = Arrays.asList(rstReportListData
					.get(CNReportList.SELECT_VALUE_FOR_SELECTED_FILTER_TYPE).split(Constants.DELIMITER_HASH));

			reportList.selectOrgOnFilter(selectValueForSelectedFilterType.get(1));
			foundation.threadWait(Constants.TWO_SECOND);
			reportList.selectLocationOnFilter(selectValueForSelectedFilterType.get(0));
			foundation.objectClick(ReportList.BTN_RUN_REPORT);
			foundation.waitforElement(promotionAnalysis.TBL_PROMOTIONAL_ANALYSIS_GROUPBY_PROMOTIONS,
					Constants.EXTRA_LONG_TIME);

			// Report data validation basesd on Groupby Promotions
			// reading the report data
			promotionAnalysis.getUITblRecordsGroupbyPromotions();
			promotionAnalysis.getIntialData().putAll(promotionAnalysis.getReportsData());
			promotionAnalysis.getRequiredRecord(promotionName);

			// Actual data
			promotionAnalysis.promotionActualData();

			String date = String
					.valueOf(dateAndTime.getDateAndTime(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION),
							rstLocationSummaryData.get(CNLocationSummary.TIME_ZONE)));
			List<String> expectedData = Arrays
					.asList(rstProductSummaryData.get(CNProductSummary.REQUIRED_DATA).split(Constants.DELIMITER_HASH));

			promotionAnalysis.updateData(promotionAnalysis.getTableHeaders().get(1), promotionName);
			promotionAnalysis.updateData(promotionAnalysis.getTableHeaders().get(3), expectedData.get(0));
			promotionAnalysis.updateData(promotionAnalysis.getTableHeaders().get(4), expectedData.get(1));
			promotionAnalysis.updateData(promotionAnalysis.getTableHeaders().get(5), expectedData.get(2));
			promotionAnalysis.updateData(promotionAnalysis.getTableHeaders().get(9), expectedData.get(3));
			promotionAnalysis.updateData(promotionAnalysis.getTableHeaders().get(10), date);
			promotionAnalysis.updateData(promotionAnalysis.getTableHeaders().get(11), date);
			promotionAnalysis.updateData(promotionAnalysis.getTableHeaders().get(12), expectedData.get(4));
			promotionAnalysis.updateData(promotionAnalysis.getTableHeaders().get(13), expectedData.get(5));

			// Expected data
			promotionAnalysis.PromotionExpectedData();

			// verify report headers
			reportList.verifyReportHeaders(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME),
					promotionAnalysis.getTableHeaders());

			// verify report data
			promotionAnalysis.verifyReportData();

			// Report data validation based on Groupby Locations
			// reading the report data
			foundation.threadWait(Constants.ONE_SECOND);
			dropdown.selectItemByIndex(promotionAnalysis.REPORT_GROUPBY_DPD, 1);
			foundation.threadWait(Constants.TWO_SECOND);
			foundation.waitforElement(promotionAnalysis.TBL_PROMOTIONAL_ANALYSIS_GROUPBY_LOCATIONS,
					Constants.EXTRA_LONG_TIME);
			promotionAnalysis.getRequiredRecordGroupbyLocations(
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));
			promotionAnalysis.expandRow();
			promotionAnalysis.getUITblRecordsGroupbyLocations();
			promotionAnalysis.getIntialData().putAll(promotionAnalysis.getReportsData());
			promotionAnalysis.getRequiredRecord(promotionName);

			// Actual data
			promotionAnalysis.promotionActualData();

			foundation.threadWait(Constants.TWO_SECOND);
			// verify report headers
			reportList.verifyReportHeaders(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME),
					promotionAnalysis.getTableHeaders());

			// verify report data
			promotionAnalysis.verifyReportData();

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// Resetting the data
			List<String> menuItems = Arrays
					.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
			promotionList.expirePromotion(menuItems.get(0), promotionName, statusType, gridName);
		}
	}

	@Test(description = "167219-This test validates Cash Flow Report Data Calculation")
	public void cashFlowReportData() {
		try {

			final String CASE_NUM = "167219";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			// process sales API to generate data
			cashFlow.processAPI(rstProductSummaryData.get(CNProductSummary.ACTUAL_DATA));

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
			cashFlow.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));
			cashFlow.getTblRecordsUI();
			cashFlow.getIntialData().putAll(cashFlow.getReportsData());

			// process sales API to generate data
			cashFlow.processAPI(rstProductSummaryData.get(CNProductSummary.ACTUAL_DATA));

			// rerun and reread report
			foundation.threadWait(Constants.MEDIUM_TIME);
			foundation.click(ReportList.BTN_RUN_REPORT);
			cashFlow.getTblRecordsUI();

			// apply calculation and update data
			cashFlow.updateData(cashFlow.getTableHeaders().get(0),
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));
			cashFlow.calculateAmount(cashFlow.getTableHeaders().get(1), cashFlow.getRequiredJsonData().get(0));
			cashFlow.calculateAmount(cashFlow.getTableHeaders().get(3), cashFlow.getRequiredJsonData().get(0));
			cashFlow.calculateAmount(cashFlow.getTableHeaders().get(4), cashFlow.getRequiredJsonData().get(0));
			cashFlow.calculateAmount(cashFlow.getTableHeaders().get(5), cashFlow.getRequiredJsonData().get(0));
			cashFlow.calculateAmount(cashFlow.getTableHeaders().get(6), cashFlow.getRequiredJsonData().get(0));
			cashFlow.calculateAmount(cashFlow.getTableHeaders().get(9), cashFlow.getRequiredJsonData().get(0));
			cashFlow.calculateAmount(cashFlow.getTableHeaders().get(11), cashFlow.getRequiredJsonData().get(0));

			// verify report headers
//			reportList.verifyReportHeaders(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME),cashFlow.getTableHeaders());

			// verify report data
//			reportList.verifyReportData(cashFlow.tableHeaders, cashFlow.getReportsData(), cashFlow.getIntialData());

			cashFlow.verifyReportHeaders(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME));

			cashFlow.verifyReportData();

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "167474-This test validates Multi Tax Report Data Calculation")
	public void multiTaxReportData() {
		try {

			final String CASE_NUM = "167474";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			// process sales API to generate data
			multiTaxReport.processAPI();

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
			multiTaxReport.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));
			multiTaxReport.getTblRecordsUI();
			multiTaxReport.getIntialData().putAll(multiTaxReport.getReportsData());

			// process sales API to generate data
			multiTaxReport.processAPI();

			// rerun and reread report
			foundation.threadWait(Constants.MEDIUM_TIME);
			foundation.click(ReportList.BTN_RUN_REPORT);
			multiTaxReport.getTblRecordsUI();

			// apply calculation and update data
			multiTaxReport.updateData(multiTaxReport.getTableHeaders().get(1),
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));
			multiTaxReport.updateTax(multiTaxReport.getTableHeaders().get(2),
					multiTaxReport.getRequiredJsonData().get(0));

			// verify report headers
			reportList.verifyReportHeaders(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME),
					multiTaxReport.getTableHeaders());

			// verify report data
			reportList.verifyReportDataOfFirstRow(multiTaxReport.tableHeaders, multiTaxReport.getReportsData(),
					multiTaxReport.getIntialData());

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "168454-Verify the Data Validation of Sales Analysis Report")
	public void salesAnalysisReportDataValication() {
		try {
			final String CASE_NUM = "168454";
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// process sales API to generate data
			salesAnalysisReport.processAPI(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			List<String> groupBy = Arrays
					.asList(rstReportListData.get(CNReportList.GROUPBY_DROPDOWN).split(Constants.DELIMITER_HASH));

			// Data validation for Report Based on Groupby ITEMS
			// Select the Report Date range and Location and run report
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));
			reportList.selectLocation(
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));
			foundation.threadWait(Constants.SHORT_TIME);
			reportList.selectGroupByOption(groupBy.get(0), Constants.TEXT);
			foundation.click(ReportList.BTN_RUN_REPORT);
			foundation.waitforElement(SalesAnalysisReport.LBL_REPORT_NAME, Constants.SHORT_TIME);
			salesAnalysisReport.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));

			// Read the Report the Data
			salesAnalysisReport.getTblRecordsUI();
			salesAnalysisReport.getIntialData().putAll(salesAnalysisReport.getReportsData());

			// process sales API to generate data
			salesAnalysisReport.processAPI(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));

			// rerun and reread report
			foundation.click(ReportList.BTN_RUN_REPORT);
			foundation.threadWait(Constants.TWO_SECOND);
			salesAnalysisReport.getTblRecordsUI();

			// update the report date baseed on calculation
			List<String> expectedData = Arrays
					.asList(rstProductSummaryData.get(CNProductSummary.REQUIRED_DATA).split(Constants.DELIMITER_HASH));
			String productPrice = rstProductSummaryData.get(CNProductSummary.PRICE);

			salesAnalysisReport.saleCount(salesAnalysisReport.getTableHeaders().get(7));
			salesAnalysisReport.calculateAmount(salesAnalysisReport.getTableHeaders().get(8), expectedData.get(0));
			salesAnalysisReport.calculateAmount(salesAnalysisReport.getTableHeaders().get(9), expectedData.get(1));
			salesAnalysisReport.calculateAmount(salesAnalysisReport.getTableHeaders().get(12), expectedData.get(2));
			salesAnalysisReport.calculateAmount(salesAnalysisReport.getTableHeaders().get(13), expectedData.get(3));
			salesAnalysisReport.calculateAmount(salesAnalysisReport.getTableHeaders().get(14), expectedData.get(4));
			salesAnalysisReport.getGMValueUsingCalculationForAllProducts(salesAnalysisReport.getTableHeaders().get(15),
					productPrice);

			// verify report headers
			reportList.verifyReportHeaders(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME),
					salesAnalysisReport.getTableHeaders());

//			salesAnalysisReport.verifyReportHeaders(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME));

			// verify report data
			salesAnalysisReport.verifyReportData();

			// Data validation for Report Based on Groupby LOCATION
			// Run the Report and read the data based on Loaction
			reportList.selectGroupByOption(groupBy.get(1), Constants.TEXT);
			foundation.threadWait(Constants.TWO_SECOND);
			foundation.click(ReportList.BTN_RUN_REPORT);
			salesAnalysisReport.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));
			foundation.click(SalesAnalysisReport.TBL_EXPAND_ROW);
			foundation.threadWait(Constants.TWO_SECOND);
			salesAnalysisReport.getUITblRecordsGroupbyLocations();

			salesAnalysisReport.removeReportDataFirstValue();

			// verify report headers
			salesAnalysisReport.verifyReportHeadersForLocation(
					salesAnalysisReport.removeHeaderFirstValue(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME)),
					salesAnalysisReport.getTableHeaders());

			// verify report data
			salesAnalysisReport.verifyReportData();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "168025-Verify the All Redeemed Promtions for Tender type of Promtion Analysis")
	public void PromtionAnalysisAllRedeemed() {
		final String CASE_NUM = "168025";

		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);
		rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);
		rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);
		rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);

		String promotionType = rstLocationData.get(CNLocation.PROMOTION_TYPE);
		String orgName = propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE);
		String locationName = propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1,
				FilePath.PROPERTY_CONFIG_FILE);
		String gridName = rstLocationData.get(CNLocation.TAB_NAME);
		String weekDays = rstLocationData.get(CNLocation.TYPE);
		String promotionName = strings.getRandomCharacter();

		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			List<String> menuItems = Arrays
					.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
			navigationBar.navigateToMenuItem(menuItems.get(0));

			// creation of promotion
			foundation.click(PromotionList.BTN_CREATE);
			foundation.isDisplayed(CreatePromotions.LBL_CREATE_PROMOTION);
			dropdown.selectItem(CreatePromotions.DPD_PROMO_TYPE, promotionType, Constants.TEXT);
			foundation.threadWait(Constants.TWO_SECOND);
			textBox.enterText(CreatePromotions.TXT_PROMO_NAME, promotionName);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.threadWait(Constants.TWO_SECOND);

			dropdown.selectItem(CreatePromotions.DPD_ORG, orgName, Constants.TEXT);
			foundation.click(CreatePromotions.BTN_ORG_RIGHT);
			foundation.threadWait(Constants.TWO_SECOND);
			dropdown.selectItem(CreatePromotions.DPD_LOCATION, locationName, Constants.TEXT);
			foundation.click(CreatePromotions.BTN_LOC_RIGHT);

			foundation.threadWait(Constants.TWO_SECOND);
			foundation.click(CreatePromotions.BTN_NEXT);
			List<String> requiredData = Arrays
					.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));

			dropdown.selectAllItems(CreatePromotions.MULTI_SELECT_TENDER_TYPES);

			dropdown.selectItem(CreatePromotions.DPD_DISCOUNT_TYPE, requiredData.get(0), Constants.TEXT);
			dropdown.selectItem(CreatePromotions.DPD_APPLY_DISCOUNT_TO, requiredData.get(1), Constants.TEXT);
			textBox.enterText(CreatePromotions.TXT_AMOUNT, requiredData.get(2));
			textBox.enterText(CreatePromotions.TXT_TRANSACTION_MIN, requiredData.get(3));
			dropdown.selectItem(CreatePromotions.DPD_DISCOUNT_TIME, requiredData.get(4), Constants.TEXT);
			createPromotions.selectWeekDays(weekDays);

			foundation.threadWait(Constants.TWO_SECOND);
			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(CreatePromotions.BTN_OK);
			foundation.threadWait(Constants.SHORT_TIME);

			// Navigate to Reports
			navigationBar.navigateToMenuItem(menuItems.get(1));

			// Selecting the Date range and Location for running report
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));

			reportList.selectGroupByOption(rstReportListData.get(CNReportList.GROUPBY_DROPDOWN), Constants.TEXT);

			reportList.selectAllOptionOfFilter();

			List<String> selectValueForSelectedFilterType = Arrays.asList(rstReportListData
					.get(CNReportList.SELECT_VALUE_FOR_SELECTED_FILTER_TYPE).split(Constants.DELIMITER_HASH));

			reportList.selectOrgOnFilter(selectValueForSelectedFilterType.get(1));
			foundation.threadWait(Constants.TWO_SECOND);

			reportList.selectLocationOnFilter(selectValueForSelectedFilterType.get(0));
			foundation.objectClick(ReportList.BTN_RUN_REPORT);
			foundation.waitforElement(promotionAnalysis.TBL_PROMOTIONAL_ANALYSIS_GROUPBY_PROMOTIONS,
					Constants.EXTRA_LONG_TIME);

			// Report data validation basesd on Groupby Promotions
			// reading the report data
			promotionAnalysis.getUITblRecordsGroupbyPromotions();
			promotionAnalysis.getIntialData().putAll(promotionAnalysis.getReportsData());
			promotionAnalysis.getRequiredRecord(promotionName);

			promotionAnalysis.PromotionExpectedData();

			String date = String
					.valueOf(dateAndTime.getDateAndTime(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION),
							rstLocationSummaryData.get(CNLocationSummary.TIME_ZONE)));
			List<String> expectedData = Arrays
					.asList(rstProductSummaryData.get(CNProductSummary.REQUIRED_DATA).split(Constants.DELIMITER_HASH));

			promotionAnalysis.updateData(promotionAnalysis.getTableHeaders().get(1), promotionName);
			promotionAnalysis.updateData(promotionAnalysis.getTableHeaders().get(3), expectedData.get(0));
			promotionAnalysis.updateData(promotionAnalysis.getTableHeaders().get(4), expectedData.get(1));
			promotionAnalysis.updateData(promotionAnalysis.getTableHeaders().get(5), expectedData.get(2));
			promotionAnalysis.updateData(promotionAnalysis.getTableHeaders().get(9), expectedData.get(3));
			promotionAnalysis.updateData(promotionAnalysis.getTableHeaders().get(10), date);
			promotionAnalysis.updateData(promotionAnalysis.getTableHeaders().get(11), date);
			promotionAnalysis.updateData(promotionAnalysis.getTableHeaders().get(12), expectedData.get(4));
			promotionAnalysis.updateData(promotionAnalysis.getTableHeaders().get(13), expectedData.get(5));
			promotionAnalysis.updateDiscount(expectedData.get(7));
			promotionAnalysis.updateRedemptionsCount(expectedData.get(6));
			login.logout();
			browser.close();
			String product = rstProductSummaryData.get(CNProductSummary.PRODUCT_NAME);
			List<String> paymentEmailDetails = Arrays
					.asList(rstProductSummaryData.get(CNProductSummary.USER_KEY).split(Constants.DELIMITER_HASH));

			// Launch V5 Device and Searching for product
			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LandingPage.IMG_SEARCH_ICON));
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadTextWithCaseSensitive(product);
			foundation.click(ProductSearch.BTN_PRODUCT);
			CustomisedAssert.assertTrue(foundation.isDisplayed(Order.BTN_CANCEL_ORDER));

			foundation.click(Payments.ACCOUNT_EMAIL);
			foundation.waitforElement(Payments.EMAIL_lOGIN_BTN, Constants.ONE_SECOND);
			foundation.click(Payments.EMAIL_lOGIN_BTN);
			foundation.threadWait(Constants.ONE_SECOND);
			textBox.enterKeypadTextWithCaseSensitive(paymentEmailDetails.get(0));
			foundation.click(AccountLogin.BTN_NEXT);
			foundation.waitforElement(AccountLogin.BTN_PIN_NEXT, Constants.SHORT_TIME);
			foundation.threadWait(Constants.ONE_SECOND);
			textBox.enterPin(paymentEmailDetails.get(1));
			foundation.click(AccountLogin.BTN_PIN_NEXT);
			CustomisedAssert.assertTrue(
					foundation.isDisplayed(order.objText(rstProductSummaryData.get(CNProductSummary.DESCRIPTION))));
			browser.close();

			// Navigate to Reports
			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Navigate to Reports
			navigationBar.navigateToMenuItem(menuItems.get(1));

			// Selecting the Date range and Location for running report
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));

			reportList.selectGroupByOption(rstReportListData.get(CNReportList.GROUPBY_DROPDOWN), Constants.TEXT);

			reportList.selectAllOptionOfFilter();

			reportList.selectOrgOnFilter(selectValueForSelectedFilterType.get(1));
			foundation.threadWait(Constants.TWO_SECOND);

			foundation.objectClick(ReportList.BTN_RUN_REPORT);
			foundation.waitforElement(promotionAnalysis.TBL_PROMOTIONAL_ANALYSIS_GROUPBY_PROMOTIONS,
					Constants.EXTRA_LONG_TIME);

			// Report data validation basesd on Groupby Promotions
			// reading the report data
			promotionAnalysis.getUITblRecordsGroupbyPromotions();
			promotionAnalysis.getIntialData().putAll(promotionAnalysis.getReportsData());
			promotionAnalysis.getRequiredRecord(promotionName);

			// Actual data
			promotionAnalysis.promotionActualData();

			// verify report headers
			reportList.verifyReportHeaders(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME),
					promotionAnalysis.getTableHeaders());

			// verify report data
			promotionAnalysis.verifyReportData();

			foundation.threadWait(Constants.SHORT_TIME);
			foundation.objectClick(ReportList.BTN_RUN_REPORT);
			foundation.waitforElement(promotionAnalysis.TBL_PROMOTIONAL_ANALYSIS_GROUPBY_PROMOTIONS,
					Constants.EXTRA_LONG_TIME);

			dropdown.selectItemByIndex(promotionAnalysis.REPORT_GROUPBY_DPD, 1);
			foundation.threadWait(Constants.TWO_SECOND);
			foundation.waitforElement(promotionAnalysis.TBL_PROMOTIONAL_ANALYSIS_GROUPBY_LOCATIONS,
					Constants.EXTRA_LONG_TIME);
			promotionAnalysis.getRequiredRecordGroupbyLocations(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));
			promotionAnalysis.expandRow();
			promotionAnalysis.getUITblRecordsGroupbyLocations();
			promotionAnalysis.getIntialData().putAll(promotionAnalysis.getReportsData());
			promotionAnalysis.getRequiredRecord(promotionName);
			foundation.threadWait(Constants.ONE_SECOND);
			promotionAnalysis.promotionActualData();

			// verify report headers
			reportList.verifyReportHeaders(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME),
					promotionAnalysis.getTableHeaders());

			// verify report data
			promotionAnalysis.verifyReportData();

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// Resetting the data
			List<String> menuItems = Arrays
					.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
			List<String> expectedData = Arrays
					.asList(rstProductSummaryData.get(CNProductSummary.REQUIRED_DATA).split(Constants.DELIMITER_HASH));
			promotionList.expirePromotion(menuItems.get(0), promotionName, expectedData.get(5), gridName);
		}
	}

	@Test(description = "168026-Verify the All Redeemed Promtions for On-Screen type of Promtion Analysis")
	public void PromtionAnalysisForRedeemedPromotions() {
		final String CASE_NUM = "168026";

		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);
		rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);
		rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);
		rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);

		// String weekDays = rstLocationData.get(CNLocation.TYPE);

		String promotionName = strings.getRandomCharacter();
		String displayName = strings.getRandomCharacter();
		String promotionType = rstLocationData.get(CNLocation.PROMOTION_TYPE);
		String orgName = propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE);
		String locationName = rstLocationData.get(CNLocation.LOCATION_NAME);
		String gridName = rstLocationData.get(CNLocation.TAB_NAME);

		List<String> requiredData = Arrays
				.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			List<String> menuItems = Arrays
					.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));

			navigationBar.navigateToMenuItem(menuItems.get(0));

			// New Promotion
			foundation.click(PromotionList.BTN_CREATE);
			foundation.isDisplayed(CreatePromotions.LBL_CREATE_PROMOTION);

			createPromotions.newPromotion(promotionType, promotionName, displayName, orgName, locationName);

			foundation.waitforElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);

			foundation.click(CreatePromotions.BTN_NEXT);
			dropdown.selectItem(CreatePromotions.DPD_DISCOUNT_BY, requiredData.get(1), Constants.TEXT);
			textBox.enterText(CreatePromotions.TXT_ITEM, requiredData.get(2));
			foundation.threadWait(Constants.ONE_SECOND);
			textBox.enterText(CreatePromotions.TXT_ITEM, Keys.ENTER);
//			foundation.click(CreatePromotions.ALL_ITEMS);

			foundation.threadWait(Constants.TWO_SECOND);
			String actualValue = dropdown.getSelectedItem(CreatePromotions.DPD_ITEM_SELECT);
			CustomisedAssert.assertEquals(actualValue, requiredData.get(2));

			textBox.enterText(CreatePromotions.TXT_AMOUNT, requiredData.get(4));
			textBox.enterText(CreatePromotions.TXT_TRANSACTION_MIN, requiredData.get(5));
			foundation.objectClick(CreatePromotions.BTN_NEXT);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);
//			foundation.threadWait(Constants.MEDIUM_TIME);
			foundation.objectClick(CreatePromotions.BTN_OK);
			login.logout();
			browser.close();
			String product = rstProductSummaryData.get(CNProductSummary.PRODUCT_NAME);
			List<String> paymentEmailDetails = Arrays
					.asList(rstProductSummaryData.get(CNProductSummary.USER_KEY).split(Constants.DELIMITER_HASH));

			// Launch V5 Device and Searching for product
			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LandingPage.IMG_SEARCH_ICON));
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadTextWithCaseSensitive(product);
			foundation.click(ProductSearch.BTN_PRODUCT);
			CustomisedAssert.assertTrue(foundation.isDisplayed(Order.BTN_CANCEL_ORDER));
			foundation.click(Payments.ACCOUNT_EMAIL);
			foundation.waitforElement(Payments.EMAIL_lOGIN_BTN, Constants.ONE_SECOND);
			foundation.click(Payments.EMAIL_lOGIN_BTN);
			foundation.threadWait(Constants.ONE_SECOND);
			textBox.enterKeypadTextWithCaseSensitive(paymentEmailDetails.get(0));
			foundation.click(AccountLogin.BTN_NEXT);
			foundation.waitforElement(AccountLogin.BTN_PIN_NEXT, Constants.SHORT_TIME);
			foundation.threadWait(Constants.ONE_SECOND);
			textBox.enterPin(paymentEmailDetails.get(1));
			foundation.click(AccountLogin.BTN_PIN_NEXT);
			CustomisedAssert.assertTrue(
					foundation.isDisplayed(order.objText(rstProductSummaryData.get(CNProductSummary.DESCRIPTION))));
			browser.close();

			// Navigate to Reports
			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.navigateToMenuItem(menuItems.get(1));

			// Selecting the Date range and Location for running report
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));

			reportList.selectGroupByOption(rstReportListData.get(CNReportList.GROUPBY_DROPDOWN), Constants.TEXT);

			reportList.selectAllOptionOfFilter();

			List<String> selectValueForSelectedFilterType = Arrays.asList(rstReportListData
					.get(CNReportList.SELECT_VALUE_FOR_SELECTED_FILTER_TYPE).split(Constants.DELIMITER_HASH));

			reportList.selectOrgOnFilter(selectValueForSelectedFilterType.get(1));
			foundation.threadWait(Constants.TWO_SECOND);

			reportList.selectLocationOnFilter(selectValueForSelectedFilterType.get(0));
			foundation.objectClick(ReportList.BTN_RUN_REPORT);
			foundation.waitforElement(promotionAnalysis.TBL_PROMOTIONAL_ANALYSIS_GROUPBY_PROMOTIONS,
					Constants.EXTRA_LONG_TIME);

			// Report data validation basesd on Groupby Promotions
			// reading the report data
			promotionAnalysis.getUITblRecordsGroupbyPromotions();
			promotionAnalysis.getIntialData().putAll(promotionAnalysis.getReportsData());
			promotionAnalysis.getRequiredRecord(promotionName);

			promotionAnalysis.PromotionExpectedData();

			String date = String
					.valueOf(dateAndTime.getDateAndTime(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION),
							rstLocationSummaryData.get(CNLocationSummary.TIME_ZONE)));
			List<String> expectedData = Arrays
					.asList(rstProductSummaryData.get(CNProductSummary.REQUIRED_DATA).split(Constants.DELIMITER_HASH));

			promotionAnalysis.updateData(promotionAnalysis.getTableHeaders().get(1), promotionName);
			promotionAnalysis.updateData(promotionAnalysis.getTableHeaders().get(2), displayName);
			promotionAnalysis.updateData(promotionAnalysis.getTableHeaders().get(3), expectedData.get(0));
			promotionAnalysis.updateData(promotionAnalysis.getTableHeaders().get(4), expectedData.get(1));
			promotionAnalysis.updateData(promotionAnalysis.getTableHeaders().get(5), expectedData.get(2));
			promotionAnalysis.updateData(promotionAnalysis.getTableHeaders().get(9), expectedData.get(3));
			promotionAnalysis.updateData(promotionAnalysis.getTableHeaders().get(10), date);
			promotionAnalysis.updateData(promotionAnalysis.getTableHeaders().get(11), date);
//			promotionAnalysis.updateData(promotionAnalysis.getTableHeaders().get(12), expectedData.get(4));
			promotionAnalysis.updateData(promotionAnalysis.getTableHeaders().get(13), expectedData.get(4));
			promotionAnalysis.updateDiscount(expectedData.get(6));
			promotionAnalysis.updateRedemptionsCount(expectedData.get(5));
			login.logout();
			browser.close();

			// Launch V5 Device and Searching for product
//			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LandingPage.IMG_SEARCH_ICON));
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadTextWithCaseSensitive(product);
			foundation.click(ProductSearch.BTN_PRODUCT);
			CustomisedAssert.assertTrue(foundation.isDisplayed(Order.BTN_CANCEL_ORDER));
			foundation.click(Payments.ACCOUNT_EMAIL);
			foundation.waitforElement(Payments.EMAIL_lOGIN_BTN, Constants.ONE_SECOND);
			foundation.click(Payments.EMAIL_lOGIN_BTN);
			foundation.threadWait(Constants.ONE_SECOND);
			textBox.enterKeypadTextWithCaseSensitive(paymentEmailDetails.get(0));
			foundation.click(AccountLogin.BTN_NEXT);
			foundation.waitforElement(AccountLogin.BTN_PIN_NEXT, Constants.SHORT_TIME);
			foundation.threadWait(Constants.ONE_SECOND);
			textBox.enterPin(paymentEmailDetails.get(1));
			foundation.click(AccountLogin.BTN_PIN_NEXT);
			CustomisedAssert.assertTrue(
					foundation.isDisplayed(order.objText(rstProductSummaryData.get(CNProductSummary.DESCRIPTION))));
			browser.close();

			// Navigate to Reports
			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Navigate to Reports
			navigationBar.navigateToMenuItem(menuItems.get(1));

			// Selecting the Date range and Location for running report
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));
			reportList.selectGroupByOption(rstReportListData.get(CNReportList.GROUPBY_DROPDOWN), Constants.TEXT);

			reportList.selectAllOptionOfFilter();

			reportList.selectOrgOnFilter(selectValueForSelectedFilterType.get(1));
			foundation.threadWait(Constants.TWO_SECOND);
			foundation.objectClick(ReportList.BTN_RUN_REPORT);
			foundation.waitforElement(promotionAnalysis.TBL_PROMOTIONAL_ANALYSIS_GROUPBY_PROMOTIONS,
					Constants.EXTRA_LONG_TIME);

			promotionAnalysis.getUITblRecordsGroupbyPromotions();
			promotionAnalysis.getIntialData().putAll(promotionAnalysis.getReportsData());
			promotionAnalysis.getRequiredRecord(promotionName);

			// Actual data
			promotionAnalysis.promotionActualData();

			// verify report headers
			reportList.verifyReportHeaders(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME),
					promotionAnalysis.getTableHeaders());

			// verify report data
			promotionAnalysis.verifyReportData();

			dropdown.selectItemByIndex(promotionAnalysis.REPORT_GROUPBY_DPD, 1);
			foundation.threadWait(Constants.TWO_SECOND);
			foundation.waitforElement(promotionAnalysis.TBL_PROMOTIONAL_ANALYSIS_GROUPBY_LOCATIONS,
					Constants.EXTRA_LONG_TIME);
			promotionAnalysis.getRequiredRecordGroupbyLocations(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));
			promotionAnalysis.expandRow();
			promotionAnalysis.getUITblRecordsGroupbyLocations();
			promotionAnalysis.getIntialData().putAll(promotionAnalysis.getReportsData());
			promotionAnalysis.getRequiredRecord(promotionName);
			foundation.threadWait(Constants.ONE_SECOND);
			promotionAnalysis.promotionActualData();

			// verify report headers
			reportList.verifyReportHeaders(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME),
					promotionAnalysis.getTableHeaders());

			// verify report data
			promotionAnalysis.verifyReportData();

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// Resetting the data
			List<String> menuItems = Arrays
					.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
			List<String> expectedData = Arrays
					.asList(rstProductSummaryData.get(CNProductSummary.REQUIRED_DATA).split(Constants.DELIMITER_HASH));
			promotionList.expirePromotion(menuItems.get(0), promotionName, expectedData.get(4), gridName);
		}
	}

	@Test(description = "177341-Verify the Duplicate Sales Itmes in Sales Items Details Report after Redeeming a promotion")
	public void VerifyDuplicateSalesItmesInSalesItemsDetailsReport() {
		final String CASE_NUM = "177341";

		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);
		rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);
		rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);

		String promotionName = strings.getRandomCharacter();
		String displayName = strings.getRandomCharacter();
		String promotionType = rstLocationData.get(CNLocation.PROMOTION_TYPE);
		String orgName = propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE);
		String locationName = rstLocationData.get(CNLocation.LOCATION_NAME);
		String gridName = rstLocationData.get(CNLocation.TAB_NAME);

		List<String> requiredData = Arrays
				.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			List<String> menuItems = Arrays
					.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));

			navigationBar.navigateToMenuItem(menuItems.get(0));

			// New Promotion
			foundation.click(PromotionList.BTN_CREATE);
			foundation.isDisplayed(CreatePromotions.LBL_CREATE_PROMOTION);

			createPromotions.newPromotion(promotionType, promotionName, displayName, orgName, locationName);

			foundation.waitforElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);

			foundation.click(CreatePromotions.BTN_NEXT);
			dropdown.selectItem(CreatePromotions.DPD_DISCOUNT_BY, requiredData.get(1), Constants.TEXT);
			textBox.enterText(CreatePromotions.TXT_ITEM, requiredData.get(2));
			foundation.threadWait(Constants.ONE_SECOND);
			textBox.enterText(CreatePromotions.TXT_ITEM, Keys.ENTER);
//			foundation.click(CreatePromotions.ALL_ITEMS);

			foundation.threadWait(Constants.TWO_SECOND);
			String actualValue = dropdown.getSelectedItem(CreatePromotions.DPD_ITEM_SELECT);
			CustomisedAssert.assertEquals(actualValue, requiredData.get(2));

			textBox.enterText(CreatePromotions.TXT_AMOUNT, requiredData.get(3));
			textBox.enterText(CreatePromotions.TXT_TRANSACTION_MIN, requiredData.get(4));
			foundation.objectClick(CreatePromotions.BTN_NEXT);
			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);
			foundation.threadWait(Constants.ONE_SECOND);
			foundation.objectClick(CreatePromotions.BTN_OK);
			login.logout();
			browser.close();

			String product = rstProductSummaryData.get(CNProductSummary.PRODUCT_NAME);
			List<String> paymentEmailDetails = Arrays
					.asList(rstProductSummaryData.get(CNProductSummary.USER_KEY).split(Constants.DELIMITER_HASH));

			// Launch V5 Device and Searching for product
			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LandingPage.IMG_SEARCH_ICON));
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadTextWithCaseSensitive(product);
			foundation.click(ProductSearch.BTN_PRODUCT);
//			foundation.waitforElement(Payments.ACCOUNT_EMAIL, Constants.ONE_SECOND);
			foundation.click(Payments.ACCOUNT_EMAIL);
			foundation.waitforElement(Payments.EMAIL_lOGIN_BTN, Constants.ONE_SECOND);
			foundation.click(Payments.EMAIL_lOGIN_BTN);
			foundation.threadWait(Constants.ONE_SECOND);
			textBox.enterKeypadTextWithCaseSensitive(paymentEmailDetails.get(0));
			foundation.click(AccountLogin.BTN_NEXT);
			foundation.waitforElement(AccountLogin.BTN_PIN_NEXT, Constants.SHORT_TIME);
			foundation.threadWait(Constants.ONE_SECOND);
			textBox.enterPin(paymentEmailDetails.get(1));
			foundation.click(AccountLogin.BTN_PIN_NEXT);
			CustomisedAssert.assertTrue(
					foundation.isDisplayed(order.objText(rstProductSummaryData.get(CNProductSummary.DESCRIPTION))));
			browser.close();

			// Navigate to Reports
			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.navigateToMenuItem(menuItems.get(1));

			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));
//			reportList.selectDate("Last 7 Days");

			reportList.selectLocationForSecondTypeDropdown(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));

			// run and read report
			foundation.click(ReportList.BTN_RUN_REPORT);
			foundation.waitforElement(ProductTaxReport.LBL_REPORT_NAME, Constants.SHORT_TIME);
			salesItemDetailsReport.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));

			textBox.enterText(SalesItemDetailsReport.TXT_SEARCH,
					rstProductSummaryData.get(CNProductSummary.PRODUCT_NAME));
			salesItemDetailsReport.getTblRecordsUI();

			salesItemDetailsReport.verifyReportHeaders(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME));
			salesItemDetailsReport.verifyReportTimeData();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// Resetting the data
			List<String> menuItems = Arrays
					.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
			List<String> expectedData = Arrays
					.asList(rstProductSummaryData.get(CNProductSummary.REQUIRED_DATA).split(Constants.DELIMITER_HASH));
			promotionList.expirePromotion(menuItems.get(0), promotionName, expectedData.get(4), gridName);
		}
	}

	@Test(description = "186509-Verifying and Validating the Cross Org Rate Report Data")
	public void VerifyCrossOrgRateReportDataValidation() {
		final String CASE_NUM = "186509";

		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstOrgSummaryData = dataBase.getOrgSummaryData(Queries.ORG_SUMMARY, CASE_NUM);
		rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);
		rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);

		// Reading test data from DataBase
		List<String> menuItems = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> requiredData = Arrays
				.asList(rstOrgSummaryData.get(CNOrgSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));

		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			List<String> newdata = Arrays.asList(requiredData.get(0).split(Constants.DELIMITER_HASH));

			// Select Menu and Menu Item for Org Summary section
			navigationBar.navigateToMenuItem(menuItems.get(0));

			// Updating the the Org summary data
			textBox.enterText(OrgSummary.TXT_GMA_RATE, newdata.get(1));
			textBox.enterText(OrgSummary.TXT_CREDIT_RATE, newdata.get(2));
			textBox.enterText(OrgSummary.TXT_NANO_GMA_RATE, newdata.get(3));
			textBox.enterText(OrgSummary.TXT_NANO_CREDIT_RATE, newdata.get(4));
			foundation.click(OrgSummary.BTN_SAVE);
			foundation.waitforElement(OrgSummary.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			String actualData = foundation.getText(OrgSummary.TXT_SPINNER_MSG);
			CustomisedAssert.assertEquals(actualData, requiredData.get(2));

			// Select Menu and Menu Item for Reports section
			navigationBar.navigateToMenuItem(menuItems.get(1));

			// Select the Report Date range and Location
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList
					.selectOrg(propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// run and read report
			foundation.click(ReportList.BTN_RUN_REPORT);
			foundation.waitforElement(ProductTaxReport.LBL_REPORT_NAME, Constants.SHORT_TIME);
			crossOrgRate.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));
			crossOrgRate.getTblRecordsUI();

			// Validating the Headers and Report data
			crossOrgRate.verifyReportHeaders(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME));
			crossOrgRate.verifyReportData(rstProductSummaryData.get(CNProductSummary.ACTUAL_DATA));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// Select Menu and Menu Item for Org Summary section
			navigationBar.navigateToMenuItem(menuItems.get(0));

			List<String> olddata = Arrays.asList(requiredData.get(1).split(Constants.DELIMITER_HASH));

			// Resetting the old data
			textBox.enterText(OrgSummary.TXT_GMA_RATE, olddata.get(1));
			textBox.enterText(OrgSummary.TXT_CREDIT_RATE, olddata.get(2));
			textBox.enterText(OrgSummary.TXT_NANO_GMA_RATE, olddata.get(3));
			textBox.enterText(OrgSummary.TXT_NANO_CREDIT_RATE, olddata.get(4));
			foundation.click(OrgSummary.BTN_SAVE);
			foundation.waitforElement(OrgSummary.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			String actualData = foundation.getText(OrgSummary.TXT_SPINNER_MSG);
			CustomisedAssert.assertEquals(actualData, requiredData.get(2));
		}
	}

	@Test(description = "186633-This test validates  inventory Adjustment Detail Report Data Calculation")
	public void inventoryAdjustmentDetailReportData() {
		try {
			final String CASE_NUM = "186633";

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);
			rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			String reportName = rstReportListData.get(CNReportList.REPORT_NAME);
			List<String> requiredData = Arrays
					.asList(rstProductSummaryData.get(CNProductSummary.REQUIRED_DATA).split(Constants.DELIMITER_HASH));
			List<String> menu = Arrays
					.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// navigate to location and Inventory section
			navigationBar.navigateToMenuItem(menu.get(0));
			locationList.selectLocationName(
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));
			foundation.waitforElement(LocationSummary.LNK_INVENTORY, Constants.SHORT_TIME);
			locationSummary.selectTab(rstLocationSummaryData.get(CNLocationSummary.TAB_NAME));

			textBox.enterText(LocationSummary.TXT_INVENTORY_FILTER,
					rstProductSummaryData.get(CNProductSummary.SCAN_CODE));
			locationSummary.updateInventory(rstProductSummaryData.get(CNProductSummary.SCAN_CODE), requiredData.get(0),
					requiredData.get(2));

			foundation.threadWait(Constants.FIFTY_FIVE_SECONDS);

			textBox.enterText(LocationSummary.TXT_INVENTORY_FILTER,
					rstProductSummaryData.get(CNProductSummary.SCAN_CODE));

			// Updating the Inventory of the product
			locationSummary.updateInventory(rstProductSummaryData.get(CNProductSummary.SCAN_CODE), requiredData.get(1),
					requiredData.get(2));

			String updatedTime = String
					.valueOf(dateAndTime.getDateAndTime1(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION),
							rstLocationSummaryData.get(CNLocationSummary.TIME_ZONE)));

			// navigate to Reports
			navigationBar.navigateToMenuItem(menu.get(1));

			// Select the Report Date range and Location
			reportList.selectReport(reportName);
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));
			reportList.selectLocation(
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(ReportList.BTN_RUN_REPORT);
			foundation.threadWait(Constants.THREE_SECOND);
			inventoryAdjustmentDetail.verifyReportName(reportName);
			textBox.enterText(InventoryAdjustmentDetail.TXT_SEARCH, String.valueOf(updatedTime).toUpperCase());
			inventoryAdjustmentDetail.getTblRecordsUI();

			// Validating the Headers and Report data
			inventoryAdjustmentDetail.verifyReportHeaders(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME));
			inventoryAdjustmentDetail.verifyReportData(rstProductSummaryData.get(CNProductSummary.ACTUAL_DATA));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
//		} finally {
//			// navigate to location and Resetting the data back
//			List<String> menu = Arrays
//					.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD)); 
//			List<String> requiredData = Arrays
//					.asList(rstProductSummaryData.get(CNProductSummary.REQUIRED_DATA).split(Constants.DELIMITER_HASH));
//			navigationBar.navigateToMenuItem(menu.get(0));
//			locationList.selectLocationName(
//					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));
//			foundation.waitforElement(LocationSummary.LNK_INVENTORY, Constants.SHORT_TIME);
//			locationSummary.selectTab(rstLocationSummaryData.get(CNLocationSummary.TAB_NAME));
//			textBox.enterText(LocationSummary.TXT_INVENTORY_FILTER,
//					rstProductSummaryData.get(CNProductSummary.SCAN_CODE));
//			locationSummary.updateInventory(rstProductSummaryData.get(CNProductSummary.SCAN_CODE), requiredData.get(0), requiredData.get(2));
		}
	}

	@Test(description = "197201-This test validates inventory Total Report Data Validation")
	public void verifyInventoryTotalReportData() {

		try {
			final String CASE_NUM = "197201";

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);
			rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			String reportName = rstReportListData.get(CNReportList.REPORT_NAME);

			List<String> requiredData = Arrays
					.asList(rstProductSummaryData.get(CNProductSummary.REQUIRED_DATA).split(Constants.DELIMITER_HASH));
			List<String> menu = Arrays
					.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// navigate to location and Inventory section
			navigationBar.navigateToMenuItem(menu.get(0));
			locationList.selectLocationName(
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));
			foundation.waitforElement(LocationSummary.LNK_INVENTORY, Constants.SHORT_TIME);

			locationSummary.selectTab(rstLocationSummaryData.get(CNLocationSummary.TAB_NAME));

			foundation.threadWait(Constants.ONE_SECOND);

			textBox.enterText(LocationSummary.TXT_INVENTORY_FILTER,
					rstProductSummaryData.get(CNProductSummary.SCAN_CODE));

			locationSummary.updateInventory(rstProductSummaryData.get(CNProductSummary.SCAN_CODE), requiredData.get(0),
					requiredData.get(2));

			foundation.threadWait(Constants.FIFTY_FIVE_SECONDS);

			textBox.enterText(LocationSummary.TXT_INVENTORY_FILTER,
					rstProductSummaryData.get(CNProductSummary.SCAN_CODE));

			// Updating the Inventory of the product
			locationSummary.updateInventory(rstProductSummaryData.get(CNProductSummary.SCAN_CODE), requiredData.get(1),
					requiredData.get(2));

			// navigate to Reports
			navigationBar.navigateToMenuItem(menu.get(1));

			// Select the Report Date range and Location
			reportList.selectReport(reportName);
			foundation.click(ReportList.BTN_RUN_REPORT);
			foundation.threadWait(Constants.THREE_SECOND);
			InventoryTotals.verifyReportName(reportName);
			textBox.enterText(InventoryAdjustmentDetail.TXT_SEARCH,
					rstProductSummaryData.get(CNProductSummary.PRODUCT_NAME));
			InventoryTotals.getTblRecordsUI();

			// Validating the Headers and Report data
			InventoryTotals.verifyReportHeaders(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME));
			InventoryTotals.verifyReportData(rstProductSummaryData.get(CNProductSummary.ACTUAL_DATA));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "197249-Verify the Remaining Guest Pass Liability Report data validation")
	public void RemainingGuestPassLiabilityReportDataValidation() {
		try {
			final String CASE_NUM = "197249";

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);
			rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);

			// navigate and login to ADM
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			String guestPassName = strings.getRandomCharacter();
			String reportName = rstReportListData.get(CNReportList.REPORT_NAME);
			List<String> requiredData = Arrays
					.asList(rstProductSummaryData.get(CNProductSummary.REQUIRED_DATA).split(Constants.DELIMITER_HASH));
			List<String> menu = Arrays
					.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// navigate to Admin and Guest Pass section
			navigationBar.navigateToMenuItem(menu.get(0));

			textBox.enterText(RemainingGuestPassLiability.GUEST_PASS_TITLE, guestPassName);
			textBox.enterText(RemainingGuestPassLiability.GUEST_PASS_CHANGETO, requiredData.get(0));
			textBox.enterText(RemainingGuestPassLiability.GUEST_PASS_AMOUNT, requiredData.get(1));
			dropdown.selectItem(RemainingGuestPassLiability.ALLOW_REUSE_DD, requiredData.get(2), Constants.TEXT);
			foundation.click(RemainingGuestPassLiability.GUEST_PASS_SAVEBTN);

			String expirydDate = String.valueOf(
					dateAndTime.getFutureDate(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION), "1"));

			// navigate to Reports
			navigationBar.navigateToMenuItem(menu.get(1));

			// Select the Report Date range and Location
			reportList.selectReport(reportName);
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));
			foundation.threadWait(Constants.ONE_SECOND);
			foundation.click(ReportList.BTN_RUN_REPORT);
			foundation.threadWait(Constants.THREE_SECOND);
			remainingGuestPassLiability.verifyReportName(reportName);
			textBox.enterText(RemainingGuestPassLiability.TXT_SEARCH, guestPassName);
			remainingGuestPassLiability.getTblRecordsUI();
			remainingGuestPassLiability.getIntialData().putAll(remainingGuestPassLiability.getReportsData());

			List<String> expectedData = Arrays
					.asList(rstProductSummaryData.get(CNProductSummary.ACTUAL_DATA).split(Constants.DELIMITER_HASH));

			remainingGuestPassLiability.updateData(remainingGuestPassLiability.getTableHeaders().get(0), guestPassName);
			remainingGuestPassLiability.updateData(remainingGuestPassLiability.getTableHeaders().get(1),
					expectedData.get(0));
			remainingGuestPassLiability.updateData(remainingGuestPassLiability.getTableHeaders().get(2), expirydDate);
			remainingGuestPassLiability.updateData(remainingGuestPassLiability.getTableHeaders().get(3),
					expectedData.get(1));
			remainingGuestPassLiability.updateData(remainingGuestPassLiability.getTableHeaders().get(4),
					expectedData.get(2));
			remainingGuestPassLiability.updateData(remainingGuestPassLiability.getTableHeaders().get(5),
					expectedData.get(3));
			remainingGuestPassLiability.updateData(remainingGuestPassLiability.getTableHeaders().get(6),
					expectedData.get(4));

			// Validating the Headers and Report data
			remainingGuestPassLiability.verifyReportHeaders(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME));
			remainingGuestPassLiability.verifyReportData();

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "197288-This test validates Guest Pass By Device Report Data Validation")
	public void guestPassByDeviceReportDataValidation() {

		try {
			final String CASE_NUM = "197288";

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			String reportName = rstReportListData.get(CNReportList.REPORT_NAME);
			List<String> requiredData = Arrays
					.asList(rstProductSummaryData.get(CNProductSummary.REQUIRED_DATA).split(Constants.DELIMITER_HASH));
			List<String> locationData = Arrays
					.asList(rstProductSummaryData.get(CNProductSummary.ACTUAL_DATA).split(Constants.DELIMITER_HASH));
			String menu = rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM);

			navigationBar.selectOrganization(locationData.get(0));
			// navigate to Reports
			navigationBar.navigateToMenuItem(menu);

			// Select the Report Date range and Location
			reportList.selectReport(reportName);
			reportList.selectDateRangeDate(rstReportListData.get(CNReportList.DATE_RANGE), locationData.get(2),
					GuestPassByDevice.DATA_EXISTING_DATE, GuestPassByDevice.DATA_EXISTING_DATE);
			reportList.selectLocation(locationData.get(1));
			foundation.click(ReportList.BTN_RUN_REPORT);
			foundation.threadWait(Constants.TWO_SECOND);
			guestPassByDevice.verifyReportName(reportName);
			textBox.enterText(GuestPassByDevice.TXT_SEARCH, requiredData.get(2));
			guestPassByDevice.getTblRecordsUI();

			// Validating the Headers and Report data
			guestPassByDevice.verifyReportHeaders(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME));
			guestPassByDevice.verifyReportData(rstProductSummaryData.get(CNProductSummary.REQUIRED_DATA));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * This Method is for Inventory Variance Report Data Validation
	 * 
	 * @author ravindhara Date: 01-07-2022
	 */
	@Test(description = "197618-This test validates Inventory Variance Report Data Validation")
	public void inventoryVarianceReportDataValidation() {

		try {
			final String CASE_NUM = "197618";

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			String reportName = rstReportListData.get(CNReportList.REPORT_NAME);
			List<String> requiredData = Arrays
					.asList(rstProductSummaryData.get(CNProductSummary.REQUIRED_DATA).split(Constants.DELIMITER_HASH));
			List<String> locationData = Arrays
					.asList(rstProductSummaryData.get(CNProductSummary.ACTUAL_DATA).split(Constants.DELIMITER_HASH));
			String menu = rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM);

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			// navigate to Reports
			navigationBar.navigateToMenuItem(menu);

			// Select the Report Date range and Location
			reportList.selectReport(reportName);
			reportList.selectLocationForSecondTypeDropdown(locationData.get(0));
			foundation.threadWait(Constants.ONE_SECOND);
			reportList.selectDateRangeDateofType2(locationData.get(1), InventoryVariance.DATA_EXISTING_DATE,
					InventoryVariance.DATA_EXISTING_DATE);
			foundation.click(ReportList.BTN_RUN_REPORT);
			foundation.threadWait(Constants.TWO_SECOND);
			inventoryVariance.verifyReportName(reportName);
			textBox.enterText(InventoryVariance.TXT_SEARCH, requiredData.get(2));
			inventoryVariance.getTblRecordsUI();

			// Validating the Headers and Report data
			inventoryVariance.verifyReportHeaders(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME));
			inventoryVariance.verifyReportData(rstProductSummaryData.get(CNProductSummary.REQUIRED_DATA));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * This Method is for Inventory List Report Data Validation
	 * 
	 * @author ravindhara Date: 01-07-2022
	 */
	@Test(description = "197657-This test validates Inventory List Report Data Validation")
	public void inventoryListReportDataValidation() {

		try {
			final String CASE_NUM = "197657";

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			String reportName = rstReportListData.get(CNReportList.REPORT_NAME);
			String locationName = propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1,
					FilePath.PROPERTY_CONFIG_FILE);
			String menu = rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM);
			List<String> columnData = Arrays
					.asList(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME).split(Constants.DELIMITER_TILD));

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			// navigate to Reports
			navigationBar.navigateToMenuItem(menu);

			// Select the Report Date range and Location
			reportList.selectReport(reportName);
			reportList.selectDateRangeOfSinglrDateofType3(rstReportListData.get(CNReportList.DATE_RANGE),
					InventoryList.DATA_EXISTING_DATE);
			reportList.selectLocation(locationName);

			foundation.click(ReportList.BTN_RUN_REPORT);
			foundation.threadWait(Constants.TWO_SECOND);
			inventoryList.verifyReportName(reportName);
			inventoryList.getTblRecordsUI();

			// Validating the Headers and Report data
			inventoryList.verifyReportHeaders(columnData.get(0));
			inventoryList.verifyReportData(rstProductSummaryData.get(CNProductSummary.REQUIRED_DATA));

			foundation.click(InventoryList.TBL_EXPAND_BTN);
			foundation.threadWait(Constants.ONE_SECOND);
			inventoryList.getExpandedTblRecordsOfUI();

			inventoryList.verifyReportHeaders(columnData.get(1));
			inventoryList.verifyReportDataForExpanded(Arrays
					.asList(rstProductSummaryData.get(CNProductSummary.ACTUAL_DATA).split(Constants.DELIMITER_TILD)));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * This Method is for Consumer Feedback Survey Report Data Validation
	 * 
	 * @author ravindhara Date: 11-07-2022
	 */
	@Test(description = "197794-Verifying and Validating the Consumer Feedback Survey Report Data")
	public void consumerFeedbackSurveyReportDataValidation() {
		final String CASE_NUM = "197794";

		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);
		rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);

		// Reading test data from DataBase
		List<String> menuItems = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> requiredData = Arrays
				.asList(rstProductSummaryData.get(CNProductSummary.REQUIRED_DATA).split(Constants.DELIMITER_HASH));

		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item for Location and to Enable Consumer Feedback Survey
			navigationBar.navigateToMenuItem(menuItems.get(0));
			locationList.selectLocationName(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(ConsumerFeedbackSurvey.LOCATION_TOGGLEINFO_BAR);
			foundation.threadWait(Constants.ONE_SECOND);
			dropdown.selectItem(ConsumerFeedbackSurvey.CONSUMER_FEEDBACK_SUMMARY_ENABLE_DD, requiredData.get(0),
					Constants.TEXT);
			textBox.enterText(ConsumerFeedbackSurvey.CONSUMER_FEEDBACK_QUESTION, requiredData.get(2));
			foundation.click(ConsumerFeedbackSurvey.LOCATION_SUMMARY_SAVE_BUTTON);
			foundation.waitforElement(ConsumerFeedbackSurvey.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			locationList.selectLocationName(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(LocationSummary.BTN_FULL_SYNC);
			foundation.isDisplayed(LocationSummary.LBL_SPINNER_MSG);
			login.logout();
			browser.close();

			String product = rstProductSummaryData.get(CNProductSummary.PRODUCT_NAME);
			List<String> paymentEmailDetails = Arrays
					.asList(rstProductSummaryData.get(CNProductSummary.USER_KEY).split(Constants.DELIMITER_HASH));

			// Launch V5 Device and purchasing a product with Happy Feedback
			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			consumerFeedbackSurvey.transactionThroughDevice(product, paymentEmailDetails.get(0),
					paymentEmailDetails.get(1), requiredData.get(2), ConsumerFeedbackSurvey.HAPPY_EMOJI_BTN,
					order.objText(rstProductSummaryData.get(CNProductSummary.DESCRIPTION)));
			browser.close();

			// Navigate to Reports
			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.navigateToMenuItem(menuItems.get(1));

			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));

			reportList.selectLocation(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));

			// run and read report
			foundation.click(ReportList.BTN_RUN_REPORT);

			foundation.threadWait(Constants.THREE_SECOND);

			consumerFeedbackSurvey.getTblRecordsUI();
			consumerFeedbackSurvey.getIntialData().putAll(consumerFeedbackSurvey.getReportsData());

			// apply calculation and update data
			consumerFeedbackSurvey.updateData(consumerFeedbackSurvey.getTableHeaders().get(1),
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));
			consumerFeedbackSurvey.updateTotalFeedbackCount(consumerFeedbackSurvey.getTableHeaders().get(3));
			consumerFeedbackSurvey.updateCount(consumerFeedbackSurvey.getTableHeaders().get(4));
			consumerFeedbackSurvey.updateCount(consumerFeedbackSurvey.getTableHeaders().get(5));
			consumerFeedbackSurvey.updateCount(consumerFeedbackSurvey.getTableHeaders().get(6));

			login.logout();
			browser.close();

			// Launch V5 Device and purchasing a product with Happy Feedback
			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			consumerFeedbackSurvey.transactionThroughDevice(product, paymentEmailDetails.get(0),
					paymentEmailDetails.get(1), requiredData.get(2), ConsumerFeedbackSurvey.HAPPY_EMOJI_BTN,
					order.objText(rstProductSummaryData.get(CNProductSummary.DESCRIPTION)));
			browser.close();

			// Launch V5 Device and purchasing a product with Sad Feedback
			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			consumerFeedbackSurvey.transactionThroughDevice(product, paymentEmailDetails.get(0),
					paymentEmailDetails.get(1), requiredData.get(2), ConsumerFeedbackSurvey.SAD_EMOJI_BTN,
					order.objText(rstProductSummaryData.get(CNProductSummary.DESCRIPTION)));
			browser.close();

			// Launch V5 Device and purchasing a product with Neutral Feedback
			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			consumerFeedbackSurvey.transactionThroughDevice(product, paymentEmailDetails.get(0),
					paymentEmailDetails.get(1), requiredData.get(2), ConsumerFeedbackSurvey.NORMAL_EMOJI_BTN,
					order.objText(rstProductSummaryData.get(CNProductSummary.DESCRIPTION)));
			browser.close();

			// Navigate to Reports
			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.navigateToMenuItem(menuItems.get(1));

			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));

			reportList.selectLocation(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));

			// run and read report
			foundation.click(ReportList.BTN_RUN_REPORT);

			foundation.threadWait(Constants.THREE_SECOND);

			consumerFeedbackSurvey.getTblRecordsUI();

			// verify report headers
			consumerFeedbackSurvey.verifyReportHeaders(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME));

			// verify report data
			consumerFeedbackSurvey.verifyReportData();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// navigate to location and disabling of Consumer Feedback Survey
			navigationBar.navigateToMenuItem(menuItems.get(0));
			locationList.selectLocationName(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(ConsumerFeedbackSurvey.LOCATION_TOGGLEINFO_BAR);
			foundation.threadWait(Constants.ONE_SECOND);
			dropdown.selectItem(ConsumerFeedbackSurvey.CONSUMER_FEEDBACK_SUMMARY_ENABLE_DD, requiredData.get(1),
					Constants.TEXT);
			foundation.click(ConsumerFeedbackSurvey.LOCATION_SUMMARY_SAVE_BUTTON);
			foundation.waitforElement(ConsumerFeedbackSurvey.TXT_SPINNER_MSG, Constants.SHORT_TIME);
		}
	}

	/**
	 * @author karthikr
	 * @Date 25/07/2022
	 */
	@Test(description = "153374 - Sales Item Details Report data validation")
	public void SalesItemDetailsReportDataValidation() {
		try {
			final String CASE_NUM = "153374";
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// process sales API to generate data
			/**productSales.processAPI(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Data validation for Report Based on Groupby ITEMS
			// Select the Report Date range and Location and run report
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));
			reportList.selectLocation(
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(ReportList.BTN_RUN_REPORT);
			foundation.waitforElement(ProductSales.LBL_REPORT_NAME, Constants.SHORT_TIME);
			productSales.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));

			// Read the Report the Data
			productSales.getTblRecordsUI();
			productSales.getIntialData().putAll(productSales.getReportsData());

			// process sales API to generate data
			productSales.processAPI(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));

			// rerun and reread report
			foundation.click(ReportList.BTN_RUN_REPORT);
			foundation.threadWait(Constants.TWO_SECOND);
			productSales.getTblRecordsUI();

			// update the report date baseed on calculation
			String productPrice = rstProductSummaryData.get(CNProductSummary.PRICE);
			String tax = rstProductSummaryData.get(CNProductSummary.TAX);
			String productName = rstProductSummaryData.get(CNProductSummary.PRODUCT_NAME);
			String scanCode = rstProductSummaryData.get(CNProductSummary.SCAN_CODE);
			String userKey = rstProductSummaryData.get(CNProductSummary.USER_KEY);

			productSales.updateData(productSales.getTableHeaders().get(0), productName);
			productSales.updateData(productSales.getTableHeaders().get(1), scanCode);
			productSales.updateData(productSales.getTableHeaders().get(2), userKey);
			productSales.saleCount(productSales.getTableHeaders().get(6));
			productSales.calculateAmount(productSales.getTableHeaders().get(7), productPrice, tax);

			// verify report headers
			reportList.verifyReportHeaders(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME),
					productSales.getTableHeaders());

			productSales.verifyReportHeaders(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME));

			// verify report data
			productSales.verifyReportData();**/

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
}
