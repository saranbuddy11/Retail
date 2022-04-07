package at.smartshop.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.aventstack.extentreports.Status;

import at.framework.browser.Factory;
import at.framework.generic.CustomisedAssert;
import at.framework.reportsetup.ExtFactory;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.keys.Constants;
import at.smartshop.tests.TestInfra;

public class CreateNewRule extends Factory {
	private Foundation foundation = new Foundation();
	private Dropdown dropdown = new Dropdown();
	private TextBox textbox = new TextBox();

	private static final By DPD_ORG = By.xpath("//*[@id='orgdt']//../span//input");
	public static final By DPD_LOCATION = By.xpath("//*[@id='locdt']//../span//input");
	public static final By DPD_RULE = By.id("ruleprice");
	public static final By TXT_PRICE = By.cssSelector("input#fromprice");
	public static final By BTN_SAVE = By.id("saveBtn");
	public static final By TXT_NATIONAL_CATEGORY = By.id("//*[@id='upctype']//..//input");
	public static final By CHCK_AUTO_LOCATION = By.cssSelector("input#autoselects");
	public static final By BTN_DELETE = By.cssSelector("button#deleteBtn");
	public static final By DELETE_PROMPT = By.cssSelector("div.ajs-dialog");
	public static final By DELETE_PROMPT_TITLE = By.cssSelector("div.ajs-header");
	public static final By BTN_CANCEL = By.id("cancelBtn");
	public static final By BTN_NO = By.cssSelector("button.ajs-button.ajs-cancel");
	public static final By BTN_YES = By.cssSelector("button.ajs-button.ajs-ok");
	public static final By TXT_SEARCH = By.cssSelector("input[type='search']");
	public static final By TXT_PAGE_TITLE = By.xpath("//div[contains(@id,'National Account')]");

	public static final By TXT_RULE_NAME = By.id("rulename");
	public static final By DPD_RULE_TYPE = By.id("ruletype");
	public static final By DPD_NATIONAL_CATEGORY = By.id("upctype");
	public static final By TXT_CLIENTRULE_NAME = By
			.xpath("//div[normalize-space(text())='National Account - Aramark:AutomationNationalAccount']");

	public void createRule(String org, String location, String ruleType, String nationalCategory, String ruleName,
			String price) {
		try {
			selectOrg(org);
			selectLocation(location);
			textbox.enterText(TXT_RULE_NAME, ruleName);
			dropdown.selectItem(DPD_RULE_TYPE, ruleType, Constants.TEXT);
			dropdown.selectItem(DPD_NATIONAL_CATEGORY, nationalCategory, Constants.TEXT);
			textbox.enterText(TXT_PRICE, price);
			foundation.click(BTN_SAVE);
			foundation.threadWait(Constants.THREE_SECOND);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void selectOrg(String org) {
		try {
			foundation.click(DPD_ORG);
			foundation.click(By.xpath("//ul[@id='select2-orgdt-results']//li[text()='" + org + "']"));
			foundation.threadWait(Constants.ONE_SECOND);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void selectLocation(String location) {
		try {
			foundation.click(DPD_LOCATION);
			foundation.click(By.xpath("//ul[@id='select2-locdt-results']//li[text()='" + location + "']"));
			foundation.threadWait(Constants.ONE_SECOND);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void verifyHintText(String hintText) {
		try {
			List<WebElement> element = getDriver().findElements(TXT_SEARCH);
			for (int iter = 0; iter < element.size(); iter++) {
				if (element.get(iter).getAttribute("placeholder").equals(hintText))
					break;
			}
			ExtFactory.getInstance().getExtent().log(Status.INFO, "Verified the Hint text for the Field" + hintText);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public List<String> getLocationDropdownValues() {
		List<String> locationDPDValues = new ArrayList<>();
		try {
			foundation.click(DPD_LOCATION);
			locationDPDValues.clear();
			WebElement values = getDriver().findElement(By.cssSelector("ul#select2-locdt-results"));
			List<WebElement> value = values.findElements(By.tagName("li"));
			for (int iter = 1; iter < value.size(); iter++) {
				locationDPDValues.add(value.get(iter).getText());
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		return locationDPDValues;
	}

	public void verifyCancelButton() {
		try {
			foundation.click(BTN_NO);
			CustomisedAssert.assertTrue(foundation.isDisplayed(BTN_DELETE));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void verifyYesButton() {
		try {
			foundation.click(BTN_DELETE);
			foundation.waitforElement(BTN_YES, Constants.SHORT_TIME);
			foundation.click(BTN_YES);
			foundation.waitforElement(AdminNationalAccounts.LBL_NATIONAL_ACCOUNT_RULE, Constants.SHORT_TIME);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public By objClientRuleName(String ruleName) {
		return By.xpath("//div[normalize-space(text())='" + ruleName + "']");
	}

}
