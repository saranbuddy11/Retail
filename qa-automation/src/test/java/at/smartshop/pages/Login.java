package at.smartshop.pages;

import org.openqa.selenium.By;
import org.testng.Assert;

import at.framework.browser.Factory;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.keys.Constants;


public class Login extends Factory {
	private TextBox textBox = new TextBox();
	private Foundation foundation = new Foundation();
	
	private static final By TXT_EMAIL = By.id("username");
	private static final By TXT_PASSWORD = By.id("password");
	private static final By BTN_SIGN_IN = By.xpath("//button[@type='submit']");
	public static final By LBL_USER_NAME = By.id("drop5");
	private static final By MUN_LOGOUT = By.id("logout");
	

	public void insertLoginFields(String userName, String password) {
		try {
			foundation.waitforElement(TXT_EMAIL, Constants.TWO_SECOND);
			textBox.enterText(TXT_EMAIL, userName);
			textBox.enterText(TXT_PASSWORD, password);
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	public void login(String userName, String password) {
		try {
			foundation.click(BTN_SIGN_IN);
			insertLoginFields(userName, password);
			foundation.click(BTN_SIGN_IN);
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	public void logout() {
		try {
			
			foundation.waitforClikableElement(LBL_USER_NAME, Constants.SHORT_TIME);
			foundation.click(LBL_USER_NAME);
			foundation.click(MUN_LOGOUT);
			foundation.waitforElement(BTN_SIGN_IN, Constants.SHORT_TIME);
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
}
