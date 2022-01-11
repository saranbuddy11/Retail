package at.framework.generic;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.stream.Collectors;

import org.testng.Assert;

public class DateAndTime {
	public String getDateAndTime(String format, String requiredTimeZone) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		Date date = new Date();
		try {
			TimeZone timeZone = TimeZone.getTimeZone(requiredTimeZone);
			formatter.setTimeZone(timeZone);

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
		return (formatter.format(date));
	}

	public Collection<LocalDate> stringListToDateList(List<String> listOfText, String pattern) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern, Locale.ENGLISH);
		return listOfText.stream().map(ds -> LocalDate.parse(ds, formatter))
				.collect(Collectors.toCollection(ArrayList::new));

	}

	public String getCurrentDay() {
		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		String currentDay = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime());
		return currentDay;
	}

	public String getFutureDate(String format, String days) {
		LocalDate date = LocalDate.now();
		date = date.plusDays(Integer.parseInt(days));
		String formattedDate = date.format(DateTimeFormatter.ofPattern(format));
		return formattedDate;
	}
}
