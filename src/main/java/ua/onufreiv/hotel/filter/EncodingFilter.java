package ua.onufreiv.hotel.filter;

import org.apache.log4j.Logger;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created by yurii on 1/4/17.
 */
public class EncodingFilter implements Filter {
    private final static Logger logger = Logger.getLogger(EncodingFilter.class);

    private static final String ENCODING_PARAM_NAME = "encoding";
    private String encoding;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        encoding = filterConfig.getInitParameter(ENCODING_PARAM_NAME);
        logger.info("EncodingFilter.init() - required encoding: " + encoding);
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

        logger.info("EncodingFilter.doFilter() - encoding was set up");
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        encoding = null;
        logger.info("EncodingFilter.destroy()");
    }
}
