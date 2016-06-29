package com.zombie.models.dto;


/**
 * Represents a user update to server side.
 */
public class UserActionDto {
    long id;
    double latitude;
    double longitude;
    int action;
    long targetId = -1;
    Object additionalParam = null;

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
    public long getId(){
        return id;
    }
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
        this.action = action.getCode();
    }
    public UserActionDto(){};
    public void setTarget(long targetId) {
        this.targetId = targetId;
    }

    public void setAdditionalParam(Object additionalParam) {
        this.additionalParam = additionalParam;
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
}
