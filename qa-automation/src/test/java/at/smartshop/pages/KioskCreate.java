package at.smartshop.pages;

import org.openqa.selenium.By;

public class KioskCreate {
	public static final By BTN_CREATE = By.xpath("//button[text()='Create New']");
	public static final By BTN_SAVE = By.xpath("//button[text()='Save']");
	public static final By TXT_NAME = By.xpath("//input[@name='name']");
	public static final By DPD_ORG = By.id("org");
	public static final By DPD_PROCESSOR = By.id("processor1");
	public static final By TXT_TERMINAL_ID = By.id("aprivaterminalid");
	public static final By TXT_DEVICE_LIST = By.id("Device List");
	public static final By TXT_IP_ADDRESS = By.id("ipaddress");
	public static final By TITLE_KIOSK_CREATE = By.xpath("//li[text()='Kiosk Create']");
	public static final By DPD_HARDWARE_TYPE = By.id("hwtype");
	public static final By LBL_KIOSK_CREATE = By.xpath("//div[@class='container-fluid']/div/div/ul/li");
	public static final By BTN_CANCEL= By.xpath("//button[text()='Cancel']");
	public static final By DPD_TYPE= By.xpath("//span[@id='select2-kiosktype-container']");
	public static final By DPD_OPTION_TYPE= By.xpath("//ul[@id='select2-kiosktype-results']//li");
}
