package at.smartshop.pages;

import java.awt.AWTException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.aventstack.extentreports.Status;

import at.framework.browser.Browser;
import at.framework.browser.Factory;
import at.framework.generic.CustomisedAssert;
import at.framework.reportsetup.ExtFactory;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.tests.TestInfra;
import at.smartshop.v5.pages.AccountLogin;
import at.smartshop.v5.pages.LandingPage;
import at.smartshop.v5.pages.Order;
import at.smartshop.v5.pages.Payments;
import at.smartshop.v5.pages.ProductSearch;

public class PayrollDeductDetails extends Factory {
	private Foundation foundation = new Foundation();
	private NavigationBar navigationBar = new NavigationBar();
	private TextBox textBox = new TextBox();
	private Dropdown dropdown = new Dropdown();
	public Browser browser = new Browser();
	private Order order = new Order();
	public Login login = new Login();
	private ReportList reportList = new ReportList();

	public static final By LBL_REPORT_NAME = By.id("Payroll Deduct Details");
	private static final By REPORT_GRID_FIRST_ROW = By
			.cssSelector("#dataGridPayrollDeductDetails > tbody > tr:nth-child(1)");
	private static final By NO_DATA_AVAILABLE_IN_TABLE = By.xpath("//td[@class='dataTables_empty']");
	public static final By DATA_EXISTING_DATE_START = By.cssSelector(
			"body > div.daterangepicker.ltr.show-ranges.opensright.show-calendar > div.drp-calendar.right > div.calendar-table > table > tbody > tr:nth-child(2) > td:nth-child(2)");
	public static final By DATA_EXISTING_DATE_END = By.cssSelector(
			"body > div.daterangepicker.ltr.show-ranges.opensright.show-calendar > div.drp-calendar.right > div.calendar-table > table > tbody > tr:nth-child(2) > td:nth-child(2)");
	private static final By TABLE = By.cssSelector("#dataGridPayrollDeductDetails");
	private static final By TABLE_GRID = By.cssSelector("#dataGridPayrollDeductDetails>tbody");
	private static final By DPD_LOCATION = By.id("select2-locdt-container");
	public final static By TXT_SEARCH_FILTER = By.cssSelector("div.ui-igeditor-input-container>#filterType");

	private Map<Integer, Map<String, String>> reportsData = new LinkedHashMap<>();
	private List<String> tableHeaders = new ArrayList<>();

	public void verifyReportName(String reportName) {
		try {
			foundation.waitforElement(LBL_REPORT_NAME, Constants.EXTRA_LONG_TIME);
			String reportTitle = foundation.getText(LBL_REPORT_NAME);
			Assert.assertTrue(reportTitle.contains(reportName));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

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

	/**
	 * Read ConsumerID and details from Consumer Summary Page
	 * 
	 * @param menu
	 * @param keyword
	 * @param location
	 * @return
	 */
	public List<String> readConsumerDetails(String menu, String keyword, String location, String bal, String reason) {
		foundation.threadWait(Constants.SHORT_TIME);
		List<String> details = new ArrayList<String>();
		navigationBar.navigateToMenuItem(menu);
		CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSearch.TXT_CONSUMER_SEARCH));
		foundation.click(ConsumerSearch.CLEAR_SEARCH);
		textBox.enterText(ConsumerSearch.TXT_SEARCH, keyword);
		dropdown.selectItem(ConsumerSearch.DPD_LOCATION, location, Constants.TEXT);
		foundation.click(ConsumerSearch.BTN_GO);
		foundation.scrollIntoViewElement(ConsumerSearch.TBL_CONSUMERS);
		foundation.threadWait(Constants.SHORT_TIME);
		CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSearch.TBL_CONSUMERS));
		foundation.click(ConsumerSearch.LNK_FIRST_ROW);
		foundation.threadWait(Constants.THREE_SECOND);
		String lName = textBox.getTextFromInput(ConsumerSearch.TXT_LAST_NAME);
		String payrollID = textBox.getTextFromInput(ConsumerSearch.TXT_PAYROLL_ID);
		String payrollGroup = dropdown.getSelectedItem(ConsumerSearch.DPD_PAY_CYCLE);
		details.add(lName);
		details.add(payrollID);
		details.add(payrollGroup);
		foundation.click(ConsumerSummary.PAYROLL_BTN_ADJUST);
		foundation.waitforElement(ConsumerSummary.LBL_POPUP_ADJUST_BALANCE, Constants.SHORT_TIME);
		textBox.enterText(ConsumerSummary.TXT_ADJUST_BALANCE, bal);
		dropdown.selectItem(ConsumerSummary.DPD_REASON, reason, Constants.TEXT);
		foundation.click(ConsumerSummary.BTN_REASON_SAVE);
		foundation.waitforElement(ConsumerSummary.SUCCESS_MESSAGE, Constants.SHORT_TIME);
		foundation.click(ConsumerSummary.BTN_SAVE);
		foundation.waitforElement(LocationList.TXT_RECORD_UPDATE_MSG, Constants.SHORT_TIME);
		return details;
	}

	/**
	 * Read Price of Transaction on V5 Device
	 * 
	 * @param product
	 * @param orderPage
	 * @param mail
	 * @param pin
	 * @param msg
	 * @return
	 * @throws ParseException
	 */
	public String readPriceFromV5Transaction(String product, String orderPage, String mail, String pin, String msg)
			throws ParseException {
		browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
		CustomisedAssert.assertTrue(foundation.isDisplayed(LandingPage.IMG_SEARCH_ICON));
		foundation.click(LandingPage.IMG_SEARCH_ICON);
		foundation.click(AccountLogin.BTN_CAMELCASE);
		textBox.enterKeypadText(product);
		foundation.click(ProductSearch.BTN_PRODUCT);
		String price = foundation.getText(ProductSearch.PRODUCT_PRICE);
		CustomisedAssert.assertTrue(foundation.isDisplayed(order.objText(orderPage)));
		foundation.click(Payments.EMAIL_ACCOUNT);
		foundation.waitforElement(Payments.BTN_EMAIL_LOGIN, Constants.ONE_SECOND);
		foundation.click(Payments.BTN_EMAIL_LOGIN);
		foundation.waitforElement(Payments.EMAIL_LOGIN_TXT, Constants.ONE_SECOND);
		foundation.click(Payments.EMAIL_LOGIN_TXT);
		foundation.threadWait(Constants.ONE_SECOND);
		foundation.click(AccountLogin.BTN_CAMELCASE);
		textBox.enterKeypadText(mail);
		foundation.click(AccountLogin.BTN_NEXT);
		foundation.waitforElement(AccountLogin.BTN_PIN_NEXT, Constants.SHORT_TIME);
		foundation.threadWait(Constants.THREE_SECOND);
		textBox.enterPin(pin);
		foundation.click(AccountLogin.BTN_PIN_NEXT);
		CustomisedAssert.assertTrue(foundation.isDisplayed(order.objText(msg)));
		foundation.threadWait(Constants.THREE_SECOND);
		return price;
	}

	/**
	 * Login ADM with Super Credentials
	 */
	public void loginToADMWithSuperCredentials() {
		browser.launch(Constants.LOCAL, Constants.CHROME);
		browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
		login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
				propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
		CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));
		navigationBar.selectOrganization(
				propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
	}

	/**
	 * Read Transaction ID from Admin > Consumer
	 * 
	 * @param menu
	 * @return
	 * @throws AWTException
	 */
	public String readTransactionID(String menu, String keyword, String location) throws AWTException {
		navigationBar.navigateToMenuItem(menu);
		CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSearch.TXT_CONSUMER_SEARCH));
		foundation.click(ConsumerSearch.CLEAR_SEARCH);
		textBox.enterText(ConsumerSearch.TXT_SEARCH, keyword);
		dropdown.selectItem(ConsumerSearch.DPD_LOCATION, location, Constants.TEXT);
		foundation.click(ConsumerSearch.BTN_GO);
		foundation.scrollIntoViewElement(ConsumerSearch.TBL_CONSUMERS);
		foundation.threadWait(Constants.SHORT_TIME);
		CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerSearch.TBL_CONSUMERS));
		foundation.click(ConsumerSearch.LNK_FIRST_ROW);
		foundation.threadWait(Constants.THREE_SECOND);
		foundation.scrollIntoViewElement(ConsumerSearch.BAL_HISTORY_SEARCH);
		foundation.threadWait(Constants.MEDIUM_TIME);
		List<WebElement> transac = Foundation.getDriver().findElements(ConsumerSearch.TRANSACTION_ID);
		String txnId = transac.get(1).getText();
		return txnId;
	}

	/**
	 * Select the report with Date range and Location to run the report
	 * 
	 * @param reportName
	 * @param date
	 * @param location
	 */
	public void selectAndRunReport(String menu, String reportName, String date, String location, String transID) {
		navigationBar.navigateToMenuItem(menu);
		reportList.selectReport(reportName);
		reportList.selectDate(date);
		foundation.threadWait(Constants.SHORT_TIME);
		selectLocation(location);
		foundation.threadWait(Constants.SHORT_TIME);
		foundation.waitforClikableElement(ReportList.BTN_RUN_REPORT, Constants.SHORT_TIME);
		foundation.click(ReportList.BTN_RUN_REPORT);
		foundation.waitforElement(ProductSales.LBL_REPORT_NAME, Constants.SHORT_TIME);
		verifyReportName(reportName);
		checkForDataAvailabilyInResultTable();
		textBox.enterText(TXT_SEARCH_FILTER, transID);
		foundation.threadWait(Constants.SHORT_TIME);
	}

	/**
	 * Get Table records from UI
	 * 
	 * @return
	 */
	public Map<Integer, Map<String, String>> getTblRecordsUI() {
		try {
			int recordCount = 0;
			tableHeaders.clear();
			WebElement tableReportsList = getDriver().findElement(TABLE_GRID);
			WebElement tableReports = getDriver().findElement(TABLE);
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

	/**
	 * Verify the Report Headers
	 * 
	 * @param columnNames
	 */
	public void verifyReportHeaders(String columnNames) {
		try {
			List<String> columnName = Arrays.asList(columnNames.split(Constants.DELIMITER_HASH));
			foundation.threadWait(Constants.SHORT_TIME);
			for (int iter = 0; iter < tableHeaders.size(); iter++) {
				CustomisedAssert.assertTrue(tableHeaders.get(iter).equals(columnName.get(iter)));
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * Verify the Common value content of Table Record
	 * 
	 * @param uiTableData
	 * @param columnName
	 * @param data
	 */
	public void verifyCommonValueContentofTableRecord(String columnName, String value) {
		Map<String, String> innerMap = new HashMap<>();
		String innerValue = " ";
		try {
			for (int i = 0; i < reportsData.size(); i++) {
				innerMap = reportsData.get(i);
				innerValue = innerMap.get(columnName);
				CustomisedAssert.assertTrue(innerValue.contains(value));
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * Select location
	 * 
	 * @param locationName
	 */
	public void selectLocation(String locationName) {
		foundation.click(DPD_LOCATION);
		foundation.click(By.xpath("//ul[@id='select2-locdt-results']/li[text()='" + locationName + "']"));
	}
}
