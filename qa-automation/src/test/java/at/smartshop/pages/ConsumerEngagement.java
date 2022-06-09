package at.smartshop.pages;

import java.util.ArrayList;
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
	public static final By BTN_ADD_GIFT_CARD = By.id("giftcardBtn");
	public static final By LBL_HEADER = By.className("header-text");
	public static final By INPUT_TITLE = By.id("title");
	public static final By INPUT_AMOUNT = By.id("amount");
	public static final By INPUT_EXPIRE_DATE = By.id("expirationdate");
	public static final By BTN_SAVE = By.id("addgiftsavebtn");
	public static final By TBL_CONSUMER_ENGAGE_GRID = By.cssSelector("#consumerengageGrid > tbody");
	public static final By TBL_CONSUMER_ENGAGE = By.id("consumerengageGrid");
	public static final By BTN_PRINT_FIRST_ROW = By.cssSelector("#consumerengageGrid tr:nth-child(1) td:nth-child(2)");
	public static final By LBL_PRINT = By.id("titletoprint");
	public static final By INPUT_CARD_PRINT = By.id("cardstoprint");
	public static final By BTN_PRINT = By.id("printBtn");

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
		foundation.click(BTN_SAVE);
		foundation.waitforElementToBeVisible(BTN_ADD_GIFT_CARD, Constants.SHORT_TIME);
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

}
