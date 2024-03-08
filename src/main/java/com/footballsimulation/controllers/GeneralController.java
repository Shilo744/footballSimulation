package com.footballsimulation.controllers;

import com.footballsimulation.Persist;
import com.footballsimulation.entities.User;
import com.footballsimulation.responses.BasicResponse;
import com.footballsimulation.responses.LoginResponse;
import com.footballsimulation.utils.DbUtils;
import games.Game;
import games.League;
import games.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static com.footballsimulation.utils.Errors.*;

@RestController
public class GeneralController {
League league=new League();
    @Autowired
    private DbUtils dbUtils;

    @Autowired
    private Persist persist;


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
    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST})
    public Object test () {
        return "Hello From Server";
    }


    @RequestMapping (value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
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

    @RequestMapping (value = "add-user")
    public boolean addUser (String username, String password, String email) {
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
    }
}
