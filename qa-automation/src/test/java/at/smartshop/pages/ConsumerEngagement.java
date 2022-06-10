package at.smartshop.pages;

import java.util.ArrayList;
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
	public static final By TBL_GMA_CONSUMER_ENGAGEMENT_GRID=By.cssSelector("#bylocationGrid > tbody");
	public static final By HEADER_GMA_CONSUMER_ENGAGEMENT=By.xpath("//table[@id='bylocationGrid']/thead");
	public static final By CHECKBOX_SELECTALL=By.id("itemcheckbox");
	public static final By CHECKBOX_GIFTCARD=By.xpath("//input[@class='commonloction']");
	public static final By RECORDS_CONSUMER_GRID=By.id("bylocationGrid_pager_label");
	public static final By TXT_ADD_TO_NOTE=By.id("issueaddnote");
	public static final By LOCATION_OF_RECIPIENTS=By.xpath("//div//h5");
	public static final By DPD_LOCATION=By.cssSelector("#comboTarget > div");
	

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
	 * Getting Table Records from UI
	 * 
	 * @return
	 */
	public Map<Integer, Map<String, String>> getTableRecordsUI() {
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
}
