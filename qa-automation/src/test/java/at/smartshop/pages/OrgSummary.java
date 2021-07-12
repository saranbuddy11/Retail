package at.smartshop.pages;

import org.openqa.selenium.By;
import org.testng.Assert;

import com.aventstack.extentreports.Status;

import at.framework.browser.Factory;
import at.framework.reportsetup.ExtFactory;
import at.smartshop.keys.Constants;

public class OrgSummary  extends Factory{
	public static final By DPD_VDI_PROVDIER=By.xpath("//select [@id='vdiprovider-added']");
	public static final By CHK_VDI =By.xpath("//input[@id='vdicbx']");
	public static final By BTN_VDI_PLUS =By.xpath("//button[@id='vdi-plus-btn']");
	public static final By BTN_VDI_DEL =By.xpath("//button[@onclick='vdiDelBtnClick(this)']");
	public static final By TXT_USER_KEY =By.xpath("//input[@placeholder='- Enter User Key -']");
	public static final By BTN_SAVE =By.xpath("//button[text()='Save']");
	public static final By BTN_YES =By.xpath("//button[text()='Yes']");
	public static final By BTN_NO =By.xpath("//button[text()='No ']");
	public static final By LBL_USER_KEY =By.xpath("//input[@id='vdiuserkey-added']");
	public static final By LBL_SPINNER_MSG =By.xpath("//div[@class='ajs-message ajs-success ajs-visible']");
	public static final By LBL_POPUP_HEADER =By.xpath("//div[@class='ajs-header']");
	public static final By LBL_POPUP_MSG =By.xpath("//div[@class='ajs-content']");
	public static final By LBL_VDI_PROVIDER =By.xpath("//tr[@class='vdirow']//td//input");
	
	
	public String getAttributeValue(By object) {
		String textAttribute = null;
		try {
			textAttribute = getDriver().findElement(object).getAttribute(Constants.ATTRIBUTE);
			if (ExtFactory.getInstance().getExtent() != null) {
				ExtFactory.getInstance().getExtent().log(Status.INFO, object + " value is "+ textAttribute);
			}
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
		return textAttribute;
	}
}
