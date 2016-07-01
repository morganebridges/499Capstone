package com.zombie.services;

import com.zombie.entityManagers.PlayerDangerManager;
import com.zombie.models.User;
import com.zombie.models.Zombie;
import com.zombie.repositories.UserRepository;
import com.zombie.repositories.ZombieRepository;
import com.zombie.utility.LatLng;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

/**
 * Created by morganebridges on 6/19/16.
 */
@Service
@ComponentScan("com.zombie")
@EnableAutoConfiguration
public class UserService {
    @Autowired
    UserRepository userRepo;

    @Autowired
    PlayerDangerManager dangerManager;

    @Autowired
    ZombieRepository zombieRepo;


    public User findUserByName(String name){
        return userRepo.findByName(name);
    }

    public User findUserByGCMId(String gcmId){
        return userRepo.findUserByGCMId(gcmId);
    }

    public User findUserById(long id){
        System.out.println("USER REPO");
        System.out.println(userRepo);
        return userRepo.findUserById(id);
    }

    public Iterable<User> getAllUsers() {
        return userRepo.findAll();
    }

    public long getUserCount() {
        return userRepo.count();
    }

    public void save(User user) {
        userRepo.save(user);
    }

    public void deleteUsers(Iterable<User> users) {
        userRepo.delete(users);
    }

    public Iterable<Zombie> update(long clientKey){


        return zombieRepo.findByClientKey(clientKey);
    }

    public void login(User user){
        user.setActive(true);
        user.setLastUsedSerum(new Date());
        user.setLastAttacked(System.currentTimeMillis());
        dangerManager.registerUser(user);
    }

    public ArrayList<Zombie> generateTestZombies(User user, int count){
        ArrayList<Zombie> zomList = new ArrayList<>();
        Random randomizer = new Random();
        double posNeg;


        for(int i = 0; i < count; i++) {
            if(randomizer.nextBoolean())
                posNeg = .005;
            else posNeg = -.008;
            System.out.println(user.toString());
            System.out.println("User Location=> Lat: " + user.getLatitude() + " : Long: " + user.getLongitude());
            LatLng zombLoc = new LatLng(user.getLatitude() + posNeg, user.getLongitude() + (.0005 * (randomizer.nextInt()%5)));
            Zombie zom = new Zombie(user.getId(), zombLoc);
            zomList.add(zom);
            System.out.println("Adding Zombie:" + zom.toString());
            System.out.println("lat:" + zom.getLatitude() + ", long: " + zom.getLongitude());

        }
        return zomList;

    }
}
