package at.framework.ui;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.aventstack.extentreports.Status;

import at.framework.browser.Factory;
import at.framework.reportsetup.ExtFactory;
import at.smartshop.keys.Constants;
import at.smartshop.tests.TestInfra;

public class Dropdown extends Factory {

	Foundation foundation = new Foundation();

	public void selectItem(By object, String text, String type) {
		try {
			Select select = new Select(getDriver().findElement(object));
			if (type == Constants.VALUE) {
				select.selectByValue(text);

			} else if (type == Constants.TEXT) {
				select.selectByVisibleText(text);
			}
			ExtFactory.getInstance().getExtent().log(Status.INFO, "selected dropdown value " + text);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public boolean verifyItemPresent(By object, String text) {
		Select select = new Select(getDriver().findElement(object));
		List<WebElement> items = select.getOptions();

		for (WebElement item : items) {
			String itemText = item.getText();
			if (itemText.equalsIgnoreCase(text)) {
				//ExtFactory.getInstance().getExtent().log(Status.INFO, "item " + text +"is present");
				return true;
			}
		}
		ExtFactory.getInstance().getExtent().log(Status.INFO, "item " + text +"is not present");
		return false;
	}

	public String getSelectedItem(By object) {
		Select select = new Select(getDriver().findElement(object));
		ExtFactory.getInstance().getExtent().log(Status.INFO, "selected dropdown value is" + select.getFirstSelectedOption().getText());
		return select.getFirstSelectedOption().getText();
	}

	public void selectItemByIndex(By object, int index) {
		try {
			Select select = new Select(getDriver().findElement(object));
			select.selectByIndex(index);
			ExtFactory.getInstance().getExtent().log(Status.INFO, "selected" + index + " dropdown value");
		} catch (Exception exc) {
			exc.printStackTrace();
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public List<String> getAllItems(By object) {
		List<String> orgList = new ArrayList<String>();
		try {
			Select select = new Select(getDriver().findElement(object));
			List<WebElement> items = select.getOptions();
			for (WebElement item : items) {
				String itemText = item.getText();
				orgList.add(itemText);
			}
			ExtFactory.getInstance().getExtent().log(Status.INFO, "got all items from the dropdown  [" + object +" ]");
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		return orgList;
	}

	public void deSelectItem(By object, String text, String type) {

		try {
			Select select = new Select(getDriver().findElement(object));
			if (type == Constants.VALUE) {
				select.deselectByValue(text);

			} else if (type == Constants.TEXT) {
				select.deselectByVisibleText(text);
			}

			ExtFactory.getInstance().getExtent().log(Status.INFO, "deselected dropdown value " + text);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
	public boolean verifyItemNotPresent(By object, String text) {

		ExtFactory.getInstance().getExtent().log(Status.INFO, "item " + text +"is not present");
		return true;
	}
	
	public void selectAllItems(By object) {
		try {
			Select select = new Select(getDriver().findElement(object));
			List<WebElement> items = select.getOptions();
			for (int i = 0; i < items.size(); i++) {
				select.selectByIndex(i);
				ExtFactory.getInstance().getExtent().log(Status.INFO, "selected" + items.get(i) + " dropdown value");
			}
		} catch (Exception exc) {
			exc.printStackTrace();
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

}
