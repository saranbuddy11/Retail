package at.smartshop.pages;

import org.openqa.selenium.By;

public class CreateLocation {

	public static final By INPUT_NAME = By.id("name");
	public static final By DPD_TIME_ZONE = By.id("timezone");
	public static final By DPD_TYPE = By.xpath("//*[@id='type-id']");
	public static final By DATE_PICKER_TOP_OFF = By.xpath("//input[@name='topoffsubsidystartdate' and @id='date']");
	public static final By DATE_PICKER_ROLL_OVER = By.xpath("//input[@name='rolloversubsidydate' and @id='date']");
}
