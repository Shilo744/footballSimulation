package com.footballsimulation.utils;


import com.footballsimulation.Persist;
import com.footballsimulation.entities.User;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Component
public class Utils {

    @Autowired
    private Persist persist;

//    @PostConstruct
//    public void init () {
//        new Thread(() -> {
//            try {
//                final int TOTAL_USERS_TO_CREATE = 20;
//                final int TOTAL_NOTES_TO_CREATE = 1200;
//                Thread.sleep(3 * 1000);
//                List<User> users = persist.loadList(User.class);
//                Faker faker = new Faker();
//                if (users.isEmpty()) {
//                    for (int i = 0; i < TOTAL_USERS_TO_CREATE; i++) {
//                        User user = new User(faker.name().firstName(), faker.lorem().word(), faker.random().hex());
//                        persist.save(user);
//                    }
//                    Random random = new Random();
//                    for (int i = 0; i < TOTAL_NOTES_TO_CREATE; i++) {
//                        StringBuilder stringBuilder = new StringBuilder();
//                        for (int j = 0; j < 5; j++) {
//                            stringBuilder.append(faker.lorem().word()).append(" ");
//                        }
//                        Note note = new Note();
//                        note.setContent(stringBuilder.toString());
//                        note.setDate(new Date());
//                        int randomId = random.nextInt(1, 20);
//                        User user = new User();
//                        user.setId(randomId);
//                        note.setOwner(user);
//                        persist.save(note);
//                    }
//                }
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//        }).start();
//    }
}
