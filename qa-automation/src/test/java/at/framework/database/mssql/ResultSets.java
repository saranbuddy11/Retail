package at.framework.database.mssql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;

import at.smartshop.database.columns.CNAdminAgeVerification;
import at.smartshop.database.columns.CNAdminDNA;
import at.smartshop.database.columns.CNConsumer;
import at.smartshop.database.columns.CNConsumerSearch;
import at.smartshop.database.columns.CNConsumerSummary;
import at.smartshop.database.columns.CNDeviceList;
import at.smartshop.database.columns.CNGlobalProductChange;
import at.smartshop.database.columns.CNGmaUser;
import at.smartshop.database.columns.CNLoadProduct;
import at.smartshop.database.columns.CNLocation;
import at.smartshop.database.columns.CNLocationList;
import at.smartshop.database.columns.CNLocationSummary;
import at.smartshop.database.columns.CNLockerSystem;
import at.smartshop.database.columns.CNLoginPage;
import at.smartshop.database.columns.CNNationalAccounts;
import at.smartshop.database.columns.CNNavigationMenu;
import at.smartshop.database.columns.CNOrgSummary;
import at.smartshop.database.columns.CNPickList;
import at.smartshop.database.columns.CNProduct;
import at.smartshop.database.columns.CNProductSummary;
import at.smartshop.database.columns.CNReportList;
import at.smartshop.database.columns.CNRoundUpCharity;
import at.smartshop.database.columns.CNSSODomain;
import at.smartshop.database.columns.CNStaffSummary;
import at.smartshop.database.columns.CNSuperList;
import at.smartshop.database.columns.CNUserRoles;
import at.smartshop.database.columns.CNV5Device;
import at.smartshop.keys.Constants;

public class ResultSets extends Connections {

	public Map<String, String> getNavigationMenuData(String query, String testcaseID) {
		Map<String, String> rstNavigationMenu = new HashMap<>();

		Statement statement = null;
		String sqlQuery = Constants.EMPTY_STRING;
		try {

			if (connection == null)
				getConnection();
			statement = connection.createStatement();
			sqlQuery = query + testcaseID;
			ResultSet resultSet = statement.executeQuery(sqlQuery);
			while (resultSet.next()) {
				rstNavigationMenu.put(CNNavigationMenu.MENU_ITEM, resultSet.getString(CNNavigationMenu.MENU_ITEM));
				rstNavigationMenu.put(CNNavigationMenu.REQUIRED_OPTION,
						resultSet.getString(CNNavigationMenu.REQUIRED_OPTION));
			}

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		} finally {
			try {
				statement.close();
			} catch (Exception exc) {
				Assert.fail(exc.toString());
			}
		}
		return rstNavigationMenu;

	}

	public Map<String, String> getConsumerSearchData(String query, String testcaseID) {
		Map<String, String> rstConsumerSearch = new HashMap<>();

		Statement statement = null;
		String sqlQuery = Constants.EMPTY_STRING;
		try {

			if (connection == null)
				getConnection();
			statement = connection.createStatement();
			sqlQuery = query + testcaseID;

			ResultSet resultSet = statement.executeQuery(sqlQuery);
			while (resultSet.next()) {
				rstConsumerSearch.put(CNConsumerSearch.CONSUMER_ID, resultSet.getString(CNConsumerSearch.CONSUMER_ID));
				rstConsumerSearch.put(CNConsumerSearch.SEARCH_BY, resultSet.getString(CNConsumerSearch.SEARCH_BY));
				rstConsumerSearch.put(CNConsumerSearch.STATUS, resultSet.getString(CNConsumerSearch.STATUS));
				rstConsumerSearch.put(CNConsumerSearch.LOCATION, resultSet.getString(CNConsumerSearch.LOCATION));
				rstConsumerSearch.put(CNConsumerSearch.COLUMN_NAME, resultSet.getString(CNConsumerSearch.COLUMN_NAME));
				rstConsumerSearch.put(CNConsumerSearch.TITLE, resultSet.getString(CNConsumerSearch.TITLE));
				rstConsumerSearch.put(CNConsumerSearch.ACTIONS, resultSet.getString(CNConsumerSearch.ACTIONS));
				rstConsumerSearch.put(CNConsumerSearch.SHEET_NAME, resultSet.getString(CNConsumerSearch.SHEET_NAME));
				rstConsumerSearch.put(CNConsumerSearch.ATTRIBUTE, resultSet.getString(CNConsumerSearch.ATTRIBUTE));
				rstConsumerSearch.put(CNConsumerSearch.FIRST_NAME, resultSet.getString(CNConsumerSearch.FIRST_NAME));
				rstConsumerSearch.put(CNConsumerSearch.SEARCH, resultSet.getString(CNConsumerSearch.SEARCH));
			}
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		} finally {
			try {
				statement.close();

			} catch (Exception exc) {
				Assert.fail(exc.toString());
			}
		}
		return rstConsumerSearch;
	}

	public Map<String, String> getProductSummaryData(String query, String testcaseID) {
		Map<String, String> rstProductSummary = new HashMap<>();
		Statement statement = null;
		String sqlQuery = Constants.EMPTY_STRING;
		try {
			if (connection == null)
				getConnection();
			statement = connection.createStatement();
			sqlQuery = query + testcaseID;

			ResultSet resultSet = statement.executeQuery(sqlQuery);
			while (resultSet.next()) {
				rstProductSummary.put(CNProductSummary.ACTUAL_DATA, resultSet.getString(CNProductSummary.ACTUAL_DATA));
				rstProductSummary.put(CNProductSummary.REQUIRED_DATA,
						resultSet.getString(CNProductSummary.REQUIRED_DATA));
				rstProductSummary.put(CNProductSummary.COLUMN_NAME, resultSet.getString(CNProductSummary.COLUMN_NAME));
				rstProductSummary.put(CNProductSummary.PRICE, resultSet.getString(CNProductSummary.PRICE));
				rstProductSummary.put(CNProductSummary.GPCMIN, resultSet.getString(CNProductSummary.GPCMIN));
				rstProductSummary.put(CNProductSummary.GPCMAX, resultSet.getString(CNProductSummary.GPCMAX));
				rstProductSummary.put(CNProductSummary.PICK_LIST_ACTION,
						resultSet.getString(CNProductSummary.PICK_LIST_ACTION));
				rstProductSummary.put(CNProductSummary.CATEGORY1, resultSet.getString(CNProductSummary.CATEGORY1));
				rstProductSummary.put(CNProductSummary.CATEGORY2, resultSet.getString(CNProductSummary.CATEGORY2));
				rstProductSummary.put(CNProductSummary.CATEGORY3, resultSet.getString(CNProductSummary.CATEGORY3));
				rstProductSummary.put(CNProductSummary.COST, resultSet.getString(CNProductSummary.COST));
				rstProductSummary.put(CNProductSummary.CASE_COUNT, resultSet.getString(CNProductSummary.CASE_COUNT));
				rstProductSummary.put(CNProductSummary.TAX_CATEGORY,
						resultSet.getString(CNProductSummary.TAX_CATEGORY));
				rstProductSummary.put(CNProductSummary.DEPOSIT_CATEGORY,
						resultSet.getString(CNProductSummary.DEPOSIT_CATEGORY));
				rstProductSummary.put(CNProductSummary.PRODUCT_NAME_ERROR,
						resultSet.getString(CNProductSummary.PRODUCT_NAME_ERROR));
				rstProductSummary.put(CNProductSummary.SHORT_PRODUCT_NAME_ERROR,
						resultSet.getString(CNProductSummary.SHORT_PRODUCT_NAME_ERROR));
				rstProductSummary.put(CNProductSummary.DESCRIPTION_ERROR,
						resultSet.getString(CNProductSummary.DESCRIPTION_ERROR));
				rstProductSummary.put(CNProductSummary.SHORT_NAME, resultSet.getString(CNProductSummary.SHORT_NAME));
				rstProductSummary.put(CNProductSummary.DESCRIPTION, resultSet.getString(CNProductSummary.DESCRIPTION));
				rstProductSummary.put(CNProductSummary.ITERATION_COUNT,
						resultSet.getString(CNProductSummary.ITERATION_COUNT));
				rstProductSummary.put(CNProductSummary.MODIFIER_NAME,
						resultSet.getString(CNProductSummary.MODIFIER_NAME));
				rstProductSummary.put(CNProductSummary.PRODUCT_TYPE,
						resultSet.getString(CNProductSummary.PRODUCT_TYPE));
				rstProductSummary.put(CNProductSummary.KITCHEN_PREP,
						resultSet.getString(CNProductSummary.KITCHEN_PREP));
				rstProductSummary.put(CNProductSummary.SCAN_CODE, resultSet.getString(CNProductSummary.SCAN_CODE));
				rstProductSummary.put(CNProductSummary.SCAN_CODE_ERROR,
						resultSet.getString(CNProductSummary.SCAN_CODE_ERROR));
				rstProductSummary.put(CNProductSummary.SUCCESSSCANCODE,
						resultSet.getString(CNProductSummary.SUCCESSSCANCODE));
				rstProductSummary.put(CNProductSummary.USER_KEY, resultSet.getString(CNProductSummary.USER_KEY));
				rstProductSummary.put(CNProductSummary.UNIT_OF_MEASURE,
						resultSet.getString(CNProductSummary.UNIT_OF_MEASURE));
				rstProductSummary.put(CNProductSummary.WEIGH, resultSet.getString(CNProductSummary.WEIGH));
				rstProductSummary.put(CNProductSummary.TARE_WEIGHT, resultSet.getString(CNProductSummary.TARE_WEIGHT));
				rstProductSummary.put(CNProductSummary.DISCOUNT, resultSet.getString(CNProductSummary.DISCOUNT));
				rstProductSummary.put(CNProductSummary.DISPLAY_NEED_BY,
						resultSet.getString(CNProductSummary.DISPLAY_NEED_BY));
				rstProductSummary.put(CNProductSummary.ROUNDING, resultSet.getString(CNProductSummary.ROUNDING));
				rstProductSummary.put(CNProductSummary.MODIFIER_TAB_NAME,
						resultSet.getString(CNProductSummary.MODIFIER_TAB_NAME));
				rstProductSummary.put(CNProductSummary.MODIFIER_TAB_DISPLAY_NAME,
						resultSet.getString(CNProductSummary.MODIFIER_TAB_DISPLAY_NAME));
				rstProductSummary.put(CNProductSummary.MODIFIER_INFO_MESSAGE,
						resultSet.getString(CNProductSummary.MODIFIER_INFO_MESSAGE));
				rstProductSummary.put(CNProductSummary.MODIFIER_TYPE,
						resultSet.getString(CNProductSummary.MODIFIER_TYPE));
				rstProductSummary.put(CNProductSummary.MODIFIER_DESCRIPTION,
						resultSet.getString(CNProductSummary.MODIFIER_DESCRIPTION));
				rstProductSummary.put(CNProductSummary.MODIFIER_DEFAULT_PRODUCT,
						resultSet.getString(CNProductSummary.MODIFIER_DEFAULT_PRODUCT));
				rstProductSummary.put(CNProductSummary.MODIFIER_DEFAULT_PRICE,
						resultSet.getString(CNProductSummary.MODIFIER_DEFAULT_PRICE));
				rstProductSummary.put(CNProductSummary.MAX_SELECTIONS,
						resultSet.getString(CNProductSummary.MAX_SELECTIONS));
				rstProductSummary.put(CNProductSummary.TOOL_TIP_MESSAGE,
						resultSet.getString(CNProductSummary.TOOL_TIP_MESSAGE));
				rstProductSummary.put(CNProductSummary.RECORDS_PER_PAGE_LIST,
						resultSet.getString(CNProductSummary.RECORDS_PER_PAGE_LIST));
				rstProductSummary.put(CNProductSummary.PRODUCT_NAME,
						resultSet.getString(CNProductSummary.PRODUCT_NAME));
				rstProductSummary.put(CNProductSummary.DEVICE_ID, resultSet.getString(CNProductSummary.DEVICE_ID));
				rstProductSummary.put(CNProductSummary.TAX, resultSet.getString(CNProductSummary.TAX));
				rstProductSummary.put(CNProductSummary.LOCATION_NAME,
						resultSet.getString(CNProductSummary.LOCATION_NAME));
			}

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		} finally {
			try {
				statement.close();
			} catch (SQLException exc) {
				Assert.fail(exc.toString());
			}
		}
		return rstProductSummary;

	}

	public Map<String, String> getLocationSummaryData(String query, String testcaseID) {
		Map<String, String> rstLocationSummary = new HashMap<>();
		Statement statement = null;
		String sqlQuery = Constants.EMPTY_STRING;
		try {
			if (connection == null)
				getConnection();
			statement = connection.createStatement();
			sqlQuery = query + testcaseID;

			ResultSet resultSet = statement.executeQuery(sqlQuery);
			while (resultSet.next()) {
				rstLocationSummary.put(CNLocationSummary.ACTUAL_DATA,
						resultSet.getString(CNLocationSummary.ACTUAL_DATA));
				rstLocationSummary.put(CNLocationSummary.REQUIRED_DATA,
						resultSet.getString(CNLocationSummary.REQUIRED_DATA));
				rstLocationSummary.put(CNLocationSummary.COLUMN_NAME,
						resultSet.getString(CNLocationSummary.COLUMN_NAME));
				rstLocationSummary.put(CNLocationSummary.TAB_NAME, resultSet.getString(CNLocationSummary.TAB_NAME));
				rstLocationSummary.put(CNLocationSummary.NAME, resultSet.getString(CNLocationSummary.NAME));
				rstLocationSummary.put(CNLocationSummary.STATE_PROVINCE,
						resultSet.getString(CNLocationSummary.STATE_PROVINCE));
				rstLocationSummary.put(CNLocationSummary.COUNTRY, resultSet.getString(CNLocationSummary.COUNTRY));
				rstLocationSummary.put(CNLocationSummary.TIME_ZONE, resultSet.getString(CNLocationSummary.TIME_ZONE));
				rstLocationSummary.put(CNLocationSummary.CONTACT_EMAIL,
						resultSet.getString(CNLocationSummary.CONTACT_EMAIL));
				rstLocationSummary.put(CNLocationSummary.EMAIL_ERROR,
						resultSet.getString(CNLocationSummary.EMAIL_ERROR));
				rstLocationSummary.put(CNLocationSummary.DOMAIN, resultSet.getString(CNLocationSummary.DOMAIN));
				rstLocationSummary.put(CNLocationSummary.ROUTE, resultSet.getString(CNLocationSummary.ROUTE));
				rstLocationSummary.put(CNLocationSummary.INITIAL_BALANCE,
						resultSet.getString(CNLocationSummary.INITIAL_BALANCE));
				rstLocationSummary.put(CNLocationSummary.INITIAL_BALANCE_ERROR,
						resultSet.getString(CNLocationSummary.INITIAL_BALANCE_ERROR));
				rstLocationSummary.put(CNLocationSummary.POPULATION_ERROR,
						resultSet.getString(CNLocationSummary.POPULATION_ERROR));
				rstLocationSummary.put(CNLocationSummary.SICLOOKUP, resultSet.getString(CNLocationSummary.SICLOOKUP));
				rstLocationSummary.put(CNLocationSummary.HOME_COMMERCIAL_PRODUCT,
						resultSet.getString(CNLocationSummary.HOME_COMMERCIAL_PRODUCT));
				rstLocationSummary.put(CNLocationSummary.COMMERCIAL_POPUP_NAME,
						resultSet.getString(CNLocationSummary.COMMERCIAL_POPUP_NAME));
				rstLocationSummary.put(CNLocationSummary.LOCATION_DISABLED,
						resultSet.getString(CNLocationSummary.LOCATION_DISABLED));
				rstLocationSummary.put(CNLocationSummary.PRODUCT_NAME,
						resultSet.getString(CNLocationSummary.PRODUCT_NAME));
				rstLocationSummary.put(CNLocationSummary.DEVICE_NAME,
						resultSet.getString(CNLocationSummary.DEVICE_NAME));
				rstLocationSummary.put(CNLocationSummary.POPUP_TABNAME,
						resultSet.getString(CNLocationSummary.POPUP_TABNAME));
				rstLocationSummary.put(CNLocationSummary.FILEPATH, resultSet.getString(CNLocationSummary.FILEPATH));
				rstLocationSummary.put(CNLocationSummary.PROMOTION_NAME,
						resultSet.getString(CNLocationSummary.PROMOTION_NAME));
				rstLocationSummary.put(CNLocationSummary.COLUMN_VALUE,
						resultSet.getString(CNLocationSummary.COLUMN_VALUE));
				rstLocationSummary.put(CNLocationSummary.FILTER_RESULT,
						resultSet.getString(CNLocationSummary.FILTER_RESULT));
				rstLocationSummary.put(CNLocationSummary.PRODUCT_MESSAGE,
						resultSet.getString(CNLocationSummary.PRODUCT_MESSAGE));
				rstLocationSummary.put(CNLocationSummary.DEVICE_STATUS,
						resultSet.getString(CNLocationSummary.DEVICE_STATUS));
				rstLocationSummary.put(CNLocationSummary.GMA_LOYALTY_POINTS,
						resultSet.getString(CNLocationSummary.GMA_LOYALTY_POINTS));
				rstLocationSummary.put(CNLocationSummary.LOYALTY_MULTIPLIER_DEFAULT,
						resultSet.getString(CNLocationSummary.LOYALTY_MULTIPLIER_DEFAULT));
				rstLocationSummary.put(CNLocationSummary.CONFIRMATION_MESSAGE,
						resultSet.getString(CNLocationSummary.CONFIRMATION_MESSAGE));
				rstLocationSummary.put(CNLocationSummary.POPUP_NAME, resultSet.getString(CNLocationSummary.POPUP_NAME));
				rstLocationSummary.put(CNLocationSummary.PRODUCT_POPUP_DETAILS,
						resultSet.getString(CNLocationSummary.PRODUCT_POPUP_DETAILS));
				rstLocationSummary.put(CNLocationSummary.SPEND_LIMIT,
						resultSet.getString(CNLocationSummary.SPEND_LIMIT));
				rstLocationSummary.put(CNLocationSummary.ENABLE_RETRIEVE_ACCOUNT,
						resultSet.getString(CNLocationSummary.ENABLE_RETRIEVE_ACCOUNT));
				rstLocationSummary.put(CNLocationSummary.HAS_LOCKERS,
						resultSet.getString(CNLocationSummary.HAS_LOCKERS));
				rstLocationSummary.put(CNLocationSummary.PAYROLL_DEDUCT,
						resultSet.getString(CNLocationSummary.PAYROLL_DEDUCT));
				rstLocationSummary.put(CNLocationSummary.START_DATE, resultSet.getString(CNLocationSummary.START_DATE));
				rstLocationSummary.put(CNLocationSummary.ADDRESS, resultSet.getString(CNLocationSummary.ADDRESS));

			}
		} catch (

		Exception exc) {
			Assert.fail(exc.toString());
		} finally {
			try {
				statement.close();
			} catch (SQLException exc) {
				Assert.fail(exc.toString());
			}
		}
		return rstLocationSummary;

	}

	public Map<String, String> getConsumerSummaryData(String query, String testcaseID) {
		Map<String, String> rstConsumerSummary = new HashMap<>();
		Statement statement = null;
		String sqlQuery = Constants.EMPTY_STRING;
		try {
			if (connection == null)
				getConnection();
			statement = connection.createStatement();
			sqlQuery = query + testcaseID;

			ResultSet resultSet = statement.executeQuery(sqlQuery);
			while (resultSet.next()) {
				rstConsumerSummary.put(CNConsumerSummary.REASON, resultSet.getString(CNConsumerSummary.REASON));
				rstConsumerSummary.put(CNConsumerSummary.ADJUST_BALANCE,
						resultSet.getString(CNConsumerSummary.ADJUST_BALANCE));
				rstConsumerSummary.put(CNConsumerSummary.FIRST_NAME, resultSet.getString(CNConsumerSummary.FIRST_NAME));
				rstConsumerSummary.put(CNConsumerSummary.LAST_NAME, resultSet.getString(CNConsumerSummary.LAST_NAME));
				rstConsumerSummary.put(CNConsumerSummary.PIN_ERROR, resultSet.getString(CNConsumerSummary.PIN_ERROR));
				rstConsumerSummary.put(CNConsumerSummary.PIN, resultSet.getString(CNConsumerSummary.PIN));
				rstConsumerSummary.put(CNConsumerSummary.MARKET_CARD_DISABLED,
						resultSet.getString(CNConsumerSummary.MARKET_CARD_DISABLED));
				rstConsumerSummary.put(CNConsumerSummary.PHONE, resultSet.getString(CNConsumerSummary.PHONE));
				rstConsumerSummary.put(CNConsumerSummary.EMAIL, resultSet.getString(CNConsumerSummary.EMAIL));
				rstConsumerSummary.put(CNConsumerSummary.AMOUNT, resultSet.getString(CNConsumerSummary.AMOUNT));
				rstConsumerSummary.put(CNConsumerSummary.PAY_CYCLE, resultSet.getString(CNConsumerSummary.PAY_CYCLE));
			}
		} catch (

		Exception exc) {
			Assert.fail(exc.toString());
		} finally {
			try {
				statement.close();
			} catch (SQLException exc) {
				Assert.fail(exc.toString());
			}
		}
		return rstConsumerSummary;

	}

	public Map<String, String> getReportListData(String query, String testcaseID) {
		Map<String, String> rstReportList = new HashMap<>();
		Statement statement = null;
		String sqlQuery = Constants.EMPTY_STRING;
		try {
			if (connection == null)
				getConnection();
			statement = connection.createStatement();
			sqlQuery = query + testcaseID;

			ResultSet resultSet = statement.executeQuery(sqlQuery);
			while (resultSet.next()) {
				rstReportList.put(CNReportList.REPORT_NAME, resultSet.getString(CNReportList.REPORT_NAME));
				rstReportList.put(CNReportList.DATE_RANGE, resultSet.getString(CNReportList.DATE_RANGE));
				rstReportList.put(CNReportList.FROM_TIME, resultSet.getString(CNReportList.FROM_TIME));
				rstReportList.put(CNReportList.TO_TIME, resultSet.getString(CNReportList.TO_TIME));
				rstReportList.put(CNReportList.START_DATE, resultSet.getString(CNReportList.START_DATE));
				rstReportList.put(CNReportList.END_DATE, resultSet.getString(CNReportList.END_DATE));
				rstReportList.put(CNReportList.START_MONTH, resultSet.getString(CNReportList.START_MONTH));
				rstReportList.put(CNReportList.END_MONTH, resultSet.getString(CNReportList.END_MONTH));
				rstReportList.put(CNReportList.DOWNLOADED_FILE_NAME,
						resultSet.getString(CNReportList.DOWNLOADED_FILE_NAME));
				rstReportList.put(CNReportList.GROUPBY_DROPDOWN, resultSet.getString(CNReportList.GROUPBY_DROPDOWN));
				rstReportList.put(CNReportList.SELECT_FILTER_TO_INCLUDE,
						resultSet.getString(CNReportList.SELECT_FILTER_TO_INCLUDE));
				rstReportList.put(CNReportList.SELECT_VALUE_FOR_SELECTED_FILTER_TYPE,
						resultSet.getString(CNReportList.SELECT_VALUE_FOR_SELECTED_FILTER_TYPE));
			}

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		} finally {
			try {
				statement.close();
			} catch (SQLException exc) {
				Assert.fail(exc.toString());
			}
		}
		return rstReportList;
	}

	public Map<String, String> getDeviceListData(String query, String testcaseID) {
		Map<String, String> rstDeviceList = new HashMap<>();
		Statement statement = null;
		String sqlQuery = Constants.EMPTY_STRING;
		try {
			if (connection == null)
				getConnection();
			statement = connection.createStatement();
			sqlQuery = query + testcaseID;

			ResultSet resultSet = statement.executeQuery(sqlQuery);
			while (resultSet.next()) {
				rstDeviceList.put(CNDeviceList.CC_PROCESSOR, resultSet.getString(CNDeviceList.CC_PROCESSOR));
				rstDeviceList.put(CNDeviceList.PRODUCT_NAME, resultSet.getString(CNDeviceList.PRODUCT_NAME));
				rstDeviceList.put(CNDeviceList.LOCATION, resultSet.getString(CNDeviceList.LOCATION));
				rstDeviceList.put(CNDeviceList.DEVICE, resultSet.getString(CNDeviceList.DEVICE));
				rstDeviceList.put(CNDeviceList.SERIAL_NUMBER, resultSet.getString(CNDeviceList.SERIAL_NUMBER));
				rstDeviceList.put(CNDeviceList.ERROR_MESSAGE, resultSet.getString(CNDeviceList.ERROR_MESSAGE));
			}
		} catch (

		Exception exc) {
			Assert.fail(exc.toString());
		} finally {
			try {
				statement.close();
			} catch (SQLException exc) {
				Assert.fail(exc.toString());
			}
		}
		return rstDeviceList;
	}

	public Map<String, String> getLocationListData(String query, String testcaseID) {
		Map<String, String> rstLocationList = new HashMap<>();
		Statement statement = null;
		String sqlQuery = Constants.EMPTY_STRING;
		try {
			if (connection == null)
				getConnection();
			statement = connection.createStatement();
			sqlQuery = query + testcaseID;

			ResultSet resultSet = statement.executeQuery(sqlQuery);
			while (resultSet.next()) {
				rstLocationList.put(CNLocationList.DROPDOWN_LOCATION_LIST,
						resultSet.getString(CNLocationList.DROPDOWN_LOCATION_LIST));
				rstLocationList.put(CNLocationList.LOCATION_NAME, resultSet.getString(CNLocationList.LOCATION_NAME));
				rstLocationList.put(CNLocationList.COLUMN_NAME, resultSet.getString(CNLocationList.COLUMN_NAME));
				rstLocationList.put(CNLocationList.INFO_MESSAGE, resultSet.getString(CNLocationList.INFO_MESSAGE));
				rstLocationList.put(CNLocationList.SHOW_RECORDS, resultSet.getString(CNLocationList.SHOW_RECORDS));
				rstLocationList.put(CNLocationList.PAY_CYCLE, resultSet.getString(CNLocationList.PAY_CYCLE));
				rstLocationList.put(CNLocationList.PRODUCT_NAME, resultSet.getString(CNLocationList.PRODUCT_NAME));
			}
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		} finally {
			try {
				statement.close();
			} catch (SQLException exc) {
				Assert.fail(exc.toString());
			}
		}
		return rstLocationList;
	}

	public Map<String, String> getGmaUserData(String query, String testcaseID) {
		Map<String, String> rstGmaUser = new HashMap<>();
		Statement statement = null;
		String sqlQuery = Constants.EMPTY_STRING;
		try {
			if (connection == null)
				getConnection();
			statement = connection.createStatement();
			sqlQuery = query + testcaseID;

			ResultSet resultSet = statement.executeQuery(sqlQuery);
			while (resultSet.next()) {
				rstGmaUser.put(CNGmaUser.START_BALANCE, resultSet.getString(CNGmaUser.START_BALANCE));
				rstGmaUser.put(CNGmaUser.PIN_VALUE, resultSet.getString(CNGmaUser.PIN_VALUE));
				rstGmaUser.put(CNGmaUser.SHEET_NAME, resultSet.getString(CNGmaUser.SHEET_NAME));
				rstGmaUser.put(CNGmaUser.COLUMN_NAME, resultSet.getString(CNGmaUser.COLUMN_NAME));
				rstGmaUser.put(CNGmaUser.COLUMN_VALUE, resultSet.getString(CNGmaUser.COLUMN_VALUE));
			}
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		} finally {
			try {
				statement.close();
			} catch (SQLException exc) {
				Assert.fail(exc.toString());
			}
		}
		return rstGmaUser;
	}

	public Map<String, String> getLoadProductData(String query, String testcaseID) {
		Map<String, String> rstLoadProduct = new HashMap<>();
		Statement statement = null;
		String sqlQuery = Constants.EMPTY_STRING;
		try {
			if (connection == null)
				getConnection();
			statement = connection.createStatement();
			sqlQuery = query + testcaseID;

			ResultSet resultSet = statement.executeQuery(sqlQuery);
			while (resultSet.next()) {
				rstLoadProduct.put(CNLoadProduct.DELETE_EXISTING_PRODUCT,
						resultSet.getString(CNLoadProduct.DELETE_EXISTING_PRODUCT));
				rstLoadProduct.put(CNLoadProduct.LOAD_TYPE, resultSet.getString(CNLoadProduct.LOAD_TYPE));

			}
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		} finally {
			try {
				statement.close();
			} catch (SQLException exc) {
				Assert.fail(exc.toString());
			}
		}
		return rstLoadProduct;
	}

	public Map<String, String> getGlobalProductChangeData(String query, String testcaseID) {
		Map<String, String> rstGlobalProductChange = new HashMap<>();

		Statement statement = null;
		String sqlQuery = Constants.EMPTY_STRING;
		try {
			if (connection == null)
				getConnection();
			statement = connection.createStatement();
			sqlQuery = query + testcaseID;

			ResultSet resultSet = statement.executeQuery(sqlQuery);
			while (resultSet.next()) {
				rstGlobalProductChange.put(CNGlobalProductChange.TAB_NAME,
						resultSet.getString(CNGlobalProductChange.TAB_NAME));
				rstGlobalProductChange.put(CNGlobalProductChange.LOCATION_NAME,
						resultSet.getString(CNGlobalProductChange.LOCATION_NAME));
				rstGlobalProductChange.put(CNGlobalProductChange.PRODUCT_NAME,
						resultSet.getString(CNGlobalProductChange.PRODUCT_NAME));
				rstGlobalProductChange.put(CNGlobalProductChange.COLUMN_NAME,
						resultSet.getString(CNGlobalProductChange.COLUMN_NAME));
				rstGlobalProductChange.put(CNGlobalProductChange.SUCCESS_MESSAGE,
						resultSet.getString(CNGlobalProductChange.SUCCESS_MESSAGE));
				rstGlobalProductChange.put(CNGlobalProductChange.INFO_MESSAGE,
						resultSet.getString(CNGlobalProductChange.INFO_MESSAGE));
				rstGlobalProductChange.put(CNGlobalProductChange.TOOL_TIP_MESSAGE,
						resultSet.getString(CNGlobalProductChange.TOOL_TIP_MESSAGE));
				rstGlobalProductChange.put(CNGlobalProductChange.FILTER_DROPDOWN,
						resultSet.getString(CNGlobalProductChange.FILTER_DROPDOWN));
				rstGlobalProductChange.put(CNGlobalProductChange.PICKLIST_DROPDOWN,
						resultSet.getString(CNGlobalProductChange.PICKLIST_DROPDOWN));
				rstGlobalProductChange.put(CNGlobalProductChange.TITLE,
						resultSet.getString(CNGlobalProductChange.TITLE));
				rstGlobalProductChange.put(CNGlobalProductChange.INCREMENT_LABEL,
						resultSet.getString(CNGlobalProductChange.INCREMENT_LABEL));
				rstGlobalProductChange.put(CNGlobalProductChange.INCREMENT_PRICE,
						resultSet.getString(CNGlobalProductChange.INCREMENT_PRICE));

			}
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		} finally {
			try {
				statement.close();
			} catch (SQLException exc) {
				Assert.fail(exc.toString());
			}
		}
		return rstGlobalProductChange;
	}

	public Map<String, String> getUserRolesData(String query, String testcaseID) {
		Map<String, String> rstUserRoles = new HashMap<>();

		Statement statement = null;
		String sqlQuery = Constants.EMPTY_STRING;
		try {
			if (connection == null)
				getConnection();
			statement = connection.createStatement();
			sqlQuery = query + testcaseID;

			ResultSet resultSet = statement.executeQuery(sqlQuery);
			while (resultSet.next()) {
				rstUserRoles.put(CNUserRoles.ROLE_NAME, resultSet.getString(CNUserRoles.ROLE_NAME));
				rstUserRoles.put(CNUserRoles.ROW_RECORD, resultSet.getString(CNUserRoles.ROW_RECORD));
				rstUserRoles.put(CNUserRoles.CLIENT_DROPDOWN, resultSet.getString(CNUserRoles.CLIENT_DROPDOWN));
				rstUserRoles.put(CNUserRoles.ERROR_MESSAGE, resultSet.getString(CNUserRoles.ERROR_MESSAGE));
			}

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		} finally {
			try {
				statement.close();
			} catch (SQLException exc) {
				Assert.fail(exc.toString());
			}
		}
		return rstUserRoles;
	}

	public Map<String, String> getNationalAccountsData(String query, String testcaseID) {
		Map<String, String> rstNationalAccounts = new HashMap<>();
		Statement statement = null;
		String sqlQuery = Constants.EMPTY_STRING;
		try {

			if (connection == null)
				getConnection();
			statement = connection.createStatement();
			sqlQuery = query + testcaseID;
			ResultSet resultSet = statement.executeQuery(sqlQuery);
			while (resultSet.next()) {
				rstNationalAccounts.put(CNNationalAccounts.CLIENT_NAME,
						resultSet.getString(CNNationalAccounts.CLIENT_NAME));
				rstNationalAccounts.put(CNNationalAccounts.DEFAULT_DROPDOWN_LOCATION,
						resultSet.getString(CNNationalAccounts.DEFAULT_DROPDOWN_LOCATION));
				rstNationalAccounts.put(CNNationalAccounts.DEFAULT_DROPDOWN_ORG,
						resultSet.getString(CNNationalAccounts.DEFAULT_DROPDOWN_ORG));
				rstNationalAccounts.put(CNNationalAccounts.LOCATION_TAGGED,
						resultSet.getString(CNNationalAccounts.LOCATION_TAGGED));
				rstNationalAccounts.put(CNNationalAccounts.NATIONAL_ACCOUNT_NAME,
						resultSet.getString(CNNationalAccounts.NATIONAL_ACCOUNT_NAME));
				rstNationalAccounts.put(CNNationalAccounts.ORG_ASSIGNED,
						resultSet.getString(CNNationalAccounts.ORG_ASSIGNED));
				rstNationalAccounts.put(CNNationalAccounts.GRID_NAME,
						resultSet.getString(CNNationalAccounts.GRID_NAME));
				rstNationalAccounts.put(CNNationalAccounts.COLUMN_NAMES,
						resultSet.getString(CNNationalAccounts.COLUMN_NAMES));
				rstNationalAccounts.put(CNNationalAccounts.RULE_NAME,
						resultSet.getString(CNNationalAccounts.RULE_NAME));
				rstNationalAccounts.put(CNNationalAccounts.RULE_TYPE,
						resultSet.getString(CNNationalAccounts.RULE_TYPE));
				rstNationalAccounts.put(CNNationalAccounts.RULE_CATEGORY,
						resultSet.getString(CNNationalAccounts.RULE_CATEGORY));
				rstNationalAccounts.put(CNNationalAccounts.RULE_PRICE,
						resultSet.getString(CNNationalAccounts.RULE_PRICE));
				rstNationalAccounts.put(CNNationalAccounts.RULE_STATUS,
						resultSet.getString(CNNationalAccounts.RULE_STATUS));
				rstNationalAccounts.put(CNNationalAccounts.LOCATION_COUNT,
						resultSet.getString(CNNationalAccounts.LOCATION_COUNT));
				rstNationalAccounts.put(CNNationalAccounts.GRID_RULE_NAME,
						resultSet.getString(CNNationalAccounts.GRID_RULE_NAME));
				rstNationalAccounts.put(CNNationalAccounts.MIN_MAX_EXACT_PRICE,
						resultSet.getString(CNNationalAccounts.MIN_MAX_EXACT_PRICE));
				rstNationalAccounts.put(CNNationalAccounts.LOCATION, resultSet.getString(CNNationalAccounts.LOCATION));
				rstNationalAccounts.put(CNNationalAccounts.PROMPT_TITLE,
						resultSet.getString(CNNationalAccounts.PROMPT_TITLE));
				rstNationalAccounts.put(CNNationalAccounts.RULE_PAGE_TITLE,
						resultSet.getString(CNNationalAccounts.RULE_PAGE_TITLE));
				rstNationalAccounts.put(CNNationalAccounts.RULE_PRICE,
						resultSet.getString(CNNationalAccounts.RULE_PRICE));
				rstNationalAccounts.put(CNNationalAccounts.ERROR_MESSAGE,
						resultSet.getString(CNNationalAccounts.ERROR_MESSAGE));

			}
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		} finally {
			try {
				statement.close();
			} catch (SQLException exc) {
				Assert.fail(exc.toString());
			}
		}
		return rstNationalAccounts;
	}

	public Map<String, String> getLockerSystemData(String query, String testcaseID) {
		Map<String, String> rstLockerSystem = new HashMap<>();
		Statement statement = null;
		String sqlQuery = Constants.EMPTY_STRING;
		try {

			if (connection == null)
				getConnection();
			statement = connection.createStatement();
			sqlQuery = query + testcaseID;
			ResultSet resultSet = statement.executeQuery(sqlQuery);
			while (resultSet.next()) {
				rstLockerSystem.put(CNLockerSystem.COLUMN_NAMES, resultSet.getString(CNLockerSystem.COLUMN_NAMES));
				rstLockerSystem.put(CNLockerSystem.PAGE_TITLE, resultSet.getString(CNLockerSystem.PAGE_TITLE));
				rstLockerSystem.put(CNLockerSystem.LOCKER_MODEL, resultSet.getString(CNLockerSystem.LOCKER_MODEL));
				rstLockerSystem.put(CNLockerSystem.LOCKER_MODEL_TITLE,
						resultSet.getString(CNLockerSystem.LOCKER_MODEL_TITLE));
				rstLockerSystem.put(CNLockerSystem.SYSTEM_NAME, resultSet.getString(CNLockerSystem.SYSTEM_NAME));
				rstLockerSystem.put(CNLockerSystem.DISPLAY_NAME, resultSet.getString(CNLockerSystem.DISPLAY_NAME));
				rstLockerSystem.put(CNLockerSystem.REQUIRED_DATA, resultSet.getString(CNLockerSystem.REQUIRED_DATA));
				rstLockerSystem.put(CNLockerSystem.LOCATION_NAME, resultSet.getString(CNLockerSystem.LOCATION_NAME));
				rstLockerSystem.put(CNLockerSystem.DEFAULT_LOCATION,
						resultSet.getString(CNLockerSystem.DEFAULT_LOCATION));
				rstLockerSystem.put(CNLockerSystem.LOCATION_NAME, resultSet.getString(CNLockerSystem.LOCATION_NAME));
				rstLockerSystem.put(CNLockerSystem.LOCKER_MODEL_TITLE,
						resultSet.getString(CNLockerSystem.LOCKER_MODEL_TITLE));
				rstLockerSystem.put(CNLockerSystem.TEST_DATA, resultSet.getString(CNLockerSystem.TEST_DATA));
				rstLockerSystem.put(CNLockerSystem.ERROR_MESSAGE, resultSet.getString(CNLockerSystem.ERROR_MESSAGE));

			}
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		} finally {
			try {
				statement.close();
			} catch (SQLException exc) {
				Assert.fail(exc.toString());
			}
		}
		return rstLockerSystem;
	}

	public Map<String, String> getLocationData(String query, String testcaseID) {
		Map<String, String> rstLocation = new HashMap<>();
		Statement statement = null;
		String sqlQuery = Constants.EMPTY_STRING;
		try {

			if (connection == null)
				getConnection();
			statement = connection.createStatement();
			sqlQuery = query + testcaseID;
			ResultSet resultSet = statement.executeQuery(sqlQuery);
			while (resultSet.next()) {
				rstLocation.put(CNLocation.LOCATION_NAME, resultSet.getString(CNLocation.LOCATION_NAME));
				rstLocation.put(CNLocation.PROMOTION_NAME, resultSet.getString(CNLocation.PROMOTION_NAME));
				rstLocation.put(CNLocation.PROMOTION_TYPE, resultSet.getString(CNLocation.PROMOTION_TYPE));
				rstLocation.put(CNLocation.REQUIRED_DATA, resultSet.getString(CNLocation.REQUIRED_DATA));
				rstLocation.put(CNLocation.ACTUAL_DATA, resultSet.getString(CNLocation.ACTUAL_DATA));
				rstLocation.put(CNLocation.LOCATIONLIST_DPDN_VALUE,
						resultSet.getString(CNLocation.LOCATIONLIST_DPDN_VALUE));
				rstLocation.put(CNLocation.TAB_NAME, resultSet.getString(CNLocation.TAB_NAME));
				rstLocation.put(CNLocation.PRODUCT_NAME, resultSet.getString(CNLocation.PRODUCT_NAME));
				rstLocation.put(CNLocation.COLUMN_VALUE, resultSet.getString(CNLocation.COLUMN_VALUE));
				rstLocation.put(CNLocation.COLUMN_NAME, resultSet.getString(CNLocation.COLUMN_NAME));
				rstLocation.put(CNLocation.POPUP_NAME, resultSet.getString(CNLocation.POPUP_NAME));
				rstLocation.put(CNLocation.TIMEZONE, resultSet.getString(CNLocation.TIMEZONE));
				rstLocation.put(CNLocation.TYPE, resultSet.getString(CNLocation.TYPE));
				rstLocation.put(CNLocation.TITLE, resultSet.getString(CNLocation.TITLE));
				rstLocation.put(CNLocation.NAME, resultSet.getString(CNLocation.NAME));
				rstLocation.put(CNLocation.DEVICE_NAME, resultSet.getString(CNLocation.DEVICE_NAME));
				rstLocation.put(CNLocation.SHOW_RECORDS, resultSet.getString(CNLocation.SHOW_RECORDS));
				rstLocation.put(CNLocation.INITIAL_BALANCE, resultSet.getString(CNLocation.INITIAL_BALANCE));
				rstLocation.put(CNLocation.CONTACT_EMAIL, resultSet.getString(CNLocation.CONTACT_EMAIL));
				rstLocation.put(CNLocation.ADDRESS, resultSet.getString(CNLocation.ADDRESS));
				rstLocation.put(CNLocation.INFO_NOTES, resultSet.getString(CNLocation.INFO_NOTES));
				rstLocation.put(CNLocation.INFO_MSG, resultSet.getString(CNLocation.INFO_MSG));
			}
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		} finally {
			try {
				statement.close();
			} catch (SQLException exc) {
				Assert.fail(exc.toString());
			}
		}
		return rstLocation;
	}

	public Map<String, String> getV5DeviceData(String query, String testcaseID) {
		Map<String, String> rstV5Device = new HashMap<>();
		Statement statement = null;
		String sqlQuery = Constants.EMPTY_STRING;
		try {
			if (connection == null)
				getConnection();
			statement = connection.createStatement();
			sqlQuery = query + testcaseID;
			ResultSet resultSet = statement.executeQuery(sqlQuery);
			while (resultSet.next()) {
				rstV5Device.put(CNV5Device.PRODUCT_NAME, resultSet.getString(CNV5Device.PRODUCT_NAME));
				rstV5Device.put(CNV5Device.REQUIRED_DATA, resultSet.getString(CNV5Device.REQUIRED_DATA));
				rstV5Device.put(CNV5Device.ACTUAL_DATA, resultSet.getString(CNV5Device.ACTUAL_DATA));
				rstV5Device.put(CNV5Device.PIN, resultSet.getString(CNV5Device.PIN));
				rstV5Device.put(CNV5Device.EMAIL_ID, resultSet.getString(CNV5Device.EMAIL_ID));
				rstV5Device.put(CNV5Device.TIMEOUT_POPUP, resultSet.getString(CNV5Device.TIMEOUT_POPUP));
				rstV5Device.put(CNV5Device.LANDING_PAGE, resultSet.getString(CNV5Device.LANDING_PAGE));
				rstV5Device.put(CNV5Device.ORDER_PAGE, resultSet.getString(CNV5Device.ORDER_PAGE));
				rstV5Device.put(CNV5Device.PRODUCT_SEARCH_PAGE, resultSet.getString(CNV5Device.PRODUCT_SEARCH_PAGE));
				rstV5Device.put(CNV5Device.DRIVER_PAGE, resultSet.getString(CNV5Device.DRIVER_PAGE));
				rstV5Device.put(CNV5Device.FUND_ACCOUNT_PAGE, resultSet.getString(CNV5Device.FUND_ACCOUNT_PAGE));
				rstV5Device.put(CNV5Device.LOGIN_PAGE, resultSet.getString(CNV5Device.LOGIN_PAGE));
				rstV5Device.put(CNV5Device.CREDIT_DEBIT_PAGE, resultSet.getString(CNV5Device.CREDIT_DEBIT_PAGE));
				rstV5Device.put(CNV5Device.CREATE_ACCOUNT, resultSet.getString(CNV5Device.CREATE_ACCOUNT));
				rstV5Device.put(CNV5Device.ACCOUNT_DETAILS, resultSet.getString(CNV5Device.ACCOUNT_DETAILS));
				rstV5Device.put(CNV5Device.QUICK_SCAN_SETUP, resultSet.getString(CNV5Device.QUICK_SCAN_SETUP));
				rstV5Device.put(CNV5Device.FINGER_PRINT_SETUP, resultSet.getString(CNV5Device.FINGER_PRINT_SETUP));
				rstV5Device.put(CNV5Device.EDIT_ACCOUNT_DETAILS, resultSet.getString(CNV5Device.EDIT_ACCOUNT_DETAILS));
				rstV5Device.put(CNV5Device.TIME_OUT_POPUP, resultSet.getString(CNV5Device.TIME_OUT_POPUP));
				rstV5Device.put(CNV5Device.TRANSACTION_CANCEL, resultSet.getString(CNV5Device.TRANSACTION_CANCEL));
				rstV5Device.put(CNV5Device.CHANGE_PIN, resultSet.getString(CNV5Device.CHANGE_PIN));
				rstV5Device.put(CNV5Device.PAYMENTS_PAGE, resultSet.getString(CNV5Device.PAYMENTS_PAGE));
				rstV5Device.put(CNV5Device.LANGUAGE, resultSet.getString(CNV5Device.LANGUAGE));
				rstV5Device.put(CNV5Device.LOCATION, resultSet.getString(CNV5Device.LOCATION));

			}
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		} finally {
			try {
				statement.close();
			} catch (SQLException exc) {
				Assert.fail(exc.toString());
			}
		}
		return rstV5Device;
	}

	public Map<String, String> getPickListData(String query, String testcaseID) {
		Map<String, String> rstPickList = new HashMap<>();
		Statement statement = null;
		String sqlQuery = Constants.EMPTY_STRING;
		try {
			if (connection == null)
				getConnection();
			statement = connection.createStatement();
			sqlQuery = query + testcaseID;
			ResultSet resultSet = statement.executeQuery(sqlQuery);
			while (resultSet.next()) {
				rstPickList.put(CNPickList.LOCATIONS, resultSet.getString(CNPickList.LOCATIONS));
				rstPickList.put(CNPickList.PRODUCT_NAME, resultSet.getString(CNPickList.PRODUCT_NAME));
				rstPickList.put(CNPickList.NEED, resultSet.getString(CNPickList.NEED));
				rstPickList.put(CNPickList.RECORDS, resultSet.getString(CNPickList.RECORDS));
				rstPickList.put(CNPickList.ROW_VALUES, resultSet.getString(CNPickList.ROW_VALUES));
				rstPickList.put(CNPickList.COLUMN_HEADERS, resultSet.getString(CNPickList.COLUMN_HEADERS));
				rstPickList.put(CNPickList.APLOCATION, resultSet.getString(CNPickList.APLOCATION));
			}
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		} finally {
			try {
				statement.close();
			} catch (SQLException exc) {
				Assert.fail(exc.toString());
			}
		}
		return rstPickList;
	}

	public Map<String, String> getOrgSummaryData(String query, String testcaseID) {
		Map<String, String> rstOrg = new HashMap<>();
		Statement statement = null;
		String sqlQuery = Constants.EMPTY_STRING;
		try {
			if (connection == null)
				getConnection();
			statement = connection.createStatement();
			sqlQuery = query + testcaseID;
			ResultSet resultSet = statement.executeQuery(sqlQuery);
			while (resultSet.next()) {

				rstOrg.put(CNOrgSummary.NAME, resultSet.getString(CNOrgSummary.NAME));
				rstOrg.put(CNOrgSummary.REQUIRED_DATA, resultSet.getString(CNOrgSummary.REQUIRED_DATA));
				rstOrg.put(CNOrgSummary.ORG_NAME, resultSet.getString(CNOrgSummary.ORG_NAME));

			}
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		} finally {
			try {
				statement.close();
			} catch (SQLException exc) {
				Assert.fail(exc.toString());
			}
		}

		return rstOrg;

	}

	public Map<String, String> getProductData(String query, String testcaseID) {
		Map<String, String> rstProduct = new HashMap<>();
		Statement statement = null;
		String sqlQuery = Constants.EMPTY_STRING;
		try {

			if (connection != null) {
				statement = connection.createStatement();
				sqlQuery = query + testcaseID;
				ResultSet resultSet = statement.executeQuery(sqlQuery);
				while (resultSet.next()) {

					rstProduct.put(CNProduct.SCANCODE, resultSet.getString(CNProduct.SCANCODE));
					rstProduct.put(CNProduct.SCANCODE_ERROR, resultSet.getString(CNProduct.SCANCODE_ERROR));
					rstProduct.put(CNProduct.SUCCESS_SCANCODE, resultSet.getString(CNProduct.SUCCESS_SCANCODE));
					rstProduct.put(CNProduct.SCANCODE, resultSet.getString(CNProduct.SCANCODE));
					rstProduct.put(CNProduct.REQUIRED_DATA, resultSet.getString(CNProduct.REQUIRED_DATA));
					rstProduct.put(CNProduct.PRODUCT_NAME, resultSet.getString(CNProduct.PRODUCT_NAME));

				}
			}
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		} finally {
			try {
				statement.close();
			} catch (SQLException exc) {
				Assert.fail(exc.toString());
			}
		}

		return rstProduct;

	}

	public Map<String, String> getConsumerData(String query, String testcaseID) {
		Map<String, String> rstConsumer = new HashMap<>();
		Statement statement = null;
		String sqlQuery = Constants.EMPTY_STRING;
		try {
			if (connection == null)
				getConnection();
			statement = connection.createStatement();
			sqlQuery = query + testcaseID;

			ResultSet resultSet = statement.executeQuery(sqlQuery);
			while (resultSet.next()) {

				rstConsumer.put(CNConsumer.LOCATION, resultSet.getString(CNConsumer.LOCATION));
				rstConsumer.put(CNConsumer.LAST_NAME, resultSet.getString(CNConsumer.LAST_NAME));
				rstConsumer.put(CNConsumer.PIN_ERROR, resultSet.getString(CNConsumer.PIN_ERROR));
				rstConsumer.put(CNConsumer.PIN, resultSet.getString(CNConsumer.PIN));
				rstConsumer.put(CNConsumer.PHONE, resultSet.getString(CNConsumer.PHONE));
				rstConsumer.put(CNConsumer.EMAIL, resultSet.getString(CNConsumer.EMAIL));
				rstConsumer.put(CNConsumer.AMOUNT, resultSet.getString(CNConsumer.AMOUNT));
				rstConsumer.put(CNConsumer.ERROR_MSG, resultSet.getString(CNConsumer.ERROR_MSG));
				rstConsumer.put(CNConsumer.INFO_MSG, resultSet.getString(CNConsumer.INFO_MSG));
				rstConsumer.put(CNConsumer.STATUS, resultSet.getString(CNConsumer.STATUS));
				rstConsumer.put(CNConsumer.SEARCH_BY, resultSet.getString(CNConsumer.SEARCH_BY));
				rstConsumer.put(CNConsumer.FIRST_NAME, resultSet.getString(CNConsumer.FIRST_NAME));
				rstConsumer.put(CNConsumer.EMAIL_ERROR, resultSet.getString(CNConsumer.EMAIL_ERROR));
				rstConsumer.put(CNConsumer.SCANID_ERROR, resultSet.getString(CNConsumer.SCANID_ERROR));
				rstConsumer.put(CNConsumer.COLUMN_NAME, resultSet.getString(CNConsumer.COLUMN_NAME));
				rstConsumer.put(CNConsumer.CONSUMER_ID, resultSet.getString(CNConsumer.CONSUMER_ID));
				rstConsumer.put(CNConsumer.ADJUST_BALANCE, resultSet.getString(CNConsumer.ADJUST_BALANCE));
			}

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		} finally {
			try {
				statement.close();
			} catch (SQLException exc) {
				Assert.fail(exc.toString());
			}
		}
		return rstConsumer;
	}

	public Map<String, String> getSuperListData(String query, String testcaseID) {
		Map<String, String> rstSuperListData = new HashMap<>();
		Statement statement = null;
		String sqlQuery = Constants.EMPTY_STRING;
		try {
			if (connection == null)
				getConnection();
			statement = connection.createStatement();
			sqlQuery = query + testcaseID;

			ResultSet resultSet = statement.executeQuery(sqlQuery);
			while (resultSet.next()) {
				rstSuperListData.put(CNSuperList.SUPER_NAME, resultSet.getString(CNSuperList.SUPER_NAME));
				rstSuperListData.put(CNSuperList.EFT_DISBURSEMENT, resultSet.getString(CNSuperList.EFT_DISBURSEMENT));
				rstSuperListData.put(CNSuperList.DISBURSEMENT_PAGE_RECORD,
						resultSet.getString(CNSuperList.DISBURSEMENT_PAGE_RECORD));
				rstSuperListData.put(CNSuperList.DISBURSEMENT_DATE, resultSet.getString(CNSuperList.DISBURSEMENT_DATE));
				rstSuperListData.put(CNSuperList.ERROR_MESSAGE, resultSet.getString(CNSuperList.ERROR_MESSAGE));
				rstSuperListData.put(CNSuperList.UPDATED_DATA, resultSet.getString(CNSuperList.UPDATED_DATA));

			}
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		} finally {
			try {
				statement.close();
			} catch (SQLException exc) {
				Assert.fail(exc.toString());
			}
		}
		return rstSuperListData;
	}

	public Map<String, String> getAdminAgeVerificationData(String query, String testcaseID) {
		Map<String, String> rstAdminAgeVerificationData = new HashMap<>();
		Statement statement = null;
		String sqlQuery = Constants.EMPTY_STRING;
		try {
			if (connection == null)
				getConnection();
			statement = connection.createStatement();
			sqlQuery = query + testcaseID;

			ResultSet resultSet = statement.executeQuery(sqlQuery);
			while (resultSet.next()) {
				rstAdminAgeVerificationData.put(CNAdminAgeVerification.LOCATION_NAME,
						resultSet.getString(CNAdminAgeVerification.LOCATION_NAME));
				rstAdminAgeVerificationData.put(CNAdminAgeVerification.MAIL,
						resultSet.getString(CNAdminAgeVerification.MAIL));
				rstAdminAgeVerificationData.put(CNAdminAgeVerification.REQUIRED_DATA,
						resultSet.getString(CNAdminAgeVerification.REQUIRED_DATA));
				rstAdminAgeVerificationData.put(CNAdminAgeVerification.STATUS,
						resultSet.getString(CNAdminAgeVerification.STATUS));
			}
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		} finally {
			try {
				statement.close();
			} catch (SQLException exc) {
				Assert.fail(exc.toString());
			}
		}
		return rstAdminAgeVerificationData;
	}

	public Map<String, String> getLoginPageData(String query, String testcaseID) {
		Map<String, String> rstLoginPageData = new HashMap<>();
		Statement statement = null;
		String sqlQuery = Constants.EMPTY_STRING;
		try {
			if (connection == null)
				getConnection();
			statement = connection.createStatement();
			sqlQuery = query + testcaseID;

			ResultSet resultSet = statement.executeQuery(sqlQuery);
			while (resultSet.next()) {
				rstLoginPageData.put(CNLoginPage.INVALID, resultSet.getString(CNLoginPage.INVALID));
				rstLoginPageData.put(CNLoginPage.MAIL, resultSet.getString(CNLoginPage.MAIL));
				rstLoginPageData.put(CNLoginPage.REQUIRED_DATA, resultSet.getString(CNLoginPage.REQUIRED_DATA));
				rstLoginPageData.put(CNLoginPage.PASSWORD, resultSet.getString(CNLoginPage.PASSWORD));
				rstLoginPageData.put(CNLoginPage.BROWSER, resultSet.getString(CNLoginPage.BROWSER));
			}
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		} finally {
			try {
				statement.close();
			} catch (SQLException exc) {
				Assert.fail(exc.toString());
			}
		}
		return rstLoginPageData;
	}

	public Map<String, String> getAdminDNAData(String query, String testcaseID) {
		Map<String, String> rstAdminDNAData = new HashMap<>();
		Statement statement = null;
		String sqlQuery = Constants.EMPTY_STRING;
		try {
			if (connection == null)
				getConnection();
			statement = connection.createStatement();
			sqlQuery = query + testcaseID;

			ResultSet resultSet = statement.executeQuery(sqlQuery);
			while (resultSet.next()) {
				rstAdminDNAData.put(CNAdminDNA.LOCATION_NAME, resultSet.getString(CNAdminDNA.LOCATION_NAME));
				rstAdminDNAData.put(CNAdminDNA.ORG_NAME, resultSet.getString(CNAdminDNA.ORG_NAME));
				rstAdminDNAData.put(CNAdminDNA.REQUIRED_DATA, resultSet.getString(CNAdminDNA.REQUIRED_DATA));
			}
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		} finally {
			try {
				statement.close();
			} catch (SQLException exc) {
				Assert.fail(exc.toString());
			}
		}
		return rstAdminDNAData;
	}

	public Map<String, String> getStaffViewData(String query, String testcaseID) {
		Map<String, String> rstStaffViewData = new HashMap<>();
		Statement statement = null;
		String sqlQuery = Constants.EMPTY_STRING;
		try {
			if (connection == null)
				getConnection();
			statement = connection.createStatement();
			sqlQuery = query + testcaseID;
			ResultSet resultSet = statement.executeQuery(sqlQuery);
			while (resultSet.next()) {
				rstStaffViewData.put(CNStaffSummary.STAFF, resultSet.getString(CNStaffSummary.STAFF));
				rstStaffViewData.put(CNStaffSummary.STAFF_NAME, resultSet.getString(CNStaffSummary.STAFF_NAME));
			}
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		} finally {
			try {
				statement.close();
			} catch (SQLException exc) {
				Assert.fail(exc.toString());
			}
		}
		return rstStaffViewData;
	}

	public Map<String, String> getSSODomainData(String query, String testcaseID) {
		Map<String, String> rstSSODomainData = new HashMap<>();
		Statement statement = null;
		String sqlQuery = Constants.EMPTY_STRING;
		try {
			if (connection == null)
				getConnection();
			statement = connection.createStatement();
			sqlQuery = query + testcaseID;
			ResultSet resultSet = statement.executeQuery(sqlQuery);
			while (resultSet.next()) {
				rstSSODomainData.put(CNSSODomain.DOMAIN_ADDRESS, resultSet.getString(CNSSODomain.DOMAIN_ADDRESS));
				rstSSODomainData.put(CNSSODomain.DOMAIN_NAME, resultSet.getString(CNSSODomain.DOMAIN_NAME));
				rstSSODomainData.put(CNSSODomain.REQUIRED_DATA, resultSet.getString(CNSSODomain.REQUIRED_DATA));
			}
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		} finally {
			try {
				statement.close();
			} catch (SQLException exc) {
				Assert.fail(exc.toString());
			}
		}
		return rstSSODomainData;
	}

	public Map<String, String> getRoundUpCharity(String query, String testcaseID) {
		Map<String, String> rstRoundUpCharityData = new HashMap<>();
		Statement statement = null;
		String sqlQuery = Constants.EMPTY_STRING;
		try {
			if (connection == null)
				getConnection();
			statement = connection.createStatement();
			sqlQuery = query + testcaseID;
			ResultSet resultSet = statement.executeQuery(sqlQuery);
			while (resultSet.next()) {
				rstRoundUpCharityData.put(CNRoundUpCharity.NAME, resultSet.getString(CNRoundUpCharity.NAME));
				rstRoundUpCharityData.put(CNRoundUpCharity.REQUIRED_OPTIONS,
						resultSet.getString(CNRoundUpCharity.REQUIRED_OPTIONS));
				rstRoundUpCharityData.put(CNRoundUpCharity.DISPLAY_NAME,
						resultSet.getString(CNRoundUpCharity.DISPLAY_NAME));
				rstRoundUpCharityData.put(CNRoundUpCharity.LOCATION, resultSet.getString(CNRoundUpCharity.LOCATION));
				rstRoundUpCharityData.put(CNRoundUpCharity.CAUSE_NAME,
						resultSet.getString(CNRoundUpCharity.CAUSE_NAME));
			}
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		} finally {
			try {
				statement.close();
			} catch (SQLException exc) {
				Assert.fail(exc.toString());
			}
		}
		return rstRoundUpCharityData;
	}
}
