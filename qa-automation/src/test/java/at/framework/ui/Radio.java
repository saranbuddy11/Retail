package at.framework.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.aventstack.extentreports.Status;

import at.framework.browser.Factory;
import at.framework.reportsetup.ExtFactory;

public class Radio extends Factory {
	
	public void set(By object) {		
		try {
			getDriver().findElement(object).click();
			ExtFactory.getInstance().getExtent().log(Status.INFO,  "Selected radio button [ "+object +" ]");
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}		
	}
	public boolean isSelected(By object) {
		boolean isSelected = false;
		try {
			WebElement element = getDriver().findElement(object);

			if (element.isSelected()) {
				isSelected = true;
			} else {
				isSelected = false;
			}

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
		return isSelected;
	}
}