package at.smartshop.v5.pages;



import org.openqa.selenium.By;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;




public class AccountLogin {
	private Foundation foundation = new Foundation();
	private TextBox textBox=new TextBox();

	public static final By BTN_EMAIL_LOGIN = By.id("email-account-login-btn-id");
	public static final By TXT_EMAIL = By.id("emailLoginInput");
	public static final By BTN_ENTER = By.xpath("//*[@id='accountModal']//div[text()='Enter']");
	public static final By BTN_NEXT = By.id("emaillogin-input-btn-go-id");
	public static final By LBL_ENTER_PIN_TITLE = By.className("input-label");
	public static final By BTN_CAMELCASE = By.xpath("//div[text()='abc']");
	public static final By BTN_PIN_NEXT = By.id("pin-back-btn-go-id");	

	public void login(String emailId, String pin) {
		foundation.click(BTN_CAMELCASE);
		textBox.enterKeypadText(emailId);
		foundation.click(BTN_ENTER);
        foundation.click(BTN_NEXT);
        textBox.enterPin(pin);
		foundation.click(BTN_PIN_NEXT);
	}

}
