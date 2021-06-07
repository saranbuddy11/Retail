package at.smartshop.pages;

import org.openqa.selenium.By;

public class UserList {
	public static final By BTN_MANAGE_ROLES = By.id("customBtn");
	public static final By TXT_FILTER = By.cssSelector("#dt_filter input");
	public static final By BTN_CREATE_NEW = By.id("createNewBtn");
    public static final By TXT_SEARCH_LOC = By.id("loc-dropdown");
    public static final By TXT_SPINNER_MSG = By.className("humane");
    public static final By BTN_UPDATE_USER = By.id("saveBtn");
    
    public By objRoleName(String roleName) {
        return By.xpath("//td[text()='" + roleName + "']");
    }
}
