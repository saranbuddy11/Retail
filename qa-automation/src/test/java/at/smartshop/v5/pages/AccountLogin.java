package at.smartshop.v5.pages;

import org.openqa.selenium.By;

import at.framework.ui.Foundation;
import at.framework.ui.TextBox;

public class AccountLogin {
	private Foundation foundation=new Foundation();

public static final By BTN_EMAIL_LOGIN= By.id("email-account-login-btn-id");
public static final By TXT_EMAIL= By.id("emailLoginInput");
public static final By BTN_ENTER=By.xpath("//*[@id='accountModal']//div[text()='Enter']");
public static final By BTN_NEXT=By.id("emaillogin-input-btn-go-id");
public static final By LBL_ENTER_PIN_TITLE=By.className("input-label");
public static final By BTN_CAMELCASE=By.xpath("//*[@id='accountModal']//div[text()='abc']");

public static final By BTN_PIN_NEXT=By.id("pin-back-btn-go-id");

public void login(String emailId, String pin) {
	enterEmail(emailId);
	enterPin(pin);
	foundation.click(BTN_PIN_NEXT);
}

public void enterEmail(String email) {
	foundation.click(BTN_CAMELCASE);
	char[] charArray = email.toCharArray();
	for (char eachChar : charArray) {
		By obj=By.xpath("//*[@id='accountModal']//div[text()='"+ eachChar +"']");
		foundation.click(obj);
	}

	foundation.click(BTN_ENTER);
	foundation.click(BTN_NEXT);
}

public void enterPin(String pin) {
	for (int i = 0; i < pin.length(); i++) {
		int number = Integer.parseInt(pin.substring(i, i + 1));
		foundation.click(By.xpath("//td[text()='"+ number+"']"));
	}
	
}

}
