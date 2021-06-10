package at.smartshop.v5.pages;

import org.openqa.selenium.By;

public class Order {
	
	public static final By BTN_CANCEL_ORDER = By.xpath("//button[text()='Cancel Order']");
	public static final By LBL_ORDER_CANCELLED = By.xpath("//span[text()='Transaction Cancelled']");
	public static final By LBL_PRODUCT_PRICE = By.className("product-price");
	public static final By LBL_SUB_TOTAL = By.xpath("//*[@class='total subtotal']//*[@class='total-value']");
	public static final By LBL_TAX = By.xpath("//*[@class='total total-tax']//*[@class='total-value']");
	public static final By LBL_DEPOSIT = By.xpath("//*[@class='total']//*[@class='total-value']");
	public static final By LBL_BALANCE_DUE = By.xpath("//*[@class='total grand-total']//*[@class='total-value']");
	public static final By TXT_HEADER=By.xpath("//div[@class='user-bar']/h1");
	public static final By TXT_PRODUCT=By.xpath("//div[@id='cartContainer']//div[@class='product-name']");
	public static final By POP_UP_TIMEOUT_YES = By.id("time-out-btn-yes-id");
	public static final By POP_UP_TIMEOUT_NO=By.id("time-out-btn-no-id");
	public static final By LBL_YOUR_ORDER = By.xpath("//h1[text()='Your Order']");
	public static final By POP_UP_LBL_ORDER_TIMEOUT = By.xpath("//h1[text()='Order Timeout']");
}

