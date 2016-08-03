package com.zombie.services.scheduledTasks;

import com.zombie.ApplicationActiveUsers;
import com.zombie.entityManagers.PlayerDangerManager;
import com.zombie.models.User;
import com.zombie.models.ZombieTraits;
import com.zombie.models.dto.LatLng;
import com.zombie.services.NotificationService;
import com.zombie.services.UserService;
import com.zombie.services.ZombieService;
import com.zombie.services.interfaces.communications.AlarmObserver;
import com.zombie.utility.Geomath;
import com.zombie.utility.Globals;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * Created by morganebridges on 7/16/16.
 */
@Component
@Scope("singleton")
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
    private ArrayList<User> users;
    public ZombieMovementScheduler(){
        Globals.prln("Zombie Movement Scheduler constructed");
        users = new ArrayList<>();

    }

    @Scheduled(fixedRate = Globals.ZOMBIE_MOVEMENT_REFRESH_INTERVAL)
    public void manageZombieGeneration() throws InterruptedException {
        runTask();
    }

    protected synchronized void runTask() throws InterruptedException {
        //Globals.prln("ZombieMovementScheduler runimp");
        //Globals.prln("ZombieMovementSched users list has size = " + users.size() + 3);
        users.forEach(
                user ->{
            Globals.prln("ZombieMovementSched fora user : " + user.getName());
            //todo register zombies or use in-memory ones instead of getting them all from the DB
            zombieService.findZombiesByUser(user).stream().forEach(
                            zombie ->{
                                if(zombie.getLocation() == null) {
                                    Globals.prln("Zombie found with no location");
                                }
                                User target = userService.findUserById(zombie.getClientKey());

                                Globals.prln("Moving zombie towards ={} location={} targetLocation={}" +
                                    zombie + zombie.getLocation() + target.getLocation());
                                //We only move the zombie if they are outside of the user's attack range
                                if(!Geomath.isInRange(zombie.getLocation(), user.getLocation(), user.getattackRange())) {
                                    LatLng newLocation = advanceTowardTarget(zombie.getLocation(), target.getLocation(), ZombieTraits.getSpeed());

                                    if (Geomath.isInRange(newLocation, target.getLocation(), ZombieTraits.getBiteRange())) {
                                        // TODO: bite target
                                    }

                                    Globals.prln("Moved zombie={} location={} newLocation={}" +
                                            zombie + zombie.getLocation() + newLocation);
                                    zombie.setLongitude(newLocation.getLongitude());
                                    zombie.setLatitude(newLocation.getLatitude());

                                    //TODO: Need to put logic for calling an attack in here too
                                }
                                zombieService.save(zombie);

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
        System.out.println("***** ADVANCING TOWARDS TARGET ****");
        System.out.println("***** moverLocation = " + moverLocation + " ****");
        Globals.prln("***** targetLocation = " + targetLocation + " ****");

//        Random rnd = new Random();

        return Geomath.moveTowardsTarget(moverLocation.getLatitude(), moverLocation.getLongitude(),
                targetLocation.getLatitude(), targetLocation.getLongitude(), speed);
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

    public void registerUser(User user) {
        users.add(user);
    }

    public class GenRequest{
        User user;
        int num;
        public GenRequest(User user, int num){this.user = user; this.num = num;}
    }
}
