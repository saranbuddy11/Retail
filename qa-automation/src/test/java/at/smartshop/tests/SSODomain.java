package at.smartshop.tests;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import at.framework.database.mssql.Queries;
import at.framework.database.mssql.ResultSets;
import at.framework.generic.CustomisedAssert;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.database.columns.CNNavigationMenu;
import at.smartshop.database.columns.CNUserRoles;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.pages.NavigationBar;
import at.smartshop.pages.SSODomainList;

@Listeners(at.framework.reportsetup.Listeners.class)
public class SSODomain extends TestInfra {

	private Foundation foundation = new Foundation();
	private NavigationBar navigationBar = new NavigationBar();
	private TextBox textBox = new TextBox();
	private ResultSets dataBase = new ResultSets();
	private SSODomainList ssoDomainList = new SSODomainList();

	private Map<String, String> rstNavigationMenuData;

	@Test(description = "198529-Verify display of SSO Domains Option in Super Dropdown"
			+ "198530-Verify SSO Domains List Landingpage"
			+ "198521-To Verify the functionality of 'Cancel' button, In Create SSO Domain Page")
	public void verifySSODomainCreationAndDeletion() {
		final String CASE_NUM = "198529";

		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);

		

		try {

			// Launch ADM as super
			navigationBar.launchBrowserAsSuperAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Navigate to Super tab and verify SSO Domain Sub Tab is present or not
			List<String> tabNames = navigationBar.getSubTabs(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			CustomisedAssert.assertTrue(tabNames.contains(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION)));

			// Navigate to SSO Domain page and verify all fields
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			ssoDomainList.verifyAllFieldsInSSODomainListpage();

			// Navigate to create SSO Domain page and click on cancel button
			ssoDomainList.navigateoCreateSSOAndClickOnCancel();

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}

	}
}
