package at.smartshop.pages;

import org.openqa.selenium.By;

import at.framework.browser.Factory;
import at.framework.ui.Foundation;

public class AdminNationalAccounts extends Factory{
	public static final By lblNationalAccountTitle = By.id("page-title");
	public static final By tblNationalAccountTitle = By.id(" National Account Rules");
	public static final By txtFilter = By.id("filterType");
	public static final By tblRulesList = By.cssSelector("#dataGrid> tbody > tr");
	public static final By tblHeader = By.cssSelector("table#dataGrid > thead > tr");
	public static final By dpdRuleType = By.id("ruletype");
	public static final By tblDataGrid = By.id("dataGrid");
	public static final By tblDataRow = By.cssSelector("#dataGrid > tbody >tr");
	public static final By dpdCategory = By.id("upctype");
	public static final By btnSave = By.id("saveBtn");
	public static final By dpdRulePrice = By.id("ruleprice");
	
	Foundation foundation = new Foundation();
	
	public void clickManageRule(String nationalAccountName, String gridName) {
		getDriver().findElement(By.xpath("//td[@aria-describedby='"+ gridName +"'][text()='"+ nationalAccountName +"']//..//td[@aria-describedby='national-account-grid_manageRules']//a")).click();
    }
}
