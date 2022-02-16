package at.smartshop.pages;

import java.util.ArrayList;
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

public class AccountAdjustment extends Factory {
	private Foundation foundation = new Foundation();

	private static final By TBL_ACCOUNT_ADJUSTMENT = By.id("rptdt");
	private static final By LBL_ROWS = By.cssSelector("#rptdt > tbody > tr");
	public static final By LBL_REPORT_NAME = By.cssSelector("#report-container > script + style + div > div");
	public static final By TXT_SEARCH = By.cssSelector("input[aria-controls='rptdt']");
	private static final By REPORT_GRID_FIRST_ROW = By.cssSelector("#rptdt > tbody > tr:nth-child(1)");
	private static final By NO_DATA_AVAILABLE_IN_TABLE = By.xpath("//td[@class='dataTables_empty']");
	public static final By TABLE_AMOUNT_SORT = By.id("rpt-amount");
    public static final By TABLE_HEAD = By.xpath("//table[@id='rptdt']/thead");
    public static final By DATE_COLUMN = By.xpath("//*[@id=\"rptdt\"]/tbody/tr/td");
    public static final By ACTION_COLUMN = By.xpath("//*[@id=\"rptdt\"]/tbody/tr[3]/td[7]");
    public static final By REP_DATE= By.xpath("//th[@aria-sort='ascending']");
	public static final By REP_REASON =By.id("rpt-reason");
    public static final By COLUMN_ROWDATA=By.xpath("//tbody[@role='alert']/tr");
    public static final By REASON = By.xpath("/html/body/div[4]/div[1]/div/div[3]/div/div/div/div[2]/div/div[2]/table/tbody/tr[4]/td[11]");
	
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
			Assert.fail(exc.toString());
		}
		return uiTblRowValues;
	}

	public void verifyReportName(String reportName) {
		try {
			foundation.waitforElement(LBL_REPORT_NAME, Constants.EXTRA_LONG_TIME);
			String reportTitle = foundation.getText(LBL_REPORT_NAME);
			Assert.assertTrue(reportTitle.contains(reportName));
		} catch (Exception exc) {
			Assert.fail(exc.toString());
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
			Assert.fail(exc.toString());
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
	public void verifyTblHeaderData(Map<String, String> actuals,String d1,String d2,String d3,String d4,String d5,String d6,String d7,String d8,String d9,String d10,String d11,String d12) {
		ArrayList<String> expectedValues = new ArrayList<String>();
		expectedValues.add(d1);
		expectedValues.add(d2);
		expectedValues.add(d3);
		expectedValues.add(d4);
		expectedValues.add(d5);
		expectedValues.add(d6);
		expectedValues.add(d7);
		expectedValues.add(d8);
		expectedValues.add(d9);
		expectedValues.add(d10);
		expectedValues.add(d11);
		expectedValues.add(d12);
	}
	public void verifyReasonCode(Map<String, String> actuals, String reason) {
		String value = actuals.get("Reason");
		CustomisedAssert.assertEquals(value, reason);
}}
