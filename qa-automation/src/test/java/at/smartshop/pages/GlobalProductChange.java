package at.smartshop.pages;

import org.openqa.selenium.By;

import at.framework.browser.Factory;

public class GlobalProductChange extends Factory {
	public static final By TXT_LOCATION_SEARCH = By.id("loc-search");
	public static final By TBL_LOCATION_LIST = By.id("location-list");
	public static final By BTN_LOCATION_APPLY = By.id("loc-filter-apply");
	public static final By BTN_PRODUCT_APPLY = By.id("prd-filter-apply");
	public static final By TAB_PRODUCT = By.cssSelector("#li2 > a");
	public static final By BTN_NEXT = By.id("prd-dt-next");
	public static final By BTN_INCREMENT = By.xpath("//*[@id='increment-radio-btn']/..");
	public static final By TXT_PRICE = By.id("prd-price");
	public static final By TXT_MIN = By.id("prd-min");
	public static final By TXT_MAX = By.id("prd-max");
	public static final By TXT_PRODUCT_NAME = By.id("filter-name");
	public static final By LBL_PRICE = By.xpath("//*[@id='prd-change-form']/dt[text()='Price']");
	public static final By LBL_MIN = By.xpath("//*[@id='prd-change-form']/dt[text()='Min']");
	public static final By LBL_OPERATOR_MIN = By.xpath("//dt[text()='Min']");
	public static final By LBL_MAX = By.xpath("//*[@id='prd-change-form']/dt[text()='Max']");
	public static final By LBL_OPERATOR_MAX = By.xpath("//dt[text()='Max']");
	public static final By LBL_PICK_LIST = By.xpath("//*[@id='prd-change-form']/dt[text()='Pick List Action']");
	public static final By LBL_OPERATOR_PICK_LIST = By.xpath("//dt[text()='Pick List Action']");
	public static final By BTN_SUBMIT = By.id("prd-update-submit");
	public static final By BTN_OK = By.cssSelector("button.ajs-button.ajs-ok");
	public static final By BTN_CANCEL = By.cssSelector("button.ajs-button.ajs-cancel");
	public static final By POP_UP_HEADER = By.cssSelector("div.ajs-header");
	public static final By MSG_SUCCESS = By.xpath("//div[text()='Updated 1 product(s)!']");
	public static final By RDO_OPERATOR_PRODUCT_CHANGE = By.xpath("//label[text()='Operator Product Catalog Change']");
	public static final By TXT_LOCATION_PRODUCT = By.id("filter-name");
	public static final By REASONBOX_TITLE = By.cssSelector("h4.modal-title");
	public static final By REASONBOX_BODY = By.cssSelector("div.modal1-body>p");
	public static final By REASON_BTNOK = By.id("gpcokbtn");
	public static final By REASONBOX_BTNOK = By.xpath("//button[@id='gpcokbtn']");
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

}
