package at.smartshop.pages;

import java.util.List;

import org.openqa.selenium.By;

import at.framework.browser.Factory;
import at.framework.generic.CustomisedAssert;
import at.framework.ui.CheckBox;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.smartshop.keys.Constants;

public class AdminRoundUpCharity extends Factory {

	private Foundation foundation = new Foundation();
	private Dropdown dropDown = new Dropdown();
	private CheckBox checkBox = new CheckBox();

	public static final By DPD_CHARITY_ROUNDUP = By.id("roundupcharity");

	/**
	 * verify charity dropdown options
	 * 
	 * @param values
	 * @param object
	 */
	public void verifyCharityDropdown(List<String> values, By object) {
		for (int i = 0; i < values.size(); i++) {
			List<String> actual = dropDown.getAllItems(object);
			CustomisedAssert.assertEquals(actual, values);
		}
	}

	/**
	 * verify charity option and check submenu
	 * 
	 * @param equaloption
	 * @param option
	 */
	public void verifyCharityOptions( String option) {
		foundation.waitforElementToBeVisible(DPD_CHARITY_ROUNDUP, Constants.THREE_SECOND);
		dropDown.selectItem(DPD_CHARITY_ROUNDUP, option, Constants.TEXT);
		foundation.waitforElementToBeVisible(OrgSummary.BTN_SAVE, Constants.THREE_SECOND);
		foundation.click(OrgSummary.BTN_SAVE);
		foundation.threadWait(Constants.THREE_SECOND);
	}
}
