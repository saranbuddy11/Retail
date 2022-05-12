package at.smartshop.pages;

import org.openqa.selenium.By;

import at.framework.browser.Factory;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.keys.Constants;
import at.smartshop.tests.TestInfra;

public class Login extends Factory {
	private TextBox textBox = new TextBox();
	private Foundation foundation = new Foundation();

	private static final By TXT_EMAIL = By.id("username");
	// private static final By TXT_EMAIL = By.id("email");
	private static final By TXT_SOS_EMAIL = By.id("email");
	private static final By TXT_PASSWORD = By.id("password");
	private static final By BTN_SIGN_IN = By.xpath("//button[@type='submit']");
	public static final By LBL_USER_NAME = By.id("drop5");
	public static final By MUN_LOGOUT = By.id("logout");
	public static final By TXT_SSO_PASSWORD = By.id("i0118");
	public static final By SSO_BTN_SIGNIN = By.id("idSIButton9");
	public static final By TITLE_HEADER = By.xpath("//div[text()='Stay signed in?']");
	public static final By TXT_FORGET_PWD = By.id("idA_PWD_ForgotPassword");
	public static final By TXT_SIGNIN_ANOTHER_ACC = By.id("i1668");
	public static final By TXT_DESCRIPTION = By.id("KmsiDescription");
	public static final By CHECKBOX_FIELD = By.id("KmsiCheckboxField");
	public static final By SHOW_MSG = By.xpath("//label/span");
	public static final By BTN_NO = By.id("idBtn_Back");
	public static final By BTN_YES = By.id("idSIButton9");
	public static final By PICK_ACC_HEADER = By.xpath("//div[text()='Pick an account']");
	public static final By SIGN_OUT = By.xpath("//div[@class='table-cell text-left content']");

	public void insertLoginFields(String userName, String password) {
		try {
			foundation.waitforElement(TXT_EMAIL, Constants.TWO_SECOND);
			textBox.enterText(TXT_EMAIL, userName);
			foundation.click(BTN_SIGN_IN);
			textBox.enterText(TXT_PASSWORD, password);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void login(String userName, String password) {
		try {
			foundation.click(BTN_SIGN_IN);
			foundation.waitforElement(TXT_EMAIL, 5);
			textBox.enterText(TXT_EMAIL, userName);
			foundation.click(BTN_SIGN_IN);
			foundation.waitforElement(TXT_PASSWORD, 5);
			textBox.enterText(TXT_PASSWORD, password);
			// insertLoginFields(userName, password);
			foundation.click(BTN_SIGN_IN);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void sosLogin(String userName, String password) {
		try {
			foundation.waitforElement(TXT_SOS_EMAIL, Constants.TWO_SECOND);
			textBox.enterText(TXT_SOS_EMAIL, userName);
			textBox.enterText(TXT_PASSWORD, password);
			foundation.click(BTN_SIGN_IN);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void logout() {
		try {

			foundation.waitforClikableElement(LBL_USER_NAME, Constants.SHORT_TIME);
			foundation.click(LBL_USER_NAME);
			foundation.click(MUN_LOGOUT);
			foundation.waitforElement(BTN_SIGN_IN, Constants.SHORT_TIME);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void ssoLogin(String userName, String password) {
		try {
			foundation.click(BTN_SIGN_IN);
			foundation.waitforElement(TXT_EMAIL, 5);
			textBox.enterText(TXT_EMAIL, userName);
			foundation.click(BTN_SIGN_IN);
			foundation.waitforElement(TXT_SSO_PASSWORD, 5);
			textBox.enterText(TXT_SSO_PASSWORD, password);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void ssoLogout() {
		try {

			foundation.waitforClikableElement(LBL_USER_NAME, Constants.SHORT_TIME);
			foundation.click(LBL_USER_NAME);
			foundation.click(MUN_LOGOUT);
			foundation.waitforElement(PICK_ACC_HEADER, Constants.SHORT_TIME);
			foundation.click(SIGN_OUT);
			foundation.waitforElement(BTN_SIGN_IN, Constants.SHORT_TIME);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
}
