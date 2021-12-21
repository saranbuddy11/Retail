package at.smartshop.tests;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import at.framework.database.mssql.Queries;
import at.framework.database.mssql.ResultSets;
import at.framework.files.Excel;
import at.framework.files.PropertyFile;
import at.framework.generic.DateAndTime;
import at.framework.generic.Strings;
import at.framework.ui.CheckBox;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.Table;
import at.framework.ui.TextBox;
import at.smartshop.database.columns.CNDeviceList;
import at.smartshop.database.columns.CNNavigationMenu;
import at.smartshop.database.columns.CNOrgSummary;
import at.smartshop.database.columns.CNSuperList;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.pages.ContactList;
import at.smartshop.pages.CorporateAccountList;
import at.smartshop.pages.CreatePromotions;
import at.smartshop.pages.DataSourceManager;
import at.smartshop.pages.EditPromotion;
import at.smartshop.pages.GlobalProduct;
import at.smartshop.pages.LocationList;
import at.smartshop.pages.NavigationBar;
import at.smartshop.pages.OrgList;
import at.smartshop.pages.OrgSummary;
import at.smartshop.pages.PromotionList;
import at.smartshop.pages.UserList;
import at.smartshop.pages.UserRoles;

public class SuperOthers extends TestInfra {

	private PropertyFile propertyFile = new PropertyFile();
	private ResultSets dataBase = new ResultSets();
	private Foundation foundation = new Foundation();
	private NavigationBar navigationBar = new NavigationBar();
	private CreatePromotions createPromotions = new CreatePromotions();
	private LocationList locationList = new LocationList();
	private Dropdown dropdown = new Dropdown();
	private TextBox textBox = new TextBox();
	private Strings string = new Strings();
	private DateAndTime dateAndTime = new DateAndTime();
	private PromotionList promotionList = new PromotionList();
	private EditPromotion editPromotion = new EditPromotion();
	private UserRoles userRoles = new UserRoles();
	private UserList userList = new UserList();
	private CheckBox checkBox = new CheckBox();
	private Table table = new Table();
	private Excel excel = new Excel();

	private Map<String, String> rstNavigationMenuData;
	private Map<String, String> rstLocationData;
	private Map<String, String> rstLocationListData;
	private Map<String, String> rstLocationSummaryData;
	private Map<String, String> rstUserRolesData;
	private Map<String, String> rstSuperListData;

	@Test(description = "164514-ADM>Super>Contact>Contact List")
	public void superContactList() {
		final String CASE_NUM = "164514";
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);

		try {

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			boolean fileExists = foundation.isFileExists(FilePath.EXCEL_CONTACT_SRC);
			if (fileExists == false) {
				foundation.deleteFile(FilePath.EXCEL_CONTACT_SRC);
			}
			foundation.click(ContactList.BTN_EXPORT);
			foundation.threadWait(Constants.TWO_SECOND);

			// download assertion
			Assert.assertTrue(excel.isFileDownloaded(FilePath.EXCEL_CONTACT_SRC));
			foundation.deleteFile(FilePath.EXCEL_CONTACT_SRC);
			textBox.enterText(ContactList.TXT_SEARCH_CONTACTS, Constants.TESTING);
			foundation.click(ContactList.BTN_EXPORT);
			foundation.threadWait(Constants.TWO_SECOND);
			Assert.assertTrue(excel.isFileDownloaded(FilePath.EXCEL_CONTACT_SRC));

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// delete files
			foundation.deleteFile(FilePath.EXCEL_CONTACT_SRC);
		}
	}
	
	@Test(description = "166895-Validate the check and uncheck checkbox of ' Is Snowflake' column")
	public void DSMCheckUncheckCheckbox() {
		final String CASE_NUM = "166895";
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstSuperListData = dataBase.getSuperListData(Queries.SUPER, CASE_NUM);
		
		final String super_Name = rstSuperListData.get(CNSuperList.SUPER_NAME);
		
		List<String> rowdata = Arrays
				.asList(rstSuperListData.get(CNSuperList.DISBURSEMENT_PAGE_RECORD).split(Constants.DELIMITER_TILD));
		String search_data = rowdata.get(0);
	
		try {

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			
			foundation.waitforElement(DataSourceManager.VALIDATE_DSM_HEADING, Constants.SHORT_TIME);
			assertTrue(foundation.isDisplayed(DataSourceManager.VALIDATE_DSM_HEADING),super_Name);
			
			//search for Report 
			textBox.enterText(DataSourceManager.DSM_SEARCH_BOX, search_data);
			assertTrue(checkBox.isChkEnabled(DataSourceManager.DSM_CHECKBOX));
			foundation.click(DataSourceManager.DSM_CHECKBOX);
			foundation.waitforElement(DataSourceManager.DSM_SUCCESS_POPUP, Constants.SHORT_TIME);
		
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			assertTrue(checkBox.isChecked(DataSourceManager.DSM_CHECKBOX));
			foundation.click(DataSourceManager.DSM_CHECKBOX);
			foundation.waitforElement(DataSourceManager.DSM_SUCCESS_POPUP, Constants.SHORT_TIME);

		}
	}
}
