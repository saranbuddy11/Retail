package at.smartshop.pages;

import org.openqa.selenium.By;
import org.testng.Assert;

import at.framework.browser.Factory;
import at.framework.ui.Foundation;

public class ProductSummary extends Factory {
	
	public static final By BTN_EXTEND = By.cssSelector("a#extend");
	public static final By TXT_FILTER = By.cssSelector("input[id=productFilterType]");
	public static final By BTN_SAVE = By.cssSelector("#modalsave");
	public static final By TXT_SEARCH = By.cssSelector("#locdt_filter > label > input");
	public static final By TBL_DATA = By.cssSelector("tbody[aria-relevant=all] span");
	public static final By BTN_REMOVE = By.id("previewremove");
	public static final By MSG_SUCCESS = By.className("div.humane.humane-libnotify-success");
	public static final By TBL_CELL_PRICE = By.cssSelector("td.edit.priceandstock.column-price");
	public static final By TXT_LOCATION_SEARCH_FILTER =  By.cssSelector("#locdt_filter > label > input");
	public static final By DPD_PRICE = By.id("price");
	
	public String getPriceFromLocationsTable() {
		String cellValue = null;
		try {
				cellValue = getDriver().findElement(TBL_CELL_PRICE).getText();
			} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
		return cellValue;
	}
}
