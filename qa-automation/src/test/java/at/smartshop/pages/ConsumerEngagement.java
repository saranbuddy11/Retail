package at.smartshop.pages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import com.aventstack.extentreports.Status;
import at.framework.browser.Factory;
import at.framework.generic.CustomisedAssert;
import at.framework.reportsetup.ExtFactory;
import at.framework.ui.Foundation;
import at.framework.ui.Table;
import at.framework.ui.TextBox;
import at.smartshop.keys.Constants;
import at.smartshop.tests.TestInfra;

public class ConsumerEngagement extends Factory {
	private Foundation foundation = new Foundation();
	private TextBox textBox = new TextBox();
	private DeviceSummary devicesummary = new DeviceSummary();
	private Table table = new Table();

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
	public static final By ADD_TO_NOTE = By.xpath("//dt[text()='Add a Note']");
	public static final By TXT_SEARCH = By.id("filterType");
	public static final By TBL_GRID = By.id("bylocationGrid");
	public static final By TBL_GMA_CONSUMER_ENGAGEMENT_GRID = By.cssSelector("#bylocationGrid > tbody");
	public static final By HEADER_GMA_CONSUMER_ENGAGEMENT = By.xpath("//table[@id='bylocationGrid']/thead");
	public static final By CHECKBOX_SELECTALL = By.cssSelector("thead>tr>th>span>span.ui-igcheckbox-normal-off");
	public static final By CHECKBOX_GIFTCARD = By.xpath("//input[@class='commonloction']");
	public static final By RECORDS_CONSUMER_GRID = By.id("bylocationGrid_pager_label");
	public static final By TXT_ADD_TO_NOTE = By.id("issueaddnote");
	public static final By LOCATION_OF_RECIPIENTS = By.xpath("//div//h5");
	public static final By DPD_LOCATION = By.xpath("//div[@title='Show drop-down']");
	public static final By TXT_LOCATION_ENGAGEMENT = By.xpath("//input[@placeholder='Name of Location']");
	public static final By LOCATION_TAB = By.id("byloc");
	public static final By DPD_CLEAR = By.xpath("//div[@title='Clear value']");
	public static final By DPD_ALL_LOCATION = By.xpath("//li[@data-value='All Locations']");

	public static final By BTN_PrintScreen_Cancel = By.xpath("//button[@id='printtitlecancel']");
	public static final By Print_Panel = By.xpath("//div[@class='container-fluid printtitlecard-main']");
	public static final By BTN_PrintScreen_Print = By.xpath("//button[@id='printBtn']");
	public static final By INPUT_CardToPrint = By.xpath("//input[@id='cardstoprint']");
	public static final By INPUT_AddNote_PrintScreen = By.xpath("//input[@id='addnote']");
	public static final By TXT_ErrorLabel_CardsToPrint = By.xpath("//label[@id='cardstoprint-error']");

	public static final By BTN_EMAIL_CARDS = By.id("issueemailbyemail");
	public static final By HEADER_ADDTONOTE = By.id("byemailaddnote");
	public static final By BY_EMAIL_FILTER = By.id("bymail");
	public static final By ENTER_RECIPIENT_EMAIL = By.xpath("//dt[contains(text(),'Enter Recipient Email')]");
	public static final By TXT_ENTER_RECIPIENT = By.id("recipientemail");
	public static final By BTN_BROWSE = By.name("file");
	public static final By BULK_EMAIL_CONSUMER = By.xpath("//b[text()='Bulk Email Consumers']");
	public static final By EGIFT_CARD_TEMPLATE = By.id("exportSample");
	public static final By TXT_DOWNLOAD_FILLOUTEMAIL = By.xpath("//li[contains(text(),'Download and fill')]");
	public static final By IMPORTANT_LINE = By.xpath("//li[contains(text(),'completed eGift Card Template file')]");
	public static final By ERROR_MSG = By.id("file-error");
	public static final By TAB_BY_LOCATION = By.id("byloc");
	public static final By TAB_BY_EMAIL = By.id("bymail");
	public static final By ADD_TO_NOTE_BY_EMAIL = By.id("byemailaddnote");
	public static final By TXT_ADMIN_TAB = By.xpath("//a[text()='Admin']");
	public static final By TAB_ACTIVE = By.id("activetab");
	public static final By TAB_EXPIRED = By.id("expiredtab");
	public static final By TXT_EXPIRED_TITLE = By.className("ui-iggrid-headertext");
	public static final By BTN_ISSUE = By.cssSelector("td[aria-describedby='consumerengageGrid_Issue']");
	public static final By TBL_EXPIRED = By.cssSelector("tr[data-id='undefined']");
	public static final By LBL_BY_LOCATION = By.id("byloc");
	public static final By LBL_BY_EMAIL = By.id("bymail");
	public static final By BTN_CANCEL_EMAIL = By.id("issuebyemailCancel");
	public static final By INPUT_EMAIL = By.id("recipientemail");
	public static final By BTN_ADD_GIFT_CANCEL = By.id("addgiftcancelbtn");
	public static final By TITLE_ERROR = By.id("title-error");
	public static final By AMOUNT_ERROR = By.id("amount-error");
	public static final By DATE_VALIDATION_ERROR = By.id("expiredDateValid");
	public static final By BTN_OK = By.cssSelector(".ajs-ok");

	public By objSearchLocation(String location) {
		return By.xpath("//div[text()='" + location + "']");
	}

	private List<String> tableHeaders = new ArrayList<>();
	private Map<Integer, Map<String, String>> tableData = new LinkedHashMap<>();

	public By objGMAConsumerEngagementColumn(String header) {
		return By.xpath("//table[@id='bylocationGrid']//th/span[text()='" + header + "']");
	}

	public By selectTabName(String tab) {
		return By.xpath("//a[text()='" + tab + "']");
	}

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
		foundation.threadWait(Constants.SHORT_TIME);
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
	 * verify GMA Consumer Engagement Table Records
	 * 
	 * @return
	 */
	public Map<Integer, Map<String, String>> verifyGMAConsumerEngagementTableRecords() {
		try {
			int recordCount = 0;
			tableHeaders.clear();
			WebElement tableList = getDriver().findElement(TBL_GMA_CONSUMER_ENGAGEMENT_GRID);
			WebElement table = getDriver().findElement(TBL_GRID);
			List<WebElement> columnHeaders = table.findElements(By.cssSelector("thead > tr[role='row'] > th"));
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
	 * verifying headers in GMA Consumer engagement grid
	 * 
	 * @param header
	 */
	public void verifyGMAConsumerEngagement(List<String> header) {
		try {
			foundation.waitforElement(HEADER_GMA_CONSUMER_ENGAGEMENT, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(HEADER_GMA_CONSUMER_ENGAGEMENT));
			CustomisedAssert.assertTrue(foundation.isDisplayed(objGMAConsumerEngagementColumn(header.get(1))));
			CustomisedAssert.assertTrue(foundation.isDisplayed(objGMAConsumerEngagementColumn(header.get(2))));
			CustomisedAssert.assertTrue(foundation.isDisplayed(objGMAConsumerEngagementColumn(header.get(3))));
			ExtFactory.getInstance().getExtent().log(Status.INFO, "Validated the GMA Consumer Engagement " + header);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * verifying Grid data in ui
	 * 
	 * @param header
	 * @param tabledata
	 */
	public void verifyColumnValuesInGrid(String header, String tabledata) {
		Map<Integer, Map<String, String>> uiTableData = verifyGMAConsumerEngagementTableRecords();
		Map<String, String> innerMap = new HashMap<>();
		String innerValue = "";
		for (int i = 0; i < uiTableData.size(); i++) {
			innerMap = uiTableData.get(i);
			innerValue = innerMap.get(header);
			CustomisedAssert.assertEquals(innerValue, tabledata);
		}
		uiTableData.clear();
	}

	/**
	 * verify User Able To Add Note Field Text
	 * 
	 * @param Object
	 * @param value
	 */
	public void verifyUserAbleToAddNoteFieldText(By Object, String inputText) {
		textBox.enterText(Object, inputText);
		foundation.threadWait(Constants.SHORT_TIME);
		String text = foundation.getTextAttribute(Object, "value");
		CustomisedAssert.assertEquals(text, inputText);
		foundation.waitforElementToBeVisible(ConsumerEngagement.TXT_SEARCH, Constants.SHORT_TIME);
	}

	/**
	 * verify SFE Section Options For MM Reload Method
	 * 
	 * @param sectionName
	 * @param values
	 */
	public void verifySFESectionOptions(String sectionName, List<String> values) {
		for (int i = 0; i < values.size(); i++) {
			String groupData = foundation.getText(devicesummary.objSFEOptions(sectionName));
			String[] value = groupData.split("\\R");
			List<String> actual = Arrays.asList(value);
			CustomisedAssert.assertEquals(actual, values);
		}
	}

	public By getFeatureInRolePermission(String text) {
		return By.xpath(
				"//table[contains(@class,'table-striped')]//td[text()='" + text + "']/following-sibling::td/input");
	}

	/**
	 * Verify All checkboxes status under User and Roles Page
	 * 
	 * @param text
	 * @param expected
	 */
	public void verifyAllCheckboxesStatus(String text, String expected) {
		try {
			foundation.scrollIntoViewElement(getFeatureInRolePermission(text));
			List<String> list = foundation.getAttributeValueofListElement(getFeatureInRolePermission(text), "checked");
			boolean response = false;
			for (int i = 0; i < list.size(); i++) {
				response = list.get(i).equals(expected);
				if (!response) {
					break;
				}
			}
			CustomisedAssert.assertTrue(response);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * Verify Role Permission not Present
	 * 
	 * @param Role
	 */
	public void verifyRolePermissionNotPresent(String Role) {
		CustomisedAssert.assertFalse(foundation.isDisplayed(getFeatureInRolePermission(Role)));

	}

	public void searchUserRolesAndNavigateToRolePermissions(String text, String tab) {
		textBox.enterText(UserList.TXT_SEARCH_ROLE, text);
		table.selectRow(text);
		CustomisedAssert.assertTrue(foundation.isDisplayed(selectTabName(tab)));
		foundation.click(selectTabName(tab));
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
		foundation.waitforElementToBeVisible(BTN_OK, Constants.SHORT_TIME);
		foundation.click(BTN_OK);
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
