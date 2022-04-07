@Test(description = "169165-QAA-287 ADM > Super > Campus > Validate error message for Mandatory Fields when Payroll Deduct is On or Off"
			+ "169175 - QAA-287 ADM > Super > Campus > Validation of all Fields for invalid characters and data"
			+ "169167 - QAA-287 ADM > Super > Campus > Create a new Campus with all details including Location and Orgs")
	public void CampusValidateAllFields() {
		final String CASE_NUM = "169165";
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstSuperListData = dataBase.getSuperListData(Queries.SUPER, CASE_NUM);

		String campusName = strings.getRandomCharacter();

		List<String> validateHeading = Arrays
				.asList(rstSuperListData.get(CNSuperList.SUPER_NAME).split(Constants.DELIMITER_TILD));
		String campusList_Page = validateHeading.get(0);
		String campusSave_Page = validateHeading.get(1);

		List<String> errorMessage = Arrays
				.asList(rstSuperListData.get(CNSuperList.ERROR_MESSAGE).split(Constants.DELIMITER_TILD));
		String mandatory_Error = errorMessage.get(0);
		String groupName_Error = errorMessage.get(1);
		String invalidData_Error = errorMessage.get(2);

		List<String> invalidData = Arrays
				.asList(rstSuperListData.get(CNSuperList.DISBURSEMENT_PAGE_RECORD).split(Constants.DELIMITER_TILD));

		try {

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			CustomisedAssert.assertEquals(foundation.getText(Campus.LBL_CAMPUSLIST_HEADING), campusList_Page);
			foundation.click(Campus.BTN_CREATE_NEW);

			// validation for mandatory fields
			CustomisedAssert.assertTrue(foundation.isDisplayed(Campus.LBL_CAMPUSSAVE_HEADING), campusSave_Page);
			dropDown.selectItem(Campus.DRP_PAYROLL, invalidData.get(0), Constants.TEXT);
			foundation.click(Campus.BTN_SAVE);
			CustomisedAssert.assertEquals(foundation.getText(Campus.TXT_PAYROLL_EMAIL_ERROR), mandatory_Error);
			CustomisedAssert.assertEquals(foundation.getText(Campus.TXT_NAME_ERROR), mandatory_Error);
			CustomisedAssert.assertEquals(foundation.getText(Campus.TXT_GROUPNAME_ERROR), groupName_Error);

			// validation for Invalid data
			campus.enterInvalidData(invalidData.get(1), invalidData_Error);
			foundation.click(Campus.BTN_CANCEL);

			// Create New Campus
			foundation.click(Campus.BTN_CREATE_NEW);
			CustomisedAssert.assertTrue(foundation.isDisplayed(Campus.LBL_CAMPUSSAVE_HEADING), campusSave_Page);
			textBox.enterText(Campus.TXTBX_NAME, campusName);
			foundation.click(Campus.BTN_SAVE);

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "169168-QAA-287 ADM > Super > Campus > Update a campus from campus list page and save the changes")
	public void CampusUpdateFields() {
		final String CASE_NUM = "169168";
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstSuperListData = dataBase.getSuperListData(Queries.SUPER, CASE_NUM);

		List<String> validateHeading = Arrays
				.asList(rstSuperListData.get(CNSuperList.SUPER_NAME).split(Constants.DELIMITER_TILD));
		String campusList_Page = validateHeading.get(0);
		String campusShow_Page = validateHeading.get(1);

		String limitError = rstSuperListData.get(CNSuperList.ERROR_MESSAGE);
		List<String> updatedData = Arrays
				.asList(rstSuperListData.get(CNSuperList.UPDATED_DATA).split(Constants.DELIMITER_TILD));

		List<String> data = Arrays
				.asList(rstSuperListData.get(CNSuperList.DISBURSEMENT_PAGE_RECORD).split(Constants.DELIMITER_TILD));

		try {

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			CustomisedAssert.assertEquals(foundation.getText(Campus.LBL_CAMPUSLIST_HEADING), campusList_Page);

			// Update the details
			textBox.enterText(Campus.SEARCH_BOX, updatedData.get(0));
			foundation.click(Campus.SELECT_GRID);
			CustomisedAssert.assertEquals(foundation.getText(Campus.LBL_CAMPUSSHOW_HEADING), campusShow_Page);
			dropDown.selectItem(Campus.DRP_PAYCYCLE, data.get(0), Constants.TEXT);
			textBox.enterText(Campus.TXT_GROUP_NAME, data.get(1));
			textBox.enterText(Campus.TXT_SPEND_LIMIT, data.get(2));
			foundation.click(Campus.BTN_SAVE);
			CustomisedAssert.assertEquals(foundation.getText(Campus.LBL_LIMIT_ERROR), limitError);
			textBox.enterText(Campus.TXT_SPEND_LIMIT, data.get(3));
			foundation.click(Campus.BTN_ADD);
			textBox.enterText(Campus.TXTBX_NAME, updatedData.get(1));
			foundation.click(Campus.BTN_SAVE);

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}finally {
			// Reset the details
			textBox.enterText(Campus.SEARCH_BOX, updatedData.get(1));
			foundation.click(Campus.SELECT_GRID);
			textBox.enterText(Campus.TXTBX_NAME, updatedData.get(0));
			foundation.click(Campus.BTN_SAVE);
		}
	}

	@Test(description = "175783-QAA-295 ADM > Super > PageSet > QAA-295 ADM > Super > Pageset > Validate error message for Mandatory Fields on Pageset Create Page"
			+ "175786 - QAA-295 ADM > Super > Pageset > Validation of all Fields for invalid characters and data"
			+ "175784 - QAA-295 ADM > Super > Pageset > Create a new Page Set with selecting Page Def and intents and save the changes"
			+ "176159 - QAA-295 ADM > Super > Pageset > Create a new Page Set with selecting Page Def and intents and cancel the changes"
			+ "176161 - QAA-295 ADM > Super > Pageset > Validate error message for Existing Pageset Name")

	public void PageSetValidateAllFields() {
		final String CASE_NUM = "175783";
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstSuperListData = dataBase.getSuperListData(Queries.SUPER, CASE_NUM);

		List<String> validateHeading = Arrays
				.asList(rstSuperListData.get(CNSuperList.SUPER_NAME).split(Constants.DELIMITER_TILD));
		String pageSetList_Page = validateHeading.get(0);
		String pageSetCreate_Page = validateHeading.get(1);

		List<String> errorMessage = Arrays
				.asList(rstSuperListData.get(CNSuperList.ERROR_MESSAGE).split(Constants.DELIMITER_TILD));
		String name_Error = errorMessage.get(0);
		String existing_Name_Error = errorMessage.get(1);

		List<String> data = Arrays
				.asList(rstSuperListData.get(CNSuperList.DISBURSEMENT_PAGE_RECORD).split(Constants.DELIMITER_TILD));
		String existing_PageSet = data.get(0);
		String pageSet_Name = data.get(0) + string.getRandomCharacter();
		String service_Name = data.get(1);
		String pageSet_Def = data.get(2);
		String pageSet_Intent = data.get(3);

		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			CustomisedAssert.assertEquals(foundation.getText(PageSet.LBL_PAGESET_LIST), pageSetList_Page);
			foundation.click(PageSet.BTN_CREATE_NEW);

			// validation for mandatory fields
			CustomisedAssert.assertTrue(foundation.isDisplayed(PageSet.LBL_PAGESET_CREATE), pageSetCreate_Page);
			foundation.click(PageSet.BTN_SAVE);
			CustomisedAssert.assertEquals(foundation.getText(PageSet.LBL_PAGESET_ERROR), name_Error);

			// Validation for existing pageset
			textBox.enterText(PageSet.TXTBX_PAGESET, data.get(0));
			foundation.click(PageSet.BTN_SAVE);

			String existingPageset = foundation.getText(PageSet.LBL_PAGESET_ERROR);
			CustomisedAssert.assertTrue(existingPageset.contains(existing_Name_Error));

			// filling the details and click on Cancel button
			pageset.createPageSet(pageSet_Name, service_Name, pageSet_Def, pageSet_Intent, existing_PageSet);
			foundation.click(PageSet.BTN_CANCEL);

			// filling the details and click on Save button
			foundation.click(PageSet.BTN_CREATE_NEW);
			pageset.createPageSet(pageSet_Name, service_Name, pageSet_Def, pageSet_Intent, existing_PageSet);
			foundation.click(PageSet.BTN_SAVE);

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "175785 -QAA-295 ADM > Super > Pageset > Update a PageSet from PageSet list page and save the changes"
			+ "176160 - QAA-295 ADM > Super > Pageset > Update a PageSet from PageSet list page and cancel the changes")
	public void PagesetUpdateFields() {
		final String CASE_NUM = "175785";
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstSuperListData = dataBase.getSuperListData(Queries.SUPER, CASE_NUM);

		List<String> validateHeading = Arrays
				.asList(rstSuperListData.get(CNSuperList.SUPER_NAME).split(Constants.DELIMITER_TILD));
		String pageSetList_Page = validateHeading.get(0);
		String pageSet_Summary_Page = validateHeading.get(1);

		List<String> updatedData = Arrays
				.asList(rstSuperListData.get(CNSuperList.UPDATED_DATA).split(Constants.DELIMITER_TILD));

		try {

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			CustomisedAssert.assertEquals(foundation.getText(PageSet.LBL_PAGESET_LIST), pageSetList_Page);

			// Update the details
			textBox.enterText(PageSet.TXTBX_SEARCHBOX, updatedData.get(0));
			foundation.click(PageSet.SELECT_GRID);
			CustomisedAssert.assertEquals(foundation.getText(PageSet.LBL_PAGESET_SUMMARY), pageSet_Summary_Page);
			textBox.enterText(PageSet.TXTBX_PAGESET, updatedData.get(1));
			foundation.click(PageSet.BTN_SAVE);

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}finally {
			// Reset the details
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			textBox.enterText(PageSet.TXTBX_SEARCHBOX, updatedData.get(1));
			foundation.click(PageSet.SELECT_GRID);
			textBox.enterText(PageSet.TXTBX_PAGESET, updatedData.get(0));
			foundation.click(PageSet.BTN_SAVE);
		}	
	}
	
	@Test(description = "176221 - QAA-289 ADM > Super > App Referral > Validate error message for Mandatory Fields on App Referral Create Page"
			+ "176222 - QAA-289 ADM > Super > App Referral > Validate error message for Referral Amount"
			+ "176223 - QAA-289 ADM > Super > App Referral > Create a new App Referral with all Fields and cancel the changes"
			+ "176226 - QAA-289 ADM > Super > App Referral > Create a new App Referral with all Fields and Save the changes"
			+ "176227 - QAA-289 ADM > Super > App Referral >Validate all fields as Disabled when select previously created App Referral and cancel the change")

	public void AppReferralValidation() {
		final String CASE_NUM = "176221";
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstSuperListData = dataBase.getSuperListData(Queries.SUPER, CASE_NUM);

		List<String> validateHeading = Arrays
				.asList(rstSuperListData.get(CNSuperList.SUPER_NAME).split(Constants.DELIMITER_TILD));

		List<String> errorMessage = Arrays
				.asList(rstSuperListData.get(CNSuperList.ERROR_MESSAGE).split(Constants.DELIMITER_TILD));

		List<String> textbox_data = Arrays
				.asList(rstSuperListData.get(CNSuperList.DISBURSEMENT_PAGE_RECORD).split(Constants.DELIMITER_TILD));
		String newReferral = rstSuperListData.get(CNSuperList.UPDATED_DATA);

		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			CustomisedAssert.assertEquals(foundation.getText(AppReferral.LBL_APP_REFERRAL), validateHeading.get(0));
			foundation.click(AppReferral.BTN_CREATE_NEW);

			// validation for mandatory fields
			CustomisedAssert.assertTrue(foundation.isDisplayed(AppReferral.LBL_CREATE_APP_REFERRAL), validateHeading.get(1));
			foundation.click(AppReferral.BTN_SAVE);
			CustomisedAssert.assertEquals(foundation.getText(AppReferral.LBL_LOCATION_ERROR), errorMessage.get(0));
			CustomisedAssert.assertEquals(foundation.getText(AppReferral.LBL_REFERRAL_AMOUNT_ERROR), errorMessage.get(1));

			// Validation error Message for Referral Amount
			textBox.enterText(AppReferral.TXTBX_REFERRAL_AMOUNT, textbox_data.get(1));
			foundation.click(AppReferral.BTN_SAVE);
			CustomisedAssert.assertEquals(foundation.getText(AppReferral.LBL_REFERRAL_AMOUNT_ERROR), errorMessage.get(2));

			// filling the details and click on Cancel button
			appReferral.createAppReferral(textbox_data);
			foundation.click(AppReferral.BTN_CANCEL);

			// filling the details and click on Save button
			foundation.click(AppReferral.BTN_CREATE_NEW);
			appReferral.createAppReferral(textbox_data);
			foundation.click(AppReferral.BTN_SAVE);
			foundation.threadWait(Constants.TWO_SECOND);
			foundation.click(AppReferral.BTN_ACCEPT_POPUP);
			
			//Validating disable data on  App Referral Page
			foundation.threadWait(Constants.TWO_SECOND);
			textBox.enterText(AppReferral.TXTBX_SEARCH, textbox_data.get(0));
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(AppReferral.SELECT_GRID);
			CustomisedAssert.assertTrue(foundation.isDisabled(AppReferral.DRP_ORG));
			CustomisedAssert.assertTrue(foundation.isDisabled(AppReferral.DRP_LOCATION));
			CustomisedAssert.assertTrue(foundation.isDisabled(AppReferral.TXTBX_REFERRAL_AMOUNT));
			CustomisedAssert.assertTrue(foundation.isDisabled(AppReferral.DRP_DISABLE));
			foundation.click(AppReferral.BTN_CANCEL);
			
			//validating existing org and location error
			textBox.enterText(AppReferral.TXTBX_SEARCH, textbox_data.get(0));
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(AppReferral.SELECT_GRID);
			foundation.click(AppReferral.BTN_COPY_REFERRAL);
			dropDown.selectItem(AppReferral.DRP_LOCATION, textbox_data.get(0),Constants.TEXT);
			foundation.click(AppReferral.BTN_SAVE);
			foundation.waitforElement(AppReferral.LBL_REFERRAL_ERROR, Constants.SHORT_TIME);
			CustomisedAssert.assertEquals(foundation.getText(AppReferral.LBL_REFERRAL_ERROR), errorMessage.get(3));
			CustomisedAssert.assertEquals(foundation.getText(AppReferral.LBL_EXISTING_LOC), errorMessage.get(4));
			foundation.click(AppReferral.BTN_ERROR_CANCEL);
			
			//copy Referral button and create a new Referral			
			dropDown.selectItem(AppReferral.DRP_LOCATION, newReferral,Constants.TEXT);
			foundation.click(AppReferral.BTN_SAVE);
			foundation.threadWait(Constants.TWO_SECOND);
			foundation.click(AppReferral.BTN_ACCEPT_POPUP);
			
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}	
		finally {
			//Resetting the data
			textBox.enterText(AppReferral.TXTBX_SEARCH, textbox_data.get(0));
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(AppReferral.SELECT_GRID);
			foundation.click(AppReferral.BTN_END_REFERRAL);
			foundation.click(AppReferral.BTN_EXPIRE_REFERRAL);
			foundation.threadWait(Constants.TWO_SECOND);
			textBox.enterText(AppReferral.TXTBX_SEARCH, newReferral);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(AppReferral.SELECT_GRID);
			foundation.click(AppReferral.BTN_END_REFERRAL);
			foundation.click(AppReferral.BTN_EXPIRE_REFERRAL);
		}
		
	}

	@Test(description = "176228 - QAA-289 ADM > Super > App Referral >Create new App Referral by Copy Referral button on App Referral Page")
			
	public void CopyButtonAppReferral() {
		final String CASE_NUM = "176228";
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstSuperListData = dataBase.getSuperListData(Queries.SUPER, CASE_NUM);

		String org = rstSuperListData.get(CNSuperList.DISBURSEMENT_PAGE_RECORD);
		
		String newReferral_Org = rstSuperListData.get(CNSuperList.UPDATED_DATA);

		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));		
			
			//copy Referral button and create a new Referral
			textBox.enterText(AppReferral.TXTBX_SEARCH, org);
			foundation.threadWait(Constants.TWO_SECOND);
			foundation.click(AppReferral.SELECT_GRID);
			foundation.click(AppReferral.BTN_COPY_REFERRAL);
			dropDown.selectItem(AppReferral.DRP_LOCATION, newReferral_Org,Constants.TEXT);
			foundation.click(AppReferral.BTN_SAVE);
			foundation.threadWait(Constants.TWO_SECOND);
			foundation.click(AppReferral.BTN_ACCEPT_POPUP);
			
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}		
		//Resetting the data
		textBox.enterText(AppReferral.TXTBX_SEARCH, newReferral_Org);
		foundation.threadWait(Constants.SHORT_TIME);
		foundation.click(AppReferral.SELECT_GRID);
		foundation.click(AppReferral.BTN_END_REFERRAL);
		foundation.click(AppReferral.BTN_EXPIRE_REFERRAL);
	}