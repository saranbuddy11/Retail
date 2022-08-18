package at.smartshop.keys;

public class Constants {
	private Constants() {

	}

	public static final String DELIMITER_COLON = ":";
	public static final String DELIMITER_HASH = "#";
	public static final String DELIMITER_TILD = "~";
	public static final String DELIMITER_HYPHEN = "-";
	public static final String DELIMITER_SPACE = " ";
	public static final String DELIMITER_COMMA = ",";
	public static final String EMPTY_STRING = "";
	public static final String REPLACE_DOLLOR = "[^-?0-9.]+";
	public static final String PARENTHESES = "[()]";
	public static final String VALUE = "value";
	public static final String SRC = "src";
	public static final String TEXT = "text";
	public static final String INNER_TEXT = "innerText";
	public static final String REGEX_TRANS_DATE = "[^a-zA-Z0-9]";
	public static final String DATA = "data";
	public static final String ASCENDING = "Ascending";
	public static final String DESCENDING = "Descending";
	public static final String LOCAL = "Local";
	public static final String REMOTE = "Remote";
	public static final String CHROME = "Chrome";
	public static final String FIREFOX = "Firefox";
	public static final String INTERNET_EXPOLRER = "InternetExplorer";
	public static final String REGEX_DDMMYYYY = "DDMMYYYY";
	public static final String REGEX_DDMMMYYYY = "ddMMMYYYY";
	public static final String REGEX_MMDDUU = "MM/dd/uu";
	public static final String REGEX_MMDDYY = "MM/dd/yy";
	public static final String REGEX_DDMMYY = "dd/MM/YY";
	public static final String REGEX_MM_DD_YYYY = "MM/dd/YYYY";
	public static final String REGEX_DD_MM_YYYY = "dd/MM/YYYY";
	public static final String REGEX_YYYY_MM_DD = "YYYY-MM-dd";
	public static final String REGEX_DD = "dd";
	public static final String TIME_ZERO = "00:00";
	public static final String REGEX_HHMMSS = "HHmmss";
	public static final String TIME_STAMP = "dd_MM_yy_hh_mm_ss_aa";
	public static final String REGEX_CHAR = "abcdefghijklmnopqrstuvwxyz";
	public static final String REGEX_NUMBER = "0123456789";
	public static final String ACCOUNT_NAME = "Test";
	public static final String REPORT_NAME = "\\Results.html";
	public static final String REPORTS = "\\Reports\\";
	public static final String DELIMITER_BACKSLASH = "\\";
	public static final String NA = "N/A";
	public static final String TIME_ZONE_INDIA = "Asia/Kolkata";
	public static final String NEW_LINE = "\n";
	public static final String DOLLAR = "\\$";
	public static final String DOLLAR_SYMBOL = "$";
	public static final int ONE_SECOND = 1;
	public static final int TWO_SECOND = 2;
	public static final int THREE_SECOND = 3;
	public static final int SHORT_TIME = 5;
	public static final int MEDIUM_TIME = 15;
	public static final int LONG_TIME = 20;
	public static final int EXTRA_LONG_TIME = 30;
	public static final int FIFTY_FIVE_SECONDS = 55;
	public static final String ATTRIBUTE_VALUE = "value";
	public static final int FIFTEEN_SECOND = 15;
	public static final String PRE_PROD = "PreProd";
	public static final String TEST3 = "Test3";
	public static final String TEST_RAIL_PASS_MESSAGE = "QA PASS";
	public static final String TEST_RAIL_PASS_STATUSID = "1";
	public static final String TEST_RAIL_FAIL_STATUSID = "5";
	public static final String PASS = "Pass";
	public static final String FAIL = "Fail";
	public static final String SKIP = "Skip";
	public static final String EMAIL_NAME = "AutomationBatchExecution_Report_envValue_Date.html";
	public static final String EMAIL_SUBJECT = "ADM and V5 | envValue Environment | Automation Batch Execution - Report | Date";
	public static final String EMAIL_MESSAGE1 = "Hi Team,\r\n" + "<br>\r\n <br>\r\n"
			+ "Please find <b>'ADM and V5 Device - Automation Batch Execution Report </b> on Testrail for <b>envValue</b> Environment, executed on ScriptFixes branch from GIT repo. "
			+ "<br>\r\n" + "<br>\r\n"
			+ "Link to view results in TestRail Suite - https://365retailmarkets.testrail.net/index.php?/runs/view/1775"
			+ "<br>\r\n" + "<br>\r\n" + "Attached Execution Report for reference.\r\n" + "<br>\r\n" + "<br>\r\n"
			+ "Environment Details: <b>envValue<b>\r\n";

	public static final String EMAIL_OVERALL_RESULT = "<b><u>Overall Result:</u></b>\r\n";
	public static final String EMAIL_MODULE_RESULT = "<b><u>Module wise Result:</u></b>";
	public static final String EMAIL_MESSAGE2 = "***this mail is auto-generated email.\r\n<br><br>" + "Thank You,\r\n"
			+ "<br>\r\n" + "AutomationTeam";
	public static final String EMAIL_RESULT_BODY = "<html><body>\r\n"
			+ "   <table cellpadding=\"0\" cellspacing=\"0\" width=\"200\" align=\"left\" border=\"1\">\r\n"
			+ "   <tbody>\r\n" + "      <tr style=\"background-color:#c4c4c4;\" bold=\"\">\r\n"
			+ "         <td align=\"center\"><b>Module</b></td>\r\n"
			+ "         <td align=\"center\"><b>Total</b></td>\r\n"
			+ "         <td align=\"center\"><b>Pass</b></td>\r\n"
			+ "         <td align=\"center\"><b>Fail</b></td>\r\n"
			+ "         <td align=\"center\"><b>Skip</b></td>\r\n" + "      </tr>\r\n";
	public static final String EMAIL_RESULT_TAIL = "   </tbody>\r\n" + "</table>\r\n" + "</body></html>";
	public static final String YES = "Yes";
	public static final String ATTRIBUTE_READ = "aria-readonly";
	public static final String AUTOMATION = "automation";
	public static final String SHOW = "Show";
	public static final String HIDE = "Hide";
	public static final String AUTO_TEST_EMAIL = "@autotestemail.com";
	public static final String AUTO_TEST = "Autotest";
	public static final String SCHEDULED = "Scheduled";
	public static final String TESTING = "Testing";
	public static final String DELIMITER_DOT = ".";
	public static final String REGEX_MMDDYYYY = "MM.dd.yyyy";
	public static final String EDITED = "Edited";
	public static final String ALL = "All";
	public static final String LANGUAGE = "LANGUAGE";
	public static final String CONTINUE = "Continue";
	public static final String ENGLISH = "English";
	public static final String CHOOSENOTHING = "-Choose-";
	public static final String TODAY = "Today";
	public static final String ACCEPTED = "ACCEPTED";
	public static final String REMOVE_LEADING_ZERO = "^0+(?!$)";
	public static final String DELIMITER_PERCENTAGE = "%";
	public static final String DELIMITER_EMPTY = "";
	public static final String USD = "USD";
	public static final String DECIMAL_FORMAT = "###.##";
}
