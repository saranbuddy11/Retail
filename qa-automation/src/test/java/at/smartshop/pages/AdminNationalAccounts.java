package at.smartshop.pages;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import at.framework.browser.Factory;
import at.framework.ui.Foundation;

public class AdminNationalAccounts extends Factory{
	public By NationalAccountTitle = By.id("page-title");
	public By tableNationalAccountList = By.cssSelector("table#national-account-grid > tbody");
    public By manageRulesLink = By.linkText("Manage Rules");
	public By tblNationalAccountTitle = By.id(" National Account Rules");
	public By txtFilter = By.id("filterType");
	public By tblNationalAccountRulesList = By.cssSelector("#dataGrid> tbody > tr");
	public By tblNationalAccountHeader = By.cssSelector("table#dataGrid > thead > tr");
	public By dpdRuleType = By.id("ruletype");
	public By tblDataGrid = By.id("dataGrid");
	public By tblDataRow = By.cssSelector("#dataGrid > tbody > tr:nth-of-type(1)");
	public By dpdNACategory = By.id("upctype");
	public By btnSave = By.id("saveBtn");
	public By dpdRulePrice = By.id("ruleprice");
	
	Foundation foundation = new Foundation();
	
	public void clickManageRulesLink(String nationalAccountName,String gridName) {
		try {
			WebElement rowList = getDriver().findElement(tableNationalAccountList);
			List<WebElement> rows = rowList.findElements(By.tagName("tr"));
			for(int iter=0;iter<rows.size();iter++) {
				WebElement column  = rows.get(iter).findElement(By.cssSelector("td[aria-describedby="+gridName+"]"));
				if(column.getText().contains(nationalAccountName)) {	
					foundation.click(manageRulesLink);
					break;
				}
			}	
		}catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
	
	
}
