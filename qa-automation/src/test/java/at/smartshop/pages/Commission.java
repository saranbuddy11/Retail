package at.smartshop.pages;

import org.openqa.selenium.By;

public class Commission {
	public static final By LBL_COMMISSION= By.id("Commission");
	public static final By BTN_CREATE_NEW= By.id("newBtn");
	public static final By BTN_CANCEL= By.id("cancelBtn");
	public static final By TXT_SEARCH= By.xpath("//*[@id='dt_filter']//input");
	public static final By TXT_SELECT= By.xpath("//*[@id='dt_length']//select");
	public static final By LBL_RECORD_COUNT= By.id("dt_info");
	public static final By BTN_PREVIOUS= By.id("prev");
	public static final By BTN_NEXT= By.id("next");
	public static final By TBL_ROW= By.xpath("//*[@aria-describedby='dt_info']/tbody/tr");
	public static final By TBL_COMMISSION= By.id("dt");
	public static final By TBL_NAME_COLUMN= By.id("Name");
	public static final By TBL_IP_ADDRESS_COLUMN= By.id("IP Address");
}
