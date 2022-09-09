package at.smartshop.v5.pages;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;

import at.framework.generic.CustomisedAssert;
import at.framework.ui.Foundation;
import at.smartshop.keys.Constants;

public class Payments {
	private Foundation foundation = new Foundation();

	public static final By ACCOUNT_EMAIL=By.xpath("//div[@data-reactid='.0.3.1.0.1.1.4']");
	public static final By EMAIL_ACCOUNT=By.xpath("//img[@data-reactid='.0.3.1.0.1.1.4.0']");
	public static final By EMAIL_lOGIN_BTN =By.id("email-login-btn-id");
	public static final By EMAIL_LOGIN_TXT=By.id("emailLoginInput");
	public static final By BTN_EMAIL_LOGIN = By.id("email-login-btn-id");
	public static final By LBL_INSUFFICIENT_FUND = By.xpath("//h1[@data-reactid='.0.q.0.0.1']");

	public By objText(String text) {
		return By.xpath("//*[normalize-space(text())='" + text + "']");
	}

	public void verifyPaymentPageLanguage(String paymentPage) {
		List<String> paymentPageData = Arrays.asList(paymentPage.split(Constants.DELIMITER_TILD));
		CustomisedAssert.assertTrue(foundation.isDisplayed(objText(paymentPageData.get(0))));
		CustomisedAssert.assertTrue(foundation.isDisplayed(objText(paymentPageData.get(1))));
		CustomisedAssert.assertTrue(foundation.isDisplayed(objText(paymentPageData.get(2))));
		CustomisedAssert.assertTrue(foundation.isDisplayed(objText(paymentPageData.get(3))));

		foundation.click(objText(paymentPageData.get(3)));
		CustomisedAssert.assertTrue(foundation.isDisplayed(objText(paymentPageData.get(4))));
		CustomisedAssert.assertTrue(foundation.isDisplayed(objText(paymentPageData.get(5))));
	}

}
