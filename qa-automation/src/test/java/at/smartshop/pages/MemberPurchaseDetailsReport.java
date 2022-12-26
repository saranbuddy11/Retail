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
import at.framework.generic.CustomisedAssert;
import at.framework.reportsetup.ExtFactory;
import at.framework.ui.Foundation;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.keys.Reports;
import at.smartshop.tests.TestInfra;
import at.smartshop.utilities.WebService;

public class MemberPurchaseDetailsReport extends Factory {

	// Reference by Class wide variables

	private JsonFile jsonFunctions = new JsonFile();
	private PropertyFile propertyFile = new PropertyFile();
	private WebService webService = new WebService();
	private Foundation foundation = new Foundation();

	// Lists
	private List<String> scancodeData = new LinkedList<>();
	private List<String> productNameData = new LinkedList<>();
	private List<String> priceData = new LinkedList<>();
	private List<String> taxData = new LinkedList<>();
	private List<String> category1Data = new LinkedList<>();
	private List<Integer> requiredList = new LinkedList<>();
	private List<String> tableHeaders = new ArrayList<>();

	// Maps
	private Map<String, Object> jsonData = new HashMap<>();
	private Map<Integer, Map<String, String>> initialData = new LinkedHashMap<>();
	private Map<Integer, Map<String, String>> reportsData = new LinkedHashMap<>();

	// Object Reference by CSS
	private static final By TABLE_MEMBER_PURCHASE_DETAILS = By.cssSelector("table[id='rptdt']");
	private static final By LBL_REPORT_NAME = By
			.cssSelector("#report-container > div > div.col-12.comment-table-heading");
	private static final By TABLE_MEMBER_PURCHASE_DETAILS_GRID = By.cssSelector("#rptdt > tbody");
	public static final By CSS_TXT_SEARCH = By.cssSelector("input[aria-controls='rptdt']");
	private static final By REPORT_GRID_FIRST_ROW = By.cssSelector("#rptdt > tbody > tr:nth-child(1)");
	private static final By NO_DATA_AVAILABLE_IN_TABLE = By.xpath("//td[@class='dataTables_empty']");

	public void getMemberPurchaseDetails() {
		try {
			tableHeaders.clear();
			WebElement tableReports = getDriver().findElement(TABLE_MEMBER_PURCHASE_DETAILS);
			List<WebElement> headers = tableReports.findElements(By.cssSelector("thead > tr > th"));
			for (WebElement header : headers) {
				tableHeaders.add(header.getText());
			}
			WebElement tableReportsList = getDriver().findElement(TABLE_MEMBER_PURCHASE_DETAILS_GRID);
			List<WebElement> rows = tableReportsList.findElements(By.tagName("tr"));
			reportsData.clear();
			int count = 0;
			for (WebElement row : rows) {
				Map<String, String> reportsdata = new LinkedHashMap<>();
				for (int iter = 1; iter < tableHeaders.size() + 1; iter++) {
					WebElement column = row.findElement(By.cssSelector("td:nth-child(" + iter + ")"));
					reportsdata.put(tableHeaders.get(iter - 1), column.getText());
				}
				reportsData.put(count, reportsdata);
				count++;
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void getRequiredRecord(List<String> scancodes) {
		try {
			requiredList.clear();
			WebElement tableReportsList = getDriver().findElement(TABLE_MEMBER_PURCHASE_DETAILS_GRID);
			List<WebElement> rows = tableReportsList.findElements(By.tagName("tr"));
			for (int iter = 0; iter < scancodes.size(); iter++) {
				for (int val = 0; val < rows.size(); val++) {
					if (initialData.get(val).get(tableHeaders.get(4)).equals(scancodes.get(iter))
							&& initialData.get(val).get(tableHeaders.get(8)).equals(jsonData.get(Reports.TRANS_DATE))) {
						requiredList.add(val);
						break;
					}
				}
				if (requiredList.size() == scancodes.size()) {
					break;
				}
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

	public void updateListData1(String columnName, List<String> values) {
		try {
			for (int iter = 0; iter < requiredList.size(); iter++) {
				String value = String.valueOf(values.get(iter));
				initialData.get(requiredList.get(iter)).put(columnName, value);
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void updateData1(String columnName, String value) {
		try {
			for (int iter = 0; iter < requiredList.size(); iter++) {
				initialData.get(requiredList.get(iter)).put(columnName, value);
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void updateListData(String columnName, List<String> values) {
		try {
			for (int iter = 0; iter < initialData.size(); iter++) {
				String value = String.valueOf(values.get(iter));
				initialData.get(iter).put(columnName, value);
				System.out.println("initialData : "+ initialData);
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void updateData(String columnName, String value) {
		try {
			for (int iter = 0; iter < initialData.size(); iter++) {
				initialData.get(iter).put(columnName, value);
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void updateTotal() {
		try {
			for (int iter = 0; iter < requiredList.size(); iter++) {
				String subTotal = reportsData.get(requiredList.get(iter)).get(tableHeaders.get(5))
						.replaceAll(Reports.REPLACE_DOLLOR, Constants.EMPTY_STRING);
				String taxAmount = reportsData.get(requiredList.get(iter)).get(tableHeaders.get(6))
						.replaceAll(Reports.REPLACE_DOLLOR, Constants.EMPTY_STRING);
				double total = Double.parseDouble(subTotal) + Double.parseDouble(taxAmount);
				total = Math.round(total * 100.0) / 100.0;
				initialData.get(requiredList.get(iter)).put(tableHeaders.get(7), String.valueOf(total));
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
			int count = initialData.size();
			System.out.println("reportsData :"+ reportsData);
			System.out.println("initialData :"+ initialData);
			for (int counter = 0; counter < count; counter++) {
				for (int iter = 0; iter < tableHeaders.size(); iter++) {
					CustomisedAssert.assertTrue(reportsData.get(counter).get(tableHeaders.get(iter))
							.contains(initialData.get(counter).get(tableHeaders.get(iter))));
				}
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	private void generateJsonDetails(String reportFormat, String environment) {
		try {
			DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(Reports.DATE_FORMAT);
			DateTimeFormatter reqFormat = DateTimeFormatter.ofPattern(reportFormat);
			LocalDateTime tranDate = LocalDateTime.now();
			String transDate = tranDate.format(dateFormat);
			String reportDate = tranDate.format(reqFormat);

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
			jsonData.put(Reports.TRANS_DATE_TIME, reportDate);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void processAPI(String value, String environment) {
		try {
			generateJsonDetails(value, environment);
			salesJsonDataUpdate(environment);
			webService.apiReportPostRequest(
					propertyFile.readPropertyFile(Configuration.TRANS_SALES, FilePath.PROPERTY_CONFIG_FILE),
					(String) jsonData.get(Reports.JSON));
			getJsonArrayData();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void getJsonArrayData() {
		try {
			JsonArray items = ((JsonObject) jsonData.get(Reports.SALES)).get(Reports.ITEMS).getAsJsonArray();
			for (JsonElement item : items) {
				JsonObject element = item.getAsJsonObject();
				scancodeData.add(element.get(Reports.SCANCODE).getAsString());
				productNameData.add(element.get(Reports.NAME).getAsString());
				priceData.add(Constants.DOLLAR_SYMBOL + element.get(Reports.PRICE).getAsString());
				String tax = element.get(Reports.TAX).getAsString();
				double total = Double.parseDouble(tax);
				total = Math.round(total * 100.0) / 100.0;
				taxData.add(Constants.DOLLAR_SYMBOL + Double.toString(total));
				category1Data.add(element.get(Reports.CATEGORY1).getAsString());
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	private void jsonArrayDataUpdate(JsonObject jsonObj, String reqString, String salesheader) {
		try {
			JsonArray items = jsonObj.get(reqString).getAsJsonArray();
			for (JsonElement item : items) {
				JsonObject element = item.getAsJsonObject();
				element.addProperty(Reports.ID,
						UUID.randomUUID().toString().replace(Constants.DELIMITER_HYPHEN, Constants.EMPTY_STRING));
				element.addProperty(Reports.SALES_HEADER, salesheader);
				element.addProperty(Reports.TRANS_ID, (String) jsonData.get(Reports.TRANS_ID));
				element.addProperty(Reports.TRANS_DATE, (String) jsonData.get(Reports.TRANS_DATE));
				if (reqString.equals(Reports.PAYMENTS)) {
					element.addProperty(Reports.TYPE, Reports.ACCOUNT.toUpperCase());
				}
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	private void salesJsonDataUpdate(String environment) {
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
			JsonObject objSales = jsonFunctions.convertStringToJson(sale);
			objSales.addProperty(Reports.ID, salesHeaderID);
			objSales.addProperty(Reports.TRANS_ID, (String) jsonData.get(Reports.TRANS_ID));
			objSales.addProperty(Reports.TRANS_DATE, (String) jsonData.get(Reports.TRANS_DATE));
			jsonArrayDataUpdate(objSales, Reports.ITEMS, salesHeaderID);
			jsonArrayDataUpdate(objSales, Reports.PAYMENTS, salesHeaderID);
			saleJson.addProperty(Reports.SALE, objSales.toString());
			jsonData.put(Reports.JSON, saleJson.toString());
			jsonData.put(Reports.SALES, objSales);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public Map<String, Object> getJsonData() {
		return jsonData;
	}

	public Map<Integer, Map<String, String>> getInitialData() {
		return initialData;
	}

	public Map<Integer, Map<String, String>> getReportsData() {
		return reportsData;
	}

	public List<String> getScancodeData() {
		return scancodeData;
	}

	public List<String> getCategoryData() {
		return category1Data;
	}

	public List<String> getTaxData() {
		return taxData;
	}

	public List<String> getTableHeaders() {
		return tableHeaders;
	}

	public List<String> getPriceData() {
		return priceData;
	}

	public List<String> getProductNameData() {
		return productNameData;
	}

}
