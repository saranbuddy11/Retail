package at.smartshop.pages;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import at.framework.browser.Factory;
import at.framework.generic.CustomisedAssert;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.Table;
import at.framework.ui.TextBox;
import at.smartshop.database.columns.CNNationalAccounts;
import at.smartshop.keys.Constants;

public class NationalAccounts extends Factory {

	public static final By BTN_CREATE = By.xpath("//button[text()='Create New']");
	public static final By TXT_ACCOUNT_NAME = By.id("name");
	public static final By DPD_CLIENT_NAME = By.id("nationalclient");
	public static final By BTN_SAVE = By.xpath("//button[text()='Save']");
	public static final By BTN_CANCEL = By.xpath("//button[text()='Cancel']");
	public static final By BTN_OK = By.xpath("//button[text()='OK']");
	public static final By TXT_FILTER = By.id("filterType");
	public static final By TBL_BODY = By.cssSelector("#dataGrid > tbody");
	public static final By BTN_POP_UP_YES = By.xpath("//button[text()='YES']");
	public static final By LBL_NATIONAL_ACCOUNT = By.id("National Accounts");
	public static final By TBL_ROW = By.cssSelector("#dataGrid > tbody >tr");
	public static final By LBL_ORGINIZATION = By.cssSelector("td[aria-describedby=nationalAccountsSummaryGrid_org]");
	public static final By LBL_LOCATION = By.cssSelector("td[aria-describedby=nationalAccountsSummaryGrid_location]");
	public static final By ICO_DELETE = By.cssSelector("td[aria-describedby=dataGrid_delete] > a");
	public static final By DPD_ORGANIZATION = By.id("org");
	public static final By DPD_LOCATION = By.id("locs");
	public static final By BTN_ADD_NATIONAL_ACCOUNT = By.id("nationalAccountBtn");
	public static final By TBL_HEADERS = By.xpath("//table//thead//tr//th");
	public static final By BTN_POPUP_YES = By.xpath("//button[text()='YES']");
	public static final By ICON_DELETE = By.xpath("//a[@class='fa fa-trash icon']");
	public static final By LBL_ORGANIZATION = By
			.cssSelector("#nationalAccountsSummaryGrid > tbody > tr > td:nth-child(1)");
	public static final By LBL_CITY = By.cssSelector("#nationalAccountsSummaryGrid > tbody > tr > td:nth-child(3)");
	public static final By LBL_STATE = By.cssSelector("#nationalAccountsSummaryGrid > tbody > tr > td:nth-child(4)");
	public static final By BTN_Cancel = By.xpath("//button[text()='CANCEL']");
	public static final By TXT_ALERT_MSG = By.id("alertifyMessage");
	public static final By TXT_ALERT_CONTENT = By.xpath("//div[@class='ajs-content']");
	public static final By LBL_NATIONAL_ACCOUNT_SUMMARY = By.id("National Account Summary");
	public static final By LBL_NATIONAL_ACCOUNT_ERROR = By.id("name-error");
	public static final By LBL_CLIENT_ERROR = By.id("nationalclient-error");
	public static final By LBL_EXISTING_ERROR = By.xpath("//p[@id='alertifyForExisitError']");
	public static final By BTN_POPUP_ACCOUNT_ADDED = By.id("toast");
	public static final By BTN_ACCEPT_POPUP = By.xpath("//button[@class='ajs-button ajs-ok']");

	public List<String> nationalAccountsHeadersList = new ArrayList<>();
	private Dropdown dropDown = new Dropdown();
	private TextBox textBox = new TextBox();
	private Table table = new Table();
	private Map<String, String> rstNationalAccountsData;

	private Foundation foundation = new Foundation();

	public By nationalAccountDetails(String accountName) {
		By element = null;
		try {
			element = By.xpath("//span[contains(text(),'" + accountName + "')]");
		} catch (Exception exc) {
			exc.printStackTrace();
			Assert.fail();
		}
		return element;
	}

	public void verifyNationalAccountsSummaryColumns(String headerList) throws Exception {
		getNationalAccountSummaryTableHeader();
		int count = nationalAccountsHeadersList.size();
		for (int iter = 0; iter < count; iter++) {
			CustomisedAssert.assertTrue(headerList.contains(nationalAccountsHeadersList.get(iter)));
		}
	}

	public void getNationalAccountSummaryTableHeader() throws Exception {
		nationalAccountsHeadersList.clear();

		List<WebElement> headers = getDriver().findElements(By.xpath("//table//thead//tr//th"));
		for (WebElement header : headers) {
			if (!header.getAttribute("aria-label").equals("")) {
				nationalAccountsHeadersList.add(header.getAttribute("aria-label"));
			}
		}
	}

	public void verifyNationalAccountSummaryTableBody(String text) throws Exception {
		try {
			Boolean isdisplayed = (getDriver().findElement(By.xpath("//table//tbody//tr//td[text()='" + text + "']")))
					.isDisplayed();
			CustomisedAssert.assertTrue(isdisplayed);
		} catch (Exception exc) {
			exc.printStackTrace();
			Assert.fail();
		}
	}

	public void verifyBackgroundColour(String location, String expctedlcolor) {
		foundation.threadWait(Constants.SHORT_TIME);
		By locationElement = By.xpath("//select[@name='locs']//option[text()='" + location + "']");
		String actualColor = getDriver().findElement(locationElement).getAttribute("style");
		CustomisedAssert.assertEquals(actualColor, expctedlcolor);
	}

	public boolean trySelectNonVisibleTextLocation(String text) {
		try {
			Select select = new Select(getDriver().findElement(LBL_LOCATION));
			select.selectByVisibleText(text);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}

	public void verifyPromptMsg(String message, String existsMsg) {
		try {

			By TXT_ALREADYEXISTS = By.xpath("//b[normalize-space(text())='" + existsMsg + "']");
			Boolean status1 = foundation.isDisplayed(TXT_ALREADYEXISTS);
			CustomisedAssert.assertTrue(status1);
			By TXT_ALREADYEXIST_MSG = By.xpath("//p[normalize-space(text()) ='" + message + "']");
			Boolean status2 = foundation.isDisplayed(TXT_ALREADYEXIST_MSG);
			CustomisedAssert.assertTrue(status2);
		} catch (Exception e) {
			Assert.fail();
		}
	}

	public void verifyDeleteConfirmationMsg(String deleteMsg) {
		try {
			By popupMessage = By.xpath("//p[text()='" + deleteMsg + "']");
			Boolean status1 = foundation.isDisplayed(popupMessage);
			CustomisedAssert.assertTrue(status1);
		} catch (Exception exc) {
			Assert.fail();
		}
	}

	public void createNewNationalAccount(String accountName, String client_Name) {
		try {
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(NationalAccounts.BTN_CREATE);
			textBox.enterText(NationalAccounts.TXT_ACCOUNT_NAME, accountName);
			dropDown.selectItem(NationalAccounts.DPD_CLIENT_NAME, client_Name, Constants.TEXT);
		} catch (Exception exc) {
			Assert.fail();
		}
	}
}
