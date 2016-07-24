package com.zombie;

import com.zombie.entityManagers.AbstractManager;
import com.zombie.entityManagers.PlayerDangerManager;
import com.zombie.models.User;
import com.zombie.repositories.UserRepository;
import com.zombie.services.NotificationService;
import com.zombie.services.UserService;

import com.zombie.services.ZombieService;
import com.zombie.services.interfaces.communications.ContextSubscriber;
import com.zombie.services.scheduledTasks.ZombieGenerationScheduler;
import com.zombie.services.scheduledTasks.ZombieMovementScheduler;
import com.zombie.utility.Globals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.*;

/** This class is to contain and maintain our active users.
 * Created by morganebridges on 7/9/16.
 */
@Service
@ComponentScan("com.zombie")
@EnableAutoConfiguration
public  class ApplicationActiveUsers {
    @Autowired
     UserService userService;
    @Autowired
     NotificationService noteService;
    /* Our task scheduling managers */
    @Autowired
     PlayerDangerManager dangerManager;
    @Autowired
    ZombieGenerationScheduler zombieGenScheduler;
    @Autowired
    ZombieService zombieService;
    @Autowired
    ZombieMovementScheduler zombieMovementScheduler;

    private static HashMap<Long, User> activeUsers;
    Date lastObjectRefresh;
    private static boolean appInitialized;
    private ArrayList<AbstractManager> managerList;
    private static ApplicationActiveUsers instance;

    public void initialize(){
        if(managerList == null)
            managerList = new ArrayList<AbstractManager>();
        if(instance == null)
            instance = this;

            appInitialized = true;

    }

    public ApplicationActiveUsers(){
        activeUsers = new HashMap<>();
    }

    public boolean activateUser(User user){
        try{

            Globals.prln("Activate user method in ApplicationActiveUsers");
            Globals.prln(user.toString());
            //set modified stamp
            user.setLastModified(new Date());
            //register for notifications
            dangerManager.registerUser(user);
            zombieGenScheduler.registerUser(user);
            activeUsers.put(user.getId(), user);
            zombieMovementScheduler.registerUser(user);
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
            zombieGenScheduler.deRegisterUser(user.getId());

        }else{
            //That user was not logged in.
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
        zombieGenScheduler.requestZombiesForUser(user, 5);
    }

    public ZombieGenerationScheduler introZombManager() throws Exception {
        if(zombieGenScheduler != null)
            return zombieGenScheduler;
        else throw new Exception("THE WORLD IS DEAD");
    }
    public Object subscribe(ContextSubscriber s){
        return new Object();
    }

    public ArrayList<AbstractManager> getManagerList() {
        return managerList;
    }

    public void setManagerList(ArrayList<AbstractManager> managerList) {
        this.managerList = managerList;
    }
    public static void setAppInitialized(boolean appInitialized) {
        ApplicationActiveUsers.appInitialized = appInitialized;
    }
    public Collection<User> requestUsersList(){
        return activeUsers.values();
    }

    /**
     * These functions are related to 
     */


}
