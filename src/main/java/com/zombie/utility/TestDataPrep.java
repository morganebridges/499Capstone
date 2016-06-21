package com.zombie.utility;

import com.zombie.models.User;
import com.zombie.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

/**
 * Created by morganebridges on 6/21/16.
 */

@ComponentScan("com.zombie")
@EnableAutoConfiguration
public class TestDataPrep {
    @Autowired
    UserService userService;


    String[] names = {"James", "Helen", "Frank", "Corin", "Morgan", "Jacob", "Siva", "Fred", "Pickle", "Jenny",
            "Samantha", "Randy", "Jimbo", "Rashen", "Khoa", "Chee", "Sandy", "Lawerence", "Mai", "Sketch"};
    private Random randomGenerator;
    public TestDataPrep(){
        this.randomGenerator = new Random();

    }

    public void populate(int numRecords){
        for(int i = 0; i < numRecords; i++){
            User user = new User(returnName());
            user.setClientKey(user.getId());
            user.setAmmo(randomGenerator.nextInt(500));
            user.setKills(randomGenerator.nextInt(60));
            user.setTotalKills(randomGenerator.nextInt(130));
            user.setActive(randomGenerator.nextBoolean());
            user.setSerum(randomGenerator.nextInt(5));
            user.setLastUsedSerum(new Date());
            userService.save(user);
        }
    }

    public String returnName() {

        int index = randomGenerator.nextInt(names.length);
        return names[index];

    }
}