package at.smartshop.pages;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.By;
import org.testng.Assert;

import com.aventstack.extentreports.Status;

import at.framework.files.JsonFile;
import at.framework.files.PropertyFile;
import at.framework.reportsetup.ExtFactory;
import at.framework.ui.Foundation;
import at.smartshop.keys.Constants;
import at.smartshop.tests.TestInfra;
import at.smartshop.utilities.WebService;

public class CashFlowEmployeeDevice {
	private Foundation foundation = new Foundation();
	private List<Integer> requiredCount = new LinkedList<>();
	private WebService webService = new WebService();
	private JsonFile jsonFunctions = new JsonFile();
	private PropertyFile propertyFile = new PropertyFile();

	public static final By LBL_REPORT_NAME = By
			.cssSelector("#report-container > div:nth-child(3) > div.first-child > label");
	private static final By REPORT_GRID_FIRST_ROW = By.cssSelector("#Totals > tbody > tr:nth-child(1)");
	private static final By NO_DATA_AVAILABLE_IN_TABLE = By
			.xpath("//*[@id='Totals']//tbody//tr//td[@class='dataTables_empty']");

	public void verifyReportName(String reportName) {
		try {
			foundation.waitforElement(LBL_REPORT_NAME, Constants.EXTRA_LONG_TIME);
			String reportTitle = foundation.getText(LBL_REPORT_NAME);
			Assert.assertTrue(reportTitle.contains(reportName));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void checkForDataAvailabilyInResultTable() {
		try {
			if (foundation.isDisplayed(REPORT_GRID_FIRST_ROW)) {
				foundation.scrollIntoViewElement(REPORT_GRID_FIRST_ROW);
				if (foundation.isDisplayed(NO_DATA_AVAILABLE_IN_TABLE)) {
					ExtFactory.getInstance().getExtent().log(Status.INFO, "No Data Available in Report Table");
					Assert.fail("Failed Report because No Data Available in Report Table");
				} else {
					ExtFactory.getInstance().getExtent().log(Status.INFO,
							"Report Data Available in the Table, Hence passing the Test case");
				}
			} else {
				ExtFactory.getInstance().getExtent().log(Status.INFO, "No Report Table Available");
				Assert.fail("Failed Report because No Report Table Available");
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void processAPI(String transStatus, String paymentType) throws Exception {
		requiredCount.clear();
		int credCount = 0;
		int voidCredCount = 0;
		int declinedCredCount = 0;
		int accCount = 0;
		int accVoidCount = 0;
		int tipCount = 0;
		List<String> tStatus = Arrays.asList(transStatus.split(Constants.DELIMITER_HASH));
		List<String> payType = Arrays.asList(paymentType.split(Constants.DELIMITER_HASH));
		for (int iterator = 0; iterator < payType.size(); iterator++) {
			for (int iter = 0; iter < tStatus.size(); iter++) {
				webService.salesAPI(
						univCont.readFromPropertyFile(UniversalControls.SALES_TRANS_KEY,
								UniversalControls.TEST4_ENV_FILE),
						salesJsonDataUpdate(tStatus.get(iter), payType.get(iterator)));
				if (tStatus.get(iter).equals(UniversalControls.ACCEPTED)
						&& payType.get(iterator).equals(UniversalControls.CREDIT)) {
					credCount = credCount + 1;
					tipCount = tipCount + 1;
				} else if (tStatus.get(iter).equals(UniversalControls.VOID)
						&& payType.get(iterator).equals(UniversalControls.CREDIT)) {
					voidCredCount = voidCredCount + 1;
				} else if (tStatus.get(iter).equals(UniversalControls.REJECTED)
						&& payType.get(iterator).equals(UniversalControls.CREDIT)) {
					declinedCredCount = declinedCredCount + 1;
				} else if (tStatus.get(iter).equals(UniversalControls.ACCEPTED)
						&& payType.get(iterator).equals(UniversalControls.ACCOUNT)) {
					accCount = accCount + 1;
				} else if (tStatus.get(iter).equals(UniversalControls.VOID)
						&& payType.get(iterator).equals(UniversalControls.ACCOUNT)) {
					accVoidCount = accVoidCount + 1;
				}
			}
		}
		requiredCount.add(credCount);
		requiredCount.add(accCount);
		requiredCount.add(tipCount);
		requiredCount.add(accVoidCount);
		requiredCount.add(voidCredCount);
		requiredCount.add(declinedCredCount);
	}
}
