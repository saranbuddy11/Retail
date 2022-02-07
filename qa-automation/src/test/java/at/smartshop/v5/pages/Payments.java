package at.smartshop.v5.pages;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;

import at.framework.generic.CustomisedAssert;
import at.framework.ui.Foundation;
import at.smartshop.keys.Constants;

public class Payments {
	private Foundation foundation = new Foundation();

	public static final By BTN_EMAIL_LOGIN = By.id("email-login-btn-id");

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
