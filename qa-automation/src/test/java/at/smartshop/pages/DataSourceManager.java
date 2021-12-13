package at.smartshop.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.aventstack.extentreports.Status;

import at.framework.browser.Browser;
import at.framework.browser.Factory;
import at.framework.reportsetup.ExtFactory;
import at.framework.ui.CheckBox;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.FilePath;
import at.smartshop.tests.TestInfra;

public class DataSourceManager extends Factory {

	private TextBox textBox = new TextBox();
	private CheckBox checkBox = new CheckBox();
	public Login login = new Login();
	private NavigationBar navigationBar = new NavigationBar();
	public Browser browser = new Browser();
	private Foundation foundation = new Foundation();

	public static final By TXT_SEARCH = By.id("search-box");
	public static final String dataSourceManagerMenu = "Super#Data Source Manager";

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
			Assert.fail(exc.toString());
		}
	}

	public void checkInListOfCheckBoxes(By object1, By object2) {
		try {
			List<WebElement> paginationList1 = getDriver().findElements(object1);
			for (int iter = 0; iter < paginationList1.size(); iter++) {
				List<WebElement> paginationList2 = getDriver().findElements(object1);
				paginationList2.get(iter).click();
				List<WebElement> checkBoxList1 = getDriver().findElements(object2);
				for (int count = 0; count < checkBoxList1.size(); count++) {
					List<WebElement> checkBoxList2 = getDriver().findElements(object2);
					if (!checkBoxList2.get(count).isSelected()) {
						checkBoxList2.get(count).click();
						Thread.sleep(2000);
					}
				}
				System.out.println("****pagination : " + iter);
			}
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	public void unCheckInListOfCheckBoxes(By object1, By object2) {
		try {
			List<WebElement> paginationList1 = getDriver().findElements(object1);
			for (int iter = 0; iter < paginationList1.size(); iter++) {
				List<WebElement> paginationList2 = getDriver().findElements(object1);
				paginationList2.get(iter).click();
				List<WebElement> checkBoxList1 = getDriver().findElements(object2);
				for (int count = 0; count < checkBoxList1.size(); count++) {
					List<WebElement> checkBoxList2 = getDriver().findElements(object2);
					if (checkBoxList2.get(count).isSelected()) {
						checkBoxList2.get(count).click();
						Thread.sleep(2000);
					}
				}
				System.out.println("****pagination : " + iter);
			}
		} catch (Exception exc) {
			Assert.fail(exc.toString());
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
			navigationBar.navigateToMenuItem(dataSourceManagerMenu);
//		textBox.enterText(By.id("search-box"), rstReportListData.get(CNReportList.REPORT_NAME));
			if (reportsDB.equals("Snowflake")) {
				Thread.sleep(5000);
				checkInListOfCheckBoxes(PAGINATION_LIST, CHECKBOXS);

//				checkBox.check(By.xpath(
//						"//tr[contains(@data-id, 'yyyyy8ea13e811eba2300afaa3adjadu')]//input[@type='checkbox']"));
//				Thread.sleep(5000);
				System.out.println("*****CHEKED");
			} else if (reportsDB.equals("RDC")) {
				Thread.sleep(5000);
				unCheckInListOfCheckBoxes(PAGINATION_LIST, CHECKBOXS);

//				checkBox.unCheck(By.xpath(
//						"//tr[contains(@data-id, 'yyyyy8ea13e811eba2300afaa3adjadu')]//input[@type='checkbox']"));
//				Thread.sleep(5000);
				System.out.println("*****UNCHEKED");
			}
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
}
