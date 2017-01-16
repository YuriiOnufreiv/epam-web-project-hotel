package ua.onufreiv.hotel.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by yurii on 1/15/17.
 */
public class DateTag extends SimpleTagSupport {
    private Date date;
    private String locale;
    private boolean showTime;

    public void setDate(Date date) {
        this.date = date;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public void setShowTime(boolean showTime) {
        this.showTime = showTime;
    }

    @Override
    public void doTag() throws JspException, IOException {
        String formattedDate;

        switch (locale) {
            case "en":
                if (showTime) {
                    formattedDate = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, Locale.US).format(date);
                } else {
                    formattedDate = DateFormat.getDateInstance(DateFormat.SHORT, Locale.US).format(date);
                }
                break;
            case "ua":
                if (showTime) {
                    formattedDate = new SimpleDateFormat("dd.MM.yyyy HH:mm").format(date);
                } else {
                    formattedDate = new SimpleDateFormat("dd.MM.yyyy").format(date);
                }
                break;
            default:
                if (showTime) {
                    formattedDate = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, Locale.UK).format(date);
                } else {
                    formattedDate = DateFormat.getDateInstance(DateFormat.SHORT, Locale.US).format(date);
                }
                break;
        }

        getJspContext().getOut().write(formattedDate);
    }
}
