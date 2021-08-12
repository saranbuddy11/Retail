package at.smartshop.pages;

import org.openqa.selenium.By;

public class PrintGroupLists {
	
	public static final By DPD_LOCATION = By.xpath("//select[@id='createurl']");
	public static final By BTN_CREATENEW = By.xpath("//button[text()='Create New']");
	public static final By TXT_NAME = By.cssSelector("input#name");
	public static final By BTN_SAVE = By.xpath("//button[text()='Save']");
	public static final By TXT_SPINNER_MSG = By.className("humane");
	

}
