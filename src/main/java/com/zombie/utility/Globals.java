package com.zombie.utility;


import java.util.ArrayList;
import java.util.List;

public class Globals {
	/**
	 * Many of these values are constants that will determine the timing of many of our
	 * play cycle tasks. Time values are in milliseconds to be comparable to System.currentTime
	 */

	public static final long ZOMBIE_LOOP_TIME = 20000;
	//Values regarding how to deal with zombie generation
	public static final long ENEMY_SPAWN_INTERVAL = 75000;
	public static final int MAXIMUM_SPAWN_NUMBER = 15;
	public static final long ZOMBIE_MANAGER_SLEEP_INTERVAL = 15000;

	//This is for those classes that are going to implement the subscription alarm interface
	public static final int WAKING_URGENCY_THRESHOLD = 7;

	//Values for the movement of zombies.
	public static final long ZOMBIE_MOVEMENT_REFRESH_INTERVAL = 1000;

	//public static HashMap<Long, Zombie> tempZomList = new HashMap<>();
	public static boolean zombiesGenerated = false;

	//global constants must be defined as static final
	public static final String GCMServerKey = "AIzaSyCF_pvDl4MTcLPIPkua65ZmhJvdsOOctoI";
	public static final double HORDE_LIKELIHOOD_COEFFICIENT = .05;
	public static List<String> gcmIdStore;

	//information about how frequently we want to be informing people about stuff via GCM (let them set it later)
	public static final long USER_TIME_BETWEEN_INFORMATIVE_MESSAGES = 75000;
	public static final long USER_TIME_BETWEEN_MILD_DANGER_MESSAGES = 35000;

	/** TODO: This is a temporary global because only have one player, to be refactored asap **/
	public static long userLastGCM = Long.MIN_VALUE;
	public static long userLastUpdate = Long.MIN_VALUE;

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
	//TODO: \n is not cross-platform
	public static void prln(String line){
		System.out.println(line + "\n");
	}

}
