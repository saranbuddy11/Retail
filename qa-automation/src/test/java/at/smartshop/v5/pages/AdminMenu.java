package at.smartshop.v5.pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import at.framework.ui.Foundation;

public class AdminMenu {
	
	private Foundation foundation = new Foundation();
	public static final By BTN_SIGN_IN = By.id("login");
	public static final By TXT_PRODUCT_SEARCH = By.cssSelector("input.search-input");
	public static final By LINK_DRIVER_LOGOUT = By.xpath("//span[text()='Logout']");
	
	public By objNumPad(int number) {
		return By.xpath("//input[@value='"+number+"']");
	}
	
	public void setPin(String pin) {
		try {
			for (int i = 0; i < pin.length(); i++) {
				int number = Integer.parseInt(pin.substring(i, i + 1));
				foundation.click(objNumPad(number));
			}
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
	

}
