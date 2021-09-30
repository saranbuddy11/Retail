package at.smartshop.pages;

import org.openqa.selenium.By;

public class SelfService {
	public static final By DPD_LOCATION = By.xpath("//select[@id='createurl']");
	public static final By TXT_MENU_NAME = By.cssSelector("#menu-name");
	public static final By BTN_SAVE = By.xpath("//button[text()='Save']");
	public static final By BTN_CREATE_NEW = By.xpath("//button[text()='Create New']");
	public static final By BTN_ADD_ITEM = By.xpath("//div[text()=' Add item button']");
	public static final By BTN_ADD = By.xpath("//div[@id='menuprd-add']");
	public static final By BTN_DELETE = By.xpath("//button[text()='Delete']");
	public static final By TXT_SPINNER_MSG = By.className("humane");
	public static final By TXT_SEARCH_PRODUCT=  By.xpath("//div[@id='menu-container']//input[@id='menuprd-search']");
	public static final By LBL_PRODUCT_NAME=  By.xpath("//div[@id='menu-container']//td[@id='menuprd-name_0']");
	public static final By LBL_BTN_ADD = By.xpath("//div[@id='menu-container']//div[@id='menuprd-add']");
	public static final By BTN_SUBMENU_ADD = By.cssSelector("div#submenu-add");
	public static final By LBL_FORWARD_ARROW = By.xpath("//div//i[contains(@class,'sub_toggle_icon')]");
	public static final By LBL_NO_PRINT = By.xpath("//span[@class='product-noprintgroup']");
	public static final By LBL_INHERIT_PRINT = By.xpath("//span[@class='product-inheritingprintgroup']");
	public static final By LBL_HAS_PRINT = By.xpath("//span[@class='product-hasprintgroup']");
	public static final By LBL_SELECT_ALL = By.xpath("//a[text()='Select All']");
	public static final By FILTER_MENU = By.cssSelector("#dt_filter > label > input[type=text]");
	public static final By RDO_BTN_SHOW = By.xpath("//input[@value='show']");
	public static final By RDO_BTN_Hide = By.xpath("//input[@value='hide']");
	public static final By LBL_ALL = By.xpath("//span[text()='All']");
	public static final By BTN_CANCEL = By.xpath("//button[text()='Cancel']");

	public By objPrintCheckbox(String text) {
	return By.xpath("//b[text()='"+text+"']//..//..//input");
	}
}
