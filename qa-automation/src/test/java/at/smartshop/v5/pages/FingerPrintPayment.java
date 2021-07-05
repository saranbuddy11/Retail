package at.smartshop.v5.pages;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.testng.Assert;

import at.framework.ui.Foundation;
import at.smartshop.keys.Constants;

public class FingerPrintPayment {
	private Foundation foundation=new Foundation();
	
    public By objText(String text) {
		return By.xpath("//*[text()='"+text+"']");
	}
    
	public void verifyFingerPrintPaymentPageLanguage(String fingerPrintPage) {
		List<String> fingerPrintPageData = Arrays.asList(fingerPrintPage.split(Constants.DELIMITER_TILD));
		

		Assert.assertTrue(foundation.isDisplayed(objText(fingerPrintPageData.get(0))));
		Assert.assertTrue(foundation.isDisplayed(objText(fingerPrintPageData.get(1))));
		Assert.assertTrue(foundation.isDisplayed(objText(fingerPrintPageData.get(2))));
		foundation.click(objText(fingerPrintPageData.get(2)));
	}
    
}
