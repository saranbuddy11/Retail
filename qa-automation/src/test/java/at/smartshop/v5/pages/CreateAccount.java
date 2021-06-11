package at.smartshop.v5.pages;

import org.openqa.selenium.By;

import at.framework.ui.Foundation;
import at.framework.ui.TextBox;

public class CreateAccount {
	//public static final By CHK_TERMS = By.xpath("//input[@id='cb1']");
	public static final By CHK_TERMS = By.id("cb1");
	public static final By CHK_LABEL = By.xpath("//label[@class='checkmark']");
	public static final By BTN_CLOSE = By.id("login-cred-modal-close-id");
	
    public By objText(String text) {
		return By.xpath("//*[text()='"+text+"']");
	}
}
