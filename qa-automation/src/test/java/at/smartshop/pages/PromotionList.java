package at.smartshop.pages;

import org.openqa.selenium.By;

public class PromotionList {
	public static final By BTN_CREATE = By.xpath("//button[text()='Create New']");
	public static final By PAGE_TITLE = By.xpath("//li[text()='Promotion List']");
	public static final By TXT_SEARCH_PROMONAME = By.id("search");
	public static final By BTN_SEARCH = By.id("searchbtn");	
	public static final By TBL_COLUMN_NAME = By.xpath("//td[@aria-describedby='hierarchicalGrid_name']");
}
