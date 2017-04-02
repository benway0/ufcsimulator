package com.github.benway0.servlets;

import com.github.benway0.fighter.Fighter;
import com.github.benway0.model.CreateFighters;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Used for editing the statistics of the fighters.
 */
@WebServlet(name = "EditProfile", urlPatterns = {"/editprofile"})
public class EditProfile extends HttpServlet {

    /**
     * Processes requests for both HTTP GET and POST methods.
     * 
     * Performs checks to make sure that the values input by the client are
     * valid. If not, redirect them to where they came and display the error.
     * If valid, update the information and redirect them to the original
     * profile.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        boolean valid = true;
        int rankPoints = 0, koPoints = 0, subPoints = 0;
        
        // Check whether the user tried to input something that isn't a number
        try {
            rankPoints = Integer.parseInt(request.getParameter("rankpoints"));
            koPoints = Integer.parseInt(request.getParameter("ko"));
            subPoints = Integer.parseInt(request.getParameter("sub"));
        } catch (NumberFormatException ex) {
            request.setAttribute("error", "You can only input numbers");
            valid = false;
        }
        
        // Make sure the rank points are between 1 and 99999
        if (rankPoints < 1 || rankPoints > 99999) {
            request.setAttribute("error",
                    "Rank Points must be between 1 and 99999");
            valid = false;
        }
        
        // Make sure ko or sub points are positive
        if (koPoints < 0 || subPoints < 0) {
            request.setAttribute("error",
                    "KO and Submission points can't be below 0");
            valid = false;
        }
        
        // Make sure ko and sub points don't add up to more than 100 (% based)
        if ((koPoints + subPoints) > 100) {
            request.setAttribute("error",
                    "KO and Submission points must add up to no more than 100");
            valid = false;
        }
        
        // If there was an error redirect them to where they came from
        if (!valid) {
            request.getRequestDispatcher("profile?wc="
                    + request.getParameter("wc") + "&id="
                    + request.getParameter("id") + "&action=edit")
                    .forward(request, response);
        } else {
            
            // If everything is fine, change the data to the client input
            ServletContext sc = request.getServletContext();
            HttpSession session = request.getSession();
            String wc = request.getParameter("wc") + "Session";
            List<Fighter> rankings = (List<Fighter>) session.getAttribute(wc);

            for (Fighter f : rankings) {
                if (f.getId().equals(request.getParameter("id"))) {
                    f.setPoints(rankPoints);
                    f.setKoPercentage(koPoints);
                    f.setSubPercentage(subPoints);
                    
                    // So the values no longer auto update based on wins
                    f.setKoSubChanged(true);
                }
            }
            
            CreateFighters cf = (CreateFighters) sc.getAttribute("cf");
            cf.sortFighters(rankings);
            
            // When all is done send them back to the fighter's profile page
            request.getRequestDispatcher("profile?wc="
                    + request.getParameter("wc") + "&id="
                    + request.getParameter("id")).forward(request, response);
        }
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
        return "Edit a fighter's profile";
    }
}