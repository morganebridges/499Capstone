package com.zombie.models.dto;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Represents a user update to server side.
 */
@Entity
public class UserActionDto {
    @Id
    @GeneratedValue
    long id;

    double latitude;
    double longitude;
    public Action action;
    long targetId;

    //Object additionalParam;

    /**
     * Generate received from the client.  If the update includes an action that
     * requires a target, setTarget() should be called after construction.  If the
     * action requires an additional parameter, setAdditionParameter() should be called.
     * @param id The id of the user making the update
     * @param latitude The latitude of the user
     * @param longitude The longitude of the user
     * @param action An inner enum representing the action the user is taking.
     */
    public UserActionDto(long id, long latitude, long longitude, Action action){
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.action = action;
    }

    public UserActionDto(){}

    public long getId(){
        return id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setTarget(long targetId) {
        this.targetId = targetId;
    }

    public void setAdditionalParam(Object additionalParam) {
        //this.additionalParam = additionalParam;
    }

    public static  enum Action {
        NOTHING (0),
        ATTACK (1),
        SALVAGE (2);

        private final int code;
        Action(int code) {
            this.code = code;
        }

        private int getCode() {
            return this.code;
        }
    }

    public long getTargetId(){
        return targetId;
    }

    public int getAction(){
        return 0;
    }
}
