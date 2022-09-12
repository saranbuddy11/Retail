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

public class AccountFunding extends Factory {
	private Foundation foundation = new Foundation();
	private WebService webService = new WebService();
	private JsonFile jsonFunctions = new JsonFile();
	private NavigationBar navigationBar = new NavigationBar();
	private ReportList reportList = new ReportList();

	private List<String> admData = new LinkedList<>();
	private Map<String, Object> data = new HashMap<>();
	private Map<Integer, Map<String, String>> initialReportsData = new HashMap<>();
	public static List<String> tableHeaders = new ArrayList<>();
	public static Map<Integer, Map<String, String>> reportsData = new LinkedHashMap<>();
	private Map<String, Object> jsonData = new HashMap<>();
	private String transID;
	private String transDate;
	private String time;

	public static final By TABLE_ACCOUNT_FUNDING = By.id("rptdt");
	public static final By LBL_REPORT_NAME = By
			.cssSelector("#report-container > div > div.col-12.comment-table-heading");
	private static final By REPORT_GRID_FIRST_ROW = By.cssSelector("#rptdt > tbody > tr:nth-child(1)");
	private static final By NO_DATA_AVAILABLE_IN_TABLE = By.xpath("//td[@class='dataTables_empty']");
	public static final By TABLE_ACCOUNT_FUNDING_GRID = By.cssSelector("#rptdt > tbody");
	public static final By TXT_SEARCH = By.cssSelector("input[aria-controls='rptdt']");

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
	 * Get Account Funding Headers
	 */
	public void getAccountFundingHeaders() {
		tableHeaders.clear();
		WebElement tableReports = getDriver().findElement(TABLE_ACCOUNT_FUNDING);
		List<WebElement> headers = tableReports.findElements(By.cssSelector("thead > tr > th"));
		for (WebElement header : headers) {
			tableHeaders.add(header.getText());
		}
	}

	/**
	 * Get Account Funding
	 */
	public void getAccountFunding() {
		int count = 0;
		reportsData.clear();
		WebElement tableReportsList = getDriver().findElement(TABLE_ACCOUNT_FUNDING_GRID);
		List<WebElement> rows = tableReportsList.findElements(By.tagName("tr"));
		for (WebElement row : rows) {
			Map<String, String> reportsdata = new LinkedHashMap<>();
			for (int iter = 1; iter < tableHeaders.size() + 1; iter++) {
				WebElement column = row.findElement(By.cssSelector("td:nth-child(" + iter + ")"));
				if (iter > 2) {
					reportsdata.put(tableHeaders.get(iter - 1),
							column.getText().replaceAll(Constants.REPLACE_DOLLOR, Constants.EMPTY_STRING));
				} else {
					reportsdata.put(tableHeaders.get(iter - 1), column.getText());
				}
			}
			reportsData.put(count, reportsdata);
			count++;
		}
	}

	/**
	 * Process Sales API for Transaction
	 * 
	 * @param actionType
	 * @param paymentType
	 * @param timeFormat
	 * @throws Exception
	 */
	public void processAPI(String actionType, String paymentType, String timeFormat) throws Exception {
		List<String> aType = Arrays.asList(actionType.split(Constants.DELIMITER_HASH));
		List<String> payType = Arrays.asList(paymentType.split(Constants.DELIMITER_HASH));
		for (int iter = 0; iter < payType.size(); iter++) {
			generateJsonDetails(timeFormat);
			salesJsonDataUpdate(payType.get(iter));
			webService.apiReportPostRequest(
					propertyFile.readPropertyFile(Configuration.TRANS_SALES, FilePath.PROPERTY_CONFIG_FILE),
					(String) jsonData.get(Reports.JSON));
			gmaAddValueJsonDataUpdate(aType.get(iter), payType.get(iter));
			webService.apiReportPostRequest(
					propertyFile.readPropertyFile(Configuration.TRANS_GMA, FilePath.PROPERTY_CONFIG_FILE),
					(String) jsonData.get(Reports.GMA_JSON));
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
	 */
	public void verifyReportRecords() {
		int coulumnCount = tableHeaders.size();
		for (int iter = 0; iter < reportsData.size(); iter++) {
			for (int val = 0; val < coulumnCount; val++) {
				System.out.println(reportsData.get(iter).get(tableHeaders.get(val)) + "-"
						+ (initialReportsData.get(iter).get(tableHeaders.get(val))));
				CustomisedAssert.assertTrue(reportsData.get(iter).get(tableHeaders.get(val))
						.contains(initialReportsData.get(iter).get(tableHeaders.get(val))));
			}
		}
	}

	/**
	 * Update the sales
	 * 
	 * @param columnName
	 */
	public void updateSales(String columnName) {
		JsonObject gmatrans = (JsonObject) data.get(Reports.GMA_TRANS);
		String amount = gmatrans.get(Reports.AMOUNT).getAsString();
		String intialSales = initialReportsData.get(0).get(columnName);
		double updatedSales = Double.parseDouble(intialSales) + Double.parseDouble(amount);
		updatedSales = Math.round(updatedSales * 100.0) / 100.0;
		initialReportsData.get(0).put(columnName, String.valueOf(updatedSales));
	}

	/**
	 * Update Credit And Cash
	 * 
	 * @param columnName
	 */
	public void updateCreditAndCash(String columnName) {
		JsonObject gmatrans = (JsonObject) data.get(Reports.GMA_TRANS);
		String amount = gmatrans.get(Reports.AMOUNT).getAsString();
		System.out.println(amount);
		double initialAmount = Double.parseDouble(initialReportsData.get(0).get(columnName));
		System.out.println(initialAmount);
		double updatedAmount = initialAmount + Double.parseDouble(amount);
		System.out.println(updatedAmount);
		initialReportsData.get(0).put(columnName, String.valueOf(updatedAmount));
	}

	/**
	 * Update Operator Credit
	 */
	public void updateOperatorCredit() {
		double initialOperatorCredit = Double.parseDouble(initialReportsData.get(0).get((tableHeaders.get(2))));
		double updatedOperatorCredit = initialOperatorCredit + Double.parseDouble(admData.get(1));
		System.out.println(admData);
		initialReportsData.get(0).put(tableHeaders.get(2), String.valueOf(updatedOperatorCredit));
	}

	/**
	 * Calculate Total Sales
	 */
	public void calculateTotalSales() {
		double accountSales = Double.parseDouble(reportsData.get(0).get(tableHeaders.get(8)));
		double creditSales = Double.parseDouble(reportsData.get(0).get(tableHeaders.get(9)));
		double kioskCash = Double.parseDouble(reportsData.get(0).get(tableHeaders.get(5)));
		double totalSales = kioskCash + accountSales + creditSales;
		totalSales = Math.round(totalSales * 100.0) / 100.0;
		initialReportsData.get(0).put(tableHeaders.get(9), String.valueOf(totalSales));
	}

	/**
	 * Json Array Data Update
	 * 
	 * @param jsonObj
	 * @param reqString
	 * @param transID
	 * @param salesheader
	 * @param transDate
	 * @param paymentType
	 */
	public static void jsonArrayDataUpdate(JsonObject jsonObj, String reqString, String transID, String salesheader,
			String transDate, String paymentType) {
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
				element.addProperty(Reports.TYPE, paymentType);
			}
		}
	}

	/**
	 * Update Remaining Account Balance
	 */
	public void updateRemainingAccountBalances() {
		JsonObject gmatrans = (JsonObject) data.get(Reports.GMA_TRANS);
		String amount = gmatrans.get(Reports.AMOUNT).getAsString();
		String initialBalance = initialReportsData.get(0).get(tableHeaders.get(7));
		double adjustAmount = Double.parseDouble(admData.get(1)) + Double.parseDouble(amount)
				+ Double.parseDouble(amount) + Double.parseDouble(amount) - Double.parseDouble(amount);
		double updatedBalance = Double.parseDouble(initialBalance) + adjustAmount;
		initialReportsData.get(0).put(tableHeaders.get(7), String.valueOf(updatedBalance));
	}

	/**
	 * Generate Json Details
	 * 
	 * @param timeFormat
	 * @throws Exception
	 */
	public void generateJsonDetails(String timeFormat) throws Exception {
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(Reports.DATE_FORMAT);
		DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern(timeFormat);
		LocalDateTime tranDate = LocalDateTime.now();
		time = tranDate.format(dateTimeFormat);
		transDate = tranDate.format(dateFormat);
		transID = propertyFile.readPropertyFile(Configuration.DEVICE_ID_1, FilePath.PROPERTY_CONFIG_FILE)
				+ Constants.DELIMITER_HYPHEN + transDate.replaceAll(Constants.REGEX_TRANS_DATE, Constants.EMPTY_STRING);
	}

	/**
	 * Sales Json Data Update
	 * 
	 * @param paymentType
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> salesJsonDataUpdate(String paymentType) throws Exception {
		String salesHeaderID = UUID.randomUUID().toString().replace(Constants.DELIMITER_HYPHEN, Constants.EMPTY_STRING);
		String jsonSale = jsonFunctions.readFileAsString(FilePath.JSON_SALES_CREATION);
		JsonObject jsonData = jsonFunctions.convertStringToJson(jsonSale);
		jsonData.addProperty(Reports.TRANS_ID, transID);
		jsonData.addProperty(Reports.TRANS_DATE, transDate);
		String sale = jsonData.get(Reports.SALE).getAsString();
		JsonObject salesObj = jsonFunctions.convertStringToJson(sale);
		salesObj.addProperty(Reports.ID, salesHeaderID);
		salesObj.addProperty(Reports.TRANS_ID, transID);
		salesObj.addProperty(Reports.TRANS_DATE, transDate);
		jsonArrayDataUpdate(salesObj, Reports.ITEMS, transID, salesHeaderID, transDate, paymentType);
		jsonArrayDataUpdate(salesObj, Reports.PAYMENTS, transID, salesHeaderID, transDate, paymentType);
		jsonData.addProperty(Reports.SALE, salesObj.toString());
		data.put(Reports.JSON, jsonData.toString());
		data.put(Reports.SALES, salesObj);
		return data;
	}

	/**
	 * GMA Add Value JSon Data Update
	 * 
	 * @param actionType
	 * @param payType
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> gmaAddValueJsonDataUpdate(String actionType, String payType) throws Exception {
		String jsonGMAAddValue = jsonFunctions.readFileAsString(FilePath.JSON_GMA_ADD_VALUE);
		JsonObject jsonGMAAddValueData = jsonFunctions.convertStringToJson(jsonGMAAddValue);
		jsonGMAAddValueData.addProperty(Reports.TRANS_ID, transID);
		jsonGMAAddValueData.addProperty(Reports.TRANS_DATE, transDate);
		String gmaData = jsonGMAAddValueData.get(Reports.DATA).getAsString();
		JsonObject gmaAddValue = jsonFunctions.convertStringToJson(gmaData);
		gmaAddValue.addProperty(Reports.TRANS_ID, transID);
		gmaAddValue.addProperty(Reports.TRANS_DATE, transDate);
		gmaAddValue.addProperty(Reports.ACTION_TYPE, actionType);
		gmaAddValue.addProperty(Reports.PAYMENT_TYPE, payType);
		jsonGMAAddValueData.addProperty(Reports.DATA, gmaAddValue.toString());
		data.put(Reports.GMA_JSON, jsonGMAAddValueData.toString());
		data.put(Reports.GMA_TRANS, gmaAddValue);
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

	public Map<Integer, Map<String, String>> getInitialReportsData() {
		return initialReportsData;
	}

	public List<String> getADMData() {
		return admData;
	}

	public String getTime() {
		return time;
	}
}