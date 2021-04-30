package at.smartshop.pages;

import org.openqa.selenium.By;

import at.framework.browser.Factory;
import at.framework.ui.Foundation;

public class AdminNationalAccounts extends Factory{
	public By lblNationalAccountTitle = By.id("page-title");
	public By tblNationalAccountTitle = By.id(" National Account Rules");
	public By txtFilter = By.id("filterType");
	public By tblRulesList = By.cssSelector("#dataGrid> tbody > tr");
	public By tblHeader = By.cssSelector("table#dataGrid > thead > tr");
	public By dpdRuleType = By.id("ruletype");
	public By tblDataGrid = By.id("dataGrid");
	public By tblDataRow = By.cssSelector("#dataGrid > tbody >tr");
	public By dpdCategory = By.id("upctype");
	public By btnSave = By.id("saveBtn");
	public By dpdRulePrice = By.id("ruleprice");
	
	Foundation foundation = new Foundation();
	
	public void clickManageRule(String nationalAccountName, String gridName) {
		getDriver().findElement(By.xpath("//td[@aria-describedby='"+ gridName +"'][text()='"+ nationalAccountName +"']//..//td[@aria-describedby='national-account-grid_manageRules']//a")).click();
    }
}
