package at.smartshop.v5.pages;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.testng.Assert;

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
		Assert.assertTrue(foundation.isDisplayed(objText(accountPageData.get(0))));
		Assert.assertTrue(foundation.isDisplayed(objText(accountPageData.get(1))));
		if (requiredData.equals(actualData)) {
			String quickOptions = foundation.getText(LBL_QUICK_GERMAN);
			Assert.assertEquals(quickOptions, accountPageData.get(2));
		} else {
			Assert.assertTrue(foundation.isDisplayed(objAccountBalance(accountPageData.get(2))));
		}
		Assert.assertTrue(foundation.isDisplayed(objText(accountPageData.get(3))));
		Assert.assertTrue(foundation.isDisplayed(objText(accountPageData.get(4))));
		Assert.assertTrue(foundation.isDisplayed(objText(accountPageData.get(5))));
		Assert.assertTrue(foundation.isDisplayed(objText(accountPageData.get(6))));
		Assert.assertTrue(foundation.isDisplayed(objText(accountPageData.get(5))));
		Assert.assertTrue(foundation.isDisplayed(objText(accountPageData.get(7))));
		Assert.assertTrue(foundation.isDisplayed(objText(accountPageData.get(8))));

		// Verify Terms and Condition Page
		foundation.click(objText(accountPageData.get(9)));
		Assert.assertTrue(foundation.isDisplayed(objText(accountPageData.get(9))));
		Assert.assertEquals(foundation.getText(BTN_OK), accountPageData.get(10));
		foundation.click(BTN_OK);
	}
}
