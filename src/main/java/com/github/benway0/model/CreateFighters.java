package com.github.benway0.model;

import com.github.benway0.fighter.Fighter;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * The class responsible for actually creating the fighter objects after
 * the rankings and IDs have been retrieved.
 */
public final class CreateFighters {
        
    /** List of IDs from the GetID class **/
    private List<List<Integer>> idLists;
    
    /** Contains the lists of fighter objects for every weight class **/
    private List<List> setList;
    
    /** Number of weight classes **/
    private static int NUM_OF_WEIGHTS = 10;

    /**
     * @param idLists 0 = Flyweight, 1 = Bantamweight, 2 = Featherweight, 3 =
     * Lightweight, 4 = Welterweight, 5 = Middleweight, 6 = Light_Heavyweight, 7
     * = Heavyweight, 8 = Women_Strawweight, 9 = Women_Bantamweight
     */
    public CreateFighters(List<List<Integer>> idLists) {
        this.idLists = idLists;
    }
    
    /**
     * Calls initLists() before populating them and setting the points.
     */
    public void init() {
        initLists();
        
        int n = 0;
        for (List<Integer> list : idLists) {
            populateList(list, n);
            n++;
        }

        for (List<Fighter> list : setList) {
            setPoints(list);
        }
    }

    /**
     * Initialises setList and creates a new ArrayList for every weight class.
     */
    private void initLists() {
        setList = new ArrayList<>();
        
        for (int i = 0; i < NUM_OF_WEIGHTS; i++)
            setList.add(new ArrayList<>());
    }

    /**
     * Populates each list of fighters with the correct information.
     * 
     * @param list current ID list to cycle through
     * @param n the specific list in setList to populate
     */
    private void populateList(List<Integer> list, int n) {
        for (int id : list) {
            setList.get(n).add(createFighterObject(id));
        }
    }

    /**
     * Set the points for each fighter (which are used to determine likelihood
     * of winning).
     * It decrements by 5% down the list, but if it reaches below 200 the
     * setPoints() method makes sure it doesn't go any lower than that.
     * 
     * @param list current list to set
     */
    private void setPoints(List<Fighter> list) {
        double points = 1000.0;
        for (Fighter f : list) {
            f.setPoints(points);
            f.setKoPercentage();
            f.setSubPercentage();
            points *= 0.95;

            /*
            For some reason profile pictures with _AD in the URL break the
            rankings page so this replaces them with the default picture
            */
            if (f.getProfile_image().contains("_AD")) {
                f.setProfile_image(
                        "http://imagec.ufc.com/http%253A%252F%252Fmedia.ufc.tv"
                        + "%252F%252Ffighter_images%252F%252FComingSoon"
                        + "%252Fcomingsoon_headshot_odopod.png?mw300-"
                        + "mh300-tc1");
            }
        }
    }

    /**
     * Takes a json ID as input and returns the corresponding fighter object.
     * 
     * @param fighterID json ID of the fighter
     * @return fighter object that matches fighterID
     */
    private Fighter createFighterObject(int fighterID) {
        try {
            URL get = new URL("http://ufc-data-api.ufc.com/api/v3/us/fighters/"
                    + fighterID + ".json");
            URLConnection uc = get.openConnection();

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(uc.getInputStream()));

            return new Gson().fromJson(br, Fighter.class);
        } catch (IOException ex) {
            ex.getMessage();
        }
        return null;
    }

    /**
     * Used to sort the fighter lists after fights occur.
     * 
     * @param list specific list to sort
     */
    public void sortFighters(List<Fighter> list) {
        Collections.sort(list, new FighterCompare());
        
        // Set new rankings based on list order
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isTitle_holder())
                list.get(i).setRank("C");
            else if (i < 16)
                list.get(i).setRank(Integer.toString(i));
            else
                list.get(i).setRank("NR");
        }
    }
    
    public void sortAll() {
        for (List<Fighter> list : setList) {
            sortFighters(list);
        }
    }
    
    public List<Fighter> getList(int i) {
        return setList.get(i);
    }
}

/**
 * Comparator used to sort the fighters after a fight and place them in the new
 * order.
 * 
 * @author benway
 */
class FighterCompare implements Comparator<Fighter> {

    /**
     * First compares the fighters by whether they hold a title (champion is
     * always on top even if he has less points than everyone else) and then
     * by points.
     * 
     * @param f1 first fighter to compare
     * @param f2 second fighter to compare
     * @return result of the comparison
     */
    @Override
    public int compare(Fighter f1, Fighter f2) {
        boolean a = f1.isTitle_holder();
        boolean b = f2.isTitle_holder();
        int sComp = Boolean.valueOf(b).compareTo(a);

        if (sComp != 0) {
            return sComp;
        }

        Double x1 = f1.getPoints();
        Double x2 = f2.getPoints();

        // If x1 and x2 are equal we need to use this so that the ArrayLists
        // don't treat the two Fighter objects as equal
        Double x3 = f2.getPoints() - 1;

        // If the two points are not equal we just return the normal comparison
        // If they are equal then compare with x3 instead so they both still
        // appear in the ArrayList.
        if (x2.compareTo(x1) != 0) {
            return x2.compareTo(x1);
        } else {
            return x1.compareTo(x3);
        }
    }
}