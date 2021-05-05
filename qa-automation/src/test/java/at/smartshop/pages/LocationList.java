package at.smartshop.pages;

import org.openqa.selenium.By;
import org.testng.Assert;

import at.framework.browser.Factory;
import at.framework.ui.Foundation;

public class LocationList extends Factory{
	Foundation foundation = new Foundation();
	public static final By txtFilter = By.id("filterType");
	public static final By dpdLocationList = By.id("filtervalues");

	public void selectLocaionName(String locationName) {
		try {
			By objLocationName = By.xpath("//a[text()='" + locationName + "']");
			foundation. click(objLocationName);
		}
		catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
}
