package at.smartshop.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import at.framework.browser.Factory;
import at.framework.generic.CustomisedAssert;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.keys.Constants;
import at.smartshop.tests.TestInfra;

public class ConsumerSummary extends Factory {
	private Foundation foundation = new Foundation();
	private Dropdown dropdown = new Dropdown();
	private TextBox textBox = new TextBox();
	private NavigationBar navigationBar = new NavigationBar();

//	private static final By  = By.xpath("//dt[text()='Consumer Account']//..//dd/span");
	public static final By LBL_READ_BALANCE = By.id("readbalance");
	private static final By LBL_READ_TYPE_BALANCE = By.id("readTypebalance");
	public static final By BTN_ADJUST = By.id("adjustBalanceBtn");
	public static final By TXT_ADJUST_BALANCE = By.id("balNum");
	public static final By DPD_REASON = By.id("reason");
	public static final By BTN_REASON_SAVE = By.id("reasonSaveBtn");
	public static final By BTN_REASON_CANCEL = By.id("reasoncancel");
	public static final By BTN_SAVE = By.id("saveBtn");
	public static final By TXT_SEARCH_ACCOUNT_ADJUSTMENT = By.xpath("//div[@id='aadt_filter']//input");
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
	public static final By LBL_POPUP_ADJUST_BALANCE = By.id("reasontitle");
	public static final By LBL_CONSUMER_SUMMARY = By.id("Consumer Summary");
	public static final By LBL_BALANCE_HISTORY = By.xpath("//h3[text()='Balance History']");
	public static final By BALANCE_HISTORY_GRID = By.id("balance-history_container");
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
	public static final By REF_EFT = By.id("oneft");
	public static final By ERROR_MESSAGE = By.xpath("//button[@class='ajs-button ajs-ok']");
	public static final By SUCCESS_MESSAGE = By.xpath("//div[text()='Success!']");
	public static final By TXT_SUBSIDY_GROUP = By.id("mkashow-pantry");
	public static final By DPD_SUBSIDY_GROUP_NAME = By.id("pantrygroup");
	public static final By TXT_TOP_OFF = By.xpath("//dt[text()='TOP_OFF']");
	public static final By TXT_CONSUMER_ACCOUNT = By.xpath("//dt[text()='Consumer Account']");
	public static final By TXT_SUBSIDY_TOP_OFF = By.xpath("//dt[text()='TOP_OFF']");
	public static final By TXT_SUBSIDY_ROLL_OVER = By.xpath("//dt[text()='ROLL_OVER']");
	public static final By BTN_TOP_OFF_ADJUST = By.id("adjustTopOffBtn");
	public static final By REASON_CODE = By.id("reason");
	public static final By TBL_LOCATION = By.id("balance-history");
	public static final By SUBSIDY_FIELD=By.xpath("/html/body/div[3]/div[2]/div/fieldset/div/dl/form/div/dt[2]");
	public static final By SUBSIDY_BALANCE = By.id("readTypebalance");
	public static final By CANCEL_BTN = By.id("cancelBtn");
	public static final By INPUT_PAY_ROLL_ID = By.id("payrollid");


	public double getBalance() {
		double initBalance = 0;
		try {
			String balance = foundation.getText(LBL_READ_BALANCE);
			initBalance = Double
					.parseDouble(balance.substring(1).replace(Constants.DELIMITER_COMMA, Constants.EMPTY_STRING));
			balance = balance.replaceAll("[\\(\\)\\$]", "");
//			initBalance = Double
//					.parseDouble(balance.substring(1).replace(Constants.DELIMITER_COMMA, Constants.EMPTY_STRING));
			initBalance = Double.parseDouble(balance);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		return initBalance;
	}

	public By objTaxCategory(String reasonCode) {
		return By.xpath("//table[@id='aadt']//*[text()='" + reasonCode + "']");
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

	public By objBalanceHistoryData(String data) {
		return By.xpath("(//tbody[@role='rowgroup']/tr)[1]/td[text()='" + data + "']");
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

	public void adjustBalance(String newBalance, String reasonCode) {
		foundation.click(BTN_ADJUST);
		textBox.enterText(TXT_ADJUST_BALANCE, newBalance);
		dropdown.selectItem(DPD_REASON, reasonCode, Constants.TEXT);
		foundation.click(BTN_REASON_SAVE);
		foundation.click(BTN_SAVE);
	}

	public void Incrementsubsidy(String menuitem, String location, String data, String reason) {
		navigationBar.navigateToMenuItem(menuitem);
		foundation.click(ConsumerSearch.CLEAR_SEARCH);
		dropdown.selectItem(DPD_LOCATION, location, Constants.TEXT);
		foundation.click(ConsumerSearch.BTN_GO);
		Assert.assertTrue(foundation.isDisplayed(ConsumerSearch.TBL_CONSUMERS));
		foundation.click(ConsumerSearch.LNK_FIRST_ROW);
		Assert.assertTrue(foundation.isDisplayed(ConsumerSummary.LBL_CONSUMER_SUMMARY));
		foundation.click(ConsumerSummary.BTN_ADJUST);
		foundation.waitforElement(ConsumerSummary.LBL_POPUP_ADJUST_BALANCE, Constants.SHORT_TIME);
		textBox.enterText(ConsumerSummary.TXT_ADJUST_BALANCE, data);
		dropdown.selectItem(ConsumerSummary.DPD_REASON, reason, Constants.TEXT);
		CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSummary.REF_EFT));
		foundation.click(ConsumerSummary.REF_EFT);

	}

	public void CancelButtonInSubsidyAdjustment(String data, String reason) {

		foundation.click(ConsumerSummary.BTN_ADJUST);
		foundation.waitforElement(ConsumerSummary.TXT_ADJUST_BALANCE, Constants.SHORT_TIME);
		textBox.enterText(ConsumerSummary.TXT_ADJUST_BALANCE, data);
		dropdown.selectItem(ConsumerSummary.DPD_REASON, reason, Constants.TEXT);
		CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSummary.BTN_REASON_CANCEL));
		foundation.click(ConsumerSummary.BTN_REASON_CANCEL);
	}

	public List<String> balanceHistoryHeaders() {
		List<String> tableHeaders = new ArrayList<>();
		try {
			WebElement tableProducts = getDriver().findElement(TBL_LOCATION);
			List<WebElement> columnHeaders = tableProducts
					.findElements(By.cssSelector("thead > tr > th > span.ui-iggrid-headertext"));
			for (WebElement columnHeader : columnHeaders) {
				tableHeaders.add(columnHeader.getText());
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		return tableHeaders;
	}

	public List<String> balanceHistoryDatas() {
		List<String> tableDatas = new ArrayList<>();
		try {
			WebElement details = getDriver().findElement(TBL_LOCATION);
			List<WebElement> rowDatas = details.findElements(By.xpath("(//tbody[@role='rowgroup']/tr)[1]/td"));
			for (WebElement rowData : rowDatas) {
				tableDatas.add(rowData.getText());
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		return tableDatas;
	}

	public void balanceHistory(List<String> expected) {
		CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSummary.LBL_BALANCE_HISTORY));
		CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSummary.BALANCE_HISTORY_GRID));
		List<String> tableHeaders = balanceHistoryHeaders();
		CustomisedAssert.assertTrue(tableHeaders.get(0).equals(expected.get(8)));
		CustomisedAssert.assertTrue(tableHeaders.get(1).equals(expected.get(9)));
		CustomisedAssert.assertTrue(tableHeaders.get(2).equals(expected.get(10)));
		CustomisedAssert.assertTrue(tableHeaders.get(3).equals(expected.get(11)));
		CustomisedAssert.assertTrue(tableHeaders.get(4).equals(expected.get(12)));
		CustomisedAssert.assertTrue(tableHeaders.get(5).equals(expected.get(13)));
	}

	public void balanceHistoryData(String value, String date) {
		List<String> data = balanceHistoryDatas();
		for (int i = data.size(); i < 0; i--) {
			CustomisedAssert.assertTrue(foundation.isDisplayed(objBalanceHistoryData(data.get(i))));
		}
		// CustomisedAssert.assertTrue(data.get(1).equals(value));
		// CustomisedAssert.assertTrue(data.get(4).contains(date));
	}

	public void balanceResettingData(String menu, String location, String balance, String reason) {
		navigationBar.navigateToMenuItem(menu);
		foundation.threadWait(Constants.SHORT_TIME);
		foundation.click(ConsumerSearch.CLEAR_SEARCH);
		dropdown.selectItem(ConsumerSearch.DPD_LOCATION, location, Constants.TEXT);
		foundation.click(ConsumerSearch.BTN_GO);
		CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSearch.TBL_CONSUMERS));
		foundation.click(ConsumerSearch.LNK_FIRST_ROW);
		foundation.click(ConsumerSummary.BTN_TOP_OFF_ADJUST);
		foundation.waitforElement(ConsumerSummary.LBL_POPUP_ADJUST_BALANCE, Constants.SHORT_TIME);
		textBox.enterText(ConsumerSummary.TXT_ADJUST_BALANCE, balance);
		dropdown.selectItem(ConsumerSummary.DPD_REASON, reason, Constants.TEXT);
		CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSummary.REF_EFT));
		foundation.click(ConsumerSummary.BTN_REASON_SAVE);
		foundation.click(ConsumerSummary.BTN_SAVE);
	}

	public void balanceResettingDataInAutomationLocation1(String menu, String Search, String location, String balance,
			String reason) {
		navigationBar.navigateToMenuItem(menu);
		CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSearch.TXT_CONSUMER_SEARCH));
		foundation.click(ConsumerSearch.CLEAR_SEARCH);
		foundation.threadWait(Constants.SHORT_TIME);
		textBox.enterText(ConsumerSearch.TXT_SEARCH, Search);
		dropdown.selectItem(ConsumerSearch.DPD_LOCATION, location, Constants.TEXT);
		foundation.click(ConsumerSearch.BTN_GO);
		CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSearch.TBL_CONSUMERS));
		foundation.click(ConsumerSearch.LNK_FIRST_ROW);
		foundation.click(ConsumerSummary.BTN_TOP_OFF_ADJUST);
		foundation.waitforElement(ConsumerSummary.LBL_POPUP_ADJUST_BALANCE, Constants.SHORT_TIME);
		textBox.enterText(ConsumerSummary.TXT_ADJUST_BALANCE, balance);
		dropdown.selectItem(ConsumerSummary.DPD_REASON, reason, Constants.TEXT);
		CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSummary.REF_EFT));
		foundation.click(ConsumerSummary.BTN_REASON_SAVE);
		foundation.threadWait(Constants.SHORT_TIME);
		foundation.click(ConsumerSummary.BTN_SAVE);
	}
}
