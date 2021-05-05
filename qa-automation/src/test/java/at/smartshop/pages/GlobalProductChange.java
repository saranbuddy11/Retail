package at.smartshop.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;


import at.framework.browser.Factory;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;

public class GlobalProductChange extends Factory{
	TextBox textBox = new TextBox();
	Foundation foundation = new Foundation();
	
	public static final By txtLocationSearch = By.id("loc-search");
	public static final By tblLocationList = By.id("location-list");
	public static final By btnLocationApply = By.id("loc-filter-apply");
	public static final By btnProductApply = By.id("prd-filter-apply");
	public static final By lnkSelectAll = By.id("loc-select-all");
	public static final By tabProduct = By.cssSelector("#li2 > a");
	private static final By tblProductsList = By.cssSelector("#filtered-prd-dt > tbody > tr");
	private static final By tblProductsListRows = By.cssSelector("td > span");
	public static final By btnNext = By.id("prd-dt-next");
	public static final By btnIncrement = By.xpath("//*[@id='increment-radio-btn']/..");
	public static final By txtPrice = By.id("prd-price");
	public static final By txtProductName = By.id("filter-name");
    public static final By drpLoyaltyMultiplier = By.id("prd-loyalty-multiplier");
    public static final By btnSubmit = By.id("prd-update-submit");
    public static final By btnOK = By.cssSelector("button.ajs-button.ajs-ok");
    public static final By msgSuccess = By.xpath("//div[text()='Updated 1 product(s)!']");
		
	public List<String> getAllFilteredLocations(){ 
		List<String> filteredLocationsGrid = new ArrayList<>();
		try {
			 List<WebElement> filteredProducts = getDriver().findElements(tblProductsList);
			 for(WebElement filteredProduct : filteredProducts)
			 {
				filteredLocationsGrid.add(filteredProduct.findElement(tblProductsListRows).getText());
			 }
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
		return filteredLocationsGrid;
    }
}
