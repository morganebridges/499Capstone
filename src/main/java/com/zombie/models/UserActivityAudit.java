package com.zombie.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by morganebridges on 5/25/16.
 */
@Entity
public class UserActivityAudit {
    @Id
    @GeneratedValue
    private long id;
    private long userId;
	private long activityDuration;

	public UserActivityAudit(){}
	public UserActivityAudit(long userId, long activityDuration) {
		this.userId = userId;
		this.activityDuration = activityDuration;

	}

	public long getId() {
		return id;
	}

	public long getUserId() {
		return userId;
	}

	public long getActivityDuration() {
		return activityDuration;
	}

	@Override
	public boolean equals(Object otherObject){
		try{
			UserActivityAudit otherUser = (UserActivityAudit)otherObject;
		}catch(ClassCastException e){
			return false;
		}

		return id == ((UserActivityAudit)otherObject).getId();
	}

	@Override
	public String toString(){
		return "UserActivityAudit id=" + this.id + " userId=" + this.userId + "duration=" + this.activityDuration;
	}
}
