package at.framework.triggeremail;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;
import org.testng.Assert;
import org.testng.annotations.Test;

import at.framework.files.PropertyFile;
import at.framework.reportsetup.Listeners;
import at.smartshop.database.columns.CNV5Device;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;

public class SendReport {
	private PropertyFile propertyFile = new PropertyFile();
	
	public void triggerMail(String path,int passedCount,int failedCount,int skippedCount) {
		try {
			List<String> toEmailIDs = Arrays.asList(propertyFile.readPropertyFile(Configuration.EMAIL_TO, FilePath.PROPERTY_CONFIG_FILE).split(Constants.DELIMITER_TILD));
			int totalCount=passedCount+failedCount+skippedCount;
			EmailAttachment attachment = new EmailAttachment();
			attachment.setPath(path);
			attachment.setDisposition(EmailAttachment.ATTACHMENT);
			attachment.setName(Constants.EMAIL_NAME);

			HtmlEmail email = new HtmlEmail();
			email.setHostName(propertyFile.readPropertyFile(Configuration.EMAIL_HOST_NAME, FilePath.PROPERTY_CONFIG_FILE));
			email.setSmtpPort(25);
			email.setSSLOnConnect(false);
			email.setFrom(toEmailIDs.get(0));
			email.setSubject(Constants.EMAIL_SUBJECT);
			
			String result="<html><body>\r\n"
					+ "   <table cellpadding=\"0\" cellspacing=\"0\" width=\"200\" align=\"left\" border=\"1\">\r\n"
					+ "   <tbody>\r\n"
					+ "      <tr style=\"background-color:#ffff00;\" bold=\"\">\r\n"
					+ "         <td align=\"center\"><b>Total</b></td>\r\n"
					+ "         <td align=\"center\"><b>Pass</b></td>\r\n"
					+ "         <td align=\"center\"><b>Fail</b></td>\r\n"
					+ "         <td align=\"center\"><b>Skip</b></td>\r\n"
					+ "      </tr>\r\n"
					+ "      <tr>\r\n"
					+ "         <td align=\"center\">"+totalCount+"</td>\r\n"
					+ "         <td align=\"center\">"+passedCount+"</td>\r\n"
					+ "         <td align=\"center\">"+failedCount+"</td>\r\n"
					+ "         <td align=\"center\">"+skippedCount+"</td>\r\n"
					+ "      </tr>\r\n"
					+ "   </tbody>\r\n"
					+ "</table>\r\n"
					+ "</body></html>";
			
			email.setHtmlMsg(Constants.EMAIL_MESSAGE1+"<br><br>"+result+"<br><br><br>"+Constants.EMAIL_MESSAGE2);
			for (String toEmailID : toEmailIDs) {
				email.addTo(toEmailID);
			}
			email.attach(attachment);
			email.send();
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
	
}
