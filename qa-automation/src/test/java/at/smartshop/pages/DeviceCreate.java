package at.smartshop.pages;

import org.openqa.selenium.By;
import at.framework.ui.*;

public class DeviceCreate {
	private Foundation foundation=new Foundation();
	private TextBox textBox=new TextBox();
	private Dropdown dropdown=new Dropdown();
	public static final By TXT_NAME = By.id("name");
	public static final By BTN_SAVE = By.id("saveBtn");
	public static final By DPD_MSR_TYPE = By.id("msrtype");
	public static final By BTN_CANCEl = By.id("cancelBtn");
	
	public String createDevice(String deviceName) {
		textBox.enterText(TXT_NAME, deviceName);
		dropdown.selectItemByIndex(DPD_MSR_TYPE, 3);
		String msrType=dropdown.getSelectedItem(DPD_MSR_TYPE);
		foundation.click(BTN_SAVE);
		return msrType;
	}

}
