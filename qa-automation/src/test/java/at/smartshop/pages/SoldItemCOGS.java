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

public class SoldItemCOGS extends Factory {
	private Foundation foundation = new Foundation();
	private WebService webService = new WebService();
	private JsonFile jsonFunctions = new JsonFile();
	private PropertyFile propertyFile = new PropertyFile();

	public static final By LBL_REPORT_NAME = By.cssSelector("#report-container > div > div.col-12.comment-table-heading");
	private static final By REPORT_GRID_FIRST_ROW = By.cssSelector("#rptdt > tbody > tr:nth-child(1)");
	private static final By NO_DATA_AVAILABLE_IN_TABLE = By.xpath("//td[@class='dataTables_empty']");

	private static final By TBL_SOLD_ITEM_COGS = By.cssSelector("#rptdt");
	private static final By TBL_SOLD_ITEM_COGS_GRID = By.cssSelector("#rptdt > tbody");

	private List<String> tableHeaders = new ArrayList<>();
	private Map<String, String> tableFooterData = new LinkedHashMap<>();
	private Map<String, String> updatedTableFooters = new LinkedHashMap<>();
	private Map<String, Object> jsonData = new HashMap<>();
	private List<String> requiredJsonData = new LinkedList<>();
	private Map<Integer, Map<String, String>> reportsData = new LinkedHashMap<>();
	private Map<Integer, Map<String, String>> intialData = new LinkedHashMap<>();
	private String soldCount;
	private String revenue;
	private String totalCOGS;
	private String profit;
	
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
	
	public void processAPI(String value) {
		try {
			generateJsonDetails(value);
			salesJsonDataUpdate();
			webService.apiReportPostRequest(
					propertyFile.readPropertyFile(Configuration.TRANS_SALES, FilePath.PROPERTY_CONFIG_FILE),
					(String) jsonData.get(Reports.JSON));
			foundation.threadWait(Constants.TWO_SECOND);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

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

	private void salesJsonDataUpdate() {
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

	public Map<Integer, Map<String, String>> getTblRecordsUI() {
		try {
			int recordCount = 0;
			tableHeaders.clear();
			WebElement tableReportsList = getDriver().findElement(TBL_SOLD_ITEM_COGS_GRID);
			WebElement tableReports = getDriver().findElement(TBL_SOLD_ITEM_COGS);
			List<WebElement> columnHeaders = tableReports.findElements(By.cssSelector("thead > tr > th"));
			List<WebElement> rows = tableReportsList.findElements(By.tagName("tr"));
			List<WebElement> footerRow = tableReports.findElements(By.cssSelector("tfoot > tr"));
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
			System.out.println("reportsData : "+ reportsData);
			System.out.println("tableFooterData : "+ tableFooterData);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		return reportsData;
	}


	public void saleCount(String columnName) {
		try {
			for (int iter = 0; iter < reportsData.size(); iter++) {
				String saleCount = intialData.get(iter).get(columnName);
				int updatedCount = Integer.parseInt(saleCount) + 1;
				soldCount = String.valueOf(updatedCount);
				intialData.get(iter).put(columnName, soldCount);
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
	
	public void calculateTotalCOGS(String columnName, String cost) {
		try {
			for (int iter = 0; iter < reportsData.size(); iter++) {
				double updatedAmount = Double.parseDouble(cost)* Double.parseDouble(soldCount);
				updatedAmount = Math.round(updatedAmount * 100.0) / 100.0;
				totalCOGS = String.valueOf(updatedAmount);
				intialData.get(iter).put(columnName, Constants.DOLLAR_SYMBOL + totalCOGS);
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void calculateRevenue(String columnName, String price) {
		try {
			for (int iter = 0; iter < reportsData.size(); iter++) {
				double updatedAmount = Double.parseDouble(price)* Double.parseDouble(soldCount);
				updatedAmount = Math.round(updatedAmount * 100.0) / 100.0;
				revenue = String.valueOf(updatedAmount);
				intialData.get(iter).put(columnName, Constants.DOLLAR_SYMBOL + revenue);
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
	
	public void calculateProfit(String columnName) {
		try {
			for (int iter = 0; iter < reportsData.size(); iter++) {
				double updatedProfit = Double.parseDouble(revenue)- Double.parseDouble(totalCOGS);
				updatedProfit = Math.round(updatedProfit * 100.0) / 100.0;
				profit = String.valueOf(updatedProfit);
				intialData.get(iter).put(columnName, Constants.DOLLAR_SYMBOL + profit);
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
	public void updateData(String columnName, String values) {
		try {
			for (int iter = 0; iter < reportsData.size(); iter++) {
				intialData.get(iter).put(columnName, values);
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
	
	public void updateMultipleData(String columnName, String values) {
		try {
			List<String> value = Arrays.asList(values.split(Constants.DELIMITER_HASH));
			for (int iter = 0; iter < reportsData.size(); iter++) {
				intialData.get(iter).put(columnName, value.get(iter));
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
	
	public void updateCostPercentOfFooter(String columnName) {
		double costPercent = (Double.parseDouble(totalCOGS) / Double.parseDouble(revenue)) * 100.0;
		costPercent = Math.round(costPercent * 100.0) / 100.0;
		updatedTableFooters.put(columnName, String.valueOf(costPercent)+Constants.DELIMITER_PERCENTAGE);
	}
	
	public void calculateIntegerTotalOfFooter(String columnName) {
		int rowCount = reportsData.size();
		int totalValue = 0;
		for (int iter = 0; iter < rowCount; iter++) {
			int value = Integer.parseInt(reportsData.get(iter).get(columnName)
					.replaceAll(Reports.REPLACE_DOLLOR, Constants.EMPTY_STRING));
			totalValue = totalValue + value;
		}
		updatedTableFooters.put(columnName, String.valueOf(totalValue));
	}

	public void calculateDoubleTotalOfFooter(String columnName) {
		int rowCount = reportsData.size();
		double totalValue = 0.0;
		for (int iter = 0; iter < rowCount; iter++) {
			double value = Double.parseDouble(reportsData.get(iter).get(columnName)
					.replaceAll(Reports.REPLACE_DOLLOR, Constants.EMPTY_STRING));
			totalValue = totalValue + value;
			totalValue = Math.round(totalValue * 100.0) / 100.0;
		}
		updatedTableFooters.put(columnName, String.valueOf(totalValue));
	}
	
	
	public void verifyReportData() {
		try {
			int count = intialData.size();
			foundation.threadWait(Constants.TWO_SECOND);
			System.out.println( "reportsData :" + reportsData);
			System.out.println( "intialData :" + intialData);
			for (int counter = 0; counter < count; counter++) {
				for (int iter = 0; iter < tableHeaders.size(); iter++) {
					CustomisedAssert.assertTrue(reportsData.get(counter).get(tableHeaders.get(iter))
							.contains(intialData.get(counter).get(tableHeaders.get(iter))));
				}
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void verifyReportHeaders(String columnNames) {
		try {
			List<String> columnName = Arrays.asList(columnNames.split(Constants.DELIMITER_HASH));
			System.out.println( "tableHeaders :" + tableHeaders);
			System.out.println( "columnName :" + columnName);
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
			System.out.println( "tableFooterData :" + tableFooterData);
			System.out.println( "updatedTableFooters :" + updatedTableFooters);
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
