package at.smartshop.pages;

import org.openqa.selenium.By;
import org.testng.Assert;
import at.framework.ui.*;
import at.smartshop.keys.Constants;

public class ConsumerSummary {
	TextBox textBox = new TextBox();

	private By lblReadBalance = By.id("readbalance");
	public By btnAdjust = By.id("adjustBalanceBtn");
	public By txtAdjustBalance = By.id("balNum");
	public By dpdReason = By.id("reason");
	public By btnSave = By.id("reasonSaveBtn");

	public double getBalance() {
		double initBalance = 0;
		try {
			String balance = textBox.getText(lblReadBalance);
			initBalance = Double.parseDouble(balance.substring(1).replace(",", Constants.EMPTY_STRING));
		} catch (Exception exc) {		
			Assert.fail();
		}
		return initBalance;
	}

}
