	package at.smartshop.pages;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import at.framework.browser.Factory;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;

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
	private static final By DPD_LOCATIONS = By.xpath("//select[@id='loc-dropdown']//..//span[contains(@class,'multiple')]");
	private static final By TXT_LOCATION_SEARCH = By.cssSelector("span.selection > span > ul > li > input");
	private static final By DPD_LOCATION_LIST = By.cssSelector("span.select2-results > #select2-locdt-results");
	public static final By LINK_TRANSACTION_ID = By.cssSelector("th#transactionId");
	public static final By BTN_PRINT = By.id("printBtn");
	public static final By TXT_TRANSACTION_SEARCH = By.xpath("//*[@id='transdt_filter']/label/input");
	
	public static final By LBL_LOCATION = By.xpath("//*[@id='location']/following-sibling::dd[1]");
	public static final By LBL_DEVICE = By.xpath("//*[@id='device']/following-sibling::dd[1]");
	public static final By LBL_SUBTOTAL = By.xpath("//*[@id='subtotal']/following-sibling::dd[1]");
	public static final By LBL_TAX = By.xpath("//*[@id='tax:']/following-sibling::dd[1]");
	public static final By LBL_TOTAL = By.xpath("//*[@id='total']/following-sibling::dd[1]");
	public static final By LBL_STATUS = By.id("status_0");
	public static final By LBL_PAYMENT_TYPE = By.id("paytype_0");
	public static final By LBL_PRODUCT = By.id("item_0");
	
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
			foundation.click(By.xpath("//li[text()='"+locationName+"']"));
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}

	}
	
	public By objTransactionId(String transactionAmount) {
		return By.xpath("//td[contains(text(),'"+transactionAmount+"')]//..//td//span");
	}
	
	public void selectTransactionID(String optionName,String location,String transactionAmount) {
		selectDate(optionName);
		selectLocation(location);
		foundation.click(TransactionSearchPage.BTN_FIND);
		textBox.enterText(TransactionSearchPage.TXT_TRANSACTION_SEARCH, transactionAmount);
		foundation.click(objTransactionId(transactionAmount));
		foundation.waitforElement(BTN_PRINT,Constants.SHORT_TIME);
		assertTrue(foundation.isDisplayed(BTN_PRINT));
	}
	
	public void verifyTransactionDetails(String total,String paymentType,String product) {
		assertEquals(foundation.getText(LBL_LOCATION),propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));
		//assertEquals(foundation.getText(LBL_DEVICE),propertyFile.readPropertyFile(Configuration.DEVICE_ID, FilePath.PROPERTY_CONFIG_FILE));
		//assertEquals(foundation.getText(LBL_SUBTOTAL),propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));
		assertTrue(foundation.getText(LBL_TOTAL).contains(total));
		assertEquals(foundation.getText(LBL_STATUS),Constants.ACCEPTED);
		assertEquals(foundation.getText(LBL_PAYMENT_TYPE),paymentType);
		assertEquals(foundation.getText(LBL_PRODUCT),product);
	}
	
}
