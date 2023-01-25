package at.smartshop.sos.pages;

import java.util.List;

import org.openqa.selenium.By;

import at.framework.browser.Factory;
import at.framework.generic.CustomisedAssert;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.Radio;
import at.framework.ui.TextBox;
import at.smartshop.keys.Constants;

public class LoadQueue extends Factory {

	Dropdown dropDown = new Dropdown();
	TextBox textBox = new TextBox();
	Radio radio = new Radio();
	Foundation foundation = new Foundation();

	public static final By LBL_QUEUE = By.xpath("//div[contains(text(),'Queued Processes')]");
	public static final By TBL_DATA = By.xpath("(//tbody[@class='ui-widget-content ui-iggrid-tablebody ui-ig-record ui-iggrid-record']//tr)[1]");
	public static final By TXT_SEARCH = By.id("queueFilterType");
	public static final By TBL_HEADER = By.xpath("//thead//th//span[@class='ui-iggrid-headertext']");
	public static final By LBL_SHOW_RECORD = By.xpath("//div[@class='ui-iggrid-results']");
	public static final By LBL_RECORDS_COUNT = By.id("queueListGrid_pager_label");
	public static final By LBL_PAGE_NAVIGATION = By.xpath("//div[@class='ui-iggrid-paging']");
	public static final By CLICK_TYPE= By.xpath("//td[@aria-describedby='queueListGrid_type']");
	public static final By DPD_SHOW_RECORD = By.xpath("/span[@id='queueListGrid_editor']");
	public static final By TBL_REQUIRED_DATA = By.xpath("(//tbody[@class='ui-widget-content ui-iggrid-tablebody ui-ig-record ui-iggrid-record']//tr)[1]//td");
	public static final By GET_TIME = By.xpath("//td[@aria-describedby='queueListGrid_time']");
	public static final By PAGE_NAVIGATION = By.xpath("//li[@title='Page 1']");
	public static final By SHOW_COUNT = By.xpath("//div[@class='ui-icon ui-icon-triangle-1-s ui-igedit-buttonimage']");
	public static final By DPD_SHOW_COUNT = By.id("queueListGrid_editorEditingInput");
	public static final By LBL_PROCESS = By.xpath("//div[contains(text(),'Process Logs')]");
	public static final By LBL_RECORDS_COUNT_PROCESS = By.id("queueErrorGrid_pager_label");

	public final String SHEET = "Sheet1";
	
	/**
	 * verify All Field And Functionality In Queue Page
	 * 
	 * @param header
	 * @param commercialName
	 * @param org
	 * @param user
	 * @param currentDate
	 * @param status
	 * @param nav
	 * @param show
	 * @param record
	 * 
	 */
	public void verifyAllFieldAndFunctionalityInQueuePage(List<String> header,String commercialName,String org, String user,String currentDate,String status,
			String nav, String show,String record) {
		
	//verify the table header
	CustomisedAssert.assertTrue(foundation.getTextofListElement(LoadQueue.TBL_HEADER).equals(header));	
	//verify search functionality
	CustomisedAssert.assertTrue(foundation.isDisplayed(LoadQueue.TXT_SEARCH));
	textBox.enterText(LoadQueue.TXT_SEARCH,commercialName);
	List <String> data=foundation.getTextofListElement(LoadQueue.TBL_REQUIRED_DATA);
	CustomisedAssert.assertTrue(data.contains(commercialName));	
	//verify data column
	foundation.threadWait(Constants.THREE_SECOND);
	CustomisedAssert.assertTrue(data.contains(org) && data.contains(user) && data.contains(commercialName)
			&& foundation.getText(LoadQueue.GET_TIME).contains(currentDate) && data.contains(status),"[Fail]: No text found");
	//verify the record
	foundation.threadWait(Constants.THREE_SECOND);
	foundation.waitforElementToBeVisible(LoadQueue.LBL_RECORDS_COUNT, 5);
	String s = foundation.getText(LoadQueue.LBL_RECORDS_COUNT);
	String[] str = s.split(" ");
	for (int i = 0; i < str.length; i++) {
	}
	CustomisedAssert.assertTrue(s.contains("1 - ") && s.contains("of") && s.contains("records"),
			"[Fail]: No total record text found");
	CustomisedAssert.assertTrue(str[2].equals(str[4]));
	//verify Page navigation
	foundation.threadWait(Constants.THREE_SECOND);
	CustomisedAssert.assertTrue(foundation.isDisplayed(LoadQueue.LBL_PAGE_NAVIGATION));
	CustomisedAssert.assertTrue(foundation.getBGColor(LoadQueue.PAGE_NAVIGATION).equals(nav));
	
	//verify show count
	foundation.threadWait(Constants.THREE_SECOND);
	CustomisedAssert.assertTrue(foundation.getText(LoadQueue.LBL_SHOW_RECORD).contains(show) && 
			foundation.getText(LoadQueue.LBL_SHOW_RECORD).contains(record),"[Fail]: No text found");
	
   }
	
	
	/**
	 * verify All Field And Functionality In Process log
	 * 
	 * @param header
	 * @param Position
	 * @param content
	 * @param nav
	 * @param show
	 * @param record
	 * 
	 */
	public void verifyAllFieldAndFunctionalityInProcesslog(List<String> header,String Position,List<String> content,String nav, String show,String record) {
		
	//verify the table header
	CustomisedAssert.assertTrue(foundation.getTextofListElement(LoadQueue.TBL_HEADER).equals(header));	
	//verify search functionality
	CustomisedAssert.assertTrue(foundation.isDisplayed(LoadQueue.TXT_SEARCH));
	textBox.enterText(LoadQueue.TXT_SEARCH,Position);
	List <String> data=foundation.getTextofListElement(LoadQueue.TBL_REQUIRED_DATA);
	CustomisedAssert.assertTrue(data.contains(Position));	
	//verify data column
	foundation.threadWait(Constants.THREE_SECOND);
	CustomisedAssert.assertTrue(data.equals(content));
	//verify the record
	foundation.threadWait(Constants.THREE_SECOND);
	foundation.waitforElementToBeVisible(LoadQueue.LBL_RECORDS_COUNT_PROCESS, 5);
	String s = foundation.getText(LoadQueue.LBL_RECORDS_COUNT_PROCESS);
	String[] str = s.split(" ");
	for (int i = 0; i < str.length; i++) {
	}
	CustomisedAssert.assertTrue(s.contains("1 - ") && s.contains("of") && s.contains("records"),
			"[Fail]: No total record text found");
	CustomisedAssert.assertTrue(str[2].equals(str[4]));
	//verify Page navigation
	foundation.threadWait(Constants.THREE_SECOND);
	CustomisedAssert.assertTrue(foundation.isDisplayed(LoadQueue.LBL_PAGE_NAVIGATION));
	CustomisedAssert.assertTrue(foundation.getBGColor(LoadQueue.PAGE_NAVIGATION).equals(nav));
	
	//verify show count
	foundation.threadWait(Constants.THREE_SECOND);
	CustomisedAssert.assertTrue(foundation.getText(LoadQueue.LBL_SHOW_RECORD).contains(show) && 
			foundation.getText(LoadQueue.LBL_SHOW_RECORD).contains(record),"[Fail]: No text found");
	
   }
}

