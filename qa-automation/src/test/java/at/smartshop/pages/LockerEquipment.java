package at.smartshop.pages;

import org.openqa.selenium.By;

public class LockerEquipment {
	public static final By TBL_LOCKER_EQUIPMENT_HEADER = By.cssSelector("#lockerequipmentgrid > thead > tr");
	public static final By TXT_PAGE_TITLE=By.xpath("//h4[text()='Locker Equipment']");
	public static final By LINK_MODEL=By.xpath("//a[text()='20 Door Satellite']");
	public static final By TXT_MODEL_NAME=By.xpath("//div[@id='20door']/div[1]");
	public static final By TBL_GRID=By.id("lockerequipmentgrid");
	public static final By TBL_ROW_1=By.cssSelector("#lockerequipmentgrid > tbody");                                    
	public static final By TBL_ROW_2=By.xpath("//table[@id='lockerequipmentgrid']/tbody/tr[2]");	
	public static final By LOCKER_EQUIPMENT_GRID = By.id("lockerequipmentgrid_container");
    public static final By LBL_18_MODEL_TITLE = By.xpath("//div[@id='18door']//div");
    public static final By LBL_20_MODEL_TITLE = By.xpath("//div[@id='20door']//div");
    public static final By LBL_18_MODEL_IMG = By.xpath("//div[@id='18door']//div[@class='innerbox']");
    public static final By LBL_20_MODEL_IMG = By.xpath("//div[@id='20door']//div[@class='innerbox']");
    
    public By objLockerModel(String lockerModel) {
        return By.xpath("//td[@aria-describedby='lockerequipmentgrid_lockername']//a[text()='"+lockerModel+"']");
    }
}
