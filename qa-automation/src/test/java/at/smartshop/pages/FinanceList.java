package at.smartshop.pages;

import org.openqa.selenium.By;

public class FinanceList {
	
	public static final By VALIDATE_HEADING = By.id("EFT Disbursement Process");
	public static final By DISBURSEMENT_HEADING = By.xpath("//th[@id='dataGrid_disbursementdate']");
	public static final By DISBURSEMENT_SORTING =  By.xpath("//div[@class='ui-iggrid-indicatorcontainer']");
	public static final By VALIDATE_DATE =  By.xpath("//td[text()='Friday - 02/05/2021']");
	public static final By VALIDATE_DISBURSEMENT_HEADING =  By.id("Disbursement Adjustment");
	public static final By SAVE_BTN =  By.id("saveBtn");
	public static final By ORG_ERROR =  By.id("org-error");
	public static final By NOTES_ERROR =  By.id("notes-error");
	public static final By CHOOSE_ORG =  By.id("select2-org-container");
	public static final By SELECT_ORG =  By.xpath("//span[@class='select2-search select2-search--dropdown']//input");
	public static final By CLICK_ORG =  By.xpath("//li[@class='select2-results__option select2-results__option--highlighted']");
	public static final By SELECT_LOC =  By.xpath("//input[@class='select2-search__field']");
	public static final By ENTER_LOC =  By.xpath("//li[@class='select2-results__option select2-results__option--highlighted']");
	public static final By SELECT_NOTES =  By.id("notes");
	public static final By SELECT_AMOUNT =  By.id("adjustment");
	public static final By CANCEL_BTN =  By.id("cancelBtn");
	public static final By CONFIRM_NO =  By.xpath("//button[@class='ajs-button ajs-cancel']");
	public static final By DOWNLOAD_DISBURSEMENT_REPORT =  By.xpath("//a[@onclick='downloadExcel(\"Friday - 02/05/2021\")']");
	public static final By VIEW_VARIANCE =  By.xpath("//a[@onclick='viewVarianceData(\"Friday - 02/05/2021\")']");
	public static final By DOWNLOAD_CSV_REPORT =  By.xpath("//a[@onclick='downloadCsv(\"Friday - 02/05/2021\")']");
	public static final By EXPORT_TOEXCEL =  By.id("viewvarianceexcel");
	public static final By BACK_TO_DISBURSEMENT_PAGE =  By.id("back-btn");
}
