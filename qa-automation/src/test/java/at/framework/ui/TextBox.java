package at.framework.ui;

import org.openqa.selenium.By;
import org.testng.Assert;

import com.aventstack.extentreports.Status;

import at.framework.browser.Factory;
import at.framework.reportsSetup.ExtFactory;

public class TextBox extends Factory {

	public void enterText(By object, String text) {
		try {
			getDriver().findElement(object).clear();
			getDriver().findElement(object).sendKeys(text);
			
			if(ExtFactory.getInstance().getExtent()!=null) {
			ExtFactory.getInstance().getExtent().log(Status.INFO, "entered text "+text);
			}
			
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	public String getText(By object) {
		String text = null;
		try {
			text = getDriver().findElement(object).getText();
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
		return text;
	}
}
