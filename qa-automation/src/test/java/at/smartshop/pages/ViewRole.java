package at.smartshop.pages;

import static org.testng.Assert.assertTrue;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import at.framework.browser.Factory;
import at.framework.ui.CheckBox;

public class ViewRole extends Factory {
	private CheckBox checkBox=new CheckBox();
	
	public static final By LBL_NATIONAL_ACCOUNT_CATEGORIES = By.xpath("//*[@id='dt5']//td[text()='Admin:National Account Categories']");
	public static final By LBL_NATIONAL_ACCOUNT_LOCKS_RULES = By.xpath("//*[@id='dt5']//td[text()='Admin:National Accounts Locks & Rules']");
	
	public void isAllCheckboxChecked(String text) {
		List<WebElement> checkboxes=getDriver().findElements(By.xpath("//td[text()='"+text+"']//..//input"));
		for(int i=1;i<checkboxes.size()-1;i++){			
			By lblRow = By.xpath("(//td[text()='"+text+"']//..//input)["+ i +"]");		
		assertTrue(checkBox.isChecked(lblRow));
		}
	}
}
