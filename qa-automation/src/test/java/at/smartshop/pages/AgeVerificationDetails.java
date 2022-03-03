package at.smartshop.pages;

import java.util.List;

import org.openqa.selenium.By;

import at.framework.browser.Factory;
import at.framework.generic.CustomisedAssert;
import at.framework.ui.Foundation;
import at.smartshop.keys.Constants;

public class AgeVerificationDetails extends Factory {

	private Foundation foundation = new Foundation();

	public static final By TXT_AGE_VERIFICATION = By.xpath("//li[text()='Age Verification']");
	public static final By TXT_PROMPT_MSG = By.xpath("//div[text()='Confirm PIN Expiration']");
	public static final By TXT_PROMPT_CONTENT = By.xpath("//div[@class='ajs-content']");
	public static final By BTN_NO = By.xpath("//button[text()='No ']");
	public static final By BTN_YES = By.xpath("//button[text()='Yes']");

	public void verifyPinExpiration(String location, List<String> prompt) {
		foundation.click(By.xpath("//td[text()='" + location + "']//..//td/button[text()='" + prompt.get(0) + "']"));
		CustomisedAssert.assertTrue(foundation.isDisplayed(TXT_PROMPT_MSG));
		CustomisedAssert.assertTrue(foundation.isDisplayed(TXT_PROMPT_CONTENT));
		String value = foundation.getText(TXT_PROMPT_CONTENT);
		String[] actuals = value.split("\\s{2,6}");
		for (int i = 0; i < actuals.length; i++) {
			CustomisedAssert.assertTrue(actuals[i].contains(prompt.get(i + 1)));
		}
		CustomisedAssert.assertTrue(foundation.isDisplayed(BTN_NO));
		CustomisedAssert.assertTrue(foundation.isDisplayed(BTN_YES));
		foundation.click(BTN_NO);
		foundation.threadWait(Constants.TWO_SECOND);
	}

}
