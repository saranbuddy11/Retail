package at.smartshop.sos.pages;

import java.util.List;
import org.openqa.selenium.By;
import at.framework.browser.Factory;
import at.framework.generic.CustomisedAssert;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.tests.TestInfra;
import at.smartshop.keys.Constants;

public class SOSHome extends Factory {

	Foundation foundation = new Foundation();
	TextBox textBox = new TextBox();
	Dropdown dropdown = new Dropdown();

	private static final By DPD_ORG = By.className("select2-selection__rendered");
	private static final By TXT_ORG = By.className("select2-search__field");
	private static final By DPD_SELECT_ORG = By.className("select2-results__option");
	public static final By MENU = By.cssSelector("a[title=UserLoad]");
	public static final By LANDING_PAGE_HEADING = By.xpath("//li[contains(text(),'Dashboard')]");
	public static final By PAGE_HEADING = By.xpath("//li[contains(text(),'Load GMA User Parameters')]");
	public static final By LBL_USER_NAME = By.xpath("//a[@id='drop5' and @class='dropdown-toggle']");
	private static final By MUN_LOGOUT = By.xpath("//a[@title='Logout']");

	public void selectOrginazation(String selectText) {
		try {
			foundation.click(DPD_ORG);
			textBox.enterText(TXT_ORG, selectText);
			foundation.click(DPD_SELECT_ORG);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void verifyColumnNames(List<String> expected, List<String> actual) {
		List<String> expectedColumns = expected;
		List<String> actualColumns = actual;
		CustomisedAssert.assertTrue(expectedColumns.get(0).equals(actualColumns.get(4)));
		CustomisedAssert.assertTrue(expectedColumns.get(1).equals(actualColumns.get(5)));
		CustomisedAssert.assertTrue(expectedColumns.get(2).equals(actualColumns.get(6)));
		CustomisedAssert.assertTrue(expectedColumns.get(3).equals(actualColumns.get(8)));
		CustomisedAssert.assertTrue(expectedColumns.get(4).equals(actualColumns.get(1)));
		CustomisedAssert.assertTrue(actualColumns.get(2).contains(expectedColumns.get(5)));
		CustomisedAssert.assertTrue(expectedColumns.get(6).equals(actualColumns.get(3)));
		CustomisedAssert.assertTrue(actualColumns.get(7).contains(expectedColumns.get(7)));
		CustomisedAssert.assertTrue(actualColumns.get(9).contains(expectedColumns.get(8)));
		CustomisedAssert.assertTrue(expectedColumns.get(9).equals(actualColumns.get(0)));
	}

	public void logout() {
		try {
			foundation.waitforClikableElement(LBL_USER_NAME, Constants.SHORT_TIME);
			foundation.click(LBL_USER_NAME);
			foundation.click(MUN_LOGOUT);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
}
