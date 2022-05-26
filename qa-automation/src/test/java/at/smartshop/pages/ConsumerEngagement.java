package at.smartshop.pages;

import org.openqa.selenium.By;

import at.framework.browser.Factory;
import at.framework.generic.CustomisedAssert;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.keys.Constants;

public class ConsumerEngagement extends Factory {
	private Foundation foundation = new Foundation();
	private TextBox textBox = new TextBox();

	public static final By PAGE_TITLE = By.id("Consumer Engagement");
	public static final By BTN_ADD_GIFT_CARD = By.id("giftcardBtn");
	public static final By LBL_HEADER = By.className("header-text");
	public static final By INPUT_TITLE = By.id("title");
	public static final By INPUT_AMOUNT = By.id("amount");
	public static final By INPUT_EXPIRE_DATE = By.id("expirationdate");
	public static final By BTN_SAVE = By.id("addgiftsavebtn");

	// Gift Card Creation with inputs of Title, Amount and Expire Date
	public void createGiftCard(String title, String amount, String expiry) {
		foundation.click(BTN_ADD_GIFT_CARD);
		CustomisedAssert.assertTrue(foundation.isDisplayed(LBL_HEADER));
		textBox.enterText(INPUT_TITLE, title);
		textBox.enterText(INPUT_AMOUNT, amount);
		textBox.enterText(INPUT_EXPIRE_DATE, expiry);
		foundation.click(BTN_SAVE);
		foundation.waitforElementToBeVisible(BTN_ADD_GIFT_CARD, Constants.SHORT_TIME);
	}

}
