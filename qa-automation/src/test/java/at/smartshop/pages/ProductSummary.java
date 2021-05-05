package at.smartshop.pages;

import org.openqa.selenium.By;
import at.framework.browser.Factory;
import at.framework.ui.TextBox;

public class ProductSummary extends Factory {
	TextBox textbox = new TextBox();
	public static final By btnExtend = By.cssSelector("a#extend");
	public static final By txtFilter = By.cssSelector("input[id=productFilterType]");
	public static final By btnSave = By.cssSelector("#modalsave");
	public static final By txtSearch = By.cssSelector("#locdt_filter > label > input");
	public static final By tblData = By.cssSelector("tbody[aria-relevant=all] span");
	public static final By btnRemove = By.id("previewremove");
	public static final By msgSuccess = By.className("div.humane.humane-libnotify-success");
	public static final By tblCellPrice = By.cssSelector("td.edit.priceandstock.column-price");
	public static final By txtLocationSearchFilter =  By.cssSelector("#locdt_filter > label > input");
	public static final By drpPrice = By.id("price");
}
