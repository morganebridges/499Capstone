package com.zombie.entityManagers;

import com.zombie.models.User;
import com.zombie.models.Zombie;
import com.zombie.models.dto.LatLng;
import com.zombie.repositories.UserRepository;
import com.zombie.repositories.ZombieRepository;
import com.zombie.services.NotificationService;
import com.zombie.services.ZombieService;
import com.zombie.services.interfaces.communications.AlarmObserver;
import com.zombie.utility.Geomath;
import com.zombie.utility.Globals;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by morganebridges on 6/21/16.
 */
@Service
@ComponentScan("com.zombie")
@EnableAutoConfiguration
public class ZombieGenerationManager extends AbstractManager implements AlarmObserver{
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

    private static ArrayList<GenRequest> requestList = new ArrayList<GenRequest>();


    static ArrayList<User> userList = new ArrayList<User>();
    public ZombieGenerationManager(){
        super();
        System.out.println("ZombieGenerationManager constructed");
    }
    private final Logger log = LoggerFactory.getLogger(ZombieGenerationManager.class);


    @Override
    synchronized void runWorkImpl() throws InterruptedException {
        long lastCheck = System.currentTimeMillis();
        System.out.println("ZombieGenerationManager runimp");


        //log.debug("ZombieGenerationManager in runWork");
        //log.debug("Zombie HashMap runWork", userMap.entrySet());
        userMap.values().stream()
                .forEach(
                        user ->{
                            User updatedUser = userRepo.findUserById(user.getId());
                            userMap.put(updatedUser.getId(), updatedUser);
                            //genRandomZombie(updatedUser);

                        }
                );


        long sleepTime = Globals.ENEMY_SPAWN_INTERVAL - (System.currentTimeMillis() - lastCheck);
        if(sleepTime > 0){
            Thread.sleep(Globals.ZOMBIE_MANAGER_SLEEP_INTERVAL);
        }
    }

    public void spawnZombies(User user) {
        //log.debug("ZombieGenerationManager.spawnZombies()");
        System.out.println("ZombieGenerationManager.spawnZombies");
        Globals.prln(user.toString());


        Random rnd = new Random();
        int numZoms = rnd.nextInt(Globals.MAXIMUM_SPAWN_NUMBER);
        if(Math.random() <= Globals.HORDE_LIKELIHOOD_COEFFICIENT)
            spawnHorde(user);

        for(int i = 0; i < numZoms; i++){
            Globals.prln("Number of zombies: " + numZoms);
            //log.trace("ZombiejGenerationManager saving zombieNumber={} ", i);
           zombieService.save(genRandomZombie(user));
        }
    }
    private void spawnHorde(User user){
        Zombie patientZero = new Zombie(user.getId(), user.getLocation());
        Random rnd = new Random();
        for(int i = 0; i < rnd.nextInt()%15; i++){
            int zomNum = rnd.nextInt(Globals.MAXIMUM_SPAWN_NUMBER * 2);
        }

    }

    @Override
    public int sendAlarm(int level) {
        return 0;
    }

    @Override
    public void beInformed(int level) {

    }
    public void requestZombiesForUser(User user, int numRequested){
        System.out.println("Requesting zombies for: \n" + user.toString());
        if(user.getLatitude() == 0 || user.getLongitude() == 0)
            return;
        for(int i = 0; i < numRequested; i++){

            genRandomZombie(user);
        }
    }
    public Zombie genRandomZombie(User user){
        Globals.prln("ZombieGenerationManager.genRandomZombie");
        Globals.prln(user.toString());
        LatLng zomLoc = Geomath.getRandomLocationWithin(user.getLatitude(), user.getLongitude(), Geomath.feetToMiles(user.getPerceptionRange()));
        System.out.printf("ZombieGenerationManager spawnZombie %f- Zombie location random %f" , zomLoc.getLatitude(), zomLoc.getLongitude());
        Zombie newZombie = new Zombie(user.getId(), zomLoc.getLatitude(), zomLoc.getLongitude());
        Globals.prln("ZombieGenerationManager : zombie in spawnZombie");
        zombieService.save(newZombie);
        return newZombie;
    }

    public boolean requestStuff(User user, int num){
        requestList.add(new GenRequest(user, num));
        return true;
    }
    public class GenRequest{
        User user;
        int num;
        public GenRequest(User user, int num){this.user = user; this.num = num;}
    }
}
