package at.smartshop.v5.pages;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;

import at.framework.generic.CustomisedAssert;
import at.framework.ui.Foundation;
import at.smartshop.keys.Constants;

public class FundAccount {

	private Foundation foundation = new Foundation();

	public By objText(String text) {
		return By.xpath("//*[normalize-space(text())='" + text + "']");
	}

	public void verifyFundAccountScreenLanguage(String fundAccountPage) {
		List<String> fundPageData = Arrays.asList(fundAccountPage.split(Constants.DELIMITER_TILD));
		CustomisedAssert.assertTrue(foundation.isDisplayed(objText(fundPageData.get(0))));
		CustomisedAssert.assertTrue(foundation.isDisplayed(objText(fundPageData.get(1))));
		CustomisedAssert.assertTrue(foundation.isDisplayed(objText(fundPageData.get(2))));
		foundation.click(objText(fundPageData.get(3)));

		// Verifying Fund with card page details page
		// CustomisedAssert.assertTrue(foundation.isDisplayed(objText(fundPageData.get(4))));
		// CustomisedAssert.assertTrue(foundation.isDisplayed(objText(fundPageData.get(5))));
		// CustomisedAssert.assertTrue(foundation.isDisplayed(objText(fundPageData.get(6))));

		// foundation.click(objText(fundPageData.get(6)));
		foundation.threadWait(Constants.TWO_SECOND);
		foundation.click(objText(fundPageData.get(2)));
	}
}
