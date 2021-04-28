package at.smartshop.pages;

import org.openqa.selenium.By;
import org.testng.Assert;

import at.framework.browser.Factory;
import at.framework.ui.Foundation;

public class LocationList extends Factory{
	Foundation foundation = new Foundation();
	public By txtFilter = By.id("filterType");
	public By dpdLocationList = By.id("filtervalues");

	public void selectLocaionName(String locationName) {
		try {
			By LocationName = By.xpath("//a[text()='" + locationName + "']");
			foundation. click(LocationName);
		}
		catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
	
	public void selectLocaionListType(String locationType) {
		try {
			By LocationName = By.xpath("//a[text()='" + locationType + "']");
			foundation. click(LocationName);
		}
		catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
}
