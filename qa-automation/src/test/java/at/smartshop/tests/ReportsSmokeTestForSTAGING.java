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
import at.smartshop.pages.DataSourceManager;
import at.smartshop.pages.NavigationBar;
import at.smartshop.pages.ReportList;

@Listeners(at.framework.reportsetup.Listeners.class)
public class ReportsSmokeTestForSTAGING extends TestInfra {

	private ResultSets dataBase = new ResultSets();
	private ReportList reportList = new ReportList();
	private Foundation foundation = new Foundation();
	private NavigationBar navigationBar = new NavigationBar();
	private DataSourceManager dataSourceManager = new DataSourceManager();
	private AVISubFeeReport AVISubFeeReport = new AVISubFeeReport();
	private AccountAdjustment accountAdjustment = new AccountAdjustment();
	private AccountFunding accountFunding = new AccountFunding();
	private AccountFundingDetail accountFundingDetail = new AccountFundingDetail();

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
	
	@Test(description = "208977- This test validates Data existance and Excel file exportaion of Account Adjustment Report in Staging Environment")
	public void accountAdjustmentReportInStaging() {
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
	public void accountFundingInStaging() {
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
	public void accountFundingDetail() {
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


}
