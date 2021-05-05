package at.smartshop.pages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import at.framework.browser.Factory;
import at.framework.database.DataBase;

public class AccountAdjustment extends Factory {

	DataBase db = new DataBase();
	private static final By tblAccountAdjustment = By.id("rptdt");
	private static final By lblRows = By.cssSelector("#rptdt > tbody > tr");
	public static final By lblReportName = By.cssSelector("#report-container > script + style + div > div");
	public static final By txtSearch = By.cssSelector("input[aria-controls='rptdt']");

	public Map<String, String> getTblRecordsUI() {
		Map<String, String> uiTblRowValues = new HashMap<>();
		try {
			int curColumnIndex = 1;
			WebElement tableReports = getDriver().findElement(tblAccountAdjustment);
			List<WebElement> columnHeaders = tableReports.findElements(By.tagName("th"));
			WebElement row = getDriver().findElement(lblRows);

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
