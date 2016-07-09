package com.zombie.entityManagers;

import com.zombie.models.User;
import com.zombie.models.Zombie;
import com.zombie.models.dto.LatLng;
import com.zombie.repositories.UserRepository;
import com.zombie.repositories.ZombieRepository;
import com.zombie.services.NotificationService;
import com.zombie.services.ZombieService;
import com.zombie.utility.Geomath;
import com.zombie.utility.Globals;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by morganebridges on 6/21/16.
 */
@Service
@ComponentScan("com.zombie")
@EnableAutoConfiguration

public class ZombieGenerationManager {
    @Autowired
    ZombieRepository zombieRepo;
    @Autowired
    UserRepository userRepo;
    @Autowired
    NotificationService noteService;
    @Autowired
    ZombieService zombieService;
    @Autowired
    PlayerDangerManager dangerManager;

    private final Logger log = LoggerFactory.getLogger(ZombieGenerationManager.class);

    private HashMap<Long, User> userMap;
    public ZombieGenerationManager(){
        userMap = new HashMap<>();

        log.debug("initializing danger manager");

        Runnable r = () -> {
            try{
                log.trace("inside the run method of danger worker");
                runWork();
            }catch(Exception e){
                e.printStackTrace();
            }
        };
        Thread privateThread = new Thread(r);
        privateThread.start();
    }

    private synchronized void runWork() throws InterruptedException {

        while(true){
            long lastCheck = System.currentTimeMillis();
            log.trace("ZombieGenerationManager.runWork()");
            userMap.entrySet().stream()
                    .forEach(
                            mapEntry -> {
                                User user = mapEntry.getValue();
                                if(zombieService.findZombiesInRange(user).size() < Globals.MAXIMUM_SPAWN_NUMBER){
                                    spawnZombies(user);
                                }
                            }
                    );
            long sleepTime = Globals.ENEMY_SPAWN_INTERVAL - (System.currentTimeMillis() - lastCheck);
            if(sleepTime > 0){
                Thread.sleep(sleepTime);
            }
        }
    }

    private void spawnZombies(User user) {
        log.debug("ZombieGenerationManager.spawnZombies()");
        Random rnd = new Random();
        int numZoms = rnd.nextInt(3);
        for(int i = 0; i < numZoms; i++){
            LatLng zomLoc = Geomath.getRandomLocationWithin(user.getLatitude(), user.getLongitude(), Geomath.feetToMiles(user.getRange()));
            log.debug("ZombieGenerationManager spawnZombies() - Zombie location random {} ,  {}", zomLoc.getLatitude(), zomLoc.getLongitude());
            Zombie newZombie = new Zombie(user.getId(), zomLoc.getLatitude(), zomLoc.getLongitude());
            zombieService.save(newZombie);
        }
    }

    public void registerUser(User user){
        if(userMap.containsKey(user.getId()))
            return;
        this.userMap.put(user.getId(), user);

    }

    public void deRegister(User user){
        if(userMap.containsKey(user.getId())){
            userMap.remove(user.getId(), userMap.get(user.getId()));
        } else{
            log.debug("Attempt to deregister user from danger manager failed - user not registered.");
        }
    }
    public void deRegister(long uid){
        deRegister(userMap.get(uid));
    }
}
