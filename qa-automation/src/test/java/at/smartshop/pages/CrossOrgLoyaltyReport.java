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
import at.smartshop.tests.TestInfra;
import at.smartshop.utilities.WebService;

public class CrossOrgLoyaltyReport extends Factory {

	private JsonFile jsonFunctions = new JsonFile();
	private PropertyFile propertyFile = new PropertyFile();
	private WebService webService = new WebService();
	private Foundation foundation = new Foundation();

	private static final By TBL_CROSS_ORG_LOYALTY = By.id("rptdt");
	public static final By LBL_REPORT_NAME = By
			.cssSelector("#report-container > div > div.col-12.comment-table-heading");
	private static final By TBL_CROSS_ORG_LOYALTY_GRID = By.cssSelector("#rptdt > tbody");
	private static final By REPORT_GRID_FIRST_ROW = By.cssSelector("#rptdt > tbody > tr:nth-child(1)");
	private static final By NO_DATA_AVAILABLE_IN_TABLE = By.xpath("//td[@class='dataTables_empty']");
	public final By SEARCH_RESULT = By.xpath("//input[@aria-controls='rptdt']");
	public static final By DATA_EXISTING_START_DATE_STAGING = By.cssSelector(
			"body > div.daterangepicker.ltr.show-ranges.opensright.show-calendar  > div.drp-calendar.right > div.calendar-table > table > tbody > tr:nth-child(2) > td:nth-child(5)");
	public static final By DATA_EXISTING_END_DATE_STAGING = By.cssSelector(
			"body > div.daterangepicker.ltr.show-ranges.opensright.show-calendar  > div.drp-calendar.right > div.calendar-table > table > tbody > tr:nth-child(4) > td:nth-child(6)");

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
			WebElement tableReportsList = getDriver().findElement(TBL_CROSS_ORG_LOYALTY_GRID);
			WebElement tableReports = getDriver().findElement(TBL_CROSS_ORG_LOYALTY);
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

	public void getRequiredRecord(String locationName) {
		boolean flag = false;
		try {
			for (int rowCount = 0; rowCount < intialData.size(); rowCount++) {
				if (intialData.get(rowCount).get(tableHeaders.get(1)).equals(locationName)) {
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

	public void updateData(String columnName, String values) {
		try {
			intialData.get(recordCount).put(columnName, values);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void updatePoints(String columnName, String value) {
		try {
			String intialPoints = intialData.get(recordCount).get(columnName);
			int updatedPoint = Integer.parseInt(intialPoints) + Integer.parseInt(value);
			intialData.get(recordCount).put(columnName, String.valueOf(updatedPoint));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void updateMoney(String value) {
		try {
			String intialMoney = intialData.get(recordCount).get(tableHeaders.get(6)).replaceAll(Reports.REPLACE_DOLLOR,
					Constants.EMPTY_STRING);
			double updatedMoney = Double.parseDouble(intialMoney) + Double.parseDouble(requiredJsonData.get(0));
			intialData.get(recordCount).put(tableHeaders.get(6), String.valueOf(updatedMoney));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void updatePointsPerDollor() {
		try {
			String pointsRedeemed = intialData.get(recordCount).get(tableHeaders.get(5));
			String moneyRedeemed = intialData.get(recordCount).get(tableHeaders.get(6));
			double pointsPerDollor = Double.parseDouble(pointsRedeemed) / Double.parseDouble(moneyRedeemed);
			int updatedPoint = (int) pointsPerDollor;
			intialData.get(recordCount).put(tableHeaders.get(7), String.valueOf(updatedPoint));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void verifyReportHeaders(String columnNames) {
		try {
			List<String> columnName = Arrays.asList(columnNames.split(Constants.DELIMITER_HASH));
			for (int iter = 0; iter < tableHeaders.size(); iter++) {
				Assert.assertTrue(tableHeaders.get(iter).equals(columnName.get(iter)));
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
					Assert.assertTrue(reportsData.get(counter).get(tableHeaders.get(iter))
							.contains(intialData.get(counter).get(tableHeaders.get(iter))));
				}
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void processAPI() {
		try {
			generateJsonDetails();
			salesJsonDataUpdate();
			gmaJsonDataUpdate();
			mkaAccountJsonDataUpdate();
			webService.apiReportPostRequest(
					propertyFile.readPropertyFile(Configuration.TRANS_SALES, FilePath.PROPERTY_CONFIG_FILE),
					(String) jsonData.get(Reports.JSON));
			webService.apiReportPostRequest(
					propertyFile.readPropertyFile(Configuration.TRANS_GMA, FilePath.PROPERTY_CONFIG_FILE),
					(String) jsonData.get(Reports.GMA_JSON));
			getJsonSalesData();
			webService.apiPutRequest(
					propertyFile.readPropertyFile(Configuration.TRANS_MKA, FilePath.PROPERTY_CONFIG_FILE),
					requiredJsonData.get(1), (String) jsonData.get(Reports.MKA_JSON));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	private void getJsonSalesData() {
		try {
			JsonObject sales = (JsonObject) jsonData.get(Reports.SALES);
			String total = sales.get(Reports.TOTAL).getAsString();
			requiredJsonData.add(total);
			JsonObject gmatrans = (JsonObject) jsonData.get(Reports.GMA_TRANS);
			String guid = gmatrans.get(Reports.GUID).getAsString();
			requiredJsonData.add(guid);
			JsonObject mkatrans = (JsonObject) jsonData.get(Reports.MKA_TRANS);
			String points = mkatrans.get(Reports.POINTS).getAsString();
			requiredJsonData.add(points);
		} catch (Exception exc) {
			exc.printStackTrace();
			TestInfra.failWithScreenShot(exc.toString());
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
				if (reqString.equals(Reports.PAYMENTS)) {
					json.addProperty(Reports.TYPE, Reports.ACCOUNT.toUpperCase());
				}
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
			gmaObj.addProperty(Reports.ACTION_TYPE, Reports.REDEMTION.toUpperCase());
			gmaObj.addProperty(Reports.PAYMENT_TYPE, Reports.ACCOUNT.toUpperCase());
			gmaJson.addProperty(Reports.DATA, gmaObj.toString());
			jsonData.put(Reports.GMA_JSON, gmaJson.toString());
			jsonData.put(Reports.GMA_TRANS, gmaObj);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public Map<String, Object> mkaAccountJsonDataUpdate() throws Exception {
		String jsonMKAAccount = jsonFunctions.readFileAsString(FilePath.JSON_MKA);
		JsonObject jsonMKAAccountValue = jsonFunctions.convertStringToJson(jsonMKAAccount);
		jsonMKAAccountValue.addProperty(Reports.TRANS_DATE, (String) jsonData.get(Reports.TRANS_DATE));
		jsonData.put(Reports.MKA_JSON, jsonMKAAccountValue.toString());
		jsonData.put(Reports.MKA_TRANS, jsonMKAAccountValue);
		return jsonData;
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
