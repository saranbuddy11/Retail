package at.smartshop.pages;

import org.openqa.selenium.By;

public class DeviceList {

	public static final By COLUMN_SERIAL_NUMBER = By.xpath("//th[@id='deviceDataGrid_table_serialnumber']");
	public static final By LIST_SERIAL_NUMBER = By.xpath("//td[@aria-describedby='deviceDataGrid_table_serialnumber']");
	public static final By TXT_SEARCH_DEVICE = By.id("deviceFilterType");
}
