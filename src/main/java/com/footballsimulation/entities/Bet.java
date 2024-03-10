package com.footballsimulation.entities;

import games.Game;

public class Bet {
    private Game game;
    private int choice;
    private int bettingAmount;
    private boolean win;
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Bet(Game game, int choice, int bettingAmount) {
        this.game = game;
        this.choice = choice;
        this.bettingAmount = bettingAmount;
    }

    public int reward(){
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
}
