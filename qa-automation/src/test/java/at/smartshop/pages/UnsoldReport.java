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

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
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

public class UnsoldReport extends Factory {

	private JsonFile jsonFunctions = new JsonFile();
	private PropertyFile propertyFile = new PropertyFile();
	private WebService webService = new WebService();
	private Foundation foundation = new Foundation();

	private static final By TBL_UNSOLD_REPORT = By.id("rptdt");
	public static final By LBL_REPORT_NAME = By
			.cssSelector("#report-container > div > div.col-12.comment-table-heading");
	private static final By TBL_UNSOLD_REPORT_GRID = By.cssSelector("#rptdt > tbody");
	public static final By DPD_FILTER_BY = By.id("flt-group-by");
	private static final By DPD_LOCATION = By.id("select2-locdt-container");
	private List<String> tableHeaders = new ArrayList<>();
	private List<String> productNameData = new LinkedList<>();
	private List<String> soldProductNameData = new LinkedList<>();
	private List<String> reportProducts = new LinkedList<>();
	private Map<String, Object> jsonData = new HashMap<>();
	private Map<Integer, Map<String, String>> reportsData = new LinkedHashMap<>();
	private Map<Integer, Map<String, String>> intialData = new LinkedHashMap<>();

	public List<String> getTblRecordsUI(String columnName) {
		try {
			tableHeaders.clear();
			WebElement tableReportsList = getDriver().findElement(TBL_UNSOLD_REPORT_GRID);
			WebElement tableReports = getDriver().findElement(TBL_UNSOLD_REPORT);
			List<WebElement> columnHeaders = tableReports.findElements(By.cssSelector("thead > tr > th"));
			List<WebElement> rows = tableReportsList.findElements(By.tagName("tr"));
			for (WebElement columnHeader : columnHeaders) {
				tableHeaders.add(columnHeader.getText());
			}
			reportProducts.clear();
			for (int rowCount = 0; rowCount < rows.size(); rowCount++) {
				for (int columnCount = 0; columnCount < tableHeaders.size(); columnCount++) {
					if (tableHeaders.get(columnCount).equals(columnName)) {
						String productName = foundation.getText(By.xpath(
								"//table[@id='rptdt']/tbody/tr[" + (rowCount + 1) + "]/td[" + (columnCount + 1) + "]"));
						reportProducts.add(productName);
						break;
					}
				}
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		return reportProducts;
	}

	public void verifyReportName(String reportName) {
		try {
			String reportTitle = foundation.getText(LBL_REPORT_NAME);
			CustomisedAssert.assertTrue(reportTitle.contains(reportName));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void selectLocation(String locationName) {
		foundation.click(DPD_LOCATION);
		foundation.click(By.xpath("//ul[@id='select2-locdt-results']/li[text()='" + locationName + "']"));
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

	public void processAPI() {
		try {
			generateJsonDetails();
			salesJsonDataUpdate();
			webService.apiReportPostRequest(
					propertyFile.readPropertyFile(Configuration.TRANS_SALES, FilePath.PROPERTY_CONFIG_FILE),
					(String) jsonData.get(Reports.JSON));
			getJsonArrayData();
		} catch (Exception exc) {
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

	private void getJsonArrayData() {
		try {
			JsonArray items = ((JsonObject) jsonData.get(Reports.SALES)).get(Reports.ITEMS).getAsJsonArray();
			for (JsonElement item : items) {
				JsonObject element = item.getAsJsonObject();
				soldProductNameData.add(element.get(Reports.NAME).getAsString());
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void verifySoldProductsExist() {
		int count = 0;
		for (int iterator = 0; iterator < soldProductNameData.size(); iterator++) {
			if (reportProducts.contains(soldProductNameData.get(iterator))) {
				count = count + 1;
			}
		}
		if (count == soldProductNameData.size()) {
			CustomisedAssert.assertTrue(true);
		}
	}

	private void removeSoldProducts() {
		int count = 0;
		for (int iterator = 0; iterator < soldProductNameData.size(); iterator++) {
			if (productNameData.contains(soldProductNameData.get(iterator))) {
				productNameData.remove(soldProductNameData.get(iterator));
				count = count + 1;
			}
		}
		if (count == soldProductNameData.size()) {
			CustomisedAssert.assertTrue(true);
		}
	}

	public void verifyAllUnSoldProductsExist() {
		removeSoldProducts();
		int count = 0;
		for (int iterator = 0; iterator < reportProducts.size(); iterator++) {
			if (productNameData.contains(reportProducts.get(iterator))) {
				count = count + 1;
			}
		}
		if (count == reportProducts.size()) {
			CustomisedAssert.assertTrue(true);
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

	public List<String> getProductNameData() {
		return productNameData;
	}

	public List<String> getSoldProductNameData() {
		return soldProductNameData;
	}

	public List<String> getReportProducts() {
		return reportProducts;
	}

}
