package at.smartshop.pages;

import org.openqa.selenium.By;
import at.framework.browser.Factory;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;

public class LocationList extends Factory {

	private Foundation foundation = new Foundation();
	private TextBox textBox = new TextBox();
	public static final By TXT_FILTER = By.id("filterType");
	public static final By DPD_LOCATION_LIST = By.id("filtervalues");
	public static final By TXT_SPINNER_MSG = By.xpath("//div[@class='humane humane-libnotify-info']");
	public static final By LINK_LOCATION_LIST = By.xpath("//td[@aria-describedby='dataGrid_table_namelink']//a");

	public void selectLocationName(String locationName) {
		textBox.enterText(TXT_FILTER, locationName);
		foundation.click(getlocationElement(locationName));
	}
	public By objGlobalProduct(String product) {
		return By.xpath("//td[@aria-describedby='dataGrid_name'][text()='" + product + "']");
	}

	public By getlocationElement(String locationName) {
		return By.xpath("//a[text()='" + locationName + "']");
	}

}
