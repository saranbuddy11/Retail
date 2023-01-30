package at.smartshop.v5.pages;

import java.util.Arrays;
import java.util.List;
import org.openqa.selenium.By;
import at.framework.browser.Browser;
import at.framework.files.PropertyFile;
import at.framework.generic.CustomisedAssert;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;

import at.smartshop.database.columns.CNV5Device;

import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;

public class LandingPage {

	public Browser browser = new Browser();
	public PropertyFile propertyFile = new PropertyFile();
	private TextBox textBox = new TextBox();
	private Foundation foundation = new Foundation();


	public static final By IMG_LOGO = By.xpath("//span[@class='logoImg']");
	public static final By LBL_HEADER = By.xpath("//h1[@id='instructionText']");
	// public static final By IMG_SEARCH_ICON =
	// By.cssSelector("div.btn.category.search-btn");
	public static final By IMG_SEARCH_ICON = By.id("product-lookup-btn-id");
	public static final By LNK_IMAGE = By.xpath("//figure[@class='Commercial animated ']//img");
	public static final By BTN_PRODUCT = By.xpath("//button[@class='product search-product']");
	public static final By TXT_HEADER = By.xpath("//div[@class='user-bar']/h1");
	public static final By TXT_PRODUCT = By.xpath("//div[@id='cartContainer']//div[@class='product-name']");
	public static final By LINK_ENGLISH = By.xpath("//button[text()='English']");
	public static final By BTN_LOGIN = By.id("account-login-id");
	// public static final By BTN_CREATE_ACCOUNT =
	// By.xpath("//h3[@data-reactid='.0.0.0.0.2.0.1']");
	public static final By BTN_MANAGE_LOGIN = By.xpath("//button[@class='button-primary']");
	public static final By BTN_CREATE_ACCOUNT = By.id("create-account-id");
	public static final By BTN_LANG = By.xpath("//h3[@data-reactid='.0.0.0.2.1']");
	public static final By LBL_ACCOUNT_LOGIN = By.xpath("//div[@id='account-login-id']//h3");
	public static final By LBL_CREATE_ACCOUNT = By.xpath("//div[@id='create-account-id']/div//h3");
	public static final By LBL_SEARCH = By.xpath("//span[@class='category-label']");
	public static final By LBL_SCAN = By.xpath("//div[@class='footer']//h2");
	public static final By IMG_ORDER_SEARCH_ICON = By.cssSelector("span.category-icon.mm-icon.search-icon.ion-search");

	public By objLanguage(String languageName) {
		return By.xpath("//button[text()='" + languageName + "']");
	}

	public void navigateDriverLoginPage() {
		foundation.doubleClick(LandingPage.IMG_LOGO);
		foundation.doubleClick(LandingPage.IMG_LOGO);
		foundation.click(LandingPage.IMG_LOGO);
	}

	public By objText(String text) {
		return By.xpath("//*[normalize-space(text())='" + text + "']");
	}

	public By objImageDisplay(String imageName) {
		return By.xpath("//img[contains(@src,'" + imageName + "')]");
	}

	public void verifyHomeScreenLanguage(String landingPage) {
		List<String> landingPageData = Arrays.asList(landingPage.split(Constants.DELIMITER_TILD));
		// Validating Landing Page
		CustomisedAssert.assertEquals(foundation.getText(LandingPage.LBL_ACCOUNT_LOGIN), landingPageData.get(0));
		CustomisedAssert.assertEquals(foundation.getText(LandingPage.LBL_CREATE_ACCOUNT), landingPageData.get(1));
		// CustomisedAssert.assertTrue(foundation.isDisplayed(objText(landingPageData.get(2))));
		// CustomisedAssert.assertTrue(foundation.isDisplayed(objText(landingPageData.get(3))));
		CustomisedAssert.assertEquals(foundation.getText(LandingPage.LBL_HEADER), landingPageData.get(4));
		CustomisedAssert.assertEquals(foundation.getText(LandingPage.LBL_SEARCH), landingPageData.get(5));
		CustomisedAssert.assertEquals(foundation.getText(LandingPage.LBL_SCAN), landingPageData.get(6));
	}

	public void changeLanguage(String languageButton, String newLanguage, String button) {
		foundation.waitforElement(BTN_LANG, Constants.SHORT_TIME);
		foundation.click(BTN_LANG);
		foundation.waitforElement(objText(newLanguage), Constants.SHORT_TIME);
		foundation.click(objText(newLanguage));
		foundation.click(objText(button));
	}

	/**
	 * Launch V5 device with URL and set the language as English
	 */
	public void launchV5AndSelectLanguageEnglish() {
		browser.launch(Constants.REMOTE, Constants.CHROME);
		browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
		changeLanguage(Constants.LANGUAGE, Constants.ENGLISH, Constants.CONTINUE);
	}


	/**
	 * Launch V5 device and select product for transaction
	 * 
	 * @param product
	 * @return
	 */
	public String launchV5AndSelectProduct(String product) {
		browser.launch(Constants.REMOTE, Constants.CHROME);
		browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
		foundation.click(IMG_SEARCH_ICON);
		foundation.threadWait(Constants.THREE_SECOND);
		textBox.enterKeypadText(product);
		foundation.click(ProductSearch.BTN_PRODUCT);
		CustomisedAssert.assertTrue(foundation.isDisplayed(Order.BTN_CANCEL_ORDER));
		CustomisedAssert.assertTrue(product.equals(foundation.getText(Order.LBL_PROMOTION_NAME)));
		String productPrice = foundation.getText(Order.LBL_PRODUCT_PRICE).split(Constants.DOLLAR)[1];
		return productPrice;
}
	/**
	 * Transaction in v5 device
	 * @param product
	 * @param email
	 * @param pin
	 */
	public void transactionInV5Device(String product,String email,String pin) {
		foundation.threadWait(Constants.SHORT_TIME);
		foundation.click(LandingPage.IMG_SEARCH_ICON);
		foundation.threadWait(Constants.THREE_SECOND);
		foundation.click(AccountLogin.BTN_CAMELCASE);
		textBox.enterKeypadText(product);
		foundation.click(ProductSearch.BTN_PRODUCT);
		foundation.waitforElementToBeVisible(Payments.EMAIL_ACC, Constants.THREE_SECOND);
		foundation.click(Payments.EMAIL_ACC);
		foundation.threadWait(Constants.SHORT_TIME);
		foundation.click(Payments.BTN_EMAIL_LOGIN);
		foundation.threadWait(Constants.THREE_SECOND);
		foundation.click(AccountLogin.BTN_CAMELCASE);
		textBox.enterKeypadText(email);
		foundation.click(AccountLogin.BTN_NEXT);
		foundation.threadWait(Constants.SHORT_TIME);
		foundation.waitforElement(AccountLogin.BTN_PIN_NEXT, Constants.SHORT_TIME);
		textBox.enterPin(pin);
		foundation.click(AccountLogin.BTN_PIN_NEXT);
		foundation.threadWait(Constants.LONG_TIME);

	}

	/**
	 * Selecting Product
	 * 
	 * @param product
	 */
	public void selectProduct(String product) {
		foundation.click(IMG_SEARCH_ICON);
		textBox.enterKeypadText(product);
		foundation.click(ProductSearch.BTN_PRODUCT);
		CustomisedAssert.assertTrue(foundation.isDisplayed(Order.BTN_CANCEL_ORDER));
	}
}
