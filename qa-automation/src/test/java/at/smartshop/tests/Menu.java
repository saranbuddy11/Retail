package at.smartshop.tests;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import at.framework.database.mssql.Queries;
import at.framework.database.mssql.ResultSets;
import at.framework.generic.CustomisedAssert;
import at.framework.generic.Strings;
import at.framework.ui.CheckBox;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.Radio;
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
	private SelfService selfService = new SelfService();
	private CheckBox checkBox = new CheckBox();
	private Radio radio = new Radio();

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
			List<String> requiredData = Arrays
					.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.navigateToMenuItem(menuItem.get(0));

			dropDown.selectItem(PrintGroupLists.DPD_LOCATION, rstLocationData.get(CNLocation.LOCATION_NAME),
					Constants.TEXT);
			foundation.click(PrintGroupLists.BTN_CREATENEW);
			final String printGroupName = requiredData.get(0) + strings.getRandomCharacter();
			textBox.enterText(PrintGroupLists.TXT_NAME, printGroupName);
			foundation.click(PrintGroupLists.BTN_SAVE);

			foundation.waitforElement(PrintGroupLists.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			navigationBar.navigateToMenuItem(menuItem.get(1));
			locationList.selectLocationName(rstLocationData.get(CNLocation.LOCATION_NAME));
			foundation.click(LocationSummary.BUTTON_LOCATION_INFO);
			dropDown.selectItem(LocationSummary.DPD_PRINTGROUP, printGroupName, Constants.TEXT);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationSummary.LBL_SPINNER_MSG, Constants.SHORT_TIME);
			navigationBar.navigateToMenuItem(menuItem.get(2));
			dropDown.selectItem(SelfService.DPD_LOCATION, rstLocationData.get(CNLocation.LOCATION_NAME),
					Constants.TEXT);
			foundation.click(SelfService.BTN_CREATE_NEW);
			textBox.enterText(SelfService.TXT_MENU_NAME, printGroupName);
			foundation.click(SelfService.BTN_ADD_ITEM);
			textBox.enterText(SelfService.TXT_SEARCH_PRODUCT, rstLocationData.get(CNLocation.PRODUCT_NAME));
			foundation.click(SelfService.LBL_PRODUCT_NAME);
			foundation.click(SelfService.LBL_BTN_ADD);
			foundation.click(SelfService.BTN_SUBMENU_ADD);
			foundation.threadWait(Constants.TWO_SECOND);
			String actualData = foundation.getText(SelfService.LBL_NO_PRINT);
			CustomisedAssert.assertEquals(actualData, requiredData.get(1));
			
			//Verify has print group
//			foundation.click(SelfService.LBL_FORWARD_ARROW);
//			foundation.threadWait(Constants.ONE_SECOND);
//			foundation.waitforElement(selfService.objPrintCheckbox(printGroupName), Constants.SHORT_TIME);
//			checkBox.check(selfService.objPrintCheckbox(printGroupName));
//
//			foundation.click(SelfService.BTN_SAVE);
//			foundation.threadWait(Constants.ONE_SECOND);
//			foundation.waitforElement(SelfService.TXT_SPINNER_MSG, Constants.SHORT_TIME);
//			textBox.enterText(SelfService.FILTER_MENU, printGroupName);
//			table.selectRow(printGroupName);
//			foundation.waitforElement(SelfService.BTN_ADD_ITEM, Constants.SHORT_TIME);
//			foundation.waitforElement(SelfService.LBL_HAS_PRINT, Constants.SHORT_TIME);
//			actualData = foundation.getText(SelfService.LBL_HAS_PRINT);
//			CustomisedAssert.assertEquals(actualData, requiredData.get(2));

			// deselect printgroup checkbox
			
			foundation.click(SelfService.LBL_FORWARD_ARROW);
			checkBox.unCheck(selfService.objPrintCheckbox(printGroupName));

			foundation.click(SelfService.BTN_SAVE);
			foundation.threadWait(Constants.ONE_SECOND);
			foundation.waitforElement(SelfService.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			foundation.waitforElement(SelfService.FILTER_MENU, Constants.SHORT_TIME);
			textBox.enterText(SelfService.FILTER_MENU, printGroupName);
			table.selectRow(printGroupName);
			foundation.waitforElement(SelfService.LBL_NO_PRINT, Constants.SHORT_TIME);
			actualData = foundation.getText(SelfService.LBL_NO_PRINT);
			CustomisedAssert.assertEquals(actualData, requiredData.get(1));
			foundation.click(SelfService.BTN_DELETE);
			foundation.alertAccept();

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "143552- QAA-35- Verify Inheriting Print Group Label in Menu(Self Service) for added products")
	public void verifyInheritingPrintGroup() {
		final String CASE_NUM = "143552";
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);

		List<String> menuItem = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> requiredData = Arrays
				.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		try {

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.navigateToMenuItem(menuItem.get(1));
			locationList.selectLocationName(rstLocationData.get(CNLocation.LOCATION_NAME));

			locationSummary.selectTab(rstLocationData.get(CNLocation.TAB_NAME));
			foundation.threadWait(Constants.THREE_SECOND);
			foundation.click(LocationSummary.TXT_PRODUCT_FILTER);
			textBox.enterText(LocationSummary.TXT_PRODUCT_FILTER, rstLocationData.get(CNLocation.PRODUCT_NAME));
            foundation.threadWait(Constants.SHORT_TIME);
            foundation.click(LocationSummary.BTN_MANAGE_COLUMNS);
            foundation.scrollIntoViewElement(LocationSummary.BTN_PRINT_GROUP);
    		foundation.waitforElementToBeVisible(LocationSummary.BTN_PRINT_GROUP, Constants.MEDIUM_TIME);
    		foundation.click(LocationSummary.BTN_PRINT_GROUP);
    		foundation.click(LocationSummary.BTN_PRINT_GROUP);
    		foundation.waitforElementToBeVisible(LocationSummary.BTN_APPLY, Constants.SHORT_TIME);
    		foundation.click(LocationSummary.BTN_APPLY);
    		foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(LocationSummary.LBL_PRINT_COLUMN);
			//foundation.click(LocationSummary.LBL_PRINT_COLUMN);
			foundation.objectClick(locationSummary.objPrintGroup(requiredData.get(0)));
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationSummary.LBL_SPINNER_MSG, Constants.SHORT_TIME);
			navigationBar.navigateToMenuItem(menuItem.get(2));
			dropDown.selectItem(SelfService.DPD_LOCATION, rstLocationData.get(CNLocation.LOCATION_NAME),
					Constants.TEXT);
			foundation.click(SelfService.BTN_CREATE_NEW);
			textBox.enterText(SelfService.TXT_MENU_NAME, requiredData.get(0));
			foundation.click(SelfService.BTN_ADD_ITEM);
			textBox.enterText(SelfService.TXT_SEARCH_PRODUCT, rstLocationData.get(CNLocation.PRODUCT_NAME));
			foundation.click(SelfService.LBL_PRODUCT_NAME);
			foundation.click(SelfService.LBL_BTN_ADD);
			foundation.click(SelfService.BTN_SUBMENU_ADD);
			foundation.threadWait(Constants.THREE_SECOND);
			foundation.click(SelfService.LBL_FORWARD_ARROW);
			checkBox.check(selfService.objPrintCheckbox(requiredData.get(0)));

			foundation.click(SelfService.BTN_SAVE);
			foundation.threadWait(Constants.ONE_SECOND);
			foundation.waitforElement(SelfService.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			textBox.enterText(SelfService.FILTER_MENU, requiredData.get(0));
			table.selectRow(requiredData.get(0));
			foundation.waitforElement(SelfService.LBL_HAS_PRINT, Constants.SHORT_TIME);
			

			//Deselect printgroup checkbox
			foundation.click(SelfService.LBL_FORWARD_ARROW);
			checkBox.unCheck(selfService.objPrintCheckbox(requiredData.get(0)));

			foundation.click(SelfService.BTN_SAVE);
			foundation.threadWait(Constants.ONE_SECOND);
			foundation.waitforElement(SelfService.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			textBox.enterText(SelfService.FILTER_MENU, requiredData.get(0));
			table.selectRow(requiredData.get(0));
			foundation.waitforElement(SelfService.LBL_INHERIT_PRINT, Constants.SHORT_TIME);
			

			foundation.click(SelfService.BTN_DELETE);
			foundation.alertAccept();
			foundation.waitforElement(SelfService.FILTER_MENU, Constants.SHORT_TIME);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} 
		finally {
			// resetting
			navigationBar.navigateToMenuItem(menuItem.get(1));
			locationList.selectLocationName(rstLocationData.get(CNLocation.LOCATION_NAME));
			locationSummary.selectTab(rstLocationData.get(CNLocation.TAB_NAME));
			foundation.threadWait(Constants.ONE_SECOND);
			textBox.enterText(LocationSummary.TXT_PRODUCT_FILTER, rstLocationData.get(CNLocation.PRODUCT_NAME));
            foundation.threadWait(Constants.SHORT_TIME);
            foundation.click(LocationSummary.BTN_MANAGE_COLUMNS);
            foundation.scrollIntoViewElement(LocationSummary.BTN_PRINT_GROUP);
    		foundation.waitforElementToBeVisible(LocationSummary.BTN_PRINT_GROUP, Constants.MEDIUM_TIME);
    		foundation.click(LocationSummary.BTN_PRINT_GROUP);
    		foundation.waitforElementToBeVisible(LocationSummary.BTN_APPLY, Constants.SHORT_TIME);
    		foundation.click(LocationSummary.BTN_APPLY);
    		foundation.threadWait(Constants.SHORT_TIME);
			//foundation.click(LocationSummary.LBL_PRINT_COLUMN);
			//foundation.click(LocationSummary.LBL_PRINT_COLUMN);
			//foundation.click(locationSummary.objPrintGroup(requiredData.get(3)));
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationSummary.LBL_SPINNER_MSG, Constants.SHORT_TIME);
		}
	}

	@Test(description = "145288-QAA-218-Verify Hide feature for All button is working")
	public void verifyHideButton() {
		try {
			final String CASE_NUM = "145288";
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);

			List<String> requiredData = Arrays
					.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			String menuItem = rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM);

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItem);
			textBox.enterText(SelfService.FILTER_MENU, requiredData.get(0));
			table.selectRow(requiredData.get(0));
			foundation.waitforElement(SelfService.RDO_BTN_SHOW, Constants.SHORT_TIME);
			radio.set(SelfService.RDO_BTN_Hide);
			foundation.click(SelfService.BTN_SAVE);

			textBox.enterText(SelfService.FILTER_MENU, requiredData.get(0));
			table.selectRow(requiredData.get(0));
			foundation.waitforElement(SelfService.RDO_BTN_SHOW, Constants.SHORT_TIME);
			boolean isenabled = radio.isSelected(SelfService.RDO_BTN_Hide);
			CustomisedAssert.assertTrue(isenabled);
			CustomisedAssert.assertFalse(foundation.isDisplayed(SelfService.LBL_ALL));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "145289-QAA-218- Verify when show radio button is enabled in self service menu, All option is displayed.")
	public void verifyShowButton() {
		try {
			final String CASE_NUM = "145289";
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);

			List<String> requiredData = Arrays
					.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			String menuItem = rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM);

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItem);
			textBox.enterText(SelfService.FILTER_MENU, requiredData.get(0));
			table.selectRow(requiredData.get(0));
			foundation.waitforElement(SelfService.RDO_BTN_SHOW, Constants.SHORT_TIME);
			radio.set(SelfService.RDO_BTN_SHOW);
			foundation.click(SelfService.BTN_SAVE);
            foundation.threadWait(Constants.SHORT_TIME);
			textBox.enterText(SelfService.FILTER_MENU, requiredData.get(0));
			table.selectRow(requiredData.get(0));
			foundation.waitforElement(SelfService.RDO_BTN_SHOW, Constants.SHORT_TIME);
			boolean isenabled = radio.isSelected(SelfService.RDO_BTN_SHOW);
			CustomisedAssert.assertTrue(isenabled);
			CustomisedAssert.assertTrue(foundation.isDisplayed(SelfService.LBL_ALL));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "145290-QAA-218-verify when new self service menu is created, show radio button is enabled by default")
	public void verifyDefualtSettings() {
		try {
			final String CASE_NUM = "145290";
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);

			String menuItem = rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM);
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItem);
			foundation.click(SelfService.BTN_CREATE_NEW);
			foundation.waitforElement(SelfService.RDO_BTN_SHOW, Constants.SHORT_TIME);
			boolean isenabled = radio.isSelected(SelfService.RDO_BTN_SHOW);
			CustomisedAssert.assertTrue(isenabled);
			CustomisedAssert.assertTrue(foundation.isDisplayed(SelfService.LBL_ALL));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "145292-QAA-218-Verify cancel button in self service menu for HIDE Radio Button")
	public void verifyCancelButtonHide() {
		try {
			final String CASE_NUM = "145292";
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);

			List<String> requiredData = Arrays
					.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			String menuItem = rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM);

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItem);
			textBox.enterText(SelfService.FILTER_MENU, requiredData.get(0));
			table.selectRow(requiredData.get(0));
			foundation.waitforElement(SelfService.RDO_BTN_SHOW, Constants.SHORT_TIME);
			radio.set(SelfService.RDO_BTN_SHOW);
			foundation.click(SelfService.BTN_SAVE);

			textBox.enterText(SelfService.FILTER_MENU, requiredData.get(0));
			table.selectRow(requiredData.get(0));
			foundation.waitforElement(SelfService.RDO_BTN_SHOW, Constants.SHORT_TIME);
			radio.set(SelfService.RDO_BTN_Hide);
			foundation.click(SelfService.BTN_CANCEL);
			textBox.enterText(SelfService.FILTER_MENU, requiredData.get(0));
			table.selectRow(requiredData.get(0));
			foundation.waitforElement(SelfService.RDO_BTN_SHOW, Constants.SHORT_TIME);
			boolean isenabled = radio.isSelected(SelfService.RDO_BTN_SHOW);
			CustomisedAssert.assertTrue(isenabled);
			CustomisedAssert.assertTrue(foundation.isDisplayed(SelfService.LBL_ALL));
			isenabled = radio.isSelected(SelfService.RDO_BTN_Hide);
			CustomisedAssert.assertFalse(isenabled);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "145291-QAA-218-Verify cancel button in self service menu for SHOW Radio Button")
	public void verifyCancelButtonShow() {
		try {
			final String CASE_NUM = "145291";
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);

			List<String> requiredData = Arrays
					.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			String menuItem = rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM);

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItem);
			textBox.enterText(SelfService.FILTER_MENU, requiredData.get(0));
			table.selectRow(requiredData.get(0));
			foundation.waitforElement(SelfService.RDO_BTN_Hide, Constants.SHORT_TIME);
			radio.set(SelfService.RDO_BTN_Hide);
			foundation.click(SelfService.BTN_SAVE);

			textBox.enterText(SelfService.FILTER_MENU, requiredData.get(0));
			table.selectRow(requiredData.get(0));
			foundation.waitforElement(SelfService.RDO_BTN_SHOW, Constants.SHORT_TIME);
			radio.set(SelfService.RDO_BTN_SHOW);
			foundation.click(SelfService.BTN_CANCEL);
			textBox.enterText(SelfService.FILTER_MENU, requiredData.get(0));
			table.selectRow(requiredData.get(0));
			foundation.waitforElement(SelfService.RDO_BTN_SHOW, Constants.SHORT_TIME);
			boolean isSelected = radio.isSelected(SelfService.RDO_BTN_SHOW);
			CustomisedAssert.assertFalse(isSelected);
			CustomisedAssert.assertFalse(foundation.isDisplayed(SelfService.LBL_ALL));
			isSelected = radio.isSelected(SelfService.RDO_BTN_Hide);
			CustomisedAssert.assertTrue(isSelected);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
	
	/**
	 * @author sakthir Date: 05-09-2022
	 */
	@Test(description = "197566-To Verify time slot and Max order columns are loading as expected in ADM->Menu->Self Service Menu-> Set Limits")
	public void verifyTimeSlotAndMaxOrderColumnInSetLimitsPage() {
		try {
			final String CASE_NUM = "197566";
			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			
			String menu =rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM);
			List<String> data =Arrays
					.asList(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION).split(Constants.DELIMITER_TILD));
			
			// Select Org & Menu
			navigationBar.launchBrowserAsSuperAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));
			
			// Select Menu and Menu Item
			navigationBar.navigateToMenuItem(menu);
			CustomisedAssert.assertTrue(foundation.isDisplayed(SelfService.LBL_SELFSERVICE));
			foundation.click(SelfService.TXT_SEARCH);
			textBox.enterText(SelfService.TXT_SEARCH,data.get(0));
			foundation.waitforElementToBeVisible(SelfService.CLICK_SET_LIMITS, 3);
			foundation.click(SelfService.CLICK_SET_LIMITS);
			foundation.waitforElementToBeVisible(SelfService.COL_TIMESLOT,Constants.MEDIUM_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(SelfService.COL_TIMESLOT));
			CustomisedAssert.assertTrue(foundation.isDisplayed(SelfService.COL_MAX_ORDER));
		    List<String> value=foundation.getTextofListElement(SelfService.ROW_SET_LIMITS);
		    CustomisedAssert.assertTrue(value.get(0).length()>5);
		    CustomisedAssert.assertFalse(value.equals(data.get(1)));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
	
	/**
	 * @author sakthir Date: 03-10-2022
	 */
	@Test(description = "205006-SOS-32134:To verify Featured Product CheckBox is Unchecked by creating new self service menu"
			+"205007-To verify Featured Product CheckBox is Unchecked in Existing self service menu"
			+"205008-To verify Featured Product CheckBox is Unchecked by adding new product to the newly created self service menu"
			+"205009-To verify Featured Product CheckBox is Unchecked in by adding new product to the Existing self service menu")
	public void verifyFeaturedProductCheckBoxStatusUncheckedForExistingAndNewProduct() {
		
			final String CASE_NUM = "205006";
			
			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			String menu =rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM);
			List<String> data =Arrays
					.asList(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION).split(Constants.DELIMITER_TILD));
			try {
			// Select Org & Menu
			navigationBar.launchBrowserAsSuperAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));
			
			// Select Menu and Menu Item
			navigationBar.navigateToMenuItem(menu);
			CustomisedAssert.assertTrue(foundation.isDisplayed(SelfService.LBL_SELFSERVICE));
			
			//select location and create new menu with Add Product
			dropDown.selectItem(SelfService.DPD_LOCATION,data.get(0),Constants.TEXT);
			foundation.click(SelfService.BTN_CREATE_NEW);
			CustomisedAssert.assertTrue(foundation.getText(SelfService.LBL_SELFSERIVICE_MENU).equals(data.get(2)));
			textBox.enterText(SelfService.TXT_MENU_NAME,data.get(7));
			foundation.scrollIntoViewElement(SelfService.BTN_ADD_MENU);
			foundation.click(SelfService.BTN_ADD_MENU);
			textBox.enterText(SelfService.TXT_MENU_BUTTON_NAME, data.get(8));
			foundation.click(SelfService.BTN_ADD_IMG);
			foundation.threadWait(Constants.THREE_SECOND);
			foundation.click(SelfService.BTN_ADD_SUBMENU);
			textBox.enterText(SelfService.TXT_MENU_BUTTON_NAME, data.get(9));
			foundation.click(SelfService.BTN_ADD_IMG);
			foundation.waitforElementToBeVisible(SelfService.BTN_ADD_PRODUCT,3);
			foundation.threadWait(3);
			foundation.click(SelfService.BTN_ADD_PRODUCT);
			textBox.enterText(SelfService.SEARCH_ADD_PRODUCT, data.get(10));
			foundation.click(selfService.selectProductToAdd(data.get(10)));
			foundation.click(SelfService.BTN_ADD_PRODUCT_POPUP);
			foundation.click(SelfService.BTN_SAVE);
			
			//select location and search for new menu
			dropDown.selectItem(SelfService.DPD_LOCATION,data.get(0),Constants.TEXT);
			textBox.enterText(SelfService.TXT_SEARCH,data.get(7));
			foundation.click(selfService.objSelectMenu(data.get(7)));
			CustomisedAssert.assertTrue(foundation.getText(SelfService.LBL_SELFSERIVICE_MENU).equals(data.get(2)));
			
			//verify the Featured Product CheckBox is unchecked
			foundation.scrollIntoViewElement(selfService.objProductExpand(data.get(3)));
			foundation.click(selfService.objProductExpand(data.get(3)));
			CustomisedAssert.assertFalse(checkBox.isChecked( SelfService.CHK_FEATURED_PRODUCT));
			
			//Add new Product for new menu
			foundation.click(SelfService.BTN_ADD_PRODUCT);
			textBox.enterText(SelfService.SEARCH_ADD_PRODUCT, data.get(6));
			foundation.click(selfService.selectProductToAdd(data.get(6)));
			foundation.click(SelfService.BTN_ADD_PRODUCT_POPUP);
			
			//verify newly added product Featured Product CheckBox is unchecked
			foundation.scrollIntoViewElement(selfService.objProductExpand(data.get(4)));
			foundation.click(selfService.objProductExpand(data.get(4)));
			CustomisedAssert.assertFalse(checkBox.isChecked( SelfService.CHK_FEATURED_PRODUCT));
			foundation.click(SelfService.BTN_CANCEL_MENU);
			
			//select location and search for existing menu
			CustomisedAssert.assertTrue(foundation.isDisplayed(SelfService.LBL_SELFSERVICE));
			dropDown.selectItem(SelfService.DPD_LOCATION,data.get(0),Constants.TEXT);
			textBox.enterText(SelfService.TXT_SEARCH,data.get(1));
			foundation.click(selfService.objSelectMenu(data.get(1)));
			CustomisedAssert.assertTrue(foundation.getText(SelfService.LBL_SELFSERIVICE_MENU).equals(data.get(2)));
			
			//verify the Featured Product CheckBox is unchecked
			foundation.scrollIntoViewElement(selfService.objProductExpand(data.get(3)));
			foundation.click(selfService.objProductExpand(data.get(3)));
			CustomisedAssert.assertFalse(checkBox.isChecked( SelfService.CHK_FEATURED_PRODUCT));
		
			//Add new Product for Existing menu
			foundation.click(SelfService.BTN_ADD_PRODUCT);
			textBox.enterText(SelfService.SEARCH_ADD_PRODUCT, data.get(6));
			foundation.click(selfService.selectProductToAdd(data.get(6)));
			foundation.click(SelfService.BTN_ADD_PRODUCT_POPUP);
			
			//verify newly added product Featured Product CheckBox is unchecked
			foundation.scrollIntoViewElement(selfService.objProductExpand(data.get(4)));
			foundation.click(selfService.objProductExpand(data.get(4)));
			CustomisedAssert.assertFalse(checkBox.isChecked( SelfService.CHK_FEATURED_PRODUCT));
			foundation.click(SelfService.BTN_CANCEL_MENU);
		}
		catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		finally
		{
			//resetting
			foundation.refreshPage();
			navigationBar.navigateToMenuItem(menu);
			CustomisedAssert.assertTrue(foundation.isDisplayed(SelfService.LBL_SELFSERVICE));
			dropDown.selectItem(SelfService.DPD_LOCATION,data.get(0),Constants.TEXT);
			textBox.enterText(SelfService.TXT_SEARCH,data.get(7));
			foundation.click(selfService.objSelectMenu(data.get(7)));
			CustomisedAssert.assertTrue(foundation.getText(SelfService.LBL_SELFSERIVICE_MENU).equals(data.get(2)));
			foundation.scrollIntoViewElement(SelfService.BTN_DELETE);
			foundation.click(SelfService.BTN_DELETE);
			foundation.alertAccept();
		}
	}
	
	@Test(description = "178414-ADM - Login with Operator/Driver/Reporter Login")
    public void verifyOtherUser() {
        try {
            final String CASE_NUM = "178414";
            // Reading test data from DataBase
            rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
                        
            List<String> data =Arrays
                    .asList(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION).split(Constants.DELIMITER_TILD));
            
            //verify operator user
            navigationBar.launchBrowserAndSelectOrg(data.get(0),data.get(1), propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
            
            navigationBar.verifyMenuBarAreClickableOrNot();
            
            navigationBar.logOutFromParticularUser();
            
            //verify reporter user



           navigationBar.launchBrowserAndSelectOrg(data.get(2),data.get(3), propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
            
            navigationBar.verifyReportAdminAreClickableOrNot();
            
            navigationBar.logOutFromParticularUser();
            
            //verify driver user

           navigationBar.launchBrowserAndSelectOrg(data.get(4),data.get(5), propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
            
            navigationBar.verifyMenuBarAreClickableOrNot();
            
            navigationBar.logOutFromParticularUser();
        }
        catch (Exception exc) {
            TestInfra.failWithScreenShot(exc.toString());
        }
    }
}
