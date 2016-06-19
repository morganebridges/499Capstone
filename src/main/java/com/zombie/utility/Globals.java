package com.zombie.utility;


import java.util.List;

public class Globals {
	//global constants must be defined as static final
	static final String GCMServerKey = "AIzaSyCF_pvDl4MTcLPIPkua65ZmhJvdsOOctoI";


	public static List<String> gcmIdStore;
	
	public static String getGCMServerKey(){
		return GCMServerKey;
	}
}
