package at.smartshop.v5.pages;

import org.openqa.selenium.By;

public class Order {
	
	public static final By BTN_CANCEL_ORDER = By.className("//button[text()='Cancel Order']");
    public static final By TXT_HEADER=By.xpath("//div[@class='user-bar']/h1");
    public static final By TXT_PRODUCT=By.xpath("//div[@id='cartContainer']//div[@class='product-name']");

}
