package at.smartshop.v5.pages;

import org.openqa.selenium.By;

import at.framework.ui.Foundation;

public class DriverLoginPage {
	
	private Foundation foundation = new Foundation();
	
	public static final By BTN_SIGN_IN = By.id("login");
	public static final By BTN_SELF_SERVICE_MODE = By.cssSelector("button#cancelMM");
	
	public void enterDriverPin(String pin) {
		for (int i = 0; i < pin.length(); i++) {
			int number = Integer.parseInt(pin.substring(i, i + 1));
			foundation.click(By.xpath("//input[@value='" + number + "']"));
		}
	}
	
	

}
