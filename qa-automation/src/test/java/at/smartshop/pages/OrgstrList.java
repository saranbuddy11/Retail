package at.smartshop.pages;

import org.openqa.selenium.By;

import at.framework.browser.Factory;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.keys.Constants;

public class OrgstrList extends Factory {
	
	private Foundation foundation = new Foundation();
	private TextBox textBox = new TextBox();
	
	public static final By BTN_CREATE = By.xpath("//button[text()='Create New']");
	public static final By ORGSTR_ORG = By.id("orglist");
	public static final By CAT_NAME = By.xpath("//input[@id='type']");
	public static final By TXT_NAME = By.xpath("//input[@id='name']");
	public static final By KEY_NAME = By.xpath("//input[@id='keystr']");
	public static final By BTN_SAVE = By.xpath("//button[text()='Save']");
	public static final By BTN_CANCEL = By.xpath("//button[@id='cancelBtn']");
	public static final By ERROR_STATUS = By.xpath("//label[@id='orglist-error']");
	public static final By TYPE_ERROR_STATUS = By.xpath("//label[@id='type-error']");
	public static final By NAME_ERROR_STATUS = By.xpath("//label[@id='name-error']");
	public static final By KEYSTR_ERROR_STATUS = By.xpath("//label[@id='keystr-error']");
	public static final By ORG_LIST = By.xpath("//div[@id='Orgstr List']");
	public static final By ORG_DEVICE_SEARCH = By.xpath("//input[@aria-controls='dt']");
	public static final By TBL_DATA = By.cssSelector("tbody[aria-relevant=all] span");
	public static final By BTN_REMOVE = By.id("rmBtn");
	
	public void acceptPopup(String device) {
		foundation.waitforElement(OrgstrList.ORG_LIST, Constants.SHORT_TIME);
		textBox.enterText(OrgstrList.ORG_DEVICE_SEARCH, device);
		foundation.click(OrgstrList.TBL_DATA);
		foundation.waitforElement(OrgstrList.BTN_REMOVE, Constants.SHORT_TIME);
		foundation.click(OrgstrList.BTN_REMOVE);
		foundation.alertAccept();
		
	}
}
