package at.smartshop.pages;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import at.framework.browser.Factory;
import at.framework.files.JsonFile;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.keys.Reports;
import at.smartshop.tests.TestInfra;
import at.smartshop.utilities.WebService;

public class TransactionSearchPage extends Factory {

	private JsonFile jsonFunctions = new JsonFile();
	private Foundation foundation = new Foundation();
	private TextBox textBox = new TextBox();
	private WebService webService = new WebService();

	public static final By TXT_SEARCH = By.cssSelector("input#transid");
	public static final By DPD_DATE_RANGE = By.cssSelector("div#daterange");
	public static final By TXT_CLEAR_ALL = By.cssSelector("span.select2-selection__clear");
	public static final By DPD_LOCATION = By.cssSelector("select#loc-dropdown");
	public static final By BTN_FIND = By.id("findBtn");
	public static final By LBL_TRANSACTION_SEARCH = By.xpath("//li[@id='Transaction Search']");
	private static final By GRID_SCHEDULED_REPORT = By.xpath("//div[@class='ranges']//ul");
	private static final By DPD_DATE_OPTIONS = By.xpath("//div[@class='ranges']//ul//li");
	public static final By TXT_LOCATION_NAME = By.xpath("//input[@placeholder='Select Locations']");
	public static final By TABLE_TRANSACTION_GRID = By.cssSelector("table#transdt > thead > tr");
	public static final By TABLE_TRANSACTION_ROW = By.cssSelector("tr.odd");
	public static final By TABLE_SALES_ITEM_GRID = By.cssSelector("table#rptdt > thead > tr");
	public static final By TXT_REPORT_SEARCH = By.xpath("//input[@aria-controls='rptdt']");
	public static final By LBL_REPORT_NAME = By
			.cssSelector("#report-container > script + div > div.col-12.comment-table-heading");
	private static final By DPD_LOCATIONS = By
			.xpath("//select[@id='loc-dropdown']//..//span[contains(@class,'multiple')]");
	private static final By TXT_LOCATION_SEARCH = By.cssSelector("span.selection > span > ul > li > input");
	private static final By DPD_LOCATION_LIST = By.cssSelector("span.select2-results > #select2-locdt-results");
	public static final By LINK_TRANSACTION_ID = By.cssSelector("th#transactionId");
	public static final By BTN_PRINT = By.id("printBtn");
	public static final By TXT_TRANSACTION_SEARCH = By.xpath("//*[@id='transdt_filter']/label/input");

	public static final By LBL_LOCATION = By.xpath("//*[@id='location']/following-sibling::dd[1]");
	public static final By LBL_DEVICE = By.xpath("//*[@id='device']/following-sibling::dd[1]");
	public static final By LBL_SUBTOTAL = By.xpath("//*[@id='subtotal']/following-sibling::dd[1]");
	public static final By LBL_TAX = By.xpath("//*[@id='tax:']/following-sibling::dd[1]");
	public static final By LBL_TOTAL = By.xpath("//*[@id='total']/following-sibling::dd[1]");
	public static final By LBL_STATUS = By.id("status_0");
	public static final By LBL_PAYMENT_TYPE = By.id("paytype_0");
	public static final By LBL_PRODUCT1 = By.id("item_0");
	public static final By LBL_PRODUCT2 = By.id("item_1");
	public final By SEARCH_RESULT = By.xpath("//input[@aria-controls='transdt']");
	public final By CHECK_BOX = By.xpath("//input[@type='checkbox']");

	public static final String TRANSACTION_SEARCH = "transaction-search";
	public static final String SNOWFLAKE = "Snowflake";
	public static final String RDS = "RDS";

	private Map<String, Object> jsonData = new HashMap<>();

	public void selectDate(String optionName) {
		try {
			foundation.click(DPD_DATE_RANGE);
			WebElement editerGrid = getDriver().findElement(GRID_SCHEDULED_REPORT);
			foundation.waitforElement(DPD_DATE_OPTIONS, Constants.EXTRA_LONG_TIME);
			List<WebElement> dateOptions = editerGrid.findElements(DPD_DATE_OPTIONS);
			for (WebElement dateOption : dateOptions) {
				if (dateOption.getText().equals(optionName)) {
					dateOption.click();
					break;
				}
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void selectLocation(String locationName) {
		try {
			foundation.click(DPD_LOCATIONS);
			textBox.enterText(TXT_LOCATION_SEARCH, locationName);
			foundation.click(By.xpath("//li[text()='" + locationName + "']"));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}

	}

	public By objTransactionId(String transactionAmount) {
		return By.xpath("//td[contains(text(),'" + transactionAmount + "')]//..//td//span");
	}

	/**
	 * This method is to select TransactionID
	 * 
	 * @param optionName
	 * @param location
	 * @param transactionAmount
	 * @param date
	 */
	public void selectTransactionID(String optionName, String location, String transactionAmount, String date) {
		selectDate(optionName);
		selectLocation(location);
		foundation.click(TransactionSearchPage.BTN_FIND);
		textBox.enterText(TransactionSearchPage.TXT_TRANSACTION_SEARCH, date);
		foundation.click(objTransactionId(transactionAmount));
		foundation.waitforElement(BTN_PRINT, Constants.SHORT_TIME);
		assertTrue(foundation.isDisplayed(BTN_PRINT));
	}

	/**
	 * This method is to verify Transaction Details
	 * 
	 * @param total
	 * @param paymentType
	 * @param product1
	 * @param product2
	 */
	public void verifyTransactionDetails(String total, String paymentType, String product1, String product2) {
		assertEquals(foundation.getText(LBL_LOCATION),
				propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));
		// assertEquals(foundation.getText(LBL_DEVICE),propertyFile.readPropertyFile(Configuration.DEVICE_ID,
		// FilePath.PROPERTY_CONFIG_FILE));
		// assertEquals(foundation.getText(LBL_SUBTOTAL),propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1,
		// FilePath.PROPERTY_CONFIG_FILE));\
		System.out.println(foundation.getText(LBL_TOTAL));
		assertTrue(foundation.getText(LBL_TOTAL).contains(total));
		assertEquals(foundation.getText(LBL_STATUS), Constants.ACCEPTED);
		assertEquals(foundation.getText(LBL_PAYMENT_TYPE), paymentType);

		System.out.println(foundation.getText(LBL_PRODUCT1));
		System.out.println(foundation.getText(LBL_PRODUCT2));
		if (foundation.getText(LBL_PRODUCT1).equals(product1)) {
			assertEquals(foundation.getText(LBL_PRODUCT1), product1);
			assertEquals(foundation.getText(LBL_PRODUCT2), product2);
		} else {
			assertEquals(foundation.getText(LBL_PRODUCT1), product2);
			assertEquals(foundation.getText(LBL_PRODUCT2), product1);
		}
	}

	/**
	 * This method is to create a sale transaction using API
	 */
	public void processAPI() {
		try {
			generateJsonDetails();
			salesJsonDataUpdate();
			webService.apiReportPostRequest(
					propertyFile.readPropertyFile(Configuration.TRANS_SALES, FilePath.PROPERTY_CONFIG_FILE),
					(String) jsonData.get(Reports.JSON));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	private void generateJsonDetails() {
		try {
			DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(Reports.DATE_FORMAT);
			LocalDateTime tranDate = LocalDateTime.now();
			String transDate = tranDate.format(dateFormat);
			String transID = propertyFile.readPropertyFile(Configuration.DEVICE_ID, FilePath.PROPERTY_CONFIG_FILE)
					+ Constants.DELIMITER_HYPHEN
					+ transDate.replaceAll(Reports.REGEX_TRANS_DATE, Constants.EMPTY_STRING);
			jsonData.put(Reports.TRANS_ID, transID);
			jsonData.put(Reports.TRANS_DATE, transDate);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	private void jsonArrayDataUpdate(JsonObject jsonObj, String reqString, String salesheader) {
		try {
			JsonArray items = jsonObj.get(reqString).getAsJsonArray();
			for (JsonElement item : items) {
				JsonObject json = item.getAsJsonObject();
				json.addProperty(Reports.ID,
						UUID.randomUUID().toString().replace(Constants.DELIMITER_HYPHEN, Constants.EMPTY_STRING));
				json.addProperty(Reports.SALES_HEADER, salesheader);
				json.addProperty(Reports.TRANS_ID, (String) jsonData.get(Reports.TRANS_ID));
				json.addProperty(Reports.TRANS_DATE, (String) jsonData.get(Reports.TRANS_DATE));
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	private void salesJsonDataUpdate() {
		try {
			String salesHeaderID = UUID.randomUUID().toString().replace(Constants.DELIMITER_HYPHEN,
					Constants.EMPTY_STRING);
			String saleValue = jsonFunctions.readFileAsString(FilePath.JSON_SALES_CREATION);
			JsonObject saleJson = jsonFunctions.convertStringToJson(saleValue);
			saleJson.addProperty(Reports.TRANS_ID, (String) jsonData.get(Reports.TRANS_ID));
			saleJson.addProperty(Reports.TRANS_DATE, (String) jsonData.get(Reports.TRANS_DATE));
			String sale = saleJson.get(Reports.SALE).getAsString();
			JsonObject salesObj = jsonFunctions.convertStringToJson(sale);
			salesObj.addProperty(Reports.ID, salesHeaderID);
			salesObj.addProperty(Reports.TRANS_ID, (String) jsonData.get(Reports.TRANS_ID));
			salesObj.addProperty(Reports.TRANS_DATE, (String) jsonData.get(Reports.TRANS_DATE));
			jsonArrayDataUpdate(salesObj, Reports.ITEMS, salesHeaderID);
			jsonArrayDataUpdate(salesObj, Reports.PAYMENTS, salesHeaderID);
			saleJson.addProperty(Reports.SALE, salesObj.toString());
			jsonData.put(Reports.JSON, saleJson.toString());
			jsonData.put(Reports.SALES, salesObj);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public Map<String, Object> getJsonData() {
		return jsonData;
	}

}
