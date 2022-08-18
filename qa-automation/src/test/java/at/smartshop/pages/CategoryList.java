package at.smartshop.pages;

import org.openqa.selenium.By;

import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.keys.Constants;

public class CategoryList {
	private TextBox textBox = new TextBox();
	private Foundation foundation = new Foundation();

	public static final By TXT_SEARCH_CATEGORY = By.xpath("//*[@id='dt_filter']//input");
	public static final By BTN_CREATE_NEW_CATEGORY = By.id("newBtn");

	public void selectCategory(String categoryName) {
		textBox.enterText(TXT_SEARCH_CATEGORY, categoryName);
		foundation.threadWait(Constants.SHORT_TIME);
		foundation.click(By.xpath("//td//span[text()='" + categoryName + "']"));
	}

	public boolean verifyCategoryExist(String categoryName) {
		textBox.enterText(TXT_SEARCH_CATEGORY, categoryName);
		return foundation.isDisplayed(By.xpath("//td//span[text()='" + categoryName + "']"));
	}

}
