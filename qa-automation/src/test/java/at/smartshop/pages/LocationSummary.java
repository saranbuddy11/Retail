package at.smartshop.pages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.aventstack.extentreports.Status;

import at.framework.browser.Factory;
import at.framework.reportsetup.ExtFactory;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.keys.Constants;

public class LocationSummary extends Factory {
	private Dropdown dropDown = new Dropdown();
	private TextBox textBox = new TextBox();
	private Foundation foundation = new Foundation();

	public static final By DPD_DISABLED = By.id("isdisabled");
	public static final By BTN_SAVE = By.id("saveBtn");
	public static final By BTN_MANAGE_COLUMNS = By.id("manageProductGridColumnButton");
	public static final By POP_UP_BTN_APPLY = By.id("productDataGrid_hiding_modalDialog_footer_buttonok_lbl");
	public static final By DLG_COLUMN_CHOOSER = By.id("productDataGrid_hiding_modalDialog_content");
	public static final By DLG_PRODUCT_COLUMN_CHOOSER_FOOTER = By.id("productDataGrid_hiding_modalDialog_footer");
	public static final By DLG_COLUMN_CHOOSER_OPTIONS = By.cssSelector("#productDataGrid_hiding_modalDialog_content > ul");
	public static final By TBL_PRODUCTS = By.id("productDataGrid");
	public static final By TBL_PRODUCTS_GRID = By.cssSelector("#productDataGrid > tbody");
	public static final By TBL_PRODUCTS_LIST = By.cssSelector("#productDataGrid > tbody > td");
	public static final By TAB_CONTAINER_GRID = By.cssSelector("#tabcontainer > ul");
	public static final By TXT_PRODUCT_FILTER = By.id("productFilterType");
	public static final By POP_UP_BTN_SAVE = By.id("confirmDisableId");
	private static final By BTN_SHOW = By.xpath("//span[text()='Taxcat']//..//a[text()='Show']");
	private static final By BTN_APPLY = By.id("productDataGrid_hiding_modalDialog_footer_buttonok_lbl");
	public static final By BTN_LOCATION_SETTINGS = By.id("toggleinfo");
	public static final By DPD_HAS_LOCKER = By.id("haslocker");
	public static final By DPD_HAS_ORDER_AHEAD = By.id("hasonlineordering");
	public static final By DPD_HAS_PICK_UP_LOCATIONS = By.id("haspickuplocations");
	public static final By LNK_PICK_UP_LOCATION = By.id("pickupLocationToggle");
	public static final By LBL_LOCKER_PICK_UP_TITLE = By.xpath("//*[@id='lockersystempickuptitle']/i");
	public static final By LNK_LOCKER_NAME = By.xpath("//*[@id='pickuplockersystems']/div/a");
	public static final By TXT_SYSTEM_NAME = By.id("systemName");
	public static final By LBL_SHELF_LIFE = By.xpath("//*[@id='pickuplockersystems']/div/span");
	public static final By BUTTON_LOCATION_INFO = By.cssSelector("button#toggleinfo");
	public static final By DPD_RETRIEVE_ACCOUNT = By.cssSelector("select#retrieveaccount");
	public static final By FIELD_RETRIEVE_CHECKBOX = By.cssSelector("div#enableRetrieveAccountOptions");
	public static final By TXT_ERR_MSG = By.cssSelector("dd.error-txt");
	private static final By TXT_HAS_LOCKERS = By.xpath("//dt[text()='Has Lockers']");
	private static final By LBL_LOCATION_SUMMARY = By.cssSelector("li[id='Location Summary']");
	public static final By TAB_PRODUCTS = By.id("loc-products");
	public static final By TXT_SEARCH = By.id("productFilterType");
	public static final By LBL_TAX_CATEGORY = By.xpath("//td[@role='gridcell' and @aria-describedby='productDataGrid_taxcat']");
	public static final By ROW_PRODUCTS = By.cssSelector("#productDataGrid > tbody > tr");
	public static final By LBL_SPINNER_MSG = By.xpath("//div[@class='humane humane-libnotify-info']");
	public static final By BTN_FULL_SYNC = By.id("fullsync");
	public static final By TXT_PRICE_IN_GRID = By.id("fullsync");
	public static final By TXT_ADD_PRODUCT_SEARCH = By.id("productFilterTypes");
	public static final By BTN_ADD_PRODUCT_ADD = By.id("modalsave");
	public static final By BTN_DEPLOY_DEVICE = By.id("deployKiosk");
	public static final By TXT_DEVICE_SEARCH = By.id("deviceFilterType");
	public static final By BTN_HOME_COMMERCIAL = By.cssSelector("a#loc-homeCommercial");
	public static final By BTN_UPLOAD_INPUT = By.xpath("//div[@class ='qq-upload-button btn btn-success']/input");
	public static final By BTN_ADD_HOME_COMMERCIAL = By.xpath("//a[text()='Add Home Commercial']");
	public static final By TXT_UPLOAD_NEW = By.xpath("//a[text()='Upload New']");
	public static final By BTN_UPLOAD_IMAGE = By.xpath("//div[@class ='qq-upload-button btn btn-success']");
	public static final By TXT_ADD_NAME = By.xpath("//input[@id = 'cmrhometext']");
	public static final By BTN_ADD = By.xpath("//a[text()= 'Add']");
	public static final By TXT_CMR_FILTER = By.id("cmrHomeFilterType");
	public static final By BTN_REMOVE = By.xpath("//a[@id='previewremove']");
	public static final By TXT_UPLOAD_STATUS = By.xpath("//span[@class='qq-upload-status-text']");
	public static final By LINK_HOME_PAGE = By.xpath("//a[@id='sup-location']");
	public static final By DPD_KIOSK_LANGUAGE = By.id("ksklanguage");
	public static final By DPD_ALTERNATE_LANGUAGE = By.id("altlanguage");
	public static final By BTN_SYNC = By.xpath("//button[text()='Update Prices & Full Sync']");
	public static final By TXT_CUSTOMER = By.id("customer");
	public static final By DPD_ROUTE = By.id("route");
	public static final By TXT_LOCATION_NUMBER = By.id("locationnumber");
	public static final By TXT_INVENTORY_FILTER = By.id("inventoryFilterType");
	public static final By BTN_ADD_PRODUCT = By.id("addProd");
	public static final By TBL_INVENTORY= By.id("inventoryDataGrid");
	public static final By DPD_SHOW_PROD_LOOKUP = By.id("showprdlup");
	public static final By LNK_INVENTORY = By.cssSelector("a#loc-inventory");
	public static final By DPD_VDI_PROVDIER = By.xpath("//select[@id='vdiprovideradded']");
	public static final By CHK_VDI = By.xpath("//input[@id='vdicbx']");
	public static final By BTN_VDI_PLUS = By.xpath("//button[@id='vdi-plus-btn']");
	public static final By BTN_VDI_DEL = By.xpath("//button[@onclick='vdiDelBtnClick(this)']");
	public static final By TXT_USER_KEY = By.xpath("//input[@id='vdiuserkey-added']");
	public static final By BTN_YES = By.xpath("//button[text()='Yes']");
	public static final By BTN_NO = By.xpath("//button[text()='No ']");
	public static final By LBL_USER_KEY = By.xpath("//input[@id='vdiuserkey-added']");
	public static final By LBL_TAX_MAPPING = By.xpath("//a[@id='addMapping']");
	public static final By DPD_TAX_CAT = By.xpath("//select[@id='taxcat']");
	public static final By DPD_TAX_RATE = By.xpath("//select[@id='taxname']");
	public static final By LBL_TAX_CAT_SAVE = By.xpath("//a[@id='taxcatsave']");
	public static final By LBL_TAX_CAT_REMOVE = By.xpath("//a[@id='taxcatremove']");
	public static final By LBL_TAX_CAT_CANCEL = By.xpath("//a[@id='taxcatcancel']");
	public static final By TBL_TAX_GRID = By.id("taxmapdt");
	public static final By TBL_ROW = By.xpath("//*[@id='taxmapdt']/tbody/tr");
	public static final By TXT_TAX_FILTER = By.cssSelector("#taxmapdt_filter > label > input[type=text]");
	public static final By BTN_CLOSE_COMMERCIAL = By.xpath("//a[text()='Add Close Commercial']");
	public static final By DPD_TAX_RATE_2 = By.xpath("//select[@id='targetid']");


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
			foundation.objectFocus(POP_UP_BTN_APPLY);
			foundation.click(DLG_PRODUCT_COLUMN_CHOOSER_FOOTER);
			if (foundation.isDisplayed(DLG_PRODUCT_COLUMN_CHOOSER_FOOTER)) {
				foundation.click(POP_UP_BTN_APPLY);
			}
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
			foundation.waitforElement(TBL_PRODUCTS_GRID, 5);
			List<String> tableHeaders = getProductsHeaders();
			foundation.waitforElement(TXT_PRODUCT_FILTER, 2);
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

	public void showTaxCategory() {
		try {
			foundation.click(BTN_MANAGE_COLUMNS);
			foundation.click(BTN_SHOW);
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
		foundation.click(BTN_APPLY);
	}

	public By objTable(String homeCommercial) {

		return By.xpath("//td[text()='" + homeCommercial + "']");

	}

	public void verifyHasLockerField(String defaultValue) {
		try {
			foundation.waitforElement(LBL_LOCATION_SUMMARY, Constants.SHORT_TIME);
			Assert.assertTrue(foundation.isDisplayed(TXT_HAS_LOCKERS));
			String value = dropDown.getSelectedItem(DPD_HAS_LOCKER);
			Assert.assertEquals(value, defaultValue);
			ExtFactory.getInstance().getExtent().log(Status.INFO,
					"Validated the has Locker default Value" + defaultValue);
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	public void updateLockerSettings(String enableORDisable) {
		dropDown.selectItem(LocationSummary.DPD_HAS_LOCKER, enableORDisable, Constants.TEXT);
		foundation.click(LocationSummary.BTN_SAVE);
		foundation.waitforElement(LocationList.DPD_LOCATION_LIST, Constants.SHORT_TIME);
	}

	public void enterPrice(String scancode, String price) {
		By priceLink = By.xpath("//td[text()='" + scancode + "']//..//td[@aria-describedby='productDataGrid_price']");
		By priceInput = By
				.xpath("//td[text()='" + scancode + "']//..//td[@aria-describedby='productDataGrid_price']//input");
		foundation.click(priceLink);
		textBox.enterText(priceInput, Keys.CONTROL + "a" + Keys.BACK_SPACE);
		textBox.enterText(priceInput, price);
		ExtFactory.getInstance().getExtent().log(Status.INFO, "updated price is" + foundation.getText(priceLink));
	}

	public void addProduct(String scancode) {
		foundation.click(BTN_ADD_PRODUCT);
		foundation.waitforElement(TXT_ADD_PRODUCT_SEARCH, 3);
		textBox.enterText(TXT_ADD_PRODUCT_SEARCH, scancode);
		foundation.click(By.xpath("//td[@aria-describedby='chooseprddt_scancode'][text()='" + scancode + "']"));
		foundation.click(BTN_ADD_PRODUCT_ADD);
		foundation.waitforElement(BTN_SAVE, 3);
	}

	public void selectDeviceName(String deviceName) {
		foundation.click(By.xpath("//a[text()='" + deviceName + "']"));
	}

	public void addHomeCommercial(String imageName, String imagePath) {

		foundation.waitforElement(BTN_HOME_COMMERCIAL, Constants.SHORT_TIME);
		foundation.click(BTN_HOME_COMMERCIAL);
		foundation.click(BTN_ADD_HOME_COMMERCIAL);
		foundation.click(TXT_UPLOAD_NEW);
		textBox.enterText(BTN_UPLOAD_INPUT, imagePath);
		textBox.enterText(TXT_ADD_NAME, imageName);
		foundation.click(BTN_ADD);
		foundation.click(BTN_SYNC);
		foundation.isDisplayed(LBL_SPINNER_MSG);
		foundation.waitforElement(Login.LBL_USER_NAME, Constants.SHORT_TIME);

	}

	public void removeHomeCommercial(String imageName) {
		foundation.waitforElement(BTN_HOME_COMMERCIAL, Constants.SHORT_TIME);
		foundation.click(BTN_HOME_COMMERCIAL);
		textBox.enterText(TXT_CMR_FILTER, imageName);
		foundation.click(objTable(imageName));
		foundation.waitforElement(BTN_REMOVE, Constants.SHORT_TIME);
		foundation.click(BTN_REMOVE);
		foundation.waitforElement(BTN_SYNC, Constants.SHORT_TIME);
		foundation.click(BTN_SYNC);
		foundation.isDisplayed(LocationSummary.LBL_SPINNER_MSG);
		foundation.waitforElement(Login.LBL_USER_NAME, Constants.SHORT_TIME);
		foundation.refreshPage();

	}

	public void updateInventory(String scancode, String inventoryValue, String reasonCode) {

		foundation.waitforElement(By.xpath("//td[@aria-describedby='inventoryDataGrid_scancode'][text()=" + scancode
				+ "]//..//td[@aria-describedby='inventoryDataGrid_qtyonhand']"), Constants.TWO_SECOND);
		foundation.click(By.xpath("//td[@aria-describedby='inventoryDataGrid_scancode'][text()=" + scancode
				+ "]//..//td[@aria-describedby='inventoryDataGrid_qtyonhand']"));
		foundation.waitforElement(By.xpath("//td[@aria-describedby='inventoryDataGrid_scancode'][text()=" + scancode
				+ "]//..//td[@aria-describedby='inventoryDataGrid_qtyonhand']/div/div/span/input"), Constants.ONE_SECOND);
		textBox.enterText(
				By.xpath("//td[@aria-describedby='inventoryDataGrid_scancode'][text()='" + scancode
						+ "']//..//td[@aria-describedby='inventoryDataGrid_qtyonhand']/div/div/span/input"),
				inventoryValue);
		foundation.click(By.xpath("//td[@aria-describedby='inventoryDataGrid_scancode'][text()=" + scancode
				+ "]//..//td[@aria-describedby='inventoryDataGrid_reasoncode']/span/div"));
		foundation.waitforElement(By.xpath("//ul[@class='ui-igcombo-listitemholder']/li[text()='" + reasonCode + "']"),
				Constants.TWO_SECOND);
		foundation.click(By.xpath("//ul[@class='ui-igcombo-listitemholder']/li[text()='" + reasonCode + "']"));
		foundation.click(TXT_INVENTORY_FILTER);
		foundation.waitforElement(TXT_INVENTORY_FILTER, Constants.ONE_SECOND);
	}

	public By objUploadStatus(String uploadMessage) {

		return By.xpath("//*[text()='" + uploadMessage + "']");

	}
	
	public Map<String, String> getProductDetails(String name) {
		Map<String, String> productsRecord = new LinkedHashMap<>();
		try {
			List<String> tableHeaders = getProductsHeaders();
			for (int columnCount = 1; columnCount < tableHeaders.size() + 1; columnCount++) {
				WebElement column = getDriver().findElement(By.xpath("//table[@id='productDataGrid']//tr//span[text()='"+ name +"']//..//..//..//..//tbody//td[" + columnCount + "]"));
				productsRecord.put(tableHeaders.get(columnCount - 1), column.getText());
			}
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
		return productsRecord;

	}

	public By objProductPrice(String productName) {
		return By.xpath("//td[text()='" + productName + "']//..//td[@aria-describedby='productDataGrid_price']");
	}

	public String getTextAttribute(By object,String attribute) {
		String textAttribute = null;
		try {
			textAttribute = getDriver().findElement(object).getAttribute(attribute);
			if (ExtFactory.getInstance().getExtent() != null) {
				ExtFactory.getInstance().getExtent().log(Status.INFO, object + " value is " + textAttribute);
			}
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
		return textAttribute;
	}

	public Boolean verifyDPDValue(String text) {
		Boolean flag = false;
		WebElement drpdwn = getDriver().findElement(DPD_VDI_PROVDIER);
		Select dpdSel = new Select(drpdwn);
		List<WebElement> DrpDwnList = dpdSel.getOptions();
		for (WebElement webElement : DrpDwnList) {

			if (webElement.getText().contains(text)) {
				flag = true;
				break;
			}
		}
		return flag;
		
	}
}
