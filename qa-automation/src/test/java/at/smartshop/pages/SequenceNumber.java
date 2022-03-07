package at.smartshop.pages;

import org.openqa.selenium.By;

import at.framework.browser.Factory;
import at.framework.generic.CustomisedAssert;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.keys.Constants;
import at.smartshop.tests.TestInfra;

public class SequenceNumber extends Factory {
	
	public static final By VALIDATE_SEQLIST_HEADING = By.id("SeqNbr List");
	public static final By SEQLIST_SEARCH_BOX = By.xpath("//div[@id='dt_filter']//input");
	public static final By BTN_CREATE = By.id("newBtn");
	public static final By VALIDATE_SEQCREATE_HEADING = By.id("SeqNbr Create");
	public static final By TXT_SOURCE = By.id("sourcekey");
	public static final By TXT_TYPE = By.xpath("//select[@id='type']");
	public static final By BTN_CANCEL= By.id("cancelBtn");
	public static final By BTN_SAVE = By.id("saveBtn");
	public static final By SEQ_GRID = By.xpath("//td[@class=' sorting_1']");
	public static final By SEQ_SOURCE_ERROR= By.id("sourcekey-error");
	public static final By SEQ_TYPE_ERROR= By.id("type-error");
	public static final By VALIDATE_SEQSHOW_HEADING = By.id("Seqnbr Show");
	
	private TextBox textBox = new TextBox();
	private Foundation foundation = new Foundation();
	private Dropdown dropDown = new Dropdown();
	
	public void createSeqNbr(String seq_Source, String seq_Type) {
		try {
			foundation.click(BTN_CREATE);
			textBox.enterText(TXT_SOURCE, seq_Source);
			dropDown.selectItem(TXT_TYPE,seq_Type, Constants.TEXT);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
	
	public void updatingSeqNbr(String search_seqNbr_record, String seqNbr_Show_Page, String update_seqNbr_record) {
		try {
			textBox.enterText(SequenceNumber.SEQLIST_SEARCH_BOX, search_seqNbr_record);
			foundation.click(SequenceNumber.SEQ_GRID);			
			foundation.waitforElement(SequenceNumber.VALIDATE_SEQSHOW_HEADING, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(SequenceNumber.VALIDATE_SEQSHOW_HEADING),seqNbr_Show_Page);
			
			//Update the changes, cancel and save
			textBox.enterText(SequenceNumber.TXT_SOURCE, update_seqNbr_record);
			foundation.click(SequenceNumber.BTN_CANCEL);
			foundation.click(SequenceNumber.SEQ_GRID);
			CustomisedAssert.assertTrue(foundation.isDisplayed(SequenceNumber.VALIDATE_SEQSHOW_HEADING),seqNbr_Show_Page);
			textBox.enterText(SequenceNumber.TXT_SOURCE, update_seqNbr_record);
			foundation.click(SequenceNumber.BTN_SAVE);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
}
