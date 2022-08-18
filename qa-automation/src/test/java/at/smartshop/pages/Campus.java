package at.smartshop.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import at.framework.browser.Factory;
import at.framework.generic.CustomisedAssert;
import at.framework.ui.Foundation;

public class Campus extends Factory{
	
	public static final By LBL_CAMPUSLIST_HEADING= By.id("Campus List");
	public static final By BTN_CREATE_NEW = By.id("newBtn");
	public static final By LBL_CAMPUSSAVE_HEADING = By.id("Campus Save");
	public static final By LBL_CAMPUSSHOW_HEADING = By.id("Campus Show");
	public static final By BTN_SAVE = By.id("saveBtn");
	public static final By BTN_CANCEL = By.id("cancelBtn");
	public static final By DRP_PAYROLL = By.id("payrolldeduct");
	public static final By TXT_PAYROLL_EMAIL_ERROR = By.id("payrolldeductemail-error");
	public static final By TXT_NAME_ERROR = By.id("name-error");
	public static final By TXT_GROUPNAME_ERROR = By.id("groupname0-error");
	public static final By TXTBX_NAME = By.id("name");
	public static final By TXTBX_LEFT = By.id("mainform");
	public static final By ERRORBOX = By.className("error");
	public static final By SEARCH_BOX = By.xpath("//input[@aria-controls=\"dt\"]");
	public static final By SELECT_GRID = By.xpath("//td[@class=\" sorting_1\"]");
	public static final By DRP_PAYCYCLE = By.id("frequency0");
	public static final By TXT_GROUP_NAME = By.id("groupname0");
	public static final By TXT_SPEND_LIMIT = By.id("spendlimit0");
	public static final By LBL_LIMIT_ERROR = By.id("spendlimit0-error");
	public static final By BTN_ADD_TO_CAMPUS = By.id("addLocation");
	public static final By BTN_ADD= By.xpath("//button[@class='btn-mini pull-right']//i");
	
	private Foundation foundation = new Foundation();
	
	public void enterInvalidData(String value, String error) {
		WebElement textboxLeft = getDriver().findElement(TXTBX_LEFT);
		List<WebElement> textboxes = textboxLeft.findElements(By.className("validstr")); 
		List<WebElement> errors = textboxLeft.findElements(By.className("error")); 
		        int Size = textboxes.size();   
		        int errorsize = errors.size();
		        for(int i=0; i< Size; i++)          
		        {   
		        	textboxes.get(i).sendKeys(value);
		        	foundation.click(BTN_SAVE);
		        	 for(int j=0;j< errorsize; j++)          
				        { 
		        	errors.get(j).getText().equals(error);
				        }
		        }	
		    }

}
