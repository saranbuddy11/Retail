package at.smartshop.v5.pages;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.testng.Assert;

import at.framework.ui.Foundation;
import at.smartshop.keys.Constants;

public class FundAccount {
	private Foundation foundation = new Foundation();
    public By objText(String text) {
		return By.xpath("//*[text()='"+text+"']");
	}
    
    public void verifyFundAccountScreenLanguage(String fundAccountPage) {
    	List<String> fundPageData = Arrays.asList(fundAccountPage.split(Constants.DELIMITER_TILD));
		Assert.assertTrue(foundation.isDisplayed(objText(fundPageData.get(0))));
		Assert.assertTrue(foundation.isDisplayed(objText(fundPageData.get(1))));
		Assert.assertTrue(foundation.isDisplayed(objText(fundPageData.get(2))));
		foundation.click(objText(fundPageData.get(3)));
		
		//Verifying Fund with card page details page
		Assert.assertTrue(foundation.isDisplayed(objText(fundPageData.get(4))));
		Assert.assertTrue(foundation.isDisplayed(objText(fundPageData.get(5))));
		Assert.assertTrue(foundation.isDisplayed(objText(fundPageData.get(6))));
		
		foundation.click(objText(fundPageData.get(6)));
		foundation.click(objText(fundPageData.get(2)));
    }
}
