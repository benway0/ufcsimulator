package com.github.benway0.filters;

import com.github.benway0.dao.DBConnect;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 * A simple authentication filter to make sure regular clients can't update
 * the context fighter data.
 */
@WebFilter(filterName = "AuthFilter", urlPatterns = {"/getfighters"})
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
            throws IOException, ServletException {
        DBConnect db = new DBConnect();
        db.connect();

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        if (db.auth(username, password))
            chain.doFilter(request, response);
        else
            request.getRequestDispatcher("/error.jsp").forward(request, response);
    }

    @Override
    public void destroy() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}