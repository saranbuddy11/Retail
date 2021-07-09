package at.smartshop.pages;

import org.openqa.selenium.By;
import at.framework.browser.Factory;
import at.framework.ui.Foundation;

public class LocationList extends Factory {
	
	private Foundation foundation = new Foundation();
	
	public static final By TXT_FILTER = By.id("filterType");
	public static final By DPD_LOCATION_LIST = By.id("filtervalues");
	public static final By TXT_SPINNER_MSG = By.xpath("//div[@class='humane humane-libnotify-info']");
	public static final By LINK_LOCATION_LIST = By.xpath("//td[@aria-describedby='dataGrid_table_namelink']//a");

	public void selectLocationName(String locationName) {		
			foundation.click(By.xpath("//a[text()='" + locationName + "']"));		
	}

	public By objGlobalProduct(String product) {
		return By.xpath("//td[@aria-describedby='dataGrid_name'][text()='" + product + "']");
	}
  
	public By getlocationElement(String locationName) {
		return By.xpath("//a[text()='" + locationName + "']");

	}
}
