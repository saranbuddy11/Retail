package at.smartshop.v5.pages;

import java.util.Arrays;
import java.util.List;

import org.apache.poi.ss.usermodel.Textbox;
import org.openqa.selenium.By;

import at.framework.generic.CustomisedAssert;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.database.columns.CNV5Device;
import at.smartshop.keys.Constants;

public class CreateAccount {
	public static final By CHK_TERMS = By.id("cb1");
	public static final By CHK_LABEL = By.xpath("//label[@class='checkmark']");
	public static final By LBL_SKIP_TO_END = By.id("updated-policy-back-btn-id");
	public static final By BTN_CLOSE = By.id("login-cred-modal-close-id");
	public static final By LBL_FINGERPRINT_HEADER = By.xpath("//h2[@class='fp-counter fixed-text']");
	public static final By LBL_SCAN_HEADER = By.xpath("//span[@data-reactid='.0.a.0.0.0.1.0.0.2.0.0.0']");
	public static final By LBL_FR_SCAN_HEADER = By.xpath("//h3[@data-reactid='.0.4.0.0.0.1.0.4.0.1.0']");
	public static final By LBL_FR_FINGERPRINT_HEADER = By.xpath("//span[@data-reactid='.0.4.0.0.0.1.0.4.0.1.0']");
	public static final By LBL_DUTCH_HEADER = By.xpath("//h2[@data-reactid='.0.4.0.0.0.1.0.0.0']");
	public static final By LBL_NORWEGIAN_HEADER = By.xpath("//h2[@data-reactid='.0.a.0.0.0.1.0.0.0']");
	public static final By LBL_NW_FINGERPRINT_HEADER = By.id("create-account-fpid-btn");
	public static final By TXT_BARCODE_GERMAN = By.xpath("//span[@data-reactid='.0.a.0.0.0.1.0.0.2.0.0.0']");
	public static final By LBL_H2 = By.xpath("//h2[@class='sub-instructions']");
	public static final By LBL_CREATE_ACCOUNT = By.xpath("//h2[@data-reactid='.0.a.0.0.0.1.0.1.1.1']");
	public static final By LBL_OPTION2 = By.xpath("//h2[@data-reactid='.0.b.0.0.0.1.0.1.1.1']");
	public static final By BTN_WILL_BE_BACK = By.id("login-cred-modal-close-id");
	public static final By LBL_OPTION3 = By.xpath("//h2[@data-reactid='.0.b.0.0.0.1.0.1.2.1']");
	public static final By LBL_OPTION1 = By.xpath("//h2[@data-reactid='.0.b.0.0.0.1.0.1.0.1']");
	public static final By LBL_HEADER = By.xpath("//h1[@data-reactid='.0.b.0.0.0.1.0.0']");
	public static final By LBL_WAITING_TO_COME_BACK = By.xpath("//h2[@data-reactid='.0.b.0.0.0.1.0.2.1']");
	public static final By LBL_WILL_BE_BACK = By.xpath("//button[@data-reactid='.0.b.0.0.0.1.0.2.2.0']");
	public static final By LBL_TITLE = By.xpath("//h1[@data-reactid='.0.b.0.0.0.0.1']");
	public static final By CONFIRM_BTN=By.id("policy-btn-go-id");
    public static final By EMAIL_BTN=By.xpath("//h2[@data-reactid='.0.b.0.0.0.1.0.0.2.1']");
    public static final By EMAIL_BTN_CREATE =By.id("emailInput-input");
    public static final By BTN_NEXT=By.id("emailinput-btn-go-id");
    public static final By BTN_PIN=By.id("pinInput0");
    public static final By BTN_PIN_NEXT=By.id("cre-pin-next-btn-go-id");
    public static final By TXT_FIRST_NAME=By.id("createaccount_firstName-input");
    public static final By TXT_LAST_NAME=By.id("createaccount_lastName-input");
    public static final By NEXT_BTN=By.id("cre-pin-back-btn-go-id");
    public static final By ADDLATER= By.xpath("(//BUTTON[@class='button-small button-primary'])[6]");
    public static final By COMPLETE_SETUP=By.id("gotoacct-button");
    public static final By CANCEL_BTN=By.xpath("//i[@data-reactid='.0.b.0.0.0.0.2.0']");
    
    
	private Foundation foundation = new Foundation();
	private TextBox textBox = new TextBox();

	public By objText(String text) {
		return By.xpath("//*[normalize-space(text())='" + text + "']");
	}
	
	public void verifyCreateAccoutnPageLanguage(String createAccount, String requiredData, String actualData) {
		List<String> createAccountPageData = Arrays.asList(createAccount.split(Constants.DELIMITER_TILD));
		CustomisedAssert.assertTrue(foundation.isDisplayed(objText(createAccountPageData.get(0))));
		CustomisedAssert.assertTrue(foundation.isDisplayed(objText(createAccountPageData.get(1))));
		CustomisedAssert.assertTrue(foundation.isDisplayed(objText(createAccountPageData.get(2))));
		foundation.click(LBL_SKIP_TO_END);
		foundation.objectFocus(CHK_LABEL);
		foundation.click(CHK_LABEL);
		foundation.click(objText(createAccountPageData.get(1)));

		foundation.waitforElement(LBL_TITLE, Constants.SHORT_TIME);
		String title = foundation.getText(LBL_TITLE);
		CustomisedAssert.assertEquals(title, createAccountPageData.get(3));

//		String header = foundation.getText(LBL_HEADER);
//		CustomisedAssert.assertEquals(header, createAccountPageData.get(4));
//
//		String optionOne = foundation.getText(LBL_OPTION1);
//		CustomisedAssert.assertEquals(optionOne, createAccountPageData.get(5));
//
//		String createAccountTxt = foundation.getText(LBL_OPTION2);
//		CustomisedAssert.assertTrue(createAccountTxt.equalsIgnoreCase(createAccountPageData.get(6)));
//
//		String optionThreeTxt = foundation.getText(LBL_OPTION3);
//		CustomisedAssert.assertEquals(optionThreeTxt, createAccountPageData.get(7));
//
//		String headerTxt = foundation.getText(LBL_WAITING_TO_COME_BACK);
//		CustomisedAssert.assertEquals(headerTxt, createAccountPageData.get(8));

//		foundation.click(objText(createAccountPageData.get(6)));
//		foundation.objectFocus(CreateAccount.CHK_LABEL);
//		foundation.click(CreateAccount.CHK_LABEL);
//		foundation.click(objText(createAccountPageData.get(1)));
//		
//		foundation.waitforElement(objText(createAccountPageData.get(8)), Constants.SHORT_TIME);
//		CustomisedAssert.assertTrue(foundation.isDisplayed(objText(createAccountPageData.get(3))));
//		CustomisedAssert.assertTrue(foundation.isDisplayed(objText(createAccountPageData.get(7))));
//		CustomisedAssert.assertTrue(foundation.isDisplayed(objText(createAccountPageData.get(8))));
//		CustomisedAssert.assertTrue(foundation.isDisplayed(objText(createAccountPageData.get(9))));
//				
//		foundation.click(objText(createAccountPageData.get(9)));
//		foundation.waitforElement(objText(createAccountPageData.get(4)), Constants.SHORT_TIME);
//		foundation.click(objText(createAccountPageData.get(5)));
//		if(requiredData.equals(actualData)) {
//		String barCode = foundation.getText(CreateAccount.TXT_BARCODE_GERMAN);
//		foundation.waitforElement(objText(createAccountPageData.get(10)), Constants.SHORT_TIME);
//		CustomisedAssert.assertEquals(barCode, createAccountPageData.get(11));
//		}else {
//		foundation.waitforElement(objText(createAccountPageData.get(11)), Constants.SHORT_TIME);
//		CustomisedAssert.assertTrue(foundation.isDisplayed(objText(createAccountPageData.get(11))));
//		CustomisedAssert.assertTrue(foundation.isDisplayed(objText(createAccountPageData.get(10))));
//		}
//		CustomisedAssert.assertTrue(foundation.isDisplayed(objText(createAccountPageData.get(3))));
//		

//		foundation.click(CreateAccount.BTN_CLOSE);

//		String willBeBackBtn = foundation.getText(LBL_WILL_BE_BACK);
//		CustomisedAssert.assertEquals(willBeBackBtn, createAccountPageData.get(9));

		foundation.click(BTN_WILL_BE_BACK);
		foundation.waitforElement(LandingPage.BTN_CREATE_ACCOUNT, Constants.SHORT_TIME);
	}
	
	public void createAccountInDevice(String email, String pin,String confirmpin,String fname,String lname) {
		foundation.waitforElement(LandingPage.BTN_CREATE_ACCOUNT, Constants.SHORT_TIME);
		foundation.click(LandingPage.BTN_CREATE_ACCOUNT);	
		foundation.click(LBL_SKIP_TO_END);
		foundation.objectFocus(CHK_LABEL);
		foundation.click(CHK_LABEL);
		foundation.threadWait(Constants.ONE_SECOND);
		foundation.click(CONFIRM_BTN);
		foundation.waitforElement(EMAIL_BTN, Constants.SHORT_TIME);
		foundation.click(EMAIL_BTN);
		foundation.waitforElement(EMAIL_BTN_CREATE, Constants.ONE_SECOND);
		foundation.click(EMAIL_BTN_CREATE);
		foundation.threadWait(Constants.ONE_SECOND);
		foundation.click(AccountLogin.BTN_CAMELCASE);
		textBox.enterKeypadText(email);
		foundation.click(BTN_NEXT);
		foundation.threadWait(Constants.SHORT_TIME);
		textBox.enterPin(pin);
		foundation.waitforElement(AccountLogin.BTN_PIN_NEXT, Constants.SHORT_TIME);
		foundation.click(BTN_PIN_NEXT);
		textBox.enterPin(confirmpin);
		foundation.waitforElement(AccountLogin.BTN_PIN_NEXT, Constants.SHORT_TIME);
		foundation.click(BTN_PIN_NEXT);
		foundation.waitforElement(TXT_FIRST_NAME, Constants.SHORT_TIME);
		foundation.click(TXT_FIRST_NAME);
		foundation.threadWait(Constants.ONE_SECOND);
		foundation.click(AccountLogin.BTN_CAMELCASE);
		textBox.enterKeypadText(fname);
		foundation.click(TXT_LAST_NAME);
		textBox.enterKeypadText(lname);
		foundation.waitforElement(NEXT_BTN, Constants.SHORT_TIME);
		foundation.click(NEXT_BTN);
		foundation.waitforElement(ADDLATER, Constants.SHORT_TIME);
		foundation.click(ADDLATER);
		foundation.waitforElement(COMPLETE_SETUP, Constants.ONE_SECOND);
		foundation.click(COMPLETE_SETUP);
		CustomisedAssert.assertTrue(foundation.isDisplayed(LandingPage.IMG_SEARCH_ICON));
		
	
	}
}
