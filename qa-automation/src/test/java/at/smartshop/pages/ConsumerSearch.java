package at.smartshop.pages;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import at.framework.browser.Factory;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.keys.Constants;

public class ConsumerSearch extends Factory {
	private TextBox textBox = new TextBox();
	private Dropdown dropdown = new Dropdown();
	private Foundation foundation = new Foundation();

	private static final By DPD_LOCATION = By.id("loc-dropdown");
	private static final By DPD_STATUS = By.id("isdisabled");
	public static final By DPD_SEARCH_BY = By.id("searchBy");
	private static final By TXT_SEARCH = By.id("search");
	private static final By BTN_GO = By.id("findBtn");
	public static final By TBL_CONSUMERS = By.id("consumerdt");
	public static final By BTN_ADJUST = By.xpath("//a[text()='Adjust']");
	public static final By TXT_BALANCE_NUM = By.id("balNum");
	public static final By TBL_LOCATION = By.id("consumerdt");
	public static final By BTN_REASON_CANCEL = By.id("reasoncancel");
	public static final By BTN_ACTIONS = By.xpath("//a[@class='btn dropdown-toggle btn-danger']");
	public static final By LBL_BULK_ADD_FUNDS = By.xpath("//li[@id='fundSelectedBtn']");
	public static final By LBL_BULK_REMOVE_FUNDS = By.xpath("//li[@id='removeFundSelectedBtn']");
	public static final By LBL_BULK_TOPOFF_FUNDS = By.xpath("//li[@id='topOffFundSelectedBtn']");
	public static final By BTN_OK = By.xpath("//button[text()='Ok']");

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

	public By objCell(String consumerName) {
		return By.xpath("//table[@id='consumerdt']//tbody//tr//td//a[text()='" + consumerName + "']");
	}

	public List<String> getConsumerHeaders() {
		List<String> tableHeaders = new ArrayList<>();
		try {
			WebElement tableProducts = getDriver().findElement(TBL_LOCATION);
			List<WebElement> columnHeaders = tableProducts.findElements(By.cssSelector("thead > tr > th"));
			for (WebElement columnHeader : columnHeaders) {
				tableHeaders.add(columnHeader.getText());
			}
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
		return tableHeaders;
	}

	public Map<String, String> getConsumerRecords(String location) {
		Map<String, String> consumerRecord = new LinkedHashMap<>();
		try {
			List<String> tableHeaders = getConsumerHeaders();
			for (int columnCount = 1; columnCount < tableHeaders.size() + 1; columnCount++) {
				WebElement column = getDriver().findElement(By.xpath("//table[@id='consumerdt']//tr//td[(text()='"
						+ location + "')]//..//..//td[" + columnCount + "]"));
				consumerRecord.put(tableHeaders.get(columnCount - 1), column.getText());
			}
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
		return consumerRecord;
	}

	public void objLocation(String location) {
		List<WebElement> uiRecords = getDriver()
				.findElements(By.xpath("//table[@id='consumerdt']//tr//td[(text()='" + location + "')]"));
		for (int i = 0; i < uiRecords.size(); i++) {

			uiRecords.get(i).click();

		}

	}

}
