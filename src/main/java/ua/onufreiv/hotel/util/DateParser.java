package ua.onufreiv.hotel.util;

import org.apache.log4j.Logger;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by yurii on 1/28/17.
 */
public class DateParser {
    private final static Logger logger = Logger.getLogger(DateParser.class);

    public static Date parse(String dateString, String format) {
        Date date = null;
        DateFormat df = new SimpleDateFormat(format);
        try {
            date = df.parse(dateString);
        } catch (ParseException e) {
            logger.error("Failed to parse date: ", e);
        }
        return date;
    }
}
