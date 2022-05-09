package at.smartshop.pages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import com.aventstack.extentreports.Status;

import at.framework.browser.Factory;
import at.framework.generic.CustomisedAssert;
import at.framework.reportsetup.ExtFactory;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.database.columns.CNLocation;
import at.smartshop.keys.Constants;
import at.smartshop.tests.TestInfra;

public class CreatePromotions extends Factory {

	private Foundation foundation = new Foundation();
	private Dropdown dropDown = new Dropdown();
	private TextBox textBox = new TextBox();

	public static final By LBL_BASICINFO = By.xpath("//div[@id='section1']//h4");
	public static final By LBL_ENTER_BASICINFO = By.xpath("//div[@id='section1']//i");
	public static final By LBL_FILTER = By.xpath("//div[@id='multiple-filters']//h4");
	public static final By LBL_SELECT_CRITERIA = By.xpath("//div[@id='multiple-filters']//i");
	public static final By LBL_DETAILS = By.xpath("//div[@id='section3']//h4");
	public static final By LBL_BUNDLE_DETAILS = By.xpath("//div[@class='bundleDetails']//h4");
	public static final By LBL_SET_PROMO_DETAILS = By.xpath("//div[@id='section3']//i");
	public static final By BTN_CANCEL = By.id("cancelBtn");
	public static final By BTN_CANCEL_1 = By.xpath("//button[@id='cancelBtn']");
	public static final By DPD_PROMO_TYPE = By.id("promotype");
	public static final By TXT_PROMO_NAME = By.id("name");
	public static final By TXT_DISPLAY_NAME = By.id("displayname");
	public static final By BTN_NEXT = By.xpath("//button[@id='submitBtn']");
	public static final By DPD_LOCATION = By.id("location-select");
	public static final By LBL_CREATE_PROMOTION = By.xpath("//li[text()='Create Promotion']");
	public static final By BTN_END_PROMO = By.id("disablepromotion");
	public static final By BTN_EXPIRE = By.xpath("//button[@class='ajs-button ajs-ok']");
	public static final By BTN_PROMPT_CANCEL = By.xpath("//button[@class='ajs-button ajs-cancel']");
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
	public static final By TXT_CATEGORY = By.id("categorySelect");
	public static final By DPD_LOCATION_LIST = By.xpath("//ul[@id='select2-location-select-results']//li");
	public static final By DPD_ORG = By.id("org-select");
	public static final By BTN_ORG_RIGHT = By.id("singleSelectLtoR");
	public static final By DPD_LOC = By.id("location-select");
	public static final By BTN_LOC_RIGHT = By.id("singleSelectLtoR-Loc");
	public static final By TXT_QUANTITY = By.id("bundleItem0");
	public static final By TXT_BUNDLE_PRICE = By.id("bundleprice");
	public static final By LBL_TOTAL_PRICE = By.id("product-price");
	public static final By LBL_BUNDLE_DISCOUNT = By.xpath("//div[@class='discount-price']//span[3]");
	public static final By TXT_ITEM1 = By.xpath("//*[@id='itemSelect']//li/input");
	public static final By CHK_NO_END_DATE = By.id("hasnoenddate");
	public static final By RB_BUNDLE_AMOUNT = By.id("bundleAmountCheckbox");
	public static final By CHK_BUNDLE_OVERFLOW = By.id("hasoverflow");
	public static final By DPD_DEVICE = By.id("device-select");
	public static final By RB_BUNDLE_PRICE = By.id("bundlePriceCheckbox");
	public static final By DPD_LOCATION_REMOVE = By
			.xpath("//select[@id='location-select']//..//span[@class='select2-selection__clear']");
	public static final By DPD_DESELECT_ORGANIZATION = By.id("multiselectId");
	public static final By DPD_DESELECT_LOCATION = By.id("multiselectIdLoc");
	public static final By BTN_ORG_LEFT = By.id("singleSelectRtoL");
	public static final By BTN_LOC_LEFT = By.id("singleSelectRtoL-Loc");
	public static final By DPD_SELECTED_ITEM = By.id("categorySelectInput");
	public static final By SELECT_DISABLED_LOCATION = By.id("filtervalues");
	public static final By SELECT_ALL_LOCATION = By.id("selectAllLtoR-Loc");
	public static final By TXT_SELECTED_ITEM = By.xpath("//li[text()='AutomationApple']");
	public static final By BUNDLE_BUILD = By.xpath("//dt[text()='Build Bundle']");
	public static final By GROUP_NAME = By.id("groupname");
	public static final By BTN_ADD = By.id("groupmodalsave");
	public static final By SELECT_CHECKBOX = By.xpath(
			"//input[@onclick='setCheckBox(\"29c6a79201bc3f424b8bab93a5ed0c89\",true,\"itemdatatable\",\"itemcheckbox\")']");
	public static final By SELECT_ANOTHER_CHECKBOX = By.xpath(
			"//input[@onclick='setCheckBox(\"40b9fbbc57f66e1b734a59c6f4c1a48a\",true,\"itemdatatable\",\"itemcheckbox\")']");
	public static final By CHECKBOX_SAMEPROD = By.xpath(
			"//input[@onclick='setCheckBox(\"259ccd00a61aab13b7774cba6f677537\",true,\"itemdatatable\",\"itemcheckbox\")']");
	public static final By ERROR_MSG = By.xpath("//div[text()='Only letters, numbers and underscores allowed.']");
	public static final By ITEM_SEARCH = By.id("itemsearch");
	public static final By CANCEL_BTN = By.id("groupmodalcancel");
	public static final By NAME_GRID = By.id("itemdatatable_name");
	public static final By UPC_GRID = By.id("itemdatatable_upc");
	public static final By CATEGORY_NAME_GRID = By.id("categorydatatable_category");
	public static final By CATEGORY_UPC_GRID = By.id("categorydatatable_upc");
	public static final By TXT_RECORD = By.id("itemdatatable_pager_label");
	public static final By PROD_CATE_SELECTED = By.xpath("//div[text()='2 Products, 1 Categories selected']");
	public static final By CATEGORY_PRODUCT = By
			.xpath("//input[@onclick='setCheckBox(\"CAT 6 PROMO\",true,\"categorydatatable\",\"categorycheckbox\")']");
	public static final By LBL_PROMO_TYPE = By.xpath("//dt[text()='Promotion Type']");
	public static final By LBL_PROMO_NAME = By.xpath("//dt[text()='Promotion Name']");
	public static final By LBL_DISPLAY_NAME = By.xpath("//dt[text()='Display Name']");
	public static final By FILTER_PAGE = By.xpath("//div[text()='Choose Promotion Filters']");
	public static final By DETAILS_PAGE = By.xpath("//div[text()='Promotion Details']");
	public static final By LBL_BUILD_BUNDLE = By.xpath("//dt[text()='Build Bundle']");
	public static final By BTN_ADD_GROUP = By.xpath("//i[text()='Add Group']");
	public static final By LBL_BUNDLE_GROUP = By.id("bundleModaltemplate-title");
	public static final By TXT_GROUP_NAME = By.id("groupname");
	public static final By ITEM_CHECK_BOX = By
			.xpath("//td[@aria-readonly='true' and @aria-describedby='itemdatatable_activeCheckBox']//input");
	public static final By CATEGORY_CHECK_BOX = By
			.xpath("//td[@aria-readonly='true' and @aria-describedby='categorydatatable_activeCheckBox']//input");
	public static final By GROUP_MODAL_SAVE = By.id("groupmodalsave");
	public static final By BUNDLE_OPTION_ITEM = By.xpath("//select[@name='discountby']/option[text()='Item']");
	public static final By BUNDLE_OPTION_CATEGORY = By.xpath("//select[@name='discountby']/option[text()='Category']");
	public static final By DELETE_GROUP = By.xpath(
			"//a[@style='color: #FFFFFF;cursor: pointer;background: #9E9E9E !important;padding: 4px 5px;font-size: 16px;border-radius: 100%;']");
	public static final By LBL_BUNDLE_GROUP_EDIT = By.xpath("//a[text()='Edit']");
	public static final By INPUT_ITEM_SEARCH = By.id("itemsearch");
	public static final By PRODUCT_FILTER = By.id("prodfilter");
	public static final By CATEGORY_FILTER = By.id("categoryfilter");
	public static final By INPUT_CATEGORY_SEARCH = By.id("categorysearch");
	public static final By DELETE_GROUP_HEADER = By.xpath("//div[text()='Confirm Group Delete']");
	public static final By CLOSE_GROUP_PROMPT = By.xpath("//div[text()='Confirm Close / Cancel']");
	public static final By PROMPT_CONTENT = By.xpath("//div[@class='ajs-content']");
	public static final By LBL_CREATED_GROUP = By.id("criterialabel");
	public static final By BUNDLE_GROUP_NAME = By.cssSelector("td>.bundle-item");
	public static final By BUNDLE_LIST = By.xpath("//td[@aria-describedby='groupdatatable_name']");
	public static final By ITEM_GRID = By.xpath("//div[@id='itemdatatable_scroll']//tbody[@role='rowgroup']/tr");
	public static final By CATEGORY_GRID = By
			.xpath("//div[@id='categorydatatable_scroll']//tbody[@role='rowgroup']/tr");
	public static final By BUNDLE_LIST_MESSAGE = By.id("groupcount");
	public static final By BUNDLE_LIST_DELETE = By.xpath("//a[@title='Delete']");
	public static final By BUNDLE_GROUP_CLOSE_BTN = By.id("groupmodalcross");

	public By objLocation(String value) {
		return By.xpath("//li[contains(text(),'" + value + "')]");
	}

	public By Product(String product) {
		return By.xpath("//tr[@data-id='259ccd00a61aab13b7774cba6f677537']//td[text()='" + product + "']");
	}

	public By dropdownBuildBundle(String dropdown) {
		return By.xpath("//select[@id='discountBy']//option[text()='" + dropdown + "']");
	}

	public void newPromotion(String promotionType, String promotionName, String displayName, String orgName,
			String locationName) {
		dropDown.selectItem(DPD_PROMO_TYPE, promotionType, Constants.TEXT);
		textBox.enterText(TXT_PROMO_NAME, promotionName);
		if (foundation.isDisplayed(TXT_DISPLAY_NAME))
			textBox.enterText(TXT_DISPLAY_NAME, displayName);
		foundation.click(BTN_NEXT);
		foundation.waitforElement(DPD_ORG, Constants.SHORT_TIME);
		dropDown.selectItem(DPD_ORG, orgName, Constants.TEXT);
		foundation.click(BTN_ORG_RIGHT);
		dropDown.selectItem(DPD_LOC, locationName, Constants.TEXT);
		foundation.click(BTN_LOC_RIGHT);
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
			CustomisedAssert.assertTrue(orgData.get(iter).contains(orgs.get(iter)));
		}
	}

	public void BundlePromotion(String promotionType, String promotionName, String displayName, String orgName,
			String locationName) {
		try {
			foundation.click(PromotionList.BTN_CREATE);
			foundation.isDisplayed(LBL_CREATE_PROMOTION);
			newPromotion(promotionType, promotionName, displayName, orgName, locationName);
			foundation.waitforElement(BTN_NEXT, Constants.SHORT_TIME);
			foundation.click(BTN_NEXT);
			foundation.waitforElement(DPD_DISCOUNT_BY, Constants.SHORT_TIME);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void selectBundlePromotionDetails(String discountBy, String item, String transactionMin,
			String discountType) {
		try {
			String actualValue;
			dropDown.selectItem(DPD_DISCOUNT_BY, discountBy, Constants.TEXT);
			if (!discountBy.equalsIgnoreCase(discountType)) {
				textBox.enterText(TXT_ITEM, item);
				foundation.threadWait(Constants.ONE_SECOND);
				textBox.enterText(TXT_ITEM, Keys.ENTER);
				foundation.threadWait(Constants.TWO_SECOND);
				actualValue = dropDown.getSelectedItem(DPD_ITEM_SELECT);
			} else {
				textBox.enterText(SEARCH_CATEGORY, item);
				foundation.threadWait(Constants.TWO_SECOND);
				textBox.enterText(SEARCH_CATEGORY, Keys.ENTER);
				foundation.threadWait(Constants.TWO_SECOND);
				actualValue = dropDown.getSelectedItem(DPD_CATEGORY);
			}
			CustomisedAssert.assertEquals(actualValue, item);
			textBox.enterText(TXT_TRANSACTION_MIN, transactionMin);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void selectBundlePromotionPricing(String bundlePrice) {
		try {
			textBox.enterText(TXT_BUNDLE_PRICE, bundlePrice);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void selectBundlePromotionTimes(String discountTime, String discountDuration) {
		try {
			dropDown.selectItem(DPD_DISCOUNT_TIME, discountTime, Constants.TEXT);
			if (!Constants.DELIMITER_SPACE.equals(discountDuration))
				dropDown.selectItem(DPD_DURATION, discountDuration, Constants.TEXT);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void recurringDay() {
		try {
			Calendar calendar = Calendar.getInstance();
			int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
			foundation.click(By.xpath("//div[@id='recurringInput']//input[" + dayOfWeek + "]"));
			ExtFactory.getInstance().getExtent().log(Status.INFO, "selected day of week as [ " + dayOfWeek + " ]");

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void tenderDiscountDetails(String tenderType, String discountType, String applyDIscountTo,
			String discountAmount, String transactionAmount) {
		try {
			dropDown.selectItem(MULTI_SELECT_TENDER_TYPES, tenderType, Constants.TEXT);
			dropDown.selectItem(DPD_DISCOUNT_TYPE, discountType, Constants.TEXT);
			dropDown.selectItem(DPD_APPLY_DISCOUNT_TO, applyDIscountTo, Constants.TEXT);
			if (foundation.isDisplayed(TXT_AMOUNT))
				textBox.enterText(TXT_AMOUNT, discountAmount);
			else
				textBox.enterText(TXT_DISCOUNT_PERCENTAGE, discountAmount);

			textBox.enterText(TXT_TRANSACTION_MIN, transactionAmount);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public String[] discountRange() {
		String[] discountprice = null;
		try {
			List<WebElement> bundleDiscount = getDriver().findElements(LBL_BUNDLE_DISCOUNT);
			for (int i = 0; i < bundleDiscount.size(); i++) {
				discountprice[i] = foundation.getText(By.xpath("//*[@id='bundlesummary']/b/span[" + i + "]"));
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		return discountprice;
	}

	public void newPromotionList(String promotionType, String promotionName, String orgName, String locationName) {
		dropDown.selectItem(DPD_PROMO_TYPE, promotionType, Constants.TEXT);
		textBox.enterText(TXT_PROMO_NAME, promotionName);
		foundation.click(BTN_NEXT);
		foundation.waitforElement(DPD_ORG, Constants.SHORT_TIME);
		dropDown.selectItem(DPD_ORG, orgName, Constants.TEXT);
		foundation.click(BTN_ORG_RIGHT);
		dropDown.selectItem(DPD_LOC, locationName, Constants.TEXT);
		foundation.click(BTN_LOC_RIGHT);
	}

	public void selectWeekDays(String weekDays) {
		try {
			List<String> weekDaysData = Arrays.asList(weekDays.split(Constants.DELIMITER_HASH));
			System.out.println(weekDaysData);
			for (int iter = 0; iter < weekDaysData.size(); iter++) {
				foundation
						.click(By.xpath("//div[@id='recurringInput']//dd/input[@id='" + weekDaysData.get(iter) + "']"));
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void createPromotion(String promotionType, String promotionName, String displayName) {
		dropDown.selectItem(DPD_PROMO_TYPE, promotionType, Constants.TEXT);
		CustomisedAssert.assertTrue(foundation.isDisplayed(objLocation(promotionType)));
		CustomisedAssert.assertTrue(foundation.isDisplayed(LBL_PROMO_NAME));
		textBox.enterText(TXT_PROMO_NAME, promotionName);
		CustomisedAssert.assertTrue(foundation.isDisplayed(LBL_DISPLAY_NAME));
		textBox.enterText(TXT_DISPLAY_NAME, displayName);
		foundation.click(BTN_NEXT);
		foundation.waitforElementToBeVisible(LBL_FILTER, 5);
	}

	public void selectOrgLoc(String org, String location) {
		dropDown.selectItem(DPD_ORG, org, Constants.TEXT);
		foundation.click(BTN_ORG_RIGHT);
		foundation.threadWait(Constants.THREE_SECOND);
		dropDown.selectItem(DPD_LOC, location, Constants.TEXT);
		foundation.click(BTN_LOC_RIGHT);
		foundation.click(BTN_NEXT);
		foundation.waitforElementToBeVisible(LBL_BUNDLE_DETAILS, 5);
	}

	public void verifyBundleOption(List<String> requiredData) {
		List<String> options = dropDown.getAllItems(DPD_DISCOUNT_BY);
		CustomisedAssert.assertEquals(options.get(0), requiredData.get(0));
		CustomisedAssert.assertEquals(options.get(1), requiredData.get(1));
		CustomisedAssert.assertEquals(options.get(2), requiredData.get(2));
		CustomisedAssert.assertEquals(options.get(3), requiredData.get(3));
	}

	public void creatingBundleGroup(String groupName, String product) {
		foundation.click(BTN_ADD_GROUP);
		foundation.waitforElementToBeVisible(LBL_BUNDLE_GROUP, 5);
		textBox.enterText(TXT_GROUP_NAME, groupName);
		foundation.click(INPUT_ITEM_SEARCH);
		textBox.clearText(INPUT_ITEM_SEARCH);
		textBox.enterText(INPUT_ITEM_SEARCH, product);
		foundation.click(ITEM_CHECK_BOX);
		foundation.threadWait(Constants.THREE_SECOND);
		foundation.click(GROUP_MODAL_SAVE);
		foundation.threadWait(Constants.THREE_SECOND);
	}

	public void creatingBundleGroupWithCategory(String groupName, String product, String category) {
		foundation.click(BTN_ADD_GROUP);
		foundation.waitforElementToBeVisible(LBL_BUNDLE_GROUP, 5);
		foundation.threadWait(Constants.TWO_SECOND);
		textBox.enterText(TXT_GROUP_NAME, groupName);
		foundation.click(INPUT_ITEM_SEARCH);
		textBox.clearText(INPUT_ITEM_SEARCH);
		textBox.enterText(INPUT_ITEM_SEARCH, product);
		foundation.click(ITEM_CHECK_BOX);
		foundation.threadWait(Constants.THREE_SECOND);
		foundation.click(CATEGORY_FILTER);
		foundation.click(INPUT_CATEGORY_SEARCH);
		textBox.clearText(INPUT_CATEGORY_SEARCH);
		textBox.enterText(INPUT_CATEGORY_SEARCH, category);
		foundation.click(CATEGORY_CHECK_BOX);
		foundation.threadWait(Constants.THREE_SECOND);
		foundation.click(GROUP_MODAL_SAVE);
		foundation.threadWait(Constants.THREE_SECOND);
	}

	public void editBundleGroup(String groupName, String product, String category) {
		foundation.click(LBL_BUNDLE_GROUP_EDIT);
		foundation.waitforElementToBeVisible(LBL_BUNDLE_GROUP, 5);
		foundation.click(TXT_GROUP_NAME);
		textBox.clearText(TXT_GROUP_NAME);
		textBox.enterText(TXT_GROUP_NAME, groupName);
		foundation.click(INPUT_ITEM_SEARCH);
		textBox.clearText(INPUT_ITEM_SEARCH);
		textBox.enterText(INPUT_ITEM_SEARCH, product);
		foundation.click(ITEM_CHECK_BOX);
		foundation.threadWait(Constants.THREE_SECOND);
		foundation.click(CATEGORY_FILTER);
		foundation.click(INPUT_CATEGORY_SEARCH);
		textBox.clearText(INPUT_CATEGORY_SEARCH);
		textBox.enterText(INPUT_CATEGORY_SEARCH, category);
		foundation.click(CATEGORY_CHECK_BOX);
		foundation.threadWait(Constants.THREE_SECOND);
		foundation.click(GROUP_MODAL_SAVE);
		foundation.threadWait(Constants.THREE_SECOND);
	}

	public void cancellingPromotion() {
		foundation.objectClick(BTN_CANCEL_1);
		foundation.waitforElementToBeVisible(LBL_FILTER, 5);
		foundation.scrollIntoViewElement(BTN_CANCEL_1);
		foundation.click(BTN_CANCEL_1);
		foundation.threadWait(Constants.SHORT_TIME);
		foundation.objectClick(BTN_CANCEL_1);
		foundation.threadWait(Constants.SHORT_TIME);
		foundation.click(BTN_CANCEL_1);
		foundation.alertAccept();
	}

	public void deleteBundleGroup() {
		foundation.click(DELETE_GROUP);
		foundation.threadWait(Constants.TWO_SECOND);
		CustomisedAssert.assertTrue(foundation.isDisplayed(DELETE_GROUP_HEADER));
		foundation.click(BTN_EXPIRE);
		foundation.threadWait(Constants.THREE_SECOND);
	}

	public void selectItem(String item) {
		foundation.click(INPUT_ITEM_SEARCH);
		textBox.clearText(INPUT_ITEM_SEARCH);
		textBox.enterText(INPUT_ITEM_SEARCH, item);
		foundation.click(ITEM_CHECK_BOX);
		foundation.threadWait(Constants.THREE_SECOND);
	}

	public void selectCategory(String category) {
		foundation.click(INPUT_CATEGORY_SEARCH);
		textBox.clearText(INPUT_CATEGORY_SEARCH);
		textBox.enterText(INPUT_CATEGORY_SEARCH, category);
		foundation.click(CATEGORY_CHECK_BOX);
		foundation.threadWait(Constants.THREE_SECOND);
	}
}
