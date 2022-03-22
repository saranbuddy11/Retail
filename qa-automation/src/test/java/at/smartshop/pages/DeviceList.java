package at.smartshop.pages;

import org.openqa.selenium.By;

public class DeviceList {

	public static final By COLUMN_SERIAL_NUMBER = By.xpath("//th[@id='device-grid_serialNumber']");
	public static final By LIST_SERIAL_NUMBER = By.xpath("//td[@aria-describedby='device-grid_serialNumber']");
	public static final By TXT_SEARCH_DEVICE = By.id("deviceFilterType");
	public static final By BTN_EXPORT_DEVICE = By.id("exportDevice");
	public static final By TXT_RECORD_COUNT = By.id("deviceDataGrid_table_pager");
	public static final By TBL_GRID = By.id("deviceDataGrid_table_headers");
	public static final By TBL_ROW = By.xpath("//*[@id='deviceDataGrid_table']/tbody");
	public static final By BTN_COMMISSION = By.id("commissionBtn");
	public static final By BTN_SEARCH = By.id("submitBtn");
	public static final By BTN_SUBMIT = By.id("submitBtn");
	public static final By DRP_PICO_DEVICE_TYPE = By.id("picodevicetype");
	public static final By TXT_DEVICE_LIST = By.id("Device List");
	public static final By BTN_CREATE_NEW = By.id("newBtn");
	public static final By SUPER_DEVICE=By.id("Super Device Dashboard");

	public By objDeveiceLink(String deviceName) {
		return By.xpath("//td[@aria-describedby='deviceDataGrid_table_namelink']//a[text()='" + deviceName + "']");
	}

	public By objLocationLink(String location) {
		return By.xpath("//td[@aria-describedby='deviceDataGrid_table_locationlink']//a[text()='" + location + "']");
	}
}
