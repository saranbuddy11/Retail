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

public class TransactionCannedReport extends Factory {

	private JsonFile jsonFunctions = new JsonFile();
	private PropertyFile propertyFile = new PropertyFile();
	private WebService webService = new WebService();
	private Foundation foundation = new Foundation();

	private By tblTransactionCanned = By.id("rptdt");
	public By lblReportName = By.cssSelector("#report-container > div > div.col-12.comment-table-heading");
	private By tblTransactionCannedGrid = By.cssSelector("#rptdt > tbody");
	private static final By REPORT_GRID_FIRST_ROW = By.cssSelector("#rptdt > tbody > tr:nth-child(1)");
	private static final By NO_DATA_AVAILABLE_IN_TABLE = By.xpath("//td[@class='dataTables_empty']");

	private List<String> tableHeaders = new ArrayList<>();
	private List<String> requiredJsonData = new LinkedList<>();
	private Map<String, Object> jsonData = new HashMap<>();
	private Map<Integer, Map<String, String>> reportsData = new LinkedHashMap<>();
	private Map<Integer, Map<String, String>> intialData = new LinkedHashMap<>();
	private Map<Integer, Map<String, String>> intialTotal = new LinkedHashMap<>();
	private Map<Integer, Map<String, String>> updatedTotal = new LinkedHashMap<>();

	public Map<Integer, Map<String, String>> getTblRecordsUI() {
		try {
			int recordCount = 0;
			tableHeaders.clear();
			WebElement tableReportsGrid = getDriver().findElement(tblTransactionCannedGrid);
			WebElement tableReports = getDriver().findElement(tblTransactionCanned);
			List<WebElement> columnHeaders = tableReports.findElements(By.cssSelector("thead > tr > th"));
			List<WebElement> rows = tableReportsGrid.findElements(By.tagName("tr"));
			for (WebElement columnHeader : columnHeaders) {
				tableHeaders.add(columnHeader.getText());
			}
			for (WebElement row : rows) {
				Map<String, String> uiTblRowValues = new LinkedHashMap<>();
				for (int columnCount = 1; columnCount < tableHeaders.size() + 1; columnCount++) {
					WebElement column = row.findElement(By.cssSelector("td:nth-child(" + columnCount + ")"));
					if (tableHeaders.get(columnCount - 1).equals(tableHeaders.get(0))) {
						uiTblRowValues.put(tableHeaders.get(columnCount - 1), column.getText());
					} else {
						uiTblRowValues.put(tableHeaders.get(columnCount - 1),
								column.getText().replaceAll(Reports.REPLACE_DOLLOR, Constants.EMPTY_STRING));
					}
				}
				reportsData.put(recordCount, uiTblRowValues);
				recordCount++;
			}
			WebElement tableFooter = tableReports.findElement(By.cssSelector("tfoot > tr"));
			Map<String, String> uiTblRowValues = new LinkedHashMap<>();
			for (int columnCount = 1; columnCount < tableHeaders.size() + 1; columnCount++) {
				WebElement column = tableFooter.findElement(By.cssSelector("th:nth-child(" + columnCount + ")"));
				uiTblRowValues.put(tableHeaders.get(columnCount - 1), column.getText());
			}
			updatedTotal.put(0, uiTblRowValues);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		return reportsData;
	}

	public void calculateIntegerTotal(String columnName) {
		int rowCount = reportsData.size();
		int totalValue = 0;
		for (int iter = 0; iter < rowCount; iter++) {
			int value = Integer.parseInt(reportsData.get(iter).get(columnName));
			totalValue = totalValue + value;
		}
		intialTotal.get(0).put(columnName, String.valueOf(totalValue));
	}

	public void calculateDoubleTotal(String columnName) {
		int rowCount = reportsData.size();
		double totalValue = 0.0;
		for (int iter = 0; iter < rowCount; iter++) {
			double value = Double.parseDouble(reportsData.get(iter).get(columnName));
			totalValue = totalValue + value;
			totalValue = Math.round(totalValue * 100.0) / 100.0;
		}
		intialTotal.get(0).put(columnName, String.valueOf(totalValue));
	}

	public void updateTotal() {
		calculateIntegerTotal(tableHeaders.get(1));
		calculateIntegerTotal(tableHeaders.get(2));
		calculateIntegerTotal(tableHeaders.get(7));
		calculateIntegerTotal(tableHeaders.get(13));
	}

	public void updateDecimalTotal() {
		calculateDoubleTotal(tableHeaders.get(3));
		calculateDoubleTotal(tableHeaders.get(4));
		calculateDoubleTotal(tableHeaders.get(5));
		calculateDoubleTotal(tableHeaders.get(6));
		calculateDoubleTotal(tableHeaders.get(8));
		calculateDoubleTotal(tableHeaders.get(10));
		calculateDoubleTotal(tableHeaders.get(11));
		calculateDoubleTotal(tableHeaders.get(12));
		calculateDoubleTotal(tableHeaders.get(14));
		calculateDoubleTotal(tableHeaders.get(16));
		calculateDoubleTotal(tableHeaders.get(17));
		calculateDoubleTotal(tableHeaders.get(18));
	}

	public void verifyReportName(String reportName) {
		try {
			foundation.waitforElement(lblReportName, Constants.EXTRA_LONG_TIME);
			String reportTitle = foundation.getText(lblReportName);
			CustomisedAssert.assertTrue(reportTitle.contains(reportName));
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


	public void updateColumnData(String columnName, String values) {
		try {
			intialData.get(0).put(columnName, values);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void updateTransactions(String columnName, String transactions) {
		try {
			String initialTrans = intialData.get(0).get(columnName);
			int updatedTrans = Integer.parseInt(initialTrans) + Integer.parseInt(transactions);
			intialData.get(0).put(columnName, String.valueOf(updatedTrans));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void updatePercent(String amountColumn, String totalAmountColumn, String requiredColumn) {
		try {
			String amount = reportsData.get(0).get(amountColumn);
			String totalAmount = reportsData.get(0).get(totalAmountColumn);
			double amountPercent = (Double.parseDouble(amount) / Double.parseDouble(totalAmount)) * 100;
			amountPercent = Math.round(amountPercent * 100.0) / 100.0;
			intialData.get(0).put(requiredColumn, String.valueOf(amountPercent));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void updateSales(String salesColumn, String transColumn, String requiredColumn) {
		try {
			String salesPerTransaction = reportsData.get(0).get(salesColumn);
			String transactions = reportsData.get(0).get(transColumn);
			double unitsPerTrans = Integer.parseInt(transactions) * Double.parseDouble(salesPerTransaction);
			unitsPerTrans = Math.round(unitsPerTrans * 100.0) / 100.0;
			intialData.get(0).put(requiredColumn, String.valueOf(unitsPerTrans));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void updateUnitsPerTransactions(String unitsColumn, String transColumn, String requiredColumn) {
		try {
			String salesUnits = reportsData.get(0).get(unitsColumn);
			String transactions = reportsData.get(0).get(transColumn);
			double unitsPerTrans = Double.parseDouble(salesUnits) / Integer.parseInt(transactions);
			intialData.get(0).put(requiredColumn, String.valueOf(unitsPerTrans));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void verifyReportHeaders(String columnNames) {
		try {
			List<String> columnName = Arrays.asList(columnNames.split(Constants.DELIMITER_TILD));
			for (int iter = 0; iter < tableHeaders.size(); iter++) {
				CustomisedAssert.assertTrue(tableHeaders.get(iter).equals(columnName.get(iter)));
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void verifyReportData() {
		try {
			int count = intialData.size();
			for (int counter = 0; counter < count; counter++) {
				for (int iter = 0; iter < tableHeaders.size(); iter++) {
					if (iter == 3 || iter == 5 || iter == 6) {
						continue;
					}
					CustomisedAssert.assertTrue(reportsData.get(counter).get(tableHeaders.get(iter))
							.contains(intialData.get(counter).get(tableHeaders.get(iter))));
				}
			}
			for (int iter = 0; iter < tableHeaders.size(); iter++) {
				if (iter == 3 || iter == 5 || iter == 6) {
					continue;
				}
				CustomisedAssert.assertTrue(updatedTotal.get(0).get(tableHeaders.get(iter))
						.contains(intialTotal.get(0).get(tableHeaders.get(iter))));
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void processAPI(String paymentType, String deviceId) {
		try {
			List<String> payType = Arrays.asList(paymentType.split(Constants.DELIMITER_HASH));
			for (int paymentCount = 0; paymentCount < payType.size(); paymentCount++) {
				generateJsonDetails(deviceId);
				salesJsonDataUpdate(payType.get(paymentCount));
				webService.apiReportPostRequest(
						propertyFile.readPropertyFile(Configuration.TRANS_SALES, FilePath.PROPERTY_CONFIG_FILE),
						(String) jsonData.get(Reports.JSON));
			}
			getJsonSalesData();
			Thread.sleep(3000);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	private void getJsonSalesData() {
		try {
			JsonObject objSales = (JsonObject) jsonData.get(Reports.SALES);
			String total = objSales.get(Reports.TOTAL).getAsString();
			requiredJsonData.add(total);
			String locationID = objSales.get(Reports.LOCATION).getAsString();
			requiredJsonData.add(locationID);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	private Map<String, Object> generateJsonDetails(String deviceId) {
		try {
			DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(Reports.DATE_FORMAT);
			LocalDateTime tranDate = LocalDateTime.now();
			String transDate = tranDate.format(dateFormat);
			String transID = deviceId + Constants.DELIMITER_HYPHEN
					+ transDate.replaceAll(Reports.REGEX_TRANS_DATE, Constants.EMPTY_STRING);
			jsonData.put(Reports.TRANS_ID, transID);
			jsonData.put(Reports.TRANS_DATE, transDate);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		return jsonData;
	}

	private void jsonArrayDataUpdate(JsonObject jsonObj, String requiredJsonValue, String salesheader,
			String paymentType) {
		try {
			JsonArray items = jsonObj.get(requiredJsonValue).getAsJsonArray();
			for (JsonElement item : items) {
				JsonObject json = item.getAsJsonObject();
				json.addProperty(Reports.ID,
						UUID.randomUUID().toString().replace(Constants.DELIMITER_HYPHEN, Constants.EMPTY_STRING));
				json.addProperty(Reports.SALES_HEADER, salesheader);
				json.addProperty(Reports.TRANS_ID, (String) jsonData.get(Reports.TRANS_ID));
				json.addProperty(Reports.TRANS_DATE, (String) jsonData.get(Reports.TRANS_DATE));
				if (requiredJsonValue.equals(Reports.PAYMENTS)) {
					json.addProperty(Reports.TYPE, paymentType);
				}
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	private Map<String, Object> salesJsonDataUpdate(String paymentType) {
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
			jsonArrayDataUpdate(salesObj, Reports.ITEMS, salesHeaderID, paymentType);
			jsonArrayDataUpdate(salesObj, Reports.PAYMENTS, salesHeaderID, paymentType);
			saleJson.addProperty(Reports.SALE, salesObj.toString());
			jsonData.put(Reports.JSON, saleJson.toString());
			jsonData.put(Reports.SALES, salesObj);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		return jsonData;
	}

	public Map<String, Object> getData() {
		return jsonData;
	}

	public Map<Integer, Map<String, String>> getIntialData() {
		return intialData;
	}

	public Map<Integer, Map<String, String>> getIntialTotal() {
		return intialTotal;
	}

	public Map<Integer, Map<String, String>> getUpdatedTotal() {
		return updatedTotal;
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

}
