package at.smartshop.v5.pages;


import org.openqa.selenium.By;
import java.util.Arrays;
import java.util.List;
import org.testng.Assert;

import at.framework.files.PropertyFile;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.database.columns.CNV5Device;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;

public class Order {
	
	 private Foundation foundation=new Foundation();
	 private TextBox textBox=new TextBox();
	 private PropertyFile propertyFile= new PropertyFile();

	public static final By BTN_CANCEL_ORDER = By.xpath("//button[text()='Cancel Order']");
	public static final By LBL_ORDER_CANCELLED = By.xpath("//span[text()='Transaction Cancelled']");
	public static final By LBL_PRODUCT_PRICE = By.className("product-price");
	public static final By LBL_SUB_TOTAL = By.xpath("//*[@class='total subtotal']//*[@class='total-value']");
	public static final By LBL_TAX_VALUE = By.xpath("//*[@class='total total-tax']//*[@class='total-value']");
	public static final By LBL_TAX = By.xpath("//*[@class='total total-tax']//*[@class='total-label']");
	public static final By LBL_DEPOSIT = By.xpath("//*[@class='total']//*[@class='total-value']");
	public static final By LBL_BALANCE_DUE = By.xpath("//*[@class='total grand-total']//*[@class='total-value']");
	public static final By TXT_HEADER=By.xpath("//div[@class='user-bar']/h1");
	public static final By TXT_PRODUCT=By.xpath("//div[@id='cartContainer']//div[@class='product-name']");
	public static final By POP_UP_TIMEOUT_YES = By.id("time-out-btn-yes-id");
	public static final By POP_UP_TIMEOUT_NO=By.id("time-out-btn-no-id");
	public static final By LBL_YOUR_ORDER = By.xpath("//h1[text()='Your Order']");
	public static final By POP_UP_LBL_ORDER_TIMEOUT = By.xpath("//h1[text()='Order Timeout']");
	public static final By POP_UP_LBL_ORDER_TIMEOUT_MSG = By.xpath("//h1[text()='Do you need more time?']");
    public static final By POP_UP_LBL_ORDER_TIMEOUT_SPANISH = By.xpath("//h1[text()='Tiempo de espera de pedido finalizado']");
    public static final By LBL_PROMOTION_NAME = By.className("product-name");
    public static final By LBL_ORDER_DISCOUNT = By.xpath("//*[@class='discount-price']/span");
    public static final By LBL_DISCOUNT = By.xpath("//*[@class='total']//div[@class='total-value']");
    public static final By LBL_EMAIL=By.xpath("//h3[text()='Email']//..");	
    public static final By LBL_MULTI_PRODUCTS= By.xpath("//*[@class='product-price']");
    public static final By LBL_DISCOUNT_NAME = By.className("discount-name");
    
    
    public By objText(String text) {
		return By.xpath("//*[text()='"+text+"']");
	}
    
	public void verifyOrderPageLanguage(String order) {
	    List<String> orderPageData = Arrays.asList(order.split(Constants.DELIMITER_TILD));
	    Assert.assertTrue(foundation.isDisplayed(objText(orderPageData.get(0))));
	    Assert.assertTrue(foundation.isDisplayed(objText(orderPageData.get(1))));
	    Assert.assertTrue(foundation.isDisplayed(objText(orderPageData.get(2))));
	    Assert.assertTrue(foundation.isDisplayed(objText(orderPageData.get(3))));
	    Assert.assertTrue(foundation.isDisplayed(objText(orderPageData.get(4))));
	    Assert.assertTrue(foundation.isDisplayed(objText(orderPageData.get(5))));
	    Assert.assertTrue(foundation.isDisplayed(objText(orderPageData.get(6))));
	    foundation.objectFocus(objText(orderPageData.get(7)));
	    Assert.assertTrue(foundation.isDisplayed(objText(orderPageData.get(7))));
	    Assert.assertTrue(foundation.isDisplayed(objText(orderPageData.get(8))));
	}	
	
	public void completeOrder(String email,String purchaseComplete, String yesButton) {
		foundation.click(objText(email));
		foundation.waitforElement(AccountLogin.BTN_NEXT, Constants.SHORT_TIME);
		foundation.click(AccountLogin.BTN_CAMELCASE);
		textBox.enterKeypadText(
				propertyFile.readPropertyFile(Configuration.V5_USER, FilePath.PROPERTY_CONFIG_FILE));
		foundation.click(AccountLogin.BTN_NEXT);
		foundation.waitforElement(AccountLogin.BTN_PIN_NEXT, Constants.SHORT_TIME);
		textBox.enterPin(propertyFile.readPropertyFile(Configuration.V5_PIN, FilePath.PROPERTY_CONFIG_FILE));
		foundation.click(AccountLogin.BTN_PIN_NEXT);
		Assert.assertTrue(foundation.isDisplayed(objText(purchaseComplete)));

		foundation.click(objText(yesButton));
		foundation.waitforElement(LandingPage.IMG_SEARCH_ICON, Constants.SHORT_TIME);
	}
}
