package com.zombie.entityManagers;

import com.zombie.models.User;
import com.zombie.models.Zombie;
import com.zombie.repositories.UserRepository;
import com.zombie.repositories.ZombieRepository;
import com.zombie.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.Iterator;

/**
 * Created by morganebridges on 6/21/16.
 */

@Service
@ComponentScan("com.zombie")
@EnableAutoConfiguration
public class ZombieProximityManager {
    @Autowired
    ZombieRepository zombieRepo;
    @Autowired
    UserRepository userRepo;
    @Autowired
    NotificationService noteService;

    //TODO: make this thing actually check a range.
    public Iterator<Zombie> findZombiesInRange(User user) {
        return zombieRepo.findByClientKey(user.getId()).iterator();
    }
}
