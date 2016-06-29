package com.zombie.entityManagers;

import com.zombie.models.User;
import com.zombie.models.Zombie;
import com.zombie.repositories.UserRepository;
import com.zombie.repositories.ZombieRepository;
import com.zombie.services.NotificationService;
import com.zombie.utility.LatLng;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;

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
            System.out.println("User Danger Worker in outer loop");
            while(System.currentTimeMillis() - lastCheck < 4000)
                wait();
                System.out.println("Danger worker done waiting");
                Iterator<User> userIt = userList.iterator();
                while(userIt.hasNext()){
                    User user = userIt.next();
                    System.out.println("Danger Worker processing for: " + user.getName());

                    Iterator<Zombie> zombIt = checkForEnemies(user);
                    if(zombIt.hasNext())
                        noteService.pushNotificationToGCM(user.getGcmRegId(), "Zombies are coming", user);
                }



        }
    }
    public Iterator<Zombie> checkForEnemies(User user){
        ArrayList<Zombie> list = new ArrayList<>();
        if(System.currentTimeMillis() - user.getLastAttacked() > 5000){
            System.out.println("checking for enemies for: " + user.getName());
            LatLng latLng = new LatLng(user.getLocation().getLatitude() + .1, user.getLocation().getLongitude() -.2);
            Zombie zomb = new Zombie(user.getId(), latLng);
            zombieRepo.save(zomb);

        }
        return list.iterator();
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
