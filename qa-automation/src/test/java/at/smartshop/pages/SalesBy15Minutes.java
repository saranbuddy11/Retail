package at.smartshop.pages;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
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

public class SalesBy15Minutes extends Factory {
	private Foundation foundation = new Foundation();
	private WebService webService = new WebService();
	private JsonFile jsonFunctions = new JsonFile();
	private PropertyFile propertyFile = new PropertyFile();

	public static final By LBL_REPORT_NAME = By
			.cssSelector("#report-container > div > div.col-12.comment-table-heading");
	private static final By REPORT_GRID_FIRST_ROW = By.cssSelector("#rptdt > tbody > tr:nth-child(1)");
	private static final By NO_DATA_AVAILABLE_IN_TABLE = By.xpath("//td[@class='dataTables_empty']");

	private static final By TBL_SALES_TIME_DETAILS_BY_DEVICE = By.cssSelector("#rptdt");
	private static final By TBL_SALES_TIME_DETAILS_BY_DEVICE_GRID = By.cssSelector("#rptdt > tbody");
	public static final By TXT_SEARCH = By.xpath("//input[@aria-controls='rptdt']");
	public static final By DATA_EXISTING_START_DATE_STAGING = By.cssSelector(
			"body > div.daterangepicker.ltr.show-ranges.opensright.show-calendar > div.drp-calendar.right > div.calendar-table > table > tbody > tr:nth-child(1) > td:nth-child(5)");
	public static final By DATA_EXISTING_END_DATE_STAGING = By.cssSelector(
			"body > div.daterangepicker.ltr.show-ranges.opensright.show-calendar > div.drp-calendar.right > div.calendar-table > table > tbody > tr:nth-child(2) > td:nth-child(5)");

	private List<String> tableHeaders = new ArrayList<>();
	private Map<String, String> tableFooterData = new LinkedHashMap<>();
	private Map<String, String> updatedTableFooters = new LinkedHashMap<>();
	private Map<String, Object> jsonData = new HashMap<>();
	private List<String> requiredJsonData = new LinkedList<>();
	private Map<Integer, Map<String, String>> reportsData = new LinkedHashMap<>();
	private Map<Integer, Map<String, String>> intialData = new LinkedHashMap<>();
	private int rowCount;
	private Double totalSales;
	private String TimeFrame;

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

	public void processAPI(String value, String environment) {
		try {
			generateJsonDetails(value, environment);
			salesJsonDataUpdate(environment);
			webService.apiReportPostRequest(
					propertyFile.readPropertyFile(Configuration.TRANS_SALES, FilePath.PROPERTY_CONFIG_FILE),
					(String) jsonData.get(Reports.JSON));
			foundation.threadWait(Constants.TWO_SECOND);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

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

	private void salesJsonDataUpdate(String environment) {
		try {
			String salesHeaderID = UUID.randomUUID().toString().replace(Constants.DELIMITER_HYPHEN,
					Constants.EMPTY_STRING);
			String saleValue;
			if (environment.equals(Constants.STAGING)) {
				 saleValue = jsonFunctions.readFileAsString(FilePath.JSON_SALES_CREATION_WITH_DEPOSIT_AND_DISCOUNT_STAGING);
			} else {
				 saleValue = jsonFunctions.readFileAsString(FilePath.JSON_SALES_CREATION_WITH_DEPOSIT_AND_DISCOUNT);
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

	public By objReportColumn(String columnName, String productName) {
		return By.xpath("//table//td[text()='" + productName + "']/following::td[@aria-describedby='hierarchicalGrid_"
				+ columnName + "']");
	}

	public void getTblRecordsUI() {
		try {
			int recordCount = 0;
			tableHeaders.clear();
			WebElement tableReportsList = getDriver().findElement(TBL_SALES_TIME_DETAILS_BY_DEVICE_GRID);
			WebElement tableReports = getDriver().findElement(TBL_SALES_TIME_DETAILS_BY_DEVICE);
			List<WebElement> columnHeaders = tableReports.findElements(By.cssSelector("thead > tr > th"));
			List<WebElement> footerRow = tableReports.findElements(By.cssSelector("tfoot > tr"));
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
			for (WebElement footerRowValue : footerRow) {
				for (int columnCount = 1; columnCount < tableHeaders.size() + 1; columnCount++) {
					WebElement column = footerRowValue.findElement(By.cssSelector("th:nth-child(" + columnCount + ")"));
					tableFooterData.put(tableHeaders.get(columnCount - 1), column.getText());
				}
			}
		} catch (Exception exc) {
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
			String initialAmount = intialData.get(rowCount).get(columnName).replaceAll(Reports.REPLACE_DOLLOR,
					Constants.EMPTY_STRING);
			double updatedAmount = Double.parseDouble(amount.replaceAll(Reports.REPLACE_DOLLOR, Constants.EMPTY_STRING))
					+ Double.parseDouble(initialAmount);
			updatedAmount = Math.round(updatedAmount * 100.0) / 100.0;
			intialData.get(rowCount).put(columnName, Constants.DOLLAR_SYMBOL + String.valueOf(updatedAmount));
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
			String initialAmount = updatedTableFooters.get(columnName).replaceAll(Reports.REPLACE_DOLLOR,
					Constants.EMPTY_STRING);
			double updatedAmount = Double.parseDouble(amount.replaceAll(Reports.REPLACE_DOLLOR, Constants.EMPTY_STRING))
					+ Double.parseDouble(initialAmount);
			updatedAmount = Math.round(updatedAmount * 100.0) / 100.0;
			updatedTableFooters.put(columnName, Constants.DOLLAR_SYMBOL + String.valueOf(updatedAmount));
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
	public double grossSales(String columnName, String price, String tax) {
		try {
			String initialAmount = intialData.get(rowCount).get(columnName).replaceAll(Reports.REPLACE_DOLLOR,
					Constants.EMPTY_STRING);
			double updatedAmount = Double.parseDouble(price.replaceAll(Reports.REPLACE_DOLLOR, Constants.EMPTY_STRING))
					* 2 + Double.parseDouble(tax.replaceAll(Reports.REPLACE_DOLLOR, Constants.EMPTY_STRING)) * 2
					+ Double.parseDouble(initialAmount);
			totalSales = Math.round(updatedAmount * 100.0) / 100.0;
			intialData.get(rowCount).put(columnName, Constants.DOLLAR_SYMBOL + String.valueOf(totalSales));
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
	public double grossSalesOfFooter(String columnName, String price, String tax) {
		try {
			String initialAmount = updatedTableFooters.get(columnName).replaceAll(Reports.REPLACE_DOLLOR,
					Constants.EMPTY_STRING);
			double updatedAmount = Double.parseDouble(price.replaceAll(Reports.REPLACE_DOLLOR, Constants.EMPTY_STRING))
					* 2 + Double.parseDouble(tax.replaceAll(Reports.REPLACE_DOLLOR, Constants.EMPTY_STRING)) * 2
					+ Double.parseDouble(initialAmount);
			totalSales = Math.round(updatedAmount * 100.0) / 100.0;
			updatedTableFooters.put(columnName, Constants.DOLLAR_SYMBOL + String.valueOf(totalSales));
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
			String saleCount = intialData.get(rowCount).get(columnName);
			int updatedCount = Integer.parseInt(saleCount) + 1;
			intialData.get(rowCount).put(columnName, String.valueOf(updatedCount));
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
			String saleCount = updatedTableFooters.get(columnName);
			int updatedCount = Integer.parseInt(saleCount) + 1;
			updatedTableFooters.put(columnName, String.valueOf(updatedCount));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * This Method is to get the Time Period
	 * 
	 * @param time
	 * @return
	 */
	public String getTimePeroid(String timePeriod) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.REGEX_YYYY_MM_DD_HH_MM_SS);
		LocalDateTime dateTimeFrame = LocalDateTime.parse((String) jsonData.get(Reports.TRANS_DATE), formatter);
		int time = Integer.parseInt(timePeriod);

		LocalTime timeFrame = (dateTimeFrame.toLocalTime()).with(temp -> {
			int currentMinute = temp.get(ChronoField.MINUTE_OF_DAY);
			int interval = (currentMinute / time) * time;
			temp = temp.with(ChronoField.SECOND_OF_MINUTE, 0);
			temp = temp.with(ChronoField.MILLI_OF_SECOND, 0);
			return temp.with(ChronoField.MINUTE_OF_DAY, interval);
		});
		TimeFrame = String.valueOf(timeFrame);
		return TimeFrame;
	}

	/**
	 * This Method is to get the row count\
	 */
	public void getRowCount() {

		for (int iter = 0; iter < intialData.size(); iter++) {
			String period = intialData.get(iter).get(tableHeaders.get(0));
			if (period.contains(TimeFrame)) {
				break;
			}
			rowCount++;
		}
	}

	/**
	 * This Method is to get the Json Sales Data
	 */
	public void getJsonSalesData() {
		JsonObject sales = (JsonObject) jsonData.get(Reports.SALES);
		String discount = sales.get(Reports.DISCOUNT).getAsString();
		requiredJsonData.add(discount);
		String total = sales.get(Reports.TOTAL).getAsString();
		requiredJsonData.add(total);
		String redeemed = sales.get(Reports.REDEEMED).getAsString();
		requiredJsonData.add(redeemed);
	}

	/**
	 * This Method is for verifying Report Data
	 */
	public void verifyReportData() {
		try {
			foundation.threadWait(Constants.TWO_SECOND);
			for (int iter = 0; iter < tableHeaders.size(); iter++) {
				CustomisedAssert.assertTrue(reportsData.get(rowCount).get(tableHeaders.get(iter))
						.contains(intialData.get(rowCount).get(tableHeaders.get(iter))));
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * This Method is for verifying Header Data
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
	 * This Method is for verifying Total Report Data
	 */
	public void verifyReportFootertData() {
		try {
			foundation.threadWait(Constants.TWO_SECOND);
			for (int iter = 0; iter < tableHeaders.size(); iter++) {
				CustomisedAssert.assertTrue(tableFooterData.get(tableHeaders.get(iter))
						.contains(updatedTableFooters.get(tableHeaders.get(iter))));
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public Map<String, Object> getJsonData() {
		return jsonData;
	}

	public Map<Integer, Map<String, String>> getIntialData() {
		return intialData;
	}

	public Map<Integer, Map<String, String>> getReportsData() {
		return reportsData;
	}

	public List<String> getRequiredJsonData() {
		return requiredJsonData;
	}

	public List<String> getTableHeaders() {
		return tableHeaders;
	}

	public Map<String, String> getTableFooters() {
		return tableFooterData;
	}

	public Map<String, String> getUpdatedTableFooters() {
		return updatedTableFooters;
	}
}
