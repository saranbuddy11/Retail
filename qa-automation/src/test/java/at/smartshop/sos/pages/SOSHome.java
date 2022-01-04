package at.smartshop.sos.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.testng.Assert;

import at.framework.browser.Factory;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;

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

	public void selectOrginazation(String selectText) {
		try {
			foundation.click(DPD_ORG);
			textBox.enterText(TXT_ORG, selectText);
			foundation.click(DPD_SELECT_ORG);
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	public void verifyColumnNames(List<String> expected, List<String> actual) {
		List<String> expectedColumns = expected;
		List<String> actualColumns = actual;
		System.out.println(expectedColumns);
		System.out.println(actualColumns);

	}

}
