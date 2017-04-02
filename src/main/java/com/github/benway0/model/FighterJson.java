package com.github.benway0.model;

/**
 * The basic fighter object used to temporarily store simple json data to
 * use before the more complex fighter objects are created.
 */
public class FighterJson {
    
    private int id;

    private String first_name;

    private String rank;

    private String last_name;

    private String weight_class;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getWeight_class() {
        return weight_class;
    }
}