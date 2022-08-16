package at.smartshop.pages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.aventstack.extentreports.Status;

import at.framework.browser.Factory;
import at.framework.generic.CustomisedAssert;
import at.framework.reportsetup.ExtFactory;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.database.columns.CNProductSummary;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.tests.TestInfra;
import at.smartshop.v5.pages.AccountLogin;
import at.smartshop.v5.pages.LandingPage;
import at.smartshop.v5.pages.Payments;
import at.smartshop.v5.pages.ProductSearch;

public class ConsumerFeedbackSurvey extends Factory {
	private Foundation foundation = new Foundation();
	private TextBox textBox = new TextBox();
	private Dropdown dropdown = new Dropdown();
	
	private static final By REPORT_GRID_FIRST_ROW = By
			.cssSelector("#consumerFeedbackLocationLevel > tbody > tr:nth-child(1)");
	private static final By NO_DATA_AVAILABLE_IN_TABLE = By.xpath("//td[@class='dataTables_empty']");

	public static final By LOCATION_TOGGLEINFO_BAR = By.xpath("//button[@id='toggleinfo']");
	public static final By CONSUMER_FEEDBACK_SUMMARY_ENABLE_DD = By.xpath("//select[@id='hasenabledeviceconsumer']");
	public static final By LOCATION_SUMMARY_SAVE_BUTTON = By.xpath("//button[@id='saveBtn']");
	public static final By TXT_SPINNER_MSG = By.xpath("//div[@class='humane humane-libnotify-info']");
	public static final By CONSUMER_FEEDBACK_QUESTION = By.xpath("//input[@id='question']");
//	public static final By FEEDBACK_QUESTION = By.id("feedback-question");
	public static final By FEEDBACK_QUESTION = By.cssSelector("#feedback-question > h3");

	private static final By TBL_CONSUMER_FEEDBACK_SUMMARY = By.id("consumerFeedbackLocationLevel");
	private static final By TBL_CONSUMER_FEEDBACK_SUMMARY_GRID = By
			.cssSelector("#consumerFeedbackLocationLevel > tbody");
	public static final By HAPPY_EMOJI_BTN = By.id("happy-btn-id");
	public static final By SAD_EMOJI_BTN = By.id("sad-btn-id");
	public static final By NORMAL_EMOJI_BTN = By.id("normal-btn-id");

	private List<String> tableHeaders = new ArrayList<>();
	private Map<Integer, Map<String, String>> reportsData = new LinkedHashMap<>();
	private Map<Integer, Map<String, String>> intialData = new LinkedHashMap<>();

	public void checkForDataAvailabilyInResultTable() {
		try {
			if (foundation.isDisplayed(REPORT_GRID_FIRST_ROW)) {
				if (foundation.isDisplayed(NO_DATA_AVAILABLE_IN_TABLE)) {
					ExtFactory.getInstance().getExtent().log(Status.INFO, "No Data Available in Report Table");
					Assert.fail("Failed Report because No Data Available in Report Table");
				} else {
					ExtFactory.getInstance().getExtent().log(Status.INFO,
							"Report Data Available in the Table, Hence passing the Test case");
				}
			} else {
				ExtFactory.getInstance().getExtent().log(Status.INFO, "No Report Table Available");
				Assert.fail("Failed Report because No Report Table Available");
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public Map<Integer, Map<String, String>> getTblRecordsUI() {
		try {
			int recordCount = 0;
			tableHeaders.clear();
			WebElement tableReportsList = getDriver().findElement(TBL_CONSUMER_FEEDBACK_SUMMARY_GRID);
			WebElement tableReports = getDriver().findElement(TBL_CONSUMER_FEEDBACK_SUMMARY);
			List<WebElement> columnHeaders = tableReports.findElements(By.cssSelector("thead > tr > th"));
			List<WebElement> rows = tableReportsList.findElements(By.tagName("tr"));
			for (WebElement columnHeader : columnHeaders) {
				tableHeaders.add(columnHeader.getText());
			}
			for (WebElement row : rows) {
				Map<String, String> uiTblRowValues = new LinkedHashMap<>();
				for (int columnCount = 1; columnCount < tableHeaders.size() + 1; columnCount++) {
					WebElement column = row.findElement(By.cssSelector("td:nth-child(" + columnCount + ")"));
					uiTblRowValues.put(tableHeaders.get(columnCount - 1), column.getText());
				}
				reportsData.put(recordCount, uiTblRowValues);
				recordCount++;
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		return reportsData;
	}

	public void updateData(String columnName, String values) {
		try {
			intialData.get(0).put(columnName, values);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void updateTotalFeedbackCount(String columnName) {
		try {
			String initialCount = intialData.get(0).get(columnName);
			int updatedCount = Integer.parseInt(initialCount) + 3;
			intialData.get(0).put(columnName, String.valueOf(updatedCount));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void updateCount(String columnName) {
		try {
			String initialCount = intialData.get(0).get(columnName);
			int updatedCount = Integer.parseInt(initialCount) + 1;
			intialData.get(0).put(columnName, String.valueOf(updatedCount));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void verifyReportHeaders(String columnNames) {
		try {
			List<String> columnName = Arrays.asList(columnNames.split(Constants.DELIMITER_HASH));
			for (int iter = 0; iter < tableHeaders.size(); iter++) {
				Assert.assertTrue(tableHeaders.get(iter).equals(columnName.get(iter)));
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void verifyReportData() {
		try {
			for (int iter = 0; iter < tableHeaders.size(); iter++) {
				CustomisedAssert.assertTrue(reportsData.get(0).get(tableHeaders.get(iter))
						.contains(intialData.get(0).get(tableHeaders.get(iter))));
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void transactionThroughDevice(String product, String paymentEmail, String PIN, String feedbackQestion, By emojiObject,  By descriptionObject){
		try {
			CustomisedAssert.assertTrue(foundation.isDisplayed(LandingPage.IMG_SEARCH_ICON));
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadTextWithCaseSensitive(product);
			foundation.click(ProductSearch.BTN_PRODUCT);
			foundation.click(Payments.ACCOUNT_EMAIL);
//			foundation.waitforElement(Payments.EMAIL_lOGIN_BTN, Constants.ONE_SECOND);
//			foundation.click(Payments.EMAIL_lOGIN_BTN);
			foundation.threadWait(Constants.ONE_SECOND);
			textBox.enterKeypadTextWithCaseSensitive(paymentEmail);
			foundation.click(AccountLogin.BTN_NEXT);
			foundation.waitforElement(AccountLogin.BTN_PIN_NEXT, Constants.SHORT_TIME);
			foundation.threadWait(Constants.ONE_SECOND);
			textBox.enterPin(PIN);
			foundation.click(AccountLogin.BTN_PIN_NEXT);
			CustomisedAssert.assertTrue(
					foundation.getText(ConsumerFeedbackSurvey.FEEDBACK_QUESTION).contains(feedbackQestion));
			foundation.click(emojiObject);
			CustomisedAssert.assertTrue(foundation.isDisplayed(descriptionObject));
//			browser.close();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public Map<Integer, Map<String, String>> getIntialData() {
		return intialData;
	}

	public Map<Integer, Map<String, String>> getReportsData() {
		return reportsData;
	}

	public List<String> getTableHeaders() {
		return tableHeaders;
	}
}
