package com.zombie.models;

import com.zombie.utility.LatLng;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

/**
 * Created by morganebridges on 5/25/16.
 */
@Entity
public class User {
    @Id
    @GeneratedValue
    private long id;
    
    private String name;
    private int totalKills;
	private int kills;
	private boolean active;
	private int ammo;
	private int serum;
	private Date lastUsedSerum;

	//Google Cloud Messaging registration Id
	private String gcmId;

	// lat and long individually to avoid mapping issue with db, update atomically with LatLng object
	private double latitude;
	private double longitude;
	
    public User(){}
    public User(String name){
        this.name = name;
    }

	public User(String name, int totalKills, int kills, boolean active, int ammo, int serum, Date lastUsedSerum) {
		this.name = name;
		this.totalKills = totalKills;
		this.kills = kills;
		this.active = active;
		this.ammo = ammo;
		this.serum = serum;
		this.lastUsedSerum = lastUsedSerum;
	}

	public Iterator<Object> getAllFields() {
		ArrayList<Object> list = new ArrayList<>();
		list.add(this.name);
		list.add(this.totalKills);
		list.add(this.kills);
		list.add(this.active);
		list.add(this.ammo);
		list.add(this.serum);
		list.add(this.lastUsedSerum);
		//leaving location out of import/export for now
		return list.iterator();
	}

	public void updateLocation(LatLng lastLocation){
		this.latitude = lastLocation.getLatitude();
		this.longitude = lastLocation.getLongitude();
	}

	public void updateLocation(double latitude, double longitude){
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	@Override
    public String toString(){
       return name;
    }

	public long getId() {
		// TODO Auto-generated method stub
		return id;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}
	public String getGcmRegId() {
		return gcmId;
	}
	public void setGcmRegId(String regId){
		this.gcmId = regId;
	}
	public void setName(String name) {
		this.name = name;
	}

	public int getTotalKills() {
		return totalKills;
	}

	public void setTotalKills(int totalKills) {
		this.totalKills = totalKills;
	}

	public int getKills() {
		return kills;
	}

	public void setKills(int kills) {
		this.kills = kills;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public int getAmmo() {
		return ammo;
	}

	public void setAmmo(int ammo) {
		this.ammo = ammo;
	}

	public int getSerum() {
		return serum;
	}

	public void setSerum(int serum) {
		this.serum = serum;
	}

	public Date getLastUsedSerum() {
		return lastUsedSerum;
	}

	public void setLastUsedSerum(Date lastUsedSerum) {
		this.lastUsedSerum = lastUsedSerum;
	}

	public LatLng getLocation(){
		return new LatLng(latitude, longitude);
	}

	public void setLocation(LatLng location) {
		this.latitude = location.getLatitude();
		this.longitude = location.getLongitude();
	}

	public void setLocation(long latitude, long longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}
}
