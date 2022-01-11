package at.smartshop.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.aventstack.extentreports.Status;

import at.framework.reportsetup.ExtFactory;
import at.framework.ui.Foundation;
import at.smartshop.keys.Constants;

public class MultiTaxReport {
	private Foundation foundation = new Foundation();

	public static final By LBL_REPORT_NAME = By.id("Multi Tax Report");
	private static final By REPORT_ROW_EXPAND_BUTTON = By.cssSelector("#multiTaxLocationLevel > tbody > tr:nth-child(1) > td.ui-iggrid-expandcolumn > span");
	private static final By NO_DATA_AVAILABLE_IN_TABLE = By.xpath("//td[@class='dataTables_empty']");

	
	public void verifyReportName(String reportName) {
		try {
			foundation.waitforElement(LBL_REPORT_NAME, Constants.EXTRA_LONG_TIME);
			String reportTitle = foundation.getText(LBL_REPORT_NAME);
			Assert.assertTrue(reportTitle.contains(reportName));
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	public void checkForDataAvailabilyInResultTable() {
		try {
			if(foundation.isDisplayed(REPORT_ROW_EXPAND_BUTTON)){
				if(foundation.isDisplayed(NO_DATA_AVAILABLE_IN_TABLE)) {
					ExtFactory.getInstance().getExtent().log(Status.INFO, "No Data Available in Report Table");
					Assert.fail("Failed Report because No Data Available in Report Table");
				}else {
					ExtFactory.getInstance().getExtent().log(Status.INFO, "Report Data Available in the Table, Hence passing the Test case");
				}		
			}else {
				ExtFactory.getInstance().getExtent().log(Status.INFO, "No Report Table Available");
				Assert.fail("Failed Report because No Report Table Available");
			}
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
}