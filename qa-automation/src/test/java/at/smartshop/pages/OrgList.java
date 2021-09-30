package at.smartshop.pages;

import org.openqa.selenium.By;

import at.framework.ui.Foundation;
import at.framework.ui.TextBox;

public class OrgList {
	private TextBox textBox = new TextBox();
	private Foundation foundation = new Foundation();
	
	public static final By TXT_SEARCH_ORG = By.xpath("//*[@id='dt_filter']//input");
	public static final By LBL_ORG_LIST = By.id("Org List");

	public void selectOrg(String organization) {
		textBox.enterText(TXT_SEARCH_ORG, organization);
		foundation.click(By.xpath("//td[@class=' sorting_1']//span[text()='"+organization+"']"));
	}
}
