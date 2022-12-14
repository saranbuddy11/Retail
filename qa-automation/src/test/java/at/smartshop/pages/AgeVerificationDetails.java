package at.smartshop.pages;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import at.framework.browser.Factory;
import at.framework.generic.CustomisedAssert;
import at.framework.generic.DateAndTime;
import at.framework.ui.CheckBox;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.keys.Constants;
import at.smartshop.tests.TestInfra;

public class AgeVerificationDetails extends Factory {

	private Foundation foundation = new Foundation();
	private Dropdown dropDown = new Dropdown();
	private TextBox textBox = new TextBox();
	private DateAndTime dateAndTime = new DateAndTime();
	private CheckBox checkBox = new CheckBox();
	private Login login = new Login();
	private NavigationBar navigationBar = new NavigationBar();
	private LocationList locationList = new LocationList();

	public static final By TXT_AGE_VERIFICATION = By.xpath("//li[text()='Age Verification']");
	public static final By TXT_PROMPT_MSG = By.xpath("//div[text()='Confirm PIN Expiration']");
	public static final By TXT_PROMPT_CONTENT = By.xpath("//div[@class='ajs-content']");
	public static final By BTN_NO = By.xpath("//button[text()='No ']");
	public static final By BTN_YES = By.xpath("//button[text()='Yes']");
	public static final By DPD_LOCATION = By.id("location");
	public static final By INPUT_MAIL = By.id("email");
	public static final By INPUT_FNAME = By.id("firstname");
	public static final By INPUT_LNAME = By.id("lastname");
	public static final By DPD_LANGUAGE = By.id("language");
	public static final By INPUT_DAILY_USES = By.id("dailyuses");
	public static final By CHECKOUT_DATE = By.id("checkout");
	public static final By BTN_CREATE_PIN = By.id("createsendpinbtn");
	public static final By EXPIRE_BTN = By
			.xpath("//button[@class='btn btn-primary blockactive expireAgeVerification']");
	public static final By LBL_AGE_VERIFICATION_SETUP_PANEL = By.id("mainform");
	public static final By TXT_STATUS = By.xpath("//dt[text()='Show Active, Expired or All']");
	public static final By DPD_STATUS = By.id("filtervalues");
	public static final By BTN_CLOSE = By.xpath("//button[@class='ajs-close']");
	public static final By TXT_LOCATION = By.xpath("//dt[normalize-space(text())='Location']");
	public static final By TXT_MAIL = By.xpath("//dt[normalize-space(text())='Email Address']");
	public static final By TXT_FNAME = By.xpath("//dt[normalize-space(text())='First Name']");
	public static final By TXT_LNAME = By.xpath("//dt[normalize-space(text())='Last Name']");
	public static final By TXT_LANGUAGE = By.xpath("//dt[normalize-space(text())='Languages']");
	public static final By TXT_CHECK_OUT = By.xpath("//dt[normalize-space(text())='Check Out']");
	public static final By TXT_DAILY_USES = By.xpath("//dt[normalize-space(text())='Daily Uses']");
	public static final By LBL_SEARCH = By.xpath("//label[text()='Search: ']");
	public static final By TABLE_GRID = By.xpath("//div[@role='grid']");
	public static final By BTN_RESEND = By.xpath("//button[text()='Resend']");
	public static final By BTN_EXPIRE = By.xpath("//button[text()='Expire']");
	public static final By TBL_EXPIRED_GRID = By.cssSelector("#dt > tbody");
	public static final By TBL_EXPIRED = By.id("dt");
	public static final By DPD_LENGTH = By.xpath("//select[@name='dt_length']");
	public static final By LABEL_RECORDS = By.xpath("//label[text()=' records per page']");
	public static final By TXT_NEXT = By.xpath("//a[contains(text(),'Next')]");
	public static final By TXT_PREVIOUS = By.xpath("//a[contains(text(),'Previous')]");
	public static final By TXT_SPINNER_MSG = By.xpath("//div[@class='ajs-message ajs-success ajs-visible']");
	public static final By DAILY_USES = By.id("Daily Uses");
	public static final By ERROR_MSG_DAILY_USES = By.id("dailyuses-error");
	public static final By TXT_EMAIL_ERROR = By.id("email-error");
	public static final By TXT_CHECKOUT_ERROR = By.id("checkout-error");
	public static final By INPUT_TEXT = By.xpath("//input[@aria-controls='dt']");
	public static final By LBL_LOCATION_NAME = By.cssSelector(
			"#divBdy > div > div > div > font:nth-child(2) > table > tbody > tr:nth-child(1) > td > font > span > b");
	public static final By EMAIL_BODY = By.xpath("//div[@id='divItmPrts']//div[@id='divBdy']//tbody//b");
	public static final By LBL_PRODUCT_NAME=By.xpath("//b[text()='Apple Juice']");
	public static final By EMAIL_DELETE = By.xpath("(//div[@id='divMsgItemTB'])[2]//a[@id='delete']");
	public static final By LBL_PRODUCT_AMOUNT=By.xpath("//td[@align='right']/font");
	public static final By MAIL_FOLDER=By.id("spnFldrNm");

	private List<String> tableHeaders = new ArrayList<>();
	private Map<Integer, Map<String, String>> tableData = new LinkedHashMap<>();

	public By automationNewLocation(String text) {
		return By.xpath("//select[@id='location']//option[text()='" + text + "']");
	}

	public By language(String text) {
		return By.xpath("//select[@id='language']//option[text()='" + text + "']");
	}

	public By objExpirePinConfirmation(String location, String text) {
		return By.xpath("//td[text()='" + location + "']//..//td/button[text()='" + text + "']");
	}

	public By objExpirePinConfirmationWithIndex(String location, String text, String index) {
		return By.xpath("(//td[text()='" + location + "']//..//td/button[text()='" + text + "'])[" + index + "]");
	}

	public By objExpiredPinlist(String text) {
		return By.xpath("//tr[@class='odd']/td[text()='" + text + "']");
	}

	public By objMailFolder(String text) {
		return By.xpath("//span[@id='spnFldrNm' and @fldrnm='" + text + "']");
	}

	public By objEmailList(String text) {
		return By.xpath("//div[@id='divList']//div[@id='divSubject' and contains(text(),'" + text + "')]");
	}

	public void verifyPinExpirationPrompt(String location, List<String> prompt, String status) {
		foundation.threadWait(Constants.SHORT_TIME);
		foundation.click(objExpirePinConfirmation(location, prompt.get(0)));
		foundation.threadWait(Constants.THREE_SECOND);
		CustomisedAssert.assertTrue(foundation.isDisplayed(TXT_PROMPT_MSG));
		foundation.threadWait(Constants.THREE_SECOND);
		CustomisedAssert.assertTrue(foundation.isDisplayed(TXT_PROMPT_CONTENT));
		String value = foundation.getText(TXT_PROMPT_CONTENT);
		String[] actuals = value.split("\\s{2,6}");
		for (int i = 0; i < actuals.length; i++) {
			CustomisedAssert.assertTrue(actuals[i].contains(prompt.get(i + 1)));
		}
		CustomisedAssert.assertTrue(foundation.isDisplayed(BTN_NO));
		CustomisedAssert.assertTrue(foundation.isDisplayed(BTN_YES));
		foundation.click(BTN_NO);
		foundation.threadWait(Constants.TWO_SECOND);
		CustomisedAssert.assertTrue(foundation.isDisplayed(TXT_STATUS));
		dropDown.selectItem(DPD_STATUS, status, Constants.TEXT);
		CustomisedAssert.assertTrue(foundation.isDisplayed(objExpirePinConfirmation(location, prompt.get(0))));
	}

	public void createAgeVerificationPin(String location, List<String> datas) {
		String currentDate = dateAndTime.getDateAndTime(Constants.REGEX_DD_MM_YYYY, Constants.TIME_ZONE_INDIA);
		dropDown.selectItem(DPD_LOCATION, location, Constants.TEXT);
		textBox.enterText(INPUT_MAIL, datas.get(3));
		textBox.enterText(INPUT_FNAME, datas.get(4));
		textBox.enterText(INPUT_LNAME, datas.get(5));
		dropDown.selectItem(DPD_LANGUAGE, datas.get(6), Constants.TEXT);
		textBox.enterText(CHECKOUT_DATE, currentDate);
		textBox.enterText(INPUT_DAILY_USES, datas.get(7));
		foundation.click(BTN_CREATE_PIN);
		foundation.objectClick(BTN_CREATE_PIN);
		foundation.waitforElementToDisappear(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
		foundation.threadWait(Constants.THREE_SECOND);
	}

	public void createAgeVerificationPinWithoutEmail(String location, List<String> datas) {
		String currentDate = dateAndTime.getDateAndTime(Constants.REGEX_DD_MM_YYYY, Constants.TIME_ZONE_INDIA);
		dropDown.selectItem(DPD_LOCATION, location, Constants.TEXT);
		textBox.enterText(INPUT_FNAME, datas.get(4));
		textBox.enterText(INPUT_LNAME, datas.get(5));
		dropDown.selectItem(DPD_LANGUAGE, datas.get(6), Constants.TEXT);
		textBox.enterText(CHECKOUT_DATE, currentDate);
		textBox.enterText(INPUT_DAILY_USES, datas.get(7));
	}

	public Map<Integer, Map<String, String>> getTblRecordsUI() {
		try {
			int recordCount = 0;
			tableHeaders.clear();
			WebElement tableList = getDriver().findElement(TBL_EXPIRED_GRID);
			WebElement table = getDriver().findElement(TBL_EXPIRED);
			List<WebElement> columnHeaders = table.findElements(By.cssSelector("thead > tr > th"));
			List<WebElement> rows = tableList.findElements(By.tagName("tr"));
			for (WebElement columnHeader : columnHeaders) {
				tableHeaders.add(columnHeader.getText());
			}
			int col = tableHeaders.size();
			for (WebElement row : rows) {
				Map<String, String> uiTblRowValues = new LinkedHashMap<>();
				for (int columnCount = 1; columnCount < col + 1; columnCount++) {
					foundation.scrollIntoViewElement(By.cssSelector("td:nth-child(" + columnCount + ")"));
					WebElement column = row.findElement(By.cssSelector("td:nth-child(" + columnCount + ")"));
					uiTblRowValues.put(tableHeaders.get(columnCount - 1), column.getText());
				}
				tableData.put(recordCount, uiTblRowValues);
				recordCount++;
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		return tableData;
	}

	public void verifyAllFieldsOfAgeVerificationSetup() {
		CustomisedAssert.assertTrue(foundation.isDisplayed(TXT_LOCATION));
		CustomisedAssert.assertTrue(foundation.isDisplayed(DPD_LOCATION));
		CustomisedAssert.assertTrue(foundation.isDisplayed(TXT_MAIL));
		CustomisedAssert.assertTrue(foundation.isDisplayed(INPUT_MAIL));
		CustomisedAssert.assertTrue(foundation.isDisplayed(TXT_FNAME));
		CustomisedAssert.assertTrue(foundation.isDisplayed(INPUT_FNAME));
		CustomisedAssert.assertTrue(foundation.isDisplayed(TXT_LNAME));
		CustomisedAssert.assertTrue(foundation.isDisplayed(INPUT_LNAME));
		CustomisedAssert.assertTrue(foundation.isDisplayed(TXT_LANGUAGE));
		CustomisedAssert.assertTrue(foundation.isDisplayed(DPD_LANGUAGE));
		CustomisedAssert.assertTrue(foundation.isDisplayed(TXT_CHECK_OUT));
		CustomisedAssert.assertTrue(foundation.isDisplayed(CHECKOUT_DATE));
		CustomisedAssert.assertTrue(foundation.isDisplayed(TXT_DAILY_USES));
		CustomisedAssert.assertTrue(foundation.isDisplayed(INPUT_DAILY_USES));
		CustomisedAssert.assertTrue(foundation.isDisplayed(BTN_CREATE_PIN));
	}

	public void verifyAllFieldsOfActivePins() {
		CustomisedAssert.assertTrue(foundation.isDisplayed(DPD_STATUS));
		CustomisedAssert.assertTrue(foundation.isDisplayed(LBL_SEARCH));
		CustomisedAssert.assertTrue(foundation.isDisplayed(TABLE_GRID));
		CustomisedAssert.assertTrue(foundation.isDisplayed(BTN_RESEND));
		CustomisedAssert.assertTrue(foundation.isDisplayed(BTN_EXPIRE));
		CustomisedAssert.assertTrue(foundation.isDisplayed(LABEL_RECORDS));
	}

	public void verifyPagination(String value, String status) {
		foundation.scrollIntoViewElement(AgeVerificationDetails.LABEL_RECORDS);
		dropDown.selectItem(AgeVerificationDetails.DPD_LENGTH, value, Constants.TEXT);
		foundation.scrollIntoViewElement(AgeVerificationDetails.TXT_STATUS);
		dropDown.selectItem(AgeVerificationDetails.DPD_STATUS, status, Constants.TEXT);
		foundation.threadWait(Constants.THREE_SECOND);
		Map<Integer, Map<String, String>> uiTableData = getTblRecordsUI();
		int record = uiTableData.size();
		CustomisedAssert.assertEquals(String.valueOf(record), value);
	}

	/**
	 * Verifying Email Field in PIN Creation under Age Verification Page
	 * 
	 * @param mail
	 * @param location
	 * @param text
	 */
	public void verifyEmailFieldinPinCreation(String mail, String location, String text) {
		textBox.enterText(AgeVerificationDetails.INPUT_MAIL, mail);
		foundation.click(AgeVerificationDetails.TXT_DAILY_USES);
		foundation.click(AgeVerificationDetails.BTN_CREATE_PIN);
		foundation.objectClick(AgeVerificationDetails.BTN_CREATE_PIN);
		foundation.waitforElementToDisappear(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
		CustomisedAssert.assertTrue(foundation.isDisplayed(objExpirePinConfirmation(location, text)));
	}

	/**
	 * Checking the Defaults of Age Verification under Location Summary Page
	 */
	public void checkingOnDefaultsOfAgeVerification() {
		foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
		foundation.scrollIntoViewElement(LocationSummary.TXT_AGE_VERIFICATION);
		CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_AGE_VERIFICATION));
		if (checkBox.isChkEnabled(LocationSummary.CHK_AGE_VERIFICATION))
			checkBox.check(LocationSummary.CHK_AGE_VERIFICATION);
		foundation.click(LocationSummary.BTN_SAVE);
		foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
	}

	/**
	 * Opening the Folder and Clicking on Mail
	 * 
	 * @param option
	 */
	public void openingFolderAndClickMail(String option, String keyword) {
		foundation.objectClick(objMailFolder(option));
		foundation.threadWait(Constants.LONG_TIME);
		foundation.objectClick(objEmailList(keyword));
	}

	/**
	 * Validates the Mail content
	 * 
	 * @param option
	 * @param heading
	 * @param location
	 * @param digitCount
	 */
	public String validateMailContent(String heading, String location, String digitCount) {
		List<String> content = foundation.getTextofListElement(EMAIL_BODY);
		CustomisedAssert.assertEquals(content.get(0), heading);
		CustomisedAssert.assertEquals(content.get(1), location);
		CustomisedAssert.assertTrue(foundation.isNumeric(content.get(4)));
		String count = String.valueOf(content.get(4).length());
		CustomisedAssert.assertEquals(count, digitCount);
		return content.get(4);
	}

	/**
	 * Deleting the Outlook Mail and Logout
	 */
	public void deleteOutLookMailAndLogout() {
		foundation.objectClick(EMAIL_DELETE);
		foundation.threadWait(Constants.SHORT_TIME);
		login.outLookLogout();
		foundation.threadWait(Constants.SHORT_TIME);
	}

	/**
	 * Expire PIN at Age Verification Page
	 * 
	 * @param location
	 * @param keyword
	 */
	public void expirePinWithConfirmationPrompt(String location, String keyword) {
		foundation.click(objExpirePinConfirmation(location, keyword));
		foundation.click(BTN_YES);
		foundation.refreshPage();
		foundation.scrollIntoViewElement(TXT_STATUS);
	}

	/**
	 * Resetting Age Verification Check Box in Location Summary Page
	 * 
	 * @param menu
	 * @param location
	 */
	public void resettingAgeVerificationCheckBox(String menu, String location) {
		foundation.scrollIntoViewElement(LocationSummary.TAB_LOCATION);
		navigationBar.navigateToMenuItem(menu);
		foundation.threadWait(Constants.THREE_SECOND);
		locationList.selectLocationName(location);
		foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
		foundation.scrollIntoViewElement(LocationSummary.TXT_AGE_VERIFICATION);
		CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_AGE_VERIFICATION));
		if (checkBox.isChkEnabled(LocationSummary.CHK_AGE_VERIFICATION))
			checkBox.unCheck(LocationSummary.CHK_AGE_VERIFICATION);
		foundation.click(LocationSummary.BTN_SAVE);
		foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
	}
}
