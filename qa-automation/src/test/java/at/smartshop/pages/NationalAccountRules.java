package at.smartshop.pages;


import org.openqa.selenium.By;
import org.testng.Assert;
import at.framework.browser.Factory;
import at.framework.ui.Foundation;

public class NationalAccountRules extends Factory {
	
	public static final  By BTN_POPUP_YES = By.xpath("//button[text()='YES']");
	public static final  By ICON_DELETE = By.xpath("//a[@class='fa fa-trash icon']");
	public static final By BTN_CANCEL = By.xpath("//button[text()='CANCEL']");
	public static final By TBL_NATIONALACCOUNT_BODY = By.cssSelector("#dataGrid > tbody");
	public static final By SEARCH_BOX = By.cssSelector("input#filterType");
	
	private Foundation foundation = new Foundation();
	
	public void clickRulesLink(String RuleName) {
		try {
			foundation.click(objRuleName(RuleName));
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
	
	public By objRuleName(String RuleName) {
		return By.xpath("//a[text()='"+RuleName+"']");
	}
	
	public By verifyPromptMsg(String promptMessage) {
		 return By.xpath("//div[text()='"+promptMessage+"']");
    }
	
	public By objRulePage(String ruleName) {		   
        return By.xpath("//input[@value='"+ruleName+"']");
                
    }
	
}
