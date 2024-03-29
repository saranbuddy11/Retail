package at.smartshop.pages;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
import at.framework.generic.DateAndTime;
import at.framework.reportsetup.ExtFactory;
import at.framework.ui.Foundation;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.keys.Reports;
import at.smartshop.tests.TestInfra;
import at.smartshop.utilities.WebService;

public class SoldDetails extends Factory {
	private Foundation foundation = new Foundation();
	private WebService webService = new WebService();
	private JsonFile jsonFunctions = new JsonFile();
	private PropertyFile propertyFile = new PropertyFile();
	private DateAndTime dateAndTime = new DateAndTime();
	private ReportList reportList = new ReportList();

	public static final By LBL_REPORT_NAME = By
			.cssSelector("#report-container > div > div.col-12.comment-table-heading");
	private static final By REPORT_GRID_FIRST_ROW = By.cssSelector("#rptdt > tbody > tr:nth-child(1)");
	private static final By NO_DATA_AVAILABLE_IN_TABLE = By.xpath("//td[@class='dataTables_empty']");
	private static final By TBL_SALES_ANALYSIS = By.cssSelector("#rptdt");
	private static final By TBL_SALES_ANALYSIS_GRID = By.cssSelector("#rptdt > tbody");
	public final static By TXT_SEARCH_TRANSACTION = By.xpath("//input[@aria-controls='transdt']");
	public final static By TXT_ID_TRANSACTION = By.cssSelector("#Row_0");
	public final static By FIND_TRANSACTION = By.xpath("//button[@id='findBtn']");
	public final static By TXT_SEARCH_FILTER = By.xpath("//input[@aria-controls='rptdt']");
	public static final By DATA_EXISTING_START_DATE_STAGING = By.cssSelector(
			"body > div.daterangepicker.ltr.show-ranges.opensright.show-calendar > div.drp-calendar.right > div.calendar-table > table > tbody > tr:nth-child(1) > td:nth-child(5)");
	public static final By DATA_EXISTING_END_DATE_STAGING = By.cssSelector(
			"body > div.daterangepicker.ltr.show-ranges.opensright.show-calendar > div.drp-calendar.right > div.calendar-table > table > tbody > tr:nth-child(2) > td:nth-child(5)");
	

	private List<String> tableHeaders = new ArrayList<>();
	private Map<String, Object> jsonData = new HashMap<>();
	private Map<Integer, Map<String, String>> reportsData = new LinkedHashMap<>();
	private Map<Integer, Map<String, String>> intialData = new LinkedHashMap<>();

	public Map<Integer, Map<String, String>> getIntialData() {
		return intialData;
	}

	public Map<Integer, Map<String, String>> getReportsData() {
		return reportsData;
	}

	public List<String> getTableHeaders() {
		return tableHeaders;
	}

	public void verifyReportName(String reportName) {
		try {
			foundation.waitforElement(LBL_REPORT_NAME, Constants.EXTRA_LONG_TIME);
			String reportTitle = foundation.getText(LBL_REPORT_NAME);
			Assert.assertTrue(reportTitle.contains(reportName));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void checkForDataAvailabilyInResultTable() {
		try {
			if (foundation.isDisplayed(REPORT_GRID_FIRST_ROW)) {
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
	 * Generating JSON Details
	 * 
	 * @param reportFormat
	 */
	private void generateJsonDetails(String reportFormat, String environment) {
		try {
			DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(Reports.DATE_FORMAT);
			DateTimeFormatter reqFormat = DateTimeFormatter.ofPattern(reportFormat);
			LocalDateTime tranDate = LocalDateTime.now();
			String transDate = tranDate.format(dateFormat);
			String reportDate = tranDate.format(reqFormat);
			String transID;
			if (environment.equals(Constants.STAGING)) {
				 transID = propertyFile.readPropertyFile(Configuration.DEVICE_ID_STAGING, FilePath.PROPERTY_CONFIG_FILE)
						+ Constants.DELIMITER_HYPHEN
						+ transDate.replaceAll(Reports.REGEX_TRANS_DATE, Constants.EMPTY_STRING);
			} else {
				 transID = propertyFile.readPropertyFile(Configuration.DEVICE_ID, FilePath.PROPERTY_CONFIG_FILE)
						+ Constants.DELIMITER_HYPHEN
						+ transDate.replaceAll(Reports.REGEX_TRANS_DATE, Constants.EMPTY_STRING);
			};

			jsonData.put(Reports.TRANS_ID, transID);
			jsonData.put(Reports.TRANS_DATE, transDate);
			jsonData.put(Reports.TRANS_DATE_TIME, reportDate);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * JASON Array data is updated
	 * 
	 * @param jsonObj
	 * @param reqString
	 * @param salesheader
	 */
	private void jsonArrayDataUpdate(JsonObject jsonObj, String reqString, String salesheader) {
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
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * Sales JSON Data Update
	 */
	private void salesJsonDataUpdate(String environment) {
		try {
			String salesHeaderID = UUID.randomUUID().toString().replace(Constants.DELIMITER_HYPHEN,
					Constants.EMPTY_STRING);
			String saleValue;
			if (environment.equals(Constants.STAGING)) {
				 saleValue = jsonFunctions.readFileAsString(FilePath.JSON_SALES_CREATION_STAGING);
			} else {
				 saleValue = jsonFunctions.readFileAsString(FilePath.JSON_SALES_CREATION);
			};
			JsonObject saleJson = jsonFunctions.convertStringToJson(saleValue);
			saleJson.addProperty(Reports.TRANS_ID, (String) jsonData.get(Reports.TRANS_ID));
			saleJson.addProperty(Reports.TRANS_DATE, (String) jsonData.get(Reports.TRANS_DATE));
			String sale = saleJson.get(Reports.SALE).getAsString();
			JsonObject salesObj = jsonFunctions.convertStringToJson(sale);
			salesObj.addProperty(Reports.ID, salesHeaderID);
			salesObj.addProperty(Reports.TRANS_ID, (String) jsonData.get(Reports.TRANS_ID));
			salesObj.addProperty(Reports.TRANS_DATE, (String) jsonData.get(Reports.TRANS_DATE));
			jsonArrayDataUpdate(salesObj, Reports.ITEMS, salesHeaderID);
			jsonArrayDataUpdate(salesObj, Reports.PAYMENTS, salesHeaderID);
			saleJson.addProperty(Reports.SALE, salesObj.toString());
			jsonData.put(Reports.JSON, saleJson.toString());
			jsonData.put(Reports.SALES, salesObj);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * Processing the API to do Transaction on Prodcuts to generate data
	 * 
	 * @param value
	 */
	public String processAPI(String value, String environment) {
		String date = "";
		try {
			generateJsonDetails(value, environment);
			salesJsonDataUpdate(environment);
			webService.apiReportPostRequest(
					propertyFile.readPropertyFile(Configuration.TRANS_SALES, FilePath.PROPERTY_CONFIG_FILE),
					(String) jsonData.get(Reports.JSON));
			date = String.valueOf(dateAndTime.getDateAndTime("MM/dd/yy hh:mm aa", "US/Alaska"));
			foundation.threadWait(Constants.TWO_SECOND);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		return date;
	}

	/**
	 * Get Table records from UI
	 * 
	 * @return
	 */
	public Map<Integer, Map<String, String>> getTblRecordsUI() {
		try {
			int recordCount = 0;
			tableHeaders.clear();
			WebElement tableReportsList = getDriver().findElement(TBL_SALES_ANALYSIS_GRID);
			WebElement tableReports = getDriver().findElement(TBL_SALES_ANALYSIS);
			List<WebElement> columnHeaders = tableReports.findElements(By.cssSelector("thead > tr > th"));
			List<WebElement> rows = tableReportsList.findElements(By.tagName("tr"));
			for (WebElement columnHeader : columnHeaders) {
				tableHeaders.add(columnHeader.getText());
			}
			for (WebElement row : rows) {
				Map<String, String> uiTblRowValues = new LinkedHashMap<>();
				for (int columnCount = 1; columnCount < tableHeaders.size() + 1; columnCount++) {
					WebElement column = row.findElement(By.cssSelector("td:nth-child(" + columnCount + ")"));
					uiTblRowValues.put(tableHeaders.get(columnCount - 1), column.getText());
				}
				reportsData.put(recordCount, uiTblRowValues);
				recordCount++;
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		return reportsData;
	}

	/**
	 * Verify the Report Headers
	 * 
	 * @param columnNames
	 */
	public void verifyReportHeaders(String columnNames) {
		try {
			List<String> columnName = Arrays.asList(columnNames.split(Constants.DELIMITER_HASH));
			foundation.threadWait(Constants.ONE_SECOND);
			for (int iter = 0; iter < tableHeaders.size(); iter++) {
				CustomisedAssert.assertTrue(tableHeaders.get(iter).equals(columnName.get(iter)));
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
		reportList.selectReport(reportName);
		reportList.selectDate(date);
		reportList.selectLocation(location);
		foundation.threadWait(Constants.SHORT_TIME);
		foundation.click(ReportList.BTN_RUN_REPORT);
		foundation.waitforElement(ProductSales.LBL_REPORT_NAME, Constants.SHORT_TIME);
		verifyReportName(reportName);
	}

	/**
	 * Verify the Common value content of Table Record
	 * 
	 * @param uiTableData
	 * @param columnName
	 * @param data
	 */
	public void verifyCommonValueContentofTableRecord(String columnName, String value) {
		Map<String, String> innerMap = new HashMap<>();
		String innerValue = " ";
		try {
			for (int i = 0; i < reportsData.size(); i++) {
				innerMap = reportsData.get(i);
				innerValue = innerMap.get(columnName);
				CustomisedAssert.assertTrue(innerValue.contains(value));
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * Verify the Different value content of Table Record
	 * 
	 * @param uiTableData
	 * @param columnName
	 * @param data
	 */
	public void verifyDifferentValueContentofTableRecord(String columnName, String value) {
		Map<String, String> innerMap = new HashMap<>();
		String innerValue = " ";
		try {
			for (int i = 0; i < reportsData.size(); i++) {
				innerMap = reportsData.get(i);
				innerValue = innerMap.get(columnName).replace("%", "");
				System.out.println(value + "-" + innerValue);
				CustomisedAssert.assertTrue(value.contains(innerValue));
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * Calculate Margin value
	 * 
	 * @param cost
	 * @param totalPrice
	 * @return
	 */
	public String calculateMargin(List<String> cost, Double totalPrice) {
		List<String> marginValues = new ArrayList<String>();
		try {
			for (int i = 0; i < cost.size(); i++) {
				double margin = ((totalPrice
						- Double.parseDouble(cost.get(i).replaceAll(Reports.REPLACE_DOLLOR, Constants.EMPTY_STRING)))
						/ totalPrice) * 100.0;
				DecimalFormat df = new DecimalFormat(Constants.DECIMAL_FORMAT);
				String d = df.format(margin);
				marginValues.add(String.valueOf(d));
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		String s = marginValues.get(0) + Constants.DELIMITER_HASH + marginValues.get(1);
		return s;
	}
}
