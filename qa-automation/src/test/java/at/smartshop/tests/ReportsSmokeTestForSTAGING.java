package at.smartshop.tests;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import at.framework.database.mssql.Queries;
import at.framework.database.mssql.ResultSets;
import at.framework.ui.Foundation;
import at.smartshop.database.columns.CNNavigationMenu;
import at.smartshop.database.columns.CNReportList;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.pages.AVISubFeeReport;
import at.smartshop.pages.AccountAdjustment;
import at.smartshop.pages.AccountFunding;
import at.smartshop.pages.AccountFundingDetail;
import at.smartshop.pages.AccountProfitability;
import at.smartshop.pages.AlcoholSoldDetailsReport;
import at.smartshop.pages.BadScanReport;
import at.smartshop.pages.BalanceReport;
import at.smartshop.pages.BillingInformationReport;
import at.smartshop.pages.CanadaMultiTaxReport;
import at.smartshop.pages.CancelReport;
import at.smartshop.pages.CashAudit;
import at.smartshop.pages.CashFlow;
import at.smartshop.pages.CashFlowDetails;
import at.smartshop.pages.CashFlowDetailsInternational;
import at.smartshop.pages.CashFlowDevice;
import at.smartshop.pages.CashFlowEmployee;
import at.smartshop.pages.CashFlowEmployeeDevice;
import at.smartshop.pages.CashoutLog;
import at.smartshop.pages.ConsumerFeedbackSurvey;
import at.smartshop.pages.ConsumerPurchaseSummary;
import at.smartshop.pages.CreditTransaction;
import at.smartshop.pages.CrossOrgGoLive;
import at.smartshop.pages.CrossOrgLoyaltyReport;
import at.smartshop.pages.CrossOrgRateReport;
import at.smartshop.pages.DailySalesSummary;
import at.smartshop.pages.DataSourceManager;
import at.smartshop.pages.DeleteSummaryReport;
import at.smartshop.pages.DeviceByCategoryReport;
import at.smartshop.pages.EntrySummaryReport;
import at.smartshop.pages.ExpressDisbursement;
import at.smartshop.pages.FinancialCanned;
import at.smartshop.pages.FinancialRecapReport;
import at.smartshop.pages.FolioBillingReport;
import at.smartshop.pages.ICEReport;
import at.smartshop.pages.IntegrationPaymentReport;
import at.smartshop.pages.InventoryAdjustmentDetail;
import at.smartshop.pages.InventoryList;
import at.smartshop.pages.InventoryTotals;
import at.smartshop.pages.InventoryValueSummary;
import at.smartshop.pages.InventoryVariance;
import at.smartshop.pages.NavigationBar;
import at.smartshop.pages.ReportList;
import at.smartshop.pages.UFSReport;
import at.smartshop.pages.UFSByDevice;
import at.smartshop.pages.UFSByEmployeeDevice;

@Listeners(at.framework.reportsetup.Listeners.class)
public class ReportsSmokeTestForSTAGING extends TestInfra {

	private ResultSets dataBase = new ResultSets();
	private ReportList reportList = new ReportList();
	private Foundation foundation = new Foundation();
	private NavigationBar navigationBar = new NavigationBar();
	private DataSourceManager dataSourceManager = new DataSourceManager();
	private AVISubFeeReport AVISubFeeReport = new AVISubFeeReport();
	private UFSReport ufsReport = new UFSReport();
	private UFSByDevice ufsByDevice = new UFSByDevice();
	private UFSByEmployeeDevice ufsByEmployeeDevice = new UFSByEmployeeDevice();
	private AccountAdjustment accountAdjustment = new AccountAdjustment();
	private AccountFunding accountFunding = new AccountFunding();
	private AccountFundingDetail accountFundingDetail = new AccountFundingDetail();
	private AccountProfitability accountProfitability = new AccountProfitability();
	private BalanceReport balanceReport = new BalanceReport();
	private CanadaMultiTaxReport canadaMultiTaxReport = new CanadaMultiTaxReport();
	private CancelReport cancelReport = new CancelReport();
	private CashAudit cashAudit = new CashAudit();
	private CashFlow cashFlow = new CashFlow();
	private CashFlowDetailsInternational cashFlowDetailsInternational = new CashFlowDetailsInternational();
	private AlcoholSoldDetailsReport alcoholSoldDetailsReport = new AlcoholSoldDetailsReport();
	private BadScanReport badScanReport = new BadScanReport();
	private BillingInformationReport billingInformationReport = new BillingInformationReport();
	private CashFlowDevice cashFlowDevice = new CashFlowDevice();
	private CashFlowEmployee cashFlowEmployee = new CashFlowEmployee();
	private CashFlowEmployeeDevice cashFlowEmployeeDevice = new CashFlowEmployeeDevice();
	private CashFlowDetails cashFlowDetails = new CashFlowDetails();
	private CashoutLog cashoutLog = new CashoutLog();
	private ConsumerFeedbackSurvey consumerFeedbackSurvey = new ConsumerFeedbackSurvey();
	private ConsumerPurchaseSummary consumerPurchaseSummary = new ConsumerPurchaseSummary();
	private CreditTransaction creditTransaction = new CreditTransaction();
	private CrossOrgGoLive crossOrgGoLive = new CrossOrgGoLive();
	private CrossOrgLoyaltyReport crossOrgLoyaltyReport = new CrossOrgLoyaltyReport();
	private CrossOrgRateReport crossOrgRateReport = new CrossOrgRateReport();
	private DailySalesSummary dailySalesSummary = new DailySalesSummary();
	private DeleteSummaryReport deleteSummaryReport = new DeleteSummaryReport();
	private DeviceByCategoryReport deviceByCategoryReport = new DeviceByCategoryReport();
	private EntrySummaryReport entrySummaryReport = new EntrySummaryReport();
	private ExpressDisbursement expressDisbursement = new ExpressDisbursement();
	private FinancialCanned financialCanned = new FinancialCanned();
	private FinancialRecapReport financialRecapReport = new FinancialRecapReport();
	private FolioBillingReport folioBillingReport = new FolioBillingReport();
	private ICEReport iceReport = new ICEReport();
	private IntegrationPaymentReport integrationPaymentReport = new IntegrationPaymentReport();
	private InventoryAdjustmentDetail inventoryAdjustmentDetail = new InventoryAdjustmentDetail();
	private InventoryList inventoryList = new InventoryList();
	private InventoryVariance inventoryVariance = new InventoryVariance();
	private InventoryTotals inventoryTotals = new InventoryTotals();
	private InventoryValueSummary inventoryValueSummary = new InventoryValueSummary();


	private Map<String, String> rstNavigationMenuData;
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

	// Nov 10 in 365Support_US
	@Test(description = "208976- This test validates Data existance and Excel file exportaion of Cross Org: AVI Sub Fee Reportin Staging Environment")
	public void AVISubFeeInStaging() {
		try {
			final String CASE_NUM = "208976";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			// Select Organization
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.STAGING_STEAVE_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Navigate to Reports
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectCurrentDay();
			reportList.selectOrg(
					propertyFile.readPropertyFile(Configuration.STAGING_STEAVE_ORG, FilePath.PROPERTY_CONFIG_FILE));
			foundation.objectClick(ReportList.BTN_RUN_REPORT);

			// Verifying the Report name with with the displayed name on the Front end
			AVISubFeeReport.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));

			// Downloading the Report
			reportList.clickOnToExcelButton(reportList.TO_EXCEL_BUTTON);

			foundation.threadWait(Constants.SHORT_TIME);

			// Verifying the Report name with with the Name in the exported file,
			// Verified file existence and deleted the file.
			reportList.verifyTheFileWithFullName(rstReportListData.get(CNReportList.REPORT_NAME),
					foundation.getText(AVISubFeeReport.LBL_REPORT_NAME));

			// Verifying, whether the Report data available or not
			AVISubFeeReport.checkForDataAvailabilyInResultTable();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "209027- This test validates Data existance and Excel file exportaion of UFS By Device Report")
	public void UFSByDevice_Staging() {
		try {
			final String CASE_NUM = "209027";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			// Select Organization
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.STAGING_STEAVE_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Navigate to Reports
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectDateRangeOfSinglrDateofType3(UFSByDevice.MONTH_OF_EXISTING_DATA,
					UFSByDevice.DATE_OF_EXISTING_DATA);
			reportList.selectLocation(propertyFile.readPropertyFile(Configuration.STAGING_STEAVE_LOCATION,
					FilePath.PROPERTY_CONFIG_FILE));
			foundation.objectClick(ReportList.BTN_RUN_REPORT);

			// Verifying the Report name with with the displayed name on the Front end
			ufsByDevice.verifyReportName(propertyFile.readPropertyFile(Configuration.STAGING_STEAVE_LOCATION,
					FilePath.PROPERTY_CONFIG_FILE));

			foundation.threadWait(Constants.SHORT_TIME);
			// Downloading the Report
			reportList.clickOnToExcelButton(reportList.TO_EXCEL_BUTTON);

			foundation.threadWait(Constants.SHORT_TIME);

			// Verifying the Report name with with the Name in the exported file,
			// Verified file existence and deleted the file.
			reportList.verifyTheFileWithFullName(
					propertyFile.readPropertyFile(Configuration.STAGING_STEAVE_LOCATION, FilePath.PROPERTY_CONFIG_FILE),
					rstReportListData.get(CNReportList.DOWNLOADED_FILE_NAME));

			// Verifying, whether the Report data available or not
			ufsByDevice.checkForDataAvailabilyInResultTable();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "209028- This test validates Data existance and Excel file exportaion of UFS By Employee Device Report")
	public void UFSByEmployeeDevice_Staging() {
		try {
			final String CASE_NUM = "209028";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			// Select Organization
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.STAGING_STEAVE_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Navigate to Reports
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectDateRangeOfSinglrDateofType3(UFSByDevice.MONTH_OF_EXISTING_DATA,
					UFSByDevice.DATE_OF_EXISTING_DATA);
			reportList.selectLocation(propertyFile.readPropertyFile(Configuration.STAGING_STEAVE_LOCATION,
					FilePath.PROPERTY_CONFIG_FILE));
			foundation.objectClick(ReportList.BTN_RUN_REPORT);
			foundation.threadWait(Constants.SHORT_TIME);

			// Verifying the Report name with with the displayed name on the Front end
			ufsByEmployeeDevice.verifyReportName(propertyFile.readPropertyFile(Configuration.STAGING_STEAVE_LOCATION,
					FilePath.PROPERTY_CONFIG_FILE));

			foundation.threadWait(Constants.SHORT_TIME);
			// Downloading the Report
			reportList.clickOnToExcelButton(reportList.TO_EXCEL_BUTTON);

			foundation.threadWait(Constants.MEDIUM_TIME);

			// Verifying the Report name with with the Name in the exported file,
			// Verified file existence and deleted the file.
			reportList.verifyTheFileWithFullName(
					propertyFile.readPropertyFile(Configuration.STAGING_STEAVE_LOCATION, FilePath.PROPERTY_CONFIG_FILE),
					rstReportListData.get(CNReportList.DOWNLOADED_FILE_NAME));

			// Verifying, whether the Report data available or not
			ufsByEmployeeDevice.checkForDataAvailabilyInResultTable();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "209029- This test validates Data existance and Excel file exportaion of UFS Report")
	public void UFSReport_Staging() {
		try {
			final String CASE_NUM = "209029";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			// Select Organization
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.STAGING_STEAVE_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Navigate to Reports
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
//			reportList.selectPriorMonthDate(rstReportListData.get(CNReportList.DATE_RANGE));

			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));
			reportList.selectLocation(propertyFile.readPropertyFile(Configuration.STAGING_STEAVE_LOCATION,
					FilePath.PROPERTY_CONFIG_FILE));
			foundation.threadWait(Constants.ONE_SECOND);
			foundation.objectClick(ReportList.BTN_RUN_REPORT);

			// Verifying the Report name with with the displayed name on the Front end
			ufsReport.verifyReportName(propertyFile.readPropertyFile(Configuration.STAGING_STEAVE_LOCATION,
					FilePath.PROPERTY_CONFIG_FILE));

			foundation.threadWait(Constants.SHORT_TIME);
			// Downloading the Report
			reportList.clickOnToExcelButton(reportList.TO_EXCEL_BUTTON);

			foundation.threadWait(Constants.MEDIUM_TIME);

			// Verifying the Report name with with the Name in the exported file,
			// Verified file existence and deleted the file.
			reportList.verifyTheFileWithFullName(
					propertyFile.readPropertyFile(Configuration.STAGING_STEAVE_LOCATION, FilePath.PROPERTY_CONFIG_FILE),
					rstReportListData.get(CNReportList.DOWNLOADED_FILE_NAME));

			// Verifying, whether the Report data available or not
			ufsReport.checkForDataAvailabilyInResultTable();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "208977- This test validates Data existance and Excel file exportaion of Account Adjustment Report in Staging Environment")
	public void accountAdjustmentReport_Staging() {
		try {
			final String CASE_NUM = "208977";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.STAGING_STEAVE_ORG, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));
			reportList.selectLocation(
					propertyFile.readPropertyFile(Configuration.ALL_LOCATIONS, FilePath.PROPERTY_CONFIG_FILE));
			foundation.objectClick(ReportList.BTN_RUN_REPORT);

			// Verifying the Report name with with the displayed name on the Front end
			accountAdjustment.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));

			// Downloading the Report
			reportList.clickOnToExcelButton(reportList.TO_EXCEL_BUTTON);

			foundation.threadWait(Constants.SHORT_TIME);

			// Verifying the Report name with with the Name in the exported file,
			// Verified file existence and deleted the file.
			reportList.verifyTheFileWithFullName(rstReportListData.get(CNReportList.REPORT_NAME),
					rstReportListData.get(CNReportList.DOWNLOADED_FILE_NAME));

			// Verifying, whether the Report data available or not
			accountAdjustment.checkForDataAvailabilyInResultTable();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	// Has the data for November 1-10 and 10-21 but loading slow in 365Support and
	// Steve
	@Test(description = "208978- This test validates Data existance and Excel file exportaion of Voided Product Report in Staging Environment")
	public void accountFunding_Staging_Staging() {
		try {
			final String CASE_NUM = "208978";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			// Select Organization
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.STAGING_STEAVE_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Navigate to Reports
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));
			reportList.selectLocation(
					propertyFile.readPropertyFile(Configuration.ALL_LOCATIONS, FilePath.PROPERTY_CONFIG_FILE));
			foundation.objectClick(ReportList.BTN_RUN_REPORT);

			// Verifying the Report name with with the displayed name on the Front end
			accountFunding.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));

			// Downloading the Report
			reportList.clickOnToExcelButton(reportList.TO_EXCEL_BUTTON);

			foundation.threadWait(Constants.MEDIUM_TIME);

			// Verifying the Report name with with the Name in the exported file,
			// verified file existence and deleted the file
			reportList.verifyTheFileWithFullName(rstReportListData.get(CNReportList.REPORT_NAME),
					rstReportListData.get(CNReportList.DOWNLOADED_FILE_NAME));

			// Verifying, whether the Report data available or not
			accountFunding.checkForDataAvailabilyInResultTable();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "209013- This test validates Data existance and Excel file exportaion of Account Funding Detail Report in Staging Environment")
	public void accountFundingDetail_Staging() {
		try {
			final String CASE_NUM = "209013";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			// Select Organization
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.STAGING_STEAVE_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Navigate to Reports
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));
			reportList.selectLocation(propertyFile.readPropertyFile(Configuration.STAGING_STEAVE_LOCATION,
					FilePath.PROPERTY_CONFIG_FILE));
			foundation.objectClick(ReportList.BTN_RUN_REPORT);

			// Verifying the Report name with with the displayed name on the Front end
			accountFundingDetail.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));

			// Downloading the Report
			reportList.clickOnToExcelButton(reportList.TO_EXCEL_BUTTON);

			foundation.threadWait(Constants.MEDIUM_TIME);

			// Verifying the Report name with with the Name in the exported file,
			// Verified file existence and deleted the file.
			reportList.verifyTheFileWithFullName(rstReportListData.get(CNReportList.REPORT_NAME),
					rstReportListData.get(CNReportList.DOWNLOADED_FILE_NAME));

			// Verifying, whether the Report data available or not
			accountFundingDetail.checkForDataAvailabilyInResultTable();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "209109- This test validates Data existance and Excel file exportaion of Account Profitability Report in Staging Environment")
	public void accountProfitability_Staging() {
		try {
			final String CASE_NUM = "209109";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			// Select Organization
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.STAGING_STEAVE_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Navigate to Reports
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));
			reportList.selectLocation(
					propertyFile.readPropertyFile(Configuration.ALL_LOCATIONS, FilePath.PROPERTY_CONFIG_FILE));
			foundation.objectClick(ReportList.BTN_RUN_REPORT);

			// Verifying the Report name with with the displayed name on the Front end
			accountProfitability.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));

			// Downloading the Report
			reportList.clickOnToExcelButton(reportList.TO_EXCEL_BUTTON);

			foundation.threadWait(Constants.SHORT_TIME);

			// Verifying the Report name with with the Name in the exported file,
			// verified file existence and deleted the file
			reportList.verifyTheFileWithFullName(rstReportListData.get(CNReportList.REPORT_NAME),
					rstReportListData.get(CNReportList.DOWNLOADED_FILE_NAME));

			// Verifying, whether the Report data available or not
			accountProfitability.checkForDataAvailabilyInResultTable();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "209110- This test validates Data existance and Excel file exportaion of Balance Report in Staging Environment")
	public void balanceReport_Staging() {
		try {
			final String CASE_NUM = "209110";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			// Select Organization
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.STAGING_STEAVE_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Navigate to Reports
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectLocation(propertyFile.readPropertyFile(Configuration.STAGING_STEAVE_LOCATION,
					FilePath.PROPERTY_CONFIG_FILE));
			foundation.objectClick(ReportList.BTN_RUN_REPORT);

			// Verifying the Report name with with the displayed name on the Front end
			balanceReport.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));

			foundation.threadWait(Constants.SHORT_TIME);
			// Downloading the Report
			reportList.clickOnToExcelButton(reportList.TO_EXCEL_BUTTON);

			foundation.threadWait(Constants.MEDIUM_TIME);

			// Verifying the Report name with with the Name in the exported file,
			// Verified file existence and deleted the file.
			reportList.verifyTheFileWithFullName(rstReportListData.get(CNReportList.REPORT_NAME),
					rstReportListData.get(CNReportList.DOWNLOADED_FILE_NAME));

			// Verifying, whether the Report data available or not
			balanceReport.checkForDataAvailabilyInResultTable();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	// Has data for November month
	@Test(description = "209111- This test validates Data existance and Excel file exportaion of Canada Multitax Report in Staging Environment")
	public void canadaMultitax_Staging() {
		try {
			final String CASE_NUM = "209111";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			// Select Organization
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.STAGING_STEAVE_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Navigate to Reports
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location
			reportList.selectReport(CanadaMultiTaxReport.REPORT_NAME_FOR_STAGING);
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));
			reportList.selectLocation(
					propertyFile.readPropertyFile(Configuration.ALL_LOCATIONS, FilePath.PROPERTY_CONFIG_FILE));
			foundation.objectClick(ReportList.BTN_RUN_REPORT);

			// Verifying the Report name with with the displayed name on the Front end
			canadaMultiTaxReport.verifyReportName(CanadaMultiTaxReport.REPORT_NAME_FOR_STAGING);

			// Downloading the Report
			reportList.clickOnToExcelButton(reportList.TO_EXCEL_BUTTON);

			foundation.threadWait(Constants.SHORT_TIME);

			// Verifying the Report name with with the Name in the exported file,
			// Verified file existence and deleted the file.
			reportList.verifyTheFileWithFullName(CanadaMultiTaxReport.REPORT_NAME_FOR_STAGING,
					rstReportListData.get(CNReportList.DOWNLOADED_FILE_NAME));

			// Verifying, whether the Report data available or not
			canadaMultiTaxReport.checkForDataAvailabilyInResultTable();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "209112- This test validates Data existance and Excel file exportaion of Cancel Report Report in Staging Environment")
	public void cancelReport_Staging() {
		try {
			final String CASE_NUM = "209112";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			// Select Organization
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.STAGING_STEAVE_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Navigate to Reports
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));
			reportList.selectLocation(
					propertyFile.readPropertyFile(Configuration.ALL_LOCATIONS, FilePath.PROPERTY_CONFIG_FILE));
			foundation.objectClick(ReportList.BTN_RUN_REPORT);

			// Verifying the Report name with with the displayed name on the Front end
			cancelReport.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));

			// Downloading the Report
			reportList.clickOnToExcelButton(reportList.TO_EXCEL_BUTTON);

			foundation.threadWait(Constants.SHORT_TIME);

			// Verifying the Report name with with the Name in the exported file,
			// Verified file existence and deleted the file.
			reportList.verifyTheFileWithFullName(rstReportListData.get(CNReportList.REPORT_NAME),
					rstReportListData.get(CNReportList.DOWNLOADED_FILE_NAME));

			// Verifying, whether the Report data available or not
			cancelReport.checkForDataAvailabilyInResultTable();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "209113- This test validates Data existance and Excel file exportaion of Cash Audit Report in Staging Environment")
	public void cashAudit_Staging() {
		try {
			final String CASE_NUM = "209113";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			// Select Organization
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.STAGING_STEAVE_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Navigate to Reports
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));

			reportList.selectDate("Last 60 Days");

//			reportList.selectDateRangeDate(rstReportListData.get(CNReportList.DATE_RANGE),
//					rstReportListData.get(CNReportList.END_MONTH), CashAudit.DATA_EXISTING_DATE,
//					CashAudit.DATA_EXISTING_DATE);

			foundation.threadWait(Constants.TWO_SECOND);
			reportList.selectLocationForSecondTypeDropdown(propertyFile
					.readPropertyFile(Configuration.STAGING_STEAVE_LOCATION, FilePath.PROPERTY_CONFIG_FILE));
			foundation.objectClick(ReportList.BTN_RUN_REPORT);

			// Verifying the Report name with with the displayed name on the Front end
			cashAudit.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));

			foundation.threadWait(Constants.TWO_SECOND);
			// Downloading the Report
			reportList.clickOnToExcelButton(reportList.TO_EXCEL_BUTTON);

			foundation.threadWait(Constants.SHORT_TIME);

			// Verifying the Report name with with the Name in the exported file,
			// Verified file existence and deleted the file.
			reportList.verifyTheFileWithFullName(rstReportListData.get(CNReportList.REPORT_NAME),
					rstReportListData.get(CNReportList.DOWNLOADED_FILE_NAME));

			// Verifying, whether the Report data available or not
			cashAudit.checkForDataAvailabilyInResultTable();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "209114- This test validates Data existance and Excel file exportaion of Cash Flow Report in Staging Environment")
	public void cashFlow_Staging() {
		try {
			final String CASE_NUM = "209114";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			// Select Organization
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.STAGING_SUPPORT_US_365, FilePath.PROPERTY_CONFIG_FILE));

			List<String> DateRange_Existing = Arrays
					.asList(rstReportListData.get(CNReportList.DATE_RANGE).split(Constants.DELIMITER_TILD));

			// Navigate to Reports
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));

			reportList.selectDateRangeDate(DateRange_Existing.get(0), DateRange_Existing.get(1),
					CashFlowEmployeeDevice.DATA_EXISTING_START_DATE_STAGING,
					CashFlowEmployeeDevice.DATA_EXISTING_END_DATE_STAGING);
//			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));
			reportList.selectLocation(propertyFile.readPropertyFile(Configuration.STAGING_SUPPORT_US_365_LOCATION,
					FilePath.PROPERTY_CONFIG_FILE));
			foundation.objectClick(ReportList.BTN_RUN_REPORT);

			// Verifying the Report name with with the displayed name on the Front end
			cashFlow.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));

			// Downloading the Report
			reportList.clickOnToExcelButton(reportList.TO_EXCEL_BUTTON);

			foundation.threadWait(Constants.SHORT_TIME);

			// Verifying the Report name with with the Name in the exported file,
			// Verified file existence and deleted the file.
			reportList.verifyTheFileWithFullName(rstReportListData.get(CNReportList.REPORT_NAME),
					rstReportListData.get(CNReportList.DOWNLOADED_FILE_NAME));

			// Verifying, whether the Report data available or not
			cashFlow.checkForDataAvailabilyInResultTable();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "209115- This test validates Data existance and Excel file exportaion of Cash Flow Details International Report in Staging Environment")
	public void cashFlowDetailsInternational_Staging() {
		try {
			final String CASE_NUM = "209115";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			// Select Organization
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.STAGING_SUPPORT_US_365, FilePath.PROPERTY_CONFIG_FILE));

			List<String> DateRange_Existing = Arrays
					.asList(rstReportListData.get(CNReportList.DATE_RANGE).split(Constants.DELIMITER_TILD));

			// Navigate to Reports
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
//			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));
			reportList.selectDateRangeDate(DateRange_Existing.get(0), DateRange_Existing.get(1),
					CashFlowEmployeeDevice.DATA_EXISTING_START_DATE_STAGING,
					CashFlowEmployeeDevice.DATA_EXISTING_END_DATE_STAGING);

			reportList.selectLocation(propertyFile.readPropertyFile(Configuration.STAGING_SUPPORT_US_365_LOCATION,
					FilePath.PROPERTY_CONFIG_FILE));
			foundation.objectClick(ReportList.BTN_RUN_REPORT);

			// Verifying the Location name with the displayed name on the Front end
			cashFlowDetailsInternational.verifyReportName(propertyFile
					.readPropertyFile(Configuration.STAGING_SUPPORT_US_365_LOCATION, FilePath.PROPERTY_CONFIG_FILE));

			// Downloading the Report
			reportList.clickOnToExcelButton(reportList.TO_EXCEL_BUTTON);

			foundation.threadWait(Constants.SHORT_TIME);

			// Verifying the Report name with with the Name in the exported file,
			// Verified file existence and deleted the file.
			reportList
					.verifyTheFileWithFullName(
							propertyFile.readPropertyFile(Configuration.STAGING_SUPPORT_US_365_LOCATION,
									FilePath.PROPERTY_CONFIG_FILE),
							rstReportListData.get(CNReportList.DOWNLOADED_FILE_NAME));

			// Verifying, whether the Report data available or not
			cashFlowDetailsInternational.checkForDataAvailabilyInResultTable();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "209117- This test validates Data existance and Excel file exportaion of Alcohol Sold Details Report in Staging Environment")
	public void alcoholSoldDetails_Staging() {
		try {
			final String CASE_NUM = "209117";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			// Select Organization
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.STAGING_STEAVE_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Navigate to Reports
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));
			reportList.selectLocation(propertyFile.readPropertyFile(Configuration.STAGING_STEAVE_LOCATION,
					FilePath.PROPERTY_CONFIG_FILE));
			foundation.objectClick(ReportList.BTN_RUN_REPORT);

			// Verifying the Report name with with the displayed name on the Front end
			alcoholSoldDetailsReport.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));

			// Downloading the Report
			reportList.clickOnToExcelButton(reportList.TO_EXCEL_BUTTON);

			foundation.threadWait(Constants.SHORT_TIME);

			// Verifying the Report name with with the Name in the exported file,
			// Verified file existence and deleted the file.
			reportList.verifyTheFileWithFullName(rstReportListData.get(CNReportList.REPORT_NAME),
					rstReportListData.get(CNReportList.DOWNLOADED_FILE_NAME));

			// Verifying, whether the Report data available or not
			alcoholSoldDetailsReport.checkForDataAvailabilyInResultTable();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "209118- This test validates Data existance and Excel file exportaion of Bad Scan Report in Staging Environment")
	public void badScanReport_Staging() {
		try {
			final String CASE_NUM = "209118";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			// Select Organization
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.STAGING_STEAVE_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Navigate to Reports
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));
			reportList.selectLocation(propertyFile.readPropertyFile(Configuration.STAGING_STEAVE_LOCATION,
					FilePath.PROPERTY_CONFIG_FILE));
			foundation.objectClick(ReportList.BTN_RUN_REPORT);

			// Verifying the Report name with with the displayed name on the Front end
			badScanReport.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));

			// Downloading the Report
			reportList.clickOnToExcelButton(reportList.TO_EXCEL_BUTTON);

			foundation.threadWait(Constants.SHORT_TIME);

			// Verifying the Report name with with the Name in the exported file,
			// Verified file existence and deleted the file.
			reportList.verifyTheFileWithFullName(rstReportListData.get(CNReportList.REPORT_NAME),
					rstReportListData.get(CNReportList.DOWNLOADED_FILE_NAME));

			// Verifying, whether the Report data available or not
			badScanReport.checkForDataAvailabilyInResultTable();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "209119- This test validates Data existance and Excel file exportaion of Billing Information Report in Staging Environment")
	public void billingInformation_Staging() {
		try {
			final String CASE_NUM = "209119";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			// Select Organization
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.STAGING_STEAVE_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Navigate to Reports
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));
			reportList.selectLocation(propertyFile.readPropertyFile(Configuration.STAGING_STEAVE_LOCATION,
					FilePath.PROPERTY_CONFIG_FILE));
			foundation.objectClick(ReportList.BTN_RUN_REPORT);

			// Verifying the Report name with with the displayed name on the Front end
			billingInformationReport.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));

			// Downloading the Report
			foundation.waitforElement(reportList.TO_EXCEL_BUTTON, Constants.FIFTY_FIVE_SECONDS);
			reportList.clickOnToExcelButton(reportList.TO_EXCEL_BUTTON);

			foundation.threadWait(Constants.TEN_SECOND);

			// Verifying the Report name with with the Name in the exported file,
			// Verified file existence and deleted the file.
			reportList.verifyTheFileWithFullName(rstReportListData.get(CNReportList.REPORT_NAME),
					rstReportListData.get(CNReportList.DOWNLOADED_FILE_NAME));

			// Verifying, whether the Report data available or not
			billingInformationReport.checkForDataAvailabilyInResultTable();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "209120- This test validates Data existance and Excel file exportaion of Cash Flow Device Report in Staging Environment")
	public void cashFlowDevice_Staging() {
		try {
			final String CASE_NUM = "209120";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			// Select Organization
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.STAGING_SUPPORT_US_365, FilePath.PROPERTY_CONFIG_FILE));

			List<String> DateRange_Existing = Arrays
					.asList(rstReportListData.get(CNReportList.DATE_RANGE).split(Constants.DELIMITER_TILD));

			// Navigate to Reports
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
//			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));
			reportList.selectDateRangeDate(DateRange_Existing.get(0), DateRange_Existing.get(1),
					CashFlowEmployeeDevice.DATA_EXISTING_START_DATE_STAGING,
					CashFlowEmployeeDevice.DATA_EXISTING_END_DATE_STAGING);
			reportList.selectLocation(propertyFile.readPropertyFile(Configuration.STAGING_SUPPORT_US_365_LOCATION,
					FilePath.PROPERTY_CONFIG_FILE));
			foundation.objectClick(ReportList.BTN_RUN_REPORT);

			// Verifying the Report name with with the displayed name on the Front end
			cashFlowDevice.verifyReportName(propertyFile.readPropertyFile(Configuration.STAGING_SUPPORT_US_365_LOCATION,
					FilePath.PROPERTY_CONFIG_FILE));

			// Downloading the Report
			reportList.clickOnToExcelButton(reportList.TO_EXCEL_BUTTON);

			foundation.threadWait(Constants.MEDIUM_TIME);

			// Verifying the Report name with with the Name in the exported file,
			// verified file existence and deleted the file
			reportList
					.verifyTheFileWithFullName(
							propertyFile.readPropertyFile(Configuration.STAGING_SUPPORT_US_365_LOCATION,
									FilePath.PROPERTY_CONFIG_FILE),
							rstReportListData.get(CNReportList.DOWNLOADED_FILE_NAME));

			// Verifying, whether the Report data available or not
			cashFlowDevice.checkForDataAvailabilyInResultTable();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	//

	@Test(description = "209122- This test validates Data existance and Excel file exportaion of Cash Flow Employee Report in Staging Environment")
	public void cashFlowEmployee_Staging() {
		try {
			final String CASE_NUM = "209122";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			// Select Organization
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.STAGING_SUPPORT_US_365, FilePath.PROPERTY_CONFIG_FILE));

			List<String> DateRange_Existing = Arrays
					.asList(rstReportListData.get(CNReportList.DATE_RANGE).split(Constants.DELIMITER_TILD));

			// Navigate to Reports
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location

			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectDateRangeDate(DateRange_Existing.get(0), DateRange_Existing.get(1),
					CashFlowEmployee.DATA_EXISTING_START_DATE_STAGING, CashFlowEmployee.DATA_EXISTING_END_DATE_STAGING);

//			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));
			reportList.selectLocation(propertyFile.readPropertyFile(Configuration.STAGING_SUPPORT_US_365_LOCATION,
					FilePath.PROPERTY_CONFIG_FILE));
			foundation.objectClick(ReportList.BTN_RUN_REPORT);

			// Verifying the Report name with with the displayed name on the Front end
			cashFlowEmployee.verifyReportName(propertyFile
					.readPropertyFile(Configuration.STAGING_SUPPORT_US_365_LOCATION, FilePath.PROPERTY_CONFIG_FILE));

			// Downloading the Report
			reportList.clickOnToExcelButton(reportList.TO_EXCEL_BUTTON);

			foundation.threadWait(Constants.TEN_SECOND);

			// Verifying the Report name with with the Name in the exported file,
			// verified file existence and deleted the file
			reportList
					.verifyTheFileWithFullName(
							propertyFile.readPropertyFile(Configuration.STAGING_SUPPORT_US_365_LOCATION,
									FilePath.PROPERTY_CONFIG_FILE),
							rstReportListData.get(CNReportList.DOWNLOADED_FILE_NAME));

			// Verifying, whether the Report data available or not
			cashFlowEmployee.checkForDataAvailabilyInResultTable();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "209123- This test validates Data existance and Excel file exportaion of Cash Flow Employee Device Report in Staging Environment")
	public void cashFlowEmployeeDevice_Staging() {
		try {
			final String CASE_NUM = "209123";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			// Select Organization
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.STAGING_SUPPORT_US_365, FilePath.PROPERTY_CONFIG_FILE));

			List<String> DateRange_Existing = Arrays
					.asList(rstReportListData.get(CNReportList.DATE_RANGE).split(Constants.DELIMITER_TILD));

			// Navigate to Reports
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));

			reportList.selectDateRangeDate(DateRange_Existing.get(0), DateRange_Existing.get(1),
					CashFlowEmployeeDevice.DATA_EXISTING_START_DATE_STAGING,
					CashFlowEmployeeDevice.DATA_EXISTING_END_DATE_STAGING);
//			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));
			reportList.selectLocation(propertyFile.readPropertyFile(Configuration.STAGING_SUPPORT_US_365_LOCATION,
					FilePath.PROPERTY_CONFIG_FILE));
			foundation.objectClick(ReportList.BTN_RUN_REPORT);

			// Verifying the Report name with with the displayed name on the Front end
			cashFlowEmployeeDevice.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));

			// Downloading the Report
			reportList.clickOnToExcelButton(reportList.TO_EXCEL_BUTTON);

			foundation.threadWait(Constants.TEN_SECOND);

			// Verifying the Report name with with the Name in the exported file,
			// verified file existence and deleted the file
			reportList
					.verifyTheFileWithFullName(
							propertyFile.readPropertyFile(Configuration.STAGING_SUPPORT_US_365_LOCATION,
									FilePath.PROPERTY_CONFIG_FILE),
							rstReportListData.get(CNReportList.DOWNLOADED_FILE_NAME));

			// Verifying, whether the Report data available or not
			cashFlowEmployeeDevice.checkForDataAvailabilyInResultTable();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "209138- This test validates Data existance and Excel file exportaion of Cash Flow Details Report in Staging Environment")
	public void cashFlowDetails_Staging() {
		try {
			final String CASE_NUM = "209138";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			// Select Organization
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.STAGING_SUPPORT_US_365, FilePath.PROPERTY_CONFIG_FILE));

			List<String> DateRange_Existing = Arrays
					.asList(rstReportListData.get(CNReportList.DATE_RANGE).split(Constants.DELIMITER_TILD));

			// Navigate to Reports
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectDateRangeDate(DateRange_Existing.get(0), DateRange_Existing.get(1),
					CashFlowDetails.DATA_EXISTING_START_DATE_STAGING, CashFlowDetails.DATA_EXISTING_END_DATE_STAGING);
			reportList.selectLocation(propertyFile.readPropertyFile(Configuration.STAGING_SUPPORT_US_365_LOCATION,
					FilePath.PROPERTY_CONFIG_FILE));
			foundation.objectClick(ReportList.BTN_RUN_REPORT);

			// Verifying the Location name with the displayed name on the Front end
			cashFlowDetails.verifyReportName(propertyFile
					.readPropertyFile(Configuration.STAGING_SUPPORT_US_365_LOCATION, FilePath.PROPERTY_CONFIG_FILE));

			// Downloading the Report
			reportList.clickOnToExcelButton(reportList.TO_EXCEL_BUTTON);

			foundation.threadWait(Constants.SHORT_TIME);

			// Verifying the Report name with with the Name in the exported file,
			// Verified file existence and deleted the file.
			reportList
					.verifyTheFileWithFullName(
							propertyFile.readPropertyFile(Configuration.STAGING_SUPPORT_US_365_LOCATION,
									FilePath.PROPERTY_CONFIG_FILE),
							rstReportListData.get(CNReportList.DOWNLOADED_FILE_NAME));

			// Verifying, whether the Report data available or not
			cashFlowDetails.checkForDataAvailabilyInResultTable();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	// Not closed

	@Test(description = "209124- This test validates Data existance and Excel file exportaion of Cashout Log Report in Staging Environment")
	public void cashoutLog_Staging() {
		try {
			final String CASE_NUM = "209124";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			// Select Organization
			navigationBar.selectOrganization(propertyFile
					.readPropertyFile(Configuration.FIVESTARCINCINNATI_ORGANIZATION, FilePath.PROPERTY_CONFIG_FILE));

			List<String> DateRange_Existing = Arrays
					.asList(rstReportListData.get(CNReportList.DATE_RANGE).split(Constants.DELIMITER_TILD));

			// Navigate to Reports
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectDateRangeDate(DateRange_Existing.get(0), DateRange_Existing.get(1),
					CashoutLog.DATA_EXISTING_START_DATE_STAGING, CashoutLog.DATA_EXISTING_END_DATE_STAGING);

//			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));
			reportList.selectLocation(
					propertyFile.readPropertyFile(Configuration.ALL_LOCATIONS, FilePath.PROPERTY_CONFIG_FILE));
			foundation.objectClick(ReportList.BTN_RUN_REPORT);

			// Verifying the Report name with with the displayed name on the Front end
			cashoutLog.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));

			// Downloading the Report
			reportList.clickOnToExcelButton(reportList.TO_EXCEL_BUTTON);

			foundation.threadWait(Constants.SHORT_TIME);

			// Verifying the Report name with with the Name in the exported file,
			// Verified file existence and deleted the file.
			reportList.verifyTheFileWithFullName(rstReportListData.get(CNReportList.REPORT_NAME),
					rstReportListData.get(CNReportList.DOWNLOADED_FILE_NAME));

			// Verifying, whether the Report data available or not
			cashoutLog.checkForDataAvailabilyInResultTable();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "209173- This test validates Data existance and Excel file exportaion of Consumer Feedback Survey Report in Staging Environment")
	public void consumerFeedbackSurvey_Staging() {
		try {
			final String CASE_NUM = "209173";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			// Select Organization
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.STAGING_SUPPORT_US_365, FilePath.PROPERTY_CONFIG_FILE));

			List<String> DateRange_Existing = Arrays
					.asList(rstReportListData.get(CNReportList.DATE_RANGE).split(Constants.DELIMITER_TILD));

			// Navigate to Reports
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectDateRangeDate(DateRange_Existing.get(0), DateRange_Existing.get(1),
					ConsumerFeedbackSurvey.DATA_EXISTING_START_DATE_STAGING,
					ConsumerFeedbackSurvey.DATA_EXISTING_END_DATE_STAGING);
			reportList.selectLocation(propertyFile.readPropertyFile(Configuration.STAGING_SUPPORT_US_365_LOCATION,
					FilePath.PROPERTY_CONFIG_FILE));
			foundation.objectClick(ReportList.BTN_RUN_REPORT);

			// Downloading the Report
			reportList.clickOnToExcelButton(reportList.TO_EXCEL_EXPORTBUTTON);

			foundation.threadWait(Constants.SHORT_TIME);

			// Verified file existence and deleted the file.
			reportList.verifyTheFileExistanceWithDateAndWithoutDataValidation(
					rstReportListData.get(CNReportList.DOWNLOADED_FILE_NAME),
					rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));

			// Verifying, whether the Report data available or not
			consumerFeedbackSurvey.checkForDataAvailabilyInResultTable();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "209174- This test validates Data existance and Excel file exportaion of Consumer Purchase Summary Report in Staging Environment")
	public void consumerPurchaseSummary_Staging() {
		try {
			final String CASE_NUM = "209174";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			// Select Organization
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.STAGING_SUPPORT_US_365, FilePath.PROPERTY_CONFIG_FILE));

			List<String> DateRange_Existing = Arrays
					.asList(rstReportListData.get(CNReportList.DATE_RANGE).split(Constants.DELIMITER_TILD));

			// Navigate to Reports
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectDateRangeDate(DateRange_Existing.get(0), DateRange_Existing.get(1),
					ConsumerPurchaseSummary.DATA_EXISTING_START_DATE_STAGING,
					ConsumerPurchaseSummary.DATA_EXISTING_END_DATE_STAGING);
			reportList.selectLocation(propertyFile.readPropertyFile(Configuration.STAGING_SUPPORT_US_365_LOCATION,
					FilePath.PROPERTY_CONFIG_FILE));
			foundation.objectClick(ReportList.BTN_RUN_REPORT);

			// Verifying the Report name with with the displayed name on the Front end
			consumerPurchaseSummary.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));

			// Downloading the Report
			reportList.clickOnToExcelButton(reportList.TO_EXCEL_BUTTON);

			foundation.threadWait(Constants.MEDIUM_TIME);

			// Verifying the Report name with with the Name in the exported file,
			// Verified file existence and deleted the file.
			reportList.verifyTheFileWithFullName(rstReportListData.get(CNReportList.REPORT_NAME),
					rstReportListData.get(CNReportList.DOWNLOADED_FILE_NAME));

			// Verifying, whether the Report data available or not
			consumerPurchaseSummary.checkForDataAvailabilyInResultTable();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "209175- This test validates Data existance and Excel file exportaion of Credit Transaction Report in Staging Environment")
	public void creditTransaction_Staging() {
		try {
			final String CASE_NUM = "209175";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			// Select Organization
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.STAGING_SUPPORT_US_365, FilePath.PROPERTY_CONFIG_FILE));

			List<String> DateRange_Existing = Arrays
					.asList(rstReportListData.get(CNReportList.DATE_RANGE).split(Constants.DELIMITER_TILD));

			// Navigate to Reports
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectDateRangeDate(DateRange_Existing.get(0), DateRange_Existing.get(1),
					CreditTransaction.DATA_EXISTING_START_DATE_STAGING,
					CreditTransaction.DATA_EXISTING_END_DATE_STAGING);
			reportList.selectLocation(propertyFile.readPropertyFile(Configuration.STAGING_SUPPORT_US_365_LOCATION,
					FilePath.PROPERTY_CONFIG_FILE));
			foundation.objectClick(ReportList.BTN_RUN_REPORT);

			// Verifying the Report name with with the displayed name on the Front end
			creditTransaction.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));

			// Downloading the Report
			reportList.clickOnToExcelButton(reportList.TO_EXCEL_BUTTON);

			foundation.threadWait(Constants.SHORT_TIME);

			// Verifying the Report name with with the Name in the exported file,
			// Verified file existence and deleted the file.
			reportList.verifyTheFileWithFullName(rstReportListData.get(CNReportList.REPORT_NAME),
					rstReportListData.get(CNReportList.DOWNLOADED_FILE_NAME));

			// Verifying, whether the Report data available or not
			creditTransaction.checkForDataAvailabilyInResultTable();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
	
	@Test(description = "209180- This test validates Data existance and Excel file exportaion of Cross-Org: GoLive Report in Staging Environment")
	public void crossOrgGoLive_Staging() {
		try {
			final String CASE_NUM = "209180";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			// Select Organization
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.STAGING_SUPPORT_US_365,
					FilePath.PROPERTY_CONFIG_FILE));
			
			List<String> DateRange_Existing = Arrays.asList(rstReportListData.get(CNReportList.DATE_RANGE).split(Constants.DELIMITER_TILD));

			// Navigate to Reports
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));

			reportList.selectDateRangeDate(DateRange_Existing.get(0),
								DateRange_Existing.get(1), CrossOrgGoLive.DATA_EXISTING_START_DATE_STAGING,
								CrossOrgGoLive.DATA_EXISTING_END_DATE_STAGING);
			reportList.selectOrg(propertyFile.readPropertyFile(Configuration.STAGING_SUPPORT_US_365, FilePath.PROPERTY_CONFIG_FILE));
			foundation.objectClick(ReportList.BTN_RUN_REPORT);

			// Verifying the Report name with with the displayed name on the Front end
			crossOrgGoLive.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));

			// Downloading the Report
			reportList.clickOnToExcelButton(reportList.TO_EXCEL_BUTTON);

			foundation.threadWait(Constants.SHORT_TIME);

			// Verifying the Report name with with the Name in the exported file,
			// Verified file existence and deleted the file.
			reportList.verifyTheFileWithFullName(rstReportListData.get(CNReportList.REPORT_NAME),
					rstReportListData.get(CNReportList.DOWNLOADED_FILE_NAME));

			// Verifying, whether the Report data available or not
			crossOrgGoLive.checkForDataAvailabilyInResultTable();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "209181- This test validates Data existance and Excel file exportaion of cross Org: Loyalty Report in Staging Environment")
	public void crossOrgLoyalty_Staging() {
		try {
			final String CASE_NUM = "209181";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			// Select Organization
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.STAGING_SUPPORT_US_365, FilePath.PROPERTY_CONFIG_FILE));

			List<String> DateRange_Existing = Arrays.asList(rstReportListData.get(CNReportList.DATE_RANGE).split(Constants.DELIMITER_TILD));

			// Navigate to Reports
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));

			reportList.selectDateRangeDate(DateRange_Existing.get(0),
								DateRange_Existing.get(1), CrossOrgLoyaltyReport.DATA_EXISTING_START_DATE_STAGING,
								CrossOrgLoyaltyReport.DATA_EXISTING_END_DATE_STAGING);
			reportList.selectOrg(
					propertyFile.readPropertyFile(Configuration.STAGING_SUPPORT_US_365, FilePath.PROPERTY_CONFIG_FILE));
			foundation.objectClick(ReportList.BTN_RUN_REPORT);

			// Verifying the Report name with with the displayed name on the Front end
			crossOrgLoyaltyReport.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));

			foundation.threadWait(Constants.SHORT_TIME);
			// Downloading the Report
			reportList.clickOnToExcelButton(reportList.TO_EXCEL_BUTTON);

			foundation.threadWait(Constants.SHORT_TIME);

			// Verifying the Report name with with the Name in the exported file,
			// Verified file existence and deleted the file.
			reportList.verifyTheFileWithFullName(rstReportListData.get(CNReportList.REPORT_NAME),
					rstReportListData.get(CNReportList.DOWNLOADED_FILE_NAME));

			// Verifying, whether the Report data available or not
			crossOrgLoyaltyReport.checkForDataAvailabilyInResultTable();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}


	@Test(description = "209183- This test validates Data existance and Excel file exportaion of Cross-Org: Rate Report in Staging Environment")
	public void crossOrgRateReport_Staging() {
		try {
			final String CASE_NUM = "209183";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			// Select Organization
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.STAGING_SUPPORT_US_365, FilePath.PROPERTY_CONFIG_FILE));

			// Navigate to Reports
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectOrg(
					propertyFile.readPropertyFile(Configuration.STAGING_SUPPORT_US_365, FilePath.PROPERTY_CONFIG_FILE));
			foundation.objectClick(ReportList.BTN_RUN_REPORT);

			// Verifying the Report name with with the displayed name on the Front end
			crossOrgRateReport.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));

			// Downloading the Report
			reportList.clickOnToExcelButton(reportList.TO_EXCEL_BUTTON);

			foundation.threadWait(Constants.SHORT_TIME);

			// Verifying the Report name with with the Name in the exported file,
			// Verified file existence and deleted the file.
			reportList.verifyTheFileWithFullName(rstReportListData.get(CNReportList.REPORT_NAME),
					rstReportListData.get(CNReportList.DOWNLOADED_FILE_NAME));

			// Verifying, whether the Report data available or not
			crossOrgRateReport.checkForDataAvailabilyInResultTable();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
	
	@Test(description = "167014- This test validates Data existance and Excel file exportaion of Daily Sales Summary Report in Staging Environment")
	public void dailySalesSummary() {
		try {
			final String CASE_NUM = "167014";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			// Select Organization
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.STAGING_SUPPORT_US_365,
					FilePath.PROPERTY_CONFIG_FILE));

			// Navigate to Reports
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));
			reportList.selectLocation(
					propertyFile.readPropertyFile(Configuration.STAGING_SUPPORT_US_365_LOCATION, FilePath.PROPERTY_CONFIG_FILE));
			foundation.objectClick(ReportList.BTN_RUN_REPORT);

			// Verifying the Report name with with the displayed name on the Front end
			dailySalesSummary.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));

			// Downloading the Report
			reportList.clickOnToExcelButton(reportList.TO_EXCEL_BUTTON);

			foundation.threadWait(Constants.SHORT_TIME);

			// Verifying the Report name with with the Name in the exported file,
			// Verified file existence and deleted the file.
			reportList.verifyTheFileWithFullName(rstReportListData.get(CNReportList.REPORT_NAME),
					rstReportListData.get(CNReportList.DOWNLOADED_FILE_NAME));

			// Verifying, whether the Report data available or not
			dailySalesSummary.checkForDataAvailabilyInResultTable();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "209187- This test validates Data existance and Excel file exportaion of Delete Summary Report Report in Staging Environment")
	public void deleteSummaryReport_Staging() {
		try {
			final String CASE_NUM = "209187";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			// Select Organization
			navigationBar.selectOrganization(propertyFile
					.readPropertyFile(Configuration.STAGING_SUPPORT_US_365, FilePath.PROPERTY_CONFIG_FILE));

			List<String> DateRange_Existing = Arrays.asList(rstReportListData.get(CNReportList.DATE_RANGE).split(Constants.DELIMITER_TILD));

			// Navigate to Reports
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			
			reportList.selectDateRangeDate(DateRange_Existing.get(0),
								DateRange_Existing.get(1), DeleteSummaryReport.DATA_EXISTING_START_DATE_STAGING,
								DeleteSummaryReport.DATA_EXISTING_END_DATE_STAGING);
			reportList.selectLocation(
					propertyFile.readPropertyFile(Configuration.STAGING_SUPPORT_US_365_LOCATION, FilePath.PROPERTY_CONFIG_FILE));
			foundation.objectClick(ReportList.BTN_RUN_REPORT);

			// Verifying the Report name with with the displayed name on the Front end
			deleteSummaryReport.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));

			// Downloading the Report
			reportList.clickOnToExcelButton(reportList.TO_EXCEL_BUTTON);

			foundation.threadWait(Constants.MEDIUM_TIME);

			// Verifying the Report name with with the Name in the exported file,
			// Verified file existence and deleted the file.
			reportList.verifyTheFileWithFullName(rstReportListData.get(CNReportList.REPORT_NAME),
					rstReportListData.get(CNReportList.DOWNLOADED_FILE_NAME));

			// Verifying, whether the Report data available or not
			deleteSummaryReport.checkForDataAvailabilyInResultTable();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
	
	@Test(description = "209188- This test validates Data existance and Excel file exportaion of Device By Category Report")
	public void deviceByCategory_Staging() {
		try {
			final String CASE_NUM = "209188";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			// Select Organization
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.STAGING_SUPPORT_US_365,
					FilePath.PROPERTY_CONFIG_FILE));
			
			List<String> DateRange_Existing = Arrays.asList(rstReportListData.get(CNReportList.DATE_RANGE).split(Constants.DELIMITER_TILD));

			// Navigate to Reports
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			
			reportList.selectDateRangeDate(DateRange_Existing.get(0),
								DateRange_Existing.get(1), DeviceByCategoryReport.DATA_EXISTING_START_DATE_STAGING,
								DeviceByCategoryReport.DATA_EXISTING_END_DATE_STAGING);
			reportList.selectLocation(
					propertyFile.readPropertyFile(Configuration.STAGING_SUPPORT_US_365_LOCATION, FilePath.PROPERTY_CONFIG_FILE));
			foundation.objectClick(ReportList.BTN_RUN_REPORT);

			// Verifying the Report name with with the displayed name on the Front end
			deviceByCategoryReport.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));

			// Downloading the Report
			reportList.clickOnToExcelButton(reportList.TO_EXCEL_BUTTON);

			foundation.threadWait(Constants.SHORT_TIME);

			// Verifying the Report name with with the Name in the exported file,
			// verified file existence and deleted the file
			reportList.verifyTheFileWithFullName(rstReportListData.get(CNReportList.REPORT_NAME),
					rstReportListData.get(CNReportList.DOWNLOADED_FILE_NAME));

			// Verifying, whether the Report data available or not
			deviceByCategoryReport.checkForDataAvailabilyInResultTable();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	
	
	@Test(description = "220353- This test validates Data existance and Excel file exportaion of Entry Summary Report in Staging Environment")
	public void entrySummaryReport_Staging() {
		try {
			final String CASE_NUM = "220353";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			// Select Organization
			navigationBar.selectOrganization(propertyFile
					.readPropertyFile(Configuration.STAGING_SUPPORT_US_365, FilePath.PROPERTY_CONFIG_FILE));

			// Navigate to Reports
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			List<String> DateRange_Existing = Arrays.asList(rstReportListData.get(CNReportList.DATE_RANGE).split(Constants.DELIMITER_TILD));

			reportList.selectDateRangeDate(DateRange_Existing.get(0),
								DateRange_Existing.get(1), EntrySummaryReport.DATA_EXISTING_START_DATE_STAGING,
								EntrySummaryReport.DATA_EXISTING_END_DATE_STAGING);
			//STAGING_SUPPORT_US_365_PERFORMANCE_LOCATION
			reportList.selectLocation(
					propertyFile.readPropertyFile(Configuration.ALL_LOCATIONS, FilePath.PROPERTY_CONFIG_FILE));
			foundation.objectClick(ReportList.BTN_RUN_REPORT);
			foundation.threadWait(Constants.SHORT_TIME);

			// Verifying the Report name with with the displayed name on the Front end
			entrySummaryReport.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));

			// Downloading the Report
			reportList.clickOnToExcelButton(reportList.TO_EXCEL_BUTTON);

			foundation.threadWait(Constants.SHORT_TIME);

			// Verifying the Report name with with the Name in the exported file,
			// Verified file existence and deleted the file.
			reportList.verifyTheFileWithFullName(rstReportListData.get(CNReportList.REPORT_NAME),
					rstReportListData.get(CNReportList.DOWNLOADED_FILE_NAME));

			// Verifying, whether the Report data available or not
			entrySummaryReport.checkForDataAvailabilyInResultTable();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}


	@Test(description = "220354- This test validates Data existance and Excel file exportaion of Express Disbursement Report in Staging Environment")
	public void expressDisbursement_Staging() {
		try {
			final String CASE_NUM = "220354";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.STAGING_STEAVE_ORG, FilePath.PROPERTY_CONFIG_FILE));

			List<String> DateRange_Existing = Arrays.asList(rstReportListData.get(CNReportList.DATE_RANGE).split(Constants.DELIMITER_TILD));

			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));

			reportList.selectDateRangeDate(DateRange_Existing.get(0),
								DateRange_Existing.get(1), ExpressDisbursement.DATA_EXISTING_START_DATE_STAGING,
								ExpressDisbursement.DATA_EXISTING_END_DATE_STAGING);
			reportList.selectLocation(
					propertyFile.readPropertyFile(Configuration.STAGING_STEAVE_LOCATION, FilePath.PROPERTY_CONFIG_FILE));
			foundation.objectClick(ReportList.BTN_RUN_REPORT);

			// Verifying the Report name with with the displayed name on the Front end
			expressDisbursement.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));

			// Downloading the Report
			reportList.clickOnToExcelButton(reportList.TO_EXCEL_BUTTON);

			foundation.threadWait(Constants.MEDIUM_TIME);

			// Verifying the Report name with with the Name in the exported file,
			// Verified file existence and deleted the file.
			reportList.verifyTheFileWithFullName(rstReportListData.get(CNReportList.REPORT_NAME),
					rstReportListData.get(CNReportList.DOWNLOADED_FILE_NAME));

			// Verifying, whether the Report data available or not
			expressDisbursement.checkForDataAvailabilyInResultTable();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "220355- This test validates Data existance and Excel file exportaion of Financial Canned Report in Staging Environment")
	public void financialCanned_Staging() {
		try {
			final String CASE_NUM = "220355";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			// Select Organization
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.STAGING_SUPPORT_US_365, FilePath.PROPERTY_CONFIG_FILE));

			// Navigate to Reports
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));
			reportList.selectLocation(
					propertyFile.readPropertyFile(Configuration.STAGING_SUPPORT_US_365_LOCATION, FilePath.PROPERTY_CONFIG_FILE));
			foundation.objectClick(ReportList.BTN_RUN_REPORT);

			// Verifying the Report name with with the displayed name on the Front end
			financialCanned.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));

			// Downloading the Report
			foundation.waitforElement(reportList.TO_EXCEL_BUTTON, Constants.FIFTY_FIVE_SECONDS);
			reportList.clickOnToExcelButton(reportList.TO_EXCEL_BUTTON);

			foundation.threadWait(Constants.TEN_SECOND);

			// Verifying the Report name with with the Name in the exported file,
			// Verified file existence and deleted the file.
			reportList.verifyTheFileWithFullName(rstReportListData.get(CNReportList.REPORT_NAME),
					rstReportListData.get(CNReportList.DOWNLOADED_FILE_NAME));

			// Verifying, whether the Report data available or not
			financialCanned.checkForDataAvailabilyInResultTable();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
	
	@Test(description = "220356- This test validates Data existance and Excel file exportaion of Financial Recap Report in Staging Environment")
	public void financialRecap_Staging() {
		try {
			final String CASE_NUM = "220356";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			// Select Organization
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.STAGING_SUPPORT_US_365, FilePath.PROPERTY_CONFIG_FILE));

			List<String> DateRange_Existing = Arrays.asList(rstReportListData.get(CNReportList.DATE_RANGE).split(Constants.DELIMITER_TILD));

			// Navigate to Reports
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));

			reportList.selectDateRangeDate(DateRange_Existing.get(0),
								DateRange_Existing.get(1), FinancialRecapReport.DATA_EXISTING_START_DATE_STAGING,
								FinancialRecapReport.DATA_EXISTING_END_DATE_STAGING);
			reportList.selectLocation(
					propertyFile.readPropertyFile(Configuration.STAGING_SUPPORT_US_365_LOCATION, FilePath.PROPERTY_CONFIG_FILE));
			foundation.threadWait(Constants.THREE_SECOND);
			foundation.objectClick(ReportList.BTN_RUN_REPORT);

			// Verifying the Report name with with the displayed name on the Front end
			financialRecapReport.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));
			
			foundation.threadWait(Constants.MEDIUM_TIME);
			// Downloading the Report
//			reportList.clickOnToExcelButton(reportList.TO_EXCEL_BUTTON);
//
//			foundation.threadWait(Constants.MEDIUM_TIME);
//
//			// Verifying the Report name with with the Name in the exported file,
//			// Verified file existence and deleted the file.
//			reportList.verifyTheFileWithFullName(rstReportListData.get(CNReportList.REPORT_NAME),
//					rstReportListData.get(CNReportList.DOWNLOADED_FILE_NAME));

			// Verifying, whether the Report data available or not
			financialRecapReport.checkForDataAvailabilyInResultTable();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
	
	@Test(description = "220357- This test validates Data existance and Excel file exportaion of Folio Billing Report in Staging Environment")
	public void folioBilling_Staging() {
		try {
			final String CASE_NUM = "220357";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			// Select Organization
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.STAGING_STEAVE_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Navigate to Reports
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));
			reportList.selectLocation(
					propertyFile.readPropertyFile(Configuration.STAGING_STEAVE_LOCATION, FilePath.PROPERTY_CONFIG_FILE));
			foundation.objectClick(ReportList.BTN_RUN_REPORT);

			// Verifying the Report name with with the displayed name on the Front end
			folioBillingReport.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));

			// Downloading the Report
			reportList.clickOnToExcelButton(reportList.TO_EXCEL_BUTTON);

			foundation.threadWait(Constants.MEDIUM_TIME);

			// Verifying the Report name with with the Name in the exported file,
			// Verified file existence and deleted the file.
			reportList.verifyTheFileWithFullName(rstReportListData.get(CNReportList.REPORT_NAME),
					rstReportListData.get(CNReportList.DOWNLOADED_FILE_NAME));

			// Verifying, whether the Report data available or not
			folioBillingReport.checkForDataAvailabilyInResultTable();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
	
	@Test(description = "220358- This test validates Data existance and Excel file exportaion of Ice Report in Staging Environment")
	public void iceReport_Staging() {
		try {
			final String CASE_NUM = "220358";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			// Select Organization
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.STAGING_SUPPORT_US_365, FilePath.PROPERTY_CONFIG_FILE));

			// Navigate to Reports
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));
			reportList.selectLocation(
					propertyFile.readPropertyFile(Configuration.STAGING_SUPPORT_US_365_LOCATION, FilePath.PROPERTY_CONFIG_FILE));
			foundation.objectClick(ReportList.BTN_RUN_REPORT);

			// Verifying the Report name with with the displayed name on the Front end
			iceReport.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));

			foundation.threadWait(Constants.SHORT_TIME);
			// Downloading the Report
			reportList.clickOnToExcelButton(reportList.TO_EXCEL_BUTTON);

			foundation.threadWait(Constants.EXTRA_LONG_TIME);

			// Verifying the Report name with with the Name in the exported file,
			// Verified file existence and deleted the file.
			reportList.verifyTheFileWithFullName(rstReportListData.get(CNReportList.REPORT_NAME),
					rstReportListData.get(CNReportList.DOWNLOADED_FILE_NAME));

			// Verifying, whether the Report data available or not
			iceReport.checkForDataAvailabilyInResultTable();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "167025- This test validates Data existance and Excel file exportaion of Integration Payments Report in Staging Environment")
	public void integrationPayments_Staging() {
		try {
			final String CASE_NUM = "167025";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			// Select Organization
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.STAGING_SUPPORT_US_365,
					FilePath.PROPERTY_CONFIG_FILE));
			
			List<String> DateRange_Existing = Arrays.asList(rstReportListData.get(CNReportList.DATE_RANGE).split(Constants.DELIMITER_TILD));

			// Navigate to Reports
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			
			reportList.selectDateRangeDate(DateRange_Existing.get(0),
								DateRange_Existing.get(1), IntegrationPaymentReport.DATA_EXISTING_START_DATE_STAGING,
								IntegrationPaymentReport.DATA_EXISTING_END_DATE_STAGING);
			reportList.selectLocation(
					propertyFile.readPropertyFile(Configuration.STAGING_SUPPORT_US_365_NOODLE_HUT, FilePath.PROPERTY_CONFIG_FILE));
			foundation.objectClick(ReportList.BTN_RUN_REPORT);

			// Verifying the Report name with with the displayed name on the Front end
			integrationPaymentReport.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));

			// Downloading the Report
			reportList.clickOnToExcelButton(reportList.TO_EXCEL_BUTTON);

			foundation.threadWait(Constants.SHORT_TIME);

			// Verifying the Report name with with the Name in the exported file,
			// Verified file existence and deleted the file.
			reportList.verifyTheFileWithFullName(rstReportListData.get(CNReportList.REPORT_NAME),
					rstReportListData.get(CNReportList.DOWNLOADED_FILE_NAME));

			// Verifying, whether the Report data available or not
			integrationPaymentReport.checkForDataAvailabilyInResultTable();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "220387- This test validates Data existance and Excel file exportaion of Inventory Adjustment Detail Report in Staging Environment")
	public void inventoryAdjustmentDetail_Staging() {
		try {
			final String CASE_NUM = "220387";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			// Select Organization
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.STAGING_SUPPORT_US_365, FilePath.PROPERTY_CONFIG_FILE));

			List<String> DateRange_Existing = Arrays.asList(rstReportListData.get(CNReportList.DATE_RANGE).split(Constants.DELIMITER_TILD));

			// Navigate to Reports
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));

			reportList.selectDateRangeDate(DateRange_Existing.get(0),
								DateRange_Existing.get(1), InventoryAdjustmentDetail.DATA_EXISTING_START_DATE_STAGING,
								InventoryAdjustmentDetail.DATA_EXISTING_END_DATE_STAGING);
			reportList.selectLocation(
					propertyFile.readPropertyFile(Configuration.ALL_LOCATIONS, FilePath.PROPERTY_CONFIG_FILE));
			foundation.objectClick(ReportList.BTN_RUN_REPORT);

			// Verifying the Report name with with the displayed name on the Front end
			inventoryAdjustmentDetail.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));

			// Downloading the Report
			reportList.clickOnToExcelButton(reportList.TO_EXCEL_BUTTON);

			foundation.threadWait(Constants.TEN_SECOND);

			// Verifying the Report name with with the Name in the exported file,
			// Verified file existence and deleted the file.
			reportList.verifyTheFileWithFullName(rstReportListData.get(CNReportList.REPORT_NAME),
					rstReportListData.get(CNReportList.DOWNLOADED_FILE_NAME));

			// Verifying, whether the Report data available or not
			inventoryAdjustmentDetail.checkForDataAvailabilyInResultTable();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
		
		/**
		 * No Data, Hence Not Completed
		 */
	
		@Test(enabled = false, description = "220388- This test validates Data existance and Excel file exportaion of Inventory List Report in Staging Environment")
		public void inventoryList_Staging() {
			try {
				final String CASE_NUM = "220388";

				browser.navigateURL(
						propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
				login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
						propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

				// Reading test data from DataBase
				rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
				rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

				String reportName = rstReportListData.get(CNReportList.REPORT_NAME);
				// Select Organization
				navigationBar.selectOrganization(
						propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

				// Navigate to Reports
				navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

				// Select the Report Date range and Location
				reportList.selectReport(reportName);
				reportList.selectLocation(
						propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));

				foundation.click(ReportList.BTN_RUN_REPORT);
				foundation.threadWait(Constants.TWO_SECOND);
				inventoryList.verifyReportName(reportName);

				// Downloading the Report
				reportList.clickOnToExcelButton(reportList.TO_EXCEL_EXPORTBUTTON);

				foundation.threadWait(Constants.SHORT_TIME);

				// Verified file existence and deleted the file.
				reportList.verifyTheFileExistanceWithDateAndWithoutDataValidation(
						rstReportListData.get(CNReportList.DOWNLOADED_FILE_NAME),
						rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));

				// Verifying, whether the Report data available or not
				inventoryList.checkForDataAvailabilyInResultTable();
			} catch (Exception exc) {
				TestInfra.failWithScreenShot(exc.toString());
			}
		}
		
		@Test(description = "220389- This test validates Data existance and Excel file exportaion of Inventory Totals Report in Staging Environment")
		public void inventoryTotals_Staging() {
			try {
				final String CASE_NUM = "220389";

				browser.navigateURL(
						propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
				login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
						propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

				// Reading test data from DataBase
				rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
				rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

				// Select Organization
				navigationBar.selectOrganization(
						propertyFile.readPropertyFile(Configuration.STAGING_SUPPORT_US_365, FilePath.PROPERTY_CONFIG_FILE));

				// Navigate to Reports
				navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

				// Select the Report and Run Report
				reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
				foundation.objectClick(ReportList.BTN_RUN_REPORT);

				// Verifying the Report name with with the displayed name on the Front end
				inventoryTotals.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));

				// Downloading the Report
				reportList.clickOnToExcelButton(reportList.TO_EXCEL_BUTTON);

				foundation.threadWait(Constants.SHORT_TIME);

				// Verifying the Report name with with the Name in the exported file,
				// Verified file existence and deleted the file.
				reportList.verifyTheFileWithFullName(rstReportListData.get(CNReportList.REPORT_NAME),
						rstReportListData.get(CNReportList.DOWNLOADED_FILE_NAME));

				// Verifying, whether the Report data available or not
				inventoryTotals.checkForDataAvailabilyInResultTable();
			} catch (Exception exc) {
				TestInfra.failWithScreenShot(exc.toString());
			}
		}

		@Test(description = "220390- This test validates Data existance and Excel file exportaion of Inventory Value Summary Report in Staging Environment")
		public void inventoryValueSummary_Staging() {
			try {
				final String CASE_NUM = "220390";

				browser.navigateURL(
						propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
				login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
						propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

				// Reading test data from DataBase
				rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
				rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

				// Select Organization
				navigationBar.selectOrganization(
						propertyFile.readPropertyFile(Configuration.STAGING_SUPPORT_US_365, FilePath.PROPERTY_CONFIG_FILE));

				// Navigate to Reports
				navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

				// Select the Report Date range and Location
				reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
				reportList.selectLocation(
						propertyFile.readPropertyFile(Configuration.STAGING_SUPPORT_US_365_PERFORMANCE_LOCATION, FilePath.PROPERTY_CONFIG_FILE));
				foundation.objectClick(ReportList.BTN_RUN_REPORT);

				// Verifying the Report name with with the displayed name on the Front end
				inventoryValueSummary.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));

				// Downloading the Report
				reportList.clickOnToExcelButton(reportList.TO_EXCEL_BUTTON);

				foundation.threadWait(Constants.MEDIUM_TIME);

				// Verifying the Report name with with the Name in the exported file,
				// verified file existence and deleted the file
				reportList.verifyTheFileContainsNameWithDateWithoutSpace(rstReportListData.get(CNReportList.REPORT_NAME),
						rstReportListData.get(CNReportList.DOWNLOADED_FILE_NAME),
						rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));

				// Verifying, whether the Report data available or not
				inventoryValueSummary.checkForDataAvailabilyInResultTable();
			} catch (Exception exc) {
				TestInfra.failWithScreenShot(exc.toString());
			}
		}
}