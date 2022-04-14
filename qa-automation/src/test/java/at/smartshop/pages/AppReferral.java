package at.smartshop.pages;

import org.openqa.selenium.By;
import org.testng.Assert;
import java.util.List;

import at.framework.ui.Dropdown;
import at.framework.ui.TextBox;
import at.smartshop.keys.Constants;

public class AppReferral {
	
	public static final By LBL_APP_REFERRAL = By.xpath("//div[@class='createnewbtnmain']//span");
	public static final By LBL_CREATE_APP_REFERRAL = By.id("Create App Referral");
	public static final By BTN_CREATE_NEW = By.id("createNewBtn");
	public static final By DRP_ORG = By.id("org");
	public static final By DRP_LOCATION = By.id("locs");
	public static final By TXTBX_REFERRAL_AMOUNT = By.id("referralamount");
	public static final By DRP_DISABLE = By.id("autodisable");
	public static final By BTN_SAVE = By.id("saveBtn");
	public static final By BTN_CANCEL = By.id("cancelBtn");
	public static final By LBL_LOCATION_ERROR = By.id("locs-error");
	public static final By LBL_REFERRAL_AMOUNT_ERROR = By.id("referralamount-error");
	public static final By TXTBX_SEARCH = By.id("filterType");
	public static final By SELECT_GRID = By.xpath("//td[@aria-describedby='appReferralTable_location']//a");
	public static final By BTN_END_REFERRAL = By.id("endReferralBtn");
	public static final By BTN_ACCEPT_POPUP = By.xpath("//button[@id='referraldetails-ok']");
	public static final By BTN_EXPIRE_REFERRAL = By.xpath("//button[@class='ajs-button ajs-ok']");
	public static final By BTN_COPY_REFERRAL = By.id("copyreferral"); 
	public static final By LBL_REFERRAL_ERROR = By.xpath("//div[@id='icon-header']//div[@id='referralerror-h3']");
	public static final By LBL_EXISTING_LOC = By.xpath("//h4[@class='text-center p-2']");
	public static final By BTN_ERROR_CANCEL = By.id("referralerrorcancel");
	
	private TextBox textBox = new TextBox();
	private Dropdown dropDown = new Dropdown();
	
	public void createAppReferral(List<String> textbox_data) {
		try {
			dropDown.selectItem(AppReferral.DRP_LOCATION, textbox_data.get(0),Constants.TEXT);
			textBox.enterText(AppReferral.TXTBX_REFERRAL_AMOUNT, textbox_data.get(2));
			dropDown.selectItem(AppReferral.DRP_DISABLE, textbox_data.get(3),Constants.TEXT);			
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
	
}
