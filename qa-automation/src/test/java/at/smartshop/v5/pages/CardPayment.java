package at.smartshop.v5.pages;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.testng.Assert;

import at.framework.ui.Foundation;
import at.smartshop.keys.Constants;

public class CardPayment {
	private Foundation foundation=new Foundation();
	
	public static final By BTN_CLOSE = By.id("cc-modal-close-id");
    public By objText(String text) {
		return By.xpath("//*[text()='"+text+"']");
	}
    
	public void verifyCardPaymentPageLanguage(String creditCardPage) {
        List<String> crditDebitPageData = Arrays.asList(creditCardPage.split(Constants.DELIMITER_TILD));
        foundation.waitforElement(objText(crditDebitPageData.get(0)), Constants.SHORT_TIME);        
        Assert.assertTrue(foundation.isDisplayed(objText(crditDebitPageData.get(0))));
        Assert.assertTrue(foundation.isDisplayed(objText(crditDebitPageData.get(1))));
	}
    
}
