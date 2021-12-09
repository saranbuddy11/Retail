package at.smartshop.v5.pages;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.testng.Assert;

import at.framework.ui.Foundation;
import at.smartshop.keys.Constants;

public class CreateAccount {
	public static final By CHK_TERMS = By.id("cb1");
	public static final By CHK_LABEL = By.xpath("//label[@class='checkmark']");
	public static final By BTN_CLOSE = By.id("login-cred-modal-close-id");
	public static final By LBL_FINGERPRINT_HEADER = By.xpath("//h2[@class='fp-counter fixed-text']");
	public static final By LBL_SCAN_HEADER = By.xpath("//span[@data-reactid='.0.a.0.0.0.1.0.0.2.0.0.0']");
	public static final By LBL_FR_SCAN_HEADER = By.xpath("//h3[@data-reactid='.0.4.0.0.0.1.0.4.0.1.0']");
	public static final By LBL_FR_FINGERPRINT_HEADER = By.xpath("//span[@data-reactid='.0.4.0.0.0.1.0.4.0.1.0']");
	public static final By LBL_DUTCH_HEADER = By.xpath("//h2[@data-reactid='.0.4.0.0.0.1.0.0.0']");
	public static final By LBL_NORWEGIAN_HEADER = By.xpath("//h2[@data-reactid='.0.a.0.0.0.1.0.0.0']");
	public static final By LBL_NW_FINGERPRINT_HEADER = By.id("create-account-fpid-btn");
    public static final By TXT_BARCODE_GERMAN= By.xpath("//span[@data-reactid='.0.a.0.0.0.1.0.0.2.0.0.0']");
    public static final By LBL_H2= By.xpath("//h2[@class='sub-instructions']");
    public static final By LBL_CREATE_ACCOUNT= By.xpath("//h2[@data-reactid='.0.a.0.0.0.1.0.1.1.1']");
    public static final By LBL_OPTION2= By.xpath("//h2[@data-reactid='.0.a.0.0.0.1.0.1.1.1']");
    public static final By BTN_WILL_BE_BACK = By.id("will-be-back-btn-id");
    public static final By LBL_OPTION3 = By.xpath("//h2[@data-reactid='.0.a.0.0.0.1.0.1.2.1']");
    public static final By LBL_OPTION1= By.xpath("//h2[@data-reactid='.0.a.0.0.0.1.0.1.0.1']");
    public static final By LBL_HEADER= By.xpath("//*[@data-reactid='.0.b.0.0.0.1.0.0']");
    public static final By LBL_WAITING_TO_COME_BACK= By.xpath("//*[@data-reactid='.0.a.0.0.0.1.0.2.1']");
    public static final By LBL_WILL_BE_BACK= By.xpath("//*[@data-reactid='.0.a.0.0.0.1.0.2.2.0']");
   
	private Foundation foundation=new Foundation();
	
	
    public By objText(String text) {
		return By.xpath("//*[text()='"+text+"']");
	}
    
    public void verifyCreateAccoutnPageLanguage(String createAccount,String requiredData, String actualData) {
    	List<String> createAccountPageData = Arrays.asList(createAccount.split(Constants.DELIMITER_TILD));
		Assert.assertTrue(foundation.isDisplayed(objText(createAccountPageData.get(0))));
		Assert.assertTrue(foundation.isDisplayed(objText(createAccountPageData.get(1))));
		Assert.assertTrue(foundation.isDisplayed(objText(createAccountPageData.get(2))));
		foundation.objectFocus(CHK_LABEL);
		foundation.click(CHK_LABEL);
	
		foundation.click(objText(createAccountPageData.get(1)));
		foundation.waitforElement(LBL_HEADER,Constants.SHORT_TIME);
		
		String header=foundation.getText(LBL_HEADER);
		Assert.assertEquals(header, createAccountPageData.get(4));
		
		String optionOne=foundation.getText(LBL_OPTION1);
		Assert.assertEquals(optionOne, createAccountPageData.get(5));
		
		String createAccountTxt=foundation.getText(LBL_OPTION2);
		Assert.assertTrue(createAccountTxt.equalsIgnoreCase(createAccountPageData.get(6)));
		
		String optionThreeTxt=foundation.getText(LBL_OPTION3);
		Assert.assertEquals(optionThreeTxt, createAccountPageData.get(7));
		
		String headerTxt=foundation.getText(LBL_WAITING_TO_COME_BACK);
		Assert.assertEquals(headerTxt, createAccountPageData.get(8));
		
//		foundation.click(objText(createAccountPageData.get(6)));
//		foundation.objectFocus(CreateAccount.CHK_LABEL);
//		foundation.click(CreateAccount.CHK_LABEL);
//		foundation.click(objText(createAccountPageData.get(1)));
//		
//		foundation.waitforElement(objText(createAccountPageData.get(8)), Constants.SHORT_TIME);
//		Assert.assertTrue(foundation.isDisplayed(objText(createAccountPageData.get(3))));
//		Assert.assertTrue(foundation.isDisplayed(objText(createAccountPageData.get(7))));
//		Assert.assertTrue(foundation.isDisplayed(objText(createAccountPageData.get(8))));
//		Assert.assertTrue(foundation.isDisplayed(objText(createAccountPageData.get(9))));
//				
//		foundation.click(objText(createAccountPageData.get(9)));
//		foundation.waitforElement(objText(createAccountPageData.get(4)), Constants.SHORT_TIME);
//		foundation.click(objText(createAccountPageData.get(5)));
//		if(requiredData.equals(actualData)) {
//		String barCode = foundation.getText(CreateAccount.TXT_BARCODE_GERMAN);
//		foundation.waitforElement(objText(createAccountPageData.get(10)), Constants.SHORT_TIME);
//		Assert.assertEquals(barCode, createAccountPageData.get(11));
//		}else {
//		foundation.waitforElement(objText(createAccountPageData.get(11)), Constants.SHORT_TIME);
//		Assert.assertTrue(foundation.isDisplayed(objText(createAccountPageData.get(11))));
//		Assert.assertTrue(foundation.isDisplayed(objText(createAccountPageData.get(10))));
//		}
//		Assert.assertTrue(foundation.isDisplayed(objText(createAccountPageData.get(3))));
//		
		
//		foundation.click(CreateAccount.BTN_CLOSE);
		
		String willBeBackBtn=foundation.getText(LBL_WILL_BE_BACK);
		Assert.assertEquals(willBeBackBtn, createAccountPageData.get(9));
		
		foundation.click(BTN_WILL_BE_BACK);
		foundation.waitforElement(LandingPage.BTN_CREATE_ACCOUNT,Constants.SHORT_TIME);
    }
}
