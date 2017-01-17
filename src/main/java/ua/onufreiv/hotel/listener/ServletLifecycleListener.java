package ua.onufreiv.hotel.listener;

import org.apache.log4j.Logger;
import ua.onufreiv.hotel.persistence.ConnectionManager;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by yurii on 1/16/17.
 */
public class ServletLifecycleListener implements ServletContextListener {
    private final static Logger logger = Logger.getLogger(ServletLifecycleListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("Servlet context is initialized");
        ConnectionManager.createPoolFromJndi();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("Servlet context is destroyed");
    }
}
