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
	public static final By TXT_GLOBAL_PRODUCT = By.id("globalproductpagetitle");
	public static final By POPUP_HEADER=By.xpath("//h4[@style='text-align:center;']");
	public static final By TXT_PRODUCT_CREATE = By.id("Product Create");
	public static final By SELECT_LOCATION = By.xpath("//input[@class='ui-igcombo-field ui-corner-all']");
	public static final By BTN_EXTEND = By.id("extend");
	public static final By DPD_LOYALTY_MULTIPLIER = By.id("pointslist");
	public static final By DPD_PICK_LIST = By.id("picklist");
	public static final By INPUT_MIN_STOCK = By.id("minstock");
	public static final By INPUT_MAX_STOCK = By.id("maxstock");
	public static final By BTN_SAVE_EXTEND=By.id("saveExtendBtn");
	public static final By INPUT_PRICE = By.id("price");
	public static final By BTN_SAVE = By.id("saveBtn");
	public static final By SHOW_DROPDOWN=By.xpath("//div[@title='Show drop-down']");
	public static final By SAVE_POPUP_BTN=By.id("nextyes"); 
    public static final By CANCEL_BTN=By.id("cancelBtn");
    public static final By BTN_EXTEND_LOC = By.xpath("//a[@id='extend']");
    public static final By MATCHING_RECORD=By.id("dataGrid_pager_label");
    public static final By CLICK_LOCATION = By.xpath("//ul[@class='ui-igcombo-listitemholder']//li//div[text()='AutomationLocation1']//preceding-sibling::span");
    public static final By LBL_GLOBAL_PRODUCT = By.xpath("//div[@id='globalproductpagetitle']");
    public static final By LBL_PRODUCT_CREATE = By.xpath("//li[text()='Product Create']");
    public static final By POPUP_DROPDOWN = By.xpath("//div[@title='Show drop-down']");
    public static final By DISABLE_PRODUCT = By.xpath("//select[@id='isdisabled']");
    public static final By CLICK_PRODUCT = By.xpath("//tbody//td[@aria-describedby='dataGrid_name']");
    public static final By LAB_RECORD = By.xpath("//div[@class='ui-iggrid-results']");
    public static final By MATCH_RECORD = By.xpath("//span[@title='Current records range']");
    public static final By DPD_RECORD = By.xpath("//div[contains(@class,'ui-igedit-buttonimage')]");
    public static final By TXT_EXTEND=By.xpath("//input[@aria-controls='locdt']");
    public static final By EXTEND_LOCATION=By.xpath("//td[@class=' sorting_1']");
    public static final By TBL_HEADER = By.cssSelector("#dataGrid > tbody");
    public static final By TBL_EXTEND=By.xpath("//tbody[@role='alert']//tr");
    public static final By SEARCH_EXTEND=By.xpath("//input[@aria-controls='locdt']");
    public static final By POPUP_SEARCH=By.id("productFilterType");
    public static final By POPUP_SAVE=By.id("modalsave");
    public static final By LBL_PRODUCT_SUMMARY = By.xpath("//li[text()='Product Summary']");
	 
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
	public By selectLocationForProduct(String location) {
		return By.xpath("(//div[@class='ui-igcombo-listitemtextwithcheckbox'])[text()='" + location + "']");
	}
	public By selectRecord(String data) {
		return By.xpath("//div[@id='dataGrid_editor_list']//span[text()='"+ data + "']");
				
	}
	public By selectLocationInExtend(String location) {
		return By.xpath("//tbody//tr//td[text()='"+ location +"']");
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
	
	public void changeRecordDataAndVerify(By recorddata,String dbData) {
		CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProduct.LAB_RECORD));
		foundation.waitforElementToBeVisible(GlobalProduct.DPD_RECORD,3);
		foundation.click(GlobalProduct.DPD_RECORD);
        foundation.click(recorddata);		
		foundation.scrollIntoViewElement(GlobalProduct.MATCH_RECORD);
		foundation.threadWait(5);
		String record=foundation.getText(MATCH_RECORD);
		CustomisedAssert.assertTrue(record.contains(dbData));
	}
	
	/**
	 * search product and edit product name and save
	 * @param product
	 * @param editproduct
	 */
	public void searchProductAndUpdateProductNameInGlobalProducts(String product,String editproduct) {
		CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProduct.TXT_GLOBAL_PRODUCT));
		foundation.waitforElementToBeVisible(TXT_FILTER, 5);
		textBox.enterText(LocationList.TXT_FILTER, product);
		foundation.threadWait(3);
		foundation.waitforElementToBeVisible(GlobalProduct.MATCHING_RECORD, Constants.SHORT_TIME);
		foundation.click(getGlobalProduct(product));
		CustomisedAssert.assertTrue(foundation.isDisplayed(ProductSummary.LBL_PRODUCT_SUMMMARY));		
		textBox.enterText(ProductSummary.TXT_PRODUCT_NAME, editproduct);
		foundation.waitforElementToBeVisible(ProductSummary.BTN_SAVE, Constants.SHORT_TIME);
		foundation.click(ProductSummary.BTN_SAVE);
		foundation.threadWait(Constants.TWO_SECOND);
	}
	/**
	 * Disable created product in GlobalProduct
	 * @param product
	 * @param editproduct
	 */
	public void disableProduct(String editoption,String product) {
		foundation.click(GlobalProduct.CLICK_PRODUCT);
		foundation.waitforElementToBeVisible(GlobalProduct.DISABLE_PRODUCT,3);
		dropDown.selectItem(GlobalProduct.DISABLE_PRODUCT, editoption, Constants.TEXT);
		foundation.waitforElementToBeVisible(GlobalProduct.BTN_SAVE,5);
		foundation.scrollIntoViewElement(GlobalProduct.BTN_SAVE);
		foundation.click(GlobalProduct.BTN_SAVE);
		foundation.waitforElementToBeVisible(GlobalProduct.TXT_FILTER,5);
		textBox.enterText(LocationList.TXT_FILTER,product);
		CustomisedAssert.assertFalse(foundation.getText(GlobalProduct.CLICK_PRODUCT).equals(product));
		
		}
	
	/**
	 * create Product In Global Product Page
	 * @param name
	 * @param price
	 * @param scancode
	 */
	public void createProducInGlobalProductPage(String name,String price,String scancode) {
		CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProduct.TXT_GLOBAL_PRODUCT));
		foundation.click(GlobalProduct.BTN_CREATE);
		foundation.isDisplayed(GlobalProduct.TXT_PRODUCT_CREATE);
		textBox.enterText(TXT_PRODUCTNAME, name);
		foundation.waitforElementToBeVisible(GlobalProduct.TXT_PRICE, 2);
		textBox.enterText(TXT_PRICE, price);
		foundation.waitforElementToBeVisible(GlobalProduct.TXT_SCAN_CODE, 2);
		textBox.enterText(TXT_SCAN_CODE, scancode);
		foundation.waitforElementToBeVisible(GlobalProduct.BTN_SAVE_EXTEND, 2);
		foundation.click(BTN_SAVE_EXTEND);
		foundation.waitforElementToBeVisible(GlobalProduct.LBL_SAVE_DONE, 2);
		foundation.click(LBL_SAVE_DONE);
		
	}
	/**
	 * create Product In Global Product Page
	 * @param name
	 * @param price
	 * @param scancode
	 */
	public void createProducInGlobalProductPageWithLocation(String name,String price,String location,String randomChr) {
		textBox.enterText(GlobalProduct.TXT_PRODUCTNAME, name);
		textBox.enterText(GlobalProduct.TXT_PRICE, price);
		textBox.enterText(GlobalProduct.TXT_SCAN_CODE,randomChr);
		foundation.waitforElementToBeVisible(GlobalProduct.BTN_SAVE_EXTEND,3);
		foundation.click(GlobalProduct.BTN_SAVE_EXTEND);
		foundation.waitforElementToBeVisible(GlobalProduct.POPUP_DROPDOWN,5);
		foundation.click(GlobalProduct.POPUP_DROPDOWN);
		foundation.click(selectLocationForProduct(location));
		foundation.click(GlobalProduct.POPUP_DROPDOWN);
		foundation.waitforElementToBeVisible(GlobalProduct.LBL_SAVE_DONE,3);
		foundation.click(GlobalProduct.LBL_SAVE_DONE);
		
	
	}
	
	/**
	 * Add new location in extend and verify the loyalty value in extend location are same
	 * @param location
	 * 
	 */
	public void verifyAddLocationInExtend(String location) {
	foundation.waitforElementToBeVisible(GlobalProduct.BTN_EXTEND_LOC,3);
	foundation.click(GlobalProduct.BTN_EXTEND_LOC);
	foundation.waitforElementToBeVisible(GlobalProduct.POPUP_SEARCH, 3);
	foundation.click(GlobalProduct.POPUP_SEARCH);
	textBox.enterText(GlobalProduct.POPUP_SEARCH, location);
	foundation.click(selectLocationInExtend(location));
	foundation.waitforElementToBeVisible(GlobalProduct.POPUP_SAVE,3);
	foundation.click(GlobalProduct.POPUP_SAVE);
	foundation.scrollIntoViewElement(GlobalProduct.SEARCH_EXTEND);
	foundation.waitforElementToBeVisible(GlobalProduct.SEARCH_EXTEND,5);
	foundation.click(GlobalProduct.SEARCH_EXTEND);
	textBox.enterText(GlobalProduct.SEARCH_EXTEND, location);
	
}
	/**
	 * create Product In Global Product Page
	 * @param name
	 * @param price
	 * @param scancode
	 */
	public void createProducInGlobalProductLoyaltyWithLocation(String name,String price,String value,String randomChr, String location) {
		CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProduct.LBL_GLOBAL_PRODUCT));
	
	foundation.click(GlobalProduct.BTN_CREATE);
	foundation.isDisplayed(GlobalProduct.LBL_PRODUCT_CREATE);
	textBox.enterText(GlobalProduct.TXT_PRODUCTNAME, name);
	textBox.enterText(GlobalProduct.TXT_PRICE,price);
	textBox.enterText(GlobalProduct.TXT_SCAN_CODE,randomChr);
	dropDown.selectItem(GlobalProduct.DPD_LOYALTY_MULTIPLIER, value,Constants.TEXT);
	foundation.waitforElementToBeVisible(GlobalProduct.BTN_SAVE_EXTEND,3);
	foundation.click(GlobalProduct.BTN_SAVE_EXTEND);
	foundation.waitforElementToBeVisible(GlobalProduct.POPUP_DROPDOWN,5);
	foundation.click(GlobalProduct.POPUP_DROPDOWN);
	foundation.click(selectLocationForProduct(location));
	foundation.click(GlobalProduct.POPUP_DROPDOWN);
	foundation.waitforElementToBeVisible(GlobalProduct.LBL_SAVE_DONE,3);
	foundation.click(GlobalProduct.LBL_SAVE_DONE);
	}
	
}
