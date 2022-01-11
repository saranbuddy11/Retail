package at.smartshop.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import at.framework.generic.Strings;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.keys.Constants;

public class CreateNewNationalAccountCategory {
	private Foundation foundation=new Foundation();
	private TextBox textBox=new TextBox();
	private Strings strings=new Strings();
	
	public static final By BTN_CREATE_NEW_CATEGORY = By.id("new-cat");
	public static final By TXT_CATEGORY_NAME = By.id("categoryName");
	public static final By BTN_SAVE_CATEGORY_NAME = By.id("create-category");
	public static final By TXT_SEARCH = By.id("search-box");
	public static final By LNK_UPC_MAPPED = By.xpath("//td[@aria-describedby='national-category-grid_upcCount']//a");
	public static final By TXT_PRODUCT_NAME = By.id("prdname");
	public static final By TXT_UPC = By.id("upc");
	public static final By BTN_ADD_UPC_PRODUCT = By.id("addUpc");
	public static final By BTN_SAVE_CATEGORY = By.id("update-category");	
	public static final By BTN_DELETE_CATEGORY = By.id("delete-category");
	public static final By BTN_YES_DELETE_CATEGORY = By.xpath("//button[text()='Yes']");
	public static final By BTN_CANCEL_PAGE = By.id("cancel-btn");
	
	public String newNationaAccount() {
		String categoryName = strings.getRandomCharacter();
		String productName = strings.getRandomCharacter();
		
		foundation.click(BTN_CREATE_NEW_CATEGORY);
		textBox.enterText(TXT_CATEGORY_NAME, categoryName);
		foundation.threadWait(Constants.THREE_SECOND);
		foundation.click(BTN_SAVE_CATEGORY_NAME);
		textBox.enterText(TXT_SEARCH, categoryName);
		foundation.click(LNK_UPC_MAPPED);
		textBox.enterText(TXT_PRODUCT_NAME, productName);
		textBox.enterText(TXT_UPC, productName);
		foundation.click(BTN_ADD_UPC_PRODUCT);
		foundation.click(BTN_SAVE_CATEGORY);
		return categoryName;
	}
	
	public void deleteNationalAccountCategory(String ruleCategory) {
		textBox.enterText(TXT_SEARCH, Keys.CLEAR);
		textBox.enterText(TXT_SEARCH, ruleCategory);
		foundation.click(LNK_UPC_MAPPED);
		foundation.waitforElement(BTN_DELETE_CATEGORY, Constants.MEDIUM_TIME);
		foundation.click(BTN_DELETE_CATEGORY);	
		foundation.waitforElement(BTN_YES_DELETE_CATEGORY, Constants.SHORT_TIME);
		foundation.click(BTN_YES_DELETE_CATEGORY);
	}
	
}
