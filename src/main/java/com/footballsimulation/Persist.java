package com.footballsimulation;
import com.footballsimulation.controllers.GeneralController;
import com.footballsimulation.entities.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Transactional
@Component
@SuppressWarnings("unchecked")
public class Persist {

    private static final Logger LOGGER = LoggerFactory.getLogger(Persist.class);

    private final SessionFactory sessionFactory;

    private static final String EMAIL_REGEX =
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    @Autowired
    public Persist(SessionFactory sf) {
        this.sessionFactory = sf;
    }

    public Session getQuerySession() {
        return sessionFactory.getCurrentSession();
    }

    public void save(Object object) {
        this.sessionFactory.getCurrentSession().saveOrUpdate(object);
    }
    //
    public <T> T loadObject(Class<T> clazz, int oid) {
        return this.getQuerySession().get(clazz, oid);
    }
    //
    public <T> List<T> loadList(Class<T> clazz) {
        return  this.sessionFactory.getCurrentSession().createQuery("FROM User").list();
    }
    public void updateBalance(User user, float amount) {
        float newBalance = user.getBalance() + amount;
        user.setBalance(newBalance);
        save(user);
    }
    public void updateBalanceById(int userId, float amount) {
        User user = loadObject(User.class, userId);
        if (user != null) {
            float newBalance = user.getBalance() + amount;
            user.setBalance(newBalance);
            save(user);
        } else {
            LOGGER.error("User with ID {} not found", userId);
        }
    }
    public boolean updateMail(int userId, String mail) {
        User user = loadObject(User.class, userId);
        if (user != null) {
            if(GeneralController.dbUtils.isValidEmail(mail)){
            user.setEmail(mail);
            save(user);
            return true;
            }
        } else {
            LOGGER.error("User with ID {} not found", userId);
        }
        return false;
    }


    public User login (String username, String password) {
        User user =(User) this.sessionFactory.getCurrentSession()
                .createQuery("FROM User WHERE username = :username " +
                        "AND password = :password ")
                .setParameter("username", username)
                .setParameter("password", password)
                .uniqueResult();
        return user;
    }

    public User register (String username, String password, String email) {
        return (User) this.sessionFactory.getCurrentSession()
                .createQuery("FROM User WHERE username = :username " +
                        "AND password = :password AND email = :email")
                .setParameter("username", username)
                .setParameter("password", password)
                .setParameter("email", email)
                .uniqueResult();
    }

    public boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


}