package at.smartshop.pages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import at.framework.browser.Factory;

public class AccountAdjustment extends Factory {

	private static final By TBL_ACCOUNT_ADJUSTMENT = By.id("rptdt");
	private static final By LBL_ROWS = By.cssSelector("#rptdt > tbody > tr");
	public static final By LBL_REPORT_NAME = By.cssSelector("#report-container > script + style + div > div");
	public static final By TXT_SEARCH = By.cssSelector("input[aria-controls='rptdt']");

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
}
