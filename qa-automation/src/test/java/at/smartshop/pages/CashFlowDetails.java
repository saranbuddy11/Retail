package at.smartshop.pages;

import java.time.LocalDateTime;
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
import org.testng.Assert;

import com.aventstack.extentreports.Status;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import at.framework.browser.Factory;
import at.framework.files.JsonFile;
import at.framework.files.PropertyFile;
import at.framework.generic.CustomisedAssert;
import at.framework.reportsetup.ExtFactory;
import at.framework.ui.Foundation;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.keys.Reports;
import at.smartshop.tests.TestInfra;
import at.smartshop.utilities.WebService;

public class CashFlowDetails extends Factory {
	private Foundation foundation = new Foundation();
	private WebService webService = new WebService();
	private PropertyFile propertyFile = new PropertyFile();
	private JsonFile jsonFunctions = new JsonFile();

	private ReportList reportList = new ReportList();

	public static final By LBL_REPORT_NAME = By
			.cssSelector("#report-container > div:nth-child(3) > div.first-child > label");
	private static final By REPORT_GRID_FIRST_ROW = By.cssSelector("#Totals > tbody > tr:nth-child(1)");
	private static final By NO_DATA_AVAILABLE_IN_TABLE = By
			.xpath("//*[@id='Totals']//tbody//tr//td[@class='dataTables_empty']");
	public static final String DPD_LOCATION = "div.span12.m-0 > span > span.selection > span > ul";
	public static final String TABLE_CASH_FLOW_DETAILS_GRID = "#Totals > tbody";
	public static final String REPORTS_CONTAINERS = "report-container";
	public static final String ID_TABLE_CASH_FLOW_DETAILS_TOTAL = "Totals";
	public static final String ID_LBL_REPORT_NAME = "#report-container > script + style + div > div > label";
	public static final By REPORT_NAME = By.id("reportId");

	private Map<String, Object> jsonData = new HashMap<>();
	private Map<Integer, Map<String, String>> initialReportsData = new HashMap<>();
	private Map<Integer, Map<String, String>> initialReportTotals = new HashMap<>();
	private Map<Integer, Map<String, String>> reportsTotalData = new LinkedHashMap<>();
	private Map<Integer, Map<String, String>> cashFlowDetailsTotal = new LinkedHashMap<>();
	private Map<Integer, Map<String, String>> calculateCashFlowTotal = new LinkedHashMap<>();
	private Map<Integer, Map<String, String>> cashFlowDetailsTotalsSum = new LinkedHashMap<>();
	public Map<Integer, Map<String, String>> reportsData = new LinkedHashMap<>();
	private List<Double> requiredJsonData = new LinkedList<>();
	private List<Integer> requiredData = new LinkedList<>();
	private List<Integer> requiredCount = new LinkedList<>();
	private List<String> intialReportsData = new ArrayList<>();
	private List<String> tableHeaders = new ArrayList<>();
	public String staffName = "Non-Employee";

	int requiredRecordCount;
	int recordCountOfCash;
	int recordCountOfCreditCard;
	int recordCountOfSOGO;
	int recordCountOfComp;
	int recordCountOfGuestPass;
	int recordCountOfSpecial;
	int recordCountOfAccount;

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

	public int getLocationCount() {
		WebElement locationDropdown = getDriver().findElement(By.cssSelector(DPD_LOCATION));
		List<WebElement> locationDropdownList = locationDropdown.findElements(By.tagName("li"));
		return locationDropdownList.size() - 1;
	}

	/**
	 * Verifying the Report Name
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
	 * Checking the Data availability in Result Table
	 */
	public void checkForDataAvailabilyInResultTable() {
		try {
			if (foundation.isDisplayed(REPORT_GRID_FIRST_ROW)) {
				foundation.scrollIntoViewElement(REPORT_GRID_FIRST_ROW);
				if (foundation.isDisplayed(NO_DATA_AVAILABLE_IN_TABLE)) {
					ExtFactory.getInstance().getExtent().log(Status.INFO, "No Data Available in Report Table");
					Assert.fail("Failed Report because No Data Available in Report Table");
				} else {
					ExtFactory.getInstance().getExtent().log(Status.INFO,
							"Report Data Available in the Table, Hence passing the Test case");
				}
			} else {
				ExtFactory.getInstance().getExtent().log(Status.INFO, "No Report Table Available");
				Assert.fail("Failed Report because No Report Table Available");
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * Read all Records from UI Report
	 * 
	 * @param deviceId
	 * @param location
	 * @throws Exception
	 */
	public void readAllRecordsFromCashFlowDetailsTable(String deviceId, String location) throws Exception {
		int locCount = getLocationCount();
		reportsData.clear();
		int count = 0;

		String locationName = location.replace("@", Constants.DELIMITER_HYPHEN);
		for (int iteration = 3; iteration < locCount + 3; iteration++) {
			String tableCashFlowDetails = locationName;
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
					if (iter == 1) {
						WebElement column = row.findElement(By.cssSelector("td:nth-child(" + iter + ")"));
						reportsdata.put(tableHeaders.get(iter - 1), column.getText());
					} else {
						WebElement column = row.findElement(By.cssSelector("td:nth-child(" + iter + ")"));
						reportsdata.put(tableHeaders.get(iter - 1),
								column.getText().replaceAll(Constants.REPLACE_DOLLOR, Constants.EMPTY_STRING));
					}
				}
				reportsData.put(count, reportsdata);
				count++;
			}
		}
		WebElement tableReportTotalsList = getDriver().findElement(By.cssSelector(TABLE_CASH_FLOW_DETAILS_GRID));
		List<WebElement> records = tableReportTotalsList.findElements(By.tagName("tr"));
		reportsTotalData.clear();
		int counter = 0;
		for (WebElement record : records) {
			Map<String, String> reportTotalsdata = new LinkedHashMap<>();
			for (int iter = 1; iter < tableHeaders.size() + 1; iter++) {
				WebElement column = record.findElement(By.cssSelector("td:nth-child(" + iter + ")"));
				reportTotalsdata.put(tableHeaders.get(iter - 1),
						column.getText().replaceAll(Constants.REPLACE_DOLLOR, Constants.EMPTY_STRING));
			}
			reportsTotalData.put(counter, reportTotalsdata);
			counter++;
		}
	}

	/**
	 * Verify Report Headers
	 * 
	 * @param columnNames
	 */
	public void verifyReportHeaders(List<String> columnNames) {
		for (int iter = 0; iter < tableHeaders.size(); iter++) {
			Assert.assertTrue(tableHeaders.get(iter).equals(columnNames.get(iter)));
		}
	}

	public void calculateCounts(String columnName, int recordCountOfPaymentType) throws Exception {
		String initialCounts = initialReportsData.get(recordCountOfPaymentType).get(columnName);
		int updatedCounts = Integer.parseInt(initialCounts) + 1;
		initialReportsData.get(recordCountOfPaymentType).put(columnName, String.valueOf(updatedCounts));
	}

	public void calculateAmounts(String columnName, int recordCountOfPaymentType) throws Exception {
		String initialAmounts = initialReportsData.get(recordCountOfPaymentType).get(columnName);
		double amount = requiredJsonData.get(0) + requiredJsonData.get(1);
		double updatedAmounts = Double
				.parseDouble(initialAmounts.replaceAll(Constants.REPLACE_DOLLOR, Constants.EMPTY_STRING)) + amount;
		updatedAmounts = Math.round(updatedAmounts * 100.0) / 100.0;
		initialReportsData.get(recordCountOfPaymentType).put(columnName, String.valueOf(updatedAmounts));
	}

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

	public void calculateLocationTax(String columnName, int recordCountOfPaymentType) throws Exception {
		String paymentCounts = reportsData.get(recordCountOfPaymentType).get(tableHeaders.get(1));
		String voidCounts = reportsData.get(recordCountOfPaymentType).get(tableHeaders.get(3));
		double taxes = requiredJsonData.get(1)
				* (Double.parseDouble(paymentCounts)
						- Double.parseDouble(voidCounts));
		taxes = Math.round(taxes * 100.0) / 100.0;
		initialReportsData.get(recordCountOfPaymentType).put(columnName, String.valueOf(taxes));
	}

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
	
	
	public void calculateCountsForTotals(String columnName, int recordCountOfPaymentType) throws Exception {
		String initialCounts = initialReportsData.get(recordCountOfPaymentType).get(columnName);
		int updatedCounts = Integer.parseInt(initialCounts) + 7;
		initialReportsData.get(recordCountOfPaymentType).put(columnName, String.valueOf(updatedCounts));
	}

	public void calculateAmountsForTotals(String columnName, int recordCountOfPaymentType) throws Exception {
		String initialAmounts = initialReportsData.get(recordCountOfPaymentType).get(columnName);
		double amount = (requiredJsonData.get(0) + requiredJsonData.get(1)) * 7;
		double updatedAmounts = Double
				.parseDouble(initialAmounts.replaceAll(Constants.REPLACE_DOLLOR, Constants.EMPTY_STRING)) + amount;
		updatedAmounts = Math.round(updatedAmounts * 100.0) / 100.0;
		initialReportsData.get(recordCountOfPaymentType).put(columnName, String.valueOf(updatedAmounts));
	}

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

	public void calculateLocationTaxForTotals(String columnName, int recordCountOfPaymentType) throws Exception {
		String paymentCounts = reportsData.get(recordCountOfPaymentType).get(tableHeaders.get(1));
		String voidCounts = reportsData.get(recordCountOfPaymentType).get(tableHeaders.get(3));
		double taxes = requiredJsonData.get(1)
				* (Double.parseDouble(paymentCounts)
						- Double.parseDouble(voidCounts));
		taxes = Math.round(taxes * 100.0) / 100.0;
		initialReportsData.get(recordCountOfPaymentType).put(columnName, String.valueOf(taxes));
	}

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
			String saleValue = jsonFunctions.readFileAsString(FilePath.JSON_SALES_CREATION);
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
	 * Select the report with Date range and Location to run the report
	 * 
	 * @param reportName
	 * @param date
	 * @param location
	 */
	public void selectAndRunReport(String reportName, String date, String location) {
		reportList.selectReport(reportName);
		reportList.selectDate(date);
		reportList.selectLocation(location);
		foundation.threadWait(Constants.SHORT_TIME);
		foundation.click(ReportList.BTN_RUN_REPORT);
		foundation.waitforElement(ProductSales.LBL_REPORT_NAME, Constants.SHORT_TIME);
		verifyReportName(reportName);
	}

	public Map<Integer, Map<String, String>> getCashFlowDetailsTotal() {
		return cashFlowDetailsTotal;
	}

	public Map<Integer, Map<String, String>> getCalculateCashFlowTotal() {
		return calculateCashFlowTotal;
	}

	public Map<Integer, Map<String, String>> getCashFlowDetailsTotalsSum() {
		return cashFlowDetailsTotalsSum;
	}

	public List<Double> getRequiredJsonData() {
		return requiredJsonData;
	}

	public List<Integer> getRequiredData() {
		return requiredData;
	}

	public List<Integer> getRequiredCount() {
		return requiredCount;
	}

	public List<String> getIntialReportsData() {
		return intialReportsData;
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

	public List<String> getTableHeaders() {
		return tableHeaders;
	}
}
