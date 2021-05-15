package at.smartshop.pages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import at.framework.browser.Factory;
import at.framework.ui.*;
import at.smartshop.keys.Constants;

public class ConsumerSearch extends Factory{
	private TextBox textBox = new TextBox();
	private Dropdown dropdown = new Dropdown();
	private Foundation foundation = new Foundation();
	
	private static final By DPD_LOCATION = By.id("loc-dropdown");
	private static final By DPD_STATUS = By.id("isdisabled");
	private static final By DPD_SEARCH_BY = By.id("searchBy");
	private static final By TXT_SEARCH = By.id("search");
	private static final By BTN_GO = By.id("findBtn");
	public static final By TBL_CONSUMERS = By.id("consumerdt");
	public static final By TBL_CONSUMER_SEARCH_GRID = By.id("consumerdt");
	private static final By LBL_ROWS = By.cssSelector("#consumerdt > tbody > tr");
	public static final By TXT_BALANCE=By.xpath("//table//tbody//tr[@class='odd']//td[5]");
	public static final By TXT_CONSUMER_NAME=By.xpath("//table//tbody//tr[@class='odd']//td[3]//a");
	public static final By BTN_ADJUST=By.xpath("//a[text()='Adjust']");
	public static final By TXT_BALANCE_NUM = By.id("balNum");

	public void enterSearchFields(String searchBy, String search, String locationName, String status) {
		try {
			dropdown.selectItem(DPD_SEARCH_BY, searchBy, Constants.TEXT);
			textBox.enterText(TXT_SEARCH, search);
			dropdown.selectItem(DPD_LOCATION, locationName, Constants.TEXT);
			dropdown.selectItem(DPD_STATUS, status, Constants.TEXT);
			foundation.click(BTN_GO);
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}

	}

	public void clickCell(String consumerName) {
		try {
			By consumerFirstName = By.xpath("//table[@id='consumerdt']//tbody//tr//td//a[text()='"+consumerName+"']");
			foundation.click(consumerFirstName);
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
	
	public Map<String, String> getTblRecordsUI() {
		Map<String, String> uiTblRowValues = new HashMap<>();
		try {
			int curColumnIndex = 1;

			WebElement tableReports = getDriver().findElement(TBL_CONSUMER_SEARCH_GRID);
			List<WebElement> headers = tableReports.findElements(By.tagName("th"));
			WebElement row = getDriver().findElement(LBL_ROWS);

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
