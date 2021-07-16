package at.smartshop.tests;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import at.framework.browser.Factory;
import at.framework.database.mssql.Queries;
import at.framework.database.mssql.ResultSets;
import at.framework.ui.Foundation;
import at.framework.ui.Table;
import at.framework.ui.TextBox;
import at.smartshop.database.columns.CNNavigationMenu;
import at.smartshop.database.columns.CNPickList;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.pages.NavigationBar;
import at.smartshop.pages.PickList;

@Listeners(at.framework.reportsetup.Listeners.class)
public class PickLists extends TestInfra {
	private NavigationBar navigationBar = new NavigationBar();
	private TextBox textBox = new TextBox();
	private Foundation foundation = new Foundation();
	private ResultSets dataBase = new ResultSets();
	private Table table = new Table();
	private PickList pickList = new PickList();

	private Factory factory = new Factory();
	private Map<String, String> rstNavigationMenuData;
	private Map<String, String> rstPickListData;

	@Test(description = "143192 QAA-67-verify picklist screen column headers -Super")
	public void verifyPickListColumnHeaders() {
		try {
			final String CASE_NUM = "143192";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstPickListData = dataBase.getPickListData(Queries.PICKLIST, CASE_NUM);
			List<String> dbRecords = Arrays
					.asList(rstPickListData.get(CNPickList.RECORDS).split(Constants.DELIMITER_TILD));
			
			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			String menuItem = rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM);
			navigationBar.navigateToMenuItem(menuItem);
			textBox.enterText(PickList.SEARCH_FILTER, rstPickListData.get(CNPickList.LOCATIONS));
			foundation.click(PickList.LBL_SELECT_ALL);
			foundation.click(PickList.BTN_APPLY);
			foundation.waitforElement(pickList.objPickList(rstPickListData.get(CNPickList.LOCATIONS)),
					Constants.SHORT_TIME);
			foundation.click(pickList.objPickList(rstPickListData.get(CNPickList.LOCATIONS)));
			foundation.click(PickList.BTN_PICKLIST_PLAN);
			List<String> uiData = pickList.getProductsHeaders().subList(1, 13);
			System.out.println(uiData);
			System.out.println(dbRecords);
			Assert.assertTrue(dbRecords.equals(uiData));
			
//			foundation.click(PickList.LBL_ADD_PRODUCT);
//			foundation.waitforElement(PickList.LBL_ADD_PRODUCT_PICKLIST, Constants.SHORT_TIME);
//			textBox.enterText(PickList.LBL_FILTER_TYPE, rstPickListData.get(CNPickList.PRODUCT_NAME));
//			foundation.threadWait(Constants.SHORT_TIME);
//			foundation.waitforElement(pickList.objPickList(rstPickListData.get(CNPickList.PRODUCT_NAME)),
//					Constants.SHORT_TIME);
//			foundation.click(PickList.TBL_NEED);
//           foundation.waitforElement(PickList.TXT_NEED, Constants.LONG_TIME);
//			textBox.enterText(PickList.TXT_NEED, rstPickListData.get(CNPickList.NEED));
//			foundation.click(pickList.objPickList(rstPickListData.get(CNPickList.PRODUCT_NAME)));
//			foundation.click(PickList.LBL_PREVIEW);
//			foundation.click(PickList.LBL_Add);

			// table.getTblHeadersUI(null)
//			foundation.waitforElement(PickList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
//			foundation.click(pickList.objPickList(rstPickListData.get(CNPickList.LOCATIONS)));

		} catch (Exception exc) {

			Assert.fail(exc.toString());
		}
	}

}
