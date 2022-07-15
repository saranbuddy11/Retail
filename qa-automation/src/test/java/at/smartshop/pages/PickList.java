package at.smartshop.pages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.aventstack.extentreports.Status;

import at.framework.browser.Factory;
import at.framework.files.Excel;
import at.framework.generic.CustomisedAssert;
import at.framework.reportsetup.ExtFactory;
import at.framework.ui.CheckBox;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.Table;
import at.framework.ui.TextBox;
import at.smartshop.database.columns.CNNavigationMenu;
import at.smartshop.database.columns.CNPickList;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.tests.TestInfra;

public class PickList extends Factory {
	private Foundation foundation = new Foundation();
	private Dropdown dropDown = new Dropdown();
	private CheckBox checkBox = new CheckBox();
	private TextBox textBox = new TextBox();
	private Excel excel = new Excel();
	private Table table=new Table();

	public static final By SEARCH_FILTER = By.xpath("//input[@placeholder='Search to filter...']");
	public static final By LBL_LOCATION = By.xpath("//ul[@id='location-list']//li");
	public static final By LBL_SELECT_ALL = By.id("prd-select-all");
	public static final By BTN_APPLY = By.xpath("//button[@id='loc-filter-apply']");
	public static final By BTN_PICKLIST_PLAN = By.xpath("//button[contains(@onclick,'toPlanPickList')]");
	public static final By LBL_REMOVE = By.xpath("//a[text()='Remove ']");
	public static final By LBL_ADD_PRODUCT = By.xpath("//input[@value='Add Product']");
	public static final By LBL_ADD_PRODUCT_PICKLIST = By.xpath("//h4[text()='Add Product(s) to Pick List']");
	public static final By LBL_TITLE_HEADER = By.xpath("//h4[@class='modal-title']");
	public static final By LBL_FILTER_TYPE = By.xpath("//input[@id='filterType']");
	public static final By TXT_PRODUCT_NAME = By.id("filter-name");
	public static final By TXT_INPUT = By.xpath("//input[@type='text']");
	public static final By EXPORT_BTN = By.id("excel-dwnld");
	public static final By LBL_ROUTES = By.id("Routes");
	public static final By LBL_PREVIEW = By.xpath("//a[text()='Preview']");
	public static final By BUTTON_SAVE = By.id("saveBtn");
	public static final By LBL_Add = By.xpath("//a[text()='Add']");
	public static final By DELETE_BTN = By.xpath("//a[@class='delete-button']");
	public static final By TXT_NEED_PICKLIST = By.id("1657277901591EditingInput");
	public static final By NEED_BTN = By.xpath("//*[@id='new-prd-grid']/tbody/tr/td[7]");
	public static final By TBL_NEED = By
			.xpath("//*[@id='new-prd-grid']/tbody/tr/td[@class='editable-style left-align']");
	public static final By TXT_NEED = By.xpath("//span//input[@class='ui-igedit-input' and @role='textbox']");// span//input[@type='tel'
																												// and
																												// @class='ui-igedit-input']");
	public static final By TXT_NEED1 = By.xpath("//span//input[@type='text' and @class='ui-igedit-input']");
	public static final By TBL_PRODUCT_GRID = By.id("filter-prd-grid");
	public static final By PAGE_TITLE = By.xpath("//li[@id='Pick List Manager']");
	public static final By TXT_SPINNER_MSG = By.xpath("//div[@class='humane humane-libnotify-success']");
	public static final By TBL_ROW = By.xpath("//*[@id='filter-prd-grid']/tbody/tr");
	public static final By BTN_RESET_NAV_TO_ZERO = By.id("showOnLocSelect3");
	public static final By LOCATION_FILTER = By.id("li1");
	public static final By POPUP_HEADER = By.xpath("//div[@class='ajs-header']");
	public static final By POPUP_CONTENT = By.xpath("//div[@class='ajs-content']");
	public static final By BTN_OKAY = By.xpath("//button[@class='ajs-button ajs-ok']");
	public static final By FILTER_GRID = By.id("filter-prd-grid");
	public static final By TBL_ROW_DATA = By.xpath("//tbody[@role='rowgroup']/tr");
	public static final By PRODUCT_DATAS = By.xpath(
			"//th[contains(@id,'grid_name')]/../../following-sibling::tbody//td[contains(@aria-describedby,'grid_name')]");
	public static final By FILTER_LOCATION = By.id("filter-loc-title");
	public static final By BTN_SELECT_ALL = By.id("loc-select-all");
	public static final By SELECTED_LOCATION = By.xpath("//tbody[@role='rowgroup']");
	public static final By DRP_LOCATION = By.id("location-select");
	public static final By BTN_CLOSE = By.id("modalcancel");
	public static final By BTN_CREATE_NEW_ROUTE = By.id("newBtn");
	public static final By DRP_ROUTE_DRIVER = By.id("driver");
	public static final By TXT_FILTERBY = By.xpath("//a[text()='Filter By']");
	public static final By BTN_SEND_TO_LIGHTSPEED = By.xpath("//button[text()='Send To LightSpeed']");
	public static final By BTN_YES = By.xpath("//button[@class='ajs-button ajs-ok']");
	public static final By BTN_CANCEL_ORDER = By.id("cancelOrderButton");
	public static final By SELECT_ORDER_TAB = By.xpath("//td[@aria-describedby='cancelorder-table_location']");
	public static final By BTN_CONFIRM_CANCEL_ORDER = By.id("cancelorder-yes");
	public static final By LBL_HISTORY_TITLE = By.id("Pick List History");
	public static final By TBL_ROW_HEADERS = By.xpath("//table[@id='picklisthistory']//thead");
	public static final By BTN_HISTORY = By.id("btn-history");
	public static final By VALIDATE_HIGHLIGHTED_LOCATIONS = By.xpath("//ul[@id='location-list']//li");
	public static final By VALIDATE_HIGHLIGHTED_PRODUCTS = By
			.xpath("//table[@id='dataGridPickListManager']//tbody//tr");
	public static final By BTN_REFRESH = By.id("refreshButton");
	public static final By BTN_CONFIRM_REFRESH = By.xpath("//button[@class='ajs-button ajs-ok']");
	public static final By BTN_EXPORT = By.xpath("//button[contains(@onclick,'exportToExcel')]");
	public static final By DPD_FILTERBY = By.id("filter-by");
	public static final By CHKBOX_EXACT_MATCH = By.id("exact-match");
	public static final By SELECT_ALL = By.id("gridloc-select-all");
	public static final By INPUT_TEXT = By.id("single-num");
	public static final By BTN_FILTER_APPLY = By.id("prd-filter-apply");
	public static final By PRODUCT_CANCEL = By.id("prd-filter-clear-icon");
	public static final By BTN_FILTER_CANCEL = By.id("prd-filter-cancel");
	public static final By DPD_PICKLIST_ACTIONS = By.id("pick-list-action");
	public static final By FILTER_PICKLIST = By.id("filter-prd-title");
	public static final By DPD_CATEGORY = By.id("cate-select");
	public static final By PRODUCT_NAME_GRID = By.id("filter-prd-grid_name");
	public static final By BTN_SCHEDULING = By.xpath("//button[@onclick='toScheduling()']");
	public static final By TXT_ROUTE_SCHEDULING = By.id("Route Scheduling");
	public static final By SUCCESS_MSG = By.xpath("//li[text()='Schedule saved']");
	public static final By ROUTE_COLUMN = By.id("dataGrid_route");
	public static final By DATA_GRID_ROUTE = By.xpath("//td[@aria-describedby='dataGrid_route']");
	public static final By DRIVER_COLUMN = By.id("dataGrid_driver");
	public static final By DATA_GRID_DRIVER = By.xpath("//td[@aria-describedby='dataGrid_driver']");
	public static final By DPD_ROUTE = By.xpath("//input[contains(@class,'ui-igcombo-field')]");
	public static final By DPD_DRIVER = By.xpath("//input[contains(@class,'ui-igcombo-field')]");
	public static final By BTN_SAVE = By.id("schedule-save");
	public static final By CHECKBOX = By.xpath("//span[contains(@class,'ui-igcheckbox-small')]");
	public static final By START_DATE_DROPDOWN = By.id("start-num");
	public static final By END_DATE_DROPDOWN = By.id("end-num");
	public static final By GRID_ROUTE = By.xpath("//tbody[@role='alert']");
	public static final By HEADER_ROUTE = By.xpath("//tr[@role='row']");
	public static final By CHECKBOX_ACTIVE = By.id("active");
	public static final By TBL_PRODUCTS=By.id("filter-prd-grid");
	public static final By TBL_PRODUCTS_HEADER=By.cssSelector("#filter-prd-grid > thead");

	public By objRouteText(String keyword) {
		return By.xpath("//li[text()='" + keyword + "']");
	}

	public By objRouteColumn(String column) {
		return By.xpath("//table[@id='dt']//tr/th[text()='" + column + "']");
	}

	public By objDriverText(String driver) {
		return By.xpath("//div[contains(@style,'top: 353.635px;')]//li[contains(@class,'ui-state-default') and text()='"
				+ driver + "']");
	}

	public By objDayCheckbox(String day) {
		return By.xpath("(//span[contains(@class,'ui-igcheckbox-small')])[" + day + "]");
	}

	public By selectRoutes(String description, String driver) {
		return By.xpath("//td[text()='" + description + "']/following-sibling::td[text()='" + driver + "']");
	}

	public By objDay(String day) {
		return By.xpath("(//span[contains(@class,'ui-igcheckbox-container')])[" + day + "]");
	}

	public By selectProduct(String product) {
		return By.xpath("//td[@aria-describedby='new-prd-grid_name'][text()='" + product + "']");
	}

	public By objPickList(String text) {
		By element = null;
		try {
			element = By.xpath("//td[text()='" + text + "']");
		} catch (Exception exc) {

			TestInfra.failWithScreenShot(exc.toString());
		}
		return element;
	}

	public Map<String, String> getTblSingleRowRecordUI(By tableGrid, By tableRow) {
		Map<String, String> uiTblRowValues = new HashMap<>();
		try {
			int index = 2;

			WebElement tableReports = getDriver().findElement(tableGrid);
			List<WebElement> columnHeaders = tableReports.findElements(By.tagName("th")).subList(1, 13);
			WebElement row = getDriver().findElement(tableRow);

			for (WebElement columnHeader : columnHeaders) {
				WebElement column = row.findElement(By.cssSelector("td:nth-child(" + index + ")"));
				uiTblRowValues.put(columnHeader.getText(), column.getText());
				index++;
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		return uiTblRowValues;
	}

	public By selectLocationFromList(String location) {
		return By.xpath("//ul[@id='location-list']//li[text()='" + location + "']");
	}

	/**
	 * select Location And Click On Navigate To Zero
	 * 
	 * @param location
	 * @param contain
	 */
	public void selectLocationAndClickOnNavigateToZero(String location, String contain) {
		CustomisedAssert.assertTrue(foundation.isDisplayed(PickList.LOCATION_FILTER));
		foundation.click(selectLocationFromList(location));
		foundation.scrollIntoViewElement(PickList.BTN_APPLY);
		foundation.click(PickList.BTN_APPLY);
		foundation.waitforElement(objPickList(location), Constants.SHORT_TIME);
		foundation.click(objPickList(location));
		foundation.click(PickList.BTN_RESET_NAV_TO_ZERO);
		foundation.waitforElementToBeVisible(PickList.POPUP_HEADER, 5);
		String content = foundation.getText(PickList.POPUP_CONTENT);
		CustomisedAssert.assertTrue(content.contains(contain));
		foundation.threadWait(Constants.SHORT_TIME);
		foundation.click(PickList.BTN_OKAY);
		foundation.isDisplayed(PickList.PAGE_TITLE);
	}

	public By verifyPickListColumn(String product) {
		return By.xpath("//td[text()='" + product + "']//..//td[@aria-describedby='productDataGrid_planning']");
	}

	public String verifyProductsHighlighted(String expected) {
		String attr = getDriver().findElement(VALIDATE_HIGHLIGHTED_PRODUCTS).getAttribute("aria-selected");
		CustomisedAssert.assertEquals(expected, attr);
		return attr;
	}

	/**
	 * Select location from location filter,apply and click on plan picklist
	 * 
	 * @param location
	 */
	public void selectLocationInFilterApplyAndClickOnPlanPick(String location) {
		CustomisedAssert.assertTrue(foundation.isDisplayed(PickList.LOCATION_FILTER));
		foundation.click(selectLocationFromList(location));
		foundation.scrollIntoViewElement(PickList.BTN_APPLY);
		foundation.click(PickList.BTN_APPLY);
		foundation.waitforElement(objPickList(location), Constants.SHORT_TIME);
		foundation.click(objPickList(location));
		foundation.waitforElementToBeVisible(PickList.FILTER_LOCATION, 5);
		foundation.click(objPickList(location));
		foundation.click(PickList.BTN_PICKLIST_PLAN);
		foundation.waitforElementToBeVisible(PickList.FILTER_GRID, 5);
		String data = foundation.getText(PickList.TBL_ROW_DATA);
		CustomisedAssert.assertTrue(data.contains(location));
	}

	/**
	 * select dropdown and Apply
	 * 
	 * @param dropdown
	 * @param text
	 */
	public void selectDropdownValueAndApply(String dropdown, String text) {
		CustomisedAssert.assertTrue(foundation.isDisplayed(PickList.DPD_FILTERBY));
		dropDown.selectItem(PickList.DPD_FILTERBY, dropdown, Constants.TEXT);
		checkBox.check(PickList.CHKBOX_EXACT_MATCH);
		textBox.enterText(PickList.INPUT_TEXT, text);
		foundation.waitforElementToBeVisible(PickList.BTN_FILTER_APPLY, 5);
		foundation.click(PickList.BTN_FILTER_APPLY);
		foundation.threadWait(Constants.SHORT_TIME);
	}

	/**
	 * verify dropdown values
	 * 
	 * @param values
	 */
	public void verifyPickListActionsInDropdown(List<String> values, By object) {
		for (int i = 0; i < values.size(); i++) {
			List<String> actual = dropDown.getAllItems(object);
			CustomisedAssert.assertEquals(actual, values);
		}
	}

	/**
	 * Enter driver , route and select service day
	 * 
	 * @param option1
	 * @param option2
	 * @param checkboxSelection
	 */
	public void checkboxsServiceDay(String option1, String option2, String checkboxSelection) {
		List<WebElement> list = getDriver().findElements(CHECKBOX);
		foundation.waitforElementToBeVisible(ROUTE_COLUMN, 5);
		foundation.click(DATA_GRID_ROUTE);
		foundation.click(DPD_ROUTE);
		foundation.click(objRouteText(option1));
		foundation.waitforElementToBeVisible(DRIVER_COLUMN, 5);
		foundation.click(DATA_GRID_DRIVER);
		foundation.click(DPD_DRIVER);
		foundation.click(objDriverText(option2));
		for (int i = 0; i <= list.size() - 1; i++) {
			if (checkboxSelection.equals("true")) {
				if (!checkBox.isChecked(objDayCheckbox(String.valueOf(i + 1)))) {
					foundation.click(objDayCheckbox(String.valueOf(i + 1)));
					foundation.threadWait(Constants.SHORT_TIME);
				}
				String value = getDriver().findElement(objDay(String.valueOf(i + 1))).getAttribute("aria-checked");
				CustomisedAssert.assertEquals(value, checkboxSelection);
			} else {
				foundation.click(objDayCheckbox(String.valueOf(i + 1)));

			}
		}
	}

	/**
	 * select dropDown and verify the exact match checkBox and cancel th filter
	 * 
	 * @param dropdown
	 */
	public void selectDropdownInFilterBy(String dropdown) {
		dropDown.selectItem(PickList.DPD_FILTERBY, dropdown, Constants.TEXT);
		CustomisedAssert.assertTrue(foundation.isDisplayed(PickList.CHKBOX_EXACT_MATCH));
		foundation.click(PickList.CHKBOX_EXACT_MATCH);
		CustomisedAssert.assertTrue(foundation.isDisplayed(PickList.INPUT_TEXT));
		foundation.click(PickList.BTN_FILTER_CANCEL);
		foundation.waitforElementToBeVisible(PickList.POPUP_HEADER, 5);
		foundation.click(PickList.BTN_YES);
	}

	/**
	 * Select dropDown and verify start,end date and cancel the filter
	 * 
	 * @param dropdown
	 */
	public void selectDropdownInFilterByAndVerify(String dropdown) {
		dropDown.selectItem(PickList.DPD_FILTERBY, dropdown, Constants.TEXT);
		CustomisedAssert.assertTrue(foundation.isDisplayed(PickList.START_DATE_DROPDOWN));
		CustomisedAssert.assertTrue(foundation.isDisplayed(PickList.END_DATE_DROPDOWN));
		foundation.click(PickList.BTN_FILTER_CANCEL);
		foundation.waitforElementToBeVisible(PickList.POPUP_HEADER, 5);
		foundation.click(PickList.BTN_YES);
	}

	/**
	 * search Route And Click On Active CheckBox
	 * 
	 * @param text
	 * @param driver
	 * @param description
	 */
	public void searchRouteAndClickOnActiveCheckbox(String text, String description, String driver) {
		CustomisedAssert.assertTrue(foundation.isDisplayed(PickList.LBL_ROUTES));
		textBox.enterText(PickList.TXT_INPUT, text);
		foundation.waitforElementToBeVisible(GRID_ROUTE, 5);
		foundation.click(selectRoutes(description, driver));
		foundation.waitforElementToBeVisible(LBL_ROUTES, 3);
		foundation.click(CHECKBOX_ACTIVE);
		foundation.waitforElementToBeVisible(BUTTON_SAVE, 2);
		foundation.click(BUTTON_SAVE);
		foundation.waitforElementToBeVisible(GRID_ROUTE, 5);
	}

	/**
	 * verify route headers UI Field
	 * 
	 * @param values
	 */
	public void verifyRouteHeaders(List<String> values) {
		try {
			foundation.waitforElement(HEADER_ROUTE, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(HEADER_ROUTE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(objRouteColumn(values.get(0))));
			CustomisedAssert.assertTrue(foundation.isDisplayed(objRouteColumn(values.get(1))));
			CustomisedAssert.assertTrue(foundation.isDisplayed(objRouteColumn(values.get(2))));
			CustomisedAssert.assertTrue(foundation.isDisplayed(objRouteColumn(values.get(3))));
			CustomisedAssert.assertTrue(foundation.isDisplayed(objRouteColumn(values.get(4))));
			CustomisedAssert.assertTrue(foundation.isDisplayed(objRouteColumn(values.get(5))));
			ExtFactory.getInstance().getExtent().log(Status.INFO, "Validated the Route header default Value" + values);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * search Product and add products
	 * 
	 * @param product
	 * @param needvalue
	 */
	public void searchProductAndAddProduct(String product, String needvalue) {
		foundation.click(PickList.LBL_ADD_PRODUCT);
		foundation.waitforElementToBeVisible(PickList.LBL_TITLE_HEADER, 5);
		CustomisedAssert.assertTrue(foundation.isDisplayed(PickList.LBL_TITLE_HEADER));
		textBox.enterText(PickList.LBL_FILTER_TYPE, product);
		foundation.waitforElementToBeVisible(PickList.TXT_NEED_PICKLIST, 5);
		foundation.click(PickList.TBL_NEED);
		foundation.objectFocus(PickList.TXT_NEED);
		foundation.click(PickList.TXT_NEED);
		foundation.waitforElement(PickList.TXT_NEED, Constants.LONG_TIME);
		textBox.enterText(TXT_NEED, needvalue);
		foundation.click(objPickList(product));
		foundation.click(PickList.LBL_PREVIEW);
		foundation.click(PickList.LBL_Add);
		foundation.waitforElement(PickList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
	}

	/**
	 * search Product and export the file
	 * 
	 * @param productname
	 * @param validate
	 * @param location
	 * @param record
	 * @param dateformat
	 */
	public void searchProductAndExport(String productname, String validate, String location, String record,
			String dateformat) {
		textBox.enterText(PickList.TXT_PRODUCT_NAME, productname);
		foundation.waitforElementToBeVisible(PickList.BTN_FILTER_APPLY, 5);
		foundation.click(PickList.BTN_FILTER_APPLY);
		String data = foundation.getText(PickList.TBL_ROW_DATA);
		CustomisedAssert.assertFalse(data.contains(validate));
		foundation.waitforElementToBeVisible(TBL_ROW_DATA, 5);
		foundation.click(selectRoutes(location, productname));
		foundation.waitforElementToBeVisible(PickList.EXPORT_BTN, 5);
		foundation.click(PickList.EXPORT_BTN);
		foundation.threadWait(Constants.SHORT_TIME);
		CustomisedAssert.assertTrue(excel.isFileDownloaded(FilePath.pickListFilePath(record, dateformat)));	
	}
}
