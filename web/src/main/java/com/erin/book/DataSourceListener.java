package com.erin.book;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class DataSourceListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        ServletContext servletContext = event.getServletContext();
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("database.xml");
        Database database = (Database) classPathXmlApplicationContext.getBean("database");
        servletContext.setAttribute("database", database);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
