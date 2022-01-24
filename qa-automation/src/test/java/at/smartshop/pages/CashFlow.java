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

import at.framework.files.JsonFile;
import at.framework.files.PropertyFile;
import at.framework.reportsetup.ExtFactory;
import at.framework.ui.Foundation;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.keys.Reports;
import at.smartshop.utilities.WebService;

public class CashFlow {
	private Foundation foundation = new Foundation();
	private JsonFile jsonFunctions = new JsonFile();
	private PropertyFile propertyFile = new PropertyFile();
	private WebService webService = new WebService();

	public static final By LBL_REPORT_NAME = By.cssSelector("#report-container > div > div.col-12.comment-table-heading");
	private static final By REPORT_GRID_FIRST_ROW = By.cssSelector("#rptdt > tbody > tr:nth-child(1)");
	private static final By NO_DATA_AVAILABLE_IN_TABLE = By.xpath("//td[@class='dataTables_empty']");
	
	private List<String> tableHeaders = new ArrayList<>();
	private List<String> scancodeData = new LinkedList<>();
	private List<String> productNameData = new LinkedList<>();
	private List<String> requiredJsonData = new LinkedList<>();
	private List<Integer> requiredRecords = new LinkedList<>();
	private Map<String, Object> jsonData = new HashMap<>();
	private Map<Integer, Map<String, String>> reportsData = new LinkedHashMap<>();
	private Map<Integer, Map<String, String>> intialData = new LinkedHashMap<>();

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
	
	public void processAPI(String paymentType) {
		try {
			List<String> payType = Arrays.asList(paymentType.split(Constants.DELIMITER_HASH));
			for (int iter = 0; iter < payType.size(); iter++) {
				generateJsonDetails();
				salesJsonDataUpdate(payType.get(iter));
				webService.apiReportPostRequest(
						propertyFile.readPropertyFile(Configuration.TRANS_SALES, FilePath.PROPERTY_CONFIG_FILE),
						(String) jsonData.get(Reports.JSON));
			}
			getJsonSalesData();
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	private void getJsonSalesData() {
		try {
			JsonObject sales = (JsonObject) jsonData.get(Reports.SALES);
			String total = sales.get(Reports.TOTAL).getAsString();
			requiredJsonData.add(total);
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
			salesObj.addProperty(Reports.SERVICE, Reports.CS);
			jsonArrayDataUpdate(salesObj, Reports.ITEMS, salesHeaderID, paymentType);
			jsonArrayDataUpdate(salesObj, Reports.PAYMENTS, salesHeaderID, paymentType);
			saleJson.addProperty(Reports.SALE, salesObj.toString());
			jsonData.put(Reports.JSON, saleJson.toString());
			jsonData.put(Reports.SALES, salesObj);
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
	
//	public void processAPI() {
//		try {
//			generateJsonDetails();
//			salesJsonDataUpdate();
//			webService.apiReportPostRequest(
//					propertyFile.readPropertyFile(Configuration.TRANS_SALES, FilePath.PROPERTY_CONFIG_FILE),
//					(String) jsonData.get(Reports.JSON));
//			getJsonSalesData();
//			getJsonArrayData();
//			System.out.println("************************************ API processed ********************************");
//		} catch (Exception exc) {
//			Assert.fail(exc.toString());
//		}
//	}
//	
//	private void generateJsonDetails() {
//		try {
//			DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(Reports.DATE_FORMAT);
//			LocalDateTime tranDate = LocalDateTime.now();
//			String transDate = tranDate.format(dateFormat);
//			String transID = propertyFile.readPropertyFile(Configuration.DEVICE_ID, FilePath.PROPERTY_CONFIG_FILE)
//					+ Constants.DELIMITER_HYPHEN
//					+ transDate.replaceAll(Reports.REGEX_TRANS_DATE, Constants.EMPTY_STRING);
//			jsonData.put(Reports.TRANS_ID, transID);
//			jsonData.put(Reports.TRANS_DATE, transDate);
//			System.out.println("jsonData :" + jsonData);
//		} catch (Exception exc) {
//			Assert.fail(exc.toString());
//		}
//	}
//	private void salesJsonDataUpdate() {
//		try {
//			String salesHeaderID = UUID.randomUUID().toString().replace(Constants.DELIMITER_HYPHEN,
//					Constants.EMPTY_STRING);
//			String saleValue = jsonFunctions.readFileAsString(FilePath.JSON_SALES_CREATION);
//			JsonObject saleJson = jsonFunctions.convertStringToJson(saleValue);
//			saleJson.addProperty(Reports.TRANS_ID, (String) jsonData.get(Reports.TRANS_ID));
//			saleJson.addProperty(Reports.TRANS_DATE, (String) jsonData.get(Reports.TRANS_DATE));
//			String sale = saleJson.get(Reports.SALE).getAsString();
//			JsonObject salesObj = jsonFunctions.convertStringToJson(sale);
//			salesObj.addProperty(Reports.ID, salesHeaderID);
//			salesObj.addProperty(Reports.TRANS_ID, (String) jsonData.get(Reports.TRANS_ID));
//			salesObj.addProperty(Reports.TRANS_DATE, (String) jsonData.get(Reports.TRANS_DATE));
//			jsonArrayDataUpdate(salesObj, Reports.ITEMS, salesHeaderID);
//			jsonArrayDataUpdate(salesObj, Reports.PAYMENTS, salesHeaderID);
//			saleJson.addProperty(Reports.SALE, salesObj.toString());
//			jsonData.put(Reports.JSON, saleJson.toString());
//			jsonData.put(Reports.SALES, salesObj);
//			System.out.println("jsonData 2 :" + jsonData);
//		} catch (Exception exc) {
//			Assert.fail(exc.toString());
//		}
//	}
//
//	private void getJsonSalesData() {
//		try {
//			requiredJsonData.clear();
//			JsonObject sales = (JsonObject) jsonData.get(Reports.SALES);
//			String total = sales.get(Reports.TOTAL).getAsString();
//			requiredJsonData.add(total);
//			String totalTax = sales.get(Reports.TAX).getAsString();
//			requiredJsonData.add(totalTax);
//			System.out.println("requiredJsonData :" + requiredJsonData);
//		} catch (Exception exc) {
//			exc.printStackTrace();
//			Assert.fail(exc.toString());
//		}
//	}
//	
//	private void getJsonArrayData() {
//		try {
//			JsonArray items = ((JsonObject) jsonData.get(Reports.SALES)).get(Reports.ITEMS).getAsJsonArray();
//			System.out.println("items :" + items);
//			for (JsonElement item : items) {
//				JsonObject element = item.getAsJsonObject();
//				scancodeData.add(element.get(Reports.SCANCODE).getAsString());
//				productNameData.add(element.get(Reports.NAME).getAsString());
//				
//				System.out.println("scancodeData :" + scancodeData);
//				System.out.println("productNameData :" + productNameData);
//			}
//		} catch (Exception exc) {
//			Assert.fail(exc.toString());
//		}
//	}
//	
//	private void jsonArrayDataUpdate(JsonObject jsonObj, String reqString, String salesheader) {
//		try {
//			JsonArray items = jsonObj.get(reqString).getAsJsonArray();
//			for (JsonElement item : items) {
//				JsonObject json = item.getAsJsonObject();
//				json.addProperty(Reports.ID,
//						UUID.randomUUID().toString().replace(Constants.DELIMITER_HYPHEN, Constants.EMPTY_STRING));
//				json.addProperty(Reports.SALES_HEADER, salesheader);
//				json.addProperty(Reports.TRANS_ID, (String) jsonData.get(Reports.TRANS_ID));
//				json.addProperty(Reports.TRANS_DATE, (String) jsonData.get(Reports.TRANS_DATE));
//				System.out.println("json :" + json);
//			}
//		} catch (Exception exc) {
//			Assert.fail(exc.toString());
//		}
//	}

//	private static final By TBL_ALCOHOL_SOLD_DETAILS = By.id("alcoholSoldDetailsRpt");
//	public static final By LBL_REPORT_NAME = By
//			.cssSelector("#report-container > div > div.col-12.comment-table-heading");
//	private static final By TBL_ALCOHOL_SOLD_DETAILS_GRID = By.cssSelector("#alcoholSoldDetailsRpt > tbody");
//	private static final By REPORT_GRID_FIRST_ROW = By.cssSelector("#alcoholSoldDetailsRpt > tbody > tr:nth-child(1)");
//	private static final By NO_DATA_AVAILABLE_IN_TABLE = By.xpath("//td[@class='dataTables_empty']");
//	
//	public Map<Integer, Map<String, String>> getTblRecordsUI() {
//		try {
//			int recordCount = 0;
//			tableHeaders.clear();
//			WebElement tableReportsList = getDriver().findElement(TBL_ALCOHOL_SOLD_DETAILS_GRID);
//			WebElement tableReports = getDriver().findElement(TBL_ALCOHOL_SOLD_DETAILS);
//			List<WebElement> columnHeaders = tableReports.findElements(By.cssSelector("thead > tr > th"));
//			List<WebElement> rows = tableReportsList.findElements(By.tagName("tr"));
//			for (WebElement columnHeader : columnHeaders) {
//				tableHeaders.add(columnHeader.getText());
//			}
//			for (WebElement row : rows) {
//				Map<String, String> uiTblRowValues = new LinkedHashMap<>();
//				for (int columnCount = 1; columnCount < tableHeaders.size() + 1; columnCount++) {
//					WebElement column = row.findElement(By.cssSelector("td:nth-child(" + columnCount + ")"));
//					uiTblRowValues.put(tableHeaders.get(columnCount - 1), column.getText());
//				}
//				reportsData.put(recordCount, uiTblRowValues);
//				recordCount++;
//			}
//		} catch (Exception exc) {
//			Assert.fail(exc.toString());
//		}
//		return reportsData;
//	}
	
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

	public List<String> getRequiredJsonData() {
		return requiredJsonData;
	}

	public List<String> getTableHeaders() {
		return tableHeaders;
	}

	public List<String> getProductNameData() {
		return productNameData;
	}
}
