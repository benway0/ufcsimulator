package com.github.benway0.fighter;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores information for each fighter, self explanatory.
 */
public class Fighter {
    
    private String first_name;
    
    private boolean title_holder;
    
    private String rank;

    private int losses;

    private int decision_wins;
    
    private String last_name;

    private String weight_class;

    private int wins;

    private int submission_wins;

    private String id;

    private int draws;

    private int ko_tko_wins;

    private double points;

    private int koPercentage;

    private int subPercentage;
    
    private String profile_image;
    
    private List<String> previousResults;
    
    /** If true stops ko and sub values from changing automatically **/
    private boolean koSubChanged;
    
    /**
     * Used to clone the fighters when creating new objects for the session.
     * 
     * @param f fighter to clone
     */
    public Fighter(Fighter f) {
        this(f.getFirst_name(), f.isTitle_holder(), f.getRank(), f.getLosses(), 
                f.getDecision_wins(), f.getLast_name(), f.getWeight_class(), 
                f.getWins(), f.getSubmission_wins(), f.getId(), f.getDraws(), 
                f.getKo_tko_wins(), f.getPoints(), f.getKoPercentage(), 
                f.getSubPercentage(), f.getProfile_image());
    }
    
    public Fighter(String first_name, boolean title_holder, String rank, 
            int losses, int decision_wins, String last_name, String weight_class, 
            int wins, int submission_wins, String id, int draws, int ko_tko_wins, 
            double points, int koPercentage, int subPercentage, 
            String profile_image) {
        this.first_name = first_name;
        this.title_holder = title_holder;
        this.rank = rank;
        this.losses = losses;
        this.decision_wins = decision_wins;
        this.last_name = last_name;
        this.weight_class = weight_class;
        this.wins = wins;
        this.submission_wins = submission_wins;
        this.id = id;
        this.draws = draws;
        this.ko_tko_wins = ko_tko_wins;
        this.points = points;
        this.koPercentage = koPercentage;
        this.subPercentage = subPercentage;
        this.profile_image = profile_image;
        previousResults = new ArrayList<>();
    }
    
    /**
     * Default constructor.
     */
    public Fighter() {
        this.first_name = "Default";
        this.title_holder = false;
        this.rank = "0";
        this.losses = 0;
        this.decision_wins = 0;
        this.last_name = "Fighter";
        this.weight_class = "Heavyweight";
        this.wins = 0;
        this.submission_wins = 0;
        this.id = "0";
        this.draws = 0;
        this.ko_tko_wins = 0;
        this.points = 0;
        this.koPercentage = 0;
        this.subPercentage = 0;
        this.profile_image = null;
        previousResults = new ArrayList<>();
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public boolean isTitle_holder() {
        return title_holder;
    }

    public void setTitle_holder(boolean title_holder) {
        this.title_holder = title_holder;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }
    
    public void addLoss() {
        losses++;
    }

    public int getDecision_wins() {
        return decision_wins;
    }

    public void setDecision_wins(int decision_wins) {
        this.decision_wins = decision_wins;
    }
    
    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getWeight_class() {
        return weight_class;
    }

    public void setWeight_class(String weight_class) {
        this.weight_class = weight_class;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }
    
    public void addWin() {
        wins++;
    }

    public int getSubmission_wins() {
        return submission_wins;
    }

    public void setSubmission_wins(int submission_wins) {
        this.submission_wins = submission_wins;
    }
    
    public void addSubWin() {
        this.submission_wins++;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getDraws() {
        return draws;
    }

    public void setDraws(int draws) {
        this.draws = draws;
    }
    
    public void addDraw() {
        this.draws++;
    }

    public int getKo_tko_wins() {
        return ko_tko_wins;
    }

    public void setKo_tko_wins(int ko_tko_wins) {
        this.ko_tko_wins = ko_tko_wins;
    }
    
    public void addKoWin() {
        this.ko_tko_wins++;
    }

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        if (points > 99999)
            this.points = 99999;
        else if (points < 150)
            this.points = 150;
        else
            this.points = points;
    }
    
    public void increasePoints(double points) {
        this.setPoints(this.points += points);
    }
    
    public void decreasePoints(double points) {
        this.setPoints(this.points -= points);
    }

    public int getKoPercentage() {
        return this.koPercentage;
    }
    
    public void setKoPercentage(int koPercentage) {
        this.koPercentage = koPercentage;
    }

    public void setKoPercentage() {
        double decimal = (double) this.ko_tko_wins / this.wins;
        
        if (decimal == 0.0)
            koPercentage = 3;
        else if (this.wins == 0)
            koPercentage = 33;
        else
            koPercentage = (int) (decimal * 100);
    }

    public int getSubPercentage() {
        return this.subPercentage;
    }
    
    public void setSubPercentage(int subPercentage) {
        this.subPercentage = subPercentage;
    }

    public void setSubPercentage() {
        double decimal = (double) this.submission_wins / this.wins;
        
        if (decimal == 0.0)
            subPercentage = 3;
        else if (this.wins == 0)
            subPercentage = 33;
        else
            subPercentage = (int) (decimal * 100);
    }
    
    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }
    
    public List<String> getPreviousResults() {
        return this.previousResults;
    }
    
    public void addResult(String result) {
        previousResults.add(result);
    }
    
    public boolean hasKoSubChanged() {
        return koSubChanged;
    }
    
    public void setKoSubChanged(boolean koSubChanged) {
        this.koSubChanged = koSubChanged;
    }
}