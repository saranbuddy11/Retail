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
	public static final By TXT_STATUS = By.xpath("//dt[text()='Show Active, Expired or All']");
	public static final By DPD_STATUS = By.id("filtervalues");
	public static final By BTN_CLOSE = By.xpath("//button[@class='ajs-close']");
	private static final By TBL_EXPIRED_GRID = By.cssSelector("#dt > tbody");
	private static final By TBL_EXPIRED = By.id("dt");

	private List<String> tableHeaders = new ArrayList<>();
	private Map<Integer, Map<String, String>> tableData = new LinkedHashMap<>();

	public By objExpirePinConfirmation(String location, String text) {
		return By.xpath("//td[text()='" + location + "']//..//td/button[text()='" + text + "']");
	}

	public By objExpiredPinlist(String text) {
		return By.xpath("//tr[@class='odd']/td[text()='" + text + "']");
	}

	public void verifyPinExpirationPrompt(String location, List<String> prompt, String status) {
		foundation.click(objExpirePinConfirmation(location, prompt.get(0)));
		CustomisedAssert.assertTrue(foundation.isDisplayed(TXT_PROMPT_MSG));
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
		foundation.objectClick(BTN_CREATE_PIN);
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
}
