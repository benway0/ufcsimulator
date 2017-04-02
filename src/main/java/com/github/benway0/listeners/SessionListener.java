package com.github.benway0.listeners;

import com.github.benway0.fighter.Fighter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/** 
 * Whenever a new session is detected and created, this class takes the fighter
 * data from the ServletContext attributes and creates its own version to be
 * used by the session. This is so that users are unable to edit the original
 * objects and always have a fresh set to work with any time they reset the
 * session.
 */
@WebListener
public class SessionListener implements HttpSessionListener {

    /**
     * Creates new session unique lists from all of the lists of fighter data
     * found in the ServletContext attributes.
     * @param se HttpSessionEvent object
     */
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        ServletContext sc = session.getServletContext();
        
        List<Fighter> flwSession = new ArrayList<>();
        for (Fighter f : (List<Fighter>) sc.getAttribute("flwContext")) {
            flwSession.add(new Fighter(f));
        }
        List<Fighter> bwSession = new ArrayList<>();
        for (Fighter f : (List<Fighter>) sc.getAttribute("bwContext")) {
            bwSession.add(new Fighter(f));
        }
        List<Fighter> fwSession = new ArrayList<>();
        for (Fighter f : (List<Fighter>) sc.getAttribute("fwContext")) {
            fwSession.add(new Fighter(f));
        }
        List<Fighter> lwSession = new ArrayList<>();
        for (Fighter f : (List<Fighter>) sc.getAttribute("lwContext")) {
            lwSession.add(new Fighter(f));
        }
        List<Fighter> wwSession = new ArrayList<>();
        for (Fighter f : (List<Fighter>) sc.getAttribute("wwContext")) {
            wwSession.add(new Fighter(f));
        }
        List<Fighter> mwSession = new ArrayList<>();
        for (Fighter f : (List<Fighter>) sc.getAttribute("mwContext")) {
            mwSession.add(new Fighter(f));
        }
        List<Fighter> lhwSession = new ArrayList<>();
        for (Fighter f : (List<Fighter>) sc.getAttribute("lhwContext")) {
            lhwSession.add(new Fighter(f));
        }
        List<Fighter> hwSession = new ArrayList<>();
        for (Fighter f : (List<Fighter>) sc.getAttribute("hwContext")) {
            hwSession.add(new Fighter(f));
        }
        List<Fighter> wswSession = new ArrayList<>();
        for (Fighter f : (List<Fighter>) sc.getAttribute("wswContext")) {
            wswSession.add(new Fighter(f));
        }
        List<Fighter> wbwSession = new ArrayList<>();
        for (Fighter f : (List<Fighter>) sc.getAttribute("wbwContext")) {
            wbwSession.add(new Fighter(f));
        }
        
        session.setAttribute("flwSession", flwSession);
        session.setAttribute("bwSession", bwSession);
        session.setAttribute("fwSession", fwSession);
        session.setAttribute("lwSession", lwSession);
        session.setAttribute("wwSession", wwSession);
        session.setAttribute("mwSession", mwSession);
        session.setAttribute("lhwSession", lhwSession);
        session.setAttribute("hwSession", hwSession);
        session.setAttribute("wswSession", wswSession);
        session.setAttribute("wbwSession", wbwSession);
    }

    /**
     * @param se HttpSessionEvent object
     */
    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
    }
}