package com.zombie.services;

import com.zombie.entityManagers.PlayerDangerManager;
import com.zombie.models.User;
import com.zombie.models.Zombie;
import com.zombie.models.dto.LatLng;
import com.zombie.repositories.UserRepository;
import com.zombie.repositories.ZombieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import javax.persistence.CascadeType;
import javax.persistence.OneToOne;
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

    Logger log = LoggerFactory.getLogger(UserService.class);

    public User findUserByName(String name){
        return userRepo.findByName(name);
    }

    public User findUserByGCMId(String gcmId){
        return userRepo.findUserByGCMId(gcmId);
    }

    public User findUserById(long id){
        log.trace("In findUserById userRepo={}", userRepo);
        return userRepo.findUserById(id);
    }

    public Iterable<User> getAllUsers() {
        return userRepo.findAll();
    }

    public long getUserCount() {
        return userRepo.count();
    }

    @OneToOne(cascade = {CascadeType.ALL})
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
        //TODO: is this for registration or login?  If login why setting lastUsedSerum?
        // 7.8.16 - Right now "registration" mostly refers to registering for GCM.

        user.setActive(true);
        //user.setLastUsedSerum(new Date());
        //user.setLastAttacked(System.currentTimeMillis());
        dangerManager.registerUser(user);

    }

    public ArrayList<Zombie> generateTestZombies(User user, int count){
        ArrayList<Zombie> zomList = new ArrayList<>();
        Random randomizer = new Random();
        double latPosNeg = 1;
        double longPosNeg = 1;

        log.debug("generating zombieCount={} zombies for userId={}", count, user);
        for(int i = 0; i < count; i++) {
            if(randomizer.nextBoolean())
                latPosNeg = -1;
            if(randomizer.nextBoolean())
                longPosNeg = -1;
            log.trace("User Location is Lat={} : Long={} ", user.getLatitude(), user.getLongitude());
            LatLng zombLoc = new LatLng(user.getLatitude() + (.0007 * (randomizer.nextInt()%5) * latPosNeg)
                    , user.getLongitude() + (.0007 * (randomizer.nextInt()%5) * longPosNeg));
            Zombie zom = new Zombie(user.getId(), zombLoc);
            zombieRepo.save(zom);
            zomList.add(zom);
            log.debug("New Zombie Location is Lat={} : Long={} zombieId={}",
                    zom.getLatitude(), zom.getLongitude(), zom.getId());
        }
        return zomList;

    }

    public Zombie attackZombie(User user, long zombieId){
        Zombie zombie = zombieRepo.findById(zombieId);
        //For now we just assume a one-hit kill
        if(zombie != null){
            zombie.dealDamage(5);
            zombieRepo.save(zombie);
            log.debug("ZombieId={} killed by userId={}", zombieId, user);
        }else{
            log.warn("UserId={} trying to kill a zombieId={} that could not be found", user, zombieId);
        }

        return zombie;
    }
}
