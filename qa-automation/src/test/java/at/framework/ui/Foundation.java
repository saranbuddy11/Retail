package at.framework.ui;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.Status;
import com.google.common.base.Function;

import at.framework.browser.Factory;
import at.framework.generic.DateAndTime;
import at.framework.reportsetup.ExtFactory;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.tests.TestInfra;

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
			TestInfra.failWithScreenShot(exc.toString());
		}
		return isElementDisplayed;
	}

	public String getText(By object) {
		String text = null;
		try {
			text = getDriver().findElement(object).getText();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		return (text);
	}

	public void click(By object) {
		try {
			// waitforClikableElement(object, Constants.SHORT_TIME);
			// waitforElement(object, Constants.SHORT_TIME);
			objectFocus(object);
			getDriver().findElement(object).click();
			if (ExtFactory.getInstance().getExtent() != null) {
				ExtFactory.getInstance().getExtent().log(Status.INFO, "clicked on [ " + object + " ]");
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
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
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public WebElement waitforElement(By object, int waitTime) {
		WebElement element = null;
		try {
			WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
			element = wait.until(ExpectedConditions.visibilityOfElementLocated(object));
			if (ExtFactory.getInstance().getExtent() != null)
				ExtFactory.getInstance().getExtent().log(Status.INFO,
						"waited for element [ " + object + " ] and the object is visible");
		} catch (TimeoutException exc) {
			// Continue
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		return element;
	}

	public WebElement waitforElementToBeVisible(By object, int waitTime) {
		WebElement element = null;
		int waitTimeInSec = waitTime;
		boolean displayed = false;
		long startTime = System.currentTimeMillis();
		do {
			try {
				WebDriverWait wait = new WebDriverWait(getDriver(), waitTimeInSec);
				element = wait.until(ExpectedConditions.visibilityOfElementLocated(object));
				displayed = getDriver().findElement(object).isDisplayed();
				ExtFactory.getInstance().getExtent().log(Status.INFO,
						"waited for element [ " + object + "] and the object is visible ");
			} catch (TimeoutException exc) {
				// Continue
			} catch (Exception exc) {
				TestInfra.failWithScreenShot(exc.toString());
			}
		} while ((!displayed) && (System.currentTimeMillis() - startTime) < waitTime * 1000);
		return element;
	}

	public WebElement waitforClikableElement(By object, int waitTime) {
		WebElement element = null;
		try {
			WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
			element = wait.until(ExpectedConditions.elementToBeClickable(object));
			if (ExtFactory.getInstance().getExtent() != null)
				ExtFactory.getInstance().getExtent().log(Status.INFO,
						"waited for element clickable [ " + object + " ]");
		} catch (TimeoutException exc) {
			// Continue
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		return element;
	}

	public void refreshPage() {
		try {
			getDriver().navigate().refresh();
			ExtFactory.getInstance().getExtent().log(Status.INFO, "page refreshed");
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public String getTextAttribute(By object, String attribute) {
		String textAttribute = null;
		try {
			textAttribute = getDriver().findElement(object).getAttribute(attribute);
			if (ExtFactory.getInstance().getExtent() != null) {
				ExtFactory.getInstance().getExtent().log(Status.INFO, object + " value is " + textAttribute);
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		return textAttribute;
	}

	public void objectFocus(By element) {
		Actions action = new Actions(getDriver());
		Action seriesOfActions = action.moveToElement(getDriver().findElement(element)).build();
		seriesOfActions.perform();
		if (ExtFactory.getInstance().getExtent() != null)
			ExtFactory.getInstance().getExtent().log(Status.INFO, "the object [" + element + " ] is focused");
	}

	public void objectFocusOnWebElement(WebElement element) {
		Actions action = new Actions(getDriver());
		Action seriesOfActions = action.moveToElement(element).build();
		seriesOfActions.perform();
		if (ExtFactory.getInstance().getExtent() != null)
			ExtFactory.getInstance().getExtent().log(Status.INFO, "the object [" + element + " ] is focused");
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
			if (ExtFactory.getInstance().getExtent() != null)
				ExtFactory.getInstance().getExtent().log(Status.INFO,
						object + "count of list element is " + sizeofObj + " ");
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		return sizeofObj;
	}

	public void threadWait(int seconds) {
		try {
			long timeMilliSec = seconds * 1000;
			Thread.sleep(timeMilliSec);
			if (ExtFactory.getInstance().getExtent() != null)
				ExtFactory.getInstance().getExtent().log(Status.INFO, "thread wait for " + seconds + " seconds");
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public String getBGColor(By object) {
		String hexColor = null;
		try {
			WebElement element = getDriver().findElement(object);
			String colorValue = element.getCssValue("background-color");
			hexColor = Color.fromString(colorValue).asHex();
			ExtFactory.getInstance().getExtent().log(Status.INFO, "Back Ground color for " + object + "is " + hexColor);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		return hexColor;
	}

	public void doubleClick(By object) {
		try {
			Actions action = new Actions(getDriver());
			action.doubleClick(getDriver().findElement(object)).perform();
			if (ExtFactory.getInstance().getExtent() != null) {
				ExtFactory.getInstance().getExtent().log(Status.INFO, "double clicked on [ " + object + " ]");
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
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
			ExtFactory.getInstance().getExtent().log(Status.INFO, "got the text of list element [ " + object + " ]");
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		return elementsText;
	}

	public boolean verifySortDate(By object, String type, String pattern) {
		boolean isSorted = false;
		Collection<LocalDate> listDate = dateAndTime.stringListToDateList(getTextofListElement(object), pattern);
		if (type.equals(Constants.ASCENDING)) {
			isSorted = listDate.stream().sorted(Comparator.naturalOrder()).collect(Collectors.toList())
					.equals(listDate);
			ExtFactory.getInstance().getExtent().log(Status.INFO, "date sorted in ascending manner-" + isSorted);
		} else if (type.equals(Constants.DESCENDING)) {
			isSorted = listDate.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList())
					.equals(listDate);
			ExtFactory.getInstance().getExtent().log(Status.INFO, "date sorted in decending manner-" + isSorted);
		}
		return isSorted;
	}

	public boolean verifySortText(By object, String type) {
		boolean isSorted = false;
		List<String> listOfText = getTextofListElement(object);
		if (type.equals(Constants.ASCENDING)) {
			isSorted = listOfText.stream().sorted(Comparator.naturalOrder()).collect(Collectors.toList())
					.equals(listOfText);
			ExtFactory.getInstance().getExtent().log(Status.INFO, "text sorted in decending manner-" + isSorted);
		} else if (type.equals(Constants.DESCENDING)) {
			isSorted = listOfText.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList())
					.equals(listOfText);
			ExtFactory.getInstance().getExtent().log(Status.INFO, "text sorted in decending manner-" + isSorted);
		}
		return isSorted;
	}

	public void adjustBrowerSize(String size) {
		try {
			JavascriptExecutor executor = (JavascriptExecutor) getDriver();
			executor.executeScript("document.body.style.zoom = '" + size + "'");
			ExtFactory.getInstance().getExtent().log(Status.INFO, "adjusted browser size to " + size);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void objectClick(By object) {
		try {
			JavascriptExecutor executor = (JavascriptExecutor) getDriver();
			executor.executeScript("arguments[0].click();", getDriver().findElement(object));
			ExtFactory.getInstance().getExtent().log(Status.INFO, "clicked object [ " + object + " ] using javascript");
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void scrollIntoViewElement(By object) {
		try {
			JavascriptExecutor executor = (JavascriptExecutor) getDriver();
			executor.executeScript("arguments[0].scrollIntoView(true);", getDriver().findElement(object));
			ExtFactory.getInstance().getExtent().log(Status.INFO,
					"Scroll into view object [ " + object + " ] using javascript");
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void scrollUp(By object) {
		try {
			JavascriptExecutor executor = (JavascriptExecutor) getDriver();
			executor.executeScript("arguments[0].scrollIntoView(false);", getDriver().findElement(object));
			ExtFactory.getInstance().getExtent().log(Status.INFO,
					"Scroll up to view object [ " + object + " ] using javascript");
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void copyFile(String from, String to) {

		Path sourceDirectory = Paths.get(from);
		Path targetDirectory = Paths.get(to);

		try {
			Files.copy(sourceDirectory, targetDirectory);
			if (ExtFactory.getInstance().getExtent() != null) {
				ExtFactory.getInstance().getExtent().log(Status.INFO, "File Copied Successfully");
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public boolean isFileExists(String filePath) {
		File file = new File(filePath);
		if (!file.exists()) {
			ExtFactory.getInstance().getExtent().log(Status.INFO, "File not exist");
			return true;
		} else
			ExtFactory.getInstance().getExtent().log(Status.INFO, "File exists");
		return false;
	}

	public void deleteFile(String filePath) {
		try {
			File file = new File(filePath);
			file.delete();
			if (ExtFactory.getInstance().getExtent() != null) {
				ExtFactory.getInstance().getExtent().log(Status.INFO, filePath + " -File Deleted Successfully");
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void alertAccept() {
		try {
			Alert alert = getDriver().switchTo().alert();
			alert.accept();
			ExtFactory.getInstance().getExtent().log(Status.INFO, "Accepted the alert");
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void alertDismiss() {
		try {
			Alert alert = getDriver().switchTo().alert();
			alert.dismiss();
			ExtFactory.getInstance().getExtent().log(Status.INFO, "Dismissed the alert");
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public String getAlertMessage() {
		String text = null;
		try {
			Alert alert = getDriver().switchTo().alert();
			text = alert.getText();

			ExtFactory.getInstance().getExtent().log(Status.INFO, "Alert Message ");
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		return text;
	}

	public Boolean waitforElementToDisappear(By object, int waitTime) {
		Boolean element = null;
		try {
			WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
			element = wait.until(ExpectedConditions.invisibilityOfElementLocated(object));
			ExtFactory.getInstance().getExtent().log(Status.INFO, "Waited for element [" + object + " ] to disappear");
		} catch (TimeoutException e) {
			// Continue
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		return element;
	}

	public List<String> getAttributeValueofListElement(By object, String attribute) {
		String attributeValue = null;
		List<String> elementsAttributeValue = new ArrayList<String>();
		try {
			List<WebElement> ListElement = getDriver().findElements(object);
			for (WebElement webElement : ListElement) {
				attributeValue = webElement.getAttribute(attribute);
				elementsAttributeValue.add(attributeValue);
			}
			ExtFactory.getInstance().getExtent().log(Status.INFO,
					"got the element attribute value for the object [" + object + " ]");
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		return elementsAttributeValue;
	}

	public String getAttributeValue(By object) {
		String textAttribute = null;
		try {
			textAttribute = getDriver().findElement(object).getAttribute(Constants.ATTRIBUTE_VALUE);
			if (ExtFactory.getInstance().getExtent() != null) {
				ExtFactory.getInstance().getExtent().log(Status.INFO, object + " value is " + textAttribute);
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		return textAttribute;
	}

	public String getAttribute(By object, String attribute) {
		String textAttribute = null;
		try {
			textAttribute = getDriver().findElement(object).getAttribute(attribute);
			if (ExtFactory.getInstance().getExtent() != null) {
				ExtFactory.getInstance().getExtent().log(Status.INFO, object + " value is " + textAttribute);
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		return textAttribute;
	}

	public void navigateToBackPage() {
		try {
			getDriver().navigate().back();
			ExtFactory.getInstance().getExtent().log(Status.INFO, "navigated back");
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public boolean isDisabled(By object) {
		boolean isElementDisabled = true;
		try {
			isElementDisabled = !getDriver().findElement(object).isEnabled();
			if (ExtFactory.getInstance().getExtent() != null) {
				ExtFactory.getInstance().getExtent().log(Status.INFO, object + " is disabled");
			}
		} catch (NoSuchElementException exc) {
			isElementDisabled = false;
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		return isElementDisabled;
	}

	public void WaitForAjax(int waitTime) {
		try {
			long startTime = System.currentTimeMillis();
			while (true) {

				Boolean ajaxIsComplete = (Boolean) ((JavascriptExecutor) getDriver())
						.executeScript("return jQuery.active == 0");
				if (ajaxIsComplete || (System.currentTimeMillis() - startTime) < waitTime * 1000) {
					break;
				}
				threadWait(100);
			}

		} catch (TimeoutException exc) {
			// continue
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public String getBorderColor(By object) {
		String hexColor = null;
		try {
			WebElement element = getDriver().findElement(object);
			String colorValue = element.getCssValue("border-color");
			hexColor = Color.fromString(colorValue).asHex();
			ExtFactory.getInstance().getExtent().log(Status.INFO, "Border color for " + object + "is " + hexColor);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		return hexColor;
	}

	public String getTextColor(By object) {
		String hexColor = null;
		try {
			WebElement element = getDriver().findElement(object);
			String colorValue = element.getCssValue("color");
			hexColor = Color.fromString(colorValue).asHex();
			ExtFactory.getInstance().getExtent().log(Status.INFO, "Text color for " + object + "is " + hexColor);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		return hexColor;
	}

	public Point getCoordinates(By object) {
		Point point = null;
		try {
			point = getDriver().findElement(object).getLocation();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		return point;
	}

	/**
	 * Scroll to the Bottom of Page
	 */
	public void scrollToBottom() {
		try {
			JavascriptExecutor executor = (JavascriptExecutor) getDriver();
			executor.executeScript("window.scrollTo(0, document.body.scrollHeight)");
			ExtFactory.getInstance().getExtent().log(Status.INFO, "Scrolled to bottom of thepage using javascript");
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * To get the Font Size using CSS value
	 * 
	 * @param object
	 * @return
	 */
	public String getFontSize(By object) {
		String fontSize = null;
		try {
			WebElement element = getDriver().findElement(object);
			fontSize = element.getCssValue("font-size");
			ExtFactory.getInstance().getExtent().log(Status.INFO,
					"Font Size of this Element " + object + "is " + fontSize);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		return fontSize;
	}

	/**
	 * To get Style properties using PseudoElement
	 * 
	 * @param object
	 * @param pseudo
	 * @param style
	 * @return
	 */
	public String getStyleUsingPseudoElemet(String object, String pseudo, String style) {
		JavascriptExecutor js = (JavascriptExecutor) getDriver();
		String text = js.executeScript("return window.getComputedStyle(document.querySelector('" + object + "'),'::"
				+ pseudo + "').getPropertyValue('" + style + "')").toString();
		return text;
	}

	/**
	 * To get the Outline Color
	 * 
	 * @param object
	 * @return
	 */
	public String getOutLineColor(By object) {
		String hexColor = null;
		try {
			WebElement element = getDriver().findElement(object);
			String colorValue = element.getCssValue("outline-color");
			hexColor = Color.fromString(colorValue).asHex();
			ExtFactory.getInstance().getExtent().log(Status.INFO, "outline-color for " + object + "is " + hexColor);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		return hexColor;
	}

	/**
	 * To Check the file is Downloaded or not
	 * 
	 * @param fileName
	 * @return
	 */
	public boolean isFileDownloaded(String fileName) {
		File dir = new File(FilePath.PATH_TO_DOWNLOAD);
		File[] dirContents = dir.listFiles();
		threadWait(Constants.LONG_TIME);

		for (int i = 0; i < dirContents.length; i++) {
			if (dirContents[i].getName().contains(fileName)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Reading the PDF file Full name
	 * 
	 * @param fileName
	 * @return
	 */
	public String getPDFFileActualName(String fileName) {
		File dir = new File(FilePath.PATH_TO_DOWNLOAD);
		File[] dirContents = dir.listFiles();
		threadWait(Constants.SHORT_TIME);
		String actualName = "";

		for (int i = 0; i < dirContents.length; i++) {
			if (dirContents[i].getName().contains(fileName)) {
				actualName = dirContents[i].getName();
			}
		}
		return actualName;
	}

	/**
	 * Reading the PDF file content
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public String readPDFFile(String filePath) throws IOException {
		File file = new File(filePath);
		FileInputStream fis = new FileInputStream(file);
		PDDocument doc = Loader.loadPDF(fis);
		String content = new PDFTextStripper().getText(doc);
		doc.close();
		fis.close();
		threadWait(Constants.SHORT_TIME);
		return content;
	}

	/**
	 * Getting the Page Count of PDF file
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public String getPDFFilePageCount(String filePath) throws IOException {
		File file = new File(filePath);
		FileInputStream fis = new FileInputStream(file);
		PDDocument doc = Loader.loadPDF(fis);
		String count = String.valueOf(doc.getPages().getCount());
		doc.close();
		fis.close();
		threadWait(Constants.SHORT_TIME);
		return count;
	}

	/**
	 * Get the Number of Count/Occurences of Particular word in sentences
	 * 
	 * @param str
	 * @param word
	 * @return
	 */
	public int countOccurrences(String str, String word) {
		String a[] = str.split("\\s");
		int count = 0;
		for (int i = 0; i < a.length; i++) {
			if (a[i].contains(word))
				count++;
		}
		return count;
	}

	/**
	 * Get the Number of Count/Occurences of Particular Character in String
	 * 
	 * @param str
	 * @param word
	 * @return
	 */
	public int countOccurrencesofChar(String str, char letter) {
		int count = 0;
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == letter)
				count++;
		}
		return count;
	}

	/**
	 * Get the Particular word by using index position from Sentence
	 * 
	 * @param str
	 * @param position
	 * @return
	 */
	public String getParticularWordFromSentence(String str, int position) {
		String a[] = str.split("\\s");
		String word = a[position];
		return word;
	}

	/**
	 * Get the numbers from a String
	 * 
	 * @param str
	 */
	public String getNumbersFromString(String str) {
		char[] chars = str.toCharArray();
		StringBuilder sb = new StringBuilder();
		for (char c : chars) {
			if (Character.isDigit(c)) {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	/**
	 * Checks the String contain numbers only or not
	 * 
	 * @param strNum
	 * @return
	 */
	public boolean isNumeric(String strNum) {
		if (strNum == null) {
			return false;
		}
		try {
			double d = Double.parseDouble(strNum);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	/**
	 * Clicking Space Bar using Keyboard Actions
	 * 
	 * @throws AWTException
	 */
	public void clickSpaceBar() throws AWTException {
		Robot r = new Robot();
		r.keyPress(KeyEvent.VK_SPACE);
		r.keyRelease(KeyEvent.VK_SPACE);
	}

	/**
	 * Zoom out of Page using Keyboard Actions
	 * 
	 * @throws AWTException
	 */
	public void pageZoomOut() throws AWTException {
		Robot r = new Robot();
		r.keyPress(KeyEvent.VK_CONTROL);
		r.keyPress(KeyEvent.VK_SUBTRACT);
		r.keyRelease(KeyEvent.VK_CONTROL);
		r.keyRelease(KeyEvent.VK_SUBTRACT);
	}

	/**
	 * Zoom in of Page using Keyboard Actions
	 * 
	 * @throws AWTException
	 */
	public void pageZoomIn() throws AWTException {
		Robot r = new Robot();
		r.keyPress(KeyEvent.VK_CONTROL);
		r.keyPress(KeyEvent.VK_ADD);
		r.keyRelease(KeyEvent.VK_CONTROL);
		r.keyRelease(KeyEvent.VK_ADD);
	}

	/**
	 * get Dimention
	 * @param object
	 * @return
	 */
	public Rectangle getDimention(By object) {
		Rectangle rect = null;
		try {
			rect = getDriver().findElement(object).getRect();
			rect.getDimension();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		return rect;
	}

	/**
	 * Clicking enter using Keyboard Actions
	 * 
	 * @throws AWTException
	 */
	public void clickEnter() throws AWTException {
		Robot r = new Robot();
		r.keyPress(KeyEvent.VK_ENTER);
		r.keyRelease(KeyEvent.VK_ENTER);
	}
	/**

	 * Clicking enter using Keyboard Actions
	 * Clicking Shift and Down using Keyboard Actions
	 * 
	 * @throws AWTException
	 */
	public void clickShiftAndDown() throws AWTException {
		Robot r = new Robot();
		r.keyPress(KeyEvent.VK_SHIFT);
		r.keyPress(KeyEvent.VK_DOWN);
		r.keyRelease(KeyEvent.VK_DOWN);
		r.keyRelease(KeyEvent.VK_SHIFT);
	}

	
}
			