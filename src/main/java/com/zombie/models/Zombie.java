package com.zombie.models;
import com.zombie.models.dto.LatLng;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by morganebridges on 5/25/16.
 */
@Entity
public class Zombie {
    @Id
    @GeneratedValue
    private long id;

    //Fields relating to relationships
    private long clientKey;

    //Fields relating to zombie's location
    private double latitude;

    private double longitude;
    //Fields relating to the temporal state of the zombie
    private static long timeToLive = 30000;

    private long freshStamp;
    //Fields relating to the zombie's game logic state

    private static int defaultHp = 5;

    private int hp;
    private boolean alive;
    private boolean foundByPlayer = false;

    protected Zombie(){}

    public Zombie(long clientKey, LatLng location) {
        this.clientKey = clientKey;
        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();
        this.alive = true;
        this.freshStamp = System.currentTimeMillis();
        this.hp = defaultHp;
    }

    public Zombie(long clientKey, double latitude, double longitude){
        this.clientKey = clientKey;
        this.latitude = latitude;
        this.longitude = longitude;
        this.alive = true;
        this.freshStamp = System.currentTimeMillis();
        this.hp = defaultHp;
    }
    /** Identification methods **/
    @Override
    public String toString(){
        return String.format(
          "ZombieId[id=%d, clientKey='%s]",
                id, clientKey
        );
    }

    public long getId() {
        // TODO Auto-generated method stub
        return id;
    }


    public void setId(long id) {
        this.id = id;
    }
    public long getClientKey(){
        return clientKey;
    }


    //Location based methods.
    public LatLng getLocation(){
        return new LatLng(latitude, longitude);
    }
    public double getLongitude(){return longitude;}
    public double getLatitude(){return latitude;}
    public void setLatitude(double lat){this.latitude = lat;}
    public void setLongitude(double lng){this.longitude = lng;}

    /**
     * A method for dealing damage to a zombie.
     * @param damageDealt - as determined by the zombie service
     * @return - true if the zombie is still alive, false if it has been destroyed.
     */

    public boolean dealDamage(int damageDealt){
        hp -= damageDealt;
        if(hp < 0)
        alive = false;
        return alive;

    }
    public boolean isAlive(){
        return(this.hp > 0 && System.currentTimeMillis() - timeToLive > 0);
    }

    public boolean isFoundByPlayer() {
        return foundByPlayer;
    }

    public void setFoundByPlayer(boolean foundByPlayer) {
        this.foundByPlayer = foundByPlayer;
    }

    public boolean getAlive(){
        return alive;
    }



    /** Temporal attribute */
    public long getTimeToLive(){
        return timeToLive;
    }
    public void setTimeToLive(long time){
        Zombie.timeToLive = time;
    }
}
