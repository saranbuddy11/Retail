package at.smartshop.pages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import com.aventstack.extentreports.Status;

import at.framework.browser.Factory;
import at.framework.generic.CustomisedAssert;
import at.framework.reportsetup.ExtFactory;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.CheckBox;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.keys.Constants;
import at.smartshop.tests.TestInfra;

public class PickList extends Factory {
	private Foundation foundation = new Foundation();
	private Dropdown dropDown = new Dropdown();
	private CheckBox checkBox = new CheckBox();
	private TextBox textBox = new TextBox();

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
	public static final By LBL_PREVIEW = By.xpath("//a[text()='Preview']");
	public static final By LBL_Add = By.xpath("//a[text()='Add']");
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
	public static final By PRODUCT_DATAS=By.xpath("//th[contains(@id,'grid_name')]/../../following-sibling::tbody//td[contains(@aria-describedby,'grid_name')]");
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
	public static final By TXT_SCHEDULING_LOCATION = By.xpath("//td[@aria-describedby='dataGrid_location']");
	public static final By TXT_SERVICE_SCHEDULE = By.xpath("//div[@id='filter-route-title']");
	public static final By TXT_SEND_PICKLIST = By.xpath("//div[@class='ajs-header']");
	public static final By TXT_CONFIRM_SENDING = By.xpath("//div[@class='ajs-content']/p[contains(text(),'You are about to')]");
	public static final By TXT_CONTINUE = By.xpath("//div[@class='ajs-content']//following-sibling::p");
	public static final By BTN_CANCEL = By.xpath("//button[@class='ajs-button ajs-cancel']");
	public static final By LOCATION_LIST = By.xpath("//td[@aria-describedby='dataGrid_table_namelink']");
	public static final By TXT_LOCATION_LIST = By.xpath("//li[@class='active']");
	public static final By TXT_SELECT_ALL = By.xpath("//a[@id='loc-select-all']");
	public static final By TXT_DESELECT_ALL = By.id("loc-deselect-all");
	public static final By BTN_CLEAR = By.xpath("//button[@id='loc-search-cancel']");
	public static final By LIST_LOCATION = By.xpath("//ul[@id='location-list']");
	public static final By VALIDATE_DESELECTED_LOCATION = By.xpath("//li[@class='list-group-item unchecked']");
	public static final By DRP_PLANNING = By.id("planningDay");
	public static final By DRP_SERVICING = By.id("servicingDay");
	public static final By DRP_ROUTE = By.id("route");
	public static final By DRP_DRIVER = By.id("driver");  
	public static final By LAST_PICKLIST_DATE = By.id("lastpicklistdaterange");
	public static final By LAST_INVENTORIED_DATE = By.id("inventorieddaterange");
	public static final By BTN_APPLY_FILTERBY = By.id("filter-by-apply");
	public static final By TBL_SELECT_LOC_HEADERS = By.xpath("//table[@id='dataGridPickListManager']//thead");
	public static final By DRP_FILTERBY = By.id("//div[@class='filter-by-group']//select");
	public static final By TAB_DATE_RANGE = By.xpath("//div[@id='daterange']"); 
	public static final By TAB_START_DATE = By.xpath("//input[@name='daterangepicker_start']");
	public static final By TAB_END_DATE = By.xpath("//input[@name='daterangepicker_end']");
	public static final By BTN_SEARCH_HISTORY = By.id("searchbtn");
	public static final By TXT_FILTERED_LOCATION = By.xpath("//td[@aria-describedby='dataGridPickListManager_location']");
	public static final By TXT_DATE_RANGE_VALUES = By.xpath("//div[@class='ranges']//ul//li");
	public static final By TXT_HISTORY_RECORD = By.id("picklisthistory_pager_label");
	public static final By LNK_REMOVE_ORG = By.xpath("//span[@class='select2-selection select2-selection--multiple']//ul/span");
	public static final By DRP_SELECT_LOC = By.cssSelector("select#loc-dropdown");
	public static final By TXT_DEFAULT_LOC = By.xpath("//ul[@class='select2-selection__rendered']//li[@class='select2-selection__choice']");
	public static final By BTN_PUSH_TO_INVENTORY = By.xpath("//button[text()='Push To Inventory']");
	public static final By DRP_HAS_LIGHTSPEED = By.id("haslightspeed");
	public static final By DPD_FILTERBY = By.id("filter-by");
	public static final By CHKBOX_EXACT_MATCH = By.id("exact-match");
	public static final By SELECT_ALL = By.id("gridloc-select-all");
	public static final By INPUT_TEXT = By.id("single-num");
	public static final By BTN_FILTER_APPLY = By.id("prd-filter-apply");
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

	public By objRouteText(String keyword) {
		return By.xpath("//li[text()='" + keyword + "']");
	}

	public By objDriverText(String driver) {
		return By.xpath("//div[contains(@style,'top: 353.635px;')]//li[contains(@class,'ui-state-default') and text()='"+ driver + "']");
	}

	public By objDayCheckbox(String day) {
		return By.xpath("(//span[contains(@class,'ui-igcheckbox-small')])[" + day + "]");
	}
	
	public By objDay(String day) {
		return By.xpath("(//span[contains(@class,'ui-igcheckbox-container')])[" + day + "]");
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
	 * Select location from location filter and apply
	 * 
	 * @param location
	 */
	public void selectLocationInFilterAndApply(String location) {
		CustomisedAssert.assertTrue(foundation.isDisplayed(PickList.LOCATION_FILTER));
		foundation.click(selectLocationFromList(location));
		foundation.scrollIntoViewElement(PickList.BTN_APPLY);
		foundation.click(PickList.BTN_APPLY);
		foundation.waitforElement(objPickList(location), Constants.SHORT_TIME);
		foundation.click(objPickList(location));
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
	* verify pick list actions in dropdown values
	* @param values
	*/
	public void verifyPickListActionsInDropdown(List<String> values,By object) {
	for (int i = 0; i < values.size(); i++) {
	List<String> actual = dropDown.getAllItems(object);
	//CustomisedAssert.assertEquals(actual, values);
	}
	}
	/**
	 * 
	 * @param Select Location Table Headers
	 * @return
	 */
	public List<String> tableHeaders = new ArrayList<>();

	public void getTableHeaders() {
		List<WebElement> columnHeaders = Foundation.getDriver().findElements(TBL_SELECT_LOC_HEADERS);
		for (WebElement columnHeader : columnHeaders) {
			tableHeaders.add(columnHeader.getText());
         }
	}

		public void verifyLocationHeaders(List<String> columnNames) {
			try {
				for (int iter = 0; iter < tableHeaders.size(); iter++) {
					CustomisedAssert.assertTrue(tableHeaders.get(iter).equals(columnNames.get(iter)));
				}
			} catch (Exception exc) {
				TestInfra.failWithScreenShot(exc.toString());
			}
		}
	
		/**
		 * 
		 * @param Verify DropDown Values on Filter By Tab
		 * @return
		 */	
		public List<String> verifyDropDownValues(List<String> values,List<String> dbData) {
			String selectedPlanning = dropDown.getSelectedItem(PickList.DRP_PLANNING);
			CustomisedAssert.assertEquals(selectedPlanning,values.get(0));
			String selectedServicing = dropDown.getSelectedItem(PickList.DRP_SERVICING);
			CustomisedAssert.assertEquals(selectedServicing,dbData.get(0));
			String selectedDriver = dropDown.getSelectedItem(PickList.DRP_DRIVER);
			CustomisedAssert.assertEquals(selectedDriver,dbData.get(1));
			String selectedRouter = dropDown.getSelectedItem(PickList.DRP_ROUTE);
			CustomisedAssert.assertEquals(selectedRouter,dbData.get(2));
			return dbData;
		}
		
		public void verifyDateRangeText(List<String> dbValues) {
			List<String> dateRangeValue = foundation.getTextofListElement(PickList.TXT_DATE_RANGE_VALUES);
			for (int i = 0; i < dateRangeValue.size(); i++)
			{	
				CustomisedAssert.assertTrue(dateRangeValue.get(i).equals(dbValues.get(i)));
			   // System.out.println(dateRangeValue.get(i).getText());
			    
			}
		}
	/**
	 * Enter driver , route and select service day 
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
				String value= getDriver().findElement(objDay(String.valueOf(i+1))).getAttribute("aria-checked");
				CustomisedAssert.assertEquals(value, checkboxSelection);
			} else {
					foundation.click(objDayCheckbox(String.valueOf(i + 1)));
				
			}
		}
	} 
  	public By selectDateRange(String text) {
		By element = null;
		try {
			element = By.xpath("//div[@class='ranges']//li[text()='" + text + "']");
			foundation.click(element);
			
		} catch (Exception exc) {

			TestInfra.failWithScreenShot(exc.toString());
		}
		return element;
}
}
