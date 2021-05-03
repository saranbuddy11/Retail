package at.framework.ui;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.Status;
import com.google.common.base.Function;

import at.framework.browser.Factory;
import at.framework.reportsSetup.ExtFactory;

public class Foundation extends Factory {

	public boolean isDisplayed(By object, String objectName) {
		boolean ObjDisplayed = false;
		try {
			ObjDisplayed = getDriver().findElement(object).isDisplayed();
			if(ExtFactory.getInstance().getExtent()!=null) {
			ExtFactory.getInstance().getExtent().log(Status.INFO, objectName+" is displayed");
			}
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
		return ObjDisplayed;
	}	

	public String getText(By object) {
		String text = null;
		try {
			text = getDriver().findElement(object).getText();
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
		return (text);
	}
	

	public void click(By object) {
		try {			
			getDriver().findElement(object).click();
			if(ExtFactory.getInstance().getExtent()!=null) {								
					ExtFactory.getInstance().getExtent().log(Status.INFO, "clicked on [ "+object +" ]");
				}
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
	
	
	public void fluentWait(String object, int waitTime) {
		try {
			FluentWait<WebDriver> wait = new FluentWait<WebDriver>(getDriver())
					.withTimeout(Duration.ofSeconds(waitTime)).pollingEvery(Duration.ofSeconds(5))
					.ignoring(NoSuchElementException.class);

			wait.until(new Function<WebDriver, WebElement>() {
				public WebElement apply(WebDriver getDriver) {
					return getDriver.findElement(By.id(object));
				}
			});
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	public WebElement waitforElement(By object, int waitTime) {
		WebElement element = null;
		try {
			WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
			element = wait.until(ExpectedConditions.visibilityOfElementLocated(object));
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
		return element;
	}
	
	public void scrollToElement(By object) {       
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", getDriver().findElement(object));
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {           
            e.printStackTrace();
        }
    }

}
