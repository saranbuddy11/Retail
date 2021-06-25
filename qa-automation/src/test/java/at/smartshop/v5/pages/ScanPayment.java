package at.smartshop.v5.pages;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.testng.Assert;

import at.framework.ui.Foundation;
import at.smartshop.keys.Constants;

public class ScanPayment {
	public static final By LBL_BARCODE_GERMAN = By.xpath("//h3[@data-reactid='.0.4.0.0.0.1.0.4.0.1.0']");
	private Foundation foundation=new Foundation();
	
    public By objText(String text) {
		return By.xpath("//*[text()='"+text+"']");
	}
    
	public void verifyScanPaymentPageLanguage(String scanPage, String requiredData, String actualData) {
		List<String> scanSetupPageData = Arrays.asList(scanPage.split(Constants.DELIMITER_TILD));
		if(requiredData.equals(actualData)) {
			String barcodeLogin = foundation.getText(LBL_BARCODE_GERMAN);
			Assert.assertEquals(barcodeLogin, scanSetupPageData.get(1));
		}else {
			Assert.assertTrue(foundation.isDisplayed(objText(scanSetupPageData.get(1))));
		}
		Assert.assertTrue(foundation.isDisplayed(objText(scanSetupPageData.get(0))));
		Assert.assertTrue(foundation.isDisplayed(objText(scanSetupPageData.get(2))));		
		foundation.click(objText(scanSetupPageData.get(2)));
		
	}
    
}
