package at.smartshop.pages;

import org.openqa.selenium.By;

public class Announcement {
	
	public static final By LBL_ANNOUNCEMENT_MSG = By.id("Announcement Message");
	public static final By DRP_COLOR = By.id("fontcolor");
	public static final By DRP_FONT_SIZE = By.id("fontsize");
	public static final By BTN_SAVE = By.id("saveBtn");	
	public static final By LBL_VALIDATE_FONT = By.xpath("//span[contains(text(),'Validating Announcement Message  ')]");
	public static final By VALIDATE_BG_COLOR = By.xpath("//div[@class='systemMessage']");
	public static final By TXT_MSG = By.id("msg");	

}
