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
	
	public By txtLocationSearch = By.id("loc-search");
	public By tblLocationList = By.id("location-list");
	public By btnLocationApply = By.id("loc-filter-apply");
	public By btnProductApply = By.id("prd-filter-apply");
	public By lnkSelectAll = By.id("loc-select-all");
	public By tabProduct = By.cssSelector("#li2 > a");
	private By tblProductsList = By.cssSelector("#filtered-prd-dt > tbody > tr");
	private By tblProductsListRows = By.cssSelector("td > span");
	public By btnNext = By.id("prd-dt-next");
	public By btnIncrement = By.xpath("//*[@id='increment-radio-btn']/..");
	public By txtPrice = By.id("prd-price");
	public By txtProductName = By.id("filter-name");
    public By drpLoyaltyMultiplier = By.id("prd-loyalty-multiplier");
    public By btnSubmit = By.id("prd-update-submit");
    public By btnOK = By.cssSelector("button.ajs-button.ajs-ok");
    public By msgSuccess = By.xpath("//div[text()='Updated 1 product(s)!']");
		
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
