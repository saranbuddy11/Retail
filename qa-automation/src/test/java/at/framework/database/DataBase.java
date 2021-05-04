package at.framework.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import org.testng.Assert;

import at.smartshop.database.columns.*;
import at.smartshop.keys.Constants;

public class DataBase {

	private MsSql mysql = new MsSql();

	public Map<String, String> getNavigationMenuData(String query, String testcaseID) throws SQLException {
		Map<String, String> rstNavigationMenu = new HashMap<>();
		Connection connection = null;
		Statement statement = null;
		String sqlQuery = Constants.EMPTY_STRING;
		try {
			connection = mysql.getDBConnection();
			if (connection != null) {
				statement = connection.createStatement();
				sqlQuery = query + testcaseID;

				ResultSet resultSet = statement.executeQuery(sqlQuery);
				while (resultSet.next()) {
					rstNavigationMenu.put(CNNavigationMenu.MENU_ITEM, resultSet.getString(CNNavigationMenu.MENU_ITEM));
					rstNavigationMenu.put(CNNavigationMenu.REQUIRED_OPTION,
							resultSet.getString(CNNavigationMenu.REQUIRED_OPTION));
				}
			}
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		} finally {
			statement.close();
			connection.close();
		}
		return rstNavigationMenu;

	}

	public Map<String, String> getConsumerSearchData(String query, String testcaseID) throws SQLException {
		Map<String, String> rstConsumerSearch = new HashMap<>();
		Connection connection = null;
		Statement statement = null;
		String sqlQuery = Constants.EMPTY_STRING;
		try {
			connection = mysql.getDBConnection();
			if (connection != null) {
				statement = connection.createStatement();
				sqlQuery = query + testcaseID;

				ResultSet resultSet = statement.executeQuery(sqlQuery);
				while (resultSet.next()) {
					rstConsumerSearch.put(CNConsumerSearch.CONSUMER_ID,
							resultSet.getString(CNConsumerSearch.CONSUMER_ID));
					rstConsumerSearch.put(CNConsumerSearch.SEARCH_BY, resultSet.getString(CNConsumerSearch.SEARCH_BY));
					rstConsumerSearch.put(CNConsumerSearch.STATUS, resultSet.getString(CNConsumerSearch.STATUS));
					rstConsumerSearch.put(CNConsumerSearch.LOCATION, resultSet.getString(CNConsumerSearch.LOCATION));
					rstConsumerSearch.put(CNConsumerSearch.COLUMN_NAME,
							resultSet.getString(CNConsumerSearch.COLUMN_NAME));
					rstConsumerSearch.put(CNConsumerSearch.TITLE, resultSet.getString(CNConsumerSearch.TITLE));
					rstConsumerSearch.put(CNConsumerSearch.ACTIONS, resultSet.getString(CNConsumerSearch.ACTIONS));
					rstConsumerSearch.put(CNConsumerSearch.SHEET_NAME,
							resultSet.getString(CNConsumerSearch.SHEET_NAME));
					rstConsumerSearch.put(CNConsumerSearch.ATTRIBUTE, resultSet.getString(CNConsumerSearch.ATTRIBUTE));
				}
			}
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		} finally {
			statement.close();
			connection.close();
		}
		return rstConsumerSearch;
	}

	public Map<String, String> getProductSummaryData(String query, String testcaseID) throws SQLException {
		Map<String, String> rstProductSummary = new HashMap<>();
		Connection connection = null;
		Statement statement = null;
		String sqlQuery = Constants.EMPTY_STRING;
		try {
			connection = mysql.getDBConnection();
			if (connection != null) {
				statement = connection.createStatement();
				sqlQuery = query + testcaseID;

				ResultSet resultSet = statement.executeQuery(sqlQuery);
				while (resultSet.next()) {
					rstProductSummary.put(CNProductSummary.ACTUAL_DATA,
							resultSet.getString(CNProductSummary.ACTUAL_DATA));
					rstProductSummary.put(CNProductSummary.REQUIRED_DATA,
							resultSet.getString(CNProductSummary.REQUIRED_DATA));
					rstProductSummary.put(CNProductSummary.COLUMN_NAME,
							resultSet.getString(CNProductSummary.COLUMN_NAME));
					rstProductSummary.put(CNProductSummary.PRICE, resultSet.getString(CNProductSummary.PRICE));
					rstProductSummary.put(CNProductSummary.GPCMIN, resultSet.getString(CNProductSummary.GPCMIN));
					rstProductSummary.put(CNProductSummary.GPCMAX, resultSet.getString(CNProductSummary.GPCMAX));
					rstProductSummary.put(CNProductSummary.PICK_LIST_ACTION,
							resultSet.getString(CNProductSummary.PICK_LIST_ACTION));
					rstProductSummary.put(CNProductSummary.CATEGORY1, resultSet.getString(CNProductSummary.CATEGORY1));
					rstProductSummary.put(CNProductSummary.CATEGORY2, resultSet.getString(CNProductSummary.CATEGORY2));
					rstProductSummary.put(CNProductSummary.CATEGORY3, resultSet.getString(CNProductSummary.CATEGORY3));
					rstProductSummary.put(CNProductSummary.COST, resultSet.getString(CNProductSummary.COST));
					rstProductSummary.put(CNProductSummary.CASE_COUNT,
							resultSet.getString(CNProductSummary.CASE_COUNT));
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
					rstProductSummary.put(CNProductSummary.SHORT_NAME,
							resultSet.getString(CNProductSummary.SHORT_NAME));
					rstProductSummary.put(CNProductSummary.DESCRIPTION,
							resultSet.getString(CNProductSummary.DESCRIPTION));
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
					rstProductSummary.put(CNProductSummary.TARE_WEIGHT,
							resultSet.getString(CNProductSummary.TARE_WEIGHT));
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
				}
			}
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		} finally {
			statement.close();
			connection.close();
		}
		return rstProductSummary;

	}

	public Map<String, String> getLocationSummaryData(String query, String testcaseID) throws SQLException {
		Map<String, String> rstLocationSummary = new HashMap<>();
		Connection connection = null;
		Statement statement = null;
		String sqlQuery = Constants.EMPTY_STRING;
		try {
			connection = mysql.getDBConnection();
			if (connection != null) {
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
					rstLocationSummary.put(CNLocationSummary.TIME_ZONE,
							resultSet.getString(CNLocationSummary.TIME_ZONE));
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
					rstLocationSummary.put(CNLocationSummary.SICLOOKUP,
							resultSet.getString(CNLocationSummary.SICLOOKUP));
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
					rstLocationSummary.put(CNLocationSummary.POPUP_NAME,
							resultSet.getString(CNLocationSummary.POPUP_NAME));
					rstLocationSummary.put(CNLocationSummary.PRODUCT_POPUP_DETAILS,
							resultSet.getString(CNLocationSummary.PRODUCT_POPUP_DETAILS));
					rstLocationSummary.put(CNLocationSummary.SPEND_LIMIT,
							resultSet.getString(CNLocationSummary.SPEND_LIMIT));
				}
			}
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		} finally {
			statement.close();
			connection.close();
		}
		return rstLocationSummary;

	}

	public Map<String, String> getConsumerSummaryData(String query, String testcaseID) throws SQLException {
		Map<String, String> rstConsumerSummary = new HashMap<>();
		Connection connection = null;
		Statement statement = null;
		String sqlQuery = Constants.EMPTY_STRING;
		try {
			connection = mysql.getDBConnection();
			if (connection != null) {
				statement = connection.createStatement();
				sqlQuery = query + testcaseID;

				ResultSet resultSet = statement.executeQuery(sqlQuery);
				while (resultSet.next()) {
					rstConsumerSummary.put(CNConsumerSummary.REASON, resultSet.getString(CNConsumerSummary.REASON));
					rstConsumerSummary.put(CNConsumerSummary.ADJUST_BALANCE,
							resultSet.getString(CNConsumerSummary.ADJUST_BALANCE));
					rstConsumerSummary.put(CNConsumerSummary.FIRST_NAME,
							resultSet.getString(CNConsumerSummary.FIRST_NAME));
					rstConsumerSummary.put(CNConsumerSummary.LAST_NAME,
							resultSet.getString(CNConsumerSummary.LAST_NAME));
					rstConsumerSummary.put(CNConsumerSummary.PIN_ERROR,
							resultSet.getString(CNConsumerSummary.PIN_ERROR));
					rstConsumerSummary.put(CNConsumerSummary.PIN, resultSet.getString(CNConsumerSummary.PIN));
					rstConsumerSummary.put(CNConsumerSummary.MARKET_CARD_DISABLED,
							resultSet.getString(CNConsumerSummary.MARKET_CARD_DISABLED));
					rstConsumerSummary.put(CNConsumerSummary.PHONE, resultSet.getString(CNConsumerSummary.PHONE));
					rstConsumerSummary.put(CNConsumerSummary.EMAIL, resultSet.getString(CNConsumerSummary.EMAIL));
					rstConsumerSummary.put(CNConsumerSummary.AMOUNT, resultSet.getString(CNConsumerSummary.AMOUNT));
				}
			}
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		} finally {
			statement.close();
			connection.close();
		}
		return rstConsumerSummary;

	}

	public Map<String, String> getReportListData(String query, String testcaseID) throws SQLException {
		Map<String, String> rstReportList = new HashMap<>();
		Connection connection = null;
		Statement statement = null;
		String sqlQuery = Constants.EMPTY_STRING;
		try {
			connection = mysql.getDBConnection();
			if (connection != null) {
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
				}
			}
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		} finally {
			statement.close();
			connection.close();
		}
		return rstReportList;
	}

	public Map<String, String> getDeviceListData(String query, String testcaseID) throws SQLException {
		Map<String, String> rstDeviceList = new HashMap<>();
		Connection connection = null;
		Statement statement = null;
		String sqlQuery = Constants.EMPTY_STRING;
		try {
			connection = mysql.getDBConnection();
			if (connection != null) {
				statement = connection.createStatement();
				sqlQuery = query + testcaseID;

				ResultSet resultSet = statement.executeQuery(sqlQuery);
				while (resultSet.next()) {
					rstDeviceList.put(CNDeviceList.CC_PROCESSOR, resultSet.getString(CNDeviceList.CC_PROCESSOR));
					rstDeviceList.put(CNDeviceList.PRODUCT_NAME, resultSet.getString(CNDeviceList.PRODUCT_NAME));
				}
			}
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		} finally {
			statement.close();
			connection.close();
		}
		return rstDeviceList;
	}

	public Map<String, String> getLocationListData(String query, String testcaseID) throws SQLException {
		Map<String, String> rstLocationList = new HashMap<>();
		Connection connection = null;
		Statement statement = null;
		String sqlQuery = Constants.EMPTY_STRING;
		try {
			connection = mysql.getDBConnection();
			if (connection != null) {
				statement = connection.createStatement();
				sqlQuery = query + testcaseID;

				ResultSet resultSet = statement.executeQuery(sqlQuery);
				while (resultSet.next()) {
					rstLocationList.put(CNLocationList.DROPDOWN, resultSet.getString(CNLocationList.DROPDOWN));
					rstLocationList.put(CNLocationList.LOCATION_NAME,
							resultSet.getString(CNLocationList.LOCATION_NAME));
					rstLocationList.put(CNLocationList.COLUMN, resultSet.getString(CNLocationList.COLUMN));
					rstLocationList.put(CNLocationList.MESSAGE, resultSet.getString(CNLocationList.MESSAGE));
				}
			}
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		} finally {
			statement.close();
			connection.close();
		}
		return rstLocationList;
	}

	public Map<String, String> getGmaUserData(String query, String testcaseID) throws SQLException {
		Map<String, String> rstGmaUser = new HashMap<>();
		Connection connection = null;
		Statement statement = null;
		String sqlQuery = Constants.EMPTY_STRING;
		try {
			connection = mysql.getDBConnection();
			if (connection != null) {
				statement = connection.createStatement();
				sqlQuery = query + testcaseID;

				ResultSet resultSet = statement.executeQuery(sqlQuery);
				while (resultSet.next()) {
					rstGmaUser.put(CNGmaUser.START_BALANCE, resultSet.getString(CNGmaUser.START_BALANCE));
					rstGmaUser.put(CNGmaUser.PIN_VALUE, resultSet.getString(CNGmaUser.PIN_VALUE));
				}
			}
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		} finally {
			statement.close();
			connection.close();
		}
		return rstGmaUser;
	}

	public Map<String, String> getLoadProductData(String query, String testcaseID) throws SQLException {
		Map<String, String> rstLoadProduct = new HashMap<>();
		Connection connection = null;
		Statement statement = null;
		String sqlQuery = Constants.EMPTY_STRING;
		try {
			connection = mysql.getDBConnection();
			if (connection != null) {
				statement = connection.createStatement();
				sqlQuery = query + testcaseID;

				ResultSet resultSet = statement.executeQuery(sqlQuery);
				while (resultSet.next()) {
					rstLoadProduct.put(CNLoadProduct.DELETE_EXISTING_PRODUCT,
							resultSet.getString(CNLoadProduct.DELETE_EXISTING_PRODUCT));
					rstLoadProduct.put(CNLoadProduct.LOAD_TYPE, resultSet.getString(CNLoadProduct.LOAD_TYPE));
				}
			}
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		} finally {
			statement.close();
			connection.close();
		}
		return rstLoadProduct;
	}

	public Map<String, String> getGlobalProductChangeData(String query, String testcaseID) throws SQLException {
		Map<String, String> rstGlobalProductChange = new HashMap<>();
		Connection connection = null;
		Statement statement = null;
		String sqlQuery = Constants.EMPTY_STRING;
		try {
			connection = mysql.getDBConnection();
			if (connection != null) {
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
			}
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		} finally {
			statement.close();
			connection.close();
		}
		return rstGlobalProductChange;
	}

	public Map<String, String> getUserRolesData(String query, String testcaseID) throws SQLException {
		Map<String, String> rstUserRoles = new HashMap<>();
		Connection connection = null;
		Statement statement = null;
		String sqlQuery = Constants.EMPTY_STRING;
		try {
			connection = mysql.getDBConnection();
			if (connection != null) {
				statement = connection.createStatement();
				sqlQuery = query + testcaseID;

				ResultSet resultSet = statement.executeQuery(sqlQuery);
				while (resultSet.next()) {
					rstUserRoles.put(CNUserRoles.ROLE_NAME, resultSet.getString(CNUserRoles.ROLE_NAME));
					rstUserRoles.put(CNUserRoles.ROW_RECORD, resultSet.getString(CNUserRoles.ROW_RECORD));
					rstUserRoles.put(CNUserRoles.CLIENT_DROPDOWN, resultSet.getString(CNUserRoles.CLIENT_DROPDOWN));
				}
			}
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		} finally {
			statement.close();
			connection.close();
		}
		return rstUserRoles;
	}

	public Map<String, String> getNationalAccounts(String query, String testcaseID) throws SQLException {
		Map<String, String> rstNationalAccounts = new HashMap<>();
		Connection connection = null;
		Statement statement = null;
		String sqlQuery = Constants.EMPTY_STRING;
		try {
			connection = mysql.getDBConnection();
			if (connection != null) {
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
				}
			}
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		} finally {
			statement.close();
			connection.close();
		}
		return rstNationalAccounts;
	}
}
