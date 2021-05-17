package at.smartshop.pages;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import at.framework.browser.Factory;

public class ProductSummary extends Factory {

	public static final By BTN_EXTEND = By.cssSelector("a#extend");
	public static final By TXT_FILTER = By.cssSelector("input[id=productFilterType]");
	public static final By BTN_SAVE = By.cssSelector("#modalsave");
	public static final By TXT_SEARCH = By.cssSelector("#locdt_filter > label > input");
	public static final By TBL_DATA = By.cssSelector("tbody[aria-relevant=all] span");
	public static final By BTN_REMOVE = By.id("previewremove");
	public static final By MSG_SUCCESS = By.className("div.humane.humane-libnotify-success");
	public static final By TBL_CELL_PRICE = By.cssSelector("td.edit.priceandstock.column-price");
	public static final By TXT_LOCATION_SEARCH_FILTER =  By.cssSelector("#locdt_filter > label > input");
	public static final By DPD_PRICE = By.id("price");
	public static final By DPD_LOYALTY_MULTIPLIER = By.id("pointslist");
	public static final By DPD_TAX_CATEGORY = By.id("taxcat");
	public static final By BTN_EDIT_LOCATION = By.id("editlocation");
	public static final By TBL_LOCATION = By.id("locdt");
	
	public By getLocationNamePath(String text) {
		By xpath = null;
		try {
			xpath = By.xpath("//span[normalize-space()='" + text + "']");
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
		return xpath;
	}
	
	public Boolean verifyLocationName(String text) {
		Boolean rulesLink= false;
		try {
		 rulesLink = (getDriver().findElement(By.xpath("//span[normalize-space()='" + text + "']"))).isDisplayed();
		}catch (Exception exc) {
			Assert.fail(exc.toString());			
		}
		return rulesLink;	
	}
	
	public List<String> getProductsHeaders() {
        List<String> tableHeaders = new ArrayList<>();
        try {
            WebElement tableProducts = getDriver().findElement(TBL_LOCATION);
            List<WebElement> columnHeaders = tableProducts
                    .findElements(By.cssSelector("thead > tr > th"));
            for (WebElement columnHeader : columnHeaders) {
                tableHeaders.add(columnHeader.getText());
            }
        } catch (Exception exc) {
            Assert.fail(exc.toString());
        }
        return tableHeaders;
    }
	
	public Map<String, String> getProductsRecords(String name) {
		Map<String, String> productsRecord = new LinkedHashMap<>();
    try {
        List<String> tableHeaders = getProductsHeaders();
            for (int columnCount = 1; columnCount < tableHeaders.size() + 1; columnCount++) {
            	WebElement column = getDriver().findElement(By.xpath("//table[@id='locdt']//tr//td//span[(text()='"+ name +"')]//..//..//td["+columnCount+"]"));
                productsRecord.put(tableHeaders.get(columnCount - 1), column.getText());
            }
    } catch (Exception exc) {
        Assert.fail(exc.toString());
    }
    return productsRecord;
    
}
}
