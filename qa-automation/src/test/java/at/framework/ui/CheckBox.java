package at.framework.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.aventstack.extentreports.Status;

import at.framework.browser.Factory;
import at.framework.reportsetup.ExtFactory;

public class CheckBox extends Factory {

	public void check(By object) {
		try {
			WebElement element = getDriver().findElement(object);

			if (!element.isSelected()) {
				element.click();
			}
			ExtFactory.getInstance().getExtent().log(Status.INFO, "Checked the checkbox [ " + object + " ]");
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
			ExtFactory.getInstance().getExtent().log(Status.INFO, "Unchecked the checkbox [ " + object + " ]");
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	public boolean isChecked(By object) {
		boolean isChecked = false;
		try {
			WebElement element = getDriver().findElement(object);

			if (element.isSelected()) {
				isChecked = true;
			} else {
				isChecked = false;
			}
			ExtFactory.getInstance().getExtent().log(Status.INFO,
					"element [ " + object + " ] is checked [ " + isChecked + " ]");
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
		return isChecked;
	}

	public boolean isChkEnabled(By object) {
		boolean isEnabled = false;
		try {
			WebElement element = getDriver().findElement(object);

			if (element.isEnabled()) {
				isEnabled = true;
			} else {
				isEnabled = false;
			}
			ExtFactory.getInstance().getExtent().log(Status.INFO,
					"element [ " + object + " ] is enabled [ " + isEnabled + " ]");
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
		return isEnabled;
	}
}
