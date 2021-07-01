package at.smartshop.pages;

import org.openqa.selenium.By;

import at.framework.browser.Factory;


public class GlobalProduct extends Factory {	
	public static final By TXT_FILTER = By.id("filterType");
	public static final By TBL_GRID = By.id("dataGrid");
	public static final By BTN_EXPORT = By.xpath("//button[text()='Export']");
	public static final By TXT_RECORD_COUNT = By.cssSelector("#dataGrid_pager_label");
	public static final By TBL_ROW =By.xpath("//*[@id='dataGrid']/tbody");

	public By getGlobalProduct(String product) {		
		return By.xpath("//td[@aria-describedby='dataGrid_name'][text()='" + product + "']");		
	}
	
}
