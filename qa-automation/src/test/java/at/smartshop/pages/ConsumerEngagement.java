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
import at.framework.ui.TextBox;
import at.smartshop.keys.Constants;
import at.smartshop.tests.TestInfra;

public class ConsumerEngagement extends Factory {
	private Foundation foundation = new Foundation();
	private TextBox textBox = new TextBox();
	private DeviceSummary devicesummary=new DeviceSummary();

	public static final By PAGE_TITLE = By.id("Consumer Engagement");
	public static final By BTN_ADD_GIFT_CARD = By.id("giftcardBtn");
	public static final By LBL_HEADER = By.className("header-text");
	public static final By INPUT_TITLE = By.id("title");
	public static final By INPUT_AMOUNT = By.id("amount");
	public static final By INPUT_EXPIRE_DATE = By.id("expirationdate");
	public static final By BTN_SAVE = By.id("addgiftsavebtn");
	public static final By TBL_CONSUMER_ENGAGE_GRID = By.cssSelector("#consumerengageGrid > tbody");
	public static final By TBL_CONSUMER_ENGAGE = By.id("consumerengageGrid");
	public static final By BTN_PRINT_FIRST_ROW = By.cssSelector("#consumerengageGrid tr:nth-child(1) td:nth-child(2)");
	public static final By BTN_ISSUE_FIRST_ROW = By.cssSelector("#consumerengageGrid tr:nth-child(1) td:nth-child(1)");
	public static final By LBL_PRINT = By.id("titletoprint");
	public static final By INPUT_CARD_PRINT = By.id("cardstoprint");
	public static final By BTN_PRINT = By.id("printBtn");
	public static final By ADD_TO_NOTE = By.xpath("//dt[text()='Add a Note']");
	public static final By TXT_SEARCH = By.id("filterType");
	public static final By TBL_GRID = By.id("bylocationGrid");
	public static final By BTN_EMAIL_CARDS = By.id("issueemailbyemail");
	public static final By HEADER_ADDTONOTE=By.id("byemailaddnote");
	public static final By TBL_GMA_CONSUMER_ENGAGEMENT_GRID=By.cssSelector("#bylocationGrid > tbody");
	public static final By HEADER_GMA_CONSUMER_ENGAGEMENT=By.xpath("//table[@id='bylocationGrid']/thead");
	public static final By CHECKBOX_SELECTALL=By.id("itemcheckbox");
	public static final By CHECKBOX_GIFTCARD=By.xpath("//input[@class='commonloction']");
	public static final By RECORDS_CONSUMER_GRID=By.id("bylocationGrid_pager_label");
	public static final By TXT_ADD_TO_NOTE=By.id("issueaddnote");
	public static final By LOCATION_OF_RECIPIENTS=By.xpath("//div//h5");
	public static final By DPD_LOCATION=By.xpath("//div[@title='Show drop-down']");
	public static final By TXT_LOCATION_ENGAGEMENT=By.xpath("//input[@placeholder='Name of Location']");
	public static final By LOCATION_TAB=By.id("byloc");
	public static final By DPD_CLEAR=By.xpath("//div[@title='Clear value']");
	public static final By DPD_ALL_LOCATION=By.xpath("//li[@data-value='All Locations']");
	public static final By BY_EMAIL_FILTER=By.id("bymail");
	public static final By ENTER_RECIPIENT_EMAIL=By.xpath("//dt[contains(text(),'Enter Recipient Email')]");
	public static final By TXT_ENTER_RECIPIENT=By.id("recipientemail");
	public static final By BTN_BROWSE=By.name("file");
	public static final By BULK_EMAIL_CONSUMER=By.xpath("//b[text()='Bulk Email Consumers']");
	public static final By EGIFT_CARD_TEMPLATE=By.id("exportSample");
	public static final By TXT_DOWNLOAD_FILLOUTEMAIL=By.xpath("//li[contains(text(),'Download and fill')]");
	public static final By IMPORTANT_LINE=By.xpath("//li[contains(text(),'completed eGift Card Template file')]");
	public static final By ERROR_MSG=By.id("file-error");
	
	
	
	public By objSearchLocation(String location) {
		return By.xpath("//div[text()='" + location + "']");
	}
	

	private List<String> tableHeaders = new ArrayList<>();
	private Map<Integer, Map<String, String>> tableData = new LinkedHashMap<>();
	
	public By objGMAConsumerEngagementColumn(String header) {
		return By.xpath("//table[@id='bylocationGrid']//th/span[text()='" + header + "']");
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
		foundation.click(BTN_SAVE);
		foundation.waitforElementToBeVisible(BTN_ADD_GIFT_CARD, Constants.SHORT_TIME);
		foundation.threadWait(Constants.THREE_SECOND);
	}

	/**
	 * Getting Table Records from UI
	 * 
	 * @return
	 */
	public Map<Integer, Map<String, String>> getTblRecordsUI() {
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
	 * @param header
	 */
	public void verifyGMAConsumerEngagement(List<String> header) {
		try {
			foundation.waitforElement(HEADER_GMA_CONSUMER_ENGAGEMENT, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(HEADER_GMA_CONSUMER_ENGAGEMENT));
			CustomisedAssert.assertTrue(foundation.isDisplayed(objGMAConsumerEngagementColumn(header.get(1))));
			CustomisedAssert.assertTrue(foundation.isDisplayed(objGMAConsumerEngagementColumn(header.get(2))));
			CustomisedAssert.assertTrue(foundation.isDisplayed(objGMAConsumerEngagementColumn(header.get(3))));
			ExtFactory.getInstance().getExtent().log(Status.INFO,
					"Validated the GMA Consumer Engagement " + header);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
	
	/**
	 * verifying Grid data in ui
	 * @param header
	 * @param tabledata
	 */
	public void verifyColumnValuesInGrid(String header,String tabledata) {
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
	 * @param Object
	 * @param value
	 */
	public void verifyUserAbleToAddNoteFieldText(By Object, String inputText  ) {
		textBox.enterText(Object, inputText);
		foundation.threadWait(Constants.SHORT_TIME);
		String text = foundation.getTextAttribute(Object, "value");
		CustomisedAssert.assertEquals(text, inputText);
		foundation.waitforElementToBeVisible(ConsumerEngagement.TXT_SEARCH, Constants.SHORT_TIME);
	}
	
	/**
	 * verify SFE Section Options For MM Reload Method
	 * @param sectionName
	 * @param values
	 */
	public void verifySFESectionOptions(String sectionName,List<String> values) {
		for (int i = 0; i < values.size(); i++) {
			String groupData = foundation.getText(devicesummary.objSFEOptions(sectionName));
			String[] value = groupData.split("\\R");
			List<String> actual = Arrays.asList(value);
			CustomisedAssert.assertEquals(actual, values);	
			
		}
		
	}
}
