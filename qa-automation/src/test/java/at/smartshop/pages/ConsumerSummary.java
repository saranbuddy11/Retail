package at.smartshop.pages;

import org.openqa.selenium.By;
import org.testng.Assert;

import at.framework.ui.Foundation;
import at.smartshop.keys.Constants;

public class ConsumerSummary {
	private Foundation foundation = new Foundation();

//	private static final By  = By.xpath("//dt[text()='Consumer Account']//..//dd/span");
	private static final By LBL_READ_BALANCE = By.id("readbalance");	
	public static final By BTN_ADJUST = By.id("adjustBalanceBtn");
	public static final By TXT_ADJUST_BALANCE = By.id("balNum");
	public static final By DPD_REASON = By.id("reason");
	public static final By BTN_REASON_SAVE = By.id("reasonSaveBtn");
	public static final By BTN_REASON_CANCEL = By.id("reasoncancel");
	public static final By BTN_SAVE = By.id("saveBtn");
	public static final By DPD_LOCATION = By.cssSelector("select#loc-dropdown");
	public static final By TXT_FIRSTNAME = By.cssSelector("input#firstname");
	public static final By TXT_LASTNAME = By.cssSelector("input#lastname");
	public static final By TXT_EMAIL = By.cssSelector("input#email");
	public static final By TXT_SCANID = By.cssSelector("input#scanid");
	public static final By TXT_PIN = By.cssSelector("input#pin");
	public static final By TXT_PHONE = By.cssSelector("input#phone");
	public static final By BTN_CREATE = By.xpath("//button[text()='Create | Invite']");
	public static final By TXT_SPINNER_MSG = By.xpath("//div[@class='humane ']");
	public static final By TXT_SCANID_ERROR = By.xpath("//label[@id='scanid-error']");
	public static final By TXT_EMAILID_ERROR = By.xpath("//label[@id='email-error']");
	public static final By TXT_PIN_ERROR = By.xpath("//label[@id='pin-error']");
	public static final By TXT_LOC_ERROR = By.xpath("//label[@id='loc-dropdown-error']");
	public static final By TXT_FN_ERROR = By.xpath("//label[@id='firstname-error']");
	public static final By TXT_LN_ERROR = By.xpath("//label[@id='lastname-error']");
	public static final By TXT_SEARCH_ACCOUNT_ADJUSTMENT = By.xpath("//div[@id='aadt_filter']//input");
	public static final By LBL_POPUP_ADJUST_BALANCE = By.id("reasontitle");
	public static final By LBL_CONSUMER_SUMMARY = By.id("Consumer Summary");
	public static final By DPD_PAY_CYCLE = By.id("paycycle");
	public static final By BTN_PAYOUT_CLOSE = By.id("payoutCloseBtn");

	public double getBalance() {
		double initBalance = 0;
		try {
			String balance = foundation.getText(LBL_READ_BALANCE);
			balance = balance.replaceAll("[\\(\\)\\$]", "");
//			initBalance = Double
//					.parseDouble(balance.substring(1).replace(Constants.DELIMITER_COMMA, Constants.EMPTY_STRING));
			initBalance = Double.parseDouble(balance);
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
		return initBalance;
	}
	
	public By objTaxCategory(String reasonCode) {
		return By.xpath("//table[@id='aadt']//*[text()='" + reasonCode + "']");
	}

}
