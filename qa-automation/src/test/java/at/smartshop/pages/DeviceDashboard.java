package at.smartshop.pages;

import org.openqa.selenium.By;

import at.framework.generic.CustomisedAssert;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;

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
	
	public By selectRecord(String data) {
		return By.xpath("//div[@id='device-grid_editor_list']//span[text()='"+ data + "']");
				
	}
	/**
	 * navigate to Admin->Device and search for existing device & new Device
	 * 
	 * @param tabName
	 */
	public void selectDeviceName(String menu,String device_name) {
	
	navigationBar.navigateToMenuItem(menu);
	CustomisedAssert.assertTrue(foundation.isDisplayed(DeviceDashboard.LBL_ADMIN_DEVICE_DASHBOARD));
	foundation.waitforElementToBeVisible(DeviceDashboard.TBL_DEVICE_NAME,3);
	foundation.click(DeviceDashboard.TXT_SEARCH);
	textBox.enterText(DeviceDashboard.TXT_SEARCH, device_name);
	foundation.click(DeviceDashboard.BTN_SEARCH);
	foundation.click(DeviceDashboard.TBL_DEVICE_NAME);
}
}
	
