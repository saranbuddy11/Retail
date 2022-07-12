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

public class ICEReport extends Factory {

	private Foundation foundation = new Foundation();
	private JsonFile jsonFunctions = new JsonFile();
	private PropertyFile propertyFile = new PropertyFile();
	private WebService webService = new WebService();
	private LocationSummary locationSummary = new LocationSummary();

	private static final By TBL_ICE = By.id("rptdt");
	public static final By LBL_REPORT_NAME = By
			.cssSelector("#report-container > div > div.col-12.comment-table-heading");
	private static final By TBL_ICE_GRID = By.cssSelector("#rptdt > tbody");
	private static final By REPORT_GRID_FIRST_ROW = By.cssSelector("#rptdt > tbody > tr:nth-child(1)");
	private static final By NO_DATA_AVAILABLE_IN_TABLE = By.xpath("//td[@class='dataTables_empty']");
	public static final By TXT_SEARCH = By.cssSelector("input[aria-controls='rptdt']");

	private List<String> tableHeaders = new ArrayList<>();
	private List<String> admData = new ArrayList<>();
	private Map<String, Object> jsonData = new HashMap<>();
	private Map<Integer, Map<String, String>> productsData = new LinkedHashMap<>();
	private Map<Integer, Map<String, String>> reportsData = new LinkedHashMap<>();
	private Map<Integer, Map<String, String>> intialData = new LinkedHashMap<>();

	public Map<Integer, Map<String, String>> getTblRecordsUI() {
		try {
			int recordCount = 0;
			tableHeaders.clear();
			WebElement tableReportsList = getDriver().findElement(TBL_ICE_GRID);
			WebElement tableReports = getDriver().findElement(TBL_ICE);
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

	private int getRequiredRecord(String scancode) {
		int rowCount = 0;
		try {
			int recordCount = 0;
			for (int val = 0; val < intialData.size(); val++) {
				if (intialData.get(val).get(tableHeaders.get(2)).equals(scancode)) {
					rowCount = recordCount;
					break;
				}
				recordCount++;
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		return rowCount;
	}

	public void verifyReportName(String reportName) {
		try {
			foundation.waitforElement(LBL_REPORT_NAME, Constants.EXTRA_LONG_TIME);
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
	
	public void updateFills(String scancode, String columnNames, String reqCount) {
		int updatedInInv;
		try {
			int rowCount = getRequiredRecord(scancode);
			List<String> columnName = Arrays.asList(columnNames.split(Constants.DELIMITER_HASH));
			int recordCount = 0;
			for (recordCount = 0; recordCount < productsData.size(); recordCount++) {
				if ((productsData.get(recordCount).get(columnName.get(1))).equals(scancode)) {
					break;
				}
			}
			String intialInInv = productsData.get(recordCount).get(columnName.get(8));
			if(Integer.parseInt(intialInInv) < 0) {
				updatedInInv = (Integer.parseInt(intialInInv) * -1) + Integer.parseInt(reqCount);
			} else {
				updatedInInv = Integer.parseInt(reqCount) - Integer.parseInt(intialInInv);
			}
			String intialValue = intialData.get(rowCount).get(tableHeaders.get(5));
			int updatedValue = updatedInInv + Integer.parseInt(intialValue);
			intialData.get(rowCount).put(tableHeaders.get(5), String.valueOf(updatedValue));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
	
	public void updateWaste(String scancode, String columnNames, String reqCount) {
		int updatedInInv;
		try {
			
			int rowCount = getRequiredRecord(scancode);
			productsData = locationSummary.getProductsRecords(scancode);
			List<String> columnName = Arrays.asList(columnNames.split(Constants.DELIMITER_HASH));
			int recordCount = 0;
			for (recordCount = 0; recordCount < productsData.size(); recordCount++) {
				if ((productsData.get(recordCount).get(columnName.get(1))).equals(scancode)) {
					break;
				}
			}
			String intialInInv = productsData.get(recordCount).get(columnName.get(8));
			updatedInInv = Integer.parseInt(intialInInv);
			String intialValue = intialData.get(rowCount).get(tableHeaders.get(6));
			int updatedValue = updatedInInv + Integer.parseInt(intialValue);
			intialData.get(rowCount).put(tableHeaders.get(6), String.valueOf(updatedValue));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
	
	public void updateSold(String value) {
		try {
			int rowCount = getRequiredRecord(value);
			String intialSold = intialData.get(rowCount).get(tableHeaders.get(7));
			int updatedSold = Integer.parseInt(intialSold) + 1;
			intialData.get(rowCount).put(tableHeaders.get(7), String.valueOf(updatedSold));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
	
	public void updateClosingLevel(String value, String columnNames) {
		try {
			int rowCount = getRequiredRecord(value);
			List<String> columnName = Arrays.asList(columnNames.split(Constants.DELIMITER_HASH));
			productsData = locationSummary.getProductsRecords(value);
			int recordCount = 0;
			for (recordCount = 0; recordCount < productsData.size(); recordCount++) {
				if ((productsData.get(recordCount).get(columnName.get(1))).equals(value)) {
					break;
				}
			}
			int inventoryValue = Integer.parseInt(productsData.get(recordCount).get(columnName.get(8))) - 1;
			intialData.get(rowCount).put(tableHeaders.get(8), String.valueOf(inventoryValue));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}


	public void updateData(String value, String columnNames) {
		try {
			int rowCount = getRequiredRecord(value);
			productsData = locationSummary.getProductsRecords(value);
			System.out.println("productsData :" + productsData);
			int recordCount = 0;
			List<String> columnName = Arrays.asList(columnNames.split(Constants.DELIMITER_HASH));
			for (recordCount = 0; recordCount < productsData.size(); recordCount++) {
				if ((productsData.get(recordCount).get(columnName.get(1))).equals(value)) {
					break;
				}
			}
			intialData.get(rowCount).put(tableHeaders.get(0),
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));
//			intialData.get(rowCount).put(tableHeaders.get(1), productsData.get(recordCount).get(columnName.get(5)));
			intialData.get(rowCount).put(tableHeaders.get(2), productsData.get(recordCount).get(columnName.get(1)));
			intialData.get(rowCount).put(tableHeaders.get(3), productsData.get(recordCount).get(columnName.get(0)));
			intialData.get(rowCount).put(tableHeaders.get(4), productsData.get(recordCount).get(columnName.get(3)));
			intialData.get(rowCount).put(tableHeaders.get(9), admData.get(2));
			intialData.get(rowCount).put(tableHeaders.get(10), admData.get(0));
			intialData.get(rowCount).put(tableHeaders.get(11), admData.get(1));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void verifyReportHeaders(String columnNames) {
		try {
			List<String> columnName = Arrays.asList(columnNames.split(Constants.DELIMITER_HYPHEN));
			for (int iter = 0; iter < tableHeaders.size(); iter++) {
				CustomisedAssert.assertTrue(tableHeaders.get(iter).equals(columnName.get(iter)));
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void verifyReportData(String scancode) {
		try {
			int recordCount = 0;
			for (recordCount = 0; recordCount < productsData.size(); recordCount++) {
				if ((intialData.get(recordCount).get(tableHeaders.get(2))).equals(scancode)) {
					break;
				}
			}
			System.out.println(reportsData.get(recordCount));
			System.out.println(intialData.get(recordCount));
			for (int iter = 0; iter < tableHeaders.size(); iter++) {
				CustomisedAssert.assertTrue(reportsData.get(recordCount).get(tableHeaders.get(iter))
						.contains(intialData.get(recordCount).get(tableHeaders.get(iter))));
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
	
	public void processAPI() {
		try {
			generateJsonDetails();
			salesJsonDataUpdate();
			webService.apiReportPostRequest(
					propertyFile.readPropertyFile(Configuration.TRANS_SALES, FilePath.PROPERTY_CONFIG_FILE),
					(String) jsonData.get(Reports.JSON));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
	
	private void generateJsonDetails() {
		try {
			DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(Reports.DATE_FORMAT);
			LocalDateTime tranDate = LocalDateTime.now();
			String transDate = tranDate.format(dateFormat);
			String transID = propertyFile.readPropertyFile(Configuration.DEVICE_ID,
					FilePath.PROPERTY_CONFIG_FILE) + Constants.DELIMITER_HYPHEN
					+ transDate.replaceAll(Reports.REGEX_TRANS_DATE, Constants.EMPTY_STRING);
			jsonData.put(Reports.TRANS_ID, transID);
			jsonData.put(Reports.TRANS_DATE, transDate);
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

	public List<String> getADMData() {
		return admData;
	}

}
