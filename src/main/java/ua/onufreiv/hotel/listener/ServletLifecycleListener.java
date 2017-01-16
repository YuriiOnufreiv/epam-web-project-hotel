package ua.onufreiv.hotel.listener;

import ua.onufreiv.hotel.jdbc.ConnectionManager;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by yurii on 1/16/17.
 */
public class ServletLifecycleListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ConnectionManager.createPoolFromJndi();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
