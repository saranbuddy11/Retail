package at.smartshop.pages;


import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import at.framework.browser.Factory;
import junit.framework.Assert;

public class OrgSummary extends Factory {
	public static final By DPD_VDI_PROVDIER = By.xpath("//select [@id='vdiprovider-added']");
	public static final By CHK_VDI = By.xpath("//input[@id='vdicbx']");
	public static final By BTN_VDI_PLUS = By.xpath("//button[@id='vdi-plus-btn']");
	public static final By BTN_VDI_DEL = By.xpath("//button[@onclick='vdiDelBtnClick(this)']");
	public static final By TXT_USER_KEY = By.xpath("//input[@id='vdiuserkey-added']");
	public static final By BTN_SAVE = By.xpath("//button[text()='Save']");
	public static final By BTN_YES = By.xpath("//button[text()='Yes']");
	public static final By BTN_NO = By.xpath("//button[text()='No ']");
	public static final By LBL_USER_KEY = By.xpath("//input[@id='vdiuserkey-added']");
	public static final By LBL_SPINNER_MSG = By.xpath("//div[@class='ajs-message ajs-success ajs-visible']");
	public static final By LBL_POPUP_HEADER = By.xpath("//div[@class='ajs-header']");
	public static final By LBL_POPUP_MSG = By.xpath("//div[@class='ajs-content']");
	public static final By LBL_VDI_PROVIDER = By.xpath("//tr[@class='vdirow']//td//input");
	public static final By LBL_ORG_LIST = By.xpath("//div[@class='dataTables_info']");
	public static final By TXT_SPINNER_MSG = By.xpath("//div[@class='humane ']");
	public static final By DPD_COUNTRY = By.id("country");
	public static final By DPD_TAX_SYSTEM = By.id("taxsystem");
	public static final By DPD_CURRENCY = By.cssSelector("select#currency");
	public static final By DPD_CROSS_ORG_ACCOUNT = By.id(" coa");
	public static final By TXT_DISPLAYNAME = By.id("displayname");
	public static final By TXT_ADDRESS = By.xpath("//input[@id='address']");
	public static final By TXT_ZIP = By.xpath("//input[@id='zip']");
	public static final By TXT_CITY = By.xpath("//input[@id='city']");
	public static final By DPD_STATE = By.xpath("//select[@id='state']");
	public static final By DPD_USG_DATA = By.xpath("//select[@id='usg_datafeed']");
	public static final By TXT_USG_ID = By.xpath("//input[@id='usgfeedid']");
	public static final By TXT_ERROR_MSG = By.xpath("//*[text()='Error 500: Internal Server Error']");
	public static final By BTN_REMOVE = By.xpath("//button[text()='Remove']");
	public static final By LBL_UPLOAD = By.xpath("//input[@id='fileUpload']");

	public By objVDI(String text) {

		return By.xpath("//input[@value='" + text + "']");
	}

	public void verifyDPDValue(String text) {
		Boolean flag = true;
		WebElement drpdwn = getDriver().findElement(DPD_VDI_PROVDIER);
		Select dpdSel = new Select(drpdwn);
		List<WebElement> DrpDwnList = dpdSel.getOptions();
		for (WebElement webElement : DrpDwnList) {
			
			if (webElement.getText().contains(text)) {
				flag = false;
				break;
			}
		}
		Assert.assertTrue(flag);

	}



}
