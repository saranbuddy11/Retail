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

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.google.gson.JsonObject;

import at.framework.browser.Factory;
import at.framework.files.JsonFile;
import at.framework.files.PropertyFile;
import at.framework.generic.CustomisedAssert;
import at.framework.ui.Foundation;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.keys.Reports;
import at.smartshop.tests.TestInfra;
import at.smartshop.utilities.WebService;

public class BadScanReport extends Factory {

	JsonFile jsonFile = new JsonFile();
	PropertyFile propertyFile = new PropertyFile();
	WebService webService = new WebService();
	Foundation foundation = new Foundation();

	private By tblBadScan = By.id("rptdt");
	private By lblReportName = By.cssSelector("#report-container > div > div.col-12.comment-table-heading");
	private By tblBadScanGrid = By.cssSelector("#rptdt > tbody");

	private List<String> tableHeaders = new ArrayList<>();
	private List<String> scancodeData = new LinkedList<>();
	private List<Integer> requiredData = new LinkedList<>();
	private List<String> requiredJsonData = new LinkedList<>();
	private Map<String, Object> jsonData = new HashMap<>();
	private Map<Integer, Map<String, String>> reportsData = new LinkedHashMap<>();
	private Map<Integer, Map<String, String>> intialData = new LinkedHashMap<>();

	public Map<Integer, Map<String, String>> getTblRecordsUI() {
		try {
			int recordCount = 0;
			tableHeaders.clear();
			WebElement tableReportsList = getDriver().findElement(tblBadScanGrid);
			WebElement tableReports = getDriver().findElement(tblBadScan);
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

	public void getRequiredRecord(String transDate) {
		try {
			requiredData.clear();
			for (int val = 0; val < intialData.size(); val++) {
				if (intialData.get(val).get(tableHeaders.get(1)).equals(transDate)) {
					requiredData.add(val);
					break;
				}
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
	
	public void verifyReportName(String reportName) {
		try {
			String reportTitle = foundation.getText(lblReportName);
			CustomisedAssert.assertTrue(reportTitle.contains(reportName));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void updateData(String columnName, String values) {
		try {
			for (int iter = 0; iter < requiredData.size(); iter++) {
				intialData.get(requiredData.get(iter)).put(columnName, values);
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

	public void processAPI(String value) {
		try {
			badScanJsonDataUpdate(value);
			webService.apiReportPostRequest(
					propertyFile.readPropertyFile(Configuration.TRANS_BAD_SCAN, FilePath.PROPERTY_CONFIG_FILE),
					(String) jsonData.get(Reports.JSON));
			getJsonSalesData();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	private void getJsonSalesData() {
		try {
			JsonObject badScan = (JsonObject) jsonData.get(Reports.BAD_SCAN);
			String scancode = badScan.get(Reports.SCANCODE).getAsString();
			requiredJsonData.add(scancode);
			String locationID = badScan.get(Reports.LOCATION).getAsString();
			requiredJsonData.add(locationID);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	private Map<String, Object> badScanJsonDataUpdate(String reportFormat) {
		try {
			DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(Reports.DATE_FORMAT);
			DateTimeFormatter reqFormat = DateTimeFormatter.ofPattern(reportFormat);
			LocalDateTime tranDate = LocalDateTime.now();
			String transDate = tranDate.format(dateFormat);
			String reportDate = tranDate.format(reqFormat);
			String transID = propertyFile.readPropertyFile(Configuration.DEVICE_ID,
					FilePath.PROPERTY_CONFIG_FILE) + Constants.DELIMITER_HYPHEN
					+ transDate.replaceAll(Reports.REGEX_TRANS_DATE, Constants.EMPTY_STRING);
			String badScanValue = jsonFile.readFileAsString(FilePath.JSON_BAD_SCAN);
			JsonObject badScanJson = jsonFile.convertStringToJson(badScanValue);
			badScanJson.addProperty(Reports.TRANS_ID, transID);
			badScanJson.addProperty(Reports.TRANS_DATE, transDate);
			badScanJson.addProperty(Reports.DATE_CREATED, transDate);
			jsonData.put(Reports.JSON, badScanJson.toString());
			jsonData.put(Reports.TRANS_ID, transID);
			jsonData.put(Reports.BAD_SCAN, badScanJson);
			jsonData.put(Reports.TRANS_DATE, transDate);
			jsonData.put(Reports.TRANS_DATE_TIME, reportDate);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		return jsonData;
	}

	public Map<String, Object> getData() {
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

}
