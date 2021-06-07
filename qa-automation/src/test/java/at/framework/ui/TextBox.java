package at.framework.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
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
	
	public String getTextFromInput(By object) {
        String text = null;
        try {
            text = getDriver().findElement(object).getAttribute("value");
        } catch (Exception exc) {
            exc.printStackTrace();
            Assert.fail(exc.toString());
        }
        return text;
    }
	
	public void enterText(By object, Keys key) {
        try {
            getDriver().findElement(object).clear();
            getDriver().findElement(object).sendKeys(key);
           
            if(ExtFactory.getInstance().getExtent()!=null) {
            ExtFactory.getInstance().getExtent().log(Status.INFO, "entered keys "+key);
            }
           
        } catch (Exception exc) {
            exc.printStackTrace();
            Assert.fail(exc.toString());
        }
       
    }
}
