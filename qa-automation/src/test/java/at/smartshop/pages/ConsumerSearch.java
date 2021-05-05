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
	TextBox textBox = new TextBox();
	Dropdown dropdown = new Dropdown();
	Foundation foundation = new Foundation();
	
	private static final By dpdLocation = By.id("loc-dropdown");
	private static final By dpdStatus = By.id("isdisabled");
	public static final By dpdSearchBy = By.id("searchBy");
	public static final By txtSearch = By.id("search");
	public static final By btnGo = By.id("findBtn");
	public static final By tblConsumers = By.id("consumerdt");
	public static final By tblConsumerSearchGrid = By.id("consumerdt");
	public static final By lblRows = By.cssSelector("#consumerdt > tbody > tr");

	public void enterSearchFields(String searchBy, String search, String locationName, String status) {
		try {
			dropdown.selectItem(dpdSearchBy, searchBy, Constants.TEXT);
			textBox.enterText(txtSearch, search);
			dropdown.selectItem(dpdLocation, locationName, Constants.TEXT);
			dropdown.selectItem(dpdStatus, status, Constants.TEXT);
			foundation.click(btnGo);
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

			WebElement tableReports = getDriver().findElement(tblConsumerSearchGrid);
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
