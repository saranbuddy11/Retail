package at.smartshop.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import at.framework.browser.Factory;

import java.util.List;

public class SelfService extends Factory {
	public static final By DPD_LOCATION = By.xpath("//select[@id='createurl']");
	public static final By TXT_MENU_NAME = By.cssSelector("#menu-name");
	public static final By BTN_SAVE = By.xpath("//button[text()='Save']");
	public static final By BTN_CREATE_NEW = By.xpath("//button[text()='Create New']");
	public static final By BTN_ADD_ITEM = By.xpath("//div[text()=' Add item button']");
	public static final By BTN_ADD = By.xpath("//div[@id='menuprd-add']");
	public static final By BTN_DELETE = By.xpath("//button[text()='Delete']");
	public static final By TXT_SPINNER_MSG = By.className("humane");
	public static final By TXT_SEARCH_PRODUCT=  By.xpath("//div[@id='menu-container']//input[@id='menuprd-search']");
	public static final By LBL_PRODUCT_NAME=  By.xpath("//div[@id='menu-container']//td[@id='menuprd-name_0']");
	public static final By LBL_BTN_ADD = By.xpath("//div[@id='menu-container']//div[@id='menuprd-add']");
	public static final By BTN_SUBMENU_ADD = By.cssSelector("div#submenu-add");
	public static final By LBL_FORWARD_ARROW = By.xpath("//div//i[contains(@class,'sub_toggle_icon')]");
	public static final By LBL_NO_PRINT = By.xpath("//span[@class='product-noprintgroup']");
	public static final By LBL_INHERIT_PRINT = By.xpath("//span[@class='product-inheritingprintgroup']");
	public static final By LBL_HAS_PRINT = By.xpath("//span[@class='product-hasprintgroup']");
	public static final By LBL_SELECT_ALL = By.xpath("//a[text()='Select All']");
	public static final By FILTER_MENU = By.cssSelector("#dt_filter > label > input[type=text]");
	public static final By RDO_BTN_SHOW = By.xpath("//input[@value='show']");
	public static final By RDO_BTN_Hide = By.xpath("//input[@value='hide']");
	public static final By LBL_ALL = By.xpath("//span[text()='All']");
	public static final By BTN_CANCEL = By.xpath("//button[text()='Cancel']");
	public static final By BTN_ADD_MENU = By.id("menu-addsubmenu");
	public static final By TXT_MENU_BUTTON_NAME = By.id("menu-addname");
	public static final By UPLOAD_BUTTON_IMG = By.xpath("//div[@id='upload-new-3']//..//div[@id='upload']//input");
	public static final By BTN_ADD_IMG = By.xpath("//div[@id='submenu-add']");
	public static final By BTN_ORDER_LIMIT= By.id("set-order-limits");
	public static final By TXT_MONDAY_STRT = By.id("monday-starttime");
	public static final By TXT_MONDAY_END = By.id("monday-endtime");
	public static final By BTN_MENU_UPLOAD = By.xpath("//a[@href='#upload-new-3']");
	public static final By TXT_ITEM = By.xpath("//div[@id='menubtn-0-Id']//div[@class='category-content']");
	public static final By TXT_MENU = By.xpath("//div[@id='menubtn-1-Id']//div[@class='category-content']");
	public static final By TXT_NO_MATCH = By.xpath("//td[@class='dataTables_empty']");
	public static final By TBL_CHECKBOX = By.xpath("//ul[@id='hide_menu_time']");

	public By objPrintCheckbox(String text) {
	return By.xpath("//b[text()='"+text+"']//..//..//input");
	}
	
	public void clickCheckbox() {
		WebElement checkBox = getDriver().findElement(TBL_CHECKBOX);
		List<WebElement> checkbox = checkBox.findElements(By.className("flex-checkbox")); 
		        int Size = checkbox.size();   	 
		        for(int i=0; i< Size; i++)          
		        {   
		         checkbox.get(i).click();	        
		        }	
		    }

	
}
