package at.smartshop.tests;

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
import at.smartshop.pages.DataSourceManager;
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
	private CashFlowDetails cashFlowDetails = new CashFlowDetails();
	private CashFlowDetailsInternational cashFlowDetailsInternational = new CashFlowDetailsInternational();
	private AlcoholSoldDetailsReport alcoholSoldDetailsReport = new AlcoholSoldDetailsReport();
	private BadScanReport badScanReport = new BadScanReport();
	private BillingInformationReport billingInformationReport = new BillingInformationReport();
	private CashFlowDevice cashFlowDevice = new CashFlowDevice();
	private CashFlowEmployee cashFlowEmployee = new CashFlowEmployee();
	private CashFlowEmployeeDevice cashFlowEmployeeDevice = new CashFlowEmployeeDevice();

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
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.STAGING_STEAVE_ORG,
					FilePath.PROPERTY_CONFIG_FILE));

			// Navigate to Reports
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectCurrentDay();
			reportList.selectOrg(propertyFile.readPropertyFile(Configuration.STAGING_STEAVE_ORG,
					FilePath.PROPERTY_CONFIG_FILE));
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
			reportList.selectLocation(
					propertyFile.readPropertyFile(Configuration.STAGING_STEAVE_LOCATION, FilePath.PROPERTY_CONFIG_FILE));
			foundation.objectClick(ReportList.BTN_RUN_REPORT);

			// Verifying the Report name with with the displayed name on the Front end
			ufsByDevice.verifyReportName(
					propertyFile.readPropertyFile(Configuration.STAGING_STEAVE_LOCATION, FilePath.PROPERTY_CONFIG_FILE));

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
			reportList.selectLocation(
					propertyFile.readPropertyFile(Configuration.STAGING_STEAVE_LOCATION, FilePath.PROPERTY_CONFIG_FILE));
			foundation.objectClick(ReportList.BTN_RUN_REPORT);
			foundation.threadWait(Constants.SHORT_TIME);

			// Verifying the Report name with with the displayed name on the Front end
			ufsByEmployeeDevice.verifyReportName(
					propertyFile.readPropertyFile(Configuration.STAGING_STEAVE_LOCATION, FilePath.PROPERTY_CONFIG_FILE));

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
			reportList.selectLocation(
					propertyFile.readPropertyFile(Configuration.STAGING_STEAVE_LOCATION, FilePath.PROPERTY_CONFIG_FILE));
			foundation.threadWait(Constants.ONE_SECOND);
			foundation.objectClick(ReportList.BTN_RUN_REPORT);

			// Verifying the Report name with with the displayed name on the Front end
			ufsReport.verifyReportName(
					propertyFile.readPropertyFile(Configuration.STAGING_STEAVE_LOCATION, FilePath.PROPERTY_CONFIG_FILE));

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

			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.STAGING_STEAVE_ORG,
					FilePath.PROPERTY_CONFIG_FILE));

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
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.STAGING_STEAVE_ORG,
					FilePath.PROPERTY_CONFIG_FILE));

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
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.STAGING_STEAVE_ORG,
					FilePath.PROPERTY_CONFIG_FILE));

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
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.STAGING_STEAVE_ORG,
					FilePath.PROPERTY_CONFIG_FILE));

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
			reportList.selectLocationForSecondTypeDropdown(
					propertyFile.readPropertyFile(Configuration.STAGING_STEAVE_LOCATION, FilePath.PROPERTY_CONFIG_FILE));
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
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.STAGING_STEAVE_ORG,
					FilePath.PROPERTY_CONFIG_FILE));

			// Navigate to Reports
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));

			reportList.selectLocation(propertyFile.readPropertyFile(Configuration.STAGING_STEAVE_LOCATION,
					FilePath.PROPERTY_CONFIG_FILE));
			foundation.objectClick(ReportList.BTN_RUN_REPORT);

			// Verifying the Location name with the displayed name on the Front end
			cashFlowDetailsInternational.verifyReportName(propertyFile.readPropertyFile(
					Configuration.STAGING_STEAVE_LOCATION, FilePath.PROPERTY_CONFIG_FILE));

			// Downloading the Report
			reportList.clickOnToExcelButton(reportList.TO_EXCEL_BUTTON);

			foundation.threadWait(Constants.SHORT_TIME);

			// Verifying the Report name with with the Name in the exported file,
			// Verified file existence and deleted the file.
			reportList
					.verifyTheFileWithFullName(
							propertyFile.readPropertyFile(Configuration.STAGING_STEAVE_LOCATION,
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
			reportList.selectLocation(
					propertyFile.readPropertyFile(Configuration.STAGING_STEAVE_LOCATION, FilePath.PROPERTY_CONFIG_FILE));
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
			reportList.selectLocation(
					propertyFile.readPropertyFile(Configuration.STAGING_STEAVE_LOCATION, FilePath.PROPERTY_CONFIG_FILE));
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
			reportList.selectLocation(
					propertyFile.readPropertyFile(Configuration.STAGING_STEAVE_LOCATION, FilePath.PROPERTY_CONFIG_FILE));
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
			cashFlowDevice.verifyReportName(
					propertyFile.readPropertyFile(Configuration.STAGING_STEAVE_LOCATION, FilePath.PROPERTY_CONFIG_FILE));

			// Downloading the Report
			reportList.clickOnToExcelButton(reportList.TO_EXCEL_BUTTON);

			foundation.threadWait(Constants.MEDIUM_TIME);

			// Verifying the Report name with with the Name in the exported file,
			// verified file existence and deleted the file
			reportList.verifyTheFileWithFullName(
					propertyFile.readPropertyFile(Configuration.STAGING_STEAVE_LOCATION, FilePath.PROPERTY_CONFIG_FILE),
					rstReportListData.get(CNReportList.DOWNLOADED_FILE_NAME));

			// Verifying, whether the Report data available or not
			cashFlowDevice.checkForDataAvailabilyInResultTable();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

}
