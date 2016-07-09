package com.zombie.utility;

import com.zombie.models.User;
import com.zombie.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Random;

/**
 * Created by morganebridges on 6/21/16.
 */


public class TestDataPrep {

    private Logger log = LoggerFactory.getLogger(TestDataPrep.class);

    private UserService userService;
    private String[] names = {"James", "Helen", "Frank", "Corin", "Morgan", "Jacob", "Ben", "Siva", "Fred", "Pickle", "Jenny",
            "Samantha", "Randy", "Jimbo", "Rashen", "Khoa", "Chee", "Sandy", "Lawerence", "Mai", "Sketch"};
    private Random randomGenerator;

    public TestDataPrep(UserService service){
        this.randomGenerator = new Random();
        this.userService = service;
    }

    public void populate(int numRecords){
        //log.trace("Generating numberOfUsers={} test users", numRecords);
        for(int i = 0; i < numRecords; i++){
            User user = new User(returnName());
            user.setAmmo(randomGenerator.nextInt(500));
            user.setKills(randomGenerator.nextInt(60));
            user.setTotalKills(randomGenerator.nextInt(130));
            user.setActive(randomGenerator.nextBoolean());
            user.setSerum(randomGenerator.nextInt(5));
            user.setLastUsedSerum(new Date());
            userService.save(user);
        }
    }

    private String returnName() {
        int index = randomGenerator.nextInt(names.length);
        return names[index];
    }
}