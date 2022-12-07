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
//	public static final By BTN_ADJUST = By.id("adjustBalanceBtn");
	public static final By BTN_ADJUST = By.cssSelector("#mainform > div > dd:nth-child(3) > a");
	public static final By PAYROLL_BTN_ADJUST = By.cssSelector("#mainform > div > dd:nth-child(5) > a");
	//public static final By BTN_ADJUST2=By.xpath("/html/body/div[3]/div[2]/div/fieldset/div/dl/form/div/dd[1]/a");
	public static final By BTN_SUBSIDY_ADJUST = By.xpath("(//a[@class='adjustBalanceBtn'])[2]");
	public static final By SUBSIDY_ADJUST=By.cssSelector("#mainform > div > dd:nth-child(7) > a");
	public static final By STRIKE_ADJUST = By.xpath("//a[@balanceid='3b1cb67f9533314380072e373ca2ba02']");
	public static final By PAYROLL_DEDUCT=By.xpath("//a[@balanceid='c9dd6add3acb48ea84e1c0bc1e189db4']");
	public static final By TXT_ADJUST_BALANCE = By.id("balNum");
	public static final By DPD_REASON = By.id("reason");
	public static final By BTN_REASON_SAVE = By.id("reasonSaveBtn");
	public static final By BTN_REASON_CANCEL = By.id("reasoncancel");
	public static final By PENDING_EMAIL=By.id("uvemail");
	public static final By PDE_BALANCE_READ=By.xpath("(//span[@id='readbalance'])[2]");
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
	public static final By TXT_SUBSIDY_TOP_OFF = By.xpath("//dt[text()='Subsidy - Top Off']");
	public static final By TXT_SUBSIDY_ROLL_OVER = By.xpath("//dt[text()='Subsidy - Rollover']");
	public static final By BTN_TOP_OFF_ADJUST = By.className("adjustBalanceBtn");
	public static final By REASON_CODE = By.id("reason");
	public static final By TBL_LOCATION = By.id("balance-history");
	// public static final By SUBSIDY_FIELD =
	// By.xpath("/html/body/div[3]/div[2]/div/fieldset/div/dl/form/div/dt[2]");
	public static final By SUBSIDY_BALANCE = By.id("readTypebalance");
	public static final By SUBSIDY_READ_BALANCE = By.xpath("(//span[@id='readbalance'])[2]");
	public static final By CANCEL_BTN = By.id("cancelBtn");
	// public static final By LBL_ROLL_OVER_SUBSIDY = By
	// .xpath("/html/body/div[3]/div[2]/div/fieldset/div/dl/form/div/dt[2]");
	public static final By INPUT_PAY_ROLL_ID = By.id("payrollid");

	/**
	 * Getting the Balance
	 * 
	 * @return the value input as a double
	 */
	public double getBalance() {
		double initBalance = 0;
		try {
			String balance = foundation.getText(LBL_READ_BALANCE);
			initBalance = Double
					.parseDouble(balance.substring(1).replace(Constants.DELIMITER_COMMA, Constants.EMPTY_STRING));
//			balance = balance.replaceAll("[\\(\\)\\$]", "");
//			initBalance = Double
//					.parseDouble(balance.substring(1).replace(Constants.DELIMITER_COMMA, Constants.EMPTY_STRING));
//			initBalance = Double.parseDouble(balance);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		return initBalance;
	}

	/**
	 * Creating Object for Tax category
	 * 
	 * @param reasonCode
	 * @return Webelement
	 */
	public By objTaxCategory(String reasonCode) {
		return By.xpath("//table[@id='aadt']//*[text()='" + reasonCode + "']");
	}

	/**
	 * Getting the Type Balance
	 * 
	 * @return the value input as a double
	 */
	public double getTypeBalance() {
		double initTypeBalance = 0;
		try {
			String typeBalance = foundation.getText(LBL_READ_TYPE_BALANCE);
			typeBalance = typeBalance.replaceAll("[\\(\\)\\$]", "");
			initTypeBalance = Double.parseDouble(typeBalance);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		return initTypeBalance;
	}

	/**
	 * Creating Object for Balance History
	 * 
	 * @param data
	 * @return Weblement
	 */
	public By objBalanceHistoryData(String data) {
		return By.xpath("(//tbody[@role='rowgroup']/tr)[1]/td[text()='" + data + "']");
	}

	/**
	 * Move the consumer to a particular Location in a Org
	 * 
	 * @param toOrg
	 * @param toLocation
	 * @return Boolean
	 */
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

	/**
	 * Adjust the balance in Consumer Summary Page
	 * 
	 * @param newBalance
	 * @param reasonCode
	 */
	public void adjustBalance(String newBalance, String reasonCode) {
		foundation.click(BTN_ADJUST);
		textBox.enterText(TXT_ADJUST_BALANCE, newBalance);
		dropdown.selectItem(DPD_REASON, reasonCode, Constants.TEXT);
		foundation.click(BTN_REASON_SAVE);
		foundation.click(BTN_SAVE);
		foundation.threadWait(Constants.SHORT_TIME);
	}

	/**
	 * Increase the subsidy balance
	 * 
	 * @param menuitem
	 * @param location
	 * @param data
	 * @param reason
	 */
	public void Incrementsubsidy(String menuitem, String location, String data, String reason) {
		navigationBar.navigateToMenuItem(menuitem);
		foundation.click(ConsumerSearch.CLEAR_SEARCH);
		dropdown.selectItem(DPD_LOCATION, location, Constants.TEXT);
		foundation.click(ConsumerSearch.BTN_GO);
		Assert.assertTrue(foundation.isDisplayed(ConsumerSearch.TBL_CONSUMERS));
		foundation.click(ConsumerSearch.LNK_FIRST_ROW);
		Assert.assertTrue(foundation.isDisplayed(LBL_CONSUMER_SUMMARY));
		foundation.click(BTN_ADJUST);
		foundation.waitforElement(LBL_POPUP_ADJUST_BALANCE, Constants.SHORT_TIME);
		textBox.enterText(TXT_ADJUST_BALANCE, data);
		dropdown.selectItem(DPD_REASON, reason, Constants.TEXT);
		CustomisedAssert.assertTrue(foundation.isDisplayed(REF_EFT));
		foundation.click(REF_EFT);
	}

	/**
	 * Cancelling the Balance Adjustment in Subsidy
	 * 
	 * @param data
	 * @param reason
	 */
	public void CancelButtonInSubsidyAdjustment(String data, String reason) {

		foundation.click(BTN_ADJUST);
		foundation.waitforElement(TXT_ADJUST_BALANCE, Constants.SHORT_TIME);
		textBox.enterText(TXT_ADJUST_BALANCE, data);
		dropdown.selectItem(DPD_REASON, reason, Constants.TEXT);
		CustomisedAssert.assertTrue(foundation.isDisplayed(BTN_REASON_CANCEL));
		foundation.click(BTN_REASON_CANCEL);
	}

	/**
	 * Read the Header values in Balance History table
	 * 
	 * @return List<String> values
	 */
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

	
	/**
	 * Adjust the balance in Consumer Summary Page
	 * 
	 * @param newBalance
	 * @param reasonCode
	 */
	public void adjustBalanceInAllAccount(By option,String newBalance, String reasonCode) {
		foundation.click(option);
		textBox.enterText(TXT_ADJUST_BALANCE, newBalance);
		dropdown.selectItem(DPD_REASON, reasonCode, Constants.TEXT);
		foundation.click(BTN_REASON_SAVE);
		foundation.threadWait(Constants.SHORT_TIME);
	}

	/**
	 * Read the data from Balance History table
	 * 
	 * @return List<String> values
	 */
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

	/**
	 * Verify the Balance History Headers
	 * 
	 * @param expected
	 */
	public void balanceHistory(List<String> expected) {
		CustomisedAssert.assertTrue(foundation.isDisplayed(LBL_BALANCE_HISTORY));
		CustomisedAssert.assertTrue(foundation.isDisplayed(BALANCE_HISTORY_GRID));
		List<String> tableHeaders = balanceHistoryHeaders();
		CustomisedAssert.assertTrue(tableHeaders.get(0).equals(expected.get(8)));
		CustomisedAssert.assertTrue(tableHeaders.get(1).equals(expected.get(9)));
		CustomisedAssert.assertTrue(tableHeaders.get(2).equals(expected.get(10)));
		CustomisedAssert.assertTrue(tableHeaders.get(3).equals(expected.get(11)));
		CustomisedAssert.assertTrue(tableHeaders.get(4).equals(expected.get(12)));
		CustomisedAssert.assertTrue(tableHeaders.get(5).equals(expected.get(13)));
	}

	/**
	 * Verify the Balance History Data
	 * 
	 * @param value
	 * @param date
	 */
	public void balanceHistoryData(String value, String date) {
		List<String> data = balanceHistoryDatas();
		for (int i = data.size(); i < 0; i--) {
			CustomisedAssert.assertTrue(foundation.isDisplayed(objBalanceHistoryData(data.get(i))));
		}
		// CustomisedAssert.assertTrue(data.get(1).equals(value));
		// CustomisedAssert.assertTrue(data.get(4).contains(date));
	}

	/**
	 * Resetting the Balance Data
	 * 
	 * @param menu
	 * @param location
	 * @param balance
	 * @param reason
	 */
	public void balanceResettingData(String menu, String location, String balance, String reason) {
		navigationBar.navigateToMenuItem(menu);
		foundation.click(ConsumerSearch.CLEAR_SEARCH);
		dropdown.selectItem(ConsumerSearch.DPD_LOCATION, location, Constants.TEXT);
		foundation.click(ConsumerSearch.BTN_GO);
		CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSearch.TBL_CONSUMERS));
		foundation.click(ConsumerSearch.LNK_FIRST_ROW);
		foundation.click(BTN_ADJUST);
		foundation.waitforElement(LBL_POPUP_ADJUST_BALANCE, Constants.SHORT_TIME);
		textBox.enterText(TXT_ADJUST_BALANCE, balance);
		dropdown.selectItem(DPD_REASON, reason, Constants.TEXT);
		CustomisedAssert.assertTrue(foundation.isDisplayed(REF_EFT));
		foundation.click(BTN_REASON_SAVE);
		foundation.threadWait(Constants.THREE_SECOND);
		foundation.click(BTN_SAVE);
		foundation.threadWait(Constants.THREE_SECOND);
	}

	/**
	 * Resetting the balance in AutomationLocation1
	 * 
	 * @param menu
	 * @param Search
	 * @param location
	 * @param balance
	 * @param reason
	 */
	public void balanceResettingDataInAutomationLocation1(String menu, String Search, String location, String balance,
			String reason) {
		navigationBar.navigateToMenuItem(menu);
		CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSearch.TXT_CONSUMER_SEARCH));
		foundation.click(ConsumerSearch.CLEAR_SEARCH);
		foundation.threadWait(Constants.SHORT_TIME);
		textBox.enterText(ConsumerSearch.TXT_SEARCH, Search);
		foundation.threadWait(Constants.THREE_SECOND);
		dropdown.selectItem(ConsumerSearch.DPD_LOCATION, location, Constants.TEXT);
		foundation.click(ConsumerSearch.BTN_GO);
		CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSearch.TBL_CONSUMERS));
		foundation.click(ConsumerSearch.LNK_FIRST_ROW);
		foundation.click(BTN_TOP_OFF_ADJUST);
		foundation.waitforElement(LBL_POPUP_ADJUST_BALANCE, Constants.SHORT_TIME);
		textBox.enterText(TXT_ADJUST_BALANCE, balance);
		dropdown.selectItem(DPD_REASON, reason, Constants.TEXT);
		CustomisedAssert.assertTrue(foundation.isDisplayed(REF_EFT));
		foundation.click(BTN_REASON_SAVE);
		foundation.threadWait(Constants.SHORT_TIME);
		foundation.click(BTN_SAVE);
	}

	/**
	 * click On Consumer In consumer search page And Update Balance In TopOffSubsidy
	 * Consumer summary
	 * 
	 * @param balance
	 */
	public void clickOnConsumerAndUpdateBalanceInTopOffSubsidy(String balance) {
		CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSearch.TBL_CONSUMERS));
		foundation.click(ConsumerSearch.LNK_FIRST_ROW);
		foundation.click(BTN_SUBSIDY_ADJUST);
		foundation.waitforElement(LBL_POPUP_ADJUST_BALANCE, Constants.SHORT_TIME);
		textBox.enterText(TXT_ADJUST_BALANCE, balance);
//		CustomisedAssert.assertTrue(foundation.isDisplayed(REF_EFT));
		foundation.threadWait(Constants.SHORT_TIME);
		foundation.click(BTN_REASON_SAVE);
		foundation.waitforElementToBeVisible(TXT_SPINNER_MSG, Constants.SHORT_TIME);
		foundation.click(BTN_SAVE);
		foundation.waitforElementToBeVisible(ConsumerSearch.TXT_CONSUMER_SEARCH, Constants.SHORT_TIME);
	}

	/**
	 * click On Consumer In consumer search page And verify the Balance In
	 * TopOffSubsidy Consumer summary
	 * 
	 * @param location
	 * @param actualbalance
	 */
	public void clickOnConsumerAndVerrifyBalanceInTopOffSubsidy(String location, String actualbalance) {
		foundation.click(ConsumerSearch.CLEAR_SEARCH);
		dropdown.selectItem(ConsumerSearch.DPD_LOCATION, location, Constants.TEXT);
		foundation.click(ConsumerSearch.BTN_GO);
		CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSearch.TBL_CONSUMERS));
		foundation.click(ConsumerSearch.LNK_FIRST_ROW);
		String balance = foundation.getText(SUBSIDY_READ_BALANCE);
		CustomisedAssert.assertEquals(balance, actualbalance);
	}

	/**
	 * Reset balance on TopOff subsidy in consumer summary page
	 * 
	 * @param balance
	 */
	public void setTopOffSubsidyBalance(String balance) {
		foundation.waitforElementToBeVisible(ConsumerSummary.BTN_SUBSIDY_ADJUST, Constants.SHORT_TIME);
		foundation.click(ConsumerSummary.BTN_SUBSIDY_ADJUST);
		foundation.waitforElement(ConsumerSummary.LBL_POPUP_ADJUST_BALANCE, Constants.SHORT_TIME);
		textBox.enterText(ConsumerSummary.TXT_ADJUST_BALANCE, balance);
//		CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSummary.REF_EFT));
		foundation.click(ConsumerSummary.BTN_REASON_SAVE);
		foundation.waitforElementToBeVisible(ConsumerSummary.TXT_SPINNER_MSG, Constants.SHORT_TIME);
		foundation.click(ConsumerSummary.BTN_SAVE);
		foundation.waitforElementToBeVisible(ConsumerSearch.TXT_CONSUMER_SEARCH, Constants.SHORT_TIME);
	}
	
	/**
	 * verify amount in consumer summary page
	 * @param bal
	 */
	public void verifyAmountInConsumerSummaryAndAdjustBalancePopup(By obj,String bal) {
		CustomisedAssert.assertTrue(foundation.isDisplayed(LBL_CONSUMER_SUMMARY));
		foundation.waitforElementToBeVisible(BTN_ADJUST, 3);
		foundation.click(obj);
		foundation.waitforElementToBeVisible(LBL_POPUP_ADJUST_BALANCE, 3);
		CustomisedAssert.assertTrue(foundation.isDisplayed(LBL_POPUP_ADJUST_BALANCE));
		String text = foundation.getAttributeValue(TXT_ADJUST_BALANCE);
		CustomisedAssert.assertEquals(text, bal);
		foundation.waitforElementToBeVisible(BTN_REASON_CANCEL, 3);
		foundation.click(BTN_REASON_CANCEL);
	}
	
	/**
	 * change pde balance in consumer summary page
	 * @param balance
	 * @param reason
	 */
	public void searchWithConsumerAndChangePDEBalance(String balance,String reason) {
		foundation.threadWait(Constants.THREE_SECOND);
		foundation.click(ConsumerSearch.LNK_FIRST_ROW);
		foundation.waitforElementToBeVisible(ConsumerSummary.LBL_CONSUMER_SUMMARY, Constants.THREE_SECOND);
		CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSummary.PAYROLL_BTN_ADJUST));
		foundation.click(ConsumerSummary.PAYROLL_BTN_ADJUST);
		foundation.waitforElement(ConsumerSummary.LBL_POPUP_ADJUST_BALANCE, Constants.SHORT_TIME);
		textBox.enterText(ConsumerSummary.TXT_ADJUST_BALANCE, balance);
		dropdown.selectItem(ConsumerSummary.DPD_REASON, reason, Constants.TEXT);
		foundation.waitforElementToBeVisible(ConsumerSummary.BTN_REASON_SAVE, Constants.THREE_SECOND);
		foundation.click(ConsumerSummary.BTN_REASON_SAVE);
		foundation.waitforElementToBeVisible(ConsumerSummary.BTN_SAVE, Constants.THREE_SECOND);
		foundation.click(ConsumerSummary.BTN_SAVE);
		foundation.threadWait(Constants.SHORT_TIME);

	}
}
