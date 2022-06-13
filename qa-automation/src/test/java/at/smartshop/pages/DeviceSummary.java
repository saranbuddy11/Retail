package at.smartshop.pages;

import org.openqa.selenium.By;

import at.framework.browser.Factory;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.keys.Constants;

public class DeviceSummary extends Factory {
	
	private TextBox textBox = new TextBox();
	private LocationList locationList = new LocationList();
	private Foundation foundation = new Foundation();
	private LocationSummary locationSummary = new LocationSummary();
	private NavigationBar navigationBar = new NavigationBar();
	
	public static final By LBL_DEVICE_SUMMARY = By.xpath("//li[@id='Device Summary']");
	public static final By TXT_SCREEN_TIMEOUT = By.id("screentimeout");
	public static final By BTN_SAVE = By.id("saveBtn");
	public static final By LBL_TIMEOUT_WARNING = By.id("v5TimeoutWarning");
	public static final By DPD_SHOW_SEARCH_BTN = By.id("showprdlupdevice");
	public static final By LBL_SERIAL_NUMBER = By.id("serialnumber");
	public static final By DPD_COOLER_TYPE = By.id("coolertype");
	public static final By MM_RELOAD_METHOD=By.xpath("//dt[text()='MM Reload Method']");
	public static final By CHECKBOX_EGIFT_CARD=By.id("cbx.mmreloadmethod.gc");
	public static final By MM_RELOAD_DATAS=By.xpath("//*[@id=\"sfelist2\"]/div/div[2]/dd[1]");
	
	public void setTime(String locationName, String deviceName, String time, String menu) {
		
		 locationList.selectLocationName(locationName);
		 foundation.objectFocus(LocationSummary.BTN_DEPLOY_DEVICE);
		 textBox.enterText(LocationSummary.TXT_DEVICE_SEARCH, deviceName);
		 locationSummary.selectDeviceName(deviceName);
		 foundation.waitforElement(LBL_DEVICE_SUMMARY, Constants.SHORT_TIME);
		 getDriver().findElement(TXT_SCREEN_TIMEOUT).clear();
		 textBox.enterText(TXT_SCREEN_TIMEOUT, time);
		 foundation.click(BTN_SAVE);
		 navigationBar.navigateToMenuItem(menu);
		 locationList.selectLocationName(locationName);
		 foundation.waitforElement(LocationSummary.BTN_SAVE, Constants.SHORT_TIME);
		 foundation.click(LocationSummary.BTN_SYNC);
		 foundation.click(LocationSummary.BTN_SAVE);
		 foundation.waitforElement(LocationList.TXT_FILTER, Constants.SHORT_TIME);
	}

}


