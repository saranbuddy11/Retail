package at.smartshop.pages;

import org.openqa.selenium.By;

import at.framework.browser.Factory;
import at.framework.ui.Foundation;


public class GlobalProduct extends Factory {
	Foundation foundation=new Foundation();
	public static final By TXT_FILTER = By.id("filterType");
	public static final By ICON_FILTER = By.id("dataGrid_dd_enabled_button");

	public By getGlobalProduct(String product) {		
		return By.xpath("//td[@aria-describedby='dataGrid_name'][text()='" + product + "']");		
	}
	
	public void selectFilter(String filterType) {
		foundation.click(ICON_FILTER);
		foundation.threadWait(500);
		foundation.click(By.xpath("//*[@id='dataGrid_dd_enabled']//span[text()='"+filterType+"']"));
	}
}
