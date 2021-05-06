package at.smartshop.pages;

import org.openqa.selenium.By;
import at.framework.browser.Factory;

public class LocationSummary extends Factory {

	public static final By DPD_DISABLED= By.id("isdisabled");
	public static final By BTN_SAVE= By.id("saveBtn");
	public static final By TBL_PRODUCTS= By.cssSelector("a#loc-products");
	public static final By TBL_PRODUCTS_LIST = By.cssSelector("#productDataGrid > tbody > td");
	public static final By POP_UP_BTN_SAVE =  By.id("confirmDisableId");
}
