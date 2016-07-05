package com.zombie.entityManagers;

import com.zombie.models.User;
import com.zombie.repositories.UserRepository;
import com.zombie.repositories.ZombieRepository;
import com.zombie.services.NotificationService;
import com.zombie.services.ZombieService;
import com.zombie.utility.Globals;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Created by morganebridges on 6/21/16.
 */
@Service
@ComponentScan("com.zombie")
@EnableAutoConfiguration

public class PlayerDangerManager {
    @Autowired
    ZombieRepository zombieRepo;
    @Autowired
    UserRepository userRepo;
    @Autowired
    NotificationService noteService;
    @Autowired
    ZombieService zombieService;

    private final Logger log = LoggerFactory.getLogger(PlayerDangerManager.class);

    private ArrayList<User> userList;
    public PlayerDangerManager(){
        userList = new ArrayList<>();

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
            log.trace("User Danger Worker in outer loop");
            userList.stream()
                .forEach(
                    user -> {
                        log.trace("Danger Worker processing for user = {}", user.getId());
                        if (zombieService.areZombiesInRange(user)) {
                            // TODO: we need some way to ensure we don't keep telling the same user there are zombies over and over, maybe deregister?
                            // Another way could be to somehow put found zombies in a list so that ZombieService.areZombiesInRange() doesn't find those zombies
                            log.info("Zombies near user = {}", user.getId());
                            noteService.pushNotificationToGCM(user.getGcmRegId(), "Zombies are coming", user);
                        }
                    }
                );
            long sleepTime = Globals.ZOMBIE_LOOP_TIME - (System.currentTimeMillis() - lastCheck);
            if(sleepTime > 0){
                Thread.sleep(sleepTime);
            }
        }
    }

    public void registerUser(User user){
        if(userList.contains(user))
            return;
        this.userList.add(user);

    }

    public void deRegister(User user){
        this.userList.remove(user);
    }
}
