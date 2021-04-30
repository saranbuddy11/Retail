package at.framework.generic;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import org.testng.Assert;

public class DateAndTime {

	public String getTimeStamp(String dateTimePattern) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(dateTimePattern);
		return dateFormat.format(new Date());
	}
	
	public String getDateBasedOnZone(Date date, String format, String requiredTimeZone) {
		SimpleDateFormat formatter =new SimpleDateFormat(format);
		try {
			TimeZone timeZone = TimeZone.getTimeZone(requiredTimeZone);
			formatter.setTimeZone(timeZone);

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
		return (formatter.format(date));
	}
	
	public String getDateWithFormat(String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		Date date = new Date();
		return (formatter.format(date));
	}

	public Date getCurrentDate() {
		return new Date();
	}
	
	
}
