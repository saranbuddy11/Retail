package at.smartshop.v5.pages;

import java.util.Arrays;
import java.util.List;
import org.openqa.selenium.By;
import org.testng.Assert;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.keys.Constants;

public class ProductSearch {

	private TextBox textBox=new TextBox();
	
	public static final By LBL_PRODUCT_NAME = By.className("product-name");
	public static final By BTN_PRODUCT = By.xpath("//button[@class='product search-product']");
	public static final By LBL_PRODUCT_SEARCH_TITLE = By.xpath("//h1[@data-reactid='.0.c.0.0.0.1']");
	public static final By LBL_PRODUCT_SEARCH_HEADER = By.xpath("//label[@data-reactid='.0.b.0.0.1.0.0.0.0']");
	public static final By LBL_PRODUCT_FOUND = By.xpath("//span[@data-reactid='.0.b.0.0.1.0.1.2']");
	public static final By BTN_123 = By.xpath("//*[text()='?123']");
	public static final By LBL_PROD_COUNT = By.xpath("//span[@data-reactid='.0.c.0.0.1.0.1.0']");
	public static final By LBL_PROD_MESSAGE = By.xpath("//span[@data-reactid='.0.c.0.0.1.0.1.2']");
	public static final By LINK_CLOSE_POPUP = By.xpath("//i[@data-reactid='.0.c.0.0.0.2.0']");
	public static final By LBL_PRODUCT_SCANCODE = By
			.xpath("//button[@class='product search-product']/span[@class='product-scan-code']");

	private Foundation foundation = new Foundation();

	public By objText(String text) {
		return By.xpath("//*[normalize-space(text())='" + text + "']");
	}

	public void verifyProductSearhPageLanguage(String productSearch) {

		List<String> productSearchPage = Arrays.asList(productSearch.split(Constants.DELIMITER_TILD));

		Assert.assertTrue(foundation.isDisplayed(objText(productSearchPage.get(0))));
		Assert.assertTrue(foundation.isDisplayed(objText(productSearchPage.get(1))));
		Assert.assertTrue(foundation.isDisplayed(objText(productSearchPage.get(2))));
	}
	
	public boolean searchProduct(String product) {
		foundation.click(LandingPage.IMG_SEARCH_ICON);
		textBox.enterKeypadText(product);
		foundation.click(ProductSearch.BTN_PRODUCT);
		if(foundation.getText(Order.TXT_PRODUCT).equals(product))
			return true;
		else
			return false;
	}

}
