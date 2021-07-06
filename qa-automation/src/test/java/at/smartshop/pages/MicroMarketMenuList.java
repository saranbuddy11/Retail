package at.smartshop.pages;

import org.openqa.selenium.By;

public class MicroMarketMenuList {

	public static final By FILTER_MENU = By.cssSelector("#dt_filter > label > input[type=text]");
	public static final By DPD_LOCATION = By.cssSelector("#createurl");
	public static final By TXT_PROD_SEARCH = By.cssSelector("#menuprd-search");
	public static final By LBL_PROD_NAME = By.cssSelector("#menuprd-name_0");
	public static final By BTN_SUBMENU_ADD = By.cssSelector("#submenu-add");
	public static final By TXT_MENU_NAME = By.cssSelector("#menu-name");
	public static final By BTN_SAVE = By.xpath("//button[text()='Save']");
	public static final By BTN_CREATE_NEW = By.xpath("//button[text()='Create New']");
	public static final By BTN_ADD_ITEM = By.xpath("//div[text()=' Add item button']");
	public static final By BTN_ADD = By.xpath("//div[@id='menuprd-add']");
	public static final By BTN_DELETE = By.xpath("//button[text()='Delete']");
	public static final By TXT_SPINNER_MSG = By.className("humane");

	public By getCurrentDayObj(String day) {
		By CHK_BOX_DAY;
		switch (day) {
		case "Monday":
			CHK_BOX_DAY = By.cssSelector("#monday-checkbox");
			break;
		case "Tuesday":
			CHK_BOX_DAY = By.cssSelector("#tuesday-checkbox");
			break;
		case "Wednesday":
			CHK_BOX_DAY = By.cssSelector("#wednesday-checkbox");
			break;
		case "Thursday":
			CHK_BOX_DAY = By.cssSelector("#thursday-checkbox");
			break;
		case "Friday":
			CHK_BOX_DAY = By.cssSelector("#friday-checkbox");
			break;
		case "Saturday":
			CHK_BOX_DAY = By.cssSelector("#saturday-checkbox");
			break;
		default:
			CHK_BOX_DAY = By.cssSelector("#sunday-checkbox");
		}
		return CHK_BOX_DAY;
	}

	public By menuNameObj(String menuName) {
		return By.xpath("//td[text()='" + menuName + "']");

	}
}
