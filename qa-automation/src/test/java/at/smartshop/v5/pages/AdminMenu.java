package at.smartshop.v5.pages;

import java.util.List;
import org.openqa.selenium.By;
import org.testng.Assert;

import at.framework.ui.Foundation;

public class AdminMenu {
	
	private Foundation foundation = new Foundation();
	
	public static final By BTN_SIGN_IN = By.id("login");
	public static final By TXT_PRODUCT_SEARCH = By.cssSelector("input.search-input");
	public static final By LINK_DRIVER_LOGOUT = By.xpath("//span[text()='Logout']");
	public static final By LINK_INVENTORY = By.xpath("//span[text()='Inventory']");
	public static final By BTN_REBOOT= By.id("reboot");

	
	public By objNumPad(int number) {
		return By.xpath("//input[@value='"+number+"']");
	}
	

	public void navigateDriverLoginPage() {
		foundation.doubleClick(LandingPage.IMG_LOGO);
		foundation.doubleClick(LandingPage.IMG_LOGO);
		foundation.click(LandingPage.IMG_LOGO);
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

	
	public void verifyOptions(List<String> options) {
		for(int iter=0; iter<options.size(); iter++) {
			foundation.isDisplayed(By.xpath("//div[text()='"+options.get(iter)+"']"));
		}
	}

}
