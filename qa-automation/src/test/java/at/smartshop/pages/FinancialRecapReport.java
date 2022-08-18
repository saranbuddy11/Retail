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
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.keys.Reports;
import at.smartshop.tests.TestInfra;
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
	private static final By REPORT_GRID_FIRST_ROW = By.cssSelector("#rptdt > tbody > tr:nth-child(1)");
	private static final By NO_DATA_AVAILABLE_IN_TABLE = By.xpath("//td[@class='dataTables_empty']");

	private List<String> tableHeaders = new ArrayList<>();
	private List<String> requiredJsonData = new LinkedList<>();
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
			reportsData.clear();
			for (WebElement row : rows) {
				Map<String, String> uiTblRowValues = new LinkedHashMap<>();
				for (int columnCount = 1; columnCount < tableHeaders.size() + 1; columnCount++) {
					WebElement column = row.findElement(By.cssSelector("td:nth-child(" + columnCount + ")"));
					if (columnCount >= 4) {
						uiTblRowValues.put(tableHeaders.get(columnCount - 1),
								column.getText().replaceAll(Reports.REPLACE_DOLLOR, Constants.EMPTY_STRING));
					} else {
						uiTblRowValues.put(tableHeaders.get(columnCount - 1), column.getText());
					}
				}
				reportsData.put(recordCount, uiTblRowValues);
				recordCount++;
			}
			reportTotalData.clear();
			Map<String, String> reportsTotaldata = new LinkedHashMap<>();
			WebElement totalRow = getDriver().findElement(By.cssSelector("tfoot > tr"));
			for (int iter = 1; iter < tableHeaders.size() + 1; iter++) {
				WebElement column = totalRow.findElement(By.cssSelector("th:nth-child(" + iter + ")"));
				reportsTotaldata.put(tableHeaders.get(iter - 1),
						column.getText().replaceAll(Reports.REPLACE_DOLLOR, Constants.EMPTY_STRING));
			}
			reportTotalData.put(0, reportsTotaldata);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		return reportsData;
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
		BigDecimal val = BigDecimal.valueOf(amount);
		val = val.setScale(2, RoundingMode.HALF_EVEN);
		intialData.get(0).put(columnName, String.valueOf(val));
	}

	public void adjustBalance(String adjustBalance, String reasonCode) throws Exception {
		List<String> reason = Arrays.asList(reasonCode.split(Constants.DELIMITER_HASH));
		for (int iter = 0; iter < reason.size(); iter++) {
			Thread.sleep(2000);
			String balance = String.valueOf(consumerSummary.getBalance());
			foundation.click(ConsumerSummary.BTN_ADJUST);
			Thread.sleep(1000);
			foundation.waitforElement(ConsumerSummary.LBL_POPUP_ADJUST_BALANCE, Constants.SHORT_TIME);
//			CustomisedAssert.assertTrue(getDriver().findElement(ConsumerSummary.LBL_POPUP_ADJUST_BALANCE).isDisplayed());
			double updatedbalance = Double.parseDouble(balance) + Double.parseDouble(adjustBalance);
			textBox.enterText(ConsumerSummary.TXT_ADJUST_BALANCE, Double.toString(updatedbalance));
			Thread.sleep(1000);
			dropDown.selectItem(ConsumerSummary.DPD_REASON, reason.get(iter), Constants.TEXT);
			foundation.click(ConsumerSummary.BTN_REASON_SAVE);
			foundation.waitforElement(ConsumerSummary.BTN_ADJUST, Constants.SHORT_TIME);
			Thread.sleep(1000);
		}
	}

	public void updateAdjustment(String balance, String columnName) {
		String initialAmount = intialData.get(0).get(columnName).replaceAll(Reports.REPLACE_DOLLOR,
				Constants.EMPTY_STRING);
		double updatedAmount = (Double.parseDouble(initialAmount) + (Double.parseDouble(balance) * -1));
		BigDecimal val = BigDecimal.valueOf(updatedAmount);
		val = val.setScale(2, RoundingMode.HALF_EVEN);
		intialData.get(0).put(columnName, String.valueOf(val));
	}

	public void updateGrossSales() {
		String salesGMA = intialData.get(0).get(tableHeaders.get(4)).replaceAll(Reports.REPLACE_DOLLOR,
				Constants.EMPTY_STRING);
		String salesCreditCard = intialData.get(0).get(tableHeaders.get(5)).replaceAll(Reports.REPLACE_DOLLOR,
				Constants.EMPTY_STRING);
		String salesOthers = intialData.get(0).get(tableHeaders.get(6)).replaceAll(Reports.REPLACE_DOLLOR,
				Constants.EMPTY_STRING);
		double grossSales = Double.parseDouble(salesGMA) + Double.parseDouble(salesCreditCard)
				+ Double.parseDouble(salesOthers);
		BigDecimal val = BigDecimal.valueOf(grossSales);
		val = val.setScale(2, RoundingMode.HALF_EVEN);
		intialData.get(0).put(tableHeaders.get(7), String.valueOf(val));
	}

	public void updateFees(String fees, String salesColumnName, String feesColumnName) {
		String sales = intialData.get(0).get(salesColumnName).replaceAll(Reports.REPLACE_DOLLOR,
				Constants.EMPTY_STRING);
		double updatedFees = (Double.parseDouble(sales) * (Double.parseDouble(fees) / 100)) * -1;
		BigDecimal val = BigDecimal.valueOf(updatedFees);
		val = val.setScale(2, RoundingMode.HALF_EVEN);
		intialData.get(0).put(feesColumnName, String.valueOf(val));
	}

	public void updateSalesTax() {
		String initialTax = intialData.get(0).get(tableHeaders.get(16)).replaceAll(Reports.REPLACE_DOLLOR,
				Constants.EMPTY_STRING);
		double updatedTax = Double.parseDouble(initialTax) + (Double.parseDouble(requiredJsonData.get(0)) * 3);
		BigDecimal val = BigDecimal.valueOf(updatedTax);
		val = val.setScale(2, RoundingMode.HALF_EVEN);
		intialData.get(0).put(tableHeaders.get(16), String.valueOf(val));
	}

	public void updateNetCashOwed() {
		String grossSales = intialData.get(0).get(tableHeaders.get(7)).replaceAll(Reports.REPLACE_DOLLOR,
				Constants.EMPTY_STRING);
		String feesGMA = intialData.get(0).get(tableHeaders.get(8)).replaceAll(Reports.REPLACE_DOLLOR,
				Constants.EMPTY_STRING);
		String feesCreditCard = intialData.get(0).get(tableHeaders.get(9)).replaceAll(Reports.REPLACE_DOLLOR,
				Constants.EMPTY_STRING);
		String productRefunds = intialData.get(0).get(tableHeaders.get(10)).replaceAll(Reports.REPLACE_DOLLOR,
				Constants.EMPTY_STRING);
		String fundingClientOperator = intialData.get(0).get(tableHeaders.get(11)).replaceAll(Reports.REPLACE_DOLLOR,
				Constants.EMPTY_STRING);
		String closedGMA = intialData.get(0).get(tableHeaders.get(12)).replaceAll(Reports.REPLACE_DOLLOR,
				Constants.EMPTY_STRING);
		String otherAdjustment = intialData.get(0).get(tableHeaders.get(13)).replaceAll(Reports.REPLACE_DOLLOR,
				Constants.EMPTY_STRING);
		String cashFunding = intialData.get(0).get(tableHeaders.get(14)).replaceAll(Reports.REPLACE_DOLLOR,
				Constants.EMPTY_STRING);
		double netCashOwed = Double.parseDouble(grossSales) + Double.parseDouble(feesGMA)
				+ Double.parseDouble(feesCreditCard) + Double.parseDouble(productRefunds)
				+ Double.parseDouble(fundingClientOperator) + Double.parseDouble(closedGMA)
				+ Double.parseDouble(otherAdjustment) + Double.parseDouble(cashFunding);
		BigDecimal val = BigDecimal.valueOf(netCashOwed);
		val = val.setScale(2, RoundingMode.HALF_EVEN);
		intialData.get(0).put(tableHeaders.get(15), String.valueOf(val));
	}

	public void updateData(String columnName, String value) {
		try {
			intialData.get(0).put(columnName, value);
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
			foundation.threadWait(Constants.TWO_SECOND);
			System.out.println("reportsData :"+ reportsData);
			System.out.println("intialData :"+ intialData);
			for (int counter = 0; counter < count; counter++) {
				for (int iter = 0; iter < tableHeaders.size(); iter++) {
					if (iter == 11 || iter == 12 || iter == 13 || iter == 15) {
						continue;
					}
					CustomisedAssert.assertTrue(reportsData.get(counter).get(tableHeaders.get(iter))
							.contains(intialData.get(counter).get(tableHeaders.get(iter))));
					System.out.println("reportsData :"+ reportsData.get(counter).get(tableHeaders.get(iter)));
					System.out.println("intialData :"+ intialData.get(counter).get(tableHeaders.get(iter)));
				}
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
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
				foundation.threadWait(Constants.ONE_SECOND);
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	private void getJsonSalesData() {
		try {
			JsonObject sales = (JsonObject) jsonData.get(Reports.SALES);
			String tax = sales.get(Reports.TAX).getAsString();
			requiredJsonData.add(tax);
			String total = sales.get(Reports.TOTAL).getAsString();
			requiredJsonData.add(total);
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
			TestInfra.failWithScreenShot(exc.toString());
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
			TestInfra.failWithScreenShot(exc.toString());
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

	public List<String> getRequiredJsonData() {
		return requiredJsonData;
	}

	public List<String> getTableHeaders() {
		return tableHeaders;
	}

}
