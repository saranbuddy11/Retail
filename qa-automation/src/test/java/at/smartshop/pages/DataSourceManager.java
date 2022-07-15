package at.smartshop.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import at.framework.browser.Browser;
import at.framework.browser.Factory;
import at.framework.generic.CustomisedAssert;
import at.framework.ui.CheckBox;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.tests.TestInfra;

public class DataSourceManager extends Factory {
	
	public static final By VALIDATE_DSM_HEADING = By.id("page-title");
	public static final By DSM_SEARCH_BOX = By.id("search-box");
	public static final By DSM_CHECKBOX = By.xpath("//td[@aria-describedby='datasourcemangergrid_issfenabled']//input");
	public static final By DSM_SUCCESS_POPUP = By.xpath("//div[@class='humane humane-libnotify-success']");
	public static final By VALIDATE_CSS_HEADING = By.id("CSS List");
	public static final By CSS_CREATE_NEW_BTN = By.id("newBtn");
	public static final By CSS_PATH = By.id("path");
	public static final By CSS_NAME = By.id("name");
	public static final By CSS_SAVE_BTN = By.id("saveBtn");
	public static final By CSS_CANCEL_BTN = By.id("cancelBtn");
	public static final By VALIDATE_CREATE_HEADING = By.id("CSS Create");
	public static final By VALIDATE_UPDATE_HEADING = By.id("CSS Show");
	public static final By CHECKBOX_SNOWFLAKE=By.xpath("//input[@type='checkbox']");
	public static final By CSS_GRID = By.xpath("//td[@class=' sorting_1']");
	public static final By CSS_PATH_ERROR = By.id("path-error");
	public static final By CSS_NAME_ERROR = By.id("name-error");
	public static final By CSS_SUCCESS_POPUP = By.xpath("//div[@class='humane ']");
	public static final By CSS_SEARCH_BOX= By.xpath("//input[@aria-controls='dt']");

	private TextBox textBox = new TextBox();
	private CheckBox checkBox = new CheckBox();
	public Login login = new Login();
	private NavigationBar navigationBar = new NavigationBar();
	public Browser browser = new Browser();
	private Foundation foundation = new Foundation();

	public static final By TXT_SEARCH = By.id("search-box");
	public final By SEARCH_BOX_DATA_SOURCE_MANAGER = By.xpath("//input[@id='search-box']");
	public static final String DATA_SOURCE_MANAGER_MANU = "Super#Data Source Manager";
	public static final String SNOWFLAKE = "Snowflake";
	public static final String RDS = "RDS";

	public static final By PAGINATION_LIST = By
			.xpath("//ul[@class='ui-helper-reset ui-iggrid-pagelist ui-iggrid-paging-item']//li");
	public static final By CHECKBOXS = By.xpath("//input[@type='checkbox']");

	public void unCheckCheckBox(String reportName) {
		try {
			textBox.enterText(TXT_SEARCH, reportName);
			By objCheckBox = By.xpath("//td[text()='" + reportName
					+ "']//..//td[@aria-describedby='datasourcemangergrid_issfenabled']/label/input");
			checkBox.unCheck(objCheckBox);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void checkInListOfCheckBoxes(By object1, By object2) {
		try {
			int countPaginationList = foundation.getSizeofListElement(object1);
			for (int iter = 0; iter < countPaginationList; iter++) {
				List<WebElement> paginationList = getDriver().findElements(object1);
				paginationList.get(iter).click();
				int countOfCheckboxes = foundation.getSizeofListElement(object2);
				for (int count = 0; count < countOfCheckboxes; count++) {
					List<WebElement> checkBoxList = getDriver().findElements(object2);
					foundation.objectFocusOnWebElement(checkBoxList.get(count));
					if (!checkBoxList.get(count).isSelected()) {
						checkBoxList.get(count).click();
						foundation.threadWait(2);
					}
				}
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void unCheckInListOfCheckBoxes(By object1, By object2) {
		try {
			int countPaginationList = foundation.getSizeofListElement(object1);
			for (int iter = 0; iter < countPaginationList; iter++) {
				List<WebElement> paginationList = getDriver().findElements(object1);
				paginationList.get(iter).click();			
				int countOfCheckboxes = foundation.getSizeofListElement(object2);
				for (int count = 0; count < countOfCheckboxes; count++) {
					List<WebElement> checkBoxList = getDriver().findElements(object2);
					foundation.objectFocusOnWebElement(checkBoxList.get(count));
					if (checkBoxList.get(count).isSelected()) {
						checkBoxList.get(count).click();
						foundation.threadWait(2);
					}
				}
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void switchToReportsDB(String reportsDB) {
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.navigateToMenuItem(DATA_SOURCE_MANAGER_MANU);
			if (reportsDB.equals(SNOWFLAKE)) {
				checkInListOfCheckBoxes(PAGINATION_LIST, CHECKBOXS);
			} else if (reportsDB.equals(RDS)) {
				unCheckInListOfCheckBoxes(PAGINATION_LIST, CHECKBOXS);
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
	
	public void createCSS(String css_Path) {
		try {
			foundation.click(CSS_CREATE_NEW_BTN);
			textBox.enterText(CSS_PATH, css_Path);
			textBox.enterText(CSS_NAME, css_Path);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
	
	/**
	 * This method is for switching to ReportsDB like SnowFlake or RDS
	 * @param reportsDB
	 * @param object
	 * @param dataObject
	 */
	public void switchToReportsDB(String reportsDB, By object, String dataObject) {
		try {
			// Select Menu and Menu Item
			navigationBar.navigateToMenuItem(DATA_SOURCE_MANAGER_MANU);
			textBox.enterText(SEARCH_BOX_DATA_SOURCE_MANAGER, dataObject );
			WebElement checkBoxList = getDriver().findElement(object);
			if (reportsDB.equals(SNOWFLAKE)) {
				foundation.objectFocusOnWebElement(checkBoxList);
				if (!checkBoxList.isSelected()) {
					checkBoxList.click();
					foundation.threadWait(2);
				}
			} else if (reportsDB.equals(RDS)) {
				foundation.objectFocusOnWebElement(checkBoxList);
				if (checkBoxList.isSelected()) {
					checkBoxList.click();
					foundation.threadWait(2);
				}
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
	
	/**
	 * search consumer and verify snow flake is checked
	 * @param consumer
	 */
	public void searchConsumerAndVerifySnowFlakeIsChecked(String consumer) {
		CustomisedAssert.assertTrue(foundation.isDisplayed(VALIDATE_DSM_HEADING));
		foundation.waitforElementToBeVisible(DSM_SEARCH_BOX, 5);
		textBox.enterText(DSM_SEARCH_BOX, consumer);
		foundation.waitforElementToBeVisible(CHECKBOX_SNOWFLAKE, 3);
		CustomisedAssert.assertTrue(checkBox.isChecked(CHECKBOX_SNOWFLAKE));
	}
	

}
