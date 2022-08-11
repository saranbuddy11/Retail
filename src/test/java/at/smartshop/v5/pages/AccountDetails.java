package at.smartshop.v5.pages;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;

import at.framework.generic.CustomisedAssert;
import at.framework.ui.Foundation;
import at.smartshop.keys.Constants;

public class AccountDetails {

	public static final By BTN_OK = By.id("term-condition-btn-go-id");
	public static final By LBL_QUICK_GERMAN = By.xpath("//h2[@data-reactid='.0.4.0.0.0.1.0.0.0']");
	public static final By BTN_FUND_CASH = By.xpath("//span[text()='Fund with cash']");
	private Foundation foundation = new Foundation();

	public By objText(String text) {
		return By.xpath("//*[normalize-space(text())='" + text + "']");
	}

	public By objAccountBalance(String text) {
		return By.xpath("//h2[normalize-space(text())='" + text + "']");
	}

	public void verifyAccountDetailsPageLanguage(String accountDetailsPage, String requiredData, String actualData) {
		List<String> accountPageData = Arrays.asList(accountDetailsPage.split(Constants.DELIMITER_TILD));
		CustomisedAssert.assertTrue(foundation.isDisplayed(objText(accountPageData.get(0))));
		CustomisedAssert.assertTrue(foundation.isDisplayed(objText(accountPageData.get(1))));
		if (requiredData.equals(actualData)) {
			String quickOptions = foundation.getText(LBL_QUICK_GERMAN);
			CustomisedAssert.assertEquals(quickOptions, accountPageData.get(2));
		} else {
			CustomisedAssert.assertTrue(foundation.isDisplayed(objAccountBalance(accountPageData.get(2))));
		}
		CustomisedAssert.assertTrue(foundation.isDisplayed(objText(accountPageData.get(3))));
		CustomisedAssert.assertTrue(foundation.isDisplayed(objText(accountPageData.get(4))));
		CustomisedAssert.assertTrue(foundation.isDisplayed(objText(accountPageData.get(5))));
		CustomisedAssert.assertTrue(foundation.isDisplayed(objText(accountPageData.get(6))));
		CustomisedAssert.assertTrue(foundation.isDisplayed(objText(accountPageData.get(5))));
		CustomisedAssert.assertTrue(foundation.isDisplayed(objText(accountPageData.get(7))));
		CustomisedAssert.assertTrue(foundation.isDisplayed(objText(accountPageData.get(8))));

		// Verify Terms and Condition Page
		foundation.click(objText(accountPageData.get(9)));
		CustomisedAssert.assertTrue(foundation.isDisplayed(objText(accountPageData.get(9))));
		CustomisedAssert.assertEquals(foundation.getText(BTN_OK), accountPageData.get(10));
		foundation.click(BTN_OK);
	}
}
