package at.smartshop.pages;

import org.openqa.selenium.By;

import at.framework.browser.Factory;
import at.framework.generic.CustomisedAssert;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.keys.Constants;

public class DeviceSummary extends Factory {
	
	private TextBox textBox = new TextBox();
	private LocationList locationList = new LocationList();
	private Foundation foundation = new Foundation();
	private LocationSummary locationSummary = new LocationSummary();
	private NavigationBar navigationBar = new NavigationBar();
	
	public static final By LBL_DEVICE_SUMMARY = By.xpath("//li[@id='Device Summary']");
	public static final By TXT_SCREEN_TIMEOUT = By.id("screentimeout");
	public static final By BTN_SAVE = By.id("saveBtn");
	public static final By LBL_TIMEOUT_WARNING = By.id("v5TimeoutWarning");
	public static final By DPD_SHOW_SEARCH_BTN = By.id("showprdlupdevice");
	public static final By LBL_SERIAL_NUMBER = By.id("serialnumber");
	public static final By DPD_COOLER_TYPE = By.id("coolertype");
	public static final By MM_RELOAD_METHOD=By.xpath("//dt[text()='MM Reload Method']");
	public static final By CHECKBOX_EGIFT_CARD=By.id("cbx.mmreloadmethod.gc");
	public static final By DPD_SELECT_PAYMENT=By.xpath("//select[@id='processor2']");
	public static final By FREEDOMPAY_CONFIG = By.id("freedompay");
	public static final By TXT_CLIENTID = By.id("freedompaystandardclientid");
	public static final By TXT_STOREID = By.id("freedompaystandardstoreid");
	public static final By TXT_PAY_LABLE = By.id("premiumpaymentlabel-freedompay");
	public static final By TXT_PAYID = By.id("terminalid2");
	public static final By DPD_MSR = By.id("encryptmsr");
	public static final By DPD_MSR_CHECK=By.xpath("//select[@id='encryptmsr']//option[@value='nomsr']");
	public static final By DPD_GMR = By.id("gmarateusedevice");
	public static final By DPD_NANOGMR = By.id("nanogmarateusedevice");
	public static final By DPD_CREDIT = By.id("creditrateusedevice");
	public static final By DPD_NANOCREDIT = By.id("nanocreditrateusedevice");
	public static final By TXT_GMR=By.xpath("//select[@id='gmarateusedevice']/option[@selected='selected']");
	public static final By TXT_NANOGMR=By.xpath("//select[@id='nanogmarateusedevice']/option[@selected='selected']");
	public static final By TXT_CREDIT=By.xpath("//select[@id='creditrateusedevice']/option[@selected='selected']");
	public static final By TXT_NANOCREDIT=By.xpath("//select[@id='nanocreditrateusedevice']/option[@selected='selected']");
	
	
	
	public By objSFEOptions(String text) {
		return By.xpath("//dt[text()='"+text+"']/following-sibling::dd[1]");
	}
	
	public void setTime(String locationName, String deviceName, String time, String menu) {
		
		 locationList.selectLocationName(locationName);
		 foundation.objectFocus(LocationSummary.BTN_DEPLOY_DEVICE);
		 textBox.enterText(LocationSummary.TXT_DEVICE_SEARCH, deviceName);
		 locationSummary.selectDeviceName(deviceName);
		 foundation.waitforElement(LBL_DEVICE_SUMMARY, Constants.SHORT_TIME);
		 getDriver().findElement(TXT_SCREEN_TIMEOUT).clear();
		 textBox.enterText(TXT_SCREEN_TIMEOUT, time);
		 foundation.click(BTN_SAVE);
		 navigationBar.navigateToMenuItem(menu);
		 locationList.selectLocationName(locationName);
		 foundation.waitforElement(LocationSummary.BTN_SAVE, Constants.SHORT_TIME);
		 foundation.click(LocationSummary.BTN_SYNC);
		 foundation.click(LocationSummary.BTN_SAVE);
		 foundation.waitforElement(LocationList.TXT_FILTER, Constants.SHORT_TIME);
	}
	
	public void freedomPayConfig(String data) {
		foundation.waitforElementToBeVisible(DeviceSummary.FREEDOMPAY_CONFIG,3);
		CustomisedAssert.assertTrue(foundation.isDisplayed(DeviceSummary.FREEDOMPAY_CONFIG));
		foundation.click(DeviceSummary.TXT_CLIENTID);
		textBox.enterText(DeviceSummary.TXT_CLIENTID, data);
		foundation.click(DeviceSummary.TXT_STOREID);
		textBox.enterText(DeviceSummary.TXT_STOREID, data);
		foundation.click(DeviceSummary.TXT_PAY_LABLE);
		textBox.enterText(DeviceSummary.TXT_PAY_LABLE, data);
	}
	
	/**
	 * verify the new Fields
	 * @param data
	 */
	public void verifyNewFields(String data) {
	CustomisedAssert.assertTrue(foundation.isDisplayed(DeviceSummary.LBL_DEVICE_SUMMARY));
	foundation.scrollIntoViewElement(DeviceSummary.DPD_GMR);
	CustomisedAssert.assertTrue(foundation.isDisplayed(DeviceSummary.DPD_GMR));
	CustomisedAssert.assertTrue(foundation.getText(DeviceSummary.TXT_GMR).equals(data));
	CustomisedAssert.assertTrue(foundation.isDisplayed(DeviceSummary.DPD_NANOGMR));
	CustomisedAssert.assertTrue(foundation.getText(DeviceSummary.TXT_NANOGMR).equals(data));
	CustomisedAssert.assertTrue(foundation.isDisplayed(DeviceSummary.DPD_CREDIT));
	CustomisedAssert.assertTrue(foundation.getText(DeviceSummary.TXT_CREDIT).equals(data));
	CustomisedAssert.assertTrue(foundation.isDisplayed(DeviceSummary.DPD_NANOCREDIT));
	CustomisedAssert.assertTrue(foundation.getText(DeviceSummary.TXT_NANOCREDIT).equals(data));
}
	
}


