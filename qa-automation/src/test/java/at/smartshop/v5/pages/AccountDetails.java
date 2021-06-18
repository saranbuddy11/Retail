package at.smartshop.v5.pages;

import org.openqa.selenium.By;

public class AccountDetails {
	public static final By BTN_OK= By.id("term-condition-btn-go-id");
	
    public By objText(String text) {
		return By.xpath("//*[text()='"+text+"']");
	}
}
