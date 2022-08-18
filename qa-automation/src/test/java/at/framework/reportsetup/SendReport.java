package at.framework.reportsetup;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.HtmlEmail;
import org.testng.Assert;

import at.framework.files.PropertyFile;
import at.framework.generic.DateAndTime;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;

public class SendReport {
	private PropertyFile propertyFile = new PropertyFile();
	private DateAndTime dateAndTime = new DateAndTime();
	

	public void triggerMail(String path,String environment) {
		try {
			List<String> toEmailIDs = Arrays.asList(propertyFile.readPropertyFile(Configuration.EMAIL_To, FilePath.PROPERTY_CONFIG_FILE).split(Constants.DELIMITER_TILD));
			List<String> ccEmailIDs = Arrays.asList(propertyFile.readPropertyFile(Configuration.EMAIL_Cc, FilePath.PROPERTY_CONFIG_FILE).split(Constants.DELIMITER_TILD));
			String fromEmailIDs = propertyFile.readPropertyFile(Configuration.EMAIL_From, FilePath.PROPERTY_CONFIG_FILE);
			
			int totalCount=Listeners.passedCount+Listeners.failedCount+Listeners.skippedCount;
			EmailAttachment attachment = new EmailAttachment();
			attachment.setPath(path);
			attachment.setDisposition(EmailAttachment.ATTACHMENT);
			
			String emailName = Constants.EMAIL_NAME.replace("envValue", environment);
			String emailName_final = emailName.replace("Date", dateAndTime.getDateAndTime(Constants.REGEX_YYYY_MM_DD, Constants.TIME_ZONE_INDIA));
			attachment.setName(emailName_final);

			HtmlEmail email = new HtmlEmail();
			email.setHostName(propertyFile.readPropertyFile(Configuration.EMAIL_HOST_NAME, FilePath.PROPERTY_CONFIG_FILE));
			email.setSmtpPort(25);
			email.setSSLOnConnect(false);
			email.setFrom(fromEmailIDs);
			String emailSubject = Constants.EMAIL_SUBJECT.replace("envValue", environment);
			String emailSubject_final = emailSubject.replace("Date", dateAndTime.getDateAndTime(Constants.REGEX_YYYY_MM_DD, Constants.TIME_ZONE_INDIA));
			email.setSubject(emailSubject_final);
			
			String result="<html><body>\r\n"
					+ "   <table cellpadding=\"2\" cellspacing=\"0\" width=\"200\" align=\"left\" border=\"1\">\r\n"
					+ "   <tbody>\r\n"
					+ "      <tr style=\"background-color:#c4c4c4;\" bold=\"\">\r\n"
					+ "         <td align=\"center\"><b>Total</b></td>\r\n"
					+ "         <td align=\"center\"><b>Pass</b></td>\r\n"
					+ "         <td align=\"center\"><b>Fail</b></td>\r\n"
					+ "         <td align=\"center\"><b>Skip</b></td>\r\n"
					+ "      </tr>\r\n"
					+ "      <tr>\r\n"
					+ "         <td align=\"center\">"+totalCount+"</td>\r\n"
					+ "         <td align=\"center\">"+Listeners.passedCount+"</td>\r\n"
					+ "         <td align=\"center\">"+Listeners.failedCount+"</td>\r\n"
					+ "         <td align=\"center\">"+Listeners.skippedCount+"</td>\r\n"
					+ "      </tr>\r\n"
					+ "   </tbody>\r\n"
					+ "</table>\r\n"
					+ "</body></html>";
			
			String rows = "";
			int index=0;
			for (Map<String,Integer> resultSet : Listeners.listResultSetFinal) {				
				rows=rows+contructRow(resultSet,Listeners.classNames.get(index));
				index++;
			}
			
			String moduleResult=Constants.EMAIL_RESULT_BODY+rows+Constants.EMAIL_RESULT_TAIL;
			String emailMessage1 = Constants.EMAIL_MESSAGE1.replace("envValue", environment);
			email.setHtmlMsg(emailMessage1+"<br><br>"+Constants.EMAIL_OVERALL_RESULT+"<br><br>"+result+"<br><br><br><br>"+Constants.EMAIL_MODULE_RESULT+"<br><br>"+moduleResult+"<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>"+Constants.EMAIL_MESSAGE2);
			for (String toEmailID : toEmailIDs) {
				email.addTo(toEmailID);
			}
			for (String ccEmailID : ccEmailIDs) {
				email.addCc(ccEmailID);
			}
			email.attach(attachment);
			email.send();
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
	
	public String contructRow(Map<String, Integer> resultSet, String className) {
		Integer total = resultSet.values()
				  .stream()
				  .mapToInt(Integer::valueOf)
				  .sum();
		
		 String row= "      <tr>\r\n"
				+ "         <td align=\"center\"><b>"+className+"</b></td>\r\n"
				+ "         <td align=\"center\">"+total+"</td>\r\n"					
				+ "         <td align=\"center\">"+resultSet.get(Constants.PASS)+"</td>\r\n"
				+ "         <td align=\"center\">"+resultSet.get(Constants.FAIL)+"</td>\r\n"
				+ "         <td align=\"center\">"+resultSet.get(Constants.SKIP)+"</td>\r\n"
				+ "      </tr>\r\n";
		 return row;
	}
	
}
