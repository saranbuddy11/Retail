package at.smartshop.pages;

import org.openqa.selenium.By;

public class Report {

	public static final By LBL_REPORT_LIST = By.id("Report List");
	public static final By LBL_CREATE_REPORT = By.id("Create Report");
	public static final By LBL_REPORT_SUMMARY = By.id("Report Summary");
	public static final By BTN_CREATE_NEW = By.id("newBtn");
	public static final By TXT_NAME = By.id("name");
	public static final By TXT_ACTION = By.id("rptaction");
	public static final By DRP_PARAMDATE = By.id("paramdate");
	public static final By DRP_PARAMSELECT = By.id("paramselect");
	public static final By TXT_SEQ = By.id("seqnbr"); 
	public static final By TXT_FILTER = By.id("filterType");
	public static final By LBL_ORG = By.xpath("//td[@aria-describedby=\"dataGridReports_name\"]");
	public static final By CHCKBX_ORG = By.xpath("//td[@aria-describedby=\"dataGridReports_activeCheckbox\"]//input");
	public static final By BTN_SAVE = By.id("saveBtn");
	public static final By BTN_CANCEL = By.id("cancelBtn");
	public static final By TXT_FILTER_REPORT = By.xpath("//input[@aria-controls='dt']");
	public static final By SELECT_GRID = By.xpath("//td[@class=' sorting_1']");
	
}
