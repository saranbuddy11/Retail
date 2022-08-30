package at.smartshop.pages;

import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.aventstack.extentreports.Status;

import at.framework.browser.Browser;
import at.framework.browser.Factory;
import at.framework.generic.CustomisedAssert;
import at.framework.reportsetup.ExtFactory;
import at.framework.ui.CheckBox;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.tests.TestInfra;

public class LocationSummary extends Factory {

	private Dropdown dropDown = new Dropdown();
	public Login login = new Login();
	private TextBox textBox = new TextBox();
	private Foundation foundation = new Foundation();
	private NavigationBar navigationBar = new NavigationBar();
	private LocationList locationList = new LocationList();
	private Browser browser = new Browser();
	private CheckBox checkBox = new CheckBox();

	public static final By DPD_DISABLED = By.id("isdisabled");
	public static final By NO_BTN_PROMPT_AGEVERIFICATION = By.id("ageverificationpopupcancel");
	public static final By YES_BTN_PROMPT_AGEVERIFICATION = By.id("ageverificationpopupSaveBtn");
	public static final By PANTRY_TYPE = By.xpath("//input[@name='readonlytype']");
	public static final By BTN_SAVE = By.id("saveBtn");
	public static final By BTN_MANAGE_COLUMNS = By.id("manageProductGridColumnButton");
	public static final By BUTTON_MANAGE_COLUMNS = By.id("managePromotionGridColumnButton");
	public static final By POP_UP_BTN_APPLY = By.id("productDataGrid_hiding_modalDialog_footer_buttonok_lbl");
	public static final By DLG_COLUMN_CHOOSER = By.id("productDataGrid_hiding_modalDialog_content");
	public static final By DLG_PRODUCT_COLUMN_CHOOSER_FOOTER = By.id("productDataGrid_hiding_modalDialog_footer");
	public static final By BTN_CLOSE_PRODUCT = By.id("previewcancel");
	public static final By DLG_COLUMN_CHOOSER_OPTIONS = By
			.cssSelector("#productDataGrid_hiding_modalDialog_content > ul");
	public static final By TBL_PRODUCTS = By.id("productDataGrid");
	public static final By TBL_PRODUCTS_GRID = By.cssSelector("#productDataGrid > tbody");
	public static final By TBL_PRODUCTS_LIST = By.cssSelector("#productDataGrid > tbody > td");
	public static final By TAB_CONTAINER_GRID = By.cssSelector("#tabcontainer > ul");
	public static final By TXT_PRODUCT_FILTER = By.cssSelector("input#productFilterType");
	public static final By TXT_TOP_OFF_SUBSIDY = By.cssSelector("div.topoffsubsidymain");
	public static final By TXT_ROLL_OVER_SUBSIDY = By.cssSelector("div.rolloversubsidymain");
	public static final By POP_UP_BTN_SAVE = By.id("confirmDisableId");
	public static final By BTN_LOCATION_SETTINGS = By.xpath("//button[@id='toggleinfo']");
	public static final By DPD_HAS_LOCKER = By.id("haslocker");
	public static final By DPD_GMA_SUBSIDY = By.id("gmasubsidy");
	public static final By DPD_SPECIAL_TYPE = By.id("specialtype");
	public static final By DPD_PAYROLL = By.id("payrolldeduct");
	public static final By DPD_TOP_OFF_RECURRENCE = By.xpath("//*[@id='topoffsubsidyrange']//td/select");
	public static final By DPD_ROLL_OVER_RECURRENCE = By.xpath("//*[@id='rolloversubsidyrange']//td/select");
	public static final By DPD_PAY_CYCLE_RECURRENCE = By.xpath("//*[@id='payRollRange']//td/select");
	public static final By DPD_HAS_ORDER_AHEAD = By.id("hasonlineordering");
	public static final By DPD_HAS_PICK_UP_LOCATIONS = By.id("haspickuplocations");
	public static final By LNK_PICK_UP_LOCATION = By.xpath("//span[@id='pickupLocationToggle']");
	public static final By LBL_ORDER_AHEAD = By.xpath("//dt[text()='Has Order Ahead']");
	public static final By LBL_LOCKER_PICK_UP_TITLE = By.xpath("//*[@id='lockersystempickuptitle']/i");
	public static final By LNK_LOCKER_NAME = By.xpath("//*[@id='pickuplockersystems']/div/a");
	public static final By TXT_SYSTEM_NAME = By.cssSelector("input#systemName");
	public static final By LBL_SHELF_LIFE = By.xpath("//*[@id='pickuplockersystems']/div/span");
	public static final By BUTTON_LOCATION_INFO = By.cssSelector("button#toggleinfo");
	public static final By DPD_RETRIEVE_ACCOUNT = By.cssSelector("select#retrieveaccount");
	public static final By FIELD_RETRIEVE_CHECKBOX = By.cssSelector("div#enableRetrieveAccountOptions");
	public static final By TXT_ERR_MSG = By.cssSelector("dd.error-txt");
	private static final By TXT_HAS_LOCKERS = By.xpath("//dt[text()='Has Lockers']");
	public static final By TXT_GMA_SUBSIDY = By.xpath("//dt[text()='GMA Subsidy']");
	public static final By TXT_SPECIAL_TYPE = By.xpath("//dt[text()='Special Type']");
	public static final By TXT_MULTI_TAX_REPORT = By.xpath("//b[text()='Multi Tax Report Naming']");
	public static final By LBL_LOCATION_SUMMARY = By.cssSelector("li[id='Location Summary']");
	public static final By TAB_PRODUCTS = By.id("loc-products");
	public static final By CANCEL_BTN = By.id("cancelBtn");
	public static final By TXT_SEARCH = By.id("productFilterType");
	public static final By LBL_TAX_CATEGORY = By
			.xpath("//td[@role='gridcell' and @aria-describedby='productDataGrid_taxcat']");
	public static final By ROW_PRODUCTS = By.cssSelector("#productDataGrid > tbody > tr");
	public static final By LBL_SPINNER_MSG = By.xpath("//div[@class='humane humane-libnotify-info']");
	public static final By BTN_FULL_SYNC = By.id("fullsync");
	public static final By TXT_PRICE_IN_GRID = By.id("fullsync");
	public static final By TXT_ADD_PRODUCT_SEARCH = By.id("productFilterTypes");
	public static final By BTN_ADD_PRODUCT_ADD = By.id("modalsave");
	public static final By BTN_DEPLOY_DEVICE = By.id("deployKiosk");
	public static final By TXT_DEVICE_SEARCH = By.id("deviceFilterType");
	public static final By BTN_HOME_COMMERCIAL = By.cssSelector("a#loc-homeCommercial");
	public static final By BTN_UPLOAD_INPUT = By.xpath("//div[@class ='qq-upload-button btn btn-success']/input");
	public static final By BTN_ADD_HOME_COMMERCIAL = By.xpath("//a[text()='Add Home Commercial']");
	public static final By TXT_UPLOAD_NEW = By.xpath("//a[text()='Upload New']");
	public static final By BTN_UPLOAD_IMAGE = By.xpath("//div[@class ='qq-upload-button btn btn-success']");
	public static final By TXT_ADD_NAME = By.xpath("//input[@id = 'cmrhometext']");
	public static final By BTN_ADD = By.xpath("//a[text()= 'Add']");
	public static final By TXT_CMR_FILTER = By.id("cmrHomeFilterType");
	public static final By BTN_REMOVE = By.xpath("//a[@id='previewremove']");
	public static final By TXT_UPLOAD_STATUS = By.xpath("//span[@class='qq-upload-status-text']");
	public static final By LINK_HOME_PAGE = By.xpath("//a[@id='sup-location']");
	public static final By DPD_KIOSK_LANGUAGE = By.id("ksklanguage");
	public static final By DPD_ALTERNATE_LANGUAGE = By.id("altlanguage");
	public static final By BTN_SYNC = By.xpath("//button[text()='Update Prices & Full Sync']");
	public static final By TXT_CUSTOMER = By.id("customer");
	public static final By DPD_ROUTE = By.id("route");
	public static final By TXT_LOCATION_NUMBER = By.id("locationnumber");
	public static final By TXT_INVENTORY_FILTER = By.id("inventoryFilterType");
	public static final By BTN_ADD_PRODUCT = By.id("addProd");
	public static final By TBL_INVENTORY = By.id("inventoryDataGrid");
	public static final By DPD_SHOW_PROD_LOOKUP = By.id("showprdlup");
	public static final By LNK_INVENTORY = By.cssSelector("a#loc-inventory");
	public static final By LNK_TAX_MAPPING = By.xpath("//a[text()='Tax Mapping']");
	public static final By BTN_ADD_MAPPING = By.cssSelector("a#addMapping");
	public static final By DPD_TAXCAT = By.cssSelector("select#taxcat");
	public static final By DPD_RATE = By.cssSelector("select#taxname");
	public static final By BTN_POPUP_SAVE = By.cssSelector("a#taxcatsave");
	public static final By BTN_POPUP_REMOVE = By.cssSelector("a#taxcatremove");
	public static final By DPD_VDI_PROVDIER = By.xpath("//select[@id='vdiprovideradded']");
	public static final By CHK_VDI = By.xpath("//input[@id='vdicbx']");
	public static final By BTN_VDI_PLUS = By.xpath("//button[@id='vdi-plus-btn']");
	public static final By BTN_VDI_DEL = By.xpath("//button[@onclick='vdiDelBtnClick(this)']");
	public static final By TXT_USER_KEY = By.xpath("//input[@id='vdiuserkey-added']");
	public static final By BTN_YES = By.xpath("//button[text()='Yes']");
	public static final By BTN_NO = By.xpath("//button[text()='No ']");
	public static final By LBL_USER_KEY = By.xpath("//input[@id='vdiuserkey-added']");
	public static final By DPD_PRINTGROUP = By.cssSelector("select#printer");
	public static final By LBL_PRINT_COLUMN = By.xpath("//tbody/tr/td[@aria-describedby='productDataGrid_printer']");
	public static final By LBL_PRINT_DOWN_ARROW = By
			.xpath("//td[@aria-describedby='productDataGrid_printer']//div[contains(@class,'ui-icon-triangle')]");
	public static final By LBL_REASON_CODE = By
			.xpath("//td[@aria-describedby='inventoryDataGrid_reasoncode'][text()='-Choose-']");
	public static final By LIST_REASON_CODE = By
			.xpath("//div[@id='promoGrid_editor_list']/..//ul[@class='ui-igcombo-listitemholder']//li");
	public static final By TAB_TAX_MAPPING = By.id("loc-taxMapping");
	public static final By DPD_TAX_CATEGORY = By.id("taxcat");
	public static final By DPD_TAX_RATE_EDIT = By.id("targetid");
	public static final By BTN_CANCEL_MAPPING = By.id("taxcatcancel");
	public static final By BTN_SAVE_MAPPING = By.id("taxcatsave");
	public static final By BTN_REMOVE_MAPPING = By.id("taxcatremove");
	public static final By TXT_SEARCH_TAX_MAPPING = By.xpath("//div[@id='taxmapdt_filter']//input");
	public static final By BTN_DEVICE = By.cssSelector("a#loc-kiosk");
	public static final By TBL_DEVICE_POPUP_GRID = By
			.cssSelector("div.dataTables_scroll > div.dataTables_scrollHead > div > table >thead");
	public static final By TBL_DEVICE_POPUP_ROW = By.xpath("//*[@id='choosekskdt']/tbody/tr");
	public static final By TXT_DEVICE_POPUP_SEARCH = By.xpath("//input[@aria-controls='choosekskdt']");
	public static final By BTN_DEVICE_ADD = By.xpath("//a[text()='Add']");
	public static final By BTN_DEVICE_CLOSE = By.xpath("//a[@id='modalcancel']");
	public static final By LBL_ROW_HEADER = By.xpath("//div[@class='dataTables_scrollHeadInner']//th[text()='Name']");
	public static final By LBL_COLUMN_DATA = By.xpath("//div[@class='namelefttxt']");
	public static final By LBL_TABLE_DATA = By
			.xpath("//div[@class='dataTables_scroll']//td[@class='dataTables_empty']");
	public static final By LBL_TABLEINFO = By.id("choosekskdt_info");
	public static final By LBL_CAUTION_ICON = By
			.xpath("//i[@class='fa fa-exclamation-triangle' and @style='color: #FF4C5B;']");
	public static final By LBL_TICKMARK_ICON = By
			.xpath("//i[@class='fa fa-check-circle' and @style='color: #2983C4;']");
	public static final By LBL_HOVER_MESSAGE = By.xpath("//td[@class='ui-state-hover']");
	public static final By TXT_DEVICE_STATUS = By.xpath("//span[@id='devicestatus']");
	public static final By TXT_DEVICE_SUMMARY = By.xpath("//li[@id='Device Summary']");
	public static final By TXT_DEVICE_NAME = By.xpath("//dd[@id='kioskshow-name']");
	public static final By TBL_DEVICE_GRID = By.id("deviceDataGrid_table");
	public static final By TBL_DEVICE_ROW = By.xpath("//table[@id='deviceDataGrid_table']/tbody/tr");
	public static final By LBL_TBL_HEADER = By
			.xpath("//th[contains(@id,'deviceDataGrid_table')]//span[@class='ui-iggrid-headertext']");
	public static final By LBL_SHOW_RECORDS = By
			.xpath("//div[@id='deviceDataGrid_table_container']//div[@class='ui-iggrid-results']");
	public static final By LBL_PAGER = By.id("deviceDataGrid_table_pager");
	private static final By BTN_SHOW = By.xpath("//span[text()='Taxcat']//..//a[text()='Show']");
	public static final By BTN_APPLY = By.id("productDataGrid_hiding_modalDialog_footer_buttonok_lbl");
	public static final By LBL_PRINT_GROUP = By.id("productDataGrid_printer");
	public static final By BTN_PRINT_GROUP = By.xpath("(//span[@class='ui-iggrid-columnchooser-hidebutton'])[23]");
	public static final By DPD_SHOW_DINING = By.id("hasdiningpreference");
	public static final By TXT_PAY_CYCLE_NAME = By
			.xpath("//*[contains(@id,'newrow')]//input[contains(@class,'paycycle-grpname')]");
	public static final By TXT_PAY_CYCLE_GROUP_NAME = By.xpath("//input[contains(@class,'paycycle-grpname')]");
	public static final By TXT_PAY_ROLL_SPEND_LIMIT = By.xpath("//input[contains(@class,'paycycle-spndlimit')]");
	public static final By TXT_PAY_CYCLE_SPEND_LIMIT = By
			.xpath("//*[contains(@id,'newrow')]//input[contains(@class,'paycycle-spndlimit')]");
	public static final By DPD_PAYROLL_DEDUCT = By.id("payrolldeduct");
	public static final By TBL_NAME_HEADER = By.xpath("//th[@id='productDataGrid_name']");
	public static final By DPD_SHOW_RECORD = By.xpath("//div[@id='productDataGrid_editor_dropDownButton']");
	public static final By TXT_10_RECORD = By.xpath("//span[@id='productDataGrid_editor_item_2']");
	public static final By TXT_5_RECORD = By.xpath("//span[@id='productDataGrid_editor_item_1']");
	public static final By TXT_PRODUCTS_COUNT = By.xpath("//span[@id='productDataGrid_pager_label']");
	public static final By BTN_EXPORT = By.cssSelector("button#productExportBtn");
	public static final By LBL_TAX_MAPPING = By.xpath("//a[@id='addMapping']");
	public static final By DPD_TAX_CAT = By.xpath("//select[@id='taxcat']");
	public static final By DPD_TAX_RATE = By.xpath("//select[@id='taxname']");
	public static final By LBL_TAX_CAT_SAVE = By.xpath("//a[@id='taxcatsave']");
	public static final By LBL_TAX_CAT_REMOVE = By.xpath("//a[@id='taxcatremove']");
	public static final By LBL_TAX_CAT_CANCEL = By.xpath("//a[@id='taxcatcancel']");
	public static final By TBL_TAX_GRID = By.id("taxmapdt");
	public static final By TBL_ROW = By.xpath("//*[@id='taxmapdt']/tbody/tr");
	public static final By TXT_TAX_FILTER = By.cssSelector("#taxmapdt_filter > label > input[type=text]");
	public static final By BTN_CLOSE_COMMERCIAL = By.xpath("//a[text()='Add Close Commercial']");
	public static final By DPD_TAX_RATE_2 = By.xpath("//select[@id='targetid']");
	public static final By BTN_LNK_DEVICE_SUMMARY = By
			.xpath("//td[@aria-describedby='deviceDataGrid_table_device']//a[@class='devices']");
	public static final By BTN_REMOVE_DEVICE = By.xpath("//button[@class='btn btn-danger']");
	public static final By BTN_YES_REMOVE = By.xpath("//button[text()='Yes, Remove']");
	public static final By LBL_DURATION = By.xpath("//td[@aria-describedby='deviceDataGrid_table_duration']//a");
	public static final By LBL_POPUP_DEPLOY_DEVICE_CLOSE = By
			.xpath("//div[@id='modaltemplate']//div[@class='modal-header']//a[@class='close']");
	public static final By TXT_FIND_DEVICE = By.xpath("//*[@id='choosekskdt_filter']/label/input");
	public static final By TBL_DEVICE_LIST = By.xpath("//*[@id='choosekskdt']/tbody//td");
	public static final By TBL_DEPLOYED_DEVICE_LIST = By.xpath("//*[@id='deviceDataGrid_table']/tbody/tr/td/a/i");
	public static final By POPUP_YES = By.id("ageverificationpopupSaveBtn");
	public static final By BTN_CREATE_CONSUMER = By.id("createconsumer");
	public static final By TBL_DEVICE_HEADER = By.xpath("//*[@id='choosekskdt_wrapper']//th");
	public static final By TBL_DEVICE_NAME_COLUMN = By.xpath("//*[@id='choosekskdt']/tbody//td[1]");
	public static final By AGE_VERIFICATION = By.id("ageverification");
	public static final By PRODUCT_NAME = By
			.xpath("//table[@id='productDataGrid']/tbody/tr/td[@aria-describedby='productDataGrid_name']");
	public static final By INVENTORY_NAME = By
			.xpath("//table[@id='inventoryDataGrid']/tbody/tr/td[@aria-describedby='inventoryDataGrid_name']");
	public static final By VALIDATE_HEADING = By.id("Location Summary");
	public static final By DPP_MARKET_CARD = By.id("mkcidedit");
	public static final By BTN_UPDATE_PRICE = By.id("updprice");
	public static final By BTN_EDIT_PRODUCT = By.xpath("//a[@class='btn btn-small btn-primary']");
	public static final By TXT_NAME = By.id("name");
	public static final By BTN_CREATE_PROMO = By.id("promoAddBtn");
	public static final By TXT_CATEGORY = By.id("taxcat");
	public static final By INVENTORY_QUANTITY = By
			.xpath("//table[@id='inventoryDataGrid']/tbody/tr/td[@aria-describedby='inventoryDataGrid_qtyonhand']");
	public static final By CHK_TOP_OFF_SUBSIDY = By.xpath("//input[@class='topoffsubsidy-default topoffcheckbox']");
	public static final By CHK_DEFAULT_TOP_OFF = By.xpath("//input[@class='topoffsubsidy topoffdefaultcheckbox']");
	public static final By CHK_DEFAULT_ROLL_OVER = By
			.xpath("//input[@class='rolloversubsidy rolloverdefaultcheckbox']");
	public static final By CHK_ROLL_OVER_SUBSIDY = By
			.xpath("//input[@class='topoffsubsidy-default rolloversubsidy-default rollovercheckbox']");
	public static final By TXT_TOP_OFF_GROUP_NAME = By.xpath("//*[@id='topoffsubsidyrange']//input[@name='groupname']");
	public static final By TXT_PAYROLL_GROUP_NAME = By.xpath("//*[@id='newrow-1']//input[@name='groupname']");
	public static final By TXT_TOP_OFF_AMOUNT = By.xpath("//*[@id='topoffsubsidyrange']//input[@name='amount']");
	public static final By TXT_ROLL_OVER_AMOUNT = By.xpath("//*[@id='rolloversubsidyrange']//input[@name='amount']");
	public static final By TXT_TOP_OFF_AMOUNT_VALUE = By.xpath(
			"/html/body/div[3]/form/div[2]/div/fieldset/div/div[2]/div[1]/dl/div[6]/div/div[1]/table[2]/tbody/tr/td[7]/input");
	public static final By TXT_ROLL_OVER_AMOUNT_VALUE = By.xpath(
			"/html/body/div[3]/form/div[2]/div/fieldset/div/div[2]/div[1]/dl/div[6]/div/div[2]/table[2]/tbody/tr/td[7]/input");
	public static final By CHK_TOP_OFF_ERROR = By.xpath(
			"/html/body/div[3]/form/div[2]/div/fieldset/div/div[2]/div[1]/dl/div[6]/div/div[1]/table[2]/tbody/tr/td[7]/p");
	public static final By CHK_ROLL_OVER_ERROR = By.xpath(
			"/html/body/div[3]/form/div[2]/div/fieldset/div/div[2]/div[1]/dl/div[6]/div/div[2]/table[2]/tbody/tr/td[7]/p");
	public static final By TXT_ROLL_OVER_GROUP_NAME = By
			.xpath("//*[@id='rolloversubsidyrange']//input[@name='groupname']");
	public static final By TXT_PICKUP_LOCATION_NAME = By
			.xpath("//input[@class='validfield pickupLocation pickupLocation-name']");
	public static final By START_DATE_PICKER_TOP_OFF = By
			.xpath("//input[@name='topoffsubsidystartdate' and @id='date']");
	public static final By START_DATE_PICKER_TOP_OFF_1 = By
			.xpath("//input[@name='topoffsubsidystartdate' and @id='date1']");
	public static final By DEVICE_BTN = By.xpath("(//a[@style='color: #2555D9;'])[2]");
	public static final By SECOND_DEVICE = By.xpath("(//a[@style='color: #2555D9;'])[4]");
	public static final By START_DATE_PICKER_ROLL_OVER = By
			.xpath("//input[@name='rolloversubsidydate' and @id='date2']");
	public static final By START_DATE_PICKER_ROLL_OVER_1 = By
			.xpath("//input[@name='rolloversubsidydate' and @id='date']");
	public static final By DATE_PICKER_PAY_ROLL = By.xpath("//input[@name='payrolldeductstartdate' and @id='date1']");
	public static final By TOP_OFF_DATE_PICKER_NEXT_LOCATION1 = By
			.xpath("/html/body/div[10]/div[1]/table/thead/tr[1]/th[3]");
	public static final By TOP_OFF_DATE_PICKER_PREV_LOCATION1 = By
			.xpath("/html/body/div[10]/div[1]/table/thead/tr[1]/th[1]");
	public static final By TOP_OFF_DATE_PICKER_NEXT_AUTOMATION1 = By
			.xpath("/html/body/div[5]/div[1]/table/thead/tr[1]/th[3]");
	public static final By TOP_OFF_DATE_PICKER_NEXT_LOCATION2 = By
			.xpath("/html/body/div[5]/div[1]/table/thead/tr[1]/th[3]");
	public static final By ROLL_OVER_DATE_PICKER_NEXT_LOCATION2 = By
			.xpath("/html/body/div[7]/div[1]/table/thead/tr[1]/th[3]");
	public static final By ROLL_OVER_DATE_PICKER_NEXT_LOCATION1 = By
			.xpath("/html/body/div[12]/div[1]/table/thead/tr[1]/th[3]");
	public static final By TOP_OFF_WARNING_MSG = By.xpath(
			"/html/body/div[3]/form/div[2]/div/fieldset/div/div[2]/div[1]/dl/div[6]/div/div[1]/table[2]/tbody/tr/td[6]/p[1]");
	public static final By ROLL_OVER_WARNING_MSG = By.xpath(
			"/html/body/div[3]/form/div[2]/div/fieldset/div/div[2]/div[1]/dl/div[6]/div/div[2]/table[2]/tbody/tr/td[6]/p[2]");
	public static final By LBL_ROLL_OVER_WARNING_MSG = By.id("rolloverwarning-message");
	public static final By BTN_DELETE_TOP_OFF = By
			.xpath("//i[@class='fa fa-minus-circle fa-2x danger-color delBtnSubsidy']");
	public static final By BTN_DELETE_ROLL_OVER = By
			.xpath("//i[@class='fa fa-minus-circle fa-2x danger-color delBtnrolloverSubsidy']");
	public static final By BTN_ADD_ROLL_OVER = By
			.xpath("//i[@class='fa fa-plus-circle fa-2x primary-color addBtnrolloverSubsidy']");
	public static final By BTN_ADD_TOP_OFF = By.xpath("//i[@class='fa fa-plus-circle fa-2x primary-color addBtn']");
	public static final By DEVICE_NAME = By.xpath("(//*[@id='deviceDataGrid_table']/tbody/tr/td)[3]");
	public static final By TXT_PAYROLL = By.xpath("//dt[text()='Payroll Deduct']");
	public static final By INPUT_PAYROLL = By.id("clientpayrolldeduct");
	public static final By TXT_AGE_VERIFICATION = By.xpath("//dt[text()='Age Verification Enabled']");
	public static final By CHK_AGE_VERIFICATION = By.id("ageverification");
	public static final By INPUT_PAYROLL_MAIL = By.id("payrolldeductemail");
	public static final By CHK_BOX_PAYROLL_DEDUCT = By.xpath("//input[@name='payrolldeductautocomplete']");
	public static final By CHK_BOX_PAYROLL_DEDUCT_STREAM = By.xpath("//input[@name='payrolldeductstreamlinemode']");
	public static final By CHK_BOX_PAYROLL_DEDUCT_OFFLINE = By.xpath("//input[@name='allowofflinepd']");
	public static final By ADDRESS_INPUT = By.xpath("//input[@id='address']");
	public static final By CONTACTNAME_INPUT = By.xpath("//input[@id='contact']");
	public static final By CONTACTEMAIL_INPUT = By.xpath("//input[@id='contactemail']");
	public static final By TAB_LOCATION = By.xpath("//a[contains(text(),'Location')]");
	public static final By CLEAR_INVENTORY_FILTER = By.xpath("//a[@onclick='clearInventoryFilter()']");
	public static final By FILTER_HOME_CMMRCIAL = By.id("cmrHomeFilterType");
	public static final By HOME_CMMRCIAL_NAME = By
			.xpath("//table[@id='cmrHomeGrid']/tbody/tr/td[@aria-describedby='cmrHomeGrid_name']");
	public static final By DEVICE_RECORD = By.xpath("//span[@id='deviceDataGrid_table_pager_label']");
	public static final By BTN_SELECTALL = By.id("selectallprdBtn");
	public static final By BTN_SELECTNONE = By.id("selectnoneprdBtn");
	public static final By LBL_ADD_PRODUCT = By.id("modaltemplate-title");
	public static final By BTN_CANCEL_PRODUCT = By.id("modalcancel");
	public static final By TBL_DATA_GRID = By.cssSelector("#productDataGrid > tbody");
	public static final By TBL_GRID = By.id("productDataGrid");
	public static final By VALIDATE_HIGHLIGHTED_TEXT = By.xpath("//table[@id='chooseprddt']//tbody//tr");
	public static final By LBL_POPUP_ADD_PRODUCT_CLOSE = By
			.xpath("//div[@id='modaltemplate']//div[@class='modal-header']//a[@class='close']");
	public static final By SELECT_PRODUCT = By.xpath("//td[@aria-describedby='chooseprddt_name']");
	public static final By PRINTGROUP_NAME = By
			.xpath("//table[@id='productDataGrid']/tbody/tr/td[@aria-describedby='productDataGrid_printer']");
	public static final By LBL_PRODUCT_POPUP = By.id("modaltitle");
	public static final By EDIT_PRODUCT = By.xpath("//a[@class='btn btn-small btn-primary']");
	public static final By BTN_PRODUCT_ADD = By.id("modalsave");
	public static final By MIN_STOCK = By
			.xpath("//table[@id='productDataGrid']/tbody/tr/td[@aria-describedby='productDataGrid_minstock']");
	public static final By TAB_PROMOTIONS = By.id("loc-promotions");
	public static final By PROMOTIONS_SEARCH = By.id("promoFilterType");
	public static final By MANAGE_COLUMN_POPUP_HEADER = By
			.xpath("//div[@id='promoGrid_hiding_modalDialog']//span[@class='ui-dialog-title']");
	public static final By MANAGE_COLUMN_RESET_BUTTON = By.xpath(
			"//div[@id='promoGrid_hiding_modalDialog']//span[@id='promoGrid_hiding_modalDialog_reset_button_lbl']");
	public static final By MANAGE_COLUMN_APPLY_BUTTON = By.id("promoGrid_hiding_modalDialog_footer_buttonok_lbl");
	public static final By MANAGE_COLUMN_CANCEL_BUTTON = By.id("promoGrid_hiding_modalDialog_footer_buttoncancel_lbl");
	public static final By TBL_PRODUCTS_HEADER = By.cssSelector("#productDataGrid > tbody");
	public static final By CHK_TOP_OFF_EXCLUDE = By.className("chcktopoffexclude");
	public static final By CHK_ROLL_OVER_EXCLUDE = By.className("chckClass_");
	public static final By COL_PRICE = By.xpath("//tbody//td[@aria-describedby='productDataGrid_price']");
	public static final By DPD_INVENTORY_RECORD = By.id("inventoryDataGrid_editor_dropDownButton");
	public static final By DPD_PRODUCT_RECORD = By.id("productDataGrid_editor_dropDownButton");
	public static final By MATCH_PRODUCT_RECORD = By.id("productDataGrid_pager_label");
	public static final By MATCH_INVENTORY_RECORD = By.id("inventoryDataGrid_pager_label");
	public static final By BTN_TAX2 = By.xpath("(//span[@class='ui-iggrid-columnchooser-hidebutton'])[13]");
	public static final By LBL_TAX2_COLUMN = By.xpath("//tbody/tr/td[@aria-describedby='productDataGrid_taxrate2']");
	
	private List<String> tableHeaders = new ArrayList<>();
	private Map<Integer, Map<String, String>> tableData = new LinkedHashMap<>();

	public By objAddTopOffSubsidy(int index) {
		return By.xpath("(//i[@class='fa fa-plus-circle fa-2x primary-color addBtn'])[" + index + "]");
	}
	public By objDeleteTopOffSubsidy(int index) {
		return By.xpath("(//i[@class='fa fa-minus-circle fa-2x danger-color delBtnSubsidy'])[" + index + "]");
	}

	public By objAddRollOverSubsidy(int index) {
		return By.xpath("(//i[@class='fa fa-plus-circle fa-2x primary-color addBtnrolloverSubsidy'])[" + index + "]");
	}

	public By objDeleteRollOverSubsidy(int index) {
		return By.xpath("(//i[@class='fa fa-minus-circle fa-2x danger-color delBtnrolloverSubsidy'])[" + index + "]");
	}

	public By objSubsidyStartDatePickerHead(int index) {
		return By.xpath("(//th[@class='switch'])[" + index + "]");
	}

	public By objTopOffSubsidyStartDatePickerMonthSelection(String month) {
		return By.xpath("(//div[@class='datepicker-months']//span[contains(text(),'" + month + "')])[2]");
	}

	public By objTopOffSubsidyStartDatePickerYearSelection(int year) {
		return By.xpath("(//span[contains(text()," + year + ")])[2]");
	}
	public By labShowRecord(String index) {
		return By.xpath("//div[@class='ui-iggrid-results'])[" + index + "]");
		
	}
	

	/**
	 * This method is to Select the Required Tab in Location
	 * 
	 * @param tabName
	 */
	public void selectTab(String tabName) {
		try {
			foundation.click(By.xpath("//ul[@class='nav nav-tabs']//li/a[(text()='" + tabName + "')]"));
			foundation.WaitForAjax(Constants.FIFTEEN_SECOND);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public By objTopOffSubsidyColumn(String column) {
		return By.xpath("//table[@id='topoffsubsidy']//th/b[text()='" + column + "']");
	}

	public By objRollOverSubsidyColumn(String column) {
		return By.xpath("//table[@id='rolloversubsidy']//th/b[text()='" + column + "']");
	}

	public By objectTopOffCalendarMonthAutoLocation1(String month) {
		return By.xpath("/html/body/div[10]/div[1]/table/thead/tr[1]/th[contains(text(),'" + month + "')]");
	}

	public By objectTopOffCalendarMonthAutoLocation2(String month) {
		return By.xpath("/html/body/div[5]/div[1]/table/thead/tr[1]/th[contains(text(),'" + month + "')]");
	}

	public By objectTopOffCalendarMonthAutomationNew(String month) {
		return By.xpath("/html/body/div[5]/div[1]/table/thead/tr[1]/th[contains(text(),'" + month + "')]");
	}

	public By objectTopOffCalendarMonthAutomationLocation1(String month) {
		return By.xpath("/html/body/div[10]/div[1]/table/thead/tr[1]/th[contains(text(),'" + month + "')]");
	}

	public By objectTopOffCalendarDayAutomationLocation1(String day) {
		return By.xpath("/html/body/div[10]/div[1]/table/tbody/tr/td[text()='" + day + "' and @class=\"day  active\"]");
	}

	public By objectTopOffCalendarNewDayAutomationLocation1(String day) {
		return By.xpath("/html/body/div[10]/div[1]/table/tbody/tr/td[text()='" + day + "' and @class=\"day \"]");
	}

	public By objectTopOffCalendarNewDayAutoLocation1(String day) {
		return By.xpath("/html/body/div[10]/div[1]/table/tbody/tr/td[text()='" + day + "' and @class=\"day \"]");
	}

	public By objectTopOffCalendarDayAutoLocation1(String day) {
		return By.xpath("/html/body/div[10]/div[1]/table/tbody/tr/td[text()='" + day + "' and @class=\"day  active\"]");
	}

	public By objectTopOffCalendarNewDayAutoLocation2(String day) {
		return By.xpath("/html/body/div[5]/div[1]/table/tbody/tr/td[text()='" + day + "' and @class=\"day \"]");
	}

	public By objectTopOffCalendarNewDayAutomationnew(String day) {
		return By.xpath("/html/body/div[5]/div[1]/table/tbody/tr/td[text()='" + day + "' and @class=\"day \"]");
	}

	public By objectTopOffCalendarDayAutoLocation2(String day) {
		return By.xpath("/html/body/div[5]/div[1]/table/tbody/tr/td[text()='" + day + "' and @class=\"day  active\"]");
	}

	public By objectTopOffCalendarDayAutomationnew(String day) {
		return By.xpath("/html/body/div[5]/div[1]/table/tbody/tr/td[text()='" + day + "' and @class=\"day  active\"]");
	}

	public By objectRollOverCalendarDayLocation(String day) {
		return By.xpath("/html/body/div[7]/div[1]/table/tbody/tr/td[text()='" + day + "' and @class=\"day  active\"]");
	}

	public By objectRollOverCalendarDayLocation1(String day) {
		return By.xpath("/html/body/div[12]/div[1]/table/tbody/tr/td[text()='" + day + "' and @class=\"day  active\"]");
	}

	public By objectRollOverCalendarMonthLocation(String month) {
		return By.xpath("/html/body/div[7]/div[1]/table/thead/tr[1]/th[contains(text(),'" + month + "')]");
	}

	public By objectRollOverCalendarMonthLocation1(String month) {
		return By.xpath("/html/body/div[12]/div[1]/table/thead/tr[1]/th[contains(text(),'" + month + "')]");
	}

	public By objectRollOverCalendarNewDayLocation(String day) {
		return By.xpath("/html/body/div[7]/div[1]/table/tbody/tr/td[text()='" + day + "' and @class=\"day \"]");
	}

	public By objectRollOverCalendarNewDayLocation1(String day) {
		return By.xpath("/html/body/div[12]/div[1]/table/tbody/tr/td[text()='" + day + "' and @class=\"day \"]");
	}

	public By objectRollOverCalendarMonth(String month) {
		return By.xpath("/html/body/div[12]/div[1]/table/thead/tr[1]/th[contains(text(),'" + month + "')]");
	}

	public By objectRollOverCalendarDay(String day) {
		return By.xpath("/html/body/div[12]/div[1]/table/tbody/tr/td[contains(text(),'" + day + "')]");
	}

	public By objectProduct(String product) {
		return By.xpath("//td[text()='" + product + "']");
	}

	public By tableData(String column) {
		return By.xpath("//table[@id='promoGrid']//th//span[text()='" + column + "']");
	}

	public By manageColumnPopup(String column) {
		return By.xpath("//div[@id='promoGrid_hiding_modalDialog_content']//li//span[text()='" + column + "']");
	}
	public By selectRecordProduct(String data) {
		return By.xpath("//div[@id='productDataGrid_editor_list']//span[text()='"+ data + "']");
				
	}
	public By selectRecordInventory(String data) {
		return By.xpath("//div[@id='inventoryDataGrid_editor_list']//span[text()='"+ data + "']");
				
	}

	/**
	 * Managing the column
	 * 
	 * @param columnNames
	 */
	public void manageColumn(String columnNames) {
		try {
			foundation.click(BTN_MANAGE_COLUMNS);
			List<String> columnName = Arrays.asList(columnNames.split(Constants.DELIMITER_HASH));
			int columnCount = columnName.size();
			for (int count = 0; count < columnCount; count++) {
				String status = foundation.getText(By.xpath(
						"//div[@id='productDataGrid_hiding_modalDialog_content']/ul//li/span[@class='ui-iggrid-dialog-text'][text()='"
								+ columnName.get(count) + "']//..//a"));
				if (!status.equalsIgnoreCase(Constants.HIDE))
					foundation.click(By.xpath(
							"//div[@id='productDataGrid_hiding_modalDialog_content']/ul//li/span[@class='ui-iggrid-dialog-text'][text()='"
									+ columnName.get(count) + "']"));
			}
			foundation.objectFocus(POP_UP_BTN_APPLY);
			foundation.click(DLG_PRODUCT_COLUMN_CHOOSER_FOOTER);
			foundation.threadWait(Constants.TWO_SECOND);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * Getting the Product Headers
	 * 
	 * @return
	 */
	public List<String> getProductsHeaders() {
		List<String> tableHeaders = new ArrayList<>();
		try {
			WebElement tableProducts = getDriver().findElement(TBL_PRODUCTS);
			List<WebElement> columnHeaders = tableProducts
					.findElements(By.cssSelector("thead > tr > th > span.ui-iggrid-headertext"));
			for (WebElement columnHeader : columnHeaders) {
				tableHeaders.add(columnHeader.getText());
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		return tableHeaders;
	}

	/**
	 * Getting the Product Record
	 * 
	 * @param recordValue
	 * @return
	 */
	public Map<Integer, Map<String, String>> getProductsRecords(String recordValue) {
		Map<Integer, Map<String, String>> productsData = new LinkedHashMap<>();
		int recordCount = 0;
		try {
			foundation.waitforElement(TBL_PRODUCTS_GRID, 5);
			List<String> tableHeaders = getProductsHeaders();
			foundation.waitforElement(TXT_PRODUCT_FILTER, 2);
			textBox.enterText(TXT_PRODUCT_FILTER, recordValue);
			WebElement tableProductsGrid = getDriver().findElement(TBL_PRODUCTS_GRID);
			List<WebElement> rows = tableProductsGrid.findElements(By.tagName("tr"));
			for (WebElement row : rows) {
				Map<String, String> productsRecord = new LinkedHashMap<>();
				for (int columnCount = 1; columnCount < tableHeaders.size() + 1; columnCount++) {
					WebElement column = row.findElement(By.cssSelector("td:nth-child(" + columnCount + ")"));
					productsRecord.put(tableHeaders.get(columnCount - 1), column.getText());
				}
				productsData.put(recordCount, productsRecord);
				recordCount++;
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		return productsData;
	}

	/**
	 * Showing the Tax Category and Apply the
	 *
	 */
	public void showTaxCategory() {
		try {
			foundation.click(BTN_MANAGE_COLUMNS);
			foundation.click(BTN_SHOW);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		foundation.click(BTN_APPLY);
	}

	/**
	 * Return By Object for Table
	 * 
	 * @param homeCommercial
	 * @return
	 */
	public By objTable(String homeCommercial) {
		return By.xpath("//td[text()='" + homeCommercial + "']");
	}

	/**
	 * Verify the Has Locker Field
	 * 
	 * @param defaultValue
	 */
	public void verifyHasLockerField(String defaultValue) {
		try {
			foundation.waitforElement(LBL_LOCATION_SUMMARY, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(TXT_HAS_LOCKERS));
			String value = dropDown.getSelectedItem(DPD_HAS_LOCKER);
			CustomisedAssert.assertEquals(value, defaultValue);
			ExtFactory.getInstance().getExtent().log(Status.INFO,
					"Validated the has Locker default Value" + defaultValue);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * Verify Has Order ahead field in Location Summary Page
	 * 
	 * @param defaultValue
	 */
	public void verifyHasOrderAheadField(String defaultValue) {
		try {
			foundation.waitforElement(LBL_LOCATION_SUMMARY, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LBL_ORDER_AHEAD));
			String value = dropDown.getSelectedItem(DPD_HAS_ORDER_AHEAD);
			CustomisedAssert.assertEquals(value, defaultValue);
			ExtFactory.getInstance().getExtent().log(Status.INFO,
					"Validated the has Locker default Value" + defaultValue);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * Verify TopOff Subsidy UI fields
	 * 
	 * @param values
	 */
	public void verifyTopOffSubsidy(List<String> values) {
		try {
			foundation.waitforElement(TXT_TOP_OFF_SUBSIDY, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(TXT_TOP_OFF_SUBSIDY));
			CustomisedAssert.assertTrue(foundation.isDisplayed(objTopOffSubsidyColumn(values.get(2))));
			CustomisedAssert.assertTrue(foundation.isDisplayed(objTopOffSubsidyColumn(values.get(3))));
			CustomisedAssert.assertTrue(foundation.isDisplayed(objTopOffSubsidyColumn(values.get(4))));
			CustomisedAssert.assertTrue(foundation.isDisplayed(objTopOffSubsidyColumn(values.get(5))));
			CustomisedAssert.assertTrue(foundation.isDisplayed(objTopOffSubsidyColumn(values.get(6))));
			ExtFactory.getInstance().getExtent().log(Status.INFO,
					"Validated the TOP Off Subsidy default Value" + values);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * Verify RollOver Subsidy UI fields
	 * 
	 * @param columns
	 */
	public void verifyRolloverSubsidy(List<String> columns) {
		try {
			foundation.waitforElement(TXT_ROLL_OVER_SUBSIDY, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(TXT_ROLL_OVER_SUBSIDY));
			CustomisedAssert.assertTrue(foundation.isDisplayed(objRollOverSubsidyColumn(columns.get(2))));
			CustomisedAssert.assertTrue(foundation.isDisplayed(objRollOverSubsidyColumn(columns.get(3))));
			CustomisedAssert.assertTrue(foundation.isDisplayed(objRollOverSubsidyColumn(columns.get(4))));
			CustomisedAssert.assertTrue(foundation.isDisplayed(objRollOverSubsidyColumn(columns.get(5))));
			CustomisedAssert.assertTrue(foundation.isDisplayed(objRollOverSubsidyColumn(columns.get(6))));
			ExtFactory.getInstance().getExtent().log(Status.INFO, "Validated the Roll Over Subsidy Value" + columns);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * Get the Product Names
	 * 
	 * @return
	 */
	public List<String> getProductsNames() {
		List<String> productNames = new LinkedList<>();
		WebElement tableProductsGrid = getDriver().findElement(TBL_PRODUCTS_GRID);
		List<WebElement> records = tableProductsGrid.findElements(By.tagName("tr"));
		for (int iter = 1; iter > records.size() + 1; iter++) {
			productNames.add(foundation.getText(By.xpath("//table[@id='productDataGrid']/tbody/tr/td[" + iter
					+ "][@aria-describedby='productDataGrid_name']")));
		}
		return productNames;
	}

	/**
	 * Update the Locker Settings
	 * 
	 * @param enableORDisable
	 */
	public void updateLockerSettings(String enableORDisable) {
		dropDown.selectItem(DPD_HAS_LOCKER, enableORDisable, Constants.TEXT);
		foundation.click(BTN_SAVE);
		foundation.waitforElement(LocationList.DPD_LOCATION_LIST, Constants.SHORT_TIME);
	}

	/**
	 * Enter the Price
	 * 
	 * @param scancode
	 * @param price
	 */
	public void enterPrice(String scancode, String price) {
		By priceLink = By.xpath("//td[text()='" + scancode + "']//..//td[@aria-describedby='productDataGrid_price']");
		By priceInput = By
				.xpath("//td[text()='" + scancode + "']//..//td[@aria-describedby='productDataGrid_price']//input");
		foundation.click(priceLink);
		textBox.enterText(priceInput, Keys.CONTROL + "a" + Keys.BACK_SPACE);
		textBox.enterText(priceInput, price);
		ExtFactory.getInstance().getExtent().log(Status.INFO, "updated price is" + foundation.getText(priceLink));
	}

	/**
	 * Enter minimum Stock Value
	 * 
	 * @param scancode
	 * @param min
	 */
	public void enterMinStock(String scancode, String min) {
		By minLink = By.xpath("//td[text()='" + scancode + "']//..//td[@aria-describedby='productDataGrid_minstock']");
		By minInput = By
				.xpath("//td[text()='" + scancode + "']//..//td[@aria-describedby='productDataGrid_minstock']//input");
		foundation.click(minLink);
		textBox.enterText(minInput, Keys.CONTROL + "a" + Keys.BACK_SPACE);
		textBox.enterText(minInput, min);
		ExtFactory.getInstance().getExtent().log(Status.INFO, "updated Min Stock is" + foundation.getText(minLink));
	}

	/**
	 * Enter maximum Stock Value
	 * 
	 * @param scancode
	 * @param max
	 */
	public void enterMaxStock(String scancode, String max) {
		By maxLink = By.xpath("//td[text()='" + scancode + "']//..//td[@aria-describedby='productDataGrid_maxstock']");
		By maxInput = By
				.xpath("//td[text()='" + scancode + "']//..//td[@aria-describedby='productDataGrid_maxstock']//input");
		foundation.click(maxLink);
		textBox.enterText(maxInput, Keys.CONTROL + "a" + Keys.BACK_SPACE);
		textBox.enterText(maxInput, max);
		ExtFactory.getInstance().getExtent().log(Status.INFO, "updated Max Stock is" + foundation.getText(maxLink));
	}

	/**
	 * Select PickList
	 * 
	 * @param scancode
	 * @param option
	 */
	public void selectPickList(String scancode, String option) {
		By pickLink = By.xpath("//td[text()='" + scancode + "']//..//td[@aria-describedby='productDataGrid_planning']");
		foundation.click(pickLink);
		foundation.click(By.xpath("//div[@class='ui-igcombo-buttonicon ui-icon-triangle-1-s ui-icon']"));
		foundation.threadWait(Constants.TWO_SECOND);
		foundation.click(By.xpath("//li[text()='" + option + "']"));
	}

	/**
	 * Add the Product using Scancode
	 * 
	 * @param scancode
	 */
	public void addProduct(String scancode) {
		foundation.click(BTN_ADD_PRODUCT);
		foundation.waitforElement(TXT_ADD_PRODUCT_SEARCH, 3);
		textBox.enterText(TXT_ADD_PRODUCT_SEARCH, scancode);
		foundation.click(By.xpath("//td[@aria-describedby='chooseprddt_scancode'][text()='" + scancode + "']"));
		foundation.click(BTN_ADD_PRODUCT_ADD);
		foundation.waitforElement(BTN_SAVE, 3);
	}

	/**
	 * Select Particular Device by its Name
	 * 
	 * @param deviceName
	 */
	public void selectDeviceName(String deviceName) {
		foundation.click(By.xpath("//a[text()='" + deviceName + "']"));
	}

	/**
	 * Add Home Commercial in Location Summary Page
	 * 
	 * @param imageName
	 * @param imagePath
	 */
	public void addHomeCommercial(String imageName, String imagePath) {
		foundation.waitforElement(BTN_HOME_COMMERCIAL, Constants.SHORT_TIME);
		foundation.click(BTN_HOME_COMMERCIAL);
		foundation.click(BTN_ADD_HOME_COMMERCIAL);
		foundation.click(TXT_UPLOAD_NEW);
		textBox.enterText(BTN_UPLOAD_INPUT, imagePath);
		textBox.enterText(TXT_ADD_NAME, imageName);
		foundation.click(BTN_ADD);
		foundation.click(BTN_SYNC);
		foundation.isDisplayed(LBL_SPINNER_MSG);
		foundation.waitforElement(Login.LBL_USER_NAME, Constants.SHORT_TIME);
		foundation.click(BTN_SAVE);
	}

	/**
	 * Remove Home Commercial in Location Summary Page
	 * 
	 * @param imageName
	 */
	public void removeHomeCommercial(String imageName) {
		foundation.waitforElement(BTN_HOME_COMMERCIAL, Constants.SHORT_TIME);
		foundation.click(BTN_HOME_COMMERCIAL);
		textBox.enterText(TXT_CMR_FILTER, imageName);
		foundation.click(objTable(imageName));
		foundation.waitforElement(BTN_REMOVE, Constants.SHORT_TIME);
		foundation.click(BTN_REMOVE);
		foundation.waitforElement(BTN_SYNC, Constants.SHORT_TIME);
		foundation.click(BTN_SYNC);
		foundation.isDisplayed(LBL_SPINNER_MSG);
		foundation.waitforElement(Login.LBL_USER_NAME, Constants.SHORT_TIME);
		foundation.refreshPage();
	}

	/**
	 * Updating the Inventory of the product
	 * 
	 * @param scancode
	 * @param inventoryValue
	 * @param reasonCode
	 */
	public void updateInventory(String scancode, String inventoryValue, String reasonCode) {
		foundation.waitforElement(By.xpath("//td[@aria-describedby='inventoryDataGrid_scancode'][text()='" + scancode
				+ "']//..//td[@aria-describedby='inventoryDataGrid_qtyonhand']"), Constants.SHORT_TIME);
		foundation.objectClick(By.xpath("//td[@aria-describedby='inventoryDataGrid_scancode'][text()='" + scancode
				+ "']//..//td[@aria-describedby='inventoryDataGrid_qtyonhand']"));
//		foundation.threadWait(Constants.TWO_SECOND);
		foundation.waitforElement(
				By.xpath("//td[@aria-describedby='inventoryDataGrid_scancode'][text()='" + scancode
						+ "']//..//td[@aria-describedby='"
						+ "DataGrid_qtyonhand']/div/div/span/input"),
				Constants.TWO_SECOND);
		textBox.enterText(
				By.xpath("//td[@aria-describedby='inventoryDataGrid_scancode'][text()='" + scancode
						+ "']//..//td[@aria-describedby='inventoryDataGrid_qtyonhand']/div/div/span/input"),
				inventoryValue);
//		foundation.threadWait(Constants.TWO_SECOND);
		foundation.click(By.xpath("//td[@aria-describedby='inventoryDataGrid_scancode'][text()='" + scancode
				+ "']//..//td[@aria-describedby='inventoryDataGrid_reasoncode']/span/div"));
		foundation.waitforElement(By.xpath("//ul[@class='ui-igcombo-listitemholder']/li[text()='" + reasonCode + "']"),
				Constants.TWO_SECOND);
		foundation.click(By.xpath("//ul[@class='ui-igcombo-listitemholder']/li[text()='" + reasonCode + "']"));

		foundation.objectClick(CLEAR_INVENTORY_FILTER);
		foundation.waitforElement(CLEAR_INVENTORY_FILTER, Constants.TWO_SECOND);
	}

	/**
	 * Return By Object for Upload Status
	 * 
	 * @param uploadMessage
	 * @return
	 */
	public By objUploadStatus(String uploadMessage) {
		return By.xpath("//a[text()='" + uploadMessage + "']");
	}

	/**
	 * Setting up Kiosk Language in Location Summary Page and do Full Sync
	 * 
	 * @param location
	 * @param defaultLanguage
	 * @param altLanguage
	 */
	public void kiosklanguageSetting(String location, String defaultLanguage, String altLanguage) {
		locationList.selectLocationName(location);
		foundation.waitforElement(DPD_KIOSK_LANGUAGE, Constants.SHORT_TIME);
		dropDown.selectItem(DPD_KIOSK_LANGUAGE, defaultLanguage, Constants.TEXT);
		dropDown.selectItem(DPD_ALTERNATE_LANGUAGE, altLanguage, Constants.TEXT);
		foundation.click(BTN_SYNC);
		foundation.click(BTN_SAVE);
		foundation.waitforElementToDisappear(LocationList.TXT_SPINNER_MSG, Constants.EXTRA_LONG_TIME);
		foundation.waitforClikableElement(Login.LBL_USER_NAME, Constants.EXTRA_LONG_TIME);
		browser.close();
	}

	/**
	 * Get the Product Details
	 * 
	 * @param name
	 * @return
	 */
	public Map<String, String> getProductDetails(String name) {
		Map<String, String> productsRecord = new LinkedHashMap<>();
		try {
			List<String> tableHeaders = getProductsHeaders();
			for (int columnCount = 1; columnCount < tableHeaders.size() + 1; columnCount++) {
				WebElement column = getDriver().findElement(By.xpath("//table[@id='productDataGrid']//tr//span[text()='"
						+ name + "']//..//..//..//..//tbody//td[" + columnCount + "]"));
				productsRecord.put(tableHeaders.get(columnCount - 1), column.getText());
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		return productsRecord;
	}

	/**
	 * Save TaxMapping
	 * 
	 * @param taxCategory
	 * @param rate
	 */
	public void saveTaxMapping(String taxCategory, String rate) {
		foundation.click(TAB_TAX_MAPPING);
		textBox.enterText(TXT_SEARCH_TAX_MAPPING, taxCategory);
		if (foundation.isDisplayed(objTaxCategory(taxCategory)) == false) {
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(BTN_ADD_MAPPING);
			foundation.waitforElement(DPD_TAXCAT, Constants.SHORT_TIME);
			dropDown.selectItem(DPD_TAXCAT, taxCategory, Constants.TEXT);
			dropDown.selectItem(DPD_RATE, rate, Constants.TEXT);
			foundation.click(BTN_POPUP_SAVE);
			foundation.click(TAB_TAX_MAPPING);
		}
	}

	/**
	 * Remove TaxMapping
	 * 
	 * @param taxCategory
	 */
	public void removeTaxMapping(String taxCategory) {
		foundation.click(TAB_TAX_MAPPING);
		textBox.enterText(TXT_SEARCH_TAX_MAPPING, taxCategory);
		foundation.click(objTaxCategory(taxCategory));
		foundation.waitforElement(BTN_POPUP_REMOVE, Constants.SHORT_TIME);
		foundation.click(BTN_POPUP_REMOVE);
		foundation.click(TAB_TAX_MAPPING);
	}

	/**
	 * Return By Object for Tax Rate
	 * 
	 * @param taxRate
	 * @return
	 */
	public By objVerifyTaxRate(String taxRate) {
		return By.xpath("//table[@id='taxmapdt']//tr/td[text()='" + taxRate + "']");
	}

	/**
	 * Return By Object for Product Price
	 * 
	 * @param productName
	 * @return
	 */
	public By objProductPrice(String productName) {
		return By.xpath("//td[text()='" + productName + "']//..//td[@aria-describedby='productDataGrid_price']");
	}

	/**
	 * Getting Text Attribute value
	 * 
	 * @param object
	 * @param attribute
	 * @return
	 */
	public String getTextAttribute(By object, String attribute) {
		String textAttribute = null;
		try {
			textAttribute = getDriver().findElement(object).getAttribute(attribute);
			if (ExtFactory.getInstance().getExtent() != null) {
				ExtFactory.getInstance().getExtent().log(Status.INFO, object + " value is " + textAttribute);
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		return textAttribute;
	}

	/**
	 * Verify DropDown Value
	 * 
	 * @param text
	 * @return
	 */
	public Boolean verifyDPDValue(String text) {
		Boolean flag = false;
		WebElement drpdwn = getDriver().findElement(DPD_VDI_PROVDIER);
		Select dpdSel = new Select(drpdwn);
		List<WebElement> DrpDwnList = dpdSel.getOptions();
		for (WebElement webElement : DrpDwnList) {

			if (webElement.getText().contains(text)) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	/**
	 * Return By Object Print Group
	 * 
	 * @param text
	 * @return
	 */
	public By objPrintGroup(String text) {
		return By.xpath("//li[@data-value='" + text + "']");
	}

	/**
	 * Show or Hide in Manage Column
	 * 
	 * @param showOrHide
	 * @param columnName
	 */
	public void showHideManageColumn(String showOrHide, String columnName) {
		By xpathHideOrShow = By
				.xpath("//div[@id='productDataGrid_hiding_modalDialog']//span[text()='" + columnName + "']//..//a");
		String hideOrShow = foundation.getText(xpathHideOrShow);
		if (showOrHide.equals(Constants.SHOW)) {
			if (hideOrShow.equals(Constants.SHOW)) {
				foundation.click(xpathHideOrShow);
			}
		} else {
			if (hideOrShow.equals(Constants.HIDE)) {
				foundation.click(xpathHideOrShow);
			}
		}
	}

	/**
	 * Get the Data from Cell
	 * 
	 * @param ariaDescribedby
	 * @return
	 */
	public String getCellData(String ariaDescribedby) {
		foundation.waitforElement(By.xpath("//tr[@role='row']//td[@aria-describedby='" + ariaDescribedby + "']"),
				Constants.EXTRA_LONG_TIME);
		return foundation.getText(By.xpath("//tr[@role='row']//td[@aria-describedby='" + ariaDescribedby + "']"));
	}

	/**
	 * Return Object Tax category
	 * 
	 * @param taxCategory
	 * @return
	 */
	public By objTaxCategory(String taxCategory) {
		return By.xpath("//table[@id='taxmapdt']//*[text()='" + taxCategory + "']");
	}

	/**
	 * Add Pay Cycle
	 * 
	 * @param location
	 * @param payCycle
	 */
	public void addPaycyle(String location, String payCycle) {
		locationList.selectLocationName(location);
		int totalPaycycleRows = foundation
				.getSizeofListElement(By.xpath("//*[@id='payRollRange']//*[@class='btn-mini pull-right']//i"));
		List<String> existingPaycycles = textBox
				.getValueofListElement(By.xpath("//*[contains(@class,'paycycle-grpname')]"));
		if (!existingPaycycles.contains(payCycle)) {
			foundation.click(By
					.xpath("(//*[@id='payRollRange']//*[@class='btn-mini pull-right']//i)[" + totalPaycycleRows + "]"));
			textBox.enterText(TXT_PAY_CYCLE_NAME, payCycle);
		}
		foundation.click(BTN_SAVE);
		foundation.waitforElementToDisappear(LocationList.TXT_SPINNER_MSG, Constants.EXTRA_LONG_TIME);
		foundation.waitforClikableElement(Login.LBL_USER_NAME, Constants.EXTRA_LONG_TIME);
	}

	/**
	 * Add new Pay Cycle
	 * 
	 * @param location
	 * @param payCycle
	 * @param yesORno
	 */
	public void addPaycylenew(String location, String payCycle, String yesORno) {
		locationList.selectLocationName(location);
		dropDown.selectItem(DPD_PAYROLL_DEDUCT, yesORno, Constants.TEXT);
		foundation.threadWait(Constants.SHORT_TIME);
		int totalPaycycleRows = foundation
				.getSizeofListElement(By.xpath("//*[@id='payRollRange']//*[@class='btn-mini pull-right']//i"));
		List<String> existingPaycycles = textBox
				.getValueofListElement(By.xpath("//*[contains(@class,'paycycle-grpname')]"));
		if (!existingPaycycles.contains(payCycle)) {
			foundation.click(By
					.xpath("(//*[@id='payRollRange']//*[@class='btn-mini pull-right']//i)[" + totalPaycycleRows + "]"));
			textBox.enterText(TXT_PAY_CYCLE_NAME, payCycle);
		}
		foundation.click(BTN_SAVE);
		foundation.waitforElementToDisappear(LocationList.TXT_SPINNER_MSG, Constants.EXTRA_LONG_TIME);
		foundation.waitforClikableElement(Login.LBL_USER_NAME, Constants.EXTRA_LONG_TIME);
	}

	/**
	 * Delete Pay Cycle
	 * 
	 * @param location
	 * @param payCycle
	 */
	public void deletePaycyle(String location, String payCycle) {
		locationList.selectLocationName(location);
		int totalPaycycleRows = foundation
				.getSizeofListElement(By.xpath("//button[@class='btn-mini']//i[contains(@class,'delBtn ')]"));
		for (int i = 1; i <= totalPaycycleRows; i++) {
			if (textBox.getTextFromInput(By.xpath("(//*[contains(@class,'paycycle-grpname')])[" + i + "]"))
					.contains(payCycle)) {
				foundation.click(By.xpath("(//button[@class='btn-mini']//i[contains(@class,'delBtn ')])[" + i + "]"));
				foundation.threadWait(Constants.ONE_SECOND);
				break;
			}
		}
		foundation.click(BTN_SAVE);
		foundation.waitforElementToDisappear(LocationList.TXT_SPINNER_MSG, Constants.EXTRA_LONG_TIME);
		foundation.waitforClikableElement(Login.LBL_USER_NAME, Constants.EXTRA_LONG_TIME);
	}

	/**
	 * Turning On or Off PayRoll Deduct
	 * 
	 * @param location
	 * @param yesORno
	 */
	public void turnOnOROffPayRollDeduct(String location, String yesORno) {
		locationList.selectLocationName(location);
		foundation.threadWait(Constants.THREE_SECOND);
		dropDown.selectItem(DPD_PAYROLL_DEDUCT, yesORno, Constants.TEXT);
		foundation.click(BTN_SAVE);
		foundation.waitforElementToDisappear(LocationList.TXT_SPINNER_MSG, Constants.EXTRA_LONG_TIME);
		foundation.waitforClikableElement(Login.LBL_USER_NAME, Constants.EXTRA_LONG_TIME);
	}

	/**
	 * Edit the Pay Cycle
	 * 
	 * @param location
	 * @param payCycle
	 * @param updatedPaycycle
	 */
	public void editPaycyle(String location, String payCycle, String updatedPaycycle) {
		locationList.selectLocationName(location);
		int totalPaycycleRows = foundation
				.getSizeofListElement(By.xpath("//*[@id='payRollRange']//*[@class='btn-mini pull-right']//i"));
		for (int i = 1; i <= totalPaycycleRows; i++) {
			if (textBox.getTextFromInput(By.xpath("(//*[contains(@class,'paycycle-grpname')])[" + i + "]"))
					.contains(payCycle)) {
				textBox.enterText(By.xpath("(//input[contains(@class,'paycycle-grpname')])[" + i + "]"),
						updatedPaycycle);
				foundation.threadWait(Constants.ONE_SECOND);
			}
		}
		foundation.click(BTN_SAVE);
		foundation.waitforElementToDisappear(LocationList.TXT_SPINNER_MSG, Constants.EXTRA_LONG_TIME);
		foundation.waitforClikableElement(Login.LBL_USER_NAME, Constants.EXTRA_LONG_TIME);
	}

	/**
	 * Edit the new Pay Cycle
	 * 
	 * @param location
	 * @param payCycle
	 * @param updatedPaycycle
	 * @param yesORno
	 */
	public void editPaycylenew(String location, String payCycle, String updatedPaycycle, String yesORno) {
		locationList.selectLocationName(location);
		dropDown.selectItem(DPD_PAYROLL_DEDUCT, yesORno, Constants.TEXT);
		foundation.threadWait(Constants.SHORT_TIME);
		int totalPaycycleRows = foundation
				.getSizeofListElement(By.xpath("//*[@id='payRollRange']//*[@class='btn-mini pull-right']//i"));
		for (int i = 1; i <= totalPaycycleRows; i++) {
			if (textBox.getTextFromInput(By.xpath("(//*[contains(@class,'paycycle-grpname')])[" + i + "]"))
					.contains(payCycle)) {
				textBox.enterText(By.xpath("(//input[contains(@class,'paycycle-grpname')])[" + i + "]"),
						updatedPaycycle);
				foundation.threadWait(Constants.ONE_SECOND);
			}
		}
		foundation.click(BTN_SAVE);
		foundation.waitforElementToDisappear(LocationList.TXT_SPINNER_MSG, Constants.EXTRA_LONG_TIME);
		foundation.waitforClikableElement(Login.LBL_USER_NAME, Constants.EXTRA_LONG_TIME);
	}

	/**
	 * Get the Column Values
	 * 
	 * @param columnData
	 * @return
	 */
	public List<String> getColumnValues(By columnData) {
		String text = null;
		List<String> elementsText = new ArrayList<String>();
		try {
			List<WebElement> ListElement = getDriver().findElements(columnData);

			for (int i = 0; i < ListElement.size(); i++) {
				text = ListElement.get(i).getText();
				elementsText.add(text);
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		return elementsText;
	}

	/**
	 * Verify Sort in Ascending
	 * 
	 * @param columnData
	 * @return
	 */
	public Boolean verifySortAscending(By columnData) {
		boolean ascending = false;
		try {
			List<String> listRuleNameAscending = getColumnValues(columnData);

			ascending = listRuleNameAscending.stream().sorted(Comparator.naturalOrder())
					.sorted(String.CASE_INSENSITIVE_ORDER).collect(Collectors.toList()).equals(listRuleNameAscending);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		return ascending;
	}

	/**
	 * Verify Sort in Descending
	 * 
	 * @param columnData
	 * @return
	 */
	public boolean verifySortDescending(By columnData) {
		boolean descending = false;
		try {
			List<String> listRuleNameDescending = getColumnValues(columnData);
			descending = listRuleNameDescending.stream().sorted(Comparator.reverseOrder())
					.sorted(String.CASE_INSENSITIVE_ORDER.reversed()).collect(Collectors.toList())
					.equals(listRuleNameDescending);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		return descending;
	}

	/**
	 * Return By Object for Column Headers
	 * 
	 * @param columnName
	 * @return
	 */
	public By objColumnHeaders(String columnName) {
		return By.xpath("//table[@id='productDataGrid']//span[text()='" + columnName + "']");
	}

	/**
	 * Return By Object of Device selected
	 * 
	 * @param deviceName
	 * @return
	 */
	public By objDevice(String deviceName) {
		return By.xpath("//div[@class='ig-tree-text' and text()='" + deviceName + "']");
	}

	/**
	 * Select the Device
	 * 
	 * @param deviceName
	 */
	public void selectDevice(String deviceName) {
		foundation.click(By.xpath("//*[@id='choosekskdt']/tbody//div[text()='" + deviceName + "']"));
	}

	/**
	 * Return By Object for Device Name
	 * 
	 * @param devicename
	 * @return
	 */
	public By deviceName(String devicename) {
		return By.xpath("//a[text()='" + devicename + "']");
	}

	/**
	 * Remove the device from Location
	 * 
	 * @param device
	 */
	public void removeDevice(String device) {
		foundation.waitforElement(TXT_DEVICE_SEARCH, Constants.SHORT_TIME);
		textBox.enterText(TXT_DEVICE_SEARCH, device);
		foundation.click(BTN_LNK_DEVICE_SUMMARY);
		foundation.click(BTN_REMOVE_DEVICE);
		foundation.click(BTN_YES_REMOVE);
		foundation.navigateToBackPage();
	}

	/**
	 * Navigate to Location and Add Tax Map
	 * 
	 * @param locationName
	 * @param taxCategory
	 * @param taxRateName
	 */
	public void navigateAndAddTaxMap(String locationName, String taxCategory, String taxRateName) {
		navigationBar.navigateToMenuItem("Location");
		locationList.selectLocationName(locationName);
		saveTaxMapping(taxCategory, taxRateName);
	}

	/**
	 * Navigate to Location Menu and Removing the Tax mapped on Location
	 * 
	 * @param locationName
	 * @param taxCategory
	 */
	public void navigateAndRemoveTaxMap(String locationName, String taxCategory) {
		navigationBar.navigateToMenuItem("Location");
		locationList.selectLocationName(locationName);
		removeTaxMapping(taxCategory);
	}

	/**
	 * Resetting the Inventory value
	 * 
	 * @param scancode
	 * @param inventory
	 */
	public void resetInventory(String scancode, String inventory) {
		By inventoryLink = By
				.xpath("//td[text()='" + scancode + "']//..//td[@aria-describedby='inventoryDataGrid_qtyonhand']");
		By inventoryValue = By.xpath(
				"//td[text()='" + scancode + "']//..//td[@aria-describedby='inventoryDataGrid_qtyonhand']//input");
		foundation.click(inventoryLink);
		textBox.enterText(inventoryValue, Keys.CONTROL + "a" + Keys.BACK_SPACE);
		textBox.enterText(inventoryValue, inventory);
		ExtFactory.getInstance().getExtent().log(Status.INFO, "updated price is" + foundation.getText(inventoryLink));
	}

	/**
	 * Selecting the Market Card
	 * 
	 * @param locationName
	 * @param ValidateHeading
	 * @param marketCard
	 */
	public void selectingMarketCard(String locationName, String ValidateHeading, String marketCard) {
		// Selecting location
		locationList.selectLocationName(locationName);
		foundation.waitforElement(VALIDATE_HEADING, Constants.SHORT_TIME);
		CustomisedAssert.assertTrue(foundation.getText(VALIDATE_HEADING).equals(ValidateHeading));
		foundation.waitforElement(DPP_MARKET_CARD, Constants.SHORT_TIME);
		dropDown.selectItem(DPP_MARKET_CARD, marketCard, Constants.TEXT);
		foundation.click(BTN_SAVE);
		foundation.waitforElement(LBL_SPINNER_MSG, Constants.SHORT_TIME);
	}

	/**
	 * Selecting the Product
	 * 
	 * @param tab
	 * @param productName
	 * @param scanCode
	 * @param productPrice
	 */
	public void selectingProduct(String tab, String productName, String scanCode, String productPrice) {
		selectTab(tab);
		foundation.threadWait(Constants.TWO_SECOND);
		textBox.enterText(TXT_PRODUCT_FILTER, productName);
		foundation.threadWait(Constants.SHORT_TIME);
		enterPrice(scanCode, productPrice);
		foundation.click(BTN_UPDATE_PRICE);
	}

	/**
	 * Selecting the Product Price and updating the same
	 * 
	 * @param tab
	 * @param productName
	 * @param productPrice
	 */
	public void selectingAndUpdatingProductPrice(String tab, String productName, String productPrice) {
		selectTab(tab);
		foundation.threadWait(Constants.TWO_SECOND);
		textBox.enterText(TXT_PRODUCT_FILTER, productName);
		enterPrice(productName, productPrice);
	}

	/**
	 * Launching the URL with Super User Credentials
	 */
	public void launchingBrowserAndSelectingOrg() {
		browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
		login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
				propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
		navigationBar.selectOrganization(
				propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
	}

	/**
	 * Add the Product and edit the same in Location Summary Page
	 * 
	 * @param tab
	 * @param productName
	 * @param updatedProductName
	 * @param menuItem
	 */
	public void addEditProduct(String tab, String productName, String updatedProductName, String menuItem) {
		selectTab(tab);
		foundation.WaitForAjax(5000);
		foundation.waitforElement(TXT_PRODUCT_FILTER, Constants.SHORT_TIME);
		foundation.threadWait(Constants.EXTRA_LONG_TIME);
		textBox.enterText(TXT_PRODUCT_FILTER, productName);
		foundation.WaitForAjax(10000);
		//CustomisedAssert.assertTrue(foundation.getText(PRODUCT_NAME).equals(productName));
		foundation.click(PRODUCT_NAME);
		foundation.waitforElement(BTN_EDIT_PRODUCT, Constants.MEDIUM_TIME);
		foundation.click(BTN_EDIT_PRODUCT);
		textBox.enterText(TXT_NAME, updatedProductName);
		foundation.click(BTN_SAVE);
		foundation.threadWait(Constants.TWO_SECOND);
		navigationBar.navigateToMenuItem(menuItem);
	}

	/**
	 * Get Month name of value given
	 * 
	 * @param monthIndex
	 * @return
	 */
	public static String getMonthName(int monthIndex) {
		if (monthIndex > 12) {
			monthIndex = monthIndex - 12;
		}
		Month name = Month.of(monthIndex);
		String result = name.toString().toLowerCase();
		String output = result.substring(0, 1).toUpperCase() + result.substring(1);
		return output;
	}

	/**
	 * Setting Topoff Start Date for AutoLocation1
	 * 
	 * @param value
	 */
	public void verifyTopOffDateAutoLocation1(String value) {
		String dateArray[] = value.split("/");
		String date = dateArray[1].replaceAll(Constants.REMOVE_LEADING_ZERO, "");
		int month = Integer.parseInt(dateArray[0]);
		String monthName = getMonthName(month);
		foundation.threadWait(Constants.ONE_SECOND);
		if (foundation.isDisplayed(objectTopOffCalendarMonthAutoLocation1(monthName))) {
			foundation.click(objectTopOffCalendarDayAutoLocation1(date));
		} else {
			foundation.click(TOP_OFF_DATE_PICKER_NEXT_LOCATION1);
			foundation.waitforElement(objectTopOffCalendarMonthAutoLocation1(monthName), Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(objectTopOffCalendarMonthAutoLocation1(monthName)));
			foundation.click(objectTopOffCalendarNewDayAutoLocation1(date));
		}
	}

	/**
	 * Setting Topoff Start Date as Past date for AutoLocation1
	 * 
	 * @param value
	 */
	public void verifyTopOffDateAsPastDateForAutoLocation1(String value) {
		String dateArray[] = value.split("/");
		String date = dateArray[1].replaceAll(Constants.REMOVE_LEADING_ZERO, "");
		int month = Integer.parseInt(dateArray[0]);
		String monthName = getMonthName(month);
		foundation.threadWait(Constants.ONE_SECOND);
		if (foundation.isDisplayed(objectTopOffCalendarMonthAutoLocation1(monthName))) {
			foundation.click(objectTopOffCalendarNewDayAutoLocation1(date));
		} else {
			foundation.click(TOP_OFF_DATE_PICKER_PREV_LOCATION1);
			foundation.waitforElement(objectTopOffCalendarMonthAutoLocation1(monthName), Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(objectTopOffCalendarMonthAutoLocation1(monthName)));
			foundation.click(objectTopOffCalendarNewDayAutoLocation1(date));
		}
	}

	/**
	 * Setting Topoff Start Date as Future date for AutoLocation1
	 * 
	 * @param value
	 */
	public void verifyTopOffDateAsFutureDateForAutoLocation1(String value, int index, int index1) {
		String dateArray[] = value.split("/");
		String date = dateArray[1].replaceAll(Constants.REMOVE_LEADING_ZERO, "");
		int year = Integer.parseInt(dateArray[2]);
		int month = Integer.parseInt(dateArray[0]);
		String monthName = getMonthName(month);
		String abbrevation = monthName.substring(0, 2);
		foundation.threadWait(Constants.ONE_SECOND);
		foundation.click(objSubsidyStartDatePickerHead(index));
		foundation.threadWait(Constants.ONE_SECOND);
		foundation.click(objSubsidyStartDatePickerHead(index1));
		foundation.threadWait(Constants.ONE_SECOND);
		foundation.click(objTopOffSubsidyStartDatePickerYearSelection(year));
		foundation.threadWait(Constants.ONE_SECOND);
		foundation.click(objTopOffSubsidyStartDatePickerMonthSelection(abbrevation));
		foundation.threadWait(Constants.ONE_SECOND);
		foundation.click(objectTopOffCalendarNewDayAutoLocation1(date));
	}

	/**
	 * Setting Topoff Start Date for AutomationLocation1
	 * 
	 * @param value
	 */
	public void verifyTopOffDateAutomationLocation1(String value) {
		String dateArray[] = value.split("/");
		String date = dateArray[1].replaceAll(Constants.REMOVE_LEADING_ZERO, "");
		int month = Integer.parseInt(dateArray[0]);
		String monthName = getMonthName(month);
		foundation.threadWait(Constants.ONE_SECOND);
		if (foundation.isDisplayed(objectTopOffCalendarMonthAutomationLocation1(monthName))) {
			foundation.click(objectTopOffCalendarDayAutomationLocation1(date));
		} else {
			foundation.click(TOP_OFF_DATE_PICKER_NEXT_AUTOMATION1);
			foundation.waitforElement(objectTopOffCalendarMonthAutomationLocation1(monthName), Constants.SHORT_TIME);
			CustomisedAssert
					.assertTrue(foundation.isDisplayed(objectTopOffCalendarMonthAutomationLocation1(monthName)));
			foundation.click(objectTopOffCalendarNewDayAutomationLocation1(date));
		}
	}

	/**
	 * Setting Topoff Start Date as future date for AutoLocation1
	 * 
	 * @param value
	 */
	public void verifyTopOffFutureDateAutoLocation1(String value) {
		String dateArray[] = value.split("/");
		String date = dateArray[1].replaceAll(Constants.REMOVE_LEADING_ZERO, "");
		int month = Integer.parseInt(dateArray[0]);
		String monthName = getMonthName(month);
		foundation.threadWait(Constants.ONE_SECOND);
		if (foundation.isDisplayed(objectTopOffCalendarMonthAutoLocation1(monthName))) {
			foundation.click(objectTopOffCalendarNewDayAutoLocation1(date));
		} else {
			foundation.click(TOP_OFF_DATE_PICKER_NEXT_LOCATION1);
			foundation.waitforElement(objectTopOffCalendarMonthAutoLocation1(monthName), Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(objectTopOffCalendarMonthAutoLocation1(monthName)));
			foundation.click(objectTopOffCalendarNewDayAutoLocation1(date));
			foundation.click(TXT_GMA_SUBSIDY);
		}
	}

	/**
	 * Setting Topoff Start Date for AutoLocation2
	 * 
	 * @param value
	 */
	public void verifyTopOffDateAutoLocation2(String value) {
		String dateArray[] = value.split("/");
		String date = dateArray[1].replaceAll(Constants.REMOVE_LEADING_ZERO, "");
		int month = Integer.parseInt(dateArray[0]);
		String monthName = getMonthName(month);
		foundation.threadWait(Constants.ONE_SECOND);
		if (foundation.isDisplayed(objectTopOffCalendarMonthAutoLocation2(monthName))) {
			foundation.click(objectTopOffCalendarDayAutoLocation2(date));
		} else {
			foundation.click(TOP_OFF_DATE_PICKER_NEXT_LOCATION2);
			foundation.waitforElement(objectTopOffCalendarMonthAutoLocation2(monthName), Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(objectTopOffCalendarMonthAutoLocation2(monthName)));
			foundation.click(objectTopOffCalendarNewDayAutoLocation2(date));
		}
	}

	/**
	 * Setting Topoff Start Date for AutomationNewLocation
	 * 
	 * @param value
	 */
	public void verifyTopOffDateAutomationNewLocation(String value) {
		String dateArray[] = value.split("/");
		String date = dateArray[1].replaceAll(Constants.REMOVE_LEADING_ZERO, "");
		int month = Integer.parseInt(dateArray[0]);
		String monthName = getMonthName(month);
		foundation.threadWait(Constants.ONE_SECOND);
		if (foundation.isDisplayed(objectTopOffCalendarMonthAutomationNew(monthName))) {
			foundation.click(objectTopOffCalendarDayAutomationnew(date));
		} else {
			foundation.click(TOP_OFF_DATE_PICKER_NEXT_AUTOMATION1);
			foundation.waitforElement(objectTopOffCalendarMonthAutoLocation2(monthName), Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(objectTopOffCalendarMonthAutomationNew(monthName)));
			foundation.click(objectTopOffCalendarNewDayAutomationnew(date));
		}
	}

	/**
	 * Setting Topoff Start Date as Future Date for AutoLocation2
	 * 
	 * @param value
	 */
	public void verifyTopOffFutureDateAutoLocation2(String value) {
		String dateArray[] = value.split("/");
		String date = dateArray[1].replaceAll(Constants.REMOVE_LEADING_ZERO, "");
		int month = Integer.parseInt(dateArray[0]);
		String monthName = getMonthName(month);
		foundation.threadWait(Constants.ONE_SECOND);
		if (foundation.isDisplayed(objectTopOffCalendarMonthAutoLocation2(monthName))) {
			foundation.click(objectTopOffCalendarNewDayAutoLocation2(date));
		} else {
			foundation.click(TOP_OFF_DATE_PICKER_NEXT_LOCATION2);
			foundation.waitforElement(objectTopOffCalendarMonthAutoLocation2(monthName), Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(objectTopOffCalendarMonthAutoLocation2(monthName)));
			foundation.click(objectTopOffCalendarNewDayAutoLocation2(date));
		}
	}

	/**
	 * Setting Rollover Start Date for Create location
	 * 
	 * @param value
	 */
	public void verifyRollOverDateCreateLocation(String value) {
		String dateArray[] = value.split("/");
		String date = dateArray[1].replaceAll(Constants.REMOVE_LEADING_ZERO, "");
		int month = Integer.parseInt(dateArray[0]);
		String monthName = getMonthName(month);
		foundation.threadWait(Constants.ONE_SECOND);
		if (foundation.isDisplayed(objectRollOverCalendarMonthLocation(monthName))) {
			foundation.click(objectRollOverCalendarDayLocation(date));
		} else {
			foundation.click(ROLL_OVER_DATE_PICKER_NEXT_LOCATION2);
			foundation.waitforElement(objectRollOverCalendarMonthLocation(monthName), Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(objectRollOverCalendarMonthLocation(monthName)));
			foundation.click(objectRollOverCalendarNewDayLocation(date));
		}
	}

	/**
	 * Setting Rollover Start Date as Future Date for Create location
	 * 
	 * @param value
	 */
	public void verifyRollOverFutureDateCreateLocation(String value) {
		String dateArray[] = value.split("/");
		String date = dateArray[1].replaceAll(Constants.REMOVE_LEADING_ZERO, "");
		int month = Integer.parseInt(dateArray[0]);
		String monthName = getMonthName(month);
		foundation.threadWait(Constants.ONE_SECOND);
		if (foundation.isDisplayed(objectRollOverCalendarMonthLocation(monthName))) {
			foundation.click(objectRollOverCalendarNewDayLocation(date));
		} else {
			foundation.click(ROLL_OVER_DATE_PICKER_NEXT_LOCATION2);
			foundation.waitforElement(objectRollOverCalendarMonthLocation(monthName), Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(objectRollOverCalendarMonthLocation(monthName)));
			foundation.click(objectRollOverCalendarNewDayLocation(date));
		}
	}

	/**
	 * Setting Rollover Start Date for location1
	 * 
	 * @param value
	 */
	public void verifyRollOverDateLocation1(String value) {
		String dateArray[] = value.split("/");
		String date = dateArray[1].replaceAll(Constants.REMOVE_LEADING_ZERO, "");
		int month = Integer.parseInt(dateArray[0]);
		String monthName = getMonthName(month);
		foundation.threadWait(Constants.ONE_SECOND);
		if (foundation.isDisplayed(objectRollOverCalendarMonthLocation1(monthName))) {
			foundation.click(objectRollOverCalendarDayLocation1(date));
		} else {
			foundation.click(ROLL_OVER_DATE_PICKER_NEXT_LOCATION1);
			foundation.waitforElement(objectRollOverCalendarMonthLocation1(monthName), Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(objectRollOverCalendarMonthLocation1(monthName)));
			foundation.click(objectRollOverCalendarNewDayLocation1(date));
		}
	}

	/**
	 * Setting Rollover Start Date as Future Date for location1
	 * 
	 * @param value
	 */
	public void verifyRollOverFutureDateLocation1(String value) {
		String dateArray[] = value.split("/");
		String date = dateArray[1].replaceAll(Constants.REMOVE_LEADING_ZERO, "");
		int month = Integer.parseInt(dateArray[0]);
		String monthName = getMonthName(month);
		foundation.threadWait(Constants.ONE_SECOND);
		if (foundation.isDisplayed(objectRollOverCalendarMonthLocation1(monthName))) {
			foundation.click(objectRollOverCalendarNewDayLocation1(date));
		} else {
			foundation.click(ROLL_OVER_DATE_PICKER_NEXT_LOCATION1);
			foundation.waitforElement(objectRollOverCalendarMonthLocation1(monthName), Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(objectRollOverCalendarMonthLocation1(monthName)));
			foundation.click(objectRollOverCalendarNewDayLocation1(date));
		}
	}

	/**
	 * Verifying Rollover Current Date
	 * 
	 * @param value
	 */
	public void verifyRollOverCurrentDate(String value) {
		String dateArray[] = value.split("/");
		String date = dateArray[1].replaceAll(Constants.REMOVE_LEADING_ZERO, "");
		int month = Integer.parseInt(dateArray[0]);
		String monthName = getMonthName(month);
		CustomisedAssert.assertTrue(foundation.isDisplayed(objectRollOverCalendarMonth(monthName)));
		foundation.click(objectRollOverCalendarDay(date));
	}

	/**
	 * Verify the Signs in TopOff
	 */
	public void verifySignsTopOff() {
		for (int i = 1; i <= 24; i++)
			foundation.click(objAddTopOffSubsidy(i));
		foundation.waitforElement(LocationList.TXT_SPINNER_ERROR_MSG, Constants.SHORT_TIME);
		for (int j = 1; j <= 12; j++) {
			foundation.click(objDeleteTopOffSubsidy(j));
			foundation.threadWait(Constants.ONE_SECOND);
		}
	}

	/**
	 * Verify the Signs in RollOver
	 */
	public void verifySignsRollOver() {
		for (int i = 1; i <= 24; i++)
			foundation.click(objAddRollOverSubsidy(i));
		foundation.waitforElement(LocationList.TXT_SPINNER_ERROR_MSG, Constants.SHORT_TIME);
		for (int j = 1; j <= 12; j++) {
			foundation.click(objDeleteRollOverSubsidy(j));
			foundation.threadWait(Constants.ONE_SECOND);
		}
	}

	/**
	 * Resetting the Subsidy value in Location Summary Page
	 * 
	 * @param optionNames
	 * @param location
	 * @param requiredData
	 */
	public void subsidyResettingOff(String optionNames, String location, String requiredData) {
		navigationBar.navigateToMenuItem(optionNames);
		locationList.selectLocationName(location);
		foundation.click(BTN_LOCATION_SETTINGS);
		dropDown.selectItem(DPD_GMA_SUBSIDY, requiredData, Constants.TEXT);
		foundation.click(BTN_SAVE);
		foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
	}

	/**
	 * Setting Off for Subsidy value with Recurrence
	 * 
	 * @param optionNames
	 * @param location
	 * @param requiredData
	 * @param recurrence
	 */
	public void subsidyResettingOffWithRecurrence(String optionNames, String location, String requiredData,
			String recurrence) {
		navigationBar.navigateToMenuItem(optionNames);
		textBox.enterText(LocationList.TXT_FILTER, location);
		locationList.selectLocationName(location);
		foundation.click(BTN_LOCATION_SETTINGS);
		checkBox.check(CHK_TOP_OFF_SUBSIDY);
		dropDown.selectItem(DPD_TOP_OFF_RECURRENCE, recurrence, Constants.TEXT);
		dropDown.selectItem(DPD_GMA_SUBSIDY, requiredData, Constants.TEXT);
		foundation.click(BTN_SAVE);
		foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
	}

	/**
	 * Resetting Subsidy value as Off
	 * 
	 * @param optionNames
	 * @param location
	 * @param requiredData
	 */
	public void subsidyResettingValidationOff(String optionNames, String location, String requiredData) {
		foundation.threadWait(Constants.SHORT_TIME);
		navigationBar.navigateToMenuItem(optionNames);
		textBox.enterText(LocationList.TXT_FILTER, location);
		locationList.selectLocationName(location);
		foundation.click(BTN_LOCATION_SETTINGS);
		CustomisedAssert.assertTrue(foundation.isDisplayed(TXT_GMA_SUBSIDY));
		String value = dropDown.getSelectedItem(DPD_GMA_SUBSIDY);
		if (value.equals(requiredData)) {
			foundation.click(BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
		} else {
			dropDown.selectItem(DPD_GMA_SUBSIDY, requiredData, Constants.TEXT);
			foundation.click(BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
		}
	}

	/**
	 * Resetting Special Type and Subsidy value
	 * 
	 * @param optionNames
	 * @param location
	 * @param specialType
	 * @param requiredData
	 */
	public void resettingSpecialTypeAndSubsidy(String optionNames, String location, String specialType,
			String requiredData) {
		navigationBar.navigateToMenuItem(optionNames);
		locationList.selectLocationName(location);
		dropDown.selectItem(DPD_SPECIAL_TYPE, specialType, Constants.TEXT);
		foundation.click(BTN_SAVE);
		foundation.waitforElement(LocationList.TXT_SPINNER_ERROR_MSG, Constants.SHORT_TIME);
		locationList.selectLocationName(location);
		foundation.click(BTN_LOCATION_SETTINGS);
		CustomisedAssert.assertTrue(foundation.isDisplayed(TXT_GMA_SUBSIDY));
		String value = dropDown.getSelectedItem(DPD_GMA_SUBSIDY);
		if (value.equals(requiredData)) {
			foundation.click(BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
		} else {
			dropDown.selectItem(DPD_GMA_SUBSIDY, requiredData, Constants.TEXT);
			foundation.click(BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
		}
	}

	/**
	 * Enter the Group Names
	 * 
	 * @param topOff
	 * @param RollOver
	 * @param pickUp
	 */
	public void enterGroupNames(String topOff, String RollOver, String pickUp) {
		textBox.enterText(TXT_TOP_OFF_GROUP_NAME, topOff);
		textBox.enterText(TXT_ROLL_OVER_GROUP_NAME, RollOver);
		textBox.enterText(TXT_PICKUP_LOCATION_NAME, pickUp);
		foundation.click(BTN_SAVE);
		foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
	}

	/**
	 * Enter the Subsidy Group Name for both Subsidies
	 * 
	 * @param topOff
	 * @param RollOver
	 */
	public void enterSubsidyGroupNames(String topOff, String RollOver) {
		textBox.enterText(TXT_TOP_OFF_GROUP_NAME, topOff);
		textBox.enterText(TXT_ROLL_OVER_GROUP_NAME, RollOver);
		foundation.click(BTN_SAVE);
		foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
	}

	/**
	 * Enter the Subsidy Amount for both Subsidies
	 * 
	 * @param topOff
	 * @param RollOver
	 */
	public void enterSubsidyAmount(String topOff, String RollOver) {
		foundation.click(TXT_TOP_OFF_AMOUNT);
		textBox.enterText(TXT_TOP_OFF_AMOUNT, topOff);
		foundation.click(TXT_ROLL_OVER_AMOUNT);
		textBox.enterText(TXT_ROLL_OVER_AMOUNT, RollOver);
	}

	/**
	 * Check the Subsidy Selected Value
	 * 
	 * @param menu
	 * @param location
	 * @param data
	 */
	public void checkSubsidy(String menu, String location, String data) {
		navigationBar.navigateToMenuItem(menu);
		locationList.selectLocationName(location);
		foundation.click(BTN_LOCATION_SETTINGS);
		String value = dropDown.getSelectedItem(DPD_GMA_SUBSIDY);
		CustomisedAssert.assertEquals(value, data);
	}

	/**
	 * Enter the TopOff Amount with Recurrence value
	 * 
	 * @param topOff
	 * @param recurrence
	 * @param amount
	 */
	public void enterTopoffAmount(String topOff, String recurrence, String amount) {
		textBox.enterText(TXT_TOP_OFF_GROUP_NAME, topOff);
		dropDown.selectItem(DPD_TOP_OFF_RECURRENCE, recurrence, Constants.TEXT);
		textBox.enterText(TXT_TOP_OFF_AMOUNT, amount);
		foundation.click(BTN_SAVE);
	}

	/**
	 * Verify the Highlighted Products
	 * 
	 * @param expected
	 * @return
	 */
	public String verifyProductsHighlighted(String expected) {
		String attr = getDriver().findElement(VALIDATE_HIGHLIGHTED_TEXT).getAttribute("aria-selected");
		CustomisedAssert.assertEquals(expected, attr);
		return attr;
	}

	/**
	 * Verify the PopUp Displayed and its UI
	 */
	public void verifyPopUpUIDisplayed() {
		foundation.threadWait(Constants.SHORT_TIME);
		CustomisedAssert.assertTrue(foundation.isDisplayed(BTN_ADD_PRODUCT));
		foundation.click(BTN_ADD_PRODUCT);
		foundation.waitforElement(LBL_ADD_PRODUCT, Constants.SHORT_TIME);
		CustomisedAssert.assertTrue(foundation.isDisplayed(LBL_ADD_PRODUCT));
		CustomisedAssert.assertTrue(foundation.isDisplayed(TXT_ADD_PRODUCT_SEARCH));
		CustomisedAssert.assertTrue(foundation.isDisplayed(BTN_CANCEL_PRODUCT));
	}

	/**
	 * Verify the Products and its UI
	 */
	public void verifyProductsUI() {
		foundation.threadWait(Constants.TWO_SECOND);
		CustomisedAssert.assertTrue(foundation.isDisplayed(BTN_ADD_PRODUCT));
		CustomisedAssert.assertTrue(foundation.isDisplayed(TXT_PRODUCT_FILTER));
	}

	/**
	 * Select the Print Group
	 * 
	 * @param productName
	 * @param option
	 */
	public void selectPrintGroup(String productName, String option) {
		By printLink = By
				.xpath("//td[text()='" + productName + "']//..//td[@aria-describedby='productDataGrid_printer']");
		foundation.click(printLink);
		foundation.threadWait(Constants.TWO_SECOND);
		foundation.click(By.xpath("//div[@class='ui-igcombo-buttonicon ui-icon-triangle-1-s ui-icon']"));
		foundation.threadWait(Constants.THREE_SECOND);
		foundation.click(By.xpath("//li[text()='" + option + "']"));
	}

	/**
	 * Verifying table Headers for Promotion List Page
	 * 
	 * @param values
	 */

	public void verifyPromotionsTableHeaders(List<String> values) {
		try {
			CustomisedAssert.assertTrue(foundation.isDisplayed(tableData(values.get(0))));
			CustomisedAssert.assertTrue(foundation.isDisplayed(tableData(values.get(1))));
			CustomisedAssert.assertTrue(foundation.isDisplayed(tableData(values.get(2))));
			CustomisedAssert.assertTrue(foundation.isDisplayed(tableData(values.get(3))));
			CustomisedAssert.assertTrue(foundation.isDisplayed(tableData(values.get(4))));
			CustomisedAssert.assertTrue(foundation.isDisplayed(tableData(values.get(5))));
			CustomisedAssert.assertTrue(foundation.isDisplayed(tableData(values.get(6))));
			CustomisedAssert.assertTrue(foundation.isDisplayed(tableData(values.get(7))));
			CustomisedAssert.assertTrue(foundation.isDisplayed(tableData(values.get(8))));
			ExtFactory.getInstance().getExtent().log(Status.INFO, "Validated the Promotion table Headers" + values);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * Validating PopUp for Promotion on Location Summary Page
	 * 
	 * @param values
	 */

	public void verifyManageColumnPopUp(List<String> values) {
		try {
			CustomisedAssert.assertTrue(foundation.isDisplayed(manageColumnPopup(values.get(0))));
			CustomisedAssert.assertTrue(foundation.isDisplayed(manageColumnPopup(values.get(1))));
			CustomisedAssert.assertTrue(foundation.isDisplayed(manageColumnPopup(values.get(2))));
			CustomisedAssert.assertTrue(foundation.isDisplayed(manageColumnPopup(values.get(3))));
			CustomisedAssert.assertTrue(foundation.isDisplayed(manageColumnPopup(values.get(4))));
			CustomisedAssert.assertTrue(foundation.isDisplayed(manageColumnPopup(values.get(5))));
			CustomisedAssert.assertTrue(foundation.isDisplayed(manageColumnPopup(values.get(6))));
			CustomisedAssert.assertTrue(foundation.isDisplayed(manageColumnPopup(values.get(7))));
			CustomisedAssert.assertTrue(foundation.isDisplayed(manageColumnPopup(values.get(8))));
			ExtFactory.getInstance().getExtent().log(Status.INFO, "Validated the Promotion table Headers" + values);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * Enter the minimum Stock
	 * 
	 * @param product
	 * @param minStock
	 */

	public void enterMinStocks(String product, String minStock) {
		enterMinStock(product, minStock);
		foundation.click(LocationSummary.TXT_PRODUCT_FILTER);
	}

	/**
	 * Selecting the Payroll Deduct values On/Off
	 * 
	 * @param menu
	 * @param location
	 * @param payroll
	 */
	public void selectPayRollDeduct(String menu, String location, String payroll) {
		navigationBar.navigateToMenuItem(menu);
		foundation.waitforElementToBeVisible(LocationList.LBL_LOCATION_LIST, Constants.SHORT_TIME);
		locationList.selectLocationName(location);
		CustomisedAssert.assertTrue(foundation.isDisplayed(LBL_LOCATION_SUMMARY));
		foundation.scrollIntoViewElement(TXT_PAYROLL);
		dropDown.selectItem(DPD_PAYROLL_DEDUCT, payroll, Constants.TEXT);
		foundation.threadWait(Constants.SHORT_TIME);
		foundation.click(BTN_SAVE);
		foundation.waitforElementToDisappear(LocationList.TXT_SPINNER_MSG, Constants.EXTRA_LONG_TIME);
	}

	/**
	 * Login to ADM as Super, Navigate to Location and select GMA subsidy to Verify
	 * TopOff Subsidy
	 * 
	 * @param menu
	 * @param location
	 * @param requiredData
	 */
	public void navigateToLocationAndSelectGMASubsidyToVerifyTopOff(String menu, String location,
			List<String> requiredData) {
		// Login to ADM as Super
		browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
		login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
				propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
		CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

		// Select Menu, Menu Item and Location
		navigationBar.selectOrganization(
				propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
		navigationBar.navigateToMenuItem(menu);
		locationList.selectLocationName(location);
		foundation.click(BTN_LOCATION_SETTINGS);

		// Verify GMA Subsidy
		CustomisedAssert.assertTrue(foundation.isDisplayed(TXT_GMA_SUBSIDY));
		String value = dropDown.getSelectedItem(DPD_GMA_SUBSIDY);
		if (value.equals(requiredData.get(1))) {
			dropDown.selectItem(DPD_GMA_SUBSIDY, requiredData.get(0), Constants.TEXT);
		}
		value = dropDown.getSelectedItem(DPD_GMA_SUBSIDY);
		CustomisedAssert.assertEquals(value, requiredData.get(0));
		foundation.scrollIntoViewElement(DPD_GMA_SUBSIDY);
		verifyTopOffSubsidy(requiredData);
		verifyRolloverSubsidy(requiredData);
		checkBox.check(CHK_TOP_OFF_SUBSIDY);
		foundation.click(START_DATE_PICKER_TOP_OFF);
	}

	/**
	 * Selecting the Product ======= Get table records in UI
	 * 
	 * @return
	 */
	public Map<Integer, Map<String, String>> getTblRecordsUI() {
		try {
			int recordCount = 0;
			tableHeaders.clear();
			WebElement tableList = getDriver().findElement(TBL_DATA_GRID);
			WebElement table = getDriver().findElement(TBL_GRID);
			List<WebElement> columnHeaders = table.findElements(By.cssSelector("thead > tr > th"));
			List<WebElement> rows = tableList.findElements(By.tagName("tr"));
			for (WebElement columnHeader : columnHeaders) {
				tableHeaders.add(columnHeader.getText());
			}
			int col = tableHeaders.size();
			for (WebElement row : rows) {
				Map<String, String> uiTblRowValues = new LinkedHashMap<>();
				for (int columnCount = 1; columnCount < col + 1; columnCount++) {
					foundation.scrollIntoViewElement(By.cssSelector("td:nth-child(" + columnCount + ")"));
					WebElement column = row.findElement(By.cssSelector("td:nth-child(" + columnCount + ")"));
					uiTblRowValues.put(tableHeaders.get(columnCount - 1), column.getText());
				}
				tableData.put(recordCount, uiTblRowValues);
				recordCount++;
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		return tableData;
	}

	/**
	 * In location summary page navigate to product tab and enable the print group
	 * in manage column
	 * 
	 * @param product
	 */
	public void clickOnProductTabAndEnableThePrintGroup(String product) {
		CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.BTN_LOCATION_SETTINGS));
		foundation.click(LocationSummary.TAB_PRODUCTS);
		foundation.threadWait(5);
		foundation.waitforElementToBeVisible(LocationSummary.BTN_MANAGE_COLUMNS, Constants.SHORT_TIME);
		foundation.click(LocationSummary.BTN_MANAGE_COLUMNS);
		foundation.waitforElementToBeVisible(LocationSummary.BTN_PRINT_GROUP, Constants.SHORT_TIME);
		foundation.click(LocationSummary.BTN_PRINT_GROUP);
		foundation.waitforElementToBeVisible(LocationSummary.BTN_APPLY, Constants.SHORT_TIME);
		foundation.click(LocationSummary.BTN_APPLY);
		foundation.waitforElementToBeVisible(LBL_PRINT_GROUP, Constants.SHORT_TIME);
		textBox.enterText(LocationSummary.TXT_PRODUCT_FILTER, product);
		foundation.waitforElementToBeVisible(LocationSummary.TBL_GRID, Constants.SHORT_TIME);
	}

	/**
	 * In location summary page click On Edit Product After Updating Price in
	 * product summary Click On Save
	 * 
	 * @param price
	 */
	public void clickOnEditProductAfterUpdatingPriceClickOnSave(String price) {
		CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.LBL_PRODUCT_POPUP));
		foundation.click(LocationSummary.EDIT_PRODUCT);
		CustomisedAssert.assertTrue(foundation.isDisplayed(ProductSummary.LBL_PRODUCT_SUMMMARY));
		textBox.enterText(ProductSummary.PRICE_FIELD, price);
		foundation.waitforElementToBeVisible(ProductSummary.BTN_SAVE, 5);
		foundation.click(ProductSummary.BTN_SAVE);
		CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProduct.TXT_GLOBAL_PRODUCT));

	}

	/**
	 * Selecting the Product >>>>>>> main
	 * 
	 * @param product
	 */
	public void selectProduct(String product) {
		textBox.enterText(TXT_ADD_PRODUCT_SEARCH, product);
		foundation.threadWait(Constants.SHORT_TIME);
		foundation.click(SELECT_PRODUCT);
		foundation.threadWait(Constants.SHORT_TIME);
		foundation.click(LocationSummary.BTN_ADD);
		foundation.waitforElementToDisappear(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
		foundation.refreshPage();
	}
	/**
	 * Verify the selected product
	 * 
	 * @param product
	 */
	public void verifySelectProduct(String product) {
		foundation.scrollIntoViewElement(LocationSummary.TAB_PRODUCTS);
		foundation.waitforElementToBeVisible(LocationSummary.TAB_PRODUCTS,5);
		foundation.click(LocationSummary.TAB_PRODUCTS);	 
		foundation.waitforElementToBeVisible(LocationSummary.TBL_PRODUCTS_HEADER,5);
		//foundation.waitforElementToBeVisible(LocationSummary.TXT_PRODUCT_FILTER,3);
	    textBox.enterText(LocationSummary.TXT_PRODUCT_FILTER, product);
	    foundation.waitforElementToBeVisible(LocationSummary.PRODUCT_NAME,5);
		
	}
	/**
	 * Verify update price value in location
	 * @param location
	 * @param product
	 * @param price
	 */
	public void updatePriceAndVerifyPrice(String location,String product,String price) {
		
		locationList.selectLocationName(location);
		CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.LBL_LOCATION_SUMMARY));
	    foundation.scrollIntoViewElement(LocationSummary.TAB_PRODUCTS);
	    foundation.waitforElementToBeVisible(LocationSummary.TAB_PRODUCTS,3);
		foundation.click(LocationSummary.TAB_PRODUCTS);
		foundation.waitforElementToBeVisible(LocationSummary.TBL_PRODUCTS_HEADER,Constants.SHORT_TIME);
		foundation.waitforElementToBeVisible(LocationSummary.TXT_PRODUCT_FILTER,3);
	    textBox.enterText(LocationSummary.TXT_PRODUCT_FILTER, product);
	    foundation.waitforElementToBeVisible(LocationSummary.COL_PRICE,5);
	    foundation.getText(LocationSummary.COL_PRICE);
	    		CustomisedAssert.assertEquals(foundation.getText(LocationSummary.COL_PRICE), price);
		foundation.threadWait(Constants.SHORT_TIME);
	   
	}
	/**
	 * Product verify page show records number cut off
	 * @param recorddata
	 */
	public void validateProductTabRecord(By recorddata,String dbData) {
		foundation.waitforElementToBeVisible(LocationSummary.TAB_PRODUCTS,3);
	    foundation.click(LocationSummary.TAB_PRODUCTS);	
	    foundation.waitforElementToBeVisible(LocationSummary.DPD_PRODUCT_RECORD,3);
	    foundation.click(LocationSummary.DPD_PRODUCT_RECORD);
	    foundation.waitforElementToBeVisible(LocationSummary.TBL_PRODUCTS_HEADER,Constants.SHORT_TIME);
	    foundation.click(recorddata);	
	    foundation.scrollIntoViewElement(LocationSummary.MATCH_PRODUCT_RECORD);
	    foundation.waitforElementToBeVisible(LocationSummary.MATCH_PRODUCT_RECORD,5);
	    String record=foundation.getText(MATCH_PRODUCT_RECORD);
	    CustomisedAssert.assertTrue(record.contains(dbData));
	
	}
	/**
	 * Inventory verify page show records number cut off
	 * @param recorddata
	 */
	public void validateInventoryTabRecord(By recorddata,String dbData) {
		foundation.waitforElementToBeVisible(LocationSummary.LNK_INVENTORY,3);
		foundation.click(LocationSummary.LNK_INVENTORY);
		foundation.waitforElementToBeVisible(LocationSummary.DPD_INVENTORY_RECORD,3);
		foundation.click(LocationSummary.DPD_INVENTORY_RECORD);
		foundation.waitforElementToBeVisible(LocationSummary.TBL_PRODUCTS_HEADER,Constants.SHORT_TIME);
		foundation.click(recorddata);		
		foundation.scrollIntoViewElement(LocationSummary.MATCH_INVENTORY_RECORD);
		foundation.waitforElementToBeVisible(LocationSummary.MATCH_INVENTORY_RECORD,5);
		String record=foundation.getText(MATCH_INVENTORY_RECORD);
		CustomisedAssert.assertTrue(record.contains(dbData));
		
	}

	/**
	 * Removing the Product from Location
	 * 
	 * @param product
	 */
	public void removeProductFromLocation(String product, String location) {
		foundation.scrollIntoViewElement(TAB_PRODUCTS);
		foundation.click(TAB_PRODUCTS);
		foundation.waitforElement(TXT_PRODUCT_FILTER, Constants.SHORT_TIME);
		foundation.threadWait(Constants.MEDIUM_TIME);
		textBox.enterText(TXT_PRODUCT_FILTER, product);
		foundation.threadWait(5);
		foundation.click(PRODUCT_NAME);
		foundation.waitforElement(BTN_REMOVE, Constants.SHORT_TIME);
		foundation.click(BTN_REMOVE);
		foundation.threadWait(Constants.SHORT_TIME);
	}

	/**
	 * Verify Both Subsidy fields
	 * 
	 * @param values
	 */
	public void verifyBothSubsidesFields(List<String> values) {
		try {
			foundation.waitforElement(TXT_TOP_OFF_SUBSIDY, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(TXT_TOP_OFF_SUBSIDY));
			CustomisedAssert.assertTrue(foundation.isDisplayed(objTopOffSubsidyColumn(values.get(2))));
			CustomisedAssert.assertTrue(foundation.isDisplayed(objTopOffSubsidyColumn(values.get(3))));
			CustomisedAssert.assertTrue(foundation.isDisplayed(objTopOffSubsidyColumn(values.get(4))));
			CustomisedAssert.assertTrue(foundation.isDisplayed(objTopOffSubsidyColumn(values.get(5))));
			CustomisedAssert.assertTrue(foundation.isDisplayed(objTopOffSubsidyColumn(values.get(6))));
			CustomisedAssert.assertTrue(foundation.isDisplayed(objTopOffSubsidyColumn(values.get(7))));
			CustomisedAssert.assertTrue(foundation.isDisplayed(CHK_TOP_OFF_EXCLUDE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(BTN_DELETE_TOP_OFF));
			CustomisedAssert.assertTrue(foundation.isDisplayed(BTN_ADD_TOP_OFF));
			CustomisedAssert.assertTrue(foundation.isDisplayed(TXT_ROLL_OVER_SUBSIDY));
			CustomisedAssert.assertTrue(foundation.isDisplayed(objRollOverSubsidyColumn(values.get(2))));
			CustomisedAssert.assertTrue(foundation.isDisplayed(objRollOverSubsidyColumn(values.get(3))));
			CustomisedAssert.assertTrue(foundation.isDisplayed(objRollOverSubsidyColumn(values.get(4))));
			CustomisedAssert.assertTrue(foundation.isDisplayed(objRollOverSubsidyColumn(values.get(5))));
			CustomisedAssert.assertTrue(foundation.isDisplayed(objRollOverSubsidyColumn(values.get(6))));
			CustomisedAssert.assertTrue(foundation.isDisplayed(objRollOverSubsidyColumn(values.get(7))));
			CustomisedAssert.assertTrue(foundation.isDisplayed(CHK_ROLL_OVER_EXCLUDE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(BTN_DELETE_ROLL_OVER));
			CustomisedAssert.assertTrue(foundation.isDisplayed(BTN_ADD_ROLL_OVER));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * Verify GMA Subsidy UI Fields
	 * 
	 * @param expected
	 */
	public void verifyGMASubsidyUIFields(List<String> expected) {
		foundation.click(BTN_LOCATION_SETTINGS);
		CustomisedAssert.assertTrue(foundation.isDisplayed(TXT_MULTI_TAX_REPORT));
		CustomisedAssert.assertTrue(foundation.isDisplayed(TXT_GMA_SUBSIDY));
		List<String> values = dropDown.getAllItems(DPD_GMA_SUBSIDY);
		List<String> expectedValues = new ArrayList<String>();
		expectedValues.add(expected.get(1));
		expectedValues.add(expected.get(0));
		CustomisedAssert.assertTrue(values.equals(expectedValues));
		String value = dropDown.getSelectedItem(DPD_GMA_SUBSIDY);
		if (value.equals(expected.get(1))) {
			dropDown.selectItem(DPD_GMA_SUBSIDY, expected.get(0), Constants.TEXT);
		}
		value = dropDown.getSelectedItem(DPD_GMA_SUBSIDY);
		CustomisedAssert.assertEquals(value, expected.get(0));
		foundation.scrollIntoViewElement(DPD_GMA_SUBSIDY);
	}
	/**
	 * Manage Tax2 Column
	 * 
	 */
	public void selectManageColumnTax2() {
	foundation.click(LocationSummary.BTN_MANAGE_COLUMNS);
    foundation.scrollIntoViewElement(LocationSummary.BTN_TAX2);
	foundation.waitforElementToBeVisible(LocationSummary.BTN_TAX2, Constants.MEDIUM_TIME);
	foundation.click(LocationSummary.BTN_TAX2);
	foundation.waitforElementToBeVisible(LocationSummary.BTN_APPLY, Constants.SHORT_TIME);
	foundation.click(LocationSummary.BTN_APPLY);
	}

	
}
