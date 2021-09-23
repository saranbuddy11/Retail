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

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import at.framework.browser.Factory;
import at.framework.files.JsonFile;
import at.framework.files.PropertyFile;
import at.framework.ui.Foundation;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.keys.Reports;
import at.smartshop.tests.TestInfra;
import at.smartshop.utilities.WebService;

public class MemberPurchaseSummaryReport extends Factory {

	private JsonFile jsonFunctions = new JsonFile();
	private PropertyFile propertyFile = new PropertyFile();
	private WebService webService = new WebService();
	private Foundation foundation = new Foundation();

	private static final By TBL_MEMBER_PURCHASE_SUMMARY = By.id("rptdt");
	public static final By LBL_REPORT_NAME = By
			.cssSelector("#report-container > div > div.col-12.comment-table-heading");
	private static final By TBL_MEMBER_PURCHASE_SUMMARY_GRID = By.cssSelector("#rptdt > tbody");

	private List<String> tableHeaders = new ArrayList<>();
	private List<String> requiredJsonData = new LinkedList<>();
	private int recordCount;
	private Map<String, Object> jsonData = new HashMap<>();
	private Map<Integer, Map<String, String>> reportsData = new LinkedHashMap<>();
	private Map<Integer, Map<String, String>> intialData = new LinkedHashMap<>();

	public Map<Integer, Map<String, String>> getTblRecordsUI() {
		try {
			int recordCount = 0;
			tableHeaders.clear();
			WebElement tableReportsList = getDriver().findElement(TBL_MEMBER_PURCHASE_SUMMARY_GRID);
			WebElement tableReports = getDriver().findElement(TBL_MEMBER_PURCHASE_SUMMARY);
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
			Assert.fail(exc.toString());
		}
		return reportsData;
	}

	public void getRequiredRecord(String values) {
		try {
			List<String> value = Arrays.asList(values.split(Constants.DELIMITER_HASH));
			for (int rowCount = 0; rowCount < intialData.size(); rowCount++) {
				if (intialData.get(rowCount).get(tableHeaders.get(0)).equals(value.get(0))) {
					recordCount = rowCount;
					break;
				}
			}
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	public void verifyReportName(String reportName) {
		try {
			String reportTitle = foundation.getText(LBL_REPORT_NAME);
			Assert.assertTrue(reportTitle.contains(reportName));
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	public void updateData(String values) {
		try {
			List<String> value = Arrays.asList(values.split(Constants.DELIMITER_HASH));
			getRequiredRecord(value.get(0));
			intialData.get(recordCount).put(tableHeaders.get(1), value.get(1));
			intialData.get(recordCount).put(tableHeaders.get(2), value.get(2));
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	public void updateAmount(String columnName, String value) {
		try {
			String intialValue = intialData.get(recordCount).get(columnName).replaceAll(Reports.REPLACE_DOLLOR,
					Constants.EMPTY_STRING);
			double updatedValue = Double.parseDouble(intialValue) + Double.parseDouble(value);
			updatedValue = Math.round(updatedValue * 100.0) / 100.0;
			intialData.get(recordCount).put(columnName, String.valueOf(updatedValue));
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	public void updateTotal() {
		try {
			String subTotal = reportsData.get(recordCount)
					.get(tableHeaders.get(3)).replaceAll(Reports.REPLACE_DOLLOR, Constants.EMPTY_STRING);
			String taxAmount = reportsData.get(recordCount)
					.get(tableHeaders.get(4)).replaceAll(Reports.REPLACE_DOLLOR, Constants.EMPTY_STRING);
			double updatedTotal = Double.parseDouble(subTotal) + Double.parseDouble(taxAmount);
			updatedTotal = Math.round(updatedTotal * 100.0) / 100.0;
			intialData.get(recordCount).put(tableHeaders.get(5), String.valueOf(updatedTotal));
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	public void verifyReportHeaders(String columnNames) {
		try {
			List<String> columnName = Arrays.asList(columnNames.split(Constants.DELIMITER_HASH));
			for (int iter = 0; iter < tableHeaders.size(); iter++) {
				Assert.assertTrue(tableHeaders.get(iter).equals(columnName.get(iter)));
			}
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	public void verifyReportData() {
		try {
			int count = intialData.size();
			for (int counter = 0; counter < count; counter++) {
				for (int iter = 0; iter < tableHeaders.size(); iter++) {
					Assert.assertTrue(reportsData.get(counter).get(tableHeaders.get(iter))
							.contains(intialData.get(counter).get(tableHeaders.get(iter))));
				}
			}
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	public void processAPI() {
		try {
			generateJsonDetails();
			salesJsonDataUpdate();
			gmaJsonDataUpdate();
			webService.apiReportPostRequest(
					propertyFile.readPropertyFile(Configuration.TRANS_SALES, FilePath.PROPERTY_CONFIG_FILE),
					(String) jsonData.get(Reports.JSON));
			webService.apiReportPostRequest(
					propertyFile.readPropertyFile(Configuration.TRANS_GMA, FilePath.PROPERTY_CONFIG_FILE),
					(String) jsonData.get(Reports.GMA_JSON));
			getJsonSalesData();
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	private void getJsonSalesData() {
		try {
			JsonObject sales = (JsonObject) jsonData.get(Reports.SALES);
			String subTotal = sales.get(Reports.SUBTOTAL).getAsString();
			requiredJsonData.add(subTotal);
			String tax = sales.get(Reports.TAX).getAsString();
			requiredJsonData.add(tax);
		} catch (Exception exc) {
			exc.printStackTrace();
			Assert.fail(exc.toString());
		}
	}

	private void generateJsonDetails() {
		try {
			DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(Reports.DATE_FORMAT);
			LocalDateTime tranDate = LocalDateTime.now();
			String transDate = tranDate.format(dateFormat);
			String transID = propertyFile.readPropertyFile(Configuration.DEVICE_ID, FilePath.PROPERTY_CONFIG_FILE)
					+ Constants.DELIMITER_HYPHEN
					+ transDate.replaceAll(Reports.REGEX_TRANS_DATE, Constants.EMPTY_STRING);
			jsonData.put(Reports.TRANS_ID, transID);
			jsonData.put(Reports.TRANS_DATE, transDate);
		} catch (Exception exc) {
			Assert.fail(exc.toString());
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
				if (reqString.equals(Reports.PAYMENTS)) {
					json.addProperty(Reports.TYPE, Reports.ACCOUNT.toUpperCase());
				}
			}

		} catch (Exception exc) {
			Assert.fail(exc.toString());
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
			Assert.fail(exc.toString());
		}
	}

	private void gmaJsonDataUpdate() {
		try {
			String gmaValue = jsonFunctions.readFileAsString(FilePath.JSON_GMA_ADD_VALUE);
			JsonObject gmaJson = jsonFunctions.convertStringToJson(gmaValue);
			gmaJson.addProperty(Reports.TRANS_ID, (String) jsonData.get(Reports.TRANS_ID));
			gmaJson.addProperty(Reports.TRANS_DATE, (String) jsonData.get(Reports.TRANS_DATE));
			String gmaData = gmaJson.get(Reports.DATA).getAsString();
			JsonObject gmaObj = jsonFunctions.convertStringToJson(gmaData);
			gmaObj.addProperty(Reports.TRANS_ID, (String) jsonData.get(Reports.TRANS_ID));
			gmaObj.addProperty(Reports.TRANS_DATE, (String) jsonData.get(Reports.TRANS_DATE));
			gmaObj.addProperty(Reports.PAYMENT_TYPE, Reports.ACCOUNT.toUpperCase());
			gmaJson.addProperty(Reports.DATA, gmaObj.toString());
			jsonData.put(Reports.GMA_JSON, gmaJson.toString());
			jsonData.put(Reports.GMA_TRANS, gmaObj);
		} catch (Exception exc) {
			Assert.fail(exc.toString());
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
