package at.smartshop.pages;

import org.openqa.selenium.By;
import org.testng.Assert;
import at.framework.browser.Factory;
import at.framework.ui.*;

public class ProductSummary extends Factory {
	TextBox textbox = new TextBox();
	public By btnExtend = By.cssSelector("a#extend");
	public By txtFilter = By.cssSelector("input[id=productFilterType]");
	public By btnSave = By.cssSelector("#modalsave");
	public By txtSearch = By.cssSelector("#locdt_filter > label > input");
	public By tblData = By.cssSelector("tbody[aria-relevant=all] span");
	public By btnRemove = By.id("previewremove");
	public By msgSuccess = By.className("div.humane.humane-libnotify-success");
	public By tblCellPrice = By.cssSelector("td.edit.priceandstock.column-price");
	public By txtLocationSearchFilter =  By.cssSelector("#locdt_filter > label > input");
	public By drpPrice = By.id("price");

	public void getLocationName() {
		try {
			textbox.getText(tblData);
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
	
	public String getPriceFromLocationsTable() {
		String cellValue = null;
		try {
				cellValue = getDriver().findElement(tblCellPrice).getText();
			} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
		
		return cellValue;
	}

}
