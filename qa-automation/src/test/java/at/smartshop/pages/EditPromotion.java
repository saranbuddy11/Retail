package at.smartshop.pages;

import org.openqa.selenium.By;

import at.framework.browser.Factory;

public class EditPromotion extends Factory {
	public static final By BTN_END_PROMO =By.id("disablepromotion");
	public static final By BTN_EXPIRE =By.xpath("//button[@class='ajs-button ajs-ok']");
}
