package com.zombie;

import com.zombie.entityManagers.AbstractManager;
import com.zombie.entityManagers.PlayerDangerManager;
import com.zombie.entityManagers.ZombieGenerationManager;
import com.zombie.models.User;
import com.zombie.repositories.UserRepository;
import com.zombie.services.NotificationService;
import com.zombie.services.UserService;

import com.zombie.services.interfaces.communications.ContextSubscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;

/** This class is to contain and maintain our active users.
 * Created by morganebridges on 7/9/16.
 */
@ComponentScan("com.zombie")
@EnableAutoConfiguration
@Service
public  class ApplicationActiveUsers {
    private static HashMap<Long, User> activeUsers;
    @Autowired
     UserService userService;
    @Autowired
     NotificationService noteService;
    @Autowired
     PlayerDangerManager dangerManager;
    @Autowired
     ZombieGenerationManager zombieGenerationManager;

    Date lastObjectRefresh;
    private static boolean appInitialized;
    private ArrayList<AbstractManager> managerList;
    static ApplicationActiveUsers instance;


    public void initialize(ApplicationActiveUsers guru){
        if(appInitialized)
            return;
        instance = guru;
        appInitialized = true;
    }
    public ApplicationActiveUsers(){
        if(appInitialized)
            throw new IllegalStateException("Don't call my constructor");
        activeUsers = new HashMap<Long, User>();
        appInitialized = true;
        managerList = new ArrayList<AbstractManager>();
    }

    public static ApplicationActiveUsers instance(){
        if(instance == null){
            throw new IllegalStateException("This should __never__ever__be__the__case___");
        }
        return instance;
    }

    public boolean activateUser(User user){
        try{
            //set modified stamp
            user.setLastModified(new Date());
            //register for notifications
            dangerManager.registerUser(user);
            zombieGenerationManager.registerUser(user);
            activeUsers.put(user.getId(), user);
            userService.save(user);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    /**
     * This method will deal with the cleaning up and book
     * @param user
     * @return
     */
    public User deactivateUser(User user){

        if((user = activeUsers.remove(user.getId())) != null){
            user.setActive(false);
            user.setLastModified(new Date());

            dangerManager.deRegisterUser(user.getId());
            zombieGenerationManager.deRegisterUser(user.getId());

        }else{
        }
        return user;

    }

    /**
     * This method refreshes the user data from hibernate to make sure that we aren't holding stale data here
     */
    public void refreshUserlist(){
        activeUsers.entrySet().stream()
        .forEach(
                entry -> {
                    activeUsers.put(entry.getKey(), userService.findUserById(entry.getKey()));
                }
        );
    }

    /**
     * If a service or a different manager notices that somebody isn't getting their fill of fun zombies,
     * we need to make sure the guru takes care of it.
     * @param user
     */
    public void requestZombies(User user) {
        zombieGenerationManager.requestZombiesForUser(user, 5);
    }

    public ZombieGenerationManager introZombManager() throws Exception {
        if(zombieGenerationManager != null)
            return zombieGenerationManager;
        else throw new Exception("THE WORLD IS DEAD");
    }
    public Object subscribe(ContextSubscriber s){
        return new Object();
    }
}
