package at.smartshop.pages;

import org.openqa.selenium.By;
import at.framework.browser.Factory;

public class EditSystem extends Factory {
	
	public static final By LBL_PAGE_TITLE = By.xpath("//div[text()='Edit a System']");
	public static final By TXT_SYSTEM_NAME = By.cssSelector("input#systemName");
	public static final By TXT_DISPLAY_NAME = By.cssSelector("input#displayName");
	public static final By TXT_SHELF_TIMER = By.cssSelector("input#timer");
	public static final By BTN_SAVE = By.cssSelector("button#saveBtn");
	
	
}
