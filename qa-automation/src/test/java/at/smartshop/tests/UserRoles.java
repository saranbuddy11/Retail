package at.smartshop.tests;

import static org.testng.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import at.framework.database.mssql.Queries;
import at.framework.database.mssql.ResultSets;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.Table;
import at.framework.ui.TextBox;
import at.smartshop.database.columns.CNNavigationMenu;
import at.smartshop.database.columns.CNUserRoles;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.keys.Configuration;
import at.smartshop.pages.NavigationBar;
import at.smartshop.pages.UserList;
import at.smartshop.pages.UserSummary;

@Listeners(at.framework.reportsetup.Listeners.class)
public class UserRoles extends TestInfra {

	private NavigationBar navigationBar = new NavigationBar();
	private TextBox textBox = new TextBox();
	private Dropdown dropDown = new Dropdown();
	private Table table = new Table();
	private Foundation foundation = new Foundation();
	private ResultSets dataBase = new ResultSets();

	private Map<String, String> rstNavigationMenuData;
	private Map<String, String> rstUserRolesData;

	@Test(description = "118208-This test is to validate 'Client' drop down field for Super and Master National Account user")
	public void verifClientDropdownField() {
		try {

			final String CASE_NUM = "118208";
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstUserRolesData = dataBase.getUserRolesData(Queries.USER_ROLES, CASE_NUM);

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			List<String> menuItem = Arrays
					.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
			navigationBar.navigateToMenuItem(menuItem.get(0));

			assertTrue(foundation.isDisplayed(UserList.BTN_MANAGE_ROLES));
			List<String> lblRowRecord = Arrays
					.asList(rstUserRolesData.get(CNUserRoles.ROW_RECORD).split(Constants.DELIMITER_TILD));
			textBox.enterText(UserList.TXT_FILTER, lblRowRecord.get(0));
			table.selectRow(lblRowRecord.get(0));
			assertTrue(foundation.isDisplayed(UserSummary.DPD_CLIENT));

			List<String> clientdropDownList = Arrays
					.asList(rstUserRolesData.get(CNUserRoles.CLIENT_DROPDOWN).split(Constants.DELIMITER_TILD));
			Assert.assertTrue(dropDown.verifyItemPresent(UserSummary.DPD_CLIENT, clientdropDownList.get(0)));
			Assert.assertTrue(dropDown.verifyItemPresent(UserSummary.DPD_CLIENT, clientdropDownList.get(1)));
			Assert.assertTrue(dropDown.verifyItemPresent(UserSummary.DPD_CLIENT, clientdropDownList.get(2)));
			Assert.assertTrue(dropDown.verifyItemPresent(UserSummary.DPD_CLIENT, clientdropDownList.get(3)));

			login.logout();
			login.login(
					propertyFile.readPropertyFile(Configuration.MASTER_NATIONAL_ACCOUNT_USER,
							FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItem.get(1));

			assertTrue(foundation.isDisplayed(UserList.BTN_MANAGE_ROLES));
			textBox.enterText(UserList.TXT_FILTER, lblRowRecord.get(1));
			table.selectRow(lblRowRecord.get(1));
			assertTrue(foundation.isDisplayed(UserSummary.DPD_CLIENT));
			Assert.assertTrue(dropDown.verifyItemPresent(UserSummary.DPD_CLIENT, clientdropDownList.get(0)));
			Assert.assertTrue(dropDown.verifyItemPresent(UserSummary.DPD_CLIENT, clientdropDownList.get(1)));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
}
