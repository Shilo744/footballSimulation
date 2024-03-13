package com.footballsimulation.utils;


import com.footballsimulation.entities.User;
import com.footballsimulation.responses.BasicResponse;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Component
public class DbUtils {

    private Connection connection;

    @PostConstruct
    public void init () {
        createDbConnection(Constants.DB_USERNAME, Constants.DB_PASSWORD);
    }

    private void createDbConnection(String username, String password){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/footballSimulation", username, password);
            System.out.println("Connection successful!");
            System.out.println();
        }catch (Exception e){
            System.out.println("Cannot create DB connection!");
        }
    }

    public boolean checkIfUsernameAvailable (String username) {
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement("SELECT username FROM users WHERE username = ?");
//            preparedStatement.setString(1,username);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            while (!resultSet.next()) {
//                available = true;
//            }
//        }catch (SQLException e){
//            e.printStackTrace();
//        }
        ArrayList<User>users= (ArrayList<User>) getAllUsers();
        for (User user:users) {
            if(user.getUsername().equals(username)){
                return false;
            }
        }
        return true;
    }
    public boolean weakPassword(String password){
        return password.length()<4;
    }
    public boolean isValidEmail(String email) {
        // בדיקה שהמייל מכיל את התווים '@' ו'.'
        if (email.contains("@") && email.contains(".")) {
            // בדיקת תווים חוקיים במייל
            for (int i = 0; i < email.length(); i++) {
                if(email.charAt(i)<'.' || email.charAt(i)>'z'){
                    return false;
                }
            }
            // בדיקה על סיום המייל ב'.com' או '.co.il'
            String[] parts = email.split("@");
            String domain = parts[1];
            if (domain.endsWith(".com") || domain.endsWith(".co.il")) {
                return true;
            }
        }
        return false;
    }

    public BasicResponse addUser (User user) {
        BasicResponse basicResponse;
        boolean success = false;
        Integer errorCode=null;
        boolean check=checkIfUsernameAvailable(user.getUsername());
        if(!check){
            errorCode=Errors.ERROR_SIGN_UP_USERNAME_TAKEN;
        }else if(weakPassword(user.getPassword())){
            errorCode=Errors.WEAK_PASSWORD;
        }else if(!isValidEmail(user.getEmail())){
            errorCode=Errors.WRONG_MAIL;
        }else {
        try {
            if (check) {
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users (username, password, email, balance) VALUES ( ? , ? , ? , ?)");
                preparedStatement.setString(1, user.getUsername());
                preparedStatement.setString(2, user.getPassword());
                preparedStatement.setString(3, user.getEmail());
                preparedStatement.setFloat(4, user.getBalance());
                preparedStatement.executeUpdate();
                success = true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
        basicResponse=new BasicResponse(success,errorCode);
        return basicResponse;
    }

    public List<User> getAllUsers () {
        List<User> allUsers = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String email = resultSet.getString("email");
                float balance = resultSet.getFloat("balance");
                User user = new User(id,username,password,email,balance);
                allUsers.add(user);
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
        return allUsers;
    }

    public User getUser (String username,String password) {
        for (User user:getAllUsers()) {
            if(user.getUsername().equals(username) && user.getPassword().equals(password)){
                return user;
            }
        }
        return null;
    }
    public float getBalance (String username,String password) {
        List<User> allUsers = getAllUsers();
        float balance=0;
        for (User user:allUsers) {
            if(user.getUsername().equals(username) && user.getPassword().equals(password)){
                balance= user.getBalance();
                break;
            }
        }
        return balance;
    }

    /*public boolean checkCredentials (String username, String password) {
        boolean ok = false;
        if (checkIfUsernameAvailable(username)) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE password = ? and username = ?");
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,password);
        }
    }*/

    public User login (String username, String password) {
        User user = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT id FROM users WHERE username = ? AND password = ? ");
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                user = new User();
                user.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;

    }

    public User getUserBySecret (String secret) {
        User user = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE secret = ?");
            preparedStatement.setString(1, secret);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                user = new User();
                user.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;

    }
    public Integer getIdByUsername(String username,String password) {
        Integer id = null; // או ערך כלשהו שמייצג שאין ID

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                id = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id;
    }


    public User updateDetails(int id, String newPassword, String newEmail) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE users SET password = ?, email = ? WHERE id = ?");
            preparedStatement.setString(1, newPassword);
            preparedStatement.setString(2, newEmail);
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();

            return getUserById(id);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update user details: " + e.getMessage());
        }
    }

    public User getUserById(int id) {
        User user = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM users WHERE id = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setEmail(resultSet.getString("email"));
                // ניתן להוסיף עוד שדות לאובייקט User בהתאם למבנה הטבלה
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get user by ID: " + e.getMessage());
        }
        return user;
    }

}
