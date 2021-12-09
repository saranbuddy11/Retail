package at.smartshop.tests;

import static org.testng.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import at.framework.database.mssql.Queries;
import at.framework.database.mssql.ResultSets;
import at.framework.files.Excel;
import at.framework.generic.Numbers;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.database.columns.CNDeviceList;
import at.smartshop.database.columns.CNNavigationMenu;
import at.smartshop.database.columns.CNSuperList;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.pages.NavigationBar;
import at.smartshop.pages.CorporateAccountList;
import at.smartshop.pages.DeviceList;
import at.smartshop.pages.FinanceList;

public class Finance extends TestInfra {

	private ResultSets dataBase = new ResultSets();
	private NavigationBar navigationBar = new NavigationBar();
	private Foundation foundation = new Foundation();
	private Map<String, String> rstNavigationMenuData;
	private Map<String, String> rstSuperListData;
	private TextBox textBox = new TextBox();
	private Excel excel = new Excel();
	private Dropdown dropDown = new Dropdown();
	private Numbers numbers = new Numbers();

	@Test(description = "166631-Validate all links on EFT Disbursement Process Page")
	public void ValidateAllEFTDisbursementLinks() {

		final String CASE_NUM = "166631";
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstSuperListData = dataBase.getSuperListData(Queries.SUPER, CASE_NUM);

		final String disbursement_Date = rstSuperListData.get(CNSuperList.DISBURSEMENT_DATE);

		try {

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Validate the heading
			foundation.waitforElement(FinanceList.VALIDATE_HEADING, Constants.SHORT_TIME);
			assertTrue(foundation.isDisplayed(FinanceList.VALIDATE_HEADING));
			assertTrue(foundation.isDisplayed(FinanceList.DISBURSEMENT_HEADING));
			assertTrue(foundation.isDisplayed(FinanceList.VALIDATE_DATE));
			Assert.assertEquals(foundation.getText(FinanceList.VALIDATE_DATE), disbursement_Date);

			// download disbursement report assertion
			foundation.click(FinanceList.DOWNLOAD_DISBURSEMENT_REPORT);
			Assert.assertTrue(excel.isFileDownloaded(FilePath.EXCEL_DISBURSEMENT_EXPORT_SRC));

			// download view Variance
			foundation.click(FinanceList.VIEW_VARIANCE);
			foundation.click(FinanceList.EXPORT_TOEXCEL);
			Assert.assertTrue(excel.isFileDownloaded(FilePath.EXCEL_VARIANCE_EXPORT_SRC));

			// download CSV Report
			foundation.click(FinanceList.BACK_TO_DISBURSEMENT_PAGE);
			foundation.click(FinanceList.DOWNLOAD_CSV_REPORT);
			Assert.assertTrue(excel.isFileDownloaded(FilePath.EXCEL_CSV_REPORT_EXPORT_SRC));

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// delete files
			foundation.deleteFile(FilePath.EXCEL_DISBURSEMENT_EXPORT_SRC);
			foundation.deleteFile(FilePath.EXCEL_VARIANCE_EXPORT_SRC);
			foundation.deleteFile(FilePath.EXCEL_CSV_REPORT_EXPORT_SRC);

		}
	}

	@Test(description = "166632-Validates the error messages for mandatory fields for Disbursement Adjustment")
	public void ValidateMandatoryDisbursementAdjustment() {

		final String CASE_NUM = "166632";
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstSuperListData = dataBase.getSuperListData(Queries.SUPER, CASE_NUM);

		List<String> totalExpectedData = Arrays
				.asList(rstSuperListData.get(CNSuperList.ERROR_MESSAGE).split(Constants.DELIMITER_TILD));
		String org_Error = totalExpectedData.get(0);
		String notes_Error = totalExpectedData.get(1);

		try {

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Validate the heading
			foundation.waitforElement(FinanceList.VALIDATE_DISBURSEMENT_HEADING, Constants.SHORT_TIME);
			assertTrue(foundation.isDisplayed(FinanceList.VALIDATE_DISBURSEMENT_HEADING));

			// Click on Save btn
			foundation.click(FinanceList.SAVE_BTN);
			Assert.assertEquals(foundation.getText(FinanceList.ORG_ERROR), org_Error);
			Assert.assertEquals(foundation.getText(FinanceList.NOTES_ERROR), notes_Error);

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "166633-Enter the information in the fields and click on 'Cancel' and 'Save' button for Disbursement Adjustment Page")
	public void DisbursementAdjustmentAllFields() {

		final String CASE_NUM = "166633";
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstSuperListData = dataBase.getSuperListData(Queries.SUPER, CASE_NUM);
		final String amount = String.valueOf(numbers.generateRandomNumber(0, 999));

		List<String> disbursementPage = Arrays
				.asList(rstSuperListData.get(CNSuperList.DISBURSEMENT_PAGE_RECORD).split(Constants.DELIMITER_TILD));

		try {

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Validate the heading
			foundation.waitforElement(FinanceList.VALIDATE_DISBURSEMENT_HEADING, Constants.SHORT_TIME);
			assertTrue(foundation.isDisplayed(FinanceList.VALIDATE_DISBURSEMENT_HEADING));

			// Enter the data in fields
			foundation.click(FinanceList.CHOOSE_ORG);
			textBox.enterText(FinanceList.SELECT_ORG, disbursementPage.get(0));
			foundation.click(FinanceList.CLICK_ORG);
			foundation.click(FinanceList.SELECT_LOC);
			textBox.enterText(FinanceList.SELECT_LOC, disbursementPage.get(1));
			foundation.click(FinanceList.ENTER_LOC);
			textBox.enterText(FinanceList.SELECT_NOTES, disbursementPage.get(2));
			textBox.enterText(FinanceList.SELECT_AMOUNT, amount);

			// Click on Cancel button
			foundation.click(FinanceList.CANCEL_BTN);
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Validate the heading
			foundation.waitforElement(FinanceList.VALIDATE_DISBURSEMENT_HEADING, Constants.SHORT_TIME);
			assertTrue(foundation.isDisplayed(FinanceList.VALIDATE_DISBURSEMENT_HEADING));

			// Enter the data in fields
			foundation.click(FinanceList.CHOOSE_ORG);
			textBox.enterText(FinanceList.SELECT_ORG, disbursementPage.get(0));
			foundation.click(FinanceList.CLICK_ORG);
			foundation.click(FinanceList.SELECT_LOC);
			textBox.enterText(FinanceList.SELECT_LOC, disbursementPage.get(1));
			foundation.click(FinanceList.ENTER_LOC);
			textBox.enterText(FinanceList.SELECT_NOTES, disbursementPage.get(2));
			textBox.enterText(FinanceList.SELECT_AMOUNT, amount);

			// Click on Save button
			foundation.click(FinanceList.SAVE_BTN);
			foundation.click(FinanceList.CONFIRM_NO);
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

}
