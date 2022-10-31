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

	private Map<Integer, Map<String, String>> reportsData = new LinkedHashMap<>();
	private Map<Integer, Map<String, String>> reportsTotalData = new LinkedHashMap<>();
	private Map<Integer, Map<String, String>> salesTimeDetailsTotal = new LinkedHashMap<>();
	private List<String> tableHeadersOfSalesTimeDetails = new ArrayList<>();
	private Map<Integer, Map<String, String>> reportsDataOfSalesTimeDetails = new LinkedHashMap<>();
	private Map<Integer, Map<String, String>> salesTimeDetails = new LinkedHashMap<>();
	private Map<Integer, Map<String, String>> initialReportsData = new HashMap<>();
	private Map<Integer, Map<String, String>> initialReportTotals = new HashMap<>();
	private Map<Integer, Map<String, String>> initialSalesTimeDetailsTotal = new LinkedHashMap<>();
	private Map<Integer, Map<String, String>> initialSalesTimeDetails = new LinkedHashMap<>();
	private Map<Integer, Map<String, String>> initialDataOfSalesTimeDetails = new LinkedHashMap<>();
	private Map<String, String> updatedTableFooters = new LinkedHashMap<>();
	private Map<String, String> tableFooterData = new LinkedHashMap<>();
	public static List<String> tableHeaders = new ArrayList<>();
	private List<String> salesTimeHeaders = new ArrayList<>();
	private List<Double> requiredJsonData = new LinkedList<>();
	private List<Integer> requiredCount = new LinkedList<>();
	private Map<String, Object> jsonData = new HashMap<>();
	private int rowCountOfSalesTimeDetils;
	private Double totalSales;
	int requiredRecordCount;
	int recordCountOfCash;
	int recordCountOfCreditCard;
	int recordCountOfSOGO;
	int recordCountOfComp;
	int recordCountOfGuestPass;
	int recordCountOfSpecial;
	int recordCountOfAccount;

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
	 * Calculate Counts
	 * 
	 * @param columnName
	 * @param recordCountOfPaymentType
	 * @throws Exception
	 */
	public void calculateCounts(String columnName, int recordCountOfPaymentType) throws Exception {
		String initialCounts = initialReportsData.get(recordCountOfPaymentType).get(columnName);
		int updatedCounts = Integer.parseInt(initialCounts) + 1;
		initialReportsData.get(recordCountOfPaymentType).put(columnName, String.valueOf(updatedCounts));
	}

	/**
	 * Calculate Amounts
	 * 
	 * @param columnName
	 * @param recordCountOfPaymentType
	 * @throws Exception
	 */
	public void calculateAmounts(String columnName, int recordCountOfPaymentType) throws Exception {
		String initialAmounts = initialReportsData.get(recordCountOfPaymentType).get(columnName);
		double amount = requiredJsonData.get(0) + requiredJsonData.get(1);
		double updatedAmounts = Double
				.parseDouble(initialAmounts.replaceAll(Constants.REPLACE_DOLLOR, Constants.EMPTY_STRING)) + amount;
		updatedAmounts = Math.round(updatedAmounts * 100.0) / 100.0;
		initialReportsData.get(recordCountOfPaymentType).put(columnName, String.valueOf(updatedAmounts));
	}

	/**
	 * Calculate Location Sales
	 * 
	 * @param columnName
	 * @param recordCountOfPaymentType
	 * @throws Exception
	 */
	public void calculateLocationSales(String columnName, int recordCountOfPaymentType) throws Exception {
		String paymentAmounts = reportsData.get(recordCountOfPaymentType).get(tableHeaders.get(2));
		String voidAmounts = reportsData.get(recordCountOfPaymentType).get(tableHeaders.get(4));
		String taxes = reportsData.get(recordCountOfPaymentType).get(tableHeaders.get(8));
		double salesData = Double
				.parseDouble(paymentAmounts.replaceAll(Constants.REPLACE_DOLLOR, Constants.EMPTY_STRING))
				- Double.parseDouble(voidAmounts.replaceAll(Constants.REPLACE_DOLLOR, Constants.EMPTY_STRING))
				- Double.parseDouble(taxes.replaceAll(Constants.REPLACE_DOLLOR, Constants.EMPTY_STRING));
		salesData = Math.round(salesData * 100.0) / 100.0;
		initialReportsData.get(recordCountOfPaymentType).put(columnName, String.valueOf(salesData));
	}

	/**
	 * Calculate Location Tax
	 * 
	 * @param columnName
	 * @param recordCountOfPaymentType
	 * @throws Exception
	 */
	public void calculateLocationTax(String columnName, int recordCountOfPaymentType) throws Exception {
		String paymentCounts = reportsData.get(recordCountOfPaymentType).get(tableHeaders.get(1));
		String voidCounts = reportsData.get(recordCountOfPaymentType).get(tableHeaders.get(3));
		double taxes = requiredJsonData.get(1) * (Double.parseDouble(paymentCounts) - Double.parseDouble(voidCounts));
		taxes = Math.round(taxes * 100.0) / 100.0;
		initialReportsData.get(recordCountOfPaymentType).put(columnName, String.valueOf(taxes));
	}

	/**
	 * Calculate Totals Column Data
	 * 
	 * @param columnName
	 * @param recordCountOfPaymentType
	 * @throws Exception
	 */
	public void calculateTotalsColumnData(String columnName, int recordCountOfPaymentType) throws Exception {
		String salesDate = reportsData.get(recordCountOfPaymentType).get(tableHeaders.get(7));
		String taxes = reportsData.get(recordCountOfPaymentType).get(tableHeaders.get(8));
		String tipsAmount = reportsData.get(recordCountOfPaymentType).get(tableHeaders.get(12));
		double totals = Double.parseDouble(tipsAmount.replaceAll(Constants.REPLACE_DOLLOR, Constants.EMPTY_STRING))
				+ Double.parseDouble(salesDate.replaceAll(Constants.REPLACE_DOLLOR, Constants.EMPTY_STRING))
				+ Double.parseDouble(taxes.replaceAll(Constants.REPLACE_DOLLOR, Constants.EMPTY_STRING));
		totals = Math.round(totals * 100.0) / 100.0;
		initialReportsData.get(recordCountOfPaymentType).put(columnName, String.valueOf(totals));
	}

	/**
	 * Calculate Counts for Totals
	 * 
	 * @param columnName
	 * @param recordCountOfPaymentType
	 * @throws Exception
	 */
	public void calculateCountsForTotals(String columnName, int recordCountOfPaymentType) throws Exception {
		String initialCounts = initialReportsData.get(recordCountOfPaymentType).get(columnName);
		int updatedCounts = Integer.parseInt(initialCounts) + 7;
		initialReportsData.get(recordCountOfPaymentType).put(columnName, String.valueOf(updatedCounts));
	}

	/**
	 * Calculate Amounts for Totals
	 * 
	 * @param columnName
	 * @param recordCountOfPaymentType
	 * @throws Exception
	 */
	public void calculateAmountsForTotals(String columnName, int recordCountOfPaymentType) throws Exception {
		String initialAmounts = initialReportsData.get(recordCountOfPaymentType).get(columnName);
		double amount = (requiredJsonData.get(0) + requiredJsonData.get(1)) * 7;
		double updatedAmounts = Double
				.parseDouble(initialAmounts.replaceAll(Constants.REPLACE_DOLLOR, Constants.EMPTY_STRING)) + amount;
		updatedAmounts = Math.round(updatedAmounts * 100.0) / 100.0;
		initialReportsData.get(recordCountOfPaymentType).put(columnName, String.valueOf(updatedAmounts));
	}

	/**
	 * Calculate Location Sales for Totals
	 * 
	 * @param columnName
	 * @param recordCountOfPaymentType
	 * @throws Exception
	 */
	public void calculateLocationSalesForTotals(String columnName, int recordCountOfPaymentType) throws Exception {
		String paymentAmounts = reportsData.get(recordCountOfPaymentType).get(tableHeaders.get(2));
		String voidAmounts = reportsData.get(recordCountOfPaymentType).get(tableHeaders.get(4));
		String taxes = reportsData.get(recordCountOfPaymentType).get(tableHeaders.get(8));
		double salesData = Double
				.parseDouble(paymentAmounts.replaceAll(Constants.REPLACE_DOLLOR, Constants.EMPTY_STRING))
				- Double.parseDouble(voidAmounts.replaceAll(Constants.REPLACE_DOLLOR, Constants.EMPTY_STRING))
				- Double.parseDouble(taxes.replaceAll(Constants.REPLACE_DOLLOR, Constants.EMPTY_STRING));
		salesData = Math.round(salesData * 100.0) / 100.0;
		initialReportsData.get(recordCountOfPaymentType).put(columnName, String.valueOf(salesData));
	}

	/**
	 * Calculate Location Tax for Totals
	 * 
	 * @param columnName
	 * @param recordCountOfPaymentType
	 * @throws Exception
	 */
	public void calculateLocationTaxForTotals(String columnName, int recordCountOfPaymentType) throws Exception {
		String paymentCounts = reportsData.get(recordCountOfPaymentType).get(tableHeaders.get(1));
		String voidCounts = reportsData.get(recordCountOfPaymentType).get(tableHeaders.get(3));
		double taxes = requiredJsonData.get(1) * (Double.parseDouble(paymentCounts) - Double.parseDouble(voidCounts));
		taxes = Math.round(taxes * 100.0) / 100.0;
		initialReportsData.get(recordCountOfPaymentType).put(columnName, String.valueOf(taxes));
	}

	/**
	 * Calculate Totals Column Data
	 * 
	 * @param columnName
	 * @param recordCountOfPaymentType
	 * @throws Exception
	 */
	public void calculateTotalsColumnDataForTotals(String columnName, int recordCountOfPaymentType) throws Exception {
		String salesDate = reportsData.get(recordCountOfPaymentType).get(tableHeaders.get(7));
		String taxes = reportsData.get(recordCountOfPaymentType).get(tableHeaders.get(8));
		String tipsAmount = reportsData.get(recordCountOfPaymentType).get(tableHeaders.get(12));
		double totals = Double.parseDouble(tipsAmount.replaceAll(Constants.REPLACE_DOLLOR, Constants.EMPTY_STRING))
				+ Double.parseDouble(salesDate.replaceAll(Constants.REPLACE_DOLLOR, Constants.EMPTY_STRING))
				+ Double.parseDouble(taxes.replaceAll(Constants.REPLACE_DOLLOR, Constants.EMPTY_STRING));
		totals = Math.round(totals * 100.0) / 100.0;
		initialReportsData.get(recordCountOfPaymentType).put(columnName, String.valueOf(totals));
	}

	/**
	 * Verify Report Records
	 * 
	 * @throws Exception
	 */
	public void verifyReportRecords() throws Exception {
		int count = initialReportsData.size();
		for (int counter = 0; counter < count; counter++) {
			for (int iter = 0; iter < tableHeaders.size(); iter++) {
				CustomisedAssert.assertTrue(reportsData.get(counter).get(tableHeaders.get(iter))
						.contains(initialReportsData.get(counter).get(tableHeaders.get(iter))));
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
	 * Read Sales Time Details Table
	 * 
	 * @throws Exception
	 */
	public void getTblRecordsUIOfSalesTimeDetails(String device, String location) {
		try {
			int recordCount = 0;
			String locationName = location.replace("@", Constants.DELIMITER_HYPHEN);
			String tableCashFlowDetails = "salestime-" + locationName + Constants.DELIMITER_HYPHEN + device;
			WebElement tableReportsSalesTimeDetails = getDriver().findElement(By.id(tableCashFlowDetails));
			tableHeadersOfSalesTimeDetails.clear();
			WebElement tableReportsListSalesTimeDetails = getDriver()
					.findElement(By.cssSelector(Constants.DELIMITER_HASH + tableCashFlowDetails + " > tbody"));
			List<WebElement> columnHeaders = tableReportsSalesTimeDetails
					.findElements(By.cssSelector("thead > tr > th"));
			List<WebElement> rows = tableReportsListSalesTimeDetails.findElements(By.tagName("tr"));
			for (WebElement columnHeader : columnHeaders) {
				tableHeadersOfSalesTimeDetails.add(columnHeader.getText());
			}
			for (WebElement row : rows) {
				Map<String, String> uiTblRowValues = new LinkedHashMap<>();
				for (int columnCount = 1; columnCount < tableHeadersOfSalesTimeDetails.size() + 1; columnCount++) {
					WebElement column = row.findElement(By.cssSelector("td:nth-child(" + columnCount + ")"));
					uiTblRowValues.put(tableHeadersOfSalesTimeDetails.get(columnCount - 1), column.getText());
				}
				reportsDataOfSalesTimeDetails.put(recordCount, uiTblRowValues);
				recordCount++;
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
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
			CustomisedAssert.assertTrue(tableHeaders.get(iter).equals(columnName.get(iter)));
		}
	}

	/**
	 * Process API
	 * 
	 * @param transStatus
	 * @param paymentType
	 * @throws Exception
	 */
	public void processAPI(String transStatus, String paymentType, String deviceID, String value) throws Exception {
		requiredCount.clear();
		List<String> tStatus = Arrays.asList(transStatus.split(Constants.DELIMITER_HASH));
		List<String> payType = Arrays.asList(paymentType.split(Constants.DELIMITER_HASH));
		for (int iterator = 0; iterator < payType.size(); iterator++) {
			for (int iter = 0; iter < tStatus.size(); iter++) {
				generateJsonDetails(value);
				salesJsonDataUpdate(tStatus.get(iter), payType.get(iterator), deviceID);
				webService.apiReportPostRequest(
						propertyFile.readPropertyFile(Configuration.TRANS_SALES, FilePath.PROPERTY_CONFIG_FILE),
						(String) jsonData.get(Reports.JSON));
				foundation.threadWait(Constants.ONE_SECOND);
			}
		}
	}

	/**
	 * Get Required Count Record
	 * 
	 * @param paymentType
	 * @return
	 */
	public int getRequiredRecord(String paymentType) {
		try {
			requiredRecordCount = 0;
			for (int rowCount = 0; rowCount < initialReportsData.size(); rowCount++) {
				if (initialReportsData.get(rowCount).get(tableHeaders.get(0)).equals(paymentType)) {
					requiredRecordCount = rowCount;
					break;
				}
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		return requiredRecordCount;
	}

	/**
	 * Generate Json Details
	 * 
	 * @param reportFormat
	 */
	private void generateJsonDetails(String reportFormat) {
		try {
			DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(Reports.DATE_FORMAT);
			DateTimeFormatter reqFormat = DateTimeFormatter.ofPattern(reportFormat);
			LocalDateTime tranDate = LocalDateTime.now();
			String transDate = tranDate.format(dateFormat);
			String reportDate = tranDate.format(reqFormat);
			String transID = propertyFile.readPropertyFile(Configuration.DEVICE_ID, FilePath.PROPERTY_CONFIG_FILE)
					+ Constants.DELIMITER_HYPHEN
					+ transDate.replaceAll(Reports.REGEX_TRANS_DATE, Constants.EMPTY_STRING);
			jsonData.put(Reports.TRANS_ID, transID);
			jsonData.put(Reports.TRANS_DATE, transDate);
			jsonData.put(Reports.TRANS_DATE_TIME, reportDate);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * Verify Sales Time Report Records
	 */
	public void verifySalesTimeReportRecords() {
		int count = initialSalesTimeDetails.size();
		int columnCount = salesTimeHeaders.size();
		for (int iter = 0; iter < count; iter++) {
			for (int val = 0; val < columnCount; val++) {
				CustomisedAssert.assertTrue(salesTimeDetails.get(0).get(salesTimeHeaders.get(val))
						.contains(initialSalesTimeDetails.get(0).get(salesTimeHeaders.get(val))));
			}
		}
		for (int val = 1; val < columnCount; val++) {
			CustomisedAssert.assertTrue(salesTimeDetailsTotal.get(0).get(salesTimeHeaders.get(val))
					.contains(initialSalesTimeDetailsTotal.get(0).get(salesTimeHeaders.get(val))));
		}
	}

	/**
	 * This Method is for Deciding the Time Range
	 * 
	 * @param transDateTime
	 */
	public void decideTimeRange(String transDateTime) {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.REGEX_YYYY_MM_DD_HH_MM_SS);
			LocalDateTime transDateTime1 = LocalDateTime.parse(transDateTime, formatter);
			LocalTime transTime = transDateTime1.toLocalTime();
			if (transTime.isAfter(LocalTime.MIN) && transTime.isBefore(LocalTime.of(6, 0, 59))) {
				rowCountOfSalesTimeDetils = 0;
			} else if (transTime.isAfter(LocalTime.of(06, 01, 00)) && transTime.isBefore(LocalTime.of(10, 30, 59))) {
				rowCountOfSalesTimeDetils = 1;
			} else if (transTime.isAfter(LocalTime.of(10, 31, 00)) && transTime.isBefore(LocalTime.of(14, 00, 59))) {
				rowCountOfSalesTimeDetils = 2;
			} else if (transTime.isAfter(LocalTime.of(14, 01, 00)) && transTime.isBefore(LocalTime.MAX)) {
				rowCountOfSalesTimeDetils = 3;
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * Get Json Sales Data
	 */
	public void getJsonSalesData() {
		try {
			requiredJsonData.clear();
			JsonObject sales = (JsonObject) jsonData.get(Reports.SALES);
			String subTotal = sales.get(Reports.SUBTOTAL).getAsString();
			requiredJsonData.add(Double.parseDouble(subTotal));
			String tax = sales.get(Reports.TAX).getAsString();
			requiredJsonData.add(Double.parseDouble(tax));
		} catch (Exception exc) {
			exc.printStackTrace();
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * This Method is for calculating Amount
	 * 
	 * @param columnName
	 * @param amount
	 */
	public void calculateAmount(String columnName, String amount) {
		try {
			String initialAmount = initialDataOfSalesTimeDetails.get(rowCountOfSalesTimeDetils).get(columnName)
					.replaceAll(Reports.REPLACE_DOLLOR, Constants.EMPTY_STRING);
			double updatedAmount = (Double
					.parseDouble(amount.replaceAll(Reports.REPLACE_DOLLOR, Constants.EMPTY_STRING)) * 2) * 7
					+ Double.parseDouble(initialAmount);
			updatedAmount = Math.round(updatedAmount * 100.0) / 100.0;
			initialDataOfSalesTimeDetails.get(rowCountOfSalesTimeDetils).put(columnName,
					Constants.DOLLAR_SYMBOL + String.valueOf(updatedAmount));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * This Method is for calculating Total Amount
	 * 
	 * @param columnName
	 * @param amount
	 */
	public void calculateAmountOfFooter(String columnName, String amount) {
		try {
			String initialAmount = initialDataOfSalesTimeDetails.get(4).get(columnName)
					.replaceAll(Reports.REPLACE_DOLLOR, Constants.EMPTY_STRING);
			double updatedAmount = (Double
					.parseDouble(amount.replaceAll(Reports.REPLACE_DOLLOR, Constants.EMPTY_STRING)) * 2) * 7
					+ Double.parseDouble(initialAmount);
			updatedAmount = Math.round(updatedAmount * 100.0) / 100.0;
			initialDataOfSalesTimeDetails.get(4).put(columnName,
					Constants.DOLLAR_SYMBOL + String.valueOf(updatedAmount));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * This Method is for Sales Including Taxes
	 * 
	 * @param columnName
	 * @param price
	 * @param tax
	 * @param discount
	 * @return
	 */
	public double saleIncludingTaxes(String columnName, String price, String tax, String discount) {
		try {
			String initialAmount = initialDataOfSalesTimeDetails.get(rowCountOfSalesTimeDetils).get(columnName)
					.replaceAll(Reports.REPLACE_DOLLOR, Constants.EMPTY_STRING);
			double updatedAmount = (Double.parseDouble(price.replaceAll(Reports.REPLACE_DOLLOR, Constants.EMPTY_STRING))
					* 2 + Double.parseDouble(tax.replaceAll(Reports.REPLACE_DOLLOR, Constants.EMPTY_STRING)) * 2) * 7
					+ Double.parseDouble(initialAmount);
			totalSales = Math.round(updatedAmount * 100.0) / 100.0;
			initialDataOfSalesTimeDetails.get(rowCountOfSalesTimeDetils).put(columnName,
					Constants.DOLLAR_SYMBOL + String.valueOf(totalSales));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		return totalSales;
	}

	/**
	 * This Method is for Total Sales Including Taxes
	 * 
	 * @param columnName
	 * @param price
	 * @param tax
	 * @param discount
	 * @return
	 */
	public double saleIncludingTaxesOfFooter(String columnName, String price, String tax, String discount) {
		try {
			String initialAmount = initialDataOfSalesTimeDetails.get(4).get(columnName)
					.replaceAll(Reports.REPLACE_DOLLOR, Constants.EMPTY_STRING);
			double updatedAmount = (Double.parseDouble(price.replaceAll(Reports.REPLACE_DOLLOR, Constants.EMPTY_STRING))
					* 2 + Double.parseDouble(tax.replaceAll(Reports.REPLACE_DOLLOR, Constants.EMPTY_STRING)) * 2) * 7
					+ Double.parseDouble(initialAmount);
			totalSales = Math.round(updatedAmount * 100.0) / 100.0;
			initialDataOfSalesTimeDetails.get(4).put(columnName, Constants.DOLLAR_SYMBOL + String.valueOf(totalSales));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		return totalSales;
	}

	/**
	 * This Method is for Transaction count
	 * 
	 * @param columnName
	 */
	public void TrasactionCount(String columnName) {
		try {
			String saleCount = initialDataOfSalesTimeDetails.get(rowCountOfSalesTimeDetils).get(columnName);
			int updatedCount = Integer.parseInt(saleCount) + 7;
			initialDataOfSalesTimeDetails.get(rowCountOfSalesTimeDetils).put(columnName, String.valueOf(updatedCount));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * This Method is for Total Transaction count
	 * 
	 * @param columnName
	 */
	public void TrasactionCountOfFooter(String columnName) {
		try {
			String saleCount = initialDataOfSalesTimeDetails.get(4).get(columnName);
			int updatedCount = Integer.parseInt(saleCount) + 7;
			initialDataOfSalesTimeDetails.get(4).put(columnName, String.valueOf(updatedCount));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * This Method is for verifying Header Data Of Sales Time Details
	 */
	public void verifyReportHeadersOfSalesTimeDetails(String columnNames) {
		try {
			List<String> columnName = Arrays.asList(columnNames.split(Constants.DELIMITER_HASH));
			foundation.threadWait(Constants.ONE_SECOND);
			for (int iter = 0; iter < tableHeadersOfSalesTimeDetails.size(); iter++) {
				CustomisedAssert.assertTrue(tableHeadersOfSalesTimeDetails.get(iter).equals(columnName.get(iter)));
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * This Method is for verifying Report Data Of Sales Time Details
	 */
	public void verifyReportDataOfSalesTimeDetails() {
		try {
			int count = initialDataOfSalesTimeDetails.size();
			foundation.threadWait(Constants.TWO_SECOND);
			for (int counter = 0; counter < count; counter++) {
				for (int iter = 0; iter < tableHeadersOfSalesTimeDetails.size(); iter++) {
					CustomisedAssert.assertTrue(reportsDataOfSalesTimeDetails.get(counter)
							.get(tableHeadersOfSalesTimeDetails.get(iter)).contains(initialDataOfSalesTimeDetails
									.get(counter).get(tableHeadersOfSalesTimeDetails.get(iter))));
				}
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * Json Array Data Update
	 * 
	 * @param jsonObj
	 * @param reqString
	 * @param salesheader
	 * @param transStatus
	 * @param paymentType
	 */
	private void jsonArrayDataUpdate(JsonObject jsonObj, String reqString, String salesheader, String transStatus,
			String paymentType) {
		try {
			JsonArray items = jsonObj.get(reqString).getAsJsonArray();
			for (JsonElement item : items) {
				JsonObject json = item.getAsJsonObject();
				json.addProperty(Reports.ID,
						UUID.randomUUID().toString().replace(Constants.DELIMITER_HYPHEN, Constants.EMPTY_STRING));
				json.addProperty(Reports.SALES_HEADER, salesheader);
				json.addProperty(Reports.TRANS_ID, (String) jsonData.get(Reports.TRANS_ID));
				json.addProperty(Reports.TRANS_DATE, (String) jsonData.get(Reports.TRANS_DATE));
			}
			if (reqString.equals(Reports.PAYMENTS)) {
				for (JsonElement jsonarr : items) {
					JsonObject element = jsonarr.getAsJsonObject();
					element.addProperty(Reports.STATUS, transStatus);
					element.addProperty(Reports.TYPE, paymentType);
				}
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * Sales Json Data Update
	 * 
	 * @param transStatus
	 * @param paymentType
	 * @param deviceID
	 */
	private void salesJsonDataUpdate(String transStatus, String paymentType, String deviceID) {
		try {
			String salesHeaderID = UUID.randomUUID().toString().replace(Constants.DELIMITER_HYPHEN,
					Constants.EMPTY_STRING);
			String saleValue = jsonFunctions.readFileAsString(FilePath.JSON_SALES_CREATION_WITH_DEPOSIT_AND_DISCOUNT);
			JsonObject saleJson = jsonFunctions.convertStringToJson(saleValue);
			saleJson.addProperty(Reports.TRANS_ID, (String) jsonData.get(Reports.TRANS_ID));
			saleJson.addProperty(Reports.TRANS_DATE, (String) jsonData.get(Reports.TRANS_DATE));
			String sale = saleJson.get(Reports.SALE).getAsString();
			JsonObject salesObj = jsonFunctions.convertStringToJson(sale);
			salesObj.addProperty(Reports.ID, salesHeaderID);
			salesObj.addProperty(Reports.TRANS_ID, (String) jsonData.get(Reports.TRANS_ID));
			salesObj.addProperty(Reports.TRANS_DATE, (String) jsonData.get(Reports.TRANS_DATE));
			jsonArrayDataUpdate(salesObj, Reports.ITEMS, salesHeaderID, transStatus, paymentType);
			jsonArrayDataUpdate(salesObj, Reports.PAYMENTS, salesHeaderID, transStatus, paymentType);
			saleJson.addProperty(Reports.SALE, salesObj.toString());
			jsonData.put(Reports.JSON, saleJson.toString());
			jsonData.put(Reports.SALES, salesObj);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * This Method is for verifying Total Report Data Of Sales Time Details
	 */
	public void verifyReportFootertDataOfSalesTimeDetails() {
		try {
			foundation.threadWait(Constants.TWO_SECOND);
			for (int iter = 0; iter < tableHeadersOfSalesTimeDetails.size(); iter++) {
				CustomisedAssert.assertTrue(tableFooterData.get(tableHeadersOfSalesTimeDetails.get(iter))
						.contains(updatedTableFooters.get(tableHeadersOfSalesTimeDetails.get(iter))));
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public Map<String, Object> getJsonData() {
		return jsonData;
	}

	public Map<Integer, Map<String, String>> getIntialDataOfSalesTimeDetails() {
		return initialDataOfSalesTimeDetails;
	}

	public Map<Integer, Map<String, String>> getReportsDataOfSalesTimeDetails() {
		return reportsDataOfSalesTimeDetails;
	}

	public Map<String, String> getTableFootersOfSalesTimeDetails() {
		return tableFooterData;
	}

	public Map<String, String> getUpdatedTableFootersOfSalesTimeDetails() {
		return updatedTableFooters;
	}

	public Map<Integer, Map<String, String>> getInitialReportsData() {
		return initialReportsData;
	}

	public Map<Integer, Map<String, String>> getInitialReportTotals() {
		return initialReportTotals;
	}

	public Map<Integer, Map<String, String>> getReportsTotalData() {
		return reportsTotalData;
	}

	public Map<Integer, Map<String, String>> getReportsData() {
		return reportsData;
	}

	public List<String> getTableHeadersOfSalesTimeDetails() {
		return tableHeadersOfSalesTimeDetails;
	}

	public List<String> getTableHeaders() {
		return tableHeaders;
	}
}
