package com.zombie.entityManagers;

import com.zombie.models.User;
import com.zombie.models.Zombie;
import com.zombie.repositories.UserRepository;
import com.zombie.repositories.ZombieRepository;
import com.zombie.services.NotificationService;
import com.zombie.utility.Globals;
import com.zombie.utility.LatLng;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

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
    private long lastCheck;

    volatile boolean here = false;

    Thread privateThread;
    ArrayList<User> userList;
    public PlayerDangerManager(){
        userList = new ArrayList<>();
        System.out.println("initializing danger manager");

        lastCheck = System.currentTimeMillis();
        Runnable r = new Runnable(){

            public void run(){
                try{
                    System.out.println("inside the run method of danger worker");
                    runWork();
                }catch(Exception e){
                    e.printStackTrace();
                }


            }
        };
        privateThread = new Thread(r);
        privateThread.start();
    }

    private synchronized void runWork() throws InterruptedException {

        while(true){
            lastCheck = System.currentTimeMillis();
            System.out.println("User Danger Worker in outer loop");
                Iterator<User> userIt = userList.iterator();
                while(userIt.hasNext()){
                    User user = userIt.next();
                    System.out.println("Danger Worker processing for: " + user.getName());
                    Iterator<Zombie> zombIt = checkForEnemies(user);
                    if(zombIt.hasNext())
                        noteService.pushNotificationToGCM(user.getGcmRegId(), "Zombies are coming", user);
                }
            long sleepTime = Globals.ZOMBIE_LOOP_TIME - (System.currentTimeMillis() - lastCheck);
            if(sleepTime > 0){
                Thread.sleep(sleepTime);
            }





        }
    }
    public boolean checkForEnemies(User user){
        //Checks to see if any Zombies are within the range of perception
        ArrayList<Zombie> list = new ArrayList<>();

        return false;
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
