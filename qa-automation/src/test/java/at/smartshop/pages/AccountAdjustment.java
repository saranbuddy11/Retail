package at.smartshop.pages;

import java.util.HashMap;
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

public class AccountAdjustment extends Factory {
	private Foundation foundation = new Foundation();

	private static final By TBL_ACCOUNT_ADJUSTMENT = By.id("rptdt");
	private static final By LBL_ROWS = By.cssSelector("#rptdt > tbody > tr");
	public static final By LBL_REPORT_NAME = By.cssSelector("#report-container > script + style + div > div");
	public static final By TXT_SEARCH = By.cssSelector("input[aria-controls='rptdt']");
	private static final By REPORT_GRID_FIRST_ROW = By.cssSelector("#rptdt > tbody > tr:nth-child(1)");
	private static final By NO_DATA_AVAILABLE_IN_TABLE = By.xpath("//td[@class='dataTables_empty']");
	public static final By TABLE_AMOUNT_SORT = By.id("rpt-amount");

	public Map<String, String> getTblRecordsUI() {
		Map<String, String> uiTblRowValues = new HashMap<>();
		try {
			int curColumnIndex = 1;
			WebElement tableReports = getDriver().findElement(TBL_ACCOUNT_ADJUSTMENT);
			List<WebElement> columnHeaders = tableReports.findElements(By.tagName("th"));
			WebElement row = getDriver().findElement(LBL_ROWS);

			for (WebElement columnHeader : columnHeaders) {
				WebElement column = row.findElement(By.cssSelector("td:nth-child(" + curColumnIndex + ")"));
				uiTblRowValues.put(columnHeader.getText(), column.getText());
				curColumnIndex++;
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		return uiTblRowValues;
	}

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

	public Map<String, String> getTblRecords(String index) {
		Map<String, String> uiTblRowValues = new HashMap<>();
		try {
			int curColumnIndex = 1;
			WebElement tableReports = getDriver().findElement(TBL_ACCOUNT_ADJUSTMENT);
			List<WebElement> columnHeaders = tableReports.findElements(By.tagName("th"));
			WebElement row = getDriver().findElement(By.cssSelector("#rptdt > tbody > tr:nth-child(" + index + ")"));

			for (WebElement columnHeader : columnHeaders) {
				WebElement column = row.findElement(By.cssSelector("td:nth-child(" + curColumnIndex + ")"));
				uiTblRowValues.put(columnHeader.getText(), column.getText());
				curColumnIndex++;
			}
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
		return uiTblRowValues;
	}

	public void verifyReasonCodeAndAmount(Map<String, String> actuals, String reason, String amount, String reflect,
			String name) {
		String value = actuals.get("Reason");
		CustomisedAssert.assertEquals(value, reason);
		value = actuals.get("After");
		CustomisedAssert.assertEquals(value, amount);
		value = actuals.get("Amount");
		CustomisedAssert.assertEquals(value, amount);
		value = actuals.get("Reflect on EFT");
		CustomisedAssert.assertEquals(value, reflect);
		value = actuals.get("Consumer Name");
		CustomisedAssert.assertTrue(value.contains(name));
	}
}
