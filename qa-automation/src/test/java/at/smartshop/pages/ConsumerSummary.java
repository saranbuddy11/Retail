package at.smartshop.pages;

import org.openqa.selenium.By;
import org.testng.Assert;
import at.framework.ui.*;
import at.smartshop.keys.Constants;

public class ConsumerSummary {
	private Foundation foundation = new Foundation();

	private static final By LBL_READ_BALANCE = By.id("readbalance");
	public static final By BTN_ADJUST = By.id("adjustBalanceBtn");
	public static final By TXT_ADJUST_BALANCE = By.id("balNum");
	public static final By DPD_REASON = By.id("reason");
	public static final By BTN_REASON_SAVE = By.id("reasonSaveBtn");
	public static final By BTN_REASON_CANCEL = By.id("reasoncancel");
	public static final By BTN_SAVE=By.id("saveBtn");
	public static final By TXT_SEARCH_ACCOUNT_ADJUSTMENT=By.xpath("//div[@id='aadt_filter']//input");
	public static final By LBL_POPUP_ADJUST_BALANCE = By.id("reasontitle");
	public static final By LBL_CONSUMER_SUMMARY = By.id("Consumer Summary");

	public double getBalance() {
		double initBalance = 0;
		try {
			String balance = foundation.getText(LBL_READ_BALANCE);
			initBalance = Double.parseDouble(balance.substring(1).replace(Constants.DELIMITER_COMMA, Constants.EMPTY_STRING));
		} catch (Exception exc) {		
			Assert.fail();
		}
		return initBalance;
	}
	
	public By objTaxCategory(String reasonCode) {
		return By.xpath("//table[@id='aadt']//*[text()='"+reasonCode+"']");
	}

}
