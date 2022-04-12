package at.smartshop.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import at.framework.browser.Factory;
import at.framework.generic.CustomisedAssert;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.keys.Constants;
import at.smartshop.tests.TestInfra;

public class GlobalProductChange extends Factory {
	private TextBox textBox = new TextBox();
	private Dropdown dropDown = new Dropdown();
	private Foundation foundation = new Foundation();
	public static final By TXT_LOCATION_SEARCH = By.id("loc-search");
	public static final By TBL_LOCATION_LIST = By.id("location-list");
	public static final By BTN_LOCATION_APPLY = By.id("loc-filter-apply");
	public static final By BTN_PRODUCT_APPLY = By.id("prd-filter-apply");
	public static final By TAB_PRODUCT = By.cssSelector("#li2 > a");
	public static final By BTN_NEXT = By.id("prd-dt-next");
	public static final By DPD_PICKLISTACTION = By.id("prd-pick-list-action");
	public static final By DPD_DISPLAY_NEEDBY = By.id("prd-display-need-by");
	public static final By TXT_MIN = By.id("prd-min");
	public static final By TXT_MAX = By.id("prd-max");
	public static final By BTN_INCREMENT = By.xpath("//*[@id='increment-radio-btn']/..");
	public static final By TXT_PRICE = By.id("prd-price");
	public static final By TXT_PRODUCT_NAME = By.id("filter-name");
	public static final By LBL_PRICE = By.xpath("//*[@id='prd-change-form']/dt[text()='Price']");
	public static final By LBL_MIN = By.xpath("//*[@id='prd-change-form']/dt[text()='Min']");
	public static final By LBL_MAX = By.xpath("//*[@id='prd-change-form']/dt[text()='Max']");
	public static final By DPD_LOYALITY_MULTIPLIER = By.id("prd-loyalty-multiplier");
	public static final By BTN_SUBMIT = By.id("prd-update-submit");
	public static final By BTN_OK = By.cssSelector("button.ajs-button.ajs-ok");
	public static final By MSG_SUCCESS = By.xpath("//div[text()='Updated 1 product(s)!']");
	public static final By RDO_OPERATOR_PRODUCT_CHANGE = By.xpath("//label[text()='Operator Product Catalog Change']");
	public static final By TXT_LOCATION_PRODUCT = By.id("filter-name");
	public static final By REASON_BTNOK = By.id("gpcokbtn");
	public static final By REASONBOX_BTNOK = By.xpath("//button[@id='gpcokbtn']");
	public static final By TXT_HEADER = By.id("Global Product Change");
	public static final By GPC_lOCATION = By.id("global-prd");
	public static final By SELECT_ALL = By.id("loc-select-all");
	public static final By SELECT_COUNT = By.id("select-count");
	public static final By OPS_LOCATION = By.id("operator-prd");
	public static final By DESELECT_ALL = By.id("loc-deselect-all");
	public static final By FILTER_PRODUCT = By.id("filter-prd-title");
	public static final By OPS_SELECT_ALL = By.id("prd-select-all");
	public static final By OPS_DESELECT_ALL = By.id("prd-deselect-all");
	public static final By ROUNDING = By.id("prd-rounding");
	public static final By PRICE_CHECKBOX = By.id("prd-price-checked");
	public static final By MIN_CHECKEDBOX = By.id("prd-min-checked");
	public static final By MAX_CHECKEDBOX = By.id("prd-max-checked");
	public static final By ACTION_CHECKEDBOX = By.id("prd-pick-list-action-checked");
	public static final By LOYALITY_CHECKEDBOX = By.id("prd-loyalty-multiplier-checked");
	public static final By DISPLAY_CHECKBOX = By.id("prd-display-need-by");
	public static final By ROUNDING_CHECKBOX = By.id("prd-display-need-by");
	public static final By TXT_PROMPT_MSG = By.xpath("//div[text()='Confirm Global Product Change for Location(s)']");
	public static final By PROMT_OPERATOR_PRODUCT = By.xpath("//div[text()='Confirm Operator Product Catalog Change']");
	public static final By TXT_PROMPT_CONTENT = By.xpath("//div[@class='ajs-content']");
	public static final By BTN_CANCEL = By.xpath("//button[@class='ajs-button ajs-cancel']");
	public static final By PRODUCT_FIELD = By.id("prd-name");
	public static final By PRODUCT_CHECKOUT = By.id("prd-name-checked");
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
	public static final By CASE_COUNT = By.id("prd-case");
	public static final By CASE_COUNT_CHECKBOX = By.id("prd-case-checked");
	public static final By TABLE_GRID_HISTORY=By.xpath("//tr[@data-id='c22dcbecf28de907e38587bc84fe5e14']");
	public static final By HISTORY_BTN = By
			.xpath("//div[@style='display:inline-block;float: right;']//button[text()='History']");
	public static final By HISTORY_GPC = By.xpath("//h4[text()='Global Product Change History']");
	public static final By HEADER_DATA = By.xpath("//tr[@role='row']");
	public static final By GRID_HEADER=By.id("gpchistory");

	public By objTableRow(String location) {
		return By.xpath("//table[@id='filtered-prd-dt']//tbody//span[text()='" + location + "']");
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
}
