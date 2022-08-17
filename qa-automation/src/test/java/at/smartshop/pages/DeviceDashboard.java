package at.smartshop.pages;

import org.openqa.selenium.By;

public class DeviceDashboard {
	public static final By BTN_LIVE_CONNECTION_STATUS= By.id("liveConnectStat");
	public static final By BTN_REMOVE_DEVICE= By.xpath("//button[@class='btn btn-danger']");
	public static final By BTN_YES_REMOVE= By.xpath("//button[@class='ajs-button ajs-ok']");
	public static final By LBL_ADMIN_DEVICE_DASHBOARD=By.id("Admin Device Dashboard");
	public static final By TBL_DEVICE_HEADER = By.cssSelector("#device-grid > tbody");
	
	public By selectRecord(String data) {
		return By.xpath("//div[@id='device-grid_editor_list']//span[text()='"+ data + "']");
				
	}
}
