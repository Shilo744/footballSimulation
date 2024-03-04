package games;

import java.util.ArrayList;

public class League {
    private String[] teamNames = {"Chicago White Sox", "Cleveland Guardians", "Detroit Tigers", "Kansas City Royals",
            "Minnesota Twins", "Chicago Cubs", "Cincinnati Reds", "Milwaukee Brewers", "Pittsburgh Pirates",
            "St. Louis Cardinals", "Baltimore Orioles", "Atlanta Braves", "Boston Red Sox", "Miami Marlins",
            "New York Yankees", "New York Mets"};
    private Team[] teams;
    private ArrayList games=new ArrayList();
    private Game game;
    public League() {
       makeGames();
    }
    private void makeGames(){
        teams = new Team[teamNames.length];
        for (int i = 0; i < teams.length; i++) {
            teams[i] = new Team(teamNames[i]);
        }
        new Thread(()->{
            for (int i = 0; i < teams.length; i++) {
                for (int j = 0; j < teams.length; j++) {
                    if (i != j) {
//                    System.out.println(teams[i]);
//                    System.out.println(teams[j]);
                        game = new Game(teams[i], teams[j]);
                        game.gamePhase();
                        games.add(game);
                        System.out.println(game);

//                    System.out.println(teams[i]);
//                    System.out.println(teams[j]);
                    }
                }
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
    public ArrayList<Game> getGames() {
        return games;
    }
}
