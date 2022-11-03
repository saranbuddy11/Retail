package at.smartshop.pages;

import java.text.DecimalFormat;
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

public class CashFlowDetailsInternational extends Factory {

	private Foundation foundation = new Foundation();
	private WebService webService = new WebService();
	private PropertyFile propertyFile = new PropertyFile();
	private JsonFile jsonFunctions = new JsonFile();
	private ReportList reportList = new ReportList();

	private static final By REPORT_GRID_FIRST_ROW = By.cssSelector("#Automation-365 > tbody > tr:nth-child(1)");
	private static final By NO_DATA_AVAILABLE_IN_TABLE = By.xpath("//td[@class='dataTables_empty']");

	public static final By LBL_REPORT_NAME = By
			.cssSelector("#report-container > div:nth-child(3) > div.first-child > label");
	public static final String DPD_LOCATION = "div.span12.m-0 > span > span.selection > span > ul";
	public static final String TABLE_CASH_FLOW_DETAILS_GRID = "#Totals > tbody";
	public static final String REPORTS_CONTAINERS = "report-container";
	public static final String ID_TABLE_CASH_FLOW_DETAILS_TOTAL = "Totals";
	public static final String ID_LBL_REPORT_NAME = "#report-container > script + style + div > div > label";
	public static final By REPORT_NAME = By.id("reportId");

	private Map<String, Object> jsonData = new HashMap<>();
	private Map<Integer, Map<String, String>> initialReportsData = new HashMap<>();
	private Map<Integer, Map<String, String>> reportsTotalData = new LinkedHashMap<>();
	public Map<Integer, Map<String, String>> reportsData = new LinkedHashMap<>();
	private List<Double> requiredJsonData = new LinkedList<>();
	private List<Integer> requiredData = new LinkedList<>();
	private List<Integer> requiredCount = new LinkedList<>();
	private List<String> intialReportsData = new ArrayList<>();
	private List<String> tableHeaders = new ArrayList<>();
	private List<String> tableHeaders1 = new ArrayList<>();
	private List<String> tableHeaders2 = new ArrayList<>();
	private List<String> tableAllHeaders = new ArrayList<>();

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
			String reportTitle = foundation.getText(REPORT_NAME);
			Assert.assertEquals(reportTitle, reportName);
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
//		int locCount = getLocationCount();
		reportsData.clear();
		int count = 0;

		int recordCount = 0;

		String locationName = location.replace("@", Constants.DELIMITER_HYPHEN);
		String tableCashFlowDetails = locationName;
		WebElement tableReports = getDriver().findElement(By.id(tableCashFlowDetails));
		List<WebElement> headers = tableReports.findElements(By.tagName("th"));
		tableHeaders.clear();
		tableHeaders1.clear();
		tableHeaders2.clear();
		tableAllHeaders.clear();
		for (int headerCount = 0; headerCount < headers.size(); headerCount++) {
			if (headerCount < 2) {
				tableHeaders.add(headers.get(headerCount).getText());
			} else if (headerCount > 4 && headerCount < 9) {
				tableHeaders1.add(headers.get(headerCount).getText());
			} else if (headerCount > 8) {
				tableHeaders2.add(headerCount + headers.get(headerCount).getText());
			}
		}
		tableHeaders.addAll(tableHeaders2);
		tableHeaders.addAll(tableHeaders1);

		for (WebElement columnHeader : headers) {
			tableAllHeaders.add(columnHeader.getText());
		}
		WebElement tableReportsList = getDriver()
				.findElement(By.cssSelector(Constants.DELIMITER_HASH + tableCashFlowDetails + " > tbody"));
		List<WebElement> rows = tableReportsList.findElements(By.tagName("tr"));
		for (WebElement row : rows) {
			Map<String, String> uiTblRowValues = new LinkedHashMap<>();
			for (int columnCount = 1; columnCount < tableHeaders.size() + 1; columnCount++) {
				WebElement column = row.findElement(By.cssSelector("td:nth-child(" + columnCount + ")"));
				uiTblRowValues.put(tableHeaders.get(columnCount - 1), column.getText());
			}
			reportsData.put(recordCount, uiTblRowValues);
			recordCount++;
		}
	}
	/**
	 * Verify Report Headers
	 * 
	 * @param columnNames
	 */
	public void verifyReportHeaders(List<String> columnNames) {
		for (int iter = 0; iter < tableAllHeaders.size(); iter++) {
			Assert.assertTrue(tableAllHeaders.get(iter).equals(columnNames.get(iter)));
		}
	}

	public void calculateProductCounts(String columnName, int recordCountOfPaymentType) throws Exception {
		String initialCounts = initialReportsData.get(recordCountOfPaymentType).get(columnName);
		int updatedCounts = Integer.parseInt(initialCounts) + 2;
		initialReportsData.get(recordCountOfPaymentType).put(columnName, String.valueOf(updatedCounts));
	}

	public void calculateProductCountsForCredit(String columnName, int recordCountOfPaymentType) throws Exception {
		String initialCounts = initialReportsData.get(recordCountOfPaymentType).get(columnName);
		int updatedCounts = Integer.parseInt(initialCounts) + 4;
		initialReportsData.get(recordCountOfPaymentType).put(columnName, String.valueOf(updatedCounts));
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
		if (updatedAmounts < 0) {
			DecimalFormat df = (DecimalFormat) DecimalFormat.getInstance();
			df.applyPattern(Constants.NUMBER_FORMATE_PATTERN_WITH_DOLLER);
			String finalValue = df.format(updatedAmounts);
			initialReportsData.get(recordCountOfPaymentType).put(columnName, finalValue);
		} else {
			initialReportsData.get(recordCountOfPaymentType).put(columnName,
					Constants.DOLLAR_SYMBOL + String.valueOf(updatedAmounts));
		}
	}

	public void calculateNetSalesIncTax(String columnName, int recordCountOfPaymentType) throws Exception {
		String salesAmounts = reportsData.get(recordCountOfPaymentType).get(tableHeaders.get(3));
		String voidAmounts = reportsData.get(recordCountOfPaymentType).get(tableHeaders.get(5));
		String creditRejectedAmounts = reportsData.get(recordCountOfPaymentType).get(tableHeaders.get(7));
		double salesData = Double.parseDouble(salesAmounts.replaceAll(Constants.REPLACE_DOLLOR, Constants.EMPTY_STRING))
				- Double.parseDouble(voidAmounts.replaceAll(Constants.REPLACE_DOLLOR, Constants.EMPTY_STRING))
				- Double.parseDouble(
						creditRejectedAmounts.replaceAll(Constants.REPLACE_DOLLOR, Constants.EMPTY_STRING));
		salesData = Math.round(salesData * 100.0) / 100.0;
		if (salesData < 0) {
			DecimalFormat df = (DecimalFormat) DecimalFormat.getInstance();
			df.applyPattern(Constants.NUMBER_FORMATE_PATTERN_WITH_DOLLER);
			String finalValue = df.format(salesData);
			initialReportsData.get(recordCountOfPaymentType).put(columnName, finalValue);
		} else {
			initialReportsData.get(recordCountOfPaymentType).put(columnName,
					Constants.DOLLAR_SYMBOL + String.valueOf(salesData));
		}
	}

	public void calculateNetTax(String columnName, int recordCountOfPaymentType) throws Exception {
		String salesCounts = reportsData.get(recordCountOfPaymentType).get(tableHeaders.get(2));
		String RejectedCounts = reportsData.get(recordCountOfPaymentType).get(tableHeaders.get(6));
		double taxes = requiredJsonData.get(1) * (Double.parseDouble(salesCounts) - Double.parseDouble(RejectedCounts));
		taxes = Math.round(taxes * 100.0) / 100.0;
		if (taxes < 0) {
			DecimalFormat df = (DecimalFormat) DecimalFormat.getInstance();
			df.applyPattern(Constants.NUMBER_FORMATE_PATTERN_WITH_DOLLER);
			String finalValue = df.format(taxes);
			initialReportsData.get(recordCountOfPaymentType).put(columnName, finalValue);
		} else {
			initialReportsData.get(recordCountOfPaymentType).put(columnName,
					Constants.DOLLAR_SYMBOL + String.valueOf(taxes));
		}
	}

	public void calculateNetTaxForCredit(String columnName, int recordCountOfPaymentType) throws Exception {
		String salesCounts = reportsData.get(recordCountOfPaymentType).get(tableHeaders.get(2));
		String RejectedCounts = reportsData.get(recordCountOfPaymentType).get(tableHeaders.get(6));
		double taxes = requiredJsonData.get(1) * (Double.parseDouble(salesCounts) - Double.parseDouble(RejectedCounts));
		taxes = Math.round(taxes * 100.0) / 100.0;
		if (taxes < 0) {
			DecimalFormat df = (DecimalFormat) DecimalFormat.getInstance();
			df.applyPattern(Constants.NUMBER_FORMATE_PATTERN_WITH_DOLLER);
			String finalValue = df.format(taxes);
			initialReportsData.get(recordCountOfPaymentType).put(columnName, finalValue);
		} else {
			initialReportsData.get(recordCountOfPaymentType).put(columnName,
					Constants.DOLLAR_SYMBOL + String.valueOf(taxes));
		}
	}

	public void calculateNetSales(String columnName, int recordCountOfPaymentType) throws Exception {
		String salesDate = reportsData.get(recordCountOfPaymentType).get(tableHeaders.get(8));
		String taxes = reportsData.get(recordCountOfPaymentType).get(tableHeaders.get(9));
		double NetSales = Double.parseDouble(salesDate.replaceAll(Constants.REPLACE_DOLLOR, Constants.EMPTY_STRING))
				- Double.parseDouble(taxes.replaceAll(Constants.REPLACE_DOLLOR, Constants.EMPTY_STRING));
		NetSales = Math.round(NetSales * 100.0) / 100.0;
		if (NetSales < 0) {
			DecimalFormat df = (DecimalFormat) DecimalFormat.getInstance();
			df.applyPattern(Constants.NUMBER_FORMATE_PATTERN_WITH_DOLLER);
			String finalValue = df.format(NetSales);
			initialReportsData.get(recordCountOfPaymentType).put(columnName, finalValue);
		} else {
			initialReportsData.get(recordCountOfPaymentType).put(columnName,
					Constants.DOLLAR_SYMBOL + String.valueOf(NetSales));
		}
	}

	public void calculateProductCountsForTotals(String columnName, int recordCountOfPaymentType) throws Exception {
		String initialCounts = initialReportsData.get(recordCountOfPaymentType).get(columnName);
		int updatedCounts = Integer.parseInt(initialCounts) + (6 * 2);
		initialReportsData.get(recordCountOfPaymentType).put(columnName, String.valueOf(updatedCounts));
	}

	public void calculateCountsForTotals(String columnName, int recordCountOfPaymentType) throws Exception {
		String initialCounts = initialReportsData.get(recordCountOfPaymentType).get(columnName);
		int updatedCounts = Integer.parseInt(initialCounts) + 5;
		initialReportsData.get(recordCountOfPaymentType).put(columnName, String.valueOf(updatedCounts));
	}

	public void calculateAmountsForTotals(String columnName, int recordCountOfPaymentType) throws Exception {
		String initialAmounts = initialReportsData.get(recordCountOfPaymentType).get(columnName);
		double amount = (requiredJsonData.get(0) + requiredJsonData.get(1)) * 5;
		double updatedAmounts = Double
				.parseDouble(initialAmounts.replaceAll(Constants.REPLACE_DOLLOR, Constants.EMPTY_STRING)) + amount;
		updatedAmounts = Math.round(updatedAmounts * 100.0) / 100.0;
		if (updatedAmounts < 0) {
			DecimalFormat df = (DecimalFormat) DecimalFormat.getInstance();
			df.applyPattern(Constants.NUMBER_FORMATE_PATTERN_WITH_DOLLER);
			String finalValue = df.format(updatedAmounts);
			initialReportsData.get(recordCountOfPaymentType).put(columnName, finalValue);
		} else {
			initialReportsData.get(recordCountOfPaymentType).put(columnName,
					Constants.DOLLAR_SYMBOL + String.valueOf(updatedAmounts));
		}
	}

	public void calculateNetSalesIncTaxForTotals(String columnName, int recordCountOfPaymentType) throws Exception {
		String salesAmounts = reportsData.get(recordCountOfPaymentType).get(tableHeaders.get(3));
		String voidAmounts = reportsData.get(recordCountOfPaymentType).get(tableHeaders.get(5));
		String creditRejectedAmounts = reportsData.get(recordCountOfPaymentType).get(tableHeaders.get(7));
		double salesData = Double.parseDouble(salesAmounts.replaceAll(Constants.REPLACE_DOLLOR, Constants.EMPTY_STRING))
				- Double.parseDouble(voidAmounts.replaceAll(Constants.REPLACE_DOLLOR, Constants.EMPTY_STRING))
				- Double.parseDouble(
						creditRejectedAmounts.replaceAll(Constants.REPLACE_DOLLOR, Constants.EMPTY_STRING));
		salesData = Math.round(salesData * 100.0) / 100.0;
		if (salesData < 0) {
			DecimalFormat df = (DecimalFormat) DecimalFormat.getInstance();
			df.applyPattern(Constants.NUMBER_FORMATE_PATTERN_WITH_DOLLER);
			String finalValue = df.format(salesData);
			initialReportsData.get(recordCountOfPaymentType).put(columnName, finalValue);
		} else {
			initialReportsData.get(recordCountOfPaymentType).put(columnName,
					Constants.DOLLAR_SYMBOL + String.valueOf(salesData));
		}
	}

	public void calculateNetTaxForTotals(String columnName, int recordCountOfPaymentType) throws Exception {
		String salesCounts = reportsData.get(recordCountOfPaymentType).get(tableHeaders.get(2));
		String RejectedCounts = reportsData.get(recordCountOfPaymentType).get(tableHeaders.get(6));
		double taxes = requiredJsonData.get(1) * (Double.parseDouble(salesCounts) - Double.parseDouble(RejectedCounts));
		taxes = Math.round(taxes * 100.0) / 100.0;
		if (taxes < 0) {
			DecimalFormat df = (DecimalFormat) DecimalFormat.getInstance();
			df.applyPattern(Constants.NUMBER_FORMATE_PATTERN_WITH_DOLLER);
			String finalValue = df.format(taxes);
			initialReportsData.get(recordCountOfPaymentType).put(columnName, finalValue);
		} else {
			initialReportsData.get(recordCountOfPaymentType).put(columnName,
					Constants.DOLLAR_SYMBOL + String.valueOf(taxes));
		}
	}

	public void calculateNetSalesForTotals(String columnName, int recordCountOfPaymentType) throws Exception {
		String salesDate = reportsData.get(recordCountOfPaymentType).get(tableHeaders.get(8));
		String taxes = reportsData.get(recordCountOfPaymentType).get(tableHeaders.get(9));
		double NetSales = Double.parseDouble(salesDate.replaceAll(Constants.REPLACE_DOLLOR, Constants.EMPTY_STRING))
				- Double.parseDouble(taxes.replaceAll(Constants.REPLACE_DOLLOR, Constants.EMPTY_STRING));
		NetSales = Math.round(NetSales * 100.0) / 100.0;
		if (NetSales < 0) {
			DecimalFormat df = (DecimalFormat) DecimalFormat.getInstance();
			df.applyPattern(Constants.NUMBER_FORMATE_PATTERN_WITH_DOLLER);
			String finalValue = df.format(NetSales);
			initialReportsData.get(recordCountOfPaymentType).put(columnName, finalValue);
		} else {
			initialReportsData.get(recordCountOfPaymentType).put(columnName,
					Constants.DOLLAR_SYMBOL + String.valueOf(NetSales));
		}
	}

	public void verifyReportRecords() throws Exception {
		int count = initialReportsData.size();
		for (int counter = 0; counter < count; counter++) {
			if (counter == 7) {
				break;
			} else {
				for (int iter = 0; iter < tableHeaders.size(); iter++) {
					CustomisedAssert.assertTrue(reportsData.get(counter).get(tableHeaders.get(iter))
							.contains(initialReportsData.get(counter).get(tableHeaders.get(iter))));
				}
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

	public Map<Integer, Map<String, String>> getReportsTotalData() {
		return reportsTotalData;
	}

	public List<String> getTableHeaders() {
		return tableHeaders;
	}
}
