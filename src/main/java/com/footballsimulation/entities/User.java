package com.footballsimulation.entities;


import com.footballsimulation.controllers.GeneralController;
import games.Game;

import java.util.ArrayList;

public class User {
    private int id;
    private String username;
    private String password;
    private float balance;
    private String email;

    private ArrayList<Bet>goingBets;
    private ArrayList<Bet>overBets;

    public boolean makeBet(Game game,int choice,int amount){
        if(amount<=balance && choice>=Game.HOME_WIN && choice<=Game.TIE){
        GeneralController.persist.updateBalanceById(id,-amount);
    addBet(new Bet(game,choice,amount));
    return true;
        }
        return false;
    }
    public void addBet(Bet bet){
     goingBets.add(bet);
    }
    public String getEmail() {
        return email;
    }

    public ArrayList<Bet> getGoingBets() {
        return goingBets;
    }
    public void addMoney(float amount){
        GeneralController.persist.updateBalanceById(id,amount);
    }
    public void setGoingBets(ArrayList<Bet> goingBets) {
        this.goingBets = goingBets;
    }

    public ArrayList<Bet> getOverBets() {
        return overBets;
    }

    public void setOverBets(ArrayList<Bet> overBets) {
        this.overBets = overBets;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User(int id, String username, String password) {
        this(username, password);
        this.id = id;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(int id, String username, String password, String email, float balance) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.balance = balance;
        this.email = email;
        goingBets=new ArrayList<>();
        overBets=new ArrayList<>();
    }
    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.balance = 1000;
        this.email = email;
    }
    public User() {
        
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isSameUsername (String username) {
        return this.username.equals(username);
    }

    public boolean isSameCreds (String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", balance=" + balance +
                ", email='" + email + '\'' +
                '}';
    }
}
