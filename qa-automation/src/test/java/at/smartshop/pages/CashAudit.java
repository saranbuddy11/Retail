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

import com.aventstack.extentreports.Status;
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

public class CashAudit extends Factory {
	private Foundation foundation = new Foundation();
	private WebService webService = new WebService();
	private JsonFile jsonFunctions = new JsonFile();
	private NavigationBar navigationBar = new NavigationBar();
	private ReportList reportList = new ReportList();

	public List<String> tableHeaders = new ArrayList<>();
	private List<String> requiredCashOutJsonData = new LinkedList<>();
	private List<String> requiredGMAJsonData = new LinkedList<>();
	private Map<Integer, Map<String, String>> reportsData = new LinkedHashMap<>();
	public Map<Integer, Map<String, String>> reportSecondLayerData = new LinkedHashMap<>();
	private Map<Integer, Map<String, String>> lastPickupData = new HashMap<>();
	private Map<String, Object> data = new HashMap<>();
	private Map<String, String> gmaData = new HashMap<>();
	private Map<String, String> kcoData = new HashMap<>();

	private String transID = Constants.EMPTY_STRING;
	private String transDate = Constants.EMPTY_STRING;

	public static final By LBL_REPORT_NAME = By
			.cssSelector("#report-container > div.span12.rpt-data > div.col-12.comment-table-heading");
	private static final By REPORT_GRID_FIRST_ROW = By.cssSelector("#rptdt > tbody");
	private static final By NO_DATA_AVAILABLE_IN_TABLE = By.xpath("//td[@class='dataTables_empty']");
	public static final By DATA_EXISTING_DATE = By.cssSelector(
			"body > div.daterangepicker.ltr.show-ranges.opensright.show-calendar > div.drp-calendar.right > div.calendar-table > table > tbody > tr:nth-child(3) > td:nth-child(4)");
	public static final By TABLE_CASH_AUDIT = By.id("rptdt");
	public static final By ROW_LAST_PICKUP = By.id("lastpickup");
	public static final By TXT_SEARCH_FILTER = By.cssSelector("#rptdt_filter > label > [aria-controls='rptdt']");
	public static final By DATA_EXISTING_START_DATE_STAGING = By.cssSelector(
			"body > div.daterangepicker.ltr.show-ranges.opensright.show-calendar > div.drp-calendar.right > div.calendar-table > table > tbody > tr:nth-child(3) > td:nth-child(6)");
	public static final By DATA_EXISTING_END_DATE_STAGING = By.cssSelector(
			"body > div.daterangepicker.ltr.show-ranges.opensright.show-calendar > div.drp-calendar.right > div.calendar-table > table > tbody > tr:nth-child(3) > td:nth-child(6)");


	/**
	 * Verify the Report Name
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
	 * Read all records from Cash Audit Table
	 */
	public void readAllRecordsFromCashAuditTable() {
		tableHeaders.clear();
		WebElement tableReports = getDriver().findElement(TABLE_CASH_AUDIT);
		List<WebElement> headers = tableReports.findElements(By.cssSelector("thead > tr > th"));
		for (WebElement header : headers) {
			tableHeaders.add(header.getText());
		}
		WebElement tableReportsList = getDriver().findElement(REPORT_GRID_FIRST_ROW);
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
		List<WebElement> footers = tableReports.findElements(By.cssSelector("tfoot > tr"));
		int counter = 0;
		for (WebElement footer : footers) {
			Map<String, String> reportsdata = new LinkedHashMap<>();
			for (int iter = 1; iter < tableHeaders.size() + 1; iter++) {
				WebElement column = footer.findElement(By.cssSelector("th:nth-child(" + iter + ")"));
				reportsdata.put(tableHeaders.get(iter - 1), column.getText());
			}
			reportSecondLayerData.put(counter, reportsdata);
			counter++;
		}
	}

	/**
	 * Get Total Row Count
	 * 
	 * @param requiredData
	 * @param requiredMap
	 * @param columnName
	 * @return
	 */
	private int totalRowCount(String requiredData, Map<Integer, Map<String, String>> requiredMap, String columnName) {
		int rowCount = 0;
		int count = requiredMap.size();
		for (int iter = 0; iter < count; iter++) {
			String actualData = requiredMap.get(iter).get(columnName);
			if (actualData.equalsIgnoreCase(requiredData)) {
				rowCount = iter;
			}
		}
		return rowCount;
	}

	/**
	 * Get Row Count
	 * 
	 * @param requiredData
	 * @param requiredMap
	 * @param columnName
	 * @param reqColumn
	 * @return
	 */
	private int rowCount(String requiredData, Map<Integer, Map<String, String>> requiredMap, String columnName,
			String reqColumn) {
		int rowCount = 0;
		int count = requiredMap.size();
		for (int iter = 0; iter < count; iter++) {
			String actualData = requiredMap.get(iter).get(columnName);
			String reqColumnData = requiredMap.get(iter).get(reqColumn);
			if (actualData.equalsIgnoreCase(requiredData) && reqColumnData.equals(Constants.EMPTY_STRING)) {
				rowCount = iter;
				break;
			}
		}
		return rowCount;
	}

	/**
	 * Calculate Total
	 * 
	 * @param columnName
	 * @return
	 */
	private double calculateTotal(String columnName) {
		int count = reportsData.size();
		double updatedTotal = 0;
		for (int iter = 0; iter < count; iter++) {
			String value = reportsData.get(iter).get(columnName).replaceAll(Constants.REPLACE_DOLLOR,
					Constants.EMPTY_STRING);
			if (value.equals(Constants.EMPTY_STRING)) {
				value = String.valueOf("0");
			}
			double total = Double.parseDouble(value);
			updatedTotal = updatedTotal + total;
		}
		return updatedTotal;
	}

	/**
	 * Verify Total
	 * 
	 * @param columnName
	 */
	public void verifyTotal(String columnName) {
		double updatedTotal = calculateTotal(columnName);
		int count = getTotalRowCount(Reports.TOTAL, reportSecondLayerData, tableHeaders.get(1));
		CustomisedAssert
				.assertTrue(reportSecondLayerData.get(count).get(columnName).contains(String.valueOf(updatedTotal)));
	}

	/**
	 * Verify Last Pickup
	 * 
	 * @throws Exception
	 */
	public void verifyLastPickUp() throws Exception {
		updateLastPickUpData();
		int count = getTotalRowCount(Reports.TOTAL, reportSecondLayerData, tableHeaders.get(1)) + 1;
		for (int iter = 0; iter < tableHeaders.size(); iter++) {
			CustomisedAssert.assertTrue(reportSecondLayerData.get(count).get(tableHeaders.get(iter))
					.contains(lastPickupData.get(count).get(tableHeaders.get(iter))));
		}
	}

	/**
	 * Update Last Pickup data
	 * 
	 * @throws Exception
	 */
	public void updateLastPickUpData() throws Exception {
		int count = getTotalRowCount(Reports.TOTAL, reportSecondLayerData, tableHeaders.get(1)) + 1;
		lastPickupData.get(count).put(tableHeaders.get(1), "LASTPICKUP-"
				+ propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));
		lastPickupData.get(count).put(tableHeaders.get(2), Constants.EMPTY_STRING);
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
	 * Update GMA Record
	 * 
	 * @param consumerName
	 */
	public void updateGMARecord(String consumerName) {
		int count = getRowCount(getRequiredGMAJsonData().get(0), reportsData, tableHeaders.get(0), tableHeaders.get(4));
		gmaData.putAll(reportsData.get(count));
		gmaData.put(tableHeaders.get(1), requiredCashOutJsonData.get(1));
//		gmaData.put(tableHeaders.get(2), consumerName);
		// gmaData.put(tableHeaders.get(3), requiredGMAJsonData.get(1));
//		gmaData.put(tableHeaders.get(6), "-5");
	}

	/**
	 * Verify GMA Record
	 */
	public void verifyGMARecord() {
		int counter = getRowCount(getRequiredGMAJsonData().get(0), reportsData, tableHeaders.get(0),
				tableHeaders.get(4));
		int count = tableHeaders.size();
		for (int iter = 0; iter < count; iter++) {
			CustomisedAssert.assertTrue(
					reportsData.get(counter).get(tableHeaders.get(iter)).contains(gmaData.get(tableHeaders.get(iter))));
		}
	}

	/**
	 * Update Kiosk Cash out Record
	 */
	public void updateKCORecord() {
		int count = getRowCount(getRequiredCashOutJsonData().get(0), reportsData, tableHeaders.get(0),
				tableHeaders.get(3));
		kcoData.putAll(reportsData.get(count));
		kcoData.put(tableHeaders.get(1), requiredCashOutJsonData.get(1));
		kcoData.put(tableHeaders.get(2),
				requiredCashOutJsonData.get(2).replace(Constants.DELIMITER_SPACE, Constants.EMPTY_STRING));
		kcoData.put(tableHeaders.get(4), requiredCashOutJsonData.get(3));
		kcoData.put(tableHeaders.get(5), requiredCashOutJsonData.get(4));
		kcoData.put(tableHeaders.get(6), requiredCashOutJsonData.get(5));
		kcoData.put(tableHeaders.get(7), requiredCashOutJsonData.get(6));
		kcoData.put(tableHeaders.get(8), requiredCashOutJsonData.get(7));
		kcoData.put(tableHeaders.get(9), requiredCashOutJsonData.get(8));
		kcoData.put(tableHeaders.get(10), requiredCashOutJsonData.get(9));
		kcoData.put(tableHeaders.get(11), requiredCashOutJsonData.get(10));
	}

	/**
	 * Verify KCO Record
	 */
	public void verifyKCORecord() {
		int counter = getRowCount(getRequiredCashOutJsonData().get(0), reportsData, tableHeaders.get(0),
				tableHeaders.get(3));
		int count = tableHeaders.size();
		for (int iter = 0; iter < count; iter++) {
			CustomisedAssert.assertTrue(
					reportsData.get(counter).get(tableHeaders.get(iter)).contains(kcoData.get(tableHeaders.get(iter))));
		}
	}

	/**
	 * Get Cashout Json Data
	 * 
	 * @throws Exception
	 */
	public void getCashoutJsonData() throws Exception {
		JsonObject kioskCashOut = (JsonObject) data.get(Reports.KIOSK_CASH_OUT_TRANS);
		String location = kioskCashOut.get(Reports.LOC_NAME).getAsString();
		requiredCashOutJsonData.add(location);
		String consumer = kioskCashOut.get(Reports.USER_NAME).getAsString();
		requiredCashOutJsonData.add(consumer);
		String totalMinus = kioskCashOut.get(Reports.TOTAL_CASH).getAsString();
		requiredCashOutJsonData.add(totalMinus);
		String bills1 = kioskCashOut.get(Reports.BILLS1).getAsString();
		requiredCashOutJsonData.add(Constants.DELIMITER_HYPHEN + bills1);
		String bills2 = kioskCashOut.get(Reports.BILLS2).getAsString();
		requiredCashOutJsonData.add(Constants.DELIMITER_HYPHEN + bills2);
		String bills3 = kioskCashOut.get(Reports.BILLS3).getAsString();
		requiredCashOutJsonData.add(Constants.DELIMITER_HYPHEN + bills3);
		String bills4 = kioskCashOut.get(Reports.BILLS4).getAsString();
		requiredCashOutJsonData.add(Constants.DELIMITER_HYPHEN + bills4);
		String bills5 = kioskCashOut.get(Reports.BILLS5).getAsString();
		requiredCashOutJsonData.add(Constants.DELIMITER_HYPHEN + bills5);
		String bills6 = kioskCashOut.get(Reports.BILLS6).getAsString();
		requiredCashOutJsonData.add(Constants.DELIMITER_HYPHEN + bills6);
		String bills7 = kioskCashOut.get(Reports.BILLS7).getAsString();
		requiredCashOutJsonData.add(Constants.DELIMITER_HYPHEN + bills7);
	}

	/**
	 * Generate Json Details
	 * 
	 * @throws Exception
	 */
	public void generateJsonDetails(String environment) throws Exception {
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(Reports.DATE_FORMAT);
		LocalDateTime tranDate = LocalDateTime.now();
		transDate = tranDate.format(dateFormat);
		if (environment.equals(Constants.STAGING)) {
			transID = propertyFile.readPropertyFile(Configuration.DEVICE_ID_STAGING, FilePath.PROPERTY_CONFIG_FILE)
					+ Constants.DELIMITER_HYPHEN
					+ transDate.replaceAll(Constants.REGEX_TRANS_DATE, Constants.EMPTY_STRING);
		} else {
			transID = propertyFile.readPropertyFile(Configuration.DEVICE_ID, FilePath.PROPERTY_CONFIG_FILE)
					+ Constants.DELIMITER_HYPHEN
					+ transDate.replaceAll(Constants.REGEX_TRANS_DATE, Constants.EMPTY_STRING);
		};

		data.put(Reports.TRANS_DATE_TIME, tranDate);
	}

	/**
	 * Process Sales API
	 * 
	 * @param amount
	 * @param timeFormat
	 * @throws Exception
	 */
	public void processAPI(String amount, String timeFormat, String environment) throws Exception {
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(timeFormat);
		generateJsonDetails(environment);
		gmaAddValueJsonDataUpdate(amount, environment);
		String gmaDate = ((LocalDateTime) data.get(Reports.TRANS_DATE_TIME)).format(dateFormat);
		webService.apiReportPostRequest(
				propertyFile.readPropertyFile(Configuration.TRANS_GMA, FilePath.PROPERTY_CONFIG_FILE),
				(String) data.get(Reports.GMA_JSON));
		requiredGMAJsonData.add(gmaDate);
		requiredGMAJsonData.add(amount);
		generateJsonDetails(environment);
		kioskCashOutJsonDataUpdate(environment);
		String kcoDate = ((LocalDateTime) data.get(Reports.TRANS_DATE_TIME)).format(dateFormat);
		webService.apiReportPostRequest(
				propertyFile.readPropertyFile(Configuration.KCO_TRANS_KEY, FilePath.PROPERTY_CONFIG_FILE),
				(String) data.get(Reports.KIOSK_CASH_OUT_JSON));
		requiredCashOutJsonData.add(kcoDate);
	}

	/**
	 * GMA add value Json Data Update
	 * 
	 * @param amount
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> gmaAddValueJsonDataUpdate(String amount, String environment) throws Exception {

		String jsonGMAAddValue;
		if (environment.equals(Constants.STAGING)) {
			 jsonGMAAddValue = jsonFunctions.readFileAsString(FilePath.JSON_GMA_ADD_VALUE_STAGING);
		} else {
			 jsonGMAAddValue = jsonFunctions.readFileAsString(FilePath.JSON_GMA_ADD_VALUE);
		};

		JsonObject jsonGMAAddValueData = jsonFunctions.convertStringToJson(jsonGMAAddValue);
		jsonGMAAddValueData.addProperty(Reports.TRANS_ID, transID);
		jsonGMAAddValueData.addProperty(Reports.TRANS_DATE, transDate);
		String gmaJsonData = jsonGMAAddValueData.get(Reports.DATA).getAsString();
		JsonObject gmaAddValue = jsonFunctions.convertStringToJson(gmaJsonData);
		gmaAddValue.addProperty(Reports.TRANS_ID, transID);
		gmaAddValue.addProperty(Reports.TRANS_DATE, transDate);
		gmaAddValue.addProperty(Reports.AMOUNT, amount);
		jsonGMAAddValueData.addProperty(Reports.DATA, gmaAddValue.toString());
		data.put(Reports.GMA_JSON, jsonGMAAddValueData.toString());
		data.put(Reports.GMA_TRANS, gmaAddValue);
		return data;
	}

	/**
	 * Kiosk Cashout Json Data Update
	 * 
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> kioskCashOutJsonDataUpdate(String environment) throws Exception {
		
		String jsonKioskCashout;
		if (environment.equals(Constants.STAGING)) {
			 jsonKioskCashout = jsonFunctions.readFileAsString(FilePath.JSON_KIOSK_CASH_OUT_STAGING);
		} else {
			 jsonKioskCashout = jsonFunctions.readFileAsString(FilePath.JSON_KIOSK_CASH_OUT);
		};
		
		JsonObject jsonKioskCashoutData = jsonFunctions.convertStringToJson(jsonKioskCashout);
		jsonKioskCashoutData.addProperty(Reports.TRANS_ID, transID);
		jsonKioskCashoutData.addProperty(Reports.TRANS_DATE, transDate);
		String kioskCashoutData = jsonKioskCashoutData.get(Reports.DATA).getAsString();
		JsonObject kioskCashout = jsonFunctions.convertStringToJson(kioskCashoutData);
		kioskCashout.addProperty(Reports.ID,
				UUID.randomUUID().toString().replace(Constants.DELIMITER_HYPHEN, Constants.EMPTY_STRING));
		kioskCashout.addProperty(Reports.TRANS_DATE, transDate);
		kioskCashout.addProperty(Reports.DATE_CREATED, transDate);
		kioskCashout.addProperty(Reports.CURRENT_CASH_OUT_TIME, transDate);
		kioskCashout.addProperty(Reports.PREVIOUS_CASH_OUT_TIME, transDate);
		jsonKioskCashoutData.addProperty(Reports.DATA, kioskCashout.toString());
		data.put(Reports.KIOSK_CASH_OUT_JSON, jsonKioskCashoutData.toString());
		data.put(Reports.KIOSK_CASH_OUT_TRANS, kioskCashout);
		return data;
	}

	/**
	 * Select the report with Date range and Location to run the report
	 * 
	 * @param reportName
	 * @param date
	 * @param location
	 */
	public void selectAndRunReport(String menu, String reportName, String date, String location, String environment) {
		navigationBar.navigateToMenuItem(menu);
		reportList.selectReport(reportName);
		reportList.selectDate(date);
		foundation.threadWait(Constants.SHORT_TIME);
		if (environment.equals(Constants.STAGING)) {
//			reportList.selectLocationForSecondTypeDropdown(location);
			reportList.selectLocation(location);
		}else{
			reportList.selectLocation(location);
		}
		foundation.threadWait(Constants.SHORT_TIME);
		foundation.waitforClikableElement(ReportList.BTN_RUN_REPORT, Constants.SHORT_TIME);
		foundation.click(ReportList.BTN_RUN_REPORT);
		foundation.waitforElement(ProductSales.LBL_REPORT_NAME, Constants.SHORT_TIME);
		verifyReportName(reportName);
		checkForDataAvailabilyInResultTable();
	}

	public Map<String, Object> getData() {
		return data;
	}

	public List<String> getRequiredGMAJsonData() {
		return requiredGMAJsonData;
	}

	public List<String> getRequiredCashOutJsonData() {
		return requiredCashOutJsonData;
	}

	public Map<Integer, Map<String, String>> getLastPickupData() {
		return lastPickupData;
	}

	public double getUpdatedTotal(String columnName) {
		return calculateTotal(columnName);
	}

	public int getTotalRowCount(String requiredData, Map<Integer, Map<String, String>> requiredMap, String columnName) {
		return totalRowCount(requiredData, requiredMap, columnName);
	}

	public int getRowCount(String requiredData, Map<Integer, Map<String, String>> requiredMap, String columnName,
			String reqColumn) {
		return rowCount(requiredData, requiredMap, columnName, reqColumn);
	}
}
