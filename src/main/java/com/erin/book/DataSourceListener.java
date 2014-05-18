package com.erin.book;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class DataSourceListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        ServletContext servletContext = event.getServletContext();
        String driver = servletContext.getInitParameter("driver");
        String url = servletContext.getInitParameter("url");
        String user = servletContext.getInitParameter("user");
        String password = servletContext.getInitParameter("password");

        Database database = new Database(driver, url, user, password);
        servletContext.setAttribute("database", database);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
