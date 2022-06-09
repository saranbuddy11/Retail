package at.smartshop.pages;

import org.openqa.selenium.By;

import at.framework.browser.Factory;
import at.framework.generic.CustomisedAssert;
import at.framework.ui.CheckBox;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.database.columns.CNLoginPage;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.tests.TestInfra;

public class Login extends Factory {
	private TextBox textBox = new TextBox();
	private Foundation foundation = new Foundation();
	private CheckBox checkBox = new CheckBox();

	public static final By TXT_EMAIL = By.id("username");
	// private static final By TXT_EMAIL = By.id("email");
	public static final By TXT_SOS_EMAIL = By.id("email");
	public static final By TXT_PASSWORD = By.id("password");
	public static final By BTN_SIGN_IN = By.xpath("//button[@type='submit']");
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
	public static final By USERNAME_ERROR = By.id("usernameError");
	public static final By PASSWORD_ERROR = By.id("passwordError");
	public static final By ERROR_MSG = By.xpath("//div[@class='humane humane-libnotify-error']");

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
			foundation.waitforElement(TXT_EMAIL, Constants.SHORT_TIME);
			textBox.enterText(TXT_EMAIL, userName);
			foundation.click(BTN_SIGN_IN);
			foundation.waitforElement(TXT_PASSWORD, Constants.SHORT_TIME);
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

	public void ssoValidationInStaySignedinPage(String usertype, String description, String msg) {
		foundation.waitforElement(Login.TITLE_HEADER, Constants.SHORT_TIME);
		CustomisedAssert.assertTrue(foundation.isDisplayed(Login.TITLE_HEADER));
		String text = foundation.getText(Login.TITLE_HEADER);
		CustomisedAssert.assertEquals(text, usertype);
		foundation.waitforElement(Login.SSO_BTN_SIGNIN, 5);
		text = foundation.getText(Login.TXT_DESCRIPTION);
		CustomisedAssert.assertTrue(text.contains(description));
		foundation.waitforElement(Login.CHECKBOX_FIELD, 5);
		text = foundation.getText(Login.SHOW_MSG);
		CustomisedAssert.assertTrue(text.contains(msg));
	}

	public void verifyCheckboxInShowMsg(String colour) {
		checkBox.check(Login.CHECKBOX_FIELD);
		foundation.waitforElementToBeVisible(Login.BTN_NO, Constants.SHORT_TIME);
		checkBox.unCheck(Login.CHECKBOX_FIELD);
		String color = foundation.getBGColor(Login.BTN_YES);
		CustomisedAssert.assertEquals(color, colour);
		foundation.click(Login.BTN_YES);
		foundation.waitforElementToBeVisible(LocationList.LBL_LOCATION_LIST, Constants.SHORT_TIME);
		CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));
		ssoLogout();
	}

	public void loginWithoutMicrosoftAccount() {
		login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
				propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
		foundation.waitforElementToBeVisible(LocationList.LBL_LOCATION_LIST, Constants.SHORT_TIME);
		CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));
		logout();
	}
}
