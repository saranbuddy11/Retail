package at.smartshop.pages;

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
import at.framework.reportsetup.ExtFactory;
import at.framework.ui.Foundation;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.keys.Reports;
import at.smartshop.tests.TestInfra;
import at.smartshop.utilities.WebService;

public class ProductSalesByCategoryReport extends Factory {

	private JsonFile jsonFunctions = new JsonFile();
	private PropertyFile propertyFile = new PropertyFile();
	private WebService webService = new WebService();
	private Foundation foundation = new Foundation();

	private static final By TBL_PRODUCT_SALES_BY_CATEGORY = By.id("productSalesByCategoryGrid");
	private static final By LBL_REPORT_NAME = By.id("Product Sales by Category Report");
	private static final By LBL_REPORT_NAME_STAGING = By.id("Product Sales By Category");
	private static final By TBL_PRODUCT_SALES_BY_CATEGORY_GRID = By.cssSelector("#productSalesByCategoryGrid > tbody");
	private static final By REPORT_GRID_FIRST_ROW = By
			.cssSelector("#productSalesByCategoryGrid > tbody > tr:nth-child(1)");
	private static final By NO_DATA_AVAILABLE_IN_TABLE = By.xpath("//td[@class='dataTables_empty']");
	public final By SEARCH_RESULT = By.xpath("//input[@id='filterType']");
	public static final By DATA_EXISTING_START_DATE_STAGING = By.cssSelector(
			"body > div.daterangepicker.ltr.show-ranges.opensright.show-calendar  > div.drp-calendar.right > div.calendar-table > table > tbody > tr:nth-child(1) > td:nth-child(5)");
	public static final By DATA_EXISTING_END_DATE_STAGING = By.cssSelector(
			"body > div.daterangepicker.ltr.show-ranges.opensright.show-calendar  > div.drp-calendar.right > div.calendar-table > table > tbody > tr:nth-child(2) > td:nth-child(4)");

	private List<String> tableHeaders = new ArrayList<>();
	private int recordCount;
	private Map<String, Object> jsonData = new HashMap<>();
	private Map<Integer, Map<String, String>> reportsData = new LinkedHashMap<>();
	private Map<Integer, Map<String, String>> intialData = new LinkedHashMap<>();

	public Map<Integer, Map<String, String>> getTblRecordsUI() {
		try {
			int recordCount = 0;
			tableHeaders.clear();
			WebElement tableReportsList = getDriver().findElement(TBL_PRODUCT_SALES_BY_CATEGORY_GRID);
			WebElement tableReports = getDriver().findElement(TBL_PRODUCT_SALES_BY_CATEGORY);
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

	public void getRequiredRecord(String orderNumber) {
		int rowCount = 0;
		boolean flag = false;
		try {
			for (int columnCount = 0; columnCount < intialData.size(); columnCount++) {
				if (intialData.get(columnCount).get(tableHeaders.get(0)).equals(orderNumber)) {
					recordCount = rowCount;
					flag = true;
					break;
				}
			}
			if (!flag) {
				Assert.fail();
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void verifyReportName(String reportName, String environment) {
		try {
			if (environment.equals(Constants.STAGING)) {
				foundation.waitforElement(LBL_REPORT_NAME_STAGING, Constants.EXTRA_LONG_TIME);
				String reportTitle = foundation.getText(LBL_REPORT_NAME_STAGING);
				CustomisedAssert.assertTrue(reportTitle.contains(reportName));
			} else {
				foundation.waitforElement(LBL_REPORT_NAME, Constants.EXTRA_LONG_TIME);
				String reportTitles = foundation.getText(LBL_REPORT_NAME);
				CustomisedAssert.assertTrue(reportTitles.contains(reportName));
			}
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

	public void updateSalesAmount() {
		try {
			String initialSalesAmount = intialData.get(recordCount).get(tableHeaders.get(5))
					.replaceAll(Reports.REPLACE_DOLLOR, Constants.EMPTY_STRING);
			String price = (String) jsonData.get(Reports.PRICE);
			String tax = (String) jsonData.get(Reports.TAX);
			String deposit = (String) jsonData.get(Reports.DEPOSIT);
			String discount = (String) jsonData.get(Reports.DISCOUNT);
			double updatedAmount = Double.parseDouble(initialSalesAmount) + Double.parseDouble(price)
					+ Double.parseDouble(tax) + Double.parseDouble(deposit) - Double.parseDouble(discount);
			updatedAmount = Math.round(updatedAmount * 100.0) / 100.0;
			intialData.get(recordCount).put(tableHeaders.get(5), String.valueOf(updatedAmount));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void updatePrice() {
		try {
			String initialSalesAmount = intialData.get(recordCount).get(tableHeaders.get(1))
					.replaceAll(Reports.REPLACE_DOLLOR, Constants.EMPTY_STRING);
			String price = (String) jsonData.get(Reports.PRICE);
			String tax = (String) jsonData.get(Reports.TAX);
			String deposit = (String) jsonData.get(Reports.DEPOSIT);
			String discount = (String) jsonData.get(Reports.DISCOUNT);
			double updatedAmount = Double.parseDouble(initialSalesAmount) + Double.parseDouble(price)
					+ Double.parseDouble(deposit) - Double.parseDouble(discount);
			updatedAmount = Math.round(updatedAmount * 100.0) / 100.0;
			intialData.get(recordCount).put(tableHeaders.get(1), String.valueOf(updatedAmount));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void updateTax() {
		try {
			String initialTax = intialData.get(recordCount).get(tableHeaders.get(2)).replaceAll(Reports.REPLACE_DOLLOR,
					Constants.EMPTY_STRING);
			String tax = (String) jsonData.get(Reports.TAX);
			double updatedTax = Double.parseDouble(initialTax) + Double.parseDouble(tax);
			updatedTax = Math.round(updatedTax * 100.0) / 100.0;
			intialData.get(recordCount).put(tableHeaders.get(2), String.valueOf(updatedTax));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void updateCount(String columnName) {
		try {
			String initialCount = intialData.get(recordCount).get(columnName);
			int updatedCount = Integer.parseInt(initialCount) + 1;
			intialData.get(recordCount).put(columnName, String.valueOf(updatedCount));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void verifyReportHeaders(String columnNames) {
		try {
			List<String> columnName = Arrays.asList(columnNames.split(Constants.DELIMITER_HASH));
			for (int iter = 0; iter < tableHeaders.size(); iter++) {
				CustomisedAssert.assertTrue(tableHeaders.get(iter).equals(columnName.get(iter)));
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void verifyReportData() {
		try {
			for (int iter = 0; iter < tableHeaders.size(); iter++) {
				CustomisedAssert.assertTrue(reportsData.get(recordCount).get(tableHeaders.get(iter))
						.contains(intialData.get(recordCount).get(tableHeaders.get(iter))));
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void processAPI(String scancode, String category, String environment) {
		try {
			generateJsonDetails(environment);
			salesJsonDataUpdate(scancode, category, environment);
			webService.apiReportPostRequest(
					propertyFile.readPropertyFile(Configuration.TRANS_SALES, FilePath.PROPERTY_CONFIG_FILE),
					(String) jsonData.get(Reports.JSON));
			getJsonArrayData(scancode, category);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	private void generateJsonDetails(String environment) {
		try {
			DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(Reports.DATE_FORMAT);
			LocalDateTime tranDate = LocalDateTime.now();
			String transDate = tranDate.format(dateFormat);
			String transID;
			if (environment.equals(Constants.STAGING)) {
				transID = propertyFile.readPropertyFile(Configuration.DEVICE_ID_STAGING, FilePath.PROPERTY_CONFIG_FILE)
						+ Constants.DELIMITER_HYPHEN
						+ transDate.replaceAll(Reports.REGEX_TRANS_DATE, Constants.EMPTY_STRING);
			} else {
				transID = propertyFile.readPropertyFile(Configuration.DEVICE_ID, FilePath.PROPERTY_CONFIG_FILE)
						+ Constants.DELIMITER_HYPHEN
						+ transDate.replaceAll(Reports.REGEX_TRANS_DATE, Constants.EMPTY_STRING);
			}
			;
			jsonData.put(Reports.TRANS_ID, transID);
			jsonData.put(Reports.TRANS_DATE, transDate);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	private void getJsonArrayData(String scancode, String category) {
		try {
			JsonArray items = ((JsonObject) jsonData.get(Reports.SALES)).get(Reports.ITEMS).getAsJsonArray();
			for (JsonElement item : items) {
				JsonObject element = item.getAsJsonObject();
				if (element.get(Reports.SCANCODE).getAsString().equals(scancode)) {
					jsonData.put(Reports.PRICE, element.get(Reports.PRICE).getAsString());
					jsonData.put(Reports.CATEGORY2, element.get(Reports.CATEGORY2).getAsString());
					jsonData.put(Reports.TAX, element.get(Reports.TAX).getAsString());
					jsonData.put(Reports.DEPOSIT, element.get(Reports.DEPOSIT).getAsString());
					jsonData.put(Reports.DISCOUNT, element.get(Reports.DISCOUNT).getAsString());
				}
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	private void jsonArrayDataUpdate(JsonObject jsonObj, String reqString, String salesheader, String scancode,
			String category) {
		try {
			JsonArray items = jsonObj.get(reqString).getAsJsonArray();
			for (JsonElement item : items) {
				JsonObject json = item.getAsJsonObject();
				json.addProperty(Reports.ID,
						UUID.randomUUID().toString().replace(Constants.DELIMITER_HYPHEN, Constants.EMPTY_STRING));
				json.addProperty(Reports.SALES_HEADER, salesheader);
				json.addProperty(Reports.TRANS_ID, (String) jsonData.get(Reports.TRANS_ID));
				json.addProperty(Reports.TRANS_DATE, (String) jsonData.get(Reports.TRANS_DATE));
				if (json.get(Reports.SCANCODE).getAsString().equals(scancode)) {
					json.addProperty(Reports.CATEGORY2, category);
				}
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	private void salesJsonDataUpdate(String scancode, String category, String environment) {
		try {
			String salesHeaderID = UUID.randomUUID().toString().replace(Constants.DELIMITER_HYPHEN,
					Constants.EMPTY_STRING);

			String saleValue;
			if (environment.equals(Constants.STAGING)) {
				saleValue = jsonFunctions.readFileAsString(FilePath.JSON_SALES_CREATION_STAGING);
			} else {
				saleValue = jsonFunctions.readFileAsString(FilePath.JSON_SALES_CREATION);
			}
			;
			JsonObject saleJson = jsonFunctions.convertStringToJson(saleValue);
			saleJson.addProperty(Reports.TRANS_ID, (String) jsonData.get(Reports.TRANS_ID));
			saleJson.addProperty(Reports.TRANS_DATE, (String) jsonData.get(Reports.TRANS_DATE));
			String sale = saleJson.get(Reports.SALE).getAsString();
			JsonObject salesObj = jsonFunctions.convertStringToJson(sale);
			salesObj.addProperty(Reports.ID, salesHeaderID);
			salesObj.addProperty(Reports.TRANS_ID, (String) jsonData.get(Reports.TRANS_ID));
			salesObj.addProperty(Reports.TRANS_DATE, (String) jsonData.get(Reports.TRANS_DATE));
			jsonArrayDataUpdate(salesObj, Reports.ITEMS, salesHeaderID, scancode, category);
			jsonArrayDataUpdate(salesObj, Reports.PAYMENTS, salesHeaderID, scancode, category);
			saleJson.addProperty(Reports.SALE, salesObj.toString());
			jsonData.put(Reports.JSON, saleJson.toString());
			jsonData.put(Reports.SALES, salesObj);
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

	public List<String> getTableHeaders() {
		return tableHeaders;
	}

}
