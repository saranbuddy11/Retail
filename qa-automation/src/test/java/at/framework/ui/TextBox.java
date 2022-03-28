package at.framework.ui;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import com.aventstack.extentreports.Status;

import at.framework.browser.Factory;
import at.framework.reportsetup.ExtFactory;
import at.smartshop.keys.Constants;
import at.smartshop.tests.TestInfra;

public class TextBox extends Factory {
	private Foundation foundation = new Foundation();

	public void enterText(By object, String text) {
		try {
			getDriver().findElement(object).clear();
			getDriver().findElement(object).sendKeys(text);

			if (ExtFactory.getInstance().getExtent() != null) {
				ExtFactory.getInstance().getExtent().log(Status.INFO, "entered text " + text);
			}

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public String getTextFromInput(By object) {
		String text = null;
		try {
			text = getDriver().findElement(object).getAttribute("value");
			ExtFactory.getInstance().getExtent().log(Status.INFO,
					"got the text [" + text + " ] from the input [" + object + " ]");
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		return text;
	}

	public void enterText(By object, Keys key) {
		try {
			getDriver().findElement(object).clear();
			getDriver().findElement(object).sendKeys(key);

			if (ExtFactory.getInstance().getExtent() != null) {
				ExtFactory.getInstance().getExtent().log(Status.INFO, "entered keys " + key);
			}

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}

	}

	public void enterKeypadNumber(String number) {
		char[] charArray = number.toCharArray();
		for (char eachChar : charArray) {
			if (eachChar == ' ') {
				foundation.click(By.xpath("//*[text()='Space']"));
			} else {
				foundation.click(By.xpath("//*[@class='keyboardNumber']//*[text()='" + eachChar + "']"));
			}
		}
	}

	public void enterPin(String pin) {
		for (int i = 0; i < pin.length(); i++) {
			int number = Integer.parseInt(pin.substring(i, i + 1));
			foundation.waitforElementToBeVisible(By.xpath("//td[text()='" + number + "']"), Constants.SHORT_TIME);
			foundation.click(By.xpath("//td[text()='" + number + "']"));
		}
	}

	public void enterDriverPin(String pin) {
		for (int i = 0; i < pin.length(); i++) {
			int number = Integer.parseInt(pin.substring(i, i + 1));
			foundation.click(By.xpath("//input[@value='" + number + "']"));
		}
	}

	public void deleteKeypadText(String priviousText) {
		for (int i = 0; i <= priviousText.length(); i++) {
			foundation.click(By.xpath("//*[text()='Del']"));
		}
	}

	public List<String> getValueofListElement(By object) {
		String text = null;
		List<String> elementsText = new ArrayList<String>();
		try {
			List<WebElement> ListElement = getDriver().findElements(object);
			for (WebElement webElement : ListElement) {
				text = webElement.getAttribute("value");
				elementsText.add(text);
			}
			ExtFactory.getInstance().getExtent().log(Status.INFO, "got the text of list element [ " + object + " ]");
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		return elementsText;
	}

	public void enterTextOnFocus(By object, String text) {
		try {
			foundation.objectFocus(object);
			getDriver().findElement(object).clear();
			getDriver().findElement(object).sendKeys(text);

			if (ExtFactory.getInstance().getExtent() != null) {
				ExtFactory.getInstance().getExtent().log(Status.INFO, "entered text " + text);
			}

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void clearText(By object) {
		try {
			foundation.objectFocus(object);
			getDriver().findElement(object).clear();
			if (ExtFactory.getInstance().getExtent() != null) {
				ExtFactory.getInstance().getExtent().log(Status.INFO, "cleared the text ");
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void enterKeypadTextWithCaseSensitive(String text) {
		char[] charArray = text.toCharArray();
		for (int i = 0; i < charArray.length; i++) {
			if (charArray[i] == ' ') {
				foundation.click(By.xpath("//*[text()='Space']"));
				foundation.click(By.xpath("//*[text()='abc']"));
				foundation.threadWait(Constants.ONE_SECOND);
			} else if (charArray[i] >= 'A' && charArray[i] <= 'Z') {
				if (i == 0) {
					foundation.click(By.xpath("//*[text()='" + charArray[i] + "']"));
				} else {
					foundation.objectClick(By.xpath("//*[text()='ABC']"));
					foundation.click(By.xpath("//*[text()='" + charArray[i] + "']"));
					foundation.click(By.xpath("//*[text()='abc']"));
					foundation.threadWait(Constants.ONE_SECOND);
				}
			} else {
				foundation.click(By.xpath("//*[text()='" + charArray[i] + "']"));
			}
		}
	}

	public void enterKeypadText(String text) {
		char[] charArray = text.toCharArray();
		for (char eachChar : charArray) {
			if (eachChar == ' ') {
				foundation.click(By.xpath("//*[text()='Space']"));
				foundation.click(By.xpath("//*[text()='abc']"));
				foundation.threadWait(Constants.ONE_SECOND);
			} else {
				foundation.click(By.xpath("//*[text()='" + eachChar + "']"));
			}
		}
	}
}
