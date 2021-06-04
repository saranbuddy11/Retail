package at.smartshop.v5.pages;

import org.openqa.selenium.By;

import at.framework.ui.Foundation;

public class AccountLogin {

private Foundation foundation=new Foundation();

public static final By BTN_EMAIL_LOGIN= By.id("email-account-login-btn-id");
public static final By TXT_EMAIL= By.id("emailLoginInput");
public static final By BTN_ENTER=By.xpath("//*[@id='accountModal']/div[text()='Enter']");
public static final By BTN_NEXT=By.id("emaillogin-input-btn-go-id");
public static final By LBL_ENTER_PIN_TITLE=By.className("input-label");
public static final By BTN_PIN_1=By.id("pinInput0");
public static final By BTN_PIN_2=By.id("pinInput1");
public static final By BTN_PIN_3=By.id("pinInput2");
public static final By BTN_PIN_4=By.id("pinInput3");


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

}

