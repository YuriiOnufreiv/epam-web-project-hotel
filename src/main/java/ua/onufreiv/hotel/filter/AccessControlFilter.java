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
public class AccessControlFilter implements Filter {
    private final static Logger logger = Logger.getLogger(AccessControlFilter.class);

    private boolean userMustBeSignedIn(ServletRequest request) {
        String command = request.getParameter("command");
        return command != null && !command.equals("forward") && !command.equals("login");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("AccessControlFilter.init()");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest) request).getSession(false);

        if (userMustBeSignedIn(request) && (session == null || session.getAttribute("user") == null)) {
            logger.info("AccessControlFilter.doFilter() - user must be signed in");
            ((HttpServletResponse) response).sendRedirect(PathConfig.getInstance()
                    .getProperty(PathConfig.FORWARD_TO_NOT_SIGNED_IN_COMMAND_PATH));
        } else {
            logger.info("AccessControlFilter.doFilter() - user must not be signed in");
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        logger.info("AccessControlFilter.destroy()");
    }
}
