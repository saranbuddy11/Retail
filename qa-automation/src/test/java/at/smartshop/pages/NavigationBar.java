package at.smartshop.pages;

import org.openqa.selenium.By;
import org.testng.Assert;
import at.framework.browser.Factory;
import at.framework.ui.*;

public class NavigationBar extends Factory {
	private TextBox textBox = new TextBox();
	private Foundation foundation = new Foundation();

	private static final By DPD_ORG = By.className("select2");
	private static final By TXT_ORG = By.className("select2-search__field");
	private static final By DPD_SELECT_ORG = By.className("select2-results__option");
	public static final By MNU_ADMIN = By.id("drop6");
	public static final By MNU_CONSUMER = By.id("supadmin-adconsumer");
	public static final By MNU_PRODUCT = By.id("drop1");
	public static final By MNU_GLOBAL_PRODUCT = By.id("sup-product");
	public static final By MNU_LOCATION_GLOBAL_PRODUCT = By.id("sup-product");
	public static final By MNU_REPORTS = By.cssSelector("a[id*=reports]");
	public static final By MNU_SUPER = By.id("drop7");
	public static final By MNU_USER_ROLES = By.id("super-supuser");

	public void selectOrginazation(String selectText) {	
		try {
			foundation.click(DPD_ORG);
			textBox.enterText(TXT_ORG, selectText);
			foundation.click(DPD_SELECT_ORG);
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
