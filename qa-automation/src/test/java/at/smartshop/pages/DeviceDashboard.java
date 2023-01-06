package at.smartshop.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import at.framework.generic.CustomisedAssert;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.keys.Constants;

public class DeviceDashboard {
	
	public Login login = new Login();
	private TextBox textBox = new TextBox();
	private Foundation foundation = new Foundation();
	private NavigationBar navigationBar = new NavigationBar();

	public static final By BTN_LIVE_CONNECTION_STATUS= By.id("liveConnectStat");
	public static final By BTN_REMOVE_DEVICE= By.xpath("//button[@class='btn btn-danger']");
	public static final By BTN_YES_REMOVE= By.xpath("//button[@class='ajs-button ajs-ok']");
	public static final By LBL_ADMIN_DEVICE_DASHBOARD=By.id("Admin Device Dashboard");
	public static final By TBL_DEVICE_HEADER = By.cssSelector("#device-grid > tbody");
	public static final By TXT_SEARCH=By.id("deviceNameTxt");
	public static final By BTN_SEARCH=By.id("submitBtn");
	public static final By TBL_DEVICE_NAME = By.xpath("//td[@aria-describedby='device-grid_name']/a");
	public static final By TABLE_HEADER=By.id("device-grid_headers");
	public static final By BTN_CREATENEW=By.id("newBtn");
	
	public By selectRecord(String data) {
		return By.xpath("//div[@id='device-grid_editor_list']//span[text()='"+ data + "']");
				
	}
	
	

	
	/**
	 * navigate to Admin->Device and search for existing device & new Device
	 * 
	 * @param tabName
	 */
	public void selectDeviceName(String device_name) {
	CustomisedAssert.assertTrue(foundation.isDisplayed(DeviceDashboard.LBL_ADMIN_DEVICE_DASHBOARD));
	foundation.waitforElementToBeVisible(DeviceDashboard.TBL_DEVICE_NAME,Constants.SHORT_TIME);
	foundation.click(DeviceDashboard.TXT_SEARCH);
	foundation.threadWait(Constants.THREE_SECOND);
	textBox.enterText(DeviceDashboard.TXT_SEARCH, device_name);
	foundation.threadWait(Constants.THREE_SECOND);
	foundation.click(DeviceDashboard.BTN_SEARCH);
	foundation.click(DeviceDashboard.TBL_DEVICE_NAME);
}
	
	
}
	
