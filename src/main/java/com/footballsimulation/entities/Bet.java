package com.footballsimulation.entities;

import games.Game;

public class Bet {
    private Game game;
    private int choice;
    private int bettingAmount;
    private boolean win;
    private int userId;
    public Bet() {

    }

    public int getUserId() {
        return userId;
    }

    public void setWin(boolean win) {
        this.win = win;
    }


    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Bet(int userId,Game game, int choice, int bettingAmount) {
        this.game = game;
        this.choice = choice;
        this.bettingAmount = bettingAmount;
        this.userId =userId;
    }

    public int getReward(){
        float multiple=0;
        if(win){
            switch (choice){
                case Game.HOME_WIN -> multiple=game.chances(Game.HOME_WIN);
                case Game.GUEST_WIN -> multiple=game.chances(Game.GUEST_WIN);
                case Game.TIE -> multiple=game.chances(Game.TIE);
            }
        }
        return (int) (bettingAmount*multiple/100);
    }
    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public int getChoice() {
        return choice;
    }

    public void setChoice(int choice) {
        this.choice = choice;
    }

    public int getBettingAmount() {
        return bettingAmount;
    }

    public void setBettingAmount(int bettingAmount) {
        this.bettingAmount = bettingAmount;
    }

    public boolean isOver() {
        if(game.isGameOver()){
            if(choice==game.winner()){
                win=true;
            }
            return true;
        }
        return false;
    }
    public boolean isWin(){
        return win;
    }

    @Override
    public String toString() {
        return "Bet{" +
                "game=" + game +
                ", choice=" + choice +
                ", bettingAmount=" + bettingAmount +
                ", win=" + win +
                ", userId=" + userId +
                '}';
    }
}
