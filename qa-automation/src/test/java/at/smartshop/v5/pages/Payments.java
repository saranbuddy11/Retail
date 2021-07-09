package at.smartshop.v5.pages;

import java.util.Arrays;
import java.util.List;
import org.openqa.selenium.By;
import org.testng.Assert;
import at.framework.ui.Foundation;
import at.smartshop.keys.Constants;

public class Payments {
	private Foundation foundation = new Foundation();

	public By objText(String text) {
		return By.xpath("//*[text()='" + text + "']");
	}

	public void verifyPaymentPageLanguage(String paymentPage) {
		List<String> paymentPageData = Arrays.asList(paymentPage.split(Constants.DELIMITER_TILD));
		Assert.assertTrue(foundation.isDisplayed(objText(paymentPageData.get(0))));
		Assert.assertTrue(foundation.isDisplayed(objText(paymentPageData.get(1))));
		Assert.assertTrue(foundation.isDisplayed(objText(paymentPageData.get(2))));
		Assert.assertTrue(foundation.isDisplayed(objText(paymentPageData.get(3))));

		foundation.click(objText(paymentPageData.get(3)));
		Assert.assertTrue(foundation.isDisplayed(objText(paymentPageData.get(4))));
		Assert.assertTrue(foundation.isDisplayed(objText(paymentPageData.get(5))));
	}

}
