package at.smartshop.pages;

import java.util.List;

import org.openqa.selenium.By;

import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.smartshop.keys.Constants;

public class UserList {
	private Dropdown dropdown = new Dropdown();
	private Foundation foundation = new Foundation();
	
	public static final By BTN_MANAGE_ROLES = By.id("customBtn");
	public static final By TXT_FILTER = By.cssSelector("#dt_filter input");
	public static final By BTN_CREATE_NEW = By.id("createNewBtn");
    public static final By TXT_SEARCH_LOC = By.id("loc-dropdown");
    public static final By TXT_SPINNER_MSG = By.className("humane");
    public static final By BTN_UPDATE_USER = By.id("saveBtn");
    public static final By DPD_ORG = By.cssSelector("select#org-dropdown");
 	public static final By LBL_USER_LIST = By.id("User List");
 	public static final By LNK_ORG_REMOVE = By.xpath("//div[@id='org-select']//li/span");
	public static final By LNK_ORG_REMOVE_ALL = By.xpath("//div[@id='org-select']//span//ul/span");
	public static final By LNK_LOCATION_REMOVE_ALL = By.xpath("//div[@id='location-select']//span//ul/span");
	public static final By CREATE_NEW_ROLE = By.id("newBtn");
	public static final By FIRST_NAME_FIELD = By.id("firstname");
	public static final By LAST_NAME_FIELD = By.id("lastname");
	public static final By EMAIL_ADDRESS_FIELD = By.id("email");
	public static final By SELECT_LOCATION = By.id("loc-dropdown");
	public static final By SELECT_CLIENT = By.xpath("//span[@id='select2-client-container']");
	public static final By ENTER_CLIENT = By.xpath("//span[@class='select2-search select2-search--dropdown']//input");
	public static final By CLICK_CLIENT = By.xpath("//li[@class='select2-results__option select2-results__option--highlighted']");
	public static final By SELECT_NATIONAL_ACCOUNT = By.xpath("//input[@placeholder='Select National Account(s)']");
	public static final By CLICK_NATIONAL_ACCOUNT = By.xpath("//li[@class='select2-results__option select2-results__option--highlighted']");
	public static final By GENERATE_PIN = By.id("genpin");
	public static final By SAVE_USER = By.xpath("//button[@id='saveBtn']");
	public static final By CANCEL_USER = By.id("cancelBtn");
	public static final By CONFIRM_CANCEL = By.xpath("//button[@class='ajs-button ajs-cancel']");
	public static final By SEARCH_FILTER = By.xpath("//input[@aria-controls='dt']");
	public static final By TBL_DATA = By.xpath("//td[@class=' sorting_1']");
	public static final By DISABLE_USER = By.xpath("//button[@id='disableBtn']");
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
	public static final By CNFRM_PASSWORD_TXT= By.id("passwordConfirm");
	public static final By SAVE_PASSWORD_BTN = By.id("savePasswordBtn");
	public static final By USER_SUMMARY= By.id("li1");
	public static final By REMOVE_USER_ROLES= By.xpath("//td[@class=' sorting_1']");
	public static final By CANCEL_USER_ROLE = By.id("cancelBtn2");
	public static final By CANCEL_USER_PASSWORD = By.id("cancelBtn3");
	public static final By NA_PLACE_HOLDER= By.xpath("//input[@placeholder='Select National Account(s)']");
	public static final By CLICK_OUTSIDE= By.id("footer");
	public static final By SELECT_NEXT= By.xpath("//th[@class='next']");
	public static final By SELECT_DATE= By.xpath("//td[@class='day  active']");
	public static final By DELETE_ROLE= By.xpath("//a[@class='fa fa-trash icon']");

    public By objRoleName(String roleName) {
        return By.xpath("//td[text()='" + roleName + "']");
    }
    
    public void selectOrgs(By object, List<String> orgName) {
		for(int i=0; i<orgName.size(); i++) {
		dropdown.selectItem(object, orgName.get(i), Constants.TEXT);
		}
	}  
      

}
