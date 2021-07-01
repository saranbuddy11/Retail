package at.framework.ui;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
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
import at.framework.generic.DateAndTime;
import at.framework.reportsetup.ExtFactory;
import at.smartshop.keys.Constants;

public class Foundation extends Factory {
	DateAndTime dateAndTime = new DateAndTime();

	public boolean isDisplayed(By object) {
		boolean isElementDisplayed = false;
		try {
			isElementDisplayed = getDriver().findElement(object).isDisplayed();
			if (ExtFactory.getInstance().getExtent() != null) {
				ExtFactory.getInstance().getExtent().log(Status.INFO, object + " is displayed");
			}
		} catch (NoSuchElementException exc) {
			isElementDisplayed = false;
		} catch (Exception exc) {
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
			if (ExtFactory.getInstance().getExtent() != null) {
				ExtFactory.getInstance().getExtent().log(Status.INFO, "clicked on [ " + object + " ]");
			}
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	public void fluentWait(String object, int waitTime) {
		try {
			FluentWait<WebDriver> wait = new FluentWait<>(getDriver()).withTimeout(Duration.ofSeconds(waitTime))
					.pollingEvery(Duration.ofSeconds(5)).ignoring(NoSuchElementException.class);

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

	public WebElement waitforClikableElement(By object, int waitTime) {
		WebElement element = null;
		try {
			WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
			element = wait.until(ExpectedConditions.elementToBeClickable(object));
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
			if (ExtFactory.getInstance().getExtent() != null) {
				ExtFactory.getInstance().getExtent().log(Status.INFO, object + " is enabled");
			}
		} catch (Exception exc) {
			ObjEnabled = false;
		}
		return ObjEnabled;
	}

	public int getSizeofListElement(By object) {
		int sizeofObj = 0;
		try {
			sizeofObj = getDriver().findElements(object).size();
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
		return sizeofObj;
	}

	public void threadWait(int seconds) {
		try {
			long timeMilliSec = seconds * 1000;
			Thread.sleep(timeMilliSec);
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	public String getBGColor(By object) {
		String hexColor = null;
		try {
			WebElement element = getDriver().findElement(object);
			String colorValue = element.getCssValue("background-color");
			hexColor = Color.fromString(colorValue).asHex();
			ExtFactory.getInstance().getExtent().log(Status.INFO, "Back Ground color for " + object);
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
		return hexColor;
	}

	public void doubleClick(By object) {
		try {
			Actions action = new Actions(getDriver());
			action.doubleClick(getDriver().findElement(object)).perform();
			if (ExtFactory.getInstance().getExtent() != null) {
				ExtFactory.getInstance().getExtent().log(Status.INFO, "clicked on [ " + object + " ]");
			}
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	public List<String> getTextofListElement(By object) {
		String text = null;
		List<String> elementsText = new ArrayList<String>();
		try {
			List<WebElement> ListElement = getDriver().findElements(object);
			for (WebElement webElement : ListElement) {
				text = webElement.getText();
				elementsText.add(text);
			}

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
		return elementsText;
	}

	public boolean verifySortDate(By object, String type, String pattern) {
		boolean isSorted = false;
		Collection<LocalDate> listDate = dateAndTime.stringListToDateList(getTextofListElement(object), pattern);
		if (type.equals(Constants.ASCENDING)) {
			isSorted = listDate.stream().sorted(Comparator.naturalOrder()).collect(Collectors.toList())
					.equals(listDate);
		} else if (type.equals(Constants.DESCENDING)) {
			isSorted = listDate.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList())
					.equals(listDate);
		}
		return isSorted;
	}

	public boolean verifySortText(By object, String type) {
		boolean isSorted = false;
		List<String> listOfText = getTextofListElement(object);
		if (type.equals(Constants.ASCENDING)) {
			isSorted = listOfText.stream().sorted(Comparator.naturalOrder()).collect(Collectors.toList())
					.equals(listOfText);
		} else if (type.equals(Constants.DESCENDING)) {
			isSorted = listOfText.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList())
					.equals(listOfText);
		}
		return isSorted;
	}

	public void adjustBrowerSize(String size) {
		try {
			JavascriptExecutor executor = (JavascriptExecutor) getDriver();
			executor.executeScript("document.body.style.zoom = '" + size + "'");
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	public void objectClick(By object) {
		try {
			JavascriptExecutor executor = (JavascriptExecutor) getDriver();
			executor.executeScript("arguments[0].click();", getDriver().findElement(object));
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

}
