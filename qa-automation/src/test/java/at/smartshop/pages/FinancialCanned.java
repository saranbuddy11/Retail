package at.smartshop.pages;

import java.math.RoundingMode;
import java.text.DecimalFormat;
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

public class FinancialCanned extends Factory {
	private WebService webService = new WebService();
	private JsonFile jsonFunctions = new JsonFile();
	private PropertyFile propertyFile = new PropertyFile();
	private Foundation foundation = new Foundation();

	public static final By LBL_REPORT_NAME = By
			.cssSelector("#report-container > div > div.col-12.comment-table-heading");
	private static final By REPORT_GRID_FIRST_ROW = By.cssSelector("#rptdt > tbody > tr:nth-child(1)");
	private static final By NO_DATA_AVAILABLE_IN_TABLE = By.xpath("//td[@class='dataTables_empty']");

	private static final By TBL_PRODUCT_CANNED = By.cssSelector("#rptdt");
	private static final By TBL_PRODUCT_CANNED_GRID = By.cssSelector("#rptdt > tbody");

	private List<String> tableHeaders = new ArrayList<>();
	private Map<String, Object> jsonData = new HashMap<>();
	private List<String> requiredJsonData = new LinkedList<>();
	private Map<Integer, Map<String, String>> reportsData = new LinkedHashMap<>();
	private Map<Integer, Map<String, String>> intialData = new LinkedHashMap<>();
	int recordCount = 0;

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
			WebElement tableReportsList = getDriver().findElement(TBL_PRODUCT_CANNED_GRID);
			WebElement tableReports = getDriver().findElement(TBL_PRODUCT_CANNED);
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

	public void getRequiredRecord(String productName) {
		try {
			for (int rowCount = 0; rowCount < intialData.size(); rowCount++) {
				if (intialData.get(rowCount).get(tableHeaders.get(0)).equals(productName)) {
					recordCount = rowCount;
					break;
				}
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * This method is to Calculate the Sales
	 * 
	 * @param columnName
	 * @param price
	 * @return 
	 */
	public String calculateSalesAmount(String columnName, String price, String tax) {
		String sales = null;
		try {
			for (int iter = 0; iter < reportsData.size(); iter++) {
				String initialAmount = intialData.get(iter).get(columnName).replaceAll(Reports.REPLACE_DOLLOR,
						Constants.EMPTY_STRING);
				double updatedAmount = Double
						.parseDouble(price.replaceAll(Reports.REPLACE_DOLLOR, Constants.EMPTY_STRING))*2
						+Double
						.parseDouble(tax.replaceAll(Reports.REPLACE_DOLLOR, Constants.EMPTY_STRING))*2
						+ Double.parseDouble(initialAmount);
				updatedAmount = Math.round(updatedAmount * 100.0) / 100.0;
				sales = String.valueOf(updatedAmount);
				intialData.get(iter).put(columnName, Constants.DOLLAR_SYMBOL + sales);
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		return sales;
	}
	
	public String calculateCost(String columnName, String price) {
		String sales = null;
		try {
			for (int iter = 0; iter < reportsData.size(); iter++) {
				String initialAmount = intialData.get(iter).get(columnName).replaceAll(Reports.REPLACE_DOLLOR,
						Constants.EMPTY_STRING);
				double updatedAmount = Double
						.parseDouble(price.replaceAll(Reports.REPLACE_DOLLOR, Constants.EMPTY_STRING))*2
						+ Double.parseDouble(initialAmount);
				updatedAmount = Math.round(updatedAmount * 100.0) / 100.0;
				sales = String.valueOf(updatedAmount);
				intialData.get(iter).put(columnName, Constants.DOLLAR_SYMBOL + sales);
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		return sales;
	}

	public String calculateSpoilDamagesAmount(String columnName, String price) {
		String cost = null;
		try {
			for (int iter = 0; iter < reportsData.size(); iter++) {
				String initialAmount = intialData.get(iter).get(columnName).replaceAll(Reports.REPLACE_DOLLOR,
						Constants.EMPTY_STRING);
				double updatedAmount = Double
						.parseDouble(price.replaceAll(Reports.REPLACE_DOLLOR, Constants.EMPTY_STRING))
						+ Double.parseDouble(initialAmount);
				updatedAmount = Math.round(updatedAmount * 100.0) / 100.0;
				cost = String.valueOf(updatedAmount);
				intialData.get(iter).put(columnName, Constants.DOLLAR_SYMBOL + cost);
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		return cost;
	}
	
	public void calculateGrossMargin(String columnName, String productCostPercentage, String spoilPercentage, String shortPercentage) {
		try {
			double margin = 100 - Double.parseDouble(productCostPercentage)
					- Double.parseDouble(spoilPercentage)
					- Double.parseDouble(shortPercentage);
			margin = Math.round(margin * 100.0) / 100.0;
			String GMPercentage = String.valueOf(margin);
			intialData.get(0).put(columnName, GMPercentage);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
	
	public void updateLastYearAmount(String columnName) {
		String lastYearofYearAmount = intialData.get(0).get(columnName).replaceAll(Reports.REPLACE_DOLLOR,
				Constants.EMPTY_STRING);
		String salesAmount = intialData.get(0).get(getTableHeaders().get(1)).replaceAll(Reports.REPLACE_DOLLOR,
				Constants.EMPTY_STRING);
		double lastYearAmount = (Double.parseDouble(salesAmount)
				- (Double.parseDouble(lastYearofYearAmount)+Double.parseDouble(salesAmount))-(3.66));
		lastYearAmount = Math.round(lastYearAmount * 100.0) / 100.0;
		intialData.get(0).put(columnName, String.valueOf(lastYearAmount));
	}
	
	public void updateLastYearPercent(String columnName) {
		String lastYearofYearAmount = intialData.get(0).get(getTableHeaders().get(11)).replaceAll(Reports.REPLACE_DOLLOR,
				Constants.EMPTY_STRING);
		String salesAmount = intialData.get(0).get(getTableHeaders().get(1)).replaceAll(Reports.REPLACE_DOLLOR,
				Constants.EMPTY_STRING);
			double lastYearPercent = (Double.parseDouble(salesAmount)
					/ ((Double.parseDouble(lastYearofYearAmount)+Double.parseDouble(salesAmount)))* 100);
			lastYearPercent = Math.round(lastYearPercent * 100.0) / 100.0;
			intialData.get(0).put(columnName, String.valueOf(lastYearPercent));
	}
	
	/**
	 * This method is to Calculate the amount
	 * 
	 * @param columnName
	 * @param price
	 * @param tax
	 */
	public void calculateAmount(String columnName, String price, String tax) {
		try {
			for (int iter = 0; iter < reportsData.size(); iter++) {
				String initialAmount = intialData.get(iter).get(columnName).replaceAll(Reports.REPLACE_DOLLOR,
						Constants.EMPTY_STRING);
				double updatedAmount = Double
						.parseDouble(price.replaceAll(Reports.REPLACE_DOLLOR, Constants.EMPTY_STRING))
						+ Double.parseDouble(tax.replaceAll(Reports.REPLACE_DOLLOR, Constants.EMPTY_STRING))
						+ Double.parseDouble(initialAmount);
				updatedAmount = Math.round(updatedAmount * 100.0) / 100.0;
				intialData.get(iter).put(columnName, Constants.DOLLAR_SYMBOL + String.valueOf(updatedAmount));
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * This method is to Calculate the Sales Count
	 * 
	 * @param columnName
	 * @return
	 */
	public String saleCount(String columnName) {
		String count = null;
		try {
			String saleCount = intialData.get(recordCount).get(columnName);
			int updatedCount = Integer.parseInt(saleCount) + 1;
			count = String.valueOf(updatedCount);
			intialData.get(recordCount).put(columnName, count);
			return count;
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		return count;
	}

	/**
	 * This method is to Update the Data
	 * 
	 * @param columnName
	 * @param values
	 */
	public void updateData(String columnName, String values) {
		try {
			for (int iter = 0; iter < reportsData.size(); iter++) {
				intialData.get(recordCount).put(columnName, values);
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * This method is to calculate percentage
	 * 
	 * @param columnName
	 * @param salesUnit
	 * @param unit
	 */
	public String calculatePercent(String columnName, String unit, String salesUnit) {
		double percent = (Double.parseDouble(unit) / Double.parseDouble(salesUnit)) * 100;
		double amountPercent = Math.round(percent * 100.0) / 100.0;
		String percentValue = String.valueOf(amountPercent);
		intialData.get(recordCount).put(columnName, percentValue);
		return percentValue;
	}

	/**
	 * This method is to get the Decremented Inventory Value
	 * 
	 * @param inventoryValue
	 * @return
	 */
	public String decrementedInventoryValue(String inventoryValue) {
		int value = Integer.parseInt(inventoryValue) - 1;
		String updatedValue = String.valueOf(value);
		return updatedValue;
	}

	/**
	 * This method is to Verify Report Data
	 */
	public void verifyReportData() {
		try {
			foundation.threadWait(Constants.TWO_SECOND);
			for (int iter = 0; iter < tableHeaders.size(); iter++) {
				CustomisedAssert.assertTrue(reportsData.get(recordCount).get(tableHeaders.get(iter))
						.contains(intialData.get(recordCount).get(tableHeaders.get(iter))));
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * This method is to Verify Report Headers
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
