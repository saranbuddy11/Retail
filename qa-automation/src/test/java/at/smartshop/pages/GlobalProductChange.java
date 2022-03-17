package at.smartshop.pages;

import org.openqa.selenium.By;

import at.framework.browser.Factory;

public class GlobalProductChange extends Factory{
	public static final By TXT_LOCATION_SEARCH = By.id("loc-search");
	public static final By TBL_LOCATION_LIST = By.id("location-list");
	public static final By BTN_LOCATION_APPLY = By.id("loc-filter-apply");
	public static final By BTN_PRODUCT_APPLY = By.id("prd-filter-apply");
	public static final By TAB_PRODUCT = By.cssSelector("#li2 > a");
	public static final By BTN_NEXT = By.id("prd-dt-next");
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
    public static final By RDO_OPERATOR_PRODUCT_CHANGE= By.xpath("//label[text()='Operator Product Catalog Change']");    
    public static final By TXT_LOCATION_PRODUCT = By.id("filter-name");
    public static final By REASON_BTNOK =By.id("gpcokbtn");
    
	public By objTableRow(String location) {
			return By.xpath("//table[@id='filtered-prd-dt']//tbody//span[text()='"+ location +"']");
	}
	
	public By objLocation(String location) {
		return By.xpath("//ul[@id='location-list']//li[text()='"+ location +"']");
}
	
	public By objProductName(String productName) {
		return By.xpath("//span[text()='"+productName+"']");
	}
	
}
