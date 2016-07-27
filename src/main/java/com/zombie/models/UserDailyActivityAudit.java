package com.zombie.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by morganebridges on 5/25/16.
 */
@Entity
public class UserDailyActivityAudit {
    @Id
    @GeneratedValue
    private long id;
	private int numberOfActiveUsers;

	public UserDailyActivityAudit(int numberOfActiveUsers) {
		this.numberOfActiveUsers = numberOfActiveUsers;
	}

	public long getId() {
		return id;
	}

	public int getNumberOfActiveUsers() {
		return numberOfActiveUsers;
	}

	@Override
	public boolean equals(Object otherObject){
		try{
			UserDailyActivityAudit otherAuditObject = (UserDailyActivityAudit)otherObject;
		}catch(ClassCastException e){
			return false;
		}

		return id == ((UserDailyActivityAudit)otherObject).getId();
	}

	@Override
	public String toString(){
		return "UserDailyActivityAudit id=" + this.id + " numberOfActiveUsers=" + this.numberOfActiveUsers;
	}
}
