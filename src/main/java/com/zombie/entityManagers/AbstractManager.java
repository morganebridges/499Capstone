package com.zombie.entityManagers;

import com.zombie.models.User;
import com.zombie.models.Zombie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * A template for all managers
 */
@Service
@ComponentScan("com.zombie")
@EnableAutoConfiguration

public abstract class AbstractManager {

    final Logger log = LoggerFactory.getLogger(AbstractManager.class);


    HashMap<Long, User> userMap;
    HashMap<Long, Zombie> zombieMap;
    public AbstractManager(){
        userMap = new HashMap<>();
        zombieMap = new HashMap<>();


        Runnable r = () -> {
                try{
                    //log.trace("inside the run method of abstract manager");
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
            runWorkImpl();
        }
    }

	/**
	 * This is where the work of the manager happens
     */
    abstract void runWorkImpl() throws InterruptedException ;

    public void registerUser(User user){
        if(userMap.containsKey(user.getId()))
            return;
        this.userMap.put(user.getId(), user);

    }

    public void deRegisterUser(User user){
        long userId = user.getId();
        if(userMap.containsKey(userId)){
            userMap.remove(userId, userMap.get(userId));
        } else{
            //log.debug("Attempt to deregister user={} from manager failed - user not registered.", userId);
        }
    }

    public void deRegisterUser(long uid){
        deRegisterUser(userMap.get(uid));
    }

    public void registerZombie(Zombie zombie){
        if(zombieMap.containsKey(zombie.getId()))
            return;
        this.zombieMap.put(zombie.getId(), zombie);

    }

    public void deRegisterZombie(Zombie zombie){
        long zombieId = zombie.getId();
        if(userMap.containsKey(zombieId)){
            zombieMap.remove(zombieId, zombieMap.get(zombieId));
        } else{
            //log.debug("Attempt to deregister zombie={} from manager failed - zombie not registered.", zombieId);
        }
    }

    public void deRegisterZombie(long uid){
        deRegisterZombie(zombieMap.get(uid));
    }


}
