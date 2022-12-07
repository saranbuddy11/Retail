package at.smartshop.v5.pages;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;

import at.framework.browser.Browser;
import at.framework.files.PropertyFile;
import at.framework.generic.CustomisedAssert;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;

public class Payments {
	private Foundation foundation = new Foundation();
	private Browser browser = new Browser();
	private PropertyFile propertyFile = new PropertyFile();
	private TextBox textBox = new TextBox();
	private Order order = new Order();

//	public static final By ACCOUNT_EMAIL = By.xpath("//div[@data-reactid='.0.3.1.0.1.1.4']");
//	public static final By EMAIL_ACCOUNT = By.xpath("//img[@data-reactid='.0.3.1.0.1.1.4.0']");
	public static final By ACCOUNT_EMAIL = By.xpath("//div[@data-reactid='.0.3.1.0.1.1.4']");
	public static final By EMAIL_ACCOUNT = By.xpath("//img[@data-reactid='.0.3.1.0.1.1.2.0']");
	public static final By EMAIL = By.xpath("//div[@data-reactid='.0.3.1.0.1.1.2']");
	public static final By EMAIL_lOGIN_BTN = By.id("email-login-btn-id");
	public static final By BTN_NEXT = By.id("emaillogin-input-btn-go-id");
	public static final By EMAIL_LOGIN_TXT = By.id("emailLoginInput");
	public static final By BTN_EMAIL_LOGIN = By.id("email-login-btn-id");
	public static final By LBL_INSUFFICIENT_FUND = By.xpath("//h1[@data-reactid='.0.q.0.0.1']");
	public static final By EMAIL_ACCOUNT_BTN = By.xpath("//div[@data-reactid='.0.3.1.0.1.1.2']");
	public static final By BTN_EMAIL_ACCOUNT=By.xpath("//h3[@data-reactid='.0.3.1.0.1.1.4.1']");
	public static final By BTN_EMAIL = By.xpath("//h3[@data-reactid='.0.3.1.0.1.1.2.1']");
	public static final By BTN_TAB = By.xpath("//div[@data-reactid='.0.0.0.0.0']");

	public By objText(String text) {
		return By.xpath("//*[normalize-space(text())='" + text + "']");
	}

	public void verifyPaymentPageLanguage(String paymentPage) {
		List<String> paymentPageData = Arrays.asList(paymentPage.split(Constants.DELIMITER_TILD));
		CustomisedAssert.assertTrue(foundation.isDisplayed(objText(paymentPageData.get(0))));
		CustomisedAssert.assertTrue(foundation.isDisplayed(objText(paymentPageData.get(1))));
		CustomisedAssert.assertTrue(foundation.isDisplayed(objText(paymentPageData.get(2))));
		CustomisedAssert.assertTrue(foundation.isDisplayed(objText(paymentPageData.get(3))));

		foundation.click(objText(paymentPageData.get(3)));
		CustomisedAssert.assertTrue(foundation.isDisplayed(objText(paymentPageData.get(4))));
		CustomisedAssert.assertTrue(foundation.isDisplayed(objText(paymentPageData.get(5))));
	}

	/**
	 * launch v5 device and do transaction
	 * 
	 * @param product
	 * @param email
	 * @param pin
	 * @param orderpage
	 * @param payment
	 */
	public void launchV5DeviceAndDoTransaction(String product, String email, String pin, String orderpage,
			String payment) {
		foundation.threadWait(Constants.THREE_SECOND);
		browser.launch(Constants.REMOTE, Constants.CHROME);
		browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
		CustomisedAssert.assertTrue(foundation.isDisplayed(LandingPage.IMG_SEARCH_ICON));
		foundation.click(LandingPage.IMG_SEARCH_ICON);
		foundation.threadWait(Constants.THREE_SECOND);
		foundation.click(AccountLogin.BTN_CAMELCASE);
		textBox.enterKeypadText(product);
		foundation.threadWait(Constants.SHORT_TIME);
		foundation.click(ProductSearch.BTN_PRODUCT);
		CustomisedAssert.assertTrue(foundation.isDisplayed(order.objText(orderpage)));
//		foundation.click(Payments.BTN_EMAIL);
		foundation.waitforElement(Payments.BTN_EMAIL_ACCOUNT, Constants.SHORT_TIME);
		foundation.click(Payments.BTN_EMAIL_ACCOUNT);
		foundation.threadWait(Constants.SHORT_TIME);
		foundation.click(Payments.EMAIL_lOGIN_BTN);
		foundation.threadWait(Constants.SHORT_TIME);
		foundation.click(AccountLogin.BTN_CAMELCASE);
		textBox.enterKeypadText(email);
		foundation.click(AccountLogin.BTN_NEXT);
		foundation.waitforElement(AccountLogin.BTN_PIN_NEXT, Constants.SHORT_TIME);
		foundation.threadWait(Constants.SHORT_TIME);
		textBox.enterPin(pin);
		foundation.click(AccountLogin.BTN_PIN_NEXT);
		foundation.threadWait(Constants.SHORT_TIME);
		CustomisedAssert.assertTrue(foundation.isDisplayed(order.objText(payment)));
		foundation.threadWait(Constants.THREE_SECOND);
		browser.close();
	}

//	public void launchV5DeviceAnd(String product,String email,String pin,String orderpage,String payment) {
//		foundation.threadWait(Constants.THREE_SECOND);
//		browser.launch(Constants.REMOTE, Constants.CHROME);
//		browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
//		CustomisedAssert.assertTrue(foundation.isDisplayed(LandingPage.IMG_SEARCH_ICON));
//		foundation.doubleClick(BTN_TAB);
//		foundation.doubleClick(BTN_TAB);
//		foundation.click(BTN_TAB);
//		
//	}

	/**
	 * Do Payment using GMA verified account to purchase
	 * 
	 * @param email
	 * @param pin
	 * @param status
	 */
	public void paymentUsingGMAVerifiedAccount(String email, String pin, String status) {
		foundation.click(Order.LBL_MY_ACCOUNT);
		foundation.waitforElement(EMAIL_lOGIN_BTN, Constants.SHORT_TIME);
		foundation.click(EMAIL_lOGIN_BTN);
		foundation.waitforElement(EMAIL_LOGIN_TXT, Constants.SHORT_TIME);
		foundation.click(BTN_NEXT);
		foundation.threadWait(Constants.THREE_SECOND);
		foundation.click(AccountLogin.BTN_CAMELCASE);
		textBox.enterKeypadText(email);
		foundation.click(AccountLogin.BTN_NEXT);
		foundation.waitforElement(AccountLogin.BTN_PIN_NEXT, Constants.SHORT_TIME);
		foundation.threadWait(Constants.THREE_SECOND);
		textBox.enterPin(pin);
		foundation.click(AccountLogin.BTN_PIN_NEXT);
		CustomisedAssert.assertTrue(foundation.isDisplayed(order.objText(status)));
		foundation.waitforElement(LandingPage.IMG_SEARCH_ICON, Constants.SHORT_TIME);
	}
}
