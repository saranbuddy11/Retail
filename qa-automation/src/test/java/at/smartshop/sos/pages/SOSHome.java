package at.smartshop.sos.pages;

import org.openqa.selenium.By;
import org.testng.Assert;
import at.framework.browser.Factory;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.tests.TestInfra;

public class SOSHome extends Factory {
	
	Foundation foundation = new Foundation();
	TextBox textBox = new TextBox();
	Dropdown dropdown = new Dropdown();
	
	private static final By DPD_ORG = By.className("select2-selection__rendered");
	private static final By TXT_ORG = By.className("select2-search__field");
	private static final By DPD_SELECT_ORG = By.className("select2-results__option");
	public static final By MENU = By.cssSelector("a[title=UserLoad]");
	
	public void selectOrginazation(String selectText) {
		try {
			foundation.click(DPD_ORG);
			textBox.enterText(TXT_ORG,selectText);
			foundation.click(DPD_SELECT_ORG);
		} catch (Exception exc) {			
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

}
