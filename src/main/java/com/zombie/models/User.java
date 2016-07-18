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
	private int hp;

	/* Fields relating to time */
	private Date lastUsedSerum;
	private Date lastModified;
	//A value that represents the last time zombies were spawned
	private long lastEnemySpawned;

	//ranges in meters (default)
	private double attackRange;
	private double perceptionRange;

	//Google Cloud Messaging registration Id
	private String gcmId;

	// lat and long individually to avoid mapping issue with db, update atomically with LatLng object
	private double latitude;
	private double longitude;



	public User(boolean active, int ammo, String gcmId, long id, int kills, long lastEnemySpawned, Date lastModified, Date lastUsedSerum, double latitude, double longitude, String name, double attackRange, int serum, int totalKills, double perceptionRange, int hp) {
		this.active = active;
		this.ammo = ammo;
		this.gcmId = gcmId;
		this.id = id;
		this.kills = kills;
		this.hp = hp;


		this.lastEnemySpawned = lastEnemySpawned;
		this.lastModified = lastModified;
		this.lastUsedSerum = lastUsedSerum;

		this.latitude = latitude;
		this.longitude = longitude;
		this.name = name;
		this.perceptionRange = perceptionRange;
		this.attackRange = attackRange;

		this.serum = serum;
		this.totalKills = totalKills;

	}

	public User(String name, int totalKills, int kills, boolean active, int ammo, int serum, long l, double attackRange, Date date) {
		this.totalKills = totalKills;
		this.kills = kills;
		this.active = active;
		this.ammo = ammo;
		this.serum = serum;
		this.lastEnemySpawned = l;
		this.attackRange = attackRange;
		this.lastModified = date;
	}


    public User(){}

    public User(String name){
		this.lastModified = new Date();
		this.attackRange = 100;
	    this.name = name;
	    this.totalKills = 0;
	    this.kills = 0;
	    this.active = true;
	    this.ammo = 5;
	    this.serum = 5;
	    this.lastUsedSerum = new Date();
		this.attackRange = 30.0;
		this.lastEnemySpawned = System.currentTimeMillis();
		this.perceptionRange = 60.0;
    }

	public User(String name, int totalKills, int kills, boolean active, int ammo, int serum, long lastEnemySpawned, double attackRange, Date lastModified, double latitude, double longitude) {
		this.name = name;
		this.totalKills = totalKills;
		this.kills = kills;
		this.active = active;
		this.ammo = ammo;
		this.serum = serum;
		this.lastUsedSerum = new Date();
		this.attackRange = attackRange;
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
	public void setAttackRange(double attackRange) {
		this.attackRange = attackRange;
	}

	public double getPerceptionRange() {
		return perceptionRange;
	}

	public void setPerceptionRange(double perceptionRange) {
		this.perceptionRange = perceptionRange;
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



	public double getattackRange() {
		return attackRange;
	}

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

	public void setattackRange(double attackRange) {
		this.attackRange = attackRange;
	}
	@Override
	public String toString(){
		return "" +
				"id : " + this.id + "\n" +
				"name : " + this.name + "\n" +
				"latitude: " + this.latitude + "\n" +
				"longitude : " + this.longitude + "\n";

	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}
}
