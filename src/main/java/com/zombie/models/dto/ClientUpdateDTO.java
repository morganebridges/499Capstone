package com.zombie.models.dto;

import com.zombie.models.User;
import com.zombie.models.Zombie;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

import java.util.Hashtable;

/**
 * This class is a data transfer object that will update the client
 * Created by morganebridges on 7/9/16.
 */
public class ClientUpdateDTO {

    long id;

    Zombie attackedZombie;

    Hashtable<Long, Zombie> zombies;
    User user;
    Action action;

    public ClientUpdateDTO(){}
    public ClientUpdateDTO(Zombie attackedZombie, Hashtable<Long, Zombie> zombies, User user, Action action){
        this.attackedZombie = attackedZombie;
        this. zombies = zombies;
        this.user = user;
        this.action = action;
    }
    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Zombie getAttackedZombie() {
        return attackedZombie;
    }

    public void setAttackedZombie(Zombie attackedZombie) {
        this.attackedZombie = attackedZombie;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Hashtable<Long, Zombie> getZombies() {
        return zombies;
    }

    public void setZombies(Hashtable<Long, Zombie> zombies) {
        this.zombies = zombies;
    }
}
