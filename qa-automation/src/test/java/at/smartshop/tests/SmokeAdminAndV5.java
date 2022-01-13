package at.smartshop.tests;

import static org.junit.Assert.assertFalse;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import at.framework.browser.Browser;
import at.framework.database.mssql.Queries;
import at.framework.database.mssql.ResultSets;
import at.framework.files.PropertyFile;
import at.framework.generic.DateAndTime;
import at.framework.generic.Strings;
import at.framework.ui.CheckBox;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.Table;
import at.framework.ui.TextBox;
import at.smartshop.database.columns.CNLocation;
import at.smartshop.database.columns.CNNavigationMenu;
import at.smartshop.database.columns.CNUserRoles;
import at.smartshop.database.columns.CNV5Device;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.pages.CreatePromotions;
import at.smartshop.pages.EditPromotion;
import at.smartshop.pages.GlobalProduct;
import at.smartshop.pages.LocationList;
import at.smartshop.pages.LocationSummary;
import at.smartshop.pages.NavigationBar;
import at.smartshop.pages.PromotionList;
import at.smartshop.pages.TaxList;
import at.smartshop.pages.UserList;
import at.smartshop.pages.UserRoles;
import at.smartshop.v5.pages.LandingPage;
import at.smartshop.v5.pages.Order;
import at.smartshop.v5.pages.ProductSearch;

public class SmokeAdminAndV5 extends TestInfra{
	private PropertyFile propertyFile = new PropertyFile();
	private ResultSets dataBase = new ResultSets();
	private Foundation foundation = new Foundation();
	private NavigationBar navigationBar = new NavigationBar();
	private CreatePromotions createPromotions = new CreatePromotions();
	private LocationList locationList = new LocationList();
	private Dropdown dropdown = new Dropdown();
	private TextBox textBox = new TextBox();
	private Strings strings = new Strings();
	private DateAndTime dateAndTime = new DateAndTime();
	private PromotionList promotionList = new PromotionList();
	private EditPromotion editPromotion = new EditPromotion();
	private UserRoles userRoles = new UserRoles();
	private UserList userList = new UserList();
	private CheckBox checkBox = new CheckBox();
	private Table table = new Table();
	private GlobalProduct globalProducts=new GlobalProduct();
	private LocationSummary locationSummary=new LocationSummary();
	private TaxList taxList=new TaxList();
	private LandingPage landingPage = new LandingPage();
	public Browser browser = new Browser();
	private ProductSearch productSearch=new ProductSearch();
	private Order order=new Order();
	
	

	private Map<String, String> rstNavigationMenuData;
	private Map<String, String> rstLocationData;
	private Map<String, String> rstLocationListData;
	private Map<String, String> rstLocationSummaryData;
	private Map<String, String> rstUserRolesData;
	private Map<String, String> rstV5DeviceData;

	@Test(description = "167028-Tax list - create and edit tax rate")
	public void createAndEditTaxRate() {
		final String CASE_NUM = "167028";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		
		List<String> menuItem = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> language = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
		List<String> requiredData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		String locationName=propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE);
		String productName=rstV5DeviceData.get(CNV5Device.PRODUCT_NAME);
		String taxCategory=requiredData.get(0);
		String taxRateName=requiredData.get(1);
		String taxRate1=requiredData.get(2);
		
		try {
			//launch and select org
			navigationBar.launchBrowserAsSuperAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			
			// Assign tax category to product
			globalProducts.assignTaxCategory(productName,productName);

			// assign tax mapping - location summary
			locationSummary.navigateAndAddTaxMap(locationName,taxCategory, taxRateName);

			// edit tax rate OR update tax rate
			assertTrue(taxList.updateTaxRate(taxRateName, taxRate1, requiredData.get(3), requiredData.get(3), requiredData.get(3)));

			// login to application
			landingPage.launchV5AndSelectLanguageEnglish();
			
			//search and add product to cart
			assertTrue(productSearch.searchProduct(productName));

			//verify the tax in order page
			order.verifyTax(taxRate1);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		finally {
			//reset data-- 
			//remove tax mapping
			locationSummary.navigateAndRemoveTaxMap(locationName,taxCategory);
			
			//remove tax assignment from product
			globalProducts.removeTaxCategory(productName);
		}
	}
	
	@Test(description = "167029-User and roles- create user role assign it to user, edit it then verify disable ")
	public void createUserAndRoleEditDelete() {
		final String CASE_NUM = "167029";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstUserRolesData = dataBase.getUserRolesData(Queries.USER_ROLES, CASE_NUM);
		
		String menuItem = rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM);
		List<String> requiredData = Arrays
				.asList(rstUserRolesData.get(CNUserRoles.ROW_RECORD).split(Constants.DELIMITER_TILD));
		List<String> roleData = Arrays
				.asList(rstUserRolesData.get(CNUserRoles.ROLE_NAME).split(Constants.DELIMITER_TILD));
		String userEmail="";
		try {
			//launch and select org
			navigationBar.launchBrowserAsSuperAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			
			//create new user and add role as super
			navigationBar.navigateToMenuItem(menuItem);
			userEmail=userList.createNewUser(requiredData.get(0), requiredData.get(1), requiredData.get(2));
			userList.updateORAddUserRole(roleData.get(0));
			
			//logout and login with created user and verify role applied
			login.logout();
			login.login(userEmail,
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			assertTrue(foundation.isDisplayed(NavigationBar.MENU_SUPER));
			
			//update user role to admin
			navigationBar.navigateToMenuItem(menuItem);
			userList.searchAndSelectUser(userEmail);
			userList.updateORAddUserRole(roleData.get(1));
			assertFalse(foundation.isDisplayed(NavigationBar.MENU_SUPER));
			
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		finally {
			//reset data
			navigationBar.navigateToMenuItem(menuItem);
			userList.disableUser(userEmail);
			
		}
	}
	
	
}
