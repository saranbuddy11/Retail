package at.smartshop.v5.pages;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.testng.Assert;

import at.framework.ui.Foundation;
import at.smartshop.keys.Constants;

public class FingerPrintPayment {
	public static final By LBL_FINGER_PRINT= By.xpath("//span[@data-reactid='.0.4.0.0.0.1.0.4.0.1.0']");
	private Foundation foundation=new Foundation();
		
    public By objText(String text) {
		return By.xpath("//*[text()='"+text+"']");
	}
    
	public void verifyFingerPrintPaymentPageLanguage(String fingerPrintPage) {
		List<String> fingerPrintPageData = Arrays.asList(fingerPrintPage.split(Constants.DELIMITER_TILD));

		Assert.assertTrue(foundation.isDisplayed(objText(fingerPrintPageData.get(0))));
		String fingerPrintTxt=foundation.getText(LBL_FINGER_PRINT);
		Assert.assertEquals(fingerPrintTxt, fingerPrintPageData.get(1));
		//Assert.assertTrue(foundation.isDisplayed(objText(fingerPrintPageData.get(1))));
		Assert.assertTrue(foundation.isDisplayed(objText(fingerPrintPageData.get(2))));
		foundation.click(objText(fingerPrintPageData.get(2)));
	}
    
}
