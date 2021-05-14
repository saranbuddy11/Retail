package at.smartshop.pages;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import at.framework.browser.Factory;
import at.framework.ui.Foundation;

public class AdminNationalAccounts extends Factory{
	public static final By LBL_NATIONAL_ACCOUNT = By.id("page-title");
	public static final By TBL_NATIONAL_ACCOUNT_LIST = By.cssSelector("table#national-account-grid > tbody");
    public static final By LNK_MANAGE_RULES = By.linkText("Manage Rules");
	public static final By TBL_NATIONAL_ACCOUNT_TITLE = By.id(" National Account Rules");
	public static final By TXT_FILTER = By.id("filterType");
	public static final By TBL_NATIONAL_ACCOUNT_RULE_LIST = By.cssSelector("#dataGrid> tbody > tr");
	public static final By TBL_NATIONAL_ACCOUNT_HEADER = By.cssSelector("table#dataGrid > thead > tr");
	public static final By DPD_RULE_TYPE = By.id("ruletype");
	public static final By TBL_DATA_GRID = By.id("dataGrid");
	public static final By TBL_DATA_ROW = By.cssSelector("#dataGrid > tbody >tr");
	public static final By DPD_NA_CATEGORY = By.id("upctype");
	public static final By BTN_SAVE = By.id("saveBtn");
	public static final By DPD_RULE_PRICE = By.id("ruleprice");
	public static final By DPD_LOCATION_RULE = By.id("locdt");
	public static final By BTN_LOCATION_CLEAR_RULE = By.xpath("//select[@id='locdt']//..//span[@class='select2-selection__clear']");
	public static final By TXT_RULE_PRICE = By.cssSelector("input#fromprice");
	public static final By LBL_CURRENCY_TYPE = By.cssSelector("#priceLess > dt");
	public static final By CHK_RULE_STATUS = By.id("rulestatus");
	public static final By POP_UP_LOCATION = By.id("successmodel");
	public static final By BTN_CANCEL= By.id("btn-cancel");
	public static final By ICO_DELETE = By.cssSelector("a.fa.fa-trash.icon");
	public static final By POP_UP_BTN_CANCEL = By.cssSelector(".ajs-button.ajs-cancel");
	public static final By LBL_RULE_NAME = By.xpath("//*[@aria-describedby='dataGrid_name']//a");
	public static final By LBL_RULE_NAME_HEADER = By.id("dataGrid_name");
	public static final By DPD_ORG = By.id("orgdt");
	public static final By CB_AUTO_ADD_LOCATION= By.id("autoselects");
	
	Foundation foundation = new Foundation();
	
	public void clickManageRule(String nationalAccountName, String gridName) {
		getDriver().findElement(By.xpath("//td[@aria-describedby='"+ gridName +"'][text()='"+ nationalAccountName +"']//..//td[@aria-describedby='national-account-grid_manageRules']//a")).click();
    }
	
	public List<String> getColumnValues(String gridName ) {
		String text = null;
		List<String> elementsText= new ArrayList<String>();
		try {
			 List<WebElement> ListElement = getDriver().findElements(By.xpath("//*[@aria-describedby='"+ gridName +"']//a"));
			 for(int i=1;i<=ListElement.size();i++)  {
				  text = getDriver().findElement(By.xpath("(//*[@aria-describedby='"+ gridName +"']//a)"+"["+i+"]")).getText();
				 elementsText.add(text);
			 }
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
		return elementsText;
	}
	
	public boolean verifySortAscending(String gridName) {
		boolean ascending=false;
		try {
			List<String> listRuleNameAccending = getColumnValues(gridName);
			ascending = listRuleNameAccending.stream().sorted(Comparator.naturalOrder()).collect(Collectors.toList()).equals(listRuleNameAccending);
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
		return ascending;
	}
	
	public boolean verifySortDescending(String gridName) {
		boolean descending=false;
		try {
			List<String> listRuleNameDescending = getColumnValues(gridName);
			descending = listRuleNameDescending.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList()).equals(listRuleNameDescending);
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
		return descending;
	}
}
