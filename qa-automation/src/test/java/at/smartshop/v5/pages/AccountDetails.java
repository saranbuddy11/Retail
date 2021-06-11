package at.smartshop.v5.pages;

import org.openqa.selenium.By;

import at.framework.ui.Foundation;
import at.framework.ui.TextBox;

public class AccountDetails {
    public By objText(String text) {
		return By.xpath("//*[text()='"+text+"']");
	}
}
