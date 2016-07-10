package com.zombie.models.dto;

import com.zombie.models.User;
import com.zombie.models.Zombie;

import java.util.ArrayList;


/**
 * This class is a data transfer object that will update the client
 * Created by morganebridges on 7/9/16.
 */
public class ClientUpdateDTO {

    long id;

    long targetId;
    ArrayList<Zombie> zombies;
    User user;
    UserActionDto.Action userAction;

    public ClientUpdateDTO(){}
    public ClientUpdateDTO(long targetId, ArrayList<Zombie> zombies, User user, UserActionDto.Action userAction){
        this.targetId = targetId;
        this. zombies = zombies;
        this.user = user;
        this.userAction = userAction;
    }
    public UserActionDto.Action getUserAction() {
        return userAction;
    }

    public void setUserAction(UserActionDto.Action userAction) {
        this.userAction = userAction;
    }


    public long getTargetId() {
        return targetId;
    }

    public void setAttackedZombie(Zombie attackedZombie) {
        this.targetId = targetId;
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

    public ArrayList<Zombie> getZombies() {
        return zombies;
    }

    public void setZombies(ArrayList<Zombie> zombies) {
        this.zombies = zombies;
    }

    public enum UserAction {
        NOTHING (0),
        ATTACK (1),
        SALVAGE (2);

        private long id;

        int code;
        UserAction(int code) {
            this.code = code;
        }

        int getCode() {
            return this.code;
        }
    }
}
