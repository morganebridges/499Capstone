package com.zombie.models;

/**
 * Created by morganebridges on 7/10/16.
 */
public class ZombieTraits {

    private static double biteRange = 2;
    private static double speed = 2;
    private static long timeToLive = 30000;
    private static int defaultHp = 5;



    public static int getDefaultHp() {
        return defaultHp;
    }

    public static void setDefaultHp(int defaultHp) {
        ZombieTraits.defaultHp = defaultHp;
    }

    public static long getTimeToLive() {
        return timeToLive;
    }

    public static void setTimeToLive(long timeToLive) {
        ZombieTraits.timeToLive = timeToLive;
    }

    public static void setSpeed(double speedArg){
        speed = speedArg;
    }
    public static double getSpeed(){
        return speed;
    }

    public static double getBiteRange() {
        return biteRange;
    }

    public static void setBiteRange(double biteRange) {
        ZombieTraits.biteRange = biteRange;
    }
}
