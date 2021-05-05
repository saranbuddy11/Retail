package at.smartshop.pages;

import org.openqa.selenium.By;
import org.testng.Assert;
import at.framework.browser.Factory;
import at.framework.ui.*;


public class Login extends Factory {
	TextBox textBox = new TextBox();
	Foundation foundation = new Foundation();
	private static final By txtEmail = By.id("email");
	private static final By txtPassword = By.id("password");
	private static final By btnSignIn = By.cssSelector("#loginform > button");
	private static final By lblUserName = By.id("drop5");
	private static final By mnuLogout = By.id("logout");
	

	public void insertLoginFields(String userName, String password) {
		try {
			textBox.enterText(txtEmail, userName);
			textBox.enterText(txtPassword, password);
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	public void login(String userName, String password) {
		try {
			insertLoginFields(userName, password);
			foundation.click(btnSignIn);
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	public void logout() {
		try {
			foundation.click(lblUserName);
			foundation.click(mnuLogout);
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
}
