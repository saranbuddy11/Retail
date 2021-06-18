package at.smartshop.v5.pages;

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
	
	public void navigateDriverLoginPage() {
		foundation.doubleClick(LandingPage.IMG_LOGO);
		foundation.doubleClick(LandingPage.IMG_LOGO);
		foundation.click(LandingPage.IMG_LOGO);
	}	
}
