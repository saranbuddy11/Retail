package at.smartshop.pages;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import at.framework.browser.Factory;
import at.framework.ui.Foundation;
import at.smartshop.keys.Constants;

public class ItemStockoutReport extends Factory {

//	private JsonFile jsonFunctions = new JsonFile();
//	private PropertyFile propertyFile = new PropertyFile();
//	private WebService webService = new WebService();
	private Foundation foundation = new Foundation();

	private static final By TBL_ITEM_STOCKOUT = By.id("summarydt");
	private static final By LBL_REPORT_NAME = By.cssSelector("#summarydt > caption");
	private static final By TBL_ITEM_STOCKOUT_GRID = By.cssSelector("#summarydt > tbody");
	private static final By TBL_ITEM_STOCKOUT_DETAILS = By.cssSelector("table[aria-describedby='detaildt_info']");
	private static final By TBL_ITEM_STOCKOUT_DETAILS_GRID = By
			.cssSelector("table[aria-describedby='detaildt_info'] > tbody");
	private static final By TXT_DETAILS_SEARCH = By.cssSelector("input[aria-controls='detaildt']");

	private List<String> tableHeaders = new ArrayList<>();
	private List<String> itemStockoutDetailsHeaders = new ArrayList<>();
//	private List<String> scancodeData = new LinkedList<>();
//	private List<String> productNameData = new LinkedList<>();
//	private List<String> priceData = new LinkedList<>();
//	private List<String> taxData = new LinkedList<>();
//	private List<String> category1Data = new LinkedList<>();
//	private List<String> category2Data = new LinkedList<>();
//	private List<String> category3Data = new LinkedList<>();
//	private List<String> discountData = new LinkedList<>();
//	private List<String> taxcatData = new LinkedList<>();
//	private List<String> requiredJsonData = new LinkedList<>();
	private int recordCount;
//	private Map<String, Object> jsonData = new HashMap<>();
	private Map<Integer, Map<String, String>> reportsData = new LinkedHashMap<>();
	private Map<Integer, Map<String, String>> intialData = new LinkedHashMap<>();
	private Map<Integer, Map<String, String>> reportsDetailsData = new LinkedHashMap<>();
	private Map<Integer, Map<String, String>> intialDetailsData = new LinkedHashMap<>();

	public void getTblRecordsUI() {
		try {
			int recordCount = 0;
			tableHeaders.clear();
			WebElement tableReportsList = getDriver().findElement(TBL_ITEM_STOCKOUT_GRID);
			WebElement tableReports = getDriver().findElement(TBL_ITEM_STOCKOUT);
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
	}
	
	public void getItemStockoutDetails() {
		try {
			int recordCount = 0;
			itemStockoutDetailsHeaders.clear();
			WebElement tableReportsList = getDriver().findElement(TBL_ITEM_STOCKOUT_DETAILS_GRID);
			WebElement tableReports = getDriver().findElement(TBL_ITEM_STOCKOUT_DETAILS);
			List<WebElement> columnHeaders = tableReports.findElements(By.cssSelector("thead > tr > th"));
			List<WebElement> rows = tableReportsList.findElements(By.tagName("tr"));
			for (WebElement columnHeader : columnHeaders) {
				itemStockoutDetailsHeaders.add(columnHeader.getText());
			}
			for (WebElement row : rows) {
				Map<String, String> uiTblRowValues = new LinkedHashMap<>();
				for (int columnCount = 1; columnCount < itemStockoutDetailsHeaders.size() + 1; columnCount++) {
					WebElement column = row.findElement(By.cssSelector("td:nth-child(" + columnCount + ")"));
					uiTblRowValues.put(itemStockoutDetailsHeaders.get(columnCount - 1), column.getText());
				}
				reportsDetailsData.put(recordCount, uiTblRowValues);
				recordCount++;
			}
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	public void getRequiredRecord(String scancode) {
		try {
			for (int rowCount = 0; rowCount < intialData.size(); rowCount++) {
				if (intialData.get(rowCount).get(tableHeaders.get(2)).equals(scancode)) {
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

	public void updateData(String values, String invValue, String stockoutTime) {
		try {
			List<String> value = Arrays.asList(values.split(Constants.DELIMITER_HASH));
			intialData.get(recordCount).put(tableHeaders.get(0), value.get(0));
			intialData.get(recordCount).put(tableHeaders.get(1), value.get(1));
			intialData.get(recordCount).put(tableHeaders.get(2), value.get(2));
			intialData.get(recordCount).put(tableHeaders.get(3), value.get(3));
			intialData.get(recordCount).put(tableHeaders.get(4), value.get(4));
			intialData.get(recordCount).put(tableHeaders.get(5), invValue);
			intialData.get(recordCount).put(tableHeaders.get(6), stockoutTime);
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	public int getHeaderCount(String headerName) {
		int headerCount = 0;
		try {
			for (headerCount = 0; headerCount < tableHeaders.size(); headerCount++) {
				if (tableHeaders.get(headerCount).equals(headerName)) {
					break;
				}
			}
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
		return headerCount;
	}

	public void navigateToProductsEvents(String headerNames, String locationName, String scancode) {
		try {
			List<String> headerName = Arrays.asList(headerNames.split(Constants.DELIMITER_HASH));
			int locationCount = getHeaderCount(headerName.get(0));
			int scancodeCount = getHeaderCount(headerName.get(1));
			foundation.click(By.xpath("//table[@id = 'summarydt']/tbody/tr/td[" + scancodeCount + "][text()='"
					+ scancode + "']//..//td[" + locationCount + "]/a[text()='" + locationName + "']"));
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	public void verifyReportHeaders(List<String> headers, String columnNames) {
		try {
			List<String> columnName = Arrays.asList(columnNames.split(Constants.DELIMITER_HASH));
			for (int iter = 0; iter < headers.size(); iter++) {
				Assert.assertTrue(headers.get(iter).equals(columnName.get(iter)));
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
	
	public void updateDetailsData(String values, String invValue, String stockoutTime) {
		try {
			List<String> value = Arrays.asList(values.split(Constants.DELIMITER_HASH));
			intialData.get(recordCount).put(tableHeaders.get(0), value.get(0));
			intialData.get(recordCount).put(tableHeaders.get(1), value.get(1));
			intialData.get(recordCount).put(tableHeaders.get(2), value.get(2));
			intialData.get(recordCount).put(tableHeaders.get(3), value.get(3));
			intialData.get(recordCount).put(tableHeaders.get(4), value.get(4));
			intialData.get(recordCount).put(tableHeaders.get(5), invValue);
			intialData.get(recordCount).put(tableHeaders.get(6), stockoutTime);
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

//	public void processAPI(String value) {
//		try {
//			generateJsonDetails(value);
//			salesJsonDataUpdate();
//			webService.apiReportPostRequest(
//					propertyFile.readPropertyFile(Configuration.TRANS_SALES, FilePath.PROPERTY_CONFIG_FILE),
//					(String) jsonData.get(Reports.JSON));
//			getJsonSalesData();
//			getJsonArrayData();
//		} catch (Exception exc) {
//			Assert.fail(exc.toString());
//		}
//	}

//	private void getJsonSalesData() {
//		try {
//			JsonObject sales = (JsonObject) jsonData.get(Reports.SALES);
//			String delivery = sales.get(Reports.DELIVERY).getAsString();
//			requiredJsonData.add(delivery);
//		} catch (Exception exc) {
//			exc.printStackTrace();
//			Assert.fail(exc.toString());
//		}
//	}

	public String getStockoutTime(String reportFormat) {
		DateTimeFormatter reqFormat = DateTimeFormatter.ofPattern(reportFormat);
		LocalDateTime tranDate = LocalDateTime.now();
		String stockoutTime = tranDate.format(reqFormat);
		return stockoutTime;
	}
	
	public void verifyReportDetailsData() {
		try {
			int count = intialDetailsData.size();
			for (int counter = 0; counter < count; counter++) {
				for (int iter = 0; iter < itemStockoutDetailsHeaders.size(); iter++) {
					Assert.assertTrue(reportsDetailsData.get(counter).get(itemStockoutDetailsHeaders.get(iter))
							.contains(intialDetailsData.get(counter).get(itemStockoutDetailsHeaders.get(iter))));
				}
			}
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

//	private void generateJsonDetails(String reportFormat) {
//		try {
//			DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(Reports.DATE_FORMAT);
//			DateTimeFormatter reqFormat = DateTimeFormatter.ofPattern(reportFormat);
//			LocalDateTime tranDate = LocalDateTime.now();
//			String transDate = tranDate.format(dateFormat);
//			String reportDate = tranDate.format(reqFormat);
//			String transID = propertyFile.readPropertyFile(Configuration.DEVICE_ID, FilePath.PROPERTY_CONFIG_FILE)
//					+ Constants.DELIMITER_HYPHEN
//					+ transDate.replaceAll(Reports.REGEX_TRANS_DATE, Constants.EMPTY_STRING);
//			jsonData.put(Reports.TRANS_ID, transID);
//			jsonData.put(Reports.TRANS_DATE, transDate);
//			jsonData.put(Reports.TRANS_DATE_TIME, reportDate);
//		} catch (Exception exc) {
//			Assert.fail(exc.toString());
//		}
//	}
//
//	private void getJsonArrayData() {
//		try {
//			JsonArray items = ((JsonObject) jsonData.get(Reports.SALES)).get(Reports.ITEMS).getAsJsonArray();
//			for (JsonElement item : items) {
//				JsonObject element = item.getAsJsonObject();
//				scancodeData.add(element.get(Reports.SCANCODE).getAsString());
//				productNameData.add(element.get(Reports.NAME).getAsString());
//				priceData.add(element.get(Reports.PRICE).getAsString());
//				taxData.add(element.get(Reports.TAX).getAsString());
//				category1Data.add(element.get(Reports.CATEGORY1).getAsString());
//				category2Data.add(element.get(Reports.CATEGORY2).getAsString());
//				category3Data.add(element.get(Reports.CATEGORY3).getAsString());
//				discountData.add(element.get(Reports.DISCOUNT).getAsString());
//				taxcatData.add(element.get(Reports.TAXCAT).getAsString());
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
//			}
//		} catch (Exception exc) {
//			Assert.fail(exc.toString());
//		}
//	}
//
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
//		} catch (Exception exc) {
//			Assert.fail(exc.toString());
//		}
//	}
//
//	public Map<String, Object> getJsonData() {
//		return jsonData;
//	}

	public Map<Integer, Map<String, String>> getIntialData() {
		return intialData;
	}

	public Map<Integer, Map<String, String>> getReportsData() {
		return reportsData;
	}
	
	public Map<Integer, Map<String, String>> getIntialDetailsData() {
		return intialDetailsData;
	}

	public Map<Integer, Map<String, String>> getReportsDetailsData() {
		return reportsDetailsData;
	}

//	public List<String> getScancodeData() {
//		return scancodeData;
//	}
//
//	public List<String> getCategory1Data() {
//		return category1Data;
//	}
//
//	public List<String> getTaxCatData() {
//		return taxcatData;
//	}
//
//	public List<String> getCategory2Data() {
//		return category2Data;
//	}
//
//	public List<String> getCategory3Data() {
//		return category3Data;
//	}
//
//	public List<String> getTaxData() {
//		return taxData;
//	}
//
//	public List<String> getRequiredJsonData() {
//		return requiredJsonData;
//	}

	public List<String> getTableHeaders() {
		return tableHeaders;
	}
	
	public List<String> getItemStockoutDetailsHeaders() {
		return itemStockoutDetailsHeaders;
	}

//	public List<String> getPriceData() {
//		return priceData;
//	}
//
//	public List<String> getProductNameData() {
//		return productNameData;
//	}

}
