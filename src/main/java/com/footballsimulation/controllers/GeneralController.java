package com.footballsimulation.controllers;

import com.footballsimulation.Persist;
import com.footballsimulation.entities.Bet;
import com.footballsimulation.entities.User;
import com.footballsimulation.responses.BasicResponse;
import com.footballsimulation.responses.LoginResponse;
import com.footballsimulation.utils.DbUtils;
import games.BettingSystem;
import games.Game;
import games.League;
import games.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static com.footballsimulation.utils.Errors.*;

@RestController
public class GeneralController {

    @Autowired
    public static DbUtils dbUtils;

    @Autowired
    public static Persist persist;

    League league=new League();
    BettingSystem bettingSystem;

    @Autowired
    public GeneralController(DbUtils dbUtils, Persist persist) {
        this.dbUtils = dbUtils;
        this.persist = persist;
        this.bettingSystem = new BettingSystem();

    }


    @RequestMapping(value = "teams")
    public Team[] teams () {
        return league.getTeams();
    }
    @RequestMapping(value = "future-games")
    public ArrayList<Game> futureGames () {
        return league.getFutureGames();
    }
    @RequestMapping(value = "game")
    public Game game () {
        return league.getGame();
    }
    @RequestMapping(value = "games")
    public ArrayList<Game> games () {
        return league.getGamesHistory();
    }

    @RequestMapping (value = "login", method = {RequestMethod.GET, RequestMethod.POST})
    public BasicResponse login (String username, String password) {
        BasicResponse basicResponse = null;
        boolean success = false;
        Integer errorCode = null;
        if (username != null && username.length() > 0) {
            if (password != null && password.length() > 0) {
                User user = persist.login(username, password);
                if (user != null) {
                    basicResponse = new LoginResponse(true, errorCode, user.getId());
                } else {
                    errorCode = ERROR_LOGIN_WRONG_CREDS;
                }
            } else {
                errorCode = ERROR_SIGN_UP_NO_PASSWORD;
            }
        } else {
            errorCode = ERROR_SIGN_UP_NO_USERNAME;
        }
        if (errorCode != null) {
            basicResponse = new BasicResponse(success, errorCode);
        }
        return basicResponse;
    }

    @RequestMapping (value = "register")
    public BasicResponse register(String username, String password, String email) {
        User userToAdd = new User(username, password,email);
        return dbUtils.addUser(userToAdd);
    }

    @RequestMapping (value = "get-users")
    public List<User> getUsers () {
        return dbUtils.getAllUsers();
    }
    @RequestMapping (value = "get-balance")
    public float getBalance (String username,String password) {
        return dbUtils.getBalance(username,password);

    }@RequestMapping (value = "get-active-bets")
    public LinkedList<Bet> getActiveBet (String username, String password) {
        LinkedList<Bet>bets=new LinkedList<>();
        for (Bet bet:BettingSystem.getGoingBets()) {
            if(bet.getUserId()==dbUtils.getUser(username,password).getId()){
                bets.add(bet);
            }
        }
        return bets;
    }

@RequestMapping (value = "get-over-bets")
public LinkedList<Bet> getOverBet (String username, String password) {
        LinkedList<Bet>bets=new LinkedList<>();
        for (Bet bet:BettingSystem.getOverBets()) {
            if(bet.getUserId()==dbUtils.getUser(username,password).getId()){
                bets.add(bet);
            }
        }
        return bets;
    }
    @RequestMapping (value = "make-bet")
    public boolean makeBet (String username,String password,int gameId,int amount,int choice) {
        if(amount>0){
        User user=dbUtils.getUser(username,password);
        Game game=league.getGameById(gameId);
        if(user!=null){
            if(game!=null){
                if(amount<=user.getBalance()){
        return bettingSystem.makeBet(user.getId(),game,choice,amount);
                }
        }
        }
        }
        return false;
    }
    @RequestMapping (value = "update-details")
    public User updateDetail (int id,String password,String newEmail) {
        return dbUtils.updateDetails(id,password,newEmail);
    }
    @RequestMapping (value = "update-mail")
    public boolean updateMail (String username,String password,String newMail) {

        return true;
    }
}
