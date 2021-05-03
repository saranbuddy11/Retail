package at.framework.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.aventstack.extentreports.Status;

import at.framework.browser.Factory;
import at.framework.reportsSetup.ExtFactory;

public class CheckBox extends Factory {

	public void check(By object) {
		try {
			WebElement element = getDriver().findElement(object);
			
			if (!element.isSelected()) {
				element.click();
			}
			ExtFactory.getInstance().getExtent().log(Status.INFO, "Checked the checkbox [ "+object +" ]");
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	public void unCheck(By object) {
		try {
			WebElement element = getDriver().findElement(object);
			if (element.isSelected()) {
				element.click();
			}
			ExtFactory.getInstance().getExtent().log(Status.INFO, "Unchecked the checkbox [ "+object +" ]");
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
	
	public boolean isChecked(By object) {
		try {
			WebElement element = getDriver().findElement(object);
			
			if (element.isSelected()) {
				return true;
			}
			else {
				return false;
			}
			
		} catch (Exception exc) {			
			Assert.fail(exc.toString());
			return false;
		}
	}
}
