package com.zombie.services.scheduledTasks;

import com.zombie.entityManagers.AbstractManager;
import com.zombie.entityManagers.PlayerDangerManager;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by morganebridges on 7/16/16.
 */
@Component
public class ZombieGenerationScheduler extends AbstractManager implements AlarmObserver {
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

    private static HashMap<Long, Boolean> hasGenerated = new HashMap<>();

    static ArrayList<User> userList = new ArrayList<User>();
    public ZombieGenerationScheduler(){

        super();
        System.out.println("ZombieGenerationManager constructed");
    }
    @Scheduled(fixedRate = Globals.ZOMBIE_MANAGER_SLEEP_INTERVAL)
    public void manageZombieGeneration() throws InterruptedException {
       runWorkImpl();
    }@Override
    protected synchronized void runWorkImpl() throws InterruptedException {
        long lastCheck = System.currentTimeMillis();
        System.out.println("ZombieScheduledTask runimp");


        //log.debug("ZombieGenerationManager in runWork");
        //log.debug("Zombie HashMap runWork", userMap.entrySet());
        userMap.values().stream()
                .forEach(
                        user ->{
                            User thisUser = userRepo.findUserById(user.getId());
                            if(!hasGenerated.containsKey(user.getId())){
                                System.out.println("Hasn't generated for user : " + user.getName());
                                if((thisUser.getLatitude() > 0)){
                                    requestZombiesForUser(thisUser, 4);
                                    hasGenerated.put(thisUser.getId(), true);
                                }
                            }
                            if(System.currentTimeMillis() - thisUser.getLastEnemySpawned() > Globals.ENEMY_SPAWN_INTERVAL){
                                if(zombieService.findZombiesByUser(user).size() > 15)
                                    return;
                                genRandomZombie(thisUser);
                                noteService.pushNotificationToGCM(user.getGcmRegId(), "A zombie has spawned near you", thisUser);
                                thisUser.setLastEnemySpawned(System.currentTimeMillis());
                            }

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
        LatLng zomLoc = Geomath.getRandomLocationWithin(user.getLatitude(), user.getLongitude(), user.getPerceptionRange() * 3);
        System.out.printf("ZombieGenerationManager spawnZombie %f- Zombie location random %f" , zomLoc.getLatitude(), zomLoc.getLongitude());
        Zombie newZombie = new Zombie(user.getId(), zomLoc.getLatitude(), zomLoc.getLongitude());
        Globals.prln("ZombieGenerationManager : zombie in spawnZombie");
        zombieService.save(newZombie);
        return newZombie;
    }

    public boolean requestStuff(User user, int num){
        //requestList.add(new GenRequest(user, num));
        return true;
    }
    public class GenRequest{
        User user;
        int num;
        public GenRequest(User user, int num){this.user = user; this.num = num;}
    }
}
