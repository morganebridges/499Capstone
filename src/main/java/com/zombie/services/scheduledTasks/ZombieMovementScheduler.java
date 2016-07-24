package com.zombie.services.scheduledTasks;

import com.zombie.ApplicationActiveUsers;
import com.zombie.entityManagers.PlayerDangerManager;
import com.zombie.models.User;
import com.zombie.models.Zombie;
import com.zombie.models.ZombieTraits;
import com.zombie.models.dto.LatLng;
import com.zombie.services.NotificationService;
import com.zombie.services.UserService;
import com.zombie.services.ZombieService;
import com.zombie.services.interfaces.communications.AlarmObserver;
import com.zombie.utility.Globals;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by morganebridges on 7/16/16.
 */
@Component
public class ZombieMovementScheduler implements AlarmObserver {
    private Logger logger = LoggerFactory.getLogger(ZombieMovementScheduler.class);

    @Autowired
    ApplicationActiveUsers guru;
    @Autowired
    UserService userService;
    @Autowired
    NotificationService noteService;
    @Autowired
    ZombieService zombieService;
    @Autowired
    PlayerDangerManager dangerManager;

    public ZombieMovementScheduler(){
        Globals.prln("Zombie Movement Scheduler constructed");
    }

    @Scheduled(fixedRate = Globals.ZOMBIE_MOVEMENT_REFRESH_INTERVAL)
    public void manageZombieGeneration() throws InterruptedException {
        runTask();
    }

    protected synchronized void runTask() throws InterruptedException {
        Globals.prln("ZombieMovementScheduler runimp");
        Collection<User> users = guru.requestUsersList();
        Globals.prln("ZombieMovementSched users list has size = " + users.size());
        users.forEach(
                user ->{
            Globals.prln("ZombieMovementSched for user : " + user.getName());
            //todo register zombies or use in-memory ones instead of getting them all from the DB
            Iterable<Zombie> zombIterable = zombieService.findZombiesByUser(user);
            zombIterable.forEach(
                            zombie ->{
                                if(zombie.getLocation() == null) {
                                    Globals.prln("Zombie found with no location");
                                    User target = userService.findUserById(zombie.getClientKey());

                                    LatLng newLocation = advanceTowardTarget(zombie.getLocation(), target.getLocation(), ZombieTraits.getSpeed());
                                    Globals.prln("Moving zombie={} location={} newLocation={}" +
                                            zombie + zombie.getLocation() + newLocation);
                                    zombie.setLongitude(newLocation.getLongitude());
                                    zombie.setLatitude(newLocation.getLatitude());

                                    //TODO: Need to put logic for calling an attack in here too

                                    zombieService.save(zombie);
                                }
                            }
                    );
                }
            );
    }

    /**
     * A method that finds a point directly in between moverLocation and targetLocation at a distance of speed
     * from the moverLocation
     * @param moverLocation
     * @param targetLocation
     * @param speed - in meters / second
     * @return newLocation: A LatLng object that represents the new location of the mover.
     */
    private LatLng advanceTowardTarget(LatLng moverLocation, LatLng targetLocation, double speed) {
        //TODO: SOmeday we need to fix this.  distance represented by 1 long degree gets smaller as we get closer to the poles
        Globals.prln("***** ADVANCING TOWARDS TARGET ****");
        int latPolarity = (moverLocation.getLatitude() >= targetLocation.getLatitude()) ? -1 : 1;
        int longPolarity = (moverLocation.getLongitude() >= targetLocation.getLongitude()) ? -1 : 1;
        Random rnd = new Random();

        // This should make each unit of zombie speed = roughly 0 to 2 feet per tic
        double newLat = moverLocation.getLatitude()
                + (speed * (rnd.nextInt(3) + Globals.ZOMBIE_SPEED_UNIT_DISTANCE)) * latPolarity;
        double newLong = moverLocation.getLongitude()
                + (speed * (rnd.nextInt(3) + Globals.ZOMBIE_SPEED_UNIT_DISTANCE)) * longPolarity;
        return new LatLng(newLat, newLong);
    }

    @Override
    public int sendAlarm(int level) {
        return 0;
    }

    @Override
    public void beInformed(int level) {

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
