package at.smartshop.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import at.framework.browser.Factory;
import at.framework.generic.CustomisedAssert;
import at.framework.ui.CheckBox;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.smartshop.keys.Constants;

public class OrgSummary extends Factory {
	private Foundation foundation = new Foundation();
	private CheckBox checkBox = new CheckBox();

	private NavigationBar navigationBar = new NavigationBar();

	private Dropdown dropDown = new Dropdown();

	public static final By DPD_VDI_PROVDIER = By.xpath("//select [@id='vdiprovider-added']");
	public static final By POPUP_LBL_HEADER = By.id("ageverificationpopup");
	public static final By POPUP_BTN_YES = By.id("ageverificationpopupSaveBtn");
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
	public static final By DPD_COUNTRY = By.xpath("//select[@name='country']");
	public static final By DPD_TAX_SYSTEM = By.id("taxsystem");
	public static final By DPD_CURRENCY = By.cssSelector("select#currency");
	public static final By DPD_CROSS_ORG_ACCOUNT = By.id(" coa");
	public static final By DPD_TAX_METHOD = By.xpath("//select[@id='taxmethod']");
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
	public static final By CHK_SHOW_HISTORY = By.xpath("//input[@id='invappshowhistory']");
	public static final By CHK_SHOW_CHECKLIST_COUNT = By.xpath("//input[@id='invappshowchecklistcount']");
	public static final By CHK_SHOW_CHECKLIST_REVIEW = By.xpath("//input[@id='invappshowchecklistcountreview']");
	public static final By TXT_GMA_RATE = By.xpath("//input[@id='gmaratepercent']");
	public static final By TXT_CREDIT_RATE = By.xpath("//input[@id='creditratepercent']");
	public static final By TXT_NANO_GMA_RATE = By.xpath("//input[@id='nanogmaratepercent']");
	public static final By TXT_NANO_CREDIT_RATE = By.xpath("//input[@id='nanocreditratepercent']");
	public static final By CHK_REPORT_VIEW = By.xpath("//input[@id='Checkbox1']");
	public static final By DPD_POLICIES = By.xpath("//select[@id='hasgdpr']");
	public static final By TXT_OPERATOR = By.xpath("//input[@id='operatorname']");
	public static final By CHK_DISBURSEMENT = By.xpath("//input[@id='cbx.disbursementdays.6']");
	public static final By TXT_DISBURSEMENT_EMAIL = By.xpath("//input[@id='disbursementemail']");
	public static final By TXT_DISBURSEMENT_EMAIL_CC = By.xpath("//input[@id='disbursementemailcc']");
	public static final By CHK_FREEZER = By.xpath("//div[text()='Freezer']");
	public static final By CHK_COOLER = By.xpath("//div[text()='Cooler']");
	public static final By LBL_SENSOR_TYPE = By
			.xpath("//div[@class='ui-igcombo-buttonicon ui-icon-triangle-1-s ui-icon']");
	public static final By LBL_COOLER = By.xpath("//div[@id='cooler']//p[@class='green-highlighter']");
	public static final By LBL_FREEZER = By.xpath("//div[@id='freezer']//p[@class='green-highlighter']");
	public static final By DPD_PROCESSORACCOUNT = By.cssSelector("select#processoraccount");
	public static final By TXT_TAXID = By.cssSelector("#operatortaxid");
	public static final By DPD_GMA_ACCOUNT = By.cssSelector("select#gma");
	public static final By DPD_SPECIAL_TYPE = By.cssSelector("select#specialtype");
	public static final By DPD_SUB_TYPE = By.cssSelector("select#subtype");
	public static final By TXT_CONTACT = By.cssSelector("input#contact");
	public static final By TXT_CONTACT_EMAIL = By.cssSelector("input#contactemail");
	public static final By TXT_CONTACT_PHONE = By.cssSelector("input#contactphone");
	public static final By TXT_SAGE_1 = By.cssSelector("input#sagenumber1");
	public static final By TXT_SAGE_2 = By.cssSelector("input#sagenumber2");
	public static final By TXT_RECEIPT_HEADER = By.cssSelector("textarea#printheader");
	public static final By TXT_RECEIPT_FOOTER = By.cssSelector("textarea#printfooter");
	public static final By TXT_MONTH_PF = By.cssSelector("input#monthlyprocessingfee");
	public static final By TXT_SUB_FEE = By.cssSelector("input#subscriptionfee");
	public static final By TXT_MAX_SUB_FEE = By.cssSelector("input#maxsubfee");
	public static final By TXT_MIN_SUB_FEE = By.cssSelector("input#minsubfee");
	public static final By TXT_DEVICE_COUNT_FEE = By.cssSelector("input#devicecountfee");
	public static final By DPD_SYSTEM = By.cssSelector("select#system");
	public static final By DPD_PAGESET = By.cssSelector("select#pageset");
	public static final By DPD_SPECIAL_PROCESSOR = By.cssSelector("select#specialprocessor");
	public static final By DPD_PROCESSOR_ACCOUNT = By.cssSelector("select#processoraccount");
	public static final By TXT_TAX_POS_PROVIDER = By.cssSelector("input#taxidposprovider");
	public static final By TXT_POS_PROVIDER_ADDRESS = By.cssSelector("input#posprovideraddress");
	public static final By TXT_POS_PROVIDER_CITY = By.cssSelector("input#posprovidercity");
	public static final By TXT_POS_STATE = By.cssSelector("input#posstate");
	public static final By TXT_POS_ZIP = By.cssSelector("input#poszip");
	public static final By TXT_POS_COUNTRY = By.cssSelector("input#poscountry");
	public static final By TXT_IMG_URL = By.cssSelector("input#imgsvrurl");
	public static final By TXT_CC_OFFLINE_THRESHOLD = By.cssSelector("input#ccofflinethreshold");
	public static final By TXT_CC_MAXVALUE = By.cssSelector("input#ccmaxvalue");
	public static final By TXT_MONTHLY_FEE = By.cssSelector("input#monthlyfee");
	public static final By TXT_LOCKERFLAT_FEE = By.cssSelector("input#lockerflatfee");
	public static final By TXT_LOCKERPERCENT_FEE = By.cssSelector("input#lockerpercentfee");
	public static final By TXT_LOCKER_FEE_CAP = By.cssSelector("input#lockerfeecap");
	public static final By TXT_LOCKER_MONTHLY_FEE = By.cssSelector("input#lockermonthlyfee");
	public static final By TXT_OPERATOR_TAX_ID = By.cssSelector("input#operatortaxid");
	public static final By TXT_CANTEENID = By.cssSelector("input#userkey");
	public static final By DPD_HAS_DIFF = By.cssSelector("select#hasdif");
	public static final By DPD_HAS_SOGO = By.cssSelector("select#hassogo");
	public static final By DPD_HAS_GUEST_PASS = By.cssSelector("select#hasguestpass");
	public static final By DPD_HAS_PROMOS = By.cssSelector("select#haspromos");
	public static final By DPD_HAS_DATAWARE_HOUSE = By.cssSelector("select#hasdatawarehouse");
	public static final By DPD_OFFLINECC_VALIDATED = By.cssSelector("select#offlineccvalidated");
	public static final By DPD_HAS_CONSUMER_ENGAGEMENT = By.cssSelector("select#hasconsumerengagement");
	public static final By DPD_HAS_SEND_SNACK = By.cssSelector("select#hassendsnack");
	public static final By DPD_HAS_LIGHTSPEED = By.cssSelector("select#haslightspeed");
	public static final By TXT_ABN = By.cssSelector("input#abn");
	public static final By TXT_NAME = By.id("name");
	public static final By BTN_CANCEL = By.xpath("//button[@id='cancelBtn']");
	public static final By LBL_ORG_SUMMARY = By.id("Org Summary");
	public static final By TXT_AGE_VERIFICATION = By.xpath("//dt[text()='Age Verification Enabled']");
	public static final By CHK_AGE_VERIFICATION = By.id("ageverification");

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
		CustomisedAssert.assertFalse(flag);
	}

	public void deleteVDIIsAlreadySelected(String VDIProvideName) {
		if (foundation.isDisplayed(objVDI(VDIProvideName))) {
			foundation.click(OrgSummary.BTN_VDI_DEL);
			foundation.waitforElement(OrgSummary.BTN_YES, Constants.SHORT_TIME);
			foundation.click(OrgSummary.BTN_YES);
			foundation.waitforElement(OrgSummary.LBL_SPINNER_MSG, Constants.SHORT_TIME);
			foundation.threadWait(Constants.TWO_SECOND);
			checkBox.check(OrgSummary.CHK_VDI);
			foundation.waitforElement(OrgSummary.DPD_VDI_PROVDIER, Constants.SHORT_TIME);
		}
	}

	/**
	 * select dropDown values from Org summary
	 * 
	 * @param object
	 * @param dropdown
	 */
	public void selectDropdownValues(By object, String dropdown) {
		CustomisedAssert.assertTrue(foundation.isDisplayed(OrgSummary.LBL_ORG_SUMMARY));
		foundation.waitforElementToBeVisible(object, 5);
		dropDown.selectItem(object, dropdown, Constants.TEXT);
		foundation.waitforElementToBeVisible(OrgSummary.BTN_SAVE, 3);
		foundation.click(OrgSummary.BTN_SAVE);
		CustomisedAssert.assertTrue(foundation.isDisplayed(OrgList.LBL_ORG_LIST));

	}

	/**
	 * Enable Tax2 column in super->org summary
	 * 
	 * @param location
	 * @param product
	 */
	public void enableTax2Column(String menu, String data) {

		navigationBar.navigateToMenuItem(menu);
		foundation.scrollIntoViewElement(OrgSummary.DPD_TAX_METHOD);
		dropDown.selectItem(OrgSummary.DPD_TAX_METHOD, data, Constants.TEXT);
		foundation.click(OrgSummary.BTN_SAVE);
		foundation.waitforElement(OrgSummary.TXT_SPINNER_MSG, Constants.SHORT_TIME);
	}

}
