package at.smartshop.v5.pages;

import java.util.Arrays;
import java.util.List;
import org.openqa.selenium.By;
import org.testng.Assert;
import at.framework.ui.Foundation;
import at.smartshop.keys.Constants;

public class LandingPage {

	public static final By IMG_LOGO = By.xpath("//span[@class='logoImg']");

	public static final By LBL_HEADER = By.xpath("//h1[@id='instructionText']");
	public static final By IMG_SEARCH_ICON = By.cssSelector("div.btn.category.search-btn");
	public static final By LNK_IMAGE = By.xpath("//figure[@class='Commercial animated ']//img");
	public static final By BTN_PRODUCT = By.xpath("//button[@class='product search-product']");
	public static final By TXT_HEADER = By.xpath("//div[@class='user-bar']/h1");
	public static final By TXT_PRODUCT = By.xpath("//div[@id='cartContainer']//div[@class='product-name']");
	public static final By LINK_ENGLISH = By.xpath("//button[text()='English']");
	public static final By BTN_LOGIN = By.id("account-login-id");
	public static final By BTN_CREATE_ACCOUNT = By.id("create-account-id");
	public static final By BTN_LANG = By.xpath("//button[@data-reactid='.0.0.0.2.1']");
	public static final By LBL_ = By.xpath("//h2");
	public static final By LBL_ACCOUNT_LOGIN = By.xpath("//div[@id='account-login-id']//h3");
	public static final By LBL_CREATE_ACCOUNT = By.xpath("//div[@id='create-account-id']/div//h3");
	public static final By LBL_SEARCH = By.xpath("//span[@class='category-label']");
	public static final By LBL_SCAN = By.xpath("//div[@class='footer']//h2");

	private Foundation foundation = new Foundation();
	
	public By objLanguage(String languageName) {
		return By.xpath("//button[text()='" + languageName + "']");
	}

	public void navigateDriverLoginPage() {
		
		foundation.doubleClick(LandingPage.IMG_LOGO);
		foundation.doubleClick(LandingPage.IMG_LOGO);
		foundation.click(LandingPage.IMG_LOGO);
	}	
	

	public By objText(String text) {
		return By.xpath("//*[text()='" + text + "']");
	}

	public By objImageDisplay(String imageName) {
		return By.xpath("//img[contains(@src,'" + imageName + "')]");
	}

	public void verifyHomeScreenLanguage(String landingPage) {
		List<String> landingPageData = Arrays.asList(landingPage.split(Constants.DELIMITER_TILD));

		// Validating Landing Page
		Assert.assertEquals(foundation.getText(LandingPage.LBL_ACCOUNT_LOGIN), landingPageData.get(0));
		Assert.assertEquals(foundation.getText(LandingPage.LBL_CREATE_ACCOUNT), landingPageData.get(1));
		Assert.assertTrue(foundation.isDisplayed(objLanguage(landingPageData.get(2))));
		Assert.assertTrue(foundation.isDisplayed(objLanguage(landingPageData.get(3))));
		Assert.assertEquals(foundation.getText(LandingPage.LBL_HEADER), landingPageData.get(4));
		Assert.assertEquals(foundation.getText(LandingPage.LBL_SEARCH), landingPageData.get(5));
		Assert.assertEquals(foundation.getText(LandingPage.LBL_SCAN), landingPageData.get(6));

	}

}
