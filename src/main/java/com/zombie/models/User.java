package com.zombie.models;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

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

    protected User(){}

	public User(String name, int totalKills, int kills, boolean active, int ammo, int serum, Date lastUsedSerum) {
		this.name = name;
		this.totalKills = totalKills;
		this.kills = kills;
		this.active = active;
		this.ammo = ammo;
		this.serum = serum;
		this.lastUsedSerum = lastUsedSerum;
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
}
