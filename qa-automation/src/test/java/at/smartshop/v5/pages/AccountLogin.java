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
	public static final By BTN_DRIVER_SIGNIN = By.id("login");
	
	public static final By LBL_PAGE_TITLE = By.xpath("//div[@id='accountModal']//h1");
	public static final By LBL_PAGE_HEADER= By.xpath("//div[@id='accountModal']//div[@class='account-content ']//h2");
	public static final By LBL_SCAN = By.xpath("//div[@class='u-g u-1-3 scan-container']/h2");
	public static final By LBL_FINGER_PRINT = By.xpath("//div[@class='u-g u-1-3 pull-left']/h2");
	public static final By BTN_I_DONT_HAVE_ACCOUNT = By.id("create-btn-id");
	
	public static final By LBL_EMAIL_HEADER = By.xpath("//label[@class='input-label']");
	public static final By BTN_EMAIl_BACK = By.id("emaillogin-input-btn-back-id");
	public static final By BTN_PIN_BACK = By.id("pin-input-btn-back-id");
	public static final By LBL_PIN_PAGE_TITLE = By.xpath("//h1[@data-reactid='.0.q.0.0.0.1']");
	public static final By LBL_PIN_HEADER = By.xpath("//label[@data-reactid='.0.q.0.0.1.0.0']");

	public void login(String emailId, String pin) {
		foundation.click(BTN_CAMELCASE);
		textBox.enterKeypadText(emailId);
		foundation.click(BTN_ENTER);
        foundation.click(BTN_NEXT);
        textBox.enterPin(pin);
		foundation.click(BTN_PIN_NEXT);
	}
}
