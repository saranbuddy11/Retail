package at.smartshop.tests;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import at.framework.database.mssql.Queries;
import at.framework.database.mssql.ResultSets;
import at.framework.generic.Strings;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.Table;
import at.framework.ui.TextBox;
import at.smartshop.database.columns.CNLocation;
import at.smartshop.database.columns.CNNavigationMenu;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.pages.LocationList;
import at.smartshop.pages.LocationSummary;
import at.smartshop.pages.NavigationBar;
import at.smartshop.pages.PrintGroupLists;
import at.smartshop.pages.SelfService;

@Listeners(at.framework.reportsetup.Listeners.class)
public class Menu extends TestInfra {
	private Foundation foundation = new Foundation();
	private ResultSets dataBase = new ResultSets();
	private TextBox textBox = new TextBox();
	private LocationList locationList = new LocationList();
	private NavigationBar navigationBar = new NavigationBar();
	private Dropdown dropDown = new Dropdown();
	private Strings strings = new Strings();
	private Table table = new Table();
	private LocationSummary locationSummary = new LocationSummary();
	
	private Map<String, String> rstNavigationMenuData;
	private Map<String, String> rstLocationData;

	@Test(description = "143551- QAA-35- Verify Has Print Group Label in Menu(Self Service) for added products")
	public void verifyHasPrintGroup() {
		try {
			final String CASE_NUM = "143551";
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);

			List<String> menuItem = Arrays
					.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
			List<String> requiredData = Arrays.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.navigateToMenuItem(menuItem.get(0));
			
			dropDown.selectItem(PrintGroupLists.DPD_LOCATION, rstLocationData.get(CNLocation.LOCATION_NAME), Constants.TEXT);
			foundation.click(PrintGroupLists.BTN_CREATENEW);
			final String printGroupName=requiredData.get(0)+strings.getRandomCharacter();
			textBox.enterText(PrintGroupLists.TXT_NAME,printGroupName);
			foundation.click(PrintGroupLists.BTN_SAVE);

			foundation.waitforElement(PrintGroupLists.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			navigationBar.navigateToMenuItem(menuItem.get(1));
			locationList.selectLocationName(rstLocationData.get(CNLocation.LOCATION_NAME));
			foundation.click(LocationSummary.BUTTON_LOCATION_INFO);
			dropDown.selectItem(LocationSummary.DPD_PRINTGROUP, printGroupName, Constants.TEXT);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationSummary.LBL_SPINNER_MSG, Constants.SHORT_TIME);
			navigationBar.navigateToMenuItem(menuItem.get(2));
			dropDown.selectItem(SelfService.DPD_LOCATION, rstLocationData.get(CNLocation.LOCATION_NAME), Constants.TEXT);
			foundation.click(SelfService.BTN_CREATE_NEW);
			textBox.enterText(SelfService.TXT_MENU_NAME, printGroupName);
			foundation.click(SelfService.BTN_ADD_ITEM);
			textBox.enterText(SelfService.TXT_SEARCH_PRODUCT, rstLocationData.get(CNLocation.PRODUCT_NAME));
			foundation.click(SelfService.LBL_PRODUCT_NAME);
			foundation.click(SelfService.LBL_BTN_ADD);
			foundation.click(SelfService.BTN_SUBMENU_ADD);
			foundation.threadWait(Constants.THREE_SECOND);
			String actualData=foundation.getText(SelfService.LBL_NO_PRINT);
			Assert.assertEquals(actualData, requiredData.get(1));
			foundation.click(SelfService.LBL_FORWARD_ARROW);
			foundation.click(SelfService.LBL_SELECT_ALL);
			foundation.click(SelfService.BTN_SAVE);
			foundation.threadWait(Constants.ONE_SECOND);
			foundation.waitforElement(SelfService.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			textBox.enterText(SelfService.FILTER_MENU, printGroupName);
			table.selectRow(printGroupName);
			foundation.waitforElement(SelfService.LBL_HAS_PRINT, Constants.SHORT_TIME);
			actualData=foundation.getText(SelfService.LBL_HAS_PRINT);
			Assert.assertEquals(actualData, requiredData.get(2));
			foundation.click(SelfService.BTN_DELETE);
			foundation.alertAccept();
			foundation.waitforElement(SelfService.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			
						

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
	
	@Test(description = "143552- QAA-35- Verify Inheriting Print Group Label in Menu(Self Service) for added products")
	public void verifyInheritingPrintGroup() {
		try {
			final String CASE_NUM = "143552";
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);

			List<String> menuItem = Arrays
					.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
			List<String> requiredData = Arrays.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

//			navigationBar.navigateToMenuItem(menuItem.get(0));
//			
//			dropDown.selectItem(PrintGroupLists.DPD_LOCATION, rstLocationData.get(CNLocation.LOCATION_NAME), Constants.TEXT);
//			foundation.click(PrintGroupLists.BTN_CREATENEW);
//			final String printGroupName=requiredData.get(0)+strings.getRandomCharacter();
//			textBox.enterText(PrintGroupLists.TXT_NAME,printGroupName);
//			foundation.click(PrintGroupLists.BTN_SAVE);
//
//			foundation.waitforElement(PrintGroupLists.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			navigationBar.navigateToMenuItem(menuItem.get(1));
			locationList.selectLocationName(rstLocationData.get(CNLocation.LOCATION_NAME));
//			foundation.click(LocationSummary.BUTTON_LOCATION_INFO);
//			dropDown.selectItem(LocationSummary.DPD_PRINTGROUP, "AutomationPrinter", Constants.TEXT);
			locationSummary.selectTab(rstLocationData.get(CNLocation.TAB_NAME));
			foundation.threadWait(Constants.ONE_SECOND);
			textBox.enterText(LocationSummary.TXT_PRODUCT_FILTER, rstLocationData.get(CNLocation.PRODUCT_NAME));
			
			foundation.click(LocationSummary.LBL_PRINT_COLUMN);
			foundation.click(LocationSummary.LBL_PRINT_COLUMN);
			foundation.click(locationSummary.objPrintGroup( requiredData.get(0)));
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationSummary.LBL_SPINNER_MSG, Constants.SHORT_TIME);
			navigationBar.navigateToMenuItem(menuItem.get(2));
			dropDown.selectItem(SelfService.DPD_LOCATION, rstLocationData.get(CNLocation.LOCATION_NAME), Constants.TEXT);
			foundation.click(SelfService.BTN_CREATE_NEW);
			textBox.enterText(SelfService.TXT_MENU_NAME, requiredData.get(0));
			foundation.click(SelfService.BTN_ADD_ITEM);
			textBox.enterText(SelfService.TXT_SEARCH_PRODUCT, rstLocationData.get(CNLocation.PRODUCT_NAME));
			foundation.click(SelfService.LBL_PRODUCT_NAME);
			foundation.click(SelfService.LBL_BTN_ADD);
			foundation.click(SelfService.BTN_SUBMENU_ADD);
			foundation.threadWait(Constants.THREE_SECOND);
			String actualData=foundation.getText(SelfService.LBL_INHERIT_PRINT);
			Assert.assertEquals(actualData, requiredData.get(1));
			foundation.click(SelfService.LBL_FORWARD_ARROW);
			foundation.click(SelfService.LBL_SELECT_ALL);
			foundation.click(SelfService.BTN_SAVE);
			foundation.threadWait(Constants.ONE_SECOND);
			foundation.waitforElement(SelfService.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			textBox.enterText(SelfService.FILTER_MENU, requiredData.get(0));
			table.selectRow(requiredData.get(0));
			foundation.waitforElement(SelfService.LBL_HAS_PRINT, Constants.SHORT_TIME);
			actualData=foundation.getText(SelfService.LBL_HAS_PRINT);
			Assert.assertEquals(actualData, requiredData.get(2));
			foundation.click(SelfService.BTN_DELETE);
			foundation.alertAccept();
			foundation.waitforElement(SelfService.FILTER_MENU, Constants.SHORT_TIME);
			//resetting
			navigationBar.navigateToMenuItem(menuItem.get(1));
			locationList.selectLocationName(rstLocationData.get(CNLocation.LOCATION_NAME));
			locationSummary.selectTab(rstLocationData.get(CNLocation.TAB_NAME));
			foundation.threadWait(Constants.ONE_SECOND);
			textBox.enterText(LocationSummary.TXT_PRODUCT_FILTER, rstLocationData.get(CNLocation.PRODUCT_NAME));
			
			foundation.click(LocationSummary.LBL_PRINT_COLUMN);
			foundation.click(LocationSummary.LBL_PRINT_COLUMN);
			foundation.click(locationSummary.objPrintGroup( requiredData.get(3)));
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationSummary.LBL_SPINNER_MSG, Constants.SHORT_TIME);
			
						

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
}
