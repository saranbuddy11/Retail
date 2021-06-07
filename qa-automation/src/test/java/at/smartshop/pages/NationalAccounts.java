package at.smartshop.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import at.framework.browser.Factory;
import at.framework.ui.Foundation;

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
	
	public static final By TXT_ALREADYEXISTS = By.xpath("//p[text()='Already Exists']");
	public static final By TXT_ALREADYEXIST_MSG = By.xpath("//b[text()=' You already have created a ']");
	public static final By TBL_HEADERS = By.xpath("//table//thead//tr//th");
	public static final By BTN_POPUP_YES = By.xpath("//button[text()='YES']");
	public static final By ICON_DELETE = By.xpath("//a[@class='fa fa-trash icon']");
	public static final By popupMessage = By.xpath("//p[text()='National Account Location Delete Confirmation']");
	public static final By TXT_POPUP_WARNING_MSG = By.xpath(
			"//div[text()='You are about to delete a National Account location. National Account rules will no longer apply to this location if deleted.']");

	public static final By lblOrginization = By.cssSelector("td[aria-describedby=nationalAccountsSummaryGrid_org]");
	public static final By lblLocation = By.cssSelector("td[aria-describedby=nationalAccountsSummaryGrid_location]");
	public static final By icoDelete = By.cssSelector("td[aria-describedby=dataGrid_delete] > a");
	public static final By BTN_ADD_NATIONALACCOUNT = By.id("nationalAccountBtn");

	public static final String AUTOMATION_ORG = "AutomationOrg";
	public static final String LOCATION = "AutomationLocation3";
	
    public static final By LBL_ORGANIZATION = By.cssSelector("#nationalAccountsSummaryGrid > tbody > tr > td:nth-child(1)");
    public static final By LBL_CITY = By.cssSelector("#nationalAccountsSummaryGrid > tbody > tr > td:nth-child(3)");
    public static final By LBL_STATE = By.cssSelector("#nationalAccountsSummaryGrid > tbody > tr > td:nth-child(4)");
    public static final By BTN_ADD_TO_NATIONAL_ACCOUNTS = By.id("nationalAccountBtn");   
    public static final By BTN_Cancel = By.xpath("//button[text()='CANCEL']");
    
	public List<String> nationalAccountsHeadersList = new ArrayList<>();
	public static final String id_tableNationalAccountsSummary = "nationalAccountsSummaryGrid";
	
	private Foundation foundation=new Foundation();

	public By nationalAccountDetails(String accountName) {
		By element = null;
		try {
			element = By.xpath("//span[contains(text(),'"+accountName+"')]");
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
			Assert.assertTrue(headerList.contains(nationalAccountsHeadersList.get(iter)));
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
		Assert.assertTrue(isdisplayed);
		} catch (Exception exc) {
			exc.printStackTrace();
			Assert.fail();
		}
	}

	public void clickNatioanlAccountName(String text) {
		try {
		By locationElement = By.xpath("//table//tbody//tr//td[text()='" + text + "']");
		foundation.click(locationElement);
		} catch (Exception exc) {
			exc.printStackTrace();
			Assert.fail();
		}
	}
	
	public void verifyBackgroundColour(String text,String actualcolor) { 
        By locationElement = By.xpath("//select[@name='locs']//option[text()='" + text + "']");
        String expected = getDriver().findElement(locationElement).getAttribute("style");
        Assert.assertEquals(actualcolor, expected);
    }
	
	public boolean trySelectNonVisibleTextLocation(String text) {
        try {
            Select select = new Select(getDriver().findElement(LBL_LOCATION));
            select.selectByVisibleText(text);
            return true;
        }
        catch (Exception e) {
            // TODO: handle exception
            return false;
        }
    }
	
	public void verifyPromptMsg(String Message, String existsmsg) {
        try {
            By TXT_ALREADYEXISTS = By.xpath("//p[text()='" + existsmsg + "']");
            Boolean status1 = foundation.isDisplayed(TXT_ALREADYEXISTS);
            Assert.assertTrue(status1);
            By TXT_ALREADYEXIST_MSG = By.xpath("//b[text()='" + Message + "']");
            Boolean status2 = foundation.isDisplayed(TXT_ALREADYEXIST_MSG);
            Assert.assertTrue(status2);
        } catch (Exception e) {
            Assert.fail();
        }
    }
	
    public void verifyDeleteConfirmationMsg(String deleteMsg) {
        try {
            By popupMessage = By.xpath("//p[text()='"+deleteMsg+"']");
            Boolean status1 = foundation.isDisplayed(popupMessage);
            Assert.assertTrue(status1);
        } catch (Exception exc) {
            Assert.fail();
        }
    }
}
