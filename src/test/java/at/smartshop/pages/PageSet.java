package at.smartshop.pages;

import static org.testng.Assert.assertTrue;

import java.util.List;

import org.openqa.selenium.By;
import org.testng.Assert;

import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.keys.Constants;

public class PageSet {

	public static final By LBL_PAGESET_LIST = By.id("PageSet List");
	public static final By LBL_PAGESET_CREATE = By.id("PageSet Create");
	public static final By BTN_CREATE_NEW = By.id("newBtn");
	public static final By BTN_ADD_PAGEDEF = By.xpath("//span[text()=' Add PageDef']");
	public static final By BTN_ADD_INTENT = By.xpath("//span[text()=' Add Intent']");
	public static final By BTN_SAVE = By.xpath("//button[text()='Save']");
	public static final By BTN_CANCEL = By.xpath("//button[text()='Cancel']");
	public static final By TXTBX_PAGESET = By.xpath("//input[@placeholder='PageSet Name']");
	public static final By LBL_PAGESET_ERROR = By.xpath("//div[@class='alert alert-error']//p");
	public static final By DRP_SERVICE = By.xpath("//select[@class='pageserviceselect']");
	public static final By DRP_PAGEDEF = By.xpath("//select[@class='pageNAMEselect']");
	public static final By DRP_PAGEINTENT = By.xpath("//select[@class='pageintentselect']");
	public static final By BTN_DELETE_PAGEDEF = By.xpath("//div[@class='delete-item delete-pagedef']//span[text()=' Delete']");
	public static final By DRP_EXISTING_PAGESET = By.xpath("//div[@class='pageset-selectlist']//select");
	public static final By TXTBX_SEARCHBOX = By.xpath("//input[@aria-controls='dt']");
	public static final By SELECT_GRID = By.xpath("//td[@class=\" sorting_1\"]");
	public static final By LBL_PAGESET_SUMMARY = By.id("PageSet Summary");
	
	private Foundation foundation = new Foundation();
	private Dropdown dropDown = new Dropdown();
	private TextBox textBox = new TextBox();
	
	public void createPageSet(String pageSet_Name, String service_Name,String pageSet_Def, String pageSet_Intent,String existing_PageSet) {
		try {
			textBox.enterText(PageSet.TXTBX_PAGESET,pageSet_Name);
			foundation.click(PageSet.BTN_ADD_PAGEDEF);
			dropDown.selectItem(PageSet.DRP_SERVICE,service_Name, Constants.TEXT);
			dropDown.selectItem(PageSet.DRP_PAGEDEF,pageSet_Def, Constants.TEXT);
			foundation.click(PageSet.BTN_ADD_INTENT);
			dropDown.selectItem(PageSet.DRP_PAGEINTENT,pageSet_Intent, Constants.TEXT);
			foundation.click(PageSet.BTN_DELETE_PAGEDEF);
			dropDown.selectItem(PageSet.DRP_EXISTING_PAGESET,existing_PageSet, Constants.TEXT);
			
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
}
