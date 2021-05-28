package at.smartshop.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import at.framework.browser.Factory;
import at.framework.files.PropertyFile;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.Table;
import at.framework.ui.TextBox;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;

public class CreatePromotions {

	private Foundation foundation = new Foundation();
	private Dropdown dropdown = new Dropdown();
	private TextBox textbox = new TextBox();
	private Table table=new Table(); 
	private PropertyFile propertyFile = new PropertyFile();

	public static final By DPD_PROMO_TYPE = By.id("promotype");
	public static final By TXT_PROMO_NAME = By.id("name");
	private static final By TXT_DISPLAY_NAME = By.id("displayname");
	public static final By BTN_NEXT = By.id("submitBtn");

	public static final By DPD_LOCATION = By.id("location-select");
	public static final By DPD_ORG = By.xpath("//input[@placeholder='Select Org(s) to include']");
	private static final By DPD_LOC = By.xpath("//input[@placeholder='Select Location(s) to include']");
	
    public static final By SEARCH_ITEM = By.xpath("//input[@placeholder='Search for an Item']");
    public static final By BTN_OK = By.xpath("//button[text()='OK']");
    public static final By TXT_SELECTION=By.xpath("(//li[@class='select2-selection__choice'])[2]");
    public static final By BTN_EXPIRE =By.xpath("//button[@class='ajs-button ajs-ok']");
    public static final By BTN_END_PROMO =By.id("disablepromotion");
    public static final By MULTI_SELECT_TENDER_TYPES =By.id("tendertypes");
    public static final By DPD_DISCOUNT_TYPE =By.id("discounttype");
    public static final By BTN_CREATE = By.id("submitBtnContainer");
    public static final By TXT_AMOUNT = By.id("amount");
    
    public static final By LBL_BASICINFO = By.xpath("//div[@id='section1']//h4");
    public static final By LBL_ENTER_BASICINFO = By.xpath("//div[@id='section1']//i");
    public static final By LBL_FILTER = By.xpath("//div[@id='multiple-filters']//h4");
    public static final By LBL_SELECT_CRITERIA = By.xpath("//div[@id='multiple-filters']//i");
    public static final By LBL_DETAILS = By.xpath("//div[@id='section3']//h4");
    public static final By LBL_SET_PROMO_DETAILS = By.xpath("//div[@id='section3']//i");
    public static final By BTN_CANCEL = By.id("cancelBtn");
    
    public static final By DPD_APPLY_DISCOUNT_TO =By.id("appliesto");
    public static final By TXT_TRANSACTION_MIN =By.id("transmin");
    public static final By DPD_DISCOUNT_TIME =By.id("timing");
    public static final By TXT_START_DATE =By.id("startdate");
    public static final By TXT_END_DATE =By.id("enddate");
    public static final By TXT_TIME_START =By.id("timestart");
    public static final By TXT_TIME_END =By.id("timeend");

    
   

	public void newPromotion(String PromoType, String PromoName, String DisplayName, String locationName) {
		dropdown.selectItem(DPD_PROMO_TYPE, PromoType, Constants.TEXT);
		textbox.enterText(TXT_PROMO_NAME, PromoName);
		textbox.enterText(TXT_DISPLAY_NAME, DisplayName);
		foundation.click(BTN_NEXT);
		textbox.enterText(DPD_ORG,
				propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
		textbox.enterText(DPD_ORG, Keys.ENTER);
		dropdown.selectItem(DPD_LOCATION, locationName, Constants.TEXT);
	}
	
	public void expirePromotion(String dataGridname,String promoName){
        foundation.waitforElement(PromotionList.TXT_SEARCH, 2000);
        textbox.enterText(PromotionList.TXT_SEARCH, promoName);
        foundation.click(PromotionList.BTN_SEARCH);
        
        table.selectRow(dataGridname,promoName);       
        foundation.doubleClick(By.xpath("//td[@aria-describedby='" + dataGridname + "'][text()='" + promoName + "']"));
        foundation.waitforElement(BTN_END_PROMO, 2000);
        foundation.click(BTN_END_PROMO);
        foundation.click(BTN_EXPIRE);
}

}
