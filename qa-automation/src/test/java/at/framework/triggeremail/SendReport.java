package at.framework.triggeremail;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SendReport {

	public void triggerMail(String path) {
		try {
			EmailAttachment attachment = new EmailAttachment();
			attachment.setPath(path);
			attachment.setDisposition(EmailAttachment.ATTACHMENT);
			attachment.setName("Results.html");

			MultiPartEmail email = new MultiPartEmail();
			email.setHostName("smtp.gmail.com");
			email.setSmtpPort(465);
			email.setAuthenticator(new DefaultAuthenticator("automationtriggeremail@gmail.com", "Welcome@1"));
			email.setSSLOnConnect(true);
			email.setFrom("automationtriggeremail@gmail.com");
			email.setSubject("Automation report");
			email.setMsg("This is a test mail....");
			email.addTo("sandeepab@nousinfo.com");
			email.addTo("vijaybu@nousinfo.com");
			email.attach(attachment);
			email.send();
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
}
