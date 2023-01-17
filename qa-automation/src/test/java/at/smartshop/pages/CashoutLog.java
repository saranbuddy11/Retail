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

public class CashoutLog extends Factory {
	private Foundation foundation = new Foundation();
	private WebService webService = new WebService();
	private JsonFile jsonFunctions = new JsonFile();
	private NavigationBar navigationBar = new NavigationBar();
	private ReportList reportList = new ReportList();

	public String transID = Constants.EMPTY_STRING;
	public String transDate = Constants.EMPTY_STRING;
	public static List<String> tableHeaders = new ArrayList<>();
	public List<String> requiredCashOutJsonData = new LinkedList<>();
	public Map<String, Object> data = new HashMap<>();
	public static Map<Integer, Map<String, String>> reportsData = new LinkedHashMap<>();

	public static final By LBL_REPORT_NAME = By
			.cssSelector("#report-container > div > div.col-12.comment-table-heading");
	private static final By REPORT_GRID_FIRST_ROW = By.cssSelector("#rptdt > tbody > tr:nth-child(1)");
	private static final By NO_DATA_AVAILABLE_IN_TABLE = By.xpath("//td[@class='dataTables_empty']");
	public static final By DATA_EXISTING_START_DATE = By.cssSelector(
			"body > div.daterangepicker.ltr.show-ranges.opensright.show-calendar  > div.drp-calendar.right > div.calendar-table > table > tbody > tr:nth-child(1) > td:nth-child(2)");
	public static final By DATA_EXISTING_END_DATE = By.cssSelector(
			"body > div.daterangepicker.ltr.show-ranges.opensright.show-calendar  > div.drp-calendar.right > div.calendar-table > table > tbody > tr:nth-child(5) > td:nth-child(4)");
	public static final By TABLE_CASH_OUT_LOG = By.cssSelector("table[id='rptdt']");
	public static final By TABLE_CASH_OUT_LOG_GRID = By.cssSelector("#rptdt > tbody");
	public static final By TXT_SEARCH_FILTER = By.cssSelector("#rptdt_filter > label > [aria-controls='rptdt']");
	public static final By DATA_EXISTING_START_DATE_STAGING = By.cssSelector(
			"body > div.daterangepicker.ltr.show-ranges.opensright.show-calendar  > div.drp-calendar.right > div.calendar-table > table > tbody > tr:nth-child(4) > td:nth-child(5)");
	public static final By DATA_EXISTING_END_DATE_STAGING = By.cssSelector(
			"body > div.daterangepicker.ltr.show-ranges.opensright.show-calendar  > div.drp-calendar.right > div.calendar-table > table > tbody > tr:nth-child(4) > td:nth-child(5)");


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
	 * Check Data Availability in Result Table
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
	 * Read the Data from Cashout Log Table UI
	 */
	public void getCashOutLog() {
		tableHeaders.clear();
		WebElement tableReports = getDriver().findElement(TABLE_CASH_OUT_LOG);
		List<WebElement> headers = tableReports.findElements(By.cssSelector("thead > tr > th"));
		for (WebElement header : headers) {
			tableHeaders.add(header.getText());
		}
		WebElement tableReportsList = getDriver().findElement(TABLE_CASH_OUT_LOG_GRID);
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
	 * Verify KCO Record
	 */
	public void verifyKCORecord() {
		int count = tableHeaders.size();
		for (int iter = 0; iter < count; iter++) {
			CustomisedAssert.assertTrue(
					reportsData.get(0).get(tableHeaders.get(iter)).contains(requiredCashOutJsonData.get(iter)));
		}
	}

	/**
	 * Get the Details from Cashout JSON Data
	 */
	public void getCashoutJsonData() {
		JsonObject kioskCashOut = (JsonObject) data.get(Reports.KIOSK_CASH_OUT_TRANS);
		String location = kioskCashOut.get(Reports.LOC_NAME).getAsString();
		requiredCashOutJsonData.add(location);
		String deviceName = kioskCashOut.get(Reports.KIOSK).getAsString();
		requiredCashOutJsonData.add(deviceName);
		String consumer = kioskCashOut.get(Reports.USER_NAME).getAsString();
		requiredCashOutJsonData.add(consumer.replace(Constants.DELIMITER_SPACE, Constants.EMPTY_STRING));
		String total = kioskCashOut.get(Reports.TOTAL_CASH).getAsString();
		requiredCashOutJsonData.add(total);
		String bills1 = kioskCashOut.get(Reports.BILLS1).getAsString();
		requiredCashOutJsonData.add(bills1);
		String bills2 = kioskCashOut.get(Reports.BILLS2).getAsString();
		requiredCashOutJsonData.add(bills2);
		String bills3 = kioskCashOut.get(Reports.BILLS3).getAsString();
		requiredCashOutJsonData.add(bills3);
		String bills4 = kioskCashOut.get(Reports.BILLS4).getAsString();
		requiredCashOutJsonData.add(bills4);
		String bills5 = kioskCashOut.get(Reports.BILLS5).getAsString();
		requiredCashOutJsonData.add(bills5);
		String bills6 = kioskCashOut.get(Reports.BILLS6).getAsString();
		requiredCashOutJsonData.add(bills6);
		String bills7 = kioskCashOut.get(Reports.BILLS7).getAsString();
		requiredCashOutJsonData.add(bills7);
	}

	/**
	 * Generate JSON details
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
					+ transDate.replaceAll(Reports.REGEX_TRANS_DATE, Constants.EMPTY_STRING);
		} else {
			 transID = propertyFile.readPropertyFile(Configuration.DEVICE_ID, FilePath.PROPERTY_CONFIG_FILE)
					+ Constants.DELIMITER_HYPHEN
					+ transDate.replaceAll(Reports.REGEX_TRANS_DATE, Constants.EMPTY_STRING);
		};
		
//		transID = propertyFile.readPropertyFile(Configuration.DEVICE_ID, FilePath.PROPERTY_CONFIG_FILE)
//				+ Constants.DELIMITER_HYPHEN + transDate.replaceAll(Constants.REGEX_TRANS_DATE, Constants.EMPTY_STRING);
		data.put(Reports.TRANS_DATE_TIME, tranDate);
	}

	/**
	 * Process API
	 * 
	 * @param timeFormat
	 * @throws Exception
	 */
	public void processAPI(String timeFormat, String environment) throws Exception {
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(timeFormat);
		generateJsonDetails(environment);
		kioskCashOutJsonDataUpdate(environment);
		String kcoDate = ((LocalDateTime) data.get(Reports.TRANS_DATE_TIME)).format(dateFormat);
		webService.apiReportPostRequest(
				propertyFile.readPropertyFile(Configuration.KCO_TRANS_KEY, FilePath.PROPERTY_CONFIG_FILE),
				(String) data.get(Reports.KIOSK_CASH_OUT_JSON));
		requiredCashOutJsonData.add(kcoDate.toUpperCase());
	}

	/**
	 * Kiosk Cashout JSON Data Update
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
		checkForDataAvailabilyInResultTable();
	}

	public Map<String, Object> getData() {
		return data;
	}

	public List<String> getRequiredCashOutJsonData() {
		return requiredCashOutJsonData;
	}
}
