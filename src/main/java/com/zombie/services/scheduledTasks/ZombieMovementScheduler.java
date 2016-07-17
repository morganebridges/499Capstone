package com.zombie.services.scheduledTasks;

import com.zombie.entityManagers.AbstractManager;
import com.zombie.entityManagers.PlayerDangerManager;
import com.zombie.models.User;
import com.zombie.models.Zombie;
import com.zombie.models.ZombieTraits;
import com.zombie.models.dto.LatLng;
import com.zombie.repositories.UserRepository;
import com.zombie.repositories.ZombieRepository;
import com.zombie.services.NotificationService;
import com.zombie.services.UserService;
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
public class ZombieMovementScheduler extends AbstractManager implements AlarmObserver {
    @Autowired
    ZombieRepository zombieRepo;
    @Autowired
    UserService userService;
    @Autowired
    NotificationService noteService;
    @Autowired
    ZombieService zombieService;
    @Autowired
    PlayerDangerManager dangerManager;

    private static HashMap<Long, Boolean> hasGenerated = new HashMap<>();
    private static HashMap<Long, Long> lastMoved;
    static ArrayList<User> userList = new ArrayList<User>();
    public ZombieMovementScheduler(){

        super();
        System.out.println("Zombie Movement Scheduler constructed");
    }
    @Scheduled(fixedRate = Globals.ZOMBIE_MOVEMENT_REFRESH_INTERVAL)
    public void manageZombieGeneration() throws InterruptedException {
        runWorkImpl();
    }
    protected synchronized void runWorkImpl() throws InterruptedException {
        long lastCheck = System.currentTimeMillis();
        System.out.println("ZombieMovementSchduler runimp");

        //log.debug("ZombieGenerationManager in runWork");
        //log.debug("Zombie HashMap runWork", userMap.entrySet());
        Iterable<Zombie> zombIterable = zombieRepo.findAll();
        zombIterable.forEach(
                        zombie ->{

                            User target = userService.findUserById(zombie.getClientKey());
                                LatLng newLocation = advanceTowardTarget(zombie.getLocation(), target.getLocation(), ZombieTraits.getSpeed());
                                zombie.setLongitude(newLocation.getLongitude());
                                zombie.setLatitude(newLocation.getLatitude());
                                //TODO: Need to put logic for calling an attack in here too

                                zombieService.save(zombie);
                                lastMoved.put(zombie.getId(), System.currentTimeMillis());
                        }
                );


        long sleepTime = Globals.ENEMY_SPAWN_INTERVAL - (System.currentTimeMillis() - lastCheck);
        if(sleepTime > 0){
            Thread.sleep(Globals.ZOMBIE_MANAGER_SLEEP_INTERVAL);
        }
    }

    /**
     * A method that
     * @param moverLocation
     * @param targetLocation
     * @param speed - in meters / second
     * @return newLocation: A LatLng object that represents the new location of the mover.
     */
    private LatLng advanceTowardTarget(LatLng moverLocation, LatLng targetLocation, double speed) {
        boolean moverLatHigher = (moverLocation.getLatitude() >= targetLocation.getLatitude());
        boolean moverLongHigher = (moverLocation.getLongitude() >= targetLocation.getLongitude());
        double newLat, newLong;
        if(moverLatHigher)
            newLat = moverLocation.getLatitude() - Geomath.metersToDegrees(speed/2.0);
        else newLat = moverLocation.getLatitude() + Geomath.metersToDegrees(speed/2.0);
        if(moverLongHigher)
            newLong = moverLocation.getLongitude() - Geomath.metersToDegrees(speed/2.0);
        else newLong = moverLocation.getLongitude() + Geomath.metersToDegrees(speed/2.0);
        return new LatLng(newLat, newLong);

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
    public void registerZombie(Zombie zombie){

    }
    /* Accessors and Mutators */

    public PlayerDangerManager getDangerManager() {
        return dangerManager;
    }

    public void setDangerManager(PlayerDangerManager dangerManager) {
        this.dangerManager = dangerManager;
    }

    public static HashMap<Long, Boolean> getHasGenerated() {
        return hasGenerated;
    }

    public static void setHasGenerated(HashMap<Long, Boolean> hasGenerated) {
        ZombieMovementScheduler.hasGenerated = hasGenerated;
    }

    public static HashMap<Long, Long> getLastMoved() {
        return lastMoved;
    }

    public static void setLastMoved(HashMap<Long, Long> lastMoved) {
        ZombieMovementScheduler.lastMoved = lastMoved;
    }

    public NotificationService getNoteService() {
        return noteService;
    }

    public void setNoteService(NotificationService noteService) {
        this.noteService = noteService;
    }

    public static ArrayList<User> getUserList() {
        return userList;
    }

    public static void setUserList(ArrayList<User> userList) {
        ZombieMovementScheduler.userList = userList;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public ZombieRepository getZombieRepo() {
        return zombieRepo;
    }

    public void setZombieRepo(ZombieRepository zombieRepo) {
        this.zombieRepo = zombieRepo;
    }

    public ZombieService getZombieService() {
        return zombieService;
    }

    public void setZombieService(ZombieService zombieService) {
        this.zombieService = zombieService;
    }
}
