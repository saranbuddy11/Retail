package at.smartshop.pages;

import org.openqa.selenium.By;

import at.framework.ui.Foundation;
import at.framework.ui.TextBox;

public class CategorySummary {
	private TextBox textBox = new TextBox();
	private Foundation foundation = new Foundation();
	
	public static final By TXT_NAME= By.id("name");
	public static final By BTN_SAVE= By.id("saveBtn");
	
	public void updateName(String categoryName) {
		textBox.enterText(TXT_NAME, categoryName);
		foundation.click(BTN_SAVE);
	}
}
