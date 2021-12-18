package at.smartshop.tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import at.framework.database.mssql.Queries;
import at.framework.database.mssql.ResultSets;
import at.framework.files.Excel;
import at.framework.ui.CheckBox;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.database.columns.CNLocation;
import at.smartshop.database.columns.CNNavigationMenu;
import at.smartshop.database.columns.CNReportList;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.pages.ReportList;
import at.smartshop.pages.SalesAnalysisReport;
import at.smartshop.pages.SalesTimeDetailsReport;
import at.smartshop.pages.AccountAdjustment;
import at.smartshop.pages.AdvanaBilling;
import at.smartshop.pages.CanadaMultiTaxReport;
import at.smartshop.pages.CashFlowDetails;
import at.smartshop.pages.CustomerReportSkymiles;
import at.smartshop.pages.DataSourceManager;
import at.smartshop.pages.ModifiersReport;
import at.smartshop.pages.MultiTaxReport;
import at.smartshop.pages.NavigationBar;
import at.smartshop.pages.PayrollDeductDetails;
import at.smartshop.pages.PayrollDeductSummary;
import at.smartshop.pages.EFTGMADisbursement;
import at.smartshop.pages.ExpressDisbursement;
import at.smartshop.pages.GMAMigration;
import at.smartshop.pages.HiatusModeReport;
import at.smartshop.pages.IntlWebAppFunding;
import at.smartshop.pages.EFTCrossOrgGMADisbursement;
import at.smartshop.pages.GJCommission;
import at.smartshop.pages.EFTDisbursementDetail;

@Listeners(at.framework.reportsetup.Listeners.class)
public class ReportsSmokeTest extends TestInfra {

	private ResultSets dataBase = new ResultSets();
	private ReportList reportList = new ReportList();
	private Foundation foundation = new Foundation();
	private NavigationBar navigationBar = new NavigationBar();
	private SalesTimeDetailsReport SalesTimeDetailsReport = new SalesTimeDetailsReport();
	private Excel excel = new Excel();
	private SalesAnalysisReport salesAnalysisReport = new SalesAnalysisReport();
	private CustomerReportSkymiles customerReportSkymiles = new CustomerReportSkymiles();
	private AccountAdjustment accountAdjustment = new AccountAdjustment();
	private CheckBox checkBox = new CheckBox();
	private TextBox textBox = new TextBox();
	private DataSourceManager dataSourceManager = new DataSourceManager();
	private ModifiersReport modifiersReport = new ModifiersReport();
	private EFTGMADisbursement EFTGMADisbursement = new EFTGMADisbursement();
	private GMAMigration GMAMigration = new GMAMigration();
	private ExpressDisbursement expressDisbursement = new ExpressDisbursement();
	private CanadaMultiTaxReport canadaMultiTaxReport = new CanadaMultiTaxReport();
	private IntlWebAppFunding intlWebAppFunding = new IntlWebAppFunding();
	private PayrollDeductDetails payrollDeductDetails = new PayrollDeductDetails();
	private PayrollDeductSummary payrollDeductSummary = new PayrollDeductSummary();
	private CashFlowDetails cashFlowDetails = new CashFlowDetails();
	private MultiTaxReport multiTaxReport = new MultiTaxReport();
	private HiatusModeReport hiatusModeReport = new HiatusModeReport();
	private EFTCrossOrgGMADisbursement EFTCrossOrgGMADisbursement = new EFTCrossOrgGMADisbursement();
	private GJCommission  GJCommission = new GJCommission();
	private EFTDisbursementDetail  EFTDisbursementDetail = new EFTDisbursementDetail();
	private AdvanaBilling advanaBilling = new AdvanaBilling();
	
	
	
	
	private Map<String, String> rstNavigationMenuData;
	private Map<String, String> rstReportListData;

	/*
	 * @Parameters({ "driver", "browser", "reportsDB" })
	 * 
	 * @BeforeClass public void beforeTest(String drivers, String browsers, String
	 * reportsDB) { try { browser.launch(drivers, browsers);
	 * dataSourceManager.switchToReportsDB(reportsDB); browser.close(); } catch
	 * (Exception exc) { Assert.fail(exc.toString()); } }
	 */
	@Test(description = "166893 - This test validates Product Tax Report Data Calculation")
	public void salesTimeDetailsReport() {
		try {
			final String CASE_NUM = "166893";

//			reportList.logInToADM();

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));
			reportList.selectLocation(
					propertyFile.readPropertyFile(Configuration.ALL_LOCATIONS, FilePath.PROPERTY_CONFIG_FILE));
			foundation.objectClick(ReportList.BTN_RUN_REPORT);

//			List<String> menuItem = Arrays
//					.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
//			
//			System.out.println(menuItem);
////			List<String> requiredData = Arrays
////					.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
//
//			navigationBar.selectOrganization(
//					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
//			
//			List<String> snowflake = new ArrayList<>();
//			snowflake.add("checked");
//			snowflake.add("unchecked");
//			
//			for (int iter = 0; iter < snowflake.size(); iter++) {
//	
//				// Select Menu and Menu Item
//				navigationBar.navigateToMenuItem(menuItem.get(0));
//				textBox.enterText(By.id("search-box"), rstReportListData.get(CNReportList.REPORT_NAME));
//				if(snowflake.get(iter).equals("checked")) {
//					
//					checkBox.check(By.xpath("//tr[contains(@data-id, 'yyyyy8ea13e811eba2300afaa3adjadu')]//input[@type='checkbox']"));
//					Thread.sleep(5000);
//					System.out.println("*****CHEKED");
//				}else if (snowflake.get(iter).equals("unchecked")) {
//					
//					checkBox.unCheck(By.xpath("//tr[contains(@data-id, 'yyyyy8ea13e811eba2300afaa3adjadu')]//input[@type='checkbox']"));
//					Thread.sleep(5000);
//					System.out.println("*****UNCHEKED");
//				}
//
//			checkBox.check(By.xpath("//tr[contains(@data-id, 'yyyyy8ea13e811eba2300afaa3adjadu')]//input[@type='checkbox']"));
//			
//			navigationBar.navigateToMenuItem(menuItem.get(1));
//			// Select the Report Date range and Location
//			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
//			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));
//			reportList.selectLocation(propertyFile.readPropertyFile(Configuration.ALL_LOCATIONS, FilePath.PROPERTY_CONFIG_FILE));
//			foundation.objectClick(ReportList.BTN_RUN_REPORT);

//			reportList.runReport(rstNavigationMenuData, rstReportListData);

			// Verifying the Report name with with the displayed name on the Front end
//			SalesTimeDetailsReport.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));

			// Downloading the Report
			reportList.clickOnToExcelButton(reportList.TO_EXCEL_BUTTON);

			foundation.threadWait(Constants.SHORT_TIME);

			System.out.println(FilePath.reportFilePath(rstReportListData.get(CNReportList.DOWNLOADED_FILE_NAME)));

			// Verifying the Report name with with the Name in the exported file, verified file existence and deleted the file
//			reportList.verifyTheFileWithFullName(rstReportListData.get(CNReportList.REPORT_NAME),
//					rstReportListData.get(CNReportList.DOWNLOADED_FILE_NAME));

			// Verifying, whether the Report data available or not
			reportList.checkForDataAvailabilyInResultTable();

//		} 

//			catch (Throwable exc) {
//			TestInfra.failWithScreenShot(exc.toString());
//		}
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "166894 - This test validates Product Tax Report Data Calculation")
	public void salesAnalisisReport() {
		try {

			final String CASE_NUM = "166894";

//			reportList.logInToADM();

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

//			reportList.runReport(rstNavigationMenuData, rstReportListData);

			// Select Menu and Menu Item
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));
			reportList.selectLocation(
					propertyFile.readPropertyFile(Configuration.ALL_LOCATIONS, FilePath.PROPERTY_CONFIG_FILE));
			foundation.objectClick(ReportList.BTN_RUN_REPORT);
			
			// Verifying the Report name with with the displayed name on the Front end
			salesAnalysisReport.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));

			// Downloading the Report
			reportList.clickOnToExcelButton(reportList.TO_EXCEL_BUTTON);

			foundation.threadWait(Constants.THREE_SECOND);

			// Verifying the Report name with with the Name in the exported file, verified file existence and deleted the file
			reportList.verifyTheFileContainsNameWithDate(rstReportListData.get(CNReportList.REPORT_NAME),
					rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));

			// Verifying, whether the Report data available or not
			reportList.checkForDataAvailabilyInResultTable();
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "166899 - This test validates Product Tax Report Data Calculation")
	public void CustomerReportSkymiles() {
		try {
			final String CASE_NUM = "166899";

//		reportList.logInToADM();

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));
			reportList.selectLocation(
					propertyFile.readPropertyFile(Configuration.ALL_LOCATIONS, FilePath.PROPERTY_CONFIG_FILE));
			foundation.objectClick(ReportList.BTN_RUN_REPORT);

			// Verifying the Report name with with the displayed name on the Front end
			customerReportSkymiles.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));

			// Downloading the Report
			reportList.clickOnToExcelButton(reportList.TO_EXCEL_BUTTON);

			foundation.threadWait(Constants.THREE_SECOND);

			// Verifying the Report name with with the Name in the exported file, verified file existence and deleted the file
			reportList.verifyTheFileWithFullName(rstReportListData.get(CNReportList.REPORT_NAME),
					rstReportListData.get(CNReportList.DOWNLOADED_FILE_NAME));

			// Verifying, whether the Report data available or not
			reportList.checkForDataAvailabilyInResultTable();
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());

		}
	}

	@Test(description = "166900 - This test validates Product Tax Report Data Calculation")
	public void accountAdjustmentReport() {
		try {
			final String CASE_NUM = "166900";

//		reportList.logInToADM();

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

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

			foundation.threadWait(Constants.THREE_SECOND);

			// Verifying the Report name with with the Name in the exported file, verified file existence and deleted the file
			reportList.verifyTheFileWithFullName(rstReportListData.get(CNReportList.REPORT_NAME),
					rstReportListData.get(CNReportList.DOWNLOADED_FILE_NAME));

			// Verifying, whether the Report data available or not
			reportList.checkForDataAvailabilyInResultTable();
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "166901 - This test validates Product Tax Report Data Calculation")
	public void modifiersReport() {
		try {
			final String CASE_NUM = "166901";

//		reportList.logInToADM();

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));
			reportList.selectLocation(
					propertyFile.readPropertyFile(Configuration.ALL_LOCATIONS, FilePath.PROPERTY_CONFIG_FILE));
			foundation.objectClick(ReportList.BTN_RUN_REPORT);
			
			// Verifying the Report name with with the displayed name on the Front end
			modifiersReport.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));

			// Downloading the Report
			reportList.clickOnToExcelButton(reportList.TO_EXCEL_BUTTON);

			foundation.threadWait(Constants.THREE_SECOND);

			// Verifying the Report name with with the Name in the exported file, verified file existence and deleted the file
			reportList.verifyTheFileWithFullName(rstReportListData.get(CNReportList.REPORT_NAME),
					rstReportListData.get(CNReportList.DOWNLOADED_FILE_NAME));

			// Verifying, whether the Report data available or not
			reportList.checkForDataAvailabilyInResultTable();
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "166902 - This test validates Product Tax Report Data Calculation")
	public void EFTGMADisbursement() {
		try {
			final String CASE_NUM = "166902";

//		reportList.logInToADM();

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and with out Location
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));
			foundation.objectClick(ReportList.BTN_RUN_REPORT);

			// Verifying the Report name with with the displayed name on the Front end
			EFTGMADisbursement.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));

			// Downloading the Report
//			reportList.clickOnToExcelButton(reportList.TO_EXCEL_BUTTON);
//			foundation.threadWait(Constants.THREE_SECOND);

			// Verifying the Report name with with the Name in the exported file, verified file existence and deleted the file
//			reportList.verifyTheFileWithFullName(rstReportListData.get(CNReportList.REPORT_NAME),
//					rstReportListData.get(CNReportList.DOWNLOADED_FILE_NAME));

			// Verifying, whether the Report data available or not
			reportList.checkForDataAvailabilyInResultTable();
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "166903 - This test validates Product Tax Report Data Calculation")
	public void GMAMigration() {
		try {
			final String CASE_NUM = "166903";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report with out Date range and Location
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			foundation.objectClick(ReportList.BTN_RUN_REPORT);

			// Verifying the Report name with with the displayed name on the Front end
			GMAMigration.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));

			// Downloading the Report
			reportList.clickOnToExcelButton(reportList.TO_EXCEL_BUTTON);

			foundation.threadWait(Constants.THREE_SECOND);

			// Verifying the Report name with with the Name in the exported file, verified file existence and deleted the file
			reportList.verifyTheFileWithFullName(rstReportListData.get(CNReportList.REPORT_NAME),
					rstReportListData.get(CNReportList.DOWNLOADED_FILE_NAME));

			// Verifying, whether the Report data available or not
			reportList.checkForDataAvailabilyInResultTable();
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
	
	
	@Test(description = "166904 - This test validates Product Tax Report Data Calculation")
	public void expressDisbursement() {
		try {
			final String CASE_NUM = "166904";

//		reportList.logInToADM();

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));
			reportList.selectLocation(
					propertyFile.readPropertyFile(Configuration.ALL_LOCATIONS, FilePath.PROPERTY_CONFIG_FILE));
			foundation.objectClick(ReportList.BTN_RUN_REPORT);
			
			// Verifying the Report name with with the displayed name on the Front end
			expressDisbursement.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));

			// Downloading the Report
			reportList.clickOnToExcelButton(reportList.TO_EXCEL_BUTTON);

			foundation.threadWait(Constants.THREE_SECOND);

			// Verifying the Report name with with the Name in the exported file, verified file existence and deleted the file
			reportList.verifyTheFileWithFullName(rstReportListData.get(CNReportList.REPORT_NAME),
					rstReportListData.get(CNReportList.DOWNLOADED_FILE_NAME));

			// Verifying, whether the Report data available or not
			reportList.checkForDataAvailabilyInResultTable();
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
	
	@Test(description = "166905 - This test validates Product Tax Report Data Calculation")
	public void canadaMultitax() {
		try {
			final String CASE_NUM = "166905";

//		reportList.logInToADM();

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			// Select Organization
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Navigate to Reports
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));
			reportList.selectLocation(
					propertyFile.readPropertyFile(Configuration.ALL_LOCATIONS, FilePath.PROPERTY_CONFIG_FILE));
			foundation.objectClick(ReportList.BTN_RUN_REPORT);
			
			// Verifying the Report name with with the displayed name on the Front end
			canadaMultiTaxReport.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));

			// Downloading the Report
			reportList.clickOnToExcelButton(reportList.TO_EXCEL_BUTTON);

			foundation.threadWait(Constants.THREE_SECOND);

			// Verifying the Report name with with the Name in the exported file, verified file existence and deleted the file
			reportList.verifyTheFileWithFullName(rstReportListData.get(CNReportList.REPORT_NAME),
					rstReportListData.get(CNReportList.DOWNLOADED_FILE_NAME));

			// Verifying, whether the Report data available or not
			reportList.checkForDataAvailabilyInResultTable();
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
	
	@Test(description = "166906 - This test validates Product Tax Report Data Calculation")
	public void intlWebAppFunding() {
		try {
			final String CASE_NUM = "166906";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			// Select Organization
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Navigate to Reports
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));
			reportList.selectLocation(
					propertyFile.readPropertyFile(Configuration.ALL_LOCATIONS, FilePath.PROPERTY_CONFIG_FILE));
			foundation.objectClick(ReportList.BTN_RUN_REPORT);
			
			// Verifying the Report name with with the displayed name on the Front end
			intlWebAppFunding.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));

			// Downloading the Report
			reportList.clickOnToExcelButton(reportList.TO_EXCEL_BUTTON);

			foundation.threadWait(Constants.THREE_SECOND);

			// Verifying the Report name with with the Name in the exported file, verified file existence and deleted the file
			reportList.verifyTheFileWithFullName(rstReportListData.get(CNReportList.REPORT_NAME),
					rstReportListData.get(CNReportList.DOWNLOADED_FILE_NAME));

			// Verifying, whether the Report data available or not
			intlWebAppFunding.checkForDataAvailabilyInResultTable();
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
	
	@Test(description = "166907 - This test validates Product Tax Report Data Calculation")
	public void payrollDeductDetails() {
		try {
			final String CASE_NUM = "166907";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			// Select Organization
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Navigate to Reports  
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));
			reportList.selectLocationForSecondTypeDropdown(
					propertyFile.readPropertyFile(Configuration.SECOND_LOC, FilePath.PROPERTY_CONFIG_FILE));
			foundation.objectClick(ReportList.BTN_RUN_REPORT);
			
			// Verifying the Report name with with the displayed name on the Front end
			payrollDeductDetails.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));

			// Downloading the Report
			reportList.clickOnToExcelButton(reportList.TO_EXCEL_BUTTON);

			foundation.threadWait(Constants.THREE_SECOND);

			// Verifying the Report name with with the Name in the exported file, verified file existence and deleted the file
			reportList.verifyTheFileWithFullName(rstReportListData.get(CNReportList.REPORT_NAME),
					rstReportListData.get(CNReportList.DOWNLOADED_FILE_NAME));

			// Verifying, whether the Report data available or not
			payrollDeductDetails.checkForDataAvailabilyInResultTable();
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
	
	@Test(description = "166908 - This test validates Product Tax Report Data Calculation")
	public void payrollDeductSummary() {
		try {
			final String CASE_NUM = "166908";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			// Select Organization
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Navigate to Reports  
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));
			reportList.selectLocationForSecondTypeDropdown(
					propertyFile.readPropertyFile(Configuration.SECOND_LOC, FilePath.PROPERTY_CONFIG_FILE));
			foundation.objectClick(ReportList.BTN_RUN_REPORT);
			
			// Verifying the Report name with with the displayed name on the Front end
			payrollDeductSummary.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));

			// Downloading the Report
//			reportList.clickOnToExcelButton(reportList.TO_EXCEL_BUTTON);
//
//			foundation.threadWait(Constants.THREE_SECOND);
//
//			// Verifying the Report name with with the Name in the exported file, verified file existence and deleted the file
//			reportList.verifyTheFileWithFullName(rstReportListData.get(CNReportList.REPORT_NAME),
//					rstReportListData.get(CNReportList.DOWNLOADED_FILE_NAME));

			// Verifying, whether the Report data available or not
			payrollDeductSummary.checkForDataAvailabilyInResultTable();
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
	
	@Test(description = "166910 - This test validates Product Tax Report Data Calculation")
	public void cashFlowDetails() {
		try {
			final String CASE_NUM = "166910";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			// Select Organization
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Navigate to Reports
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));

//			String fisrtLocationFromList = reportList.firstLocationName();
			
			reportList.selectLocation(
					propertyFile.readPropertyFile(Configuration.SECOND_LOC, FilePath.PROPERTY_CONFIG_FILE));
			foundation.objectClick(ReportList.BTN_RUN_REPORT);
			
//			System.out.println(fisrtLocationFromList);
			// Verifying the Location name with the displayed name on the Front end
			cashFlowDetails.verifyReportName(propertyFile.readPropertyFile(Configuration.SECOND_LOC, FilePath.PROPERTY_CONFIG_FILE));

			// Downloading the Report
			reportList.clickOnToExcelButton(reportList.TO_EXCEL_BUTTON);

			foundation.threadWait(Constants.THREE_SECOND);

			// Verifying the Report name with with the Name in the exported file, verified file existence and deleted the file
			reportList.verifyTheFileWithFullName(propertyFile.readPropertyFile(Configuration.SECOND_LOC, FilePath.PROPERTY_CONFIG_FILE),
					rstReportListData.get(CNReportList.DOWNLOADED_FILE_NAME));

			// Verifying, whether the Report data available or not
			cashFlowDetails.checkForDataAvailabilyInResultTable();
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
	
	@Test(description = "166928 - This test validates Product Tax Report Data Calculation")
	public void multiTaxReport() {
		try {
			final String CASE_NUM = "166928";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			// Select Organization
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Navigate to Reports
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));
			reportList.selectLocation(
					propertyFile.readPropertyFile(Configuration.ALL_LOCATIONS, FilePath.PROPERTY_CONFIG_FILE));
			foundation.objectClick(ReportList.BTN_RUN_REPORT);
			
			// Verifying the Report name with with the displayed name on the Front end
			multiTaxReport.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));

			// Downloading the Report
			reportList.clickOnToExcelButton(reportList.TO_EXCEL_BUTTON);

			foundation.threadWait(Constants.THREE_SECOND);

			// Verifying the Report name with with the Name in the exported file, verified file existence and deleted the file
			reportList.verifyTheFileContainsNameWithDate(rstReportListData.get(CNReportList.DOWNLOADED_FILE_NAME),
					rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));
			
			// Verifying, whether the Report data available or not
			multiTaxReport.checkForDataAvailabilyInResultTable();
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
	
	@Test(description = "166929 - This test validates Product Tax Report Data Calculation")
	public void hiatusModeReport() {
		try {
			final String CASE_NUM = "166929";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			// Select Organization
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Navigate to Reports  
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));
			reportList.selectLocation(
					propertyFile.readPropertyFile(Configuration.ALL_LOCATIONS, FilePath.PROPERTY_CONFIG_FILE));
			foundation.objectClick(ReportList.BTN_RUN_REPORT);
			
			// Verifying the Report name with with the displayed name on the Front end
			hiatusModeReport.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));

			// Downloading the Report
			reportList.clickOnToExcelButton(reportList.TO_EXCEL_BUTTON);

			foundation.threadWait(Constants.THREE_SECOND);

			// Verifying the Report name with with the Name in the exported file, verified file existence and deleted the file
			reportList.verifyTheFileWithFullName(rstReportListData.get(CNReportList.REPORT_NAME),
					rstReportListData.get(CNReportList.DOWNLOADED_FILE_NAME));

			// Verifying, whether the Report data available or not
			hiatusModeReport.checkForDataAvailabilyInResultTable();
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
	
	@Test(description = "166930 - This test validates Product Tax Report Data Calculation")
	public void EFTCrossOrgGMADisbursement() {
		try {
			final String CASE_NUM = "166930";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			// Select Organization
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Navigate to Reports  
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));
			reportList.selectOrg(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			foundation.objectClick(ReportList.BTN_RUN_REPORT);
			
			// Verifying the Report name with with the displayed name on the Front end
			EFTCrossOrgGMADisbursement.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));

			foundation.threadWait(Constants.THREE_SECOND);
			// Downloading the Report
			reportList.clickOnToExcelButton(reportList.TO_EXCEL_BUTTON);

			foundation.threadWait(Constants.THREE_SECOND);

			// Verifying the Report name with with the Name in the exported file, verified file existence and deleted the file
			reportList.verifyTheFileContainsNameAsOrgDateAndGMA(rstReportListData.get(CNReportList.REPORT_NAME), propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE),
					rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));

			// Verifying, whether the Report data available or not
			EFTCrossOrgGMADisbursement.checkForDataAvailabilyInResultTable();
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
	
	@Test(description = "166931 - This test validates Product Tax Report Data Calculation")
	public void GJCommission() {
		try {
			final String CASE_NUM = "166931";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			// Select Organization
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Navigate to Reports  
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));
			reportList.selectOrg(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			foundation.objectClick(ReportList.BTN_RUN_REPORT);
			
			// Verifying the Report name with with the displayed name on the Front end
			GJCommission.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));

			// Downloading the Report
			reportList.clickOnToExcelButton(reportList.TO_EXCEL_BUTTON);

			foundation.threadWait(Constants.THREE_SECOND);

			// Verifying the Report name with with the Name in the exported file, verified file existence and deleted the file
			reportList.verifyTheFileWithFullName(rstReportListData.get(CNReportList.REPORT_NAME),
					rstReportListData.get(CNReportList.DOWNLOADED_FILE_NAME));

			// Verifying, whether the Report data available or not
			GJCommission.checkForDataAvailabilyInResultTable();
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
	
	@Test(description = "166932 - This test validates Product Tax Report Data Calculation")
	public void advanaBilling() {
		try {
			final String CASE_NUM = "166932";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			// Select Organization
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Navigate to Reports  
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));
			
			reportList.selectFilter(propertyFile.readPropertyFile(Configuration.FILTER_FOR_ORG, FilePath.PROPERTY_CONFIG_FILE));
			
			reportList.selectOrgOnFilter(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			foundation.objectClick(ReportList.BTN_RUN_REPORT);
			
			// Verifying the Report name with with the displayed name on the Front end
			advanaBilling.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));

			// Downloading the Report
			reportList.clickOnToExcelButton(reportList.TO_EXCEL_EXPORTBUTTON);

			foundation.threadWait(Constants.THREE_SECOND);

			// Verifying the Report name with with the Name in the exported file, verified file existence and deleted the file
			reportList.verifyTheFileContainsNameWithDate(rstReportListData.get(CNReportList.DOWNLOADED_FILE_NAME),
					rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));

			// Verifying, whether the Report data available or not
			advanaBilling.checkForDataAvailabilyInResultTable();
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
	
	@Test(description = "166934 - This test validates Product Tax Report Data Calculation")
	public void EFTDisbursementDetail() {
		try {
			final String CASE_NUM = "166934";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstReportListData = dataBase.getReportListData(Queries.REPORT_LIST, CASE_NUM);

			// Select Organization
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Navigate to Reports  
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));
			reportList.selectLocation(
					propertyFile.readPropertyFile(Configuration.ALL_LOCATIONS, FilePath.PROPERTY_CONFIG_FILE));
			foundation.objectClick(ReportList.BTN_RUN_REPORT);
			
			// Verifying the Report name with with the displayed name on the Front end
			EFTDisbursementDetail.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));

			// Downloading the Report
			reportList.clickOnToExcelButton(reportList.TO_EXCEL_BUTTON);

			foundation.threadWait(Constants.THREE_SECOND);

			// Verifying the Report name with with the Name in the exported file, verified file existence and deleted the file
			reportList.verifyTheFileWithFullName(rstReportListData.get(CNReportList.REPORT_NAME),
					rstReportListData.get(CNReportList.DOWNLOADED_FILE_NAME));

			// Verifying, whether the Report data available or not
			EFTDisbursementDetail.checkForDataAvailabilyInResultTable();
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
}
