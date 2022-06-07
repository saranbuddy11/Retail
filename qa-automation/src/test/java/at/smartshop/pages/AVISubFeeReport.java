package at.smartshop.pages;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
import at.framework.ui.TextBox;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.keys.Reports;
import at.smartshop.tests.TestInfra;
import at.smartshop.utilities.WebService;

public class AVISubFeeReport extends Factory {

	private JsonFile jsonFunctions = new JsonFile();
	private PropertyFile propertyFile = new PropertyFile();
	private WebService webService = new WebService();
	private Foundation foundation = new Foundation();
	private ReportList reportList = new ReportList();
	private TextBox textBox = new TextBox();

	private static final By TBL_AVI_SUB_FEE = By.id("subFeeGrid");
	public final By LBL_REPORT_NAME = By.cssSelector("#report-container > div > div:nth-child(1) > div");
	private static final By TBL_AVI_SUB_FEE_GRID = By.cssSelector("#subFeeGrid > tbody");
	private static final By BTN_PREVIOUS_MONTH = By.cssSelector("th.prev");
	private static final By REPORT_GRID_FIRST_ROW = By.cssSelector("#subFeeGrid  > tbody > tr:nth-child(1)");
	private static final By NO_DATA_AVAILABLE_IN_TABLE = By.xpath("//td[@class='dataTables_empty']");
	public final By SEARCH_RESULT = By.xpath("//input[@id='filterType']");

	private List<String> tableHeaders = new ArrayList<>();
	private List<String> requiredJsonData = new LinkedList<>();
	private Map<String, Object> jsonData = new HashMap<>();
	private int recordCount;
	private Map<Integer, Map<String, String>> reportsData = new LinkedHashMap<>();
	private Map<Integer, Map<String, String>> intialData = new LinkedHashMap<>();

	public Map<Integer, Map<String, String>> getTblRecordsUI() {
		try {
			int recordCount = 0;
			tableHeaders.clear();
			foundation.waitforElement(TBL_AVI_SUB_FEE_GRID, Constants.SHORT_TIME);
			WebElement tableReportsList = getDriver().findElement(TBL_AVI_SUB_FEE_GRID);
			WebElement tableReports = getDriver().findElement(TBL_AVI_SUB_FEE);
			List<WebElement> columnHeaders = tableReports.findElements(By.cssSelector("thead > tr > th"));
			List<WebElement> rows = tableReportsList.findElements(By.tagName("tr"));
			for (WebElement columnHeader : columnHeaders) {
				tableHeaders.add(columnHeader.getText());
			}
			reportsData.clear();
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

	public void getRequiredRecord(String locationID, String deviceID) {
		try {
			boolean flag = false;
			for (int rowCount = 0; rowCount < reportsData.size(); rowCount++) {
				if (reportsData.get(rowCount).get(tableHeaders.get(0)).equals(locationID)
						&& reportsData.get(rowCount).get(tableHeaders.get(3)).equals(deviceID)) {
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

	public void updateData(String columnName, String value) {
		try {
			intialData.get(recordCount).put(columnName, value);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void selectToday() {
		try {
			foundation.click(ReportList.DPD_DATE);
			foundation.click(ReportList.TODAYS_DATE);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void calculateTotalBillable() {
		double totalBillable;
		try {
			String initialData = reportsData.get(recordCount).get(tableHeaders.get(6))
					.replaceAll(Reports.REPLACE_DOLLOR, Constants.EMPTY_STRING);
			if (Double.parseDouble(
					requiredJsonData.get(0).replaceAll(Reports.REPLACE_DOLLOR, Constants.EMPTY_STRING)) > 75) {
				totalBillable = Double.parseDouble(initialData) * 0.05;
				totalBillable = Math.round(totalBillable * 100.0) / 100.0;
				if (totalBillable > 100) {
					totalBillable = 100;
				}
			} else {
				totalBillable = 0;
			}
			requiredJsonData.add(Constants.DOLLAR_SYMBOL +String.valueOf(totalBillable));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void getPriorMonthData(String requiredDate, String orgName, String locationID, String deviceID) {
		try {
			foundation.click(ReportList.DPD_DATE);
			foundation.click(BTN_PREVIOUS_MONTH);
			selectLastDate(requiredDate);
			reportList.selectOrg(orgName);
			foundation.click(ReportList.BTN_RUN_REPORT);
			textBox.enterText(SEARCH_RESULT,deviceID);
			getTblRecordsUI();
			getRequiredRecord(locationID, deviceID.toUpperCase());
			requiredJsonData.add(reportsData.get(recordCount).get(tableHeaders.get(6)));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void selectLastDate(String requiredDate) {
		try {
			List<String> reqDate = Arrays.asList(requiredDate.split(Constants.DELIMITER_HASH));
			WebElement lastMonthDate = getDriver().findElement(
					By.xpath("//table[@class = 'table-condensed']/tbody/tr/td[text()='" + reqDate.get(0) + "']"));
			if (lastMonthDate.isDisplayed()) {
				foundation.click(By.xpath("//table[@class = 'table-condensed']/tbody/tr/td[text()='" + reqDate.get(2)
						+ "'][not(contains(@class , 'off'))]"));
			} else {
				foundation.click(By.xpath("//table[@class = 'table-condensed']/tbody/tr/td[text()='" + reqDate.get(1)
						+ "'][not(contains(@class , 'off'))]"));
			}
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
			int count = intialData.size();
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

	public void processAPI(String deviceId) {
		try {
			generateJsonDetails(deviceId);
			salesJsonDataUpdate();
			webService.apiReportPostRequest(
					propertyFile.readPropertyFile(Configuration.TRANS_SALES, FilePath.PROPERTY_CONFIG_FILE),
					(String) jsonData.get(Reports.JSON));
			getJsonSalesData();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	private void getJsonSalesData() {
		try {
			JsonObject sales = (JsonObject) jsonData.get(Reports.SALES);
			String total = sales.get(Reports.TOTAL).getAsString();
			requiredJsonData.add(total);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	private void generateJsonDetails(String deviceId) {
		try {
			DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(Reports.DATE_FORMAT);
			LocalDateTime tranDate = LocalDateTime.now();
			String transDate = tranDate.format(dateFormat);
			String transID = deviceId + Constants.DELIMITER_HYPHEN
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

	public List<String> getRequiredJsonData() {
		return requiredJsonData;
	}

	public List<String> getTableHeaders() {
		return tableHeaders;
	}

}
