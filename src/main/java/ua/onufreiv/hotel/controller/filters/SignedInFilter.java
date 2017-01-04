package ua.onufreiv.hotel.controller.filters;

import ua.onufreiv.hotel.controller.manager.PathConfig;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by yurii on 1/4/17.
 */
public class SignedInFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest) request).getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            ((HttpServletResponse) response).sendRedirect(PathConfig.getInstance()
                    .getProperty(PathConfig.NOT_SIGNED_IN_ERROR_PAGE_PATH));
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
