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

import java.util.Random;

/**
 * Created by morganebridges on 6/21/16.
 */
@Service
@ComponentScan("com.zombie")
@EnableAutoConfiguration

public class ZombieGenerationManager extends AbstractManager{
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


    @Override
    synchronized void runWorkImpl() throws InterruptedException {
        long lastCheck = System.currentTimeMillis();

        log.debug("ZombieGenerationManager in runWork");
        log.debug("Zombie HashMap runWork", userMap.entrySet());
        userMap.values().stream()
                .forEach(this::spawnZombies);
        long sleepTime = Globals.ENEMY_SPAWN_INTERVAL - (System.currentTimeMillis() - lastCheck);
        if(sleepTime > 0){
            Thread.sleep(Globals.ZOMBIE_MANAGER_SLEEP_INTERVAL);
        }
    }

    private void spawnZombies(User user) {
        log.debug("ZombieGenerationManager.spawnZombies()");
        Random rnd = new Random();
        int numZoms = rnd.nextInt(Globals.MAXIMUM_SPAWN_NUMBER);
        if(Math.random() <= Globals.HORDE_LIKELIHOOD_COEFFICIENT)
            spawnHorde(user);

        for(int i = 0; i < numZoms; i++){
            log.trace("ZombiejGenerationManager saving zombieNumber={} ", i);
            LatLng zomLoc = Geomath.getRandomLocationWithin(user.getLatitude(), user.getLongitude(), Geomath.feetToMiles(user.getPerceptionRange()));
            log.debug("ZombieGenerationManager spawnZombies() - Zombie location random {} ,  {}", zomLoc.getLatitude(), zomLoc.getLongitude());
            Zombie newZombie = new Zombie(user.getId(), zomLoc.getLatitude(), zomLoc.getLongitude());
            zombieService.save(newZombie);
        }
    }
    private void spawnHorde(User user){
        Zombie patientZero = new Zombie(user.getId(), user.getLocation());
        Random rnd = new Random();
        int zomNum = rnd.nextInt(Globals.MAXIMUM_SPAWN_NUMBER * 2);

    }
}
