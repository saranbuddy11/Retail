package at.smartshop.pages;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.loading.PrivateClassLoader;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import at.framework.browser.Factory;
import at.framework.generic.CustomisedAssert;
import at.framework.generic.Numbers;
import at.framework.generic.Strings;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.tests.TestInfra;

public class GlobalProduct extends Factory {

	private Foundation foundation = new Foundation();
	private TextBox textBox = new TextBox();
	private Dropdown dropDown = new Dropdown();
	private NavigationBar navigationBar = new NavigationBar();
	private Numbers number = new Numbers();
	private Strings strings = new Strings();
	private FilePath  filePath  = new FilePath(); 

	public static final By TXT_FILTER = By.id("filterType");
	public static final By ICON_FILTER = By.id("dataGrid_dd_enabled_button");
	public static final By TBL_GRID = By.id("dataGrid");
	public static final By BTN_EXPORT = By.xpath("//button[text()='Export']");
	public static final By LBL_Inches = By.xpath("//b[text()='(In Inches)']");
	public static final By TXT_RECORD_COUNT = By.cssSelector("#dataGrid_pager_label");
	public static final By TBL_ROW = By.xpath("//*[@id='dataGrid']/tbody");
	public static final By BTN_CREATE = By.cssSelector("button#createNewBtn");
	public static final By TXT_PRODUCTNAME = By.xpath("//dd//input[@id='name']");
	public static final By TXT_Product_HEIGHT = By.id("prodheight");
	public static final By TXT_Product_WIDTH = By.id("prodwidth");
	public static final By TXT_Product_DEPTH = By.id("proddepth");
	public static final By TXT_SCAN_CODE = By.xpath("//input[@name='scancode']");
	public static final By SCANCODE = By.xpath("//dt[text()='Scancode(s)']");
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
	public static final By POPUP_HEADER = By.xpath("//h4[@style='text-align:center;']");
	public static final By TXT_PRODUCT_CREATE = By.id("Product Create");
	public static final By SELECT_LOCATION = By.xpath("//input[@class='ui-igcombo-field ui-corner-all']");
	public static final By BTN_EXTEND = By.id("extend");
	public static final By DPD_LOYALTY_MULTIPLIER = By.id("pointslist");
	public static final By DPD_PICK_LIST = By.id("picklist");
	public static final By INPUT_MIN_STOCK = By.id("minstock");
	public static final By INPUT_MAX_STOCK = By.id("maxstock");
	public static final By BTN_SAVE_EXTEND = By.id("saveExtendBtn");
	public static final By INPUT_PRICE = By.id("price");
	public static final By BTN_SAVE = By.id("saveBtn");
	public static final By SHOW_DROPDOWN = By.xpath("//div[@title='Show drop-down']");
	public static final By SAVE_POPUP_BTN = By.id("nextyes");
	public static final By CANCEL_BTN = By.id("cancelBtn");
	public static final By BTN_EXTEND_LOC = By.xpath("//a[@id='extend']");
	public static final By MATCHING_RECORD = By.id("dataGrid_pager_label");
	public static final By CLICK_LOCATION = By.xpath(
			"//ul[@class='ui-igcombo-listitemholder']//li//div[text()='AutomationLocation1']//preceding-sibling::span");
	public static final By LBL_GLOBAL_PRODUCT = By.xpath("//div[@id='globalproductpagetitle']");
	public static final By LBL_PRODUCT_CREATE = By.xpath("//li[text()='Product Create']");
	public static final By POPUP_DROPDOWN = By.xpath("//div[@title='Show drop-down']");
	public static final By DISABLE_PRODUCT = By.xpath("//select[@id='isdisabled']");
	public static final By CLICK_PRODUCT = By.xpath("//tbody//td[@aria-describedby='dataGrid_name']");
	public static final By LAB_RECORD = By.xpath("//div[@class='ui-iggrid-results']");
	public static final By MATCH_RECORD = By.xpath("//span[@title='Current records range']");
	public static final By DPD_RECORD = By.xpath("//div[contains(@class,'ui-igedit-buttonimage')]");
	public static final By TXT_EXTEND = By.xpath("//input[@aria-controls='locdt']");
	public static final By EXTEND_LOCATION = By.xpath("//td[@class=' sorting_1']");
	public static final By TBL_HEADER = By.cssSelector("#dataGrid > tbody");
	public static final By TBL_EXTEND = By.xpath("//tbody[@role='alert']//tr");
	public static final By TBL_ROW_PRODUCT = By.xpath("//tbody[@role='rowgroup']//tr/td");
	public static final By SEARCH_EXTEND = By.xpath("//input[@aria-controls='locdt']");
	public static final By POPUP_SEARCH = By.id("productFilterType");
	public static final By POPUP_SAVE = By.id("modalsave");
	public static final By SAVE_MSG = By.xpath("//li[text()='DummyAutoTest added to : ']");
	public static final By LBL_PRODUCT_SUMMARY = By.xpath("//li[text()='Product Summary']");

	public static final By DPD_TAX_CATEGORY1 = By.id("category1");
	public static final By DPD_TAX_CATEGORY2 = By.id("category2");
	public static final By DPD_TAX_CATEGORY3 = By.id("category3");
	public static final By DPD_DEPOSIT_CATEGORY = By.id("depositcat");
	public static final By DPD_TYPE = By.id("type");
	public static final By DPD_DISPLAY_NEED_BY = By.id("displayNeed");
	public static final By DPD_ROUNDING = By.id("rounding");
	public static final By DPD_DISCOUNT = By.id("hasemployeediscount");
	public static final By DPD_WEIGH = By.id("weigh");
	public static final By DPD_IS_DISPLAYED = By.id("isdisabled");
	public static final By LBL_PRICE_ERROR = By.xpath("//label[@id='price-error']");
	public static final By BTN_SHOW_IMAGES = By.id("showimage");
	public static final By BTN_SMALL_CHANGE = By.id("prod-smallimg");
	public static final By BTN_LARGE_CHANGE = By.id("prod-largeimg");
	public static final By BTN_SMALL_UPLOAD_IMAGE = By.id("uploadSmall");
	public static final By BTN_LARGE_UPLOAD_IMAGE = By.id("uploadLarge");
	public static final By TXT_FILE = By.name("file");
	public static final By TXT_FILE_LARGE = By.xpath("//div[@id='uploadLarge']//child::input[@type='file']");
	public static final By LBL_SMALL_IMAGE_PREVIEW = By.id("smallimage-preview");
	public static final By LBL_LARGE_IMAGE_PREVIEW = By.id("largeimage-preview");
	public static final By LBL_SMALL_IMAGE_NAME = By.id("smallimage-name");
	public static final By LBL_LARGE_IMAGE_NAME = By.id("largeimage-name");
	public static final By LBL_IMAGE = By.xpath("//div[@class='img-block']");
	public static final By LBL_UPLOAD_SMALL_IMAGE = By.xpath("//li[@id='smallimage-fineuploader']//div");
	public static final By LBL_UPLOAD_LARGE_IMAGE = By.xpath("//li[@id='largeimage-fineuploader']//div");
	public static final By LBL_SMALL_IMAGE_POPUP = By.xpath("//h3[@id='smallimage-header']");
	public static final By LBL_LARGE_IMAGE_POPUP = By.xpath("//h3[@id='largeimage-header']");
	public static final By CHOOSE_SMALL_IMAGE = By.id("smallimage-choose");
	public static final By CHOOSE_LARGE_IMAGE = By.id("largeimage-choose");
	public static final By BTN_CANCEL = By.id("cancelBtn");

	public By selectSmallImage(String image) {
		return By.xpath("//table[@id='small-img-tbl']//i[@title='"+image+"']");
	}
	public By selectLargeImage(String image) {
		return By.xpath("//table[@id='large-img-tbl']//i[@title='"+image+"']");
	}

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
		return By.xpath("//div[@id='dataGrid_editor_list']//span[text()='" + data + "']");

	}

	public By selectLocationInExtend(String location) {
		return By.xpath("//tbody//tr//td[text()='" + location + "']");
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

	public void changeRecordDataAndVerify(By recorddata, String dbData) {
		CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProduct.LAB_RECORD));
		foundation.waitforElementToBeVisible(GlobalProduct.DPD_RECORD, 3);
		foundation.click(GlobalProduct.DPD_RECORD);
		foundation.click(recorddata);
		foundation.scrollIntoViewElement(GlobalProduct.MATCH_RECORD);
		foundation.threadWait(5);
		String record = foundation.getText(MATCH_RECORD);
		CustomisedAssert.assertTrue(record.contains(dbData));
	}

	public void validateProductDimension(String height, String width, String depth) {
		CustomisedAssert.assertTrue(foundation.isDisplayed(LBL_PRODUCT_SUMMARY));
		validateProductDimensionVisibleOrNot();
		// Enter value in product dimension
		textBox.enterText(TXT_Product_HEIGHT, height);
		textBox.enterText(TXT_Product_WIDTH, width);
		textBox.enterText(TXT_Product_DEPTH, depth);
		foundation.click(LBL_Inches);
		// verify height product dimension contains only 4 digit and only numeric data
		String expectedHeight = foundation.getAttribute(TXT_Product_HEIGHT, "value");
		boolean heightStatus = number.fetchNumberOfDecimalPoints(expectedHeight) == 4
				&& strings.verifyOnlyNumberAndDot(expectedHeight);
		CustomisedAssert.assertTrue(heightStatus);
		// verify width product dimension contains only 4 digit and only numeric data
		String expectedWidth = foundation.getAttribute(TXT_Product_WIDTH, "value");
		boolean widthStatus = number.fetchNumberOfDecimalPoints(expectedWidth) == 4
				&& strings.verifyOnlyNumberAndDot(expectedWidth);
		CustomisedAssert.assertTrue(widthStatus);
		// verify depth product dimension contains only 4 digit and only numeric data
		String expectedDepth = foundation.getAttribute(TXT_Product_DEPTH, "value");
		boolean depthStatus = number.fetchNumberOfDecimalPoints(expectedDepth) == 4
				&& strings.verifyOnlyNumberAndDot(expectedDepth);
		CustomisedAssert.assertTrue(depthStatus);
		// clear dimension value
		textBox.clearText(TXT_Product_HEIGHT);
		textBox.clearText(TXT_Product_WIDTH);
		textBox.clearText(TXT_Product_DEPTH);
		validateProductDimensionVisibleOrNot();
		// click save button without entering dimension
		foundation.click(BTN_SAVE);
		CustomisedAssert.assertTrue(foundation.isDisplayed(LBL_GLOBAL_PRODUCT));

		// verify for dimension for new global product
		foundation.click(BTN_CREATE);
		validateProductDimensionVisibleOrNot();
	}

	public void validateProductDimensionVisibleOrNot() {
		// verify product dimension is Available or not
		CustomisedAssert.assertTrue(foundation.isDisplayed(TXT_Product_HEIGHT));
		CustomisedAssert.assertTrue(foundation.isDisplayed(TXT_Product_WIDTH));
		CustomisedAssert.assertTrue(foundation.isDisplayed(TXT_Product_DEPTH));
	}

	/**
	 * search product and edit product name and save
	 * 
	 * @param product
	 * @param editproduct
	 */
	public void searchProductAndUpdateProductNameInGlobalProducts(String product, String editproduct) {
		CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProduct.TXT_GLOBAL_PRODUCT));
		foundation.waitforElementToBeVisible(GBL_PRODUCT_DATA, 5);
		textBox.enterText(LocationList.TXT_FILTER, product);
		foundation.threadWait(3);
		foundation.waitforElementToBeVisible(GlobalProduct.MATCHING_RECORD, Constants.SHORT_TIME);
		foundation.click(getGlobalProduct(product));
		CustomisedAssert.assertTrue(foundation.isDisplayed(ProductSummary.LBL_PRODUCT_SUMMMARY));
		textBox.enterText(ProductSummary.TXT_PRODUCT_NAME, editproduct);
		foundation.waitforElementToBeVisible(ProductSummary.BTN_SAVE, Constants.SHORT_TIME);
		foundation.click(ProductSummary.BTN_SAVE);
		// foundation.threadWait(Constants.SHORT_TIME);

	}

	/**
	 * Disable created product in GlobalProduct
	 * 
	 * @param product
	 * @param editproduct
	 */
	public void disableProduct(String editoption, String product) {
		foundation.click(GlobalProduct.CLICK_PRODUCT);
		foundation.waitforElementToBeVisible(GlobalProduct.DISABLE_PRODUCT, 3);
		dropDown.selectItem(GlobalProduct.DISABLE_PRODUCT, editoption, Constants.TEXT);
		foundation.waitforElementToBeVisible(GlobalProduct.BTN_SAVE, 5);
		foundation.scrollIntoViewElement(GlobalProduct.BTN_SAVE);
		foundation.click(GlobalProduct.BTN_SAVE);
		foundation.waitforElementToBeVisible(GlobalProduct.TXT_FILTER, 5);
		textBox.enterText(LocationList.TXT_FILTER, product);
		CustomisedAssert.assertFalse(foundation.getText(GlobalProduct.CLICK_PRODUCT).equals(product));

	}

	/**
	 * create Product In Global Product Page
	 * 
	 * @param name
	 * @param price
	 * @param scancode
	 */
	public void createProducInGlobalProductPage(String name, String price, String scancode) {
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
	 * 
	 * @param name
	 * @param price
	 * @param scancode
	 */
	public void createProducInGlobalProductPageWithLocation(String name, String price, String location,
			String randomChr) {
		textBox.enterText(GlobalProduct.TXT_PRODUCTNAME, name);
		textBox.enterText(GlobalProduct.TXT_PRICE, price);
		textBox.enterText(GlobalProduct.TXT_SCAN_CODE, randomChr);
		foundation.threadWait(Constants.THREE_SECOND);
		foundation.waitforElementToBeVisible(GlobalProduct.BTN_SAVE_EXTEND, 3);
		foundation.click(GlobalProduct.BTN_SAVE_EXTEND);
		foundation.threadWait(Constants.THREE_SECOND);
		foundation.waitforElementToBeVisible(GlobalProduct.POPUP_DROPDOWN, 5);
		foundation.click(GlobalProduct.POPUP_DROPDOWN);
		foundation.threadWait(Constants.THREE_SECOND);
		foundation.click(selectLocationForProduct(location));
		foundation.click(GlobalProduct.POPUP_DROPDOWN);
		foundation.waitforElementToBeVisible(GlobalProduct.LBL_SAVE_DONE, 3);
		foundation.click(GlobalProduct.LBL_SAVE_DONE);
		foundation.threadWait(3);
		
	}

	/**
	 * Add new location in extend and verify the loyalty value in extend location
	 * are same
	 * 
	 * @param location
	 * 
	 */
	public void verifyAddLocationInExtend(String location) {
		foundation.waitforElementToBeVisible(GlobalProduct.BTN_EXTEND_LOC, 3);
		foundation.threadWait(Constants.THREE_SECOND);
		foundation.click(GlobalProduct.BTN_EXTEND_LOC);
		foundation.waitforElementToBeVisible(GlobalProduct.POPUP_SEARCH, 3);
		foundation.threadWait(Constants.THREE_SECOND);
		foundation.click(GlobalProduct.POPUP_SEARCH);
		textBox.enterText(GlobalProduct.POPUP_SEARCH, location);
		foundation.threadWait(Constants.THREE_SECOND);
		foundation.click(selectLocationInExtend(location));
		foundation.waitforElementToBeVisible(GlobalProduct.POPUP_SAVE, 3);
		foundation.threadWait(Constants.THREE_SECOND);
		foundation.click(GlobalProduct.POPUP_SAVE);
		foundation.threadWait(Constants.THREE_SECOND);
		foundation.waitforElementToBeVisible(GlobalProduct.SAVE_MSG, 15);
		CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProduct.SAVE_MSG));
		foundation.scrollIntoViewElement(GlobalProduct.SEARCH_EXTEND);
		foundation.waitforElementToBeVisible(GlobalProduct.SEARCH_EXTEND, 5);
		foundation.click(GlobalProduct.SEARCH_EXTEND);
		foundation.threadWait(Constants.THREE_SECOND);
		textBox.enterText(GlobalProduct.SEARCH_EXTEND, location);

	}

	/**
	 * create Product In Global Product Page
	 * 
	 * @param name
	 * @param price
	 * @param scancode
	 */
	public void createProducInGlobalProductLoyaltyWithLocation(String name, String price, String value,
			String randomChr, String location) {
		CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProduct.LBL_GLOBAL_PRODUCT));

		foundation.click(GlobalProduct.BTN_CREATE);
		foundation.isDisplayed(GlobalProduct.LBL_PRODUCT_CREATE);
		textBox.enterText(GlobalProduct.TXT_PRODUCTNAME, name);
		textBox.enterText(GlobalProduct.TXT_PRICE, price);
		textBox.enterText(GlobalProduct.TXT_SCAN_CODE, randomChr);
		dropDown.selectItem(GlobalProduct.DPD_LOYALTY_MULTIPLIER, value, Constants.TEXT);
		foundation.waitforElementToBeVisible(GlobalProduct.BTN_SAVE_EXTEND, 3);
		foundation.click(GlobalProduct.BTN_SAVE_EXTEND);
		foundation.waitforElementToBeVisible(GlobalProduct.POPUP_DROPDOWN, 5);
		foundation.click(GlobalProduct.POPUP_DROPDOWN);
		foundation.click(selectLocationForProduct(location));
		foundation.click(GlobalProduct.POPUP_DROPDOWN);
		foundation.waitforElementToBeVisible(GlobalProduct.LBL_SAVE_DONE, 3);
		foundation.click(GlobalProduct.LBL_SAVE_DONE);
	}

	/**
	 * update Price Value
	 * 
	 * @param name
	 * @param price
	 */
	public void updatePriceValueInGlobalProduct(String name, String price) {
		CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProduct.LBL_GLOBAL_PRODUCT));
		selectGlobalProduct(name);
		CustomisedAssert.assertTrue(foundation.isDisplayed(ProductSummary.LBL_PRODUCT_SUMMMARY));
		textBox.enterText(ProductSummary.PRICE_FIELD, price);
		foundation.scrollIntoViewElement(ProductSummary.BTN_SAVE);
		foundation.click(ProductSummary.BTN_SAVE);
	}

	/**
	 * verify Price Value
	 * @param name
	 * @param price
	 */
	public void verifyUpdatedPriceValueInGlobalProduct(String name, String price) {
		CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProduct.LBL_GLOBAL_PRODUCT));
		foundation.waitforElementToBeVisible(GlobalProduct.TBL_ROW_PRODUCT, 3);
		textBox.enterText(GlobalProduct.TXT_FILTER, name);
		CustomisedAssert.assertTrue(foundation.getTextofListElement(GlobalProduct.TBL_ROW_PRODUCT).contains(price));
	}

	/**
	 * verify all option in dropdown by their text value and count
	 * 
	 * @param Object
	 * @param Count
	 * @param allOptions
	 */
	public void verifyDropDownOptionsTextAndCount(By Object, int Count, List<String> allOptions) {
		List<String> allItems = dropDown.getAllItems(Object);
		CustomisedAssert.assertTrue(allItems.size() == Count);
		for (int i = 0; i < allItems.size(); i++) {
			CustomisedAssert.assertEquals(allItems.get(i), allOptions.get(i));
		}
		CustomisedAssert.assertFalse(dropDown.verifyMultipleSelectPossible(Object));
	}

	/**
	 * verify Drop down options Available
	 * 
	 * @param Object
	 */
	public void verifyDropDownOptionsAvailable(By Object) {
		List<String> allItems = dropDown.getAllItems(Object);
		CustomisedAssert.assertTrue(allItems.size() > 18);
		for (String item : allItems) {
			CustomisedAssert.assertEquals(item.length() > 1, item != null);
		}
		CustomisedAssert.assertFalse(dropDown.verifyMultipleSelectPossible(Object));

	}

	/**
	 * verify global product with out enter mandatory fields
	 * 
	 * @param productName
	 * @param location
	 * @param scanError
	 * @param priceError
	 */
	public void verifyGlobalProductMandotoryFields(String productName, String location, String scanError,
			String priceError) {
		foundation.waitforElement(TXT_GLOBAL_PRODUCT, Constants.THREE_SECOND);
		CustomisedAssert.assertTrue(foundation.isDisplayed(TXT_GLOBAL_PRODUCT));
		foundation.click(BTN_CREATE);
		CustomisedAssert.assertTrue(foundation.isDisplayed(TXT_PRODUCT_CREATE));
		textBox.enterText(TXT_PRODUCTNAME, productName);
		foundation.click(BTN_SAVE_EXTEND);
		foundation.waitforElementToBeVisible(SELECT_LOCATION, Constants.THREE_SECOND);
		textBox.enterText(SELECT_LOCATION, location);
		foundation.click(LBL_SAVE_DONE);
		CustomisedAssert.assertTrue(foundation.isDisplayed(LBL_SCANCODE_ERROR));
		CustomisedAssert.assertTrue(foundation.isDisplayed(LBL_PRICE_ERROR));
		CustomisedAssert.assertEquals(foundation.getText(LBL_SCANCODE_ERROR), scanError);
		CustomisedAssert.assertEquals(foundation.getText(LBL_PRICE_ERROR), priceError);

	}

	/**
	 * Upload a image for small image from system file
	 * 
	 * @param name
	 * @param Filepath
	 * @throws Exception
	 */
	public void uploadSmallImage(String name, String Filepath) throws Exception {
		CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProduct.TXT_GLOBAL_PRODUCT));
		foundation.threadWait(Constants.THREE_SECOND);
		foundation.click(GlobalProduct.BTN_CREATE);
		foundation.threadWait(Constants.THREE_SECOND);
		foundation.isDisplayed(GlobalProduct.TXT_PRODUCT_CREATE);
		textBox.enterText(TXT_PRODUCTNAME, name);
		foundation.threadWait(Constants.THREE_SECOND);
		foundation.waitforElementToBeVisible(BTN_SHOW_IMAGES, Constants.THREE_SECOND);
		foundation.click(BTN_SHOW_IMAGES);
		foundation.threadWait(Constants.THREE_SECOND);
		foundation.waitforElementToBeVisible(BTN_SMALL_CHANGE, Constants.THREE_SECOND);
		foundation.click(BTN_SMALL_CHANGE);
		foundation.threadWait(Constants.THREE_SECOND);
		foundation.waitforElementToBeVisible(BTN_SMALL_UPLOAD_IMAGE, Constants.THREE_SECOND);
		foundation.threadWait(Constants.THREE_SECOND);
		File uploadFile = filePath.copyFile(Filepath);
		textBox.enterText(TXT_FILE, filePath.getFileAbsolutePath(uploadFile));
		foundation.threadWait(6);
		CustomisedAssert.assertTrue(foundation.isDisplayed(LBL_SMALL_IMAGE_PREVIEW));
		CustomisedAssert.assertTrue(
				filePath.getFileAbsolutePath(uploadFile).contains(foundation.getText(LBL_SMALL_IMAGE_NAME)));
		filePath.deleteFile(filePath.getFileAbsolutePath(uploadFile));
	}

	/**
	 * upload small image with image size is greater than 2MB
	 * 
	 * @param name
	 * @param Filepath
	 * @param message
	 * @throws Exception
	 */
	public void uploadSmallImage2MB(String name, String Filepath, String message) throws Exception {
		CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProduct.TXT_GLOBAL_PRODUCT));
		foundation.threadWait(Constants.THREE_SECOND);
		foundation.click(GlobalProduct.BTN_CREATE);
		foundation.threadWait(Constants.THREE_SECOND);
		foundation.isDisplayed(GlobalProduct.TXT_PRODUCT_CREATE);
		textBox.enterText(TXT_PRODUCTNAME, name);
		foundation.threadWait(Constants.THREE_SECOND);
		foundation.waitforElementToBeVisible(BTN_SHOW_IMAGES, Constants.THREE_SECOND);
		foundation.click(BTN_SHOW_IMAGES);
		foundation.threadWait(Constants.THREE_SECOND);
		foundation.waitforElementToBeVisible(BTN_SMALL_CHANGE, Constants.THREE_SECOND);
		foundation.click(BTN_SMALL_CHANGE);
		foundation.threadWait(Constants.THREE_SECOND);
		foundation.waitforElementToBeVisible(BTN_SMALL_UPLOAD_IMAGE, Constants.THREE_SECOND);
		foundation.threadWait(Constants.THREE_SECOND);
		textBox.enterText(TXT_FILE, Filepath);
		foundation.threadWait(Constants.THREE_SECOND);
		foundation.clickEnter();
		String actualMessage = foundation.WaitForAlertAndFetchText(true);
		CustomisedAssert.assertTrue(actualMessage.contains(message));
	}

	/**
	 * upload image for large image from system file
	 * 
	 * @param name
	 * @param Filepath
	 * @throws Exception
	 */
	public void uploadLargeImage(String name, String Filepath) throws Exception {
		CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProduct.TXT_GLOBAL_PRODUCT));
		foundation.threadWait(Constants.THREE_SECOND);
		foundation.click(GlobalProduct.BTN_CREATE);
		foundation.threadWait(Constants.THREE_SECOND);
		foundation.isDisplayed(GlobalProduct.TXT_PRODUCT_CREATE);
		foundation.waitforElementToBeVisible(BTN_SHOW_IMAGES, Constants.THREE_SECOND);
		foundation.click(BTN_SHOW_IMAGES);
		foundation.threadWait(Constants.THREE_SECOND);
		foundation.waitforElementToBeVisible(BTN_LARGE_CHANGE, Constants.THREE_SECOND);
		foundation.click(BTN_LARGE_CHANGE);
		foundation.threadWait(Constants.THREE_SECOND);
		foundation.waitforElementToBeVisible(BTN_LARGE_UPLOAD_IMAGE, Constants.THREE_SECOND);
		foundation.threadWait(Constants.THREE_SECOND);
		File uploadFile = filePath.copyFile(Filepath);
		textBox.enterText(TXT_FILE_LARGE, filePath.getFileAbsolutePath(uploadFile));
		foundation.threadWait(6);
		foundation.threadWait(Constants.THREE_SECOND);
		CustomisedAssert.assertTrue(foundation.isDisplayed(LBL_LARGE_IMAGE_PREVIEW));
		CustomisedAssert.assertTrue(
				filePath.getFileAbsolutePath(uploadFile).contains(foundation.getText(LBL_LARGE_IMAGE_NAME)));
		filePath.deleteFile(filePath.getFileAbsolutePath(uploadFile));
	}

	/**
	 * upload large image with size greater than 2MB 
	 * 
	 * @param name
	 * @param Filepath
	 * @param message
	 * @throws Exception
	 */
	public void uploadLargeImage2MB(String name, String Filepath, String message) throws Exception {
		CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProduct.TXT_GLOBAL_PRODUCT));
		foundation.click(GlobalProduct.BTN_CREATE);
		foundation.threadWait(Constants.THREE_SECOND);
		foundation.isDisplayed(GlobalProduct.TXT_PRODUCT_CREATE);
		foundation.threadWait(Constants.THREE_SECOND);
		foundation.waitforElementToBeVisible(BTN_SHOW_IMAGES, Constants.THREE_SECOND);
		foundation.click(BTN_SHOW_IMAGES);
		foundation.threadWait(Constants.THREE_SECOND);
		foundation.waitforElementToBeVisible(BTN_LARGE_CHANGE, Constants.THREE_SECOND);
		foundation.click(BTN_LARGE_CHANGE);
		foundation.threadWait(Constants.THREE_SECOND);
		foundation.waitforElementToBeVisible(BTN_LARGE_UPLOAD_IMAGE, Constants.THREE_SECOND);
		foundation.threadWait(Constants.THREE_SECOND);
		textBox.enterText(TXT_FILE_LARGE, Filepath);
		foundation.threadWait(2);
		foundation.clickEnter();
		String actualMessage = foundation.WaitForAlertAndFetchText(true);
		CustomisedAssert.assertTrue(actualMessage.contains(message));
	}

	/**
	 * Upload Small Image In Global Product
	 * 
	 * @param data
	 */
	public void uploadSmallImageInGlobalProduct(String data) {
		foundation.threadWait(Constants.THREE_SECOND);
	foundation.click(BTN_SMALL_CHANGE);
	foundation.threadWait(Constants.THREE_SECOND);
	foundation.click(CHOOSE_SMALL_IMAGE);
	foundation.threadWait(Constants.THREE_SECOND);
	foundation.waitforElementToBeVisible(selectSmallImage(data), 3);
	foundation.threadWait(Constants.THREE_SECOND);
	foundation.click(selectSmallImage(data));
	CustomisedAssert.assertTrue(foundation.getText(LBL_SMALL_IMAGE_NAME).equals(data));
}
	
	/**
	 * Upload large Image In Global Product
	 * 
	 * @param data
	 */
	public void uploadLargeImageInGlobalProduct(String data) {
		foundation.threadWait(Constants.THREE_SECOND);
		foundation.click(BTN_LARGE_CHANGE);
		foundation.threadWait(Constants.THREE_SECOND);
		foundation.click(CHOOSE_LARGE_IMAGE);
		foundation.waitforElementToBeVisible(selectLargeImage(data), 3);
		foundation.threadWait(Constants.THREE_SECOND);
		foundation.click(selectLargeImage(data));
		CustomisedAssert.assertTrue(foundation.getText(LBL_LARGE_IMAGE_NAME).equals(data));
}
}
