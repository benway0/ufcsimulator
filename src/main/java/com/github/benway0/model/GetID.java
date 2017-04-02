package com.github.benway0.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Retrieve the fighter IDs from the UFC json API.
 * First gets the ranked fighters and then any other fighters in each weight
 * class who are in the system and aren't ranked.
 */
public final class GetID {
    
    /** The list of ranked fighters from GetRankings **/
    private List<String> rankingList;

    /** The json data from the UFC API **/
    private List<FighterJson> jsonData;

    /** List containing an ID list for each weight class **/
    private List<List<Integer>> idLists;
    
    public GetID(List<String> rankingList) {
        this.rankingList = rankingList;
    }
    
    public void init() {
        getJsonData();
        initLists();
    }
    
    /**
     * Retrieve the json data from the UFC API.
     */
    private void getJsonData() {
        try {
            URL get = new URL("http://ufc-data-api.ufc.com/api/v3/us/fighters");
            URLConnection uc = get.openConnection();

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(uc.getInputStream()));

            jsonData = new Gson().fromJson(br,
                    new TypeToken<List<FighterJson>>() {}.getType());

        } catch (IOException e) {
            e.getMessage();
        }
    }

    /**
     * Initialise the ID lists and send them to be populated.
     */
    private void initLists() {
        idLists = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            idLists.add(new ArrayList<Integer>());
        }
        
        // For each ID list, populate based on the relevant weight class.
        String[] classes = {"Flyweight", "Bantamweight", "Featherweight",
            "Lightweight", "Welterweight", "Middleweight", "Light_Heavyweight",
            "Heavyweight", "Women_Strawweight", "Women_Bantamweight"};
        
        int lo = 0, hi = 16, wc = 0;
        
        for (List list : idLists) {
            populateList(list, lo, hi, classes[wc]);
            populateUnranked(list, classes[wc]);
            lo = hi;
            hi += 16;
            wc++;
        }
    }

    /**
     * Takes a list and populates it from the rankingList.
     * Uses lo and hi to make sure it only takes the data relevant to the
     * current weight class being processed, which is stored in wc.
     * 
     * @param wcList current list to be populated
     * @param lo number to start from in rankingList
     * @param hi number to end in rankingList
     * @param wc weight class as a String
     */
    private void populateList(List<Integer> wcList, int lo, int hi, String wc) {

        String[] ranks = {"C", "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "10", "11", "12", "13", "14", "15"};

        int n = 0;
        for (int i = lo; i < hi; i++) {
            wcList.add(getFighterID(rankingList.get(i), wc, ranks[n]));
            n++;
        }
    }
    
    /**
     * Populates the lists with any unranked fighters after the rankings have
     * been established.
     * Looks for fighters with weight class data but no ranking.
     * Includes a check to filter out rogue information in the json such as
     * "Opponent TBD"
     * 
     * @param wcList current list to be populated
     * @param wc weight class as a String
     */
    private void populateUnranked(List wcList, String wc) {
        for (FighterJson f : jsonData) {
            if (f.getWeight_class() != null
                    && f.getWeight_class().equals(wc)
                    && f.getRank() == null
                    && !f.getLast_name().equals("TBD")
                    && !f.getLast_name().equals("To Be Determined")) {
                wcList.add(f.getId());
            }
        }
    }

    /**
     * Looks through the json data and returns the ID of a fighter based
     * on their name, weight class and rank.
     * We have to attempt two methods as sometimes the API is reliable and the
     * data is incorrect or doesn't match the data from the UFC Rankings page.
     * 
     * @param fighter current fighter to find the ID for
     * @param weightClass fighter's weight class as a String
     * @param rank fighter's rank
     * @return the json ID of the current fighter
     */
    private int getFighterID(String fighter, String wc, String rank) {
        
        // Split the fighter's name into two names to make it easier to search
        String[] names = fighter.split(" ");
        if (names.length > 2) {
            names[1] = names[names.length - 1];
        }

        /*
        The first check looks for a fighter in the json data that matches the
        first and last names retrieved from the UFC Ranking page. It generally
        works, but sometimes the data doesn't match up, in which case a backup
        check searches for a fighter of equal rank, weight class and last name.
        */
        for (FighterJson f : jsonData) {
            if (f.getFirst_name().contains(names[0])
                    && f.getLast_name().contains(names[1])) {
                return f.getId();
            } else if (f.getRank() != null
                    && f.getRank().equals(rank)
                    && f.getWeight_class().equals(wc)
                    && f.getLast_name().contains(names[1].substring(0, 2))) {
                return f.getId();
            }
        }
        return -1;
    }
    
    public List<List<Integer>> getIDLists() {
        return idLists;
    }
}