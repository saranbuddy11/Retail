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
import at.framework.reportsetup.ExtFactory;
import at.framework.ui.Foundation;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.keys.Reports;
import at.smartshop.tests.TestInfra;
import at.smartshop.utilities.WebService;

public class CashFlowEmployeeDevice extends Factory {
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

	private Map<String, Object> jsonData = new HashMap<>();
	private Map<Integer, Map<String, String>> initialReportsData = new HashMap<>();
	private Map<Integer, Map<String, String>> initialReportTotals = new HashMap<>();
	private Map<Integer, Map<String, String>> reportsTotalData = new LinkedHashMap<>();
	private Map<Integer, Map<String, String>> cashFlowDetailsTotal = new LinkedHashMap<>();
	private Map<Integer, Map<String, String>> calculateCashFlowTotal = new LinkedHashMap<>();
	private Map<Integer, Map<String, String>> cashFlowDetailsTotalsSum = new LinkedHashMap<>();
	public static Map<Integer, Map<String, String>> reportsData = new LinkedHashMap<>();
	private List<Double> requiredJsonData = new LinkedList<>();
	private List<Integer> requiredData = new LinkedList<>();
	private List<Integer> requiredCount = new LinkedList<>();
	private List<String> intialReportsData = new ArrayList<>();
	private List<String> tableHeaders = new ArrayList<>();
	private JsonObject sales;
	public String staffName;

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
			Assert.assertTrue(reportTitle.contains(reportName));
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

	public void calculateIntegerTotal(String columnName) {
		int rowSize = cashFlowDetailsTotal.size();
		int totalValue = 0;
		for (int iter = 0; iter < rowSize; iter++) {
			int value = Integer.parseInt(cashFlowDetailsTotal.get(iter).get(columnName)
					.replaceAll(Constants.REPLACE_DOLLOR, Constants.EMPTY_STRING));
			totalValue = totalValue + value;
		}
		calculateCashFlowTotal.get(0).put(columnName, String.valueOf(totalValue));
	}

	public void calculateDoubleTotal(String columnName) {
		int rowSize = cashFlowDetailsTotal.size();
		double totalValue = 0.0;
		for (int iter = 0; iter < rowSize; iter++) {
			double value = Double.parseDouble(cashFlowDetailsTotal.get(iter).get(columnName)
					.replaceAll(Constants.REPLACE_DOLLOR, Constants.EMPTY_STRING));
			totalValue = totalValue + value;
			totalValue = Math.round(totalValue * 100.0) / 100.0;
		}
		calculateCashFlowTotal.get(0).put(columnName, String.valueOf(totalValue));
	}

//current_loc=Automation@365
	public void calculateCashFlowDetailsTotals(String location) throws Exception {
		cashFlowDetailsTotal.clear();
		String locationName = location.replace("@", Constants.DELIMITER_HYPHEN);
		WebElement containers = getDriver().findElement(By.id(REPORTS_CONTAINERS));
		List<WebElement> tables = containers.findElements(By.cssSelector("div > div > div > table"));
		int counter = 0;
		int iterCount = 2;
		for (int iter = 0; iter < tables.size(); iter++) {
			Map<String, String> reportsdata = new LinkedHashMap<>();
			String id = tables.get(iter).getAttribute("id");
			iterCount++;
			if (id.contains(locationName)) {
				List<WebElement> rows = tables.get(iter).findElements(By.cssSelector("tbody > tr"));
				int count = rows.size();
				if (count >= 4) {
					WebElement totalRow = getDriver()
							.findElement(By.cssSelector("#report-container > div:nth-child(" + (iterCount)
									+ ") > div:nth-child(2) > div > table > tbody > tr:nth-child(" + count + ")"));
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

//deviceID = Vsh601263    current_loc=Automation@365
	public void readAllRecordsFromCashFlowDetailsTable(String deviceId, String location) throws Exception {
		int locCount = getLocationCount();
		reportsData.clear();
		int count = 0;

		String locationName = location.replace("@", Constants.DELIMITER_HYPHEN);
		for (int iteration = 3; iteration < locCount + 3; iteration++) {
			String tableCashFlowDetails = locationName + Constants.DELIMITER_HYPHEN + staffName
					+ Constants.DELIMITER_HYPHEN + Constants.DELIMITER_HYPHEN + deviceId.toUpperCase()
					+ Constants.DELIMITER_HYPHEN;
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

	public void verifyReportHeaders(String columnNames) {
		List<String> columnName = Arrays.asList(columnNames.split(Constants.DELIMITER_HASH));
		for (int iter = 0; iter < tableHeaders.size(); iter++) {
			Assert.assertTrue(tableHeaders.get(iter).equals(columnName.get(iter)));
		}
	}

	// deviceID = Vsh601263 current_loc=Automation@365
	public int getSubHeaderCount(String deviceName, String location, String columnName, String columnValue)
			throws Exception {
		String locationName = location.replace("@", Constants.DELIMITER_HYPHEN);
		String tableCashFlowDetails = locationName + Constants.DELIMITER_HYPHEN + staffName + Constants.DELIMITER_HYPHEN
				+ Constants.DELIMITER_HYPHEN + deviceName.toUpperCase() + Constants.DELIMITER_HYPHEN;
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
		int rowCount = 0;
		WebElement tableReportsList = getDriver()
				.findElement(By.cssSelector(Constants.DELIMITER_HASH + tableCashFlowDetails + " > tbody"));
		List<WebElement> rows = tableReportsList.findElements(By.tagName("tr"));
		for (WebElement row : rows) {
			rowCount++;
			WebElement subHeaderColumn = row.findElement(By.cssSelector("td:nth-child(" + subHeaderCount + ")"));
			if (subHeaderColumn.getText().equals(columnValue)) {
				count = rowCount;
				break;
			}
		}
		return count;
	}

	// deviceID = Vsh601263 current_loc=Automation@365
	public void getHeaderCounts(String deviceName, String location, String columnName, String reqColumnName,
			String columnValue, String reqColumnValue) throws Exception {
		String locationName = location.replace("@", Constants.DELIMITER_HYPHEN);
		String tableCashFlowDetails = locationName + Constants.DELIMITER_HYPHEN + staffName + Constants.DELIMITER_HYPHEN
				+ Constants.DELIMITER_HYPHEN + deviceName.toUpperCase() + Constants.DELIMITER_HYPHEN;
		requiredData.clear();
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
		int rowCount = 0;
		for (WebElement row : rows) {
			rowCount++;
			WebElement subHeaderColumn = row.findElement(By.cssSelector("td:nth-child(" + subHeaderCount + ")"));
			WebElement reqColumn = row.findElement(By.cssSelector("td:nth-child(" + reqColumnCount + ")"));
			if (subHeaderColumn.getText().equals(columnValue)) {
				count = rowCount;
			}
			if (reqColumn.getText().equals(reqColumnValue)) {
				reqCount = rowCount;
				break;
			}
		}
		requiredData.add(count);
		requiredData.add(reqCount);
	}

	public void calculateCreditCardSubTotalCounts(String deviceName, String location, String columnName,
			String columnValue) throws Exception {
		List<String> colName = Arrays.asList(columnName.split(Constants.DELIMITER_HASH));
		int count = getSubHeaderCount(deviceName, location, colName.get(0), columnValue);
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

	public void calculateCreditCardSubTotalAmounts(String deviceName, String location, String columnName,
			String columnValue) throws Exception {
		List<String> colName = Arrays.asList(columnName.split(Constants.DELIMITER_HASH));
		int count = getSubHeaderCount(deviceName, location, colName.get(0), columnValue);
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

	public void calculateLocationTotalsAmounts(String columnName, String columnValue) throws Exception {
		double totalAmount = 0;
		double amount = 0;
		double subCreditTotal = 0;
		List<String> colName = Arrays.asList(columnName.split(Constants.DELIMITER_HASH));
		List<String> colValue = Arrays.asList(columnValue.split(Constants.DELIMITER_HASH));
		int counter = initialReportsData.size() - 1;
		for (int iter = 0; iter < counter; iter++) {
			if (!initialReportsData.get(iter).get(tableHeaders.get(0)).contains("Credit")) {
				amount = Double.parseDouble(reportsData.get(iter).get(colName.get(1)));
				totalAmount = totalAmount + amount;
			}
			if (initialReportsData.get(iter).get(tableHeaders.get(0)).contains(colValue.get(0))) {
				subCreditTotal = Double.parseDouble(reportsData.get(iter).get(colName.get(1)));
			}
		}
		double updatedAmount = totalAmount + subCreditTotal;
		updatedAmount = Math.round(updatedAmount * 100.0) / 100.0;
		initialReportsData.get(counter).put(colName.get(1), String.valueOf(updatedAmount));
	}

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

	public void calculateCounts(String device, String location, String columnName, String columnValue, int count)
			throws Exception {
		List<String> colName = Arrays.asList(columnName.split(Constants.DELIMITER_HASH));
		String initialCounts = initialReportsData
				.get(getSubHeaderCount(device, location, colName.get(0), columnValue) - 1).get(colName.get(1));
		int updatedCounts = Integer.parseInt(initialCounts) + count;
		initialReportsData.get(getSubHeaderCount(device, location, colName.get(0), columnValue) - 1).put(colName.get(1),
				String.valueOf(updatedCounts));
	}

	public void calculateAmounts(String device, String location, String columnName, String columnValue)
			throws Exception {
		List<String> colName = Arrays.asList(columnName.split(Constants.DELIMITER_HASH));
		String initialAmounts = initialReportsData
				.get(getSubHeaderCount(device, location, colName.get(0), columnValue) - 1).get(colName.get(1));
		double amount = requiredJsonData.get(0) + requiredJsonData.get(1);
		double updatedAmounts = Double
				.parseDouble(initialAmounts.replaceAll(Constants.REPLACE_DOLLOR, Constants.EMPTY_STRING)) + amount;
		updatedAmounts = Math.round(updatedAmounts * 100.0) / 100.0;
		initialReportsData.get(getSubHeaderCount(device, location, colName.get(0), columnValue) - 1).put(colName.get(1),
				String.valueOf(updatedAmounts));
	}

	public void calculateDeclinedAmounts(String device, String location, String columnName, String columnValue)
			throws Exception {
		double updatedAmounts = 0;
		List<String> colName = Arrays.asList(columnName.split(Constants.DELIMITER_HASH));
		String initialAmounts = initialReportsData
				.get(getSubHeaderCount(device, location, colName.get(0), columnValue) - 1).get(colName.get(1));
		double amount = requiredJsonData.get(0) + requiredJsonData.get(1);
		if (initialAmounts.contains("0.00")) {
			updatedAmounts = Math.round(updatedAmounts * 100.0) / 100.0;
			initialReportsData.get(getSubHeaderCount(device, location, colName.get(0), columnValue) - 1)
					.put(colName.get(1), String.valueOf(updatedAmounts));
		} else {
			updatedAmounts = Double
					.parseDouble(initialAmounts.replaceAll(Constants.REPLACE_DOLLOR, Constants.EMPTY_STRING)) + amount;
			updatedAmounts = Math.round(updatedAmounts * 100.0) / 100.0;
			initialReportsData.get(getSubHeaderCount(device, location, colName.get(0), columnValue) - 1)
					.put(colName.get(1), String.valueOf(updatedAmounts));
		}
	}

	public void calculateTotalsColumnData(String device, String location, String columnName, String columnValue)
			throws Exception {
		String total;
		List<String> colName = Arrays.asList(columnName.split(Constants.DELIMITER_HASH));
		String salesDate = reportsData.get(getSubHeaderCount(device, location, colName.get(0), columnValue) - 1)
				.get(colName.get(1));
		String taxes = reportsData.get(getSubHeaderCount(device, location, colName.get(0), columnValue) - 1)
				.get(colName.get(2));
		String tipsAmount = reportsData.get(getSubHeaderCount(device, location, colName.get(0), columnValue) - 1)
				.get(colName.get(3));
		double totals = Double.parseDouble(tipsAmount.replaceAll(Constants.REPLACE_DOLLOR, Constants.EMPTY_STRING))
				+ Double.parseDouble(salesDate.replaceAll(Constants.REPLACE_DOLLOR, Constants.EMPTY_STRING))
				+ Double.parseDouble(taxes.replaceAll(Constants.REPLACE_DOLLOR, Constants.EMPTY_STRING));
		totals = Math.round(totals * 100.0) / 100.0;
		if (totals < 0) {
			total = String.valueOf(totals).replaceAll(Constants.DELIMITER_HYPHEN, Constants.EMPTY_STRING);
		} else {
			total = String.valueOf(totals);
		}
		initialReportsData.get(getSubHeaderCount(device, location, colName.get(0), columnValue) - 1).put(colName.get(4),
				String.valueOf(total));
	}

	public void verifyReportRecords() throws Exception {
		int count = initialReportsData.size();
		int coulumnCount = tableHeaders.size();
		for (int val = 1; val < coulumnCount; val++) {
			Assert.assertTrue(reportsTotalData.get(0).get(tableHeaders.get(val)).contains(cashFlowDetailsTotalsSum
					.get(0).get(tableHeaders.get(val)).replaceAll(Constants.REPLACE_DOLLOR, Constants.EMPTY_STRING)));
		}
		for (int iter = 0; iter < count; iter++) {
			for (int val = 0; val < coulumnCount; val++) {
				Assert.assertTrue(reportsData.get(iter).get(tableHeaders.get(val))
						.contains(initialReportsData.get(iter).get(tableHeaders.get(val))));
			}
		}

	}

	public static void jsonArrayDataUpdate(JsonObject jsonObj, String reqString, String transID, String salesheader,
			String transDate, String transStatus, String paymentType) {
		JsonArray jsonarray = jsonObj.get(reqString).getAsJsonArray();
		for (JsonElement jsonarr : jsonarray) {
			JsonObject element = jsonarr.getAsJsonObject();
			element.addProperty(Reports.ID,
					UUID.randomUUID().toString().replace(Constants.DELIMITER_HYPHEN, Constants.EMPTY_STRING));
			element.addProperty(Reports.SALES_HEADER, salesheader);
			element.addProperty(Reports.TRANS_ID, transID);
			element.addProperty(Reports.TRANS_DATE, transDate);
		}
		if (reqString.equals(Reports.PAYMENTS)) {
			for (JsonElement jsonarr : jsonarray) {
				JsonObject element = jsonarr.getAsJsonObject();
				element.addProperty(Reports.STATUS, transStatus);
				element.addProperty(Reports.TYPE, paymentType);
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
	public void processAPI(String transStatus, String paymentType, String deviceID) throws Exception {
		requiredCount.clear();
		int credCount = 0;
		int voidCredCount = 0;
		int declinedCredCount = 0;
		int accCount = 0;
		int accVoidCount = 0;
		int tipCount = 0;
		List<String> tStatus = Arrays.asList(transStatus.split(Constants.DELIMITER_HASH));
		List<String> payType = Arrays.asList(paymentType.split(Constants.DELIMITER_HASH));
		for (int iterator = 0; iterator < payType.size(); iterator++) {
			for (int iter = 0; iter < tStatus.size(); iter++) {
				webService.apiReportPostRequest(
						propertyFile.readPropertyFile(Configuration.TRANS_SALES, FilePath.PROPERTY_CONFIG_FILE),
						salesJsonDataUpdate(tStatus.get(iter), payType.get(iterator), deviceID));
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
				}
			}
		}
		requiredCount.add(credCount);
		requiredCount.add(accCount);
		requiredCount.add(tipCount);
		requiredCount.add(accVoidCount);
		requiredCount.add(voidCredCount);
		requiredCount.add(declinedCredCount);
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

	public String salesJsonDataUpdate(String transStatus, String paymentType, String deviceID) throws Exception {
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(Reports.DATE_FORMAT);
		String transDate = LocalDateTime.now().format(dateFormat);
		String salesHeaderID = UUID.randomUUID().toString().replace(Constants.DELIMITER_HYPHEN, Constants.EMPTY_STRING);
		String transID = deviceID + Constants.DELIMITER_HYPHEN
				+ transDate.replaceAll(Constants.REGEX_TRANS_DATE, Constants.EMPTY_STRING);
		String saleValue = jsonFunctions.readFileAsString(FilePath.JSON_SALES_CREATION);
		JsonObject jsonData = jsonFunctions.convertStringToJson(saleValue);
		jsonData.addProperty(Reports.TRANS_ID, transID);
		jsonData.addProperty(Reports.TRANS_DATE, transDate);
		String sale = jsonData.get(Reports.SALE).getAsString();
		sales = jsonFunctions.convertStringToJson(sale);
		sales.addProperty(Reports.ID, salesHeaderID);
		sales.addProperty(Reports.TRANS_ID, transID);
		sales.addProperty(Reports.TRANS_DATE, transDate);
		jsonArrayDataUpdate(sales, Reports.ITEMS, transID, salesHeaderID, transDate, transStatus, paymentType);
		jsonArrayDataUpdate(sales, Reports.PAYMENTS, transID, salesHeaderID, transDate, transStatus, paymentType);
		jsonData.addProperty(Reports.SALE, sales.toString());
		return jsonData.toString();
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
