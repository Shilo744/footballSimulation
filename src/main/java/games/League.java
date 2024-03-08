package games;

import java.util.ArrayList;

public class League {
    private String[] teamNames = {"Chicago White Sox", "Cleveland Guardians", "Detroit Tigers", "Kansas City Royals",
            "Minnesota Twins", "Chicago Cubs", "Cincinnati Reds", "Milwaukee Brewers", "Pittsburgh Pirates",
            "St. Louis Cardinals", "Baltimore Orioles", "Atlanta Braves", "Boston Red Sox", "Miami Marlins",
            "New York Yankees", "New York Mets"};
    private Team[] teams;
    private ArrayList <Game>gamesHistory;
    private Game game;
    private ArrayList<Game>futureGames;
    public League() {
        gamesHistory =new ArrayList();
        futureGames=new ArrayList<>();
       makeGames();
    }
    private void makeGames(){
        teams = new Team[teamNames.length];
        for (int i = 0; i < teams.length; i++) {
            teams[i] = new Team(teamNames[i]);
        }
        int id=1;
        for (int i = 0; i < teams.length; i++) {
            for (int j = 0; j < teams.length; j++) {
                if (i != j) {
                    game = new Game(id,teams[i], teams[j]);
                    futureGames.add(game);
                    id++;
                }
            }
        }
        new Thread(()->{
            while (!futureGames.isEmpty()) {
                this.game = futureGames.get(0);
                futureGames.remove(game);
                game.gamePhase();
                gamesHistory.add(game);
            }
            makeGames();
        }).start();
    }
    public Team[] getTeams() {
        return teams;
    }

    public Game getGame() {
        return game;
    }

    public ArrayList<Game> getFutureGames() {
        return futureGames;
    }

    public ArrayList<Game> getGamesHistory() {
        return gamesHistory;
    }
}
