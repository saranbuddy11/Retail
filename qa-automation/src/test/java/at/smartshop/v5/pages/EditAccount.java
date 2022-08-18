package at.smartshop.v5.pages;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;

import at.framework.generic.CustomisedAssert;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.keys.Constants;

public class EditAccount {
	private Foundation foundation = new Foundation();
	private TextBox textBox = new TextBox();

	public static final By BTN_EDIT_ACCOUNT = By.xpath("//*[text()='Edit Account']");
	public static final By TXT_FIRST_NAME = By.id("editaccount_firstName");
	public static final By TXT_LAST_NAME = By.id("editaccount_lastName");
	public static final By BTN_EDIT_NEXT = By.id("pin-reset-btn-go-id");
	public static final By TXT_EMAIL_ADDRESS = By.id("editaccount_emailAddress");
	public static final By BTN_CHANGE_PIN = By.xpath("//*[text()='Change PIN']");
	public static final By TXT_PIN_1 = By.id("pinInput0");
	public static final By TXT_PIN_2 = By.id("pinInput1");
	public static final By TXT_PIN_3 = By.id("pinInput2");
	public static final By TXT_PIN_4 = By.id("pinInput3");
	public static final By BTN_SAVE = By.id("editaccount-btn-go-id");
	public static final By BTN_SAVE_PIN = By.id("pin-reset-btn-go-id");
	public static final By BTN_CAMEL_CASE = By.xpath("//div[text()='abc']");
	public static final By BTN_DELETE = By.xpath("//*[text()='Del']");

	public void updateText(By obj, String text, String priviousText) {

		foundation.click(obj);
		for (int i = 0; i <= priviousText.length(); i++) {
			foundation.click(BTN_DELETE);
		}
		foundation.click(obj);
		textBox.enterKeypadText(text);
	}

	public By objText(String text) {
		return By.xpath("//*[normalize-space(text())='" + text + "']");
	}

	public void verifyEditAccountPageLanguage(String editAccountPage) {
		List<String> accountEditPageData = Arrays.asList(editAccountPage.split(Constants.DELIMITER_TILD));
		CustomisedAssert.assertTrue(foundation.isDisplayed(objText(accountEditPageData.get(0))));
		CustomisedAssert.assertTrue(foundation.isDisplayed(objText(accountEditPageData.get(1))));
		CustomisedAssert.assertTrue(foundation.isDisplayed(objText(accountEditPageData.get(2))));
		CustomisedAssert.assertTrue(foundation.isDisplayed(objText(accountEditPageData.get(3))));
		CustomisedAssert.assertTrue(foundation.isDisplayed(objText(accountEditPageData.get(4))));
		CustomisedAssert.assertTrue(foundation.isDisplayed(objText(accountEditPageData.get(5))));
		CustomisedAssert.assertTrue(foundation.isDisplayed(objText(accountEditPageData.get(6))));
	}

	public void verifyTimeOutPopLanguage(String timeOutPopup) {

		List<String> timeOutPopupData = Arrays.asList(timeOutPopup.split(Constants.DELIMITER_TILD));
		foundation.waitforElement(objText(timeOutPopupData.get(0)), Constants.LONG_TIME);
		CustomisedAssert.assertTrue(foundation.isDisplayed(objText(timeOutPopupData.get(0))));
		CustomisedAssert.assertEquals(foundation.getText(Order.POP_UP_TIMEOUT_YES), timeOutPopupData.get(3));
		foundation.click(Order.POP_UP_TIMEOUT_YES);
		foundation.waitforElement(objText(timeOutPopupData.get(0)), Constants.EXTRA_LONG_TIME);
		CustomisedAssert.assertTrue(foundation.isDisplayed(objText(timeOutPopupData.get(1))));
		CustomisedAssert.assertTrue(foundation.isDisplayed(objText(timeOutPopupData.get(2))));
	}
}
