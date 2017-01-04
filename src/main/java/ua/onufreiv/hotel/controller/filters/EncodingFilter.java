package ua.onufreiv.hotel.controller.filters;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created by yurii on 1/4/17.
 */
public class EncodingFilter implements Filter {
    private static final String ENCODING_PARAM_NAME = "encoding";
    private String encoding;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        encoding = filterConfig.getInitParameter(ENCODING_PARAM_NAME);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String reqEncoding = request.getCharacterEncoding();
        String respEncoding = response.getCharacterEncoding();

        if(!encoding.equalsIgnoreCase(reqEncoding)) {
            request.setCharacterEncoding(encoding);
        }
        if(!encoding.equalsIgnoreCase(respEncoding)) {
            response.setCharacterEncoding(encoding);
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        encoding = null;
    }
}
