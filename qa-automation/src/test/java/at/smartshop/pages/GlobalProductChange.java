package at.smartshop.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import at.framework.browser.Factory;

public class GlobalProductChange extends Factory{	
	public static final By TXT_LOCATION_SEARCH = By.id("loc-search");
	public static final By TBL_LOCATION_LIST = By.id("location-list");
	public static final By BTN_LOCATION_APPLY = By.id("loc-filter-apply");
	public static final By BTN_PRODUCT_APPLY = By.id("prd-filter-apply");
	public static final By LNK_SEARCH_ALL = By.id("loc-select-all");
	public static final By TAB_PRODUCT = By.cssSelector("#li2 > a");
	private static final By TBL_PRODUCT_LIST = By.cssSelector("#filtered-prd-dt > tbody > tr");
	private static final By TBL_PRODUCT_LIST_ROWS = By.cssSelector("td > span");
	public static final By BTN_NEXT = By.id("prd-dt-next");
	public static final By BTN_INCREMENT = By.xpath("//*[@id='increment-radio-btn']/..");
	public static final By TXT_PRICE = By.id("prd-price");
	public static final By TXT_PRODUCT_NAME = By.id("filter-name");
	public static final By LBL_PRICE = By.xpath("//*[@id='prd-change-form']/dt[text()='Price']");
	public static final By LBL_MIN = By.xpath("//*[@id='prd-change-form']/dt[text()='Min']");
	public static final By LBL_MAX = By.xpath("//*[@id='prd-change-form']/dt[text()='Max']");
	public static final By LBL_PRODUCT = By.xpath("((//tbody//tr)[1]//td)[1]");   
    public static final By DPD_LOYALITY_MULTIPLIER = By.id("prd-loyalty-multiplier");
    public static final By BTN_SUBMIT = By.id("prd-update-submit");
    public static final By BTN_OK = By.cssSelector("button.ajs-button.ajs-ok");
    public static final By MSG_SUCCESS = By.xpath("//div[text()='Updated 1 product(s)!']");
    public static final By RDO_OPERATOR_PRODUCT_CHANGE= By.xpath("//label[text()='Operator Product Catalog Change']");

	public List<String> getAllFilteredLocations(){ 
		List<String> filteredLocations = new ArrayList<>();
		try {
			 List<WebElement> filteredProducts = getDriver().findElements(TBL_PRODUCT_LIST);
			 for(WebElement filteredProduct : filteredProducts)
			 {
				filteredLocations.add(filteredProduct.findElement(TBL_PRODUCT_LIST_ROWS).getText());
			 }
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
		return filteredLocations;
    }
}
