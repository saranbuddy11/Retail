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
import at.framework.ui.CheckBox;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.database.columns.CNConsumerSearch;
import at.smartshop.database.columns.CNConsumerSummary;
import at.smartshop.database.columns.CNDeviceList;
import at.smartshop.database.columns.CNLocation;
import at.smartshop.database.columns.CNLocationList;
import at.smartshop.database.columns.CNLocationSummary;
import at.smartshop.database.columns.CNNavigationMenu;
import at.smartshop.database.columns.CNOrgSummary;
import at.smartshop.database.columns.CNProductSummary;
import at.smartshop.database.columns.CNReportList;
import at.smartshop.database.columns.CNV5Device;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.keys.Reports;
import at.smartshop.pages.AVISubFeeReport;
import at.smartshop.pages.AccountAdjustment;
import at.smartshop.pages.AccountFunding;
import at.smartshop.pages.AccountProfitability;
import at.smartshop.pages.AlcoholSoldDetailsReport;
import at.smartshop.pages.BadScanReport;
import at.smartshop.pages.BalanceReport;
import at.smartshop.pages.BillingInformationReport;
import at.smartshop.pages.CanadaMultiTaxReport;
import at.smartshop.pages.CancelReport;
import at.smartshop.pages.CashAudit;
import at.smartshop.pages.CashFlow;
import at.smartshop.pages.CashFlowEmployee;
import at.smartshop.pages.CashFlowDetails;
import at.smartshop.pages.CashFlowDetailsInternational;
import at.smartshop.pages.CashFlowDevice;
import at.smartshop.pages.CashFlowEmployeeDevice;
import at.smartshop.pages.CashoutLog;
import at.smartshop.pages.ConsumerFeedbackSurvey;
import at.smartshop.pages.ConsumerSearch;
import at.smartshop.pages.ConsumerSummary;
import at.smartshop.pages.CreatePromotions;
import at.smartshop.pages.CreditTransaction;
import at.smartshop.pages.CrossOrgLoyaltyReport;
import at.smartshop.pages.CrossOrgRateReport;
import at.smartshop.pages.DailySalesSummary;
import at.smartshop.pages.DataSourceManager;
import at.smartshop.pages.DeleteSummaryReport;
import at.smartshop.pages.DeviceByCategoryReport;
import at.smartshop.pages.DeviceDashboard;
import at.smartshop.pages.EmployeeCompDetailsReport;
import at.smartshop.pages.EntrySummaryReport;
import at.smartshop.pages.FinancialCanned;
import at.smartshop.pages.FinancialRecapReport;
import at.smartshop.pages.FolioBillingReport;
import at.smartshop.pages.GuestPassByDevice;
import at.smartshop.pages.HealthAheadPercentageReport;
import at.smartshop.pages.HealthAheadReport;
import at.smartshop.pages.ICEReport;
import at.smartshop.pages.IntegrationPaymentReport;
import at.smartshop.pages.IntlWebAppFunding;
import at.smartshop.pages.InventoryAdjustmentDetail;
import at.smartshop.pages.InventoryList;
import at.smartshop.pages.InventoryTotals;
import at.smartshop.pages.InventoryValueSummary;
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
import at.smartshop.pages.PayrollDeductDetails;
import at.smartshop.pages.PersonalChargeReport;
import at.smartshop.pages.ProductCannedReport;
import at.smartshop.pages.ProductPricingReport;
import at.smartshop.pages.ProductSales;
import at.smartshop.pages.ProductSalesByCategoryReport;
import at.smartshop.pages.ProductTaxReport;
import at.smartshop.pages.PromotionAnalysis;
import at.smartshop.pages.PromotionList;
import at.smartshop.pages.QueuedCreditTransactionsReport;
import at.smartshop.pages.RemainingGuestPassLiability;
import at.smartshop.pages.ReportList;
import at.smartshop.pages.SalesAnalysisReport;
import at.smartshop.pages.SalesBy15Minutes;
import at.smartshop.pages.SalesBy30Minutes;
import at.smartshop.pages.SalesItemDetailsReport;
import at.smartshop.pages.SalesSummaryAndCost;
import at.smartshop.pages.SalesTimeDetailsByDevice;
import at.smartshop.pages.SalesTimeDetailsReport;
import at.smartshop.pages.SoldDetails;
import at.smartshop.pages.SoldDetailsInt;
import at.smartshop.pages.SoldItemCOGS;
import at.smartshop.pages.SubsidyConsumerSpend;
import at.smartshop.pages.TenderTransactionLogReport;
import at.smartshop.pages.TipDetailsReport;
import at.smartshop.pages.TipSummaryReport;
import at.smartshop.pages.TransactionCannedReport;
import at.smartshop.pages.UFSByEmployeeDevice;
import at.smartshop.pages.UFSReport;
import at.smartshop.pages.UFSByDevice;
import at.smartshop.pages.UnfinishedCloseReport;
import at.smartshop.pages.UnpaidOrder;
import at.smartshop.pages.UnsoldReport;
import at.smartshop.pages.VoidedProductReport;
import at.smartshop.utilities.CurrenyConverter;
import at.smartshop.utilities.WebService;
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
	private InventoryTotals inventoryTotals = new InventoryTotals();
	private RemainingGuestPassLiability remainingGuestPassLiability = new RemainingGuestPassLiability();
	private GuestPassByDevice guestPassByDevice = new GuestPassByDevice();
	private InventoryList inventoryList = new InventoryList();
	private InventoryVariance inventoryVariance = new InventoryVariance();
	private ConsumerFeedbackSurvey consumerFeedbackSurvey = new ConsumerFeedbackSurvey();
	private DailySalesSummary dailySalesSummary = new DailySalesSummary();
	private IntlWebAppFunding intlWebAppFunding = new IntlWebAppFunding();
	private ProductSales productSales = new ProductSales();
	private CashFlowEmployeeDevice cashFlowEmployeeDevice = new CashFlowEmployeeDevice();
	private CashFlowDevice cashFlowDevice = new CashFlowDevice();
	private SalesSummaryAndCost salesSummaryAndCost = new SalesSummaryAndCost();
	private SoldDetails soldDetails = new SoldDetails();
	private SalesTimeDetailsReport salesTimeDetailsReport = new SalesTimeDetailsReport();
	private SalesTimeDetailsByDevice salesTimeDetailsByDevice = new SalesTimeDetailsByDevice();
	private SoldDetailsInt soldDetailsInt = new SoldDetailsInt();
	private SubsidyConsumerSpend subsidyConsumerSpend = new SubsidyConsumerSpend();
	private UnpaidOrder unpaidOrder = new UnpaidOrder();
	private InventoryValueSummary inventoryValueSummary = new InventoryValueSummary();
	private EntrySummaryReport entrySummaryReport = new EntrySummaryReport();
	private SoldItemCOGS soldItemCOGS = new SoldItemCOGS();
	private DeleteSummaryReport deleteSummaryReport = new DeleteSummaryReport();
	private AccountFunding accountFunding = new AccountFunding();
	private CashAudit cashAudit = new CashAudit();
	private ProductCannedReport ProductCannedReport = new ProductCannedReport();
	private CreditTransaction creditTransaction = new CreditTransaction();
	private CashoutLog cashOutLog = new CashoutLog();
	private CancelReport cancelReport = new CancelReport();
	private BalanceReport balanceReport = new BalanceReport();
	private WebService webService = new WebService();
	private AccountProfitability accountProfitability = new AccountProfitability();
	private FinancialCanned financialCanned = new FinancialCanned();
	private SalesBy15Minutes salesBy15Minutes = new SalesBy15Minutes();
	private SalesBy30Minutes salesBy30Minutes = new SalesBy30Minutes();
	private UFSByEmployeeDevice ufsByEmployeeDevice = new UFSByEmployeeDevice();
	private UFSReport ufsReport = new UFSReport();
	private CashFlowEmployee CashFlowEmployee = new CashFlowEmployee();
	private CashFlowDetails cashFlowDetails = new CashFlowDetails();
	private CheckBox checkBox = new CheckBox();
	private PayrollDeductDetails payrollDeductDetails = new PayrollDeductDetails();
	private UFSByDevice ufsByDevice = new UFSByDevice();
	private CashFlowDetailsInternational cashFlowDetailsInternational = new CashFlowDetailsInternational();

	private Map<String, String> rstNavigationMenuData;
	private Map<String, String> rstConsumerSearchData;
	private Map<String, String> rstProductSummaryData;
	private Map<String, String> rstLocationSummaryData;
	private Map<String, String> rstConsumerSummaryData;
	private Map<String, String> rstReportListData;
	private Map<String, String> rstLocationData;
	private Map<String, String> rstOrgSummaryData;
	private Map<String, String> rstV5DeviceData;
	private Map<String, String> rstDeviceListData;
	private Map<String, String> rstLocationListData;

//	@Parameters({ "driver", "browser", "reportsDB" })
//	@BeforeClass
//	public void beforeTest(String drivers, String browsers, String reportsDB) {
//		try {
//			browser.launch(drivers, browsers);
//			dataSourceManager.switchToReportsDB(reportsDB);
//			browser.close();
//		} catch (Exception exc) {
//			TestInfra.failWithScreenShot(exc.toString());
//		}
//	}

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

			String updatedTime = String
					.valueOf(dateAndTime.getDateAndTime(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION),
							rstLocationSummaryData.get(CNLocationSummary.TIME_ZONE)));

			textBox.enterText(ConsumerSummary.TXT_ADJUST_BALANCE, Double.toString(updatedbalance));
			dropdown.selectItem(ConsumerSummary.DPD_REASON, rstConsumerSummaryData.get(CNConsumerSummary.REASON),
					Constants.TEXT);

			dropdown.selectItemByIndex(ConsumerSummary.REF_EFT, 0);

			foundation.threadWait(Constants.MEDIUM_TIME);

			foundation.click(ConsumerSummary.BTN_REASON_SAVE);

			// converting time zone to specific time zone
			/*
			 * String updatedTime =
			 * String.valueOf(dateAndTime.getDateAndTimeWithOneHourAhead(
			 * rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION),
			 * rstLocationSummaryData.get(CNLocationSummary.TIME_ZONE)));
			 */

			foundation.threadWait(Constants.SHORT_TIME);

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
	@Parameters({ "environment" })
	public void productTaxReportData(String environment) {
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
			productTax.processAPI(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION), environment);

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
			textBox.enterText(ProductPricingReport.TXT_SEARCH, rstProductSummaryData.get(CNProductSummary.SCAN_CODE));
			productPricing.getTblRecordsUI();
			productPricing.getIntialData().putAll(productPricing.getReportsData());
			productPricing.getTblRecordsUI();

			// get Location Data
			navigationBar.navigateToMenuItem(menu.get(1));
			locationList.selectLocationName(
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));
			locationSummary.selectTab(rstLocationSummaryData.get(CNLocationSummary.TAB_NAME));
			locationSummary.manageColumn(rstLocationSummaryData.get(CNLocationSummary.COLUMN_NAME));

			textBox.enterText(LocationSummary.TXT_PRODUCT_FILTER,
					rstProductSummaryData.get(CNProductSummary.SCAN_CODE));

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
	@Parameters({ "environment" })
	public void transactionCannedReportData(String environment) {
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

			String deviceId;
			if (environment.equals(Constants.STAGING)) {
				deviceId = propertyFile.readPropertyFile(Configuration.DEVICE_ID_STAGING, FilePath.PROPERTY_CONFIG_FILE);
			} else {
				deviceId = propertyFile.readPropertyFile(Configuration.DEVICE_ID, FilePath.PROPERTY_CONFIG_FILE);
			};
//			String deviceId = rstProductSummaryData.get(CNProductSummary.DEVICE_ID);

			// process sales API to generate data
			transactionCanned.processAPI(rstLocationSummaryData.get(CNLocationSummary.REQUIRED_DATA), deviceId, environment);

			// Select Organization
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Navigate to Reports
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location
			List<String> reportName = Arrays
					.asList(rstReportListData.get(CNReportList.REPORT_NAME).split(Constants.DELIMITER_HASH));
			if (environment.equals(Constants.STAGING)) {
				reportList.selectReport(reportName.get(1));
			} else {
				reportList.selectReport(reportName.get(0));
			};
//			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));
			reportList.selectLocation(
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));

			// run and read report
			foundation.objectFocus(ReportList.BTN_RUN_REPORT);
			foundation.click(ReportList.BTN_RUN_REPORT);
			
			if (environment.equals(Constants.STAGING)) {
				transactionCanned.verifyReportName(reportName.get(1));
			} else {
				transactionCanned.verifyReportName(reportName.get(0));
			};
//			transactionCanned.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));
			transactionCanned.getTblRecordsUI();
			transactionCanned.getIntialData().putAll(transactionCanned.getReportsData());
			transactionCanned.getIntialTotal().putAll(transactionCanned.getUpdatedTotal());

			// read updated data
			transactionCanned.processAPI(rstLocationSummaryData.get(CNLocationSummary.REQUIRED_DATA), deviceId, environment);
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
	@Parameters({ "environment" })
	public void memberPurchaseDetailsReport(String environment) {
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
			memberPurchaseDetails.processAPI(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION), environment);

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
			
			memberPurchaseDetails.getMemberPurchaseDetails();

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
	@Parameters({ "environment" })
	public void deviceByCategoryReportData(String environment) {
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

			String deviceId;
			if (environment.equals(Constants.STAGING)) {
				deviceId = propertyFile.readPropertyFile(Configuration.DEVICE_ID_STAGING, FilePath.PROPERTY_CONFIG_FILE);
			} else {
				deviceId = propertyFile.readPropertyFile(Configuration.DEVICE_ID, FilePath.PROPERTY_CONFIG_FILE);
			};

			// process sales API to generate data
			deviceByCategory.processAPI(deviceId, environment);
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

			deviceByCategory.processAPI(deviceId, environment);

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
	@Parameters({ "environment" })
	public void employeeCompDetailsReportData(String environment) {
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
			employeeCompDetails.processAPI(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION), environment);

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
	@Parameters({ "environment" })
	public void memberPurchaseSummaryReportData(String environment) {
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
			memberPurchaseSummary.processAPI(environment);

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
			textBox.enterText(MemberPurchaseSummaryReport.TXT_SEARCH,
					rstProductSummaryData.get(CNProductSummary.SHORT_NAME));
			memberPurchaseSummary.getTblRecordsUI();
			memberPurchaseSummary.getIntialData().putAll(memberPurchaseSummary.getReportsData());

			// Process GMA and sales API
			memberPurchaseSummary.processAPI(environment);
			foundation.click(ReportList.BTN_RUN_REPORT);
			textBox.enterText(MemberPurchaseSummaryReport.TXT_SEARCH,
					rstProductSummaryData.get(CNProductSummary.SHORT_NAME));
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
	@Parameters({ "environment" })
	public void iceReportData(String environment) {
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
			foundation.threadWait(Constants.SHORT_TIME);
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
			foundation.threadWait(Constants.TWO_SECOND);
			locationSummary.updateInventory(rstProductSummaryData.get(CNProductSummary.SCAN_CODE), requiredData.get(0),
					actualData.get(0));
			foundation.threadWait(Constants.TWO_SECOND);
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
			iceReport.processAPI(environment);
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
	@Parameters({ "environment" })
	public void tipSummaryReportData(String environment) {
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
			tipSummary.processAPI(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION), environment);

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
			tipSummary.processAPI(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION), environment);
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
	@Parameters({ "environment" })
	public void itemStockoutReportData(String environment) {
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
			foundation.waitforElementToBeVisible(LocationSummary.INVENTORY_GRID_FIRST_CELL, Constants.MEDIUM_TIME);

			textBox.enterText(LocationSummary.TXT_INVENTORY_FILTER,
					rstProductSummaryData.get(CNProductSummary.SCAN_CODE));

			locationSummary.updateInventory(rstProductSummaryData.get(CNProductSummary.SCAN_CODE), actualData.get(0),
					reason.get(0));

			foundation.waitforElementToBeVisible(LocationSummary.INVENTORY_GRID_FIRST_CELL, Constants.MEDIUM_TIME);
			textBox.enterText(LocationSummary.TXT_INVENTORY_FILTER,
					rstProductSummaryData.get(CNProductSummary.SCAN_CODE));
			locationSummary.updateInventory(rstProductSummaryData.get(CNProductSummary.SCAN_CODE), actualData.get(1),
					reason.get(1));
			String stockout = itemStockout.getStockoutTime(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION),
					rstLocationSummaryData.get(CNLocationSummary.TIME_ZONE));
			// navigate to Reports

			navigationBar.navigateToMenuItem(menu.get(0));

			// Select the Report Date range and Location
			if (environment.equals(Constants.STAGING)) {
				reportList.selectReport(reportName.get(2));
			} else {
				reportList.selectReport(reportName.get(0));
			}
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
			if (environment.equals(Constants.STAGING)) {
				itemStockout.verifyReportName(reportName.get(3));
			} else {
				itemStockout.verifyReportName(reportName.get(1));
			}
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
	@Parameters({ "environment" })
	public void tipDetailsReportData(String environment) {
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
			tipDetails.processAPI(requiredOption.get(0), requiredOption.get(1), environment);

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

			textBox.enterText(TipDetailsReport.TXT_SEARCH,
					String.valueOf((String) tipDetails.getJsonData().get(Reports.TRANS_DATE_TIME)).toUpperCase());
			tipDetails.getTblRecordsUI();
			tipDetails.getIntialData().putAll(tipDetails.getReportsData());
			tipDetails.getRequiredRecord(rstProductSummaryData.get(CNProductSummary.REQUIRED_DATA),
					(String) tipDetails.getJsonData().get(Reports.TRANS_ID));

			// process API
//			tipDetails.processAPI(requiredOption.get(0), requiredOption.get(1));
			foundation.click(ReportList.BTN_RUN_REPORT);
			textBox.enterText(TipDetailsReport.TXT_SEARCH,
					String.valueOf((String) tipDetails.getJsonData().get(Reports.TRANS_DATE_TIME)).toUpperCase());
			tipDetails.getTblRecordsUI();

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
	@Parameters({ "environment" })
	public void healthAheadReportData(String environment) {
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

//			String deviceId = rstProductSummaryData.get(CNProductSummary.DEVICE_ID);
			String deviceId;
			if (environment.equals(Constants.STAGING)) {
				deviceId = propertyFile.readPropertyFile(Configuration.DEVICE_ID_STAGING, FilePath.PROPERTY_CONFIG_FILE);
			} else {
				deviceId = propertyFile.readPropertyFile(Configuration.DEVICE_ID, FilePath.PROPERTY_CONFIG_FILE);
			};

			// process sales API to generate data
			healthAhead.processAPI(rstProductSummaryData.get(CNProductSummary.ACTUAL_DATA),
					rstProductSummaryData.get(CNProductSummary.REQUIRED_DATA), environment);

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
	@Parameters({ "environment" })
	public void canadaMultiTaxReportData(String environment) {
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
			canadaMultiTax.processAPI(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION), environment);

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location
//			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			
			List<String> reportName = Arrays
					.asList(rstReportListData.get(CNReportList.REPORT_NAME).split(Constants.DELIMITER_HASH));
			
			if (environment.equals(Constants.STAGING)) {
				reportList.selectReport(reportName.get(1));
			}else{
				reportList.selectReport(reportName.get(0));
			}
			
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));

			reportList.selectLocation(
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));

			// run and read report
			foundation.click(ReportList.BTN_RUN_REPORT);
			if (environment.equals(Constants.STAGING)) {
				canadaMultiTax.verifyReportName(reportName.get(1));
			} else {
				canadaMultiTax.verifyReportName(reportName.get(0));
			}
//			canadaMultiTax.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));
			
			textBox.enterText(canadaMultiTax.SEARCH_RESULT,
					(String) canadaMultiTax.getJsonData().get(Reports.TRANS_DATE_TIME));
			
			canadaMultiTax.getTblRecordsUI();
			canadaMultiTax.getIntialData().putAll(canadaMultiTax.getReportsData());
//			canadaMultiTax.getRequiredRecord((String) canadaMultiTax.getJsonData().get(Reports.TRANS_DATE_TIME),
//					canadaMultiTax.getScancodeData());
			System.out.println("report" + canadaMultiTax.getIntialData());
			System.out.println("report" + canadaMultiTax.getReportsData());
			
			String deviceId;
			if (environment.equals(Constants.STAGING)) {
				deviceId = propertyFile.readPropertyFile(Configuration.DEVICE_ID_STAGING, FilePath.PROPERTY_CONFIG_FILE);
			} else {
				deviceId = propertyFile.readPropertyFile(Configuration.DEVICE_ID, FilePath.PROPERTY_CONFIG_FILE);
			};

			// apply calculation and update data
			canadaMultiTax.updateData(canadaMultiTax.getTableHeaders().get(0),
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));
			canadaMultiTax.updateData(canadaMultiTax.getTableHeaders().get(1),deviceId.toUpperCase());
			canadaMultiTax.updateData(canadaMultiTax.getTableHeaders().get(2), canadaMultiTax.getCategory1Data());
			canadaMultiTax.updateData(canadaMultiTax.getTableHeaders().get(3), canadaMultiTax.getCategory2Data());
			canadaMultiTax.updateData(canadaMultiTax.getTableHeaders().get(4), canadaMultiTax.getCategory3Data());
			canadaMultiTax.updateData(canadaMultiTax.getTableHeaders().get(5), canadaMultiTax.getProductNameData());
			canadaMultiTax.updateData(canadaMultiTax.getTableHeaders().get(6), canadaMultiTax.getScancodeData());
			canadaMultiTax.updateData_Amount(canadaMultiTax.getTableHeaders().get(7), canadaMultiTax.getPriceData());
			canadaMultiTax.updateData_Amount(canadaMultiTax.getTableHeaders().get(8), canadaMultiTax.getDepositData());
			canadaMultiTax.updateData_Amount(canadaMultiTax.getTableHeaders().get(9), canadaMultiTax.getDiscountData());
			canadaMultiTax.updateTotalPrice();
			canadaMultiTax.updateData(canadaMultiTax.getTableHeaders().get(11), canadaMultiTax.getTaxCatData());
			canadaMultiTax.updateData(canadaMultiTax.getTableHeaders().get(12),
					(String) canadaMultiTax.getJsonData().get(Reports.TAX_1_LABEL));
			canadaMultiTax.updateData_Amount(canadaMultiTax.getTableHeaders().get(13), canadaMultiTax.getTax1Data());
			canadaMultiTax.updateData(canadaMultiTax.getTableHeaders().get(14),
					(String) canadaMultiTax.getJsonData().get(Reports.TAX_2_LABEL));
			canadaMultiTax.updateData_Amount(canadaMultiTax.getTableHeaders().get(15), canadaMultiTax.getTax2Data());
			canadaMultiTax.updateData(canadaMultiTax.getTableHeaders().get(16),
					(String) canadaMultiTax.getJsonData().get(Reports.TAX_3_LABEL));
			canadaMultiTax.updateData_Amount(canadaMultiTax.getTableHeaders().get(17), canadaMultiTax.getTax3Data());
			canadaMultiTax.updateData(canadaMultiTax.getTableHeaders().get(18),
					(String) canadaMultiTax.getJsonData().get(Reports.TAX_4_LABEL));
			canadaMultiTax.updateData_Amount(canadaMultiTax.getTableHeaders().get(19), canadaMultiTax.getTax4Data());
			canadaMultiTax.updateDataForTax(canadaMultiTax.getTableHeaders().get(20),
					(String) canadaMultiTax.getJsonData().get(Reports.TAX));
			
			canadaMultiTax.getTblRecordsUI();

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
	@Parameters({ "environment" })
	public void unfinishedCloseReportData(String environment) {
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
					rstProductSummaryData.get(CNProductSummary.REQUIRED_DATA), environment);

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
	@Parameters({ "environment" })
	public void voidedProductReportData(String environment) {
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
					rstProductSummaryData.get(CNProductSummary.REQUIRED_DATA), environment);

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

			textBox.enterText(tenderTransactionLog.SEARCH_RESULT,
					(String) tenderTransactionLog.getJsonData().get(Reports.TRANS_ID));

			tenderTransactionLog.getTblRecordsUI();
			tenderTransactionLog.getIntialData().putAll(tenderTransactionLog.getReportsData());
//			tenderTransactionLog.getRequiredRecord(
//					(String) tenderTransactionLog.getJsonData().get(Reports.TRANS_DATE_TIME),
//					productTax.getScancodeData());

			tenderTransactionLog.getTblRecordsUI();

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
	@Parameters({ "environment" })
	public void healthAheadPercentageReportData(String environment) {
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
			healthAheadPercentage.processAPI(environment);

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
	@Parameters({ "environment" })
	public void billingInformationReportData(String environment) {
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
			billingInformation.processAPI(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION), environment);

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

			// Re-read report
			billingInformation.getTblRecordsUI();
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
	@Parameters({ "environment" })
	public void personalChargeReportData(String environment) {
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
			personalCharge.processAPI(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION), environment);

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
	@Parameters({ "environment" })
	public void alcoholSoldDetailsReportData(String environment) {
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
			alcoholSoldDetails.processAPI(environment);

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			List<String> reportName = Arrays
					.asList(rstReportListData.get(CNReportList.REPORT_NAME).split(Constants.DELIMITER_HASH));
			
			// Select Menu and Menu Item
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location
			if (environment.equals(Constants.STAGING)) {
				reportList.selectReport(reportName.get(1));
			}else{
				reportList.selectReport(reportName.get(0));
			}
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));

			reportList.selectLocation(
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));

			// run and read report
			foundation.click(ReportList.BTN_RUN_REPORT);
			foundation.waitforElement(ProductTaxReport.LBL_REPORT_NAME, Constants.SHORT_TIME);

			if (environment.equals(Constants.STAGING)) {
				alcoholSoldDetails.verifyReportName(reportName.get(1));
			} else {
				alcoholSoldDetails.verifyReportName(reportName.get(0));
			}
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
//			dropdown.selectItem(CreatePromotions.DPD_PROMO_TYPE, promotionType, Constants.TEXT);
//			foundation.threadWait(Constants.TWO_SECOND);
//			textBox.enterText(CreatePromotions.TXT_PROMO_NAME, promotionName);
//			foundation.click(CreatePromotions.BTN_NEXT);
//			foundation.threadWait(Constants.TWO_SECOND);
//
//			dropdown.selectItem(CreatePromotions.DPD_ORG, orgName, Constants.TEXT);
//			foundation.click(CreatePromotions.BTN_ORG_RIGHT);
//			foundation.threadWait(Constants.TWO_SECOND);
//			dropdown.selectItem(CreatePromotions.DPD_LOCATION, locationName, Constants.TEXT);
//			foundation.click(CreatePromotions.BTN_LOC_RIGHT);
//
//			foundation.threadWait(Constants.TWO_SECOND);

			createPromotions.newPromotionUsingTenderDiscount(promotionType, promotionName, orgName, locationName);
//			foundation.click(CreatePromotions.BTN_NEXT);
			List<String> requiredData = Arrays
					.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));

			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.waitforElement(CreatePromotions.MULTI_SELECT_TENDER_TYPES, Constants.SHORT_TIME);

			foundation.waitforElement(CreatePromotions.BTN_NEXT, Constants.THREE_SECOND);
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

			// ==============
			// creation of promotion
//			foundation.click(PromotionList.BTN_CREATE);
//			foundation.isDisplayed(CreatePromotions.LBL_CREATE_PROMOTION);
//			dropdown.selectItem(CreatePromotions.DPD_PROMO_TYPE, promotionType, Constants.TEXT);
//			foundation.threadWait(Constants.TWO_SECOND);
//			textBox.enterText(CreatePromotions.TXT_PROMO_NAME, promotionName);
//			foundation.click(CreatePromotions.BTN_NEXT);
//			foundation.threadWait(Constants.TWO_SECOND);
//
//			dropdown.selectItem(CreatePromotions.DPD_ORG, orgName, Constants.TEXT);
//			foundation.click(CreatePromotions.BTN_ORG_RIGHT);
//			foundation.threadWait(Constants.TWO_SECOND);
//			dropdown.selectItem(CreatePromotions.DPD_LOCATION, locationName, Constants.TEXT);
//			foundation.click(CreatePromotions.BTN_LOC_RIGHT);
//
//			foundation.threadWait(Constants.TWO_SECOND);
//			foundation.click(CreatePromotions.BTN_NEXT);
//			List<String> requiredData = Arrays
//					.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
//
//			dropdown.selectItem(CreatePromotions.MULTI_SELECT_TENDER_TYPES, requiredData.get(0), Constants.TEXT);
//			dropdown.selectItem(CreatePromotions.DPD_DISCOUNT_TYPE, requiredData.get(1), Constants.TEXT);
//			dropdown.selectItem(CreatePromotions.DPD_APPLY_DISCOUNT_TO, requiredData.get(2), Constants.TEXT);
//			textBox.enterText(CreatePromotions.TXT_AMOUNT, requiredData.get(3));
//			textBox.enterText(CreatePromotions.TXT_TRANSACTION_MIN, requiredData.get(4));
//			dropdown.selectItem(CreatePromotions.DPD_DISCOUNT_TIME, requiredData.get(5), Constants.TEXT);
//			foundation.click(CreatePromotions.CHK_SUNDAY);
//
//			foundation.threadWait(Constants.TWO_SECOND);
//			foundation.click(CreatePromotions.BTN_NEXT);
//			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);
//			foundation.threadWait(Constants.SHORT_TIME);
//			foundation.click(CreatePromotions.BTN_OK);
//			foundation.threadWait(Constants.TWO_SECOND);

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
			cashFlow.calculateAmount(cashFlow.getTableHeaders().get(7), cashFlow.getRequiredJsonData().get(0));
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

	@Test(priority = 4, description = "168025-Verify the All Redeemed Promtions for Tender type of Promtion Analysis")
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
//			dropdown.selectItem(CreatePromotions.DPD_PROMO_TYPE, promotionType, Constants.TEXT);
//			foundation.threadWait(Constants.TWO_SECOND);
//			textBox.enterText(CreatePromotions.TXT_PROMO_NAME, promotionName);
//			foundation.click(CreatePromotions.BTN_NEXT);
//			foundation.threadWait(Constants.TWO_SECOND);
//
//			dropdown.selectItem(CreatePromotions.DPD_ORG, orgName, Constants.TEXT);
//			foundation.click(CreatePromotions.BTN_ORG_RIGHT);
//			foundation.threadWait(Constants.TWO_SECOND);
//			dropdown.selectItem(CreatePromotions.DPD_LOCATION, locationName, Constants.TEXT);
//			foundation.click(CreatePromotions.BTN_LOC_RIGHT);
//
//			foundation.threadWait(Constants.TWO_SECOND);
//			foundation.click(CreatePromotions.BTN_NEXT);

//			createPromotions.newPromotionUsingTenderDiscount(promotionType, promotionName,orgName, locationName);
////			foundation.click(CreatePromotions.BTN_NEXT);
//			List<String> requiredData = Arrays
//					.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
//
//			foundation.click(CreatePromotions.BTN_NEXT);
//			foundation.waitforElement(CreatePromotions.MULTI_SELECT_TENDER_TYPES, Constants.SHORT_TIME);
//			
////			foundation.waitforElement(CreatePromotions.BTN_NEXT, Constants.THREE_SECOND);
//			dropdown.selectItem(CreatePromotions.MULTI_SELECT_TENDER_TYPES, requiredData.get(0), Constants.TEXT);

//			foundation.click(CreatePromotions.BTN_NEXT);
//			foundation.waitforElement(CreatePromotions.MULTI_SELECT_TENDER_TYPES, Constants.SHORT_TIME);
//			
//			foundation.waitforElement(CreatePromotions.BTN_NEXT, Constants.THREE_SECOND);
//			List<String> requiredData = Arrays
//					.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
//
//			dropdown.selectAllItems(CreatePromotions.MULTI_SELECT_TENDER_TYPES);

//			dropdown.selectItem(CreatePromotions.DPD_DISCOUNT_BY, requiredData.get(1), Constants.TEXT);
//			foundation.click(CreatePromotions.ADD_ITEM);
//			foundation.click(CreatePromotions.ITEM_BUNDLE_ALL_CHECKBOX);
////			textBox.enterText(CreatePromotions.ITEM_SEARCH_TXT, requiredData.get(2));
//			foundation.threadWait(Constants.SHORT_TIME);
////			checkBox.check(CreatePromotions.SELECT_ITEM_PRODUCT);
//			foundation.waitforElementToBeVisible(CreatePromotions.BTN_CANCEL_ITEM_POPUP, Constants.THREE_SECOND);
//			foundation.click(CreatePromotions.BTN_CANCEL_ITEM_POPUP);

//			dropdown.selectItem(CreatePromotions.DPD_DISCOUNT_TYPE, requiredData.get(0), Constants.TEXT);
//			dropdown.selectItem(CreatePromotions.DPD_APPLY_DISCOUNT_TO, requiredData.get(1), Constants.TEXT);
//			textBox.enterText(CreatePromotions.TXT_AMOUNT, requiredData.get(2));
//			textBox.enterText(CreatePromotions.TXT_TRANSACTION_MIN, requiredData.get(3));
//			dropdown.selectItem(CreatePromotions.DPD_DISCOUNT_TIME, requiredData.get(4), Constants.TEXT);
//			createPromotions.selectWeekDays(weekDays);
//
//			foundation.threadWait(Constants.TWO_SECOND);
//			foundation.click(CreatePromotions.BTN_NEXT);
//			foundation.waitforElement(CreatePromotions.BTN_OK, Constants.SHORT_TIME);
//			foundation.threadWait(Constants.SHORT_TIME);
//			foundation.click(CreatePromotions.BTN_OK);
//			foundation.threadWait(Constants.SHORT_TIME);

			createPromotions.newPromotionUsingTenderDiscount(promotionType, promotionName, orgName, locationName);
//			foundation.click(CreatePromotions.BTN_NEXT);
			List<String> requiredData = Arrays
					.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));

			foundation.click(CreatePromotions.BTN_NEXT);
			foundation.waitforElement(CreatePromotions.MULTI_SELECT_TENDER_TYPES, Constants.SHORT_TIME);

			foundation.waitforElement(CreatePromotions.BTN_NEXT, Constants.THREE_SECOND);
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
			promotionAnalysis.updateData(promotionAnalysis.getTableHeaders().get(13), expectedData.get(5));

			promotionAnalysis.PromotionExpectedDataGroupbyLocations();
			promotionAnalysis.updateDataGroupbyLocations(promotionAnalysis.getTableHeaders().get(12),
					expectedData.get(4));
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

			foundation.waitforElement(Payments.EMAIL_LOGIN_TXT, Constants.ONE_SECOND);
			foundation.click(Payments.EMAIL_LOGIN_TXT);
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
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
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
//			promotionAnalysis.getIntialData().putAll(promotionAnalysis.getReportsData());
			foundation.threadWait(Constants.ONE_SECOND);
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
//			promotionAnalysis.getIntialData().putAll(promotionAnalysis.getReportsData());
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

	@Test(priority = 3, description = "168026-Verify the All Redeemed Promtions for On-Screen type of Promtion Analysis")
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

		System.out.println("promotionName : " + promotionName);

		List<String> requiredData = Arrays
				.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
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

//			foundation.waitforElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);

//			foundation.click(CreatePromotions.BTN_NEXT);

			dropdown.selectItem(CreatePromotions.DPD_DISCOUNT_BY, requiredData.get(1), Constants.TEXT);
			foundation.click(CreatePromotions.ADD_ITEM);
			textBox.enterText(CreatePromotions.ITEM_SEARCH_TXT, requiredData.get(2));
			foundation.threadWait(Constants.SHORT_TIME);
			checkBox.check(CreatePromotions.SELECT_ITEM_PRODUCT);
			foundation.waitforElementToBeVisible(CreatePromotions.BTN_CANCEL_ITEM_POPUP, Constants.THREE_SECOND);
			foundation.click(CreatePromotions.BTN_CANCEL_ITEM_POPUP);

			// --------
//			dropdown.selectItem(CreatePromotions.DPD_DISCOUNT_BY, requiredData.get(1), Constants.TEXT);
//			textBox.enterText(CreatePromotions.TXT_ITEM, requiredData.get(2));
//			foundation.threadWait(Constants.ONE_SECOND);
//			textBox.enterText(CreatePromotions.TXT_ITEM, Keys.ENTER);
////			foundation.click(CreatePromotions.ALL_ITEMS);

			foundation.threadWait(Constants.TWO_SECOND);
//			String actualValue = dropdown.getSelectedItem(CreatePromotions.DPD_ITEM_SELECT);
//			CustomisedAssert.assertEquals(actualValue, requiredData.get(2));

			// ----

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

			foundation.waitforElement(Payments.EMAIL_LOGIN_TXT, Constants.ONE_SECOND);
			foundation.click(Payments.EMAIL_LOGIN_TXT);
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
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
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
			foundation.waitforElement(Payments.EMAIL_LOGIN_TXT, Constants.ONE_SECOND);
			foundation.click(Payments.EMAIL_LOGIN_TXT);
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
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
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

	@Test(priority = 2, description = "177341-Verify the Duplicate Sales Itmes in Sales Items Details Report after Redeeming a promotion")
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
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
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

			dropdown.selectItem(CreatePromotions.DPD_DISCOUNT_BY, requiredData.get(1), Constants.TEXT);
			foundation.click(CreatePromotions.ADD_ITEM);
			textBox.enterText(CreatePromotions.ITEM_SEARCH_TXT, requiredData.get(2));
			foundation.threadWait(Constants.SHORT_TIME);
			checkBox.check(CreatePromotions.SELECT_ITEM_PRODUCT);
			foundation.waitforElementToBeVisible(CreatePromotions.BTN_CANCEL_ITEM_POPUP, Constants.THREE_SECOND);
			foundation.click(CreatePromotions.BTN_CANCEL_ITEM_POPUP);

//			foundation.waitforElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);
//
//			foundation.click(CreatePromotions.BTN_NEXT);
//			dropdown.selectItem(CreatePromotions.DPD_DISCOUNT_BY, requiredData.get(1), Constants.TEXT);
//			textBox.enterText(CreatePromotions.TXT_ITEM, requiredData.get(2));
//			foundation.threadWait(Constants.ONE_SECOND);
//			textBox.enterText(CreatePromotions.TXT_ITEM, Keys.ENTER);
////			foundation.click(CreatePromotions.ALL_ITEMS);

			foundation.threadWait(Constants.TWO_SECOND);
//			String actualValue = dropdown.getSelectedItem(CreatePromotions.DPD_ITEM_SELECT);
//			CustomisedAssert.assertEquals(actualValue, requiredData.get(2));

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
			CustomisedAssert.assertTrue(foundation.isDisplayed(Order.BTN_CANCEL_ORDER));
			foundation.click(Payments.ACCOUNT_EMAIL);
			foundation.waitforElement(Payments.EMAIL_lOGIN_BTN, Constants.ONE_SECOND);
			foundation.click(Payments.EMAIL_lOGIN_BTN);
			foundation.waitforElement(Payments.EMAIL_LOGIN_TXT, Constants.ONE_SECOND);
			foundation.click(Payments.EMAIL_LOGIN_TXT);
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
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
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
			foundation.threadWait(Constants.TWO_SECOND);
			locationSummary.updateInventory(rstProductSummaryData.get(CNProductSummary.SCAN_CODE), requiredData.get(0),
					requiredData.get(3));

			foundation.threadWait(Constants.FIFTY_FIVE_SECONDS);

			String updatedTime = String
					.valueOf(dateAndTime.getDateAndTime1(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION),
							rstLocationSummaryData.get(CNLocationSummary.TIME_ZONE)));

			textBox.enterText(LocationSummary.TXT_INVENTORY_FILTER,
					rstProductSummaryData.get(CNProductSummary.SCAN_CODE));
//			foundation.threadWait(Constants.ONE_SECOND);

			locationSummary.updateInventory(rstProductSummaryData.get(CNProductSummary.SCAN_CODE), requiredData.get(1),
					requiredData.get(2));

			// Updating the Inventory of the product
//			String updatedTime =locationSummary.updateInventoryWithTimeOfTransacction(rstProductSummaryData.get(CNProductSummary.SCAN_CODE), requiredData.get(1),
//					requiredData.get(2), rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION),
//					rstLocationSummaryData.get(CNLocationSummary.TIME_ZONE));
			System.out.println("updatedTime :" + updatedTime);

//			String updatedTime = String
//					.valueOf(dateAndTime.getDateAndTime1(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION),
//							rstLocationSummaryData.get(CNLocationSummary.TIME_ZONE)));

			// navigate to Reports
			navigationBar.navigateToMenuItem(menu.get(1));

			// Select the Report Date range and Location
			reportList.selectReport(reportName);
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));
			reportList.selectLocation(
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(ReportList.BTN_RUN_REPORT);
			foundation.waitforElement(InventoryAdjustmentDetail.LBL_REPORT_NAME, Constants.SHORT_TIME);
			inventoryAdjustmentDetail.verifyReportName(reportName);
			textBox.enterText(InventoryAdjustmentDetail.TXT_SEARCH, String.valueOf(updatedTime.toUpperCase()));
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
			foundation.threadWait(Constants.ONE_SECOND);

			locationSummary.updateInventory(rstProductSummaryData.get(CNProductSummary.SCAN_CODE), requiredData.get(0),
					requiredData.get(3));

			foundation.threadWait(Constants.FIFTY_FIVE_SECONDS);

			textBox.enterText(LocationSummary.TXT_INVENTORY_FILTER,
					rstProductSummaryData.get(CNProductSummary.SCAN_CODE));
			foundation.threadWait(Constants.ONE_SECOND);

			// Updating the Inventory of the product
			locationSummary.updateInventory(rstProductSummaryData.get(CNProductSummary.SCAN_CODE), requiredData.get(1),
					requiredData.get(2));

			// navigate to Reports
			navigationBar.navigateToMenuItem(menu.get(1));

			// Select the Report Date range and Location
			reportList.selectReport(reportName);
			foundation.click(ReportList.BTN_RUN_REPORT);
			foundation.waitforElement(InventoryTotals.LBL_REPORT_NAME, Constants.SHORT_TIME);
			inventoryTotals.verifyReportName(reportName);
			textBox.enterText(InventoryTotals.TXT_SEARCH, rstProductSummaryData.get(CNProductSummary.PRODUCT_NAME));
			inventoryTotals.getTblRecordsUI();

			// Validating the Headers and Report data
			inventoryTotals.verifyReportHeaders(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME));
			inventoryTotals.verifyReportData(rstProductSummaryData.get(CNProductSummary.ACTUAL_DATA));

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
			foundation.waitforElement(RemainingGuestPassLiability.LBL_REPORT_NAME, Constants.SHORT_TIME);
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
			foundation.waitforElement(GuestPassByDevice.LBL_REPORT_NAME, Constants.SHORT_TIME);
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
			foundation.threadWait(Constants.TWO_SECOND);
			reportList.selectDateRangeDateofType2(locationData.get(1), InventoryVariance.DATA_EXISTING_DATE_STAGING,
					InventoryVariance.DATA_EXISTING_DATE_STAGING);
			foundation.click(ReportList.BTN_RUN_REPORT);
			foundation.waitforElement(InventoryVariance.LBL_REPORT_NAME, Constants.SHORT_TIME);
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
			foundation.waitforElement(InventoryList.LBL_REPORT_NAME, Constants.SHORT_TIME);
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
	 * This Method is for Consumer Feedback Survey Report Data Validation For Happy
	 * Feedback
	 * 
	 * @author ravindhara Date: 11-07-2022
	 */
	@Test(priority = 1, description = "197794-Verifying and Validating the Consumer Feedback Survey Report Data For Happy Feedback")
	public void consumerFeedbackSurveyReportDataValidationForHappyFeedback() {
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
			foundation.threadWait(Constants.SHORT_TIME);
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

			foundation.threadWait(Constants.SHORT_TIME);

			consumerFeedbackSurvey.getTblRecordsUI();
			consumerFeedbackSurvey.getIntialData().putAll(consumerFeedbackSurvey.getReportsData());

			// apply calculation and update data
			consumerFeedbackSurvey.updateData(consumerFeedbackSurvey.getTableHeaders().get(1),
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));
			consumerFeedbackSurvey.updateTotalFeedbackCount(consumerFeedbackSurvey.getTableHeaders().get(3));
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

			// Navigate to Reports
			foundation.threadWait(Constants.SHORT_TIME);
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

			foundation.threadWait(Constants.SHORT_TIME);

			consumerFeedbackSurvey.getTblRecordsUI();

			// verify report headers
			consumerFeedbackSurvey.verifyReportHeaders(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME));

			// verify report data
			consumerFeedbackSurvey.verifyReportData();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
//		} finally {
//			// navigate to location and disabling of Consumer Feedback Survey
//			navigationBar.navigateToMenuItem(menuItems.get(0));
//			locationList.selectLocationName(
//					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));
//			foundation.click(ConsumerFeedbackSurvey.LOCATION_TOGGLEINFO_BAR);
//			foundation.threadWait(Constants.ONE_SECOND);
//			dropdown.selectItem(ConsumerFeedbackSurvey.CONSUMER_FEEDBACK_SUMMARY_ENABLE_DD, requiredData.get(1),
//					Constants.TEXT);
//			foundation.click(ConsumerFeedbackSurvey.LOCATION_SUMMARY_SAVE_BUTTON);
//			foundation.waitforElement(ConsumerFeedbackSurvey.TXT_SPINNER_MSG, Constants.SHORT_TIME);
		}
	}

	/**
	 * This Method is for Intl Web/App Funding Report Data Validation
	 * 
	 * @author ravindhara Date: 13-07-2022
	 */
	@Test(description = "197820-This test validates Intl Web/App Funding Report Data Validation")
	@Parameters({ "environment" })
	public void intlWebAppFundingReportDataValidation(String environment) {
		try {
			final String CASE_NUM = "197820";
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
			List<String> expectedData = Arrays
					.asList(rstProductSummaryData.get(CNProductSummary.ACTUAL_DATA).split(Constants.DELIMITER_TILD));
			List<String> requiredData = Arrays
					.asList(rstProductSummaryData.get(CNProductSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> requiredDataForTest4 = Arrays.asList(requiredData.get(0).split(Constants.DELIMITER_HASH));
			List<String> requiredDataForStaging = Arrays.asList(requiredData.get(1).split(Constants.DELIMITER_HASH));

			String menu = rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM);

			if (environment.equals(Constants.STAGING)) {
				navigationBar.selectOrganization(requiredDataForStaging.get(0));
				// navigate to Reports
				navigationBar.navigateToMenuItem(menu);

				// Select the Report Date range and Location
				reportList.selectReport(reportName);
				reportList.selectDateRangeDate(rstReportListData.get(CNReportList.DATE_RANGE),
						requiredDataForStaging.get(2), IntlWebAppFunding.DATA_EXISTING_DATE_STAGING,
						IntlWebAppFunding.DATA_EXISTING_DATE_STAGING);
				reportList.selectLocation(requiredDataForStaging.get(1));
			} else {
				navigationBar.selectOrganization(requiredDataForTest4.get(0));
				// navigate to Reports
				navigationBar.navigateToMenuItem(menu);

				// Select the Report Date range and Location
				reportList.selectReport(reportName);
				reportList.selectDateRangeDate(rstReportListData.get(CNReportList.DATE_RANGE),
						requiredDataForTest4.get(2), IntlWebAppFunding.DATA_EXISTING_DATE,
						IntlWebAppFunding.DATA_EXISTING_DATE);
				reportList.selectLocation(requiredDataForTest4.get(1));
			}
			foundation.click(ReportList.BTN_RUN_REPORT);
			foundation.waitforElement(IntlWebAppFunding.LBL_REPORT_NAME, Constants.SHORT_TIME);
			intlWebAppFunding.verifyReportName(reportName);
			intlWebAppFunding.getTblRecordsUI();

			// Validating the Headers and Report data
			intlWebAppFunding.verifyReportHeaders(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME));
			
			if (environment.equals(Constants.STAGING)) {
				intlWebAppFunding.verifyReportData(expectedData.get(1));
			}else{
				intlWebAppFunding.verifyReportData(expectedData.get(0));
			}

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/*
	 * This Method is for Daily Sales Summary Report Data Validation
	 * 
	 * @author ravindhara Date: 22-07-2022
	 * 
	 */
	@Test(description = "198560-Verify the Data Validation of Daily Sales Summary Report")
	@Parameters({ "environment" })
	public void dailySalesSummaryReportDataValication(String environment) {
		try {
			final String CASE_NUM = "198560";
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			String locationName = propertyFile.readPropertyFile(Configuration.CURRENT_LOC,
					FilePath.PROPERTY_CONFIG_FILE);

			// process sales API to generate data
			dailySalesSummary.processAPI(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION), environment);

			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location and run report
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));
			reportList.selectLocation(locationName);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(ReportList.BTN_RUN_REPORT);
			foundation.waitforElement(DailySalesSummary.LBL_REPORT_NAME, Constants.SHORT_TIME);
			dailySalesSummary.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));

			// Read the Report the Data
			dailySalesSummary.getTblRecordsUI();
			dailySalesSummary.getIntialData().putAll(dailySalesSummary.getReportsData());

			// process sales API to generate data
			dailySalesSummary.processAPI(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION), environment);

			// rerun and reread report
			foundation.click(ReportList.BTN_RUN_REPORT);
			foundation.threadWait(Constants.TWO_SECOND);
			dailySalesSummary.getTblRecordsUI();

			// update the report date based on calculation
			String date = String
					.valueOf(dateAndTime.getDateAndTime(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION),
							rstProductSummaryData.get(CNProductSummary.REQUIRED_DATA)));
			String productPrice = rstProductSummaryData.get(CNProductSummary.PRICE);
			String tax = rstProductSummaryData.get(CNProductSummary.TAX);
			String deposit = rstProductSummaryData.get(CNProductSummary.DEPOSIT_CATEGORY);
			String discount = rstProductSummaryData.get(CNProductSummary.DISCOUNT);

			dailySalesSummary.updateData(dailySalesSummary.getTableHeaders().get(0), date);
			dailySalesSummary.updateData(dailySalesSummary.getTableHeaders().get(1), locationName);
			dailySalesSummary.TrasactionCount(dailySalesSummary.getTableHeaders().get(2));
			dailySalesSummary.itemCount(dailySalesSummary.getTableHeaders().get(3));
			dailySalesSummary.calculateSales(dailySalesSummary.getTableHeaders().get(4), productPrice, deposit,
					discount);
			dailySalesSummary.calculateAmount(dailySalesSummary.getTableHeaders().get(5), tax);
			dailySalesSummary.calculateAmount(dailySalesSummary.getTableHeaders().get(6), deposit);
			dailySalesSummary.calculateAmount(dailySalesSummary.getTableHeaders().get(7), discount);
			dailySalesSummary.totalAmount(dailySalesSummary.getTableHeaders().get(8), productPrice, tax, deposit,
					discount);

			// verify report headers
			dailySalesSummary.verifyReportHeaders(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME));

			// verify report data
			dailySalesSummary.verifyReportData();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * This Method is for Product Sales Report Data Validation
	 * 
	 * @author ravindhara Date: 22-07-2022
	 */
	@Test(description = "198531-Verify the Data Validation of Product Sales Report")
	public void ProductSalesReportDataValidation() {
		try {
			final String CASE_NUM = "198531";
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
			productSales.processAPI(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));

			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

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

			// update the report date based on calculation
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
			productSales.verifyReportHeaders(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME));

			// verify report data
			productSales.verifyReportData();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * Sold Details Report Data Validation
	 * 
	 * @author KarthikR
	 * @date: 28-07-2022
	 */
	@Test(description = "198561 - Sold Details Report data validation")
	public void soldDetailsReportDataValidation() {
		final String CASE_NUM = "198561";

		// Reading Test Data from DB
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);
		rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

		List<String> menu = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> columns = Arrays
				.asList(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME).split(Constants.DELIMITER_HASH));
		String location = propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE);
		try {
			// Navigate to ADM and Select Org
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// process sales API to generate data
			String date = soldDetails.processAPI(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));

			navigationBar.navigateToMenuItem(menu.get(1));
			reportList.selectDateTransactionSearch(rstReportListData.get(CNReportList.DATE_RANGE));
			reportList.selectLocationForTransactionSearch(location);
			foundation.click(SoldDetails.FIND_TRANSACTION);
			foundation.waitforElementToBeVisible(SoldDetails.TXT_SEARCH_TRANSACTION, Constants.LONG_TIME);

			textBox.enterText(SoldDetails.TXT_SEARCH_TRANSACTION, date);
			foundation.threadWait(Constants.LONG_TIME);
			String txnId = foundation.getText(SoldDetails.TXT_ID_TRANSACTION);

			// Navigate to Report Tab
			navigationBar.navigateToMenuItem(menu.get(0));

			// Select the Report Date range and Location and run report
			soldDetails.selectAndRunReport(rstReportListData.get(CNReportList.REPORT_NAME),
					rstReportListData.get(CNReportList.DATE_RANGE), location);

			// Search with Transaction ID and get the data
			textBox.enterText(SoldDetails.TXT_SEARCH_FILTER, txnId);

			// Read the Report the Data and validate it
			soldDetails.getTblRecordsUI();
			soldDetails.verifyReportHeaders(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME));
			soldDetails.verifyCommonValueContentofTableRecord(columns.get(0), location);
			soldDetails.verifyCommonValueContentofTableRecord(columns.get(2), txnId);
			soldDetails.verifyCommonValueContentofTableRecord(columns.get(13),
					rstProductSummaryData.get(CNProductSummary.PRICE));
			soldDetails.verifyCommonValueContentofTableRecord(columns.get(14),
					rstProductSummaryData.get(CNProductSummary.TAX));
			soldDetails.verifyDifferentValueContentofTableRecord(columns.get(5),
					rstProductSummaryData.get(CNProductSummary.PRODUCT_NAME));
			soldDetails.verifyDifferentValueContentofTableRecord(columns.get(6),
					rstProductSummaryData.get(CNProductSummary.SCAN_CODE));
			soldDetails.verifyDifferentValueContentofTableRecord(columns.get(7),
					rstProductSummaryData.get(CNProductSummary.USER_KEY));
			soldDetails.verifyDifferentValueContentofTableRecord(columns.get(21),
					rstProductSummaryData.get(CNProductSummary.COST));
			List<String> cost = Arrays
					.asList(rstProductSummaryData.get(CNProductSummary.COST).split(Constants.DELIMITER_HASH));

			// Calculation of Total Price
			Double totalPrice = Double.parseDouble(rstProductSummaryData.get(CNProductSummary.PRICE))
					+ Double.parseDouble(rstProductSummaryData.get(CNProductSummary.TAX));
			soldDetails.verifyCommonValueContentofTableRecord(columns.get(20), String.valueOf(totalPrice));

			// Calculation of Margin and verify Data
			String margin = soldDetails.calculateMargin(cost, totalPrice);
			soldDetails.verifyDifferentValueContentofTableRecord(columns.get(22), margin);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/*
	 * This Method is for Daily Sales Summary Report Data Validation
	 * 
	 * @author ravindhara Date: 22-07-2022
	 * 
	 */
	@Test(priority = 1, description = "202034-Verify the Data Validation of Daily Sales Summary Report")
	public void salesSummaryAndCostReportDataValication() {
		try {
			final String CASE_NUM = "202034";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			String locationName = propertyFile.readPropertyFile(Configuration.CURRENT_LOC,
					FilePath.PROPERTY_CONFIG_FILE);

			// process sales API to generate data
			salesSummaryAndCost.processAPI(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));

			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location and run report
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));
			reportList.selectLocation(locationName);
			foundation.threadWait(Constants.TWO_SECOND);
			foundation.click(ReportList.BTN_RUN_REPORT);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.waitforElement(SalesSummaryAndCost.LBL_REPORT_NAME, Constants.SHORT_TIME);
			salesSummaryAndCost.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));

			// Read the Report the Data
			salesSummaryAndCost.getTblRecordsUI();
			salesSummaryAndCost.getIntialData().putAll(salesSummaryAndCost.getReportsData());

			// process sales API to generate data
			salesSummaryAndCost.processAPI(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));

			// rerun and reread report
			foundation.click(ReportList.BTN_RUN_REPORT);
			foundation.threadWait(Constants.TWO_SECOND);
			salesSummaryAndCost.getTblRecordsUI();

			// update the report date based on calculation
			String productPrice = rstProductSummaryData.get(CNProductSummary.PRICE);
			String tax = rstProductSummaryData.get(CNProductSummary.TAX);
			String deposit = rstProductSummaryData.get(CNProductSummary.DEPOSIT_CATEGORY);
			String discount = rstProductSummaryData.get(CNProductSummary.DISCOUNT);
			String cost = rstProductSummaryData.get(CNProductSummary.COST);

			salesSummaryAndCost.updateData(salesSummaryAndCost.getTableHeaders().get(0), locationName);
			salesSummaryAndCost.calculateAmount(salesSummaryAndCost.getTableHeaders().get(1), productPrice);
			salesSummaryAndCost.calculateAmount(salesSummaryAndCost.getTableHeaders().get(2), tax);
			salesSummaryAndCost.calculateAmount(salesSummaryAndCost.getTableHeaders().get(3), deposit);
			salesSummaryAndCost.calculateAmount(salesSummaryAndCost.getTableHeaders().get(4), discount);
			Double totalAmount = salesSummaryAndCost.totalAmount(salesSummaryAndCost.getTableHeaders().get(5),
					productPrice, tax, deposit, discount);
			String totalCost = salesSummaryAndCost.calculateCost(salesSummaryAndCost.getTableHeaders().get(6), cost);
//			salesSummaryAndCost.calculateGrossMargin(salesSummaryAndCost.getTableHeaders().get(7));
			salesSummaryAndCost.TrasactionCount(salesSummaryAndCost.getTableHeaders().get(8));
			salesSummaryAndCost.itemCount(salesSummaryAndCost.getTableHeaders().get(9));

			// verify report headers
			salesSummaryAndCost.verifyReportHeaders(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME));

			// verify report data
			salesSummaryAndCost.verifyReportData();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * This Method is for Product Sales Report Data Validation
	 * 
	 * @author ravindhara Date:29-07-2022
	 */

	@Test(description = "198562-Verify the Data Validation of Sales Item Details Report")
	public void salesItemDetailsReportDataValidation() {
		try {
			final String CASE_NUM = "198562";
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			List<String> menuItems = Arrays
					.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
			String location = propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE);

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// process sales API to generate data
			String date = salesItemDetailsReport
					.processAPI(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));

			String txnId = (String) salesItemDetailsReport.getJsonData().get(Reports.TRANS_ID);

			navigationBar.navigateToMenuItem(menuItems.get(1));
			// Select the Report Date range and Location and run report
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));
			reportList.selectLocationForSecondTypeDropdown(location);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(ReportList.BTN_RUN_REPORT);
			foundation.waitforElement(ProductSales.LBL_REPORT_NAME, Constants.SHORT_TIME);
			salesItemDetailsReport.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));

			// Read the Report the Data
			textBox.enterText(SalesItemDetailsReport.TXT_SEARCH, txnId);
			salesItemDetailsReport.getTblRecordsUI();
			salesItemDetailsReport.getIntialData().putAll(salesItemDetailsReport.getReportsData());

			// update the report date based on calculation
			List<String> requiredData = Arrays
					.asList(rstProductSummaryData.get(CNProductSummary.REQUIRED_DATA).split(Constants.DELIMITER_HASH));
			String deviceId = rstProductSummaryData.get(CNProductSummary.DEVICE_ID);
			String productPrice = rstProductSummaryData.get(CNProductSummary.PRICE);
			String tax = rstProductSummaryData.get(CNProductSummary.TAX);
			String productName = rstProductSummaryData.get(CNProductSummary.PRODUCT_NAME);
			String scanCode = rstProductSummaryData.get(CNProductSummary.SCAN_CODE);
			String cat1 = rstProductSummaryData.get(CNProductSummary.CATEGORY1);
			String cat2 = rstProductSummaryData.get(CNProductSummary.CATEGORY2);
			String cat3 = rstProductSummaryData.get(CNProductSummary.CATEGORY3);
			String productType = rstProductSummaryData.get(CNProductSummary.PRODUCT_TYPE);

			salesItemDetailsReport.updateData(salesItemDetailsReport.getTableHeaders().get(0), location);
			salesItemDetailsReport.updateData(salesItemDetailsReport.getTableHeaders().get(1), deviceId);
			salesItemDetailsReport.updateData(salesItemDetailsReport.getTableHeaders().get(3), requiredData.get(0));
			salesItemDetailsReport.updateData(salesItemDetailsReport.getTableHeaders().get(4), txnId);
			salesItemDetailsReport.updateMultiData(salesItemDetailsReport.getTableHeaders().get(5), productName);
			salesItemDetailsReport.updateMultiData(salesItemDetailsReport.getTableHeaders().get(6), scanCode);
			salesItemDetailsReport.updateData(salesItemDetailsReport.getTableHeaders().get(7), cat1);
			salesItemDetailsReport.updateData(salesItemDetailsReport.getTableHeaders().get(8), cat2);
			salesItemDetailsReport.updateData(salesItemDetailsReport.getTableHeaders().get(9), cat3);
			salesItemDetailsReport.updateMultiData(salesItemDetailsReport.getTableHeaders().get(10), productType);
			salesItemDetailsReport.calculateAmount(salesItemDetailsReport.getTableHeaders().get(11), productPrice, tax);
			salesItemDetailsReport.updateData(salesItemDetailsReport.getTableHeaders().get(12), requiredData.get(1));
			salesItemDetailsReport.updateData(salesItemDetailsReport.getTableHeaders().get(13),
					(String) salesItemDetailsReport.getJsonData().get(Reports.TRANS_DATE_TIME));
			salesItemDetailsReport.updateData(salesItemDetailsReport.getTableHeaders().get(14), requiredData.get(2));

			salesItemDetailsReport.getTblRecordsUI();

			salesItemDetailsReport.verifyReportHeaders(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME));

			// verify report data
			salesItemDetailsReport.verifyReportData();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/*
	 * This Method is for Sales Time Details Report data validation
	 * 
	 * @author ravindhara Date: 17-08-2022
	 * 
	 */
	@Test(description = "202038-Verify the Data Validation of Sales Time Details Report")
	public void SalesTimeDetailsReportDataValidation() {
		try {
			final String CASE_NUM = "202038";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			String locationName = propertyFile.readPropertyFile(Configuration.CURRENT_LOC,
					FilePath.PROPERTY_CONFIG_FILE);

			// process sales API to generate data
			salesTimeDetailsReport.processAPI(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));

			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location and run report
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));
			reportList.selectLocation(locationName);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(ReportList.BTN_RUN_REPORT);
			foundation.waitforElement(SalesSummaryAndCost.LBL_REPORT_NAME, Constants.SHORT_TIME);
			salesTimeDetailsReport.verifyReportName(locationName);

			// Read the Report the Data
			salesTimeDetailsReport.getTblRecordsUI();
			salesTimeDetailsReport.getIntialData().putAll(salesTimeDetailsReport.getReportsData());
			salesTimeDetailsReport.getUpdatedTableFooters().putAll(salesTimeDetailsReport.getTableFooters());

			// process sales API to generate data
			salesTimeDetailsReport.processAPI(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));

			// rerun and reread report
			foundation.click(ReportList.BTN_RUN_REPORT);
			foundation.threadWait(Constants.TWO_SECOND);

			salesTimeDetailsReport.getTblRecordsUI();

			salesTimeDetailsReport
					.decideTimeRange((String) salesTimeDetailsReport.getJsonData().get(Reports.TRANS_DATE));

			// update the report date based on calculation
			String productPrice = rstProductSummaryData.get(CNProductSummary.PRICE);
			String tax = rstProductSummaryData.get(CNProductSummary.TAX);
			String discount = rstProductSummaryData.get(CNProductSummary.DISCOUNT);

			// Updating Table data
			salesTimeDetailsReport.TrasactionCount(salesTimeDetailsReport.getTableHeaders().get(1));
			salesTimeDetailsReport.calculateAmount(salesTimeDetailsReport.getTableHeaders().get(2), productPrice);
			salesTimeDetailsReport.calculateAmount(salesTimeDetailsReport.getTableHeaders().get(3), discount);
			salesTimeDetailsReport.calculateAmount(salesTimeDetailsReport.getTableHeaders().get(4), tax);
			salesTimeDetailsReport.saleIncludingTaxes(salesTimeDetailsReport.getTableHeaders().get(5), productPrice,
					tax, discount);

			// Updating Footer data
			salesTimeDetailsReport.TrasactionCountOfFooter(salesTimeDetailsReport.getTableHeaders().get(1));
			salesTimeDetailsReport.calculateAmountOfFooter(salesTimeDetailsReport.getTableHeaders().get(2),
					productPrice);
			salesTimeDetailsReport.calculateAmountOfFooter(salesTimeDetailsReport.getTableHeaders().get(3), discount);
			salesTimeDetailsReport.calculateAmountOfFooter(salesTimeDetailsReport.getTableHeaders().get(4), tax);
			salesTimeDetailsReport.saleIncludingTaxesOfFooter(salesTimeDetailsReport.getTableHeaders().get(5),
					productPrice, tax, discount);

			// verify report headers
			salesTimeDetailsReport.verifyReportHeaders(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME));

			// verify report data
			salesTimeDetailsReport.verifyReportData();

			// verify report total data
			salesTimeDetailsReport.verifyReportFootertData();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * This Method is for Sold Details Int Report Data Validation
	 * 
	 * @author ravindhara Date:16-08-2022
	 */

	@Test(description = "202035-Verify the Data Validation of Sold Details Int Report")
	public void soldDetailsIntReportDataValidation() {
		try {
			final String CASE_NUM = "202035";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			String location = propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE);

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// process sales API to generate data
			soldDetailsInt.processAPI(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));

			String txnId = (String) soldDetailsInt.getJsonData().get(Reports.TRANS_ID);
			List<String> txnDate_and_Time = soldDetailsInt.txnDateAndTime();

			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			// Select the Report Date range and Location and run report
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));
			reportList.selectLocation(location);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(ReportList.BTN_RUN_REPORT);
			foundation.waitforElement(ProductSales.LBL_REPORT_NAME, Constants.ONE_SECOND);
			foundation.waitforElement(ProductSales.LBL_REPORT_NAME, Constants.SHORT_TIME);
			soldDetailsInt.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));

			// Read the Report the Data
			textBox.enterText(SalesItemDetailsReport.TXT_SEARCH, txnId);
			soldDetailsInt.getTblRecordsUI();
			soldDetailsInt.getIntialData().putAll(soldDetailsInt.getReportsData());

			foundation.waitforElement(ProductSales.LBL_REPORT_NAME, Constants.ONE_SECOND);
			soldDetailsInt.getTblRecordsUI();

			// update the report date based on calculation
			List<String> requiredData = Arrays
					.asList(rstProductSummaryData.get(CNProductSummary.REQUIRED_DATA).split(Constants.DELIMITER_HASH));
			String deviceId = rstProductSummaryData.get(CNProductSummary.DEVICE_ID);
			String productPrice = rstProductSummaryData.get(CNProductSummary.PRICE);
			String tax = rstProductSummaryData.get(CNProductSummary.TAX);

			String cat1 = rstProductSummaryData.get(CNProductSummary.CATEGORY1);
			String cat2 = rstProductSummaryData.get(CNProductSummary.CATEGORY2);
			String cat3 = rstProductSummaryData.get(CNProductSummary.CATEGORY3);

			List<String> productNames = Arrays
					.asList(rstProductSummaryData.get(CNProductSummary.PRODUCT_NAME).split(Constants.DELIMITER_TILD));
			List<String> scanCodes = Arrays
					.asList(rstProductSummaryData.get(CNProductSummary.SCAN_CODE).split(Constants.DELIMITER_TILD));
			List<String> costs = Arrays
					.asList(rstProductSummaryData.get(CNProductSummary.COST).split(Constants.DELIMITER_TILD));

			if (soldDetailsInt.getIntialData().get(0).get(soldDetailsInt.getTableHeaders().get(4))
					.equals(rstProductSummaryData.get(CNProductSummary.ACTUAL_DATA))) {
				soldDetailsInt.updateMultiData(soldDetailsInt.getTableHeaders().get(3), productNames.get(0));
				soldDetailsInt.updateMultiData(soldDetailsInt.getTableHeaders().get(4), scanCodes.get(0));
				soldDetailsInt.updateMultiData(soldDetailsInt.getTableHeaders().get(15), costs.get(0));
			} else {
				soldDetailsInt.updateMultiData(soldDetailsInt.getTableHeaders().get(3), productNames.get(1));
				soldDetailsInt.updateMultiData(soldDetailsInt.getTableHeaders().get(4), scanCodes.get(1));
				soldDetailsInt.updateMultiData(soldDetailsInt.getTableHeaders().get(15), costs.get(1));
			}

			soldDetailsInt.updateData(soldDetailsInt.getTableHeaders().get(0), location);
			soldDetailsInt.updateData(soldDetailsInt.getTableHeaders().get(1), deviceId);
			soldDetailsInt.updateData(soldDetailsInt.getTableHeaders().get(2), txnId);
			soldDetailsInt.updateData(soldDetailsInt.getTableHeaders().get(5), requiredData.get(0));
			soldDetailsInt.updateData(soldDetailsInt.getTableHeaders().get(6), requiredData.get(1));
			soldDetailsInt.updateData(soldDetailsInt.getTableHeaders().get(7), requiredData.get(2));
			soldDetailsInt.updateData(soldDetailsInt.getTableHeaders().get(8), cat1);
			soldDetailsInt.updateData(soldDetailsInt.getTableHeaders().get(9), cat2);
			soldDetailsInt.updateData(soldDetailsInt.getTableHeaders().get(10), cat3);
			soldDetailsInt.updateData(soldDetailsInt.getTableHeaders().get(11), tax);
			soldDetailsInt.updateData(soldDetailsInt.getTableHeaders().get(12), productPrice);
			soldDetailsInt.updateData(soldDetailsInt.getTableHeaders().get(14), productPrice);
			soldDetailsInt.updateData(soldDetailsInt.getTableHeaders().get(17), txnDate_and_Time.get(0));
			soldDetailsInt.updateData(soldDetailsInt.getTableHeaders().get(18), txnDate_and_Time.get(1).toUpperCase());

			// verify report headers
			soldDetailsInt.verifyReportHeaders(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME));

			// verify report data
			soldDetailsInt.verifyReportData();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * Cash Flow Employee Device Report Data Validation
	 * 
	 * @author KarthikR
	 * @date: 05-08-2022
	 */
	@Test(description = "202033 - Cash Flow Employee Device Report data validation")
	public void CashFlowEmployeeDeviceReportDataValidation() {
		final String CASE_NUM = "202033";

		// Reading Test Data from DB
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);
		rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);
		rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

		List<String> columns = Arrays
				.asList(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME).split(Constants.DELIMITER_HASH));
		List<String> columnValue = Arrays
				.asList(rstLocationSummaryData.get(CNLocationSummary.COLUMN_VALUE).split(Constants.DELIMITER_HASH));
		String location = propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE);
		try {
			// Navigate to ADM and Select Org
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// process sales API to generate data
			cashFlowEmployeeDevice.processAPI(rstProductSummaryData.get(CNProductSummary.ACTUAL_DATA),
					rstProductSummaryData.get(CNProductSummary.REQUIRED_DATA),
					rstProductSummaryData.get(CNProductSummary.DEVICE_ID),
					rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));

			// navigate To Reports
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location and run report
			cashFlowEmployeeDevice.selectAndRunReport(rstReportListData.get(CNReportList.REPORT_NAME),
					rstReportListData.get(CNReportList.DATE_RANGE), location);

			// read Report Data
			cashFlowEmployeeDevice.readAllRecordsFromCashFlowEmployeeDetailsTable(
					rstProductSummaryData.get(CNProductSummary.DEVICE_ID), location);
			cashFlowEmployeeDevice.getInitialReportsData().putAll(cashFlowEmployeeDevice.reportsData);
			cashFlowEmployeeDevice.getInitialReportTotals().putAll(cashFlowEmployeeDevice.getReportsTotalData());

			// process sales API to generate data
			cashFlowEmployeeDevice.processAPI(rstProductSummaryData.get(CNProductSummary.ACTUAL_DATA),
					rstProductSummaryData.get(CNProductSummary.REQUIRED_DATA),
					rstProductSummaryData.get(CNProductSummary.DEVICE_ID),
					rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));

			// navigate To Reports
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location and run report
			cashFlowEmployeeDevice.selectAndRunReport(rstReportListData.get(CNReportList.REPORT_NAME),
					rstReportListData.get(CNReportList.DATE_RANGE), location);

			// read Updated Report Data
			cashFlowEmployeeDevice.readAllRecordsFromCashFlowEmployeeDetailsTable(
					rstProductSummaryData.get(CNProductSummary.DEVICE_ID), location);
			cashFlowEmployeeDevice.getJsonSalesData();

			int recordCountOfCash = cashFlowEmployeeDevice.getRequiredRecord(columnValue.get(0));
			int recordCountOfCreditCard = cashFlowEmployeeDevice.getRequiredRecord(columnValue.get(1));
			int recordCountOfGEN3 = cashFlowEmployeeDevice.getRequiredRecord(columnValue.get(2));
			int recordCountOfSOGO = cashFlowEmployeeDevice.getRequiredRecord(columnValue.get(3));
			int recordCountOfComp = cashFlowEmployeeDevice.getRequiredRecord(columnValue.get(4));
			int recordCountOfGuestPass = cashFlowEmployeeDevice.getRequiredRecord(columnValue.get(5));
			int recordCountOfSpecial = cashFlowEmployeeDevice.getRequiredRecord(columnValue.get(6));
			int recordCountOfAccount = cashFlowEmployeeDevice.getRequiredRecord(columnValue.get(7));
			int recordCountOfTotals = cashFlowEmployeeDevice.getRequiredRecord(columnValue.get(8));

			// verify Report Headers
			cashFlowEmployeeDevice.verifyReportHeaders(columns);

			// calculate Credit Payment Counts
			cashFlowEmployeeDevice.calculateCounts(cashFlowEmployeeDevice.getTableHeaders().get(1), recordCountOfCash);

			// calculate Credit Payment Amounts
			cashFlowEmployeeDevice.calculateAmounts(cashFlowEmployeeDevice.getTableHeaders().get(2), recordCountOfCash);

			// calculate Credit Void Counts
			cashFlowEmployeeDevice.calculateCounts(cashFlowEmployeeDevice.getTableHeaders().get(3), recordCountOfCash);

			// calculate Credit Void Amounts
			cashFlowEmployeeDevice.calculateAmounts(cashFlowEmployeeDevice.getTableHeaders().get(4), recordCountOfCash);

			// calculate Credit Sales
			cashFlowEmployeeDevice.calculateLocationSales(cashFlowEmployeeDevice.getTableHeaders().get(7),
					recordCountOfCash);

			// calculate Credit Taxes
			cashFlowEmployeeDevice.calculateLocationTax(cashFlowEmployeeDevice.getTableHeaders().get(8),
					recordCountOfCash);

			// calculate Credit Total
			cashFlowEmployeeDevice.calculateTotalsColumnData(cashFlowEmployeeDevice.getTableHeaders().get(13),
					recordCountOfCash);

			// calculate Credit Payment Counts
			cashFlowEmployeeDevice.calculateCounts(cashFlowEmployeeDevice.getTableHeaders().get(1),
					recordCountOfCreditCard);

			// calculate Credit Payment Amounts
			cashFlowEmployeeDevice.calculateAmounts(cashFlowEmployeeDevice.getTableHeaders().get(2),
					recordCountOfCreditCard);

			// calculate Credit Void Counts
			cashFlowEmployeeDevice.calculateCounts(cashFlowEmployeeDevice.getTableHeaders().get(3),
					recordCountOfCreditCard);

			// calculate Credit Void Amounts
			cashFlowEmployeeDevice.calculateAmounts(cashFlowEmployeeDevice.getTableHeaders().get(4),
					recordCountOfCreditCard);

			// calculate Credit Declined Counts
			cashFlowEmployeeDevice.calculateCounts(cashFlowEmployeeDevice.getTableHeaders().get(5),
					recordCountOfCreditCard);

			// calculate Credit Declined Amounts
			cashFlowEmployeeDevice.calculateAmounts(cashFlowEmployeeDevice.getTableHeaders().get(6),
					recordCountOfCreditCard);

			// calculate Credit Sales
			cashFlowEmployeeDevice.calculateLocationSales(cashFlowEmployeeDevice.getTableHeaders().get(7),
					recordCountOfCreditCard);

			// calculate Credit Taxes
			cashFlowEmployeeDevice.calculateLocationTax(cashFlowEmployeeDevice.getTableHeaders().get(8),
					recordCountOfCreditCard);

			// calculate Credit Total
			cashFlowEmployeeDevice.calculateTotalsColumnData(cashFlowEmployeeDevice.getTableHeaders().get(13),
					recordCountOfCreditCard);

			// calculate gen3 Payment Counts
			cashFlowEmployeeDevice.calculateCounts(cashFlowEmployeeDevice.getTableHeaders().get(1), recordCountOfGEN3);

			// calculate gen3 Payment Amounts
			cashFlowEmployeeDevice.calculateAmounts(cashFlowEmployeeDevice.getTableHeaders().get(2), recordCountOfGEN3);

			// calculate gen3 Void Counts
			cashFlowEmployeeDevice.calculateCounts(cashFlowEmployeeDevice.getTableHeaders().get(3), recordCountOfGEN3);

			// calculate gen3 Void Amounts
			cashFlowEmployeeDevice.calculateAmounts(cashFlowEmployeeDevice.getTableHeaders().get(4), recordCountOfGEN3);

			// calculate gen3 Declined Counts
			cashFlowEmployeeDevice.calculateCounts(cashFlowEmployeeDevice.getTableHeaders().get(5), recordCountOfGEN3);

			// calculate gen3 Declined Amounts
			cashFlowEmployeeDevice.calculateAmounts(cashFlowEmployeeDevice.getTableHeaders().get(6), recordCountOfGEN3);

			// calculate gen3 Sales
			cashFlowEmployeeDevice.calculateLocationSales(cashFlowEmployeeDevice.getTableHeaders().get(7),
					recordCountOfGEN3);

			// calculate gen3 Taxes
			cashFlowEmployeeDevice.calculateLocationTax(cashFlowEmployeeDevice.getTableHeaders().get(8),
					recordCountOfGEN3);

			// calculate gen3 Total
			cashFlowEmployeeDevice.calculateTotalsColumnData(cashFlowEmployeeDevice.getTableHeaders().get(13),
					recordCountOfGEN3);

			// calculate SOGO Payment Counts
			cashFlowEmployeeDevice.calculateCounts(cashFlowEmployeeDevice.getTableHeaders().get(1), recordCountOfSOGO);

			// calculate SOGO Payment Amounts
			cashFlowEmployeeDevice.calculateAmounts(cashFlowEmployeeDevice.getTableHeaders().get(2), recordCountOfSOGO);

			// calculate SOGO Void Counts
			cashFlowEmployeeDevice.calculateCounts(cashFlowEmployeeDevice.getTableHeaders().get(3), recordCountOfSOGO);

			// calculate SOGO Void Amounts
			cashFlowEmployeeDevice.calculateAmounts(cashFlowEmployeeDevice.getTableHeaders().get(4), recordCountOfSOGO);

			// calculate SOGO Sales
			cashFlowEmployeeDevice.calculateLocationSales(cashFlowEmployeeDevice.getTableHeaders().get(7),
					recordCountOfSOGO);

			// calculate SOGO Taxes
			cashFlowEmployeeDevice.calculateLocationTax(cashFlowEmployeeDevice.getTableHeaders().get(8),
					recordCountOfSOGO);

			// calculate SOGO Total
			cashFlowEmployeeDevice.calculateTotalsColumnData(cashFlowEmployeeDevice.getTableHeaders().get(13),
					recordCountOfSOGO);

			// calculate Comp Payment Counts
			cashFlowEmployeeDevice.calculateCounts(cashFlowEmployeeDevice.getTableHeaders().get(1), recordCountOfComp);

			// calculate Comp Payment Amounts
			cashFlowEmployeeDevice.calculateAmounts(cashFlowEmployeeDevice.getTableHeaders().get(2), recordCountOfComp);

			// calculate Comp Void Counts
			cashFlowEmployeeDevice.calculateCounts(cashFlowEmployeeDevice.getTableHeaders().get(3), recordCountOfComp);

			// calculate Comp Void Amounts
			cashFlowEmployeeDevice.calculateAmounts(cashFlowEmployeeDevice.getTableHeaders().get(4), recordCountOfComp);

			// calculate Comp Sales
			cashFlowEmployeeDevice.calculateLocationSales(cashFlowEmployeeDevice.getTableHeaders().get(7),
					recordCountOfComp);

			// calculate Comp Taxes
			cashFlowEmployeeDevice.calculateLocationTax(cashFlowEmployeeDevice.getTableHeaders().get(8),
					recordCountOfComp);

			// calculate Comp Total
			cashFlowEmployeeDevice.calculateTotalsColumnData(cashFlowEmployeeDevice.getTableHeaders().get(13),
					recordCountOfComp);

			// calculate GuestPass Payment Counts
			cashFlowEmployeeDevice.calculateCounts(cashFlowEmployeeDevice.getTableHeaders().get(1),
					recordCountOfGuestPass);

			// calculate GuestPass Payment Amounts
			cashFlowEmployeeDevice.calculateAmounts(cashFlowEmployeeDevice.getTableHeaders().get(2),
					recordCountOfGuestPass);

			// calculate GuestPass Void Counts
			cashFlowEmployeeDevice.calculateCounts(cashFlowEmployeeDevice.getTableHeaders().get(3),
					recordCountOfGuestPass);

			// calculate GuestPass Void Amounts
			cashFlowEmployeeDevice.calculateAmounts(cashFlowEmployeeDevice.getTableHeaders().get(4),
					recordCountOfGuestPass);

			// calculate GuestPass Sales
			cashFlowEmployeeDevice.calculateLocationSales(cashFlowEmployeeDevice.getTableHeaders().get(7),
					recordCountOfGuestPass);

			// calculate GuestPass Taxes
			cashFlowEmployeeDevice.calculateLocationTax(cashFlowEmployeeDevice.getTableHeaders().get(8),
					recordCountOfGuestPass);

			// calculate GuestPass Total
			cashFlowEmployeeDevice.calculateTotalsColumnData(cashFlowEmployeeDevice.getTableHeaders().get(13),
					recordCountOfGuestPass);

			// calculate Special Payment Counts
			cashFlowEmployeeDevice.calculateCounts(cashFlowEmployeeDevice.getTableHeaders().get(1),
					recordCountOfSpecial);

			// calculate Special Payment Amounts
			cashFlowEmployeeDevice.calculateAmounts(cashFlowEmployeeDevice.getTableHeaders().get(2),
					recordCountOfSpecial);

			// calculate Special Void Counts
			cashFlowEmployeeDevice.calculateCounts(cashFlowEmployeeDevice.getTableHeaders().get(3),
					recordCountOfSpecial);

			// calculate Special Void Amounts
			cashFlowEmployeeDevice.calculateAmounts(cashFlowEmployeeDevice.getTableHeaders().get(4),
					recordCountOfSpecial);

			// calculate Special Sales
			cashFlowEmployeeDevice.calculateLocationSales(cashFlowEmployeeDevice.getTableHeaders().get(7),
					recordCountOfSpecial);

			// calculate Special Taxes
			cashFlowEmployeeDevice.calculateLocationTax(cashFlowEmployeeDevice.getTableHeaders().get(8),
					recordCountOfSpecial);

			// calculate Special Total
			cashFlowEmployeeDevice.calculateTotalsColumnData(cashFlowEmployeeDevice.getTableHeaders().get(13),
					recordCountOfSpecial);

			// calculate Account Payment Counts
			cashFlowEmployeeDevice.calculateCounts(cashFlowEmployeeDevice.getTableHeaders().get(1),
					recordCountOfAccount);

			// calculate Account Payment Amounts
			cashFlowEmployeeDevice.calculateAmounts(cashFlowEmployeeDevice.getTableHeaders().get(2),
					recordCountOfAccount);

			// calculate Account Void Counts
			cashFlowEmployeeDevice.calculateCounts(cashFlowEmployeeDevice.getTableHeaders().get(3),
					recordCountOfAccount);

			// calculate Account Void Amounts
			cashFlowEmployeeDevice.calculateAmounts(cashFlowEmployeeDevice.getTableHeaders().get(4),
					recordCountOfAccount);

			// calculate Account Sales
			cashFlowEmployeeDevice.calculateLocationSales(cashFlowEmployeeDevice.getTableHeaders().get(7),
					recordCountOfAccount);

			// calculate Account Taxes
			cashFlowEmployeeDevice.calculateLocationTax(cashFlowEmployeeDevice.getTableHeaders().get(8),
					recordCountOfAccount);

			// calculate Account Total
			cashFlowEmployeeDevice.calculateTotalsColumnData(cashFlowEmployeeDevice.getTableHeaders().get(13),
					recordCountOfAccount);

			// calculate Totals Payment Counts
			cashFlowEmployeeDevice.calculateCountsForTotals(cashFlowEmployeeDevice.getTableHeaders().get(1),
					recordCountOfTotals);

			// calculate Totals Payment Amounts
			cashFlowEmployeeDevice.calculateAmountsForTotals(cashFlowEmployeeDevice.getTableHeaders().get(2),
					recordCountOfTotals);

			// calculate Totals Void Counts
			cashFlowEmployeeDevice.calculateCountsForTotals(cashFlowEmployeeDevice.getTableHeaders().get(3),
					recordCountOfTotals);

			// calculate Totals Void Amounts
			cashFlowEmployeeDevice.calculateAmountsForTotals(cashFlowEmployeeDevice.getTableHeaders().get(4),
					recordCountOfTotals);

			// calculate Totals Declined Counts
			cashFlowEmployeeDevice.calculateCounts(cashFlowEmployeeDevice.getTableHeaders().get(5),
					recordCountOfTotals);

			// calculate Totals Declined Amounts
			cashFlowEmployeeDevice.calculateAmounts(cashFlowEmployeeDevice.getTableHeaders().get(6),
					recordCountOfTotals);

			// calculate Totals Sales
			cashFlowEmployeeDevice.calculateLocationSalesForTotals(cashFlowEmployeeDevice.getTableHeaders().get(7),
					recordCountOfTotals);

			// calculate Totals Taxes
			cashFlowEmployeeDevice.calculateLocationTaxForTotals(cashFlowEmployeeDevice.getTableHeaders().get(8),
					recordCountOfTotals);

			// calculate Totals Total
			cashFlowEmployeeDevice.calculateTotalsColumnDataForTotals(cashFlowEmployeeDevice.getTableHeaders().get(13),
					recordCountOfTotals);

			// verify Report Data
			cashFlowEmployeeDevice.verifyReportRecords();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/*
	 * This Method is for Sales Time Details Report data validation
	 * 
	 * @author ravindhara Date: 17-08-2022
	 * 
	 */
	@Test(description = "203347-Verify the Data Validation of Sales Time Details By Device Report")
	public void SalesTimeDetailsByDeviceReportDataValidation() {
		try {
			final String CASE_NUM = "203347";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			String locationName = propertyFile.readPropertyFile(Configuration.CURRENT_LOC,
					FilePath.PROPERTY_CONFIG_FILE);

			// process sales API to generate data
			salesTimeDetailsByDevice.processAPI(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));

			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location and run report
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));
			reportList.selectLocation(locationName);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(ReportList.BTN_RUN_REPORT);
			foundation.waitforElement(SalesSummaryAndCost.LBL_REPORT_NAME, Constants.SHORT_TIME);
			salesTimeDetailsByDevice.verifyReportName(rstProductSummaryData.get(CNProductSummary.DEVICE_ID));

			// Read the Report the Data
			salesTimeDetailsByDevice.getTblRecordsUI();
			salesTimeDetailsByDevice.getIntialData().putAll(salesTimeDetailsByDevice.getReportsData());
			salesTimeDetailsByDevice.getUpdatedTableFooters().putAll(salesTimeDetailsByDevice.getTableFooters());

			// process sales API to generate data
			salesTimeDetailsByDevice.processAPI(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));

			// rerun and reread report
			foundation.click(ReportList.BTN_RUN_REPORT);
			foundation.threadWait(Constants.TWO_SECOND);

			salesTimeDetailsByDevice.getTblRecordsUI();

			salesTimeDetailsByDevice
					.decideTimeRange((String) salesTimeDetailsByDevice.getJsonData().get(Reports.TRANS_DATE));

			// update the report date based on calculation
			String productPrice = rstProductSummaryData.get(CNProductSummary.PRICE);
			String tax = rstProductSummaryData.get(CNProductSummary.TAX);
			String discount = rstProductSummaryData.get(CNProductSummary.DISCOUNT);

			// Updating Table data
			salesTimeDetailsByDevice.TrasactionCount(salesTimeDetailsByDevice.getTableHeaders().get(1));
			salesTimeDetailsByDevice.calculateAmount(salesTimeDetailsByDevice.getTableHeaders().get(2), productPrice);
			salesTimeDetailsByDevice.calculateAmount(salesTimeDetailsByDevice.getTableHeaders().get(3), discount);
			salesTimeDetailsByDevice.calculateAmount(salesTimeDetailsByDevice.getTableHeaders().get(4), tax);
			salesTimeDetailsByDevice.saleIncludingTaxes(salesTimeDetailsByDevice.getTableHeaders().get(5), productPrice,
					tax, discount);

			// Updating Footer data
			salesTimeDetailsByDevice.TrasactionCountOfFooter(salesTimeDetailsByDevice.getTableHeaders().get(1));
			salesTimeDetailsByDevice.calculateAmountOfFooter(salesTimeDetailsByDevice.getTableHeaders().get(2),
					productPrice);
			salesTimeDetailsByDevice.calculateAmountOfFooter(salesTimeDetailsByDevice.getTableHeaders().get(3),
					discount);
			salesTimeDetailsByDevice.calculateAmountOfFooter(salesTimeDetailsByDevice.getTableHeaders().get(4), tax);
			salesTimeDetailsByDevice.saleIncludingTaxesOfFooter(salesTimeDetailsByDevice.getTableHeaders().get(5),
					productPrice, tax, discount);

			// verify report headers
			salesTimeDetailsByDevice.verifyReportHeaders(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME));

			// verify report data
			salesTimeDetailsByDevice.verifyReportData();

			// verify report total data
			salesTimeDetailsByDevice.verifyReportFootertData();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * This Method is for Unpaid Order Report Data Validation
	 * 
	 * @author ravindhara Date: 25-08-2022
	 */
	@Test(description = "203621-This test validates Unpaid Order Report Data")
	public void UnpaidOrderReportDataValidation() {
		try {
			final String CASE_NUM = "203621";
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
			String menu = rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM);

			navigationBar.selectOrganization(requiredData.get(0));
			// navigate to Reports
			navigationBar.navigateToMenuItem(menu);

			// Select the Report Date range and Location
			reportList.selectReport(reportName);
			reportList.selectDateRangeDate(rstReportListData.get(CNReportList.DATE_RANGE), requiredData.get(2),
					UnpaidOrder.DATA_EXISTING_DATE, UnpaidOrder.DATA_EXISTING_DATE);
			reportList.selectLocation(requiredData.get(1));
			foundation.click(ReportList.BTN_RUN_REPORT);
			foundation.waitforElement(UnpaidOrder.LBL_REPORT_NAME, Constants.SHORT_TIME);
			unpaidOrder.verifyReportName(reportName);
			unpaidOrder.getTblRecordsUI();

			// Validating the Headers and Report data
			unpaidOrder.verifyReportHeaders(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME));
			unpaidOrder.verifyReportData(rstProductSummaryData.get(CNProductSummary.ACTUAL_DATA));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * This Method is for Inventory Value Summary Report Data Validation
	 * 
	 * @author ravindhara Date: 29-08-2022
	 */
	@Test(description = "203690-Verify the Data Validation of Inventory Value Summary Report")
	public void inventoryValueSummaryReportDataValidation() {
		try {
			final String CASE_NUM = "203690";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			String productName = rstProductSummaryData.get(CNProductSummary.PRODUCT_NAME);
			String locationName = propertyFile.readPropertyFile(Configuration.CURRENT_LOC,
					FilePath.PROPERTY_CONFIG_FILE);

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location and run report
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectLocation(locationName);
			textBox.enterText(InventoryValueSummary.PRODUCT_SEARCH_BY, productName);

			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(ReportList.BTN_RUN_REPORT);
			foundation.waitforElement(ProductSales.LBL_REPORT_NAME, Constants.SHORT_TIME);
			inventoryValueSummary.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));

			// Read the Report the Data
			inventoryValueSummary.getTblRecordsUI();
			inventoryValueSummary.getIntialData().putAll(inventoryValueSummary.getReportsData());

			// process sales API to generate data
			inventoryValueSummary.processAPI(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));

			// rerun and reread report
			foundation.click(ReportList.BTN_RUN_REPORT);
			foundation.threadWait(Constants.TWO_SECOND);
			inventoryValueSummary.getTblRecordsUI();

			// update the report date based on calculation
			String productPrice = rstProductSummaryData.get(CNProductSummary.PRICE);
			String cost = rstProductSummaryData.get(CNProductSummary.COST);
			String scanCode = rstProductSummaryData.get(CNProductSummary.SCAN_CODE);
			String userKey = rstProductSummaryData.get(CNProductSummary.USER_KEY);
			String cat1 = rstProductSummaryData.get(CNProductSummary.CATEGORY1);
			String cat2 = rstProductSummaryData.get(CNProductSummary.CATEGORY2);
			String cat3 = rstProductSummaryData.get(CNProductSummary.CATEGORY3);

			inventoryValueSummary.updateData(inventoryValueSummary.getTableHeaders().get(0), locationName);
			inventoryValueSummary.updateData(inventoryValueSummary.getTableHeaders().get(1), userKey);
			inventoryValueSummary.updateData(inventoryValueSummary.getTableHeaders().get(2), scanCode);
			inventoryValueSummary.updateData(inventoryValueSummary.getTableHeaders().get(3), productName);
			inventoryValueSummary.updateData(inventoryValueSummary.getTableHeaders().get(4), cat1);
			inventoryValueSummary.updateData(inventoryValueSummary.getTableHeaders().get(5), cat2);
			inventoryValueSummary.updateData(inventoryValueSummary.getTableHeaders().get(6), cat3);
			inventoryValueSummary.updateData(inventoryValueSummary.getTableHeaders().get(7), cost);
			inventoryValueSummary.updateData(inventoryValueSummary.getTableHeaders().get(8), productPrice);
			inventoryValueSummary.quantityOnHand(inventoryValueSummary.getTableHeaders().get(9));
			inventoryValueSummary.calculateTotal(inventoryValueSummary.getTableHeaders().get(12), cost);
			inventoryValueSummary.calculateTotal(inventoryValueSummary.getTableHeaders().get(13), productPrice);
			inventoryValueSummary.saleCount(inventoryValueSummary.getTableHeaders().get(14));
			inventoryValueSummary.saleCount(inventoryValueSummary.getTableHeaders().get(15));

			// verify report headers
			inventoryValueSummary.verifyReportHeaders(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME));

			// verify report data
			inventoryValueSummary.verifyReportData();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * This Method is for Entry Summary Report Report Data Validation
	 * 
	 * @author ravindhara Date: 02-09-2022
	 */
	@Test(description = "203698-This test validates Entry Summary Report Data Calculation")
	public void entrySummaryReportDataValidation() {
		try {
			final String CASE_NUM = "203698";

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
			String reason = rstProductSummaryData.get(CNProductSummary.REQUIRED_DATA);
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
			foundation.threadWait(Constants.ONE_SECOND);

			String inventoryValue = locationSummary
					.getInventoryValue(rstProductSummaryData.get(CNProductSummary.SCAN_CODE));

			String updatedTime = String
					.valueOf(dateAndTime.getDateAndTime(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION),
							rstLocationSummaryData.get(CNLocationSummary.TIME_ZONE)));

			locationSummary.updateInventory(rstProductSummaryData.get(CNProductSummary.SCAN_CODE),
					entrySummaryReport.incrementedInventoryValue(inventoryValue), reason);

			// Updating the Inventory of the product
//			String updatedTime = locationSummary.updateInventoryWithTimeOfTransacction(rstProductSummaryData.get(CNProductSummary.SCAN_CODE),
//					entrySummaryReport.incrementedInventoryValue(inventoryValue), reason, rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION),
//					rstLocationSummaryData.get(CNLocationSummary.TIME_ZONE));

//			String updatedTime = String
//					.valueOf(dateAndTime.getDateAndTime1(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION),
//							rstLocationSummaryData.get(CNLocationSummary.TIME_ZONE)));
			System.out.println("updatedTime entry :" + updatedTime);

			// navigate to Reports
			navigationBar.navigateToMenuItem(menu.get(1));

			// Select the Report Date range and Location
			reportList.selectReport(reportName);
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));
			reportList.selectLocation(
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(ReportList.BTN_RUN_REPORT);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.waitforElement(EntrySummaryReport.LBL_REPORT_NAME, Constants.SHORT_TIME);
			entrySummaryReport.verifyReportName(reportName);
			textBox.enterText(InventoryAdjustmentDetail.TXT_SEARCH, String.valueOf(updatedTime).toUpperCase());
			entrySummaryReport.getTblRecordsUI();

			// Validating the Headers and Report data
			entrySummaryReport.verifyReportHeaders(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME));
			entrySummaryReport.verifyReportData(rstProductSummaryData.get(CNProductSummary.ACTUAL_DATA));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * This Method is for Delete Summary Report Data Validation
	 * 
	 * @author ravindhara Date: 07-08-2022
	 */
	@Test(description = "203720-This test validates Delete Summary Report Data Calculation")
	public void deleteSummaryReportDataValidation() {
		try {
			final String CASE_NUM = "203720";

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
			String reason = rstProductSummaryData.get(CNProductSummary.REQUIRED_DATA);
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
			foundation.threadWait(Constants.ONE_SECOND);

			String inventoryValue = locationSummary
					.getInventoryValue(rstProductSummaryData.get(CNProductSummary.SCAN_CODE));
			
			String updatedTime = String
					.valueOf(dateAndTime.getDateAndTime(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION),
							rstLocationSummaryData.get(CNLocationSummary.TIME_ZONE)));

			locationSummary.updateInventory(rstProductSummaryData.get(CNProductSummary.SCAN_CODE),
					deleteSummaryReport.decrementedInventoryValue(inventoryValue), reason);


			// Updating the Inventory of the product
//			String updatedTime = locationSummary.updateInventoryWithTimeOfTransacction(
//					rstProductSummaryData.get(CNProductSummary.SCAN_CODE),
//					deleteSummaryReport.decrementedInventoryValue(inventoryValue), reason,
//					rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION),
//					rstLocationSummaryData.get(CNLocationSummary.TIME_ZONE));

//			String updatedTime = String
//					.valueOf(dateAndTime.getDateAndTime1(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION),
//							rstLocationSummaryData.get(CNLocationSummary.TIME_ZONE)));

			// navigate to Reports
			navigationBar.navigateToMenuItem(menu.get(1));

			// Select the Report Date range and Location
			reportList.selectReport(reportName);
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));
			reportList.selectLocation(
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(ReportList.BTN_RUN_REPORT);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.waitforElement(DeleteSummaryReport.LBL_REPORT_NAME, Constants.SHORT_TIME);
			deleteSummaryReport.verifyReportName(reportName);
			textBox.enterText(InventoryAdjustmentDetail.TXT_SEARCH, String.valueOf(updatedTime).toUpperCase());
			deleteSummaryReport.getTblRecordsUI();

			// Validating the Headers and Report data
			deleteSummaryReport.verifyReportHeaders(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME));
			deleteSummaryReport.verifyReportData(rstProductSummaryData.get(CNProductSummary.ACTUAL_DATA));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * This Method is for Product Sales Report Data Validation
	 * 
	 * @author ravindhara Date: 08-09-2022
	 */
	@Test(description = "203834-Verify the Data Validation of Sold Item COGS Report")
	public void soldItemCOGSReportDataValidation() {
		try {
			final String CASE_NUM = "203834";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			String locationName = propertyFile.readPropertyFile(Configuration.CURRENT_LOC,
					FilePath.PROPERTY_CONFIG_FILE);

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// process sales API to generate data
			soldItemCOGS.processAPI(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));

			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location and run report
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));
			reportList.selectLocation(locationName);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(ReportList.BTN_RUN_REPORT);
			foundation.waitforElement(ProductSales.LBL_REPORT_NAME, Constants.SHORT_TIME);
			soldItemCOGS.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));

			// Read the Report the Data
			soldItemCOGS.getTblRecordsUI();
			soldItemCOGS.getIntialData().putAll(soldItemCOGS.getReportsData());
			soldItemCOGS.getUpdatedTableFooters().putAll(soldItemCOGS.getTableFooters());

			// process sales API to generate data
			soldItemCOGS.processAPI(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));

			// rerun and reread report
			foundation.click(ReportList.BTN_RUN_REPORT);
			foundation.threadWait(Constants.TWO_SECOND);
			soldItemCOGS.getTblRecordsUI();

			// update the report date based on calculation
			String productPrice = rstProductSummaryData.get(CNProductSummary.PRICE);
			// String tax = rstProductSummaryData.get(CNProductSummary.TAX);
			String productName = rstProductSummaryData.get(CNProductSummary.PRODUCT_NAME);
			String scanCode = rstProductSummaryData.get(CNProductSummary.SCAN_CODE);
			String cost = rstProductSummaryData.get(CNProductSummary.COST);

			// Updating Table data
			soldItemCOGS.updateData(soldItemCOGS.getTableHeaders().get(0), locationName);
			soldItemCOGS.updateMultipleData(soldItemCOGS.getTableHeaders().get(1), productName);
			soldItemCOGS.updateMultipleData(soldItemCOGS.getTableHeaders().get(2), scanCode);
			soldItemCOGS.saleCount(soldItemCOGS.getTableHeaders().get(4));
			soldItemCOGS.updateData(soldItemCOGS.getTableHeaders().get(5), cost);
			soldItemCOGS.calculateTotalCOGS(soldItemCOGS.getTableHeaders().get(6), cost);
			soldItemCOGS.calculateRevenue(soldItemCOGS.getTableHeaders().get(7), productPrice);
			soldItemCOGS.calculateProfit(soldItemCOGS.getTableHeaders().get(8));

			// Updating Footer data
			soldItemCOGS.updateCostPercentOfFooter(soldItemCOGS.getTableHeaders().get(1));
			soldItemCOGS.calculateIntegerTotalOfFooter(soldItemCOGS.getTableHeaders().get(4));
			soldItemCOGS.calculateDoubleTotalOfFooter(soldItemCOGS.getTableHeaders().get(5));
			soldItemCOGS.calculateDoubleTotalOfFooter(soldItemCOGS.getTableHeaders().get(6));
			soldItemCOGS.calculateDoubleTotalOfFooter(soldItemCOGS.getTableHeaders().get(7));
			soldItemCOGS.calculateDoubleTotalOfFooter(soldItemCOGS.getTableHeaders().get(8));

			// verify report headers
			soldItemCOGS.verifyReportHeaders(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME));

			// verify report data
			soldItemCOGS.verifyReportData();

			// verify report total data
			soldItemCOGS.verifyReportFootertData();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * Subsidy Consumer Spend Report Data Validation
	 * 
	 * @author KarthikR
	 * @date: 24-08-2022
	 */
	@Test(description = "203623 - Subsidy Consumer Spend Report data validation")
	public void SubsidyConsumerSpendReportDataValidation() {
		final String CASE_NUM = "203623";

		// Reading Test Data from DB
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		rstConsumerSearchData = dataBase.getConsumerSearchData(Queries.CONSUMER_SEARCH, CASE_NUM);
		rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

		List<String> menu = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> requiredData = Arrays
				.asList(rstV5DeviceData.get(CNLocationSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		String currentDate = dateAndTime.getDateAndTime(Constants.REGEX_MM_DD_YYYY, Constants.TIME_ZONE_INDIA);
		List<String> orderPageData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.ORDER_PAGE).split(Constants.DELIMITER_TILD));
		List<String> product = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME).split(Constants.DELIMITER_TILD));
		List<String> columnName = Arrays
				.asList(rstConsumerSearchData.get(CNConsumerSearch.COLUMN_NAME).split(Constants.DELIMITER_HASH));
		List<String> subsidyGroup = Arrays
				.asList(rstConsumerSearchData.get(CNConsumerSearch.TITLE).split(Constants.DELIMITER_TILD));
		try {
			// Login to ADM with Super Credentials
			subsidyConsumerSpend.loginToADMWithSuperCredentials();

			// Select Location
			locationList.selectLocationName(rstV5DeviceData.get(CNV5Device.LOCATION));

			// Verifying the selection of defaults as Top Off for GMA subsidy
			subsidyConsumerSpend.selectionOfTopOffSubsidy(requiredData.get(0), currentDate, requiredData.get(9),
					requiredData.get(7), requiredData.get(10));

			// Navigate to Admin > Consumer to Read Scancode and Consumer ID
			List<String> details = subsidyConsumerSpend.readConsumerDetails(menu.get(1),
					rstConsumerSearchData.get(CNConsumerSearch.SEARCH), rstV5DeviceData.get(CNV5Device.LOCATION));
			login.logout();
			browser.close();

			// Launch V5 Device to do Transaction on Subsidy Balance
			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			String price = subsidyConsumerSpend.readPriceFromV5Transaction(product.get(0), orderPageData.get(0),
					rstV5DeviceData.get(CNV5Device.EMAIL_ID), rstV5DeviceData.get(CNV5Device.PIN),
					rstV5DeviceData.get(CNV5Device.PAYMENTS_PAGE));
			browser.close();

			// Navigate to ADM
			subsidyConsumerSpend.loginToADMWithSuperCredentials();

			// Navigate to Admin Consumer to Read Transaction ID
			String transID = subsidyConsumerSpend.readTransactionID(menu.get(1),
					rstConsumerSearchData.get(CNConsumerSearch.SEARCH), rstV5DeviceData.get(CNV5Device.LOCATION));

			// Select Report, Data and Location to run Report
			subsidyConsumerSpend.selectAndRunReport(menu.get(0), rstReportListData.get(CNReportList.REPORT_NAME),
					rstReportListData.get(CNReportList.DATE_RANGE), rstV5DeviceData.get(CNV5Device.LOCATION), transID);

			// Read the Table Data from UI and Validate
			subsidyConsumerSpend.getTblRecordsUI();
			subsidyConsumerSpend.verifyReportHeaders(rstConsumerSearchData.get(CNConsumerSearch.COLUMN_NAME));
			subsidyConsumerSpend.verifyCommonValueContentofTableRecord(columnName.get(0),
					rstV5DeviceData.get(CNV5Device.LOCATION));
			subsidyConsumerSpend.verifyCommonValueContentofTableRecord(columnName.get(1), details.get(1));
			subsidyConsumerSpend.verifyCommonValueContentofTableRecord(columnName.get(2),
					rstConsumerSearchData.get(CNConsumerSearch.FIRST_NAME));
			subsidyConsumerSpend.verifyCommonValueContentofTableRecord(columnName.get(3),
					rstConsumerSearchData.get(CNConsumerSearch.ATTRIBUTE));
			subsidyConsumerSpend.verifyCommonValueContentofTableRecord(columnName.get(4), details.get(0));
			subsidyConsumerSpend.verifyCommonValueContentofTableRecord(columnName.get(5),
					rstV5DeviceData.get(CNV5Device.EMAIL_ID));
			subsidyConsumerSpend.verifyCommonValueContentofTableRecord(columnName.get(6), subsidyGroup.get(0));
			subsidyConsumerSpend.verifyCommonValueContentofTableRecord(columnName.get(7), transID);
			String dateArray[] = currentDate.split("/");
			String date = dateArray[0].replaceAll(Constants.REMOVE_LEADING_ZERO, "") + "/"
					+ dateArray[1].replaceAll(Constants.REMOVE_LEADING_ZERO, "") + "/" + dateArray[2];
			subsidyConsumerSpend.verifyCommonValueContentofTableRecord(columnName.get(8), date);
			price = price.replace("$", "");
			price = price.replace(".", "");
			price = price.replace("0", "");
			System.out.println(columnName.get(9) + " : " + price);
			subsidyConsumerSpend.verifyCommonValueContentofTableRecord(columnName.get(9), price);

			// Verifying the selection of defaults as Roll Over for GMA subsidy
			navigationBar.navigateToMenuItem(menu.get(3));
			locationList.selectLocationName(rstV5DeviceData.get(CNV5Device.LOCATION));
			subsidyConsumerSpend.selectionOfRollOverSubsidy(requiredData.get(0), currentDate, requiredData.get(9),
					requiredData.get(8), requiredData.get(11));
			login.logout();
			browser.close();

			// Again Launch V5 Device to do another Transaction with Subsidy Balance
			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			price = subsidyConsumerSpend.readPriceFromV5Transaction(product.get(1), orderPageData.get(0),
					rstV5DeviceData.get(CNV5Device.EMAIL_ID), rstV5DeviceData.get(CNV5Device.PIN),
					rstV5DeviceData.get(CNV5Device.PAYMENTS_PAGE));
			foundation.threadWait(Constants.THREE_SECOND);
			browser.close();

			// Navigate to ADM
			subsidyConsumerSpend.loginToADMWithSuperCredentials();

			// Read Transaction ID
			transID = subsidyConsumerSpend.readTransactionID(menu.get(1),
					rstConsumerSearchData.get(CNConsumerSearch.SEARCH), rstV5DeviceData.get(CNV5Device.LOCATION));

			// Select Report, Data and Location to run Report
			subsidyConsumerSpend.selectAndRunReport(menu.get(0), rstReportListData.get(CNReportList.REPORT_NAME),
					rstReportListData.get(CNReportList.DATE_RANGE), rstV5DeviceData.get(CNV5Device.LOCATION), transID);

			// Search with Transaction ID and get the data
			subsidyConsumerSpend.getTblRecordsUI();
			subsidyConsumerSpend.verifyReportHeaders(rstConsumerSearchData.get(CNConsumerSearch.COLUMN_NAME));
			subsidyConsumerSpend.verifyCommonValueContentofTableRecord(columnName.get(0),
					rstV5DeviceData.get(CNV5Device.LOCATION));
			subsidyConsumerSpend.verifyCommonValueContentofTableRecord(columnName.get(1), details.get(1));
			subsidyConsumerSpend.verifyCommonValueContentofTableRecord(columnName.get(2),
					rstConsumerSearchData.get(CNConsumerSearch.FIRST_NAME));
			subsidyConsumerSpend.verifyCommonValueContentofTableRecord(columnName.get(3),
					rstConsumerSearchData.get(CNConsumerSearch.ATTRIBUTE));
			subsidyConsumerSpend.verifyCommonValueContentofTableRecord(columnName.get(4), details.get(0));
			subsidyConsumerSpend.verifyCommonValueContentofTableRecord(columnName.get(5),
					rstV5DeviceData.get(CNV5Device.EMAIL_ID));
			subsidyConsumerSpend.verifyCommonValueContentofTableRecord(columnName.get(6), subsidyGroup.get(0));
			subsidyConsumerSpend.verifyCommonValueContentofTableRecord(columnName.get(7), transID);
			subsidyConsumerSpend.verifyCommonValueContentofTableRecord(columnName.get(8), date);
			price = price.replace("$", "");
			price = price.replace(".", "");
			price = price.replace("0", "");
			subsidyConsumerSpend.verifyCommonValueContentofTableRecord(columnName.get(9), price);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/*
	 * This Method is for Product Canned Report Data Validation
	 * 
	 * @author ravindhara Date: 20-09-2022
	 */
	@Test(description = "204450-Verify the Data Validation of Product Canned Report")
	public void productCannedReportDataValidation() {
		try {
			final String CASE_NUM = "204450";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);
			rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);

			String reportName = rstReportListData.get(CNReportList.REPORT_NAME);
			String location = propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE);
			String productName = rstProductSummaryData.get(CNProductSummary.PRODUCT_NAME);
			String scanCode = rstProductSummaryData.get(CNProductSummary.SCAN_CODE);

			List<String> menu = Arrays
					.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			// process sales API to generate data
			ProductCannedReport.processAPI(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));

			// Select the Report Date range and Location and run report
			navigationBar.navigateToMenuItem(menu.get(1));
			reportList.selectReport(reportName);
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));
			reportList.selectLocation(location);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(ReportList.BTN_RUN_REPORT);
			foundation.waitforElement(ProductSales.LBL_REPORT_NAME, Constants.SHORT_TIME);
			ProductCannedReport.verifyReportName(location);

			// Read the Report the Data
			ProductCannedReport.getTblRecordsUI();
			ProductCannedReport.getIntialData().putAll(ProductCannedReport.getReportsData());

			List<String> reason = Arrays
					.asList(rstProductSummaryData.get(CNProductSummary.REQUIRED_DATA).split(Constants.DELIMITER_HASH));

			// navigate to location and Inventory section & Updating Spoil Damage of product
			navigationBar.navigateToMenuItem(menu.get(0));
			locationList.selectLocationName(
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));
			foundation.waitforElement(LocationSummary.LNK_INVENTORY, Constants.SHORT_TIME);
			locationSummary.selectTab(rstLocationSummaryData.get(CNLocationSummary.TAB_NAME));

			textBox.enterText(LocationSummary.TXT_INVENTORY_FILTER, scanCode);
			foundation.threadWait(Constants.ONE_SECOND);

			String inventoryValue = locationSummary.getInventoryValue(scanCode);

			// Updating the Inventory of the product
			locationSummary.updateInventory(scanCode, ProductCannedReport.decrementedInventoryValue(inventoryValue),
					reason.get(0));

			// Updating Shrink of product
			foundation.waitforElement(LocationSummary.LNK_INVENTORY, Constants.SHORT_TIME);
			locationSummary.selectTab(rstLocationSummaryData.get(CNLocationSummary.TAB_NAME));

			textBox.enterText(LocationSummary.TXT_INVENTORY_FILTER, scanCode);
			foundation.threadWait(Constants.ONE_SECOND);

			String inventoryValue1 = locationSummary.getInventoryValue(scanCode);

			// Updating the Inventory of the product
			locationSummary.updateInventory(scanCode, ProductCannedReport.decrementedInventoryValue(inventoryValue1),
					reason.get(1));

			// navigate to Products section & Updating Min & Max quantity of product
			foundation.waitforElement(LocationSummary.TAB_PRODUCTS, Constants.SHORT_TIME);
			foundation.click(LocationSummary.TAB_PRODUCTS);
			foundation.WaitForAjax(5000);
			foundation.waitforElement(LocationSummary.TXT_PRODUCT_FILTER, Constants.SHORT_TIME);
			foundation.threadWait(Constants.MEDIUM_TIME);
			// updating Min Stock
			textBox.enterText(LocationSummary.TXT_PRODUCT_FILTER, productName);
			foundation.threadWait(Constants.MEDIUM_TIME);
			CustomisedAssert.assertTrue(foundation.getText(LocationSummary.PRODUCT_NAME).equals(productName));
			locationSummary.enterMinStock(productName, rstProductSummaryData.get(CNProductSummary.GPCMIN));
			foundation.threadWait(Constants.TWO_SECOND);
			// updating Min Stock
			locationSummary.enterMaxStock(productName, rstProductSummaryData.get(CNProductSummary.GPCMAX));
			foundation.click(LocationSummary.TXT_PRODUCT_FILTER);

			// process sales API to generate data again
			ProductCannedReport.processAPI(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));

			foundation.threadWait(Constants.TWO_SECOND);
			// rerun and reread report
			navigationBar.navigateToMenuItem(menu.get(1));

			// Select the Report Date range and Location and Re-run report
			reportList.selectReport(reportName);
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));
			reportList.selectLocation(location);
			foundation.threadWait(Constants.SHORT_TIME);

			foundation.click(ReportList.BTN_RUN_REPORT);
			foundation.threadWait(Constants.TWO_SECOND);
			ProductCannedReport.getTblRecordsUI();
			ProductCannedReport.getRequiredRecord(productName);

			// update the report date based on calculation
			String productPrice = rstProductSummaryData.get(CNProductSummary.PRICE);

			ProductCannedReport.updateData(ProductCannedReport.getTableHeaders().get(0), productName);
			String salesUnits = ProductCannedReport.saleCount(ProductCannedReport.getTableHeaders().get(1));
			ProductCannedReport.calculateSales(ProductCannedReport.getTableHeaders().get(2), productPrice);
			String spoilUnits = ProductCannedReport.saleCount(ProductCannedReport.getTableHeaders().get(4));
			String shrinkUnits = ProductCannedReport.saleCount(ProductCannedReport.getTableHeaders().get(6));
			ProductCannedReport.calculatePercent(ProductCannedReport.getTableHeaders().get(5), salesUnits, spoilUnits);
			ProductCannedReport.calculatePercent(ProductCannedReport.getTableHeaders().get(7), salesUnits, shrinkUnits);

			// verify report headers
			ProductCannedReport.verifyReportHeaders(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME));

			// verify report data
			ProductCannedReport.verifyReportData();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/*
	 * Credit Transaction Spend Report Data Validation
	 * 
	 * @author ravindhara Date: 22-09-2022
	 * 
	 * @date: 24-08-2022
	 */
	@Test(description = "204887-This test validates Credit Transaction Report Data Calculation")
	public void creditTransactionReportData() {
		try {

			final String CASE_NUM = "204887";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			// process sales API to generate data
			creditTransaction.processAPI(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));

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

			creditTransaction.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));

			textBox.enterText(MemberPurchaseSummaryReport.TXT_SEARCH,
					(String) creditTransaction.getJsonData().get(Reports.TRANS_ID));
			creditTransaction.getTblRecordsUI();
			creditTransaction.getIntialData().putAll(creditTransaction.getReportsData());
			creditTransaction.getRequiredRecord((String) creditTransaction.getJsonData().get(Reports.TRANS_DATE_TIME));

			List<String> requiredData = Arrays
					.asList(rstProductSummaryData.get(CNProductSummary.REQUIRED_DATA).split(Constants.DELIMITER_HASH));

			// apply calculation and update data
			creditTransaction.updateData(creditTransaction.getTableHeaders().get(0),
					(String) creditTransaction.getJsonData().get(Reports.TRANS_DATE_TIME));
			creditTransaction.updateData(creditTransaction.getTableHeaders().get(1),
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));
			creditTransaction.updateData(creditTransaction.getTableHeaders().get(2),
					(String) creditTransaction.getJsonData().get(Reports.TRANS_ID));
			creditTransaction.updateData(creditTransaction.getTableHeaders().get(3),
					creditTransaction.getRequiredJsonData().get(0));
			creditTransaction.updateData(creditTransaction.getTableHeaders().get(4), requiredData.get(0));
			creditTransaction.updateData(creditTransaction.getTableHeaders().get(5),
					creditTransaction.getRequiredJsonData().get(1));
			creditTransaction.updateData(creditTransaction.getTableHeaders().get(6),
					creditTransaction.getRequiredJsonData().get(2));
			creditTransaction.updateData(creditTransaction.getTableHeaders().get(7),
					creditTransaction.getRequiredJsonData().get(3));
			creditTransaction.updateData(creditTransaction.getTableHeaders().get(9), requiredData.get(1));
			creditTransaction.updateData(creditTransaction.getTableHeaders().get(10), requiredData.get(2));

			// verify report headers
			creditTransaction.verifyReportHeaders(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME));

			// verify report data
			creditTransaction.verifyReportData();

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * Cashout Log Report data validation
	 * 
	 * @author KarthikR Date: 22-09-2022
	 */
	@Test(description = "120168 - CashOut Log Report data validation")
	public void cashOutLogReportDataValidation() {
		try {
			final String CASE_NUM = "120168";

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			// Login to ADM with Super Credentials
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// process Kiosk Cashout API to generate data
			cashOutLog.processAPI(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));
			cashOutLog.getCashoutJsonData();

			// Navigate To Report Tab and Select the Report Date range & Location, run
			// report
			cashOutLog.selectAndRunReport(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM),
					rstReportListData.get(CNReportList.REPORT_NAME), rstReportListData.get(CNReportList.DATE_RANGE),
					rstProductSummaryData.get(CNProductSummary.LOCATION_NAME));

			// Read Report Data from UI
			textBox.enterText(CashoutLog.TXT_SEARCH_FILTER, cashOutLog.getRequiredCashOutJsonData().get(0));
			cashOutLog.getCashOutLog();

			// verify Report Headers
			cashOutLog.verifyReportHeaders(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME));

			// verify Report Data
			cashOutLog.verifyKCORecord();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * This Method is for Account Profitability Report Data Validation
	 * 
	 * @author ravindhara Date: 22-07-2022
	 */
	@Test(description = "205004-Verify the Data Validation of Account Profitability Report ")
	public void AccountProfitabilityReportDataValidation() {
		try {
			final String CASE_NUM = "205004";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			String location = propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE);

			// Select the Report Date range and Location and run report
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));
			reportList.selectLocation(location);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(ReportList.BTN_RUN_REPORT);
			foundation.waitforElement(ProductSales.LBL_REPORT_NAME, Constants.SHORT_TIME);

			accountProfitability.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));

			// Read the Report the Data
			accountProfitability.getTblRecordsUI();
			accountProfitability.getIntialData().putAll(accountProfitability.getReportsData());

			// process sales API to generate data
			accountProfitability.processAPI(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));

			// rerun and reread report
			foundation.click(ReportList.BTN_RUN_REPORT);
			foundation.threadWait(Constants.TWO_SECOND);
			accountProfitability.getTblRecordsUI();

			// update the report date based on calculation
			String productPrice = rstProductSummaryData.get(CNProductSummary.PRICE);
			String tax = rstProductSummaryData.get(CNProductSummary.TAX);
			String deposit = rstProductSummaryData.get(CNProductSummary.DEPOSIT_CATEGORY);

			accountProfitability.updateData(accountProfitability.getTableHeaders().get(0), location);
			accountProfitability.calculateAmount(accountProfitability.getTableHeaders().get(1), productPrice, tax);
			accountProfitability.calculateTax(accountProfitability.getTableHeaders().get(2), tax);
			accountProfitability.calculateDeposit(accountProfitability.getTableHeaders().get(3), deposit);
			accountProfitability.calculateNetSales(accountProfitability.getTableHeaders().get(4));

			// verify report headers
			accountProfitability.verifyReportHeaders(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME));

			// verify report data
			accountProfitability.verifyReportData();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * This Method is for Financial Canned Report Data Validation 204450
	 * 
	 * @author ravindhara Date: 29-09-2022
	 */
	@Test(description = "203570-Verify the Data Validation of Financial Canned Report")
	public void financialCannedReportDataValidation() {
		try {
			final String CASE_NUM = "203570";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);

			String reportName = rstReportListData.get(CNReportList.REPORT_NAME);
			String location = propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE);
			String scanCode = rstProductSummaryData.get(CNProductSummary.SCAN_CODE);

			List<String> menu = Arrays
					.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Select the Report Date range and Location and run report
			navigationBar.navigateToMenuItem(menu.get(1));
			reportList.selectReport(reportName);

			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));
			reportList.selectLocation(location);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(ReportList.BTN_RUN_REPORT);
			foundation.waitforElement(ProductSales.LBL_REPORT_NAME, Constants.SHORT_TIME);

			financialCanned.verifyReportName(reportName);

			// Read the Report the Data
			financialCanned.getTblRecordsUI();
			financialCanned.getIntialData().putAll(financialCanned.getReportsData());

			List<String> reason = Arrays
					.asList(rstProductSummaryData.get(CNProductSummary.REQUIRED_DATA).split(Constants.DELIMITER_HASH));

			// navigate to location and Inventory section & Updating Spoil Damage of product
			navigationBar.navigateToMenuItem(menu.get(0));
			locationList.selectLocationName(
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));
			foundation.waitforElement(LocationSummary.LNK_INVENTORY, Constants.SHORT_TIME);
			locationSummary.selectTab(rstLocationSummaryData.get(CNLocationSummary.TAB_NAME));

			textBox.enterText(LocationSummary.TXT_INVENTORY_FILTER, scanCode);
			foundation.threadWait(Constants.ONE_SECOND);

			String inventoryValue = locationSummary.getInventoryValue(scanCode);

			// Updating the Inventory of the product
			locationSummary.updateInventory(scanCode, financialCanned.decrementedInventoryValue(inventoryValue),
					reason.get(0));

			// Updating Shrink of product
			foundation.waitforElement(LocationSummary.LNK_INVENTORY, Constants.SHORT_TIME);
			locationSummary.selectTab(rstLocationSummaryData.get(CNLocationSummary.TAB_NAME));

			textBox.enterText(LocationSummary.TXT_INVENTORY_FILTER, scanCode);
			foundation.threadWait(Constants.ONE_SECOND);

			String inventoryValue1 = locationSummary.getInventoryValue(scanCode);

			// Updating the Inventory of the product
			locationSummary.updateInventory(scanCode, financialCanned.decrementedInventoryValue(inventoryValue1),
					reason.get(1));

			// process sales API to generate data again
			financialCanned.processAPI(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));

			foundation.threadWait(Constants.TWO_SECOND);
			// rerun and reread report
			navigationBar.navigateToMenuItem(menu.get(1));

			// Select the Report Date range and Location and Re-run report
			reportList.selectReport(reportName);
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));
			reportList.selectLocation(location);
			foundation.threadWait(Constants.SHORT_TIME);

			foundation.click(ReportList.BTN_RUN_REPORT);
			foundation.threadWait(Constants.TWO_SECOND);
			financialCanned.getTblRecordsUI();

			// update the report date based on calculation
			String productPrice = rstProductSummaryData.get(CNProductSummary.PRICE);
			String cost = rstProductSummaryData.get(CNProductSummary.COST);
			String tax = rstProductSummaryData.get(CNProductSummary.TAX);

			financialCanned.updateData(financialCanned.getTableHeaders().get(0), location);
			String sales = financialCanned.calculateSalesAmount(financialCanned.getTableHeaders().get(1), productPrice,
					tax);
			String productCost = financialCanned.calculateCost(financialCanned.getTableHeaders().get(2), cost);
			String ProductCostPercentage = financialCanned.calculatePercent(financialCanned.getTableHeaders().get(3),
					productCost, sales);
			String spoilAmount = financialCanned.calculateSpoilDamagesAmount(financialCanned.getTableHeaders().get(4),
					cost);
			String spoilPercentage = financialCanned.calculatePercent(financialCanned.getTableHeaders().get(5),
					spoilAmount, sales);
			String shortAmount = financialCanned.calculateSpoilDamagesAmount(financialCanned.getTableHeaders().get(8),
					cost);
			String shortPercentage = financialCanned.calculatePercent(financialCanned.getTableHeaders().get(9),
					shortAmount, sales);

			financialCanned.calculateGrossMargin(financialCanned.getTableHeaders().get(10), ProductCostPercentage,
					spoilPercentage, shortPercentage);
			double updatedLastYearAmount = financialCanned
					.updateLastYearAmount(financialCanned.getTableHeaders().get(11));
			financialCanned.updateLastYearPercent(financialCanned.getTableHeaders().get(12), updatedLastYearAmount);

			// verify report headers
			financialCanned.verifyReportHeaders(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME));

			// verify report data
			financialCanned.verifyReportData();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/*
	 * This Method is for Sales By 15 mins Report data validation 203347
	 * 
	 * @author ravindhara Date: 06-10-2022
	 * 
	 */
	@Test(description = "205070-Verify the Data Validation of Sales By 15 mins Report")
	public void salesBy15MinsReportDataValidation() {
		try {
			final String CASE_NUM = "205070";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			String locationName = propertyFile.readPropertyFile(Configuration.CURRENT_LOC,
					FilePath.PROPERTY_CONFIG_FILE);

			// process sales API to generate data
			salesBy15Minutes.processAPI(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));

			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location and run report
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));
			reportList.selectLocation(locationName);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(ReportList.BTN_RUN_REPORT);
			foundation.waitforElement(SalesSummaryAndCost.LBL_REPORT_NAME, Constants.SHORT_TIME);
			salesBy15Minutes.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));

			// Read the Report the Data
			String timePeriod = salesBy15Minutes
					.getTimePeroid(rstProductSummaryData.get(CNProductSummary.REQUIRED_DATA));
			textBox.enterText(SalesBy15Minutes.TXT_SEARCH, timePeriod);
			salesBy15Minutes.getTblRecordsUI();
			salesBy15Minutes.getIntialData().putAll(salesBy15Minutes.getReportsData());
			salesBy15Minutes.getUpdatedTableFooters().putAll(salesBy15Minutes.getTableFooters());

			// process sales API to generate data
			salesBy15Minutes.processAPI(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));

			// rerun and reread report
			foundation.click(ReportList.BTN_RUN_REPORT);
			foundation.threadWait(Constants.TWO_SECOND);

			textBox.enterText(SalesBy15Minutes.TXT_SEARCH, timePeriod);
			salesBy15Minutes.getTblRecordsUI();

			salesBy15Minutes.getRowCount();
			salesBy15Minutes.getJsonSalesData();

			// update the report date based on calculation
			String productPrice = rstProductSummaryData.get(CNProductSummary.PRICE);
			String tax = rstProductSummaryData.get(CNProductSummary.TAX);

			// Updating Table data
			salesBy15Minutes.grossSales(salesBy15Minutes.getTableHeaders().get(1), productPrice, tax);
			salesBy15Minutes.calculateAmount(salesBy15Minutes.getTableHeaders().get(2),
					salesBy15Minutes.getRequiredJsonData().get(2));
			salesBy15Minutes.calculateAmount(salesBy15Minutes.getTableHeaders().get(3),
					salesBy15Minutes.getRequiredJsonData().get(0));
			salesBy15Minutes.TrasactionCount(salesBy15Minutes.getTableHeaders().get(4));

			// Updating Footer data
			salesBy15Minutes.grossSalesOfFooter(salesBy15Minutes.getTableHeaders().get(1), productPrice, tax);
			salesBy15Minutes.calculateAmountOfFooter(salesBy15Minutes.getTableHeaders().get(2),
					salesBy15Minutes.getRequiredJsonData().get(2));
			salesBy15Minutes.calculateAmountOfFooter(salesBy15Minutes.getTableHeaders().get(3),
					salesBy15Minutes.getRequiredJsonData().get(0));
			salesBy15Minutes.TrasactionCountOfFooter(salesBy15Minutes.getTableHeaders().get(4));

			// verify report headers
			salesBy15Minutes.verifyReportHeaders(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME));

			// verify report data
			salesBy15Minutes.verifyReportData();

			// verify report total data
			salesBy15Minutes.verifyReportFootertData();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/*
	 * This Method is for Sales By 30 mins Report data validation
	 * 
	 * @author ravindhara Date: 10-10-2022
	 * 
	 */
	@Test(description = "206144-Verify the Data Validation of Sales By 30 mins Report")
	public void salesBy30MinsReportDataValidation() {
		try {
			final String CASE_NUM = "206144";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			String locationName = propertyFile.readPropertyFile(Configuration.CURRENT_LOC,
					FilePath.PROPERTY_CONFIG_FILE);

			// process sales API to generate data
			salesBy30Minutes.processAPI(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));

			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location and run report
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));
			reportList.selectLocation(locationName);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(ReportList.BTN_RUN_REPORT);
			foundation.waitforElement(SalesSummaryAndCost.LBL_REPORT_NAME, Constants.SHORT_TIME);
			salesBy30Minutes.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));

			// Read the Report the Data
			String timePeriod = salesBy30Minutes
					.getTimePeroid(rstProductSummaryData.get(CNProductSummary.REQUIRED_DATA));
			textBox.enterText(SalesBy30Minutes.TXT_SEARCH, timePeriod);
			salesBy30Minutes.getTblRecordsUI();
			salesBy30Minutes.getIntialData().putAll(salesBy30Minutes.getReportsData());
			salesBy30Minutes.getUpdatedTableFooters().putAll(salesBy30Minutes.getTableFooters());

			// process sales API to generate data
			salesBy30Minutes.processAPI(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));

			// rerun and reread report
			foundation.click(ReportList.BTN_RUN_REPORT);
			foundation.threadWait(Constants.TWO_SECOND);

			textBox.enterText(SalesBy15Minutes.TXT_SEARCH, timePeriod);
			salesBy30Minutes.getTblRecordsUI();

			salesBy30Minutes.getRowCount();
			salesBy30Minutes.getJsonSalesData();

			// update the report date based on calculation
			String productPrice = rstProductSummaryData.get(CNProductSummary.PRICE);
			String tax = rstProductSummaryData.get(CNProductSummary.TAX);

			// Updating Table data
			salesBy30Minutes.grossSales(salesBy30Minutes.getTableHeaders().get(1), productPrice, tax);
			salesBy30Minutes.calculateAmount(salesBy30Minutes.getTableHeaders().get(2),
					salesBy30Minutes.getRequiredJsonData().get(2));
			salesBy30Minutes.calculateAmount(salesBy30Minutes.getTableHeaders().get(3),
					salesBy30Minutes.getRequiredJsonData().get(0));
			salesBy30Minutes.TrasactionCount(salesBy30Minutes.getTableHeaders().get(4));

			// Updating Footer data
			salesBy30Minutes.grossSalesOfFooter(salesBy30Minutes.getTableHeaders().get(1), productPrice, tax);
			salesBy30Minutes.calculateAmountOfFooter(salesBy30Minutes.getTableHeaders().get(2),
					salesBy30Minutes.getRequiredJsonData().get(2));
			salesBy30Minutes.calculateAmountOfFooter(salesBy30Minutes.getTableHeaders().get(3),
					salesBy30Minutes.getRequiredJsonData().get(0));
			salesBy30Minutes.TrasactionCountOfFooter(salesBy30Minutes.getTableHeaders().get(4));

			// verify report headers
			salesBy30Minutes.verifyReportHeaders(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME));

			// verify report data
			salesBy30Minutes.verifyReportData();

			// verify report total data
			salesBy30Minutes.verifyReportFootertData();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * Cancel Report data validation
	 * 
	 * @author KarthikR Date: 26-09-2022
	 */
	@Test(description = "204991 - Cancel Report data validation")
	@Parameters({ "environment" })
	public void cancelReportDataValidation(String environment) {
		try {
			final String CASE_NUM = "204991";

			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			List<String> columnName = Arrays
					.asList(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME).split(Constants.DELIMITER_TILD));

			// Login to ADM with Super Credentials
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// process API POST Request to generate data
			webService.apiReportPostRequest(
					propertyFile.readPropertyFile(Configuration.SALE_CANCEL_TRANS, FilePath.PROPERTY_CONFIG_FILE),
					cancelReport.saleCancelJsonDataUpdate(environment));
			cancelReport.getJsonSalesData();
			cancelReport.getJsonArrayData(CancelReport.jsonData, Reports.ITEMS, Reports.PAYMENTS);

			// Navigate To Report Tab and Select the Report Date range & Location, run
			// report
			cancelReport.selectAndRunReport(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM),
					rstReportListData.get(CNReportList.REPORT_NAME), rstReportListData.get(CNReportList.DATE_RANGE),
					rstProductSummaryData.get(CNProductSummary.LOCATION_NAME));

			// Read Report Data from UI
			cancelReport.readAllRecordsFromCancelReportTable();
			cancelReport.getRequiredRecord(columnName.get(0),
					String.valueOf(CancelReport.REQUIRED_JSON_DATA_LIST.get(0)));

			// verify Report Headers
			cancelReport.verifyReportHeaders(columnName.get(1));

			// verify Location of UI Table Record
			cancelReport.verifyLocation(columnName.get(2));

			// verify Time Cancelled column of UI Table Record
			cancelReport.verifyTimeCancelled(columnName.get(3),
					rstProductSummaryData.get(CNProductSummary.ACTUAL_DATA));

			// verify TransID column of UI Table Record
			cancelReport.verifyTransID(columnName.get(4));

			// verify Items column of UI Table Record
			cancelReport.verifyItems(columnName.get(5));

			// verify Total column of UI Table Record
			cancelReport.verifyTotal(columnName.get(6));

			// verify Name On CC column of UI Table Record
			cancelReport.verifyNameOnCC(columnName.get(7));

			// verify Last 4 Of CC column of UI Table Record
			cancelReport.verifyLast4OfCC(columnName.get(8));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * Balance Report data validation
	 * 
	 * @author KarthikR Date: 29-09-2022
	 */
	@Test(description = "204999 - Balance Report data validation")
	public void balanceReportDataValidation() {
		try {
			final String CASE_NUM = "204999";

			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);
			rstConsumerSearchData = dataBase.getConsumerSearchData(Queries.CONSUMER_SEARCH, CASE_NUM);
			rstConsumerSummaryData = dataBase.getConsumerSummaryData(Queries.CONSUMER_SUMMARY, CASE_NUM);

			List<String> columnName = Arrays
					.asList(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME).split(Constants.DELIMITER_TILD));
			List<String> requiredData = Arrays
					.asList(rstProductSummaryData.get(CNProductSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> menu = Arrays
					.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));

			// Login to ADM with Super Credentials
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Navigate To Report Tab and Select the Report Date range & Location, run
			// report
			balanceReport.selectAndRunReport(menu.get(0), rstReportListData.get(CNReportList.REPORT_NAME),
					rstProductSummaryData.get(CNProductSummary.LOCATION_NAME));

			// Read Report Data from UI
			balanceReport.readBalanceReportTableHeaders();

			// verify Report Headers
			textBox.enterText(BalanceReport.TXT_SEARCH, requiredData.get(1));
			foundation.threadWait(Constants.SHORT_TIME);
			balanceReport.readAllRecordsFromBalanceReportTable(columnName.get(2));
			balanceReport.getInitialReportsData().putAll(BalanceReport.reportsData);

			// Navigate to Admin > Consumer
			navigationBar.navigateToMenuItem(menu.get(1));
			foundation.waitforElementToBeVisible(ConsumerSearch.TXT_CONSUMER_SEARCH, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSearch.TXT_CONSUMER_SEARCH));

			// Search for Consumer
			consumerSearch.enterSearchFields(rstConsumerSearchData.get(CNConsumerSearch.SEARCH_BY),
					rstConsumerSearchData.get(CNConsumerSearch.CONSUMER_ID),
					rstProductSummaryData.get(CNProductSummary.LOCATION_NAME),
					rstConsumerSearchData.get(CNConsumerSearch.STATUS));
			foundation.click(consumerSearch.objFirstNameCell(requiredData.get(0)));
			foundation.threadWait(Constants.SHORT_TIME);

			// Read Balance from Consumer Summary page and do Adjust on Consumer Balance
			balanceReport.getADMData().add(String.valueOf(consumerSummary.getBalance()));
			foundation.click(ConsumerSummary.BTN_ADJUST);
			foundation.threadWait(Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSummary.LBL_POPUP_ADJUST_BALANCE));
			balanceReport.getADMData().add(rstConsumerSummaryData.get(CNConsumerSummary.ADJUST_BALANCE));
			double updatedbalance = Double.parseDouble(balanceReport.getADMData().get(0))
					- Double.parseDouble(balanceReport.getADMData().get(1));
			textBox.enterText(ConsumerSummary.TXT_ADJUST_BALANCE, Double.toString(updatedbalance));
			dropdown.selectItem(ConsumerSummary.DPD_REASON, rstConsumerSummaryData.get(CNConsumerSummary.REASON),
					Constants.TEXT);
			foundation.click(ConsumerSummary.BTN_REASON_SAVE);
			foundation.threadWait(Constants.SHORT_TIME);
			balanceReport.getADMData().add(String.valueOf(updatedbalance));

			// Read Balance of Subsidy from Consumer Summary page and do Adjust on Subsidy
			// Balance
			foundation.click(ConsumerSummary.BTN_SUBSIDY_ADJUST);
			foundation.threadWait(Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSummary.LBL_POPUP_ADJUST_BALANCE));
			balanceReport.getADMData()
					.add(String.valueOf(foundation.getText(ConsumerSummary.SUBSIDY_READ_BALANCE).replace("$", "")));
			double updatedSubisdybalance = Double.parseDouble(balanceReport.getADMData().get(3))
					+ Double.parseDouble(balanceReport.getADMData().get(1));
			textBox.enterText(ConsumerSummary.TXT_ADJUST_BALANCE, Double.toString(updatedSubisdybalance));
			dropdown.selectItem(ConsumerSummary.DPD_REASON, rstConsumerSummaryData.get(CNConsumerSummary.REASON),
					Constants.TEXT);
			foundation.click(ConsumerSummary.BTN_REASON_SAVE);
			foundation.threadWait(Constants.SHORT_TIME);
			balanceReport.getADMData().add(String.valueOf(updatedSubisdybalance));

			// Navigate To Report Tab and Select the Report Date range & Location, run
			// report
			balanceReport.selectAndRunReport(menu.get(0), rstReportListData.get(CNReportList.REPORT_NAME),
					rstProductSummaryData.get(CNProductSummary.LOCATION_NAME));

			// Read Updated Report Data
			textBox.enterText(BalanceReport.TXT_SEARCH, requiredData.get(1));
			foundation.threadWait(Constants.SHORT_TIME);
			balanceReport.readAllRecordsFromBalanceReportTable(columnName.get(2));

			// Update Consumer Balance
			balanceReport.updateConsumerBalance();

			// Update Subsidy Balance
			balanceReport.updateSubsidyBalance();

			// Verify Report Headers
			balanceReport.verifyReportHeaders(columnName.get(1));

			// Verify Report Data
			balanceReport.verifyReportRecords(requiredData.get(1));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * Account Funding Report Data Validation
	 * 
	 * @author KarthikR Date: 12-09-2022
	 */
	@Test(description = "119931 - Account Funding Report data validation")
	public void accountFundingReportDataValidation() {
		try {
			final String CASE_NUM = "119931";

			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);
			rstConsumerSearchData = dataBase.getConsumerSearchData(Queries.CONSUMER_SEARCH, CASE_NUM);
			rstConsumerSummaryData = dataBase.getConsumerSummaryData(Queries.CONSUMER_SUMMARY, CASE_NUM);

			String locationName = propertyFile.readPropertyFile(Configuration.CURRENT_LOC,
					FilePath.PROPERTY_CONFIG_FILE);
			List<String> requiredData = Arrays
					.asList(rstProductSummaryData.get(CNProductSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> menus = Arrays
					.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
			List<String> columnName = Arrays
					.asList(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME).split(Constants.DELIMITER_TILD));

			// Login to ADM with Super Credentials
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// process sales API to generate data
			accountFunding.processAPI(rstProductSummaryData.get(CNProductSummary.ACTUAL_DATA), requiredData.get(0),
					rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));

			// Navigate To Report Tab and Select the Report Date range & Location, run
			// report
			accountFunding.selectAndRunReport(menus.get(0), rstReportListData.get(CNReportList.REPORT_NAME),
					rstReportListData.get(CNReportList.DATE_RANGE), locationName);

			// Read Account Funding Report Data
			accountFunding.getAccountFundingHeaders();
			accountFunding.getAccountFunding();
			accountFunding.getInitialReportsData().putAll(AccountFunding.reportsData);

			// Navigate To Admin > Consumer and search for Consumer
			navigationBar.navigateToMenuItem(menus.get(1));
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSearch.TXT_CONSUMER_SEARCH));
			dropdown.selectItem(ConsumerSearch.DPD_SEARCH_BY, rstConsumerSearchData.get(CNConsumerSearch.SEARCH_BY),
					Constants.TEXT);
			foundation.click(ConsumerSearch.CLEAR_SEARCH);
			textBox.enterText(ConsumerSearch.TXT_SEARCH, rstConsumerSearchData.get(CNConsumerSearch.CONSUMER_ID));
			dropdown.selectItem(ConsumerSearch.DPD_LOCATION, locationName, Constants.TEXT);
			foundation.click(ConsumerSearch.BTN_GO);
			foundation.scrollIntoViewElement(ConsumerSearch.TBL_CONSUMERS);
			foundation.threadWait(Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSearch.TBL_CONSUMERS));
			foundation.click(ConsumerSearch.LNK_FIRST_ROW);
			foundation.threadWait(Constants.SHORT_TIME);

			// Read Balance from Consumer Summary page and do Adjust on Report
			accountFunding.getADMData().add(String.valueOf(consumerSummary.getBalance()));
			foundation.click(ConsumerSummary.BTN_ADJUST);
			foundation.threadWait(Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSummary.LBL_POPUP_ADJUST_BALANCE));
			accountFunding.getADMData().add(rstConsumerSummaryData.get(CNConsumerSummary.ADJUST_BALANCE));
			double updatedbalance = Double.parseDouble(accountFunding.getADMData().get(0))
					+ Double.parseDouble(accountFunding.getADMData().get(1));
			textBox.enterText(ConsumerSummary.TXT_ADJUST_BALANCE, Double.toString(updatedbalance));
			dropdown.selectItem(ConsumerSummary.DPD_REASON, rstConsumerSummaryData.get(CNConsumerSummary.REASON),
					Constants.TEXT);
			foundation.click(ConsumerSummary.BTN_REASON_SAVE);
			foundation.threadWait(Constants.SHORT_TIME);

			// process sales API again to generate new data
			accountFunding.processAPI(rstProductSummaryData.get(CNProductSummary.ACTUAL_DATA), requiredData.get(0),
					rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));

			foundation.threadWait(Constants.THREE_SECOND);
			// Navigate to Report again to read Updated Report Data
			accountFunding.selectAndRunReport(menus.get(0), rstReportListData.get(CNReportList.REPORT_NAME),
					rstReportListData.get(CNReportList.DATE_RANGE), locationName);
			accountFunding.getAccountFunding();

			// Update Date
			accountFunding.getInitialReportsData().get(0).put(AccountFunding.tableHeaders.get(0),
					accountFunding.getTime());

			// Update Operator Credit
			accountFunding.updateOperatorCredit();

			// Update Consumer Credit
			accountFunding.updateCreditAndCash(AccountFunding.tableHeaders.get(3));

			// Update Kiosk Credit
			accountFunding.updateCreditAndCash(AccountFunding.tableHeaders.get(4));

			// Update Kiosk Cash
			accountFunding.updateCreditAndCash(AccountFunding.tableHeaders.get(5));

			// Update Remaining Account Balances
			accountFunding.updateRemainingAccountBalances();

			// Update Account Sales
			accountFunding.updateSales(AccountFunding.tableHeaders.get(8));

			// Update Credit Sales
			accountFunding.updateSales(AccountFunding.tableHeaders.get(9));

			// Update Total Sales
			accountFunding.calculateTotalSales();

			// Verify Report Headers
			accountFunding.verifyReportHeaders(columnName.get(1));

			// Verify Report Data
			accountFunding.verifyReportRecords();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * Cash Audit Report data validation
	 * 
	 * @author KarthikR Date: 16-09-2022
	 */
	@Test(description = "120109 - Cash Audit Report data validation")
	public void cashAuditReportDataValidation() {
		try {
			final String CASE_NUM = "120109";

			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);
			rstConsumerSearchData = dataBase.getConsumerSearchData(Queries.CONSUMER_SEARCH, CASE_NUM);

			String locationName = propertyFile.readPropertyFile(Configuration.CURRENT_LOC,
					FilePath.PROPERTY_CONFIG_FILE);
			List<String> menus = Arrays
					.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));

			// Login to ADM with Super Credentials
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// process sales API to generate data
			cashAudit.processAPI(rstProductSummaryData.get(CNProductSummary.REQUIRED_DATA),
					rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));
			cashAudit.getCashoutJsonData();

			// Navigate To Report Tab and Select the Report Date range & Location, run
			// report
			cashAudit.selectAndRunReport(menus.get(0), rstReportListData.get(CNReportList.REPORT_NAME),
					rstReportListData.get(CNReportList.DATE_RANGE), locationName);

			// read Report Data
			cashAudit.readAllRecordsFromCashAuditTable();
			cashAudit.getLastPickupData().putAll(cashAudit.reportSecondLayerData);

			// verify Report Headers
			cashAudit.verifyReportHeaders(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME));

			// verify GMA Record
			cashAudit.updateGMARecord(rstConsumerSearchData.get(CNConsumerSearch.CONSUMER_ID));
			cashAudit.verifyGMARecord();

			// verify KCO Record
			cashAudit.updateKCORecord();
			cashAudit.verifyKCORecord();

			// verify TotalPlus Total
			// cashAudit.verifyTotal(cashAudit.tableHeaders.get(3));

			// verify TotalMinus Total
			cashAudit.verifyTotal(cashAudit.tableHeaders.get(4));

			// verify Last PickUp Record
			cashAudit.verifyLastPickUp();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * This Method is for Consumer Feedback Survey Report Data Validation for Bad
	 * Feedback
	 * 
	 * @author ravindhara Date: 12-10-2022
	 */
	@Test(priority = 1, description = "206251-Verifying and Validating the Consumer Feedback Survey Report Data")
	public void consumerFeedbackSurveyReportDataValidationForSadFeedback() {
		final String CASE_NUM = "206251";

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
			foundation.threadWait(Constants.SHORT_TIME);
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

			foundation.threadWait(Constants.SHORT_TIME);

			consumerFeedbackSurvey.getTblRecordsUI();
			consumerFeedbackSurvey.getIntialData().putAll(consumerFeedbackSurvey.getReportsData());

			// apply calculation and update data
			consumerFeedbackSurvey.updateData(consumerFeedbackSurvey.getTableHeaders().get(1),
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));
			consumerFeedbackSurvey.updateTotalFeedbackCount(consumerFeedbackSurvey.getTableHeaders().get(3));
			consumerFeedbackSurvey.updateCount(consumerFeedbackSurvey.getTableHeaders().get(4));

			login.logout();
			browser.close();

			// Launch V5 Device and purchasing a product with Sad Feedback
			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			consumerFeedbackSurvey.transactionThroughDevice(product, paymentEmailDetails.get(0),
					paymentEmailDetails.get(1), requiredData.get(2), ConsumerFeedbackSurvey.SAD_EMOJI_BTN,
					order.objText(rstProductSummaryData.get(CNProductSummary.DESCRIPTION)));
			browser.close();

			// Navigate to Reports
			foundation.threadWait(Constants.SHORT_TIME);
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

			foundation.threadWait(Constants.SHORT_TIME);

			consumerFeedbackSurvey.getTblRecordsUI();

			// verify report headers
			consumerFeedbackSurvey.verifyReportHeaders(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME));

			// verify report data
			consumerFeedbackSurvey.verifyReportData();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * This Method is for Consumer Feedback Survey Report Data Validation for
	 * Neutral Feedback
	 * 
	 * @author ravindhara Date: 12-10-2022
	 */
	@Test(priority = 1, description = "206251-Verifying and Validating the Consumer Feedback Survey Report Data Neutral Feedback")
	public void consumerFeedbackSurveyReportDataValidationForNeutralFeedback() {
		final String CASE_NUM = "206252";

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
			foundation.threadWait(Constants.SHORT_TIME);
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

			foundation.threadWait(Constants.SHORT_TIME);

			consumerFeedbackSurvey.getTblRecordsUI();
			consumerFeedbackSurvey.getIntialData().putAll(consumerFeedbackSurvey.getReportsData());

			// apply calculation and update data
			consumerFeedbackSurvey.updateData(consumerFeedbackSurvey.getTableHeaders().get(1),
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));
			consumerFeedbackSurvey.updateTotalFeedbackCount(consumerFeedbackSurvey.getTableHeaders().get(3));
			consumerFeedbackSurvey.updateCount(consumerFeedbackSurvey.getTableHeaders().get(5));

			login.logout();
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
			foundation.threadWait(Constants.SHORT_TIME);
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

			foundation.threadWait(Constants.SHORT_TIME);

			consumerFeedbackSurvey.getTblRecordsUI();

			// verify report headers
			consumerFeedbackSurvey.verifyReportHeaders(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME));

			// verify report data
			consumerFeedbackSurvey.verifyReportData();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * Payroll Deduct Details data validation
	 * 
	 * @author KarthikR
	 * @date: 12-10-2022
	 */

	@Test(enabled = false, description = "168647 - Payroll Deduct Details data validation")
	public void PayrollDeductDetailsReportDataValidation() {
		final String CASE_NUM = "168647";

		// Reading Test Data from DB
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		rstConsumerSearchData = dataBase.getConsumerSearchData(Queries.CONSUMER_SEARCH, CASE_NUM);
		rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
		rstDeviceListData = dataBase.getDeviceListData(Queries.DEVICE_LIST, CASE_NUM);

		List<String> menus = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> location = Arrays
				.asList(rstLocationListData.get(CNLocationList.LOCATION_NAME).split(Constants.DELIMITER_TILD));
		List<String> requiredData = Arrays
				.asList(rstV5DeviceData.get(CNLocationSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		String currentDate = dateAndTime.getDateAndTime(Constants.REGEX_MMDDYY, Constants.TIME_ZONE_INDIA);
		List<String> columnName = Arrays
				.asList(rstConsumerSearchData.get(CNConsumerSearch.COLUMN_NAME).split(Constants.DELIMITER_HASH));
		try {
			// Launch ADM and select Org
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Select Location
			navigationBar.navigateToMenuItem(menus.get(0));
			locationList.selectLocationName(location.get(0));
			foundation.objectFocus(LocationSummary.BTN_DEPLOY_DEVICE);

			// Remove Device from AutomationLocation1 Location
			if (foundation.isDisplayed(LocationSummary.TBL_DEPLOYED_DEVICE_LIST)) {
				foundation.click(LocationSummary.TBL_DEPLOYED_DEVICE_LIST);
				foundation.waitforElement(DeviceDashboard.BTN_LIVE_CONNECTION_STATUS, Constants.SHORT_TIME);
				foundation.click(DeviceDashboard.BTN_REMOVE_DEVICE);
				foundation.waitforElement(DeviceDashboard.BTN_YES_REMOVE, Constants.SHORT_TIME);
				foundation.click(DeviceDashboard.BTN_YES_REMOVE);
				foundation.waitforElement(LocationSummary.BTN_DEPLOY_DEVICE, Constants.SHORT_TIME);
			}

			// Deploy Device to AutoLocationConsumerVerified Location
			navigationBar.navigateToMenuItem(menus.get(0));
			locationList.selectLocationName(location.get(1));
			foundation.objectFocus(LocationSummary.BTN_DEPLOY_DEVICE);
			foundation.click(LocationSummary.BTN_DEPLOY_DEVICE);
			foundation.waitforElement(LocationSummary.TXT_FIND_DEVICE, Constants.SHORT_TIME);
			textBox.enterText(LocationSummary.TXT_FIND_DEVICE, rstDeviceListData.get(CNDeviceList.PRODUCT_NAME));
			foundation.click(LocationSummary.TBL_DEVICE_LIST);
			foundation.click(LocationSummary.BTN_ADD_PRODUCT_ADD);
			foundation.waitforElement(LocationSummary.BTN_DEPLOY_DEVICE, Constants.SHORT_TIME);
			foundation.refreshPage();
			foundation.objectFocus(LocationSummary.BTN_LOCATION_SETTINGS);
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);

			// Select GMA Subsidy OFF
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_GMA_SUBSIDY));
			String value = dropdown.getSelectedItem(LocationSummary.DPD_GMA_SUBSIDY);
			if (value.equals(requiredData.get(0)))
				dropdown.selectItem(LocationSummary.DPD_GMA_SUBSIDY, requiredData.get(1), Constants.TEXT);

			// Select Payroll Deduct ON
			foundation.objectFocus(LocationSummary.TXT_PAYROLL);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_PAYROLL));
			value = dropdown.getSelectedItem(LocationSummary.DPD_PAYROLL);
			CustomisedAssert.assertTrue(value.equals(requiredData.get(0)));
			CustomisedAssert.assertTrue(checkBox.isChecked(LocationSummary.CHK_BOX_PAYROLL_DEDUCT));
			CustomisedAssert.assertTrue(checkBox.isChecked(LocationSummary.CHK_BOX_PAYROLL_DEDUCT_STREAM));
			CustomisedAssert.assertTrue(checkBox.isChecked(LocationSummary.CHK_BOX_PAYROLL_DEDUCT_OFFLINE));
			String payCycle = dropdown.getSelectedItem(LocationSummary.DPD_PAY_CYCLE_RECURRENCE);
			foundation.click(LocationSummary.BTN_SYNC);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);

			// Navigate to Admin > Consumer to Read Payroll Details
			List<String> details = payrollDeductDetails.readConsumerDetails(menus.get(1),
					rstConsumerSearchData.get(CNConsumerSearch.FIRST_NAME),
					rstDeviceListData.get(CNDeviceList.LOCATION), requiredData.get(3), requiredData.get(4));

			// Select location and sync with device
			locationList.syncDevice(menus.get(0), location.get(1));

			// Launch V5 Device to do Transaction on Subsidy Balance
			foundation.threadWait(Constants.SHORT_TIME);
			browser.launch(Constants.REMOTE, Constants.CHROME);
			String price = payrollDeductDetails.readPriceFromV5Transaction(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME),
					rstV5DeviceData.get(CNV5Device.ORDER_PAGE), rstV5DeviceData.get(CNV5Device.EMAIL_ID),
					rstV5DeviceData.get(CNV5Device.PIN), rstV5DeviceData.get(CNV5Device.PAYMENTS_PAGE));
			browser.close();

			// Navigate to ADM
			payrollDeductDetails.loginToADMWithSuperCredentials();

			// Navigate to Admin Consumer to Read Transaction ID
			String transID = payrollDeductDetails.readTransactionID(menus.get(1),
					rstConsumerSearchData.get(CNConsumerSearch.FIRST_NAME),
					rstDeviceListData.get(CNDeviceList.LOCATION));

			// Select Report, Data and Location to run Report
			payrollDeductDetails.selectAndRunReport(menus.get(2), rstReportListData.get(CNReportList.REPORT_NAME),
					rstReportListData.get(CNReportList.DATE_RANGE), rstDeviceListData.get(CNDeviceList.LOCATION),
					transID);

			// Search with Transaction ID and get the data
			payrollDeductDetails.getTblRecordsUI();
			payrollDeductDetails.verifyReportHeaders(rstConsumerSearchData.get(CNConsumerSearch.COLUMN_NAME));
			payrollDeductDetails.verifyCommonValueContentofTableRecord(columnName.get(0), transID);
			payrollDeductDetails.verifyCommonValueContentofTableRecord(columnName.get(1), currentDate);
			payrollDeductDetails.verifyCommonValueContentofTableRecord(columnName.get(2), currentDate);
			payrollDeductDetails.verifyCommonValueContentofTableRecord(columnName.get(3),
					rstConsumerSearchData.get(CNConsumerSearch.FIRST_NAME));
			payrollDeductDetails.verifyCommonValueContentofTableRecord(columnName.get(4), details.get(0));
			payrollDeductDetails.verifyCommonValueContentofTableRecord(columnName.get(5), details.get(2));
			payrollDeductDetails.verifyCommonValueContentofTableRecord(columnName.get(6), payCycle.toUpperCase());
			payrollDeductDetails.verifyCommonValueContentofTableRecord(columnName.get(7), details.get(1));
			payrollDeductDetails.verifyCommonValueContentofTableRecord(columnName.get(8), price);
			payrollDeductDetails.verifyCommonValueContentofTableRecord(columnName.get(9), requiredData.get(2));
			login.logout();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			browser.close();
			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Select Org and menu
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Remove Device from AutoLocationConsumerVerified Location
			navigationBar.navigateToMenuItem(menus.get(0));
			foundation.waitforElement(locationList.getlocationElement(location.get(1)), Constants.SHORT_TIME);
			textBox.enterText(LocationList.TXT_FILTER, location.get(1));
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(LocationList.TBL_DEPLOYED_DEVICE_LIST);
			foundation.waitforElement(DeviceDashboard.BTN_LIVE_CONNECTION_STATUS, Constants.SHORT_TIME);
			foundation.click(DeviceDashboard.BTN_REMOVE_DEVICE);
			foundation.waitforElement(DeviceDashboard.BTN_YES_REMOVE, Constants.SHORT_TIME);
			foundation.click(DeviceDashboard.BTN_YES_REMOVE);
			foundation.threadWait(Constants.SHORT_TIME);

			// Deploy Device to AutomationLocation1 Location
			navigationBar.navigateToMenuItem(menus.get(0));
			locationList.deployDevice(location.get(0), rstDeviceListData.get(CNDeviceList.PRODUCT_NAME));
		}
	}

	/**
	 * Cash Flow Device Report data validation
	 * 
	 * @author KarthikR
	 * @date: 27-10-2022
	 */
	@Test(description = "119864 - Cash Flow Device Report data validation")
	public void CashFlowDeviceReportDataValidation() {
		final String CASE_NUM = "119864";

		// Reading Test Data from DB
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);
		rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);
		rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

		List<String> columns = Arrays
				.asList(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME).split(Constants.DELIMITER_HASH));
		List<String> columnValue = Arrays
				.asList(rstLocationSummaryData.get(CNLocationSummary.COLUMN_VALUE).split(Constants.DELIMITER_HASH));
		String location = propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE);
		try {
			// Navigate to ADM and Select Org
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// process sales API to generate data
			cashFlowDevice.processAPI(rstProductSummaryData.get(CNProductSummary.ACTUAL_DATA),
					rstProductSummaryData.get(CNProductSummary.REQUIRED_DATA),
					rstProductSummaryData.get(CNProductSummary.DEVICE_ID),
					rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));

			// navigate To Reports
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location and run report
			cashFlowDevice.selectAndRunReport(rstReportListData.get(CNReportList.REPORT_NAME),
					rstReportListData.get(CNReportList.DATE_RANGE), location);

			// read Report Data
			cashFlowDevice.readAllRecordsFromCashFlowEmployeeDetailsTable(
					rstProductSummaryData.get(CNProductSummary.DEVICE_ID), location);
			cashFlowDevice.getInitialReportsData().putAll(cashFlowDevice.reportsData);
			cashFlowDevice.getInitialReportTotals().putAll(cashFlowDevice.getReportsTotalData());

			// process sales API to generate data
			cashFlowDevice.processAPI(rstProductSummaryData.get(CNProductSummary.ACTUAL_DATA),
					rstProductSummaryData.get(CNProductSummary.REQUIRED_DATA),
					rstProductSummaryData.get(CNProductSummary.DEVICE_ID),
					rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));

			// navigate To Reports
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location and run report
			cashFlowDevice.selectAndRunReport(rstReportListData.get(CNReportList.REPORT_NAME),
					rstReportListData.get(CNReportList.DATE_RANGE), location);

			// read Updated Report Data
			cashFlowDevice.readAllRecordsFromCashFlowEmployeeDetailsTable(
					rstProductSummaryData.get(CNProductSummary.DEVICE_ID), location);
			cashFlowDevice.getJsonSalesData();

			int recordCountOfCash = cashFlowDevice.getRequiredRecord(columnValue.get(0));
			int recordCountOfCreditCard = cashFlowDevice.getRequiredRecord(columnValue.get(1));
			int recordCountOfGEN3 = cashFlowDevice.getRequiredRecord(columnValue.get(2));
			int recordCountOfSOGO = cashFlowDevice.getRequiredRecord(columnValue.get(3));
			int recordCountOfComp = cashFlowDevice.getRequiredRecord(columnValue.get(4));
			int recordCountOfGuestPass = cashFlowDevice.getRequiredRecord(columnValue.get(5));
			int recordCountOfSpecial = cashFlowDevice.getRequiredRecord(columnValue.get(6));
			int recordCountOfAccount = cashFlowDevice.getRequiredRecord(columnValue.get(7));
			int recordCountOfTotals = cashFlowDevice.getRequiredRecord(columnValue.get(8));

			// verify Report Headers
			cashFlowDevice.verifyReportHeaders(columns);

			// calculate Credit Payment Counts
			cashFlowDevice.calculateCounts(cashFlowDevice.getTableHeaders().get(1), recordCountOfCash);

			// calculate Credit Payment Amounts
			cashFlowDevice.calculateAmounts(cashFlowDevice.getTableHeaders().get(2), recordCountOfCash);

			// calculate Credit Void Counts
			cashFlowDevice.calculateCounts(cashFlowDevice.getTableHeaders().get(3), recordCountOfCash);

			// calculate Credit Void Amounts
			cashFlowDevice.calculateAmounts(cashFlowDevice.getTableHeaders().get(4), recordCountOfCash);

			// calculate Credit Sales
			cashFlowDevice.calculateLocationSales(cashFlowDevice.getTableHeaders().get(7), recordCountOfCash);

			// calculate Credit Taxes
			cashFlowDevice.calculateLocationTax(cashFlowDevice.getTableHeaders().get(8), recordCountOfCash);

			// calculate Credit Total
			cashFlowDevice.calculateTotalsColumnData(cashFlowDevice.getTableHeaders().get(13), recordCountOfCash);

			// calculate Credit Payment Counts
			cashFlowDevice.calculateCounts(cashFlowDevice.getTableHeaders().get(1), recordCountOfCreditCard);

			// calculate Credit Payment Amounts
			cashFlowDevice.calculateAmounts(cashFlowDevice.getTableHeaders().get(2), recordCountOfCreditCard);

			// calculate Credit Void Counts
			cashFlowDevice.calculateCounts(cashFlowDevice.getTableHeaders().get(3), recordCountOfCreditCard);

			// calculate Credit Void Amounts
			cashFlowDevice.calculateAmounts(cashFlowDevice.getTableHeaders().get(4), recordCountOfCreditCard);

			// calculate Credit Declined Counts
			cashFlowDevice.calculateCounts(cashFlowDevice.getTableHeaders().get(5), recordCountOfCreditCard);

			// calculate Credit Declined Amounts
			cashFlowDevice.calculateAmounts(cashFlowDevice.getTableHeaders().get(6), recordCountOfCreditCard);

			// calculate Credit Sales
			cashFlowDevice.calculateLocationSales(cashFlowDevice.getTableHeaders().get(7), recordCountOfCreditCard);

			// calculate Credit Taxes
			cashFlowDevice.calculateLocationTax(cashFlowDevice.getTableHeaders().get(8), recordCountOfCreditCard);

			// calculate Credit Total
			cashFlowDevice.calculateTotalsColumnData(cashFlowDevice.getTableHeaders().get(13), recordCountOfCreditCard);

			// calculate gen3 Payment Counts
			cashFlowDevice.calculateCounts(cashFlowDevice.getTableHeaders().get(1), recordCountOfGEN3);

			// calculate gen3 Payment Amounts
			cashFlowDevice.calculateAmounts(cashFlowDevice.getTableHeaders().get(2), recordCountOfGEN3);

			// calculate gen3 Void Counts
			cashFlowDevice.calculateCounts(cashFlowDevice.getTableHeaders().get(3), recordCountOfGEN3);

			// calculate gen3 Void Amounts
			cashFlowDevice.calculateAmounts(cashFlowDevice.getTableHeaders().get(4), recordCountOfGEN3);

			// calculate gen3 Declined Counts
			cashFlowDevice.calculateCounts(cashFlowDevice.getTableHeaders().get(5), recordCountOfGEN3);

			// calculate gen3 Declined Amounts
			cashFlowDevice.calculateAmounts(cashFlowDevice.getTableHeaders().get(6), recordCountOfGEN3);

			// calculate gen3 Sales
			cashFlowDevice.calculateLocationSales(cashFlowDevice.getTableHeaders().get(7), recordCountOfGEN3);

			// calculate gen3 Taxes
			cashFlowDevice.calculateLocationTax(cashFlowDevice.getTableHeaders().get(8), recordCountOfGEN3);

			// calculate gen3 Total
			cashFlowDevice.calculateTotalsColumnData(cashFlowDevice.getTableHeaders().get(13), recordCountOfGEN3);

			// calculate SOGO Payment Counts
			cashFlowDevice.calculateCounts(cashFlowDevice.getTableHeaders().get(1), recordCountOfSOGO);

			// calculate SOGO Payment Amounts
			cashFlowDevice.calculateAmounts(cashFlowDevice.getTableHeaders().get(2), recordCountOfSOGO);

			// calculate SOGO Void Counts
			cashFlowDevice.calculateCounts(cashFlowDevice.getTableHeaders().get(3), recordCountOfSOGO);

			// calculate SOGO Void Amounts
			cashFlowDevice.calculateAmounts(cashFlowDevice.getTableHeaders().get(4), recordCountOfSOGO);

			// calculate SOGO Sales
			cashFlowDevice.calculateLocationSales(cashFlowDevice.getTableHeaders().get(7), recordCountOfSOGO);

			// calculate SOGO Taxes
			cashFlowDevice.calculateLocationTax(cashFlowDevice.getTableHeaders().get(8), recordCountOfSOGO);

			// calculate SOGO Total
			cashFlowDevice.calculateTotalsColumnData(cashFlowDevice.getTableHeaders().get(13), recordCountOfSOGO);

			// calculate Comp Payment Counts
			cashFlowDevice.calculateCounts(cashFlowDevice.getTableHeaders().get(1), recordCountOfComp);

			// calculate Comp Payment Amounts
			cashFlowDevice.calculateAmounts(cashFlowDevice.getTableHeaders().get(2), recordCountOfComp);

			// calculate Comp Void Counts
			cashFlowDevice.calculateCounts(cashFlowDevice.getTableHeaders().get(3), recordCountOfComp);

			// calculate Comp Void Amounts
			cashFlowDevice.calculateAmounts(cashFlowDevice.getTableHeaders().get(4), recordCountOfComp);

			// calculate Comp Sales
			cashFlowDevice.calculateLocationSales(cashFlowDevice.getTableHeaders().get(7), recordCountOfComp);

			// calculate Comp Taxes
			cashFlowDevice.calculateLocationTax(cashFlowDevice.getTableHeaders().get(8), recordCountOfComp);

			// calculate Comp Total
			cashFlowDevice.calculateTotalsColumnData(cashFlowDevice.getTableHeaders().get(13), recordCountOfComp);

			// calculate GuestPass Payment Counts
			cashFlowDevice.calculateCounts(cashFlowDevice.getTableHeaders().get(1), recordCountOfGuestPass);

			// calculate GuestPass Payment Amounts
			cashFlowDevice.calculateAmounts(cashFlowDevice.getTableHeaders().get(2), recordCountOfGuestPass);

			// calculate GuestPass Void Counts
			cashFlowDevice.calculateCounts(cashFlowDevice.getTableHeaders().get(3), recordCountOfGuestPass);

			// calculate GuestPass Void Amounts
			cashFlowDevice.calculateAmounts(cashFlowDevice.getTableHeaders().get(4), recordCountOfGuestPass);

			// calculate GuestPass Sales
			cashFlowDevice.calculateLocationSales(cashFlowDevice.getTableHeaders().get(7), recordCountOfGuestPass);

			// calculate GuestPass Taxes
			cashFlowDevice.calculateLocationTax(cashFlowDevice.getTableHeaders().get(8), recordCountOfGuestPass);

			// calculate GuestPass Total
			cashFlowDevice.calculateTotalsColumnData(cashFlowDevice.getTableHeaders().get(13), recordCountOfGuestPass);

			// calculate Special Payment Counts
			cashFlowDevice.calculateCounts(cashFlowDevice.getTableHeaders().get(1), recordCountOfSpecial);

			// calculate Special Payment Amounts
			cashFlowDevice.calculateAmounts(cashFlowDevice.getTableHeaders().get(2), recordCountOfSpecial);

			// calculate Special Void Counts
			cashFlowDevice.calculateCounts(cashFlowDevice.getTableHeaders().get(3), recordCountOfSpecial);

			// calculate Special Void Amounts
			cashFlowDevice.calculateAmounts(cashFlowDevice.getTableHeaders().get(4), recordCountOfSpecial);

			// calculate Special Sales
			cashFlowDevice.calculateLocationSales(cashFlowDevice.getTableHeaders().get(7), recordCountOfSpecial);

			// calculate Special Taxes
			cashFlowDevice.calculateLocationTax(cashFlowDevice.getTableHeaders().get(8), recordCountOfSpecial);

			// calculate Special Total
			cashFlowDevice.calculateTotalsColumnData(cashFlowDevice.getTableHeaders().get(13), recordCountOfSpecial);

			// calculate Account Payment Counts
			cashFlowDevice.calculateCounts(cashFlowDevice.getTableHeaders().get(1), recordCountOfAccount);

			// calculate Account Payment Amounts
			cashFlowDevice.calculateAmounts(cashFlowDevice.getTableHeaders().get(2), recordCountOfAccount);

			// calculate Account Void Counts
			cashFlowDevice.calculateCounts(cashFlowDevice.getTableHeaders().get(3), recordCountOfAccount);

			// calculate Account Void Amounts
			cashFlowDevice.calculateAmounts(cashFlowDevice.getTableHeaders().get(4), recordCountOfAccount);

			// calculate Account Sales
			cashFlowDevice.calculateLocationSales(cashFlowDevice.getTableHeaders().get(7), recordCountOfAccount);

			// calculate Account Taxes
			cashFlowDevice.calculateLocationTax(cashFlowDevice.getTableHeaders().get(8), recordCountOfAccount);

			// calculate Account Total
			cashFlowDevice.calculateTotalsColumnData(cashFlowDevice.getTableHeaders().get(13), recordCountOfAccount);

			// calculate Totals Payment Counts
			cashFlowDevice.calculateCountsForTotals(cashFlowDevice.getTableHeaders().get(1), recordCountOfTotals);

			// calculate Totals Payment Amounts
			cashFlowDevice.calculateAmountsForTotals(cashFlowDevice.getTableHeaders().get(2), recordCountOfTotals);

			// calculate Totals Void Counts
			cashFlowDevice.calculateCountsForTotals(cashFlowDevice.getTableHeaders().get(3), recordCountOfTotals);

			// calculate Totals Void Amounts
			cashFlowDevice.calculateAmountsForTotals(cashFlowDevice.getTableHeaders().get(4), recordCountOfTotals);

			// calculate Totals Declined Counts
			cashFlowDevice.calculateCounts(cashFlowDevice.getTableHeaders().get(5), recordCountOfTotals);

			// calculate Totals Declined Amounts
			cashFlowDevice.calculateAmounts(cashFlowDevice.getTableHeaders().get(6), recordCountOfTotals);

			// calculate Totals Sales
			cashFlowDevice.calculateLocationSalesForTotals(cashFlowDevice.getTableHeaders().get(7),
					recordCountOfTotals);

			// calculate Totals Taxes
			cashFlowDevice.calculateLocationTaxForTotals(cashFlowDevice.getTableHeaders().get(8), recordCountOfTotals);

			// calculate Totals Total
			cashFlowDevice.calculateTotalsColumnDataForTotals(cashFlowDevice.getTableHeaders().get(13),
					recordCountOfTotals);

			// verify Report Data
			cashFlowDevice.verifyReportRecords();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * UFS By Device Report data validation
	 * 
	 * @author KarthikR
	 * @date: 16-08-2022
	 */
	@Test(description = "198563 - UFS By Device Report data validation")
	public void ufsByDeviceReportDataValidation() {
		final String CASE_NUM = "198563";

		// Reading Test Data from DB
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);
		rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);
		rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

		List<String> columnValue = Arrays
				.asList(rstLocationSummaryData.get(CNLocationSummary.COLUMN_VALUE).split(Constants.DELIMITER_HASH));
		List<String> columnName = Arrays
				.asList(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME).split(Constants.DELIMITER_TILD));
		String location = propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE);
		try {
			// Navigate to ADM and Select Org
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// process sales API to generate data
			ufsByDevice.processAPI(rstProductSummaryData.get(CNProductSummary.ACTUAL_DATA),
					rstProductSummaryData.get(CNProductSummary.REQUIRED_DATA),
					rstProductSummaryData.get(CNProductSummary.DEVICE_ID),
					rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));

			// navigate To Reports
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			CustomisedAssert.assertTrue(foundation.isDisplayed(UFSByDevice.LBL_REPORTS_LIST));

			// Select the Report Date range and Location and run report
			ufsByDevice.selectAndRunReport(rstReportListData.get(CNReportList.REPORT_NAME),
					rstReportListData.get(CNReportList.DATE_RANGE), location);
			CustomisedAssert.assertTrue(foundation.isDisplayed(UFSByDevice.TABLE_CASH_FLOW_DETAILS_TOTAL));

			// read Report Data
			ufsByDevice.readAllRecordsFromCashFlowDetailsTable(rstProductSummaryData.get(CNProductSummary.DEVICE_ID),
					location);
			ufsByDevice.getInitialReportsData().putAll(ufsByDevice.getReportsData());
			ufsByDevice.getInitialReportTotals().putAll(ufsByDevice.getReportsTotalData());
			ufsByDevice.getTblRecordsUIOfSalesTimeDetails(rstProductSummaryData.get(CNProductSummary.DEVICE_ID),
					location);
			ufsByDevice.getIntialDataOfSalesTimeDetails().putAll(ufsByDevice.getReportsDataOfSalesTimeDetails());
			ufsByDevice.getUpdatedTableFootersOfSalesTimeDetails()
					.putAll(ufsByDevice.getTableFootersOfSalesTimeDetails());

			// process sales API to generate data
			ufsByDevice.processAPI(rstProductSummaryData.get(CNProductSummary.ACTUAL_DATA),
					rstProductSummaryData.get(CNProductSummary.REQUIRED_DATA),
					rstProductSummaryData.get(CNProductSummary.DEVICE_ID),
					rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));

			// navigate To Reports
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location and run report
			ufsByDevice.selectAndRunReport(rstReportListData.get(CNReportList.REPORT_NAME),
					rstReportListData.get(CNReportList.DATE_RANGE), location);

			// read Updated Report Data
			ufsByDevice.readAllRecordsFromCashFlowDetailsTable(rstProductSummaryData.get(CNProductSummary.DEVICE_ID),
					location);
			ufsByDevice.getJsonSalesData();
			ufsByDevice.getTblRecordsUIOfSalesTimeDetails(rstProductSummaryData.get(CNProductSummary.DEVICE_ID),
					location);

			int recordCountOfCash = ufsByDevice.getRequiredRecord(columnValue.get(0));
			int recordCountOfCreditCard = ufsByDevice.getRequiredRecord(columnValue.get(1));
			int recordCountOfGEN3 = ufsByDevice.getRequiredRecord(columnValue.get(2));
			int recordCountOfSOGO = ufsByDevice.getRequiredRecord(columnValue.get(3));
			int recordCountOfComp = ufsByDevice.getRequiredRecord(columnValue.get(4));
			int recordCountOfGuestPass = ufsByDevice.getRequiredRecord(columnValue.get(5));
			int recordCountOfSpecial = ufsByDevice.getRequiredRecord(columnValue.get(6));
			int recordCountOfAccount = ufsByDevice.getRequiredRecord(columnValue.get(7));
			int recordCountOfTotals = ufsByDevice.getRequiredRecord(columnValue.get(8));

			// verify Report Headers
			ufsByDevice.verifyCashFlowHeaders(columnName.get(0));

			// calculate Credit Payment Counts
			ufsByDevice.calculateCounts(ufsByDevice.getTableHeaders().get(1), recordCountOfCash);

			// calculate Credit Payment Amounts
			ufsByDevice.calculateAmounts(ufsByDevice.getTableHeaders().get(2), recordCountOfCash);

			// calculate Credit Void Counts
			ufsByDevice.calculateCounts(ufsByDevice.getTableHeaders().get(3), recordCountOfCash);

			// calculate Credit Void Amounts
			ufsByDevice.calculateAmounts(ufsByDevice.getTableHeaders().get(4), recordCountOfCash);

			// calculate Credit Sales
			ufsByDevice.calculateLocationSales(ufsByDevice.getTableHeaders().get(7), recordCountOfCash);

			// calculate Credit Taxes
			ufsByDevice.calculateLocationTax(ufsByDevice.getTableHeaders().get(8), recordCountOfCash);

			// calculate Credit Total
			ufsByDevice.calculateTotalsColumnData(ufsByDevice.getTableHeaders().get(13), recordCountOfCash);

			// calculate Credit Payment Counts
			ufsByDevice.calculateCounts(ufsByDevice.getTableHeaders().get(1), recordCountOfCreditCard);

			// calculate Credit Payment Amounts
			ufsByDevice.calculateAmounts(ufsByDevice.getTableHeaders().get(2), recordCountOfCreditCard);

			// calculate Credit Void Counts
			ufsByDevice.calculateCounts(ufsByDevice.getTableHeaders().get(3), recordCountOfCreditCard);

			// calculate Credit Void Amounts
			ufsByDevice.calculateAmounts(ufsByDevice.getTableHeaders().get(4), recordCountOfCreditCard);

			// calculate Credit Declined Counts
			ufsByDevice.calculateCounts(ufsByDevice.getTableHeaders().get(5), recordCountOfCreditCard);

			// calculate Credit Declined Amounts
			ufsByDevice.calculateAmounts(ufsByDevice.getTableHeaders().get(6), recordCountOfCreditCard);

			// calculate Credit Sales
			ufsByDevice.calculateLocationSales(ufsByDevice.getTableHeaders().get(7), recordCountOfCreditCard);

			// calculate Credit Taxes
			ufsByDevice.calculateLocationTax(ufsByDevice.getTableHeaders().get(8), recordCountOfCreditCard);

			// calculate Credit Total
			ufsByDevice.calculateTotalsColumnData(ufsByDevice.getTableHeaders().get(13), recordCountOfCreditCard);

			// calculate gen3 Payment Counts
			ufsByDevice.calculateCounts(ufsByDevice.getTableHeaders().get(1), recordCountOfGEN3);

			// calculate gen3 Payment Amounts
			ufsByDevice.calculateAmounts(ufsByDevice.getTableHeaders().get(2), recordCountOfGEN3);

			// calculate gen3 Void Counts
			ufsByDevice.calculateCounts(ufsByDevice.getTableHeaders().get(3), recordCountOfGEN3);

			// calculate gen3 Void Amounts
			ufsByDevice.calculateAmounts(ufsByDevice.getTableHeaders().get(4), recordCountOfGEN3);

			// calculate gen3 Declined Counts
			ufsByDevice.calculateCounts(ufsByDevice.getTableHeaders().get(5), recordCountOfGEN3);

			// calculate gen3 Declined Amounts
			ufsByDevice.calculateAmounts(ufsByDevice.getTableHeaders().get(6), recordCountOfGEN3);

			// calculate gen3 Sales
			ufsByDevice.calculateLocationSales(ufsByDevice.getTableHeaders().get(7), recordCountOfGEN3);

			// calculate gen3 Taxes
			ufsByDevice.calculateLocationTax(ufsByDevice.getTableHeaders().get(8), recordCountOfGEN3);

			// calculate gen3 Total
			ufsByDevice.calculateTotalsColumnData(ufsByDevice.getTableHeaders().get(13), recordCountOfGEN3);

			// calculate SOGO Payment Counts
			ufsByDevice.calculateCounts(ufsByDevice.getTableHeaders().get(1), recordCountOfSOGO);

			// calculate SOGO Payment Amounts
			ufsByDevice.calculateAmounts(ufsByDevice.getTableHeaders().get(2), recordCountOfSOGO);

			// calculate SOGO Void Counts
			ufsByDevice.calculateCounts(ufsByDevice.getTableHeaders().get(3), recordCountOfSOGO);

			// calculate SOGO Void Amounts
			ufsByDevice.calculateAmounts(ufsByDevice.getTableHeaders().get(4), recordCountOfSOGO);

			// calculate SOGO Sales
			ufsByDevice.calculateLocationSales(ufsByDevice.getTableHeaders().get(7), recordCountOfSOGO);

			// calculate SOGO Taxes
			ufsByDevice.calculateLocationTax(ufsByDevice.getTableHeaders().get(8), recordCountOfSOGO);

			// calculate SOGO Total
			ufsByDevice.calculateTotalsColumnData(ufsByDevice.getTableHeaders().get(13), recordCountOfSOGO);

			// calculate Comp Payment Counts
			ufsByDevice.calculateCounts(ufsByDevice.getTableHeaders().get(1), recordCountOfComp);

			// calculate Comp Payment Amounts
			ufsByDevice.calculateAmounts(ufsByDevice.getTableHeaders().get(2), recordCountOfComp);

			// calculate Comp Void Counts
			ufsByDevice.calculateCounts(ufsByDevice.getTableHeaders().get(3), recordCountOfComp);

			// calculate Comp Void Amounts
			ufsByDevice.calculateAmounts(ufsByDevice.getTableHeaders().get(4), recordCountOfComp);

			// calculate Comp Sales
			ufsByDevice.calculateLocationSales(ufsByDevice.getTableHeaders().get(7), recordCountOfComp);

			// calculate Comp Taxes
			ufsByDevice.calculateLocationTax(ufsByDevice.getTableHeaders().get(8), recordCountOfComp);

			// calculate Comp Total
			ufsByDevice.calculateTotalsColumnData(ufsByDevice.getTableHeaders().get(13), recordCountOfComp);

			// calculate GuestPass Payment Counts
			ufsByDevice.calculateCounts(ufsByDevice.getTableHeaders().get(1), recordCountOfGuestPass);

			// calculate GuestPass Payment Amounts
			ufsByDevice.calculateAmounts(ufsByDevice.getTableHeaders().get(2), recordCountOfGuestPass);

			// calculate GuestPass Void Counts
			ufsByDevice.calculateCounts(ufsByDevice.getTableHeaders().get(3), recordCountOfGuestPass);

			// calculate GuestPass Void Amounts
			ufsByDevice.calculateAmounts(ufsByDevice.getTableHeaders().get(4), recordCountOfGuestPass);

			// calculate GuestPass Sales
			ufsByDevice.calculateLocationSales(ufsByDevice.getTableHeaders().get(7), recordCountOfGuestPass);

			// calculate GuestPass Taxes
			ufsByDevice.calculateLocationTax(ufsByDevice.getTableHeaders().get(8), recordCountOfGuestPass);

			// calculate GuestPass Total
			ufsByDevice.calculateTotalsColumnData(ufsByDevice.getTableHeaders().get(13), recordCountOfGuestPass);

			// calculate Special Payment Counts
			ufsByDevice.calculateCounts(ufsByDevice.getTableHeaders().get(1), recordCountOfSpecial);

			// calculate Special Payment Amounts
			ufsByDevice.calculateAmounts(ufsByDevice.getTableHeaders().get(2), recordCountOfSpecial);

			// calculate Special Void Counts
			ufsByDevice.calculateCounts(ufsByDevice.getTableHeaders().get(3), recordCountOfSpecial);

			// calculate Special Void Amounts
			ufsByDevice.calculateAmounts(ufsByDevice.getTableHeaders().get(4), recordCountOfSpecial);

			// calculate Special Sales
			ufsByDevice.calculateLocationSales(ufsByDevice.getTableHeaders().get(7), recordCountOfSpecial);

			// calculate Special Taxes
			ufsByDevice.calculateLocationTax(ufsByDevice.getTableHeaders().get(8), recordCountOfSpecial);

			// calculate Special Total
			ufsByDevice.calculateTotalsColumnData(ufsByDevice.getTableHeaders().get(13), recordCountOfSpecial);

			// calculate Account Payment Counts
			ufsByDevice.calculateCounts(ufsByDevice.getTableHeaders().get(1), recordCountOfAccount);

			// calculate Account Payment Amounts
			ufsByDevice.calculateAmounts(ufsByDevice.getTableHeaders().get(2), recordCountOfAccount);

			// calculate Account Void Counts
			ufsByDevice.calculateCounts(ufsByDevice.getTableHeaders().get(3), recordCountOfAccount);

			// calculate Account Void Amounts
			ufsByDevice.calculateAmounts(ufsByDevice.getTableHeaders().get(4), recordCountOfAccount);

			// calculate Account Sales
			ufsByDevice.calculateLocationSales(ufsByDevice.getTableHeaders().get(7), recordCountOfAccount);

			// calculate Account Taxes
			ufsByDevice.calculateLocationTax(ufsByDevice.getTableHeaders().get(8), recordCountOfAccount);

			// calculate Account Total
			ufsByDevice.calculateTotalsColumnData(ufsByDevice.getTableHeaders().get(13), recordCountOfAccount);

			// calculate Totals Payment Counts
			ufsByDevice.calculateCountsForTotals(ufsByDevice.getTableHeaders().get(1), recordCountOfTotals);

			// calculate Totals Payment Amounts
			ufsByDevice.calculateAmountsForTotals(ufsByDevice.getTableHeaders().get(2), recordCountOfTotals);

			// calculate Totals Void Counts
			ufsByDevice.calculateCountsForTotals(ufsByDevice.getTableHeaders().get(3), recordCountOfTotals);

			// calculate Totals Void Amounts
			ufsByDevice.calculateAmountsForTotals(ufsByDevice.getTableHeaders().get(4), recordCountOfTotals);

			// calculate Totals Declined Counts
			ufsByDevice.calculateCounts(ufsByDevice.getTableHeaders().get(5), recordCountOfTotals);

			// calculate Totals Declined Amounts
			ufsByDevice.calculateAmounts(ufsByDevice.getTableHeaders().get(6), recordCountOfTotals);

			// calculate Totals Sales
			ufsByDevice.calculateLocationSalesForTotals(ufsByDevice.getTableHeaders().get(7), recordCountOfTotals);

			// calculate Totals Taxes
			ufsByDevice.calculateLocationTaxForTotals(ufsByDevice.getTableHeaders().get(8), recordCountOfTotals);

			// calculate Totals Total
			ufsByDevice.calculateTotalsColumnDataForTotals(ufsByDevice.getTableHeaders().get(13), recordCountOfTotals);

			// verify Report Data for Cash Flow
			ufsByDevice.verifyReportRecords();

			// Validations for Sales Time Details
			ufsByDevice.decideTimeRange((String) ufsByDevice.getJsonData().get(Reports.TRANS_DATE));

			// update the report date based on calculation
			String productPrice = rstProductSummaryData.get(CNProductSummary.PRICE);
			String tax = rstProductSummaryData.get(CNProductSummary.TAX);
			String discount = rstProductSummaryData.get(CNProductSummary.DISCOUNT);

			// Updating Table data
			ufsByDevice.TrasactionCount(ufsByDevice.getTableHeadersOfSalesTimeDetails().get(1));
			ufsByDevice.calculateAmount(ufsByDevice.getTableHeadersOfSalesTimeDetails().get(2), productPrice);
			ufsByDevice.calculateAmount(ufsByDevice.getTableHeadersOfSalesTimeDetails().get(3), discount);
			ufsByDevice.calculateAmount(ufsByDevice.getTableHeadersOfSalesTimeDetails().get(4), tax);
			ufsByDevice.saleIncludingTaxes(ufsByDevice.getTableHeadersOfSalesTimeDetails().get(5), productPrice, tax,
					discount);

			// Updating Footer data
			ufsByDevice.TrasactionCountOfFooter(ufsByDevice.getTableHeadersOfSalesTimeDetails().get(1));
			ufsByDevice.calculateAmountOfFooter(ufsByDevice.getTableHeadersOfSalesTimeDetails().get(2), productPrice);
			ufsByDevice.calculateAmountOfFooter(ufsByDevice.getTableHeadersOfSalesTimeDetails().get(3), discount);
			ufsByDevice.calculateAmountOfFooter(ufsByDevice.getTableHeadersOfSalesTimeDetails().get(4), tax);
			ufsByDevice.saleIncludingTaxesOfFooter(ufsByDevice.getTableHeadersOfSalesTimeDetails().get(5), productPrice,
					tax, discount);

			// verify report headers Validations for Sales Time Details
			ufsByDevice.verifyReportHeadersOfSalesTimeDetails(columnName.get(1));

			// verify report dataValidations for Sales Time Details
			ufsByDevice.verifyReportDataOfSalesTimeDetails();

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * Cash Flow Details Report Data Validation
	 * 
	 * @author ravindhara, Date:18-10-2022
	 */
	@Test(description = "206324- Cash Flow Details Report data validation")
	public void CashFlowDetailsReportDataValidation() {
		final String CASE_NUM = "206324";

		// Reading Test Data from DB
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);
		rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);
		rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

		List<String> paymentType = Arrays
				.asList(rstLocationSummaryData.get(CNLocationSummary.COLUMN_VALUE).split(Constants.DELIMITER_HASH));
		List<String> columnNames = Arrays
				.asList(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME).split(Constants.DELIMITER_HASH));
		String location = propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE);
		try {
			// Navigate to ADM and Select Org
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// process sales API to generate data
			cashFlowDetails.processAPI(rstProductSummaryData.get(CNProductSummary.ACTUAL_DATA),
					rstProductSummaryData.get(CNProductSummary.REQUIRED_DATA),
					rstProductSummaryData.get(CNProductSummary.DEVICE_ID),
					rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));

			// navigate To Reports
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location and run report
			cashFlowDetails.selectAndRunReport(rstReportListData.get(CNReportList.REPORT_NAME),
					rstReportListData.get(CNReportList.DATE_RANGE), location);

			// read Report Data
			cashFlowDetails.readAllRecordsFromCashFlowDetailsTable(
					rstProductSummaryData.get(CNProductSummary.DEVICE_ID), location);
			cashFlowDetails.getInitialReportsData().putAll(cashFlowDetails.reportsData);
			cashFlowDetails.getInitialReportTotals().putAll(cashFlowDetails.getReportsTotalData());

			// process sales API to generate data
			cashFlowDetails.processAPI(rstProductSummaryData.get(CNProductSummary.ACTUAL_DATA),
					rstProductSummaryData.get(CNProductSummary.REQUIRED_DATA),
					rstProductSummaryData.get(CNProductSummary.DEVICE_ID),
					rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));

			// navigate To Reports
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location and run report
			cashFlowDetails.selectAndRunReport(rstReportListData.get(CNReportList.REPORT_NAME),
					rstReportListData.get(CNReportList.DATE_RANGE), location);

			// read Updated Report Data
			cashFlowDetails.readAllRecordsFromCashFlowDetailsTable(
					rstProductSummaryData.get(CNProductSummary.DEVICE_ID), location);
			cashFlowDetails.getJsonSalesData();

			int recordCountOfCash = cashFlowDetails.getRequiredRecord(paymentType.get(0));
			int recordCountOfCreditCard = cashFlowDetails.getRequiredRecord(paymentType.get(1));
			int recordCountOfGEN3 = cashFlowDetails.getRequiredRecord(paymentType.get(2));
			int recordCountOfSOGO = cashFlowDetails.getRequiredRecord(paymentType.get(3));
			int recordCountOfComp = cashFlowDetails.getRequiredRecord(paymentType.get(4));
			int recordCountOfGuestPass = cashFlowDetails.getRequiredRecord(paymentType.get(5));
			int recordCountOfSpecial = cashFlowDetails.getRequiredRecord(paymentType.get(6));
			int recordCountOfAccount = cashFlowDetails.getRequiredRecord(paymentType.get(7));
			int recordCountOfTotals = cashFlowDetails.getRequiredRecord(paymentType.get(8));

			// verify Report Headers
			cashFlowDetails.verifyReportHeaders(columnNames);

			// calculate Credit Payment Counts
			cashFlowDetails.calculateCounts(cashFlowDetails.getTableHeaders().get(1), recordCountOfCash);

			// calculate Credit Payment Amounts
			cashFlowDetails.calculateAmounts(cashFlowDetails.getTableHeaders().get(2), recordCountOfCash);

			// calculate Credit Void Counts
			cashFlowDetails.calculateCounts(cashFlowDetails.getTableHeaders().get(3), recordCountOfCash);

			// calculate Credit Void Amounts
			cashFlowDetails.calculateAmounts(cashFlowDetails.getTableHeaders().get(4), recordCountOfCash);

			// calculate Credit Sales
			cashFlowDetails.calculateLocationSales(cashFlowDetails.getTableHeaders().get(7), recordCountOfCash);

			// calculate Credit Taxes
			cashFlowDetails.calculateLocationTax(cashFlowDetails.getTableHeaders().get(8), recordCountOfCash);

			// calculate Credit Total
			cashFlowDetails.calculateTotalsColumnData(cashFlowDetails.getTableHeaders().get(13), recordCountOfCash);

			// calculate Credit Payment Counts
			cashFlowDetails.calculateCounts(cashFlowDetails.getTableHeaders().get(1), recordCountOfCreditCard);

			// calculate Credit Payment Amounts
			cashFlowDetails.calculateAmounts(cashFlowDetails.getTableHeaders().get(2), recordCountOfCreditCard);

			// calculate Credit Void Counts
			cashFlowDetails.calculateCounts(cashFlowDetails.getTableHeaders().get(3), recordCountOfCreditCard);

			// calculate Credit Void Amounts
			cashFlowDetails.calculateAmounts(cashFlowDetails.getTableHeaders().get(4), recordCountOfCreditCard);

			// calculate Credit Declined Counts
			cashFlowDetails.calculateCounts(cashFlowDetails.getTableHeaders().get(5), recordCountOfCreditCard);

			// calculate Credit Declined Amounts
			cashFlowDetails.calculateAmounts(cashFlowDetails.getTableHeaders().get(6), recordCountOfCreditCard);

			// calculate Credit Sales
			cashFlowDetails.calculateLocationSales(cashFlowDetails.getTableHeaders().get(7), recordCountOfCreditCard);

			// calculate Credit Taxes
			cashFlowDetails.calculateLocationTax(cashFlowDetails.getTableHeaders().get(8), recordCountOfCreditCard);

			// calculate Credit Total
			cashFlowDetails.calculateTotalsColumnData(cashFlowDetails.getTableHeaders().get(13),
					recordCountOfCreditCard);

			// calculate gen3 Payment Counts
			cashFlowDetails.calculateCounts(cashFlowDetails.getTableHeaders().get(1), recordCountOfGEN3);

			// calculate gen3 Payment Amounts
			cashFlowDetails.calculateAmounts(cashFlowDetails.getTableHeaders().get(2), recordCountOfGEN3);

			// calculate gen3 Void Counts
			cashFlowDetails.calculateCounts(cashFlowDetails.getTableHeaders().get(3), recordCountOfGEN3);

			// calculate gen3 Void Amounts
			cashFlowDetails.calculateAmounts(cashFlowDetails.getTableHeaders().get(4), recordCountOfGEN3);

			// calculate gen3 Declined Counts
			cashFlowDetails.calculateCounts(cashFlowDetails.getTableHeaders().get(5), recordCountOfGEN3);

			// calculate gen3 Declined Amounts
			cashFlowDetails.calculateAmounts(cashFlowDetails.getTableHeaders().get(6), recordCountOfGEN3);

			// calculate gen3 Sales
			cashFlowDetails.calculateLocationSales(cashFlowDetails.getTableHeaders().get(7), recordCountOfGEN3);

			// calculate gen3 Taxes
			cashFlowDetails.calculateLocationTax(cashFlowDetails.getTableHeaders().get(8), recordCountOfGEN3);

			// calculate gen3 Total
			cashFlowDetails.calculateTotalsColumnData(cashFlowDetails.getTableHeaders().get(13), recordCountOfGEN3);

			// calculate SOGO Payment Counts
			cashFlowDetails.calculateCounts(cashFlowDetails.getTableHeaders().get(1), recordCountOfSOGO);

			// calculate SOGO Payment Amounts
			cashFlowDetails.calculateAmounts(cashFlowDetails.getTableHeaders().get(2), recordCountOfSOGO);

			// calculate SOGO Void Counts
			cashFlowDetails.calculateCounts(cashFlowDetails.getTableHeaders().get(3), recordCountOfSOGO);

			// calculate SOGO Void Amounts
			cashFlowDetails.calculateAmounts(cashFlowDetails.getTableHeaders().get(4), recordCountOfSOGO);

			// calculate SOGO Sales
			cashFlowDetails.calculateLocationSales(cashFlowDetails.getTableHeaders().get(7), recordCountOfSOGO);

			// calculate SOGO Taxes
			cashFlowDetails.calculateLocationTax(cashFlowDetails.getTableHeaders().get(8), recordCountOfSOGO);

			// calculate SOGO Total
			cashFlowDetails.calculateTotalsColumnData(cashFlowDetails.getTableHeaders().get(13), recordCountOfSOGO);

			// calculate Comp Payment Counts
			cashFlowDetails.calculateCounts(cashFlowDetails.getTableHeaders().get(1), recordCountOfComp);

			// calculate Comp Payment Amounts
			cashFlowDetails.calculateAmounts(cashFlowDetails.getTableHeaders().get(2), recordCountOfComp);

			// calculate Comp Void Counts
			cashFlowDetails.calculateCounts(cashFlowDetails.getTableHeaders().get(3), recordCountOfComp);

			// calculate Comp Void Amounts
			cashFlowDetails.calculateAmounts(cashFlowDetails.getTableHeaders().get(4), recordCountOfComp);

			// calculate Comp Sales
			cashFlowDetails.calculateLocationSales(cashFlowDetails.getTableHeaders().get(7), recordCountOfComp);

			// calculate Comp Taxes
			cashFlowDetails.calculateLocationTax(cashFlowDetails.getTableHeaders().get(8), recordCountOfComp);

			// calculate Comp Total
			cashFlowDetails.calculateTotalsColumnData(cashFlowDetails.getTableHeaders().get(13), recordCountOfComp);

			// calculate GuestPass Payment Counts
			cashFlowDetails.calculateCounts(cashFlowDetails.getTableHeaders().get(1), recordCountOfGuestPass);

			// calculate GuestPass Payment Amounts
			cashFlowDetails.calculateAmounts(cashFlowDetails.getTableHeaders().get(2), recordCountOfGuestPass);

			// calculate GuestPass Void Counts
			cashFlowDetails.calculateCounts(cashFlowDetails.getTableHeaders().get(3), recordCountOfGuestPass);

			// calculate GuestPass Void Amounts
			cashFlowDetails.calculateAmounts(cashFlowDetails.getTableHeaders().get(4), recordCountOfGuestPass);

			// calculate GuestPass Sales
			cashFlowDetails.calculateLocationSales(cashFlowDetails.getTableHeaders().get(7), recordCountOfGuestPass);

			// calculate GuestPass Taxes
			cashFlowDetails.calculateLocationTax(cashFlowDetails.getTableHeaders().get(8), recordCountOfGuestPass);

			// calculate GuestPass Total
			cashFlowDetails.calculateTotalsColumnData(cashFlowDetails.getTableHeaders().get(13),
					recordCountOfGuestPass);

			// calculate Special Payment Counts
			cashFlowDetails.calculateCounts(cashFlowDetails.getTableHeaders().get(1), recordCountOfSpecial);

			// calculate Special Payment Amounts
			cashFlowDetails.calculateAmounts(cashFlowDetails.getTableHeaders().get(2), recordCountOfSpecial);

			// calculate Special Void Counts
			cashFlowDetails.calculateCounts(cashFlowDetails.getTableHeaders().get(3), recordCountOfSpecial);

			// calculate Special Void Amounts
			cashFlowDetails.calculateAmounts(cashFlowDetails.getTableHeaders().get(4), recordCountOfSpecial);

			// calculate Special Sales
			cashFlowDetails.calculateLocationSales(cashFlowDetails.getTableHeaders().get(7), recordCountOfSpecial);

			// calculate Special Taxes
			cashFlowDetails.calculateLocationTax(cashFlowDetails.getTableHeaders().get(8), recordCountOfSpecial);

			// calculate Special Total
			cashFlowDetails.calculateTotalsColumnData(cashFlowDetails.getTableHeaders().get(13), recordCountOfSpecial);

			// calculate Account Payment Counts
			cashFlowDetails.calculateCounts(cashFlowDetails.getTableHeaders().get(1), recordCountOfAccount);

			// calculate Account Payment Amounts
			cashFlowDetails.calculateAmounts(cashFlowDetails.getTableHeaders().get(2), recordCountOfAccount);

			// calculate Account Void Counts
			cashFlowDetails.calculateCounts(cashFlowDetails.getTableHeaders().get(3), recordCountOfAccount);

			// calculate Account Void Amounts
			cashFlowDetails.calculateAmounts(cashFlowDetails.getTableHeaders().get(4), recordCountOfAccount);

			// calculate Account Sales
			cashFlowDetails.calculateLocationSales(cashFlowDetails.getTableHeaders().get(7), recordCountOfAccount);

			// calculate Account Taxes
			cashFlowDetails.calculateLocationTax(cashFlowDetails.getTableHeaders().get(8), recordCountOfAccount);

			// calculate Account Total
			cashFlowDetails.calculateTotalsColumnData(cashFlowDetails.getTableHeaders().get(13), recordCountOfAccount);

			// calculate Totals Payment Counts
			cashFlowDetails.calculateCountsForTotals(cashFlowDetails.getTableHeaders().get(1), recordCountOfTotals);

			// calculate Totals Payment Amounts
			cashFlowDetails.calculateAmountsForTotals(cashFlowDetails.getTableHeaders().get(2), recordCountOfTotals);

			// calculate Totals Void Counts
			cashFlowDetails.calculateCountsForTotals(cashFlowDetails.getTableHeaders().get(3), recordCountOfTotals);

			// calculate Totals Void Amounts
			cashFlowDetails.calculateAmountsForTotals(cashFlowDetails.getTableHeaders().get(4), recordCountOfTotals);

			// calculate Totals Declined Counts
			cashFlowDetails.calculateCounts(cashFlowDetails.getTableHeaders().get(5), recordCountOfTotals);

			// calculate Totals Declined Amounts
			cashFlowDetails.calculateAmounts(cashFlowDetails.getTableHeaders().get(6), recordCountOfTotals);

			// calculate Totals Sales
			cashFlowDetails.calculateLocationSalesForTotals(cashFlowDetails.getTableHeaders().get(7),
					recordCountOfTotals);

			// calculate Totals Taxes
			cashFlowDetails.calculateLocationTaxForTotals(cashFlowDetails.getTableHeaders().get(8),
					recordCountOfTotals);

			// calculate Totals Total
			cashFlowDetails.calculateTotalsColumnDataForTotals(cashFlowDetails.getTableHeaders().get(13),
					recordCountOfTotals);

			// verify Report Data
			cashFlowDetails.verifyReportRecords();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * Cash Flow Details International Report Data Validation
	 * 
	 * @author ravindhara, Date:21-10-2022
	 */
	@Test(description = "206385- Cash Flow Details International Report data validation")
	public void CashFlowDetailsInternationalReportDataValidation() {
		final String CASE_NUM = "206385";

		// Reading Test Data from DB
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);
		rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);
		rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

		List<String> paymentType = Arrays
				.asList(rstLocationSummaryData.get(CNLocationSummary.COLUMN_VALUE).split(Constants.DELIMITER_HASH));
		List<String> columnNames = Arrays
				.asList(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME).split(Constants.DELIMITER_HASH));
		String location = propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE);
		try {
			// Navigate to ADM and Select Org
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// process sales API to generate data
			cashFlowDetailsInternational.processAPI(rstProductSummaryData.get(CNProductSummary.ACTUAL_DATA),
					rstProductSummaryData.get(CNProductSummary.REQUIRED_DATA),
					rstProductSummaryData.get(CNProductSummary.DEVICE_ID),
					rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));

			// navigate To Reports
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location and run report
			cashFlowDetailsInternational.selectAndRunReport(rstReportListData.get(CNReportList.REPORT_NAME),
					rstReportListData.get(CNReportList.DATE_RANGE), location);

			// read Report Data
			cashFlowDetailsInternational.readAllRecordsFromCashFlowDetailsTable(
					rstProductSummaryData.get(CNProductSummary.DEVICE_ID), location);
			cashFlowDetailsInternational.getInitialReportsData().putAll(cashFlowDetailsInternational.reportsData);
//			cashFlowDetailsInternational.getInitialReportTotals().putAll(cashFlowDetailsInternational.getReportsTotalData());

			// process sales API to generate data
			cashFlowDetailsInternational.processAPI(rstProductSummaryData.get(CNProductSummary.ACTUAL_DATA),
					rstProductSummaryData.get(CNProductSummary.REQUIRED_DATA),
					rstProductSummaryData.get(CNProductSummary.DEVICE_ID),
					rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));

			foundation.threadWait(Constants.FIFTY_FIVE_SECONDS);

			// run and read report
			foundation.click(ReportList.BTN_RUN_REPORT);

			// read Updated Report Data
			cashFlowDetailsInternational.readAllRecordsFromCashFlowDetailsTable(
					rstProductSummaryData.get(CNProductSummary.DEVICE_ID), location);
			cashFlowDetailsInternational.getJsonSalesData();

			int recordCountOfComp = cashFlowDetailsInternational.getRequiredRecord(paymentType.get(0));
			int recordCountOfGEN3 = cashFlowDetailsInternational.getRequiredRecord(paymentType.get(1));
			int recordCountOfCreditCard = cashFlowDetailsInternational.getRequiredRecord(paymentType.get(2));
			int recordCountOfSpecial = cashFlowDetailsInternational.getRequiredRecord(paymentType.get(3));
			int recordCountOfCash = cashFlowDetailsInternational.getRequiredRecord(paymentType.get(4));
			int recordCountOfAccount = cashFlowDetailsInternational.getRequiredRecord(paymentType.get(5));
			int recordCountOfTotals = cashFlowDetailsInternational.getRequiredRecord(paymentType.get(6));

			// calculate Comp Product Counts
			cashFlowDetailsInternational.calculateProductCounts(cashFlowDetailsInternational.getTableHeaders().get(1),
					recordCountOfComp);

			// calculate Comp Sales Counts
			cashFlowDetailsInternational.calculateCounts(cashFlowDetailsInternational.getTableHeaders().get(2),
					recordCountOfComp);

			// calculate Comp Sales Amount
			cashFlowDetailsInternational.calculateAmounts(cashFlowDetailsInternational.getTableHeaders().get(3),
					recordCountOfComp);

			// calculate Comp Net Sales Amount Inclusive of sales
			cashFlowDetailsInternational.calculateNetSalesIncTax(cashFlowDetailsInternational.getTableHeaders().get(8),
					recordCountOfComp);

			// calculate Comp Net Tax Amount
			cashFlowDetailsInternational.calculateNetTax(cashFlowDetailsInternational.getTableHeaders().get(9),
					recordCountOfComp);

			// calculate Comp Net Sales Amount
			cashFlowDetailsInternational.calculateNetSales(cashFlowDetailsInternational.getTableHeaders().get(10),
					recordCountOfComp);

			// calculate Comp Total Amount
			cashFlowDetailsInternational.calculateNetSalesIncTax(cashFlowDetailsInternational.getTableHeaders().get(11),
					recordCountOfComp);

			// calculate gen3 Product Counts
			cashFlowDetailsInternational.calculateProductCountsForCredit(
					cashFlowDetailsInternational.getTableHeaders().get(1), recordCountOfGEN3);

			// calculate gen3 Sales Counts
			cashFlowDetailsInternational.calculateCounts(cashFlowDetailsInternational.getTableHeaders().get(2),
					recordCountOfGEN3);

			// calculate gen3 Sales Amount
			cashFlowDetailsInternational.calculateAmounts(cashFlowDetailsInternational.getTableHeaders().get(3),
					recordCountOfGEN3);

			// calculate gen3 Void Counts
			cashFlowDetailsInternational.calculateCounts(cashFlowDetailsInternational.getTableHeaders().get(4),
					recordCountOfGEN3);

			// calculate gen3 Void Amount
			cashFlowDetailsInternational.calculateAmounts(cashFlowDetailsInternational.getTableHeaders().get(5),
					recordCountOfGEN3);

			// calculate gen3 Credit Rejected Counts
			cashFlowDetailsInternational.calculateCounts(cashFlowDetailsInternational.getTableHeaders().get(6),
					recordCountOfGEN3);

			// calculate gen3 Credit Rejected Amount
			cashFlowDetailsInternational.calculateAmounts(cashFlowDetailsInternational.getTableHeaders().get(7),
					recordCountOfGEN3);

			// calculate gen3 Net Sales Amount Inclusive of sales
			cashFlowDetailsInternational.calculateNetSalesIncTax(cashFlowDetailsInternational.getTableHeaders().get(8),
					recordCountOfGEN3);

			// calculate gen3 Net Tax Amount
			cashFlowDetailsInternational.calculateNetTaxForCredit(cashFlowDetailsInternational.getTableHeaders().get(9),
					recordCountOfGEN3);

			// calculate gen3 Net Sales Amount
			cashFlowDetailsInternational.calculateNetSales(cashFlowDetailsInternational.getTableHeaders().get(10),
					recordCountOfGEN3);

			// calculate gen3 Total Amount
			cashFlowDetailsInternational.calculateNetSalesIncTax(cashFlowDetailsInternational.getTableHeaders().get(11),
					recordCountOfGEN3);

			// calculate Credit Card Sub Total Product Counts
			cashFlowDetailsInternational.calculateProductCountsForCredit(
					cashFlowDetailsInternational.getTableHeaders().get(1), recordCountOfCreditCard);

			// calculate Credit Card Sub Total Sales Counts
			cashFlowDetailsInternational.calculateCounts(cashFlowDetailsInternational.getTableHeaders().get(2),
					recordCountOfCreditCard);

			// calculate Credit Card Sub Total Sales Amount
			cashFlowDetailsInternational.calculateAmounts(cashFlowDetailsInternational.getTableHeaders().get(3),
					recordCountOfCreditCard);

			// calculate Credit Card Sub Total Void Counts
			cashFlowDetailsInternational.calculateCounts(cashFlowDetailsInternational.getTableHeaders().get(4),
					recordCountOfCreditCard);

			// calculate Credit Card Sub Total Void Amount
			cashFlowDetailsInternational.calculateAmounts(cashFlowDetailsInternational.getTableHeaders().get(5),
					recordCountOfCreditCard);

			// calculate Credit Card Sub Total Credit Rejected Counts
			cashFlowDetailsInternational.calculateCounts(cashFlowDetailsInternational.getTableHeaders().get(6),
					recordCountOfCreditCard);

			// calculate Credit Card Sub Total Credit Rejected Amount
			cashFlowDetailsInternational.calculateAmounts(cashFlowDetailsInternational.getTableHeaders().get(7),
					recordCountOfCreditCard);

			// calculate Credit Card Sub Total Net Sales Amount Inclusive of sales
			cashFlowDetailsInternational.calculateNetSalesIncTax(cashFlowDetailsInternational.getTableHeaders().get(8),
					recordCountOfCreditCard);

			// calculate Credit Card Sub Total Net Tax Amount
			cashFlowDetailsInternational.calculateNetTaxForCredit(cashFlowDetailsInternational.getTableHeaders().get(9),
					recordCountOfCreditCard);

			// calculate Credit Card Sub Total Net Sales Amount
			cashFlowDetailsInternational.calculateNetSales(cashFlowDetailsInternational.getTableHeaders().get(10),
					recordCountOfCreditCard);

			// calculate Credit Card Sub Total Total Amount
			cashFlowDetailsInternational.calculateNetSalesIncTax(cashFlowDetailsInternational.getTableHeaders().get(11),
					recordCountOfCreditCard);

			// calculate Special - SPECIAL Product Counts
			cashFlowDetailsInternational.calculateProductCounts(cashFlowDetailsInternational.getTableHeaders().get(1),
					recordCountOfSpecial);

			// calculate Special - SPECIAL Sales Counts
			cashFlowDetailsInternational.calculateCounts(cashFlowDetailsInternational.getTableHeaders().get(2),
					recordCountOfSpecial);

			// calculate Special - SPECIAL Sales Amount
			cashFlowDetailsInternational.calculateAmounts(cashFlowDetailsInternational.getTableHeaders().get(3),
					recordCountOfSpecial);

			// calculate Special - SPECIAL Void Counts
			cashFlowDetailsInternational.calculateCounts(cashFlowDetailsInternational.getTableHeaders().get(4),
					recordCountOfSpecial);

			// calculate Special - SPECIAL Void Amount
			cashFlowDetailsInternational.calculateAmounts(cashFlowDetailsInternational.getTableHeaders().get(5),
					recordCountOfSpecial);

			// calculate Special - SPECIAL Net Sales Amount Inclusive of sales
			cashFlowDetailsInternational.calculateNetSalesIncTax(cashFlowDetailsInternational.getTableHeaders().get(8),
					recordCountOfSpecial);

			// calculate Special - SPECIAL Net Tax Amount
			cashFlowDetailsInternational.calculateNetTax(cashFlowDetailsInternational.getTableHeaders().get(9),
					recordCountOfSpecial);

			// calculate Special - SPECIAL Net Sales Amount
			cashFlowDetailsInternational.calculateNetSales(cashFlowDetailsInternational.getTableHeaders().get(10),
					recordCountOfSpecial);

			// calculate Special - SPECIAL Total Amount
			cashFlowDetailsInternational.calculateNetSalesIncTax(cashFlowDetailsInternational.getTableHeaders().get(11),
					recordCountOfSpecial);

			// calculate Cash Product Counts
			cashFlowDetailsInternational.calculateProductCounts(cashFlowDetailsInternational.getTableHeaders().get(1),
					recordCountOfCash);

			// calculate Cash Sales Counts
			cashFlowDetailsInternational.calculateCounts(cashFlowDetailsInternational.getTableHeaders().get(2),
					recordCountOfCash);

			// calculate Cash Sales Amount
			cashFlowDetailsInternational.calculateAmounts(cashFlowDetailsInternational.getTableHeaders().get(3),
					recordCountOfCash);

			// calculate Cash Void Counts
			cashFlowDetailsInternational.calculateCounts(cashFlowDetailsInternational.getTableHeaders().get(4),
					recordCountOfCash);

			// calculate Cash Void Amount
			cashFlowDetailsInternational.calculateAmounts(cashFlowDetailsInternational.getTableHeaders().get(5),
					recordCountOfCash);

			// calculate Cash Net Sales Amount Inclusive of sales
			cashFlowDetailsInternational.calculateNetSalesIncTax(cashFlowDetailsInternational.getTableHeaders().get(8),
					recordCountOfCash);

			// calculate Cash Net Tax Amount
			cashFlowDetailsInternational.calculateNetTax(cashFlowDetailsInternational.getTableHeaders().get(9),
					recordCountOfCash);

			// calculate Cash Net Sales Amount
			cashFlowDetailsInternational.calculateNetSales(cashFlowDetailsInternational.getTableHeaders().get(10),
					recordCountOfCash);

			// calculate Cash Total Amount
			cashFlowDetailsInternational.calculateNetSalesIncTax(cashFlowDetailsInternational.getTableHeaders().get(11),
					recordCountOfCash);

			// calculate Account Product Counts
			cashFlowDetailsInternational.calculateProductCounts(cashFlowDetailsInternational.getTableHeaders().get(1),
					recordCountOfAccount);

			// calculate Account Sales Counts
			cashFlowDetailsInternational.calculateCounts(cashFlowDetailsInternational.getTableHeaders().get(2),
					recordCountOfAccount);

			// calculate Account Sales Amount
			cashFlowDetailsInternational.calculateAmounts(cashFlowDetailsInternational.getTableHeaders().get(3),
					recordCountOfAccount);

			// calculate Account Void Counts
			cashFlowDetailsInternational.calculateCounts(cashFlowDetailsInternational.getTableHeaders().get(4),
					recordCountOfAccount);

			// calculate Account Void Amount
			cashFlowDetailsInternational.calculateAmounts(cashFlowDetailsInternational.getTableHeaders().get(5),
					recordCountOfAccount);

			// calculate Account Net Sales Amount Inclusive of sales
			cashFlowDetailsInternational.calculateNetSalesIncTax(cashFlowDetailsInternational.getTableHeaders().get(8),
					recordCountOfAccount);

			// calculate Account Net Tax Amount
			cashFlowDetailsInternational.calculateNetTax(cashFlowDetailsInternational.getTableHeaders().get(9),
					recordCountOfAccount);

			// calculate Account Net Sales Amount
			cashFlowDetailsInternational.calculateNetSales(cashFlowDetailsInternational.getTableHeaders().get(10),
					recordCountOfAccount);

			// calculate Account Total Amount
			cashFlowDetailsInternational.calculateNetSalesIncTax(cashFlowDetailsInternational.getTableHeaders().get(11),
					recordCountOfAccount);

			// calculate Totals Product Counts
			cashFlowDetailsInternational.calculateProductCountsForTotals(
					cashFlowDetailsInternational.getTableHeaders().get(1), recordCountOfTotals);

			// calculate Totals Sales Counts
			cashFlowDetailsInternational.calculateCountsForTotals(cashFlowDetailsInternational.getTableHeaders().get(2),
					recordCountOfTotals);

			// calculate Totals Sales Amount
			cashFlowDetailsInternational.calculateAmountsForTotals(
					cashFlowDetailsInternational.getTableHeaders().get(3), recordCountOfTotals);

			// calculate Totals Void Counts
			cashFlowDetailsInternational.calculateCountsForTotals(cashFlowDetailsInternational.getTableHeaders().get(4),
					recordCountOfTotals);

			// calculate Totals Void Amount
			cashFlowDetailsInternational.calculateAmountsForTotals(
					cashFlowDetailsInternational.getTableHeaders().get(5), recordCountOfTotals);

			// calculate Totals Credit Rejected Counts
			cashFlowDetailsInternational.calculateCounts(cashFlowDetailsInternational.getTableHeaders().get(6),
					recordCountOfTotals);

			// calculate Totals Credit Rejected Amount
			cashFlowDetailsInternational.calculateAmounts(cashFlowDetailsInternational.getTableHeaders().get(7),
					recordCountOfTotals);

			// calculate Totals Net Sales Amount Inclusive of sales
			cashFlowDetailsInternational.calculateNetSalesIncTaxForTotals(
					cashFlowDetailsInternational.getTableHeaders().get(8), recordCountOfTotals);

			// calculate Totals Net Tax Amount
			cashFlowDetailsInternational.calculateNetTaxForTotals(cashFlowDetailsInternational.getTableHeaders().get(9),
					recordCountOfTotals);

			// calculate Totals Net Sales Amount
			cashFlowDetailsInternational.calculateNetSalesForTotals(
					cashFlowDetailsInternational.getTableHeaders().get(10), recordCountOfTotals);

			// calculate Totals Total Amount
			cashFlowDetailsInternational.calculateNetSalesIncTaxForTotals(
					cashFlowDetailsInternational.getTableHeaders().get(11), recordCountOfTotals);

			// verify Report Headers
			cashFlowDetailsInternational.verifyReportHeaders(columnNames);

			// verify Report Data
			cashFlowDetailsInternational.verifyReportRecords();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * Cash Flow Employee Report Data Validation
	 * 
	 * @author ravindhara, Date:26-10-2022
	 */
	@Test(description = "206407- Cash Flow Employee Report data validation")
	public void CashFlowEmployeeReportDataValidation() {
		final String CASE_NUM = "206407";

		// Reading Test Data from DB
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);
		rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);
		rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

		List<String> paymentType = Arrays
				.asList(rstLocationSummaryData.get(CNLocationSummary.COLUMN_VALUE).split(Constants.DELIMITER_HASH));
		List<String> columnNames = Arrays
				.asList(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME).split(Constants.DELIMITER_HASH));
		String location = propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE);
		try {
			// Navigate to ADM and Select Org
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// process sales API to generate data
			CashFlowEmployee.processAPI(rstProductSummaryData.get(CNProductSummary.ACTUAL_DATA),
					rstProductSummaryData.get(CNProductSummary.REQUIRED_DATA),
					rstProductSummaryData.get(CNProductSummary.DEVICE_ID),
					rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));

			// navigate To Reports
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location and run report
			CashFlowEmployee.selectAndRunReport(rstReportListData.get(CNReportList.REPORT_NAME),
					rstReportListData.get(CNReportList.DATE_RANGE), location);

			// read Report Data
			CashFlowEmployee.readAllRecordsFromCashFlowDetailsTable(
					rstProductSummaryData.get(CNProductSummary.DEVICE_ID), location);
			CashFlowEmployee.getInitialReportsData().putAll(CashFlowEmployee.reportsData);
			CashFlowEmployee.getInitialReportTotals().putAll(CashFlowEmployee.getReportsTotalData());

			// process sales API to generate data
			CashFlowEmployee.processAPI(rstProductSummaryData.get(CNProductSummary.ACTUAL_DATA),
					rstProductSummaryData.get(CNProductSummary.REQUIRED_DATA),
					rstProductSummaryData.get(CNProductSummary.DEVICE_ID),
					rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));

			// navigate To Reports
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location and run report
			CashFlowEmployee.selectAndRunReport(rstReportListData.get(CNReportList.REPORT_NAME),
					rstReportListData.get(CNReportList.DATE_RANGE), location);

			// read Updated Report Data
			CashFlowEmployee.readAllRecordsFromCashFlowDetailsTable(
					rstProductSummaryData.get(CNProductSummary.DEVICE_ID), location);
			CashFlowEmployee.getJsonSalesData();

			int recordCountOfCash = CashFlowEmployee.getRequiredRecord(paymentType.get(0));
			int recordCountOfCreditCard = CashFlowEmployee.getRequiredRecord(paymentType.get(1));
			int recordCountOfGEN3 = CashFlowEmployee.getRequiredRecord(paymentType.get(2));
			int recordCountOfSOGO = CashFlowEmployee.getRequiredRecord(paymentType.get(3));
			int recordCountOfComp = CashFlowEmployee.getRequiredRecord(paymentType.get(4));
			int recordCountOfGuestPass = CashFlowEmployee.getRequiredRecord(paymentType.get(5));
			int recordCountOfSpecial = CashFlowEmployee.getRequiredRecord(paymentType.get(6));
			int recordCountOfAccount = CashFlowEmployee.getRequiredRecord(paymentType.get(7));
			int recordCountOfTotals = CashFlowEmployee.getRequiredRecord(paymentType.get(8));

			// verify Report Headers
			CashFlowEmployee.verifyReportHeaders(columnNames);

			// calculate Credit Payment Counts
			CashFlowEmployee.calculateCounts(CashFlowEmployee.getTableHeaders().get(1), recordCountOfCash);

			// calculate Credit Payment Amounts
			CashFlowEmployee.calculateAmounts(CashFlowEmployee.getTableHeaders().get(2), recordCountOfCash);

			// calculate Credit Void Counts
			CashFlowEmployee.calculateCounts(CashFlowEmployee.getTableHeaders().get(3), recordCountOfCash);

			// calculate Credit Void Amounts
			CashFlowEmployee.calculateAmounts(CashFlowEmployee.getTableHeaders().get(4), recordCountOfCash);

			// calculate Credit Sales
			CashFlowEmployee.calculateLocationSales(CashFlowEmployee.getTableHeaders().get(7), recordCountOfCash);

			// calculate Credit Taxes
			CashFlowEmployee.calculateLocationTax(CashFlowEmployee.getTableHeaders().get(8), recordCountOfCash);

			// calculate Credit Total
			CashFlowEmployee.calculateTotalsColumnData(CashFlowEmployee.getTableHeaders().get(13), recordCountOfCash);

			// calculate Credit Payment Counts
			CashFlowEmployee.calculateCounts(CashFlowEmployee.getTableHeaders().get(1), recordCountOfCreditCard);

			// calculate Credit Payment Amounts
			CashFlowEmployee.calculateAmounts(CashFlowEmployee.getTableHeaders().get(2), recordCountOfCreditCard);

			// calculate Credit Void Counts
			CashFlowEmployee.calculateCounts(CashFlowEmployee.getTableHeaders().get(3), recordCountOfCreditCard);

			// calculate Credit Void Amounts
			CashFlowEmployee.calculateAmounts(CashFlowEmployee.getTableHeaders().get(4), recordCountOfCreditCard);

			// calculate Credit Declined Counts
			CashFlowEmployee.calculateCounts(CashFlowEmployee.getTableHeaders().get(5), recordCountOfCreditCard);

			// calculate Credit Declined Amounts
			CashFlowEmployee.calculateAmounts(CashFlowEmployee.getTableHeaders().get(6), recordCountOfCreditCard);

			// calculate Credit Sales
			CashFlowEmployee.calculateLocationSales(CashFlowEmployee.getTableHeaders().get(7), recordCountOfCreditCard);

			// calculate Credit Taxes
			CashFlowEmployee.calculateLocationTax(CashFlowEmployee.getTableHeaders().get(8), recordCountOfCreditCard);

			// calculate Credit Total
			CashFlowEmployee.calculateTotalsColumnData(CashFlowEmployee.getTableHeaders().get(13),
					recordCountOfCreditCard);

			// calculate gen3 Payment Counts
			CashFlowEmployee.calculateCounts(CashFlowEmployee.getTableHeaders().get(1), recordCountOfGEN3);

			// calculate gen3 Payment Amounts
			CashFlowEmployee.calculateAmounts(CashFlowEmployee.getTableHeaders().get(2), recordCountOfGEN3);

			// calculate gen3 Void Counts
			CashFlowEmployee.calculateCounts(CashFlowEmployee.getTableHeaders().get(3), recordCountOfGEN3);

			// calculate gen3 Void Amounts
			CashFlowEmployee.calculateAmounts(CashFlowEmployee.getTableHeaders().get(4), recordCountOfGEN3);

			// calculate gen3 Declined Counts
			CashFlowEmployee.calculateCounts(CashFlowEmployee.getTableHeaders().get(5), recordCountOfGEN3);

			// calculate gen3 Declined Amounts
			CashFlowEmployee.calculateAmounts(CashFlowEmployee.getTableHeaders().get(6), recordCountOfGEN3);

			// calculate gen3 Sales
			CashFlowEmployee.calculateLocationSales(CashFlowEmployee.getTableHeaders().get(7), recordCountOfGEN3);

			// calculate gen3 Taxes
			CashFlowEmployee.calculateLocationTax(CashFlowEmployee.getTableHeaders().get(8), recordCountOfGEN3);

			// calculate gen3 Total
			CashFlowEmployee.calculateTotalsColumnData(CashFlowEmployee.getTableHeaders().get(13), recordCountOfGEN3);

			// calculate SOGO Payment Counts
			CashFlowEmployee.calculateCounts(CashFlowEmployee.getTableHeaders().get(1), recordCountOfSOGO);

			// calculate SOGO Payment Amounts
			CashFlowEmployee.calculateAmounts(CashFlowEmployee.getTableHeaders().get(2), recordCountOfSOGO);

			// calculate SOGO Void Counts
			CashFlowEmployee.calculateCounts(CashFlowEmployee.getTableHeaders().get(3), recordCountOfSOGO);

			// calculate SOGO Void Amounts
			CashFlowEmployee.calculateAmounts(CashFlowEmployee.getTableHeaders().get(4), recordCountOfSOGO);

			// calculate SOGO Sales
			CashFlowEmployee.calculateLocationSales(CashFlowEmployee.getTableHeaders().get(7), recordCountOfSOGO);

			// calculate SOGO Taxes
			CashFlowEmployee.calculateLocationTax(CashFlowEmployee.getTableHeaders().get(8), recordCountOfSOGO);

			// calculate SOGO Total
			CashFlowEmployee.calculateTotalsColumnData(CashFlowEmployee.getTableHeaders().get(13), recordCountOfSOGO);

			// calculate Comp Payment Counts
			CashFlowEmployee.calculateCounts(CashFlowEmployee.getTableHeaders().get(1), recordCountOfComp);

			// calculate Comp Payment Amounts
			CashFlowEmployee.calculateAmounts(CashFlowEmployee.getTableHeaders().get(2), recordCountOfComp);

			// calculate Comp Void Counts
			CashFlowEmployee.calculateCounts(CashFlowEmployee.getTableHeaders().get(3), recordCountOfComp);

			// calculate Comp Void Amounts
			CashFlowEmployee.calculateAmounts(CashFlowEmployee.getTableHeaders().get(4), recordCountOfComp);

			// calculate Comp Sales
			CashFlowEmployee.calculateLocationSales(CashFlowEmployee.getTableHeaders().get(7), recordCountOfComp);

			// calculate Comp Taxes
			CashFlowEmployee.calculateLocationTax(CashFlowEmployee.getTableHeaders().get(8), recordCountOfComp);

			// calculate Comp Total
			CashFlowEmployee.calculateTotalsColumnData(CashFlowEmployee.getTableHeaders().get(13), recordCountOfComp);

			// calculate GuestPass Payment Counts
			CashFlowEmployee.calculateCounts(CashFlowEmployee.getTableHeaders().get(1), recordCountOfGuestPass);

			// calculate GuestPass Payment Amounts
			CashFlowEmployee.calculateAmounts(CashFlowEmployee.getTableHeaders().get(2), recordCountOfGuestPass);

			// calculate GuestPass Void Counts
			CashFlowEmployee.calculateCounts(CashFlowEmployee.getTableHeaders().get(3), recordCountOfGuestPass);

			// calculate GuestPass Void Amounts
			CashFlowEmployee.calculateAmounts(CashFlowEmployee.getTableHeaders().get(4), recordCountOfGuestPass);

			// calculate GuestPass Sales
			CashFlowEmployee.calculateLocationSales(CashFlowEmployee.getTableHeaders().get(7), recordCountOfGuestPass);

			// calculate GuestPass Taxes
			CashFlowEmployee.calculateLocationTax(CashFlowEmployee.getTableHeaders().get(8), recordCountOfGuestPass);

			// calculate GuestPass Total
			CashFlowEmployee.calculateTotalsColumnData(CashFlowEmployee.getTableHeaders().get(13),
					recordCountOfGuestPass);

			// calculate Special Payment Counts
			CashFlowEmployee.calculateCounts(CashFlowEmployee.getTableHeaders().get(1), recordCountOfSpecial);

			// calculate Special Payment Amounts
			CashFlowEmployee.calculateAmounts(CashFlowEmployee.getTableHeaders().get(2), recordCountOfSpecial);

			// calculate Special Void Counts
			CashFlowEmployee.calculateCounts(CashFlowEmployee.getTableHeaders().get(3), recordCountOfSpecial);

			// calculate Special Void Amounts
			CashFlowEmployee.calculateAmounts(CashFlowEmployee.getTableHeaders().get(4), recordCountOfSpecial);

			// calculate Special Sales
			CashFlowEmployee.calculateLocationSales(CashFlowEmployee.getTableHeaders().get(7), recordCountOfSpecial);

			// calculate Special Taxes
			CashFlowEmployee.calculateLocationTax(CashFlowEmployee.getTableHeaders().get(8), recordCountOfSpecial);

			// calculate Special Total
			CashFlowEmployee.calculateTotalsColumnData(CashFlowEmployee.getTableHeaders().get(13),
					recordCountOfSpecial);

			// calculate Account Payment Counts
			CashFlowEmployee.calculateCounts(CashFlowEmployee.getTableHeaders().get(1), recordCountOfAccount);

			// calculate Account Payment Amounts
			CashFlowEmployee.calculateAmounts(CashFlowEmployee.getTableHeaders().get(2), recordCountOfAccount);

			// calculate Account Void Counts
			CashFlowEmployee.calculateCounts(CashFlowEmployee.getTableHeaders().get(3), recordCountOfAccount);

			// calculate Account Void Amounts
			CashFlowEmployee.calculateAmounts(CashFlowEmployee.getTableHeaders().get(4), recordCountOfAccount);

			// calculate Account Sales
			CashFlowEmployee.calculateLocationSales(CashFlowEmployee.getTableHeaders().get(7), recordCountOfAccount);

			// calculate Account Taxes
			CashFlowEmployee.calculateLocationTax(CashFlowEmployee.getTableHeaders().get(8), recordCountOfAccount);

			// calculate Account Total
			CashFlowEmployee.calculateTotalsColumnData(CashFlowEmployee.getTableHeaders().get(13),
					recordCountOfAccount);

			// calculate Totals Payment Counts
			CashFlowEmployee.calculateCountsForTotals(CashFlowEmployee.getTableHeaders().get(1), recordCountOfTotals);

			// calculate Totals Payment Amounts
			CashFlowEmployee.calculateAmountsForTotals(CashFlowEmployee.getTableHeaders().get(2), recordCountOfTotals);

			// calculate Totals Void Counts
			CashFlowEmployee.calculateCountsForTotals(CashFlowEmployee.getTableHeaders().get(3), recordCountOfTotals);

			// calculate Totals Void Amounts
			CashFlowEmployee.calculateAmountsForTotals(CashFlowEmployee.getTableHeaders().get(4), recordCountOfTotals);

			// calculate Totals Declined Counts
			CashFlowEmployee.calculateCounts(CashFlowEmployee.getTableHeaders().get(5), recordCountOfTotals);

			// calculate Totals Declined Amounts
			CashFlowEmployee.calculateAmounts(CashFlowEmployee.getTableHeaders().get(6), recordCountOfTotals);

			// calculate Totals Sales
			CashFlowEmployee.calculateLocationSalesForTotals(CashFlowEmployee.getTableHeaders().get(7),
					recordCountOfTotals);

			// calculate Totals Taxes
			CashFlowEmployee.calculateLocationTaxForTotals(CashFlowEmployee.getTableHeaders().get(8),
					recordCountOfTotals);

			// calculate Totals Total
			CashFlowEmployee.calculateTotalsColumnDataForTotals(CashFlowEmployee.getTableHeaders().get(13),
					recordCountOfTotals);

			// verify Report Data
			CashFlowEmployee.verifyReportRecords();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * UFS Report Data Validation
	 * 
	 * @author ravindhara, Date:28-10-2022
	 */
	@Test(description = "206324- UFS Report data validation")
	public void UFSReportDataValidation() {
		final String CASE_NUM = "206411";

		// Reading Test Data from DB
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);
		rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);
		rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

		List<String> paymentType = Arrays
				.asList(rstLocationSummaryData.get(CNLocationSummary.COLUMN_VALUE).split(Constants.DELIMITER_HASH));
		List<String> columnNames = Arrays
				.asList(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME).split(Constants.DELIMITER_TILD));
		String location = propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE);
		try {
			// Navigate to ADM and Select Org
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// process sales API to generate data
			ufsReport.processAPI(rstProductSummaryData.get(CNProductSummary.ACTUAL_DATA),
					rstProductSummaryData.get(CNProductSummary.REQUIRED_DATA),
					rstProductSummaryData.get(CNProductSummary.DEVICE_ID),
					rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));

			// navigate To Reports
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location and run report
			ufsReport.selectAndRunReport(rstReportListData.get(CNReportList.REPORT_NAME),
					rstReportListData.get(CNReportList.DATE_RANGE), location);

			// read Report Data
			ufsReport.readAllRecordsFromCashFlowDetailsTable(rstProductSummaryData.get(CNProductSummary.DEVICE_ID),
					location);
			ufsReport.getInitialReportsData().putAll(ufsReport.reportsData);
			ufsReport.getInitialReportTotals().putAll(ufsReport.getReportsTotalData());

			// Read the Report the Data
			ufsReport.getTblRecordsUIOfSalesTimeDetails(location);
			ufsReport.getIntialDataOfSalesTimeDetails().putAll(ufsReport.getReportsDataOfSalesTimeDetails());
			ufsReport.getUpdatedTableFootersOfSalesTimeDetails().putAll(ufsReport.getTableFootersOfSalesTimeDetails());

			// process sales API to generate data
			ufsReport.processAPI(rstProductSummaryData.get(CNProductSummary.ACTUAL_DATA),
					rstProductSummaryData.get(CNProductSummary.REQUIRED_DATA),
					rstProductSummaryData.get(CNProductSummary.DEVICE_ID),
					rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));

			// navigate To Reports
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location and run report
			ufsReport.selectAndRunReport(rstReportListData.get(CNReportList.REPORT_NAME),
					rstReportListData.get(CNReportList.DATE_RANGE), location);

			// read Updated Report Data
			ufsReport.readAllRecordsFromCashFlowDetailsTable(rstProductSummaryData.get(CNProductSummary.DEVICE_ID),
					location);
			ufsReport.getJsonSalesData();

			ufsReport.getTblRecordsUIOfSalesTimeDetails(location);

			int recordCountOfCash = ufsReport.getRequiredRecord(paymentType.get(0));
			int recordCountOfCreditCard = ufsReport.getRequiredRecord(paymentType.get(1));
			int recordCountOfGEN3 = ufsReport.getRequiredRecord(paymentType.get(2));
			int recordCountOfSOGO = ufsReport.getRequiredRecord(paymentType.get(3));
			int recordCountOfComp = ufsReport.getRequiredRecord(paymentType.get(4));
			int recordCountOfGuestPass = ufsReport.getRequiredRecord(paymentType.get(5));
			int recordCountOfSpecial = ufsReport.getRequiredRecord(paymentType.get(6));
			int recordCountOfAccount = ufsReport.getRequiredRecord(paymentType.get(7));
			int recordCountOfTotals = ufsReport.getRequiredRecord(paymentType.get(8));

			// verify Report Headers
			ufsReport.verifyReportHeaders(columnNames.get(0));

			// calculate Credit Payment Counts
			ufsReport.calculateCounts(ufsReport.getTableHeaders().get(1), recordCountOfCash);

			// calculate Credit Payment Amounts
			ufsReport.calculateAmounts(ufsReport.getTableHeaders().get(2), recordCountOfCash);

			// calculate Credit Void Counts
			ufsReport.calculateCounts(ufsReport.getTableHeaders().get(3), recordCountOfCash);

			// calculate Credit Void Amounts
			ufsReport.calculateAmounts(ufsReport.getTableHeaders().get(4), recordCountOfCash);

			// calculate Credit Sales
			ufsReport.calculateLocationSales(ufsReport.getTableHeaders().get(7), recordCountOfCash);

			// calculate Credit Taxes
			ufsReport.calculateLocationTax(ufsReport.getTableHeaders().get(8), recordCountOfCash);

			// calculate Credit Total
			ufsReport.calculateTotalsColumnData(ufsReport.getTableHeaders().get(13), recordCountOfCash);

			// calculate Credit Payment Counts
			ufsReport.calculateCounts(ufsReport.getTableHeaders().get(1), recordCountOfCreditCard);

			// calculate Credit Payment Amounts
			ufsReport.calculateAmounts(ufsReport.getTableHeaders().get(2), recordCountOfCreditCard);

			// calculate Credit Void Counts
			ufsReport.calculateCounts(ufsReport.getTableHeaders().get(3), recordCountOfCreditCard);

			// calculate Credit Void Amounts
			ufsReport.calculateAmounts(ufsReport.getTableHeaders().get(4), recordCountOfCreditCard);

			// calculate Credit Declined Counts
			ufsReport.calculateCounts(ufsReport.getTableHeaders().get(5), recordCountOfCreditCard);

			// calculate Credit Declined Amounts
			ufsReport.calculateAmounts(ufsReport.getTableHeaders().get(6), recordCountOfCreditCard);

			// calculate Credit Sales
			ufsReport.calculateLocationSales(ufsReport.getTableHeaders().get(7), recordCountOfCreditCard);

			// calculate Credit Taxes
			ufsReport.calculateLocationTax(ufsReport.getTableHeaders().get(8), recordCountOfCreditCard);

			// calculate Credit Total
			ufsReport.calculateTotalsColumnData(ufsReport.getTableHeaders().get(13), recordCountOfCreditCard);

			// calculate gen3 Payment Counts
			ufsReport.calculateCounts(ufsReport.getTableHeaders().get(1), recordCountOfGEN3);

			// calculate gen3 Payment Amounts
			ufsReport.calculateAmounts(ufsReport.getTableHeaders().get(2), recordCountOfGEN3);

			// calculate gen3 Void Counts
			ufsReport.calculateCounts(ufsReport.getTableHeaders().get(3), recordCountOfGEN3);

			// calculate gen3 Void Amounts
			ufsReport.calculateAmounts(ufsReport.getTableHeaders().get(4), recordCountOfGEN3);

			// calculate gen3 Declined Counts
			ufsReport.calculateCounts(ufsReport.getTableHeaders().get(5), recordCountOfGEN3);

			// calculate gen3 Declined Amounts
			ufsReport.calculateAmounts(ufsReport.getTableHeaders().get(6), recordCountOfGEN3);

			// calculate gen3 Sales
			ufsReport.calculateLocationSales(ufsReport.getTableHeaders().get(7), recordCountOfGEN3);

			// calculate gen3 Taxes
			ufsReport.calculateLocationTax(ufsReport.getTableHeaders().get(8), recordCountOfGEN3);

			// calculate gen3 Total
			ufsReport.calculateTotalsColumnData(ufsReport.getTableHeaders().get(13), recordCountOfGEN3);

			// calculate SOGO Payment Counts
			ufsReport.calculateCounts(ufsReport.getTableHeaders().get(1), recordCountOfSOGO);

			// calculate SOGO Payment Amounts
			ufsReport.calculateAmounts(ufsReport.getTableHeaders().get(2), recordCountOfSOGO);

			// calculate SOGO Void Counts
			ufsReport.calculateCounts(ufsReport.getTableHeaders().get(3), recordCountOfSOGO);

			// calculate SOGO Void Amounts
			ufsReport.calculateAmounts(ufsReport.getTableHeaders().get(4), recordCountOfSOGO);

			// calculate SOGO Sales
			ufsReport.calculateLocationSales(ufsReport.getTableHeaders().get(7), recordCountOfSOGO);

			// calculate SOGO Taxes
			ufsReport.calculateLocationTax(ufsReport.getTableHeaders().get(8), recordCountOfSOGO);

			// calculate SOGO Total
			ufsReport.calculateTotalsColumnData(ufsReport.getTableHeaders().get(13), recordCountOfSOGO);

			// calculate Comp Payment Counts
			ufsReport.calculateCounts(ufsReport.getTableHeaders().get(1), recordCountOfComp);

			// calculate Comp Payment Amounts
			ufsReport.calculateAmounts(ufsReport.getTableHeaders().get(2), recordCountOfComp);

			// calculate Comp Void Counts
			ufsReport.calculateCounts(ufsReport.getTableHeaders().get(3), recordCountOfComp);

			// calculate Comp Void Amounts
			ufsReport.calculateAmounts(ufsReport.getTableHeaders().get(4), recordCountOfComp);

			// calculate Comp Sales
			ufsReport.calculateLocationSales(ufsReport.getTableHeaders().get(7), recordCountOfComp);

			// calculate Comp Taxes
			ufsReport.calculateLocationTax(ufsReport.getTableHeaders().get(8), recordCountOfComp);

			// calculate Comp Total
			ufsReport.calculateTotalsColumnData(ufsReport.getTableHeaders().get(13), recordCountOfComp);

			// calculate GuestPass Payment Counts
			ufsReport.calculateCounts(ufsReport.getTableHeaders().get(1), recordCountOfGuestPass);

			// calculate GuestPass Payment Amounts
			ufsReport.calculateAmounts(ufsReport.getTableHeaders().get(2), recordCountOfGuestPass);

			// calculate GuestPass Void Counts
			ufsReport.calculateCounts(ufsReport.getTableHeaders().get(3), recordCountOfGuestPass);

			// calculate GuestPass Void Amounts
			ufsReport.calculateAmounts(ufsReport.getTableHeaders().get(4), recordCountOfGuestPass);

			// calculate GuestPass Sales
			ufsReport.calculateLocationSales(ufsReport.getTableHeaders().get(7), recordCountOfGuestPass);

			// calculate GuestPass Taxes
			ufsReport.calculateLocationTax(ufsReport.getTableHeaders().get(8), recordCountOfGuestPass);

			// calculate GuestPass Total
			ufsReport.calculateTotalsColumnData(ufsReport.getTableHeaders().get(13), recordCountOfGuestPass);

			// calculate Special Payment Counts
			ufsReport.calculateCounts(ufsReport.getTableHeaders().get(1), recordCountOfSpecial);

			// calculate Special Payment Amounts
			ufsReport.calculateAmounts(ufsReport.getTableHeaders().get(2), recordCountOfSpecial);

			// calculate Special Void Counts
			ufsReport.calculateCounts(ufsReport.getTableHeaders().get(3), recordCountOfSpecial);

			// calculate Special Void Amounts
			ufsReport.calculateAmounts(ufsReport.getTableHeaders().get(4), recordCountOfSpecial);

			// calculate Special Sales
			ufsReport.calculateLocationSales(ufsReport.getTableHeaders().get(7), recordCountOfSpecial);

			// calculate Special Taxes
			ufsReport.calculateLocationTax(ufsReport.getTableHeaders().get(8), recordCountOfSpecial);

			// calculate Special Total
			ufsReport.calculateTotalsColumnData(ufsReport.getTableHeaders().get(13), recordCountOfSpecial);

			// calculate Account Payment Counts
			ufsReport.calculateCounts(ufsReport.getTableHeaders().get(1), recordCountOfAccount);

			// calculate Account Payment Amounts
			ufsReport.calculateAmounts(ufsReport.getTableHeaders().get(2), recordCountOfAccount);

			// calculate Account Void Counts
			ufsReport.calculateCounts(ufsReport.getTableHeaders().get(3), recordCountOfAccount);

			// calculate Account Void Amounts
			ufsReport.calculateAmounts(ufsReport.getTableHeaders().get(4), recordCountOfAccount);

			// calculate Account Sales
			ufsReport.calculateLocationSales(ufsReport.getTableHeaders().get(7), recordCountOfAccount);

			// calculate Account Taxes
			ufsReport.calculateLocationTax(ufsReport.getTableHeaders().get(8), recordCountOfAccount);

			// calculate Account Total
			ufsReport.calculateTotalsColumnData(ufsReport.getTableHeaders().get(13), recordCountOfAccount);

			// calculate Totals Payment Counts
			ufsReport.calculateCountsForTotals(ufsReport.getTableHeaders().get(1), recordCountOfTotals);

			// calculate Totals Payment Amounts
			ufsReport.calculateAmountsForTotals(ufsReport.getTableHeaders().get(2), recordCountOfTotals);

			// calculate Totals Void Counts
			ufsReport.calculateCountsForTotals(ufsReport.getTableHeaders().get(3), recordCountOfTotals);

			// calculate Totals Void Amounts
			ufsReport.calculateAmountsForTotals(ufsReport.getTableHeaders().get(4), recordCountOfTotals);

			// calculate Totals Declined Counts
			ufsReport.calculateCounts(ufsReport.getTableHeaders().get(5), recordCountOfTotals);

			// calculate Totals Declined Amounts
			ufsReport.calculateAmounts(ufsReport.getTableHeaders().get(6), recordCountOfTotals);

			// calculate Totals Sales
			ufsReport.calculateLocationSalesForTotals(ufsReport.getTableHeaders().get(7), recordCountOfTotals);

			// calculate Totals Taxes
			ufsReport.calculateLocationTaxForTotals(ufsReport.getTableHeaders().get(8), recordCountOfTotals);

			// calculate Totals Total
			ufsReport.calculateTotalsColumnDataForTotals(ufsReport.getTableHeaders().get(13), recordCountOfTotals);

			// verify Report Data for Cash Flow
			ufsReport.verifyReportRecords();

			// Validations for Sales Time Details

			ufsReport.decideTimeRange((String) ufsReport.getJsonData().get(Reports.TRANS_DATE));

			// update the report date based on calculation
			String productPrice = rstProductSummaryData.get(CNProductSummary.PRICE);
			String tax = rstProductSummaryData.get(CNProductSummary.TAX);
			String discount = rstProductSummaryData.get(CNProductSummary.DISCOUNT);

			// Updating Table data
			ufsReport.TrasactionCount(ufsReport.getTableHeadersOfSalesTimeDetails().get(1));
			ufsReport.calculateAmount(ufsReport.getTableHeadersOfSalesTimeDetails().get(2), productPrice);
			ufsReport.calculateAmount(ufsReport.getTableHeadersOfSalesTimeDetails().get(3), discount);
			ufsReport.calculateAmount(ufsReport.getTableHeadersOfSalesTimeDetails().get(4), tax);
			ufsReport.saleIncludingTaxes(ufsReport.getTableHeadersOfSalesTimeDetails().get(5), productPrice, tax,
					discount);

			// Updating Footer data
			ufsReport.TrasactionCountOfFooter(ufsReport.getTableHeadersOfSalesTimeDetails().get(1));
			ufsReport.calculateAmountOfFooter(ufsReport.getTableHeadersOfSalesTimeDetails().get(2), productPrice);
			ufsReport.calculateAmountOfFooter(ufsReport.getTableHeadersOfSalesTimeDetails().get(3), discount);
			ufsReport.calculateAmountOfFooter(ufsReport.getTableHeadersOfSalesTimeDetails().get(4), tax);
			ufsReport.saleIncludingTaxesOfFooter(ufsReport.getTableHeadersOfSalesTimeDetails().get(5), productPrice,
					tax, discount);

			// verify report headers Validations for Sales Time Details
			ufsReport.verifyReportHeadersOfSalesTimeDetails(columnNames.get(1));

			// verify report dataValidations for Sales Time Details
			ufsReport.verifyReportDataOfSalesTimeDetails();

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * UFS By Employee Device Report Data Validation
	 * 
	 * @author ravindhara, Date:02/11/2022
	 */
	@Test(description = "206489- UFS By Employee Device Report data validation")
	public void UFSByEmployeeDeviceReportDataValidation() {
		final String CASE_NUM = "206489";

		// Reading Test Data from DB
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);
		rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);
		rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

		List<String> paymentType = Arrays
				.asList(rstLocationSummaryData.get(CNLocationSummary.COLUMN_VALUE).split(Constants.DELIMITER_HASH));
		List<String> columnNames = Arrays
				.asList(rstProductSummaryData.get(CNProductSummary.COLUMN_NAME).split(Constants.DELIMITER_TILD));
		String location = propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE);
		try {
			// Navigate to ADM and Select Org
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// process sales API to generate data
			ufsByEmployeeDevice.processAPI(rstProductSummaryData.get(CNProductSummary.ACTUAL_DATA),
					rstProductSummaryData.get(CNProductSummary.REQUIRED_DATA),
					rstProductSummaryData.get(CNProductSummary.DEVICE_ID),
					rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));

			// navigate To Reports
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location and run report
			ufsByEmployeeDevice.selectAndRunReport(rstReportListData.get(CNReportList.REPORT_NAME),
					rstReportListData.get(CNReportList.DATE_RANGE), location);

			// read Report Data
			ufsByEmployeeDevice.readAllRecordsFromCashFlowDetailsTable(
					rstProductSummaryData.get(CNProductSummary.DEVICE_ID), location);
			ufsByEmployeeDevice.getInitialReportsData().putAll(ufsByEmployeeDevice.reportsData);
			ufsByEmployeeDevice.getInitialReportTotals().putAll(ufsByEmployeeDevice.getReportsTotalData());

			// Read the Report the Data
			ufsByEmployeeDevice.getTblRecordsUIOfSalesTimeDetails(rstProductSummaryData.get(CNProductSummary.DEVICE_ID),
					location);
			ufsByEmployeeDevice.getIntialDataOfSalesTimeDetails()
					.putAll(ufsByEmployeeDevice.getReportsDataOfSalesTimeDetails());
			ufsByEmployeeDevice.getUpdatedTableFootersOfSalesTimeDetails()
					.putAll(ufsByEmployeeDevice.getTableFootersOfSalesTimeDetails());

			// process sales API to generate data
			ufsByEmployeeDevice.processAPI(rstProductSummaryData.get(CNProductSummary.ACTUAL_DATA),
					rstProductSummaryData.get(CNProductSummary.REQUIRED_DATA),
					rstProductSummaryData.get(CNProductSummary.DEVICE_ID),
					rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));

			// navigate To Reports
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location and run report
			ufsByEmployeeDevice.selectAndRunReport(rstReportListData.get(CNReportList.REPORT_NAME),
					rstReportListData.get(CNReportList.DATE_RANGE), location);

			// read Updated Report Data
			ufsByEmployeeDevice.readAllRecordsFromCashFlowDetailsTable(
					rstProductSummaryData.get(CNProductSummary.DEVICE_ID), location);
			ufsByEmployeeDevice.getJsonSalesData();

			ufsByEmployeeDevice.getTblRecordsUIOfSalesTimeDetails(rstProductSummaryData.get(CNProductSummary.DEVICE_ID),
					location);

			int recordCountOfCash = ufsByEmployeeDevice.getRequiredRecord(paymentType.get(0));
			int recordCountOfCreditCard = ufsByEmployeeDevice.getRequiredRecord(paymentType.get(1));
			int recordCountOfGEN3 = ufsByEmployeeDevice.getRequiredRecord(paymentType.get(2));
			int recordCountOfSOGO = ufsByEmployeeDevice.getRequiredRecord(paymentType.get(3));
			int recordCountOfComp = ufsByEmployeeDevice.getRequiredRecord(paymentType.get(4));
			int recordCountOfGuestPass = ufsByEmployeeDevice.getRequiredRecord(paymentType.get(5));
			int recordCountOfSpecial = ufsByEmployeeDevice.getRequiredRecord(paymentType.get(6));
			int recordCountOfAccount = ufsByEmployeeDevice.getRequiredRecord(paymentType.get(7));
			int recordCountOfTotals = ufsByEmployeeDevice.getRequiredRecord(paymentType.get(8));

			// verify Report Headers
			ufsByEmployeeDevice.verifyReportHeaders(columnNames.get(0));

			// calculate Credit Payment Counts
			ufsByEmployeeDevice.calculateCounts(ufsByEmployeeDevice.getTableHeaders().get(1), recordCountOfCash);

			// calculate Credit Payment Amounts
			ufsByEmployeeDevice.calculateAmounts(ufsByEmployeeDevice.getTableHeaders().get(2), recordCountOfCash);

			// calculate Credit Void Counts
			ufsByEmployeeDevice.calculateCounts(ufsByEmployeeDevice.getTableHeaders().get(3), recordCountOfCash);

			// calculate Credit Void Amounts
			ufsByEmployeeDevice.calculateAmounts(ufsByEmployeeDevice.getTableHeaders().get(4), recordCountOfCash);

			// calculate Credit Sales
			ufsByEmployeeDevice.calculateLocationSales(ufsByEmployeeDevice.getTableHeaders().get(7), recordCountOfCash);

			// calculate Credit Taxes
			ufsByEmployeeDevice.calculateLocationTax(ufsByEmployeeDevice.getTableHeaders().get(8), recordCountOfCash);

			// calculate Credit Total
			ufsByEmployeeDevice.calculateTotalsColumnData(ufsByEmployeeDevice.getTableHeaders().get(13),
					recordCountOfCash);

			// calculate Credit Payment Counts
			ufsByEmployeeDevice.calculateCounts(ufsByEmployeeDevice.getTableHeaders().get(1), recordCountOfCreditCard);

			// calculate Credit Payment Amounts
			ufsByEmployeeDevice.calculateAmounts(ufsByEmployeeDevice.getTableHeaders().get(2), recordCountOfCreditCard);

			// calculate Credit Void Counts
			ufsByEmployeeDevice.calculateCounts(ufsByEmployeeDevice.getTableHeaders().get(3), recordCountOfCreditCard);

			// calculate Credit Void Amounts
			ufsByEmployeeDevice.calculateAmounts(ufsByEmployeeDevice.getTableHeaders().get(4), recordCountOfCreditCard);

			// calculate Credit Declined Counts
			ufsByEmployeeDevice.calculateCounts(ufsByEmployeeDevice.getTableHeaders().get(5), recordCountOfCreditCard);

			// calculate Credit Declined Amounts
			ufsByEmployeeDevice.calculateAmounts(ufsByEmployeeDevice.getTableHeaders().get(6), recordCountOfCreditCard);

			// calculate Credit Sales
			ufsByEmployeeDevice.calculateLocationSales(ufsByEmployeeDevice.getTableHeaders().get(7),
					recordCountOfCreditCard);

			// calculate Credit Taxes
			ufsByEmployeeDevice.calculateLocationTax(ufsByEmployeeDevice.getTableHeaders().get(8),
					recordCountOfCreditCard);

			// calculate Credit Total
			ufsByEmployeeDevice.calculateTotalsColumnData(ufsByEmployeeDevice.getTableHeaders().get(13),
					recordCountOfCreditCard);

			// calculate gen3 Payment Counts
			ufsByEmployeeDevice.calculateCounts(ufsByEmployeeDevice.getTableHeaders().get(1), recordCountOfGEN3);

			// calculate gen3 Payment Amounts
			ufsByEmployeeDevice.calculateAmounts(ufsByEmployeeDevice.getTableHeaders().get(2), recordCountOfGEN3);

			// calculate gen3 Void Counts
			ufsByEmployeeDevice.calculateCounts(ufsByEmployeeDevice.getTableHeaders().get(3), recordCountOfGEN3);

			// calculate gen3 Void Amounts
			ufsByEmployeeDevice.calculateAmounts(ufsByEmployeeDevice.getTableHeaders().get(4), recordCountOfGEN3);

			// calculate gen3 Declined Counts
			ufsByEmployeeDevice.calculateCounts(ufsByEmployeeDevice.getTableHeaders().get(5), recordCountOfGEN3);

			// calculate gen3 Declined Amounts
			ufsByEmployeeDevice.calculateAmounts(ufsByEmployeeDevice.getTableHeaders().get(6), recordCountOfGEN3);

			// calculate gen3 Sales
			ufsByEmployeeDevice.calculateLocationSales(ufsByEmployeeDevice.getTableHeaders().get(7), recordCountOfGEN3);

			// calculate gen3 Taxes
			ufsByEmployeeDevice.calculateLocationTax(ufsByEmployeeDevice.getTableHeaders().get(8), recordCountOfGEN3);

			// calculate gen3 Total
			ufsByEmployeeDevice.calculateTotalsColumnData(ufsByEmployeeDevice.getTableHeaders().get(13),
					recordCountOfGEN3);

			// calculate SOGO Payment Counts
			ufsByEmployeeDevice.calculateCounts(ufsByEmployeeDevice.getTableHeaders().get(1), recordCountOfSOGO);

			// calculate SOGO Payment Amounts
			ufsByEmployeeDevice.calculateAmounts(ufsByEmployeeDevice.getTableHeaders().get(2), recordCountOfSOGO);

			// calculate SOGO Void Counts
			ufsByEmployeeDevice.calculateCounts(ufsByEmployeeDevice.getTableHeaders().get(3), recordCountOfSOGO);

			// calculate SOGO Void Amounts
			ufsByEmployeeDevice.calculateAmounts(ufsByEmployeeDevice.getTableHeaders().get(4), recordCountOfSOGO);

			// calculate SOGO Sales
			ufsByEmployeeDevice.calculateLocationSales(ufsByEmployeeDevice.getTableHeaders().get(7), recordCountOfSOGO);

			// calculate SOGO Taxes
			ufsByEmployeeDevice.calculateLocationTax(ufsByEmployeeDevice.getTableHeaders().get(8), recordCountOfSOGO);

			// calculate SOGO Total
			ufsByEmployeeDevice.calculateTotalsColumnData(ufsByEmployeeDevice.getTableHeaders().get(13),
					recordCountOfSOGO);

			// calculate Comp Payment Counts
			ufsByEmployeeDevice.calculateCounts(ufsByEmployeeDevice.getTableHeaders().get(1), recordCountOfComp);

			// calculate Comp Payment Amounts
			ufsByEmployeeDevice.calculateAmounts(ufsByEmployeeDevice.getTableHeaders().get(2), recordCountOfComp);

			// calculate Comp Void Counts
			ufsByEmployeeDevice.calculateCounts(ufsByEmployeeDevice.getTableHeaders().get(3), recordCountOfComp);

			// calculate Comp Void Amounts
			ufsByEmployeeDevice.calculateAmounts(ufsByEmployeeDevice.getTableHeaders().get(4), recordCountOfComp);

			// calculate Comp Sales
			ufsByEmployeeDevice.calculateLocationSales(ufsByEmployeeDevice.getTableHeaders().get(7), recordCountOfComp);

			// calculate Comp Taxes
			ufsByEmployeeDevice.calculateLocationTax(ufsByEmployeeDevice.getTableHeaders().get(8), recordCountOfComp);

			// calculate Comp Total
			ufsByEmployeeDevice.calculateTotalsColumnData(ufsByEmployeeDevice.getTableHeaders().get(13),
					recordCountOfComp);

			// calculate GuestPass Payment Counts
			ufsByEmployeeDevice.calculateCounts(ufsByEmployeeDevice.getTableHeaders().get(1), recordCountOfGuestPass);

			// calculate GuestPass Payment Amounts
			ufsByEmployeeDevice.calculateAmounts(ufsByEmployeeDevice.getTableHeaders().get(2), recordCountOfGuestPass);

			// calculate GuestPass Void Counts
			ufsByEmployeeDevice.calculateCounts(ufsByEmployeeDevice.getTableHeaders().get(3), recordCountOfGuestPass);

			// calculate GuestPass Void Amounts
			ufsByEmployeeDevice.calculateAmounts(ufsByEmployeeDevice.getTableHeaders().get(4), recordCountOfGuestPass);

			// calculate GuestPass Sales
			ufsByEmployeeDevice.calculateLocationSales(ufsByEmployeeDevice.getTableHeaders().get(7),
					recordCountOfGuestPass);

			// calculate GuestPass Taxes
			ufsByEmployeeDevice.calculateLocationTax(ufsByEmployeeDevice.getTableHeaders().get(8),
					recordCountOfGuestPass);

			// calculate GuestPass Total
			ufsByEmployeeDevice.calculateTotalsColumnData(ufsByEmployeeDevice.getTableHeaders().get(13),
					recordCountOfGuestPass);

			// calculate Special Payment Counts
			ufsByEmployeeDevice.calculateCounts(ufsByEmployeeDevice.getTableHeaders().get(1), recordCountOfSpecial);

			// calculate Special Payment Amounts
			ufsByEmployeeDevice.calculateAmounts(ufsByEmployeeDevice.getTableHeaders().get(2), recordCountOfSpecial);

			// calculate Special Void Counts
			ufsByEmployeeDevice.calculateCounts(ufsByEmployeeDevice.getTableHeaders().get(3), recordCountOfSpecial);

			// calculate Special Void Amounts
			ufsByEmployeeDevice.calculateAmounts(ufsByEmployeeDevice.getTableHeaders().get(4), recordCountOfSpecial);

			// calculate Special Sales
			ufsByEmployeeDevice.calculateLocationSales(ufsByEmployeeDevice.getTableHeaders().get(7),
					recordCountOfSpecial);

			// calculate Special Taxes
			ufsByEmployeeDevice.calculateLocationTax(ufsByEmployeeDevice.getTableHeaders().get(8),
					recordCountOfSpecial);

			// calculate Special Total
			ufsByEmployeeDevice.calculateTotalsColumnData(ufsByEmployeeDevice.getTableHeaders().get(13),
					recordCountOfSpecial);

			// calculate Account Payment Counts
			ufsByEmployeeDevice.calculateCounts(ufsByEmployeeDevice.getTableHeaders().get(1), recordCountOfAccount);

			// calculate Account Payment Amounts
			ufsByEmployeeDevice.calculateAmounts(ufsByEmployeeDevice.getTableHeaders().get(2), recordCountOfAccount);

			// calculate Account Void Counts
			ufsByEmployeeDevice.calculateCounts(ufsByEmployeeDevice.getTableHeaders().get(3), recordCountOfAccount);

			// calculate Account Void Amounts
			ufsByEmployeeDevice.calculateAmounts(ufsByEmployeeDevice.getTableHeaders().get(4), recordCountOfAccount);

			// calculate Account Sales
			ufsByEmployeeDevice.calculateLocationSales(ufsByEmployeeDevice.getTableHeaders().get(7),
					recordCountOfAccount);

			// calculate Account Taxes
			ufsByEmployeeDevice.calculateLocationTax(ufsByEmployeeDevice.getTableHeaders().get(8),
					recordCountOfAccount);

			// calculate Account Total
			ufsByEmployeeDevice.calculateTotalsColumnData(ufsByEmployeeDevice.getTableHeaders().get(13),
					recordCountOfAccount);

			// calculate Totals Payment Counts
			ufsByEmployeeDevice.calculateCountsForTotals(ufsByEmployeeDevice.getTableHeaders().get(1),
					recordCountOfTotals);

			// calculate Totals Payment Amounts
			ufsByEmployeeDevice.calculateAmountsForTotals(ufsByEmployeeDevice.getTableHeaders().get(2),
					recordCountOfTotals);

			// calculate Totals Void Counts
			ufsByEmployeeDevice.calculateCountsForTotals(ufsByEmployeeDevice.getTableHeaders().get(3),
					recordCountOfTotals);

			// calculate Totals Void Amounts
			ufsByEmployeeDevice.calculateAmountsForTotals(ufsByEmployeeDevice.getTableHeaders().get(4),
					recordCountOfTotals);

			// calculate Totals Declined Counts
			ufsByEmployeeDevice.calculateCounts(ufsByEmployeeDevice.getTableHeaders().get(5), recordCountOfTotals);

			// calculate Totals Declined Amounts
			ufsByEmployeeDevice.calculateAmounts(ufsByEmployeeDevice.getTableHeaders().get(6), recordCountOfTotals);

			// calculate Totals Sales
			ufsByEmployeeDevice.calculateLocationSalesForTotals(ufsByEmployeeDevice.getTableHeaders().get(7),
					recordCountOfTotals);

			// calculate Totals Taxes
			ufsByEmployeeDevice.calculateLocationTaxForTotals(ufsByEmployeeDevice.getTableHeaders().get(8),
					recordCountOfTotals);

			// calculate Totals Total
			ufsByEmployeeDevice.calculateTotalsColumnDataForTotals(ufsByEmployeeDevice.getTableHeaders().get(13),
					recordCountOfTotals);

			// verify Report Data for Cash Flow
			ufsByEmployeeDevice.verifyReportRecords();

			// Validations for Sales Time Details

			ufsByEmployeeDevice.decideTimeRange((String) ufsByEmployeeDevice.getJsonData().get(Reports.TRANS_DATE));

			// update the report date based on calculation
			String productPrice = rstProductSummaryData.get(CNProductSummary.PRICE);
			String tax = rstProductSummaryData.get(CNProductSummary.TAX);
			String discount = rstProductSummaryData.get(CNProductSummary.DISCOUNT);

			// Updating Table data
			ufsByEmployeeDevice.TrasactionCount(ufsByEmployeeDevice.getTableHeadersOfSalesTimeDetails().get(1));
			ufsByEmployeeDevice.calculateAmount(ufsByEmployeeDevice.getTableHeadersOfSalesTimeDetails().get(2),
					productPrice);
			ufsByEmployeeDevice.calculateAmount(ufsByEmployeeDevice.getTableHeadersOfSalesTimeDetails().get(3),
					discount);
			ufsByEmployeeDevice.calculateAmount(ufsByEmployeeDevice.getTableHeadersOfSalesTimeDetails().get(4), tax);
			ufsByEmployeeDevice.saleIncludingTaxes(ufsByEmployeeDevice.getTableHeadersOfSalesTimeDetails().get(5),
					productPrice, tax, discount);

			// Updating Footer data
			ufsByEmployeeDevice.TrasactionCountOfFooter(ufsByEmployeeDevice.getTableHeadersOfSalesTimeDetails().get(1));
			ufsByEmployeeDevice.calculateAmountOfFooter(ufsByEmployeeDevice.getTableHeadersOfSalesTimeDetails().get(2),
					productPrice);
			ufsByEmployeeDevice.calculateAmountOfFooter(ufsByEmployeeDevice.getTableHeadersOfSalesTimeDetails().get(3),
					discount);
			ufsByEmployeeDevice.calculateAmountOfFooter(ufsByEmployeeDevice.getTableHeadersOfSalesTimeDetails().get(4),
					tax);
			ufsByEmployeeDevice.saleIncludingTaxesOfFooter(
					ufsByEmployeeDevice.getTableHeadersOfSalesTimeDetails().get(5), productPrice, tax, discount);

			// verify report headers Validations for Sales Time Details
			ufsByEmployeeDevice.verifyReportHeadersOfSalesTimeDetails(columnNames.get(1));

			// verify report dataValidations for Sales Time Details
			ufsByEmployeeDevice.verifyReportDataOfSalesTimeDetails();

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
}