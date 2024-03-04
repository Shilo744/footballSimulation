package games;

import java.util.Arrays;

public class Team implements victory{
    private String name;
    private Player[]players;
    private int score;

    public Team(String name) {
        this.name = name;
        this.score=0;
        players=new Player[11];
        for (int i = 0; i < players.length; i++) {
            players[i]=new Player(name.charAt(0)+""+(i+1),Game.random.nextInt(Game.HUNDRED),Game.random.nextInt(Game.HUNDRED));
        }
    }

    @Override
    public void victory() {
        score+=3;
        for (int i = 0; i < players.length; i++) {
            players[i].victory();
        }
    }
    public void lose(){
        for (int i = 0; i < players.length; i++) {
            players[i].lose();
        }
    }

    @Override
    public void tie() {
        score++;
        for (int i = 0; i < players.length; i++) {
            players[i].tie();
        }
    }
    public int getPower(){
        int totalPower=0;
        for (int i = 0; i < players.length; i++) {
            totalPower+=players[i].getAttack()+players[i].getDefence();
        }
        return totalPower;
    }

    public String getName() {
        return name;
    }
    public int getScore(){
        return score;
    }
    @Override
    public String toString() {
        return "Team: " + name + "\n"+
                "players: " + Arrays.toString(players) + "\n"+
                "score: " + score +"\n"+
                "power: "+ getPower()+"\n";
    }
}
