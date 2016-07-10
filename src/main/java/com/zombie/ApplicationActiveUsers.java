package com.zombie;

import com.zombie.entityManagers.PlayerDangerManager;
import com.zombie.entityManagers.ZombieGenerationManager;
import com.zombie.models.User;
import com.zombie.repositories.UserRepository;
import com.zombie.services.NotificationService;
import com.zombie.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

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
    static UserService userService;
    @Autowired
    static NotificationService noteService;
    @Autowired
    static PlayerDangerManager dangerManager;
    @Autowired
    static ZombieGenerationManager zombieGenerationManager;

    Date lastObjectRefresh;


    public ApplicationActiveUsers(){activeUsers = new HashMap<Long, User>();}
    public ApplicationActiveUsers(HashMap<Long, User> userList){activeUsers = userList;};

    public static boolean setUserActive(User user){
        try{
            //set modified stamp
            user.setLastModified(new Date());
            //register for notifications
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
    public static User setUserInactive(User user){

        if((user = activeUsers.remove(user.getId())) != null){
            user.setActive(false);
            user.setLastModified(new Date());

            dangerManager.deRegister(user.getId());
            zombieGenerationManager.deRegister(user.getId());

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


}
