package at.smartshop.v5.pages;

import org.openqa.selenium.By;

import at.framework.ui.Foundation;
import at.framework.ui.TextBox;

public class CardPayment {
	public static final By BTN_CLOSE = By.id("cc-modal-close-id");
    public By objText(String text) {
		return By.xpath("//*[text()='"+text+"']");
	}
}
