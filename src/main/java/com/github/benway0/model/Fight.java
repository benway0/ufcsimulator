package com.github.benway0.model;

import com.github.benway0.fighter.Fighter;

/**
 * Class to process fight events.
 */
public final class Fight {
    
    /** The initial fighter object passed to the class **/
    private Fighter f1, f2;
    
    /** Stores the winner and the loser of the fight once determined **/
    private Fighter winner, loser;
    
    /** Stores the scorecard results if necessary **/
    private String scores;
    
    /** The result of the fight **/
    private StringBuilder result;
    
    /** How many rounds the fight will last **/
    private int rounds;
    
    /** Whether the fight went to a decision **/
    private boolean decision;
    
    /** Whether the fight went to a draw **/
    private boolean draw;
    
    public Fight(Fighter f1, Fighter f2, int rounds) {
        this.f1 = f1;
        this.f2 = f2;
        this.rounds = rounds;
        result = new StringBuilder();
    }
    
    /**
     * Initialises the fight, then decides what to do based on whether the
     * result is a draw or not.
     * If it isn't a draw, the points are calculated and the method is decided.
     * If it is a draw, appends the draw result.
     * Afterwards adds the result to the fighter profiles.
     */
    public void init() {
        doFight();
        
        if (!draw) {
            calculatePoints();
            doMethod();
        } else {
            decision = true;
            result.append(f1.getFirst_name())
                    .append(" ")
                    .append(f1.getLast_name())
                    .append(" vs. ")
                    .append(f2.getFirst_name())
                    .append(" ")
                    .append(f2.getLast_name())
                    .append(" resulted in a draw.");
            f1.addDraw();
            f2.addDraw();
            doDrawScorecards();
            
            /*
            Set the winner and loser just for functionality purposes.
            The only thing affected by this is who is shown first on the
            results page.
            */
            winner = f1;
            loser = f2;
        }

        addResults();
    }
    
    /**
     * Calculate the result of the fight.
     * Generates 3 random numbers for each fighter between 0 and the number of
     * points they have. Adds them together and whoever has the highest total
     * is the winner. If the two totals are within 5 of each other the fight
     * is considered a draw.
     */
    private void doFight() {
        int r1 = (int) (Math.random() * f1.getPoints());
        int r2 = (int) (Math.random() * f1.getPoints());
        int r3 = (int) (Math.random() * f1.getPoints());
        int f1Total = r1 + r2 + r3;
        
        r1 = (int) (Math.random() * f2.getPoints());
        r2 = (int) (Math.random() * f2.getPoints());
        r3 = (int) (Math.random() * f2.getPoints());
        int f2Total = r1 + r2 + r3;
        
        int max = Math.max(f1Total, f2Total);
        int min = Math.min(f1Total, f2Total);
        
        if (max - min < 5) {
            draw = true;
        } else if (f1Total > f2Total) {
            winner = f1;
            loser = f2;
        } else {
            winner = f2;
            loser = f1;
        }
    }
    
    /**
     * Update the point values of the winner and the loser.
     */
    private void calculatePoints() {
        double winnerPoints = winner.getPoints();
        double loserPoints = loser.getPoints();
        
        /*
        If the winner has less points than the loser, the winner absorbs the
        points of the loser with a 0.05% bonus increase. The loser has their
        points decreased by 0.025%.
        
        If the winner is already above the loser, they gain 0.025% of the
        loser's points only if they are a champion.
        */
        if (winnerPoints <= loserPoints) {
            winner.setPoints(loserPoints += (loserPoints * 0.05));
            loser.decreasePoints(loserPoints * 0.025);
        } else {
            if (winner.isTitle_holder()) {
                winner.increasePoints(loserPoints * 0.025);
            }
            loser.decreasePoints(loserPoints * 0.025);
        }
        
        winner.addWin();
        loser.addLoss();
        
        result.append(winner.getFirst_name())
                .append(" ")
                .append(winner.getLast_name())
                .append(" ")
                .append("def. ")
                .append(loser.getFirst_name())
                .append(" ")
                .append(loser.getLast_name())
                .append(" via ");
        
        // Title changes hands if the champion is defeated in a 5 round fight
        if (loser.isTitle_holder() && rounds == 5) {
            loser.setTitle_holder(false);
            winner.setTitle_holder(true);
        }
    }
    
    /**
     * Decide the method of the fight outcome.
     */
    private void doMethod() {
        
        /*
        Only automatically update the KO and Submission likelihoods if the
        values haven't already been edited manually by the client
        */
        if (!winner.hasKoSubChanged()) {
            winner.setKoPercentage();
            winner.setSubPercentage();
        }
        
        /*
        Calculate the outcome based on how likely the winning fighter is to
        get a KO or a Submission
        */
        int r = (int) (Math.random() * 101);
        
        if (r < winner.getKoPercentage()) {
            doKo();
        } else if (r > winner.getKoPercentage() &&
                r < (winner.getKoPercentage() + winner.getSubPercentage())) {
            doSubmission();
        } else {
            doDecision();
        }
    }
    
    /**
     * If the fight results in a KO, this method decides whether it is a TKO
     * or a KO.
     * TKO is 80% likely.
     */
    private void doKo() {
        int r = (int) (Math.random() * 10);
        
        if (r >= 0 && r < 8)
            result.append("TKO");
        else
            result.append("KO");
        
        result.append(" (Round ");
        result.append(doRound());
        result.append(")");
        winner.addKoWin();
    }
    
    /**
     * If the fight results in a submission, this method decides which
     * particular submission. Partially based on a list of data compiled
     * which detailed the most common submissions used in the UFC.
     */
    private void doSubmission() {
        int r = (int) (Math.random() * 101);
        
        String[] rareSubs = {"Heel Hook", "D'Arce Choke", "Kneebar",
            "Peruvian Necktie", "Anaconda", "Triangle Armbar"};
        
        if (r >=0 && r < 33)
            result.append("Rear Naked Choke");
        else if (r >= 33 && r < 51)
            result.append("Guillotine");
        else if (r >= 51 && r < 67)
            result.append("Armbar");
        else if (r >= 67 && r < 76)
            result.append("Triangle Choke");
        else if (r >= 76 && r < 85)
            result.append("Arm Triangle");
        else if (r >= 85 && r < 89)
            result.append("Kimura");
        else
            result.append(rareSubs[(int) (Math.random() * rareSubs.length)]);
        
        result.append(" (Round ");
        result.append(doRound());
        result.append(")");
        winner.addSubWin();
    }
    
     /**
     * Calculates which round the fight ends in.
     *
     * @return the round number
     */
    private int doRound() {
        return (int) ((Math.random() * rounds) + 1);
    }
    
    /**
     * If the fight results in a decision, this method decides whether it
     * is Unanimous or Split and then calls the doScorecards() method.
     * Unanimous Decision is 90% likely.
     */
    private void doDecision() {
        int r = (int) (Math.random() * 10);
        decision = true;
        
        if (r >= 0 && r < 9) {
            result.append("Unanimous Decision");
            doScorecards(true);
        } else {
            result.append("Split Decision");
            doScorecards(false);
        }
    }
    
    /**
     * Calculates the judges' scorecards for a decision.
     * Takes a random value from an array based on how many rounds the fight
     * is and whether the decision is unanimous or split.
     * 
     * @param unan whether the decision is unanimous
     */
    private void doScorecards(boolean unan) {
        String[] unanOutcomesThree = new String[4];
        unanOutcomesThree[0] = "(30-27) (30-27) (30-27)";
        unanOutcomesThree[1] = "(30-27) (30-27) (29-28)";
        unanOutcomesThree[2] = "(30-27) (29-28) (29-28)";
        unanOutcomesThree[3] = "(29-28) (29-28) (29-28)";
        String[] splitOutcomesThree = new String[4];
        splitOutcomesThree[0] = "(30-27) (28-29) (29-28)";
        splitOutcomesThree[1] = "(30-27) (30-27) (28-29)";
        splitOutcomesThree[2] = "(29-28) (29-28) (28-29)";
        splitOutcomesThree[3] = "(27-30) (30-27) (29-28)";
        String[] unanOutcomesFive = new String[4];
        unanOutcomesFive[0] = "(50-45) (50-45) (50-45)";
        unanOutcomesFive[1] = "(49-46) (49-46) (49-46)";
        unanOutcomesFive[2] = "(50-45) (49-46) (49-46)";
        unanOutcomesFive[3] = "(48-47) (48-47) (48-47)";
        String[] splitOutcomesFive = new String[4];
        splitOutcomesFive[0] = "(48-47) (48-47) (47-48)";
        splitOutcomesFive[1] = "(49-46) (49-46) (47-48)";
        splitOutcomesFive[2] = "(50-45) (48-47) (47-48)";
        splitOutcomesFive[3] = "(48-47) (48-47) (46-49)";
        
        int r = (int) (Math.random() * 4);
        
        if (unan & rounds == 3)
            scores = unanOutcomesThree[r];
        if (!unan & rounds == 3)
            scores = splitOutcomesThree[r];
        if (unan & rounds == 5)
            scores = unanOutcomesFive[r];
        if (!unan & rounds == 5)
            scores = splitOutcomesFive[r];
    }
    
    /**
     * Calculates the judges' scorecards for a draw decision.
     * Takes a random value from an array based on how many rounds the fight
     * is. May implement functionality for different kinds of draw (Majority,
     * Split etc.) in future.
     */
    private void doDrawScorecards() {
        String[] outcomesThree = new String[3];
        outcomesThree[0] = "(28-28) (28-28) (28-28)";
        outcomesThree[1] = "(29-27) (28-28) (28-28)";
        outcomesThree[2] = "(29-29) (29-29) (29-29)";
        String[] outcomesFive = new String[3];
        outcomesFive[0] = "(47-47) (47-47) (47-47)";
        outcomesFive[1] = "(47-47) (47-47) (48-47)";
        outcomesFive[2] = "(47-48) (48-48) (48-48)";
        
        int r = (int) (Math.random() * 3);
        
        if (rounds == 3)
            scores = outcomesThree[r];
        else
            scores = outcomesFive[r];
    }
    
    /**
     * Add the result to the fighter object to be displayed on their profile.
     */
    private void addResults() {
        if (isDecision()) {
            f1.addResult(getResult() + " " + getScores());
            f2.addResult(getResult() + " " + getScores());
        } else {
            f1.addResult(getResult().toString());
            f2.addResult(getResult().toString());
        }
    }
    
    public StringBuilder getResult() {
        return result;
    }
    
    public String getScores() {
        return scores;
    }
    
    public boolean isDecision() {
        return decision;
    }
    
    public Fighter getWinner() {
        return winner;
    }
    
    public Fighter getLoser() {
        return loser;
    }
    
    public boolean isDraw() {
        return draw;
    }
}