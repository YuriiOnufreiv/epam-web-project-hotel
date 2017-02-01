package ua.onufreiv.hotel.tag;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/**
 * Created by yurii on 1/15/17.
 */
public class AutoPageRefreshTag extends SimpleTagSupport {
    private final String REFRESH_HEADER_NAME = "Refresh";
    private int interval;

    public void setInterval(int interval) {
        this.interval = interval;
    }

    @Override
    public void doTag() throws JspException, IOException {
        PageContext context = (PageContext) getJspContext();
        HttpServletResponse response = (HttpServletResponse) context.getResponse();
        response.setIntHeader(REFRESH_HEADER_NAME, interval);
    }
}
