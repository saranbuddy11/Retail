package at.smartshop.pages;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;

import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.database.columns.CNLocation;
import at.smartshop.keys.Constants;

public class CreatePromotions {

	private Foundation foundation = new Foundation();
	private Dropdown dropDown = new Dropdown();
	private TextBox textBox = new TextBox();

	public static final By LBL_BASICINFO = By.xpath("//div[@id='section1']//h4");
	public static final By LBL_ENTER_BASICINFO = By.xpath("//div[@id='section1']//i");
	public static final By LBL_FILTER = By.xpath("//div[@id='multiple-filters']//h4");
	public static final By LBL_SELECT_CRITERIA = By.xpath("//div[@id='multiple-filters']//i");
	public static final By LBL_DETAILS = By.xpath("//div[@id='section3']//h4");
	public static final By LBL_SET_PROMO_DETAILS = By.xpath("//div[@id='section3']//i");
	public static final By BTN_CANCEL = By.id("cancelBtn");	
	public static final By DPD_PROMO_TYPE = By.id("promotype");
	public static final By TXT_PROMO_NAME = By.id("name");
	public static final By TXT_DISPLAY_NAME = By.id("displayname");
	public static final By BTN_NEXT = By.xpath("//button[@id='submitBtn']");
	public static final By DPD_LOCATION = By.id("location-select");
	public static final By DPD_ORG = By.xpath("//input[@placeholder='Select Org(s) to include']");
	public static final By DPD_LOC = By.xpath("//input[@placeholder='Select Location(s) to include']");
	public static final By LBL_CREATE_PROMOTION = By.xpath("//li[text()='Create Promotion']");
	public static final By BTN_END_PROMO = By.id("disablepromotion");
	public static final By BTN_EXPIRE = By.xpath("//button[@class='ajs-button ajs-ok']");
	public static final By MULTI_SELECT_TENDER_TYPES = By.id("tendertypes");
	public static final By BTN_OK = By.xpath("//button[text()='OK']");
	public static final By LBL_PAGE_TITLE = By.id("pagesubtitle");
	public static final By DPD_DISCOUNT_TIME = By.id("timing");
	public static final By DPD_DISCOUNT_TYPE = By.id("discounttype");
	public static final By BTN_CREATE = By.id("submitBtnContainer");
	public static final By TXT_AMOUNT = By.id("amount");
	public static final By TXT_START_DATE = By.id("startdate");
	public static final By TXT_END_DATE = By.id("enddate");
	public static final By TXT_TIME_START = By.id("timestart");
	public static final By TXT_TIME_END = By.id("timeend");
	public static final By POP_UP_MESSAGES = By.xpath("//div[@class='ajs-dialog']//b");
	public static final By LBL_POPUP_FIELD = By.xpath("//div[@class='alert-details']//b");
	public static final By LBL_POPUP_VALUES = By.xpath("//div[@class='alert-details']");
	public static final By DPD_APPLY_DISCOUNT_TO = By.id("appliesto");
	public static final By TXT_TRANSACTION_MIN = By.id("transmin");
	public static final By CHK_SUNDAY = By.xpath("//div[@id='recurringInput']//dd/input[@id='sunday']");
	public static final By DPD_ITEM_SELECT = By.id("itemSelectInput");
	public static final By TXT_DISCOUNT_PERCENTAGE = By.id("percentage");
	public static final By DPD_DISCOUNT_BY = By.id("discountBy");
	public static final By TXT_ITEMS = By.xpath("//*[@id='itemSelectInput']//..//input");
	public static final By TXT_CATEGORYS = By.xpath("//*[@id='categorySelectInput']//..//input");
	public static final By TXT_PER_TRANSACTION_LIMIT = By.id("promolimit");
	public static final By CHK_PROMO_RESTRICTION = By.id("haspromolimit");
	public static final By DPD_DURATION = By.id("duration");
	public static final By DPD_CATEGORY = By.id("categorySelectInput");
	public static final By SEARCH_CATEGORY = By.xpath("//input[@placeholder='Search for a Category']");
	public static final By TXT_SEARCH = By.xpath("//input[@class='select2-search__field valid']");
	public static final By LBL_BASIC_INFORMATION = By.xpath("//h4[text()='Basic Information']");
	public static final By BTN_CONTINUE = By.xpath("//button[text()='Continue']");
	public static final By DPD_ORGANIZATION = By.id("org-select");
	public static final By LINK_LOCATION_LIST = By.xpath("//td[@aria-describedby='dataGrid_table_namelink']//a");
	public static final By TXT_ITEM = By.xpath("//input[@placeholder='Search for an Item']");
	public static final By DPD_LOCATION_LIST = By.xpath("//ul[@id='select2-location-select-results']//li");
	public static final By TXT_QUANTITY=By.id("bundleItem0");
	public static final By TXT_BUNDLE_PRICE =By.id("bundleprice");
	public static final By LBL_TOTAL_PRICE =By.id("totalprice");
	public static final By LBL_BUNDLE_DISCOUNT = By.xpath("//*[@id='bundlesummary']/b/span");
	public static final By TXT_ITEM1 = By.xpath("//*[@id='itemSelect']//li/input");
	public static final By CHK_NO_END_DATE =By.id("hasnoenddate");
	
	public By objLocation(String value) {
		return By.xpath("//li[contains(text(),'" + value + "')]");
	}

	public void newPromotion(String promoType, String promoName, String displayName, String orgName,String locationName) {
		dropDown.selectItem(DPD_PROMO_TYPE, promoType, Constants.TEXT);
		textBox.enterText(TXT_PROMO_NAME, promoName);
		if (foundation.isDisplayed(TXT_DISPLAY_NAME))
			textBox.enterText(TXT_DISPLAY_NAME, displayName);
		foundation.click(BTN_NEXT);
		textBox.enterText(DPD_ORG, orgName);
		textBox.enterText(DPD_ORG, Keys.ENTER);
		dropDown.selectItem(DPD_LOCATION, locationName, Constants.TEXT);
		foundation.threadWait(Constants.TWO_SECOND);
	}

	public List<String> getPopUpData() {

		List<String> popupFieldValues = foundation.getTextofListElement(LBL_POPUP_VALUES);
		List<String> popupFieldArray = new ArrayList<String>();
		List<String> promoValues;
		for (int i = 0; i < popupFieldValues.size(); i++) {
			promoValues = Arrays.asList(popupFieldValues.get(i).split(Constants.NEW_LINE));
			for (int j = 0; j < promoValues.size(); j++) {
				popupFieldArray.add(promoValues.get(j));
			}
		}
		return popupFieldArray;
	}

	public By objFieldSet(String filedSetText) {
		return By.xpath("//fieldset[@id='fieldset']//*[text()='" + filedSetText + "']");
	}
	
	public By filterOptions(String fieldName) {
		return By.xpath("//dt[text()='" + fieldName + "']");
	}

	public void verifyOrgField(List<String> orgs) {
		List<String> orgData = dropDown.getAllItems(CreatePromotions.DPD_ORGANIZATION);
		for (int iter = 0; iter < orgData.size(); iter++) {
			Assert.assertTrue(orgData.get(iter).contains(orgs.get(iter)));
		}
	}
	
	public void BundlePromotion(String promotionType, String promotionName, String displayName, String orgName,String locationName) {
		try {
			foundation.click(PromotionList.BTN_CREATE);
			foundation.isDisplayed(LBL_CREATE_PROMOTION);
			newPromotion(promotionType, promotionName, displayName, orgName, locationName);
			foundation.waitforElement(BTN_NEXT, Constants.SHORT_TIME);
			foundation.click(BTN_NEXT);
			foundation.waitforElement(DPD_DISCOUNT_BY, Constants.SHORT_TIME);
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}		
			
	public void selectBundlePromotionDetails(String discountBy, String item, String transactionMin) {
		try {
			dropDown.selectItem(DPD_DISCOUNT_BY, discountBy, Constants.TEXT);
			textBox.enterText(TXT_ITEM, item);
			foundation.threadWait(Constants.ONE_SECOND);
			textBox.enterText(TXT_ITEM, Keys.ENTER);
			foundation.threadWait(Constants.TWO_SECOND);
			String actualValue = dropDown.getSelectedItem(DPD_ITEM_SELECT);
			assertEquals(actualValue, item);
			textBox.enterText(TXT_TRANSACTION_MIN, transactionMin);
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
	
	public void selectBundlePromotionPricing(String bundlePrice) {
		try {
			textBox.enterText(TXT_BUNDLE_PRICE, bundlePrice);	
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
	public void selectBundlePromotionTimes(String discountTime,String discountDuration) {
		try {
			dropDown.selectItem(DPD_DISCOUNT_TIME, discountTime, Constants.TEXT);
			if (!Constants.DELIMITER_SPACE.equals(discountDuration))
				dropDown.selectItem(DPD_DURATION, discountDuration, Constants.TEXT);	
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
	
	public void recurringDay() {
		try {
			Calendar calendar = Calendar.getInstance();
			int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
			foundation.click(By.xpath("//div[@id='recurringInput']//input["+dayOfWeek+"]"));
			
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

}
