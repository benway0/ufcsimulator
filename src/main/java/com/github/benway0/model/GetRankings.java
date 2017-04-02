package com.github.benway0.model;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Scrapes the UFC.com ranking page using JSoup and gets a list of all currently
 * ranked fighters for each major weight class.
 * This class is only necessary because the UFC json API contains bugs and
 * has fighters listed in the wrong weight class etc. so there is no way to
 * reliably get the latest rankings from the json.
 */
public final class GetRankings {
    
    /** A list of all the currently ranked fighters **/
    private List<String> rankingList;

    /**
     * Uses JSoup to scrape the UFC ranking page to retrieve a list of all
     * ranked fighters.
     * The UFC json API can often be erroneous, so it's
     * necessary to have a complete list of names prior to working with the
     * json API.
     */
    public void populateList() {
        rankingList = new ArrayList<>();
        List<String> champList = new ArrayList<>();

        try {
            Document doc = Jsoup.connect("http://www.ufc.com/rankings").get();

            // Scrape the page to find every ranked fighter
            Elements fighters = doc.getElementsByClass("name-column");
            for (Element fighter : fighters) {
                Elements links = fighter.select("a[href]");
                for (Element link : links) {

                    /*
                    Fighter name might contain additional information if they
                    are an interim champion, this makes sure it's removed
                    before adding it to the list
                     */
                    String s = link.text();
                    if (s.contains(" (Interim Champion)")) {
                        s = s.replace(" (Interim Champion)", "");
                    }
                    rankingList.add(s);
                }
            }

            // Scrape the page to find champion fighters
            Elements champions = doc.getElementsByClass("fighter-name");
            for (Element champion : champions) {
                Elements links = champion.select("a[href]");
                for (Element link : links) {
                    champList.add(link.text());
                }
            }

            // Remove pound for pound rankings
            rankingList.subList(0, 15).clear();

            // Insert champions in their correct places
            int pos = 0;
            for (String s : champList) {
                rankingList.add(pos, s);
                pos += 16;
            }
        } catch (IOException ex) {
            ex.getMessage();
        }
    }
    
    /**
     * Accessor method for the ranking list.
     * 
     * @return list of currently ranked fighters
     */
    public List<String> getRankingList() {
        return rankingList;
    }
}