package at.smartshop.pages;

import org.openqa.selenium.By;

public class DeviceList {

	public static final By COLUMN_SERIAL_NUMBER = By.xpath("//th[@id='device-grid_serialNumber']");
	public static final By LIST_SERIAL_NUMBER = By.xpath("//td[@aria-describedby='device-grid_serialNumber']");
	public static final By TXT_SEARCH_DEVICE = By.id("deviceNameTxt");
	public static final By BTN_EXPORT_DEVICE = By.id("exportDevice");
	public static final By TXT_RECORD_COUNT = By.id("deviceDataGrid_table_pager");
	public static final By TBL_GRID = By.id("deviceDataGrid_table_headers");
	public static final By TBL_ROW = By.xpath("//*[@id='deviceDataGrid_table']/tbody");
	public static final By BTN_COMMISSION = By.id("commissionBtn");
	public static final By BTN_SEARCH = By.id("submitBtn");

	public By objDeveiceLink(String deviceName) {
		return By.xpath("//td[@aria-describedby='device-grid_name']//a[text()='" + deviceName + "']");
	}

	public By objLocationLink(String location) {
		return By.xpath("//td[@aria-describedby='deviceDataGrid_table_locationlink']//a[text()='" + location + "']");
	}
}
