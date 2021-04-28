package at.smartshop.pages;

import org.openqa.selenium.By;
import org.testng.Assert;
import at.framework.browser.Factory;
import at.framework.ui.*;

public class NavigationBar extends Factory {
	TextBox textBox = new TextBox();
	Dropdown dropdown = new Dropdown();
	Foundation foundation = new Foundation();

	private By dpdOrg = By.className("select2");
	private By txtOrg = By.className("select2-search__field");
	private By dpdSelectOrg = By.className("select2-results__option");
	public By mnuAdmin = By.id("drop6");
	public By mnuConsumer = By.id("supadmin-adconsumer");
	public By mnuProduct = By.id("drop1");
	public By mnuGobalProduct = By.id("sup-product");
	public By mnuLocationGobalProduct = By.id("sup-product");
	public By mnuReports = By.cssSelector("a[id*=reports]");
	public By mnuLocation = By.id("//a[contains(@id,'location')])[1]");
	public By mnuSuper = By.id("drop7");
	public By mnuUserRoles = By.id("super-supuser");
	public By mnuUNationalAccounts = By.xpath("(//a[text()='National Accounts'])[2]");

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

	
	public void navigateToSubmenu(By menu, String menuItem) {
		try {
		foundation.click(menu);
		foundation.click(By.xpath("(//a[text()='" + menuItem + "'])[2]"));
		}
		catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
}
