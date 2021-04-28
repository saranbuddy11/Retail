package at.smartshop.pages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import at.framework.browser.Factory;
import at.smartshop.utilities.DataBase;

public class AccountAdjustment extends Factory {

	DataBase db = new DataBase();
	private By tblAccountAdjustment = By.id("rptdt");
	public By lblReportName = By.cssSelector("#report-container > script + style + div > div");
	public By txtSearch = By.cssSelector("input[aria-controls='rptdt']");
	private By lblRows = By.cssSelector("#rptdt > tbody > tr:nth-of-type(1)");

	public Map<String, String> getTblRecordsUI() {
		Map<String, String> uiTblRowValues = new HashMap<>();
		try {
			int curColumnIndex = 1;
			WebElement tableReports = getDriver().findElement(tblAccountAdjustment);
			List<WebElement> headers = tableReports.findElements(By.tagName("th"));
			WebElement row = getDriver().findElement(lblRows);

			for (WebElement header : headers) {
				WebElement column = row.findElement(By.cssSelector("td:nth-child(" + curColumnIndex + ")"));
				uiTblRowValues.put(header.getText(), column.getText());
				curColumnIndex++;
			}
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
		return uiTblRowValues;
	}
}
