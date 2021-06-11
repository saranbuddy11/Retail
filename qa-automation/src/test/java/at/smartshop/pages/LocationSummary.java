package at.smartshop.pages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.aventstack.extentreports.Status;

import at.framework.browser.Factory;
import at.framework.reportsetup.ExtFactory;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.keys.Constants;

public class LocationSummary extends Factory {
    private Dropdown dropDown=new Dropdown();
	private TextBox textBox = new TextBox();
	private Foundation foundation = new Foundation();

	public static final By DPD_DISABLED = By.id("isdisabled");
	public static final By BTN_SAVE = By.id("saveBtn");
	public static final By BTN_MANAGE_COLUMNS = By.id("manageProductGridColumnButton");
	public static final By POP_UP_BTN_APPLY = By.id("productDataGrid_hiding_modalDialog_footer_buttonok_lbl");
	public static final By DLG_COLUMN_CHOOSER = By.id("productDataGrid_hiding_modalDialog_content");
	public static final By DLG_COLUMN_CHOOSER_OPTIONS = By
			.cssSelector("#productDataGrid_hiding_modalDialog_content > ul");
	public static final By TBL_PRODUCTS = By.id("productDataGrid");
	public static final By TBL_PRODUCTS_GRID = By.cssSelector("#productDataGrid > tbody");
	public static final By TBL_PRODUCTS_LIST = By.cssSelector("#productDataGrid > tbody > td");
	public static final By TAB_CONTAINER_GRID = By.cssSelector("#tabcontainer > ul");
	public static final By TXT_PRODUCT_FILTER = By.id("productFilterType");
	public static final By POP_UP_BTN_SAVE = By.id("confirmDisableId");
	public static final By BTN_LOCATION_SETTINGS =  By.id("toggleinfo");
	public static final By DPD_HAS_LOCKER =  By.id("haslocker");
	public static final By DPD_HAS_ORDER_AHEAD =  By.id("hasonlineordering");
	public static final By DPD_HAS_PICK_UP_LOCATIONS =  By.id("haspickuplocations");
	public static final By LNK_PICK_UP_LOCATION =  By.id("pickupLocationToggle");
	public static final By LBL_LOCKER_PICK_UP_TITLE =  By.xpath("//*[@id='lockersystempickuptitle']/i");
	public static final By LNK_LOCKER_NAME=  By.xpath("//*[@id='pickuplockersystems']/div/a");
	public static final By TXT_SYSTEM_NAME=  By.id("systemName");
	public static final By LBL_SHELF_LIFE=  By.xpath("//*[@id='pickuplockersystems']/div/span");
    public static final By BUTTON_LOCATION_INFO = By.cssSelector("button#toggleinfo");
    public static final By DPD_RETRIEVE_ACCOUNT = By.cssSelector("select#retrieveaccount");
    public static final  By FIELD_RETRIEVE_CHECKBOX = By.cssSelector("div#enableRetrieveAccountOptions");
    public static final By TXT_ERR_MSG = By.cssSelector("dd.error-txt");
    private static final By TXT_HAS_LOCKERS = By.xpath("//dt[text()='Has Lockers']");
    private static final By LBL_LOCATION_SUMMARY = By.cssSelector("li[id='Location Summary']");
    public static final By TAB_PRODUCTS = By.id("loc-products");
    public static final By TXT_SEARCH = By.id("productFilterType");
    public static final By LBL_TAX_CATEGORY= By.xpath("(//td[@aria-describedby='productDataGrid_taxcat'])[2]");
    private static final By BTN_SHOW = By.xpath("//span[text()='Taxcat']//..//a[text()='Show']");
    private static final By BTN_APPLY = By.id("productDataGrid_hiding_modalDialog_footer_buttonok_lbl");
    public static final By ROW_PRODUCTS = By.cssSelector("#productDataGrid > tbody > tr");
    public static final By LBL_SPINNER_MSG =By.xpath("//div[@class='humane humane-libnotify-info']");
    
    public static final By BTN_FULL_SYNC =  By.id("fullsync");

    
	public void selectTab(String tabName) {
		try {
			foundation.click(By.xpath("//ul[@class='nav nav-tabs']//li/a[(text()='" + tabName + "')]"));
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	public void manageColumn(String columnNames) {
		try {
			foundation.click(BTN_MANAGE_COLUMNS);
			List<String> columnName = Arrays.asList(columnNames.split(Constants.DELIMITER_HASH));
			int columnCount = columnName.size();
			for (int count = 0; count < columnCount; count++) {
				foundation.click(By.xpath(
						"//div[@id='productDataGrid_hiding_modalDialog_content']/ul//li/span[@class='ui-iggrid-dialog-text'][text()='"
								+ columnName.get(count) + "']"));
			}
			foundation.click(POP_UP_BTN_APPLY);
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
	
	public List<String> getProductsHeaders() {
		List<String> tableHeaders = new ArrayList<>();
		try {
			WebElement tableProducts = getDriver().findElement(TBL_PRODUCTS);
			List<WebElement> columnHeaders = tableProducts
					.findElements(By.cssSelector("thead > tr > th > span.ui-iggrid-headertext"));
			for (WebElement columnHeader : columnHeaders) {
				tableHeaders.add(columnHeader.getText());
			}
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
		return tableHeaders;
	}

	public Map<Integer, Map<String, String>> getProductsRecords(String recordValue) {
		Map<Integer, Map<String, String>> productsData = new LinkedHashMap<>();
		int recordCount = 0;
		try {
			List<String> tableHeaders = getProductsHeaders();
			textBox.enterText(TXT_PRODUCT_FILTER, recordValue);
			WebElement tableProductsGrid = getDriver().findElement(TBL_PRODUCTS_GRID);
			List<WebElement> rows = tableProductsGrid.findElements(By.tagName("tr"));
			for (WebElement row : rows) {
				Map<String, String> productsRecord = new LinkedHashMap<>();
				for (int columnCount = 1; columnCount < tableHeaders.size() + 1; columnCount++) {
					WebElement column = row.findElement(By.cssSelector("td:nth-child(" + columnCount + ")"));
					productsRecord.put(tableHeaders.get(columnCount - 1), column.getText());
				}
				productsData.put(recordCount, productsRecord);
				recordCount++;
			}
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
		return productsData;
	}

    public void verifyHasLockerField(String defaultValue) {
        try {
            foundation.waitforElement(LBL_LOCATION_SUMMARY, 5);
            Assert.assertTrue(foundation.isDisplayed(TXT_HAS_LOCKERS));
            String value = dropDown.getSelectedItem(DPD_HAS_LOCKER);
            Assert.assertEquals(value, defaultValue);
            ExtFactory.getInstance().getExtent().log(Status.INFO, "Validated the has Locker default Value"+ defaultValue);
        }catch(Exception exc) {
            Assert.fail(exc.toString());
        }
    }
    
    public void showTaxCategory() {
        try {
        foundation.click(BTN_MANAGE_COLUMNS);
        foundation.click(BTN_SHOW);
        }
        catch (Exception exc) { 
        	Assert.fail(exc.toString());
        }
        foundation.click(BTN_APPLY);
    }
    
    public void updateLockerSettings(String enableORDisable) {    	
        dropDown.selectItem(LocationSummary.DPD_HAS_LOCKER, enableORDisable, Constants.TEXT);
        foundation.click(LocationSummary.BTN_SAVE);
        foundation.waitforElement(LBL_SPINNER_MSG, 2000);
    }

}

