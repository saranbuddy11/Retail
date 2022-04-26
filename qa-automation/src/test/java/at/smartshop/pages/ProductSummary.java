package at.smartshop.pages;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import at.framework.browser.Factory;
import at.framework.ui.Foundation;
import at.smartshop.tests.TestInfra;

public class ProductSummary extends Factory {
	private Foundation foundation = new Foundation();
	public static final By BTN_EXTEND = By.cssSelector("a#extend");
	public static final By SEARCH_FILTER = By.xpath("//input[@aria-controls='locdt']");
	public static final By TXT_FILTER = By.cssSelector("input[id=productFilterType]");
	public static final By BTN_SAVE = By.id("saveBtn");
	public static final By TXT_SEARCH = By.cssSelector("#locdt_filter > label > input");
	public static final By TBL_DATA = By.cssSelector("tbody[aria-relevant=all] span");
	public static final By BTN_REMOVE = By.id("previewremove");
	public static final By TXT_LOCATION_SEARCH_FILTER = By.cssSelector("#locdt_filter > label > input");
	public static final By DPD_LOYALTY_MULTIPLIER = By.id("pointslist");
	public static final By DPD_TAX_CATEGORY = By.id("taxcat");
	public static final By BTN_EDIT_LOCATION = By.id("editlocation");
	public static final By TBL_LOCATION = By.id("locdt");
	public static final By TXT_SPINNER_MSG = By.xpath("//div[@class='humane humane-libnotify-info']");
	public static final By TXT_PRODUCT_NAME = By.id("name");
	public static final By DPD_DEPOSIT_CATEGORY = By.id("depositcat");
	public static final By TBL_EXPIRED_GRID = By.xpath("//tr[@class='editable odd']");
	public static final By DPD_DISCOUNT = By.id("hasemployeediscount");
	public static final By DPD_IS_DISABLED = By.id("isdisabled");
	public static final By TBL_EXPIRED = By.id("locdt");
	public static final By PRICE_FIELD=By.id("price");
	public static final By BTN_MODAL_SAVE = By.cssSelector("a#modalsave");
	public static final By DPD_CATEGORY1 = By.id("category1");
	public static final By DPD_CATEGORY2 = By.id("category2");
	public static final By DPD_CATEGORY3 = By.id("category3");
	public static final By LBL_REASON_CODE=By.xpath("//td[contains(@class,'column-reasonCode')]");
	public static final By DPD_REASON_CODE = By.xpath("//td[contains(@class,'column-reasonCode')]//select");
	public static final By TXT_PRICE = By.xpath("//input[@id='price']");
    public static final By LOATION_NAME=By.xpath("/html/body/div[3]/div[4]/div/div[2]/table/tbody/tr/td[1]");
    public static final By DPD_PICKLIST=By.id("picklist");
    public static final By DPD_DISPLAY=By.id("displayNeed");
    public static final By DPD_ROUNDING=By.id("rounding");
    public static final By MIN_STOCK=By.id("minstock");
    public static final By PRODUCT_NAME=By.id("name");
    public static final By MAX_STOCK=By.id("maxstock");
    public static final By CASE_COUNT=By.id("eoq");
    
	   
	public By getLocationNamePath(String text) {
		return By.xpath("//span[normalize-space()='" + text + "']");
	}
	private List<String> tableHeaders = new ArrayList<>();
	private Map<Integer, Map<String, String>> tableData = new LinkedHashMap<>();

	public List<String> getProductsHeaders() {
		List<String> tableHeaders = new ArrayList<>();
		try {
			WebElement tableProducts = getDriver().findElement(TBL_LOCATION);
			List<WebElement> columnHeaders = tableProducts.findElements(By.cssSelector("thead > tr > th"));
			for (WebElement columnHeader : columnHeaders) {
				tableHeaders.add(columnHeader.getText());
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		return tableHeaders;
	}

	public Map<String, String> getProductDetails(String name) {
		Map<String, String> productsRecord = new LinkedHashMap<>();
		try {
			List<String> tableHeaders = getProductsHeaders();
			for (int columnCount = 1; columnCount < tableHeaders.size() + 1; columnCount++) {
				WebElement column = getDriver().findElement(By.xpath("//table[@id='locdt']//tr//td//span[(text()='"
						+ name + "')]//..//..//td[" + columnCount + "]"));
				productsRecord.put(tableHeaders.get(columnCount - 1), column.getText());
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		return productsRecord;
	}
	public Map<Integer, Map<String, String>> getTblRecordsUI() {
		try {
			int recordCount = 0;
			tableHeaders.clear();
			WebElement tableList = getDriver().findElement(TBL_EXPIRED_GRID);
			WebElement table = getDriver().findElement(TBL_EXPIRED);
			List<WebElement> columnHeaders = table.findElements(By.cssSelector("thead > tr > th"));
			List<WebElement> rows = tableList.findElements(By.tagName("tr"));
			for (WebElement columnHeader : columnHeaders) {
				tableHeaders.add(columnHeader.getText());
			}
			int col = tableHeaders.size();
			for (WebElement row : rows) {
				Map<String, String> uiTblRowValues = new LinkedHashMap<>();
				for (int columnCount = 1; columnCount < col + 1; columnCount++) {
					foundation.scrollIntoViewElement(By.cssSelector("td:nth-child(" + columnCount + ")"));
					WebElement column = row.findElement(By.cssSelector("td:nth-child(" + columnCount + ")"));
					uiTblRowValues.put(tableHeaders.get(columnCount - 1), column.getText());
				}
				tableData.put(recordCount, uiTblRowValues);
				recordCount++;
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		return tableData;
	}
}
