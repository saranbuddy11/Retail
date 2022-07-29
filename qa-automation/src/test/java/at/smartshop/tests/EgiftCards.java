package at.smartshop.tests;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.Rectangle;
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
import at.smartshop.pages.DeviceDashboard;
import at.smartshop.pages.DeviceList;
import at.smartshop.pages.DeviceSummary;
import at.smartshop.pages.LocationList;
import at.smartshop.pages.NavigationBar;
import at.smartshop.pages.UserList;

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

	private Map<String, String> rstNavigationMenuData;
	private Map<String, String> rstLocationData;
	private Map<String, String> rstDeviceListData;

	/**
	 * @author karthikr
	 */
	@Test(description = "186472 - Validate the eGift Cards >Consumer Engagement Field"
			+ "186454 - Verify ADM > Promotions > Printable Gift Card PDF (layout)"
			+ "186455 - Verify ADM > Promotions > Gift Cards > Barcode Generated Structure"
			+ "186458 - Verify eGift cards Landing page is as per requirement"
			+ "186459 - Verify eGift cards Active tab is as per requirement"
			+ "186460 - Verify eGift cards Expired tab is as per requirement"
			+ "186464 - Verify PIN is autogenerated for Printed eGift card in PDF format"
			+ "186465 - Verify PIN is autogenerated for an eGift card received to Consumer mail"
			+ "186468 - ADM > eGift Cards > Create New")
	public void verifyEGiftCardCreationAndPDFValidation() {
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
		List<String> mailIDs = Arrays.asList(rstLocationData.get(CNLocation.ADDRESS).split(Constants.DELIMITER_TILD));
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

			// Verify Add Gift Card Cancel field in Panel
			consumerEngagement.verifyAddGiftCardPanel();
			String s = foundation.getText(ConsumerEngagement.HEADER);
			CustomisedAssert.assertEquals(s, rstLocationData.get(CNLocation.INFO_NOTES));
			foundation.click(ConsumerEngagement.BTN_ADD_GIFT_SAVE);
			consumerEngagement.verifyGiftCardCreationFields(giftTitle, actuals.get(3), actuals.get(4));

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
			consumerEngagement.verifyAmountFieldInGiftCard(uiTableData, actuals.get(0), requiredData.get(6));

			// Verify the Expires field in Active Gift Card
			innerValue = innerMap.get(actuals.get(1));
			CustomisedAssert.assertEquals(innerValue,
					dateAndTime.getFutureDate(Constants.REGEX_MM_DD_YYYY, requiredData.get(1)));

			// Verify the Issued field in Active Gift Card
			consumerEngagement.verifyIssueFieldInGiftCard(uiTableData, actuals.get(2));

			// Click on created Gift card for print and validate its opening
			consumerEngagement.validateCreatedGiftCard(giftTitle);
			uiTableData.clear();

			// Check Print Gift Card with input cards to print and validate whether
			// downloaded or not
//			textBox.enterText(ConsumerEngagement.INPUT_CARD_PRINT, requiredData.get(2));
//			foundation.click(ConsumerEngagement.BTN_PRINT);
//			foundation.threadWait(Constants.SHORT_TIME);
			// CustomisedAssert.assertTrue(foundation.isFileDownloaded(rstLocationData.get(CNLocation.NAME)));

			// Read the content of PDF file to validate the content
//			innerValue = foundation.getPDFFileActualName(rstLocationData.get(CNLocation.NAME));
//			CustomisedAssert.assertEquals(foundation.getPDFFilePageCount(FilePath.PATH_TO_DOWNLOAD + "\\" + innerValue),
//					requiredData.get(1));
//			String pdfContent = foundation.readPDFFile(FilePath.PATH_TO_DOWNLOAD + "\\" + innerValue);
//			CustomisedAssert.assertTrue(pdfContent.contains(giftTitle));
//			int count = foundation.countOccurrences(pdfContent, giftTitle);
//			CustomisedAssert.assertEquals(String.valueOf(count), requiredData.get(2));

			// Validating the Barcode Structure
//			consumerEngagement.validateBarCodeStructure(pdfContent, requiredData.get(2), requiredData.get(3),
//					requiredData.get(4), requiredData.get(5), requiredData.get(1));
//
//			// Validating the PIN number generated for Egift cards
//			String actual = foundation.getParticularWordFromSentence(pdfContent, Integer.valueOf(requiredData.get(0)));
//			CustomisedAssert.assertEquals(String.valueOf(actual.length()), requiredData.get(7));
//			CustomisedAssert.assertTrue(foundation.isNumeric(actual));

			// Click on created Gift card for Issue and validate its opening
			consumerEngagement.verifyIssuePanelOnCreatedGiftCard(giftTitle);

			// Check Issue Gift Card by Email and its content fields
			consumerEngagement.verifyIssueGiftCardByMail(mailIDs.get(0));

			// Verify Egift Card Expired Tab
			consumerEngagement.validateGiftCardExpiredTabAndContent(status.get(1), requiredData.get(6));

			// Delete the file
			// foundation.deleteFile(FilePath.PATH_TO_DOWNLOAD + "\\" + innerValue);
			login.logout();
			browser.close();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * @author afrose
	 */
	@Test(description = "C186594-Verify the �Search� field"
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
			foundation.scrollIntoViewElement(ConsumerEngagement.CHECKBOX_SELECTALL);

			// Verify checkbox in GMA Consumer engagement grid
//			checkbox.check(ConsumerEngagement.CHECKBOX_SELECTALL);
//			foundation.waitforElementToBeVisible(ConsumerEngagement.TXT_SEARCH, Constants.SHORT_TIME);
//			checkbox.unCheck(ConsumerEngagement.CHECKBOX_SELECTALL);
			foundation.waitforElementToBeVisible(ConsumerEngagement.TXT_SEARCH, Constants.SHORT_TIME);
			textBox.enterText(ConsumerEngagement.TXT_SEARCH, requiredData.get(0));
			foundation.waitforElementToBeVisible(ConsumerEngagement.CHECKBOX_GIFTCARD, Constants.SHORT_TIME);
//			checkbox.check(ConsumerEngagement.CHECKBOX_GIFTCARD);
//			foundation.waitforElementToBeVisible(ConsumerEngagement.RECORDS_CONSUMER_GRID, Constants.SHORT_TIME);
//			checkbox.unCheck(ConsumerEngagement.CHECKBOX_GIFTCARD);

//			// Verify the First name header in grid data's
//			consumerEngagement.verifyColumnValuesInGrid(requiredData.get(1), requiredData.get(0));
//
//			// Verify the Last name header in grid data's
//			consumerEngagement.verifyColumnValuesInGrid(requiredData.get(2), requiredData.get(4));
//
//			// Verify the Email header in grid data's
//			consumerEngagement.verifyColumnValuesInGrid(requiredData.get(3), requiredData.get(5));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * @author afrose
	 */
	@Test(description = "C186593- Verify the field �Add Note� in By Location")

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
			consumerEngagement.verifyUserAbleToAddNoteFieldText(ConsumerEngagement.TXT_ADD_TO_NOTE, Datas.get(2));
			consumerEngagement.verifyUserAbleToAddNoteFieldText(ConsumerEngagement.TXT_ADD_TO_NOTE, Datas.get(3));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * @author afrose
	 */
	@Test(description = "C186592- Verify the field �Location of Recipients"
			+ "C186591- Verify the Issue� panel should have a label of �By Location")
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

			// Verify the �By Location� tabs in the Issue page
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

	/**
	 * @author afrose
	 */
	@Test(description = "C186590- verify the �MM Reload Method� has Gift card option in device summary page")

	public void verifyMMReloadMethods() {
		final String CASE_NUM = "186590";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstDeviceListData = dataBase.getDeviceListData(Queries.DEVICE_LIST, CASE_NUM);

		List<String> requiredData = Arrays
				.asList(rstDeviceListData.get(CNDeviceList.DEVICE).split(Constants.DELIMITER_TILD));
		List<String> devicename = Arrays
				.asList(rstDeviceListData.get(CNDeviceList.PRODUCT_NAME).split(Constants.DELIMITER_TILD));
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
			textBox.enterText(DeviceList.TXT_SEARCH_DEVICE, devicename.get(0));
			foundation.waitforElementToBeVisible(DeviceList.BTN_SEARCH, Constants.SHORT_TIME);
			foundation.objectClick(DeviceList.BTN_SEARCH);
			foundation.waitforElementToBeVisible(DeviceList.HEADER_DEVICE_NAME, Constants.SHORT_TIME);
			foundation.objectClick(devicelist.deveiceLink(devicename.get(0)));

			// Navigate to device summary page and verify e gift card
			CustomisedAssert.assertTrue(foundation.isDisplayed(DeviceSummary.MM_RELOAD_METHOD));
			CustomisedAssert.assertTrue(checkbox.isChecked(DeviceSummary.CHECKBOX_EGIFT_CARD));
			consumerEngagement.verifySFESectionOptions(devicename.get(1), requiredData);
			foundation.click(DeviceSummary.BTN_SAVE);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * @author afrose
	 */
	@Test(description = "C186583- Verify the �Enter Recipient Email (Comma separate individual addresses)� field in the �Issue� panel"
			+ "C186585- Verify the �Download and fill out the email eGift Card Template�in �Bulk Email Consumers� section "
			+ "C186584-Verify the �Bulk Email Consumers� field in the �Issue� panel"
			+ "C186586- Verify the �Click the 'Browse' in �Bulk Email Consumers� section")

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

	/**
	 * @author prabha
	 */
	@Test(description = "C186581- SOS-27896: Verify the Issue� panel should have a label of �By Email�"
			+ "C186582 - SOS-27896: Verify the 'Add Note' field in the �Issue� panel by Email")

	public void verifyByEmailTabAndAddNoteOnIssuePanel() {
		final String CASE_NUM = "186581";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);
		List<String> Datas = Arrays
				.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));

		try {
			// Login to ADM with Super User, Select Org,
			navigationBar.launchBrowserAsSuperAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Navigate to Admin->ConsuemrEngagement and create gift card
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerEngagement.PAGE_TITLE));

			// click on any issue button
			foundation.click(ConsumerEngagement.BTN_ISSUE_FIRST_ROW);

			// verify that By Location and By Email tab is present
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerEngagement.TAB_BY_LOCATION));
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerEngagement.TAB_BY_EMAIL));

			// Click on By Email and verify that add to note field with alphanumeric
			// characters & Special characters
			foundation.click(ConsumerEngagement.TAB_BY_EMAIL);
			consumerEngagement.verifyUserAbleToAddNoteFieldText(ConsumerEngagement.ADD_TO_NOTE_BY_EMAIL, Datas.get(0));
			consumerEngagement.verifyUserAbleToAddNoteFieldText(ConsumerEngagement.ADD_TO_NOTE_BY_EMAIL, Datas.get(1));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * @author prabha
	 */
	@Test(description = "186587-SOS-28932: verify the permission levels for E-Gift cards for operator"
			+ "186588 - SOS-28932: verify the permission levels for E-Gift cards for super"
			+ "186589 - SOS-28932: verify the permission levels for E-Gift cards for other than super and operator")
	public void verifyPermissionForOperatorSuperAndOtherRolesEGiftCard() {
		try {

			final String CASE_NUM = "186587";
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);
			List<String> lblRowRecord = Arrays
					.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			String Tab = rstLocationData.get(CNLocation.TAB_NAME);

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// verify Operator Role Permissions
			CustomisedAssert.assertTrue(foundation.isDisplayed(UserList.BTN_MANAGE_ROLES));
			foundation.click(UserList.BTN_MANAGE_ROLES);
			consumerEngagement.searchUserRolesAndNavigateToRolePermissions(lblRowRecord.get(0), Tab);
			consumerEngagement.verifyAllCheckboxesStatus(lblRowRecord.get(1), "true");

			// navigating back to verify Super Role Permissions
			foundation.navigateToBackPage();
			consumerEngagement.searchUserRolesAndNavigateToRolePermissions(lblRowRecord.get(2), Tab);
			consumerEngagement.verifyAllCheckboxesStatus(lblRowRecord.get(1), "true");

			// navigating back to verify Other Role Permissions(Driver,Finance,Reporter and
			// Hotel)
			foundation.navigateToBackPage();
			consumerEngagement.searchUserRolesAndNavigateToRolePermissions(lblRowRecord.get(3), Tab);
			consumerEngagement.verifyRolePermissionNotPresent(lblRowRecord.get(1));

			foundation.navigateToBackPage();
			consumerEngagement.searchUserRolesAndNavigateToRolePermissions(lblRowRecord.get(4), Tab);
			consumerEngagement.verifyRolePermissionNotPresent(lblRowRecord.get(1));

			foundation.navigateToBackPage();
			consumerEngagement.searchUserRolesAndNavigateToRolePermissions(lblRowRecord.get(5), Tab);
			consumerEngagement.verifyRolePermissionNotPresent(lblRowRecord.get(1));

			foundation.navigateToBackPage();
			consumerEngagement.searchUserRolesAndNavigateToRolePermissions(lblRowRecord.get(6), Tab);
			consumerEngagement.verifyRolePermissionNotPresent(lblRowRecord.get(1));
			login.logout();

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * @author santosh
	 */
	@Test(description = "C186469- Verify ADM > eGift Cards > Print Cards panel layout displayed as per Requirement"
			+ "C186470 - Verify Functionality of buttons in Print Cards panel when an individual eGfit Card is selected for Print from existing records"
			+ "C186471 - Verify body of Print Cards panel when an individual eGift Card is selected for Print from existing records grid")

	public void verifyeGiftCardsPrintCardsPanelLayout() {
		final String CASE_NUM = "186469";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);

		List<String> Datas = Arrays
				.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		String randomString = strings.getRandomCharacter();
		String giftTitle = rstLocationData.get(CNLocation.NAME) + randomString;
		String expireDate = dateAndTime.getFutureDate(Constants.REGEX_DD_MM_YYYY, Datas.get(1));
		List<String> values = Arrays
				.asList(rstLocationData.get(CNLocation.COLUMN_VALUE).split(Constants.DELIMITER_TILD));
		try {
			// Login to ADM with Super User, Select Org,
			navigationBar.launchBrowserAsSuperAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Navigate to Admin->ConsuemrEngagement and create gift card
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerEngagement.PAGE_TITLE));
			foundation.waitforElementToBeVisible(ConsumerEngagement.TBL_CONSUMER_ENGAGE, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerEngagement.TBL_CONSUMER_ENGAGE));
			foundation.waitforElementToBeVisible(ConsumerEngagement.BTN_ADD_GIFT_CARD, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerEngagement.BTN_ADD_GIFT_CARD));
			foundation.scrollIntoViewElement(ConsumerEngagement.BTN_ADD_GIFT_CARD);
			consumerEngagement.createGiftCard(giftTitle, Datas.get(0), expireDate);

			// click on issue with created gift card name
			foundation.click(ConsumerEngagement.BTN_PRINT_FIRST_ROW);
			foundation.waitforElementToBeVisible(ConsumerEngagement.LOCATION_OF_RECIPIENTS, Constants.SHORT_TIME);
			foundation.waitforElementToBeVisible(ConsumerEngagement.LBL_PRINT, Constants.SHORT_TIME);
			foundation.scrollIntoViewElement(ConsumerEngagement.LBL_PRINT);
			String innerValue = foundation.getText(ConsumerEngagement.LBL_PRINT);
			String[] value = innerValue.split("\\s");
			CustomisedAssert.assertEquals(value[1], giftTitle);
			// C186469
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerEngagement.BTN_PrintScreen_Cancel));
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerEngagement.BTN_PrintScreen_Print));

			// C186470
			foundation.click(ConsumerEngagement.BTN_PrintScreen_Cancel);
			CustomisedAssert.assertTrue(
					Datas.get(2).contains(foundation.getTextAttribute(ConsumerEngagement.Print_Panel, "style")));
			// click on issue with created gift card name
			foundation.click(ConsumerEngagement.BTN_PRINT_FIRST_ROW);
			foundation.waitforElementToBeVisible(ConsumerEngagement.LOCATION_OF_RECIPIENTS, Constants.SHORT_TIME);
			// C186471
			textBox.enterText(ConsumerEngagement.INPUT_CardToPrint, Datas.get(4));
			foundation.click(ConsumerEngagement.ADD_TO_NOTE);
			CustomisedAssert.assertTrue(
					foundation.getText(ConsumerEngagement.TXT_ErrorLabel_CardsToPrint).equals(values.get(0)));
			textBox.enterText(ConsumerEngagement.INPUT_CardToPrint, Datas.get(5));
			CustomisedAssert.assertTrue(
					foundation.getText(ConsumerEngagement.TXT_ErrorLabel_CardsToPrint).equals(values.get(1)));
			textBox.enterText(ConsumerEngagement.INPUT_CardToPrint, Datas.get(6));
			CustomisedAssert.assertTrue(
					foundation.getText(ConsumerEngagement.TXT_ErrorLabel_CardsToPrint).equals(values.get(1)));
			textBox.enterText(ConsumerEngagement.INPUT_CardToPrint, Datas.get(7));
			CustomisedAssert.assertTrue(
					foundation.getText(ConsumerEngagement.TXT_ErrorLabel_CardsToPrint).equals(values.get(2)));
			textBox.enterText(ConsumerEngagement.INPUT_CardToPrint, Datas.get(1));
			String randomAlphNumString = Datas.get(8);
			textBox.enterText(ConsumerEngagement.INPUT_AddNote_PrintScreen, randomAlphNumString);
			CustomisedAssert.assertEquals(
					foundation.getAttribute(ConsumerEngagement.INPUT_AddNote_PrintScreen, "value").length(), 100);
			textBox.enterText(ConsumerEngagement.INPUT_AddNote_PrintScreen, values.get(3));
			foundation.click(ConsumerEngagement.BTN_PrintScreen_Print);
			foundation.threadWait(Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isFileDownloaded(Datas.get(3)));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * @author karthikr
	 * @date 22-07-2022
	 */
	@Test(description = "198467 - Verify arrow icon toggle should change directions when expanding/collapsing."
			+ "198468 - Verify expiry date error messaging"
			+ "198469 - Verify expiry date error messaging when has No End date Check box is checked")
	public void verifyExpandCollapseFunctionalityAndExpDateValidationMessage() {
		final String CASE_NUM = "198467";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);

		List<String> menu = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		String title = strings.getRandomCharacter();
		List<String> requiredData = Arrays
				.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		try {
			// Login to ADM with Super User, Select Org,
			navigationBar.launchBrowserAsSuperAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Navigate to Menu Item and verifying Arrow Icon
			navigationBar.navigateToMenuItem(menu.get(0));
			consumerEngagement.verifyExpandAndCollapseGiftCardPanel(requiredData);

			// Verify Create Gift Card and its Date Field
			consumerEngagement.verifyErrorMsgOfCreateAddGiftCard(title, rstLocationData.get(CNLocation.ACTUAL_DATA),
					rstLocationData.get(CNLocation.INFO_MSG));

			// Create Egift Card without Expiration date and checked has no end date check
			// box
			consumerEngagement.validateCreationOfGiftCardWithoutExpirationDateAndNoEndDateChecked(title,
					rstLocationData.get(CNLocation.ACTUAL_DATA),
					rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));
			login.logout();
			browser.close();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	
	}

	/**
	 * @author afrosean Data: 27-07-2022
	 */
	@Test(description = "198470- verify the button size of issue and print button"
			+ "198472-Verify the error message when duplicate title is given in upper/lowercase"
			+ "198471- Verify the error message when duplicate title is given")
	public void verifyButtonSizeOfIssueAndPrintButon() {
		final String CASE_NUM = "198470";

		// Reading test data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);

		List<String> heigwid = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION).split(Constants.DELIMITER_TILD));
		List<String> requiredData = Arrays
				.asList(rstLocationData.get(CNLocation.INITIAL_BALANCE).split(Constants.DELIMITER_TILD));
		String expireDate = dateAndTime.getFutureDate(Constants.REGEX_DD_MM_YYYY, requiredData.get(2));

		try {
			// Login to ADM with Super User, Select Org
			navigationBar.launchBrowserAsSuperAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Navigate to Menu Item
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerEngagement.PAGE_TITLE));
			foundation.waitforElementToBeVisible(ConsumerEngagement.TBL_CONSUMER_ENGAGE_GRID, 5);
			CustomisedAssert.assertTrue(foundation.isDisplayed(ConsumerEngagement.TBL_CONSUMER_ENGAGE_GRID));

			// verify the dimension of issue button
			consumerEngagement.verifyDimentions(ConsumerEngagement.BTN_ISSUE_FIRST_ROW, heigwid.get(0), heigwid.get(1));

			// verify the dimension of print button
			consumerEngagement.verifyDimentions(ConsumerEngagement.BTN_PRINT_FIRST_ROW, heigwid.get(0), heigwid.get(1));

			// Create E-Gift card with lower case
			consumerEngagement.createGiftCard(requiredData.get(0), requiredData.get(1), expireDate);

			// validate the error
			consumerEngagement.verifyErrorMessageInTitle(requiredData.get(0), requiredData.get(3));

			// Create E-Gift card With Upper case
			consumerEngagement.createGiftCard(requiredData.get(4), requiredData.get(1), expireDate);

			// validate the error
			consumerEngagement.verifyErrorMessageInTitle(requiredData.get(4), requiredData.get(3));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
}