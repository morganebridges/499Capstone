package com.zombie.models;

import com.zombie.models.dto.LatLng;

import javax.persistence.*;
import java.util.*;

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
	private Date lastModified;
	//A value that represents the lsaat time zombies were spawned
	private long lastEnemySpawned;

	//range in feet (default)
	private double range;

	//Google Cloud Messaging registration Id

	private String gcmId;
	// lat and long individually to avoid mapping issue with db, update atomically with LatLng object
	private double latitude;
	private double longitude;

	public String getGcmId() {
		return gcmId;
	}

	public void setGcmId(String gcmId) {
		this.gcmId = gcmId;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getLastEnemySpawned() {
		return lastEnemySpawned;
	}

	public void setLastEnemySpawned(long lastEnemySpawned) {
		this.lastEnemySpawned = lastEnemySpawned;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}



    public User(){}

    public User(String name){
		this.range = 100;
	    this.name = name;
	    this.totalKills = 0;
	    this.kills = 0;
	    this.active = true;
	    this.ammo = 5;
	    this.serum = 5;
	    this.lastUsedSerum = new Date();
		this.range = 100;
		this.lastEnemySpawned = System.currentTimeMillis();
    }

	public User(String name, int totalKills, int kills, boolean active, int ammo, int serum, long lastEnemySpawned, double range, Date lastModified, double latitude, double longitude) {
		this.name = name;
		this.totalKills = totalKills;
		this.kills = kills;
		this.active = active;
		this.ammo = ammo;
		this.serum = serum;
		this.lastUsedSerum = new Date();
		this.range = range;
		this.lastEnemySpawned = lastEnemySpawned;
		this.lastModified = lastModified;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	public Iterator<Object> getAllFields() {
		ArrayList<Object> list = new ArrayList<>();
		list.add(name);
		list.add(totalKills);
		list.add(kills);
		list.add(active);
		list.add(ammo);
		list.add(serum);
		list.add(lastUsedSerum);
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
	public long getId() {
		return id;
	}

	public String getName() {
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

	public void setLocation(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}
	public double getLatitude(){
		return latitude;
	}
	public double getLongitude(){
		return longitude;
	}

	public long getlastEnemySpawneded() {
		return lastEnemySpawned;
	}

	public void setlastEnemySpawneded(long lastEnemySpawned) {
		this.lastEnemySpawned = lastEnemySpawned;
	}

	@Override
	public String toString() {
		return "" + this.getId();
	}

	public double getRange() {
		return range;
	}

	/**
	 * Considering moving zombieFound logic to a boolean flag in zombie model
	 * @return
     */
	/*public List<Zombie> getZombiesFound(){
		return zombiesFound;
	}


	public boolean isZombiesFound(){
		return(zombiesFound.size() > 0);
	}
	public List<Zombie> addFoundZombie(Zombie zombie){
		if(zombiesFound == null)
			zombiesFound = new ArrayList<>();
		zombiesFound.add(zombie);
		return zombiesFound;
	}
	public List<Zombie> addFoundZombieList(List<Zombie> newList){
		if(zombiesFound == null)
			zombiesFound = new ArrayList<>();
		zombiesFound.addAll(newList);
		return zombiesFound;
	}*/

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}
	@Override
	public boolean equals(Object otherObject){
		try{
			User otherUser = (User)otherObject;
		}catch(ClassCastException e){
			e.printStackTrace();
		}
		if(id == ((User)otherObject).getId())
			return true;

		return false;
	}

	public void setRange(double range) {
		this.range = range;
	}
}
