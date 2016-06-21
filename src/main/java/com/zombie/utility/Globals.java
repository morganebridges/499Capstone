package com.zombie.utility;


import java.util.ArrayList;
import java.util.List;

public class Globals {
	//global constants must be defined as static final
	static final String GCMServerKey = "AIzaSyCF_pvDl4MTcLPIPkua65ZmhJvdsOOctoI";


	public static List<String> gcmIdStore;
	
	public static String getGCMServerKey(){
		return GCMServerKey;
	}

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
}
