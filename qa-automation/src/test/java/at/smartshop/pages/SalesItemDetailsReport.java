package at.smartshop.pages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.aventstack.extentreports.Status;

import at.framework.browser.Factory;
import at.framework.generic.CustomisedAssert;
import at.framework.reportsetup.ExtFactory;
import at.framework.ui.Foundation;
import at.smartshop.keys.Constants;
import at.smartshop.tests.TestInfra;

public class SalesItemDetailsReport extends Factory {

	private Foundation foundation = new Foundation();

	public static final By LBL_REPORT_NAME = By
			.cssSelector("#report-container > div > div.col-12.comment-table-heading");
	private static final By REPORT_GRID_FIRST_ROW = By.cssSelector("#rptdt > tbody > tr:nth-child(1)");
	private static final By NO_DATA_AVAILABLE_IN_TABLE = By.xpath("//td[@class='dataTables_empty']");

	public final static By TXT_SEARCH = By.cssSelector("input[aria-controls='rptdt']");

	private static final By TBL_SALES_ANALYSIS = By.cssSelector("#rptdt");
	private static final By TBL_SALES_ANALYSIS_GRID = By.cssSelector("#rptdt > tbody");
	public static final By TBL_EXPAND_ROW = By.xpath("//span[@title='Expand Row']");
//	private static final By TBL_SALES_ANALYSIS_DETAILED_GROUPBY_LOCATIONS = By
//			.cssSelector("#rptdt > tbody > tr:nth-child(2) > td  > div >div >div >table");
//	private static final By TBL_SALES_ANALYSIS_GRID_DETAILED_GROUPBY_LOCATIONS = By
//			.cssSelector("#rptdt > tbody > tr:nth-child(2) > td  > div >div >div >table > tbody");

	private List<String> tableHeaders = new ArrayList<>();
	private List<String> salesTime = new ArrayList<>();
	private Map<Integer, Map<String, String>> reportsData = new LinkedHashMap<>();
	private Map<Integer, Map<String, String>> intialData = new LinkedHashMap<>();

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

	public Map<Integer, Map<String, String>> getTblRecordsUI() {
		try {
			int recordCount = 0;
			tableHeaders.clear();
			WebElement tableReportsList = getDriver().findElement(TBL_SALES_ANALYSIS_GRID);
			WebElement tableReports = getDriver().findElement(TBL_SALES_ANALYSIS);
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
		return reportsData;
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

	public void verifyReportTimeData() {
		try {
			int count = reportsData.size();
			for (int counter = 0; counter < count; counter++) {
				salesTime.add(reportsData.get(counter).get(tableHeaders.get(13)));
			}
			for (int iter = 1; iter < salesTime.size(); iter++) {
				if (salesTime.get(0).equals(salesTime.get(iter))) {
					Assert.fail("Failed due to Sales Items got created for Second time after redeeming promotion");
				}
			}
			for (int iter = 1; iter < salesTime.size(); iter++) {
				if (salesTime.get(iter-1).equals(salesTime.get(iter))) {
					Assert.fail("Failed due to Sales Items got created for Second time after redeeming promotion");
				}
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public Map<Integer, Map<String, String>> getReportsData() {
		return reportsData;
	}

	public List<String> getTableHeaders() {
		return tableHeaders;
	}
}
