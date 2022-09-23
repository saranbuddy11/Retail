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
import at.smartshop.database.columns.CNSSODomain;
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
	private Map<String, String> rstSSODomainData;

	/**
	 * @author afrosean Date:20-09-2022
	 */
	@Test(description = "198529-Verify display of SSO Domains Option in Super Dropdown"
			+ "198530-Verify SSO Domains List Landingpage"
			+ "198521-To Verify the functionality of 'Cancel' button, In Create SSO Domain Page"
			+ "198523-To Verify the functionality of  'Save' button by entering Valid Domain Name and Address, In Create SSO Domain Page"
			+ "198527-To Verify the functionality of manage SSO Domain where user deletes the existing SSO Domain record")
	public void verifySSODomainCreationAndDeletion() {
		final String CASE_NUM = "198529";

		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstSSODomainData = dataBase.getSSODomainData(Queries.SSO_DOMAIN, CASE_NUM);

		List<String> datas = Arrays
				.asList(rstSSODomainData.get(CNSSODomain.REQUIRED_DATA).split(Constants.DELIMITER_TILD));

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

			// create SSO Domain
			ssoDomainList.createSSODomain(datas.get(0), datas.get(1));

			// Delete created SSO Domain in manage SSO domain
			ssoDomainList.deleteCreatedSSODomain(datas.get(0));

			// Navigate to create SSO Domain page and click on cancel button
			ssoDomainList.navigateoCreateSSOAndClickOnCancel();

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}

	}

	/**
	 * @author afrosean Date:23-09-2022
	 */
	@Test(description = "198522-To Verify the functionality of 'Save' button by entering Duplicate Domain Name and Address, In Create SSO Domain Pag"
			+ "198526-To Verify the functionality of manage SSO Domain where user can’t make the changes because of duplicate error"
			+ "198525-To Verify the functionality of manage SSO Domain where user can’t make the changes because of duplicate error")
	public void verifyFunctionaliyOfManageSSOPage() {
		final String CASE_NUM = "198522";

		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstSSODomainData = dataBase.getSSODomainData(Queries.SSO_DOMAIN, CASE_NUM);

		List<String> datas = Arrays
				.asList(rstSSODomainData.get(CNSSODomain.REQUIRED_DATA).split(Constants.DELIMITER_TILD));

		try {

			// Launch ADM as super
			navigationBar.launchBrowserAsSuperAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Navigate to SSO Domain page and verify all fields
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			ssoDomainList.createSSODomain(datas.get(0), datas.get(1));
			ssoDomainList.createSSODomain(datas.get(2), datas.get(3));

			// verify error while creating SSO Domain with duplicate name and address
			ssoDomainList.createSSODomainWithDuplicaeNameAndAddress(datas.get(0), datas.get(1), datas.get(6));

			// verify created SSO domain edit domain name and address
			ssoDomainList.searchCreatedSSODomainEditDomainNameAndDominAddress(datas.get(0), datas.get(4), datas.get(5));

			// verify with created sso domain and enter duplicate name address
			ssoDomainList.searchWithCreatedSSODomainAndEnterDupicateNameAddress(datas.get(4), datas.get(2),
					datas.get(3), datas.get(6));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			ssoDomainList.deleteCreatedSSODomain(datas.get(2));
		}
	}
}
