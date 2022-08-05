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
import at.smartshop.tests.TestInfra;
import at.smartshop.utilities.WebService;

public class CashFlowEmployeeDevice {
	private Foundation foundation = new Foundation();
	private WebService webService = new WebService();
	private PropertyFile propertyFile = new PropertyFile();
	private JsonFile jsonFunctions = new JsonFile();

	private ReportList reportList = new ReportList();

	public static final By LBL_REPORT_NAME = By
			.cssSelector("#report-container > div:nth-child(3) > div.first-child > label");
	private static final By REPORT_GRID_FIRST_ROW = By.cssSelector("#Totals > tbody > tr:nth-child(1)");
	private static final By NO_DATA_AVAILABLE_IN_TABLE = By
			.xpath("//*[@id='Totals']//tbody//tr//td[@class='dataTables_empty']");

	private Map<String, Object> jsonData = new HashMap<>();
	private Map<Integer, Map<String, String>> initialReportsData = new HashMap<>();
	private Map<Integer, Map<String, String>> initialReportTotals = new HashMap<>();
	private Map<Integer, Map<String, String>> reportsTotalData = new LinkedHashMap<>();
	private Map<Integer, Map<String, String>> cashFlowDetailsTotal = new LinkedHashMap<>();
	private Map<Integer, Map<String, String>> calculateCashFlowTotal = new LinkedHashMap<>();
	private Map<Integer, Map<String, String>> cashFlowDetailsTotalsSum = new LinkedHashMap<>();
	private List<Double> requiredJsonData = new LinkedList<>();
	private List<Integer> requiredData = new LinkedList<>();
	private List<Integer> requiredCount = new LinkedList<>();
	private List<String> intialReportsData = new ArrayList<>();
	private JsonObject sales;

	/**
	 * Verifying the Report Name
	 * 
	 * @param reportName
	 */
	public void verifyReportName(String reportName) {
		try {
			foundation.waitforElement(LBL_REPORT_NAME, Constants.EXTRA_LONG_TIME);
			String reportTitle = foundation.getText(LBL_REPORT_NAME);
			Assert.assertTrue(reportTitle.contains(reportName));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * Checking the Data availability in Result Table
	 */
	public void checkForDataAvailabilyInResultTable() {
		try {
			if (foundation.isDisplayed(REPORT_GRID_FIRST_ROW)) {
				foundation.scrollIntoViewElement(REPORT_GRID_FIRST_ROW);
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

	public static void jsonArrayDataUpdate(JsonObject jsonObj, String reqString, String transID, String salesheader,
			String transDate, String transStatus, String paymentType) {
		JsonArray jsonarray = jsonObj.get(reqString).getAsJsonArray();
		for (JsonElement jsonarr : jsonarray) {
			JsonObject element = jsonarr.getAsJsonObject();
			element.addProperty(Reports.ID,
					UUID.randomUUID().toString().replace(Constants.DELIMITER_HYPHEN, Constants.EMPTY_STRING));
			element.addProperty(Reports.SALES_HEADER, salesheader);
			element.addProperty(Reports.TRANS_ID, transID);
			element.addProperty(Reports.TRANS_DATE, transDate);
		}
		if (reqString.equals(Reports.PAYMENTS)) {
			for (JsonElement jsonarr : jsonarray) {
				JsonObject element = jsonarr.getAsJsonObject();
				element.addProperty(Reports.STATUS, transStatus);
				element.addProperty(Reports.TYPE, paymentType);
			}
		}
	}

	/**
	 * Process API
	 * 
	 * @param transStatus
	 * @param paymentType
	 * @throws Exception
	 */
	public void processAPI(String transStatus, String paymentType, String deviceID) throws Exception {
		requiredCount.clear();
		int credCount = 0;
		int voidCredCount = 0;
		int declinedCredCount = 0;
		int accCount = 0;
		int accVoidCount = 0;
		int tipCount = 0;
		List<String> tStatus = Arrays.asList(transStatus.split(Constants.DELIMITER_HASH));
		List<String> payType = Arrays.asList(paymentType.split(Constants.DELIMITER_HASH));
		for (int iterator = 0; iterator < payType.size(); iterator++) {
			for (int iter = 0; iter < tStatus.size(); iter++) {
				webService.apiReportPostRequest(
						propertyFile.readPropertyFile(Configuration.TRANS_SALES, FilePath.PROPERTY_CONFIG_FILE),
						salesJsonDataUpdate(tStatus.get(iter), payType.get(iterator), deviceID));
				if (tStatus.get(iter).equals(Constants.ACCEPTED) && payType.get(iterator).equals(Constants.CREDIT)) {
					credCount = credCount + 1;
					tipCount = tipCount + 1;
				} else if (tStatus.get(iter).equals(Constants.VOID) && payType.get(iterator).equals(Constants.CREDIT)) {
					voidCredCount = voidCredCount + 1;
				} else if (tStatus.get(iter).equals(Constants.REJECTED)
						&& payType.get(iterator).equals(Constants.CREDIT)) {
					declinedCredCount = declinedCredCount + 1;
				} else if (tStatus.get(iter).equals(Constants.ACCEPTED)
						&& payType.get(iterator).equals(Constants.ACCOUNT)) {
					accCount = accCount + 1;
				} else if (tStatus.get(iter).equals(Constants.VOID)
						&& payType.get(iterator).equals(Constants.ACCOUNT)) {
					accVoidCount = accVoidCount + 1;
				}
			}
		}
		requiredCount.add(credCount);
		requiredCount.add(accCount);
		requiredCount.add(tipCount);
		requiredCount.add(accVoidCount);
		requiredCount.add(voidCredCount);
		requiredCount.add(declinedCredCount);
	}

	/**
	 * Get Json Sales Data
	 */
	public void getJsonSalesData() {
		try {
			requiredJsonData.clear();
			JsonObject sales = (JsonObject) jsonData.get(Reports.SALES);
			String subTotal = sales.get(Reports.SUBTOTAL).getAsString();
			requiredJsonData.add(Double.parseDouble(subTotal));
			String tax = sales.get(Reports.TAX).getAsString();
			requiredJsonData.add(Double.parseDouble(tax));
		} catch (Exception exc) {
			exc.printStackTrace();
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public String salesJsonDataUpdate(String transStatus, String paymentType, String deviceID) throws Exception {
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(Reports.DATE_FORMAT);
		String transDate = LocalDateTime.now().format(dateFormat);
		String salesHeaderID = UUID.randomUUID().toString().replace(Constants.DELIMITER_HYPHEN, Constants.EMPTY_STRING);
		String transID = deviceID + Constants.DELIMITER_HYPHEN
				+ transDate.replaceAll(Constants.REGEX_TRANS_DATE, Constants.EMPTY_STRING);
		String saleValue = jsonFunctions.readFileAsString(FilePath.JSON_SALES_CREATION);
		JsonObject jsonData = jsonFunctions.convertStringToJson(saleValue);
		jsonData.addProperty(Reports.TRANS_ID, transID);
		jsonData.addProperty(Reports.TRANS_DATE, transDate);
		String sale = jsonData.get(Reports.SALE).getAsString();
		sales = jsonFunctions.convertStringToJson(sale);
		sales.addProperty(Reports.ID, salesHeaderID);
		sales.addProperty(Reports.TRANS_ID, transID);
		sales.addProperty(Reports.TRANS_DATE, transDate);
		jsonArrayDataUpdate(sales, Reports.ITEMS, transID, salesHeaderID, transDate, transStatus, paymentType);
		jsonArrayDataUpdate(sales, Reports.PAYMENTS, transID, salesHeaderID, transDate, transStatus, paymentType);
		jsonData.addProperty(Reports.SALE, sales.toString());
		return jsonData.toString();
	}

	/**
	 * Select the report with Date range and Location to run the report
	 * 
	 * @param reportName
	 * @param date
	 * @param location
	 */
	public void selectAndRunReport(String reportName, String date, String location) {
		reportList.selectReport(reportName);
		reportList.selectDate(date);
		reportList.selectLocation(location);
		foundation.threadWait(Constants.SHORT_TIME);
		foundation.click(ReportList.BTN_RUN_REPORT);
		foundation.waitforElement(ProductSales.LBL_REPORT_NAME, Constants.SHORT_TIME);
		verifyReportName(reportName);
	}

	public Map<Integer, Map<String, String>> getCashFlowDetailsTotal() {
		return cashFlowDetailsTotal;
	}

	public Map<Integer, Map<String, String>> getCalculateCashFlowTotal() {
		return calculateCashFlowTotal;
	}

	public Map<Integer, Map<String, String>> getCashFlowDetailsTotalsSum() {
		return cashFlowDetailsTotalsSum;
	}

	public List<Double> getRequiredJsonData() {
		return requiredJsonData;
	}

	public List<Integer> getRequiredData() {
		return requiredData;
	}

	public List<Integer> getRequiredCount() {
		return requiredCount;
	}

	public List<String> getIntialReportsData() {
		return intialReportsData;
	}

	public Map<Integer, Map<String, String>> getInitialReportsData() {
		return initialReportsData;
	}

	public Map<Integer, Map<String, String>> getInitialReportTotals() {
		return initialReportTotals;
	}

	public Map<Integer, Map<String, String>> getReportsTotalData() {
		return reportsTotalData;
	}
}
