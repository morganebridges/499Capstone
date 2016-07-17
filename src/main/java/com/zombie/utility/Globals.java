package com.zombie.utility;


import com.zombie.models.Zombie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Globals {
	public static final long ZOMBIE_LOOP_TIME = 20000;
	public static final long ENEMY_SPAWN_INTERVAL = 75000;
	public static final int MAXIMUM_SPAWN_NUMBER = 15;
	public static final int ZOMBIE_MANAGER_SLEEP_INTERVAL = 15000;
	public static final int WAKING_URGENCY_THRESHOLD = 7;
	public static HashMap<Long, Zombie> tempZomList = new HashMap<>();
	public static boolean zombiesGenerated = false;

	//global constants must be defined as static final
	public static final String GCMServerKey = "AIzaSyCF_pvDl4MTcLPIPkua65ZmhJvdsOOctoI";
	public static final double HORDE_LIKELIHOOD_COEFFICIENT = .05;
	public static List<String> gcmIdStore;

	public static List<String> getOrderedUserFields() {
		ArrayList<String> list = new ArrayList<>();
		list.add("name");
		list.add("total kills");
		list.add("kills");
		list.add("active");
		list.add("ammo");
		list.add("serum");
		list.add("last used serum");
		return list;
	}
	public static void prln(String line){
		System.out.println(line + "\n");
	}
	public static void addTempZom(Zombie zombie){
		tempZomList.put(zombie.getId(), zombie);
	}
	public static void removeTempZom(long zombieId){
		tempZomList.remove(zombieId);
	}
}
