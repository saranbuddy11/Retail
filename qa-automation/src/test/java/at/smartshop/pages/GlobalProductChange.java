package at.smartshop.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import java.awt.AWTException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import at.framework.browser.Factory;
import at.framework.generic.CustomisedAssert;
import at.framework.ui.CheckBox;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.Table;
import at.framework.ui.TextBox;
import at.smartshop.keys.Constants;
import at.smartshop.tests.TestInfra;

public class GlobalProductChange extends Factory {

	private TextBox textBox = new TextBox();
	private Dropdown dropDown = new Dropdown();
	private Foundation foundation = new Foundation();
	private CheckBox checkBox = new CheckBox();
	private Table table = new Table();

	public static final By TXT_LOCATION_SEARCH = By.id("loc-search");
	public static final By TBL_LOCATION_LIST = By.id("location-list");
	public static final By BTN_LOCATION_APPLY = By.id("loc-filter-apply");
	public static final By BTN_PRODUCT_APPLY = By.id("prd-filter-apply");
	public static final By TAB_PRODUCT = By.cssSelector("#li2 > a");
	public static final By BTN_NEXT = By.id("prd-dt-next");
	public static final By DPD_PICKLISTACTION = By.id("prd-pick-list-action");
	public static final By DPD_DISPLAY_NEEDBY = By.id("prd-display-need-by");
	public static final By TXT_TAX1 = By.id("prd-tax-1");
	public static final By TXT_MIN = By.id("prd-min");
	public static final By TXT_MAX = By.id("prd-max");
	public static final By TBL_ROW_RECORD = By.xpath("//tbody[@role='alert']");
	public static final By TXT_TAX2=By.id("prd-tax-2");
	public static final By TAX2_CHECKED=By.id("prd-tax-2-checked");
	public static final By BTN_INCREMENT = By.xpath("//*[@id='increment-radio-btn']/..");
	public static final By TXT_PRICE = By.id("prd-price");
	public static final By TXT_PRODUCT_NAME = By.id("filter-name");
	public static final By LBL_PRICE = By.xpath("//*[@id='prd-change-form']/dt[text()='Price']");
	public static final By LBL_MIN = By.xpath("//*[@id='prd-change-form']/dt[text()='Min']");
	public static final By LBL_OPERATOR_MIN = By.xpath("//dt[text()='Min']");
	public static final By LBL_MAX = By.xpath("//*[@id='prd-change-form']/dt[text()='Max']");
	public static final By DEPOSITE_CHECKED = By.id("prd-deposit-checked");
	public static final By DPD_LOYALITY_MULTIPLIER = By.id("prd-loyalty-multiplier");
	public static final By BTN_SUBMIT = By.id("prd-update-submit");
	public static final By BTN_OK = By.xpath("//button[@class='ajs-button ajs-ok']");
	public static final By MSG_SUCCESS = By.xpath("//div[text()='Updated 1 product(s)!']");
	public static final By RDO_OPERATOR_PRODUCT_CHANGE = By.xpath("//label[text()='Operator Product Catalog Change']");
	public static final By TXT_LOCATION_PRODUCT = By.id("filter-name");
	public static final By REASON_BTNOK = By.id("gpcokbtn");
	public static final By REASONBOX_BTNOK = By.xpath("//button[@id='gpcokbtn']");
	public static final By TXT_HEADER = By.id("Global Product Change");
	public static final By GPC_lOCATION = By.id("global-prd");
	public static final By SELECT_ALL = By.id("loc-select-all");
	public static final By SELECT_COUNT = By.id("select-count");
	public static final By FILTER_PRODUCTS_CONTENT = By.id("filter-content");
	public static final By OPS_LOCATION = By.id("operator-prd");
	public static final By DESELECT_ALL = By.id("loc-deselect-all");
	public static final By FILTER_PRODUCT = By.id("filter-prd-title");
	public static final By OPS_SELECT_ALL = By.id("prd-select-all");
	public static final By OPS_DESELECT_ALL = By.id("prd-deselect-all");
	public static final By ROUNDING = By.id("prd-rounding");
	public static final By PRICE_CHECKBOX = By.id("prd-price-checked");
	public static final By INPUT_TEXT=By.xpath("//input[@class='ajs-input']");
	public static final By BUTTON_OK=By.id("gpcUpdateokbtn");
	public static final By MIN_CHECKEDBOX = By.id("prd-min-checked");
	public static final By MAX_CHECKEDBOX = By.id("prd-max-checked");
	public static final By ACTION_CHECKEDBOX = By.id("prd-pick-list-action-checked");
	public static final By LOYALITY_CHECKEDBOX = By.id("prd-loyalty-multiplier-checked");
	public static final By DISPLAY_CHECKBOX = By.id("prd-display-need-by");
	public static final By TBL_GRID_PRODUCTS = By.cssSelector("#filtered-prd-dt > tbody > tr");
	public static final By TAX1_CHECKED=By.id("prd-tax-1-checked");
	public static final By ROUNDING_CHECKBOX = By.id("prd-display-need-by");
	public static final By TXT_PROMPT_MSG = By.xpath("//div[text()='Confirm Global Product Change for Location(s)']");
	public static final By PROMT_OPERATOR_PRODUCT = By.xpath("//div[text()='Confirm Operator Product Catalog Change']");
	public static final By TXT_PROMPT_CONTENT = By.xpath("//div[@class='ajs-content']");
	public static final By BTN_CANCEL = By.xpath("//button[@class='ajs-button ajs-cancel']");
	public static final By PRODUCT_FIELD = By.id("prd-name");
	public static final By PRODUCT_CHECKOUT = By.id("prd-name-checked");
	public static final By PROMPT_MESSAGE=By.id("gpc-UpdatepopupBody");
	public static final By BUTTON_CANCEL=By.id("gpcUpdateCancelbtn");
	public static final By COST_FIELD = By.id("prd-cost");
	public static final By COST_CHECKOUT = By.id("prd-cost-checked");
	public static final By CATEGORY_ONE = By.id("prd-cate-1");
	public static final By CATEGORYONE_CHECKBOX = By.id("prd-cate-1-checked");
	public static final By CATEGORY_TWO = By.id("prd-cate-2");
	public static final By CATEGORYTWO_CHECKBOX = By.id("prd-cate-2-checked");
	public static final By CATEGORY_THREE = By.id("prd-cate-3");
	public static final By CATEGORYTHREE_CHECKBOX = By.id("prd-cate-3-checked");
	public static final By TAX_CATEGORY = By.id("prd-tax-cate");
	public static final By TAX_CATEGORY_CHECKBOX = By.id("prd-tax-cate-checked");
	public static final By DEPOSIT_CATEGORY = By.id("prd-deposit-cate");
	public static final By DEPOSIT_CAT = By.id("prd-deposit");
	public static final By CASE_COUNT = By.id("prd-case");
	public static final By CASE_COUNT_CHECKBOX = By.id("prd-case-checked");
	public static final By TABLE_GRID_HISTORY = By.cssSelector("#gpchistory > tbody >tr");
	public static final By HISTORY_BTN = By
			.xpath("//div[@style='display:inline-block;float: right;']//button[text()='History']");
	public static final By HISTORY_GPC = By.xpath("//h4[text()='Global Product Change History']");
	public static final By HEADER_DATA = By.xpath("//tr[@role='row']");
	public static final By GRID_HEADER = By.id("gpchistory");
	public static final By GRID_CONTENT = By.id("filtered-prd-dt");
	public static final By LBL_OPERATOR_MAX = By.xpath("//dt[text()='Max']");
	public static final By LBL_PICK_LIST = By.xpath("//*[@id='prd-change-form']/dt[text()='Pick List Action']");
	public static final By LBL_OPERATOR_PICK_LIST = By.xpath("//dt[text()='Pick List Action']");
	public static final By POP_UP_HEADER = By.cssSelector("div.ajs-header");
	public static final By REASONBOX_TITLE = By.cssSelector("h4.modal-title");
	public static final By REASONBOX_BODY = By.cssSelector("div.modal1-body>p");
	
	public static final By LBL_GPC = By.id("Global Product Change");
	public static final By GPC_CHECK_BOX = By.id("global-prd");
	public static final By OPC_CHECK_BOX = By.id("operator-prd");
	public static final By LBL_FILTERED_PRODUCTS = By.id("filter-prd-title");
	public static final By TXT_PRODUCT_SEARCH = By.xpath("//input[@placeholder='Product Name']");
	public static final By LBL_PRODUCT_FIELD_CHANGE = By.id("product-change-title");
	public static final By CHK_PRODUCT_PRICE = By.id("prd-price-checked");
	public static final By CHK_PRODUCT_MIN = By.id("prd-min-checked");
	public static final By CHK_PRODUCT_MAX = By.id("prd-max-checked");
	public static final By CHK_PRODUCT_PICK_LIST = By.id("prd-pick-list-action-checked");
	public static final By DPD_PICK_LIST = By.id("prd-pick-list-action");
	public static final By DPD_LOYALTY_MULTIPLIER = By.id("prd-loyalty-multiplier");
	public static final By CHK_PRODUCT_LOYALTY_MULTIPLIER = By.id("prd-loyalty-multiplier-checked");
	public static final By DPD_FILTER_BY = By.id("filter-by");
	public static final By LBL_UPDATE = By.xpath("//label[@class='checked']");
	public static final By TABLE_RECORD = By.xpath("//div[@id='prd-dt-paging']//a[contains(@id,'page')]");
	public static final By TBL_ROW_DATA = By.xpath("//tr[@class='odd']");
	public static final By CHECK_ALL_LOC = By.xpath("//input[@name='all-locs']");
	public static final By CONFIRM_SUMITTED = By.xpath("//h4[contains(text(),'Change Submitted')]");
	public static final By CONFIRM_CHANGE_OPC = By.xpath("//div[contains(text(),' Product Catalog Change')]");
	public static final By DPD_REMOVE=By.xpath("//select[@id='prd-remove-product-loc']");
	public static final By CHK_GREENCIRCLE=By.id("prd-remove-product-loc-checked");
	public static final By TXT_CONFIRM_POPUP=By.xpath("//input[@class='ajs-input']");
	public static final By POPUP_OK=By.xpath("//button[@class='btn btn-light']");
	public static final By TXT_TAX2_PRODUCT=By.xpath("//input[@id='tax-2']");
	public static final By COL_TAX2_PRODUCT=By.xpath("(//tr[@class='odd']//td)[11]");
	public static final By TABLE_TAX2_COL=By.xpath("//table[@id='filtered-prd-dt']//tbody//td[11]");


	public By objTableRow(String location) {
		return By.xpath("//table[@id='filtered-prd-dt']//tbody//span[text()='" + location + "']");
	}

	public By objTableDataProduct(String product) {
		return By.xpath("//table[@id='filtered-prd-dt']//tbody//td[text()='" + product + "']");
	}

	public By objTableDataOperatorProduct(String product) {
		return By.xpath("//table[@id='filtered-prd-dt']//tbody//td/span[text()='" + product + "']");

	}

	public By objLocation(String location) {
		return By.xpath("//ul[@id='location-list']//li[text()='" + location + "']");
	}

	public By objProductName(String productName) {
		return By.xpath("//span[text()='" + productName + "']");

	}
	

	private List<String> tableHeaders = new ArrayList<>();
	private Map<Integer, Map<String, String>> tableData = new LinkedHashMap<>();

	public void productFieldChange(List<String> data) {
		textBox.enterText(TXT_PRICE, data.get(0));
		CustomisedAssert.assertTrue(foundation.isDisplayed(PRICE_CHECKBOX));
		textBox.enterText(TXT_MIN, data.get(1));
		CustomisedAssert.assertTrue(foundation.isDisplayed(MIN_CHECKEDBOX));
		textBox.enterText(TXT_MAX, data.get(2));
		CustomisedAssert.assertTrue(foundation.isDisplayed(MAX_CHECKEDBOX));
		dropDown.selectItem(DPD_PICKLISTACTION, data.get(3), Constants.TEXT);
		CustomisedAssert.assertTrue(foundation.isDisplayed(ACTION_CHECKEDBOX));
		dropDown.selectItem(DPD_LOYALITY_MULTIPLIER, data.get(4), Constants.TEXT);
		CustomisedAssert.assertTrue(foundation.isDisplayed(LOYALITY_CHECKEDBOX));
		dropDown.selectItem(DPD_DISPLAY_NEEDBY, data.get(5), Constants.TEXT);
		CustomisedAssert.assertTrue(foundation.isDisplayed(DISPLAY_CHECKBOX));
		dropDown.selectItem(ROUNDING, data.get(6), Constants.TEXT);
		CustomisedAssert.assertTrue(foundation.isDisplayed(ROUNDING_CHECKBOX));
	}

	public void productFieldChangeInopc(List<String> data) {
		textBox.enterText(PRODUCT_FIELD, data.get(0));
		CustomisedAssert.assertTrue(foundation.isDisplayed(PRODUCT_CHECKOUT));
		textBox.enterText(COST_FIELD, data.get(1));
		CustomisedAssert.assertTrue(foundation.isDisplayed(COST_CHECKOUT));
		textBox.enterText(TXT_PRICE, data.get(2));
		CustomisedAssert.assertTrue(foundation.isDisplayed(PRICE_CHECKBOX));
		textBox.enterText(TXT_MIN, data.get(3));
		CustomisedAssert.assertTrue(foundation.isDisplayed(MIN_CHECKEDBOX));
		textBox.enterText(TXT_MAX, data.get(4));
		CustomisedAssert.assertTrue(foundation.isDisplayed(MAX_CHECKEDBOX));
		dropDown.selectItem(DPD_PICKLISTACTION, data.get(5), Constants.TEXT);
		CustomisedAssert.assertTrue(foundation.isDisplayed(ACTION_CHECKEDBOX));
		dropDown.selectItem(DPD_DISPLAY_NEEDBY, data.get(6), Constants.TEXT);
		CustomisedAssert.assertTrue(foundation.isDisplayed(DISPLAY_CHECKBOX));
		dropDown.selectItem(ROUNDING, data.get(7), Constants.TEXT);
		CustomisedAssert.assertTrue(foundation.isDisplayed(ROUNDING_CHECKBOX));
		dropDown.selectItem(DPD_LOYALITY_MULTIPLIER, data.get(8), Constants.TEXT);
		CustomisedAssert.assertTrue(foundation.isDisplayed(LOYALITY_CHECKEDBOX));
		dropDown.selectItem(CATEGORY_ONE, data.get(9), Constants.TEXT);
		CustomisedAssert.assertTrue(foundation.isDisplayed(CATEGORYONE_CHECKBOX));
		dropDown.selectItem(CATEGORY_TWO, data.get(10), Constants.TEXT);
		CustomisedAssert.assertTrue(foundation.isDisplayed(CATEGORYTWO_CHECKBOX));
		dropDown.selectItem(CATEGORY_THREE, data.get(11), Constants.TEXT);
		CustomisedAssert.assertTrue(foundation.isDisplayed(CATEGORYTHREE_CHECKBOX));
		dropDown.selectItem(TAX_CATEGORY, data.get(12), Constants.TEXT);
		CustomisedAssert.assertTrue(foundation.isDisplayed(TAX_CATEGORY_CHECKBOX));
		dropDown.selectItem(DEPOSIT_CATEGORY, data.get(13), Constants.TEXT);
		textBox.enterText(CASE_COUNT, data.get(14));
		CustomisedAssert.assertTrue(foundation.isDisplayed(CASE_COUNT_CHECKBOX));

	}

	public Map<Integer, Map<String, String>> getTblRecordsUI() {
		try {
			int recordCount = 0;
			tableHeaders.clear();
			WebElement tableList = getDriver().findElement(TABLE_GRID_HISTORY);
			WebElement table = getDriver().findElement(GRID_HEADER);
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

	/**
	 * get table record datas from ui
	 * 
	 * @return
	 */
	public Map<Integer, Map<String, String>> getTablelRecordsUI() {
		try {
			int recordCount = 0;
			tableHeaders.clear();
			WebElement tableList = getDriver().findElement(TBL_GRID_PRODUCTS);
			WebElement table = getDriver().findElement(GRID_CONTENT);
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
   
	/**
	 * verify the records data in UI
	 * @param header
	 * @param value
	 */
	public void verifyRecordData(String header,String value) {
		// verify the print group field
		Map<Integer, Map<String, String>> uiTableData = getTablelRecordsUI();
		Map<String, String> innerMap = new HashMap<>();
		String innerValue = "";
		for (int i = 0; i < uiTableData.size(); i++) {
			innerMap = uiTableData.get(i);
			innerValue = innerMap.get(header);
			CustomisedAssert.assertEquals(innerValue, value);
		}
		uiTableData.clear();
	}

	/**
	 * searching a Products In Products Filter And Verify The Datas in the Grid
	 * 
	 * @param product
	 */
	public void searchingProductsInProductsFilterAndVerifyTheDatas(String product) {
		foundation.click(TAB_PRODUCT);
		foundation.waitforElementToBeVisible(TXT_PRODUCT_NAME, 3);
		textBox.enterText(TXT_PRODUCT_NAME, product);
		foundation.click(BTN_PRODUCT_APPLY);
		foundation.threadWait(Constants.SHORT_TIME);
		CustomisedAssert.assertTrue(foundation.isDisplayed(FILTER_PRODUCTS_CONTENT));
	}

	/**
	 * select location and click on apply
	 * 
	 * @param Location
	 */
	public void selectLocationAndClickOnApply(By Location) {
		checkBox.isChecked(GlobalProductChange.GPC_lOCATION);
		foundation.click(Location);
		foundation.waitforElementToBeVisible(GlobalProductChange.BTN_LOCATION_APPLY, 3);
		foundation.click(GlobalProductChange.BTN_LOCATION_APPLY);
		foundation.threadWait(Constants.SHORT_TIME);
	}


	/**
	 * select location and click on products
	 */
	public void selectLocationAndProductClickOnNext(String location, String product) {
		CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProductChange.TXT_HEADER));
		foundation.waitforElementToBeVisible(GlobalProductChange.TXT_HEADER, 5);
		selectLocationAndClickOnApply(objLocation(location));
		table.selectRow(product);
		foundation.waitforElementToBeVisible(GlobalProductChange.BTN_NEXT, 3);
		foundation.click(GlobalProductChange.BTN_NEXT);
	}

	/**
	 * update deposite and tax field 
	 * @param deposit
	 * @param tax1
	 * @param tax2
	 * @throws AWTException 
	 */
	public void updateDepositeAndTaxField(String deposit,String tax1,String tax2) throws AWTException {
		foundation.waitforElementToBeVisible(GlobalProductChange.LBL_PRODUCT_FIELD_CHANGE, 5);
		textBox.enterText(GlobalProductChange.DEPOSIT_CAT, deposit);
		CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProductChange.DEPOSITE_CHECKED));
		textBox.enterText(GlobalProductChange.TXT_TAX1, tax1);
		CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProductChange.TAX1_CHECKED));
		textBox.enterText(GlobalProductChange.TXT_TAX2, tax2);
		CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProductChange.TAX2_CHECKED));
		foundation.click(GlobalProductChange.BTN_SUBMIT);
		foundation.waitforElement(GlobalProductChange.BUTTON_OK, Constants.THREE_SECOND);
		verifyButtonOkayInGPC();
		foundation.waitforElementToBeVisible(GlobalProductChange.TXT_HEADER, 3);
	}
	
	/**
	 * verify Button okay in GPC
	 * @throws AWTException
	 */
	public void verifyButtonOkayInGPC() throws AWTException {
		foundation.click(GlobalProductChange.BUTTON_OK);
		foundation.waitforElementToBeVisible(INPUT_TEXT, 3);
		textBox.enterText(INPUT_TEXT, "CONFIRM");
		foundation.threadWait(1);
		foundation.clickEnter();
		foundation.threadWait(Constants.THREE_SECOND);
		foundation.waitforElementToBeVisible(REASON_BTNOK, 3);
		foundation.click(REASON_BTNOK);

	}


	/**
	 * update price value
	 * 
	 * @param price
	 */
	public void updatePriceInAllLocation(String price) {
		foundation.waitforElementToBeVisible(GlobalProductChange.TXT_PRICE,3);
		foundation.click(GlobalProductChange.TXT_PRICE);
		textBox.enterText(GlobalProductChange.TXT_PRICE, price);
		foundation.waitforElementToBeVisible(GlobalProductChange.CHECK_ALL_LOC,3);
		CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProductChange.CHECK_ALL_LOC));
		foundation.click(GlobalProductChange.CHECK_ALL_LOC);
		
	}
	/**
	 * Select Operator product catalog change 
	 *
	 * @param product
	 * @param price
	 */
	public void selectProductOPC(String product) {
	if (!checkBox.isChecked(GlobalProductChange.OPC_CHECK_BOX))
		checkBox.check(GlobalProductChange.OPC_CHECK_BOX);
	foundation.threadWait(Constants.SHORT_TIME);
	CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProductChange.LBL_FILTERED_PRODUCTS));
	foundation.waitforElementToBeVisible(objTableDataOperatorProduct(product),3);
	foundation.click(objTableDataOperatorProduct(product));
	foundation.waitforElementToBeVisible(GlobalProductChange.BTN_NEXT,3);
	foundation.click(GlobalProductChange.BTN_NEXT);
	}
	/**
	 * Click confirmation buttons in OPC
	 */
	public void clickConfirmMsgInOPC() {
		foundation.waitforElementToBeVisible(GlobalProductChange.BTN_SUBMIT,3);
	CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProductChange.BTN_SUBMIT));
	foundation.click(GlobalProductChange.BTN_SUBMIT);
	foundation.waitforElementToBeVisible(GlobalProductChange.CONFIRM_CHANGE_OPC,3);
	CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProductChange.CONFIRM_CHANGE_OPC));
	foundation.click(GlobalProductChange.BTN_OK);
	foundation.waitforElementToBeVisible(GlobalProductChange.REASON_BTNOK,5);
	foundation.click(GlobalProductChange.REASON_BTNOK);
	CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProductChange.LBL_GPC));
	}
	
	/**
	 * Click confirmation buttons in GPC
	 * @param data
	 */
	public void clickConfirmMsgInGPC(String data) {
		foundation.waitforElementToBeVisible(GlobalProductChange.BTN_SUBMIT,5);
		CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProductChange.BTN_SUBMIT));
		foundation.click(GlobalProductChange.BTN_SUBMIT);
		foundation.waitforElementToBeVisible(GlobalProductChange.POPUP_OK,3);
		foundation.click(GlobalProductChange.POPUP_OK);
		foundation.waitforElementToBeVisible(GlobalProductChange.TXT_CONFIRM_POPUP,3);
		foundation.click(GlobalProductChange.TXT_CONFIRM_POPUP);
		textBox.enterText(GlobalProductChange.TXT_CONFIRM_POPUP,data);
		textBox.enterText(GlobalProductChange.TXT_CONFIRM_POPUP,Keys.ENTER);
		foundation.waitforElementToBeVisible(GlobalProductChange.REASONBOX_BTNOK,3);
		foundation.click(GlobalProductChange.REASONBOX_BTNOK);
		CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProductChange.LBL_GPC));
	}
	
	/**
	 * select product in global product change 
	 * 
	 * @param location
	 * @param product
	 */
	public void selectProductInGPC(String location,String product) {

		CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProductChange.LBL_GPC));
		foundation.click(objLocation(location));
		foundation.click(GlobalProductChange.BTN_LOCATION_APPLY);
		textBox.enterText(GlobalProductChange.TXT_PRODUCT_SEARCH, product);
		foundation.click(objTableDataProduct(product));
		foundation.waitforElementToBeVisible(GlobalProductChange.BTN_NEXT,3);
		foundation.click(GlobalProductChange.BTN_NEXT);
		}
	
	/**
	 * verify Tax2 value in ProductsTab
	 * 
	 * @param location
	 * @param product
	 */
	public void verifyTax2ValueInProductTab(String location,String product) {
			CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProductChange.LBL_GPC));
			foundation.click(objLocation(location));
			foundation.waitforElementToBeVisible(GlobalProductChange.TAB_PRODUCT, 3);
			foundation.click(GlobalProductChange.TAB_PRODUCT);
			foundation.waitforElementToBeVisible(GlobalProductChange.TXT_TAX2_PRODUCT, 3);
			foundation.click(GlobalProductChange.TXT_TAX2_PRODUCT);
			textBox.enterText(GlobalProductChange.TXT_TAX2_PRODUCT,product);
			foundation.click(GlobalProductChange.BTN_PRODUCT_APPLY);
			foundation.waitforElementToBeVisible(GlobalProductChange.COL_TAX2_PRODUCT,5);
			foundation.scrollIntoViewElement(GlobalProductChange.COL_TAX2_PRODUCT);
			String value=foundation.getText(GlobalProductChange.COL_TAX2_PRODUCT);
			CustomisedAssert.assertTrue(value.contains(product));	
}
	/**
	 * remove product in GPC
	 * 
	 * @param product
	 */
	public void removeProductInGPC(String product) {
		foundation.waitforElementToBeVisible(GlobalProductChange.DPD_REMOVE,3);
		CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProductChange.DPD_REMOVE));
		CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProductChange.CHK_GREENCIRCLE));
		foundation.click(GlobalProductChange.DPD_REMOVE);
		dropDown.selectItem(GlobalProductChange.DPD_REMOVE, product, Constants.TEXT);
		
	}
	/**
	 * update Tax2 value
	 * 
	 * @param product
	 * 
	 */
	public void updateTax2Value(String product) {
	foundation.waitforElementToBeVisible(GlobalProductChange.TXT_TAX2, 3);
	foundation.click(GlobalProductChange.TXT_TAX2);
	textBox.enterText(GlobalProductChange.TXT_TAX2, product);
	
	}
}
