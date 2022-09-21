package at.smartshop.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.testng.Assert;

import at.framework.generic.CustomisedAssert;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.keys.Constants;

public class PageSet {
	private NavigationBar navigationBar = new NavigationBar();

	public static final By LBL_PAGESET_LIST = By.id("PageSet List");
	public static final By LBL_PAGESET_CREATE = By.id("PageSet Create");
	public static final By BTN_CREATE_NEW = By.id("newBtn");
	public static final By BTN_ADD_PAGEDEF = By.xpath("//span[text()=' Add PageDef']");
	public static final By BTN_ADD_INTENT = By.xpath("//span[text()=' Add Intent']");
	public static final By DATE_CREATED = By.id("Date Created");
	public static final By STATUS_TXT = By.id("Status");
	public static final By DPD_FILTERPAGESET = By.id("filtervalue");
	public static final By BTN_SAVE = By.xpath("//button[text()='Save']");
	public static final By BTN_CANCEL = By.xpath("//button[text()='Cancel']");
	public static final By TXTBX_PAGESET = By.xpath("//input[@placeholder='PageSet Name']");
	public static final By LBL_PAGESET_ERROR = By.xpath("//div[@class='alert alert-error']//p");
	public static final By DRP_SERVICE = By.xpath("//select[@class='pageserviceselect']");
	public static final By DRP_PAGEDEF = By.xpath("//select[@class='pageNAMEselect']");
	public static final By DRP_PAGEINTENT = By.xpath("//select[@class='pageintentselect']");
	public static final By GRID_DISABLE = By.xpath("//td[text()='Disabled']");
	public static final By TXT_ORG = By.id("pageset-org");
	public static final By BTN_DELETE_PAGEDEF = By
			.xpath("//div[@class='delete-item delete-pagedef']//span[text()=' Delete']");
	public static final By DRP_EXISTING_PAGESET = By.xpath("//div[@class='pageset-selectlist']//select");
	public static final By TXTBX_SEARCHBOX = By.xpath("//input[@aria-controls='dt']");
	public static final By SELECT_GRID = By.xpath("//td[@class=' sorting_1']");
	public static final By LBL_PAGESET_SUMMARY = By.id("PageSet Summary");
	public static final By DPD_ISDISABLED = By.id("pageSetisdisable");
	public static final By TXT_NAME = By.xpath("//td[@class=' sorting_1']/span");
	public static final By TBL_GRID = By.xpath("//tr[@class='odd']");

	private Foundation foundation = new Foundation();
	private Dropdown dropDown = new Dropdown();
	private TextBox textBox = new TextBox();

	/**
	 * verify disable pageset
	 * 
	 * @param createdPageset
	 * @param active
	 * @param dropdown
	 */
	public void verifyActiveAndDisablePageset(String createdPageset, String active, String dropdown, String disable,
			String disabled) {
		textBox.enterText(PageSet.TXTBX_SEARCHBOX, createdPageset);
		String text = foundation.getText(PageSet.TBL_GRID);
		CustomisedAssert.assertTrue(text.contains(active));
		foundation.objectClick(PageSet.SELECT_GRID);
		CustomisedAssert.assertTrue(foundation.isDisplayed(PageSet.LBL_PAGESET_SUMMARY));
		dropDown.selectItem(PageSet.DPD_ISDISABLED, dropdown, Constants.TEXT);
		foundation.waitforElementToBeVisible(PageSet.BTN_SAVE, Constants.THREE_SECOND);
		foundation.click(PageSet.BTN_SAVE);
		dropDown.selectItem(DPD_FILTERPAGESET, disable, Constants.TEXT);
		textBox.enterText(PageSet.TXTBX_SEARCHBOX, createdPageset);
		foundation.waitforElementToBeVisible(GRID_DISABLE, Constants.THREE_SECOND);
		text = foundation.getText(PageSet.GRID_DISABLE);
		CustomisedAssert.assertTrue(text.contains(disabled));

	}

	/**
	 * verify dropDown in pageset
	 * 
	 * @param dd1
	 * @param dd2
	 * @param dd3
	 */
	public void verifyDropdownValueInPageset(String dd1, String dd2, String dd3) {
		List<String> value = dropDown.getAllItems(PageSet.DPD_FILTERPAGESET);
		List<String> expectedValues = new ArrayList<String>();
		expectedValues.add(dd1);
		expectedValues.add(dd2);
		expectedValues.add(dd3);
		CustomisedAssert.assertTrue(value.equals(expectedValues));
	}

	/**
	 * search with created pageset and capture pageset name
	 * 
	 * @param name
	 */
	public void capturePagesetName(String name, String menu) {
		foundation.waitforElementToBeVisible(PageSet.TXTBX_SEARCHBOX, Constants.THREE_SECOND);
		textBox.enterText(TXTBX_SEARCHBOX, name);
		foundation.waitforElementToBeVisible(TXT_NAME, Constants.THREE_SECOND);
		String namepageset = foundation.getText(TXT_NAME);
		navigationBar.navigateToMenuItem(menu);
		foundation.waitforElementToBeVisible(OrgSummary.LBL_ORG_SUMMARY, Constants.THREE_SECOND);
		dropDown.selectItem(OrgSummary.DPD_PAGESET, namepageset, Constants.TEXT);
		foundation.waitforElementToBeVisible(OrgSummary.BTN_SAVE, Constants.THREE_SECOND);
		foundation.click(OrgSummary.BTN_SAVE);
		foundation.threadWait(Constants.THREE_SECOND);

	}

	public void createPageSet(String pageSet_Name, String service_Name, String pageSet_Def, String pageSet_Intent,
			String existing_PageSet) {
		try {
			textBox.enterText(PageSet.TXTBX_PAGESET, pageSet_Name);
			foundation.click(PageSet.BTN_ADD_PAGEDEF);
			dropDown.selectItem(PageSet.DRP_SERVICE, service_Name, Constants.TEXT);
			dropDown.selectItem(PageSet.DRP_PAGEDEF, pageSet_Def, Constants.TEXT);
			foundation.click(PageSet.BTN_ADD_INTENT);
			dropDown.selectItem(PageSet.DRP_PAGEINTENT, pageSet_Intent, Constants.TEXT);
			foundation.click(PageSet.BTN_DELETE_PAGEDEF);
			dropDown.selectItem(PageSet.DRP_EXISTING_PAGESET, existing_PageSet, Constants.TEXT);

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	/**
	 * search pageSet and verify Org is mapped
	 * 
	 * @param pageset
	 * @param dropdown
	 * @param org
	 */
	public void searchPagesetAndverifyOrgIsMapped(String menu,String pageset, String org) {
		navigationBar.navigateToMenuItem(menu);
		foundation.threadWait(Constants.THREE_SECOND);
		textBox.enterText(PageSet.TXTBX_SEARCHBOX, pageset);
		foundation.objectClick(PageSet.SELECT_GRID);
		foundation.waitforElementToBeVisible(LBL_PAGESET_SUMMARY, Constants.THREE_SECOND);
		CustomisedAssert.assertTrue(foundation.isDisplayed(PageSet.LBL_PAGESET_SUMMARY));
		foundation.waitforElementToBeVisible(TXT_ORG, Constants.THREE_SECOND);
		String text = foundation.getText(TBL_GRID);
		CustomisedAssert.assertTrue(text.contains(org));		
	}
	
	/**
	 * disable pageset
	 * @param dropdown
	 */
	public void disablePageset(String menu,String pageset,String dropdown) {
		navigationBar.navigateToMenuItem(menu);
		foundation.threadWait(Constants.THREE_SECOND);
		textBox.enterText(PageSet.TXTBX_SEARCHBOX, pageset);
		foundation.objectClick(PageSet.SELECT_GRID);
		foundation.waitforElementToBeVisible(LBL_PAGESET_SUMMARY, Constants.THREE_SECOND);
		dropDown.selectItem(PageSet.DPD_ISDISABLED, dropdown, Constants.TEXT);
		foundation.waitforElementToBeVisible(PageSet.BTN_SAVE, Constants.THREE_SECOND);
		foundation.click(PageSet.BTN_SAVE);
	}

	/**
	 * verify create pageset
	 * 
	 * @param title
	 */
	public void verifyCreatePageset(String title) {
		CustomisedAssert.assertTrue(foundation.isDisplayed(PageSet.LBL_PAGESET_LIST));
		CustomisedAssert.assertTrue(foundation.isDisplayed(PageSet.DATE_CREATED));
		CustomisedAssert.assertTrue(foundation.isDisplayed(PageSet.STATUS_TXT));
		foundation.click(PageSet.BTN_CREATE_NEW);
		CustomisedAssert.assertTrue(foundation.isDisplayed(PageSet.TXTBX_PAGESET));
		textBox.enterText(PageSet.TXTBX_PAGESET, title);
		foundation.click(PageSet.BTN_SAVE);
		foundation.waitforElementToBeVisible(PageSet.LBL_PAGESET_LIST, Constants.THREE_SECOND);
	}
}
