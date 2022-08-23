package at.smartshop.pages;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.aventstack.extentreports.Status;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import at.framework.browser.Factory;
import at.framework.files.JsonFile;
import at.framework.generic.CustomisedAssert;
import at.framework.reportsetup.ExtFactory;
import at.framework.ui.Foundation;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.keys.Reports;
import at.smartshop.tests.TestInfra;
import at.smartshop.utilities.WebService;

public class UFSByDevice extends Factory {
	private Foundation foundation = new Foundation();
	private WebService webService = new WebService();
	private JsonFile jsonFunctions = new JsonFile();
	private ReportList reportList = new ReportList();

	public static final By LBL_REPORT_NAME = By
			.cssSelector("#report-container > div:nth-child(3) > div.first-child > label");
	private static final By REPORT_GRID_FIRST_ROW = By.cssSelector("#salestime-Totals > tbody > tr:nth-child(1)");
	private static final By NO_DATA_AVAILABLE_IN_TABLE = By
			.xpath("//*[@id='salestime-Totals']//tbody//tr//td[@class='dataTables_empty']");
	public static final By REPORTS_CONTAINERS = By.id("report-container");
	public static final By TABLE_CASH_FLOW_DETAILS_TOTALS = By.cssSelector("#cashflow-Totals > tbody");
	public static final By LBL_REPORTS_LIST = By.cssSelector("#nav-panel> div> div.title-row");
	public static final By TABLE_CASH_FLOW_DETAILS_TOTAL = By.id("cashflow-Totals");
	public static final By TABLE_SALES_TIME_DETAILS_TOTAL = By.id("salestime-Totals");

	private Map<Integer, Map<String, String>> cashFlowDetailsTotal = new LinkedHashMap<>();
	private Map<Integer, Map<String, String>> cashFlowDetailsTotalsSum = new LinkedHashMap<>();
	private Map<Integer, Map<String, String>> reportsData = new LinkedHashMap<>();
	private Map<Integer, Map<String, String>> reportsTotalData = new LinkedHashMap<>();
	private Map<Integer, Map<String, String>> salesTimeDetailsTotal = new LinkedHashMap<>();
	private Map<Integer, Map<String, String>> salesTimeDetails = new LinkedHashMap<>();
	private Map<Integer, Map<String, String>> initialReportsData = new HashMap<>();
	private Map<Integer, Map<String, String>> initialReportTotals = new HashMap<>();
	private Map<Integer, Map<String, String>> initialSalesTimeDetailsTotal = new LinkedHashMap<>();
	private Map<Integer, Map<String, String>> initialSalesTimeDetails = new LinkedHashMap<>();
	private Map<Integer, Map<String, String>> salesTimeDetailsTotalsSum = new LinkedHashMap<>();
	public static List<String> tableHeaders = new ArrayList<>();
	private List<String> salesTimeHeaders = new ArrayList<>();
	private List<Integer> requiredList = new LinkedList<>();
	private List<Double> requiredJsonData = new LinkedList<>();
	private List<Integer> countDetails = new LinkedList<>();
	private List<LocalDateTime> transDates = new LinkedList<>();
	private List<Integer> recordsDetails = new LinkedList<>();
	private List<String> depositDetails = new LinkedList<>();
	private List<String> discountDetails = new LinkedList<>();
	private List<String> intialReportsDatas = new ArrayList<>();
	private Map<String, Object> data = new HashMap<>();
	private int rowCount = 0;

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
	 * Check For Data Availability in Result Table
	 */
	public void checkForDataAvailabilyInResultTable() {
		try {
			if (foundation.isDisplayed(REPORT_GRID_FIRST_ROW)) {
				foundation.scrollIntoViewElement(REPORT_GRID_FIRST_ROW);
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
	 * @param date
	 * @param location
	 */
	public void selectAndRunReport(String reportName, String date, String location) {
		List<String> name = Arrays.asList(reportName.split(Constants.DELIMITER_HASH));
		reportList.selectReport(name.get(0));
		reportList.selectDate(date);
		reportList.selectLocation(location);
		foundation.threadWait(Constants.SHORT_TIME);
		foundation.click(ReportList.BTN_RUN_REPORT);
		foundation.waitforElement(ProductSales.LBL_REPORT_NAME, Constants.SHORT_TIME);
		verifyReportName(name.get(1));
	}

	/**
	 * Calculate Integer Total
	 * 
	 * @param columnName
	 */
	public void calculateIntegerTotal(String columnName) {
		int rowSize = cashFlowDetailsTotal.size();
		int totalValue = 0;
		for (int iter = 0; iter < rowSize; iter++) {
			int value = Integer.parseInt(cashFlowDetailsTotal.get(iter).get(columnName)
					.replaceAll(Constants.REPLACE_DOLLOR, Constants.EMPTY_STRING));
			totalValue = totalValue + value;
		}
		cashFlowDetailsTotalsSum.get(0).put(columnName, String.valueOf(totalValue));
	}

	/**
	 * Calculate Double Total
	 * 
	 * @param columnName
	 */
	public void calculateDoubleTotal(String columnName) {
		int rowSize = cashFlowDetailsTotal.size();
		double totalValue = 0.0;
		for (int iter = 0; iter < rowSize; iter++) {
			double value = Double.parseDouble(cashFlowDetailsTotal.get(iter).get(columnName)
					.replaceAll(Constants.REPLACE_DOLLOR, Constants.EMPTY_STRING));
			totalValue = totalValue + value;
			totalValue = Math.round(totalValue * 100.0) / 100.0;
		}
		cashFlowDetailsTotalsSum.get(0).put(columnName, String.valueOf(totalValue));
	}

	/**
	 * Calculate Cash Flow Details Total
	 */
	public void calculateCashFlowDetailsTotals() {
		WebElement containers = getDriver().findElement(REPORTS_CONTAINERS);
		List<WebElement> tables = containers.findElements(By.cssSelector("div > div > div > table"));
		int counter = 0;
		for (int iter = 0; iter < tables.size(); iter++) {
			Map<String, String> reportsdata = new LinkedHashMap<>();
			String id = tables.get(iter).getAttribute("id");
			if (id.contains("cashflow")) {
				List<WebElement> rows = tables.get(iter).findElements(By.cssSelector("tbody > tr"));
				int count = rows.size();
				if (count == 4) {
					WebElement totalRow = getDriver().findElement(By.cssSelector(
							"#report-container > div > div > div > table > tbody > tr:nth-child(" + count + ")"));
					for (int iterator = 1; iterator < tableHeaders.size() + 1; iterator++) {
						WebElement column = totalRow.findElement(By.cssSelector("td:nth-child(" + iterator + ")"));
						reportsdata.put(tableHeaders.get(iterator - 1), column.getText());
					}
					cashFlowDetailsTotal.put(counter, reportsdata);
					counter++;
				}
			}
		}
	}

	/**
	 * Read all Records from Cash flow Details Table
	 * 
	 * @throws Exception
	 */
	public void readAllRecordsFromCashFlowDetailsTable(String deviceID, String location) throws Exception {
		String locationName = location.replace("@", Constants.DELIMITER_HYPHEN);
		String deviceName = deviceID;
		reportsData.clear();
		int count = 0;
		String tableCashFlowDetails = "cashflow-" + locationName + Constants.DELIMITER_HYPHEN + deviceName;
		WebElement tableReports = getDriver().findElement(By.id(tableCashFlowDetails));
		List<WebElement> headers = tableReports.findElements(By.tagName("th"));
		tableHeaders.clear();
		for (WebElement header : headers) {
			tableHeaders.add(header.getText());
		}
		WebElement tableReportsList = getDriver()
				.findElement(By.cssSelector(Constants.DELIMITER_HASH + tableCashFlowDetails + " > tbody"));
		List<WebElement> rows = tableReportsList.findElements(By.tagName("tr"));
		for (WebElement row : rows) {
			Map<String, String> reportsdata = new LinkedHashMap<>();
			for (int iter = 1; iter < tableHeaders.size() + 1; iter++) {
				if ((iter - 1) > 1) {
					WebElement column = row.findElement(By.cssSelector("td:nth-child(" + iter + ")"));
					reportsdata.put(tableHeaders.get(iter - 1),
							column.getText().replaceAll(Constants.REPLACE_DOLLOR, Constants.EMPTY_STRING));
				} else {
					WebElement column = row.findElement(By.cssSelector("td:nth-child(" + iter + ")"));
					reportsdata.put(tableHeaders.get(iter - 1), column.getText());
				}
			}
			reportsData.put(count, reportsdata);
			count++;
		}
		WebElement tableReportTotalsList = getDriver().findElement(TABLE_CASH_FLOW_DETAILS_TOTALS);
		List<WebElement> records = tableReportTotalsList.findElements(By.tagName("tr"));
		reportsTotalData.clear();
		int counter = 0;
		for (WebElement record : records) {
			Map<String, String> reportTotalsdata = new LinkedHashMap<>();
			for (int iter = 1; iter < tableHeaders.size() + 1; iter++) {
				WebElement column = record.findElement(By.cssSelector("td:nth-child(" + iter + ")"));
				if ((iter - 1) >= 1) {
					reportTotalsdata.put(tableHeaders.get(iter - 1),
							column.getText().replaceAll(Constants.REPLACE_DOLLOR, Constants.EMPTY_STRING));
				} else {
					reportTotalsdata.put(tableHeaders.get(iter - 1), column.getText());
				}
			}
			reportsTotalData.put(counter, reportTotalsdata);
			counter++;
		}
	}

	/**
	 * Calculate Sales Time Details Total
	 */
	public void calculateSalesTimeDetailsTotals() {
		WebElement containers = getDriver().findElement(REPORTS_CONTAINERS);
		List<WebElement> tables = containers.findElements(By.cssSelector("div > div > div > table"));
		int counter = 0;
		for (int iter = 0; iter < tables.size(); iter++) {
			Map<String, String> reportsdata = new LinkedHashMap<>();
			String id = tables.get(iter).getAttribute("id");
			if (id.contains("salestime")) {
				List<WebElement> rows = tables.get(iter).findElements(By.cssSelector("tbody > tr"));
				int count = rows.size();
				if (count == 5) {
					WebElement totalRow = getDriver().findElement(By.cssSelector(
							"#report-container > div > div > div > table > tbody > tr:nth-child(" + count + ")"));
					for (int iterator = 1; iterator < salesTimeHeaders.size() + 1; iterator++) {
						WebElement column = totalRow.findElement(By.cssSelector("td:nth-child(" + iterator + ")"));
						reportsdata.put(salesTimeHeaders.get(iterator - 1), column.getText());
					}
					salesTimeDetailsTotal.put(counter, reportsdata);
					counter++;
				}
			}
		}
	}

	/**
	 * Read Sales Time Details Table
	 * 
	 * @throws Exception
	 */
	public void readSalesTimeDetailsTable(String deviceID, String location) throws Exception {
		String locName = location.replace("@", Constants.DELIMITER_HYPHEN);
		String deviceName = deviceID;
		salesTimeHeaders.clear();
		String tableSalesTimeDetails = "salestime-" + locName + Constants.DELIMITER_HYPHEN + deviceName;
		WebElement tableReports = getDriver().findElement(By.id(tableSalesTimeDetails));
		List<WebElement> headers = tableReports.findElements(By.cssSelector("thead > tr > th"));
		for (WebElement header : headers) {
			salesTimeHeaders.add(header.getText());
		}
		WebElement tableReportsList = getDriver()
				.findElement(By.cssSelector(Constants.DELIMITER_HASH + tableSalesTimeDetails + " > tbody"));
		List<WebElement> rows = tableReportsList.findElements(By.tagName("tr"));
		salesTimeDetails.clear();
		int count = 0;
		for (WebElement row : rows) {
			Map<String, String> reportsdata = new LinkedHashMap<>();
			for (int iter = 1; iter < salesTimeHeaders.size() + 1; iter++) {
				WebElement column = row.findElement(By.cssSelector("td:nth-child(" + iter + ")"));
				reportsdata.put(salesTimeHeaders.get(iter - 1),
						column.getText().replaceAll(Constants.REPLACE_DOLLOR, Constants.EMPTY_STRING));
			}
			salesTimeDetails.put(count, reportsdata);
			count++;
		}
		salesTimeDetailsTotal.clear();
		int counter = 0;
		WebElement tableSalesTimeTotal = getDriver().findElement(By.cssSelector("#salestime-Totals > tbody"));
		List<WebElement> rowList = tableSalesTimeTotal.findElements(By.tagName("tr"));
		for (WebElement row : rowList) {
			Map<String, String> reportsdata = new LinkedHashMap<>();
			for (int iter = 1; iter < salesTimeHeaders.size() + 1; iter++) {
				WebElement column = row.findElement(By.cssSelector("td:nth-child(" + iter + ")"));
				if ((iter - 1) >= 1) {
					reportsdata.put(salesTimeHeaders.get(iter - 1),
							column.getText().replaceAll(Constants.REPLACE_DOLLOR, Constants.EMPTY_STRING));
				} else {
					reportsdata.put(salesTimeHeaders.get(iter - 1), column.getText());
				}
			}
			salesTimeDetailsTotal.put(counter, reportsdata);
			counter++;
		}
	}

	/**
	 * Verify Cash Flow Headers
	 * 
	 * @param columnNames
	 */
	public void verifyCashFlowHeaders(String columnNames) {
		List<String> columnName = Arrays.asList(columnNames.split(Constants.DELIMITER_HASH));
		for (int iter = 0; iter < tableHeaders.size(); iter++) {
			System.out.println(tableHeaders.get(iter));
			CustomisedAssert.assertTrue(tableHeaders.get(iter).equals(columnName.get(iter)));
		}
	}

	/**
	 * Get Sub Headers Count
	 * 
	 * @param deviceID
	 * @param location
	 * @param columnName
	 * @param columnValue
	 * @return
	 * @throws Exception
	 */
	public int getSubHeaderCount(String deviceID, String location, String columnName, String columnValue)
			throws Exception {
		String locationName = location.replace("@", Constants.DELIMITER_HYPHEN);
		String deviceName = deviceID;
		String tableCashFlowDetails = "cashflow-" + locationName + Constants.DELIMITER_HYPHEN + deviceName + "_wrapper";
		WebElement tableReports = getDriver().findElement(By.id(tableCashFlowDetails));
		List<WebElement> headers = tableReports.findElements(By.tagName("th"));
		int counter = 0;
		int subHeaderCount = 0;
		for (WebElement header : headers) {
			counter++;
			if (header.getText().equalsIgnoreCase(columnName)) {
				subHeaderCount = counter;
				break;
			}
		}
		int count = 0;
		int headerCount = 0;
		WebElement tableReportsList = getDriver()
				.findElement(By.cssSelector(Constants.DELIMITER_HASH + tableCashFlowDetails + ">div>table>tbody"));
		List<WebElement> rows = tableReportsList.findElements(By.tagName("tr"));
		for (WebElement row : rows) {
			headerCount++;
			WebElement subHeaderColumn = row.findElement(By.cssSelector("td:nth-child(" + subHeaderCount + ")"));
			if (subHeaderColumn.getText().equals(columnValue)) {
				count = headerCount;
				break;
			}
		}
		return count;
	}

	/**
	 * Get Header Count
	 * 
	 * @param columnName
	 * @param reqColumnName
	 * @param columnValue
	 * @param reqColumnValue
	 * @throws Exception
	 */
	public void getHeaderCounts(String deviceID, String location, String columnName, String reqColumnName,
			String columnValue, String reqColumnValue) throws Exception {
		String locationName = location.replace("@", Constants.DELIMITER_HYPHEN);
		String deviceName = deviceID;
		String tableCashFlowDetails = "cashflow-" + locationName + Constants.DELIMITER_HYPHEN + deviceName;
		requiredList.clear();
		WebElement tableReports = getDriver().findElement(By.id(tableCashFlowDetails));
		List<WebElement> headers = tableReports.findElements(By.tagName("th"));
		int counter = 0;
		int reqColumnCount = 0;
		int subHeaderCount = 0;
		for (WebElement header : headers) {
			counter++;
			if (header.getText().equalsIgnoreCase(columnName)) {
				subHeaderCount = counter;
			}
			if (header.getText().equalsIgnoreCase(reqColumnName)) {
				reqColumnCount = counter;
				break;
			}
		}
		int count = 0;
		int reqCount = 0;
		WebElement tableReportsList = getDriver()
				.findElement(By.cssSelector(Constants.DELIMITER_HASH + tableCashFlowDetails + " > tbody"));
		List<WebElement> rows = tableReportsList.findElements(By.tagName("tr"));
		int headerCount = 0;
		for (WebElement row : rows) {
			headerCount++;
			WebElement subHeaderColumn = row.findElement(By.cssSelector("td:nth-child(" + subHeaderCount + ")"));
			WebElement reqColumn = row.findElement(By.cssSelector("td:nth-child(" + reqColumnCount + ")"));
			if (subHeaderColumn.getText().equals(columnValue)) {
				count = headerCount;
			}
			if (reqColumn.getText().equals(reqColumnValue)) {
				reqCount = headerCount;
				break;
			}
		}
		requiredList.add(count);
		requiredList.add(reqCount);
	}

	/**
	 * Verify Sales Time Headers
	 * 
	 * @param columnNames
	 */
	public void verifySalesTimeHeaders(String columnNames) {
		List<String> columnName = Arrays.asList(columnNames.split(Constants.DELIMITER_HASH));
		for (int iter = 0; iter < salesTimeHeaders.size(); iter++) {
			System.out.println(salesTimeHeaders.get(iter));
			CustomisedAssert.assertTrue(salesTimeHeaders.get(iter).equals(columnName.get(iter)));
		}
	}

	/**
	 * Calculate Credit Card SubTotal Counts
	 * 
	 * @param columnName
	 * @param columnValue
	 * @throws Exception
	 */
	public void calculateCreditCardSubTotalCounts(String deviceID, String location, String columnName,
			String columnValue) throws Exception {
		List<String> colName = Arrays.asList(columnName.split(Constants.DELIMITER_HASH));
		int count = getSubHeaderCount(deviceID, location, colName.get(0), columnValue);
		int counter = initialReportsData.size();
		int payCount = 0;
		String updatedPayCount = Constants.EMPTY_STRING;
		for (int iter = 0; iter < counter; iter++) {
			if (initialReportsData.get(iter).get(tableHeaders.get(0)).contains("Credit")
					&& (!(initialReportsData.get(iter).get(tableHeaders.get(0))).equals("Credit Card Sub Total"))) {
				String paySubCount = reportsData.get(iter).get(colName.get(1));
				payCount = payCount + Integer.parseInt(paySubCount);
				updatedPayCount = String.valueOf(payCount);
			}
		}
		initialReportsData.get(count - 1).put(colName.get(1), updatedPayCount);
	}

	/**
	 * Calculate Credit Card Sub total Amounts
	 * 
	 * @param columnName
	 * @param columnValue
	 * @throws Exception
	 */
	public void calculateCreditCardSubTotalAmounts(String deviceId, String location, String columnName,
			String columnValue) throws Exception {
		List<String> colName = Arrays.asList(columnName.split(Constants.DELIMITER_HASH));
		int count = getSubHeaderCount(deviceId, location, colName.get(0), columnValue);
		int counter = initialReportsData.size();
		double payAmount = 0;
		for (int iter = 0; iter < counter; iter++) {
			if (initialReportsData.get(iter).get(tableHeaders.get(0)).contains("Credit")
					&& (!initialReportsData.get(iter).get(tableHeaders.get(0)).equals("Credit Card Sub Total"))) {
				String paySubAmount = reportsData.get(iter).get(colName.get(1)).replaceAll(Constants.REPLACE_DOLLOR,
						Constants.EMPTY_STRING);
				payAmount = payAmount
						+ Double.parseDouble(paySubAmount.replaceAll(Constants.REPLACE_DOLLOR, Constants.EMPTY_STRING));
				payAmount = Math.round(payAmount * 100.0) / 100.0;
			}
		}
		initialReportsData.get(count - 1).put(colName.get(1), String.valueOf(payAmount));
	}

	/**
	 * Calculate Location Total Amounts
	 * 
	 * @param columnName
	 * @param columnValue
	 * @throws Exception
	 */
	public void calculateLocationTotalsAmounts(String columnName, String columnValue) throws Exception {
		double totalAmount = 0;
		double amount = 0;
		double subCreditTotal = 0;
		List<String> colName = Arrays.asList(columnName.split(Constants.DELIMITER_HASH));
		List<String> colValue = Arrays.asList(columnValue.split(Constants.DELIMITER_HASH));
		int counter = initialReportsData.size() - 1;
		for (int iter = 0; iter < counter; iter++) {
			if (!initialReportsData.get(iter).get(tableHeaders.get(0)).contains("Credit")) {
				amount = Double.parseDouble(reportsData.get(iter).get(colName.get(1))
						.replaceAll(Constants.REPLACE_DOLLOR, Constants.EMPTY_STRING));
				totalAmount = totalAmount + amount;
			}
			if (initialReportsData.get(iter).get(tableHeaders.get(0)).contains(colValue.get(0))) {
				subCreditTotal = Double.parseDouble(reportsData.get(iter).get(colName.get(1))
						.replaceAll(Constants.REPLACE_DOLLOR, Constants.EMPTY_STRING));
			}
		}
		double updatedAmount = totalAmount + subCreditTotal;
		updatedAmount = Math.round(updatedAmount * 100.0) / 100.0;
		initialReportsData.get(counter).put(colName.get(1), String.valueOf(updatedAmount));
	}

	/**
	 * Calculate Location Total Counts
	 * 
	 * @param columnName
	 * @param columnValue
	 * @throws Exception
	 */
	public void calculateLocationTotalCounts(String columnName, String columnValue) throws Exception {
		int totalCount = 0;
		int count = 0;
		int subCreditTotal = 0;
		List<String> colName = Arrays.asList(columnName.split(Constants.DELIMITER_HASH));
		List<String> colValue = Arrays.asList(columnValue.split(Constants.DELIMITER_HASH));
		int counter = initialReportsData.size() - 1;
		for (int iter = 0; iter < counter; iter++) {
			if (!initialReportsData.get(iter).get(tableHeaders.get(0)).contains("Credit")) {
				count = Integer.parseInt(reportsData.get(iter).get(colName.get(1)));
				totalCount = totalCount + count;
			}
			if (initialReportsData.get(iter).get(tableHeaders.get(0)).contains(colValue.get(0))) {
				subCreditTotal = Integer.parseInt(reportsData.get(iter).get(colName.get(1)));
			}
		}
		int updatedCount = totalCount + subCreditTotal;
		initialReportsData.get(counter).put(colName.get(1), String.valueOf(updatedCount));
	}

	/**
	 * Calculate Counts
	 * 
	 * @param columnName
	 * @param columnValue
	 * @param count
	 * @throws Exception
	 */
	public void calculateCounts(String deviceId, String location, String columnName, String columnValue, int count)
			throws Exception {
		List<String> colName = Arrays.asList(columnName.split(Constants.DELIMITER_HASH));
		System.out.println("a1");
		System.out.println(initialReportsData);
		System.out.println(getSubHeaderCount(deviceId, location, colName.get(0), columnValue));
		String initialCounts = initialReportsData
				.get(getSubHeaderCount(deviceId, location, colName.get(0), columnValue) - 1).get(colName.get(1));
		System.out.println(initialCounts);
		int updatedCounts = Integer.parseInt(initialCounts) + count;
		System.out.println("a3");
		initialReportsData.get(getSubHeaderCount(deviceId, location, colName.get(0), columnValue) - 1)
				.put(colName.get(1), String.valueOf(updatedCounts));
		System.out.println("a4");
	}

	/**
	 * Calculate Amounts
	 * 
	 * @param columnName
	 * @param columnValue
	 * @throws Exception
	 */
	public void calculateAmounts(String deviceId, String location, String columnName, String columnValue)
			throws Exception {
		List<String> colName = Arrays.asList(columnName.split(Constants.DELIMITER_HASH));
		String initialAmounts = initialReportsData
				.get(getSubHeaderCount(deviceId, location, colName.get(0), columnValue) - 1).get(colName.get(1));
		double amount = requiredJsonData.get(0) + requiredJsonData.get(1);
		double updatedAmounts = Double
				.parseDouble(initialAmounts.replaceAll(Constants.REPLACE_DOLLOR, Constants.EMPTY_STRING)) + amount;
		updatedAmounts = Math.round(updatedAmounts * 100.0) / 100.0;
		initialReportsData.get(getSubHeaderCount(deviceId, location, colName.get(0), columnValue) - 1)
				.put(colName.get(1), String.valueOf(updatedAmounts));
	}

	/**
	 * Calculate Declined Amounts
	 * 
	 * @param columnName
	 * @param columnValue
	 * @throws Exception
	 */
	public void calculateDeclinedAmounts(String deviceId, String location, String columnName, String columnValue)
			throws Exception {
		double updatedAmounts = 0;
		List<String> colName = Arrays.asList(columnName.split(Constants.DELIMITER_HASH));
		String initialAmounts = initialReportsData
				.get(getSubHeaderCount(deviceId, location, colName.get(0), columnValue) - 1).get(colName.get(1));
		double amount = requiredJsonData.get(0) + requiredJsonData.get(1);
		if (initialAmounts.contains("0.00")) {
			updatedAmounts = Math.round(updatedAmounts * 100.0) / 100.0;
			initialReportsData.get(getSubHeaderCount(deviceId, location, colName.get(0), columnValue) - 1)
					.put(colName.get(1), String.valueOf(updatedAmounts));
		} else {
			updatedAmounts = Double
					.parseDouble(initialAmounts.replaceAll(Constants.REPLACE_DOLLOR, Constants.EMPTY_STRING)) + amount;
			updatedAmounts = Math.round(updatedAmounts * 100.0) / 100.0;
			initialReportsData.get(getSubHeaderCount(deviceId, location, colName.get(0), columnValue) - 1)
					.put(colName.get(1), String.valueOf(updatedAmounts));
		}
	}

	/**
	 * Calculate Total Column Data
	 * 
	 * @param columnName
	 * @param columnValue
	 * @throws Exception
	 */
	public void calculateTotalsColumnData(String deviceId, String location, String columnName, String columnValue)
			throws Exception {
		String total = Constants.EMPTY_STRING;
		List<String> colName = Arrays.asList(columnName.split(Constants.DELIMITER_HASH));
		String sales = reportsData.get(getSubHeaderCount(deviceId, location, colName.get(0), columnValue) - 1)
				.get(colName.get(1));
		String taxes = reportsData.get(getSubHeaderCount(deviceId, location, colName.get(0), columnValue) - 1)
				.get(colName.get(2));
		String tipsAmount = reportsData.get(getSubHeaderCount(deviceId, location, colName.get(0), columnValue) - 1)
				.get(colName.get(3));
		double totals = Double.parseDouble(tipsAmount.replaceAll(Constants.REPLACE_DOLLOR, Constants.EMPTY_STRING))
				+ Double.parseDouble(sales.replaceAll(Constants.REPLACE_DOLLOR, Constants.EMPTY_STRING))
				+ Double.parseDouble(taxes.replaceAll(Constants.REPLACE_DOLLOR, Constants.EMPTY_STRING));
		totals = Math.round(totals * 100.0) / 100.0;
		if (totals < 0) {
			total = String.valueOf(totals).replaceAll(Constants.DELIMITER_HYPHEN, Constants.EMPTY_STRING);
		} else {
			total = String.valueOf(totals);
		}
		initialReportsData.get(getSubHeaderCount(deviceId, location, colName.get(0), columnValue) - 1)
				.put(colName.get(4), String.valueOf(total));
	}

	/**
	 * Calculate Location Sales
	 * 
	 * @param columnName
	 * @param columnValue
	 * @throws Exception
	 */
	public void calculateLocationSales(String deviceId, String location, String columnName, String columnValue)
			throws Exception {
		List<String> colName = Arrays.asList(columnName.split(Constants.DELIMITER_HASH));
		String paymentAmounts = reportsData.get(getSubHeaderCount(deviceId, location, colName.get(0), columnValue) - 1)
				.get(colName.get(1));
		String voidAmounts = reportsData.get(getSubHeaderCount(deviceId, location, colName.get(0), columnValue) - 1)
				.get(colName.get(2));
		String taxes = reportsData.get(getSubHeaderCount(deviceId, location, colName.get(0), columnValue) - 1)
				.get(colName.get(3));
		double sales = Double.parseDouble(paymentAmounts.replaceAll(Constants.REPLACE_DOLLOR, Constants.EMPTY_STRING))
				- Double.parseDouble(voidAmounts.replaceAll(Constants.REPLACE_DOLLOR, Constants.EMPTY_STRING))
				- Double.parseDouble(taxes.replaceAll(Constants.REPLACE_DOLLOR, Constants.EMPTY_STRING));
		sales = Math.round(sales * 100.0) / 100.0;
		initialReportsData.get(getSubHeaderCount(deviceId, location, colName.get(0), columnValue) - 1)
				.put(colName.get(4), String.valueOf(sales));
		String newSales = reportsData.get(getSubHeaderCount(deviceId, location, colName.get(0), columnValue) - 1)
				.get(colName.get(4)).replaceAll(Constants.REPLACE_DOLLOR, Constants.EMPTY_STRING);
		reportsData.get(getSubHeaderCount(deviceId, location, colName.get(0), columnValue) - 1).put(colName.get(4),
				String.valueOf(newSales));
	}

	/**
	 * Calculate Location Tax
	 * 
	 * @param columnName
	 * @param columnValue
	 * @throws Exception
	 */
	public void calculateLocationTax(String deviceId, String location, String columnName, String columnValue)
			throws Exception {
		List<String> colName = Arrays.asList(columnName.split(Constants.DELIMITER_HASH));
		String paymentCounts = reportsData.get(getSubHeaderCount(deviceId, location, colName.get(0), columnValue) - 1)
				.get(colName.get(1));
		String voidCounts = reportsData.get(getSubHeaderCount(deviceId, location, colName.get(0), columnValue) - 1)
				.get(colName.get(2));
		double taxes = requiredJsonData.get(1)
				* (Double.parseDouble(paymentCounts.replaceAll(Constants.REPLACE_DOLLOR, Constants.EMPTY_STRING))
						- Double.parseDouble(voidCounts.replaceAll(Constants.REPLACE_DOLLOR, Constants.EMPTY_STRING)));
		taxes = Math.round(taxes * 100.0) / 100.0;
		initialReportsData.get(getSubHeaderCount(deviceId, location, colName.get(0), columnValue) - 1)
				.put(colName.get(3), String.valueOf(taxes));
		String newTax = reportsData.get(getSubHeaderCount(deviceId, location, colName.get(0), columnValue) - 1)
				.get(colName.get(3)).replaceAll(Constants.REPLACE_DOLLOR, Constants.EMPTY_STRING);
		reportsData.get(getSubHeaderCount(deviceId, location, colName.get(0), columnValue) - 1).put(colName.get(3),
				String.valueOf(newTax));
	}

	/**
	 * Update Total Grid Value
	 * 
	 * @param columnName
	 * @param columnValue
	 * @throws Exception
	 */
	public void updateTotalsGridValue(String deviceId, String location, String columnName, String columnValue)
			throws Exception {
		List<String> colName = Arrays.asList(columnName.split(Constants.DELIMITER_HASH));
		initialReportTotals.get(0)
				.putAll(initialReportsData.get(getSubHeaderCount(deviceId, location, colName.get(0), columnValue) - 1));
	}

	/**
	 * Calculate Total Grid Counts
	 * 
	 * @param columnName
	 * @param columnValue
	 * @param count
	 * @throws Exception
	 */
	public void calculateTotalsGridCounts(String deviceId, String location, String columnName, String columnValue,
			int count) throws Exception {
		List<String> colName = Arrays.asList(columnName.split(Constants.DELIMITER_HASH));
		String initialCounts = initialReportTotals
				.get(getSubHeaderCount(deviceId, location, colName.get(0), columnValue) - 1).get(colName.get(1));
		int updatedCounts = Integer.parseInt(initialCounts) + count;
		initialReportTotals.get(getSubHeaderCount(deviceId, location, colName.get(0), columnValue) - 1)
				.put(colName.get(1), String.valueOf(updatedCounts));
	}

	/**
	 * Calculate Total Grid Amounts
	 * 
	 * @param columnName
	 * @param columnValue
	 * @throws Exception
	 */
	public void calculateTotalsGridAmounts(String deviceId, String location, String columnName, String columnValue)
			throws Exception {
		List<String> colName = Arrays.asList(columnName.split(Constants.DELIMITER_HASH));
		String initialAmounts = initialReportTotals
				.get(getSubHeaderCount(deviceId, location, colName.get(0), columnValue) - 1).get(colName.get(1));
		double amount = requiredJsonData.get(0) + requiredJsonData.get(1);
		double updatedAmounts = Double
				.parseDouble(initialAmounts.replaceAll(Constants.REPLACE_DOLLOR, Constants.EMPTY_STRING)) + amount;
		updatedAmounts = Math.round(updatedAmounts * 100.0) / 100.0;
		initialReportTotals.get(getSubHeaderCount(deviceId, location, colName.get(0), columnValue) - 1)
				.put(colName.get(1), String.valueOf(updatedAmounts));
	}

	/**
	 * Verify Cash Flow Report Records
	 */
	public void verifyCashFlowReportRecords() {
		int count = initialReportsData.size();
		int coulumnCount = tableHeaders.size();
		System.out.println(initialReportsData);
		System.out.println(tableHeaders);
		System.out.println(reportsData);
		for (int iter = 0; iter < count; iter++) {
			for (int val = 0; val < coulumnCount; val++) {
				System.out.println(reportsData.get(iter).get(tableHeaders.get(val)) + "**"
						+ initialReportsData.get(iter).get(tableHeaders.get(val)).replaceAll(Constants.REPLACE_DOLLOR,
								Constants.EMPTY_STRING));
				CustomisedAssert.assertTrue(reportsData.get(iter).get(tableHeaders.get(val))
						.contains(initialReportsData.get(iter).get(tableHeaders.get(val))
								.replaceAll(Constants.REPLACE_DOLLOR, Constants.EMPTY_STRING)));
			}
		}
		for (int val = 0; val < coulumnCount; val++) {
			System.out.println(reportsTotalData.get(0).get(tableHeaders.get(val)) + "**" + cashFlowDetailsTotalsSum
					.get(0).get(tableHeaders.get(val)).replaceAll(Constants.REPLACE_DOLLOR, Constants.EMPTY_STRING));
			CustomisedAssert.assertTrue(
					reportsTotalData.get(0).get(tableHeaders.get(val)).contains(cashFlowDetailsTotalsSum.get(0)
							.get(tableHeaders.get(val)).replaceAll(Constants.REPLACE_DOLLOR, Constants.EMPTY_STRING)));
		}
	}

	/**
	 * Json Array Data Update
	 * 
	 * @param jsonObj
	 * @param reqString
	 * @param transID
	 * @param salesheader
	 * @param transDate
	 * @param transStatus
	 * @param paymentType
	 */
	public void jsonArrayDataUpdate(JsonObject jsonObj, String reqString, String transID, String salesheader,
			String transDate, String transStatus, String paymentType) {
		JsonArray jsonarray = jsonObj.get(reqString).getAsJsonArray();
		for (JsonElement jsonarr : jsonarray) {
			JsonObject element = jsonarr.getAsJsonObject();
			element.addProperty(ReportList.ID,
					UUID.randomUUID().toString().replace(Constants.DELIMITER_HYPHEN, Constants.EMPTY_STRING));
			element.addProperty(ReportList.SALES_HEADER, salesheader);
			element.addProperty(ReportList.TRANS_ID, transID);
			element.addProperty(ReportList.TRANS_DATE, transDate);
		}
		if (reqString.equals(ReportList.PAYMENTS)) {
			for (JsonElement jsonarr : jsonarray) {
				JsonObject element = jsonarr.getAsJsonObject();
				element.addProperty(ReportList.STATUS, transStatus);
				element.addProperty(ReportList.TYPE, paymentType);
			}
		}
	}

	/**
	 * Process API
	 * 
	 * @param transStatus
	 * @param paymentType
	 * @throws Exception
	 */
	public void processAPI(String deviceId, String transStatus, String paymentType) throws Exception {
		countDetails.clear();
		int credCount = 0;
		int voidCredCount = 0;
		int declinedCredCount = 0;
		int declinedAccountCount = 0;
		int accCount = 0;
		int accVoidCount = 0;
		int tipCount = 0;
		int cashCount = 0;
		int cashVoidCount = 0;
		int declinedCashCount = 0;
		int sogoCount = 0;
		int sogoVoidCount = 0;
		int declinedSoGoCount = 0;
		int compCount = 0;
		int voidCompCount = 0;
		int declinedCompCount = 0;
		int guestPassCount = 0;
		int guestPassVoidCount = 0;
		int declinedguestPassCount = 0;
		List<String> tStatus = Arrays.asList(transStatus.split(Constants.DELIMITER_HASH));
		List<String> payType = Arrays.asList(paymentType.split(Constants.DELIMITER_HASH));
		transDates.clear();
		for (int iterator = 0; iterator < payType.size(); iterator++) {
			for (int iter = 0; iter < tStatus.size(); iter++) {
				salesJsonDataUpdate(deviceId, tStatus.get(iter), payType.get(iterator));
				webService.apiReportPostRequest(
						propertyFile.readPropertyFile(Configuration.TRANS_SALES, FilePath.PROPERTY_CONFIG_FILE),
						(String) data.get(Reports.JSON));
				LocalDateTime transDate = (LocalDateTime) data.get(ReportList.TRANS_DATE);
				if (tStatus.get(iter).contentEquals(Constants.ACCEPTED) && !transDates.contains(transDate)) {
					transDates.add(transDate);
				}
				if (tStatus.get(iter).equals(Constants.ACCEPTED) && payType.get(iterator).equals(Constants.CREDIT)) {
					credCount = credCount + 1;
					tipCount = tipCount + 1;
				} else if (tStatus.get(iter).equals(Constants.VOID) && payType.get(iterator).equals(Constants.CREDIT)) {
					voidCredCount = voidCredCount + 1;
				} else if (tStatus.get(iter).equals(Constants.REJECTED)
						&& payType.get(iterator).equals(Constants.CREDIT)) {
					declinedCredCount = declinedCredCount + 1;
				} else if (tStatus.get(iter).equals(Constants.ACCEPTED)
						&& payType.get(iterator).equals(Constants.ACCOUNT)) {
					accCount = accCount + 1;
				} else if (tStatus.get(iter).equals(Constants.VOID)
						&& payType.get(iterator).equals(Constants.ACCOUNT)) {
					accVoidCount = accVoidCount + 1;
				} else if (tStatus.get(iter).equals(Constants.REJECTED)
						&& payType.get(iterator).equals(Constants.ACCOUNT)) {
					declinedAccountCount = declinedAccountCount + 1;
				} else if (tStatus.get(iter).equals(Constants.ACCEPTED)
						&& payType.get(iterator).equals(Constants.CASH)) {
					cashCount = cashCount + 1;
				} else if (tStatus.get(iter).equals(Constants.VOID) && payType.get(iterator).equals(Constants.CASH)) {
					cashVoidCount = cashVoidCount + 1;
				} else if (tStatus.get(iter).equals(Constants.REJECTED)
						&& payType.get(iterator).equals(Constants.CASH)) {
					declinedCashCount = declinedCashCount + 1;
				} else if (tStatus.get(iter).equals(Constants.ACCEPTED)
						&& payType.get(iterator).equals(Constants.SOGO)) {
					sogoCount = sogoCount + 1;
				} else if (tStatus.get(iter).equals(Constants.VOID) && payType.get(iterator).equals(Constants.SOGO)) {
					sogoVoidCount = sogoVoidCount + 1;
				} else if (tStatus.get(iter).equals(Constants.REJECTED)
						&& payType.get(iterator).equals(Constants.SOGO)) {
					declinedSoGoCount = declinedSoGoCount + 1;
				} else if (tStatus.get(iter).equals(Constants.ACCEPTED)
						&& payType.get(iterator).equals(Constants.COMP)) {
					compCount = compCount + 1;
				} else if (tStatus.get(iter).equals(Constants.VOID) && payType.get(iterator).equals(Constants.COMP)) {
					voidCompCount = voidCompCount + 1;
				} else if (tStatus.get(iter).equals(Constants.REJECTED)
						&& payType.get(iterator).equals(Constants.COMP)) {
					declinedCompCount = declinedCompCount + 1;
				} else if (tStatus.get(iter).equals(Constants.ACCEPTED)
						&& payType.get(iterator).equals(Constants.GUESTPASS)) {
					guestPassCount = guestPassCount + 1;
				} else if (tStatus.get(iter).equals(Constants.VOID)
						&& payType.get(iterator).equals(Constants.GUESTPASS)) {
					guestPassVoidCount = guestPassVoidCount + 1;
				} else if (tStatus.get(iter).equals(Constants.REJECTED)
						&& payType.get(iterator).equals(Constants.GUESTPASS)) {
					declinedguestPassCount = declinedguestPassCount + 1;
				}
			}
		}
		countDetails.add(cashCount);
		countDetails.add(cashVoidCount);
		countDetails.add(declinedCashCount);
		countDetails.add(credCount);
		countDetails.add(accCount);
		countDetails.add(tipCount);
		countDetails.add(accVoidCount);
		countDetails.add(voidCredCount);
		countDetails.add(declinedCredCount);
		countDetails.add(declinedAccountCount);
		countDetails.add(sogoCount);
		countDetails.add(sogoVoidCount);
		countDetails.add(declinedSoGoCount);
		countDetails.add(compCount);
		countDetails.add(voidCompCount);
		countDetails.add(declinedCompCount);
		countDetails.add(guestPassCount);
		countDetails.add(guestPassVoidCount);
		countDetails.add(declinedguestPassCount);
		System.out.println(countDetails);
	}

	/**
	 * Get Sales Time Row Details
	 */
	public void getSalesTimeRowDetails() {
		for (int iter = 0; iter < transDates.size(); iter++) {
			decideTimeRange((LocalDateTime) data.get(ReportList.TRANS_DATE));
			recordsDetails.add(rowCount);
		}
	}

	/**
	 * Verify Sales Time Report Records
	 */
	public void verifySalesTimeReportRecords() {
		int count = initialSalesTimeDetails.size();
		int columnCount = salesTimeHeaders.size();
		System.out.println(initialSalesTimeDetails);
		System.out.println(salesTimeHeaders);
		for (int iter = 0; iter < count; iter++) {
			for (int val = 0; val < columnCount; val++) {
				System.out.println(salesTimeDetails.get(0).get(salesTimeHeaders.get(val)) + "**"
						+ initialSalesTimeDetails.get(0).get(salesTimeHeaders.get(val)));
				CustomisedAssert.assertTrue(salesTimeDetails.get(0).get(salesTimeHeaders.get(val))
						.contains(initialSalesTimeDetails.get(0).get(salesTimeHeaders.get(val))));
			}
		}
		for (int val = 1; val < columnCount; val++) {
			System.out.println(salesTimeDetailsTotal.get(0).get(salesTimeHeaders.get(val)) + "**"
					+ initialSalesTimeDetailsTotal.get(0).get(salesTimeHeaders.get(val)));
			CustomisedAssert.assertTrue(salesTimeDetailsTotal.get(0).get(salesTimeHeaders.get(val))
					.contains(initialSalesTimeDetailsTotal.get(0).get(salesTimeHeaders.get(val))));
		}
	}

	/**
	 * Decide Time Range
	 * 
	 * @param transDateTime
	 */
	public void decideTimeRange(LocalDateTime transDateTime) {
		LocalTime transTime = transDateTime.toLocalTime();
		if (transTime.isAfter(LocalTime.MIN) && transTime.isBefore(LocalTime.of(6, 0, 59))) {
			rowCount = 1;
		} else if (transTime.isAfter(LocalTime.of(06, 01, 00)) && transTime.isBefore(LocalTime.of(10, 30, 59))) {
			rowCount = 2;
		} else if (transTime.isAfter(LocalTime.of(10, 31, 00)) && transTime.isBefore(LocalTime.of(14, 00, 59))) {
			rowCount = 3;
		} else if (transTime.isAfter(LocalTime.of(14, 01, 00)) && transTime.isBefore(LocalTime.MAX)) {
			rowCount = 4;
		}
	}

	/**
	 * Update Transactions
	 * 
	 * @param columnName
	 */
	public void updateTransactions(String columnName) {
		for (int iter = 0; iter < recordsDetails.size(); iter++) {
			int initialTransCount = Integer
					.parseInt(initialSalesTimeDetails.get(recordsDetails.get(iter) - 1).get(columnName));
			int updatedTransCount = initialTransCount + 1;
			initialSalesTimeDetails.get(recordsDetails.get(iter) - 1).put(columnName,
					String.valueOf(updatedTransCount));
		}
	}

	/**
	 * Update Transaction Totals
	 * 
	 * @param columnName
	 */
	public void updateTransactionsTotal(String columnName) {
		int count = recordsDetails.size();
		int initialTransCount = Integer.parseInt(initialSalesTimeDetailsTotal.get(0).get(columnName));
		int updatedTransCount = initialTransCount + count;
		initialSalesTimeDetailsTotal.get(0).put(columnName, String.valueOf(updatedTransCount));
	}

	/**
	 * Update Data
	 * 
	 * @param columnName
	 * @param data
	 */
	public void updateData(String columnName, String data) {
		for (int iter = 0; iter < recordsDetails.size(); iter++) {
			double initialData = Double.parseDouble(initialSalesTimeDetails.get(recordsDetails.get(iter) - 1)
					.get(columnName).replaceAll(Constants.REPLACE_DOLLOR, Constants.EMPTY_STRING));
			double updatedData = initialData + Double.parseDouble(data);
			updatedData = Math.round(updatedData * 100.0) / 100.0;
			initialSalesTimeDetails.get(recordsDetails.get(iter) - 1).put(columnName, String.valueOf(updatedData));
		}
	}

	/**
	 * Update Total Data
	 * 
	 * @param columnName
	 * @param data
	 */
	public void updateTotalData(String columnName, String data) {
		int count = recordsDetails.size();
		double initialData = Double.parseDouble(initialSalesTimeDetailsTotal.get(0).get(columnName)
				.replaceAll(Constants.REPLACE_DOLLOR, Constants.EMPTY_STRING));
		double updatedData = initialData + (Double.parseDouble(data) * count);
		updatedData = Math.round(updatedData * 100.0) / 100.0;
		initialSalesTimeDetailsTotal.get(0).put(columnName, String.valueOf(updatedData));
	}

	/**
	 * Calculate Sales Time Integer Total
	 * 
	 * @param columnName
	 */
	public void calculateSalesTimeIntegerTotal(String columnName) {
		int rowSize = salesTimeDetailsTotal.size();
		int totalValue = 0;
		for (int iter = 0; iter < rowSize; iter++) {
			int value = Integer.parseInt(salesTimeDetailsTotal.get(iter).get(columnName)
					.replaceAll(Constants.REPLACE_DOLLOR, Constants.EMPTY_STRING));
			totalValue = totalValue + value;
		}
		salesTimeDetailsTotalsSum.get(0).put(columnName, String.valueOf(totalValue));
	}

	/**
	 * Calculate Sales Time Double Total
	 * 
	 * @param columnName
	 */
	public void calculateSalesTimeDoubleTotal(String columnName) {
		int rowSize = salesTimeDetailsTotal.size();
		double totalValue = 0.0;
		for (int iter = 0; iter < rowSize; iter++) {
			double value = Double.parseDouble(salesTimeDetailsTotal.get(iter).get(columnName)
					.replaceAll(Constants.REPLACE_DOLLOR, Constants.EMPTY_STRING));
			totalValue = totalValue + value;
			totalValue = Math.round(totalValue * 100.0) / 100.0;
		}
		salesTimeDetailsTotalsSum.get(0).put(columnName, String.valueOf(totalValue));
	}

	/**
	 * Get JSON Sales Data
	 */
	public void getJsonSalesData() {
		JsonObject sales = (JsonObject) data.get(Reports.SALES);
		String subTotal = sales.get(Reports.SUBTOTAL).getAsString();
		requiredJsonData.add(Double.parseDouble(subTotal));
		String tax = sales.get(Reports.TAX).getAsString();
		requiredJsonData.add(Double.parseDouble(tax));
		String discount = sales.get(Reports.DISCOUNT).getAsString();
		requiredJsonData.add(Double.parseDouble(discount));
	}

	/**
	 * Update Sales Incurred Tax
	 * 
	 * @param columnName
	 */
	public void updateSalesIncTax(String columnName) {
		for (int iter = 0; iter < recordsDetails.size(); iter++) {
			List<String> colName = Arrays.asList(columnName.split(Constants.DELIMITER_HASH));
			double sales = Double.parseDouble(salesTimeDetails.get(recordsDetails.get(iter) - 1).get(colName.get(0))
					.replaceAll(Constants.REPLACE_DOLLOR, Constants.EMPTY_STRING));
			double taxes = Double.parseDouble(salesTimeDetails.get(recordsDetails.get(iter) - 1).get(colName.get(1))
					.replaceAll(Constants.REPLACE_DOLLOR, Constants.EMPTY_STRING));
			double salesIncTax = sales + taxes;
			salesIncTax = Math.round(salesIncTax * 100.0) / 100.0;
			initialSalesTimeDetails.get(recordsDetails.get(iter) - 1).put(colName.get(2), String.valueOf(salesIncTax));
		}
	}

	/**
	 * Update Total Sales Incurred Tax
	 * 
	 * @param columnName
	 */
	public void updateTotalSalesIncTax(String columnName) {
		List<String> colName = Arrays.asList(columnName.split(Constants.DELIMITER_HASH));
		double sales = Double.parseDouble(initialSalesTimeDetailsTotal.get(0).get(colName.get(0))
				.replaceAll(Constants.REPLACE_DOLLOR, Constants.EMPTY_STRING));
		double taxes = Double.parseDouble(initialSalesTimeDetailsTotal.get(0).get(colName.get(1))
				.replaceAll(Constants.REPLACE_DOLLOR, Constants.EMPTY_STRING));
		double salesIncTax = sales + taxes;
		salesIncTax = Math.round(salesIncTax * 100.0) / 100.0;
		initialSalesTimeDetailsTotal.get(0).put(colName.get(2), String.valueOf(salesIncTax));
	}

	/**
	 * Sales JSON Data Update
	 * 
	 * @param transStatus
	 * @param paymentType
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> salesJsonDataUpdate(String deviceId, String transStatus, String paymentType)
			throws Exception {
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(Reports.DATE_FORMAT);
		LocalDateTime tranDate = LocalDateTime.now();
		String transDate = tranDate.format(dateFormat);
		String salesHeaderID = UUID.randomUUID().toString().replace(Constants.DELIMITER_HYPHEN, Constants.EMPTY_STRING);
		String transID = deviceId + Constants.DELIMITER_HYPHEN
				+ transDate.replaceAll(Reports.REGEX_TRANS_DATE, Constants.EMPTY_STRING);
		String jsonString = jsonFunctions.readFileAsString(FilePath.JSON_SALES_CREATION_UFS_BY_DEVICE);
		JsonObject jsonData = jsonFunctions.convertStringToJson(jsonString);
		jsonData.addProperty(Reports.TRANS_ID, transID);
		jsonData.addProperty(Reports.TRANS_DATE, transDate);
		String sale = jsonData.get(Reports.SALE).getAsString();
		JsonObject sales = jsonFunctions.convertStringToJson(sale);
		sales.addProperty(Reports.ID, salesHeaderID);
		sales.addProperty(Reports.TRANS_ID, transID);
		sales.addProperty(Reports.TRANS_DATE, transDate);
		jsonArrayDataUpdate(sales, Reports.ITEMS, transID, salesHeaderID, transDate, transStatus, paymentType);
		jsonArrayDataUpdate(sales, Reports.PAYMENTS, transID, salesHeaderID, transDate, transStatus, paymentType);
		jsonData.addProperty(Reports.SALE, sales.toString());
		data.put(Reports.JSON, jsonData.toString());
		data.put(Reports.TRANS_DATE, tranDate);
		data.put(Reports.SALES, sales);
		return data;
	}

//	public UniversalControls getUnivCont() {
//		return univCont;
//	}

	public WebService getWebService() {
		return webService;
	}

	public int getRowCount() {
		return rowCount;
	}

	public List<Double> getRequiredJsonData() {
		return requiredJsonData;
	}

	public List<String> getDepositDetails() {
		return depositDetails;
	}

	public List<String> getDiscountDetails() {
		return discountDetails;
	}

	public List<Integer> getRequiredList() {
		return requiredList;
	}

	public List<Integer> getCountDetails() {
		return countDetails;
	}

	public List<LocalDateTime> getTransDates() {
		return transDates;
	}

	public List<Integer> getRecordsDetails() {
		return recordsDetails;
	}

	public List<String> getIntialReportsData() {
		return intialReportsDatas;
	}

	public List<String> getSalesTimeHeaders() {
		return salesTimeHeaders;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public Map<Integer, Map<String, String>> getInitialReportsData() {
		return initialReportsData;
	}

	public Map<Integer, Map<String, String>> getInitialReportTotals() {
		return initialReportTotals;
	}

	public Map<Integer, Map<String, String>> getInitialSalesTimeDetailsTotal() {
		return initialSalesTimeDetailsTotal;
	}

	public Map<Integer, Map<String, String>> getInitialSalesTimeDetails() {
		return initialSalesTimeDetails;
	}

	public Map<Integer, Map<String, String>> getReportsTotalData() {
		return reportsTotalData;
	}

	public Map<Integer, Map<String, String>> getReportsData() {
		return reportsData;
	}

	public Map<Integer, Map<String, String>> getCashFlowDetailsTotal() {
		return cashFlowDetailsTotal;
	}

	public Map<Integer, Map<String, String>> getSalesTimeDetails() {
		return salesTimeDetails;
	}

	public Map<Integer, Map<String, String>> getSalesTimeDetailsTotal() {
		return salesTimeDetailsTotal;
	}

	public Map<Integer, Map<String, String>> getCashFlowDetailsTotalsSum() {
		return cashFlowDetailsTotalsSum;
	}

	public Map<Integer, Map<String, String>> getSalesTimeDetailsTotalsSum() {
		return salesTimeDetailsTotalsSum;
	}
}
