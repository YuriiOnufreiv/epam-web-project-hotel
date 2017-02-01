package ua.onufreiv.hotel.filter;

import org.apache.log4j.Logger;
import ua.onufreiv.hotel.controller.manager.PathConfig;

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
public class AuthorizationFilter implements Filter {
    private final static Logger logger = Logger.getLogger(AuthorizationFilter.class);

    private static final String NO_AUTH_COMMANDS_PARAM_NAME = "noAuthCommands";
    private static final String REQUEST_COMMAND_PARAM_NAME = "command";
    private static final String USER_SESSION_ATTR_NAME = "user";

    private Set<String> authNoRequiredCommands;

    private boolean authRequired(String command) {
        return !authNoRequiredCommands.contains(command);
    }

    private boolean sessionUserIsActive(HttpSession session) {
        return session != null && session.getAttribute(USER_SESSION_ATTR_NAME) != null;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        authNoRequiredCommands = new HashSet<>();

        String noAuthCommandsParam = filterConfig.getInitParameter(NO_AUTH_COMMANDS_PARAM_NAME);
        if (noAuthCommandsParam == null || noAuthCommandsParam.length() == 0) {
            logger.info("AuthorizationFilter: no commands that doesn't require auth added");
            return;
        }

        String[] allowedCommands = noAuthCommandsParam.split(",");
        for (String command : allowedCommands) {
            String formattedCommand = command.trim().toLowerCase();
            if (formattedCommand.length() > 0) {
                authNoRequiredCommands.add(formattedCommand);
                logger.info("AuthorizationFilter: command that doesn't require auth added - " + formattedCommand);
            }
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest requestHttp = (HttpServletRequest) request;
        HttpSession session = requestHttp.getSession(false);

        String url = requestHttp.getRequestURL().toString();
        String queryString = requestHttp.getQueryString();
        StringBuilder urlBuilder = new StringBuilder(url);

        if (queryString != null) {
            urlBuilder.append("?")
                    .append(queryString);
        }

        String command = request.getParameter(REQUEST_COMMAND_PARAM_NAME);
        if (command != null && authRequired(command) && !sessionUserIsActive(session)) {
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
