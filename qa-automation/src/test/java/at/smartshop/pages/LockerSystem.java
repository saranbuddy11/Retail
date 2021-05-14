package at.smartshop.pages;

import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import at.framework.browser.Factory;
import at.framework.ui.Foundation;

public class LockerSystem extends Factory{
	private Foundation foundation=new Foundation();

	public static final By BTN_CREATE_SYSTEM= By.id("systemcreate");
	public static final By BTN_YES_DELETE = By.xpath("//button[text()='Yes, Delete']");
	public static final By BTN_CANCEL = By.xpath("//button[text()='Cancel']");
	public static final By POP_UP_TITLE = By.cssSelector("div.ajs-header");
	public static final By POP_UP_MESSAGE= By.cssSelector("div.ajs-content");
	public static final By MSG_DELETE_SUCCESS = By.xpath("//div[text()='Deleted Successfully']");
	public static final By ICO_SIBLING_DELETE = By.xpath("//a[contains(@title,'Copy')]//..//following-sibling::td");
    public static final By BTN_DELETE = By.xpath("button:contains(Yes, Delete)");
    public static final By LBL_PAGE_TITLE = By.xpath("//div[text()='Location Locker Systems']");
    public static final By BTN_SCHEDULING = By.xpath("//a[text()='Scheduling']");
    private static final By TABLE_LOCATION_HEADER = By.xpath("//table[@id='lockersystemtable']//table/thead/tr/th/span[text()]");
    private static final By LBL_CUBBY_SCHEDULING_SCREEN = By.cssSelector("div.cubbyscheduleback > span");

	
    public static List<String> columnNamesList = new ArrayList<>();

	public void expandLocationLocker(String location) {
		try {
			Thread.sleep(2000);
			foundation.click(By.xpath("//td[@aria-describedby='lockersystemtable_locationName'][text()='"+location+"']//..//span[@class='ui-iggrid-expandbuttoncontainer']"));
		} catch (InterruptedException exc) {
		
		}
	}
	
	public By objSchedule(String systemName) {		
		return By.xpath("//a[@id='linksystemname'][text()='"+systemName+"']//..//..//a[text()='Scheduling']");
	}
	
	public void disableLocker(String address) {		
		foundation.click(By.xpath("//td[@aria-describedby='cubbyschedulinggrid_address'][text()='"+address+"']//..//td[@aria-describedby='cubbyschedulinggrid_disable']//a"));
		assertEquals(cubbyStatus(address),"Disabled");
	}
	
	public void resetleLocker(String address) {		
		foundation.click(By.xpath("//td[@aria-describedby='cubbyschedulinggrid_address'][text()='"+address+"']//..//td[@aria-describedby='cubbyschedulinggrid_reset']//a"));
		assertEquals(cubbyStatus(address),"Available");
	}
	
	public boolean resetleLockerDisplay(String address) {		
		return foundation.isDisplayed(By.xpath("//td[@aria-describedby='cubbyschedulinggrid_address'][text()='"+address+"']//..//td[@aria-describedby='cubbyschedulinggrid_reset']//a"),"reset locker");
	}
	
	public String cubbyStatus(String address) {		
		return foundation.getText(By.xpath("//td[@aria-describedby='cubbyschedulinggrid_address'][text()='"+address+"']//..//td[@aria-describedby='cubbyschedulinggrid_status']"));
	}
	
	public void copyDeleteSystem(String location, String name) {		
		foundation.click(By.xpath("//a[@id='linksystemname'][text()='"+location+"']//..//..//a[contains(@title,'"+ name +"')]"));
	}
	
	public void getLocationColumns() {
        try {
            columnNamesList.clear();
            List<WebElement> header = getDriver().findElements(TABLE_LOCATION_HEADER);
            for(int iter=1; iter<=header.size(); iter++) {
                String column = header.get(iter).getText();
                columnNamesList.add(column);
            }           
        }catch(Exception exc) {
            Assert.fail(exc.toString());
        }
    }
   
    public void verifyLocationColumns(String columnNames) {
        try {
            getLocationColumns();
            int count = columnNamesList.size();
            for (int iter = 0; iter < count; iter++) {
                Assert.assertTrue(columnNames.contains(columnNamesList.get(iter)));
            }
        } catch (Exception exc) {
            Assert.fail(exc.toString());
        }
    }
    
    public void verifyCubbySchedulingScreen(String pageTitle) {
        try {
            String text = foundation.getText(LBL_CUBBY_SCHEDULING_SCREEN);
            Assert.assertTrue(text.contains(pageTitle));               
        } catch (Exception exc) {
            Assert.fail(exc.toString());
        }
    }
    
    public void verifyLocationNameNotExist(String location) {
        try {
         List<WebElement> name = getDriver().findElements(By.xpath("//td[@aria-describedby='lockersystemtable_locationName'][text()='"+location+"']"));
         Assert.assertTrue(name.size()==0);
        }catch(Exception exc) {
            Assert.fail(exc.toString());
        }
    }
}
