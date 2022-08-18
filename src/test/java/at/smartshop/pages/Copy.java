package at.smartshop.pages;

import org.openqa.selenium.By;

public class Copy {

	public static final By LBL_COPY_PRODUCT = By.id("Copy Product from Location to Location");
	public static final By DRP_FROM = By.xpath("//select[@name='src']");
	public static final By DRP_TO = By.id("dst");
	public static final By BTN_SAVE = By.id("saveBtn");
	public static final By BTN_CANCEL = By.id("cancelBtn");
	public static final By BTN_CHECKALL = By.id("checkallBtn");
	public static final By BTN_UNCHECKALL = By.id("uncheckallBtn");
	public static final By LBL_COPY_CATEGORY = By.id("Copy Category from Location to Location");
	public static final By LBL_COPY_HOME_COMMERCIAL = By.id("Copy Home Commercial from Location to Location");
	public static final By LBL_COPY_CART_COMMERCIAL = By.id("Copy Cart Commerical from Location to Location ");
	public static final By TXT_SPINNER_MSG = By.xpath("//div[@class='humane humane-libnotify-success']");
	public static final By SELECT_PRODUCT = By.xpath("//td[@class=' sorting_1']");
	public static final By SELECT_PRODUCTS = By.xpath("//tbody[@aria-live='polite']");
}
