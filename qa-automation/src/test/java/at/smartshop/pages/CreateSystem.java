package at.smartshop.pages;

import org.openqa.selenium.By;

import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.keys.Constants;

public class CreateSystem {

	private Dropdown dropDown = new Dropdown();
	private TextBox textBox = new TextBox();
	private Foundation foundation = new Foundation();

	public static final By LBL_CREATE_SYSTEM = By.id("create-form");
	public static final By DPD_LOCATION = By.id("locs");
	public static final By TXT_SYSTEM_NAME = By.id("systemName");
	public static final By TXT_DISPLAY_NAME = By.id("displayName");
	public static final By DPD_LOCKER_MODEL = By.id("tester");
	public static final By DPD_EDIT_LOCKER_MODEL = By.id("tester1");
	public static final By BTN_SAVE = By.id("saveBtn");
	public static final By BTN_CANCEL = By.id("cancelbtn");
	public static final By BTN_CREATE_SYSTEM = By.id("systemcreate");
	public static final By LBL_PAGE_TITLE = By.xpath("//div[text()='Create a System']");
	public static final By DPD_LOCATION_SIBLING = By
			.xpath("//select[@id='locs']//..//..//..//following-sibling::input[@id='systemName']");
	public static final By LBL_SYSTEM_ERROR = By.id("systemName-error");
	public static final By LBL_DISPLAY_ERROR = By.id("displayName-error");
	public static final By MSG_UNIQUE_SYSTEM_NAME = By
			.xpath("//div[contains(text(),'Data for Locker System exists:')]");
	public static final By TXT_TIMER = By.id("timer");

	public void createNewSystem(String locationName, String systemName, String displayName, String lockerModel) {
		dropDown.selectItem(DPD_LOCATION, locationName, Constants.TEXT);
		textBox.enterText(TXT_SYSTEM_NAME, systemName);
		textBox.enterText(TXT_DISPLAY_NAME, displayName);
		dropDown.selectItem(DPD_LOCKER_MODEL, lockerModel, Constants.TEXT);
		foundation.click(TXT_TIMER);
		foundation.click(BTN_SAVE);
		foundation.threadWait(Constants.THREE_SECOND);
	}

}
