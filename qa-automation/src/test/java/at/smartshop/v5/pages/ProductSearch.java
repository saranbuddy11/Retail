package at.smartshop.v5.pages;


import java.util.Arrays;
import java.util.List;
import org.openqa.selenium.By;
import org.testng.Assert;
import at.framework.ui.Foundation;
import at.smartshop.keys.Constants;

public class ProductSearch {	

	public static final By LBL_PRODUCT_NAME = By.className("product-name");
	public static final By BTN_PRODUCT=By.xpath("//button[@class='product search-product']");	
	public static final By LBL_PRODUCT_SEARCH_TITLE = By.xpath("//h1[@data-reactid='.0.b.0.0.0.1']");
	public static final By LBL_PRODUCT_SEARCH_HEADER = By.xpath("//label[@data-reactid='.0.b.0.0.1.0.0.0.0']");
	public static final By LBL_PRODUCT_FOUND = By.xpath("//span[@data-reactid='.0.b.0.0.1.0.1.2']");
	public static final By LBL_PROD_COUNT =By.xpath("//span[@data-reactid='.0.b.0.0.1.0.1.0']");
	public static final By LBL_PROD_MESSAGE =By.xpath("//span[@data-reactid='.0.b.0.0.1.0.1.2']");
	public static final By LINK_CLOSE_POPUP = By.xpath("//i[@data-reactid='.0.b.0.0.0.2.0']");

	
	private Foundation foundation=new Foundation();

	public void verifyProductSearhPageLanguage(String productSearch) {
		List<String> productSearchPage = Arrays.asList(productSearch.split(Constants.DELIMITER_TILD));
		Assert.assertEquals(foundation.getText(LBL_PRODUCT_SEARCH_TITLE),productSearchPage.get(0));
		Assert.assertEquals(foundation.getText(LBL_PRODUCT_SEARCH_HEADER),productSearchPage.get(1));
		Assert.assertEquals(foundation.getText(LBL_PRODUCT_FOUND),productSearchPage.get(2));
	}

}
