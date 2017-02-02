package ua.onufreiv.hotel.filter;

import org.apache.log4j.Logger;
import ua.onufreiv.hotel.controller.config.PathConfig;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by yurii on 1/4/17.
 */
public class PageAccessFilter implements Filter {
    private final static Logger logger = Logger.getLogger(PageAccessFilter.class);

    private static final String ALLOWED_PAGES_PARAM_NAME = "allowedPages";
    private static final String REQUEST_COMMAND_PARAM_NAME = "command";
    private static final String REQUEST_FORWARD_COMMAND_PARAM_NAME = "forward";
    private static final String REQUEST_PAGE_PARAM_NAME = "page";
    private static final String USER_SESSION_ATTR_NAME = "user";

    private Set<String> allowedPages;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        allowedPages = new HashSet<>();

        String allowedPagesParam = filterConfig.getInitParameter(ALLOWED_PAGES_PARAM_NAME);
        if (allowedPagesParam == null || allowedPagesParam.length() == 0) {
            logger.info("PageAccessFilter: no forbidden pages added");
            return;
        }

        String[] allowedPages = allowedPagesParam.split(",");
        for (String page : allowedPages) {
            String formattedPage = page.trim().toLowerCase();
            if (formattedPage.length() > 0) {
                this.allowedPages.add(formattedPage);
                logger.info("PageAccessFilter: forbidden page added - " + formattedPage);
            }
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest requestHttp = (HttpServletRequest) request;
        HttpSession session = requestHttp.getSession(false);

        String command = request.getParameter(REQUEST_COMMAND_PARAM_NAME);

        if (command == null || !command.equalsIgnoreCase(REQUEST_FORWARD_COMMAND_PARAM_NAME)) {
            logger.info("AuthorizationFilter: another command");
        } else {
            String page = request.getParameter(REQUEST_PAGE_PARAM_NAME).toLowerCase();

            if (allowedPages.contains(page) || session.getAttribute(USER_SESSION_ATTR_NAME) != null) {
                chain.doFilter(request, response);
            } else {
                logger.info("PageAccessFilter: access to FORBIDDEN page " + page);
                ((HttpServletResponse) response).sendRedirect(PathConfig.getInstance()
                        .getProperty(PathConfig.FORWARD_TO_NOT_SIGNED_IN_COMMAND_PATH));
                return;
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        logger.info("PageAccessFilter: destroyed");
    }
}
