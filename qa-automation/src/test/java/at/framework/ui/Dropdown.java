package at.framework.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.aventstack.extentreports.Status;

import at.framework.browser.Factory;
import at.framework.reportsetup.ExtFactory;
import at.smartshop.keys.Constants;

public class Dropdown extends Factory {
	
	Foundation foundation=new Foundation();

	public void selectItem(By object, String text, String type) {
		try {
			Select select = new Select(getDriver().findElement(object));
			if (type == Constants.VALUE) {
				select.selectByValue(text);

			} else if (type == Constants.TEXT) {
				select.selectByVisibleText(text);
			}
			ExtFactory.getInstance().getExtent().log(Status.INFO, "selected dropdown value " + text );
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
}
