package at.smartshop.pages;

import org.openqa.selenium.By;
import at.framework.browser.Factory;
import at.framework.ui.Foundation;

public class LocationList extends Factory {
	private Foundation foundation = new Foundation();
	public static final By TXT_FILTER = By.id("filterType");
	public static final By DPD_LOCATION_LIST = By.id("filtervalues");
	public static final By BTN_HOME_COMMERCIAL = By.xpath("//a[text()='Home Commercial']");
	public static final By BTN_UPLOAD_INPUT = By.xpath("//div[@class ='qq-upload-button btn btn-success']/input");
	public static final By BTN_ADD_HOME_COMMERCIAL = By.xpath("//a[text()='Add Home Commercial']");
	public static final By TXT_UPLOAD_NEW = By.xpath("//a[text()='Upload New']");
	public static final By BTN_UPLOAD_IMAGE = By.xpath("//div[@class ='qq-upload-button btn btn-success']");
	public static final By TXT_ADD_NAME = By.xpath("//input[@id = 'cmrhometext']");
	public static final By BTN_ADD = By.xpath("//a[text()= 'Add']");
	public static final By LINK_HOME_PAGE = By.xpath("//a[@id='sup-location']");
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
