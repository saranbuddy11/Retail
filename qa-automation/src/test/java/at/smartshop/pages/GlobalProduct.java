package at.smartshop.pages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import at.framework.browser.Factory;
import at.framework.generic.CustomisedAssert;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.keys.Constants;
import at.smartshop.tests.TestInfra;

public class GlobalProduct extends Factory {

	private Foundation foundation = new Foundation();
	private TextBox textBox = new TextBox();
	private Dropdown dropDown = new Dropdown();
	private NavigationBar navigationBar = new NavigationBar();

	public static final By TXT_FILTER = By.id("filterType");
	public static final By ICON_FILTER = By.id("dataGrid_dd_enabled_button");
	public static final By TBL_GRID = By.id("dataGrid");
	public static final By BTN_EXPORT = By.xpath("//button[text()='Export']");
	public static final By TXT_RECORD_COUNT = By.cssSelector("#dataGrid_pager_label");
	public static final By TBL_ROW = By.xpath("//*[@id='dataGrid']/tbody");
	public static final By BTN_CREATE = By.cssSelector("button#createNewBtn");
	public static final By TXT_PRODUCTNAME = By.xpath("//dd//input[@id='name']");
	public static final By TXT_SCAN_CODE = By.xpath("//input[@name='scancode']");
	public static final By SCANCODE=By.xpath("//dt[text()='Scancode(s)']");
	public static final By BUTTON_ADD = By.xpath("//button[text()='Add']");
	public static final By LBL_SCANCODE_MSG = By.xpath("//div[@class='scmsg error']");
	public static final By TXT_PRICE = By.xpath("//input[@id='price']");
	public static final By BUTTON_SAVE = By.xpath("//button[text()='Save And Extend']");
	public static final By LBL_SCANCODE_ERROR = By.xpath("//label[@id='scancode-error']");
	public static final By LBL_COST = By.xpath("//input[@id='cost']");
	public static final By LBL_SHORT_NAME = By.xpath("//input[@id='shortname']");
	public static final By LBL_SAVE_DONE = By.id("nextyes");
	public static final By LBL_ALERT_HEADER = By.xpath("//div[@class='ajs-header']");
	public static final By LBL_ALERT_OK = By.xpath("//button[@class='ajs-button ajs-ok']");
	public static final By LBL_ALERT_CONTENT = By.xpath("//div[@class='ajs-content']");
	public static final By TXT_SCAN_CODE_2 = By.xpath("(//input[@name='scancode'])[2]");
	public static final By TXT_SCAN_CODE_ERROR = By
			.xpath("//div[@class='scmsg error' and @style='color: rgb(255, 0, 0);']");
	public static final By IMG_DATA_GRID_LOADING = By.id("dataGrid_container_loading");
	public static final By TXT_SPINNER_MSG = By.xpath("//div[@class='humane humane-libnotify-info']");
	public static final By GBL_PRODUCT_DATA = By
			.xpath("//table[@id='dataGrid']/tbody/tr/td[@aria-describedby='dataGrid_name']");
	public static final By TXT_GLOBAL_PRODUCT = By.id("Global Products");
	public static final By POPUP_HEADER=By.xpath("//h4[@style='text-align:center;']");
	public static final By TXT_PRODUCT_CREATE = By.id("Product Create");
	public static final By SELECT_LOCATION = By.xpath("//input[@class='ui-igcombo-field ui-corner-all']");
	public static final By BTN_EXTEND = By.id("extend");
	public static final By DPD_LOYALTY_MULTIPLIER = By.id("pointslist");
	public static final By DPD_PICK_LIST = By.id("picklist");
	public static final By INPUT_MIN_STOCK = By.id("minstock");
	public static final By INPUT_MAX_STOCK = By.id("maxstock");
	public static final By INPUT_PRICE = By.id("price");
	public static final By BTN_SAVE = By.id("saveBtn");
	public static final By SHOW_DROPDOWN=By.xpath("//div[@title='Show drop-down']");
	public static final By SAVE_POPUP_BTN=By.id("nextyes"); 
    public static final By CANCEL_BTN=By.id("cancelBtn");
    public static final By BTN_EXTEND_LOC = By.xpath("//a[@id='extend']");
    public static final By MATCHING_RECORD=By.id("dataGrid_pager_label");
    public static final By CLICK_LOCATION = By.xpath("//ul[@class='ui-igcombo-listitemholder']//li//div[text()='AutomationLocation1']//preceding-sibling::span");
	 
	public By getGlobalProduct(String product) {
		return By.xpath("//td[@aria-describedby='dataGrid_name'][text()='" + product + "']");
	}
	public By getGlobalProductSearch(String product) {
		return By.xpath("//tr[@data-id='40b9fbbc57f66e1b734a59c6f4c1a48a']//td[text()='" + product + "']");
	}

	public By selectGlobalProduct(String product, String category) {
		return By.xpath("//td[text()='" + product + "']/following-sibling::td[text()='" + category + "']");
	}

	public By selectProductPrice(String location) {
		return By.xpath("//span[text()='" + location
				+ "']/parent::td/following-sibling::td[@class='edit priceandstock column-price']");
	}

	public By selectProductMin(String location) {
		return By.xpath("//span[text()='" + location
				+ "']/parent::td/following-sibling::td[@class='edit minstock column-minstock']");
	}

	public By selectProductMax(String location) {
		return By.xpath("//span[text()='" + location
				+ "']/parent::td/following-sibling::td[@class='edit maxstock column-maxstock']");
	}

	public By selectProductPickList(String location) {
		return By.xpath("//span[text()='" + location
				+ "']/parent::td/following-sibling::td[@class='edit planningcolumn column-picklist']");
	}

	public By selectProductLoyaltyMultiplier(String location) {
		return By.xpath("//span[text()='" + location
				+ "']/parent::td/following-sibling::td[@class='edit pointscolumn column-points']");
	}

	public By getExistingScancode(String scancode) {
		return By.xpath("//table[@id='dataGrid']/tbody/tr/td[@aria-describedby='dataGrid_scancode' and text()='"
				+ scancode + "']");
	}

	public Map<String, String> getTblRecordsUI() {
		Map<String, String> uiTblRowValues = new HashMap<>();
		try {
			int curColumnIndex = 1;
			WebElement tableReports = getDriver().findElement(TBL_GRID);
			List<WebElement> columnHeaders = tableReports.findElements(By.tagName("th"));
			WebElement row = getDriver().findElement(TBL_ROW);

			for (WebElement columnHeader : columnHeaders) {
				WebElement column = row.findElement(By.cssSelector("td:nth-child(" + curColumnIndex + ")"));
				uiTblRowValues.put(columnHeader.getText(), column.getText());
				curColumnIndex++;
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		return uiTblRowValues;
	}

	public boolean verifyExcelData(List<String> uiList, List<String> excelList) {
		try {

			for (String uiCelldata : uiList) {
				Boolean isTest = false;
				for (String cell : excelList) {

					if (uiCelldata.equals(cell)) {
						isTest = true;
						break;
					}
				}
				if (isTest.equals(false)) {
					return false;
				}
			}
		} catch (Exception exc) {
			exc.printStackTrace();
		}

		return true;

	}

	public void selectFilter(String filterType) {
		foundation.click(ICON_FILTER);
		foundation.threadWait(Constants.ONE_SECOND);
		foundation.click(By.xpath("//*[@id='dataGrid_dd_enabled']//span[text()='" + filterType + "']"));
	}

	public void selectGlobalProduct(String product) {
		textBox.enterText(TXT_FILTER, product);
		foundation.click(By.xpath("//td[@aria-describedby='dataGrid_name'][text()='" + product + "']"));
	}

	public void assignTaxCategory(String productName, String taxCategory) {
		navigationBar.navigateToMenuItem("Product#Global Products");
		selectGlobalProduct(productName);
		dropDown.selectItem(ProductSummary.DPD_TAX_CATEGORY, taxCategory, Constants.TEXT);
		foundation.click(ProductSummary.BTN_SAVE);
		foundation.waitforElementToDisappear(TXT_SPINNER_MSG, Constants.SHORT_TIME);
	}

	public void removeTaxCategory(String productName) {
		navigationBar.navigateToMenuItem("Product#Global Products");
		selectGlobalProduct(productName);
		dropDown.selectItem(ProductSummary.DPD_TAX_CATEGORY, Constants.CHOOSENOTHING, Constants.TEXT);
		foundation.click(ProductSummary.BTN_SAVE);
		foundation.waitforElementToDisappear(TXT_SPINNER_MSG, Constants.SHORT_TIME);
	}
	/**
	 * search product and edit product name and save
	 * @param product
	 * @param editproduct
	 */
	public void searchProductAndUpdateProductNameInGlobalProducts(String product,String editproduct) {
		CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProduct.TXT_GLOBAL_PRODUCT));
		textBox.enterText(LocationList.TXT_FILTER, product);
		foundation.waitforElementToBeVisible(GlobalProduct.MATCHING_RECORD, Constants.SHORT_TIME);
		foundation.click(getGlobalProduct(product));
		CustomisedAssert.assertTrue(foundation.isDisplayed(ProductSummary.LBL_PRODUCT_SUMMMARY));		
		textBox.enterText(ProductSummary.TXT_PRODUCT_NAME, editproduct);
		foundation.waitforElementToBeVisible(ProductSummary.BTN_SAVE, Constants.SHORT_TIME);
		foundation.click(ProductSummary.BTN_SAVE);
	}
}
