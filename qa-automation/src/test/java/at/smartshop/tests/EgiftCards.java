package at.smartshop.tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import at.framework.database.mssql.Queries;
import at.framework.database.mssql.ResultSets;
import at.framework.files.Excel;
import at.framework.files.PropertyFile;
import at.framework.generic.CustomisedAssert;
import at.framework.generic.DateAndTime;
import at.framework.generic.Strings;
import at.framework.ui.CheckBox;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.database.columns.CNDeviceList;
import at.smartshop.database.columns.CNLocation;
import at.smartshop.database.columns.CNNavigationMenu;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.pages.ConsumerEngagement;
import at.smartshop.pages.CreatePromotions;
import at.smartshop.pages.DeviceDashboard;
import at.smartshop.pages.DeviceList;
import at.smartshop.pages.DeviceSummary;
import at.smartshop.pages.LocationList;
import at.smartshop.pages.LocationSummary;
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
	private CheckBox checkbox = new CheckBox();
	private DeviceList devicelist = new DeviceList();
	private Excel excel = new Excel();
	private DeviceSummary devicesummary = new DeviceSummary();

	private Map<String, String> rstNavigationMenuData;
	private Map<String, String> rstLocationData;
	private Map<String, String> rstDeviceListData;

	@Test(description = "186472 - Validate the eGift Cards >Consumer Engagement Field"
			+ "186454 - Verify ADM > Promotions > Printable Gift Card PDF (layout)"
			+ "186455 - Verify ADM > Promotions > Gift Cards > Barcode Generated Structure")
	public void verifyPrintableGiftCardPDF() {
		final String CASE_NUM = "186454";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);

		List<String> menu = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> requiredData = Arrays
				.asList(rstLocationData.get(CNLocation.INITIAL_BALANCE).split(Constants.DELIMITER_TILD));
		String expireDate = dateAndTime.getFutureDate(Constants.REGEX_DD_MM_YYYY, requiredData.get(1));
		String giftTitle = rstLocationData.get(CNLocation.TITLE) + strings.getRandomCharacter();
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

			// Verify the table to check created gift card is present or not
			Map<Integer, Map<String, String>> uiTableData = consumerEngagement.getTblRecordsUI();
			Map<String, String> innerMap = new HashMap<>();
			String innerValue = " ";
			innerMap = uiTableData.get(0);
			innerValue = innerMap.get(CNLocation.TITLE);
			CustomisedAssert.assertEquals(innerValue, giftTitle);

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

			// Delete the file
			foundation.deleteFile(FilePath.PATH_TO_DOWNLOAD + "\\" + innerValue);
			login.logout();
			browser.close();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "C186594-Verify the “Search” field"
			+ "C186596-Verify the column that are available in GMA consumer grid"
			+ "C186595-Verify the GMA consumer grid")
	public void verifyEgiftCardsConsumerSearchAndColumnsInGMAConsumerGrid() {
		final String CASE_NUM = "186594";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);

		List<String> Datas = Arrays
				.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> requiredData = Arrays
				.asList(rstLocationData.get(CNLocation.SHOW_RECORDS).split(Constants.DELIMITER_TILD));
		String giftTitle = rstLocationData.get(CNLocation.NAME) + strings.getRandomCharacter();
		String expireDate = dateAndTime.getFutureDate(Constants.REGEX_DD_MM_YYYY, Datas.get(1));
		try {
			// Login to ADM with Super User, Select Org,
			navigationBar.launchBrowserAsSuperAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Navigate to Admin->ConsuemrEngagement and create gift card
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerEngagement.PAGE_TITLE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerEngagement.BTN_ADD_GIFT_CARD));
			foundation.scrollIntoViewElement(ConsumerEngagement.BTN_ADD_GIFT_CARD);
			consumerEngagement.createGiftCard(giftTitle, Datas.get(0), expireDate);

			// click on issue with created gift card name
			foundation.click(ConsumerEngagement.BTN_ISSUE_FIRST_ROW);
			foundation.waitforElementToBeVisible(ConsumerEngagement.ADD_TO_NOTE, Constants.SHORT_TIME);

			// Verify the column in GMA consumer grid
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerEngagement.TBL_GRID));
			foundation.waitforElementToBeVisible(ConsumerEngagement.ADD_TO_NOTE, Constants.SHORT_TIME);
			consumerEngagement.verifyGMAConsumerEngagement(requiredData);
			foundation.waitforElementToBeVisible(ConsumerEngagement.TXT_SEARCH, Constants.SHORT_TIME);

			// Verify checkbox in GMA Consumer engagement grid
			checkbox.check(ConsumerEngagement.CHECKBOX_SELECTALL);
			foundation.waitforElementToBeVisible(ConsumerEngagement.TXT_SEARCH, Constants.SHORT_TIME);
			checkbox.unCheck(ConsumerEngagement.CHECKBOX_SELECTALL);
			foundation.waitforElementToBeVisible(ConsumerEngagement.TXT_SEARCH, Constants.SHORT_TIME);
			textBox.enterText(ConsumerEngagement.TXT_SEARCH, requiredData.get(0));
			foundation.waitforElementToBeVisible(ConsumerEngagement.CHECKBOX_GIFTCARD, Constants.SHORT_TIME);
			checkbox.check(ConsumerEngagement.CHECKBOX_GIFTCARD);
			foundation.waitforElementToBeVisible(ConsumerEngagement.RECORDS_CONSUMER_GRID, Constants.SHORT_TIME);
			checkbox.unCheck(ConsumerEngagement.CHECKBOX_GIFTCARD);

			// Verify the First name header in grid data's
			consumerEngagement.verifyGridDatas(requiredData.get(1), requiredData.get(0));

			// Verify the Last name header in grid data's
			consumerEngagement.verifyGridDatas(requiredData.get(2), requiredData.get(4));

			// Verify the Email header in grid data's
			consumerEngagement.verifyGridDatas(requiredData.get(3), requiredData.get(5));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "C186593- Verify the field “Add Note” in By Location")

	public void verifyAddNoteFieldByLocation() {
		final String CASE_NUM = "186593";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);

		List<String> Datas = Arrays
				.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		String giftTitle = rstLocationData.get(CNLocation.NAME) + strings.getRandomCharacter();
		String expireDate = dateAndTime.getFutureDate(Constants.REGEX_DD_MM_YYYY, Datas.get(1));

		try {
			// Login to ADM with Super User, Select Org,
			navigationBar.launchBrowserAsSuperAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Navigate to Admin->ConsuemrEngagement and create gift card
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerEngagement.PAGE_TITLE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerEngagement.BTN_ADD_GIFT_CARD));
			foundation.scrollIntoViewElement(ConsumerEngagement.BTN_ADD_GIFT_CARD);
			consumerEngagement.createGiftCard(giftTitle, Datas.get(0), expireDate);

			// click on issue with created gift card name
			foundation.click(ConsumerEngagement.BTN_ISSUE_FIRST_ROW);
			foundation.waitforElementToBeVisible(ConsumerEngagement.ADD_TO_NOTE, Constants.SHORT_TIME);

			// verify the add to note field with alphanumeric Special characters & enter up
			// to 100 characters
			textBox.enterText(ConsumerEngagement.TXT_ADD_TO_NOTE, Datas.get(2));
			String text = foundation.getText(ConsumerEngagement.TXT_ADD_TO_NOTE);
			CustomisedAssert.assertEquals(text, Datas.get(2));
			foundation.waitforElementToBeVisible(ConsumerEngagement.TXT_SEARCH, Constants.SHORT_TIME);
			textBox.enterText(ConsumerEngagement.TXT_ADD_TO_NOTE, Datas.get(3));
			text = foundation.getText(ConsumerEngagement.TXT_ADD_TO_NOTE);
			CustomisedAssert.assertEquals(text, Datas.get(3));
			foundation.waitforElementToBeVisible(ConsumerEngagement.TXT_SEARCH, Constants.SHORT_TIME);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "C186592- Verify the field “Location of Recipients"
			+ "C186591- Verify the Issue” panel should have a label of “By Location")
	public void verifyLocationOfRecipientsField() {
		final String CASE_NUM = "186592";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);

		List<String> Datas = Arrays
				.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> location = Arrays
				.asList(rstLocationData.get(CNLocation.LOCATION_NAME).split(Constants.DELIMITER_TILD));
		String giftTitle = rstLocationData.get(CNLocation.NAME) + strings.getRandomCharacter();
		String expireDate = dateAndTime.getFutureDate(Constants.REGEX_DD_MM_YYYY, Datas.get(1));

		try {
			// Login to ADM with Super User, Select Org,
			navigationBar.launchBrowserAsSuperAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Navigate to Admin->ConsuemrEngagement and create gift card
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerEngagement.PAGE_TITLE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerEngagement.BTN_ADD_GIFT_CARD));
			foundation.scrollIntoViewElement(ConsumerEngagement.BTN_ADD_GIFT_CARD);
			consumerEngagement.createGiftCard(giftTitle, Datas.get(0), expireDate);

			// click on issue with created gift card name
			foundation.click(ConsumerEngagement.BTN_ISSUE_FIRST_ROW);
			foundation.waitforElementToBeVisible(ConsumerEngagement.LOCATION_OF_RECIPIENTS, Constants.SHORT_TIME);

			// Verify the “By Location” tabs in the Issue page
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerEngagement.LOCATION_TAB));

			// verify DropDown in location of recipient
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerEngagement.DPD_LOCATION));
			foundation.click(ConsumerEngagement.DPD_LOCATION);
			textBox.enterText(ConsumerEngagement.TXT_LOCATION_ENGAGEMENT, location.get(1));
			foundation.click(consumerEngagement.objSearchLocation(location.get(1)));
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerEngagement.DPD_CLEAR));
			String loc = foundation.getText(ConsumerEngagement.DPD_ALL_LOCATION);
			CustomisedAssert.assertEquals(loc, location.get(1));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "C186590- verify the “MM Reload Method” has Gift card option in device summary page")

	public void verifyMMReloadMethods() {
		final String CASE_NUM = "186590";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstDeviceListData = dataBase.getDeviceListData(Queries.DEVICE_LIST, CASE_NUM);

		List<String> requiredData = Arrays
				.asList(rstDeviceListData.get(CNDeviceList.DEVICE).split(Constants.DELIMITER_TILD));
		try {
			// Login to ADM with Super User, Select Org,
			navigationBar.launchBrowserAsSuperAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Navigate to Admin->ConsuemrEngagement and create gift card
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			CustomisedAssert.assertTrue(foundation.isDisplayed(DeviceDashboard.LBL_ADMIN_DEVICE_DASHBOARD));
			foundation.adjustBrowerSize("0.7");
			foundation.threadWait(Constants.SHORT_TIME);
			textBox.enterText(DeviceList.TXT_SEARCH_DEVICE, rstDeviceListData.get(CNDeviceList.PRODUCT_NAME));
			foundation.waitforElementToBeVisible(DeviceList.BTN_SEARCH, Constants.SHORT_TIME);
			foundation.objectClick(DeviceList.BTN_SEARCH);
			foundation.waitforElementToBeVisible(DeviceList.HEADER_DEVICE_NAME, Constants.SHORT_TIME);
			foundation.objectClick(devicelist.deveiceLink(rstDeviceListData.get(CNDeviceList.PRODUCT_NAME)));

			// Navigate to device summary page and verify e gift card
			CustomisedAssert.assertTrue(foundation.isDisplayed(DeviceSummary.MM_RELOAD_METHOD));
			CustomisedAssert.assertTrue(checkbox.isChecked(DeviceSummary.CHECKBOX_EGIFT_CARD));
			String groupData = foundation.getText(devicesummary.objMMReloadDatas(requiredData.get(3)));
			String[] value = groupData.split("\\R");
			CustomisedAssert.assertEquals(value[0], requiredData.get(0));
			CustomisedAssert.assertEquals(value[1], requiredData.get(1));
			CustomisedAssert.assertEquals(value[2], requiredData.get(2));
			foundation.click(DeviceSummary.BTN_SAVE);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "C186583- Verify the “Enter Recipient Email (Comma separate individual addresses)” field in the “Issue” panel"
			+ "C186585- Verify the “Download and fill out the email eGift Card Template”in “Bulk Email Consumers” section "
			+ "C186584-Verify the “Bulk Email Consumers” field in the “Issue” panel"
			+ "C186586- Verify the “Click the 'Browse' in “Bulk Email Consumers” section")

	public void verifyEnteringRecipientEmailAndBulkEmailConsumersSection() {
		final String CASE_NUM = "186583";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);

		List<String> Datas = Arrays
				.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> mail = Arrays
				.asList(rstLocationData.get(CNLocation.CONTACT_EMAIL).split(Constants.DELIMITER_TILD));
		String giftTitle = rstLocationData.get(CNLocation.NAME) + strings.getRandomCharacter();
		String expireDate = dateAndTime.getFutureDate(Constants.REGEX_DD_MM_YYYY, Datas.get(1));

		try {
			// Login to ADM with Super User, Select Org,
			navigationBar.launchBrowserAsSuperAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Navigate to Admin->ConsuemrEngagement and create gift card
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerEngagement.PAGE_TITLE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerEngagement.BTN_ADD_GIFT_CARD));
			foundation.scrollIntoViewElement(ConsumerEngagement.BTN_ADD_GIFT_CARD);
			consumerEngagement.createGiftCard(giftTitle, Datas.get(0), expireDate);

			// click on issue with created gift card name
			foundation.click(ConsumerEngagement.BTN_ISSUE_FIRST_ROW);
			foundation.waitforElementToBeVisible(ConsumerEngagement.ADD_TO_NOTE, Constants.SHORT_TIME);

			// Click on email filter and verify enter recipient email
			foundation.click(ConsumerEngagement.BY_EMAIL_FILTER);
			foundation.waitforElementToBeVisible(ConsumerEngagement.ENTER_RECIPIENT_EMAIL, Constants.TWO_SECOND);
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerEngagement.ENTER_RECIPIENT_EMAIL));
			textBox.enterText(ConsumerEngagement.TXT_ENTER_RECIPIENT, mail.get(0));
			foundation.waitforElementToBeVisible(ConsumerEngagement.BTN_BROWSE, Constants.TWO_SECOND);
			textBox.enterText(ConsumerEngagement.TXT_ENTER_RECIPIENT, mail.get(1));

			// verify the 'Bulk Email Consumers'
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerEngagement.HEADER_ADDTONOTE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerEngagement.ENTER_RECIPIENT_EMAIL));
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerEngagement.BULK_EMAIL_CONSUMER));

			// click on egift card template and download
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerEngagement.TXT_DOWNLOAD_FILLOUTEMAIL));
			String color = foundation.getTextColor(ConsumerEngagement.EGIFT_CARD_TEMPLATE);
			CustomisedAssert.assertEquals(color, mail.get(2));
			foundation.click(ConsumerEngagement.EGIFT_CARD_TEMPLATE);
			foundation.threadWait(Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(excel.isFileDownloaded(FilePath.GIFT_CARDS));

			// Delete the file
			foundation.deleteFile(FilePath.GIFT_CARDS);

			// click on browser and select egift card
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerEngagement.IMPORTANT_LINE));
			textBox.enterText(ConsumerEngagement.BTN_BROWSE, FilePath.IMAGE_PNG_PATH);
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerEngagement.ERROR_MSG));
			foundation.waitforElementToBeVisible(ConsumerEngagement.BTN_BROWSE, Constants.TWO_SECOND);
			textBox.enterText(ConsumerEngagement.BTN_BROWSE, FilePath.EGIFT_CARD_TEMPLATE);
			foundation.click(ConsumerEngagement.BTN_EMAIL_CARDS);
			foundation.waitforElementToBeVisible(ConsumerEngagement.PAGE_TITLE, Constants.TWO_SECOND);
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerEngagement.PAGE_TITLE));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
}