package at.smartshop.pages;

import org.openqa.selenium.By;

import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.keys.Constants;

public class CategorySummary {
	private TextBox textBox = new TextBox();
	private Foundation foundation = new Foundation();
	private Dropdown dropDown=new Dropdown();
	
	public static final By TXT_NAME= By.id("name");
	public static final By BTN_SAVE= By.id("saveBtn");
	public static final By DPD_CATEGORY_TYPE= By.id("typesel");
	
	public void updateName(String categoryName) {
		textBox.enterText(TXT_NAME, categoryName);
		foundation.click(BTN_SAVE);
		foundation.threadWait(Constants.SHORT_TIME);
	}
	
	public void addCategory(String categoryName,String categoryType) {
		textBox.enterText(TXT_NAME, categoryName);
		dropDown.selectItem(DPD_CATEGORY_TYPE, categoryType, Constants.TEXT);
		foundation.click(BTN_SAVE);
	}
}
