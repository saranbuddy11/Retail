package at.framework.ui;

import org.openqa.selenium.By;
import org.testng.Assert;

import com.aventstack.extentreports.Status;

import at.framework.browser.Factory;
import at.framework.reportsetup.ExtFactory;

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
}
