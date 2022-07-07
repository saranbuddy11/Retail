package at.smartshop.pages;

import org.openqa.selenium.By;

import at.framework.browser.Factory;
import at.framework.generic.CustomisedAssert;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;

public class UserRoles extends Factory {
	
	private Foundation foundation = new Foundation();
	private TextBox textBox = new TextBox();
	
	
	public static final By BTN_CREATE_NEW_ROLE = By.id("newBtn");
	public static final By LBL_VIEW_ROLE = By.id("pagetitle");
	public static final By TAB_ADMIN = By.xpath("//*[@id='nav-tabs']/li/a[text()='Admin']");
	public static final By TXT_SEARCH_FILTER = By.cssSelector("#dt_filter > label > input");
	public static final By LBL_USER_LIST = By.id("User List");
	public static final By BTN_MANAGE_ROLE = By.id("customBtn");
	public static final By LBL_USER_ROLES = By.xpath("//div[normalize-space(text())='User Roles']");
	public static final By TBL_ROW=By.cssSelector("#dt > tbody");

	public By getRowByText(String text) {
		return By.xpath("//td[text()='" + text + "']");
	}

	public By getAgeVerificationFeature(String index) {
		return By.xpath("(//td[text()='Age Verification']/following-sibling::td/input)[" + index + "]");
	}
	/**
	 * search driver and verify
	 * @param driver
	 */
	public void searchDriver(String driver) {
		CustomisedAssert.assertTrue(foundation.isDisplayed(LBL_USER_LIST));
		textBox.enterText(TXT_SEARCH_FILTER, driver);
		foundation.waitforElementToBeVisible(TBL_ROW, 5);
		String text = foundation.getText(TBL_ROW);
		CustomisedAssert.assertTrue(text.contains(text));
		
	}
	
}
