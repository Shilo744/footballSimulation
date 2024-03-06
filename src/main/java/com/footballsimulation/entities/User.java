package com.footballsimulation.entities;


public class User {
    private int id;
    private String username;
    private String password;
    private float balance;
    private String email;

    public String getEmail() {
        return email;
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
