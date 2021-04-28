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
	Dropdown Dropdown = new Dropdown();
	Foundation foundation = new Foundation();

	public By dpdSearchBy = By.id("searchBy");
	public By txtSearch = By.id("search");
	private By dpdLocation = By.id("loc-dropdown");
	private By dpdStatus = By.id("isdisabled");
	public By btnGo = By.id("findBtn");
	public By tblConsumers = By.id("consumerdt");
	public By tblConsumerSearchGrid = By.id("consumerdt");
	public By lblRows = By.cssSelector("#consumerdt > tbody > tr:nth-of-type(1)");
	

	public void enterSearchFields(String searchBy, String search, String locationName, String status) {
		try {
			Dropdown.selectItem(dpdSearchBy, searchBy, Constants.TEXT);
			textBox.enterText(txtSearch, search);
			Dropdown.selectItem(dpdLocation, locationName, Constants.TEXT);
			Dropdown.selectItem(dpdStatus, status, Constants.TEXT);
			foundation.click(btnGo);
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}

	}

	public void clickCell(String consumerName) {
		try {
			foundation.click(tblConsumers.partialLinkText(consumerName));
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
