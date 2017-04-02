package com.github.benway0.listeners;

import com.github.benway0.servlets.GetFighters;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Web application lifecycle listener.
 * Loads all of the latest fighter data into the application on deployment.
 * Can also be controlled through the GetFighters servlet on the fly.
 */
@WebListener
public class ContextListener implements ServletContextListener {

    /**
     * Gets all of the fighter data and stores it in context attributes to be
     * used later by individual sessions.
     * 
     * @param sce ServletContextEvent
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        GetFighters gf = new GetFighters();
        ServletContext sc = sce.getServletContext();
        
        gf.getData(sc);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        
    }
}
