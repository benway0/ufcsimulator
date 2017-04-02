package com.github.benway0.servlets;

import com.github.benway0.fighter.Fighter;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Redirects the client to the relevant profile based on weight class and ID
 * parameters.
 */
@WebServlet(name = "Profile", urlPatterns = {"/profile"})
public class Profile extends HttpServlet {

    /**
     * Processes requests for both HTTP GET and POST methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Find the right list based on weight class
        HttpSession session = request.getSession();
        String wc = request.getParameter("wc") + "Session";
        List<Fighter> rankings = (List<Fighter>) session.getAttribute(wc);
        
        // Find fighter whos ID matches the ID in the request parameter
        Fighter f1 = new Fighter();
        for (Fighter f : rankings)
            if (f.getId().equals(request.getParameter("id")))
                f1 = f;
        request.setAttribute("fighter", f1);
        
        String action = request.getParameter("action");
        if (action != null && action.equals("edit"))
            request.getRequestDispatcher("editprofile.jsp")
                    .forward(request, response);
        else
            request.getRequestDispatcher("profile.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP GET method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP POST method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Direct client to the relevant profile";
    }
}