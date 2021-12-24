package at.smartshop.v5.pages;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.testng.Assert;

import at.framework.ui.Foundation;
import at.smartshop.keys.Constants;

public class ChangePin {
	private Foundation foundation = new Foundation();

	public By objText(String text) {
		return By.xpath("//*[normalize-space(text())='" + text + "']");
	}

	public void verifyChangePinPageLanguage(String changePinPage) {
		List<String> changePinPageData = Arrays.asList(changePinPage.split(Constants.DELIMITER_TILD));
		Assert.assertTrue(foundation.isDisplayed(objText(changePinPageData.get(0))));
		Assert.assertTrue(foundation.isDisplayed(objText(changePinPageData.get(1))));
		Assert.assertTrue(foundation.isDisplayed(objText(changePinPageData.get(2))));
		foundation.click(objText(changePinPageData.get(2)));
	}
}
