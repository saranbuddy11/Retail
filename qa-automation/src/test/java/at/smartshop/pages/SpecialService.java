package at.smartshop.pages;

import org.openqa.selenium.By;

public class SpecialService {
	public static final By TITL_SPL_SERVICE_LIST = By.id("Special Service List");
	public static final By BTN_CREATE_NEW = By.id("newBtn");
	public static final By TXT_SEARCH = By.xpath("//div[@id='dt_filter']//input");
	public static final By TITL_SPL_SERVICE_SUMMARY = By.xpath("//li[contains(@id,'Device Summary')]");
	public static final By TXT_SPINNER_MSG = By.xpath("//div[@class='humane humane-libnotify-info']");
	public static final By LBL_ROW_DATA = By.xpath("//tbody//tr//td");
	public static final By LBL_NO_RESULT = By.xpath("//td[text()='No matching records found']");
	public static final By LST_COLUMN_HEADERS = By.xpath("//thead//th");
	
	public By objSplServiceName(String name) {
		return By.xpath("//tbody[@role='alert']//span[text()= '" + name + "'");
	}
	
	public By listColumns(int columnNumber) {
		return By.xpath("//tr//td["+columnNumber+"]");
	}
	
	
}
