package at.smartshop.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import at.framework.browser.Factory;
import at.framework.ui.Foundation;
import at.smartshop.keys.Constants;

public class TransactionSearchPage extends Factory{
	
	private Foundation foundation = new Foundation();
	
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
	
}
