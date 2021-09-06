package at.smartshop.pages;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.testng.Assert;

import at.framework.browser.Factory;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.keys.Constants;
import at.smartshop.tests.TestInfra;

public class NavigationBar extends Factory {
	private TextBox textBox = new TextBox();
	private Foundation foundation = new Foundation();

	public static final By DPD_ORG = By.className("select2");
	private static final By TXT_ORG = By.className("select2-search__field");
	private static final By DPD_SELECT_ORG = By.className("select2-results__option");

	public void selectOrganization(String selectText) {	
		try {
			foundation.waitforElement(DPD_ORG, Constants.SHORT_TIME);
			foundation.click(DPD_ORG);
			textBox.enterText(TXT_ORG, selectText);
			foundation.click(DPD_SELECT_ORG);
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	public void navigateToMenuItem(String optionNames) {
        try {
            List<String> optionName = Arrays.asList(optionNames.split(Constants.DELIMITER_HASH));
            foundation.click(By.xpath("//ul[@role='navigation']//li//a[contains(text(),'" + optionName.get(0) + "')]"));
            if (optionName.size() == 2) {
                foundation.click(By.xpath("//ul[@role='navigation']//li//a[contains(text(),'" + optionName.get(0)+ "')]//..//ul//li//a[(text()='" + optionName.get(1) + "')]"));
            } else if(optionName.size() > 2) {
                foundation.objectFocus(By.xpath("//ul[@role='navigation']//li//a[contains(text(),'" + optionName.get(0)+ "')]//..//ul//li//a[(text()='" + optionName.get(1) + "')]"));
                foundation.click(By.xpath("//ul[@role='navigation']//li//a[contains(text(),'" + optionName.get(0)+ "')]//..//ul//li//a[(text()='" + optionName.get(1) + "')]//..//ul/li/a[(text()='" + optionName.get(2) + "')]"));
            }
        } catch (Exception exc) {
            Assert.fail(exc.toString());
        }
    }
	
}
