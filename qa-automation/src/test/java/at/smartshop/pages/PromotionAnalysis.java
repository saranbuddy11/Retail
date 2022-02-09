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
	private static final By TBL_PROMOTIONAL_ANALYSIS_GRID = By.cssSelector("#promoPromotionLevel > tbody");
	public static final By TXT_PRODUCT_FILTER = By.cssSelector("input[placeholder='  - Enter Product Description -']");

	public List<String> tableHeaders = new ArrayList<>();
	private int recordCount;
	private Map<Integer, Map<String, String>> reportsData = new LinkedHashMap<>();
	private Map<Integer, Map<String, String>> intialData = new LinkedHashMap<>();
	private Map<String, String> promoActualData = new LinkedHashMap<>();
	private Map<String, String> PromoExpectedData = new LinkedHashMap<>();

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
	
	public void getTblRecordsUI() {
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
	}
	
	public void getRequiredRecord(String promotionName) {
		try {
			for (int rowCount = 1; rowCount < intialData.size(); rowCount++) {
				if (intialData.get(rowCount).get(tableHeaders.get(1)).equals(promotionName)) {
					System.out.println(intialData.get(rowCount).get(tableHeaders.get(1)));
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
				intialData.get(recordCount).put(columnName, values);
				System.out.println("intialData :"+ intialData.get(recordCount));
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
	
	public Map<String, String> promotionActualData() {
		try {
			System.out.println("recordCount22 :" + recordCount);
				for (int iter = 0; iter < tableHeaders.size(); iter++) {
					promoActualData.put(tableHeaders.get(iter), reportsData.get(recordCount).get(tableHeaders.get(iter)));
				}
//			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		return promoActualData;
	}
	
	public Map<String, String> PromotionExpectedData() {
		try {
				for (int iter = 0; iter < tableHeaders.size(); iter++) {
					PromoExpectedData.put(tableHeaders.get(iter), intialData.get(recordCount).get(tableHeaders.get(iter)));
				}
//			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		return PromoExpectedData;
	}

	public void verifyReportData() {
		try {
			System.out.println("reportsData :" + promoActualData);
			System.out.println("intialData :" + PromoExpectedData);
				for (int iter = 0; iter < tableHeaders.size(); iter++) {	
					CustomisedAssert.assertTrue(promoActualData.get(tableHeaders.get(iter))
							.contains(PromoExpectedData.get(tableHeaders.get(iter))));
					System.out.println("reportsData :" + promoActualData.get(tableHeaders.get(iter)));
					System.out.println("intialData :" + PromoExpectedData.get(tableHeaders.get(iter)));
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

	public List<String> getTableHeaders() {
		return tableHeaders;
	}
}
