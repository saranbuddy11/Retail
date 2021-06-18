package at.smartshop.v5.pages;

import org.openqa.selenium.By;

public class LandingPage {
	
	public static final By IMG_LOGO= By.xpath("//span[@class='logoImg']");
	public static final By LBL_HEADER = By.xpath("//h1[@id='instructionText']");
	public static final By IMG_SEARCH_ICON = By.cssSelector("div.btn.category.search-btn");
	public static final By BTN_LOGIN=By.id("account-login-id");

	public By objLanguage(String languageName) {
		return By.xpath("//button[text()='"+languageName+"']");
	}
	
}
