package at.smartshop.pages;

import java.util.List;

import org.openqa.selenium.By;

import at.framework.browser.Factory;
import at.framework.files.PropertyFile;
import at.framework.generic.CustomisedAssert;
import at.framework.generic.Strings;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.database.columns.CNConsumerSearch;
import at.smartshop.database.columns.CNNavigationMenu;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;

public class UserList extends Factory {
	private Dropdown dropdown = new Dropdown();
	private Foundation foundation = new Foundation();
	private TextBox textBox = new TextBox();
	private Strings string = new Strings();
	private PropertyFile propertyFile = new PropertyFile();

	public static final By BTN_MANAGE_ROLES = By.id("customBtn");
	public static final By TXT_FILTER = By.cssSelector("#dt_filter input");
	public static final By BTN_CREATE_NEW = By.id("createNewBtn");
	public static final By TXT_SEARCH_LOC = By.id("loc-dropdown");
	public static final By TXT_SPINNER_MSG = By.className("humane");
	public static final By BTN_UPDATE_USER = By.id("saveBtn");
	public static final By EDIT_USERS = By.id("pagetitle");
	public static final By DPD_ORG = By.cssSelector("select#org-dropdown");
	public static final By LBL_USER_LIST = By.id("User List");
	public static final By LNK_ORG_REMOVE = By.xpath("//div[@id='org-select']//li/span");
	public static final By LNK_ORG_REMOVE_ALL = By.xpath("//div[@id='org-select']//span//ul/span");
	public static final By LNK_LOCATION_REMOVE_ALL = By.xpath("//div[@id='location-select']//span//ul/span");
	public static final By CREATE_NEW_ROLE = By.xpath("//div[@class='dropdown standarduserBtn']//button");
	public static final By CREATE_NEW = By.xpath("//button[@id='newBtn']");
	public static final By FIRST_NAME_FIELD = By.id("firstname");
	public static final By LAST_NAME_FIELD = By.id("lastname");
	public static final By EMAIL_ADDRESS_FIELD = By.id("email");
	public static final By BTN_COPY_USER = By.id("copyBtn");
	public static final By SELECT_ORG = By.xpath("(//ul[@class='select2-selection__rendered'])[1]");
	public static final By PIN_TXT = By.id("pin");
	public static final By SELECT_LOCATION = By.id("loc-dropdown");
	public static final By SELECT_CLIENT = By.xpath("//span[@id='select2-client-container']");
	public static final By ENTER_CLIENT = By.xpath("//span[@class='select2-search select2-search--dropdown']//input");
	public static final By CLICK_CLIENT = By
			.xpath("//li[@class='select2-results__option select2-results__option--highlighted']");
	public static final By SELECT_NATIONAL_ACCOUNT = By.xpath("//input[@placeholder='Select National Account(s)']");
	public static final By CLICK_NATIONAL_ACCOUNT = By
			.xpath("//li[@class='select2-results__option select2-results__option--highlighted']");
	public static final By GENERATE_PIN = By.id("genpin");
	public static final By SAVE_USER = By.xpath("//button[@id='saveBtn']");
	public static final By ADMIN_TAB = By.id("li5");
	public static final By SELECTED_ORG = By.xpath("//ul[@id='select2-org-dropdown-results']//li");
	public static final By SELECTED_LOCATION = By.xpath("//ul[@id='select2-loc-dropdown-results']//li");
	public static final By CANCEL_USER = By.id("cancelBtn");
	public static final By CONFIRM_CANCEL = By.xpath("//button[@class='ajs-button ajs-cancel']");
	public static final By SEARCH_FILTER = By.xpath("//input[@aria-controls='dt']");
	public static final By TBL_DATA = By.xpath("//td[@class=' sorting_1']");
	public static final By DISABLE_USER = By.xpath("//button[@id='disableBtn']");
	public static final By ENABLE_USER = By.xpath("//button[text()='Enable User']");
	public static final By CONFIRM_DISABLE = By.xpath("//button[@class='ajs-button ajs-ok']");
	public static final By FIRST_NAME_ERROR = By.xpath("//label[@id='firstname-error']");
	public static final By LAST_NAME_ERROR = By.xpath("//label[@id='lastname-error']");
	public static final By EMAIL_ERROR = By.id("email-error");
	public static final By LOCATION_ERROR = By.id("hasLoc-error");
	public static final By MANAGE_USER_ROLES = By.id("li2");
	public static final By SECURITY_ROLE = By.id("role");
	public static final By EXPIRATION_DATE = By.id("enddate");
	public static final By SELECT_EXPIRATION_DATE = By.xpath("(//td[@class='day  active'])[2]");
	public static final By SEND_NOTIFICATION = By.id("notify");
	public static final By ADD_ROLE_USER_BTN = By.id("addRoleBtn");
	public static final By CANCEL_BTN = By.id("cancelBtn2");
	public static final By MANAGE_PASSWORD = By.id("li3");
	public static final By PASSWORD_TXT = By.id("password");
	public static final By CNFRM_PASSWORD_TXT = By.id("passwordConfirm");
	public static final By SAVE_PASSWORD_BTN = By.id("savePasswordBtn");
	public static final By USER_SUMMARY = By.id("li1");
	public static final By EGIFTCARD_CHECKBOX = By
			.xpath("//input[contains(@name,'hasview-c793c084c70e11ec9d640242ac12000')]");
	public static final By REMOVE_USER_ROLES = By.xpath("//td[@class=' sorting_1']");
	public static final By CANCEL_USER_ROLE = By.id("cancelBtn2");
	public static final By CANCEL_USER_PASSWORD = By.id("cancelBtn3");
	public static final By NA_PLACE_HOLDER = By.xpath("//input[@placeholder='Select National Account(s)']");
	public static final By CLICK_OUTSIDE = By.id("footer");
	public static final By SELECT_NEXT = By.xpath("//th[@class='next']");
	public static final By SELECT_DATE = By.xpath("//td[@class='day  active']");
	public static final By DELETE_ROLE = By.xpath("//a[@class='fa fa-trash icon']");
	public static final By TXT_SEARCH_ROLE = By.xpath("//input[@aria-controls='dt']");
	public static final By DRP_SELECT_ROLE = By.xpath("//a[@id='standarduserBtn']");
	public static final By SELECTED_LOC = By.xpath(
			"(//span[contains(@class,'select2-selection--multiple')]//ul[@class='select2-selection__rendered'])[2]");
	public static final By DPD_DEFAULT_ORG = By.id("defaultorg");
	public static final By DPD_SELECTED_ORG = By.id("org-dropdown");
	public static final By BTN_MANAGE_FEATURE = By.id("manageFeaturesBtn");
	public static final By FEATURE_LIST_PAGE = By.id("Permission Feature List");

	public By objRoleName(String roleName) {
		return By.xpath("//td[text()='" + roleName + "']");
	}

	public void selectOrgs(By object, List<String> orgName) {
		for (int i = 0; i < orgName.size(); i++) {
			dropdown.selectItem(object, orgName.get(i), Constants.TEXT);
		}
	}

	public void clickOrgs(By object, String orgName) {
		foundation.getAttributeValueofListElement(object, orgName);
	}

	/**
	 * click on copy user and enter all mandatory fields
	 * 
	 * @param fname
	 * @param lname
	 * @param address
	 * @param org
	 * @param loc
	 */
	public void clickOnCopyUserAndEnterAllMandatoryFields(String fname, String lname, String address, String org,
			String loc) {
		foundation.click(BTN_COPY_USER);
		foundation.threadWait(Constants.SHORT_TIME);
		CustomisedAssert.assertTrue(foundation.getAttribute(FIRST_NAME_FIELD, "value").isEmpty());
		CustomisedAssert.assertTrue(foundation.getAttribute(LAST_NAME_FIELD, "value").isEmpty());
		CustomisedAssert.assertTrue(foundation.getAttribute(EMAIL_ADDRESS_FIELD, "value").isEmpty());
		textBox.enterText(UserList.FIRST_NAME_FIELD, fname);
		textBox.enterText(UserList.LAST_NAME_FIELD, lname);
		textBox.enterText(UserList.EMAIL_ADDRESS_FIELD, address);
		dropdown.selectItem(UserList.DPD_DEFAULT_ORG, org, Constants.TEXT);
		foundation.threadWait(Constants.THREE_SECOND);
		dropdown.selectItem(UserList.DPD_SELECTED_ORG, org, Constants.TEXT);
		foundation.threadWait(Constants.THREE_SECOND);
		dropdown.selectItem(UserList.SELECT_LOCATION, loc, Constants.TEXT);
		foundation.click(UserList.CLICK_OUTSIDE);
		foundation.click(UserList.BTN_UPDATE_USER);
		foundation.threadWait(Constants.THREE_SECOND);
	}

	public String createNewUser(String location, String client, String nationalAccount) {
		String firstName = Constants.AUTO_TEST + string.getRandomCharacter();
		String lastName = Constants.AUTO_TEST + string.getRandomCharacter();
		String email = string.getRandomCharacter() + Constants.AUTO_TEST_EMAIL;
		foundation.click(CREATE_NEW_ROLE);
		textBox.enterText(FIRST_NAME_FIELD, firstName);
		textBox.enterText(LAST_NAME_FIELD, lastName);
		textBox.enterText(EMAIL_ADDRESS_FIELD, email);
		// textBox.enterText(PIN_TXT,
		// propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD,
		// FilePath.PROPERTY_CONFIG_FILE));
		dropdown.selectItem(SELECT_LOCATION, location, Constants.TEXT);
		foundation.click(CLICK_OUTSIDE);
		foundation.click(SELECT_CLIENT);
		textBox.enterText(ENTER_CLIENT, client);
		foundation.click(CLICK_CLIENT);
		foundation.threadWait(Constants.TWO_SECOND);
		foundation.click(SELECT_NATIONAL_ACCOUNT);
		textBox.enterText(SELECT_NATIONAL_ACCOUNT, nationalAccount);
		foundation.click(CLICK_NATIONAL_ACCOUNT);
		foundation.click(SAVE_USER);
		foundation.waitforElement(PASSWORD_TXT, Constants.LONG_TIME);
		foundation.click(MANAGE_PASSWORD);
		foundation.waitforElement(PASSWORD_TXT, Constants.LONG_TIME);
		textBox.enterText(PASSWORD_TXT,
				propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
		textBox.enterText(CNFRM_PASSWORD_TXT,
				propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
		foundation.click(SAVE_PASSWORD_BTN);
		foundation.waitforElement(DISABLE_USER, Constants.FIFTEEN_SECOND);
		foundation.waitforElementToDisappear(UserList.TXT_SPINNER_MSG, Constants.MEDIUM_TIME);
		return email;
	}

	public void removeRole(String navigationData, String userEmail) {
		textBox.enterText(UserList.SEARCH_FILTER, userEmail);
		foundation.click(UserList.TBL_DATA);
		foundation.click(UserList.MANAGE_USER_ROLES);
		foundation.click(UserList.REMOVE_USER_ROLES);
		foundation.click(UserList.CONFIRM_DISABLE);
	}

	public void updateORAddUserRole(String newRole) {
		foundation.click(UserList.MANAGE_USER_ROLES);
		if (foundation.isDisplayed(REMOVE_USER_ROLES)) {
			foundation.click(REMOVE_USER_ROLES);
			foundation.click(CONFIRM_DISABLE);
		}
		foundation.threadWait(Constants.ONE_SECOND);
		foundation.refreshPage();
		foundation.click(UserList.MANAGE_USER_ROLES);
		dropdown.selectItem(UserList.SECURITY_ROLE, newRole, Constants.TEXT);
		foundation.click(UserList.EXPIRATION_DATE);
		foundation.click(UserList.SELECT_EXPIRATION_DATE);
		foundation.click(UserList.SEND_NOTIFICATION);
		foundation.click(UserList.ADD_ROLE_USER_BTN);
		foundation.threadWait(Constants.SHORT_TIME);
		// Navigate to User Summary Tab
		foundation.click(UserList.USER_SUMMARY);
		foundation.threadWait(Constants.TWO_SECOND);
		// Click on Update User Button
		foundation.click(UserList.SAVE_USER);
		foundation.waitforElementToDisappear(UserList.TXT_SPINNER_MSG, Constants.MEDIUM_TIME);
		foundation.threadWait(Constants.TWO_SECOND);
	}

	public void disableUser(String userFirstName) {
		textBox.enterText(UserList.SEARCH_FILTER, userFirstName);
		foundation.click(UserList.TBL_DATA);
		// foundation.click(UserList.MANAGE_USER_ROLES);
		foundation.click(UserList.DISABLE_USER);
		foundation.click(UserList.CONFIRM_DISABLE);
		foundation.waitforElement(ENABLE_USER, Constants.EXTRA_LONG_TIME);
	}

	public void searchAndSelectUser(String userFirstName) {
		textBox.enterText(UserList.SEARCH_FILTER, userFirstName);
		foundation.click(UserList.TBL_DATA);
	}

	/**
	 * search user and verify copy user
	 * 
	 * @param fname
	 */
	public void searchUserAndVerifyCopyUser(String fname) {
		searchAndSelectUser(fname);
		CustomisedAssert.assertTrue(foundation.getText(UserList.EDIT_USERS).contains(fname));
		foundation.click(UserList.BTN_COPY_USER);
		foundation.threadWait(Constants.SHORT_TIME);
		CustomisedAssert.assertTrue(foundation.getAttribute(UserList.FIRST_NAME_FIELD, "value").isEmpty());
	}

	/**
	 * Select location in User and Roles
	 * 
	 * @param location
	 *
	 */
	public void selectLocation(String location) {
		CustomisedAssert.assertTrue(foundation.isDisplayed(UserRoles.LBL_VIEW_ROLE));
		foundation.click(UserList.LNK_LOCATION_REMOVE_ALL);
		dropdown.selectItem(UserList.SELECT_LOCATION, location, Constants.TEXT);
		foundation.getText(UserList.SELECTED_LOC);
		CustomisedAssert.assertTrue(foundation.getText(UserList.SELECTED_LOC).contains(location));
		foundation.click(UserList.BTN_UPDATE_USER);
	}

	/**
	 * verify edit user page by clicking on operator
	 * 
	 * @param operator
	 * @param operatorname
	 */
	public void verifyEditUserPage(String operator, String operatorname) {
		CustomisedAssert.assertTrue(foundation.isDisplayed(UserList.LBL_USER_LIST));
		foundation.waitforElementToBeVisible(BTN_MANAGE_ROLES, 3);
		foundation.click(BTN_MANAGE_ROLES);
		textBox.enterText(UserList.SEARCH_FILTER, operator);
		foundation.click(objRoleName(operator));
		foundation.waitforElementToBeVisible(ViewRole.LBL_HEADER, 3);
		CustomisedAssert.assertTrue(foundation.isDisplayed(ViewRole.LBL_HEADER));
		CustomisedAssert.assertTrue(foundation.isDisplayed(ViewRole.USER_ASSOCIATE_ROLES));
//		foundation.click(objRoleName(operatorname));
//		foundation.waitforElementToBeVisible(EDIT_USERS, 3);
//		CustomisedAssert.assertTrue(foundation.isDisplayed(EDIT_USERS));
	}

	/**
	 * verify enabled E-gift card
	 * 
	 * @param operator
	 */
	public void verifyEnabledEgiftCard(String operator) {
		CustomisedAssert.assertTrue(foundation.isDisplayed(UserList.LBL_USER_LIST));
		foundation.waitforElementToBeVisible(BTN_MANAGE_ROLES, 3);
		foundation.click(BTN_MANAGE_ROLES);
		textBox.enterText(UserList.SEARCH_FILTER, operator);
		foundation.click(objRoleName(operator));
		foundation.waitforElementToBeVisible(ViewRole.LBL_HEADER, 3);
		CustomisedAssert.assertTrue(foundation.isDisplayed(ADMIN_TAB));
		foundation.click(ADMIN_TAB);
		foundation.threadWait(Constants.SHORT_TIME);
		foundation.click(EGIFTCARD_CHECKBOX);
		foundation.waitforElementToBeVisible(BTN_UPDATE_USER, 3);
		foundation.click(BTN_UPDATE_USER);
		foundation.waitforElementToBeVisible(BTN_UPDATE_USER, 3);
	}
}
