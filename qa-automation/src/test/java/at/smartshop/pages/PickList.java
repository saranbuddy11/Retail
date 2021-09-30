package at.smartshop.pages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import at.framework.browser.Factory;
import at.smartshop.tests.TestInfra;

public class PickList extends Factory {

	public static final By SEARCH_FILTER = By.xpath("//input[@placeholder='Search to filter...']");
	public static final By LBL_LOCATION = By.xpath("//ul[@id='location-list']//li");
	public static final By LBL_SELECT_ALL = By.xpath("//a[text()='Select All']");
	public static final By BTN_APPLY = By.xpath("//button[@id='loc-filter-apply']");
	public static final By BTN_PICKLIST_PLAN = By.xpath("//button[text()='Plan Pick List(s)']");
	public static final By LBL_REMOVE = By.xpath("//a[text()='Remove ']");
	public static final By LBL_ADD_PRODUCT = By.xpath("//input[@value='Add Product']");
	public static final By LBL_ADD_PRODUCT_PICKLIST = By.xpath("//h4[text()='Add Product(s) to Pick List']");
	public static final By LBL_TITLE_HEADER = By.xpath("//h4[@class='modal-title']");
	public static final By LBL_FILTER_TYPE = By.xpath("//input[@id='filterType']");
	public static final By LBL_PREVIEW = By.xpath("//a[text()='Preview']");
	public static final By LBL_Add = By.xpath("//a[text()='Add']");
	public static final By TBL_NEED = By.xpath("//*[@id='new-prd-grid']/tbody/tr/td[@class='editable-style left-align']");
	public static final By TXT_NEED = By.xpath("//span//input[@class='ui-igedit-input' and @role='textbox']");//span//input[@type='tel' and @class='ui-igedit-input']");
	public static final By TXT_NEED1 = By.xpath("//span//input[@type='text' and @class='ui-igedit-input']");
	public static final By TBL_PRODUCT_GRID = By.id("filter-prd-grid");
	public static final By PAGE_TITLE = By.id("//li[@id='Pick List Manager']");
	public static final By TXT_SPINNER_MSG = By.xpath("//div[@class='humane humane-libnotify-success']");
	public static final By TBL_ROW = By.xpath("//*[@id='filter-prd-grid']/tbody/tr");

	public By objPickList(String text) {
		By element = null;
		try {
			element = By.xpath("//td[text()='" + text + "']");
		} catch (Exception exc) {

			Assert.fail(exc.toString());
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
			Assert.fail(exc.toString());
		}
		return uiTblRowValues;
	}
}
