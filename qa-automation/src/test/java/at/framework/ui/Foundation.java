package at.framework.ui;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.Status;
import com.google.common.base.Function;

import at.framework.browser.Factory;
import at.framework.reportsetup.ExtFactory;
import at.smartshop.keys.Constants;

public class Foundation extends Factory {

	public boolean isDisplayed(By object) {
		boolean isElementDisplayed = false;
		try {
			isElementDisplayed = getDriver().findElement(object).isDisplayed();
			if(ExtFactory.getInstance().getExtent()!=null) {
			ExtFactory.getInstance().getExtent().log(Status.INFO, object+" is displayed");
			}
		} catch (NoSuchElementException exc) {
			isElementDisplayed=false;
		}
		 catch (Exception exc) {			 
			Assert.fail(exc.toString());
		}
		return isElementDisplayed;
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
			FluentWait<WebDriver> wait = new FluentWait<>(getDriver())
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
	
	public void refreshPage() {       
        try {
        	getDriver().navigate().refresh();
        } catch (Exception exc) {           
        	exc.printStackTrace();
        }
    }
	
	public String getTextAttribute(By object) {
		String textAttribute = null;
		try {
			textAttribute = getDriver().findElement(object).getAttribute(Constants.VALUE);
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
		return textAttribute;
	}
	
	public void objectFocus(By element) {
		Actions action = new Actions(getDriver());
        Action seriesOfActions = action.moveToElement(getDriver().findElement(element)).build();
        seriesOfActions.perform();
	}
	
	public boolean isEnabled(By object) {
        boolean ObjEnabled = false;
        try {
            ObjEnabled = getDriver().findElement(object).isEnabled();
            if(ExtFactory.getInstance().getExtent()!=null) {
            ExtFactory.getInstance().getExtent().log(Status.INFO, object+" is enabled");
            }
        } catch (Exception exc) {
            ObjEnabled = false;                   
        }
        return ObjEnabled;
    }
	
	public int getSizeofListElement(By object) {
		int sizeofObj = 0;
        try {
        	sizeofObj= getDriver().findElements(object).size();        
        }catch(Exception exc) {
        	Assert.fail(exc.toString()); 
        }
        return sizeofObj;
    }
	
	public void threadWait(int milliSeconds) {
		try {
			Thread.sleep(milliSeconds);
		}
	 catch(Exception exc) {
     	Assert.fail(exc.toString()); 
     }
	}
	
	 public String getBGColor(By object) {
	    	String hexColor=null;
	    	try {
	    		WebElement element = getDriver().findElement(object);
	    		String colorValue = element.getCssValue("background-color");
	    		hexColor = Color.fromString(colorValue).asHex();
	    		 ExtFactory.getInstance().getExtent().log(Status.INFO,"Back Ground color for "+ object);
	    	} catch (Exception exc) {
	       	 Assert.fail(exc.toString());
	       }
	    	return hexColor;  	
	    }
	 
	 public void doubleClick(By object) {
         try {           
              Actions action = new Actions(getDriver());
              action.doubleClick(getDriver().findElement(object)).perform();
             if(ExtFactory.getInstance().getExtent()!=null) {                               
                     ExtFactory.getInstance().getExtent().log(Status.INFO, "clicked on [ "+object +" ]");
                 }
         } catch (Exception exc) {
             Assert.fail(exc.toString());
         }
     }
}
