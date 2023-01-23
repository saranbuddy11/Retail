package at.smartshop.pages;

import org.openqa.selenium.By;
import at.framework.browser.Factory;
import at.framework.generic.CustomisedAssert;
import at.framework.generic.Strings;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.keys.Constants;

public class VDIProvider extends Factory{
	private TextBox textBox = new TextBox();
	private Foundation foundation = new Foundation();
	private Dropdown dropDown = new Dropdown();
	private Strings string = new Strings();
	
		public static final By BTN_CREATE = By.id("newBtn");
		public static final By LBL_VPI_PROVIDER_LIST = By.id("VDI Provider List");
		public static final By DPD_VERSION = By.id("version");
		public static final By TXT_NAME = By.id("name");
		public static final By TXT_URL = By.id("outurl");
		public static final By TXT_USERNAME = By.id("outuname");
		public static final By TXT_PASSWORD = By.id("outpassword");
		public static final By BTN_GENERATE = By.id("generateBtn");
		public static final By BTN_CANCEL = By.id("cancelBtn");
		public static final By BTN_SAVE = By.id("saveBtn");
		public static final By BTN_DELETE = By.id("deleteBtn");
		public static final By LBL_VPI_PROVIDER_CREATE = By.xpath("//li[text()='VDI Provider Create']");
		public static final By LBL_VPI_PROVIDER_SUMMARY = By.xpath("//li[text()='VDI Provider Summary']");
		public static final By LBL_ENABLE_MSG = By.id("vdimsgsdt");
		public static final By TXT_CLIENTURL = By.id("inurl");
		public static final By TXT_CLIENTUSERNAME = By.id("inuname");
		public static final By TXT_CLIENTPASSWORD = By.id("inpassword");
		public static final By MSG_CREATED = By.xpath("//div[text()='VDI Provider created']");
		public static final By TXT_SEARCH = By.xpath("//input[@type='text']");
		public static final By TBL_ROW = By.xpath("//table//tbody[@role='alert']");
		public static final By LBL_CONFIRM_DELETE=By.xpath("//div[text()='Confirm Delete']");
		public static final By BTN_CONFIRM_YES=By.xpath("//button[@class='ajs-button ajs-ok']");
		public static final By BTN_CONFIRM_CANCEL=By.xpath("//button[@class='ajs-button ajs-cancel']");
		
		
		public By checkBoxClick(String name) {
				return By.xpath("//div[@id='" + name + "']//input[@type='checkbox']");
			}
		
		
		/**
		 * Creating API
		 * 
		 * @param Name
		 * @param version
		 * @param url
		 * @param username
		 * @param password
		 * @param data
		 */
		public void createVersionToGenerate(String Name, String version, String url, String username,String password,String data,String data1) {
		    CustomisedAssert.assertTrue(foundation.isDisplayed(VDIProvider.LBL_VPI_PROVIDER_LIST));
		    foundation.click(VDIProvider.BTN_CREATE);
		    CustomisedAssert.assertTrue(foundation.isDisplayed(VDIProvider.LBL_VPI_PROVIDER_CREATE));
		    foundation.click(VDIProvider.TXT_NAME);
		    textBox.enterText(VDIProvider.TXT_NAME,Name+string.getRandomCharacter());
		    foundation.click(VDIProvider.DPD_VERSION);
		    dropDown.selectItem(VDIProvider.DPD_VERSION, version,  Constants.TEXT);
		    CustomisedAssert.assertTrue(foundation.isDisplayed(VDIProvider.BTN_GENERATE));
		    CustomisedAssert.assertFalse(foundation.getAttributeValue(VDIProvider.TXT_CLIENTUSERNAME).length()>=40);
		    CustomisedAssert.assertFalse(foundation.getAttributeValue(VDIProvider.TXT_CLIENTPASSWORD).length()>=75);
		    CustomisedAssert.assertFalse(foundation.getText(VDIProvider.TXT_CLIENTURL).equals(data1));
		    foundation.click(VDIProvider.TXT_URL);
		    textBox.enterText(VDIProvider.TXT_URL, url);
		    foundation.click(VDIProvider.TXT_USERNAME);
		    textBox.enterText(VDIProvider.TXT_USERNAME, username);
		    foundation.click(VDIProvider.TXT_PASSWORD);
		    textBox.enterText(VDIProvider.TXT_PASSWORD, password);
		    foundation.click(VDIProvider.BTN_GENERATE);
		    foundation.threadWait(Constants.SHORT_TIME);
		    CustomisedAssert.assertTrue(foundation.getAttributeValue(VDIProvider.TXT_CLIENTUSERNAME).length()>=32);
		    CustomisedAssert.assertTrue(foundation.getAttributeValue(VDIProvider.TXT_CLIENTPASSWORD).length()>=48);
		    CustomisedAssert.assertTrue(foundation.getAttributeValue(VDIProvider.TXT_CLIENTURL).equals(data1));
		    foundation.click(checkBoxClick(data));
		    foundation.waitforElementToBeVisible(VDIProvider.BTN_SAVE, 5);
		    foundation.scrollIntoViewElement(VDIProvider.BTN_SAVE);
		    foundation.click(VDIProvider.BTN_SAVE);
		    foundation.waitforElementToBeVisible(VDIProvider.MSG_CREATED, 5);
		    CustomisedAssert.assertTrue(foundation.isDisplayed(VDIProvider.MSG_CREATED));}
}
