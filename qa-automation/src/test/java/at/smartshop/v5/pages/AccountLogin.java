package at.smartshop.v5.pages;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;

import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.database.columns.CNLocationSummary;
import at.smartshop.database.columns.CNV5Device;
import at.smartshop.keys.Constants;

public class AccountLogin {
	
	private TextBox textBox=new TextBox();
	private Foundation foundation=new Foundation();

public static final By BTN_EMAIL_LOGIN= By.id("email-account-login-btn-id");
public static final By TXT_EMAIL= By.id("emailLoginInput");
public static final By BTN_ENTER=By.xpath("//*[@id='accountModal']/div[text()='Enter']");
//*[@id="accountModal"]/div/div/div[2]/div/div[1]/div[2]/div/div/div[21]/div
public static final By BTN_NEXT=By.id("emaillogin-input-btn-go-id");
public static final By LBL_ENTER_PIN_TITLE=By.className("input-label");
public static final By BTN_PIN_1=By.id("pinInput0");
public static final By BTN_PIN_2=By.id("pinInput1");
public static final By BTN_PIN_3=By.id("pinInput2");
public static final By BTN_PIN_4=By.id("pinInput3");

public void login(String emailId, String pin) {
	List<String> pinValue = Arrays.asList(pin.split(Constants.DELIMITER_TILD));
	
	enterEmail(emailId);
	enterPin(pin);
//	By q=By.xpath("//*[@id='accountModal']//div[text()='A']");
	//textBox.enterText(TXT_EMAIL, emailId);
	
	foundation.click(BTN_ENTER);
	foundation.click(BTN_NEXT);
	
	textBox.enterText(BTN_PIN_1, pinValue.get(0));
	textBox.enterText(BTN_PIN_2, pinValue.get(1));
	textBox.enterText(BTN_PIN_3, pinValue.get(2));
	textBox.enterText(BTN_PIN_4, pinValue.get(3));
	
	foundation.click(BTN_NEXT);
	//*[@id="accountModal"]/div/div/div[2]/div/div[1]/div[2]/div/div/div[12]/div
}

public void enterEmail(String email) {
	foundation.click(By.xpath("//*[@id='accountModal']//div[text()='abc']"));
	char[] charArray = email.toCharArray();
	for (char eachChar : charArray) {
		By obj=By.xpath("//*[@id='accountModal']//div[text()='"+ eachChar +"']");
		foundation.click(obj);
	}

	foundation.click(By.xpath("//*[@id='accountModal']//div[text()='Enter']"));
}

public void enterPin(String pin) {
	for (int i = 0; i < pin.length(); i++) {
		int number = Integer.parseInt(pin.substring(i, i + 1));
		foundation.click(By.xpath("//input[@value='"+number+"']"));
	}
}

//public static final By BTN_EDIT_ACCOUNT=By.id("touch-setup-btn-id");
//public static final By TXT_FIRST_NAME=By.id("editaccount_firstName");
//public static final By TXT_LAST_NAME=By.id("editaccount_lastName");
//public static final By BTN_EDIT_NEXT=By.id("editaccount-btn-go-id");
//public static final By TXT_EMAIL_ADDRESS=By.id("editaccount_emailAddress");
//public static final By BTN_CHANGE_PIN=By.className("button-small button-dark");
//public static final By BTN_SAVE=By.id("pin-reset-btn-go-id");
//public static final By BTN_PRIVACY=By.id("privacy-button");
//public static final By BTN_TERMS_CONDITION=By.id("tc-button");
//public static final By LBL_POLICY_TITLE=By.xpath("//*[@id='viewPolicyModal']/div/h2");
//public static final By BTN_OK=By.id("term-condition-btn-go-id");
//public static final By LBL_TERMS_CONDITION_TITLE=By.xpath("//*[@id='viewTCModal']/div/h2");
//public static final By LBL_BIOMETRIC=By.xpath("//*[@id='toggle']/div/label");
//public static final By CHK_BIOMETRIC=By.xpath("//*[@id='bpModal']/div/h2");
//public static final By BTN_CONFIRM=By.id("bpmodal-btn-go-id");


//id="pin-back-btn-go-id"
//class="input-label" 
//Enter your PIN
//
//class="key key_enter"
//id="touch-setup-btn-id" - edit 
//
//id="editaccount-btn-go-id"
//class="button-small button-dark" Change PIN
//
//
//
//id="pin-reset-btn-go-id"
//
//id="privacy-button"
//id="tc-button"
//
////*[@id="viewPolicyModal"]/div/h2
//
//id="term-condition-btn-go-id"
//
////*[@id="viewTCModal"]/div/h2
//id="term-condition-btn-go-id"
//
//
////*[@id="bpModal"]/div/h2
//id="bpmodal-btn-go-id"
////*[@id="toggle"]/div/label
//
////*[@id="accountModal"]/div/div/div[2]/div/span/label
}
