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
import at.smartshop.utilities.WebService;

public class IntegrationPaymentReport extends Factory {

	private JsonFile jsonFunctions = new JsonFile();
	private PropertyFile propertyFile = new PropertyFile();
	private WebService webService = new WebService();
	private Foundation foundation = new Foundation();

	private static final By TBL_INTEGRATION_PAYMENTS = By.id("rptdt");
	private static final By LBL_REPORT_NAME = By
			.cssSelector("#report-container > div > div.col-12.comment-table-heading");
	private static final By TBL_INTEGRATION_PAYMENTS_GRID = By.cssSelector("#rptdt > tbody");

	private List<String> tableHeaders = new ArrayList<>();
	private List<String> amountData = new LinkedList<>();
	private List<Integer> requiredRecords = new LinkedList<>();
	private Map<String, Object> jsonData = new HashMap<>();
	private Map<Integer, Map<String, String>> reportsData = new LinkedHashMap<>();
	private Map<Integer, Map<String, String>> intialData = new LinkedHashMap<>();

	public Map<Integer, Map<String, String>> getTblRecordsUI() {
		try {
			int recordCount = 0;
			tableHeaders.clear();
			WebElement tableReportsList = getDriver().findElement(TBL_INTEGRATION_PAYMENTS_GRID);
			WebElement tableReports = getDriver().findElement(TBL_INTEGRATION_PAYMENTS);
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

	public void getRequiredRecord(String locationName, String paymentType) {
		try {
			List<String> payType = Arrays.asList(paymentType.split(Constants.DELIMITER_TILD));
			requiredRecords.clear();
			for (int iter = 0; iter < payType.size(); iter++) {
				for (int val = 0; val < intialData.size(); val++) {
					if (intialData.get(val).get(tableHeaders.get(0)).equals(locationName)
							&& intialData.get(val).get(tableHeaders.get(2)).equals(payType.get(iter))) {
						requiredRecords.add(val);
						break;
					}
				}
				if (requiredRecords.size() == payType.size()) {
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

	public void updateValue(String columnName, String values) {
		try {
			List<String> value = Arrays.asList(values.split(Constants.DELIMITER_TILD));
			for (int iter = 0; iter < requiredRecords.size(); iter++) {
				String data = String.valueOf(value.get(iter));
				intialData.get(requiredRecords.get(iter)).put(columnName, data);
			}
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
	
	public void updateData(String columnName, List<String> values) {
		try {
			for (int iter = 0; iter < requiredRecords.size(); iter++) {
				String data = String.valueOf(values.get(iter));
				intialData.get(requiredRecords.get(iter)).put(columnName, data);
			}
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
	
	public void calculateAmount(List<String> amount) {
		try {
			for (int iter = 0; iter < requiredRecords.size(); iter++) {
				String initialAmount = intialData.get(requiredRecords.get(iter)).get(tableHeaders.get(3));
				String data = String.valueOf(amount.get(iter));
				double updatedAmount = Double.parseDouble(initialAmount) + Double.parseDouble(data);
				updatedAmount = Math.round(updatedAmount * 100.0) / 100.0;
				intialData.get(requiredRecords.get(iter)).put(tableHeaders.get(3), String.valueOf(updatedAmount));
			}
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	public void updateData(String columnName, String value) {
		try {
			for (int iter = 0; iter < requiredRecords.size(); iter++) {
				intialData.get(requiredRecords.get(iter)).put(columnName, value);
			}
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

	public void processAPI(String paymentType) {
		try {
			List<String> payType = Arrays.asList(paymentType.split(Constants.DELIMITER_TILD));
			for (int iter = 0; iter < payType.size(); iter++) {
				generateJsonDetails();
				salesJsonDataUpdate(payType.get(iter));
				webService.apiReportPostRequest(
						propertyFile.readPropertyFile(Configuration.TRANS_SALES, FilePath.PROPERTY_CONFIG_FILE),
						(String) jsonData.get(Reports.JSON));
			}
		} catch (Exception exc) {
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

	private void jsonArrayDataUpdate(JsonObject jsonObj, String reqString, String salesheader, String paymentType) {
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
					json.addProperty(Reports.TYPE, paymentType);
					amountData.add(json.get(Reports.AMOUNT).getAsString());
				}
			}
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	private void salesJsonDataUpdate(String paymentType) {
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

	public List<String> getTableHeaders() {
		return tableHeaders;
	}
	
	public List<String> getAmountData() {
		return amountData;
	}

}
