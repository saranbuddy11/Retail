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
import at.smartshop.pages.CustomerReportSkymiles;
import at.smartshop.pages.DataSourceManager;
import at.smartshop.pages.ModifiersReport;
import at.smartshop.pages.NavigationBar;
import at.smartshop.pages.EFTGMADisbursement;
import at.smartshop.pages.GMAMigration;

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
			Assert.fail(exc.toString());
		}
	}

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
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(ReportList.BTN_RUN_REPORT);

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
//			reportList.selectLocation(propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));
//			foundation.click(ReportList.BTN_RUN_REPORT);

//			reportList.runReport(rstNavigationMenuData, rstReportListData);

//			SalesTimeDetailsReport.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));

			reportList.clickOnToExcelButton(reportList.TO_EXCEL_BUTTON);

			foundation.threadWait(Constants.SHORT_TIME);

			System.out.println(FilePath.reportFilePath(rstReportListData.get(CNReportList.DOWNLOADED_FILE_NAME)));

//			reportList.verifyTheFileWithFullName(rstReportListData.get(CNReportList.REPORT_NAME),
//					rstReportListData.get(CNReportList.DOWNLOADED_FILE_NAME));

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
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(ReportList.BTN_RUN_REPORT);
			salesAnalysisReport.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));

			reportList.clickOnToExcelButton(reportList.TO_EXCEL_BUTTON);

			foundation.threadWait(Constants.THREE_SECOND);

			reportList.verifyTheFileContainsNameWithDate(rstReportListData.get(CNReportList.REPORT_NAME),
					rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));

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
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(ReportList.BTN_RUN_REPORT);

			customerReportSkymiles.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));

			reportList.clickOnToExcelButton(reportList.TO_EXCEL_BUTTON);

			foundation.threadWait(Constants.THREE_SECOND);

			reportList.verifyTheFileWithFullName(rstReportListData.get(CNReportList.REPORT_NAME),
					rstReportListData.get(CNReportList.DOWNLOADED_FILE_NAME));

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
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(ReportList.BTN_RUN_REPORT);

			accountAdjustment.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));

			reportList.clickOnToExcelButton(reportList.TO_EXCEL_BUTTON);

			foundation.threadWait(Constants.THREE_SECOND);

			reportList.verifyTheFileWithFullName(rstReportListData.get(CNReportList.REPORT_NAME),
					rstReportListData.get(CNReportList.DOWNLOADED_FILE_NAME));

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
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(ReportList.BTN_RUN_REPORT);
			modifiersReport.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));

			reportList.clickOnToExcelButton(reportList.TO_EXCEL_BUTTON);

			foundation.threadWait(Constants.THREE_SECOND);

			reportList.verifyTheFileWithFullName(rstReportListData.get(CNReportList.REPORT_NAME),
					rstReportListData.get(CNReportList.DOWNLOADED_FILE_NAME));

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

			// Select the Report Date range and Location
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			reportList.selectDate(rstReportListData.get(CNReportList.DATE_RANGE));
			foundation.click(ReportList.BTN_RUN_REPORT);

			EFTGMADisbursement.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));

//			reportList.clickOnToExcelButton(reportList.TO_EXCEL_BUTTON);
//			foundation.threadWait(Constants.THREE_SECOND);

//			reportList.verifyTheFileWithFullName(rstReportListData.get(CNReportList.REPORT_NAME),
//					rstReportListData.get(CNReportList.DOWNLOADED_FILE_NAME));

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

			// Select the Report Date range and Location
			reportList.selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			foundation.click(ReportList.BTN_RUN_REPORT);

			GMAMigration.verifyReportName(rstReportListData.get(CNReportList.REPORT_NAME));

			reportList.clickOnToExcelButton(reportList.TO_EXCEL_BUTTON);

			foundation.threadWait(Constants.THREE_SECOND);

			reportList.verifyTheFileWithFullName(rstReportListData.get(CNReportList.REPORT_NAME),
					rstReportListData.get(CNReportList.DOWNLOADED_FILE_NAME));

			reportList.checkForDataAvailabilyInResultTable();
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
}
