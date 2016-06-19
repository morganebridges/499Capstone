package com.zombie.services;

import com.zombie.models.User;
import com.zombie.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

/**
 * Created by morganebridges on 6/19/16.
 */
@Service
@ComponentScan("com.zombie")
@EnableAutoConfiguration
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
        System.out.println("USER REPO");
        System.out.println(userRepo);
        return userRepo.findUserById(id);
    }

    public void save(User user) {
        userRepo.save(user);
    }
}
