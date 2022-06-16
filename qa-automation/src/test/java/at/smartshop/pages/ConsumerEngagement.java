package at.smartshop.pages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import at.framework.browser.Factory;
import at.framework.generic.CustomisedAssert;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.keys.Constants;
import at.smartshop.tests.TestInfra;

public class ConsumerEngagement extends Factory {
	private Foundation foundation = new Foundation();
	private TextBox textBox = new TextBox();

	public static final By PAGE_TITLE = By.id("Consumer Engagement");
	public static final By HEADER = By.cssSelector("#mainform > div > h4");
	public static final By BTN_ADD_GIFT_CARD = By.id("giftcardBtn");
	public static final By TAB_GIFT_CARD = By.className("accordion");
	public static final By LBL_HEADER = By.className("header-text");
	public static final By INPUT_TITLE = By.id("title");
	public static final By INPUT_AMOUNT = By.id("amount");
	public static final By INPUT_EXPIRE_DATE = By.id("expirationdate");
	public static final By CHECK_BOX_NO_END_DATE = By.id("noenddate");
	public static final By BTN_ADD_GIFT_SAVE = By.id("addgiftsavebtn");
	public static final By TBL_CONSUMER_ENGAGE_GRID = By.cssSelector("#consumerengageGrid > tbody");
	public static final By TBL_HEADERS_EXPIRED_GRID = By.cssSelector("#consumerexpiredGrid >thead > tr > th");
	public static final By TBL_CONSUMER_ENGAGE = By.id("consumerengageGrid");
	public static final By BTN_PRINT_FIRST_ROW = By.cssSelector("#consumerengageGrid tr:nth-child(1) td:nth-child(2)");
	public static final By BTN_ISSUE_FIRST_ROW = By.cssSelector("#consumerengageGrid tr:nth-child(1) td:nth-child(1)");
	public static final By LBL_PRINT = By.id("titletoprint");
	public static final By LBL_ISSUE = By.id("titletoissue");
	public static final By INPUT_CARD_PRINT = By.id("cardstoprint");
	public static final By BTN_PRINT = By.id("printBtn");
	public static final By TAB_ACTIVE = By.id("activetab");
	public static final By TAB_EXPIRED = By.id("expiredtab");
	public static final By TXT_EXPIRED_TITLE = By.className("ui-iggrid-headertext");
	public static final By BTN_ISSUE = By.cssSelector("td[aria-describedby='consumerengageGrid_Issue']");
	public static final By TBL_EXPIRED = By.cssSelector("tr[data-id='undefined']");
	public static final By LBL_BY_LOCATION = By.id("byloc");
	public static final By LBL_BY_EMAIL = By.id("bymail");
	public static final By BTN_EMAIL_CARDS = By.id("issueemailbyemail");
	public static final By BTN_CANCEL_EMAIL = By.id("issuebyemailCancel");
	public static final By INPUT_EMAIL = By.id("recipientemail");
	public static final By BTN_ADD_GIFT_CANCEL = By.id("addgiftcancelbtn");
	public static final By TITLE_ERROR = By.id("title-error");
	public static final By AMOUNT_ERROR = By.id("amount-error");
	public static final By DATE_VALIDATION_ERROR = By.id("expiredDateValid");

	private List<String> tableHeaders = new ArrayList<>();
	private Map<Integer, Map<String, String>> tableData = new LinkedHashMap<>();

	/**
	 * Gift Card Creation with inputs
	 * 
	 * @param title
	 * @param amount
	 * @param expiry
	 */
	public void createGiftCard(String title, String amount, String expiry) {
		foundation.click(BTN_ADD_GIFT_CARD);
		CustomisedAssert.assertTrue(foundation.isDisplayed(LBL_HEADER));
		textBox.enterText(INPUT_TITLE, title);
		textBox.enterText(INPUT_AMOUNT, amount);
		textBox.enterText(INPUT_EXPIRE_DATE, expiry);
		foundation.click(BTN_ADD_GIFT_SAVE);
		foundation.waitforElementToBeVisible(BTN_ADD_GIFT_CARD, Constants.SHORT_TIME);
		foundation.threadWait(Constants.THREE_SECOND);
	}

	/**
	 * Getting Table Records from Active Gift card Tab
	 * 
	 * @return
	 */
	public Map<Integer, Map<String, String>> getTblActiveRecordsUI() {
		try {
			int recordCount = 0;
			tableHeaders.clear();
			WebElement tableList = getDriver().findElement(TBL_CONSUMER_ENGAGE_GRID);
			WebElement table = getDriver().findElement(TBL_CONSUMER_ENGAGE);
			List<WebElement> columnHeaders = table.findElements(By.cssSelector("thead > tr[data-mch-level='1'] > th"));
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

	/**
	 * Verify the content of Table Record with Particular Value
	 * 
	 * @param uiData
	 * @param expectedValue
	 */
	public void verifyContentofTableRecord(Map<Integer, Map<String, String>> uiData, String expectedValue) {
		Map<String, String> innerMap = new HashMap<>();
		for (int i = 0; i < uiData.size(); i++) {
			innerMap = uiData.get(i);
			CustomisedAssert.assertTrue(innerMap.containsValue(expectedValue));
		}
	}

	/**
	 * Verify the panel of Add Gift Card
	 */
	public void verifyAddGiftCardPanel() {
		foundation.click(BTN_ADD_GIFT_CARD);
		CustomisedAssert.assertTrue(foundation.isDisplayed(BTN_ADD_GIFT_CANCEL));
		CustomisedAssert.assertTrue(foundation.isDisplayed(BTN_ADD_GIFT_SAVE));
		CustomisedAssert.assertTrue(foundation.isDisplayed(HEADER));
		CustomisedAssert.assertTrue(foundation.isDisplayed(INPUT_TITLE));
		CustomisedAssert.assertTrue(foundation.isDisplayed(INPUT_AMOUNT));
		CustomisedAssert.assertTrue(foundation.isDisplayed(INPUT_EXPIRE_DATE));
		CustomisedAssert.assertTrue(foundation.isDisplayed(CHECK_BOX_NO_END_DATE));
	}

	/**
	 * Validate the Gift Card fields
	 * 
	 * @param title
	 * @param amount
	 * @param actual
	 */
	public void verifyGiftCardCreationFields(String title, String amount, String actual) {
		CustomisedAssert.assertTrue(foundation.isDisplayed(TITLE_ERROR));
		CustomisedAssert.assertTrue(foundation.isDisplayed(AMOUNT_ERROR));
		CustomisedAssert.assertTrue(foundation.isDisplayed(DATE_VALIDATION_ERROR));
		textBox.enterText(INPUT_TITLE, title);
		CustomisedAssert.assertTrue(foundation.isDisplayed(TITLE_ERROR));
		textBox.enterText(INPUT_AMOUNT, amount);
		CustomisedAssert.assertTrue(foundation.isDisplayed(AMOUNT_ERROR));
		textBox.enterText(INPUT_AMOUNT, actual);
		foundation.click(BTN_ADD_GIFT_CANCEL);
		foundation.threadWait(Constants.SHORT_TIME);
		CustomisedAssert.assertFalse(foundation.isDisplayed(BTN_ADD_GIFT_CANCEL));
	}

	/**
	 * Verify the Amount Field in Active Gift Card
	 * 
	 * @param uiTableData
	 * @param actual
	 * @param data
	 */
	public void verifyAmountFieldInGiftCard(Map<Integer, Map<String, String>> uiTableData, String actual, String data) {
		Map<String, String> innerMap = new HashMap<>();
		String innerValue = " ";
		Map<Integer, Map<String, String>> actualData = uiTableData;
		for (int i = 0; i < actualData.size(); i++) {
			innerMap = actualData.get(i);
			innerValue = innerMap.get(actual);
			CustomisedAssert.assertTrue(innerValue.contains(data));
		}
	}

	/**
	 * Verify the Issue Field in Active Gift Card
	 * 
	 * @param uiTableData
	 * @param actual
	 * @param data
	 */
	public void verifyIssueFieldInGiftCard(Map<Integer, Map<String, String>> uiTableData, String actual) {
		Map<String, String> innerMap = new HashMap<>();
		String innerValue = " ";
		Map<Integer, Map<String, String>> actualData = uiTableData;
		for (int i = 0; i < actualData.size(); i++) {
			innerMap = actualData.get(i);
			innerValue = innerMap.get(actual);
			CustomisedAssert.assertTrue(foundation.isNumeric(innerValue));
		}
	}

	/**
	 * Validate the Created Gift Card
	 * 
	 * @param title
	 */
	public void validateCreatedGiftCard(String title) {
		foundation.click(BTN_PRINT_FIRST_ROW);
		foundation.waitforElementToBeVisible(LBL_PRINT, Constants.SHORT_TIME);
		foundation.scrollIntoViewElement(LBL_PRINT);
		String innerValue = foundation.getText(LBL_PRINT);
		String[] value = innerValue.split("\\s");
		CustomisedAssert.assertEquals(value[1], title);
	}

	/**
	 * Validate the Barcode Structure in Printed Gift Card
	 * 
	 * @param content
	 * @param word
	 * @param letter
	 * @param characters
	 * @param number
	 * @param value
	 */
	public void validateBarCodeStructure(String content, String word, String letter, String characters, String number,
			String value) {
		String actual = foundation.getParticularWordFromSentence(content, Integer.valueOf(word));
		CustomisedAssert.assertTrue(actual.startsWith(letter));
		String s = characters;
		char c = s.charAt(0);
		int count = foundation.countOccurrencesofChar(actual, c);
		CustomisedAssert.assertEquals(String.valueOf(count), number);
		s = foundation.getNumbersFromString(actual);
		CustomisedAssert.assertTrue(foundation.isNumeric(s));
		CustomisedAssert.assertTrue(s.contains(value));
	}

	/**
	 * Verify the Issue Panel and its fields for Created Gift Card
	 * 
	 * @param title
	 */
	public void verifyIssuePanelOnCreatedGiftCard(String title) {
		foundation.click(BTN_ISSUE_FIRST_ROW);
		foundation.waitforElementToBeVisible(LBL_ISSUE, Constants.SHORT_TIME);
		foundation.scrollIntoViewElement(LBL_ISSUE);
		String innerValue = foundation.getText(LBL_ISSUE);
		String[] value = innerValue.split("\\s");
		CustomisedAssert.assertEquals(value[1], title);
		CustomisedAssert.assertTrue(foundation.isDisplayed(LBL_BY_LOCATION));
		CustomisedAssert.assertTrue(foundation.isDisplayed(LBL_BY_EMAIL));
	}

	/**
	 * Verify Issue Gift Card By Email Field
	 * 
	 * @param mail
	 */
	public void verifyIssueGiftCardByMail(String mail) {
		foundation.click(LBL_BY_EMAIL);
		foundation.waitforElementToBeVisible(BTN_EMAIL_CARDS, Constants.SHORT_TIME);
		CustomisedAssert.assertTrue(foundation.isDisplayed(BTN_EMAIL_CARDS));
		CustomisedAssert.assertTrue(foundation.isDisplayed(BTN_CANCEL_EMAIL));
		CustomisedAssert.assertTrue(foundation.isDisplayed(INPUT_EMAIL));
		textBox.enterText(INPUT_EMAIL, mail);
		foundation.click(BTN_EMAIL_CARDS);
		foundation.waitforElementToBeVisible(PAGE_TITLE, Constants.SHORT_TIME);
	}

	/**
	 * Validate Gift Card Expired Tab and its content
	 * 
	 * @param header
	 * @param actual
	 */
	public void validateGiftCardExpiredTabAndContent(String header, String actual) {
		foundation.click(TAB_EXPIRED);
		foundation.waitforElementToBeVisible(TXT_EXPIRED_TITLE, Constants.THREE_SECOND);
		List<String> datas = foundation.getTextofListElement(TBL_HEADERS_EXPIRED_GRID);
		CustomisedAssert.assertEquals(datas.get(2), header);
		datas = foundation.getTextofListElement(TBL_EXPIRED);
		String s = datas.get(0);
		String[] value = s.split("\\s");
		CustomisedAssert.assertTrue(value[0].matches("[a-zA-Z]+"));
		CustomisedAssert.assertTrue(value[1].contains(actual));
		value[2] = value[2].replaceAll("[^a-zA-Z0-9]+", "");
		CustomisedAssert.assertTrue(value[2].matches("[0-9]+"));
		CustomisedAssert.assertTrue(value[3].matches("[0-9]+"));

	}
}
