package at.smartshop.pages;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import at.framework.browser.Factory;
import at.framework.generic.Numbers;
import at.framework.generic.Strings;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.keys.Constants;

public class ConsumerSearch extends Factory {
	private TextBox textBox = new TextBox();
	private Dropdown dropdown = new Dropdown();
	private Foundation foundation = new Foundation();
	private Strings strings=new Strings();
	private Numbers numbers=new Numbers();
	
	public static final By DPD_LOCATION = By.id("loc-dropdown");
	private static final By DPD_STATUS = By.id("isdisabled");
	public static final By DPD_SEARCH_BY = By.id("searchBy");
	private static final By TXT_SEARCH = By.id("search");
	private static final By BTN_GO = By.id("findBtn");
	public static final By TBL_CONSUMERS = By.id("consumerdt");
	public static final By BTN_ADJUST = By.xpath("//a[text()='Adjust']");
	public static final By TXT_BALANCE_NUM = By.id("balNum");
	public static final By TBL_LOCATION = By.id("consumerdt");
	public static final By BTN_REASON_CANCEL = By.id("reasoncancel");
	public static final By BTN_CREATE = By.cssSelector("button#createNewBtn");
	public static final By TBL_ROW = By.xpath("//*[@id='consumerdt']/tbody/tr[@class='odd']");
	public final static By TXT_CONSUMER_SEARCH = By.id("Consumer Search");
	public final static By BTN_CREATE_NEW = By.id("createNewBtn");
	public final static By TXT_EMAIL = By.id("email");
	public final static By TXT_SCAN_ID = By.id("scanid");
	public final static By TXT_FIRST_NAME = By.id("firstname");
	public final static By TXT_LAST_NAME = By.id("lastname");
	public final static By TXT_PIN = By.id("pin");
	public static final By DPD_PAY_CYCLE = By.id("paycycle");
	public static final By LNK_FIRST_ROW = By.xpath("//table[@id='consumerdt']//td//a");
	

	public void enterSearchFields(String searchBy, String search, String locationName, String status) {
		try {
			dropdown.selectItem(DPD_SEARCH_BY, searchBy, Constants.TEXT);
			textBox.enterText(TXT_SEARCH, search);
			dropdown.selectItem(DPD_LOCATION, locationName, Constants.TEXT);
			dropdown.selectItem(DPD_STATUS, status, Constants.TEXT);
			foundation.click(BTN_GO);
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}

	}

	public By objCell(String consumerName) {
		return By.xpath("//table[@id='consumerdt']//tbody//tr//td//a[text()='" + consumerName + "']");
	}

	public void verifyConsumerSummary(String consumerName) {
		foundation.click(objCell(consumerName));
		Assert.assertTrue(getDriver().findElement(ConsumerSummary.LBL_CONSUMER_SUMMARY).isDisplayed());
	}

	public List<String> getConsumerHeaders() {
        List<String> tableHeaders = new ArrayList<>();
        try {
            WebElement tableProducts = getDriver().findElement(TBL_LOCATION);
            List<WebElement> columnHeaders = tableProducts.findElements(By.cssSelector("thead > tr > th"));
            for (WebElement columnHeader : columnHeaders) {
                tableHeaders.add(columnHeader.getText());
            }
        } catch (Exception exc) {
            Assert.fail(exc.toString());
        }
        return tableHeaders;
    }
	
    public Map<String, String> getConsumerRecords(String location) {
        Map<String, String> consumerRecord = new LinkedHashMap<>();
        try {
            List<String> tableHeaders = getConsumerHeaders();
            for (int columnCount = 1; columnCount < tableHeaders.size() + 1; columnCount++) {
                WebElement column = getDriver().findElement(By.xpath("//table[@id='consumerdt']//tr//td[(text()='"+location+"')]//..//..//td[" + columnCount + "]"));
                consumerRecord.put(tableHeaders.get(columnCount - 1), column.getText());
            }
        } catch (Exception exc) {
            Assert.fail(exc.toString());
        }
        return consumerRecord;
    }
    
    public String createConsumer(String location) {
    	String emailID=strings.getRandomCharacter()+Constants.AUTO_TEST_EMAIL;
    	int scanID=numbers.generateRandomNumber(99999, 999999);
    	int pin=numbers.generateRandomNumber(1000, 9999);
    	dropdown.selectItem(DPD_LOCATION, location, Constants.TEXT);
    	textBox.enterText(TXT_FIRST_NAME, Constants.AUTO_TEST+strings.getRandomCharacter());
    	textBox.enterText(TXT_LAST_NAME, Constants.AUTO_TEST+strings.getRandomCharacter());
    	textBox.enterText(TXT_EMAIL, emailID);
    	textBox.enterText(TXT_SCAN_ID, ""+scanID);
    	textBox.enterText(TXT_PIN, ""+pin);
    	foundation.click(BTN_CREATE);
    	return emailID;
    }
}
