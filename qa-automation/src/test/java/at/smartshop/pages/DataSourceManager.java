package at.smartshop.pages;

import org.openqa.selenium.By;
import org.testng.Assert;

import at.framework.browser.Factory;
import at.framework.ui.CheckBox;
import at.framework.ui.TextBox;

public class DataSourceManager extends Factory {
	
	public static final By VALIDATE_DSM_HEADING = By.id("page-title");
	public static final By DSM_SEARCH_BOX = By.id("search-box");
	public static final By DSM_CHECKBOX = By.xpath("//td[@aria-describedby='datasourcemangergrid_issfenabled']//input");
	public static final By DSM_SUCCESS_POPUP = By.xpath("//div[@class='humane humane-libnotify-success']");
	public static final By VALIDATE_CSS_HEADING = By.id("CSS List");
	public static final By CSS_CREATE_NEW_BTN = By.id("newBtn");
	public static final By CSS_SEARCH_FILTER = By.xpath("//input[@type='text']");
	public static final By CSS_PATH = By.id("path");
	public static final By CSS_NAME = By.id("name");
	public static final By CSS_SAVE_BTN = By.id("saveBtn");
	public static final By CSS_CANCEL_BTN = By.id("cancelBtn");
	public static final By VALIDATE_CREATE_HEADING = By.id("CSS Create");
	public static final By VALIDATE_UPDATE_HEADING = By.id("CSS Show");
	public static final By CSS_GRID = By.xpath("//td[@class=' sorting_1']");
	public static final By CSS_PATH_ERROR = By.id("path-error");
	public static final By CSS_NAME_ERROR = By.id("name-error");
	public static final By CSS_SUCCESS_POPUP = By.xpath("//div[@class='humane ']");
	
	private TextBox textBox=new TextBox();
	private CheckBox checkBox=new CheckBox();
	public static final By TXT_SEARCH = By.id("search-box");
	
    public void unCheckCheckBox(String reportName) {
        try {
        	textBox.enterText(TXT_SEARCH, reportName);        	
        	By objCheckBox=By.xpath ("//td[text()='"+reportName+"']//..//td[@aria-describedby='datasourcemangergrid_issfenabled']/label/input");
        	checkBox.unCheck(objCheckBox);
                
        }catch(Exception exc) {
            Assert.fail(exc.toString());
        }
    }
}
