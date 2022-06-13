package at.smartshop.tests;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import at.framework.database.mssql.Queries;
import at.framework.database.mssql.ResultSets;
import at.framework.files.PropertyFile;
import at.framework.generic.CustomisedAssert;
import at.framework.generic.DateAndTime;
import at.framework.generic.Strings;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.database.columns.CNLocation;
import at.smartshop.database.columns.CNNavigationMenu;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.pages.ConsumerEngagement;
import at.smartshop.pages.NavigationBar;

@Listeners(at.framework.reportsetup.Listeners.class)
public class EgiftCards extends TestInfra {

	private PropertyFile propertyFile = new PropertyFile();
	private ResultSets dataBase = new ResultSets();
	private Foundation foundation = new Foundation();
	private NavigationBar navigationBar = new NavigationBar();
	private TextBox textBox = new TextBox();
	private Strings strings = new Strings();
	private DateAndTime dateAndTime = new DateAndTime();
	private ConsumerEngagement consumerEngagement = new ConsumerEngagement();

	private Map<String, String> rstNavigationMenuData;
	private Map<String, String> rstLocationData;

	@Test(description = "186472 - Validate the eGift Cards >Consumer Engagement Field"
			+ "186454 - Verify ADM > Promotions > Printable Gift Card PDF (layout)"
			+ "186455 - Verify ADM > Promotions > Gift Cards > Barcode Generated Structure"
			+ "186458 - Verify eGift cards Landing page is as per requirement"
			+ "186459 - Verify eGift cards Active tab is as per requirement"
			+ "186460 - Verify eGift cards Expired tab is as per requirement")
	public void verifyPrintableGiftCardPDF() {
		final String CASE_NUM = "186454";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);

		List<String> menu = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> requiredData = Arrays
				.asList(rstLocationData.get(CNLocation.INITIAL_BALANCE).split(Constants.DELIMITER_TILD));
		String expireDate = dateAndTime.getFutureDate(Constants.REGEX_MM_DD_YYYY, requiredData.get(1));
		String giftTitle = rstLocationData.get(CNLocation.TITLE) + strings.getRandomCharacter();
		List<String> status = Arrays.asList(rstLocationData.get(CNLocation.TAB_NAME).split(Constants.DELIMITER_TILD));
		List<String> actuals = Arrays
				.asList(rstLocationData.get(CNLocation.ACTUAL_DATA).split(Constants.DELIMITER_TILD));
		try {
			// Login to ADM with Super User, Select Org,
			navigationBar.launchBrowserAsSuperAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Navigate to Admin tab and verify Consumer Engagement Sub Tab is present or
			// not
			List<String> tabNames = navigationBar.getSubTabs(menu.get(2));
			CustomisedAssert.assertTrue(tabNames.contains(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION)));

			// Navigate to Menu Item and click Create Gift Card
			navigationBar.navigateToMenuItem(menu.get(0));
			foundation.waitforElementToBeVisible(ConsumerEngagement.PAGE_TITLE, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerEngagement.PAGE_TITLE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerEngagement.BTN_ADD_GIFT_CARD));
			foundation.scrollIntoViewElement(ConsumerEngagement.TBL_CONSUMER_ENGAGE);
			consumerEngagement.createGiftCard(giftTitle, requiredData.get(0), expireDate);

			// Verify Egift Card Active Tab and its content
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerEngagement.TAB_GIFT_CARD));
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerEngagement.TAB_ACTIVE));
			Map<Integer, Map<String, String>> uiTableData = consumerEngagement.getTblActiveRecordsUI();
			consumerEngagement.verifyContentofTableRecord(uiTableData, status.get(0));
			List<String> datas = foundation.getTextofListElement(ConsumerEngagement.BTN_ISSUE);
			for (int i = 0; i < datas.size(); i++) {
				CustomisedAssert.assertEquals(datas.get(i), status.get(2));
			}
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerEngagement.TAB_EXPIRED));

			// Verify the table to check created gift card is present or not
			Map<String, String> innerMap = new HashMap<>();
			String innerValue = " ";
			innerMap = uiTableData.get(0);
			innerValue = innerMap.get(CNLocation.TITLE);
			CustomisedAssert.assertEquals(innerValue, giftTitle);

			// Verify the Amount field in Active Gift Card
			for (int i = 0; i < uiTableData.size(); i++) {
				innerMap = uiTableData.get(i);
				innerValue = innerMap.get(actuals.get(0));
				CustomisedAssert.assertTrue(innerValue.contains(requiredData.get(6)));
			}

			// Verify the Expires field in Active Gift Card
			innerMap = uiTableData.get(0);
			innerValue = innerMap.get(actuals.get(1));
			CustomisedAssert.assertEquals(innerValue,
					dateAndTime.getFutureDate(Constants.REGEX_MM_DD_YYYY, requiredData.get(1)));

			// Verify the Issued field in Active Gift Card
			for (int i = 0; i < uiTableData.size(); i++) {
				innerMap = uiTableData.get(i);
				innerValue = innerMap.get(actuals.get(2));
				CustomisedAssert.assertTrue(foundation.isNumeric(innerValue));
			}

			// Click on created Gift card for print and validate its opening
			foundation.click(ConsumerEngagement.BTN_PRINT_FIRST_ROW);
			foundation.waitforElementToBeVisible(ConsumerEngagement.LBL_PRINT, Constants.SHORT_TIME);
			foundation.scrollIntoViewElement(ConsumerEngagement.LBL_PRINT);
			innerValue = foundation.getText(ConsumerEngagement.LBL_PRINT);
			String[] value = innerValue.split("\\s");
			CustomisedAssert.assertEquals(value[1], giftTitle);
			uiTableData.clear();

			// Check Print Gift Card with input cards to print and validate whether
			// downloaded or not
			textBox.enterText(ConsumerEngagement.INPUT_CARD_PRINT, requiredData.get(2));
			foundation.click(ConsumerEngagement.BTN_PRINT);
			foundation.threadWait(Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isFileDownloaded(rstLocationData.get(CNLocation.NAME)));

			// Read the content of PDF file to validate the content
			innerValue = foundation.getPDFFileActualName(rstLocationData.get(CNLocation.NAME));
			CustomisedAssert.assertEquals(foundation.getPDFFilePageCount(FilePath.PATH_TO_DOWNLOAD + "\\" + innerValue),
					requiredData.get(1));
			String pdfContent = foundation.readPDFFile(FilePath.PATH_TO_DOWNLOAD + "\\" + innerValue);
			CustomisedAssert.assertTrue(pdfContent.contains(giftTitle));
			int count = foundation.countOccurrences(pdfContent, giftTitle);
			CustomisedAssert.assertEquals(String.valueOf(count), requiredData.get(2));

			// Validating the Barcode Structure
			String actual = foundation.getParticularWordFromSentence(pdfContent, Integer.valueOf(requiredData.get(2)));
			CustomisedAssert.assertTrue(actual.startsWith(requiredData.get(3)));
			String s = requiredData.get(4);
			char c = s.charAt(0);
			count = foundation.countOccurrencesofChar(actual, c);
			CustomisedAssert.assertEquals(String.valueOf(count), requiredData.get(5));
			s = foundation.getNumbersFromString(actual);
			CustomisedAssert.assertTrue(foundation.isNumeric(s));
			CustomisedAssert.assertTrue(s.contains(requiredData.get(1)));

			// Verify Egift Card Expired Tab
			foundation.click(ConsumerEngagement.TAB_EXPIRED);
			foundation.waitforElementToBeVisible(ConsumerEngagement.TXT_EXPIRED_TITLE, Constants.THREE_SECOND);
			datas = foundation.getTextofListElement(ConsumerEngagement.TBL_HEADERS_EXPIRED_GRID);
			CustomisedAssert.assertEquals(datas.get(2), status.get(1));
			datas = foundation.getTextofListElement(ConsumerEngagement.TBL_EXPIRED);
			s = datas.get(0);
			value = s.split("\\s");
			CustomisedAssert.assertTrue(value[0].matches("[a-zA-Z]+"));
			CustomisedAssert.assertTrue(value[1].contains(requiredData.get(6)));
			value[2] = value[2].replaceAll("[^a-zA-Z0-9]+", "");
			CustomisedAssert.assertTrue(value[2].matches("[0-9]+"));
			CustomisedAssert.assertTrue(value[3].matches("[0-9]+"));

			// Delete the file
			foundation.deleteFile(FilePath.PATH_TO_DOWNLOAD + "\\" + innerValue);
			login.logout();
			browser.close();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
}
