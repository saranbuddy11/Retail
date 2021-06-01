package at.smartshop.pages;

import org.openqa.selenium.By;

import at.framework.browser.Factory;

public class EditPromotion extends Factory {
	public static final By BTN_END_PROMO =By.id("disablepromotion");
	public static final By BTN_EXPIRE =By.xpath("//button[@class='ajs-button ajs-ok']");
	public static final By BTN_UPDATE =By.id("submitBtn");
	public static final By BTN_SAVE_AS =By.xpath("//button[text()='Save As New Promotion']");
	public static final By TXT_PROMOTION_NAME =By.xpath("//input[@placeholder='Promotion Name']");
	public static final By BTN_CONTINUE =By.xpath("//button[@class='ajs-button ajs-ok']");
	public static final By CHK_ACTIVE =By.id("active");
	public static final By BTN_OK=By.xpath("//button[text()='OK']");
	public static final By TXT_POPUP_HEADER=By.xpath("//div[@class='ajs-header']");
	public static final By TXT_POPUP_ALERT_MSG=By.xpath("//div[@class='ajs-content']");
	
}
