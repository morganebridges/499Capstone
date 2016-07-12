package com.zombie.services;

import com.zombie.entityManagers.ZombieGenerationManager;
import com.zombie.models.User;
import com.zombie.models.Zombie;
import com.zombie.repositories.UserRepository;
import com.zombie.repositories.ZombieRepository;
import com.zombie.utility.Geomath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by morganebridges on 6/21/16.
 */

@Service
@ComponentScan("com.zombie")
@EnableAutoConfiguration
public class ZombieService {
    @Autowired
    ZombieRepository zombieRepo;
    @Autowired
    UserRepository userRepo;
    @Autowired
    NotificationService noteService;
    @Autowired
    ZombieGenerationManager zombieManager;

    //TODO: make this thing actually check a range.
    public HashMap<Long, Zombie> findZombiesInRange(User user) {
        Stream<Zombie> userZombies = zombieRepo.streamByUserId(user.getId());
        HashMap<Long, Zombie> zombiesInRange = new HashMap<>();
        userZombies.forEach(
                zombie -> {
                    if(Geomath.getDistance(user.getLocation(), zombie.getLocation(), "M")
                                    <= Geomath.feetToMiles(user.getattackRange()) && zombie.isAlive()){
                        zombiesInRange.put(zombie.getId(),zombie);
                    }
                }
        );
        return zombiesInRange;

    }
    public ArrayList<Zombie> findZombiesByUser(User user){
        ArrayList<Zombie> zomArr = listToArrayList(zombieRepo.findByClientKey(user.getId()));
        ArrayList<Zombie> returnList = new ArrayList<>();
        Iterator<Zombie> zomIt = zomArr.iterator();
        while(zomIt.hasNext()){
            Zombie zom = zomIt.next();
            if(zom.isAlive() && zom.getLatitude() > 42){
                returnList.add(zom);
            }
        }
        return returnList;
    }

    //TODO: make this more efficient in the great somey or if it is a performance issue
    public boolean areZombiesInRange(User user) {
        return findZombiesInRange(user).entrySet().iterator().hasNext();
    }

    //TODO: Make a cooler way to take a list and a type and create an arraylist of list and type
    public ArrayList<Zombie> listToArrayList(List<Zombie> list){
        ArrayList<Zombie> arrayList = new ArrayList<Zombie>();
        arrayList.addAll(list);
        return arrayList;

    }
    public HashMap<Long, Zombie> listToMap(List<Zombie> list){
        HashMap<Long, Zombie> zMap = new HashMap<>();
        list.stream()
                .forEach(
                        zombie -> {
                            zMap.put(zombie.getId(), zombie);
                        }
                );
        return zMap;
    }

    public void save(Zombie newZombie) {
        zombieRepo.save(newZombie);
    }

    public ArrayList<Zombie> mapToList(HashMap<Long, Zombie> map){
        ArrayList<Zombie> list = new ArrayList<Zombie>();
        list.addAll(map.values());
        return list;
    }
    public void requestZombies(User user, int num){
        zombieManager.requestZombiesForUser(user, num);
    }
}
