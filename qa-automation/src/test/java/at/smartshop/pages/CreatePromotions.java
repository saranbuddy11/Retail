package at.smartshop.pages;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;

import at.framework.generic.DateAndTime;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.keys.Constants;

public class CreatePromotions {

	private Foundation foundation = new Foundation();
	private Dropdown dropDown = new Dropdown();
	private TextBox textBox = new TextBox();
	private DateAndTime dateAndTime = new DateAndTime();

	public static final By TXT_SELECTION = By.xpath("(//li[@class='select2-selection__choice'])[2]");
	public static final By LBL_BASICINFO = By.xpath("//div[@id='section1']//h4");
	public static final By LBL_ENTER_BASICINFO = By.xpath("//div[@id='section1']//i");
	public static final By LBL_FILTER = By.xpath("//div[@id='multiple-filters']//h4");
	public static final By LBL_SELECT_CRITERIA = By.xpath("//div[@id='multiple-filters']//i");
	public static final By LBL_DETAILS = By.xpath("//div[@id='section3']//h4");
	public static final By LBL_SET_PROMO_DETAILS = By.xpath("//div[@id='section3']//i");
	public static final By BTN_CANCEL = By.id("cancelBtn");
	public static final By TXT_LOCATION = By.xpath("//input[@placeholder='Select Location(s) to include']");
	public static final By DPD_PROMO_TYPE = By.id("promotype");
	public static final By TXT_PROMO_NAME = By.id("name");
	public static final By TXT_DISPLAY_NAME = By.id("displayname");
	public static final By BTN_NEXT = By.id("submitBtn");
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
	public static final By SEARCH_ITEM = By.xpath("//input[@placeholder='Search for an Item']");
	public static final By DPD_CATEGORY = By.id("categorySelectInput");
	public static final By SEARCH_CATEGORY = By.xpath("//input[@placeholder='Search for a Category']");
	public static final By TXT_SEARCH = By.xpath("//input[@class='select2-search__field valid']");
	public static final By LBL_BASIC_INFORMATION = By.xpath("//h4[text()='Basic Information']");
	public static final By DPD_ITEM = By.xpath("//input[@placeholder='Search for an Item']");
	public static final By BTN_CONTINUE = By.xpath("//button[text()='Continue']");
	public static final By DPD_ORGANIZATION = By.id("org-select");
	public static final By LINK_LOCATION_LIST = By.xpath("//td[@aria-describedby='dataGrid_table_namelink']//a");
	public static final By TXT_ITEM = By.xpath("//input[@placeholder='Search for an Item']");


	public By objLocation(String value) {
		return By.xpath("//li[contains(text(),'" + value + "')]");
	}

	public void newPromotion(String promoType, String promoName, String displayName, String orgName,
			String locationName) {
		dropDown.selectItem(DPD_PROMO_TYPE, promoType, Constants.TEXT);
		textBox.enterText(TXT_PROMO_NAME, promoName);
		if (foundation.isDisplayed(TXT_DISPLAY_NAME))
			textBox.enterText(TXT_DISPLAY_NAME, displayName);
		foundation.click(BTN_NEXT);
		textBox.enterText(DPD_ORG, orgName);
		textBox.enterText(DPD_ORG, Keys.ENTER);
		dropDown.selectItem(DPD_LOCATION, locationName, Constants.TEXT);

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

	public void verifyPromotionPopupDetails(List<String> actualData, String promotionType, String promotionName) {
		
		List<String> popupFieldType = foundation.getTextofListElement(CreatePromotions.POP_UP_MESSAGES);
		List<String> popupField = null;
		popupField = Arrays.asList(popupFieldType.get(0).split(Constants.DELIMITER_COMMA));
		popupField = Arrays.asList(popupField.get(0).split(Constants.NEW_LINE));
		assertEquals(popupField.get(0), actualData.get(0));
		assertEquals(popupField.get(1), actualData.get(1));
		List<String> popupFieldArray = getPopUpData();
		String currentDate = dateAndTime.getDateAndTime(Constants.REGEX_MMDDYY, Constants.TIME_ZONE_INDIA);
		assertTrue(popupFieldArray.get(0).contains(promotionType));
		assertTrue(popupFieldArray.get(1).contains(promotionName));
		if (promotionType.equalsIgnoreCase(Constants.PROMOTION_TYPE_TENDER_DISCOUNT)) {
			assertEquals(popupFieldArray.get(2), actualData.get(2));
			assertEquals(popupFieldArray.get(3), actualData.get(3));
			assertEquals(popupFieldArray.get(4), actualData.get(4));
			assertEquals(popupFieldArray.get(5), actualData.get(5));
			assertEquals(popupFieldArray.get(6), actualData.get(6));
			assertTrue(popupFieldArray.get(7).contains(currentDate));
			assertTrue(popupFieldArray.get(8).contains(currentDate));
		} else if (promotionType.equalsIgnoreCase(Constants.PROMOTION_TYPE_ON_SCREEN)) {
			assertTrue(popupFieldArray.get(2).contains(promotionName));
			assertEquals(popupFieldArray.get(3), actualData.get(2));
			assertEquals(popupFieldArray.get(4), actualData.get(3));
			assertEquals(popupFieldArray.get(5), actualData.get(4));
			assertEquals(popupFieldArray.get(6), actualData.get(5));
			assertTrue(popupFieldArray.get(7).contains(currentDate));
			assertTrue(popupFieldArray.get(8).contains(currentDate));
		}
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
}
