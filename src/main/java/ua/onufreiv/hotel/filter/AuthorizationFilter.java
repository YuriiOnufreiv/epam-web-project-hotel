package ua.onufreiv.hotel.filter;

import org.apache.log4j.Logger;
import ua.onufreiv.hotel.controller.manager.PathConfig;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by yurii on 1/4/17.
 */
public class AuthorizationFilter implements Filter {
    private final static Logger logger = Logger.getLogger(AuthorizationFilter.class);

    private boolean authenticationRequired(ServletRequest request) {
        String command = request.getParameter("command");
        return command != null && !command.equals("forward") && !command.equals("login");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("AuthorizationFilter: initialized");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest requestHttp = (HttpServletRequest) request;
        HttpSession session = requestHttp.getSession(false);

        String url = requestHttp.getRequestURL().toString();
        String queryString = requestHttp.getQueryString();
        StringBuilder urlBuilder = new StringBuilder(url)
                .append("?")
                .append(queryString);

        if (authenticationRequired(request) && (session == null || session.getAttribute("user") == null)) {
            logger.info("AuthorizationFilter: UNAUTHORIZED access request to " + urlBuilder.toString());
            ((HttpServletResponse) response).sendRedirect(PathConfig.getInstance()
                    .getProperty(PathConfig.FORWARD_TO_NOT_SIGNED_IN_COMMAND_PATH));
        } else {
            logger.info("AuthorizationFilter: NO violations in access to " + urlBuilder.toString());
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        logger.info("AuthorizationFilter: destroyed");
    }
}
