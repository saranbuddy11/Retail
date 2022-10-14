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
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

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

public class SalesAnalysisReport extends Factory {

	public static final By LBL_REPORT_NAME = By.id("Sales Analysis Report");
	private static final By REPORT_GRID_FIRST_ROW = By.cssSelector("#hierarchicalGrid > tbody > tr:nth-child(1)");
	private static final By NO_DATA_AVAILABLE_IN_TABLE = By.xpath("//td[@class='dataTables_empty']");
	private static final By TBL_SALES_ANALYSIS = By.cssSelector("#hierarchicalGrid");
	private static final By TBL_SALES_ANALYSIS_GRID = By.cssSelector("#hierarchicalGrid > tbody");
	public static final By TBL_EXPAND_ROW = By.xpath("//span[@title='Expand Row']");
	private static final By TBL_SALES_ANALYSIS_DETAILED_GROUPBY_LOCATIONS = By
			.cssSelector("#hierarchicalGrid > tbody > tr:nth-child(2) > td  > div >div >div >table");
	private static final By TBL_SALES_ANALYSIS_GRID_DETAILED_GROUPBY_LOCATIONS = By
			.cssSelector("#hierarchicalGrid > tbody > tr:nth-child(2) > td  > div >div >div >table > tbody");

	private Foundation foundation = new Foundation();
	private WebService webService = new WebService();
	private JsonFile jsonFunctions = new JsonFile();
	private PropertyFile propertyFile = new PropertyFile();

	private List<String> tableHeaders = new ArrayList<>();
	private Map<String, Object> jsonData = new HashMap<>();
	private List<String> requiredJsonData = new LinkedList<>();
	private Map<Integer, Map<String, String>> reportsData = new LinkedHashMap<>();
	private Map<Integer, Map<String, String>> intialData = new LinkedHashMap<>();

	public void verifyReportName(String reportName) {
		try {
			String reportTitle = foundation.getText(LBL_REPORT_NAME);
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

	public double getGMValueUsingCalculation(String productPrice, String productName, String columnSold,
			String columnDiscount, String columnCost) {
		String soldCount = foundation.getText(objReportColumn(columnSold, productName));
		double totalProductPrice = Double.parseDouble(soldCount) * Double.parseDouble(productPrice);

		String discountValue = foundation.getText(objReportColumn(columnDiscount, productName)).replace("$",
				Constants.EMPTY_STRING);
		String costValue = foundation.getText(objReportColumn(columnCost, productName)).replace("$",
				Constants.EMPTY_STRING);

		double finalValue = (totalProductPrice - Double.parseDouble(discountValue.trim())
				- Double.parseDouble(costValue.trim())) * 100
				/ (totalProductPrice - Double.parseDouble(discountValue.trim()));
		finalValue = Math.round(finalValue * 100.0) / 100.0;
		return finalValue;
	}

	public double getGMValue(String columnName, String productName) {
		String value = foundation.getText(objReportColumn(columnName, productName));
		return Double.parseDouble(value.replace("%", Constants.EMPTY_STRING).trim());
	}

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

	public void getUITblRecordsGroupbyLocations() {
		try {
			int recordCount = 0;
			tableHeaders.clear();
			reportsData.clear();
			JavascriptExecutor js = (JavascriptExecutor) getDriver();
			js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
			WebElement tableReportsList = getDriver().findElement(TBL_SALES_ANALYSIS_GRID_DETAILED_GROUPBY_LOCATIONS);
			WebElement tableReports = getDriver().findElement(TBL_SALES_ANALYSIS_DETAILED_GROUPBY_LOCATIONS);
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
	}

	public void calculateAmount(String columnName, String amount) {
		try {
			for (int iter = 0; iter < reportsData.size(); iter++) {
				String initialAmount = intialData.get(iter).get(columnName).replaceAll(Reports.REPLACE_DOLLOR,
						Constants.EMPTY_STRING);
				double updatedAmount = Double
						.parseDouble(amount.replaceAll(Reports.REPLACE_DOLLOR, Constants.EMPTY_STRING))
						+ Double.parseDouble(initialAmount);
				updatedAmount = Math.round(updatedAmount * 100.0) / 100.0;
				intialData.get(iter).put(columnName, Constants.DOLLAR_SYMBOL + String.valueOf(updatedAmount));
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void saleCount(String columnName) {
		try {
			for (int iter = 0; iter < reportsData.size(); iter++) {
				String saleCount = intialData.get(iter).get(columnName);
				int updatedCount = Integer.parseInt(saleCount) + 1;
				intialData.get(iter).put(columnName, String.valueOf(updatedCount));
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void getGMValueUsingCalculationForAllProducts(String columnName, String productPrice) {
		for (int iter = 0; iter < reportsData.size(); iter++) {
			String soldCount = intialData.get(iter).get(tableHeaders.get(7));
			double totalProductPrice = Double.parseDouble(soldCount) * Double.parseDouble(productPrice);
			String discountValue = intialData.get(iter).get(tableHeaders.get(11)).replace("$", Constants.EMPTY_STRING);
			String costValue = intialData.get(iter).get(tableHeaders.get(13)).replace("$", Constants.EMPTY_STRING);
			double finalValue = (totalProductPrice - Double.parseDouble(discountValue.trim())
					- Double.parseDouble(costValue.trim())) * 100
					/ (totalProductPrice - Double.parseDouble(discountValue.trim()));
			finalValue = Math.round(finalValue * 100.0) / 100.0;
			String gmValue = String.valueOf(finalValue);
			intialData.get(iter).put(columnName, gmValue);
		}
	}

	public void verifyReportData() {
		try {
			int count = intialData.size();
			foundation.threadWait(Constants.TWO_SECOND);
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

	public void removeReportDataFirstValue() {
		try {
			for (int iter = 0; iter < reportsData.size(); iter++) {
				intialData.get(iter).remove(Constants.DELIMITER_EMPTY);
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public List<String> removeHeaderFirstValue(String columnNames) {
		List<String> columnName = new LinkedList<String>(Arrays.asList(columnNames.split(Constants.DELIMITER_HASH)));
		columnName.remove(0);
		return columnName;
	}
	
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


	public void verifyReportHeadersForLocation(List<String> columnName, List<String> tableHeaders) {
		try {
			foundation.threadWait(Constants.ONE_SECOND);
			for (int iter = 0; iter < tableHeaders.size(); iter++) {
				CustomisedAssert.assertTrue(tableHeaders.get(iter).equals(columnName.get(iter)));
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
}
