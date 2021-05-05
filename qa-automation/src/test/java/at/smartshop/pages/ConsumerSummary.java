package at.smartshop.pages;

import org.openqa.selenium.By;
import org.testng.Assert;
import at.framework.ui.*;
import at.smartshop.keys.Constants;

public class ConsumerSummary {
	TextBox textBox = new TextBox();

	private static final By lblReadBalance = By.id("readbalance");
	public static final By btnAdjust = By.id("adjustBalanceBtn");
	public static final By txtAdjustBalance = By.id("balNum");
	public static final By dpdReason = By.id("reason");
	public static final By btnSave = By.id("reasonSaveBtn");

	public double getBalance() {
		double initBalance = 0;
		try {
			String balance = textBox.getText(lblReadBalance);
			initBalance = Double.parseDouble(balance.substring(1).replace(Constants.DELIMITER_COMMA, Constants.EMPTY_STRING));
		} catch (Exception exc) {		
			Assert.fail();
		}
		return initBalance;
	}

}
