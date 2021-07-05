package at.framework.database.mssql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import org.testng.Assert;

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
import at.smartshop.database.columns.CNNationalAccounts;
import at.smartshop.database.columns.CNNavigationMenu;
import at.smartshop.database.columns.CNProductSummary;
import at.smartshop.database.columns.CNReportList;
import at.smartshop.database.columns.CNUserRoles;
import at.smartshop.database.columns.CNV5Device;
import at.smartshop.keys.Constants;

public class ResultSets extends Connections {

	public Map<String, String> getNavigationMenuData(String query, String testcaseID) {
		Map<String, String> rstNavigationMenu = new HashMap<>();

		Statement statement = null;
		String sqlQuery = Constants.EMPTY_STRING;
		try {

			if (connection != null) {
				statement = connection.createStatement();
				sqlQuery = query + testcaseID;
				ResultSet resultSet = statement.executeQuery(sqlQuery);
				while (resultSet.next()) {
					rstNavigationMenu.put(CNNavigationMenu.MENU_ITEM, resultSet.getString(CNNavigationMenu.MENU_ITEM));
					rstNavigationMenu.put(CNNavigationMenu.REQUIRED_OPTION,resultSet.getString(CNNavigationMenu.REQUIRED_OPTION));
				}
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
					rstLocationSummary.put(CNLocationSummary.ENABLE_RETRIEVE_ACCOUNT,
							resultSet.getString(CNLocationSummary.ENABLE_RETRIEVE_ACCOUNT));
					rstLocationSummary.put(CNLocationSummary.HAS_LOCKERS,
							resultSet.getString(CNLocationSummary.HAS_LOCKERS));
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
		return rstLocationSummary;

	}

	public Map<String, String> getConsumerSummaryData(String query, String testcaseID) {
		Map<String, String> rstConsumerSummary = new HashMap<>();
		Statement statement = null;
		String sqlQuery = Constants.EMPTY_STRING;
		try {
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
			if (connection != null) {
				statement = connection.createStatement();
				sqlQuery = query + testcaseID;

				ResultSet resultSet = statement.executeQuery(sqlQuery);
				while (resultSet.next()) {
					rstLocationList.put(CNLocationList.DROPDOWN_LOCATION_LIST,
							resultSet.getString(CNLocationList.DROPDOWN_LOCATION_LIST));
					rstLocationList.put(CNLocationList.LOCATION_NAME,
							resultSet.getString(CNLocationList.LOCATION_NAME));
					rstLocationList.put(CNLocationList.COLUMN_NAME, resultSet.getString(CNLocationList.COLUMN_NAME));
					rstLocationList.put(CNLocationList.INFO_MESSAGE, resultSet.getString(CNLocationList.INFO_MESSAGE));
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
		return rstLocationList;
	}

	public Map<String, String> getGmaUserData(String query, String testcaseID) {
		Map<String, String> rstGmaUser = new HashMap<>();
		Statement statement = null;
		String sqlQuery = Constants.EMPTY_STRING;
		try {
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
					rstNationalAccounts.put(CNNationalAccounts.LOCATION,
							resultSet.getString(CNNationalAccounts.LOCATION));
					rstNationalAccounts.put(CNNationalAccounts.PROMPT_TITLE,
							resultSet.getString(CNNationalAccounts.PROMPT_TITLE));
					rstNationalAccounts.put(CNNationalAccounts.RULE_PAGE_TITLE,
							resultSet.getString(CNNationalAccounts.RULE_PAGE_TITLE));
					rstNationalAccounts.put(CNNationalAccounts.RULE_PRICE,
							resultSet.getString(CNNationalAccounts.RULE_PRICE));
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
		return rstNationalAccounts;
	}

	public Map<String, String> getLockerSystemData(String query, String testcaseID) {
		Map<String, String> rstLockerSystem = new HashMap<>();
		Statement statement = null;
		String sqlQuery = Constants.EMPTY_STRING;
		try {

			if (connection != null) {
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
					rstLockerSystem.put(CNLockerSystem.REQUIRED_DATA,
							resultSet.getString(CNLockerSystem.REQUIRED_DATA));
					rstLockerSystem.put(CNLockerSystem.LOCATION_NAME,
							resultSet.getString(CNLockerSystem.LOCATION_NAME));
					rstLockerSystem.put(CNLockerSystem.DEFAULT_LOCATION,
							resultSet.getString(CNLockerSystem.DEFAULT_LOCATION));
					rstLockerSystem.put(CNLockerSystem.LOCATION_NAME,
							resultSet.getString(CNLockerSystem.LOCATION_NAME));
					rstLockerSystem.put(CNLockerSystem.LOCKER_MODEL_TITLE,
							resultSet.getString(CNLockerSystem.LOCKER_MODEL_TITLE));
					rstLockerSystem.put(CNLockerSystem.TEST_DATA, resultSet.getString(CNLockerSystem.TEST_DATA));
					rstLockerSystem.put(CNLockerSystem.ERROR_MESSAGE,
							resultSet.getString(CNLockerSystem.ERROR_MESSAGE));

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
		return rstLockerSystem;
	}

	public Map<String, String> getLocationData(String query, String testcaseID) {
		Map<String, String> rstLocation = new HashMap<>();
		Statement statement = null;
		String sqlQuery = Constants.EMPTY_STRING;
		try {

			if (connection != null) {
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
					rstLocation.put(CNLocation.POPUP_NAME, resultSet.getString(CNLocation.POPUP_NAME));
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
		return rstLocation;
	}

	public Map<String, String> getV5DeviceData(String query, String testcaseID) {
		Map<String, String> rstV5Device = new HashMap<>();
		Statement statement = null;
		String sqlQuery = Constants.EMPTY_STRING;
		try {
			if (connection != null) {
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
		return rstV5Device;
	}
}
