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
import at.smartshop.keys.Reports;
import at.smartshop.tests.TestInfra;

public class PromotionAnalysis extends Factory {
	private Foundation foundation = new Foundation();

	private static final By REPORT_GRID_FIRST_ROW = By.cssSelector("#promoPromotionLevel > tbody > tr:nth-child(1)");
	private static final By NO_DATA_AVAILABLE_IN_TABLE = By.xpath("//td[@class='dataTables_empty']");

	public final By TBL_PROMOTIONAL_ANALYSIS_GROUPBY_PROMOTIONS = By.xpath("//table[@id='promoPromotionLevel']");
	private static final By TBL_PROMOTIONAL_ANALYSIS_GRID_GROUPBY_PROMOTIONS = By
			.cssSelector("#promoPromotionLevel > tbody");
	public static final By TXT_PRODUCT_FILTER = By.cssSelector("input[placeholder='  - Enter Product Description -']");
	public final By TBL_PROMOTIONAL_ANALYSIS_GROUPBY_LOCATIONS = By.xpath("//table[@id='promoLocationLevel']");
	public final By TBL_PROMOTIONAL_ANALYSIS_GRID_GROUPBY_LOCATIONS = By.cssSelector("#promoLocationLevel > tbody");
//	public final By TBL_PROMOTIONAL_ANALYSIS_DETAILED_GROUPBY_LOCATIONS = By
//			.xpath("//table[@id='promoLocationLevel_c35ea2fb3ff6ee6479a9ac5ffb2ba5d2_secondLevel_child']");
//	private static final By TBL_PROMOTIONAL_ANALYSIS_GRID_DETAILED_GROUPBY_LOCATIONS = By
//			.cssSelector("#promoLocationLevel_c35ea2fb3ff6ee6479a9ac5ffb2ba5d2_secondLevel_child > tbody");
	public static final By EXPAND_ROW = By.xpath("//span[@title='Collapse Row']");
	public final By REPORT_GROUPBY_DPD = By.xpath("//select[@id='sorting']");

	public List<String> tableHeaders = new ArrayList<>();
	public List<String> tableHeadersForGroupbyLocation = new ArrayList<>();
	private int recordCount;
	private int recordCountForGroupbyLocation;
	private Map<Integer, Map<String, String>> reportsData = new LinkedHashMap<>();
	private Map<Integer, Map<String, String>> intialData = new LinkedHashMap<>();
	private Map<String, String> promoActualData = new LinkedHashMap<>();
	private Map<String, String> PromoExpectedData = new LinkedHashMap<>();
	private Map<Integer, Map<String, String>> reportsDataForGroupByrLocation = new LinkedHashMap<>();
	private Map<Integer, Map<String, String>> intialDataForGroupByrLocation = new LinkedHashMap<>();

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

	public void getUITblRecordsGroupbyPromotions() {
		try {
			int recordCount = 0;
			tableHeaders.clear();
			JavascriptExecutor js = (JavascriptExecutor) getDriver();
			js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
			WebElement tableReportsList = getDriver().findElement(TBL_PROMOTIONAL_ANALYSIS_GRID_GROUPBY_PROMOTIONS);
			WebElement tableReports = getDriver().findElement(TBL_PROMOTIONAL_ANALYSIS_GROUPBY_PROMOTIONS);
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
	}

	public void getUITblRecordsGroupbyLocations() {
		try {
			int recordCount = 0;
			tableHeaders.clear();
			reportsData.clear();
			By TBL_PROMOTIONAL_ANALYSIS_DETAILED_GROUPBY_LOCATIONS = By
					.cssSelector("#promoLocationLevel > tbody > tr:nth-child("+(recordCountForGroupbyLocation+2)+") > td > div > div > div > table");
			By TBL_PROMOTIONAL_ANALYSIS_GRID_DETAILED_GROUPBY_LOCATIONS = By
					.cssSelector("#promoLocationLevel > tbody > tr:nth-child("+(recordCountForGroupbyLocation+2)+") > td > div > div > div > table > tbody");
			JavascriptExecutor js = (JavascriptExecutor) getDriver();
			js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
			WebElement tableReportsList = getDriver()
					.findElement(TBL_PROMOTIONAL_ANALYSIS_GRID_DETAILED_GROUPBY_LOCATIONS);
			WebElement tableReports = getDriver().findElement(TBL_PROMOTIONAL_ANALYSIS_DETAILED_GROUPBY_LOCATIONS);
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
	}

	public void getRequiredRecord(String promotionName) {
		try {
			for (int rowCount = 0; rowCount < intialData.size(); rowCount++) {
				if (intialData.get(rowCount).get(tableHeaders.get(1)).equals(promotionName)) {
					recordCount = rowCount;
					break;
				}
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void getFirstRecordsOfGroupbyLocations() {
		try {
			int recordCount = 0;
			tableHeadersForGroupbyLocation.clear();
			WebElement tableReportsListOfGroupbyLocations = getDriver()
					.findElement(TBL_PROMOTIONAL_ANALYSIS_GRID_GROUPBY_LOCATIONS);
			WebElement tableReportsOfGroupbyLocations = getDriver()
					.findElement(TBL_PROMOTIONAL_ANALYSIS_GROUPBY_LOCATIONS);
			List<WebElement> columnHeadersOfGroupbyLocations = tableReportsOfGroupbyLocations
					.findElements(By.cssSelector("thead > tr > th"));
			List<WebElement> rows = tableReportsListOfGroupbyLocations.findElements(By.tagName("tr"));
			for (WebElement columnHeader : columnHeadersOfGroupbyLocations) {
				tableHeadersForGroupbyLocation.add(columnHeader.getText());
			}
			for (WebElement row : rows) {
				Map<String, String> uiTblRowValues = new LinkedHashMap<>();
				for (int columnCount = 1; columnCount < tableHeadersForGroupbyLocation.size() + 1; columnCount++) {
					WebElement column = row.findElement(By.cssSelector("td:nth-child(" + columnCount + ")"));
					uiTblRowValues.put(tableHeadersForGroupbyLocation.get(columnCount - 1), column.getText());
				}
				intialDataForGroupByrLocation.put(recordCount, uiTblRowValues);
				recordCount++;
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void getRequiredRecordGroupbyLocations(String promotionName) {
		try {
//			recordCountForGroupbyLocation=0;
			getFirstRecordsOfGroupbyLocations();
			for (int rowCount = 1; rowCount < intialDataForGroupByrLocation.size(); rowCount++) {
				if (intialDataForGroupByrLocation.get(rowCount).get(tableHeadersForGroupbyLocation.get(2))
						.equals(promotionName)) {
					recordCountForGroupbyLocation = rowCount;
					break;
				}
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void updateData(String columnName, String values) {
		try {
			intialData.get(recordCount).put(columnName, values);
			System.out.println("updated");
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
	
	public void updateDiscount(String discountAmount) {
		try {
			System.out.println("testttt :"+tableHeaders.get(8));
			String initialAmount = intialData.get(recordCount).get(tableHeaders.get(8)).replaceAll(Reports.REPLACE_DOLLOR,
					Constants.EMPTY_STRING);
//			String discountAmount = (String) jsonData.get("discount");
			double updatedAmount = Double.parseDouble(initialAmount) + Double.parseDouble(discountAmount);
			updatedAmount = Math.round(updatedAmount * 100.0) / 100.0;
			intialData.get(recordCount).put(tableHeaders.get(8), String.valueOf(updatedAmount));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
	
	public void updateRedemptionsCount(String redemptions) {
		try {
			String initialRedemptions = intialData.get(recordCount).get(tableHeaders.get(7));
//			String Redemptions = (String) jsonData.get("redeemcount");
			int updatedRedemptions = Integer.parseInt(initialRedemptions) + (Integer.parseInt(redemptions));
//			updatedRedemptions = Math.round(updatedAmount * 100.0) / 100.0;
			intialData.get(recordCount).put(tableHeaders.get(7), String.valueOf(updatedRedemptions));
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
			for (int iter = 0; iter < tableHeaders.size(); iter++) {
				promoActualData.put(tableHeaders.get(iter), reportsData.get(recordCount).get(tableHeaders.get(iter)));
			}
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
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		return PromoExpectedData;
	}

	public void verifyReportData() {
		try {
			System.out.println(promoActualData);
			System.out.println(PromoExpectedData);
			for (int iter = 0; iter < tableHeaders.size(); iter++) {
				CustomisedAssert.assertTrue(promoActualData.get(tableHeaders.get(iter))
						.contains(PromoExpectedData.get(tableHeaders.get(iter))));
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void expandRow() {
		try {
			foundation.click(By.cssSelector("#promoLocationLevel > tbody > tr:nth-child("
					+ (recordCountForGroupbyLocation + 1) + ") >  td > span"));
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

	public List<String> getTableHeadersForGroupbyLocation() {
		return tableHeadersForGroupbyLocation;
	}

	public Map<Integer, Map<String, String>> getIntialDataForGroupByrLocation() {
		return intialDataForGroupByrLocation;
	}

	public Map<Integer, Map<String, String>> getReportsDataForGroupByrLocation() {
		return reportsDataForGroupByrLocation;
	}
}
