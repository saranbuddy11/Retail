package at.smartshop.pages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.aventstack.extentreports.Status;

import at.framework.browser.Factory;
import at.framework.generic.CustomisedAssert;
import at.framework.reportsetup.ExtFactory;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.keys.Constants;
import at.smartshop.tests.TestInfra;

public class BalanceReport extends Factory {
	private Foundation foundation = new Foundation();
	private TextBox textBox = new TextBox();
	private NavigationBar navigationBar = new NavigationBar();
	private ReportList reportList = new ReportList();

	public static List<String> tableHeaders = new ArrayList<>();
	private List<String> admData = new LinkedList<>();
	private Map<String, Object> data = new HashMap<>();
	private Map<Integer, Map<String, String>> initialReportsData = new HashMap<>();
	public static Map<Integer, Map<String, String>> reportsData = new LinkedHashMap<>();

	public static final By LBL_REPORT_NAME = By.id("Balance Report");
	private static final By REPORT_GRID_FIRST_ROW = By.cssSelector("#balanceReportGrid > tbody > tr:nth-child(1)");
	private static final By NO_DATA_AVAILABLE_IN_TABLE = By.xpath("//td[@class='dataTables_empty']");
	public static final By TABLE_BALANCE_REPORT = By.id("balanceReportGrid");
	public static final By TABLE_BALANCE = By.id("balanceReportGrid_scroll");
	public static final By TABLE_BALANCE_REPORT_GRID = By.cssSelector("#balanceReportGrid > tbody");
	public static final By TXT_SEARCH = By.id("filterType");

	/**
	 * Verify Report Name
	 * 
	 * @param reportName
	 */
	public void verifyReportName(String reportName) {
		try {
			foundation.waitforElement(LBL_REPORT_NAME, Constants.EXTRA_LONG_TIME);
			String reportTitle = foundation.getText(LBL_REPORT_NAME);
			CustomisedAssert.assertTrue(reportTitle.contains(reportName));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * Check for Data Availability in Result Table
	 */
	public void checkForDataAvailabilyInResultTable() {
		try {
			if (foundation.isDisplayed(REPORT_GRID_FIRST_ROW)) {
				if (foundation.isDisplayed(NO_DATA_AVAILABLE_IN_TABLE)) {
					ExtFactory.getInstance().getExtent().log(Status.INFO, "No Data Available in Report Table");
					CustomisedAssert.fail("Failed Report because No Data Available in Report Table");
				} else {
					ExtFactory.getInstance().getExtent().log(Status.INFO,
							"Report Data Available in the Table, Hence passing the Test case");
				}
			} else {
				ExtFactory.getInstance().getExtent().log(Status.INFO, "No Report Table Available");
				CustomisedAssert.fail("Failed Report because No Report Table Available");
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * Select the report with Date range and Location to run the report
	 * 
	 * @param reportName
	 * @param location
	 */
	public void selectAndRunReport(String menu, String reportName, String location) {
		navigationBar.navigateToMenuItem(menu);
		reportList.selectReport(reportName);
		foundation.threadWait(Constants.SHORT_TIME);
		reportList.selectLocation(location);
		foundation.threadWait(Constants.SHORT_TIME);
		foundation.waitforClikableElement(ReportList.BTN_RUN_REPORT, Constants.SHORT_TIME);
		foundation.click(ReportList.BTN_RUN_REPORT);
		foundation.waitforElement(ProductSales.LBL_REPORT_NAME, Constants.SHORT_TIME);
		verifyReportName(reportName);
		foundation.threadWait(Constants.SHORT_TIME);
		checkForDataAvailabilyInResultTable();
	}

	/**
	 * Read Balance Report Table Headers
	 */
	public void readBalanceReportTableHeaders() {
		tableHeaders.clear();
		WebElement tableReports = getDriver().findElement(TABLE_BALANCE_REPORT);
		List<WebElement> headers = tableReports.findElements(By.cssSelector("thead > tr > th"));
		for (WebElement header : headers) {
			tableHeaders.add(header.getText());
		}
	}

	/**
	 * Read the data from Balance Report Table
	 * 
	 * @param columnName
	 * @throws Exception
	 */
	public void readAllRecordsFromBalanceReportTable(String columnName) throws Exception {
		tableHeaders.clear();
		WebElement tableReports = getDriver().findElement(TABLE_BALANCE);
		List<WebElement> headers = tableReports.findElements(By.tagName("th"));
		for (WebElement header : headers) {
			tableHeaders.add(header.getText());
		}
		WebElement tableReportsList = getDriver().findElement(TABLE_BALANCE_REPORT_GRID);
		List<WebElement> rows = tableReportsList.findElements(By.tagName("tr"));
		reportsData.clear();
		int count = 0;
		for (WebElement row : rows) {
			Map<String, String> reportsdata = new LinkedHashMap<>();
			for (int iter = 1; iter < tableHeaders.size() + 1; iter++) {
				WebElement column = row.findElement(By.cssSelector("td:nth-child(" + iter + ")"));
				reportsdata.put(tableHeaders.get(iter - 1), column.getText());
			}
			reportsData.put(count, reportsdata);
			count++;
		}
	}

	/**
	 * Verify Report Headers
	 * 
	 * @param columnNames
	 */
	public void verifyReportHeaders(String columnNames) {
		List<String> columnName = Arrays.asList(columnNames.split(Constants.DELIMITER_HASH));
		for (int iter = 0; iter < tableHeaders.size(); iter++) {
			CustomisedAssert.assertTrue(tableHeaders.get(iter).equals(columnName.get(iter)));
		}
	}

	/**
	 * Verify Report Records
	 * 
	 * @param scancode
	 * @throws Exception
	 */
	public void verifyReportRecords(String scancode) throws Exception {
		int rowCount = getRowCount(scancode);
		int coulumnCount = tableHeaders.size();
		for (int val = 0; val < coulumnCount; val++) {
			CustomisedAssert.assertTrue(reportsData.get(rowCount).get(tableHeaders.get(val))
					.contains(initialReportsData.get(rowCount).get(tableHeaders.get(val))));
		}
	}

	/**
	 * Get Row Count
	 * 
	 * @param scanCode
	 * @return
	 * @throws Exception
	 */
	private int getRowCount(String scanCode) throws Exception {
		int rowCount = 0;
		boolean flag = false;
		textBox.enterText(TXT_SEARCH, scanCode);
		WebElement tableReports = getDriver().findElement(TABLE_BALANCE_REPORT_GRID);
		List<WebElement> rows = tableReports.findElements(By.tagName("tr"));
		for (int iter = 0; iter < rows.size(); iter++) {
			if (rows.get(iter).findElement(By.cssSelector("td[aria-describedby='balanceReportGrid_scancode']"))
					.getText().equals(scanCode)) {
				rowCount = iter;
				flag = true;
				break;
			}
		}
		if (!flag == true) {
			Assert.fail();
		}
		return rowCount;
	}

	/**
	 * Get Required Row Count
	 * 
	 * @param requiredData
	 * @return
	 */
	public int getRequiredRowCount(String requiredData) {
		int count = 0;
		for (int iter = 0; iter < initialReportsData.size(); iter++) {
			String scancode = initialReportsData.get(iter).get(tableHeaders.get(5));
			if (scancode.equals(requiredData)) {
				count = iter;
				break;
			}
		}
		return count;
	}

	/**
	 * Update Balance
	 * 
	 * @param requiredData
	 */
	public void updateBalance(String requiredData) {
		int count = getRequiredRowCount(requiredData);
		double updatedBalance = Double
				.parseDouble(initialReportsData.get(count).put(tableHeaders.get(3), admData.get(2)));
		updatedBalance = Math.round(updatedBalance * 100.0) / 100.0;
		initialReportsData.get(count).put(tableHeaders.get(3), String.valueOf(updatedBalance));
	}

	public Map<String, Object> getData() {
		return data;
	}

	public Map<Integer, Map<String, String>> getInitialReportsData() {
		return initialReportsData;
	}

	public List<String> getADMData() {
		return admData;
	}
}
