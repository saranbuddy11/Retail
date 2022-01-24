package at.smartshop.pages;

import org.openqa.selenium.By;

import at.framework.browser.Factory;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.smartshop.keys.Constants;
import at.smartshop.tests.TestInfra;

public class ConsumerSummary extends Factory {
	private Foundation foundation = new Foundation();
	private Dropdown dropdown = new Dropdown();

//	private static final By  = By.xpath("//dt[text()='Consumer Account']//..//dd/span");
	private static final By LBL_READ_BALANCE = By.id("readbalance");
	private static final By LBL_READ_TYPE_BALANCE = By.id("readTypebalance");
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
	public static final By ERROR_FIRSTNAME = By.id("firstname-error");
	public static final By ERROR_LASTNAME = By.id("lastname-error");
	public static final By BTN_MOVE = By.id("moveBtn");
	public static final By DPD_MOVE_ORG = By.id("moveform-org");
	public static final By DPD_MOVE_LOCATION = By.id("moveform-loc");
	public static final By BTN_MODEL_MOVE_SAVE = By.id("modalsave");
	public static final By LBL_LOCATION_SELECTED = By.id("locname");
	public static final By SPINNER = By.id("//span[contains(@id,'container_loading')]");
	public static final By TXT_SUBSIDY_GROUP = By.id("mkashow-pantry");
	public static final By DPD_SUBSIDY_GROUP_NAME = By.id("pantrygroup");
	public static final By TXT_TOP_OFF = By.xpath("//dt[text()='TOP_OFF']");
	public static final By TXT_CONSUMER_ACCOUNT = By.xpath("//dt[text()='Consumer Account']");
	public static final By TXT_SUBSIDY_TOP_OFF = By.xpath("//dt[text()='TOP_OFF']");
	public static final By TXT_SUBSIDY_ROLL_OVER = By.xpath("//dt[text()='ROLL_OVER']");

	public double getBalance() {
		double initBalance = 0;
		try {
			String balance = foundation.getText(LBL_READ_BALANCE);
			balance = balance.replaceAll("[\\(\\)\\$]", "");
//			initBalance = Double
//					.parseDouble(balance.substring(1).replace(Constants.DELIMITER_COMMA, Constants.EMPTY_STRING));
			initBalance = Double.parseDouble(balance);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		return initBalance;
	}

	public double getTypeBalance() {
		double initTypeBalance = 0;
		try {
			String typeBalance = foundation.getText(LBL_READ_TYPE_BALANCE);
			typeBalance = typeBalance.replaceAll("[\\(\\)\\$]", "");
			initTypeBalance = Double.parseDouble(typeBalance);
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
		return initTypeBalance;
	}

	public By objTaxCategory(String reasonCode) {
		return By.xpath("//table[@id='aadt']//*[text()='" + reasonCode + "']");
	}

	public boolean moveConsumer(String toOrg, String toLocation) {
		foundation.click(BTN_MOVE);
		foundation.threadWait(Constants.THREE_SECOND);
		dropdown.selectItem(DPD_MOVE_ORG, toOrg, Constants.TEXT);
		foundation.threadWait(Constants.ONE_SECOND);
		dropdown.selectItem(DPD_MOVE_LOCATION, toLocation, Constants.TEXT);
		foundation.click(BTN_MODEL_MOVE_SAVE);
		foundation.alertAccept();
		foundation.waitforElementToDisappear(DPD_MOVE_ORG, Constants.SHORT_TIME);
		return foundation.getAttributeValue(LBL_LOCATION_SELECTED).equals(toLocation);
	}

}
