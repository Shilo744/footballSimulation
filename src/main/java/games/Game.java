package games;

import java.util.Random;

public class Game {
    public static Random random = new Random();
    public static final int HUNDRED = 100;
    public static final int HOME_WIN = 1;
    public static final int GUEST_WIN = 2;
    public static final int TIE = 3;
    private static final int GOAL_CHANCE = 15;
    private static final int GAME_TIME = 30;
    private static final int MAX_MORAL_ADD=10;
    private int winner=-1;
    private Team home;
    private Team guest;
    private int homeScore, guestScore;
    private final byte START_SCORE = 0;
    private int currentTime=30;
    private int gameId;
    private boolean gameOver=false;
    private int homePower;
    private int guestPower;

    public Game(int gameId,Team home, Team guest) {
        this.home = home;
        this.guest = guest;
        this.homeScore = START_SCORE;
        this.guestScore = START_SCORE;
        this.gameId=gameId;
        this.homePower=home.getPower();
        this.guestPower=guest.getPower();
    }
    public float chances(int team){
        switch (team){
            case HOME_WIN -> {
                return calculateProfit(homePower,guestPower);
            }
            case GUEST_WIN -> {
                return calculateProfit(guestPower,homePower);
            }
            case TIE -> {
                return calculateTieProfit(homePower,guestPower);
            }
        }
        return 0;
    }
    public int calculateOdds(int team1, int team2) {
        int tie = calculateTieOdds(team1, team2);
        double chance = ((double) team1 / (team1 + team2)) * 100 * ((double) (100 - tie) / 100);
        return (int) Math.round(chance);
    }

    public int calculateProfit(int team1, int team2) {
        return Math.round((float) 100 / calculateOdds(team1, team2) * 100);
    }

    public int calculateTieOdds(int team1, int team2) {
        int totalPower = team1 + team2;
        double change = 35 - (100.0 / totalPower) * Math.abs(team1 - team2);
        if (change <= 0) {
            return 1;
        }
        return (int) Math.round(change);
    }

    public int calculateTieProfit(int team1, int team2) {
        return Math.round((float) 100 / calculateTieOdds(team1, team2) * 100);
    }

    public void gamePhase() {
        if(winner==-1) {
            currentTime = GAME_TIME;
            int homePower = home.getPower();
            int guestPower = guest.getPower();
            double homeChance = ((double) homePower / (homePower + guestPower)) * HUNDRED;
            homeChance += random.nextInt(MAX_MORAL_ADD);
            while (currentTime > 0) {
                sleep(1);
//                printState();
                if (random.nextInt(HUNDRED) <= GOAL_CHANCE) {
                    if (homeChance >= random.nextInt(HUNDRED)) {
                        homeScore++;
                    } else {
                        guestScore++;
                    }
                }
                currentTime--;
            }

//            printState();
            winner=gameOver();
            gameOver=true;
        }
    }
    public boolean isGameOver(){
        return gameOver;
    }
    public int getGameId(){
        return gameId;
    }
    public int getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(int currentTime) {
        this.currentTime = currentTime;
    }

    private int gameOver() {
        if (homeScore > guestScore) {
            home.victory();
            guest.lose();
            return HOME_WIN;
        } else if (homeScore < guestScore) {
            guest.victory();
            home.lose();
            return GUEST_WIN;
        } else {
            guest.tie();
            home.tie();
            return TIE;
        }
    }

    private void sleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void printState() {
        System.out.println(getState());
    }
    public String getWinner(){
        String name="";
        switch (winner) {
            case HOME_WIN -> name = "Home";
            case GUEST_WIN -> name = "Guest";
            case TIE -> name = "Tie";
        }
        return name;
    }
    public int winner(){
        return winner;
    }
    public Team getHome() {
        return home;
    }

    public Team getGuest() {
        return guest;
    }

    public String getState(){
        return homeScore +"-" + guestScore;
    }
    @Override
    public String toString() {
        String victory = "The victory is ";
        if (homeScore > guestScore) {
            victory += home.getName();
        } else if (guestScore > homeScore) {
            victory += guest.getName();
        } else {
            victory = "tie";
        }
        victory += " result: " + homeScore + "-" + guestScore;
        return victory;
    }
}
