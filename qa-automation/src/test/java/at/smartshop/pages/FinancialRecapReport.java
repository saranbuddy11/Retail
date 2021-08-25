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
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.keys.Reports;
import at.smartshop.utilities.WebService;

public class FinancialRecapReport extends Factory {

	private JsonFile jsonFunctions = new JsonFile();
	private PropertyFile propertyFile = new PropertyFile();
	private WebService webService = new WebService();
	private Foundation foundation = new Foundation();
	private TextBox textBox = new TextBox();
	private Dropdown dropDown = new Dropdown();
	private ConsumerSummary consumerSummary = new ConsumerSummary();

	private static final By TBL_FINANCIAL_RECAP = By.id("rptdt");
	private static final By LBL_REPORT_NAME = By
			.cssSelector("#report-container > div > div.col-12.comment-table-heading");
	private static final By TBL_FINANCIAL_RECAP_GRID = By.cssSelector("#rptdt > tbody");

	private List<String> tableHeaders = new ArrayList<>();
//	private List<String> scancodeData = new LinkedList<>();
//	private List<String> productNameData = new LinkedList<>();
//	private List<String> priceData = new LinkedList<>();
//	private List<String> taxData = new LinkedList<>();
//	private List<String> category1Data = new LinkedList<>();
//	private List<String> category2Data = new LinkedList<>();
//	private List<String> category3Data = new LinkedList<>();
//	private List<String> discountData = new LinkedList<>();
//	private List<String> taxcatData = new LinkedList<>();
	private List<String> requiredJsonData = new LinkedList<>();
//	private List<Integer> requiredRecords = new LinkedList<>();
	private Map<String, Object> jsonData = new HashMap<>();
	private Map<Integer, Map<String, String>> reportsData = new LinkedHashMap<>();
	private Map<Integer, Map<String, String>> intialData = new LinkedHashMap<>();
	private Map<Integer, Map<String, String>> reportTotalData = new LinkedHashMap<>();
	private Map<Integer, Map<String, String>> financialRecapTotal = new HashMap<>();

	public Map<Integer, Map<String, String>> getTblRecordsUI() {
		try {
			int recordCount = 0;
			tableHeaders.clear();
			WebElement tableReportsList = getDriver().findElement(TBL_FINANCIAL_RECAP_GRID);
			WebElement tableReports = getDriver().findElement(TBL_FINANCIAL_RECAP);
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
			Map<String, String> reportsTotaldata = new LinkedHashMap<>();
			WebElement totalRow = getDriver().findElement(By.cssSelector("tfoot > tr"));
			for (int iter = 1; iter < tableHeaders.size() + 1; iter++) {
				WebElement column = totalRow.findElement(By.cssSelector("th:nth-child(" + iter + ")"));
				reportsTotaldata.put(tableHeaders.get(iter - 1),
						column.getText().replaceAll(Reports.REPLACE_DOLLOR, Constants.EMPTY_STRING));
			}
			reportTotalData.put(0, reportsTotaldata);
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
		return reportsData;
	}

//	public void getRequiredRecord(String transDate, List<String> scancodes) {
//		try {
//			requiredRecords.clear();
//			for (int iter = 0; iter < scancodes.size(); iter++) {
//				for (int val = 0; val < intialData.size(); val++) {
//					if (intialData.get(val).get(tableHeaders.get(0)).equals(transDate)
//							&& intialData.get(val).get(tableHeaders.get(4)).equals(scancodes.get(iter))) {
//						requiredRecords.add(val);
//						break;
//					}
//				}
//				if (requiredRecords.size() == scancodes.size()) {
//					break;
//				}
//			}
//		} catch (Exception exc) {
//			Assert.fail(exc.toString());
//		}
//	}

	public void verifyReportName(String reportName) {
		try {
			String reportTitle = foundation.getText(LBL_REPORT_NAME);
			Assert.assertTrue(reportTitle.contains(reportName));
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	public void calculateDoubleTotal(String columnName) {
		int rowSize = reportsData.size();
		double totalValue = 0.0;
		for (int iter = 0; iter < rowSize; iter++) {
			double value = Double.parseDouble(
					reportsData.get(iter).get(columnName).replaceAll(Reports.REPLACE_DOLLOR, Constants.EMPTY_STRING));
			totalValue = totalValue + value;
			totalValue = Math.round(totalValue * 100.0) / 100.0;
		}
		financialRecapTotal.get(0).put(columnName, String.valueOf(totalValue));
	}

	public void updateTotal() {
		int count = tableHeaders.size();
		for (int iter = 4; iter < count; iter++) {
			calculateDoubleTotal(tableHeaders.get(iter));
		}
	}

	public void calculateAmount(String columnName, String value) {
		String initialAmount = intialData.get(0).get(columnName).replaceAll(Reports.REPLACE_DOLLOR,
				Constants.EMPTY_STRING);
		double amount = Double.parseDouble(initialAmount) + Double.parseDouble(value);
		amount = Math.round((amount * 100.0) / 100.0);
		reportsData.get(0).put(columnName, String.valueOf(amount));
	}

	public void adjustBalance(String adjustBalance, String reasonCode) throws Exception {
		List<String> reason = Arrays.asList(reasonCode.split(Constants.DELIMITER_HASH));
		for (int iter = 0; iter < reason.size(); iter++) {
			String balance = String.valueOf(consumerSummary.getBalance());
			foundation.click(ConsumerSummary.BTN_ADJUST);
			foundation.waitforElement(ConsumerSummary.LBL_POPUP_ADJUST_BALANCE, Constants.SHORT_TIME);
			Assert.assertTrue(getDriver().findElement(ConsumerSummary.LBL_POPUP_ADJUST_BALANCE).isDisplayed());
			double updatedbalance = Double.parseDouble(balance) + Double.parseDouble(adjustBalance);
			textBox.enterText(ConsumerSummary.TXT_ADJUST_BALANCE, Double.toString(updatedbalance));
			dropDown.selectItem(ConsumerSummary.DPD_REASON, reason.get(iter), Constants.TEXT);
			foundation.click(ConsumerSummary.BTN_REASON_SAVE);
		}
	}

	public void updateAdjustment(String balance, String columnName) {
		String initialAmount = reportsData.get(0).get(columnName).replaceAll(Reports.REPLACE_DOLLOR,
				Constants.EMPTY_STRING);
		double updatedAmount = (Double.parseDouble(initialAmount) + Double.parseDouble(balance));
		updatedAmount = Math.round((updatedAmount * 100.0) / 100.0);
		reportsData.get(0).put(columnName, String.valueOf(updatedAmount));
	}

	public void updateGrossSales() {
		String salesGMA = reportsData.get(0).get(tableHeaders.get(4)).replaceAll(Reports.REPLACE_DOLLOR,
				Constants.EMPTY_STRING);
		String salesCreditCard = reportsData.get(0).get(tableHeaders.get(5)).replaceAll(Reports.REPLACE_DOLLOR,
				Constants.EMPTY_STRING);
		String salesOthers = reportsData.get(0).get(tableHeaders.get(6)).replaceAll(Reports.REPLACE_DOLLOR,
				Constants.EMPTY_STRING);
		double grossSales = Double.parseDouble(salesGMA) + Double.parseDouble(salesCreditCard)
				+ Double.parseDouble(salesOthers);
		grossSales = Math.round((grossSales * 100.0) / 100.0);
		reportsData.get(0).put(tableHeaders.get(7), String.valueOf(grossSales));
	}

	public void updateFees(String fees, String salesColumnName, String feesColumnName) {
		String sales = reportsData.get(0).get(salesColumnName).replaceAll(Reports.REPLACE_DOLLOR,
				Constants.EMPTY_STRING);
		double updatedFees = (Double.parseDouble(sales) * (Double.parseDouble(fees) / 100)) * -1;
		updatedFees = Math.round((updatedFees * 100.0) / 100.0);
		reportsData.get(0).put(feesColumnName, String.valueOf(updatedFees));
	}

	public void updateSalesTax() {
		String initialTax = intialData.get(0).get(tableHeaders.get(17)).replaceAll(Reports.REPLACE_DOLLOR,
				Constants.EMPTY_STRING);
		double updatedTax = Double.parseDouble(initialTax) + Double.parseDouble(requiredJsonData.get(0));
		updatedTax = Math.round((updatedTax * 100.0) / 100.0);
		reportsData.get(0).put(tableHeaders.get(17), String.valueOf(updatedTax));
	}

	public void updateNetCashOwed() {
		String grossSales = reportsData.get(0).get(tableHeaders.get(7)).replaceAll(Reports.REPLACE_DOLLOR,
				Constants.EMPTY_STRING);
		String feesGMA = reportsData.get(0).get(tableHeaders.get(8)).replaceAll(Reports.REPLACE_DOLLOR,
				Constants.EMPTY_STRING);
		String feesCreditCard = reportsData.get(0).get(tableHeaders.get(9)).replaceAll(Reports.REPLACE_DOLLOR,
				Constants.EMPTY_STRING);
		String productRefunds = reportsData.get(0).get(tableHeaders.get(10)).replaceAll(Reports.REPLACE_DOLLOR,
				Constants.EMPTY_STRING);
		String fundingClientOperator = reportsData.get(0).get(tableHeaders.get(11)).replaceAll(Reports.REPLACE_DOLLOR,
				Constants.EMPTY_STRING);
		String closedGMA = reportsData.get(0).get(tableHeaders.get(12)).replaceAll(Reports.REPLACE_DOLLOR,
				Constants.EMPTY_STRING);
		String otherAdjustment = reportsData.get(0)
				.get(tableHeaders.get(13)).replaceAll(Reports.REPLACE_DOLLOR, Constants.EMPTY_STRING);
		String cashFunding = reportsData.get(0).get(tableHeaders.get(14)).replaceAll(Reports.REPLACE_DOLLOR,
				Constants.EMPTY_STRING);
//		String cashAdjustment = reportsData.get(0).get(tableHeaders.get(15)).replaceAll(Reports.REPLACE_DOLLOR,
//				Constants.EMPTY_STRING);
		double netCashOwed = Double.parseDouble(grossSales) + Double.parseDouble(feesGMA)
				+ Double.parseDouble(feesCreditCard) + Double.parseDouble(productRefunds)
				+ Double.parseDouble(fundingClientOperator) + Double.parseDouble(closedGMA)
				+ Double.parseDouble(otherAdjustment) + Double.parseDouble(cashFunding);
		netCashOwed = Math.round((netCashOwed * 100.0) / 100.0);
		reportsData.get(0).put(tableHeaders.get(16), String.valueOf(netCashOwed));
	}

//	public void updateData(String columnName, List<String> values) {
//		try {
//			for (int iter = 0; iter < requiredRecords.size(); iter++) {
//				String value = String.valueOf(values.get(iter));
//				intialData.get(requiredRecords.get(iter)).put(columnName, value);
//			}
//		} catch (Exception exc) {
//			Assert.fail(exc.toString());
//		}
//	}
//
	public void updateData(String columnName, String value) {
		try {
			intialData.get(0).put(columnName, value);
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
//
//	public void updatePrice() {
//		try {
//			for (int iter = 0; iter < requiredRecords.size(); iter++) {
//				String price = priceData.get(iter);
//				String discount = discountData.get(iter);
//				double updatedPrice = Double.parseDouble(price) - Double.parseDouble(discount);
//				updatedPrice = Math.round(updatedPrice * 100.0) / 100.0;
//				intialData.get(requiredRecords.get(iter)).put(tableHeaders.get(8), String.valueOf(updatedPrice));
//			}
//		} catch (Exception exc) {
//			Assert.fail(exc.toString());
//		}
//	}

	public void verifyReportHeaders(String columnNames) {
		System.out.println(columnNames);
		System.out.println(tableHeaders);
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
		System.out.println(reportsData);
		System.out.println(intialData);
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
			List<String> payType = Arrays.asList(paymentType.split(Constants.DELIMITER_HASH));
			for (int iter = 0; iter < payType.size(); iter++) {
				generateJsonDetails();
				salesJsonDataUpdate(payType.get(iter));
				webService.apiReportPostRequest(
						propertyFile.readPropertyFile(Configuration.TRANS_SALES, FilePath.PROPERTY_CONFIG_FILE),
						(String) jsonData.get(Reports.JSON));
				getJsonSalesData();
			}
//			getJsonArrayData();
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	private void getJsonSalesData() {
		try {
			JsonObject sales = (JsonObject) jsonData.get(Reports.SALES);
			String tax = sales.get(Reports.TAX).getAsString();
			requiredJsonData.add(tax);
			String total = sales.get(Reports.TOTAL).getAsString();
			requiredJsonData.add(total);
//			JsonObject kioskCashOut = (JsonObject) data.get(PoReportsList.KIOSK_CASH_OUT_TRANS);
//			String totalCash = kioskCashOut.get(Reports.TOTAL_CASH).getAsString();
//			requiredJsonData.add(totalCash);
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

	public Map<Integer, Map<String, String>> getFinancialRecapTotalData() {
		return financialRecapTotal;
	}

	public Map<Integer, Map<String, String>> getReportsData() {
		return reportsData;
	}

	public Map<Integer, Map<String, String>> getReportsTotalData() {
		return reportTotalData;
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

	public List<String> getRequiredJsonData() {
		return requiredJsonData;
	}

	public List<String> getTableHeaders() {
		return tableHeaders;
	}

//	public List<String> getPriceData() {
//		return priceData;
//	}
//
//	public List<String> getProductNameData() {
//		return productNameData;
//	}

}
