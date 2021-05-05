package at.smartshop.pages;

import org.openqa.selenium.By;
import org.testng.Assert;
import at.framework.browser.Factory;
import at.framework.ui.Foundation;


public class GlobalProduct extends Factory {
	Foundation foundation = new Foundation();
	public static final By txtFilter = By.id("filterType");

	public void selectGlobalProduct(String product) {
		try {
		By objColumn = By.xpath("//td[@aria-describedby='dataGrid_name'][text()='" + product + "']");
		foundation.click(objColumn);
		}
		catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
}
