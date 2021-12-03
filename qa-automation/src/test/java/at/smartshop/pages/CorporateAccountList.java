package at.smartshop.pages;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.By;

import at.framework.browser.Factory;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;

public class CorporateAccountList extends Factory {
	
	private Foundation foundation = new Foundation();
	private TextBox textBox = new TextBox();
	
	public static final By BTN_CREATE = By.id("createNewBtn");
	public static final By CORPORATE_NAME = By.id("name");
	public static final By CORPORATE_ADDRESS =  By.id("address");
	public static final By CORPORATE_ZIP =  By.id("zip");
	public static final By CORPORATE_CITY =  By.id("city");
	public static final By CORPORATE_STATE =  By.id("state");
	public static final By CORPORATE_CONTACT =  By.id("contact");
	public static final By CORPORATE_PHONENUMBER =  By.id("contactphone");
	public static final By CORPORATE_CONTACTEMAIL =  By.id("contactemail");
	public static final By CORPORATE_NOTES =  By.id("notes");
	public static final By FINANCIAL_NAME =  By.id("crpoperatorname");
	public static final By DISBURSEMENT_EMAIL =  By.id("crpdisbursementemail");
	public static final By FINANCIAL_EMAIL =  By.id("crpdisbursementemailcc");
	public static final By SAVE_BTN =  By.id("saveBtn");
	public static final By CANCEL_BTN =  By.id("cancelBtn");
	public static final By NAME_ERROR =  By.xpath("//label[@id='name-error']");
	public static final By CRPOPERATOR_ERROR =   By.xpath("//label[@id='crpoperatorname-error']");
	public static final By CRPDISBURSEMENT_ERROR =   By.xpath("//label[@id='crpdisbursementemail-error']");
	public static final By SEARCH_FILTER =   By.xpath("//input[@id='filterType']");
	public static final By TBL_DATA =   By.xpath("//td[@aria-describedby='dataGrid_name']");
	public static final By ZIP_ERROR =   By.id("zip-error");
	public static final By PHONE_ERROR =  By.id("contactphone-error");
	public static final By EMAIL_ERROR =   By.id("contactemail-error");	
	public static final By DISBURSEMENT_DAY =   By.xpath("//input[@id='cbx.crpdisbursementdays.0']");	
	public static final By DELETE_ACCOUNT =   By.xpath("//a[@class='fa fa-trash icon']");
	public static final By CONFIRM_DELETE=   By.xpath("//button[@class='ajs-button ajs-ok']");
	public static final By VALIDATE_MESSAGE =  By.xpath("//div[@id='checkBoxAlert']");
	public static final By NO_RECORD =  By.xpath("//span[@id='dataGrid_pager_label']");
	public static final By DISABLED_CHECKBOX =  By.id("cbx.crpdisbursementdays.1");
	public static final By VALIDATE_TITLE =  By.id("//div[@id='Corporate List']");
	public static final By DISBURSEMENT_ERROR_EMAIL = By.id("crpdisbursementemail");
	
	public void acceptPopup(String device) {
		foundation.waitforElement(OrgstrList.ORG_LIST, Constants.SHORT_TIME);
		textBox.enterText(OrgstrList.ORG_DEVICE_SEARCH, device);
		foundation.click(OrgstrList.TBL_DATA);
		foundation.waitforElement(OrgstrList.BTN_REMOVE, Constants.SHORT_TIME);
		foundation.click(OrgstrList.BTN_REMOVE);
		foundation.alertAccept();
		
	}
	

}