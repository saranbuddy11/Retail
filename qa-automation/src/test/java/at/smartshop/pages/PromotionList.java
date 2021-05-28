package at.smartshop.pages;

import org.openqa.selenium.By;

public class PromotionList {
	public static final By BTN_CREATE = By.xpath("//button[text()='Create New']");
	public static final By PAGE_TITLE = By.xpath("//li[text()='Promotion List']");
	public static final By TXT_SEARCH = By.id("search");
	
	public static final By BTN_SEARCH = By.id("searchbtn");
	public static final By LBL_START_DATE_HEADER = By.id("hierarchicalGrid_datestart");
	public static final By LBL_START_DATE_LIST = By.xpath("//td[@aria-describedby='hierarchicalGrid_datestart']");
	public static final By BTN_OK = By.xpath("//button[text()='OK']");
	public static final By MULTI_SELECT_TENDER_TYPES = By.id("tendertypes");
	public static final By TBL_COLUMN_NAME = By.xpath("//td[@aria-describedby='hierarchicalGrid_name']");
	
	public static final By CALENDER_DATE_RANGE = By.id("daterange");
	public static final By DPD_LOCATION = By.id("loc-dropdown");
	public static final By DPD_STATUS = By.id("status");
	public static final By DPD_PROMOTYPE = By.id("promotype");	
	public static final By LBL_SEARCH = By.xpath("//input[@id='search']//..//..//dt");
	public static final By LBL_CALENDER_DATE_RANGE = By.xpath("//input[@id='startdate']//..//..//dt");
	public static final By LBL_LOCATION  = By.xpath("//select[@id='loc-dropdown']//..//..//dt");
	public static final By LBL_STATUS = By.xpath("//select[@id='status']//..//..//dt");
	public static final By LBL_PROMOTYPE = By.xpath("//select[@id='promotype']//..//..//dt");
}
