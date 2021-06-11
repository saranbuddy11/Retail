package at.smartshop.v5.pages;

import org.openqa.selenium.By;

public class LandingPage {
	
	public static final By IMG_LOGO= By.xpath("//span[@class='logoImg']");
	public static final By LBL_HEADER = By.xpath("//h1[@id='instructionText']");
	public static final By IMG_SEARCH_ICON = By.cssSelector("div.btn.category.search-btn");
	public static final By BTN_LOGIN=By.id("account-login-id");
	public static final By BTN_CREATE_ACCOUNT=By.id("create-account-id");
	public static final By BTN_LANG=By.xpath("//button[2]");
	public static final By LBL_=By.xpath("//h2");
	public static final By LBL_PRODUCT_NAME=By.id("");
	
	public static final By LBL_ACCOUNT_LOGIN= By.xpath("//div[@id='account-login-id']//h3");
	public static final By LBL_CREATE_ACCOUNT= By.xpath("//div[@id='create-account-id']/div//h3");
	public static final By LBL_SEARCH= By.xpath("//span[@class='category-label']");
	public static final By LBL_SCAN= By.xpath("//div[@class='footer']//h2");
	
	public By objLanguage(String languageName) {
		return By.xpath("//button[text()='"+languageName+"']");
	}
	
}
