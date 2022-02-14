package at.smartshop.v5.pages;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;

import at.framework.files.PropertyFile;
import at.framework.generic.CustomisedAssert;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;

public class AccountLogin {
	private Foundation foundation = new Foundation();
	private TextBox textBox = new TextBox();

	public static final By BTN_EMAIL_LOGIN = By.id("email-account-login-btn-id");
	public static final By TXT_EMAIL = By.id("emailLoginInput");
	public static final By BTN_ENTER = By.xpath("//*[text()='Enter']");
	public static final By BTN_NEXT = By.id("emaillogin-input-btn-go-id");
	public static final By LBL_ENTER_PIN_TITLE = By.className("input-label");
	public static final By BTN_CAMELCASE = By.xpath("//div[text()='abc']");
	public static final By BTN_PIN_NEXT = By.id("pin-back-btn-go-id");
	public static final By BTN_DRIVER_SIGNIN = By.id("login");
	public static final By LBL_PAGE_TITLE = By.xpath("//div[@id='accountModal']//h1");
	public static final By LBL_PAGE_HEADER = By.xpath("//div[@id='accountModal']//div[@class='account-content ']//h2");
	public static final By LBL_SCAN = By.xpath("//div[@class='u-g u-1-3 scan-container']/h2");
	public static final By LBL_FINGER_PRINT = By.xpath("//div[@class='u-g u-1-3 pull-left']/h2");
	public static final By BTN_I_DONT_HAVE_ACCOUNT = By.id("create-btn-id");
	public static final By LBL_EMAIL_HEADER = By.xpath("//label[@class='input-label']");
	public static final By BTN_EMAIl_BACK = By.id("emaillogin-input-btn-back-id");
	public static final By BTN_PIN_BACK = By.id("pin-input-btn-back-id");
	public static final By LBL_PIN_PAGE_TITLE = By.xpath("//h1[@data-reactid='.0.r.0.0.0.1']");
	public static final By LBL_PIN_HEADER = By.xpath("//label[@data-reactid='.0.r.0.0.1.0.0']");

	public static final By LBL_ACCOUNT_NOT_AVAILABLE = By.xpath("//*[@id='errorModal']//h1");
	public static final By LBL_GEO_GRAPHIC_LOCATION = By.xpath("//*[@id='errorModal']//h2");

	private PropertyFile propertyFile = new PropertyFile();

	public void login(String emailId, String pin) {
		foundation.click(BTN_CAMELCASE);
		textBox.enterKeypadText(emailId);
		foundation.click(BTN_ENTER);
		foundation.click(BTN_NEXT);
		foundation.threadWait(Constants.THREE_SECOND);
		textBox.enterPin(pin);
		foundation.click(BTN_PIN_NEXT);
		foundation.threadWait(Constants.ONE_SECOND);
	}

	public By objText(String text) {
		return By.xpath("//*[normalize-space(text())='" + text + "']");
	}

	public void verifyAccountLoginPageLanguage(String accountLoginPage) {
		List<String> loginPageData = Arrays.asList(accountLoginPage.split(Constants.DELIMITER_TILD));
		CustomisedAssert.assertEquals(foundation.getText(LBL_PAGE_TITLE), loginPageData.get(0));
		CustomisedAssert.assertEquals(foundation.getText(LBL_PAGE_HEADER), loginPageData.get(1));
		CustomisedAssert.assertEquals(foundation.getText(LBL_SCAN), loginPageData.get(2));
		CustomisedAssert.assertEquals(foundation.getText(LBL_FINGER_PRINT), loginPageData.get(3));
		CustomisedAssert.assertEquals(foundation.getText(BTN_EMAIL_LOGIN), loginPageData.get(4));
		CustomisedAssert.assertEquals(foundation.getText(BTN_I_DONT_HAVE_ACCOUNT), loginPageData.get(5));

		// Validating Account Login Email Page
		foundation.click(AccountLogin.BTN_EMAIL_LOGIN);
		foundation.waitforElement(AccountLogin.BTN_NEXT, Constants.SHORT_TIME);
		CustomisedAssert.assertEquals(foundation.getText(LBL_PAGE_TITLE), loginPageData.get(0));
		CustomisedAssert.assertEquals(foundation.getText(LBL_EMAIL_HEADER), loginPageData.get(6));
		CustomisedAssert.assertEquals(foundation.getText(BTN_EMAIl_BACK), loginPageData.get(7));
		CustomisedAssert.assertEquals(foundation.getText(BTN_NEXT), loginPageData.get(8));

		foundation.click(BTN_CAMELCASE);
		textBox.enterKeypadText(propertyFile.readPropertyFile(Configuration.V5_USER, FilePath.PROPERTY_CONFIG_FILE));
		foundation.click(BTN_ENTER);
		foundation.click(BTN_NEXT);

		// Validating Account Login PIN Page
		foundation.waitforElement(BTN_PIN_NEXT, Constants.SHORT_TIME);
		CustomisedAssert.assertEquals(foundation.getText(LBL_PIN_PAGE_TITLE), loginPageData.get(0));
		CustomisedAssert.assertEquals(foundation.getText(LBL_PIN_HEADER), loginPageData.get(9));
		CustomisedAssert.assertEquals(foundation.getText(BTN_PIN_BACK), loginPageData.get(7));
		CustomisedAssert.assertEquals(foundation.getText(BTN_PIN_NEXT), loginPageData.get(10));
	}

}
