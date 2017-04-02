package com.github.benway0.servlets;

import com.github.benway0.fighter.Fighter;
import com.github.benway0.model.CreateFighters;
import com.github.benway0.model.Fight;

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
 * This servlet processes fights when using the single fight simulator on the
 * rankings page.
 */
@WebServlet(name = "FightServlet", urlPatterns = {"/fight"})
public class FightServlet extends HttpServlet {

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
        
        ServletContext sc = request.getServletContext();
        HttpSession session = request.getSession();
                
        String fighterA[] = request.getParameter("fighterA").split("\\|");
        String fighterB[] = request.getParameter("fighterB").split("\\|");
        
        // Check whether both fighters are the same, if so display an error
        if (fighterA[1].equals(fighterB[1])) {
            request.setAttribute("error", true);
            getServletContext().getRequestDispatcher("/rankings.jsp?wc=" +
                    request.getParameter("wc")).forward(request, response);
        } else {
            
            /*
            Otherwise find the relevant fighter from the relevant ranking
            list and process the fight.
            */
            String wc = request.getParameter("wc") + "Session";
            List<Fighter> rankings = (List<Fighter>) session.getAttribute(wc);
            
            Fighter f1 = new Fighter();
            Fighter f2 = new Fighter();
            
            for (Fighter f : rankings) {
                if (fighterA[1].equals(f.getId()))
                    f1 = f;
                if (fighterB[1].equals(f.getId()))
                    f2 = f;
            }
            
            int rounds;
            if (request.getParameter("rounds").equals("three"))
                rounds = 3;
            else
                rounds = 5;
            
            Fight fight = new Fight(f1, f2, rounds);
            fight.init();
            
            CreateFighters cf = (CreateFighters) sc.getAttribute("cf");
            cf.sortFighters(rankings);
                        
            request.setAttribute("fight", fight);
            
            request.getRequestDispatcher("rankings.jsp?wc=" +
                    request.getParameter("wc")).forward(request, response);
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
        return "Processes single fights from the rankings page";
    }
}