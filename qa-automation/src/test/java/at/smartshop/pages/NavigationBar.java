package at.smartshop.pages;

import org.openqa.selenium.By;
import org.testng.Assert;
import at.framework.browser.Factory;
import at.framework.ui.*;

public class NavigationBar extends Factory {
	TextBox textBox = new TextBox();
	Dropdown dropdown = new Dropdown();
	Foundation foundation = new Foundation();

	private static final By dpdOrg = By.className("select2");
	private static final By txtOrg = By.className("select2-search__field");
	private static final By dpdSelectOrg = By.className("select2-results__option");
	public static final By mnuAdmin = By.id("drop6");
	public static final By mnuConsumer = By.id("supadmin-adconsumer");
	public static final By mnuProduct = By.id("drop1");
	public static final By mnuGobalProduct = By.id("sup-product");
	public static final By mnuLocationGobalProduct = By.id("sup-product");
	public static final By mnuReports = By.cssSelector("a[id*=reports]");
	public static final By mnuSuper = By.id("drop7");
	public static final By mnuUserRoles = By.id("super-supuser");

	public void selectOrginazation(String selectText) {	
		try {
			foundation.click(dpdOrg);
			textBox.enterText(txtOrg, selectText);
			foundation.click(dpdSelectOrg);
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	public void navigateToMenuItem(By menu, String menuItem) {
		try {
		foundation.click(menu);
		foundation.click(By.xpath("//a[text()='" + menuItem + "']"));
		}
		catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
	
	public void navigateToMenuItem(By menu, By menuItem) {
		try {
		foundation.click(menu);
		foundation.scrollToElement(menuItem);
		foundation.click(menuItem);
		}
		catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
}
