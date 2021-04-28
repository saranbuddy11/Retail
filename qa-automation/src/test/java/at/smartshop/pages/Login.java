package at.smartshop.pages;

import org.openqa.selenium.By;
import org.testng.Assert;
import at.framework.browser.Factory;
import at.framework.ui.*;


public class Login extends Factory {
	TextBox textBox = new TextBox();
	Foundation foundation = new Foundation();
	private By txtEmail = By.id("email");
	private By txtPassword = By.id("password");
	private By btnSignin = By.cssSelector("#loginform > button");
	private By lblUserName = By.id("drop5");
	private By mnuLogout = By.id("logout");
	

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
			foundation.click(btnSignin);
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
