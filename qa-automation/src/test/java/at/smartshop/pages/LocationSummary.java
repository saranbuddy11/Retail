package at.smartshop.pages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.aventstack.extentreports.Status;

import at.framework.browser.Browser;
import at.framework.browser.Factory;
import at.framework.reportsetup.ExtFactory;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.keys.Constants;
import at.smartshop.tests.TestInfra;

public class LocationSummary extends Factory {

	private Dropdown dropDown = new Dropdown();
	private TextBox textBox = new TextBox();
	private Foundation foundation = new Foundation();
	private LocationList locationList = new LocationList();
	private Login login = new Login();
	private Browser browser = new Browser();

	public static final By DPD_DISABLED = By.id("isdisabled");
	public static final By BTN_SAVE = By.id("saveBtn");
	public static final By BTN_MANAGE_COLUMNS = By.id("manageProductGridColumnButton");
	public static final By POP_UP_BTN_APPLY = By.id("productDataGrid_hiding_modalDialog_footer_buttonok_lbl");
	public static final By DLG_COLUMN_CHOOSER = By.id("productDataGrid_hiding_modalDialog_content");
	public static final By DLG_PRODUCT_COLUMN_CHOOSER_FOOTER = By.id("productDataGrid_hiding_modalDialog_footer");
	public static final By DLG_COLUMN_CHOOSER_OPTIONS = By
			.cssSelector("#productDataGrid_hiding_modalDialog_content > ul");
	public static final By TBL_PRODUCTS = By.id("productDataGrid");
	public static final By TBL_PRODUCTS_GRID = By.cssSelector("#productDataGrid > tbody");
	public static final By TBL_PRODUCTS_LIST = By.cssSelector("#productDataGrid > tbody > td");
	public static final By TAB_CONTAINER_GRID = By.cssSelector("#tabcontainer > ul");
	public static final By TXT_PRODUCT_FILTER = By.cssSelector("input#productFilterType");
	public static final By POP_UP_BTN_SAVE = By.id("confirmDisableId");
	public static final By BTN_LOCATION_SETTINGS = By.id("toggleinfo");
	public static final By DPD_HAS_LOCKER = By.id("haslocker");
	public static final By DPD_HAS_ORDER_AHEAD = By.id("hasonlineordering");
	public static final By DPD_HAS_PICK_UP_LOCATIONS = By.id("haspickuplocations");
	public static final By LNK_PICK_UP_LOCATION = By.id("pickupLocationToggle");
	public static final By LBL_LOCKER_PICK_UP_TITLE = By.xpath("//*[@id='lockersystempickuptitle']/i");
	public static final By LNK_LOCKER_NAME = By.xpath("//*[@id='pickuplockersystems']/div/a");
	public static final By TXT_SYSTEM_NAME = By.cssSelector("input#systemName");
	public static final By LBL_SHELF_LIFE = By.xpath("//*[@id='pickuplockersystems']/div/span");
	public static final By BUTTON_LOCATION_INFO = By.cssSelector("button#toggleinfo");
	public static final By DPD_RETRIEVE_ACCOUNT = By.cssSelector("select#retrieveaccount");
	public static final By FIELD_RETRIEVE_CHECKBOX = By.cssSelector("div#enableRetrieveAccountOptions");
	public static final By TXT_ERR_MSG = By.cssSelector("dd.error-txt");
	private static final By TXT_HAS_LOCKERS = By.xpath("//dt[text()='Has Lockers']");
	private static final By LBL_LOCATION_SUMMARY = By.cssSelector("li[id='Location Summary']");
	public static final By TAB_PRODUCTS = By.id("loc-products");
	public static final By TXT_SEARCH = By.id("productFilterType");
	public static final By LBL_TAX_CATEGORY = By
			.xpath("//td[@role='gridcell' and @aria-describedby='productDataGrid_taxcat']");
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
	public static final By TBL_INVENTORY = By.id("inventoryDataGrid");
	public static final By DPD_SHOW_PROD_LOOKUP = By.id("showprdlup");
	public static final By LNK_INVENTORY = By.cssSelector("a#loc-inventory");
	public static final By LNK_TAX_MAPPING = By.xpath("//a[text()='Tax Mapping']");
	public static final By BTN_ADD_MAPPING = By.cssSelector("a#addMapping");
	public static final By DPD_TAXCAT = By.cssSelector("select#taxcat");
	public static final By DPD_RATE = By.cssSelector("select#taxname");
	public static final By BTN_POPUP_SAVE = By.cssSelector("a#taxcatsave");
	public static final By BTN_POPUP_REMOVE = By.cssSelector("a#taxcatremove");
	public static final By DPD_VDI_PROVDIER = By.xpath("//select[@id='vdiprovideradded']");
	public static final By CHK_VDI = By.xpath("//input[@id='vdicbx']");
	public static final By BTN_VDI_PLUS = By.xpath("//button[@id='vdi-plus-btn']");
	public static final By BTN_VDI_DEL = By.xpath("//button[@onclick='vdiDelBtnClick(this)']");
	public static final By TXT_USER_KEY = By.xpath("//input[@id='vdiuserkey-added']");
	public static final By BTN_YES = By.xpath("//button[text()='Yes']");
	public static final By BTN_NO = By.xpath("//button[text()='No ']");
	public static final By LBL_USER_KEY = By.xpath("//input[@id='vdiuserkey-added']");
	public static final By DPD_PRINTGROUP = By.cssSelector("select#printer");
	public static final By LBL_PRINT_COLUMN = By.xpath("//tbody/tr/td[@aria-describedby='productDataGrid_printer']");
	public static final By LBL_PRINT_DOWN_ARROW = By
			.xpath("//td[@aria-describedby='productDataGrid_printer']//div[contains(@class,'ui-icon-triangle')]");
	public static final By LBL_REASON_CODE = By
			.xpath("//td[@aria-describedby='inventoryDataGrid_reasoncode'][text()='-Choose-']");
	public static final By LIST_REASON_CODE = By
			.xpath("//div[@id='promoGrid_editor_list']/..//ul[@class='ui-igcombo-listitemholder']//li");
	public static final By TAB_TAX_MAPPING = By.id("loc-taxMapping");
	public static final By DPD_TAX_CATEGORY = By.id("taxcat");
	public static final By DPD_TAX_RATE_EDIT = By.id("targetid");
	public static final By BTN_CANCEL_MAPPING = By.id("taxcatcancel");
	public static final By BTN_SAVE_MAPPING = By.id("taxcatsave");
	public static final By BTN_REMOVE_MAPPING = By.id("taxcatremove");
	public static final By TXT_SEARCH_TAX_MAPPING = By.xpath("//div[@id='taxmapdt_filter']//input");
	public static final By BTN_DEVICE = By.cssSelector("a#loc-kiosk");
	public static final By TBL_DEVICE_POPUP_GRID = By
			.cssSelector("div.dataTables_scroll > div.dataTables_scrollHead > div > table >thead");
	public static final By TBL_DEVICE_POPUP_ROW = By.xpath("//*[@id='choosekskdt']/tbody/tr");
	public static final By TXT_DEVICE_POPUP_SEARCH = By.xpath("//input[@aria-controls='choosekskdt']");
	public static final By BTN_DEVICE_ADD = By.xpath("//a[text()='Add']");
	public static final By BTN_DEVICE_CLOSE = By.xpath("//a[@id='modalcancel']");
	public static final By LBL_ROW_HEADER = By.xpath("//div[@class='dataTables_scrollHeadInner']//th[text()='Name']");
	public static final By LBL_COLUMN_DATA = By.xpath("//div[@class='namelefttxt']");
	public static final By LBL_TABLE_DATA = By
			.xpath("//div[@class='dataTables_scroll']//td[@class='dataTables_empty']");
	public static final By LBL_TABLEINFO = By.id("choosekskdt_info");
	public static final By LBL_CAUTION_ICON = By
			.xpath("//i[@class='fa fa-exclamation-triangle' and @style='color: #FF4C5B;']");
	public static final By LBL_TICKMARK_ICON = By
			.xpath("//i[@class='fa fa-check-circle' and @style='color: #2983C4;']");
	public static final By LBL_HOVER_MESSAGE = By.xpath("//td[@class='ui-state-hover']");
	public static final By TXT_DEVICE_STATUS = By.xpath("//span[@id='devicestatus']");
	public static final By TXT_DEVICE_SUMMARY = By.xpath("//li[@id='Device Summary']");
	public static final By TXT_DEVICE_NAME = By.xpath("//dd[@id='kioskshow-name']");
	public static final By TBL_DEVICE_GRID = By.id("deviceDataGrid_table");
	public static final By TBL_DEVICE_ROW = By.xpath("//table[@id='deviceDataGrid_table']/tbody/tr");
	public static final By LBL_TBL_HEADER = By.xpath("//th[contains(@id,'deviceDataGrid_table')]//span[@class='ui-iggrid-headertext']");
	public static final By LBL_SHOW_RECORDS = By.xpath("//div[@id='deviceDataGrid_table_container']//div[@class='ui-iggrid-results']");
	public static final By LBL_PAGER = By.id("deviceDataGrid_table_pager");
	private static final By BTN_SHOW = By.xpath("//span[text()='Taxcat']//..//a[text()='Show']");
	public static final By BTN_APPLY = By.id("productDataGrid_hiding_modalDialog_footer_buttonok_lbl");
	public static final By TXT_PAY_CYCLE_NAME = By.xpath("//*[contains(@id,'newrow')]//input[contains(@class,'paycycle-grpname')]");
	public static final By TXT_PAY_CYCLE_SPEND_LIMIT = By.xpath("//*[contains(@id,'newrow')]//input[contains(@class,'paycycle-spndlimit')]");
	public static final By DPD_PAYROLL_DEDUCT = By.id("payrolldeduct");
	public static final By TBL_NAME_HEADER = By.xpath("//th[@id='productDataGrid_name']");
	public static final By DPD_SHOW_RECORD = By.xpath("//div[@id='productDataGrid_editor_dropDownButton']");
	public static final By TXT_10_RECORD = By.xpath("//span[@id='productDataGrid_editor_item_2']");
	public static final By TXT_5_RECORD = By.xpath("//span[@id='productDataGrid_editor_item_1']");
	public static final By TXT_PRODUCTS_COUNT = By.xpath("//span[@id='productDataGrid_pager_label']");
	public static final By BTN_EXPORT = By.cssSelector("button#productExportBtn");
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
	public static final By TXT_FIND_DEVICE = By.xpath("//*[@id='choosekskdt_filter']/label/input");
	public static final By TBL_DEVICE_LIST = By.xpath("//table[@id='choosekskdt']//*[@class='namelefttxt']");

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
				String status = foundation.getText(By.xpath(
						"//div[@id='productDataGrid_hiding_modalDialog_content']/ul//li/span[@class='ui-iggrid-dialog-text'][text()='"
								+ columnName.get(count) + "']//..//a"));
				if (!status.equalsIgnoreCase(Constants.HIDE))
					foundation.click(By.xpath(
							"//div[@id='productDataGrid_hiding_modalDialog_content']/ul//li/span[@class='ui-iggrid-dialog-text'][text()='"
									+ columnName.get(count) + "']"));
			}
			foundation.objectFocus(POP_UP_BTN_APPLY);
			foundation.click(DLG_PRODUCT_COLUMN_CHOOSER_FOOTER);
			foundation.threadWait(Constants.TWO_SECOND);
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

	public List<String> getProductsNames() {
		List<String> productNames = new LinkedList<>();
		WebElement tableProductsGrid = getDriver().findElement(TBL_PRODUCTS_GRID);
		List<WebElement> records = tableProductsGrid.findElements(By.tagName("tr"));
		for (int iter = 1; iter > records.size() + 1; iter++) {
			productNames.add(foundation.getText(By.xpath("//table[@id='productDataGrid']/tbody/tr/td[" + iter
					+ "][@aria-describedby='productDataGrid_name']")));
		}
		return productNames;
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
		foundation.click(BTN_SAVE);
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

		foundation.waitforElement(By.xpath("//td[@aria-describedby='inventoryDataGrid_scancode'][text()='" + scancode
				+ "']//..//td[@aria-describedby='inventoryDataGrid_qtyonhand']"), Constants.SHORT_TIME);
		foundation.click(By.xpath("//td[@aria-describedby='inventoryDataGrid_scancode'][text()='" + scancode
				+ "']//..//td[@aria-describedby='inventoryDataGrid_qtyonhand']"));
		foundation.waitforElement(
				By.xpath("//td[@aria-describedby='inventoryDataGrid_scancode'][text()='" + scancode
						+ "']//..//td[@aria-describedby='inventoryDataGrid_qtyonhand']/div/div/span/input"),
				Constants.ONE_SECOND);
		textBox.enterText(
				By.xpath("//td[@aria-describedby='inventoryDataGrid_scancode'][text()='" + scancode
						+ "']//..//td[@aria-describedby='inventoryDataGrid_qtyonhand']/div/div/span/input"),
				inventoryValue);
		foundation.click(By.xpath("//td[@aria-describedby='inventoryDataGrid_scancode'][text()='" + scancode
				+ "']//..//td[@aria-describedby='inventoryDataGrid_reasoncode']/span/div"));
		foundation.waitforElement(By.xpath("//ul[@class='ui-igcombo-listitemholder']/li[text()='" + reasonCode + "']"),
				Constants.TWO_SECOND);
		foundation.click(By.xpath("//ul[@class='ui-igcombo-listitemholder']/li[text()='" + reasonCode + "']"));

		foundation.click(TXT_INVENTORY_FILTER);
		foundation.waitforElement(TXT_INVENTORY_FILTER, Constants.ONE_SECOND);
	}

	public By objUploadStatus(String uploadMessage) {
		return By.xpath("//*[text()='" + uploadMessage + "']");
	}

	public void kiosklanguageSetting(String location, String defaultLanguage, String altLanguage) {

		locationList.selectLocationName(location);
		foundation.waitforElement(LocationSummary.DPD_KIOSK_LANGUAGE, Constants.SHORT_TIME);
		dropDown.selectItem(LocationSummary.DPD_KIOSK_LANGUAGE, defaultLanguage, Constants.TEXT);
		dropDown.selectItem(LocationSummary.DPD_ALTERNATE_LANGUAGE, altLanguage, Constants.TEXT);
		foundation.click(LocationSummary.BTN_SYNC);
		foundation.click(LocationSummary.BTN_SAVE);
		foundation.waitforElementToDisappear(LocationList.TXT_SPINNER_MSG, Constants.EXTRA_LONG_TIME);
		foundation.waitforClikableElement(Login.LBL_USER_NAME, Constants.EXTRA_LONG_TIME);
		browser.close();

	}

	public Map<String, String> getProductDetails(String name) {
		Map<String, String> productsRecord = new LinkedHashMap<>();
		try {
			List<String> tableHeaders = getProductsHeaders();
			for (int columnCount = 1; columnCount < tableHeaders.size() + 1; columnCount++) {
				WebElement column = getDriver().findElement(By.xpath("//table[@id='productDataGrid']//tr//span[text()='"
						+ name + "']//..//..//..//..//tbody//td[" + columnCount + "]"));
				productsRecord.put(tableHeaders.get(columnCount - 1), column.getText());
			}
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
		return productsRecord;

	}

	public void saveTaxMapping(String taxCategory, String rate) {
		foundation.click(TAB_TAX_MAPPING);
		textBox.enterText(TXT_SEARCH_TAX_MAPPING, taxCategory);
		if (foundation.isDisplayed(objTaxCategory(taxCategory)) == false) {
			foundation.click(BTN_ADD_MAPPING);
			foundation.waitforElement(DPD_TAXCAT, Constants.SHORT_TIME);
			dropDown.selectItem(DPD_TAXCAT, taxCategory, Constants.TEXT);
			dropDown.selectItem(DPD_RATE, rate, Constants.TEXT);
			foundation.click(BTN_POPUP_SAVE);
			foundation.click(TAB_TAX_MAPPING);
		}
	}

	public void removeTaxMapping(String taxCategory) {
		foundation.click(TAB_TAX_MAPPING);
		textBox.enterText(TXT_SEARCH_TAX_MAPPING, taxCategory);
		foundation.click(objTaxCategory(taxCategory));
		foundation.waitforElement(BTN_POPUP_REMOVE, Constants.SHORT_TIME);
		foundation.click(BTN_POPUP_REMOVE);
		foundation.click(TAB_TAX_MAPPING);
	}

	public By objVerifyTaxRate(String taxRate) {
		return By.xpath("//table[@id='taxmapdt']//tr/td[text()='" + taxRate + "']");
	}

	public By objProductPrice(String productName) {
		return By.xpath("//td[text()='" + productName + "']//..//td[@aria-describedby='productDataGrid_price']");
	}

	public String getTextAttribute(By object, String attribute) {
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

	public By objPrintGroup(String text) {
		return By.xpath("//li[@data-value='" + text + "']");
	}

	public void showHideManageColumn(String showOrHide, String columnName) {
		By xpathHideOrShow = By
				.xpath("//div[@id='productDataGrid_hiding_modalDialog']//span[text()='" + columnName + "']//..//a");
		String hideOrShow = foundation.getText(xpathHideOrShow);
		if (showOrHide.equals(Constants.SHOW)) {
			if (hideOrShow.equals(Constants.SHOW)) {
				foundation.click(xpathHideOrShow);
			}
		} else {
			if (hideOrShow.equals(Constants.HIDE)) {
				foundation.click(xpathHideOrShow);
			}
		}
	}

	public String getCellData(String ariaDescribedby) {
		foundation.waitforElement(By.xpath("//tr[@role='row']//td[@aria-describedby='" + ariaDescribedby + "']"),
				Constants.EXTRA_LONG_TIME);
		return foundation.getText(By.xpath("//tr[@role='row']//td[@aria-describedby='" + ariaDescribedby + "']"));
	}

	public By objTaxCategory(String taxCategory) {
		return By.xpath("//table[@id='taxmapdt']//*[text()='" + taxCategory + "']");
	}
	
	public void addPaycyle(String location,String payCycle) {
		locationList.selectLocationName(location);
		int totalPaycycleRows = foundation.getSizeofListElement(By.xpath("//*[@id='payRollRange']//*[@class='btn-mini pull-right']//i"));		
		foundation.click(By.xpath("(//*[@id='payRollRange']//*[@class='btn-mini pull-right']//i)["+totalPaycycleRows+"]"));
		textBox.enterText(TXT_PAY_CYCLE_NAME, payCycle);		
		foundation.click(BTN_SAVE);
		foundation.waitforElementToDisappear(LocationList.TXT_SPINNER_MSG, Constants.EXTRA_LONG_TIME);
		foundation.waitforClikableElement(Login.LBL_USER_NAME, Constants.EXTRA_LONG_TIME);
	}
	
	public void deletePaycyle(String location,String payCycle) {
		locationList.selectLocationName(location);
		int totalPaycycleRows = foundation.getSizeofListElement(By.xpath("//button[@class='btn-mini']//i[contains(@class,'delBtn ')]"));
		for(int i=1;i<=totalPaycycleRows;i++) {
			if (textBox.getTextFromInput(By.xpath("(//*[contains(@class,'paycycle-grpname')])[" + i + "]"))
					.contains(payCycle)) {
				foundation.click(By.xpath("(//button[@class='btn-mini']//i[contains(@class,'delBtn ')])[" + i + "]"));
				foundation.threadWait(Constants.ONE_SECOND);
				break;
			}
		}	
		foundation.click(BTN_SAVE);
		foundation.waitforElementToDisappear(LocationList.TXT_SPINNER_MSG, Constants.EXTRA_LONG_TIME);
		foundation.waitforClikableElement(Login.LBL_USER_NAME, Constants.EXTRA_LONG_TIME);
	}
	
	public void turnOnOROffPayRollDeduct(String location,String yesORno) {
		locationList.selectLocationName(location);
		dropDown.selectItem(DPD_PAYROLL_DEDUCT, yesORno, Constants.TEXT);
		foundation.click(BTN_SAVE);
		foundation.waitforElementToDisappear(LocationList.TXT_SPINNER_MSG, Constants.EXTRA_LONG_TIME);
		foundation.waitforClikableElement(Login.LBL_USER_NAME, Constants.EXTRA_LONG_TIME);
	}
	
	public void editPaycyle(String location,String payCycle,String updatedPaycycle) {
		locationList.selectLocationName(location);
		int totalPaycycleRows = foundation.getSizeofListElement(By.xpath("//*[@id='payRollRange']//*[@class='btn-mini pull-right']//i"));
		for(int i=1;i<=totalPaycycleRows;i++) {
			if(textBox.getTextFromInput(By.xpath("(//*[contains(@class,'paycycle-grpname')])["+i+"]")).contains(payCycle))
			{
			textBox.enterText(By.xpath("(//input[contains(@class,'paycycle-grpname')])["+i+"]"), updatedPaycycle);
			foundation.threadWait(Constants.ONE_SECOND);
			}
		}		
		foundation.click(BTN_SAVE);
		foundation.waitforElementToDisappear(LocationList.TXT_SPINNER_MSG, Constants.EXTRA_LONG_TIME);
		foundation.waitforClikableElement(Login.LBL_USER_NAME, Constants.EXTRA_LONG_TIME);
	}

	public List<String> getColumnValues(By columnData) {
		String text = null;
		List<String> elementsText = new ArrayList<String>();
		try {
			List<WebElement> ListElement = getDriver().findElements(columnData);

			for (int i = 0; i < ListElement.size(); i++) {
				text = ListElement.get(i).getText();
				elementsText.add(text);
			}
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
		return elementsText;
	}

	public Boolean verifySortAscending(By columnData) {
		boolean ascending = false;
		try {
			List<String> listRuleNameAscending = getColumnValues(columnData);

			ascending = listRuleNameAscending.stream().sorted(Comparator.naturalOrder()).collect(Collectors.toList())
					.equals(listRuleNameAscending);

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
		return ascending;
	}

	public boolean verifySortDescending(By columnData) {
		boolean descending = false;
		try {
			List<String> listRuleNameDescending = getColumnValues(columnData);
			descending = listRuleNameDescending.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList())
					.equals(listRuleNameDescending);
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
		return descending;
	}

	public By objColumnHeaders(String columnName) {
		return By.xpath("//table[@id='productDataGrid']//span[text()='" + columnName + "']");
	}

	public By objDevice(String deviceName) {
		return By.xpath("//div[@class='ig-tree-text' and text()='" + deviceName + "']");

	}

}
