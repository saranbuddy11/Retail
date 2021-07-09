package at.smartshop.pages;

import org.openqa.selenium.By;
import at.framework.browser.Factory;
import at.framework.ui.TextBox;

public class DeviceSummary extends Factory{
	
	private TextBox textBox = new TextBox();
	
	public static final By LBL_DEVICE_SUMMARY = By.xpath("//li[@id='Device Summary']");
	public static final By TXT_SCREEN_TIMEOUT = By.id("screentimeout");
	public static final By BTN_SAVE = By.id("saveBtn");
	public static final By LBL_TIMEOUT_WARNING = By.id("v5TimeoutWarning");

	
	public void setTimeOut(String time) {
		getDriver().findElement(TXT_SCREEN_TIMEOUT).clear();
		textBox.enterText(TXT_SCREEN_TIMEOUT, time);		
	}

}
