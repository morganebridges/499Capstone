package com.zombie.services.interfaces.communications;

/**
 * Created by morganebridges on 7/10/16.
 */
public interface AlarmObserver {
    int sendAlarm(int level);
    void beInformed(int level);

}
