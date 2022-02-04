package at.smartshop.pages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.aventstack.extentreports.Status;

import at.framework.browser.Factory;
import at.framework.generic.CustomisedAssert;
import at.framework.reportsetup.ExtFactory;
import at.framework.ui.Foundation;
import at.smartshop.keys.Constants;
import at.smartshop.tests.TestInfra;

public class PromotionAnalysis extends Factory {
	private Foundation foundation = new Foundation();

	private static final By REPORT_GRID_FIRST_ROW = By.cssSelector("#promoPromotionLevel > tbody > tr:nth-child(1)");
	private static final By NO_DATA_AVAILABLE_IN_TABLE = By.xpath("//td[@class='dataTables_empty']");

	
	public final By TBL_PROMOTIONAL_ANALYSIS = By.xpath("//table[@id='promoPromotionLevel']");
//	public static final By LBL_REPORT_NAME = By.cssSelector("#summarydt > caption");
	private static final By TBL_PROMOTIONAL_ANALYSIS_GRID = By.cssSelector("#promoPromotionLevel > tbody");
//	private static final By TBL_ITEM_STOCKOUT_DETAILS = By.cssSelector("table[aria-describedby='detaildt_info']");
//	private static final By TBL_ITEM_STOCKOUT_DETAILS_GRID = By.cssSelector("table[aria-describedby='detaildt_info'] > tbody");
	public static final By TXT_PRODUCT_FILTER = By.cssSelector("input[placeholder='  - Enter Product Description -']");

	private List<String> tableHeaders = new ArrayList<>();
	private List<String> itemStockoutDetailsHeaders = new ArrayList<>();
	private int recordCount;
	private Map<Integer, Map<String, String>> reportsData = new LinkedHashMap<>();
	private Map<Integer, Map<String, String>> intialData = new LinkedHashMap<>();
	private Map<Integer, Map<String, String>> reportsDetailsData = new LinkedHashMap<>();
	private Map<Integer, Map<String, String>> intialDetailsData = new LinkedHashMap<>();

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
	
	public Map<Integer, Map<String, String>> getTblRecordsUI() {
		try {
			int recordCount = 0;
			tableHeaders.clear();
			JavascriptExecutor js = (JavascriptExecutor) getDriver();
			js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
			WebElement tableReportsList = getDriver().findElement(TBL_PROMOTIONAL_ANALYSIS_GRID);
			WebElement tableReports = getDriver().findElement(TBL_PROMOTIONAL_ANALYSIS);
			List<WebElement> columnHeaders = tableReports.findElements(By.cssSelector("thead > tr > th"));
			List<WebElement> rows = tableReportsList.findElements(By.tagName("tr"));
			System.out.println("columnHeaders :"+ columnHeaders.size());
			System.out.println("rows :"+ rows.size());
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
		System.out.println("reportsData :"+ reportsData);
		return reportsData;
	}
	
	public void getRequiredRecord(String promotionName) {
		try {
			for (int rowCount = 0; rowCount < intialData.size(); rowCount++) {
				if (intialData.get(rowCount).get(tableHeaders.get(1)).equals(promotionName)) {
					recordCount = rowCount;
					break;
				}
			}
			System.out.println("recordCount :" + recordCount);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void updateData(String columnName, String values) {
		try {
//			for (int iter = 0; iter < requiredRecords.size(); iter++) {
				intialData.get(recordCount).put(columnName, values);
//			}
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

	public List<String> getTableHeaders() {
		return tableHeaders;
	}

//	public List<String> getItemStockoutDetailsHeaders() {
//		return itemStockoutDetailsHeaders;
//	}

}
