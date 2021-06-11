package at.smartshop.v5.pages;

import org.openqa.selenium.By;

public class ProductSearch {
	public static final By LBL_PRODUCT_NAME = By.className("product-name");
	public static final By BTN_PRODUCT=By.xpath("//button[@class='product search-product']");
	
	public static final By LBL_PRODUCT_SEARCH_TITLE = By.xpath("//h1[@data-reactid='.0.b.0.0.0.1']");
	public static final By LBL_PRODUCT_SEARCH_HEADER = By.xpath("//label[@data-reactid='.0.b.0.0.1.0.0.0.0']");
	public static final By LBL_PRODUCT_FOUND = By.xpath("//span[@data-reactid='.0.b.0.0.1.0.1.2']");
}
