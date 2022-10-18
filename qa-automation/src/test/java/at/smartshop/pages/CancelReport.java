package at.smartshop.pages;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
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

public class CancelReport extends Factory {
	private Foundation foundation = new Foundation();
	private JsonFile jsonFunctions = new JsonFile();
	private NavigationBar navigationBar = new NavigationBar();
	private ReportList reportList = new ReportList();

	private static int recordCount = 0;
	public static LocalDateTime tranDate;
	public static JsonObject jsonData;
	public static JsonObject items;
	public static JsonObject payments;

	public static List<String> tableHeaders = new ArrayList<>();
	public static final List<String> NAME_ON_CC_LIST = new LinkedList<>();
	public static final List<String> PAN_LIST = new LinkedList<>();
	public static final List<String> PRODUCT_NAME_LIST = new LinkedList<>();
	public static final List<String> SUBTOTAL_LIST = new LinkedList<>();
	public static final List<String> REQUIRED_JSON_DATA_LIST = new LinkedList<>();
	public static Map<Integer, Map<String, String>> reportsData = new LinkedHashMap<>();

	public static final By LBL_REPORT_NAME = By
			.cssSelector("#report-container > div.span12.rpt-data > div.col-12.comment-table-heading");
	private static final By REPORT_GRID_FIRST_ROW = By.cssSelector("#rptdt > tbody > tr:nth-child(1)");
	private static final By NO_DATA_AVAILABLE_IN_TABLE = By.xpath("//td[@class='dataTables_empty']");
	public static final By TABLE_CANCEL = By.id("rptdt");
	public static final By TABLE_CANCEL_GRID = By.cssSelector("#rptdt > tbody");

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
	 * Verify the Data availability in Result Table
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
	 * Read All Records from Cancel Report Table
	 */
	public void readAllRecordsFromCancelReportTable() {
		tableHeaders.clear();
		WebElement tableReports = getDriver().findElement(TABLE_CANCEL);
		List<WebElement> headers = tableReports.findElements(By.tagName("th"));
		for (WebElement header : headers) {
			tableHeaders.add(header.getText());
		}
		WebElement tableReportsList = getDriver().findElement(TABLE_CANCEL_GRID);
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
	 * Verify name on Cancel Report column
	 * 
	 * @param columnName
	 */
	public void verifyNameOnCC(String columnName) {
		String nameOnCC = reportsData.get(recordCount).get(columnName);
		CustomisedAssert.assertTrue(nameOnCC.equals(NAME_ON_CC_LIST.get(0)));
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
	 * Verify Last 4 of cancel report column
	 * 
	 * @param columnName
	 */
	public void verifyLast4OfCC(String columnName) {
		String last4OfCC = reportsData.get(recordCount).get(columnName);
		CustomisedAssert.assertTrue(last4OfCC.equals(PAN_LIST.get(0)));
	}

	/**
	 * Get Required Record
	 * 
	 * @param columnName
	 * @param transID
	 */
	public void getRequiredRecord(String columnName, String transID) {
		WebElement tableReportsList = getDriver().findElement(TABLE_CANCEL_GRID);
		List<WebElement> rows = tableReportsList.findElements(By.tagName("tr"));
		for (int val = 0; val < rows.size(); val++) {
			if (reportsData.get(val).get(columnName).equals(transID)) {
				recordCount = val;
				break;
			}
		}
	}

	/**
	 * Verify Location from Report
	 * 
	 * @param columnName
	 * @throws Exception
	 */
	public void verifyLocation(String columnName) throws Exception {
		String updatedLocation = propertyFile.readPropertyFile(Configuration.CURRENT_LOC,
				FilePath.PROPERTY_CONFIG_FILE);
		String location = reportsData.get(recordCount).get(columnName);
		CustomisedAssert.assertTrue(location.equalsIgnoreCase(updatedLocation));
	}

	/**
	 * Verify Time Cancelled column in Report
	 * 
	 * @param columnName
	 * @param dateFormat
	 */
	public void verifyTimeCancelled(String columnName, String dateFormat) {
		String timeCancelled = reportsData.get(recordCount).get(columnName);
		DateTimeFormatter dateTimeformat = DateTimeFormatter.ofPattern(dateFormat);
		String updatedTime = tranDate.format(dateTimeformat).toUpperCase();
		CustomisedAssert.assertTrue(timeCancelled.equals(updatedTime));
	}

	/**
	 * Verify Transaction ID
	 * 
	 * @param columnName
	 */
	public void verifyTransID(String columnName) {
		String transID = reportsData.get(recordCount).get(columnName);
		CustomisedAssert.assertTrue(transID.equals(REQUIRED_JSON_DATA_LIST.get(0)));
	}

	/**
	 * Verify Items
	 * 
	 * @param columnName
	 */
	public void verifyItems(String columnName) {
		String item = reportsData.get(recordCount).get(columnName);
		for (int iter = 0; iter < PRODUCT_NAME_LIST.size(); iter++) {
			CustomisedAssert.assertTrue(item.contains(PRODUCT_NAME_LIST.get(iter)));
		}
	}

	/**
	 * Get Json Array Data
	 * 
	 * @param jsonObj
	 * @param itemData
	 * @param paymentData
	 */
	public void getJsonArrayData(JsonObject jsonObj, String itemData, String paymentData) {
		JsonArray items = jsonObj.get(itemData).getAsJsonArray();
		for (JsonElement item : items) {
			JsonObject element = item.getAsJsonObject();
			PRODUCT_NAME_LIST.add(element.get(Reports.NAME).getAsString());
		}
		JsonArray payments = jsonObj.get(paymentData).getAsJsonArray();
		for (JsonElement payment : payments) {
			JsonObject element = payment.getAsJsonObject();
			NAME_ON_CC_LIST.add(element.get(Reports.NAME).getAsString());
			PAN_LIST.add(element.get(Reports.PAN).getAsString());
		}
	}

	/**
	 * Verify Total
	 * 
	 * @param columnName
	 */
	public void verifyTotal(String columnName) {
		String totals = reportsData.get(recordCount).get(columnName);
		CustomisedAssert.assertTrue(totals.contains(REQUIRED_JSON_DATA_LIST.get(1)));
	}

	/**
	 * Get Json Sales Data
	 */
	public void getJsonSalesData() {
		String transID = jsonData.get(Reports.TRANS_ID).getAsString();
		REQUIRED_JSON_DATA_LIST.add(transID);
		String subTotal = jsonData.get(Reports.SUBTOTAL).getAsString();
		REQUIRED_JSON_DATA_LIST.add(subTotal);
	}

	/**
	 * Json Array Data Update
	 * 
	 * @param jsonObj
	 * @param data
	 * @param transID
	 * @param salesHeader
	 * @param transDate
	 */
	public void jsonArrayDataUpdate(JsonObject jsonObj, String data, String transID, String salesHeader,
			String transDate) {
		JsonArray items = jsonObj.get(data).getAsJsonArray();
		for (JsonElement item : items) {
			JsonObject element = item.getAsJsonObject();
			element.addProperty(Reports.ID,
					UUID.randomUUID().toString().replace(Constants.DELIMITER_HYPHEN, Constants.EMPTY_STRING));
			element.addProperty(Reports.SALES_HEADER, salesHeader);
			element.addProperty(Reports.TRANS_ID, transID);
			element.addProperty(Reports.TRANS_DATE, transDate);
		}
	}

	/**
	 * Sale Cancel Json Data Update
	 * 
	 * @return
	 * @throws Exception
	 */
	public String saleCancelJsonDataUpdate() throws Exception {
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(Reports.DATE_FORMAT);
		tranDate = LocalDateTime.now();
		String transDate = tranDate.format(dateFormat);
		String cancelHeaderID = UUID.randomUUID().toString().replace(Constants.DELIMITER_HYPHEN,
				Constants.EMPTY_STRING);
		String transID = propertyFile.readPropertyFile(Configuration.DEVICE_ID, FilePath.PROPERTY_CONFIG_FILE)
				+ Constants.DELIMITER_HYPHEN + transDate.replaceAll("[^a-zA-Z0-9]", Constants.EMPTY_STRING);
		String jsonString = jsonFunctions.readFileAsString(FilePath.CANCEL_SALE_CREATION);
		jsonData = jsonFunctions.convertStringToJson(jsonString);
		jsonData.addProperty(Reports.ID, cancelHeaderID);
		jsonData.addProperty(Reports.TRANS_ID, transID);
		jsonData.addProperty(Reports.TRANS_DATE, transDate);
		jsonArrayDataUpdate(jsonData, Reports.ITEMS, transID, cancelHeaderID, transDate);
		jsonArrayDataUpdate(jsonData, Reports.PAYMENTS, transID, cancelHeaderID, transDate);
		return jsonData.toString();
	}

	/**
	 * Select the report with Date range and Location to run the report
	 * 
	 * @param reportName
	 * @param date
	 * @param location
	 */
	public void selectAndRunReport(String menu, String reportName, String date, String location) {
		navigationBar.navigateToMenuItem(menu);
		reportList.selectReport(reportName);
		reportList.selectDate(date);
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
}
