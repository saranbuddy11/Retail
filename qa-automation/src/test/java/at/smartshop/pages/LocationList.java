package at.smartshop.pages;

import org.openqa.selenium.By;

import at.framework.browser.Factory;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.keys.Constants;

public class LocationList extends Factory {

	private Foundation foundation = new Foundation();
	private TextBox textBox = new TextBox();
	public static final By TXT_FILTER = By.id("filterType");
	public static final By BTN_CREATE = By.xpath("//button[text()='Create New']");
	public static final By DPD_LOCATION_LIST = By.id("filtervalues");
	public static final By TXT_SPINNER_MSG = By.xpath("//div[@class='humane humane-libnotify-info']");
	public static final By TXT_RECORD_UPDATE_MSG = By.xpath("//div[@class='humane ']");
	public static final By TXT_SPINNER_ERROR_MSG = By.xpath("//div[@class='humane humane-libnotify-error']");
	public static final By LINK_LOCATION_LIST = By.xpath("//td[@aria-describedby='dataGrid_table_namelink']//a");
	public static final By LINK_HOME_PAGE = By.xpath("//a[@id='sup-location']");
	public static final By LBL_LOCATION_LIST = By.xpath("//li[text()='Location List']");

	public void selectLocationName(String locationName) {
		foundation.waitforElement(getlocationElement(locationName), Constants.SHORT_TIME);
		textBox.enterText(TXT_FILTER, locationName);
		foundation.click(By.xpath("//a[text()='" + locationName + "']"));
	}

	public By objGlobalProduct(String product) {
		return By.xpath("//td[@aria-describedby='dataGrid_name'][text()='" + product + "']");
	}

	public By getlocationElement(String locationName) {
		return By.xpath("//a[text()='" + locationName + "']");
	}

	public By objDailyRevenue(String locationName) {
		return By.xpath("//a[text()='" + locationName + "']//..//..//*[@aria-describedby='dataGrid_table_revenue']");
	}

}
