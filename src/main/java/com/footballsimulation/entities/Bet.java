package com.footballsimulation.entities;

import games.Game;

public class Bet {
    private Game game;
    private User user;
    private int choice;
    private int bettingAmount;
    private int tieReward;
    private int homeReward;
    private int guestReward;
    private boolean over;
    private boolean win;

    public Bet(Game game, User user, int choice, int bettingAmount, int tieReward, int homeReward, int guestReward) {
        this.game = game;
        this.user = user;
        this.choice = choice;
        this.bettingAmount = bettingAmount;
        this.tieReward = tieReward;
        this.homeReward = homeReward;
        this.guestReward = guestReward;
        this.over = false;
    }

    public boolean checkFinish(){
        if(game.isGameOver()){
            over=true;
            if(choice==game.winner()){
                win=true;
            }
            return true;
        }
        return false;
    }
    public int reward(){
        int multiple=0;
        if(win){
            switch (choice){
                case Game.HOME_WIN -> multiple=homeReward;
                case Game.GUEST_WIN -> multiple=guestReward;
                case Game.TIE -> multiple=tieReward;
            }
        }
        return bettingAmount*multiple/100;
    }
    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public int getTieReward() {
        return tieReward;
    }

    public void setTieReward(int tieReward) {
        this.tieReward = tieReward;
    }

    public int getHomeReward() {
        return homeReward;
    }

    public void setHomeReward(int homeReward) {
        this.homeReward = homeReward;
    }

    public int getGuestReward() {
        return guestReward;
    }

    public void setGuestReward(int guestReward) {
        this.guestReward = guestReward;
    }

    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }
}
