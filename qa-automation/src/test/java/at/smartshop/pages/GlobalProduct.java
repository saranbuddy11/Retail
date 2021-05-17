package at.smartshop.pages;

import org.openqa.selenium.By;

import at.framework.browser.Factory;


public class GlobalProduct extends Factory {	
	public static final By TXT_FILTER = By.id("filterType");

	public By getGlobalProduct(String product) {		
		return By.xpath("//td[@aria-describedby='dataGrid_name'][text()='" + product + "']");		
	}
}
