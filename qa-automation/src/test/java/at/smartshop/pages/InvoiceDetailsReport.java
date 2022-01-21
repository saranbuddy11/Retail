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
import at.framework.reportsetup.ExtFactory;
import at.framework.ui.Foundation;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.keys.Reports;
import at.smartshop.utilities.WebService;

public class InvoiceDetailsReport extends Factory {

	private JsonFile jsonFunctions = new JsonFile();
	private PropertyFile propertyFile = new PropertyFile();
	private WebService webService = new WebService();
	private Foundation foundation = new Foundation();

	private static final By TBL_INVOICE_DETAILS = By.id("rptdt");
	private static final By LBL_REPORT_NAME = By.cssSelector("#report-container > div > div.col-12.comment-table-heading");
	private static final By TBL_INVOICE_DETAILS_GRID = By.cssSelector("#rptdt > tbody");
	private static final By REPORT_GRID_FIRST_ROW = By.cssSelector("#rptdt > tbody > tr:nth-child(1)");
	private static final By NO_DATA_AVAILABLE_IN_TABLE = By.xpath("//td[@class='dataTables_empty']");

	private List<String> tableHeaders = new ArrayList<>();
	private List<String> scancodeData = new LinkedList<>();
	private List<String> productNameData = new LinkedList<>();
	private List<String> priceData = new LinkedList<>();
	private List<String> taxData = new LinkedList<>();
	private List<String> unitMeasureData = new LinkedList<>();
	private List<String> quantityData = new LinkedList<>();
	private List<String> taxrateData = new LinkedList<>();
	private List<String> requiredJsonData = new LinkedList<>();
	private List<Integer> requiredRecords = new LinkedList<>();
	private Map<String, Object> jsonData = new HashMap<>();
	private Map<Integer, Map<String, String>> reportsData = new LinkedHashMap<>();
	private Map<Integer, Map<String, String>> intialData = new LinkedHashMap<>();

	public Map<Integer, Map<String, String>> getTblRecordsUI() {
		try {
			int recordCount = 0;
			tableHeaders.clear();
			WebElement tableReportsList = getDriver().findElement(TBL_INVOICE_DETAILS_GRID);
			WebElement tableReports = getDriver().findElement(TBL_INVOICE_DETAILS);
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

	public void getRequiredRecord(String transID, List<String> scancodes) {
		try {
			requiredRecords.clear();
			for (int iter = 0; iter < scancodes.size(); iter++) {
				for (int val = 0; val < intialData.size(); val++) {
					if (intialData.get(val).get(tableHeaders.get(0)).equals(transID)
							&& intialData.get(val).get(tableHeaders.get(2)).equals(scancodes.get(iter))) {
						requiredRecords.add(val);
						break;
					}
				}
				if (requiredRecords.size() == scancodes.size()) {
					break;
				}
			}
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
	
	public void verifyReportName(String reportName) {
		try {
			foundation.waitforElement(LBL_REPORT_NAME, Constants.EXTRA_LONG_TIME);
			String reportTitle = foundation.getText(LBL_REPORT_NAME);
			Assert.assertTrue(reportTitle.contains(reportName));
		} catch (Exception exc) {
			Assert.fail(exc.toString());
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
			Assert.fail(exc.toString());
		}
	}

	public void updateData(String columnName, List<String> values) {
		try {
			for (int iter = 0; iter < requiredRecords.size(); iter++) {
				String value = String.valueOf(values.get(iter));
				intialData.get(requiredRecords.get(iter)).put(columnName, value);
			}
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	public void updateData(String columnName, String values) {
		try {
			for (int iter = 0; iter < requiredRecords.size(); iter++) {
				intialData.get(requiredRecords.get(iter)).put(columnName, values);
			}
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	public void updateVAT() {
		try {
			for (int iter = 0; iter < requiredRecords.size(); iter++) {
				String taxrate = taxrateData.get(iter);
				double updatedVAT = Double.parseDouble(taxrate) * 100;
				updatedVAT = Math.round(updatedVAT * 100.0) / 100.0;
				intialData.get(requiredRecords.get(iter)).put(tableHeaders.get(8), String.valueOf(updatedVAT));
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

	public void processAPI(String value) {
		try {
			generateJsonDetails(value);
			salesJsonDataUpdate();
			webService.apiReportPostRequest(
					propertyFile.readPropertyFile(Configuration.TRANS_SALES, FilePath.PROPERTY_CONFIG_FILE),
					(String) jsonData.get(Reports.JSON));
			getJsonArrayData();
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
	
	private void generateJsonDetails(String reportFormat) {
		try {
			DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(Reports.DATE_FORMAT);
			DateTimeFormatter reqFormat = DateTimeFormatter.ofPattern(reportFormat);
			LocalDateTime tranDate = LocalDateTime.now();
			String transDate = tranDate.format(dateFormat);
			String reportDate = tranDate.format(reqFormat);
			String transID = propertyFile.readPropertyFile(Configuration.DEVICE_ID,
					FilePath.PROPERTY_CONFIG_FILE) + Constants.DELIMITER_HYPHEN
					+ transDate.replaceAll(Reports.REGEX_TRANS_DATE, Constants.EMPTY_STRING);
			jsonData.put(Reports.TRANS_ID, transID);
			jsonData.put(Reports.TRANS_DATE, transDate);
			jsonData.put(Reports.TRANS_DATE_TIME, reportDate);
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	private void getJsonArrayData() {
		try {
			JsonArray items = ((JsonObject) jsonData.get(Reports.SALES)).get(Reports.ITEMS).getAsJsonArray();
			for (JsonElement item : items) {
				JsonObject element = item.getAsJsonObject();
				scancodeData.add(element.get(Reports.SCANCODE).getAsString());
				productNameData.add(element.get(Reports.NAME).getAsString());
				priceData.add(element.get(Reports.PRICE).getAsString());
				taxData.add(element.get(Reports.TAX).getAsString());
				unitMeasureData.add(element.get(Reports.UM).getAsString());
				quantityData.add(element.get(Reports.QUANTITY).getAsString());
				taxrateData.add(element.get(Reports.TAXRATE).getAsString());
			}
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

	public Map<String, Object> getJsonData() {
		return jsonData;
	}

	public Map<Integer, Map<String, String>> getIntialData() {
		return intialData;
	}

	public Map<Integer, Map<String, String>> getReportsData() {
		return reportsData;
	}

	public List<String> getScancodeData() {
		return scancodeData;
	}
	
	public List<String> getUnitMeasureData() {
		return unitMeasureData;
	}
	
	public List<String> getQuantityData() {
		return quantityData;
	}

	public List<String> getTaxData() {
		return taxData;
	}
	
	public List<String> getRequiredJsonData() {
		return requiredJsonData;
	}

	public List<String> getTableHeaders() {
		return tableHeaders;
	}

	public List<String> getPriceData() {
		return priceData;
	}
	
	public List<String> getTaxRateData() {
		return taxrateData;
	}

	public List<String> getProductNameData() {
		return productNameData;
	}

}
