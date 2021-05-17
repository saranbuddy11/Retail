package at.smartshop.pages;

import org.openqa.selenium.By;
import org.testng.Assert;
import at.framework.browser.Factory;
import at.framework.ui.Foundation;


public class GlobalProduct extends Factory {
	private Foundation foundation = new Foundation();
	public static final By TXT_FILTER = By.id("filterType");

	public By getGlobalProduct(String product) {		
		return By.xpath("//td[@aria-describedby='dataGrid_name'][text()='" + product + "']");		
	}
}
