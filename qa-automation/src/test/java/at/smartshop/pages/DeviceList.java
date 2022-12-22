package at.smartshop.pages;

import org.openqa.selenium.By;

public class DeviceList {

	public static final By COLUMN_SERIAL_NUMBER = By.xpath("//th[@id='device-grid_serialNumber']");
	public static final By LIST_SERIAL_NUMBER = By.xpath("//td[@aria-describedby='device-grid_serialNumber']");
	public static final By TXT_SEARCH_DEVICE = By.id("deviceNameTxt");
	public static final By BTN_EXPORT_DEVICE = By.id("exportDevice");
	public static final By TXT_RECORD_COUNT = By.id("device-grid_pager");
	public static final By TBL_GRID = By.id("device-grid_headers");
	public static final By TBL_ROW = By.xpath("//*[@id='device-grid_scroll']//tbody");
	public static final By BTN_COMMISSION = By.id("commissionBtn");
	public static final By BTN_SEARCH = By.cssSelector("#submitBtn.btn.btn-primary");
	public static final By BTN_SUBMIT = By.id("submitBtn");
	public static final By DRP_PICO_DEVICE_TYPE = By.id("picodevicetype");
	public static final By TXT_DEVICE_LIST = By.id("Device List");
	public static final By BTN_CREATE_NEW = By.id("newBtn");
	public static final By SUPER_DEVICE = By.id("Super Device Dashboard");
	public static final By TXT_RECORDS_DATA = By.id("device-grid_pager_label");
	public static final By TXT_TABLE_RECORD = By.xpath("//td[@aria-describedby='device-grid_name']//a");
	public static final By LOCATION_LINK = By.xpath("//td[@aria-describedby='device-grid_locationName']/a");
	public static final By HEADER_DEVICE_NAME = By.id("device-grid_name");
	public static final By TXT_SEARCH = By.id("deviceFilterType");
	public static final By TBL_DEVICE_NAME = By.xpath("//td[@aria-describedby='deviceDataGrid_table_namelink']");

	public By objDeveiceLink(String deviceName) {
		return By.xpath("//td[@aria-describedby='device-grid_name']//a[text()='" + deviceName + "']");
	}

	public By DeveiceLink(String deviceName) {
		return By.xpath("//td[@aria-describedby='device-grid_name']//a[contains(text(),'" + deviceName + "')]");
	}

	public By objLocationLink(String location) {
		return By.xpath("//td[@aria-describedby='deviceDataGrid_table_locationlink']//a[text()='" + location + "']");
	}

	public By deveiceLink(String deviceName) {
		return By.xpath("//td[@aria-describedby='device-grid_name']//a[text()='" + deviceName + "']");
	}
}
