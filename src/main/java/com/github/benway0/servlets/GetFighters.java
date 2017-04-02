package com.github.benway0.servlets;

import com.github.benway0.fighter.Fighter;
import com.github.benway0.model.CreateFighters;
import com.github.benway0.model.GetID;
import com.github.benway0.model.GetRankings;

import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * The main class that loads when the app is deployed and can also be triggered
 * from the admin panel. Gets all of the fighter data and stores it in context
 * attributes to be used later by individual sessions.
 */
@WebServlet(name = "GetFighters", urlPatterns = {"/getfighters"})
public class GetFighters extends HttpServlet {

    /**
     * Processes requests for both HTTP GET and POST methods.
     * 
     * Uses classes from the model to load fighter data into context attributes.
     * Whenever a new session is created it copies data from these attributes
     * into its own unique attributes.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletContext sc = request.getServletContext();
        getData(sc);
        
        response.sendRedirect("reset");
    }
    
    public void getData(ServletContext sc) {
        GetRankings gr = new GetRankings();
        gr.populateList();

        GetID gID = new GetID(gr.getRankingList());
        gID.init();

        CreateFighters cf = new CreateFighters(gID.getIDLists());
        cf.init();

        sc.setAttribute("cf", cf);

        /*
        This is only here because Germaine de Randamie is champion in
        another division at the moment and she shouldn't be ranked as
        a champion
         */
        for (Fighter f : cf.getList(9)) {
            if (f.getFirst_name().equals("Germaine")) {
                f.setTitle_holder(false);
            }
        }

        sc.setAttribute("flwContext", cf.getList(0));
        sc.setAttribute("bwContext", cf.getList(1));
        sc.setAttribute("fwContext", cf.getList(2));
        sc.setAttribute("lwContext", cf.getList(3));
        sc.setAttribute("wwContext", cf.getList(4));
        sc.setAttribute("mwContext", cf.getList(5));
        sc.setAttribute("lhwContext", cf.getList(6));
        sc.setAttribute("hwContext", cf.getList(7));
        sc.setAttribute("wswContext", cf.getList(8));
        sc.setAttribute("wbwContext", cf.getList(9));
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
        return "Loads the fighter data into context attributes";
    }
}