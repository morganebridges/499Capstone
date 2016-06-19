package com.zombie.services;

import com.zombie.models.User;
import com.zombie.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by morganebridges on 6/19/16.
 */
public class UserService {
    @Autowired
    UserRepository userRepo;

    public User findUserByName(String name){
        return userRepo.findByName(name);
    }

    public User findUserByGCMId(String gcmId){
        return userRepo.findUserByGCMId(gcmId);
    }
    public User findUserById(long id){
        return userRepo.findUserById(id);
    }

    public void save(User user) {
        userRepo.save(user);
    }
}
