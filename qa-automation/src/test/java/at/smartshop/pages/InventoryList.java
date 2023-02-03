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
import at.framework.reportsetup.ExtFactory;
import at.framework.ui.Foundation;
import at.smartshop.keys.Constants;
import at.smartshop.tests.TestInfra;

public class InventoryList extends Factory {
	private Foundation foundation = new Foundation();

	public static final By LBL_REPORT_NAME = By.xpath("//div[@id='Inventory List']");
	private static final By REPORT_GRID_FIRST_ROW = By.cssSelector("#hierarchicalGrid > tbody > tr:nth-child(1)");
	private static final By NO_DATA_AVAILABLE_IN_TABLE = By.xpath("//td[@class='dataTables_empty']");

	private static final By TBL_INVENTORY_LIST = By.id("hierarchicalGrid");
	private static final By TBL_INVENTORY_LIST_GRID = By.cssSelector("#hierarchicalGrid > tbody");
	public static final By TXT_SEARCH = By.cssSelector("input[aria-controls='rptdt']");
	public static final By DATA_EXISTING_DATE = By.cssSelector(
			"body > div.daterangepicker.ltr.single.auto-apply.opensright.show-calendar > div.drp-calendar.left.single > div.calendar-table > table > tbody > tr:nth-child(4) > td:nth-child(5)");
	private static final By TBL_INVENTORY_LIST_EXPANDED = By
			.cssSelector("#hierarchicalGrid > tbody > tr:nth-child(2) > td > div  > div > div > table");
	private static final By TBL_INVENTORY_LIST_GRID_EXPANDED = By
			.cssSelector("#hierarchicalGrid > tbody > tr:nth-child(2) > td > div  > div > div > table > tbody");
	public static final By TBL_EXPAND_BTN = By.xpath("//span[@title='Expand Row']");
	public static final By DATA_EXISTING_DATE_STAGING = By.cssSelector(
			"body > div.daterangepicker.ltr.single.auto-apply.opensright.show-calendar > div.drp-calendar.left.single > div.calendar-table > table > tbody > tr:nth-child(2) > td:nth-child(3)");
	
	private List<String> tableHeaders = new ArrayList<>();
	private Map<Integer, Map<String, String>> reportsData = new LinkedHashMap<>();

	public void verifyReportName(String reportName) {
		try {
			foundation.waitforElement(LBL_REPORT_NAME, Constants.EXTRA_LONG_TIME);
			String reportTitle = foundation.getText(LBL_REPORT_NAME);
			Assert.assertTrue(reportTitle.contains(reportName));
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

	/**
	 * This method is to get the Table Records Data from UI
	 */
	public void getTblRecordsUI() {
		try {
			int recordCount = 0;
			tableHeaders.clear();
			WebElement tableReportsList = getDriver().findElement(TBL_INVENTORY_LIST_GRID);
			WebElement tableReports = getDriver().findElement(TBL_INVENTORY_LIST);
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

	/**
	 * This method is to get the Expanded Table Records Data from UI
	 */
	public void getExpandedTblRecordsOfUI() {
		try {
			int recordCount = 0;
			tableHeaders.clear();
			reportsData.clear();
			WebElement tableReportsList = getDriver().findElement(TBL_INVENTORY_LIST_GRID_EXPANDED);
			WebElement tableReports = getDriver().findElement(TBL_INVENTORY_LIST_EXPANDED);
			JavascriptExecutor js = (JavascriptExecutor) getDriver();
			js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
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

	/**
	 * This method is to validate the Report Table Headers
	 * 
	 * @param columnNames
	 */
	public void verifyReportHeaders(String columnNames) {
		try {
			List<String> columnName = Arrays.asList(columnNames.split(Constants.DELIMITER_HASH));
			for (int iter = 1; iter < tableHeaders.size(); iter++) {
				Assert.assertTrue(tableHeaders.get(iter).equals(columnName.get(iter)));
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * This method is to validate the Report Data
	 * 
	 * @param expextedData
	 */
	public void verifyReportData(String expectedData) {
		try {
			List<String> expectedDataList = Arrays.asList(expectedData.split(Constants.DELIMITER_HASH));
			System.out.println("reportsData :"+ reportsData );
			System.out.println("expectedDataList :"+ expectedDataList );
			for (int iter = 0; iter < tableHeaders.size(); iter++) {
				Assert.assertTrue(reportsData.get(0).get(tableHeaders.get(iter)).contains(expectedDataList.get(iter)));
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * This method is to validate the Report Data
	 * 
	 * @param expextedData
	 */
	public void verifyReportDataForExpanded(List<String> expectedData) {
		try {
			System.out.println("reportsData :"+reportsData);
			System.out.println("expectedData :"+expectedData);
			for (int counter = 0; counter < 1; counter++) {
				List<String> expectedDataList = Arrays
						.asList(expectedData.get(counter).split(Constants.DELIMITER_HASH));
				for (int iter = 0; iter < tableHeaders.size(); iter++) {
					Assert.assertTrue(
							reportsData.get(counter).get(tableHeaders.get(iter)).contains(expectedDataList.get(iter)));
				}
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
}
