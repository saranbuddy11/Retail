package at.smartshop.pages;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import at.framework.browser.Factory;
import at.framework.generic.CustomisedAssert;
import at.framework.generic.Numbers;
import at.framework.generic.Strings;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.Table;
import at.framework.ui.TextBox;
import at.smartshop.keys.Constants;
import at.smartshop.tests.TestInfra;

public class ConsumerSearch extends Factory {
	private TextBox textBox = new TextBox();
	private Dropdown dropdown = new Dropdown();
	private Foundation foundation = new Foundation();
	private NavigationBar navigationBar = new NavigationBar();
	private Strings strings = new Strings();
	private Numbers numbers = new Numbers();
	private Table table = new Table();

	public static final By DPD_LOCATION = By.id("loc-dropdown");
	private static final By DPD_STATUS = By.id("isdisabled");
	public static final By DPD_SEARCH_BY = By.id("searchBy");
	public static final By TXT_SEARCH = By.id("search");
	public static final By BTN_GO = By.id("findBtn");
	public static final By TBL_CONSUMERS = By.id("consumerdt");
	public static final By BTN_ADJUST = By.xpath("//a[text()='Adjust']");
	public static final By TXT_BALANCE_NUM = By.id("balNum");
	public static final By TBL_LOCATION = By.id("consumerdt");
	public static final By BTN_REASON_CANCEL = By.id("reasoncancel");
	public static final By BTN_ACTIONS = By.xpath("//a[@class='btn dropdown-toggle btn-danger']");
	public static final By LBL_BULK_ADD_FUNDS = By.xpath("//li[@id='fundSelectedBtn']");
	public static final By LBL_BULK_REMOVE_FUNDS = By.xpath("//li[@id='removeFundSelectedBtn']");
	public static final By LBL_BULK_TOPOFF_FUNDS = By.xpath("//li[@id='topOffFundSelectedBtn']");
	public static final By LBL_BULK_PAYOUT = By.xpath("//li[@id='payoutAndCloseBtn']");
	public static final By BTN_OK = By.xpath("//button[text()='Ok']");
	public static final By BTN_CREATE = By.cssSelector("button#createNewBtn");
	public static final By TBL_ROW = By.xpath("//*[@id='consumerdt']/tbody/tr[@class='odd']");
	public static final By BTN_CONFIRM = By.xpath("//button[text()='Confirm']");
	public static final By BTN_CREATE_OR_INVITE = By.id("submitBtn");
	public final static By TXT_CONSUMER_SEARCH = By.id("Consumer Search");
	public final static By BTN_CREATE_NEW = By.id("createNewBtn");
	public final static By TXT_EMAIL = By.id("email");
	public final static By TXT_SCAN_ID = By.id("scanid");
	public final static By TXT_FIRST_NAME = By.id("firstname");
	public final static By TXT_LAST_NAME = By.id("lastname");
	public final static By TXT_PIN = By.id("pin");
	public static final By DPD_PAY_CYCLE = By.id("paycycle");
	public static final By LNK_FIRST_ROW = By.xpath("//table[@id='consumerdt']//td//a");
	public static final By LNK_RECORD = By.xpath("//table[@id='consumerdt']//td");
	public static final By BTN_CREATE_CONSUMER = By.id("submitBtn");
	public static final By TXT_SPINNER_MSG = By.xpath("//div[@class='humane humane-libnotify-info']");
	public static final By BTN_ACTION = By.xpath("//ul[@style='left: -100px; right: auto;']//li");
	public static final By ACTION_BTN = By.xpath("//a[@class='btn dropdown-toggle btn-danger']");
	public static final By BULK_ASSIGN_SUBSIDY_GROUP = By.id("subsidyGroupSelectedBtn");
	public static final By SUBSIDY_GROUP = By.id("subsidyGroupData");
	public static final By RSN_CANCEL = By.id("reasoncancel");
	public static final By CLEAR_SEARCH = By.xpath("//span[@class='select2-selection__clear']");
	public static final By LBL_BULK_ASSIGN_POPUP = By.id("reasontitle");
	public static final By BTN_SAVE = By.id("reasonSaveBtn");
	public static final By BTN_EXPORT = By.id("exportBtn");


	public void enterSearchFields(String searchBy, String search, String locationName, String status) {
		try {
			dropdown.selectItem(DPD_SEARCH_BY, searchBy, Constants.TEXT);
			textBox.enterText(TXT_SEARCH, search);
			foundation.click(ConsumerSearch.CLEAR_SEARCH);
			dropdown.selectItem(DPD_LOCATION, locationName, Constants.TEXT);
			dropdown.selectItem(DPD_STATUS, status, Constants.TEXT);
			foundation.click(BTN_GO);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}

	}

	public By objCell(String consumerName) {
		return By.xpath("//table[@id='consumerdt']//tbody//tr//td//a[text()='" + consumerName + "']");
	}

	public By objFirstNameCell(String consumerFirstName) {
		return By.xpath("(//table[@id='consumerdt']//tbody//tr//td//a[text()='" + consumerFirstName + "'])[1]");
	}

	public String getConsumerName() {
		return foundation.getText(By.xpath("//table[@id='consumerdt']//tbody//tr//td//a"));
	}

	public String getConsumerFirstName() {
		return foundation.getText(By.xpath("(//table[@id='consumerdt']//tbody//tr//td//a)[1]"));
	}

	public String getSubsidyName() {
		return foundation.getText(By.xpath("(//table[@id='consumerdt']//tbody//tr//td)[17]"));
	}

	public void verifyConsumerSummary(String consumerName) {
		foundation.click(objCell(consumerName));
		CustomisedAssert.assertTrue(getDriver().findElement(ConsumerSummary.LBL_CONSUMER_SUMMARY).isDisplayed());
	}

	public List<String> getConsumerHeaders() {
		List<String> tableHeaders = new ArrayList<>();
		try {
			WebElement tableProducts = getDriver().findElement(TBL_LOCATION);
			List<WebElement> columnHeaders = tableProducts.findElements(By.cssSelector("thead > tr > th"));
			for (WebElement columnHeader : columnHeaders) {
				tableHeaders.add(columnHeader.getText());
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		return tableHeaders;
	}

	public String getBalance(String location) {
		Map<String, String> consumerTblRecords = getConsumerRecords(location);
		return consumerTblRecords.get("Balance").replaceAll("[\\(\\)\\$]", "");
	}

	public Map<String, String> getConsumerRecords(String location) {
		Map<String, String> consumerRecord = new LinkedHashMap<>();
		try {
			List<String> tableHeaders = getConsumerHeaders();
			for (int columnCount = 1; columnCount < tableHeaders.size() + 1; columnCount++) {
				WebElement column = getDriver().findElement(By.xpath("//table[@id='consumerdt']//tr//td[(text()='"
						+ location + "')]//..//..//td[" + columnCount + "]"));
				consumerRecord.put(tableHeaders.get(columnCount - 1), column.getText());
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		return consumerRecord;
	}

	public void objLocation(String location) {
		List<WebElement> uiRecords = getDriver()
				.findElements(By.xpath("//table[@id='consumerdt']//tr//td[(text()='" + location + "')]"));
		for (int i = 0; i < uiRecords.size(); i++) {

			uiRecords.get(i).click();

		}

	}

	public String createConsumer(String location) {
		String emailID = strings.getRandomCharacter() + Constants.AUTO_TEST_EMAIL;
		int scanID = numbers.generateRandomNumber(99999, 999999);
		int pin = numbers.generateRandomNumber(1000, 9999);
		dropdown.selectItem(DPD_LOCATION, location, Constants.TEXT);
		textBox.enterText(TXT_FIRST_NAME, Constants.AUTO_TEST + strings.getRandomCharacter());
		textBox.enterText(TXT_LAST_NAME, Constants.AUTO_TEST + strings.getRandomCharacter());
		textBox.enterText(TXT_EMAIL, emailID);
		textBox.enterText(TXT_SCAN_ID, "" + scanID);
		textBox.enterText(TXT_PIN, "" + pin);
		foundation.click(BTN_CREATE_OR_INVITE);
		foundation.WaitForAjax(Constants.SHORT_TIME);
		foundation.waitforElementToDisappear(TXT_SPINNER_MSG, Constants.SHORT_TIME);
		return emailID;
	}

	public void BulkAssignSubsidyGroup(String location, String row, List<String> values) {

		dropdown.selectItem(DPD_LOCATION, location, Constants.TEXT);
		foundation.click(ConsumerSearch.BTN_GO);
		CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSearch.TBL_CONSUMERS));
		table.selectRow(row);
		foundation.click(ConsumerSearch.ACTION_BTN);
		foundation.threadWait(Constants.SHORT_TIME);
		List<String> actualData = foundation.getTextofListElement(BTN_ACTION);
		CustomisedAssert.assertTrue(actualData.equals(values));

	}

	public void BulkAssignSubsidyGroupInMoreThanTwoGrid(String location, String row, String row2) {

		dropdown.selectItem(DPD_LOCATION, location, Constants.TEXT);
		foundation.click(ConsumerSearch.BTN_GO);
		CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSearch.TBL_CONSUMERS));
		foundation.threadWait(Constants.SHORT_TIME);
		table.selectRow(row);
		table.selectRow(row2);
		foundation.threadWait(Constants.SHORT_TIME);
		foundation.click(ConsumerSearch.ACTION_BTN);
		foundation.threadWait(Constants.SHORT_TIME);
	}

	public void searchConsumer(String option, String location) {
		navigationBar.navigateToMenuItem(option);
		foundation.click(ConsumerSearch.CLEAR_SEARCH);
		dropdown.selectItem(ConsumerSearch.DPD_LOCATION, location, Constants.TEXT);
		foundation.click(ConsumerSearch.BTN_GO);
		foundation.threadWait(Constants.ONE_SECOND);
	}

	public void verifyColumnName(List<String> expected, String actual) {
		List<String> expectedColumns = expected;
		CustomisedAssert.assertTrue(expectedColumns.get(0).equals(actual));
	}

	
	public void createConsumerInConsumerSearch(String location,String firstname,String lastname,String emailID,String scanID,String pin) {
		
		dropdown.selectItem(DPD_LOCATION, location, Constants.TEXT);
		textBox.enterText(TXT_FIRST_NAME, firstname );
		textBox.enterText(TXT_LAST_NAME, lastname);
		textBox.enterText(TXT_EMAIL, emailID);
		textBox.enterText(TXT_SCAN_ID,scanID);
		textBox.enterText(TXT_PIN, pin);
		foundation.click(BTN_CREATE_OR_INVITE);
		foundation.WaitForAjax(Constants.SHORT_TIME);
		foundation.waitforElementToDisappear(TXT_SPINNER_MSG, Constants.SHORT_TIME);


}

}
