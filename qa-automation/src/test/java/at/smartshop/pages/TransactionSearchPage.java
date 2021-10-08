	package at.smartshop.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import at.framework.browser.Factory;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.keys.Constants;

public class TransactionSearchPage extends Factory{
	
	private Foundation foundation = new Foundation();
	private TextBox textBox = new TextBox();
	
	public static final By TXT_SEARCH = By.cssSelector("input#transid");
	public static final By DPD_DATE_RANGE = By.cssSelector("div#daterange");
	public static final By TXT_CLEAR_ALL = By.cssSelector("span.select2-selection__clear");
	public static final By DPD_LOCATION = By.cssSelector("select#loc-dropdown");
	public static final By BTN_FIND = By.id("findBtn");
	public static final By LBL_TRANSACTION_SEARCH = By.xpath("//li[@id='Transaction Search']");
	private static final By GRID_SCHEDULED_REPORT = By.xpath("//div[@class='ranges']//ul");
	private static final By DPD_DATE_OPTIONS = By.xpath("//div[@class='ranges']//ul//li");
	public static final By TXT_LOCATION_NAME = By.xpath("//input[@placeholder='Select Locations']");
	public static final By TABLE_TRANSACTION_GRID = By.cssSelector("table#transdt > thead > tr");
	public static final By TABLE_TRANSACTION_ROW = By.cssSelector("tr.odd");
	public static final By TABLE_SALES_ITEM_GRID = By.cssSelector("table#rptdt > thead > tr");
	public static final By TXT_REPORT_SEARCH = By.xpath("//input[@aria-controls='rptdt']");
	public static final By LBL_REPORT_NAME = By.cssSelector("#report-container > script + div > div.col-12.comment-table-heading");
	private static final By DPD_LOCATIONS = By.cssSelector("div.span12.m-0 > span > span.selection > span");
	private static final By TXT_LOCATION_SEARCH = By.cssSelector("span.select2-search.select2-search--dropdown > input");
	private static final By DPD_LOCATION_LIST = By.cssSelector("span.select2-results > #select2-locdt-results");
	public static final By LINK_TRANSACTION_ID = By.cssSelector("th#transactionId");
	
	public void selectDate(String optionName) {
		try {
			foundation.click(DPD_DATE_RANGE);
			WebElement editerGrid = getDriver().findElement(GRID_SCHEDULED_REPORT);
			foundation.waitforElement(DPD_DATE_OPTIONS, Constants.EXTRA_LONG_TIME);
			List<WebElement> dateOptions = editerGrid.findElements(DPD_DATE_OPTIONS);			
			for (WebElement dateOption : dateOptions) {
				if (dateOption.getText().equals(optionName)) {	
					dateOption.click();
					break;
				}
			}
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
	
	public void selectLocation(String locationName) {
		try {
			foundation.click(DPD_LOCATIONS);
			textBox.enterText(TXT_LOCATION_SEARCH, locationName);
			foundation.click(DPD_LOCATION_LIST);
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}

	}
	
}