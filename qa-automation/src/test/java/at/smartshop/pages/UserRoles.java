package at.smartshop.pages;

import org.openqa.selenium.By;

import at.framework.ui.Foundation;

public class UserRoles {
	public static final By BTN_CREATE_NEW_ROLE = By.id("newBtn");
	public static final By LBL_VIEW_ROLE = By.id("pagetitle");
	public static final By TAB_ADMIN = By.xpath("//*[@id='nav-tabs']/li/a[text()='Admin']");  
	public static final By TXT_SEARCH_FILTER = By.cssSelector("#dt_filter > label > input");

	private Foundation foundation = new Foundation();

	public void selectRowByText(String text) {
		By lblRow = By.xpath("//td[text()='"+text+"']");
		foundation.click(lblRow);	
	}
	
}
