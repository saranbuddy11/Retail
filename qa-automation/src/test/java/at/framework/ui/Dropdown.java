package at.framework.ui;

import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.aventstack.extentreports.Status;

import at.framework.browser.Factory;
import at.framework.reportsSetup.ExtFactory;
import at.smartshop.keys.Constants;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

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
	
	public String getSelectedItem(By object) {
        foundation.scrollToElement(object);
        Select select = new Select(getDriver().findElement(object));
        return select.getFirstSelectedOption().getText();        
    }	
   
    public void selectItemByIndex(By object,int index) {
        try {
            Select select = new Select(getDriver().findElement(object));           
            select.selectByIndex(index);                       
            ExtFactory.getInstance().getExtent().log(Status.INFO, "selected 1st dropdown value");
        } catch (Exception exc) {
            Assert.fail(exc.toString());
        }
    }
    
    public boolean verifyItemPresent(By object,String text) {
        foundation.scrollToElement(object);
        Select select = new Select(getDriver().findElement(object));
        List<WebElement> items = select.getOptions();
       
        for(WebElement item : items) {
            String itemText = item.getText();
            if(itemText.equalsIgnoreCase(text)) {
                return true;
            }
        }
        return false;
    }
}
