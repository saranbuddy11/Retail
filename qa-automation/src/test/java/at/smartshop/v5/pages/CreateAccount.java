package at.smartshop.v5.pages;

import org.openqa.selenium.By;

public class CreateAccount {
	public static final By CHK_TERMS = By.id("cb1");
	public static final By CHK_LABEL = By.xpath("//label[@class='checkmark']");
	public static final By BTN_CLOSE = By.id("login-cred-modal-close-id");
	public static final By LBL_FINGERPRINT_HEADER = By.xpath("//h2[@class='fp-counter fixed-text']");
	public static final By LBL_SCAN_HEADER = By.xpath("//span[@data-reactid='.0.a.0.0.0.1.0.0.2.0.0.0']");
	public static final By LBL_FR_SCAN_HEADER = By.xpath("//h3[@data-reactid='.0.4.0.0.0.1.0.4.0.1.0']");
	public static final By LBL_FR_FINGERPRINT_HEADER = By.xpath("//span[@data-reactid='.0.4.0.0.0.1.0.4.0.1.0']");
	public static final By LBL_DUTCH_HEADER = By.xpath("//h2[@data-reactid='.0.4.0.0.0.1.0.0.0']");
	public static final By LBL_NORWEGIAN_HEADER = By.xpath("//h2[@data-reactid='.0.a.0.0.0.1.0.0.0']");
	public static final By LBL_NW_FINGERPRINT_HEADER = By.id("create-account-fpid-btn");

    public By objText(String text) {
		return By.xpath("//*[text()='"+text+"']");
	}
}
