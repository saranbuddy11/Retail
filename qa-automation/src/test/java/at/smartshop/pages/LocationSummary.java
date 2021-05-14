package at.smartshop.pages;

import org.openqa.selenium.By;
import at.framework.browser.Factory;

public class LocationSummary extends Factory {

	public static final By DPD_DISABLED= By.id("isdisabled");
	public static final By BTN_SAVE= By.id("saveBtn");
	public static final By TBL_PRODUCTS= By.cssSelector("a#loc-products");
	public static final By TBL_PRODUCTS_LIST = By.cssSelector("#productDataGrid > tbody > td");
	public static final By POP_UP_BTN_SAVE =  By.id("confirmDisableId");
	public static final By BTN_LOCATION_SETTINGS =  By.id("toggleinfo");
	public static final By DPD_HAS_LOCKER =  By.id("haslocker");
	public static final By DPD_HAS_ORDER_AHEAD =  By.id("hasonlineordering");
	public static final By DPD_HAS_PICK_UP_LOCATIONS =  By.id("haspickuplocations");
	public static final By LNK_PICK_UP_LOCATION =  By.id("pickupLocationToggle");
	public static final By LBL_LOCKER_PICK_UP_TITLE =  By.xpath("//*[@id='lockersystempickuptitle']/i");
	public static final By LNK_LOCKER_NAME=  By.xpath("//*[@id='pickuplockersystems']/div/a");
	public static final By TXT_SYSTEM_NAME=  By.id("systemName");
	public static final By LBL_SHELF_LIFE=  By.xpath("//*[@id='pickuplockersystems']/div/span");
}
