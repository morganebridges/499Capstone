package com.zombie.models;
import com.zombie.utility.LatLng;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

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
    private int hp;
    private static int defaultHp = 5;
    private boolean alive;

    protected Zombie(){}

    public Zombie(long clientKey, LatLng location) {
        this.clientKey = clientKey;
        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();
        this.alive = true;
        this.freshStamp = System.currentTimeMillis();
    }
    public Zombie(long clientKey, double latitude, double longitude){
        this.clientKey = clientKey;
        this.latitude = latitude;
        this.longitude = longitude;
        this.alive = true;
        this.freshStamp = System.currentTimeMillis();
    }

    @Override
    public String toString(){
        return String.format(
          "GamerTag[id=%d, clientKey='%s]",
                id, clientKey
        );
    }

    public boolean isAlive(){
        return(this.hp > 0 && System.currentTimeMillis() - timeToLive > 0);
    }
	public long clientKey() {
		// TODO Auto-generated method stub
		return clientKey;
	}
	public long getId() {
		// TODO Auto-generated method stub
		return id;
	}
    public LatLng getLatLng(){
        return new LatLng(latitude, longitude);
    }
    public double getLatitude(){
        return latitude;
    }
    public double getLongitude(){
        return longitude;
    }

}
