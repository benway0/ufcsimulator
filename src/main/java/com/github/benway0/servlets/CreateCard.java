package com.github.benway0.servlets;

import com.github.benway0.fighter.Fighter;
import com.github.benway0.model.CreateFighters;
import com.github.benway0.model.Fight;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Processes the fights on a card created by the client.
 */
@WebServlet(name = "CreateCard", urlPatterns = {"/createcard"})
public class CreateCard extends HttpServlet {
    
    /** Holds the list of fighter IDs on the card **/
    private String[] fighters;
    
    /** Holds the relevant ranking list **/
    private List<Fighter> rankings;
    
    /** Holds the actual objects of fighters on the card **/
    private List<Fighter> fighterObj;
    
    /** Holds the list of fights on the card **/
    private List<Fight> fightList;
    
    /** Constant for number of fights on the card **/
    private static int NUM_OF_FIGHTS = 11;
    
    /**
     * Processes requests for both HTTP GET and POST methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, 
            HttpServletResponse response) throws ServletException, IOException {
        ServletContext sc = request.getServletContext();
        
        initArray(request);
        
        fighterObj = new ArrayList<>();
        fightList = new ArrayList<>();
        
        if (hasDuplicate())
            response.sendRedirect("card.jsp");
        else {
            getFighterObj(request);
            doFights(request);

            request.setAttribute("fightList", fightList);
            
            CreateFighters cf = (CreateFighters) sc.getAttribute("cf");
            cf.sortAll();
            
            request.getRequestDispatcher("cardresults.jsp")
                    .forward(request, response);  
        }
    }
    
    /**
     * Loads the fighter IDs into an array based on the parameters passed from
     * the create card page.
     * 
     * @param request 
     */
    protected void initArray(HttpServletRequest request) {
        fighters = new String[NUM_OF_FIGHTS * 2];
        
        for (int i = 0, j = 1; i < fighters.length; i+=2, j++) {
            fighters[i] = request.getParameter("fighterA" + j);
            fighters[i+1] = request.getParameter("fighterB" + j);
        }
    }
    
    /**
     * Uses a HashSet to check the fighters on the card for duplicates incase
     * the Javascript fails.
     * 
     * @return whether the ID list contains duplicates
     */
    protected boolean hasDuplicate() {
        Set<String> set = new HashSet<>();
        for (String s : fighters) {
            if (set.contains(s)) return true;
            set.add(s);
        }
        return false;
    }
    
    /**
     * Loads the actual fighter objects into an array.
     * 
     * @param req HttpServletRequest object
     */
    protected void getFighterObj(HttpServletRequest req) {
        HttpSession session = req.getSession();
        
        for (int i = 1; i <= NUM_OF_FIGHTS; i++) {
            String fightWc = req.getParameter("fight" + i);
            rankings = (List<Fighter>) session.getAttribute(fightWc
                    + "Session");
            String fighterA = req.getParameter("fighterA" + i);
            String fighterB = req.getParameter("fighterB" + i);
            for (Fighter f : rankings) {
                if (f.getId().equals(fighterA)) {
                    fighterObj.add(f);
                }
            }
            for (Fighter f : rankings) {
                if (f.getId().equals(fighterB)) {
                    fighterObj.add(f);
                }
            }
        }
    }
    
    /**
     * Processes the fights on the card.
     * 
     * @param req HttpServletRequest object
     */
    protected void doFights(HttpServletRequest req) {
        int rounds;
        int j = 0;
        for (int i = 0; i < NUM_OF_FIGHTS; i++) {
            boolean check = (req.getParameter("check" + (i+1)) != null);
            if (check) 
                rounds = 5;
            else 
                rounds = 3;
            fightList.add(new Fight(
                    fighterObj.get(j), fighterObj.get(j+1), rounds));
            j+=2;
        }
        
        for (Fight fight : fightList) {
            fight.init();
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
        return "Create the card and process the fights";
    }
}