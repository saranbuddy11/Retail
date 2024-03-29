package at.smartshop.v5.pages;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;

import at.framework.files.PropertyFile;
import at.framework.generic.CustomisedAssert;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.database.columns.CNV5Device;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;

public class AccountLogin {
	private Foundation foundation = new Foundation();
	private TextBox textBox = new TextBox();
	private Order order = new Order();

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
	public static final By LBL_Email = By.xpath("//div[@data-reactid='.0.7.0.0.1.0.0.0.1']");
	public static final By LBL_PIN_PAGE_TITLE = By.xpath("//h1[@data-reactid='.0.r.0.0.0.1']");
	public static final By LBL_PIN_HEADER = By.xpath("//label[@data-reactid='.0.r.0.0.1.0.0']");
	public static final By LBL_ACCOUNT_NOT_AVAILABLE = By.xpath("//*[@id='errorModal']//h1");
	public static final By LBL_GEO_GRAPHIC_LOCATION = By.xpath("//*[@id='errorModal']//h2");
	public static final By LBL_CONSUMER_NAME = By.xpath("//h1[@data-reactid='.0.4.0.0.0.0.1']");
	public static final By LBL_SUBSIDY = By.xpath("//span[text()='Subsidy']");
	public static final By LBL_ACCOUNT = By.xpath("//span[text()='Account']");
	public static final By LBL_ACCOUNT_1 = By.xpath("//h2[text()='Account']");
	public static final By LBL_ACC_BAL = By.xpath("//h2[text()='Account Balance']");
	public static final By LBL_BALANCE = By.xpath("//button[@class='active']/h3");
	public static final By LBL_BALANCE_1 = By.xpath("//div[@class='account-balance']");
	public static final By TAB_BALANCE = By.xpath("//button[@class='active']");
	public static final By BTN_PROFILE_CLOSE = By.xpath("//i[@data-reactid='.0.4.0.0.0.0.2.0']");
	public static final By SUBSIDY_BALANCE=By.xpath("//div[@data-reactid='.0.4.0.0.0.1.1.0:1.0.1']");
	public static final By CANCEL_BUTTON=By.xpath("//i[@class='ion-close-circled']");
	public static final By SUBSIDY_BALANCE_TAB=By.xpath("//button[@data-reactid='.0.4.0.0.0.1.0.1']");

	private PropertyFile propertyFile = new PropertyFile();

	public void login(String emailId, String pin) {
		foundation.click(BTN_CAMELCASE);
		textBox.enterKeypadText(emailId);
		foundation.click(BTN_ENTER);
		foundation.threadWait(Constants.THREE_SECOND);
		foundation.click(BTN_NEXT);
		foundation.threadWait(Constants.THREE_SECOND);
		textBox.enterPin(pin);
		foundation.click(BTN_PIN_NEXT);
		foundation.threadWait(Constants.THREE_SECOND);
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
		foundation.threadWait(Constants.THREE_SECOND);
		CustomisedAssert.assertEquals(foundation.getText(LBL_PIN_PAGE_TITLE), loginPageData.get(0));
		CustomisedAssert.assertEquals(foundation.getText(LBL_PIN_HEADER), loginPageData.get(9));
		CustomisedAssert.assertEquals(foundation.getText(BTN_PIN_BACK), loginPageData.get(7));
		CustomisedAssert.assertEquals(foundation.getText(BTN_PIN_NEXT), loginPageData.get(10));
	}

	public void verifyConsumerAccountLogin(String email, String pin, String consumerName, String expectedBal,
			String typeBalance, String borderColor) {
		foundation.click(LandingPage.BTN_LOGIN);
		foundation.click(BTN_EMAIL_LOGIN);
		login(email, pin);
		String text = foundation.getText(LBL_CONSUMER_NAME);
		CustomisedAssert.assertTrue(text.contains(consumerName));
		CustomisedAssert.assertTrue(foundation.isDisplayed(LBL_ACCOUNT));
		String color = foundation.getBorderColor(TAB_BALANCE);
		CustomisedAssert.assertEquals(borderColor, color);
		text = foundation.getText(LBL_BALANCE);
		CustomisedAssert.assertEquals(text, expectedBal);
		foundation.click(LBL_SUBSIDY);
		CustomisedAssert.assertTrue(foundation.isDisplayed(LBL_SUBSIDY));
		color = foundation.getBorderColor(TAB_BALANCE);
		CustomisedAssert.assertEquals(borderColor, color);
		text = foundation.getText(LBL_BALANCE);
		CustomisedAssert.assertEquals(text, typeBalance);
		foundation.click(BTN_PROFILE_CLOSE);
		CustomisedAssert.assertTrue(foundation.isDisplayed(LandingPage.IMG_SEARCH_ICON));
	}

	public String productPurchase(String product, List<String> actuals, String orderPage, String mail, String pin,
			String expectedBal, String payment) {
		if (expectedBal.contains(Constants.DOLLAR_SYMBOL)) {
			expectedBal = expectedBal.split(Constants.DOLLAR)[1];
			expectedBal = expectedBal.replace(".0", "");
		}
		foundation.click(LandingPage.IMG_ORDER_SEARCH_ICON);
		textBox.enterKeypadText(product);
		foundation.click(ProductSearch.BTN_PRODUCT);
		CustomisedAssert.assertEquals(foundation.getText(Order.TXT_HEADER), actuals.get(0));
		CustomisedAssert.assertEquals(foundation.getText(Order.TXT_PRODUCT), actuals.get(1));

		// Verify product purchase Transaction with balance
		foundation.objectFocus(order.objText(orderPage));
		foundation.click(order.objText(orderPage));
		foundation.waitforElement(Payments.BTN_EMAIL_LOGIN, Constants.SHORT_TIME);
		String balanceDue = foundation.getText(Order.LBL_CHARGE_AMT).split(Constants.DOLLAR)[1];
		double bal = Double.valueOf(expectedBal) - Double.valueOf(balanceDue);
		String accBalance = "$" + String.valueOf(bal);
		foundation.click(Payments.BTN_EMAIL_LOGIN);
		foundation.click(AccountLogin.BTN_CAMELCASE);
		textBox.enterKeypadText(mail);
		foundation.click(AccountLogin.BTN_NEXT);
		foundation.waitforElement(AccountLogin.BTN_PIN_NEXT, Constants.SHORT_TIME);
		textBox.enterPin(pin);
		foundation.click(AccountLogin.BTN_PIN_NEXT);
		CustomisedAssert.assertTrue(foundation.isDisplayed(order.objText(payment)));
		foundation.waitforElement(LandingPage.IMG_SEARCH_ICON, Constants.SHORT_TIME);
		return accBalance;
	}

	public void verifyAccountDetails(String mail, String pin, String expectedBal) {
		if (!expectedBal.contains("$"))
			expectedBal = "$" + expectedBal + ".00";
		else
			expectedBal = expectedBal + "0";
		foundation.click(LandingPage.BTN_LOGIN);
		foundation.click(BTN_EMAIL_LOGIN);
		login(mail, pin);
		CustomisedAssert.assertTrue(foundation.isDisplayed(AccountLogin.LBL_ACCOUNT));
		String balance = foundation.getText(AccountLogin.LBL_BALANCE);
		CustomisedAssert.assertEquals(balance, expectedBal);
		foundation.click(BTN_PROFILE_CLOSE);
		CustomisedAssert.assertTrue(foundation.isDisplayed(LandingPage.IMG_SEARCH_ICON));
	}

	public void verifySubsidyDetails(String mail, String pin, String expectedBal) {
		if (!expectedBal.contains("$"))
			expectedBal = "$" + expectedBal + ".00";
		else
			expectedBal = expectedBal + "0";
		foundation.click(LandingPage.BTN_LOGIN);
		foundation.click(BTN_EMAIL_LOGIN);
		login(mail, pin);
		CustomisedAssert.assertTrue(foundation.isDisplayed(AccountLogin.LBL_ACCOUNT));
		foundation.click(LBL_SUBSIDY);
		CustomisedAssert.assertTrue(foundation.isDisplayed(LBL_SUBSIDY));
		String balance = foundation.getText(LBL_BALANCE);
		CustomisedAssert.assertEquals(balance, expectedBal);
		foundation.click(BTN_PROFILE_CLOSE);
		CustomisedAssert.assertTrue(foundation.isDisplayed(LandingPage.IMG_SEARCH_ICON));
	}
	
	/**
	 * verify account balance in v5 device
	 * @param email
	 * @param pin
	 * @param consumerbalance
	 * @param subsidybalance
	 */
	public void verifyAccountBalanceInV5(String email,String pin,String consumerbalance,String subsidybalance) {
		CustomisedAssert.assertTrue(foundation.isDisplayed(LandingPage.LBL_ACCOUNT_LOGIN));
		foundation.waitforElementToBeVisible(LandingPage.LBL_ACCOUNT_LOGIN, Constants.SHORT_TIME);
		foundation.click(LandingPage.LBL_ACCOUNT_LOGIN);
		foundation.waitforElementToBeVisible(AccountLogin.BTN_EMAIL_LOGIN, Constants.THREE_SECOND);
		foundation.click(AccountLogin.BTN_EMAIL_LOGIN);
		foundation.click(AccountLogin.BTN_CAMELCASE);
		textBox.enterKeypadText(email);
		foundation.click(AccountLogin.BTN_NEXT);
		foundation.waitforElement(AccountLogin.BTN_PIN_NEXT, Constants.SHORT_TIME);
		foundation.threadWait(Constants.TWO_SECOND);
		textBox.enterPin(pin);
		foundation.click(AccountLogin.BTN_PIN_NEXT);
		foundation.waitforElementToBeVisible(AccountLogin.LBL_CONSUMER_NAME, Constants.SHORT_TIME);
		String text = foundation.getText(AccountLogin.LBL_BALANCE_1);
		CustomisedAssert.assertEquals(text, consumerbalance);
		foundation.waitforElementToBeVisible(AccountLogin.LBL_SUBSIDY, Constants.SHORT_TIME);
		foundation.click(AccountLogin.LBL_SUBSIDY);
		foundation.waitforElementToBeVisible(AccountLogin.SUBSIDY_BALANCE, Constants.SHORT_TIME);
		text = foundation.getText(AccountLogin.SUBSIDY_BALANCE);
		CustomisedAssert.assertEquals(text, subsidybalance);
		foundation.waitforElementToBeVisible(AccountLogin.CANCEL_BUTTON, Constants.SHORT_TIME);
		foundation.click(AccountLogin.CANCEL_BUTTON);
		foundation.threadWait(Constants.SHORT_TIME);
	}
}
