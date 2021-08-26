package at.smartshop.pages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import at.framework.browser.Factory;
import at.smartshop.tests.TestInfra;

public class LockerEquipment extends Factory {
	
	public static final By TBL_LOCKER_EQUIPMENT_HEADER = By.cssSelector("#lockerequipmentgrid > thead > tr");
	public static final By TXT_PAGE_TITLE=By.xpath("//h4[text()='Locker Equipment']");
	public static final By LINK_MODEL=By.xpath("//a[text()='20 Door Satellite']");
	public static final By TXT_MODEL_NAME=By.xpath("//div[@id='20door']/div[text()='Locker Model: 20 Door Satellite']");
	public static final By TBL_GRID=By.id("lockerequipmentgrid");
	public static final By TBL_ROW_1=By.cssSelector("#lockerequipmentgrid > tbody");                                    
	public static final By TBL_ROW_2=By.xpath("//table[@id='lockerequipmentgrid']/tbody/tr[@class='ui-ig-altrecord ui-iggrid-altrecord']");	
	public static final By LOCKER_EQUIPMENT_GRID = By.id("lockerequipmentgrid_container");
    public static final By LBL_18_MODEL_TITLE = By.xpath("//div[@id='18door']//div");
    public static final By LBL_20_MODEL_TITLE = By.xpath("//div[@id='20door']//div");
    public static final By LBL_18_MODEL_IMG = By.xpath("//div[@id='18door']//div[@class='innerbox']");
    public static final By LBL_20_MODEL_IMG = By.xpath("//div[@id='20door']//div[@class='innerbox']");
    
    public By objLockerModel(String lockerModel) {
        return By.xpath("//td[@aria-describedby='lockerequipmentgrid_lockername']//a[text()='"+lockerModel+"']");
    }
    
	public Map<String, String> getLockerEquipmentTblHeadersUI(By tableGrid) {
		Map<String, String> uiTblHeaders = new HashMap<>();
		try {
			int index = 1;

			WebElement tableReports = getDriver().findElement(tableGrid);
			List<WebElement> columnHeaders = tableReports.findElements(By.tagName("th"));
			
			for (WebElement columnHeader : columnHeaders) {
				getDriver().findElement(By.cssSelector("#lockerequipmentgrid > thead > tr > th:nth-child(" + index + ")"));
				uiTblHeaders.put(columnHeader.getText(), columnHeader.getText());
				index++;
			}
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
		return uiTblHeaders;
	}
}
